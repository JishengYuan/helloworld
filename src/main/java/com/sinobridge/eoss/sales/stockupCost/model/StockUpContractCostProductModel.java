/*
 * FileName: SalesContract.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.sales.stockupCost.model;

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
 * Description: 商务确认成本部分，关联的备货合同的产品表
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
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "stockUpContractCostModel" })
@Table(name = "stockup_contractcost_product")
public class StockUpContractCostProductModel extends DefaultBaseModel {
    private static final long serialVersionUID = 1L;
    private long productType;
    private String productTypeName;
    private long productPartner;
    private String productPartnerName;
    //private long productNo;
    private String productName;
    private StockUpContractCostModel stockUpContractCostModel; //新产品合同ID
    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
    private int isReady;
    private int surplusNum;
    private Long relateDeliveryProductId;
    private String remark;
    private Date serviceStartDate;
    private Date serviceEndDate;

    private String bhSaleContractId; //备货合同ID
    private String orderId; //订单ID
    private String cpSaleContractId; //产品合同ID

    public void setServiceEndDate(Date serviceEndDate) {
        this.serviceEndDate = serviceEndDate;
    }

    public StockUpContractCostProductModel() {
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
    //    @Column(name = "ProductNo", length = 20)
    //    public long getProductNo() {
    //        return productNo;
    //    }

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
    @JoinColumn(name = "StockUpContractId")
    public StockUpContractCostModel getStockUpContractCostModel() {
        return stockUpContractCostModel;
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
    //    public void setProductNo(long productNo) {
    //        this.productNo = productNo;
    //    }

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
    public void setStockUpContractCostModel(StockUpContractCostModel stockUpContractCostModel) {
        this.stockUpContractCostModel = stockUpContractCostModel;
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

    @Temporal(TemporalType.DATE)
    @Column(name = "ServiceStartDate")
    public Date getServiceStartDate() {
        return serviceStartDate;
    }

    public void setServiceStartDate(Date serviceStartDate) {
        this.serviceStartDate = serviceStartDate;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "ServiceEndDate")
    public Date getServiceEndDate() {
        return serviceEndDate;
    }

    @Column(name = "bhSaleContractId", length = 100)
    public String getBhSaleContractId() {
        return bhSaleContractId;
    }

    public void setBhSaleContractId(String bhSaleContractId) {
        this.bhSaleContractId = bhSaleContractId;
    }

    @Column(name = "orderId", length = 100)
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Column(name = "CpSaleContractId", length = 32)
    public String getCpSaleContractId() {
        return cpSaleContractId;
    }

    public void setCpSaleContractId(String cpSaleContractId) {
        this.cpSaleContractId = cpSaleContractId;
    }

}
