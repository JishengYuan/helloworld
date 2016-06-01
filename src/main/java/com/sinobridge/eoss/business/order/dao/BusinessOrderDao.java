package com.sinobridge.eoss.business.order.dao;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sinobridge.base.core.dao.imp.DefaultBaseDao;
import com.sinobridge.base.core.hibernate.id.IdentifierGeneratorImpl;
import com.sinobridge.base.core.utils.PaginationSupport;
import com.sinobridge.eoss.business.interPurchas.model.InterPurchasModel;
import com.sinobridge.eoss.business.order.constant.BusinessOrderContant;
import com.sinobridge.eoss.business.order.model.BusinessOrderModel;
import com.sinobridge.eoss.business.order.model.BusinessOrderProductModel;
import com.sinobridge.eoss.business.order.model.BusinessPayOrderModel;
import com.sinobridge.eoss.business.suppliermanage.dao.SupplierContactsDao;
import com.sinobridge.eoss.business.suppliermanage.dao.SupplierInfoDao;
import com.sinobridge.eoss.business.suppliermanage.model.ReturnSpotModel;
import com.sinobridge.eoss.sales.contract.constant.SalesContractConstant;
import com.sinobridge.eoss.sales.contract.dao.SalesContractDao;
import com.sinobridge.eoss.sales.contract.dao.SalesContractStatusDao;
import com.sinobridge.eoss.sales.contract.dao.SalesProductDao;
import com.sinobridge.eoss.sales.contract.model.SalesContractModel;
import com.sinobridge.eoss.sales.contract.model.SalesContractStatusModel;
import com.sinobridge.eoss.sales.stockupCost.model.StockUpContractCostModel;
import com.sinobridge.eoss.sales.stockupCost.service.StockUpContractCostProductService;
import com.sinobridge.eoss.sales.stockupCost.service.StockUpContractCostService;
import com.sinobridge.systemmanage.dao.SysStaffDao;

@SuppressWarnings("unchecked")
@Repository
public class BusinessOrderDao extends DefaultBaseDao<BusinessOrderModel, Long> {
    private static String removeSelect(String hql) {
        int beginPos = hql.toLowerCase().indexOf("from");
        return hql.substring(beginPos);
    }

    @Autowired
    private SupplierInfoDao supplierInfoDao;
    @Autowired
    private SupplierContactsDao supplierContactsDao;
    @Autowired
    private BusinessPaymentPlanDao businessPaymentPlanDao;
    @Autowired
    private BusinessOrderProductDao businessOrderProductDao;
    @Autowired
    private SalesContractDao salesContractDao;
    @Autowired
    private SysStaffDao sysStaffDao;
    @Autowired
    private SalesProductDao salesContractProductDao;
    @Autowired
    private SalesContractStatusDao salesContractStatusDao;
    @Autowired
    private StockUpContractCostProductService stockUpContractCostProductService;
    @Autowired
    private StockUpContractCostService stockUpContractCost;

    /**查询合同订单状态表
     * @param salesId
     * @return
     */
    public SalesContractStatusModel contractOrderStatus(Long salesId) {
        Object[] param = new Object[1];
        String sql = "SELECT * FROM sales_contract_status WHERE salecontractId=? ORDER BY CreateTime ";
        param[0] = salesId;
        Query query = salesContractStatusDao.createSQLQuery(sql, param).addEntity(SalesContractStatusModel.class);
        List<SalesContractStatusModel> objList = query.list();
        SalesContractStatusModel contract = objList.get(0);
        return contract;
    }

    /*   *//**
                      * @param id
                      */
    /*
    public void endOrderStatus(Long id) {
     // TODO Auto-generated method stub
     Object[] param = new Object[2];
     String sql = "update BusinessPayOrderModel set reimBursStatus=? where id=?";
     param[0] = BusinessOrderContant.NFF_CG;
     param[1] = id;
     this.executeSql(sql, param);
    }*/

