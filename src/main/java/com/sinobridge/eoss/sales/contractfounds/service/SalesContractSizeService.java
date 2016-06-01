/*
 * FileName: SalesContractSizeService.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.sales.contractfounds.service;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinobridge.base.core.service.DefaultBaseService;
import com.sinobridge.eoss.sales.contractfounds.dao.SalesContractSizeDao;
import com.sinobridge.eoss.sales.contractfounds.model.SalesContractSizeModel;

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
 * 2015年12月7日 下午4:49:39          vermouth        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Service
@Transactional
public class SalesContractSizeService extends DefaultBaseService<SalesContractSizeModel, SalesContractSizeDao> {

    @Autowired
    private SalesFundsoptLogService salesFundsoptLogService;

    /**
     * 更新往来款信息
     * @param payid
     * @param payPrice
     * @param userName
     */
    public void updatePayInfo(String payid, String payPrice, String userName) {
        SalesContractSizeModel salesContractSizeModel = this.get(Long.parseLong(payid));
        salesContractSizeModel.setRealPayDate(new Date());
        salesContractSizeModel.setRealPayPrices(BigDecimal.valueOf(Double.parseDouble(payPrice)));
        salesContractSizeModel.setRealPayUser(userName);

        this.update(salesContractSizeModel);

        //保存日志
        Long applyFoundsId = salesContractSizeModel.getSalesContractFoundModel().getId();
        String optdesc = "" + userName + ",完成往来款付款，付款" + payPrice + "";
        salesFundsoptLogService.saveOptLog(applyFoundsId, optdesc);
    }
}
