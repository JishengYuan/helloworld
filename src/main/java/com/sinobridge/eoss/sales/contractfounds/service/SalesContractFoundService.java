/*
 * FileName: SalesContractFoundService.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.sales.contractfounds.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinobridge.base.core.service.DefaultBaseService;
import com.sinobridge.base.core.utils.PaginationSupport;
import com.sinobridge.eoss.sales.contract.service.SalesContractService;
import com.sinobridge.eoss.sales.contractfounds.dao.QualificationChapterInfoDao;
import com.sinobridge.eoss.sales.contractfounds.dao.SalesContractChapterDao;
import com.sinobridge.eoss.sales.contractfounds.dao.SalesContractFoundDao;
import com.sinobridge.eoss.sales.contractfounds.dao.SalesContractQualificationDao;
import com.sinobridge.eoss.sales.contractfounds.dao.SalesContractSizeDao;
import com.sinobridge.eoss.sales.contractfounds.dao.SalesContractsCertificateDao;
import com.sinobridge.eoss.sales.contractfounds.model.QualificationChapterInfoModel;
import com.sinobridge.eoss.sales.contractfounds.model.SalesContractChapterModel;
import com.sinobridge.eoss.sales.contractfounds.model.SalesContractFoundModel;
import com.sinobridge.eoss.sales.contractfounds.model.SalesContractQualificationModel;
import com.sinobridge.eoss.sales.contractfounds.model.SalesContractSizeModel;
import com.sinobridge.eoss.sales.contractfounds.model.SalesContractsCertificateModel;
import com.sinobridge.systemmanage.util.StringUtil;

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
 * 2015年12月1日 下午4:58:34      liyx           1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Service
@Transactional
public class SalesContractFoundService extends DefaultBaseService<SalesContractFoundModel, SalesContractFoundDao> {
    @Autowired
    private SalesContractFoundDao salesContractFoundDao;
    @Autowired
    private SalesContractChapterDao salesContractChapterDao;
    @Autowired
    private SalesContractQualificationDao salesContractQualificationDao;
    @Autowired
    private SalesContractsCertificateDao salesContractsCertificateDao;
    @Autowired
    private SalesContractSizeDao salesContractSizeDao;
    @Autowired
    private QualificationChapterInfoDao qualificationChapterInfoDao;
    @Autowired
    private SalesContractService salesContractService;

    @Autowired
    private SalesFundsoptLogService salesFundsoptLogService;

    /**
     * 保存
     * @param foundModel
     * @param chapterList
     * @param ficationList
     * @param certificateList
     * @param sizeList
     */
    public void saveAll(SalesContractFoundModel foundModel, List<SalesContractChapterModel> chapterList, List<SalesContractQualificationModel> ficationList, List<SalesContractsCertificateModel> certificateList, List<SalesContractSizeModel> sizeList) {
        if (foundModel != null) {
            salesContractFoundDao.saveOrUpdate(foundModel);
        }
        if (chapterList != null) {
            salesContractChapterDao.saveOrUpdateAll(chapterList);
        }
        if (ficationList != null) {
            salesContractQualificationDao.saveOrUpdateAll(ficationList);
        }
        if (certificateList != null) {
            salesContractsCertificateDao.saveOrUpdateAll(certificateList);
        }
        if (sizeList != null) {
            salesContractSizeDao.saveOrUpdateAll(sizeList);
        }

    }

    /**重新提交保存
     * @param salesContractFoundModel
     * @param chapterList
     * @param ficationList
     * @param certificateList
     * @param sizeList
     * @param oldsales
     * wangya 
     */
    public void updateAll(SalesContractFoundModel salesContractFoundModel, List<SalesContractChapterModel> chapterList, List<SalesContractQualificationModel> ficationList, List<SalesContractsCertificateModel> certificateList, List<SalesContractSizeModel> sizeList, SalesContractFoundModel oldsales) {
        //删除原表
        deleteSalesList(oldsales);

        if (chapterList != null) {
            salesContractChapterDao.saveOrUpdateAll(chapterList);
        }
        if (ficationList != null) {
            salesContractQualificationDao.saveOrUpdateAll(ficationList);
        }
        if (certificateList != null) {
            salesContractsCertificateDao.saveOrUpdateAll(certificateList);
        }
        if (sizeList != null) {
            salesContractSizeDao.saveOrUpdateAll(sizeList);
        }

        oldsales.setApplyDesc(salesContractFoundModel.getApplyDesc());
        oldsales.setApplyFundsName(salesContractFoundModel.getApplyFundsName());
        oldsales.setApplyFundsState(salesContractFoundModel.getApplyFundsState());
        oldsales.setCusIndustryId(salesContractFoundModel.getCusIndustryId());
        oldsales.setExpectProfit(salesContractFoundModel.getExpectProfit());
        salesContractFoundDao.saveOrUpdate(oldsales);

    }

    /**
     * @param oldsales
     */
    private void deleteSalesList(SalesContractFoundModel oldsales) {
        salesContractChapterDao.deleteChapter(oldsales.getId());
        salesContractQualificationDao.deleteQualification(oldsales.getId());
        salesContractsCertificateDao.deleteCertificate(oldsales.getId());
        salesContractSizeDao.deleteSize(oldsales.getId());

    }

    /**
     * @param c
     * @return
     */
    public String findChaperTime(SalesContractChapterModel c) {
        String name = salesContractChapterDao.findChaperTime(c);
        return name;
    }

    /**我都投标列表
     * @param searchMap
     * @param i
     * @param pageSize
     * @return
     */
    public PaginationSupport findFoundList(HashMap<String, Object> searchMap, int startIndex, Integer pageSize) {
        return getDao().findFoundList(searchMap, startIndex, pageSize);
    }

    /**投标回款列表
     * @param searchMap
     * @param i
     * @param pageSize
     * @return
     */
    public PaginationSupport findBiddingList(HashMap<String, Object> searchMap, int startIndex, Integer pageSize) {
        return getDao().findBiddingList(searchMap, startIndex, pageSize);
    }

    /**根据Code 查合同Name
     * @param id
     * @return
     */
    public Map<String, Object> getSalesContractByCode(String code) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (!StringUtil.isEmpty(code)) {
            String hql = "select ContractName from sales_contract where ContractCode = ?";
            List<Map<String, String>> mapList = getDao().createSQLQuery(hql, new Object[] { code }).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
            if (mapList.size() > 0) {
                map.put("name", mapList.get(0).get("ContractName"));
            }
        }
        return map;
    }

    /**
     * 获得没有退回保证金的投标单
     * @param systemUser
     * @param cuscustomerId
     * @return
     */
    public List<SalesContractFoundModel> getNotReturnPrice(String creator, Long cuscustomerId) {
        return getDao().getNotReturnPrice(creator, cuscustomerId);

    }

    /**下拉框查询
       * @param type
       * @return
       */
    public List<Map<String, Object>> findSalesType(String type) {
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        mapList = getDao().findSalesType(type);
        return mapList;
    }

    /**
     * @param c
     * @return
     */
    public String findQualificationTime(SalesContractQualificationModel c) {
        String name = salesContractQualificationDao.findQualificationTime(c);
        return name;
    }

    /**
     * 获取保证金申请详细信息
     * @param SalesContractFoundModelId
     * @return
     */
    public SalesContractFoundModel getEarnesMoneyInfo(Long SalesContractFoundModelId) {
        SalesContractFoundModel salesContractFoundModel = this.get(SalesContractFoundModelId);
        List<SalesContractChapterModel> stamps = salesContractFoundModel.getSalesContractChapterModel();
        List<SalesContractQualificationModel> qualifications = salesContractFoundModel.getSalesContractQualificationModel();
        List<SalesContractsCertificateModel> certificates = salesContractFoundModel.getSalesContractsCertificateModel();
        List<QualificationChapterInfoModel> qualificationChapterlist = qualificationChapterInfoDao.list();
        for (SalesContractQualificationModel salesContractQualificationModel : qualifications) {
            salesContractQualificationModel.setQualificationName(qualificationName(qualificationChapterlist, salesContractQualificationModel.getQualificationId()));
        }
        for (SalesContractChapterModel salesContractChapterModel : stamps) {
            salesContractChapterModel.setChapterName(qualificationName(qualificationChapterlist, salesContractChapterModel.getChapterId()));
        }
        for (SalesContractsCertificateModel salesContractsCertificateModel : certificates) {
            salesContractsCertificateModel.setCertificateName(qualificationName(qualificationChapterlist, salesContractsCertificateModel.getCertificateType()));
        }
        return salesContractFoundModel;
    }

    /**
     * 根据ID获取资质、章的名称
     * @param qualificationChapterlist
     * @param qcid
     * @return
     */
    public String qualificationName(List<QualificationChapterInfoModel> qualificationChapterlist, Long qcid) {
        for (QualificationChapterInfoModel qualificationChapterInfoModel : qualificationChapterlist) {
            if (qualificationChapterInfoModel.getId() == qcid) {
                return qualificationChapterInfoModel.getQcName();
            }
        }
        return null;
    }

    public void updateMergerfunds(String foundId, String contractCode, String mergercontractPrice, String username) {
        SalesContractFoundModel salesContractFoundModel = this.get(Long.parseLong(foundId));
        salesContractFoundModel.setContractCode(contractCode);
        Map<String, Object> contract = salesContractService.getContractName(Long.parseLong(contractCode));
        salesContractFoundModel.setContractName(contract.get("name").toString());
        salesContractFoundModel.setMergerProjectPrice(BigDecimal.valueOf(Double.parseDouble(mergercontractPrice)));
        this.update(salesContractFoundModel);
        String optDesc = username + ",项目划归完成[" + salesContractFoundModel.getContractName() + "]，金额：￥" + mergercontractPrice;
        salesFundsoptLogService.saveOptLog(Long.parseLong(foundId), optDesc);
    }

}
