/*
 * FileName: BasePartnerInfoService.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.business.projectmanage.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinobridge.base.core.service.DefaultBaseService;
import com.sinobridge.base.core.utils.PaginationSupport;
import com.sinobridge.eoss.business.projectmanage.BaseConstants;
import com.sinobridge.eoss.business.projectmanage.dao.BasePartnerInfoDao;
import com.sinobridge.eoss.business.projectmanage.dao.BasePartnerProductDao;
import com.sinobridge.eoss.business.projectmanage.dao.BaseProductTypeDao;
import com.sinobridge.eoss.business.projectmanage.dto.Partnerinfo;
import com.sinobridge.eoss.business.projectmanage.dto.PartnerinfoTree;
import com.sinobridge.eoss.business.projectmanage.model.BasePartnerInfo;
import com.sinobridge.eoss.business.projectmanage.model.BasePartnerProduct;
import com.sinobridge.systemmanage.dao.SysDomainDao;
import com.sinobridge.systemmanage.model.SysDomain;
import com.sinobridge.systemmanage.model.SysDomainValue;
import com.sinobridge.systemmanage.util.ChineseSpell;

/**
 * <code>BasePartnerInfoService</code>
 * 
 * @version  1.0
 * @author  guokemenng
 * @since 1.0  2013-11-25
 */
@Service
@Transactional
public class BasePartnerInfoService extends DefaultBaseService<BasePartnerInfo, BasePartnerInfoDao> {

    @Autowired
    private BasePartnerInfoDao basePartnerInfoDao;

    @Autowired
    protected BasePartnerProductDao basePartnerProductDao;

    @Autowired
    protected BaseProductTypeDao baseProductTypeDao;



    /** 
     * SysDomainDao SysDomainDao :
     * 字典表   
     * @since  2013-11-13 guokemenng
     */
    @Autowired
    private SysDomainDao sysDomainDao;

    /**
     * <code>findPageByCriteria</code>
     *
     * @param detachedCriteria
     * @param pageSize
     * @param startIndex
     * @return
     * @since   2013-11-13    guokemenng
     */
    public PaginationSupport findPageByCriteria(DetachedCriteria detachedCriteria, Integer pageNo, Integer pageSize) {
        Integer startIndex = (pageNo - 1) * pageSize;
        return basePartnerInfoDao.findPageByCriteria(detachedCriteria, pageSize, startIndex);
    }

    /**
     * <code>getConfPartnerType</code>
     * 得到厂商设备分类
     * @param sysDomainId
     * @return
     * @since   2013-11-13    guokemenng
     */
    public List<SysDomainValue> getConfPartnerType() {
        SysDomain domain = sysDomainDao.get(BaseConstants.BASEPARTNERINFO);
        return domain.getSysDomainValues();
    }

    /**
     * <code>getPartnerServiceType</code>
     * 得到服务供应商类型
     * @param sysDomainId
     * @return
     * @since   2013-11-13    guokemenng
     */
    public List<SysDomainValue> getPartnerServiceType() {
        SysDomain domain = sysDomainDao.get(BaseConstants.BASEPARTNERSERVICE);
        return domain.getSysDomainValues();
    }

