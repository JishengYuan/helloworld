/*
 * FileName: FundsSalesContractDao.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.salesfundsCost.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.sinobridge.base.core.dao.imp.DefaultBaseDao;
import com.sinobridge.eoss.sales.contract.model.SalesContractModel;
import com.sinobridge.eoss.salesfundsCost.model.FundsSalesContract;
import com.sinobridge.eoss.salesfundsCost.model.FundsSalesLog;

/**
 * <p>
 * Description: 
 * </p>
 *
 * @author tigq
 * @version 1.0

 * <p>
 * History: 
 *
 * Date                     Author         Version     Description
 * ---------------------------------------------------------------------------------
 * 2015年12月24日 上午11:32:19          tigq        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Repository
public class FundsSalesContractDao extends DefaultBaseDao<FundsSalesContract, Long> {

    /**查询合同信息
     * @param contractCode
     * @return
     */
    @SuppressWarnings("unchecked")
    public SalesContractModel getSalesContract(String contractCode) {
        StringBuffer sb = new StringBuffer();
        SalesContractModel sale = new SalesContractModel();
        sb.append("SELECT * FROM sales_contract s WHERE s.`ContractCode`='" + contractCode + "' ");
        List<SalesContractModel> sales = createSQLQuery(sb.toString()).addEntity(SalesContractModel.class).list();
        if (sales.size() > 0) {
            sale = sales.get(0);
        }
        return sale;
    }

    /**查询合同日志信息
     * @param contractCode
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<FundsSalesLog> getFundsLogs(String contractCode) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT * FROM `funds_saleslog` s WHERE s.`contractCode`='" + contractCode + "' ORDER BY s.`ID`");
        List<FundsSalesLog> logs = createSQLQuery(sb.toString()).addEntity(FundsSalesLog.class).list();
        return logs;
    }

    /**FundsSalesContract
     * @param contractCode
     * @return
     */
    @SuppressWarnings("unchecked")
    public FundsSalesContract getFundsSales(String contractCode) {
        FundsSalesContract fund = new FundsSalesContract();
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT * FROM `funds_salescontract` f WHERE f.`contractCode`='" + contractCode + "' ");
        List<FundsSalesContract> funds = createSQLQuery(sb.toString()).addEntity(FundsSalesContract.class).list();
        if (funds.size() > 0) {
            fund = funds.get(0);
        }
        return fund;
    }

}
