/*
 * FileName: BusinessChangeService.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.business.order.service;

import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinobridge.base.core.service.DefaultBaseService;
import com.sinobridge.eoss.business.order.constant.BusinessOrderContant;
import com.sinobridge.eoss.business.order.dao.BusinessChangeDao;
import com.sinobridge.eoss.business.order.model.BusinessChangeModel;
import com.sinobridge.eoss.business.order.model.BusinessOrderModel;

/**
 * <p>
 * Description: 
 * </p>
 *
 * @author wangya
 * @version 1.0

 * <p>
 * History: 
 *
 * Date                     Author         Version     Description
 * ---------------------------------------------------------------------------------
 * 2014年12月24日 下午4:18:50          wangya        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Service
@Transactional
public class BusinessChangeService extends DefaultBaseService<BusinessChangeModel, BusinessChangeDao> {

    /**通过订单ID，查询订单
     * @param orderId
     * @return
     */
    public BusinessOrderModel findOrder(Long orderId) {
        BusinessOrderModel order = getDao().findOrder(orderId);
        return order;
    }

    /**订单变更
     * @param isAgree
     * @param id
     */
    public void close(int isAgree, Long id) {
        String status = "";
        BusinessChangeModel change = this.get(id);
        Date time = new Date();
        change.setCloseTime(time);
        if (isAgree == 1) {
            status = "CTGSP";
            change.setStatus(BusinessOrderContant.CHANGE_TGSP);
        } else {
            status = "0";
            change.setStatus(BusinessOrderContant.CHANGE_BTY);
        }
        try {
            getDao().updateOrderStatus(change.getOrderId(), status);
            getDao().save(change);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
