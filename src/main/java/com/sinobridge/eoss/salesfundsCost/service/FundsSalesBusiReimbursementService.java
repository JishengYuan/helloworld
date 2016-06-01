/*
 * FileName: FundsSalesBusiReimbursementService.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.salesfundsCost.service;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinobridge.base.core.service.DefaultBaseService;
import com.sinobridge.eoss.salesfundsCost.dao.FundsSalesBusiReimbursementDao;
import com.sinobridge.eoss.salesfundsCost.model.FundsSalesBusiReimbursement;

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
 * 2016年1月2日 上午10:20:08          tigq        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Service
@Transactional
public class FundsSalesBusiReimbursementService extends DefaultBaseService<FundsSalesBusiReimbursement, FundsSalesBusiReimbursementDao> {

    /**
     * 得到已确认的商务发票
     * @param contractid
     * @return
     */
    public List<FundsSalesBusiReimbursement> getBusiReimbursement(Long contractid) {
        String hql = "from FundsSalesBusiReimbursement t where t.doState='1' and t.salesContractId=" + contractid;
        Query query = getDao().createQuery(hql);
        List<FundsSalesBusiReimbursement> rs = query.list();
        return rs;
    }

    /**
     * @param id
     * @return
     */
    public List<FundsSalesBusiReimbursement> getOrderPlanAmout(Long id) {
        String hql = "from FundsSalesBusiReimbursement t where t.doState='0' and t.salesContractId=" + id;
        Query query = getDao().createQuery(hql);
        List<FundsSalesBusiReimbursement> rs = query.list();
        return rs;
    }
}
