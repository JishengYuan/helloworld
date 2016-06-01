/*
 * FileName: SalesContractFoundModel.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.sales.contractfounds.model;

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
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * <p>
 * Description: 
 * </p>
 *
 * @author liyx
 * @version 1.0

 * <p>
 * History: 
 *
 * Date                     Author         Version     Description
 * ---------------------------------------------------------------------------------
 * 2015年12月1日 下午4:05:39      liyx           1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Entity
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "salesContractChapterModel", "salesContractQualificationModel", "salesContractsCertificateModel", "salesContractSizeModel" })
@Table(name = "sales_contractfounds")
public class SalesContractFoundModel implements java.io.Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Long id;
    private String applyFundsName;
    private Long cusIndustryId;//行业客户ID
    private Long cusCustomerId;//客户ID
    private String creator;
    private String creatorName;
    private double expectProfit;
    private String applyDesc;
    private Date createTime;
    private String contractCode;//合同编号
    private String contractName;
    private String applyFundsState;
    private Date payTime;
    private BigDecimal totalFunds;
    private Long processInstanceId;
    private String partnerCompany;
    private BigDecimal returnPrice;//退回保证金
    private BigDecimal mergerProjectPrice;//划归合同费用

    private List<SalesContractChapterModel> salesContractChapterModel = new ArrayList<SalesContractChapterModel>(0);
    private List<SalesContractQualificationModel> salesContractQualificationModel = new ArrayList<SalesContractQualificationModel>(0);
    private List<SalesContractsCertificateModel> salesContractsCertificateModel = new ArrayList<SalesContractsCertificateModel>(0);
    private List<SalesContractSizeModel> salesContractSizeModel = new ArrayList<SalesContractSizeModel>(0);

    //为接收多条 往来款，章，资质，证书 数据做临时映射字段
    private String tableDataChapter;
    private String tableDataQualification;
    private String tableDataCertificate;
    private String tableDataSize;

    @Id
    @Column(name = "id", nullable = false, unique = true, length = 20)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "applyFundsName", length = 128)
    public String getApplyFundsName() {
        return applyFundsName;
    }

    public void setApplyFundsName(String applyFundsName) {
        this.applyFundsName = applyFundsName;
    }

    @Column(name = "cusIndustryId", length = 20)
    public Long getCusIndustryId() {
        return cusIndustryId;
    }

    public void setCusIndustryId(Long cusIndustryId) {
        this.cusIndustryId = cusIndustryId;
    }

    @Column(name = "cusCustomerId", length = 20)
    public Long getCusCustomerId() {
        return cusCustomerId;
    }

    public void setCusCustomerId(Long cusCustomerId) {
        this.cusCustomerId = cusCustomerId;
    }

    @Column(name = "creator", length = 32)
    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    @Column(name = "creatorName", length = 128)
    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    @Column(name = "expectProfit")
    public double getExpectProfit() {
        return expectProfit;
    }

    public void setExpectProfit(double expectProfit) {
        this.expectProfit = expectProfit;
    }

    @Column(name = "applyDesc", length = 128)
    public String getApplyDesc() {
        return applyDesc;
    }

    public void setApplyDesc(String applyDesc) {
        this.applyDesc = applyDesc;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "createTime")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "contractCode", length = 128)
    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }

    @Column(name = "contractName", length = 128)
    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    @Column(name = "applyFundsState", length = 32)
    public String getApplyFundsState() {
        return applyFundsState;
    }

    public void setApplyFundsState(String applyFundsState) {
        this.applyFundsState = applyFundsState;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "payTime")
    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    @Column(name = "totalFunds")
    public BigDecimal getTotalFunds() {
        return totalFunds;
    }

    public void setTotalFunds(BigDecimal totalFunds) {
        this.totalFunds = totalFunds;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "salesContractFoundModel")
    public List<SalesContractChapterModel> getSalesContractChapterModel() {
        return salesContractChapterModel;
    }

    public void setSalesContractChapterModel(List<SalesContractChapterModel> salesContractChapterModel) {
        this.salesContractChapterModel = salesContractChapterModel;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "salesContractFoundModel")
    public List<SalesContractQualificationModel> getSalesContractQualificationModel() {
        return salesContractQualificationModel;
    }

    public void setSalesContractQualificationModel(List<SalesContractQualificationModel> salesContractQualificationModel) {
        this.salesContractQualificationModel = salesContractQualificationModel;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "salesContractFoundModel")
    public List<SalesContractsCertificateModel> getSalesContractsCertificateModel() {
        return salesContractsCertificateModel;
    }

    public void setSalesContractsCertificateModel(List<SalesContractsCertificateModel> salesContractsCertificateModel) {
        this.salesContractsCertificateModel = salesContractsCertificateModel;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "salesContractFoundModel")
    public List<SalesContractSizeModel> getSalesContractSizeModel() {
        return salesContractSizeModel;
    }

    public void setSalesContractSizeModel(List<SalesContractSizeModel> salesContractSizeModel) {
        this.salesContractSizeModel = salesContractSizeModel;
    }

    @Column(name = "processInstanceId")
    public Long getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(Long processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    @Column(name = "PartnerCompany", length = 64)
    public String getPartnerCompany() {
        return partnerCompany;
    }

    public void setPartnerCompany(String partnerCompany) {
        this.partnerCompany = partnerCompany;
    }

    @Transient
    public String getTableDataChapter() {
        return tableDataChapter;
    }

    public void setTableDataChapter(String tableDataChapter) {
        this.tableDataChapter = tableDataChapter;
    }

    @Transient
    public String getTableDataQualification() {
        return tableDataQualification;
    }

    public void setTableDataQualification(String tableDataQualification) {
        this.tableDataQualification = tableDataQualification;
    }

    @Transient
    public String getTableDataCertificate() {
        return tableDataCertificate;
    }

    public void setTableDataCertificate(String tableDataCertificate) {
        this.tableDataCertificate = tableDataCertificate;
    }

    @Transient
    public String getTableDataSize() {
        return tableDataSize;
    }

    public void setTableDataSize(String tableDataSize) {
        this.tableDataSize = tableDataSize;
    }

    @Column(name = "returnPrice")
    public BigDecimal getReturnPrice() {
        return returnPrice;
    }

    public void setReturnPrice(BigDecimal returnPrice) {
        this.returnPrice = returnPrice;
    }

    /**
     * @return the mergerProjectPrice
     */
    @Column(name = "mergerProjectPrice")
    public BigDecimal getMergerProjectPrice() {
        return mergerProjectPrice;
    }

    /**
     * @param mergerProjectPrice the mergerProjectPrice to set
     */
    public void setMergerProjectPrice(BigDecimal mergerProjectPrice) {
        this.mergerProjectPrice = mergerProjectPrice;
    }

}
