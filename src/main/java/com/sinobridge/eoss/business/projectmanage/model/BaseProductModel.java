package com.sinobridge.eoss.business.projectmanage.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.sinobridge.eoss.business.projectmanage.BaseConstants;

/**
 * BaseProductmodel entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = BaseConstants.BASE_PRODUCTMODEL)
public class BaseProductModel implements java.io.Serializable {

    /** 
     * long serialVersionUID :       
     * @since  2013-11-25 guokemenng
     */
    private static final long serialVersionUID = 1L;
    public static final String PARTNERID = "partnerId";
    public static final String BRANDCODE = "brandCode";
    public static final String PRODUCTLINE = "productLine";
    public static final String PRODUCTTYPE = "productType";
    public static final String DELETEFLAG = "deleteFlag";
    public static final String PRODUCTMODEL = "productModel";
    // Fields    

    private String id;
    private String partnerId;
    private String productType;
    private String brandCode;
    private String productLine;
    private String productModel;
    private String productModelName;
    private String productDesc;
    private String productOid;
    private String productOutWard;
    private String productWeight;
    private String productIcon;
    private String productBgImage;
    private Date stopDate;
    private Date stopSevDate;
    private String productPdlife;
    private String productWorkDesc;

    private Short deleteFlag;
    
    private String listPrice;
    private String currency;

    // Constructors

    /** default constructor */
    public BaseProductModel() {
    }

    /** minimal constructor */
    public BaseProductModel(String id) {
        this.id = id;
    }

    /** full constructor */
    public BaseProductModel(String id, String partnerId, String productType, String brandCode, String productLine, String productModel, String productModelName, String productDesc, String productOid, String productOutWard, String productWeight, String productIcon, String productBgImage, Date stopDate, Date stopSevDate, String productPdlife, String productWorkDesc) {
        this.id = id;
        this.partnerId = partnerId;
        this.productType = productType;
        this.brandCode = brandCode;
        this.productLine = productLine;
        this.productModel = productModel;
        this.productModelName = productModelName;
        this.productDesc = productDesc;
        this.productOid = productOid;
        this.productOutWard = productOutWard;
        this.productWeight = productWeight;
        this.productIcon = productIcon;
        this.productBgImage = productBgImage;
        this.stopDate = stopDate;
        this.stopSevDate = stopSevDate;
        this.productPdlife = productPdlife;
        this.productWorkDesc = productWorkDesc;
    }

    // Property accessors
    @Id
    @Column(name = "Id", unique = true, nullable = false, length = 36)
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "PartnerId", length = 36)
    public String getPartnerId() {
        return this.partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    @Column(name = "ProductType", length = 36)
    public String getProductType() {
        return this.productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    @Column(name = "BrandCode", length = 32)
    public String getBrandCode() {
        return this.brandCode;
    }

    public void setBrandCode(String brandCode) {
        this.brandCode = brandCode;
    }

    @Column(name = "ProductLine", length = 36)
    public String getProductLine() {
        return this.productLine;
    }

    public void setProductLine(String productLine) {
        this.productLine = productLine;
    }

    @Column(name = "ProductModel", length = 36)
    public String getProductModel() {
        return this.productModel;
    }

    public void setProductModel(String productModel) {
        this.productModel = productModel;
    }

    @Column(name = "ProductModelName", length = 30)
    public String getProductModelName() {
        return this.productModelName;
    }

    public void setProductModelName(String productModelName) {
        this.productModelName = productModelName;
    }

    @Column(name = "ProductDesc", length = 256)
    public String getProductDesc() {
        return this.productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    @Column(name = "ProductOid", length = 64)
    public String getProductOid() {
        return this.productOid;
    }

    public void setProductOid(String productOid) {
        this.productOid = productOid;
    }

    @Column(name = "ProductOutWard", length = 64)
    public String getProductOutWard() {
        return this.productOutWard;
    }

    public void setProductOutWard(String productOutWard) {
        this.productOutWard = productOutWard;
    }

    @Column(name = "ProductWeight", length = 10)
    public String getProductWeight() {
        return this.productWeight;
    }

    public void setProductWeight(String productWeight) {
        this.productWeight = productWeight;
    }

    @Column(name = "ProductIcon", length = 10)
    public String getProductIcon() {
        return this.productIcon;
    }

    public void setProductIcon(String productIcon) {
        this.productIcon = productIcon;
    }

    @Column(name = "ProductBgImage", length = 10)
    public String getProductBgImage() {
        return this.productBgImage;
    }

    public void setProductBgImage(String productBgImage) {
        this.productBgImage = productBgImage;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "StopDate", length = 10)
    public Date getStopDate() {
        return this.stopDate;
    }

    public void setStopDate(Date stopDate) {
        this.stopDate = stopDate;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "StopSevDate", length = 10)
    public Date getStopSevDate() {
        return this.stopSevDate;
    }

    public void setStopSevDate(Date stopSevDate) {
        this.stopSevDate = stopSevDate;
    }

    @Column(name = "ProductPdlife", length = 10)
    public String getProductPdlife() {
        return this.productPdlife;
    }

    public void setProductPdlife(String productPdlife) {
        this.productPdlife = productPdlife;
    }

    @Column(name = "ProductWorkDesc", length = 128)
    public String getProductWorkDesc() {
        return this.productWorkDesc;
    }

    public void setProductWorkDesc(String productWorkDesc) {
        this.productWorkDesc = productWorkDesc;
    }

    @Column(name = "DeleteFlag", length = 1)
    public Short getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Short deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    @Column(name = "ListPrice", length = 36)
    public String getListPrice() {
        return listPrice;
    }

    public void setListPrice(String listPrice) {
        this.listPrice = listPrice;
    }

    @Column(name = "Currency", length = 36)
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
    
    
}