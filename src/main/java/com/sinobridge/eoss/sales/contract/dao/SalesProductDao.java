/*
 * FileName: SalesContractDao.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.sales.contract.dao;

import org.springframework.stereotype.Repository;

import com.sinobridge.base.core.dao.imp.DefaultBaseDao;
import com.sinobridge.eoss.sales.contract.model.SalesContractProductModel;

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
public class SalesProductDao extends DefaultBaseDao<SalesContractProductModel, Long> {

    /**
     * @param salesContractModelId
     */
    public void delSalesProductsByContractId(long salesContractModelId) {
        Long[] param = new Long[1];
        param[0] = salesContractModelId;
        String sql = "delete from sales_contract_product where SaleContractId = ?";
        createSQLQuery(sql, param).executeUpdate();
    }

}
