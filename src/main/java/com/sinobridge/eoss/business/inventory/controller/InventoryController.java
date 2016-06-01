/*
 * FileName: InventoryController.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.business.inventory.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.ehcache.Ehcache;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
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
import com.sinobridge.eoss.business.inventory.model.BorrowQuantityModel;
import com.sinobridge.eoss.business.inventory.model.BorrwInventoryProductModel;
import com.sinobridge.eoss.business.inventory.model.InventoryModel;
import com.sinobridge.eoss.business.inventory.model.OutOrInInventoryModel;
import com.sinobridge.eoss.business.inventory.model.PutOrInInventoryModel;
import com.sinobridge.eoss.business.inventory.service.BorrowQuantityService;
import com.sinobridge.eoss.business.inventory.service.BorrwInventoryProductService;
import com.sinobridge.eoss.business.inventory.service.InventoryService;
import com.sinobridge.eoss.business.inventory.service.OutOrInInventoryService;
import com.sinobridge.eoss.business.inventory.service.PutOrInInventoryService;
import com.sinobridge.eoss.sales.contract.utils.ExcelUtil;
import com.sinobridge.systemmanage.model.SysPowerAction;
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
 * 2015年2月3日 下午5:11:30          guo_kemeng        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Controller
@RequestMapping(value = "business/inventory")
public class InventoryController extends DefaultBaseController<InventoryModel, InventoryService> {

    @Autowired
    OutOrInInventoryService outService;
    @Autowired
    PutOrInInventoryService putService;
    @Autowired
    InventoryService inventoryService;
    @Autowired
    BorrwInventoryProductService productService;
    @Autowired
    BorrowQuantityService quantityService;

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
        ModelAndView mav = new ModelAndView("business/inventory/manage");
        return mav;
    }

    /**
     * <code>getList</code>
     * 列表数据
     * @param request
     * @param response
     * @return
     * @since   2015年2月4日    guokemenng
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
     * <code>uploadProducts</code>
     * 导入Excel增加产品
     * @param request
     * @param response
     * @since   2015年2月4日    guokemenng
     */
    @ResponseBody
    @RequestMapping(value = "/uploadProducts")
    public String uploadProducts(HttpServletRequest request, HttpServletResponse response) {
        try {
            String filePath = doUpload(request);
            String realPath = UploadUtil.buildWebRootPath() + filePath;
            List<InventoryModel> modelList = readExcel(realPath);
            getService().saveOrUpdate(modelList);
            return SUCCESS;
        } catch (Exception e) {
            return FAIL;
        }
    }

    /**
     * <code>doUpload</code>
     * 上传操作
     * @param request
     * @return
     * @since   2015年2月4日    guokemenng
     */
    public String doUpload(HttpServletRequest request) {
        MultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        MultipartHttpServletRequest multipartRequest = resolver.resolveMultipart(request);

        CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest.getFile("attachment");
        String picName = file.getFileItem().getName();

        String webPath = UploadUtil.buildWebPath(UploadUtil.getBasePath() + "inventory/", picName);
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
        return webPath;
    }

    /**
     * <code>readExcel</code>
     * 读取Excel
     * @param path
     * @return
     * @since   2015年2月4日    guokemenng
     */
    public List<InventoryModel> readExcel(String path) {
        List<InventoryModel> modelList = new ArrayList<InventoryModel>();
        try {
            File excelFile = new File(path);
            String fileName = excelFile.getName();
            fileName = fileName.toLowerCase();
            if (fileName.toLowerCase().endsWith(".xls")) {
                //把一张xls的数据表读到wb里
                HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(excelFile));
                //读取第一页,一般一个excel文件会有三个工作表，这里获取第一个工作表来进行操作    HSSFSheet sheet = wb.getSheetAt(0);
                Sheet sheet = wb.getSheetAt(0);
                //循环遍历表sheet.getLastRowNum()是获取一个表最后一条记录的记录号，
                //第一条为标题 从第二行开始读取数据
                for (int j = 1; j < sheet.getLastRowNum() + 1; j++) {
                    //创建一个行对象
                    Row row = sheet.getRow(j);
                    InventoryModel m = new InventoryModel();
                    getMapModel(m, row);
                    modelList.add(m);
                }
            } else {
                InputStream input = new FileInputStream(excelFile);
                XSSFWorkbook workBook = new XSSFWorkbook(input);
                // 获取Sheet数量
                Integer sheetNum = workBook.getNumberOfSheets();
                for (int i = 0; i < sheetNum; i++) {
                    XSSFSheet sheet = workBook.getSheetAt(i);
                    for (int j = 1; j < sheet.getLastRowNum() + 1; j++) {
                        //创建一个行对象
                        XSSFRow row = sheet.getRow(j);
                        InventoryModel m = new InventoryModel();
                        getMapModel(m, row);
                        modelList.add(m);
                    }
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return modelList;
    }

    /**
     * <code>getMapModel</code>
     * 组成对象
     * @param map
     * @param row
     * @return
     * @since   2015年2月4日    guokemenng
     */
    public InventoryModel getMapModel(InventoryModel m, Row row) {
        //把一行里的每一个字段遍历出来
        for (int i = 0; i < row.getLastCellNum(); i++) {
            //创建一个行里的一个字段的对象，也就是获取到的一个单元格中的值
            Cell cell = row.getCell(i);
            try {
                Object o = ExcelUtil.getCellvalue(cell);
                switch (i) {
                    case 0: {
                        if (o != null) {
                            m.setPartner(o.toString());
                        }
                        break;
                    }
                    case 1: {
                        if (o != null) {
                            m.setProductNo(o.toString());
                        }
                        break;
                    }
                    case 2: {
                        if (o != null) {
                            m.setNumber(Integer.parseInt(o.toString()));
                        }
                        break;
                    }
                    case 3: {
                        if (o != null) {
                            m.setAppearance(o.toString());
                        }
                        break;
                    }
                    case 4: {
                        if (o != null) {
                            m.setSeriesNo(o.toString());
                        }
                        break;
                    }
                    case 5: {
                        if (o != null) {
                            m.setLocation(o.toString());
                        }
                        break;
                    }
                    case 6: {
                        if (o != null) {
                            m.setRemark(o.toString());
                        }
                        break;
                    }
                    case 7: {
                        if (o != null) {
                            m.setCost(new BigDecimal(o.toString()));
                        }
                        break;
                    }
                    case 8: {
                        if (o != null) {
                            m.setRent(new BigDecimal(o.toString()));
                        }
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return m;
    }

    /**
     * <code>detail</code>
     * 详情页面
     * @param request
     * @param response
     * @return
     * @since   2015年2月6日    guokemenng
     */
    @RequestMapping(value = "/detail")
    public ModelAndView detail(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("business/inventory/detail");

        String id = request.getParameter("id");
        InventoryModel model = getService().get(Long.parseLong(id));
        mav.addObject("model", model);

        return mav;
    }

    /**
     * <code>getDetailList</code>
     * 设备借出归还记录
     * @param request
     * @param response
     * @return
     * @since   2015年2月6日    guokemenng
     */
    @ResponseBody
    @RequestMapping(value = "/getDetailList")
    public Map<String, Object> getDetailList(HttpServletRequest request, HttpServletResponse response) {

        Map<String, Object> map = new HashMap<String, Object>();
        String pageNo = request.getParameter("pageNo");
        String pageSize = request.getParameter("pageSize");

        String id = request.getParameter("id");

        if (pageNo != null) {
            this.pageNo = Integer.parseInt(pageNo) - 1;
        }
        if (pageSize != null) {
            this.pageSize = Integer.parseInt(pageSize);
        }

        PaginationSupport paginationSupport = getService().getDetailList(id, this.pageNo * this.pageSize, this.pageSize);

        map.put("rows", paginationSupport.getItems());
        map.put("total", paginationSupport.getTotalCount());
        map.put("page", pageNo);
        return map;
    }

    /**
     * 杰出设备租金修改页面
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/updateRent")
    public ModelAndView updateRent(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("business/inventory/updateRent");
        String id = request.getParameter("id");
        BorrwInventoryProductModel invPro = productService.get(Long.parseLong(id));
        mav.addObject("model", invPro);
        return mav;
    }

    /**
     * 保存租金修改
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getNewRent")
    public String getNewRent(HttpServletRequest request, HttpServletResponse response) {
        String msg = "true";
        String id = request.getParameter("id");
        BorrwInventoryProductModel invPro = productService.get(Long.parseLong(id));
        String rent = request.getParameter("rent");
        invPro.setRent(new BigDecimal(rent));
        try {
            productService.saveOrUpdate(invPro);
        } catch (Exception e) {
            e.printStackTrace();
            msg = "false";
        }
        return msg;
    }

    /**
     * <code>approveManage</code>
     * 审批列表页面
     * @param request
     * @param response
     * @return
     * @since   2015年2月9日    guokemenng
     */
    @RequestMapping(value = "/approveManage")
    public ModelAndView approveManage(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("business/inventory/approveManage");

        SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
        mav.addObject("sysUser", systemUser);

        return mav;
    }

    /**
     * <code>getApproveList</code>
     * 得到审批列表
     * @param request
     * @param response
     * @return
     * @since   2015年2月9日    guokemenng
     */
    @ResponseBody
    @RequestMapping(value = "/getApproveList")
    public Map<String, Object> getApproveList(HttpServletRequest request, HttpServletResponse response) {

        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> paramMap = new HashMap<String, Object>();
        String pageNo = request.getParameter("pageNo");
        String pageSize = request.getParameter("pageSize");
        if (pageNo != null) {
            this.pageNo = Integer.parseInt(pageNo) - 1;
        }
        if (pageSize != null) {
            this.pageSize = Integer.parseInt(pageSize);
        }
        buildApproveSearch(request, paramMap);
        PaginationSupport paginationSupport = getService().getApproveList(paramMap, this.pageNo * this.pageSize, this.pageSize);

        map.put("rows", paginationSupport.getItems());
        map.put("total", paginationSupport.getTotalCount());
        map.put("page", pageNo);
        return map;
    }

    /**
     * <code>buildApproveSearch</code>
     * 审批列表查询条件
     * @param request
     * @param paramMap
     * @since   2015年2月9日    guokemenng
     */
    public void buildApproveSearch(HttpServletRequest request, Map<String, Object> paramMap) {
        SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
        String userName = request.getParameter("userName");
        String state = ""; //request.getParameter("state");

        //        String userName = "刘博";
        //        String state = "1";
        if (!StringUtil.isEmpty(userName)) {
            String staffName = ((SysStaff) systemUserCache.get(userName).getObjectValue()).getStaffName();
            paramMap.put("userName", staffName);
        }
        if (!StringUtil.isEmpty(state)) {
            //            paramMap.put("state", state);
        } else {
            List<SysPowerAction> actions = systemUser.getSysPowerActions();
            for (SysPowerAction sysPowerAction : actions) {
                if ("InventoryApprove1".equals(sysPowerAction.getPowCode())) {
                    state = "1";
                }
                if ("InventoryApprove2".equals(sysPowerAction.getPowCode())) {
                    state = "2";
                }
            }
            paramMap.put("state", state);
        }
    }

    /**
     * <code>approvePage</code>
     * 备件审批页面
     * @param request
     * @param response
     * @return
     * @since   2015年2月11日    guokemenng
     */
    @RequestMapping(value = "/approvePage")
    public ModelAndView approvePage(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("business/inventory/approvePage");

        String idStr = request.getParameter("id");
        String id = idStr.split("_")[0];
        String type = idStr.split("_")[1];

        String approveState = "";
        SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
        List<SysPowerAction> actions = systemUser.getSysPowerActions();
        for (SysPowerAction sysPowerAction : actions) {
            if ("InventoryApprove1".equals(sysPowerAction.getPowCode())) {
                approveState = "1";
            }
            if ("InventoryApprove2".equals(sysPowerAction.getPowCode())) {
                approveState = "2";
            }
        }

        //出库
        if (type.equals("0")) {
            OutOrInInventoryModel outModel = outService.get(Long.parseLong(id));
            mav.addObject("model", outModel);

            /*出库产品数据已改需求，2015-11-24
            List<InventoryModel> productList = getService().getInventorysByOutId(outModel.getId());
            mav.addObject("productList", productList);*/

        }
        //入库
        if (type.equals("1")) {
            mav = new ModelAndView("business/inventory/putApprovePage");
            PutOrInInventoryModel putModel = putService.get(Long.parseLong(id));
            mav.addObject("model", putModel);

            List<InventoryModel> productList = getService().getInventorysByPutId(putModel.getId());
            mav.addObject("productList", productList);

            if (putModel.getBorrowingId() != null) {
                mav.addObject("outModel", outService.get(putModel.getBorrowingId()));
            }

        }

        mav.addObject("approveState", approveState);
        return mav;
    }

    /**
     * 异步获得待审批的借货产品数据
     * @param request
     * @param response
     * @return
     * @since   2015年11月24日    liyx
     */
    @RequestMapping(value = "/getProductData")
    @ResponseBody
    public List<Map<String, Object>> getProductData(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        //当前登录用户
        //SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);

        List<Map<String, Object>> productList = null;
        List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
        if (!StringUtil.isEmpty(id)) {
            productList = outService.getProducts(Integer.parseInt(id));
            if (productList.size() > 0) {
                for (int i = 0; i < productList.size(); i++) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("id", productList.get(i).get("id"));
                    map.put("partner", productList.get(i).get("partner"));
                    map.put("productNo", productList.get(i).get("productNo"));
                    map.put("quantity", productList.get(i).get("quantity"));
                    map.put("status", productList.get(i).get("status"));
                    map.put("borrowingId", productList.get(i).get("borrowingId"));
                    listMap.add(map);
                }
            }
        }
        return listMap;
    }

    /**
     * 
     * @param request
     * @param response
     * @return
     * @since   2015年11月25日    liyx
     */
    @RequestMapping(value = "/getSeriesByProductNo")
    @ResponseBody
    public List<Map<String, Object>> getSeriesByProductNo(HttpServletRequest request, HttpServletResponse response) {
        String productNo = request.getParameter("productNo");
        String borrowingId = request.getParameter("borrowingId");
        List<Map<String, Object>> productList = null;
        List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
        if (!StringUtil.isEmpty(productNo) && !StringUtil.isEmpty(borrowingId)) {
            productList = outService.getSeriesNoByProductNo(Integer.parseInt(borrowingId), productNo);
            if (productList.size() > 0) {
                for (int i = 0; i < productList.size(); i++) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("id", productList.get(i).get("id"));
                    map.put("name", productList.get(i).get("seriesNo"));
                    listMap.add(map);
                }
            }
        }
        return listMap;
    }

    /**
     * <code>outApproveSubmit</code>
     * 出库审批
     * @param request
     * @param response
     * @return
     * @since   2015年2月12日    liyx
     */
    @ResponseBody
    @RequestMapping(value = "/outApproveSubmit")
    public String outApproveSubmit(HttpServletRequest request, HttpServletResponse response) {
        try {
            String outId = request.getParameter("outId");
            String[] inventoryIds = request.getParameterValues("inventoryIds");

            Short state = 0;
            SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
            List<SysPowerAction> actions = systemUser.getSysPowerActions();
            for (SysPowerAction sysPowerAction : actions) {
                if ("InventoryApprove1".equals(sysPowerAction.getPowCode())) {
                    state = 2;
                }
                if ("InventoryApprove2".equals(sysPowerAction.getPowCode())) {
                    state = 3;
                }
            }
            //            List<InventoryModel> productList = getService().getInventorysByOutId(Long.parseLong(outId));
            //            for (InventoryModel product : productList) {
            //                product.setState(state);
            //            }
            //            getService().saveOrUpdate(productList);

            OutOrInInventoryModel outModel = outService.get(Long.parseLong(outId));
            outModel.setState(state);
            outService.update(outModel);

            if (inventoryIds != null && inventoryIds.length > 0) {
                List<BorrwInventoryProductModel> productList = new ArrayList<BorrwInventoryProductModel>();
                //List<InventoryModel> inventoryList = new ArrayList<InventoryModel>();
                for (int i = 0; i < inventoryIds.length; i++) {
                    BorrwInventoryProductModel product = new BorrwInventoryProductModel();
                    product.setBorrowingId(outModel);
                    product.setBorrwTime(new Date());
                    product.setState(state); //0:未归还
                    if (!StringUtil.isEmpty(inventoryIds[i])) {
                        InventoryModel inventory = inventoryService.get(Long.parseLong(inventoryIds[i]));
                        inventory.setState(state);
                        //inventoryList.add(inventory);
                        inventoryService.saveOrUpdate(inventory);
                        product.setInventoryId(inventory);
                    }
                    productList.add(product);
                }
                //inventoryService.saveOrUpdate(inventoryList);
                productService.saveOrUpdate(productList);

                //修改BorrowQuantityMode状态
                List<BorrowQuantityModel> quantityList = quantityService.getInventoryquaByOutId(Long.parseLong(outId));
                for (BorrowQuantityModel quantity : quantityList) {
                    quantity.setStatus((short) 2); //0:待审批  1:刘新芳审批通过  2:刘博审批通过 
                }
                quantityService.saveOrUpdateAll(quantityList);
            }
            return SUCCESS;
        } catch (Exception e) {
            return FAIL;
        }
    }

    /**
     * <code>putApproveSubmit</code>
     * 入库审批
     * @param request
     * @param response
     * @return
     * @since   2015年2月12日    liyx
     */
    @ResponseBody
    @RequestMapping(value = "/putApproveSubmit")
    public String putApproveSubmit(HttpServletRequest request, HttpServletResponse response) {
        try {
            String putId = request.getParameter("putId");

            Short state = 0;
            //            SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
            //            List<SysPowerAction> actions = systemUser.getSysPowerActions();
            //            for (SysPowerAction sysPowerAction : actions) {
            //                if ("InventoryApprove1".equals(sysPowerAction.getPowCode())) {
            //                    state = 2;
            //                }
            //                if ("InventoryApprove2".equals(sysPowerAction.getPowCode())) {
            //                    state = 0;
            //                }
            //            }
            //更新库存状态
            List<InventoryModel> productList = getService().getInventorysByPutId(Long.parseLong(putId));
            for (InventoryModel product : productList) {
                product.setState(state);
            }
            getService().saveOrUpdate(productList);

            //更新借货单设备状态
            List<BorrwInventoryProductModel> productModels = productService.getProductByPutId(Long.parseLong(putId));
            for (BorrwInventoryProductModel model : productModels) {
                model.setState((short) 4); //0:在库  1:待审批  2:已借出  3:待归还  4:已归还
            }
            productService.saveOrUpdate(productModels);

            //更新入库单状态
            PutOrInInventoryModel putModel = putService.get(Long.parseLong(putId));
            putModel.setState((short) 3); //0:草稿  1,2:待审批  3：已归还
            putService.update(putModel);

            //查询借货单中设备状态，更新出库单状态
            int updateFlag = 0;
            List<BorrwInventoryProductModel> productModels2 = productService.getProductByOutId(putModel.getBorrowingId());
            for (BorrwInventoryProductModel model : productModels2) {
                if (model.getState() == 3) {
                    updateFlag = 1;
                }
            }
            if (updateFlag == 0) {
                OutOrInInventoryModel outModel = outService.get(putModel.getBorrowingId());
                outModel.setState((short) 4); //0:草稿  1:待审批  2:已借出  3:待归还  4:已归还
                outService.saveOrUpdate(outModel);
            }

            return SUCCESS;
        } catch (Exception e) {
            return FAIL;
        }
    }

}
