/*
 * FileName: BusinessInboundBill.java
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

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.GenericGenerator;

import com.sinobridge.base.core.utils.CustomDateSerializer;

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
 * 2014年10月9日 下午1:26:24          tigq        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Entity
@Table(name = "business_inbound_bill")
public class BusinessInboundBill implements java.io.Serializable{
    /**
     * long serialVersionUID :       
     * @since  2014年10月9日 guokemenng
     */
    private static final long serialVersionUID = 1L;
    private Long id;
    private Date inboundTime; //入库日期
    private String inboundCode; //入库单号
    private String pono; //产品编码
    private String productCode; //产品编码
    private Integer productNum;//产品数量
    private String boxCode; //箱号
    private String boundLocation; //库位号
    private String productSn; //序列号
    
    /**
     * short state :
     * 1 到库存表  0 未到库存表       
     * @since  2014年10月11日 guokemenng
     */
    private short state;

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

    /**
     * @return the inboundTime
     */
    @JsonSerialize(using = CustomDateSerializer.class)
    @Column(name = "InboundTime")
    public Date getInboundTime() {
        return inboundTime;
    }

    /**
     * @param inboundTime the inboundTime to set
     */
    public void setInboundTime(Date inboundTime) {
        this.inboundTime = inboundTime;
    }

    /**
     * @return the inboundCode
     */
    @Column(name = "InboundCode")
    public String getInboundCode() {
        return inboundCode;
    }

    /**
     * @param inboundCode the inboundCode to set
     */
    public void setInboundCode(String inboundCode) {
        this.inboundCode = inboundCode;
    }

    /**
     * @return the pono
     */
    @Column(name = "pono")
    public String getPono() {
        return pono;
    }

    /**
     * @param pono the pono to set
     */
    public void setPono(String pono) {
        this.pono = pono;
    }

    /**
     * @return the productCode
     */
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

    /**
     * @return the productNum
     */
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
     * @return the boxCode
     */
    @Column(name = "BoxCode")
    public String getBoxCode() {
        return boxCode;
    }

    /**
     * @param boxCode the boxCode to set
     */
    public void setBoxCode(String boxCode) {
        this.boxCode = boxCode;
    }

    /**
     * @return the boundLocation
     */
    @Column(name = "BoundLocation")
    public String getBoundLocation() {
        return boundLocation;
    }

    /**
     * @param boundLocation the boundLocation to set
     */
    public void setBoundLocation(String boundLocation) {
        this.boundLocation = boundLocation;
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

    @Column(name = "State")
    public short getState() {
        return state;
    }
    public void setState(short state) {
        this.state = state;
    }
}
