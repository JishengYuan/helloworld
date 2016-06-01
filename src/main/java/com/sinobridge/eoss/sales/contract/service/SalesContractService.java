/*
 * FileName: SalesContractService.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.sales.contract.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import net.sf.ehcache.Ehcache;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.sinobridge.base.core.hibernate.id.IdentifierGeneratorImpl;
import com.sinobridge.base.core.service.DefaultBaseService;
import com.sinobridge.base.core.utils.JacksonJsonMapper;
import com.sinobridge.base.core.utils.PaginationSupport;
import com.sinobridge.base.core.utils.UploadUtil;
import com.sinobridge.eoss.bpm.service.process.ProcessInstService;
import com.sinobridge.eoss.bpm.service.process.ProcessService;
import com.sinobridge.eoss.business.order.constant.BusinessOrderContant;
import com.sinobridge.eoss.business.order.dao.BusinessOrderDao;
import com.sinobridge.eoss.business.order.dao.BusinessPayOrderDao;
import com.sinobridge.eoss.business.order.model.BusinessOrderModel;
import com.sinobridge.eoss.business.order.utils.SendEmailUtils;
import com.sinobridge.eoss.business.projectmanage.service.BasePartnerInfoService;
import com.sinobridge.eoss.business.projectmanage.service.BaseProductTypeService;
import com.sinobridge.eoss.sales.contract.constant.SalesContractConstant;
import com.sinobridge.eoss.sales.contract.dao.SalesContractDao;
import com.sinobridge.eoss.sales.contract.dao.SalesContractStatusDao;
import com.sinobridge.eoss.sales.contract.dao.SalesProductDao;
import com.sinobridge.eoss.sales.contract.dao.SalesReceivePlanDao;
import com.sinobridge.eoss.sales.contract.dto.SalesContractDto;
import com.sinobridge.eoss.sales.contract.dto.SalesContractInfoAndStatus;
import com.sinobridge.eoss.sales.contract.model.SalesContractModel;
import com.sinobridge.eoss.sales.contract.model.SalesContractProductModel;
import com.sinobridge.eoss.sales.contract.model.SalesContractReceivePlanModel;
import com.sinobridge.eoss.sales.contract.model.SalesContractStatusModel;
import com.sinobridge.eoss.sales.contract.utils.CodeUtils;
import com.sinobridge.eoss.sales.contract.utils.DateUtils;
import com.sinobridge.eoss.sales.contractchange.dao.ContractProductSnapShootDao;
import com.sinobridge.eoss.sales.contractchange.dao.ContractRecivePlanSnapShootDao;
import com.sinobridge.eoss.sales.contractchange.dao.ContractSnapShootDao;
import com.sinobridge.eoss.sales.contractchange.model.ContractProductSnapShootModel;
import com.sinobridge.eoss.sales.contractchange.model.ContractRecivePlanSnapShootModel;
import com.sinobridge.eoss.sales.contractchange.model.ContractSnapShootModel;
import com.sinobridge.eoss.sales.invoice.dao.SalesInvoiceDao;
import com.sinobridge.eoss.sales.invoice.model.SalesInvoicePlanModel;
import com.sinobridge.eoss.sales.stockupCost.model.StockUpContractCostModel;
import com.sinobridge.eoss.sales.stockupCost.service.StockUpContractCostProductService;
import com.sinobridge.eoss.sales.stockupCost.service.StockUpContractCostService;
import com.sinobridge.eoss.salesfundsCost.dao.FundsSalesBudgetDao;
import com.sinobridge.eoss.salesfundsCost.dao.SalesBudgetLogDao;
import com.sinobridge.eoss.salesfundsCost.model.FundsSalesContract;
import com.sinobridge.eoss.salesfundsCost.model.FundsSalesLog;
import com.sinobridge.eoss.salesfundsCost.model.SalesBudgetFunds;
import com.sinobridge.eoss.salesfundsCost.model.SalesBudgetLog;
import com.sinobridge.eoss.salesfundsCost.service.FundsSalesContractService;
import com.sinobridge.eoss.salesfundsCost.service.FundsSalesLogService;
import com.sinobridge.systemmanage.model.SysPosition;
import com.sinobridge.systemmanage.model.SysStaff;
import com.sinobridge.systemmanage.model.SysStaffPos;
import com.sinobridge.systemmanage.service.SysDomainService;
import com.sinobridge.systemmanage.service.SysStaffService;
import com.sinobridge.systemmanage.util.Constants;
import com.sinobridge.systemmanage.util.Global;
import com.sinobridge.systemmanage.util.SQLUtil;
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
public class SalesContractService extends DefaultBaseService<SalesContractModel, SalesContractDao> {

    @Autowired
    private ProcessService processService;
    @Autowired
    private SysStaffService sysStaffService;
    @Autowired
    private SalesReceivePlanDao salesReceivePlanDao;
    @Autowired
    private SalesProductDao salesProductDao;
    @Autowired
    private SalesContractStatusDao salesContractStatusDao;
    @Autowired
    private ContractProductSnapShootDao contractProductSnapShootDao;
    @Autowired
    private ContractRecivePlanSnapShootDao contractRecivePlanSnapShootDao;
    @Autowired
    private ContractSnapShootDao contractSnapShootDao;

    @Autowired
    private SysDomainService sysDomainService;

    @Autowired
    BasePartnerInfoService partnerInfoService;
    @Autowired
    BaseProductTypeService productTypeService;

    @Autowired
    BusinessOrderDao orderDao;

    @Autowired
    BusinessPayOrderDao payOrderDao;

    @Autowired
    ProcessInstService processInstService;
    @Autowired
    private SalesContractProductService salesContractProductService;

    @Autowired
    StockUpContractCostProductService stockUpContractCostProductService;

    @Autowired
    StockUpContractCostService stockUpContractCostService;

    @Autowired
    private FundsSalesContractService fundsSalesContractService;
    @Autowired
    private FundsSalesLogService fundsSalesLogService;

    @Autowired
    private SalesInvoiceDao salesInvoiceDao;
    @Autowired
    private FundsSalesBudgetDao fundsSalesBudgetDao;
    @Autowired
    private SalesBudgetLogDao salesBudgetLogDao;

    private final static Ehcache systemUserCache = Constants.CACHE_MANAGER.getEhcache("systemUser");

    /**
     * <code>saveOrUpdate</code>
     * @param invoicePlans 
     *
     * @param tableData
     * @since 2014年5月19日 3unshine
     */
    public void save(SalesContractModel salesContractModel, List<SalesContractReceivePlanModel> receivePlans, List<SalesContractProductModel> salesContractProducts, SystemUser systemUser, List<SalesInvoicePlanModel> invoicePlans) throws Exception {
        if (salesContractModel.getContractState().equals("SH")) {
            //创建工单
            String procDefKey = SalesContractConstant.CONTRACT_PROCDEFKEY;
            creatProcInst(salesContractModel, systemUser, procDefKey);
            SalesContractStatusModel salesContractStatusModel = new SalesContractStatusModel();
            salesContractStatusModel.setId(IdentifierGeneratorImpl.generatorLong());
            salesContractStatusModel.setReciveStatus(SalesContractConstant.CONTRACT_RECIVE_INITSTATE);
            salesContractStatusModel.setInvoiceStatus(SalesContractConstant.CONTRACT_INVOICE_SP);
            salesContractStatusModel.setOrderStatus(SalesContractConstant.CONTRACT_ORDER_INITSTATE);
            salesContractStatusModel.setCachetStatus(SalesContractConstant.CONTRACT_CACHET_INITSTATE);
            salesContractStatusModel.setChangeStatus(SalesContractConstant.CONTRACT_CHANGE_INITSTATE);
            salesContractStatusModel.setSaleContractId(salesContractModel.getId());
            salesContractStatusModel.setCreateTime(new Date());
            //创建合同状态
            salesContractStatusDao.save(salesContractStatusModel);
        }
        getDao().save(salesContractModel);
        salesReceivePlanDao.saveOrUpdateAll(receivePlans);
        salesProductDao.saveOrUpdateAll(salesContractProducts);
        salesInvoiceDao.saveOrUpdateAll(invoicePlans);
    }

    /**
     * <code>update</code>
     * @param invoicePlans 
     * @param tableData
     * @since 2014年5月19日 3unshine
     */
    public void update(SalesContractModel salesContractModel, List<SalesContractReceivePlanModel> receivePlans, List<SalesContractProductModel> salesContractProducts, SystemUser systemUser, List<SalesInvoicePlanModel> invoicePlans) {
        long salesContractModelId = salesContractModel.getId();
        //删除原有产品记录
        salesReceivePlanDao.delReceivePlansByContractId(salesContractModelId);
        Set<SalesContractReceivePlanModel> newReceivePlans = salesReceivePlanDao.saveAll(receivePlans);
        salesContractModel.setSalesContractReceivePlans(newReceivePlans);
        salesProductDao.delSalesProductsByContractId(salesContractModelId);
        salesProductDao.saveOrUpdateAll(salesContractProducts);
        salesInvoiceDao.delSalesInvoiceByContractId(salesContractModelId);
        getDao().flush();
        SalesContractStatusModel contractStatusModel = salesContractStatusDao.findContractStatusByContractId(salesContractModelId);
        if (salesContractModel.getContractState().equals(SalesContractConstant.CONTRACT_STATE_SH)) {

            Map<String, Object> map = getSaleStateById(salesContractModelId);
            if (map.get("NAME_") == null) {
                String procDefKey = SalesContractConstant.CONTRACT_PROCDEFKEY;
                creatProcInst(salesContractModel, systemUser, procDefKey);
            } else if (map.get("NAME_").toString().equals("重新提交")) {
                //                String procDefKey = SalesContractConstant.CONTRACT_PROCDEFKEY;
                //                creatProcInst(salesContractModel, systemUser, procDefKey);
            }

            if (contractStatusModel == null) {
                SalesContractStatusModel salesContractStatusModel = new SalesContractStatusModel();
                salesContractStatusModel.setId(IdentifierGeneratorImpl.generatorLong());
                salesContractStatusModel.setReciveStatus(SalesContractConstant.CONTRACT_RECIVE_INITSTATE);
                salesContractStatusModel.setInvoiceStatus(SalesContractConstant.CONTRACT_INVOICE_SP);
                salesContractStatusModel.setOrderStatus(SalesContractConstant.CONTRACT_ORDER_INITSTATE);
                salesContractStatusModel.setCachetStatus(SalesContractConstant.CONTRACT_CACHET_INITSTATE);
                salesContractStatusModel.setChangeStatus(SalesContractConstant.CONTRACT_CHANGE_INITSTATE);
                salesContractStatusModel.setSaleContractId(salesContractModel.getId());
                salesContractStatusModel.setCreateTime(new Date());
                //创建合同状态
                salesContractStatusDao.save(salesContractStatusModel);
            }
        }
        getDao().merge(salesContractModel);
    }

    public void deletes(String[] ids) {
        List<SalesContractModel> delsalesContracts = new ArrayList<SalesContractModel>();
        //        List<SalesContractReceivePlanModel> delReceivePlans = new ArrayList<SalesContractReceivePlanModel>();
        for (String id : ids) {
            long trueId = Long.parseLong(id);
            SalesContractModel salesContract = getDao().get(trueId);
            delsalesContracts.add(salesContract);

            //            DetachedCriteria detachedCriteria = DetachedCriteria.forClass(SalesContractReceivePlanModel.class);
            //            detachedCriteria.add(Restrictions.eq("salesContract.id", trueId));
            //            detachedCriteria.createAlias("salesContract", SalesContractReceivePlanModel.CONTACTID);
            //            List<SalesContractReceivePlanModel> oldReceivePlans = salesReceivePlanDao.findByCriteria(detachedCriteria);
            //            delReceivePlans.addAll(oldReceivePlans);

            //产品
            Set<SalesContractProductModel> productSet = salesContract.getSalesContractProductModel();
            Iterator<SalesContractProductModel> it = productSet.iterator();
            while (it.hasNext()) {
                SalesContractProductModel pm = it.next();
                if (pm.getRelateDeliveryProductId() != null) {
                    SalesContractProductModel relateDeliveryProduct = salesProductDao.get(pm.getRelateDeliveryProductId());
                    int originalNum = relateDeliveryProduct.getSurplusNum() - pm.getQuantity();
                    relateDeliveryProduct.setSurplusNum(originalNum);
                    salesProductDao.update(relateDeliveryProduct);
                }
                //                salesProductDao.delete(pm);
            }

            //状态
            orderDao.deleteContractOrder(Long.parseLong(id));
        }
        getDao().deleteAll(delsalesContracts);
        //        if (delReceivePlans.size() > 0)
        //            salesReceivePlanDao.deleteAll(delReceivePlans);
    }

    /**
     * @param id
     * @return
     */
    public SalesContractModel detail(String id) {
        SalesContractModel a = new SalesContractModel();
        return a;
    }

    /**
     * <code>creatProcInst</code>
     * 创建工单
     * @param tableData
     * @param tableData
     * @since 2014年6月19日 3unshine
     */
    public long creatProcInst(SalesContractModel salesContractModel, SystemUser systemUser, String procDefKey) {
        Map<String, Object> variableMap = new HashMap<String, Object>();
        long processInstanceId = processService.nextValId();
        salesContractModel.setProcessInstanceId(processInstanceId);
        variableMap.put("salesContractModel", salesContractModel);
        //插入流程变量合同是否达到转发限额的变量
        variableMap.put(SalesContractConstant.CONTRACT_AMOUNT_KEY, salesContractModel.getContractAmount());

        SysStaff staff = sysStaffService.get(systemUser.getUserId());
        List<SysStaffPos> posList = staff.getSysStaffPoses();
        Short posType = 100;
        for (SysStaffPos pos : posList) {
            SysPosition po = pos.getSysPosition();
            Short p = po.getPosType();
            if (p > posType) {
                posType = p;
            }
        }

        //只要岗位类型不是400 或者组织是金融事业部的 的就走另外一条线
        if (posType != 400 || "110110".equals(systemUser.getSysOrgnization().getOrgCode())) {
            variableMap.put(SalesContractConstant.IS_DEPT, 1);
        } else {
            variableMap.put(SalesContractConstant.IS_DEPT, 0);
        }

        Long[] procInstId = processService.create(processInstanceId, salesContractModel.getContractName() + "[￥" + salesContractModel.getContractAmount() + "]", systemUser.getUserName(), procDefKey, 1, variableMap, null, null, salesContractModel.getContractCode());
        System.out.println("创建工单成功：" + procInstId[0]);
        return procInstId[0];
    }

    /**
     * <code>handleFlow</code>
     * 处理工单
     * @param flowFlag 流程节点标示
     * @param salesContractModel 销售合同实体(重新提交订单时使用)
     * @param receivePlans 收款计划实体(重新提交订单时使用)
     * @param salesContractProducts 销售合同产品实体(重新提交订单时使用)
     * @param systemUser 操作人实体
     * @param taskId 任务ID
     * @param isAgree 是否同意
     * @param remark 审批意见
     * @throws Exception 
     * @since 2014年6月23日 3unshine 
     */
    @SuppressWarnings("unchecked")
    public void handleFlow(String flowFlag, SalesContractModel salesContractModel, List<SalesContractReceivePlanModel> receivePlans, List<SalesContractProductModel> salesContractProducts, SystemUser systemUser, String procInstId, String taskId, int isAgree, String remark, List<SalesInvoicePlanModel> invoicePlans) throws Exception {
        String userId = systemUser.getUserName();
        Map<String, Object> variableMap = new HashMap<String, Object>();
        SalesContractModel saleAll = this.get(salesContractModel.getId());
        if ("CXTJ".equals(flowFlag)) {
            //判断更新是否正确，正确则处理表单
            update(salesContractModel, receivePlans, salesContractProducts, systemUser, invoicePlans);
            salesInvoiceDao.saveOrUpdateAll(invoicePlans);
            //插入流程变量合同是否为重新提交
            boolean isResubmit = true;
            if (salesContractModel.getContractState().equals(SalesContractConstant.CONTRACT_STATE_CG))
                isResubmit = false;
            //更新工单标题
            if (isResubmit) {
                String name = salesContractModel.getContractName() + "[￥" + salesContractModel.getContractAmount() + "]";
                getDao().updateWorkName(name, salesContractModel.getProcessInstanceId());
            }
            variableMap.put(SalesContractConstant.IS_RE_SUBMIT, isResubmit);
            //因为是重新提交原流程变量里面的salesInvoicePlanList要替换掉
            variableMap.put("salesContractModel", salesContractModel);
            //因为是重新提交原流程变量里面合同是否达到转发限额的变量要替换掉
            variableMap.put(SalesContractConstant.CONTRACT_AMOUNT_KEY, salesContractModel.getContractAmount());
            processService.handle(taskId, userId, variableMap, null, null);
        } else {
            //插入流程变量合同是否达到转发限额的变量
            BigDecimal contractAmount = new BigDecimal(processService.getVariable(taskId, SalesContractConstant.CONTRACT_AMOUNT_KEY) + "");

            if ("HYYXZJSP".equals(flowFlag) && isAgree == 1 && (contractAmount.compareTo(SalesContractConstant.CONTRACT_AMOUNT) == 1 ? true : false)) {
                Long procInstId4send = Long.parseLong(procInstId);
                String currentUserId = systemUser.userId;
                String[] sendTo = new String[1];
                if (systemUser.userName.equals("zhoushu") || systemUser.userName.equals("jianghb")) {
                    sendTo[0] = systemUser.userName;
                } else {
                    String userName = sysStaffService.getDeptManager(systemUser.userName);
                    sendTo[0] = userName;
                }
                processService.send(procInstId4send, currentUserId, sendTo);
            }
            if ("SWJLSP".equals(flowFlag) && isAgree == 1) {
                //更新商务经理添加订单处理人
                getDao().updateOrderProcessor(salesContractModel);
            }
            if ("SWZGSP".equals(flowFlag) && isAgree == 1) {
                //更新合同的状态为审批通过
                SalesContractModel contractModel = this.get(salesContractModel.getId());
                contractModel.setContractState(SalesContractConstant.CONTRACT_STATE_TGSH);
                //                getDao().updateContractState(salesContractModel);
                //                contractModel.setCloseTime(new Date());
                //王佺审批通过 合同生效
                if (contractModel.getIsChanged() != 1) {
                    if (contractModel.getSalesStartDate() == null) {
                        contractModel.setSalesStartDate(new Date());//添加合同生效时间
                    }
                    int flat = getDao().findsaleProduct(salesContractModel.getId());
                    if (flat == 1) {
                        getDao().updateContractOrder(salesContractModel.getId());//有关联备货的订单状态置为采购中

                    }

                    //增加发票，回款的预测和日志2016-04-06wangya-----------------------------------------------------------------
                    List<SalesBudgetFunds> budgets = new ArrayList<SalesBudgetFunds>();
                    List<SalesBudgetLog> budgetlogs = new ArrayList<SalesBudgetLog>();
                    long start = DateUtils.getStartTime();
                    long end = DateUtils.getEndTime();
                    DetachedCriteria detachedCriteria = DetachedCriteria.forClass(SalesBudgetFunds.class);
                    detachedCriteria.add(Restrictions.ge(SalesBudgetFunds.CREATEDATE, new Date(start)));
                    detachedCriteria.add(Restrictions.le(SalesBudgetFunds.CREATEDATE, new Date(end)));
                    detachedCriteria.add(Restrictions.isNotNull(SalesBudgetFunds.BUDGETINVOICE));
                    List<SalesBudgetFunds> salesBudgetFunds = fundsSalesBudgetDao.findByCriteria(detachedCriteria);
                    int serialNum = salesBudgetFunds.size();//流水号

                    //添加发票预测
                    invoicePlans = getDao().getSalesInvoicePlan(salesContractModel.getId());
                    Iterator<SalesInvoicePlanModel> plans = invoicePlans.iterator();
                    while (plans.hasNext()) {
                        SalesInvoicePlanModel p = plans.next();
                        SalesBudgetFunds b = new SalesBudgetFunds();
                        long id = IdentifierGeneratorImpl.generatorLong();
                        b.setId(id);
                        b.setBudgetDate(p.getInvoiceTime());
                        b.setBudgetInvoice(p.getInvoiceAmount());
                        b.setContractCode(saleAll.getContractCode());
                        b.setContractId(saleAll.getId());
                        b.setContractName(saleAll.getContractName());
                        b.setCreateDate(new Date());
                        b.setInvoiceType(p.getInvoiceType());
                        b.setUserName(saleAll.getCreatorName());
                        b.setSalesInvoiceId(p.getId());
                        b.setRemark(p.getRemark());
                        serialNum = serialNum + 1;
                        int t = serialNum;
                        String title = DateUtils.convertDateToString(new Date(), "yyyyMMdd") + "-" + t;
                        b.setTitle(title);
                        budgets.add(b);
                        SalesBudgetLog log = new SalesBudgetLog();

                        log.setBudgetFundsId(id);
                        log.setCreateDate(new Date());
                        log.setRemark("预测发票，金额：" + p.getInvoiceAmount() + "，时间：" + p.getInvoiceTime());
                        budgetlogs.add(log);
                    }

                    //添加回款预测
                    DetachedCriteria detachedCriterias = DetachedCriteria.forClass(SalesBudgetFunds.class);
                    detachedCriterias.add(Restrictions.ge(SalesBudgetFunds.CREATEDATE, new Date(start)));
                    detachedCriterias.add(Restrictions.le(SalesBudgetFunds.CREATEDATE, new Date(end)));
                    detachedCriterias.add(Restrictions.isNotNull(SalesBudgetFunds.BUDGETRECEIVE));
                    List<SalesBudgetFunds> salesBudgetFundss = fundsSalesBudgetDao.findByCriteria(detachedCriteria);
                    int serial = salesBudgetFundss.size();//流水号
                    Set<SalesContractReceivePlanModel> receives = saleAll.getSalesContractReceivePlans();
                    Iterator<SalesContractReceivePlanModel> receive = receives.iterator();
                    while (receive.hasNext()) {
                        SalesContractReceivePlanModel p = receive.next();
                        SalesBudgetFunds b = new SalesBudgetFunds();
                        long id = IdentifierGeneratorImpl.generatorLong();
                        b.setId(id);
                        b.setBudgetDate(p.getPlanedReceiveDate());
                        b.setBudgetReceive(p.getPlanedReceiveAmount());
                        b.setContractCode(saleAll.getContractCode());
                        b.setContractId(saleAll.getId());
                        b.setContractName(saleAll.getContractName());
                        b.setCreateDate(new Date());
                        b.setUserName(saleAll.getCreatorName());
                        b.setSalesReceiveId(p.getId());
                        b.setRemark(p.getRemark());
                        serial = serial + 1;
                        int t = serialNum;
                        String title = DateUtils.convertDateToString(new Date(), "yyyyMMdd") + "-" + t;
                        b.setTitle(title);
                        budgets.add(b);
                        SalesBudgetLog log = new SalesBudgetLog();

                        log.setBudgetFundsId(id);
                        log.setCreateDate(new Date());
                        log.setRemark("预测回款，金额：" + p.getPlanedReceiveAmount() + "，时间：" + p.getPlanedReceiveDate());
                        budgetlogs.add(log);
                    }
                    fundsSalesBudgetDao.saveOrUpdateAll(budgets);
                    salesBudgetLogDao.saveOrUpdateAll(budgetlogs);

                    //增加发票，回款的预测和日志完------------------------------------------------------------------

                    //************************王总审批通过后，若此合同关联了备货合同，生成中间数据，计算备货部分的资金占用成本 Start************

                    if (salesContractModel.getContractType() != 9000) {
                        //通过的合同不是备货合同，同时此合同关联的备货合同，其关联的产品下过单，并进行过付款。
                        StringBuffer sb2 = new StringBuffer();
                        sb2.append("select sc.id,sc.contractName,sc.contractCode,sc.contractAmount,sc.contractState,cs.orderStatus,cs.reciveStatus,cs.cachetStatus,cs.invoiceStatus,cs.changeStatus,sc.isChanged,sc.contractType,sc.createTime,sc.creator,");
                        sb2.append("sc.creatorName,bo.id orderId,bo.orderCode orderCode,bo.OrderAmount orderAmount,bo.creatorId orderProcessor,bp.saleContractId bhSaleContractId,(select contractCode from sales_contract where id=bp.SaleContractId) bhSaleContractCode,sc.salesEndDate salesEndDate ");
                        sb2.append("from sales_contract sc,sales_contract_product cp,Sales_Contract_Status cs,business_order bo,business_order_product bp ");
                        sb2.append("where sc.id=cp.SaleContractId and sc.id=cs.SaleContractId and cp.RelateDeliveryProductId=bp.salesContractProductId ");
                        sb2.append("and bo.id=bp.OrderId  and cp.relateDeliveryProductId is not null and bo.OrderStatus='TGSP' and sc.id=" + salesContractModel.getId());
                        sb2.append(" and (select sum(payplan.Amount) from business_pay_apply apply,business_payment_plan payplan");
                        sb2.append(" where apply.id=payplan.payOrderId and payplan.orderId=bo.id and apply.PlanStatus=10)>0 ");
                        sb2.append(" GROUP BY bp.saleContractId ");

                        List<Map<String, Object>> mapList2 = getDao().createSQLQuery(sb2.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
                        if (mapList2.size() > 0) {
                            List<StockUpContractCostModel> costList = new ArrayList<StockUpContractCostModel>();
                            //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            for (Map<String, Object> map : mapList2) {
                                //判断备货是否在funds_salescontract表中存过
                                StringBuffer sbt = new StringBuffer();
                                sbt.append("SELECT * FROM `funds_salescontract` s WHERE s.`contractCode`='" + map.get("bhSaleContractCode").toString() + "' ");
                                List<Map<String, Object>> funds = getDao().createSQLQuery(sbt.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
                                if (funds.size() == 0) {//没有则插入值
                                    FundsSalesContract fundsSales = new FundsSalesContract();
                                    SalesContractModel sales = this.get(Long.parseLong(map.get("bhSaleContractId").toString()));
                                    fundsSales.setContractCode(sales.getContractCode());
                                    fundsSales.setContractAmount(sales.getContractAmount());
                                    fundsSales.setContractName(sales.getContractName());
                                    fundsSales.setCreatorName(sales.getCreatorName());
                                    fundsSales.setSalesStartDate(sales.getSalesStartDate());
                                    fundsSales.setBhflag("1");//后期插入标志位
                                    //查询付款总金额
                                    StringBuffer sbs = new StringBuffer();
                                    sbs.append("SELECT SUM(m.amount) a ,SUM(realAmount) ra FROM ( SELECT CAST(t.subtotal/o.`OrderAmount`*IFNULL(a.payAmount,0) AS DECIMAL(11,2)) amount, ");
                                    sbs.append("CAST(t.subtotal/o.`OrderAmount`*IFNULL(a.realPayMount,0) AS DECIMAL(11,2)) realAmount,t.SaleContractId FROM ");
                                    sbs.append("(SELECT p.`OrderId` oid,p.`SaleContractId`,SUM(p.`SubTotal`) subtotal  ");
                                    sbs.append("FROM business_order_product p WHERE p.`SaleContractId`='" + sales.getId() + "' GROUP BY p.`OrderId` ) t ");
                                    sbs.append("LEFT JOIN (SELECT l.`OrderId`,SUM(l.`Amount`) payAmount,SUM(l.`RealPayAmount`) realPayMount ");
                                    sbs.append("FROM business_payment_plan l,business_pay_apply a WHERE a.`id` = l.`PayOrderId` ");
                                    sbs.append("AND a.`PlanStatus` = '10' GROUP BY l.`OrderId`)a ON a.OrderId=t.oid LEFT JOIN business_order o ON o.`id`=t.oid ");
                                    sbs.append(")m GROUP BY m.SaleContractId ");
                                    List<Map<String, Object>> cost = getDao().createSQLQuery(sbs.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
                                    BigDecimal amount = new BigDecimal(0.00);
                                    if (cost.size() > 0) {
                                        if (!"0.00".equals(cost.get(0).get("a").toString())) {
                                            amount = new BigDecimal(cost.get(0).get("a").toString());
                                        }
                                        if (!"0.00".equals(cost.get(0).get("ra").toString())) {//美金
                                            amount = new BigDecimal(cost.get(0).get("ra").toString());
                                        }
                                    }
                                    fundsSales.setBusinessCost(amount);
                                    fundsSalesContractService.saveOrUpdate(fundsSales);
                                    if (SendEmailUtils.isSend())
                                        processService.sendEmail("系统提醒:备货合同成本核对", "合同:" + sales.getCreatorName() + "[" + sales.getContractCode() + "]为备货合同，成本为：" + amount + "，其成本为人工添加，需进行核对。", null, null, "tianj", null, null);
                                }

                                //插入备货分解表主表内容
                                StringBuffer sbtt = new StringBuffer();
                                sbtt.append("SELECT * FROM stockup_contractcost s WHERE s.`ContractCode`='" + map.get("contractCode").toString() + "' and  s.doState='0' ");
                                List<Map<String, Object>> stock = getDao().createSQLQuery(sbtt.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
                                if (stock.size() == 0) {
                                    StockUpContractCostModel contractCostModel = new StockUpContractCostModel();
                                    long id = IdentifierGeneratorImpl.generatorLong();
                                    contractCostModel.setId(id);
                                    contractCostModel.setContractName(map.get("contractName").toString());
                                    contractCostModel.setContractCode(map.get("contractCode").toString());
                                    contractCostModel.setContractAmount(new BigDecimal(map.get("contractAmount").toString()));
                                    contractCostModel.setCreatorName(map.get("creatorName").toString());
                                    contractCostModel.setOrderProcessor(map.get("orderProcessor").toString());
                                    contractCostModel.setSalesContractId(map.get("id").toString());
                                    contractCostModel.setDoState("0"); //确认状态，0:未确认,1:已确认
                                    costList.add(contractCostModel);
                                }
                            }
                            List<StockUpContractCostModel> costs = new ArrayList<StockUpContractCostModel>();
                            Map<String, String> maps = new HashMap<String, String>();
                            for (StockUpContractCostModel cost : costList) {
                                if (!cost.getContractCode().equals(maps.get(cost.getContractCode()))) {
                                    costs.add(cost);
                                }
                                maps.put(cost.getContractCode(), cost.getContractCode());
                            }
                            stockUpContractCostService.saveOrUpdateAll(costs);
                        }
                    }
                    //************************王总审批通过后，若此合同关联了备货合同，生成中间数据，计算备货部分的资金占用成本 End************//

                } else {
                    if (SendEmailUtils.isSend()) {
                        //变更的合同变更审批通过后给运营中心发邮件
                        processService.sendEmail("系统提醒:合同审批通过", "合同:" + contractModel.getContractName() + "[" + contractModel.getContractCode() + "]变更审批提交后审批通过", null, null, "tianj", null, null);
                        processService.sendEmail("系统提醒:合同审批通过", "合同:" + contractModel.getContractName() + "[" + contractModel.getContractCode() + "]变更审批提交后审批通过", null, null, "luanpl", null, null);
                    }
                }
                getDao().update(contractModel);
                getDao().flush();
                //给商务发邮件
                String buyer = contractModel.getOrderProcessor();
                if (!StringUtil.isEmpty(buyer)) {
                    String[] buyers = buyer.split(",");
                    for (String s : buyers) {
                        if (!StringUtil.isEmpty(s)) {
                            processService.sendEmail("系统提醒:合同审批通过", "合同:" + salesContractModel.getContractName() + "[" + salesContractModel.getContractCode() + "]审批通过", null, null, s, null, null);
                        }
                    }
                }

                //找出关联备货的合同并给运营中心发邮件

                StringBuilder sb = new StringBuilder();
                sb.append("SELECT * FROM `sales_contract` s WHERE s.`id` IN ( ");
                sb.append("SELECT p.`SaleContractId` FROM `sales_contract_product` p WHERE p.`id` IN ( ");
                sb.append("SELECT pr.RelateDeliveryProductId FROM sales_contract_product pr WHERE pr.`SaleContractId` = ? AND pr.RelateDeliveryProductId IS NOT NULL GROUP BY pr.`SaleContractId` ");
                sb.append(") GROUP BY p.`SaleContractId`) ");

                Query query = getDao().createSQLQuery(sb.toString(), new Object[] { contractModel.getId() });
                List<Map<String, Object>> mapList = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
                if (mapList.size() > 0) {
                    String contractCodeStr = "";
                    for (Map<String, Object> map : mapList) {
                        contractCodeStr += map.get("ContractCode") + ",";
                    }

                    if (SendEmailUtils.isSend()) {
                        processService.sendEmail("系统提醒:备货合同关联提醒", "合同:" + contractModel.getContractName() + "[" + contractModel.getContractCode() + "]已关联备货合同,合同编号为：" + contractCodeStr + "请您登陆EOSS系统查看", null, null, "tianj", null, null);
                        processService.sendEmail("系统提醒:备货合同关联提醒", "合同:" + contractModel.getContractName() + "[" + contractModel.getContractCode() + "]已关联备货合同,合同编号为：" + contractCodeStr + "请您登陆EOSS系统查看", null, null, "luanpl", null, null);
                    }
                }

                /********************************合同资金合算操作 by tigq*************************************************/

                FundsSalesContract fundsSalesContract = fundsSalesContractService.getBycontractCode(contractModel.getContractCode());
                if (fundsSalesContract == null) {
                    fundsSalesContract = new FundsSalesContract();
                }
                BigDecimal hiAmount = fundsSalesContract.getContractAmount();
                fundsSalesContract.setContractCode(contractModel.getContractCode());
                fundsSalesContract.setContractName(contractModel.getContractName());
                fundsSalesContract.setContractAmount(contractModel.getContractAmount());
                fundsSalesContract.setSalesStartDate(contractModel.getSalesStartDate());
                fundsSalesContract.setCreatorName(contractModel.getCreatorName());

                fundsSalesContractService.saveOrUpdate(fundsSalesContract);
                //记录日志
                FundsSalesLog fundsSalesLog = new FundsSalesLog();
                fundsSalesLog.setContractCode(contractModel.getContractCode());
                fundsSalesLog.setOpAmount(contractModel.getContractAmount());
                fundsSalesLog.setOpDesc("项目成立");
                fundsSalesLog.setOpDate(new Date());

                //DateUtils.convertStringToDate(dateStr, pattern)
                /*历史金额，没有的话使用第一次作为历史金额*/
                if (hiAmount != null) {
                    fundsSalesLog.setHiAmount(hiAmount);
                } else {
                    fundsSalesLog.setHiAmount(contractModel.getContractAmount());
                }

                fundsSalesLogService.saveOrUpdate(fundsSalesLog);

                /********************************合同资金合算操作 end*************************************************/

            }
            processService.handle(taskId, userId, isAgree, remark, variableMap, null, null);

        }
    }

    /**
     * <code>findSalesContractsByCriteria</code>
     * 条件查询符合的合同List
     * @param detachedCriteria 查询条件
     * @since 2014年6月25日  3unshine
     */
    public List<SalesContractModel> findSalesContractsByCriteria(DetachedCriteria detachedCriteria) {
        List<SalesContractModel> salesContractModels = new ArrayList<SalesContractModel>();
        salesContractModels = getDao().findByCriteria(detachedCriteria);
        return salesContractModels;
    }

    /**
     * @param detachedCriteria
     * @param i
     * @param pageSize
     * @return
     */
    public PaginationSupport findPageBySearchMap(HashMap<String, Object> searchMap, int startIndex, Integer pageSize) {
        return getDao().findPageBySearchMap(searchMap, startIndex, pageSize);
    }

    /**
     * 合同综合查询
     * @param detachedCriteria
     * @param i
     * @param pageSize
     * @return
     */
    @SuppressWarnings("unchecked")
    public PaginationSupport findPageByMutiSearchMap(HashMap<String, Object> searchMap, int startIndex, Integer pageSize) {
        PaginationSupport ps = getDao().findPageByMutiSearchMap(searchMap, startIndex, pageSize);

        List<SalesContractInfoAndStatus> list = new ArrayList<SalesContractInfoAndStatus>();
        List<Object> rows = (List<Object>) ps.getItems();

        for (int i = 0; i < rows.size(); i++) {
            SalesContractInfoAndStatus salesContractInfoAndStatus = new SalesContractInfoAndStatus();
            Object[] entity = (Object[]) rows.get(i);
            salesContractInfoAndStatus.setSalesContractId(entity[0] == null ? null : Long.parseLong(entity[0].toString()));//合同ID
            salesContractInfoAndStatus.setContractName(entity[1] == null ? "" : entity[1].toString());
            salesContractInfoAndStatus.setContractCode(entity[2] == null ? "" : entity[2].toString());
            salesContractInfoAndStatus.setContractAmount(entity[3] == null ? null : entity[3].toString());
            String entity4 = entity[4] == null ? "" : entity[4].toString();
            if (entity4.equals(SalesContractConstant.CONTRACT_STATE_CG)) {
                entity4 = "草稿";
            } else if (entity4.equals(SalesContractConstant.CONTRACT_STATE_SH)) {
                entity4 = "审核中";
            } else if (entity4.equals(SalesContractConstant.CONTRACT_STATE_TGSH)) {
                entity4 = "审核通过";
            } else if (entity4.equals(SalesContractConstant.CONTRACT_STATE_FQ)) {
                entity4 = "废弃";
            } else if (entity4.equals(SalesContractConstant.CONTRACT_STATE_CXTJ)) {
                entity4 = "重新提交";
            } else if (entity4.equals(SalesContractConstant.CONTRACT_STATE_DGB)) {
                entity4 = "待关闭";
            } else if (entity4.equals(SalesContractConstant.CONTRACT_STATE_CBYG)) {
                entity4 = "成本预估";
            }
            salesContractInfoAndStatus.setContractState(entity4);
            salesContractInfoAndStatus.setOrderStatus(entity[5] == null ? "" : "合同订单状态：" + entity[5]);
            salesContractInfoAndStatus.setReciveStatus(entity[6] == null ? "" : "收款状态：" + entity[6]);
            salesContractInfoAndStatus.setCachetStatus(entity[7] == null ? "" : "盖章状态：" + entity[7]);
            salesContractInfoAndStatus.setInvoiceStatus(entity[8] == null ? "" : "发票状态：" + entity[8]);
            salesContractInfoAndStatus.setChangeStatus(entity[9] == null ? "" : entity[9].toString());
            salesContractInfoAndStatus.setIsChanged(entity[10] == null ? null : Integer.parseInt(entity[10].toString()));
            if (entity[11] == null && entity4.equals("审核通过")) {
                salesContractInfoAndStatus.setCurrentNode("合同审批完成");
            } else {
                salesContractInfoAndStatus.setCurrentNode(entity[11] == null ? "" : entity[11].toString());
            }

            salesContractInfoAndStatus.setTaskReachTime(entity[12] == null ? null : (Date) entity[12]);

            if (entity[13] != null) {
                String staffName = ((SysStaff) systemUserCache.get(entity[13].toString()).getObjectValue()).getStaffName();
                salesContractInfoAndStatus.setDealUserName(staffName);
            } else {
                salesContractInfoAndStatus.setDealUserName("");
            }
            salesContractInfoAndStatus.setContractTypeName(sysDomainService.getValueMeaning("contractType", entity[14].toString()));

            salesContractInfoAndStatus.setCreatorName(entity[15] == null ? "" : (entity[15].toString()));
            salesContractInfoAndStatus.setCreateTime(entity[16] == null ? null : (Date) entity[16]);
            salesContractInfoAndStatus.setCustomerName(entity[17] == null ? "" : (entity[17].toString()));
            salesContractInfoAndStatus.setIsold(entity[18] == null ? "" : (entity[18].toString()));
            salesContractInfoAndStatus.setQu(entity[19] == null ? "" : (entity[19].toString()));
            salesContractInfoAndStatus.setSur(entity[20] == null ? "" : (entity[20].toString()));
            list.add(salesContractInfoAndStatus);
        }
        ps.setItems(list);

        return ps;
    }

    /**
     * 前台传入的合同清单数据更新
     * @param salesContractDto
     * @param salesContractModel
     * @return
     */
    public Set<SalesContractProductModel> salesContractProductsByAm(SalesContractDto salesContractDto, SalesContractModel salesContractModel) {

        //仅有日期的格式
        String DatePattern = "yyyy-MM-dd";
        //删除原有合同清单数据
        //        Session session = getDao().getSessionFactory().openSession();
        //        Transaction tx = session.beginTransaction();
        //        String sql = "delete from sales_contract_product where SaleContractId='" + salesContractModel.getId() + "'";
        //        session.createSQLQuery(sql).executeUpdate();
        //        tx.commit();
        //        session.close();
        /*getDao().deleteSalesProducts(salesContractModel.getId().toString());
        getDao().flush();*/
        Set<SalesContractProductModel> salesContractProducts = new HashSet<SalesContractProductModel>();
        List<SalesContractProductModel> salesContractProducts1 = new ArrayList<SalesContractProductModel>();

        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();

        long[] contractProductIds = salesContractDto.getContractProductIds();
        long[] productTypes = salesContractDto.getProductTypes();
        String[] productTypeNames = salesContractDto.getProductTypeNames();
        long[] productPartners = salesContractDto.getProductPartners();
        String[] productPartnerNames = salesContractDto.getProductPartnerNames();
        long[] productNos = salesContractDto.getProductNos();
        String[] productNames = salesContractDto.getProductNames();
        int[] quantitys = salesContractDto.getQuantitys();
        float[] unitPrices = salesContractDto.getUnitPrices();
        float[] totalPrices = salesContractDto.getTotalPrices();

        String[] serviceStartDates = salesContractDto.getServiceStartDates();
        String[] serviceEndDates = salesContractDto.getServiceEndDates();
        String[] productRemarks = salesContractDto.getProductRemarks();

        Long[] relateContractProductIds = salesContractDto.getRelateContractProductId();
        Long[] relateDeliveryProductIds = salesContractDto.getRelateDeliveryProductId();

        if (quantitys != null) {
            Set<SalesContractProductModel> sets = salesContractModel.getSalesContractProductModel();
            for (int i = 0; i < quantitys.length; i++) {
                SalesContractProductModel salesContractProduct = null;
                if (contractProductIds != null && i < contractProductIds.length) {
                    for (SalesContractProductModel salesContractProductModel : sets) {
                        if (salesContractProductModel.getId() == contractProductIds[i]) {
                            salesContractProduct = salesContractProductModel;
                            break;
                        }
                    }
                } else {
                    salesContractProduct = new SalesContractProductModel();
                }
                /*if (salesContractProduct == null) {
                    salesContractProduct = new SalesContractProductModel();
                }*/
                //salesContractProduct = salesContractProductService.get(contractProductIds[i]);
                //salesContractProduct.setId(contractProductIds[i]);
                salesContractProduct.setProductType(productTypes[i]);
                salesContractProduct.setProductTypeName(productTypeNames[i]);
                salesContractProduct.setProductPartner(productPartners[i]);
                salesContractProduct.setProductPartnerName(productPartnerNames[i]);
                salesContractProduct.setProductName(productNames[i]);

                if (serviceStartDates.length > 0 && !StringUtil.isEmpty(serviceStartDates[i])) {
                    salesContractProduct.setServiceStartDate(DateUtils.convertStringToDate(serviceStartDates[i], DatePattern));
                }
                if (serviceEndDates.length > 0 && !StringUtil.isEmpty(serviceEndDates[i])) {
                    salesContractProduct.setServiceEndDate(DateUtils.convertStringToDate(serviceEndDates[i], DatePattern));
                }

                if (productRemarks != null && productRemarks.length > 0 && !StringUtil.isEmpty(productRemarks[i])) {
                    salesContractProduct.setRemark(productRemarks[i]);
                }

                //                salesContractProduct.setProductName(productNames[i]);
                salesContractProduct.setProductNo(productNos[i]);
                salesContractProduct.setSalesContractModel(salesContractModel);
                salesContractProduct.setQuantity(quantitys[i]);
                salesContractProduct.setTotalPrice(new BigDecimal(totalPrices[i]));
                salesContractProduct.setUnitPrice(new BigDecimal(unitPrices[i]));
                salesContractProduct.setSalesContractModel(salesContractModel);
                if (relateDeliveryProductIds != null) {
                    if (relateDeliveryProductIds.length > 0 && relateDeliveryProductIds[i] != null) {
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("relateDeliveryProductId", relateDeliveryProductIds[i]);
                        map.put("productNum", quantitys[i]);
                        map.put("relateContractProductId", relateContractProductIds[i]);
                        mapList.add(map);

                        salesContractProduct.setRelateDeliveryProductId(relateDeliveryProductIds[i]);

                        salesContractProduct.setIsReady(1);

                    }
                }

                salesContractProducts.add(salesContractProduct);
                salesContractProducts1.add(salesContractProduct);
            }
        }
        //处理关联合同产品
        if (mapList.size() > 0) {
            handlerRelateContractProducts(mapList, salesContractProducts1);
        }
        salesProductDao.saveOrUpdateAll(salesContractProducts);
        return salesContractProducts;
    }

    /**
     * 前台传入的收款计划数据更新
     * @param salesContractDto
     * @param salesContractModel
     * @param systemUser
     * @return
     */
    public List<SalesContractReceivePlanModel> receivePlansDataByDto(SalesContractDto salesContractDto, SalesContractModel salesContractModel, SystemUser systemUser) {
        String pattern = "yyyy-MM-dd HH:mm";
        //仅有日期的格式
        String DatePattern = "yyyy-MM-dd";
        List<SalesContractReceivePlanModel> receivePlans = new ArrayList<SalesContractReceivePlanModel>();
        Set<SalesContractReceivePlanModel> recePlans = new HashSet<SalesContractReceivePlanModel>();
        //删除以前的收款计划
        getDao().deleteReceivePlan(salesContractModel.getId().toString());

        ObjectMapper objectMapper = JacksonJsonMapper.getInstance();
        Map<String, Object> tableDataMap = null;
        //构造新的收款计划
        String tableData = salesContractDto.getTableData();
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
                        Map<String, Object> map = gridList.get(i);
                        String planedReceiveDateString = map.get("planedReceiveDate").toString();
                        Date planedReceiveDate = new Date();
                        if (planedReceiveDateString.indexOf(":") > 0) {
                            planedReceiveDate = DateUtils.convertStringToDate(planedReceiveDateString, pattern);
                        } else {
                            planedReceiveDate = DateUtils.convertStringToDate(planedReceiveDateString, DatePattern);
                        }
                        String payCondition = map.get("payCondition") == null ? "" : map.get("payCondition").toString();
                        BigDecimal planedReceiveAmount = new BigDecimal(map.get("planedReceiveAmount").toString());
                        SalesContractReceivePlanModel receivePlan = new SalesContractReceivePlanModel();
                        //创建时间
                        receivePlan.setCreateTime(new Date());
                        receivePlan.setCreator(Long.valueOf(systemUser.getUserId()).longValue());
                        receivePlan.setPayCondition(payCondition);
                        receivePlan.setPlanedReceiveAmount(planedReceiveAmount);
                        receivePlan.setPlanedReceiveDate(planedReceiveDate);
                        receivePlan.setSalesContract(salesContractModel);
                        receivePlans.add(receivePlan);
                        recePlans.add(receivePlan);
                    }
                }
            }
        }
        salesReceivePlanDao.saveOrUpdateAll(recePlans);
        return receivePlans;
    }

    public void handlerRelateContractProducts(List<Map<String, Object>> mapList, List<SalesContractProductModel> salesContractProducts) {
        for (Map<String, Object> map : mapList) {
            //新建合同
            if (map.get("relateContractProductId").toString().equals("0")) {
                SalesContractProductModel salesContractProduct = salesContractProductService.get(Long.parseLong(map.get("relateDeliveryProductId").toString()));
                int countNum = salesContractProduct.getSurplusNum();
                int surplusNum = countNum + Integer.parseInt(map.get("productNum").toString());
                salesContractProduct.setSurplusNum(surplusNum);
                salesContractProductService.update(salesContractProduct);
            } else {
                SalesContractProductModel relateDeliveryProduct = salesContractProductService.get(Long.parseLong(map.get("relateDeliveryProductId").toString()));
                SalesContractProductModel salesProduct = salesContractProductService.get(Long.parseLong(map.get("relateContractProductId").toString()));
                int originalNum = relateDeliveryProduct.getSurplusNum() - salesProduct.getQuantity();
                originalNum += Integer.parseInt(map.get("productNum").toString());
                relateDeliveryProduct.setSurplusNum(originalNum);
                salesContractProductService.update(relateDeliveryProduct);
            }
        }
    }

    /**
     * <code>updateChange</code>
     * 合同变更操作
     * @param oldSalesContractModel
     * @param newSalesContractModel
     * @param systemUser
     */
    public Long updateChange(SalesContractModel oldSalesContractModel, SalesContractModel newSalesContractModel, SystemUser systemUser, int changeType, long salesContractChangeApplyId) {
        //首先将原始数据插入到快照表
        ContractSnapShootModel contractSnapShootModel = new ContractSnapShootModel();
        long contractSnapShootId = IdentifierGeneratorImpl.generatorLong();
        contractSnapShootModel.setId(contractSnapShootId);
        contractSnapShootModel.setSaleContractChangeApplyId(salesContractChangeApplyId);
        contractSnapShootModel.setAccountCurrency(oldSalesContractModel.getAccountCurrency());
        contractSnapShootModel.setAttachIds(oldSalesContractModel.getAttachIds());
        Set<BusinessOrderModel> businessOrderModels = oldSalesContractModel.getBusinessOrderModel();
        String businessOrderModelIds = "";
        if (businessOrderModels.size() > 0) {
            Iterator<BusinessOrderModel> it = businessOrderModels.iterator();
            while (it.hasNext()) {
                businessOrderModelIds += it.next().getId() + ",";
            }
            businessOrderModelIds = businessOrderModelIds.substring(0, businessOrderModelIds.lastIndexOf(","));
        }
        contractSnapShootModel.setBusinessOrderModelIds(businessOrderModelIds);
        contractSnapShootModel.setContractAmount(oldSalesContractModel.getContractAmount());
        contractSnapShootModel.setContractCode(oldSalesContractModel.getContractCode());
        contractSnapShootModel.setContractName(oldSalesContractModel.getContractName());
        contractSnapShootModel.setContractShortName(oldSalesContractModel.getContractShortName());
        contractSnapShootModel.setContractState(oldSalesContractModel.getContractState());
        contractSnapShootModel.setContractType(oldSalesContractModel.getContractType());
        contractSnapShootModel.setCreateTime(oldSalesContractModel.getCreateTime());
        contractSnapShootModel.setCustomerId(oldSalesContractModel.getCustomerId());
        contractSnapShootModel.setInvoiceType(oldSalesContractModel.getInvoiceType());
        contractSnapShootModel.setIsChanged(oldSalesContractModel.getIsChanged());
        contractSnapShootModel.setOrderProcessor(oldSalesContractModel.getOrderProcessor());
        contractSnapShootModel.setProcessInstanceId(oldSalesContractModel.getProcessInstanceId());
        contractSnapShootModel.setReceiveWay(oldSalesContractModel.getReceiveWay());
        contractSnapShootModel.setSaleContractChangeCreateTime(new Date());
        contractSnapShootModel.setSaleContractChangeCreator(Long.parseLong(systemUser.getUserId()));
        contractSnapShootModel.setSaleContractId(oldSalesContractModel.getId());

        contractSnapShootModel.setEstimateProfit(oldSalesContractModel.getEstimateProfit());
        contractSnapShootModel.setEstimateProfitDesc(oldSalesContractModel.getEstimateProfitDesc());
        contractSnapShootModel.setBusiEstimateProfit(oldSalesContractModel.getBusiEstimateProfit());
        contractSnapShootModel.setBusiEstimateProfitDesc(oldSalesContractModel.getBusiEstimateProfitDesc());
        contractSnapShootModel.setHopeArriveTime(oldSalesContractModel.getHopeArriveTime());
        contractSnapShootModel.setContractRemark(oldSalesContractModel.getContractRemark());
        contractSnapShootModel.setSalesStartDate(oldSalesContractModel.getSalesStartDate());
        contractSnapShootModel.setSalesEndDate(oldSalesContractModel.getSalesEndDate());
        contractSnapShootModel.setServiceStartDate(oldSalesContractModel.getServiceStartDate());
        contractSnapShootModel.setServiceEndDate(oldSalesContractModel.getServiceEndDate());
        contractSnapShootModel.setContractRemark(oldSalesContractModel.getContractRemark());
        contractSnapShootModel.setHopeArrivePlace(oldSalesContractModel.getHopeArrivePlace());

        //先查下是否有历史版本   有的话加1 没有置为v1
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ContractSnapShootModel.class);
        detachedCriteria.add(Restrictions.eq(ContractSnapShootModel.SALECONTRACTID, oldSalesContractModel.getId()));
        List<ContractSnapShootModel> oldContractSnapShootModels = contractSnapShootDao.findByCriteria(detachedCriteria);
        int oldContractSnapShootCount = oldContractSnapShootModels.size();
        contractSnapShootModel.setSaleContractVersion("v" + (oldContractSnapShootCount + 1));
        //原始与产品一对多条目
        Set<SalesContractProductModel> salesContractProductModels = oldSalesContractModel.getSalesContractProductModel();
        //新的与产品一对多条目
        Set<ContractProductSnapShootModel> salesContractProductSnapShoots = new HashSet<ContractProductSnapShootModel>(0);
        if (salesContractProductModels.size() > 0) {
            Iterator<SalesContractProductModel> it = salesContractProductModels.iterator();
            while (it.hasNext()) {
                SalesContractProductModel salesContractProductModel = it.next();
                ContractProductSnapShootModel contractProductSnapShootModel = new ContractProductSnapShootModel();
                long contractProductSnapShootsId = IdentifierGeneratorImpl.generatorLong();
                contractProductSnapShootModel.setId(contractProductSnapShootsId);
                contractProductSnapShootModel.setContractSnapShootModel(contractSnapShootModel);
                contractProductSnapShootModel.setInvoiceType(salesContractProductModel.getInvoiceType());
                contractProductSnapShootModel.setProductId(salesContractProductModel.getId());
                contractProductSnapShootModel.setProductName(salesContractProductModel.getProductName());
                contractProductSnapShootModel.setProductNo(salesContractProductModel.getProductNo());
                contractProductSnapShootModel.setProductPartner(salesContractProductModel.getProductPartner());
                contractProductSnapShootModel.setProductPartnerName(salesContractProductModel.getProductPartnerName());
                contractProductSnapShootModel.setProductType(salesContractProductModel.getProductType());
                contractProductSnapShootModel.setProductTypeName(salesContractProductModel.getProductTypeName());
                contractProductSnapShootModel.setQuantity(salesContractProductModel.getQuantity());
                contractProductSnapShootModel.setRemark(salesContractProductModel.getRemark());
                contractProductSnapShootModel.setServiceEndDate(salesContractProductModel.getServiceEndDate());
                contractProductSnapShootModel.setServiceStartDate(salesContractProductModel.getServiceStartDate());
                contractProductSnapShootModel.setServiceType(salesContractProductModel.getServiceType());
                contractProductSnapShootModel.setTotalPrice(salesContractProductModel.getTotalPrice());
                contractProductSnapShootModel.setUnitPrice(salesContractProductModel.getUnitPrice());

                contractProductSnapShootModel.setIsReady(salesContractProductModel.getIsReady());
                contractProductSnapShootModel.setSurplusNum(salesContractProductModel.getSurplusNum());
                contractProductSnapShootModel.setRelateDeliveryProductId(salesContractProductModel.getRelateDeliveryProductId());

                contractProductSnapShootModel.setSerialNumber(salesContractProductModel.getSerialNumber());
                contractProductSnapShootModel.setServicePeriod(salesContractProductModel.getServicePeriod());
                contractProductSnapShootModel.setEquipmentSplace(salesContractProductModel.getEquipmentSplace());

                salesContractProductSnapShoots.add(contractProductSnapShootModel);
            }
        }
        contractSnapShootModel.setSalesContractProductSnapShoots(salesContractProductSnapShoots);
        //原始与收款计划一对多条目
        Set<SalesContractReceivePlanModel> salesContractReceivePlans = oldSalesContractModel.getSalesContractReceivePlans();
        //新的与收款计划一对多条目
        Set<ContractRecivePlanSnapShootModel> salesContractRecivePlanSnapShoots = new HashSet<ContractRecivePlanSnapShootModel>(0);
        if (salesContractReceivePlans.size() > 0) {
            Iterator<SalesContractReceivePlanModel> it = salesContractReceivePlans.iterator();
            while (it.hasNext()) {
                SalesContractReceivePlanModel salesContractReceivePlanModel = it.next();
                ContractRecivePlanSnapShootModel contractRecivePlanSnapShootModel = new ContractRecivePlanSnapShootModel();
                contractRecivePlanSnapShootModel.setContractSnapShootModel(contractSnapShootModel);
                contractRecivePlanSnapShootModel.setCreateTime(salesContractReceivePlanModel.getCreateTime());
                contractRecivePlanSnapShootModel.setCreator(salesContractReceivePlanModel.getCreator());
                contractRecivePlanSnapShootModel.setCurrentAccountId(salesContractReceivePlanModel.getCurrentAccountId());
                long contractRecivePlanSnapShootId = IdentifierGeneratorImpl.generatorLong();
                contractRecivePlanSnapShootModel.setId(contractRecivePlanSnapShootId);
                contractRecivePlanSnapShootModel.setModifier(salesContractReceivePlanModel.getModifier());
                contractRecivePlanSnapShootModel.setModifyTime(salesContractReceivePlanModel.getModifyTime());
                contractRecivePlanSnapShootModel.setPayCondition(salesContractReceivePlanModel.getPayCondition());
                contractRecivePlanSnapShootModel.setPlanedReceiveAmount(salesContractReceivePlanModel.getPlanedReceiveAmount());
                contractRecivePlanSnapShootModel.setPlanedReceiveDate(salesContractReceivePlanModel.getPlanedReceiveDate());
                contractRecivePlanSnapShootModel.setRecivePlanId(salesContractReceivePlanModel.getId());
                contractRecivePlanSnapShootModel.setRemark(salesContractReceivePlanModel.getRemark());
                contractRecivePlanSnapShootModel.setStatus(salesContractReceivePlanModel.getStatus());
                salesContractRecivePlanSnapShoots.add(contractRecivePlanSnapShootModel);
            }
        }

        //进入流程
        //TODO 暂时只做了变更合同金额的流程
        //        if (changeType == 1) {
        //            String procDefKey = SalesContractConstant.CONTRACT_AMOUNT_CHANGE_PROCDEFKEY;
        //            creatProcInst(newSalesContractModel, systemUser, procDefKey);
        //        }
        //        //将合同的变更状态设置成已变更
        //        newSalesContractModel.setIsChanged(1);
        //        //将合同的状态设置成提交审核
        //        newSalesContractModel.setContractState("SH");
        //        //将新数据updata
        //        getDao().update(newSalesContractModel);
        //合同状态快照包
        SalesContractStatusModel oldSalesContractStatusModel = salesContractStatusDao.findContractStatusByContractId(oldSalesContractModel.getId());
        SalesContractStatusModel contractStatusModel = new SalesContractStatusModel();
        contractStatusModel.setCachetStatus(oldSalesContractStatusModel.getCachetStatus());
        contractStatusModel.setInvoiceStatus(oldSalesContractStatusModel.getInvoiceStatus());
        contractStatusModel.setOrderStatus(oldSalesContractStatusModel.getOrderStatus());
        contractStatusModel.setReciveStatus(oldSalesContractStatusModel.getReciveStatus());
        contractStatusModel.setContractSnapShootId(contractSnapShootId);
        contractStatusModel.setId(IdentifierGeneratorImpl.generatorLong());
        salesContractStatusDao.save(contractStatusModel);
        //        SalesContractStatusModel salesContractStatusModel = new SalesContractStatusModel();
        //        salesContractStatusModel.setId(IdentifierGeneratorImpl.generatorLong());
        //        salesContractStatusModel.setReciveStatus(SalesContractConstant.CONTRACT_RECIVE_INITSTATE);
        //        salesContractStatusModel.setInvoiceStatus(SalesContractConstant.CONTRACT_INVOICE_INITSTATE);
        //        salesContractStatusModel.setOrderStatus(SalesContractConstant.CONTRACT_ORDER_INITSTATE);
        //        salesContractStatusModel.setCachetStatus(SalesContractConstant.CONTRACT_CACHET_INITSTATE);
        //        salesContractStatusModel.setChangeStatus(SalesContractConstant.CONTRACT_CHANGE_INITSTATE);
        //        salesContractStatusModel.setSaleContractId(newSalesContractModel.getId());
        //        salesContractStatusModel.setCreateTime(new Date());
        //创建合同状态
        //        salesContractStatusDao.save(salesContractStatusModel);
        contractSnapShootModel.setSalesContractRecivePlanSnapShoots(salesContractRecivePlanSnapShoots);
        contractProductSnapShootDao.saveOrUpdateAll(salesContractProductSnapShoots);
        contractRecivePlanSnapShootDao.saveOrUpdateAll(salesContractRecivePlanSnapShoots);
        contractSnapShootDao.save(contractSnapShootModel);

        return contractSnapShootId;
    }

    /**
     * @return
     */
    public Map<String, Object> getContractName(long id) {
        return getDao().getContractName(id);
    }

    /**
     * <code>uploadProducts</code>
     * 导入产品
     * @param request
     * @return
     * @since   2014年9月17日    guokemenng
     */
    public Map<String, Object> uploadProducts(HttpServletRequest request, String state) {
        try {
            String filePath = doUpload(request);
            String realPath = UploadUtil.buildWebRootPath() + filePath;
            Map<String, Object> map = null;
            if (StringUtil.isEmpty(state)) {
                map = readExcel(realPath);
            } else {
                map = readMAExcel(realPath);
            }
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * <code>doUpload</code>
     * 上传操作
     * @param request
     * @return
     * @since   2014年9月17日    guokemenng
     */
    public String doUpload(HttpServletRequest request) {
        MultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        MultipartHttpServletRequest multipartRequest = resolver.resolveMultipart(request);

        CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest.getFile("attachment");
        String picName = file.getFileItem().getName();

        String webPath = UploadUtil.buildWebPath(UploadUtil.getBasePath() + "salesContract/", picName);
        String storeFilePath = UploadUtil.buildStroeFilePath(webPath);

        File descPath = new File(UploadUtil.buildPath(storeFilePath));
        if (!descPath.exists()) {
            descPath.mkdirs();
        }

        File descF = new File(storeFilePath);
        try {
            FileCopyUtils.copy(file.getBytes(), descF);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return webPath;
    }

    /**
     * <code>readExcel</code>
     * 读取xls 组成List map
     * @param path
     * @return
     * @since   2014年9月17日    guokemenng
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> readExcel(String path) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        try {
            File excelFile = new File(path);
            String fileName = excelFile.getName();
            fileName = fileName.toLowerCase();
            if (fileName.toLowerCase().endsWith(".xls")) {
                //把一张xls的数据表读到wb里
                HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(excelFile));
                //读取第一页,一般一个excel文件会有三个工作表，这里获取第一个工作表来进行操作    HSSFSheet sheet = wb.getSheetAt(0);
                Sheet sheet = wb.getSheetAt(0);
                //循环遍历表sheet.getLastRowNum()是获取一个表最后一条记录的记录号，
                //第一条为标题 从第二行开始读取数据
                for (int j = 1; j < sheet.getLastRowNum() + 1; j++) {
                    //创建一个行对象
                    Row row = sheet.getRow(j);
                    Map<String, Object> m = new HashMap<String, Object>();
                    getMapModel(m, row);
                    if (m.get("productName") == null) {
                        break;
                    }
                    String sql = "select productModel.id as productId,partner.id as partnerId,partner.partnerFullName,partner.partnerCode,productType.id as typeId,productType.typeName from business_productmodel productModel left join business_partnerinfo partner on partner.id = productModel.partnerId left join business_producttype productType on productType.id = productModel.productType where 1=1 and productModel.deleteFlag='1' and productModel.productModel = ?";
                    Query query = getDao().createSQLQuery(sql, new String[] { m.get("productName").toString().trim() });
                    query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                    List<Map<String, Object>> list = query.list();
                    if (list.size() <= 0) {
                        map.put("lineNo", j);
                        map.put("productName", m.get("productName"));
                        break;
                    } else {
                        if (list.get(0).get("partnerId") == null) {
                            map.put("lineNo", j);
                            map.put("partnerName", m.get("partnerName"));
                            break;
                        } else {
                            //                            BasePartnerInfo info = partnerInfoService.get(list.get(0).get("partnerId").toString());
                            if (!(list.get(0).get("partnerFullName").equals(m.get("partnerName").toString()) || list.get(0).get("partnerCode").equals(m.get("partnerName").toString()))) {
                                map.put("lineNo", j);
                                map.put("partnerName", m.get("partnerName"));
                                break;
                            }
                        }
                        if (list.get(0).get("typeId") == null) {
                            map.put("lineNo", j);
                            //                            map.put("typeName", m.get("typeName"));
                            map.put("typeName", "(" + m.get("productName") + "此设备数据库中没有型号)");
                            break;
                        } else {
                            //                            BaseProductType type = productTypeService.get(list.get(0).get("typeId").toString());
                            //                            if (!m.get("typeName").toString().equals(list.get(0).get("typeName"))) {
                            //                                map.put("lineNo", j);
                            //                                map.put("typeName", m.get("typeName"));
                            //                                break;
                            //                            }
                        }
                        m.put("productId", list.get(0).get("productId"));
                        m.put("partnerId", list.get(0).get("partnerId"));
                        m.put("typeId", list.get(0).get("typeId"));
                        m.put("typeName", list.get(0).get("typeName"));
                    }
                    mapList.add(m);
                }
            } else {
                InputStream input = new FileInputStream(excelFile);
                XSSFWorkbook workBook = new XSSFWorkbook(input);
                // 获取Sheet数量
                Integer sheetNum = workBook.getNumberOfSheets();
                for (int i = 0; i < sheetNum; i++) {
                    XSSFSheet sheet = workBook.getSheetAt(i);
                    for (int j = 1; j < sheet.getLastRowNum() + 1; j++) {
                        //创建一个行对象
                        XSSFRow row = sheet.getRow(j);
                        Map<String, Object> m = new HashMap<String, Object>();
                        getMapModel(m, row);

                        String sql = "select productModel.id as productId,partner.id as partnerId,partner.partnerFullName,partner.partnerCode,productType.id as typeId,productType.typeName from business_productmodel productModel left join business_partnerinfo partner on partner.id = productModel.partnerId left join business_producttype productType on productType.id = productModel.productType where 1=1 and productModel.deleteFlag='1' and productModel.productModel = ?";
                        Query query = getDao().createSQLQuery(sql, new String[] { m.get("productName").toString().trim() });
                        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                        List<Map<String, Object>> list = query.list();
                        if (list.size() <= 0) {
                            map.put("lineNo", j);
                            map.put("productName", m.get("productName"));
                            break;
                        } else {
                            if (list.get(0).get("partnerId") == null) {
                                map.put("lineNo", j);
                                map.put("partnerName", m.get("partnerName"));
                                break;
                            } else {
                                //                                BasePartnerInfo info = partnerInfoService.get(list.get(0).get("partnerId").toString());
                                if (!(list.get(0).get("partnerFullName").equals(m.get("partnerName").toString()) || list.get(0).get("partnerCode").equals(m.get("partnerName").toString()))) {
                                    map.put("lineNo", j);
                                    map.put("partnerName", m.get("partnerName"));
                                    break;
                                }
                            }
                            if (list.get(0).get("typeId") == null) {
                                map.put("lineNo", j);
                                map.put("typeName", m.get("typeName"));
                                break;
                            } else {
                                //                                BaseProductType type = productTypeService.get(list.get(0).get("typeId").toString());
                                if (!m.get("typeName").toString().equals(list.get(0).get("typeName"))) {
                                    map.put("lineNo", j);
                                    map.put("typeName", m.get("typeName"));
                                    break;
                                }
                            }
                            m.put("productId", list.get(0).get("productId"));
                            m.put("partnerId", list.get(0).get("partnerId"));
                            m.put("typeId", list.get(0).get("typeId"));
                        }
                        mapList.add(m);
                    }
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        map.put("rows", mapList);
        return map;
    }

    /**
     * <code>getSalaryTopicModel</code>
     * 每行row 组建一个一个Map<String,Object>对象
     * @return
     * @since   2014年4月18日    guokemenng
     */
    public Map<String, Object> getMapModel(Map<String, Object> map, Row row) {
        //把一行里的每一个字段遍历出来
        for (int i = 0; i < row.getLastCellNum(); i++) {
            //创建一个行里的一个字段的对象，也就是获取到的一个单元格中的值
            Cell cell = row.getCell(i);
            try {
                Object o = getCellvalue(cell);
                if (o == null) {
                    o = "";
                }
                switch (i) {
                    case 0: {
                        if (o != null) {
                            map.put("typeName", o);
                        }
                        break;
                    }
                    case 1: {
                        if (o != null) {
                            map.put("partnerName", o);
                        }
                        break;
                    }
                    case 2: {
                        if (o != null) {
                            map.put("productName", o);
                        }
                        break;
                    }
                    case 3: {
                        if (o != null) {
                            map.put("startTime", o);
                        }
                        break;
                    }
                    case 4: {
                        if (o != null) {
                            map.put("endTime", o);
                        }
                        break;
                    }
                    case 5: {
                        if (o != null) {
                            map.put("quantity", o);
                        }
                        break;
                    }
                    case 6: {
                        if (o != null) {
                            map.put("unitPrice", o);
                        }
                        break;
                    }
                    case 7: {
                        if (o != null) {
                            map.put("totalPrice", o);
                        }
                        break;
                    }
                    case 8: {
                        if (o != null) {
                            map.put("productRemark", o);
                        }
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    /**
     * <code>readMAExcel</code>
     * MA 续保合同导入
     * @param path
     * @return
     * @since   2014年11月20日    guokemenng
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> readMAExcel(String path) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        try {
            File excelFile = new File(path);
            String fileName = excelFile.getName();
            fileName = fileName.toLowerCase();
            if (fileName.toLowerCase().endsWith(".xls")) {
                //把一张xls的数据表读到wb里
                HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(excelFile));
                //读取第一页,一般一个excel文件会有三个工作表，这里获取第一个工作表来进行操作    HSSFSheet sheet = wb.getSheetAt(0);
                Sheet sheet = wb.getSheetAt(0);
                //循环遍历表sheet.getLastRowNum()是获取一个表最后一条记录的记录号，
                //第一条为标题 从第二行开始读取数据
                for (int j = 1; j < sheet.getLastRowNum() + 1; j++) {
                    //创建一个行对象
                    Row row = sheet.getRow(j);
                    Map<String, Object> m = new HashMap<String, Object>();
                    getMAMapModel(m, row);
                    if (m.get("productName") == null) {
                        break;
                    }
                    String sql = "select productModel.id as productId,partner.id as partnerId,partner.partnerFullName,partner.partnerCode,productType.id as typeId,productType.typeName from business_productmodel productModel left join business_partnerinfo partner on partner.id = productModel.partnerId left join business_producttype productType on productType.id = productModel.productType where 1=1 and productModel.deleteFlag='1' and productModel.productModel = ?";
                    Query query = getDao().createSQLQuery(sql, new String[] { m.get("productName").toString().trim() });
                    query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                    List<Map<String, Object>> list = query.list();
                    if (list.size() <= 0) {
                        map.put("lineNo", j);
                        map.put("productName", m.get("productName"));
                        break;
                    } else {
                        if (list.get(0).get("partnerId") == null) {
                            map.put("lineNo", j);
                            map.put("partnerName", m.get("partnerName"));
                            break;
                        } else {
                            //                            BasePartnerInfo info = partnerInfoService.get(list.get(0).get("partnerId").toString());
                            if (!(list.get(0).get("partnerFullName").equals(m.get("partnerName").toString()) || list.get(0).get("partnerCode").equals(m.get("partnerName").toString()))) {
                                map.put("lineNo", j);
                                map.put("partnerName", m.get("partnerName"));
                                break;
                            }
                        }
                        //                        if (list.get(0).get("typeId") == null) {
                        //                            map.put("lineNo", j);
                        //                            map.put("typeName", m.get("typeName"));
                        //                            break;
                        //                        } else {
                        ////                            BaseProductType type = productTypeService.get(list.get(0).get("typeId").toString());
                        //                            if(!m.get("typeName").toString().equals(list.get(0).get("typeName"))){
                        //                                map.put("lineNo", j);
                        //                                map.put("typeName", m.get("typeName"));
                        //                                break;
                        //                            }
                        //                        }
                        m.put("productId", list.get(0).get("productId"));
                        m.put("partnerId", list.get(0).get("partnerId"));
                        //                        m.put("typeId", list.get(0).get("typeId"));
                    }
                    mapList.add(m);
                }
            } else {
                InputStream input = new FileInputStream(excelFile);
                XSSFWorkbook workBook = new XSSFWorkbook(input);
                // 获取Sheet数量
                Integer sheetNum = workBook.getNumberOfSheets();
                for (int i = 0; i < sheetNum; i++) {
                    XSSFSheet sheet = workBook.getSheetAt(i);
                    for (int j = 1; j < sheet.getLastRowNum() + 1; j++) {
                        //创建一个行对象
                        XSSFRow row = sheet.getRow(j);
                        Map<String, Object> m = new HashMap<String, Object>();
                        getMAMapModel(m, row);

                        String sql = "select productModel.id as productId,partner.id as partnerId,partner.partnerFullName,partner.partnerCode,productType.id as typeId,productType.typeName from business_productmodel productModel left join business_partnerinfo partner on partner.id = productModel.partnerId left join business_producttype productType on productType.id = productModel.productType where 1=1 and productModel.deleteFlag='1' and productModel.productModel = ?";
                        Query query = getDao().createSQLQuery(sql, new String[] { m.get("productName").toString().trim() });
                        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                        List<Map<String, Object>> list = query.list();
                        if (list.size() <= 0) {
                            map.put("lineNo", j);
                            map.put("productName", m.get("productName"));
                            break;
                        } else {
                            if (list.get(0).get("partnerId") == null) {
                                map.put("lineNo", j);
                                map.put("partnerName", m.get("partnerName"));
                                break;
                            } else {
                                //                                BasePartnerInfo info = partnerInfoService.get(list.get(0).get("partnerId").toString());
                                if (!(list.get(0).get("partnerFullName").equals(m.get("partnerName").toString()) || list.get(0).get("partnerCode").equals(m.get("partnerName").toString()))) {
                                    map.put("lineNo", j);
                                    map.put("partnerName", m.get("partnerName"));
                                    break;
                                }
                            }

                            //                            if (list.get(0).get("typeId") == null) {
                            //                                map.put("lineNo", j);
                            //                                map.put("typeName", m.get("typeName"));
                            //                                break;
                            //                            } else {
                            //                                //                                BaseProductType type = productTypeService.get(list.get(0).get("typeId").toString());
                            //                                if (!m.get("typeName").toString().equals(list.get(0).get("typeName"))) {
                            //                                    map.put("lineNo", j);
                            //                                    map.put("typeName", m.get("typeName"));
                            //                                    break;
                            //                                }
                            //                            }

                            m.put("productId", list.get(0).get("productId"));
                            m.put("partnerId", list.get(0).get("partnerId"));
                            m.put("typeId", list.get(0).get("typeId"));
                        }
                        mapList.add(m);
                    }
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        map.put("rows", mapList);
        return map;
    }

    /**
     * <code>getMAMapModel</code>
     * MA 续保合同导入 组装Map
     * @param map
     * @param row
     * @return
     * @since   2014年11月20日    guokemenng
     */
    public Map<String, Object> getMAMapModel(Map<String, Object> map, Row row) {
        //把一行里的每一个字段遍历出来
        for (int i = 0; i < row.getLastCellNum(); i++) {
            //创建一个行里的一个字段的对象，也就是获取到的一个单元格中的值
            Cell cell = row.getCell(i);
            try {
                Object o = getCellvalue(cell);
                if (o == null) {
                    o = "";
                }
                switch (i) {
                    case 0: {
                        if (o != null) {
                            map.put("partnerName", o);
                        }
                        break;
                    }
                    case 1: {
                        if (o != null) {
                            map.put("productName", o);
                        }
                        break;
                    }
                    case 2: {
                        if (o != null) {
                            map.put("serialNumber", o);
                        }
                        break;
                    }
                    case 3: {
                        if (o != null) {
                            map.put("servicePeriod", o);
                        }
                        break;
                    }
                    case 4: {
                        if (o != null) {
                            map.put("serviceStartDate", o);
                        }
                        break;
                    }
                    case 5: {
                        if (o != null) {
                            map.put("serviceEndDate", o);
                        }
                        break;
                    }
                    case 6: {
                        if (o != null) {
                            map.put("unitPrice", o);
                        }
                        break;
                    }
                    case 7: {
                        if (o != null) {
                            map.put("quantity", o);
                        }
                        break;
                    }
                    case 8: {
                        if (o != null) {
                            map.put("totalPrice", o);
                        }
                        break;
                    }
                    case 9: {
                        if (o != null) {
                            map.put("equipmentSplace", o);
                        }
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    /**
     * <code>getCellvalue</code>
     * 得到cell值
     * @param salary
     * @param cell
     * @param i
     * @return
     * @since   2014年4月28日    guokemenng
     */
    public Object getCellvalue(Cell cell) {
        if (cell == null) {
            return null;
        }
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_NUMERIC:
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    try {
                        return DateUtils.convertDateToString(cell.getDateCellValue(), "yyyy-MM-dd");
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                return cell.getNumericCellValue();
            case Cell.CELL_TYPE_STRING:
                return cell.getStringCellValue();
            case Cell.CELL_TYPE_BLANK:
                return null;
            case Cell.CELL_TYPE_FORMULA:
                cell.setCellType(Cell.CELL_TYPE_STRING);
                String str = cell.getStringCellValue();
                return str;
            default:
                return null;
        }
    }

    /**
     * <code>getSalesContractList</code>
     * 得到备货合同列表
     * @param request
     * @param start
     * @param pageSize
     * @since   2014年10月9日    guokemenng
     */
    public PaginationSupport getSalesContractList(HttpServletRequest request, Integer start, Integer pageSize) {
        String contractType = request.getParameter("contractType");
        SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
        String sql = "select s.* from sales_contract s left join  sales_contract_product p on s.id = p.SaleContractId where p.Quantity != p.SurplusNum and s.ContractType = ? and s.Creator = ? and s.ContractState = 'TGSH' group by s.id order by s.id";
        Object[] params = new Object[2];
        params[0] = Integer.parseInt(contractType);
        params[1] = systemUser.getUserId();
        Query query = getDao().createSQLQuery(sql, params);
        query.setFirstResult(start);
        query.setMaxResults(pageSize);
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        PaginationSupport paginationSupport = new PaginationSupport(query.list(), getSalesContractListCount(sql, params));
        return paginationSupport;
    }

    /**
     * <code>getSalesContractListCount</code>
     * 得到备货合同总数量
     * @param params
     * @return
     * @since   2014年10月9日    guokemenng
     */
    public Integer getSalesContractListCount(String sql, Object[] params) {
        String sql1 = "select count(*) from (" + sql + ") as t";
        if (getDao().createSQLQuery(sql, params).list().size() > 0) {
            return Integer.valueOf(getDao().createSQLQuery(sql1, params).list().get(0).toString()).intValue();
        } else {
            return 0;
        }
    }

    /**
     * <code>getOutboundSalesContractList</code>
     * 得到出库合同
     * @param request
     * @param start
     * @param pageSize
     * @return
     * @since   2014年10月17日    guokemenng
     */
    public PaginationSupport getOutboundSalesContractList(HttpServletRequest request, Integer start, Integer pageSize) {
        SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
        String sql = "select c.* from sales_contract c left join sales_contract_status s on c.id = s.salecontractId where s.orderStatus != '未采购' and c.creator = ? order by c.id desc";
        Object[] params = new Object[1];
        params[0] = systemUser.getUserId();
        Query query = getDao().createSQLQuery(sql, params);
        query.setFirstResult(start);
        query.setMaxResults(pageSize);
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        PaginationSupport paginationSupport = new PaginationSupport(query.list(), getOutboundSalesContractListCount(params));
        return paginationSupport;
    }

    /**
     * <code>getSalesContractListCount</code>
     * 得到出库合同总数量
     * @param params
     * @return
     * @since   2014年10月9日    guokemenng
     */
    public Integer getOutboundSalesContractListCount(Object[] params) {
        String sql = "select count(*) from sales_contract c left join sales_contract_status s on c.id = s.salecontractId where s.orderStatus != '未采购' and c.creator = ?";
        return Integer.valueOf(getDao().createSQLQuery(sql, params).list().get(0).toString()).intValue();
    }

    /**
     * 只增加备品备件
     * @param salesContractModel
     * @param salesContractProducts
     * @param systemUser
     * @throws Exception
     */
    public void changeSalesParts(SalesContractModel salesContractModel, Set<SalesContractProductModel> salesContractProducts, SystemUser systemUser) throws Exception {
        salesContractModel.setSalesContractProductModel(salesContractProducts);
        getDao().save(salesContractModel);
        // salesProductDao.saveOrUpdateAll(salesContractProducts);
        SalesContractStatusModel salesContractStatusModel = salesContractStatusDao.contractOrderStatus(salesContractModel.getId());
        salesContractStatusModel.setChangeStatus(SalesContractConstant.CONTRACT_CHANGE_INITSTATE);
        salesContractStatusDao.save(salesContractStatusModel);
    }

    /**
     * <code>createSalesByChange</code>
     * 变更后创建新的合同
     * @param salesContractModel
     * @param receivePlans
     * @param salesContractProducts
     * @param systemUser
     * @throws Exception
     * @since   2014年11月3日    guokemenng
     */
    public void createSalesByChange(SalesContractModel salesContractModel, List<SalesContractReceivePlanModel> receivePlans, Set<SalesContractProductModel> salesContractProducts, SystemUser systemUser, Long contractSnapShootId) throws Exception {
        //        if (salesContractModel.getContractState().equals("SH")) {//合同处于审批中
        //
        //            SalesContractStatusModel salesContractStatusModel = new SalesContractStatusModel();
        //            salesContractStatusModel.setId(IdentifierGeneratorImpl.generatorLong());
        //            salesContractStatusModel.setReciveStatus(SalesContractConstant.CONTRACT_RECIVE_INITSTATE);
        //            salesContractStatusModel.setInvoiceStatus(SalesContractConstant.CONTRACT_INVOICE_INITSTATE);
        //            salesContractStatusModel.setOrderStatus(SalesContractConstant.CONTRACT_ORDER_INITSTATE);
        //            salesContractStatusModel.setCachetStatus(SalesContractConstant.CONTRACT_CACHET_INITSTATE);
        //            salesContractStatusModel.setChangeStatus(SalesContractConstant.CONTRACT_CHANGE_INITSTATE);
        //            salesContractStatusModel.setSaleContractId(salesContractModel.getId());
        //            salesContractStatusModel.setCreateTime(new Date());
        //            //创建合同状态
        //            salesContractStatusDao.save(salesContractStatusModel);
        //        }
        SalesContractStatusModel salesContractStatusModel = salesContractStatusDao.contractOrderStatus(salesContractModel.getId());
        salesContractStatusModel.setChangeStatus(SalesContractConstant.CONTRACT_CHANGE_INITSTATE);
        salesContractStatusDao.save(salesContractStatusModel);

        salesContractModel.setSalesContractReceivePlans(null);
        //salesContractModel.setSalesContractProductModel(salesContractProducts);
        salesContractModel.setProcessInstanceId(null);
        salesContractModel.setContractState(SalesContractConstant.CONTRACT_STATE_SH);
        getDao().save(salesContractModel);
        getDao().flush();
        //salesReceivePlanDao.saveOrUpdateAll(receivePlans);
        // salesProductDao.saveOrUpdateAll(salesContractProducts);
        //创建工单
        String procDefKey = SalesContractConstant.CONTRACT_PROCDEFKEY;
        creatProcInst(salesContractModel, systemUser, procDefKey);
    }

    /**
     * 只修改收款计划的保存
     * @param salesContractModel
     * @param receivePlans
     */
    public void changeReceivePlan(SalesContractModel salesContractModel, List<SalesContractReceivePlanModel> receivePlans) {
        salesContractModel.setIsChanged(1);
        salesContractModel.setSalesContractReceivePlans(null);
        //合同信息
        getDao().update(salesContractModel);
        //收款计划
        salesReceivePlanDao.saveOrUpdateAll(receivePlans);
        //合同状态
        SalesContractStatusModel contractStatusModel = salesContractStatusDao.findContractStatusByContractId(salesContractModel.getId());
        contractStatusModel.setChangeStatus(SalesContractConstant.CONTRACT_CHANGE_INITSTATE);
        salesContractStatusDao.update(contractStatusModel);
    }

    /**
     * <code>deleteSalesRelateOrders</code>
     * 删除合同和订单的关联以及关联的合同产品
     * @param contractModel
     * @since   2014年12月12日    guokemenng
     */
    public void deleteSalesRelateOrders(SalesContractModel contractModel) {
        Set<BusinessOrderModel> orderSet = contractModel.getBusinessOrderModel();

        String sql0 = "delete from business_order_product where saleContractId = ?";
        Object[] param = new Object[] { contractModel.getId() };
        getDao().createSQLQuery(sql0, param).executeUpdate();

        String sql1 = "delete from order_contract_map where sales_contract = ?";
        Object[] param1 = new Object[] { contractModel.getId() };
        getDao().createSQLQuery(sql1, param1).executeUpdate();
        getDao().flush();

        Iterator<BusinessOrderModel> it = orderSet.iterator();
        while (it.hasNext()) {
            BusinessOrderModel order = it.next();
            if (order.getOrderStatus().equals(BusinessOrderContant.ORDER_SH)) {
                try {
                    processInstService.removeProcessInst(order.getProcessInstanceId().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            order.setOrderStatus(BusinessOrderContant.ORDER_CG);
            orderDao.update(order);
            processService.sendEmail("订单[" + order.getOrderName() + "]回退", "因为订单[" + order.getOrderCode() + "]相关的合同变更了合同主体,因此退回此订单.", null, null, order.getCreatorId(), null, null);
        }
    }

    /**
     *
     * @param salesContractModel
     * @param newSalesContractModel
     * @throws Exception
     */
    public void changeSalesOther(SalesContractModel salesContractModel, SalesContractDto newSalesContractModel) throws Exception {
        salesContractModel.setIsChanged(1);
        salesContractModel.setContractName(newSalesContractModel.getContractName());
        salesContractModel.setServiceStartDate(newSalesContractModel.getServiceStartDate());
        salesContractModel.setServiceEndDate(newSalesContractModel.getServiceEndDate());
        salesContractModel.setEstimateProfit(newSalesContractModel.getEstimateProfit());
        salesContractModel.setEstimateProfitDesc(newSalesContractModel.getEstimateProfitDesc());
        salesContractModel.setAccountCurrency(newSalesContractModel.getAccountCurrency());
        salesContractModel.setAccountCurrency(newSalesContractModel.getAccountCurrency());
        salesContractModel.setAccountCurrency(newSalesContractModel.getAccountCurrency());
        salesContractModel.setInvoiceType(newSalesContractModel.getInvoiceType());
        salesContractModel.setReceiveWay(newSalesContractModel.getReceiveWay());
        salesContractModel.setAttachIds(newSalesContractModel.getAttachIds());
        salesContractModel.setHopeArriveTime(newSalesContractModel.getHopeArriveTime());
        getDao().update(salesContractModel);

        SalesContractStatusModel contractStatusModel = salesContractStatusDao.findContractStatusByContractId(salesContractModel.getId());
        contractStatusModel.setChangeStatus(SalesContractConstant.CONTRACT_CHANGE_INITSTATE);
        salesContractStatusDao.update(contractStatusModel);
    }

    /**
     * <code>createSalesByChangeOther</code>
     * 合同变更其他类型操作
     * @param salesContractModel
     * @param newSalesContractModel
     * @throws Exception
     * @since   2014年12月12日    guokemenng
     */
    public void updateSalesByChangeOther(SalesContractModel salesContractModel, SalesContractModel newSalesContractModel) throws Exception {
        salesContractModel.setIsChanged(1);
        salesContractModel.setContractName(newSalesContractModel.getContractName());
        salesContractModel.setServiceStartDate(newSalesContractModel.getServiceStartDate());
        salesContractModel.setServiceEndDate(newSalesContractModel.getServiceEndDate());
        salesContractModel.setEstimateProfit(newSalesContractModel.getEstimateProfit());
        salesContractModel.setEstimateProfitDesc(newSalesContractModel.getEstimateProfitDesc());
        salesContractModel.setAccountCurrency(newSalesContractModel.getAccountCurrency());
        salesContractModel.setAccountCurrency(newSalesContractModel.getAccountCurrency());
        salesContractModel.setAccountCurrency(newSalesContractModel.getAccountCurrency());
        salesContractModel.setInvoiceType(newSalesContractModel.getInvoiceType());
        salesContractModel.setReceiveWay(newSalesContractModel.getReceiveWay());
        salesContractModel.setAttachIds(newSalesContractModel.getAttachIds());
        salesContractModel.setHopeArriveTime(newSalesContractModel.getHopeArriveTime());
        getDao().update(salesContractModel);

        SalesContractStatusModel contractStatusModel = salesContractStatusDao.findContractStatusByContractId(salesContractModel.getId());
        contractStatusModel.setChangeStatus(SalesContractConstant.CONTRACT_CHANGE_INITSTATE);
        salesContractStatusDao.update(contractStatusModel);
    }

    /**
     * <code>getSaleStateById</code>
     *
     * @param saleId
     * @return
     * @since   2014年11月17日    guokemenng
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> getSaleStateById(Long saleId) {
        String sql = "select c.id,c.contractName,c.ContractCode,c.ContractAmount,c.ContractState,cs.OrderStatus,cs.ReciveStatus,cs.CachetStatus,cs.InvoiceStatus,cs.changeStatus,c.isChanged,art.NAME_,art.CREATE_TIME_,art.ASSIGNEE_ from Sales_Contract c left join Sales_Contract_Status cs on c.id=cs.SaleContractId LEFT JOIN bpm_process_inst bpi ON c.ProcessInstanceId=bpi.ID_  LEFT JOIN act_ru_task art ON bpi.HI_PROC_INST_ID= art.proc_inst_id_ where 0=0 and cs.contractSnapShootId IS NULL and c.Id = ?";
        List<Map<String, Object>> mapList = this.getDao().createSQLQuery(sql, new Object[] { saleId }).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        if (mapList.size() > 0) {
            return mapList.get(0);
        } else {
            return null;
        }
    }

    /**
     * <code>getSimpleDataXml</code>
     * 得到销售部合同金额
     * @return
     * @since   2014年11月18日    guokemenng
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> getSimpleDataXml() {
        StringBuilder sb = new StringBuilder();
        sb.append("select t.orgName,SUM(t.amount) totalAmount from ");
        sb.append("(SELECT case ");
        sb.append("when c.orgId like '1101%' then '销售一部' ");
        sb.append("when c.orgId like '1102%' then '销售二部' ");
        sb.append("when c.orgId like '1103%' then '销售三部' ");
        sb.append("else null end orgName, ");
        sb.append("c.orgId,a.creator,SUM(a.contractamount) amount ");
        sb.append("FROM sales_contract a,sys_stafforg b,sys_orgnization c ");
        sb.append("WHERE a.creator=b.staffid AND c.orgid=b.orgid ");
        sb.append("AND (c.orgcode like '1101%' or c.orgcode like '1102%' or c.orgcode like '1103%') ");
        sb.append("GROUP BY a.creator ");
        sb.append(") t group by t.orgName");
        return getDao().createSQLQuery(sb.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
    }

    /**
     * <code>getSaleStateById</code>
     *
     * @param saleId
     * @return
     * @since   2014年11月17日    guokemenng
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> getProductDifferent(Long id) {
        String sql = "SELECT cp.`ProductTypeName` typeName,cp.ProductPartnerName partnerName, cp.`ProductName` pName,cp.`Quantity` contractQua,b.`OrderName` oName, bp.`Quantity` orderQua FROM  `sales_contract` c LEFT JOIN `sales_contract_product` cp ON c.id=cp.SaleContractId LEFT JOIN `business_order_product` bp ON cp.id=bp.`salesContractProductId` LEFT JOIN  `business_order` b ON bp.`OrderId`=b.`id` WHERE c.`id`=? GROUP BY cp.ProductNo,b.`OrderName`";
        Object[] params = new Object[1];
        params[0] = id;
        Query query = getDao().createSQLQuery(sql, params).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List<Map<String, Object>> obList = query.list();

        return obList;
    }

    /**
     * <code>getFeeIncomeBySalesId</code>
     * 收款确认明细
     * 2015-01-08 收款人改出出纳 by tigq
     * @param salseId
     * @return
     * @since   2014年12月12日    guokemenng
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> getFeeIncomeBySalesId(Long salseId) {
        String sql = " select f.Amount,i.ReceiveTime,sys.staffName Creator,i.CreateTime,i.Remark from business_sales_feeIncome f left join business_feeincom i on i.id = f.feeIncomeId left join sys_staff sys on sys.staffId = i.treasurer where f.salesId = ?";
        Object[] params = new Object[1];
        params[0] = salseId;
        Query query = getDao().createSQLQuery(sql, params).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List<Map<String, Object>> obList = query.list();
        return obList;
    }

    /**
     * <code>exportSales</code>
     * 合同查询导出Excel
     * @param searchMap
     * @return
     * @since   2014年12月18日    guokemenng
     */
    @SuppressWarnings("unchecked")
    public List<SalesContractInfoAndStatus> exportSales(HashMap<String, Object> searchMap) {
        int start = 0;
        Object[] values = new Object[searchMap.size()];
        Object creatorString = searchMap.get(SalesContractModel.CREATOR);
        Object contractName = searchMap.get(SalesContractModel.CONTRACTNAME);
        Object contractShortname = searchMap.get(SalesContractModel.CONTRACTSHORTNAME);
        Object contractState = searchMap.get(SalesContractModel.CONTRACTSTATE);
        Object contractCode = searchMap.get(SalesContractModel.CONTRACTCODE);
        Object contractAmountString = searchMap.get(SalesContractModel.CONTRACTAMOUNT);

        //        Object contractAmountStringb = searchMap.get("contractAmountb");

        Object contractStateNotEquals = searchMap.get("contractStateNotEquals");
        Object contractType = searchMap.get(SalesContractModel.CONTRACTTYPE);
        Object startTime = searchMap.get(SalesContractModel.CLOSETIME);
        Object endTime = searchMap.get("endTime");
        Object customerInfo = searchMap.get(SalesContractModel.CUSTOMERID);
        String hql = "select c.id,c.contractName,c.ContractCode,c.ContractAmount,c.ContractState,cs.OrderStatus,cs.ReciveStatus,cs.CachetStatus,cs.InvoiceStatus,cs.changeStatus,c.isChanged,art.NAME_,art.CREATE_TIME_,art.ASSIGNEE_ ,c.ContractType,c.CreatorName,c.CreateTime,cust.ShortName from Sales_Contract c left join Sales_Contract_Status cs on c.id=cs.SaleContractId left join  sales_contract_product p on c.id = p.SaleContractId LEFT JOIN bpm_process_inst bpi ON c.ProcessInstanceId=bpi.ID_  LEFT JOIN act_ru_task art ON bpi.HI_PROC_INST_ID= art.proc_inst_id_  LEFT JOIN customer_info cust ON c.CustomerId=cust.id where 0=0 and cs.contractSnapShootId IS NULL";
        if (creatorString != null) {
            Long creator = Long.parseLong(creatorString + "");
            values[start++] = creator;
            hql += " and c." + SalesContractModel.CREATOR + "=?";
        }
        if (contractName != null) {
            values[start++] = contractName + "";
            hql += " and c." + SalesContractModel.CONTRACTNAME + " like ?";
        }
        if (contractShortname != null) {
            values[start++] = contractShortname + "";
            hql += " and c." + SalesContractModel.CONTRACTSHORTNAME + " like ?";
        }
        if (contractState != null) {
            values[start++] = contractState + "";
            hql += " and c." + SalesContractModel.CONTRACTSTATE + "=?";
        }
        if (contractStateNotEquals != null) {
            values[start++] = contractStateNotEquals + "";
            hql += " and c." + SalesContractModel.CONTRACTSTATE + "!=?";
        }
        if (contractCode != null) {
            values[start++] = contractCode + "";
            hql += " and c." + SalesContractModel.CONTRACTCODE + " like ?";
        }
        if (contractType != null) {
            values[start++] = contractType + "";
            hql += " and c." + SalesContractModel.CONTRACTTYPE + " =?";
        }
        if (contractAmountString != null) {
            // float contractAmount = Float.parseFloat(contractAmountString + "");
            values[start++] = contractAmountString + ".%";
            hql += " and c." + SalesContractModel.CONTRACTAMOUNT + " like ? ";
        }

        if (startTime != null) {
            values[start++] = startTime + "";
            hql += " and c." + SalesContractModel.CLOSETIME + " >=?";
        }
        if (endTime != null) {
            values[start++] = endTime + "";
            hql += " and c." + SalesContractModel.CLOSETIME + " <=?";
        }
        if (customerInfo != null) {
            values[start++] = customerInfo + "";
            hql += " and c." + SalesContractModel.CUSTOMERID + " =?";
        }
        hql += " group by c.id order By c.id DESC";

        List<Object> items = getDao().createSQLQuery(hql, values).list();

        List<SalesContractInfoAndStatus> list = new ArrayList<SalesContractInfoAndStatus>();

        for (int i = 0; i < items.size(); i++) {
            SalesContractInfoAndStatus salesContractInfoAndStatus = new SalesContractInfoAndStatus();
            Object[] entity = (Object[]) items.get(i);
            salesContractInfoAndStatus.setSalesContractId(entity[0] == null ? null : Long.parseLong(entity[0].toString()));//合同ID
            salesContractInfoAndStatus.setContractName(entity[1] == null ? "" : entity[1].toString());
            salesContractInfoAndStatus.setContractCode(entity[2] == null ? "" : entity[2].toString());
            salesContractInfoAndStatus.setContractAmount(entity[3] == null ? null : entity[3].toString());
            String entity4 = entity[4] == null ? "" : entity[4].toString();
            if (entity4.equals(SalesContractConstant.CONTRACT_STATE_CG)) {
                entity4 = "草稿";
            } else if (entity4.equals(SalesContractConstant.CONTRACT_STATE_SH)) {
                entity4 = "审核中";
            } else if (entity4.equals(SalesContractConstant.CONTRACT_STATE_TGSH)) {
                entity4 = "审核通过";
            } else if (entity4.equals(SalesContractConstant.CONTRACT_STATE_FQ)) {
                entity4 = "废弃";
            } else if (entity4.equals(SalesContractConstant.CONTRACT_STATE_CXTJ)) {
                entity4 = "重新提交";
            } else if (entity4.equals(SalesContractConstant.CONTRACT_STATE_DGB)) {
                entity4 = "待关闭";
            } else if (entity4.equals(SalesContractConstant.CONTRACT_STATE_CBYG)) {
                entity4 = "成本预估";
            }
            salesContractInfoAndStatus.setContractState(entity4);
            salesContractInfoAndStatus.setOrderStatus(entity[5] == null ? "" : "合同订单状态：" + entity[5]);
            salesContractInfoAndStatus.setReciveStatus(entity[6] == null ? "" : "收款状态：" + entity[6]);
            salesContractInfoAndStatus.setCachetStatus(entity[7] == null ? "" : "盖章状态：" + entity[7]);
            salesContractInfoAndStatus.setInvoiceStatus(entity[8] == null ? "" : "发票状态：" + entity[8]);
            salesContractInfoAndStatus.setChangeStatus(entity[9] == null ? "" : entity[9].toString());
            salesContractInfoAndStatus.setIsChanged(entity[10] == null ? null : Integer.parseInt(entity[10].toString()));
            if (entity[11] == null && entity4.equals("审核通过")) {
                salesContractInfoAndStatus.setCurrentNode("合同审批完成");
            } else {
                salesContractInfoAndStatus.setCurrentNode(entity[11] == null ? "" : entity[11].toString());
            }

            salesContractInfoAndStatus.setTaskReachTime(entity[12] == null ? null : (Date) entity[12]);

            if (entity[13] != null) {
                String staffName = ((SysStaff) systemUserCache.get(entity[13].toString()).getObjectValue()).getStaffName();
                salesContractInfoAndStatus.setDealUserName(staffName);
            } else {
                salesContractInfoAndStatus.setDealUserName("");
            }
            salesContractInfoAndStatus.setContractTypeName(sysDomainService.getValueMeaning("contractType", entity[14].toString()));

            salesContractInfoAndStatus.setCreatorName(entity[15] == null ? "" : (entity[15].toString()));
            salesContractInfoAndStatus.setCreateTime(entity[16] == null ? null : (Date) entity[16]);
            salesContractInfoAndStatus.setCustomerName(entity[17] == null ? "" : (entity[17].toString()));
            list.add(salesContractInfoAndStatus);
        }
        return list;
    }

    /**
     * <code>getSaleContractBuyers</code>
     * 得到采购员的名称
     * @param buyers
     * @return
     * @since   2014年12月23日    guokemenng
     */
    public String getSaleContractBuyers(String buyers) {
        StringBuilder sb = new StringBuilder();
        String[] ss = buyers.split(",");
        for (int i = 0; i < ss.length; i++) {
            String staffName = ((SysStaff) systemUserCache.get(ss[i]).getObjectValue()).getStaffName();
            if (i == ss.length - 1) {
                sb.append(staffName);
            } else {
                sb.append(staffName + ",");
            }
        }
        return sb.toString();
    }

    /**
     * 根据合同查询订单显示实体
     * @param saleid
     * @return
     */
    public List<Map<String, Object>> getOrderInfo(Long saleid) {
        List<Map<String, Object>> rs = null;
        String sql = "SELECT * FROM business_order a INNER JOIN order_contract_map b ON a.`id`=b.`business_order` INNER JOIN sales_contract c ON b.`sales_contract`=c.id WHERE c.`id`='" + saleid + "'";
        Query query = getDao().createSQLQuery(sql);
        rs = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();

        return rs;
    }

    /**
     * 查询合同下，订单的产品信息
     * @param salesid
     * @param orderid
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> getOrderProductDifferent(Long salesid, Long orderid) {
        String sql = "SELECT cp.`ProductTypeName` typeName,cp.ProductPartnerName partnerName, cp.`ProductName` pName, bp.`Quantity` orderQua,bp.unitprice unitprice,bp.`SubTotal` subtotal,  bp.`Quantity` ordernum FROM  `sales_contract` c LEFT JOIN `sales_contract_product` cp ON c.id=cp.SaleContractId LEFT JOIN `business_order_product` bp ON cp.id=bp.`salesContractProductId` LEFT JOIN  `business_order` b ON bp.`OrderId`=b.`id` WHERE c.`id`='" + salesid + "' AND bp.`OrderId`='" + orderid + "'";
        Object[] params = new Object[2];
        params[0] = salesid;
        params[1] = orderid;
        Query query = getDao().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List<Map<String, Object>> obList = query.list();

        return obList;
    }

    /**
     * @param searchMap
     * @return
     */
    public String findTotallAmount(HashMap<String, Object> searchMap) {
        String amount = getDao().findTotallAmount(searchMap);
        return amount;
    }

    /**
     * @param userId
     * @return
     */
    public List<Map<String, Object>> findOrg(String userId) {
        List<Map<String, Object>> orgName = getDao().findOrg(userId);
        return orgName;
    }

    /**
     * @param userId
     * @return
     */
    public int findroles(String userId) {
        int flat = getDao().findroles(userId);
        return flat;
    }

    /**
     * <code>getContractMergeList</code>
     * 得到要合并的合同列表
     * @param customerId
     * @param pageNo
     * @param pageSize
     * @return
     * @since   2015年3月24日    guokemenng
     */
    @SuppressWarnings("unchecked")
    public PaginationSupport getContractMergeList(String userId, String name, String customerId, Integer pageNo, Integer pageSize) {

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT a.`id`,a.`ContractName` AS name,a.`ContractCode` AS code,a.`CustomerId`,c.sQuantity,e.oQuantity FROM `sales_contract` a ");
        sb.append("LEFT JOIN (SELECT b.`SaleContractId`,SUM(b.`Quantity`) AS sQuantity FROM `sales_contract_product` b GROUP BY b.`SaleContractId`) c ON a.`id` = c.SaleContractId ");
        sb.append("LEFT JOIN (SELECT d.`SaleContractId`,SUM(d.`Quantity`) AS oQuantity FROM `business_order_product` d GROUP BY d.`SaleContractId`) e ON a.id = e.SaleContractId ");
        //        sb.append("LEFT JOIN `sales_contract_status` f ON a.`id` = f.`SaleContractId` WHERE f.`ChangeStatus` = '未申请' AND f.`CachetStatus` = '未申请' AND f.`InvoiceStatus` = '未申请' AND sQuantity = e.oQuantity ");
        sb.append("LEFT JOIN `sales_contract_status` f ON a.`id` = f.`SaleContractId` WHERE f.`ChangeStatus` = '未申请' AND f.`CachetStatus` = '未申请' AND sQuantity = e.oQuantity ");
        sb.append("AND a.`ContractState` = 'TGSH' AND a.`ContractType` != '3000' AND a.`ContractType` != '9000' ");

        sb.append("AND a.Creator='" + userId + "' ");
        sb.append("AND (a.ContractName like '%" + name + "%' OR a.`ContractCode` LIKE '%" + name + "%') ");

        if (!StringUtil.isEmpty(customerId)) {
            sb.append("AND a.CustomerId = '" + customerId + "' ");
        }

        Query query = getDao().createSQLQuery(sb.toString());
        query.setFirstResult(pageNo * pageSize);
        query.setMaxResults(pageSize);
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List<Map<String, Object>> mapList = query.list();

        PaginationSupport pg = new PaginationSupport(mapList, getContractMergeListCount(sb.toString()));

        return pg;
    }

    /**
     * <code>getContractMergeListCount</code>
     * 得到要合并合同的条目总数量
     * @param sql
     * @return
     * @since   2015年3月24日    guokemenng
     */
    public Integer getContractMergeListCount(String sql) {
        sql = "select count(*) from (" + sql + " ) as total";
        return Integer.valueOf(getDao().createSQLQuery(sql).list().get(0).toString()).intValue();
    }

    /**
     * <code>getSalesByIds</code>
     * 根据IDs得到合同List id格式（XX,XX）
     * @param ids
     * @return
     * @since   2015年3月25日    guokemenng
     */
    public List<SalesContractModel> getSalesByIds(String ids) {
        String hql = "from SalesContractModel where id in " + SQLUtil.makeInStr(ids.split(","));
        return getDao().find(hql);
    }

    /**
     * <code>updateSalesState</code>
     * 根据ID更新合同的状态 为HB id格式（XX,XX）
     * @param ids
     * @since   2015年3月25日    guokemenng
     */
    public void updateSalesState(String ids, String state) {
        String sql = "update sales_contract c set c.ContractState = '" + state + "' where c.id in " + SQLUtil.makeInStr(ids.split(","));
        getDao().createSQLQuery(sql).executeUpdate();
    }

    public void mergeContracts(String ids) {

    }

    /**
     * @param salesContractDto
     * @param newSalesContractModel
     * @return
     */
    public Set<SalesContractProductModel> salesContractProductsMAByAm(SalesContractDto salesContractDto, SalesContractModel salesContractModel) {
        //仅有日期的格式
        String DatePattern = "yyyy-MM-dd";
        //删除原有合同清单数据
        //        Session session = getDao().getSessionFactory().openSession();
        //        Transaction tx = session.beginTransaction();
        //        String sql = "delete from sales_contract_product where SaleContractId='" + salesContractModel.getId() + "'";
        //        session.createSQLQuery(sql).executeUpdate();
        //        tx.commit();
        //        session.close();
        /*getDao().deleteSalesProducts(salesContractModel.getId().toString());
        getDao().flush();*/
        Set<SalesContractProductModel> salesContractProducts = new HashSet<SalesContractProductModel>();
        List<SalesContractProductModel> salesContractProducts1 = new ArrayList<SalesContractProductModel>();

        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();

        long[] contractProductIds = salesContractDto.getContractProductIds();
        /*long[] productTypes = salesContractDto.getProductTypes();
        String[] productTypeNames = salesContractDto.getProductTypeNames();*/
        long[] productPartners = salesContractDto.getProductPartners();
        String[] productPartnerNames = salesContractDto.getProductPartnerNames();
        long[] productNos = salesContractDto.getProductNos();
        String[] productNames = salesContractDto.getProductNames();
        int[] quantitys = salesContractDto.getQuantitys();
        float[] unitPrices = salesContractDto.getUnitPrices();
        float[] totalPrices = salesContractDto.getTotalPrices();

        String[] serviceStartDates = salesContractDto.getServiceStartDates();
        String[] serviceEndDates = salesContractDto.getServiceEndDates();
        String[] productRemarks = salesContractDto.getProductRemarks();

        Long[] relateContractProductIds = salesContractDto.getRelateContractProductId();
        Long[] relateDeliveryProductIds = salesContractDto.getRelateDeliveryProductId();
        String[] equipmentSplace = salesContractDto.getEquipmentSplace();
        String[] serialNumber = salesContractDto.getSerialNumber();
        int[] servicePeriod = salesContractDto.getServicePeriod();

        if (quantitys != null) {
            Set<SalesContractProductModel> sets = salesContractModel.getSalesContractProductModel();
            for (int i = 0; i < quantitys.length; i++) {
                SalesContractProductModel salesContractProduct = null;
                if (contractProductIds != null && i < contractProductIds.length) {
                    for (SalesContractProductModel salesContractProductModel : sets) {
                        if (salesContractProductModel.getId() == contractProductIds[i]) {
                            salesContractProduct = salesContractProductModel;
                            break;
                        }
                    }
                } else {
                    salesContractProduct = new SalesContractProductModel();
                }
                /*if (salesContractProduct == null) {
                    salesContractProduct = new SalesContractProductModel();
                }*/
                //salesContractProduct = salesContractProductService.get(contractProductIds[i]);
                //salesContractProduct.setId(contractProductIds[i]);
                salesContractProduct.setEquipmentSplace(equipmentSplace[i]);
                //salesContractProduct.setProductType(productTypes[i]);
                //salesContractProduct.setProductTypeName(productTypeNames[i]);
                salesContractProduct.setProductPartner(productPartners[i]);
                salesContractProduct.setProductPartnerName(productPartnerNames[i]);
                salesContractProduct.setProductName(productNames[i]);
                salesContractProduct.setServicePeriod(servicePeriod[i]);
                salesContractProduct.setSerialNumber(serialNumber[i]);
                salesContractProduct.setServicePeriod(servicePeriod[i]);
                salesContractProduct.setSerialNumber(serialNumber[i]);

                if (serviceStartDates.length > 0 && !StringUtil.isEmpty(serviceStartDates[i])) {
                    salesContractProduct.setServiceStartDate(DateUtils.convertStringToDate(serviceStartDates[i], DatePattern));
                }
                if (serviceEndDates.length > 0 && !StringUtil.isEmpty(serviceEndDates[i])) {
                    salesContractProduct.setServiceEndDate(DateUtils.convertStringToDate(serviceEndDates[i], DatePattern));
                }

                if (productRemarks != null && productRemarks.length > 0 && !StringUtil.isEmpty(productRemarks[i])) {
                    salesContractProduct.setRemark(productRemarks[i]);
                }

                //                salesContractProduct.setProductName(productNames[i]);
                salesContractProduct.setProductNo(productNos[i]);
                salesContractProduct.setSalesContractModel(salesContractModel);
                salesContractProduct.setQuantity(quantitys[i]);
                salesContractProduct.setTotalPrice(new BigDecimal(totalPrices[i]));
                salesContractProduct.setUnitPrice(new BigDecimal(unitPrices[i]));
                salesContractProduct.setSalesContractModel(salesContractModel);
                if (relateDeliveryProductIds != null) {
                    if (relateDeliveryProductIds.length > 0 && relateDeliveryProductIds[i] != null) {
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("relateDeliveryProductId", relateDeliveryProductIds[i]);
                        map.put("productNum", quantitys[i]);
                        map.put("relateContractProductId", relateContractProductIds[i]);
                        mapList.add(map);

                        salesContractProduct.setRelateDeliveryProductId(relateDeliveryProductIds[i]);

                        salesContractProduct.setIsReady(1);

                    }
                }

                salesContractProducts.add(salesContractProduct);
                salesContractProducts1.add(salesContractProduct);
            }
        }
        //处理关联合同产品
        if (mapList.size() > 0) {
            handlerRelateContractProducts(mapList, salesContractProducts1);
        }
        salesProductDao.saveOrUpdateAll(salesContractProducts);
        return salesContractProducts;
    }

    /**
     * @param salesContractDto
     * @param newSalesContractModel
     * @return
     */
    public Set<SalesContractProductModel> salesContractProductsMAByPro(SalesContractDto salesContractDto, SalesContractModel salesContractModel) {
        //仅有日期的格式
        String DatePattern = "yyyy-MM-dd";
        //删除原有合同清单数据
        //        Session session = getDao().getSessionFactory().openSession();
        //        Transaction tx = session.beginTransaction();
        //        String sql = "delete from sales_contract_product where SaleContractId='" + salesContractModel.getId() + "'";
        //        session.createSQLQuery(sql).executeUpdate();
        //        tx.commit();
        //        session.close();
        Set<SalesContractProductModel> salesContractProducts = new HashSet<SalesContractProductModel>();
        List<SalesContractProductModel> salesContractProducts1 = new ArrayList<SalesContractProductModel>();

        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        /*  long[] contractProductIds = salesContractDto.getContractProductIds();
          long[] productTypes = salesContractDto.getProductTypes();
          String[] productTypeNames = salesContractDto.getProductTypeNames();*/
        long[] productPartners = salesContractDto.getProductPartners();
        String[] productPartnerNames = salesContractDto.getProductPartnerNames();
        long[] productNos = salesContractDto.getProductNos();
        String[] productNames = salesContractDto.getProductNames();
        int[] quantitys = salesContractDto.getQuantitys();
        float[] unitPrices = salesContractDto.getUnitPrices();
        float[] totalPrices = salesContractDto.getTotalPrices();

        String[] serviceStartDates = salesContractDto.getServiceStartDates();
        String[] serviceEndDates = salesContractDto.getServiceEndDates();
        String[] productRemarks = salesContractDto.getProductRemarks();

        Long[] relateContractProductIds = salesContractDto.getRelateContractProductId();
        Long[] relateDeliveryProductIds = salesContractDto.getRelateDeliveryProductId();

        String[] equipmentSplace = salesContractDto.getEquipmentSplace();
        String[] serialNumber = salesContractDto.getSerialNumber();
        int[] servicePeriod = salesContractDto.getServicePeriod();

        if (quantitys != null) {
            // Set<SalesContractProductModel> sets = salesContractModel.getSalesContractProductModel();
            for (int i = 0; i < quantitys.length; i++) {
                SalesContractProductModel salesContractProduct = null;
                /* if (contractProductIds != null && i < contractProductIds.length) {
                     for (SalesContractProductModel salesContractProductModel : sets) {
                         if (salesContractProductModel.getId() == contractProductIds[i]) {
                             salesContractProduct = salesContractProductModel;
                             break;
                         }
                     }
                 } else {
                     salesContractProduct = new SalesContractProductModel();
                 }*/
                if (salesContractProduct == null) {
                    salesContractProduct = new SalesContractProductModel();
                }
                /*salesContractProduct.setProductType(productTypes[i]);
                salesContractProduct.setProductTypeName(productTypeNames[i]);*/
                salesContractProduct.setProductPartner(productPartners[i]);
                salesContractProduct.setProductPartnerName(productPartnerNames[i]);
                salesContractProduct.setProductName(productNames[i]);

                if (serviceStartDates.length > 0 && !StringUtil.isEmpty(serviceStartDates[i])) {
                    salesContractProduct.setServiceStartDate(DateUtils.convertStringToDate(serviceStartDates[i], DatePattern));
                }
                if (serviceEndDates.length > 0 && !StringUtil.isEmpty(serviceEndDates[i])) {
                    salesContractProduct.setServiceEndDate(DateUtils.convertStringToDate(serviceEndDates[i], DatePattern));
                }

                if (productRemarks != null && productRemarks.length > 0 && !StringUtil.isEmpty(productRemarks[i])) {
                    salesContractProduct.setRemark(productRemarks[i]);
                }

                //                salesContractProduct.setProductName(productNames[i]);
                salesContractProduct.setProductNo(productNos[i]);
                salesContractProduct.setSalesContractModel(salesContractModel);
                salesContractProduct.setQuantity(quantitys[i]);
                salesContractProduct.setTotalPrice(new BigDecimal(totalPrices[i]));
                salesContractProduct.setUnitPrice(new BigDecimal(unitPrices[i]));
                salesContractProduct.setSalesContractModel(salesContractModel);
                salesContractProduct.setServicePeriod(servicePeriod[i]);
                salesContractProduct.setSerialNumber(serialNumber[i]);
                salesContractProduct.setEquipmentSplace(equipmentSplace[i]);
                if (relateDeliveryProductIds != null) {
                    if (relateDeliveryProductIds.length > 0 && relateDeliveryProductIds[i] != null) {
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("relateDeliveryProductId", relateDeliveryProductIds[i]);
                        map.put("productNum", quantitys[i]);
                        map.put("relateContractProductId", relateContractProductIds[i]);
                        mapList.add(map);

                        salesContractProduct.setRelateDeliveryProductId(relateDeliveryProductIds[i]);

                        salesContractProduct.setIsReady(1);

                    }
                }

                salesContractProducts.add(salesContractProduct);
                salesContractProducts1.add(salesContractProduct);
            }
        }
        //处理关联合同产品
        if (mapList.size() > 0) {
            handlerRelateContractProducts(mapList, salesContractProducts1);
        }
        salesProductDao.saveOrUpdateAll(salesContractProducts);
        return salesContractProducts;
    }

    /**
     * @param newSalesContractModel
     */
    public void deleteSalesProducts(SalesContractModel salesContractModel) {
        getDao().deleteSalesProducts(salesContractModel.getId().toString());
    }

    /**
     * @param salesContractDto
     * @param newSalesContractModel
     * @return
     */
    public Set<SalesContractProductModel> salesContractProductsByPro(SalesContractDto salesContractDto, SalesContractModel salesContractModel) {
        //仅有日期的格式
        String DatePattern = "yyyy-MM-dd";
        //删除原有合同清单数据
        //        Session session = getDao().getSessionFactory().openSession();
        //        Transaction tx = session.beginTransaction();
        //        String sql = "delete from sales_contract_product where SaleContractId='" + salesContractModel.getId() + "'";
        //        session.createSQLQuery(sql).executeUpdate();
        //        tx.commit();
        //        session.close();
        /*getDao().deleteSalesProducts(salesContractModel.getId().toString());
        getDao().flush();*/
        Set<SalesContractProductModel> salesContractProducts = new HashSet<SalesContractProductModel>();
        List<SalesContractProductModel> salesContractProducts1 = new ArrayList<SalesContractProductModel>();

        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();

        //long[] contractProductIds = salesContractDto.getContractProductIds();
        long[] productTypes = salesContractDto.getProductTypes();
        String[] productTypeNames = salesContractDto.getProductTypeNames();
        long[] productPartners = salesContractDto.getProductPartners();
        String[] productPartnerNames = salesContractDto.getProductPartnerNames();
        long[] productNos = salesContractDto.getProductNos();
        String[] productNames = salesContractDto.getProductNames();
        int[] quantitys = salesContractDto.getQuantitys();
        float[] unitPrices = salesContractDto.getUnitPrices();
        float[] totalPrices = salesContractDto.getTotalPrices();

        String[] serviceStartDates = salesContractDto.getServiceStartDates();
        String[] serviceEndDates = salesContractDto.getServiceEndDates();
        String[] productRemarks = salesContractDto.getProductRemarks();

        Long[] relateContractProductIds = salesContractDto.getRelateContractProductId();
        Long[] relateDeliveryProductIds = salesContractDto.getRelateDeliveryProductId();

        if (quantitys != null) {
            Set<SalesContractProductModel> sets = salesContractModel.getSalesContractProductModel();
            for (int i = 0; i < quantitys.length; i++) {
                SalesContractProductModel salesContractProduct = null;
                /* if (contractProductIds != null && i < contractProductIds.length) {
                     for (SalesContractProductModel salesContractProductModel : sets) {
                         if (salesContractProductModel.getId() == contractProductIds[i]) {
                             salesContractProduct = salesContractProductModel;
                             break;
                         }
                     }
                 } else {
                     salesContractProduct = new SalesContractProductModel();
                 }*/
                if (salesContractProduct == null) {
                    salesContractProduct = new SalesContractProductModel();
                }
                //salesContractProduct = salesContractProductService.get(contractProductIds[i]);
                //salesContractProduct.setId(contractProductIds[i]);
                salesContractProduct.setProductType(productTypes[i]);
                salesContractProduct.setProductTypeName(productTypeNames[i]);
                salesContractProduct.setProductPartner(productPartners[i]);
                salesContractProduct.setProductPartnerName(productPartnerNames[i]);
                salesContractProduct.setProductName(productNames[i]);

                if (serviceStartDates.length > 0 && !StringUtil.isEmpty(serviceStartDates[i])) {
                    salesContractProduct.setServiceStartDate(DateUtils.convertStringToDate(serviceStartDates[i], DatePattern));
                }
                if (serviceEndDates.length > 0 && !StringUtil.isEmpty(serviceEndDates[i])) {
                    salesContractProduct.setServiceEndDate(DateUtils.convertStringToDate(serviceEndDates[i], DatePattern));
                }

                if (productRemarks != null && productRemarks.length > 0 && !StringUtil.isEmpty(productRemarks[i])) {
                    salesContractProduct.setRemark(productRemarks[i]);
                }

                //                salesContractProduct.setProductName(productNames[i]);
                salesContractProduct.setProductNo(productNos[i]);
                salesContractProduct.setSalesContractModel(salesContractModel);
                salesContractProduct.setQuantity(quantitys[i]);
                salesContractProduct.setTotalPrice(new BigDecimal(totalPrices[i]));
                salesContractProduct.setUnitPrice(new BigDecimal(unitPrices[i]));
                salesContractProduct.setSalesContractModel(salesContractModel);
                if (relateDeliveryProductIds != null) {
                    if (relateDeliveryProductIds.length > 0 && relateDeliveryProductIds[i] != null) {
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("relateDeliveryProductId", relateDeliveryProductIds[i]);
                        map.put("productNum", quantitys[i]);
                        map.put("relateContractProductId", relateContractProductIds[i]);
                        mapList.add(map);

                        salesContractProduct.setRelateDeliveryProductId(relateDeliveryProductIds[i]);

                        salesContractProduct.setIsReady(1);

                    }
                }

                salesContractProducts.add(salesContractProduct);
                salesContractProducts1.add(salesContractProduct);
            }
        }
        //处理关联合同产品
        if (mapList.size() > 0) {
            handlerRelateContractProducts(mapList, salesContractProducts1);
        }
        salesProductDao.saveOrUpdateAll(salesContractProducts);
        return salesContractProducts;
    }

    /**
     * @param id
     * @return
     */
    public List<Map<String, Object>> findproduct(Long id) {
        List<Map<String, Object>> product = getDao().findproduct(id);
        return product;
    }

    /**
     * @param id
     * @return
     */
    public List<Map<String, Object>> getOrderInfos(Long id) {
        String sql = "SELECT b.id,b.PayAmount,b.`ReimAmount`,b.`OrderAmount`,b.`OrderCode`,b.`OrderName`,b.`OrderStatus`,b.`Creator`,b.`ArrivalStatus` FROM `sales_contract_product` cp ";
        sql += "LEFT JOIN `business_order_product` bp ON bp.`salesContractProductId`=cp.`id` ";
        sql += "LEFT JOIN `business_order` b ON  b.`id`=bp.`OrderId` ";
        sql += "WHERE cp.`SaleContractId`='" + id + "' AND b.`id`=bp.`OrderId` ";
        sql += "AND b.`OrderStatus`!='CG' ";
        sql += "UNION ";
        sql += "SELECT b.id,b.PayAmount,b.`ReimAmount`,b.`OrderAmount`,b.`OrderCode`,b.`OrderName`,b.`OrderStatus`,b.`Creator`,b.`ArrivalStatus` FROM (SELECT * FROM sales_contract_product p WHERE p.`SaleContractId`='" + id + "' ";
        sql += "AND p.`RelateDeliveryProductId` IS NOT NULL)cp ";
        sql += "LEFT JOIN `business_order_product` bp ON bp.`salesContractProductId`=cp.`RelateDeliveryProductId` ";
        sql += "LEFT JOIN `business_order` b ON b.`id`=bp.`OrderId` ";
        sql += "WHERE b.`OrderStatus`!='CG' ";
        //String sql = "SELECT b.* FROM `sales_contract_product` cp, `business_order_product` bp, `business_order` b WHERE cp.`SaleContractId`='" + id + "' AND bp.`salesContractProductId`=cp.`id` AND b.`id`=bp.`OrderId` UNION SELECT b.* FROM (SELECT * FROM sales_contract_product p WHERE p.`SaleContractId`='" + id + "' AND p.`RelateDeliveryProductId` IS NOT NULL)cp, `business_order_product` bp , `business_order` b WHERE bp.`salesContractProductId`=cp.`RelateDeliveryProductId` AND b.`id`=bp.`OrderId` ";
        Query query = getDao().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List<Map<String, Object>> obList = query.list();
        return obList;
    }

    /**
     * @param id
     * @param parseLong
     * @return
     */
    public List<Map<String, Object>> getOrderProductDifferents(Long id, long orderId) {
        String sql = "SELECT cp.`ProductTypeName` typeName,cp.ProductPartnerName partnerName, cp.`ProductName` pName,  bp.`Quantity` orderQua, ";
        sql += "bp.unitprice unitprice,bp.`SubTotal` subtotal,  bp.`Quantity` ordernum ,bp.`OrderId` ,s.`ContractCode`,s.id,bp.`salesContractProductId` ";
        sql += "FROM `sales_contract_product` cp ";
        sql += "LEFT JOIN `business_order_product` bp ON bp.`salesContractProductId`=cp.`id` ";
        sql += "LEFT JOIN sales_contract s ON s.`id`=bp.`SaleContractId` ";
        sql += "WHERE cp.`SaleContractId`='" + id + "' AND bp.`OrderId`='" + orderId + "' ";
        sql += "UNION ALL ";
        sql += "SELECT cp.`ProductTypeName` typeName,cp.ProductPartnerName partnerName, cp.`ProductName` pName,  bp.`Quantity` orderQua, ";
        sql += "bp.unitprice unitprice,bp.`SubTotal` subtotal,  bp.`Quantity` ordernum ,bp.`OrderId` ,s.`ContractCode`,s.id,bp.`salesContractProductId` ";
        sql += "FROM (SELECT * FROM sales_contract_product p WHERE p.`SaleContractId`='" + id + "' AND p.`RelateDeliveryProductId` IS NOT NULL)cp ";
        sql += "LEFT JOIN `business_order_product` bp ON bp.`salesContractProductId`=cp.`RelateDeliveryProductId` ";
        sql += "LEFT JOIN sales_contract s ON s.`id`=bp.`SaleContractId` ";
        sql += " WHERE bp.`OrderId`='" + orderId + "' ";

        //String sql = "SELECT cp.`ProductTypeName` typeName,cp.ProductPartnerName partnerName, cp.`ProductName` pName,  bp.`Quantity` orderQua,bp.unitprice unitprice,bp.`SubTotal` subtotal,  bp.`Quantity` ordernum ,bp.`OrderId` FROM `sales_contract_product` cp, `business_order_product` bp WHERE cp.`SaleContractId`='" + id + "' AND bp.`salesContractProductId`=cp.`id` AND bp.`OrderId`='" + orderId + "'";
        //sql += "UNION ALL SELECT cp.`ProductTypeName` typeName,cp.ProductPartnerName partnerName, cp.`ProductName` pName,  bp.`Quantity` orderQua,bp.unitprice unitprice,bp.`SubTotal` subtotal,  bp.`Quantity` ordernum ,bp.`OrderId` FROM (SELECT * FROM sales_contract_product p WHERE p.`SaleContractId`='" + id + "' AND p.`RelateDeliveryProductId` IS NOT NULL)cp, `business_order_product` bp WHERE bp.`salesContractProductId`=cp.`RelateDeliveryProductId` AND bp.`OrderId`='" + orderId + "'";
        Query query = getDao().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List<Map<String, Object>> obList = query.list();
        return obList;
    }

    /**
     * @param id
     * @return
     */
    public List<Map<String, Object>> findProSalesCode(Long id) {
        String sql = "SELECT s.`id`,s.`contractCode` FROM sales_contract_product p, sales_contract s ";
        sql += "WHERE p.`RelateDeliveryProductId` IN (SELECT t.id FROM sales_contract_product t WHERE t.`SaleContractId`='" + id + "' ) ";
        sql += "AND p.`SaleContractId`=s.`id` GROUP BY s.`id` ";
        List<Map<String, Object>> obList = getDao().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        return obList;
    }

    /**
     * @param contractIds
     * @return
     */
    public List<SalesContractModel> getSalesIds(String contractIds) {
        String[] salesId = contractIds.split(",");
        List<SalesContractModel> sales = getDao().getSalesIds(salesId);
        return sales;
    }

    /**
     * 根据id查找合同
     * @param saleContractId
     * @return
     */
    public SalesContractModel getSaleContractById(String saleContractId) {
        return getDao().getSaleContractById(saleContractId);
    }

    /**
     * @param procInstId
     * @return
     */
    public SalesContractModel findContractModel(long procInstId) {
        SalesContractModel sales = getDao().findContractModel(procInstId);
        return sales;
    }

    /**
     * @param id
     * @return
     */
    public List<SalesInvoicePlanModel> getSalesInvoicePlan(Long id) {
        List<SalesInvoicePlanModel> invoice = getDao().getSalesInvoicePlan(id);
        return invoice;
    }

    /**
     * 查询公司的信息，默认查找第一条记录信息
     * @return
     */
    public List<Map<String, Object>> findCompanyInfo() {
        return payOrderDao.findCompanyInfoById(1);
    }

}
