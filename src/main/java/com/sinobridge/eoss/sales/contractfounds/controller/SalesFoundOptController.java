/*
 * FileName: SalesFoundOptController.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.sales.contractfounds.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sinobridge.eoss.bpm.service.ProcInstAppr;
import com.sinobridge.eoss.bpm.service.process.ProcessService;
import com.sinobridge.eoss.sales.contract.service.SalesContractService;
import com.sinobridge.eoss.sales.contractfounds.model.QualificationChapterInfoModel;
import com.sinobridge.eoss.sales.contractfounds.model.SalesContractFoundModel;
import com.sinobridge.eoss.sales.contractfounds.model.SalesContractSizeModel;
import com.sinobridge.eoss.sales.contractfounds.model.SalesFundsoptlogModel;
import com.sinobridge.eoss.sales.contractfounds.service.QualificationChapterInfoService;
import com.sinobridge.eoss.sales.contractfounds.service.SalesContractChapterService;
import com.sinobridge.eoss.sales.contractfounds.service.SalesContractFoundService;
import com.sinobridge.eoss.sales.contractfounds.service.SalesContractQualificationService;
import com.sinobridge.eoss.sales.contractfounds.service.SalesContractSizeService;
import com.sinobridge.eoss.sales.contractfounds.service.SalesFundsoptLogService;
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
 * 2015年12月16日 下午4:34:35          tigq        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Controller
@RequestMapping(value = "/sales/contractFoundOpt")
public class SalesFoundOptController {

    @Autowired
    private SalesContractFoundService salesContractFoundService;
    @Autowired
    private SalesContractSizeService salesContractSizeService;
    @Autowired
    private SalesContractChapterService salesContractChapterService;
    @Autowired
    private SalesContractQualificationService salesContractQualificationService;
    @Autowired
    private SalesContractService salesContractService;
    @Autowired
    private SalesFundsoptLogService salesFundsoptLogService;
    @Autowired
    private ProcessService processService;
    @Autowired
    private QualificationChapterInfoService qualificationChapterInfoService;

    /**
     * 显示投标保证金详细信息
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/bidInfo")
    @ResponseBody
    public ModelAndView bidInfo(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("sales/contractFound/biddingReview/bidInfo");
        String id = request.getParameter("id");
        SalesContractFoundModel salesContractFoundModel = salesContractFoundService.getEarnesMoneyInfo(Long.parseLong(id));
        List<SalesContractSizeModel> size = salesContractFoundModel.getSalesContractSizeModel();

        BigDecimal amount = new BigDecimal(0.00);
        for (SalesContractSizeModel s : size) {
            amount = amount.add(s.getApplyPrices());
        }
        request.setAttribute("amount", amount);
        mav.addObject("model", salesContractFoundModel);
        List<ProcInstAppr> proInstLogList = processService.findProcInstApprLog(salesContractFoundModel.getProcessInstanceId());
        mav.addObject("proInstLogList", proInstLogList);

        List<SalesFundsoptlogModel> fundLog = salesFundsoptLogService.findFundsoptlog(salesContractFoundModel.getId());
        mav.addObject("fundLogList", fundLog);
        return mav;
    }

    /**
     * 往来款付款
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/realpay")
    @ResponseBody
    public Map<String, Object> realPayprice(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();
        String payid = request.getParameter("payid");
        String paypirce = request.getParameter("payPrice");
        map.put("message", true);
        SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
        salesContractSizeService.updatePayInfo(payid, paypirce, systemUser.getStaffName());
        return map;
    }

    /**
     * 章借出
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/stampborrow")
    @ResponseBody
    public Map<String, Object> stampborrow(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();
        String stampid = request.getParameter("stampid");
        SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
        salesContractChapterService.brrowStamp(stampid, systemUser.getStaffName());
        map.put("message", true);
        return map;
    }

    /**
     * 章归还
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/stampreturn")
    @ResponseBody
    public Map<String, Object> stampreturn(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();
        String stampid = request.getParameter("stampid");
        SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
        salesContractChapterService.returnStamp(stampid, systemUser.getStaffName());
        map.put("message", true);
        return map;
    }

    /**
     * 资质借出
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/qualificationborrow")
    @ResponseBody
    public Map<String, Object> qualificationborrow(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();
        String qualificationid = request.getParameter("qualificationid");
        SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
        salesContractQualificationService.borrowqualification(qualificationid, systemUser.getStaffName());
        map.put("message", true);
        return map;
    }

    /**
     * 资质归还
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/qualificationreturn")
    @ResponseBody
    public Map<String, Object> qualificationreturn(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();
        String qualificationid = request.getParameter("qualificationid");
        SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
        salesContractQualificationService.returnqualification(qualificationid, systemUser.getStaffName());
        map.put("message", true);
        return map;
    }

    /**
     * 项目划归保证金
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/mergercontractPrice")
    @ResponseBody
    public Map<String, Object> mergercontractPrice(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();
        String foundId = request.getParameter("foundId");
        String contractCode = request.getParameter("contractCode");
        String mergercontractPrice = request.getParameter("mergercontractPrice");
        SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
        String username = systemUser.getStaffName();
        salesContractFoundService.updateMergerfunds(foundId, contractCode, mergercontractPrice, username);
        map.put("message", true);
        return map;
    }

    /**
     * 返还保证金
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/returncontractPrice")
    @ResponseBody
    public Map<String, Object> returncontractPrice(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();
        String foundId = request.getParameter("foundId");
        String returnPrice = request.getParameter("returnPrice");
        SalesContractFoundModel salesContractFoundModel = salesContractFoundService.get(Long.parseLong(foundId));
        salesContractFoundModel.setReturnPrice(BigDecimal.valueOf(Double.parseDouble(returnPrice)));
        salesContractFoundService.update(salesContractFoundModel);
        SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
        String optDesc = systemUser.getStaffName() + ",保证金已返还，金额：" + returnPrice;
        salesFundsoptLogService.saveOptLog(Long.parseLong(foundId), optDesc);
        map.put("message", true);
        return map;
    }

    /**
     * 
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/qcListInfo")
    public ModelAndView qcListInfo(HttpServletRequest request, HttpServletResponse response) {
        String val = request.getParameter("val");
        if (val == null) {
            val = "";
        }
        System.out.println(val);
        ModelAndView mav = new ModelAndView("sales/contractFound/biddingReview/qcListInfo");
        List<QualificationChapterInfoModel> qclist = qualificationChapterInfoService.getAllordertype(val);
        mav.addObject("qclist", qclist);
        return mav;
    }

    @RequestMapping(value = "/qcManage")
    public ModelAndView qcManage(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("sales/contractFound/biddingReview/qcManage");
        return mav;
    }
}
