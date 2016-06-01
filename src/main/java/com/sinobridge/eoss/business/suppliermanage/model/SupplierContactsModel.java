package com.sinobridge.eoss.business.suppliermanage.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sinobridge.base.core.model.imp.DefaultBaseModel;

@Entity
@Table(name = "bussiness_suppliercontacts")
public class SupplierContactsModel extends DefaultBaseModel {
    //public class SupplierContactsModel implements java.io.Serializable {

    //    private BigInteger id;
    //
    //    @Id
    //    @Column(name = "id", unique = true, nullable = false)
    //    public BigInteger getId() {
    //        return id;
    //    }
    //
    //    public void setId(Long id) {
    //        this.id = id;
    //    }

    private static final long serialVersionUID = 1L;
    public static final String SUPPLIERINFO = "supplierInfo";
    public static final String CONTRACTNAME = "contactName";
    private String contactName;
    private String contactPhone;
    private String contactTelPhone;
    private String remark;

    private SupplierInfoModel supplierInfo;

    @Column(name = "ContactName", length = 36)
    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    @Column(name = "ContactPhone", length = 20)
    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    @Column(name = "ContactTelPhone", length = 20)
    public String getContactTelPhone() {
        return contactTelPhone;
    }

    public void setContactTelPhone(String contactTelPhone) {
        this.contactTelPhone = contactTelPhone;
    }

    @Column(name = "Remark", length = 128)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "SupplierId")
    public SupplierInfoModel getSupplierInfo() {
        return supplierInfo;
    }

    public void setSupplierInfo(SupplierInfoModel supplierInfo) {
        this.supplierInfo = supplierInfo;
    }

}