package com.sinobridge.eoss.business.projectmanage.dto;

import java.util.HashSet;
import java.util.Set;

import com.sinobridge.eoss.business.projectmanage.model.BasePartnerProduct;

/**
 * ConfPartnerinfo entity. @author MyEclipse Persistence Tools
 */
public class Partnerinfo implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    // Fields    

    public static final String PARTNERTYPE = "partnerType";

    public static final String SERVICEPARTNERTYPE = "servicePartnerType";

    private String id;
    private int index;
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
    private Set<BasePartnerProduct> basePartnerProducts = new HashSet<BasePartnerProduct>(0);

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Short getServicePartnerType() {
        return servicePartnerType;
    }

    public void setServicePartnerType(Short servicePartnerType) {
        this.servicePartnerType = servicePartnerType;
    }

    public String getPartnerCode() {
        return partnerCode;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public String getPartnerFullName() {
        return partnerFullName;
    }

    public void setPartnerFullName(String partnerFullName) {
        this.partnerFullName = partnerFullName;
    }

    public String getPartnerOid() {
        return partnerOid;
    }

    public void setPartnerOid(String partnerOid) {
        this.partnerOid = partnerOid;
    }

    public String getEnterpriseCode() {
        return enterpriseCode;
    }

    public void setEnterpriseCode(String enterpriseCode) {
        this.enterpriseCode = enterpriseCode;
    }

    public String getEnterprisePerson() {
        return enterprisePerson;
    }

    public void setEnterprisePerson(String enterprisePerson) {
        this.enterprisePerson = enterprisePerson;
    }

    public String getRegisterMoney() {
        return registerMoney;
    }

    public void setRegisterMoney(String registerMoney) {
        this.registerMoney = registerMoney;
    }

    public String getRegisterAddress() {
        return registerAddress;
    }

    public void setRegisterAddress(String registerAddress) {
        this.registerAddress = registerAddress;
    }

    public Short getEnterpriseSize() {
        return enterpriseSize;
    }

    public void setEnterpriseSize(Short enterpriseSize) {
        this.enterpriseSize = enterpriseSize;
    }

    public Short getEnterpriseType() {
        return enterpriseType;
    }

    public void setEnterpriseType(Short enterpriseType) {
        this.enterpriseType = enterpriseType;
    }

    public String getQualifications() {
        return qualifications;
    }

    public void setQualifications(String qualifications) {
        this.qualifications = qualifications;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public String getHotLine() {
        return hotLine;
    }

    public void setHotLine(String hotLine) {
        this.hotLine = hotLine;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPartnerLogo() {
        return partnerLogo;
    }

    public void setPartnerLogo(String partnerLogo) {
        this.partnerLogo = partnerLogo;
    }

    public String getPartnerType() {
        return partnerType;
    }

    public void setPartnerType(String partnerType) {
        this.partnerType = partnerType;
    }

    public String getPartnerEnCode() {
        return partnerEnCode;
    }

    public void setPartnerEnCode(String partnerEnCode) {
        this.partnerEnCode = partnerEnCode;
    }


    public Set<BasePartnerProduct> getBasePartnerProducts() {
        return basePartnerProducts;
    }

    public void setBasePartnerProducts(Set<BasePartnerProduct> basePartnerProducts) {
        this.basePartnerProducts = basePartnerProducts;
    }

}