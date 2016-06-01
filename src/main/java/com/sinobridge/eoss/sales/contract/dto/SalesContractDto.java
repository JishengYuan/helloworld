/*
 * FileName: SalesContract.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.sales.contract.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.sinobridge.eoss.business.order.model.BusinessOrderModel;
import com.sinobridge.eoss.sales.contract.model.SalesContractProductModel;
import com.sinobridge.eoss.sales.contract.model.SalesContractReceivePlanModel;

/**
 * <p>
 * Description: 合同的实体类
 * </p>
 *
 * @author 3unshine
 * @version 1.0

 * <p>
 * History: 
 *
 * Date                     Author         Version     Description
 * ---------------------------------------------------------------------------------
 * 2014年5月5日 上午10:42:25          3unshine        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */

public class SalesContractDto implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    public static final String CONTRACTNAME = "contractName";
    public static final String CONTRACTSHORTNAME = "contractShortName";
    public static final String CONTRACTCODE = "contractCode";
    public static final String CREATOR = "creator";
    public static final String CREATETIME = "createTime";
    public static final String CONTRACTSTATE = "contractState";
    public static final String CONTRACTAMOUNT = "contractAmount";
    public static final String CONTRACTTYPE = "contractType";
    public static final String CREATORNAME = "creatorName";
    public static final String PROCESSINSTANCEID = "processInstanceId";
    public static final String CUSTOMERID = "customerId";

    public static final String CLOSETIME = "closeTime";

    private Long id;
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
    //合同开始时间
    private Date serviceStartDate;
    //合同结束时间
    private Date serviceEndDate;
    //预估毛利
    private String estimateProfit;

    private String estimateProfitDesc; //预估毛利描述

    private String busiEstimateProfit; //商务预估毛利
    private String busiEstimateProfitDesc; //商务预估毛利描述
    private Date hopeArriveTime; //期望到期日期

    private String contractRemark;
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

    //关联备货合同
    private String relateDeliveryContract;
    //与产品一对多关系
    private Set<SalesContractProductModel> salesContractProductModel = new HashSet<SalesContractProductModel>(0);
    //与收款计划一对多关系
    private Set<SalesContractReceivePlanModel> salesContractReceivePlans = new HashSet<SalesContractReceivePlanModel>(0);
    //订单多对多关系
    private Set<BusinessOrderModel> businessOrderModel = new HashSet<BusinessOrderModel>(0);

    //合同关闭时间
    private Date closeTime;

    private Date salesStartDate;
    private Date salesEndDate;

    private String hopeArrivePlace;
    //---------------------以下为临时字段----------------------------
    //为接收多条SalesContractReceivePlanModel数据做临时映射字段
    private String tableData;
    //为接收多条SalesContractProductModel数据做临时映射字段
    private long[] contractProductIds;
    private long[] productTypes;
    private String[] productTypeNames;
    private long[] productPartners;
    private String[] productPartnerNames;
    private long[] productNos;
    private String[] productNames;
    private int[] quantitys;
    private float[] unitPrices;
    private float[] totalPrices;

    private Long[] relateContractProductId;
    private Long[] relateDeliveryProductId;

    private String[] serialNumber;
    private int[] servicePeriod;
    private String[] equipmentSplace;
    private String[] serviceStartDates;
    private String[] serviceEndDates;
    private String[] productRemarks;

    //-------------------------------------------------

    public SalesContractDto() {
    }

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

    public Set<BusinessOrderModel> getBusinessOrderModel() {
        return businessOrderModel;
    }

    public Date getCloseTime() {
        return closeTime;
    }

    public Date getSalesStartDate() {
        return salesStartDate;
    }

    public void setSalesStartDate(Date salesStartDate) {
        this.salesStartDate = salesStartDate;
    }

    public Date getSalesEndDate() {
        return salesEndDate;
    }

    public void setSalesEndDate(Date salesEndDate) {
        this.salesEndDate = salesEndDate;
    }

    public BigDecimal getContractAmount() {
        return this.contractAmount;
    }

    public String getContractCode() {
        return contractCode;
    }

    public String getContractName() {
        return this.contractName;
    }

    /**
     * @return the contractProductIds
     */
    public long[] getContractProductIds() {
        return contractProductIds;
    }

    /**
     * @return the contractRemark
     */
    public String getContractRemark() {
        return contractRemark;
    }

    public String getContractShortName() {
        return this.contractShortName;
    }

    /**
     * @return the contractState
     */
    public String getContractState() {
        return contractState;
    }

    public int getContractType() {
        return this.contractType;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public long getCreator() {
        return this.creator;
    }

    public String getCreatorName() {
        return this.creatorName;
    }

    /**
     * @return the customerId
     */
    public Long getCustomerId() {
        return customerId;
    }

    /**
     * @return the estimateProfit
     */
    public String getEstimateProfit() {
        return estimateProfit;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
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
     * @return the processInstanceId
     */
    public Long getProcessInstanceId() {
        return processInstanceId;
    }

    /**
     * @return the productNames
     */
    public String[] getProductNames() {
        return productNames;
    }

    /**
     * @return the productNos
     */
    public long[] getProductNos() {
        return productNos;
    }

    /**
     * @return the productPartnerNames
     */
    public String[] getProductPartnerNames() {
        return productPartnerNames;
    }

    /**
     * @return the productPartners
     */
    public long[] getProductPartners() {
        return productPartners;
    }

    /**
     * @return the productTypeNames
     */
    public String[] getProductTypeNames() {
        return productTypeNames;
    }

    /**
     * @return the productTypes
     */
    public long[] getProductTypes() {
        return productTypes;
    }

    /**
     * @return the quantitys
     */
    public int[] getQuantitys() {
        return quantitys;
    }

    /**
     * @return the receiveWay
     */
    public String getReceiveWay() {
        return receiveWay;
    }

    /**
     * @return the relateDeliveryContract
     */
    public String getRelateDeliveryContract() {
        return relateDeliveryContract;
    }

    public Set<SalesContractProductModel> getSalesContractProductModel() {
        return salesContractProductModel;
    }

    /**
     * @return the salesContractReceivePlans
     */
    public Set<SalesContractReceivePlanModel> getSalesContractReceivePlans() {
        return salesContractReceivePlans;
    }

    /**
     * @return the serviceEndDate
     */
    public Date getServiceEndDate() {
        return serviceEndDate;
    }

    /**
     * @return the serviceStartDate
     */
    public Date getServiceStartDate() {
        return serviceStartDate;
    }

    public String getHopeArrivePlace() {
        return hopeArrivePlace;
    }

    public void setHopeArrivePlace(String hopeArrivePlace) {
        this.hopeArrivePlace = hopeArrivePlace;
    }

    //-------------------------------------------------
    //为接收多条SalesContractReceivePlanModel数据做临时映射字段
    /**
     * @return the tableData
     */
    public String getTableData() {
        return tableData;
    }

    /**
     * @return the totalPrices
     */
    public float[] getTotalPrices() {
        return totalPrices;
    }

    /**
     * @return the unitPrices
     */
    public float[] getUnitPrices() {
        return unitPrices;
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

    public void setBusinessOrderModel(Set<BusinessOrderModel> businessOrderModel) {
        this.businessOrderModel = businessOrderModel;
    }

    public void setCloseTime(Date closeTime) {
        this.closeTime = closeTime;
    }

    public void setContractAmount(BigDecimal contractAmount) {
        this.contractAmount = contractAmount;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    /**
     * @param contractProductIds the contractProductIds to set
     */
    public void setContractProductIds(long[] contractProductIds) {
        this.contractProductIds = contractProductIds;
    }

    /**
     * @param contractRemark the contractRemark to set
     */
    public void setContractRemark(String contractRemark) {
        this.contractRemark = contractRemark;
    }

    public void setContractShortName(String contractShortName) {
        this.contractShortName = contractShortName;
    }

    /**
     * @param contractState the contractState to set
     */
    public void setContractState(String contractState) {
        this.contractState = contractState;
    }

    public void setContractType(int contractType) {
        this.contractType = contractType;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setCreator(long creator) {
        this.creator = creator;
    }

    //为接收多条SalesContractProductModel数据做临时映射字段

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
    public void setId(Long id) {
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
     * @param orderProcessorId the orderProcessor to set
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
     * @param productNames the productNames to set
     */
    public void setProductNames(String[] productNames) {
        this.productNames = productNames;
    }

    /**
     * @param productNos the productNos to set
     */
    public void setProductNos(long[] productNos) {
        this.productNos = productNos;
    }

    /**
     * @param productPartnerNames the productPartnerNames to set
     */
    public void setProductPartnerNames(String[] productPartnerNames) {
        this.productPartnerNames = productPartnerNames;
    }

    /**
     * @param productPartners the productPartners to set
     */
    public void setProductPartners(long[] productPartners) {
        this.productPartners = productPartners;
    }

    /**
     * @param productTypeNames the productTypeNames to set
     */
    public void setProductTypeNames(String[] productTypeNames) {
        this.productTypeNames = productTypeNames;
    }

    /**
     * @param productTypes the productTypes to set
     */
    public void setProductTypes(long[] productTypes) {
        this.productTypes = productTypes;
    }

    /**
     * @param quantitys the quantitys to set
     */
    public void setQuantitys(int[] quantitys) {
        this.quantitys = quantitys;
    }

    /**
     * @param receiveWay the receiveWay to set
     */
    public void setReceiveWay(String receiveWay) {
        this.receiveWay = receiveWay;
    }

    /**
     * @param relateDeliveryContract the relateDeliveryContract to set
     */
    public void setRelateDeliveryContract(String relateDeliveryContract) {
        this.relateDeliveryContract = relateDeliveryContract;
    }

    public void setSalesContractProductModel(Set<SalesContractProductModel> salesContractProductModel) {
        this.salesContractProductModel = salesContractProductModel;
    }

    public void setSalesContractReceivePlans(Set<SalesContractReceivePlanModel> salesContractReceivePlans) {
        this.salesContractReceivePlans = salesContractReceivePlans;
    }

    /**
     * @param serviceEndDate the serviceEndDate to set
     */
    public void setServiceEndDate(Date serviceEndDate) {
        this.serviceEndDate = serviceEndDate;
    }

    //-------------------------------------------------

    /**
     * @param serviceStartDate the serviceStartDate to set
     */

    public void setServiceStartDate(Date serviceStartDate) {
        this.serviceStartDate = serviceStartDate;
    }

    /**
     * @param tableData the tableData to set
     */
    public void setTableData(String tableData) {
        this.tableData = tableData;
    }

    /**
     * @param totalPrices the totalPrices to set
     */
    public void setTotalPrices(float[] totalPrices) {
        this.totalPrices = totalPrices;
    }

    /**
     * @param unitPrices the unitPrices to set
     */
    public void setUnitPrices(float[] unitPrices) {
        this.unitPrices = unitPrices;
    }

    @Transient
    public Long[] getRelateContractProductId() {
        return relateContractProductId;
    }

    public void setRelateContractProductId(Long[] relateContractProductId) {
        this.relateContractProductId = relateContractProductId;
    }

    @Transient
    public Long[] getRelateDeliveryProductId() {
        return relateDeliveryProductId;
    }

    public void setRelateDeliveryProductId(Long[] relateDeliveryProductId) {
        this.relateDeliveryProductId = relateDeliveryProductId;
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

    @Transient
    public String[] getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String[] serialNumber) {
        this.serialNumber = serialNumber;
    }

    @Transient
    public String[] getEquipmentSplace() {
        return equipmentSplace;
    }

    @Transient
    public int[] getServicePeriod() {
        return servicePeriod;
    }

    public void setServicePeriod(int[] servicePeriod) {
        this.servicePeriod = servicePeriod;
    }

    public void setEquipmentSplace(String[] equipmentSplace) {
        this.equipmentSplace = equipmentSplace;
    }

    @Transient
    public String[] getServiceStartDates() {
        return serviceStartDates;
    }

    public void setServiceStartDates(String[] serviceStartDates) {
        this.serviceStartDates = serviceStartDates;
    }

    @Transient
    public String[] getServiceEndDates() {
        return serviceEndDates;
    }

    public void setServiceEndDates(String[] serviceEndDates) {
        this.serviceEndDates = serviceEndDates;
    }

    @Transient
    public String[] getProductRemarks() {
        return productRemarks;
    }

    public void setProductRemarks(String[] productRemarks) {
        this.productRemarks = productRemarks;
    }

}
