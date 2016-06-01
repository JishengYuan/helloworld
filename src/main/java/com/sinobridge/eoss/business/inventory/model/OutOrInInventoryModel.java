package com.sinobridge.eoss.business.inventory.model;

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
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.sinobridge.base.core.model.imp.DefaultBaseModel;
import com.sinobridge.base.core.utils.CustomDateSerializer;

/**
 * <p>
 * Description: OutOrInInventoryModel
 * </p>
 *
 * @author guo_kemeng
 * @version 1.0

 * <p>
 * History: 
 *
 * Date                     Author         Version     Description
 * ---------------------------------------------------------------------------------
 * 2015年2月3日 下午4:10:17          guo_kemeng        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Entity
@Table(name = "business_inventory_borr")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "borrowProductModels", "borrowQuantityModels" })
public class OutOrInInventoryModel extends DefaultBaseModel {

    /**
     * long serialVersionUID :       
     * @since  2015年2月3日 guokemenng
     */
    private static final long serialVersionUID = -8605721631625532307L;

    private String serialNum;
    private String remark;
    private String customerManage;
    private String customerManageName;
    private Date borrowTime;
    private long projectId;
    private String projectName;
    private String engineers;
    private String userContact;
    private String userDept;
    private String address;
    private String phone;
    private Date dueTime;
    private int zipCode;
    private Date createDate;

    //出库、入库单状态
    private short state;
    //出库、入库区别
    private short type;

    private Set<BorrwInventoryProductModel> borrowProductModels = new HashSet<BorrwInventoryProductModel>();
    private Set<BorrowQuantityModel> borrowQuantityModels = new HashSet<BorrowQuantityModel>();

    @Column(name = "SerialNum", length = 32)
    public String getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum;
    }

    @Column(name = "Remark", length = 128)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name = "CustomerManage", length = 32)
    public String getCustomerManage() {
        return customerManage;
    }

    public void setCustomerManage(String customerManage) {
        this.customerManage = customerManage;
    }

    @Column(name = "CustomerManageName", length = 32)
    public String getCustomerManageName() {
        return customerManageName;
    }

    public void setCustomerManageName(String customerManageName) {
        this.customerManageName = customerManageName;
    }

    @JsonSerialize(using = CustomDateSerializer.class)
    @Column(name = "BorrowTime")
    public Date getBorrowTime() {
        return borrowTime;
    }

    public void setBorrowTime(Date borrowTime) {
        this.borrowTime = borrowTime;
    }

    @Column(name = "ProjectId")
    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    @Column(name = "ProjectName", length = 32)
    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    @Column(name = "Engineers", length = 32)
    public String getEngineers() {
        return engineers;
    }

    public void setEngineers(String engineers) {
        this.engineers = engineers;
    }

    @Column(name = "UserContact", length = 32)
    public String getUserContact() {
        return userContact;
    }

    public void setUserContact(String userContact) {
        this.userContact = userContact;
    }

    @Column(name = "UserDept", length = 32)
    public String getUserDept() {
        return userDept;
    }

    public void setUserDept(String userDept) {
        this.userDept = userDept;
    }

    @Column(name = "Address", length = 32)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "ZipCode")
    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "borrowingId")
    public Set<BorrwInventoryProductModel> getBorrowProductModels() {
        return borrowProductModels;
    }

    public void setBorrowProductModels(Set<BorrwInventoryProductModel> borrowProductModels) {
        this.borrowProductModels = borrowProductModels;
    }

    @Column(name = "State")
    public short getState() {
        return state;
    }

    public void setState(short state) {
        this.state = state;
    }

    @Column(name = "Type")
    public short getType() {
        return type;
    }

    public void setType(short type) {
        this.type = type;
    }

    @Column(name = "phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @JsonSerialize(using = CustomDateSerializer.class)
    @Column(name = "createDate")
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "borrowingId")
    public Set<BorrowQuantityModel> getBorrowQuantityModels() {
        return borrowQuantityModels;
    }

    public void setBorrowQuantityModels(Set<BorrowQuantityModel> borrowQuantityModels) {
        this.borrowQuantityModels = borrowQuantityModels;
    }

    @JsonSerialize(using = CustomDateSerializer.class)
    @Column(name = "DueTime")
    public Date getDueTime() {
        return dueTime;
    }

    public void setDueTime(Date dueTime) {
        this.dueTime = dueTime;
    }

}
