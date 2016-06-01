package com.sinobridge.eoss.business.stock.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.sinobridge.base.core.dao.imp.DefaultBaseDao;
import com.sinobridge.eoss.business.stock.model.StockModel;

/**
 *	库存表
 */
@Repository
public class StockDao extends DefaultBaseDao<StockModel, String>{
	
	/**
	 * 根据id修改库存状态
	 * @param projectId
	 * @throws DataAccessException
	 */
	public void upInProject(Integer stockState,String inboundTime,Long projectId) throws DataAccessException {
		StringBuffer hql = new StringBuffer();
		hql.append(" update business_stock set stockState='").append(stockState);
		if(inboundTime!=null&&!"".equals(inboundTime)){
			hql.append("',inboundTime='").append(inboundTime).append("'");
		}else{
			hql.append("',inboundTime=").append(inboundTime);
		}
		hql.append(" where id=").append(projectId);
		this.createSQLQuery(hql.toString()).executeUpdate();
	}
	
	public void upOutProject(Integer stockState,String outboundTime,Long projectId) throws DataAccessException {
		StringBuffer hql = new StringBuffer();
		hql.append(" update business_stock set stockState='").append(stockState);
		if(outboundTime!=null&&!"".equals(outboundTime)){
			hql.append("',outboundTime='").append(outboundTime).append("'");
		}else{
			hql.append("',outboundTime=").append(outboundTime);
		}
		hql.append(" where id=").append(projectId);
		this.createSQLQuery(hql.toString()).executeUpdate();
	}
	
	/**
	 * 根据id修改库存设备出库信息
	 * @param stockState
	 * @param projectId
	 * @param outboundCode
	 * @throws DataAccessException
	 */
	public void upProject(Integer stockState,String outboundCode,String outboundTime,Long projectId) throws DataAccessException {
		StringBuffer hql = new StringBuffer();
		hql.append(" update business_stock set stockState=").append(stockState);
		hql.append(",outboundCode='").append(outboundCode).append("',outboundTime='").append(outboundTime).append("' where id=").append(projectId);
		this.createSQLQuery(hql.toString()).executeUpdate();
	}
	
}