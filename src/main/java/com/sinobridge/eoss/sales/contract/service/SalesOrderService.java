/*
 * FileName: SalesOrderService.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.sales.contract.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinobridge.base.core.service.DefaultBaseService;
import com.sinobridge.base.core.utils.PaginationSupport;
import com.sinobridge.eoss.sales.contract.dao.SalesContractDao;
import com.sinobridge.eoss.sales.contract.dto.SalesRelevanceDto;
import com.sinobridge.eoss.sales.contract.model.SalesContractModel;

/**
 * <p>
 * Description: 
 * </p>
 *
 * @author vermouth
 * @version 1.0

 * <p>
 * History: 
 *
 * Date                     Author         Version     Description
 * ---------------------------------------------------------------------------------
 * 2015年3月4日 下午4:02:56          wangya        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Service
@Transactional
public class SalesOrderService extends DefaultBaseService<SalesContractModel, SalesContractDao> {

    /**
     * @param searchMap
     * @param i
     * @param parseInt
     * @return
     */
    public PaginationSupport findRelevanceBySearchMap(HashMap<String, Object> searchMap, int num, int parseInt) {
        String whereStr = "";
        Object creatorString = searchMap.get(SalesContractModel.CREATOR);
        Object contractCode = searchMap.get(SalesContractModel.CONTRACTCODE);
        Object contractAmountString = searchMap.get(SalesContractModel.CONTRACTAMOUNT);

        Object contractType = searchMap.get(SalesContractModel.CONTRACTTYPE);
        Object startTime = searchMap.get("sales_startTime");
        Object endTime = searchMap.get("sales_endTime");
        Object order_startTime = searchMap.get("order_startTime");
        Object order_endTime = searchMap.get("order_endTime");
        Object orgId = searchMap.get("orgId");
        //  String whereStr = "select c.id,c.contractName,c.ContractCode,c.ContractAmount,c.ContractState,cs.OrderStatus,cs.ReciveStatus,cs.CachetStatus,cs.InvoiceStatus,cs.changeStatus,c.isChanged,art.NAME_,art.CREATE_TIME_,art.ASSIGNEE_ ,c.ContractType,c.CreatorName,c.CreateTime,cust.ShortName from Sales_Contract c left join Sales_Contract_Status cs on c.id=cs.SaleContractId left join  sales_contract_product p on c.id = p.SaleContractId LEFT JOIN bpm_process_inst bpi ON c.ProcessInstanceId=bpi.ID_  LEFT JOIN act_ru_task art ON bpi.HI_PROC_INST_ID= art.proc_inst_id_  LEFT JOIN customer_info cust ON c.CustomerId=cust.id where 0=0 and cs.contractSnapShootId IS NULL";
        whereStr = "";
        if (creatorString != null) {
            Long creator = Long.parseLong(creatorString + "");
            whereStr += " and b." + SalesContractModel.CREATOR + "='" + creator + "' ";
        }
        if (contractCode != null) {
            whereStr += " and b." + SalesContractModel.CONTRACTCODE + " like '" + contractCode + "%'";
        }
        if (contractType != null) {
            whereStr += " and b." + SalesContractModel.CONTRACTTYPE + " ='" + contractType + "'";
        }
        if (contractAmountString != null) {
            whereStr += " and b." + SalesContractModel.CONTRACTAMOUNT + " like '" + contractAmountString + "%'";
        }

        if (order_startTime != null) {
            whereStr += " and c.BeginTime >='" + order_startTime + "'";
        }
        if (order_endTime != null) {
            whereStr += " and c.BeginTime <='" + order_endTime + "'";
        }
        if (startTime != null) {
            whereStr += " and b.SalesStartDate >='" + startTime + "'";
        }
        if (endTime != null) {
            whereStr += " and b.SalesStartDate <='" + endTime + "'";
        }
        if (orgId != null) {
            String sql = "SELECT orgcode FROM sys_orgnization bt WHERE bt.OrgId='" + orgId + "'";
            Query orgquery = getDao().createSQLQuery(sql);
            String orgcode = (String) orgquery.uniqueResult();
            whereStr += " AND b.Creator IN(SELECT s.staffid FROM sys_staff s  LEFT JOIN sys_stafforg o ON o.staffId = s.staffId LEFT JOIN sys_orgnization org ON o.orgId = org.orgId WHERE s.State!='0' AND org.orgCode  LIKE '" + orgcode + "%' )";
        }
        
        //排除合并的合同
        whereStr += " and b.ContractState != 'HB' ";
        
        StringBuffer buffer = new StringBuffer();
//        buffer.append("SELECT tt.*,(tt.sales_contract+tt.business_order) gg,SUM(da.`Quantity`) orderProNum ,SUM(da.`SubTotal`) orderProductAmount FROM ( ");
        buffer.append("SELECT tt.*,CONCAT(tt.ContractCode,tt.OrderCode) gg,SUM(da.`Quantity`) orderProNum ,SUM(da.`SubTotal`) orderProductAmount FROM ( ");
        buffer.append("SELECT t1.*,t2.productNum,t3.orderNum,t4.OrderCode,t4.`BeginTime`,t4.`CreateDate` orderCreatTime FROM ( ");
        buffer.append("SELECT a.`sales_contract`,a.`business_order`, b.`ContractCode`,b.`ContractAmount`,ba.`OrderStatus` salesOrder,b.`CreatorName`, b.`ContractType`  ");
        buffer.append("FROM `order_contract_map` a LEFT JOIN `sales_contract` b ON a.`sales_contract` = b.`id` ");
        buffer.append("LEFT JOIN `sales_contract_status` ba ON ba.`SalecontractId` = a.`sales_contract`  ");
        buffer.append("LEFT JOIN `business_order` c ON a.`business_order` = c.`id` WHERE 1=1 " + whereStr + ") t1  ");
        buffer.append("LEFT JOIN (SELECT bb.`SaleContractId`,SUM(bb.`Quantity`) productNum FROM `sales_contract_product` bb GROUP BY bb.`SaleContractId`) t2  ");
        buffer.append("ON t1.sales_contract = t2.SaleContractId  ");
        buffer.append("LEFT JOIN (SELECT aa.`sales_contract`,COUNT(*) orderNum FROM order_contract_map aa GROUP BY aa.`sales_contract`) t3  ");
        buffer.append("ON t1.sales_contract = t3.sales_contract  ");
        buffer.append("LEFT JOIN (SELECT d.`id`,d.`OrderCode`,d.`CreateDate`,d.`BeginTime` FROM `business_order` d) t4  ");
        buffer.append("ON t1.business_order = t4.id ) tt,`business_order_product` da  ");
        buffer.append("WHERE tt.sales_contract = da.`SaleContractId` AND tt.business_order = da.`OrderId` AND tt.BeginTime IS NOT NULL GROUP BY gg ORDER BY orderCreatTime DESC ");
        String sql = buffer.toString();
        int totals = getTotalCount(sql);
        Query query = getDao().createSQLQuery(sql);
        List<Map<String, Object>> rst = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        List<Object> items = query.setFirstResult(num).setMaxResults(parseInt).list();
        PaginationSupport ps = new PaginationSupport(items, totals, parseInt, num);
        return ps;
    }

    /**
     * <code>getTotalCount</code>
     * 得到总条数
     * @param sql
     * @param params
     * @return
     * @since   2015年03月04日   wangya
     */
    @SuppressWarnings("unchecked")
    public Integer getTotalCount(String sql) {
        String countQueryString = " select COUNT(*) total from (" + sql + ") t";
        Query orgquery = getDao().createSQLQuery(countQueryString);
        List<Map<String, Object>> rst = orgquery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        BigInteger total = (BigInteger) rst.get(0).get("total");
        //(BigInteger) orgquery.uniqueResult();
        return Integer.parseInt(total.toString());
    }

    /**
     * 导出查询合同数据
     * @return
     */
    public List<SalesRelevanceDto> orderContracts(Map<String, Object> searchMap) {
        List<SalesRelevanceDto> rs = new ArrayList<SalesRelevanceDto>();
        String whereStr = "";
        Object creatorString = searchMap.get(SalesContractModel.CREATOR);
        Object contractCode = searchMap.get(SalesContractModel.CONTRACTCODE);
        Object contractAmountString = searchMap.get(SalesContractModel.CONTRACTAMOUNT);

        Object contractType = searchMap.get(SalesContractModel.CONTRACTTYPE);
        Object startTime = searchMap.get("sales_startTime");
        Object endTime = searchMap.get("sales_endTime");
        Object order_startTime = searchMap.get("order_startTime");
        Object order_endTime = searchMap.get("order_endTime");
        Object orgId = searchMap.get("orgId");
        //  String whereStr = "select c.id,c.contractName,c.ContractCode,c.ContractAmount,c.ContractState,cs.OrderStatus,cs.ReciveStatus,cs.CachetStatus,cs.InvoiceStatus,cs.changeStatus,c.isChanged,art.NAME_,art.CREATE_TIME_,art.ASSIGNEE_ ,c.ContractType,c.CreatorName,c.CreateTime,cust.ShortName from Sales_Contract c left join Sales_Contract_Status cs on c.id=cs.SaleContractId left join  sales_contract_product p on c.id = p.SaleContractId LEFT JOIN bpm_process_inst bpi ON c.ProcessInstanceId=bpi.ID_  LEFT JOIN act_ru_task art ON bpi.HI_PROC_INST_ID= art.proc_inst_id_  LEFT JOIN customer_info cust ON c.CustomerId=cust.id where 0=0 and cs.contractSnapShootId IS NULL";
        whereStr = "";
        if (creatorString != null) {
            Long creator = Long.parseLong(creatorString + "");
            whereStr += " and b." + SalesContractModel.CREATOR + "='" + creator + "' ";
        }
        if (contractCode != null) {
            whereStr += " and b." + SalesContractModel.CONTRACTCODE + " like '" + contractCode + "%'";
        }
        if (contractType != null) {
            whereStr += " and b." + SalesContractModel.CONTRACTTYPE + " ='" + contractType + "'";
        }
        if (contractAmountString != null) {
            whereStr += " and b." + SalesContractModel.CONTRACTAMOUNT + " like '" + contractAmountString + "%'";
        }

        if (order_startTime != null) {
            whereStr += " and c.BeginTime >='" + order_startTime + "'";
        }
        if (order_endTime != null) {
            whereStr += " and c.BeginTime <='" + order_endTime + "'";
        }
        if (startTime != null) {
            whereStr += " and b.SalesStartDate >='" + startTime + "'";
        }
        if (endTime != null) {
            whereStr += " and b.SalesStartDate <='" + endTime + "'";
        }
        if (orgId != null) {
            String sql = "SELECT orgcode FROM sys_orgnization bt WHERE bt.OrgId='" + orgId + "%'";
            Query orgquery = getDao().createSQLQuery(sql);
            String orgcode = (String) orgquery.uniqueResult();
            whereStr += " AND b.Creator IN(SELECT s.staffid FROM sys_staff s  LEFT JOIN sys_stafforg o ON o.staffId = s.staffId LEFT JOIN sys_orgnization org ON o.orgId = org.orgId WHERE s.State!='0' AND org.orgCode  LIKE '" + orgcode + "' )";
        }
        
        //排除合并的合同
        whereStr += " and b.ContractState != 'HB' ";
        
        StringBuffer buffer = new StringBuffer();
//        buffer.append("SELECT tt.*,(tt.sales_contract+tt.business_order) gg,SUM(da.`Quantity`) orderProNum ,SUM(da.`SubTotal`) orderProductAmount FROM ( ");
        buffer.append("SELECT tt.*,CONCAT(tt.ContractCode,tt.OrderCode) gg,SUM(da.`Quantity`) orderProNum ,SUM(da.`SubTotal`) orderProductAmount FROM ( ");
        buffer.append("SELECT t1.*,t2.productNum,t3.orderNum,t4.OrderCode,t4.`BeginTime`,t4.`CreateDate` orderCreatTime FROM ( ");
        buffer.append("SELECT a.`sales_contract`,a.`business_order`, b.`ContractCode`,b.`ContractAmount`,ba.`OrderStatus` salesOrder,b.`CreatorName`, b.`ContractType`  ");
        buffer.append("FROM `order_contract_map` a LEFT JOIN `sales_contract` b ON a.`sales_contract` = b.`id` ");
        buffer.append("LEFT JOIN `sales_contract_status` ba ON ba.`SalecontractId` = a.`sales_contract`  ");
        buffer.append("LEFT JOIN `business_order` c ON a.`business_order` = c.`id` WHERE 1=1 " + whereStr + ") t1  ");
        buffer.append("LEFT JOIN (SELECT bb.`SaleContractId`,SUM(bb.`Quantity`) productNum FROM `sales_contract_product` bb GROUP BY bb.`SaleContractId`) t2  ");
        buffer.append("ON t1.sales_contract = t2.SaleContractId  ");
        buffer.append("LEFT JOIN (SELECT aa.`sales_contract`,COUNT(*) orderNum FROM order_contract_map aa GROUP BY aa.`sales_contract`) t3  ");
        buffer.append("ON t1.sales_contract = t3.sales_contract  ");
        buffer.append("LEFT JOIN (SELECT d.`id`,d.`OrderCode`,d.`CreateDate`,d.`BeginTime` FROM `business_order` d) t4  ");
        buffer.append("ON t1.business_order = t4.id ) tt,`business_order_product` da  ");
        buffer.append("WHERE tt.sales_contract = da.`SaleContractId` AND tt.business_order = da.`OrderId` AND tt.BeginTime IS NOT NULL GROUP BY gg ORDER BY orderCreatTime DESC ");
        String sql = buffer.toString();
        Query query = getDao().createSQLQuery(sql);
        List<Map<String, Object>> rst = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        rs = buildExcelList(rst);
        return rs;
    }

    /**
     * 构造导出EXCEL所需要的数据
     * @param datas
     * @return
     */
    public List<SalesRelevanceDto> buildExcelList(List<Map<String, Object>> datas) {
        List<SalesRelevanceDto> rs = new ArrayList<SalesRelevanceDto>();
        for (Map<String, Object> map : datas) {
            SalesRelevanceDto rsmap = new SalesRelevanceDto();
            BigDecimal contractAmount = new BigDecimal(0.00);
            BigDecimal orderAmount = new BigDecimal(0.00);
            contractAmount = new BigDecimal(map.get("ContractAmount") == null ? "0.00" : map.get("ContractAmount").toString());
            rsmap.setContractAmount(contractAmount);//合同金额
            orderAmount = new BigDecimal(map.get("orderProductAmount") == null ? "0.00" : map.get("orderProductAmount").toString());
            rsmap.setOrderProductAmount(orderAmount);//订单分项金额
            rsmap.setContractCode(map.get("ContractCode") == null ? "" : map.get("ContractCode").toString());//合同编号
            rsmap.setOrderCode(map.get("OrderCode") == null ? "" : map.get("OrderCode").toString());//订单编号

            rsmap.setProductNum(map.get("productNum") == null ? "" : map.get("productNum").toString());//合同下单数
            rsmap.setOrderNum(map.get("orderNum") == null ? "" : map.get("orderNum").toString());//对应订单数
            rsmap.setSalesOrder(map.get("salesOrder") == null ? "" : map.get("salesOrder").toString());//合同状态
            rsmap.setOrderProNum(map.get("orderProNum") == null ? "" : map.get("orderProNum").toString());//订单下单数
            rsmap.setBeginTime(map.get("BeginTime") == null ? "" : map.get("BeginTime").toString());//订单下单日期

            /*   String contractType = map.get("ContractType").toString();

               if (contractType.equals("1000")) {
                   contractType = "产品合同";
               } else if (contractType.equals("2000")) {
                   contractType = "临时采购";
               } else if (contractType.equals("3000")) {
                   contractType = "MA续保合同";
               } else if (contractType.equals("4000")) {
                   contractType = "技术服务合同";
               } else if (contractType.equals("5000")) {
                   contractType = "采购确认函";
               } else if (contractType.equals("6000")) {
                   contractType = "软件开发合同";
               } else if (contractType.equals("7000")) {
                   contractType = "公司备件";
               } else if (contractType.equals("8000")) {
                   contractType = "客户配件";
               } else if (contractType.equals("9000")) {
                   contractType = "备货合同";
               } else if (contractType.equals("0")) {
                   contractType = "其他合同类型";
               }
               rsmap.setContractType(contractType);//合同类型
            */
            rs.add(rsmap);
        }
        return rs;
    }

    /**
     * @param creator
     * @return
     */
    public String findCreatorId(String creator) {
        String sql = "SELECT s.staffId FROM `sys_staff` s WHERE s.UserName='" + creator + "' ";
        Query query = getDao().createSQLQuery(sql);
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List<Map<String, Object>> objList = query.list();
        String id = objList.get(0).get("staffId").toString();
        return id;
    }

}
