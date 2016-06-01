/*
 * FileName: SalesContract.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.sales.invoice.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * <p>
 * Description: 合同的发票计划类
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
@Table(name = "sales_invoice_plan")
public class SalesInvoicePlanModel implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final String CONTRACTID = "salesContractId";
    public static final String INVOICESTATUS = "invoiceStatus";

    private Long id;
    private Long salesContractId;
    //合同Name冗余
    private String salesContractName;
    private Date invoiceTime;
    private BigDecimal invoiceAmount;
    private int invoiceType;
    private String invoiceStatus;
    private Date createTime;
    private long creator;
    private Date preliminaryInspection;
    private Date finalInspection;
    private long isContractFlag;
    private Long processInstanceId;
    
    private Long changeProInstanceId;
    
    private String remark;

    public SalesInvoicePlanModel() {
    }

    @Id
    @Column(name = "id", nullable = false, unique = true, length = 20)
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the salesContract
     */
    @Column(name = "SalesContractId")
    public Long getSalesContractId() {
        return salesContractId;
    }

    /**
     * @param salesContract the salesContractModel to set
     */
    public void setSalesContractId(Long salesContractId) {
        this.salesContractId = salesContractId;
    }

    /**
     * @return the salesContractName
     */
    @Column(name = "SalesContractName", length = 128)
    public String getSalesContractName() {
        return salesContractName;
    }

    /**
     * @param salesContractName the salesContractName to set
     */
    public void setSalesContractName(String salesContractName) {
        this.salesContractName = salesContractName;
    }

    /**
     * @return the invoiceTime
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "InvoiceTime")
    public Date getInvoiceTime() {
        return invoiceTime;
    }

    /**
     * @param invoiceTime the invoiceTime to set
     */
    public void setInvoiceTime(Date invoiceTime) {
        this.invoiceTime = invoiceTime;
    }

    /**
     * @return the invoiceAmount
     */
    @Column(name = "InvoiceAmount", scale = 2, precision = 11)
    public BigDecimal getInvoiceAmount() {
        return invoiceAmount;
    }

    /**
     * @param invoiceAmount the invoiceAmount to set
     */
    public void setInvoiceAmount(BigDecimal invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
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
     * @return the invoiceStatus
     */
    @Column(name = "InvoiceStatus", length = 10)
    public String getInvoiceStatus() {
        return invoiceStatus;
    }

    /**
     * @param invoiceStatus the invoiceStatus to set
     */
    public void setInvoiceStatus(String invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    /**
     * @return the createTime
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "CreateTime")
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime the createTime to set
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return the creator
     */
    @Column(name = "Creator", length = 20)
    public long getCreator() {
        return creator;
    }

    /**
     * @param creator the creator to set
     */
    public void setCreator(long creator) {
        this.creator = creator;
    }

    /**
     * @return the preliminaryInspection
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "PreliminaryInspection")
    public Date getPreliminaryInspection() {
        return preliminaryInspection;
    }

    /**
     * @param preliminaryInspection the preliminaryInspection to set
     */
    public void setPreliminaryInspection(Date preliminaryInspection) {
        this.preliminaryInspection = preliminaryInspection;
    }

    /**
     * @return the finalInspection
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "FinalInspection")
    public Date getFinalInspection() {
        return finalInspection;
    }

    /**
     * @param finalInspection the finalInspection to set
     */
    public void setFinalInspection(Date finalInspection) {
        this.finalInspection = finalInspection;
    }

    /**
     * @return the isContractFlag
     */
    @Column(name = "IsContractFlag", length = 8)
    public long getIsContractFlag() {
        return isContractFlag;
    }

    /**
     * @param isContractFlag the isContractFlag to set
     */
    public void setIsContractFlag(long isContractFlag) {
        this.isContractFlag = isContractFlag;
    }

    /**
     * @return the processInstanceId
     */
    @Column(name = "ProcessInstanceId", length = 20)
    public Long getProcessInstanceId() {
        return processInstanceId;
    }

    /**
     * @param processInstanceId the processInstanceId to set
     */
    public void setProcessInstanceId(Long processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    @Column(name = "ChangeProInstanceId", length = 20)
    public Long getChangeProInstanceId() {
        return changeProInstanceId;
    }

    public void setChangeProInstanceId(Long changeProInstanceId) {
        this.changeProInstanceId = changeProInstanceId;
    }

    /**
     * @return the remark
     */
    @Column(name = "Remark", length = 1024)
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
