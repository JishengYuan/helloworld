/*
 * FileName: FundsSalesBusiCostService.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.salesfundsCost.service;

import java.util.List;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinobridge.base.core.service.DefaultBaseService;
import com.sinobridge.eoss.business.order.model.BusinessPayOrderModel;
import com.sinobridge.eoss.business.order.service.BusinessPayOrderService;
import com.sinobridge.eoss.salesfundsCost.dao.FundsSalesBusiCostDao;
import com.sinobridge.eoss.salesfundsCost.model.FundsSalesBusiCost;

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
 * 2015年12月28日 下午5:44:45          tigq        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Service
@Transactional
public class FundsSalesBusiCostService extends DefaultBaseService<FundsSalesBusiCost, FundsSalesBusiCostDao> {

    @Autowired
    private BusinessPayOrderService businessPayOrderService;

    /**
     * 查询需要商务进行合同划分的商务付款
     * @param userName
     * @return
     */
    public List<BusinessPayOrderModel> busiCostList(String userName) {
        String sql = "SELECT * FROM business_pay_apply a WHERE a.id IN (SELECT m.PayOrderId FROM funds_salesbusicost t,business_payment_plan m WHERE t.payPlanId=m.id AND t.doUser='" + userName + "' AND t.doState='0' GROUP BY m.PayOrderId)";
        Query query = getDao().createSQLQuery(sql).addEntity(BusinessPayOrderModel.class);
        List<BusinessPayOrderModel> rs = query.list();
        return rs;
    }

    /**
     * 根据合同ID和付款计划ID，查询出未确认的商务成本
     */
    public FundsSalesBusiCost getfundsSalesBusiCost(long planid, Long contractid) {
        FundsSalesBusiCost fundsSalesBusiCost = null;
        String hql = "from FundsSalesBusiCost t where t.payPlanId=" + planid + " and t.salesContractId=" + contractid;
        Query query = getDao().createQuery(hql);
        fundsSalesBusiCost = (FundsSalesBusiCost) query.uniqueResult();
        return fundsSalesBusiCost;
    }

    /**
     * 得到已确认的商务成本
     * @param contractid
     * @return
     */
    public List<FundsSalesBusiCost> getfundsBusiCost(Long contractid) {
        String hql = "from FundsSalesBusiCost t where t.doState='1' and t.salesContractId=" + contractid;
        Query query = getDao().createQuery(hql);
        List<FundsSalesBusiCost> rs = query.list();
        return rs;
    }

    public void findApplyInfo(String applyId) {
        BusinessPayOrderModel businessPayOrderModel = businessPayOrderService.get(Long.valueOf(applyId));

    }
}
