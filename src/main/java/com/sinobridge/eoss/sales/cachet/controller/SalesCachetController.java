/*
 * FileName: SalesContractController.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.sales.cachet.controller;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.ehcache.Ehcache;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sinobridge.base.core.controller.DefaultBaseController;
import com.sinobridge.base.core.exception.SinoRuntimeException;
import com.sinobridge.base.core.hibernate.id.IdentifierGeneratorImpl;
import com.sinobridge.eoss.bpm.common.constants.ProcessConstants;
import com.sinobridge.eoss.bpm.service.ProcInstAppr;
import com.sinobridge.eoss.bpm.service.process.ProcessService;
import com.sinobridge.eoss.sales.cachet.model.SalesCachetModel;
import com.sinobridge.eoss.sales.cachet.service.SalesCachetService;
import com.sinobridge.eoss.sales.contract.model.SalesContractModel;
import com.sinobridge.eoss.sales.contract.service.SalesContractService;
import com.sinobridge.eoss.sales.contract.utils.CodeUtils;
import com.sinobridge.eoss.sales.contract.utils.FileUtils;
import com.sinobridge.eoss.sales.invoice.model.SalesInvoicePlanModel;
import com.sinobridge.systemmanage.model.SysStaff;
import com.sinobridge.systemmanage.service.FileService;
import com.sinobridge.systemmanage.util.Constants;
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
 * 2014年7月8日 上午10:27:56          3unshine        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Controller
@RequestMapping(value = "/sales/cachet")
public class SalesCachetController extends DefaultBaseController<SalesCachetModel, SalesCachetService> {
    String pattern = "yyyy-MM-dd";
    @Autowired
    private SalesContractService salesContractService;
    @Autowired
    private FileService fileService;
    @Autowired
    private ProcessService processService;

    private final static Ehcache systemUserCache = Constants.CACHE_MANAGER.getEhcache("systemUser");
    
    /**
     * <code>apply</code>
     * 跳转到apply页面
     * @param request
     * @param response
     * @return
     * @since 2014年7月8日     3unshine
     */
    @RequestMapping(value = "/apply")
    public ModelAndView apply(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("sales/cachet/apply");
        String id = request.getParameter("id");
        SalesContractModel salesContract = salesContractService.get(Long.parseLong(id));
        mav.addObject("model", salesContract);
        return mav;
    }

    /**
     * <code>reApply</code>
     * 跳转到reApply页面
     * @param request
     * @param response
     * @return
     * @since 2014年7月10日     3unshine
     */
    @RequestMapping(value = "/reApply")
    public ModelAndView reApply(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("sales/cachet/reApply");
        String flowFlag = request.getAttribute("flowFlag") + "";
        if (flowFlag != null && flowFlag != "")
            request.setAttribute("flowFlag", flowFlag);
        String id = request.getParameter("id") != null ? request.getParameter("id") : request.getAttribute("contractId") + "";
        String taskId = request.getAttribute("taskId") + "";
        if (taskId != null && taskId != "")
            request.setAttribute("taskId", taskId);
        String procInstId = request.getAttribute("procInstId") + "";
        if (procInstId != null && procInstId != "")
            request.setAttribute("procInstId", procInstId);
        SalesCachetModel salesCachetModel = (SalesCachetModel) request.getAttribute("salesCachetModel");
        //取出合同实体发送到前台页面
        if (id != null && !"".equals(id)) {
            SalesContractModel salesContract = salesContractService.get(Long.parseLong(id));
            mav.addObject("model", salesContract);
            //审批日志
            List<ProcInstAppr> proInstLogList = processService.findProcInstApprLog(salesCachetModel.getProcessInstanceId());
        }
        return mav;
    }

    /**
     * <code>applyDetail</code>
     * 跳转到applyDetail页面
     * @param request
     * @param response
     * @return
     * @since 2014年7月10日     3unshine
     */
    @RequestMapping(value = "/applyDetail")
    public ModelAndView applyDetail(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("sales/cachet/applyDetail");
        String isFlow = request.getAttribute("isFlow") + "";
        String taskId = request.getAttribute("taskId") + "";
        String procInstId = request.getAttribute("procInstId") + "";
        if (!StringUtil.isEmpty(taskId))
            request.setAttribute("taskId", taskId);
        if (!StringUtil.isEmpty(procInstId))
            request.setAttribute("procInstId", procInstId);
        long contractId = 0L;
        SalesCachetModel salesCachetModel = new SalesCachetModel();
        String idString = request.getParameter("id");
        if (StringUtil.isEmpty(idString)) {
            salesCachetModel = (SalesCachetModel) request.getAttribute("salesCachetModel");
            contractId = salesCachetModel.getSalesContractId();
            mav.addObject("cachetModel", salesCachetModel);
        } else {
            contractId = Long.parseLong(idString);
            DetachedCriteria detachedCriteria = DetachedCriteria.forClass(SalesCachetModel.class);
            detachedCriteria.add(Restrictions.eq(SalesCachetModel.CONTRACTID, contractId));
            detachedCriteria.addOrder(Order.desc("id"));
            salesCachetModel = getService().getSalesCachetModelByContractId(detachedCriteria);
        }
        
        Set<String> userSet = processService.getUserIdSetByProcInst(salesCachetModel.getProcessInstanceId(),ProcessConstants.ROLE_ASSIGNEE);
        Iterator<String> it = userSet.iterator();
        while(it.hasNext()){
            String str = it.next();
            mav.addObject("approName", ((SysStaff) systemUserCache.get(str).getObjectValue()).getStaffName());
            
        }
        //审批日志
        List<ProcInstAppr> proInstLogList = processService.findProcInstApprLog(salesCachetModel.getProcessInstanceId());
        mav.addObject("proInstLogList", proInstLogList);
        SalesContractModel salesContract = salesContractService.get(contractId);
        mav.addObject("model", salesContract);
        request.setAttribute("isFlow", isFlow);
        return mav;
    }

    /**
     * <code>fileUploadData</code>
     * AJAX获取附件
     * @param request
     * @param response
     * @return
     * @since 2014年7月8日     3unshine
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
            SalesCachetModel salesCachetModel = getService().getBysalesContractId(salesContractId);
            if (!StringUtil.isEmpty(salesCachetModel.getAttachIds())) {
                String[] attachIds = salesCachetModel.getAttachIds().split(",");
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

    /**
     * <code>doSave</code>
     * 保存更新实体
     * @param request
     * @param response
     * @param salesCachetModel
     * @return
     * @since 2014年5月27日    3unshine
     */
    @ResponseBody
    @RequestMapping(value = "/doSave")
    public String doSaveOrUpdate(HttpServletRequest request, HttpServletResponse response, SalesCachetModel salesCachetModel) {
        try {
            //创建者
            SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
            long id = IdentifierGeneratorImpl.generatorLong();
            salesCachetModel.setId(id);
            salesCachetModel.setCreateTime(new Date());
            salesCachetModel.setCreator(Long.parseLong(systemUser.getUserId()));
            getService().save(salesCachetModel, systemUser);
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return FAIL;
        }
    }

    /**
     * <code>SalesCachetFlowSteps</code>
     * 用于流程控制所需的4个URL中标示的枚举类
     * @since   2014年7月10日    3unshine
     */
    public enum SalesCachetFlowSteps {
        //SWZGGZSP  商务主管盖章审批
        //CXTJ 重新提交
        //HGXSCSP 合规性审查
        //ZKZYSP 质控专员审查
        //SHOW 查看权限
        SWZGGZSP, CXTJ,HGXSCSP, ZKZYSP, SHOW;
    }

    /**
     * <code>cachetFlowManage</code>
     * 用于流程控制所需的URL
     * @param request
     * @param response
     * @return
     * @since   2014年7月10日    3unshine
     */
    @RequestMapping(value = "/cachetFlowManage")
    public ModelAndView cachetFlowManage(HttpServletRequest request, HttpServletResponse response) {
        String flowStep = request.getParameter("flowStep");
        String taskId = request.getParameter("taskId");
        String procInstId = request.getParameter("procInstId");
        String isFlow = "isFlow";
//        SalesCachetModel salesCachetModel = (SalesCachetModel) processService.getVariable(taskId, "salesCachetModel");
        SalesCachetModel salesCachetModel = getService().getSalesCachetModelByProcessInstanceId(Long.parseLong(procInstId));
        request.setAttribute("salesCachetModel", salesCachetModel);
        if (salesCachetModel != null)
            request.setAttribute("contractId", salesCachetModel.getSalesContractId());
        request.setAttribute("taskId", taskId);
        request.setAttribute("procInstId", procInstId);
        request.setAttribute("flowStep", flowStep);
        switch (SalesCachetFlowSteps.valueOf(flowStep)) {
            case SWZGGZSP:
                request.setAttribute("isFlow", isFlow);
                return applyDetail(request, response);
            case CXTJ:
                request.setAttribute("isFlow", isFlow);
                request.setAttribute("flowFlag", "CXTJ");
                return reApply(request, response);
            case ZKZYSP:
                request.setAttribute("isFlow", isFlow);
                request.setAttribute("flowFlag", "ZKZYSP");
                return applyDetail(request, response);
            case HGXSCSP:
                request.setAttribute("isFlow", isFlow);
                request.setAttribute("flowFlag", "HGXSCSP");
                return applyDetail(request, response);
            case SHOW:
                return applyDetail(request, response);
        }
        return null;
    }

    /**
     * <code>handleFlow</code>
     * 处理流程
     * @param request
     * @param response
     * @return
     * @since   2014年7月16日    3unshine
     */
    @ResponseBody
    @RequestMapping(value = "/handleFlow")
    public String handleFlow(HttpServletRequest request, HttpServletResponse response, SalesCachetModel salesCachetModel) {
//        ModelAndView mav = new ModelAndView("sales/contract/manage");
        try {
            String taskId = request.getParameter("taskId");
            String flowFlag = request.getParameter("flowFlag");
            String procInstId = request.getParameter("procInstId");
            int isAgree = Integer.parseInt(request.getParameter("isAgree") == null ? "1" : request.getParameter("isAgree"));
            String remark = request.getParameter("remark");
            //创建者
            SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
            
            List<SalesInvoicePlanModel> salesInvoicePlanModels = (List<SalesInvoicePlanModel>) processService.getVariable(taskId, "salesInvoicePlanList");
            
            //流程处理service
            try {
                getService().handleFlow(salesInvoicePlanModels,flowFlag, salesCachetModel, systemUser, procInstId, taskId, isAgree, remark);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return SUCCESS;
        } catch (SinoRuntimeException e) {
            e.printStackTrace();
            return FAIL;
        }
    }
}
