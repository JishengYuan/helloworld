/*
 * FileName: FundsSalesLogService.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.salesfundsCost.service;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinobridge.base.core.service.DefaultBaseService;
import com.sinobridge.eoss.salesfundsCost.dao.FundsSalesLogDao;
import com.sinobridge.eoss.salesfundsCost.model.FundsSalesLog;

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
 * 2015年12月28日 上午11:04:41          tigq        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Service
@Transactional
public class FundsSalesLogService extends DefaultBaseService<FundsSalesLog, FundsSalesLogDao> {

    public List<FundsSalesLog> getSalesLog(String contractCode) {
        List<FundsSalesLog> rs = null;
        String hql = "from FundsSalesLog where contractCode='" + contractCode + "'";
        Query query = getDao().createQuery(hql);
        rs = query.list();
        return rs;
    }

    /**
     * 根据合同编号，操作类型查询最后操作的日志
     * @param contractCode
     * @param opDesc
     * @return
     */
    public FundsSalesLog getSalesIds(String contractCode, String opDesc) {
        StringBuffer sb = new StringBuffer();
        sb.append("select l.* from funds_saleslog l where l.id in(select max(b.id) from funds_saleslog b ");
        sb.append("where b.contractCode='" + contractCode + "' and b.opDesc='" + opDesc + "') ");
        sb.append("and l.contractCode='" + contractCode + "' and l.opDesc='" + opDesc + "'");

        List<FundsSalesLog> sales = getDao().createSQLQuery(sb.toString()).addEntity(FundsSalesLog.class).list();
        if (sales.size() > 0) {
            return sales.get(0);
        }
        return null;
    }
}
