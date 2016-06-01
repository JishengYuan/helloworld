package com.sinobridge.eoss.business.order.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
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
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinobridge.base.core.hibernate.id.IdentifierGeneratorImpl;
import com.sinobridge.base.core.service.DefaultBaseService;
import com.sinobridge.base.core.utils.JacksonJsonMapper;
import com.sinobridge.base.core.utils.PaginationSupport;
import com.sinobridge.eoss.bpm.service.process.ProcessInstService;
import com.sinobridge.eoss.bpm.service.process.ProcessService;
import com.sinobridge.eoss.business.order.constant.BusinessOrderContant;
import com.sinobridge.eoss.business.order.dao.BusinessOrderDao;
import com.sinobridge.eoss.business.order.dao.BusinessPayOrderDao;
import com.sinobridge.eoss.business.order.model.BusinessOrderModel;
import com.sinobridge.eoss.business.order.model.BusinessPayOrderModel;
import com.sinobridge.eoss.business.order.model.BusinessPaymentPlanModel;
import com.sinobridge.eoss.business.order.utils.SendEmailUtils;
import com.sinobridge.eoss.sales.contract.dao.SalesContractDao;
import com.sinobridge.eoss.sales.contract.model.SalesContractModel;
import com.sinobridge.eoss.sales.contract.service.SalesContractService;
import com.sinobridge.eoss.sales.stockupCost.model.StockUpContractCostModel;
import com.sinobridge.eoss.sales.stockupCost.service.StockUpContractCostService;
import com.sinobridge.eoss.salesfundsCost.model.FundsSalesBusiCost;
import com.sinobridge.eoss.salesfundsCost.model.FundsSalesContract;
import com.sinobridge.eoss.salesfundsCost.model.FundsSalesLog;
import com.sinobridge.eoss.salesfundsCost.service.FundsSalesBusiCostService;
import com.sinobridge.eoss.salesfundsCost.service.FundsSalesContractService;
import com.sinobridge.eoss.salesfundsCost.service.FundsSalesLogService;
import com.sinobridge.systemmanage.model.SysStaff;
import com.sinobridge.systemmanage.util.Constants;
import com.sinobridge.systemmanage.vo.SystemUser;

import net.sf.ehcache.Ehcache;

@Service
@Transactional
public class BusinessPayOrderService extends DefaultBaseService<BusinessPayOrderModel, BusinessPayOrderDao> {

    private final static Ehcache systemUserCache = Constants.CACHE_MANAGER.getEhcache("systemUser");
    @Autowired
    private BusinessPaymentPlanService businessPaymentPlanService;
    @Autowired
    private ProcessService processService;
    @Autowired
    private BusinessOrderDao businessOrderDao;
    @Autowired
    private ProcessInstService processInstService;
    @Autowired
    private BusinessOrderService businessOrderService;
    @Autowired
    private SalesContractDao salesContractDao;
    @Autowired
    private FundsSalesContractService fundsSalesContractService;
    @Autowired
    private FundsSalesLogService fundsSalesLogService;
    @Autowired
    private FundsSalesBusiCostService fundsSalesBusiCostService;
    @Autowired
    private StockUpContractCostService stockUpContractCostService;
    @Autowired
    private SalesContractService salesContractService;

    /**
     * <code>doAddPay</code>
     * 执行保存付款计划操作
    * @param payOrderModel
    * @param systemUser
     * @param orderIds 订单IDs
     * @param tableData 订单表格
     * @since   2014年12月15日    wangya
     */
    public String doAddPay(BusinessPayOrderModel payOrderModel, SystemUser systemUser, String invoiceType, String tableData) {
        try {
            long payModelId = IdentifierGeneratorImpl.generatorLong();
            payOrderModel.setId(payModelId);
            Date time = new Date();
            payOrderModel.setCreateTime(time);
            payOrderModel.setPayApplyUser(systemUser.getUserId());
            payOrderModel.setPlanStatus(BusinessOrderContant.INTERPURCHAS_SH);
            //创建工单
            creatProcInst(payOrderModel, payModelId, systemUser);
            getDao().save(payOrderModel);
            List<Object> gridDataList = getTableGridData(tableData);
            businessPaymentPlanService.savePayPlan(payOrderModel, gridDataList, invoiceType);

        } catch (Exception e) {
            e.printStackTrace();
            return "false";
        }
        return "true";
    }

    /**
     * <code>creatProcInst</code>
     * 创建工单
     * @param map
     * @since   2014年12月15日    wangya
     */
    private void creatProcInst(BusinessPayOrderModel payOrderModel, long payModelId, SystemUser systemUser) {
        Long valId = processService.nextValId();
        payOrderModel.setProcessInstanceId(valId);
        Map<String, Object> variableMap = new HashMap<String, Object>();
        variableMap.put("payModelId", payModelId);
        variableMap.put(BusinessOrderContant.PAYMENT_AMOUNT, payOrderModel.getPayAmonut());
        String currency = "￥";
        if (payOrderModel.getCurrency().equals("usd")) {
            currency = "$";
        }
        //创建工单
        Long[] procInstId = processService.create(valId, payOrderModel.getPayApplyName() + "[" + currency + payOrderModel.getPayAmonut() + "]", systemUser.getUserName(), BusinessOrderContant.PAYMENT_KEY, 1, variableMap, null, null, null);
        System.out.println("创建工单成功：" + procInstId[0]);
    }

