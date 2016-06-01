/*
 * FileName: SalesContractService.java
 */
package com.sinobridge.eoss.salesfundsCost.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
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
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinobridge.base.core.hibernate.id.IdentifierGeneratorImpl;
import com.sinobridge.base.core.service.DefaultBaseService;
import com.sinobridge.base.core.utils.JacksonJsonMapper;
import com.sinobridge.base.core.utils.PaginationSupport;
import com.sinobridge.eoss.bpm.service.process.ProcessService;
import com.sinobridge.eoss.sales.contract.constant.SalesContractConstant;
import com.sinobridge.eoss.sales.contract.dao.SalesContractStatusDao;
import com.sinobridge.eoss.sales.contract.model.SalesContractModel;
import com.sinobridge.eoss.sales.contract.model.SalesContractStatusModel;
import com.sinobridge.eoss.sales.contract.service.SalesContractService;
import com.sinobridge.eoss.sales.contract.utils.CodeUtils;
import com.sinobridge.eoss.sales.invoice.dao.SalesInvoiceDao;
import com.sinobridge.eoss.sales.invoice.model.SalesInvoicePlanModel;
import com.sinobridge.eoss.sales.invoice.service.SalesInvoiceService;
import com.sinobridge.eoss.salesfundsCost.dao.FundsSalesBudgetDao;
import com.sinobridge.eoss.salesfundsCost.dao.SalesBudgetLogDao;
import com.sinobridge.eoss.salesfundsCost.model.SalesBudgetFunds;
import com.sinobridge.eoss.salesfundsCost.model.SalesBudgetLog;
import com.sinobridge.eoss.salesfundsCost.utils.DateUtils;
import com.sinobridge.systemmanage.vo.SystemUser;

@Service
@Transactional
public class FundsSalesBudgetService extends DefaultBaseService<SalesBudgetFunds, FundsSalesBudgetDao> {

    @Autowired
    private SalesContractService salesContractService;
    @Autowired
    private SalesBudgetLogDao salesBudgetLogDao;
    @Autowired
    private SalesInvoiceService salesInvoiceService;
    @Autowired
    private SalesInvoiceDao salesInvoiceDao;
    @Autowired
    private SalesContractStatusDao salesContractStatusDao;

    @Autowired
    private ProcessService processService;

