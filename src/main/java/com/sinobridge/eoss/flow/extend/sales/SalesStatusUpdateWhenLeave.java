/*
 * FileName: SalesStatusUpdate.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.flow.extend.sales;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinobridge.eoss.bpm.listener.LeaveProcessExe;
import com.sinobridge.eoss.business.order.dao.BusinessOrderDao;
import com.sinobridge.eoss.sales.contract.service.SalesContractService;

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
 * 2014年7月18日 上午9:32:06          3unshine        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Service
@Transactional
public class SalesStatusUpdateWhenLeave implements LeaveProcessExe {

    @Autowired
    private SalesContractService salesContractService;

    @Autowired
    private BusinessOrderDao businessOrderDao;

    /* (non-Javadoc)
     * @see com.sinobridge.eoss.bpm.listener.LeaveProcessExe#execute(java.lang.Long, java.lang.String)
     */
    @Override
    public void execute(Long procInstId, String procDefKey) {
        System.out.println("进入工单关闭前处理操作");
        //判断是不是合同申请的流程
        if (procDefKey.equals("ssp")) {
            //根据工单ID查出要更新的SalesContractModel实体
//            DetachedCriteria detachedCriteria = DetachedCriteria.forClass(SalesContractModel.class);
//            detachedCriteria.add(Restrictions.eq(SalesContractModel.PROCESSINSTANCEID, procInstId));
//            List<SalesContractModel> salesContractList = salesContractService.findSalesContractsByCriteria(detachedCriteria);
//            if (salesContractList.size() > 0) {
//                SalesContractModel sales = salesContractList.get(0);
//                sales.setCloseTime(new Date());
//                salesContractService.getDao().update(sales);
//                salesContractService.getDao().flush();
//            }
        }

    }

    @Override
    public void execute(Long procInstId, String procDefKey, String userName) {
        // TODO Auto-generated method stub
        
    }

}
