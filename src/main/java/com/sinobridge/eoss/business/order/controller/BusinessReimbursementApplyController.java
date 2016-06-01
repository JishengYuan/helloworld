/*
 * FileName: BusinessReimbursementApplyController.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.business.order.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sinobridge.base.core.controller.DefaultBaseController;
import com.sinobridge.base.core.utils.JacksonJsonMapper;
import com.sinobridge.base.core.utils.PaginationSupport;
import com.sinobridge.eoss.base.customermanage.model.CustomerInfoModel;
import com.sinobridge.eoss.base.customermanage.service.CustomerInfoService;
import com.sinobridge.eoss.bpm.service.ProcInstAppr;
import com.sinobridge.eoss.bpm.service.process.ProcessService;
import com.sinobridge.eoss.business.order.model.BusinessOrderModel;
import com.sinobridge.eoss.business.order.model.BusinessReimbursementApply;
import com.sinobridge.eoss.business.order.model.BusinessReimbursementModel;
import com.sinobridge.eoss.business.order.service.BusinessOrderService;
import com.sinobridge.eoss.business.order.service.BusinessReimbursementApplyService;
import com.sinobridge.eoss.business.order.utils.MoneyFormatUtil;
import com.sinobridge.eoss.business.suppliermanage.model.SupplierInfoModel;
import com.sinobridge.eoss.sales.contract.model.SalesContractModel;
import com.sinobridge.eoss.sales.contract.service.SalesContractService;
import com.sinobridge.eoss.salesfundsCost.model.FundsSalesBusiReimbursement;
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
 * 2014年12月16日 上午10:49:21          tigq        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Controller
@RequestMapping("/business/rbmApply")
public class BusinessReimbursementApplyController extends DefaultBaseController<BusinessReimbursementApply, BusinessReimbursementApplyService> {

    @Autowired
    private SalesContractService salesContractService;
    @Autowired
    private BusinessOrderService businessOrderService;
    @Autowired
    private ProcessService processService;
    @Autowired
    private SysStaffService sysStaffService;
    @Autowired
    private CustomerInfoService customerInfoService;

    private final String columns[] = { "orderCode", "supplierShortName", "supplierId", "unplanAmount", "invoiceAmount", "invoiceNum", "invoiceDate", "coursesType", "orderId", "remark", "taxRateType" };

    /**报销页面
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/manage")
    public ModelAndView manage(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("business/reimbursementApply/manage");
        return mav;
    }

    /**
     * 查询条件
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/search")
    public ModelAndView search(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("business/reimbursementApply/search");
        return mav;
    }

    /**报销列表
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getList")
    public Map<String, Object> getList(HttpServletRequest request, HttpServletResponse response) {
        SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
        Map<String, Object> map = new HashMap<String, Object>();
        String pageNo = request.getParameter("pageNo");
        String pageSize = request.getParameter("pageSize");
        if (pageNo != null) {
            this.pageNo = Integer.parseInt(pageNo) - 1;
        }
        if (pageSize != null) {
            this.pageSize = Integer.parseInt(pageSize);
        }
        /* DetachedCriteria detachedCriteria = DetachedCriteria.forClass(getModel().getClass());
         detachedCriteria.addOrder(Order.desc("id"));*/
        HashMap<String, Object> searchMap = new HashMap<String, Object>();
        Builder_search(searchMap, request);
        searchMap.put("reimbursementUser", systemUser.getStaffName());
        PaginationSupport paginationSupport = getService().findPageByReim(searchMap, this.pageNo * this.pageSize, this.pageSize);
        map.put("rows", paginationSupport.getItems());
        map.put("total", paginationSupport.getTotalCount());
        map.put("page", pageNo);
        return map;
    }

    /**
     * @param searchMap
     * @param request
     */
    private void Builder_search(HashMap<String, Object> searchMap, HttpServletRequest request) {
        String reimbursementName = request.getParameter("reimbursementName");
        String reimbursementAmount = request.getParameter("reimbursementAmount");
        String status = request.getParameter("orderId");
        String supplierShortName = request.getParameter("supplierShortName");
        String amountPart = request.getParameter("amountPart");

        if (!StringUtil.isEmpty(status) && !status.equals(",")) {
            if (status.contains("1,2")) {

            } else if (status.contains("1")) {
                searchMap.put(BusinessReimbursementApply.REIMSTATUS, "2");
            } else if (status.contains("2")) {
                searchMap.put(BusinessReimbursementApply.REIMSTATUS, "10");
            } else {

            }
        } else {
            searchMap.put(BusinessReimbursementApply.REIMSTATUS, "2");
        }
        if (!StringUtil.isEmpty(reimbursementName)) {
            searchMap.put(BusinessReimbursementApply.REIMNAME, "%" + reimbursementName + "%");
        }
        if (!StringUtil.isEmpty(reimbursementAmount)) {
            searchMap.put(BusinessReimbursementApply.AMOUNT, reimbursementAmount);
        }
        if (!StringUtil.isEmpty(amountPart)) {
            searchMap.put("amountPart", amountPart);
        }
        if (!StringUtil.isEmpty(supplierShortName)) {
            searchMap.put("supplierShortName", supplierShortName);
        }

    }

    /**
     * 添加页面
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/add")
    public ModelAndView add(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("business/reimbursementApply/add");
        SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
        Map<String, String> applyinfo = new HashMap<String, String>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMdd");
        applyinfo.put("userName", systemUser.getStaffName());
        applyinfo.put("applyDate", format.format(new Date()));
        long startTime = getStartTime();
        long endTime = getEndTime();
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(BusinessReimbursementApply.class);
        detachedCriteria.add(Restrictions.ge(BusinessReimbursementApply.CREATETIME, new Date(startTime)));
        detachedCriteria.add(Restrictions.le(BusinessReimbursementApply.CREATETIME, new Date(endTime)));
        List<BusinessReimbursementApply> reimbur = getService().findOrderByCriteria(detachedCriteria);
        int serialNum = reimbur.size();//流水号
        serialNum = serialNum + 1;
        applyinfo.put("applyTitle", systemUser.getStaffName() + "报销发票" + format1.format(new Date()) + "-" + serialNum);
        mav.addObject("applyinfo", applyinfo);
        return mav;
    }

    private long getStartTime() {
        Calendar todayStart = Calendar.getInstance();
        todayStart.set(Calendar.HOUR, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);
        return todayStart.getTime().getTime();
    }

    private long getEndTime() {
        Calendar todayEnd = Calendar.getInstance();
        todayEnd.set(Calendar.HOUR, 23);
        todayEnd.set(Calendar.MINUTE, 59);
        todayEnd.set(Calendar.SECOND, 59);
        todayEnd.set(Calendar.MILLISECOND, 999);
        return todayEnd.getTime().getTime();
    }

    /**选择订单
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/selectOrderView")
    public ModelAndView selectOrderView(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("business/reimbursementApply/selectOrderView");
        String supplier = request.getParameter("supplierId");
        request.setAttribute("supplierId", supplier);
        return mav;
    }

    /**
     * 异步获得订单信息
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/getOrders")
    @ResponseBody
    public Map<String, Object> getOrders(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> pg = new HashMap<String, Object>();
        String params = request.getParameter("sEcho");
        String iDisplayStart = request.getParameter("iDisplayStart");
        String iDisplayLength = request.getParameter("iDisplayLength");
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(BusinessOrderModel.class);
        buildSearch_Order(request, detachedCriteria);
        PaginationSupport paginationSupport = getService().findPageByCriteria(detachedCriteria, Integer.parseInt(iDisplayStart), Integer.parseInt(iDisplayLength));
        pg.put("aaData", paginationSupport.getItems());
        pg.put("sEcho", params);
        pg.put("iTotalDisplayRecords", paginationSupport.getTotalCount());
        pg.put("iTotalRecords", paginationSupport.getTotalCount());

        return pg;
    }

    private void buildSearch_Order(HttpServletRequest request, DetachedCriteria detachedCriteria) {
        SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
        detachedCriteria.add(Restrictions.eq(BusinessOrderModel.CREATOR, systemUser.getStaffName()));
        detachedCriteria.add(Restrictions.eq(BusinessOrderModel.ORDERSTATUS, "TGSP"));
        detachedCriteria.add(Restrictions.ne(BusinessOrderModel.REIMSTATUS, "A"));
        String params = request.getParameter("supplierId");
        detachedCriteria.add(Restrictions.eq("supplierInfoModel.id", Long.parseLong(params)));
        String sSearch_0 = request.getParameter("sSearch_0");
        if (!StringUtil.isEmpty(sSearch_0)) {

        }
    }

    /**显示订单信息
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    //@RequestMapping(value = "/getContractData", produces = "application/json; charset=utf-8")
    @RequestMapping(value = "/getOrderData")
    public String getOrderData(HttpServletRequest request, HttpServletResponse response) {
        String form = "";
        String orderIds = request.getParameter("orderIds");
        BusinessOrderModel order = new BusinessOrderModel();
        List<BusinessOrderModel> orders = new ArrayList<BusinessOrderModel>();
        Map<String, Object> tableModel = new HashMap<String, Object>(); //表格数据模型
        tableModel.put("name", "editGridName"); //表格名称
        List<HashMap<String, Object>> rowDatas = new ArrayList<HashMap<String, Object>>(); //表格总的数据，一行数据对应一个map
        if (orderIds != null || orderIds != "") {
            String[] ids = orderIds.substring(0, orderIds.lastIndexOf(",")).split(",");
            for (String id : ids) {
                order = businessOrderService.get(Long.parseLong(id));
                orders.add(order);
            }
            Iterator<BusinessOrderModel> it = orders.iterator();
            while (it.hasNext()) {
                HashMap<String, Object> dataMap = new HashMap<String, Object>(); //因为先前在rowNames中定义表格有3列
                BusinessOrderModel s = it.next();
                BigDecimal unplanAmount = new BigDecimal(0.00);
                Set<BusinessReimbursementModel> reim = s.getBusinessReimbursementModel();
                Iterator<BusinessReimbursementModel> p = reim.iterator();
                while (p.hasNext()) {
                    BusinessReimbursementModel pl = p.next();
                    unplanAmount = unplanAmount.add(pl.getAmount());
                }
                unplanAmount = s.getOrderAmount().subtract(unplanAmount);
                unplanAmount = unplanAmount.subtract(new BigDecimal(s.getSpotNum())).setScale(2, java.math.BigDecimal.ROUND_HALF_UP);
                List<Map<String, Object>> sales = businessOrderService.findSalesByOrderId(s.getId());//合同
                List<Map<String, Object>> neicai = businessOrderService.findNeicaiByOrderId(s.getId());//内采
                dataMap.put(columns[0], s.getOrderCode());
                dataMap.put(columns[1], s.getSupplierShortName());
                dataMap.put(columns[2], s.getSupplierInfoModel().getId());
                dataMap.put(columns[3], unplanAmount);
                dataMap.put(columns[4], "0.0");
                dataMap.put(columns[5], "0");
                dataMap.put(columns[6], "");
                dataMap.put(columns[7], "");
                dataMap.put(columns[8], s.getId());
                dataMap.put(columns[10], "");
                if (sales.size() > 0) {
                    dataMap.put("sales", sales);
                } else {
                    dataMap.put("neicai", neicai);
                }

                rowDatas.add(dataMap);
            }
        }
        tableModel.put("defaultAmount", 0);//新建表格时，默认为0，如果是修改，应该表格数据的总行数。
        tableModel.put("dataList", rowDatas);
        ObjectMapper objectMapper = JacksonJsonMapper.getInstance();
        try {
            form = objectMapper.writeValueAsString(tableModel);
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return form;
    }

    /**保存报销
     * @param request
     * @param response
     * @param businessReimbursementApply
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/save")
    public Map<String, String> saverbmApply(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("_sino_eoss_cuotomer_addform") BusinessReimbursementApply businessReimbursementApply) {
        Map<String, String> rsmsg = new HashMap<String, String>();
        try {

            SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
            String amountReim = request.getParameter("amountReim");
            BigDecimal amount = new BigDecimal(amountReim);
            businessReimbursementApply.setAmount(amount);
            String tableData = request.getParameter("tableGridData");//订单报表发票数据
            String salesRbmData = request.getParameter("salesRbmData");//每个订单对应合同报销发票数据
            String rs = getService().saverbmApply(businessReimbursementApply, systemUser, tableData, salesRbmData);
            if (!rs.equals("true")) {
                rsmsg.put("result", FAIL);
                rsmsg.put("message", "报销申请提交出错！");
                return rsmsg;
            }
            rsmsg.put("result", SUCCESS);
            return rsmsg;

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            rsmsg.put("result", FAIL);
            rsmsg.put("message", "报销申请提交出错!");
            return rsmsg;
        }
    }

    /**
     * <code>doUpdateOrder</code>
     * 保存更新实体
     * @param request
     * @param response
     * @param payOrder
     * @return
     * @since   2014年12月16日    wangya
     */
    @ResponseBody
    @RequestMapping(value = "/doUpdate")
    public Map<String, String> doUpdate(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("_sino_eoss_cuotomer_addform") BusinessReimbursementApply reimburs, Errors error) {
        Map<String, String> rsmsg = new HashMap<String, String>();
        try {
            if (error.hasErrors()) {
                List<?> l = error.getAllErrors();
                rsmsg.put("result", FAIL);
                return rsmsg;
            }
            SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
            String tableData = request.getParameter("tableGridData");
            String taskId = request.getParameter("taskId");
            String rs = getService().doUpdate(reimburs, tableData, taskId, systemUser);
            if (!rs.equals("ture")) {
                rsmsg.put("result", FAIL);
                rsmsg.put("message", "[" + rs + "]设备数量填写，大于合同中的数量！");
                return rsmsg;
            }
            rsmsg.put("result", SUCCESS);
            return rsmsg;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            rsmsg.put("result", FAIL);
            rsmsg.put("message", "提交出错!");
            return rsmsg;
        }
    }

    /**
     * <code>detail</code>
     * 详情
     * @param request
     * @param response
     * @return
     * @since   2014年12月16日    wangya
     */
    @RequestMapping(value = "/detail")
    public ModelAndView detail(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("business/reimbursementApply/detail");
        BusinessReimbursementApply reim = new BusinessReimbursementApply();
        String id = request.getParameter("id");
        String taskId = request.getParameter("taskId");
        if (id != null) {
            reim = getService().get(Long.parseLong(id));
        }
        if (taskId != null) {
            request.setAttribute("taskId", taskId);
            long reimId = (Long) processService.getVariable(taskId, "reimId");
            reim = getService().get(reimId);
        }
        List<BusinessReimbursementModel> reimbursementModes = reim.getReimbursementModes();
        for (BusinessReimbursementModel businessReimbursementModel : reimbursementModes) {
            List<FundsSalesBusiReimbursement> fundsSalesBusiReimbursements = businessReimbursementModel.getFundsSalesBusiReimbursements();
            List<Map<String, String>> contractRbm = new ArrayList<Map<String, String>>();
            if (fundsSalesBusiReimbursements.size() > 0) {
                for (FundsSalesBusiReimbursement fundsSalesBusiReimbursement : fundsSalesBusiReimbursements) {
                    SalesContractModel salesContractModel = salesContractService.get(fundsSalesBusiReimbursement.getSalesContractId());

                    /*使用变量，根据报销金额，合同编号，确认是否需要插入数据*/
                    double rbmAmount = fundsSalesBusiReimbursement.getContractBusiReimbursement().doubleValue();
                    double rbmAmount2 = businessReimbursementModel.getAmount().doubleValue();
                    int isTrue = 0;
                    for (Map<String, String> mapStr : contractRbm) {
                        String contractCodeMap = mapStr.get("contractCode");
                        if (contractCodeMap.equals(salesContractModel.getContractCode())) {
                            isTrue = 1;
                        }
                    }

                    if (isTrue == 0 && rbmAmount <= rbmAmount2) {
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("contractCode", salesContractModel.getContractCode());
                        map.put("contractName", salesContractModel.getContractName());
                        map.put("creatorName", salesContractModel.getCreatorName());
                        map.put("contractAmount", salesContractModel.getContractAmount().toString());
                        map.put("rbmAmount", fundsSalesBusiReimbursement.getContractBusiReimbursement().toString());
                        contractRbm.add(map);
                    }
                }
            } else {
                contractRbm = getService().getInterPurse(businessReimbursementModel.getId());
            }
            businessReimbursementModel.setContractReimbursements(contractRbm);
        }
        /*List<BusinessReimbursementModel> r= reim.getReimbursementModes();
        Iterator<BusinessReimbursementModel> p = r.iterator();
        String invoiceType = "";
        while(p.hasNext()){
        	BusinessReimbursementModel pl = p.next();
        	invoiceType = pl.getInvoiceType();
        }
        request.setAttribute("invoiceType", invoiceType);//发票类型*/
        List<ProcInstAppr> proInstLogList = processService.findProcInstApprLog(reim.getProcessInstanceId());
        mav.addObject("proInstLogList", proInstLogList);
        mav.addObject("model", reim);
        mav.addObject("isPrint", getService().print(reim.getProcessInstanceId()));
        return mav;

    }

    /**
     * <code>update</code>
     * 重新提交页面
     * @param request
     * @param response
     * @return
     * @since   2014年12月16日    wangya
     */
    @RequestMapping(value = "/update")
    private ModelAndView update(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("business/reimbursementApply/update");
        String form = "";
        BusinessReimbursementApply reim = new BusinessReimbursementApply();
        String taskId = request.getParameter("taskId");
        if (taskId != null) {
            request.setAttribute("taskId", taskId);
            long payId = (Long) processService.getVariable(taskId, "reimId");
            reim = getService().get(payId);
        }
        List<BusinessReimbursementModel> r = reim.getReimbursementModes();
        List<HashMap<String, Object>> listMap = new ArrayList<HashMap<String, Object>>();
        BigDecimal unplanAmount = new BigDecimal(0.00);
        BigDecimal amo = new BigDecimal(0.00);
        Iterator<BusinessReimbursementModel> p = r.iterator();
        while (p.hasNext()) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            BusinessReimbursementModel pl = p.next();
            BusinessOrderModel order = pl.getBusinessOrder();
            amo = getService().findReimAmount(order.getId());//此订单的计划金额
            unplanAmount = pl.getAmount();
            unplanAmount = amo.subtract(unplanAmount);
            unplanAmount = order.getOrderAmount().subtract(unplanAmount);
            map.put(columns[0], pl.getOrderCode());
            map.put(columns[1], pl.getSupplierShortName());
            map.put(columns[2], order.getSupplierInfoModel().getId());
            map.put(columns[3], unplanAmount);
            map.put(columns[4], pl.getAmount());
            map.put(columns[5], pl.getNumber());
            map.put(columns[6], pl.getCreateTime());
            map.put(columns[7], pl.getCoursesType());
            map.put(columns[8], order.getId());
            map.put(columns[9], pl.getRemark());
            map.put(columns[10], pl.getInvoiceType());
            listMap.add(map);
        }
        request.setAttribute("listMap", listMap);
        mav.addObject("model", reim);
        return mav;
    }

    /**
     * <code>reimFlowManage</code>
     * 用于流程控制所需的URL
     * @param request
     * @param response
     * @return
     * @since   2014年12月19日    wangya
     */
    @RequestMapping(value = "/reimFlowManage")
    public ModelAndView reimFlowManage(HttpServletRequest request, HttpServletResponse response) {
        String flowStep = request.getParameter("flowStep");
        String taskId = request.getParameter("taskId");
        String procInstId = request.getParameter("procInstId");
        String isFlow = "isFlow";
        if (!taskId.equals("undefined")) {
            request.setAttribute("taskId", taskId);
            Long payOrder = (Long) processService.getVariable(taskId, "payModelId");
            request.setAttribute("id", payOrder);
        }
        request.setAttribute("procInstId", procInstId);

        request.setAttribute("isFlow", isFlow);
        request.setAttribute("flowStep", flowStep);
        switch (FlowSteps.valueOf(flowStep)) {
            case CXTJ:
                System.out.println("CXTJ");
                return update(request, response);
            case SWJLSP:
                System.out.println("SWJLSP");
                return detail(request, response);
            case SWZGSP:
                System.out.println("SWZGSP");
                return detail(request, response);
            case SWFZCSP:
                System.out.println("SWFZCSP");
                return detail(request, response);
            case ZCSP:
                System.out.println("ZCSP");
                return detail(request, response);
            case CWFH:
                System.out.println("CWFH");
                return detail(request, response);
            case SHOW:
                System.out.println("SHOW");
                return reimFinish(request, response);
            case END:
                return null;
        }
        return null;
    }

    /**
     * <code>FlowSteps</code>
     * 用于流程控制所需的7个URL中标示的枚举类
     * @since   2014年12月16日    wangya
     */
    public enum FlowSteps {
        //STATR 开始
        //SWJLSP 商务经理审批
        //CXTJ 重新提交
        //SWZGSP  商务主管审批
        //SWFZCSP 商务副总裁审批
        //ZCSP总裁审批
        //CWFH 财务复核
        //SHOW 查看权限
        //END 结束
        SWJLSP, SWZGSP, SWFZCSP, ZCSP, CWFH, CXTJ, SHOW, END;
    }

    /**
     * <code>handleFlow</code>
     * 处理流程
     * @param request
     * @param response
     * @return
     * @since   2014年12月19日    wangya
     */
    @RequestMapping(value = "/handleFlow")
    public ModelAndView handleFlow(HttpServletRequest request, HttpServletResponse response, BusinessReimbursementApply reimburs) {
        String flowStep = request.getParameter("flowStep");
        String taskId = request.getParameter("taskId");
        int isAgree = Integer.parseInt(request.getParameter("isAgree") == null ? "1" : request.getParameter("isAgree"));
        String remark = request.getParameter("remark");
        //创建者
        SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);

        //流程处理service
        getService().handleFlow(flowStep, systemUser, reimburs, taskId, isAgree, remark);
        ModelAndView mav = new ModelAndView("business/reimbursementApply/manage");
        return mav;
    }

    /**
     * <code>costTaskList</code>
     * 得到待报销审核列表
     * @param request
     * @param response
     * @return
     * @since   2014年12月20日    wangya
     */
    @ResponseBody
    @RequestMapping(value = "/costTaskList")
    public Map<String, Object> costTaskList(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();
        String pageNo = request.getParameter("pageNo");
        String pageSize = request.getParameter("pageSize");
        String sortname = request.getParameter("sortname");
        String sortorder = request.getParameter("sortorder");
        if (pageNo != null) {
            this.pageNo = Integer.parseInt(pageNo) - 1;
        }
        if (pageSize != null) {
            this.pageSize = Integer.parseInt(pageSize);
        }
        //DetachedCriteria detachedCriteria = DetachedCriteria.forClass(getModel().getClass());
        Map<String, Object> search = new HashMap<String, Object>();
        buildSearch_payTask(request, search);
        search.put("sortname", sortname);
        search.put("sortorder", sortorder);
        PaginationSupport paginationSupport = getService().reimTaskList(search, this.pageNo * this.pageSize, Integer.parseInt(pageNo) * this.pageSize);

        map.put("rows", paginationSupport.getItems());
        map.put("total", paginationSupport.getTotalCount());
        map.put("page", pageNo);
        return map;
    }

    public void buildSearch_payTask(HttpServletRequest request, Map<String, Object> search) {
        SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
        search.put("systemUser", systemUser.getUserName());
        String reimbursementName = request.getParameter("reimbursementName");
        String amount = request.getParameter("amount");
        if (!StringUtil.isEmpty(reimbursementName)) {
            search.put(BusinessReimbursementApply.REIMNAME, reimbursementName);
        }
        if (!StringUtil.isEmpty(amount)) {
            search.put(BusinessReimbursementApply.AMOUNT, amount);
        }

    }

    /**
     * 查询条件
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/reimSearch")
    public ModelAndView reimSearch(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("business/costTask/reimTaskSearch");
        return mav;
    }

    /**
    * <code>endpay</code>
    * 删除报销单
    * @param request
    * @param response
    * @return
    * @since   2014年12月20日    wangya
    */
    @ResponseBody
    @RequestMapping(value = "/endreim")
    public String endreim(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("_sino_eoss_cuotomer_addform") BusinessReimbursementApply reimApply) {
        try {
            SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
            String taskId = request.getParameter("taskId");
            getService().endreim(taskId, reimApply, systemUser);
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return FAIL;
        }
    }

    /**
     * <code>reimFinish</code>
     * 详情
     * @param request
     * @param response
     * @return
     * @since   2014年12月16日    wangya
     */
    @RequestMapping(value = "/reimFinish")
    public ModelAndView reimFinish(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("business/reimbursementApply/reimFinish");
        BusinessReimbursementApply reim = new BusinessReimbursementApply();
        String flowStep = request.getParameter("flowStep");
        String procInstId = request.getParameter("procInstId");
        if (procInstId != null) {
            reim = getService().findReimId(procInstId);
        }
        mav.addObject("flowStep", flowStep);
        List<ProcInstAppr> proInstLogList = processService.findProcInstApprLog(reim.getProcessInstanceId());//审批日志
        mav.addObject("proInstLogList", proInstLogList);
        mav.addObject("model", reim);
        return mav;

    }

    /**
     * <code>printReim</code>
     * 打印报销单页面
     * @param request
     * @param response
     * @return
     * @since 2014年12月20日    wangya
     */
    @RequestMapping(value = "/printReim")
    public ModelAndView printReim(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("business/reimbursementApply/printReim");
        Long id = Long.parseLong(request.getParameter("id"));
        BusinessReimbursementApply reim = getService().get(id);
        //总额大写
        String amount = reim.getAmount().toString();
        String capslk = MoneyFormatUtil.getChnmoney(amount);
        mav.addObject("capitalMoney", capslk);
        String org = getService().findSatff(reim.getReimbursementUser());//报销人组织
        mav.addObject("org", org);
        List<ProcInstAppr> proInstLogList = processService.findProcInstApprLog(reim.getProcessInstanceId());//审批日志
        StringBuilder sb = new StringBuilder();
        for (ProcInstAppr appr : proInstLogList) {
            sb.append(appr.getUser() + " ");//所有审批人 
        }
        List<BusinessReimbursementModel> p = reim.getReimbursementModes();
        /*********增加合同信息**************/
        //   List<BusinessReimbursementModel> reimbursementModes = reim.getReimbursementModes();
        for (BusinessReimbursementModel businessReimbursementModel : p) {
            List<FundsSalesBusiReimbursement> fundsSalesBusiReimbursements = businessReimbursementModel.getFundsSalesBusiReimbursements();
            List<Map<String, String>> contractRbm = new ArrayList<Map<String, String>>();
            if (fundsSalesBusiReimbursements.size() > 0) {
                for (FundsSalesBusiReimbursement fundsSalesBusiReimbursement : fundsSalesBusiReimbursements) {
                    SalesContractModel salesContractModel = salesContractService.get(fundsSalesBusiReimbursement.getSalesContractId());
                    CustomerInfoModel customer = customerInfoService.get(salesContractModel.getCustomerId());

                    /*使用变量，根据报销金额，合同编号，确认是否需要插入数据*/
                    double rbmAmount = fundsSalesBusiReimbursement.getContractBusiReimbursement().doubleValue();
                    double rbmAmount2 = businessReimbursementModel.getAmount().doubleValue();
                    int isTrue = 0;
                    for (Map<String, String> mapStr : contractRbm) {
                        String contractCodeMap = mapStr.get("contractCode");
                        if (contractCodeMap.equals(salesContractModel.getContractCode())) {
                            isTrue = 1;
                        }
                    }

                    if (isTrue == 0 && rbmAmount <= rbmAmount2) {
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("custormer", customer.getShortName());
                        map.put("contractCode", salesContractModel.getContractCode());
                        map.put("contractName", salesContractModel.getContractName());
                        map.put("creatorName", salesContractModel.getCreatorName());
                        map.put("contractAmount", salesContractModel.getContractAmount().toString());
                        map.put("rbmAmount", fundsSalesBusiReimbursement.getContractBusiReimbursement().toString());
                        contractRbm.add(map);
                    }
                }
            } else {
                contractRbm = getService().getInterPurse(businessReimbursementModel.getId());//内采
            }
            businessReimbursementModel.setContractReimbursements(contractRbm);
        }
        /**********增加合同信息end*************/

        Iterator<BusinessReimbursementModel> q = p.iterator();
        int num = 0;
        while (q.hasNext()) {
            BusinessReimbursementModel re = q.next();
            num += re.getNumber();
        }
        mav.addObject("num", num);
        SupplierInfoModel suInfo = p.get(0).getSupplierInfo();
        String type = p.get(0).getCoursesType();
        mav.addObject("taskId", getService().getTaskIdByProInst(reim.getProcessInstanceId()));
        mav.addObject("proInstId", reim.getProcessInstanceId() + "");
        mav.addObject("suInfo", suInfo);
        mav.addObject("plan", p);
        mav.addObject("type", type);
        //审批人
        mav.addObject("approver", sb.toString());
        mav.addObject("model", reim);
        return mav;
    }

    /**
     * 报销单列表
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getAllReim")
    public Map<String, Object> getAllPay(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();
        String pageNo = request.getParameter("pageNo");
        String pageSize = request.getParameter("pageSize");
        if (pageNo != null) {
            this.pageNo = Integer.parseInt(pageNo) - 1;
        }
        if (pageSize != null) {
            this.pageSize = Integer.parseInt(pageSize);
        }
        Map<String, Object> searchMap = new HashMap<String, Object>();
        buildSearch_allPay(request, searchMap);
        PaginationSupport paginationSupport = getService().getModelPage(searchMap, this.pageNo * this.pageSize, this.pageSize);

        map.put("rows", paginationSupport.getItems());
        map.put("total", paginationSupport.getTotalCount());
        map.put("page", pageNo);
        return map;
    }

    /**
     * @param request
     * @param detachedCriteria
     * @param searchMap 
     */
    private void buildSearch_allPay(HttpServletRequest request, Map<String, Object> searchMap) {
        //String supplierShortName = request.getParameter("supplierShortName");
        String startTime = request.getParameter("startdate");
        String endTime = request.getParameter("enddate");
        String minAmount = request.getParameter("reimminAmount");
        //String maxAmount = request.getParameter("reimmaxAmount");
        if (!StringUtil.isEmpty(startTime)) {
            searchMap.put("startTime", startTime);
        }
        if (!StringUtil.isEmpty(endTime)) {
            searchMap.put("endTime", endTime);
        }
        /* if (!StringUtil.isEmpty(supplierShortName)) {
             searchMap.put("supplierId", Long.parseLong(supplierShortName));
         }*/
        if (!StringUtil.isEmpty(minAmount)) {
            searchMap.put("minAmount", minAmount);
        }
        /*if (!StringUtil.isEmpty(maxAmount)) {
            searchMap.put("maxAmount", maxAmount);
        }*/
    }

    /**
     * <code>searchCostPay</code>
     * 查询页面
     * @param request
     * @param response
     * @return
     * @since   2014年12月15日    wangya
     */
    @RequestMapping(value = "/searchCostReim")
    public ModelAndView searchCostReim(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("business/order/costControl/searchReim");
        return mav;
    }

    /**
     * <code>finish</code>
     * 我的订单页面报销详情查看
     * @param request
     * @param response
     * @return
     * @since 2015.12.21   wangya
     */
    @RequestMapping(value = "/finish")
    public ModelAndView finish(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("business/reimbursement/finish");
        String id = request.getParameter("id");
        BigDecimal sum = new BigDecimal(0.00);
        BusinessOrderModel orderModel = new BusinessOrderModel();
        if (id != null && id != "") {
            orderModel = businessOrderService.get(Long.parseLong(id));
            Set<BusinessReimbursementModel> reim = orderModel.getBusinessReimbursementModel();
            request.setAttribute("reim", reim);
            List<BusinessReimbursementApply> reimApplys = getService().getReimApplys(orderModel.getId());//查询发票申请相关信息
            request.setAttribute("reimApplys", reimApplys);
        }
        sum = orderModel.getOrderAmount().subtract(getService().getReimAmount(id));
        request.setAttribute("orderDueAmnount", sum);
        mav.addObject("model", orderModel);
        return mav;
    }
}
