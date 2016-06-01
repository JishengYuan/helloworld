package com.sinobridge.eoss.business.order.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinobridge.base.core.service.DefaultBaseService;
import com.sinobridge.eoss.business.order.dao.BusinessReimbursementDao;
import com.sinobridge.eoss.business.order.model.BusinessReimbursementModel;

@Service
@Transactional
public class BusinessReimbursementService extends DefaultBaseService<BusinessReimbursementModel, BusinessReimbursementDao> {


    /**
     * <code>deletePlan</code>
     *
     * @param 删除原有的计划
     * @since   2014年12月16日    wangya
     */
    public void deletePlan(Long id) {
        getDao().deletePlan(id);
    }

}
