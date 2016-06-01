package com.sinobridge.eoss.flow.extend.project;

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
 * 
 * <p>
 * Description: 
 * </p>
 *
 * @author Administrator
 * @version 1.0

 * <p>
 * History: 
 *
 * Date                     Author         Version     Description
 * ---------------------------------------------------------------------------------
 * 2014年7月9日 下午6:08:58          liyx        1.0         To create
 * </p>
 *
 * @since 
 * @see
 */
@Service
@Transactional
public class ProjectAreaManager implements Assignee {
    @Autowired
    private SysStaffService sysStaffService;
	@Override
	public List<String> getAssigneeUserId(Map<String, Object> args) {
        List<String> list = new ArrayList<String>();
        String userId = ((ProcessInst) args.get(ProcessConstants.V_PROC_INST)).getCreator();
        if ("admin".equals(userId)) {
            list.add("admin");
        } else {
            String userName = sysStaffService.getAreaManager(userId);
            list.add(userName);
        }
		return list;
	}

}
