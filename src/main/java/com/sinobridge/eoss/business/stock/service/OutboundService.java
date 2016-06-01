package com.sinobridge.eoss.business.stock.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinobridge.base.core.exception.SinoRuntimeException;
import com.sinobridge.base.core.hibernate.id.IdentifierGeneratorImpl;
import com.sinobridge.base.core.service.DefaultBaseService;
import com.sinobridge.eoss.business.stock.dao.BusinessOutboundBillDao;
import com.sinobridge.eoss.business.stock.dao.OutboundDao;
import com.sinobridge.eoss.business.stock.model.OutboundModel;
import com.sinobridge.eoss.sales.contract.model.SalesContractModel;
import com.sinobridge.eoss.sales.contract.model.SalesContractProductModel;
import com.sinobridge.eoss.sales.contract.service.SalesContractService;
import com.sinobridge.systemmanage.vo.SystemUser;

/**
 *	出库表
 */
@Service
@Transactional
public class OutboundService extends DefaultBaseService<OutboundModel, OutboundDao> {

    @Autowired
    StockService stockService;

    @Autowired
    SalesContractService contractService;
    
    @Autowired
    BusinessOutboundBillDao outboundDao;

    /**
     * 删除入库信息
     * @param projectId
     * @throws DataAccessException
     */
    public void delProject(Long projectId) throws DataAccessException {
        this.getDao().delProject(projectId);
    }

