package com.sinobridge.eoss.business.suppliermanage.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.sinobridge.base.core.model.imp.DefaultBaseModel;

@Entity
@Table(name="business_returnspot")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "supplierInfo","spotLogSet"})
public class ReturnSpotModel extends DefaultBaseModel {

    private static final long serialVersionUID = 1L;
    
    public static final String RETURNAMOUNT = "returnAmount";
    public static final String SUPPLIERINFO = "supplierInfo";
    
    private float returnAmount;
    private String remark;
    private String creator;
    private Date createTime;
    
    private SupplierInfoModel supplierInfo;
    private Set<ReturnSpotLogModel> spotLogSet = new HashSet<ReturnSpotLogModel>();
    
    @Column(name = "ReturnAmount")
    public float getReturnAmount() {
        return returnAmount;
    }

    public void setReturnAmount(float returnAmount) {
        this.returnAmount = returnAmount;
    }

    @Column(name = "Remark", length = 64)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name = "Creator", length = 36)
    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    @Column(name = "CreateTime")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @ManyToOne(cascade={},fetch = FetchType.LAZY)
    @JoinColumn(name = "SupplierId")
    public SupplierInfoModel getSupplierInfo() {
        return supplierInfo;
    }
    public void setSupplierInfo(SupplierInfoModel supplierInfo) {
        this.supplierInfo = supplierInfo;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "spotModel")
    public Set<ReturnSpotLogModel> getSpotLogSet() {
        return spotLogSet;
    }
    public void setSpotLogSet(Set<ReturnSpotLogModel> spotLogSet) {
        this.spotLogSet = spotLogSet;
    }
    
    private String supplierType;
    private String supplierCode;
    private String shortName;

    @Transient
    public String getSupplierType() {
        if(getSupplierInfo() != null){
            return getSupplierInfo().getSupplierType();
        } else {
            return supplierType;
        }
    }

    public void setSupplierType(String supplierType) {
        this.supplierType = supplierType;
    }
    
    @Transient
    public String getSupplierCode() {
        if(getSupplierInfo() != null){
            return getSupplierInfo().getSupplierCode();
        } else {
            return supplierCode;
        }
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    @Transient
    public String getShortName() {
        if(getSupplierInfo() != null){
            return getSupplierInfo().getShortName();
        } else {
            return shortName;
        }
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
    
}