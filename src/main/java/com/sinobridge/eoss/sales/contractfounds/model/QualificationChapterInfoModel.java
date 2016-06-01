/*
 * FileName: QualificationChapterInfoModel.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.sales.contractfounds.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.sinobridge.base.core.model.imp.DefaultBaseModel;

/**
 * <p>
 * Description: 信息记录
 * </p>
 *
 * @author vermouth
 * @version 1.0

 * <p>
 * History: 
 *
 * Date                     Author         Version     Description
 * ---------------------------------------------------------------------------------
 * 2015年12月16日 上午10:32:33          vermouth        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Entity
@Table(name = "sales_qualificationchapterinfo")
public class QualificationChapterInfoModel extends DefaultBaseModel {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String qcName;
    private String qcType;
    private int totals;
    private int borrows;
    private int remains;
    private String qcstatus;
    private String usedesc;

    /**
     * @return the qcName
     */
    @Column(name = "QcName", length = 128)
    public String getQcName() {
        return qcName;
    }

    /**
     * @param qcName the qcName to set
     */
    public void setQcName(String qcName) {
        this.qcName = qcName;
    }

    /**
     * @return the qcType
     */
    @Column(name = "QcType", length = 4)
    public String getQcType() {
        return qcType;
    }

    /**
     * @param qcType the qcType to set
     */
    public void setQcType(String qcType) {
        this.qcType = qcType;
    }

    /**
     * @return the totals
     */
    @Column(name = "Totals", length = 11)
    public int getTotals() {
        return totals;
    }

    /**
     * @param totals the totals to set
     */
    public void setTotals(int totals) {
        this.totals = totals;
    }

    /**
     * @return the borrows
     */
    @Column(name = "Borrows", length = 11)
    public int getBorrows() {
        return borrows;
    }

    /**
     * @param borrows the borrows to set
     */
    public void setBorrows(int borrows) {
        this.borrows = borrows;
    }

    /**
     * @return the remains
     */
    @Column(name = "Remains", length = 11)
    public int getRemains() {
        return remains;
    }

    /**
     * @param remains the remains to set
     */
    public void setRemains(int remains) {
        this.remains = remains;
    }

    /**
     * @return the qcstatus
     */
    @Column(name = "Qcstatus", length = 1)
    public String getQcstatus() {
        return qcstatus;
    }

    /**
     * @param qcstatus the qcstatus to set
     */
    public void setQcstatus(String qcstatus) {
        this.qcstatus = qcstatus;
    }

    /**
     * @return the usedesc
     */
    @Transient
    public String getUsedesc() {
        return usedesc;
    }

    /**
     * @param usedesc the usedesc to set
     */
    public void setUsedesc(String usedesc) {
        this.usedesc = usedesc;
    }

}
