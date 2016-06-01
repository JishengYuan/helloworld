/*
 * FileName: StockUpContractCostProductDao.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.sales.stockupCost.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.sinobridge.base.core.dao.imp.DefaultBaseDao;
import com.sinobridge.eoss.sales.stockupCost.model.StockUpContractCostProductModel;
import com.sinobridge.systemmanage.util.StringUtil;

/**
 * <p>
 * Description: StockUpContractCostProductDao
 * </p>
 *
 * @author liyx
 * @version 1.0

 * <p>
 * History: 
 *
 * Date                     Author         Version     Description
 * ---------------------------------------------------------------------------------
 * 2015年10月14日 下午2:03:40      liyx           1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Repository
public class StockUpContractCostProductDao extends DefaultBaseDao<StockUpContractCostProductModel, Long> {

    /**
     * 根据备货合同ID得到备货产品
     * @param bhSaleContractId
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> getProductByBhSaleContractId(String bhSaleContractId, String cpSaleContractId, String saleContractId) {
        String sql = "select id,productName,productPartnerName,productTypeName,quantity,unitPrice,totalPrice,serviceStartDate,serviceEndDate ";
        sql += "from stockup_contractcost_product where bhSaleContractId='" + bhSaleContractId + "'";
        if (!StringUtil.isEmpty(cpSaleContractId)) {
            sql += " and cpSaleContractId='" + cpSaleContractId + "'";
        }
        if (!StringUtil.isEmpty(saleContractId)) {
            sql += " and saleContractId='" + saleContractId + "'";
        }

        List<Map<String, Object>> obList = createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        return obList;
    }

    /**
     * 查找产品合同关联备货相关订单，产品信息 ，未确认部分产品
     * @param bhSaleContractId
     * @param cpSaleContractId
     * @param saleContractId
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> getProducts(String saleContractId, String orderBuy) {

        StringBuffer sb = new StringBuffer();

        sb.append("select sc.id,sc.contractName,sc.contractCode,sc.contractAmount,");
        sb.append("sc.creatorName,bo.id orderId,bo.orderCode orderCode,bo.OrderAmount orderAmount,bo.creatorId orderProcessor,bp.saleContractId bhsaleContractId,bhsc.ContractCode bhSaleContractCode,sc.salesEndDate salesEndDate,bhc.businessCost bhbusinessCost,bhsc.ContractName bhSaleContractName ");
        sb.append("from sales_contract sc,  sales_contract bhsc,sales_contract_product cp,business_order bo,business_order_product bp,funds_salescontract bhc ");
        sb.append("where sc.id=cp.SaleContractId  AND bhsc.id=bp.SaleContractId and cp.RelateDeliveryProductId=bp.salesContractProductId AND bhsc.ContractCode=bhc.contractCode ");
        sb.append("and bo.id=bp.OrderId  and cp.relateDeliveryProductId is not null and bo.OrderStatus='TGSP' and sc.id=" + saleContractId + " AND bo.Creator='" + orderBuy + "'");
        sb.append(" and (select sum(payplan.Amount) from business_pay_apply apply,business_payment_plan payplan");
        sb.append(" where apply.id=payplan.payOrderId and payplan.orderId=bo.id and apply.PlanStatus=10)>0");
        sb.append(" GROUP BY bo.id,bhsc.id "); //一个订单对应一个备货合同

        List<Map<String, Object>> listMap = createSQLQuery(sb.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        List<Map<String, Object>> listProduct = new ArrayList<Map<String, Object>>();
        if (listMap.size() > 0) {
            for (int i = 0; i < listMap.size(); i++) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("orderId", listMap.get(i).get("orderId"));
                map.put("orderCode", listMap.get(i).get("orderCode"));
                map.put("orderAmount", listMap.get(i).get("orderAmount"));
                String orderId = listMap.get(i).get("orderId").toString();
                String bhContractId = listMap.get(i).get("bhsaleContractId").toString();
                map.put("bhsaleContractId", listMap.get(i).get("bhsaleContractId"));
                map.put("bhSaleContractCode", listMap.get(i).get("bhSaleContractCode"));
                map.put("bhSaleContractName", listMap.get(i).get("bhSaleContractName"));
                map.put("bhbusinessCost", listMap.get(i).get("bhbusinessCost") == null ? 0 : listMap.get(i).get("bhbusinessCost")); //备货合同总成本

                String bhqr = "SELECT t.bhSaleContractId,SUM(t.TotalPrice) totalPrice FROM stockup_contractcost_product t WHERE t.bhSaleContractId='" + listMap.get(i).get("bhsaleContractId") + "' GROUP BY t.bhSaleContractId";
                Map<String, Object> rs = (Map<String, Object>) createSQLQuery(bhqr).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).uniqueResult();
                if (rs != null) {//已确认的备货成本
                    map.put("bhqrCost", rs.get("totalPrice"));
                } else {
                    map.put("bhqrCost", 0);
                }
                map.put("bhwqrCost", new BigDecimal(map.get("bhbusinessCost").toString()).subtract(new BigDecimal(map.get("bhqrCost").toString()))); //未确认的备货成本

                StringBuffer sb2 = new StringBuffer();
                sb2.append("select t.*,t2.tq,(IFNULL(t.ddQ,0)-IFNULL(t2.tq,0)) syQ,(select ContractName from sales_contract c where c.id=t.bhsaleContractId) bhSaleContractName from (");
                sb2.append("select cp.quantity cpQ,op.quantity ddQ,op.productNo,op.unitPrice,op.subTotal,op.vendorCode,op.productType,");
                sb2.append("op.saleContractId bhsaleContractId,cp.relateDeliveryProductId,bo.id orderId,op.id orderProductId,cp.salecontractId,bo.orderCode ");
                sb2.append("from business_order_product op,business_order bo,sales_contract_product cp ");
                sb2.append("where op.orderId=bo.id and op.salesContractProductId=cp.RelateDeliveryProductId and bo.orderStatus='TGSP' and cp.relateDeliveryProductId is not null ");
                sb2.append("and op.orderId=" + orderId + " and op.SaleContractId=" + bhContractId + " and cp.SaleContractId=" + saleContractId + ")t ");
                sb2.append("LEFT JOIN ");
                sb2.append("(select scp.orderId,scp.relateDeliveryProductId,scp.productTypeName,sum(scp.quantity) tq from stockup_contractcost_product scp ");
                sb2.append("where orderid=" + orderId + " GROUP BY relateDeliveryProductId)t2 on t2.relateDeliveryProductId=t.relateDeliveryProductId ");
                sb2.append("where (IFNULL(t.ddQ,0)-IFNULL(t2.tq,0))>0");

                List lists = createSQLQuery(sb2.toString()).list();
                map.put("products", lists);

                listProduct.add(map);
            }
        }

        return listProduct;
    }

    /**
     * 删除
     * @param id
     */
    public void deleteProduct(Long id) {
        Long[] param = new Long[1];
        param[0] = id;
        String sql = "delete from stockup_contractcost_product where SaleContractId=?";
        createSQLQuery(sql, param).executeUpdate();
    }

}
