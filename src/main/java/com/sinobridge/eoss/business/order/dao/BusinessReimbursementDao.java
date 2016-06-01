package com.sinobridge.eoss.business.order.dao;

import org.springframework.stereotype.Repository;

import com.sinobridge.base.core.dao.imp.DefaultBaseDao;
import com.sinobridge.eoss.business.order.model.BusinessReimbursementModel;

@Repository
public class BusinessReimbursementDao  extends DefaultBaseDao<BusinessReimbursementModel, Long>{

	/**删除原有的计划
 * @param id
 */
public void deletePlan(Long id) {
		 Long[] param = new Long[1];
	        param[0] = id;
	        String sql = "delete from business_reimbursement where rbmApplyId=?";
	        createSQLQuery(sql, param).executeUpdate();
	}


}
