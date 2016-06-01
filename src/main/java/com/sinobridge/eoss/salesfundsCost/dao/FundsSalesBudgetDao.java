package com.sinobridge.eoss.salesfundsCost.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.sinobridge.base.core.dao.imp.DefaultBaseDao;
import com.sinobridge.base.core.utils.PaginationSupport;
import com.sinobridge.eoss.sales.contract.model.SalesContractModel;
import com.sinobridge.eoss.sales.contract.model.SalesContractStatusModel;
import com.sinobridge.eoss.salesfundsCost.model.SalesBudgetFunds;
import com.sinobridge.eoss.salesfundsCost.model.SalesBudgetLog;

@Repository
public class FundsSalesBudgetDao extends DefaultBaseDao<SalesBudgetFunds, Long> {

    /**
     * <code>getModelPage</code>
     * 根据条件查询 返回page对象
     * @param searchMap
     * @param startIndex
     * @param pageSize
     * @return
     * @since   2016年2月27日    wangya
     */
    @SuppressWarnings("unchecked")
    public PaginationSupport findAllFundsSalesBudget(String sql, Integer startIndex, Integer pageSize) {

        StringBuffer sb = new StringBuffer();
        sb.append("SELECT s.`contractCode`,s.`contractAmount`,i.`shortName`,s.`id`,s.contractName FROM sales_contract s ");
        sb.append("LEFT JOIN customer_info i ON i.`id`=s.`CustomerId` WHERE s.`ContractState`='TGSH' AND s.`CreateTime`>'2014-01-01' ");
        sb.append("AND s.`ContractType`!='9000' " + sql + " ");
        sb.append("ORDER BY s.id DESC ");
        Query query = null;
        query = createSQLQuery(sb.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).setFirstResult(startIndex).setMaxResults(pageSize);
        //   query = createSQLQuery(hql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).setFirstResult(startIndex).setMaxResults(pageSize);
        // List<Map<String, Object>> list = query.list();
        PaginationSupport ps = new PaginationSupport(query.list(), totalCount(sb.toString()), startIndex);
        return ps;
    }

    /**
     * <code>totalCount</code>
     * 总条数
     * @param sql
     * @param params
     * @return
     * @since   2016年1月27日  zsd
     */
    public Integer totalCount(String sql) {
        //  if (values[0] != null && !"".equals(values[1])) {
        sql = "select count(*) from (" + sql + " ) as total";
        return Integer.valueOf(createSQLQuery(sql).list().get(0).toString()).intValue();
        /* } else {
             sql = "select count(*) from (" + sql + " ) as total";
             return Integer.valueOf(createSQLQuery(sql).list().get(0).toString()).intValue();
         }*/
    }

    //删除预算
    public boolean delBudget(String id) {
        String hql = "DELETE FROM `funds_salesbudget` WHERE id='" + id + "'";
        // createSQLQuery(hql); 
        SQLQuery query = this.getSession().createSQLQuery(hql);
        query.executeUpdate();
        return true;
    }

    @SuppressWarnings("unchecked")
    public List<SalesContractModel> findSalesContractByCreator(String creatorName) {
        Query query = null;
        String hql = "select * from sales_contract where 0=0 ";
        if (creatorName != null && !"".equals(creatorName)) {
            hql += "and creatorName='" + creatorName + "'";
        }
        query = createSQLQuery(hql).addEntity(SalesContractModel.class);
        //   query = createSQLQuery(hql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).setFirstResult(startIndex).setMaxResults(pageSize);
        List<SalesContractModel> list = query.list();//SalesContractModel
        // List<Map<String, Object>> ls = query.list();
        return list;
    }

