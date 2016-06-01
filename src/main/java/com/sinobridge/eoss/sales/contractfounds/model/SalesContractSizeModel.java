/*
 * FileName: SalesContractSizeModel.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.sales.contractfounds.model;

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
 * Description: 
 * </p>
 *
 * @author liyx
 * @version 1.0

 * <p>
 * History: 
 *
 * Date                     Author         Version     Description
 * ---------------------------------------------------------------------------------
 * 2015年12月1日 下午4:29:41      liyx           1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Entity
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "salesContractFoundModel" })
@Table(name = "sales_contractSize")
public class SalesContractSizeModel extends DefaultBaseModel {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private SalesContractFoundModel salesContractFoundModel;
    private BigDecimal applyPrices;
    private String payType;
    private String payDesc;
    private String realPayUser;
    private Date realPayDate;
    private BigDecimal realPayPrices; //实际付款金额

    /**
     * @return the realPayUser
     */
    @Column(name = "realPayUser", length = 32)
    public String getRealPayUser() {
        return realPayUser;
    }

    /**
     * @param realPayUser the realPayUser to set
     */
    public void setRealPayUser(String realPayUser) {
        this.realPayUser = realPayUser;
    }

    /**
     * @return the realPayDate
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "realPayDate")
    public Date getRealPayDate() {
        return realPayDate;
    }

    /**
     * @param realPayDate the realPayDate to set
     */
    public void setRealPayDate(Date realPayDate) {
        this.realPayDate = realPayDate;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "applyFoundsID")
    public SalesContractFoundModel getSalesContractFoundModel() {
        return salesContractFoundModel;
    }

    public void setSalesContractFoundModel(SalesContractFoundModel salesContractFoundModel) {
        this.salesContractFoundModel = salesContractFoundModel;
    }

    @Column(name = "applyPrices")
    public BigDecimal getApplyPrices() {
        return applyPrices;
    }

    public void setApplyPrices(BigDecimal applyPrices) {
        this.applyPrices = applyPrices;
    }

    @Column(name = "payType", length = 128)
    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    @Column(name = "payDesc", length = 128)
    public String getPayDesc() {
        return payDesc;
    }

    public void setPayDesc(String payDesc) {
        this.payDesc = payDesc;
    }

    /**
     * @return the realPayPrices
     */
    @Column(name = "realPayPrices")
    public BigDecimal getRealPayPrices() {
        return realPayPrices;
    }

    /**
     * @param realPayPrices the realPayPrices to set
     */
    public void setRealPayPrices(BigDecimal realPayPrices) {
        this.realPayPrices = realPayPrices;
    }

}
