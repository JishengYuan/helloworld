/*
 * FileName: SalesContractDao.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.sales.contract.dao;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.sinobridge.base.core.dao.imp.DefaultBaseDao;
import com.sinobridge.eoss.sales.contract.model.SalesContractReceivePlanModel;

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
 * 2014年5月15日 下午1:24:25          3unshine        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Repository
public class SalesReceivePlanDao extends DefaultBaseDao<SalesContractReceivePlanModel, String> {
    /**
    public void saveAll(List<SalesContractReceivePlanModel> salesContractReceivePlans) {
        for (Iterator<SalesContractReceivePlanModel> i = salesContractReceivePlans.iterator(); i.hasNext();) {
            save(i.next());
        }
    }
     * 
     */
    public Set<SalesContractReceivePlanModel> updateAll(List<SalesContractReceivePlanModel> oldReceivePlans, List<SalesContractReceivePlanModel> receivePlans) {
        //删除原来所有子条目
        deleteAll(oldReceivePlans);
        //插入进当前的子条目
        saveOrUpdateAll(receivePlans);
        Set receivePlanSet = new HashSet(Arrays.asList(receivePlans));
        return receivePlanSet;
    }

    /**
     * @param salesContractModelId
     */
    public void delReceivePlansByContractId(long salesContractModelId) {
        Long[] param = new Long[1];
        param[0] = salesContractModelId;
        String sql = "delete from sales_contract_receive_plan where SalesContractId = ?";
        createSQLQuery(sql, param).executeUpdate();
    }

    /**
     * @param receivePlans
     * @return
     */
    public Set<SalesContractReceivePlanModel> saveAll(List<SalesContractReceivePlanModel> receivePlans) {
        Set<SalesContractReceivePlanModel> salesContractReceivePlanModels = new HashSet<SalesContractReceivePlanModel>();
        saveOrUpdateAll(receivePlans);
        salesContractReceivePlanModels.addAll(receivePlans);
        return salesContractReceivePlanModels;
    }

}
