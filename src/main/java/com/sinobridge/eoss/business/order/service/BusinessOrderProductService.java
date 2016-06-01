package com.sinobridge.eoss.business.order.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinobridge.base.core.service.DefaultBaseService;
import com.sinobridge.eoss.business.interPurchas.model.InterPurchasModel;
import com.sinobridge.eoss.business.interPurchas.model.InterPurchasProductModel;
import com.sinobridge.eoss.business.interPurchas.service.InterPurchasProductService;
import com.sinobridge.eoss.business.interPurchas.service.InterPurchasService;
import com.sinobridge.eoss.business.order.constant.BusinessOrderContant;
import com.sinobridge.eoss.business.order.dao.BusinessOrderProductDao;
import com.sinobridge.eoss.business.order.model.BusinessOrderModel;
import com.sinobridge.eoss.business.order.model.BusinessOrderProductModel;
import com.sinobridge.eoss.sales.contract.model.SalesContractModel;
import com.sinobridge.eoss.sales.contract.model.SalesContractProductModel;
import com.sinobridge.eoss.sales.contract.service.SalesContractProductService;
import com.sinobridge.eoss.sales.contract.service.SalesContractService;

@Service
@Transactional
public class BusinessOrderProductService extends DefaultBaseService<BusinessOrderProductModel, BusinessOrderProductDao> {
    @Autowired
    private SalesContractService salesContractService;
    @Autowired
    private SalesContractProductService salesContractProduct;
    @Autowired
    private InterPurchasService interPurchasService;
    @Autowired
    private InterPurchasProductService interPurchasProductService;

    String pattern = "yyyy-MM-dd HH:mm";
    //仅有日期的格式
    String DatePattern = "yyyy-MM-dd";

