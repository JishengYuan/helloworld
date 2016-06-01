/*
 * FileName: BusinessInboundBillController.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.business.stock.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sinobridge.base.core.controller.DefaultBaseController;
import com.sinobridge.base.core.utils.PaginationSupport;
import com.sinobridge.eoss.business.stock.model.BusinessInboundBill;
import com.sinobridge.eoss.business.stock.model.InboundModel;
import com.sinobridge.eoss.business.stock.service.BusinessInboundBillService;

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
 * 2014年10月10日 上午8:51:50          guo_kemeng        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Controller
@RequestMapping(value = "/inboundBill")
public class BusinessInboundBillController extends DefaultBaseController<BusinessInboundBill,BusinessInboundBillService>{

    
    /**
     * 初始化excel表格导入页面
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/inbound")
    public ModelAndView excel(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("business/stock/excel");
        return mav;
    }
    
    /**
     * <code>getExcelInfo</code>
     *
     * @param request
     * @param response
     * @return
     * @since   2014年10月10日    guokemenng
     */
    @ResponseBody
    @RequestMapping(value = "/getInboundInfo")
    public Map<String, Object> getExcelInfo(HttpServletRequest request, HttpServletResponse response) {
        String params = request.getParameter("sEcho");
        String iDisplayStart = request.getParameter("iDisplayStart");
        String iDisplayLength = request.getParameter("iDisplayLength");
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(InboundModel.class);
        
        detachedCriteria.add(Restrictions.eq(InboundModel.STATE, (short)0));
        detachedCriteria.addOrder(Order.desc(InboundModel.ID));
        
        PaginationSupport paginationSupport = this.getService().findPageByCriteria(detachedCriteria, Integer.parseInt(iDisplayStart), Integer.parseInt(iDisplayLength));
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("aaData", paginationSupport.getItems());
        map.put("sEcho", params);
        map.put("iTotalDisplayRecords", paginationSupport.getTotalCount());
        map.put("iTotalRecords", paginationSupport.getTotalCount());
        return map;
    }
    
    /**
     * <code>uploadExcel</code>
     *
     * @param request
     * @param response
     * @return
     * @since   2014年10月10日    guokemenng
     */
    @ResponseBody
    @RequestMapping(value = "/uploadExcel")
    public String uploadExcel(HttpServletRequest request,HttpServletResponse response){
        try {
            if(getService().uploadFile(request)){
                return SUCCESS;
            } else {
                return FAIL;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return FAIL;
        }
    }
    
}
