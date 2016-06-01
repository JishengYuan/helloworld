/*
 * FileName: ContractChangeApplyDao.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.sales.contractchange.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.sinobridge.base.core.dao.imp.DefaultBaseDao;
import com.sinobridge.eoss.sales.contractchange.model.ContractChangeApplyModel;

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
 * 2014年7月23日 下午2:59:19          3unshine        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Repository
public class ContractChangeApplyDao extends DefaultBaseDao<ContractChangeApplyModel, String> {

    /**
     * @param contractId
     * @return
     */
    public ContractChangeApplyModel getContractChangeApplyModelByContractId(Long contractId) {
        Object[] values = new Object[1];
        values[0] = contractId;
        String hql = "SELECT * FROM sales_contract_change_apply cca WHERE cca.saleContractId=? order by cca.id desc";
        Query query = createSQLQuery(hql, values).addEntity(ContractChangeApplyModel.class);
        List<ContractChangeApplyModel> list = query.list();
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * @param parseLong
     * @return
     */
    public int findSalesContractId(long saleId) {
        int flat = 0;
        Object[] values = new Object[1];
        values[0] = saleId;
        String hql = "SELECT * FROM `order_contract_map` WHERE sales_contract=?";
        Query query = createSQLQuery(hql, values).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List<Object> list = query.list();
        if (list.size() > 0) {
            flat = 1;
        }
        return flat;
    }

}
