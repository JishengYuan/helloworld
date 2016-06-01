package com.sinobridge.eoss.business.order.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.sinobridge.base.core.dao.imp.DefaultBaseDao;
import com.sinobridge.eoss.business.order.model.BusinessOrderProductModel;

@Repository
public class BusinessOrderProductDao extends DefaultBaseDao<BusinessOrderProductModel, String> {

    /**查询订单产品
     * @param orderId
     * @return
     */
    public List<BusinessOrderProductModel> getProductId(Long orderId) {
        //		String sql="";
        //    	sql="SELECT Id FROM  business_order_product  WHERE salesContractProductId="+orderId+"";
        String hql = "from BusinessOrderProductModel where salesContractProductModel.id = ?";
        //		List<BusinessOrderProductModel> rs = this.createSQLQuery(sql).list();
        Long[] param = new Long[1];
        param[0] = orderId;
        List<BusinessOrderProductModel> rs = this.find(hql, param);
        if (rs.size() == 0) {
            return null;
        } else {
            return rs;
        }
    }

    /**查询內采产品
     * @param orderId
     * @return
     */
    public List<BusinessOrderProductModel> getPurchasProductId(Long orderId) {
        // TODO Auto-generated method stub
        String hql = "from BusinessOrderProductModel where interPurchasProduct.id = ?";
        Long[] param = new Long[1];
        param[0] = orderId;
        List<BusinessOrderProductModel> rs = this.find(hql, param);
        if (rs.size() == 0) {
            return null;
        } else {
            return rs;
        }
    }

    /**查询订单产品
     * @param id
     * @return
     */
    public List<BusinessOrderProductModel> getSaleContract(Long id) {
        String hql = "from BusinessOrderProductModel where salesContractModel.id = ?";
        Long[] param = new Long[1];
        param[0] = id;
        List<BusinessOrderProductModel> rs = this.find(hql, param);
        if (rs.size() == 0) {
            return null;
        } else {
            return rs;
        }
    }

    /**删除产品
     * @param id
     */
    public void deleteOrderProduct(Long id) {
        Long[] param = new Long[1];
        param[0] = id;
        String sql = "delete from business_order_product where OrderId = ?";
        createSQLQuery(sql, param).executeUpdate();
    }

    /**查询订单产品
     * @param orderId 內采ID
     * @return
     */
    public List<BusinessOrderProductModel> getProductOrder(Long orderId) {
        String hql = "from BusinessOrderProductModel where interPurchasProduct.id = ?";
        Long[] param = new Long[1];
        param[0] = orderId;
        List<BusinessOrderProductModel> rs = this.find(hql, param);
        if (rs.size() == 0) {
            return null;
        } else {
            return rs;
        }
    }

    /**查询合同产品数量
     * @param id
     * @param productNo
     * @return
     */
    public int findQuantity(long id) {
        Object[] param = new Long[1];
        String sql = "select Quantity from sales_contract_product where id=?";
        param[0] = id;
        Query query = this.createSQLQuery(sql, param).setReadOnly(true);
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List<Map<String, Object>> objList = query.list();
        String num = objList.get(0).get("Quantity").toString();
        return Integer.parseInt(num);
    }

}
