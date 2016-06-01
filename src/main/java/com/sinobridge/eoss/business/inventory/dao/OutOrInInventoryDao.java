/*
 * FileName: OutOrInInventoryDao.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.business.inventory.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.transform.Transformers;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.sinobridge.base.core.dao.imp.DefaultBaseDao;
import com.sinobridge.eoss.business.inventory.model.OutOrInInventoryModel;

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
 * 2015年2月3日 下午4:56:26          guo_kemeng        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Repository
public class OutOrInInventoryDao extends DefaultBaseDao<OutOrInInventoryModel, Long> {

    /**
     * <code>getSalesContractByCode</code>
     * 根据Code 查合同Name
     * @param code
     * @return
     * @since   2015年2月12日    liyx
     */
    //    public List getProjectAll() {
    //        Map<String, Object> map = new HashMap<String, Object>();
    //        	@SuppressWarnings("rawtypes")
    //			List list = find("select projectName from pms_project");
    //
    //            if (list.size() > 0) {
    //            	System.out.println("name="+list.get(0));
    //                map.put("name", list.get(0));
    //            }
    //        return list;
    //    }
    /**
     * 查询出未归还的出库单 
     * @return
     * @throws DataAccessException
     * @since   2015年2月13日    liyx
     */
    public List<OutOrInInventoryModel> getAllOutOrInInventory() throws DataAccessException {
        List<OutOrInInventoryModel> list = find("from OutOrInInventoryModel where state in(3) ");
        if (list == null || list.size() == 0) {
            return null;
        }
        return list;
    }

    /**
    * 根据借货单，查找对应的产品数据
    * @param borrowingId
    * @return
    */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> getProducts(int borrowingId) {
        StringBuffer sb = new StringBuffer();
        //        sb.append("select borr.id,inve.Partner,inve.ProductNo,inve.SeriesNo,bpro.State ");
        //        sb.append("from business_inventory_borr borr,business_inventory_bproduct bpro,business_inventory inve ");
        //        sb.append("where borr.id=bpro.BorroWingId and bpro.InventoryId=inve.id and borr.id=" + borrowingId);

        sb.append("select id,partner,productNo,quantity,status,borrowingId ");
        sb.append("from business_inventory_qua bsip where bsip.borrowingId=" + borrowingId);

        List<Map<String, Object>> listProduct = createSQLQuery(sb.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        return listProduct;
    }

    /**
     * 借货单详情设备信息
     * @param borrowingId
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> getProductsDetail(int borrowingId, int returnId) {
        StringBuffer sb = new StringBuffer();
        sb.append("select borr.id,inve.Partner,inve.ProductNo,inve.SeriesNo,bpro.State,inve.Rent ");
        sb.append("from business_inventory_borr borr,business_inventory_bproduct bpro,business_inventory inve ");
        sb.append("where borr.id=bpro.BorroWingId and bpro.InventoryId=inve.id ");
        if (borrowingId != 0) {
            sb.append("and borr.id=" + borrowingId);
        }
        if (returnId != 0) {
            sb.append("and bpro.ReturnId=" + returnId);
        }

        List<Map<String, Object>> listProduct = createSQLQuery(sb.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        return listProduct;
    }

    /**
     * 根据产品型号查找产品序列号
     * @param borrowingId
     * @param productNo
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> getSeriesNoByProductNo(int borrowingId, String productNo) {
        StringBuffer sb = new StringBuffer();
        sb.append("select bsi.id,bsi.seriesNo from business_inventory bsi,business_inventory_qua bsiq ");
        sb.append("where bsi.productNo=bsiq.productNo and bsi.state=0 and bsiq.productNo='" + productNo + "' and bsiq.borrowingId=" + borrowingId);

        List<Map<String, Object>> listProduct = createSQLQuery(sb.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        return listProduct;
    }

    /**出库申请列表
    * @return
    */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> getOutAppliList() {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT i.id,i.`ProductNo`,SUM(i.`Number`) totalNum,IFNULL(bi.unNum,0) unNum,IFNULL(qi.qua,0) qua,");
        sb.append("(SUM(i.`Number`)-IFNULL(bi.unNum,0) - IFNULL(qi.qua,0)) syqua,i.`Partner` ");
        sb.append("FROM `business_inventory` i ");
        sb.append("LEFT JOIN ");
        sb.append("(SELECT b.`ProductNo`,SUM(b.`Number`) unNum  FROM ");
        sb.append("business_inventory b WHERE b.`State`<>'0' GROUP BY b.`ProductNo`) bi ON bi.ProductNo=i.`ProductNo` ");
        sb.append("LEFT JOIN ");
        sb.append("(SELECT q.`ProductNo`,SUM(q.`Quantity`)qua FROM `business_inventory_qua` q ");
        sb.append("WHERE q.`Status`='0' GROUP BY q.`ProductNo`)qi ON qi.ProductNo = i.ProductNo GROUP BY i.`ProductNo` ");

        /*sb.append("SELECT i.id,i.`ProductNo`,SUM(i.`Number`) totalNum,bi.unNum,(bi.unNum - qi.qua) qua,i.`Partner`  FROM `business_inventory` i  ");
        sb.append("LEFT JOIN (SELECT b.`ProductNo`,SUM(b.`Number`) unNum  FROM business_inventory b WHERE b.`State`='0' GROUP BY b.`ProductNo`) ");
        sb.append("bi ON bi.ProductNo=i.`ProductNo` ");
        sb.append("LEFT JOIN (SELECT q.`ProductNo`,SUM(q.`Quantity`)qua FROM `business_inventory_qua` q ");
        sb.append("WHERE q.`Status`='0' GROUP BY q.`ProductNo`)qi ON qi.ProductNo = bi.ProductNo ");
        sb.append("GROUP BY i.`ProductNo` ");*/

        List<Map<String, Object>> list = this.createSQLQuery(sb.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> m = list.get(i);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", m.get("id").toString());
            map.put("ProductNo", m.get("ProductNo").toString());
            map.put("totalNum", m.get("totalNum").toString());
            map.put("Partner", m.get("Partner") == null ? "" : m.get("Partner").toString());
            map.put("unNum", Integer.parseInt(m.get("syqua").toString()));
            /*int qua = 0;
            if (m.get("unNum") != null) {
                int num = Integer.parseInt(m.get("unNum").toString());
                if (m.get("qua") != null) {
                    qua = Integer.parseInt(m.get("qua").toString());
                } else {
                    qua = num;
                }
                map.put("unNum", qua);
            } else {
                map.put("unNum", 0);
            }*/
            listMap.add(map);
        }

        return listMap;
    }

    /**查询型号剩余数量
     * @param product
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> getChoiceProduct(String[] product) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT i.`ProductNo`, SUM(i.`Number`) num, i.`Rent`,i.`Partner`,qi.qua FROM `business_inventory` i ");
        sb.append("LEFT JOIN (SELECT q.`ProductNo`,SUM(q.`Quantity`)qua FROM `business_inventory_qua` q ");
        sb.append("WHERE q.`Status`='0' GROUP BY q.`ProductNo`)qi ON qi.ProductNo = i.ProductNo WHERE i.`State`='0' ");
        sb.append("AND i.`ProductNo` IN (");
        if (product.length != 0) {
            for (int i = 0; i < product.length - 1; i++) {
                sb.append("'" + product[i] + "',");
            }
            sb.append("'" + product[product.length - 1] + "'");
        }
        sb.append(") GROUP BY i.`ProductNo`");
        List<Map<String, Object>> list = this.createSQLQuery(sb.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> m = list.get(i);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("ProductNo", m.get("ProductNo").toString());
            if (m.get("Rent") != null) {
                map.put("Rent", m.get("Rent").toString());
            } else {
                map.put("Rent", "0");
            }
            map.put("Partner", m.get("Partner").toString());
            int qua = 0;
            if (m.get("num") != null) {
                int num = Integer.parseInt(m.get("num").toString());
                if (m.get("qua") != null) {
                    qua = num - Integer.parseInt(m.get("qua").toString());
                } else {
                    qua = num;
                }
                map.put("num", qua);
            } else {
                map.put("num", 0);
            }
            listMap.add(map);
        }
        return listMap;
    }
}
