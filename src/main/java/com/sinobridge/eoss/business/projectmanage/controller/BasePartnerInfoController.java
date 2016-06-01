/*
 * FileName: BasePartnerInfoController.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.business.projectmanage.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sinobridge.base.core.controller.DefaultBaseController;
import com.sinobridge.base.core.utils.PaginationSupport;
import com.sinobridge.eoss.business.projectmanage.dto.PartnerinfoTree;
import com.sinobridge.eoss.business.projectmanage.model.BasePartnerInfo;
import com.sinobridge.eoss.business.projectmanage.model.BasePartnerProduct;
import com.sinobridge.eoss.business.projectmanage.model.BaseProductType;
import com.sinobridge.eoss.business.projectmanage.service.BasePartnerInfoService;
import com.sinobridge.eoss.business.projectmanage.service.BasePartnerProductService;
import com.sinobridge.eoss.business.projectmanage.service.BaseProductTypeService;
import com.sinobridge.eoss.business.projectmanage.utils.PartnerUtils;
import com.sinobridge.systemmanage.controller.bean.RoleForm;
import com.sinobridge.systemmanage.model.SysDomainValue;
import com.sinobridge.systemmanage.model.SysPowerAction;
import com.sinobridge.systemmanage.util.Global;
import com.sinobridge.systemmanage.util.StringUtil;
import com.sinobridge.systemmanage.util.UUIDUtil;
import com.sinobridge.systemmanage.vo.SystemUser;

/**
 * <code>BasePartnerInfoController</code>
 * 
 * @version  1.0
 * @author  guokemenng
 * @since 1.0  2013-11-26
 */
@Controller
@RequestMapping(value = "/business/projectm/partnerInfo")
public class BasePartnerInfoController extends DefaultBaseController<BasePartnerInfo, BasePartnerInfoService> {

    @Autowired
    private BaseProductTypeService productTypeService;

    @Autowired
    protected BasePartnerProductService partnerProductService;

    /**
     * <code>partnerManager</code>
     * 主页面
     * @param request
     * @param response
     * @return
     * @since   2013-11-13    guokemenng
     */
    @RequestMapping(value = "/partnerInfo_main")
    public ModelAndView getPartnerInfo(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("business/projectm/partnerInfo_main");
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
     * 获取列表
     * @return
     * @since   2013-11-13    guokemenng
     */
    @ResponseBody
    @RequestMapping(value = "/getList")
    public Map<String, Object> getList(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();
        String page = request.getParameter("page");
        String rows = request.getParameter("rows");
        if (page != null) {
            this.pageNo = Integer.parseInt(page);
        }
        if (rows != null) {
            this.pageSize = Integer.parseInt(rows);
        }
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(this.model.getClass());
        buildSearch(request, detachedCriteria);
        PaginationSupport paginationSupport = getService().findPageByCriteria(detachedCriteria, this.pageNo, this.pageSize);

        map.put("total", paginationSupport.getTotalCount());
        map.put("rows", paginationSupport.getItems());
        return map;
    }

    /**
     * <code>getList</code>
     * 得到所有厂商格式{["id":XX,"name":XX]}
     * @return
     * @throws NoSuchMethodException 
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
     * @since   2013-11-13    guokemenng
     */
    @ResponseBody
    @RequestMapping(value = "/getAll")
    public Map<String, List<PartnerinfoTree>> getAll(HttpServletRequest request, HttpServletResponse response, @RequestParam("productTypeId") String productTypeId) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(this.model.getClass());
        Short s = 1;
        detachedCriteria.add(Restrictions.eq(BasePartnerInfo.DELETEFLAG, s));
        List<PartnerinfoTree> partnerMap = getService().findPartnerByMap(detachedCriteria, productTypeId);
        Map<String, List<PartnerinfoTree>> data = new HashMap<String, List<PartnerinfoTree>>();
        data.put("source", partnerMap);
        return data;
    }

    @ResponseBody
    @RequestMapping(value = "/getAllProperty")
    public List<Map<String, String>> getAllProperty(HttpServletRequest request, HttpServletResponse response, @RequestParam("partnerType") String partnerType) {
        partnerType = "2000";
        List<Map<String, String>> rs = getService().findPartnerByProperty(partnerType);
        return rs;
    }

