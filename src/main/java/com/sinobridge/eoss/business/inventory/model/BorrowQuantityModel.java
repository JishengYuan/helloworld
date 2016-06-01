/*
 * FileName: BorrowQuantityModel.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.business.inventory.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.sinobridge.base.core.model.imp.DefaultBaseModel;

/**备货借出单产品数量表
 * <p>
 * Description: 
 * </p>
 *
 * @author vermouth
 * @version 1.0

 * <p>
 * History: 
 *
 * Date                     Author         Version     Description
 * ---------------------------------------------------------------------------------
 * 2015年11月20日 下午4:26:53          vermouth        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Entity
@Table(name="business_inventory_qua")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "borrowingId"})
public class BorrowQuantityModel extends DefaultBaseModel{

    /**
     * 
     */
    private static final long serialVersionUID = -7277769490874063452L;
    
    private OutOrInInventoryModel borrowingId;
    private String partner;
    private String productNo;
    private int quantity;
        //0是未审批  1是已审批
    private short status;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "BorrowingId")
    public OutOrInInventoryModel getBorrowingId() {
        return borrowingId;
    }
    public void setBorrowingId(OutOrInInventoryModel borrowingId) {
        this.borrowingId = borrowingId;
    }
    
    @Column(name = "ProductNo",length = 32)
    public String getProductNo() {
        return productNo;
    }
    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }
    
    @Column(name = "Quantity",length = 8)
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    @Column(name = "Status")
    public short getStatus() {
        return status;
    }
    public void setStatus(short status) {
        this.status = status;
    }
    
    @Column(name = "Partner",length = 32)
    public String getPartner() {
        return partner;
    }
    public void setPartner(String partner) {
        this.partner = partner;
    }
    
   
  
    
    
    
}
