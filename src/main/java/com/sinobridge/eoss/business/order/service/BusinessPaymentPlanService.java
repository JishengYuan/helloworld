package com.sinobridge.eoss.business.order.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinobridge.base.core.hibernate.id.IdentifierGeneratorImpl;
import com.sinobridge.base.core.service.DefaultBaseService;
import com.sinobridge.eoss.bpm.service.process.ProcessService;
import com.sinobridge.eoss.business.order.constant.BusinessOrderContant;
import com.sinobridge.eoss.business.order.dao.BusinessOrderDao;
import com.sinobridge.eoss.business.order.dao.BusinessPaymentPlanDao;
import com.sinobridge.eoss.business.order.model.BusinessOrderModel;
import com.sinobridge.eoss.business.order.model.BusinessPayOrderModel;
import com.sinobridge.eoss.business.order.model.BusinessPaymentPlanModel;
import com.sinobridge.eoss.sales.contract.model.SalesContractModel;
import com.sinobridge.systemmanage.model.SysStaff;
import com.sinobridge.systemmanage.service.SysStaffService;

@Service
@Transactional
public class BusinessPaymentPlanService extends DefaultBaseService<BusinessPaymentPlanModel, BusinessPaymentPlanDao> {

    @Autowired
    private BusinessOrderService businessOrderService;
    @Autowired
    private ProcessService processService;
    @Autowired
    private BusinessOrderDao businessOrderDao;
    @Autowired
    private SysStaffService sysStaff;

    /**
     * <code>savePayPlan</code>
     * 保存付款计划
     * @param payOrderModel
     * @param gridDataList
     * @param invoiceType 
     * @return
     * @since   2014年12月15日    wangya
     */
    public void savePayPlan(BusinessPayOrderModel payOrderModel, List<Object> gridDataList, String invoiceType) {
        try {
            if (gridDataList != null) {
                for (int i = 0; i < gridDataList.size(); i++) {//加了非空判断
                    Map<String, Object> map = StringToMap(gridDataList.get(i).toString());//格式转换
                    if (map != null) {
                        BusinessPaymentPlanModel plan = new BusinessPaymentPlanModel();
                        plan.setPayOrder(payOrderModel);
                        savePay(plan, map, payOrderModel, invoiceType);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
    * <code>savePayPlan</code>
    * 执行保存订单付款计划操作
    * @param orderModel 
    * @param map
    * @param payOrderModel 
    * @param invoiceType 
    * @param name 
    * @since   2014年12月15日    wangya
    */
    public void savePay(BusinessPaymentPlanModel plan, Map<String, Object> map, BusinessPayOrderModel payOrderModel, String invoiceType) {
        try {
            String orderCode = map.get("column0").toString();
            String supplierShortName = map.get("column1").toString();
            String orderAmount = map.get("column2").toString();
            String payAmount = map.get("column4").toString();
            String credit = map.get("column5").toString();
            String orderId = map.get("column6").toString();
            long payModelId = IdentifierGeneratorImpl.generatorLong();
            plan.setId(payModelId);
            BigDecimal amount = new BigDecimal(payAmount);
            if (orderId != null || orderId != "") {
                BusinessOrderModel businessOrder = businessOrderService.get(Long.parseLong(orderId));
                plan.setBusinessOrder(businessOrder);
                Set<SalesContractModel> sales = businessOrder.getSalesContractModel();
                Iterator<SalesContractModel> s = sales.iterator();
                String saleRemark = "";
                while (s.hasNext()) {
                    SalesContractModel sale = s.next();
                    saleRemark += "【合同：" + sale.getContractCode() + "，" + sale.getContractName() + "，￥" + sale.getContractAmount() + "】</br>";
                }
                plan.setRemark(saleRemark);
            }
            plan.setAmount(amount);
            plan.setCreatorId(Long.parseLong(payOrderModel.getPayApplyUser()));
            String userId = payOrderModel.getPayApplyUser();
            SysStaff user = sysStaff.get(userId);
            plan.setCreator(user.getStaffName());
            plan.setCreatorId(Long.parseLong(userId));
            Date time = new Date();
            plan.setCredit(Integer.parseInt(credit));
            plan.setInvoiceType(invoiceType);
            plan.setCreateTime(time);
            plan.setOrderAmount(orderAmount);
            plan.setOrderCode(orderCode);
            plan.setSupplierShortName(supplierShortName);
            plan.setPlanTime(payOrderModel.getPlanPayDate());
            this.create(plan);
            BusinessOrderModel order = businessOrderService.get(Long.parseLong(orderId));
            String payStatus = BusinessOrderContant.ORDER_PAYMENT_S;
            if (amount.compareTo(order.getOrderAmount()) == 0) {
                payStatus = BusinessOrderContant.ORDER_PAYMENT_ASP;
            }
            getDao().updateOrderStatus(Long.parseLong(orderId), payStatus);//修改订单中的付款状态
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
    * <code>StringToMap</code>
    *
    * @param mapText
    * @return
    * @since   2014年12月15日   wangya
    */
    public synchronized Map<String, Object> StringToMap(String mapText) {

        if (mapText == null || mapText.equals("")) {
            return null;
        }
        mapText = mapText.substring(1, mapText.length() - 1);
        Map<String, Object> map = new HashMap<String, Object>();
        String[] text = mapText.split(",");
        for (String str : text) {
            String[] keyText = str.split("="); // 转换key与value的数组  
            if (keyText.length < 1) {
                continue;
            }
            if (keyText.length == 1) {

            } else {
                String key = keyText[0]; // key  
                String value = keyText[1]; // value  
                if (!value.trim().equals("")) {
                    map.put(key, value);
                }
            }
        }
        return map;
    }

    /**
     * <code>deletePlan</code>
     *
     * @param 删除原有的计划
     * @since   2014年12月16日    wangya
     */
    public void deletePlan(Long id) {
        getDao().deletePlan(id);
    }

}