/*
 * FileName: BorrowQuantityService.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.business.inventory.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinobridge.base.core.service.DefaultBaseService;
import com.sinobridge.eoss.business.inventory.dao.BorrowQuantityDao;
import com.sinobridge.eoss.business.inventory.model.BorrowQuantityModel;
import com.sinobridge.eoss.business.inventory.model.OutOrInInventoryModel;

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
 * 2015年11月24日 下午2:03:02          vermouth        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Service
@Transactional
public class BorrowQuantityService extends DefaultBaseService<BorrowQuantityModel, BorrowQuantityDao> {

    /**保存产品数量实体
     * @param gridDataList
     * @param outOrInventoryModel
     * @return
     */
    public String getProductQua(List<Object> gridDataList, OutOrInInventoryModel outOrInventoryModel) {
        String rs = "true";
        if (gridDataList != null) {
            for (int i = 0; i < gridDataList.size(); i++) {//加了非空判断
                Map<String, Object> map = StringToMap(gridDataList.get(i).toString());//格式转换
                if (map != null) {
                    BorrowQuantityModel borrow = new BorrowQuantityModel();
                    String partner = map.get("column0").toString();
                    borrow.setPartner(partner);
                    String productNo = map.get("column1").toString();
                    borrow.setProductNo(productNo);
                    String num = map.get("column2").toString();
                    borrow.setQuantity(Integer.parseInt(num));
                    borrow.setStatus((short) 0);
                    borrow.setBorrowingId(outOrInventoryModel);
                    try {
                        this.saveOrUpdate(borrow);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return "false";
                    }
                }
            }
        }
        return rs;
    }

    public synchronized Map<String, Object> StringToMap(String mapText) {

        if (mapText == null || mapText.equals("")) {
            return null;
        }
        mapText = mapText.substring(1, mapText.length() - 1);
        Map<String, Object> map = new HashMap<String, Object>();
        String[] text = mapText.split(",");
        for (String str : text) {
            String[] keyText = str.split("="); // 转换key与value的数组  
            if (keyText.length < 1) {
                continue;
            }
            if (keyText.length == 1) {

            } else {
                String key = keyText[0]; // key  
                String value = keyText[1]; // value  
                if (!value.trim().equals("")) {
                    map.put(key, value);
                }
            }
        }
        return map;
    }

    /**
     * 根据出库单得到出库设备总数
     * @param outId
     * @since   2015年12月22日    liyx
     * @return
     */
    public List<BorrowQuantityModel> getInventoryquaByOutId(Long outId) {
        return getDao().getInventoryquaByOutId(outId);
    }

    /**
     * <code>saveOrUpdate</code>
     * 批量修改
     * @param modelList
     * @since   2015年12月22日    liyx
     */
    public void saveOrUpdateAll(List<BorrowQuantityModel> modelList) {
        getDao().saveOrUpdateAll(modelList);
    }

}
