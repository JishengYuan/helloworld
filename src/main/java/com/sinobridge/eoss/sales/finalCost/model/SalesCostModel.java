/*
 * FileName: SalesCostModel.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.sales.finalCost.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.sinobridge.base.core.model.imp.DefaultBaseModel;

/**
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
 * 2015年9月8日 上午10:04:19          vermouth        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Entity
@Table(name = "sales_cost")
public class SalesCostModel extends DefaultBaseModel{
    /**
     * long serialVersionUID :       
     * @since  
     */
    private static final long serialVersionUID = 1L;
    
    //合同ID
    private Long salesContractId;
    //计算时间
    private int dateInt;
    //时间
    private Date createTime;
    //合同收款
    private BigDecimal salesReceive;
    //订单付款
    private BigDecimal orderPay;
    //资金占用成本
    private BigDecimal cost;
    
    public SalesCostModel(){
    }
    
    @Column(name="SalesContractId", length = 20)
    public Long getSalesContractId() {
        return salesContractId;
    }
    public void setSalesContractId(Long salesContractId) {
        this.salesContractId = salesContractId;
    }
    
    @Column(name="DateInt", length = 10)
    public int getDateInt() {
        return dateInt;
    }
    public void setDateInt(int dateInt) {
        this.dateInt = dateInt;
    }
    
    @Column(name="SalesReceive", precision = 11, scale = 2)
    public BigDecimal getSalesReceive() {
        return salesReceive;
    }
    public void setSalesReceive(BigDecimal salesReceive) {
        this.salesReceive = salesReceive;
    }
    
    @Column(name="OrderPay", precision = 11, scale = 2)
    public BigDecimal getOrderPay() {
        return orderPay;
    }
    public void setOrderPay(BigDecimal orderPay) {
        this.orderPay = orderPay;
    }
    
    @Column(name="Cost", precision = 11, scale = 2)
    public BigDecimal getCost() {
        return cost;
    }
    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }
    
    @Temporal(TemporalType.DATE)
    @Column(name = "CreateTime")
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
    
}
