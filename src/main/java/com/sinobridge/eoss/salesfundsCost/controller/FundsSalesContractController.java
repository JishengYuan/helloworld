/*
 * FileName: FundsSalesContractController.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.salesfundsCost.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sinobridge.base.core.controller.DefaultBaseController;
import com.sinobridge.base.core.utils.PaginationSupport;
import com.sinobridge.eoss.sales.contract.model.SalesContractModel;
import com.sinobridge.eoss.salesfundsCost.model.FundsSalesContract;
import com.sinobridge.eoss.salesfundsCost.model.FundsSalesLog;
import com.sinobridge.eoss.salesfundsCost.service.FundsSalesContractService;
import com.sinobridge.eoss.salesfundsCost.service.FundsSalesLogService;
import com.sinobridge.systemmanage.util.Global;
import com.sinobridge.systemmanage.util.StringUtil;
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
 * 2015年12月24日 下午3:12:32          tigq        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Controller
@RequestMapping(value = "/sales/fundsSalesContract")
public class FundsSalesContractController extends DefaultBaseController<FundsSalesContract, FundsSalesContractService> {

    @Autowired
    private FundsSalesContractService fundsSalesContractService;

    @Autowired
    private FundsSalesLogService fundsSalesLogService;

    @RequestMapping(value = "/manage")
    public ModelAndView manage(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("/sales/funds/manage");
        return mav;
    }

    @RequestMapping(value = "/modi")
    public ModelAndView modi(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("/sales/funds/modi");
        String fundsId = request.getParameter("fundsId");
        FundsSalesContract fundsSalesContract = fundsSalesContractService.get(Long.valueOf(fundsId));
        mav.addObject("model", fundsSalesContract);
        return mav;
    }

    @RequestMapping(value = "/modifunds")
    @ResponseBody
    public String modifunds(HttpServletRequest request, HttpServletResponse response, FundsSalesContract fundsSalesContract) throws Exception {
        String id = request.getParameter("id");
        FundsSalesContract fundsSalesContractOld = fundsSalesContractService.get(Long.parseLong(id));
        //当前登录用户
        SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
        String rs = getService().saveModiFunds(fundsSalesContract, fundsSalesContractOld, systemUser);
        return rs;
    }

    /**
     * 查询页面
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/search")
    public ModelAndView search(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("/sales/funds/search");
        return mav;
    }

    /**
     * 成本合同查询数据 
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getList")
    public Map<String, Object> getList(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();
        String iDisplayStart = request.getParameter("pageNo");
        String iDisplayLength = request.getParameter("pageSize");
        String contractName = request.getParameter("contractName");
        String contractCode = request.getParameter("contractCode");
        String contractAmount = request.getParameter("contractAmount");
        String contractAmountb = request.getParameter("contractAmountb");
        String test = request.getParameter("test");
        if (iDisplayStart != null) {
            this.pageNo = Integer.parseInt(iDisplayStart) - 1;
        }
        if (iDisplayLength != null) {
            this.pageSize = Integer.parseInt(iDisplayLength);
        }

        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(FundsSalesContract.class);
        if (!StringUtil.isEmpty(test)) {
            detachedCriteria.add(Restrictions.or(Restrictions.like("contractCode", test, MatchMode.ANYWHERE), Restrictions.like("contractName", test, MatchMode.ANYWHERE)));

        }
        if (!StringUtil.isEmpty(contractName)) {
            detachedCriteria.add(Restrictions.like("contractName", "%" + contractName + "%"));
        }
        if (!StringUtil.isEmpty(contractCode)) {
            detachedCriteria.add(Restrictions.like("contractCode", "%" + contractCode + "%"));
        }

        if (!StringUtil.isEmpty(contractAmount)) {
            detachedCriteria.add(Restrictions.ge("contractAmount", new BigDecimal(contractAmount)));
        }
        if (!StringUtil.isEmpty(contractAmountb)) {
            detachedCriteria.add(Restrictions.le("contractAmount", new BigDecimal(contractAmountb)));
        }
        detachedCriteria.addOrder(Order.desc("id"));
        // detachedCriteria.addOrder(org.hibernate.criterion.Order.asc("doState"));

        PaginationSupport paginationSupport = getService().findPageByCriteria(detachedCriteria, this.pageNo * this.pageSize, this.pageSize);
        map.put("rows", paginationSupport.getItems());
        map.put("total", paginationSupport.getTotalCount());
        map.put("page", iDisplayStart);
        return map;
    }

    /**
     * 详情
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/detail")
    public ModelAndView detail(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("/sales/funds/detail");
        String contractCode = request.getParameter("contractCode");
        SalesContractModel sale = getService().getSalesContract(contractCode);
        mav.addObject("model", sale);
        List<FundsSalesLog> logs = getService().getFundsLogs(contractCode);
        request.setAttribute("logs", logs);
        FundsSalesContract fundSales = getService().getFundsSales(contractCode);
        request.setAttribute("fundSales", fundSales);
        return mav;
    }

}
