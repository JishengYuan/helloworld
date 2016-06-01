package com.sinobridge.eoss.business.projectmanage.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.sinobridge.base.core.model.imp.DefaultBaseModel;

@Entity
@Table(name = "business_vendor")
public class VendorModel extends DefaultBaseModel{

    private static final long serialVersionUID = 1L;

    private String vendorCode;
    private String vendorName;
    private String vendorEnName;
    private String currency;
    
    @Column(name = "VendorCode", length = 32)
    public String getVendorCode() {
        return vendorCode;
    }
    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }
    @Column(name = "VendorName", length = 32)
    public String getVendorName() {
        return vendorName;
    }
    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }
    @Column(name = "VendorEnName", length = 32)
    public String getVendorEnName() {
        return vendorEnName;
    }
    public void setVendorEnName(String vendorEnName) {
        this.vendorEnName = vendorEnName;
    }
    @Column(name = "Currency", length = 36)
    public String getCurrency() {
        return currency;
    }
    public void setCurrency(String currency) {
        this.currency = currency;
    }
    
    
}