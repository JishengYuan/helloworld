/*
 * FileName: BaseProductmodelController.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.business.projectmanage.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.criterion.DetachedCriteria;
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
import com.sinobridge.base.core.hibernate.id.IdentifierGeneratorImpl;
import com.sinobridge.base.core.utils.PaginationSupport;
import com.sinobridge.eoss.business.projectmanage.model.BasePartnerInfo;
import com.sinobridge.eoss.business.projectmanage.model.BaseProductModel;
import com.sinobridge.eoss.business.projectmanage.model.BaseProductType;
import com.sinobridge.eoss.business.projectmanage.service.BasePartnerInfoService;
import com.sinobridge.eoss.business.projectmanage.service.BaseProductModelService;
import com.sinobridge.eoss.business.projectmanage.service.BaseProductTypeService;
import com.sinobridge.systemmanage.model.SysPowerAction;
import com.sinobridge.systemmanage.util.Global;
import com.sinobridge.systemmanage.util.StringUtil;
import com.sinobridge.systemmanage.vo.SystemUser;

/**
 * <code>BaseProductmodelController</code>
 * 
 * @version  1.0
 * @author  guokemenng
 * @since 1.0  2013-11-26
 */
@Controller
@RequestMapping(value = "/business/projectm/productModel")
public class BaseProductModelController extends DefaultBaseController<BaseProductModel, BaseProductModelService> {

    @Autowired
    private BaseProductTypeService productTypeService;

    @Autowired
    private BasePartnerInfoService partnerInfoService;

