/*
 * FileName: ContractChangeApplyService.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.sales.contractchange.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinobridge.base.core.service.DefaultBaseService;
import com.sinobridge.eoss.sales.contractchange.dao.ContractSnapShootDao;
import com.sinobridge.eoss.sales.contractchange.model.ContractSnapShootModel;

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
public class ContractSnapShootService extends DefaultBaseService<ContractSnapShootModel, ContractSnapShootDao> {

    /**
     * <code>getContractSnapShootsByContractId</code>
     * 根据合同ID得到该合同的快照信息
     * @param salesId
     * @return List<ContractSnapShootModel>
     * @since  2014年8月7日 3unshine
     */
    public List<Map<String, Object>> getContractSnapShootsByContractId(Long salesId) {
        return getDao().getContractSnapShootsByContractId(salesId);
    }

    /**
     * <code>getContractSnapShootsByProcInstId</code>
     * 根据工单ID得到实体对象
     * @param procInstId
     * @return
     * @since   2014年11月28日    guokemenng
     */
    public ContractSnapShootModel getContractSnapShootsByProcInstId(Long procInstId) {

        String hql = "from ContractSnapShootModel where processInstanceId = ?";
        List<ContractSnapShootModel> modelList = this.getDao().find(hql, new Object[] { procInstId });
        if (modelList.size() > 0) {
            return modelList.get(0);
        } else {
            return null;
        }
    }

}
