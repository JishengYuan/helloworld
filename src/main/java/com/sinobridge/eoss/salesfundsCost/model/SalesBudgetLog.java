/*
 * FileName: SalesBudgetLog.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.salesfundsCost.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.sinobridge.base.core.model.imp.DefaultBaseModel;

/**
 * <p>
 * Description: 预测log实体
 * </p>
 *
 * @author vermouth
 * @version 1.0

 * <p>
 * History: 
 *
 * Date                     Author         Version     Description
 * ---------------------------------------------------------------------------------
 * 2016年2月23日 下午4:02:57          vermouth        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Entity
@Table(name = "funds_salesBudget_log")
public class SalesBudgetLog extends DefaultBaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private Date createDate;
    private Long budgetFundsId;//预测ID
    private String remark;//记录:原值，新值

    /**
     * @return the createDate
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "createDate")
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate the createDate to set
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * @return the budgetFundsId
     */
    @Column(name = "budgetFundsId", length = 20)
    public Long getBudgetFundsId() {
        return budgetFundsId;
    }

    /**
     * @param budgetFundsId the budgetFundsId to set
     */
    public void setBudgetFundsId(Long budgetFundsId) {
        this.budgetFundsId = budgetFundsId;
    }

    /**
     * @return the remark
     */
    @Column(name = "remark", length = 128)
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark the remark to set
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

}
