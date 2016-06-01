package com.sinobridge.eoss.business.stock.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.sinobridge.base.core.model.imp.DefaultBaseModel;


/**
 *	库存表
 */
@Entity
@Table(name = "business_stock")
public class StockModel extends DefaultBaseModel {

    private static final long serialVersionUID = 1L;
    
    public static final String ID = "id";
    public static final String ORDERCODE = "orderCode";
    public static final String CONTRACTCODE = "contractCode";
    public static final String INBOUNDTIME = "inboundTime";
    public static final String OUTBOUNDTIME = "outboundTime";
    public static final String PRODUCTCODE = "productCode";
    
    private String orderCode;			//订单编号
    private String contractCode;		//合同编号
    private Date inboundTime;			//入库时间
    private String inboundCode;		//入库单号
    private String recipientName;		//收货人
    private Date outboundTime;		//出库时间
    private int stockState;					//库存状态
    private String outboundCode;	//出库单号
    private String vendor;					//厂商
    private String productCode;		//产品编码
    private String model;					//型号
    private String productDescribe;	//产品描述
    private int stockNum;					//库存量
    private String storePlace;			//存放地点
    private String pono;						//批次
    private String note;						//备注
    
    private String productSn; //序列号
    
    public StockModel(){}
	public StockModel(String orderCode, String contractCode, Date inboundTime,String inboundCode, String pono,
			String recipientName, Date outboundTime, String outboundCode, String vendor, String productCode,
			String model, String productDescribe, int stockState,int stockNum, String storePlace, String note) {
		super();
		this.orderCode = orderCode;
		this.contractCode = contractCode;
		this.inboundTime = inboundTime;
		this.inboundCode = inboundCode;
		this.pono = pono;
		this.recipientName = recipientName;
		this.outboundTime = outboundTime;
		this.outboundCode = outboundCode;
		this.vendor = vendor;
		this.productCode = productCode;
		this.model = model;
		this.productDescribe = productDescribe;
		this.stockState = stockState;
		this.stockNum = stockNum;
		this.storePlace = storePlace;
		this.note = note;
	}
	
	@Column(name = "orderCode")
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	
	@Column(name = "contractCode")
	public String getContractCode() {
		return contractCode;
	}
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name = "inboundTime")
	public Date getInboundTime() {
		return inboundTime;
	}
	public void setInboundTime(Date inboundTime) {
		this.inboundTime = inboundTime;
	}
	
	@Column(name = "inboundCode")
	public String getInboundCode() {
		return inboundCode;
	}
	public void setInboundCode(String inboundCode) {
		this.inboundCode = inboundCode;
	}
	
	@Column(name = "recipientName")
	public String getRecipientName() {
		return recipientName;
	}
	public void setRecipientName(String recipientName) {
		this.recipientName = recipientName;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name = "outboundTime")
	public Date getOutboundTime() {
		return outboundTime;
	}
	public void setOutboundTime(Date outboundTime) {
		this.outboundTime = outboundTime;
	}
	
	@Column(name = "stockState")
	public int getStockState() {
		return stockState;
	}
	public void setStockState(int stockState) {
		this.stockState = stockState;
	}
	
	@Column(name = "outboundCode")
	public String getOutboundCode() {
		return outboundCode;
	}
	public void setOutboundCode(String outboundCode) {
		this.outboundCode = outboundCode;
	}
	
	@Column(name = "vendor")
	public String getVendor() {
		return vendor;
	}
	public void setVendor(String vendor) {
		this.vendor = vendor;
	}
	
	@Column(name = "productCode")
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	
	@Column(name = "model")
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	
	@Column(name = "productDescribe")
	public String getProductDescribe() {
		return productDescribe;
	}
	public void setProductDescribe(String productDescribe) {
		this.productDescribe = productDescribe;
	}
	
	@Column(name = "stockNum")
	public int getStockNum() {
		return stockNum;
	}
	public void setStockNum(int stockNum) {
		this.stockNum = stockNum;
	}
	
	@Column(name = "storePlace")
	public String getStorePlace() {
		return storePlace;
	}
	public void setStorePlace(String storePlace) {
		this.storePlace = storePlace;
	}
	
	@Column(name = "pono")
	public String getPono() {
		return pono;
	}
	public void setPono(String pono) {
		this.pono = pono;
	}
	
	@Column(name = "note")
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
 @Column(name = "ProductSn")
    public String getProductSn() {
        return productSn;
    }
    public void setProductSn(String productSn) {
        this.productSn = productSn;
    }
	
	
    
}