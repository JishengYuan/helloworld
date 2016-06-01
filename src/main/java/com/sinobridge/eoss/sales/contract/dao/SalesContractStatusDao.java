/*
 * FileName: SalesContractDao.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.sales.contract.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.sinobridge.base.core.dao.imp.DefaultBaseDao;
import com.sinobridge.eoss.sales.contract.model.SalesContractStatusModel;

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
 * 2014年7月18日 下午8:24:25          3unshine        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Repository
public class SalesContractStatusDao extends DefaultBaseDao<SalesContractStatusModel, Long> {

    /** 根据合同盖章申请中的工单ID得到合同对应的状态实体
     * 
     * @param ProcessInstanceId
     * @return SalesContractStatusModel
     */
    public SalesContractStatusModel findContractStatusByCachetPId(long ProcessInstanceId) {
        Object[] values = new Object[1];
        values[0] = ProcessInstanceId;
        String hql = "SELECT cs.* FROM sales_contract_status cs,sales_contract c,sales_cachet cc WHERE c.id=cs.SalecontractId AND cc.SalesContractId=c.id AND cc.ProcessInstanceId=?";
        Query query = createSQLQuery(hql, values).addEntity(SalesContractStatusModel.class);
        List<SalesContractStatusModel> list = query.list();
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * @param contractId
     * @return
     */
    public SalesContractStatusModel findContractStatusByContractId(long contractId) {
        Object[] values = new Object[1];
        values[0] = contractId;
        String hql = "SELECT cs.* FROM sales_contract_status cs WHERE cs.SaleContractId=? AND cs.ContractSnapShootId is null ORDER BY cs.CreateTime DESC";
        Query query = createSQLQuery(hql, values).addEntity(SalesContractStatusModel.class);
        List<SalesContractStatusModel> list = query.list();
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * @param procInstId
     * @return
     */
    public SalesContractStatusModel findContractStatusByChangePId(long procInstId) {
        Object[] values = new Object[1];
        values[0] = procInstId;
        String hql = "SELECT cs.* FROM sales_contract_status cs,sales_contract c,sales_contract_change_apply scca WHERE c.id=cs.SalecontractId AND scca.SaleContractId=c.id AND scca.processInstanceId=?";
        Query query = createSQLQuery(hql, values).addEntity(SalesContractStatusModel.class);
        List<SalesContractStatusModel> list = query.list();
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }
    
    @SuppressWarnings("unchecked")
    public SalesContractStatusModel contractOrderStatus(Long salesId) {
        Object[] param = new Object[1];
        String sql = "SELECT * FROM sales_contract_status WHERE salecontractId=? ORDER BY CreateTime ";
        param[0] = salesId;
        Query query = this.createSQLQuery(sql,param).addEntity(SalesContractStatusModel.class);
        List<SalesContractStatusModel> objList = query.list();
        SalesContractStatusModel contract=objList.get(0);
        return contract;
    }
}