    /**
     * <code>getOutboundSalesProduct</code>
     * 得到合同要出库的产品
     * @return
     * @since   2014年10月17日    guokemenng
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> getOutboundSalesProduct(HttpServletRequest request) {

        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();

        String salesId = request.getParameter("salesId");
        SalesContractModel sales = contractService.get(Long.parseLong(salesId));
        String contractCode = sales.getContractCode();
        Set<SalesContractProductModel> salesProductSet = sales.getSalesContractProductModel();
//        //合同订单
//        Set<BusinessOrderModel> salesOrderS = sales.getBusinessOrderModel();
//
//        String[] productNos = new String[salesProductSet.size()];
//        Iterator<SalesContractProductModel> itP = salesProductSet.iterator();
//        Map<String, Integer> productMap = new HashMap<String, Integer>();
//        int i = 0;
//        //合同产品
//        while (itP.hasNext()) {
//            SalesContractProductModel p = itP.next();
//            productNos[i] = p.getProductName();
//            productMap.put(p.getProductName(), p.getQuantity());
//            i++;
//        }
//        //合同订单号
//        String[] orders = new String[salesOrderS.size()];
//        Iterator<BusinessOrderModel> itO = salesOrderS.iterator();
//        int j = 0;
//        while (itO.hasNext()) {
//            BusinessOrderModel o = itO.next();
//            orders[j] = o.getOrderCode();
//            j++;
//        }
//        //合同订单产品
//        if (productNos.length > 0 && orders.length > 0) {
//
//            String param0 = "";
//            for (int m = 0; m < productNos.length; m++) {
//                if (m != productNos.length - 1) {
//                    param0 += "'" + productNos[m] + "',";
//                } else {
//                    param0 += "'" + productNos[m] + "'";
//                }
//            }
//            String param1 = "";
//            for (int m = 0; m < orders.length; m++) {
//                if (m != orders.length - 1) {
//                    param1 += "'" + orders[m] + "',";
//                } else {
//                    param1 += "'" + orders[m] + "'";
//                }
//            }
//
//            String sql = "select *,sum(StockNum) totalNum from business_stock where ProductCode in(" + param0 + ") and OrderCode in(" + param1 + ") and ContractCode is null group by ProductCode";
//            Query query = this.getDao().createSQLQuery(sql);
//            query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
//
//            List<Map<String, Object>> maoObjectList = query.list();
//
//            //组装需要出库的产品数据
//            for (Map<String, Object> stockMap : maoObjectList) {
//                Map<String, Object> map = new HashMap<String, Object>();
//                String orderCode = stockMap.get("OrderCode").toString();
//                String productNo = stockMap.get("ProductCode").toString();
//                Integer productNum = Integer.parseInt(stockMap.get("totalNum").toString());
//
//                Integer salesProductNum = productMap.get(productNo);
//                if (productMap.get(productNo) != null) {
//                    map.put("orderCode", orderCode);
//                    map.put("productNo", productNo);
//                    map.put("productNum", productNum);
//                    map.put("salesProductNum", salesProductNum);
//                    map.put("contractCode", contractCode);
//                    //                    map.put("storePlace", stockMap.get("StorePlace"));
//                    mapList.add(map);
//                }
//
//            }contractCode
//
//        }

        //判断合同已出库的产品
        
        String sql = "select productCode productCode,sum(productNum) productNum from business_outbound where contractCode = '"+contractCode+"' group by productCode";
        
        Map<String, Integer> outProductMap = new HashMap<String, Integer>();
//        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(OutboundModel.class);
//        detachedCriteria.add(Restrictions.eq(OutboundModel.CONTRACTCODE,contractCode));
//        List<OutboundModel> outList = this.findByCriteria(detachedCriteria);
        
        List<Map<String,Object>> mL = getDao().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        
        if(mL.size() > 0){
            for(Map<String,Object> m : mL){
                outProductMap.put(m.get("productCode").toString(), Integer.parseInt(m.get("productNum").toString()));
            }
        } 
        
        Iterator<SalesContractProductModel> itP = salesProductSet.iterator();
        while(itP.hasNext()){
            SalesContractProductModel p = itP.next();
            Integer pN = outProductMap.get(p.getProductName());
            if(pN != null){
                if(pN != p.getQuantity()){
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("productNo", p.getProductName());
                    map.put("productNum", pN);
                    map.put("salesProductNum", p.getQuantity());
                    map.put("contractCode", contractCode);
                    mapList.add(map);
                }
            } else {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("productNo", p.getProductName());
                map.put("productNum", 0);
                map.put("salesProductNum", p.getQuantity());
                map.put("contractCode", contractCode);
                mapList.add(map);
            }
        }
        
        return mapList;
    }

    /**
     * <code>doSalesOutboundProduct</code>
     * 合同产品出库
     * @param parameterMap
     * @param systemUser
     * @since   2014年10月18日    guokemenng
     */
    public void doSalesOutboundProduct(Map<String, String[]> parameterMap, SystemUser systemUser,String storePlace) {
        Long l = IdentifierGeneratorImpl.generatorLong();

        try {
            String[] contractCode = parameterMap.get("contractCode");
            String[] productCode = parameterMap.get("productCode");
            String[] productNum = parameterMap.get("productNum");

//            String[] orderCode = parameterMap.get("orderCode");

            List<OutboundModel> outList = new ArrayList<OutboundModel>();
            for (int i = 0; i < contractCode.length; i++) {
                OutboundModel m = new OutboundModel();
                m.setOutboundCode(l.toString());
                m.setOutboundTime(new Date());
                m.setContractCode(contractCode[i]);
                m.setProductCode(productCode[i]);
                m.setProductNum(Integer.parseInt(productNum[i]));
                m.setOutBoundPer(systemUser.getUserName());
                m.setArrivePlace(storePlace);
                outList.add(m);
            }
            getDao().saveOrUpdateAll(outList);
            
//            DetachedCriteria detachedCriteria = DetachedCriteria.forClass(StockModel.class);
//            detachedCriteria.add(Restrictions.in(StockModel.ORDERCODE,orderCode));
//            detachedCriteria.add(Restrictions.in(StockModel.PRODUCTCODE,productCode));
//            
//            List<StockModel> stockList = stockService.findByCriteria(detachedCriteria);
//            List<BusinessOutboundBill> outboundList = new ArrayList<BusinessOutboundBill>();
//            for(StockModel stock:stockList){
//                stock.setOutboundCode(l.toString());
//                stock.setOutboundTime(new Date());
//                stock.setContractCode(contractCode[0]);
//                stockService.update(stock);
//                
//                BusinessOutboundBill outBill = new BusinessOutboundBill();
//                outBill.setOutboundCode(l.toString());
//                outBill.setOutboundTime(new Date());
//                outBill.setProductCode(stock.getProductCode());
//                outBill.setProductNum(stock.getStockNum());
//                outBill.setProductSn(stock.getProductSn());
//                outboundList.add(outBill);
//                
//            }
//            
//            outboundDao.saveOrUpdateAll(outboundList);
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new SinoRuntimeException("操作失败");
        }
    }
}