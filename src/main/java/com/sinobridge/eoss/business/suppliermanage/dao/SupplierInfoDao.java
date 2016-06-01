package com.sinobridge.eoss.business.suppliermanage.dao;

import java.util.List;
import java.util.Map;

import com.sinobridge.eoss.business.suppliermanage.model.SupplierInfoModel;
import com.sinobridge.base.core.dao.imp.DefaultBaseDao;

import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

@Repository
public class SupplierInfoDao extends DefaultBaseDao<SupplierInfoModel, Long>{

    /**
     * @param name
     * @return 
     */
    public int checkShortName(String name) {
       String sql="SELECT * FROM `bussiness_supplier` s WHERE s.`ShortName`='"+name+"' ";
       List<Map<String,Object>> listMap = this.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
       int flat=0;
       if(listMap.isEmpty()){
           flat=1;
       }
       return flat;
    }

    /**
     * @param supplierCode
     * @return
     */
    public int checkSupplierCode(String supplierCode) {
        String sql="SELECT * FROM `bussiness_supplier` s WHERE s.`SupplierCode`='"+supplierCode+"' ";
        List<Map<String,Object>> listMap = this.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        int flat=0;
        if(listMap.isEmpty()){
            flat=1;
        }
        return flat;
    }


}