    /**
     * <code>partnerManager</code>
     * 首页
     * @param request
     * @param response
     * @return
     * @since   2013-11-20    guokemenng
     */
    @RequestMapping(value = "/productModel_main")
    public ModelAndView getProductModelr(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("business/projectm/productModel_main");
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
     * <code>getList</code>
     *
     * @param request
     * @param response
     * @return
     * @since   2013-12-2    guokemenng
     */
    @ResponseBody
    @RequestMapping(value = "/getList")
    public Map<String, Object> getList(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();
        String params = request.getParameter("sEcho");
        String iDisplayStart = request.getParameter("iDisplayStart");
        String iDisplayLength = request.getParameter("iDisplayLength");

        String searchParam = request.getParameter("sSearch_0");

        if (StringUtil.isEmpty(iDisplayStart)) {
            iDisplayStart = "0";
        }
        if (StringUtil.isEmpty(iDisplayLength)) {
            iDisplayLength = "10";
        }

        map.put("aaData", getService().getMapList(iDisplayStart, iDisplayLength, searchParam));
        map.put("sEcho", params);
        Integer totalCount = getService().getTotalCount(searchParam);
        map.put("iTotalDisplayRecords", totalCount);
        map.put("iTotalRecords", totalCount);
        return map;
    }

    /**
     * <code>buildSearch</code>
     *
     * @param request
     * @param detachedCriteria
     * @since   2013-12-2    guokemenng
     */
    public void buildSearch(HttpServletRequest request, DetachedCriteria detachedCriteria) {
        //String sSearch = request.getParameter("sSearch");
    }

    /**
     * <code>getProductModel_saveOrUpdate</code>
     *
     * @param request
     * @param response
     * @return
     * @since   2013-12-3    guokemenng
     */
    @RequestMapping(value = "/productModel_saveOrUpdate")
    public ModelAndView getProductModel_saveOrUpdate(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("business/projectm/productModel_saveOrUpdate");

        String id = request.getParameter("id");
        if (!StringUtil.isEmpty(id)) {
            this.model = getService().get(id);
            mav.addObject("model", this.model);
            String productTypeId = this.model.getProductType();
            if (!StringUtil.isEmpty(productTypeId)) {
                BaseProductType productType = this.productTypeService.get(productTypeId);
                mav.addObject("productType", productType);
            }
            String partnerId = this.model.getPartnerId();
            if (!StringUtil.isEmpty(partnerId)) {
                BasePartnerInfo partnerInfo = partnerInfoService.get(partnerId);
                mav.addObject("partnerInfo", partnerInfo);
            }
        }

        return mav;
    }

    /**
     * <code>getProductModel_detail</code>
     *
     * @param request
     * @param response
     * @return
     * @since   2013-12-12    guokemenng
     */
    @RequestMapping(value = "/productModel_detail")
    public ModelAndView getProductModel_detail(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("business/projectm/productModel_detail");

        String id = request.getParameter("detailId");
        if (!StringUtil.isEmpty(id)) {
            this.model = getService().get(id);
            mav.addObject("model", this.model);
            String productTypeId = this.model.getProductType();
            if (!StringUtil.isEmpty(productTypeId)) {
                BaseProductType productType = this.productTypeService.get(productTypeId);
                mav.addObject("productType", productType);
            }
            String partnerId = this.model.getPartnerId();
            if (!StringUtil.isEmpty(partnerId)) {
                BasePartnerInfo partnerInfo = partnerInfoService.get(partnerId);
                mav.addObject("partnerInfo", partnerInfo);
            }
        }

        return mav;
    }

    /* @InitBinder
     public void initBinder(WebDataBinder binder) throws Exception {
         DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
         CustomDateEditor dateEditor = new CustomDateEditor(df, true);
         //表示如果命令对象有Date类型的属性，将使用该属性编辑器进行类型转换
         binder.registerCustomEditor(Date.class, dateEditor);
         binder.registerCustomEditor(BaseProductModel.class, new PropertyEditorSupport());
     }
    */

    /**
     * <code>doSaveOrUpdate</code>
     *
     * @param request
     * @param response
     * @return
     * @since   2013-12-3    guokemenng
     */
    @ResponseBody
    @RequestMapping(value = "/saveOrUpdate")
    public String doSaveOrUpdate(HttpServletRequest request, HttpServletResponse response,BaseProductModel productModel,Errors errors) {
        
        if (errors.hasErrors()) {
            return FAIL+errors.getObjectName()+errors.getFieldError().getField();
        }
        
        String idStr = IdentifierGeneratorImpl.generatorLong().toString();
        try {
            String id = request.getParameter("id");
            if (StringUtil.isEmpty(id)) {
                productModel.setId(idStr);
            }
            logger.info("success"+idStr+"==============productModel");
            getService().saveOrUpdate(productModel);
            return productModel.getId();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("error"+idStr+"==============productModel");
            return FAIL;
        }
    }

    /**
     * <code>remove</code>
     * 删除信息
     * @param request
     * @param response
     * @param id
     * @return
     * @since   2013-11-20    guokemenng
     */
    @ResponseBody
    @RequestMapping(value = "/remove")
    public String remove(HttpServletRequest request, HttpServletResponse response, @RequestParam("id") String id) {
        try {
            getService().delete(id);
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return FAIL;
        }
    }

    /**
     * <code>getList</code>
     * 获取产品型号列表
     * @return
     * @since   2014-06-09    3unshine
     */
    @ResponseBody
    @RequestMapping(value = "/getProductModelList")
    public List<Map<String, String>> getProductModelList(HttpServletRequest request, HttpServletResponse response) {
        List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
        String partnerId = request.getParameter("partnerId");
        HashMap<String, String> seachMap = new HashMap<String, String>();
        if (partnerId != "" && partnerId != null) {
            String[] strs = partnerId.split(",");
            if (strs.length == 1) {
                seachMap.put(BaseProductModel.PARTNERID, partnerId);
            } else {
                for (int i = 0; i < strs.length; i++) {
                    if (i == 0) {
                        seachMap.put(BaseProductModel.PRODUCTTYPE, strs[i]);
                    } else {
                        seachMap.put(BaseProductModel.PARTNERID, strs[i]);
                    }
                }
            }
        }
        List<BaseProductModel> productModelList = getService().getProductModel(seachMap);
        Iterator<BaseProductModel> it = productModelList.iterator();
        while (it.hasNext()) {
            Map<String, String> map = new HashMap<String, String>();
            BaseProductModel productModel = it.next();
            map.put("id", productModel.getId());
            map.put("name", productModel.getProductModel());
            mapList.add(map);
        }
        return mapList;
    }
    //    @ResponseBody
    //    @RequestMapping(value = "/test")
    //    public String test(){
    //        for(int i = 0;i < 500;i++){
    //            BaseProductModel pm = new BaseProductModel();
    //            pm.setId(i+"");
    //            pm.setProductDesc("hello world!!我是描述"+i);
    //            pm.setProductModel(i+"1");
    //            pm.setProductModelName("name"+i);
    //            pm.setProductPdlife("110");
    //            pm.setStopDate(new Date());
    //            pm.setStopSevDate(new Date());
    //            getService().create(pm);
    //        }
    //        return SUCCESS;
    //    }

    @SuppressWarnings("unchecked")
    /**
     * <code>getCustomerInfosByName</code>
     * 根据名称查询商品
     * @param request
     * @param response
     * @return
     * @since   2015年1月7日    guokemenng
     */
    @ResponseBody
    @RequestMapping(value = "/getProductInfosByName")
    public Map<String, Object> getCustomerInfosByName(HttpServletRequest request, HttpServletResponse response) {

        String name = request.getParameter("name");
        Map<String, Object> map = new HashMap<String, Object>();
        String pageNo = request.getParameter("pageNo");
        String pageSize = request.getParameter("pageSize");
        String partnerId = request.getParameter("partnerId");
        if (pageNo != null) {
            this.pageNo = Integer.parseInt(pageNo);
        }
        if (pageSize != null) {
            this.pageSize = Integer.parseInt(pageSize);
        }
        
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(BaseProductModel.class);
        if (!StringUtil.isEmpty(partnerId)) {
            String[] strs = partnerId.split(",");
            if (strs.length == 1) {
                detachedCriteria.add(Restrictions.eq(BaseProductModel.PARTNERID, partnerId));
            } else {
                for (int i = 0; i < strs.length; i++) {
                    if (i == 0) {
                        detachedCriteria.add(Restrictions.eq(BaseProductModel.PRODUCTTYPE, strs[i]));
                    } else {
                        detachedCriteria.add(Restrictions.eq(BaseProductModel.PARTNERID, strs[i]));
                    }
                }
            }
        }
        
        if(!StringUtil.isEmpty(name)){
            detachedCriteria.add(Restrictions.like(BaseProductModel.PRODUCTMODEL, "%" + name + "%"));
        }

        PaginationSupport paginationSupport = getService().findPageByCriteria(detachedCriteria,this.pageSize ,this.pageNo * this.pageSize);
        
        List<BaseProductModel> objList = (List<BaseProductModel>) paginationSupport.getItems();
        List<Map<String,Object>> mapList = new ArrayList<Map<String,Object>>();
        for(BaseProductModel o : objList){
            Map<String,Object> m = new HashMap<String,Object>();
            m.put("id", o.getId());
            m.put("name", o.getProductModel());
            mapList.add(m);
        }
        
        map.put("datas", mapList);
        map.put("pageNo", this.pageNo);
        map.put("pageSize", this.pageSize);
        map.put("totalCount", paginationSupport.getTotalCount());
        return map;
    }
    
    
    /**
     * <code>getInfoModelById</code>
     * 根据Id 查询出商品实体
     * @param request
     * @param response
     * @return
     * @since   2015年1月7日    guokemenng
     */
    @ResponseBody
    @RequestMapping(value = "/getProductoModelById")
    public Map<String,Object> getInfoModelById(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        
        BaseProductModel p = getService().get(id);
        Map<String,Object> m = new HashMap<String,Object>();
        if(p != null){
            m.put("id", p.getId());
            m.put("name", p.getProductModel());
        } else {
            m.put("id","");
            m.put("name", "");
        }
        return m;
    }
    
    /**
     * <code>getParentModelById</code>
     * 根据产品ID 得到厂商和型号数据
     * @param request
     * @param response
     * @return
     * @since   2015年1月7日    guokemenng
     */
    @ResponseBody
    @RequestMapping(value = "/getParentModelById")
    public Map<String,Object> getParentModelById(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        Map<String,Object> m = new HashMap<String,Object>();
        m = getService().getParentModelById(id);
        return m;
    }
}
