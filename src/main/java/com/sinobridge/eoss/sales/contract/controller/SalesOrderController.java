/*
 * FileName: SalesOrderController.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.sales.contract.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sinobridge.base.chart.fusioncharts.util.FusionChartUtil;
import com.sinobridge.base.core.utils.PaginationSupport;
import com.sinobridge.eoss.sales.contract.dto.SalesRelevanceDto;
import com.sinobridge.eoss.sales.contract.model.SalesContractModel;
import com.sinobridge.eoss.sales.contract.service.SalesOrderService;
import com.sinobridge.eoss.sales.contract.utils.DateUtils;
import com.sinobridge.eoss.sales.contract.utils.ExcelUtilRelevance;
import com.sinobridge.systemmanage.service.SysStaffService;
import com.sinobridge.systemmanage.util.StringUtil;

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
 * 2015年3月4日 下午2:46:55          wangya        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Controller
@RequestMapping(value = "/sales/contract/relevance")
public class SalesOrderController {

    @Autowired
    private SysStaffService staffService;

    @Autowired
    private SalesOrderService salesOrderService;

    /**
     * <code>relevance/manage</code>
     * 我的合同管理页面
     * @param request
     * @param response
     * @return
     * @since   2015年3月04日    wangya
     */
    @RequestMapping(value = "manage")
    public ModelAndView relevanceManage(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("sales/contract/relevance/manage");
        return mav;
    }

    /**
     * <code>getListRelevance</code>
     * @param request
     * @param response
     * @return
     * @since    2015年3月04日    wangya
     */
    @ResponseBody
    @RequestMapping(value = "/getListRelevance")
    public Map<String, Object> getListRelevance(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();
        String iDisplayStart = request.getParameter("pageNo");
        String iDisplayLength = request.getParameter("pageSize");
        HashMap<String, Object> searchMap = new HashMap<String, Object>();
        buildSearch(request, searchMap);
        //当前登录用户
        // SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
        //用户只能管理自己创建的合同
        // searchMap.put(SalesContractModel.CREATOR, Long.parseLong(systemUser.getUserId()));
        //按创建时间排序
        //searchMap.put("orderBy", "c.createTime");
        PaginationSupport paginationSupport = salesOrderService.findRelevanceBySearchMap(searchMap, (Integer.parseInt(iDisplayStart) - 1) * Integer.parseInt(iDisplayLength), Integer.parseInt(iDisplayLength));
        map.put("rows", paginationSupport.getItems());
        map.put("total", paginationSupport.getTotalCount());
        map.put("page", iDisplayStart);
        return map;
    }

    /**
     * <code>buildSearch</code>
     * 通过HashMap创建查询条件
     * @param searchMap
     * @since 2015年3月04日    wangya
     */
    public void buildSearch(HttpServletRequest request, HashMap<String, Object> searchMap) {
        String contractCode = request.getParameter("contractCode");
        String contractAmount = request.getParameter("contractAmount");
        String creator = request.getParameter("creator");
        //String contractType = request.getParameter("contractType");
        String sales_startTime = request.getParameter("sales_startTime");
        String sales_endTime = request.getParameter("sales_endTime");
        String order_startTime = request.getParameter("order_startTime");
        String order_endTime = request.getParameter("order_endTime");
        String orgId = request.getParameter("orgId");

        if (!StringUtil.isEmpty(creator)) {
            String staff = salesOrderService.findCreatorId(creator);
            searchMap.put(SalesContractModel.CREATOR, staff);
        }
        /*if (!StringUtil.isEmpty(contractType)) {
            searchMap.put(SalesContractModel.CONTRACTTYPE, contractType);
        }*/
        if (!StringUtil.isEmpty(contractCode)) {
            searchMap.put(SalesContractModel.CONTRACTCODE, "%" + contractCode + "%");
        }
        if (!StringUtil.isEmpty(contractAmount)) {
            searchMap.put(SalesContractModel.CONTRACTAMOUNT, contractAmount);
        }
        if (!StringUtil.isEmpty(sales_startTime)) {
            searchMap.put("sales_startTime", sales_startTime);
        }
        if (!StringUtil.isEmpty(sales_endTime)) {
            searchMap.put("sales_endTime", sales_endTime);
        }
        if (!StringUtil.isEmpty(order_startTime)) {
            searchMap.put("order_startTime", order_startTime);
        }
        if (!StringUtil.isEmpty(order_endTime)) {
            searchMap.put("order_endTime", order_endTime);
        }
        if (!StringUtil.isEmpty(orgId)) {
            searchMap.put("orgId", orgId);
        }
    }

    /**
     * <code>searchRelevance</code>
     * 查询条件管理页面
     * @param request
     * @param response
     * @return
     * @since   2015年3月04日    wangya
     */
    @RequestMapping("/search")
    public ModelAndView searchRelevance(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("sales/contract/relevance/search");
        return mav;
    }

    @RequestMapping(value = "/exportSales")
    public void exportSales(HttpServletRequest request, HttpServletResponse response) {
        HashMap<String, Object> searchMap = new HashMap<String, Object>();
        buildSearch(request, searchMap);
        List<SalesRelevanceDto> SalesOrderDto = salesOrderService.orderContracts(searchMap);

        List<String> titles = new ArrayList<String>();
        List<String> fields = new ArrayList<String>();

        titles.add("销售合同编号");
        titles.add("销售合同状态");
        titles.add("合同下单数");
        titles.add("对应订单数量");
        titles.add("合同总金额");
        titles.add("对应订单编号");
        titles.add("订单下单数");
        titles.add("订单分项金额");
        titles.add("下单日期");

        fields.add("contractCode");
        fields.add("salesOrder");
        fields.add("productNum");
        fields.add("orderNum");
        fields.add("contractAmount");
        fields.add("orderCode");
        fields.add("orderProNum");
        fields.add("orderProductAmount");
        fields.add("beginTime");

        Date time = new Date();
        Workbook excel = ExcelUtilRelevance.buildPage(titles, SalesOrderDto, fields);
        try {
            FusionChartUtil.renderExcel(response, excel, "salesOrderRelevance" + DateUtils.convertDateToString(time, "yyyy-MM-dd") + ".xls");
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

}
