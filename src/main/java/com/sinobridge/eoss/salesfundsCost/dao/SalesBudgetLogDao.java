/*
 * FileName: SalesBudgetLogDao.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.salesfundsCost.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.sinobridge.base.core.dao.imp.DefaultBaseDao;
import com.sinobridge.eoss.salesfundsCost.model.SalesBudgetLog;

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
 * 2016年3月3日 下午4:38:40          vermouth        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Repository
public class SalesBudgetLogDao extends DefaultBaseDao<SalesBudgetLog, Long> {

    /**通过合同ID查询此合同所有预测日志信息
     * @param saleId
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<SalesBudgetLog> getLogsBySalesId(long saleId) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT * FROM `funds_salesbudget_log` s LEFT JOIN `funds_salesbudget` f ON f.`id`=s.`budgetFundsId` WHERE f.`contractId`='" + saleId + "' ");
        List<SalesBudgetLog> logs = createSQLQuery(sb.toString()).addEntity(SalesBudgetLog.class).list();
        return logs;
    }
}
