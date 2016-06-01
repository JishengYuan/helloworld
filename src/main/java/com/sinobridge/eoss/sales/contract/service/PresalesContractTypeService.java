/*
 * FileName: PresalesContractTypeService.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.sales.contract.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinobridge.base.core.exception.SinoRuntimeException;
import com.sinobridge.base.core.service.DefaultBaseService;
import com.sinobridge.eoss.sales.contract.dao.PresalesContractTypeDao;
import com.sinobridge.eoss.sales.contract.model.PresalesContractTypeModel;

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
 * 2015年11月11日 上午9:35:09          guo_kemeng        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Service
@Transactional
public class PresalesContractTypeService extends DefaultBaseService<PresalesContractTypeModel,PresalesContractTypeDao>{

    /**
     * <code>getTypeListByState</code>
     * 根据state得到合同分类
     * @param state
     * @return
     * @since   2015年11月11日    guokemenng
     */
    public List<PresalesContractTypeModel> getTypeListByState(Short state){
        String hql = "from PresalesContractTypeModel where type = '"+state+"'";
        return getDao().find(hql);
    }
    
    /**
     * <code>updatePresaleType</code>
     * 同步合同类型产讯条件
     * @param request
     * @param response
     * @return
     * @since   2015年11月11日    guokemenng
     */
    public void updatePresaleType(){

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ( ");
        sb.append("SELECT * FROM ( ");
        sb.append("SELECT p.`customerType` typeName,3 AS tt FROM `sales_precontract` p GROUP BY p.`customerType` ");
        sb.append(") t LEFT JOIN `sales_precontract_type` p ON p.name = t.typeName ");
        sb.append(") t WHERE t.name IS NULL ");
        try {
            Query query = getDao().createSQLQuery(sb.toString());
            query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
            @SuppressWarnings("unchecked")
            List<Map<String,Object>> mapList = query.list();
            List<PresalesContractTypeModel> typeList = new ArrayList<PresalesContractTypeModel>();
            for(int i = 0;i < mapList.size();i++){
                Map<String,Object> m = mapList.get(i);
                PresalesContractTypeModel type = new PresalesContractTypeModel();
                type.setCreateTime(new Date());
                if(m.get("typeName") != null){
                    type.setName(m.get("typeName").toString());
                }
                type.setType(Short.parseShort(m.get("tt").toString()));
                typeList.add(type);
            }
            getDao().saveOrUpdateAll(typeList);
        } catch (SinoRuntimeException e) {
            throw new SinoRuntimeException("操作失败");
        }
    }
    
}
