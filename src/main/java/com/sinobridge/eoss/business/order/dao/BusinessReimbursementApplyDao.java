/*
 * FileName: BusinessReimbursementApplyDao.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.business.order.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.sinobridge.base.core.dao.imp.DefaultBaseDao;
import com.sinobridge.base.core.utils.PaginationSupport;
import com.sinobridge.eoss.business.order.model.BusinessReimbursementApply;
import com.sinobridge.eoss.sales.contract.model.SalesContractModel;

/**
 * <p>
 * Description: 
 * </p>
 *
 * @author tigq
 * @version 1.0

 * <p>
 * History: 
 *
 * Date                     Author         Version     Description
 * ---------------------------------------------------------------------------------
 * 2014年12月16日 下午3:18:39          tigq        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@SuppressWarnings("unchecked")
@Repository
public class BusinessReimbursementApplyDao extends DefaultBaseDao<BusinessReimbursementApply, Long> {

    /**
     * 查询订单的计划金额
     * @param id
     * @return
     */
    @SuppressWarnings("unchecked")
    public BigDecimal findReimAmount(Long id) {
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        BigDecimal amount = new BigDecimal(0.00);
        String[] param = new String[1];
        param[0] = id.toString();
        String sql = "select sum(Amount) amount from business_reimbursement where OrderId=?";
        Query query = this.createSQLQuery(sql, param);
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        mapList = query.list();
        Map<String, Object> map = mapList.get(0);
        Object am = map.get("amount");
        if (am != null) {
            amount = BigDecimal.valueOf(Double.parseDouble(am.toString()));
        }
        return amount;
    }

    public void updateOrderReim(Long orderId, String flat) {
        Object[] param = new Object[2];
        param[0] = flat;
        param[1] = orderId;
        String sql = "update BusinessOrderModel set reimStatus=? where id=?";
        this.executeSql(sql, param);
    }

    /**
     * 更新订单的发票金额
     * @param id
     * @param amount
     * @param planStatus
     */
    public void updateOrderReimAmount(Long id, BigDecimal amount, String planStatus) {
        Object[] param = new Object[3];
        String sql = "update BusinessOrderModel set reimStatus=?,reimAmount=? where id=?";
        param[0] = planStatus;
        param[1] = amount;
        param[2] = id;
        this.executeSql(sql, param);
    }

    /**发票申请：审批通过
     * @param id
     * @param closeTime
     */
    public void updatePlanStatus(Long id, Date closeTime) {
        Object[] param = new Object[3];
        param[0] = "10";
        param[1] = closeTime;
        param[2] = id;
        String sql = "update BusinessReimbursementApply set reimBursStatus=?,closeTime=? where id=?";
        this.executeSql(sql, param);
    }

    /*  *//**
                         * @param id
                         */
    /*
    public void endreimStatus(Long id) {
     Object[] param = new Object[2];
     String sql = "update BusinessReimbursementApply set reimBursStatus=? where id=?";
     param[0] = BusinessOrderContant.NFF_CG;
     param[1] = id;
     this.executeSql(sql, param);
    }
    */
    /**
     * 通过ID查询报销单
     * @param procInstId
     * @return
     */
    public BusinessReimbursementApply findReimId(String procInstId) {
        BusinessReimbursementApply reim = new BusinessReimbursementApply();
        String[] param = new String[1];
        param[0] = procInstId;
        String sql = "SELECT * FROM  business_reimbursement_apply WHERE ProcessInstanceId=?";
        Query query = this.createSQLQuery(sql, param).addEntity(BusinessReimbursementApply.class);
        reim = (BusinessReimbursementApply) query.list().get(0);
        return reim;
    }

    /**更新订单报销状态
     * @param orderId
     * @param reimStatus
     */
    public void updateOrderStatus(Long orderId, String reimStatus) {
        Object[] param = new Object[2];
        String sql = "update BusinessOrderModel set reimStatus=? where id=?";
        param[0] = reimStatus;
        param[1] = orderId;
        executeSql(sql, param);

    }

    /**报销列表
     * @param searchMap
     * @param startIndex
     * @param pageSize
     * @return
     */
    public PaginationSupport findPayPage(HashMap<String, Object> searchMap, int startIndex, Integer pageSize) {
        int start = 0;
        Object[] values = new Object[searchMap.size()];
        Object userId = searchMap.get("reimbursementUser");
        Object reimbursementName = searchMap.get(BusinessReimbursementApply.REIMNAME);
        Object amount = searchMap.get(BusinessReimbursementApply.AMOUNT);
        Object status = searchMap.get(BusinessReimbursementApply.REIMSTATUS);
        Object supplierShortName = searchMap.get("supplierShortName");
        Object amountPart = searchMap.get("amountPart");

        String hql = "select c.id,c.reimbursementName,c.reimBursStatus,c.amount,c.reimbursementUser,c.createTime,c.closeTime,art.NAME_,art.CREATE_TIME_,art.ASSIGNEE_ ,r.`supplierShortName` from business_reimbursement_apply c LEFT JOIN bpm_process_inst bpi ON c.ProcessInstanceId=bpi.ID_ LEFT JOIN `business_reimbursement` r ON c.`Id`=r.`rbmApplyId` LEFT JOIN act_ru_task art ON bpi.HI_PROC_INST_ID= art.proc_inst_id_ where 0=0 ";
        if (reimbursementName != null) {
            values[start++] = reimbursementName;
            hql += " and c." + BusinessReimbursementApply.REIMNAME + " LIKE ?";
        }
        if (userId != null) {
            values[start++] = userId + "";
            hql += " and c.reimbursementUser=?";
        }
        if (amount != null) {
            BigDecimal amountBig = new BigDecimal(amount.toString());
            values[start++] = amountBig + "";
            hql += " and c." + BusinessReimbursementApply.AMOUNT + "=?";
        }
        if (amountPart != null) {
            BigDecimal amountBig = new BigDecimal(amountPart.toString());
            values[start++] = amountBig + "";
            hql += " and r.`amount`=?";
        }
        if (supplierShortName != null) {
            values[start++] = supplierShortName + "";
            hql += " and r.`supplierInfo`=?";
        }
        if (status != null) {
            values[start++] = status + "";
            hql += " and c." + BusinessReimbursementApply.REIMSTATUS + "=?";
        }
        hql += " group by c.id order By c.id DESC";

        Query query = createSQLQuery(hql, values);
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List<Object> items = query.setFirstResult(startIndex).setMaxResults(pageSize).list();
        PaginationSupport ps = new PaginationSupport(items, getTotalCount(hql, values), pageSize, startIndex);

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
     * 查询组织名称
     * @param reimbursementUser
     * @return
     */
    public String findSatff(String reimbursementUser) {
        Object[] param = new Object[1];
        String sql = "SELECT o.OrgFullName FROM sys_stafforg so LEFT JOIN sys_staff s ON so.StaffId=s.StaffId LEFT JOIN sys_orgnization o ON o.OrgId=so.OrgId WHERE s.StaffName=?";
        param[0] = reimbursementUser;
        Query query = this.createSQLQuery(sql, param);
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List<Map<String, Object>> mapList = query.list();
        Map<String, Object> map = mapList.get(0);
        String org = map.get("OrgFullName").toString();
        return org;
    }

    /**更新报销单标题
     * @param title
     * @param processInstanceId
     */
    public void updateRmbTitle(String title, Long processInstanceId) {
        String sql = "UPDATE bpm_process_inst t SET t.TITLE_='" + title + "' WHERE t.ID_='" + processInstanceId + "' ";
        createSQLQuery(sql).executeUpdate();
    }

    /**查询报销金额
     * @param id
     * @return
     */
    public BigDecimal getReimAmount(String id) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT SUM(r.`amount`) amount  FROM business_order o ,`business_reimbursement` r,`business_reimbursement_apply` a ");
        sb.append("WHERE o.`id`=r.`orderid` AND r.`rbmApplyId`=a.`Id` AND o.`id`='" + id + "' AND a.`reimBursStatus`='10' GROUP BY o.`id` ");
        List<Map<String, Object>> listMap = createSQLQuery(sb.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        BigDecimal amount = new BigDecimal(0.00);
        if (listMap.size() > 0) {
            amount = new BigDecimal(listMap.get(0).get("amount").toString());
        }
        return amount;
    }

    /**查询发票申请相关信息
     * @param orderId
     * @return
     */
    public List<BusinessReimbursementApply> getReimApplys(Long orderId) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT a.* FROM `business_reimbursement` r, `business_reimbursement_apply` a ");
        sb.append("WHERE r.`rbmApplyId`=a.`Id` AND r.`orderid`='" + orderId + "' ");
        List<BusinessReimbursementApply> reimApplys = createSQLQuery(sb.toString()).addEntity(BusinessReimbursementApply.class).list();
        return reimApplys;
    }

    /**
     * @param long1
     * @return
     */
    public List<SalesContractModel> getSalesContract(long salesId) {
        StringBuffer sb = new StringBuffer();
        sb.append("select * from sales_contract s where s.id='" + salesId + "' ");
        List<SalesContractModel> sales = createSQLQuery(sb.toString()).addEntity(SalesContractModel.class).list();
        return sales;
    }

    /**
     * @param id
     * @return
     */
    public List<Map<String, String>> getInterPurse(Long id) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT s.* FROM `business_inter_purchas` s ,`order_interpurchas_map` m,`business_reimbursement` r  ");
        sb.append("WHERE  s.`id`=m.`business_inter_purchas` AND m.`business_order`=r.`orderid` AND r.id ='" + id + "' ");
        List<Map<String, String>> sales = createSQLQuery(sb.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        return sales;
    }

}
