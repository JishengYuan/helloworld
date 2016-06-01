package com.sinobridge.eoss.business.stock.dao;

import com.sinobridge.eoss.business.stock.model.OutboundModel;
import com.sinobridge.base.core.dao.imp.DefaultBaseDao;

import org.hibernate.Query;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

/**
 *	出库表
 */
@Repository
public class OutboundDao extends DefaultBaseDao<OutboundModel, String>{
	
	/**
	 * 删除出库信息
	 * @param projectId
	 * @throws DataAccessException
	 */
	public void delProject(Long projectId) throws DataAccessException {
    	Query q = getSession().createQuery("delete from OutboundModel where id=?");
		q.setParameter(0, projectId);
		q.executeUpdate();
    }
	
}