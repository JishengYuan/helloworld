/*
 * FileName: SalesExportController.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.sales.contract.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sinobridge.base.chart.fusioncharts.util.FusionChartUtil;
import com.sinobridge.eoss.sales.contract.dto.SalesExpprtDto;
import com.sinobridge.eoss.sales.contract.model.SalesContractModel;
import com.sinobridge.eoss.sales.contract.service.SalesExportService;
import com.sinobridge.eoss.sales.contract.utils.ExcelBuilderUtil;
import com.sinobridge.systemmanage.model.SysOrgnization;
import com.sinobridge.systemmanage.model.SysStaff;
import com.sinobridge.systemmanage.service.SysStaffService;
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
 * 2015年1月15日 下午5:58:18          tigq        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Controller
@RequestMapping(value = "/sales/export")
public class SalesExportController {
    @Autowired
    private SysStaffService staffService;

    @Autowired
    private SalesExportService salesExportService;

    @RequestMapping(value = "/exportSales")
    public void exportSales(HttpServletRequest request, HttpServletResponse response) {
        HashMap<String, Object> searchMap = new HashMap<String, Object>();
        searchMap.put("contractStateNotEquals", "CG");
        buildSearchHashMap(request, searchMap);
        List<SalesExpprtDto> salesDtoList = salesExportService.exportContracts(searchMap);

        List<String> titles = new ArrayList<String>();
        List<String> fields = new ArrayList<String>();

        titles.add("客户经理");
        titles.add("客户名称");
        titles.add("省份");
        titles.add("合同编码");
        titles.add("合同名称");
        titles.add("合同类型");
        titles.add("合同状态");
        titles.add("合同订单状态");
        titles.add("生效时间");
        titles.add("合同文本盖章日期");
        titles.add("合同金额");
        titles.add("资金占用数");
        titles.add("合同下采购成本");
        titles.add("占用资金成本");
        titles.add("项目实施成本");
        titles.add("合同毛利");
        titles.add("税后毛利");
        titles.add("毛利率(%)");
        titles.add("已结算合同额");
        titles.add("已开发票金额");
        titles.add("未开发票金额");
        titles.add("已收款");
        titles.add("未收款");
        titles.add("已付款");
        titles.add("未付款");
        titles.add("是否已结算");
        titles.add("发票类型");

        fields.add("creatorName");
        fields.add("customerName");
        fields.add("provinces");
        fields.add("contractCode");
        fields.add("contractName");
        fields.add("contractType");
        fields.add("contractState");
        fields.add("contractOrderState");
        fields.add("salesStartDate");
        fields.add("cachetdate");
        fields.add("contractAmount");
        fields.add("capital");
        fields.add("procurementCosts");
        fields.add("capitalCost");
        fields.add("projectCost");
        fields.add("contractMargin");
        fields.add("afterTaxMargin");
        fields.add("profitRate");
        fields.add("costAmount");
        fields.add("invoiceAmount");
        fields.add("uninvoiceAmount");
        fields.add("receiveAmount");
        fields.add("unreceiveAmount");
        fields.add("payment");
        fields.add("unpayment");
        fields.add("isCost");
        fields.add("invoiceType");

        Workbook excel = ExcelBuilderUtil.buildPage(titles, salesDtoList, fields);
        FusionChartUtil.renderExcel(response, excel, "sales.xls");

    }

    /**
     * 构造查询条件
     * @param request
     * @param searchMap
     */
    public void buildSearchHashMap(HttpServletRequest request, HashMap<String, Object> searchMap) {
        String contractName = request.getParameter("contractName");
        String contractShortname = request.getParameter("contractShortname");
        String contractState = request.getParameter("contractState");
        String contractCode = request.getParameter("contractCode");
        String contractAmount = request.getParameter("contractAmount");
        String contractAmountb = request.getParameter("contractAmountb");
        String creator = request.getParameter("creator");
        String contractType = request.getParameter("contractType");
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        String orgId = request.getParameter("orgId");
        String customerIndustry = request.getParameter("customerIndustry");
        String customerIdtCustomer = request.getParameter("customerIdtCustomer");
        String customerInfo = request.getParameter("customerInfo");
        String userName = request.getParameter("userName");
        String orgName =  request.getParameter("orgName");
        if (!StringUtil.isEmpty(creator)) {
            SysStaff staff = staffService.getStaffByAccount(creator);
            searchMap.put(SalesContractModel.CREATOR, staff.getStaffId());
        }
        if (!StringUtil.isEmpty(contractType)) {
            searchMap.put(SalesContractModel.CONTRACTTYPE, contractType);
        }
        if (!StringUtil.isEmpty(contractName)) {
            searchMap.put(SalesContractModel.CONTRACTNAME, "%" + contractName + "%");
        }
        if (!StringUtil.isEmpty(contractShortname)) {
            searchMap.put(SalesContractModel.CONTRACTSHORTNAME, "%" + contractShortname + "%");
        }
        if (!StringUtil.isEmpty(contractState)) {
            searchMap.put(SalesContractModel.CONTRACTSTATE, contractState);
        }
        if (!StringUtil.isEmpty(contractCode)) {
            searchMap.put(SalesContractModel.CONTRACTCODE, "%" + contractCode + "%");
        }
        if (!StringUtil.isEmpty(contractAmount)) {
            searchMap.put(SalesContractModel.CONTRACTAMOUNT, contractAmount);
        }
        if (!StringUtil.isEmpty(contractAmountb)) {
            searchMap.put("contractAmountb", contractAmountb);
        }
        if (!StringUtil.isEmpty(startTime)) {
            searchMap.put(SalesContractModel.CLOSETIME, startTime);
        }
        if (!StringUtil.isEmpty(endTime)) {
            searchMap.put("endTime", endTime);
        }
        if (!StringUtil.isEmpty(customerInfo)) {
            searchMap.put(SalesContractModel.CUSTOMERID, customerInfo);
        }
        if (!StringUtil.isEmpty(orgId)) {
            searchMap.put("orgId", orgId);
        }
        if (!StringUtil.isEmpty(customerIndustry)) {
            searchMap.put("cusIndustryId", customerIndustry);
        }
        if (!StringUtil.isEmpty(customerIdtCustomer)) {
            searchMap.put("cusCustomerId", customerIdtCustomer);
        }
        if (!StringUtil.isEmpty(userName)) {
            SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
            searchMap.put(SalesContractModel.CREATOR, systemUser.getUserId());
        }
        if (!StringUtil.isEmpty(orgName)) {
            SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
            SysOrgnization org = systemUser.getSysOrgnization();
            searchMap.put("orgId", org.getOrgId());
        }
        
    }

}
