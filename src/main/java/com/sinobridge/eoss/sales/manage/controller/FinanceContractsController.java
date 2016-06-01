package com.sinobridge.eoss.sales.manage.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sinobridge.base.core.controller.DefaultBaseController;
import com.sinobridge.base.core.utils.PaginationSupport;
import com.sinobridge.eoss.business.order.model.BusinessOrderModel;
import com.sinobridge.eoss.business.order.service.BusinessOrderService;
import com.sinobridge.eoss.sales.contract.model.SalesContractModel;
import com.sinobridge.eoss.sales.contract.service.SalesContractService;
import com.sinobridge.eoss.sales.contract.utils.DateUtils;
import com.sinobridge.eoss.sales.manage.model.FinanceContractsModel;
import com.sinobridge.eoss.sales.manage.service.FinanceContractsService;
import com.sinobridge.systemmanage.util.StringUtil;

/**
 * <p>
 * Description: 
 * </p>
 *
 * @author guo_kemeng
 * @version 1.0

 * <p>
 * History: 
 *
 * Date                     Author         Version     Description
 * ---------------------------------------------------------------------------------
 * 2015年1月16日 上午10:39:29          guo_kemeng        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Controller
@RequestMapping(value = "/sales/manage/finance")
public class FinanceContractsController extends DefaultBaseController<FinanceContractsModel, FinanceContractsService> {

    @Autowired
    SalesContractService salesContractService;
    String DatePattern = "yyyy-MM-dd";
    @Autowired
    BusinessOrderService businessOrderService;

    /**
     * <code>manage</code>
     * 主页面
     * @param request
     * @param response
     * @return
     * @since   2015年1月16日    guokemenng
     */
    @RequestMapping(value = "/manage")
    public ModelAndView manage(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("sales/manage/finance/manage");
        return mav;
    }

    /**
     * <code>search</code>
     * 搜索页面
     * @param request
     * @param response
     * @return
     * @since   2015年1月16日    guokemenng
     */
    @RequestMapping(value = "/search")
    public ModelAndView search(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("sales/manage/finance/search");
        Date sTime = DateUtils.getLastMonthDay();
        Date eTime = DateUtils.getLastMonthDayEnd();
        try {
            request.setAttribute("startTime", DateUtils.convertDateToString(sTime, DatePattern));
            request.setAttribute("endTime", DateUtils.convertDateToString(eTime, DatePattern));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return mav;
    }

    /**
     * <code>getList</code>
     * 获取列表
     * @param request
     * @param response
     * @return
     * @since   2015年1月16日    guokemenng
     */
    @ResponseBody
    @RequestMapping(value = "/getList")
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
        //DetachedCriteria detachedCriteria = DetachedCriteria.forClass(BusinessOrderModel.class);
        //detachedCriteria.addOrder(Order.desc("id"));
        Map<String, Object> searchMap = new HashMap<String, Object>();
        buildSearch(request, searchMap);
        PaginationSupport paginationSupport = getService().findOrderContract(searchMap, this.pageNo * this.pageSize, this.pageSize);
        map.put("rows", paginationSupport.getItems());
        map.put("total", paginationSupport.getTotalCount());
        map.put("page", pageNo);
        return map;
    }

    /**
    * <code>buildSearch</code>
    * 查询条件
    * @param request
    * @param searchMap
    * @since   2015年1月16日    guokemenng
    */
    public void buildSearch(HttpServletRequest request, Map<String, Object> searchMap) {
        String contractCode = request.getParameter("contractCode");
        String contractAmount = request.getParameter("contractAmount");
        String supplierInfo = request.getParameter("supplierInfo");
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        String orderStatus = request.getParameter("orderId");
        Date sTime = DateUtils.getLastMonthDay();
        Date eTime = DateUtils.getLastMonthDayEnd();
        String st = "";
        String et = "";
        try {
            st = DateUtils.convertDateToString(sTime, DatePattern);
            et = DateUtils.convertDateToString(eTime, DatePattern);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (!StringUtil.isEmpty(contractCode)) {
            searchMap.put(BusinessOrderModel.ORDERCODE, '%' + contractCode + '%');
        }
        if (!StringUtil.isEmpty(contractAmount)) {
            searchMap.put(BusinessOrderModel.ORDERAMOUNT, contractAmount);
        }
        if (!StringUtil.isEmpty(supplierInfo)) {
            searchMap.put("supplierInfo", supplierInfo);
        }
        if (!StringUtil.isEmpty(startTime)) {
            searchMap.put("startTime", startTime);
        } else {
            searchMap.put("startTime", st);
        }
        if (!StringUtil.isEmpty(endTime)) {
            searchMap.put("endTime", endTime);
        } else {
            searchMap.put("endTime", et);
        }
        if (!StringUtil.isEmpty(orderStatus) && !orderStatus.equals(",")) {

            if (orderStatus.equals("1,")) {
                searchMap.put("orderStatus", "unfinish");
            } else if (orderStatus.equals("2,")) {
                searchMap.put("orderStatus", "finish");
            }
        } else {
            searchMap.put("orderStatus", "unfinish");
        }
    }

    /**
     * <code>getSalesContractListPage</code>
     * 选择合同页面
     * @param request
     * @param response
     * @return
     * @since   2014年10月29日    guokemenng
     */
    @RequestMapping(value = "/getSalesContractListPage")
    public ModelAndView getSalesContractListPage(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("sales/manage/finance/getSalesContractListPage");

        String id = request.getParameter("id");
        if (!StringUtil.isEmpty(id)) {
            BusinessOrderModel order = businessOrderService.get(Long.parseLong(id));
            List<FinanceContractsModel> list = getService().findfinanceModel(Long.parseLong(id));
            if (list.size() > 0) {
                FinanceContractsModel fina = list.get(0);
                mav.addObject("model", fina);
            }
            mav.addObject("order", order);
        }

        return mav;
    }

    /**
     * <code>getSalesContractList</code>
     * 合同页面列表
     * @param request
     * @param response
     * @return
     * @since   2014年10月29日    guokemenng
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

        PaginationSupport paginationSupport = getService().getSalesContractList(Integer.parseInt(iDisplayStart), this.pageSize);
        Map<String, Object> pg = new HashMap<String, Object>();
        pg.put("aaData", paginationSupport.getItems());
        pg.put("sEcho", params);
        pg.put("iTotalDisplayRecords", paginationSupport.getTotalCount());
        pg.put("iTotalRecords", paginationSupport.getTotalCount());
        return pg;
    }

    /**
     * <code>getSalesContractByName</code>
     * 合同插件数据
     * @param request
     * @param response
     * @return
     * @since   2014年12月25日    guokemenng
     */
    @ResponseBody
    @RequestMapping(value = "/getSalesContract")
    public Map<String, Object> getSalesContractByName(HttpServletRequest request, HttpServletResponse response) {

        String name = request.getParameter("name");
        Map<String, Object> map = new HashMap<String, Object>();
        String pageNo = request.getParameter("pageNo");
        String pageSize = request.getParameter("pageSize");
        if (pageNo != null) {
            this.pageNo = Integer.parseInt(pageNo);
        }
        if (pageSize != null) {
            this.pageSize = Integer.parseInt(pageSize);
        }
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(SalesContractModel.class);

        detachedCriteria.add(Restrictions.eq(SalesContractModel.CONTRACTSTATE, "TGSH"));
        detachedCriteria.add(Restrictions.like(SalesContractModel.CONTRACTNAME, "%" + name + "%"));

        detachedCriteria.addOrder(Order.desc("id"));
        PaginationSupport paginationSupport = getService().findPageByCriteria(detachedCriteria, this.pageNo * this.pageSize, this.pageSize);

        @SuppressWarnings("unchecked")
        List<SalesContractModel> salesList = (List<SalesContractModel>) paginationSupport.getItems();
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        for (SalesContractModel s : salesList) {
            Map<String, Object> m = new HashMap<String, Object>();
            m.put("id", s.getId());
            m.put("code", s.getContractCode());
            m.put("name", s.getContractName());
            mapList.add(m);
        }

        map.put("datas", mapList);
        map.put("pageNo", this.pageNo);
        map.put("pageSize", this.pageSize);
        map.put("totalCount", paginationSupport.getTotalCount());
        return map;
    }

    /**
     * <code>getSalesContractByCode</code>
     * 根据合同Code得到合同
     * @param request
     * @param response
     * @return
     * @since   2015年1月16日    guokemenng
     */
    @ResponseBody
    @RequestMapping(value = "/getSalesContractByCode")
    public Map<String, Object> getSalesContractByCode(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        return getService().getSalesContractByCode(id);
    }

    /**
     * <code>getSalesContractById</code>
     * 根据ID查询合同
     * @param request
     * @param response
     * @return
     * @since   2015年1月16日    guokemenng
     */
    @ResponseBody
    @RequestMapping(value = "/getSalesContractById")
    public SalesContractModel getSalesContractById(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        if (!StringUtil.isEmpty(id)) {
            return salesContractService.get(Long.parseLong(id));
        } else {
            return null;
        }
    }

    /**
     * <code>saveOrUpdate</code>
     * 增加修改方法
     * @param request
     * @param response
     * @param mod
     * @return
     * @since   2015年1月16日    guokemenng
     */
    @ResponseBody
    @RequestMapping(value = "/saveOrUpdate")
    public String saveOrUpdate(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("_eoss_sales_finance_contracts") FinanceContractsModel mod) {
        try {
            String orderId = request.getParameter("orderid");
            BusinessOrderModel order = businessOrderService.get(Long.parseLong(orderId));
            mod.setContractCode(order.getOrderCode());
            mod.setOrderId(Long.parseLong(orderId));
            mod.setContractAmount(order.getOrderAmount());
            getService().saveOrUpdate(mod);
            return SUCCESS;
        } catch (Exception e) {
            return FAIL;
        }
    }

    /**
     * <code>remove</code>
     * 删除
     * @param request
     * @param response
     * @param ids
     * @return
     * @since   2015年1月16日    guokemenng
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

}
