/*
 * FileName: BorrowQuantityDao.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.business.inventory.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.sinobridge.base.core.dao.imp.DefaultBaseDao;
import com.sinobridge.eoss.business.inventory.model.BorrowQuantityModel;

/**
 * <p>
 * Description: 
 * </p>
 *
 * @author vermouth
 * @version 1.0

 * <p>
 * History: 
 *
 * Date                     Author         Version     Description
 * ---------------------------------------------------------------------------------
 * 2015年11月24日 下午2:04:42          vermouth        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Repository
public class BorrowQuantityDao extends DefaultBaseDao<BorrowQuantityModel, Long> {
    /**
     * 根据出库单得到出库设备总数
     * @param outId
     * @since   2015年12月22日    liyx
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<BorrowQuantityModel> getInventoryquaByOutId(Long outId) {
        String sql = "select q.* from business_inventory_qua q where q.BorrowingId=?";
        Object[] param = new Object[] { outId };
        Query query = createSQLQuery(sql, param).addEntity(BorrowQuantityModel.class);
        return query.list();
    }

}
