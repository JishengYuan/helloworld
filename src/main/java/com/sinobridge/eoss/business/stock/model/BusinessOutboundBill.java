/*
 * FileName: BusinessOutboundBill.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.business.stock.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * <p>
 * Description: 
 * </p>
 *
 * @author tigq
 * @version 1.0

 * <p>
 * History: 
 *
 * Date                     Author         Version     Description
 * ---------------------------------------------------------------------------------
 * 2014年10月9日 下午2:06:27          tigq        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Entity
@Table(name = "business_outbound_bill")
public class BusinessOutboundBill {
    private Long id;
    private String outboundCode; //出库单号
    private Date outboundTime; //出库日期
    private String productCode; //产品编码
    private Integer productNum;//产品数量
    private String productSn; //序列号

    private String storePlace; //所入库房
    
    @Id
    @GenericGenerator(name = "generator", strategy = "com.sinobridge.base.core.hibernate.id.IdentifierGeneratorImpl")
    @GeneratedValue(generator = "generator")
    @Column(name = "id")
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "OutboundTime")
    public Date getOutboundTime() {
        return outboundTime;
    }

    public void setOutboundTime(Date outboundTime) {
        this.outboundTime = outboundTime;
    }

    @Column(name = "OutboundCode")
    public String getOutboundCode() {
        return outboundCode;
    }

    public void setOutboundCode(String outboundCode) {
        this.outboundCode = outboundCode;
    }

    @Column(name = "ProductCode")
    public String getProductCode() {
        return productCode;
    }

    /**
     * @param productCode the productCode to set
     */
    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    @Column(name = "ProductNum")
    public Integer getProductNum() {
        return productNum;
    }

    /**
     * @param productNum the productNum to set
     */
    public void setProductNum(Integer productNum) {
        this.productNum = productNum;
    }

    /**
     * @return the productSn
     */
    @Column(name = "ProductSn")
    public String getProductSn() {
        return productSn;
    }

    /**
     * @param productSn the productSn to set
     */
    public void setProductSn(String productSn) {
        this.productSn = productSn;
    }

    @Column(name = "StorePlace")
    public String getStorePlace() {
        return storePlace;
    }

    public void setStorePlace(String storePlace) {
        this.storePlace = storePlace;
    }

    
    
}
