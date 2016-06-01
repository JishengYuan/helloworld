/*
 * FileName: SalesContract.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.sales.contract.dto;

import java.util.Date;

/**
 * <p>
 * Description: 合同主体信息与状态的的DTO类
 * </p>
 *
 * @author 3unshine
 * @version 1.0

 * <p>
 * History: 
 *
 * Date                     Author         Version     Description
 * ---------------------------------------------------------------------------------
 * 2014年7月15日 上午10:42:25          3unshine        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
public class SalesContractInfoAndStatus implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    private Long salesContractId;
    //合同编码
    private String contractCode;
    //合同金额
    private String contractAmount;
    //合同名称
    private String contractName;
    //合同简称
    private String contractShortName;
    //合同类型
    private int contractType;
    //合同类型
    private String contractTypeName;
    //合同创建时间
    private Date createTime;
    //合同创建人ID
    private long creator;

    private String creatorName;

    private String customerName;
    //合同状态
    private String contractState;
    //结算币种
    private String accountCurrency;
    //发票类型
    private String invoiceType;
    //收款方式
    private String receiveWay;
    //客户ID
    private Long customerId;
    //工单ID
    private Long processInstanceId;
    //是否为变更合同(0:否 1：是)
    private int isChanged;
    //订单处理人UserName
    private String orderProcessor;
    //附件ID
    private String attachIds;
    //订单采购状态
    private String orderStatus;
    //订单ID
    private String orderId;
    //收款状态
    private String reciveStatus;
    //盖章状态
    private String cachetStatus;
    //发票状态
    private String invoiceStatus;
    //变更状态
    private String changeStatus;

    private String currentNode;

    private Date taskReachTime;

    private String dealUserName;
    //备注
    private String remark;

    private String qu;
    private String sur;

    private String isold;

    //关联的备货合同ID
    private String bhSaleContractId;

    /**
     * @return the accountCurrency
     */
    public String getAccountCurrency() {
        return accountCurrency;
    }

    /**
     * @return the attachIds
     */
    public String getAttachIds() {
        return attachIds;
    }

    /**
     * @return the cachetStatus
     */
    public String getCachetStatus() {
        return cachetStatus;
    }

    /**
     * @return the changeStatus
     */
    public String getChangeStatus() {
        return changeStatus;
    }

    /**
     * @return the contractAmount
     */
    public String getContractAmount() {
        return contractAmount;
    }

    /**
     * @return the contractCode
     */
    public String getContractCode() {
        return contractCode;
    }

    /**
     * @return the contractName
     */
    public String getContractName() {
        return contractName;
    }

    /**
     * @return the contractShortName
     */
    public String getContractShortName() {
        return contractShortName;
    }

    /**
     * @return the contractState
     */
    public String getContractState() {
        return contractState;
    }

    /**
     * @return the contractType
     */
    public int getContractType() {
        return contractType;
    }

    /**
     * @return the createTime
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @return the creator
     */
    public long getCreator() {
        return creator;
    }

    /**
     * @return the currentNode
     */
    public String getCurrentNode() {
        return currentNode;
    }

    /**
     * @return the customerId
     */
    public Long getCustomerId() {
        return customerId;
    }

    /**
     * @return the dealUserName
     */
    public String getDealUserName() {
        return dealUserName;
    }

    /**
     * @return the invoiceStatus
     */
    public String getInvoiceStatus() {
        return invoiceStatus;
    }

    /**
     * @return the invoiceType
     */
    public String getInvoiceType() {
        return invoiceType;
    }

    /**
     * @return the isChanged
     */
    public int getIsChanged() {
        return isChanged;
    }

    /**
     * @return the orderProcessor
     */
    public String getOrderProcessor() {
        return orderProcessor;
    }

    /**
     * @return the orderStatus
     */
    public String getOrderStatus() {
        return orderStatus;
    }

    /**
     * @return the processInstanceId
     */
    public Long getProcessInstanceId() {
        return processInstanceId;
    }

    /**
     * @return the receiveWay
     */
    public String getReceiveWay() {
        return receiveWay;
    }

    /**
     * @return the reciveStatus
     */
    public String getReciveStatus() {
        return reciveStatus;
    }

    /**
     * @return the remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @return the id
     */
    public Long getSalesContractId() {
        return salesContractId;
    }

    /**
     * @return the taskReachTime
     */
    public Date getTaskReachTime() {
        return taskReachTime;
    }

    /**
     * 
     * @return
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * @param accountCurrency the accountCurrency to set
     */
    public void setAccountCurrency(String accountCurrency) {
        this.accountCurrency = accountCurrency;
    }

    /**
     * @param attachIds the attachIds to set
     */
    public void setAttachIds(String attachIds) {
        this.attachIds = attachIds;
    }

    /**
     * @param cachetStatus the cachetStatus to set
     */
    public void setCachetStatus(String cachetStatus) {
        this.cachetStatus = cachetStatus;
    }

    /**
     * @param changeStatus the changeStatus to set
     */
    public void setChangeStatus(String changeStatus) {
        this.changeStatus = changeStatus;
    }

    /**
     * @param contractAmount the contractAmount to set
     */
    public void setContractAmount(String contractAmount) {
        this.contractAmount = contractAmount;
    }

    /**
     * @param contractCode the contractCode to set
     */
    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }

    /**
     * @param contractName the contractName to set
     */
    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    /**
     * @param contractShortName the contractShortName to set
     */
    public void setContractShortName(String contractShortName) {
        this.contractShortName = contractShortName;
    }

    /**
     * @param contractState the contractState to set
     */
    public void setContractState(String contractState) {
        this.contractState = contractState;
    }

    /**
     * @param contractType the contractType to set
     */
    public void setContractType(int contractType) {
        this.contractType = contractType;
    }

    /**
     * @return the contractTypeName
     */
    public String getContractTypeName() {
        return contractTypeName;
    }

    /**
     * @param contractTypeName the contractTypeName to set
     */
    public void setContractTypeName(String contractTypeName) {
        this.contractTypeName = contractTypeName;
    }

    /**
     * @param createTime the createTime to set
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @param creator the creator to set
     */
    public void setCreator(long creator) {
        this.creator = creator;
    }

    /**
     * @return the creatorName
     */
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
     * @return the customerName
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * @param customerName the customerName to set
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * @param currentNode the currentNode to set
     */
    public void setCurrentNode(String currentNode) {
        this.currentNode = currentNode;
    }

    /**
     * @param customerId the customerId to set
     */
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    /**
     * @param dealUserName the dealUserName to set
     */
    public void setDealUserName(String dealUserName) {
        this.dealUserName = dealUserName;
    }

    /**
     * @param invoiceStatus the invoiceStatus to set
     */
    public void setInvoiceStatus(String invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    /**
     * @param invoiceType the invoiceType to set
     */
    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }

    /**
     * @param isChanged the isChanged to set
     */
    public void setIsChanged(int isChanged) {
        this.isChanged = isChanged;
    }

    /**
     * @param orderProcessor the orderProcessor to set
     */
    public void setOrderProcessor(String orderProcessor) {
        this.orderProcessor = orderProcessor;
    }

    /**
     * @param orderStatus the orderStatus to set
     */
    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    /**
     * @param processInstanceId the processInstanceId to set
     */
    public void setProcessInstanceId(Long processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    /**
     * @param receiveWay the receiveWay to set
     */
    public void setReceiveWay(String receiveWay) {
        this.receiveWay = receiveWay;
    }

    /**
     * @param reciveStatus the reciveStatus to set
     */
    public void setReciveStatus(String reciveStatus) {
        this.reciveStatus = reciveStatus;
    }

    /**
     * @param remark the remark to set
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * @param id the id to set
     */
    public void setSalesContractId(Long salesContractId) {
        this.salesContractId = salesContractId;
    }

    /**
     * @param taskReachTime the taskReachTime to set
     */
    public void setTaskReachTime(Date taskReachTime) {
        this.taskReachTime = taskReachTime;
    }

    public String getQu() {
        return qu;
    }

    public void setQu(String qu) {
        this.qu = qu;
    }

    public String getSur() {
        return sur;
    }

    public void setSur(String sur) {
        this.sur = sur;
    }

    /**
     * @return the isold
     */
    public String getIsold() {
        return isold;
    }

    /**
     * @param isold the isold to set
     */
    public void setIsold(String isold) {
        this.isold = isold;
    }

    /**
     * 
     * @param orderId
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getBhSaleContractId() {
        return bhSaleContractId;
    }

    public void setBhSaleContractId(String bhSaleContractId) {
        this.bhSaleContractId = bhSaleContractId;
    }

}
