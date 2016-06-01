/*
 * FileName: BasePartnerInfo.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.business.projectmanage.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.sinobridge.base.core.dao.imp.DefaultBaseDao;
import com.sinobridge.eoss.business.projectmanage.model.BasePartnerInfo;
import com.sinobridge.eoss.business.projectmanage.model.BasePartnerProduct;
import com.sinobridge.eoss.business.projectmanage.model.BaseProductType;

/**
 * <code>BasePartnerInfo</code>
 * 
 * @version  1.0
 * @author  guokemenng
 * @since 1.0  2013-11-25
 */
@Repository
public class BasePartnerInfoDao extends DefaultBaseDao<BasePartnerInfo, String> {

    /**
     * @param baseProductType
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<BasePartnerInfo> getPartnerInfoByProductTypeId(BaseProductType baseProductType) {
        //String hql = "from BasePartnerProduct s where  s.productTypeId in (select p.id from BaseProductType p  where p.typeCode like '" + baseProductType.getTypeCode() + "%') group by s.basePartnerInfo.id";
        String hql = "from BasePartnerProduct s where  s.productTypeId = '"+baseProductType.getId()+"'";
        List<BasePartnerInfo> basePartnerInfos = new ArrayList<BasePartnerInfo>();
        List<BasePartnerProduct> list = this.getHibernateTemplate().find(hql);
        for (BasePartnerProduct basePartnerProduct : list) {
            basePartnerInfos.add(basePartnerProduct.getBasePartnerInfo());
        }
        return basePartnerInfos;
    }
}
