/*
 * FileName: SalesContractSizeDao.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.sales.contractfounds.dao;

import org.springframework.stereotype.Repository;

import com.sinobridge.base.core.dao.imp.DefaultBaseDao;
import com.sinobridge.eoss.sales.contractfounds.model.SalesContractSizeModel;

/**
 * <p>
 * Description: 
 * </p>
 *
 * @author liyx
 * @version 1.0

 * <p>
 * History: 
 *
 * Date                     Author         Version     Description
 * ---------------------------------------------------------------------------------
 * 2015年12月1日 下午8:28:02      liyx           1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Repository
public class SalesContractSizeDao extends DefaultBaseDao<SalesContractSizeModel, Long> {

    /**
     * @param id
     */
    public void deleteSize(Long id) {
        Long[] param = new Long[1];
        param[0] = id;
        String sql = "delete from sales_contractsize where applyFoundsID = ?";
        createSQLQuery(sql, param).executeUpdate();
        
    }

}
