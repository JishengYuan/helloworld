/*
 * FileName: BusinessOrderPaymentPlan.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */

package com.sinobridge.eoss.business.order.model;

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

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * <p>
 * Description: 订单付款计划的实体类
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
@Table(name = "business_payment_plan")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "businessOrder", "payOrder" })
public class BusinessPaymentPlanModel implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    public static final String ORDERID = "businessOrder";
    public static final String CREATOR = "creator";
    public static final String ID = "id";
    public static final String PLANTIME = "planTime";
    public static final String CLOSETIME = "closeTime";
    public static final String AMOUNT = "amount";

    private Long id;
    private BusinessOrderModel businessOrder;
    //private String oderPlanType;
    //付款时间
    private Date planTime;
    //付款金额
    private BigDecimal amount;
    //付款计划状态，用于商务是否已确认商务成本，1：已确认，0（null）：未确认
    private String planStatus;
    //美金，实际付款金额
    private BigDecimal realPayAmount;
    //用途
    private String remark;
    //创建时间
    private Date createTime;
    //创建人
    private String creator;
    //账期天数
    private int credit;
    private Long creatorId;
    //订单编码
    private String orderCode;
    //订单金额
    private String orderAmount;
    //发票类型
    private String invoiceType;
    //供应商简称
    private String supplierShortName;
    private BusinessPayOrderModel payOrder;

    public BusinessPaymentPlanModel() {
    }

    /**
     * @return the id
     */
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

    @Column(name = "Creator", length = 32)
    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    @Column(name = "CreatorId", length = 32)
    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    /**
     * @return the businessOrder
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OrderId")
    public BusinessOrderModel getBusinessOrder() {
        return businessOrder;
    }

    /**
     * @param businessOrder the businessOrder to set
     */
    public void setBusinessOrder(BusinessOrderModel businessOrder) {
        this.businessOrder = businessOrder;
    }

    /*	@Column(name = "OderPlanType",length=32)
    	public String getOderPlanType() {
    		return oderPlanType;
    	}
    	public void setOderPlanType(String oderPlanType) {
    		this.oderPlanType = oderPlanType;
    	}*/

    @Temporal(TemporalType.DATE)
    @Column(name = "PlanTime")
    public Date getPlanTime() {
        return planTime;
    }

    public void setPlanTime(Date planTime) {
        this.planTime = planTime;
    }

    @Column(name = "Amount", precision = 11, scale = 2)
    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * @return the realPayAmount
     */
    @Column(name = "RealPayAmount", precision = 11, scale = 2)
    public BigDecimal getRealPayAmount() {
        return realPayAmount;
    }

    /**
     * @param realPayAmount the realPayAmount to set
     */
    public void setRealPayAmount(BigDecimal realPayAmount) {
        this.realPayAmount = realPayAmount;
    }

    @Column(name = "PlanStatus", length = 8)
    public String getPlanStatus() {
        return planStatus;
    }

    public void setPlanStatus(String planStatus) {
        this.planStatus = planStatus;
    }

    @Column(name = "Remark", length = 2048)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name = "Credit", length = 8)
    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "CreateTime")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "OrderCode", length = 32)
    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    @Column(name = "SupplierShortName", length = 64)
    public String getSupplierShortName() {
        return supplierShortName;
    }

    public void setSupplierShortName(String supplierShortName) {
        this.supplierShortName = supplierShortName;
    }

    @Column(name = "OrderAmount", length = 32)
    public String getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(String orderAmount) {
        this.orderAmount = orderAmount;
    }

    /**
     * @return the businessOrder
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PayOrderId")
    public BusinessPayOrderModel getPayOrder() {
        return payOrder;
    }

    public void setPayOrder(BusinessPayOrderModel payOrder) {
        this.payOrder = payOrder;
    }

    @Column(name = "InvoiceType", length = 32)
    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }

}
