/*
 * FileName: SupplierReturnDto.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.business.suppliermanage.dto;

/**
 * <code>SupplierReturnDto</code>
 * 
 * @version  1.0
 * @author  guokemenng
 * @since 1.0  2014年5月26日
 */
public class SupplierReturnDto {

    private Object id;
    private Object returnAmount;
    private Object supplierCode;
    private Object shortName;
    public Object getId() {
        return id;
    }
    public void setId(Object id) {
        this.id = id;
    }
    public Object getReturnAmount() {
        return returnAmount;
    }
    public void setReturnAmount(Object returnAmount) {
        this.returnAmount = returnAmount;
    }
    public Object getSupplierCode() {
        return supplierCode;
    }
    public void setSupplierCode(Object supplierCode) {
        this.supplierCode = supplierCode;
    }
    public Object getShortName() {
        return shortName;
    }
    public void setShortName(Object shortName) {
        this.shortName = shortName;
    }
    
}
