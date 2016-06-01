/*
 * FileName: ContractProductSnapShootModel.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.sales.contractchange.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * <p>
 * Description:快照表-合同产品 
 * </p>
 *
 * @author 3unshine
 * @version 1.0

 * <p>
 * History: 
 *
 * Date                     Author         Version     Description
 * ---------------------------------------------------------------------------------
 * 2014年7月23日 上午10:17:54          3unshine        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Entity
@Table(name = "sales_contract_product_snapshoot")
public class ContractProductSnapShootModel implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    private long id;
    private long productId;
    private long productType;
    private String productTypeName;
    private long productPartner;
    private String productPartnerName;
    private long productNo;
    private String productName;
    private ContractSnapShootModel contractSnapShootModel;
    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
    private int invoiceType;
    private int serviceType;
    private Date serviceStartDate;
    private Date serviceEndDate;
    
    private int isReady;
    private int surplusNum;
    private Long relateDeliveryProductId;
    
    private String remark;
    
    private String serialNumber;
    private int servicePeriod;
    private String equipmentSplace;
    

    /**
     * 
     */
    public ContractProductSnapShootModel() {
    }

    /**
     * @param id
     * @param productId
     * @param productType
     * @param productTypeName
     * @param productPartner
     * @param productPartnerName
     * @param productNo
     * @param productName
     * @param salesContractModel
     * @param quantity
     * @param unitPrice
     * @param totalPrice
     * @param invoiceType
     * @param serviceType
     * @param serviceStartDate
     * @param serviceEndDate
     * @param remark
     */
    public ContractProductSnapShootModel(long id, long productId, long productType, String productTypeName, long productPartner, String productPartnerName, long productNo, String productName, ContractSnapShootModel contractSnapShootModel, int quantity, BigDecimal unitPrice, BigDecimal totalPrice, int invoiceType, int serviceType, Date serviceStartDate, Date serviceEndDate, String remark) {
        super();
        this.id = id;
        this.productId = productId;
        this.productType = productType;
        this.productTypeName = productTypeName;
        this.productPartner = productPartner;
        this.productPartnerName = productPartnerName;
        this.productNo = productNo;
        this.productName = productName;
        this.contractSnapShootModel = contractSnapShootModel;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
        this.invoiceType = invoiceType;
        this.serviceType = serviceType;
        this.serviceStartDate = serviceStartDate;
        this.serviceEndDate = serviceEndDate;
        this.remark = remark;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "ContractProductSnapShootModel [id=" + id + ", productId=" + productId + ", productType=" + productType + ", productTypeName=" + productTypeName + ", productPartner=" + productPartner + ", productPartnerName=" + productPartnerName + ", productNo=" + productNo + ", productName=" + productName + ", contractSnapShootModel=" + contractSnapShootModel + ", quantity=" + quantity + ", unitPrice=" + unitPrice + ", totalPrice=" + totalPrice + ", invoiceType=" + invoiceType + ", serviceType=" + serviceType + ", serviceStartDate=" + serviceStartDate + ", serviceEndDate=" + serviceEndDate + ", remark=" + remark + "]";
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
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the productId
     */
    @Column(name = "ProductId", length = 20)
    public long getProductId() {
        return productId;
    }

    /**
     * @param productId the productId to set
     */
    public void setProductId(long productId) {
        this.productId = productId;
    }

    /**
     * @return the productType
     */
    @Column(name = "ProductType", length = 20)
    public long getProductType() {
        return productType;
    }

    /**
     * @param productType the productType to set
     */
    public void setProductType(long productType) {
        this.productType = productType;
    }

    /**
     * @return the productTypeName
     */
    @Column(name = "ProductTypeName", length = 100)
    public String getProductTypeName() {
        return productTypeName;
    }

    /**
     * @param productTypeName the productTypeName to set
     */
    public void setProductTypeName(String productTypeName) {
        this.productTypeName = productTypeName;
    }

    /**
     * @return the productPartner
     */
    @Column(name = "ProductPartner", length = 20)
    public long getProductPartner() {
        return productPartner;
    }

    /**
     * @param productPartner the productPartner to set
     */
    public void setProductPartner(long productPartner) {
        this.productPartner = productPartner;
    }

    /**
     * @return the productPartnerName
     */
    @Column(name = "ProductPartnerName", length = 100)
    public String getProductPartnerName() {
        return productPartnerName;
    }

    /**
     * @param productPartnerName the productPartnerName to set
     */
    public void setProductPartnerName(String productPartnerName) {
        this.productPartnerName = productPartnerName;
    }

    /**
     * @return the productNo
     */
    @Column(name = "ProductNo", length = 20)
    public long getProductNo() {
        return productNo;
    }

    /**
     * @param productNo the productNo to set
     */
    public void setProductNo(long productNo) {
        this.productNo = productNo;
    }

    /**
     * @return the productName
     */
    @Column(name = "ProductName", length = 100)
    public String getProductName() {
        return productName;
    }

    /**
     * @param productName the productName to set
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * @return the contractSnapShootModel
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ContractSnapShootId")
    public ContractSnapShootModel getContractSnapShootModel() {
        return contractSnapShootModel;
    }

    /**
     * @param contractSnapShootModel the contractSnapShootModel to set
     */
    public void setContractSnapShootModel(ContractSnapShootModel contractSnapShootModel) {
        this.contractSnapShootModel = contractSnapShootModel;
    }

    /**
     * @return the quantity
     */
    @Column(name = "Quantity", length = 4)
    public int getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * @return the unitPrice
     */
    @Column(name = "UnitPrice")
    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    /**
     * @param unitPrice the unitPrice to set
     */
    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    /**
     * @return the totalPrice
     */
    @Column(name = "TotalPrice")
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    /**
     * @param totalPrice the totalPrice to set
     */
    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    /**
     * @return the invoiceType
     */
    @Column(name = "InvoiceType", length = 4)
    public int getInvoiceType() {
        return invoiceType;
    }

    /**
     * @param invoiceType the invoiceType to set
     */
    public void setInvoiceType(int invoiceType) {
        this.invoiceType = invoiceType;
    }

    /**
     * @return the serviceType
     */
    @Column(name = "ServiceType", length = 4)
    public int getServiceType() {
        return serviceType;
    }

    /**
     * @param serviceType the serviceType to set
     */
    public void setServiceType(int serviceType) {
        this.serviceType = serviceType;
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
     * @param serviceStartDate the serviceStartDate to set
     */
    public void setServiceStartDate(Date serviceStartDate) {
        this.serviceStartDate = serviceStartDate;
    }

    /**
     * @return the serviceEndDate
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "ServiceEndDate")
    public Date getServiceEndDate() {
        return serviceEndDate;
    }

    /**
     * @param serviceEndDate the serviceEndDate to set
     */
    public void setServiceEndDate(Date serviceEndDate) {
        this.serviceEndDate = serviceEndDate;
    }

    @Column(name = "IsReady")
    public int getIsReady() {
        return isReady;
    }

    public void setIsReady(int isReady) {
        this.isReady = isReady;
    }

    @Column(name = "SurplusNum", length = 8)
    public int getSurplusNum() {
        return surplusNum;
    }

    public void setSurplusNum(int surplusNum) {
        this.surplusNum = surplusNum;
    }

    @Column(name = "RelateDeliveryProductId")
    public Long getRelateDeliveryProductId() {
        return relateDeliveryProductId;
    }

    public void setRelateDeliveryProductId(Long relateDeliveryProductId) {
        this.relateDeliveryProductId = relateDeliveryProductId;
    }

    /**
     * @return the remark
     */
    @Column(name = "Remark", length = 255)
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark the remark to set
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
    @Column(name = "SerialNumber", length = 36)
    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    @Column(name = "ServicePeriod")
    public int getServicePeriod() {
        return servicePeriod;
    }

    public void setServicePeriod(int servicePeriod) {
        this.servicePeriod = servicePeriod;
    }

    @Column(name = "EquipmentSplace", length = 36)
    public String getEquipmentSplace() {
        return equipmentSplace;
    }

    public void setEquipmentSplace(String equipmentSplace) {
        this.equipmentSplace = equipmentSplace;
    }
}
