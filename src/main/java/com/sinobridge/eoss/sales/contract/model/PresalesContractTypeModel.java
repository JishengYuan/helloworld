/*
 * FileName: PresalesContractType.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.sales.contract.model;

import java.util.Date;

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
 * 2015年11月11日 上午9:24:10          guo_kemeng        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Entity
@Table(name = "sales_precontract_type")
public class PresalesContractTypeModel extends DefaultBaseModel{

    /**
     * long serialVersionUID :       
     * @since  2015年11月11日 guokemenng
     */
    private static final long serialVersionUID = -9051111582822706853L;
    
    //1 代表厂商 2 代表产品技术  3 代表客户类型
    private Short type;
    private String name;
    private Date createTime;
    
    public Short getType() {
        return type;
    }
    public void setType(Short type) {
        this.type = type;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
}
