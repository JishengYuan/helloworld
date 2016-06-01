/*
 * FileName: SalesContractService.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.sales.cachet.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinobridge.base.core.service.DefaultBaseService;
import com.sinobridge.eoss.bpm.service.process.ProcessService;
import com.sinobridge.eoss.sales.cachet.dao.SalesCachetDao;
import com.sinobridge.eoss.sales.cachet.model.SalesCachetModel;
import com.sinobridge.eoss.sales.contract.constant.SalesContractConstant;
import com.sinobridge.eoss.sales.contract.dao.SalesContractStatusDao;
import com.sinobridge.eoss.sales.contract.model.SalesContractModel;
import com.sinobridge.eoss.sales.contract.model.SalesContractStatusModel;
import com.sinobridge.eoss.sales.contract.service.SalesContractService;
import com.sinobridge.eoss.sales.contract.service.SalesContractStatusService;
import com.sinobridge.eoss.sales.invoice.model.SalesInvoicePlanModel;
import com.sinobridge.eoss.sales.invoice.service.SalesInvoiceService;
import com.sinobridge.systemmanage.vo.SystemUser;

/**
 * <p>
 * Description: 
 * </p>
 *
 * @author 3unshine
 * @version 1.0

 * <p>
 * History: 
 *
 * Date                     Author         Version     Description
 * ---------------------------------------------------------------------------------
 * 2014年5月5日 下午8:25:36          3unshine        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Service
@Transactional
public class SalesCachetService extends DefaultBaseService<SalesCachetModel, SalesCachetDao> {
    @Autowired
    private ProcessService processService;
    @Autowired
    private SalesContractStatusService salesContractStatusService;
    @Autowired
    private SalesContractStatusDao salesContractStatusDao;
    @Autowired
    private SalesContractService salesContractService;
    @Autowired
    private SalesInvoiceService invoiceService;

    /**
     * <code>save</code>
     * 保存盖章申请实体，并提交流程
     * @param salesCachetModel
     */
    public void save(SalesCachetModel salesCachetModel, SystemUser systemUser) {
        SalesContractStatusModel salesContractStatusModel = salesContractStatusService.findContractStatusByContractId(salesCachetModel.getSalesContractId());
        salesContractStatusModel.setCachetStatus(SalesContractConstant.CONTRACT_CACHET_FIRSTNODE);
        salesContractStatusService.update(salesContractStatusModel);
        creatProcInst(salesCachetModel, systemUser);
        getDao().save(salesCachetModel);
    }

    /**
     * <code>creatProcInst</code>
     * 创建工单
     * @param tableData
     * @param tableData
     * @since 2014年7月8日 3unshine
     */
    public long creatProcInst(SalesCachetModel salesCachetModel, SystemUser systemUser) {
        Map<String, Object> variableMap = new HashMap<String, Object>();
        SalesContractModel sale = salesContractService.get(salesCachetModel.getSalesContractId());
        long processInstanceId = processService.nextValId();
        salesCachetModel.setProcessInstanceId(processInstanceId);
        variableMap.put("salesCachetModel", salesCachetModel);
        Long[] procInstId = processService.create(processInstanceId, salesCachetModel.getSalesContractName() + "[￥" + sale.getContractAmount() + "]" + SalesContractConstant.CONTRACT_CACHET_PROCTITLE, systemUser.getUserName(), SalesContractConstant.CONTRACT_CACHET_PROCDEFKEY, 1, variableMap, null, null, null);
        System.out.println("创建工单成功：" + procInstId[0]);
        return procInstId[0];
    }

    /**
     * @param salesContractId
     * @return
     */
    public SalesCachetModel getBysalesContractId(long salesContractId) {
        SalesCachetModel salesCachetModel = getDao().getBysalesContractId(salesContractId);
        return salesCachetModel;
    }

    /**
     * <code>handleFlow</code>
     * 处理工单
     * @param flowFlag 流程节点标示
     * @param salesCachetModel 销售合同盖章实体(重新提交审核时使用)
     * @param systemUser 操作人实体
     * @param taskId 任务ID
     * @param isAgree 是否同意
     * @param remark 审批意见
     * @throws Exception 
     * @since 2014年7月16日 3unshine 
     */
    public void handleFlow(List<SalesInvoicePlanModel> salesInvoicePlanModels, String flowFlag, SalesCachetModel salesCachetModel, SystemUser systemUser, String procInstId, String taskId, int isAgree, String remark) throws Exception {
        String userId = systemUser.getUserName();
        Map<String, Object> variableMap = new HashMap<String, Object>();
        if ("CXTJ".equals(flowFlag)) {

            //插入流程变量合同是否为重新提交
            boolean isResubmit = true;
            //根据传进来的值判断是不是废弃申请 isResubmit = false;
            if (salesCachetModel.getCachetStatus().equals("FQ")) {

                //2016-5-18测试，废弃盖章不应跟发票有关联
                /*for (SalesInvoicePlanModel invoice : salesInvoicePlanModels) {
                    invoice.setInvoiceStatus("FQ");
                    invoiceService.update(invoice);
                }*/

                isResubmit = false;
                //将该合同的蓋章申请状态设置为待重新申请
                long contractId = salesCachetModel.getSalesContractId();
                SalesContractStatusModel salesContractStatusModel = salesContractStatusDao.findContractStatusByContractId(contractId);
                salesContractStatusModel.setCachetStatus(SalesContractConstant.CONTRACT_CACHET_REAPPLYSTATE);
                salesContractStatusDao.update(salesContractStatusModel);

                List<SalesCachetModel> salesCachetLists = getDao().getCachetListsContractId(contractId);
                for (SalesCachetModel cachet : salesCachetLists) {
                    cachet.setCachetStatus("FQ");
                    getDao().update(cachet);
                }

            }
            //判断更新是否正确，正确则处理表单
            //update(salesCachetModel);
            variableMap.put(SalesContractConstant.IS_RE_SUBMIT, isResubmit);
            //因为是重新提交原流程变量里面的salesCachetModel要替换掉
            variableMap.put("salesCachetModel", salesCachetModel);
            processService.handle(taskId, userId, variableMap, null, null);
        } else {
            if ("ZKZYSP".equals(flowFlag) && isAgree == 1) { //质控专员审批通过
                //更新合同的蓋章状态为审核通过
                long contractId = salesCachetModel.getSalesContractId();
                SalesContractStatusModel salesContractStatusModel = salesContractStatusDao.findContractStatusByContractId(contractId);
                salesContractStatusModel.setCachetStatus(SalesContractConstant.CONTRACT_CACHET_ENDSTATE);
                salesContractStatusDao.update(salesContractStatusModel);
            }
            if ("HGXSCSP".equals(flowFlag) && isAgree == 1) { //合规性审查审批通过
                //更新合同的蓋章状态为已盖章，张洁卿审批通过即视为已盖章
                salesCachetModel.setCachetStatus(SalesContractConstant.CONTRACT_CACHET_PASSSTATE);
                salesCachetModel.setCachetDate(new Date());
                update(salesCachetModel);

            }
            processService.handle(taskId, userId, isAgree, remark, variableMap, null, null);
        }
    }

    /**
     * <code>saveOrUpdate</code>
     * 
     * @param salesCachetModel
     * @param systemUser
     * @since 2014年7月16日 3unshine
     */
    @Override
    public void update(SalesCachetModel salesCachetModel) {
        getDao().saveOrUpdate(salesCachetModel);
    }

    /**
     * @param detachedCriteria
     * @return
     */
    public SalesCachetModel getSalesCachetModelByContractId(DetachedCriteria detachedCriteria) {
        return getDao().findByCriteria(detachedCriteria).get(0);
    }

    /**
     * <code>getSalesCachetModelByProcessInstanceId</code>
     * 根据工单ID得到盖章实体
     * @param processInstanceId
     * @return
     * @since   2014年11月28日    guokemenng
     */
    public SalesCachetModel getSalesCachetModelByProcessInstanceId(Long processInstanceId) {
        String hql = "from SalesCachetModel where processInstanceId = ?";
        List<SalesCachetModel> modelList = getDao().find(hql, new Object[] { processInstanceId });
        if (modelList.size() > 0) {
            return modelList.get(0);
        } else {
            return null;
        }
    }
}
