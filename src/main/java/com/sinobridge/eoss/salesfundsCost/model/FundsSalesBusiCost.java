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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.sinobridge.base.core.model.imp.DefaultBaseModel;

/**
 * <p>
 * Description: 商务为多个合同付款时，产生需要商务确认的各合同的商务成本
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
@Table(name = "funds_salesBusiCost")
public class FundsSalesBusiCost extends DefaultBaseModel {

    private static final long serialVersionUID = 1L;

    private Long payPlanId;
    private Long salesContractId;
    private BigDecimal contractBusiCost;
    private String doUser;
    private String doState;
    private Date doDate;

    /**
     * @return the payPlanId
     */
    @Column(name = "payPlanId")
    public Long getPayPlanId() {
        return payPlanId;
    }

    /**
     * @param payPlanId the payPlanId to set
     */
    public void setPayPlanId(Long payPlanId) {
        this.payPlanId = payPlanId;
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
    @Column(name = "contractBusiCost")
    public BigDecimal getContractBusiCost() {
        return contractBusiCost;
    }

    /**
     * @param contractBusiCost the contractBusiCost to set
     */
    public void setContractBusiCost(BigDecimal contractBusiCost) {
        this.contractBusiCost = contractBusiCost;
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

}
