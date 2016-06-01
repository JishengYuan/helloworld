/*
 * FileName: SalseContractMergeDao.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.sales.contract.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.sinobridge.base.core.dao.imp.DefaultBaseDao;
import com.sinobridge.eoss.sales.contract.model.SalseContractMergeModel;

/**
 * <p>
 * Description: 
 * </p>
 *
 * @author guo_kemeng
 * @version 1.0

 * <p>
 * History: 
 *
 * Date                     Author         Version     Description
 * ---------------------------------------------------------------------------------
 * 2015年3月23日 下午2:54:19          guo_kemeng        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Repository
public class SalseContractMergeDao extends DefaultBaseDao<SalseContractMergeModel, Long> {

    public SalseContractMergeModel findbySalesContractId(Long salescontractId) {
        String hql = "from SalseContractMergeModel t where t.contractId=" + salescontractId;
        SalseContractMergeModel rs = (SalseContractMergeModel) this.createQuery(hql).uniqueResult();
        return rs;
    }

    /**
     * @param salesId
     * @return
     */
    public SalseContractMergeModel getSalesMergeBySalesId(Long salesId) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT * FROM `sales_contractmerge_apply` a WHERE a.`ContractId`='" + salesId + "' ;");
        List<SalseContractMergeModel> merge = createSQLQuery(sb.toString()).addEntity(SalseContractMergeModel.class).list();
        if (merge.size() > 0) {
            return merge.get(0);
        }
        return null;
    }

}
