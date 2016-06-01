/*
 * FileName: BusinessOrderPaymentPlan.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.business.order.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.sinobridge.eoss.business.suppliermanage.model.SupplierInfoModel;

/**
 * <p>
 * Description: 订单付款申请的实体类
 * </p>
 * 
 * 
 * <p>
 * History:
 * 
 * Date Author Version Description
 * ----------------------------------------------
 * ----------------------------------- 2014年12月15日 上午10:42:25 wangya 1.0 To
 * create
 * </p>
 * 
 * @param <createDate>
 * 
 * @since
 * @see
 */
@Entity
@Table(name = "business_pay_apply")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "supplierName", "businessPaymentPlanModel" })
public class BusinessPayOrderModel implements java.io.Serializable {

    public static final String CLOSETIME = "closeTime";
    public static final String AMOUNT = "payAmonut";
    public static final String CREATEDATE = "createTime";
    public static final String PAYAPPLYNAME = "payApplyName";
    public static final String PLANPAYDATE = "planPayDate";
    public static final String PAYAPPLYUSER = "payApplyUser";
    public static final String PLANSTATUS = "planStatus";
    private static final long serialVersionUID = 1L;

    private Long id;
    // 应付时间
    private Date planPayDate;
    // 关闭时间
    private Date closeTime;
    // 供应商
    private SupplierInfoModel supplierName;
    //付款金额
    private BigDecimal payAmonut;
    //美金，实际付款金额
    private BigDecimal realPayAmount;
    // 科目类型
    private String coursesType;
    // 付款状态:1,2,10
    private String planStatus;
    // 付款人Id
    private String payUser;
    // 实付时间
    private Date realPayDate;
    // 付款单位
    private String payCompany;
    // 付款单位银行
    private String payCompanyBank;
    // 工单ID
    private Long processInstanceId;
    // 申请人Id
    private String payApplyUser;
    // 备注
    private String remark;
    // 创建时间
    private Date createTime;
    // 付款申请名称
    private String payApplyName;
    //付款方式，现金，电汇，支票
    private String payMethod;
    //税率
    private String taxType;
    //结算币种，人民币，美元
    private String currency;
    // 付款计划实体
    private Set<BusinessPaymentPlanModel> businessPaymentPlanModel = new HashSet<BusinessPaymentPlanModel>(0);

    public BusinessPayOrderModel() {

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

    @Temporal(TemporalType.DATE)
    @Column(name = "PlanPayDate")
    public Date getPlanPayDate() {
        return planPayDate;
    }

    public void setPlanPayDate(Date planPayDate) {
        this.planPayDate = planPayDate;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "CloseTime")
    public Date getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Date closeTime) {
        this.closeTime = closeTime;
    }

    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "SupplierName")
    public SupplierInfoModel getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(SupplierInfoModel supplierName) {
        this.supplierName = supplierName;
    }

    @Column(name = "PayAmonut", precision = 11, scale = 2)
    public BigDecimal getPayAmonut() {
        return payAmonut;
    }

    public void setPayAmonut(BigDecimal payAmonut) {
        this.payAmonut = payAmonut;
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

    @Column(name = "CoursesType", length = 32)
    public String getCoursesType() {
        return coursesType;
    }

    public void setCoursesType(String coursesType) {
        this.coursesType = coursesType;
    }

    @Column(name = "PlanStatus", length = 8)
    public String getPlanStatus() {
        return planStatus;
    }

    public void setPlanStatus(String planStatus) {
        this.planStatus = planStatus;
    }

    @Column(name = "PayUser", length = 32)
    public String getPayUser() {
        return payUser;
    }

    public void setPayUser(String payUser) {
        this.payUser = payUser;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "RealPayDate")
    public Date getRealPayDate() {
        return realPayDate;
    }

    public void setRealPayDate(Date realPayDate) {
        this.realPayDate = realPayDate;
    }

    @Column(name = "PayCompany", length = 64)
    public String getPayCompany() {
        return payCompany;
    }

    public void setPayCompany(String payCompany) {
        this.payCompany = payCompany;
    }

    @Column(name = "PayCompanyBank", length = 64)
    public String getPayCompanyBank() {
        return payCompanyBank;
    }

    public void setPayCompanyBank(String payCompanyBank) {
        this.payCompanyBank = payCompanyBank;
    }

    @Column(name = "ProcessInstanceId")
    public Long getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(Long processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    @Column(name = "PayApplyUser", length = 32)
    public String getPayApplyUser() {
        return payApplyUser;
    }

    public void setPayApplyUser(String payApplyUser) {
        this.payApplyUser = payApplyUser;
    }

    @Column(name = "Remark", length = 64)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * @return the businessPaymentPlanModel
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "payOrder")
    @OrderBy("id asc")
    public Set<BusinessPaymentPlanModel> getBusinessPaymentPlanModel() {
        return businessPaymentPlanModel;
    }

    public void setBusinessPaymentPlanModel(Set<BusinessPaymentPlanModel> businessPaymentPlanModel) {
        this.businessPaymentPlanModel = businessPaymentPlanModel;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "CreateTime")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "PayApplyName", length = 64)
    public String getPayApplyName() {
        return payApplyName;
    }

    public void setPayApplyName(String payApplyName) {
        this.payApplyName = payApplyName;
    }

    @Column(name = "PayMethod", length = 8)
    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    /**
     * @return the taxType
     */
    @Column(name = "TaxType", length = 32)
    public String getTaxType() {
        return taxType;
    }

    /**
     * @param taxType the taxType to set
     */
    public void setTaxType(String taxType) {
        this.taxType = taxType;
    }

    /**
     * @return the currency
     */
    @Column(name = "currency", length = 32)
    public String getCurrency() {
        return currency;
    }

    /**
     * @param currency the currency to set
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

}
