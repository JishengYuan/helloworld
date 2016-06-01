/*
 * FileName: BasePartnerProduct.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.business.projectmanage.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.sinobridge.eoss.business.projectmanage.BaseConstants;

/**
 * <code>BasePartnerProduct</code>
 * 
 * @version  1.0
 * @author  guokemenng
 * @since 1.0  2013-11-25
 */
@Entity
@Table(name = BaseConstants.BASE_PARTNERPRODUCT)
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "basePartnerInfo" })
public class BasePartnerProduct implements java.io.Serializable{

    // Fields    

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    public static final String PRODUCTTYPEID = "productTypeId";
    
    private String id;
    private BasePartnerInfo basePartnerInfo;
    private String productTypeId;
    private String brandId;
    private String icon;
    private String bgImage;

    // Property accessors
    @Id
    @Column(name = "Id", unique = true, nullable = false, length = 36)
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PartnerId")
    public BasePartnerInfo getBasePartnerInfo() {
        return this.basePartnerInfo;
    }

    public void setBasePartnerInfo(BasePartnerInfo basePartnerInfo) {
        this.basePartnerInfo = basePartnerInfo;
    }


    @Column(name = "BrandId", length = 36)
    public String getBrandId() {
        return this.brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    @Column(name = "Icon", length = 128)
    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Column(name = "BgImage", length = 128)
    public String getBgImage() {
        return this.bgImage;
    }

    public void setBgImage(String bgImage) {
        this.bgImage = bgImage;
    }

    public String getProductTypeId() {
        return productTypeId;
    }

    @Column(name = "ProductTypeId", length = 36)
    public void setProductTypeId(String productTypeId) {
        this.productTypeId = productTypeId;
    }
    
    
}
