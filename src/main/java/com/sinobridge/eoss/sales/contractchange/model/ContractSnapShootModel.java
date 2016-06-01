/*
 * FileName: ContractSnapShootModel.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.sales.contractchange.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * <p>
 * Description: 快照表-合同主体
 * </p>
 *
 * @author 3unshine
 * @version 1.0

 * <p>
 * History: 
 *
 * Date                     Author         Version     Description
 * ---------------------------------------------------------------------------------
 * 2014年7月22日 下午4:47:15          3unshine        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Entity
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "salesContractProductSnapShoots", "salesContractRecivePlanSnapShoots" })
@Table(name = "sales_contract_snapshoot")
public class ContractSnapShootModel implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    public static final String SALECONTRACTID = "saleContractId";
    //快照ID
    private long id;
    //合同变更申请ID
    private Long saleContractChangeApplyId;
    //合同版本号(v1)
    private String saleContractVersion;
    //合同变更时间
    private Date saleContractChangeCreateTime;
    //合同变更创建人ID
    private long saleContractChangeCreator;
    //合同ID
    private Long saleContractId;
    //合同编码
    private String contractCode;
    //合同金额
    private BigDecimal contractAmount;
    //合同名称
    private String contractName;
    //合同简称
    private String contractShortName;
    //合同类型
    private int contractType;
    //合同创建时间
    private Date createTime;
    //合同创建人ID
    private long creator;
    //合同创建人名称
    private String creatorName;
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
    //订单处理人UserName
    private String orderProcessor;
    //附件ID
    private String attachIds;
    //是否为变更合同(0:否 1：是)
    private int isChanged;
    //与产品快照一对多关系
    private Set<ContractProductSnapShootModel> salesContractProductSnapShoots = new HashSet<ContractProductSnapShootModel>(0);
    //与收款计划快照一对多关系
    private Set<ContractRecivePlanSnapShootModel> salesContractRecivePlanSnapShoots = new HashSet<ContractRecivePlanSnapShootModel>(0);
    //订单多对多关系
    private String businessOrderModelIds;

    //合同开始时间
    private Date serviceStartDate;
    //合同结束时间
    private Date ServiceEndDate;
    //预估毛利
    private String estimateProfit;
    private String contractRemark;

    private String estimateProfitDesc; //预估毛利描述
    private String busiEstimateProfit; //商务预估毛利
    private String busiEstimateProfitDesc; //商务预估毛利描述
    private Date hopeArriveTime; //期望到期日期

    private Date salesStartDate;
    private Date salesEndDate;

    private String hopeArrivePlace;

    /**
     * 
     */
    public ContractSnapShootModel() {
    }

    /**
     * @param id
     * @param saleContractChangeApplyId
     * @param saleContractVersion
     * @param saleContractChangeCreateTime
     * @param saleContractChangeCreator
     * @param saleContractId
     * @param contractCode
     * @param contractAmount
     * @param contractName
     * @param contractShortName
     * @param contractType
     * @param createTime
     * @param creator
     * @param contractState
     * @param accountCurrency
     * @param invoiceType
     * @param receiveWay
     * @param customerId
     * @param processInstanceId
     * @param orderProcessor
     * @param attachIds
     * @param isChanged
     * @param businessOrderModelIds
     */
    public ContractSnapShootModel(long id, Long saleContractChangeApplyId, String saleContractVersion, Date saleContractChangeCreateTime, long saleContractChangeCreator, Long saleContractId, String contractCode, BigDecimal contractAmount, String contractName, String contractShortName, int contractType, Date createTime, long creator, String creatorName, String contractState, String accountCurrency, String invoiceType, String receiveWay, Long customerId, Long processInstanceId, String orderProcessor, String attachIds, int isChanged, String businessOrderModelIds) {
        super();
        this.id = id;
        this.saleContractChangeApplyId = saleContractChangeApplyId;
        this.saleContractVersion = saleContractVersion;
        this.saleContractChangeCreateTime = saleContractChangeCreateTime;
        this.saleContractChangeCreator = saleContractChangeCreator;
        this.saleContractId = saleContractId;
        this.contractCode = contractCode;
        this.contractAmount = contractAmount;
        this.contractName = contractName;
        this.contractShortName = contractShortName;
        this.contractType = contractType;
        this.createTime = createTime;
        this.creator = creator;
        this.creatorName = creatorName;
        this.contractState = contractState;
        this.accountCurrency = accountCurrency;
        this.invoiceType = invoiceType;
        this.receiveWay = receiveWay;
        this.customerId = customerId;
        this.processInstanceId = processInstanceId;
        this.orderProcessor = orderProcessor;
        this.attachIds = attachIds;
        this.isChanged = isChanged;
        this.businessOrderModelIds = businessOrderModelIds;
    }

    /**
     * @return the accountCurrency
     */
    @Column(name = "AccountCurrency", length = 4)
    public String getAccountCurrency() {
        return accountCurrency;
    }

    /**
     * @return the attachIds
     */
    @Column(name = "AttachIds", length = 100)
    public String getAttachIds() {
        return attachIds;
    }

    /**
     * @return the businessOrderModelIds
     */
    @Column(name = "BusinessOrderModelIds", length = 1024)
    public String getBusinessOrderModelIds() {
        return businessOrderModelIds;
    }

    /**
     * @return the contractAmount
     */
    @Column(name = "ContractAmount")
    public BigDecimal getContractAmount() {
        return contractAmount;
    }

    /**
     * @return the contractCode
     */
    @Column(name = "ContractCode", length = 32)
    public String getContractCode() {
        return contractCode;
    }

    /**
     * @return the contractName
     */
    @Column(name = "ContractName", length = 128)
    public String getContractName() {
        return contractName;
    }

    @Column(name = "ContractRemark", length = 255)
    public String getContractRemark() {
        return contractRemark;
    }

    /**
     * @return the contractShortName
     */
    @Column(name = "ContractShortName", length = 128)
    public String getContractShortName() {
        return contractShortName;
    }

    /**
     * @return the contractState
     */
    @Column(name = "ContractState", length = 5)
    public String getContractState() {
        return contractState;
    }

    /**
     * @return the contractType
     */
    @Column(name = "ContractType", length = 4)
    public int getContractType() {
        return contractType;
    }

    /**
     * @return the createTime
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "CreateTime")
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @return the creator
     */
    @Column(name = "Creator", length = 20)
    public long getCreator() {
        return creator;
    }

    /**
     * @return the creatorName
     */
    @Column(name = "CreatorName", length = 30)
    public String getCreatorName() {
        return this.creatorName;
    }

    /**
     * @return the customerId
     */
    @Column(name = "CustomerId", length = 32)
    public Long getCustomerId() {
        return customerId;
    }

    @Column(name = "EstimateProfit", length = 30)
    public String getEstimateProfit() {
        return estimateProfit;
    }

    /**
     * @return the id
     */
    @Id
    @Column(name = "id", nullable = false, unique = true, length = 20)
    public long getId() {
        return id;
    }

    /**
     * @return the invoiceType
     */
    @Column(name = "InvoiceType", length = 4)
    public String getInvoiceType() {
        return invoiceType;
    }

    /**
     * @return the isChanged
     */
    @Column(name = "IsChanged", length = 4)
    public int getIsChanged() {
        return isChanged;
    }

    /**
     * @return the orderProcessor
     */
    @Column(name = "OrderProcessor", length = 32)
    public String getOrderProcessor() {
        return orderProcessor;
    }

    /**
     * @return the processInstanceId
     */
    @Column(name = "ProcessInstanceId", length = 11)
    public Long getProcessInstanceId() {
        return processInstanceId;
    }

    /**
     * @return the receiveWay
     */
    @Column(name = "ReceiveWay", length = 4)
    public String getReceiveWay() {
        return receiveWay;
    }

    /**
     * @return the saleContractChangeApplyId
     */
    @Column(name = "SaleContractChangeApplyId", length = 32)
    public Long getSaleContractChangeApplyId() {
        return saleContractChangeApplyId;
    }

    /**
     * @return the saleContractChangeCreateTime
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "SaleContractChangeCreateTime")
    public Date getSaleContractChangeCreateTime() {
        return saleContractChangeCreateTime;
    }

    /**
     * @return the saleContractChangeCreator
     */
    @Column(name = "SaleContractChangeCreator", length = 32)
    public long getSaleContractChangeCreator() {
        return saleContractChangeCreator;
    }

    /**
     * @return the saleContractId
     */
    @Column(name = "SaleContractId", length = 32)
    public Long getSaleContractId() {
        return saleContractId;
    }

    /**
     * @return the saleContractVersion
     */
    @Column(name = "SaleContractVersion", length = 5)
    public String getSaleContractVersion() {
        return saleContractVersion;
    }

    /**
     * @return the salesContractProductSnapShoots
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "contractSnapShootModel")
    public Set<ContractProductSnapShootModel> getSalesContractProductSnapShoots() {
        return salesContractProductSnapShoots;
    }

    /**
     * @return the salesContractRecivePlanSnapShoots
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "contractSnapShootModel")
    public Set<ContractRecivePlanSnapShootModel> getSalesContractRecivePlanSnapShoots() {
        return salesContractRecivePlanSnapShoots;
    }

    /**
     * @return the serviceEndDate
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "ServiceEndDate")
    public Date getServiceEndDate() {
        return ServiceEndDate;
    }

    /**
     * @return the serviceStartDate
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "ServiceStartDate")
    public Date getServiceStartDate() {
        return serviceStartDate;
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
     * @param businessOrderModelIds the businessOrderModelIds to set
     */
    public void setBusinessOrderModelIds(String businessOrderModelIds) {
        this.businessOrderModelIds = businessOrderModelIds;
    }

    /**
     * @param contractAmount the contractAmount to set
     */
    public void setContractAmount(BigDecimal contractAmount) {
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
     * @param contractRemark the contractRemark to set
     */
    public void setContractRemark(String contractRemark) {
        this.contractRemark = contractRemark;
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

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    /**
     * @param customerId the customerId to set
     */
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    /**
     * @param estimateProfit the estimateProfit to set
     */
    public void setEstimateProfit(String estimateProfit) {
        this.estimateProfit = estimateProfit;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
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
     * @param saleContractChangeApplyId the saleContractChangeApplyId to set
     */
    public void setSaleContractChangeApplyId(Long saleContractChangeApplyId) {
        this.saleContractChangeApplyId = saleContractChangeApplyId;
    }

    /**
     * @param saleContractChangeCreateTime the saleContractChangeCreateTime to set
     */
    public void setSaleContractChangeCreateTime(Date saleContractChangeCreateTime) {
        this.saleContractChangeCreateTime = saleContractChangeCreateTime;
    }

    /**
     * @param saleContractChangeCreator the saleContractChangeCreator to set
     */
    public void setSaleContractChangeCreator(long saleContractChangeCreator) {
        this.saleContractChangeCreator = saleContractChangeCreator;
    }

    /**
     * @param saleContractId the saleContractId to set
     */
    public void setSaleContractId(Long saleContractId) {
        this.saleContractId = saleContractId;
    }

    /**
     * @param saleContractVersion the saleContractVersion to set
     */
    public void setSaleContractVersion(String saleContractVersion) {
        this.saleContractVersion = saleContractVersion;
    }

    public void setSalesContractProductSnapShoots(Set<ContractProductSnapShootModel> salesContractProductSnapShoots) {
        this.salesContractProductSnapShoots = salesContractProductSnapShoots;
    }

    public void setSalesContractRecivePlanSnapShoots(Set<ContractRecivePlanSnapShootModel> salesContractRecivePlanSnapShoots) {
        this.salesContractRecivePlanSnapShoots = salesContractRecivePlanSnapShoots;
    }

    /**
     * @param serviceEndDate the serviceEndDate to set
     */
    public void setServiceEndDate(Date serviceEndDate) {
        ServiceEndDate = serviceEndDate;
    }

    /**
     * @param serviceStartDate the serviceStartDate to set
     */
    public void setServiceStartDate(Date serviceStartDate) {
        this.serviceStartDate = serviceStartDate;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "ContractSnapShootModel [id=" + id + ", saleContractChangeApplyId=" + saleContractChangeApplyId + ", saleContractVersion=" + saleContractVersion + ", saleContractChangeCreateTime=" + saleContractChangeCreateTime + ", saleContractChangeCreator=" + saleContractChangeCreator + ", saleContractId=" + saleContractId + ", contractCode=" + contractCode + ", contractAmount=" + contractAmount + ", contractName=" + contractName + ", contractShortName=" + contractShortName + ", contractType=" + contractType + ", createTime=" + createTime + ", creator=" + creator + ", creatorName=" + creatorName + ", contractState=" + contractState + ", accountCurrency=" + accountCurrency + ", invoiceType=" + invoiceType + ", receiveWay=" + receiveWay + ", customerId=" + customerId + ", processInstanceId=" + processInstanceId + ", orderProcessor=" + orderProcessor + ", attachIds=" + attachIds + ", isChanged=" + isChanged + ", salesContractProductSnapShoots=" + salesContractProductSnapShoots + ", salesContractRecivePlanSnapShoots=" + salesContractRecivePlanSnapShoots + ", businessOrderModelIds=" + businessOrderModelIds + "]";
    }

    /**
     * @return the estimateProfitDesc
     */
    @Column(name = "EstimateProfitDesc", length = 64)
    public String getEstimateProfitDesc() {
        return estimateProfitDesc;
    }

    /**
     * @param estimateProfitDesc the estimateProfitDesc to set
     */
    public void setEstimateProfitDesc(String estimateProfitDesc) {
        this.estimateProfitDesc = estimateProfitDesc;
    }

    /**
     * @return the busiEstimateProfit
     */
    @Column(name = "BusiEstimateProfit", length = 30)
    public String getBusiEstimateProfit() {
        return busiEstimateProfit;
    }

    /**
     * @param busiEstimateProfit the busiEstimateProfit to set
     */
    public void setBusiEstimateProfit(String busiEstimateProfit) {
        this.busiEstimateProfit = busiEstimateProfit;
    }

    /**
     * @return the busiEstimateProfitDesc
     */
    @Column(name = "BusiEstimateProfitDesc", length = 64)
    public String getBusiEstimateProfitDesc() {
        return busiEstimateProfitDesc;
    }

    /**
     * @param busiEstimateProfitDesc the busiEstimateProfitDesc to set
     */
    public void setBusiEstimateProfitDesc(String busiEstimateProfitDesc) {
        this.busiEstimateProfitDesc = busiEstimateProfitDesc;
    }

    /**
     * @return the hopeArriveTime
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "HopeArriveTime")
    public Date getHopeArriveTime() {
        return hopeArriveTime;
    }

    /**
     * @param hopeArriveTime the hopeArriveTime to set
     */
    public void setHopeArriveTime(Date hopeArriveTime) {
        this.hopeArriveTime = hopeArriveTime;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "SalesStartDate")
    public Date getSalesStartDate() {
        return salesStartDate;
    }

    public void setSalesStartDate(Date salesStartDate) {
        this.salesStartDate = salesStartDate;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "SalesEndDate")
    public Date getSalesEndDate() {
        return salesEndDate;
    }

    public void setSalesEndDate(Date salesEndDate) {
        this.salesEndDate = salesEndDate;
    }

    @Column(name = "HopeArrivePlace", length = 128)
    public String getHopeArrivePlace() {
        return hopeArrivePlace;
    }

    public void setHopeArrivePlace(String hopeArrivePlace) {
        this.hopeArrivePlace = hopeArrivePlace;
    }

}