    @SuppressWarnings("unchecked")
    public PaginationSupport getModelPage(String hql, Integer startIndex, Integer pageSize) {
        PaginationSupport paginating = getDao().findAllFundsSalesBudget(hql, startIndex, pageSize);
        List<Map<String, Object>> rows = (List<Map<String, Object>>) paginating.getItems();
        List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < rows.size(); i++) {
            Map<String, Object> entity = rows.get(i);
            List<Map<String, Object>> list = getDao().getSalesReceive(entity.get("id").toString());
            if (list.size() > 0) {
                entity.put("receive", list.get(0).get("amount"));
            } else {
                entity.put("receive", "0");
            }
            List<Map<String, Object>> listInvioce = getDao().getSalesInvioce(entity.get("id").toString());
            if (listInvioce.size() > 0) {
                entity.put("invoice", listInvioce.get(0).get("amount"));
            } else {
                entity.put("invoice", "0");
            }
            listmap.add(entity);
        }
        paginating.setItems(listmap);
        return paginating;
    }

    public void saveBudget(SalesBudgetFunds salesBudgetFunds) {
        getDao().save(salesBudgetFunds);
    }

    public void delBudget(String id) {
        getDao().delBudget(id);
    }

    public List<SalesContractModel> findSalesContractByCreator(String createName) {
        return getDao().findSalesContractByCreator(createName);
    }

    /**
     * @param timeSpace
     * @param systemUser
     * @return
     */
    public Map<String, Object> getStatist(String timeSpace, SystemUser systemUser) {
        Map<String, Object> map = new HashMap<String, Object>();
        Date firstTime = null;
        Date lastTime = null;
        String furTime = "";
        String first = null;
        String last = null;
        if ("week".equals(timeSpace)) {//周统计
            Date[] week = DateUtils.getLastWeekTime();
            firstTime = week[0];
            lastTime = week[1];
            try {
                furTime = DateUtils.someDayAgo(new Date(), 7);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            map.put("time", "周");
        }
        if ("month".equals(timeSpace)) {//月统计
            firstTime = DateUtils.getLastMonthDay();
            try {
                last = DateUtils.someDayAgo(firstTime, 30);
                furTime = DateUtils.someDayAgo(new Date(), 30);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            map.put("time", "月");
        }
        if ("total".equals(timeSpace)) {//总统计
            firstTime = null;
            map.put("time", "总");
        }

        try {
            if (firstTime != null) {
                first = DateUtils.convertDateToString(firstTime, "yyyy-MM-dd");
            }
            if (lastTime != null) {
                last = DateUtils.convertDateToString(lastTime, "yyyy-MM-dd");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<Map<String, Object>> realRecive = getDao().getRealRecive(systemUser.getUserId(), first, last);
        if (realRecive.get(0).get("total") != null) {
            BigDecimal amount = new BigDecimal(0.00);
            amount = new BigDecimal(realRecive.get(0).get("total").toString()).setScale(2);
            map.put("realRecive", amount);
        } else {
            map.put("realRecive", "0.00");
        }
        List<Map<String, Object>> budRecive = getDao().getBudRecive(systemUser.getUserId(), first, last);
        if (budRecive.get(0).get("bugReceive") != null) {
            BigDecimal amount = new BigDecimal(0.00);
            amount = new BigDecimal(budRecive.get(0).get("bugReceive").toString());
            map.put("bugReceive", amount);
        } else {
            map.put("bugReceive", "0.00");
        }
        if (budRecive.get(0).get("bugInvoice") != null) {
            BigDecimal amount = new BigDecimal(0.00);
            amount = new BigDecimal(budRecive.get(0).get("bugInvoice").toString());
            map.put("bugInvoice", amount);
        } else {
            map.put("bugInvoice", "0.00");
        }
        List<Map<String, Object>> realInvioce = getDao().getRealInvoice(systemUser.getUserId(), first, last);
        if (realInvioce.get(0).get("total") != null) {
            BigDecimal amount = new BigDecimal(0.00);
            amount = new BigDecimal(realInvioce.get(0).get("total").toString());
            map.put("realInvioce", amount);
        } else {
            map.put("realInvioce", "0.00");
        }
        List<Map<String, Object>> furBudget = new ArrayList<Map<String, Object>>();
        try {
            furBudget = getDao().getFurBudget(systemUser.getUserId(), DateUtils.convertDateToString(new Date(), "yyyy-MM-dd"), furTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (furBudget.get(0).get("bugReceive") != null) {
            BigDecimal amount = new BigDecimal(0.00);
            amount = new BigDecimal(furBudget.get(0).get("bugReceive").toString());
            map.put("furReceive", amount);
        } else {
            map.put("furReceive", "0.00");
        }
        if (furBudget.get(0).get("bugInvoice") != null) {
            BigDecimal amount = new BigDecimal(0.00);
            amount = new BigDecimal(furBudget.get(0).get("bugInvoice").toString());
            map.put("furInvoice", amount);
        } else {
            map.put("furInvoice", "0.00");
        }
        return map;
    }

    /**
     * @param saleId
     * @return
     */
    public Map<String, Object> getSales(String saleId) {
        List<Map<String, Object>> list = getDao().getSales(saleId);
        Map<String, Object> map = list.get(0);
        return map;
    }

    /**回款保存
     * @param id
     * @param recieveAmount
     * @param recieveTime
     * @param fundsId 
     * @return
     */
    public Map<String, Object> doRecieve(String id, String recieveAmount, String recieveTime, String fundsId) {
        SalesBudgetFunds funds = new SalesBudgetFunds();
        SalesBudgetLog log = new SalesBudgetLog();
        if (fundsId != null) {
            funds = this.get(Long.parseLong(fundsId));
            log.setBudgetFundsId(Long.parseLong(fundsId));
            log.setRemark("更新预测回款，金额：" + recieveAmount + ",时间：" + recieveTime);
        } else {
            long ids = IdentifierGeneratorImpl.generatorLong();
            funds.setId(ids);
            funds.setContractId(Long.parseLong(id));
            SalesContractModel sales = salesContractService.get(Long.parseLong(id));
            funds.setContractCode(sales.getContractCode());
            funds.setContractName(sales.getContractName());
            funds.setUserName(sales.getCreatorName());
            long start = DateUtils.getStartTime();
            long end = DateUtils.getEndTime();
            DetachedCriteria detachedCriteria = DetachedCriteria.forClass(SalesBudgetFunds.class);
            detachedCriteria.add(Restrictions.ge(SalesBudgetFunds.CREATEDATE, new Date(start)));
            detachedCriteria.add(Restrictions.le(SalesBudgetFunds.CREATEDATE, new Date(end)));
            detachedCriteria.add(Restrictions.isNotNull(SalesBudgetFunds.BUDGETRECEIVE));
            List<SalesBudgetFunds> salesBudgetFunds = getDao().findByCriteria(detachedCriteria);
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
            log.setRemark("预测回款，金额：" + recieveAmount + ",时间：" + recieveTime);
        }
        log.setCreateDate(new Date());
        funds.setCreateDate(new Date());
        funds.setBudgetReceive(new BigDecimal(recieveAmount));
        funds.setBudgetDate(DateUtils.convertStringToDate(recieveTime, "yyyy-MM-dd"));

        Map<String, Object> msg = new HashMap<String, Object>();

        try {
            getDao().saveOrUpdate(funds);
            salesBudgetLogDao.save(log);
            msg.put("date", "success");
        } catch (DataAccessException e) {
            e.printStackTrace();
            msg.put("date", "flase");
            msg.put("msg", "预测数据保存失败！");
        }
        return msg;
    }

    /**
     * @param saleId
     * @return
     */
    public List<Map<String, Object>> getSalesInvoice(String saleId) {
        List<Map<String, Object>> list = getDao().getSalesInvoice(saleId);
        return list;
    }

    /**
     * @param id
     * @param invoiceAmount
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<SalesInvoicePlanModel> doInvoice(String id, String tableData) {
        List<SalesInvoicePlanModel> invoiceReturn = new ArrayList<SalesInvoicePlanModel>();

        List<SalesBudgetFunds> fundbuds = new ArrayList<SalesBudgetFunds>();
        List<SalesBudgetLog> logs = new ArrayList<SalesBudgetLog>();
        List<SalesInvoicePlanModel> invoices = new ArrayList<SalesInvoicePlanModel>();

        List<SalesBudgetFunds> fundOld = getDao().getfundOld(id);//历史数据
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
                        String fundsId = null;
                        if (map.get("column5") != null) {
                            fundsId = map.get("column5").toString();
                        }
                        String flat = map.get("column0").toString();
                        String invoiceAmount = map.get("column1").toString();
                        String invoiceTime = map.get("column2").toString();
                        String invoiceType = map.get("column4").toString();
                        String remark = "";
                        if (map.get("column3") != null) {
                            remark = map.get("column3").toString();
                        }
                        if (fundsId != null) {//原来有的，修改
                            newMap.put(Long.parseLong(fundsId), invoiceTime);
                            funds = this.get(Long.parseLong(fundsId));
                            log.setBudgetFundsId(Long.parseLong(fundsId));
                            log.setRemark("更新预测发票，金额：" + invoiceAmount + ",时间：" + invoiceTime);
                            invoice = salesInvoiceService.get(funds.getSalesInvoiceId());
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
                            List<SalesBudgetFunds> salesBudgetFunds = getDao().findByCriteria(detachedCriteria);
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
                    SalesBudgetFunds fundold = this.get(k);
                    getDao().delete(fundold);
                    salesInvoiceService.delete(fundold.getSalesInvoiceId());
                }
            }
        }

        Map<String, Object> msg = new HashMap<String, Object>();

        try {
            getDao().saveOrUpdateAll(fundbuds);
            salesBudgetLogDao.saveOrUpdateAll(logs);
            salesInvoiceDao.saveOrUpdateAll(invoices);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return invoiceReturn;
    }

    /**
     * @param hql
     * @param i
     * @param pageSize
     * @return
     */
    public PaginationSupport getHisReceive(String hql, int startIndex, Integer pageSize) {
        PaginationSupport paginating = getDao().findHisReceive(hql, startIndex, pageSize);
        return paginating;
    }

    /**
     * @param hql
     * @param i
     * @param pageSize
     * @return
     */
    public PaginationSupport getHisInvoice(String hql, int startIndex, Integer pageSize) {
        //最近一个月的
        Date time = DateUtils.getNextMonth();
        try {
            String timeStr = DateUtils.convertDateToString(time, "yyyy-MM-dd");
            hql += " and f.budgetDate<='" + timeStr + "' ";
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        PaginationSupport paginating = getDao().findHisInvoice(hql, startIndex, pageSize);
        return paginating;
    }

    /**
     * @param systemUser
     * @return
     */
    public List<Map<String, Object>> getSalesName(SystemUser systemUser) {
        Date time = new Date();
        String startTime = "";
        String endTime = "";
        try {
            startTime = DateUtils.someDayAgo(time, 7);
            endTime = DateUtils.someDayAgo(time, 30);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<Map<String, Object>> list = getDao().getSalesName(systemUser.getUserId(), startTime, endTime);
        return list;
    }

    /**
     * @param saleId
     * @return
     */
    public String getReceiveAmount(String saleId) {
        List<Map<String, Object>> amount = getDao().getSalesReceive(saleId);
        if (amount.size() > 0) {
            return amount.get(0).get("amount").toString();
        } else {
            return "0";
        }
    }

    /**
     * @param saleId
     * @return
     */
    public String getInvoiceAmount(String saleId) {
        List<Map<String, Object>> listInvioce = getDao().getSalesInvioce(saleId);
        if (listInvioce.size() > 0) {
            return listInvioce.get(0).get("amount").toString();
        } else {
            return "0";
        }
    }

    /*    *//**
                                                                                                                                                                                                                           * <code>getSalesContractByCode</code>
                                                                                                                                                                                                                           * 根据Code 查合同Name
                                                                                                                                                                                                                           * @param code
                                                                                                                                                                                                                           * @return
                                                                                                                                                                                                                           * @since   2014年12月25日    guokemenng
                                                                                                                                                                                                                           */
    /*
    @SuppressWarnings("unchecked")
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
    */
    /**
     * @param sql
     * @param i
     * @param pageSize
     * @return
     */
    public PaginationSupport findSalesTotalInfo(String sql, int pageNo, Integer pageSize) {
        PaginationSupport pa = getDao().findSalesTotalInfo(sql, pageNo, pageSize);
        return pa;
    }

    /**
     * @param id
     * @return
     */
    public List<SalesBudgetLog> getSalesLogs(Long id) {
        List<SalesBudgetLog> logs = getDao().getSalesLogs(id);
        return logs;
    }

    /**
     * @param start
     * @param end
     * @param systemUser
     * @return
     */
    public Map<String, Object> getSelfChoice(String start, String end, SystemUser systemUser) {
        Map<String, Object> map = new HashMap<String, Object>();
        String nowTime = "";
        String startTime = "";
        String midTime = null;
        String endTime = "";
        try {
            nowTime = DateUtils.convertDateToString(new Date(), "yyyy-MM-dd");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int i = DateUtils.timeCompare(start, nowTime);
        int j = DateUtils.timeCompare(nowTime, end);
        if (i == -1 && j == -1) {//开始时间小于当前时间，并且结束时间大于当前时间
            midTime = nowTime;
        }
        startTime = start;
        endTime = end;
        List<Map<String, Object>> invoiceList = getDao().getRealInvoice(systemUser.getUserId(), startTime, endTime);
        if (invoiceList.size() > 0) {
            BigDecimal amount = new BigDecimal(0.00);
            amount = new BigDecimal(invoiceList.get(0).get("total").toString());
            map.put("realInvioce", amount);
        } else {
            map.put("realInvioce", "0.00");
        }
        List<Map<String, Object>> revoiceList = getDao().getRealRecive(systemUser.getUserId(), startTime, endTime);
        if (revoiceList.get(0).get("total") != null) {
            BigDecimal amount = new BigDecimal(0.00);
            amount = new BigDecimal(revoiceList.get(0).get("total").toString()).setScale(2);
            map.put("realRecive", amount);
        } else {
            map.put("realRecive", "0.00");
        }
        List<Map<String, Object>> realBudget = getDao().getBudRecive(systemUser.getUserId(), startTime, endTime);
        if (realBudget.get(0).get("bugReceive") != null) {
            BigDecimal amount = new BigDecimal(0.00);
            amount = new BigDecimal(realBudget.get(0).get("bugReceive").toString());
            map.put("bugReceive", amount);
        } else {
            map.put("bugReceive", "0.00");
        }
        if (realBudget.get(0).get("bugInvoice") != null) {
            BigDecimal amount = new BigDecimal(0.00);
            amount = new BigDecimal(realBudget.get(0).get("bugInvoice").toString());
            map.put("bugInvoice", amount);
        } else {
            map.put("bugInvoice", "0.00");
        }
        if (midTime != null) {
            List<Map<String, Object>> furBudget = getDao().getFurBudget(systemUser.getUserId(), midTime, endTime);
            if (furBudget.get(0).get("bugReceive") != null) {
                BigDecimal amount = new BigDecimal(0.00);
                amount = new BigDecimal(furBudget.get(0).get("bugReceive").toString());
                map.put("furReceive", amount);
            } else {
                map.put("furReceive", "0.00");
            }
            if (furBudget.get(0).get("bugInvoice") != null) {
                BigDecimal amount = new BigDecimal(0.00);
                amount = new BigDecimal(furBudget.get(0).get("bugInvoice").toString());
                map.put("furInvoice", amount);
            } else {
                map.put("furInvoice", "0.00");
            }
            map.put("midTime", midTime);
        }
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        return map;
    }

    /**
     * @param id
     * @return
     */
    public List<SalesBudgetFunds> getInvoiceList(Long id) {
        List<SalesBudgetFunds> invoiceList = getDao().getInvoiceList(id);
        return invoiceList;
    }

    /**
     * @param id
     * @return
     */
    public List<SalesBudgetFunds> getReceiveList(Long id) {
        List<SalesBudgetFunds> receive = getDao().getReceiveList(id);
        return receive;
    }

    /**
     * @param saleId
     * @return
     */
    public List<SalesBudgetFunds> getFundsInvoiceBySaleId(String saleId) {
        List<SalesBudgetFunds> invoice = getDao().getFundsInvoiceBySaleId(saleId);
        return invoice;
    }

    /**
     * @param saleId
     * @return
     */
    public List<SalesBudgetLog> getInvoiecLogBySaleID(String saleId) {
        List<SalesBudgetLog> logs = getDao().getInvoiecLogBySaleID(saleId);
        return logs;
    }

    /**提交
     * @param id
     * @param systemUser 
     * @param choise
     * @return
     */
    @SuppressWarnings("unchecked")
    public String doSaveInvoice(String id, List<SalesInvoicePlanModel> invoiceReturn, SystemUser systemUser) {
        List<SalesBudgetFunds> fundbuds = new ArrayList<SalesBudgetFunds>();
        List<SalesInvoicePlanModel> invoices = new ArrayList<SalesInvoicePlanModel>();
        BigDecimal invoiceCount = new BigDecimal(0.00);
        long processInstanceId = processService.nextValId();
        int flat = 0;//加急标志：有发票日期距当前时间小于7天
        Date today = new Date();
        String rs = "";

        Iterator<SalesInvoicePlanModel> plan = invoiceReturn.iterator();
        while (plan.hasNext()) {
            SalesInvoicePlanModel p = plan.next();
            SalesBudgetFunds funds = new SalesBudgetFunds();//存预测表
            funds = getDao().getFunds(p.getId());
            p.setProcessInstanceId(processInstanceId);
            p.setInvoiceStatus(SalesContractConstant.CONTRACT_STATE_SH);
            funds.setProcessInstanceId(processInstanceId);
            invoices.add(p);
            fundbuds.add(funds);
            invoiceCount = invoiceCount.add(p.getInvoiceAmount());
            int days = (int) ((p.getInvoiceTime().getTime() - today.getTime()) / 86400000);
            if (days <= 7) {
                flat = 1;
            }
        }

        SalesContractStatusModel status = getDao().getSalesStatus(id);
        status.setInvoiceStatus(SalesContractConstant.CONTRACT_INVOICE_APPLYSTATE);

        Map<String, Object> variableMap = new HashMap<String, Object>();
        String procName = invoices.get(0).getSalesContractName() + SalesContractConstant.CONTRACT_INVOICE_PROCTITLE + "[￥" + invoiceCount + "]";
        if (flat == 1) {
            procName = "加急_" + procName;
        }
        variableMap.put("salesInvoicePlanList", invoices);
        try {
            getDao().saveOrUpdateAll(fundbuds);
            salesInvoiceDao.saveOrUpdateAll(invoices);
            salesContractStatusDao.saveOrUpdate(status);
            Long[] procInstId = processService.create(processInstanceId, procName, systemUser.getUserName(), SalesContractConstant.CONTRACT_INVOICE_PROCDEFKEY, 1, variableMap, null, null, null);
            log.info("创建工单成功：" + procInstId[0]);
            rs = "success";
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            rs = "flase";
        }
        return rs;
    }

    /**
     * @param saleId
     * @return
     */
    public String getInvoiceAmounttt(String saleId) {
        List<Map<String, Object>> listInvioce = getDao().getSalesInvioceTT(saleId);
        if (listInvioce.size() > 0) {
            return listInvioce.get(0).get("amount").toString();
        } else {
            return "0";
        }
    }

}
