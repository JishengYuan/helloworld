/*
 * FileName: FundsSalesLog.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.salesfundsCost.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.sinobridge.base.core.model.imp.DefaultBaseModel;
import com.sinobridge.base.core.utils.CustomDateSerializer;

/**
 * <p>
 * Description: 合同金额核算日志记录表
 * </p>
 *
 * @author tigq
 * @version 1.0

 * <p>
 * History: 
 *
 * Date                     Author         Version     Description
 * ---------------------------------------------------------------------------------
 * 2015年12月25日 下午3:55:05          tigq        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Entity
@Table(name = "funds_saleslog")
public class FundsSalesLog extends DefaultBaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private BigDecimal opAmount;
    private Date opDate;
    private String opDesc;
    private String contractCode;
    private BigDecimal hiAmount;

    /**
     * @return the opAmount
     */
    @Column(name = "opAmount")
    public BigDecimal getOpAmount() {
        return opAmount;
    }

    /**
     * @param opAmount the opAmount to set
     */
    public void setOpAmount(BigDecimal opAmount) {
        this.opAmount = opAmount;
    }

    /**
     * @return the opDate
     */
    //@Temporal(TemporalType.DATE)
    @JsonSerialize(using = CustomDateSerializer.class)
    @Column(name = "opDate")
    public Date getOpDate() {
        return opDate;
    }

    /**
     * @param opDate the opDate to set
     */
    public void setOpDate(Date opDate) {
        this.opDate = opDate;
    }

    /**
     * @return the opDesc
     */
    @Column(name = "opDesc", length = 256)
    public String getOpDesc() {
        return opDesc;
    }

    /**
     * @param opDesc the opDesc to set
     */
    public void setOpDesc(String opDesc) {
        this.opDesc = opDesc;
    }

    /**
     * @return the contractCode
     */
    @Column(name = "contractCode", length = 32)
    public String getContractCode() {
        return contractCode;
    }

    /**
     * @param contractCode the contractCode to set
     */
    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }

    @Column(name = "hiAmount")
    public BigDecimal getHiAmount() {
        return hiAmount;
    }

    public void setHiAmount(BigDecimal hiAmount) {
        this.hiAmount = hiAmount;
    }

}
