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
 * 
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
 * 2015年2月5日 下午2:11:13       liyx        1.0         To create
 * </p>
 *
 * @since 
 * @see
 */
@Entity
@Table(name = "business_inventory_return")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "borrowProductModels" })
public class PutOrInInventoryModel extends DefaultBaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private Long borrowingId;
    private String returnUser;
    private String returnUserName;
    private Date returnDate;
    private String serialNum;
    private short state;
    private Date createDate;
    private String remark;

    private Set<BorrwInventoryProductModel> borrowProductModels = new HashSet<BorrwInventoryProductModel>();

    @Column(name = "BorrowingId")
    public Long getBorrowingId() {
        return borrowingId;
    }

    public void setBorrowingId(Long borrowingId) {
        this.borrowingId = borrowingId;
    }

    @Column(name = "ReturnUser", length = 32)
    public String getReturnUser() {
        return returnUser;
    }

    public void setReturnUser(String returnUser) {
        this.returnUser = returnUser;
    }

    @Column(name = "ReturnDate")
    @JsonSerialize(using = CustomDateSerializer.class)
    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "putOrInInventoryModel")
    public Set<BorrwInventoryProductModel> getBorrowProductModels() {
        return borrowProductModels;
    }

    public void setBorrowProductModels(Set<BorrwInventoryProductModel> borrowProductModels) {
        this.borrowProductModels = borrowProductModels;
    }

    @Column(name = "ReturnUserName", length = 32)
    public String getReturnUserName() {
        return returnUserName;
    }

    public void setReturnUserName(String returnUserName) {
        this.returnUserName = returnUserName;
    }

    @Column(name = "SerialNum", length = 32)
    public String getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum;
    }

    @Column(name = "State")
    public short getState() {
        return state;
    }

    public void setState(short state) {
        this.state = state;
    }

    @Column(name = "createDate")
    @JsonSerialize(using = CustomDateSerializer.class)
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Column(name = "Remark", length = 512)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
