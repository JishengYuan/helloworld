/*
 * FileName: SalesCostService.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.sales.finalCost.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinobridge.base.core.service.DefaultBaseService;
import com.sinobridge.base.core.utils.PaginationSupport;
import com.sinobridge.eoss.sales.finalCost.dao.SalesCostDao;
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
 * 2015年9月7日 上午10:46:40          vermouth        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Service
@Transactional
public class SalesCostService extends DefaultBaseService<SalesCostModel, SalesCostDao>{

    
    /**添加资金成本
     * @param time 
     * @param preMonday
     * @param nowTime
     * @param timeStr 
     * @return
     */
    public Map<String, Object> newSaleCost(String time, String preMonday, Date nowTime, String timeStr) {
        Map<String ,Object> m = new HashMap<String, Object>();
        //备货单算的
      /*  //普通合同
         List<Map<String,Object>> newSaleCost = getDao().getNewSaleCost(preMonday,timeStr);
         //关联备货合同
        List<Map<String,Object>> saleCostBH = getDao().getSaleCostBh(preMonday,timeStr); 
        String msg = getInsertCost(time,newSaleCost,nowTime);
        String msg2 = getInsertCost(time,saleCostBH,nowTime);
        m.put("newSaleCost", msg);
        m.put("saleCostBH", msg2);*/
        
        
        //老代码，备货没单算时的
        List<Map<String,Object>> newSaleCost = getDao().getNewSaleCost(preMonday,timeStr);
        String msg = getInsertCost(time,newSaleCost,nowTime);
        m.put("newSaleCost", msg);
        return m;
    }

    /**
     * @param time
     * @param newSaleCost
     * @param nowTime
     */
    public String getInsertCost(String time, List<Map<String, Object>> newSaleCost, Date nowTime) {
        List<SalesCostModel> costs = new ArrayList<SalesCostModel>();
       try{
        for(Map<String, Object> m : newSaleCost){
            //判断成本是否为空，若为空则跳过
            if(m.get("orderPay")!=null){
                BigDecimal payStr = new BigDecimal(m.get("orderPay").toString());
                
                BigDecimal salesReceive = new BigDecimal(0.00);
                if(m.get("salesRevice")!=null){
                    salesReceive=new BigDecimal(m.get("salesRevice").toString());
                }
                //判断回款是否小于成本，若小于，插入数据
                if(payStr.compareTo(salesReceive)==1){
                    SalesCostModel cost = new SalesCostModel();
                    cost.setDateInt(Integer.parseInt(time));
                    cost.setOrderPay(payStr);
                    cost.setCreateTime(nowTime);
                    cost.setSalesReceive(salesReceive);
                    cost.setSalesContractId(Long.parseLong(m.get("SaleContractId").toString()));
                    BigDecimal costAmount = payStr.subtract(salesReceive);
                    cost.setCost(costAmount.multiply(new BigDecimal(0.01)).setScale(2,BigDecimal.ROUND_HALF_UP));
                    costs.add(cost);
                }
            }
        }
        getDao().saveOrUpdateAll(costs);
       }catch (Exception e) {
           e.printStackTrace();
           return "false";
       }
       return "true";
    }

    /**普通合同列表
     * @param searchMap
     * @param i
     * @param pageSize
     * @return
     */
    public PaginationSupport findPageBySearchMap(HashMap<String, Object> searchMap, int pageNo, Integer pageSize) {
        return getDao().findPageBySearchMap(searchMap, pageNo, pageSize);
    }

    /**关联备货合同列表
     * @param searchMap
     * @param i
     * @param pageSize
     * @return
     */
    public PaginationSupport findSpecialContract(HashMap<String, Object> searchMap, int pageNo, Integer pageSize) {
        return getDao().findSpecialContract(searchMap, pageNo, pageSize);
    }
    /**详情：查询合同
     * @param code
     * @param time 
     * @return
     */
    public List<Map<String, Object>> getFindSales(String salesId, String time) {
        return getDao().getFindSales(salesId,time);
    }

    /**详情：查询回款
     * @param id
     * @param time 
     * @return
     */
    public List<Map<String, Object>> getFindRevice(Long id, String time) {
        return getDao().getFindRevice(id,time);
    }

    /**详情：查询订单
     * @param id
     * @return
     */
    public List<Map<String, Object>> findOrder(Long id,String time) {
        return getDao().findOrder(id, time);
    }

    /**详情：查询订单相关合同
     * @param time 
     * @param object
     * @return
     */
    public List<Map<String, Object>> getFindOrderSale(Object orderId, String time) {
        return getDao().getFindOrderSale(orderId,time);
    }

    /**当月是否计算过成本
     * @param time
     * @return
     */
    public int findTime(String time) {
        int falt = getDao().findTime(time);
        return falt;
    }

    /**查询成本信息
     * @param salesId
     * @param time
     * @return
     */
    public List<Map<String, Object>> findSalesCost(String salesId, String time) {
        List<Map<String, Object>> sales = getDao().findSalesCost(salesId,time);
        return sales;
    }

    /**保存成本修改
     * @param id
     * @param orderpay
     * @param salesReceive
     * @param cost
     * @return
     */
    public String doUpdate(String id, String orderpay, String salesReceive, String cost) {
        SalesCostModel costModel = this.get(Long.parseLong(id));
        costModel.setCost(new BigDecimal(cost));
        costModel.setSalesReceive(new BigDecimal(salesReceive));
        costModel.setOrderPay(new BigDecimal(orderpay));
        try{
        getDao().saveOrUpdate(costModel);
        }catch (Exception e) {
            e.printStackTrace();
            return "false";
        }
        return "true";
    }





}
