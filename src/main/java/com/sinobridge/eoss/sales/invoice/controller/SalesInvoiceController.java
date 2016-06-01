/*
 * FileName: SalesContractController.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.sales.invoice.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sinobridge.base.chart.fusioncharts.util.FusionChartUtil;
import com.sinobridge.base.core.controller.DefaultBaseController;
import com.sinobridge.base.core.hibernate.id.IdentifierGeneratorImpl;
import com.sinobridge.base.core.utils.JacksonJsonMapper;
import com.sinobridge.base.core.utils.PaginationSupport;
import com.sinobridge.eoss.bpm.common.constants.ProcessConstants;
import com.sinobridge.eoss.bpm.service.ProcInstAppr;
import com.sinobridge.eoss.bpm.service.process.ProcessService;
import com.sinobridge.eoss.business.suppliermanage.service.SupplierInfoService;
import com.sinobridge.eoss.sales.contract.constant.SalesContractConstant;
import com.sinobridge.eoss.sales.contract.model.SalesContractModel;
import com.sinobridge.eoss.sales.contract.service.SalesContractService;
import com.sinobridge.eoss.sales.contract.utils.CodeUtils;
import com.sinobridge.eoss.sales.contract.utils.DateUtils;
import com.sinobridge.eoss.sales.invoice.dto.SalesInvoiceExportDto;
import com.sinobridge.eoss.sales.invoice.model.SalesInvoicePlanModel;
import com.sinobridge.eoss.sales.invoice.service.SalesInvoiceService;
import com.sinobridge.eoss.sales.invoice.utils.ExportInvoiceUtils;
import com.sinobridge.eoss.salesfundsCost.service.FundsSalesBudgetService;
import com.sinobridge.systemmanage.model.SysPowerAction;
import com.sinobridge.systemmanage.model.SysStaff;
import com.sinobridge.systemmanage.util.Constants;
import com.sinobridge.systemmanage.util.Global;
import com.sinobridge.systemmanage.util.StringUtil;
import com.sinobridge.systemmanage.vo.SystemUser;

import net.sf.ehcache.Ehcache;

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
 * 2014年5月26日 上午10:27:56          3unshine        1.0         To create
 * </p>
 *
 * @since
 * @see
 */
@Controller
@RequestMapping(value = "/sales/invoice")
public class SalesInvoiceController extends DefaultBaseController<SalesInvoicePlanModel, SalesInvoiceService> {
    String pattern = "yyyy-MM-dd";
    //仅有日期的格式
    String DatePattern = "yyyy-MM-dd";
    private final String addColumns[] = { "invoiceAmount", "invoiceTime", "invoiceType", "remark", "id" };
    private final String addColumnNames[] = { "发票金额 ", "开发票日期 ", "发票类型", "发票备注说明", "id" };
    private final String addColumnTypes[] = { "string", "datetime", "select", "string", "hidden" };
    private final String addColumnUrl[] = { "", "", "", "", "" };
    private String columnWidths[] = { "100", "100", "260", "380", "1" };

    @Autowired
    private SalesContractService salesContractService;
    @Autowired
    private ProcessService processService;
    @Autowired
    private SupplierInfoService supplierInfoService;

    private final static Ehcache systemUserCache = Constants.CACHE_MANAGER.getEhcache("systemUser");

    @Autowired
    private FundsSalesBudgetService fundsSalesBudgetService;

    /**
     * <code>apply</code>
     * 跳转到apply页面
     * @param request
     * @param response
     * @return
     * @since 2014年7月22日     3unshine
     */
    @RequestMapping(value = "/apply")
    public ModelAndView apply(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("sales/invoice/apply");
        String id = request.getParameter("id");
        SalesContractModel salesContract = salesContractService.get(Long.parseLong(id));
        mav.addObject("model", salesContract);

        String colseType = request.getParameter("colseType");
        mav.addObject("colseType", colseType);
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(SalesInvoicePlanModel.class);
        detachedCriteria.add(Restrictions.eq(SalesInvoicePlanModel.CONTRACTID, salesContract.getId()));
        detachedCriteria.add(Restrictions.ne(SalesInvoicePlanModel.INVOICESTATUS, "FQ"));
        detachedCriteria.add(Restrictions.ne(SalesInvoicePlanModel.INVOICESTATUS, "BG"));
        List<SalesInvoicePlanModel> salesInvoicePlanModels4SurplusAmount = getService().getSalesInvoicePlanListByContractId(detachedCriteria);
        //算出剩余的可开发票金额，如果还没有申请过，那么显示可申请金额为合同的总金额
        if (salesInvoicePlanModels4SurplusAmount.size() > 0) {
            BigDecimal surplusAmount = surplusInvoiceAmount(salesContract);
            mav.addObject("surplusAmount", surplusAmount);
        } else {
            mav.addObject("surplusAmount", salesContract.getContractAmount());
        }
        String formDataJson = "";
        List<SalesInvoicePlanModel> salesInvoicePlanModels = null;
        //拼凑出tableGrid所需的JSON数据
        formDataJson = tableGridDataUnit(salesInvoicePlanModels, "save");
        request.setAttribute("form", formDataJson);
        return mav;
    }

