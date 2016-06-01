package com.sinobridge.eoss.business.stock.service;

import java.util.Map;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinobridge.base.core.service.DefaultBaseService;
import com.sinobridge.base.core.utils.PaginationSupport;
import com.sinobridge.eoss.business.stock.dao.StockDao;
import com.sinobridge.eoss.business.stock.model.StockModel;

/**
 *	库存表
 */
@Service
@Transactional
public class StockService extends DefaultBaseService<StockModel, StockDao> {

    /**
     * 根据id修改库存状态
     * @param projectId
     * @throws DataAccessException
     */
    public void upInProject(Integer stockState, String inboundTime, Long projectId) throws DataAccessException {
        this.getDao().upInProject(stockState, inboundTime, projectId);
    }

    public void upOutProject(Integer stockState, String outboundTime, Long projectId) throws DataAccessException {
        this.getDao().upOutProject(stockState, outboundTime, projectId);
    }

    /**
     * 出库操作
     * @param stockState
     * @param outboundCode
     * @param outboundTime
     * @param projectId
     * @throws DataAccessException
     */
    public void upProject(Integer stockState, String outboundCode, String outboundTime, Long projectId) throws DataAccessException {
        this.getDao().upProject(stockState, outboundCode, outboundTime, projectId);
    }
    
    /**
     * <code>getStockPageList</code>
     *
     * @param params
     * @param startIndex
     * @param pageSize
     * @return
     * @since   2014年10月23日    guokemenng
     */
    public PaginationSupport getStockPageList(Map<String,Object> params,Integer startIndex,Integer pageSize){
       
        StringBuilder sb = new StringBuilder();
        sb.append("select s.id,t.TypeName,pt.PartnerFullName,s.ProductCode,s.StockNum productNum from business_stock s left join business_productmodel p on p.ProductModel = s.ProductCode left join business_partnerinfo pt on pt.id = p.PartnerId left join business_producttype t on t.id = p.ProductType where 0=0");
        
        Object[] param = new Object[params.size()];
        int i = 0;
        if(params.get("typeId") != null){
            param[i++] = params.get("typeId");
            sb.append(" and t.id = ?");
        }
        if(params.get("partnerId") != null){
            param[i++] = params.get("partnerId");
            sb.append(" and pt.id = ?");
        }
        if(params.get("productCode") != null){
            param[i++] = "%"+params.get("productCode")+"%";
            sb.append(" and s.ProductCode like ?");
        }
        
        sb.append(" group by s.ProductCode");
        
        Query query = null;
        if(param.length > 0){
            query = getDao().createSQLQuery(sb.toString(), param);
        } else {
            query = getDao().createSQLQuery(sb.toString());
        }
        query.setFirstResult(startIndex);
        query.setMaxResults(pageSize);
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        
        PaginationSupport pg = new PaginationSupport(query.list(),getStockListCount(sb.toString(),param));
        return pg;
    }

    /**
     * <code>getStockListCount</code>
     *
     * @param sql
     * @param params
     * @return
     * @since   2014年10月23日    guokemenng
     */
    public Integer getStockListCount(String sql,Object[] params){
        sql = "select count(*) from (" + sql + " ) as total";
        if(params.length > 0){
            return Integer.valueOf(getDao().createSQLQuery(sql,params).list().get(0).toString()).intValue();
        } else {
            return Integer.valueOf(getDao().createSQLQuery(sql).list().get(0).toString()).intValue();
        }
    }
    
}