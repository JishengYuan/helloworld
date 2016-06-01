/*
 * FileName: OutOrInInventoryService.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.business.inventory.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinobridge.base.core.service.DefaultBaseService;
import com.sinobridge.base.core.utils.JacksonJsonMapper;
import com.sinobridge.eoss.business.inventory.dao.OutOrInInventoryDao;
import com.sinobridge.eoss.business.inventory.model.OutOrInInventoryModel;

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
 * 2015年2月3日 下午4:59:33          guo_kemeng        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Service
@Transactional
public class OutOrInInventoryService extends DefaultBaseService<OutOrInInventoryModel, OutOrInInventoryDao> {

    //	 public Map<String, Object> getProjectAll(){
    //		 return getDao().getProjectAll();
    //	 }
    //	 public Map<String, Object> getProjectAll(){
    //		 return getDao().getProjectAll();
    //	 }

    @Autowired
    private BorrowQuantityService borrowQuantityService;

    /**
     * <code>getAllProject</code>
     * @since   2015年2月12日    liyx
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> getAllProject() {
        //Map<String, Object> map = new HashMap<String, Object>();
        //if (!StringUtil.isEmpty(code)) {
        String hql = "select id,projectName from pms_project ";
        List<Map<String, Object>> mapList = getDao().createSQLQuery(hql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        return mapList;
    }

    /**
        * <code>getAllOutOrInInventory</code>
        * @since   2015年2月13日    liyx
        */
    public List<OutOrInInventoryModel> getAllOutOrInInventory() {
        return getDao().getAllOutOrInInventory();
    }

    /**
     * 根据产品合同得到关联的备货合同
     * @param id
     * @return
     * @since  2015年11月24日    liyx
     */
    public List<Map<String, Object>> getProducts(int borrowingId) {
        return getDao().getProducts(borrowingId);
    }

    /**
     * 借货单详情设备信息
     * @param borrowingId
     * @return
     */
    public List<Map<String, Object>> getProductsDetail(int borrowingId, int returnId) {
        return getDao().getProductsDetail(borrowingId, returnId);
    }

    /**
     * 根据产品型号查找产品序列号
     * @param borrowingId
     * @param productNo
     * @return
     */
    public List<Map<String, Object>> getSeriesNoByProductNo(int borrowingId, String productNo) {
        return getDao().getSeriesNoByProductNo(borrowingId, productNo);
    }

    /**
     * 出库申请列表
     * @return
     */
    public List<Map<String, Object>> getOutAppliList() {
        return getDao().getOutAppliList();
    }

    /**查询型号剩余数量
     * @param product
     * @return
     */
    public List<Map<String, Object>> getChoiceProduct(String[] product) {
        return getDao().getChoiceProduct(product);
    }

    /**保存实体
     * @param outOrInventoryModel
     * @param tableData
     * @return
     */
    public String doSave(OutOrInInventoryModel outOrInventoryModel, String tableData) {
        String rs = "true";

        // Long reimId = IdentifierGeneratorImpl.generatorLong();
        //outOrInventoryModel.setId(reimId);
        List<Object> gridDataList = getTableGridData(tableData);

        try {
            this.saveOrUpdate(outOrInventoryModel);
        } catch (Exception e) {
            e.printStackTrace();
            return "false";
        }
        rs = borrowQuantityService.getProductQua(gridDataList, outOrInventoryModel);
        return rs;
    }

    @SuppressWarnings("unchecked")
    private List<Object> getTableGridData(String tableData) {
        ObjectMapper objectMapper = JacksonJsonMapper.getInstance();
        Map<String, Object> tableDataMap = null;
        List<Object> gridDataList = new ArrayList<Object>();
        try {
            tableDataMap = objectMapper.readValue(tableData, HashMap.class);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (tableDataMap != null && tableDataMap.size() > 0) {
            for (String key : tableDataMap.keySet()) {
                gridDataList = (List<Object>) tableDataMap.get(key);
            }
        }
        return gridDataList;
    }

}
