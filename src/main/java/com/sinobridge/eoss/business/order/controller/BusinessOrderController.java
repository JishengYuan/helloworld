package com.sinobridge.eoss.business.order.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
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
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sinobridge.base.core.controller.DefaultBaseController;
import com.sinobridge.base.core.utils.JacksonJsonMapper;
import com.sinobridge.base.core.utils.PaginationSupport;
import com.sinobridge.eoss.bpm.service.ProcInstAppr;
import com.sinobridge.eoss.bpm.service.process.ProcessLogService;
import com.sinobridge.eoss.bpm.service.process.ProcessService;
import com.sinobridge.eoss.business.interPurchas.model.InterPurchasModel;
import com.sinobridge.eoss.business.interPurchas.model.InterPurchasProductModel;
import com.sinobridge.eoss.business.interPurchas.service.InterPurchasProductService;
import com.sinobridge.eoss.business.interPurchas.service.InterPurchasService;
import com.sinobridge.eoss.business.interPurchas.utils.DateUtils;
import com.sinobridge.eoss.business.order.constant.BusinessOrderContant;
import com.sinobridge.eoss.business.order.model.BusinessOrderModel;
import com.sinobridge.eoss.business.order.model.BusinessOrderProductModel;
import com.sinobridge.eoss.business.order.model.CloseContractModel;
import com.sinobridge.eoss.business.order.service.BusinessOrderProductService;
import com.sinobridge.eoss.business.order.service.BusinessOrderService;
import com.sinobridge.eoss.business.order.service.CloseContractService;
import com.sinobridge.eoss.business.order.utils.CodeUtils;
import com.sinobridge.eoss.business.order.utils.FileUtils;
import com.sinobridge.eoss.business.stock.model.InboundModel;
import com.sinobridge.eoss.business.suppliermanage.model.ReturnSpotModel;
import com.sinobridge.eoss.business.suppliermanage.model.SupplierContactsModel;
import com.sinobridge.eoss.business.suppliermanage.model.SupplierInfoModel;
import com.sinobridge.eoss.business.suppliermanage.service.SupplierContactsService;
import com.sinobridge.eoss.business.suppliermanage.service.SupplierInfoService;
import com.sinobridge.eoss.sales.contract.constant.SalesContractConstant;
import com.sinobridge.eoss.sales.contract.model.SalesContractModel;
import com.sinobridge.eoss.sales.contract.model.SalesContractProductModel;
import com.sinobridge.eoss.sales.contract.model.SalesContractReceivePlanModel;
import com.sinobridge.eoss.sales.contract.model.SalesContractStatusModel;
import com.sinobridge.eoss.sales.contract.service.SalesContractProductService;
import com.sinobridge.eoss.sales.contract.service.SalesContractService;
import com.sinobridge.systemmanage.model.SysStaff;
import com.sinobridge.systemmanage.service.FileService;
import com.sinobridge.systemmanage.service.SysStaffService;
import com.sinobridge.systemmanage.util.Global;
import com.sinobridge.systemmanage.util.StringUtil;
import com.sinobridge.systemmanage.vo.FileVo;
import com.sinobridge.systemmanage.vo.SystemUser;

@Controller
@RequestMapping(value = "business/order")
public class BusinessOrderController extends DefaultBaseController<BusinessOrderModel, BusinessOrderService> {
    @Autowired
    private SalesContractService salesContractServive;
    @Autowired
    private BusinessOrderProductService orderProductService;
    @Autowired
    private SupplierInfoService supplierInfoService;
    @Autowired
    private FileService fileService;
    @Autowired
    private InterPurchasService interPurchasService;
    @Autowired
    private CloseContractService closeContractService;
    @Autowired
    private BusinessOrderProductService businessOrderProductService;
    @Autowired
    private ProcessLogService processLogService;
    @Autowired
    private SysStaffService sysStaffService;
    @Autowired
    private SupplierContactsService supplierContactsService;
    @Autowired
    private SalesContractProductService salesContractProductService;
    @Autowired
    private InterPurchasProductService interPurchasProductService;

    private final String columns[] = { "contractName", "productType", "vendorCode", "productNo", "unitPrice", "quantity", "sub", "serviceType", "serviceStartTime", "serviceEndTime", "productId", "mark" };
    private final String columnNames[] = { "名称", "产品类型", "厂商编号", "产品型号", "单价", "数量", "总价", "服务类型", "服务开始时间", "服务结束时间", "产品ID", "标志位" };

    private final String column[] = { "planedReceiveAmount", "planedReceiveDate", "payCondition" };
    private final String columnName[] = { "计划收款金额", "计划收款时间", "收款条件" };
    private final String columnTypes[] = { "string", "datetime", "string" };

    @Autowired
    private ProcessService processService;

    /**
     * <code>manage</code>
     *我的订单页面
     * @param request
     * @param response
     * @return
     * @since   2014年5月5日    wangya
     */
    @RequestMapping(value = "/manage")
    public ModelAndView manage(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("business/order/manage");

        return mav;
    }

    /**
     * <code>getList</code>
     * 得到表格列表
     * @param request
     * @param response
     * @return
     * @since   2014年5月5日    wangya
     */
    @SuppressWarnings("unchecked")
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

        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(getModel().getClass());

        detachedCriteria.addOrder(Order.desc(BusinessOrderModel.ID));
        detachedCriteria.addOrder(Order.desc(BusinessOrderModel.CREATEDATE));
        buildSearch(request, detachedCriteria);

