package com.sinobridge.eoss.business.order.model;

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
import com.sinobridge.eoss.business.interPurchas.model.InterPurchasModel;
import com.sinobridge.eoss.business.interPurchas.model.InterPurchasProductModel;
import com.sinobridge.eoss.sales.contract.model.SalesContractModel;
import com.sinobridge.eoss.sales.contract.model.SalesContractProductModel;

/**
 * <p>
 * Description: 订单产品表
 * </p>
 *
 * @author wangya
 * @version 1.0

 * <p>
 * History: 
 *
 * Date                     Author         Version     Description
 * ---------------------------------------------------------------------------------
 * 2014年5月14日 上午10:42:25          wangya        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Entity
@Table(name = "business_order_product")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "businessOrder", "salesContractModel", "interPurchas", "interPurchasProduct" })
public class BusinessOrderProductModel extends DefaultBaseModel {
    private static final long serialVersionUID = 1L;
    public static final String SALESCONTRACT = "salesContractModel";
    public static final String SALESCONTRACTPRODUCT = "salesContractProductModel";
    public static final String BUSINESSORDER = "businessOrder";

    private String vendorCode; //TODO厂商表关联
    private String productNo; //TODO产品表关联
    private String productType;//产品类型
    private BusinessOrderModel businessOrder;
    private SalesContractModel salesContractModel;
    private SalesContractProductModel salesContractProductModel;
    private InterPurchasModel interPurchas;
    private InterPurchasProductModel interPurchasProduct;
    //数量
    private int quantity;
    //单价
    private BigDecimal unitPrice;
    //总价
    private BigDecimal subTotal;
    //原价格
    private String listPrice;
    //折扣率
    private String exchangeRate;
    //服务类型
    private String serviceType;
    //服务开始时间
    private Date serviceStartTime;
    //服务结束时间
    private Date serviceEndTime;
    //产品标志位：HT产品/NC内采
    private String mark;
    //产品说明
    private String remark;

    @Column(name = "Mark", length = 8)
    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    @Column(name = "VendorCode", length = 32)
    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    @Column(name = "ProductNo", length = 64)
    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    @Column(name = "Quantity", length = 8)
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OrderId")
    public BusinessOrderModel getBusinessOrder() {
        return businessOrder;
    }

    public void setBusinessOrder(BusinessOrderModel businessOrder) {
        this.businessOrder = businessOrder;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SaleContractId")
    public SalesContractModel getSalesContractModel() {
        return salesContractModel;
    }

    public void setSalesContractModel(SalesContractModel salesContractModel) {
        this.salesContractModel = salesContractModel;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "salesContractProductId")
    public SalesContractProductModel getSalesContractProductModel() {
        return salesContractProductModel;
    }

    public void setSalesContractProductModel(SalesContractProductModel salesContractProductModel) {
        this.salesContractProductModel = salesContractProductModel;
    }

    @Column(name = "ListPrice")
    public String getListPrice() {
        return listPrice;
    }

    public void setListPrice(String listPrice) {
        this.listPrice = listPrice;
    }

    @Column(name = "UnitPrice", precision = 11, scale = 2)
    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Column(name = "ExchangeRate", length = 32)
    public String getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(String exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    @Column(name = "SubTotal", precision = 11, scale = 2)
    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "InsterPurchasId")
    public InterPurchasModel getInterPurchas() {
        return interPurchas;
    }

    public void setInterPurchas(InterPurchasModel interPurchas) {
        this.interPurchas = interPurchas;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PurchasProductId")
    public InterPurchasProductModel getInterPurchasProduct() {
        return interPurchasProduct;
    }

    public void setInterPurchasProduct(InterPurchasProductModel interPurchasProduct) {
        this.interPurchasProduct = interPurchasProduct;
    }

    @Column(name = "ServiceType", length = 8)
    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    @Column(name = "ProductType", length = 32)
    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType2) {
        this.productType = productType2;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "ServiceStartTime")
    public Date getServiceStartTime() {
        return serviceStartTime;
    }

    public void setServiceStartTime(Date serviceStartTime) {
        this.serviceStartTime = serviceStartTime;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "ServiceEndTime")
    public Date getServiceEndTime() {
        return serviceEndTime;
    }

    public void setServiceEndTime(Date serviceEndTime) {
        this.serviceEndTime = serviceEndTime;
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

}
