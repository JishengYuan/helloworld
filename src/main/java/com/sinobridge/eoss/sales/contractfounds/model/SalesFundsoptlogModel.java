/*
 * FileName: SalesFundsoptlogModel.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.sales.contractfounds.model;

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
 * @author tigq
 * @version 1.0

 * <p>
 * History: 
 *
 * Date                     Author         Version     Description
 * ---------------------------------------------------------------------------------
 * 2015年12月17日 下午1:42:13          tigq        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Entity
@Table(name = "sales_fundsoptlog")
public class SalesFundsoptlogModel extends DefaultBaseModel {

    private static final long serialVersionUID = 1L;
    private Long applyFundsId;
    private String optDesc;
    private Date optDate;

    /**
     * @return the applyFundsId
     */
    @Column(name = "applyFundsId")
    public Long getApplyFundsId() {
        return applyFundsId;
    }

    /**
     * @param applyFundsId the applyFundsId to set
     */
    public void setApplyFundsId(Long applyFundsId) {
        this.applyFundsId = applyFundsId;
    }

    /**
     * @return the optDesc
     */
    @Column(name = "optDesc")
    public String getOptDesc() {
        return optDesc;
    }

    /**
     * @param optDesc the optDesc to set
     */
    public void setOptDesc(String optDesc) {
        this.optDesc = optDesc;
    }

    /**
     * @return the optDate
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "optDate")
    public Date getOptDate() {
        return optDate;
    }

    /**
     * @param optDate the optDate to set
     */
    public void setOptDate(Date optDate) {
        this.optDate = optDate;
    }

}
