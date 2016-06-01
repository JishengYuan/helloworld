/*
 * FileName: InventoryModel.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.business.inventory.model;

import java.math.BigDecimal;
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

/**
 * <p>
 * Description: 
 * </p>
 *
 * @author guo_kemeng
 * @version 1.0

 * <p>
 * History: 
 *
 * Date                     Author         Version     Description
 * ---------------------------------------------------------------------------------
 * 2015年2月3日 下午3:42:33          guo_kemeng        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Entity
@Table(name = "business_inventory")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "borrowProductModels"})
public class InventoryModel extends DefaultBaseModel{

    /**
     * long serialVersionUID :       
     * @since  2015年2月3日 guokemenng
     */
    private static final long serialVersionUID = 3541842761964098953L;
    
    public static String PRODUCTNO = "productNo";
    public static String SERIESNO = "seriesNo";
    
    private String partner;
    private String productNo;
    private int number;
    private String appearance;
    private String seriesNo;
    private String location;
    private String remark;
    private BigDecimal cost;
    private BigDecimal rent;
    private short state;
    
    private Set<BorrwInventoryProductModel> borrowProductModels = new HashSet<BorrwInventoryProductModel>();
    
    @Column(name = "Partner", length = 32)
    public String getPartner() {
        return partner;
    }
    public void setPartner(String partner) {
        this.partner = partner;
    }
    
    @Column(name = "ProductNo", length = 32)
    public String getProductNo() {
        return productNo;
    }
    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }
    
    @Column(name = "Number")
    public int getNumber() {
        return number;
    }
    public void setNumber(int number) {
        this.number = number;
    }
    @Column(name = "Appearance", length = 32)
    public String getAppearance() {
        return appearance;
    }
    public void setAppearance(String appearance) {
        this.appearance = appearance;
    }
    
    @Column(name = "SeriesNo", length = 32)
    public String getSeriesNo() {
        return seriesNo;
    }
    public void setSeriesNo(String seriesNo) {
        this.seriesNo = seriesNo;
    }
    @Column(name = "Location", length = 32)
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    @Column(name = "Remark", length = 128)
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    @Column(name = "Cost",precision = 11, scale = 2)
    public BigDecimal getCost() {
        return cost;
    }
    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }
    @Column(name = "Rent",precision = 11, scale = 2)
    public BigDecimal getRent() {
        return rent;
    }
    public void setRent(BigDecimal rent) {
        this.rent = rent;
    }
    @Column(name = "State")
    public short getState() {
        return state;
    }
    public void setState(short state) {
        this.state = state;
    }
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "inventoryId")
    public Set<BorrwInventoryProductModel> getBorrowProductModels() {
        return borrowProductModels;
    }
    public void setBorrowProductModels(Set<BorrwInventoryProductModel> borrowProductModels) {
        this.borrowProductModels = borrowProductModels;
    }
}
