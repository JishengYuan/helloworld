package com.sinobridge.eoss.sales.contract.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.sinobridge.base.core.model.imp.DefaultBaseModel;

@Entity
@Table(name = "sales_precontract")
public class PresaleContractModel extends DefaultBaseModel{

    public static final String PRECONTRACTCODE = "preContractCode";
    public static final String CONTRACTNAME = "contractName";
    public static final String CUSTOMERNAME = "customerName";
    public static final String CUSTOMERTYPE = "customerType";
    public static final String VENDOR = "vendor";
    public static final String PROTECHNOLOGY = "proTechnology";
    public static final String PROJECTSITE = "projectSite";
    public static final String CONTRACTAMOUNT = "contractAmount";
    public static final String CONTRACTTIME = "contractTime";
    public static final String CONTRACTCODE = "contractCode";
    
    /**
     * long serialVersionUID :       
     * @since  2015年11月9日 guokemenng
     */
    private static final long serialVersionUID = 6320633077857693667L;

    private String preContractCode;
    private String contractName;
    private String customerName;
    private String customerType;
    private String vendor;
    private String proTechnology;
    private String projectSite;
    private BigDecimal contractAmount;
    private Date contractTime;
    private Date createTime;
    private String contractCode;
    
    
    @Column(name = "contractName", length = 128)
    public String getContractName() {
        return contractName;
    }
    public void setContractName(String contractName) {
        this.contractName = contractName;
    }
    public String getCustomerName() {
        return customerName;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    public String getCustomerType() {
        return customerType;
    }
    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }
    public String getVendor() {
        return vendor;
    }
    public void setVendor(String vendor) {
        this.vendor = vendor;
    }
    public String getProTechnology() {
        return proTechnology;
    }
    public void setProTechnology(String proTechnology) {
        this.proTechnology = proTechnology;
    }
    public String getProjectSite() {
        return projectSite;
    }
    public void setProjectSite(String projectSite) {
        this.projectSite = projectSite;
    }
    @Column(name = "contractAmount", precision = 11, scale = 2)
    public BigDecimal getContractAmount() {
        return contractAmount;
    }
    public void setContractAmount(BigDecimal contractAmount) {
        this.contractAmount = contractAmount;
    }
    public Date getContractTime() {
        return contractTime;
    }
    public void setContractTime(Date contractTime) {
        this.contractTime = contractTime;
    }
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public String getPreContractCode() {
        return preContractCode;
    }
    public void setPreContractCode(String preContractCode) {
        this.preContractCode = preContractCode;
    }
    public String getContractCode() {
        return contractCode;
    }
    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }
    
}
