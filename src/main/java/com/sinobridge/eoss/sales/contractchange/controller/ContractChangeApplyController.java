/*
 * FileName: SalesContractController.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.sales.contractchange.controller;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.ehcache.Ehcache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sinobridge.base.core.controller.DefaultBaseController;
import com.sinobridge.base.core.hibernate.id.IdentifierGeneratorImpl;
import com.sinobridge.eoss.bpm.common.constants.ProcessConstants;
import com.sinobridge.eoss.bpm.service.ProcInstAppr;
import com.sinobridge.eoss.bpm.service.process.ProcessService;
import com.sinobridge.eoss.business.order.model.BusinessOrderModel;
import com.sinobridge.eoss.sales.contract.constant.SalesContractConstant;
import com.sinobridge.eoss.sales.contract.model.SalesContractModel;
import com.sinobridge.eoss.sales.contract.model.SalesContractStatusModel;
import com.sinobridge.eoss.sales.contract.service.SalesContractService;
import com.sinobridge.eoss.sales.contract.service.SalesContractStatusService;
import com.sinobridge.eoss.sales.contractchange.model.ContractChangeApplyModel;
import com.sinobridge.eoss.sales.contractchange.service.ContractChangeApplyService;
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
 * @author 3unshine
 * @version 1.0

 * <p>
 * History: 
 *
 * Date                     Author         Version     Description
 * ---------------------------------------------------------------------------------
 * 2014年7月23日 上午10:27:56          3unshine        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Controller
@RequestMapping(value = "/sales/contractchange")
public class ContractChangeApplyController extends DefaultBaseController<ContractChangeApplyModel, ContractChangeApplyService> {
    String pattern = "yyyy-MM-dd";
    @Autowired
    private SalesContractService salesContractService;
    @Autowired
    private ProcessService processService;
    @Autowired
    private SalesContractStatusService salesContractStatusService;

    @Autowired
    SalesContractStatusService contractStatusService;

    private final static Ehcache systemUserCache = Constants.CACHE_MANAGER.getEhcache("systemUser");

    /**
     * <code>apply</code>
     * 跳转到apply页面
     * @param request
     * @param response
     * @return
     * @since 2014年7月23日     3unshine
     */
    @RequestMapping(value = "/apply")
    public ModelAndView apply(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("sales/contractchange/apply");
        String id = request.getParameter("id");
        SalesContractModel salesContract = salesContractService.get(Long.parseLong(id));

        SalesContractStatusModel statusModel = contractStatusService.findContractStatusByContractId(Long.parseLong(id));

        mav.addObject("statusModel", statusModel);
        mav.addObject("model", salesContract);
        return mav;
    }

    /**
     * <code>findSalesContractId</code>
     * 查询合同订单关联关系
     * @param request
     * @param response
     * @return
     * @since 2015年3月27日    wangya
     */
    @ResponseBody
    @RequestMapping(value = "/findSalesContractId")
    public String findSalesContractId(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("salesContractId");
        int flat = getService().findSalesContractId(id);
        if (flat == 0) {
            return SUCCESS;
        } else {
            return FAIL;
        }
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
    public String doSaveOrUpdate(HttpServletRequest request, HttpServletResponse response, ContractChangeApplyModel contractChangeApplyModel) {
        try {
            //创建者
            SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
            long id = IdentifierGeneratorImpl.generatorLong();
            contractChangeApplyModel.setId(id);
            contractChangeApplyModel.setCreateTime(new Date());
            contractChangeApplyModel.setCreator(Long.parseLong(systemUser.getUserId()));
            getService().save(contractChangeApplyModel, systemUser);
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return FAIL;
        }
    }

    /**
     * <code>applyDetail</code>
     * 跳转到applyDetail页面(主要为合同变更审核提供页面)
     * @param request
     * @param response
     * @return
     * @since 2014年7月23日     3unshine
     */
    @RequestMapping(value = "/applyDetail")
    public ModelAndView applyDetail(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("sales/contractchange/applyDetail");
        ContractChangeApplyModel contractChangeApplyModel = new ContractChangeApplyModel();
        long contractId;
        String taskId = request.getParameter("taskId");
        String procInstId = request.getParameter("procInstId");
        if (!StringUtil.isEmpty(taskId))
            request.setAttribute("taskId", taskId);
        String contractIdString = request.getParameter("id");
        //如果contractId为空则是从流程过来的，不为空就是查看申请详情
        if (StringUtil.isEmpty(contractIdString)) {
            //            contractChangeApplyModel = (ContractChangeApplyModel) processService.getVariable(taskId, "contractChangeApplyModel");
            contractChangeApplyModel = getService().getContractChangeApplyModelByProcInstId(Long.parseLong(procInstId));
            contractId = contractChangeApplyModel.getSaleContractId();
        } else {
            contractId = Long.parseLong(contractIdString);
            contractChangeApplyModel = getService().getContractChangeApplyModelByContractId(contractId);
        }
        String flowStep = request.getParameter("flowStep");
        if (!StringUtil.isEmpty(flowStep))
            request.setAttribute("flowStep", flowStep);
        if (!StringUtil.isEmpty(procInstId))
            request.setAttribute("procInstId", procInstId);
        mav.addObject("contractChangeApplyModel", contractChangeApplyModel);
        SalesContractModel salesContract = salesContractService.get(contractId);
        SalesContractStatusModel salesContractStatusModel = salesContractStatusService.findContractStatusByContractId(contractId);
        mav.addObject("salesContractStatusModel", salesContractStatusModel);
        //审批日志
        List<ProcInstAppr> proInstLogList = processService.findProcInstApprLog(contractChangeApplyModel.getProcessInstanceId());
        mav.addObject("proInstLogList", proInstLogList);
        mav.addObject("model", salesContract);

        //订单信息
        Set<BusinessOrderModel> orderSet = salesContract.getBusinessOrderModel();
        mav.addObject("orderModel", orderSet);

        Set<String> userSet = processService.getUserIdSetByProcInst(contractChangeApplyModel.getProcessInstanceId(), ProcessConstants.ROLE_ASSIGNEE);
        Iterator<String> it = userSet.iterator();
        while (it.hasNext()) {
            String str = it.next();
            mav.addObject("approName", ((SysStaff) systemUserCache.get(str).getObjectValue()).getStaffName());

        }

        return mav;
    }

    /**
     * <code>handleFlow</code>
     * 处理流程
     * @param request
     * @param response
     * @return
     * @since   2014年7月24日    3unshine
     */
    @RequestMapping(value = "/handleFlow")
    public ModelAndView handleFlow(HttpServletRequest request, HttpServletResponse response, ContractChangeApplyModel contractChangeApplyModel) {
        ModelAndView mav = new ModelAndView("sales/contract/manage");
        String taskId = request.getParameter("taskId");
        String procInstId = request.getParameter("procInstId");
        int isAgree = Integer.parseInt(request.getParameter("isAgree") == null ? "1" : request.getParameter("isAgree"));
        String remark = request.getParameter("remark");
        String flowStep = request.getParameter("flowStep");
        //创建者
        SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
        //流程处理service
        try {

            SalesContractStatusModel salesContractStatusModel = salesContractStatusService.findContractStatusByChangePId(Long.parseLong(procInstId));
            if (isAgree == 0) {
                salesContractStatusModel.setChangeStatus(SalesContractConstant.CONTRACT_CHANGE_UNPASSSTATE);
                salesContractStatusService.update(salesContractStatusModel);
            } else if (flowStep.equals("BGSP2") && isAgree == 1) {
                salesContractStatusModel.setChangeStatus(SalesContractConstant.CONTRACT_CHANGE_ENDSTATE);
                salesContractStatusService.update(salesContractStatusModel);

                //变更通过给商务采购员、运营中心发邮件提醒
                SalesContractModel salesContract = salesContractService.get(salesContractStatusModel.getSaleContractId());
                getService().sendEmail(salesContract, contractChangeApplyModel.getRemark());

                //假如类型为废弃该合同 则改变合同状态
                if (contractChangeApplyModel.getChangeType() == 3) {
                    salesContract.setContractState(SalesContractConstant.CONTRACT_STATE_FQ);
                    salesContractService.update(salesContract);
                }

            }

            getService().handleFlow(contractChangeApplyModel, systemUser, procInstId, taskId, isAgree, remark);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mav;
    }
}
