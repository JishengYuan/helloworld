/*
 * FileName: BasePartnerProductService.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.business.projectmanage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinobridge.base.core.service.DefaultBaseService;
import com.sinobridge.eoss.business.projectmanage.dao.BasePartnerProductDao;
import com.sinobridge.eoss.business.projectmanage.model.BasePartnerProduct;

/**
 * <code>BasePartnerProductService</code>
 * 
 * @version  1.0
 * @author  guokemenng
 * @since 1.0  2013-11-25
 */
@Service
@Transactional
public class BasePartnerProductService extends DefaultBaseService<BasePartnerProduct, BasePartnerProductDao> {

    @Autowired
    protected BasePartnerProductDao basePartnerProductDao;

    /**
     * @param partnerId
     * @param typeId
     */
    public void delete(String partnerId, String typeId) {
        String[] param = new String[2];
        param[0] = partnerId;
        param[1] = typeId;
        String hql = "delete BasePartnerProduct s where s.basePartnerInfo.id = ? and s.productTypeId = ?";
        this.basePartnerProductDao.createQuery(hql, param).executeUpdate();
        //this.basePartnerProductDao.createQuery("delete BasePartnerProduct s where s.basePartnerInfo.id = '"+partnerId+"' and s.productTypeId = '"+typeId+"'").executeUpdate();
    }

    public void delete(String partnerId) {
        String[] param = new String[1];
        param[0] = partnerId;
        String hql = "delete BasePartnerProduct s where s.basePartnerInfo.id = ?";
        this.basePartnerProductDao.createQuery(hql, param).executeUpdate();
        //this.basePartnerProductDao.createQuery("delete BasePartnerProduct s where s.basePartnerInfo.id = '"+partnerId+"' and s.productTypeId = '"+typeId+"'").executeUpdate();
    }

}