    /**
     * 查询物业公司
     * @param partnerType
     * @return
     */
    public List<Map<String, String>> findPartnerByProperty(String partnerType) {
        List<BasePartnerInfo> partnerList = new ArrayList<BasePartnerInfo>();
        //添加查询条件
        if (partnerType != null && partnerType != "") {
            DetachedCriteria detachedCriteria = DetachedCriteria.forClass(BasePartnerInfo.class);
            detachedCriteria.add(Restrictions.eq(BasePartnerInfo.SERVICEPARTNERTYPE, Short.parseShort(partnerType)));
            partnerList = basePartnerInfoDao.findByCriteria(detachedCriteria);
        }

        List<Map<String, String>> rs = new ArrayList<Map<String, String>>();
        for (BasePartnerInfo basePartnerInfo : partnerList) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("id", basePartnerInfo.getId());
            map.put("name", basePartnerInfo.getPartnerFullName());
            rs.add(map);
        }
        return rs;

    }

    /**
     * <code>findPageByCriteria</code>
     *
     * @param detachedCriteria
     * @return
     * @throws NoSuchMethodException 
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
     * @since   2013-11-15    guokemenng
     */
    public List<PartnerinfoTree> findPartnerByMap(DetachedCriteria detachedCriteria, String productTypeId) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        List<PartnerinfoTree> partnerinfoTrees = new ArrayList<PartnerinfoTree>();
        List<BasePartnerInfo> partnerList = null;

        //添加查询条件
        if (productTypeId != null && productTypeId != "") {
            partnerList = getPartnerInfoByType(productTypeId);
        } else {
            partnerList = this.basePartnerInfoDao.findByCriteria(detachedCriteria);
        }
        PartnerinfoTree partnerinfoA_E = new PartnerinfoTree();
        partnerinfoA_E.setId("A-E");
        partnerinfoA_E.setName("A-E");
        PartnerinfoTree partnerinfoF_M = new PartnerinfoTree();
        partnerinfoF_M.setId("F_M");
        partnerinfoF_M.setName("F_M");
        PartnerinfoTree partnerinfoN_R = new PartnerinfoTree();
        partnerinfoN_R.setId("N_R");
        partnerinfoN_R.setName("N_R");
        PartnerinfoTree partnerinfoS_Z = new PartnerinfoTree();
        partnerinfoS_Z.setId("S_Z");
        partnerinfoS_Z.setName("S_Z");
        List<PartnerinfoTree> partnerinfoTreeChildA_Es = new ArrayList<PartnerinfoTree>();
        List<PartnerinfoTree> partnerinfoTreeChildF_Ms = new ArrayList<PartnerinfoTree>();
        List<PartnerinfoTree> partnerinfoTreeChildN_Rs = new ArrayList<PartnerinfoTree>();
        List<PartnerinfoTree> partnerinfoTreeChildS_Zs = new ArrayList<PartnerinfoTree>();

        for (BasePartnerInfo confPartner : partnerList) {
            PartnerinfoTree partnerinfoTree = new PartnerinfoTree();
            Partnerinfo partnerinfo = new Partnerinfo();
            PropertyUtils.copyProperties(partnerinfo, confPartner);
            String cnStr = partnerinfo.getPartnerFullName();

            String efn = ChineseSpell.getFullSpell(cnStr);
            char[] cc = efn.toLowerCase().toCharArray();
            int ascii = ChineseSpell.getCnAscii(cc[0]);

            partnerinfo.setIndex(ascii);
            partnerinfoTree.setId(partnerinfo.getId());
            partnerinfoTree.setName(partnerinfo.getPartnerFullName());
            switch (partnerinfo.getIndex()) {
                case 97:
                    partnerinfoTreeChildA_Es.add(partnerinfoTree);
                    break;
                case 98:
                    partnerinfoTreeChildA_Es.add(partnerinfoTree);
                    break;
                case 99:
                    partnerinfoTreeChildA_Es.add(partnerinfoTree);
                    break;
                case 100:
                    partnerinfoTreeChildA_Es.add(partnerinfoTree);
                    break;
                case 101:
                    partnerinfoTreeChildA_Es.add(partnerinfoTree);
                    break;
                case 102:
                    partnerinfoTreeChildF_Ms.add(partnerinfoTree);
                    break;
                case 103:
                    partnerinfoTreeChildF_Ms.add(partnerinfoTree);
                    break;
                case 104:
                    partnerinfoTreeChildF_Ms.add(partnerinfoTree);
                    break;
                case 105:
                    partnerinfoTreeChildF_Ms.add(partnerinfoTree);
                    break;
                case 106:
                    partnerinfoTreeChildF_Ms.add(partnerinfoTree);
                    break;
                case 107:
                    partnerinfoTreeChildF_Ms.add(partnerinfoTree);
                    break;
                case 108:
                    partnerinfoTreeChildF_Ms.add(partnerinfoTree);
                    break;
                case 109:
                    partnerinfoTreeChildF_Ms.add(partnerinfoTree);
                    break;
                case 110:
                    partnerinfoTreeChildN_Rs.add(partnerinfoTree);
                    break;
                case 111:
                    partnerinfoTreeChildN_Rs.add(partnerinfoTree);
                    break;
                case 112:
                    partnerinfoTreeChildN_Rs.add(partnerinfoTree);
                    break;
                case 113:
                    partnerinfoTreeChildN_Rs.add(partnerinfoTree);
                    break;
                case 114:
                    partnerinfoTreeChildN_Rs.add(partnerinfoTree);
                    break;
                case 115:
                    partnerinfoTreeChildS_Zs.add(partnerinfoTree);
                    break;
                case 116:
                    partnerinfoTreeChildS_Zs.add(partnerinfoTree);
                    break;
                case 117:
                    partnerinfoTreeChildS_Zs.add(partnerinfoTree);
                    break;
                case 118:
                    partnerinfoTreeChildS_Zs.add(partnerinfoTree);
                    break;
                case 119:
                    partnerinfoTreeChildS_Zs.add(partnerinfoTree);
                    break;
                case 120:
                    partnerinfoTreeChildS_Zs.add(partnerinfoTree);
                    break;
                case 121:
                    partnerinfoTreeChildS_Zs.add(partnerinfoTree);
                    break;
                case 122:
                    partnerinfoTreeChildS_Zs.add(partnerinfoTree);
                    break;
            }
        }
        partnerinfoA_E.setChildren(partnerinfoTreeChildA_Es);
        partnerinfoF_M.setChildren(partnerinfoTreeChildF_Ms);
        partnerinfoN_R.setChildren(partnerinfoTreeChildN_Rs);
        partnerinfoS_Z.setChildren(partnerinfoTreeChildS_Zs);
        partnerinfoTrees.add(partnerinfoA_E);
        partnerinfoTrees.add(partnerinfoF_M);
        partnerinfoTrees.add(partnerinfoN_R);
        partnerinfoTrees.add(partnerinfoS_Z);
        return partnerinfoTrees;
    }

    public List<BasePartnerInfo> getPartnerInfoByType(String productTypeId) {

        List<BasePartnerInfo> partnerInfoList = new ArrayList<BasePartnerInfo>();
        partnerInfoList = this.basePartnerInfoDao.getPartnerInfoByProductTypeId(this.baseProductTypeDao.get(productTypeId));
        /* DetachedCriteria detachedCriteria = DetachedCriteria.forClass(BasePartnerProduct.class);
         detachedCriteria.add(Restrictions.eq(BasePartnerProduct.PRODUCTTYPEID, productTypeId));
         List<BasePartnerProduct> productList = basePartnerProductDao.findByCriteria(detachedCriteria);
         for (BasePartnerProduct product : productList) {
             partnerInfoList.add(product.getBasePartnerInfo());
         }*/
        return partnerInfoList;
    }

    /**
     * <code>saveOrUpdate</code>
     * 新增或者更新信息
     * @param partner
     * @since   2013-11-18    guokemenng
     */
    public void saveOrUpdate(BasePartnerInfo partner) {

        Short s = 1;
        partner.setDeleteFlag(s);
        basePartnerInfoDao.saveOrUpdate(partner);
    }

    /**
     * <code>addPartnerProduct</code>
     * 厂商增加设备类型
     * @param permissions
     * @since   2013-11-19    guokemenng
     */
    public void addPartnerProduct(String permissions) {
        //confPartnerproductDao
        String[] p = permissions.split(",");

        for (int i = 0; i < p.length; i++) {
            String s = p[i];
            String[] ss = s.split("_");
            String partnerId = ss[0];
            String deviceTypeId = ss[1];
            String isPer = ss[2];
            BasePartnerInfo partner = this.basePartnerInfoDao.get(partnerId);
            if (isPer.equals("false")) {
                Collection<BasePartnerProduct> delProductC = new ArrayList<BasePartnerProduct>();
                Set<BasePartnerProduct> productSet = partner.getBasePartnerProducts();
                Iterator<BasePartnerProduct> it = productSet.iterator();
                while (it.hasNext()) {
                    BasePartnerProduct product = it.next();
                    if (product.getProductTypeId().equals(deviceTypeId)) {
                        delProductC.add(product);
                        //                        this.BasePartnerProductDao.delete(product);
                        //                        this.BasePartnerProductDao.getHibernateTemplate().flush();
                    }
                }
                productSet.removeAll(delProductC);
                this.basePartnerProductDao.deleteAll(delProductC);
                this.basePartnerProductDao.getHibernateTemplate().flush();
            } else {
                BasePartnerProduct product = new BasePartnerProduct();
                //BaseProductType deviceType = this.baseProductTypeDao.get(deviceTypeId);
                product.setId(new Date().getTime() + "");
                product.setBasePartnerInfo(partner);
                //                product.setDeTypeCode(deviceTypeId);
                //                product.setDeTypeName(deviceType.getTypeName());
                this.basePartnerProductDao.save(product);
                this.basePartnerProductDao.getHibernateTemplate().flush();
            }
        }
    }

    /**
     * <code>delete</code>
     *
     * @param ids
     * @since   2013-11-20    guokemenng
     */
    public void delete(String ids) {
        String[] idStr = ids.split(",");
        //        Collection<BasePartnerInfo> partnerList = new ArrayList<BasePartnerInfo>();
        for (int i = 0; i < idStr.length; i++) {
            BasePartnerInfo partner = this.basePartnerInfoDao.get(idStr[i]);
            Short s = 0;
            partner.setDeleteFlag(s);
            this.basePartnerInfoDao.update(partner);
            this.basePartnerInfoDao.getHibernateTemplate().flush();

            //            partnerList.add(partner);
        }
        //        this.basePartnerInfoDao.deleteAll(partnerList);
    }

    public static void main(String[] args) {
        System.out.println(ChineseSpell.getFirstLetterSpell("哈哈哈阿拉善看到"));
    }

    /**
     * <code>getPartnerInfoByProductTypeId</code>
     *
     * @param sql
     * @param params
     * @return
     * @since   2014年8月20日    guokemenng
     */
    @SuppressWarnings("unchecked")
    public List<BasePartnerInfo> getPartnerInfoByProductTypeId(String sql ,Object[] params) {
        List<BasePartnerInfo> basePartnerInfos = new ArrayList<BasePartnerInfo>();
        
        basePartnerInfos = getDao().createSQLQuery(sql, params).addEntity(BasePartnerInfo.class).list();
        
        return basePartnerInfos;
    }
    
    /**
     * <code>getPartnerInfoByProductTypeId</code>
     *
     * @param pageNo
     * @param pageSize
     * @param params
     * @return
     * @since   2014年8月20日    guokemenng
     */
    @SuppressWarnings("unchecked")
    public List<BasePartnerInfo> getPartnerInfoByProductTypeId(Integer pageNo,Integer pageSize,Object[] params) {
        List<BasePartnerInfo> basePartnerInfos = new ArrayList<BasePartnerInfo>();
        
        String sql = "select * from business_partnerinfo p,business_partnerproduct t where p.id = t.partnerId and t.productTypeId = ? and (p.partnerFullName like ? or p.partnerCode like ?) group by p.id";
        
        Query query =  getDao().createSQLQuery(sql, params).addEntity(BasePartnerInfo.class);
        query.setFirstResult(pageNo*pageSize);
        query.setMaxResults(pageSize);
        
        basePartnerInfos = query.list();
        return basePartnerInfos;
    }
    
    /**
     * <code>getTotalCount</code>
     *
     * @param params
     * @return
     * @since   2014年8月20日    guokemenng
     */
    @SuppressWarnings("unchecked")
    public Integer getTotalCount(Object[] params){
        String sql = "select count(*) from business_partnerinfo p,business_partnerproduct t where p.id = t.partnerId and t.productTypeId = ? and (p.partnerFullName like ? or p.partnerCode like ?) group by p.id";
        List<Object> list = getDao().createSQLQuery(sql,params).list();
        if(list.size() > 0){
            return Integer.valueOf(list.get(0).toString()).intValue();
        } else {
            return 0;
        }
        
    }
}
