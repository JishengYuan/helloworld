/*
 * FileName: SalesContractDao.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.sales.contract.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.ehcache.Ehcache;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.sinobridge.base.core.dao.imp.DefaultBaseDao;
import com.sinobridge.base.core.utils.PaginationSupport;
import com.sinobridge.eoss.sales.contract.constant.SalesContractConstant;
import com.sinobridge.eoss.sales.contract.dto.SalesContractInfoAndStatus;
import com.sinobridge.eoss.sales.contract.model.SalesContractModel;
import com.sinobridge.eoss.sales.invoice.model.SalesInvoicePlanModel;
import com.sinobridge.systemmanage.model.SysStaff;
import com.sinobridge.systemmanage.util.Constants;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author 3unshine
 * @version 1.0

 * <p>
 * History:
 *
 * Date                     Author         Version     Description
 * ---------------------------------------------------------------------------------
 * 2014年5月5日 下午8:24:25          3unshine        1.0         To create
 * </p>
 *
 * @since
 * @see
 */
@Repository
public class SalesContractDao extends DefaultBaseDao<SalesContractModel, Long> {

    private final static Ehcache systemUserCache = Constants.CACHE_MANAGER.getEhcache("systemUser");

    /**
     * @param seachMap
     * @param startIndex
     * @param pageSize
     * @return
     */
    @SuppressWarnings("unchecked")
    public PaginationSupport findPageBySearchMap(HashMap<String, Object> searchMap, int startIndex, Integer pageSize) {
        int start = 0;
        Object[] values = new Object[searchMap.size()];
        Object creatorString = searchMap.get(SalesContractModel.CREATOR);
        Object contractName = searchMap.get(SalesContractModel.CONTRACTNAME);
        Object contractShortname = searchMap.get(SalesContractModel.CONTRACTSHORTNAME);
        Object contractState = searchMap.get(SalesContractModel.CONTRACTSTATE);
        Object contractCode = searchMap.get(SalesContractModel.CONTRACTCODE);
        Object contractAmountString = searchMap.get(SalesContractModel.CONTRACTAMOUNT);

        Object contractAmountStringb = searchMap.get("contractAmountb");

        Object contractStateNotEquals = searchMap.get("contractStateNotEquals");
        Object contractType = searchMap.get(SalesContractModel.CONTRACTTYPE);
        Object startTime = searchMap.get(SalesContractModel.CLOSETIME);
        Object endTime = searchMap.get("endTime");
        Object customerInfo = searchMap.get(SalesContractModel.CUSTOMERID);
        String hql = "select c.id,c.contractName,c.ContractCode,c.ContractAmount,c.ContractState,cs.OrderStatus,cs.ReciveStatus,cs.CachetStatus,cs.InvoiceStatus,cs.changeStatus,c.isChanged,art.NAME_,art.CREATE_TIME_,art.ASSIGNEE_ ,c.ContractType,sum(p.Quantity) qu,sum(p.SurplusNum) sur,c.isold isold from Sales_Contract c left join Sales_Contract_Status cs on c.id=cs.SaleContractId left join  sales_contract_product p on c.id = p.SaleContractId LEFT JOIN bpm_process_inst bpi ON c.ProcessInstanceId=bpi.ID_  LEFT JOIN act_ru_task art ON bpi.HI_PROC_INST_ID= art.proc_inst_id_ where 0=0 and cs.contractSnapShootId IS NULL and c.contractState != 'FQ'";
        if (creatorString != null) {
            Long creator = Long.parseLong(creatorString + "");
            values[start++] = creator;
            hql += " and c." + SalesContractModel.CREATOR + "=?";
        }
        if (contractName != null) {
            values[start++] = contractName + "";
            hql += " and c." + SalesContractModel.CONTRACTNAME + " like ?";
        }
        if (contractShortname != null) {
            values[start++] = contractShortname + "";
            hql += " and c." + SalesContractModel.CONTRACTSHORTNAME + " like ?";
        }
        if (contractState != null) {
            values[start++] = contractState + "";
            hql += " and c." + SalesContractModel.CONTRACTSTATE + "=?";
        }
        if (contractStateNotEquals != null) {
            values[start++] = contractStateNotEquals + "";
            hql += " and c." + SalesContractModel.CONTRACTSTATE + "!=?";
        }
        if (contractCode != null) {
            values[start++] = contractCode + "";
            hql += " and c." + SalesContractModel.CONTRACTCODE + " like ?";
        }
        if (contractType != null) {
            values[start++] = contractType + "";
            hql += " and c." + SalesContractModel.CONTRACTTYPE + " =?";
        }
        if (contractAmountString != null) {
            float contractAmount = Float.parseFloat(contractAmountString + "");
            values[start++] = contractAmount;
            hql += " and c." + SalesContractModel.CONTRACTAMOUNT + ">=?";
        }
        if (contractAmountStringb != null) {
            float contractAmount = Float.parseFloat(contractAmountStringb + "");
            values[start++] = contractAmount;
            hql += " and c." + SalesContractModel.CONTRACTAMOUNT + "<=?";
        }
        if (startTime != null) {
            values[start++] = startTime + "";
            hql += " and c.createtime >=?";
        }
        if (endTime != null) {
            values[start++] = endTime + "";
            hql += " and c.createtime <=?";
        }
        if (customerInfo != null) {
            values[start++] = customerInfo + "";
            hql += " and c." + SalesContractModel.CUSTOMERID + " =?";
        }

        //排除合并的合同
        hql += " and c.ContractState != 'HB'";

        hql += " group by c.id,c.id,c.contractName,c.ContractCode,c.ContractAmount,c.ContractState,cs.OrderStatus,cs.ReciveStatus,cs.CachetStatus,cs.InvoiceStatus,cs.changeStatus,c.isChanged,art.NAME_,art.CREATE_TIME_,art.ASSIGNEE_ ,c.ContractType,c.isold ";
        int totals = getTotalCount(hql, values);
        hql += " order By c.id DESC";

        Query query = createSQLQuery(hql, values);
        List<Object> items = query.setFirstResult(startIndex).setMaxResults(pageSize).list();
        PaginationSupport ps = new PaginationSupport(items, totals, pageSize, startIndex);

        //        PaginationSupport ps = findPageByQueryWithParam(hql, values, pageSize, startIndex);
        List<SalesContractInfoAndStatus> list = new ArrayList<SalesContractInfoAndStatus>();
        List<Object> rows = (List<Object>) ps.getItems();

        for (int i = 0; i < rows.size(); i++) {
            SalesContractInfoAndStatus salesContractInfoAndStatus = new SalesContractInfoAndStatus();
            Object[] entity = (Object[]) rows.get(i);
            salesContractInfoAndStatus.setSalesContractId(entity[0] == null ? null : Long.parseLong(entity[0].toString()));//合同ID
            salesContractInfoAndStatus.setContractName(entity[1] == null ? "" : entity[1].toString());
            salesContractInfoAndStatus.setContractCode(entity[2] == null ? "" : entity[2].toString());
            salesContractInfoAndStatus.setContractAmount(entity[3] == null ? null : entity[3].toString());
            String entity4 = entity[4] == null ? "" : entity[4].toString();
            if (entity4.equals(SalesContractConstant.CONTRACT_STATE_CG)) {
                entity4 = "草稿";
            } else if (entity4.equals(SalesContractConstant.CONTRACT_STATE_SH)) {
                entity4 = "审核中";
            } else if (entity4.equals(SalesContractConstant.CONTRACT_STATE_TGSH)) {
                entity4 = "审核通过";
            } else if (entity4.equals(SalesContractConstant.CONTRACT_STATE_FQ)) {
                entity4 = "废弃";
            } else if (entity4.equals(SalesContractConstant.CONTRACT_STATE_CXTJ)) {
                entity4 = "重新提交";
            } else if (entity4.equals(SalesContractConstant.CONTRACT_STATE_DGB)) {
                entity4 = "待关闭";
            } else if (entity4.equals(SalesContractConstant.CONTRACT_STATE_CBYG)) {
                entity4 = "成本预估";
            }
            salesContractInfoAndStatus.setContractState(entity4);
            salesContractInfoAndStatus.setOrderStatus(entity[5] == null ? "" : "订单状态：" + entity[5]);
            salesContractInfoAndStatus.setReciveStatus(entity[6] == null ? "" : "收款状态：" + entity[6]);
            salesContractInfoAndStatus.setCachetStatus(entity[7] == null ? "" : "盖章状态：" + entity[7]);
            salesContractInfoAndStatus.setInvoiceStatus(entity[8] == null ? "" : "发票状态：" + entity[8]);
            salesContractInfoAndStatus.setChangeStatus(entity[9] == null ? "" : entity[9].toString());
            salesContractInfoAndStatus.setIsChanged(entity[10] == null ? null : Integer.parseInt(entity[10].toString()));
            if (entity[11] == null && entity4.equals("审核通过")) {
                salesContractInfoAndStatus.setCurrentNode("合同审批完成");
            } else {
                salesContractInfoAndStatus.setCurrentNode(entity[11] == null ? "" : entity[11].toString());
            }
            //salesContractInfoAndStatus.setCurrentNode(entity[11] == null ? "" : entity[11].toString());
            salesContractInfoAndStatus.setTaskReachTime(entity[12] == null ? null : (Date) entity[12]);

            if (entity[13] != null) {
                String staffName = ((SysStaff) systemUserCache.get(entity[13].toString()).getObjectValue()).getStaffName();
                salesContractInfoAndStatus.setDealUserName(staffName);
            } else {
                salesContractInfoAndStatus.setDealUserName("");
            }
            salesContractInfoAndStatus.setContractType(entity[14] == null ? 0 : (Integer.parseInt(entity[14].toString())));
            salesContractInfoAndStatus.setQu(entity[15] == null ? "" : (entity[15].toString()));
            salesContractInfoAndStatus.setSur(entity[16] == null ? "" : (entity[16].toString()));
            salesContractInfoAndStatus.setIsold(entity[17] == null ? "" : (entity[17].toString()));
            list.add(salesContractInfoAndStatus);
        }
        ps.setItems(list);
        return ps;
    }

