/*
 * FileName: SalesContract.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.sales.contract.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.sinobridge.base.core.model.imp.DefaultBaseModel;

/**
 * <p>
 * Description: 合同的产品表
 * </p>
 *
 * @author 3unshine
 * @version 1.0

 * <p>
 * History: 
 *
 * Date                     Author         Version     Description
 * ---------------------------------------------------------------------------------
 * 2014年5月14日 上午10:42:25          3unshine        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Entity
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "salesContractModel"})
@Table(name = "sales_contract_product")
public class SalesContractProductModel extends DefaultBaseModel {
    private static final long serialVersionUID = 1L;
    public static final String SALESCONTRACT = "salesContractModel";
    private long productType;
    private String productTypeName;
    private long productPartner;
    private String productPartnerName;
    private long productNo;
    private String productName;
    private SalesContractModel salesContractModel;
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

    public SalesContractProductModel() {
    }

    /**
     * @return the invoiceType
     */
    @Column(name = "InvoiceType", length = 4)
    public int getInvoiceType() {
        return invoiceType;
    }

    /**
     * @return the isReady
     */
    @Column(name = "IsReady")
    public int getIsReady() {
        return isReady;
    }

    /**
     * @return the productName
     */
    @Column(name = "ProductName", length = 100)
    public String getProductName() {
        return productName;
    }

    /**
     * @return the productNo
     */
    @Column(name = "ProductNo", length = 20)
    public long getProductNo() {
        return productNo;
    }

    /**
     * @return the productPartner
     */
    @Column(name = "ProductPartner", length = 20)
    public long getProductPartner() {
        return productPartner;
    }

    /**
     * @return the productPartnerName
     */
    @Column(name = "ProductPartnerName", length = 100)
    public String getProductPartnerName() {
        return productPartnerName;
    }

    /**
     * @return the productType
     */
    @Column(name = "ProductType", length = 20)
    public long getProductType() {
        return productType;
    }

    /**
     * @return the productTypeName
     */
    @Column(name = "ProductTypeName", length = 100)
    public String getProductTypeName() {
        return productTypeName;
    }

    /**
     * @return the quantity
     */
    @Column(name = "Quantity", length = 8)
    public int getQuantity() {
        return quantity;
    }

    /**
     * @return the relateDeliveryProductId
     */
    @Column(name = "RelateDeliveryProductId")
    public Long getRelateDeliveryProductId() {
        return relateDeliveryProductId;
    }

    /**
     * @return the remark
     */
    @Column(name = "Remark", length = 255)
    public String getRemark() {
        return remark;
    }

    /**
     * @return the salesContractModel
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SaleContractId")
    public SalesContractModel getSalesContractModel() {
        return salesContractModel;
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
     * @return the serviceStartDate
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "ServiceStartDate")
    public Date getServiceStartDate() {
        return serviceStartDate;
    }

    /**
     * @return the serviceType
     */
    @Column(name = "ServiceType", length = 4)
    public int getServiceType() {
        return serviceType;
    }

    /**
     * @return the surplusNum
     */
    @Column(name = "SurplusNum", length = 8)
    public int getSurplusNum() {
        return surplusNum;
    }

    /**
     * @return the totalPrice
     */
    @Column(name = "TotalPrice")
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    /**
     * @return the unitPrice
     */
    @Column(name = "UnitPrice")
    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    /**
     * @param invoiceType the invoiceType to set
     */
    public void setInvoiceType(int invoiceType) {
        this.invoiceType = invoiceType;
    }

    /**
     * @param isReady the isReady to set
     */
    public void setIsReady(int isReady) {
        this.isReady = isReady;
    }

    /**
     * @param productName the productName to set
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * @param productNo the productNo to set
     */
    public void setProductNo(long productNo) {
        this.productNo = productNo;
    }

    /**
     * @param productPartner the productPartner to set
     */
    public void setProductPartner(long productPartner) {
        this.productPartner = productPartner;
    }

    /**
     * @param productPartnerName the productPartnerName to set
     */
    public void setProductPartnerName(String productPartnerName) {
        this.productPartnerName = productPartnerName;
    }

    /**
     * @param productType the productType to set
     */
    public void setProductType(long productType) {
        this.productType = productType;
    }

    /**
     * @param productTypeName the productTypeName to set
     */
    public void setProductTypeName(String productTypeName) {
        this.productTypeName = productTypeName;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * @param relateDeliveryProductId the relateDeliveryProductId to set
     */
    public void setRelateDeliveryProductId(Long relateDeliveryProductId) {
        this.relateDeliveryProductId = relateDeliveryProductId;
    }

    /**
     * @param remark the remark to set
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * @param salesContractModel the salesContractModel to set
     */
    public void setSalesContractModel(SalesContractModel salesContractModel) {
        this.salesContractModel = salesContractModel;
    }

    /**
     * @param serviceEndDate the serviceEndDate to set
     */
    public void setServiceEndDate(Date serviceEndDate) {
        this.serviceEndDate = serviceEndDate;
    }

    /**
     * @param serviceStartDate the serviceStartDate to set
     */
    public void setServiceStartDate(Date serviceStartDate) {
        this.serviceStartDate = serviceStartDate;
    }

    /**
     * @param serviceType the serviceType to set
     */
    public void setServiceType(int serviceType) {
        this.serviceType = serviceType;
    }

    /**
     * @param surplusNum the surplusNum to set
     */
    public void setSurplusNum(int surplusNum) {
        this.surplusNum = surplusNum;
    }

    /**
     * @param totalPrice the totalPrice to set
     */
    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    /**
     * @param unitPrice the unitPrice to set
     */
    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
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
