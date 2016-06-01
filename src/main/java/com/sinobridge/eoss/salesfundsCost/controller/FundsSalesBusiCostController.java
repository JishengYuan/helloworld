/*
 * FileName: FundsSalesBusiCostController.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.salesfundsCost.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sinobridge.base.core.controller.DefaultBaseController;
import com.sinobridge.eoss.business.order.model.BusinessOrderModel;
import com.sinobridge.eoss.business.order.model.BusinessPayOrderModel;
import com.sinobridge.eoss.business.order.model.BusinessPaymentPlanModel;
import com.sinobridge.eoss.business.order.service.BusinessPayOrderService;
import com.sinobridge.eoss.sales.contract.model.SalesContractModel;
import com.sinobridge.eoss.sales.contract.service.SalesContractService;
import com.sinobridge.eoss.salesfundsCost.model.FundsSalesBusiCost;
import com.sinobridge.eoss.salesfundsCost.model.FundsSalesContract;
import com.sinobridge.eoss.salesfundsCost.model.FundsSalesLog;
import com.sinobridge.eoss.salesfundsCost.service.FundsSalesBusiCostService;
import com.sinobridge.eoss.salesfundsCost.service.FundsSalesContractService;
import com.sinobridge.eoss.salesfundsCost.service.FundsSalesLogService;
import com.sinobridge.systemmanage.util.Global;
import com.sinobridge.systemmanage.vo.SystemUser;

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
 * 2015年12月29日 上午11:03:12          tigq        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Controller
@RequestMapping(value = "/sales/fundsSalesBusiCost")
public class FundsSalesBusiCostController extends DefaultBaseController<FundsSalesBusiCost, FundsSalesBusiCostService> {

    @Autowired
    private SalesContractService salesContractServive;
    @Autowired
    private BusinessPayOrderService businessPayOrderService;
    @Autowired
    private FundsSalesContractService fundsSalesContractService;
    @Autowired
    private FundsSalesLogService fundsSalesLogService;
    @Autowired
    private FundsSalesBusiCostService fundsSalesBusiCostService;

    @RequestMapping(value = "busiCostList")
    public ModelAndView busiCostList(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("sales/funds/busiCostList");
        SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
        List<BusinessPayOrderModel> rs = getService().busiCostList(systemUser.getStaffName());
        mav.addObject("rs", rs);
        return mav;
    }

    /**
     * 商务付款确认页面
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "busiCostInfo")
    public ModelAndView busiCostInfo(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("sales/funds/busiCostInfo");
        String payApplyId = request.getParameter("payAppid");
        BusinessPayOrderModel businessPayOrderModel = businessPayOrderService.get(Long.valueOf(payApplyId));//查询出付款申请
        Set<BusinessPaymentPlanModel> businessPaymentPlanModels = businessPayOrderModel.getBusinessPaymentPlanModel();//查询出付款计划
        List<Map<String, Object>> rs = new ArrayList<Map<String, Object>>();
        BigDecimal qramount = new BigDecimal(0.00);
        for (BusinessPaymentPlanModel businessPaymentPlanModel : businessPaymentPlanModels) {
            Map<String, Object> map = new HashMap<String, Object>();
            BusinessOrderModel orderModel = businessPaymentPlanModel.getBusinessOrder();//查询每个付款计划的订单
            map.put("model", orderModel);
            if ("usd".equals(businessPayOrderModel.getCurrency())) {//如果是美元付款，取实际付的人民币
                map.put("payOrderAmount", businessPaymentPlanModel.getRealPayAmount());
            } else {
                map.put("payOrderAmount", businessPaymentPlanModel.getAmount());
            }

            map.put("payPlanId", businessPaymentPlanModel.getId());
            map.put("payPlanStatus", businessPaymentPlanModel.getPlanStatus());
            if (businessPaymentPlanModel.getPlanStatus() != null && businessPaymentPlanModel.getPlanStatus().equals("1")) {
                if ("usd".equals(businessPayOrderModel.getCurrency())) {
                    //如果是美元付款，取实际付的人民币
                    qramount = qramount.add(businessPaymentPlanModel.getRealPayAmount()); //已确认的金额
                } else {
                    qramount = qramount.add(businessPaymentPlanModel.getAmount()); //已确认的金额

                }
            }

            Set<SalesContractModel> sales = orderModel.getSalesContractModel();//根据订单查询出每个订单对应的合同信息
            for (SalesContractModel salesContractModel : sales) {

                List<Map<String, Object>> product = salesContractServive.getOrderProductDifferent(salesContractModel.getId(), orderModel.getId());//取出合同下单产品列表
                salesContractModel.setOrdersaleproducts(product);
                FundsSalesContract fundsSalesContracts = fundsSalesContractService.getBycontractCode(salesContractModel.getContractCode());//取出已手工确认的商务成本
                if (fundsSalesContracts.getBusinessCost() != null) {
                    salesContractModel.setBusiCost(fundsSalesContracts.getBusinessCost().toString());
                } else {
                    salesContractModel.setBusiCost("");
                }

            }

            map.put("sales", sales);
            rs.add(map);

        }
        mav.addObject("payOrderModel", businessPayOrderModel);
        mav.addObject("qramount", qramount);
        if ("usd".equals(businessPayOrderModel.getCurrency())) {
            mav.addObject("wqramount", businessPayOrderModel.getRealPayAmount().subtract(qramount));
        } else {
            mav.addObject("wqramount", businessPayOrderModel.getPayAmonut().subtract(qramount));
        }

        mav.addObject("rs", rs);
        return mav;
    }

    @ResponseBody
    @RequestMapping(value = "dobusiCost")
    public Map<String, String> dobusiCost(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> rsmsg = new HashMap<String, String>();
        String jsonStr = request.getParameter("aid");
        /* System.out.println(request.getParameter("aid"));
         System.out.println(request.getParameter("planid"));*/
        // String planid = request.getParameter("planid");
        JSONArray jsonArray = JSONArray.fromObject(jsonStr);
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonJ = jsonArray.getJSONObject(i);
            //System.out.println(jsonJ.getString("name") + "|" + jsonJ.getDouble("value") + "|" + jsonJ.getLong("contractid"));
            FundsSalesContract fundsSalesContract = fundsSalesContractService.getBycontractCode(jsonJ.getString("name"));
            if (fundsSalesContract != null) {
                BigDecimal tmp = fundsSalesContract.getBusinessCost();
                if (tmp == null) {
                    tmp = new BigDecimal(0.0);
                }
                tmp = tmp.add(BigDecimal.valueOf(jsonJ.getDouble("value")));
                fundsSalesContract.setBusinessCost(tmp);
                fundsSalesContractService.update(fundsSalesContract); //更新商务成本
                //记录商务成本产生日志
                FundsSalesLog fundsSalesLog = new FundsSalesLog();
                fundsSalesLog.setOpAmount(tmp);
                fundsSalesLog.setOpDate(new Date());
                fundsSalesLog.setContractCode(fundsSalesContract.getContractCode());
                fundsSalesLog.setOpDesc("产生商务成本");
                fundsSalesLogService.saveOrUpdate(fundsSalesLog);
                //更新商务待办任务
                FundsSalesBusiCost fundsSalesBusiCost = fundsSalesBusiCostService.getfundsSalesBusiCost(jsonJ.getLong("planid"), jsonJ.getLong("contractid"));
                fundsSalesBusiCost.setDoDate(new Date());
                fundsSalesBusiCost.setDoState("1");
                fundsSalesBusiCost.setContractBusiCost(BigDecimal.valueOf(jsonJ.getDouble("value")));
                fundsSalesBusiCostService.update(fundsSalesBusiCost);
            }
        }
        return rsmsg;
    }
}
