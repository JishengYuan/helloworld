/*
 * FileName: FundsSalesContract.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.salesfundsCost.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.sinobridge.base.core.model.imp.DefaultBaseModel;

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
 * 2015年12月23日 下午5:00:38          tigq        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Entity
@Table(name = "funds_salesContract")
public class FundsSalesContract extends DefaultBaseModel {
    private static final long serialVersionUID = 4047417105316954078L;

    private String contractCode;
    private BigDecimal contractAmount;
    private String contractName;
    private Date salesStartDate;
    private BigDecimal businessCost;
    private BigDecimal incomeInvoice;
    private BigDecimal outInvoice;
    private BigDecimal receiveAmount;
    private BigDecimal finalAmount;
    private String fundstate;
    private String creatorName;
    private String bhflag;

    /**
     * @return the contractCode
     */
    @Column(name = "contractCode", length = 32)
    public String getContractCode() {
        return contractCode;
    }

    /**
     * @param contractCode the contractCode to set
     */
    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }

    /**
     * @return the contractAmount
     */
    @Column(name = "contractAmount")
    public BigDecimal getContractAmount() {
        return contractAmount;
    }

    /**
     * @param contractAmount the contractAmount to set
     */
    public void setContractAmount(BigDecimal contractAmount) {
        this.contractAmount = contractAmount;
    }

    /**
     * @return the contractName
     */
    @Column(name = "contractName", length = 128)
    public String getContractName() {
        return contractName;
    }

    /**
     * @param contractName the contractName to set
     */
    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    /**
     * @return the salesStartDate
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "salesStartDate")
    public Date getSalesStartDate() {
        return salesStartDate;
    }

    /**
     * @param salesStartDate the salesStartDate to set
     */
    public void setSalesStartDate(Date salesStartDate) {
        this.salesStartDate = salesStartDate;
    }

    /**
     * @return the businessCost
     */
    @Column(name = "businessCost")
    public BigDecimal getBusinessCost() {
        return businessCost;
    }

    /**
     * @param businessCost the businessCost to set
     */
    public void setBusinessCost(BigDecimal businessCost) {
        this.businessCost = businessCost;
    }

    /**
     * @return the incomeInvoice
     */
    @Column(name = "incomeInvoice")
    public BigDecimal getIncomeInvoice() {
        return incomeInvoice;
    }

    /**
     * @param incomeInvoice the incomeInvoice to set
     */
    public void setIncomeInvoice(BigDecimal incomeInvoice) {
        this.incomeInvoice = incomeInvoice;
    }

    /**
     * @return the outInvoide
     */
    @Column(name = "outInvoice")
    public BigDecimal getOutInvoice() {
        return outInvoice;
    }

    /**
     * @param outInvoide the outInvoide to set
     */
    public void setOutInvoice(BigDecimal outInvoice) {
        this.outInvoice = outInvoice;
    }

    /**
     * @return the receiveAmount
     */
    @Column(name = "receiveAmount")
    public BigDecimal getReceiveAmount() {
        return receiveAmount;
    }

    /**
     * @param receiveAmount the receiveAmount to set
     */
    public void setReceiveAmount(BigDecimal receiveAmount) {
        this.receiveAmount = receiveAmount;
    }

    /**
     * @return the finalAmount
     */
    @Column(name = "finalAmount")
    public BigDecimal getFinalAmount() {
        return finalAmount;
    }

    /**
     * @param finalAmount the finalAmount to set
     */
    public void setFinalAmount(BigDecimal finalAmount) {
        this.finalAmount = finalAmount;
    }

    /**
     * @return the fundstate
     */
    @Column(name = "fundstate")
    public String getFundstate() {
        return fundstate;
    }

    /**
     * @param fundstate the fundstate to set
     */
    public void setFundstate(String fundstate) {
        this.fundstate = fundstate;
    }

    /**
     * @return the creatorName
     */
    @Column(name = "creatorName", length = 32)
    public String getCreatorName() {
        return creatorName;
    }

    /**
     * @param creatorName the creatorName to set
     */
    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    /**
     * @return the bhflag
     */
    @Column(name = "bhflag", length = 2)
    public String getBhflag() {
        return bhflag;
    }

    /**
     * @param bhflag the bhflag to set
     */
    public void setBhflag(String bhflag) {
        this.bhflag = bhflag;
    }

}
