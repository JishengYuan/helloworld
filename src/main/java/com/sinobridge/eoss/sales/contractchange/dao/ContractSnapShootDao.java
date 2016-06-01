/*
 * FileName: ContractChangeApplyDao.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.sales.contractchange.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.sinobridge.base.core.dao.imp.DefaultBaseDao;
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
 * 2014年7月25日 下午2:59:19          3unshine        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Repository
public class ContractSnapShootDao extends DefaultBaseDao<ContractSnapShootModel, String> {

    /**
     * @param salesId
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> getContractSnapShootsByContractId(Long salesId) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT s.`id`,s.`saleContractVersion`,a.`remark` FROM `sales_contract_snapshoot` s,`sales_contract_change_apply` a ");
        sb.append("WHERE s.`SaleContractChangeApplyId`=a.`id` AND a.`SaleContractId`='" + salesId + "';");
        List<Map<String, Object>> lists = createSQLQuery(sb.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        return lists;
    }

}
