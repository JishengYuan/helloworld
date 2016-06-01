/*
 * FileName: SalesBudgetFunds.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.salesfundsCost.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * <p>
 * Description: 合同预测
 * </p>
 *
 * @author tigq
 * @version 1.0

 * <p>
 * History: 
 *
 * Date                     Author         Version     Description
 * ---------------------------------------------------------------------------------
 * 2016年1月27日 上午10:26:31          tigq        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Entity
@Table(name = "funds_salesBudget")
public class SalesBudgetFunds implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    public static final String CREATEDATE = "createDate";
    public static final String BUDGETRECEIVE = "budgetReceive";
    public static final String BUDGETINVOICE = "budgetInvoice";

    private Long id;
    private String contractCode;//合同编号
    private String contractName;//合同名称
    private Long contractId;//合同ID
    private BigDecimal budgetReceive; //预计收款
    private BigDecimal budgetInvoice; //预计开的发票金额
    private Date budgetDate;//预测时间
    private String userName;//销售中文名
    private String remark;//注释
    private Date createDate;//创建时间，修改时间
    private String title;//标题
    private int invoiceType;//发票类型
    private Long processInstanceId;//发票流程ID
    private String flat;//标志位
    private Long salesInvoiceId;//发票ID
    private Long salesReceiveId;//回款ID

    /**
     * @return the contractCode
     */
    @Column(name = "contractCode", length = 32)
    public String getContractCode() {
        return contractCode;
    }

    /**
     * @param contractCode the contractCode to set
     */
    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }

    /**
     * @return the contractName
     */
    @Column(name = "contractName", length = 128)
    public String getContractName() {
        return contractName;
    }

    /**
     * @param contractName the contractName to set
     */
    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    /**
     * @return the budgetReceive
     */
    @Column(name = "budgetReceive")
    public BigDecimal getBudgetReceive() {
        return budgetReceive;
    }

    /**
     * @param budgetReceive the budgetReceive to set
     */
    public void setBudgetReceive(BigDecimal budgetReceive) {
        this.budgetReceive = budgetReceive;
    }

    /**
     * @return the budgetInvoice
     */
    @Column(name = "budgetInvoice")
    public BigDecimal getBudgetInvoice() {
        return budgetInvoice;
    }

    /**
     * @param budgetInvoice the budgetInvoice to set
     */
    public void setBudgetInvoice(BigDecimal budgetInvoice) {
        this.budgetInvoice = budgetInvoice;
    }

    /**
     * @return the budgetDate
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "budgetDate")
    public Date getBudgetDate() {
        return budgetDate;
    }

    /**
     * @param budgetDate the budgetDate to set
     */
    public void setBudgetDate(Date budgetDate) {
        this.budgetDate = budgetDate;
    }

    /**
     * @return the userName
     */
    @Column(name = "userName", length = 32)
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the remark
     */
    @Column(name = "remark", length = 128)
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark the remark to set
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * @return the createDate
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "createDate")
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate the createDate to set
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * @return the contractId
     */
    @Column(name = "contractId", length = 20)
    public Long getContractId() {
        return contractId;
    }

    /**
     * @param contractId the contractId to set
     */
    public void setContractId(Long contractId) {
        this.contractId = contractId;
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

    /**
     * @return the title
     */
    @Column(name = "title", length = 32)
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the invoiceType
     */
    @Column(name = "invoiceType", length = 8)
    public int getInvoiceType() {
        return invoiceType;
    }

    /**
     * @param invoiceType the invoiceType to set
     */
    public void setInvoiceType(int invoiceType) {
        this.invoiceType = invoiceType;
    }

    /**
     * @return the processInstanceId
     */
    @Column(name = "processInstanceId", length = 20)
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
     * @return the flat
     */
    @Column(name = "flat", length = 4)
    public String getFlat() {
        return flat;
    }

    /**
     * @param flat the flat to set
     */
    public void setFlat(String flat) {
        this.flat = flat;
    }

    /**
     * @return the salesInvoiceId
     */
    @Column(name = "salesInvoiceId", length = 20)
    public Long getSalesInvoiceId() {
        return salesInvoiceId;
    }

    /**
     * @param salesInvoiceId the salesInvoiceId to set
     */
    public void setSalesInvoiceId(Long salesInvoiceId) {
        this.salesInvoiceId = salesInvoiceId;
    }

    /**
     * @return the salesReceiveId
     */
    @Column(name = "salesReceiveId", length = 20)
    public Long getSalesReceiveId() {
        return salesReceiveId;
    }

    /**
     * @param salesReceiveId the salesReceiveId to set
     */
    public void setSalesReceiveId(Long salesReceiveId) {
        this.salesReceiveId = salesReceiveId;
    }

}
