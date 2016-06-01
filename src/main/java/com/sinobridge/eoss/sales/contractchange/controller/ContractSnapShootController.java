/*
 * FileName: SalesContractController.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.sales.contractchange.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sinobridge.base.core.controller.DefaultBaseController;
import com.sinobridge.base.core.utils.JacksonJsonMapper;
import com.sinobridge.eoss.bpm.service.ProcInstAppr;
import com.sinobridge.eoss.bpm.service.process.ProcessService;
import com.sinobridge.eoss.sales.contract.service.SalesContractService;
import com.sinobridge.eoss.sales.contract.service.SalesContractStatusService;
import com.sinobridge.eoss.sales.contract.utils.CodeUtils;
import com.sinobridge.eoss.sales.contract.utils.FileUtils;
import com.sinobridge.eoss.sales.contractchange.model.ContractRecivePlanSnapShootModel;
import com.sinobridge.eoss.sales.contractchange.model.ContractSnapShootModel;
import com.sinobridge.eoss.sales.contractchange.service.ContractSnapShootService;
import com.sinobridge.systemmanage.service.FileService;
import com.sinobridge.systemmanage.util.Global;
import com.sinobridge.systemmanage.util.StringUtil;
import com.sinobridge.systemmanage.vo.FileVo;
import com.sinobridge.systemmanage.vo.SystemUser;

/**
 * <p>
 * Description: 
 * </p>
 *
 * @author 3unshine
 * @version 1.0

 * <p>
 * History: 
 *
 * Date                     Author         Version     Description
 * ---------------------------------------------------------------------------------
 * 2014年8月7日 上午10:27:56          3unshine        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Controller
@RequestMapping(value = "/sales/contractSnapShoot")
public class ContractSnapShootController extends DefaultBaseController<ContractSnapShootModel, ContractSnapShootService> {
    String pattern = "yyyy-MM-dd";
    @Autowired
    private SalesContractService salesContractService;
    @Autowired
    private ProcessService processService;
    
    @Autowired
    private FileService fileService;
    
    @Autowired
    private SalesContractStatusService salesContractStatusService;
    private final String columns[] = { "planedReceiveAmount", "planedReceiveDate", "payCondition" };
    private final String columnNames[] = { "计划收款金额", "计划收款时间", "收款条件" };
    private final String columnTypes[] = { "string", "datetime", "string" };

    /**
     * <code>toDetail</code>
     * 跳转到合同快照历史版本页面
     * @param request
     * @param response
     * @return
     * @since 2014年8月7日     3unshine
     */
    @RequestMapping(value = "/toDetail")
    public ModelAndView toDetail(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("sales/contract/contractSnapShootDetail");
        String id = request.getParameter("id");
        String procInstId = request.getParameter("procInstId");
        String formDataJson = "";
        ContractSnapShootModel contractSnapShootModel = null;
        if (id != null && !"".equals(id)) {
            contractSnapShootModel = getService().get(Long.parseLong(id));
            mav.addObject("model", contractSnapShootModel);
            //审批日志
            List<ProcInstAppr> proInstLogList = processService.findProcInstApprLog(contractSnapShootModel.getProcessInstanceId());
            mav.addObject("proInstLogList", proInstLogList);
        } else {
            contractSnapShootModel = getService().getContractSnapShootsByProcInstId(Long.parseLong(procInstId));
            mav.addObject("model", contractSnapShootModel);
            //审批日志
            List<ProcInstAppr> proInstLogList = processService.findProcInstApprLog(contractSnapShootModel.getProcessInstanceId());
            mav.addObject("proInstLogList", proInstLogList);
        }
        //拼凑出tableGrid所需的JSON数据
        formDataJson = tableGridDataUnit(contractSnapShootModel, "detail");
        request.setAttribute("form", formDataJson);
        return mav;
    }

    /**
     * <code>tableGridDataUnit</code>
     * 拼接tableGridData所需要的JSON数据
     * @param salesContractModel 销售合同实体
     * @return String
     * @since   2014年6月23日    3unshine
     */
    public String tableGridDataUnit(ContractSnapShootModel contractSnapShootModel, String showType) {
        String formDataJson = "";
        Map<String, Object> tableModel = new HashMap<String, Object>(); //表格数据模型
        tableModel.put("name", "editGridName"); //表格名称
        List<HashMap<String, Object>> rowNames = new ArrayList<HashMap<String, Object>>(); //表格中的列数据

        for (int i = 0; i < columns.length; i++) {
            HashMap<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("name", columns[i]); //字段名称，用于保存数据
            dataMap.put("label", columnNames[i]);//字段在页面上的显示名称
            dataMap.put("type", columnTypes[i]); //表格中输入字段的类型，目前只支持string,date
            if (contractSnapShootModel != null && showType.equals("detail"))
                dataMap.put("boolWrite", false); //表格中输入字段的类型，目前只支持string,date
            rowNames.add(dataMap);
        }

        tableModel.put("fieldList", rowNames);
        List<HashMap<String, Object>> rowDatas = new ArrayList<HashMap<String, Object>>(); //表格总的数据，一行数据对应一个map
        if (contractSnapShootModel != null) {
            Set<ContractRecivePlanSnapShootModel> salesContractRecivePlanSnapShoots = contractSnapShootModel.getSalesContractRecivePlanSnapShoots();
            int rows = contractSnapShootModel.getSalesContractRecivePlanSnapShoots().size();
            tableModel.put("defaultAmount", rows);//新建表格时，默认为0，如果是修改，应该表格数据的总行数。
            for (ContractRecivePlanSnapShootModel s : salesContractRecivePlanSnapShoots) { //放入2行数据
                HashMap<String, Object> dataMap = new HashMap<String, Object>(); //因为先前在rowNames中定义表格有3列
                dataMap.put(columns[0], s.getPlanedReceiveAmount());
                dataMap.put(columns[1], s.getPlanedReceiveDate());
                dataMap.put(columns[2], s.getPayCondition());
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
     * <code>fileUploadData</code>
     *
     * @param request
     * @return
     * @since   2014年11月24日    guokemenng
     */
    @RequestMapping(value = "/fileUploadData")
    @ResponseBody
    public FileVo fileUploadData(HttpServletRequest request) {
        FileVo fileVo = FileUtils.creatFileUploadData();
        SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
        fileVo.setUsername(systemUser.getUserName());
        fileVo.setStaffname(systemUser.getStaffName());
        String flag = request.getParameter("fileUploadFlag");
        if (!flag.equals("add")) {
            long salesContractId = Long.parseLong(request.getParameter("salesContractId"));
            ContractSnapShootModel salesContractModel = getService().get(salesContractId);
            if (!StringUtil.isEmpty(salesContractModel.getAttachIds())) {
                String[] attachIds = salesContractModel.getAttachIds().split(",");
                Long[] ids = CodeUtils.stringArrayToLongArray(attachIds);
                fileVo.setValue(fileService.findSysAttachByIds(ids));
            }
        }
        if (flag.equals("detail")) {
            fileVo.setBoolRequire(false);
            fileVo.setBoolWrite(false);
        }
        return fileVo;
    }
    
}
