package com.sinobridge.eoss.business.interPurchas.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sinobridge.base.core.dao.imp.DefaultBaseDao;
import com.sinobridge.base.core.utils.PaginationSupport;
import com.sinobridge.eoss.business.interPurchas.model.InterPurchasModel;
import com.sinobridge.eoss.business.interPurchas.model.InterPurchasProductModel;
import com.sinobridge.eoss.business.order.constant.BusinessOrderContant;
import com.sinobridge.eoss.business.order.dao.BusinessOrderProductDao;
import com.sinobridge.eoss.business.order.model.BusinessOrderProductModel;

@SuppressWarnings("unchecked")
@Repository
public class InterPurchasDao extends DefaultBaseDao<InterPurchasModel, Long> {

    @Autowired
    private BusinessOrderProductDao businessOrderProductDao;

    /**放弃提交
     * @param id
     */
    public void endInterPurchas(Long id) {
        // TODO Auto-generated method stub
        Object[] param = new Object[2];
        String sql = "update InterPurchasModel set purchasStatus=? where id=?";
        param[0] = BusinessOrderContant.NFF_CG;
        param[1] = id;
        this.executeSql(sql, param);
    }

    /**更新状态为审批通过,处理状态为等待下单
     * @param id
     */
    public void purchasStatusTG(Long id) {
        Object[] param = new Object[3];
        String sql = "update InterPurchasModel set purchasStatus=?, interOrderStatus=? where id=?";
        param[0] = BusinessOrderContant.INTERPURCHAS_OK;
        param[1] = BusinessOrderContant.INTER_ORDER_WAIT;
        param[2] = id;
        executeSql(sql, param);
    }

    /**查询用户名，和ID
     * @return
     */
    public List<Map<String, Object>> getCreator() {
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        String sql = "select staffId,staffName from sys_staff ";
        Query query = this.createSQLQuery(sql);
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List<Map<String, Object>> objList = query.list();
        for (Map<String, Object> objMap : objList) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", objMap.get("StaffId"));
            map.put("name", objMap.get("StaffName"));
            mapList.add(map);
        }
        return mapList;
    }

    /**內采列表
     * @param searchMap
     * @param parseInt
     * @param pageSize
     * @return
     */
    public PaginationSupport findInterPurchas(HashMap<String, Object> searchMap, int parseInt, Integer pageSize) {
        int start = 0;
        Object[] values = new Object[searchMap.size()];
        Object creatorId = searchMap.get(InterPurchasModel.CREATORID);
        Object purchasStatus = searchMap.get(InterPurchasModel.PURCHASSTATUS);
        Object interPurchasStatus = searchMap.get(InterPurchasModel.INTERORDERSTATUS);
        Object time = searchMap.get(InterPurchasModel.EXPECTEDTIME);

        String hql = "select c.* from business_inter_purchas c where 0=0";
        if (creatorId != null) {
            values[start++] = creatorId + "";
            hql += " and c." + InterPurchasModel.CREATORID + "=?";
        }
        if (purchasStatus != null) {
            values[start++] = purchasStatus + "";
            hql += " and c." + InterPurchasModel.PURCHASSTATUS + " =?";
        }
        if (interPurchasStatus != null) {
            values[start++] = interPurchasStatus + "";
            hql += " and c." + InterPurchasModel.INTERORDERSTATUS + "!=?";
        }
        if (time != null) {
            values[start++] = time + "";
            hql += " and c." + InterPurchasModel.EXPECTEDTIME + "=?";
        }
        hql += " order By c.id DESC";
        Query query = this.createSQLQuery(hql, values).addEntity(InterPurchasModel.class);
        //query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List<InterPurchasModel> objList = query.list();

        //除去没有产品的合同
        List<InterPurchasModel> inter = query.list();
        List<InterPurchasModel> showInter = new ArrayList<InterPurchasModel>();
        Iterator<InterPurchasModel> it = inter.iterator();
        while (it.hasNext()) {
            InterPurchasModel interPurchas = it.next();
            Set<InterPurchasProductModel> productSet = interPurchas.getInterPurchasProduct();
            Iterator<InterPurchasProductModel> its = productSet.iterator();
            int flat = 0;
            while (its.hasNext()) {
                InterPurchasProductModel s = its.next();
                List<BusinessOrderProductModel> order = businessOrderProductDao.getPurchasProductId(s.getId());
                if (order != null) {
                    int qua = 0;
                    for (int i = 0; i < order.size(); i++) {
                        BusinessOrderProductModel orderProduct = order.get(i);
                        qua += orderProduct.getQuantity();
                    }
                    qua = s.getQuantity() - qua;
                    if (qua > 0) {
                        flat = 1;
                    }
                } else {
                    flat = 1;
                }
            }
            if (flat == 1) {
                showInter.add(interPurchas);
            }
        }
        List<InterPurchasModel> interPur = new ArrayList<InterPurchasModel>();
        int num = 0;
        if (pageSize + parseInt < showInter.size()) {
            num = pageSize + parseInt;
        } else {
            num = showInter.size();
        }
        for (int i = parseInt; i < num; i++) {
            interPur.add(showInter.get(i));
        }
        PaginationSupport ps = new PaginationSupport(interPur, showInter.size(), pageSize, parseInt);
        return ps;
    }

    /**通过工单ID，查询內采
     * @param proId
     * @return
     */
    public InterPurchasModel findProId(String proId) {
        Long id = Long.parseLong(proId);
        Object[] param = new Object[1];
        String sql = "select * from business_inter_purchas where processInstanceId=?";
        param[0] = id;
        Query query = this.createSQLQuery(sql, param).addEntity(InterPurchasModel.class);
        List<InterPurchasModel> objList = query.list();
        InterPurchasModel inter = objList.get(0);
        return inter;
    }

}
