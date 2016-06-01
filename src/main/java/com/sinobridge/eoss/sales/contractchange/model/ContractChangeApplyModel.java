/*
 * FileName: ContractChangeApplyModel.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.sales.contractchange.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * <p>
 * Description: 合同变更申请实体
 * </p>
 *
 * @author 3unshine
 * @version 1.0

 * <p>
 * History: 
 *
 * Date                     Author         Version     Description
 * ---------------------------------------------------------------------------------
 * 2014年7月22日 下午4:42:47          3unshine        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Entity
@Table(name = "sales_contract_change_apply")
public class ContractChangeApplyModel implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    //合同变更申请ID
    private long id;
    //合同ID
    private Long saleContractId;
    //合同Name冗余
    private String salesContractName;
    //变更的内容(1:合同金额 2：备配备件 3:4：其他)
    private int changeType;
    //合同变更申请创建时间
    private Date createTime;
    //合同变更申请创建人ID
    private long creator;
    //工单ID
    private Long processInstanceId;
    //备注
    private String remark;

    /**
     * 
     */
    public ContractChangeApplyModel() {
    }

    /**
     * @param id
     * @param saleContractId
     * @param changeType
     * @param createTime
     * @param creator
     * @param processInstanceId
     * @param remark
     */
    public ContractChangeApplyModel(long id, Long saleContractId, int changeType, Date createTime, String salesContractName, long creator, Long processInstanceId, String remark) {
        super();
        this.id = id;
        this.saleContractId = saleContractId;
        this.changeType = changeType;
        this.createTime = createTime;
        this.salesContractName = salesContractName;
        this.creator = creator;
        this.processInstanceId = processInstanceId;
        this.remark = remark;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "ContractChangeApplyModel [id=" + id + ", saleContractId=" + saleContractId + ", salesContractName=" + salesContractName + ", changeType=" + changeType + ", createTime=" + createTime + ", creator=" + creator + ", processInstanceId=" + processInstanceId + ", remark=" + remark + "]";
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
     * @return the saleContractId
     */
    @Column(name = "SaleContractId", length = 32)
    public Long getSaleContractId() {
        return saleContractId;
    }

    /**
     * @param saleContractId the saleContractId to set
     */
    public void setSaleContractId(Long saleContractId) {
        this.saleContractId = saleContractId;
    }

    /**
     * @return the changeType
     */
    @Column(name = "ChangeType", length = 4)
    public int getChangeType() {
        return changeType;
    }

    /**
     * @param changeType the changeType to set
     */
    public void setChangeType(int changeType) {
        this.changeType = changeType;
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
     * @return the processInstanceId
     */
    @Column(name = "ProcessInstanceId", length = 30)
    public Long getProcessInstanceId() {
        return processInstanceId;
    }

    /**
     * @param processInstanceId the processInstanceId to set
     */
    public void setProcessInstanceId(Long processInstanceId) {
        this.processInstanceId = processInstanceId;
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
