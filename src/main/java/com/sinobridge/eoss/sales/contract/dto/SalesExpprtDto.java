/*
 * FileName: SalesExpprtDto.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.sales.contract.dto;

import java.math.BigDecimal;

/**
 * <p>
 * Description: 合同导出
 * </p>
 *
 * @author vermouth
 * @version 1.0

 * <p>
 * History: 
 *
 * Date                     Author         Version     Description
 * ---------------------------------------------------------------------------------
 * 2015年1月16日 下午4:21:20          vermouth        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
public class SalesExpprtDto implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    //客户经理
    private String creatorName;
    //客户名称 
    private String customerName;
    //省份
    private String provinces;
    //合同编码
    private String contractCode;
    //合同名称
    private String contractName;
    //合同类型
    private String contractType;
    //合同状态
    private String contractState;
    //合同订单状态
    private String contractOrderState;
    //合同创建时间
    private String salesStartDate;
    //合同文本盖章日期
    private String cachetdate;
    //合同金额
    private BigDecimal contractAmount;
    //资金占用数
    private BigDecimal capital;
    //合同下采购成本
    private BigDecimal procurementCosts;
    //占用资金成本
    private BigDecimal capitalCost;
    //项目实施成本
    private BigDecimal projectCost;
    //合同毛利
    private BigDecimal contractMargin;
    //税后毛利
    private BigDecimal afterTaxMargin;
    //毛利率(%)
    private BigDecimal profitRate;
    //已结算合同额
    private BigDecimal costAmount;
    //已开发票金额
    private BigDecimal invoiceAmount;
    //未开发票金额
    private BigDecimal uninvoiceAmount;
    //已收款
    private BigDecimal receiveAmount;
    //未收款
    private BigDecimal unreceiveAmount;
    //已付款
    private BigDecimal payment;
    //未付款
    private BigDecimal unpayment;
    //是否已结算
    private String isCost;
    //发票类型
    private String invoiceType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getProvinces() {
        return provinces;
    }

    public void setProvinces(String provinces) {
        this.provinces = provinces;
    }

    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public String getContractState() {
        return contractState;
    }

    public void setContractState(String contractState) {
        this.contractState = contractState;
    }

    public String getCachetdate() {
        return cachetdate;
    }

    public void setCachetdate(String cachetdate) {
        this.cachetdate = cachetdate;
    }

    public BigDecimal getProcurementCosts() {
        return procurementCosts;
    }

    public void setProcurementCosts(BigDecimal procurementCosts) {
        this.procurementCosts = procurementCosts;
    }

    public String getIsCost() {
        return isCost;
    }

    public void setIsCost(String isCost) {
        this.isCost = isCost;
    }

    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public String getSalesStartDate() {
        return salesStartDate;
    }

    public void setSalesStartDate(String salesStartDate) {
        this.salesStartDate = salesStartDate;
    }

    public BigDecimal getContractAmount() {
        return contractAmount;
    }

    public void setContractAmount(BigDecimal contractAmount) {
        this.contractAmount = contractAmount;
    }

    public BigDecimal getCapital() {
        return capital;
    }

    public void setCapital(BigDecimal capital) {
        this.capital = capital;
    }

    public BigDecimal getCapitalCost() {
        return capitalCost;
    }

    public void setCapitalCost(BigDecimal capitalCost) {
        this.capitalCost = capitalCost;
    }

    public BigDecimal getProjectCost() {
        return projectCost;
    }

    public void setProjectCost(BigDecimal projectCost) {
        this.projectCost = projectCost;
    }

    public BigDecimal getContractMargin() {
        return contractMargin;
    }

    public void setContractMargin(BigDecimal contractMargin) {
        this.contractMargin = contractMargin;
    }

    public BigDecimal getAfterTaxMargin() {
        return afterTaxMargin;
    }

    public void setAfterTaxMargin(BigDecimal afterTaxMargin) {
        this.afterTaxMargin = afterTaxMargin;
    }

    public BigDecimal getProfitRate() {
        return profitRate;
    }

    public void setProfitRate(BigDecimal profitRate) {
        this.profitRate = profitRate;
    }

    public BigDecimal getCostAmount() {
        return costAmount;
    }

    public void setCostAmount(BigDecimal costAmount) {
        this.costAmount = costAmount;
    }

    public BigDecimal getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(BigDecimal invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    public BigDecimal getUninvoiceAmount() {
        return uninvoiceAmount;
    }

    public void setUninvoiceAmount(BigDecimal uninvoiceAmount) {
        this.uninvoiceAmount = uninvoiceAmount;
    }

    public BigDecimal getReceiveAmount() {
        return receiveAmount;
    }

    public void setReceiveAmount(BigDecimal receiveAmount) {
        this.receiveAmount = receiveAmount;
    }

    public BigDecimal getUnreceiveAmount() {
        return unreceiveAmount;
    }

    public void setUnreceiveAmount(BigDecimal unreceiveAmount) {
        this.unreceiveAmount = unreceiveAmount;
    }

    public BigDecimal getPayment() {
        return payment;
    }

    public void setPayment(BigDecimal payment) {
        this.payment = payment;
    }

    public BigDecimal getUnpayment() {
        return unpayment;
    }

    public void setUnpayment(BigDecimal unpayment) {
        this.unpayment = unpayment;
    }

    public String getContractOrderState() {
        return contractOrderState;
    }

    public void setContractOrderState(String contractOrderState) {
        this.contractOrderState = contractOrderState;
    }

}
