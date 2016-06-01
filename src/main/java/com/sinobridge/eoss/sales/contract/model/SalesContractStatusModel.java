/*
 * FileName: SalesContract.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.sales.contract.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * <p>
 * Description: 合同的状态类
 * </p>
 *
 * @author 3unshine
 * @version 1.0

 * <p>
 * History: 
 *
 * Date                     Author         Version     Description
 * ---------------------------------------------------------------------------------
 * 2014年7月15日 上午10:42:25          3unshine        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Entity
@Table(name = "sales_contract_status")
public class SalesContractStatusModel implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    private long id;
    //合同ID
    private long saleContractId;
    //合同快照ID
    private Long contractSnapShootId;
    //订单采购状态
    private String orderStatus;
    //收款状态
    private String reciveStatus;
    //盖章状态
    private String cachetStatus;
    //发票状态
    private String invoiceStatus;
    //变更状态
    private String changeStatus;
    //创建时间
    private Date createTime;
    //备注
    private String remark;

    public SalesContractStatusModel() {
    }

    /**
     * @param id
     * @param salecontractId
     * @param orderStatus
     * @param reciveStatus
     * @param cachetStatus
     * @param invoiceStatus
     * @param remark
     */
    public SalesContractStatusModel(long id, long saleContractId, Long contractSnapShootId, String orderStatus, String reciveStatus, String cachetStatus, String invoiceStatus, String changeStatus, Date createTime, String remark) {
        super();
        this.id = id;
        this.saleContractId = saleContractId;
        this.contractSnapShootId = contractSnapShootId;
        this.orderStatus = orderStatus;
        this.reciveStatus = reciveStatus;
        this.cachetStatus = cachetStatus;
        this.invoiceStatus = invoiceStatus;
        this.changeStatus = changeStatus;
        this.createTime = createTime;
        this.remark = remark;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "SalesContractStatusModel [id=" + id + ", saleContractId=" + saleContractId + ", contractSnapShootId=" + contractSnapShootId + ", orderStatus=" + orderStatus + ", reciveStatus=" + reciveStatus + ", cachetStatus=" + cachetStatus + ", invoiceStatus=" + invoiceStatus + ", changeStatus=" + changeStatus + ", createTime=" + createTime + ", remark=" + remark + "]";
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
     * @return the creator
     */
    @Column(name = "SaleContractId", length = 20)
    public long getSaleContractId() {
        return saleContractId;
    }

    /**
     * @param creator the creator to set
     */
    public void setSaleContractId(long saleContractId) {
        this.saleContractId = saleContractId;
    }

    /**
     * @return the contractSnapShootId
     */
    @Column(name = "ContractSnapShootId", length = 20)
    public Long getContractSnapShootId() {
        return contractSnapShootId;
    }

    /**
     * @param contractSnapShootId the contractSnapShootId to set
     */
    public void setContractSnapShootId(Long contractSnapShootId) {
        this.contractSnapShootId = contractSnapShootId;
    }

    /**
     * @return the orderStatus
     */
    @Column(name = "OrderStatus", length = 100)
    public String getOrderStatus() {
        return orderStatus;
    }

    /**
     * @param orderStatus the orderStatus to set
     */
    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    /**
     * @return the reciveStatus
     */
    @Column(name = "ReciveStatus", length = 100)
    public String getReciveStatus() {
        return reciveStatus;
    }

    /**
     * @param reciveStatus the reciveStatus to set
     */
    public void setReciveStatus(String reciveStatus) {
        this.reciveStatus = reciveStatus;
    }

    /**
     * @return the cachetStatus
     */
    @Column(name = "CachetStatus", length = 100)
    public String getCachetStatus() {
        return cachetStatus;
    }

    /**
     * @param cachetStatus the cachetStatus to set
     */
    public void setCachetStatus(String cachetStatus) {
        this.cachetStatus = cachetStatus;
    }

    /**
     * @return the invoiceStatus
     */
    @Column(name = "InvoiceStatus", length = 100)
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
     * @return the changeStatus
     */
    @Column(name = "ChangeStatus", length = 100)
    public String getChangeStatus() {
        return changeStatus;
    }

    /**
     * @param changeStatus the changeStatus to set
     */
    public void setChangeStatus(String changeStatus) {
        this.changeStatus = changeStatus;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "CreateTime")
    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return the remark
     */
    @Column(name = "Remark", length = 100)
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
