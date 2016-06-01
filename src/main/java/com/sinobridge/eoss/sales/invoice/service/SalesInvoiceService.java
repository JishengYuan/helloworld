/*
 * FileName: SalesContractService.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.sales.invoice.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mysql.jdbc.StringUtils;
import com.sinobridge.base.core.exception.SinoRuntimeException;
import com.sinobridge.base.core.hibernate.id.IdentifierGeneratorImpl;
import com.sinobridge.base.core.service.DefaultBaseService;
import com.sinobridge.base.core.utils.JacksonJsonMapper;
import com.sinobridge.base.core.utils.PaginationSupport;
import com.sinobridge.eoss.bpm.service.process.ProcessService;
import com.sinobridge.eoss.sales.contract.constant.SalesContractConstant;
import com.sinobridge.eoss.sales.contract.dao.SalesContractDao;
import com.sinobridge.eoss.sales.contract.dao.SalesContractStatusDao;
import com.sinobridge.eoss.sales.contract.dao.SalesReceivePlanDao;
import com.sinobridge.eoss.sales.contract.model.SalesContractModel;
import com.sinobridge.eoss.sales.contract.model.SalesContractStatusModel;
import com.sinobridge.eoss.sales.contract.service.SalesContractService;
import com.sinobridge.eoss.sales.contract.utils.CodeUtils;
import com.sinobridge.eoss.sales.invoice.dao.SalesInvoiceDao;
import com.sinobridge.eoss.sales.invoice.dto.SalesInvoiceExportDto;
import com.sinobridge.eoss.sales.invoice.model.SalesInvoicePlanModel;
import com.sinobridge.eoss.salesfundsCost.dao.FundsSalesBudgetDao;
import com.sinobridge.eoss.salesfundsCost.dao.SalesBudgetLogDao;
import com.sinobridge.eoss.salesfundsCost.model.FundsSalesContract;
import com.sinobridge.eoss.salesfundsCost.model.FundsSalesLog;
import com.sinobridge.eoss.salesfundsCost.model.SalesBudgetFunds;
import com.sinobridge.eoss.salesfundsCost.model.SalesBudgetLog;
import com.sinobridge.eoss.salesfundsCost.service.FundsSalesBudgetService;
import com.sinobridge.eoss.salesfundsCost.service.FundsSalesContractService;
import com.sinobridge.eoss.salesfundsCost.service.FundsSalesLogService;
import com.sinobridge.eoss.salesfundsCost.utils.DateUtils;
import com.sinobridge.systemmanage.util.StringUtil;
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
public class SalesInvoiceService extends DefaultBaseService<SalesInvoicePlanModel, SalesInvoiceDao> {

    @Autowired
    private SalesReceivePlanDao salesReceivePlanDao;
    @Autowired
    private ProcessService processService;
    @Autowired
    private SalesContractStatusDao salesContractStatusDao;
    @Autowired
    SalesContractService salesContractService;

    @Autowired
    private FundsSalesContractService fundsSalesContractService;
    @Autowired
    private FundsSalesLogService fundsSalesLogService;

    @Autowired
    private FundsSalesBudgetDao fundsSalesBudgetDao;
    @Autowired
    private SalesBudgetLogDao salesBudgetLogDao;
    @Autowired
    private FundsSalesBudgetService fundsSalesBudgetService;

    @Autowired
    private SalesContractDao salesContractDao;

    /**
     * <code>getBySalesContractId</code>
     * @param detachedCriteria
     * @since 2014年5月26日 3unshine
     */
    public List<SalesInvoicePlanModel> getBySalesContractId(DetachedCriteria detachedCriteria) {
        List<SalesInvoicePlanModel> salesInvoicePlans = getDao().findByCriteria(detachedCriteria);
        return salesInvoicePlans;
    }

    /**<code>save</code>
     *
     * @param salesInvoicePlanList
     * @param systemUser
     * @since 2014年7月22日 3unshine
     */
    public void save(SalesContractModel salesContractModel, List<SalesInvoicePlanModel> salesInvoicePlanList, SystemUser systemUser, String invoiceIds) throws Exception {
        long contractId = salesContractModel.getId();
        // doInvoice(contractId, allDate);

        List<SalesInvoicePlanModel> salesInvoicePlans = creatProcInst(salesInvoicePlanList, systemUser, invoiceIds);
        //更新合同状态表中的发票申请状态
        SalesContractStatusModel salesContractStatusModel = salesContractStatusDao.findContractStatusByContractId(contractId);
        salesContractStatusModel.setInvoiceStatus(SalesContractConstant.CONTRACT_INVOICE_APPLYSTATE);
        salesContractStatusDao.update(salesContractStatusModel);
        //getDao().saveOrUpdateAll(salesInvoicePlans);

        //删除草稿状态数据
        /* DetachedCriteria detachedCriteria12 = DetachedCriteria.forClass(SalesInvoicePlanModel.class);
         detachedCriteria12.add(Restrictions.eq(SalesInvoicePlanModel.CONTRACTID, contractId));
         detachedCriteria12.add(Restrictions.eq(SalesInvoicePlanModel.INVOICESTATUS, "CG"));
         List<SalesInvoicePlanModel> tableInvoicePlanModels = this.getSalesInvoicePlanListByContractId(detachedCriteria12);
         this.getDao().deleteAll(tableInvoicePlanModels);*/
    }

    /**
     * @param contractId
     * @param allDate
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> doInvoice(long contractId, String tableData) {
        List<SalesBudgetFunds> fundbuds = new ArrayList<SalesBudgetFunds>();
        List<SalesBudgetLog> logs = new ArrayList<SalesBudgetLog>();
        List<SalesInvoicePlanModel> invoices = new ArrayList<SalesInvoicePlanModel>();
        String id = String.valueOf(contractId);
        List<SalesBudgetFunds> fundOld = fundsSalesBudgetDao.getfundOld(id);//历史数据
        Map<Long, Object> oldMap = new HashMap<Long, Object>();
        Iterator<SalesBudgetFunds> old = fundOld.iterator();
        while (old.hasNext()) {//得到原来的发票ID
            SalesBudgetFunds m = old.next();
            oldMap.put(m.getId(), m.getBudgetDate());
        }
        ObjectMapper objectMapper = JacksonJsonMapper.getInstance();
        Map<String, Object> tableDataMap = null;
        Map<Long, Object> newMap = new HashMap<Long, Object>();
        if (tableData != null && tableData != "") {
            try {
                tableDataMap = objectMapper.readValue(tableData, HashMap.class);
            } catch (JsonParseException e) {
                e.printStackTrace();
            } catch (JsonMappingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (tableDataMap != null && tableDataMap.size() > 0) {
                for (String key : tableDataMap.keySet()) {
                    List<Object> gridDataList = (List<Object>) tableDataMap.get(key);
                    List<Map<String, Object>> gridList = new ArrayList<Map<String, Object>>();
                    for (int i = 0; (gridDataList != null && i < gridDataList.size()); i++) {//加了非空判断
                        Map<String, Object> map = CodeUtils.stringToMap(gridDataList.get(i).toString());//格式转换
                        if (map.size() != 0) {
                            gridList.add(map);
                        }
                    }
                    for (int i = 0; i < gridList.size(); i++) {
                        SalesBudgetFunds funds = new SalesBudgetFunds();//存预测表
                        SalesBudgetLog log = new SalesBudgetLog();//存日志
                        SalesInvoicePlanModel invoice = new SalesInvoicePlanModel();//存发票表

                        Map<String, Object> map = gridList.get(i);
                        String invoiceId = null;
                        if (map.get("id") != null) {
                            invoiceId = map.get("id").toString();
                        }
                        String invoiceAmount = map.get("invoiceAmount").toString();
                        String invoiceTime = map.get("invoiceTime").toString();
                        String invoiceType = map.get("invoiceType").toString();
                        String remark = "";
                        if (map.get("remark") != null) {
                            remark = map.get("remark").toString();
                        }
                        if (invoiceId != null) {//原来有的，修改
                            invoice = this.get(Long.parseLong(invoiceId));
                            funds = fundsSalesBudgetDao.getFunds(Long.parseLong(invoiceId));
                            newMap.put(funds.getId(), invoiceTime);
                            log.setBudgetFundsId(Long.parseLong(invoiceId));
                            log.setRemark("更新预测发票，金额：" + invoiceAmount + ",时间：" + invoiceTime);
                        } else {//新加的条目
                            long ids = IdentifierGeneratorImpl.generatorLong();
                            funds.setId(ids);
                            funds.setContractId(Long.parseLong(id));
                            SalesContractModel sales = salesContractService.get(Long.parseLong(id));
                            funds.setContractCode(sales.getContractCode());
                            funds.setContractName(sales.getContractName());
                            funds.setUserName(sales.getCreatorName());
                            funds.setSalesInvoiceId(ids);
                            long start = DateUtils.getStartTime();
                            long end = DateUtils.getEndTime();
                            DetachedCriteria detachedCriteria = DetachedCriteria.forClass(SalesBudgetFunds.class);
                            detachedCriteria.add(Restrictions.ge(SalesBudgetFunds.CREATEDATE, new Date(start)));
                            detachedCriteria.add(Restrictions.le(SalesBudgetFunds.CREATEDATE, new Date(end)));
                            detachedCriteria.add(Restrictions.isNotNull(SalesBudgetFunds.BUDGETINVOICE));
                            List<SalesBudgetFunds> salesBudgetFunds = fundsSalesBudgetDao.findByCriteria(detachedCriteria);
                            int serialNum = salesBudgetFunds.size();//流水号
                            serialNum = serialNum + 1;
                            int t = serialNum;
                            String title = "";
                            try {
                                title = DateUtils.convertDateToString(new Date(), "yyyyMMdd") + "-" + t;
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            funds.setTitle(title);
                            log.setBudgetFundsId(ids);
                            log.setRemark("预测发票，金额：" + invoiceAmount + ",时间：" + invoiceTime);

                            invoice.setId(ids);
                            invoice.setInvoiceAmount(new BigDecimal(invoiceAmount));
                            invoice.setSalesContractId(Long.parseLong(id));
                            invoice.setCreateTime(new Date());
                            invoice.setCreator(sales.getCreator());
                            invoice.setInvoiceStatus(SalesContractConstant.CONTRACT_STATE_CG);
                            invoice.setSalesContractName(sales.getContractName());
                            invoice.setSalesContractId(sales.getId());

                        }
                        funds.setInvoiceType(Integer.parseInt(invoiceType));
                        funds.setBudgetDate(DateUtils.convertStringToDate(invoiceTime, "yyyy-MM-dd"));
                        funds.setCreateDate(new Date());
                        funds.setBudgetInvoice(new BigDecimal(invoiceAmount));
                        funds.setRemark(remark);

                        invoice.setInvoiceType(Integer.parseInt(invoiceType));
                        invoice.setInvoiceTime(DateUtils.convertStringToDate(invoiceTime, "yyyy-MM-dd"));
                        invoice.setInvoiceAmount(new BigDecimal(invoiceAmount));
                        invoice.setRemark(remark);

                        log.setCreateDate(new Date());

                        fundbuds.add(funds);
                        logs.add(log);
                        invoices.add(invoice);

                    }
                }
            }
            //=======================是否有删除条目==================================
            Set<Long> oldKey = oldMap.keySet();
            Iterator<Long> key = oldKey.iterator();
            while (key.hasNext()) {
                Long k = key.next();
                if (newMap.get(k) == null) {
                    SalesBudgetFunds fundold = fundsSalesBudgetDao.get(k);
                    fundsSalesBudgetDao.delete(fundold);
                    SalesInvoicePlanModel invoice = this.get(k);
                    getDao().delete(invoice);
                }
            }
        }

        Map<String, Object> msg = new HashMap<String, Object>();

        try {
            fundsSalesBudgetDao.saveOrUpdateAll(fundbuds);
            salesBudgetLogDao.saveOrUpdateAll(logs);
            getDao().saveOrUpdateAll(invoices);
            msg.put("date", "success");
        } catch (DataAccessException e) {
            e.printStackTrace();
            msg.put("date", "flase");
            msg.put("msg", "预测数据保存失败！");
        }
        return msg;
    }

    /**
     * <code>creatProcInst</code>
     * 创建工单
     * @param tableData
     * @param tableData
     * @since 2014年7月22日 3unshine
     */
    public List<SalesInvoicePlanModel> creatProcInst(List<SalesInvoicePlanModel> salesInvoicePlanList, SystemUser systemUser, String invoiceIds) {
        try {
            //            Double invoiceCount = 0d;
            BigDecimal invoiceCount = new BigDecimal("0");

            List<SalesInvoicePlanModel> salesInvoicePlans = new ArrayList<SalesInvoicePlanModel>();
            Map<String, Object> variableMap = new HashMap<String, Object>();
            long processInstanceId = processService.nextValId();
            Iterator<SalesInvoicePlanModel> it = salesInvoicePlanList.iterator();
            Date today = new Date();
            int flat = 0;
            while (it.hasNext()) {
                SalesInvoicePlanModel s = it.next();
                s.setInvoiceStatus(SalesContractConstant.CONTRACT_STATE_SH);
                int days = (int) ((s.getInvoiceTime().getTime() - today.getTime()) / 86400000);
                if (days <= 7) {
                    flat = 1;
                }
                //添加发票预测
                SalesBudgetFunds funds = new SalesBudgetFunds();
                SalesBudgetLog log = new SalesBudgetLog();
                long ids = IdentifierGeneratorImpl.generatorLong();
                funds = fundsSalesBudgetDao.getFunds(s.getId());
                if (funds == null) {
                    funds = new SalesBudgetFunds();
                    funds.setId(ids);
                    funds.setProcessInstanceId(processInstanceId);
                    funds.setContractId(s.getSalesContractId());
                    SalesContractModel sales = salesContractService.get(s.getSalesContractId());
                    funds.setContractCode(sales.getContractCode());
                    funds.setContractName(sales.getContractName());
                    funds.setUserName(sales.getCreatorName());
                    funds.setInvoiceType(s.getInvoiceType());
                    funds.setBudgetDate(s.getInvoiceTime());
                    funds.setCreateDate(new Date());
                    funds.setBudgetInvoice(s.getInvoiceAmount());
                    long start = DateUtils.getStartTime();
                    long end = DateUtils.getEndTime();
                    DetachedCriteria detachedCriteria = DetachedCriteria.forClass(SalesBudgetFunds.class);
                    detachedCriteria.add(Restrictions.ge(SalesBudgetFunds.CREATEDATE, new Date(start)));
                    detachedCriteria.add(Restrictions.le(SalesBudgetFunds.CREATEDATE, new Date(end)));
                    detachedCriteria.add(Restrictions.isNotNull(SalesBudgetFunds.BUDGETINVOICE));
                    List<SalesBudgetFunds> salesBudgetFunds = fundsSalesBudgetDao.findByCriteria(detachedCriteria);
                    int serialNum = salesBudgetFunds.size();//流水号
                    serialNum = serialNum + 1;
                    String title = "";
                    try {
                        title = DateUtils.convertDateToString(new Date(), "yyyyMMdd") + "-" + serialNum;
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    funds.setTitle(title);
                    log.setBudgetFundsId(ids);
                    try {
                        log.setRemark("预测发票，金额：" + s.getInvoiceAmount() + "，时间：" + DateUtils.convertDateToString(s.getInvoiceTime(), "yyyy-MM-dd"));
                    } catch (ParseException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                //假如是变更过来的发票加上标示符,更新发票预测
                if (!StringUtil.isEmpty(invoiceIds)) {
                    s.setChangeProInstanceId(processInstanceId);
                    funds.setProcessInstanceId(processInstanceId);
                    String[] invids = invoiceIds.split(",");
                    List<SalesBudgetLog> logolds = new ArrayList<SalesBudgetLog>();
                    for (int i = 0; i < invids.length; i++) {
                        SalesBudgetFunds fundold = new SalesBudgetFunds();
                        fundold = fundsSalesBudgetDao.getFunds(Long.parseLong(invids[i]));
                        SalesBudgetLog logold = new SalesBudgetLog();
                        logold.setRemark("此发票提交变更。");
                        logold.setCreateDate(new Date());
                        logold.setBudgetFundsId(fundold.getId());
                        logolds.add(logold);
                    }
                    salesBudgetLogDao.saveOrUpdateAll(logolds);
                }

                //--------------------------------------------------------------------------------
                funds.setProcessInstanceId(processInstanceId);
                fundsSalesBudgetDao.saveOrUpdate(funds);
                s.setProcessInstanceId(processInstanceId);
                salesInvoicePlans.add(s);
                invoiceCount = invoiceCount.add(s.getInvoiceAmount());

                //                invoiceCount += s.getInvoiceAmount().doubleValue();

            }

            String procName = salesInvoicePlanList.get(0).getSalesContractName() + SalesContractConstant.CONTRACT_INVOICE_PROCTITLE + "[￥" + invoiceCount + "]";
            if (flat == 1) {
                procName = "加急_" + procName;
            }
            variableMap.put("salesInvoicePlanList", salesInvoicePlans);
            Long[] procInstId = processService.create(processInstanceId, procName, systemUser.getUserName(), SalesContractConstant.CONTRACT_INVOICE_PROCDEFKEY, 1, variableMap, null, null, null);
            //System.out.println("创建工单成功：" + procInstId[0]);
            log.info("创建工单成功：" + procInstId[0]);
            return salesInvoicePlans;
        } catch (SinoRuntimeException e) {
            e.printStackTrace();
            log.error("操作失败");
            throw new SinoRuntimeException("操作失败");
        }
    }

    /**
     * <code>handleFlow</code>
     * 处理工单
     * @param flowFlag 流程节点标示
     * @param salesInvoicePlanList 销售合同发票条目(重新提交审核时使用)
     * @param systemUser 操作人实体
     * @param taskId 任务ID
     * @param isAgree 是否同意
     * @param remark 审批意见
     * @throws Exception
     * @since 2014年7月16日 3unshine
     */
    @SuppressWarnings("unchecked")
    public void handleFlow(String flowFlag, long contractId, List<SalesInvoicePlanModel> salesInvoicePlanList, List<SalesInvoicePlanModel> oldSalesInvoicePlanModels, SystemUser systemUser, long procInstId, String taskId, int isAgree, String remark) throws Exception {
        String userId = systemUser.getUserName();
        Map<String, Object> variableMap = new HashMap<String, Object>();
        try {
            if ("CXTJ".equals(flowFlag)) {
                //删除原数据
                //getDao().deleteAll(oldSalesInvoicePlanModels);
                //判断更新是否正确，正确则处理表单
                getDao().saveOrUpdateAll(salesInvoicePlanList);
                //插入流程变量合同是否为重新提交
                boolean isResubmit = true;
                if (salesInvoicePlanList.get(0).getInvoiceStatus().equals("FQ")) {
                    isResubmit = false;
                    //将该合同的发票申请状态设置为待重新申请
                    SalesContractStatusModel salesContractStatusModel = salesContractStatusDao.findContractStatusByContractId(contractId);
                    salesContractStatusModel.setInvoiceStatus(SalesContractConstant.CONTRACT_INVOICE_REAPPLYSTATE);
                    salesContractStatusDao.update(salesContractStatusModel);

                    //新变更的发票废弃后  旧的发票计划还原
                    DetachedCriteria detachedCriteria1 = DetachedCriteria.forClass(SalesInvoicePlanModel.class);
                    detachedCriteria1.add(Restrictions.eq(SalesInvoicePlanModel.CONTRACTID, contractId));
                    detachedCriteria1.add(Restrictions.eq(SalesInvoicePlanModel.INVOICESTATUS, "BG"));
                    List<SalesInvoicePlanModel> changeInvoicePlanModels = this.getSalesInvoicePlanListByContractId(detachedCriteria1);
                    for (SalesInvoicePlanModel plan : changeInvoicePlanModels) {
                        plan.setInvoiceStatus("TGSH");
                        this.update(plan);
                    }
                } else {
                    //给预测表加流程ID
                    updateFundsBudget(salesInvoicePlanList);
                }
                variableMap.put(SalesContractConstant.IS_RE_SUBMIT, isResubmit);
                //因为是重新提交原流程变量里面的salesInvoicePlanList要替换掉
                variableMap.put("salesInvoicePlanList", salesInvoicePlanList);
                processService.handle(taskId, userId, variableMap, null, null);

                if (salesInvoicePlanList.size() > 0) {
                    BigDecimal invoiceCount = new BigDecimal("0");

                    Iterator<SalesInvoicePlanModel> it = salesInvoicePlanList.iterator();
                    while (it.hasNext()) {
                        SalesInvoicePlanModel s = it.next();
                        invoiceCount = invoiceCount.add(s.getInvoiceAmount());
                    }

                    Long proInstanceId = salesInvoicePlanList.get(0).getProcessInstanceId();

                    String sql = "select i.TITLE_ title from bpm_process_inst i where i.ID_ = '" + proInstanceId + "'";
                    List<Map<String, Object>> mapList = getDao().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
                    if (mapList.size() > 0) {
                        String name = mapList.get(0).get("title").toString();
                        String title = name.split("￥")[0] + "￥" + invoiceCount + "]";

                        String sqlStr = "update bpm_process_inst set TITLE_ = '" + title + "' where ID_ = '" + proInstanceId + "'";
                        getDao().createSQLQuery(sqlStr).executeUpdate();
                    }
                }

            } else {
                if ("CWFH".equals(flowFlag) && isAgree == 1) {
                    Date time = new Date();
                    //新变更的合同同意后  旧的发票计划废弃
                    DetachedCriteria detachedCriteria1 = DetachedCriteria.forClass(SalesInvoicePlanModel.class);
                    detachedCriteria1.add(Restrictions.eq(SalesInvoicePlanModel.CONTRACTID, contractId));
                    detachedCriteria1.add(Restrictions.eq(SalesInvoicePlanModel.INVOICESTATUS, "BG"));
                    List<SalesInvoicePlanModel> changeInvoicePlanModels = this.getSalesInvoicePlanListByContractId(detachedCriteria1);

                    for (SalesInvoicePlanModel plan : changeInvoicePlanModels) {
                        plan.setInvoiceStatus("FQ");
                        this.update(plan);
                    }

                    Iterator<SalesInvoicePlanModel> planList = oldSalesInvoicePlanModels.iterator();
                    BigDecimal invoiceAmountNew = new BigDecimal(0.00);
                    while (planList.hasNext()) {
                        SalesInvoicePlanModel s = planList.next();
                        if (!s.getInvoiceStatus().equals("TGSH")) {
                            s.setInvoiceStatus("TGSH");
                            s.setChangeProInstanceId(0l);
                            s.setFinalInspection(time);
                            getDao().update(s);
                            invoiceAmountNew = invoiceAmountNew.add(s.getInvoiceAmount());
                        }
                    }
                    getDao().flush();
                    List<SalesInvoicePlanModel> salesInvoice = new ArrayList<SalesInvoicePlanModel>();
                    DetachedCriteria detachedCriteria = DetachedCriteria.forClass(SalesInvoicePlanModel.class);
                    detachedCriteria.add(Restrictions.eq(SalesInvoicePlanModel.CONTRACTID, contractId));
                    detachedCriteria.add(Restrictions.ne(SalesInvoicePlanModel.INVOICESTATUS, "FQ"));
                    detachedCriteria.add(Restrictions.ne(SalesInvoicePlanModel.INVOICESTATUS, "BG"));
                    salesInvoice = this.getSalesInvoicePlanListByContractId(detachedCriteria);
                    Iterator<SalesInvoicePlanModel> inv = salesInvoice.iterator();
                    int flat = 0;
                    BigDecimal invoiceAmount = new BigDecimal(0.00);
                    while (inv.hasNext()) {
                        SalesInvoicePlanModel s = inv.next();
                        if (!s.getInvoiceStatus().equals("TGSH")) {
                            flat = 1;
                        } else {
                            invoiceAmount = invoiceAmount.add(s.getInvoiceAmount());
                        }
                    }
                    SalesContractModel sale = null;
                    if (flat == 0) {
                        //如合同发票总金额和合同金额相等 则新合同的发票状态为审核通过
                        sale = salesContractService.get(contractId);
                        DetachedCriteria detach = DetachedCriteria.forClass(SalesInvoicePlanModel.class);
                        detach.add(Restrictions.eq(SalesInvoicePlanModel.CONTRACTID, contractId));
                        detach.add(Restrictions.ne(SalesInvoicePlanModel.INVOICESTATUS, "FQ"));
                        detach.add(Restrictions.ne(SalesInvoicePlanModel.INVOICESTATUS, "BG"));
                        List<SalesInvoicePlanModel> planModelList = getSalesInvoicePlanListByContractId(detach);
                        Double d = 0d;
                        for (SalesInvoicePlanModel plan : planModelList) {
                            d += plan.getInvoiceAmount().doubleValue();
                        }
                        if (sale.getContractAmount().doubleValue() == d) {
                            SalesContractStatusModel salesContractStatusModel = salesContractStatusDao.findContractStatusByContractId(contractId);
                            salesContractStatusModel.setInvoiceStatus(SalesContractConstant.CONTRACT_INVOICE_ENDSTATE);
                            salesContractStatusDao.update(salesContractStatusModel);
                        }
                    }

                    /********************核算合同开出的发票**************************************/
                    if (sale == null) {
                        sale = salesContractService.get(contractId);
                    }
                    FundsSalesContract fundsSalesContract = fundsSalesContractService.getBycontractCode(sale.getContractCode());
                    if (fundsSalesContract != null) {
                        BigDecimal amount1 = new BigDecimal(0.00);
                        BigDecimal hiAmount = fundsSalesContract.getOutInvoice();
                        //if (fundsSalesContract.getOutInvoice() != null) {
                        //amount1 = amount1.add(fundsSalesContract.getOutInvoice()); //加上原来的开票金额
                        //}
                        amount1 = amount1.add(invoiceAmount);
                        fundsSalesContract.setOutInvoice(amount1);
                        fundsSalesContractService.update(fundsSalesContract);
                        FundsSalesLog fundsSalesLog = new FundsSalesLog();
                        fundsSalesLog.setOpAmount(amount1);
                        fundsSalesLog.setOpDate(new Date());
                        fundsSalesLog.setContractCode(sale.getContractCode());
                        fundsSalesLog.setOpDesc("合同开出发票");
                        /*历史金额，没有的话使用第一次作为历史金额*/
                        if (hiAmount != null) {
                            fundsSalesLog.setHiAmount(hiAmount);
                        } else {
                            fundsSalesLog.setHiAmount(amount1);
                        }

                        fundsSalesLogService.saveOrUpdate(fundsSalesLog);
                    } else {
                        log.error("合同：" + sale.getContractCode() + ",没有进行开出发票计算！");
                    }
                }
                processService.handle(taskId, userId, isAgree, remark, variableMap, null, null);
            }
        } catch (SinoRuntimeException e) {
            e.printStackTrace();
            log.error("操作失败");
            throw new SinoRuntimeException("操作失败");
        }
    }

    /**
     * @param salesInvoicePlanList
     */
    private void updateFundsBudget(List<SalesInvoicePlanModel> salesInvoicePlanList) {
        Iterator<SalesInvoicePlanModel> invoice = salesInvoicePlanList.iterator();
        List<SalesBudgetFunds> fundsList = new ArrayList<SalesBudgetFunds>();
        while (invoice.hasNext()) {
            SalesInvoicePlanModel i = invoice.next();
            SalesBudgetFunds funds = new SalesBudgetFunds();//存预测表
            funds = fundsSalesBudgetDao.getFunds(i.getId());
            funds.setProcessInstanceId(i.getProcessInstanceId());
            fundsList.add(funds);
        }
        try {
            fundsSalesBudgetDao.saveOrUpdateAll(fundsList);
        } catch (DataAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * <code>getSalesInvoicePlanListByContractId</code>
     * 根据合同ID查出合同发票
     * @param detachedCriteria
     * @return List<SalesInvoicePlanModel>
     * @since 2014年8月1日 3unshine
     */
    public List<SalesInvoicePlanModel> getSalesInvoicePlanListByContractId(DetachedCriteria detachedCriteria) {
        return getDao().findByCriteria(detachedCriteria);
    }

    /**
     * <code>isPrint</code>
     * 是否出现打印按钮
     * @param proInstId
     * @return
     * @since   2014年8月20日    guokemenng
     */
    @SuppressWarnings("unchecked")
    public String isPrint(Long processInstanceId) {
        String print = "1";
        String noPrint = "0";
        Object[] param = new Object[1];
        param[0] = processInstanceId;
        String sql = "select t.NAME_ name from bpm_process_inst b left join act_ru_task t on b.HI_PROC_INST_ID = t.PROC_INST_ID_ where b.ID_ = ?";
        Query query = null;
        query = getDao().createSQLQuery(sql, param);
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List<Map<String, Object>> mapList = query.list();
        String str = "财务";
        if (mapList != null && mapList.size() > 0) {
            Object name = mapList.get(0).get("name");
            if (name == null) {
                return noPrint;
            } else {
                if (name.toString().indexOf(str) != -1) {
                    return print;
                } else {
                    return noPrint;
                }
            }
        } else {
            return noPrint;
        }
    }

    /**
     * <code>getInvoicePlanModelByProcessInstanceId</code>
     * 根据工单ID 得到计划
     * @param processInstanceId
     * @return
     * @since   2014年11月28日    guokemenng
     */
    public List<SalesInvoicePlanModel> getInvoicePlanModelByProcessInstanceId(Long processInstanceId) {
        String hql = "from SalesInvoicePlanModel where processInstanceId = ?";
        List<SalesInvoicePlanModel> planList = this.getDao().find(hql, new Object[] { processInstanceId });
        return planList;
    }

    public static String getShowNumber(BigDecimal number) {

        String sb = "";
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        dfs.setDecimalSeparator('.');
        dfs.setGroupingSeparator(',');
        dfs.setMonetaryDecimalSeparator('.');
        DecimalFormat df = new DecimalFormat("###,###.##", dfs);
        sb = df.format(number);
        if (!sb.contains(".")) {
            sb = sb + ".00";
        }
        return sb.toString();
    }

    public PaginationSupport getList(Map<String, String> paramMap, Integer startIndex, Integer pageSize) {
        StringBuilder sb = new StringBuilder();
        sb.append("select s.ContractName,s.ContractAmount,s.CreatorName,i.id,i.InvoiceAmount,i.CreateTime,i.InvoiceStatus,art.NAME_,art.CREATE_TIME_,art.ASSIGNEE_,u.last_,i.FinalInspection,i.salesContractId");
        sb.append(" from sales_invoice_plan i left join Sales_Contract s on i.SalesContractId = s.id");
        sb.append(" LEFT JOIN bpm_process_inst bpi ON i.ProcessInstanceId=bpi.ID_");
        sb.append(" LEFT JOIN act_ru_task art ON bpi.HI_PROC_INST_ID= art.proc_inst_id_");
        sb.append(" LEFT JOIN act_id_user u on u.id_ = art.ASSIGNEE_");
        sb.append(" where 0=0 and i.InvoiceStatus !='FQ' AND i.InvoiceStatus != 'CG' AND s.`ContractState`!='HB' ");

        int start = 0;
        Object[] params = new Object[paramMap.size() - 2];
        if (paramMap.get("creator") != null) {
            params[start++] = paramMap.get("creator");
            sb.append(" and i.Creator = ?");
        }
        if (paramMap.get("creatorUse") != null) {
            params[start++] = paramMap.get("creatorUse");
            sb.append(" and i.Creator = ?");
        }
        if (paramMap.get("startTime") != null) {
            params[start++] = paramMap.get("startTime");
            sb.append(" and i.FinalInspection >= ?");
        }
        if (paramMap.get("endTime") != null) {
            params[start++] = paramMap.get("endTime");
            sb.append(" and i.FinalInspection < ?");
        }
        if (paramMap.get("minAmount") != null) {
            String amount = paramMap.get("minAmount");
            if (amount.indexOf(".") != -1) {
                params[start++] = paramMap.get("minAmount");
                sb.append(" and i.InvoiceAmount = ?");
            } else {
                params[start++] = paramMap.get("minAmount") + ".%";
                sb.append(" and i.InvoiceAmount like ?");
            }
        }
        if (paramMap.get("contractName") != null) {
            params[start++] = "%" + paramMap.get("contractName") + "%";
            sb.append(" and s.ContractName like ?");
        }

        String orgId = paramMap.get("orgId");
        if (orgId != null) {
            String sql = "SELECT orgcode FROM sys_orgnization b WHERE b.OrgId='" + orgId + "'";
            Query orgquery = salesContractDao.createSQLQuery(sql);
            String orgcode = (String) orgquery.uniqueResult();
            params[start++] = orgcode + "%";
            sb.append(" AND i.Creator IN(SELECT s.staffid FROM sys_staff s ");
            sb.append("LEFT JOIN sys_stafforg o ON o.staffId = s.staffId ");
            sb.append("LEFT JOIN sys_orgnization org ON o.orgId = org.orgId ");
            sb.append("WHERE s.State!='0' AND org.orgCode  LIKE ?) ");
        }

        int totals = getTotalCount(sb.toString(), params);
        if (paramMap.get("sortname") != null) {
            String sortname = paramMap.get("sortname").toString();
            String sortorder = paramMap.get("sortorder").toString();
            sb.append(" ORDER BY i." + sortname + " " + sortorder);
        }
        Query query = getDao().createSQLQuery(sb.toString(), params).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).setFirstResult(startIndex).setMaxResults(pageSize);
        PaginationSupport ps = new PaginationSupport(query.list(), totals);

        return ps;
    }

    public Integer getTotalCount(String sql, Object[] params) {
        sql = "select count(*) from (" + sql + " ) as total";
        return Integer.valueOf(getDao().createSQLQuery(sql, params).list().get(0).toString()).intValue();
    }

    /**
     * <code>getAmountByContractId</code>
     * 得到已开票合同金额
     * @param contractId
     * @param state
     * @return
     * @since   2015年3月23日    guokemenng
     */
    public String getAmountByContractId(Long contractId) {
        String sql = "SELECT SUM(p.`InvoiceAmount`) AS InvoiceAmount FROM `sales_invoice_plan` p WHERE p.`SalesContractId` = ? AND p.`InvoiceStatus` = 'TGSH'";
        Query query = getDao().createSQLQuery(sql, new Object[] { contractId });
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> mapList = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        if (mapList.size() > 0) {
            if (mapList.get(0).get("InvoiceAmount") != null) {
                return mapList.get(0).get("InvoiceAmount").toString();
            } else {
                return "0";
            }
        } else {
            return "0";
        }
    }

    /**
     * <code>getAmountByContractId</code>
     * 得到已申请合同发票金额
     * @param contractId
     * @param state
     * @return
     * @since   2015年3月23日    guokemenng
     */
    public String getAmountByContractId(Long contractId, String proInstId) {
        if (StringUtil.isEmpty(proInstId)) {
            String sql = "SELECT SUM(p.`InvoiceAmount`) AS InvoiceAmount FROM `sales_invoice_plan` p WHERE p.`SalesContractId` = ? AND p.`InvoiceStatus` = 'SH' AND p.`ProcessInstanceId` != ?";
            Query query = getDao().createSQLQuery(sql, new Object[] { contractId, proInstId });
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> mapList = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
            if (mapList.size() > 0) {
                if (mapList.get(0).get("InvoiceAmount") != null) {
                    return mapList.get(0).get("InvoiceAmount").toString();
                } else {
                    return "0";
                }
            } else {
                return "0";
            }
        } else {
            return "0";
        }
    }

    /**查用户Id
     * @param creatorUse
     * @return
     */
    public String findUserId(String creatorUse) {
        String id = getDao().findUserId(creatorUse);
        return id;
    }

    /**
     * @param long1
     * @return
     */
    public String findKeyCode(Long id) {
        String keyCode = getDao().findKeyCode(id);
        return keyCode;
    }

    /**
     * <code>getTaskIdByProInst</code>
     * 根据流程Id获取taskId
     * @param proInstId
     * @return
     * @since   2015年12月30日    chibj
     */
    @SuppressWarnings("unchecked")
    public String getTaskIdByProInst(Long proInstId) {
        Object[] param = new Object[1];
        param[0] = proInstId;
        String sql = "select t.id_ taskId from bpm_process_inst b left join act_ru_task t on b.HI_PROC_INST_ID = t.PROC_INST_ID_ where b.ID_ = ?";
        Query query = null;
        query = getDao().createSQLQuery(sql, param);
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List<Map<String, Object>> mapList = query.list();
        if (mapList != null && mapList.size() > 0) {
            Object taskId = mapList.get(0).get("taskId");
            if (taskId == null || "".equals(taskId)) {
                return "";
            } else {
                return taskId.toString();
            }
        } else {
            return "";
        }
    }

    /**
     * @param id
     * @param allDate
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<SalesInvoicePlanModel> doInvoiceSTR(String id, String tableData) {
        List<SalesInvoicePlanModel> invoiceReturn = new ArrayList<SalesInvoicePlanModel>();

        List<SalesBudgetFunds> fundbuds = new ArrayList<SalesBudgetFunds>();
        List<SalesBudgetLog> logs = new ArrayList<SalesBudgetLog>();
        List<SalesInvoicePlanModel> invoices = new ArrayList<SalesInvoicePlanModel>();

        List<SalesBudgetFunds> fundOld = fundsSalesBudgetDao.getfundOld(id);//历史数据
        Map<Long, Object> oldMap = new HashMap<Long, Object>();
        Iterator<SalesBudgetFunds> old = fundOld.iterator();
        while (old.hasNext()) {//得到原来的发票ID
            SalesBudgetFunds m = old.next();
            oldMap.put(m.getId(), m.getBudgetDate());
        }
        ObjectMapper objectMapper = JacksonJsonMapper.getInstance();
        Map<String, Object> tableDataMap = null;
        Map<Long, Object> newMap = new HashMap<Long, Object>();
        if (tableData != null && tableData != "") {
            try {
                tableDataMap = objectMapper.readValue(tableData, HashMap.class);
            } catch (JsonParseException e) {
                e.printStackTrace();
            } catch (JsonMappingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (tableDataMap != null && tableDataMap.size() > 0) {
                for (String key : tableDataMap.keySet()) {
                    List<Object> gridDataList = (List<Object>) tableDataMap.get(key);
                    List<Map<String, Object>> gridList = new ArrayList<Map<String, Object>>();
                    for (int i = 0; (gridDataList != null && i < gridDataList.size()); i++) {//加了非空判断
                        Map<String, Object> map = CodeUtils.stringToMap(gridDataList.get(i).toString());//格式转换
                        if (map.size() != 0) {
                            gridList.add(map);
                        }
                    }
                    for (int i = 0; i < gridList.size(); i++) {
                        SalesBudgetFunds funds = new SalesBudgetFunds();//存预测表
                        SalesBudgetLog log = new SalesBudgetLog();//存日志
                        SalesInvoicePlanModel invoice = new SalesInvoicePlanModel();//存发票表

                        Map<String, Object> map = gridList.get(i);
                        String invoiceID = null;
                        if (map.get("column5") != null) {
                            invoiceID = map.get("column5").toString();
                        }
                        String flat = map.get("column0").toString();
                        String invoiceAmount = map.get("column1").toString();
                        String invoiceTime = map.get("column2").toString();
                        String invoiceType = map.get("column4").toString();
                        String remark = "";
                        if (map.get("column3") != null) {
                            remark = map.get("column3").toString();
                        }
                        if (invoiceID != null) {//原来有的，修改
                            invoice = this.get(Long.parseLong(invoiceID));
                            funds = fundsSalesBudgetDao.getFunds(Long.parseLong(invoiceID));
                            newMap.put(funds.getId(), invoiceTime);
                            log.setBudgetFundsId(funds.getId());
                            log.setRemark("更新预测发票，金额：" + invoiceAmount + ",时间：" + invoiceTime);
                        } else {//新加的条目
                            long ids = IdentifierGeneratorImpl.generatorLong();
                            funds.setId(ids);
                            funds.setContractId(Long.parseLong(id));
                            SalesContractModel sales = salesContractService.get(Long.parseLong(id));
                            funds.setContractCode(sales.getContractCode());
                            funds.setContractName(sales.getContractName());
                            funds.setUserName(sales.getCreatorName());
                            funds.setSalesInvoiceId(ids);
                            long start = DateUtils.getStartTime();
                            long end = DateUtils.getEndTime();
                            DetachedCriteria detachedCriteria = DetachedCriteria.forClass(SalesBudgetFunds.class);
                            detachedCriteria.add(Restrictions.ge(SalesBudgetFunds.CREATEDATE, new Date(start)));
                            detachedCriteria.add(Restrictions.le(SalesBudgetFunds.CREATEDATE, new Date(end)));
                            detachedCriteria.add(Restrictions.isNotNull(SalesBudgetFunds.BUDGETINVOICE));
                            List<SalesBudgetFunds> salesBudgetFunds = fundsSalesBudgetDao.findByCriteria(detachedCriteria);
                            int serialNum = salesBudgetFunds.size();//流水号
                            serialNum = serialNum + 1;
                            int t = serialNum;
                            String title = "";
                            try {
                                title = DateUtils.convertDateToString(new Date(), "yyyyMMdd") + "-" + t;
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            funds.setTitle(title);
                            log.setBudgetFundsId(ids);
                            log.setRemark("预测发票，金额：" + invoiceAmount + ",时间：" + invoiceTime);

                            invoice.setId(ids);
                            invoice.setInvoiceAmount(new BigDecimal(invoiceAmount));
                            invoice.setSalesContractId(Long.parseLong(id));
                            invoice.setCreateTime(new Date());
                            invoice.setCreator(sales.getCreator());
                            invoice.setInvoiceStatus(SalesContractConstant.CONTRACT_STATE_CG);
                            invoice.setSalesContractName(sales.getContractName());
                            invoice.setSalesContractId(sales.getId());

                        }
                        funds.setInvoiceType(Integer.parseInt(invoiceType));
                        funds.setBudgetDate(DateUtils.convertStringToDate(invoiceTime, "yyyy-MM-dd"));
                        funds.setCreateDate(new Date());
                        funds.setBudgetInvoice(new BigDecimal(invoiceAmount));
                        funds.setRemark(remark);
                        funds.setProcessInstanceId(null);

                        invoice.setInvoiceType(Integer.parseInt(invoiceType));
                        invoice.setInvoiceTime(DateUtils.convertStringToDate(invoiceTime, "yyyy-MM-dd"));
                        invoice.setInvoiceAmount(new BigDecimal(invoiceAmount));
                        invoice.setRemark(remark);
                        invoice.setInvoiceStatus("CG");

                        log.setCreateDate(new Date());

                        fundbuds.add(funds);
                        logs.add(log);
                        invoices.add(invoice);
                        if ("1".equals(flat)) {//选中提交的
                            invoiceReturn.add(invoice);
                        }
                    }
                }
            }
            //=======================是否有删除条目==================================
            Set<Long> oldKey = oldMap.keySet();
            Iterator<Long> key = oldKey.iterator();
            while (key.hasNext()) {
                Long k = key.next();
                if (newMap.get(k) == null) {
                    SalesBudgetFunds fundold = fundsSalesBudgetService.get(k);
                    fundsSalesBudgetDao.delete(fundold);
                    SalesInvoicePlanModel invoice = this.get(fundold.getSalesInvoiceId());
                    getDao().delete(invoice);
                }
            }
        }

        try {
            fundsSalesBudgetDao.saveOrUpdateAll(fundbuds);
            salesBudgetLogDao.saveOrUpdateAll(logs);
            getDao().saveOrUpdateAll(invoices);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return invoiceReturn;
    }

    /**
     * @param procInstId
     * @param idString
     * @return
     */
    public List<SalesInvoicePlanModel> getInvoiceByProcInstId(String procInstId, String idString) {
        List<SalesInvoicePlanModel> invoice = getDao().getInvoiceByProcInstId(procInstId, idString);
        return invoice;
    }

    /**
     * 根据构造好的参数HashMap数据信息，拼接出动态sql语句，到数据库中查询出需要导出的发票数据信息
     * @param paramMap
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<SalesInvoiceExportDto> exportInvoice(Map<String, String> paramMap) {
        //构造好的paramMap中就有查询条件数据
        String startTime = paramMap.get("startTime");
        String endTime = paramMap.get("endTime");
        String invoiceAmount = paramMap.get("minAmount");
        String creatorId = paramMap.get("creatorUse");
        String contractName = paramMap.get("contractName");
        String orgId = paramMap.get("orgId");

        StringBuffer sb = new StringBuffer();
        sb.append("SELECT sc.id,sc.`ContractCode`,sc.`ContractName`,sc.`ContractAmount`,");
        sb.append("t1.alreadyAmount,sip.`InvoiceAmount`,sc.`CreatorName`,sip.`CreateTime`,");
        sb.append("sc.orgCode FROM sales_contract sc ");
        sb.append("INNER JOIN sales_invoice_plan sip ON sc.`id`=sip.`SalesContractId` ");
        sb.append("INNER JOIN (SELECT SUM(InvoiceAmount) alreadyAmount, SalesContractId ");
        sb.append("FROM sales_invoice_plan GROUP BY SalesContractId) t1 ON sc.`id`=t1.salesContractid");
        sb.append(" where (sip.InvoiceStatus='TGSH' OR sip.InvoiceStatus='SH') ");
        //动态拼接where条件
        //用于封装预编译查询sql语句的参数信息
        Object[] queryArr = new Object[paramMap.size()];
        int index = 0;
        if (!StringUtils.isNullOrEmpty(startTime)) {
            queryArr[index++] = startTime;
            //时间范围是最终审核通过的时间，而不是createTime
            //如果使用between ? and ?  是两个区间都包括的，然而这不满足需求
            sb.append("and sip.FinalInspection >= ? ");
        }
        if (!StringUtils.isNullOrEmpty(endTime)) {
            queryArr[index++] = endTime;
            sb.append("and sip.FinalInspection < ? ");
        }
        if (!StringUtils.isNullOrEmpty(invoiceAmount)) {
            queryArr[index++] = invoiceAmount;
            sb.append("and sip.invoiceAmount=? ");
        }
        if (!StringUtils.isNullOrEmpty(creatorId)) {
            queryArr[index++] = creatorId;
            sb.append("and sip.Creator=? ");
        }
        if (!StringUtils.isNullOrEmpty(contractName)) {
            queryArr[index++] = "%" + contractName + "%";
            sb.append("and sc.contractName like ? ");
        }
        String orgName = "";
        boolean flag = false;
        if (!StringUtils.isNullOrEmpty(orgId)) {
            String sql = "SELECT OrgCode,OrgName FROM sys_orgnization b WHERE b.OrgId='" + orgId + "'";

            List<Object> orgList = salesContractDao.createSQLQuery(sql).list();
            Object[] orgArr = (Object[]) orgList.get(0);
            String orgCode = orgArr[0] != null ? orgArr[0].toString() : null;
            orgName = orgArr[1] != null ? orgArr[1].toString() : null;

            queryArr[index++] = orgCode + "%";
            sb.append(" AND sip.Creator IN(SELECT s.staffid FROM sys_staff s ");
            sb.append("LEFT JOIN sys_stafforg o ON o.staffId = s.staffId ");
            sb.append("LEFT JOIN sys_orgnization org ON o.orgId = org.orgId ");
            sb.append("WHERE s.State!='0' AND org.orgCode  LIKE ?) ");
            flag = true;
        }

        String hql = sb.toString();
        //如果查询出来多条记录，则List中封装的是Object[]类型的数据
        List<Object> items = getDao().createSQLQuery(hql, queryArr).list();

        return buildExportDto(items, flag, orgId);
    }

    /**
     * 构造用于导出发票信息的数据传输对象SalesInvoiceExportDto
     * @param items
     * @param flag
     * @param orgId
     * @return
     */
    public List<SalesInvoiceExportDto> buildExportDto(List<Object> items, boolean flag, String orgId) {
        List<SalesInvoiceExportDto> exportDtoList = new ArrayList<SalesInvoiceExportDto>();
        for (int x = 0; x < items.size(); x++) {
            SalesInvoiceExportDto exportDto = new SalesInvoiceExportDto();
            Object[] results = (Object[]) items.get(x);
            Long id = Long.parseLong(results[0] != null ? results[0].toString() : null);
            exportDto.setId(id);
            //合同编码
            String contractCode = results[1] != null ? results[1].toString() : null;
            exportDto.setContractCode(contractCode);
            //合同名称
            exportDto.setContractName(results[2] != null ? results[2].toString() : null);
            //合同金额
            String cAmount = results[3] != null ? results[3].toString() : null;
            exportDto.setContractAmount(new BigDecimal(cAmount));
            //已开发票金额
            String alreadyAmount = results[4] != null ? results[4].toString() : null;
            exportDto.setAlreadyAmount(new BigDecimal(alreadyAmount));
            //本次所开发票金额
            String invoiceAmount2 = results[5] != null ? results[5].toString() : null;
            exportDto.setInvoiceAmount(new BigDecimal(invoiceAmount2));
            //客户经理姓名
            String creatorName = results[6] != null ? results[6].toString() : null;
            exportDto.setCreatorName(creatorName);
            try {
                //开发票日期
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date date = sdf.parse(results[7] != null ? results[7].toString() : null);
                exportDto.setCreateTime(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            //根据orgCode获取其所在的team
            String orgCode = results[8] != null ? results[8].toString() : null;
            if (!flag) { //如果用户没有输入"部门名称"这个查询条件，那么按照这里的方法来获取部门，从而导出
                if (orgCode != null) {
                    String strSql = "SELECT OrgName FROM sys_orgnization WHERE orgcode='" + orgCode + "'";
                    String orgName2 = (String) salesContractDao.createSQLQuery(strSql).uniqueResult();
                    exportDto.setDeptName(orgName2);
                }
            }
            exportDtoList.add(exportDto);
        }
        return exportDtoList;
    }

}
