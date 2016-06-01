/*
 * FileName: SalesStatusUpdateWhenEnter.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.flow.extend.sales;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinobridge.eoss.bpm.listener.EnterTaskExe;
import com.sinobridge.eoss.sales.contract.model.SalesContractStatusModel;
import com.sinobridge.eoss.sales.contract.service.SalesContractStatusService;

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
 * 2014年7月18日 上午9:34:30          3unshine        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Service
@Transactional
public class SalesStatusUpdateWhenEnter implements EnterTaskExe {
    @Autowired
    private SalesContractStatusService salesContractStatusService;

    /* (non-Javadoc)
     * @see com.sinobridge.eoss.bpm.listener.EnterTaskExe#execute(java.lang.Long, java.lang.String, java.lang.String)
     */
    @Override
    public void execute(Long procInstId, String taskName, String procDefKey) {
        //判断是不是盖章申请的流程
        if (procDefKey.equals("sgz")) {
            //根据工单ID查出要更新的salesContractStatusModel实体
            SalesContractStatusModel salesContractStatusModel = salesContractStatusService.findContractStatusByCachetPId(procInstId);
            //创建流程的时候查不出salesContractStatusModel，所以创建的时候在insert salesContractStatusModel时直接update状态表中的盖章当前任务节点名
            if (salesContractStatusModel != null) {
                salesContractStatusModel.setCachetStatus(taskName);
                salesContractStatusService.update(salesContractStatusModel);
            }

        }
    }
}
