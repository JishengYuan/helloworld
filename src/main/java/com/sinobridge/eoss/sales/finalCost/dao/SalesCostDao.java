/*
 * FileName: SalesCostDao.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.sales.finalCost.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.sinobridge.base.core.dao.imp.DefaultBaseDao;
import com.sinobridge.base.core.utils.PaginationSupport;
import com.sinobridge.eoss.sales.finalCost.model.SalesCostModel;

/**
 * <p>
 * Description: 
 * </p>
 *
 * @author vermouth
 * @version 1.0

 * <p>
 * History: 
 *
 * Date                     Author         Version     Description
 * ---------------------------------------------------------------------------------
 * 2015年9月7日 上午10:45:25          vermouth        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Repository
public class SalesCostDao extends DefaultBaseDao<SalesCostModel, Long> {

    /**得到资金成本数据：普通合同
     * @param timeType
     * @param time
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> getNewSaleCost(String timeType, String time) {
        StringBuffer sb = new StringBuffer();
        //老版本，备货用的一个表
        sb.append("SELECT tt.orderId2,tt.SaleContractId,tt.contactOrderAmount orderPay,sr.salesRevice  FROM (");
        sb.append("SELECT t1.orderId2,t1.SaleContractId,SUM(CAST((t1.subTotal/t1.OrderAmount)*totalOrderAmount AS DECIMAL(11,2))) contactOrderAmount FROM ( ");
        sb.append("SELECT ut.SaleContractId,SUM(ut.SubTotal) subTotal,ut.orderId2,bo.`OrderAmount`, ut.osId FROM ( ");
        sb.append("SELECT cp.`SaleContractId`,SUM(cp.`Quantity`*bp.`UnitPrice`) SubTotal ,bp.`OrderId` orderId2,CONCAT(bp.`OrderId`,cp.`SaleContractId`) osId ");
        sb.append("FROM (SELECT * FROM sales_contract_product p WHERE p.`RelateDeliveryProductId` IS NOT NULL)cp  ");
        sb.append("LEFT JOIN sales_contract_product sp ON sp.`id`=cp.RelateDeliveryProductId  ");
        sb.append("LEFT JOIN business_order_product bp ON bp.`salesContractProductId`=sp.`id` ");
        sb.append("WHERE bp.`SaleContractId` IS NOT NULL  GROUP BY osId UNION  ");
        sb.append("SELECT bp.`SaleContractId`,SUM(bp.`SubTotal`) SubTotal ,bp.`OrderId` orderId2,CONCAT(bp.`OrderId`,bp.`SaleContractId`) osId  ");
        sb.append("FROM business_order_product bp  GROUP BY osId) ut LEFT JOIN business_order bo ON ut.orderId2=bo.`id` GROUP BY ut.osId) t1  ");
        sb.append("LEFT JOIN (SELECT p.`OrderId`,SUM(p.`Amount`) totalOrderAmount FROM `business_payment_plan` p  ");
        sb.append("LEFT JOIN `business_pay_apply` py ON p.`PayOrderId` = py.`id` WHERE py.`RealPayDate` <= '" + timeType + "' GROUP BY p.`OrderId` ");
        sb.append(") t2 ON t1.orderId2 = t2.orderId  LEFT JOIN `sales_contract` c ON t1.SaleContractId = c.`id`   ");
        sb.append("WHERE t2.orderId IS NOT NULL AND c.`SalesStartDate`>= '2013-01-01' AND  c.`SalesStartDate`<='" + time + "'  ");
        sb.append("AND c.`ContractType`!='9000' GROUP BY c.`id` )tt  LEFT JOIN (SELECT tm.SalesId,SUM(tm.Amount) salesRevice FROM (   ");
        sb.append("SELECT sf.`SalesId`,f.`Amount`,f.`CreateTime` FROM `business_feeincom` f  ");
        sb.append("LEFT JOIN `business_sales_feeincome` sf ON sf.`FeeIncomeId` = f.`id`  ");
        sb.append("WHERE f.`ReceiveTime` >= '2013-01-01' AND f.`ReceiveTime`<='" + time + "' AND sf.`SalesId` IS NOT NULL   ");
        sb.append(") tm GROUP BY tm.SalesId ) sr ON tt.SaleContractId=sr.SalesId  GROUP BY tt.SaleContractId    ");

        //修改后的，备货另算的表
        /*  sb.append("SELECT tt.orderId2,tt.SaleContractId,tt.contactOrderAmount orderPay,sr.salesRevice  FROM ( ");
          sb.append("SELECT t1.orderId2,t1.SaleContractId,SUM(CAST((t1.subTotal/t1.OrderAmount)*totalOrderAmount AS DECIMAL(11,2))) contactOrderAmount FROM ( ");
          sb.append("SELECT ut.SaleContractId,SUM(ut.SubTotal) subTotal,ut.orderId2,bo.`OrderAmount`, ut.osId FROM (");
          sb.append("SELECT bp.`SaleContractId`,pt.pSales,SUM(bp.`SubTotal`) SubTotal ,bp.`OrderId` orderId2,CONCAT(bp.`OrderId`,bp.`SaleContractId`) osId ");
          sb.append("FROM business_order_product bp LEFT JOIN (SELECT p.`SaleContractId` pSales,p.`RelateDeliveryProductId` FROM sales_contract_product p ");
          sb.append("LEFT JOIN sales_contract sa ON sa.`id`=p.`SaleContractId` WHERE p.`RelateDeliveryProductId` IS NOT NULL ");
          sb.append(")pt ON pt.pSales=bp.`SaleContractId` WHERE pt.RelateDeliveryProductId IS NULL GROUP BY bp.`SaleContractId`,osId) ut ");
          sb.append("LEFT JOIN business_order bo ON ut.orderId2=bo.`id` GROUP BY ut.osId) t1 ");
          sb.append("LEFT JOIN (SELECT p.`OrderId`,SUM(p.`Amount`) totalOrderAmount FROM `business_payment_plan` p ");
          sb.append("LEFT JOIN `business_pay_apply` py ON p.`PayOrderId` = py.`id` WHERE py.`RealPayDate` <= '" + timeType + "' GROUP BY p.`OrderId` ");
          sb.append(") t2 ON t1.orderId2 = t2.orderId LEFT JOIN `sales_contract` c ON t1.SaleContractId = c.`id` ");
          sb.append("WHERE t2.orderId IS NOT NULL AND c.`SalesStartDate`>= '2013-01-01' AND  c.`SalesStartDate`<='" + time + "' ");
          sb.append("AND c.`ContractType`!='9000' GROUP BY c.`id`)tt LEFT JOIN (SELECT tm.SalesId,SUM(tm.Amount) salesRevice FROM ( ");
          sb.append("SELECT sf.`SalesId`,f.`Amount`,f.`CreateTime` FROM `business_feeincom` f ");
          sb.append("LEFT JOIN `business_sales_feeincome` sf ON sf.`FeeIncomeId` = f.`id` ");
          sb.append("WHERE f.`ReceiveTime` >= '2013-01-01' AND f.`ReceiveTime`<='" + time + "' AND sf.`SalesId` IS NOT NULL  ");
          sb.append(") tm GROUP BY tm.SalesId ) sr ON tt.SaleContractId=sr.SalesId GROUP BY tt.SaleContractId ");
          */

        List<Map<String, Object>> newSalesCost = this.createSQLQuery(sb.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        return newSalesCost;
    }

    /**得到资金成本数据：关联备货的合同
     * @param preMonday
     * @param timeStr
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> getSaleCostBh(String preMonday, String timeStr) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT un.SaleContractId,SUM(un.orderpay) orderPay,re.salesRevice FROM (  ");
        sb.append("SELECT so.OrderId,so.SaleContractId,SUM(CAST((so.subtotal/so.OrderAmount)*pay.pAmount AS DECIMAL(11,2))) orderpay FROM ( ");
        sb.append("SELECT op.`SaleContractId`,op.`OrderId`,CONCAT(op.`SaleContractId`,op.`OrderId`) soid, ");
        sb.append("SUM(op.`SubTotal`) subtotal,bo.OrderAmount FROM (SELECT p.`id` FROM sales_contract_product p WHERE ");
        sb.append("p.`SaleContractId` IN (SELECT k.`SalesContractId` FROM stockup_contractcost k WHERE k.`DoState`='1' ");
        sb.append("and  k.`SalesStartDate`<='" + timeStr + "' GROUP BY k.SalesContractId) AND p.`RelateDeliveryProductId` IS NULL ) sp ");
        sb.append("LEFT JOIN business_order_product op ON op.`salesContractProductId`=sp.id ");
        sb.append("LEFT JOIN business_order bo ON bo.`id`=op.`OrderId` WHERE op.`SaleContractId` IS NOT NULL GROUP BY soid)so ");
        sb.append("LEFT JOIN (SELECT SUM(p.`Amount`) pAmount,p.`OrderId` orderId2 FROM business_payment_plan p  ");
        sb.append("LEFT JOIN  business_pay_apply a ON p.`PayOrderId`=a.`id` WHERE a.`RealPayDate`<='" + preMonday + "' GROUP BY p.`OrderId` ");
        sb.append(")pay ON pay.orderId2 = so.OrderId GROUP BY so.SaleContractId UNION ALL ");
        sb.append("SELECT s.orderId,s.SalesContractId,SUM(CAST((s.totalPrice/s.OrderAmount)*pa.totalOrderAmount AS DECIMAL(11,2))) orderpay FROM (  ");
        sb.append("SELECT kc.`SalesContractId`,kp.`orderId`,SUM(kp.`TotalPrice`) totalPrice,  ");
        sb.append("CONCAT(kc.`SalesContractId`,kp.`orderId`) coid,bo.`OrderAmount` FROM `stockup_contractcost_product` kp  ");
        sb.append("LEFT JOIN `stockup_contractcost` kc ON kp.`SaleContractId`=kc.`id` LEFT JOIN business_order bo  ");
        sb.append("ON bo.`id`=kp.orderId WHERE kc.`DoState`='1' AND kc.`SalesStartDate`<='" + timeStr + "' GROUP BY coid) s  ");
        sb.append("LEFT JOIN (SELECT p.`OrderId` pOrd,SUM(p.`Amount`) totalOrderAmount FROM `business_payment_plan` p  ");
        sb.append("LEFT JOIN `business_pay_apply` py ON p.`PayOrderId` = py.`id` WHERE py.`RealPayDate` <= '" + preMonday + "'  ");
        sb.append("GROUP BY pOrd  ) pa ON pa.pOrd = s.orderId GROUP BY s.SalesContractId)un LEFT JOIN (  ");
        sb.append("SELECT tm.SalesId,SUM(tm.Amount) salesRevice FROM (SELECT sf.`SalesId`,f.`Amount`,f.`CreateTime`  ");
        sb.append("FROM `business_feeincom` f LEFT JOIN `business_sales_feeincome` sf ON sf.`FeeIncomeId` = f.`id`  ");
        sb.append("WHERE f.`ReceiveTime` >= '2013-01-01' AND f.`ReceiveTime`<='" + timeStr + "' AND sf.`SalesId` IS NOT NULL  ");
        sb.append(")tm GROUP BY tm.SalesId)re ON re.SalesId=un.SaleContractId GROUP BY un.SaleContractId  ");
        List<Map<String, Object>> newSalesCost = this.createSQLQuery(sb.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        return newSalesCost;
    }

    /**合同列表信息：普通合同
     * @param searchMap
     * @param pageNo
     * @param pageSize
     * @return
     */
    @SuppressWarnings("unchecked")
    public PaginationSupport findPageBySearchMap(HashMap<String, Object> searchMap, int pageNo, Integer pageSize) {
        Object time = searchMap.get("time");
        Object code = searchMap.get("salesCode");
        Object sortName = searchMap.get("sortName");
        Object sortorder = searchMap.get("sortorder");
        Object timeEnd = searchMap.get("timeEnd");
        Object salesName = searchMap.get("salesName");
        Object salesAmount = searchMap.get("salesAmount");
        Object salesCreator = searchMap.get("salesCreator");

        StringBuffer sb = new StringBuffer();
        sb.append("select * from (");
        sb.append("SELECT c.`ContractCode`,c.`CreatorName`,s.`orderPay`,s.`salesReceive`,s.`cost`,s.`dateInt`,c.`ContractAmount`,s.CreateTime,c.SalesStartDate,c.id,c.`ContractName` ");
        sb.append("FROM `sales_cost` s,`sales_contract` c ");
        sb.append("WHERE s.`salesContractId`=c.`id` ");
        if (code != null) {
            sb.append(" AND c.`ContractCode` like '%" + code + "%'");
        }
        if (salesName != null) {
            sb.append(" AND c.`ContractName` like '%" + salesName + "%'");
        }
        if (salesAmount != null) {
            sb.append(" AND c.`ContractAmount`=" + salesAmount);
        }
        if (salesCreator != null) {
            sb.append(" AND c.`Creator`=" + salesCreator);
        }
        if (time != null) {
            String[] timestr = time.toString().split("-");
            sb.append(" AND s.`dateInt`>='" + timestr[0] + timestr[1] + "01'");
        }
        if (timeEnd != null) {
            String[] timeEndstr = timeEnd.toString().split("-");
            sb.append(" AND s.`dateInt`<='" + timeEndstr[0] + timeEndstr[1] + "30'");
        }

        //        if (time != null) {
        //            String[] timestr = time.toString().split("-");
        //            sb.append("AND s.`dateInt` LIKE '" + timestr[0] + timestr[1] + "%' ");
        //        }
        sb.append(")t LEFT JOIN (");
        sb.append("SELECT p.`SaleContractId`,p.`RelateDeliveryProductId` FROM sales_contract_product p LEFT JOIN sales_contract sa ON sa.`id`=p.`SaleContractId` ");
        sb.append("WHERE p.`RelateDeliveryProductId` IS NOT NULL )pt ON pt.SaleContractId = t.id ");
        sb.append("WHERE pt.RelateDeliveryProductId IS NULL  group by t.id");
        if (sortName != null) {
            sb.append(" order by t." + sortName + " " + sortorder + " ");
        }
        int totals = getTotalCount(sb.toString());
        Query query = createSQLQuery(sb.toString());
        List<Object> items = query.setFirstResult(pageNo).setMaxResults(pageSize).list();
        PaginationSupport ps = new PaginationSupport(items, totals, pageSize, pageNo);

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        List<Object> rows = (List<Object>) ps.getItems();
        for (int i = 0; i < rows.size(); i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            Object[] entity = (Object[]) rows.get(i);
            map.put("ContractCode", entity[0]);
            map.put("CreatorName", entity[1]);
            map.put("orderPay", entity[2]);
            map.put("salesReceive", entity[3]);
            map.put("cost", entity[4]);
            map.put("dateInt", entity[5]);
            map.put("ContractAmount", entity[6]);
            map.put("CreateTime", entity[7]);
            map.put("SalesStartDate", entity[8]);
            map.put("id", entity[9]);
            map.put("ContractName", entity[10]);
            list.add(map);
        }
        ps.setItems(list);
        return ps;
    }

    /**合同列表信息：关联备货的合同
     * @param searchMap
     * @param pageNo
     * @param pageSize
     * @return
     */
    @SuppressWarnings("unchecked")
    public PaginationSupport findSpecialContract(HashMap<String, Object> searchMap, int pageNo, Integer pageSize) {
        Object time = searchMap.get("time");
        Object code = searchMap.get("salesCode");
        Object sortName = searchMap.get("sortName");
        Object sortorder = searchMap.get("sortorder");
        Object timeEnd = searchMap.get("timeEnd");
        Object salesName = searchMap.get("salesName");
        Object salesAmount = searchMap.get("salesAmount");
        Object salesCreator = searchMap.get("salesCreator");
        StringBuffer sb = new StringBuffer();
        sb.append("select * from (");
        sb.append("SELECT c.`ContractCode`,c.`CreatorName`,s.`orderPay`,s.`salesReceive`,s.`cost`,s.`dateInt`,c.`ContractAmount`,s.CreateTime,c.SalesStartDate,c.id,c.ContractName ");
        sb.append("FROM `sales_cost` s,`sales_contract` c,sales_contract_product p  ");
        sb.append("WHERE s.`salesContractId`=c.`id` AND p.`SaleContractId`=c.`id` AND p.`RelateDeliveryProductId` IS NOT NULL ");
        if (code != null) {
            sb.append("AND c.`ContractCode` like '%" + code + "%' ");
        }
        if (salesName != null) {
            sb.append(" AND c.`ContractName` like '%" + salesName + "%'");
        }
        if (salesAmount != null) {
            sb.append(" AND c.`ContractAmount`=" + salesAmount);
        }
        if (salesCreator != null) {
            sb.append(" AND c.`Creator`=" + salesCreator);
        }
        if (time != null) {
            String[] timestr = time.toString().split("-");
            sb.append(" AND s.`dateInt`>='" + timestr[0] + timestr[1] + "01'");
        }
        if (timeEnd != null) {
            String[] timeEndstr = timeEnd.toString().split("-");
            sb.append(" AND s.`dateInt`<='" + timeEndstr[0] + timeEndstr[1] + "30'");
        }

        //        if (time != null) {
        //            String[] timestr = time.toString().split("-");
        //            sb.append("AND s.`dateInt` LIKE '" + timestr[0] + timestr[1] + "%' ");
        //        }
        if (sortName != null) {
            sb.append(") t group by t.id order by t." + sortName + " " + sortorder + " ");
        }
        int totals = getTotalCount(sb.toString());
        Query query = createSQLQuery(sb.toString());
        List<Object> items = query.setFirstResult(pageNo).setMaxResults(pageSize).list();
        PaginationSupport ps = new PaginationSupport(items, totals, pageSize, pageNo);

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        List<Object> rows = (List<Object>) ps.getItems();
        for (int i = 0; i < rows.size(); i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            Object[] entity = (Object[]) rows.get(i);
            map.put("ContractCode", entity[0]);
            map.put("CreatorName", entity[1]);
            map.put("orderPay", entity[2]);
            map.put("salesReceive", entity[3]);
            map.put("cost", entity[4]);
            map.put("dateInt", entity[5]);
            map.put("ContractAmount", entity[6]);
            map.put("CreateTime", entity[7]);
            map.put("SalesStartDate", entity[8]);
            map.put("id", entity[9]);
            map.put("ContractName", entity[10]);
            list.add(map);
        }
        ps.setItems(list);
        return ps;
    }

    /**
     * <code>getTotalCount</code>
     * 得到总条数
     * @param sql
     * @param params
     * @return
     * @since   2015年9月8日    wangya
     */
    @SuppressWarnings("rawtypes")
    public Integer getTotalCount(String sql) {
        String countQueryString = " select COUNT(*) from (" + sql + ") t";
        List countlist = createSQLQuery(countQueryString).list();
        return Integer.parseInt(countlist.get(0).toString());
    }

    /**详情：查询合同
     * @param code
     * @param time 
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> getFindSales(String salesId, String time) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT  s.id,s.`ContractCode`,s.`ContractName`,s.`ContractAmount`,s.`CreatorName`,c.`orderPay`,c.`cost`,c.`salesReceive` FROM `sales_contract` s,`sales_cost` c WHERE s.`id`=c.`salesContractId` AND s.id='" + salesId + "' AND c.createTime='" + time + "' ");
        List<Map<String, Object>> sales = this.createSQLQuery(sb.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        return sales;
    }

    /**详情：查回款
     * @param id
     * @param time 
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> getFindRevice(Long id, String time) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT * FROM `business_sales_feeincome` f ,business_feeincom ft WHERE f.`FeeIncomeId`=ft.`id` and f.`SalesId`='" + id + "' AND ft.`ReceiveTime`<='" + time + "'");
        List<Map<String, Object>> sales = this.createSQLQuery(sb.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        return sales;
    }

    /**详情：查订单
     * @param id
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> findOrder(Long id, String time) {
        StringBuffer sb = new StringBuffer();
        /*sb.append("SELECT o.`OrderCode`,o.`OrderAmount`,o.`PayAmount`,o.id ");
        sb.append("FROM business_order o,sales_contract c,order_contract_map m WHERE o.`id`=m.`business_order` ");
        sb.append("AND m.`sales_contract`='"+id+"' AND o.`OrderStatus`='TGSP'");*/
        sb.append("SELECT t.*,SUM(p.`Amount`) pay FROM (");
        sb.append("SELECT o.`id`,o.`OrderCode`,o.`OrderAmount`,SUM(p.`SubTotal`)subTatul FROM business_order o,order_contract_map m,business_order_product p ");
        sb.append("WHERE o.id=m.`business_order` AND m.`sales_contract`='" + id + "' AND o.`OrderStatus`='TGSP' ");
        sb.append("AND p.`OrderId`=m.`business_order` GROUP BY m.`business_order` )t, `business_payment_plan` p,`business_pay_apply` pa ");
        sb.append("WHERE p.`OrderId`=t.`id` AND p.`PayOrderId`=pa.`id` AND pa.`RealPayDate`<='" + time + "' GROUP BY t.id ");
        sb.append("UNION  ");
        sb.append("SELECT cp.*, CAST(SUM(p.`Amount`)*cp.subTatul/cp.OrderAmount AS DECIMAL(11,2)) pay FROM (");
        sb.append("SELECT o.`id`,o.`OrderCode`,o.`OrderAmount`,SUM(bp.`UnitPrice`*p.`Quantity`)subTatul FROM sales_contract_product p, `business_order_product` bp, business_order o ");
        sb.append("WHERE p.`SaleContractId`='" + id + "' AND bp.`salesContractProductId`=p.`RelateDeliveryProductId` ");
        sb.append("AND o.`id`=bp.`OrderId` AND p.`RelateDeliveryProductId` IS NOT NULL  GROUP BY o.`id`)cp  ");
        sb.append("LEFT JOIN `business_payment_plan` p ON p.`OrderId`=cp.id LEFT JOIN `business_pay_apply` pa ON pa.`id`=p.`PayOrderId` ");
        sb.append("WHERE pa.`RealPayDate`<='" + time + "' GROUP BY cp.id ");
        List<Map<String, Object>> order = this.createSQLQuery(sb.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        return order;
    }

    /**详情：查订单相关的合同
     * @param orderId
     * @param time 
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> getFindOrderSale(Object orderId, String time) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT c.id,c.`ContractCode`,c.`ContractName`,c.`ContractAmount`,c.`CreatorName`,t.orderContract salesProAmount ");
        sb.append("FROM(SELECT p.`OrderId`,py.`RealPayDate`,m.`sales_contract`, (");
        sb.append("SELECT SUM(op.`SubTotal`) FROM `business_order_product` op ");
        sb.append("WHERE op.`OrderId` = p.`OrderId` AND op.`SaleContractId` = m.`sales_contract`) AS orderContract ");
        sb.append("FROM `business_payment_plan` p  RIGHT JOIN `order_contract_map` m ON m.`business_order` = p.`OrderId` ");
        sb.append("LEFT JOIN `business_pay_apply` py ON p.`PayOrderId` = py.`id`  WHERE py.`RealPayDate` <= '" + time + "' ) t  ");
        sb.append("LEFT JOIN `sales_contract` c ON t.`sales_contract` = c.`id` ");
        sb.append("WHERE t.OrderId='" + orderId + "' GROUP BY  c.`id` ");
        List<Map<String, Object>> order = this.createSQLQuery(sb.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        return order;
    }

    /**判断当月是否计算过成本
     * @param time
     * @return
     */
    @SuppressWarnings("unchecked")
    public int findTime(String time) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT * FROM `sales_cost` s WHERE s.`CreateTime` LIKE '" + time + "%'");
        List<Map<String, Object>> order = this.createSQLQuery(sb.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        int flat = 0;
        if (order.size() > 0) {
            flat = 1;
        }
        return flat;
    }

    /**修改：查合同成本
     * @param salesId
     * @param time
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> findSalesCost(String salesId, String time) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT s.id,s.createTime,s.orderPay,s.cost,s.salesReceive,c.`contractName`,c.`contractCode`,c.`contractAmount`,c.creatorName FROM `sales_cost` s ,sales_contract c  ");
        sb.append("WHERE s.`salesContractId`=c.`id`  AND s.`CreateTime`='" + time + "' AND s.`salesContractId`='" + salesId + "' ");
        List<Map<String, Object>> listMap = this.createSQLQuery(sb.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        return listMap;
    }

}
