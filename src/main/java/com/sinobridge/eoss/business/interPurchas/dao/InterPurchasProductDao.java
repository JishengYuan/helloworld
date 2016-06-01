package com.sinobridge.eoss.business.interPurchas.dao;

import org.springframework.stereotype.Repository;

import com.sinobridge.base.core.dao.imp.DefaultBaseDao;
import com.sinobridge.eoss.business.interPurchas.model.InterPurchasProductModel;

@Repository
public class InterPurchasProductDao extends DefaultBaseDao<InterPurchasProductModel, String>{

    /**
     * <code>deleteContactsBySupplierId</code>
     *
     * @param 删除原有的产品
     * @since   2014年5月21日    wangya
     */
    public void deleteProductByPurchasId(long id) {
        try{
            Long[] param = new Long[1];
            param[0] = id;
            String sql = "delete from business_inter_purchas_product where InterPurchasId = ?";
            this.createSQLQuery(sql, param).executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
