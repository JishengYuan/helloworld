/*
 * FileName: QualificationChapterInfoService.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.sales.contractfounds.service;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinobridge.base.core.service.DefaultBaseService;
import com.sinobridge.eoss.sales.contractfounds.dao.QualificationChapterInfoDao;
import com.sinobridge.eoss.sales.contractfounds.model.QualificationChapterInfoModel;

/**
 * <p>
 * Description: 
 * </p>
 *
 * @author vermouth
 * @version 1.0

 * <p>
 * History: 
 *
 * Date                     Author         Version     Description
 * ---------------------------------------------------------------------------------
 * 2015年12月16日 下午3:21:10          vermouth        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Service
@Transactional
public class QualificationChapterInfoService extends DefaultBaseService<QualificationChapterInfoModel, QualificationChapterInfoDao> {

    public List<QualificationChapterInfoModel> getAllordertype(String val) {
        String hql = "from QualificationChapterInfoModel where qcName like '%" + val + "%'order by qcType,id asc";
        Query query = getDao().createQuery(hql);
        List<QualificationChapterInfoModel> rs = query.list();

        String sql = "SELECT applyFoundsID,chapterId ,creatorName,realborrowDate FROM sales_contractchapter WHERE realborrowDate IS NOT NULL AND realReturnDate IS NULL " + " UNION ALL " + "SELECT applyFoundsID,qualificationid chapterId,creatorName,realborrowDate FROM sales_contractqualification WHERE realborrowDate IS NOT NULL AND realReturnDate IS NULL";
        Query query1 = getDao().createSQLQuery(sql);
        List<Map<String, Object>> rs1 = query1.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        for (QualificationChapterInfoModel qualificationChapterInfoModel : rs) {
            for (Map<String, Object> map : rs1) {
                if (qualificationChapterInfoModel.getId() == ((BigInteger) map.get("chapterId")).longValue()) {
                    qualificationChapterInfoModel.setUsedesc("借出人：" + map.get("creatorName") + "  " + map.get("realborrowDate"));
                }
            }
        }

        return rs;
    }

}