    /**查询合同回款金额
     * @param string
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> getSalesReceive(String salesId) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT SUM(f.`Amount`) amount FROM business_sales_feeincome f ");
        sb.append("LEFT JOIN business_feeincom fi ON fi.`id`=f.`FeeIncomeId` ");
        sb.append("WHERE f.`SalesId`='" + salesId + "' AND fi.`State`='2' GROUP BY f.`SalesId`");
        List<Map<String, Object>> list = createSQLQuery(sb.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        return list;
    }

    /**查询合同发票金额
     * @param string
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> getSalesInvioce(String salesId) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT SUM(p.`InvoiceAmount`) amount FROM sales_invoice_plan p ");
        sb.append("WHERE p.`SalesContractId`='" + salesId + "' AND p.`InvoiceStatus`='TGSH' GROUP BY p.`SalesContractId` ");
        List<Map<String, Object>> list = createSQLQuery(sb.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        return list;
    }

    /**
     * @param userId
     * @param first
     * @param last
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> getRealRecive(String userId, String first, String last) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT SUM(t.amount) total FROM (SELECT SUM(f.`Amount`) amount,f.`SalesId` ");
        sb.append("FROM `business_sales_feeincome` f ,`business_feeincom` i WHERE f.`FeeIncomeId`=i.`id` AND i.`State`='2' ");
        if (first != null) {
            sb.append("AND i.`ReceiveTime`>'" + first + "' ");
        }
        sb.append(" AND i.`ReceiveTime`<='" + last + "' GROUP BY f.`SalesId` )t ");
        sb.append("LEFT JOIN sales_contract s ON s.id = t.SalesId WHERE s.`Creator`='" + userId + "' ");
        List<Map<String, Object>> list = createSQLQuery(sb.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        return list;
    }

    /**
     * @param userId
     * @param first
     * @param last
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> getBudRecive(String userId, String first, String last) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT  SUM(s.`budgetInvoice`) bugInvoice,SUM(s.`budgetReceive`) bugReceive FROM funds_salesBudget s ");
        sb.append("LEFT JOIN sales_contract c ON c.`id`=s.`contractId` ");
        sb.append("WHERE c.`Creator`='" + userId + "' ");
        if (first != null) {
            sb.append("AND s.`budgetDate`>'" + first + "' ");
        }
        sb.append("AND s.`budgetDate`<='" + last + "' ");
        List<Map<String, Object>> list = createSQLQuery(sb.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        return list;
    }

    /**
     * @param saleId
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> getSales(String saleId) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT s.`id`,s.`ContractName`,s.`ContractAmount`,i.`ShortName` FROM sales_contract s ");
        sb.append("LEFT JOIN `customer_info` i ON i.`id`=s.`CustomerId` WHERE s.id ='" + saleId + "' ");
        List<Map<String, Object>> list = createSQLQuery(sb.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        return list;
    }

    /**
     * @param saleId
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> getSalesInvoice(String saleId) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT p.`InvoiceAmount`,p.`InvoiceTime` FROM `sales_invoice_plan` p WHERE p.`SalesContractId`='" + saleId + "' ");
        List<Map<String, Object>> list = createSQLQuery(sb.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        return list;
    }

    /**
     * @param userId
     * @param first
     * @param last
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> getRealInvoice(String userId, String first, String last) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT SUM(p.`InvoiceAmount`) total FROM `sales_invoice_plan` p LEFT JOIN sales_contract s ON s.`id`=p.`SalesContractId` ");
        sb.append("WHERE p.`InvoiceStatus`='TGSH' AND s.`Creator`='" + userId + "' ");
        if (first != null) {
            sb.append("AND p.`InvoiceTime`>='" + first + "' ");
        }
        sb.append("AND p.`InvoiceTime`<'" + last + "' ");
        List<Map<String, Object>> list = createSQLQuery(sb.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        return list;
    }

    /**
     * @param hql
     * @param startIndex
     * @param pageSize
     * @return
     */
    public PaginationSupport findHisReceive(String hql, int startIndex, Integer pageSize) {
        Query query = null;
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT DATE_FORMAT(f.`budgetDate`,'%Y-%m-%d') budgetDate,DATE_FORMAT(f.`createDate`,'%Y-%m-%d') createDate, ");
        sb.append("f.`budgetReceive`,f.`contractName`,f.`id`,f.`contractId`,f.title FROM funds_salesBudget f LEFT JOIN sales_contract s ON s.`id`=f.`contractId` ");
        sb.append("WHERE f.`budgetReceive` IS NOT NULL " + hql + " ORDER by f.id desc");
        query = createSQLQuery(sb.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).setFirstResult(startIndex).setMaxResults(pageSize);
        PaginationSupport ps = new PaginationSupport(query.list(), totalCount(sb.toString()), startIndex);
        return ps;
    }

