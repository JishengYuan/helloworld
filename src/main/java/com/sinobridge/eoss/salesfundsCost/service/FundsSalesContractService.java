/*
 * FileName: FundsSalesContractService.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.salesfundsCost.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinobridge.base.core.service.DefaultBaseService;
import com.sinobridge.eoss.sales.contract.model.SalesContractModel;
import com.sinobridge.eoss.salesfundsCost.dao.FundsSalesContractDao;
import com.sinobridge.eoss.salesfundsCost.dao.FundsSalesLogDao;
import com.sinobridge.eoss.salesfundsCost.model.FundsSalesContract;
import com.sinobridge.eoss.salesfundsCost.model.FundsSalesLog;
import com.sinobridge.systemmanage.vo.SystemUser;

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
 * 2015年12月24日 上午11:33:03          tigq        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Service
@Transactional
public class FundsSalesContractService extends DefaultBaseService<FundsSalesContract, FundsSalesContractDao> {

    @Autowired
    private FundsSalesLogDao fundsSalesLogDao;

    public FundsSalesContract getBycontractCode(String contractCode) {
        FundsSalesContract fundsSalesContract = null;
        String hql = "from FundsSalesContract  where contractCode='" + contractCode + "'";
        Query query = getDao().createQuery(hql);
        List rs = query.list();
        if (rs != null && rs.size() > 0) {
            fundsSalesContract = (FundsSalesContract) rs.get(0);
        }
        return fundsSalesContract;
    }

    /**查询合同信息
     * @param contractCode
     * @return
     */
    public SalesContractModel getSalesContract(String contractCode) {
        SalesContractModel sale = getDao().getSalesContract(contractCode);
        return sale;
    }

    /**查询合同日志信息
     * @param contractCode
     * @return
     */
    public List<FundsSalesLog> getFundsLogs(String contractCode) {
        List<FundsSalesLog> logs = getDao().getFundsLogs(contractCode);
        return logs;
    }

    /**FundsSalesContract
     * @param contractCode
     * @return
     */
    public FundsSalesContract getFundsSales(String contractCode) {
        FundsSalesContract funds = getDao().getFundsSales(contractCode);
        return funds;
    }

    /**修改资金成本
     * @param fundsSalesContract
     * @param fundsSalesContractOld
     * @param systemUser 
     * @return
     */
    public String saveModiFunds(FundsSalesContract fundsSalesContract, FundsSalesContract fundsSalesContractOld, SystemUser systemUser) {
        Date time = new Date();
        if (!(fundsSalesContract.getBusinessCost() == null ? new BigDecimal(0.00) : fundsSalesContract.getBusinessCost()).equals(fundsSalesContractOld.getBusinessCost() == null ? new BigDecimal(0.00) : fundsSalesContractOld.getBusinessCost())) {//判断是否修改了，若是则保存相关日志
            FundsSalesLog log = new FundsSalesLog();
            log.setOpDate(time);
            log.setContractCode(fundsSalesContractOld.getContractCode());
            log.setOpDesc(systemUser.getStaffName() + "修改商务成本，原金额:￥" + fundsSalesContractOld.getBusinessCost());
            log.setOpAmount(fundsSalesContract.getBusinessCost());
            try {
                fundsSalesLogDao.saveOrUpdate(log);
            } catch (DataAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        if (!(fundsSalesContract.getContractAmount() == null ? new BigDecimal(0.00) : fundsSalesContract.getContractAmount()).equals(fundsSalesContractOld.getContractAmount() == null ? new BigDecimal(0.00) : fundsSalesContractOld.getContractAmount())) {//判断是否修改了，若是则保存相关日志
            BigDecimal amount = fundsSalesContractOld.getContractAmount();
            FundsSalesLog log = new FundsSalesLog();
            log.setOpDate(time);
            log.setContractCode(fundsSalesContractOld.getContractCode());
            log.setOpDesc(systemUser.getStaffName() + "修改合同金额，原金额:￥" + amount);
            log.setOpAmount(fundsSalesContract.getContractAmount());
            fundsSalesLogDao.save(log);
        }
        if (!(fundsSalesContract.getIncomeInvoice() == null ? new BigDecimal(0.00) : fundsSalesContract.getIncomeInvoice()).equals(fundsSalesContractOld.getIncomeInvoice() == null ? new BigDecimal(0.00) : fundsSalesContractOld.getIncomeInvoice())) {//判断是否修改了，若是则保存相关日志
            FundsSalesLog log = new FundsSalesLog();
            log.setOpDate(time);
            log.setContractCode(fundsSalesContractOld.getContractCode());
            log.setOpDesc(systemUser.getStaffName() + "修改进项发票，原金额:￥" + fundsSalesContractOld.getIncomeInvoice());
            log.setOpAmount(fundsSalesContract.getIncomeInvoice());
            fundsSalesLogDao.save(log);
        }
        if (!(fundsSalesContract.getOutInvoice() == null ? new BigDecimal(0.00) : fundsSalesContract.getOutInvoice()).equals(fundsSalesContractOld.getOutInvoice() == null ? new BigDecimal(0.00) : fundsSalesContractOld.getOutInvoice())) {//判断是否修改了，若是则保存相关日志
            FundsSalesLog log = new FundsSalesLog();
            log.setOpDate(time);
            log.setContractCode(fundsSalesContractOld.getContractCode());
            log.setOpDesc(systemUser.getStaffName() + "修改出项发票，原金额:￥" + fundsSalesContractOld.getOutInvoice());
            log.setOpAmount(fundsSalesContract.getOutInvoice());
            fundsSalesLogDao.save(log);
        }
        if (!(fundsSalesContract.getReceiveAmount() == null ? new BigDecimal(0.00) : fundsSalesContract.getReceiveAmount()).equals(fundsSalesContractOld.getReceiveAmount() == null ? new BigDecimal(0.00) : fundsSalesContractOld.getReceiveAmount())) {//判断是否修改了，若是则保存相关日志
            FundsSalesLog log = new FundsSalesLog();
            log.setOpDate(time);
            log.setContractCode(fundsSalesContractOld.getContractCode());
            log.setOpDesc(systemUser.getStaffName() + "修改合同回款，原金额:￥" + fundsSalesContractOld.getReceiveAmount());
            log.setOpAmount(fundsSalesContract.getReceiveAmount());
            fundsSalesLogDao.save(log);
        }

        fundsSalesContractOld.setBusinessCost(fundsSalesContract.getBusinessCost());
        fundsSalesContractOld.setContractAmount(fundsSalesContract.getContractAmount());
        fundsSalesContractOld.setOutInvoice(fundsSalesContract.getOutInvoice());
        fundsSalesContractOld.setReceiveAmount(fundsSalesContract.getReceiveAmount());
        fundsSalesContractOld.setIncomeInvoice(fundsSalesContract.getIncomeInvoice());
        try {
            getDao().update(fundsSalesContractOld);
            return "success";
        } catch (DataAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "保存失败。";
        }
    }

}
