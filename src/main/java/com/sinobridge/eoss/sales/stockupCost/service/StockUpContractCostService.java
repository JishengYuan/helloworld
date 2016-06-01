/*
 * FileName: StockUpContractCostService.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.sales.stockupCost.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinobridge.base.core.service.DefaultBaseService;
import com.sinobridge.base.core.utils.PaginationSupport;
import com.sinobridge.eoss.sales.stockupCost.dao.StockUpContractCostDao;
import com.sinobridge.eoss.sales.stockupCost.dao.StockUpContractCostProductDao;
import com.sinobridge.eoss.sales.stockupCost.model.StockUpContractCostModel;

/**
 * <p>
 * Description: StockUpContractCostService
 * </p>
 *
 * @author liyx
 * @version 1.0

 * <p>
 * History: 
 *
 * Date                     Author         Version     Description
 * ---------------------------------------------------------------------------------
 * 2015年10月9日 下午5:29:54      liyx           1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Service
@Transactional
public class StockUpContractCostService extends DefaultBaseService<StockUpContractCostModel, StockUpContractCostDao> {
    @Autowired
    private StockUpContractCostProductDao costProductDao;

    /**
     * 条件查询
     * @param detachedCriteria
     * @param i
     * @param pageSize
     * @return
     */
    public PaginationSupport findPageBySearchMap(HashMap<String, Object> searchMap, int startIndex, Integer pageSize) {
        return getDao().findPageBySearchMap(searchMap, startIndex, pageSize);
    }

    /**
     * 根据备货合同设备ID得到备货产品
     * @param ids
     * @return
     */
    public List<Map<String, Object>> getProductByIds(String ids) {
        return getDao().getProductByIds(ids);
    }

    /**
     * 根据产品合同得到关联的备货合同
     * @param id
     * @return
     */
    public List<Map<String, Object>> getProSalesCode(Long id) {
        return getDao().getProSalesCode(id);
    }

    //
    /**
     * 保存多个实体
     * @param costList
     */
    public void saveOrUpdateAll(List<StockUpContractCostModel> costList) {
        if (costList != null) {
            getDao().saveOrUpdateAll(costList);
        }
    }

    /**
     * 删除未确认的合同
     * @param ids
     */
    public void deletes(String[] ids) {
        List<StockUpContractCostModel> delCostContracts = new ArrayList<StockUpContractCostModel>();
        for (String id : ids) {
            long trueId = Long.parseLong(id);
            StockUpContractCostModel costModel = getDao().get(trueId);
            delCostContracts.add(costModel);

            //删除合同产品
            costProductDao.deleteProduct(trueId);
        }
        getDao().deleteAll(delCostContracts);
    }

    public void getStockCost(Long id) {

    }
}
