package com.sinobridge.eoss.business.stock.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.sinobridge.base.core.model.imp.DefaultBaseModel;

/**
 * 序列号表
 */
@Entity
@Table(name = "business_serial")
public class SerialModel extends DefaultBaseModel {

    private static final long serialVersionUID = 1L;
    
    private String contractCode;	//合同编号
    private String inboundCode;	//入库单号
    private String orderCode;		//订单编号
    private String serial;				//序列号

    public SerialModel(){}
	public SerialModel(String contractCode, String inboundCode, String orderCode,String serial) {
    	super();
    	this.contractCode = contractCode;
    	this.inboundCode = inboundCode;
    	this.orderCode = orderCode;
    	this.serial = serial;
	}
    
	@Column(name = "contractCode")
	public String getContractCode() {
		return contractCode;
	}
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	
	@Column(name = "inboundCode")
	public String getInboundCode() {
		return inboundCode;
	}
	public void setInboundCode(String inboundCode) {
		this.inboundCode = inboundCode;
	}
	
	@Column(name = "orderCode")
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	
	@Column(name = "serial")
	public String getSerial() {
		return serial;
	}
	public void setSerial(String serial) {
		this.serial = serial;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}