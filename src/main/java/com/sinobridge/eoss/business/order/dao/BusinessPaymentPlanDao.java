package com.sinobridge.eoss.business.order.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sinobridge.base.core.dao.imp.DefaultBaseDao;
import com.sinobridge.base.core.utils.PaginationSupport;
import com.sinobridge.eoss.business.order.constant.BusinessOrderContant;
import com.sinobridge.eoss.business.order.model.BusinessPaymentPlanModel;

@Repository
public class BusinessPaymentPlanDao extends DefaultBaseDao<BusinessPaymentPlanModel, Long> {
    @Autowired
    private BusinessOrderDao businessOrderDao;


    /**删除付款计划
     * @param id
     */
    public void deletePlan(Long id) {
        Long[] param = new Long[1];
        param[0] = id;
        String sql = "delete from business_payment_plan where PayOrderId=?";
        createSQLQuery(sql, param).executeUpdate();
    }

    /**更新订单付款状态
     * @param id
     * @param payStatus
     */
    public void updateOrderStatus(Long id, String payStatus) {
        Object[] param = new Object[2];
        String sql = "update BusinessOrderModel set payStatus=? where id=?";
        param[0] = payStatus;
        param[1] = id;
        businessOrderDao.executeSql(sql, param);
    }

}
