package com.sinobridge.eoss.sales.manage.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.sinobridge.base.core.dao.imp.DefaultBaseDao;
import com.sinobridge.eoss.sales.manage.model.FinanceContractsModel;

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
 * 2015年1月16日 上午10:48:01          guo_kemeng        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Repository
public class FinanceContractsDao extends DefaultBaseDao<FinanceContractsModel, Long> {

    /**
     * @param id
     * @return
     */
    public List<FinanceContractsModel> findfinanceModel(long id) {
        Object param[] = new Object[1];
        String sql = "select * from sales_finance_contract where orderId=? ";
        param[0] = id;
        Query query = this.createSQLQuery(sql, param).addEntity(FinanceContractsModel.class);
        List<FinanceContractsModel> list = query.list();
        return list;
    }

}
