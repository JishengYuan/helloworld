package com.sinobridge.eoss.business.stock.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.sinobridge.base.core.model.imp.DefaultBaseModel;
import com.sinobridge.base.core.utils.CustomDateSerializer;

/**
 * 入库单表
 */
@Entity
@Table(name = "business_inbound")
public class InboundModel extends DefaultBaseModel {

    private static final long serialVersionUID = 1L;
    
    public static final String INBOUNDCODE = "inboundCode";
    public static final String ID = "id";
    public static final String STATE = "state";

    private Date inboundTime; //入库日期
    private String inboundCode; //入库单号
    //    private Integer buyType; //采购分类
    //    private Integer supplierType;//厂商类型
    //    private String supplierName; //厂商简称
    //    private Integer orderType; //订单类型
    //    private String orderCode; //订单编号
    //    private Integer inboundType;//入库类型
    //    private Integer inboundState;//入库状态
    private String productCode; //产品编码
    private String storePlace; //所入库房
    private Integer productNum;//产品数量
    private String recipientName;//收货人
    private String note; //备注
    
    /**
     * short state :
     * 1 到库存表  0 未到库存表       
     * @since  2014年10月11日 guokemenng
     */
    private short state;

    public InboundModel() {
    }

    @JsonSerialize(using = CustomDateSerializer.class)
    @Column(name = "InboundTime")
    public Date getInboundTime() {
        return inboundTime;
    }

    public void setInboundTime(Date inboundTime) {
        this.inboundTime = inboundTime;
    }

    @Column(name = "InboundCode")
    public String getInboundCode() {
        return inboundCode;
    }

    public void setInboundCode(String inboundCode) {
        this.inboundCode = inboundCode;
    }

    //    @Column(name = "BuyType")
    //    public Integer getBuyType() {
    //        return buyType;
    //    }
    //
    //    public void setBuyType(Integer buyType) {
    //        this.buyType = buyType;
    //    }
    //
    //    @Column(name = "SupplierType")
    //    public Integer getSupplierType() {
    //        return supplierType;
    //    }
    //
    //    public void setSupplierType(Integer supplierType) {
    //        this.supplierType = supplierType;
    //    }
    //
    //    @Column(name = "SupplierName")
    //    public String getSupplierName() {
    //        return supplierName;
    //    }
    //
    //    public void setSupplierName(String supplierName) {
    //        this.supplierName = supplierName;
    //    }
    //
    //    @Column(name = "OrderType")
    //    public Integer getOrderType() {
    //        return orderType;
    //    }
    //
    //    public void setOrderType(Integer orderType) {
    //        this.orderType = orderType;
    //    }
    //
    //    @Column(name = "OrderCode")
    //    public String getOrderCode() {
    //        return orderCode;
    //    }
    //
    //    public void setOrderCode(String orderCode) {
    //        this.orderCode = orderCode;
    //    }
    //
    //    @Column(name = "InboundType")
    //    public Integer getInboundType() {
    //        return inboundType;
    //    }
    //
    //    public void setInboundType(Integer inboundType) {
    //        this.inboundType = inboundType;
    //    }
    //
    //    @Column(name = "InboundState")
    //    public Integer getInboundState() {
    //        return inboundState;
    //    }
    //
    //    public void setInboundState(Integer inboundState) {
    //        this.inboundState = inboundState;
    //    }

    @Column(name = "RecipientName")
    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    @Column(name = "Note")
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    /**
     * @return the productCode
     */
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

    /**
     * @return the storePlace
     */
    @Column(name = "StorePlace")
    public String getStorePlace() {
        return storePlace;
    }

    /**
     * @param storePlace the storePlace to set
     */
    public void setStorePlace(String storePlace) {
        this.storePlace = storePlace;
    }

    /**
     * @return the productNum
     */
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

    @Column(name = "State")
    public short getState() {
        return state;
    }
    public void setState(short state) {
        this.state = state;
    }
    
}