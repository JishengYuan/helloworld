/*
 * FileName: StockUpContractCostDao.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.sales.stockupCost.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.sinobridge.base.core.dao.imp.DefaultBaseDao;
import com.sinobridge.base.core.utils.PaginationSupport;
import com.sinobridge.eoss.sales.contract.constant.SalesContractConstant;
import com.sinobridge.eoss.sales.contract.dto.SalesContractInfoAndStatus;
import com.sinobridge.eoss.sales.contract.model.SalesContractModel;
import com.sinobridge.eoss.sales.stockupCost.model.StockUpContractCostModel;

/**
 * <p>
 * Description: StockUpContractCostDao
 * </p>
 *
 * @author liyx
 * @version 1.0

 * <p>
 * History: 
 *
 * Date                     Author         Version     Description
 * ---------------------------------------------------------------------------------
 * 2015年10月9日 下午5:25:50      liyx           1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Repository
public class StockUpContractCostDao extends DefaultBaseDao<StockUpContractCostModel, Long> {
    //private final static Ehcache systemUserCache = Constants.CACHE_MANAGER.getEhcache("systemUser");

    /**
     * 条件查询
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
        Object orderBy = searchMap.get("orderBy");
        //String hql = "select c.id,c.contractName,c.ContractCode,c.ContractAmount,c.ContractState,cs.OrderStatus,cs.ReciveStatus,cs.CachetStatus,cs.InvoiceStatus,cs.changeStatus,c.isChanged,art.NAME_,art.CREATE_TIME_,art.ASSIGNEE_ ,c.ContractType,sum(p.Quantity) qu,sum(p.SurplusNum) sur,c.isold isold from Sales_Contract c left join Sales_Contract_Status cs on c.id=cs.SaleContractId left join  sales_contract_product p on c.id = p.SaleContractId LEFT JOIN bpm_process_inst bpi ON c.ProcessInstanceId=bpi.ID_  LEFT JOIN act_ru_task art ON bpi.HI_PROC_INST_ID= art.proc_inst_id_ where 0=0 and cs.contractSnapShootId IS NULL and c.contractState != 'FQ'";
        //String hql = "select sc.id,sc.contractName,sc.ContractCode,sc.ContractAmount,sc.ContractState,cs.OrderStatus,cs.ReciveStatus,cs.CachetStatus,cs.InvoiceStatus,cs.changeStatus,sc.isChanged,sc.ContractType from sales_contract sc,sales_contract_product cp,Sales_Contract_Status cs where sc.id=cp.SaleContractId and sc.id=cs.SaleContractId and cp.relateDeliveryProductId is not null  ";
        String hql = "select sc.id,sc.contractName,sc.ContractCode,sc.ContractAmount,sc.ContractState,cs.OrderStatus,cs.ReciveStatus,cs.CachetStatus,cs.InvoiceStatus,cs.changeStatus,sc.isChanged,sc.ContractType,bo.id orderId,bo.CreatorId,bp.SaleContractId bhSaleContractId ";
        hql += "from sales_contract sc,sales_contract_product cp,Sales_Contract_Status cs,business_order bo,business_order_product bp ";
        hql += "where sc.id=cp.SaleContractId and sc.id=cs.SaleContractId and cp.RelateDeliveryProductId=bp.salesContractProductId ";
        hql += "and bo.id=bp.OrderId  and cp.relateDeliveryProductId is not null and bo.OrderStatus='TGSP' ";

        if (orderBy != null) {
            values[start++] = orderBy.toString();
            //hql += "and bo.CreatorId='" + orderBy.toString() + "' ";
            hql += "and bo.CreatorId=? ";
        }

        if (creatorString != null) {
            Long creator = Long.parseLong(creatorString + "");
            values[start++] = creator;
            hql += " and c." + SalesContractModel.CREATOR + "=?";
        }
        hql += " GROUP BY bp.SaleContractId ORDER BY cp.SaleContractId ";

        int totals = getTotalCount(hql, values);

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
            salesContractInfoAndStatus.setContractType(entity[11] == null ? 0 : (Integer.parseInt(entity[11].toString())));
            salesContractInfoAndStatus.setOrderId(entity[12] == null ? "" : (entity[12].toString()));
            salesContractInfoAndStatus.setOrderProcessor(entity[13] == null ? "" : (entity[13].toString()));
            salesContractInfoAndStatus.setBhSaleContractId(entity[14] == null ? "" : (entity[14].toString()));
            list.add(salesContractInfoAndStatus);
        }
        ps.setItems(list);
        return ps;
    }

    /**
     * <code>getTotalCount</code>
     * 得到总条数
     * @param sql
     * @param params
     * @return
     * @since   2015年10月12日    liyx
     */
    @SuppressWarnings("rawtypes")
    public Integer getTotalCount(String sql, Object[] params) {
        String countQueryString = " select COUNT(*) from (" + sql + ") t";
        List countlist = createSQLQuery(countQueryString, params).list();
        return Integer.parseInt(countlist.get(0).toString());
    }

    /**
     * 根据备货合同设备ID得到备货产品
     * @param ids
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> getProductByIds(String ids) {
        List<Map<String, Object>> rs = null;
        //String sql = "select * from sales_contract_product where id in (" + ids + ")";
        String sql = "select cp.id,cp.quantity,cp.unitPrice,cp.totalPrice,cp.productName,cp.productPartnerName,cp.productTypeName,cp.serviceStartDate,cp.serviceEndDate ";
        sql += "from business_order_product op,sales_contract_product cp ";
        sql += "where op.salesContractProductId=cp.RelateDeliveryProductId and op.salesContractProductId in(" + ids + ") ";
        sql += "GROUP BY op.salesContractProductId";

        Query query = createSQLQuery(sql);
        rs = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();

        return rs;
    }

    /**
     * 根据产品合同得到关联的备货合同
     * @param id
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> getProSalesCode(Long id) {
        String sql = "select id,contractCode from sales_contract where id in(select SaleContractId from sales_contract_product where id in";
        sql += "(select sp.RelateDeliveryProductId from sales_contract sc,sales_contract_product sp ";
        sql += "where sc.id=sp.saleContractid  and  sc.id=" + id + " and sp.RelateDeliveryProductId is not null))";

        List<Map<String, Object>> obList = createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        return obList;
    }

}