    /**
     *
     * 合同查询方法
     * @param seachMap
     * @param startIndex
     * @param pageSize
     * @return
     */
    @SuppressWarnings("unchecked")
    public PaginationSupport findPageByMutiSearchMap(HashMap<String, Object> searchMap, int startIndex, Integer pageSize) {
        int start = 0;
        Object[] values = new Object[searchMap.size()];
        Object creatorString = searchMap.get(SalesContractModel.CREATOR);
        Object contractName = searchMap.get(SalesContractModel.CONTRACTNAME);
        Object contractShortname = searchMap.get(SalesContractModel.CONTRACTSHORTNAME);
        Object contractState = searchMap.get(SalesContractModel.CONTRACTSTATE);
        Object contractCode = searchMap.get(SalesContractModel.CONTRACTCODE);
        Object contractAmountString = searchMap.get(SalesContractModel.CONTRACTAMOUNT);

        Object contractStateNotEquals = searchMap.get("contractStateNotEquals");
        Object contractType = searchMap.get(SalesContractModel.CONTRACTTYPE);
        Object startTime = searchMap.get(SalesContractModel.CLOSETIME);
        Object endTime = searchMap.get("endTime");
        Object customerInfo = searchMap.get(SalesContractModel.CUSTOMERID);
        Object orgId = searchMap.get("orgId");

        Object customerIndustry = searchMap.get("cusIndustryId");
        Object customerIdtCustomer = searchMap.get("cusCustomerId");

        String hql = "select c.id,c.contractName,c.ContractCode,c.ContractAmount,c.ContractState,cs.OrderStatus,cs.ReciveStatus,cs.CachetStatus,cs.InvoiceStatus,cs.changeStatus,c.isChanged,art.NAME_,art.CREATE_TIME_,art.ASSIGNEE_ ,c.ContractType,c.CreatorName,c.CreateTime,cust.ShortName,c.isold,SUM(p.Quantity) qu,SUM(p.SurplusNum) sur from Sales_Contract c left join Sales_Contract_Status cs on c.id=cs.SaleContractId  LEFT JOIN bpm_process_inst bpi ON c.ProcessInstanceId=bpi.ID_ LEFT JOIN  sales_contract_product p ON c.id = p.SaleContractId  LEFT JOIN act_ru_task art ON bpi.HI_PROC_INST_ID= art.proc_inst_id_  LEFT JOIN customer_info cust ON c.CustomerId=cust.id where 0=0 and cs.contractSnapShootId IS NULL and c.contractState != 'FQ'";
        if (creatorString != null) {
            Long creator = Long.parseLong(creatorString + "");
            values[start++] = creator;
            hql += " and c." + SalesContractModel.CREATOR + "=?";
        }
        if (customerIndustry != null) {
            values[start++] = customerIndustry + "";
            hql += " and c.cusIndustryId = ?";
        }
        if (customerIdtCustomer != null) {
            values[start++] = customerIdtCustomer + "";
            hql += " and c.cusCustomerId = ?";
        }

        if (contractName != null) {
            values[start++] = contractName + "";
            hql += " and c." + SalesContractModel.CONTRACTNAME + " like ?";
        }
        if (contractShortname != null) {
            values[start++] = contractShortname + "";
            hql += " and c." + SalesContractModel.CONTRACTSHORTNAME + " like ?";
        }
        if (contractState != null) {
            values[start++] = contractState + "";
            hql += " and c." + SalesContractModel.CONTRACTSTATE + "=?";
        } else {
            //排除合并的合同
            hql += " and c.ContractState != 'HB'";
        }

        if (contractStateNotEquals != null) {
            values[start++] = contractStateNotEquals + "";
            hql += " and c." + SalesContractModel.CONTRACTSTATE + "!=?";
        }
        if (contractCode != null) {
            values[start++] = contractCode + "";
            hql += " and c." + SalesContractModel.CONTRACTCODE + " like ?";
        }
        if (contractType != null) {
            values[start++] = contractType + "";
            hql += " and c." + SalesContractModel.CONTRACTTYPE + " =?";
        }
        if (contractAmountString != null) {
            // float contractAmount = Float.parseFloat(contractAmountString + "");
            //            values[start++] = contractAmountString + ".%";
            //            hql += " and c." + SalesContractModel.CONTRACTAMOUNT + " like ? ";

            if (contractAmountString.toString().indexOf(".") != -1) {
                hql += " AND c." + SalesContractModel.CONTRACTAMOUNT + " = ?";
                values[start++] = contractAmountString;
            } else {
                values[start++] = contractAmountString + ".%";
                hql += " AND c." + SalesContractModel.CONTRACTAMOUNT + " like ?";
            }

        }

        if (startTime != null) {
            values[start++] = startTime + "";
            hql += " and c.salesStartDate >=?";
        }
        if (endTime != null) {
            values[start++] = endTime + "";
            hql += " and c.salesStartDate <=?";
        }
        if (customerInfo != null) {
            values[start++] = customerInfo + "";
            hql += " and c." + SalesContractModel.CUSTOMERID + " =?";
        }
        if (orgId != null) {
            String sql = "SELECT orgcode FROM sys_orgnization b WHERE b.OrgId='" + orgId + "'";
            Query orgquery = createSQLQuery(sql);
            String orgcode = (String) orgquery.uniqueResult();
            values[start++] = orgcode + "%";
            hql += " AND c.Creator  IN( SELECT s.staffid FROM sys_staff s  LEFT JOIN sys_stafforg o ON o.staffId = s.staffId LEFT JOIN sys_orgnization org ON o.orgId = org.orgId WHERE s.State!='0' AND org.orgCode  LIKE ? ) ";
        }

        hql += " GROUP BY c.id,c.contractName,c.ContractCode,c.ContractAmount,c.ContractState,cs.OrderStatus,cs.ReciveStatus,cs.CachetStatus,cs.InvoiceStatus,cs.changeStatus,c.isChanged,art.NAME_,art.CREATE_TIME_,art.ASSIGNEE_ ,c.ContractType,c.isold ";
        int totals = getTotalCount(hql, values);

        hql += " order By c.id DESC";

        Query query = createSQLQuery(hql, values);
        List<Object> items = query.setFirstResult(startIndex).setMaxResults(pageSize).list();
        PaginationSupport ps = new PaginationSupport(items, totals, pageSize, startIndex);

        return ps;
    }

