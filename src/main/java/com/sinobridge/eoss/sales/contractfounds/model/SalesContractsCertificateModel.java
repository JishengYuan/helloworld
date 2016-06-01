/*
 * FileName: SaleContractsCertificateModel.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.sales.contractfounds.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.sinobridge.base.core.model.imp.DefaultBaseModel;

/**
 * <p>
 * Description: 
 * </p>
 *
 * @author liyx
 * @version 1.0

 * <p>
 * History: 
 *
 * Date                     Author         Version     Description
 * ---------------------------------------------------------------------------------
 * 2015年12月1日 下午4:45:42      liyx           1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Entity
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "salesContractFoundModel" })
@Table(name = "sale_contractsCertificate")
public class SalesContractsCertificateModel extends DefaultBaseModel {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private SalesContractFoundModel salesContractFoundModel;
    private Long certificateType;
    private String certificateName;
    private int certificateNum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "applyFoundsID")
    public SalesContractFoundModel getSalesContractFoundModel() {
        return salesContractFoundModel;
    }

    public void setSalesContractFoundModel(SalesContractFoundModel salesContractFoundModel) {
        this.salesContractFoundModel = salesContractFoundModel;
    }

    @Column(name = "certificateType", length = 20)
    public Long getCertificateType() {
        return certificateType;
    }

    public void setCertificateType(Long certificateType) {
        this.certificateType = certificateType;
    }

    @Column(name = "certificateNum")
    public int getCertificateNum() {
        return certificateNum;
    }

    public void setCertificateNum(int certificateNum) {
        this.certificateNum = certificateNum;
    }

    /**
     * @return the certificateName
     */
    @Transient
    public String getCertificateName() {
        return certificateName;
    }

    /**
     * @param certificateName the certificateName to set
     */
    public void setCertificateName(String certificateName) {
        this.certificateName = certificateName;
    }

}
