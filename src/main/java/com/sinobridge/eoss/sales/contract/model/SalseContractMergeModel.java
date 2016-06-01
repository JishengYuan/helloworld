/*
 * FileName: SalseContractMergeModel.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.sales.contract.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
 * 2015年3月23日 下午2:10:35          guo_kemeng        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Entity
@Table(name = "sales_contractmerge_apply")
public class SalseContractMergeModel extends DefaultBaseModel{

    /**
     * long serialVersionUID :       
     * @since  2015年3月23日 guokemenng
     */
    private static final long serialVersionUID = 1L;
    private String name;
    private Date applyTime;
    private String contractIds;
    private String remark;
    //状态  0,1,2--- 不同意，提交，同意
    private short state;
    
    private String empoyeeId;
    
    //主合同
    private Long contractId;
    
    @Column(name = "Name", length = 36)
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
    @Temporal(TemporalType.DATE)
    @Column(name = "ApplyTime")
    public Date getApplyTime() {
        return applyTime;
    }
    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }
    
    @Column(name = "ContractIds", length = 255)
    public String getContractIds() {
        return contractIds;
    }
    public void setContractIds(String contractIds) {
        this.contractIds = contractIds;
    }
    
    @Column(name = "Remark", length = 128)
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    @Column(name = "State")
    public short getState() {
        return state;
    }
    public void setState(short state) {
        this.state = state;
    }
    
    @Column(name = "EmpoyeeId")
    public String getEmpoyeeId() {
        return empoyeeId;
    }
    public void setEmpoyeeId(String empoyeeId) {
        this.empoyeeId = empoyeeId;
    }
    
    @Column(name = "ContractId")
    public Long getContractId() {
        return contractId;
    }
    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }
    

}
