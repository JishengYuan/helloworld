/*
 * FileName: SalesDeptPrincipalAndPresident.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.flow.extend.sales;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinobridge.eoss.bpm.common.constants.ProcessConstants;
import com.sinobridge.eoss.bpm.model.ProcessInst;
import com.sinobridge.eoss.bpm.service.assignee.Assignee;
import com.sinobridge.systemmanage.service.SysStaffService;

/**
 * <p>
 * Description: 该类用于销售合同流程中获取创建人部门经理的扩展类
 * </p>
 *
 * @author 3unshine
 * @version 1.0

 * <p>
 * History: 
 *
 * Date                     Author         Version     Description
 * ---------------------------------------------------------------------------------
 * 2014年7月7日 下午2:35:27          3unshine        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Service
@Transactional
public class SalesDeptPrincipalAndPresident implements Assignee {
    @Autowired
    private SysStaffService sysStaffService;

    /* (non-Javadoc)
     * @see com.sinobridge.eoss.bpm.service.assignee.Assignee#getAssigneeUserId(java.util.Map)
     */
    @Override
    public List<String> getAssigneeUserId(Map<String, Object> args) {
        List<String> list = new ArrayList<String>();
        String userId = ((ProcessInst) args.get(ProcessConstants.V_PROC_INST)).getCreator();
        if ("admin".equals(userId)) {
            list.add("admin");
        } else {
            String userName;
            try {
                if(userId.equals("jianghb")||userId.equals("zhoushu")){
                    list.add(userId);
                } else {
                    userName = sysStaffService.getDeptManager(userId);
                    list.add(userName);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }

}
