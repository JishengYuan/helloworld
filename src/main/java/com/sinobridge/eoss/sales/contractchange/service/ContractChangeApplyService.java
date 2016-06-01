/*
 * FileName: ContractChangeApplyService.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.sales.contractchange.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinobridge.base.core.service.DefaultBaseService;
import com.sinobridge.eoss.bpm.service.process.ProcessService;
import com.sinobridge.eoss.business.order.utils.SendEmailUtils;
import com.sinobridge.eoss.sales.contract.constant.SalesContractConstant;
import com.sinobridge.eoss.sales.contract.dao.SalesContractStatusDao;
import com.sinobridge.eoss.sales.contract.model.SalesContractModel;
import com.sinobridge.eoss.sales.contract.model.SalesContractStatusModel;
import com.sinobridge.eoss.sales.contractchange.dao.ContractChangeApplyDao;
import com.sinobridge.eoss.sales.contractchange.model.ContractChangeApplyModel;
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
 * 2014年7月23日 下午2:47:16          3unshine        1.0         To create
 * </p>
 *
 * @since
 * @see
 */
@Service
@Transactional
public class ContractChangeApplyService extends DefaultBaseService<ContractChangeApplyModel, ContractChangeApplyDao> {
    @Autowired
    private ProcessService processService;
    @Autowired
    private SalesContractStatusDao salesContractStatusDao;

    /**
     * @param contractChangeApplyModel
     * @param systemUser
     */
    public void save(ContractChangeApplyModel contractChangeApplyModel, SystemUser systemUser) {
        creatProcInst(contractChangeApplyModel, systemUser);
        SalesContractStatusModel salesContractStatusModel = salesContractStatusDao.findContractStatusByContractId(contractChangeApplyModel.getSaleContractId());
        if (salesContractStatusModel != null) {
            salesContractStatusModel.setChangeStatus(SalesContractConstant.CONTRACT_CHANGE_APPLYSTATE);
            salesContractStatusDao.update(salesContractStatusModel);
        }
        getDao().save(contractChangeApplyModel);
    }

    /**
     * <code>creatProcInst</code>
     * 创建工单
     * @param tableData
     * @param tableData
     * @since 2014年7月23日 3unshine
     */
    public long creatProcInst(ContractChangeApplyModel contractChangeApplyModel, SystemUser systemUser) {
        Map<String, Object> variableMap = new HashMap<String, Object>();
        long processInstanceId = processService.nextValId();
        contractChangeApplyModel.setProcessInstanceId(processInstanceId);
        variableMap.put("contractChangeApplyModel", contractChangeApplyModel);
        Long[] procInstId = processService.create(processInstanceId, contractChangeApplyModel.getSalesContractName() + SalesContractConstant.CONTRACT_CHANGE_PROCTITLE, systemUser.getUserName(), SalesContractConstant.CONTRACT_CHANGE_PROCDEFKEY, 1, variableMap, null, null, null);
        System.out.println("创建工单成功：" + procInstId[0]);
        return procInstId[0];
    }

    /**
     * @param contractChangeApplyModel
     * @param systemUser
     * @param procInstId
     * @param taskId
     * @param isAgree
     * @param remark
     */
    public void handleFlow(ContractChangeApplyModel contractChangeApplyModel, SystemUser systemUser, String procInstId, String taskId, int isAgree, String remark) {
        String userId = systemUser.getUserName();
        Map<String, Object> variableMap = new HashMap<String, Object>();
        processService.handle(taskId, userId, isAgree, remark, variableMap, null, null);
    }

    /**
     * <code>getContractChangeApplyModelByContractId</code>
     * 根据合同ID得到合同变更申请
     * @param contractId
     * @return ContractChangeApplyModel
     */
    public ContractChangeApplyModel getContractChangeApplyModelByContractId(Long contractId) {
        return getDao().getContractChangeApplyModelByContractId(contractId);
    }

    /**
     * <code>getContractChangeApplyModelByProcInstId</code>
     * 根据工单ID 得到变更实体
     * @param procInstId
     * @return
     * @since   2014年11月28日    guokemenng
     */
    public ContractChangeApplyModel getContractChangeApplyModelByProcInstId(Long procInstId) {

        ContractChangeApplyModel applyModel = null;

        String hql = "from ContractChangeApplyModel where processInstanceId = ?";
        List<ContractChangeApplyModel> applyModelList = getDao().find(hql, new Object[] { procInstId });
        if (applyModelList.size() > 0) {
            applyModel = applyModelList.get(0);
        }
        return applyModel;
    }

    /**
     * <code>sendEmail</code>
     * 变更通过给商务采购员、运营中心发邮件提醒
     * @param salesContract
     * @since   2015年1月21日    guokemenng
     */
    public void sendEmail(SalesContractModel salesContract, String remark) {
        //给商务发邮件
        if (salesContract != null) {
            String buyer = salesContract.getOrderProcessor();
            if (!StringUtil.isEmpty(buyer)) {
                String[] buyers = buyer.split(",");
                for (String s : buyers) {
                    if (!StringUtil.isEmpty(s)) {
                        processService.sendEmail("系统提醒:合同变更审批通过", "合同:" + salesContract.getContractName() + "[" + salesContract.getContractCode() + "]变更审批通过", null, null, s, null, null);
                    }
                }
            }

            if (SendEmailUtils.isSend()) {
                processService.sendEmail("系统提醒:合同变更审批通过", "合同:" + salesContract.getContractName() + "[" + salesContract.getContractCode() + "]变更审批通过,变更内容:" + remark, null, null, "tianj", null, null);
                processService.sendEmail("系统提醒:合同变更审批通过", "合同:" + salesContract.getContractName() + "[" + salesContract.getContractCode() + "]变更审批通过,变更内容:" + remark, null, null, "luanpl", null, null);
            }
        }
    }

    /**
     * @param id
     * @return
     */
    public int findSalesContractId(String id) {
        int flat = getDao().findSalesContractId(Long.parseLong(id));
        return flat;
    }

}
