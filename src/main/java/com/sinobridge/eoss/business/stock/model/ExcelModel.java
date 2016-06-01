package com.sinobridge.eoss.business.stock.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.sinobridge.base.core.model.imp.DefaultBaseModel;

/**
 * excel
 */
@Entity
@Table(name = "business_excel")
public class ExcelModel extends DefaultBaseModel {

    private static final long serialVersionUID = 1L;
    
    private String inboundCode;	//入库单号
    private String orderCode;		//订单编号
    private String contractCode;	//合同编号
    private String pono;					//批次号
    private String model;				//型号
    private int number;			//数量
    private String serial;				//序列号
    private String recipientName;//收货人
    
    public ExcelModel(){}
	public ExcelModel(String inboundCode, String orderCode, String contractCode, String pono,String model, int number, String serial, String recipientName) {
		super();
		this.inboundCode = inboundCode;
		this.orderCode = orderCode;
		this.contractCode = contractCode;
		this.pono = pono;
		this.model = model;
		this.number = number;
		this.serial = serial;
		this.recipientName = recipientName;
	}
	
	@Column(name = "InboundCode")
	public String getInboundCode() {
		return inboundCode;
	}
	public void setInboundCode(String inboundCode) {
		this.inboundCode = inboundCode;
	}
	
	@Column(name = "OrderCode")
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	
	@Column(name = "ContractCode")
	public String getContractCode() {
		return contractCode;
	}
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	
	@Column(name = "Pono")
	public String getPono() {
		return pono;
	}
	public void setPono(String pono) {
		this.pono = pono;
	}
	
	@Column(name = "Model")
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	
	@Column(name = "Number")
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	
	@Column(name = "Serial")
	public String getSerial() {
		return serial;
	}
	public void setSerial(String serial) {
		this.serial = serial;
	}
	
	@Column(name = "RecipientName")
	public String getRecipientName() {
		return recipientName;
	}
	public void setRecipientName(String recipientName) {
		this.recipientName = recipientName;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
    
}