/*
 * FileName: SalesContractFoundDao.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.sales.contractfounds.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.sinobridge.base.core.dao.imp.DefaultBaseDao;
import com.sinobridge.base.core.utils.PaginationSupport;
import com.sinobridge.eoss.sales.contractfounds.model.SalesContractFoundModel;

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
 * 2015年12月1日 下午4:57:22      liyx           1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Repository
public class SalesContractFoundDao extends DefaultBaseDao<SalesContractFoundModel, Long> {

    /**
     * @param searchMap
     * @param startIndex
     * @param pageSize
     * @return
     */
    @SuppressWarnings("unchecked")
    public PaginationSupport findFoundList(HashMap<String, Object> searchMap, int startIndex, Integer pageSize) {
        Object contractName = searchMap.get("contractName");
        Object contractAmount = searchMap.get("contractAmount");
        Object contractAmountb = searchMap.get("contractAmountb");
        Object creator = searchMap.get("creator");

        StringBuffer sb = new StringBuffer();
        sb.append("SELECT s.id,s.`applyFundsName`,s.`applyFundsState`,s.`CreatorName`,s.`payTime`, st.`staffName`,i.`shortName`,s.`totalFunds` FROM `sales_contractfounds` s ");
        sb.append("LEFT JOIN bpm_process_inst bpi ON s.`processInstanceId`=bpi.ID_  ");
        sb.append("LEFT JOIN act_ru_task art ON bpi.HI_PROC_INST_ID= art.proc_inst_id_ ");
        sb.append("LEFT JOIN sys_staff st ON st.`UserName`=art.`ASSIGNEE_` ");
        sb.append("LEFT JOIN `customer_info` i ON i.`id`=s.`cusCustomerId` WHERE 0=0 ");
        if (contractName != null) {
            sb.append("and s.applyFundsName like '%" + contractName + "%' ");
        }
        if (contractAmount != null) {
            sb.append("and s.`totalFunds` >='" + contractAmount + "' ");
        }
        if (contractAmountb != null) {
            sb.append("and s.`totalFunds` <'" + contractAmountb + "' ");
        }
        if (creator != null) {
            sb.append("and s.`creator` = '" + creator + "' ");
        }
        sb.append("ORDER BY s.`ID` DESC ");
        int totals = getTotalCount(sb.toString());
        Query query = createSQLQuery(sb.toString());
        List<Object> items = query.setFirstResult(startIndex).setMaxResults(pageSize).list();
        PaginationSupport ps = new PaginationSupport(items, totals, pageSize, startIndex);
        List<Object> rows = (List<Object>) ps.getItems();
        List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < rows.size(); i++) {
            Object[] entity = (Object[]) rows.get(i);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("ID", entity[0].toString());
            map.put("applyFundsName", entity[1].toString());
            map.put("applyFundsState", entity[2] == null ? "" : entity[2].toString());
            map.put("CreatorName", entity[3] == null ? "" : entity[3].toString());
            map.put("payTime", entity[4] == null ? "" : entity[4].toString());
            map.put("staffName", entity[5] == null ? "" : entity[5].toString());
            map.put("shortName", entity[6] == null ? "" : entity[6].toString());
            map.put("totalFunds", entity[7] == null ? "" : entity[7].toString());
            listMap.add(map);
        }
        ps.setItems(listMap);
        return ps;
    }

    /**
     * <code>getTotalCount</code>
     * 得到总条数
     * @param sql
     * @param params
     * @return
     * @since   2014年11月24日    guokemenng
     */
    @SuppressWarnings("rawtypes")
    public Integer getTotalCount(String sql) {
        String countQueryString = " select COUNT(*) from (" + sql + ") t";
        List countlist = createSQLQuery(countQueryString).list();
        return Integer.parseInt(countlist.get(0).toString());
    }

    /**
     * @param searchMap
     * @param startIndex
     * @param pageSize
     * @return
     */
    @SuppressWarnings("unchecked")
    public PaginationSupport findBiddingList(HashMap<String, Object> searchMap, int startIndex, Integer pageSize) {
        Object contractName = searchMap.get("contractName");
        Object contractAmount = searchMap.get("contractAmount");
        Object contractAmountb = searchMap.get("contractAmountb");
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT s.`ID`,s.`applyFundsName`,s.`CreatorName`,s.`totalFunds`,s.`payTime`,i.`ShortName` FROM `sales_contractfounds` s,`customer_info` i ");
        sb.append("WHERE s.`cusCustomerId`=i.id AND s.`applyFundsState`='TGSP' ");
        sb.append("AND s.`ContractCode` IS NULL AND s.returnPrice IS NULL ");
        if (contractName != null) {
            sb.append("and s.applyFundsName like '%" + contractName + "%' ");
        }
        if (contractAmount != null) {
            sb.append("and s.`totalFunds` >='" + contractAmount + "' ");
        }
        if (contractAmountb != null) {
            sb.append("and s.`totalFunds` <'" + contractAmountb + "' ");
        }
        sb.append("ORDER BY s.`ID` DESC ");
        int totals = getTotalCount(sb.toString());
        Query query = createSQLQuery(sb.toString());
        List<Object> items = query.setFirstResult(startIndex).setMaxResults(pageSize).list();
        PaginationSupport ps = new PaginationSupport(items, totals, pageSize, startIndex);
        List<Object> rows = (List<Object>) ps.getItems();
        List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < rows.size(); i++) {
            Object[] entity = (Object[]) rows.get(i);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("ID", entity[0].toString());
            map.put("applyFundsName", entity[1].toString());
            map.put("CreatorName", entity[2] == null ? "" : entity[2].toString());
            map.put("totalFunds", entity[3] == null ? "" : entity[3].toString());
            map.put("payTime", entity[4] == null ? "" : entity[4].toString());
            map.put("ShortName", entity[5] == null ? "" : entity[5].toString());
            listMap.add(map);
        }
        ps.setItems(listMap);
        return ps;
    }

    /*public void saveAll(SalesContractFoundModel foundModel, List<SalesContractChapterModel> chapterList, List<SalesContractQualificationModel> ficationList, List<SalesContractsCertificateModel> certificateList, List<SalesContractSizeModel> sizeList) {
    /**
     * 获得没有退回保证金的投标单
     * @param systemUser
     * @param cuscustomerId
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<SalesContractFoundModel> getNotReturnPrice(String creator, Long cuscustomerId) {
        StringBuilder sb = new StringBuilder();
        sb.append("select sc.* from sales_contractfounds sc where creator='" + creator + "' and cuscustomerId=" + cuscustomerId);
        sb.append(" and payTime is not NULL and (contractCode is NULL or returnPrice is not NUll)");

        Query query = createSQLQuery(sb.toString()).addEntity(SalesContractFoundModel.class);
        return query.list();
    }

    /**
     * @param type
     * @return
     */
    public List<Map<String, Object>> findSalesType(String type) {
        String sql = "SELECT s.`ID` id,s.`qcName` name FROM `sales_qualificationchapterinfo` s WHERE s.`qcType`='" + type + "' ";
        List<Map<String, Object>> list = this.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        return list;
    }

}