    /**
     * <code>partnerSearch</code>
     *
     * @param request
     * @param response
     * @return
     * @since   2013-11-13    guokemenng
     */
    @RequestMapping(value = "/partnerInfo_Search")
    public ModelAndView partnerSearch(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("business/projectm/partnerInfo_search");

        //厂商设备类型
        List<SysDomainValue> typeList = getService().getConfPartnerType();
        mav.addObject("typeList", typeList);

        //厂商服务供应商类型
        List<SysDomainValue> serviceList = getService().getPartnerServiceType();
        mav.addObject("serviceList", serviceList);

        return mav;
    }

    /**
     * <code>add</code>
     * 返回到增加页面
     * @param request
     * @param response
     * @return
     * @since   2013-11-18    guokemenng
     */
    @RequestMapping(value = "/add")
    public ModelAndView add(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("business/projectm/partnerInfo_saveOrUpdate");
        //厂商设备类型
        List<SysDomainValue> typeList = getService().getConfPartnerType();
        mav.addObject("typeList", typeList);

        //厂商服务供应商类型
        List<SysDomainValue> serviceList = getService().getPartnerServiceType();
        mav.addObject("serviceList", serviceList);
        return mav;
    }

    @SuppressWarnings("unchecked")
    @ResponseBody
    @RequestMapping(value = "/saveOrUpdate")
    public String saveOrUpdate(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, String[]> parameterMap = request.getParameterMap();
            String id = parameterMap.get("id")[0];
            if (id == null || id.equals("")) {
                this.model.setId(new Date().getTime() + "");
            }
            this.model.setPartnerCode(parameterMap.get("partnerCode")[0]);
            this.model.setPartnerFullName(parameterMap.get("partnerFullName")[0]);
            // this.model.setPartnerEnCode(parameterMap.get("partnerEnCode")[0]);
            // this.model.setPartnerLogo(parameterMap.get("partnerLogo")[0]);
            // this.model.setRegisterAddress(parameterMap.get("registerAddress")[0]);
            this.model.setAddress(parameterMap.get("address")[0]);
            this.model.setHotLine(parameterMap.get("hotLine")[0]);
            // this.model.setPartnerOid(parameterMap.get("partnerOid")[0]);
            // this.model.setPartnerEmail(parameterMap.get("partnerEmail")[0]);
            // this.model.setWebUrl(parameterMap.get("webUrl")[0]);

            String[] partnerTypes = parameterMap.get("partnerType");
            //            if (partnerType != null && partnerType != "") {
            //                this.model.setPartnerType(Short.parseShort(partnerType));
            //            }
            String partnerType = "";
            boolean b = false;
            if (partnerTypes != null) {
                for (String type : partnerTypes) {
                    if (type.equals("3000")) {
                        b = true;
                    }
                    partnerType += type + ",";
                }
            }
            this.model.setPartnerType(partnerType);

            String productType = parameterMap.get("productType")[0];

            if (b) {
                String servicePartnerType = parameterMap.get("servicePartnerType")[0];
                if (servicePartnerType != null && servicePartnerType != "") {
                    this.model.setServicePartnerType(Short.parseShort(servicePartnerType));
                }
            } else {
                this.model.setServicePartnerType(null);
            }

            this.model.setCurrency(parameterMap.get("currency")[0]);
            getService().saveOrUpdate(this.model);

            if (StringUtil.isEmpty(id)) {
                if (!StringUtil.isEmpty(productType)) {
                    String[] productTypeIds = productType.split(",");
                    for (String typeId : productTypeIds) {
                        //厂商产品类别
                        BasePartnerProduct partnerProduct = new BasePartnerProduct();
                        partnerProduct.setId(UUIDUtil.random());
                        partnerProduct.setProductTypeId(typeId);
                        partnerProduct.setBasePartnerInfo(this.model);
                        this.partnerProductService.create(partnerProduct);
                    }
                }
            } else {
                if (!StringUtil.isEmpty(productType)) {
                    String[] productTypeIds = productType.split(",");
                    partnerProductService.delete(id); //先删除所有厂商对应类型
                    for (String typeId : productTypeIds) {
                        //依次添加厂商对应的产品类别
                        BasePartnerProduct partnerProduct = new BasePartnerProduct();
                        partnerProduct.setId(UUIDUtil.random());
                        partnerProduct.setProductTypeId(typeId);
                        partnerProduct.setBasePartnerInfo(this.model);
                        this.partnerProductService.create(partnerProduct);
                    }
                }
            }

            return this.model.getId();
        } catch (Exception e) {
            e.printStackTrace();
            return FAIL;
        }
    }

    /**
     * <code>detail</code>
     *
     * @param request
     * @param response
     * @return
     * @since   2014-12-9    wangya
     */
    @RequestMapping(value = "/detail")
    public ModelAndView detail(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("business/projectm/partnerInfo_detail");
        String id = request.getParameter("id");
        BasePartnerInfo model = getService().get(id);
        //厂商类型
        Set<BasePartnerProduct> productTypeSet = model.getBasePartnerProducts();
        StringBuffer sb = new StringBuffer();
        StringBuffer sbNames = new StringBuffer();
        StringBuffer sbs = new StringBuffer();
        StringBuffer sbIds = new StringBuffer();
        if (productTypeSet.size() > 0) {
            Iterator<BasePartnerProduct> it = productTypeSet.iterator();
            while (it.hasNext()) {
                BasePartnerProduct product = it.next();
                BaseProductType type = this.productTypeService.get(product.getProductTypeId());
                if (type != null) {
                    sb.append(type.getTypeName() + ",");
                    sbs.append(product.getProductTypeId() + ",");
                }
            }
            String[] typeNames = sb.toString().split(",");
            for (int i = 0; i < typeNames.length; i++) {
                if (i == typeNames.length - 1) {
                    sbNames.append(typeNames[i]);
                } else {
                    sbNames.append(typeNames[i] + ",");
                }
            }

            String[] typeIds = sbs.toString().split(",");
            for (int i = 0; i < typeIds.length; i++) {
                if (i == typeIds.length - 1) {
                    sbIds.append(typeIds[i]);
                } else {
                    sbIds.append(typeIds[i] + ",");
                }
            }

        }
        mav.addObject("typeNames", sbNames.toString());
        mav.addObject("model", model);

        //厂商设备类型
        List<SysDomainValue> typeList = getService().getConfPartnerType();
        mav.addObject("typeList", typeList);
        return mav;
    }

    @RequestMapping(value = "/edit")
    public ModelAndView edit(HttpServletRequest request, HttpServletResponse response, @RequestParam("id") String id) {
        ModelAndView mav = new ModelAndView("business/projectm/partnerInfo_saveOrUpdate");
        this.model = getService().get(id);

        //厂商设备类型
        List<SysDomainValue> typeList = getService().getConfPartnerType();
        mav.addObject("typeList", typeList);

        //厂商服务供应商类型
        List<SysDomainValue> serviceList = getService().getPartnerServiceType();
        mav.addObject("serviceList", serviceList);
        mav.addObject("model", this.model);

        //厂商类型
        Set<BasePartnerProduct> productTypeSet = this.model.getBasePartnerProducts();
        StringBuffer sb = new StringBuffer();
        StringBuffer sbNames = new StringBuffer();

        StringBuffer sbs = new StringBuffer();
        StringBuffer sbIds = new StringBuffer();
        if (productTypeSet.size() > 0) {
            Iterator<BasePartnerProduct> it = productTypeSet.iterator();
            while (it.hasNext()) {
                BasePartnerProduct product = it.next();
                if (!StringUtil.isEmpty(product.getProductTypeId())) {
                    BaseProductType type = this.productTypeService.get(product.getProductTypeId());
                    if (type != null) {
                        sb.append(type.getTypeName() + ",");
                    }
                    sbs.append(product.getProductTypeId() + ",");
                }
            }
            String[] typeNames = sb.toString().split(",");
            for (int i = 0; i < typeNames.length; i++) {
                if (i == typeNames.length - 1) {
                    sbNames.append(typeNames[i]);
                } else {
                    sbNames.append(typeNames[i] + ",");
                }
            }

            String[] typeIds = sbs.toString().split(",");
            for (int i = 0; i < typeIds.length; i++) {
                if (i == typeIds.length - 1) {
                    sbIds.append(typeIds[i]);
                } else {
                    sbIds.append(typeIds[i] + ",");
                }
            }

        }
        mav.addObject("typeIds", sbIds.toString());
        mav.addObject("typeNames", sbNames.toString());
        return mav;
    }

    /**
     * <code>buildSearch</code>
     * 创建查询条件
     * @param detachedCriteria
     * @since   2013-11-13    guokemenng
     */
    public void buildSearch(HttpServletRequest request, DetachedCriteria detachedCriteria) {

        //没删除
        Short s = 1;
        detachedCriteria.add(Restrictions.eq(BasePartnerInfo.DELETEFLAG, s));

        //厂商类型
        String partnerType = request.getParameter("partnerType");
        //服务供应商类型
        String servicePartnerType = request.getParameter("servicePartnerType");

        String partnerName = request.getParameter("partnerName");
        if (partnerType != null) {
            detachedCriteria.add(Restrictions.like(BasePartnerInfo.PARTNERTYPE, partnerType));
        }
        if (partnerName != null) {
            detachedCriteria.add(Restrictions.like(BasePartnerInfo.PARTNERFULLNAME, partnerName));
        }
        if (servicePartnerType != null) {
            detachedCriteria.add(Restrictions.eq(BasePartnerInfo.SERVICEPARTNERTYPE, Short.parseShort(servicePartnerType)));
        }
    }

    @RequestMapping(value = "/getAlltype")
    @ResponseBody
    public List<RoleForm> getAlltype() {
        List<RoleForm> rs = new ArrayList<RoleForm>();
        List<BaseProductType> types = productTypeService.find();
        for (BaseProductType baseProductType : types) {
            RoleForm rf = new RoleForm();
            rf.setId(baseProductType.getId());
            rf.setName(baseProductType.getTypeName());
            rf.setPiny(baseProductType.getTypeCode());
            rs.add(rf);
        }

        return rs;

    }

    @RequestMapping(value = "/getPartnertype")
    @ResponseBody
    public List<RoleForm> getPartnertype(HttpServletRequest request, HttpServletResponse response, @RequestParam("id") String id) {

        List<RoleForm> rs = new ArrayList<RoleForm>();
        List<BaseProductType> types = productTypeService.getListByPartner(id);
        for (BaseProductType baseProductType : types) {
            RoleForm rf = new RoleForm();
            rf.setId(baseProductType.getId());
            rf.setName(baseProductType.getTypeName());
            rf.setPiny(baseProductType.getTypeCode());
            rs.add(rf);
        }

        return rs;

    }

    /**
     * <code>addPartnerProduct</code>
     * 添加设备类型
     * @param request
     * @param response
     * @param permissions
     * @return
     * @since   2013-11-19    guokemenng
     */
    @ResponseBody
    @RequestMapping(value = "/addPartnerProduct", method = RequestMethod.POST)
    public String addPartnerProduct(HttpServletRequest request, HttpServletResponse response, @RequestParam("permissions") String permissions) {
        try {
            if (permissions != null) {
                getService().addPartnerProduct(permissions);
            }
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
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
     * <code>getProductByPartner</code>
     * 得到厂商所支持的类型
     * 格式{["id":XX,"name":XX]}
     * @param partnerId
     * @return
     * @since   2013-11-20    guokemenng
     */
    @ResponseBody
    @RequestMapping(value = "/getProductByPartner")
    public List<Map<String, String>> getProductByPartner(@RequestParam("partnerId") String partnerId) {
        this.model = getService().get(partnerId);
        Set<BasePartnerProduct> productSet = new HashSet<BasePartnerProduct>(0);
        if (this.model != null) {
            productSet = this.model.getBasePartnerProducts();
        }
        List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
        if (productSet.size() > 0) {
            Iterator<BasePartnerProduct> it = productSet.iterator();
            while (it.hasNext()) {
                BasePartnerProduct product = it.next();
                Map<String, String> map = new HashMap<String, String>();
                map.put("id", product.getId());
                map.put("name", this.productTypeService.get(product.getProductTypeId()).getTypeName());
                mapList.add(map);
            }
        }
        return mapList;
    }

    /**
     * <code>searchStaff</code>
     * 厂商插件，搜索厂商
     * @param request
     * @param response
     * @return
     * @since   2014年8月19日    guokemenng
     */
    @ResponseBody
    @RequestMapping(value = "/searchPartnerInfo")
    public Map<String, Object> searchPartnerInfo(HttpServletRequest request, HttpServletResponse response) {

        Map<String, Object> map = new HashMap<String, Object>();
        String pageNo = request.getParameter("pageNo");
        String pageSize = request.getParameter("pageSize");
        String name = request.getParameter("text");
        String code = request.getParameter("code");
        if (StringUtil.isEmpty(code)) {
            return map;
        } else if (code.equals("root")) {
            if (pageNo != null) {
                this.pageNo = Integer.parseInt(pageNo) - 1;
            }
            if (pageSize != null) {
                this.pageSize = Integer.parseInt(pageSize);
            }
            DetachedCriteria detachedCriteria = DetachedCriteria.forClass(getModel().getClass());
            if (!StringUtil.isEmpty(name)) {
                detachedCriteria.add(Restrictions.or(Restrictions.like(BasePartnerInfo.PARTNERFULLNAME, name + "%"), Restrictions.like(BasePartnerInfo.PARTNERCODE, name + "%")));
            }
            PaginationSupport paginationSupport = getService().findPageByCriteria(detachedCriteria, this.pageNo * this.pageSize, this.pageSize);
            @SuppressWarnings("unchecked")
            List<BasePartnerInfo> modelList = (List<BasePartnerInfo>) paginationSupport.getItems();
            List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
            for (BasePartnerInfo info : modelList) {
                mapList.add(PartnerUtils.transferPartner(info.getId(), info.getPartnerFullName()));
            }
            map.put("rows", mapList);
            map.put("total", paginationSupport.getTotalCount());
            map.put("page", pageNo);
            return map;
        } else {
            if (pageNo != null) {
                this.pageNo = Integer.parseInt(pageNo) - 1;
            }
            if (pageSize != null) {
                this.pageSize = Integer.parseInt(pageSize);
            }
            Object[] params = new Object[3];
            params[0] = code;
            params[1] = name + "%";
            params[2] = name + "%";
            List<BasePartnerInfo> modelList = getService().getPartnerInfoByProductTypeId(this.pageNo, this.pageSize, params);
            List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
            for (BasePartnerInfo info : modelList) {
                mapList.add(PartnerUtils.transferPartner(info.getId(), info.getPartnerFullName()));
            }
            map.put("rows", mapList);
            map.put("total", getService().getTotalCount(params));
            map.put("page", pageNo);
            return map;
        }
    }

    /**
     * <code>getPartnerCtyByType</code>
     * 厂商插件，厂商根据字母分类列表
     * @return
     * @since   2014年8月19日    guokemenng
     */
    @ResponseBody
    @RequestMapping(value = "/getPartnerCtyByType")
    public List<Map<String, Object>> getPartnerCtyByType(HttpServletRequest request, HttpServletResponse response) {

        String code = request.getParameter("code");
        String id = request.getParameter("id");
        if (StringUtil.isEmpty(id)) {
            return PartnerUtils.getInitializePartnerCty();
        } else {
            List<BasePartnerInfo> modelList = null;
            //所有的厂商
            if (StringUtil.isEmpty(code)) {
                modelList = new ArrayList<BasePartnerInfo>();
                //显示为空
            } else if (code.equals("root")) {
                modelList = getService().find();
            } else {
                Object[] params = new Object[1];
                params[0] = code;
                String sql = "select * from business_partnerinfo p,business_partnerproduct t where p.id = t.partnerId and t.productTypeId = ?";
                //                modelList = getService().getPartnerInfoByType(code);
                modelList = getService().getPartnerInfoByProductTypeId(sql, params);
            }
            return PartnerUtils.transferPartner(id, modelList);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/getParentsByPartner")
    public List<Map<String, Object>> getParentsByPartner(HttpServletRequest request, HttpServletResponse response) {
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();

        String selectId = request.getParameter("selectId");
        BasePartnerInfo model = getService().get(selectId);
        String id = PartnerUtils.getTypeByPartner(model);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        mapList.add(map);
        return mapList;
    }

    public static void main(String[] args) {
        System.out.println(11);

        /* try {
             InetAddress a = InetAddress.getLocalHost();
             System.out.println(a);
         } catch (Exception e) {
         }*/

    }

}
