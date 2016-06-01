/*
 * FileName: SalesContractQualificationService.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.sales.contractfounds.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinobridge.base.core.service.DefaultBaseService;
import com.sinobridge.eoss.sales.contractfounds.dao.SalesContractQualificationDao;
import com.sinobridge.eoss.sales.contractfounds.model.QualificationChapterInfoModel;
import com.sinobridge.eoss.sales.contractfounds.model.SalesContractQualificationModel;

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
 * 2015年12月17日 下午4:06:32          tigq        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Service
@Transactional
public class SalesContractQualificationService extends DefaultBaseService<SalesContractQualificationModel, SalesContractQualificationDao> {

    @Autowired
    private SalesFundsoptLogService salesFundsoptLogService;

    @Autowired
    private QualificationChapterInfoService qualificationChapterInfoService;

    /**
     * 借出资质操作
     * @param qualificationid
     * @param username
     */
    public void borrowqualification(String qualificationid, String username) {
        SalesContractQualificationModel alesContractQualificationModel = this.get(Long.parseLong(qualificationid));
        //更新资源表状态
        List<Map<String, Object>> listMap = getDao().findQualificationchapterinfo(alesContractQualificationModel.getQualificationId());
        if (listMap.size() > 0) {
            int borrows = Integer.parseInt(listMap.get(0).get("borrows").toString());
            int totals = Integer.parseInt(listMap.get(0).get("totals").toString());
            String qcstatus = listMap.get(0).get("qcstatus").toString();
            if (qcstatus.equals("0")) {
                if (totals == (borrows + 1)) {
                    getDao().updateQualificationchapterinfoStatus(alesContractQualificationModel.getQualificationId(), borrows + 1);
                } else {
                    getDao().updateQualificationchapterinfo(alesContractQualificationModel.getQualificationId(), borrows + 1, totals - borrows - 1);
                }
            }

        }
        alesContractQualificationModel.setAgreeBorrowUser(username);
        alesContractQualificationModel.setRealBorrowDate(new Date());
        alesContractQualificationModel.setQualificationsState("0");
        this.update(alesContractQualificationModel);

        //保存日志
        Long applyFoundsId = alesContractQualificationModel.getSalesContractFoundModel().getId();
        QualificationChapterInfoModel qualificationChapterInfoModel = qualificationChapterInfoService.get(alesContractQualificationModel.getQualificationId());
        String optdesc = username + ",借出资质[" + qualificationChapterInfoModel.getQcName() + "]";
        salesFundsoptLogService.saveOptLog(applyFoundsId, optdesc);
    }

    /**
     * 归还资质操作
     * @param qualificationid
     * @param username
     */
    public void returnqualification(String qualificationid, String username) {
        SalesContractQualificationModel alesContractQualificationModel = this.get(Long.parseLong(qualificationid));
        //更新资源表，减少借出数量，判断是否全部借出
        List<Map<String, Object>> listMap = getDao().findQualificationchapterinfo(alesContractQualificationModel.getQualificationId());
        if (listMap.size() > 0) {
            int borrows = Integer.parseInt(listMap.get(0).get("borrows").toString());
            int totals = Integer.parseInt(listMap.get(0).get("totals").toString());
            getDao().updateQualificationinfoRed(alesContractQualificationModel.getQualificationId(), borrows - 1, totals - borrows + 1);
        }

        alesContractQualificationModel.setAgreeBorrowUser(username);
        alesContractQualificationModel.setRealReturnDate(new Date());
        alesContractQualificationModel.setQualificationsState("1");
        this.update(alesContractQualificationModel);

        //保存日志
        Long applyFoundsId = alesContractQualificationModel.getSalesContractFoundModel().getId();
        QualificationChapterInfoModel qualificationChapterInfoModel = qualificationChapterInfoService.get(alesContractQualificationModel.getQualificationId());
        String optdesc = username + ",归还资质[" + qualificationChapterInfoModel.getQcName() + "]";
        salesFundsoptLogService.saveOptLog(applyFoundsId, optdesc);
    }
}