    /**
     * <code>saveSupplierContacts</code>
     * 保存订单产品
     * @param request 
     * @param infoId
     * @param gridDataList2
     * @param name 
     * @since   2014年6月6日   wangya
     */
    public Set<BusinessOrderProductModel> saveProducts(BusinessOrderModel orderModel, List<Object> gridDataList) {
        Set<BusinessOrderProductModel> businessOrderProductModels = new HashSet<BusinessOrderProductModel>();
        try {
            if (gridDataList != null) {
                for (int i = 0; i < gridDataList.size(); i++) {//加了非空判断
                    Map<String, Object> map = StringToMap(gridDataList.get(i).toString());//格式转换
                    if (map != null) {
                        BusinessOrderProductModel products = new BusinessOrderProductModel();
                        products.setBusinessOrder(orderModel);
                        saveProduct(products, map);
                        businessOrderProductModels.add(products);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return businessOrderProductModels;

    }

    /**
     * 根据合同ID，查询所有合同中，不同类型设备数量的总和
     * @param contractids
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> getAllProcductsbycontractId(String contractids) {
        List<Map<String, Object>> rs = null;
        if (contractids != null && contractids.length() > 1) {
            contractids = contractids.substring(0, contractids.length() - 1);
        }

        String sql = "SELECT productNo,SUM(proNums) proNums FROM (" + " SELECT t.`ProductName` productNo,SUM(t.`Quantity`) proNums FROM sales_contract_product t WHERE  t.`SaleContractId` IN (" + contractids + ") GROUP BY t.`ProductNo`" + " UNION ALL" + " SELECT t.`ProductNo`, SUM(t.`Quantity`) proNums FROM business_order_product t WHERE  t.`SaleContractId` IN (" + contractids + ") GROUP BY t.`ProductNo`" + " ) m GROUP BY m.productNo";
        Query query = this.getDao().createSQLQuery(sql);
        rs = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();

        return rs;
    }

    /**
     * <code>saveSupplierContacts</code>
     * 执行保存订单产品操作
     * @param orderModel 
     * @param map
     * @param name 
     * @since   2014年6月6日    wangya
     */
    public void saveProduct(BusinessOrderProductModel products, Map<String, Object> map) {
        try {
            String productNo = map.get("column1").toString();
            String vendorCode = map.get("column2").toString();
            Double price = Double.parseDouble(map.get("column4").toString());
            BigDecimal unitPrice = BigDecimal.valueOf(price);
            int quantity = Integer.parseInt(map.get("column3").toString());
            Double subnum = Double.parseDouble(map.get("column5").toString());
            BigDecimal sub = BigDecimal.valueOf(subnum);
            String id = map.get("column6").toString();
            String productId = map.get("column7").toString();
            String mark = map.get("column8").toString();
            String productType = map.get("column10")!=null?map.get("column10").toString():"";
            /*  String serviceType = map.get("column9").toString();
              String serviceStartTimeString = map.get("column11") != null ? map.get("column11").toString() : map.get("column11") + "";
              String serviceEndTimeString = map.get("column12") != null ? map.get("column12").toString() : map.get("column12") + "";*/
            /*Date serviceStartTime = new Date();
            Date serviceEndTime = new Date();*/
            /* if (!serviceStartTimeString.equals("null")) {
                 if (serviceStartTimeString.indexOf(":") > 0) {
                     serviceStartTime = DateUtils.convertStringToDate(serviceStartTimeString, pattern);
                 } else {
                     serviceStartTime = DateUtils.convertStringToDate(serviceStartTimeString, DatePattern);
                 }
                 products.setServiceStartTime(serviceStartTime);
             }
             if (!serviceEndTimeString.equals("null")) {
                 if (serviceEndTimeString.indexOf(":") > 0) {
                     serviceEndTime = DateUtils.convertStringToDate(serviceEndTimeString, pattern);
                 } else {
                     serviceEndTime = DateUtils.convertStringToDate(serviceEndTimeString, DatePattern);
                 }
                 products.setServiceEndTime(serviceEndTime);
             }*/
            /*  float exchangeRate = 0;
              float ListPrice = 0;*/
            if (mark.equals(BusinessOrderContant.SELECTCONTRACT)) {
                SalesContractModel salesContract = salesContractService.get(Long.parseLong(id));
                SalesContractProductModel contractProduct = salesContractProduct.get(Long.parseLong(productId));
                products.setSalesContractModel(salesContract);
                products.setSalesContractProductModel(contractProduct);
            }
            if (mark.equals(BusinessOrderContant.SELCETINTERPURCHAS)) {
                InterPurchasModel interPurchas = interPurchasService.get(Long.parseLong(id));
                InterPurchasProductModel interProduct = interPurchasProductService.get(Long.parseLong(productId));
                products.setInterPurchas(interPurchas);
                products.setInterPurchasProduct(interProduct);
            }
            products.setProductNo(productNo);
            products.setVendorCode(vendorCode);
            products.setUnitPrice(unitPrice);
            products.setSubTotal(sub);
            products.setQuantity(quantity);
            products.setMark(mark);
            //products.setServiceType(serviceType);
            products.setProductType(productType);
            this.create(products);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * <code>StringToMap</code>
     *
     * @param mapText
     * @return
     * @since   2014年6月6日   wangya
     */
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

    /**查询订单产品
     * @param s
     * @return
     */
    public List<BusinessOrderProductModel> getOrderProductId(SalesContractProductModel s) {
        // TODO Auto-generated method stub
        Long orderId = s.getId();
        List<BusinessOrderProductModel> businessOrderProductModel = getDao().getProductId(orderId);
        return businessOrderProductModel;
    }

    /**查询內采产品
     * @param s
     * @return
     */
    public List<BusinessOrderProductModel> getPurchasProductId(InterPurchasProductModel s) {
        // TODO Auto-generated method stub
        Long orderId = s.getId();
        List<BusinessOrderProductModel> businessOrderProductModel = getDao().getPurchasProductId(orderId);
        return businessOrderProductModel;
    }

    /**
     * <code>deleteContactsBySupplierId</code>
     *
     * @param 删除原有的产品
     * @since   2014年5月21日    wangya
     */
    public void deleteOrderProduct(Long id) {
        getDao().deleteOrderProduct(id);
    }

    /**查询订单产品
     * @param id 合同id
     * @return
     */
    public List<BusinessOrderProductModel> getOrderProduct(Long id) {
        List<BusinessOrderProductModel> businessOrderProductModel = getDao().getSaleContract(id);
        return businessOrderProductModel;
    }

    /**
     * <code>getProductOrder</code>
     *
     * @param 得到订单产品
     * @since   2014年11月6日    wangya
     */
    public List<BusinessOrderProductModel> getProductOrder(InterPurchasProductModel pro) {
        // TODO Auto-generated method stub
        Long orderId = pro.getId();
        List<BusinessOrderProductModel> businessOrderProductModel = getDao().getProductOrder(orderId);
        return businessOrderProductModel;

    }

    /**查找合同产品数量
     * @param productNo 
     * @param l
     * @return
     */
    public int findQuantity(long id) {
        int num = getDao().findQuantity(id);
        return num;
    }

}
