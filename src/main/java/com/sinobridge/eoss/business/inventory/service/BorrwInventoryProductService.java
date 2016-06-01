/*
 * FileName: BorrwInventoryProductService.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.business.inventory.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinobridge.base.core.service.DefaultBaseService;
import com.sinobridge.eoss.business.inventory.dao.BorrwInventoryProductDao;
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
 * 2015年2月4日 上午11:22:37          guo_kemeng        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Service
@Transactional
public class BorrwInventoryProductService extends DefaultBaseService<BorrwInventoryProductModel, BorrwInventoryProductDao> {

    /**
    * 保存，修改一个集合数据
    * @param members
    */
    public void saveOrUpdate(List<BorrwInventoryProductModel> products) {
        getDao().saveOrUpdateAll(products);
    }

    //    public List<Object[]> getList(Long borrowingId,Long InventoryId){
    //    	return getDao().getList(borrowingId, inventoryId);
    //    }
    public BorrwInventoryProductModel getList(Long borrowingId, Long inventoryId) {
        return getDao().getList(borrowingId, inventoryId);
    }

    /**
     * 根据入库单ID得到设备
     * @param putId
     * @return
     */
    public List<BorrwInventoryProductModel> getProductByPutId(Long putId) {
        return getDao().getProductByPutId(putId);
    }

    /**
     * 根据借库单ID得到设备
     * @param outId
     * @return
     */
    public List<BorrwInventoryProductModel> getProductByOutId(Long outId) {
        return getDao().getProductByOutId(outId);
    }

}
