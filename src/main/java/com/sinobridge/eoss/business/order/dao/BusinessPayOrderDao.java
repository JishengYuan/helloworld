package com.sinobridge.eoss.business.order.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.sinobridge.base.core.dao.imp.DefaultBaseDao;
import com.sinobridge.base.core.utils.PaginationSupport;
import com.sinobridge.eoss.business.order.OrderConstants;
import com.sinobridge.eoss.business.order.constant.BusinessOrderContant;
import com.sinobridge.eoss.business.order.model.BusinessPayOrderModel;

@Repository
public class BusinessPayOrderDao extends DefaultBaseDao<BusinessPayOrderModel, Long> {

    /**订单的计划付款金额
     * @param id
     * @return
     */
    @SuppressWarnings("unchecked")
    public BigDecimal findPlanAmount(Long id) {
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        Object[] param = new Object[1];
        param[0] = id;
        String sql = "select sum(Amount) amount from business_payment_plan where OrderId=?";
        Query query = this.createSQLQuery(sql, param);
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        mapList = query.list();
        Map<String, Object> map = mapList.get(0);
        String am = map.get("amount").toString();
        BigDecimal amount = new BigDecimal(am);
        return amount;
    }

    public void updateOrderPayAmount(Long id, BigDecimal amount, String planStatus) {
        Object[] param = new Object[3];
        String sql = "update BusinessOrderModel set payAmount=?, payStatus=? where id=?";
        param[0] = amount;
        param[1] = planStatus;
        param[2] = id;
        executeSql(sql, param);

    }

    /**查询公司
     * @return
     */
    public List<Map<String, Object>> findCompany() {
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        String sql = "select c.id id,c.CompanyName name from business_accountcompany c";
        Query query = this.createSQLQuery(sql);
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        mapList = query.list();
        return mapList;
    }

    /**
    * <code>getBankAccountByCmp</code>
    * 根据公司ID得到公司账户
    * @param companyId
    * @return
    * @since   2014年12月18日    wangya
    */
    public List<Map<String, Object>> findBankAccount(long companyId) {
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        String sql = "select c.id id,c.BankName name from business_bankaccount c where companyId=?";
        Object[] params = new Object[1];
        params[0] = companyId;
        Query query = this.createSQLQuery(sql, params);
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        mapList = query.list();
        return mapList;
    }

    /**付款银行信息
     * @param id
     * @return
     */
    public List<Map<String, Object>> getfindBankName(Long id) {
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        String sql = "select c.id, c.BankAccount,c.Balance,c.BankName from business_bankaccount c where id=?";
        Object[] params = new Object[1];
        params[0] = id;
        Query query = this.createSQLQuery(sql, params);
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        mapList = query.list();
        return mapList;
    }

