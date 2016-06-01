/*
 * FileName: SalesContract.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.sales.cachet.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * <p>
 * Description: 合同的盖章类
 * </p>
 *
 * @author 3unshine
 * @version 1.0

 * <p>
 * History: 
 *
 * Date                     Author         Version     Description
 * ---------------------------------------------------------------------------------
 * 2014年7月8日 上午10:42:25          3unshine        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Entity
@Table(name = "sales_cachet")
public class SalesCachetModel implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    public static final String CONTRACTID = "salesContractId";

    private long id;
    //创建人ID
    private long creator;
    //创建时间
    private Date createTime;
    //对应合同ID
    private Long salesContractId;
    //合同Name冗余
    private String salesContractName;
    //合同盖章状态(FQ=废弃)
    private String cachetStatus;
    //工单ID
    private Long processInstanceId;
    //附件ID
    private String attachIds;

    private Date cachetDate;

    public SalesCachetModel() {
    }

    /**
     * @param id
     * @param modifier
     * @param creator
     * @param createTime
     * @param salesContractId
     * @param processInstanceId
     * @param attachIds
     */
    public SalesCachetModel(long id, long creator, Date createTime, Long salesContractId, String salesContractName, String cachetStatus, Long processInstanceId, String attachIds) {
        super();
        this.id = id;
        this.creator = creator;
        this.createTime = createTime;
        this.salesContractId = salesContractId;
        this.salesContractName = salesContractName;
        this.cachetStatus = cachetStatus;
        this.processInstanceId = processInstanceId;
        this.attachIds = attachIds;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "SalesCachetModel [id=" + id + ", creator=" + creator + ", createTime=" + createTime + ", salesContractId=" + salesContractId + ", salesContractName=" + salesContractName + ", cachetStatus=" + cachetStatus + ", processInstanceId=" + processInstanceId + ", attachIds=" + attachIds + "]";
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
     * @return the salesContractId
     */
    @Column(name = "SalesContractId", length = 20)
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
     * @return the cachetStatus
     */
    @Column(name = "CachetStatus", length = 10)
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

    /**
     * @return the attachIds
     */
    @Column(name = "AttachIds", length = 20)
    public String getAttachIds() {
        return attachIds;
    }

    /**
     * @param attachIds the attachIds to set
     */
    public void setAttachIds(String attachIds) {
        this.attachIds = attachIds;
    }

    /**
     * @return the cachetDate
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "CachetDate")
    public Date getCachetDate() {
        return cachetDate;
    }

    /**
     * @param cachetDate the cachetDate to set
     */
    public void setCachetDate(Date cachetDate) {
        this.cachetDate = cachetDate;
    }
}