    /**
     * @param hql
     * @param startIndex
     * @param pageSize
     * @return
     */
    public PaginationSupport findHisInvoice(String hql, int startIndex, Integer pageSize) {
        Query query = null;
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT DATE_FORMAT(f.`budgetDate`,'%Y-%m-%d') budgetDate,DATE_FORMAT(f.`createDate`,'%Y-%m-%d') createDate, ");
        sb.append("f.`budgetInvoice`,f.`contractName`,f.`id`,f.`contractId`,f.title,art.NAME_,sa.`StaffName` ");
        sb.append("FROM funds_salesBudget f LEFT JOIN sales_contract s ON s.`id`=f.`contractId` ");
        sb.append("LEFT JOIN `sales_invoice_plan` i ON i.`SalesContractId`=s.`id` ");
        sb.append("LEFT JOIN bpm_process_inst bpi ON i.ProcessInstanceId=bpi.ID_  ");
        sb.append("LEFT JOIN act_ru_task art ON bpi.HI_PROC_INST_ID= art.proc_inst_id_ ");
        sb.append("LEFT JOIN `sys_staff` sa ON sa.`UserName`=art.ASSIGNEE_ ");
        sb.append("WHERE f.`budgetInvoice` IS NOT NULL " + hql + "  AND f.`processInstanceId` IS NULL  GROUP BY f.`id` ORDER by f.id desc");
        query = createSQLQuery(sb.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).setFirstResult(startIndex).setMaxResults(pageSize);
        PaginationSupport ps = new PaginationSupport(query.list(), totalCount(sb.toString()), startIndex);
        return ps;
    }

    /**
     * @param userId
     * @param startTime
     * @param endTime
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> getSalesName(String userId, String startTime, String endTime) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT s.`ContractName`,p.`PlanedReceiveDate`,s.id FROM `sales_contract_receive_plan` p ");
        sb.append("LEFT JOIN sales_contract s ON s.`id`=p.`SalesContractId` ");
        sb.append("WHERE p.`PlanedReceiveDate`>='" + startTime + "' AND p.`PlanedReceiveDate`<'" + endTime + "' ");
        sb.append("AND s.`Creator`='" + userId + "' GROUP BY s.`id`  ");
        List<Map<String, Object>> list = createSQLQuery(sb.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        return list;
    }

    /**
     * @param sql
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PaginationSupport findSalesTotalInfo(String sql, int pageNo, Integer pageSize) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT s.`id`,s.`ContractCode`,s.`ContractName` FROM sales_contract s ");
        sb.append("LEFT JOIN `customer_info` i ON i.`id`=s.`CustomerId` ");
        sb.append("WHERE s.`ContractState`='TGSH' and s.contractType!='9000' " + sql + " ");
        Query query = null;
        query = createSQLQuery(sb.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).setFirstResult(pageNo).setMaxResults(pageSize);
        PaginationSupport ps = new PaginationSupport(query.list(), totalCount(sb.toString()), pageNo);
        return ps;
    }

    /**
     * @param id
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<SalesBudgetLog> getSalesLogs(Long id) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT * FROM `funds_salesbudget_log` s WHERE s.`budgetFundsId`='" + id + "' ");
        List<SalesBudgetLog> logs = createSQLQuery(sb.toString()).addEntity(SalesBudgetLog.class).list();
        return logs;
    }

    /**
     * @param userId
     * @param last
     * @param furTime
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> getFurBudget(String userId, String last, String furTime) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT  SUM(s.`budgetInvoice`) bugInvoice,SUM(s.`budgetReceive`) bugReceive FROM funds_salesBudget s ");
        sb.append("LEFT JOIN sales_contract c ON c.`id`=s.`contractId` ");
        sb.append("WHERE c.`Creator`='" + userId + "' ");
        if (last != null) {
            sb.append("AND s.`budgetDate`>'" + last + "' ");
        }
        sb.append("AND s.`budgetDate`<='" + furTime + "' ");
        List<Map<String, Object>> list = createSQLQuery(sb.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        return list;
    }

    /**
     * @param id
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<SalesBudgetFunds> getInvoiceList(Long id) {
        StringBuffer sb = new StringBuffer();
        sb.append("select * from funds_salesBudget s where s.contractId='" + id + "' and s.budgetInvoice is not null ");
        List<SalesBudgetFunds> invoice = createSQLQuery(sb.toString()).addEntity(SalesBudgetFunds.class).list();
        return invoice;
    }

    /**
     * @param id
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<SalesBudgetFunds> getReceiveList(Long id) {
        StringBuffer sb = new StringBuffer();
        sb.append("select * from funds_salesBudget s where s.contractId='" + id + "' and s.budgetReceive is not null ");
        List<SalesBudgetFunds> receive = createSQLQuery(sb.toString()).addEntity(SalesBudgetFunds.class).list();
        return receive;
    }

    /**
     * @param processInstanceId
     * @return
     */
    @SuppressWarnings("unchecked")
    public SalesBudgetFunds findFunds(long processInstanceId) {
        StringBuffer sb = new StringBuffer();
        sb.append("select * from funds_salesBudget s where s.processInstanceId='" + processInstanceId + "' ");
        List<SalesBudgetFunds> receive = createSQLQuery(sb.toString()).addEntity(SalesBudgetFunds.class).list();
        if (receive.size() > 0) {
            return receive.get(0);
        } else {
            return null;
        }
    }

