/*
 * FileName: SalesContract.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2015 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.sales.stockupCost.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * 商务确认成本部分的合同实体类
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
 * 2015年10月9日 下午5:05:09      liyx           1.0         To create
 * </p>
 *
 * @since 
 * @see
 */
@Entity
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "stockUpContractCostProductModel" })
@Table(name = "stockup_contractcost")
public class StockUpContractCostModel implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    //合同编码
    private String contractCode;
    //合同金额
    private BigDecimal contractAmount;
    //合同名称
    private String contractName;

    //合同创建人名称
    private String creatorName;

    //订单处理人UserName
    private String orderProcessor;

    //产品合同ID
    private String salesContractId;

    //与产品一对多关系
    private Set<StockUpContractCostProductModel> stockUpContractCostProductModel = new HashSet<StockUpContractCostProductModel>(0);

    //成本确认状态
    private String doState;
    //成本确认时间
    private Date doDate;
    //成本数
    private BigDecimal costNum;

    @Column(name = "ContractAmount")
    public BigDecimal getContractAmount() {
        return contractAmount;
    }

    @Column(name = "ContractCode", length = 32)
    public String getContractCode() {
        return contractCode;
    }

    @Column(name = "ContractName", length = 128)
    public String getContractName() {
        return contractName;
    }

    @Column(name = "CreatorName", length = 20)
    public String getCreatorName() {
        return creatorName;
    }

    /**
     * @return the id
     */
    @Id
    @Column(name = "id", nullable = false, unique = true, length = 20)
    public Long getId() {
        return id;
    }

    /**
     * @return the orderProcessor
     */
    @Column(name = "OrderProcessor", length = 32)
    public String getOrderProcessor() {
        return orderProcessor;
    }

    @Column(name = "SalesContractId", length = 128)
    public String getSalesContractId() {
        return salesContractId;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "stockUpContractCostModel")
    public Set<StockUpContractCostProductModel> getStockUpContractCostProductModel() {
        return stockUpContractCostProductModel;
    }

    public void setContractAmount(BigDecimal contractAmount) {
        this.contractAmount = contractAmount;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @param orderProcessorId the orderProcessor to set
     */
    public void setOrderProcessor(String orderProcessor) {
        this.orderProcessor = orderProcessor;
    }

    public void setStockUpContractCostProductModel(Set<StockUpContractCostProductModel> stockUpContractCostProductModel) {
        this.stockUpContractCostProductModel = stockUpContractCostProductModel;
    }

    public void setSalesContractId(String salesContractId) {
        this.salesContractId = salesContractId;
    }

    @Column(name = "DoState", length = 32)
    public String getDoState() {
        return doState;
    }

    public void setDoState(String doState) {
        this.doState = doState;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "DoDate")
    public Date getDoDate() {
        return doDate;
    }

    public void setDoDate(Date doDate) {
        this.doDate = doDate;
    }

    @Column(name = "CostNum")
    public BigDecimal getCostNum() {
        return costNum;
    }

    public void setCostNum(BigDecimal costNum) {
        this.costNum = costNum;
    }

}
