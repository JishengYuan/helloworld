/*
 * FileName: SalesContractQualificationModel.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.sales.contractfounds.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

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
 * 2015年12月1日 下午4:39:41      liyx           1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Entity
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "salesContractFoundModel" })
@Table(name = "sales_contractQualification")
public class SalesContractQualificationModel extends DefaultBaseModel {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private SalesContractFoundModel salesContractFoundModel;
    private Long qualificationId;
    private String qualificationName;
    private String qualificationsState;
    private String creatorName; //申请人名称
    private Date forecastBorrowDate;
    private Date forecastReturnDate;
    private String agreeBorrowUser;
    private Date realBorrowDate;
    private Date realReturnDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "applyFoundsID")
    public SalesContractFoundModel getSalesContractFoundModel() {
        return salesContractFoundModel;
    }

    public void setSalesContractFoundModel(SalesContractFoundModel salesContractFoundModel) {
        this.salesContractFoundModel = salesContractFoundModel;
    }

    @Column(name = "qualificationId", length = 20)
    public Long getQualificationId() {
        return qualificationId;
    }

    public void setQualificationId(Long qualificationId) {
        this.qualificationId = qualificationId;
    }

    /**
     * @return the qualificationsState
     */
    @Column(name = "qualificationState", length = 1)
    public String getQualificationsState() {
        return qualificationsState;
    }

    /**
     * @param qualificationsState the qualificationsState to set
     */
    public void setQualificationsState(String qualificationsState) {
        this.qualificationsState = qualificationsState;
    }

    /**
     * @return the creatorName
     */
    @Column(name = "creatorName", length = 32)
    public String getCreatorName() {
        return creatorName;
    }

    /**
     * @param creatorName the creatorName to set
     */
    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    /**
     * @return the forecastBorrowDate
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "forecastBorrowDate")
    public Date getForecastBorrowDate() {
        return forecastBorrowDate;
    }

    /**
     * @param forecastBorrowDate the forecastBorrowDate to set
     */
    public void setForecastBorrowDate(Date forecastBorrowDate) {
        this.forecastBorrowDate = forecastBorrowDate;
    }

    /**
     * @return the forecastReturnDate
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "forecastReturnDate")
    public Date getForecastReturnDate() {
        return forecastReturnDate;
    }

    /**
     * @param forecastReturnDate the forecastReturnDate to set
     */
    public void setForecastReturnDate(Date forecastReturnDate) {
        this.forecastReturnDate = forecastReturnDate;
    }

    /**
     * @return the agreeBorrowUser
     */
    @Column(name = "agreeBorrowUser", length = 32)
    public String getAgreeBorrowUser() {
        return agreeBorrowUser;
    }

    /**
     * @param agreeBorrowUser the agreeBorrowUser to set
     */
    public void setAgreeBorrowUser(String agreeBorrowUser) {
        this.agreeBorrowUser = agreeBorrowUser;
    }

    /**
     * @return the realBorrowDate
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "realBorrowDate")
    public Date getRealBorrowDate() {
        return realBorrowDate;
    }

    /**
     * @param realBorrowDate the realBorrowDate to set
     */
    public void setRealBorrowDate(Date realBorrowDate) {
        this.realBorrowDate = realBorrowDate;
    }

    /**
     * @return the realReturnDate
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "realReturnDate")
    public Date getRealReturnDate() {
        return realReturnDate;
    }

    /**
     * @param realReturnDate the realReturnDate to set
     */
    public void setRealReturnDate(Date realReturnDate) {
        this.realReturnDate = realReturnDate;
    }

    /**
     * @return the qualificationName
     */
    @Transient
    public String getQualificationName() {
        return qualificationName;
    }

    /**
     * @param qualificationName the qualificationName to set
     */
    public void setQualificationName(String qualificationName) {
        this.qualificationName = qualificationName;
    }

}
