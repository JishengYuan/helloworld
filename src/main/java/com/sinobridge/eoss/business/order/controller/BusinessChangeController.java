/*
 * FileName: BusinessChangeController.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.business.order.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sinobridge.base.core.controller.DefaultBaseController;
import com.sinobridge.base.core.utils.PaginationSupport;
import com.sinobridge.eoss.business.order.constant.BusinessOrderContant;
import com.sinobridge.eoss.business.order.model.BusinessChangeModel;
import com.sinobridge.eoss.business.order.model.BusinessOrderModel;
import com.sinobridge.eoss.business.order.service.BusinessChangeService;

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
 * 2014年12月24日 下午4:50:22          vermouth        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Controller
@RequestMapping(value = "business/changeOrder")
public class BusinessChangeController extends DefaultBaseController<BusinessChangeModel, BusinessChangeService> {
    /**
     * <code>manage</code>
     * 订单变更页面
     * @param request
     * @param response
     * @return
     * @since   2014年5月5日    wangya
     */
    @RequestMapping(value = "/manage")
    public ModelAndView manage(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("business/changeOrder/manage");

        return mav;
    }

    /**
     * <code>getTableGrid</code>
     * 得到表格列表
     * @param request
     * @param response
     * @return
     * @since   2014年12月24日    wangya
     */
    @ResponseBody
    @RequestMapping(value = "/getTableGrid")
    public Map<String, Object> getTableGrid(HttpServletRequest request, HttpServletResponse response) {
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
        detachedCriteria.add(Restrictions.eq("status", BusinessOrderContant.CHANGE_SP));
        PaginationSupport paginationSupport = getService().findPageByCriteria(detachedCriteria, this.pageNo * this.pageSize, this.pageSize);
        map.put("rows", paginationSupport.getItems());
        map.put("total", paginationSupport.getTotalCount());
        map.put("page", pageNo);
        return map;
    }

    /**
     * <code>settlement</code>
     * 变更审批页面
     * @param request
     * @param response
     * @return
     * @since   2014年7月25日    wangya
     */
    @ResponseBody
    @RequestMapping(value = "/settlement")
    public ModelAndView settlement(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("business/changeOrder/settlement");
        String id = request.getParameter("id");
        BusinessChangeModel change = new BusinessChangeModel();
        //取出合同实体发送到前台页面
        if (id != null && !"".equals(id)) {
            change = getService().get(Long.parseLong(id));
            BusinessOrderModel order = getService().findOrder(change.getOrderId());
            mav.addObject("model", change);
            mav.addObject("order", order);
        }
        return mav;
    }

    /**订单变更
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/close")
    public String close(HttpServletRequest request, HttpServletResponse response) {
        try {
            int isAgree = Integer.parseInt(request.getParameter("isAgree"));
            Long id = Long.parseLong(request.getParameter("id"));
            getService().close(isAgree, id);
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return FAIL;
        }

    }
}
