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
 * @author  guokemenng
 * @since 1.0  2013-11-18
 */
public class Tree {

    /** 
     * String id :       
     * @since  2013-11-18 guokemenng
     */
    private String id;
    /** 
     * String text :  
     * 显示名称     
     * @since  2013-11-18 guokemenng
     */
    private String text;

    /**
     * 下拉树显示名称
     */
    private String way;
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
    private List<Tree> children = new ArrayList<Tree>(0);

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

    /**
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * @return the way
     */
    public String getWay() {
        return way;
    }

    /**
     * @param way the way to set
     */
    public void setWay(String way) {
        this.way = way;
    }

    /**
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * @return the children
     */
    public List<Tree> getChildren() {
        return children;
    }

    public void setChildren(List<Tree> children) {
        this.children = children;
    }

}
