/*
 * FileName: FundsSalesBusiCost.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.salesfundsCost.model;

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

import com.sinobridge.base.core.model.imp.DefaultBaseModel;
import com.sinobridge.eoss.business.order.model.BusinessReimbursementModel;

/**
 * <p>
 * Description: 商务为多个合同报销发票时，需要商务确认各合同的报销金额
 * </p>
 *
 * @author tigq
 * @version 1.0

 * <p>
 * History: 
 *
 * Date                     Author         Version     Description
 * ---------------------------------------------------------------------------------
 * 2015年12月28日 下午5:37:40          tigq        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Entity
@Table(name = "funds_salesBusiReimbursement")
public class FundsSalesBusiReimbursement extends DefaultBaseModel {

    private static final long serialVersionUID = 1L;

    private BusinessReimbursementModel businessReimbursementModel;
    private Long salesContractId;
    private BigDecimal contractBusiReimbursement;
    private String doUser;
    private String doState;
    private Date doDate;
    private Date auditDate;

    /**
     * @return the businessReimbursementModel
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reimbursementId")
    public BusinessReimbursementModel getBusinessReimbursementModel() {
        return businessReimbursementModel;
    }

    /**
     * @param businessReimbursementModel the businessReimbursementModel to set
     */
    public void setBusinessReimbursementModel(BusinessReimbursementModel businessReimbursementModel) {
        this.businessReimbursementModel = businessReimbursementModel;
    }

    /**
     * @return the salesContractId
     */
    @Column(name = "salesContractId")
    public Long getSalesContractId() {
        return salesContractId;
    }

    /**
     * @param salesContractId the salesContractId to set
     */
    public void setSalesContractId(Long salesContractId) {
        this.salesContractId = salesContractId;
    }

    /**
     * @return the contractBusiCost
     */
    @Column(name = "contractBusiReimbursement")
    public BigDecimal getContractBusiReimbursement() {
        return contractBusiReimbursement;
    }

    /**
     * @param contractBusiCost the contractBusiCost to set
     */
    public void setContractBusiReimbursement(BigDecimal contractBusiReimbursement) {
        this.contractBusiReimbursement = contractBusiReimbursement;
    }

    /**
     * @return the doUser
     */
    @Column(name = "doUser", length = 32)
    public String getDoUser() {
        return doUser;
    }

    /**
     * @param doUser the doUser to set
     */
    public void setDoUser(String doUser) {
        this.doUser = doUser;
    }

    /**
     * @return the doState
     */
    @Column(name = "doState", length = 32)
    public String getDoState() {
        return doState;
    }

    /**
     * @param doState the doState to set
     */
    public void setDoState(String doState) {
        this.doState = doState;
    }

    /**
     * @return the doDate
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "doDate")
    public Date getDoDate() {
        return doDate;
    }

    /**
     * @param doDate the doDate to set
     */
    public void setDoDate(Date doDate) {
        this.doDate = doDate;
    }

    /**
     * @return the auditDate
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "auditDate")
    public Date getAuditDate() {
        return auditDate;
    }

    /**
     * @param auditDate the auditDate to set
     */
    public void setAuditDate(Date auditDate) {
        this.auditDate = auditDate;
    }

}
