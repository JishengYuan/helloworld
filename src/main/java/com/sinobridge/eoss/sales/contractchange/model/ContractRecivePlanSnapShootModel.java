/*
 * FileName: ContractRecivePlanSnapShootModel.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.sales.contractchange.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * <p>
 * Description: 快照表-合同收款计划
 * </p>
 *
 * @author 3unshine
 * @version 1.0

 * <p>
 * History: 
 *
 * Date                     Author         Version     Description
 * ---------------------------------------------------------------------------------
 * 2014年7月23日 上午10:20:40          3unshine        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Entity
@Table(name = "sales_contract_reciveplan_snapshoot")
public class ContractRecivePlanSnapShootModel implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    private long id;
    private long recivePlanId;
    private ContractSnapShootModel contractSnapShootModel;
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

    /**
     * 
     */
    public ContractRecivePlanSnapShootModel() {
    }

    /**
     * @param id
     * @param recivePlanId
     * @param contractSnapShootModel
     * @param planedReceiveDate
     * @param planedReceiveAmount
     * @param payCondition
     * @param creator
     * @param createTime
     * @param modifier
     * @param modifyTime
     * @param status
     * @param currentAccountId
     * @param remark
     */
    public ContractRecivePlanSnapShootModel(long id, long recivePlanId, ContractSnapShootModel contractSnapShootModel, Date planedReceiveDate, BigDecimal planedReceiveAmount, String payCondition, long creator, Date createTime, long modifier, Date modifyTime, int status, long currentAccountId, String remark) {
        super();
        this.id = id;
        this.recivePlanId = recivePlanId;
        this.contractSnapShootModel = contractSnapShootModel;
        this.planedReceiveDate = planedReceiveDate;
        this.planedReceiveAmount = planedReceiveAmount;
        this.payCondition = payCondition;
        this.creator = creator;
        this.createTime = createTime;
        this.modifier = modifier;
        this.modifyTime = modifyTime;
        Status = status;
        this.currentAccountId = currentAccountId;
        Remark = remark;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "ContractRecivePlanSnapShootModel [id=" + id + ", recivePlanId=" + recivePlanId + ", contractSnapShootModel=" + contractSnapShootModel + ", planedReceiveDate=" + planedReceiveDate + ", planedReceiveAmount=" + planedReceiveAmount + ", payCondition=" + payCondition + ", creator=" + creator + ", createTime=" + createTime + ", modifier=" + modifier + ", modifyTime=" + modifyTime + ", Status=" + Status + ", currentAccountId=" + currentAccountId + ", Remark=" + Remark + "]";
    }

    /**
     * @return the id
     */
    @Id
    @Column(name = "id", nullable = false, unique = true, length = 20)
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the recivePlanId
     */
    @Column(name = "RecivePlanId", length = 20)
    public long getRecivePlanId() {
        return recivePlanId;
    }

    /**
     * @param recivePlanId the recivePlanId to set
     */
    public void setRecivePlanId(long recivePlanId) {
        this.recivePlanId = recivePlanId;
    }

    /**
     * @return the contractSnapShootModel
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ContractSnapShootId")
    public ContractSnapShootModel getContractSnapShootModel() {
        return contractSnapShootModel;
    }

    /**
     * @param contractSnapShootModel the contractSnapShootModel to set
     */
    public void setContractSnapShootModel(ContractSnapShootModel contractSnapShootModel) {
        this.contractSnapShootModel = contractSnapShootModel;
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
    @Column(name = "PlanedReceiveAmount")
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
