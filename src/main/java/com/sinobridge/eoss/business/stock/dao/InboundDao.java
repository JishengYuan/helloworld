package com.sinobridge.eoss.business.stock.dao;

import org.hibernate.Query;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.sinobridge.base.core.dao.imp.DefaultBaseDao;
import com.sinobridge.eoss.business.stock.model.InboundModel;

/**
 *	入库表
 */
@Repository
public class InboundDao extends DefaultBaseDao<InboundModel, String>{
	
	/**
	 * 删除入库信息
	 * @param projectId
	 * @throws DataAccessException
	 */
	public void delProject(Long projectId) throws DataAccessException {
    	Query q = getSession().createQuery("delete from InboundModel where id=?");
		q.setParameter(0, projectId);
		q.executeUpdate();
    }
	
}