    /**待处理的合同
     * @param searchMap
     * @param parseInt
     * @param pageSize
     * @param flat
     * @return
     */
    public PaginationSupport findCloseContract(HashMap<String, Object> searchMap, int parseInt, Integer pageSize, int flat) {
        int start = 0;
        Object[] values = new Object[searchMap.size()];
        // Object orderBy = searchMap.get("orderBy");
        Object orderTGSP = searchMap.get("orderStatusNotTGSP");
        Object contractState = searchMap.get(SalesContractModel.CONTRACTSTATE);
        Object contractStatuse = searchMap.get("contractStatuse");
        Object person1 = searchMap.get("orderProcessor1");
        Object person2 = searchMap.get("orderProcessor2");
        Object person3 = searchMap.get("orderProcessor3");
        Object person4 = searchMap.get("orderProcessor4");
        Object per1 = searchMap.get("orderPro1");
        Object per2 = searchMap.get("orderPro2");
        Object per3 = searchMap.get("orderPro3");
        Object per4 = searchMap.get("orderPro4");
        Object contractName = searchMap.get(SalesContractModel.CONTRACTNAME);
        Object contractCode = searchMap.get(SalesContractModel.CONTRACTCODE);
        Object contractAmountString = searchMap.get(SalesContractModel.CONTRACTAMOUNT);
        Object reimNot = searchMap.get("reimNot");
        Object reim = searchMap.get("reim");
        Object payment = searchMap.get("payment");
        //Object arrival = searchMap.get("arrival");
        String hql = "select * from (select distinct c.id, c.ContractCode, c.ContractState, c.ContractName, d.PayStatus, f.Statuse, d.OrderName,d.ArrivalStatus, d.ReimStatus, d.OrderCode, c.ContractAmount FROM sales_contract c LEFT JOIN  sales_contract_status cs ON c.id=cs.SaleContractId LEFT JOIN order_contract_map e ON c.id=e.sales_contract LEFT JOIN  business_order d ON e.business_order=d.id LEFT JOIN order_sales_close f ON c.id=f.SalesContractId  WHERE cs.ChangeStatus='未申请' ";
        if (orderTGSP != null) {
            values[start++] = orderTGSP + "";
            hql += " and cs.OrderStatus!=?";
        }
        if (reimNot != null) {
            values[start++] = reimNot + "";
            hql += " and d.ReimStatus!=?";
        }
        if (reim != null) {
            values[start++] = reim + "";
            hql += " and d.ReimStatus=?";
        }
        if (payment != null) {
            values[start++] = payment + "";
            hql += " and d.PayStatus=?";
        }
        if (contractState != null) {
            values[start++] = contractState + "";
            hql += " and c.ContractState=?";
        }
        if (person1 != null) {
            values[start++] = person1 + "";
            hql += " and (c.OrderProcessor=? ";
            values[start++] = person2 + "";
            hql += " or c.OrderProcessor like ? ";
            values[start++] = person3 + "";
            hql += " or c.OrderProcessor like ? ";
            values[start++] = person4 + "";
            hql += " or c.OrderProcessor like ? )";
        }
        if (flat == 1) {
            hql += " UNION ALL SELECT c.id, c.ContractCode,c.ContractState,c.ContractName,'N','NULL','创建订单','未到货','N','NULL',c.ContractAmount  FROM sales_contract c, sales_contract_status sct WHERE c.id NOT IN( SELECT sales_contract FROM order_contract_map) and c.id=sct.salecontractId and sct.changeStatus='未申请' ";
            if (contractStatuse != null) {
                values[start++] = contractStatuse + "";
                hql += " and c.ContractState=?";
            }
            if (per1 != null) {
                values[start++] = per1 + "";
                hql += " and (c.OrderProcessor=? ";
                values[start++] = per2 + "";
                hql += " or c.OrderProcessor like ? ";
                values[start++] = per3 + "";
                hql += " or c.OrderProcessor like ? ";
                values[start++] = per4 + "";
                hql += " or c.OrderProcessor like ? )";
            }
        }
        hql += " ) m where 0=0 ";
        if (contractName != null) {
            values[start++] = contractName + "";
            hql += " and m." + SalesContractModel.CONTRACTNAME + " like ?";
        }
        if (contractCode != null) {
            values[start++] = contractCode + "";
            hql += " and m." + SalesContractModel.CONTRACTCODE + " like ?";
        }
        if (contractAmountString != null) {
            BigDecimal amount = new BigDecimal(contractAmountString.toString());
            values[start++] = amount;
            hql += " and m." + SalesContractModel.CONTRACTAMOUNT + "=?";
        }
        hql += " order By m.id DESC";
        PaginationSupport ps = findPageByQueryWithParam(hql, values, pageSize, parseInt);
        List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
        List<Object> rows = (List<Object>) ps.getItems();
        for (int i = 0; i < rows.size(); i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            Object[] entity = (Object[]) rows.get(i);
            map.put("id", entity[0]);
            map.put("contractCode", entity[1]);
            map.put("contractState", entity[2]);
            map.put("contractName", entity[3]);
            map.put("paymentStatus", entity[4]);
            if (entity[5] != null) {
                map.put("closeStatuse", "申请中");
            } else {
                map.put("closeStatuse", "未申请");
            }
            map.put("orderName", entity[6]);
            map.put("arrivalStatus", entity[7]);
            map.put("reimStatus", entity[8]);
            map.put("orderCode", entity[9]);
            list.add(map);
        }
        ps.setItems(list);
        return ps;
    }

