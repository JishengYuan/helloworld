/*
 * FileName: PresaleContractController.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.sales.contract.controller;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import com.sinobridge.base.core.controller.DefaultBaseController;
import com.sinobridge.base.core.utils.PaginationSupport;
import com.sinobridge.base.core.utils.UploadUtil;
import com.sinobridge.eoss.sales.contract.model.PresaleContractModel;
import com.sinobridge.eoss.sales.contract.model.PresalesContractTypeModel;
import com.sinobridge.eoss.sales.contract.service.PresaleContractService;
import com.sinobridge.eoss.sales.contract.service.PresalesContractTypeService;
import com.sinobridge.systemmanage.model.SysStaff;
import com.sinobridge.systemmanage.service.SysStaffService;
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
 * 2015年11月9日 下午4:01:02          guo_kemeng        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Controller
@RequestMapping(value = "/sales/precontract")
public class PresaleContractController extends DefaultBaseController<PresaleContractModel,PresaleContractService>{

    
    @Autowired
    private SysStaffService staffService;
    
    @Autowired
    PresalesContractTypeService presalesContractTypeService;
    
    /**
     * <code>manage</code>
     *
     * @param request
     * @param response
     * @return
     * @since   2015年11月9日    guokemenng
     */
    @RequestMapping(value = "/manage")
    public ModelAndView manage(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("sales/precontract/manage");
        return mav;
    }
    
    /**
     * <code>search</code>
     *
     * @param request
     * @param response
     * @return
     * @since   2015年11月10日    guokemenng
     */
    @RequestMapping("/search")
    public ModelAndView search(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("sales/precontract/search");
        return mav;
    }
    /**
     * <code>getList</code>
     * 得到列表
     * @param request
     * @param response
     * @return
     * @since   2015年11月9日    guokemenng
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
        buildSearch(request, searchMap);
        PaginationSupport paginationSupport = getService().findPageBySearchMap(searchMap, this.pageNo * this.pageSize, this.pageSize);
        
        @SuppressWarnings("unchecked")
        List<Map<String,Object>> mapList = (List<Map<String, Object>>) paginationSupport.getItems();
        BigDecimal pageAmount = new BigDecimal(0.00);
        for(int i = 0;i < mapList.size();i++){
            Map<String,Object> m = mapList.get(i);
            Object contractAmount = m.get("ContractAmount");
            if (contractAmount != null) {
                BigDecimal pa = new BigDecimal(contractAmount.toString());
                pageAmount = pageAmount.add(pa);
            }
        }
        String page = pageAmount.toString();
        
        //得到查询后所有合同的总金额
        String amount = getService().findTotallAmount(searchMap);
        
        map.put("rows", paginationSupport.getItems());
        map.put("total", paginationSupport.getTotalCount());
        map.put("page", iDisplayStart);
        map.put("pageAmount", page);
        map.put("totallAmount", amount);
        return map;
    }
    
    /**
     * <code>buildSearch4HashMap</code>
     * 通过HashMap创建查询条件
     * @param searchMap
     * @since 2014年7月16日     3unshine
     */
    public void buildSearch(HttpServletRequest request, HashMap<String, Object> searchMap) {
        String creator = request.getParameter("creator");
        String contractAmount = request.getParameter("contractAmount");
        String contractAmountb = request.getParameter("contractAmountb");
        String vendor = request.getParameter("vendor");
        String customerName = request.getParameter("customerName");
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        String proTechnology = request.getParameter("proTechnology");
        String customerType = request.getParameter("customerType");
        String preContractCode = request.getParameter("preContractCode");
        String contractCode = request.getParameter("contractCode");
        String projectSite = request.getParameter("projectSite");

        if (!StringUtil.isEmpty(creator)) {
            SysStaff staff = staffService.getStaffByAccount(creator);
            searchMap.put("creator", staff.getStaffId());
        }
        if (!StringUtil.isEmpty(contractAmount)) {
            searchMap.put("contractAmount", contractAmount);
        }
        if (!StringUtil.isEmpty(contractAmountb)) {
            searchMap.put("contractAmountb", contractAmountb);
        }
        if (!StringUtil.isEmpty(vendor)) {
            searchMap.put(PresaleContractModel.VENDOR, "%"+vendor+"%");
        }
        if (!StringUtil.isEmpty(customerName)) {
            searchMap.put(PresaleContractModel.CUSTOMERNAME, "%"+customerName+"%");
        }
        if (!StringUtil.isEmpty(startTime)) {
            searchMap.put("startTime", startTime);
        }
        if (!StringUtil.isEmpty(endTime)) {
            searchMap.put("endTime", endTime);
        }
        if (!StringUtil.isEmpty(proTechnology)) {
            searchMap.put(PresaleContractModel.PROTECHNOLOGY, "%"+proTechnology+"%");
        }
        if (!StringUtil.isEmpty(customerType)) {
            searchMap.put(PresaleContractModel.CUSTOMERTYPE, customerType);
        }
        if (!StringUtil.isEmpty(contractCode)) {
            searchMap.put(PresaleContractModel.CONTRACTCODE, contractCode);
        }
        if (!StringUtil.isEmpty(projectSite)) {
            searchMap.put(PresaleContractModel.PROJECTSITE, "%"+projectSite+"%");
        }
        if (!StringUtil.isEmpty(preContractCode)) {
            searchMap.put(PresaleContractModel.PRECONTRACTCODE, preContractCode);
        }
    }
    
    
    /**
     * <code>uploadFile</code>
     *
     * @param request
     * @param response
     * @return
     * @since   2015年11月10日    guokemenng
     */
    @ResponseBody
    @RequestMapping(value = "/uploadExcel")
    public Map<String,String> uploadFile(HttpServletRequest request, HttpServletResponse response) {
        Map<String,String> map = new HashMap<String,String>();
        try{
            
            String category = request.getParameter("category");
            MultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
            MultipartHttpServletRequest multipartRequest = resolver.resolveMultipart(request);

            CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest.getFile("attachment");
            String picName = file.getFileItem().getName();

            String webPath = UploadUtil.buildWebPath(UploadUtil.getBasePath() + "precontract/", picName);
            String storeFilePath = UploadUtil.buildStroeFilePath(webPath);

            File descPath = new File(UploadUtil.buildPath(storeFilePath));
            if (!descPath.exists()) {
                descPath.mkdirs();
            }

            File descF = new File(storeFilePath);
            try {
                FileCopyUtils.copy(file.getBytes(), descF);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String realPath = UploadUtil.buildWebRootPath() + webPath;
            
            getService().readExcel(realPath, category);
            map.put("msg", "success");
            return map;
        }catch(Exception e){
            e.printStackTrace();
            map.put("msg", "fail");
            return map;
        }
    }
    
    /**
     * <code>getTypeListByState</code>
     * 根据state得到合同分类
     * @param state
     * @return
     * @since   2015年11月11日    guokemenng
     */
    @ResponseBody
    @RequestMapping(value = "/getTypeListByState")
    public List<PresalesContractTypeModel> getTypeListByState(HttpServletRequest request, HttpServletResponse response){
        String state = request.getParameter("state");
        return presalesContractTypeService.getTypeListByState(Short.parseShort(state));
    }
    
    /**
     * <code>updatePresaleType</code>
     * 同步合同类型产讯条件
     * @param request
     * @param response
     * @return
     * @since   2015年11月11日    guokemenng
     */
    @ResponseBody
    @RequestMapping(value = "/updatePresaleType")
    public String updatePresaleType(HttpServletRequest request, HttpServletResponse response){
        try {
            presalesContractTypeService.updatePresaleType();
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return FAIL;
        }
    }
    
}
