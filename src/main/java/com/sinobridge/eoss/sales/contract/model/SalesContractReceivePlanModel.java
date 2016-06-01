/*
 * FileName: SalesContract.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.sales.contract.model;

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
 * 2014年5月26日 上午10:42:25          3unshine        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Entity
@Table(name = "sales_contract_receive_plan")
public class SalesContractReceivePlanModel extends DefaultBaseModel {
    private static final long serialVersionUID = 1L;

    public static final String CONTACTID = "salesContract";

    private SalesContractModel salesContract;
    private Date planedReceiveDate;
    private BigDecimal planedReceiveAmount;
    private String payCondition;
    private long creator;
    private Date createTime;
    private long modifier;
    private Date modifyTime;
    private int Status;
    private long currentAccountId;
    private String Remark;

    public SalesContractReceivePlanModel() {
    }

    /**
     * @return the salesContractModel
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SalesContractId")
    public SalesContractModel getSalesContract() {
        return salesContract;
    }

    /**
     * @param salesContractModel the salesContractModel to set
     */
    public void setSalesContract(SalesContractModel salesContract) {
        this.salesContract = salesContract;
    }

    /**
     * @return the planedReceiveDate
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "PlanedReceiveDate")
    public Date getPlanedReceiveDate() {
        return planedReceiveDate;
    }

    /**
     * @param planedReceiveDate the planedReceiveDate to set
     */
    public void setPlanedReceiveDate(Date planedReceiveDate) {
        this.planedReceiveDate = planedReceiveDate;
    }

    /**
     * @return the planedReceiveAmount
     */
    @Column(name = "PlanedReceiveAmount", length = 9)
    public BigDecimal getPlanedReceiveAmount() {
        return planedReceiveAmount;
    }

    /**
     * @param planedReceiveAmount the planedReceiveAmount to set
     */
    public void setPlanedReceiveAmount(BigDecimal planedReceiveAmount) {
        this.planedReceiveAmount = planedReceiveAmount;
    }

    /**
     * @return the payCondition
     */
    @Column(name = "PayCondition", length = 1024)
    public String getPayCondition() {
        return payCondition;
    }

    /**
     * @param payCondition the payCondition to set
     */
    public void setPayCondition(String payCondition) {
        this.payCondition = payCondition;
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
     * @return the modifier
     */
    @Column(name = "Modifier", length = 20)
    public long getModifier() {
        return modifier;
    }

    /**
     * @param modifier the modifier to set
     */
    public void setModifier(long modifier) {
        this.modifier = modifier;
    }

    /**
     * @return the modifyTime
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "ModifyTime")
    public Date getModifyTime() {
        return modifyTime;
    }

    /**
     * @param modifyTime the modifyTime to set
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * @return the status
     */
    @Column(name = "Status", length = 4)
    public int getStatus() {
        return Status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(int status) {
        Status = status;
    }

    /**
     * @return the currentAccountId
     */
    @Column(name = "CurrentAccountId", length = 8)
    public long getCurrentAccountId() {
        return currentAccountId;
    }

    /**
     * @param currentAccountId the currentAccountId to set
     */
    public void setCurrentAccountId(long currentAccountId) {
        this.currentAccountId = currentAccountId;
    }

    /**
     * @return the remark
     */
    @Column(name = "Remark", length = 1024)
    public String getRemark() {
        return Remark;
    }

    /**
     * @param remark the remark to set
     */
    public void setRemark(String remark) {
        Remark = remark;
    }

}