    @SuppressWarnings("unchecked")
    private List<Object> getTableGridData(String tableData) {
        ObjectMapper objectMapper = JacksonJsonMapper.getInstance();
        Map<String, Object> tableDataMap = null;
        List<Object> gridDataList = new ArrayList<Object>();
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
                gridDataList = (List<Object>) tableDataMap.get(key);
            }
        }
        return gridDataList;
    }

    /**
     * <code>findOrderByCriteria</code>
     * 条件查询符合的List
     * @param detachedCriteria 查询条件
     * @since 2014年6月25日  wangya
     */
    public List<BusinessPayOrderModel> findOrderByCriteria(DetachedCriteria detachedCriteria) {
        List<BusinessPayOrderModel> payOrder = new ArrayList<BusinessPayOrderModel>();
        payOrder = getDao().findByCriteria(detachedCriteria);
        return payOrder;
    }

    /**订单的计划付款金额
     * @param id
     * @return
     */
    public BigDecimal findPlanAmount(Long id) {
        BigDecimal amount = getDao().findPlanAmount(id);
        return amount;
    }

    /**
    * <code>handleFlow</code>
    * 处理工单
    * @param flowFlag 流程节点标示
    * @param payOrder
    * @param systemUser 操作人实体
    * @param taskId 任务ID
    * @param isAgree 是否同意
    * @param remark 审批意见
    * @param banlance
    * @since 2014年12月16日 wangya
    */
    public void handleFlow(String flowStep, SystemUser systemUser, BusinessPayOrderModel payOrder, String taskId, int isAgree, String remark, String banlance) {
        String userId = systemUser.getUserName();
        Map<String, Object> variableMap = new HashMap<String, Object>();
        if (flowStep.equals("SWJLSP") && isAgree == 1) {
            List<Map<String, Object>> codes = getDao().findOrderCode(payOrder.getId());
            String orderCode = "";
            for (int i = 0; i < codes.size(); i++) {
                orderCode += codes.get(i).get("orderCode").toString() + ",";
            }

            if (SendEmailUtils.isSend())
                processService.sendEmail("系统提醒:付款申请通过", "付款申请：" + payOrder.getPayApplyName() + "[" + payOrder.getPayAmonut() + "]通过商务经理审批。相关订单编号：" + orderCode, null, null, "cuixn", null, null);
        }
        if (flowStep.equals(BusinessOrderContant.CWFH) && isAgree == 1) {
            Long id = payOrder.getId();
            BusinessPayOrderModel pay = this.get(id);
            String subjectName = pay.getCoursesType();
            pay.setPayCompany(payOrder.getPayCompany());
            pay.setPayCompanyBank(payOrder.getPayCompanyBank());
            pay.setPayMethod(payOrder.getPayMethod());
            pay.setPayUser(payOrder.getPayUser());
            pay.setRealPayDate(payOrder.getRealPayDate());
            pay.setRealPayAmount(payOrder.getRealPayAmount());
            Date time = new Date();
            pay.setCloseTime(time);
            Set<BusinessPaymentPlanModel> plan = pay.getBusinessPaymentPlanModel();
            Map<Long, BigDecimal> realAmount = new HashMap<Long, BigDecimal>();//存储每个订单实付的人民币
            if (pay.getCurrency() != null && pay.getCurrency().equals("usd")) { //判断如果是付款申请是美金，则将实付的人民币按比例拆分到每个订单中
                BigDecimal totalAmount = new BigDecimal(0.00);
                for (BusinessPaymentPlanModel businessPaymentPlanModel : plan) {
                    totalAmount = totalAmount.add(businessPaymentPlanModel.getAmount());//计算美金总数
                }
                for (BusinessPaymentPlanModel businessPaymentPlanModel : plan) {
                    BigDecimal tempAmount = businessPaymentPlanModel.getAmount().divide(totalAmount, 10, BigDecimal.ROUND_HALF_UP);
                    tempAmount = tempAmount.multiply(payOrder.getRealPayAmount());
                    tempAmount = tempAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
                    realAmount.put(businessPaymentPlanModel.getId(), tempAmount);
                    businessPaymentPlanModel.setRealPayAmount(tempAmount);
                }

            }

            Iterator<BusinessPaymentPlanModel> p = plan.iterator();
            while (p.hasNext()) {
                BusinessPaymentPlanModel planModel = p.next();
                BusinessOrderModel order = planModel.getBusinessOrder();
                BigDecimal amount = new BigDecimal(0.00);
                if (pay.getCurrency() != null && pay.getCurrency().equals("usd")) {
                    amount = amount.add(realAmount.get(planModel.getId()));
                } else {
                    amount = amount.add(planModel.getAmount());//本次付款金额
                }

                amount = amount.add(order.getPayAmount());//订单已经付款金额
                amount = amount.add(new BigDecimal(order.getSpotNum()));
                String planStatus = BusinessOrderContant.ORDER_PAYMENT_S;
                if (amount.compareTo(order.getOrderAmount()) == 0) {
                    planStatus = BusinessOrderContant.ORDER_PAYMENT_A;
                }
                getDao().updateOrderPayAmount(order.getId(), amount, planStatus);//更新订单的付款金额

                /********************************核算合同商务成本*************************************************/

                String sql1 = "SELECT * FROM order_contract_map m LEFT JOIN sales_contract n ON m.sales_contract=n.id WHERE m.business_order=" + order.getId();
                Query query = getDao().createSQLQuery(sql1);
                List<Map<String, Object>> rsmap = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
                if (rsmap.size() == 1) { //关联一个合同
                    Map<String, Object> ms = rsmap.get(0);
                    FundsSalesContract fundsSalesContract = fundsSalesContractService.getBycontractCode((String) ms.get("ContractCode"));
                    if (fundsSalesContract == null) { //只要付款，就记录该合同的信息，备货也记录
                        fundsSalesContract = new FundsSalesContract();
                        fundsSalesContract.setContractCode((String) ms.get("ContractCode"));
                        fundsSalesContract.setContractAmount((BigDecimal) ms.get("ContractAmount"));
                        fundsSalesContract.setContractName((String) ms.get("ContractName"));
                        fundsSalesContract.setCreatorName((String) ms.get("CreatorName"));
                        fundsSalesContract.setSalesStartDate((Date) ms.get("SalesStartDate"));
                        log.error("合同：" + ms.get("ContractCode") + ",原来没有进行商务成本计算！");
                    }
                    BigDecimal amount1 = new BigDecimal(0.00);
                    BigDecimal hiAmount = fundsSalesContract.getBusinessCost();
                    if (fundsSalesContract.getBusinessCost() != null) {
                        amount1 = amount1.add(fundsSalesContract.getBusinessCost());
                    }
                    if (pay.getCurrency() != null && pay.getCurrency().equals("usd")) {
                        amount1 = amount1.add(realAmount.get(planModel.getId()));
                    } else {
                        amount1 = amount1.add(planModel.getAmount());//本次付款金额
                    }
                    fundsSalesContract.setBusinessCost(amount1);
                    fundsSalesContractService.saveOrUpdate(fundsSalesContract);
                    FundsSalesLog fundsSalesLog = new FundsSalesLog();
                    fundsSalesLog.setOpAmount(amount1);
                    fundsSalesLog.setOpDate(new Date());
                    fundsSalesLog.setContractCode((String) ms.get("ContractCode"));
                    fundsSalesLog.setOpDesc("产生商务成本");
                    /*历史金额，没有的话使用第一次作为历史金额*/
                    if (hiAmount != null) {
                        fundsSalesLog.setHiAmount(hiAmount);
                    } else {
                        fundsSalesLog.setHiAmount(amount);
                    }
                    fundsSalesLogService.saveOrUpdate(fundsSalesLog);
                    planModel.setPlanStatus("1");//系统自动确认商务成本到合同中
                }
                if (rsmap.size() > 1) { //关联多个合同，为每个商务产生待分办任务
                    for (Map<String, Object> map : rsmap) {
                        FundsSalesContract fundsSalesContract = fundsSalesContractService.getBycontractCode((String) map.get("ContractCode"));
                        if (fundsSalesContract == null) { //判断付款的合同，是否在核算表中，如果不在，则重新插入
                            fundsSalesContract = new FundsSalesContract();
                            fundsSalesContract.setContractCode((String) map.get("ContractCode"));
                            fundsSalesContract.setContractAmount((BigDecimal) map.get("ContractAmount"));
                            fundsSalesContract.setContractName((String) map.get("ContractName"));
                            fundsSalesContract.setCreatorName((String) map.get("CreatorName"));
                            fundsSalesContract.setSalesStartDate((Date) map.get("SalesStartDate"));
                            log.error("合同：" + map.get("ContractCode") + ",原来没有进行商务成本计算！");
                            fundsSalesContractService.saveOrUpdate(fundsSalesContract);
                        }
                        //产生需要商务手工确认的商务成本
                        FundsSalesBusiCost fundsSalesBusiCost = new FundsSalesBusiCost();
                        fundsSalesBusiCost.setDoState("0"); //商务未分配
                        fundsSalesBusiCost.setPayPlanId(planModel.getId());
                        fundsSalesBusiCost.setSalesContractId(((BigInteger) map.get("sales_contract")).longValue());
                        fundsSalesBusiCost.setDoUser(planModel.getCreator());//分配给商务
                        fundsSalesBusiCostService.saveOrUpdate(fundsSalesBusiCost);
                    }
                }
                /********************************核算合同商务成本end*************************************************/

            }
            pay.setBusinessPaymentPlanModel(plan);
            pay.setPlanStatus(BusinessOrderContant.PAYMENT_OK);//付款申请：审批通过
            getDao().saveOrUpdate(pay);

            BigDecimal bankAmount = new BigDecimal(banlance);
            bankAmount = bankAmount.subtract(pay.getPayAmonut());
            String remarks = "付款操作：" + payOrder.getPayApplyName() + "：" + payOrder.getPayAmonut();
            String creator = systemUser.getStaffName();
            String applyUser = payOrder.getPayUser();
            String bankAccountId = payOrder.getPayCompanyBank();
            long logId = IdentifierGeneratorImpl.generatorLong();
            getDao().insertBankAccount(bankAmount.doubleValue(), time, payOrder.getPayAmonut().doubleValue(), remarks, creator, applyUser, subjectName, bankAccountId, logId);//填log日志
            getDao().updateBankAccountItem(bankAmount, bankAccountId);//改银行余额
        }
        processService.handle(taskId, userId, isAgree, remark, variableMap, null, null);
    }

    /**
     * 若订单付款包含备货，则在关联备货成本中插入一条产品合同信息
     * @param payOrder
     * @param isAgree
     * @param flowStep
     */
    @SuppressWarnings("unchecked")
    public void doSalesContractbh(BusinessPayOrderModel payOrder) {
        Long id = payOrder.getId();
        BusinessPayOrderModel pay = this.get(id);
        Set<BusinessPaymentPlanModel> plan = pay.getBusinessPaymentPlanModel();

        Iterator<BusinessPaymentPlanModel> p = plan.iterator();
        while (p.hasNext()) {
            BusinessPaymentPlanModel planModel = p.next();
            BusinessOrderModel order = planModel.getBusinessOrder();
            String sql1 = "SELECT * FROM order_contract_map m LEFT JOIN sales_contract n ON m.sales_contract=n.id WHERE m.business_order=" + order.getId();
            Query query = getDao().createSQLQuery(sql1);
            List<Map<String, Object>> rsmap = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
            if (rsmap.size() > 0) {//关联的所有合同
                List<StockUpContractCostModel> costList = new ArrayList<StockUpContractCostModel>();
                for (Map<String, Object> map : rsmap) {//所有合同遍历
                    if (map.get("ContractType").toString().equals("9000")) {//是备货合同
                        String conId = map.get("id").toString();
                        //判断备货是否关联过
                        StringBuffer cp = new StringBuffer();
                        cp.append("SELECT s.`ContractCode`,s.ContractName , s.ContractAmount,s.CreatorName,s.id FROM ");
                        cp.append("(SELECT op.`salesContractProductId` FROM business_order_product op WHERE op.`OrderId`='" + order.getId() + "') op ");
                        cp.append("LEFT JOIN (SELECT p.`id` FROM sales_contract_product p WHERE p.`SaleContractId`='" + conId + "') p  ON p.id=op.salesContractProductId ");
                        cp.append("LEFT JOIN sales_contract_product cp ON cp.`RelateDeliveryProductId`=p.id  ");
                        cp.append("LEFT JOIN sales_contract s ON s.`id`=cp.`SaleContractId` WHERE s.`id` IS NOT NULL  AND s.`ContractState`='TGSH' GROUP BY cp.`SaleContractId` ");
                        List<Map<String, Object>> listcp = getDao().createSQLQuery(cp.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
                        if (listcp.size() > 0) {//备货已经关联了的

                            //判断备货是否在funds_salescontract表中存过
                            StringBuffer sbt = new StringBuffer();
                            String contractCode = map.get("contractCode").toString();
                            sbt.append("SELECT * FROM `funds_salescontract` s WHERE s.`contractCode`='" + contractCode + "' ");
                            List<Map<String, Object>> funds = getDao().createSQLQuery(sbt.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
                            if (funds.size() == 0) {//没有则插入值
                                FundsSalesContract fundsSales = new FundsSalesContract();
                                SalesContractModel sales = salesContractService.get(Long.parseLong(conId));
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
                                    processService.sendEmail("系统提醒:关联备货成本", "合同:" + sales.getCreatorName() + "[" + sales.getContractCode() + "]为备货合同，成本为：" + amount + "，其成本为人工添加，需进行核对。", null, null, "tianj", null, null);
                            }

                            for (Map<String, Object> maps : listcp) {//插入所有产品合同
                                StringBuffer sbtt = new StringBuffer();
                                sbtt.append("SELECT * FROM stockup_contractcost s WHERE s.`ContractCode`='" + maps.get("ContractCode").toString() + "' and  s.doState='0' ");
                                List<Map<String, Object>> stock = getDao().createSQLQuery(sbtt.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
                                if (stock.size() == 0) {
                                    StockUpContractCostModel contractCostModel = new StockUpContractCostModel();
                                    long ids = IdentifierGeneratorImpl.generatorLong();
                                    contractCostModel.setId(ids);
                                    contractCostModel.setContractName(maps.get("ContractName").toString());
                                    contractCostModel.setContractCode(maps.get("ContractCode").toString());
                                    contractCostModel.setContractAmount(new BigDecimal(maps.get("ContractAmount").toString()));
                                    contractCostModel.setCreatorName(maps.get("CreatorName").toString());
                                    contractCostModel.setDoState("0"); //确认状态，0:未确认,1:已确认
                                    contractCostModel.setOrderProcessor(order.getCreatorId());
                                    contractCostModel.setSalesContractId(maps.get("id").toString());
                                    costList.add(contractCostModel);
                                }
                            }
                            /* String sql2 = "SELECT SUM(sp.`Quantity`) spQua,SUM(bp.`Quantity`) bpQua FROM sales_contract_product sp,business_order_product bp WHERE bp.`SaleContractId`=sp.`SaleContractId` AND sp.`SaleContractId`='" + conId + "' GROUP BY sp.`SaleContractId`";
                             Query query2 = getDao().createSQLQuery(sql2);
                             List<Map<String, Object>> productQue = query2.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
                             if (productQue.size() > 0 && productQue.get(0).get("spQua").toString().equals(productQue.get(0).get("bpQua").toString())) {//所有产品都下订单了
                                 StringBuffer sb = new StringBuffer();
                                 sb.append("SELECT o.`id`,a.payAmount,o.`OrderAmount`,a.realPay FROM (SELECT bp.`OrderId` FROM business_order_product bp ");
                                 sb.append("WHERE bp.`SaleContractId`='" + conId + "' GROUP BY bp.`OrderId`) b ");
                                 sb.append("LEFT JOIN (SELECT SUM(p.`Amount`) payAmount, p.`OrderId`,SUM(p.`RealPayAmount`) realPay FROM `business_payment_plan` p ");
                                 sb.append("LEFT JOIN `business_pay_apply` a ON p.`PayOrderId`=a.`id` WHERE a.`PlanStatus`='10' GROUP BY p.`OrderId` ");
                                 sb.append(")a ON a.OrderId=b.OrderId LEFT JOIN business_order o ON o.`id`=b.OrderId  ");
                                 List<Map<String, Object>> pays = getDao().createSQLQuery(sb.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
                                 int flat = 1;
                                 if (pays.size() > 0) {//判断
                                     BigDecimal amount = new BigDecimal(0.00);
                                     BigDecimal realPay = new BigDecimal(0.00);
                                     if (pays.get(0).get("payAmount") != null) {
                                         amount = new BigDecimal(pays.get(0).get("payAmount").toString());//人民币付款的查看这个值
                                     }
                                     if (pays.get(0).get("realPay") != null) {
                                         realPay = new BigDecimal(pays.get(0).get("realPay").toString());//美金付款的查看这个值
                                     }
                                     BigDecimal orderAmount = new BigDecimal(pays.get(0).get("OrderAmount").toString());
                                     if (orderAmount.compareTo(amount) == 0 || orderAmount.compareTo(realPay) == 0) {
                                         flat = 0;
                                     }
                                 }
                                 if (flat == 0) {//全部付款了
                                     for (Map<String, Object> maps : listcp) {//插入所有产品合同
                                         StringBuffer sbt = new StringBuffer();
                                         sbt.append("SELECT * FROM stockup_contractcost s WHERE s.`ContractCode`='" + maps.get("ContractCode").toString() + "' and  s.doState='0' ");
                                         List<Map<String, Object>> stock = getDao().createSQLQuery(sbt.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
                                         if (stock.size() == 0) {
                                             StockUpContractCostModel contractCostModel = new StockUpContractCostModel();
                                             long ids = IdentifierGeneratorImpl.generatorLong();
                                             contractCostModel.setId(ids);
                                             contractCostModel.setContractName(maps.get("ContractName").toString());
                                             contractCostModel.setContractCode(maps.get("ContractCode").toString());
                                             contractCostModel.setContractAmount(new BigDecimal(maps.get("ContractAmount").toString()));
                                             contractCostModel.setCreatorName(maps.get("CreatorName").toString());
                                             contractCostModel.setDoState("0"); //确认状态，0:未确认,1:已确认
                                             contractCostModel.setOrderProcessor(order.getCreatorId());
                                             contractCostModel.setSalesContractId(maps.get("id").toString());
                                             costList.add(contractCostModel);
                                         }
                                     }
                                 }
                             }*/
                        }
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

    }

    /**重新提交付款
     * @param payOrder
     * @param tableData
     * @param taskId
     * @param systemUser
     * @param invoiceType
     * @return
     */
    public String doUpdateOrder(BusinessPayOrderModel payOrder, String tableData, String taskId, SystemUser systemUser, String invoiceType) {
        try {
            Long id = payOrder.getId();
            Date d = new Date();
            payOrder.setCreateTime(d);
            if (id != null) {//删除旧的付款计划
                businessPaymentPlanService.deletePlan(id);
            }
            payOrder.setPlanStatus(BusinessOrderContant.INTERPURCHAS_SH);
            Map<String, Object> map = new HashMap<String, Object>();
            //从新提交订单时提供的key值
            map.put(BusinessOrderContant.IS_RE_SUBMIT, true);
            map.put(BusinessOrderContant.PAYMENT_AMOUNT, payOrder.getPayAmonut());
            processService.handle(taskId, systemUser.getUserName(), map, null, null);
            getDao().saveOrUpdate(payOrder);
            List<Object> gridDataList = getTableGridData(tableData);
            if (gridDataList != null) {//付款计划
                businessPaymentPlanService.savePayPlan(payOrder, gridDataList, invoiceType);
            }
            String[] t = payOrder.getPayApplyName().split("\\[");
            String mark = "￥";
            if (payOrder.getCurrency().equals("usd")) {
                mark = "$";
            }

            String title = t[0] + "[" + mark + payOrder.getPayAmonut().setScale(2, BigDecimal.ROUND_HALF_UP) + "]";
            getDao().updateTitleAmount(payOrder.getProcessInstanceId(), title);//更新工单标题
            //更新订单付款状态

        } catch (Exception e) {
            e.printStackTrace();
            return "false";
        }
        return "ture";
    }

    /**
     * <code>payTaskList</code>
     * 得到财务待处理的任务
     * @param request
     * @return
     * @since   2014年12月16日    wangya
     */
    @SuppressWarnings("unchecked")
    public PaginationSupport payTaskList(Map<String, Object> search, Integer startNum, Integer endNum) {

        Object sortname = search.get("sortname");
        Object sortorder = search.get("sortorder");
        Object payApplyName = search.get(BusinessPayOrderModel.PAYAPPLYNAME);
        Object amount = search.get(BusinessPayOrderModel.AMOUNT);
        Object planPayDate = search.get(BusinessPayOrderModel.PLANPAYDATE);
        Object systemUser = search.get("systemUser");
        if (sortname.equals("createTime")) {
            sortname = "create_time_";
        }
        StringBuilder sb = new StringBuilder();

        sb.append("SELECT * FROM (( SELECT * FROM ( SELECT ");
        sb.append("p.id_ procInstId,");
        sb.append("b.payApplyName,");
        sb.append("b.PayAmonut,");
        sb.append("p.key_ procDefKey,");
        sb.append("d.process_name_ processName,");
        sb.append("t.name_ taskName,");
        sb.append("i.prev_handler_,");
        sb.append("date_format(p.create_time_,'%Y-%m-%d %H:%i') createTime,");
        sb.append("date_format(p.last_mod_time_,'%Y-%m-%d %H:%i') modiTime,");
        sb.append("date_format(t.create_time_,'%Y-%m-%d %H:%i') taskCreateTime,");
        sb.append("p.status_ status_,");
        sb.append("t.id_ taskId,");
        sb.append("p.time_over_ timeOver,");
        sb.append("p.priority_ priority,");
        sb.append("t.due_date_ dueDate,");
        sb.append("d.process_group_ groupId,");
        sb.append("d.id_ defId,");
        sb.append("p.creator_ creator,");
        sb.append("d.re_proc_def_key_ reProcDefKey,");
        sb.append("u.last_,");
        sb.append("b.currency");
        sb.append(" FROM bpm_process_inst p,act_ru_task t,act_ru_identitylink l,act_id_membership m,act_id_group g,bpm_process_def d,act_id_user u, business_pay_apply b,bpm_task_inst i ");
        sb.append(" WHERE m.user_id_ = u.id_ AND m.group_id_ = g.id_ AND g.id_ = l.group_id_ AND l.task_id_ = t.id_ AND t.proc_inst_id_ = p.hi_proc_inst_id AND p.proc_def_ = d.id_ AND u.id_ = ? AND t.assignee_ IS NULL AND p.status_ = 1 AND b.processInstanceId = p.id_ AND i.id_ = t.id_ ");

        if (payApplyName != null) {
            sb.append(" AND b.payApplyName like '%" + payApplyName + "%' ");
        }

        if (amount != null) {
            String payAmount = amount.toString();
            if (payAmount.indexOf(".") != -1) {
                sb.append(" AND b.PayAmonut='" + payAmount + "' ");
            } else {
                sb.append(" AND b.PayAmonut like '" + payAmount + ".%' ");
            }
        }
        if (planPayDate != null) {
            sb.append(" AND b.planPayDate = " + planPayDate + " ");
        }
        sb.append("order by p." + sortname + " " + sortorder);
        sb.append(" ) as t1");

        sb.append(" ) UNION ( SELECT * FROM (SELECT");
        sb.append(" p.id_ procInstId,");
        sb.append("b.payApplyName,");
        sb.append("b.PayAmonut,");
        sb.append("p.key_ procDefKey,");
        sb.append("d.process_name_ processName,");
        sb.append("t.name_ taskName,");
        sb.append("i.prev_handler_,");
        sb.append("date_format(p.create_time_,'%yY-%m-%d %H:%i') createTime,");
        sb.append("date_format(p.last_mod_time_,'%Y-%m-%d %H:%i') modiTime,");
        sb.append("date_format(t.create_time_,'%Y-%m-%d %H:%i') taskCreateTime,");
        sb.append("p.status_ status_,");
        sb.append("t.id_ taskId,");
        sb.append("p.time_over_ timeOver,");
        sb.append("p.priority_ priority,");
        sb.append("t.due_date_ dueDate,");
        sb.append("d.process_group_ groupId,");
        sb.append("d.id_ defId,");
        sb.append("p.creator_ creator,");
        sb.append("d.re_proc_def_key_ reProcDefKey,");
        sb.append("u.last_,");
        sb.append("b.currency");
        sb.append(" FROM bpm_process_inst p,act_ru_task t,bpm_process_def d,act_id_user u,business_pay_apply b,bpm_task_inst i ");
        sb.append(" WHERE t.proc_inst_id_ = p.hi_proc_inst_id AND p.PROC_DEF_ = d.ID_ AND t.assignee_ = ? AND p.status_ = 1 AND u.ID_ = i.prev_handler_ AND b.processInstanceId = p.id_ AND i.id_ = t.id_ ");

        if (payApplyName != null) {
            sb.append(" AND b.payApplyName like '%" + payApplyName + "%' ");
        }
        if (amount != null) {
            BigDecimal payAmount = new BigDecimal(amount.toString());
            sb.append(" AND b.PayAmonut like '" + payAmount + ".%' ");
        }
        if (planPayDate != null) {
            sb.append(" AND b.planPayDate = " + planPayDate + " ");
        }

        sb.append("order by p." + sortname + " " + sortorder);
        sb.append(" ) as t2");

        sb.append(" ) ) AS info WHERE 1 = 1");

        String userName = systemUser.toString();
        String[] params = new String[2];
        params[0] = userName;
        params[1] = userName;

        Query query = null;
        query = getDao().createSQLQuery(sb.toString(), params);
        query.setFirstResult(startNum);
        query.setMaxResults(endNum);
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

        List<Map<String, Object>> mapList = query.list();
        for (Map<String, Object> map : mapList) {
            String staffName = ((SysStaff) systemUserCache.get(map.get("prev_handler_").toString()).getObjectValue()).getStaffName();
            map.put("prev_handler_", staffName);
            staffName = ((SysStaff) systemUserCache.get(map.get("creator").toString()).getObjectValue()).getStaffName();
            map.put("creator", staffName);
        }

        PaginationSupport paginationSupport = new PaginationSupport(mapList, payTaskCount(sb.toString(), params));
        return paginationSupport;
    }

    /**
     * <code>payTaskCount</code>
     * 得到财务待处理任务的总数量
     * @param request
     * @return
     * @since   2014年12月18日    wangya
     */
    public Integer payTaskCount(String sql, String[] params) {
        sql = "select count(*) from (" + sql + " ) as total";
        return Integer.valueOf(getDao().createSQLQuery(sql, params).list().get(0).toString()).intValue();
    }

    /**
     * <code>findCompany</code>
     * 查询公司
     * @param request
     * @return
     * @since   2014年12月18日    wangya
     */
    public List<Map<String, Object>> findCompany() {
        List<Map<String, Object>> listMap = getDao().findCompany();
        return listMap;
    }

    /**查询公司账户
     * @param parseLong
     * @return
     */
    public List<Map<String, Object>> getBankAccountByCmp(long parseLong) {
        List<Map<String, Object>> account = getDao().findBankAccount(parseLong);
        return account;
    }

    /**付款银行信息
     * @param id
     * @return
     */
    public List<Map<String, Object>> getfindBankName(Long id) {
        List<Map<String, Object>> account = getDao().getfindBankName(id);
        return account;
    }

    /**
     * <code>getAccountPersion</code>
     * 得到财务部员工
     * @return
     * @since   2014年7月28日    guokemenng
     */
    public List<Map<String, Object>> getAccountPersion() {
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        mapList = getDao().getAccountPersion();
        return mapList;
    }

    /**
     * 放弃提交，删除付款
     * @param taskId
     * @param payOrder
     * @param systemUser
     */
    public void endpay(String taskId, BusinessPayOrderModel payOrder, SystemUser systemUser) {
        Long id = payOrder.getId();
        //getDao().endPayStatus(id);
        /* Map<String, Object> map = new HashMap<String, Object>();
         //从新提交订单时提供的key值
         map.put(BusinessOrderContant.IS_RE_SUBMIT, false);*/
        try {
            // processService.handle(taskId, systemUser.getUserName(), map, null, null);
            List<Map<String, Object>> orderIds = getDao().findOrderId(id);
            for (int i = 0; i < orderIds.size(); i++) {
                Long oid = Long.parseLong(orderIds.get(i).get("id").toString());
                BusinessOrderModel order = businessOrderService.get(oid);
                if (order.getPayAmount().compareTo(BigDecimal.ZERO) == 0) {
                    businessOrderDao.updatePayStatus(oid);
                }
            }
            processInstService.removeProcessInst(payOrder.getProcessInstanceId().toString());
            this.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**查询付款
     * @param procInstId
     * @return
     */
    public BusinessPayOrderModel findPayModel(String procInstId) {
        BusinessPayOrderModel pay = getDao().findPayModel(procInstId);
        return pay;
    }

    /**总金额查询
     * @param searchMap
     * @return
     */
    public String findTotallAmount(Map<String, Object> searchMap) {
        String amount = getDao().findTotallAmount(searchMap);
        return amount;
    }

    /**
     * <code>getModelPage</code>
     * 根据条件查询 返回page对象
     * @param searchMap
     * @param startIndex
     * @param pageSize
     * @return
     * @since   2014年12月22日    guokemenng
     */
    @SuppressWarnings({ "unchecked", "unused" })
    public PaginationSupport getModelPage(Map<String, Object> searchMap, Integer startIndex, Integer pageSize) {
        int start = 0;
        Object[] values = new Object[searchMap.size()];
        Object startTime = searchMap.get("startTime");
        Object endTime = searchMap.get("endTime");
        Object supplierShortName = searchMap.get("supplierId");
        Object minAmount = searchMap.get("minAmount");
        // Object maxAmount = searchMap.get("maxAmount");
        Object payName = searchMap.get("payName");
        String hql = "SELECT c.id,c.PayAmonut,c.PayApplyName,c.PayApplyUser,c.PlanPayDate,c.PlanStatus,c.RealPayDate,c.Currency,c.CoursesType,art.NAME_,art.CREATE_TIME_, art.ASSIGNEE_ FROM business_pay_apply c LEFT JOIN bpm_process_inst bpi ON c.ProcessInstanceId=bpi.ID_  LEFT JOIN act_ru_task art ON bpi.HI_PROC_INST_ID= art.proc_inst_id_  WHERE c.PlanStatus!='1' ";
        if (startTime != null) {
            values[start++] = startTime + "";
            hql += " and c.RealPayDate>=?";
        }
        if (endTime != null) {
            values[start++] = endTime + "";
            hql += " and c.RealPayDate<=?";
        }
        if (supplierShortName != null) {
            values[start++] = supplierShortName + "";
            hql += " and c.SupplierName=?";
        }
        if (payName != null) {
            values[start++] = payName + "";
            hql += " and c.PayApplyName like ?";
        }
        if (minAmount != null) {
            values[start++] = minAmount + "";
            hql += " and c.PayAmonut=?";
        }
        /* if (maxAmount != null) {
             values[start++] = maxAmount + "";
             hql += " and c.PayAmonut<=?";
         }*/
        hql += " order by c.id desc";
        Query query = null;
        if (searchMap.size() != 0) {
            query = getDao().createSQLQuery(hql, values).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).setFirstResult(startIndex).setMaxResults(pageSize);
        } else {
            query = getDao().createSQLQuery(hql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).setFirstResult(startIndex).setMaxResults(pageSize);
        }
        List<Map<String, Object>> list = query.list();
        PaginationSupport ps = new PaginationSupport(query.list(), totalCount(hql, values));
        return ps;
    }

    /**
     * <code>totalCount</code>
     * 总条数
     * @param sql
     * @param params
     * @return
     * @since   2014年12月22日    guokemenng
     */
    public Integer totalCount(String sql, Object[] params) {
        if (params.length == 0) {
            sql = "select count(*) from (" + sql + " ) as total";
            return Integer.valueOf(getDao().createSQLQuery(sql).list().get(0).toString()).intValue();
        } else {
            sql = "select count(*) from (" + sql + " ) as total";
            return Integer.valueOf(getDao().createSQLQuery(sql, params).list().get(0).toString()).intValue();
        }
    }

    /**付款列表
     * @param searchMap
     * @param i
     * @param pageSize
     * @return
     */
    public PaginationSupport findPayPage(HashMap<String, Object> searchMap, int startIndex, Integer pageSize) {
        return getDao().findPayPage(searchMap, startIndex, pageSize);
    }

    /**
     * @param searchMap
     * @return
     */
    public BigDecimal totallA(HashMap<String, Object> searchMap) {
        return getDao().totallA(searchMap);
    }

    /**查询流程审批人
     * @param processInstanceId
     * @return
     */
    @SuppressWarnings("unchecked")
    public Object print(Long processInstanceId) {
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

    /**公司信息
     * @param parseLong
     * @return
     */
    public String findCompany(long parseLong) {
        String company = getDao().findCompany(parseLong);
        return company;
    }

    /**查询未付金额
     * @param orderId
     * @return
     */
    public BigDecimal getpaymentAmount(String orderId) {
        BigDecimal amount = getDao().getpaymentAmount(orderId);
        return amount;
    }

    /**查询付款申请信息
     * @param id
     * @return
     */
    public List<BusinessPayOrderModel> getPayOrderList(Long id) {
        List<BusinessPayOrderModel> payOrder = getDao().getPayOrderList(id);
        return payOrder;
    }

    /*  public static void main(String args[]) {
          List<StockUpContractCostModel> costList = new ArrayList<StockUpContractCostModel>();
          StockUpContractCostModel stockUpContractCostModel = new StockUpContractCostModel();
          stockUpContractCostModel.setContractCode("aa");
          StockUpContractCostModel stockUpContractCostModel1 = new StockUpContractCostModel();
          stockUpContractCostModel1.setContractCode("aab");
          StockUpContractCostModel stockUpContractCostModel2 = new StockUpContractCostModel();
          stockUpContractCostModel2.setContractCode("aa");

          costList.add(stockUpContractCostModel);
          costList.add(stockUpContractCostModel1);
          costList.add(stockUpContractCostModel2);
          List<StockUpContractCostModel> costs = new ArrayList<StockUpContractCostModel>();
          Map<String, String> maps = new HashMap<String, String>();
          for (StockUpContractCostModel cost : costList) {
              if (!cost.getContractCode().equals(maps.get(cost.getContractCode()))) {
                  costs.add(cost);
              }
              maps.put(cost.getContractCode(), cost.getContractCode());
          }
          System.out.println(costs);
      }*/
}
