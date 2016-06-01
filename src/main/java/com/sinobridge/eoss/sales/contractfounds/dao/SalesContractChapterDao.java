/*
 * FileName: SalesContractChapterDao.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.sales.contractfounds.dao;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.sinobridge.base.core.dao.imp.DefaultBaseDao;
import com.sinobridge.eoss.sales.contract.utils.DateUtils;
import com.sinobridge.eoss.sales.contractfounds.model.SalesContractChapterModel;

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
 * 2015年12月1日 下午8:24:19      liyx           1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Repository
public class SalesContractChapterDao extends DefaultBaseDao<SalesContractChapterModel, Long> {

    //仅有日期的格式
    String DatePattern = "yyyy-MM-dd";

    /**
     * @param c
     * @return
     */
    @SuppressWarnings("unchecked")
    public String findChaperTime(SalesContractChapterModel c) {
        StringBuffer sb = new StringBuffer();
        String returnDate = "";
        String borrowDate = "";
        try {
            borrowDate = DateUtils.convertDateToString(c.getForecastBorrowDate(), DatePattern);
            returnDate = DateUtils.convertDateToString(c.getForecastReturnDate(), DatePattern);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        sb.append("SELECT c.`CreatorName`,s.`forecastBorrowDate`,s.`forecastReturnDate`,s.`chapterId` FROM sales_contractchapter s,`sales_contractfounds` c  WHERE s.`chapterId`='" + c.getChapterId() + "' ");
        sb.append("AND s.`applyFoundsID`=c.`ID` AND  s.`forecastBorrowDate`>='" + borrowDate + "' AND s.`forecastReturnDate`<='" + returnDate + "' ");
        List<Map<String, Object>> list = this.createSQLQuery(sb.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        String name = "";
        if (list.size() > 0) {
            for (int i = 0; i < list.size() - 1; i++) {
                String n = list.get(i).get("CreatorName").toString();
                if (name.indexOf(n) == -1) {
                    name += n + ",";
                }
            }
            String m = list.get(list.size() - 1).get("CreatorName").toString();
            if (name.indexOf(m) == -1) {
                name += m + ",";
            } else {
                name = name.substring(0, name.length() - 1);
            }
        }
        return name;
    }

    /**
     * @param foundId
     */
    public void deleteChapter(Long foundId) {
        Long[] param = new Long[1];
        param[0] = foundId;
        String sql = "delete from sales_contractchapter where applyFoundsID = ?";
        createSQLQuery(sql, param).executeUpdate();
    }

    /**查询章或资质是否都借出
     * @param id
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> findQualificationchapterinfo(Long id) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT * FROM sales_qualificationchapterinfo s WHERE s.ID='" + id + "' ");
        List<Map<String, Object>> list = createSQLQuery(sb.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        return list;
    }

    /**借出数量加1
     * @param id
     * @param borrow 
     * @param rem 
     */
    public void updateQualificationchapterinfo(Long id, int borrow, int remains) {
        String sql = "UPDATE sales_qualificationchapterinfo s SET s.`borrows`='" + borrow + "',s.remains='" + remains + "' WHERE s.`ID`='" + id + "'";
        createSQLQuery(sql).executeUpdate();
    }

    /**借出数量加1,状态置为1
     * @param id
     * @param borrow 
     */
    public void updateQualificationchapterinfoStatus(Long id, int borrow) {
        String sql = "UPDATE sales_qualificationchapterinfo s SET s.`borrows`='" + borrow + "',s.remains='0', s.`qcstatus`='1' WHERE s.`ID`='" + id + "'";
        createSQLQuery(sql).executeUpdate();
    }

    /**借出数量减1，状态置为0
     * @param id
     * @param borrows
     * @param remains 
     */
    public void updateQualificationinfoRed(Long id, int borrows, int remains) {
        String sql = "UPDATE sales_qualificationchapterinfo s SET s.`borrows`='" + borrows + "', s.remains='" + remains + "',s.`qcstatus`='0' WHERE s.`ID`='" + id + "'";
        createSQLQuery(sql).executeUpdate();
    }

}
