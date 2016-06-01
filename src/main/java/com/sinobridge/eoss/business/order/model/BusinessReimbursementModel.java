package com.sinobridge.eoss.business.order.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.sinobridge.eoss.business.suppliermanage.model.SupplierInfoModel;
import com.sinobridge.eoss.salesfundsCost.model.FundsSalesBusiReimbursement;

/**
 * <p>
 * Description: 订单发票计划的实体类
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
@Table(name = "business_reimbursement")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "businessOrder", "supplierInfo", "businessReimbursementApply" })
public class BusinessReimbursementModel implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    public static final String ORDERID = "businessOrder";
    public static final String CREATOR = "creator";
    public static final String ID = "id";
    public static final String PLANTIME = "planTime";
    public static final String AMOUNT = "amount";
    public static final String CLOSETIME = "closeTime";

    private Long id;
    private BusinessOrderModel businessOrder;
    //发票时间
    private Date planTime;
    //发票类型
    private String invoiceType;
    //报销金额
    private BigDecimal amount;
    //发票计划状态:1/草稿,2审批中，10/通过审批
    // private String reimBursStatus;
    //用途
    private String remark;
    //创建时间
    private Date createTime;

    //创建人
    private String creator;
    private Long creatorId;
    //账期天数
    //  private int credit;
    //报销张数
    private int number;
    //科目类型
    private String coursesType;
    //供应商
    private SupplierInfoModel supplierInfo;
    //订单编码
    private String orderCode;
    //订单金额
    private String orderAmount;
    //供应商简称
    private String supplierShortName;

    private BusinessReimbursementApply businessReimbursementApply;

    private List<FundsSalesBusiReimbursement> fundsSalesBusiReimbursements = new ArrayList<FundsSalesBusiReimbursement>();

    private List<Map<String, String>> contractReimbursements = new ArrayList<Map<String, String>>();

    public BusinessReimbursementModel() {
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

    @Column(name = "Number", length = 8)
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Column(name = "CoursesType", length = 32)
    public String getCoursesType() {
        return coursesType;
    }

    public void setCoursesType(String coursesType) {
        this.coursesType = coursesType;
    }

    @Column(name = "Creator", length = 32)
    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    @Column(name = "CreatorId", length = 20)
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

    public void setBusinessOrder(BusinessOrderModel businessOrder) {
        this.businessOrder = businessOrder;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "PlanTime")
    public Date getPlanTime() {
        return planTime;
    }

    public void setPlanTime(Date planTime) {
        this.planTime = planTime;
    }

    @Column(name = "InvoiceType", length = 32)
    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }

    @Column(name = "Amount", precision = 11, scale = 2)
    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Column(name = "Remark", length = 2048)
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

    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "supplierInfo")
    public SupplierInfoModel getSupplierInfo() {
        return supplierInfo;
    }

    public void setSupplierInfo(SupplierInfoModel supplierInfo) {
        this.supplierInfo = supplierInfo;
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
     * @return the businessReimbursementApply
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rbmApplyId")
    public BusinessReimbursementApply getBusinessReimbursementApply() {
        return businessReimbursementApply;
    }

    /**
     * @param businessReimbursementApply the businessReimbursementApply to set
     */
    public void setBusinessReimbursementApply(BusinessReimbursementApply businessReimbursementApply) {
        this.businessReimbursementApply = businessReimbursementApply;
    }

    /**
     * @return the fundsSalesBusiReimbursements
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "businessReimbursementModel")
    public List<FundsSalesBusiReimbursement> getFundsSalesBusiReimbursements() {
        return fundsSalesBusiReimbursements;
    }

    /**
     * @param fundsSalesBusiReimbursements the fundsSalesBusiReimbursements to set
     */
    public void setFundsSalesBusiReimbursements(List<FundsSalesBusiReimbursement> fundsSalesBusiReimbursements) {
        this.fundsSalesBusiReimbursements = fundsSalesBusiReimbursements;
    }

    /**
     * @return the contractReimbursements
     */
    @Transient
    public List<Map<String, String>> getContractReimbursements() {
        return contractReimbursements;
    }

    /**
     * @param contractReimbursements the contractReimbursements to set
     */
    public void setContractReimbursements(List<Map<String, String>> contractReimbursements) {
        this.contractReimbursements = contractReimbursements;
    }

}
