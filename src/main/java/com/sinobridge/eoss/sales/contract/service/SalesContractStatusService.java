/*
 * FileName: SalesContractService.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.sales.contract.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinobridge.base.core.service.DefaultBaseService;
import com.sinobridge.eoss.business.order.dao.BusinessOrderDao;
import com.sinobridge.eoss.business.order.model.BusinessOrderModel;
import com.sinobridge.eoss.sales.contract.constant.SalesContractConstant;
import com.sinobridge.eoss.sales.contract.dao.SalesContractStatusDao;
import com.sinobridge.eoss.sales.contract.model.SalesContractStatusModel;

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
 * 2014年5月5日 下午8:25:36          3unshine        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Service
@Transactional
public class SalesContractStatusService extends DefaultBaseService<SalesContractStatusModel, SalesContractStatusDao> {
    @Autowired
    private BusinessOrderDao businessOrderDao;

    /**
     * 
     * @param ContractId
     * @param changeType
     */
    public String changeContractStatus(String contractId, String changeType, String orderId) {
        Map<String, String> orderStatusdata = new HashMap<String, String>();
        orderStatusdata.put("SH", "1");
        orderStatusdata.put("TGSP", "2");
        orderStatusdata.put("SDDH", "3");
        orderStatusdata.put("CHDH", "4");
        orderStatusdata.put("CGWB", "5");
        String orderStatus = SalesContractConstant.CONTRACT_ORDER_INITSTATE;
        if (changeType.equals("order")) {//改变订单状态
            //1.查询该合同的所有订单
            List<BusinessOrderModel> orders = businessOrderDao.findOrdersByContract(contractId);
            orderStatus = SalesContractConstant.CONTRACT_ORDER_INITSTATE;
            int i = 0;
            for (BusinessOrderModel businessOrderModel : orders) {

                if (businessOrderModel.getOrderStatus().equals("SH")) { //订单处于审核状态
                    orderStatus = SalesContractConstant.CONTRACT_ORDER_APPLY;
                    i = Integer.parseInt(orderStatusdata.get(businessOrderModel.getOrderStatus()));
                    if (orders.size() == 1 && businessOrderModel.getId() != Long.valueOf(orderId)) {
                        orderStatus = SalesContractConstant.CONTRACT_ORDER_DEVICEAPPLY;
                    }
                    break;
                } else if (businessOrderModel.getOrderStatus().equals("TGSP")) {
                    i = Integer.parseInt(orderStatusdata.get(businessOrderModel.getOrderStatus()));
                    orderStatus = SalesContractConstant.CONTRACT_ORDER_DEVICEAPPLY;
                    if (businessOrderModel.getDeliveryAddress().equals("1")) { //发货到公司
                        if ("A".equals(businessOrderModel.getWareHouseStatus())) {
                            i = 3;
                            orderStatus = SalesContractConstant.CONTRACT_ORDER_DEVICEREACH;
                            if ("A".equals(businessOrderModel.getArrivalStatus())) { //客户到货状态
                                i = 4;
                                orderStatus = SalesContractConstant.CONTRACT_ORDER_REACHCUSTOM;
                                if (businessOrderModel.getStatuse() != null) {
                                    orderStatus = SalesContractConstant.CONTRACT_ORDER_FINISH;
                                } else {
                                    orderStatus = SalesContractConstant.CONTRACT_ORDER_REACHCUSTOM;
                                }
                            } else {
                                i = 3;
                                orderStatus = SalesContractConstant.CONTRACT_ORDER_DEVICEREACH;
                            }
                        } else {
                            i = 2;
                            orderStatus = SalesContractConstant.CONTRACT_ORDER_DEVICEAPPLY;
                        }
                    } else {//发货到客户
                        if ("A".equals(businessOrderModel.getArrivalStatus())) {
                            i = 4;
                            orderStatus = SalesContractConstant.CONTRACT_ORDER_REACHCUSTOM;
                            if (businessOrderModel.getStatuse() != null) {
                                orderStatus = SalesContractConstant.CONTRACT_ORDER_FINISH;
                            } else {
                                orderStatus = SalesContractConstant.CONTRACT_ORDER_REACHCUSTOM;
                            }
                        } else {
                            i = 2;
                            orderStatus = SalesContractConstant.CONTRACT_ORDER_DEVICEAPPLY;
                        }
                    }

                }
                System.out.println("订单状态:" + orderStatus);
            }
        }
        //
        return orderStatus;
    }

    /** 
     * <code>findContractStatusByCachetPId</code>
     * 根据合同主体中的工单ID得到合同对应的状态实体
     * @param ProcessInstanceId
     * @return SalesContractStatusModel
     */
    public SalesContractStatusModel findContractStatusByCachetPId(long ProcessInstanceId) {
        return getDao().findContractStatusByCachetPId(ProcessInstanceId);
    }

    /**
     * <code>findContractStatusByChangePId</code>
     * 根据合同变更申请工单ID得到合同对应的状态实体
     * @param procInstId
     * @return
     */
    public SalesContractStatusModel findContractStatusByChangePId(long procInstId) {
        return getDao().findContractStatusByChangePId(procInstId);
    }

    /** 
     * <code>findContractStatusByContractId</code>
     * 根据合同主体ID得到合同对应的状态实体
     * @param ProcessInstanceId
     * @return SalesContractStatusModel
     */
    public SalesContractStatusModel findContractStatusByContractId(long ContractId) {
        return getDao().findContractStatusByContractId(ContractId);
    }
}
