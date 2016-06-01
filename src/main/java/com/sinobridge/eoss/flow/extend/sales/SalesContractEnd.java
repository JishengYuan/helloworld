/*
 * FileName: SalesContractEnd.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.flow.extend.sales;

import java.util.Date;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinobridge.eoss.bpm.common.constants.ProcessConstants;
import com.sinobridge.eoss.bpm.model.ProcessInst;
import com.sinobridge.eoss.bpm.service.autotask.AutoTask;
import com.sinobridge.eoss.sales.contract.constant.SalesContractConstant;
import com.sinobridge.eoss.sales.contract.dao.SalesContractDao;
import com.sinobridge.eoss.sales.contract.model.SalesContractModel;
import com.sinobridge.eoss.sales.contract.service.SalesContractService;
import com.sinobridge.eoss.sales.contract.service.SalesContractStatusService;
import com.sinobridge.systemmanage.util.Constants;

/**
 * <p>
 * Description: 
 * </p>
 *
 * @author vermouth
 * @version 1.0

 * <p>
 * History: 
 *
 * Date                     Author         Version     Description
 * ---------------------------------------------------------------------------------
 * 2016年3月7日 下午3:02:33          vermouth        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Service
@Transactional
public class SalesContractEnd implements AutoTask {

    private final SalesContractStatusService salesContractStatusService = Constants.WEB_APPLICATION_CONTEXT.getBean(SalesContractStatusService.class);
    private final SalesContractService salesContractService = Constants.WEB_APPLICATION_CONTEXT.getBean(SalesContractService.class);
    private final SalesContractDao salesContractDao = Constants.WEB_APPLICATION_CONTEXT.getBean(SalesContractDao.class);
    private Expression value;

    /* 相孚用合同通过审批后，改状态
     * @see org.activiti.engine.delegate.JavaDelegate#execute(org.activiti.engine.delegate.DelegateExecution)
     */
    @Override
    public void execute(DelegateExecution delegateTask) throws Exception {
        ProcessInst processInst = (ProcessInst) delegateTask.getVariable(ProcessConstants.V_PROC_INST);
        long procInstId = processInst.getId();
        SalesContractModel contractModel = salesContractService.findContractModel(procInstId);
        //根据流程工单ID找到对应合同状态实体
        // SalesContractStatusModel salesContractStatusModel = salesContractStatusService.findContractStatusByChangePId(procInstId);
        //创建流程的时候查不出salesContractStatusModel，所以创建的时候在insert salesContractStatusModel时直接update状态表中的盖章当前任务节点名
        if (contractModel != null) {
            contractModel.setContractState(SalesContractConstant.CONTRACT_STATE_TGSH);
            contractModel.setSalesStartDate(new Date());//添加合同生效时间
            int flat = salesContractDao.findsaleProduct(contractModel.getId());
            if (flat == 1) {
                salesContractDao.updateContractOrder(contractModel.getId());//有关联备货的订单状态置为采购中

            }
            salesContractDao.update(contractModel);
        }

    }

    /**
     * @return the value
     */
    public Expression getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(Expression value) {
        this.value = value;
    }

}
