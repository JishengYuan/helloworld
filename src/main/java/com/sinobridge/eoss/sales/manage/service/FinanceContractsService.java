/*
 * FileName: FinanceContractsService.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.sales.manage.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinobridge.base.core.service.DefaultBaseService;
import com.sinobridge.base.core.utils.PaginationSupport;
import com.sinobridge.eoss.business.order.model.BusinessOrderModel;
import com.sinobridge.eoss.sales.manage.dao.FinanceContractsDao;
import com.sinobridge.eoss.sales.manage.model.FinanceContractsModel;
import com.sinobridge.systemmanage.util.StringUtil;

/**
 * <p>
 * Description: 
 * </p>
 *
 * @author guo_kemeng
 * @version 1.0

 * <p>
 * History: 
 *
 * Date                     Author         Version     Description
 * ---------------------------------------------------------------------------------
 * 2015年1月16日 上午10:47:57          guo_kemeng        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Service
@Transactional
public class FinanceContractsService extends DefaultBaseService<FinanceContractsModel, FinanceContractsDao> {

    /**
     * <code>getSalesContractList</code>
     * 合同列表
     * @param request
     * @param start
     * @param pageSize
     * @return
     * @since   2014年10月29日    guokemenng
     */
    public PaginationSupport getSalesContractList(Integer start, Integer pageSize) {
        String sql = "select s.* from sales_contract s left join sales_contract_status st on s.id = st.SalecontractId where st.ContractSnapShootId is null and s.ContractState = 'TGSH' and s.isold is null order by s.id";
        Object[] params = new Object[1];
        Query query = getDao().createSQLQuery(sql);
        query.setFirstResult(start);
        query.setMaxResults(pageSize);
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        PaginationSupport paginationSupport = new PaginationSupport(query.list(), getSalesContractListCount(params));
        return paginationSupport;
    }

    /**
     * <code>getSalesContractListCount</code>
     * 合同总数量
     * @param params
     * @return
     * @since   2014年10月9日    guokemenng
     */
    public Integer getSalesContractListCount(Object[] params) {
        String sql = "select count(*) from sales_contract s left join sales_contract_status st on s.id = st.SalecontractId where s.ContractState = 'TGSH' and s.isold is null";
        @SuppressWarnings("unchecked")
        List<Object> objList = getDao().createSQLQuery(sql).list();
        if (objList.size() > 0) {
            return Integer.valueOf(objList.get(0).toString()).intValue();
        } else {
            return 0;
        }
    }

    /**
     * <code>getSalesContractByCode</code>
     * 根据Code 查合同Name
     * @param code
     * @return
     * @since   2014年12月25日    guokemenng
     */
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

    /**
    * <code>deletes</code>
    * 删除
    * @param ids
    * @since   2015年1月16日    guokemenng
    */
    public void deletes(String[] ids) {
        for (String id : ids) {
            long trueId = Long.parseLong(id);
            this.delete(trueId);
        }
    }

    /**
     * @param searchMap
     * @param i
     * @param pageSize
     * @return
     */
    public PaginationSupport findOrderContract(Map<String, Object> searchMap, int pageL, Integer pageSize) {
        Object orderCode = searchMap.get(BusinessOrderModel.ORDERCODE);
        Object supplierInfo = searchMap.get("supplierInfo");
        Object startTime = searchMap.get("startTime");
        Object endTime = searchMap.get("endTime");
        Object orderAmount = searchMap.get(BusinessOrderModel.ORDERAMOUNT);
        Object orderStatus = searchMap.get("orderStatus");
        String sql = "SELECT q.*,f.`InvoiceAmount`,f.`UninvoiceAmount`,f.`ReceiveAmount`,f.`UnreceiveAmount` FROM (SELECT b.id,b.`OrderCode`,b.`OrderAmount`,b.`OrderName`,B.`OrderType`,i.`ShortName` customer,s.`ShortName` supplier FROM business_order b ,`sales_contract` c ,`order_contract_map` m ,`customer_info` i,`bussiness_supplier` s,`business_payment_plan` p WHERE b.id=m.business_order AND c.id=m.sales_contract AND c.CustomerId= i.id AND b.`SupplierInfo`=s.id AND b.`OrderStatus`='TGSP' AND b.id=p.`OrderId` ";
        if (orderCode != null) {
            sql += " and b.OrderCode like '" + orderCode + "' ";
        }
        if (supplierInfo != null) {
            sql += " and b.SupplierInfo='" + supplierInfo + "' ";
        }
        if (startTime != null) {
            sql += " and p.PlanTime>='" + startTime + "' ";
        }
        if (endTime != null) {
            sql += " and p.PlanTime<'" + endTime + "' ";
        }
        if (orderAmount != null) {
            String amount = orderAmount.toString();
            if (amount.indexOf(".") != -1) {
                sql += " and b.OrderAmount='" + amount + "' ";
            } else {
                sql += " and b.OrderAmount='" + amount + ".%' ";
            }
        }
        sql += "GROUP BY b.id,b.`OrderCode` ORDER BY b.id DESC) q LEFT JOIN sales_finance_contract f ON f.`OrderId`=Q.id where 0=0 ";
        if (orderStatus != null) {
            if (orderStatus.equals("finish")) {
                sql += " and f.`UninvoiceAmount`='0' and f.`InvoiceAmount` is not null and f.`UnreceiveAmount`='0' and f.`ReceiveAmount` is not null";
            } else {
                sql += "and (f.`UninvoiceAmount`IS NULL OR f.`UninvoiceAmount`!='0') AND (f.`ReceiveAmount`IS NULL OR f.`UnreceiveAmount`!='0')";
            }
        }
        Query query = getDao().createSQLQuery(sql);
        List<Object> items = query.setFirstResult(pageL).setMaxResults(pageSize).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        PaginationSupport ps = new PaginationSupport(items, getTotalCount(sql), pageSize, pageL);
        return ps;
    }

    @SuppressWarnings("rawtypes")
    public Integer getTotalCount(String sql) {
        String countQueryString = " select COUNT(*) from (" + sql + ") t";
        List countlist = getDao().createSQLQuery(countQueryString).list();
        return Integer.parseInt(countlist.get(0).toString());
    }

    /**
     * @param parseLong
     * @return
     */
    public List<FinanceContractsModel> findfinanceModel(long id) {
        List<FinanceContractsModel> fina = getDao().findfinanceModel(id);
        return fina;
    }

}
