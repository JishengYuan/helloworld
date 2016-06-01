/*
 * FileName: BaseProductType.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.business.projectmanage.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.sinobridge.eoss.business.projectmanage.BaseConstants;

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
 * 2013-11-25 下午03:53:16          guo_kemeng        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Entity
@Table(name = BaseConstants.BASE_PRODUCTTYPE)
public class BaseProductType implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    // Fields    

    public static final String PARENTID = "parentId";
    public static final String ORDERVALUE = "orderValue";
    public static final String TYPENAME = "typeName";
    public static final String DELETEFLAG = "deleteFlag";
    public static final String TYPECODE = "typeCode";
    private String id;
    private String typeCode;
    private String typeName;
    private String parentId;
    private Short isModule;
    private Short isLogic;
    private String icon;
    private String bgImage;
    private Integer orderValue;
    private Short deleteFlag;

    private List<BaseProductType> children = new ArrayList<BaseProductType>();

    @Id
    @Column(name = "Id", unique = true, nullable = false, length = 36)
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "TypeCode", length = 20)
    public String getTypeCode() {
        return this.typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    @Column(name = "TypeName", length = 36)
    public String getTypeName() {
        return this.typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Column(name = "ParentId", length = 36)
    public String getParentId() {
        return this.parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    @Column(name = "IsModule")
    public Short getIsModule() {
        return this.isModule;
    }

    public void setIsModule(Short isModule) {
        this.isModule = isModule;
    }

    @Column(name = "IsLogic")
    public Short getIsLogic() {
        return this.isLogic;
    }

    public void setIsLogic(Short isLogic) {
        this.isLogic = isLogic;
    }

    @Column(name = "Icon", length = 128)
    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Column(name = "BgImage", length = 128)
    public String getBgImage() {
        return this.bgImage;
    }

    public void setBgImage(String bgImage) {
        this.bgImage = bgImage;
    }

    @Column(name = "OrderValue")
    public Integer getOrderValue() {
        return this.orderValue;
    }

    public void setOrderValue(Integer orderValue) {
        this.orderValue = orderValue;
    }

    @Column(name = "DeleteFlag", length = 1)
    public Short getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Short deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, mappedBy = "parentId")
    public List<BaseProductType> getChildren() {
        return children;

    }

    public void setChildren(List<BaseProductType> children) {
        this.children = children;
    }

}
