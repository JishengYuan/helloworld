/*
 * FileName: BaseProductmodelService.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.business.projectmanage.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinobridge.base.core.service.DefaultBaseService;
import com.sinobridge.base.core.utils.PaginationSupport;
import com.sinobridge.eoss.business.projectmanage.dao.BaseProductmodelDao;
import com.sinobridge.eoss.business.projectmanage.model.BaseProductModel;
import com.sinobridge.systemmanage.util.StringUtil;

/**
 * <code>BaseProductmodelService</code>
 * 
 * @version  1.0
 * @author  guokemenng
 * @since 1.0  2013-11-25
 */
@Service
@Transactional
public class BaseProductModelService extends DefaultBaseService<BaseProductModel, BaseProductmodelDao> {

    @Autowired
    private BaseProductmodelDao baseProductmodelDao;

    /**
     * <code>findPageByCriteria</code>
     *
     * @param detachedCriteria
     * @param pageSize
     * @param startIndex
     * @return
     * @since   2013-12-2    guokemenng
     */
    @Override
    public PaginationSupport findPageByCriteria(DetachedCriteria detachedCriteria, Integer pageSize, Integer startIndex) {
        return baseProductmodelDao.findPageByCriteria(detachedCriteria, pageSize, startIndex);
    }

    /**
     * <code>saveOrUpdate</code>
     *
     * @param productModel
     * @since   2013-12-4    guokemenng
     */
    @Override
    public void saveOrUpdate(BaseProductModel productModel) {
        Short deleteFlag = 1;
        productModel.setDeleteFlag(deleteFlag);
        baseProductmodelDao.saveOrUpdate(productModel);
    }

    /**
     * <code>getMapList</code>
     * 以map形式得到实体类集合
     * @return
     * @since   2013-12-7    guokemenng
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> getMapList(String startIndex, String length, String searchParam) {
        StringBuffer sb = new StringBuffer();
        //sb.append("select productModel.id,productModel.productModel,productModel.productModelName,productModel.productPdlife,productModel.StopDate,productModel.stopSevDate,partner.partnerName as partnerName,brand.brandName as brandName,(select line.productLine  from business_productline line where line.id = productModel.productLine) as lineName , (select productType.typeName from business_producttype productType where productType.id = productModel.productType) as typeName from business_productmodel productModel left join business_partnerinfo partner on partner.id = productModel.partnerId left join business_partnerbrand brand on brand.id = productModel.brandCode where 1=1 and productModel.deleteFlag='1' limit "+startIndex+","+length);
        Query query = null;
        if (StringUtil.isEmpty(searchParam)) {
            sb.append("select productModel.id,productModel.productModel,productModel.productModelName,productModel.productPdlife,productModel.StopDate,productModel.stopSevDate,partner.partnerFullName as partnerFullName,(select productType.typeName from business_producttype productType where productType.id = productModel.productType) as typeName from business_productmodel productModel left join business_partnerinfo partner on partner.id = productModel.partnerId  where 1=1 and productModel.deleteFlag='1'");
            query = baseProductmodelDao.createSQLQuery(sb.toString());
        } else {
            //            String[] ss = searchParam.split("_");
            //            String[] param = new String[1];
            //            param[0] = ss[0];
            //            sb.append("select productModel.id,productModel.productModel,productModel.productModelName,productModel.productPdlife,productModel.StopDate,productModel.stopSevDate,partner.partnerName as partnerName,brand.brandName as brandName,(select line.productLine  from business_productline line where line.id = productModel.productLine) as lineName , (select productType.typeName from business_producttype productType where productType.id = productModel.productType) as typeName from business_productmodel productModel left join business_partnerinfo partner on partner.id = productModel.partnerId left join business_partnerbrand brand on brand.id = productModel.brandCode where 1=1 and productModel.deleteFlag='1' and productModel." + ss[1] + " = ? ");
            //            query = baseProductmodelDao.createSQLQuery(sb.toString(), param);
            sb.append("select productModel.id,productModel.productModel,productModel.productModelName,productModel.productPdlife,productModel.StopDate,productModel.stopSevDate,partner.partnerFullName as partnerFullName,(select productType.typeName from business_producttype productType where productType.id = productModel.productType) as typeName from business_productmodel productModel left join business_partnerinfo partner on partner.id = productModel.partnerId  where 1=1 and productModel.deleteFlag='1' ");
            String[] ss = searchParam.split(",");
            String[] param = new String[ss.length];
            for (int i = 0; i < ss.length; i++) {
                String[] sss = ss[i].split("_");
                if (sss[1].equals("productModelName")) {
                    param[i] = "%" + sss[0] + "%";
                    sb.append("and (productModel." + sss[1] + " like ?  or productModel.productModel like '%" + sss[0] + "%') ");
                } else {
                    param[i] = sss[0];
                    sb.append("and productModel." + sss[1] + " = ? ");
                }
            }
            query = baseProductmodelDao.createSQLQuery(sb.toString(), param);
        }
        query.setFirstResult(Integer.parseInt(startIndex));
        query.setMaxResults(Integer.parseInt(length));

        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List<Map<String, Object>> mapList = query.list();
        return mapList;
    }

    /**
     * <code>getTotalCount</code>
     *
     * @return
     * @since   2013-12-7    guokemenng
     */
    public Integer getTotalCount(String searchParam) {
        String hql = "select count(*) from BaseProductModel where deleteFlag = '1'";
        StringBuffer sb = new StringBuffer();
        sb.append(hql);
        Object o = null;
        if (StringUtil.isEmpty(searchParam)) {
            o = baseProductmodelDao.getHibernateTemplate().find(hql).listIterator().next();
        } else {
            //            String[] ss = searchParam.split("_");
            //            String[] param = new String[1];
            //            param[0] = ss[0];
            //            hql += " and " + ss[1] + " = ?";
            String[] ss = searchParam.split(",");
            String[] param = new String[ss.length];
            for (int i = 0; i < ss.length; i++) {
                String[] sss = ss[i].split("_");
                param[i] = sss[0];
                if (sss[1].equals("productModelName")) {
                    param[i] = "%" + sss[0] + "%";
                    sb.append("and productModel.productModel like ? ");
                } else {
                    param[i] = sss[0];
                    sb.append("and productModel." + sss[1] + " = ? ");
                }
            }
            Object[] paramO = param;
            o = baseProductmodelDao.getHibernateTemplate().find(sb.toString(), paramO).listIterator().next();
        }
        if (o != null) {
            Long l = (Long) o;
            return l.intValue();
        } else {
            return 0;
        }
    }

