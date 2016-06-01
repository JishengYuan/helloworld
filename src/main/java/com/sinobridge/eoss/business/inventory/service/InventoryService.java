/*
 * FileName: InventoryService.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.business.inventory.service;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinobridge.base.core.service.DefaultBaseService;
import com.sinobridge.base.core.utils.PaginationSupport;
import com.sinobridge.eoss.business.inventory.dao.InventoryDao;
import com.sinobridge.eoss.business.inventory.model.InventoryModel;

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
 * 2015年2月3日 下午4:59:16          guo_kemeng        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Service
@Transactional
public class InventoryService extends DefaultBaseService<InventoryModel, InventoryDao> {

    /**
     * <code>saveOrUpdate</code>
     * 批量增加
     * @param modelList
     * @since   2015年2月4日    guokemenng
     */
    public void saveOrUpdate(List<InventoryModel> modelList) {
        getDao().saveOrUpdateAll(modelList);
    }

    /**
     * <code>getDetailList</code>
     * 设备借出归还记录
     * @param productId
     * @param startIndex
     * @param pageSize
     * @return
     * @since   2015年2月6日    guokemenng
     */
    public PaginationSupport getDetailList(String productId, Integer startIndex, Integer pageSize) {
        String sql = "SELECT b.`CustomerManageName`,bp.`BorrwTime`,r.`ReturnUserName`,r.`ReturnDate`,bp.`Rent`,bp.id FROM `business_inventory_bproduct` bp LEFT JOIN `business_inventory_borr` b ON bp.`BorrowingId` = b.`id` LEFT JOIN `business_inventory_return` r ON bp.`ReturnID` = r.`id` where bp.inventoryId=?";

        Object[] params = new Object[] { productId };
        Query query = getDao().createSQLQuery(sql, params);
        query.setFirstResult(startIndex);
        query.setMaxResults(pageSize);

        Integer totalCount = getDao().getCountBySQLQuery(sql, params);
        PaginationSupport pg = new PaginationSupport(query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list(), totalCount);

        return pg;
    }

    /**
     * <code>getApproveList</code>
     * 得到审批列表
     * @param param
     * @param startIndex
     * @param pageSize
     * @return
     * @since   2015年2月9日    guokemenng
     */
    public PaginationSupport getApproveList(Map<String, Object> param, Integer startIndex, Integer pageSize) {

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ( ");
        sb.append("SELECT b.id,b.`CustomerManageName` userName,b.`SerialNum` num,b.`BorrowTime` ti,b.`State` state,0 AS ss FROM `business_inventory_borr` b ");
        sb.append("UNION ALL ");
        sb.append("SELECT r.id,r.returnUserName userName,r.serialNum num,r.returnDate ti,r.state state,1 AS ss FROM `business_inventory_return` r ");
        sb.append(") t where 1=1 ");

        Object[] params = new Object[param.size()];
        int paramIndex = 0;
        if (param.get("userName") != null) {
            params[paramIndex] = param.get("userName");
            paramIndex++;
            sb.append(" and t.userName = ?");
        }
        if (param.get("state") != null) {
            params[paramIndex] = param.get("state");
            paramIndex++;
            sb.append("and t.state = ?");
        }
        Query query = getDao().createSQLQuery(sb.toString(), params);
        query.setFirstResult(startIndex);
        query.setMaxResults(pageSize);

        Integer totalCount = getDao().getCountBySQLQuery(sb.toString(), params);
        PaginationSupport pg = new PaginationSupport(query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list(), totalCount);

        return pg;
    }

    /**
     * <code>getInventorysByOutId</code>
     * 根据出库ID 得到出库的产品
     * @param outId
     * @return
     * @since   2015年2月12日    guokemenng
     */
    @SuppressWarnings("unchecked")
    public List<InventoryModel> getInventorysByOutId(Long outId) {
        String sql = "SELECT i.* FROM `business_inventory` i LEFT JOIN `business_inventory_bproduct` b ON b.`InventoryId` = i.`id` WHERE b.`borrowingId` = ?";
        Object[] param = new Object[] { outId };
        Query query = getDao().createSQLQuery(sql, param).addEntity(InventoryModel.class);
        return query.list();
    }

    /**
     * 查询出所有在库的设备
     * @return
     * @throws DataAccessException
     * @since   2015年2月12日    liyx
     */
    public List<InventoryModel> findAllProduct() {
        return getDao().findAllProduct();
    }

    /**
     * <code>getInventorysByPutId</code>
     * 根据出库ID 得到入库的产品
     * @param putId
     * @return
     * @since   2015年2月12日    guokemenng
     */
    @SuppressWarnings("unchecked")
    public List<InventoryModel> getInventorysByPutId(Long putId) {
        String sql = "SELECT i.* FROM `business_inventory` i LEFT JOIN `business_inventory_bproduct` b ON b.`InventoryId` = i.`id` WHERE b.`returnID` = ?";
        Object[] param = new Object[] { putId };
        Query query = getDao().createSQLQuery(sql, param).addEntity(InventoryModel.class);
        return query.list();
    }

}