    /**得到财务部员工
     * @return
     */
    public List<Map<String, Object>> getAccountPersion() {
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        Object[] param = new Object[1];
        param[0] = OrderConstants.ACCOUNTPERSION;
        String sql = "select staffId,staffName from sys_staff where staffId in (select staffId from sys_stafforg where orgId = ?)";
        Query query = this.createSQLQuery(sql, param);
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List<Map<String, Object>> objList = query.list();
        for (Map<String, Object> objMap : objList) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", objMap.get("staffId"));
            map.put("name", objMap.get("staffName"));
            mapList.add(map);
        }
        return mapList;
    }

    public void insertBankAccount(double bankAmount, Date time, double payAmonut, String remarks, String creator, String applyUser, String subjectName, String bankAccountId, long logId) {
        Object[] param = new Object[10];
        String sql = "insert into business_bankaccountitem (id,Amount,Balance,BankAccountId,CreateTime,Creator,InOutFlag,Remark,SubjectName,Treasurer) values (?,?,?,?,?,?,?,?,?,?)";
        param[0] = logId;
        param[1] = payAmonut;
        param[2] = bankAmount;
        param[3] = Long.parseLong(bankAccountId);
        param[4] = time;
        param[5] = creator;
        param[6] = 0;
        param[7] = remarks;
        param[8] = subjectName;
        param[9] = applyUser;
        //executeSql(sql, param);
        this.createSQLQuery(sql, param).executeUpdate();

    }

    public void updateBankAccountItem(BigDecimal bankAmount, String bankAccountId) {
        Object[] param = new Object[2];
        String sql = "update business_bankaccount set Balance=? where id=?";
        param[0] = bankAmount;
        param[1] = Long.parseLong(bankAccountId);
        // executeSql(sql, param);
        this.createSQLQuery(sql, param).executeUpdate();
    }

    /**
     * @param id
     */
    public void endPayStatus(Long id) {
        Object[] param = new Object[2];
        String sql = "update BusinessOrderModel set orderStatus=? where id=?";
        param[0] = BusinessOrderContant.NFF_CG;
        param[1] = id;
        this.executeSql(sql, param);
    }

    /**查询付款
     * @param procInstId
     * @return
     */
    public BusinessPayOrderModel findPayModel(String procInstId) {
        BusinessPayOrderModel pay = new BusinessPayOrderModel();
        String[] param = new String[1];
        param[0] = procInstId;
        String sql = "SELECT * FROM  business_pay_apply WHERE ProcessInstanceId=?";
        Query query = this.createSQLQuery(sql, param).addEntity(BusinessPayOrderModel.class);
        pay = (BusinessPayOrderModel) query.list().get(0);
        return pay;
    }

    /**总金额
     * @param searchMap
     * @return
     */
    public String findTotallAmount(Map<String, Object> searchMap) {
        int start = 0;
        Object[] values = new Object[searchMap.size()];
        Object startTime = searchMap.get("startTime");
        Object endTime = searchMap.get("endTime");
        Object supplierShortName = searchMap.get("supplierId");
        Object minAmount = searchMap.get("minAmount");
        Object maxAmount = searchMap.get("maxAmount");
        Object payName = searchMap.get("payName");
        String hql = "SELECT c.* FROM business_pay_apply c WHERE c.PlanStatus!='1' ";
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
        if (minAmount != null) {
            values[start++] = minAmount + "";
            hql += " and c.PayAmonut>=?";
        }
        if (maxAmount != null) {
            values[start++] = maxAmount + "";
            hql += " and c.PayAmonut<=?";
        }
        if (payName != null) {
            values[start++] = payName + "";
            hql += " and c.PayApplyName like ?";
        }
        Query query = null;
        if (searchMap.size() != 0) {
            query = this.createSQLQuery(hql, values).addEntity(BusinessPayOrderModel.class);
        } else {
            query = this.createSQLQuery(hql).addEntity(BusinessPayOrderModel.class);
        }
        List<BusinessPayOrderModel> objList = query.list();
        Iterator<BusinessPayOrderModel> bus = objList.iterator();
        BigDecimal amount = new BigDecimal(0.00);
        while (bus.hasNext()) {
            BusinessPayOrderModel b = bus.next();
            if (b.getPayAmonut() != null) {
                amount = amount.add(b.getPayAmonut());
            }
        }
        String totall = amount.toString();
        return totall;
    }

    /**付款列表
     * @param searchMap
     * @param startIndex
     * @param pageSize
     * @return
     */
    public PaginationSupport findPayPage(HashMap<String, Object> searchMap, int startIndex, Integer pageSize) {
        int start = 0;
        Object[] values = new Object[searchMap.size()];
        Object closeTime = searchMap.get(BusinessPayOrderModel.CLOSETIME);
        Object userId = searchMap.get(BusinessPayOrderModel.PAYAPPLYUSER);
        Object amount = searchMap.get(BusinessPayOrderModel.AMOUNT);
        Object status = searchMap.get(BusinessPayOrderModel.PLANSTATUS);
        Object supplierShortName = searchMap.get("supplierShortName");
        Object amountPart = searchMap.get("amountPart");
        String hql = "select c.id,c.PayAmonut,c.PayApplyName,c.PayApplyUser,c.PlanPayDate,c.PlanStatus,c.RealPayDate,c.CoursesType, art.NAME_,art.CREATE_TIME_,art.ASSIGNEE_, s.`ShortName`,c.currency from business_pay_apply c LEFT JOIN bpm_process_inst bpi ON c.ProcessInstanceId=bpi.ID_ LEFT JOIN `bussiness_supplier` s ON s.`id`=c.`SupplierName` LEFT JOIN act_ru_task art ON bpi.HI_PROC_INST_ID= art.proc_inst_id_ where 0=0";
        if (closeTime != null) {
            values[start++] = closeTime;
            hql += " and c." + BusinessPayOrderModel.CLOSETIME + "=?";
        }
        if (userId != null) {
            values[start++] = userId + "";
            hql += " and c." + BusinessPayOrderModel.PAYAPPLYUSER + "=?";
        }
        if (amount != null) {
            BigDecimal amountBig = new BigDecimal(amount.toString());
            values[start++] = amountBig + "";
            hql += " and c." + BusinessPayOrderModel.AMOUNT + "=?";
        }
        if (amountPart != null) {
            BigDecimal amounts = new BigDecimal(amountPart.toString());
            values[start++] = amounts + "";
            hql += " and c.id IN (SELECT p.`PayOrderId` FROM `business_payment_plan` p WHERE c.`id`=p.`PayOrderId` AND p.`Amount`=?) ";
        }
        if (supplierShortName != null) {
            values[start++] = supplierShortName + "";
            hql += " and c.SupplierName=? ";
        }
        if (status != null) {
            values[start++] = status + "";
            hql += " and c." + BusinessPayOrderModel.PLANSTATUS + "=?";
        }

        hql += " group by c.id order By c.id DESC";

        Query query = createSQLQuery(hql, values);
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List<Object> items = query.setFirstResult(startIndex).setMaxResults(pageSize).list();
        PaginationSupport ps = new PaginationSupport(items, getTotalCount(hql, values), pageSize, startIndex);

        return ps;
    }

    /**
     * <code>getTotalCount</code>
     * 得到总条数
     * @param sql
     * @param params
     * @return
     * @since   2014年11月24日    guokemenng
     */
    @SuppressWarnings("rawtypes")
    public Integer getTotalCount(String sql, Object[] params) {
        String countQueryString = " select COUNT(*) from (" + sql + ") t";
        List countlist = createSQLQuery(countQueryString, params).list();
        return Integer.parseInt(countlist.get(0).toString());
    }

    /**
     * @param searchMap
     * @return
     */
    public BigDecimal totallA(HashMap<String, Object> searchMap) {
        int start = 0;
        Object[] values = new Object[searchMap.size()];
        Object closeTime = searchMap.get(BusinessPayOrderModel.CLOSETIME);
        Object userId = searchMap.get(BusinessPayOrderModel.PAYAPPLYUSER);
        Object amount = searchMap.get(BusinessPayOrderModel.AMOUNT);
        Object status = searchMap.get(BusinessPayOrderModel.PLANSTATUS);
        Object supplierShortName = searchMap.get("supplierShortName");
        Object amountPart = searchMap.get("amountPart");
        String hql = "select c.id,c.PayAmonut,c.PayApplyName,c.PayApplyUser,c.PlanPayDate,c.PlanStatus,c.RealPayDate,art.NAME_,art.CREATE_TIME_,art.ASSIGNEE_ from business_pay_apply c LEFT JOIN bpm_process_inst bpi ON c.ProcessInstanceId=bpi.ID_  LEFT JOIN act_ru_task art ON bpi.HI_PROC_INST_ID= art.proc_inst_id_ where 0=0";
        if (closeTime != null) {
            values[start++] = closeTime;
            hql += " and c." + BusinessPayOrderModel.CLOSETIME + "=?";
        }
        if (userId != null) {
            values[start++] = userId + "";
            hql += " and c." + BusinessPayOrderModel.PAYAPPLYUSER + "=?";
        }
        if (supplierShortName != null) {
            values[start++] = supplierShortName + "";
            hql += " and c.SupplierName=? ";
        }
        if (amount != null) {
            BigDecimal amountBig = new BigDecimal(amount.toString());
            values[start++] = amountBig + "";
            hql += " and c." + BusinessPayOrderModel.AMOUNT + "=?";
        }
        if (amountPart != null) {
            BigDecimal amounts = new BigDecimal(amountPart.toString());
            values[start++] = amounts + "";
            hql += " and c.id IN (SELECT p.`PayOrderId` FROM `business_payment_plan` p WHERE c.`id`=p.`PayOrderId` AND p.`Amount`=?) ";
        }
        if (status != null) {
            values[start++] = status + "";
            hql += " and c." + BusinessPayOrderModel.PLANSTATUS + "=?";
        }
        hql += " group by c.id order By c.id DESC";

        Query query = createSQLQuery(hql, values);
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List<Map<String, Object>> listMap = query.list();
        BigDecimal amountT = new BigDecimal(0.00);
        for (Map<String, Object> objMap : listMap) {
            BigDecimal a = new BigDecimal(objMap.get("PayAmonut").toString());
            amountT = amountT.add(a);
        }
        return amountT;
    }

    /**公司信息
     * @param parseLong
     * @return
     */
    public String findCompany(long parseLong) {
        Object[] param = new Object[1];
        param[0] = parseLong;
        String sql = "select c.CompanyName from business_accountcompany c where c.id=?";
        Query query = this.createSQLQuery(sql, param);
        String name = query.uniqueResult().toString();
        return name;
    }

    /**通过付款计划ID查询订单ID，付款金额
     * @param id
     * @return
     */
    public List<Map<String, Object>> findOrderId(Long id) {
        Object[] param = new Object[1];
        param[0] = id;
        String sql = "SELECT o.id,pl.`Amount` FROM `business_payment_plan` pl,`business_pay_apply` p,`business_order` o WHERE p.`id`=? AND p.id=pl.`PayOrderId` AND pl.`OrderId`=o.id";
        Query query = createSQLQuery(sql, param);
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List<Map<String, Object>> listMap = query.list();
        return listMap;
    }

    /**通过付款计划ID查订单编号
     * @param id
     * @return
     */
    public List<Map<String, Object>> findOrderCode(Long id) {
        Object[] param = new Object[1];
        param[0] = id;
        String sql = "SELECT p.orderCode FROM business_payment_plan p,`business_pay_apply` a WHERE a.`id`=? AND a.`id`=p.`PayOrderId`";
        Query query = createSQLQuery(sql, param);
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List<Map<String, Object>> listMap = query.list();
        return listMap;
    }

    /**更新工单标题
     * @param processInstanceId
     * @param payAmonut
     * @param title
     */
    public void updateTitleAmount(Long processInstanceId, String title) {
        String sql = "UPDATE bpm_process_inst t SET t.TITLE_='" + title + "' WHERE t.ID_='" + processInstanceId + "' ";
        createSQLQuery(sql).executeUpdate();
    }

    /**查询未付金额
     * @param orderId
     * @return
     */
    @SuppressWarnings("unchecked")
    public BigDecimal getpaymentAmount(String orderId) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT SUM(p.`Amount`) amount,SUM(p.`RealPayAmount`) realAmount FROM business_order o,`business_payment_plan` p,`business_pay_apply` a ");
        sb.append("WHERE o.`id`=p.`OrderId` AND p.`PayOrderId`=a.`id` AND o.`id`='" + orderId + "' AND a.`PlanStatus`='10' GROUP BY o.`id` ");
        List<Map<String, Object>> listmap = createSQLQuery(sb.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        BigDecimal amount = new BigDecimal(0.00);
        if (listmap.size() > 0) {
            if (listmap.get(0).get("realAmount") != null) {
                amount = new BigDecimal(listmap.get(0).get("realAmount").toString());
            } else {
                amount = new BigDecimal(listmap.get(0).get("amount").toString());
            }
        }
        return amount;
    }

    /**
     * @param id
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<BusinessPayOrderModel> getPayOrderList(Long orderId) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT a.* FROM `business_payment_plan` p,`business_pay_apply` a ");
        sb.append("WHERE p.`PayOrderId`=a.`id` AND p.`OrderId`='" + orderId + "' ");
        List<BusinessPayOrderModel> payOrders = createSQLQuery(sb.toString()).addEntity(BusinessPayOrderModel.class).list();
        return payOrders;
    }

    /**
     * 根据id查询公司信息
     * @param id
     * @return
     */
    public List<Map<String, Object>> findCompanyInfoById(long id) {
        String sql = "select address,bankaccount,bankname,companyname,companyCode from business_accountcompany where id=?";
        Query query = this.createSQLQuery(sql);

        //需要设置一个转换器，这样才可以有像map那样的结构
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List<Map<String, Object>> list = query.setParameter(0, id).list();
        return list;
    }

}
