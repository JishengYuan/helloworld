/*
 * FileName: BusinessOrder.java
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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.Cascade;

import com.sinobridge.eoss.business.interPurchas.model.InterPurchasModel;
import com.sinobridge.eoss.business.suppliermanage.model.SupplierContactsModel;
import com.sinobridge.eoss.business.suppliermanage.model.SupplierInfoModel;
import com.sinobridge.eoss.sales.contract.model.SalesContractModel;

/**
 * <p>
 * Description: 订单的实体类
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
@Table(name = "business_order")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "supplierContactsModel", "supplierInfoModel", "businessPaymentPlanModel", "salesContractModel", "businessOrderProductModel", "interPurchasModel", "businessReimbursementModel" })
public class BusinessOrderModel implements java.io.Serializable {

    public static final String ORDERCODE = "orderCode";
    public static final String ORDERTYPE = "orderType";
    public static final String ORDERAMOUNT = "orderAmount";
    public static final String CREATOR = "creator";
    public static final String CREATORID = "creatorId";
    public static final String ORDERNAME = "orderName";
    public static final String CREATEDATE = "createDate";
    public static final String PURCHASETYPE = "purchaseType";
    public static final String SUPPLIERNAME = "supplierShortName";
    public static final String ORDERSTATUS = "orderStatus";
    public static final String PAYSTATUS = "payStatus";
    public static final String REIMSTATUS = "reimStatus";
    public static final String ID = "id";
    public static final String WAREHOUSESTATUS = "wareHouseStatus";
    public static final String DELIVERYADDRESS = "deliveryAddress";

    private static final long serialVersionUID = 1L;
    private Long id;
    private String orderName;
    private String orderCode;
    //订单类型
    private String orderType;

    //采购分类
    private String purchaseType;
    //创建人
    private String creator;
    //useName
    private String creatorId;
    //创建时间
    private Date createDate;
    //生效时间
    private Date beginTime;

    //订单状态:CG草稿,SH审批中,TGSP通过审批
    private String orderStatus;
    //到货状态：“N”没有到货，“S”部分，“A”全部
    private String wareHouseStatus;
    //到货状态：“N”没有到货，“S”部分，“A”全部
    private String arrivalStatus;
    //付款状态：“N”没有付款，“S”部分付款，“A”全部付款
    private String payStatus;
    //报销状态：“N”没有报销，“S”部分，“A”全部
    private String reimStatus;
    //订单金额
    private BigDecimal orderAmount;
    private String orderAmountStr;
    //已付金额
    private BigDecimal payAmount;
    private String payAmountStr;

    //已报销金额
    private BigDecimal reimAmount;
    private String reimAmountStr;
    //公司信息

    private String companyName;
    private String companyAddress;
    private String bankName;
    //银行账号
    private String bankAccount;

    //工单ID
    private Long processInstanceId;

    //服务期
    private String serviceDate;

    //发票类型
    private String invoiceType;

    //期望到货时间
    private Date expectedDeliveryTime;
    //期望送货地点
    private String deliveryAddress;
    //发货时间
    private Date arrivalTime;

    //供应商实体
    private SupplierInfoModel supplierInfoModel;
    //供应商简称
    private String supplierShortName;
    //供应商联系人实体
    private SupplierContactsModel supplierContactsModel;

    //付款方式
    private String paymentMode;

    //附件ID
    private String attachIds;

    //使用的返点数
    private float spotNum;
    //
    private String spotSupplier;
    //合同致订单变更的描述
    private String changeRemark;
    //变更申请：申请：CSP,可变更：CTGSP, 不可变更：0
    private String isChange;

    //合同实体
    private Set<SalesContractModel> salesContractModel = new HashSet<SalesContractModel>(0);

    //内采实体
    private Set<InterPurchasModel> interPurchasModel = new HashSet<InterPurchasModel>(0);

    //付款计划实体
    private Set<BusinessPaymentPlanModel> businessPaymentPlanModel = new HashSet<BusinessPaymentPlanModel>(0);

    //订单产品实体
    private Set<BusinessOrderProductModel> businessOrderProductModel = new HashSet<BusinessOrderProductModel>(0);
    //发票计划实体
    private Set<BusinessReimbursementModel> businessReimbursementModel = new HashSet<BusinessReimbursementModel>(0);

    //暂存合同Id
    private String contractId;

    //暂存内部采购Id 
    private String interPurchasId;

    //-------------------------------------------------
    //为接收多条SalesContractReceivePlanModel数据做临时映射字段
    private String tableData;

    private String statuse;

    //订单利润 分类
    //    private String orderProfits;
    //厂商折扣
    private String partnerPV;
    //客户利润
    private String customerPV;
    //利润
    private String profitsValue;
    //盖章时间
    private Date cachetTime;
    //盖章意见
    private String cachetAdvice;
    //结算币种
    private String accountCurrency;

    //-------------------------------------------------

    public BusinessOrderModel() {

    }

    @Column(name = "ArrivalStatus", length = 32)
    public String getArrivalStatus() {
        return arrivalStatus;
    }

    /**
     * @return the attachIds
     */
    @Column(name = "AttachIds", length = 100)
    public String getAttachIds() {
        return attachIds;
    }

    @Column(name = "BankAccount", length = 32)
    public String getBankAccount() {
        return bankAccount;
    }

    @Column(name = "BankName", length = 32)
    public String getBankName() {
        return bankName;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "businessOrder")
    public Set<BusinessOrderProductModel> getBusinessOrderProductModel() {
        return businessOrderProductModel;
    }

    /**
     * @return the businessPaymentPlanModel
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "businessOrder")
    public Set<BusinessPaymentPlanModel> getBusinessPaymentPlanModel() {
        return businessPaymentPlanModel;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "businessOrder")
    public Set<BusinessReimbursementModel> getBusinessReimbursementModel() {
        return businessReimbursementModel;
    }

    @Column(name = "CompanyAddress", length = 64)
    public String getCompanyAddress() {
        return companyAddress;
    }

    @Column(name = "CompanyName", length = 32)
    public String getCompanyName() {
        return companyName;
    }

    @Transient
    public String getContractId() {
        return contractId;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "CreateDate")
    public Date getCreateDate() {
        return createDate;
    }

    @Column(name = "Creator", length = 32)
    public String getCreator() {
        return creator;
    }

    @Column(name = "CreatorId", length = 32)
    public String getCreatorId() {
        return creatorId;
    }

    @Column(name = "DeliveryAddress", length = 16)
    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "ExpectedDeliveryTime")
    public Date getExpectedDeliveryTime() {
        return expectedDeliveryTime;
    }

    /**
     * @return the id
     */
    @Id
    @Column(name = "id", nullable = false, unique = true, length = 20)
    public Long getId() {
        return id;
    }

    @Transient
    public String getInterPurchasId() {
        return interPurchasId;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @Cascade(value = org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinTable(name = "order_interPurchas_map", joinColumns = @JoinColumn(name = "business_order", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "business_inter_purchas", referencedColumnName = "id"))
    public Set<InterPurchasModel> getInterPurchasModel() {
        return interPurchasModel;
    }

    @Column(name = "InvoiceType", length = 8)
    public String getInvoiceType() {
        return invoiceType;
    }

    @Column(name = "OrderAmount", precision = 11, scale = 2)
    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    @Column(name = "OrderCode", length = 32)
    public String getOrderCode() {
        return orderCode;
    }

    @Column(name = "OrderName", length = 64)
    public String getOrderName() {
        return orderName;
    }

    @Column(name = "OrderStatus", length = 32)
    public String getOrderStatus() {
        return orderStatus;
    }

    @Column(name = "OrderType", length = 32)
    public String getOrderType() {
        return orderType;
    }

    @Column(name = "PayAmount", precision = 11, scale = 2)
    public BigDecimal getPayAmount() {
        return payAmount;
    }

    @Column(name = "PaymentMode", length = 8)
    public String getPaymentMode() {
        return paymentMode;
    }

    @Column(name = "PayStatus", length = 32)
    public String getPayStatus() {
        return payStatus;
    }

    @Column(name = "ProcessInstanceId")
    public Long getProcessInstanceId() {
        return processInstanceId;
    }

    @Column(name = "PurchaseType", length = 32)
    public String getPurchaseType() {
        return purchaseType;
    }

    @Column(name = "ReimAmount", precision = 11, scale = 2)
    public BigDecimal getReimAmount() {
        return reimAmount;
    }

    @Column(name = "ReimStatus", length = 32)
    public String getReimStatus() {
        return reimStatus;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @Cascade(value = org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinTable(name = "order_contract_map", joinColumns = @JoinColumn(name = "business_order", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "sales_contract", referencedColumnName = "id"))
    @OrderBy("id asc")
    public Set<SalesContractModel> getSalesContractModel() {
        return salesContractModel;
    }

    @Column(name = "ServiceDate", length = 32)
    public String getServiceDate() {
        return serviceDate;
    }

    @Column(name = "SpotNum")
    public float getSpotNum() {
        return spotNum;
    }

    /**
     * 记录订单合同是否关闭
     * @return the statuse
     */
    @Transient
    public String getStatuse() {
        return statuse;
    }

    //-------------------------------------------------

    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "SupplierContacts")
    public SupplierContactsModel getSupplierContactsModel() {
        return supplierContactsModel;
    }

    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "supplierInfo")
    public SupplierInfoModel getSupplierInfoModel() {
        return supplierInfoModel;
    }

    @Column(name = "SupplierShortName", length = 32)
    public String getSupplierShortName() {
        return supplierShortName;
    }

    //-------------------------------------------------
    //为接收多条SalesContractReceivePlanModel数据做临时映射字段
    @Transient
    public String getTableData() {
        return tableData;
    }

    /**
     * @return the wareHouseStatus
     */
    @Column(name = "WareHouseStatus", length = 32)
    public String getWareHouseStatus() {
        return wareHouseStatus;
    }

    @Column(name = "ChangeRemark", length = 64)
    public String getChangeRemark() {
        return changeRemark;
    }

    public void setChangeRemark(String changeRemark) {
        this.changeRemark = changeRemark;
    }

    @Column(name = "IsChange", length = 32)
    public String getIsChange() {
        return isChange;
    }

    public void setIsChange(String isChange) {
        this.isChange = isChange;
    }

    public void setArrivalStatus(String arrivalStatus) {
        this.arrivalStatus = arrivalStatus;
    }

    /**
     * @param attachIds the attachIds to set
     */
    public void setAttachIds(String attachIds) {
        this.attachIds = attachIds;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public void setBusinessOrderProductModel(Set<BusinessOrderProductModel> businessOrderProductModel) {
        this.businessOrderProductModel = businessOrderProductModel;
    }

    public void setBusinessPaymentPlanModel(Set<BusinessPaymentPlanModel> businessPaymentPlanModel) {
        this.businessPaymentPlanModel = businessPaymentPlanModel;
    }

    public void setBusinessReimbursementModel(Set<BusinessReimbursementModel> businessReimbursementModel) {
        this.businessReimbursementModel = businessReimbursementModel;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public void setExpectedDeliveryTime(Date expectedDeliveryTime) {
        this.expectedDeliveryTime = expectedDeliveryTime;
    }

    public void setSpotNum(float spotNum) {
        this.spotNum = spotNum;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    public void setInterPurchasId(String interPurchasId) {
        this.interPurchasId = interPurchasId;
    }

    public void setInterPurchasModel(Set<InterPurchasModel> interPurchasModel) {
        this.interPurchasModel = interPurchasModel;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public void setProcessInstanceId(Long processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public void setPurchaseType(String purchaseType) {
        this.purchaseType = purchaseType;
    }

    public void setReimAmount(BigDecimal reimAmount) {
        this.reimAmount = reimAmount;
    }

    public void setReimStatus(String reimStatus) {
        this.reimStatus = reimStatus;
    }

    public void setSalesContractModel(Set<SalesContractModel> salesContractModel) {
        this.salesContractModel = salesContractModel;
    }

    public void setServiceDate(String serviceDate) {
        this.serviceDate = serviceDate;
    }

    /**
     * @param statuse the statuse to set
     */
    public void setStatuse(String statuse) {
        this.statuse = statuse;
    }

    public void setSupplierContactsModel(SupplierContactsModel supplierContactsModel) {
        this.supplierContactsModel = supplierContactsModel;
    }

    public void setSupplierInfoModel(SupplierInfoModel supplierInfoModel) {
        this.supplierInfoModel = supplierInfoModel;
    }

    public void setSupplierShortName(String supplierShortName) {
        this.supplierShortName = supplierShortName;
    }

    /**
     * @param tableData the tableData to set
     */
    public void setTableData(String tableData) {
        this.tableData = tableData;
    }

    /**
     * @param wareHouseStatus the wareHouseStatus to set
     */
    public void setWareHouseStatus(String wareHouseStatus) {
        this.wareHouseStatus = wareHouseStatus;
    }

    @Transient
    public String getPayAmountStr() {
        if (getPayAmount() != null) {
            return getPayAmount().toString();
        } else {
            return payAmountStr;
        }
    }

    public void setPayAmountStr(String payAmountStr) {
        this.payAmountStr = payAmountStr;
    }

    @Transient
    public String getOrderAmountStr() {
        if (getOrderAmount() != null) {
            return getOrderAmount().toString();
        } else {
            return orderAmountStr;
        }
    }

    public void setOrderAmountStr(String orderAmountStr) {
        this.orderAmountStr = orderAmountStr;
    }

    @Transient
    public String getReimAmountStr() {
        if (getReimAmount() != null) {
            return getReimAmount().toString();
        } else {
            return reimAmountStr;
        }
    }

    public void setReimAmountStr(String reimAmountStr) {
        this.reimAmountStr = reimAmountStr;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "ArrivalTime")
    public Date getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "BeginTime")
    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    @Column(name = "SpotSupplier", length = 64)
    public String getSpotSupplier() {
        return spotSupplier;
    }

    public void setSpotSupplier(String spotSupplier) {
        this.spotSupplier = spotSupplier;
    }

    @Column(name = "ProfitsValue")
    public String getProfitsValue() {
        return profitsValue;
    }

    public void setProfitsValue(String profitsValue) {
        this.profitsValue = profitsValue;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "CachetTime")
    public Date getCachetTime() {
        return cachetTime;
    }

    public void setCachetTime(Date cachetTime) {
        this.cachetTime = cachetTime;
    }

    @Column(name = "CachetAdvice", length = 128)
    public String getCachetAdvice() {
        return cachetAdvice;
    }

    public void setCachetAdvice(String cachetAdvice) {
        this.cachetAdvice = cachetAdvice;
    }

    @Column(name = "PartnerPV")
    public String getPartnerPV() {
        return partnerPV;
    }

    public void setPartnerPV(String partnerPV) {
        this.partnerPV = partnerPV;
    }

    @Column(name = "CustomerPV")
    public String getCustomerPV() {
        return customerPV;
    }

    public void setCustomerPV(String customerPV) {
        this.customerPV = customerPV;
    }

    @Column(name = "AccountCurrency", length = 32)
    public String getAccountCurrency() {
        return accountCurrency;
    }

    public void setAccountCurrency(String accountCurrency) {
        this.accountCurrency = accountCurrency;
    }

}
