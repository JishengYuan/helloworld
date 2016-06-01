package com.sinobridge.eoss.business.inventory.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.transform.Transformers;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.sinobridge.base.core.dao.imp.DefaultBaseDao;
import com.sinobridge.eoss.business.inventory.model.InventoryModel;

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
 * 2015年2月3日 下午4:55:26          guo_kemeng        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Repository
public class InventoryDao extends DefaultBaseDao<InventoryModel,Long>{
    
 	/**
 	 * 查询出所有在库的设备
 	 * @return
 	 * @throws DataAccessException
 	 * @since   2015年2月12日    liyx
 	 */
    public List<InventoryModel> findAllProduct() throws DataAccessException {	
    	List<InventoryModel> list = find("from InventoryModel where state=0 ");
        if (list == null || list.size() == 0) {
            return null;
        }
        return list;
    }

  

}
