/*
 * FileName: SalesFundsoptLogDao.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.sales.contractfounds.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.sinobridge.base.core.dao.imp.DefaultBaseDao;
import com.sinobridge.eoss.sales.contractfounds.model.SalesFundsoptlogModel;

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
 * 2015年12月17日 下午1:51:24          tigq        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Repository
public class SalesFundsoptLogDao extends DefaultBaseDao<SalesFundsoptlogModel, Long> {

    /**
     * @param applyFundsId
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<SalesFundsoptlogModel> findFundsoptlog(Long applyFundsId) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT * FROM `sales_fundsoptlog` s WHERE s.`applyFundsId`='" + applyFundsId + "'");
        List<SalesFundsoptlogModel> log = this.createSQLQuery(sb.toString()).addEntity(SalesFundsoptlogModel.class).list();
        return log;
    }

}
