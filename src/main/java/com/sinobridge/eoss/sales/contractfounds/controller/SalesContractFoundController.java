/*
 * FileName: SalesContractFoundController.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.sales.contractfounds.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sinobridge.base.core.controller.DefaultBaseController;
import com.sinobridge.base.core.hibernate.id.IdentifierGeneratorImpl;
import com.sinobridge.base.core.utils.JacksonJsonMapper;
import com.sinobridge.base.core.utils.PaginationSupport;
import com.sinobridge.eoss.bpm.service.ProcInstAppr;
import com.sinobridge.eoss.bpm.service.process.ProcessService;
import com.sinobridge.eoss.sales.contract.model.SalesContractModel;
import com.sinobridge.eoss.sales.contract.service.SalesContractService;
import com.sinobridge.eoss.sales.contract.utils.CodeUtils;
import com.sinobridge.eoss.sales.contract.utils.DateUtils;
import com.sinobridge.eoss.sales.contractfounds.model.SalesContractChapterModel;
import com.sinobridge.eoss.sales.contractfounds.model.SalesContractFoundModel;
import com.sinobridge.eoss.sales.contractfounds.model.SalesContractQualificationModel;
import com.sinobridge.eoss.sales.contractfounds.model.SalesContractSizeModel;
import com.sinobridge.eoss.sales.contractfounds.model.SalesContractsCertificateModel;
import com.sinobridge.eoss.sales.contractfounds.service.QualificationChapterInfoService;
import com.sinobridge.eoss.sales.contractfounds.service.SalesContractFoundService;
import com.sinobridge.eoss.sales.contractfounds.service.SalesContractSizeService;
import com.sinobridge.systemmanage.model.SysStaff;
import com.sinobridge.systemmanage.service.SysStaffService;
import com.sinobridge.systemmanage.util.Global;
import com.sinobridge.systemmanage.util.StringUtil;
import com.sinobridge.systemmanage.vo.SystemUser;

/**
 * <p>
 * Description: 
 * </p>
 *
 * @author liyx
 * @version 1.0

 * <p>
 * History: 
 *
 * Date                     Author         Version     Description
 * ---------------------------------------------------------------------------------
 * 2015年12月1日 下午5:00:23      liyx           1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Controller
@RequestMapping(value = "/sales/contractFound")
public class SalesContractFoundController extends DefaultBaseController<SalesContractFoundModel, SalesContractFoundService> {
    @Autowired
    private ProcessService processService;
    @Autowired
    private SysStaffService sysStaffService;
    @Autowired
    private SysStaffService staffService;
    @Autowired
    private SalesContractService salesContractService;
    @Autowired
    private SalesContractSizeService salesContractSizeService;
    @Autowired
    private QualificationChapterInfoService qualificationChapterInfo;
    //往来款
    private final String sizeColumns[] = { "applyPrices", "borrowingPayType", "currentMoneyUses" };
    private final String sizeColumnNames[] = { "金额", "支付方式", "用途" };
    private final String sizeColumnTypes[] = { "string", "select", "select" };
    private final String sizeColumnUrl[] = { "", "", "" };
    //盖章
    private final String chapterColumns[] = { "chopType", "borrowDate", "returnDate" };
    private final String chapterColumnNames[] = { "章类型", "使用时间", "归还时间" };
    private final String chapterColumnTypes[] = { "select", "datetime", "datetime" };
    private final String chapterColumnUrl[] = { "/sales/contractFound/findSalesType?type=1", "", "" };
    //资质
    private final String ficationColumns[] = { "aptitudeName", "borrowDate", "returnDate" };
    private final String ficationColumnNames[] = { "资质名称", "使用时间", "归还时间" };
    private final String ficationColumnTypes[] = { "select", "datetime", "datetime" };
    private final String ficationColumnUrl[] = { "/sales/contractFound/findSalesType?type=2", "", "" };
    //证书
    private final String certificateColumns[] = { "certificatesType", "certificateNum" };
    private final String certificateColumnNames[] = { "证书名称", "数量" };
    private final String certificateColumnTypes[] = { "select", "string" };
    private final String certificateColumnUrl[] = { "/sales/contractFound/findSalesType?type=3", "" };

    //日期的格式
    String DatePattern = "yyyy-MM-dd";

    @RequestMapping(value = "/manage")
    public ModelAndView manage(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("sales/contractFound/manage");
        return mav;
    }

    @RequestMapping(value = "/search")
    public ModelAndView search(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("sales/contractFound/search");
        return mav;
    }

    @ResponseBody
    @RequestMapping(value = "/getSearchManageList")
    public Map<String, Object> getSearchManageList(HttpServletRequest request, HttpServletResponse response) {
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
        // DetachedCriteria detachedCriteria = DetachedCriteria.forClass(SalesContractFoundModel.class);
        //当前登录用户
        SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
        searchMap.put("creator", systemUser.getUserName());
        //根据项目中的项目计划模版查询
        //detachedCriteria.add(Restrictions.eq(PmsPlanTemplateModel.TEMPLATETYPE, Integer.parseInt(templateType)));
        PaginationSupport paginationSupport = getService().findFoundList(searchMap, this.pageNo * this.pageSize, this.pageSize);

        map.put("rows", paginationSupport.getItems());
        map.put("total", paginationSupport.getTotalCount());
        map.put("page", iDisplayStart);
        return map;
    }

    /**
     * @param request
     * @param searchMap
     */
    private void buildSearch(HttpServletRequest request, HashMap<String, Object> searchMap) {
        String contractName = request.getParameter("contractName");
        String contractAmount = request.getParameter("contractAmount");
        String contractAmountb = request.getParameter("contractAmountb");
        if (!StringUtil.isEmpty(contractName)) {
            searchMap.put("contractName", contractName);
        }
        if (!StringUtil.isEmpty(contractAmount)) {
            searchMap.put("contractAmount", contractAmount);
        }
        if (!StringUtil.isEmpty(contractAmountb)) {
            searchMap.put("contractAmountb", contractAmountb);
        }

    }

    @RequestMapping(value = "/addBidding")
    public ModelAndView addBidding(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("sales/contractFound/addBidding");

        String formDataJson_size = "";
        String formDataJson_chapter = "";
        String formDataJson_fication = "";
        String formDataJson_certificate = "";

        SalesContractFoundModel salesContractFoundModel = null;
        //创建者
        SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
        request.setAttribute("systemUser", systemUser);
        //拼凑出tableGrid所需的JSON数据
        formDataJson_size = tableGridDataUnit_size(salesContractFoundModel, "save");
        formDataJson_chapter = tableGridDataUnit_chapter(salesContractFoundModel, "save");
        formDataJson_fication = tableGridDataUnit_fication(salesContractFoundModel, "save");
        formDataJson_certificate = tableGridDataUnit_certificate(salesContractFoundModel, "save");

        request.setAttribute("form_size", formDataJson_size);
        request.setAttribute("form_chapter", formDataJson_chapter);
        request.setAttribute("form_fication", formDataJson_fication);
        request.setAttribute("form_certificate", formDataJson_certificate);

        return mav;
    }

    /**
     * 跳转到编辑页面
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/editBidding")
    public ModelAndView editBidding(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("sales/contractFound/editBidding");
        String taskId = request.getParameter("taskId");

        String formDataJson_size = "";
        String formDataJson_chapter = "";
        String formDataJson_fication = "";
        String formDataJson_certificate = "";

        SalesContractFoundModel salesContractFoundModel = null;

        SalesContractFoundModel model = (SalesContractFoundModel) processService.getVariable(taskId, "salesContractFounds");

        if (model != null) {
            salesContractFoundModel = getService().get(model.getId());
        }

        //创建者
        SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
        request.setAttribute("systemUser", systemUser);
        //拼凑出tableGrid所需的JSON数据
        formDataJson_size = tableGridDataUnit_size(salesContractFoundModel, "update");
        formDataJson_chapter = tableGridDataUnit_chapter(salesContractFoundModel, "update");
        formDataJson_fication = tableGridDataUnit_fication(salesContractFoundModel, "update");
        formDataJson_certificate = tableGridDataUnit_certificate(salesContractFoundModel, "update");

        request.setAttribute("model", salesContractFoundModel);
        request.setAttribute("form_size", formDataJson_size);
        request.setAttribute("form_chapter", formDataJson_chapter);
        request.setAttribute("form_fication", formDataJson_fication);
        request.setAttribute("form_certificate", formDataJson_certificate);

        return mav;
    }

    /**
     * 预览页面
     * @param request
     * @param response
     * @param salesContractFoundModel
     * @return
     */
    @RequestMapping(value = "/nextPage")
    @ResponseBody
    public ModelAndView nextPage(HttpServletRequest request, HttpServletResponse response, SalesContractFoundModel salesContractFoundModel) {
        ModelAndView mav = new ModelAndView("sales/contractFound/nextPage");
        mav.addObject("model", salesContractFoundModel);
        //创建者
        SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
        List<SalesContractSizeModel> size = dataAnalyst_size(salesContractFoundModel, systemUser);
        BigDecimal amount = new BigDecimal(0.00);
        for (SalesContractSizeModel s : size) {
            amount = amount.add(s.getApplyPrices());
        }
        request.setAttribute("amount", amount);
        List<SalesContractChapterModel> chapter = dataAnalyst_chapter(salesContractFoundModel, systemUser);
        List<Map<String, Object>> chapList = new ArrayList<Map<String, Object>>();
        request.setAttribute("chap", "no");
        for (SalesContractChapterModel c : chapter) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("borrowDate", c.getForecastBorrowDate());
            map.put("chapterId", c.getChapterId());
            map.put("returnDate", c.getForecastReturnDate());
            if (c.getForecastBorrowDate() != null && c.getForecastReturnDate() != null) {
                String name = getService().findChaperTime(c);
                if (name == "") {
                    map.put("creatname", "");
                } else {
                    map.put("creatname", name);
                    request.setAttribute("chap", "yes");
                }
            }
            chapList.add(map);
        }
        List<SalesContractQualificationModel> fication = dataAnalyst_fication(salesContractFoundModel, systemUser);
        List<Map<String, Object>> ficatList = new ArrayList<Map<String, Object>>();
        request.setAttribute("ficat", "no");
        for (SalesContractQualificationModel c : fication) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("forecastBorrowDate", c.getForecastBorrowDate());
            map.put("qualificationId", c.getQualificationId());
            map.put("forecastReturnDate", c.getForecastReturnDate());
            if (c.getForecastBorrowDate() != null && c.getForecastReturnDate() != null) {
                String name = getService().findQualificationTime(c);
                if (name == "") {
                    map.put("creatname", "");
                } else {
                    map.put("creatname", name);
                    request.setAttribute("ficat", "yes");
                }
            }
            ficatList.add(map);
        }
        List<SalesContractsCertificateModel> certificate = dataAnalyst_certificate(salesContractFoundModel, systemUser);
        mav.addObject("size", size);
        mav.addObject("chapter", chapList);
        mav.addObject("fication", ficatList);
        mav.addObject("certificate", certificate);

        return mav;
    }

    /**
     * 保存
     * @param request
     * @param response
     * @param salesContractFoundModel
     * @param errors
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/doSave")
    public String doSave(HttpServletRequest request, HttpServletResponse response, SalesContractFoundModel salesContractFoundModel, Errors errors) {
        String doFlag = request.getParameter("doFlag");
        String taskId = request.getParameter("taskId");
        String id = request.getParameter("id");

        try {
            if (errors.hasErrors()) {
                return FAIL;
            }
            //创建者
            SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
            //是否是行业营销总监
            int isTeamLeader = 0;
            //是否要周总审批
            int flat = 0;
            String teamLeader = sysStaffService.getTeamLeader(systemUser.getUserName());
            if (teamLeader.equals(systemUser.getUserName())) {
                isTeamLeader = 1;
            }
            SalesContractFoundModel oldsales = new SalesContractFoundModel();
            if ("update".equals(doFlag)) {
                if (!StringUtil.isEmpty(id)) {
                    oldsales = getService().get(Long.parseLong(id));
                }
            } else {
                //设置投标单ID
                long salesContractFoundModelId = IdentifierGeneratorImpl.generatorLong();
                salesContractFoundModel.setId(salesContractFoundModelId);
            }

            //创建时间
            salesContractFoundModel.setCreateTime(new Date());

            salesContractFoundModel.setCreator(systemUser.getUserName());
            salesContractFoundModel.setCreatorName(systemUser.getStaffName());
            salesContractFoundModel.setApplyFundsState("SP");
            List<SalesContractSizeModel> sizeList = dataAnalyst_size(salesContractFoundModel, systemUser);
            if (sizeList != null) {
                BigDecimal amount = new BigDecimal(0.00);
                for (SalesContractSizeModel s : sizeList) {//投标总金额
                    amount = amount.add(s.getApplyPrices());
                }
                salesContractFoundModel.setTotalFunds(amount);
            }

            List<SalesContractChapterModel> chapterList = dataAnalyst_chapter(salesContractFoundModel, systemUser);
            for (SalesContractChapterModel f : chapterList) {
                if (f.getChapterId() == 74 || f.getChapterId() == 80 || f.getChapterId() == 81) {
                    flat = 1;
                }
            }

            List<SalesContractQualificationModel> ficationList = dataAnalyst_fication(salesContractFoundModel, systemUser);
            List<SalesContractsCertificateModel> certificateList = dataAnalyst_certificate(salesContractFoundModel, systemUser);

            for (SalesContractQualificationModel f : ficationList) {
                if (f.getQualificationId() == 1 || f.getQualificationId() == 2) {
                    flat = 1;
                }
            }
            //流程变量
            Map<String, Object> variableMap = new HashMap<String, Object>();
            variableMap.put("salesContractFounds", salesContractFoundModel);
            variableMap.put("isTeamLeader", isTeamLeader);
            variableMap.put("isApproveFlag", flat);
            if ("update".equals(doFlag)) {
                boolean isResubmit = true;
                variableMap.put("is_re_submit", isResubmit); //重新提交流程变量，流程图里定义好的
                processService.handle(taskId, systemUser.getUserName(), variableMap, null, null);
                getService().updateAll(salesContractFoundModel, chapterList, ficationList, certificateList, sizeList, oldsales);
            } else {
                long processInstanceId = processService.nextValId();
                salesContractFoundModel.setProcessInstanceId(processInstanceId);
                processService.create(processInstanceId, "投标-" + salesContractFoundModel.getApplyFundsName(), systemUser.getUserName(), "tbsp", 1, variableMap, null, null, salesContractFoundModel.getApplyFundsName());
                getService().saveAll(salesContractFoundModel, chapterList, ficationList, certificateList, sizeList);
            }

            //创建流程
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return FAIL;
        }
    }

    /**
     * <code>tableGridDataUnit</code>
     * 拼接tableGridData所需要的JSON数据 －－往来款
     * @param salesContractFoundModel 投标实体
     * @return String
     */
    public String tableGridDataUnit_size(SalesContractFoundModel salesContractFoundModel, String showType) {
        String formDataJson = "";
        Map<String, Object> tableModel = new HashMap<String, Object>(); //表格数据模型
        tableModel.put("name", "editGridName_size"); //表格名称
        List<HashMap<String, Object>> rowNames = new ArrayList<HashMap<String, Object>>(); //表格中的列数据

        for (int i = 0; i < sizeColumns.length; i++) {
            HashMap<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("name", sizeColumns[i]); //字段名称，用于保存数据
            dataMap.put("label", sizeColumnNames[i]);//字段在页面上的显示名称
            dataMap.put("type", sizeColumnTypes[i]); //表格中输入字段的类型，目前只支持string,date
            dataMap.put("url", sizeColumnUrl[i]);
            if (salesContractFoundModel != null && showType.equals("detail"))
                dataMap.put("boolWrite", false); //表格中输入字段的类型，目前只支持string,date
            rowNames.add(dataMap);
        }

        tableModel.put("fieldList", rowNames);
        List<HashMap<String, Object>> rowDatas = new ArrayList<HashMap<String, Object>>(); //表格总的数据，一行数据对应一个map
        if (salesContractFoundModel != null) {
            List<SalesContractSizeModel> salesContractSizeModels = salesContractFoundModel.getSalesContractSizeModel();
            int rows = salesContractFoundModel.getSalesContractSizeModel().size();
            tableModel.put("defaultAmount", rows);//新建表格时，默认为0，如果是修改，应该表格数据的总行数。
            for (SalesContractSizeModel s : salesContractSizeModels) { //放入2行数据
                HashMap<String, Object> dataMap = new HashMap<String, Object>(); //因为先前在rowNames中定义表格有3列
                if (showType.equals("update")) {
                    dataMap.put(sizeColumns[0], s.getApplyPrices());
                } else {
                    //dataMap.put(columns[0], "￥" + getShowNumber(s.getPlanedReceiveAmount()));
                }
                dataMap.put(sizeColumns[1], s.getPayType());
                dataMap.put(sizeColumns[2], s.getPayDesc());
                rowDatas.add(dataMap);
            }
        } else {
            tableModel.put("defaultAmount", 0);//新建表格时，默认为0，如果是修改，应该表格数据的总行数。
        }

        tableModel.put("dataList", rowDatas);
        ObjectMapper objectMapper = JacksonJsonMapper.getInstance();
        try {
            formDataJson = objectMapper.writeValueAsString(tableModel);
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return formDataJson;
    }

    /**
     * <code>tableGridDataUnit_chapter</code>
     * 拼接tableGridData所需要的JSON数据 －－章管理
     * @param salesContractFoundModel 投标实体
     * @return String
     */
    public String tableGridDataUnit_chapter(SalesContractFoundModel salesContractFoundModel, String showType) {
        String formDataJson = "";
        Map<String, Object> tableModel = new HashMap<String, Object>(); //表格数据模型
        tableModel.put("name", "editGridName_chapter"); //表格名称
        List<HashMap<String, Object>> rowNames = new ArrayList<HashMap<String, Object>>(); //表格中的列数据

        for (int i = 0; i < chapterColumns.length; i++) {
            HashMap<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("name", chapterColumns[i]); //字段名称，用于保存数据
            dataMap.put("label", chapterColumnNames[i]);//字段在页面上的显示名称
            dataMap.put("type", chapterColumnTypes[i]); //表格中输入字段的类型，目前只支持string,date
            dataMap.put("url", chapterColumnUrl[i]);

            if (salesContractFoundModel != null && showType.equals("detail"))
                dataMap.put("boolWrite", false); //表格中输入字段的类型，目前只支持string,date
            rowNames.add(dataMap);
        }

        tableModel.put("fieldList", rowNames);
        List<HashMap<String, Object>> rowDatas = new ArrayList<HashMap<String, Object>>(); //表格总的数据，一行数据对应一个map
        if (salesContractFoundModel != null) {
            List<SalesContractChapterModel> salesContractChapterModels = salesContractFoundModel.getSalesContractChapterModel();
            int rows = salesContractFoundModel.getSalesContractChapterModel().size();
            tableModel.put("defaultAmount", rows);//新建表格时，默认为0，如果是修改，应该表格数据的总行数。
            for (SalesContractChapterModel s : salesContractChapterModels) { //放入2行数据
                HashMap<String, Object> dataMap = new HashMap<String, Object>(); //因为先前在rowNames中定义表格有3列
                if (showType.equals("update")) {
                    dataMap.put(chapterColumns[0], s.getChapterId());
                } else {
                    //dataMap.put(columns[0], "￥" + getShowNumber(s.getPlanedReceiveAmount()));
                }
                dataMap.put(chapterColumns[1], s.getForecastBorrowDate());
                dataMap.put(chapterColumns[2], s.getForecastReturnDate());
                rowDatas.add(dataMap);
            }
        } else {
            tableModel.put("defaultAmount", 0);//新建表格时，默认为0，如果是修改，应该表格数据的总行数。
        }

        tableModel.put("dataList", rowDatas);
        ObjectMapper objectMapper = JacksonJsonMapper.getInstance();
        try {
            formDataJson = objectMapper.writeValueAsString(tableModel);
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return formDataJson;
    }

    /**
     * <code>tableGridDataUnit_fication</code>
     * 拼接tableGridData所需要的JSON数据 －－资质
     * @param salesContractFoundModel 投标实体
     * @return String
     */
    public String tableGridDataUnit_fication(SalesContractFoundModel salesContractFoundModel, String showType) {
        String formDataJson = "";
        Map<String, Object> tableModel = new HashMap<String, Object>(); //表格数据模型
        tableModel.put("name", "editGridName_fication"); //表格名称
        List<HashMap<String, Object>> rowNames = new ArrayList<HashMap<String, Object>>(); //表格中的列数据

        for (int i = 0; i < ficationColumns.length; i++) {
            HashMap<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("name", ficationColumns[i]); //字段名称，用于保存数据
            dataMap.put("label", ficationColumnNames[i]);//字段在页面上的显示名称
            dataMap.put("type", ficationColumnTypes[i]); //表格中输入字段的类型，目前只支持string,date
            dataMap.put("url", ficationColumnUrl[i]); //表格中输入字段的路径，select用
            if (salesContractFoundModel != null && showType.equals("detail"))
                dataMap.put("boolWrite", false); //表格中输入字段的类型，目前只支持string,date
            rowNames.add(dataMap);
        }

        tableModel.put("fieldList", rowNames);
        List<HashMap<String, Object>> rowDatas = new ArrayList<HashMap<String, Object>>(); //表格总的数据，一行数据对应一个map
        if (salesContractFoundModel != null) {
            List<SalesContractQualificationModel> salesContractQualificationModels = salesContractFoundModel.getSalesContractQualificationModel();
            int rows = salesContractFoundModel.getSalesContractQualificationModel().size();
            tableModel.put("defaultAmount", rows);//新建表格时，默认为0，如果是修改，应该表格数据的总行数。
            for (SalesContractQualificationModel s : salesContractQualificationModels) { //放入2行数据
                HashMap<String, Object> dataMap = new HashMap<String, Object>(); //因为先前在rowNames中定义表格有3列
                if (showType.equals("update")) {
                    dataMap.put(ficationColumns[0], s.getQualificationId());
                } else {
                    //dataMap.put(columns[0], "￥" + getShowNumber(s.getPlanedReceiveAmount()));
                }
                dataMap.put(ficationColumns[1], s.getForecastBorrowDate());
                dataMap.put(ficationColumns[2], s.getForecastReturnDate());
                // dataMap.put(ficationColumns[1], s.getQualificationName());
                rowDatas.add(dataMap);
            }
        } else {
            tableModel.put("defaultAmount", 0);//新建表格时，默认为0，如果是修改，应该表格数据的总行数。
        }

        tableModel.put("dataList", rowDatas);
        ObjectMapper objectMapper = JacksonJsonMapper.getInstance();
        try {
            formDataJson = objectMapper.writeValueAsString(tableModel);
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return formDataJson;
    }

    /**
     * <code>tableGridDataUnit_certificate</code>
     * 拼接tableGridData所需要的JSON数据 －－证书
     * @param salesContractFoundModel 投标实体
     * @return String
     */
    public String tableGridDataUnit_certificate(SalesContractFoundModel salesContractFoundModel, String showType) {
        String formDataJson = "";
        Map<String, Object> tableModel = new HashMap<String, Object>(); //表格数据模型
        tableModel.put("name", "editGridName_certificate"); //表格名称
        List<HashMap<String, Object>> rowNames = new ArrayList<HashMap<String, Object>>(); //表格中的列数据

        for (int i = 0; i < certificateColumns.length; i++) {
            HashMap<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("name", certificateColumns[i]); //字段名称，用于保存数据
            dataMap.put("label", certificateColumnNames[i]);//字段在页面上的显示名称
            dataMap.put("type", certificateColumnTypes[i]); //表格中输入字段的类型，目前只支持string,date
            dataMap.put("url", certificateColumnUrl[i]);

            if (salesContractFoundModel != null && showType.equals("detail"))
                dataMap.put("boolWrite", false); //表格中输入字段的类型，目前只支持string,date
            rowNames.add(dataMap);
        }

        tableModel.put("fieldList", rowNames);
        List<HashMap<String, Object>> rowDatas = new ArrayList<HashMap<String, Object>>(); //表格总的数据，一行数据对应一个map
        if (salesContractFoundModel != null) {
            List<SalesContractsCertificateModel> salesContractsCertificateModels = salesContractFoundModel.getSalesContractsCertificateModel();
            int rows = salesContractFoundModel.getSalesContractsCertificateModel().size();
            tableModel.put("defaultAmount", rows);//新建表格时，默认为0，如果是修改，应该表格数据的总行数。
            for (SalesContractsCertificateModel s : salesContractsCertificateModels) { //放入2行数据
                HashMap<String, Object> dataMap = new HashMap<String, Object>(); //因为先前在rowNames中定义表格有3列
                if (showType.equals("update")) {
                    dataMap.put(certificateColumns[0], s.getCertificateType());
                } else {
                    //dataMap.put(columns[0], "￥" + getShowNumber(s.getPlanedReceiveAmount()));
                }
                dataMap.put(certificateColumns[1], s.getCertificateNum());
                rowDatas.add(dataMap);
            }
        } else {
            tableModel.put("defaultAmount", 0);//新建表格时，默认为0，如果是修改，应该表格数据的总行数。
        }

        tableModel.put("dataList", rowDatas);
        ObjectMapper objectMapper = JacksonJsonMapper.getInstance();
        try {
            formDataJson = objectMapper.writeValueAsString(tableModel);
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return formDataJson;
    }

    /**
     * <code>receivePlansDataAnalyst</code>
     * 前台传入数据后进行|往来款|的数据的整理
     * @param SalesContractFoundModel 投标实体
     * @param systemUser 当前登录用户实体
     * @return List<SalesContractReceivePlanModel>
     * @since   2015年12月2日    liyx
     */
    @SuppressWarnings("unchecked")
    public List<SalesContractSizeModel> dataAnalyst_size(SalesContractFoundModel salesContractFoundModel, SystemUser systemUser) {
        List<SalesContractSizeModel> salesContractSizeList = null;

        ObjectMapper objectMapper = JacksonJsonMapper.getInstance();
        Map<String, Object> tableDataMap = null;
        String tableDataSize = salesContractFoundModel.getTableDataSize();
        if (tableDataSize != null && tableDataSize != "") {
            try {
                tableDataMap = objectMapper.readValue(tableDataSize, HashMap.class);
            } catch (JsonParseException e) {
                e.printStackTrace();
            } catch (JsonMappingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (tableDataMap != null && tableDataMap.size() > 0) {
                salesContractSizeList = new ArrayList<SalesContractSizeModel>();
                for (String key : tableDataMap.keySet()) {
                    logger.info("key= " + key + " and value= " + tableDataMap.get(key));
                    List<Object> gridDataList = (List<Object>) tableDataMap.get(key);
                    List<Map<String, Object>> gridList = new ArrayList<Map<String, Object>>();
                    for (int i = 0; (gridDataList != null && i < gridDataList.size()); i++) {//加了非空判断
                        Map<String, Object> map = CodeUtils.stringToMap(gridDataList.get(i).toString());//格式转换
                        if (map.size() != 0) {
                            gridList.add(map);
                        }
                    }
                    for (int i = 0; i < gridList.size(); i++) {
                        Map<String, Object> map = gridList.get(i);
                        SalesContractSizeModel salesContractSizeModel = new SalesContractSizeModel();
                        if (map.get("applyPrices").toString() != null) {
                            BigDecimal applyPrices = new BigDecimal(map.get("applyPrices").toString());
                            salesContractSizeModel.setApplyPrices(applyPrices);
                        } else {
                            salesContractSizeModel.setApplyPrices(new BigDecimal(0.00));
                        }
                        String currentMoneyUses = map.get("currentMoneyUses") == null ? "" : map.get("currentMoneyUses").toString();
                        String borrowingPayType = map.get("borrowingPayType") == null ? "" : map.get("borrowingPayType").toString();

                        salesContractSizeModel.setPayType(borrowingPayType);
                        salesContractSizeModel.setPayDesc(currentMoneyUses);
                        salesContractSizeModel.setSalesContractFoundModel(salesContractFoundModel);
                        salesContractSizeList.add(salesContractSizeModel);
                    }
                }
            }
        }
        return salesContractSizeList;
    }

    /**
     * <code>DataAnalyst_chapter</code>
     * 前台传入数据后进行|盖章|的数据的整理
     * @param SalesContractFoundModel 投标实体
     * @param systemUser 当前登录用户实体
     * @return List<SalesContractChapterModel>
     * @since   2015年12月2日    liyx
     */
    @SuppressWarnings("unchecked")
    public List<SalesContractChapterModel> dataAnalyst_chapter(SalesContractFoundModel salesContractFoundModel, SystemUser systemUser) {
        List<SalesContractChapterModel> salesContractChapterList = null;

        ObjectMapper objectMapper = JacksonJsonMapper.getInstance();
        Map<String, Object> tableDataMap = null;
        String tableDataChapter = salesContractFoundModel.getTableDataChapter();
        if (tableDataChapter != null && tableDataChapter != "") {
            try {
                tableDataMap = objectMapper.readValue(tableDataChapter, HashMap.class);
            } catch (JsonParseException e) {
                e.printStackTrace();
            } catch (JsonMappingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (tableDataMap != null && tableDataMap.size() > 0) {
                salesContractChapterList = new ArrayList<SalesContractChapterModel>();
                for (String key : tableDataMap.keySet()) {
                    logger.info("key= " + key + " and value= " + tableDataMap.get(key));
                    List<Object> gridDataList = (List<Object>) tableDataMap.get(key);
                    List<Map<String, Object>> gridList = new ArrayList<Map<String, Object>>();
                    for (int i = 0; (gridDataList != null && i < gridDataList.size()); i++) {//加了非空判断
                        Map<String, Object> map = CodeUtils.stringToMap(gridDataList.get(i).toString());//格式转换
                        if (map.size() != 0) {
                            gridList.add(map);
                        }
                    }
                    for (int i = 0; i < gridList.size(); i++) {
                        SalesContractChapterModel salesContractChapterModel = new SalesContractChapterModel();
                        Map<String, Object> map = gridList.get(i);
                        String borrowingPayType = map.get("chopType") == null ? "" : map.get("chopType").toString();
                        Date borrowDate = new Date();
                        Date returnDate = new Date();
                        if (map.get("borrowDate") != null) {
                            String borrowDateString = map.get("borrowDate").toString();
                            borrowDate = DateUtils.convertStringToDate(borrowDateString, DatePattern);
                            salesContractChapterModel.setForecastBorrowDate(borrowDate);
                        }
                        if (map.get("returnDate") != null) {
                            String returnDateString = map.get("returnDate").toString();
                            returnDate = DateUtils.convertStringToDate(returnDateString, DatePattern);
                            salesContractChapterModel.setForecastReturnDate(returnDate);
                        }
                        salesContractChapterModel.setCreatorName(systemUser.staffName);
                        salesContractChapterModel.setChapterId(Long.parseLong(borrowingPayType));//by tigq
                        salesContractChapterModel.setSalesContractFoundModel(salesContractFoundModel);
                        salesContractChapterList.add(salesContractChapterModel);
                    }
                }
            }
        }
        return salesContractChapterList;
    }

    /**
     * <code>DataAnalyst_fication</code>
     * 前台传入数据后进行|资质|的数据的整理
     * @param SalesContractFoundModel 投标实体
     * @param systemUser 当前登录用户实体
     * @return List<SalesContractQualificationModel>
     * @since   2015年12月2日    liyx
     */
    @SuppressWarnings("unchecked")
    public List<SalesContractQualificationModel> dataAnalyst_fication(SalesContractFoundModel salesContractFoundModel, SystemUser systemUser) {
        List<SalesContractQualificationModel> salesContractQualificationList = null;

        ObjectMapper objectMapper = JacksonJsonMapper.getInstance();
        Map<String, Object> tableDataMap = null;
        String tableDataFication = salesContractFoundModel.getTableDataQualification();
        if (tableDataFication != null && tableDataFication != "") {
            try {
                tableDataMap = objectMapper.readValue(tableDataFication, HashMap.class);
            } catch (JsonParseException e) {
                e.printStackTrace();
            } catch (JsonMappingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (tableDataMap != null && tableDataMap.size() > 0) {
                salesContractQualificationList = new ArrayList<SalesContractQualificationModel>();
                for (String key : tableDataMap.keySet()) {
                    logger.info("key= " + key + " and value= " + tableDataMap.get(key));
                    List<Object> gridDataList = (List<Object>) tableDataMap.get(key);
                    List<Map<String, Object>> gridList = new ArrayList<Map<String, Object>>();
                    for (int i = 0; (gridDataList != null && i < gridDataList.size()); i++) {//加了非空判断
                        Map<String, Object> map = CodeUtils.stringToMap(gridDataList.get(i).toString());//格式转换
                        if (map.size() != 0) {
                            gridList.add(map);
                        }
                    }
                    for (int i = 0; i < gridList.size(); i++) {
                        Map<String, Object> map = gridList.get(i);
                        SalesContractQualificationModel salesContractQualificationModel = new SalesContractQualificationModel();
                        String applyFoundsID = map.get("aptitudeName") == null ? "" : map.get("aptitudeName").toString();
                        //String aptitudeType = map.get("aptitudeType") == null ? "" : map.get("aptitudeType").toString();
                        Date borrowDate = new Date();
                        Date returnDate = new Date();
                        if (map.get("borrowDate") != null) {
                            String borrowDateString = map.get("borrowDate").toString();
                            borrowDate = DateUtils.convertStringToDate(borrowDateString, DatePattern);
                            salesContractQualificationModel.setForecastBorrowDate(borrowDate);
                        }
                        if (map.get("returnDate") != null) {
                            String returnDateString = map.get("returnDate").toString();
                            returnDate = DateUtils.convertStringToDate(returnDateString, DatePattern);
                            salesContractQualificationModel.setForecastReturnDate(returnDate);
                        }
                        //Long.parseLong(applyFoundsID)
                        salesContractQualificationModel.setCreatorName(systemUser.staffName);
                        salesContractQualificationModel.setQualificationId(Long.parseLong(applyFoundsID));
                        //salesContractQualificationModel.setQualificationId(1l); //by tigq
                        // salesContractQualificationModel.setQualificationsType(aptitudeType);
                        salesContractQualificationModel.setSalesContractFoundModel(salesContractFoundModel);
                        salesContractQualificationList.add(salesContractQualificationModel);
                    }
                }
            }
        }
        return salesContractQualificationList;
    }

    /**
     * <code>DataAnalyst_certificate</code>
     * 前台传入数据后进行|证书|的数据的整理
     * @param SalesContractFoundModel 投标实体
     * @param systemUser 当前登录用户实体
     * @return List<SalesContractQualificationModel>
     * @since   2015年12月2日    liyx
     */
    @SuppressWarnings("unchecked")
    public List<SalesContractsCertificateModel> dataAnalyst_certificate(SalesContractFoundModel salesContractFoundModel, SystemUser systemUser) {
        List<SalesContractsCertificateModel> salesContractsCertificateList = null;

        ObjectMapper objectMapper = JacksonJsonMapper.getInstance();
        Map<String, Object> tableDataMap = null;
        String tableDataCertificate = salesContractFoundModel.getTableDataCertificate();
        if (tableDataCertificate != null && tableDataCertificate != "") {
            try {
                tableDataMap = objectMapper.readValue(tableDataCertificate, HashMap.class);
            } catch (JsonParseException e) {
                e.printStackTrace();
            } catch (JsonMappingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (tableDataMap != null && tableDataMap.size() > 0) {
                salesContractsCertificateList = new ArrayList<SalesContractsCertificateModel>();
                for (String key : tableDataMap.keySet()) {
                    logger.info("key= " + key + " and value= " + tableDataMap.get(key));
                    List<Object> gridDataList = (List<Object>) tableDataMap.get(key);
                    List<Map<String, Object>> gridList = new ArrayList<Map<String, Object>>();
                    for (int i = 0; (gridDataList != null && i < gridDataList.size()); i++) {//加了非空判断
                        Map<String, Object> map = CodeUtils.stringToMap(gridDataList.get(i).toString());//格式转换
                        if (map.size() != 0) {
                            gridList.add(map);
                        }
                    }
                    for (int i = 0; i < gridList.size(); i++) {
                        Map<String, Object> map = gridList.get(i);
                        String certificatesType = map.get("certificatesType") == null ? "" : map.get("certificatesType").toString();
                        String certificateNumStr = map.get("certificateNum") == null ? "" : map.get("certificateNum").toString();
                        int certificateNum = 0;
                        if (!StringUtil.isEmpty(certificateNumStr)) {
                            certificateNum = Integer.parseInt(certificateNumStr);
                        }

                        SalesContractsCertificateModel salesContractsCertificateModel = new SalesContractsCertificateModel();
                        salesContractsCertificateModel.setCertificateType(Long.valueOf(certificatesType));
                        salesContractsCertificateModel.setCertificateNum(certificateNum);
                        salesContractsCertificateModel.setSalesContractFoundModel(salesContractFoundModel);
                        salesContractsCertificateList.add(salesContractsCertificateModel);
                    }
                }
            }
        }
        return salesContractsCertificateList;
    }

    /**
     * 审批页面
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/foundApprove")
    @ResponseBody
    public ModelAndView foundApprove(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("sales/contractFound/foundApprove");
        String taskId = request.getParameter("taskId");
        String flowStep = request.getParameter("flowStep");
        String proId = request.getParameter("procInstId");
        request.setAttribute("page", "approve");//标志：显示审批按钮
        request.setAttribute("flowStep", flowStep);//标志：显示审批按钮
        SalesContractFoundModel salesContractFoundModel = (SalesContractFoundModel) processService.getVariable(taskId, "salesContractFounds");
        //审批日志
        List<ProcInstAppr> proInstLogList = processService.findProcInstApprLog(Long.parseLong(proId));
        mav.addObject("proInstLogList", proInstLogList);
        if (salesContractFoundModel != null) {
            SalesContractFoundModel model = getService().get(salesContractFoundModel.getId());
            mav.addObject("model", model);
            if ("hehuasp".equals(flowStep)) {
                List<SalesContractFoundModel> modelList = getService().getNotReturnPrice(salesContractFoundModel.getCreator(), salesContractFoundModel.getCusCustomerId());

                mav.addObject("modelList", modelList);
            }
        }

        return mav;
    }

    /**详情
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/detail")
    @ResponseBody
    public ModelAndView detail(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("sales/contractFound/foundApprove");
        String id = request.getParameter("id");
        SalesContractFoundModel salesContractFoundModel = getService().get(Long.parseLong(id));
        //审批日志
        List<ProcInstAppr> proInstLogList = processService.findProcInstApprLog(salesContractFoundModel.getProcessInstanceId());
        mav.addObject("proInstLogList", proInstLogList);

        mav.addObject("model", salesContractFoundModel);
        request.setAttribute("page", "detail");//标志：隐藏审批按钮
        return mav;
    }

    /**
     * 审批处理
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/doFoundApprove")
    @ResponseBody
    public ModelAndView doFoundApprove(HttpServletRequest request, HttpServletResponse response) {
        //System.out.println("doProjectApprove===============================");
        SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
        ModelAndView mav = new ModelAndView("sales/contractFound/manage");
        String taskId = request.getParameter("taskId");
        String flowStep = request.getParameter("flowStep");

        int isAgree = Integer.parseInt(request.getParameter("isAgree") == null ? "1" : request.getParameter("isAgree"));
        String remark = request.getParameter("remark");
        String foundId = request.getParameter("foundId");

        //if ("hehuasp".equals(flowStep)) {
        if (foundId != null && foundId != "") {
            SalesContractFoundModel foundModel = getService().get(Long.parseLong(foundId));
            //*****************审批不通过************//
            if (isAgree == 0) {
                foundModel.setApplyFundsState("CG");
                getService().saveOrUpdate(foundModel);
            }
        }
        //}
        Map<String, Object> variableMap = new HashMap<String, Object>();
        //重新提交处理
        if ("CXTJ".equals(flowStep)) {
            //**********插入流程变量合同是否为重新提交
            ////////*******************************-----重新提交
            if (foundId != null && foundId != "") {
                SalesContractFoundModel foundModel = getService().get(Long.parseLong(foundId));
                foundModel.setApplyFundsState("CXTJ");
                getService().saveOrUpdate(foundModel);
            }
            ////////********************************－－--

            boolean isResubmit = true;
            variableMap.put("is_re_submit", isResubmit); //重新提交流程变量，流程图里定义好的
            processService.handle(taskId, systemUser.getUserName(), variableMap, null, null);
        } else if ("zhangjqsp".equals(flowStep)) {
            int flat = Integer.parseInt(processService.getVariable(taskId, "isApproveFlag").toString());
            if (flat == 0) {//判断是否要周总审批，不需要则改状态
                if (isAgree == 1) {
                    SalesContractFoundModel foundModel = getService().get(Long.parseLong(foundId));
                    foundModel.setApplyFundsState("TGSP");
                    foundModel.setPayTime(new Date());
                    getService().saveOrUpdate(foundModel);
                }
            }
            processService.handle(taskId, systemUser.getUserName(), isAgree, remark, null, null, null);
        } else if ("zhoushusp".equals(flowStep)) {
            if (isAgree == 1) {
                SalesContractFoundModel foundModel = getService().get(Long.parseLong(foundId));
                foundModel.setApplyFundsState("TGSP");
                foundModel.setPayTime(new Date());
                getService().saveOrUpdate(foundModel);
            }
            processService.handle(taskId, systemUser.getUserName(), isAgree, remark, null, null, null);
        } else { //**********正常审批处理
            processService.handle(taskId, systemUser.getUserName(), isAgree, remark, null, null, null);
        }
        return mav;
    }

    /**投标回款页面
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/biddingReview/manage")
    public ModelAndView biddingManange(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("sales/contractFound/biddingReview/manage");
        return mav;
    }

    /**投标回款列表
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/biddingReview/getBiddingReviewList")
    public Map<String, Object> getBiddingReviewList(HttpServletRequest request, HttpServletResponse response) {
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
        PaginationSupport paginationSupport = getService().findBiddingList(searchMap, this.pageNo * this.pageSize, this.pageSize);

        map.put("rows", paginationSupport.getItems());
        map.put("total", paginationSupport.getTotalCount());
        map.put("page", iDisplayStart);
        return map;
    }

    @RequestMapping(value = "/biddingReview/search")
    public ModelAndView biddingSearch(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("sales/contractFound/biddingReview/search");
        return mav;
    }

    /**详情
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/biddingReview/bidApprove")
    @ResponseBody
    public ModelAndView bidApprove(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("sales/contractFound/biddingReview/bidApprove");
        String id = request.getParameter("id");
        SalesContractFoundModel salesContractFoundModel = getService().get(Long.parseLong(id));
        List<SalesContractSizeModel> size = salesContractFoundModel.getSalesContractSizeModel();
        BigDecimal amount = new BigDecimal(0.00);
        for (SalesContractSizeModel s : size) {
            amount = amount.add(s.getApplyPrices());
        }
        request.setAttribute("amount", amount);
        mav.addObject("model", salesContractFoundModel);
        return mav;
    }

    @ResponseBody
    @RequestMapping(value = "/biddingReview/doBidApprove")
    public Map<String, Object> doBidApprove(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();
        String id = request.getParameter("foundId");
        SalesContractFoundModel salesContractFoundModel = getService().get(Long.parseLong(id));
        String returnPrice = request.getParameter("returnPrice");
        if (returnPrice != "") {
            salesContractFoundModel.setReturnPrice(new BigDecimal(returnPrice));
        }
        String contractId = request.getParameter("contractCode");
        if (contractId != "") {
            SalesContractModel sales = salesContractService.get(Long.parseLong(contractId));
            salesContractFoundModel.setContractCode(sales.getContractCode());
            salesContractFoundModel.setContractName(sales.getContractName());
        }

        try {
            getService().create(salesContractFoundModel);
            map.put("message", "true");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("message", "保存失败。");
        }

        return map;
    }

    /**
     * <code>getSalesContractByCode</code>
     * 根据Code 查合同Name
     * @param request
     * @param response
     * @return
     * @since   2014年12月25日    guokemenng
     */
    @ResponseBody
    @RequestMapping(value = "/biddingReview/getSalesContractByCode")
    public Map<String, Object> getSalesContractByCode(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        return getService().getSalesContractByCode(id);
    }

    /**
     * <code>getSalesContractByName</code>
     * 合同列表
     * @param request
     * @param response
     * @return
     * @since   2014年12月25日    guokemenng
     */
    @ResponseBody
    @RequestMapping(value = "/biddingReview/getSalesContract")
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

        //销售部门只能选自己的合同
        SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
        SysStaff staff = staffService.get(systemUser.getUserId());
        String orgCode = staff.getSysStaffOrgs().get(0).getSysOrgnization().getOrgCode();
        if (orgCode.indexOf("1101") >= 0 || orgCode.indexOf("1102") >= 0 || orgCode.indexOf("1103") >= 0) {
            detachedCriteria.add(Restrictions.eq(SalesContractModel.CREATOR, Long.parseLong(systemUser.getUserId())));
        }

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

    /**修改往来款金额页面
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/sizeAmount")
    public ModelAndView getSizeAmount(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("sales/contractFound/sizeAmount");
        String id = request.getParameter("id");
        String num = request.getParameter("num");
        request.setAttribute("num", num);
        SalesContractSizeModel size = salesContractSizeService.get(Long.parseLong(id));
        mav.addObject("model", size);
        return mav;
    }

    /**保存金额修改
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/doSizeAmount")
    public String doSizeAmount(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        String amount = request.getParameter("amount");
        SalesContractSizeModel size = salesContractSizeService.get(Long.parseLong(id));
        size.setApplyPrices(new BigDecimal(amount));
        try {
            salesContractSizeService.saveOrUpdate(size);
            return "success";
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "false";
        }
    }

    /**下拉框查询
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/findSalesType")
    public List<Map<String, Object>> findSalesType(HttpServletRequest request, HttpServletResponse response) {
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        String type = request.getParameter("type");
        mapList = getService().findSalesType(type);
        return mapList;
    }
}
