/*
 * FileName: BasePartnerInfo.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.business.projectmanage.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.sinobridge.eoss.business.projectmanage.BaseConstants;

/**
 * <code>BasePartnerInfo</code>
 * 
 * @version  1.0
 * @author  guokemenng
 * @since 1.0  2013-11-25
 */
@Entity
@Table(name = BaseConstants.BASE_PARTNERINFO )
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "basePartnerProducts" })
public class BasePartnerInfo implements java.io.Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    // Fields    

    /** 
     * String PARTNERTYPE :  
     * 厂商类型     
     * @since  2013-11-14 guokemenng
     */
    public static final String PARTNERTYPE = "partnerType";
    public static final String DELETEFLAG = "deleteFlag";
    /** 
     * String SERVICEPARTNERTYPE :
     * 服务供应商类型       
     * @since  2013-11-14 guokemenng
     */
    public static final String SERVICEPARTNERTYPE = "servicePartnerType";
    public static final String PARTNERFULLNAME = "partnerFullName";
    public static final String PARTNERCODE = "partnerCode";

    private String id;
    private String partnerType;
    private Short servicePartnerType;
    private String partnerCode;
    private String partnerEnCode;
    private String partnerName;
    private String partnerFullName;
    private String partnerOid;
    private String enterpriseCode;
    private String enterprisePerson;
    private String registerMoney;
    private String registerAddress;
    private Short enterpriseSize;
    private Short enterpriseType;
    private String qualifications;
    private String webUrl;
    private String hotLine;
    private String address;
    private String partnerLogo;
    /** 
     * String partnerEmail :    
     * 公司 邮箱   
     * @since  2013-12-16 guokemenng
     */
    private String partnerEmail;
    private Short deleteFlag;
    
    private String currency;
    
    private Set<BasePartnerProduct> basePartnerProducts = new HashSet<BasePartnerProduct>(0);


    // Property accessors
    @Id
    @Column(name = "Id", unique = true, nullable = false, length = 36)
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "PartnerType")
    public String getPartnerType() {
        return this.partnerType;
    }

    public void setPartnerType(String partnerType) {
        this.partnerType = partnerType;
    }

    @Column(name = "ServicePartnerType")
    public Short getServicePartnerType() {
        return this.servicePartnerType;
    }

    public void setServicePartnerType(Short servicePartnerType) {
        this.servicePartnerType = servicePartnerType;
    }

    @Column(name = "PartnerCode", length = 36)
    public String getPartnerCode() {
        return this.partnerCode;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }

    @Column(name = "PartnerName", length = 36)
    public String getPartnerName() {
        return this.partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    @Column(name = "PartnerFullName", length = 64)
    public String getPartnerFullName() {
        return this.partnerFullName;
    }

    public void setPartnerFullName(String partnerFullName) {
        this.partnerFullName = partnerFullName;
    }

    @Column(name = "PartnerOid", length = 64)
    public String getPartnerOid() {
        return this.partnerOid;
    }

    public void setPartnerOid(String partnerOid) {
        this.partnerOid = partnerOid;
    }

    @Column(name = "EnterpriseCode", length = 36)
    public String getEnterpriseCode() {
        return this.enterpriseCode;
    }

    public void setEnterpriseCode(String enterpriseCode) {
        this.enterpriseCode = enterpriseCode;
    }

    @Column(name = "EnterprisePerson", length = 36)
    public String getEnterprisePerson() {
        return this.enterprisePerson;
    }

    public void setEnterprisePerson(String enterprisePerson) {
        this.enterprisePerson = enterprisePerson;
    }

    @Column(name = "RegisterMoney", length = 20)
    public String getRegisterMoney() {
        return this.registerMoney;
    }

    public void setRegisterMoney(String registerMoney) {
        this.registerMoney = registerMoney;
    }

    @Column(name = "RegisterAddress", length = 64)
    public String getRegisterAddress() {
        return this.registerAddress;
    }

    public void setRegisterAddress(String registerAddress) {
        this.registerAddress = registerAddress;
    }

    @Column(name = "EnterpriseSize")
    public Short getEnterpriseSize() {
        return this.enterpriseSize;
    }

    public void setEnterpriseSize(Short enterpriseSize) {
        this.enterpriseSize = enterpriseSize;
    }

    @Column(name = "EnterpriseType")
    public Short getEnterpriseType() {
        return this.enterpriseType;
    }

    public void setEnterpriseType(Short enterpriseType) {
        this.enterpriseType = enterpriseType;
    }

    @Column(name = "Qualifications", length = 256)
    public String getQualifications() {
        return this.qualifications;
    }

    public void setQualifications(String qualifications) {
        this.qualifications = qualifications;
    }

    @Column(name = "WebUrl", length = 128)
    public String getWebUrl() {
        return this.webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    @Column(name = "HotLine", length = 20)
    public String getHotLine() {
        return this.hotLine;
    }

    public void setHotLine(String hotLine) {
        this.hotLine = hotLine;
    }

    @Column(name = "Address", length = 64)
    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the partnerLogo
     */
    @Column(name = "PartnerLogo", length = 128)
    public String getPartnerLogo() {
        return partnerLogo;
    }

    /**
     * @param partnerLogo the partnerLogo to set
     */
    public void setPartnerLogo(String partnerLogo) {
        this.partnerLogo = partnerLogo;
    }
    
    
    @Column(name = "PartnerEnCode", length = 50)
    public String getPartnerEnCode() {
        return partnerEnCode;
    }

    public void setPartnerEnCode(String partnerEnCode) {
        this.partnerEnCode = partnerEnCode;
    }

    @OneToMany(cascade = {}, fetch = FetchType.LAZY, mappedBy = "basePartnerInfo")
    public Set<BasePartnerProduct> getBasePartnerProducts() {
        return this.basePartnerProducts;
    }

    public void setBasePartnerProducts(Set<BasePartnerProduct> basePartnerProducts) {
        this.basePartnerProducts = basePartnerProducts;
    }

    @Column(name = "PartnerEmail", length = 36)
    public String getPartnerEmail() {
        return partnerEmail;
    }

    public void setPartnerEmail(String partnerEmail) {
        this.partnerEmail = partnerEmail;
    }

    @Column(name = "DeleteFlag", length = 1)
    public Short getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Short deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    @Column(name = "Currency", length = 36)
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
    
    
}
