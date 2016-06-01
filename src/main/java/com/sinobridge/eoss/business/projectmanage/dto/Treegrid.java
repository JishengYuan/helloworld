/*
  * FileName: Treegrid.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.business.projectmanage.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * <code>Treegrid</code>
 * 
 * @version  1.0
 * @author  guokemenng
 * @since 1.0  2013-11-8
 */
public class Treegrid {

    /** 
     * String id :       
     * 树的ID
     * @since  2013年10月17日 guokemenng
     */
    private String id;
    /** 
     * String name :
     * 树的名称       
     * @since  2013年10月17日 guokemenng
     */
    private String name;
    /** 
     * String state :
     * 树的状态       
     * @since  2013年10月17日 guokemenng
     */
    private String state;
    /** 
     * List<Tree> children :
     * 树的子对象集合       
     * @since  2013年10月17日 guokemenng
     */
    private List<Treegrid> children = new ArrayList<Treegrid>(0);
    
    /** 
     * String productLineDesc :    
     * 系列描述   
     * @since  2013-11-29 guokemenng
     */
    private String productLineDesc;
    private String partnerName;
    private String brandName;
    private String productTypeName;

    //设备类型表 属性
    private Short isModule;
    private Short isLogic;
    private String icon;
    private String bgImage;
    private Integer orderValue;
    private String typeCode;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    
    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<Treegrid> getChildren() {
        return children;
    }

    public void setChildren(List<Treegrid> childList) {
        this.children = childList;
    }

    /**
     * @return the isModule
     */
    public Short getIsModule() {
        return isModule;
    }

    /**
     * @param isModule the isModule to set
     */
    public void setIsModule(Short isModule) {
        this.isModule = isModule;
    }

    /**
     * @return the isLogic
     */
    public Short getIsLogic() {
        return isLogic;
    }

    /**
     * @param isLogic the isLogic to set
     */
    public void setIsLogic(Short isLogic) {
        this.isLogic = isLogic;
    }

    /**
     * @return the icon
     */
    public String getIcon() {
        return icon;
    }

    /**
     * @param icon the icon to set
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getBgImage() {
        return bgImage;
    }

    public void setBgImage(String bgImage) {
        this.bgImage = bgImage;
    }

    public Integer getOrderValue() {
        return orderValue;
    }

    public void setOrderValue(Integer orderValue) {
        this.orderValue = orderValue;
    }

    public String getProductLineDesc() {
        return productLineDesc;
    }

    public void setProductLineDesc(String productLineDesc) {
        this.productLineDesc = productLineDesc;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

	public String getProductTypeName() {
		return productTypeName;
	}

	public void setProductTypeName(String productTypeName) {
		this.productTypeName = productTypeName;
	}

    
}
