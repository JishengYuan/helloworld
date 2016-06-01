/*
 * FileName: SalesFundsoptLogService.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.sales.contractfounds.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinobridge.base.core.service.DefaultBaseService;
import com.sinobridge.eoss.sales.contractfounds.dao.SalesFundsoptLogDao;
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
 * 2015年12月17日 下午1:52:33          tigq        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Service
@Transactional
public class SalesFundsoptLogService extends DefaultBaseService<SalesFundsoptlogModel, SalesFundsoptLogDao> {

    /**
     * 保存操作日志
     * @param applyFundsId
     * @param optDesc
     */
    public void saveOptLog(Long applyFundsId, String optDesc) {
        SalesFundsoptlogModel salesFundsoptlogModel = new SalesFundsoptlogModel();
        salesFundsoptlogModel.setApplyFundsId(applyFundsId);
        salesFundsoptlogModel.setOptDesc(optDesc);
        salesFundsoptlogModel.setOptDate(new Date());
        this.saveOrUpdate(salesFundsoptlogModel);
    }

    /**查询日志
     * @param applyFundsId
     * @return
     */
    public List<SalesFundsoptlogModel> findFundsoptlog(Long applyFundsId) {
        List<SalesFundsoptlogModel> log = getDao().findFundsoptlog(applyFundsId);
        return log;
    }

}
