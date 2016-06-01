package com.sinobridge.eoss.business.stock.service;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinobridge.base.core.service.DefaultBaseService;
import com.sinobridge.eoss.business.stock.dao.InboundDao;
import com.sinobridge.eoss.business.stock.model.InboundModel;

/**
 *	入库表
 */
@Service
@Transactional
public class InboundService extends DefaultBaseService<InboundModel, InboundDao>{
	
	/**
	 * 删除入库信息
	 * @param projectId
	 * @throws DataAccessException
	 */
	public void delProject(Long projectId) throws DataAccessException {
		this.getDao().delProject(projectId);
    }
	
}