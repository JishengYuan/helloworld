/*
 * FileName: BusinessReimbursementApplyService.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.business.order.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.ehcache.Ehcache;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinobridge.base.core.hibernate.id.IdentifierGeneratorImpl;
import com.sinobridge.base.core.service.DefaultBaseService;
import com.sinobridge.base.core.utils.JacksonJsonMapper;
import com.sinobridge.base.core.utils.PaginationSupport;
import com.sinobridge.eoss.bpm.service.process.ProcessInstService;
import com.sinobridge.eoss.bpm.service.process.ProcessService;
import com.sinobridge.eoss.business.order.constant.BusinessOrderContant;
import com.sinobridge.eoss.business.order.dao.BusinessOrderDao;
import com.sinobridge.eoss.business.order.dao.BusinessReimbursementApplyDao;
import com.sinobridge.eoss.business.order.model.BusinessOrderModel;
import com.sinobridge.eoss.business.order.model.BusinessReimbursementApply;
import com.sinobridge.eoss.business.order.model.BusinessReimbursementModel;
import com.sinobridge.eoss.business.suppliermanage.service.SupplierInfoService;
import com.sinobridge.eoss.sales.contract.model.SalesContractModel;
import com.sinobridge.eoss.sales.contract.service.SalesContractService;
import com.sinobridge.eoss.salesfundsCost.model.FundsSalesBusiReimbursement;
import com.sinobridge.eoss.salesfundsCost.model.FundsSalesContract;
import com.sinobridge.eoss.salesfundsCost.model.FundsSalesLog;
import com.sinobridge.eoss.salesfundsCost.service.FundsSalesBusiReimbursementService;
import com.sinobridge.eoss.salesfundsCost.service.FundsSalesContractService;
import com.sinobridge.eoss.salesfundsCost.service.FundsSalesLogService;
import com.sinobridge.systemmanage.model.SysStaff;
import com.sinobridge.systemmanage.service.SysStaffService;
import com.sinobridge.systemmanage.util.Constants;
import com.sinobridge.systemmanage.vo.SystemUser;

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
 * 2014年12月16日 下午3:16:05          tigq        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Service
public class BusinessReimbursementApplyService extends DefaultBaseService<BusinessReimbursementApply, BusinessReimbursementApplyDao> {

    private final static Ehcache systemUserCache = Constants.CACHE_MANAGER.getEhcache("systemUser");
    @Autowired
    private BusinessOrderService businessOrderService;
    @Autowired
    private SupplierInfoService supplierInfoService;
    @Autowired
    private SysStaffService staffService;
    @Autowired
    private ProcessService processService;
    @Autowired
    private SalesContractService salesContractService;
    @Autowired
    private BusinessReimbursementService businessReimbursementService;
    @Autowired
    private ProcessInstService processInstService;
    @Autowired
    private BusinessOrderDao businessOrderDao;
    @Autowired
    private FundsSalesContractService fundsSalesContractService;
    @Autowired
    private FundsSalesLogService fundsSalesLogService;
    @Autowired
    private FundsSalesBusiReimbursementService fundsSalesBusiReimbursementService;

    public PaginationSupport getListbyUser(String userId, int startIndex, Integer pageSize) {
        String hsql = "";
        List<BusinessReimbursementApply> rs = getDao().findPage(hsql, pageSize, startIndex);
        PaginationSupport ps = new PaginationSupport(rs, rs.size(), pageSize, startIndex);
        return ps;
    }

    /**
     * 保存发票报销
     * @param businessReimbursementApply
     * @param systemUser
     * @param tableData
     * @return
     */
    public String saverbmApply(BusinessReimbursementApply businessReimbursementApply, SystemUser systemUser, String tableData, String salesRbmData) {
        String rs = "true";
        Long reimId = IdentifierGeneratorImpl.generatorLong();
        businessReimbursementApply.setId(reimId);
        businessReimbursementApply.setCreateTime(new Date());
        businessReimbursementApply.setReimbursementUser(systemUser.getStaffName());
        businessReimbursementApply.setReimBursStatus(BusinessOrderContant.INTERPURCHAS_SH);
        List<Object> gridDataList = getTableGridData(tableData);
        JSONArray jsonArray = JSONArray.fromObject(salesRbmData);

        long modelId = 0L;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        List<BusinessReimbursementModel> rbms = new ArrayList<BusinessReimbursementModel>();

        if (gridDataList != null) {
            for (int i = 0; i < gridDataList.size(); i++) {//加了非空判断
                Map<String, Object> map = StringToMap(gridDataList.get(i).toString());//格式转换
                if (map != null) {
                    BusinessReimbursementModel rmbMode = new BusinessReimbursementModel();
                    modelId = IdentifierGeneratorImpl.generatorLong();
                    rmbMode.setId(modelId);
                    Long orderId = Long.valueOf(map.get("column8").toString());
                    BigDecimal amount = new BigDecimal(map.get("column3").toString());
                    BusinessOrderModel order = businessOrderService.get(orderId);
                    //                    Set<SalesContractModel> sales = order.getSalesContractModel();
                    String saleRemark = "";
                    //                    Iterator<SalesContractModel> s = sales.iterator();
                    //                    while (s.hasNext()) {
                    //                        SalesContractModel sale = s.next();
                    //                        saleRemark += "【合同编号：" + sale.getContractCode() + "，合同名称：" + sale.getContractName() + "，合同金额：￥" + sale.getContractAmount() + "】</br>";
                    //                    }

                    rmbMode.setRemark(saleRemark);
                    List<FundsSalesBusiReimbursement> fundsSalesBusiReimbursements = new ArrayList<FundsSalesBusiReimbursement>();
                    for (int j = 0; j < jsonArray.size(); j++) {
                        JSONObject jsonJ = jsonArray.getJSONObject(j);
                        List<SalesContractModel> sales = getDao().getSalesContract(jsonJ.getLong("salesid"));
                        if (sales.size() > 0) {
                            if (orderId == jsonJ.getLong("orderid")) {
                                FundsSalesBusiReimbursement fundsSalesBusiReimbursement = new FundsSalesBusiReimbursement();
                                fundsSalesBusiReimbursement.setContractBusiReimbursement(BigDecimal.valueOf(jsonJ.getDouble("value")));
                                fundsSalesBusiReimbursement.setDoDate(new Date());
                                fundsSalesBusiReimbursement.setDoState("0");
                                fundsSalesBusiReimbursement.setDoUser(systemUser.getStaffName());
                                fundsSalesBusiReimbursement.setSalesContractId(jsonJ.getLong("salesid"));
                                fundsSalesBusiReimbursement.setBusinessReimbursementModel(rmbMode);
                                fundsSalesBusiReimbursements.add(fundsSalesBusiReimbursement);
                            }
                        }
                    }
                    rmbMode.setFundsSalesBusiReimbursements(fundsSalesBusiReimbursements);
                    rmbMode.setBusinessOrder(order);
                    rmbMode.setSupplierInfo(supplierInfoService.get(Long.valueOf(map.get("column9").toString())));
                    rmbMode.setSupplierShortName(map.get("column1").toString());
                    rmbMode.setAmount(amount);
                    rmbMode.setCoursesType(map.get("column7").toString());
                    rmbMode.setInvoiceType(map.get("column6").toString());
                    rmbMode.setNumber(Integer.parseInt(map.get("column4").toString()));
                    rmbMode.setOrderCode(map.get("column0").toString());
                    updateOrderReimStatus(Long.parseLong(map.get("column8").toString()), new BigDecimal(map.get("column3").toString()));
                    try {
                        rmbMode.setPlanTime(format.parse(map.get("column5").toString()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    rmbMode.setCreator(systemUser.getStaffName());
                    rmbMode.setCreatorId(Long.parseLong(systemUser.getUserId()));
                    rmbMode.setCreateTime(new Date());
                    rmbMode.setBusinessReimbursementApply(businessReimbursementApply);
                    rbms.add(rmbMode);
                    String reimStatus = "S";
                    if (amount.compareTo(order.getOrderAmount()) == 0) {
                        reimStatus = "A";
                    }
                    getDao().updateOrderStatus(orderId, reimStatus);//修改订单中的付款状态
                }

            }
        }
        businessReimbursementApply.setReimbursementModes(rbms);
        //创建工单
        creatProcInst(businessReimbursementApply, reimId, systemUser);
        try {
            this.saveOrUpdate(businessReimbursementApply);
        } catch (Exception e) {
            e.printStackTrace();
            rs = "false";
        }

        return rs;
    }

    /**重新提交
     * @param reimburs
     * @param tableData
     * @param taskId
     * @param systemUser
     * @return
     */
    public String doUpdate(BusinessReimbursementApply reimburs, String tableData, String taskId, SystemUser systemUser) {
        String rs = "true";
        Long id = reimburs.getId();
        if (id != null) {//删除旧的付款计划
            businessReimbursementService.deletePlan(id);
        }
        reimburs.setReimBursStatus(BusinessOrderContant.INTERPURCHAS_SH);
        reimburs.setReimbursementUser(systemUser.getStaffName());
        List<Object> gridDataList = getTableGridData(tableData);
        long modelId = 0L;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        List<BusinessReimbursementModel> rbms = new ArrayList<BusinessReimbursementModel>();

        if (gridDataList != null) {
            for (int i = 0; i < gridDataList.size(); i++) {//加了非空判断
                Map<String, Object> map = StringToMap(gridDataList.get(i).toString());//格式转换
                if (map != null) {
                    BusinessReimbursementModel rmbMode = new BusinessReimbursementModel();
                    modelId = IdentifierGeneratorImpl.generatorLong();
                    rmbMode.setId(modelId);
                    rmbMode.setBusinessOrder(businessOrderService.get(Long.valueOf(map.get("column8").toString())));
                    rmbMode.setSupplierInfo(supplierInfoService.get(Long.valueOf(map.get("column9").toString())));
                    rmbMode.setSupplierShortName(map.get("column1").toString());
                    rmbMode.setAmount(BigDecimal.valueOf(Double.parseDouble(map.get("column3").toString())));
                    rmbMode.setCoursesType(map.get("column7").toString());
                    rmbMode.setInvoiceType(map.get("column6").toString());
                    rmbMode.setNumber(Integer.parseInt(map.get("column4").toString()));
                    rmbMode.setOrderCode(map.get("column0").toString());
                    updateOrderReimStatus(Long.valueOf(map.get("column8").toString()), BigDecimal.valueOf(Double.parseDouble(map.get("column3").toString())));
                    try {
                        rmbMode.setPlanTime(format.parse(map.get("column5").toString()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    rmbMode.setRemark(map.get("column10").toString());
                    rmbMode.setCreator(systemUser.getStaffName());
                    rmbMode.setCreatorId(Long.parseLong(systemUser.getUserId()));
                    rmbMode.setCreateTime(new Date());
                    rmbMode.setBusinessReimbursementApply(reimburs);
                    rbms.add(rmbMode);
                }

            }
        }
        reimburs.setReimbursementModes(rbms);
        Map<String, Object> map = new HashMap<String, Object>();
        //从新提交订单时提供的key值
        map.put(BusinessOrderContant.IS_RE_SUBMIT, true);
        map.put(BusinessOrderContant.REIM_AMOUNT, reimburs.getAmount());
        processService.handle(taskId, systemUser.getUserName(), map, null, null);
        try {
            this.saveOrUpdate(reimburs);
        } catch (Exception e) {
            e.printStackTrace();
            rs = "false";
        }
        String[] t = reimburs.getReimbursementName().split("\\[");
        String title = t[0] + "[" + reimburs.getAmount() + "]";
        getDao().updateRmbTitle(title, reimburs.getProcessInstanceId());
        return rs;
    }

    /**更新订单报销状态
     * @param orderId
     * @param amount
     */
    private void updateOrderReimStatus(Long orderId, BigDecimal amount) {
        BusinessOrderModel order = businessOrderService.get(orderId);
        String flat = "";
        BigDecimal am = getDao().findReimAmount(orderId);
        am = am.add(amount);
        if (am.compareTo(order.getOrderAmount()) == -1) {
            flat = BusinessOrderContant.ORDER_PAYMENT_S;
        } else {
            flat = BusinessOrderContant.ORDER_PAYMENT_ASP;
        }
        getDao().updateOrderReim(orderId, flat);
    }

    /**
     * <code>creatProcInst</code>
     * 创建发票申请报销工单
     * @param map
     * @since   2014年12月19日    wangya
     */
    private void creatProcInst(BusinessReimbursementApply businessReimbursementApply, long reimId, SystemUser systemUser) {
        Long valId = processService.nextValId();
        businessReimbursementApply.setProcessInstanceId(valId);
        Map<String, Object> variableMap = new HashMap<String, Object>();
        variableMap.put("reimId", reimId);
        variableMap.put(BusinessOrderContant.REIM_AMOUNT, businessReimbursementApply.getAmount());
        //创建工单
        Long[] procInstId = processService.create(valId, businessReimbursementApply.getReimbursementName() + "[￥" + businessReimbursementApply.getAmount() + "]", systemUser.getUserName(), BusinessOrderContant.REIM_KEY, 1, variableMap, null, null, null);
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

    public synchronized Map<String, Object> StringToMap(String mapText) {

        if (mapText == null || mapText.equals("")) {
            return null;
        }
        mapText = mapText.substring(1, mapText.length() - 1);
        Map<String, Object> map = new HashMap<String, Object>();
        String[] text = mapText.split(",");
        for (String str : text) {
            String[] keyText = str.split("="); // 转换key与value的数组  
            if (keyText.length < 1) {
                continue;
            }
            if (keyText.length == 1) {

            } else {
                String key = keyText[0]; // key  
                String value = keyText[1]; // value  
                if (!value.trim().equals("")) {
                    map.put(key, value);
                }
            }
        }
        return map;
    }

    /**
     * 查询订单的计划金额
     * @param id
     * @return
     */
    public BigDecimal findReimAmount(Long id) {
        BigDecimal amount = getDao().findReimAmount(id);
        return amount;
    }

    /**
     * 查询报销单
     * @param detachedCriteria
     * @return
     */
    public List<BusinessReimbursementApply> findOrderByCriteria(DetachedCriteria detachedCriteria) {
        List<BusinessReimbursementApply> reim = new ArrayList<BusinessReimbursementApply>();
        reim = getDao().findByCriteria(detachedCriteria);
        return reim;
    }

    /**
     * 处理流程
     * @param flowStep
     * @param systemUser
     * @param reimburs
     * @param taskId
     * @param isAgree
     * @param remark
     */
    public void handleFlow(String flowStep, SystemUser systemUser, BusinessReimbursementApply reimburs, String taskId, int isAgree, String remark) {
        String userId = systemUser.getUserName();
        Map<String, Object> variableMap = new HashMap<String, Object>();
        BusinessReimbursementApply reim = this.get(reimburs.getId());
        if (flowStep.equals(BusinessOrderContant.CWFH) && isAgree == 1) {//财务通过
            List<BusinessReimbursementModel> plan = reim.getReimbursementModes();
            Iterator<BusinessReimbursementModel> p = plan.iterator();
            while (p.hasNext()) {
                BusinessReimbursementModel planModel = p.next();
                BusinessOrderModel order = planModel.getBusinessOrder();
                BigDecimal amount = new BigDecimal(0.00);
                amount = amount.add(planModel.getAmount());
                amount = amount.add(order.getReimAmount());
                amount = amount.add(new BigDecimal(order.getSpotNum()));
                String planStatus = BusinessOrderContant.ORDER_PAYMENT_S;
                if (amount.compareTo(order.getOrderAmount()) == 0) {
                    planStatus = BusinessOrderContant.ORDER_PAYMENT_A;
                }
                getDao().updateOrderReimAmount(order.getId(), amount, planStatus);//更新订单的发票金额

                /*****************************更新合同进项发票****************************************************/
                List<FundsSalesBusiReimbursement> fundsSalesBusiReimbursements = planModel.getFundsSalesBusiReimbursements();
                for (FundsSalesBusiReimbursement fundsSalesBusiReimbursement : fundsSalesBusiReimbursements) {
                    SalesContractModel salesContractModel = salesContractService.get(fundsSalesBusiReimbursement.getSalesContractId());
                    FundsSalesContract fundsSalesContract = fundsSalesContractService.getBycontractCode(salesContractModel.getContractCode());
                    if (fundsSalesContract == null) { //只要付款，就记录该合同的信息，备货也记录
                        fundsSalesContract = new FundsSalesContract();
                        fundsSalesContract.setContractCode(salesContractModel.getContractCode());
                        fundsSalesContract.setContractAmount(salesContractModel.getContractAmount());
                        fundsSalesContract.setContractName(salesContractModel.getContractName());
                        fundsSalesContract.setCreatorName(salesContractModel.getCreatorName());
                        fundsSalesContract.setSalesStartDate(salesContractModel.getSalesStartDate());
                        log.error("合同：" + salesContractModel.getContractCode() + ",原来没有进行商务成本计算！");
                    }
                    BigDecimal hiAmount = fundsSalesContract.getIncomeInvoice();
                    BigDecimal amount1 = new BigDecimal(0.00);
                    if (fundsSalesContract.getIncomeInvoice() != null) {
                        amount1 = amount1.add(fundsSalesContract.getIncomeInvoice());
                    }
                    amount1 = amount1.add(fundsSalesBusiReimbursement.getContractBusiReimbursement());
                    fundsSalesContract.setIncomeInvoice(amount1);
                    fundsSalesContractService.saveOrUpdate(fundsSalesContract);
                    //记录日志
                    FundsSalesLog fundsSalesLog = new FundsSalesLog();
                    fundsSalesLog.setOpAmount(amount1);
                    fundsSalesLog.setOpDate(new Date());
                    fundsSalesLog.setContractCode(salesContractModel.getContractCode());
                    fundsSalesLog.setOpDesc("进项发票");
                    /*历史金额，没有的话使用第一次作为历史金额*/
                    if (hiAmount != null) {
                        fundsSalesLog.setHiAmount(hiAmount);
                    } else {
                        fundsSalesLog.setHiAmount(amount);
                    }
                    fundsSalesLogService.saveOrUpdate(fundsSalesLog);

                    //更新商务分拆发票金额状态
                    fundsSalesBusiReimbursement.setAuditDate(new Date());
                    fundsSalesBusiReimbursement.setDoState("1");
                    fundsSalesBusiReimbursementService.saveOrUpdate(fundsSalesBusiReimbursement);
                }
            }
            Date closeTime = new Date();
            getDao().updatePlanStatus(reim.getId(), closeTime);//发票申请：审批通过
        }
        processService.handle(taskId, userId, isAgree, remark, variableMap, null, null);//流程处理

        //该到财务审批了 给申请人发邮件
        if (flowStep.equals("SWFZCSP") && isAgree == 1 && reim.getAmount().compareTo(new BigDecimal("500000.00")) == -1) {
            SysStaff staff = staffService.getStaffByName(reim.getReimbursementUser());
            if (staff != null) {
                processService.sendEmail("系统提醒:发票审批打印", "您的发票到财务审批了。", null, null, staff.getUserName(), null, null);
            }
        }
        if (flowStep.equals("ZCSP") && isAgree == 1) {
            SysStaff staff = staffService.getStaffByName(reim.getReimbursementUser());
            if (staff != null) {
                processService.sendEmail("系统提醒:发票审批打印", "您的发票到财务审批了。", null, null, staff.getUserName(), null, null);
            }
        }

    }

    /**
     *  <code>reimTaskList</code>
     * 得到财务待处理的任务
     * @param search
     * @param i
     * @param pageSize
     * @return
     */
    @SuppressWarnings("unchecked")
    public PaginationSupport reimTaskList(Map<String, Object> search, int pageStart, Integer pageEnd) {

        Object sortname = search.get("sortname");
        Object sortorder = search.get("sortorder");
        Object reimbursementName = search.get(BusinessReimbursementApply.REIMNAME);
        Object amount = search.get(BusinessReimbursementApply.AMOUNT);
        Object systemUser = search.get("systemUser");
        if (sortname.equals("createTime")) {
            sortname = "create_time_";
        }
        StringBuilder sb = new StringBuilder();

        sb.append("SELECT * FROM (( SELECT * FROM ( SELECT ");
        sb.append("p.id_ procInstId,");
        sb.append("b.ReimbursementName,");
        sb.append("b.amount,");
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
        sb.append("u.last_");
        sb.append(" FROM bpm_process_inst p,act_ru_task t,act_ru_identitylink l,act_id_membership m,act_id_group g,bpm_process_def d,act_id_user u, business_reimbursement_apply b,bpm_task_inst i ");
        sb.append(" WHERE m.user_id_ = u.id_ AND m.group_id_ = g.id_ AND g.id_ = l.group_id_ AND l.task_id_ = t.id_ AND t.proc_inst_id_ = p.hi_proc_inst_id AND p.proc_def_ = d.id_ AND u.id_ = ? AND t.assignee_ IS NULL AND p.status_ = 1 AND b.processInstanceId = p.id_ AND i.id_ = t.id_ ");

        if (reimbursementName != null) {
            sb.append(" AND b.ReimbursementName like '%" + reimbursementName + "%' ");
        }

        if (amount != null) {
            String payAmount = amount.toString();
            if (payAmount.indexOf(".") != -1) {
                sb.append(" AND b.amount='" + payAmount + "' ");
            } else {
                sb.append(" AND b.amount like '" + payAmount + ".%' ");
            }
        }
        sb.append("order by p." + sortname + " " + sortorder);
        sb.append(" ) as t1");

        sb.append(" ) UNION ( SELECT * FROM (SELECT");
        sb.append(" p.id_ procInstId,");
        sb.append("b.ReimbursementName,");
        sb.append("b.amount,");
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
        sb.append("u.last_");
        sb.append(" FROM bpm_process_inst p,act_ru_task t,bpm_process_def d,act_id_user u,business_reimbursement_apply b,bpm_task_inst i ");
        sb.append(" WHERE t.proc_inst_id_ = p.hi_proc_inst_id AND p.PROC_DEF_ = d.ID_ AND t.assignee_ = ? AND p.status_ = 1 AND u.ID_ = i.prev_handler_ AND b.processInstanceId = p.id_ AND i.id_ = t.id_ ");

        if (reimbursementName != null) {
            sb.append(" AND b.ReimbursementName like '%" + reimbursementName + "%' ");
        }
        if (amount != null) {
            BigDecimal payAmount = new BigDecimal(amount.toString());
            sb.append(" AND b.amount like '" + payAmount + ".%' ");
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
        query.setFirstResult(pageStart);
        query.setMaxResults(pageEnd);
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

        List<Map<String, Object>> mapList = query.list();
        for (Map<String, Object> map : mapList) {
            String staffName = ((SysStaff) systemUserCache.get(map.get("prev_handler_").toString()).getObjectValue()).getStaffName();
            map.put("prev_handler_", staffName);
            staffName = ((SysStaff) systemUserCache.get(map.get("creator").toString()).getObjectValue()).getStaffName();
            map.put("creator", staffName);
        }

        PaginationSupport paginationSupport = new PaginationSupport(mapList, reimTaskCount(sb.toString(), params));
        return paginationSupport;
    }

    /**
     * <code>reimTaskCount</code>
     * 得到财务待处理任务的总数量
     * @param sql
     * @param params
     * @return
     * @since   2014年12月18日    wangya
     */
    public Integer reimTaskCount(String sql, String[] params) {
        sql = "select count(*) from (" + sql + " ) as total";
        return Integer.valueOf(getDao().createSQLQuery(sql, params).list().get(0).toString()).intValue();
    }

    /** 
     * 删除报销单
     * @param taskId
     * @param reimApply
     * @param systemUser
     */
    public void endreim(String taskId, BusinessReimbursementApply reimApply, SystemUser systemUser) {
        Long id = reimApply.getId();
        BusinessReimbursementApply reim = this.get(id);
        List<BusinessOrderModel> setOrder = new ArrayList<BusinessOrderModel>();
        List<BusinessReimbursementModel> r = reim.getReimbursementModes();
        Iterator<BusinessReimbursementModel> re = r.iterator();
        while (re.hasNext()) {
            //更新订单报销状态
            BusinessReimbursementModel reims = re.next();
            BusinessOrderModel order = reims.getBusinessOrder();
            if (order.getReimStatus().equals("A")) {
                order.setReimStatus("S");
                setOrder.add(order);
            }
        }
        businessOrderDao.saveOrUpdateAll(setOrder);
        /* getDao().endreimStatus(id);
         Map<String, Object> map = new HashMap<String, Object>();
         //从新提交订单时提供的key值
         map.put(BusinessOrderContant.IS_RE_SUBMIT, false);
         processService.handle(taskId, systemUser.getUserName(), map, null, null);*/
        try {
            // processService.handle(taskId, systemUser.getUserName(), map, null, null);

            processInstService.removeProcessInst(reimApply.getProcessInstanceId().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.delete(id);
    }

    /**
     * 通过ID查询报销单
     * @param procInstId
     * @return
     */
    public BusinessReimbursementApply findReimId(String procInstId) {
        BusinessReimbursementApply reim = getDao().findReimId(procInstId);
        return reim;
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
    public PaginationSupport getModelPage(Map<String, Object> searchMap, Integer startIndex, Integer pageSize) {
        int start = 0;
        Object[] values = new Object[searchMap.size()];
        Object startTime = searchMap.get("startTime");
        Object endTime = searchMap.get("endTime");
        //Object supplierShortName = searchMap.get("supplierId");
        Object minAmount = searchMap.get("minAmount");
        // Object maxAmount = searchMap.get("maxAmount");

        String hql = "select c.* from business_reimbursement_apply c where c.reimBursStatus='10' ";
        if (startTime != null) {
            values[start++] = startTime + "";
            hql += " and c.CloseTime>=?";
        }
        if (endTime != null) {
            values[start++] = endTime + "";
            hql += " and c.CloseTime<=?";
        }
        /* if (supplierShortName != null) {
             values[start++] = supplierShortName + "";
             hql += " and c.SupplierName=?";
         }*/
        if (minAmount != null) {
            values[start++] = minAmount + "";
            hql += " and c.amount=?";
        }
        /* if (maxAmount != null) {
             values[start++] = maxAmount + "";
             hql += " and c.amount<=?";
         }*/
        hql += " order by c.id desc";
        Query query = null;
        if (searchMap.size() != 0) {
            query = getDao().createSQLQuery(hql, values).addEntity(BusinessReimbursementApply.class).setFirstResult(startIndex).setMaxResults(pageSize);
        } else {
            query = getDao().createSQLQuery(hql).addEntity(BusinessReimbursementApply.class).setFirstResult(startIndex).setMaxResults(pageSize);
        }
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

    /**报销列表
     * @param searchMap
     * @param i
     * @param pageSize
     * @return
     */
    public PaginationSupport findPageByReim(HashMap<String, Object> searchMap, int startIndex, Integer pageSize) {
        return getDao().findPayPage(searchMap, startIndex, pageSize);
    }

    /**判断是否可以打印
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

    /**
     * 查询组织名称
     * @param reimbursementUser
     * @return
     */
    public String findSatff(String reimbursementUser) {
        String orgName = getDao().findSatff(reimbursementUser);
        return orgName;
    }

    /**查询报销金额
     * @param id
     * @return
     */
    public BigDecimal getReimAmount(String id) {
        BigDecimal amount = getDao().getReimAmount(id);
        return amount;
    }

    /**查询发票申请相关信息
     * @param id
     * @return
     */
    public List<BusinessReimbursementApply> getReimApplys(Long id) {
        List<BusinessReimbursementApply> reimApplys = getDao().getReimApplys(id);
        return reimApplys;
    }

    /**
     * @param id
     * @return
     */
    public List<Map<String, String>> getInterPurse(Long id) {
        List<Map<String, String>> inter = getDao().getInterPurse(id);
        return inter;
    }

}