    /**
     * <code>doSave</code>
     * 保存更新实体
     * @param request
     * @param response
     * @param salesCachetModel
     * @return
     * @since 2014年7月22日    3unshine
     */
    @ResponseBody
    @RequestMapping(value = "/doSave")
    public String doSave(HttpServletRequest request, HttpServletResponse response, SalesContractModel salesContractModel) {
        try {
            String invoiceIds = request.getParameter("invoiceIds");
            String id = salesContractModel.getId().toString();
            //创建者
            SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
            //List<SalesInvoicePlanModel> salesInvoicePlanList = invoicePlanDataAnalyst(salesContractModel, systemUser);
            String allDate = request.getParameter("allDate");
            List<SalesInvoicePlanModel> salesInvoice = getService().doInvoiceSTR(id, allDate);
            getService().save(salesContractModel, salesInvoice, systemUser, invoiceIds);

            //改变变更发票状态
            if (!StringUtil.isEmpty(invoiceIds)) {
                String[] ids = invoiceIds.split(",");
                for (String s : ids) {
                    SalesInvoicePlanModel model = getService().get(Long.parseLong(s));
                    model.setInvoiceStatus("BG");
                    getService().update(model);
                }
            }

            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return "erorr";
        }
    }

    /**
     * <code>reApply</code>
     * 跳转到reApply页面
     * @param request
     * @param response
     * @return
     * @since 2014年7月22日     3unshine
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/reApply")
    public ModelAndView reApply(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("sales/invoice/reApply");
        String flowFlag = request.getAttribute("flowFlag") + "";
        if (flowFlag != null && flowFlag != "")
            request.setAttribute("flowFlag", flowFlag);
        String taskId = request.getAttribute("taskId") + "";
        if (taskId != null && taskId != "")
            request.setAttribute("taskId", taskId);
        String procInstId = request.getAttribute("procInstId") + "";
        if (procInstId != null && procInstId != "")
            request.setAttribute("procInstId", procInstId);
        String idString = request.getAttribute("contractId") + "";
        List<SalesInvoicePlanModel> salesInvoicePlanModels = new ArrayList<SalesInvoicePlanModel>();
        if (!StringUtil.isEmpty(idString)) {
            long contractId = Long.parseLong(idString);
            BigDecimal applyAmount = new BigDecimal(0.00);
            //salesInvoicePlanModels = (List<SalesInvoicePlanModel>) request.getAttribute("salesInvoicePlanList");
            salesInvoicePlanModels = getService().getInvoiceByProcInstId(procInstId, idString);
            SalesContractModel salesContract = salesContractService.get(contractId);
            mav.addObject("model", salesContract);
            //把要申请的这部分金额加回到剩余金额中
            Iterator<SalesInvoicePlanModel> i = salesInvoicePlanModels.iterator();
            while (i.hasNext()) {
                SalesInvoicePlanModel s = i.next();
                BigDecimal invoiceAmount = applyAmount.add(s.getInvoiceAmount());
                applyAmount = invoiceAmount;
            }
            String formDataJson = "";
            //BigDecimal surplusAmount = surplusInvoiceAmount(salesContract).add(applyAmount);
            BigDecimal surplusAmount = applyAmount;
            mav.addObject("surplusAmount", surplusAmount);
            //拼凑出tableGrid所需的JSON数据
            formDataJson = tableGridDataUnit(salesInvoicePlanModels, "update");
            request.setAttribute("form", formDataJson);
        }
        return mav;
    }

    /**
     * <code>applyDetail</code>
     * 跳转到applyDetail页面
     * @param request
     * @param response
     * @param CWFH
     * @return
     * @since 2014年7月22日     3unshine
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/applyDetail")
    public ModelAndView applyDetail(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("sales/invoice/applyDetail");
        String isFlow = request.getAttribute("isFlow") + "";
        String taskId = request.getAttribute("taskId") + "";
        String procInstId = request.getAttribute("procInstId") + "";

        if (!StringUtil.isEmpty(taskId)) {
            request.setAttribute("taskId", taskId);
        }
        if (!StringUtil.isEmpty(procInstId)) {
            request.setAttribute("procInstId", procInstId);
        }
        long contractId = 0L;
        BigDecimal applyAmount = new BigDecimal(0.00);
        List<SalesInvoicePlanModel> salesInvoicePlanModels = new ArrayList<SalesInvoicePlanModel>();
        String idString = request.getParameter("id");
        if (StringUtil.isEmpty(idString)) {
            salesInvoicePlanModels = (List<SalesInvoicePlanModel>) request.getAttribute("salesInvoicePlanList");
            if (salesInvoicePlanModels.size() > 0) {
                contractId = salesInvoicePlanModels.get(0).getSalesContractId();
            }
            //把要申请的这部分金额加回到剩余金额中
            //            Iterator<SalesInvoicePlanModel> i = salesInvoicePlanModels.iterator();
            //            while (i.hasNext()) {
            //                SalesInvoicePlanModel s = i.next();
            //                BigDecimal invoiceAmount = applyAmount.add(s.getInvoiceAmount());
            //                applyAmount = invoiceAmount;
            //            }

            //要变更的发票
            DetachedCriteria detachedCriteria1 = DetachedCriteria.forClass(SalesInvoicePlanModel.class);
            detachedCriteria1.add(Restrictions.eq(SalesInvoicePlanModel.CONTRACTID, contractId));
            detachedCriteria1.add(Restrictions.eq(SalesInvoicePlanModel.INVOICESTATUS, "BG"));
            List<SalesInvoicePlanModel> changeInvoicePlanModels = getService().getSalesInvoicePlanListByContractId(detachedCriteria1);
            mav.addObject("changeInvoicePlanModels", changeInvoicePlanModels);

        } else {
            contractId = Long.parseLong(idString);
            DetachedCriteria detachedCriteria = DetachedCriteria.forClass(SalesInvoicePlanModel.class);
            detachedCriteria.add(Restrictions.eq(SalesInvoicePlanModel.CONTRACTID, contractId));
            detachedCriteria.add(Restrictions.ne(SalesInvoicePlanModel.INVOICESTATUS, "FQ"));
            detachedCriteria.add(Restrictions.ne(SalesInvoicePlanModel.INVOICESTATUS, "BG"));
            detachedCriteria.add(Restrictions.ne(SalesInvoicePlanModel.INVOICESTATUS, "CG"));
            salesInvoicePlanModels = getService().getSalesInvoicePlanListByContractId(detachedCriteria);
            Iterator<SalesInvoicePlanModel> inv = salesInvoicePlanModels.iterator();

            //是否有打印按钮：财务审核时才有
            List<Map<String, String>> isPrint = new ArrayList<Map<String, String>>();
            while (inv.hasNext()) {
                SalesInvoicePlanModel s = inv.next();
                Map<String, String> map = new HashMap<String, String>();
                if ("TGSH".equals(s.getInvoiceStatus())) {
                    map.put("inPrint", "1");
                } else {
                    map.put("inPrint", getService().isPrint(s.getProcessInstanceId()));
                }

                isPrint.add(map);
            }
            mav.addObject("isPrint", isPrint);

            //要变更的发票
            DetachedCriteria detachedCriteria1 = DetachedCriteria.forClass(SalesInvoicePlanModel.class);
            detachedCriteria1.add(Restrictions.eq(SalesInvoicePlanModel.CONTRACTID, contractId));
            detachedCriteria1.add(Restrictions.eq(SalesInvoicePlanModel.INVOICESTATUS, "BG"));
            List<SalesInvoicePlanModel> changeInvoicePlanModels = getService().getSalesInvoicePlanListByContractId(detachedCriteria1);
            mav.addObject("changeInvoicePlanModels", changeInvoicePlanModels);

            //草稿状态的发票金额
            DetachedCriteria detachedCriteria2 = DetachedCriteria.forClass(SalesInvoicePlanModel.class);
            detachedCriteria2.add(Restrictions.eq(SalesInvoicePlanModel.CONTRACTID, contractId));
            detachedCriteria2.add(Restrictions.eq(SalesInvoicePlanModel.INVOICESTATUS, "CG"));
            List<SalesInvoicePlanModel> totalPlan = getService().getSalesInvoicePlanListByContractId(detachedCriteria2);
            Iterator<SalesInvoicePlanModel> plan = totalPlan.iterator();
            BigDecimal unAmount = new BigDecimal(0.00);
            while (plan.hasNext()) {
                SalesInvoicePlanModel s = plan.next();
                unAmount = unAmount.add(s.getInvoiceAmount());
            }
            mav.addObject("unAmount", unAmount);
            //没开过发票，老合同的
            DetachedCriteria detachedCriteria3 = DetachedCriteria.forClass(SalesInvoicePlanModel.class);
            detachedCriteria3.add(Restrictions.eq(SalesInvoicePlanModel.CONTRACTID, contractId));
            List<SalesInvoicePlanModel> oldPlan = getService().getSalesInvoicePlanListByContractId(detachedCriteria3);
            if (oldPlan.size() == 0) {
                SalesContractModel s = salesContractService.get(contractId);
                mav.addObject("unAmount", s.getContractAmount());
            }
        }
        request.setAttribute("salesInvoicePlanModels", salesInvoicePlanModels);
        SalesContractModel salesContract = salesContractService.get(contractId);
        mav.addObject("model", salesContract);
        //把要申请的这部分金额加回到剩余金额中
        BigDecimal surplusAmount = surplusInvoiceAmount(salesContract).add(applyAmount);
        mav.addObject("surplusAmount", surplusAmount);
        request.setAttribute("isFlow", isFlow);

        String formDataJson = "";
        //拼凑出tableGrid所需的JSON数据

        //草稿状态的发票
        DetachedCriteria detachedCriteria12 = DetachedCriteria.forClass(SalesInvoicePlanModel.class);
        detachedCriteria12.add(Restrictions.eq(SalesInvoicePlanModel.CONTRACTID, contractId));
        detachedCriteria12.add(Restrictions.eq(SalesInvoicePlanModel.INVOICESTATUS, "CG"));
        List<SalesInvoicePlanModel> tableInvoicePlanModels = getService().getSalesInvoicePlanListByContractId(detachedCriteria12);

        formDataJson = tableGridDataUnit(tableInvoicePlanModels, "update");
        request.setAttribute("form", formDataJson);
        //已开票合同金额
        mav.addObject("amount1", getService().getAmountByContractId(contractId));
        //已申请合同金额
        mav.addObject("amount2", getService().getAmountByContractId(contractId, procInstId));

        return mav;
    }

    /**
     * <code>surplusInvoiceAmount</code>
     * 求剩余的可申请的发票金额
     * @param salesInvoicePlanModels
     * @param salesContract
     * @return
     * @since 2014年8月18日     3unshine
     */
    private BigDecimal surplusInvoiceAmount(SalesContractModel salesContract) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(SalesInvoicePlanModel.class);
        detachedCriteria.add(Restrictions.eq(SalesInvoicePlanModel.CONTRACTID, salesContract.getId()));
        detachedCriteria.add(Restrictions.ne(SalesInvoicePlanModel.INVOICESTATUS, "FQ"));
        detachedCriteria.add(Restrictions.ne(SalesInvoicePlanModel.INVOICESTATUS, "BG"));
        detachedCriteria.add(Restrictions.ne(SalesInvoicePlanModel.INVOICESTATUS, "CG"));
        List<SalesInvoicePlanModel> salesInvoicePlanModels = getService().getSalesInvoicePlanListByContractId(detachedCriteria);
        //求剩余的可申请的发票金额
        BigDecimal applyAmount = new BigDecimal(0.00);
        Iterator<SalesInvoicePlanModel> i = salesInvoicePlanModels.iterator();
        while (i.hasNext()) {
            SalesInvoicePlanModel s = i.next();
            BigDecimal invoiceAmount = applyAmount.add(s.getInvoiceAmount());
            applyAmount = invoiceAmount;
        }
        BigDecimal surplusAmount = salesContract.getContractAmount().subtract(applyAmount).setScale(2, BigDecimal.ROUND_HALF_UP);
        return surplusAmount;
    }

    /**
     * <code>getProInstLogList</code>
     * JSON异步获取审核日志信息
     * @param request
     * @param response
     * @return
     * @since 2014年8月19日     3unshine
     */
    @ResponseBody
    @RequestMapping(value = "/getProInstLogList")
    public Map<String, Object> getProInstLogList(HttpServletRequest request, HttpServletResponse response) {

        Map<String, Object> logs = new HashMap<String, Object>();
        if (!StringUtil.isEmpty(request.getParameter("procInstId"))) {
            long procInstId = request.getParameter("procInstId") != null ? Long.parseLong(request.getParameter("procInstId")) : null;
            //审批日志
            List<ProcInstAppr> proInstLogList = processService.findProcInstApprLog(procInstId);
            Set<String> userSet = processService.getUserIdSetByProcInst(procInstId, ProcessConstants.ROLE_ASSIGNEE);
            String name = null;
            Iterator<String> it = userSet.iterator();
            while (it.hasNext()) {
                String str = it.next();
                name = ((SysStaff) systemUserCache.get(str).getObjectValue()).getStaffName();
            }
            // List<Map<String, Object>> map = new ArrayList<Map<String,Object>>();
            logs.put("approName", name);
            logs.put("edit", proInstLogList);
            //map.add(logs);
        }

        return logs;
    }

    /**
     * <code>SalesInvoiceFlowSteps</code>
     * 用于流程控制所需的5个URL中标示的枚举类
     * @since   2014年7月22日    3unshine
     */
    public enum SalesInvoiceFlowSteps {
        //ZKSP  质控审批
        //CXTJ 重新提交
        //BMJLSP 部门经理审批
        //CWFH 财务复核
        //SHOW 查看权限
        ZKSP, BMJLSP, CWFH, CXTJ, SHOW;
    }

    /**
     * <code>cachetFlowManage</code>
     * 用于流程控制所需的URL
     * @param request
     * @param response
     * @return
     * @since   2014年7月10日    3unshine
     */
    @RequestMapping(value = "/invoiceFlowManage")
    public ModelAndView invoiceFlowManage(HttpServletRequest request, HttpServletResponse response) {
        String flowStep = request.getParameter("flowStep");
        String taskId = request.getParameter("taskId");
        String procInstId = request.getParameter("procInstId");
        String isFlow = "isFlow";

        //        List<SalesInvoicePlanModel> SalesInvoicePlanModels = (List<SalesInvoicePlanModel>) processService.getVariable(taskId, "salesInvoicePlanList");
        List<SalesInvoicePlanModel> SalesInvoicePlanModels = getService().getInvoicePlanModelByProcessInstanceId(Long.parseLong(procInstId));
        request.setAttribute("salesInvoicePlanList", SalesInvoicePlanModels);
        if (SalesInvoicePlanModels.size() > 0) {
            request.setAttribute("contractId", SalesInvoicePlanModels.get(0).getSalesContractId());
        }
        request.setAttribute("taskId", taskId);
        request.setAttribute("procInstId", procInstId);
        request.setAttribute("isFlow", isFlow);
        request.setAttribute("flowStep", flowStep);
        switch (SalesInvoiceFlowSteps.valueOf(flowStep)) {
            case ZKSP:
                return applyDetail(request, response);
            case CXTJ:
                request.setAttribute("flowFlag", "CXTJ");
                return reApply(request, response);
            case BMJLSP:
                return applyDetail(request, response);
            case CWFH:
                return applyDetail(request, response);
            case SHOW:
                return applyDetail(request, response);
        }
        return null;
    }

    /**
     * <code>handleFlow</code>
     * 处理流程
     * @param request
     * @param response
     * @return
     * @since   2014年7月22日    3unshine
     */
    @SuppressWarnings("unchecked")
    @ResponseBody
    @RequestMapping(value = "/handleFlow")
    public String handleFlow(HttpServletRequest request, HttpServletResponse response, SalesContractModel salesContractModel) {
        //        ModelAndView mav = new ModelAndView("sales/contract/manage");
        long contractId = request.getParameter("id") == null ? null : Long.parseLong(request.getParameter("id"));
        String taskId = request.getParameter("taskId");
        String flowFlag = request.getParameter("flowFlag");
        long procInstId = request.getParameter("procInstId") != "" ? Long.parseLong(request.getParameter("procInstId")) : null;
        int isAgree = Integer.parseInt(request.getParameter("isAgree") == null ? "1" : request.getParameter("isAgree"));
        String remark = request.getParameter("remark");
        //得到要审批的发票计划
        List<SalesInvoicePlanModel> oldSalesInvoicePlanModels = (List<SalesInvoicePlanModel>) processService.getVariable(taskId, "salesInvoicePlanList");
        String allDate = request.getParameter("allDate");
        //创建者
        SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
        /*List<SalesInvoicePlanModel> salesInvoicePlanList = invoicePlanDataAnalyst(salesContractModel, systemUser);*/
        List<SalesInvoicePlanModel> salesInvoicePlanList = getService().doInvoiceSTR(String.valueOf(contractId), allDate);
        String invoiceState = request.getParameter("invoiceState");
        if (!StringUtil.isEmpty(invoiceState)) {
            Iterator<SalesInvoicePlanModel> it = salesInvoicePlanList.iterator();
            while (it.hasNext()) {
                SalesInvoicePlanModel s = it.next();
                if ("CXTJ".equals(invoiceState)) {
                    s.setInvoiceStatus("SH");
                }
                s.setProcessInstanceId(procInstId);
            }
        }
        //流程处理service
        try {
            getService().handleFlow(flowFlag, contractId, salesInvoicePlanList, oldSalesInvoicePlanModels, systemUser, procInstId, taskId, isAgree, remark);
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return FAIL;
        }
    }

    /**
     * <code>tableGridDataUnit</code>
     * 拼接tableGridData所需要的JSON数据
     * @return String
     * @since   2014年6月23日    3unshine
     */
    public String tableGridDataUnit(List<SalesInvoicePlanModel> salesInvoicePlanModels, String showType) {
        String formDataJson = "";
        Map<String, Object> tableModel = new HashMap<String, Object>(); //表格数据模型
        tableModel.put("name", "editGridName"); //表格名称
        List<HashMap<String, Object>> rowNames = new ArrayList<HashMap<String, Object>>(); //表格中的列数据

        for (int i = 0; i < addColumns.length; i++) {
            HashMap<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("name", addColumns[i]); //字段名称，用于保存数据
            dataMap.put("label", addColumnNames[i]);//字段在页面上的显示名称
            dataMap.put("type", addColumnTypes[i]); //表格中输入字段的类型，目前只支持string,date
            dataMap.put("url", addColumnUrl[i]);
            dataMap.put("width", columnWidths[i]);
            if (salesInvoicePlanModels != null && showType.equals("detail")) {
                dataMap.put("boolWrite", false); //表格中输入字段的类型，目前只支持string,date
            }
            rowNames.add(dataMap);
        }

        tableModel.put("fieldList", rowNames);
        List<HashMap<String, Object>> rowDatas = new ArrayList<HashMap<String, Object>>(); //表格总的数据，一行数据对应一个map
        if (salesInvoicePlanModels != null) {
            int rows = salesInvoicePlanModels.size();
            tableModel.put("defaultAmount", rows);//新建表格时，默认为0，如果是修改，应该表格数据的总行数。
            for (SalesInvoicePlanModel s : salesInvoicePlanModels) { //放入2行数据
                HashMap<String, Object> dataMap = new HashMap<String, Object>(); //因为先前在rowNames中定义表格有3列
                dataMap.put(addColumns[0], s.getInvoiceAmount().setScale(2));
                SimpleDateFormat formatter = new SimpleDateFormat(pattern);
                ;
                dataMap.put(addColumns[1], formatter.format(s.getInvoiceTime()));
                dataMap.put(addColumns[2], s.getInvoiceType());
                dataMap.put(addColumns[3], s.getRemark());
                dataMap.put(addColumns[4], s.getId());
                rowDatas.add(dataMap);
            }
        } else {
            tableModel.put("defaultAmount", 0);//新建表格时，默认为0，如果是修改，应该表格数据的总行数。
        }

        tableModel.put("dataList", rowDatas);
        ObjectMapper objectMapper = JacksonJsonMapper.getInstance();
        try {
            formDataJson = objectMapper.writeValueAsString(tableModel);
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return formDataJson;
    }

    /**
     * @param salesContractModel
     * @param systemUser
     * @return
     */
    @SuppressWarnings("unchecked")
    private List<SalesInvoicePlanModel> invoicePlanDataAnalyst(SalesContractModel salesContractModel, SystemUser systemUser) {
        List<SalesInvoicePlanModel> invoicePlans = new ArrayList<SalesInvoicePlanModel>();
        ObjectMapper objectMapper = JacksonJsonMapper.getInstance();
        Map<String, Object> tableDataMap = null;
        String tableData = salesContractModel.getTableData();
        if (tableData != null && tableData != "") {
            try {
                tableDataMap = objectMapper.readValue(tableData, HashMap.class);
            } catch (JsonParseException e) {
                e.printStackTrace();
            } catch (JsonMappingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (tableDataMap != null && tableDataMap.size() > 0) {
                for (String key : tableDataMap.keySet()) {
                    List<Object> gridDataList = (List<Object>) tableDataMap.get(key);
                    List<Map<String, Object>> gridList = new ArrayList<Map<String, Object>>();
                    for (int i = 0; (gridDataList != null && i < gridDataList.size()); i++) {//加了非空判断
                        Map<String, Object> map = CodeUtils.stringToMap(gridDataList.get(i).toString());//格式转换
                        System.out.println(map.size());
                        if (map.size() != 0) {
                            gridList.add(map);
                        }
                    }
                    for (int i = 0; i < gridList.size(); i++) {
                        Map<String, Object> map = gridList.get(i);
                        String invoiceTimeString = map.get("invoiceTime").toString();
                        Date invoiceTimeDate = new Date();
                        if (invoiceTimeString.indexOf(":") > 0) {
                            invoiceTimeDate = DateUtils.convertStringToDate(invoiceTimeString, pattern);
                        } else {
                            invoiceTimeDate = DateUtils.convertStringToDate(invoiceTimeString, DatePattern);
                        }
                        int invoiceType = Integer.parseInt(map.get("invoiceType").toString());
                        BigDecimal invoiceAmount = new BigDecimal(map.get("invoiceAmount").toString());
                        String remark = map.get("remark") == null ? "" : map.get("remark").toString();
                        SalesInvoicePlanModel salesInvoicePlanModel = new SalesInvoicePlanModel();
                        long id = IdentifierGeneratorImpl.generatorLong();
                        salesInvoicePlanModel.setId(id);
                        salesInvoicePlanModel.setSalesContractId(salesContractModel.getId());
                        salesInvoicePlanModel.setSalesContractName(salesContractModel.getContractName());
                        salesInvoicePlanModel.setCreateTime(new Date());
                        salesInvoicePlanModel.setCreator(Long.parseLong(systemUser.getUserId()));
                        salesInvoicePlanModel.setInvoiceTime(invoiceTimeDate);
                        salesInvoicePlanModel.setInvoiceType(invoiceType);
                        salesInvoicePlanModel.setInvoiceAmount(invoiceAmount);
                        salesInvoicePlanModel.setRemark(remark);
                        salesInvoicePlanModel.setInvoiceStatus(SalesContractConstant.CONTRACT_STATE_SH);
                        invoicePlans.add(salesInvoicePlanModel);
                    }
                }
            }
        }
        return invoicePlans;
    }

    /**
     * <code>manage</code>
     *
     * @param request
     * @param response
     * @return
     * @since   2014年5月26日    3unshine
     */
    @RequestMapping(value = "/manage")
    public ModelAndView manage(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("sales/invoice/manage");

        return mav;
    }

    /**
     * <code>getList</code>
     * 得到manage页面的列表
     * @param request
     * @param response
     * @return
     * @since   2014年5月26日    3unshine
     */
    @ResponseBody
    @RequestMapping(value = "/getList")
    public Map<String, Object> getList(HttpServletRequest request, HttpServletResponse response) {

        Map<String, Object> map = new HashMap<String, Object>();
        String iDisplayStart = request.getParameter("pageNo");
        String iDisplayLength = request.getParameter("pageSize");
        String sortorder = request.getParameter("sortorder");
        String sortname = request.getParameter("sortname");
        if (iDisplayStart != null) {
            this.pageNo = Integer.parseInt(iDisplayStart) - 1;
        }
        if (iDisplayLength != null) {
            this.pageSize = Integer.parseInt(iDisplayLength);
        }
        //        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(getModel().getClass());
        Map<String, String> paramMap = new HashMap<String, String>();
        buildSearch(request, paramMap);
        paramMap.put("sortorder", sortorder);
        paramMap.put("sortname", sortname);
        PaginationSupport paginationSupport = getService().getList(paramMap, this.pageNo * this.pageSize, this.pageSize);

        map.put("rows", paginationSupport.getItems());
        map.put("total", paginationSupport.getTotalCount());
        map.put("page", iDisplayStart);
        return map;
    }

    /**
     * <code>buildSearch</code>
     * 创建查询条件
     * @param detachedCriteria
     * @since   2014年5月26日     3unshine
     */
    public void buildSearch(HttpServletRequest request, Map<String, String> paramMap) {

        boolean b = false;
        SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
        List<SysPowerAction> actions = systemUser.getSysPowerActions();
        for (SysPowerAction sysPowerAction : actions) {
            if (sysPowerAction.getPowCode().equals("invoiceStaffSearch")) {
                b = true;
            }
        }
        if (!b) {
            paramMap.put("creator", systemUser.getUserId());
        } else {
            String creator = request.getParameter("creator");
            if (!StringUtil.isEmpty(creator)) {
                paramMap.put("creator", ((SysStaff) systemUserCache.get(creator).getObjectValue()).getStaffId());
            }
        }

        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        String minAmount = request.getParameter("minAmount");
        String creatorUse = request.getParameter("creatorUse");
        String contractName = request.getParameter("contractName");
        String orgId = request.getParameter("orgId");
        if (!StringUtil.isEmpty(startTime)) {
            paramMap.put("startTime", startTime);
        }
        if (!StringUtil.isEmpty(endTime)) {
            paramMap.put("endTime", endTime);
        }
        if (!StringUtil.isEmpty(minAmount)) {
            paramMap.put("minAmount", minAmount);
        }
        if (!StringUtil.isEmpty(creatorUse)) {
            //根据用户名查询得到用户id
            String userId = getService().findUserId(creatorUse);
            paramMap.put("creatorUse", userId);
            //System.out.println(creatorUse + "----" + userId);
        }
        if (!StringUtil.isEmpty(contractName)) {
            paramMap.put("contractName", contractName);
        }
        if (!StringUtil.isEmpty(orgId)) {
            paramMap.put("orgId", orgId);
        }

    }

    /**
     * <code>search</code>
     * 搜索页面
     * @param request
     * @param response
     * @return
     * @since   2014年5月26日    3unshine
     */
    @RequestMapping("/search")
    public ModelAndView search(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("sales/invoice/search");

        SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
        List<SysPowerAction> actions = systemUser.getSysPowerActions();
        StringBuffer buffers = new StringBuffer();
        for (SysPowerAction sysPowerAction : actions) {
            buffers.append(sysPowerAction.getPowCode());
            buffers.append(",");
        }

        mav.addObject("powerActions", buffers.toString());

        return mav;
    }

    /**
     * <code>remove</code>
     *
     * @param request
     * @param response
     * @param id
     * @return
     * @since   2014-05-22    3unshine
     */
    @ResponseBody
    @RequestMapping(value = "/remove")
    public String remove(HttpServletRequest request, HttpServletResponse response, @RequestParam("id") String ids) {
        try {
            String[] id = ids.substring(0, ids.lastIndexOf(",")).split(",");
            getService().delete(id);
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return FAIL;
        }
    }

    /**
     * <code>getPrint</code>
     * 跳转到getPrint页面
     * @param request
     * @param response
     * @return
     * @since 2014年7月26日    wangya
     */
    @RequestMapping(value = "/getPrint")
    public ModelAndView getPrint(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("sales/invoice/applyPrint");
        Long id = Long.parseLong(request.getParameter("contractId"));
        SalesInvoicePlanModel invoicePlan = getService().get(id);

        SalesContractModel contract = salesContractService.get(invoicePlan.getSalesContractId());
        mav.addObject("contract", contract);

        BigDecimal amount = alreadyInvoiceAmount(contract, invoicePlan);
        mav.addObject("conAmount", amount);
        List<ProcInstAppr> proInstLogList = processService.findProcInstApprLog(invoicePlan.getProcessInstanceId());
        String keyCode = getService().findKeyCode(invoicePlan.getId());
        mav.addObject("keyCode", keyCode);

        StringBuilder sb = new StringBuilder();
        for (ProcInstAppr appr : proInstLogList) {
            sb.append(appr.getUser() + " ");
        }
        //审批人
        mav.addObject("approver", sb.toString());
        mav.addObject("model", invoicePlan);

        request.setAttribute("proInstId", invoicePlan.getProcessInstanceId());
        mav.addObject("taskId", getService().getTaskIdByProInst(invoicePlan.getProcessInstanceId()));
        //request.setAttribute("taskId", o);
        return mav;
    }

    /**
     * <code>alreadyInvoiceAmount</code>
     *
     * @param salesContract
     * @return
     * @since   2015年2月5日    guokemenng
     */
    private BigDecimal alreadyInvoiceAmount(SalesContractModel salesContract, SalesInvoicePlanModel invoicePlan) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(SalesInvoicePlanModel.class);
        detachedCriteria.add(Restrictions.eq(SalesInvoicePlanModel.CONTRACTID, salesContract.getId()));
        detachedCriteria.add(Restrictions.eq(SalesInvoicePlanModel.INVOICESTATUS, "TGSH"));
        List<SalesInvoicePlanModel> salesInvoicePlanModels = getService().getSalesInvoicePlanListByContractId(detachedCriteria);
        //求剩余的可申请的发票金额
        BigDecimal applyAmount = new BigDecimal(0.00);
        Iterator<SalesInvoicePlanModel> i = salesInvoicePlanModels.iterator();
        while (i.hasNext()) {
            SalesInvoicePlanModel s = i.next();
            BigDecimal invoiceAmount = applyAmount.add(s.getInvoiceAmount());
            applyAmount = invoiceAmount;
        }
        if ("TGSH".equals(invoicePlan.getInvoiceStatus())) {
            BigDecimal surplusAmount = applyAmount.subtract(invoicePlan.getInvoiceAmount()).setScale(2, BigDecimal.ROUND_HALF_UP);
            return surplusAmount;
        } else {
            return applyAmount;
        }
    }

    /**
     * 导出发票信息到excel表格中
     * @param request
     * @param response
     */
    @RequestMapping("/exportInvoice")
    public void exportInvoiceInfo(HttpServletRequest request, HttpServletResponse response) {

        Map<String, String> paramMap = new HashMap<String, String>();
        //构造查询参数到HashMap集合中
        buildSearch(request, paramMap);
        List<SalesInvoiceExportDto> invoiceExportList = getService().exportInvoice(paramMap);

        //excel表格标题信息
        List<String> titles = new ArrayList<String>();
        titles.add("合同编号");
        titles.add("合同名称");
        titles.add("合同金额");
        titles.add("已开发票金额");
        titles.add("本次开发票金额");
        titles.add("部门");
        titles.add("客户经理");
        titles.add("开发票日期");

        //表格头每个字段对应的传输对象中的属性名称
        List<String> fields = new ArrayList<String>();
        fields.add("contractCode");
        fields.add("contractName");
        fields.add("contractAmount");
        fields.add("alreadyAmount");
        fields.add("invoiceAmount");
        fields.add("deptName");
        fields.add("creatorName");
        fields.add("createTime");
        Workbook wb = ExportInvoiceUtils.buildList(titles, invoiceExportList, fields);
        FusionChartUtil.renderExcel(response, wb, "invoice.xls");
    }

}
