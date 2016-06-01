/*
 * FileName: SalesContractChapterService.java
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
import com.sinobridge.eoss.sales.contractfounds.dao.SalesContractChapterDao;
import com.sinobridge.eoss.sales.contractfounds.model.QualificationChapterInfoModel;
import com.sinobridge.eoss.sales.contractfounds.model.SalesContractChapterModel;

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
 * 2015年12月17日 下午3:15:46          tigq        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Service
@Transactional
public class SalesContractChapterService extends DefaultBaseService<SalesContractChapterModel, SalesContractChapterDao> {

    @Autowired
    private SalesFundsoptLogService salesFundsoptLogService;

    @Autowired
    private QualificationChapterInfoService qualificationChapterInfoService;

    /**
     * 借出操作
     * @param stampid
     */
    public void brrowStamp(String stampid, String username) {
        SalesContractChapterModel salesContractChapterModel = this.get(Long.parseLong(stampid));

        //更新资源表，添加借出数量，判断是否全部借出
        List<Map<String, Object>> listMap = getDao().findQualificationchapterinfo(salesContractChapterModel.getChapterId());
        if (listMap.size() > 0) {
            int borrows = Integer.parseInt(listMap.get(0).get("borrows").toString());
            int totals = Integer.parseInt(listMap.get(0).get("totals").toString());
            String qcstatus = listMap.get(0).get("qcstatus").toString();
            if (qcstatus.equals("0")) {
                if (totals == (borrows + 1)) {
                    getDao().updateQualificationchapterinfoStatus(salesContractChapterModel.getChapterId(), borrows + 1);
                } else {
                    getDao().updateQualificationchapterinfo(salesContractChapterModel.getChapterId(), borrows + 1, totals - borrows - 1);
                }
            }

        }

        salesContractChapterModel.setAgreeBorrowUser(username);
        salesContractChapterModel.setRealBorrowDate(new Date());
        salesContractChapterModel.setChapterstate("0");
        this.update(salesContractChapterModel);

        //保存日志
        Long applyFoundsId = salesContractChapterModel.getSalesContractFoundModel().getId();
        QualificationChapterInfoModel qualificationChapterInfoModel = qualificationChapterInfoService.get(salesContractChapterModel.getChapterId());
        String optdesc = username + ",借出章[" + qualificationChapterInfoModel.getQcName() + "]";
        salesFundsoptLogService.saveOptLog(applyFoundsId, optdesc);
    }

    /**
     * 归还操作
     * @param stampid
     */
    public void returnStamp(String stampid, String username) {
        SalesContractChapterModel salesContractChapterModel = this.get(Long.parseLong(stampid));
        //更新资源表，减少借出数量，判断是否全部借出
        List<Map<String, Object>> listMap = getDao().findQualificationchapterinfo(salesContractChapterModel.getChapterId());
        if (listMap.size() > 0) {
            int borrows = Integer.parseInt(listMap.get(0).get("borrows").toString());
            int totals = Integer.parseInt(listMap.get(0).get("totals").toString());
            getDao().updateQualificationinfoRed(salesContractChapterModel.getChapterId(), borrows - 1, totals - borrows + 1);
        }

        salesContractChapterModel.setAgreeBorrowUser(username);
        salesContractChapterModel.setRealReturnDate(new Date());
        salesContractChapterModel.setChapterstate("1");
        this.update(salesContractChapterModel);

        //保存日志
        Long applyFoundsId = salesContractChapterModel.getSalesContractFoundModel().getId();
        QualificationChapterInfoModel qualificationChapterInfoModel = qualificationChapterInfoService.get(salesContractChapterModel.getChapterId());
        String optdesc = username + ",归还章[" + qualificationChapterInfoModel.getQcName() + "]";
        salesFundsoptLogService.saveOptLog(applyFoundsId, optdesc);
    }

}
