package com.sinobridge.eoss.business.order.dao;

import org.springframework.stereotype.Repository;

import com.sinobridge.base.core.dao.imp.DefaultBaseDao;
import com.sinobridge.eoss.business.order.constant.BusinessOrderContant;
import com.sinobridge.eoss.business.order.model.CloseContractModel;

@Repository
public class CloseContractDao extends DefaultBaseDao<CloseContractModel , Long>{

    /**更新合同状态表的订单状态
     * @param salesId
     */
    public void closeContractOk(Long salesId) {
        Object[] param = new Object[2];
        String sql = "update SalesContractStatusModel set orderStatus=? where salecontractId=?";
        param[0] = BusinessOrderContant.ORDER_STATUSE_OK;
        param[1] = salesId;
        executeSql(sql, param);
    }

}
