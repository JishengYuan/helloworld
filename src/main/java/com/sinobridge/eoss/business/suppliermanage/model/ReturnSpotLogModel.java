package com.sinobridge.eoss.business.suppliermanage.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sinobridge.base.core.model.imp.DefaultBaseModel;

@Entity
@Table(name="business_returnspotlog")
public class ReturnSpotLogModel extends DefaultBaseModel {

    private static final long serialVersionUID = 1L;
    private float banlance;
    private float amount;
    private String operaotor;
    private Date operatorTime;
    private String remark;
    
    private ReturnSpotModel spotModel;

    @Column(name = "Banlance", length = 20)
    public float getBanlance() {
        return banlance;
    }

    public void setBanlance(float banlance) {
        this.banlance = banlance;
    }

    @Column(name = "Amount", length = 20)
    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    @Column(name = "Operaotor", length = 36)
    public String getOperaotor() {
        return operaotor;
    }

    public void setOperaotor(String operaotor) {
        this.operaotor = operaotor;
    }

    @Column(name = "OperatorTime")
    public Date getOperatorTime() {
        return operatorTime;
    }

    public void setOperatorTime(Date operatorTime) {
        this.operatorTime = operatorTime;
    }

    @Column(name = "Remark", length = 64)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ReturnSpot")
    public ReturnSpotModel getSpotModel() {
        return spotModel;
    }
    public void setSpotModel(ReturnSpotModel spotModel) {
        this.spotModel = spotModel;
    }

}