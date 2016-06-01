package com.sinobridge.eoss.business.stock.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.sinobridge.base.core.model.imp.DefaultBaseModel;

/**
 *	出库表
 */
@Entity
@Table(name = "business_outbound")
public class OutboundModel extends DefaultBaseModel {

    private static final long serialVersionUID = 1L;

    public static final String ID ="id";
    public static final String CONTRACTCODE ="contractCode";
    public static final String OUTBOUNDTIME ="outboundTime";
    
    private String outboundCode; //出库单号
    private Date outboundTime; //出库日期
    private String customerName; //客户简称
    private String customerManager; //客户经理
    private String contractCode; //合同编号
    private String productCode; //产品编码
    private Integer productNum;//产品数量
    private Integer outboundType; //出库类型
    private Integer outboundState; //出库状态
    private String outBoundPer; //出库人

    //到达地址
    private String arrivePlace;
    private String note; //备注

    public OutboundModel() {
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "OutboundTime")
    public Date getOutboundTime() {
        return outboundTime;
    }

    public void setOutboundTime(Date outboundTime) {
        this.outboundTime = outboundTime;
    }

    @Column(name = "CustomerName")
    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    @Column(name = "CustomerManager")
    public String getCustomerManager() {
        return customerManager;
    }

    public void setCustomerManager(String customerManager) {
        this.customerManager = customerManager;
    }

    @Column(name = "ContractCode")
    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }

    @Column(name = "OutboundType")
    public Integer getOutboundType() {
        return outboundType;
    }

    public void setOutboundType(Integer outboundType) {
        this.outboundType = outboundType;
    }

    @Column(name = "OutboundState")
    public Integer getOutboundState() {
        return outboundState;
    }

    public void setOutboundState(Integer outboundState) {
        this.outboundState = outboundState;
    }

    @Column(name = "OutboundCode")
    public String getOutboundCode() {
        return outboundCode;
    }

    public void setOutboundCode(String outboundCode) {
        this.outboundCode = outboundCode;
    }

    @Column(name = "ProductCode")
    public String getProductCode() {
        return productCode;
    }

    /**
     * @param productCode the productCode to set
     */
    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    @Column(name = "ProductNum")
    public Integer getProductNum() {
        return productNum;
    }

    /**
     * @param productNum the productNum to set
     */
    public void setProductNum(Integer productNum) {
        this.productNum = productNum;
    }

    @Column(name = "Note")
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Column(name = "OutBoundPer")
    public String getOutBoundPer() {
        return outBoundPer;
    }

    public void setOutBoundPer(String outBoundPer) {
        this.outBoundPer = outBoundPer;
    }

    @Column(name = "ArrivePlace",length=128)
    public String getArrivePlace() {
        return arrivePlace;
    }

    public void setArrivePlace(String arrivePlace) {
        this.arrivePlace = arrivePlace;
    }
    
    
}