    /**
     * 根据合同ID查询所有订单
     * @param contractId
     * @return
     */
    public List<BusinessOrderModel> findOrdersByContract(String contractId) {
        String sql = "SELECT a.*,c.Statuse FROM business_order a INNER JOIN order_contract_map b ON a.id=b.business_order LEFT JOIN order_sales_close c ON b.sales_contract=c.SalesContractId WHERE  b.sales_contract='" + contractId + "'";
        Query query = createSQLQuery(sql).addEntity(BusinessOrderModel.class);
        List<BusinessOrderModel> rs = query.list();
        return rs;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public PaginationSupport findPageByQueryWithParam(final String sql, final Object[] values, final int pageSize, final int startIndex) {
        int totalCount = getCountBySQLQuery(sql, values);

        if (totalCount < 1) {
            return new PaginationSupport(new ArrayList(0), 0);
        }

        Query query = createSQLQuery(sql, values);
        List items = query.setFirstResult(startIndex).setMaxResults(pageSize).list();
        PaginationSupport ps = new PaginationSupport(items, totalCount, pageSize, startIndex);
        return ps;
    }

    /**订单选合同
     * @param searchMap
     * @param parseInt
     * @param iDisplayLength
     * @return
     */
    public PaginationSupport findSalesContract(HashMap<String, Object> searchMap, Integer parseInt, Integer iDisplayLength) {

        Object creator = searchMap.get(SalesContractModel.CREATOR);
        Object contractCode = searchMap.get(SalesContractModel.CONTRACTCODE);
        Object contractName = searchMap.get(SalesContractModel.CONTRACTNAME);
        Object ContractState = searchMap.get(SalesContractModel.CONTRACTSTATE);
        Object OrderStatus = searchMap.get("orderStatusNotTGSP");
        Object orderProcessor1 = searchMap.get("orderProcessor1");
        Object orderProcessor2 = searchMap.get("orderProcessor2");
        Object orderProcessor3 = searchMap.get("orderProcessor3");
        Object orderProcessor4 = searchMap.get("orderProcessor4");
        String hql = "select * from (select t.id,t.contractName,t.contractCode,t.creatorName,t.orderProcessor from (select a.id,a.contractName,a.contractCode,b.quantity-SUM(c.quantity) ordersnum,a.creatorName,a.orderProcessor from sales_contract a, sales_contract_product b,business_order_product c,sales_contract_status sct where a.id=b.salecontractId and b.id=c.salescontractproductId AND b.relatedeliveryproductid IS NULL ";
        if (ContractState != null) {
            //     values[start++] = ContractState + "";
            hql += " and a.ContractState='" + ContractState + "'";
        }
        hql += " and sct.SalecontractId=a.id AND sct.ChangeStatus='未申请'";
        if (creator != null) {
            String creatorId = getUserNameId(creator);
            //     values[start++] = creatorId;
            hql += " and a.CreatorName='" + creatorId + "'";
        }
        if (contractCode != null) {
            //   values[start++] = contractCode + "";
            hql += " and a." + SalesContractModel.CONTRACTCODE + " like '" + contractCode + "'";
        }
        if (contractName != null) {
            //  values[start++] = contractName + "";
            hql += " and a." + SalesContractModel.CONTRACTNAME + " like '" + contractName + "'";
        }
        if (orderProcessor1 != null) {
            hql += " and (a.orderProcessor='" + orderProcessor1 + "'";
            hql += " or a.orderProcessor like '" + orderProcessor2 + "'";
            hql += " or a.orderProcessor like '" + orderProcessor3 + "'";
            hql += " or a.orderProcessor like '" + orderProcessor4 + "' )";
        }
        hql += " GROUP BY a.id,b.quantity,a.contractName,a.contractCode,a.creatorName,c.salescontractproductId ) t where t.ordersnum>0 ";

        hql += " union all select a.id,a.contractName,a.contractCode,a.creatorName,a.orderProcessor from sales_contract a,sales_contract_product b,sales_contract_status d where a.id=b.salecontractId AND b.relatedeliveryproductid IS NULL and not exists (select b.id from business_order_product c where b.id=c.salescontractproductId)  and a.id=d.SaleContractId and d.ChangeStatus='未申请' ";
        if (ContractState != null) {
            //   values[start++] = ContractState + "";
            hql += " and a.ContractState='" + ContractState + "'";
        }
        if (OrderStatus != null) {
            //   values[start++] = OrderStatus + "";
            hql += " and d.OrderStatus!='" + OrderStatus + "'";
        }
        if (creator != null) {
            String creatorId = getUserNameId(creator);
            //     values[start++] = creatorId;
            hql += " and a.CreatorName='" + creatorId + "'";
        }
        if (contractCode != null) {
            //   values[start++] = contractCode + "";
            hql += " and a." + SalesContractModel.CONTRACTCODE + " like '" + contractCode + "'";
        }
        if (contractName != null) {
            //  values[start++] = contractName + "";
            hql += " and a." + SalesContractModel.CONTRACTNAME + " like '" + contractName + "'";
        }
        if (orderProcessor1 != null) {
            hql += " and (a.orderProcessor='" + orderProcessor1 + "'";
            hql += " or a.orderProcessor like '" + orderProcessor2 + "'";
            hql += " or a.orderProcessor like '" + orderProcessor3 + "'";
            hql += " or a.orderProcessor like '" + orderProcessor4 + "')";
        }
        hql += ") m where 0=0 ";

        hql += " group by m.id ";
        int totalnum = getTotalCount(hql);
        hql += " order by m.id desc";
        Query query = salesContractDao.createSQLQuery(hql);
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        //        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        //        mapList = query.list();
        List<Object> items = query.setFirstResult(parseInt).setMaxResults(iDisplayLength).list();
        PaginationSupport ps = new PaginationSupport(items, totalnum, iDisplayLength, parseInt);

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
    public Integer getTotalCount(String sql) {
        String countQueryString = " select COUNT(*) from (" + sql + ") t";
        List countlist = createSQLQuery(countQueryString).list();
        return Integer.parseInt(countlist.get(0).toString());
    }

    @Override
    public int getCountBySQLQuery(final String sql, Object[] values) {
        String countQueryString = " select COUNT(*) from(" + sql + ") as totalCount";
        List countlist = createSQLQuery(countQueryString, values).list();
        return Integer.parseInt(countlist.get(0).toString());
    }

    /**查询订单产品
     * @param id
     * @return
     */
    public List<BusinessOrderProductModel> getOrderProduct(String id) {
        Object[] param = new Object[1];
        String sql = "select * from business_order_product where SaleContractId=?";
        param[0] = id;
        Query query = businessOrderProductDao.createSQLQuery(sql, param);
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List<BusinessOrderProductModel> objList = query.list();
        /*for(int i=0;i<objList.size();i++) {
            System.out.println(objList.get(i));
            BusinessOrderProductModel product=objList.get(i);
            orderproduct.add(product);
        }*/
        return objList;
    }

    /**得到供应商编码
     * @param id
     * @return
     */
    public List<Map<String, Object>> getSupplierCode(String id) {
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        String[] param = new String[1];
        param[0] = id;
        String sql = "select SupplierCode,id from bussiness_supplier where id=?";
        Query query = this.supplierInfoDao.createSQLQuery(sql, param);
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List<Map<String, Object>> objList = query.list();
        for (Map<String, Object> objMap : objList) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", objMap.get("id"));
            map.put("name", objMap.get("SupplierCode"));
            mapList.add(map);
        }
        return mapList;
    }

    /**得到供应商联系人
     * @param id
     * @return
     */
    public List<Map<String, Object>> getSupplierContacts(String id) {
        //List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        String[] param = new String[1];
        param[0] = id;
        String sql = "select * from bussiness_suppliercontacts where SupplierId=?";
        Query query = this.supplierContactsDao.createSQLQuery(sql, param);
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List<Map<String, Object>> objList = query.list();
        /*  for(Map<String,Object> objMap : objList){
              Map<String,Object> map = new HashMap<String,Object>();
              map.put("id", objMap.get("id"));
              map.put("name", objMap.get("ContactName"));
              mapList.add(map);
          }*/
        return objList;
    }

    /**查询用户信息
     * @param creator
     * @return
     */
    private String getUserNameId(Object creator) {
        // TODO Auto-generated method stub
        //List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        Object[] param = new Object[1];
        String sql = "select StaffId,UserName,StaffName from sys_staff where UserName = ?";
        param[0] = creator;
        Query query = sysStaffDao.createSQLQuery(sql, param);
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List<Map<String, Object>> objList = query.list();

        Map<String, Object> ids = objList.get(0);
        String name = ids.get("StaffName").toString();
        return name;
    }

    /**更新状态为审批通过
     * @param id
     */
    public void orderOK(Long id) {
        //更新状态为审批通过
        Object[] param = new Object[2];
        String sql = "update BusinessOrderModel set orderStatus=? where id=?";
        param[0] = BusinessOrderContant.ORDER_OK;
        param[1] = id;
        this.executeSql(sql, param);

    }

    /**查询合同订单状态
     * @param id
     * @return
     */
    public SalesContractStatusModel salesStart(Long id) {
        Object[] param = new Object[1];
        String sql = "SELECT * FROM sales_contract_status WHERE salecontractId=? ORDER BY CreateTime ";
        param[0] = id;
        Query query = salesContractStatusDao.createSQLQuery(sql, param).addEntity(SalesContractStatusModel.class);
        List<SalesContractStatusModel> objList = query.list();
        SalesContractStatusModel contract = objList.get(0);
        return contract;

    }

    /**保存订单到货状态，签收单ID
     * @param id
     * @param fileId
     */
    public void savefile(Long id, String fileId) {
        Object[] param = new Object[3];
        String sql = "update BusinessOrderModel set attachIds=? , arrivalStatus=? where id=?";
        param[0] = fileId;
        param[1] = BusinessOrderContant.COMMODITY;
        param[2] = id;
        this.executeSql(sql, param);
    }

    /*   public List<SalesContractProductModel> findProduct(Long id) {
           Object[] param = new Object[1];
           String sql = "SELECT * FROM sales_contract_product WHERE saleContractId=?";
           param[0] = id;
           Query query = salesContractProductDao.createSQLQuery(sql, param).addEntity(SalesContractProductModel.class);
           List<SalesContractProductModel> objList = query.list();
           return objList;
       }*/

    /**查询所有订单的总金额
     * @param searchMap
     * @return
     */
    public String findTotallAmount(HashMap<String, Object> searchMap) {
        int start = 0;
        Object[] values = new Object[searchMap.size()];
        Object orderState = searchMap.get(BusinessOrderModel.ORDERSTATUS);
        Object orderCode = searchMap.get(BusinessOrderModel.ORDERCODE);
        Object orderName = searchMap.get(BusinessOrderModel.ORDERNAME);
        Object purchaseType = searchMap.get(BusinessOrderModel.PURCHASETYPE);
        Object supplierId = searchMap.get("supplierInfoId");
        Object name = searchMap.get(BusinessOrderModel.CREATORID);
        Object payStatus = searchMap.get(BusinessOrderModel.PAYSTATUS);
        Object reimStatus = searchMap.get(BusinessOrderModel.REIMSTATUS);
        Object startTime = searchMap.get(BusinessOrderModel.CREATEDATE);
        Object endTime = searchMap.get("endTime");
        Object orderAmount = searchMap.get(BusinessOrderModel.ORDERAMOUNT);
        Object orderType = searchMap.get(BusinessOrderModel.ORDERTYPE);

        String hql = "select sum(c.orderAmount) from business_order c where 0=0";
        if (orderState != null) {
            values[start++] = orderState + "";
            hql += " and c." + BusinessOrderModel.ORDERSTATUS + "!=?";
        }
        if (orderType != null) {
            values[start++] = orderType + "";
            hql += " and c." + BusinessOrderModel.ORDERTYPE + "=?";
        }
        if (orderAmount != null) {
            values[start++] = orderAmount + "";
            hql += " and c." + BusinessOrderModel.ORDERAMOUNT + "=?";
        }
        if (orderCode != null) {
            values[start++] = orderCode + "";
            hql += " and c." + BusinessOrderModel.ORDERCODE + " like ?";
        }
        if (orderName != null) {
            values[start++] = orderName + "";
            hql += " and c." + BusinessOrderModel.ORDERNAME + " like ?";
        }
        if (purchaseType != null) {
            values[start++] = purchaseType + "";
            hql += " and c." + BusinessOrderModel.PURCHASETYPE + "=?";
        }
        if (supplierId != null) {
            values[start++] = supplierId + "";
            hql += " and c.SupplierInfo=?";
        }
        if (name != null) {
            values[start++] = name + "";
            hql += " and c." + BusinessOrderModel.CREATORID + " =? ";
        }
        if (payStatus != null) {
            values[start++] = payStatus + "";
            hql += " and c." + BusinessOrderModel.PAYSTATUS + " =? ";
        }
        if (reimStatus != null) {
            values[start++] = reimStatus + "";
            hql += " and c." + BusinessOrderModel.REIMSTATUS + " =? ";
        }
        if (startTime != null) {
            values[start++] = startTime + "";
            hql += " and c." + BusinessOrderModel.CREATEDATE + " >=? ";
        }
        if (endTime != null) {
            values[start++] = endTime + "";
            hql += " and c." + BusinessOrderModel.CREATEDATE + " <=? ";
        }
        Query query = this.createSQLQuery(hql, values);
        //query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        BigDecimal amount = (BigDecimal) query.uniqueResult();
        // Iterator<BusinessOrderModel> bus = objList.iterator();
        // BigDecimal amount = new BigDecimal(0.00);
        //        while (bus.hasNext()) {
        //            BusinessOrderModel b = bus.next();
        //            if (b.getOrderAmount() != null) {
        //                amount = amount.add(b.getOrderAmount());
        //            }
        //        }
        String totall = "0";
        if (amount != null) {
            totall = amount.toString();
        }
        return totall;
    }

    /**查询订单
     * @param code
     * @return
     */
    public BusinessOrderModel findOrder(String code) {
        BusinessOrderModel order = new BusinessOrderModel();
        Object[] param = new Object[1];
        String sql = "select * from business_order where OrderCode=?";
        param[0] = code;
        Query query = this.createSQLQuery(sql, param).addEntity(BusinessOrderModel.class);
        List<BusinessOrderModel> objList = query.list();
        order = objList.get(0);
        return order;
    }

    /**更新內采状态
     * @param interId
     */
    public void updateInterOrderStatus(Long interId) {
        Object[] param = new Object[2];
        String sql = "update InterPurchasModel set interOrderstatus=? where id=?";
        param[0] = BusinessOrderContant.INTER_ORDER_OK;
        param[1] = interId;
        executeSql(sql, param);
    }

    /**保存內采中订单状态
     * @param interId
     */
    public void getInterStatus(long interId) {
        Object[] param = new Object[2];
        String sql = "update InterPurchasModel set interOrderstatus=? where id=?";
        param[0] = BusinessOrderContant.INTER_ORDER_DOING;
        param[1] = interId;
        executeSql(sql, param);
    }

    /**查询合同产品数量
     * @param id
     * @return
     */
    public int findproductQue(long id) {
        int num = 0;
        Object[] param = new Object[1];
        String sql = "select * from business_order_product where salesContractProductId=?";
        param[0] = id;
        Query query = this.createSQLQuery(sql, param).addEntity(BusinessOrderProductModel.class);
        List<BusinessOrderProductModel> objList = query.list();
        Iterator<BusinessOrderProductModel> product = objList.iterator();
        while (product.hasNext()) {
            BusinessOrderProductModel p = product.next();
            num = num + p.getQuantity();
        }
        return num;
    }

    /**查询內采产品数量
     * @param id
     * @return
     */
    public int findInterproductQue(long id) {
        int num = 0;
        Object[] param = new Object[1];
        String sql = "select * from business_order_product where PurchasProductId=?";
        param[0] = id;
        Query query = this.createSQLQuery(sql, param).addEntity(BusinessOrderProductModel.class);
        List<BusinessOrderProductModel> objList = query.list();
        Iterator<BusinessOrderProductModel> product = objList.iterator();
        while (product.hasNext()) {
            BusinessOrderProductModel p = product.next();
            num = num + p.getQuantity();
        }
        return num;
    }

    /**是否有流程ID
     * @param proId
     * @return
     */
    public BusinessOrderModel findProdId(String proId) {
        Long id = Long.parseLong(proId);
        Object[] param = new Object[1];
        String sql = "select * from business_order where processInstanceId=?";
        param[0] = id;
        Query query = this.createSQLQuery(sql, param).addEntity(BusinessOrderModel.class);
        List<BusinessOrderModel> objList = query.list();
        BusinessOrderModel order = objList.get(0);
        return order;
    }

    /**查询厂商返点数
     * @param supplierId
     * @return
     */
    public ReturnSpotModel getSupplierSpot(long supplierId) {
        Object[] param = new Object[1];
        String sql = "select * from business_returnspot where SupplierId=?";
        param[0] = supplierId;
        Query query = this.createSQLQuery(sql, param).addEntity(ReturnSpotModel.class);
        List<ReturnSpotModel> objList = query.list();
        ReturnSpotModel spot = new ReturnSpotModel();
        if (objList.size() > 0) {
            spot = objList.get(0);
            return spot;
        } else {
            return null;
        }

    }

    /**查询所有厂商返点数
     * @return
     */
    public List<Map<String, Object>> getListSupplierSpot() {
        String sql = " SELECT t.`id`,t.SupplierId, s.`SupplierName`,t.`ReturnAmount` FROM business_returnspot t,`bussiness_supplier` s WHERE t.`SupplierId`=s.`id`";
        Query query = this.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List<Map<String, Object>> objList = query.list();
        return objList;
    }

    /**删除发票计划
     * @param id
     */
    public void deleteContractInvoice(Long id) {
        Long[] param = new Long[1];
        param[0] = id;
        String sql = "delete from sales_invoice_plan where SalesContractId = ?";
        createSQLQuery(sql, param).executeUpdate();
    }

    /**删除盖章申请
     * @param id
     */
    public void deleteContractCachet(Long id) {
        Long[] param = new Long[1];
        param[0] = id;
        String sql = "delete from sales_cachet where SalesContractId = ?";
        createSQLQuery(sql, param).executeUpdate();
    }

    /**删除合同状态表
     * @param id
     */
    public void deleteContractOrder(Long id) {
        Long[] param = new Long[1];
        param[0] = id;
        String sql = "delete from sales_contract_status where SalecontractId=?";
        createSQLQuery(sql, param).executeUpdate();
    }

    /**改合同状态
     * @param id
     */
    public void updataContractStatus(long id) {
        Object[] param = new Object[2];
        String sql = "update SalesContractModel set contractState=? where id=?";
        param[0] = SalesContractConstant.CONTRACT_STATE_CG;
        param[1] = id;
        executeSql(sql, param);
    }

    /**查询合同状态表
     * @param id
     * @return
     */
    public List<SalesContractStatusModel> findContratStatus(String id) {
        Object[] param = new Object[1];
        String sql = "select * from sales_contract_status where SalecontractId=?";
        param[0] = id;
        Query query = this.createSQLQuery(sql, param).addEntity(SalesContractStatusModel.class);
        List<SalesContractStatusModel> objList = query.list();
        return objList;
    }

    /**查询产品类型
     * @param id
     * @return
     */
    public String findProductTypeName(long id) {
        String name = "";
        Object[] param = new Object[1];
        String sql = "SELECT c.TypeName FROM business_producttype c LEFT JOIN business_productmodel p ON  p.ProductType=c.id WHERE p.id=?";
        param[0] = id;
        Query query = this.createSQLQuery(sql, param).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List<Map<String, Object>> objList = query.list();
        if (objList.size() > 0) {
            name = (String) objList.get(0).get("TypeName");
        }
        return name;
    }

    /**查询订单编号是否存在
     * @param orderCode
     * @return
     */
    public List<Map<String, Object>> findOrderCode(String orderCode) {
        Object[] param = new Object[1];
        String sql = "SELECT * from business_order where OrderCode=?";
        param[0] = orderCode;
        Query query = this.createSQLQuery(sql, param).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List<Map<String, Object>> objList = query.list();
        return objList;
    }

    /**修改订单变更状态为SP
     * @param id
     */
    public void updateOrderStatusBG(Long id) {
        Object[] param = new Object[2];
        String sql = "update BusinessOrderModel set isChange=? where id=?";
        param[0] = "CSP";
        param[1] = id;
        this.executeSql(sql, param);
    }

    /**查询合同产品列表
     * @param parseLong
     * @return
     */
    public List<Map<String, Object>> findProductUn(long parseLong) {
        Object[] param = new Object[2];
        param[0] = parseLong;
        param[1] = parseLong;
        String sql = "SELECT c.id,c.ServiceStartDate,c.ServiceEndDate,c.ProductNo,c.ProductTypeName,c.Remark,c.VendorCode,c.TotalPrice,c.UnitPrice,c.ProductName,c.ProductPartnerName,c.ProductType,c.SaleContractId,c.`Quantity`-SUM(t.Quantity) quantity  FROM  sales_contract_product c , business_order_product t WHERE t.salesContractProductId=c.id AND c.RelateDeliveryProductId IS NULL and c.SaleContractId=? GROUP BY c.id ";
        sql += " UNION ALL SELECT c.id,c.ServiceStartDate,c.ServiceEndDate,c.ProductNo,c.ProductTypeName,c.Remark,c.VendorCode,c.TotalPrice,c.UnitPrice,c.ProductName,c.ProductPartnerName,c.ProductType,c.SaleContractId,c.`Quantity` FROM sales_contract_product c WHERE NOT EXISTS (SELECT c.id FROM business_order_product t WHERE c.id=t.`salesContractProductId`) AND c.RelateDeliveryProductId IS NULL AND c.SaleContractId=? ";
        Query query = this.createSQLQuery(sql, param).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List<Map<String, Object>> objList = query.list();
        return objList;
    }

    /*  public void getArrivalStatus(Long id) {
        Object[] param = new Object[2];
        String sql = "update SalesContractStatusModel set orderStatus=?  where id=?";
        param[0] = "已到货";
        param[2] = id;
        this.executeSql(sql, param);
    }*/

    /**添加订单生效时间
     * @param id
     * @param time 
     */
    public void updateBeginTime(Long id, Date time) {
        Object[] param = new Object[2];
        String sql = "update BusinessOrderModel set beginTime=? where id=?";
        param[0] = time;
        param[1] = id;
        executeSql(sql, param);

    }

    /**查询合同是否变更
     * @param contractId
     * @return
     */
    public String findContractName(String[] contractId) {
        String str = "";
        for (int i = 0; i < contractId.length; i++) {
            str += "'" + contractId[i] + "'";
        }
        String sql = "SELECT c.`ContractName` FROM `sales_contract_status` s,`sales_contract` c WHERE s.`SalecontractId` IN (" + str + ") AND c.`id`=s.`SalecontractId` AND s.`ChangeStatus`!='未申请'";
        Query query = this.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List<Map<String, Object>> objList = query.list();
        String name = "";
        if (objList.size() > 0) {
            for (int i = 0; i < objList.size(); i++) {
                name += objList.get(i).get("ContractName") + ",";
            }
        }
        return name;
    }

    /**更新订单付款状态为未付款
     * @param oid
     */
    public void updatePayStatus(Long oid) {
        Object[] param = new Object[2];
        String sql = "update BusinessOrderModel set PayStatus=? where id=?";
        param[0] = "N";
        param[1] = oid;
        executeSql(sql, param);
    }

    /**查询厂商
     * @param spotSupplier
     * @return
     */
    public ReturnSpotModel getSupplierNameSpot(String spotSupplier) {
        Object[] param = new Object[1];
        param[0] = spotSupplier;
        String sql = "SELECT t.* FROM business_returnspot t ,`bussiness_supplier` s WHERE t.`SupplierId`=s.`id` AND s.`SupplierName`=? ";
        Query query = this.createSQLQuery(sql, param).addEntity(ReturnSpotModel.class);
        List<ReturnSpotModel> objList = query.list();
        if (objList.size() > 0) {
            return objList.get(0);
        } else {
            return null;
        }
    }

    /**根据合同ID，查询所有关联的订单
     * @param id
     * @return
     */
    public List<BusinessOrderModel> findOrderInSales(Long id) {
        Object[] param = new Object[1];
        param[0] = id;
        String sql = "SELECT b.* FROM `sales_contract` s LEFT JOIN `order_contract_map` m ON m.`sales_contract`=s.`id` LEFT JOIN `business_order` b ON b.`id`=m.`business_order` WHERE s.`id`=? AND b.`OrderStatus`!='CG' ";
        Query query = this.createSQLQuery(sql, param).addEntity(BusinessOrderModel.class);
        List<BusinessOrderModel> objList = query.list();
        return objList;
    }

    /**
     * 根据订单ID，查询所有关联的合同
     * @param id
     * @return
     */
    public List<SalesContractModel> findSalesByOrderId(Long id) {
        Object[] param = new Object[1];
        param[0] = id;
        String sql = "SELECT s.* FROM `sales_contract` s LEFT JOIN `order_contract_map` m ON m.`sales_contract`=s.`id` LEFT JOIN `business_order` b ON b.`id`=m.`business_order` WHERE b.`id`=? AND b.`OrderStatus`!='CG' ";
        Query query = this.createSQLQuery(sql, param).addEntity(SalesContractModel.class);
        List<SalesContractModel> objList = query.list();
        return objList;
    }

    /**更新合同订单状态
     * @param id
     */
    public void updateConOrderStatus(Long id) {
        Object[] param = new Object[2];
        String sql = "update SalesContractStatusModel set OrderStatus=? where SalecontractId=?";
        param[0] = "未采购";
        param[1] = id;
        executeSql(sql, param);
    }

    /**更新流程标题
     * @param title
     * @param processInstanceId
     */
    public void updateOrderTitle(String title, Long processInstanceId) {
        String sql = "UPDATE bpm_process_inst t SET t.TITLE_='" + title + "' WHERE t.ID_='" + processInstanceId + "' ";
        createSQLQuery(sql).executeUpdate();
    }

    /**备货合同----资金占用成本计算
     * @param id
     * @return
     */
    public void getCpSales(Long oid) {
        StringBuffer sb = new StringBuffer();
        //主表，查询出所有相关的合同
        sb.append(" SELECT t.*,c.`ContractCode`,c.`ContractName`,c.`ContractAmount`,c.`ContractState`,c.`IsChanged`,c.`ContractType`,");
        sb.append(" c.`CreateTime`,c.`Creator`,c.`CreatorName`,c.id cid,c.SalesStartDate,t.OrderAmount,t.OrderCode FROM (");
        sb.append(" SELECT o.`id` oid,sp.`id` spid,sc.id bhid,o.`CreatorId`,sc.`ContractCode` bhCode,o.`OrderAmount`,o.`OrderCode` FROM business_order_product p ");
        sb.append(" LEFT JOIN business_order o ON o.`id`=p.`OrderId`");
        sb.append(" LEFT JOIN sales_contract_product sp ON sp.`id`=p.`salesContractProductId`");
        sb.append(" LEFT JOIN sales_contract sc ON p.`SaleContractId`=sc.`id`");
        sb.append(" WHERE sc.`ContractType`='9000' AND o.`id`='" + oid + "' )t  ");
        sb.append(" LEFT JOIN sales_contract_product cp ON cp.`RelateDeliveryProductId` = t.spid ");
        sb.append(" LEFT JOIN sales_contract c ON cp.`SaleContractId`=c.`id`");
        sb.append(" WHERE c.`SalesStartDate` IS NOT NULL GROUP BY c.`id`");
        List<Map<String, Object>> listMap = this.createSQLQuery(sb.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        if (listMap.size() > 0) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            for (Map<String, Object> map : listMap) {
                String cpSaleId = map.get("cid").toString();

                StringBuffer bb = new StringBuffer();
                //查询此合同是否被确认完。
                bb.append("SELECT salesP.SaleContractId ,stockP.RelateDeliveryProductId,salesP.Quantity sq,stockP.pQue tq FROM ( ");
                bb.append("SELECT t.* FROM (SELECT sp.`SaleContractId`,sp.`Quantity`,sp.`RelateDeliveryProductId` ");
                bb.append("FROM sales_contract_product sp WHERE sp.`SaleContractId`='" + cpSaleId + "' AND sp.`RelateDeliveryProductId` IS NOT NULL ");
                bb.append(")t LEFT JOIN sales_contract_product bhp ON bhp.`id`=t.RelateDeliveryProductId ) salesP , ");
                bb.append("(SELECT p.`RelateDeliveryProductId`,p.`CpSaleContractId`,SUM(p.`Quantity`) pQue ");
                bb.append("FROM `stockup_contractcost` s,`stockup_contractcost_product` p WHERE s.`SalesContractId`='" + cpSaleId + "' ");
                bb.append("AND s.`id`=p.`SaleContractId` AND s.`DoState`='1' ");
                bb.append(")stockP WHERE  salesP.RelateDeliveryProductId=stockP.RelateDeliveryProductId ");
                bb.append("AND stockP.RelateDeliveryProductId IN (SELECT op.salesContractProductId FROM business_order_product op ");
                bb.append("WHERE op.`OrderId`='" + oid + "' GROUP BY op.salesContractProductId) GROUP BY stockP.RelateDeliveryProductId");

                List<Map<String, Object>> list = this.createSQLQuery(bb.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
                int flat = 0;
                for (Map<String, Object> ma : list) {
                    if (!ma.get("sq").toString().equals(ma.get("tq").toString())) {
                        flat = 1;
                    }
                }
                int n = list.size();
                //若没确认完，插入合同信息
                if (flat == 1 || n < 1) {
                    StockUpContractCostModel contractCostModel = new StockUpContractCostModel();
                    long id = IdentifierGeneratorImpl.generatorLong();
                    contractCostModel.setId(id);
                    contractCostModel.setContractName(map.get("ContractName").toString());
                    contractCostModel.setContractCode(map.get("ContractCode").toString());
                    contractCostModel.setContractAmount(new BigDecimal(map.get("ContractAmount").toString()));
                    contractCostModel.setCreatorName(map.get("CreatorName").toString());
                    //contractCostModel.setOrderId(map.get("oid").toString());
                    contractCostModel.setOrderProcessor(map.get("CreatorId").toString());
                    //contractCostModel.setRelateDeliveryContractId(map.get("bhid").toString());
                    //contractCostModel.setRelateDeliveryContractCode(map.get("bhCode").toString());
                    contractCostModel.setSalesContractId(map.get("cid").toString());
                    contractCostModel.setDoState("0");
                    //contractCostModel.setCostNum(new BigDecimal(0.00));
                    //contractCostModel.setOrderAmount(new BigDecimal(map.get("OrderAmount").toString()));
                    //contractCostModel.setOrderCode(map.get("OrderCode").toString());
                    stockUpContractCost.create(contractCostModel);

                    /*  StringBuffer sb3 = new StringBuffer();
                      sb3.append("select cp.id,cp.quantity cpQua,op.unitPrice,cp.productName,cp.productPartnerName,cp.productTypeName,cp.serviceStartDate,");
                      sb3.append("cp.serviceEndDate,cp.salecontractId,op.saleContractId bhsaleContractId,cp.relateDeliveryProductId,bo.id orderId,op.`Quantity` opQua  ");
                      sb3.append("from business_order_product op,business_order bo,sales_contract_product cp ");
                      sb3.append("where op.orderId=bo.id and op.salesContractProductId=cp.RelateDeliveryProductId and bo.orderStatus='TGSP' and op.SaleContractId='" + bhSaleContractId + "' ");
                      sb3.append("and bo.`id`='"+oid+"' AND cp.salecontractId='"+cpSaleId+"' ");
                      List<Map<String, Object>> mapList3 = this.createSQLQuery(sb3.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
                      if (mapList3.size() > 0) {
                          Map<String,Object> mapId = new HashMap<String, Object>();
                          for (Map<String, Object> map1 : mapList3) {
                             String ordId = map1.get("orderId").toString();
                             String relateId = map1.get("relateDeliveryProductId").toString();
                             if(mapId.get(ordId)!=null){
                                 
                             }
                          }
                          
                          List<StockUpContractCostProductModel> productList = new ArrayList<StockUpContractCostProductModel>();
                          for (Map<String, Object> map2 : mapList3) {
                              String salecontractId = map2.get("salecontractId").toString();
                              String relateId = map2.get("relateDeliveryProductId").toString();
                              
                              
                              StockUpContractCostProductModel costProduct = new StockUpContractCostProductModel();
                              costProduct.setOrderId(map2.get("orderId").toString());
                              costProduct.setProductTypeName(map2.get("productTypeName").toString());
                              costProduct.setStockUpContractCostModel(contractCostModel);
                              costProduct.setProductPartnerName(map2.get("productPartnerName").toString());
                              costProduct.setProductName(map2.get("productName").toString());
                              try {
                                  if (map2.get("serviceEndDate")!=null) {
                                      costProduct.setServiceStartDate(sdf.parse(map2.get("serviceEndDate").toString()));
                                  }
                                  if (map2.get("serviceStartDate")!=null) {
                                      costProduct.setServiceEndDate(sdf.parse(map2.get("serviceStartDate").toString()));
                                  }
                              } catch (ParseException e) {
                                  // TODO Auto-generated catch block
                                  e.printStackTrace();
                              }
                              int cpQue = Integer.parseInt(map2.get("cpQua").toString());
                              int opQue = Integer.parseInt(map2.get("opQua").toString());
                              BigDecimal unitPrice = new BigDecimal(map2.get("unitPrice").toString());
                              BigDecimal total=new BigDecimal(0.00);
                              if(cpQue>opQue){
                                  costProduct.setQuantity(opQue);
                                  total = unitPrice.multiply(new BigDecimal(opQue));
                              }else{
                                  costProduct.setQuantity(cpQue);
                                  total = unitPrice.multiply(new BigDecimal(cpQue));
                              }
                              //costProduct.setQuantity(Integer.parseInt(map2.get("quantity").toString()));
                              costProduct.setUnitPrice(new BigDecimal(map2.get("unitPrice").toString()));
                              costProduct.setTotalPrice(total);
                              costProduct.setBhSaleContractId(map2.get("bhsaleContractId").toString());
                              costProduct.setRelateDeliveryProductId(Long.parseLong(map2.get("relateDeliveryProductId").toString()));
                              costProduct.setCpSaleContractId(map2.get("salecontractId").toString());
                              productList.add(costProduct);
                          }
                          stockUpContractCostProductService.saveOrUpdateAll(contractCostModel, productList);
                    }*/
                }

            }
        }

    }

    /**
     * @param orderId
     * @param salesId
     * @return
     */
    public List<Map<String, Object>> getOrderProductTotalAmount(Long orderId, Long salesId) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT SUM(p.`SubTotal`) amount FROM business_order_product p ");
        sb.append("WHERE p.`SaleContractId`='" + salesId + "' AND p.`OrderId`='" + orderId + "' ");
        sb.append("GROUP BY p.`SaleContractId`,p.`OrderId` ");
        List<Map<String, Object>> list = this.createSQLQuery(sb.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        return list;
    }

    /**
     * @param orderId
     * @return
     */
    public List<BusinessPayOrderModel> getPayModel(Long orderId) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT a.* FROM business_pay_apply a,business_payment_plan p, business_order o  ");
        sb.append("WHERE o.`id`=p.`OrderId` AND p.`PayOrderId`=a.`id` AND o.`id`='" + orderId + "' ");
        List<BusinessPayOrderModel> list = this.createSQLQuery(sb.toString()).addEntity(BusinessPayOrderModel.class).list();
        return list;
    }

    /**
     * @param orderId
     * @return
     */
    public List<InterPurchasModel> getInterPurch(Long orderId) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT s.* FROM business_inter_purchas s ,`order_interpurchas_map` m ");
        sb.append("WHERE s.`id`=m.`business_inter_purchas` AND m.`business_order`='" + orderId + "'");
        List<InterPurchasModel> list = this.createSQLQuery(sb.toString()).addEntity(InterPurchasModel.class).list();
        return list;
    }

}
