/*
 * FileName: StockUpContractCostProductService.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.sales.stockupCost.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinobridge.base.core.service.DefaultBaseService;
import com.sinobridge.eoss.sales.stockupCost.dao.StockUpContractCostDao;
import com.sinobridge.eoss.sales.stockupCost.dao.StockUpContractCostProductDao;
import com.sinobridge.eoss.sales.stockupCost.model.StockUpContractCostModel;
import com.sinobridge.eoss.sales.stockupCost.model.StockUpContractCostProductModel;

/**
 * <p>
 * Description: StockUpContractCostProductService
 * </p>
 *
 * @author liyx
 * @version 1.0

 * <p>
 * History: 
 *
 * Date                     Author         Version     Description
 * ---------------------------------------------------------------------------------
 * 2015年10月14日 下午2:05:46      liyx           1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Service
@Transactional
public class StockUpContractCostProductService extends DefaultBaseService<StockUpContractCostProductModel, StockUpContractCostProductDao> {
    @Autowired
    private StockUpContractCostDao stockUpContractCostDao;

    /**
     * 保存确认合同成本实体
     * @param productList
     */
    public void saveOrUpdateAll(StockUpContractCostModel stockUpContractCostModel, List<StockUpContractCostProductModel> productList) {
        if (stockUpContractCostModel != null) {
            /*保存成本合同*/
            stockUpContractCostDao.save(stockUpContractCostModel);
        }
        if (productList != null) {
            /*保存成本合同中的产品*/
            getDao().saveOrUpdateAll(productList);
        }
    }

    /**
     * 根据备货合同ID得到备货产品
     * @param bhSaleContractId
     * @param cpSaleContractId
     * @param saleContractId
     * @return
     */
    public List<Map<String, Object>> getProductByBhSaleContractId(String bhSaleContractId, String cpSaleContractId, String saleContractId) {
        return getDao().getProductByBhSaleContractId(bhSaleContractId, cpSaleContractId, saleContractId);
    }

    /**
     * 查找产品合同关联备货相关订单，产品信息 ，未确认部分产品
     * @param bhSaleContractId
     * @param cpSaleContractId
     * @param saleContractId
     * @return
     */
    public List<Map<String, Object>> getProducts(String saleContractId, String orderBuy) {
        return getDao().getProducts(saleContractId, orderBuy);
    }

}
