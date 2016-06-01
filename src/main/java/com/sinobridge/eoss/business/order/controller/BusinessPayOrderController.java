package com.sinobridge.eoss.business.order.controller;

import java.io.IOException;
import java.math.BigDecimal;
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
import com.sinobridge.eoss.bpm.service.ProcInstAppr;
import com.sinobridge.eoss.bpm.service.process.ProcessService;
import com.sinobridge.eoss.business.order.constant.BusinessOrderContant;
import com.sinobridge.eoss.business.order.model.BusinessOrderModel;
import com.sinobridge.eoss.business.order.model.BusinessPayOrderModel;
import com.sinobridge.eoss.business.order.model.BusinessPaymentPlanModel;
import com.sinobridge.eoss.business.order.service.BusinessOrderService;
import com.sinobridge.eoss.business.order.service.BusinessPayOrderService;
import com.sinobridge.eoss.business.order.utils.CodeUtils;
import com.sinobridge.eoss.business.order.utils.MoneyCurrencyChange;
import com.sinobridge.eoss.business.order.utils.MoneyFormatUtil;
import com.sinobridge.eoss.business.suppliermanage.model.SupplierInfoModel;
import com.sinobridge.eoss.business.suppliermanage.service.SupplierInfoService;
import com.sinobridge.systemmanage.model.SysStaff;
import com.sinobridge.systemmanage.service.SysStaffService;
import com.sinobridge.systemmanage.util.Global;
import com.sinobridge.systemmanage.util.StringUtil;
import com.sinobridge.systemmanage.vo.SystemUser;

