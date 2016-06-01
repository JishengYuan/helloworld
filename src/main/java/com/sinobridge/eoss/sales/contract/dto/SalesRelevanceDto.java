/*
 * FileName: SalesRelevanceDto.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.sales.contract.dto;

import java.math.BigDecimal;

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
 * 2015年3月5日 上午9:18:45          vermouth        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
public class SalesRelevanceDto implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    //合同编码
    private String contractCode;
    //合同类型
    private String contractType;
    //订单编号
    private String orderCode;
    //合同下单数
    private String productNum;
    //对应订单数
    private String orderNum;
    //合同状态
    private String salesOrder;
    //合同总金额
    private BigDecimal contractAmount;
    //订单下单数
    private String orderProNum;
    //订单分项金额
    private BigDecimal orderProductAmount;
    //订单下单日期
    private String beginTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getProductNum() {
        return productNum;
    }

    public void setProductNum(String productNum) {
        this.productNum = productNum;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getSalesOrder() {
        return salesOrder;
    }

    public void setSalesOrder(String salesOrder) {
        this.salesOrder = salesOrder;
    }

    public String getOrderProNum() {
        return orderProNum;
    }

    public void setOrderProNum(String orderProNum) {
        this.orderProNum = orderProNum;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public BigDecimal getContractAmount() {
        return contractAmount;
    }

    public void setContractAmount(BigDecimal contractAmount) {
        this.contractAmount = contractAmount;
    }

    public BigDecimal getOrderProductAmount() {
        return orderProductAmount;
    }

    public void setOrderProductAmount(BigDecimal orderProductAmount) {
        this.orderProductAmount = orderProductAmount;
    }

}
