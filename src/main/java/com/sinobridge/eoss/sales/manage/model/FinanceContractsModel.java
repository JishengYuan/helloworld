package com.sinobridge.eoss.sales.manage.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.sinobridge.base.core.model.imp.DefaultBaseModel;

/**
 * <p>
 * Description: 
 * </p>
 *
 * @author guo_kemeng
 * @version 1.0

 * <p>
 * History: 
 *
 * Date                     Author         Version     Description
 * ---------------------------------------------------------------------------------
 * 2015年1月16日 上午10:39:33          guo_kemeng        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Entity
@Table(name = "sales_finance_contract")
public class FinanceContractsModel extends DefaultBaseModel {

    /**
     * long serialVersionUID :       
     * @since  2015年1月16日 guokemenng
     */
    private static final long serialVersionUID = 4047417105316954078L;
    private Long orderId;
    private String contractCode;
    private BigDecimal contractAmount;
    private String invoiceAmount;//认证金额
    private String receiveAmount;//已付款金额
    private String uninvoiceAmount;
    private String unreceiveAmount;

    @Column(name = "InvoiceAmount", length = 64)
    public String getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(String invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    @Column(name = "ReceiveAmount", length = 64)
    public String getReceiveAmount() {
        return receiveAmount;
    }

    public void setReceiveAmount(String receiveAmount) {
        this.receiveAmount = receiveAmount;
    }

    @Column(name = "UninvoiceAmount", length = 64)
    public String getUninvoiceAmount() {
        return uninvoiceAmount;
    }

    public void setUninvoiceAmount(String uninvoiceAmount) {
        this.uninvoiceAmount = uninvoiceAmount;
    }

    @Column(name = "UnreceiveAmount", length = 64)
    public String getUnreceiveAmount() {
        return unreceiveAmount;
    }

    public void setUnreceiveAmount(String unreceiveAmount) {
        this.unreceiveAmount = unreceiveAmount;
    }

    @Column(name = "ContractCode", length = 32)
    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }

    @Column(name = "ContractAmount", precision = 11, scale = 1)
    public BigDecimal getContractAmount() {
        return contractAmount;
    }

    public void setContractAmount(BigDecimal contractAmount) {
        this.contractAmount = contractAmount;
    }

    @Column(name = "OrderId", length = 20)
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

}