/**
 * <p>
 * History: 
 *
 * Date                     Author         Version     Description
 * ---------------------------------------------------------------------------------
 * 2014年12月15日 上午10:27:56          wangya       1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Controller
@RequestMapping(value = "/business/payOrder")
public class BusinessPayOrderController extends DefaultBaseController<BusinessPayOrderModel, BusinessPayOrderService> {
    private final String columns[] = { "orderCode", "supplierShortName", "orderAmount", "payAmount", "amount", "credit", "orderId" };

    @Autowired
    private SupplierInfoService supplierInfoService;
    @Autowired
    private BusinessOrderService businessOrderService;
    @Autowired
    private ProcessService processService;
    @Autowired
    private SysStaffService sysStaffService;

    /**
     * <code>manage</code>
     *我的订单页面
     * @param request
     * @param response
     * @return
     * @since   2014年12月15日    wangya
     */
    @RequestMapping(value = "/manage")
    public ModelAndView manage(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("business/payOrder/manage");

        return mav;
    }

    /**
    * <code>getList</code>
    * 得到表格列表
    * @param request
    * @param response
    * @return
    * @since   2014年12月15日    wangya
    */
    @ResponseBody
    @RequestMapping(value = "/getTableGrid")
    public Map<String, Object> getList(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();
        String pageNo = request.getParameter("pageNo");
        String pageSize = request.getParameter("pageSize");
        if (pageNo != null) {
            this.pageNo = Integer.parseInt(pageNo) - 1;
        }
        if (pageSize != null) {
            this.pageSize = Integer.parseInt(pageSize);
        }
        // DetachedCriteria detachedCriteria = DetachedCriteria.forClass(getModel().getClass());
        HashMap<String, Object> searchMap = new HashMap<String, Object>();
        //detachedCriteria.addOrder(Order.desc("id"));
        buildSearch(request, searchMap);
        PaginationSupport paginationSupport = getService().findPayPage(searchMap, this.pageNo * this.pageSize, this.pageSize);
        BigDecimal amount = getService().totallA(searchMap);
        /*map.put("totallamount", amount);*/
        map.put("rows", paginationSupport.getItems());
        map.put("total", paginationSupport.getTotalCount());
        map.put("page", pageNo);
        return map;
    }

    public void buildSearch(HttpServletRequest request, HashMap<String, Object> searchMap) {

        SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
        searchMap.put(BusinessPayOrderModel.PAYAPPLYUSER, systemUser.userId);
        String closeTime = request.getParameter("closeTime");
        String amount = request.getParameter("amount");
        String status = request.getParameter("orderId");
        String supplierShortName = request.getParameter("supplierShortName");
        String amountPart = request.getParameter("amountPart");

        if (!StringUtil.isEmpty(status) && !status.equals(",")) {
            if (status.contains("1,2")) {

            } else if (status.contains("1")) {
                searchMap.put(BusinessPayOrderModel.PLANSTATUS, "2");
            } else if (status.contains("2")) {
                searchMap.put(BusinessPayOrderModel.PLANSTATUS, "10");
            } else {

            }
        } else {
            searchMap.put(BusinessPayOrderModel.PLANSTATUS, "2");
        }
        if (!StringUtil.isEmpty(closeTime)) {
            searchMap.put(BusinessPayOrderModel.CLOSETIME, closeTime);
        }
        if (!StringUtil.isEmpty(amount)) {
            BigDecimal payAmount = new BigDecimal(amount);
            searchMap.put(BusinessPayOrderModel.AMOUNT, payAmount);
        }
        if (!StringUtil.isEmpty(supplierShortName)) {
            searchMap.put("supplierShortName", supplierShortName);
        }
        if (!StringUtil.isEmpty(amountPart)) {
            BigDecimal amounts = new BigDecimal(amountPart);
            searchMap.put("amountPart", amounts);
        }

    }

    /**
     * <code>search</code>
     * 查询页面
     * @param request
     * @param response
     * @return
     * @since   2014年12月15日    wangya
     */
    @RequestMapping(value = "/search")
    public ModelAndView search(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("business/payOrder/search");
        return mav;
    }

    /**
     * <code>addPay</code>
     * 订单付款添加页面
     * @param request
     * @param response
     * @return
     * @since   2014年12月15日    wangya
     */
    @RequestMapping(value = "/addPay")
    public ModelAndView addPay(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("business/payOrder/addPay");
        SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(BusinessPayOrderModel.class);
        long startTime = getStartTime();
        long endTime = getEndTime();
        detachedCriteria.add(Restrictions.ge(BusinessPayOrderModel.CREATEDATE, new Date(startTime)));
        detachedCriteria.add(Restrictions.le(BusinessPayOrderModel.CREATEDATE, new Date(endTime)));
        List<BusinessPayOrderModel> businessPayOrderModel = getService().findByCriteria(detachedCriteria);
        int serialNum = businessPayOrderModel.size();//流水号
        serialNum = serialNum + 1;
        String payName = CodeUtils.creatCode(systemUser.getStaffName() + "付款申请") + "-" + serialNum;
        request.setAttribute("payName", payName);//付款申请名称

        Map<String, Object> tableModel = new HashMap<String, Object>(); //表格数据模型
        tableModel.put("name", "editGridName"); //表格名称
        List<HashMap<String, Object>> rowNames = new ArrayList<HashMap<String, Object>>(); //表格中的列数据
        tableModel.put("fieldList", rowNames);
        List<HashMap<String, Object>> rowDatas = new ArrayList<HashMap<String, Object>>(); //表格总的数据，一行数据对应一个map
        tableModel.put("defaultAmount", 0);//新建表格时，默认为0，如果是修改，应该表格数据的总行数。
        tableModel.put("dataList", rowDatas);
        ObjectMapper objectMapper = JacksonJsonMapper.getInstance();
        try {
            String form = objectMapper.writeValueAsString(tableModel);
            request.setAttribute("form", form);
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //添加当前美元汇率
        Map<String, String> currencyMap = MoneyCurrencyChange.getCurrentRate();
        request.setAttribute("currencyMap", currencyMap);
        return mav;
    }

    /**
     * <code>selectOrderView</code>
     *选订单页面
     * @param request
     * @param response
     * @return
     * @since   2014年12月15日    wangya
     */
    @ResponseBody
    @RequestMapping(value = "/selectOrderView")
    public ModelAndView selectOrderView(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("business/payOrder/selectOrderView");
        String supplier = request.getParameter("supplierId");
        String subject = request.getParameter("subject");
        request.setAttribute("subject", subject);
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
        detachedCriteria.add(Restrictions.ne(BusinessOrderModel.PAYSTATUS, "A"));
        String params = request.getParameter("supplierId");
        detachedCriteria.add(Restrictions.eq("supplierInfoModel.id", Long.parseLong(params)));
        String subject = request.getParameter("subject");
        if (subject.equals("5")) {
            detachedCriteria.add(Restrictions.isNotNull("attachIds"));
        }
        String sSearch_0 = request.getParameter("sSearch_0");
        if (!StringUtil.isEmpty(sSearch_0)) {
            String[] ss = sSearch_0.split("_");
            if (!ss[0].equals("searchCode")) {
                detachedCriteria.add(Restrictions.eq(BusinessOrderModel.ORDERCODE, ss[0]));
            }
            if (!ss[1].equals("searchName")) {
                detachedCriteria.add(Restrictions.like(BusinessOrderModel.ORDERNAME, "%" + ss[1] + "%"));
            }
            if (!ss[2].equals("searchAmount")) {
                BigDecimal amount = new BigDecimal(ss[2]);
                detachedCriteria.add(Restrictions.eq(BusinessOrderModel.ORDERAMOUNT, amount));
            }

        }
    }

    /**
     * <code>getOrderData</code>
     * 订单数据接收
     * @param request
     * @param response
     * @return
     * @since 2014年12月15日   wangya
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
                Set<BusinessPaymentPlanModel> plan = s.getBusinessPaymentPlanModel();
                Iterator<BusinessPaymentPlanModel> p = plan.iterator();
                while (p.hasNext()) {
                    BusinessPaymentPlanModel pl = p.next();
                    unplanAmount = unplanAmount.add(pl.getAmount());
                }
                unplanAmount = s.getOrderAmount().subtract(unplanAmount);
                unplanAmount = unplanAmount.subtract(new BigDecimal(s.getSpotNum()));
                dataMap.put(columns[0], s.getOrderCode());
                dataMap.put(columns[1], s.getSupplierShortName());
                dataMap.put(columns[2], s.getOrderAmount());
                dataMap.put(columns[3], unplanAmount);
                dataMap.put(columns[4], "0.00");
                dataMap.put(columns[5], "0");
                dataMap.put(columns[6], s.getId());
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

    /**
    * <code>doAddPay</code>
    * 保存更新实体
    * @param request
    * @param response
    * @param manageModel
    * @return
    * @since   2014年12月15日    wangya
    */
    @ResponseBody
    @RequestMapping(value = "/doAddPay")
    public Map<String, String> doAddPay(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("_sino_eoss_cuotomer_addform") BusinessPayOrderModel payOrderModel) {
        Map<String, String> rsmsg = new HashMap<String, String>();
        try {
            //是否提交过
            if (payOrderModel.getId() != null) {
                rsmsg.put("result", FAIL);
                rsmsg.put("message", "此付款计划已提交过，请刷新页面！");
                return rsmsg;
            } else {
                if (request.getParameter("supplierId") != null && request.getParameter("supplierId") != "") {
                    long supplierId = Long.parseLong(request.getParameter("supplierId"));
                    SupplierInfoModel supplierInfoModel = supplierInfoService.get(supplierId);
                    payOrderModel.setSupplierName(supplierInfoModel);
                }
                if (request.getParameter("payAmount") != null && request.getParameter("payAmount") != "") {
                    Double amo = Double.parseDouble(request.getParameter("payAmount"));
                    BigDecimal amount = BigDecimal.valueOf(amo);
                    payOrderModel.setPayAmonut(amount);
                }
                SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
                String invoiceType = request.getParameter("taxType");

                String tableData = request.getParameter("tableGridData");
                String rs = getService().doAddPay(payOrderModel, systemUser, invoiceType, tableData);
                if (!rs.equals("true")) {
                    rsmsg.put("result", FAIL);
                    rsmsg.put("message", "付款申请提交出错！");
                    return rsmsg;
                }
                rsmsg.put("result", SUCCESS);
                return rsmsg;
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            rsmsg.put("result", FAIL);
            rsmsg.put("message", "付款申请提交出错!");
            return rsmsg;
        }
    }

    /**
     * <code>payFlowManage</code>
     * 用于流程控制所需的URL
     * @param request
     * @param response
     * @return
     * @since   2014年12月16日    wangya
     */
    @RequestMapping(value = "/payFlowManage")
    public ModelAndView payFlowManage(HttpServletRequest request, HttpServletResponse response) {
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
                return updatePay(request, response);
            case SWJLSP:
                System.out.println("SWJLSP");
                return payDetail(request, response);
            case SWZGSP:
                System.out.println("SWZGSP");
                return payDetail(request, response);
            case SWFZCSP:
                System.out.println("SWFZCSP");
                return payDetail(request, response);
            case ZCSP:
                System.out.println("ZCSP");
                return payDetail(request, response);
            case CWFH:
                System.out.println("CWFH");
                return payDetail(request, response);
            case SHOW:
                System.out.println("SHOW");
                return payFinish(request, response);
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
     * <code>payDetail</code>
     * 付款详情页面
     * @param request
     * @param response
     * @return
     * @since   2014年12月16日    wangya
     */
    @RequestMapping(value = "/payDetail")
    public ModelAndView payDetail(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("business/payOrder/payDetail");
        BusinessPayOrderModel payOrder = new BusinessPayOrderModel();
        String id = request.getParameter("id");
        String taskId = request.getParameter("taskId");
        if (id != null) {
            payOrder = getService().get(Long.parseLong(id));
        } else if (taskId != null) {
            request.setAttribute("taskId", taskId);
            long payId = (Long) processService.getVariable(taskId, "payModelId");
            payOrder = getService().get(payId);
        }
        Set<BusinessPaymentPlanModel> plans = payOrder.getBusinessPaymentPlanModel();
        Iterator<BusinessPaymentPlanModel> p = plans.iterator();
        String invoiceType = "";
        while (p.hasNext()) {
            BusinessPaymentPlanModel pl = p.next();
            invoiceType = pl.getInvoiceType();
        }

        request.setAttribute("invoiceType", invoiceType);
        SysStaff user = sysStaffService.get(payOrder.getPayApplyUser());
        request.setAttribute("payApplyUser", user.getStaffName());
        List<ProcInstAppr> proInstLogList = processService.findProcInstApprLog(payOrder.getProcessInstanceId());
        mav.addObject("isPrint", getService().print(payOrder.getProcessInstanceId()));//查询流程审批人
        mav.addObject("proInstLogList", proInstLogList);
        if (taskId != null && payOrder.getRealPayDate() == null) {
            payOrder.setRealPayDate(new Date());
        }
        mav.addObject("model", payOrder);
        return mav;

    }

    /**
     * <code>payFinish</code>
     * 详情页面
     * @param request
     * @param response
     * @return
     * @since   2014年12月16日    wangya
     */
    @RequestMapping(value = "/payFinish")
    public ModelAndView payFinish(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("business/payOrder/payFinish");
        BusinessPayOrderModel payOrder = new BusinessPayOrderModel();
        String taskId = request.getParameter("taskId");
        String procInstId = request.getParameter("procInstId");
        if (procInstId != null) {
            payOrder = getService().findPayModel(procInstId);
        }
        if (payOrder.getPayCompanyBank() != null) {
            List<Map<String, Object>> mapList = getService().getfindBankName(Long.parseLong(payOrder.getPayCompanyBank()));
            Map<String, Object> map = mapList.get(0);
            mav.addObject("mapList", map);//银行信息
            String company = getService().findCompany(Long.parseLong(payOrder.getPayCompany()));
            request.setAttribute("Company", company);//公司信息
        }
        Set<BusinessPaymentPlanModel> plans = payOrder.getBusinessPaymentPlanModel();
        Iterator<BusinessPaymentPlanModel> p = plans.iterator();
        String invoiceType = "";
        while (p.hasNext()) {
            BusinessPaymentPlanModel pl = p.next();
            invoiceType = pl.getInvoiceType();
        }
        request.setAttribute("invoiceType", invoiceType);//发票类型
        SysStaff user = sysStaffService.get(payOrder.getPayApplyUser());
        request.setAttribute("payApplyUser", user.getStaffName());//付款申请人
        if (payOrder.getPayUser() != null) {
            //            SysStaff user2 = sysStaffService.get(payOrder.getPayUser());
            //            request.setAttribute("payUser", user2.getStaffName());//付款人
            request.setAttribute("payUser", payOrder.getPayUser());//付款人
        }
        List<ProcInstAppr> proInstLogList = processService.findProcInstApprLog(payOrder.getProcessInstanceId());
        mav.addObject("proInstLogList", proInstLogList);//审批日志
        mav.addObject("model", payOrder);
        return mav;

    }

    /**
     * <code>updatePay</code>
     * 重新提交页面
     * @param request
     * @param response
     * @return
     * @since   2014年12月16日    wangya
     */
    @RequestMapping(value = "/updatePay")
    private ModelAndView updatePay(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("business/payOrder/updatePay");
        BusinessPayOrderModel payOrder = new BusinessPayOrderModel();
        String taskId = request.getParameter("taskId");
        if (taskId != null) {
            request.setAttribute("taskId", taskId);
            long payId = (Long) processService.getVariable(taskId, "payModelId");
            payOrder = getService().get(payId);
        }
        Set<BusinessPaymentPlanModel> plans = payOrder.getBusinessPaymentPlanModel();
        List<HashMap<String, Object>> listMap = new ArrayList<HashMap<String, Object>>();
        BigDecimal unplanAmount = new BigDecimal(0.00);
        BigDecimal amou = new BigDecimal(0.00);
        Iterator<BusinessPaymentPlanModel> p = plans.iterator();
        String invoiceType = "";
        while (p.hasNext()) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            BusinessPaymentPlanModel pl = p.next();
            invoiceType = pl.getInvoiceType();
            BusinessOrderModel order = pl.getBusinessOrder();
            unplanAmount = getService().findPlanAmount(order.getId());//此订单的计划付款金额
            amou = pl.getAmount();
            unplanAmount = unplanAmount.subtract(amou);
            unplanAmount = order.getOrderAmount().subtract(unplanAmount);
            map.put(columns[0], pl.getOrderCode());
            map.put(columns[1], pl.getSupplierShortName());
            map.put(columns[2], order.getOrderAmount());
            map.put(columns[3], unplanAmount);//可付款金额
            map.put(columns[4], pl.getAmount());
            map.put(columns[5], pl.getCredit());
            map.put(columns[6], order.getId());
            listMap.add(map);
        }
        request.setAttribute("listMap", listMap);
        request.setAttribute("invoiceType", invoiceType);
        mav.addObject("model", payOrder);
        return mav;
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
    @RequestMapping(value = "/doUpdateOrder")
    public Map<String, String> doUpdateOrder(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("_sino_eoss_cuotomer_addform") BusinessPayOrderModel payOrder, Errors error) {
        Map<String, String> rsmsg = new HashMap<String, String>();
        try {
            if (error.hasErrors()) {

                rsmsg.put("result", FAIL);
                return rsmsg;
            }
            SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
            payOrder.setPayApplyUser(systemUser.getUserId());
            if (request.getParameter("supplierId") != null && request.getParameter("supplierId") != "") {
                long supplierId = Long.parseLong(request.getParameter("supplierId"));
                SupplierInfoModel supplierInfoModel = supplierInfoService.get(supplierId);
                payOrder.setSupplierName(supplierInfoModel);
            }
            if (request.getParameter("payAmount") != null && request.getParameter("payAmount") != "") {
                Double amo = Double.parseDouble(request.getParameter("payAmount"));
                BigDecimal amount = BigDecimal.valueOf(amo);
                payOrder.setPayAmonut(amount);
            }

            String tableData = request.getParameter("tableGridData");
            String taskId = request.getParameter("taskId");
            String invoiceType = request.getParameter("taxType");
            String rs = getService().doUpdateOrder(payOrder, tableData, taskId, systemUser, invoiceType);
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
     * <code>handleFlow</code>
     * 处理流程
     * @param request
     * @param response
     * @return
     * @since   2014年12月16日    wangya
     */
    @RequestMapping(value = "/handleFlow")
    public ModelAndView handleFlow(HttpServletRequest request, HttpServletResponse response, BusinessPayOrderModel payOrder) {
        String flowStep = request.getParameter("flowStep");
        String taskId = request.getParameter("taskId");
        int isAgree = Integer.parseInt(request.getParameter("isAgree") == null ? "1" : request.getParameter("isAgree"));
        String remark = request.getParameter("remark");
        //创建者
        SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
        String banlance = request.getParameter("banlance")/* == null ? "0" : request.getParameter("banlance")*/;

        //流程处理service
        getService().handleFlow(flowStep, systemUser, payOrder, taskId, isAgree, remark, banlance);
        //若订单付款包含备货，并已被关联，则在关联备货成本中插入一条产品合同信息
        if (isAgree == 1 && flowStep.equals(BusinessOrderContant.CWFH)) {
            getService().doSalesContractbh(payOrder);
        }
        ModelAndView mav = new ModelAndView("business/payOrder/manage");
        return mav;
    }

    /**
     * <code>search</code>
     * 查询页面
     * @param request
     * @param response
     * @return
     * @since   2014年12月15日    wangya
     */
    @RequestMapping(value = "/taskSearch")
    public ModelAndView taskSearch(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("business/costTask/payTaskSearch");
        return mav;
    }

    /**
     * <code>costTaskManage</code>
     * 
     * @param request
     * @param response
     * @return
     * @since   2014年12月15日    wangya
     */
    @RequestMapping(value = "/costTaskManage")
    public ModelAndView costTaskManage(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("business/costTask/costTaskManage");
        return mav;
    }

    /**
     * <code>costTaskList</code>
     * 得到待付款审核列表
     * @param request
     * @param response
     * @return
     * @since   2014年12月15日    wangya
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
        PaginationSupport paginationSupport = getService().payTaskList(search, this.pageNo * this.pageSize, Integer.parseInt(pageNo) * this.pageSize);

        map.put("rows", paginationSupport.getItems());
        map.put("total", paginationSupport.getTotalCount());
        map.put("page", pageNo);
        return map;
    }

    public void buildSearch_payTask(HttpServletRequest request, Map<String, Object> search) {
        SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
        search.put("systemUser", systemUser.getUserName());
        String payApplyName = request.getParameter("payApplyName");
        String payAmount = request.getParameter("payAmount");
        String planPayDate = request.getParameter("planPayDate");
        if (!StringUtil.isEmpty(payApplyName)) {
            search.put(BusinessPayOrderModel.PAYAPPLYNAME, payApplyName);
        }
        if (!StringUtil.isEmpty(payAmount)) {
            search.put(BusinessPayOrderModel.AMOUNT, payAmount);
        }
        if (!StringUtil.isEmpty(planPayDate)) {
            search.put(BusinessPayOrderModel.PLANPAYDATE, planPayDate);
        }

    }

    /**
     * <code>getCompany</code>
     * 得到公司formSelect List<Map<String,Object>>集合 
     * @param request
     * @param response
     * @return
     * @since   2014年7月28日    wangya
     */
    @ResponseBody
    @RequestMapping(value = "/getCompany")
    public List<Map<String, Object>> getCompany(HttpServletRequest request, HttpServletResponse response) {
        List<Map<String, Object>> comPanyList = getService().findCompany();
        return comPanyList;
    }

    /**
     * <code>getBankAccountByCmp</code>
     * 根据公司ID得到公司账户
     * @param request
     * @param response
     * @return
     * @since   2014年12月18日    wangya
     */
    @ResponseBody
    @RequestMapping(value = "/getBankAccountByCmp")
    public List<Map<String, Object>> getBankAccountByCmp(HttpServletRequest request, HttpServletResponse response) {
        String companyId = request.getParameter("companyId");
        List<Map<String, Object>> modelList = getService().getBankAccountByCmp(Long.parseLong(companyId));
        return modelList;
    }

    /**
     * <code>getBankAccountModel</code>
     * 根据Id得到账户信息
     * @param request
     * @param response
     * @return
     * @since   2014年7月28日    wangya
     */
    @ResponseBody
    @RequestMapping(value = "/getBankAccountModel")
    public List<Map<String, Object>> getBankAccountModel(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        if (id != null) {
            mapList = getService().getfindBankName(Long.parseLong(id));
        }
        return mapList;
    }

    /**
     * <code>getAccountPersion</code>
     * 得到财务部员工
     * @param request
     * @param response
     * @return
     * @since   2014年12月18日    wangya
     */
    @ResponseBody
    @RequestMapping(value = "/getAccountPersion")
    public List<Map<String, Object>> getAccountPersion(HttpServletRequest request, HttpServletResponse response) {
        return getService().getAccountPersion();
    }

    /**报销单查询页面
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/reimSearch")
    public ModelAndView remiSearch(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("business/costTask/reimTaskSearch");
        return mav;
    }

    /**
    * <code>endpay</code>
    * 删除付款
    * @param request
    * @param response
    * @return
    * @since   2014年12月20日    wangya
    */
    @ResponseBody
    @RequestMapping(value = "/endpay")
    public String endpay(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("_sino_eoss_cuotomer_addform") BusinessPayOrderModel payOrder) {
        try {
            SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
            String taskId = request.getParameter("taskId");
            getService().endpay(taskId, payOrder, systemUser);
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return FAIL;
        }
    }

    /**
     * <code>printPay</code>
     * 付款打印页面
     * @param request
     * @param response
     * @return
     * @since 2014年12月20日    wangya
     */
    @RequestMapping(value = "/printPay")
    public ModelAndView printPay(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("business/payOrder/printPay");
        Long id = Long.parseLong(request.getParameter("id"));
        BusinessPayOrderModel pay = getService().get(id);
        //总额大写
        String amount = pay.getPayAmonut().toString();
        String capslk = MoneyFormatUtil.getChnmoney(amount);
        mav.addObject("capitalMoney", capslk);
        List<ProcInstAppr> proInstLogList = processService.findProcInstApprLog(pay.getProcessInstanceId());//审批日志
        StringBuilder sb = new StringBuilder();
        for (ProcInstAppr appr : proInstLogList) {
            sb.append(appr.getUser() + " ");
        }
        mav.addObject("taskId", getService().getTaskIdByProInst(pay.getProcessInstanceId()));
        mav.addObject("proInstId", pay.getProcessInstanceId() + "");
        mav.addObject("type", "3");
        Set<BusinessPaymentPlanModel> p = pay.getBusinessPaymentPlanModel();
        mav.addObject("plan", p);
        //审批人
        mav.addObject("approver", sb.toString());
        mav.addObject("model", pay);
        return mav;
    }

    /**
     * <code>getAllPay</code>
     * 得到表格列表
     * @param request
     * @param response
     * @return
     * @since   2014年12月21日    wangya
     */
    @ResponseBody
    @RequestMapping(value = "/getAllPay")
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
        // DetachedCriteria detachedCriteria = DetachedCriteria.forClass(getModel().getClass());
        Map<String, Object> searchMap = new HashMap<String, Object>();
        // detachedCriteria.addOrder(Order.desc("id"));
        buildSearch_allPay(request, searchMap);
        PaginationSupport paginationSupport = getService().getModelPage(searchMap, this.pageNo * this.pageSize, this.pageSize);
        //付款金额
        String amount = getService().findTotallAmount(searchMap);
        map.put("totallamount", amount);

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
        String supplierShortName = request.getParameter("supplierShortName");
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        String minAmount = request.getParameter("minAmount");
        String payName = request.getParameter("payName");
        //String maxAmount = request.getParameter("maxAmount");
        if (!StringUtil.isEmpty(startTime)) {
            searchMap.put("startTime", startTime);
            //            detachedCriteria.add(Restrictions.ge(BusinessPayOrderModel.CLOSETIME, DateUtils.convertStringToDate(startTime, "yyyy-MM-dd")));
        }
        if (!StringUtil.isEmpty(endTime)) {
            searchMap.put("endTime", endTime);
            //            detachedCriteria.add(Restrictions.le(BusinessPayOrderModel.CLOSETIME, DateUtils.convertStringToDate(endTime, "yyyy-MM-dd")));
        }
        if (!StringUtil.isEmpty(supplierShortName)) {
            searchMap.put("supplierId", Long.parseLong(supplierShortName));
            //            detachedCriteria.createAlias("supplierName", "supplierName");
            //            detachedCriteria.add(Restrictions.eq("supplierName.id", Long.parseLong(supplierShortName)));
        }
        if (!StringUtil.isEmpty(minAmount)) {
            searchMap.put("minAmount", minAmount);
        }
        if (!StringUtil.isEmpty(payName)) {
            searchMap.put("payName", "%" + payName + "%");
        }
        /* if (!StringUtil.isEmpty(maxAmount)) {
             searchMap.put("maxAmount", maxAmount);
         }*/
    }

    /**
     * <code>searchCostPay</code>
     * 付款查询页面
     * @param request
     * @param response
     * @return
     * @since   2014年12月15日    wangya
     */
    @RequestMapping(value = "/searchCostPay")
    public ModelAndView searchCostPay(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("business/order/costControl/searchPay");
        return mav;
    }

    /**
     * <code>detail</code>
     * 我的订单中的付款页面
     * @param request
     * @return
     * @since 2015.12.21   wangya
     */
    @RequestMapping(value = "/detail")
    public ModelAndView detail(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("business/payment/detail");
        String orderId = request.getParameter("id");
        BusinessOrderModel orderModel = new BusinessOrderModel();
        BigDecimal sum = new BigDecimal(0.00);
        if (orderId != null && orderId != "") {
            orderModel = businessOrderService.get(Long.parseLong(orderId));
            Set<BusinessPaymentPlanModel> payments = orderModel.getBusinessPaymentPlanModel();
            request.setAttribute("payments", payments);
            //查询付款申请信息
            List<BusinessPayOrderModel> pays = getService().getPayOrderList(orderModel.getId());
            request.setAttribute("pays", pays);
            request.setAttribute("currency", pays.get(0).getCurrency());
        }
        sum = orderModel.getOrderAmount().subtract(getService().getpaymentAmount(orderId));
        request.setAttribute("orderDueAmnount", sum);
        mav.addObject("model", orderModel);
        return mav;
    }
}
