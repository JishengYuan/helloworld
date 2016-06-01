/*
 * FileName: BusinessChangeDao.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.business.order.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.sinobridge.base.core.dao.imp.DefaultBaseDao;
import com.sinobridge.eoss.business.order.model.BusinessChangeModel;
import com.sinobridge.eoss.business.order.model.BusinessOrderModel;

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
 * 2014年12月24日 下午4:17:21         wangya        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@SuppressWarnings("unchecked")
@Repository
public class BusinessChangeDao extends DefaultBaseDao<BusinessChangeModel, Long> {

    /**通过订单ID，查询订单
     * @param orderId
     * @return
     */
    public BusinessOrderModel findOrder(Long orderId) {
        Object[] param = new Object[1];
        String sql = "select * from business_order where id=?";
        param[0] = orderId;
        Query query = this.createSQLQuery(sql, param).addEntity(BusinessOrderModel.class);
        List<BusinessOrderModel> objList = query.list();
        BusinessOrderModel order = objList.get(0);
        return order;
    }

    /**更新订单状态
     * @param orderId
     * @param status 
     */
    public void updateOrderStatus(Long orderId, String status) {
        Object[] param = new Object[2];
        String sql = "update BusinessOrderModel set isChange=? where id=?";
        param[0] = status;
        param[1] = orderId;
        executeSql(sql, param);
    }

}