    /**
     * <code>delete</code>
     *
     * @param id
     * @since   2013-12-17    guokemenng
     */
    public void delete(String id) {
        BaseProductModel model = this.baseProductmodelDao.get(id);
        Short deleteFlag = 0;
        model.setDeleteFlag(deleteFlag);
        this.baseProductmodelDao.update(model);
    }

    /**
     * @param brandCodeId
     * @return
     */
    public List<BaseProductModel> getProductModel(HashMap<String, String> seachMap) {
        List<BaseProductModel> productModelList = new ArrayList<BaseProductModel>();
        DetachedCriteria criteria = DetachedCriteria.forClass(BaseProductModel.class);
        criteria.add(Restrictions.eq(BaseProductModel.DELETEFLAG, (short) 1));
        if (seachMap.containsKey(BaseProductModel.PARTNERID))
            criteria.add(Restrictions.eq(BaseProductModel.PARTNERID, seachMap.get(BaseProductModel.PARTNERID)));
        if (seachMap.containsKey(BaseProductModel.PRODUCTTYPE))
            criteria.add(Restrictions.eq(BaseProductModel.PRODUCTTYPE, seachMap.get(BaseProductModel.PRODUCTTYPE)));
        productModelList = getDao().findByCriteria(criteria);
        return productModelList;
    }

    /**
     * <code>getParentModelById</code>
     * 根据产品ID 得到厂商和型号数据
     * @param id
     * @return
     * @since   2015年1月7日    guokemenng
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> getParentModelById(String id) {
        String sql = "select productModel.productType,productModel.partnerId,partner.partnerFullName as partnerFullName,(select productType.typeName from business_producttype productType where productType.id = productModel.productType) as typeName from business_productmodel productModel left join business_partnerinfo partner on partner.id = productModel.partnerId  where 1=1 and productModel.deleteFlag='1' and productModel.id = ?";
        Object[] param = new Object[] { id };
        List<Map<String, Object>> mapList = getDao().createSQLQuery(sql, param).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        if (mapList.size() > 0) {
            return mapList.get(0);
        } else {
            return null;
        }
    }
}
