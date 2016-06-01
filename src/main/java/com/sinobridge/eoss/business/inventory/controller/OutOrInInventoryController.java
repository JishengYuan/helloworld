/*
 * FileName: OutOrInInventoryController.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.business.inventory.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.ehcache.Ehcache;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sinobridge.base.core.controller.DefaultBaseController;
import com.sinobridge.base.core.utils.PaginationSupport;
import com.sinobridge.eoss.business.inventory.model.InventoryModel;
import com.sinobridge.eoss.business.inventory.model.OutOrInInventoryModel;
import com.sinobridge.eoss.business.inventory.model.PutOrInInventoryModel;
import com.sinobridge.eoss.business.inventory.service.BorrwInventoryProductService;
import com.sinobridge.eoss.business.inventory.service.InventoryService;
import com.sinobridge.eoss.business.inventory.service.OutOrInInventoryService;
import com.sinobridge.systemmanage.model.SysStaff;
import com.sinobridge.systemmanage.util.Constants;
import com.sinobridge.systemmanage.util.Global;
import com.sinobridge.systemmanage.util.StringUtil;
import com.sinobridge.systemmanage.vo.SystemUser;

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
 * 2015年2月3日 下午5:11:47          guo_kemeng        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Controller
@RequestMapping(value = "business/outOrInInventory")
public class OutOrInInventoryController extends DefaultBaseController<OutOrInInventoryModel, OutOrInInventoryService> {
    @Autowired
    InventoryService inventoryService;
    @Autowired
    OutOrInInventoryService outOrInInventoryService;
    @Autowired
    BorrwInventoryProductService productService;

    //    private final String columns[] = { "productNo", "serialNum", "cost" };
    //    private final String columnNames[] = { "产品编号", "数量", "成本" };
    //    private final String columnTypes[] = { "string", "string", "string"};
    private final static Ehcache systemUserCache = Constants.CACHE_MANAGER.getEhcache("systemUser");

    /**
     * <code>manage</code>
     *
     * @param request
     * @param response
     * @return
     * @since   2015年2月4日    guokemenng
     */
    @RequestMapping(value = "/manage")
    public ModelAndView manage(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("business/outOrInInventory/manage");
        //登录用户
        SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);

        mav.addObject("loginUser", systemUser.getUserName());
        return mav;
    }

    /**
     * <code>getList</code>
     * 出库信息列表
     * @param request
     * @param response
     * @return
     * @since   2015年2月9日    liyx
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
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(getModel().getClass());
        buildSearch(request, detachedCriteria);
        detachedCriteria.addOrder(Order.desc("id"));

        PaginationSupport paginationSupport = getService().findPageByCriteria(detachedCriteria, this.pageNo * this.pageSize, this.pageSize);

        map.put("rows", paginationSupport.getItems());
        map.put("total", paginationSupport.getTotalCount());
        map.put("page", pageNo);
        return map;
    }

    /**
     * <code>manage</code>
     * 入库信息列表
     * @param request
     * @param response
     * @return
     * @since   2015年3月5日    liyx
     */
    @ResponseBody
    @RequestMapping(value = "/getInInfoList")
    public Map<String, Object> getInInfoList(HttpServletRequest request, HttpServletResponse response) {

        Map<String, Object> map = new HashMap<String, Object>();
        String pageNo = request.getParameter("pageNo");
        String pageSize = request.getParameter("pageSize");
        if (pageNo != null) {
            this.pageNo = Integer.parseInt(pageNo) - 1;
        }
        if (pageSize != null) {
            this.pageSize = Integer.parseInt(pageSize);
        }
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(PutOrInInventoryModel.class);
        buildSearch(request, detachedCriteria);
        detachedCriteria.addOrder(Order.desc("id"));

        PaginationSupport paginationSupport = getService().findPageByCriteria(detachedCriteria, this.pageNo * this.pageSize, this.pageSize);

        map.put("rows", paginationSupport.getItems());
        map.put("total", paginationSupport.getTotalCount());
        map.put("page", pageNo);
        return map;
    }

    /**
     * <code>buildSearch</code>
     * 查询条件
     * @param request
     * @param detachedCriteria
     * @since   2015年2月4日    guokemenng
     */
    public void buildSearch(HttpServletRequest request, DetachedCriteria detachedCriteria) {
        String name = request.getParameter("name");
        if (!StringUtil.isEmpty(name)) {
            detachedCriteria.add(Restrictions.or(Restrictions.like(InventoryModel.PRODUCTNO, "%" + name + "%"), Restrictions.like(InventoryModel.SERIESNO, "%" + name + "%")));
        }
    }

    /**
     * 跳转到申请出库页面
     * @param request
     * @param response
     * @since 2015年2月11日    liyx
     */
    @RequestMapping(value = "/outStorageAdd")
    public ModelAndView outStorage(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("business/outOrInInventory/outStorageAdd");
        //登录用户
        SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
        StringBuffer serialNoStr = new StringBuffer("SZXQ-");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sdf.format(new Date());
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(OutOrInInventoryModel.class);
        //        long startTime = getStartTime();
        //        long endTime = getEndTime();
        //        detachedCriteria.add(Restrictions.ge("createDate", new Date(startTime)));
        //        detachedCriteria.add(Restrictions.le("createDate", new Date(endTime)));
        detachedCriteria.add(Restrictions.eq("customerManage", systemUser.getUserName()));
        PaginationSupport paginationSupport = this.getService().findPageByCriteria(detachedCriteria, 0, 1000);
        int serialNum = paginationSupport.getItems().size();

        serialNum += 1;

        serialNoStr.append(systemUser.getUserName()).append(dateStr).append(serialNum);
        mav.addObject("serialNoStr", serialNoStr);
        mav.addObject("userName", systemUser.getUserName());
        mav.addObject("staffName", systemUser.getStaffName());
        mav.addObject("dateTime", sdf2.format(new Date()));

        //        int contactsBySize = 0;
        //        /**
        //         * 生成可编辑表格方法
        //         * @param request
        //         * @param response
        //         * @return
        //         */
        //        String productId = request.getParameter("productId");
        //        Map<String, Object> tableModel = new HashMap<String, Object>(); //表格数据模型
        //        tableModel.put("name", "editGridName"); //表格名称
        //        tableModel.put("defaultAmount", contactsBySize);//新建表格时，默认为0，如果是修改，应改表格数据的总行数。
        //
        //        List<HashMap<String, Object>> rowNames = new ArrayList<HashMap<String, Object>>(); //表格中的列数据
        //        for (int i = 0; i < 3; i++) {
        //            HashMap<String, Object> dataMap = new HashMap<String, Object>();
        //            dataMap.put("name", columns[i]); //字段名称，用于保存数据
        //            dataMap.put("label", columnNames[i]);//字段在页面上的显示名称
        //            dataMap.put("type", columnTypes[i]); //表格中输入字段的类型，目前只支持string,date
        //            rowNames.add(dataMap);
        //        }
        //
        //        tableModel.put("fieldList", rowNames);
        //        List<HashMap<String, Object>> rowDatas = new ArrayList<HashMap<String, Object>>(); //表格总的数据，一行数据对应一个map
        //
        //        if (productId != null && !"".equals(productId)) {
        //        	OutOrInInventoryModel inventoryModel = outOrInInventoryService.get(Long.parseLong(productId));
        //            Set<BorrwInventoryProductModel> product =  inventoryModel.getBorrowProductModels();
        //            for (BorrwInventoryProductModel s : product) { //放入1行数据
        //                HashMap<String, Object> dataMap = new HashMap<String, Object>(); //因为先前在rowNames中定义表格有3列
        //                dataMap.put(columns[0], s.getInventoryId().getProductNo());
        //                dataMap.put(columns[2], s.getInventoryId().getNumber());
        //                dataMap.put(columns[3], s.getInventoryId().getCost());
        //                rowDatas.add(dataMap);
        //            }
        //        } else {
        //            tableModel.put("defaultAmount", 0);//新建表格时，默认为0，如果是修改，应该表格数据的总行数。
        //        }
        //        tableModel.put("dataList", rowDatas);
        //        ObjectMapper objectMapper = JacksonJsonMapper.getInstance();
        //        try {
        //            String form = objectMapper.writeValueAsString(tableModel);
        //            mav.addObject("form",form);
        //        } catch (JsonGenerationException e) {
        //            e.printStackTrace();
        //        } catch (JsonMappingException e) {
        //            e.printStackTrace();
        //        } catch (IOException e) {
        //            e.printStackTrace();
        //        }

        //得到选择的型号，组list
        String productNo = request.getParameter("product");
        String[] product = productNo.split(",");
        List<Map<String, Object>> listProduct = getService().getChoiceProduct(product);//查询型号剩余数量   
        mav.addObject("product", listProduct);
        //        int contactsBySize = 0;
        //        /**
        //         * 生成可编辑表格方法
        //         * @param request
        //         * @param response
        //         * @return
        //         */
        //        String productId = request.getParameter("productId");
        //        Map<String, Object> tableModel = new HashMap<String, Object>(); //表格数据模型
        //        tableModel.put("name", "editGridName"); //表格名称
        //        tableModel.put("defaultAmount", contactsBySize);//新建表格时，默认为0，如果是修改，应改表格数据的总行数。
        //
        //        List<HashMap<String, Object>> rowNames = new ArrayList<HashMap<String, Object>>(); //表格中的列数据
        //        for (int i = 0; i < 3; i++) {
        //            HashMap<String, Object> dataMap = new HashMap<String, Object>();
        //            dataMap.put("name", columns[i]); //字段名称，用于保存数据
        //            dataMap.put("label", columnNames[i]);//字段在页面上的显示名称
        //            dataMap.put("type", columnTypes[i]); //表格中输入字段的类型，目前只支持string,date
        //            rowNames.add(dataMap);
        //        }
        //
        //        tableModel.put("fieldList", rowNames);
        //        List<HashMap<String, Object>> rowDatas = new ArrayList<HashMap<String, Object>>(); //表格总的数据，一行数据对应一个map
        //
        //        if (productId != null && !"".equals(productId)) {
        //        	OutOrInInventoryModel inventoryModel = outOrInInventoryService.get(Long.parseLong(productId));
        //            Set<BorrwInventoryProductModel> product =  inventoryModel.getBorrowProductModels();
        //            for (BorrwInventoryProductModel s : product) { //放入1行数据
        //                HashMap<String, Object> dataMap = new HashMap<String, Object>(); //因为先前在rowNames中定义表格有3列
        //                dataMap.put(columns[0], s.getInventoryId().getProductNo());
        //                dataMap.put(columns[2], s.getInventoryId().getNumber());
        //                dataMap.put(columns[3], s.getInventoryId().getCost());
        //                rowDatas.add(dataMap);
        //            }
        //        } else {
        //            tableModel.put("defaultAmount", 0);//新建表格时，默认为0，如果是修改，应该表格数据的总行数。
        //        }
        //        tableModel.put("dataList", rowDatas);
        //        ObjectMapper objectMapper = JacksonJsonMapper.getInstance();
        //        try {
        //            String form = objectMapper.writeValueAsString(tableModel);
        //            mav.addObject("form",form);
        //        } catch (JsonGenerationException e) {
        //            e.printStackTrace();
        //        } catch (JsonMappingException e) {
        //            e.printStackTrace();
        //        } catch (IOException e) {
        //            e.printStackTrace();
        //        }

        mav.addObject("model", this.model);
        return mav;
    }

    /* private long getStartTime() {
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
     }*/

    /**
     * 保存借货申请
     * @param request
     * @param response
     * @param project
     * @return
     * @since 2015年2月12日    liyx
     */
    @RequestMapping(value = "/doSave")
    @ResponseBody
    //public String doSave(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("_sino_eoss_inventory_addform") OutOrInInventoryModel outOrInventoryModel, Errors errors) throws Exception {
    //String tableData = request.getParameter("tableData");
    public Map<String, String> doSave(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("_sino_eoss_inventory_addform") OutOrInInventoryModel outOrInventoryModel, Errors errors) throws Exception {
        Map<String, String> rsmsg = new HashMap<String, String>();
        if (!StringUtil.isEmpty(outOrInventoryModel.getCustomerManage())) {
            String staffName = ((SysStaff) systemUserCache.get(outOrInventoryModel.getCustomerManage()).getObjectValue()).getStaffName();
            outOrInventoryModel.setCustomerManageName(staffName);
            outOrInventoryModel.setState((short) 1);
            outOrInventoryModel.setCreateDate(new Date());
        }

        String tableData = request.getParameter("tableGridData");
        String rs = getService().doSave(outOrInventoryModel, tableData);

        if (!rs.equals("true")) {
            rsmsg.put("result", FAIL);
            rsmsg.put("message", "报销申请提交出错！");
            return rsmsg;
        }
        rsmsg.put("result", SUCCESS);
        return rsmsg;
        /*if (!StringUtil.isEmpty(outOrInventoryModel.getCustomerManage())) {
            String staffName = ((SysStaff) systemUserCache.get(outOrInventoryModel.getCustomerManage()).getObjectValue()).getStaffName();
            outOrInventoryModel.setCustomerManageName(staffName);
            outOrInventoryModel.setState((short) 1);
            outOrInventoryModel.setCreateDate(new Date());
        }

        String[] ids = request.getParameterValues("ids");

        getService().saveOrUpdate(outOrInventoryModel);

        List<BorrwInventoryProductModel> productList = new ArrayList<BorrwInventoryProductModel>();
        for (int i = 0; i < ids.length; i++) {
            BorrwInventoryProductModel product = new BorrwInventoryProductModel();
            product.setBorrowingId(outOrInventoryModel);
            product.setBorrwTime(new Date());
            product.setState((short) 0); //0:未归还
            if (!StringUtil.isEmpty(ids[i])) {
                InventoryModel inventory = inventoryService.get(Long.parseLong(ids[i]));
                inventory.setState((short) 1);
                inventoryService.saveOrUpdate(inventory);
                product.setInventoryId(inventory);
            }
            productList.add(product);
        }
        productService.saveOrUpdate(productList);

        //  			tableDataMap = objectMapper.readValue(tableData, HashMap.class);
        //  			
        //  		  if (tableDataMap != null && tableDataMap.size() > 0) {
        //              for (String key : tableDataMap.keySet()) {
        //                  List<Object> gridDataList = (List<Object>) tableDataMap.get(key);
        //                  List<Map<String, Object>> gridList = new ArrayList<Map<String, Object>>();
        //                  for (int i = 0; (gridDataList != null && i < gridDataList.size()); i++) {//加了非空判断
        //                      Map<String, Object> map = StringToMap(gridDataList.get(i).toString());//格式转换
        //                      if (map.size() != 0) {
        //                          gridList.add(map);
        //                      }
        //                  }
        //                  for (int i = 0; i < gridList.size(); i++) {
        //                      Map<String, Object> map = gridList.get(i);
        //                      BorrwInventoryProductModel product = new BorrwInventoryProductModel();
        //                      product.setBorrowingId(outOrInventoryModel);
        //                      product.setBorrwTime(new Date());
        //                      product.setState((short) 0);   //0:未归还
        //                      if(!StringUtil.isEmpty((String)map.get("productNo"))){
        //                    	  InventoryModel inventory = inventoryService.get(Long.parseLong((String) map.get("productNo")));
        //                    	  inventory.setState((short) 1);
        //                    	  inventoryService.saveOrUpdate(inventory);
        //                    	  product.setInventoryId(inventory);
        //                      }
        //                      productList.add(product);
        //                  }
        //              }
        //              productService.saveOrUpdate(productList);
        //          }
        //return "success";

        // String[] ids = request.getParameterValues("ids");
        /* 	 List<BorrwInventoryProductModel> productList = new ArrayList<BorrwInventoryProductModel>();
        	  for(int i = 0; i < ids.length; i++){
        		 BorrwInventoryProductModel product = new BorrwInventoryProductModel();
        		 product.setBorrowingId(outOrInventoryModel);
        		 product.setBorrwTime(new Date());
        	     product.setState((short) 0);   //0:未归还
        	     if(!StringUtil.isEmpty(ids[i])){
        	    	 InventoryModel inventory = inventoryService.get(Long.parseLong(ids[i]));
        	    	 inventory.setState((short) 1);
        	    	 inventoryService.saveOrUpdate(inventory);
        	    	 product.setInventoryId(inventory);
        	     }
        	     productList.add(product);
        	  }
               productService.saveOrUpdate(productList);
        	  */
        //  			tableDataMap = objectMapper.readValue(tableData, HashMap.class);
        //  			
        //  		  if (tableDataMap != null && tableDataMap.size() > 0) {
        //              for (String key : tableDataMap.keySet()) {
        //                  List<Object> gridDataList = (List<Object>) tableDataMap.get(key);
        //                  List<Map<String, Object>> gridList = new ArrayList<Map<String, Object>>();
        //                  for (int i = 0; (gridDataList != null && i < gridDataList.size()); i++) {//加了非空判断
        //                      Map<String, Object> map = StringToMap(gridDataList.get(i).toString());//格式转换
        //                      if (map.size() != 0) {
        //                          gridList.add(map);
        //                      }
        //                  }
        //                  for (int i = 0; i < gridList.size(); i++) {
        //                      Map<String, Object> map = gridList.get(i);
        //                      BorrwInventoryProductModel product = new BorrwInventoryProductModel();
        //                      product.setBorrowingId(outOrInventoryModel);
        //                      product.setBorrwTime(new Date());
        //                      product.setState((short) 0);   //0:未归还
        //                      if(!StringUtil.isEmpty((String)map.get("productNo"))){
        //                    	  InventoryModel inventory = inventoryService.get(Long.parseLong((String) map.get("productNo")));
        //                    	  inventory.setState((short) 1);
        //                    	  inventoryService.saveOrUpdate(inventory);
        //                    	  product.setInventoryId(inventory);
        //                      }
        //                      productList.add(product);
        //                  }
        //              }
        //              productService.saveOrUpdate(productList);
        //          }
        //return "success";
    }

    /**
     * 获取所有在库设备
     * @param request
     * @param response
     * @return
     * @since 2015年2月12日    liyx
     */
    @RequestMapping(value = "/findAllProduct")
    @ResponseBody
    public List<Map<String, String>> findAllProduct(HttpServletRequest request, HttpServletResponse response) {
        List<Map<String, String>> listMap = new ArrayList<Map<String, String>>();
        List<InventoryModel> lists = inventoryService.findAllProduct();
        for (int i = 0; i < lists.size(); i++) {
            InventoryModel pro = lists.get(i);
            Map<String, String> map = new HashMap<String, String>();
            map.put("id", pro.getId().toString());
            map.put("name", pro.getProductNo());
            listMap.add(map);
        }
        return listMap;
    }

    /**
     * 获得项目信息
     * @param request
     * @param response
     * @return
     * @since 2015年2月12日    liyx
     */
    @RequestMapping(value = "/findProjectAll")
    @ResponseBody
    public List<Map<String, Object>> findAllProject(HttpServletRequest request, HttpServletResponse response) {
        List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> listMap2 = getService().getAllProject();

        if (listMap2.size() > 0) {
            for (int i = 0; i < listMap2.size(); i++) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("id", listMap2.get(i).get("id"));
                map.put("name", listMap2.get(i).get("projectName"));
                listMap.add(map);
            }
        }
        return listMap;
    }

    @SuppressWarnings("unchecked")
    /**
     * <code>getCustomerInfosByName</code>
     * 查询出在库设
     * @param request
     * @param response
     * @return
     * @since   2015年3月3日    liyx
     */
    @ResponseBody
    @RequestMapping(value = "/getProductInfosByName")
    public Map<String, Object> getCustomerInfosByName(HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter("name");
        Map<String, Object> map = new HashMap<String, Object>();
        String pageNo = request.getParameter("pageNo");
        String pageSize = request.getParameter("pageSize");

        if (pageNo != null) {
            this.pageNo = Integer.parseInt(pageNo) - 1;
        }
        if (pageSize != null) {
            this.pageSize = Integer.parseInt(pageSize);
        }

        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(InventoryModel.class);
        detachedCriteria.add(Restrictions.eq("state", Short.parseShort("0")));
        if (!StringUtil.isEmpty(name)) {
            detachedCriteria.add(Restrictions.like("productNo", "%" + name + "%"));
        }

        PaginationSupport paginationSupport = getService().findPageByCriteria(detachedCriteria, this.pageNo * this.pageSize, this.pageSize);

        List<InventoryModel> objList = (List<InventoryModel>) paginationSupport.getItems();
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        for (InventoryModel o : objList) {
            Map<String, Object> m = new HashMap<String, Object>();
            m.put("id", o.getId());
            m.put("name", o.getProductNo());
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
     * 根据Id 查询出产品实体
     * @param request
     * @param response
     * @return
     * @since   2015年3月3日    liyx
     */
    @ResponseBody
    @RequestMapping(value = "/getProductoModelById")
    public Map<String, Object> getInfoModelById(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");

        Map<String, Object> m = new HashMap<String, Object>();
        if (!StringUtil.isEmpty(id)) {
            InventoryModel p = inventoryService.get(Long.parseLong(id));
            if (p != null) {
                m.put("id", p.getId());
                m.put("name", p.getProductNo());
                m.put("cost", p.getCost());
            } else {
                m.put("id", "");
                m.put("name", "");
            }
        }
        return m;
    }

    /**
     * <code>getParentModelById</code>
     * 根据产品ID得到产品
     * @param request
     * @param response
     * @return
     * @since   2015年3月3日    liyx
     */
    @ResponseBody
    @RequestMapping(value = "/getParentModelById")
    public Map<String, Object> getParentModelById(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");

        Map<String, Object> m = new HashMap<String, Object>();
        if (!StringUtil.isEmpty(id)) {
            InventoryModel p = inventoryService.get(Long.parseLong(id));
            if (p != null) {
                m.put("id", p.getId());
                m.put("cost", p.getCost());
            } else {
                m.put("id", "");
                m.put("name", "");
            }
        }
        return m;
    }

    /**
     * <code>detail</code>
     * 借货单详情
     * @param request
     * @param response
     * @return
     * @since   2015年3月6日    liyx
     */
    @RequestMapping(value = "/detail")
    @ResponseBody
    public ModelAndView detail(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");

        ModelAndView mav = new ModelAndView("business/outOrInInventory/outStorageDetail");
        if (!StringUtil.isEmpty(id)) {
            OutOrInInventoryModel outorInInventoryModel = this.getService().get(Long.parseLong(id));
            String staffName = ((SysStaff) systemUserCache.get(outorInInventoryModel.getEngineers()).getObjectValue()).getStaffName();

            /*Set<BorrwInventoryProductModel> productSet = outorInInventoryModel.getBorrowProductModels();
            List<InventoryModel> productList = new ArrayList<InventoryModel>();
            for (BorrwInventoryProductModel s : productSet) {
                InventoryModel inventory = s.getInventoryId();
                productList.add(inventory);
            }*/

            List<Map<String, Object>> productList = null;
            List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
            if (!StringUtil.isEmpty(id)) {
                productList = outOrInInventoryService.getProductsDetail(Integer.parseInt(id), 0);
                if (productList.size() > 0) {
                    for (int i = 0; i < productList.size(); i++) {
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("id", productList.get(i).get("id"));
                        map.put("partner", productList.get(i).get("Partner"));
                        map.put("productNo", productList.get(i).get("ProductNo"));
                        map.put("quantity", 1);
                        map.put("status", productList.get(i).get("State"));
                        //map.put("borrowingId", productList.get(i).get("borrowingId"));
                        listMap.add(map);
                    }
                }
            }

            mav.addObject("outModel", outorInInventoryModel);
            mav.addObject("enginners", staffName);
            mav.addObject("productList", productList);
        }
        return mav;
    }

    public Map<String, Object> StringToMap(String mapText) {

        if (mapText == null || mapText.equals("")) {
            return null;
        }
        mapText = mapText.substring(1, mapText.length() - 1);

        Map<String, Object> map = new HashMap<String, Object>();
        String[] text = mapText.split(",");

        for (String str : text) {
            String[] keyText = str.split("="); // 转换key与value的数组  
            if (keyText.length < 1) {
                continue;
            }
            if (keyText.length == 1) {
                //                String key = keyText[0]; // key  
                //                String value = " "; // value  
                //map.put(key, value);

            } else {
                String key = keyText[0]; // key  
                String value = keyText[1]; // value  
                if (!value.trim().equals("")) {
                    map.put(key, value);
                }
            }

        }
        return map;
    }

    /**出库申请页面
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/outAppliManage")
    public ModelAndView OutApplicationManage(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("business/outOrInInventory/outAppliManage");
        return mav;
    }

    /**
     * 出库申请列表
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getOutAppliList")
    public Map<String, Object> getOutAppliList(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<Map<String, Object>> list = getService().getOutAppliList();
        map.put("rows", list);
        map.put("total", list.size());
        map.put("page", 100);
        return map;
    }

}