        PaginationSupport paginationSupport = getService().findPageByCriteria(detachedCriteria, this.pageNo * this.pageSize, this.pageSize);
        map.put("rows", paginationSupport.getItems());
        map.put("total", paginationSupport.getTotalCount());
        map.put("page", pageNo);
        //map.put("totallAmount", amount);
        return map;
    }

    /**
     * <code>buildSearchStaff</code>
     *
     * @param request
     * @param detachedCriteria
     * @since   2014年5月20日    wangya
     */
    public void buildSearch(HttpServletRequest request, DetachedCriteria detachedCriteria) {
        SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
        detachedCriteria.add(Restrictions.eq(BusinessOrderModel.CREATOR, systemUser.getStaffName()));

        String orderCode = request.getParameter("orderCode");
        String orderType = request.getParameter("orderType");
        String orderName = request.getParameter("orderName");
        String orderAmount = request.getParameter("orderAmount");
        String purchaseType = request.getParameter("purchaseType");
        String supplierShortName = request.getParameter("supplierShortName");
        String accountCurrency = request.getParameter("accountCurrency");

        if (!StringUtil.isEmpty(orderCode)) {
            detachedCriteria.add(Restrictions.like(BusinessOrderModel.ORDERCODE, "%" + orderCode + "%"));
        }
        if (!StringUtil.isEmpty(orderType)) {
            detachedCriteria.add(Restrictions.eq(BusinessOrderModel.ORDERTYPE, orderType));
        }
        if (!StringUtil.isEmpty(orderName)) {
            detachedCriteria.add(Restrictions.like(BusinessOrderModel.ORDERNAME, "%" + orderName + "%"));
        }
        if (!StringUtil.isEmpty(orderAmount)) {
            BigDecimal amount = new BigDecimal(orderAmount);
            detachedCriteria.add(Restrictions.eq(BusinessOrderModel.ORDERAMOUNT, amount));
        }
        if (!StringUtil.isEmpty(purchaseType)) {
            detachedCriteria.add(Restrictions.eq(BusinessOrderModel.PURCHASETYPE, purchaseType));
        }
        if (!StringUtil.isEmpty(supplierShortName)) {
            Long supplierId = Long.parseLong(supplierShortName);
            detachedCriteria.add(Restrictions.eq("supplierInfoModel.id", supplierId));
        }
        if (!StringUtil.isEmpty(accountCurrency)) {
            Object[] accountCurrencys = accountCurrency.split(",");
            detachedCriteria.add(Restrictions.in("accountCurrency", accountCurrencys));
        }

    }

    /**
     * <code>manage</code>
     *待下单的合同页面
     * @param request
     * @param response
     * @return
     * @since   2014年5月5日    wangya
     */
    @RequestMapping(value = "/waitContract/manage")
    public ModelAndView contractManage(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("business/order/waitContract/manage");
        return mav;
    }

    /**
     * <code>getContractsList</code>
     * 得到合同manage页面的列表
     * @param request
     * @param response
     * @return
     * @since   2014年6月27日    wangya
     */
    @ResponseBody
    @RequestMapping(value = "/getContractsList")
    public Map<String, Object> getContractsList(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();
        String iDisplayStart = request.getParameter("pageNo");
        String iDisplayLength = request.getParameter("pageSize");
        if (iDisplayStart != null) {
            this.pageNo = Integer.parseInt(iDisplayStart) - 1;
        }
        if (iDisplayLength != null) {
            this.pageSize = Integer.parseInt(iDisplayLength);
        }

        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(SalesContractModel.class);
        HashMap<String, Object> searchMap = new HashMap<String, Object>();
        searchMap.put(SalesContractModel.CONTRACTSTATE, BusinessOrderContant.CONTRACT_OK);
        SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
        searchMap.put("orderProcessor1", systemUser.getUserName());
        searchMap.put("orderProcessor2", "%," + systemUser.getUserName());
        searchMap.put("orderProcessor3", systemUser.getUserName() + ",%");
        searchMap.put("orderProcessor4", "%," + systemUser.getUserName() + ",%");
        searchMap.put("orderStatusNotTGSP", BusinessOrderContant.ORDER_STATUSE_OK);
        searchMap.put("orderPro1", systemUser.getUserName());
        searchMap.put("orderPro2", "%," + systemUser.getUserName());
        searchMap.put("orderPro3", systemUser.getUserName() + ",%");
        searchMap.put("orderPro4", "%," + systemUser.getUserName() + ",%");
        searchMap.put("contractStatuse", BusinessOrderContant.CONTRACT_OK);
        buildSearch_waitContracts(request, searchMap);
        detachedCriteria.addOrder(Order.desc(SalesContractModel.CREATETIME));
        int flat = 1;
        PaginationSupport paginationSupport = getService().findCloseContract(searchMap, this.pageNo * this.pageSize, this.pageSize, flat);
        map.put("rows", paginationSupport.getItems());
        map.put("total", paginationSupport.getTotalCount());
        map.put("page", iDisplayStart);
        return map;
    }

    private void buildSearch_waitContracts2(HttpServletRequest request, HashMap<String, Object> searchMap) {
        //   SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
        searchMap.put("reim", BusinessOrderContant.ORDER_PAYMENT_A);
        searchMap.put("payment", BusinessOrderContant.ORDER_PAYMENT_A);
        //searchMap.put("arrival", BusinessOrderContant.COMMODITY);
        String contractName = request.getParameter("contractName");
        String contractCode = request.getParameter("contractCode");
        String contractAmount = request.getParameter("contractAmount");
        if (!StringUtil.isEmpty(contractName)) {
            searchMap.put(SalesContractModel.CONTRACTNAME, "%" + contractName + "%");
        }
        if (!StringUtil.isEmpty(contractCode)) {
            searchMap.put(SalesContractModel.CONTRACTCODE, "%" + contractCode + "%");
        }
        if (!StringUtil.isEmpty(contractAmount)) {
            searchMap.put(SalesContractModel.CONTRACTAMOUNT, contractAmount);
        }
    }

    /**
     * <code>getContractsList</code>
     * 得到合同manage页面的列表
     * @param request
     * @param response
     * @return
     * @since   2014年6月27日    wangya
     */
    @ResponseBody
    @RequestMapping(value = "/getContractsList2")
    public Map<String, Object> getContractsList2(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();
        String iDisplayStart = request.getParameter("pageNo");
        String iDisplayLength = request.getParameter("pageSize");
        if (iDisplayStart != null) {
            this.pageNo = Integer.parseInt(iDisplayStart) - 1;
        }
        if (iDisplayLength != null) {
            this.pageSize = Integer.parseInt(iDisplayLength);
        }
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(SalesContractModel.class);
        HashMap<String, Object> searchMap = new HashMap<String, Object>();
        searchMap.put(SalesContractModel.CONTRACTSTATE, BusinessOrderContant.CONTRACT_OK);
        SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
        searchMap.put("orderProcessor1", systemUser.getUserName());
        searchMap.put("orderProcessor2", "%," + systemUser.getUserName());
        searchMap.put("orderProcessor3", systemUser.getUserName() + ",%");
        searchMap.put("orderProcessor4", "%," + systemUser.getUserName() + ",%");
        searchMap.put("orderStatusNotTGSP", BusinessOrderContant.ORDER_STATUSE_OK);
        buildSearch_waitContracts2(request, searchMap);
        detachedCriteria.addOrder(Order.desc(SalesContractModel.CREATETIME));
        int flat = 0;
        PaginationSupport paginationSupport = getService().findCloseContract(searchMap, this.pageNo * this.pageSize, this.pageSize, flat);
        map.put("rows", paginationSupport.getItems());
        map.put("total", paginationSupport.getTotalCount());
        map.put("page", iDisplayStart);
        return map;
    }

    private void buildSearch_waitContracts(HttpServletRequest request, HashMap<String, Object> searchMap) {

        searchMap.put("reimNot", BusinessOrderContant.ORDER_PAYMENT_A);
        String contractName = request.getParameter("contractName");
        String contractCode = request.getParameter("contractCode");
        String contractAmount = request.getParameter("contractAmount");
        if (!StringUtil.isEmpty(contractName)) {
            searchMap.put(SalesContractModel.CONTRACTNAME, "%" + contractName + "%");
        }
        if (!StringUtil.isEmpty(contractCode)) {
            searchMap.put(SalesContractModel.CONTRACTCODE, "%" + contractCode + "%");
        }
        if (!StringUtil.isEmpty(contractAmount)) {
            searchMap.put(SalesContractModel.CONTRACTAMOUNT, contractAmount);
        }
    }

    /**
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getClosingSales")
    public List<CloseContractModel> getClosingSales(HttpServletRequest request, HttpServletResponse response) {
        return closeContractService.findClosingContract();
    }

    /**
     * <code>interPurchasManage</code>
     *待下单的内部采购页面
     * @param request
     * @param response
     * @return
     * @since   2014年7月5日    wangya
     */
    @RequestMapping(value = "/waitInterPurchas/manage")
    public ModelAndView interPurchasManage(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("business/order/waitInterPurchas/manage");
        return mav;
    }

    /**
     * <code>getInterPurchasList</code>
     * 得到内部采购manage页面的列表
     * @param request
     * @param response
     * @return
     * @since   2014年7月3日    wangya
     */
    @ResponseBody
    @RequestMapping(value = "/getInterPurchasList")
    public Map<String, Object> getInterPurchasList(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();
        String iDisplayStart = request.getParameter("pageNo");
        String iDisplayLength = request.getParameter("pageSize");
        if (iDisplayStart != null) {
            this.pageNo = Integer.parseInt(iDisplayStart) - 1;
        }
        if (iDisplayLength != null) {
            this.pageSize = Integer.parseInt(iDisplayLength);
        }
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(InterPurchasModel.class);
        buildSearch_waitInterPurchas(request, detachedCriteria);
        PaginationSupport paginationSupport = getService().findPageByCriteria(detachedCriteria, this.pageNo * this.pageSize, this.pageSize);
        map.put("rows", paginationSupport.getItems());
        map.put("total", paginationSupport.getTotalCount());
        map.put("page", pageNo);
        return map;
    }

    private void buildSearch_waitInterPurchas(HttpServletRequest request, DetachedCriteria detachedCriteria) {
        detachedCriteria.add(Restrictions.eq(InterPurchasModel.PURCHASSTATUS, BusinessOrderContant.INTERPURCHAS_OK));
        detachedCriteria.add(Restrictions.ne(InterPurchasModel.INTERORDERSTATUS, BusinessOrderContant.INTER_ORDER_OK));
        detachedCriteria.addOrder(Order.desc(InterPurchasModel.CREATETIME));
        String creator = request.getParameter("creator");
        String expectedDeliveryTime = request.getParameter("expectedDeliveryTime");

        if (!StringUtil.isEmpty(creator)) {
            SysStaff staff = sysStaffService.getStaffByAccount(creator);
            detachedCriteria.add(Restrictions.eq(InterPurchasModel.CREATOR, staff.getStaffName()));
        }
        if (!StringUtil.isEmpty(expectedDeliveryTime)) {
            Date time = DateUtils.convertStringToDate(expectedDeliveryTime, "yyyy-MM-dd");
            detachedCriteria.add(Restrictions.eq(InterPurchasModel.EXPECTEDTIME, time));
        }
    }

    /**
     * <code>interSearch</code>
     * 搜索页面
     * @param request
     * @param response
     * @return
     * @since   2014年5月20日    wangya
     */
    @RequestMapping("/waitInterPurchas/search")
    public ModelAndView interSearch(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("business/order/waitInterPurchas/search");
        return mav;
    }

    /**
     * <code>manage</code>
     *订单查询页面
     * @param request
     * @param response
     * @return
     * @since   2014年5月5日    wangya
     */
    @RequestMapping(value = "/orderSearch/manage")
    public ModelAndView orderSearch(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("business/order/orderSearch/manage");
        return mav;
    }

    /**
     * <code>getOrderList</code>
     * 得到订单查询manage页面的列表
     * @param request
     * @param response
     * @return
     * @since   2014年6月27日    wangya
     */
    @ResponseBody
    @RequestMapping(value = "/getOrderList")
    public Map<String, Object> getOrderList(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();
        String pageNo = request.getParameter("pageNo");
        String pageSize = request.getParameter("pageSize");
        if (pageNo != null) {
            this.pageNo = Integer.parseInt(pageNo) - 1;
        }
        if (pageSize != null) {
            this.pageSize = Integer.parseInt(pageSize);
        }
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(getModel().getClass());
        HashMap<String, Object> searchMap = new HashMap<String, Object>();
        buildSearch_orderSearch(request, detachedCriteria, searchMap);
        detachedCriteria.addOrder(Order.desc(BusinessOrderModel.ID));
        PaginationSupport paginationSupport = getService().findPageByCriteria(detachedCriteria, this.pageNo * this.pageSize, this.pageSize);

        //得到查询后所有订单的总金额
        String amount = getService().findTotallAmount(searchMap);

        //得到查询后页面总金额
        List<BusinessOrderModel> listbus = (List<BusinessOrderModel>) paginationSupport.getItems();

        Iterator<BusinessOrderModel> bus = listbus.iterator();
        BigDecimal pageAmount = new BigDecimal(0.00);
        while (bus.hasNext()) {
            BusinessOrderModel b = bus.next();
            if (b.getOrderAmount() != null) {
                pageAmount = pageAmount.add(b.getOrderAmount());
            }
        }
        String page = pageAmount.toString();
        map.put("rows", paginationSupport.getItems());
        map.put("total", paginationSupport.getTotalCount());
        map.put("page", pageNo);
        map.put("totallAmount", amount);
        map.put("pageAmount", page);
        return map;
    }

    private void buildSearch_orderSearch(HttpServletRequest request, DetachedCriteria detachedCriteria, HashMap<String, Object> searchMap) {

        detachedCriteria.add(Restrictions.ne(BusinessOrderModel.ORDERSTATUS, BusinessOrderContant.ORDER_CG));
        searchMap.put(BusinessOrderModel.ORDERSTATUS, BusinessOrderContant.ORDER_CG);
        String orderCode = request.getParameter("orderCode");
        String orderType = request.getParameter("orderType");
        String orderName = request.getParameter("orderName");
        String orderAmount = request.getParameter("orderAmount");
        String creator = request.getParameter("creator");
        String purchaseType = request.getParameter("purchaseType");
        String supplierShortName = request.getParameter("supplierShortName");
        String payStatus = request.getParameter("payStatus");
        String reimStatus = request.getParameter("reimStatus");
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");

        if (!StringUtil.isEmpty(orderCode)) {
            detachedCriteria.add(Restrictions.like(BusinessOrderModel.ORDERCODE, "%" + orderCode + "%"));
            searchMap.put(BusinessOrderModel.ORDERCODE, "%" + orderCode + "%");
        }
        if (!StringUtil.isEmpty(orderType)) {
            detachedCriteria.add(Restrictions.eq(BusinessOrderModel.ORDERTYPE, orderType));
            searchMap.put(BusinessOrderModel.ORDERTYPE, orderType);
        }
        if (!StringUtil.isEmpty(orderName)) {
            detachedCriteria.add(Restrictions.like(BusinessOrderModel.ORDERNAME, "%" + orderName + "%"));
            searchMap.put(BusinessOrderModel.ORDERNAME, "%" + orderName + "%");
        }
        if (!StringUtil.isEmpty(orderAmount)) {
            BigDecimal amount = new BigDecimal(orderAmount);
            detachedCriteria.add(Restrictions.eq(BusinessOrderModel.ORDERAMOUNT, amount));
            searchMap.put(BusinessOrderModel.ORDERAMOUNT, amount);
        }
        if (!StringUtil.isEmpty(purchaseType)) {
            detachedCriteria.add(Restrictions.eq(BusinessOrderModel.PURCHASETYPE, purchaseType));
            searchMap.put(BusinessOrderModel.PURCHASETYPE, purchaseType);
        }
        if (!StringUtil.isEmpty(supplierShortName)) {
            Long supplierId = Long.parseLong(supplierShortName);
            detachedCriteria.add(Restrictions.eq("supplierInfoModel.id", supplierId));
            searchMap.put("supplierInfoId", supplierId);
        }
        if (!StringUtil.isEmpty(creator)) {
            SysStaff useName = sysStaffService.get(creator);
            String name = useName.getUserName();
            detachedCriteria.add(Restrictions.eq(BusinessOrderModel.CREATORID, name));
            searchMap.put(BusinessOrderModel.CREATORID, name);
        }
        if (!StringUtil.isEmpty(payStatus)) {
            detachedCriteria.add(Restrictions.eq(BusinessOrderModel.PAYSTATUS, payStatus));
            searchMap.put(BusinessOrderModel.PAYSTATUS, payStatus);
        }
        if (!StringUtil.isEmpty(reimStatus)) {
            detachedCriteria.add(Restrictions.eq(BusinessOrderModel.REIMSTATUS, reimStatus));
            searchMap.put(BusinessOrderModel.REIMSTATUS, reimStatus);
        }
        if (!StringUtil.isEmpty(startTime)) {
            detachedCriteria.add(Restrictions.ge(BusinessOrderModel.CREATEDATE, DateUtils.convertStringToDate(startTime, "yyyy-MM-dd")));
            searchMap.put(BusinessOrderModel.CREATEDATE, startTime);
        }
        if (!StringUtil.isEmpty(endTime)) {
            detachedCriteria.add(Restrictions.le(BusinessOrderModel.CREATEDATE, DateUtils.convertStringToDate(endTime, "yyyy-MM-dd")));
            searchMap.put("endTime", endTime);
        }

    }

    /**
     * <code>manage</code>
     *查询付款报销页面
     * @param request
     * @param response
     * @return
     * @since   2014年5月5日    wangya
     */
    @RequestMapping(value = "/costControl/manage")
    public ModelAndView paymentReimManage(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("business/order/costControl/manage");
        return mav;
    }

    /**
     * <code>search</code>
     *待下单合同查询页面
     * @param request
     * @param response
     * @return
     * @since   2014年9月25日    wangya
     */
    @RequestMapping(value = "/waitContract/search")
    public ModelAndView getContractSerach(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("business/order/waitContract/search");
        return mav;
    }

    /**
     * <code>closeSearch</code>
     *待下单合同查询页面
     * @param request
     * @param response
     * @return
     * @since   2014年9月25日    wangya
     */
    @RequestMapping(value = "/waitContract/closeSearch")
    public ModelAndView getContractCloseSerach(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("business/order/waitContract/closeSearch");
        return mav;
    }

    /**
     * 异步获得合同信息
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/getContracts")
    @ResponseBody
    public Map<String, Object> getContracts(HttpServletRequest request, HttpServletResponse response) {
        //TODO
        Map<String, Object> pg = new HashMap<String, Object>();
        String params = request.getParameter("sEcho");
        String iDisplayStart = request.getParameter("iDisplayStart");
        String iDisplayLength = request.getParameter("iDisplayLength");
        //String iDisplayLength = request.getParameter("iDisplayLength");
        //System.out.println("iDisplayStart="+iDisplayStart+";iDisplayLength="+iDisplayLength);
        //String sSearch = "1";  //request.getParameter("sSearch");
        HashMap<String, Object> searchMap = new HashMap<String, Object>();
        buildSearch_Contract(request, searchMap);
        PaginationSupport paginationSupport = getService().findSalesContract(searchMap, Integer.parseInt(iDisplayStart), Integer.parseInt(iDisplayLength));
        pg.put("aaData", paginationSupport.getItems());
        pg.put("sEcho", params);
        pg.put("iTotalDisplayRecords", paginationSupport.getTotalCount());
        pg.put("iTotalRecords", paginationSupport.getTotalCount());
        /* pg.put("aaData", showSales);
         pg.put("sEcho", params);
         pg.put("iTotalDisplayRecords", showSales.size());
         pg.put("iTotalRecords",  showSales.size());*/

        return pg;
    }

    /**
     * 创建合同的查询条件
     * 默认显示分配给自己的合同，如果输入查询条件，则查询所有需要下单的合同
     * @param request
     * @param searchMap
     */
    public void buildSearch_Contract(HttpServletRequest request, HashMap<String, Object> searchMap) {
        SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
        searchMap.put(SalesContractModel.CONTRACTSTATE, BusinessOrderContant.CONTRACT_OK);
        searchMap.put("orderStatusNotTGSP", BusinessOrderContant.ORDER_STATUSE_OK);
        String sSearch_0 = request.getParameter("sSearch_0");
        if (!StringUtil.isEmpty(sSearch_0)) {
            String[] ss = sSearch_0.split("_");
            if (!ss[0].equals("searchcreator")) {
                searchMap.put(SalesContractModel.CREATOR, ss[0]);
            }
            if (!ss[1].equals("searchCode")) {
                searchMap.put(SalesContractModel.CONTRACTCODE, "%" + ss[1] + "%");
            }
            if (!ss[2].equals("searchName")) {
                searchMap.put(SalesContractModel.CONTRACTNAME, "%" + ss[2] + "%");
            }
            if (ss[0].equals("searchcreator") && ss[1].equals("searchCode") && ss[2].equals("searchName")) {
                searchMap.put("orderProcessor1", systemUser.getUserName());
                searchMap.put("orderProcessor2", "%," + systemUser.getUserName());
                searchMap.put("orderProcessor3", systemUser.getUserName() + ",%");
                searchMap.put("orderProcessor4", "%," + systemUser.getUserName() + ",%");
            }

        } else {
            searchMap.put("orderProcessor1", systemUser.getUserName());
            searchMap.put("orderProcessor2", "%," + systemUser.getUserName());
            searchMap.put("orderProcessor3", systemUser.getUserName() + ",%");
            searchMap.put("orderProcessor4", "%," + systemUser.getUserName() + ",%");
        }
    }

    /**
     * 异步获得内部采购信息
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/getInterPurchas")
    @ResponseBody
    public Map<String, Object> getInterPurchas(HttpServletRequest request, HttpServletResponse response) {
        String params = request.getParameter("sEcho");
        String iDisplayStart = request.getParameter("iDisplayStart");
        //String iDisplayLength = request.getParameter("iDisplayLength");
        int iDisplayLength = 5;
        HashMap<String, Object> searchMap = new HashMap<String, Object>();
        buildSearch_InterPurchas(request, searchMap);

        PaginationSupport paginationSupport = interPurchasService.findInterPurchas(searchMap, Integer.parseInt(iDisplayStart), iDisplayLength);
        Map<String, Object> pg = new HashMap<String, Object>();
        pg.put("aaData", paginationSupport.getItems());
        pg.put("sEcho", params);
        pg.put("iTotalDisplayRecords", paginationSupport.getTotalCount());
        pg.put("iTotalRecords", paginationSupport.getTotalCount());
        return pg;
    }

    private void buildSearch_InterPurchas(HttpServletRequest request, HashMap<String, Object> searchMap) {
        // TODO Auto-generated method stub
        searchMap.put(InterPurchasModel.PURCHASSTATUS, BusinessOrderContant.INTERPURCHAS_OK);
        searchMap.put(InterPurchasModel.INTERORDERSTATUS, BusinessOrderContant.INTER_ORDER_OK);
        String sSearch_0 = request.getParameter("sSearch_0");
        if (!StringUtil.isEmpty(sSearch_0)) {
            String[] ss = sSearch_0.split("_");
            if (!ss[0].equals("searchCreator")) {
                searchMap.put("creatorId", ss[0]);
            }
            if (!ss[1].equals("searchTime")) {
                Date time = DateUtils.convertStringToDate(ss[1], "yyyy-MM-dd");
                searchMap.put(InterPurchasModel.EXPECTEDTIME, time);
            }

        }
    }

    /**
     * <code>findSupplierInfoValue</code>
     * 前台供应商查询下拉
     * @param request
     * @param response
     * @return
     * @since 2014年6月4日   wangya
     */
    @RequestMapping(value = "/findSupplierInfoValue")
    @ResponseBody
    public Map<String, Object> findSupplierInfoValue(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        String seacher = request.getParameter("q") == null ? "" : request.getParameter("q");
        int suppPageNo = Integer.parseInt(request.getParameter("page")) - 1;
        int suppPageSize = Integer.parseInt(request.getParameter("page_limit"));
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(SupplierInfoModel.class);
        String newSeacher = "%" + seacher + "%";
        if (id != null && !id.equals("")) {
            detachedCriteria.add(Restrictions.like(SupplierInfoModel.SUPPLIERTYPE, id));
        }
        detachedCriteria.add(Restrictions.eq("state", "1"));
        detachedCriteria.add(Restrictions.like(SupplierInfoModel.SHORTNAME, newSeacher));
        PaginationSupport paginationSupport = supplierInfoService.findPageByCriteria(detachedCriteria, suppPageNo, suppPageSize);
        /* ObjectMapper objectMapper = JacksonJsonMapper.getInstance();
         String result = "";*/
        Map<String, Object> pg = new HashMap<String, Object>();
        pg.put("total", paginationSupport.getTotalCount());
        pg.put("rows", paginationSupport.getItems());
        pg.put("page", suppPageNo);
        /*pg.put("iTotalDisplayRecords", paginationSupport.getTotalCount());
        pg.put("iTotalRecords", paginationSupport.getTotalCount());*/
        //try {
        // result = objectMapper.writeValueAsString(paginationSupport.getItems());

        /*  } catch (JsonGenerationException e) {
              e.printStackTrace();
          } catch (JsonMappingException e) {
              e.printStackTrace();
          } catch (IOException e) {
              e.printStackTrace();
          }*/
        return pg;
    }

    /**
     * <code>findContactsValue</code>
     * 前台供应商联系人查询下拉
     * @param request
     * @param response
     * @return
     * @since 2014年6月4日   wangya
     */
    @RequestMapping(value = "/findContactsValue")
    @ResponseBody
    public String findContactsValue(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        /* DetachedCriteria detachedCriteria = DetachedCriteria.forClass(SupplierContactsModel.class);
         detachedCriteria.createAlias(SupplierContactsModel.SUPPLIERINFO, SupplierContactsModel.SUPPLIERINFO);
         detachedCriteria.add(Restrictions.eq("supplierInfo.id", Long.parseLong(id)));
         PaginationSupport paginationSupport = supplierContactsService.findPageByCriteria(detachedCriteria, suppPageNo, suppPageSize);
         List<SupplierContactsModel> modelList = new ArrayList<SupplierContactsModel>();
         for(int i = 0;i < paginationSupport.getItems().size();i++){
             Object o = paginationSupport.getItems().get(i);
             if(o instanceof SupplierContactsModel){
                 modelList.add((SupplierContactsModel) o);
             } else {
                 Object[] oL = (Object[]) o;
                 for(Object obj : oL){
                     if(obj instanceof SupplierContactsModel){
                         modelList.add((SupplierContactsModel)obj);
                     }
                 }
             }
         }
         */
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        if (!StringUtil.isEmpty(id)) {
            list = getService().getSupplierContacts(id);
        }
        ObjectMapper objectMapper = JacksonJsonMapper.getInstance();
        String result = "";
        try {
            result = objectMapper.writeValueAsString(list);
            System.out.println(list);
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * <code>getContractData</code>
     * 合同数据接收
     * @param request
     * @param response
     * @return
     * @since 2014年6月4日   wangya
     */
    @SuppressWarnings("unused")
    @ResponseBody
    //@RequestMapping(value = "/getContractData", produces = "application/json; charset=utf-8")
    @RequestMapping(value = "/getContractData")
    public String getContractData(HttpServletRequest request, HttpServletResponse response) {
        String form = "";
        String contractId = request.getParameter("contractId");
        SalesContractModel salesContract = new SalesContractModel();
        Map<String, Object> tableModel = new HashMap<String, Object>(); //表格数据模型
        List<HashMap<String, Object>> rowDatas = new ArrayList<HashMap<String, Object>>(); //表格总的数据，一行数据对应一个map
        tableModel.put("name", "editGridName"); //表格名称
        if (contractId != null && !contractId.equals("undefined")) {
            salesContract = salesContractServive.get(Long.parseLong(contractId));
            List<Map<String, Object>> product = getService().findProductUn(Long.parseLong(contractId));
            Iterator<Map<String, Object>> it = product.iterator();
            while (it.hasNext()) {
                Map<String, Object> map = it.next();
                int que = Integer.parseInt(map.get("quantity").toString());
                if (que > 0) {
                    HashMap<String, Object> dataMap = new HashMap<String, Object>(); //因为先前在rowNames中定义表格有3列
                    dataMap.put(columns[0], salesContract.getContractName());
                    if (map.get("ProductTypeName") == null) {
                        String typeName = getService().findProductTypeName(Long.parseLong(map.get("ProductNo").toString()));
                        dataMap.put(columns[1], typeName);
                    } else {
                        dataMap.put(columns[1], map.get("ProductTypeName"));
                    }
                    dataMap.put(columns[2], map.get("ProductPartnerName"));
                    dataMap.put(columns[3], map.get("ProductName"));
                    dataMap.put(columns[4], map.get("UnitPrice"));
                    dataMap.put(columns[5], map.get("quantity"));
                    dataMap.put(columns[6], map.get("TotalPrice"));
                    dataMap.put(columns[7], "");
                    if (map.get("ServiceStartDate") != null) {
                        dataMap.put(columns[8], map.get("ServiceStartDate"));
                    } else {
                        dataMap.put(columns[8], "");
                    }
                    if (map.get("ServiceEndDate") != null) {
                        dataMap.put(columns[9], map.get("ServiceEndDate"));
                    } else {
                        dataMap.put(columns[9], "");
                    }
                    dataMap.put(columns[10], map.get("id"));
                    dataMap.put(columns[11], "HT");
                    rowDatas.add(dataMap);
                }
            }

        }
        /*BusinessOrderProductModel businessOrdreProduct = new BusinessOrderProductModel();
        //SalesContractProductModel salesContractProduct=new SalesContractProductModel();
        Set<SalesContractProductModel> salesContractProduct = salesContract.getSalesContractProductModel();
        
        Set<SalesContractProductModel> productSet = salesContract.getSalesContractProductModel();
        Iterator<SalesContractProductModel> it = productSet.iterator();
        //TODO

        while (it.hasNext()) {
            SalesContractProductModel s = it.next();
            List<BusinessOrderProductModel> order = orderProductService.getOrderProductId(s);
            System.out.println(s.getRelateDeliveryProductId());
            if (s.getRelateDeliveryProductId() == null) {
                if (order != null) {
                    int qua = 0;
                    for (int i = 0; i < order.size(); i++) {
        
                        System.out.println(order.get(i));
                        BusinessOrderProductModel orderProduct = order.get(i);
                        qua += orderProduct.getQuantity();
                    }
                    qua = s.getQuantity() - qua;
        
                    if (qua > 0) {
                        HashMap<String, Object> dataMap = new HashMap<String, Object>(); //因为先前在rowNames中定义表格有3列
                        dataMap.put(columns[0], salesContract.getContractName());
                        if (s.getProductTypeName() == null) {//ma续保合同的产品没有类型，自动补上
                            String typeName = getService().findProductTypeName(s.getProductNo());
                            dataMap.put(columns[1], typeName);
                        } else {
                            dataMap.put(columns[1], s.getProductTypeName());
                        }
                        dataMap.put(columns[2], s.getProductPartnerName());
                        dataMap.put(columns[3], s.getProductName());
                        dataMap.put(columns[4], s.getUnitPrice());
                        dataMap.put(columns[5], qua);
                        dataMap.put(columns[6], s.getTotalPrice());
                        dataMap.put(columns[7], "");
                        if (s.getServiceStartDate() != null) {
                        dataMap.put(columns[8], s.getServiceStartDate());
                        } else {
                        dataMap.put(columns[8], "");
                        }
                        if (s.getServiceEndDate() != null) {
                        dataMap.put(columns[9], s.getServiceEndDate());
                        } else {
                        dataMap.put(columns[9], "");
                        }
                        dataMap.put(columns[10], s.getId());
                        dataMap.put(columns[11], "HT");
                        rowDatas.add(dataMap);
                    }
                } else {
                    HashMap<String, Object> dataMap = new HashMap<String, Object>(); //因为先前在rowNames中定义表格有3列
                    dataMap.put(columns[0], salesContract.getContractName());
                    if (s.getProductTypeName() == null) {//ma续保合同的产品没有类型，自动补上
                        String typeName = getService().findProductTypeName(s.getProductNo());
                        dataMap.put(columns[1], typeName);
                    } else {
                        dataMap.put(columns[1], s.getProductTypeName());
                    }
                    dataMap.put(columns[2], s.getProductPartnerName());
                    dataMap.put(columns[3], s.getProductName());
                    dataMap.put(columns[4], s.getUnitPrice());
                    dataMap.put(columns[5], s.getQuantity());
                    dataMap.put(columns[6], s.getTotalPrice());
                    dataMap.put(columns[7], "");
                    if (s.getServiceStartDate() != null) {
                    dataMap.put(columns[8], s.getServiceStartDate());
                    } else {
                    dataMap.put(columns[8], "");
                    }
                    if (s.getServiceEndDate() != null) {
                    dataMap.put(columns[9], s.getServiceEndDate());
                    } else {
                    dataMap.put(columns[9], "");
                    }
                    dataMap.put(columns[10], s.getId());
                    dataMap.put(columns[11], "HT");
                    rowDatas.add(dataMap);
                }
            }
        }
        }*/
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

    /**
     * <code>getInterPurchasDate</code>
     * 合同数据接收
     * @param request
     * @param response
     * @return
     * @since 2014年7月14日   wangya
     */
    @SuppressWarnings("unused")
    @ResponseBody
    //@RequestMapping(value = "/getInterPurchasDate", produces = "application/json; charset=utf-8")
    @RequestMapping(value = "/getInterPurchasDate")
    public String getInterPurchasDate(HttpServletRequest request, HttpServletResponse response) {
        String form = "";
        String interPurchasId = request.getParameter("interPurchasId");
        InterPurchasModel interPurchas = interPurchasService.get(Long.parseLong(interPurchasId));
        BusinessOrderProductModel businessOrdreProduct = new BusinessOrderProductModel();
        Set<InterPurchasProductModel> interPurchasProduct = interPurchas.getInterPurchasProduct();

        businessOrdreProduct.getInterPurchas();
        Map<String, Object> tableModel = new HashMap<String, Object>(); //表格数据模型
        tableModel.put("name", "editGridName"); //表格名称

        List<HashMap<String, Object>> rowDatas = new ArrayList<HashMap<String, Object>>(); //表格总的数据，一行数据对应一个map
        Set<InterPurchasProductModel> productSet = interPurchas.getInterPurchasProduct();
        Iterator<InterPurchasProductModel> it = productSet.iterator();

        while (it.hasNext()) {
            InterPurchasProductModel s = it.next();
            List<BusinessOrderProductModel> order = orderProductService.getPurchasProductId(s);
            if (order != null) {
                int qua = 0;
                for (int i = 0; i < order.size(); i++) {
                    BusinessOrderProductModel orderProduct = order.get(i);
                    qua += orderProduct.getQuantity();
                }
                qua = s.getQuantity() - qua;

                if (qua != 0) {
                    HashMap<String, Object> dataMap = new HashMap<String, Object>(); //因为先前在rowNames中定义表格有3列
                    dataMap.put(columns[0], interPurchas.getPurchasName());
                    dataMap.put(columns[1], s.getProductTypeName());
                    dataMap.put(columns[2], s.getProductPartnerName());
                    dataMap.put(columns[3], s.getProductName());
                    dataMap.put(columns[4], 0);
                    dataMap.put(columns[5], qua);
                    dataMap.put(columns[6], 0);
                    /* dataMap.put(columns[7], "");
                     dataMap.put(columns[8], "");
                     dataMap.put(columns[9], "");*/
                    dataMap.put(columns[10], s.getId());
                    dataMap.put(columns[11], "NC");
                    rowDatas.add(dataMap);
                }
            } else {
                HashMap<String, Object> dataMap = new HashMap<String, Object>(); //因为先前在rowNames中定义表格有3列
                dataMap.put(columns[0], interPurchas.getPurchasName());
                dataMap.put(columns[1], s.getProductTypeName());
                dataMap.put(columns[2], s.getProductPartnerName());
                dataMap.put(columns[3], s.getProductName());
                dataMap.put(columns[4], 0);
                dataMap.put(columns[5], s.getQuantity());
                dataMap.put(columns[6], 0);
                /* dataMap.put(columns[7], "");
                 dataMap.put(columns[8], "");
                 dataMap.put(columns[9], "");*/
                dataMap.put(columns[10], s.getId());
                dataMap.put(columns[11], "NC");
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

    /**
     * <code>search</code>
     * 搜索页面
     * @param request
     * @param response
     * @return
     * @since   2014年7月16日    wangya
     */
    @RequestMapping("/search")
    public ModelAndView search(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("business/order/search");
        return mav;
    }

    /**
     * <code>allSearch</code>
     * 搜索页面
     * @param request
     * @param response
     * @return
     * @since   2014年7月16日    wangya
     */
    @RequestMapping("/orderSearch/allSearch")
    public ModelAndView allSearch(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("business/order/orderSearch/allSearch");
        return mav;
    }

    /**
     * <code>getBizOwner</code>
     * 商务负责人
     * @return
     * @since   2014年5月23日    wangya
     */
    @ResponseBody
    @RequestMapping(value = "/getOrderCreator")
    public List<Map<String, Object>> getBizOwner() {
        return getService().getOrderCreator();
    }

    /**
     * <code>getSupplierShortName</code>
     * 供应商简称
     * @return
     * @since   2014年7月16日    wangya
     */
    @ResponseBody
    @RequestMapping(value = "/getSupplierShortName")
    public List<Map<String, Object>> getSupplierShortName() {
        return getService().getSupplierShortName();
    }

    /**
     * <code>getOrgFullName</code>
     * 公司部门
     * @return
     * @since   2014年7月15日    wangya
     */
    @ResponseBody
    @RequestMapping(value = "/getOrgFullName")
    public List<Map<String, Object>> getOrgFullName() {
        return getService().getOrgFullName();
    }

    /*
    @ResponseBody
    @RequestMapping(value = "/getSupplierName")
    public List<Map<String, Object>> getSupplierName(HttpServletRequest request, HttpServletResponse response) {
     String supplierTypeId = request.getParameter("id");
     List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
     if (!StringUtil.isEmpty(supplierTypeId)) {
         list = getService().getSupplierName(supplierTypeId);
     }
     return list;
    }*/

    /**
     * <code>getSupplierContacts</code>
     * 供应商联系人
     * @return
     * @since   201465月23日    wangay
     */
    @ResponseBody
    @RequestMapping(value = "/getSupplierContacts")
    public List<Map<String, Object>> getSupplierContacts(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        if (!StringUtil.isEmpty(id)) {
            list = getService().getSupplierContacts(id);
        }
        return list;
    }

    /**
     * <code>getSupplierCode</code>
     * 供应商编码
     * @return
     * @since   201465月23日    wangay
     */
    @ResponseBody
    @RequestMapping(value = "/getSupplierCode")
    public List<Map<String, Object>> getSupplierCode(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        if (!StringUtil.isEmpty(id)) {
            list = getService().getSupplierCode(id);
        }
        return list;
    }

    /*
    @ResponseBody
    @RequestMapping(value = "/getContactPhone")
    public List<Map<String, Object>> getContactPhone(HttpServletRequest request, HttpServletResponse response) {
     String id = request.getParameter("id");
     List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
     if (!StringUtil.isEmpty(id)) {
         list = getService().getContactPhone(id);
     }
     return list;
    }*/

    /*
    @ResponseBody
    @RequestMapping(value = "/getContactTelPhone")
    public List<Map<String, Object>> getContactTelPhone(HttpServletRequest request, HttpServletResponse response) {
     String id = request.getParameter("id");
     List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
     if (!StringUtil.isEmpty(id)) {
         list = getService().getContactTelPhone(id);
     }
     return list;
    }*/

    /**
     * <code>addOrder</code>
     * 转到addOrder页面
     * @param request
     * @param response
     * @return
     * @since   2014年5月5日    wangya
     */
    @RequestMapping(value = "/addOrder")
    public ModelAndView addOrder(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("business/order/addOrder");

        Map<String, Object> map = getService().findCompanyInfo().get(0);
        request.setAttribute("address", map.get("address"));
        request.setAttribute("companyName", map.get("companyname"));
        request.setAttribute("bankName", map.get("bankname"));
        request.setAttribute("account", map.get("bankaccount"));

        Map<String, Object> tableModel = new HashMap<String, Object>(); //表格数据模型
        tableModel.put("name", "editGridName"); //表格名称
        List<HashMap<String, Object>> rowNames = new ArrayList<HashMap<String, Object>>(); //表格中的列数据

        /*   for (int i = 0; i < columns.length; i++) {
               HashMap<String, Object> dataMap = new HashMap<String, Object>();
               dataMap.put("name", columns[i]); //字段名称，用于保存数据
               dataMap.put("label", columnNames[i]);//字段在页面上的显示名称
               dataMap.put("type", "string"); //表格中输入字段的类型，目前只支持string,date
               rowNames.add(dataMap);
           }*/
        tableModel.put("fieldList", rowNames);
        List<HashMap<String, Object>> rowDatas = new ArrayList<HashMap<String, Object>>(); //表格总的数据，一行数据对应一个map
        //SalesContractProductModel salesContractProductModel= new SalesContractProductModel();

        /* for (int i = 0; i < 1; i++) { //放入2行数据
             HashMap<String, Object> dataMap = new HashMap<String, Object>(); //因为先前在rowNames中定义表格有3列
             dataMap.put(columns[0], "1");
             dataMap.put(columns[1], "2");
             dataMap.put(columns[2], "3");
             dataMap.put(columns[3], "4");
             dataMap.put(columns[4], "5");

             rowDatas.add(dataMap);
         }*/
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
        return mav;
    }

    /**
     * <code>saveOrUpdate</code>
     * 转到修改页面
     * @param request
     * @param response
     * @return
     * @since   2014年5月5日    wangya
     */
    @RequestMapping(value = "/saveOrUpdate")
    public ModelAndView saveOrUpdate(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("business/order/saveOrUpdate");
        String id = request.getParameter("id");
        BusinessOrderModel order = new BusinessOrderModel();
        if (id != null && !"".equals(id)) {

            order = getService().get(Long.parseLong(id));
            mav.addObject("model", order);
            //判断是合同产品还是内采产品
            Set<BusinessOrderProductModel> products = order.getBusinessOrderProductModel();
            Iterator<BusinessOrderProductModel> p = products.iterator();
            while (p.hasNext()) {
                BusinessOrderProductModel pro = p.next();
                if (pro.getSalesContractModel() != null) {
                    request.setAttribute("mark", "HT");
                } else {
                    request.setAttribute("mark", "NC");
                }
            }
            //判断是否能修改
            Object nameObj = getService().getOrderById(order.getId()).get("NAME_");
            String name = "";
            if (nameObj != null) {
                name = nameObj.toString();
            }
            if (!(order.getOrderStatus().equals(BusinessOrderContant.ORDER_CG) || name.equals("重新提交")) || order.getOrderStatus().equals(BusinessOrderContant.ORDER_OK)) {
                mav = new ModelAndView("business/order/saveOrUpdate1");
                return mav;
            }
        }
        Map<String, Object> tableModel = new HashMap<String, Object>(); //表格数据模型
        tableModel.put("name", "editGridName"); //表格名称
        List<HashMap<String, Object>> rowNames = new ArrayList<HashMap<String, Object>>(); //表格中的列数据
        tableModel.put("fieldList", rowNames);
        List<HashMap<String, Object>> rowDatas = new ArrayList<HashMap<String, Object>>(); //表格总的数据，一行数据对应一个map
        //   Set<BusinessOrderProductModel> businessOrderProductModel = order.getBusinessOrderProductModel();
        /*
                 for (BusinessOrderProductModel b: businessOrderProductModel) { //放入2行数据
                     SalesContractModel salesContract=b.getSalesContractModel();
                     HashMap<String, Object> dataMap = new HashMap<String, Object>(); //因为先前在rowNames中定义表格有3列
                     dataMap.put(columns[0], salesContract.getContractName());
                     dataMap.put(columns[1], b.getProductNo());
                     dataMap.put(columns[2], b.getVendorCode());
                     dataMap.put(columns[3], b.getUnitPrice());
                     dataMap.put(columns[4], b.getQuantity());
                     dataMap.put(columns[5], b.getSubTotal());
                     dataMap.put(columns[6], b.getId());
                     rowDatas.add(dataMap);
                 }*/
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
        return mav;
    }

    /**
     * <code>doSaveOrUpdate</code>
     * 保存更新实体
     * @param request
     * @param response
     * @param manageModel
     * @return
     * @since   2014年5月5日    wangya
     */
    @ResponseBody
    @RequestMapping(value = "/doSaveOrUpdate")
    public Map<String, String> doSaveOrUpdate(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("_sino_eoss_cuotomer_updateform") BusinessOrderModel orderModel) {
        Map<String, String> rsmsg = new HashMap<String, String>();
        try {
            Long id = orderModel.getId();
            Object nameObj = getService().getOrderById(id).get("NAME_");//审批人
            if (nameObj != null) {
                rsmsg.put("result", FAIL);
                rsmsg.put("message", "订单已提交审批，无法再次提交！");
                return rsmsg;
            } else {
                if (request.getParameter("supplierId") != null && request.getParameter("supplierId") != "") {
                    long supplierId = Long.parseLong(request.getParameter("supplierId"));
                    System.out.println(supplierId);
                    SupplierInfoModel supplierInfoModel = supplierInfoService.get(supplierId);
                    orderModel.setSupplierInfoModel(supplierInfoModel);
                    String supplierShortName = supplierInfoModel.getShortName();
                    orderModel.setSupplierShortName(supplierShortName);
                }
                if (request.getParameter("contactId") != null && request.getParameter("contactId") != "") {
                    long contactId = Long.parseLong(request.getParameter("contactId"));
                    System.out.println(contactId);
                    SupplierContactsModel supplierConstacts = supplierContactsService.get(contactId);
                    orderModel.setSupplierContactsModel(supplierConstacts);
                }
                if (request.getParameter("orderAmount") != null && request.getParameter("orderAmount") != "") {
                    double amo = Double.parseDouble(request.getParameter("orderAmount"));
                    BigDecimal amount = BigDecimal.valueOf(amo);
                    orderModel.setOrderAmount(amount);
                }

                if (request.getParameter("accountCurrency") != null && request.getParameter("accountCurrency") != "") {
                    String accountCurrency = request.getParameter("accountCurrency");
                    String currencyId = "";
                    if (!StringUtil.isEmpty(accountCurrency)) {
                        String[] currency = accountCurrency.split(",");
                        currencyId = currency[0];
                    }
                    orderModel.setAccountCurrency(currencyId);
                }
                SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
                String contract = request.getParameter("contract");
                String interPurchas = request.getParameter("interPurchas");
                String tableData = request.getParameter("tableGridData");
                float spot = Float.parseFloat(request.getParameter("spotNum").toString());
                int isAgree = Integer.parseInt(request.getParameter("spotChange"));
                float oldSpotNum = Float.parseFloat(request.getParameter("oldSpotNum").toString());
                String oldspotSupplier = request.getParameter("oldspotSupplier");
                String spotSupplier = request.getParameter("spotSupplier");
                String amount = request.getParameter("orderAmount");
                String rs = getService().doSaveOrUpdate(orderModel, systemUser, contract, interPurchas, tableData, spot, isAgree, oldSpotNum, amount, oldspotSupplier, spotSupplier);
                if (!rs.equals("true")) {
                    rsmsg.put("result", FAIL);
                    rsmsg.put("message", rs);
                    return rsmsg;
                }
                rsmsg.put("result", SUCCESS);
                return rsmsg;
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            rsmsg.put("result", FAIL);
            rsmsg.put("message", "订单保存出错!");
            return rsmsg;
        }
    }

    /**
     * <code>findproduct</code>
     * 查找产品剩余数量
     * @param request
     * @param response
     * @return
     * @since 2014年11月12日    wangya
     */
    @RequestMapping(value = "/findproduct")
    @ResponseBody
    public int findproduct(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        String mark = request.getParameter("mark");
        String pronum = request.getParameter("pronum");
        int num = 0;
        if (mark.equals("HT")) {//合同
            SalesContractProductModel product = salesContractProductService.get(Long.parseLong(id));
            num = getService().findproductQue(id);
            num = product.getQuantity() - num;
        } else {//內采
            InterPurchasProductModel product = interPurchasProductService.get(Long.parseLong(id));
            num = getService().findInterproductQue(id);
            num = product.getQuantity() - num;
        }
        num = num + Integer.parseInt(pronum);
        return num;
    }

    /**
     * <code>creatOrderCode</code>
     * 生成订单编号的AJAX方法
     * @param request
     * @param response
     * @return
     * @since 2014年6月25日    3unshine
     */
    @RequestMapping(value = "/creatOrderCode")
    @ResponseBody
    public String creatOrderCode(HttpServletRequest request, HttpServletResponse response) {
        String supplierCode = request.getParameter("supplierCode");
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(BusinessOrderModel.class);
        long startTime = getStartTime();
        long endTime = getEndTime();
        detachedCriteria.add(Restrictions.ge(BusinessOrderModel.CREATEDATE, new Date(startTime)));
        detachedCriteria.add(Restrictions.le(BusinessOrderModel.CREATEDATE, new Date(endTime)));

        SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
        detachedCriteria.add(Restrictions.eq(BusinessOrderModel.CREATORID, systemUser.getUserName()));
        //System.out.println("商务采购号码：" + systemUser.getBusiflag());
        List<BusinessOrderModel> BusinessOrderModel = this.getService().findOrderByCriteria(detachedCriteria);
        int serialNum = BusinessOrderModel.size();
        serialNum += 1;
        //根据商务人员自己的编码，自动生成订单编码

        Map<String, Object> map = getService().findCompanyInfo().get(0);//取公司编号
        String orderCode = map.get("companyCode") + "-" + CodeUtils.creatCode(supplierCode) + "-" + systemUser.getBusiflag() + serialNum;
        return orderCode;
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
     * <code>doAddOrder</code>
     * 保存并提交订单
     * @param request
     * @param response
     * @param manageModel
     * @return
     * @since   2014年5月5日    wangya
     */
    @ResponseBody
    @RequestMapping(value = "/doAddOrder")
    public Map<String, String> doAddOrder(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("_sino_eoss_cuotomer_addform") BusinessOrderModel orderModel, Errors error) {
        Map<String, String> rsmsg = new HashMap<String, String>();
        try {
            if (error.hasErrors()) {
                List<?> l = error.getAllErrors();
                rsmsg.put("result", FAIL);
                return rsmsg;
            }
            //是否提交过
            if (orderModel.getId() != null) {
                rsmsg.put("result", FAIL);
                rsmsg.put("message", "订单ID为空，提交失败！");
                return rsmsg;
            } else {
                if (request.getParameter("supplierId") != null && request.getParameter("supplierId") != "") {
                    long supplierId = Long.parseLong(request.getParameter("supplierId"));
                    SupplierInfoModel supplierInfoModel = supplierInfoService.get(supplierId);
                    orderModel.setSupplierInfoModel(supplierInfoModel);
                    String supplierShortName = supplierInfoModel.getShortName();
                    orderModel.setSupplierShortName(supplierShortName);
                }
                if (request.getParameter("contactId") != null && request.getParameter("contactId") != "") {
                    long contactId = Long.parseLong(request.getParameter("contactId"));
                    SupplierContactsModel supplierConstacts = supplierContactsService.get(contactId);
                    orderModel.setSupplierContactsModel(supplierConstacts);
                }
                if (request.getParameter("orderAmount") != null && request.getParameter("orderAmount") != "") {
                    double amo = Double.parseDouble(request.getParameter("orderAmount"));
                    BigDecimal amount = BigDecimal.valueOf(amo);
                    orderModel.setOrderAmount(amount);
                }

                if (request.getParameter("accountCurrency") != null && request.getParameter("accountCurrency") != "") {
                    String accountCurrency = request.getParameter("accountCurrency");
                    String currencyId = "";
                    if (!StringUtil.isEmpty(accountCurrency)) {
                        String[] currency = accountCurrency.split(",");
                        currencyId = currency[0];
                    }
                    orderModel.setAccountCurrency(currencyId);
                }

                SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
                String contract = request.getParameter("_contract");
                String interPurchas = request.getParameter("interPurchas");
                String tableData = request.getParameter("tableGridData");
                String spotStr = request.getParameter("spotNum");
                float spot = Float.parseFloat(spotStr);
                String spotSupplier = request.getParameter("spotSupplier");
                String spotId = request.getParameter("spotId");
                int isAgree = Integer.parseInt(request.getParameter("spotChange"));
                String amount = request.getParameter("orderAmount");
                String rs = getService().doAddOrders(orderModel, systemUser, contract, interPurchas, tableData, isAgree, spot, amount, spotSupplier, spotId);
                if (!rs.equals("true")) {
                    rsmsg.put("result", FAIL);
                    rsmsg.put("message", rs);
                    return rsmsg;
                }
                rsmsg.put("result", SUCCESS);
                return rsmsg;
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            rsmsg.put("result", FAIL);
            rsmsg.put("message", "订单保存出错!");
            return rsmsg;
        }
    }

    /**
     * <code>closeContractStatus</code>
     * 申请关闭合同
     * @param request
     * @return
     * @since   2014年7月25日    wangya
     */
    @ResponseBody
    @RequestMapping(value = "/closeContractStatus")
    public String closeContractStatus(HttpServletRequest request) {
        try {
            String id = request.getParameter("id");
            SalesContractModel salesContract = salesContractServive.get(Long.parseLong(id));
            long creatorId = salesContract.getCreator();
            BigDecimal contractAmount = salesContract.getContractAmount();
            int contractType = salesContract.getContractType();
            String contractShortName = salesContract.getContractName();
            closeContractService.closeContract(creatorId, contractAmount, contractType, contractShortName, id);
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return FAIL;
        }

    }

    /**
     * <code>rejectContract</code>
     * 驳回合同为草稿状态
     * @param request
     * @return
     * @since   2014年11月25日    wangya
     */
    @ResponseBody
    @RequestMapping(value = "/rejectContract")
    public String rejectContract(HttpServletRequest request) {
        try {
            String id = request.getParameter("id");
            //   SalesContractModel salesContract = salesContractServive.get(Long.parseLong(id));
            getService().rejectContract(Long.parseLong(id));//删除合同发票计划和盖章申请,改合同状态为草稿
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return FAIL;
        }

    }

    /*
    @ResponseBody
    @RequestMapping(value = "/delete")
    public String delete(HttpServletRequest request, HttpServletResponse response, @RequestParam("id") Long id) {
     try {
         getService().delete(id);
         return SUCCESS;
     } catch (Exception e) {
         e.printStackTrace();
         logger.error(e.getMessage());
         return FAIL;
     }
    }
    */
    /**
     * <code>detail</code>
     * 订单详情
     * @param request
     * @param response
     * @return
     * @since   2014年5月6日    wangya
     */
    @RequestMapping(value = "/detail")
    public ModelAndView detail(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("business/order/detail");
        BusinessOrderModel orderModel = new BusinessOrderModel();
        /* String taskId = request.getParameter("taskId")+"";
         long orderId = (Long) processService.getVariable(taskId, "orderId");
         if(orderId!=0){
             orderModel = getService().get(orderId);
         }*/
        String id = request.getParameter("id");
        String code = request.getParameter("code");
        String proId = request.getParameter("procInstId");
        String flat = request.getParameter("flat");
        if (flat != null) {
            request.setAttribute("flat", flat);
        }
        if (id != null) {
            orderModel = getService().get(Long.parseLong(id));
            request.setAttribute("proId", "0");
        }
        if (code != null) {
            orderModel = getService().findOrder(code);
            request.setAttribute("proId", "0");
        }
        if (proId != null) {
            orderModel = getService().findProdId(proId);
            request.setAttribute("proId", "1");
        }
        if (orderModel.getOrderStatus().contains(BusinessOrderContant.ORDER_CG)) {
            request.setAttribute("display", "CG");
        }
        if (orderModel.getOrderStatus().contains(BusinessOrderContant.ORDER_OK)) {
            request.setAttribute("display", "TGSP");
        }
        /*request.setAttribute("payment", orderModel.getPayStatus());
        request.setAttribute("reim", orderModel.getReimStatus());*/

        List<ProcInstAppr> proInstLogList = processService.findProcInstApprLog(orderModel.getProcessInstanceId());
        mav.addObject("proInstLogList", proInstLogList);
        mav.addObject("model", orderModel);
        String curreny = getService().getPayCurrenyType(orderModel.getId());
        request.setAttribute("currency", curreny);
        Set<SalesContractModel> sales = orderModel.getSalesContractModel();
        for (SalesContractModel salesContractModel : sales) {

            List<Map<String, Object>> product = salesContractServive.getOrderProductDifferent(salesContractModel.getId(), orderModel.getId());
            salesContractModel.setOrdersaleproducts(product);
        }

        mav.addObject("sales", sales);
        return mav;

    }

    /**
     * <code>saveOrUpdateUsd</code>
     * 美元修改详情
     * @param request
     * @param response
     * @return
     * @since   2016年2月1日    liyx
     */
    @RequestMapping(value = "/saveOrUpdateUsd")
    public ModelAndView saveOrUpdateUsd(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("business/order/saveOrUpdateUsd");
        BusinessOrderModel orderModel = new BusinessOrderModel();
        String id = request.getParameter("id");
        String code = request.getParameter("code");
        String proId = request.getParameter("procInstId");
        String flat = request.getParameter("flat");
        if (flat != null) {
            request.setAttribute("flat", flat);
        }
        if (id != null) {
            orderModel = getService().get(Long.parseLong(id));
            request.setAttribute("proId", "0");
        }
        if (code != null) {
            orderModel = getService().findOrder(code);
            request.setAttribute("proId", "0");
        }
        if (proId != null) {
            orderModel = getService().findProdId(proId);
            request.setAttribute("proId", "1");
        }
        if (orderModel.getOrderStatus().contains(BusinessOrderContant.ORDER_CG)) {
            request.setAttribute("display", "CG");
        }
        if (orderModel.getOrderStatus().contains(BusinessOrderContant.ORDER_OK)) {
            request.setAttribute("display", "TGSP");
        }

        Map<String, Object> map = getService().findCompanyInfo().get(0);
        request.setAttribute("address", map.get("address"));
        request.setAttribute("companyName", map.get("companyname"));
        request.setAttribute("bankName", map.get("bankname"));
        request.setAttribute("account", map.get("bankaccount"));

        List<ProcInstAppr> proInstLogList = processService.findProcInstApprLog(orderModel.getProcessInstanceId());
        mav.addObject("proInstLogList", proInstLogList);
        mav.addObject("model", orderModel);
        Set<SalesContractModel> sales = orderModel.getSalesContractModel();
        for (SalesContractModel salesContractModel : sales) {

            List<Map<String, Object>> product = salesContractServive.getOrderProductDifferent(salesContractModel.getId(), orderModel.getId());
            salesContractModel.setOrdersaleproducts(product);
        }

        mav.addObject("sales", sales);

        return mav;
    }

    /**
     * <code>doSaveOrUpdateUsd</code>
     * 保存更新产品信息（美元）
     * @param request
     * @param response
     * @param manageModel
     * @return
     * @since   2016年2月1日    liyx
     */
    @ResponseBody
    @RequestMapping(value = "/doSaveOrUpdateUsd")
    public Map<String, String> doSaveOrUpdateUsd(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> rsmsg = new HashMap<String, String>();
        try {
            String id = request.getParameter("id");
            String orderAmount = request.getParameter("_orderAmount");
            String[] ids = request.getParameterValues("ids");
            String[] unitPrices = request.getParameterValues("unitPrices");
            String[] subTotals = request.getParameterValues("subTotals");

            //更新订单金额
            if (!StringUtil.isEmpty(id)) {
                BusinessOrderModel order = getService().get(Long.parseLong(id));
                order.setOrderAmount(new BigDecimal(orderAmount));
                getService().update(order);
            }

            //更新订单产品
            if (ids != null) {
                for (int i = 0; i < ids.length; i++) {
                    BusinessOrderProductModel product = businessOrderProductService.get(Long.parseLong(ids[i]));
                    product.setUnitPrice(new BigDecimal(unitPrices[i]));
                    product.setSubTotal(new BigDecimal(subTotals[i]));
                    businessOrderProductService.update(product);
                }
                rsmsg.put("result", SUCCESS);
                return rsmsg;
            } else {
                rsmsg.put("result", FAIL);
                rsmsg.put("message", "订单保存出错！");
                return rsmsg;
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            rsmsg.put("result", FAIL);
            rsmsg.put("message", "订单保存出错!");
            return rsmsg;
        }
    }

    /**
     * <code>detail</code>
     * 合同详情
     * @param request
     * @param response
     * @return
     * @since   2014年5月6日    wangya
     */
    @RequestMapping(value = "/Contractdetail")
    public ModelAndView Contractdetail(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("business/order/waitContract/detail");
        String id = request.getParameter("id");
        String close = request.getParameter("close");
        if (close != null) {
            request.setAttribute("close", close);
        }
        SalesContractModel salesContractModel = new SalesContractModel();
        Set<BusinessOrderProductModel> orderproduct = new HashSet<BusinessOrderProductModel>(0);
        //取出合同实体发送到前台页面
        if (id != null && !"".equals(id)) {
            salesContractModel = salesContractServive.get(Long.parseLong(id));
            List<SalesContractStatusModel> status = getService().findContratStatus(id);
            String orderStatus = status.get(0).getOrderStatus();
            if (orderStatus.equals(SalesContractConstant.CONTRACT_ORDER_INITSTATE)) {
                request.setAttribute("orderstatus", "no");//未下订单
            } else {
                request.setAttribute("orderstatus", "yes");
            }
            List<BusinessOrderProductModel> products = businessOrderProductService.getOrderProduct(Long.parseLong(id));
            if (products != null) {
                for (int i = 0; i < products.size(); i++) {
                    BusinessOrderProductModel orderProduct = products.get(i);
                    orderproduct.add(orderProduct);
                }
            }
            mav.addObject("productmodel", orderproduct);
            mav.addObject("model", salesContractModel);
            mav.addObject("isApply", closeContractService.getCloseContractModelBySalesId(salesContractModel.getId()));
        }
        return mav;
    }

    /**
     * <code>tableGridDataUnit</code>
     * 拼接tableGridData所需要的JSON数据
     * @param salesContractModel 销售合同实体
     * @return String
     * @since   2014年6月23日    3unshine
     */
    public String tableGridDataUnit(SalesContractModel salesContractModel) {
        String formDataJson = "";
        Map<String, Object> tableModel = new HashMap<String, Object>(); //表格数据模型
        tableModel.put("name", "editGridName"); //表格名称
        List<HashMap<String, Object>> rowNames = new ArrayList<HashMap<String, Object>>(); //表格中的列数据

        for (int i = 0; i < column.length; i++) {
            HashMap<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("name", column[i]); //字段名称，用于保存数据
            dataMap.put("label", columnName[i]);//字段在页面上的显示名称
            dataMap.put("type", columnTypes[i]); //表格中输入字段的类型，目前只支持string,date
            rowNames.add(dataMap);
        }

        tableModel.put("fieldList", rowNames);
        List<HashMap<String, Object>> rowDatas = new ArrayList<HashMap<String, Object>>(); //表格总的数据，一行数据对应一个map
        if (salesContractModel != null) {
            Set<SalesContractProductModel> salesContractProductModels = salesContractModel.getSalesContractProductModel();
            for (SalesContractProductModel s : salesContractProductModels) { //放入2行数据
                System.out.println(s.getProductName());
            }
            Set<SalesContractReceivePlanModel> salesContractReceivePlans = salesContractModel.getSalesContractReceivePlans();
            int rows = salesContractModel.getSalesContractReceivePlans().size();
            tableModel.put("defaultAmount", rows);//新建表格时，默认为0，如果是修改，应该表格数据的总行数。
            for (SalesContractReceivePlanModel s : salesContractReceivePlans) { //放入2行数据
                HashMap<String, Object> dataMap = new HashMap<String, Object>(); //因为先前在rowNames中定义表格有3列
                dataMap.put(columns[0], s.getPlanedReceiveAmount());
                dataMap.put(columns[1], s.getPlanedReceiveDate());
                dataMap.put(columns[2], s.getPayCondition());
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

    @RequestMapping(value = "/selectContractView")
    @ResponseBody
    public ModelAndView selectContractView(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("business/order/selectContract");
        return mav;
    }

    @RequestMapping(value = "/selectInterPurchasView")
    @ResponseBody
    public ModelAndView selectInterPurchasView(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("business/order/selectInterPurchas");
        return mav;
    }

    /**
     * <code>swjlspDetail</code>
     *商务经理审批页面
     * @param request
     * @param response
     * @return
     * @since   2014年5月6日    wangya
     */
    @RequestMapping(value = "/orderReview/swjlsp")
    public ModelAndView swjlspDetail(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("business/order/orderReview/swjlsp");
        String taskId = request.getParameter("taskId");
        request.setAttribute("taskId", taskId);
        long id = (Long) processService.getVariable(taskId, "orderId");
        BusinessOrderModel orderModel = getService().get(id);
        /* //判断是否是商务经理审批
         Object nameObj = getService().getOrderById(orderModel.getId()).get("NAME_");
         String name = "";
         if(nameObj != null){
             name = nameObj.toString();
         }
         if(!name.equals("商务经理审批")){
             mav = new ModelAndView("sales/contract/updateSalesContract1");
             return mav;
         }*/

        //审批日志
        List<ProcInstAppr> proInstLogList = processService.findProcInstApprLog(orderModel.getProcessInstanceId());
        mav.addObject("proInstLogList", proInstLogList);
        mav.addObject("model", orderModel);
        //判断是合同产品还是内采产品
        Set<BusinessOrderProductModel> products = orderModel.getBusinessOrderProductModel();
        Iterator<BusinessOrderProductModel> p = products.iterator();
        while (p.hasNext()) {
            BusinessOrderProductModel pro = p.next();
            if (pro.getSalesContractModel() != null) {
                request.setAttribute("mark", "HT");
            } else {
                request.setAttribute("mark", "NC");
            }
        }
        return mav;
    }

    /**
     * <code>swzgsp</code>
     * 商务主管审批页面
     * @param request
     * @param response
     * @return
     * @since   2014年5月6日    wangya
     */
    @RequestMapping(value = "/orderReview/swzgsp")
    public ModelAndView swzgspDetail(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("business/order/orderReview/swzgsp");
        String taskId = request.getParameter("taskId");
        long id = (Long) processService.getVariable(taskId, "orderId");
        BusinessOrderModel orderModel = getService().get(id);
        request.setAttribute("taskId", taskId);
        //判断是合同产品还是内采产品
        Set<BusinessOrderProductModel> products = orderModel.getBusinessOrderProductModel();
        Iterator<BusinessOrderProductModel> p = products.iterator();
        while (p.hasNext()) {
            BusinessOrderProductModel pro = p.next();
            if (pro.getSalesContractModel() != null) {
                request.setAttribute("mark", "HT");
            } else {
                request.setAttribute("mark", "NC");
            }
        }
        //审批日志
        List<ProcInstAppr> proInstLogList = processService.findProcInstApprLog(orderModel.getProcessInstanceId());
        mav.addObject("proInstLogList", proInstLogList);
        mav.addObject("model", orderModel);
        return mav;
    }

    /**
    * <code>orderReview/saveOrUpdate</code>
    * 重新提交页面
    * @param request
    * @param response
    * @return
    * @since   2014年5月6日    wangya
    */
    @RequestMapping(value = "/orderReview/saveOrUpdate")
    public ModelAndView reviewUpdate(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("business/order/orderReview/saveOrUpdate");
        String taskId = request.getParameter("taskId");
        long id = (Long) processService.getVariable(taskId, "orderId");
        BusinessOrderModel orderModel = getService().get(id);
        request.setAttribute("taskId", taskId);
        mav.addObject("model", orderModel);

        //判断是合同产品还是内采产品
        if (orderModel.getSalesContractModel().size() != 0) {
            request.setAttribute("mark", "HT");
        }
        if (orderModel.getInterPurchasModel().size() != 0) {
            request.setAttribute("mark", "NC");
        }
        // Set<BusinessOrderProductModel> products = orderModel.getBusinessOrderProductModel();
        /* Iterator<BusinessOrderProductModel> p = products.iterator();
         while (p.hasNext()) {
             BusinessOrderProductModel pro = p.next();
             if (pro.getSalesContractModel() != null) {
                 request.setAttribute("mark", "HT");
             } else {
                 request.setAttribute("mark", "NC");
             }
         }*/
        Map<String, Object> tableModel = new HashMap<String, Object>(); //表格数据模型
        tableModel.put("name", "editGridName"); //表格名称
        List<HashMap<String, Object>> rowNames = new ArrayList<HashMap<String, Object>>(); //表格中的列数据
        tableModel.put("fieldList", rowNames);
        List<HashMap<String, Object>> rowDatas = new ArrayList<HashMap<String, Object>>(); //表格总的数据，一行数据对应一个map
        //     Set<BusinessOrderProductModel> businessOrderProductModel = model.getBusinessOrderProductModel();
        tableModel.put("defaultAmount", 0);//新建表格时，默认为0，如果是修改，应该表格数据的总行数。
        tableModel.put("dataList", rowDatas);
        ObjectMapper objectMapper = JacksonJsonMapper.getInstance();
        try {
            List<ProcInstAppr> proInstLogList = processService.findProcInstApprLog(orderModel.getProcessInstanceId());
            mav.addObject("proInstLogList", proInstLogList);
            String form = objectMapper.writeValueAsString(tableModel);
            request.setAttribute("form", form);
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mav;
    }

    /**
    * <code>endOrder</code>
    * 删除订单
    * @param request
    * @param response
    * @return
    * @since   2014年5月6日    wangya
    */
    @ResponseBody
    @RequestMapping(value = "/endOrder")
    public String endOrder(HttpServletRequest request, HttpServletResponse response, BusinessOrderModel orderModel) {
        try {
            SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
            getService().endOrder(systemUser, orderModel);
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return FAIL;
        }
    }

    /**
     * <code>doReviewUpdate</code>
     * 保存更新实体
     * @param request
     * @param response
     * @param orderModel
     * @return
     * @since   2014年5月5日    wangya
     */
    @ResponseBody
    @RequestMapping(value = "/doReviewUpdate")
    public Map<String, String> doReviewUpdate(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("_sino_eoss_cuotomer_updateform") BusinessOrderModel orderModel) {
        Map<String, String> rsmsg = new HashMap<String, String>();
        try {
            SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
            orderModel.setCreator(systemUser.getStaffName());
            orderModel.setCreatorId(systemUser.getUserName());
            BigDecimal amount = new BigDecimal(0.00);
            if (request.getParameter("supplierId") != null && request.getParameter("supplierId") != "") {
                long supplierId = Long.parseLong(request.getParameter("supplierId"));
                System.out.println(supplierId);
                SupplierInfoModel supplierInfoModel = supplierInfoService.get(supplierId);
                orderModel.setSupplierInfoModel(supplierInfoModel);
                String supplierShortName = supplierInfoModel.getShortName();
                orderModel.setSupplierShortName(supplierShortName);
            }
            if (request.getParameter("contactId") != null && request.getParameter("contactId") != "") {
                long contactId = Long.parseLong(request.getParameter("contactId"));
                System.out.println(contactId);
                SupplierContactsModel supplierConstacts = supplierContactsService.get(contactId);
                orderModel.setSupplierContactsModel(supplierConstacts);
            }
            if (request.getParameter("orderAmount") != null && request.getParameter("orderAmount") != "") {
                String amo = request.getParameter("orderAmount").toString();
                amount = new BigDecimal(amo);
                orderModel.setOrderAmount(amount);
            }
            if (request.getParameter("accountCurrency") != null && request.getParameter("accountCurrency") != "") {
                String accountCurrency = request.getParameter("accountCurrency");
                String currencyId = "";
                if (!StringUtil.isEmpty(accountCurrency)) {
                    String[] currency = accountCurrency.split(",");
                    currencyId = currency[0];
                }
                orderModel.setAccountCurrency(currencyId);
            }

            String contract = request.getParameter("contract");
            String interPurchas = request.getParameter("interPurchas");
            String tableData = request.getParameter("tableGridData");
            String taskId = request.getParameter("taskId");
            float spot = Float.parseFloat(request.getParameter("spotNum").toString());
            int isAgree = Integer.parseInt(request.getParameter("spotChange"));
            float oldSpotNum = Float.parseFloat(request.getParameter("oldSpotNum").toString());
            String oldspotSupplier = request.getParameter("oldspotSupplier");
            String spotSupplier = request.getParameter("spotSupplier");
            String rs = getService().doReviewUpdate(orderModel, contract, interPurchas, tableData, taskId, systemUser, spot, isAgree, oldSpotNum, oldspotSupplier, spotSupplier);

            if (!rs.equals("ture")) {
                rsmsg.put("result", FAIL);
                rsmsg.put("message", rs);
                return rsmsg;
            }
            rsmsg.put("result", SUCCESS);
            return rsmsg;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            rsmsg.put("result", FAIL);
            rsmsg.put("message", "订单保存出错!");
            return rsmsg;
        }
    }

    /**
     * <code>remove</code>
     * 订单删除：草稿状态的
     * @param request
     * @param response
     * @param id
     * @return
     * @since   2014-05-22    wangya
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
            logger.error(e.getMessage());
            return FAIL;
        }
    }

    /**
     * <code>handleFlow</code>
     * 处理流程
     * @param systemUser 操作人实体
     * @param taskId 任务ID
     * @param isAgree 是否同意
     * @param remark 审批意见
     * @since   2014年6月16日    wangya
     */
    @RequestMapping(value = "/handleFlow")
    public ModelAndView handleFlow(HttpServletRequest request, HttpServletResponse response) {
        SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
        String userId = systemUser.getUserName();
        String taskId = request.getParameter("taskId");
        int isAgree = Integer.parseInt(request.getParameter("isAgree"));
        String tmp = request.getParameter("local");
        Long id = Long.parseLong(request.getParameter("id"));
        BusinessOrderModel businessOrderModel = getService().get(id);
        Object nameObj = getService().getOrderById(id).get("NAME_");
        String name = "";
        if (nameObj != null) {
            name = nameObj.toString();
        }
        if (name.equals(tmp)) {
            String remark = request.getParameter("remark");
            processService.handle(taskId, userId, isAgree, remark, null, null, null);
            if (tmp.equals(BusinessOrderContant.SWJLSP)) {//添加订单生效时间，只用于订单的导出
                //  if (tmp.equals(BusinessOrderContant.FZJLSP_XF)) { //给相孚使用
                if (isAgree == 1) {
                    Date time = new Date();
                    getService().updateBeginTime(id, time);
                }
            }
            if (tmp.equals(BusinessOrderContant.SWZGSP)) {
                //if (tmp.equals(BusinessOrderContant.GZSP_XF)) { //给相孚使用
                if (isAgree == 1) {
                    getService().updateOrderStatus(id);
                    //判断：内采还是合同
                    if (businessOrderModel.getOrderType().equals("4")) {
                        //更改内采中的处理状态
                        Set<InterPurchasModel> inter = businessOrderModel.getInterPurchasModel();
                        Iterator<InterPurchasModel> i = inter.iterator();
                        while (i.hasNext()) {
                            int flat = 0;
                            InterPurchasModel t = i.next();
                            Set<InterPurchasProductModel> p = t.getInterPurchasProduct();
                            Iterator<InterPurchasProductModel> ip = p.iterator();
                            while (ip.hasNext()) {
                                InterPurchasProductModel pro = ip.next();
                                List<BusinessOrderProductModel> order = orderProductService.getProductOrder(pro);
                                if (order != null) {
                                    int qua = 0;
                                    for (int j = 0; j < order.size(); j++) {
                                        BusinessOrderProductModel orderProduct = order.get(j);
                                        qua += orderProduct.getQuantity();
                                    }
                                    qua = pro.getQuantity() - qua;
                                    if (qua > 0) {
                                        flat = 1;
                                    }
                                } else {
                                    flat = 1;
                                }
                            }
                            if (flat == 0) {
                                getService().updateInterStatus(t.getId());
                            }
                        }
                    } else {
                        //更改合同中的订单状态
                        getService().updateSaleSatatus(id);
                    }
                }
            }
        }
        ModelAndView mav = new ModelAndView("business/order/manage");
        return mav;
    }

    /**
     * <code>FlowSteps</code>
     * 用于流程控制所需标示的枚举类
     * @since   2014年6月09日    wangya
     */
    public enum FlowSteps {
        //STATR 开始
        //SWJLSP  商务经理审批
        //SWZGSP  商务主管审批
        //DDCK 订单查看
        //DDXG 重新修改
        SWJLSP, SWZGSP, DDCK, DDXG;
    }

    /**
     * <code>fileUpdate</code>
     *客户签收单页面
     * @param request
     * @param response
     * @return
     * @since   2014年7月15日    wangya
     */
    @RequestMapping(value = "/fileUpdate")
    public ModelAndView fileUpdate(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("business/order/fileUpdate");
        String id = request.getParameter("id");
        BusinessOrderModel orderModel = getService().get(Long.parseLong(id));
        mav.addObject("model", orderModel);
        return mav;
    }

    /**客户签收单页面
     * <code>fileDetail</code>
     * @param request
     * @param response
     * @return
     * @since   2014年12月25日    wangya
     */
    @RequestMapping(value = "/fileDetail")
    public ModelAndView fileDetail(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("business/order/fileDetail");
        String id = request.getParameter("id");
        BusinessOrderModel orderModel = getService().get(Long.parseLong(id));
        mav.addObject("model", orderModel);
        return mav;
    }

    /**
     * <code>fileUploadData</code>
     * AJAX获取附件
     * @param request
     * @param response
     * @return
     * @since 2014年7月18日     wangya
     */
    @RequestMapping(value = "/fileUploadData")
    @ResponseBody
    public FileVo fileUploadData(HttpServletRequest request) {
        FileVo fileVo = FileUtils.creatFileUploadData();
        SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
        fileVo.setUsername(systemUser.getUserName());
        fileVo.setStaffname(systemUser.getStaffName());
        String flag = request.getParameter("fileUploadFlag");

        long orderId = Long.parseLong(request.getParameter("id"));
        BusinessOrderModel orderModel = getService().get(orderId);
        if (orderModel.getAttachIds() != null && !orderModel.getAttachIds().equals("")) {
            String[] attachIds = orderModel.getAttachIds().split(",");
            Long[] ids = CodeUtils.stringArrayToLongArray(attachIds);
            fileVo.setValue(fileService.findSysAttachByIds(ids));
        }

        if (flag.equals("detail")) {
            fileVo.setBoolRequire(false);
            fileVo.setBoolWrite(false);
        }

        return fileVo;
    }

    /**
     * <code>doSaveFile</code>
     * 保存更新实体
     * @param request
     * @param response
     * @param orderModel
     * @return
     * @since   2014年7月15日    wangya
     */
    @ResponseBody
    @RequestMapping(value = "/doSaveFile")
    public String doSaveFile(HttpServletRequest request, HttpServletResponse response, BusinessOrderModel orderModel) {
        try {
            String fileId = request.getParameter("attachIds") == null ? "" : request.getParameter("attachIds");
            getService().savefile(orderModel, fileId);
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return FAIL;
        }
    }

    /**
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/confirmInbound")
    public ModelAndView confirmInbound(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("business/order/inbound/confirmInbound");
        return mav;
    }

    /**
     * <code>inboundInfo</code>
     * 库存列表数据
     * @param request
     * @param response
     * @return
     * @since   2014年10月11日    guokemenng
     */
    @ResponseBody
    @RequestMapping(value = "/inboundTableList")
    public Map<String, Object> inboundInfo(HttpServletRequest request, HttpServletResponse response) {
        String params = request.getParameter("sEcho");
        String iDisplayStart = request.getParameter("iDisplayStart");
        String iDisplayLength = request.getParameter("iDisplayLength");
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(InboundModel.class);

        detachedCriteria.add(Restrictions.eq(InboundModel.STATE, (short) 0));
        detachedCriteria.addOrder(Order.desc(InboundModel.INBOUNDCODE));

        PaginationSupport paginationSupport = this.getService().findPageByCriteria(detachedCriteria, Integer.parseInt(iDisplayStart), Integer.parseInt(iDisplayLength));
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("aaData", paginationSupport.getItems());
        map.put("sEcho", params);
        map.put("iTotalDisplayRecords", paginationSupport.getTotalCount());
        map.put("iTotalRecords", paginationSupport.getTotalCount());
        return map;
    }

    /**
     * <code>getOrderListPage</code>
     * 得到没有确认收货的订单页面
     * @param request
     * @param response
     * @return
     * @since   2014年10月11日    guokemenng
     */
    @RequestMapping(value = "/getOrderListPage")
    public ModelAndView getOrderListPage(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("business/order/inbound/getOrderListPage");
        return mav;
    }

    /**
     * <code>getProductOrderList</code>
     * 得到没有确认收货的订单列表
     * @param request
     * @param response
     * @return
     * @since   2014年10月11日    guokemenng
     */
    @ResponseBody
    @RequestMapping(value = "/getProductOrderList")
    public Map<String, Object> getProductOrderList(HttpServletRequest request, HttpServletResponse response) {
        String params = request.getParameter("sEcho");
        String iDisplayStart = request.getParameter("iDisplayStart");
        String iDisplayLength = request.getParameter("iDisplayLength");
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(BusinessOrderModel.class);

        SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
        detachedCriteria.add(Restrictions.eq(BusinessOrderModel.CREATORID, systemUser.getUserName()));
        detachedCriteria.add(Restrictions.eq(BusinessOrderModel.DELIVERYADDRESS, "1"));
        detachedCriteria.add(Restrictions.ne(BusinessOrderModel.WAREHOUSESTATUS, "A"));

        detachedCriteria.addOrder(Order.desc(BusinessOrderModel.ID));

        PaginationSupport paginationSupport = this.getService().findPageByCriteria(detachedCriteria, Integer.parseInt(iDisplayStart), Integer.parseInt(iDisplayLength));
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("aaData", paginationSupport.getItems());
        map.put("sEcho", params);
        map.put("iTotalDisplayRecords", paginationSupport.getTotalCount());
        map.put("iTotalRecords", paginationSupport.getTotalCount());
        return map;
    }

    /**产品关联订单
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/relateOrderAndProduct")
    public String relateOrderAndProduct(HttpServletRequest request, HttpServletResponse response) {
        try {
            String orderId = request.getParameter("orderId");
            String inboundCode = request.getParameter("inboundCode");

            getService().relateOrderAndProduct(Long.parseLong(orderId), inboundCode);
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return FAIL;
        }
    }

    /**
     * <code>getProductDetailByOrderId</code>
     * 得到订单产品详情
     * @param request
     * @param response
     * @return
     * @since   2014年10月11日    guokemenng
     */
    @ResponseBody
    @RequestMapping(value = "/getProductDetailByOrderId")
    public List<Map<String, Object>> getProductDetailByOrderId(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        BusinessOrderModel order = getService().get(Long.parseLong(id));
        List<Map<String, Object>> pList = new ArrayList<Map<String, Object>>();

        Set<BusinessOrderProductModel> pSet = order.getBusinessOrderProductModel();
        Iterator<BusinessOrderProductModel> it = pSet.iterator();
        while (it.hasNext()) {
            BusinessOrderProductModel p = it.next();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("productNo", p.getProductNo());
            map.put("productNum", p.getQuantity());
            pList.add(map);
        }

        return pList;
    }

    /**
     * <code>findSpotNum</code>
     * 得到厂商返点数
     * @param request
     * @param response
     * @return
     * @since   2014年11月20日    wangya
     */
    @ResponseBody
    @RequestMapping(value = "/findSpotNum")
    public Map<String, Object> findSpotNum(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<Map<String, Object>> spotModel = getService().getListSupplierSpot();
        map.put("spotModel", spotModel);
        if (request.getParameter("orderId") != null) {
            String orderId = request.getParameter("orderId");
            BusinessOrderModel order = getService().get(Long.parseLong(orderId));
            if (order.getSpotNum() > 0) {
                ReturnSpotModel spot = getService().getSupplierNameSpot(order.getSpotSupplier());
                map.put("spotId", spot.getId() - 1);
            } else {
                map.put("spotId", "-1");
            }
        }
        return map;
    }

    /**
     * <code>findSpotNum</code>
     * 得到厂商返点数
     * @param request
     * @param response
     * @return
     * @since   2014年11月20日    wangya
     */

    /*
    @ResponseBody
    @RequestMapping(value = "/findSpotNum")
    public float findSpotNum(HttpServletRequest request, HttpServletResponse response) {
     String id = request.getParameter("supplierId");
     float num = 0;
     if (id != null && id != "") {
         ReturnSpotModel spotModel = getService().getSupplierSpot(Long.parseLong(id));
         if (spotModel != null) {
             num = spotModel.getReturnAmount();
         }
     }
     return num;
    }*/

    /**
     * <code>searchPay</code>
     * “成本管理”中查询条件管理页面
     * @param request
     * @param response
     * @return
     * @since   2014年11月20日    wangya
     */
    @RequestMapping("/costControl/searchPay")
    public ModelAndView searchPay(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("business/order/costControl/searchPay");
        return mav;
    }

    /**
     * <code>searchReim</code>
     * “成本管理”中查询条件管理页面
     * @param request
     * @param response
     * @return
     * @since   2014年11月20日    wangya
     */
    @RequestMapping("/costControl/searchReim")
    public ModelAndView searchReim(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("business/order/costControl/searchReim");
        return mav;
    }

    /**验证订单编号是否唯一
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/checkOrderCode")
    @ResponseBody
    public Map<String, String> checkOrderCode(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> rsmsg = new HashMap<String, String>();
        String orderCode = request.getParameter("orderCode");
        List<Map<String, Object>> check = getService().findOrderCode(orderCode);
        rsmsg.put("result", SUCCESS);
        if (check.size() > 0) {
            rsmsg.put("result", FAIL);
            rsmsg.put("message", "订单编号重复，请重新填写！");
            return rsmsg;
        }
        return rsmsg;
    }

    /**订单变更申请页面
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/change")
    public ModelAndView change(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("business/order/change");
        String id = request.getParameter("id");
        BusinessOrderModel order = getService().get(Long.parseLong(id));
        mav.addObject("model", order);
        return mav;
    }

    /**
     * <code>doChange</code>
     * 保存变更信息
     * @param request
     * @param response
     * @param manageModel
     * @return
     * @since   2014年12月24日    wangya
     */
    @ResponseBody
    @RequestMapping(value = "/doChange")
    public Map<String, String> doChange(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("_sino_eoss_cuotomer_updateform") BusinessOrderModel orderModel) {
        Map<String, String> rsmsg = new HashMap<String, String>();
        try {
            SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
            String remark = request.getParameter("remark");
            String rs = getService().dosaveChange(orderModel, systemUser, remark);
            if (!rs.equals("true")) {
                rsmsg.put("result", FAIL);
                rsmsg.put("message", "变更提交出错!");
                return rsmsg;
            }
            rsmsg.put("result", SUCCESS);
            return rsmsg;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            rsmsg.put("result", FAIL);
            rsmsg.put("message", "变更提交出错!");
            return rsmsg;
        }
    }

    /**订单变更修改页面
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/changeUpdate")
    public ModelAndView changeUpdate(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("business/order/changeUpdate");
        String id = request.getParameter("id");
        BusinessOrderModel order = new BusinessOrderModel();
        if (id != null && !"".equals(id)) {
            order = getService().get(Long.parseLong(id));
            mav.addObject("model", order);
            //判断是合同产品还是内采产品
            Set<BusinessOrderProductModel> products = order.getBusinessOrderProductModel();
            Iterator<BusinessOrderProductModel> p = products.iterator();
            while (p.hasNext()) {
                BusinessOrderProductModel pro = p.next();
                if (pro.getSalesContractModel() != null) {
                    request.setAttribute("mark", "HT");
                } else {
                    request.setAttribute("mark", "NC");
                }
            }
            String supplier = "OK";
            if (!order.getPayStatus().equals("N") || !order.getReimStatus().equals("N")) {
                supplier = "No";
            }
            request.setAttribute("supplier", supplier);
        }
        return mav;
    }

    /**
     * <code>doChangeUpdate</code>
     * 保存更新实体
     * @param request
     * @param response
     * @param manageModel
     * @return
     * @since   2014年12月25日    wangya
     */
    @ResponseBody
    @RequestMapping(value = "/doChangeUpdate")
    public Map<String, String> doChangeUpdate(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("_sino_eoss_cuotomer_updateform") BusinessOrderModel orderModel) {
        Map<String, String> rsmsg = new HashMap<String, String>();
        try {
            String status = orderModel.getIsChange();
            if (status.equals("0") || status == null) {
                rsmsg.put("result", FAIL);
                rsmsg.put("message", "订单已变更！");
                return rsmsg;
            } else {
                if (request.getParameter("supplierId") != null && request.getParameter("supplierId") != "") {
                    long supplierId = Long.parseLong(request.getParameter("supplierId"));
                    SupplierInfoModel supplierInfoModel = supplierInfoService.get(supplierId);
                    orderModel.setSupplierInfoModel(supplierInfoModel);
                    String supplierShortName = supplierInfoModel.getShortName();
                    orderModel.setSupplierShortName(supplierShortName);
                }
                if (request.getParameter("contactId") != null && request.getParameter("contactId") != "") {
                    long contactId = Long.parseLong(request.getParameter("contactId"));
                    SupplierContactsModel supplierConstacts = supplierContactsService.get(contactId);
                    orderModel.setSupplierContactsModel(supplierConstacts);
                }
                BigDecimal amount = new BigDecimal(0.00);
                if (request.getParameter("orderAmount") != null && request.getParameter("orderAmount") != "") {
                    double amo = Double.parseDouble(request.getParameter("orderAmount"));
                    amount = BigDecimal.valueOf(amo);
                    orderModel.setOrderAmount(amount);
                }
                SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
                String contract = request.getParameter("contract");
                String interPurchas = request.getParameter("interPurchas");
                String tableData = request.getParameter("tableGridData");
                float spot = Float.parseFloat(request.getParameter("spotNum").toString());
                int isAgree = Integer.parseInt(request.getParameter("spotChange"));
                float oldSpotNum = Float.parseFloat(request.getParameter("oldSpotNum").toString());
                String oldspotSupplier = request.getParameter("oldspotSupplier");
                String spotSupplier = request.getParameter("spotSupplier");
                String orderCode = request.getParameter("code");
                String rStatus = request.getParameter("rStatus");
                String pStatus = request.getParameter("pStatus");
                BigDecimal oldAmount = new BigDecimal(request.getParameter("oldAmount"));
                orderModel.setOrderCode(orderCode);
                if (oldAmount.compareTo(amount) == -1) {
                    if (pStatus.equals("A")) {
                        orderModel.setPayStatus("S");
                    }
                    if (rStatus.equals("A")) {
                        orderModel.setReimStatus("S");
                    }
                }
                String rs = getService().doChangeUpdate(orderModel, systemUser, contract, interPurchas, tableData, spot, isAgree, oldSpotNum, oldspotSupplier, spotSupplier);
                if (!rs.equals("true")) {
                    rsmsg.put("result", FAIL);
                    rsmsg.put("message", rs);
                    return rsmsg;
                }
                rsmsg.put("result", SUCCESS);
                return rsmsg;
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            rsmsg.put("result", FAIL);
            return rsmsg;
        }
    }

    /**
     * <code>orderCachetManage</code>
     * 订单盖章
     * @param request
     * @param response
     * @return
     * @since   2015年7月6日    guokemenng
     */
    @RequestMapping(value = "/cachet/manage")
    public ModelAndView orderCachetManage(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("business/order/cachet/manage");
        return mav;
    }

    /**
     * <code>cachetSearch</code>
     * 订单盖章搜索页面
     * @param request
     * @param response
     * @return
     * @since   2015年7月6日    guokemenng
     */
    @RequestMapping("/cachet/cachetSearch")
    public ModelAndView cachetSearch(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("business/order/cachet/cachetSearch");
        return mav;
    }

    /**
     * <code>getCachetList</code>
     *
     * @param request
     * @param response
     * @return
     * @since   2015年7月6日    guokemenng
     */
    @ResponseBody
    @RequestMapping(value = "/cachet/getCachetList")
    public Map<String, Object> getCachetList(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();
        String pageNo = request.getParameter("pageNo");
        String pageSize = request.getParameter("pageSize");
        if (pageNo != null) {
            this.pageNo = Integer.parseInt(pageNo) - 1;
        }
        if (pageSize != null) {
            this.pageSize = Integer.parseInt(pageSize);
        }
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(getModel().getClass());
        HashMap<String, Object> searchMap = new HashMap<String, Object>();
        buildSearch_orderSearch(request, detachedCriteria, searchMap);

        detachedCriteria.add(Restrictions.eq(BusinessOrderModel.ORDERSTATUS, "TGSP"));

        detachedCriteria.addOrder(Order.desc(BusinessOrderModel.ID));
        PaginationSupport paginationSupport = getService().findPageByCriteria(detachedCriteria, this.pageNo * this.pageSize, this.pageSize);

        //得到查询后所有订单的总金额
        //        String amount = getService().findTotallAmount(searchMap);

        //得到查询后页面总金额
        //        List<BusinessOrderModel> listbus = (List<BusinessOrderModel>) paginationSupport.getItems();

        //        Iterator<BusinessOrderModel> bus = listbus.iterator();
        //        BigDecimal pageAmount = new BigDecimal(0.00);
        //        while (bus.hasNext()) {
        //            BusinessOrderModel b = bus.next();
        //            if (b.getOrderAmount() != null) {
        //                pageAmount = pageAmount.add(b.getOrderAmount());
        //            }
        //        }
        //        String page = pageAmount.toString();
        map.put("rows", paginationSupport.getItems());
        map.put("total", paginationSupport.getTotalCount());
        map.put("page", pageNo);
        //        map.put("totallAmount", amount);
        //        map.put("pageAmount", page);
        return map;
    }

    /**
     * <code>cachetApprove</code>
     * 执行盖章操作
     * @param request
     * @param response
     * @return
     * @since   2015年7月6日    guokemenng
     */
    @ResponseBody
    @RequestMapping("/cachet/cachetApprove")
    public String cachetApprove(HttpServletRequest request, HttpServletResponse response) {
        try {

            String id = request.getParameter("id");
            String cachetAdvice = request.getParameter("cachetAdvice");
            BusinessOrderModel model = getService().get(Long.parseLong(id));
            model.setCachetTime(new Date());
            if (!StringUtil.isEmpty(cachetAdvice)) {
                model.setCachetAdvice(cachetAdvice);
            }
            getService().update(model);

            return SUCCESS;
        } catch (Exception e) {
            return FAIL;
        }
    }
}