    /**
     * <code>getTotalCount</code>
     * 得到总条数
     * @param sql
     * @param params
     * @return
     * @since   2014年11月24日    guokemenng
     */
    @SuppressWarnings("rawtypes")
    public Integer getTotalCount(String sql, Object[] params) {
        String countQueryString = " select COUNT(*) from (" + sql + ") t";
        List countlist = createSQLQuery(countQueryString, params).list();
        return Integer.parseInt(countlist.get(0).toString());
    }

    /**
     * @return
     */
    public Map<String, Object> getContractName(long id) {
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        Object[] param = new Object[1];
        param[0] = id;
        String sql = "select Id,ContractName from sales_contract where id=?";

        Query query = createSQLQuery(sql, param);
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List<Map<String, Object>> objList = query.list();
        for (Map<String, Object> objMap : objList) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", objMap.get("Id"));
            map.put("name", objMap.get("ContractName"));
            mapList.add(map);
        }
        return mapList.get(0);
    }

    /**
     * 更新商务经理添加订单处理人
     * @param salesContractModel
     */
    public void updateContractState(SalesContractModel salesContractModel) {
        Object[] param = new Object[2];
        String sql = "update SalesContractModel set contractState=? where id=?";
        param[0] = SalesContractConstant.CONTRACT_STATE_TGSH;
        param[1] = salesContractModel.getId();
        executeSql(sql, param);
    }

    /**
     * 更新合同的状态为审批通过
     * @param salesContractModel
     */
    public void updateOrderProcessor(SalesContractModel salesContractModel) {
        SalesContractModel s = this.get(salesContractModel.getId());
        s.setOrderProcessor(salesContractModel.getOrderProcessor());
        s.setBusiEstimateProfit(salesContractModel.getBusiEstimateProfit());
        s.setBusiEstimateProfitDesc(salesContractModel.getBusiEstimateProfitDesc());
        this.update(s);
        //        Object[] param = new Object[4];
        //        String sql = "update SalesContractModel set orderProcessor=?,set busiEstimateProfit=?,busiEstimateProfitDesc=?  where id=?";
        //        param[0] = salesContractModel.getOrderProcessor();
        //        param[1] = salesContractModel.getBusiEstimateProfit();
        //        param[2] = salesContractModel.getBusiEstimateProfitDesc();
        //        param[3] = salesContractModel.getId();
        //        executeSql(sql, param);
    }

    /**
     * 删除合同收款数据
     * @param salecontractId
     */
    public void deleteReceivePlan(String salecontractId) {
        String sql = "delete from sales_contract_receive_plan where salescontractid='" + salecontractId + "'";
        this.createSQLQuery(sql).executeUpdate();
    }

    /**
     * 删除合同收款数据
     * @param salecontractId
     */
    public void deleteSalesProducts(String salecontractId) {
        String sql = "delete from sales_contract_product where SaleContractId='" + salecontractId + "'";
        this.createSQLQuery(sql).executeUpdate();
    }

    /**
     * @param searchMap
     * @return
     */
    public String findTotallAmount(HashMap<String, Object> searchMap) {
        int start = 0;
        Object[] values = new Object[searchMap.size()];
        Object creatorString = searchMap.get(SalesContractModel.CREATOR);
        Object contractName = searchMap.get(SalesContractModel.CONTRACTNAME);
        Object contractShortname = searchMap.get(SalesContractModel.CONTRACTSHORTNAME);
        Object contractState = searchMap.get(SalesContractModel.CONTRACTSTATE);
        Object contractCode = searchMap.get(SalesContractModel.CONTRACTCODE);
        Object contractAmountString = searchMap.get(SalesContractModel.CONTRACTAMOUNT);

        Object contractAmountStringb = searchMap.get("contractAmountb");
        Object contractStateNotEquals = searchMap.get("contractStateNotEquals");
        Object contractType = searchMap.get(SalesContractModel.CONTRACTTYPE);
        Object startTime = searchMap.get(SalesContractModel.CLOSETIME);
        Object endTime = searchMap.get("endTime");
        Object customerInfo = searchMap.get(SalesContractModel.CUSTOMERID);
        Object orgId = searchMap.get("orgId");
        Object customerIndustry = searchMap.get("cusIndustryId");
        Object customerIdtCustomer = searchMap.get("cusCustomerId");
        String hql = "SELECT SUM(c.ContractAmount) amount FROM Sales_Contract c LEFT JOIN Sales_Contract_Status cs ON c.id=cs.SaleContractId  LEFT JOIN bpm_process_inst bpi ON c.ProcessInstanceId=bpi.ID_  LEFT JOIN act_ru_task art ON bpi.HI_PROC_INST_ID= art.proc_inst_id_  LEFT JOIN customer_info cust ON c.CustomerId=cust.id WHERE 0=0 AND cs.contractSnapShootId IS NULL and c.contractState != 'FQ' ";
        if (creatorString != null) {
            Long creator = Long.parseLong(creatorString + "");
            values[start++] = creator;
            hql += " and c." + SalesContractModel.CREATOR + "=?";
        }
        if (orgId != null) {
            if (orgId != null) {
                String sql = "SELECT orgcode FROM sys_orgnization b WHERE b.OrgId='" + orgId + "'";
                Query orgquery = createSQLQuery(sql);
                String orgcode = (String) orgquery.uniqueResult();
                values[start++] = orgcode + "%";
                hql += " AND c.Creator  IN( SELECT s.staffid FROM sys_staff s  LEFT JOIN sys_stafforg o ON o.staffId = s.staffId LEFT JOIN sys_orgnization org ON o.orgId = org.orgId WHERE s.State!='0' AND org.orgCode  LIKE ? ) ";
            }
        }
        if (customerIndustry != null) {
            values[start++] = customerIndustry + "";
            hql += " and c.cusIndustryId = ?";
        }
        if (customerIdtCustomer != null) {
            values[start++] = customerIdtCustomer + "";
            hql += " and c.cusCustomerId = ?";
        }
        if (contractName != null) {
            values[start++] = contractName + "";
            hql += " and c." + SalesContractModel.CONTRACTNAME + " like ?";
        }
        if (contractShortname != null) {
            values[start++] = contractShortname + "";
            hql += " and c." + SalesContractModel.CONTRACTSHORTNAME + " like ?";
        }
        if (contractState != null) {
            values[start++] = contractState + "";
            hql += " and c." + SalesContractModel.CONTRACTSTATE + " =?";
        }
        if (contractStateNotEquals != null) {
            values[start++] = contractStateNotEquals + "";
            hql += " and c." + SalesContractModel.CONTRACTSTATE + "!=?";
        }
        if (contractCode != null) {
            values[start++] = contractCode + "";
            hql += " and c." + SalesContractModel.CONTRACTCODE + " like ?";
        }
        if (contractType != null) {
            values[start++] = contractType + "";
            hql += " and c." + SalesContractModel.CONTRACTTYPE + " =?";
        }
        if (contractAmountString != null) {
            // float contractAmount = Float.parseFloat(contractAmountString + "");
            //            values[start++] = contractAmountString + ".%";
            //            hql += " and c." + SalesContractModel.CONTRACTAMOUNT + " like ? ";

            if (contractAmountString.toString().indexOf(".") != -1) {
                values[start++] = contractAmountString;
                hql += " AND c." + SalesContractModel.CONTRACTAMOUNT + " = ?";
            } else {
                values[start++] = contractAmountString + ".%";
                hql += " AND c." + SalesContractModel.CONTRACTAMOUNT + " like ?";
            }

        }
        if (contractAmountStringb != null) {
            float contractAmount = Float.parseFloat(contractAmountStringb + "");
            values[start++] = contractAmount;
            hql += " and c." + SalesContractModel.CONTRACTAMOUNT + "<=?";
        }
        if (startTime != null) {
            values[start++] = startTime + "";
            hql += " and c.SalesStartDate >=?";
        }
        if (endTime != null) {
            values[start++] = endTime + "";
            hql += " and c.SalesStartDate <=?";
        }
        if (customerInfo != null) {
            values[start++] = customerInfo + "";
            hql += " and c." + SalesContractModel.CUSTOMERID + " =?";
        }
        hql += " ORDER BY c.id DESC";

        Query query = this.createSQLQuery(hql, values);
        BigDecimal amount = (BigDecimal) query.uniqueResult();
        /* List<SalesContractModel> objList = query.list();
         Iterator<SalesContractModel> bus = objList.iterator();
         BigDecimal amount = new BigDecimal(0.00);
         while (bus.hasNext()) {
             SalesContractModel b = bus.next();
             if (b.getContractAmount() != null) {
                 amount = amount.add(b.getContractAmount());
             }
         }*/
        String totall = "0";
        if (amount != null) {
            totall = amount.toString();
        }
        return totall;

    }

    /**
     * @param userId
     * @return
     */
    public List<Map<String, Object>> findOrg(String userId) {
        Object[] param = new Object[1];
        param[0] = userId;
        String sql = "SELECT sp.positionName,sp.orgId FROM sys_user u, sys_staffpos st, sys_position sp WHERE u.staffId=st.staffId AND u.userId=? AND st.positionId=sp.positionId";

        Query query = createSQLQuery(sql, param);
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List<Map<String, Object>> objList = query.list();
        return objList;
    }

    /**
     * @param userId
     * @return
     */
    public int findroles(String userId) {
        Object[] param = new Object[1];
        param[0] = userId;
        String sql = "SELECT r.* FROM Sys_Role r,sys_userrole u WHERE u.userId=? AND u.roleId=r.roleId AND r.roleName='销售二部助理'";
        Query query = createSQLQuery(sql, param);
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List<Map<String, Object>> objList = query.list();
        int flat = 0;
        if (objList.size() > 0) {
            flat = 1;
        }
        return flat;
    }

    /**
     * @param id
     * @return
     */
    public List<Map<String, Object>> findproduct(Long id) {
        Object[] param = new Object[1];
        param[0] = id;
        String sql = "SELECT cp.`ProductTypeName` typeName,cp.ProductPartnerName partnerName, cp.`ProductName` pName,  bp.`Quantity` orderQua,bp.unitprice unitprice,";
        sql += "bp.`SubTotal` subtotal,  bp.`Quantity` ordernum ,bp.`OrderId` FROM `sales_contract_product` cp, `business_order_product` bp WHERE ";
        sql += "cp.`SaleContractId`='" + id + "' AND bp.`salesContractProductId`=cp.`id` UNION SELECT cp.`ProductTypeName` typeName,cp.ProductPartnerName partnerName, cp.`ProductName` pName,  bp.`Quantity` orderQua,bp.unitprice unitprice, ";
        sql += "bp.`SubTotal` subtotal,  bp.`Quantity` ordernum ,bp.`OrderId` FROM (SELECT * FROM sales_contract_product p WHERE p.`SaleContractId`='" + id + "' AND p.`RelateDeliveryProductId` IS NOT NULL";
        sql += ")cp, `business_order_product` bp  WHERE bp.`salesContractProductId`=cp.`RelateDeliveryProductId` ";
        Query query = this.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List<Map<String, Object>> obList = query.list();
        return obList;
    }

    /**
     * @param id
     * @return
     */
    public int findsaleProduct(Long id) {
        Object[] param = new Object[1];
        param[0] = id;
        String sql = "SELECT * FROM sales_contract_product p WHERE p.`SaleContractId`='" + id + "' AND p.`RelateDeliveryProductId` IS NOT NULL";
        Query query = this.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List<Map<String, Object>> obList = query.list();
        if (obList.size() > 0) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * @param id
     */
    public void updateContractOrder(Long id) {
        Object[] param = new Object[2];
        String sql = "update SalesContractStatusModel set OrderStatus=? where SalecontractId=?";
        param[0] = SalesContractConstant.CONTRACT_ORDER_DEVICEAPPLY;
        param[1] = id;
        executeSql(sql, param);
    }

    /**
     * @param name
     * @param processInstanceId
     */
    public void updateWorkName(String name, Long processInstanceId) {
        String sql = "update bpm_process_inst b set b.`TITLE_`='" + name + "' WHERE b.`ID_`='" + processInstanceId + "' ";
        this.createSQLQuery(sql).executeUpdate();
    }

    /**
     * @param salesId
     * @return
     */
    public List<SalesContractModel> getSalesIds(String[] salesId) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT s.* FROM sales_contract s WHERE s.id IN (");
        int size = salesId.length;
        for (int i = 0; i < size - 1; i++) {
            sb.append(" '" + salesId[i] + "', ");
        }
        sb.append("'" + salesId[size - 1] + "')");
        List<SalesContractModel> sales = createSQLQuery(sb.toString()).addEntity(SalesContractModel.class).list();
        return sales;
    }

    /**
     * 根据id查找合同
     * @param saleContractId
     * @return
     */
    public SalesContractModel getSaleContractById(String saleContractId) {
        String sql = "SELECT s.* FROM sales_contract s WHERE s.id='" + saleContractId + "'";
        List<SalesContractModel> list = createSQLQuery(sql).addEntity(SalesContractModel.class).list();
        SalesContractModel salesContractModel = new SalesContractModel();
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }

    }

    /**
     * @param procInstId
     * @return
     */
    public SalesContractModel findContractModel(long procInstId) {
        String sql = "SELECT s.* FROM sales_contract s WHERE s.`ProcessInstanceId`='" + procInstId + "'";
        List<SalesContractModel> list = createSQLQuery(sql).addEntity(SalesContractModel.class).list();
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }

    }

    /**
     * 根据id查询公司信息
     * @param id
     * @return
     */
    public List<Map<String, Object>> findCompanyInfoById(long id) {
        String sql = "select address,bankaccount,bankname,companyname,companyCode from business_accountcompany where id=?";
        Query query = this.createSQLQuery(sql);

        //需要设置一个转换器，这样才可以有像map那样的结构
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List<Map<String, Object>> list = query.setParameter(0, id).list();
        return list;
    }
    
    /**
     * @param id
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<SalesInvoicePlanModel> getSalesInvoicePlan(Long id) {
        String sql = "SELECT s.* FROM sales_invoice_plan s WHERE s.`SalesContractId`='" + id + "'";
        List<SalesInvoicePlanModel> list = createSQLQuery(sql).addEntity(SalesInvoicePlanModel.class).list();
        return list;
    }

}
