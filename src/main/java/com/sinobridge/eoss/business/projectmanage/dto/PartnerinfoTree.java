/*
  * FileName: Tree.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.business.projectmanage.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * <code>Tree</code>
 * 
 * @version  1.0
 * @author  sunwukong
 * @since 1.0  2013-11-18
 */
public class PartnerinfoTree {

    /** 
     * String id :       
     * @since  2013-11-18 sunwukong
     */
    private String id;
    /** 
     * String name :  
     * 显示名称     
     * @since  2013-11-18 sunwukong
     */
    private String name;

    /** 
     * List<Tree> children :
     * 树的子对象集合       
     * @since  2013年10月17日 sunwukong
     */
    private List<PartnerinfoTree> children = new ArrayList<PartnerinfoTree>();

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }


    public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	/**
     * @return the children
     */
    public List<PartnerinfoTree> getChildren() {
        return children;
    }

    public void setChildren(List<PartnerinfoTree> children) {
        this.children = children;
    }

}
