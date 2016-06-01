/*
 * FileName: SalesContractDao.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.sales.cachet.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.sinobridge.base.core.dao.imp.DefaultBaseDao;
import com.sinobridge.eoss.sales.cachet.model.SalesCachetModel;

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
 * 2014年7月8日 下午1:24:25          3unshine        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Repository
public class SalesCachetDao extends DefaultBaseDao<SalesCachetModel, String> {

    /**
     * @param salesContractId
     * @return
     */
    public SalesCachetModel getBysalesContractId(long salesContractId) {
        List<SalesCachetModel> salesCachetList = new ArrayList<SalesCachetModel>();
        SalesCachetModel salesCachetModel = new SalesCachetModel();
        Long[] param = new Long[1];
        param[0] = salesContractId;
        String sql = "from SalesCachetModel where salesContractId=?";
        salesCachetList = find(sql, param);
        if (salesCachetList.size() > 0) {
            salesCachetModel = salesCachetList.get(0);
        }
        return salesCachetModel;
    }

    /**
     *  根据合同ID查找所有的盖章申请
     * @param salesContractId
     * @return
     */
    public List<SalesCachetModel> getCachetListsContractId(long salesContractId) {
        List<SalesCachetModel> salesCachetList = new ArrayList<SalesCachetModel>();
        Long[] param = new Long[1];
        param[0] = salesContractId;
        String sql = "from SalesCachetModel where salesContractId=?";
        salesCachetList = find(sql, param);
        return salesCachetList;
    }
}
