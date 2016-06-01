/*
 * FileName: BorrwInventoryProductDao.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.business.inventory.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.sinobridge.base.core.dao.imp.DefaultBaseDao;
import com.sinobridge.eoss.business.inventory.model.BorrwInventoryProductModel;

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
 * 2015年2月4日 上午11:21:20          guo_kemeng        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Repository
public class BorrwInventoryProductDao extends DefaultBaseDao<BorrwInventoryProductModel, Long> {
    public List<Object[]> getList2(Long borrowingId, Long inventoryId) {
        String sql = "select id,BorrwTime,BorrowingId,InventoryId from business_inventory_bproduct where BorrowingId=" + borrowingId + " and InventoryId=" + inventoryId;
        @SuppressWarnings("unchecked")
        List<Object[]> partnerAllList = this.createQuery(sql).list();
        return partnerAllList;
    }

    @SuppressWarnings("unchecked")
    public BorrwInventoryProductModel getList(Long borrowingId, Long inventoryId) throws DataAccessException {
        //List<PmsArea> list = find("from PmsArea where areaName like ?");
        Query query = getSession().createQuery("from BorrwInventoryProductModel where borrowingId.id=? and inventoryId.id=?");
        query.setParameter(0, borrowingId);
        query.setParameter(1, inventoryId);

        List<BorrwInventoryProductModel> list = query.list();
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 根据入库单ID得到设备
     * @param putId
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<BorrwInventoryProductModel> getProductByPutId(Long putId) {
        String sql = "select b.* from business_inventory_bproduct b where b.ReturnId=?";
        Object[] param = new Object[] { putId };
        Query query = createSQLQuery(sql, param).addEntity(BorrwInventoryProductModel.class);
        return query.list();
    }

    /**
     * 根据借货单ID得到设备
     * @param outId
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<BorrwInventoryProductModel> getProductByOutId(Long outId) {
        String sql = "select b.* from business_inventory_bproduct b where b.BorrowingId=?";
        Object[] param = new Object[] { outId };
        Query query = createSQLQuery(sql, param).addEntity(BorrwInventoryProductModel.class);
        return query.list();
    }

}
