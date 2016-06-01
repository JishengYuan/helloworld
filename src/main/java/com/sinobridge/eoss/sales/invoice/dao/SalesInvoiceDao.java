/*
 * FileName: SalesContractDao.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.sales.invoice.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.sinobridge.base.core.dao.imp.DefaultBaseDao;
import com.sinobridge.eoss.sales.invoice.model.SalesInvoicePlanModel;

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
 * 2014年5月15日 下午1:24:25          3unshine        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Repository
public class SalesInvoiceDao extends DefaultBaseDao<SalesInvoicePlanModel, String> {

    /**
     * @param creatorUse
     * @return
     */
    public String findUserId(String creatorUse) {
        Object[] param = new Object[1];
        param[0] = creatorUse;
        String sql = "SELECT * FROM `sys_staff` s WHERE s.UserName=?";
        Query query = createSQLQuery(sql, param);
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List<Map<String, Object>> objList = query.list();
        String id = objList.get(0).get("StaffId").toString();

        return id;
    }

    /**
     * @param id
     * @return
     */
    public String findKeyCode(Long id) {
        Object[] param = new Object[1];
        param[0] = id;
        String sql = "SELECT i.`KEY_` FROM `sales_invoice_plan` p , `bpm_process_inst` i WHERE p.`ProcessInstanceId`=i.`ID_` AND p.id=?";
        Query query = createSQLQuery(sql, param);
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List<Map<String, Object>> objList = query.list();
        String key = "";
        if (objList.size() > 0) {
            key = objList.get(0).get("KEY_").toString();
        }
        return key;
    }

    /**删除发票计划
     * @param salesContractModelId
     * 2016-04-06 wangya
     */
    public void delSalesInvoiceByContractId(long salesContractModelId) {
        Long[] param = new Long[1];
        param[0] = salesContractModelId;
        String sql = "delete from sales_invoice_plan where SalesContractId = ?";
        createSQLQuery(sql, param).executeUpdate();
    }

    /**
     * @param procInstId
     * @param idString
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<SalesInvoicePlanModel> getInvoiceByProcInstId(String procInstId, String idString) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT * FROM sales_invoice_plan p WHERE p.`SalesContractId`='" + idString + "' ");
        sb.append("AND (p.`ProcessInstanceId`='" + procInstId + "' OR p.`InvoiceStatus`='CG') ");
        List<SalesInvoicePlanModel> list = createSQLQuery(sb.toString()).addEntity(SalesInvoicePlanModel.class).list();
        return list;
    }

}
