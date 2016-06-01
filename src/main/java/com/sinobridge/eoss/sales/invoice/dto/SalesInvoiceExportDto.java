/*
 * FileName: SalesInvoiceExportDto.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.sales.invoice.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * Description: 导出发票信息到Excel表格中的数据传输对象
 * </p>
 *
 * @author Jack
 * @version 1.0

 * <p>
 * History:
 *
 * Date                     Author         Version     Description
 * ---------------------------------------------------------------------------------
 * 2016年5月23日 下午3:21:49          Jack        1.0         To create
 * </p>
 *
 * @since
 * @see
 */
public class SalesInvoiceExportDto {
    private Long id;

    //合同编码
    private String contractCode;

    //合同名称
    private String contractName;

    //合同金额
    private BigDecimal contractAmount;

    //已开发票金额
    private BigDecimal alreadyAmount;

    //本次开发票金额
    private BigDecimal invoiceAmount;

    //所在部门
    private String deptName;

    //客户经理
    private String creatorName;

    //开发票日期
    private Date createTime;

    /**
     * @return the contractCode
     */
    public String getContractCode() {
        return contractCode;
    }

    /**
     * @param contractCode the contractCode to set
     */
    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }

    /**
     * @return the deptName
     */
    public String getDeptName() {
        return deptName;
    }

    /**
     * @param deptName the deptName to set
     */
    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    /**
     * @return the contractName
     */
    public String getContractName() {
        return contractName;
    }

    /**
     * @param contractName the contractName to set
     */
    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    /**
     * @return the alreadyAmount
     */
    public BigDecimal getAlreadyAmount() {
        return alreadyAmount;
    }

    /**
     * @param alreadyAmount the alreadyAmount to set
     */
    public void setAlreadyAmount(BigDecimal alreadyAmount) {
        this.alreadyAmount = alreadyAmount;
    }

    /**
     * @return the contractAmount
     */
    public BigDecimal getContractAmount() {
        return contractAmount;
    }

    /**
     * @param contractAmount the contractAmount to set
     */
    public void setContractAmount(BigDecimal contractAmount) {
        this.contractAmount = contractAmount;
    }

    /**
     * @return the invoiceAmount
     */
    public BigDecimal getInvoiceAmount() {
        return invoiceAmount;
    }

    /**
     * @param invoiceAmount the invoiceAmount to set
     */
    public void setInvoiceAmount(BigDecimal invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    /**
     * @return the creatorName
     */
    public String getCreatorName() {
        return creatorName;
    }

    /**
     * @param creatorName the creatorName to set
     */
    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    /**
     * @return the createTime
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime the createTime to set
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

}
