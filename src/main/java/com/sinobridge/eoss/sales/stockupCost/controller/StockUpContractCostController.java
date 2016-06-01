/*
 * FileName: StockUpContractController.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.sales.stockupCost.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sinobridge.base.core.controller.DefaultBaseController;
import com.sinobridge.base.core.utils.PaginationSupport;
import com.sinobridge.eoss.bpm.service.process.ProcessService;
import com.sinobridge.eoss.sales.cachet.service.SalesCachetService;
import com.sinobridge.eoss.sales.contract.model.SalesContractModel;
import com.sinobridge.eoss.sales.contract.service.SalesContractService;
import com.sinobridge.eoss.sales.contractchange.service.ContractSnapShootService;
import com.sinobridge.eoss.sales.invoice.service.SalesInvoiceService;
import com.sinobridge.eoss.sales.stockupCost.model.StockUpContractCostModel;
import com.sinobridge.eoss.sales.stockupCost.model.StockUpContractCostProductModel;
import com.sinobridge.eoss.sales.stockupCost.service.StockUpContractCostProductService;
import com.sinobridge.eoss.sales.stockupCost.service.StockUpContractCostService;
import com.sinobridge.eoss.salesfundsCost.model.FundsSalesContract;
import com.sinobridge.eoss.salesfundsCost.model.FundsSalesLog;
import com.sinobridge.eoss.salesfundsCost.service.FundsSalesContractService;
import com.sinobridge.eoss.salesfundsCost.service.FundsSalesLogService;
import com.sinobridge.systemmanage.util.Global;
import com.sinobridge.systemmanage.util.StringUtil;
import com.sinobridge.systemmanage.vo.SystemUser;

/**
 * <p>
 * Description: StockUpContractCostController
 * </p>
 *
 * @author liyx
 * @version 1.0

 * <p>
 * History: 
 *
 * Date                     Author         Version     Description
 * ---------------------------------------------------------------------------------
 * 2015年10月9日 下午4:37:26      liyx           1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Controller
@RequestMapping(value = "/stockup/contractCost")
public class StockUpContractCostController extends DefaultBaseController<StockUpContractCostModel, StockUpContractCostService> {
    @Autowired
    private SalesContractService salesContractService;
    @Autowired
    private ProcessService processService;
    @Autowired
    private SalesInvoiceService salesInvoiceService;
    @Autowired
    private ContractSnapShootService contractSnapShootService;
    @Autowired
    SalesCachetService cachetService;
    @Autowired
    StockUpContractCostProductService stockUpContractCostProductService;
    @Autowired
    private FundsSalesLogService fundsSalesLogService;
    @Autowired
    private FundsSalesContractService fundsSalesContractService;

    //private final static Ehcache systemUserCache = Constants.CACHE_MANAGER.getEhcache("systemUser");

    /**
     * 待确认的合同列表页面
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/manage")
    public ModelAndView manage(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("sales/stockUpCost/manage");
        return mav;
    }

    /**
     * 查询页面
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/search")
    public ModelAndView search(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("sales/stockUpCost/search");
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
        String doState = request.getParameter("doState");
        if (iDisplayStart != null) {
            this.pageNo = Integer.parseInt(iDisplayStart) - 1;
        }
        if (iDisplayLength != null) {
            this.pageSize = Integer.parseInt(iDisplayLength);
        }
        //HashMap<String, Object> searchMap = new HashMap<String, Object>();
        //buildSearch4HashMap(request, searchMap);
        //当前登录用户
        SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);

        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(StockUpContractCostModel.class);
        //采购员确认自己的合同
        detachedCriteria.add(Restrictions.eq("orderProcessor", systemUser.getUserName()));

        if (!StringUtil.isEmpty(doState)) {
            Object[] doStateStr = doState.split(",");
            System.out.println(doStateStr);
            detachedCriteria.add(Restrictions.in("doState", doStateStr));
        } else {
            detachedCriteria.add(Restrictions.eq("doState", "0"));
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

        detachedCriteria.addOrder(org.hibernate.criterion.Order.asc("doState"));

        PaginationSupport paginationSupport = getService().findPageByCriteria(detachedCriteria, this.pageNo * this.pageSize, this.pageSize);
        map.put("rows", paginationSupport.getItems());
        map.put("total", paginationSupport.getTotalCount());
        map.put("page", iDisplayStart);
        return map;
    }

    /**
     * <code>toDetail</code>
     * 成本合同详情页面
     * @param request
     * @param response
     * @return
     * @since 2014年5月19日     3unshine
     */
    @RequestMapping(value = "/toDetail")
    public ModelAndView toDetail(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("sales/stockUpCost/detail");
        String id = request.getParameter("id") != null ? request.getParameter("id") : request.getAttribute("contractId") + "";

        //取出合同实体发送到前台页面
        if (id != null && !"".equals(id)) {
            StockUpContractCostModel contractCostModel = getService().get(Long.parseLong(id));
            mav.addObject("model", contractCostModel);

            //            if (!StringUtil.isEmpty(contractCostModel.getRelateDeliveryContractId())) {
            //                List<Map<String, Object>> productList = stockUpContractCostProductService.getProductByBhSaleContractId(contractCostModel.getRelateDeliveryContractId());
            //
            //                List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
            //                if (productList.size() > 0) {
            //                    for (int i = 0; i < productList.size(); i++) {
            //                        Map<String, Object> map = new HashMap<String, Object>();
            //                        map.put("id", productList.get(i).get("id"));
            //                        map.put("quantity", productList.get(i).get("quantity"));
            //                        map.put("unitPrice", productList.get(i).get("unitPrice"));
            //                        map.put("totalPrice", productList.get(i).get("totalPrice"));
            //                        map.put("productName", productList.get(i).get("productName"));
            //                        map.put("productPartnerName", productList.get(i).get("productPartnerName"));
            //                        map.put("productTypeName", productList.get(i).get("productTypeName"));
            //                        map.put("serviceStartDate", productList.get(i).get("serviceStartDate"));
            //                        map.put("serviceEndDate", productList.get(i).get("serviceEndDate"));
            //                        listMap.add(map);
            //                    }
            //                    mav.addObject("productList", listMap);
            //                    mav.addObject("listNum", productList.size());
            //                }
            //            }
        }

        return mav;
    }

    /**
     * 异步获得待确认的产品数据
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/getProductData")
    @ResponseBody
    public List<Map<String, Object>> getProductData(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        //当前登录用户
        SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
        List<Map<String, Object>> productList = stockUpContractCostProductService.getProducts(id, systemUser.getStaffName());
        return productList;
    }

    /**
     * 查询已确认的产品详情 
     * @param request
     * @param response
     * @return
     */
    //    @RequestMapping(value = "/getProductsDetail")
    //    @ResponseBody
    //    public List<Map<String, Object>> getProductsDetail(HttpServletRequest request, HttpServletResponse response) {
    //        String id = request.getParameter("id");
    //        /*根据合同ID查找相关的产品信息*/
    //        List<Map<String, Object>> productList = stockUpContractCostProductService.getProductsDetail(id);
    //        return productList;
    //    }

    /**
     * <code>detail</code>
     * 查看合同详情,旧版本已不再用
     * @param request
     * @param response
     * @return ModelAndView
     * @since 2014年5月26日    3unshine
     */
    /*@RequestMapping(value = "/toDetail2")
    public ModelAndView toDetail2(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("sales/stockUpCost/detail");
        String id = request.getParameter("id") != null ? request.getParameter("id") : request.getAttribute("contractId") + "";
        String isFlow = request.getAttribute("isFlow") + "";
        String taskId = request.getAttribute("taskId") + "";
        String flowStep = request.getAttribute("flowStep") + "";
        String procInstId = request.getAttribute("procInstId") + "";
        String orderId = request.getParameter("orderId");
        String orderProcessor = request.getParameter("orderProcessor");
        String bhSaleContractId = request.getParameter("bhSaleContractId");

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
                salesContractModel = salesContractService.get(Long.parseLong(id));
                Set<SalesContractProductModel> salesContractProducts = salesContractModel.getSalesContractProductModel();
                Iterator<SalesContractProductModel> t1 = salesContractProducts.iterator();
                String productIds = "";
                int count = 0;
                while (t1.hasNext()) {
                    SalesContractProductModel p = t1.next();
                    productIds += p.getRelateDeliveryProductId();
                    count++;
                    if (count < salesContractProducts.size()) {
                        productIds += ",";
                    }
                }

                //得到采购过的备货设备
                if (!StringUtil.isEmpty(productIds)) {
                    List<Map<String, Object>> productList = getService().getProductByIds(productIds);

                    List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
                    if (productList.size() > 0) {
                        for (int i = 0; i < productList.size(); i++) {
                            Map<String, Object> map = new HashMap<String, Object>();
                            map.put("id", productList.get(i).get("id"));
                            map.put("quantity", productList.get(i).get("quantity"));
                            map.put("unitPrice", productList.get(i).get("unitPrice"));
                            map.put("totalPrice", productList.get(i).get("totalPrice"));
                            map.put("productName", productList.get(i).get("productName"));
                            map.put("productPartnerName", productList.get(i).get("productPartnerName"));
                            map.put("productTypeName", productList.get(i).get("productTypeName"));
                            map.put("serviceStartDate", productList.get(i).get("serviceStartDate"));
                            map.put("serviceEndDate", productList.get(i).get("serviceEndDate"));
                            listMap.add(map);
                        }
                        mav.addObject("productList", listMap);
                    }
                }

                mav.addObject("model", salesContractModel);

                //审批日志
                List<ProcInstAppr> proInstLogList = processService.findProcInstApprLog(salesContractModel.getProcessInstanceId());
                mav.addObject("proInstLogList", proInstLogList);

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
                request.setAttribute("isFlow", isFlow);
                request.setAttribute("orderId", orderId);
                request.setAttribute("orderProcessor", orderProcessor);
                request.setAttribute("bhSaleContractId", bhSaleContractId);

            }
            List<Map<String, Object>> salesCode = new ArrayList<Map<String, Object>>();
            salesCode = getService().getProSalesCode(salesContractModel.getId());
            mav.addObject("salesCode", salesCode);
            return mav;
        }
    }*/
    /**
     * 保存确认合同成本实体
     * @param request
     * @param response
     * @param salesContractModel
     * @param errors
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/doSave")
    public String doSave(HttpServletRequest request, HttpServletResponse response) {
        String contractCostId = request.getParameter("contractCostId");
        String[] ids = request.getParameterValues("ids");
        String[] unitPrices = request.getParameterValues("unitPrices");
        String[] quantitys = request.getParameterValues("quantitys");
        String[] totalPrices = request.getParameterValues("totalPrices");
        String[] productTypeNames = request.getParameterValues("productTypeNames");
        String[] productPartnerNames = request.getParameterValues("productPartnerNames");
        String[] productNames = request.getParameterValues("productNames");
        String[] bhSaleContractIds = request.getParameterValues("bhSaleContractIds");
        String[] bhProductIds = request.getParameterValues("bhProductIds");
        String[] orderIds = request.getParameterValues("orderIds");
        String productTotal = request.getParameter("productTotal");

        //此处的事务处理有问题
        try {
            StockUpContractCostModel stockUpContractCostModel = getService().get(Long.parseLong(contractCostId));
            stockUpContractCostModel.setDoState("1"); //确认状态，0:未确认,1:已确认
            stockUpContractCostModel.setDoDate(new Date()); //确认时间
            stockUpContractCostModel.setCostNum(new BigDecimal(productTotal));
            getService().update(stockUpContractCostModel);

            List<StockUpContractCostProductModel> lists = new ArrayList<StockUpContractCostProductModel>();
            for (int i = 0; i < ids.length; i++) {
                StockUpContractCostProductModel costProduct = new StockUpContractCostProductModel();
                costProduct.setUnitPrice(new BigDecimal(unitPrices[i]));
                costProduct.setQuantity(Integer.parseInt(quantitys[i]));
                costProduct.setTotalPrice(new BigDecimal(totalPrices[i]));
                costProduct.setProductTypeName(productTypeNames[i]);
                costProduct.setProductPartnerName(productPartnerNames[i]);
                costProduct.setProductName(productNames[i]);
                costProduct.setBhSaleContractId(bhSaleContractIds[i]);
                costProduct.setOrderId(orderIds[i]);
                costProduct.setRelateDeliveryProductId(Long.parseLong(bhProductIds[i]));
                costProduct.setStockUpContractCostModel(stockUpContractCostModel);
                costProduct.setCpSaleContractId(stockUpContractCostModel.getSalesContractId());

                lists.add(costProduct);
                SalesContractModel salesContractModel = salesContractService.get(Long.parseLong(costProduct.getBhSaleContractId()));
                //更新备货合同的商务成本，直到为0 
                FundsSalesContract fundsSalesContract = fundsSalesContractService.getBycontractCode(salesContractModel.getContractCode());
                if (fundsSalesContract != null) {
                    BigDecimal tmp = fundsSalesContract.getBusinessCost();
                    if (tmp == null) {
                        tmp = new BigDecimal(0.0);
                    }
                    tmp = tmp.subtract(costProduct.getTotalPrice());
                    fundsSalesContract.setBusinessCost(tmp);
                    fundsSalesContractService.update(fundsSalesContract); //更新商务成本
                    //记录商务成本产生日志
                    FundsSalesLog fundsSalesLog = new FundsSalesLog();
                    fundsSalesLog.setOpAmount(costProduct.getTotalPrice());
                    fundsSalesLog.setOpDate(new Date());
                    fundsSalesLog.setContractCode(fundsSalesContract.getContractCode());
                    fundsSalesLog.setOpDesc("关联备货商务成本，被[" + stockUpContractCostModel.getContractCode() + "]关联掉");
                    fundsSalesLogService.saveOrUpdate(fundsSalesLog);
                }
                //stockUpContractCostProductService.update(costProduct);
            }
            stockUpContractCostProductService.saveOrUpdateAll(null, lists);

            //更新产品合同商务成本
            FundsSalesContract fundsSalesContract = fundsSalesContractService.getBycontractCode(stockUpContractCostModel.getContractCode());
            if (fundsSalesContract != null) {
                BigDecimal tmp = fundsSalesContract.getBusinessCost();
                if (tmp == null) {
                    tmp = new BigDecimal(0.0);
                }
                tmp = tmp.add(stockUpContractCostModel.getCostNum());
                fundsSalesContract.setBusinessCost(tmp);
                fundsSalesContractService.update(fundsSalesContract); //更新商务成本
                //记录商务成本产生日志
                FundsSalesLog fundsSalesLog = new FundsSalesLog();
                fundsSalesLog.setOpAmount(tmp);
                fundsSalesLog.setOpDate(new Date());
                fundsSalesLog.setContractCode(fundsSalesContract.getContractCode());
                fundsSalesLog.setOpDesc("备货转销售商务成本");
                fundsSalesLogService.saveOrUpdate(fundsSalesLog);
            }
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return FAIL;
        }
    }

    /**
     * 删除
     * @param request
     * @param response
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/delContract")
    public String delContract(HttpServletRequest request, HttpServletResponse response, @RequestParam("id") String ids) {
        try {
            String[] id = ids.substring(0, ids.lastIndexOf(",")).split(",");
            getService().deletes(id);
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return FAIL;
        }
    }

}
