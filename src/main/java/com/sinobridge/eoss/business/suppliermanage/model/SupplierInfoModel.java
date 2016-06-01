package com.sinobridge.eoss.business.suppliermanage.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.sinobridge.base.core.model.imp.DefaultBaseModel;

@Entity
@Table(name = "bussiness_supplier")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "contactsModels", "spotModels" })
//public class SupplierInfoModel implements java.io.Serializable {
public class SupplierInfoModel extends DefaultBaseModel {

    private static final long serialVersionUID = 1L;

    public static final String BIZOWNER = "bizOwner";
    public static final String SUPPLIERTYPE = "supplierType";
    public static final String SUPPLIERCODE = "supplierCode";
    public static final String SHORTNAME = "shortName";
    public static final String ENABLERETURNSPOT = "enableReturnSpot";

    //    private Long id;
    //
    //    @Id
    //    @Column(name = "id", unique = true, nullable = false)
    //    public Long getId() {
    //        return id;
    //    }
    //
    //    public void setId(Long id) {
    //        this.id = id;
    //    }

    private String supplierType;
    private String bizOwner;
    private String supplierCode;
    private String supplierName;
    private String shortName;
    private String englishName;
    private String grade;
    private String scal;
    private String relationShip;
    private String relationGrade;
    private String country;
    private String region;
    private String province;
    private String city;
    private String phone;
    private String fax;
    private int zipCode;
    private String web;
    private String address;
    private String otherAddress;
    private String bankName;
    private String bankAccount;
    private String taxNo;
    private String orgCode;
    private String bizScope;
    private String background;
    private String remark;
    private Date createTime;
    private String creator;
    private Date modifyTime;
    private String modifier;

    private short enableReturnSpot;
    private String state; //审批状态,0：未审批,1：审批通过,2:审批未通过

    private Set<SupplierContactsModel> contactsModels = new HashSet<SupplierContactsModel>();
    private Set<ReturnSpotModel> spotModels = new HashSet<ReturnSpotModel>();

    @Column(name = "SupplierType", length = 36)
    public String getSupplierType() {
        return supplierType;
    }

    public void setSupplierType(String supplierType) {
        this.supplierType = supplierType;
    }

    @Column(name = "BizOwner", length = 36)
    public String getBizOwner() {
        return bizOwner;
    }

    public void setBizOwner(String bizOwner) {
        this.bizOwner = bizOwner;
    }

    @Column(name = "SupplierCode", length = 36)
    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    @Column(name = "SupplierName", length = 36)
    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    @Column(name = "Grade", length = 36)
    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    @Column(name = "Scal", length = 36)
    public String getScal() {
        return scal;
    }

    public void setScal(String scal) {
        this.scal = scal;
    }

    @Column(name = "RelationShip", length = 36)
    public String getRelationShip() {
        return relationShip;
    }

    @Column(name = "RelationGrade", length = 36)
    public String getRelationGrade() {
        return relationGrade;
    }

    public void setRelationGrade(String relationGrade) {
        this.relationGrade = relationGrade;
    }

    public void setRelationShip(String relationShip) {
        this.relationShip = relationShip;
    }

    @Column(name = "Country", length = 36)
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Column(name = "Region", length = 36)
    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @Column(name = "Province", length = 36)
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @Column(name = "City", length = 36)
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Column(name = "Phone", length = 36)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Column(name = "Fax", length = 36)
    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    @Column(name = "ZipCode", length = 10)
    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    @Column(name = "Web", length = 36)
    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    @Column(name = "Address", length = 256)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "OtherAddress", length = 256)
    public String getOtherAddress() {
        return otherAddress;
    }

    public void setOtherAddress(String otherAddress) {
        this.otherAddress = otherAddress;
    }

    @Column(name = "BankName", length = 36)
    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    @Column(name = "BankAccount", length = 36)
    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    @Column(name = "TaxNo", length = 36)
    public String getTaxNo() {
        return taxNo;
    }

    public void setTaxNo(String taxNo) {
        this.taxNo = taxNo;
    }

    @Column(name = "OrgCode", length = 36)
    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    @Column(name = "BizScope", length = 256)
    public String getBizScope() {
        return bizScope;
    }

    public void setBizScope(String bizScope) {
        this.bizScope = bizScope;
    }

    @Column(name = "Background", length = 256)
    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    @Column(name = "Remark", length = 256)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name = "CreateTime")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "Creator", length = 36)
    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    @Column(name = "ModifyTime")
    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    @Column(name = "Modifier", length = 36)
    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    @Column(name = "ShortName", length = 36)
    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    @Column(name = "EnableReturnSpot", length = 2)
    public short getEnableReturnSpot() {
        return enableReturnSpot;
    }

    public void setEnableReturnSpot(short enableReturnSpot) {
        this.enableReturnSpot = enableReturnSpot;
    }

    @Column(name = "EnglishName", length = 108)
    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "supplierInfo")
    public Set<SupplierContactsModel> getContactsModels() {
        return contactsModels;
    }

    public void setContactsModels(Set<SupplierContactsModel> contactsModels) {
        this.contactsModels = contactsModels;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "supplierInfo")
    public Set<ReturnSpotModel> getSpotModels() {
        return spotModels;
    }

    public void setSpotModels(Set<ReturnSpotModel> spotModels) {
        this.spotModels = spotModels;
    }

    @Column(name = "State", length = 32)
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

}