    /**查询所有发票预测记录
     * @param saleId
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<SalesBudgetFunds> getFundsInvoiceBySaleId(String saleId) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT * FROM funds_salesBudget s WHERE s.`contractId`='" + saleId + "' and s.`budgetInvoice` IS NOT NULL AND s.`processInstanceId` IS NULL");
        List<SalesBudgetFunds> invoice = createSQLQuery(sb.toString()).addEntity(SalesBudgetFunds.class).list();
        return invoice;
    }

    /**通过合同ID查询此合同所有发票预测日志信息
     * @param saleId
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<SalesBudgetLog> getInvoiecLogBySaleID(String saleId) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT * FROM `funds_salesbudget_log` s LEFT JOIN `funds_salesbudget` f ON f.`id`=s.`budgetFundsId` WHERE f.`contractId`='" + saleId + "' and f.`budgetInvoice` IS NOT NULL ");
        List<SalesBudgetLog> logs = createSQLQuery(sb.toString()).addEntity(SalesBudgetLog.class).list();
        return logs;
    }

    /**
     * @param id
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<SalesBudgetFunds> getfundOld(String id) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT * FROM funds_salesBudget p WHERE p.`contractId`='" + id + "' AND p.`budgetInvoice` IS NOT NULL and p.processInstanceId is null ");
        List<SalesBudgetFunds> logs = createSQLQuery(sb.toString()).addEntity(SalesBudgetFunds.class).list();
        return logs;
    }

    /**
     * @param id
     * @return
     */
    @SuppressWarnings("unchecked")
    public SalesContractStatusModel getSalesStatus(String id) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT * FROM sales_contract_status s WHERE s.`SalecontractId`='" + id + "' ");
        List<SalesContractStatusModel> logs = createSQLQuery(sb.toString()).addEntity(SalesContractStatusModel.class).list();
        if (logs.size() > 0) {
            return logs.get(0);
        } else {
            return null;
        }
    }

    /**
     * @param saleId
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> getSalesInvioceTT(String saleId) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT SUM(p.`InvoiceAmount`) amount FROM sales_invoice_plan p ");
        sb.append("WHERE p.`SalesContractId`='" + saleId + "' AND p.`InvoiceStatus`='CG' GROUP BY p.`SalesContractId` ");
        List<Map<String, Object>> list = createSQLQuery(sb.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        return list;
    }

    /**
     * @param id
     * @return
     */
    @SuppressWarnings("unchecked")
    public SalesBudgetFunds getFunds(Long id) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT * FROM funds_salesBudget s WHERE s.`salesInvoiceId`='" + id + "' ");
        List<SalesBudgetFunds> logs = createSQLQuery(sb.toString()).addEntity(SalesBudgetFunds.class).list();
        if (logs.size() > 0) {
            return logs.get(0);
        } else {
            return null;
        }
    }

    /**
     * @param salesContractModelId
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<SalesBudgetFunds> getFundsBySalesId(long salesContractModelId) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT * FROM funds_salesBudget p WHERE p.`contractId`='" + salesContractModelId + "' ");
        List<SalesBudgetFunds> logs = createSQLQuery(sb.toString()).addEntity(SalesBudgetFunds.class).list();
        return logs;
    }

}
