package com.sinobridge.eoss.business.order.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * <p>
 * Description: 发票申请报销实体
 * </p>


 * <p>
 * History: 
 *
 * Date                          Author      Version     Description
 * ---------------------------------------------------------------------------------
 * 2014年5月5日 上午10:42:25          wangya       1.0         To create
 * </p>
 * @param <createDate>
 *
 * @since 
 * @see     
 */
@Entity
@Table(name = "business_reimbursement_apply")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "supplierInfo", "reimbursementModes" })
public class BusinessReimbursementApply implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    public static final String ORDERID = "businessOrder";
    public static final String CREATOR = "creator";
    public static final String ID = "id";
    public static final String PLANTIME = "planTime";
    public static final String AMOUNT = "amount";
    public static final String CLOSETIME = "closeTime";
    public static final String CREATETIME = "createTime";
    public static final String REIMNAME = "reimbursementName";
    public static final String REIMSTATUS = "reimBursStatus";
    private Long id;
    //商务发票报销名称
    private String reimbursementName;

    //报销状态:1/草稿,2审批中，10/通过审批
    private String reimBursStatus;
    //报销金额
    private BigDecimal amount;
    //报销人
    private String reimbursementUser;
    //创建时间
    private Date createTime;
    //用途
    private String remark;
    //关闭时间
    private Date closeTime;
    //工单ID
    private Long processInstanceId;

    private List<BusinessReimbursementModel> reimbursementModes = new ArrayList<BusinessReimbursementModel>();

    public BusinessReimbursementApply() {
    }

    /**
     * @return the id
     */
    @Id
    @Column(name = "id", nullable = false, unique = true, length = 20)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the reimbursementName
     */
    @Column(name = "reimbursementName", length = 64)
    public String getReimbursementName() {
        return reimbursementName;
    }

    /**
     * @param reimbursementName the reimbursementName to set
     */
    public void setReimbursementName(String reimbursementName) {
        this.reimbursementName = reimbursementName;
    }

    @Column(name = "reimbursementUser", length = 32)
    public String getReimbursementUser() {
        return reimbursementUser;
    }

    public void setReimbursementUser(String reimbursementUser) {
        this.reimbursementUser = reimbursementUser;
    }

    @Column(name = "Amount", precision = 11, scale = 2)
    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Column(name = "Remark", length = 64)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "CreateTime")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "ProcessInstanceId")
    public Long getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(Long processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    @Column(name = "ReimBursStatus", length = 8)
    public String getReimBursStatus() {
        return reimBursStatus;
    }

    public void setReimBursStatus(String reimBursStatus) {
        this.reimBursStatus = reimBursStatus;
    }

    /**
     * @return the reimbursementModes
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "businessReimbursementApply")
    public List<BusinessReimbursementModel> getReimbursementModes() {
        return reimbursementModes;
    }

    /**
     * @param reimbursementModes the reimbursementModes to set
     */
    public void setReimbursementModes(List<BusinessReimbursementModel> reimbursementModes) {
        this.reimbursementModes = reimbursementModes;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "CloseTime")
    public Date getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Date closeTime) {
        this.closeTime = closeTime;
    }

}
