/*
 * FileName: SalesCostController.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.sales.finalCost.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.ehcache.Ehcache;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sinobridge.base.core.controller.DefaultBaseController;
import com.sinobridge.base.core.utils.PaginationSupport;
import com.sinobridge.eoss.sales.contract.utils.DateUtils;
import com.sinobridge.eoss.sales.finalCost.model.SalesCostModel;
import com.sinobridge.eoss.sales.finalCost.service.SalesCostService;
import com.sinobridge.systemmanage.model.SysStaff;
import com.sinobridge.systemmanage.util.Constants;
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
 * 2015年9月7日 上午9:45:35          vermouth        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Controller
@RequestMapping(value = "/sales/cost")
public class SalesCostController extends DefaultBaseController<SalesCostModel, SalesCostService> {

    private final static Ehcache systemUserCache = Constants.CACHE_MANAGER.getEhcache("systemUser");

    /**资金成本占用
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/manage")
    public ModelAndView manage(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("sales/cost/manage");
        return mav;
    }

    /**查询
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/search")
    public ModelAndView search(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("sales/cost/search");
        Date nowTime = new Date();
        String times = "";
        try {
            //nowTime = DateUtils.getLastMonthDay();
            times = DateUtils.convertDateToString(nowTime, "yyyy-MM");
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        request.setAttribute("time", times);
        return mav;
    }

    /**查询
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/specilSearch")
    public ModelAndView specilSearch(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("sales/cost/specilSearch");
        Date nowTime = new Date();
        String times = "";
        try {
            //nowTime = DateUtils.getLastMonthDay();
            times = DateUtils.convertDateToString(nowTime, "yyyy-MM");
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        request.setAttribute("time", times);
        return mav;
    }

    /**成本列表：普通合同
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
        String sortName = request.getParameter("sortname");//排序字段
        String sortorder = request.getParameter("sortorder");
        if (iDisplayStart != null) {
            this.pageNo = Integer.parseInt(iDisplayStart) - 1;
        }
        if (iDisplayLength != null) {
            this.pageSize = Integer.parseInt(iDisplayLength);
        }
        HashMap<String, Object> searchMap = new HashMap<String, Object>();
        searchMap.put("sortName", sortName);
        searchMap.put("sortorder", sortorder);
        buildSearch(request, searchMap);
        //按创建时间排序
        //searchMap.put("orderBy", "c.createTime");
        PaginationSupport paginationSupport = getService().findPageBySearchMap(searchMap, this.pageNo * this.pageSize, this.pageSize);
        map.put("rows", paginationSupport.getItems());
        map.put("total", paginationSupport.getTotalCount());
        map.put("page", iDisplayStart);
        return map;
    }

    /**成本列表：关联备货的合同
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getSpecialList")
    public Map<String, Object> getSpecialList(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();
        String iDisplayStart = request.getParameter("pageNo");
        String iDisplayLength = request.getParameter("pageSize");
        String sortName = request.getParameter("sortname");//排序字段
        String sortorder = request.getParameter("sortorder");
        if (iDisplayStart != null) {
            this.pageNo = Integer.parseInt(iDisplayStart) - 1;
        }
        if (iDisplayLength != null) {
            this.pageSize = Integer.parseInt(iDisplayLength);
        }
        HashMap<String, Object> searchMap = new HashMap<String, Object>();
        searchMap.put("sortName", sortName);
        searchMap.put("sortorder", sortorder);
        buildSearch(request, searchMap);
        //按创建时间排序
        //searchMap.put("orderBy", "c.createTime");
        PaginationSupport paginationSupport = getService().findSpecialContract(searchMap, this.pageNo * this.pageSize, this.pageSize);
        map.put("rows", paginationSupport.getItems());
        map.put("total", paginationSupport.getTotalCount());
        map.put("page", iDisplayStart);
        return map;
    }

    public void buildSearch(HttpServletRequest request, HashMap<String, Object> searchMap) {
        String time = request.getParameter("time");
        String timeEnd = request.getParameter("timeEnd");
        String salesCode = request.getParameter("salesCode");
        String salesName = request.getParameter("salesName");
        String salesAmount = request.getParameter("salesAmount");
        String salesCreator = request.getParameter("salesCreator");

        String employeeId = null;
        if (!StringUtil.isEmpty(salesCreator)) {
            employeeId = ((SysStaff) systemUserCache.get(salesCreator).getObjectValue()).getStaffId();
        }

        if (!StringUtil.isEmpty(time)) {
            searchMap.put("time", time);
        } else {
            Date nowTime = new Date();
            String times = "";
            try {
                //nowTime = DateUtils.getLastMonthDay();
                times = DateUtils.convertDateToString(nowTime, "yyyy-MM");
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            searchMap.put("time", times);
        }
        if (!StringUtil.isEmpty(timeEnd)) {
            searchMap.put("timeEnd", timeEnd);
        }
        if (!StringUtil.isEmpty(salesCode)) {
            searchMap.put("salesCode", salesCode);
        }
        if (!StringUtil.isEmpty(salesName)) {
            searchMap.put("salesName", salesName);
        }
        if (!StringUtil.isEmpty(salesAmount)) {
            searchMap.put("salesAmount", salesAmount);
        }
        if (!StringUtil.isEmpty(salesCreator)) {
            searchMap.put("salesCreator", employeeId);
        }

    }

    /**添加一个月的占用成本
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getInsertSalesCost")
    public Map<String, Object> getInsertSalesCost(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> msg = new HashMap<String, Object>();
        String time = request.getParameter("time");
        //截取月份
        String timeSub = time.substring(0, 7);
        Date timeDate = DateUtils.convertStringToDate(time, "yyyy-MM-dd");
        // 判断是否添加过此月
        int flat = getService().findTime(timeSub);
        if (flat == 0) {
            String timeStr = "";
            String preTime = "";
            try {
                timeStr = DateUtils.convertDateToString(timeDate, "yyyyMMdd");
                //60天前的时间
                preTime = DateUtils.someDayAgo(timeDate, 60);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            msg = getService().newSaleCost(timeStr, preTime, timeDate, time);
        } else {
            msg.put("time", timeSub);
        }
        /*  Date nowTime = new Date();
          String preMonday="";
          String time="";
          String timeType="";
              try {
                  time = DateUtils.convertDateToString(nowTime, "yyyyMMdd");
                  timeType = DateUtils.convertDateToString(nowTime,"yyyy-MM-dd");
                  //60天前的时间
                  preMonday = DateUtils.someDayAgo(nowTime,60);
              } catch (ParseException e) {
                  e.printStackTrace();
              }
              msg = getService().newSaleCost(time,preMonday,nowTime);
        //        String msg = getService().getInsertCost(time,newSaleCost);
        */return msg;
    }

    /**详情
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/detail")
    public ModelAndView detail(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("sales/cost/detail");
        String time = request.getParameter("time");
        request.setAttribute("time", time);
        String salesId = request.getParameter("id");
        request.setAttribute("salesId", salesId);
        //查询合同
        List<Map<String, Object>> sales = getService().getFindSales(salesId, time);
        mav.addObject("model", sales.get(0));
        Long id = Long.parseLong(salesId);
        //相关回款信息
        List<Map<String, Object>> revice = getService().getFindRevice(id, time);
        request.setAttribute("revice", revice);
        String preMonday = "";
        try {
            Date agoTime = DateUtils.convertStringToDate(time, "yyyy-MM-dd");
            preMonday = DateUtils.someDayAgo(agoTime, 60);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //下单订单信息
        List<Map<String, Object>> orderPay = getService().findOrder(id, preMonday);
        //成本的计算截止日期
        request.setAttribute("agoTime", preMonday);
        for (Map<String, Object> m : orderPay) {
            //与订单相关的合同信息
            List<Map<String, Object>> salesCon = getService().getFindOrderSale(m.get("id"), preMonday);
            //订单金额
            BigDecimal orderAmount = new BigDecimal(m.get("OrderAmount").toString());
            //付款金额
            BigDecimal pay = new BigDecimal(m.get("pay").toString());
            for (Map<String, Object> t : salesCon) {
                if (t.get("salesProAmount") != null) {
                    BigDecimal salesProAmount = new BigDecimal(t.get("salesProAmount").toString());
                    BigDecimal amount = salesProAmount.divide(orderAmount, 8, BigDecimal.ROUND_HALF_UP);
                    BigDecimal payOrder = amount.multiply(pay);
                    //amount = amount.multiply(new BigDecimal(0.01));
                    //得到每个合同的成本
                    t.put("payOrder", payOrder.setScale(2, BigDecimal.ROUND_HALF_UP));
                } else {
                    t.put("payOrder", "0");
                }
            }
            m.put("sales", salesCon);
        }
        request.setAttribute("order", orderPay);
        return mav;
    }

    /**选择时间页面
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/selectTime")
    public ModelAndView selectTime(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("/sales/cost/selectTime");
        return mav;
    }

    /**修改页面
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/update")
    public ModelAndView update(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("/sales/cost/update");
        String time = request.getParameter("time");
        String salesId = request.getParameter("id");
        List<Map<String, Object>> sales = getService().findSalesCost(salesId, time);
        mav.addObject("model", sales.get(0));
        return mav;
    }

    /**保存修改页面
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/doUpdate")
    public String doUpdate(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        String orderpay = request.getParameter("orderPay");
        String salesReceive = request.getParameter("salesReceive");
        String cost = request.getParameter("cost");
        String rs = getService().doUpdate(id, orderpay, salesReceive, cost);
        return rs;
    }
}
