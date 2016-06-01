/*
 * FileName: DataSet.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.tools;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Description: 
 * </p>
 *
 * @author tigq
 * @version 1.0

 * <p>
 * History: 
 *
 * Date                     Author         Version     Description
 * ---------------------------------------------------------------------------------
 * 2014年10月6日 上午9:12:22          tigq        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */

public class DataSet {
    private String[] headers;
    private String[] rowHeaders;
    private List<String[]> datasList = new ArrayList<String[]>();
    private List<String> conStrctSet;

    public DataSet(String[] header, String[] rowsHeader, List<String[]> datasList2) {
        this.headers = header;
        this.rowHeaders = rowsHeader;
        this.datasList = datasList2;
    }

    public DataSet(String[] headers, String[] rowHeaders, List<String[]> datasList, List<String> conStrctSet) {
        this.headers = headers;
        this.rowHeaders = rowHeaders;
        this.datasList = datasList;
        this.conStrctSet = conStrctSet;
    }

    public List<String> getConStrctSet() {
        return conStrctSet;
    }

    public List<String[]> getDatasList() {
        return datasList;
    }

    public String[] getHeaders() {
        return headers;
    }

    public String[] getRowHeaders() {
        return rowHeaders;
    }

    public void setConStrctSet(List<String> conStrctSet) {
        this.conStrctSet = conStrctSet;
    }

    public void setDatasList(List<String[]> datasList) {
        this.datasList = datasList;
    }

    public void setHeaders(String[] headers) {
        this.headers = headers;
    }

    public void setRowHeaders(String[] rowHeaders) {
        this.rowHeaders = rowHeaders;
    }
}
