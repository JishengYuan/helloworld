/*
 * FileName: BaseProductTypeController.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.business.projectmanage.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sinobridge.base.core.controller.DefaultBaseController;
import com.sinobridge.eoss.business.projectmanage.dto.Tree;
import com.sinobridge.eoss.business.projectmanage.dto.Treegrid;
import com.sinobridge.eoss.business.projectmanage.model.BaseProductType;
import com.sinobridge.eoss.business.projectmanage.service.BaseProductTypeService;
import com.sinobridge.systemmanage.model.SysPowerAction;
import com.sinobridge.systemmanage.util.Global;
import com.sinobridge.systemmanage.vo.SystemUser;

/**
 * <code>BaseProductTypeController</code>
 * 
 * @version  1.0
 * @author  guokemenng
 * @since 1.0  2013-11-26
 */
@Controller
@RequestMapping(value = "/business/projectm/productType")
public class BaseProductTypeController extends DefaultBaseController<BaseProductType, BaseProductTypeService> {

    /*
     * 设置页面初始化
     */
    @RequestMapping(value = "/productType_main")
    public ModelAndView getProductTypeMain(HttpServletRequest request, HttpServletResponse response) {

        ModelAndView mav = new ModelAndView("business/projectm/productType_main");
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
     * <code>getMenus</code>
     * 得到tree的json数据
     * @param request
     * @param response
     * @return
     * @since   2013-11-8    guokemenng
     */
    @ResponseBody
    @RequestMapping(value = "/getTreegrid")
    public List<Treegrid> getTreegrid(HttpServletRequest request, HttpServletResponse response) {
        List<Treegrid> treegridList = getService().getTreegrid();
        return treegridList;
    }

    /**
     * <code>add</code>
     *
     * @param request
     * @param response
     * @return
     * @since   2013-11-11    guokemenng
     */
    @SuppressWarnings("unchecked")
    @ResponseBody
    @RequestMapping(value = "/add")
    public String add(HttpServletRequest request, HttpServletResponse response) {
        try {

            Map<String, String[]> parameterMap = request.getParameterMap();
            //            for (Entry<String, String[]> e : parameterMap.entrySet()) {
            //                String paraKey = e.getKey();
            //                String[] paraValue = e.getValue();
            //                this.setElement(this.model, paraKey, paraValue[0]);
            //                this.model.setOrderValue(new Date().getTime());
            //            }

            this.model.setId(new Date().getTime() + "");
            //            this.model.setOrderValue(new Date().getTime());
            this.model.setIcon(parameterMap.get("icon")[0]);
            String isLogic = parameterMap.get("isLogic")[0];
            if (isLogic != null && isLogic != "") {
                this.model.setIsLogic(Short.parseShort(isLogic));
            }
            String isModule = parameterMap.get("isModule")[0];
            if (isModule != null && isModule != "") {
                this.model.setIsModule(Short.parseShort(isModule));
            }
            String parentId = parameterMap.get("parentId")[0];
            this.model.setParentId(parentId == "" ? null : parentId);
            this.model.setTypeName(parameterMap.get("typeName")[0]);

            String orderValue = parameterMap.get("orderValue")[0];
            if (!StringUtils.isEmpty(orderValue)) {
                this.model.setOrderValue(Integer.parseInt(orderValue));
            }
            this.model.setBgImage(parameterMap.get("bgImage")[0]);
            this.model.setTypeCode(parameterMap.get("typeCode")[0]);

            getService().create(this.model);
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return FAIL;
        }
    }

    /**
     * <code>edit</code>
     *
     * @param request
     * @param response
     * @return
     * @since   2013-11-12    guokemenng
     */
    @SuppressWarnings("unchecked")
    @ResponseBody
    @RequestMapping(value = "/edit")
    public String edit(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, String[]> parameterMap = request.getParameterMap();

            this.model = getService().get(parameterMap.get("id")[0]);

            this.model.setIcon(parameterMap.get("icon")[0]);
            String isLogic = parameterMap.get("isLogic")[0];
            if (isLogic != null && isLogic != "") {
                this.model.setIsLogic(Short.parseShort(isLogic));
            }
            String isModule = parameterMap.get("isModule")[0];
            if (isModule != null && isModule != "") {
                this.model.setIsModule(Short.parseShort(isModule));
            }
            this.model.setTypeName(parameterMap.get("typeName")[0]);
            String orderValue = parameterMap.get("orderValue")[0];
            if (!StringUtils.isEmpty(orderValue)) {
                this.model.setOrderValue(Integer.parseInt(orderValue));
            }
            this.model.setBgImage(parameterMap.get("bgImage")[0]);
            this.model.setTypeCode(parameterMap.get("typeCode")[0]);
            getService().update(this.model);

            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return FAIL;
        }
    }

    /**
     * <code>remove</code>
     *
     * @param request
     * @param response
     * @return
     * @since   2013-11-11    guokemenng
     */
    @ResponseBody
    @RequestMapping(value = "/del")
    public String remove(HttpServletRequest request, HttpServletResponse response) {
        try {
            String id = request.getParameter("devicetypeId");
            //            ConfDevicetypeinfo deviceType = getService().get(id);
            getService().del(id);
            //            getService().del(this.model);

            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return FAIL;
        }
    }

    /**
     * <code>getTree</code>
     *
     * @param request
     * @param response
     * @return
     * @since   2013-11-19    guokemenng
     */
    @ResponseBody
    @RequestMapping(value = "/getTree")
    public List<Tree> getTree(HttpServletRequest request, HttpServletResponse response) {
        String param = request.getParameter("text");
        String treeId = request.getParameter("id");
        List<Tree> treeList = getService().getTree(param, treeId);
        return treeList;
    }

    /**
     * 根据厂商选择类型
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getTreeBypartner")
    public List<Tree> getTreeBypartner(HttpServletRequest request, HttpServletResponse response) {
        String partnerId = request.getParameter("partnerId");

        List<Tree> treeList = getService().getTreeBypartner(partnerId);
        return treeList;
    }

    @ResponseBody
    @RequestMapping(value = "/getTreeResource")
    public List<Tree> getTreeResource(HttpServletRequest request, HttpServletResponse response) {
        String param = request.getParameter("text");
        String resourceId = request.getParameter("resourceId");
        List<Tree> treeList = getService().getTreeResource(param, resourceId);
        return treeList;
    }

}
