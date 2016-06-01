/*
 * FileName: SalesContractController.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.sales.contract.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.ehcache.Ehcache;
import net.sf.json.JSONSerializer;

import org.apache.poi.ss.usermodel.Workbook;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.sinobridge.base.chart.fusioncharts.util.FusionChartUtil;
import com.sinobridge.base.core.controller.DefaultBaseController;
import com.sinobridge.base.core.hibernate.id.IdentifierGeneratorImpl;
import com.sinobridge.base.core.utils.JacksonJsonMapper;
import com.sinobridge.base.core.utils.PaginationSupport;
import com.sinobridge.eoss.base.customermanage.model.CustomerContactsModel;
import com.sinobridge.eoss.base.customermanage.model.CustomerInfoModel;
import com.sinobridge.eoss.base.customermanage.service.CustomerContactsService;
import com.sinobridge.eoss.base.customermanage.service.CustomerInfoService;
import com.sinobridge.eoss.bpm.common.constants.ProcessConstants;
import com.sinobridge.eoss.bpm.service.ProcInstAppr;
import com.sinobridge.eoss.bpm.service.process.ProcessService;
import com.sinobridge.eoss.business.order.service.BusinessOrderService;
import com.sinobridge.eoss.sales.cachet.model.SalesCachetModel;
import com.sinobridge.eoss.sales.cachet.service.SalesCachetService;
import com.sinobridge.eoss.sales.contract.constant.SalesContractConstant;
import com.sinobridge.eoss.sales.contract.dto.SalesContractDto;
import com.sinobridge.eoss.sales.contract.dto.SalesContractInfoAndStatus;
import com.sinobridge.eoss.sales.contract.model.SalesContractModel;
import com.sinobridge.eoss.sales.contract.model.SalesContractProductModel;
import com.sinobridge.eoss.sales.contract.model.SalesContractReceivePlanModel;
import com.sinobridge.eoss.sales.contract.model.SalseContractMergeModel;
import com.sinobridge.eoss.sales.contract.service.SalesContractProductService;
import com.sinobridge.eoss.sales.contract.service.SalesContractService;
import com.sinobridge.eoss.sales.contract.service.SalseContractMergeService;
import com.sinobridge.eoss.sales.contract.utils.CodeUtils;
import com.sinobridge.eoss.sales.contract.utils.DateUtils;
import com.sinobridge.eoss.sales.contract.utils.ExcelBuilderUtil;
import com.sinobridge.eoss.sales.contract.utils.FileUtils;
import com.sinobridge.eoss.sales.contractchange.model.ContractChangeApplyModel;
import com.sinobridge.eoss.sales.contractchange.service.ContractChangeApplyService;
import com.sinobridge.eoss.sales.contractchange.service.ContractSnapShootService;
import com.sinobridge.eoss.sales.invoice.model.SalesInvoicePlanModel;
import com.sinobridge.eoss.sales.invoice.service.SalesInvoiceService;
import com.sinobridge.eoss.salesfundsCost.model.SalesBudgetFunds;
import com.sinobridge.eoss.salesfundsCost.service.FundsSalesBudgetService;
import com.sinobridge.systemmanage.model.SysOrgnization;
import com.sinobridge.systemmanage.model.SysStaff;
import com.sinobridge.systemmanage.service.FileService;
import com.sinobridge.systemmanage.service.SysStaffService;
import com.sinobridge.systemmanage.util.Constants;
import com.sinobridge.systemmanage.util.Global;
import com.sinobridge.systemmanage.util.StringUtil;
import com.sinobridge.systemmanage.vo.FileVo;
import com.sinobridge.systemmanage.vo.SystemUser;

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
 * 2014年5月5日 下午8:27:56          3unshine        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Controller
@RequestMapping(value = "/sales/contract")
public class SalesContractController extends DefaultBaseController<SalesContractModel, SalesContractService> {
    @Autowired
    private CustomerInfoService customerInfoService;
    @Autowired
    private ProcessService processService;
    @Autowired
    private FileService fileService;
    @Autowired
    private ContractChangeApplyService contractChangeApplyService;
    @Autowired
    private ContractSnapShootService contractSnapShootService;
    @Autowired
    private SysStaffService staffService;
    @Autowired
    private SalesInvoiceService salesInvoiceService;
    @Autowired
    private BusinessOrderService businessOrderService;
    @Autowired
    SalesContractProductService salesContractProductService;
    @Autowired
    CustomerContactsService contactService;
    
    @Autowired
    private FundsSalesBudgetService fundsSalesBudgetService;
    @Autowired
    private SalseContractMergeService salseContractMergeService;

    private final static Ehcache systemUserCache = Constants.CACHE_MANAGER.getEhcache("systemUser");

    @Autowired
    SalesCachetService cachetService;

    String pattern = "yyyy-MM-dd HH:mm";
    //仅有日期的格式
    String DatePattern = "yyyy-MM-dd";

    /*  private final String columns[] = { "planedReceiveAmount", "planedReceiveDate", "payCondition", "planedReceiveId" };
        private final String columnNames[] = { "计划收款金额", "计划收款时间", "收款条件", "收款计划ID" };
        private final String columnTypes[] = { "string", "datetime_false", "string", "hidden" };*/
    private final String columns[] = { "planedReceiveAmount", "planedReceiveDate", "payCondition" };
    private final String columnNames[] = { "计划收款金额", "计划收款时间", "收款条件" };
    private final String columnTypes[] = { "string", "datetime", "string" };
    private final String columns2[] = { "planedInvoiceAmount", "planedInvoiceDate", "planCondition" };
    private final String columnNames2[] = { "计划发票金额", "计划发票时间", "说明" };
    private final String columnTypes2[] = { "string", "datetime", "string" };

    /**
     * <code>manage</code>
     * 我的合同管理页面
     * @param request
     * @param response
     * @return
     * @since   2014年5月05日    3unshine
     */
    @RequestMapping(value = "/manage")
    public ModelAndView manage(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("sales/contract/manage");
        return mav;
    }

    /**
     * <code>getList</code>
     * 得到“我的合同”manage页面的列表
     * @param request
     * @param response
     * @return
     * @since   2014年5月20日    3unshine
     */
    @ResponseBody
    @RequestMapping(value = "/getList")
    public Map<String, Object> getList(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();
        String iDisplayStart = request.getParameter("pageNo");
        String iDisplayLength = request.getParameter("pageSize");
        if (iDisplayStart != null) {
            this.pageNo = Integer.parseInt(iDisplayStart) - 1;
        }
        if (iDisplayLength != null) {
            this.pageSize = Integer.parseInt(iDisplayLength);
        }
        HashMap<String, Object> searchMap = new HashMap<String, Object>();
        buildSearch4HashMap(request, searchMap);
        //当前登录用户
        SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
        //用户只能管理自己创建的合同
        searchMap.put(SalesContractModel.CREATOR, Long.parseLong(systemUser.getUserId()));
        //按创建时间排序
        //searchMap.put("orderBy", "c.createTime");
        PaginationSupport paginationSupport = getService().findPageBySearchMap(searchMap, this.pageNo * this.pageSize, this.pageSize);
        map.put("rows", paginationSupport.getItems());
        map.put("total", paginationSupport.getTotalCount());
        map.put("page", iDisplayStart);
        return map;
    }

    /**
     * <code>search</code>
     * “我的合同”中查询条件管理页面
     * @param request
     * @param response
     * @return
     * @since   2014年5月20日    3unshine
     */
    @RequestMapping("/search")
    public ModelAndView search(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("sales/contract/search");
        return mav;
    }

    /**
     * <code>getSearchManageList</code>
     * 得到“综合合同查询”manage页面的列表
     * @param request
     * @param response
     * @return
     * @since   2014年5月20日    3unshine
     */
    @ResponseBody
    @RequestMapping(value = "/getSearchManageList")
    public Map<String, Object> getSearchManageList(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();
        String iDisplayStart = request.getParameter("pageNo");
        String iDisplayLength = request.getParameter("pageSize");
        if (iDisplayStart != null) {
            this.pageNo = Integer.parseInt(iDisplayStart) - 1;
        }
        if (iDisplayLength != null) {
            this.pageSize = Integer.parseInt(iDisplayLength);
        }
        HashMap<String, Object> searchMap = new HashMap<String, Object>();
        searchMap.put("contractStateNotEquals", "CG");
        buildSearch4HashMap(request, searchMap);
        //按创建时间排序
        //searchMap.put("orderBy", SalesContractModel.CREATETIME);
        //综合查询的合同列表不能给用户展示草稿状态的合同
        PaginationSupport paginationSupport = getService().findPageByMutiSearchMap(searchMap, this.pageNo * this.pageSize, this.pageSize);

        //得到查询后所有订单的总金额
        String amount = getService().findTotallAmount(searchMap);

        //得到查询后页面总金额
        List<SalesContractInfoAndStatus> listbus = (List<SalesContractInfoAndStatus>) paginationSupport.getItems();

        Iterator<SalesContractInfoAndStatus> bus = listbus.iterator();
        BigDecimal pageAmount = new BigDecimal(0.00);
        while (bus.hasNext()) {
            SalesContractInfoAndStatus b = bus.next();
            if (b.getContractAmount() != null) {
                BigDecimal pa = new BigDecimal(b.getContractAmount());
                pageAmount = pageAmount.add(pa);
            }
        }
        String page = pageAmount.toString();

        map.put("rows", paginationSupport.getItems());
        map.put("total", paginationSupport.getTotalCount());
        map.put("page", iDisplayStart);
        map.put("totallAmount", amount);
        map.put("pageAmount", page);
        return map;
    }

    /**
     * <code>getSearchCompreList</code>
     * 得到“综合合同查询”manage页面的列表
     * @param request
     * @param response
     * @return
     * @since   2014年5月20日    3unshine
     */
    @ResponseBody
    @RequestMapping(value = "/getSearchCompreList")
    public Map<String, Object> getSearchCompreList(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();
        String iDisplayStart = request.getParameter("pageNo");
        String iDisplayLength = request.getParameter("pageSize");
        String orgId = request.getParameter("orgId");
        if (iDisplayStart != null) {
            this.pageNo = Integer.parseInt(iDisplayStart) - 1;
        }
        if (iDisplayLength != null) {
            this.pageSize = Integer.parseInt(iDisplayLength);
        }
        HashMap<String, Object> searchMap = new HashMap<String, Object>();
        searchMap.put("contractStateNotEquals", "CG");
        buildSearch4HashMap(request, searchMap);
        searchMap.put("orgId", orgId);
        //综合查询的合同列表不能给用户展示草稿状态的合同
        PaginationSupport paginationSupport = getService().findPageByMutiSearchMap(searchMap, this.pageNo * this.pageSize, this.pageSize);

        //得到查询后所有订单的总金额
        String amount = getService().findTotallAmount(searchMap);

        //得到查询后页面总金额
        List<SalesContractInfoAndStatus> listbus = (List<SalesContractInfoAndStatus>) paginationSupport.getItems();

        Iterator<SalesContractInfoAndStatus> bus = listbus.iterator();
        BigDecimal pageAmount = new BigDecimal(0.00);
        while (bus.hasNext()) {
            SalesContractInfoAndStatus b = bus.next();
            if (b.getContractAmount() != null) {
                BigDecimal pa = new BigDecimal(b.getContractAmount());
                pageAmount = pageAmount.add(pa);
            }
        }
        String page = pageAmount.toString();

        map.put("rows", paginationSupport.getItems());
        map.put("total", paginationSupport.getTotalCount());
        map.put("page", iDisplayStart);
        map.put("totallAmount", amount);
        map.put("pageAmount", page);
        return map;
    }

    /**
     * <code>searchManage</code>
     * “综合合同查询”中查询条件管理页面
     * @param request
     * @param response
     * @return
     * @since   2014年6月30日    3unshine
     */
    @RequestMapping(value = "/searchManage")
    public ModelAndView searchManage(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("sales/contract/searchManage");
        return mav;
    }

    /**
     * <code>searchManage</code>
     * “综合合同查询”中查询条件管理页面
     * @param request
     * @param response
     * @return
     * @since   2014年6月30日    3unshine
     */
    @RequestMapping(value = "/searchCompre")
    public ModelAndView searchCompre(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("sales/contract/searchCompre");
        //创建者
        SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
        List<Map<String, Object>> orgName = getService().findOrg(systemUser.getUserId());
        String orgId = "";
        String orgNa = "";
        orgNa = orgName.get(0).get("positionName").toString();
        int flat = getService().findroles(systemUser.userId);
        if (orgNa.equals("部门经理")) {
            orgId = orgName.get(0).get("orgId").toString();
        } else if (flat == 1) {
            SysOrgnization orgs = systemUser.getSysOrgnization();
            orgId = orgs.getParentId();
        } else {
            SysOrgnization orgs = systemUser.getSysOrgnization();
            orgId = orgs.getOrgId();
        }

        request.setAttribute("orgId", orgId);
        return mav;
    }

    /**
     * <code>multipleSearch</code>
     * “综合合同查询”中查询条件管理页面
     * @param request
     * @param response
     * @return
     * @since   2014年6月30日    3unshine
     */
    @RequestMapping(value = "/multipleSearch")
    public ModelAndView multipleSearch(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("sales/contract/multipleSearch");
        return mav;
    }

    /**
     * <code>multipleSearch</code>
     * “综合合同查询”中查询条件管理页面
     * @param request
     * @param response
     * @return
     * @since   2014年6月30日    3unshine
     */
    @RequestMapping(value = "/multipleCompre")
    public ModelAndView multipleCompre(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("sales/contract/multipleCompre");
        String orgId = request.getParameter("orgId");
        String orgParentId = request.getParameter("orgParentId");
        request.setAttribute("orgId", orgId);
        request.setAttribute("orgParentId", orgParentId);
        return mav;
    }

    /**
     * <code>buildSearch4HashMap</code>
     * 通过HashMap创建查询条件
     * @param searchMap
     * @since 2014年7月16日     3unshine
     */
    public void buildSearch4HashMap(HttpServletRequest request, HashMap<String, Object> searchMap) {
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

        if (!StringUtil.isEmpty(customerIndustry)) {
            searchMap.put("cusIndustryId", customerIndustry);
        }
        if (!StringUtil.isEmpty(customerIdtCustomer)) {
            searchMap.put("cusCustomerId", customerIdtCustomer);
        }
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
    }

    /**
     * <code>buildSearch4Criteria</code>
     * 通过DetachedCriteria创建查询条件
     * @param detachedCriteria
     * @since   2014年5月05日     3unshine
     */
    public void buildSearch4Criteria(HttpServletRequest request, DetachedCriteria detachedCriteria) {
        String contractName = request.getParameter("contractName");
        String contractShortname = request.getParameter("contractShortname");
        String contractState = request.getParameter("contractState");
        String contractCode = request.getParameter("contractCode");
        String contractAmount = request.getParameter("contractAmount");
        if (!StringUtil.isEmpty(contractName)) {
            detachedCriteria.add(Restrictions.eq(SalesContractModel.CONTRACTNAME, "%" + contractName + "%"));
        }
        if (!StringUtil.isEmpty(contractShortname)) {
            detachedCriteria.add(Restrictions.eq(SalesContractModel.CONTRACTSHORTNAME, "%" + contractShortname + "%"));
        }
        if (!StringUtil.isEmpty(contractState)) {
            detachedCriteria.add(Restrictions.eq(SalesContractModel.CONTRACTSTATE, "%" + contractState + "%"));
        }
        if (!StringUtil.isEmpty(contractCode)) {
            detachedCriteria.add(Restrictions.eq(SalesContractModel.CONTRACTCODE, "%" + contractCode + "%"));
        }
        if (!StringUtil.isEmpty(contractAmount)) {
            detachedCriteria.add(Restrictions.gt(SalesContractModel.CONTRACTAMOUNT, contractAmount));
        }
    }

    /**
     * <code>toSave</code>
     * 跳转到toSave页面
     * @param request
     * @param response
     * @return
     * @since 2014年5月19日     3unshine
     */
    @RequestMapping(value = "/toSave")
    public ModelAndView toSave(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("sales/contract/addSalesContract");
        String formDataJson = "";
        String formDataJson2 = "";
        SalesContractModel salesContractModel = null;
        //创建者
        SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
        request.setAttribute("systemUser", systemUser);
        //拼凑出tableGrid所需的JSON数据
        formDataJson = tableGridDataUnit(salesContractModel, "save");
        formDataJson2 = tableGridDataUnit2(salesContractModel, "save");
        request.setAttribute("form", formDataJson);
        request.setAttribute("form2", formDataJson2);
        Map<String, Object> map = getService().findCompanyInfo().get(0);
        request.setAttribute("companyName", map.get("companyname"));
        request.setAttribute("bankName", map.get("bankname"));
        request.setAttribute("account", map.get("bankaccount"));
        return mav;
    }

    /**
     * <code>doSave</code>
     * 保存实体
     * @param request
     * @param response
     * @param manageModel
     * @return
     * @since 2014年5月19日    3unshine
     */
    @ResponseBody
    @RequestMapping(value = "/doSave")
    public String doSave(HttpServletRequest request, HttpServletResponse response, SalesContractModel salesContractModel, Errors errors) {
        try {

            if (errors.hasErrors()) {
                return FAIL;
            }
            //设置合同ID
            long salesContractModelId = IdentifierGeneratorImpl.generatorLong();
            salesContractModel.setId(salesContractModelId);
            //创建时间
            salesContractModel.setCreateTime(new Date());
            //创建者
            SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
            salesContractModel.setCreator(Long.parseLong(systemUser.getUserId()));
            salesContractModel.setCreatorName(systemUser.staffName);
            //设置合同初始变更状态为0
            salesContractModel.setIsChanged(0);

            salesContractModel.setOrgCode(systemUser.getSysOrgnization().getOrgCode());
            //得到合同中的产品
            List<SalesContractProductModel> salesContractProducts = null;
            //得到付款计划
            List<SalesContractReceivePlanModel> receivePlans = receivePlansDataAnalyst(salesContractModel, systemUser);
            //得到发票计划
            List<SalesInvoicePlanModel> invoicePlans = invoicePlansDataAnalyst(salesContractModel, systemUser);
            //MA续保合同
            if (salesContractModel.getContractType() == 3000) {
                salesContractProducts = salesContracMAtProductsDataAnalyst(salesContractModel);
            } else {
                salesContractProducts = salesContractProductsDataAnalyst(salesContractModel);
            }
            getService().save(salesContractModel, receivePlans, salesContractProducts, systemUser, invoicePlans);
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return FAIL;
        }
    }

    /**
     * <code>toUpdate</code>
     * 跳转到toUpdate页面(为草稿状态下的修改和重新提交提供页面)
     * @param request
     * @param response
     * @return
     * @since 2014年5月19日     3unshine
     */
    @RequestMapping(value = "/toUpdate")
    public ModelAndView toUpdate(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("sales/contract/updateSalesContract");
        String flowFlag = request.getAttribute("flowFlag") + "";
        String id = request.getParameter("id") != null ? request.getParameter("id") : request.getAttribute("contractId") + "";
        String taskId = request.getAttribute("taskId") + "";
        String formDataJson = "";
        String formDataJson2 = "";
        SalesContractModel salesContractModel = null;
        if (taskId != null && taskId != "")
            request.setAttribute("taskId", taskId);
        //取出合同实体发送到前台页面
        if (id != null && !"".equals(id)) {
            salesContractModel = getService().get(Long.parseLong(id));
            mav.addObject("model", salesContractModel);
            //审批日志
            List<ProcInstAppr> proInstLogList = processService.findProcInstApprLog(salesContractModel.getProcessInstanceId());
            mav.addObject("proInstLogList", proInstLogList);
            //判断是否能修改
            Object nameObj = getService().getSaleStateById(salesContractModel.getId()).get("NAME_");
            String name = "";
            if (nameObj != null) {
                name = nameObj.toString();
            }
            if (!(salesContractModel.getContractState().equals(SalesContractConstant.CONTRACT_STATE_CG) || name.equals("重新提交"))) {
                mav = new ModelAndView("sales/contract/updateSalesContract1");
                return mav;
            }
        }
        //拼凑出tableGrid所需的JSON数据
        formDataJson = tableGridDataUnit(salesContractModel, "update");
        request.setAttribute("form", formDataJson);
        formDataJson2 = tableGridDataUnit2(salesContractModel, "update");
        request.setAttribute("form2", formDataJson2);
        request.setAttribute("flowFlag", flowFlag);
        return mav;
    }

    /**
     * <code>toContractChange</code>
     * 跳转到toContractChange页面(为合同变更提供页面)
     * @param request
     * @param response
     * @return
     * @since 2014年5月19日     3unshine
     */
    @RequestMapping(value = "/toContractChange")
    public ModelAndView toContractChange(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("sales/contractchange/changeSalesContract");
        String id = request.getParameter("id");
        SalesContractModel salesContractModel = getService().get(Long.parseLong(id));
        mav.addObject("model", salesContractModel);
        ContractChangeApplyModel contractChangeApplyModel = contractChangeApplyService.getContractChangeApplyModelByContractId(salesContractModel.getId());

        if (contractChangeApplyModel.getChangeType() == 1) { //变更金额
            mav = new ModelAndView("sales/contractchange/changeSalesContractAmount");
            mav.addObject("model", salesContractModel);
            mav.addObject("contractChangeApplyModel", contractChangeApplyModel);
            String formDataJson = tableGridDataUnit(salesContractModel, "update");
            request.setAttribute("form", formDataJson);
            String formDataJson2 = tableGridDataUnit2(salesContractModel, "update");
            request.setAttribute("form2", formDataJson2);
        } else if (contractChangeApplyModel.getChangeType() == 5) { //变更清单
            mav = new ModelAndView("sales/contractchange/changeSalesContractProduct");
            mav.addObject("model", salesContractModel);
            mav.addObject("contractChangeApplyModel", contractChangeApplyModel);
            String formDataJson = tableGridDataUnit(salesContractModel, "update");
            request.setAttribute("form", formDataJson);
            String formDataJson2 = tableGridDataUnit2(salesContractModel, "update");
            request.setAttribute("form2", formDataJson2);
        } else if (contractChangeApplyModel.getChangeType() == 2) { //增加备品备件
            mav = new ModelAndView("sales/contractchange/changeSalesContractParts");
            mav.addObject("model", salesContractModel);
            mav.addObject("contractChangeApplyModel", contractChangeApplyModel);
            String formDataJson = tableGridDataUnit(salesContractModel, "detail");
            request.setAttribute("form", formDataJson);
            String formDataJson2 = tableGridDataUnit2(salesContractModel, "detail");
            request.setAttribute("form2", formDataJson2);
        } else if (contractChangeApplyModel.getChangeType() == 4) { //变更其他
            mav = new ModelAndView("sales/contractchange/changeSalesContractOther");
            mav.addObject("model", salesContractModel);
            mav.addObject("contractChangeApplyModel", contractChangeApplyModel);
            String formDataJson = tableGridDataUnit(salesContractModel, "detail");
            request.setAttribute("form", formDataJson);
            String formDataJson2 = tableGridDataUnit2(salesContractModel, "detail");
            request.setAttribute("form2", formDataJson2);
        } else if (contractChangeApplyModel.getChangeType() == 8) { //变更收款计划
            mav = new ModelAndView("sales/contractchange/changeSalesContractPlan");
            mav.addObject("model", salesContractModel);
            mav.addObject("contractChangeApplyModel", contractChangeApplyModel);
            String formDataJson = tableGridDataUnit(salesContractModel, "update");
            request.setAttribute("form", formDataJson);
            String formDataJson2 = tableGridDataUnit2(salesContractModel, "update");
            request.setAttribute("form2", formDataJson2);
        } else if (contractChangeApplyModel.getChangeType() == 9) { //添加产品
            mav = new ModelAndView("sales/contractchange/changeSalesContractProductAdd");
            mav.addObject("model", salesContractModel);
            mav.addObject("contractChangeApplyModel", contractChangeApplyModel);
            String formDataJson = tableGridDataUnit(salesContractModel, "update");
            request.setAttribute("form", formDataJson);
            String formDataJson2 = tableGridDataUnit2(salesContractModel, "update");
            request.setAttribute("form2", formDataJson2);
        } else {
            String formDataJson = tableGridDataUnit(salesContractModel, "update");
            request.setAttribute("form", formDataJson);
            String formDataJson2 = tableGridDataUnit2(salesContractModel, "update");
            request.setAttribute("form2", formDataJson2);
        }

        request.setAttribute("changeApplyModel", contractChangeApplyModel);
        return mav;
    }

    /**
     * 合同金额变更
     * @param request
     * @param response
     * @param salesContractModel
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/doContractAmountChange")
    public String doContractAmountChange(HttpServletRequest request, HttpServletResponse response, SalesContractDto salesContractDto) {
        //查询出变更前的合同
        SalesContractModel oldSalesContractModel = getService().get(salesContractDto.getId());

        //构造变更后的合同
        SalesContractModel newSalesContractModel = oldSalesContractModel;

        //变更的内容类型
        int changeType = Integer.parseInt(request.getParameter("changeType"));

        //变更申请的ID
        long salesContractChangeApplyId = Long.parseLong(request.getParameter("salesContractChangeApplyId"));
        //快照表，只有合同金额变更时，才进行快照保存
        SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
        //保存快照
        Long contractSnapShootId = getService().updateChange(oldSalesContractModel, newSalesContractModel, systemUser, changeType, salesContractChangeApplyId);

        //保存快照完成后，更新实体内容
        newSalesContractModel.setContractAmount(salesContractDto.getContractAmount());
        newSalesContractModel.setIsChanged(1);
        Set<SalesContractProductModel> salesContractProducts = null;
        if (newSalesContractModel.getContractType() == 3000) { //MA合同金额变更
            salesContractProducts = getService().salesContractProductsMAByAm(salesContractDto, newSalesContractModel);
        } else {
            salesContractProducts = getService().salesContractProductsByAm(salesContractDto, newSalesContractModel);
        }

        List<SalesContractReceivePlanModel> receivePlans = getService().receivePlansDataByDto(salesContractDto, newSalesContractModel, systemUser);
        try {
            //附件
            newSalesContractModel.setAttachIds(salesContractDto.getAttachIds());
            getService().createSalesByChange(newSalesContractModel, receivePlans, salesContractProducts, systemUser, contractSnapShootId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    /**
     * 合同清单变更（设备变化），包括增加备品备件
     * @param request
     * @param response
     * @param salesContractModel
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/doContractProductChange")
    public String doContractProductChange(HttpServletRequest request, HttpServletResponse response, SalesContractDto salesContractDto, Errors errors) throws Exception {
        //查询出变更前的合同
        SalesContractModel oldSalesContractModel = getService().get(salesContractDto.getId());
        //构造变更后的合同
        SalesContractModel newSalesContractModel = oldSalesContractModel;

        //变更的内容类型，如果为5合同清单变更，需要增加订单的变更20141226，还未实现
        int changeType = Integer.parseInt(request.getParameter("changeType"));

        //变更申请的ID
        long salesContractChangeApplyId = Long.parseLong(request.getParameter("salesContractChangeApplyId"));
        //快照表，只有合同金额变更时，才进行快照保存
        SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
        //保存快照
        Long contractSnapShootId = getService().updateChange(oldSalesContractModel, newSalesContractModel, systemUser, changeType, salesContractChangeApplyId);

        //保存快照完成后，更新实体内容
        newSalesContractModel.setContractAmount(salesContractDto.getContractAmount());
        newSalesContractModel.setIsChanged(1);
        Set<SalesContractProductModel> salesContractProducts = null;
        if (changeType == 9 || changeType == 2) {//备品备件和添加设备部不删除原有设备
            if (newSalesContractModel.getContractType() == 3000) { //MA合同金额变更,暂未实现。
                salesContractProducts = getService().salesContractProductsMAByAm(salesContractDto, newSalesContractModel);
            } else {//现只有产品合同的清单变更。
                salesContractProducts = getService().salesContractProductsByAm(salesContractDto, newSalesContractModel);
            }
        } else {
            if (newSalesContractModel.getContractType() == 3000) { //MA合同金额变更,暂未实现。
                getService().deleteSalesProducts(newSalesContractModel);//删除产品
                salesContractProducts = getService().salesContractProductsMAByPro(salesContractDto, newSalesContractModel);
            } else {//现只有产品合同的清单变更。
                getService().deleteSalesProducts(newSalesContractModel);//删除产品
                salesContractProducts = getService().salesContractProductsByPro(salesContractDto, newSalesContractModel);
            }
        }
        try {
            if (changeType == 5 || changeType == 9) {//未实现，目前按订单不变更处理，后续需要完善
                //自动区分是增加设备，还是减少设备，增加设备订单不变，减少设备订单改变
                List<SalesContractReceivePlanModel> receivePlans = getService().receivePlansDataByDto(salesContractDto, newSalesContractModel, systemUser);

                //附件
                newSalesContractModel.setAttachIds(salesContractDto.getAttachIds());

                getService().createSalesByChange(newSalesContractModel, receivePlans, salesContractProducts, systemUser, contractSnapShootId);
            } else if (changeType == 2) {//备品备件
                getService().changeSalesParts(newSalesContractModel, salesContractProducts, systemUser);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    /**
     * <code>doContractOtherChange</code>
     * 对合同不涉及金额的信息进行变更，不重新走审批流程
     * @param request
     * @param response
     * @param manageModel
     * @return String
     * @since 2014年5月19日    3unshine
     */
    @ResponseBody
    @RequestMapping(value = "/doContractOtherChange")
    public String doContractOtherChange(HttpServletRequest request, HttpServletResponse response, SalesContractDto salesContractDto) {
        try {
            //得到合同ID
            //查询出变更前的合同
            SalesContractModel oldSalesContractModel = getService().get(salesContractDto.getId());
            //构造变更后的合同
            SalesContractModel newSalesContractModel = oldSalesContractModel;

            //变更的内容类型，如果为5合同清单变更，需要增加订单的变更20141226，还未实现
            int changeType = Integer.parseInt(request.getParameter("changeType"));

            //变更申请的ID
            long salesContractChangeApplyId = Long.parseLong(request.getParameter("salesContractChangeApplyId"));
            //变更的内容类型

            //快照表
            SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
            Long contractSnapShootId = getService().updateChange(oldSalesContractModel, newSalesContractModel, systemUser, changeType, salesContractChangeApplyId);

            if (changeType == 8) {//收款计划
                List<SalesContractReceivePlanModel> receivePlans = getService().receivePlansDataByDto(salesContractDto, newSalesContractModel, systemUser);
                getService().changeReceivePlan(newSalesContractModel, receivePlans);
            } else if (changeType == 4) {//合同其他信息
                getService().changeSalesOther(oldSalesContractModel, salesContractDto);
            }
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return FAIL;
        }
    }

    /**
     * <code>doContractChange</code>
     * 对合同信息进行变更
     * @param request
     * @param response
     * @param manageModel
     * @return String
     * @since 2014年5月19日    3unshine
     */
    @ResponseBody
    @RequestMapping(value = "/doContractChange")
    public String doContractChange(HttpServletRequest request, HttpServletResponse response, SalesContractModel salesContractModel) {
        //        try {
        //            //得到合同ID
        //            String id = request.getParameter("contractId");
        //            //查询出变更前的合同
        //            SalesContractModel oldSalesContractModel = getService().get(Long.parseLong(id));
        //            //构造变更后的合同
        //            SalesContractModel newSalesContractModel = oldSalesContractModel;
        //            //变更申请的ID
        //            long salesContractChangeApplyId = Long.parseLong(request.getParameter("salesContractChangeApplyId"));
        //            //变更的内容类型
        //            int changeType = Integer.parseInt(request.getParameter("changeType"));
        //            //如果变更类型是1代表变更了合同金额
        //            if (changeType == 1) {
        //                //                BigDecimal contractAmount = new BigDecimal(request.getParameter("contractAmount"));
        //                //List<SalesContractProductModel> salesContractProducts = salesContractProductsDataAnalyst(salesContractModel);
        //                //                newSalesContractModel.setContractAmount(contractAmount);
        //            }
        //            //如果变更类型是2代表变更了合同的备品备件
        //            if (changeType == 2) {
        //                //                BigDecimal contractAmount = new BigDecimal(request.getParameter("contractAmount"));
        //                //List<SalesContractProductModel> salesContractProducts = salesContractProductsDataAnalyst(salesContractModel);
        //                //                newSalesContractModel.setContractAmount(contractAmount);
        //            }
        //            //如果变更类型是3代表变更了合同的XXXX
        //            if (changeType == 3) {
        //            }
        //
        //            //快照宝
        //            SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
        //            Long contractSnapShootId = getService().updateChange(oldSalesContractModel, newSalesContractModel, systemUser, changeType, salesContractChangeApplyId);
        //            //如果变更类型是4代表变更了合同的其他
        //            if (changeType == 4) {
        //                getService().updateSalesByChangeOther(oldSalesContractModel, salesContractModel);
        //                return SUCCESS;
        //            }
        //
        //            //删除合同和订单关联  以及订单关联的此合同的产品
        //            getService().deleteSalesRelateOrders(oldSalesContractModel);
        //
        //            //更新新的合同
        //            getService().deletes(new String[] { id });
        //            salesContractModel.setId(Long.parseLong(id));
        //            //创建时间
        //            salesContractModel.setCreateTime(new Date());
        //            //创建者
        //            salesContractModel.setCreator(Long.parseLong(systemUser.getUserId()));
        //            salesContractModel.setCreatorName(systemUser.staffName);
        //            salesContractModel.setIsChanged(1);
        //            //得到合同中的产品
        //            //            List<SalesContractProductModel> salesContractProducts = salesContractProductsDataAnalyst(salesContractModel);
        //
        //            Set<SalesContractProductModel> salesContractProducts = null;
        //            if (salesContractModel.getContractType() == 3000) {
        //                //    salesContractProducts = salesContracMAtProductsDataAnalyst(salesContractModel);
        //            } else {
        //                salesContractProducts = salesContractProductsDataAnalyst(salesContractModel);
        //            }
        //
        //            List<SalesContractReceivePlanModel> receivePlans = receivePlansDataAnalyst(salesContractModel, systemUser);
        //            getService().createSalesByChange(salesContractModel, receivePlans, salesContractProducts, systemUser, contractSnapShootId);
        //
        //            //            List<SalesContractProductModel> salesContractProducts = salesContractProductsDataAnalyst(salesContractModel);
        //            //            List<SalesContractReceivePlanModel> receivePlans = receivePlansDataAnalyst(salesContractModel, systemUser);
        //            //            getService().update(salesContractModel, receivePlans, salesContractProducts, systemUser);
        //
        return SUCCESS;
        //        } catch (Exception e) {
        //            e.printStackTrace();
        //            logger.error(e.getMessage());
        //            return FAIL;
        //        }
    }

    /**
     * <code>doUpdate</code>
     * 更新实体
     * @param request
     * @param response
     * @param manageModel
     * @return String
     * @since 2014年5月19日    3unshine
     */
    @ResponseBody
    @RequestMapping(value = "/doUpdate")
    public String doUpdate(HttpServletRequest request, HttpServletResponse response, SalesContractModel salesContractModel, Errors errors) {
        try {

            if (errors.hasErrors()) {
                return FAIL;
            }

            List<SalesContractProductModel> salesContractProducts = null;
            SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
            List<SalesContractReceivePlanModel> receivePlans = receivePlansDataAnalyst(salesContractModel, systemUser);
            List<SalesInvoicePlanModel> invoicePlans = invoicePlansDataAnalyst(salesContractModel, systemUser);
            if (salesContractModel.getContractType() == 3000) {
                salesContractProducts = salesContracMAtProductsDataAnalyst(salesContractModel);
            } else {
                salesContractProducts = salesContractProductsDataAnalyst(salesContractModel);
            }
            salesContractModel.setOrgCode(systemUser.getSysOrgnization().getOrgCode());
            //创建者
            String isSubmit = request.getParameter("isSubmit");
            salesContractModel.setContractState(isSubmit);
            getService().update(salesContractModel, receivePlans, salesContractProducts, systemUser, invoicePlans);
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return FAIL;
        }
    }

    /**
     * <code>invoicePlansDataAnalyst</code>
     * 前台传入数据后进行|发票计划|的数据的整理
     * @param salesContractModel 销售合同实体
     * @param systemUser 当前登录用户实体
     * @return List<SalesInvoicePlanModel>
     * @since   2016年3月28日    wangya
     */
    @SuppressWarnings("unchecked")
    private List<SalesInvoicePlanModel> invoicePlansDataAnalyst(SalesContractModel salesContractModel, SystemUser systemUser) {
        List<SalesInvoicePlanModel> invoicePlans = new ArrayList<SalesInvoicePlanModel>();
        ObjectMapper objectMapper = JacksonJsonMapper.getInstance();
        Map<String, Object> tableDataMap = null;
        String tableData = salesContractModel.getTableData2();
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
                    logger.info("key= " + key + " and value= " + tableDataMap.get(key));
                    List<Object> gridDataList = (List<Object>) tableDataMap.get(key);
                    List<Map<String, Object>> gridList = new ArrayList<Map<String, Object>>();
                    for (int i = 0; (gridDataList != null && i < gridDataList.size()); i++) {//加了非空判断
                        Map<String, Object> map = CodeUtils.stringToMap(gridDataList.get(i).toString());//格式转换
                        if (map.size() != 0) {
                            gridList.add(map);
                        }
                    }
                    for (int i = 0; i < gridList.size(); i++) {
                        Map<String, Object> map = gridList.get(i);
                        String planedInvoiceDateString = map.get("planedInvoiceDate").toString();
                        Date planedReceiveDate = new Date();
                        if (planedInvoiceDateString.indexOf(":") > 0) {
                            planedReceiveDate = DateUtils.convertStringToDate(planedInvoiceDateString, pattern);
                        } else {
                            planedReceiveDate = DateUtils.convertStringToDate(planedInvoiceDateString, DatePattern);
                        }
                        String planCondition = map.get("planCondition") == null ? "" : map.get("planCondition").toString();
                        BigDecimal planedInvoiceAmount = new BigDecimal(map.get("planedInvoiceAmount").toString());
                        SalesInvoicePlanModel invoicePlan = new SalesInvoicePlanModel();
                        long id = IdentifierGeneratorImpl.generatorLong();
                        invoicePlan.setId(id);
                        //创建时间
                        invoicePlan.setCreateTime(new Date());
                        invoicePlan.setCreator(Long.valueOf(systemUser.getUserId()).longValue());
                        invoicePlan.setRemark(planCondition);
                        invoicePlan.setInvoiceAmount(planedInvoiceAmount);
                        invoicePlan.setInvoiceTime(planedReceiveDate);
                        invoicePlan.setSalesContractId(salesContractModel.getId());
                        invoicePlan.setInvoiceType(Integer.parseInt(salesContractModel.getInvoiceType()));
                        invoicePlan.setSalesContractName(salesContractModel.getContractName());
                        invoicePlan.setInvoiceStatus("CG");//草稿
                        invoicePlans.add(invoicePlan);
                    }
                }
            }
        }
        return invoicePlans;
    }

    /**
     * <code>remove</code>
     * 删除合同以及与之相对应的收款计划，产品
     * @param request
     * @param response
     * @param id 合同的ID
     * @return String
     * @since   2014年5月22日    3unshine
     */
    @ResponseBody
    @RequestMapping(value = "/remove")
    public String remove(HttpServletRequest request, HttpServletResponse response, @RequestParam("id") String ids) {
        try {
            String[] id = ids.substring(0, ids.lastIndexOf(",")).split(",");
            getService().deletes(id);
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return FAIL;
        }
    }

    /**
     * <code>detail</code>
     * 查看合同详情
     * @param request
     * @param response
     * @return ModelAndView
     * @since 2014年5月26日    3unshine
     */
    @RequestMapping(value = "/detail")
    public ModelAndView detail(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("sales/contract/detail");
        String id = request.getParameter("id") != null ? request.getParameter("id") : request.getAttribute("contractId") + "";
        String isFlow = request.getAttribute("isFlow") + "";
        String taskId = request.getAttribute("taskId") + "";
        String flowStep = request.getAttribute("flowStep") + "";
        String procInstId = request.getAttribute("procInstId") + "";
        String formDataJson = "";
        //        String opts = request.getParameter("opts");
        String opts = "";

        SalesContractModel salesContractModel = null;
        synchronized (id) {
            //取出合同实体发送到前台页面
            if (taskId != null && taskId != "")
                request.setAttribute("taskId", taskId);
            if (procInstId != null && procInstId != "")
                request.setAttribute("procInstId", procInstId);
            if (flowStep != null && flowStep != "")
                request.setAttribute("flowStep", flowStep);
            if (id != null && !"".equals(id)) {
                salesContractModel = getService().get(Long.parseLong(id));
                mav.addObject("model", salesContractModel);
                //判断合同状态
                Map<String, Object> map = getService().getSaleStateById(salesContractModel.getId());
                Object contractState = "";
                if (map == null) {
                } else {
                    contractState = map.get("ContractState");
                }
                if (contractState.toString().equals(SalesContractConstant.CONTRACT_STATE_CG)) {
                    opts = "draft,";
                } else if (contractState.toString().equals(SalesContractConstant.CONTRACT_STATE_TGSH)) {
                    String invoiceStatus = map.get("InvoiceStatus").toString();
                    String changeStatus = map.get("changeStatus").toString();

                    String cachetStatus = map.get("CachetStatus").toString();
                    String orderStatus = map.get("OrderStatus").toString();

                    if ((invoiceStatus.equals("未申请") && !invoiceStatus.equals("变更申请通过") && !invoiceStatus.equals("变更申请中")) || invoiceStatus.equals("待重新申请")) {
                        opts += opts + "Invoice,";
                    }
                    if ((cachetStatus.equals("未申请") && !changeStatus.equals("变更申请通过") && !changeStatus.equals("变更申请中")) || cachetStatus.equals("待重新申请")) {
                        opts += opts + "Cachet,";
                    }
                    //                    if (changeStatus.equals("未申请") && cachetStatus.equals("未申请") && invoiceStatus.equals("未申请") && orderStatus.equals("未采购")) {
                    //                        opts += opts + "ChangeApply,";
                    //                    }
                    //变更申请，只有发票和盖章已经申请的情况下才不允许变更
                    if ((!changeStatus.equals("变更申请通过") && !changeStatus.equals("变更申请中") && !invoiceStatus.equals("审批通过") && cachetStatus.equals("未申请"))) {
                        opts += "ChangeApply,";
                    }
                    if (changeStatus.equals("变更申请通过")) {
                        opts += opts + "Change,";
                    }
                    if (changeStatus.equals("变更申请中") || changeStatus.equals("变更申请通过")) {
                        opts += opts + "ChangeDetail,";
                    }
                    if (changeStatus.equals("变更申请不通过")) {
                        opts += opts + "ChangeDetail,";
                    }
                }
                //预测发票列表
                List<SalesBudgetFunds> invoice = fundsSalesBudgetService.getInvoiceList(salesContractModel.getId());
                if (invoice.size() > 0) {
                    request.setAttribute("invoiceBudget", invoice);
                }
                List<SalesBudgetFunds> receive = fundsSalesBudgetService.getReceiveList(salesContractModel.getId());
                if (receive.size() > 0) {
                    request.setAttribute("receiveBudget", receive);
                }
            }
            //审批日志
            List<ProcInstAppr> proInstLogList = processService.findProcInstApprLog(salesContractModel.getProcessInstanceId());
            mav.addObject("proInstLogList", proInstLogList);
            //发票计划
            List<SalesInvoicePlanModel> salesInvoicePlanModels = new ArrayList<SalesInvoicePlanModel>();
            DetachedCriteria detachedCriteria = DetachedCriteria.forClass(SalesInvoicePlanModel.class);
            detachedCriteria.add(Restrictions.eq(SalesInvoicePlanModel.CONTRACTID, Long.parseLong(id)));
            detachedCriteria.add(Restrictions.ne(SalesInvoicePlanModel.INVOICESTATUS, "FQ"));
            detachedCriteria.add(Restrictions.ne(SalesInvoicePlanModel.INVOICESTATUS, "BG"));
            salesInvoicePlanModels = salesInvoiceService.getSalesInvoicePlanListByContractId(detachedCriteria);
            request.setAttribute("invoice", salesInvoicePlanModels);
            int flats = 0;
            Iterator<SalesInvoicePlanModel> invoiec = salesInvoicePlanModels.iterator();
            while (invoiec.hasNext()) {
                SalesInvoicePlanModel i = invoiec.next();
                if (i.getProcessInstanceId() == null) {
                    flats = 1;
                }
            }
            if (SalesContractConstant.CONTRACT_STATE_TGSH.equals(salesContractModel.getContractState())) {
                request.setAttribute("invoiceStatus", flats);
            }
            // 如果合同发生过变更，取出变更过的历史快照信息
            if (salesContractModel.getIsChanged() == 1) {
                List<Map<String, Object>> ContractSnapShootList = contractSnapShootService.getContractSnapShootsByContractId(salesContractModel.getId());
                request.setAttribute("ContractSnapShootList", ContractSnapShootList);
            }
            //如果合同发生过合并，显示合并的合同名称
            SalseContractMergeModel merge = salseContractMergeService.getSalesMergeBySalesId(salesContractModel.getId());
            if (merge != null) {
                List<SalesContractModel> salesMerge = getService().getSalesIds(merge.getContractIds());
                for (int i = 0; i < salesMerge.size(); i++) {
                    SalesContractModel salesContractModel2 = salesMerge.get(i);
                    if (salesContractModel2.getId() == salesContractModel.getId()) {
                        salesMerge.remove(salesContractModel2);
                    }
                }
                request.setAttribute("merge", salesMerge);
                request.setAttribute("mergelog", merge);
            }
            //商务主管选择采购员，若合同产品都由备货合同来，则不选择采购员。flat=0（都是备货产品或没有产品）
            if (flowStep.equals("SWJLSP")) {
                Set<SalesContractProductModel> product = salesContractModel.getSalesContractProductModel();
                int flat = 0;
                Iterator<SalesContractProductModel> t = product.iterator();
                while (t.hasNext()) {
                    SalesContractProductModel p = t.next();
                    if (p.getRelateDeliveryProductId() == null) {
                        flat = 1;
                    }
                }
                request.setAttribute("flat", flat);
            }

            //拼凑出tableGrid所需的Json数据
            //回款
            formDataJson = tableGridDataUnit(salesContractModel, "detail");
            request.setAttribute("form", formDataJson);

            request.setAttribute("isFlow", isFlow);
            if (flowStep.equals("SHOW")) {
                opts = "";
            }

            SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
            if (!systemUser.getUserId().equals(String.valueOf(salesContractModel.getCreator()))) {
                opts = null;
            }

            if (opts != null) {

                if (opts.contains("draft")) {
                    request.setAttribute("draft", "1");
                } else {
                    if (opts.contains("Invoice")) {
                        request.setAttribute("InvoiceStatus", "1");
                    }
                    if (opts.contains("Cachet")) {
                        request.setAttribute("Cachet", "1");
                    }
                    if (opts.contains("ChangeApply")) {
                        request.setAttribute("ChangeApply", "1");
                        opts = opts.replaceAll("ChangeApply", "1");
                    }
                    if (opts.contains("ChangeDetail")) {
                        request.setAttribute("ChangeDetail", "1");
                        opts = opts.replaceAll("ChangeDetail", "1");
                    }
                    if (opts.contains("Change")) {
                        request.setAttribute("Change", "1");
                    }

                }
            }
        }

        //盖章审批日志
        SalesCachetModel cachet = cachetService.getBysalesContractId(salesContractModel.getId());
        Set<String> userSet = processService.getUserIdSetByProcInst(cachet.getProcessInstanceId(), ProcessConstants.ROLE_ASSIGNEE);
        Iterator<String> it = userSet.iterator();
        while (it.hasNext()) {
            String str = it.next();
            mav.addObject("approName", ((SysStaff) systemUserCache.get(str).getObjectValue()).getStaffName());

        }
        List<ProcInstAppr> cachetLogList = processService.findProcInstApprLog(cachet.getProcessInstanceId());
        mav.addObject("cachetLogList", cachetLogList);
        List<Map<String, Object>> salesCode = new ArrayList<Map<String, Object>>();
        if (salesContractModel.getContractType() == 9000) {
            salesCode = getService().findProSalesCode(salesContractModel.getId());
            mav.addObject("salesCode", salesCode);
        }

        return mav;
    }

    /**
     * <code>toAddOrUpdateProductsView</code>
     * 弹出toAddOrUpdateProductsView页面
     * @param request
     * @param response
     * @return ModelAndView
     * @since 2014年5月05日     3unshine
     */
    @RequestMapping(value = "/toAddOrUpdateProductsView")
    public ModelAndView toAddOrUpdateProductsView(HttpServletRequest request, HttpServletResponse response, SalesContractProductModel salesContractProductModel) {
        ModelAndView mav = new ModelAndView("sales/contract/products/addOrUpdateProducts");
        mav.addObject("model", salesContractProductModel);
        return mav;
    }

    /**
     * <code>SalesContractFlowSteps</code>
     * 用于流程控制所需的8个URL中标示的枚举类
     * @since   2014年6月09日    3unshine
     */
    public enum SalesContractFlowSteps {
        //STATR 开始
        //HGXSC 合规性审查
        //CXTJ 重新提交
        //HYYXZJSP  行业营销总监审批
        //BMJLSP   部门经理审批
        //SWJLSP  商务经理审批
        //SWZGSP  商务主管审批
        //SHOW 查看权限
        HGXSH, CXTJ, HYYXZJSP, BMJLSP, SWJLSP, SWZGSP, SHOW, END;
    }

    /**
     * <code>manage</code>
     * 用于流程控制所需的URL
     * @param request
     * @param response
     * @return
     * @since   2014年5月05日    3unshine
     */
    @RequestMapping(value = "/contractFlowManage")
    public ModelAndView contractFlowManage(HttpServletRequest request, HttpServletResponse response) {
        String flowStep = request.getParameter("flowStep");
        String taskId = request.getParameter("taskId");
        String procInstId = request.getParameter("procInstId");
        String isFlow = "isFlow";
        //        SalesContractModel salesContractModel = (SalesContractModel) processService.getVariable(taskId, "salesContractModel");

        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(SalesContractModel.class);
        detachedCriteria.add(Restrictions.eq(SalesContractModel.PROCESSINSTANCEID, Long.parseLong(procInstId)));
        List<SalesContractModel> contractList = getService().findByCriteria(detachedCriteria);
        SalesContractModel salesContractModel = null;
        if (contractList.size() > 0) {
            salesContractModel = contractList.get(0);
        } else {
            ModelAndView mav = new ModelAndView(new RedirectView(request.getContextPath() + "/sales/contractSnapShoot/toDetail?procInstId=" + procInstId));
            return mav;
        }

        long contractId = salesContractModel.getId();
        request.setAttribute("contractId", contractId);
        request.setAttribute("taskId", taskId);
        request.setAttribute("procInstId", procInstId);
        request.setAttribute("flowStep", flowStep);
        switch (SalesContractFlowSteps.valueOf(flowStep)) {
            case HGXSH:
                logger.info("HGXSH");
                request.setAttribute("isFlow", isFlow);
                return detail(request, response);
            case CXTJ:
                logger.info("CXTJ");
                request.setAttribute("flowFlag", "CXTJ");
                return toUpdate(request, response);
            case HYYXZJSP:
                request.setAttribute("isFlow", isFlow);
                logger.info("HYYXZJSP");
                return detail(request, response);
            case BMJLSP:
                request.setAttribute("isFlow", isFlow);
                logger.info("BMJLSP");
                return detail(request, response);
            case SWJLSP:
                request.setAttribute("isFlow", isFlow);
                logger.info("SWJLSP");
                return detail(request, response);
            case SWZGSP:
                request.setAttribute("isFlow", isFlow);
                logger.info("SWZGSP");
                return detail(request, response);
            case SHOW:
                logger.info("SHOW");
                return detail(request, response);
            case END:
                return null;
        }
        return null;
    }

    /**
     * <code>handleFlow</code>
     * 处理流程
     * @param request
     * @param response
     * @return
     * @since   2014年6月16日    3unshine
     */
    @ResponseBody
    @RequestMapping(value = "/handleFlow")
    public String handleFlow(HttpServletRequest request, HttpServletResponse response, SalesContractModel salesContractModel) {
        //        ModelAndView mav = new ModelAndView("sales/contract/manage");
        String taskId = request.getParameter("taskId");
        String flowFlag = request.getParameter("flowFlag");
        String procInstId = request.getParameter("procInstId");
        int isAgree = Integer.parseInt(request.getParameter("isAgree") == null ? "1" : request.getParameter("isAgree"));
        String remark = request.getParameter("remark");

        //创建者
        SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
        List<SalesContractReceivePlanModel> receivePlans = receivePlansDataAnalyst(salesContractModel, systemUser);
        List<SalesInvoicePlanModel> invoicePlans = invoicePlansDataAnalyst(salesContractModel, systemUser);
        List<SalesContractProductModel> salesContractProducts = null;

        if (salesContractModel.getContractType() == 3000) {
            salesContractProducts = salesContracMAtProductsDataAnalyst(salesContractModel);
        } else {
            salesContractProducts = salesContractProductsDataAnalyst(salesContractModel);
        }
        salesContractModel.setOrgCode(systemUser.getSysOrgnization().getOrgCode());

        //流程处理service
        try {
            getService().handleFlow(flowFlag, salesContractModel, receivePlans, salesContractProducts, systemUser, procInstId, taskId, isAgree, remark, invoicePlans);
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return FAIL;
        }
    }

    /**
     * <code>salesContracMAtProductsDataAnalyst</code>
     * 合同MA产品
     * @param salesContractModel
     * @return
     * @since   2014年11月20日    guokemenng
     */
    public List<SalesContractProductModel> salesContracMAtProductsDataAnalyst(SalesContractModel salesContractModel) {
        List<SalesContractProductModel> salesContractProducts = new ArrayList<SalesContractProductModel>();

        //        long[] productTypes = salesContractModel.getProductTypes();
        //        String[] productTypeNames = salesContractModel.getProductTypeNames();
        long[] productPartners = salesContractModel.getProductPartners();
        String[] productPartnerNames = salesContractModel.getProductPartnerNames();
        long[] productNos = salesContractModel.getProductNos();
        String[] productNames = salesContractModel.getProductNames();
        int[] quantitys = salesContractModel.getQuantitys();
        String[] serialNumber = salesContractModel.getSerialNumber();
        int[] servicePeriod = salesContractModel.getServicePeriod();
        String[] serviceStartDates = salesContractModel.getServiceStartDates();
        String[] serviceEndDates = salesContractModel.getServiceEndDates();
        BigDecimal[] unitPrices = salesContractModel.getUnitPrices();
        BigDecimal[] totalPrices = salesContractModel.getTotalPrices();
        String[] equipmentSplace = salesContractModel.getEquipmentSplace();

        if (quantitys != null) {
            for (int i = 0; i < quantitys.length; i++) {
                SalesContractProductModel salesContractProduct = new SalesContractProductModel();
                salesContractProduct.setProductPartner(productPartners[i]);
                salesContractProduct.setProductPartnerName(productPartnerNames[i]);
                salesContractProduct.setProductName(productNames[i]);
                salesContractProduct.setProductNo(productNos[i]);
                if (serialNumber.length > i) {
                    salesContractProduct.setSerialNumber(serialNumber[i]);
                }
                salesContractProduct.setServicePeriod(servicePeriod[i]);
                salesContractProduct.setServiceStartDate(DateUtils.convertStringToDate(serviceStartDates[i], DatePattern));
                salesContractProduct.setServiceEndDate(DateUtils.convertStringToDate(serviceEndDates[i], DatePattern));
                if (equipmentSplace.length > i) {
                    salesContractProduct.setEquipmentSplace(equipmentSplace[i]);
                }

                salesContractProduct.setQuantity(quantitys[i]);
                salesContractProduct.setTotalPrice(totalPrices[i]);
                salesContractProduct.setUnitPrice(unitPrices[i]);
                salesContractProduct.setSalesContractModel(salesContractModel);
                salesContractProducts.add(salesContractProduct);
            }
        }

        return salesContractProducts;
    }

    /**
     * <code>salesContractProductsDataAnalyst</code>
     * 前台传入数据后进行|合同产品|的数据的整理
     * @param salesContractModel 销售合同实体
     * @return List<SalesContractProductModel>
     * @since   2014年6月23日    3unshine
     */
    public List<SalesContractProductModel> salesContractProductsDataAnalyst(SalesContractModel salesContractModel) {
        List<SalesContractProductModel> salesContractProducts = new ArrayList<SalesContractProductModel>();
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();

        long[] productTypes = salesContractModel.getProductTypes();
        String[] productTypeNames = salesContractModel.getProductTypeNames();
        long[] productPartners = salesContractModel.getProductPartners();
        String[] productPartnerNames = salesContractModel.getProductPartnerNames();
        long[] productNos = salesContractModel.getProductNos();
        String[] productNames = salesContractModel.getProductNames();
        int[] quantitys = salesContractModel.getQuantitys();
        BigDecimal[] unitPrices = salesContractModel.getUnitPrices();
        BigDecimal[] totalPrices = salesContractModel.getTotalPrices();

        String[] serviceStartDates = salesContractModel.getServiceStartDates();
        String[] serviceEndDates = salesContractModel.getServiceEndDates();
        String[] productRemarks = salesContractModel.getProductRemarks();

        Long[] relateContractProductIds = salesContractModel.getRelateContractProductId();
        Long[] relateDeliveryProductIds = salesContractModel.getRelateDeliveryProductId();

        if (quantitys != null) {
            for (int i = 0; i < quantitys.length; i++) {
                SalesContractProductModel salesContractProduct = new SalesContractProductModel();
                salesContractProduct.setProductType(productTypes[i]);
                salesContractProduct.setProductTypeName(productTypeNames[i]);
                salesContractProduct.setProductPartner(productPartners[i]);
                salesContractProduct.setProductPartnerName(productPartnerNames[i]);
                salesContractProduct.setProductName(productNames[i]);

                if (serviceStartDates.length > 0 && !StringUtil.isEmpty(serviceStartDates[i])) {
                    salesContractProduct.setServiceStartDate(DateUtils.convertStringToDate(serviceStartDates[i], DatePattern));
                }
                if (serviceEndDates.length > 0 && !StringUtil.isEmpty(serviceEndDates[i])) {
                    salesContractProduct.setServiceEndDate(DateUtils.convertStringToDate(serviceEndDates[i], DatePattern));
                }

                if (productRemarks != null && productRemarks.length > 0 && !StringUtil.isEmpty(productRemarks[i])) {
                    salesContractProduct.setRemark(productRemarks[i]);
                }

                //                salesContractProduct.setProductName(productNames[i]);
                salesContractProduct.setProductNo(productNos[i]);
                salesContractProduct.setQuantity(quantitys[i]);
                salesContractProduct.setTotalPrice(totalPrices[i]);
                salesContractProduct.setUnitPrice(unitPrices[i]);
                salesContractProduct.setSalesContractModel(salesContractModel);
                if (relateDeliveryProductIds.length > 0 && relateDeliveryProductIds[i] != null) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("relateDeliveryProductId", relateDeliveryProductIds[i]);
                    map.put("productNum", quantitys[i]);
                    map.put("relateContractProductId", relateContractProductIds[i]);
                    mapList.add(map);

                    salesContractProduct.setRelateDeliveryProductId(relateDeliveryProductIds[i]);

                    salesContractProduct.setIsReady(1);
                }
                salesContractProducts.add(salesContractProduct);
            }
        }

        handlerRelateContractProducts(mapList, salesContractProducts);

        return salesContractProducts;
    }

    /**
     * <code>handlerRelateContractProducts</code>
     * 假如是关联备货合同  对备货合同产品数量进行增减
     * @param mapList
     * @since   2014年9月28日    guokemenng
     */
    public void handlerRelateContractProducts(List<Map<String, Object>> mapList, List<SalesContractProductModel> salesContractProducts) {
        for (Map<String, Object> map : mapList) {
            //新建合同
            if (map.get("relateContractProductId").toString().equals("0")) {
                SalesContractProductModel salesContractProduct = salesContractProductService.get(Long.parseLong(map.get("relateDeliveryProductId").toString()));
                int countNum = salesContractProduct.getSurplusNum();
                int surplusNum = countNum + Integer.parseInt(map.get("productNum").toString());
                salesContractProduct.setSurplusNum(surplusNum);
                salesContractProductService.update(salesContractProduct);
            } else {
                SalesContractProductModel relateDeliveryProduct = salesContractProductService.get(Long.parseLong(map.get("relateDeliveryProductId").toString()));
                SalesContractProductModel salesProduct = salesContractProductService.get(Long.parseLong(map.get("relateContractProductId").toString()));
                int originalNum = relateDeliveryProduct.getSurplusNum() - salesProduct.getQuantity();
                originalNum += Integer.parseInt(map.get("productNum").toString());
                relateDeliveryProduct.setSurplusNum(originalNum);
                salesContractProductService.update(relateDeliveryProduct);
            }
        }
    }

    /**
     * <code>deleteSalesProduct</code>
     * 合同删除关联备货合同的产品
     * @param request
     * @param response
     * @since   2014年9月28日    guokemenng
     */
    @RequestMapping(value = "/deleteSalesProduct")
    public void deleteSalesProduct(HttpServletRequest request, HttpServletResponse response) {
        //关联备货合同产品ID
        String id1 = request.getParameter("id1");
        String id2 = request.getParameter("id2");
        if (!StringUtil.isEmpty(id1)) {
            SalesContractProductModel relateDeliveryProduct = salesContractProductService.get(Long.parseLong(id1));
            SalesContractProductModel salesProduct = salesContractProductService.get(Long.parseLong(id2));
            int originalNum = relateDeliveryProduct.getSurplusNum() - salesProduct.getQuantity();
            relateDeliveryProduct.setSurplusNum(originalNum);
            salesContractProductService.update(relateDeliveryProduct);
            salesContractProductService.delete(Long.parseLong(id2));
        }
    }

    /**
     * <code>receivePlansDataAnalyst</code>
     * 前台传入数据后进行|收款计划|的数据的整理
     * @param salesContractModel 销售合同实体
     * @param systemUser 当前登录用户实体
     * @return List<SalesContractReceivePlanModel>
     * @since   2014年6月23日    3unshine
     */
    @SuppressWarnings("unchecked")
    public List<SalesContractReceivePlanModel> receivePlansDataAnalyst(SalesContractModel salesContractModel, SystemUser systemUser) {
        List<SalesContractReceivePlanModel> receivePlans = new ArrayList<SalesContractReceivePlanModel>();
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
                    logger.info("key= " + key + " and value= " + tableDataMap.get(key));
                    List<Object> gridDataList = (List<Object>) tableDataMap.get(key);
                    List<Map<String, Object>> gridList = new ArrayList<Map<String, Object>>();
                    for (int i = 0; (gridDataList != null && i < gridDataList.size()); i++) {//加了非空判断
                        Map<String, Object> map = CodeUtils.stringToMap(gridDataList.get(i).toString());//格式转换
                        if (map.size() != 0) {
                            gridList.add(map);
                        }
                    }
                    for (int i = 0; i < gridList.size(); i++) {
                        Map<String, Object> map = gridList.get(i);
                        String planedReceiveDateString = map.get("planedReceiveDate").toString();
                        Date planedReceiveDate = new Date();
                        if (planedReceiveDateString.indexOf(":") > 0) {
                            planedReceiveDate = DateUtils.convertStringToDate(planedReceiveDateString, pattern);
                        } else {
                            planedReceiveDate = DateUtils.convertStringToDate(planedReceiveDateString, DatePattern);
                        }
                        String payCondition = map.get("payCondition") == null ? "" : map.get("payCondition").toString();
                        BigDecimal planedReceiveAmount = new BigDecimal(map.get("planedReceiveAmount").toString());
                        SalesContractReceivePlanModel receivePlan = new SalesContractReceivePlanModel();
                        //创建时间
                        receivePlan.setCreateTime(new Date());
                        receivePlan.setCreator(Long.valueOf(systemUser.getUserId()).longValue());
                        receivePlan.setPayCondition(payCondition);
                        receivePlan.setPlanedReceiveAmount(planedReceiveAmount);
                        receivePlan.setPlanedReceiveDate(planedReceiveDate);
                        receivePlan.setSalesContract(salesContractModel);
                        receivePlans.add(receivePlan);
                    }
                }
            }
        }
        return receivePlans;
    }

    /**
     * <code>tableGridDataUnit</code>
     * 拼接tableGridData所需要的JSON数据
     * @param salesContractModel 销售合同实体
     * @return String
     * @since   2014年6月23日    3unshine
     */
    public String tableGridDataUnit(SalesContractModel salesContractModel, String showType) {
        String formDataJson = "";
        Map<String, Object> tableModel = new HashMap<String, Object>(); //表格数据模型
        tableModel.put("name", "editGridName"); //表格名称
        List<HashMap<String, Object>> rowNames = new ArrayList<HashMap<String, Object>>(); //表格中的列数据

        for (int i = 0; i < columns.length; i++) {
            HashMap<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("name", columns[i]); //字段名称，用于保存数据
            dataMap.put("label", columnNames[i]);//字段在页面上的显示名称
            dataMap.put("type", columnTypes[i]); //表格中输入字段的类型，目前只支持string,date
            if (salesContractModel != null && showType.equals("detail"))
                dataMap.put("boolWrite", false); //表格中输入字段的类型，目前只支持string,date
            rowNames.add(dataMap);
        }

        tableModel.put("fieldList", rowNames);
        List<HashMap<String, Object>> rowDatas = new ArrayList<HashMap<String, Object>>(); //表格总的数据，一行数据对应一个map
        if (salesContractModel != null) {
            Set<SalesContractReceivePlanModel> salesContractReceivePlans = salesContractModel.getSalesContractReceivePlans();
            int rows = salesContractModel.getSalesContractReceivePlans().size();
            tableModel.put("defaultAmount", rows);//新建表格时，默认为0，如果是修改，应该表格数据的总行数。
            for (SalesContractReceivePlanModel s : salesContractReceivePlans) { //放入2行数据
                HashMap<String, Object> dataMap = new HashMap<String, Object>(); //因为先前在rowNames中定义表格有3列
                if (showType.equals("update")) {
                    dataMap.put(columns[0], s.getPlanedReceiveAmount());
                } else {
                    dataMap.put(columns[0], "￥" + getShowNumber(s.getPlanedReceiveAmount()));
                }
                dataMap.put(columns[1], s.getPlanedReceiveDate());
                dataMap.put(columns[2], s.getPayCondition());
                rowDatas.add(dataMap);
            }
        } else {
            tableModel.put("defaultAmount", 0);//新建表格时，默认为0，如果是修改，应该表格数据的总行数。
            //一开始进入到addSalesContract.jsp时，走的是这里
            //往tableModel中放入和公司有关信息的数据

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
     * @param string
     * @return
     */
    private String tableGridDataUnit2(SalesContractModel salesContractModel, String showType) {
        String formDataJson = "";
        Map<String, Object> tableModel = new HashMap<String, Object>(); //表格数据模型
        tableModel.put("name", "editGridName2"); //表格名称
        List<HashMap<String, Object>> rowNames = new ArrayList<HashMap<String, Object>>(); //表格中的列数据

        for (int i = 0; i < columns2.length; i++) {
            HashMap<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("name", columns2[i]); //字段名称，用于保存数据
            dataMap.put("label", columnNames2[i]);//字段在页面上的显示名称
            dataMap.put("type", columnTypes2[i]); //表格中输入字段的类型，目前只支持string,date
            if (salesContractModel != null && showType.equals("detail"))
                dataMap.put("boolWrite", false); //表格中输入字段的类型，目前只支持string,date
            rowNames.add(dataMap);
        }

        tableModel.put("fieldList", rowNames);
        List<HashMap<String, Object>> rowDatas = new ArrayList<HashMap<String, Object>>(); //表格总的数据，一行数据对应一个map
        if (salesContractModel != null) {
            List<SalesInvoicePlanModel> invoicePlan = getService().getSalesInvoicePlan(salesContractModel.getId());
            int rows = invoicePlan.size();
            tableModel.put("defaultAmount", rows);//新建表格时，默认为0，如果是修改，应该表格数据的总行数。
            for (SalesInvoicePlanModel s : invoicePlan) { //放入2行数据
                HashMap<String, Object> dataMap = new HashMap<String, Object>(); //因为先前在rowNames中定义表格有3列
                if (showType.equals("update")) {
                    dataMap.put(columns2[0], s.getInvoiceAmount());
                } else {
                    dataMap.put(columns2[0], "￥" + getShowNumber(s.getInvoiceAmount()));
                }
                dataMap.put(columns2[1], s.getInvoiceTime());
                dataMap.put(columns2[2], s.getRemark());
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

    public static String getShowNumber(BigDecimal number) {

        String sb = "";
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        dfs.setDecimalSeparator('.');
        dfs.setGroupingSeparator(',');
        dfs.setMonetaryDecimalSeparator('.');
        DecimalFormat df = new DecimalFormat("###,###.##", dfs);
        sb = df.format(number);
        if (!sb.contains(".")) {
            sb = sb + ".00";
        }
        return sb.toString();
    }

    /**
     * <code>creatContractCode</code>
     * 生成合同编号的AJAX方法
     * @param request
     * @param response
     * @return
     * @since 2014年6月25日    3unshine
     */
    @RequestMapping(value = "/creatContractCode")
    @ResponseBody
    public String creatContractCode(HttpServletRequest request, HttpServletResponse response) {
        //得到客户CODE
        String customerInfoCode = request.getParameter("customerInfoCode");
        //查询出当天有多少合同新增进来
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(SalesContractModel.class);
        long startTime = DateUtils.getStartTime();
        long endTime = DateUtils.getEndTime();
        detachedCriteria.add(Restrictions.ge(SalesContractModel.CREATETIME, new Date(startTime)));
        detachedCriteria.add(Restrictions.le(SalesContractModel.CREATETIME, new Date(endTime)));
        List<SalesContractModel> salesContractModels = this.getService().findSalesContractsByCriteria(detachedCriteria);
        int serialNum = salesContractModels.size();
       /* Map<String, Object> map = getService().findCompanyInfo().get(0);
        String code = "";
        try {
            code = map.get("companyCode") + "-" + customerInfoCode + "-" + DateUtils.convertDateToString(new Date(), "yyyyMMdd") + "-" + (serialNum + 1);
        } catch (ParseException e) {
            e.printStackTrace();
        }*/
        String contractCode = CodeUtils.creatCode(customerInfoCode, serialNum);
        return contractCode;
    }

    /**
     * <code>fileUploadData</code>
     * AJAX获取附件
     * @param request
     * @param response
     * @return
     * @since 2014年7月8日     3unshine
     */
    @RequestMapping(value = "/fileUploadData")
    @ResponseBody
    public FileVo fileUploadData(HttpServletRequest request) {
        FileVo fileVo = FileUtils.creatFileUploadData();
        SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
        fileVo.setUsername(systemUser.getUserName());
        fileVo.setStaffname(systemUser.getStaffName());
        String flag = request.getParameter("fileUploadFlag");
        if (!flag.equals("add")) {
            long salesContractId = Long.parseLong(request.getParameter("salesContractId"));
            SalesContractModel salesContractModel = getService().get(salesContractId);
            if (!StringUtil.isEmpty(salesContractModel.getAttachIds())) {
                String[] attachIds = salesContractModel.getAttachIds().split(",");
                Long[] ids = CodeUtils.stringArrayToLongArray(attachIds);
                fileVo.setValue(fileService.findSysAttachByIds(ids));
            }
        }
        if (flag.equals("detail")) {
            fileVo.setBoolRequire(false);
            fileVo.setBoolWrite(false);
        }
        return fileVo;
    }

    /**
     * <code>getContractName</code>
     * 得到财务部员工
     * @param request
     * @param response
     * @return
     * @since   2014年7月30日    3unshine
     */
    @ResponseBody
    @RequestMapping(value = "/getContractName")
    public Map<String, Object> getContractName(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        return getService().getContractName(Long.parseLong(id));
    }

    /**
     * <code>uploadFile</code>
     * 导入产品
     * @param request
     * @param response
     * @return
     * @since   2014年9月17日    guokemenng
     */
    @ResponseBody
    @RequestMapping(value = "/uploadProducts")
    public void uploadProducts(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = null;
        try {
            String state = request.getParameter("state");
            map = getService().uploadProducts(request, state);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            PrintWriter out = null;
            try {
                out = response.getWriter();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //必须设置字符编码，否则返回json会乱码
            response.setContentType("text/html;charset=UTF-8");
            out.write(JSONSerializer.toJSON(map).toString());
            out.flush();
            out.close();
        }
    }

    /**
     * <code>getSalesContractPage</code>
     *
     * @param request
     * @param response
     * @return
     * @since   2014年9月28日    guokemenng
     */
    @ResponseBody
    @RequestMapping(value = "/getSalesContractListPage")
    public ModelAndView getSalesContractListPage(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("sales/contract/getSalesContractListPage");
        return mav;
    }

    /**
     * <code>getSalesContract</code>
     *
     * @param request
     * @param response
     * @return
     * @since   2014年9月28日    guokemenng
     */
    @ResponseBody
    @RequestMapping(value = "/getSalesContractList")
    public Map<String, Object> getSalesContractList(HttpServletRequest request, HttpServletResponse response) {
        String params = request.getParameter("sEcho");
        String iDisplayStart = request.getParameter("iDisplayStart");
        String iDisplayLength = request.getParameter("iDisplayLength");
        if (!StringUtil.isEmpty(iDisplayLength)) {
            this.pageSize = Integer.parseInt(iDisplayLength);
        }
        //
        //        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(SalesContractModel.class);
        //
        //        String contractType = request.getParameter("contractType");
        //        if(!StringUtil.isEmpty(contractType)){
        //            detachedCriteria.add(Restrictions.eq(SalesContractModel.CONTRACTTYPE,Integer.parseInt(contractType)));
        //        }
        //        //当前登录用户
        //        SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
        //        detachedCriteria.add(Restrictions.eq(SalesContractModel.CREATOR,Long.parseLong(systemUser.getUserId())));

        //        PaginationSupport paginationSupport = getService().findPageByCriteria(detachedCriteria, Integer.parseInt(iDisplayStart), this.pageSize);
        PaginationSupport paginationSupport = getService().getSalesContractList(request, Integer.parseInt(iDisplayStart), this.pageSize);
        Map<String, Object> pg = new HashMap<String, Object>();
        pg.put("aaData", paginationSupport.getItems());
        pg.put("sEcho", params);
        pg.put("iTotalDisplayRecords", paginationSupport.getTotalCount());
        pg.put("iTotalRecords", paginationSupport.getTotalCount());
        return pg;
    }

    /**
     * <code>getRelateDeliveryProductList</code>
     * 得到备货合同产品
     * @param request
     * @param response
     * @return
     * @since   2014年9月28日    guokemenng
     */
    @ResponseBody
    @RequestMapping(value = "/getRelateDeliveryProductList")
    public Set<SalesContractProductModel> getRelateDeliveryProductList(HttpServletRequest request, HttpServletResponse response) {
        String salesId = request.getParameter("salesId");
        return getService().get(Long.parseLong(salesId)).getSalesContractProductModel();
    }

    /**
     * <code>contractOrderDetail</code>
     * 合同订单状态
     * @param request
     * @param response
     * @return
     * @since   2014年10月8日    guokemenng
     */
    @RequestMapping(value = "/contractOrderDetail")
    public ModelAndView contractOrderDetail(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("sales/contract/contractOrderDetail");
        Long id = Long.parseLong(request.getParameter("id"));
        SalesContractModel salesContract = getService().get(id);
        mav.addObject("model", salesContract);
        /* Set<BusinessOrderModel> orderSet = salesContract.getBusinessOrderModel();
         mav.addObject("orderModel", orderSet);
         //产品对比：查询合同产品的下单情况
         List<Map<String, Object>> product = getService().getProductDifferent(id);
         mav.addObject("product", product);*/
        List<Map<String, Object>> orderSet = getService().getOrderInfos(id);
        mav.addObject("orderModel", orderSet);
        for (Map<String, Object> businessOrderModel : orderSet) {
            List<Map<String, Object>> orderProduts = getService().getOrderProductDifferents(id, Long.parseLong(businessOrderModel.get("id").toString()));
            businessOrderModel.put("orderProduts", orderProduts);
            double orderCost = 0;
            //计算总的采购成本
            for (Map<String, Object> map : orderProduts) {
                orderCost = orderCost + Double.parseDouble(map.get("subtotal").toString());
            }
            businessOrderModel.put("orderCost", orderCost);
        }
        return mav;
    }

    /**
     * 合同订单
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/contractOrderAllDetail")
    public ModelAndView contractOrderAllDetail(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("sales/contract/contractOrderAllDetail");
        Long id = Long.parseLong(request.getParameter("id"));
        SalesContractModel salesContract = getService().get(id);
        mav.addObject("model", salesContract);
        /*List<Map<String, Object>> orderSet = getService().getOrderInfo(id);
        mav.addObject("orderModel", orderSet);
        for (Map<String, Object> businessOrderModel : orderSet) {
            List<Map<String, Object>> orderProduts = getService().getOrderProductDifferent(id, Long.parseLong(businessOrderModel.get("id").toString()));
            businessOrderModel.put("orderProduts", orderProduts);
            double orderCost = 0;
            //计算总的采购成本
            for (Map<String, Object> map : orderProduts) {
                orderCost = orderCost + Double.parseDouble(map.get("subtotal").toString());
            }
            businessOrderModel.put("orderCost", orderCost);
        }
        */
        List<Map<String, Object>> orderSet = getService().getOrderInfos(id);
        mav.addObject("orderModel", orderSet);
        for (Map<String, Object> businessOrderModel : orderSet) {
            List<Map<String, Object>> orderProduts = getService().getOrderProductDifferents(id, Long.parseLong(businessOrderModel.get("id").toString()));
            businessOrderModel.put("orderProduts", orderProduts);
            double orderCost = 0;
            //计算总的采购成本
            for (Map<String, Object> map : orderProduts) {
                orderCost = orderCost + Double.parseDouble(map.get("subtotal").toString());
            }
            businessOrderModel.put("orderCost", orderCost);
        }
        return mav;
    }

    /**
     * 合同查询：合同订单
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/contractOrderAllDetail_link")
    public ModelAndView contractOrderAllDetail_link(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("sales/contract/contractOrderAllDetail_link");
        Long id = Long.parseLong(request.getParameter("id"));
        SalesContractModel salesContract = getService().get(id);
        mav.addObject("model", salesContract);
        Set<SalesContractProductModel> productSet = salesContract.getSalesContractProductModel();

        Iterator<SalesContractProductModel> it = productSet.iterator();
        Map<Long, Integer> m = new HashMap<Long, Integer>();
        while (it.hasNext()) {
            SalesContractProductModel p = it.next();
            m.put(p.getRelateDeliveryProductId(), p.getQuantity());
        }
        /* List<Map<String, Object>> orderSet = getService().getOrderInfo(id);
         for (Map<String, Object> businessOrderModel : orderSet) {
             List<Map<String, Object>> orderProduts = getService().getOrderProductDifferent(id, Long.parseLong(businessOrderModel.get("id").toString()));
             businessOrderModel.put("orderProduts", orderProduts);
             double orderCost = 0;
             //计算总的采购成本
             for (Map<String, Object> map : orderProduts) {
                 orderCost = orderCost + Double.parseDouble(map.get("subtotal").toString());
             }
             businessOrderModel.put("orderCost", orderCost);
         }
         mav.addObject("orderModel", orderSet);
         //产品对比：查询合同产品的下单情况
         //        List<Map<String, Object>> product = getService().getProductDifferent(id);
         //        mav.addObject("product", product);

         //List<Map<String, Object>> orderProduts = getService().getOrderProductDifferent(id, Long.parseLong(businessOrderModel.get("id").toString()));
        */
        List<Map<String, Object>> orderSet = getService().getOrderInfos(id);
        for (Map<String, Object> businessOrderModel : orderSet) {
            List<Map<String, Object>> orderProduts = getService().getOrderProductDifferents(id, Long.parseLong(businessOrderModel.get("id").toString()));
            businessOrderModel.put("orderProduts", orderProduts);
            double orderCost = 0;
            //计算总的采购成本
            for (Map<String, Object> map : orderProduts) {
                Object orderQua = map.get("orderQua");
                Object salesContractProductId = map.get("salesContractProductId");
                Object unitprice = map.get("unitprice");
                if (orderQua != null && salesContractProductId != null) {
                    Integer contractProQ = m.get(Long.parseLong(salesContractProductId.toString()));
                    if (contractProQ != null && Integer.parseInt(orderQua.toString()) > contractProQ) {
                        map.put("orderQua", contractProQ);
                        map.put("subtotal", new BigDecimal(unitprice.toString()).multiply(new BigDecimal(contractProQ)));
                    }
                }
                orderCost = orderCost + Double.parseDouble(map.get("subtotal").toString());
            }
            businessOrderModel.put("orderCost", orderCost);
        }
        mav.addObject("orderModel", orderSet);
        return mav;
    }

    /**
     * <code>getContract</code>
     * 得到关联设备合同名称
     * @param request
     * @param response
     * @return
     * @since   2014年10月28日    guokemenng
     */
    @ResponseBody
    @RequestMapping(value = "/getContract")
    public Map<String, Object> getContract(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();
        String id = request.getParameter("pruductId");
        SalesContractProductModel product = salesContractProductService.get(Long.parseLong(id));
        String contractName = product.getSalesContractModel().getContractName();
        map.put("contractName", contractName);
        return map;
    }

    public static void main(String args[]) {
        //String a = SalesContractController.getShowNumber(new BigDecimal(1111.00));
        //Log.info(a);

    }

    /**
     * <code>getFeeIncomeBySalesId</code>
     * 合同收款明细
     * @param request
     * @param response
     * @return
     * @since   2014年12月12日    guokemenng
     */
    @ResponseBody
    @RequestMapping(value = "/getFeeIncomeBySalesId")
    public List<Map<String, Object>> getFeeIncomeBySalesId(HttpServletRequest request, HttpServletResponse response) {
        String salesId = request.getParameter("salesId");
        return getService().getFeeIncomeBySalesId(Long.parseLong(salesId));
    }

    /**
     * <code>exportSales</code>
     * 合同查询导出Excel
     * @param request
     * @param response
     * @since   2014年12月18日    guokemenng
     */
    @RequestMapping(value = "/exportSales")
    public void exportSales(HttpServletRequest request, HttpServletResponse response) {
        HashMap<String, Object> searchMap = new HashMap<String, Object>();
        searchMap.put("contractStateNotEquals", "CG");
        buildSearch4HashMap(request, searchMap);
        List<SalesContractInfoAndStatus> salesDtoList = getService().exportSales(searchMap);

        List<String> titles = new ArrayList<String>();
        List<String> fields = new ArrayList<String>();

        titles.add("合同编码");
        titles.add("合同名称");
        titles.add("合同金额");
        titles.add("合同状态");
        titles.add("合同类型");
        titles.add("客户名称");
        titles.add("客户经理");
        titles.add("创建时间");

        fields.add("contractCode");
        fields.add("contractName");
        fields.add("contractAmount");
        fields.add("contractState");
        fields.add("contractTypeName");
        fields.add("customerName");
        fields.add("creatorName");
        fields.add("createTime");

        Workbook excel = ExcelBuilderUtil.buildPage(titles, salesDtoList, fields);
        FusionChartUtil.renderExcel(response, excel, "sales.xls");

    }

    /**
     * <code>getSaleContractBuyers</code>
     * 得到采购员的名称
     * @param request
     * @param response
     * @return
     * @since   2014年12月23日    guokemenng
     */
    @ResponseBody
    @RequestMapping(value = "/getSaleContractBuyers")
    public Map<String, String> getSaleContractBuyers(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> map = new HashMap<String, String>();
        String buyers = request.getParameter("buyers");
        map.put("buyers", getService().getSaleContractBuyers(buyers));
        return map;
    }

    /**
     * <code>getContactsByCustomer</code>
     * 根据客户得到客户联系人
     * @param request
     * @param response
     * @return
     * @since   2015年1月23日    guokemenng
     */
    @ResponseBody
    @RequestMapping(value = "/getContactsByCustomer")
    public List<Map<String, Object>> getContactsByCustomer(HttpServletRequest request, HttpServletResponse response) {
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();

        String customerId = request.getParameter("customerId");
        if (!StringUtil.isEmpty(customerId)) {
            CustomerInfoModel inModel = customerInfoService.get(Long.parseLong(customerId));
            Set<CustomerContactsModel> contactsSet = inModel.getCustomerContacts();
            Iterator<CustomerContactsModel> it = contactsSet.iterator();
            while (it.hasNext()) {
                CustomerContactsModel contactsModel = it.next();
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("id", contactsModel.getId());
                map.put("name", contactsModel.getName());
                map.put("code", contactsModel.getTelphone());
                mapList.add(map);
            }
        }

        return mapList;
    }

    /**
     * <code>getCustomerContactById</code>
     * 根据ID或者客户ID得到客户联系人
     * @param request
     * @param response
     * @return
     * @since   2015年1月23日    guokemenng
     */
    @ResponseBody
    @RequestMapping(value = "/getCustomerContactById")
    public Map<String, Object> getCustomerContactById(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();
        String contactId = request.getParameter("contactId");
        String customerId = request.getParameter("customerId");
        CustomerContactsModel contact = null;
        if (!StringUtil.isEmpty(contactId)) {
            contact = contactService.get(Long.parseLong(contactId));
            if (contact != null) {
                map.put("name", contact.getName());
                map.put("telphone", contact.getTelphone());
            } else {
                map.put("name", "");
                map.put("telphone", "");
            }

        } else {
            CustomerInfoModel inModel = customerInfoService.get(Long.parseLong(customerId));
            Set<CustomerContactsModel> contactsSet = inModel.getCustomerContacts();
            if (contactsSet.size() > 0) {
                contact = contactsSet.iterator().next();
                map.put("name", contact.getName());
                map.put("telphone", contact.getTelphone());
            }
        }
        return map;
    }

}
