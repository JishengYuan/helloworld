/*
 * FileName: SalesContractChangeApplyUpdate.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.flow.extend.sales;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinobridge.eoss.bpm.common.constants.ProcessConstants;
import com.sinobridge.eoss.bpm.model.ProcessInst;
import com.sinobridge.eoss.bpm.service.autotask.AutoTask;
import com.sinobridge.eoss.sales.contract.constant.SalesContractConstant;
import com.sinobridge.eoss.sales.contract.model.SalesContractStatusModel;
import com.sinobridge.eoss.sales.contract.service.SalesContractStatusService;
import com.sinobridge.systemmanage.util.Constants;

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
 * 2014年7月23日 下午5:00:45          3unshine        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Service
@Transactional
public class SalesContractChangeApplyUpdate implements AutoTask {
    private final SalesContractStatusService salesContractStatusService = Constants.WEB_APPLICATION_CONTEXT.getBean(SalesContractStatusService.class);

    private Expression value;

    /* 合同变更申请通过后，通过流程的自动任务将合同状态表中的变更字段置为“变更申请通过”
     * @see org.activiti.engine.delegate.JavaDelegate#execute(org.activiti.engine.delegate.DelegateExecution)
     */
    @Override
    public void execute(DelegateExecution delegateTask) throws Exception {
        ProcessInst processInst = (ProcessInst) delegateTask.getVariable(ProcessConstants.V_PROC_INST);
        long procInstId = processInst.getId();
        //根据流程工单ID找到对应合同状态实体
        SalesContractStatusModel salesContractStatusModel = salesContractStatusService.findContractStatusByChangePId(procInstId);
        //创建流程的时候查不出salesContractStatusModel，所以创建的时候在insert salesContractStatusModel时直接update状态表中的盖章当前任务节点名
        if (salesContractStatusModel != null) {
            if(Boolean.parseBoolean(delegateTask.getVariable("APPR_counterSignNode2").toString())){
                salesContractStatusModel.setChangeStatus(SalesContractConstant.CONTRACT_CHANGE_ENDSTATE);
            } else {
                salesContractStatusModel.setChangeStatus(SalesContractConstant.CONTRACT_CHANGE_UNPASSSTATE);
            }
            
        }
        salesContractStatusService.update(salesContractStatusModel);
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
