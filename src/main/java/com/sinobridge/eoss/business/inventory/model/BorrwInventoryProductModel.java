package com.sinobridge.eoss.business.inventory.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.sinobridge.base.core.model.imp.DefaultBaseModel;
import com.sinobridge.base.core.utils.CustomDateSerializer;

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
 * 2015年2月4日 上午11:08:00          guo_kemeng        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Entity
@Table(name = "business_inventory_bproduct")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "borrowingId", "inventoryId"})
public class BorrwInventoryProductModel extends DefaultBaseModel{

    /**
     * long serialVersionUID :       
     * @since  2015年2月4日 guokemenng
     */
    private static final long serialVersionUID = -1315555421934147150L;
    
    private OutOrInInventoryModel borrowingId;
    private InventoryModel inventoryId;
    private BigDecimal rent;
    private Date borrwTime;
    private Date returnTime;
    private PutOrInInventoryModel putOrInInventoryModel;
    
    
   
	//0 是未归还  1是已归还
    private short state;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "BorrowingId")
    public OutOrInInventoryModel getBorrowingId() {
        return borrowingId;
    }
    public void setBorrowingId(OutOrInInventoryModel borrowingId) {
        this.borrowingId = borrowingId;
    }
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "InventoryId")
    public InventoryModel getInventoryId() {
        return inventoryId;
    }
    public void setInventoryId(InventoryModel inventoryId) {
        this.inventoryId = inventoryId;
    }
    @Column(name = "Rent",precision = 11, scale = 2)
    public BigDecimal getRent() {
        return rent;
    }
    public void setRent(BigDecimal rent) {
        this.rent = rent;
    }
    @JsonSerialize(using = CustomDateSerializer.class)
    @Column(name = "BorrwTime")
    public Date getBorrwTime() {
        return borrwTime;
    }
    public void setBorrwTime(Date borrwTime) {
        this.borrwTime = borrwTime;
    }
    @JsonSerialize(using = CustomDateSerializer.class)
    @Column(name = "ReturnTime")
    public Date getReturnTime() {
        return returnTime;
    }
    public void setReturnTime(Date returnTime) {
        this.returnTime = returnTime;
    }
    
    @Column(name = "State")
    public short getState() {
        return state;
    }
    public void setState(short state) {
        this.state = state;
    }
    
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ReturnID")
    public PutOrInInventoryModel getPutOrInInventoryModel() {
		return putOrInInventoryModel;
	}
	public void setPutOrInInventoryModel(PutOrInInventoryModel putOrInInventoryModel) {
		this.putOrInInventoryModel = putOrInInventoryModel;
	}
    

}
