/*
 * FileName: BaseProductTypeService.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.business.projectmanage.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinobridge.base.core.service.DefaultBaseService;
import com.sinobridge.eoss.business.projectmanage.dao.BaseProductTypeDao;
import com.sinobridge.eoss.business.projectmanage.dto.Tree;
import com.sinobridge.eoss.business.projectmanage.dto.Treegrid;
import com.sinobridge.eoss.business.projectmanage.model.BaseProductType;
import com.sinobridge.systemmanage.util.StringUtil;

/**
 * <code>BaseProductTypeService</code>
 * 
 * @version  1.0
 * @author  guokemenng
 * @since 1.0  2013-11-25
 */
@Service
@Transactional
public class BaseProductTypeService extends DefaultBaseService<BaseProductType, BaseProductTypeDao> {

    private List<BaseProductType> confallList = null;

    @Autowired
    private BaseProductTypeDao baseProductTypeDao;

    /**
     * 
     */
    public BaseProductTypeService() {
        super();
        // TODO Auto-generated constructor stub
        //   
    }

    /**
     * <code>getTreegrid</code>
     *
     * @return
     * 得到treegrid集合
     * @since   2013-11-8    guokemenng
     */
    public List<Treegrid> getTreegrid() {
        List<Treegrid> treeList = new ArrayList<Treegrid>();

        Short deleteFlag = 1;
        //得到没有父节点list
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(BaseProductType.class); //应用hibernate的DetachedCriteria,创建DetachedCriteria
        detachedCriteria.add(Restrictions.eq(BaseProductType.DELETEFLAG, deleteFlag));
        detachedCriteria.add(Restrictions.isNull(BaseProductType.PARENTID));
        detachedCriteria.addOrder(Order.asc(BaseProductType.ORDERVALUE));
        List<BaseProductType> confPList = baseProductTypeDao.findByCriteria(detachedCriteria); //实际查询语句

        //得到有父节点list
        DetachedCriteria criteria = DetachedCriteria.forClass(BaseProductType.class);
        criteria.add(Restrictions.eq(BaseProductType.DELETEFLAG, deleteFlag));
        criteria.addOrder(Order.asc(BaseProductType.ORDERVALUE));
        criteria.add(Restrictions.isNotNull(BaseProductType.PARENTID));
        List<BaseProductType> confList = baseProductTypeDao.findByCriteria(criteria);

        for (BaseProductType conf : confPList) {
            Treegrid treegrid = new Treegrid();

            getTreegrid(treegrid, conf);

            getChildren(treegrid, confList);
            treeList.add(treegrid);
        }
        for (Treegrid tree : treeList) {
            //设为关闭状态
            if (tree.getChildren().size() > 0) {
                tree.setState("closed");
                closeState(tree.getChildren());

            }
        }
        return treeList;
    }

    /**
     * <code>getTreegrid</code>
     * 组成一条treegrid信息
     * @param treegrid
     * @param conf
     * @since   2013-11-8    guokemenng
     */
    public void getTreegrid(Treegrid treegrid, BaseProductType conf) {
        treegrid.setId(conf.getId());
        treegrid.setName(conf.getTypeName());
        treegrid.setIcon(conf.getIcon());
        treegrid.setIsModule(conf.getIsModule());
        treegrid.setIsLogic(conf.getIsLogic());
        treegrid.setBgImage(conf.getBgImage());
        treegrid.setOrderValue(conf.getOrderValue());
        treegrid.setTypeCode(conf.getTypeCode());
    }

    /**
     * <code>getTree</code>
     * 组成一条tree信息
     * @param treegrid
     * @param conf
     * @since   2013-11-18    guokemenng
     */
    public void getTree(Tree treegrid, BaseProductType conf) {
        treegrid.setId(conf.getId());
        treegrid.setText(conf.getTypeName());
        treegrid.setWay(conf.getTypeName());
    }

    /**
     * <code>getChildren</code>
     *
     * @param treegrid
     * 得到树节点的子节点信息
     * @param confList
     * @since   2013-11-8    guokemenng
     */
    public synchronized void getChildren(Treegrid treegrid, List<BaseProductType> confList) {
        List<Treegrid> childList = new ArrayList<Treegrid>();
        for (BaseProductType conf : confList) {
            if (treegrid.getId().equals(conf.getParentId())) {

                Treegrid tree = new Treegrid();
                getTreegrid(tree, conf);

                getChildren(tree, confList);
                childList.add(tree);
            }
        }
        if (childList.size() > 0) {
            treegrid.setChildren(childList);
        }
    }

    /**
     * <code>getChildren</code>
     *
     * @param tree
     * 得到树节点的子节点信息
     * @param confList
     * @since   2013-11-8    guokemenng
     */
    public synchronized void getTreeChildren(Tree tree, List<BaseProductType> confList) {
        List<Tree> childList = new ArrayList<Tree>();
        for (BaseProductType conf : confList) {
            if (tree.getId().equals(conf.getParentId())) {

                Tree tr = new Tree();
                getTree(tr, conf);

                getTreeChildren(tr, confList);
                childList.add(tr);
            }
        }
        if (childList.size() > 0) {
            tree.setChildren(childList);
        }
    }

    /**
     * <code>closeState</code>
     * 设置关闭状态
     * @param tree
     * @since   2013-11-8    guokemenng
     */
    public void closeState(List<Treegrid> tree) {
        for (Treegrid t : tree) {
            if (t.getChildren().size() > 0) {
                t.setState("closed");
                closeState(t.getChildren());
            }
        }
    }

    public void closeTreeState(List<Tree> tree) {
        for (Tree t : tree) {
            if (t.getChildren().size() > 0) {
                t.setState("closed");
                closeTreeState(t.getChildren());
            }
        }
    }

    /**
     * <code>del</code>
     * 删除信息
     * @param id
     * @since   2013-11-12    guokemenng
     */
    public void del(String id) {
        BaseProductType productType = this.baseProductTypeDao.get(id);
        Short s = 0;
        productType.setDeleteFlag(s);
        this.baseProductTypeDao.update(productType);
    }

    /**
     * <code>create</code>
     *
     * @param devicetype
     * @since   2013-11-18    guokemenng
     */
    @Override
    public void create(BaseProductType devicetype) {
        Short s = 1;
        devicetype.setDeleteFlag(s);
        baseProductTypeDao.save(devicetype);
    }

    @Override
    public void update(BaseProductType devicetype) {
        Short s = 1;
        devicetype.setDeleteFlag(s);
        baseProductTypeDao.update(devicetype);
    }

    public List<BaseProductType> initProductType() {
        Short deleteFlag = 1;
        //得到有父节点list
        DetachedCriteria criteria = DetachedCriteria.forClass(BaseProductType.class);
        criteria.add(Restrictions.eq(BaseProductType.DELETEFLAG, deleteFlag));
        criteria.addOrder(Order.asc(BaseProductType.ORDERVALUE));
        //  criteria.add(Restrictions.isNotNull(BaseProductType.PARENTID));
        List<BaseProductType> confList = baseProductTypeDao.findByCriteria(criteria);
        return confList;
    }

    public List<Tree> getTree(String param, String id) {
        List<Tree> treeList = new ArrayList<Tree>();
        Short deleteFlag = 1;
        if (!StringUtil.isEmpty(param)) {
            DetachedCriteria detachedCriteriap = DetachedCriteria.forClass(BaseProductType.class);
            detachedCriteriap.add(Restrictions.like(BaseProductType.TYPENAME, "%" + param + "%"));
            detachedCriteriap.add(Restrictions.eq(BaseProductType.DELETEFLAG, deleteFlag));
            detachedCriteriap.addOrder(Order.desc(BaseProductType.ORDERVALUE));
            List<BaseProductType> confListByParam = baseProductTypeDao.findByCriteria(detachedCriteriap);
            for (BaseProductType conf : confListByParam) {
                Tree tree = new Tree();
                getTree(tree, conf);
                treeList.add(tree);
            }
        } else {
            //     if (treecache == null) {
            //得到没有父节点list
            if (id != null) {
                //得到有父节点list
                DetachedCriteria criteria = DetachedCriteria.forClass(BaseProductType.class);
                criteria.add(Restrictions.eq(BaseProductType.DELETEFLAG, deleteFlag));
                criteria.addOrder(Order.asc(BaseProductType.ORDERVALUE));
                criteria.add(Restrictions.eq(BaseProductType.PARENTID, id));
                List<BaseProductType> confList = baseProductTypeDao.findByCriteria(criteria);
                if (confallList == null) {
                    confallList = initProductType();
                }
                for (BaseProductType conf : confList) {
                    Tree tree = new Tree();
                    for (BaseProductType baseProductType : confallList) {
                        if (conf.getId().equals(baseProductType.getParentId())) {
                            tree.setState("closed");
                            break;
                        }
                    }
                    getTree(tree, conf);
                    treeList.add(tree);
                }

            } else {

                DetachedCriteria detachedCriteria = DetachedCriteria.forClass(BaseProductType.class);
                detachedCriteria.add(Restrictions.eq(BaseProductType.DELETEFLAG, deleteFlag));
                detachedCriteria.add(Restrictions.isNull(BaseProductType.PARENTID));
                detachedCriteria.addOrder(Order.asc(BaseProductType.ORDERVALUE));
                List<BaseProductType> confPList = baseProductTypeDao.findByCriteria(detachedCriteria);

                for (BaseProductType conf : confPList) {
                    Tree tree = new Tree();
                    tree.setState("closed");
                    getTree(tree, conf);

                    //    getTreeChildren(tree, confList);
                    treeList.add(tree);
                }
            }

        }

        //        for (Tree tree : treeList) {
        //            //设为关闭状态
        //            if (tree.getChildren().size() >= 0) {
        //                tree.setState("closed");
        //                closeTreeState(tree.getChildren());
        //            }
        //        }

        return treeList;
    }

    /**
     * 根据厂商选择类别树
     * @param partnerId
     * @return
     */
    public List<Tree> getTreebyPartner(String partnerId) {
        List<Tree> treeList = new ArrayList<Tree>();

        return treeList;
    }

    /**
     * @param param
     * @return
     */
    public List<Tree> getTreeResource(String param, String resourcesId) {
        List<Tree> treeList = new ArrayList<Tree>();
        Short deleteFlag = 1;
        if (!StringUtil.isEmpty(param)) {
            DetachedCriteria detachedCriteriap = DetachedCriteria.forClass(BaseProductType.class);
            detachedCriteriap.add(Restrictions.like(BaseProductType.TYPENAME, "%" + param + "%"));
            detachedCriteriap.add(Restrictions.eq(BaseProductType.DELETEFLAG, deleteFlag));
            detachedCriteriap.add(Restrictions.like(BaseProductType.TYPECODE, resourcesId + "%"));
            detachedCriteriap.addOrder(Order.desc(BaseProductType.ORDERVALUE));
            List<BaseProductType> confListByParam = baseProductTypeDao.findByCriteria(detachedCriteriap);
            for (BaseProductType conf : confListByParam) {
                Tree tree = new Tree();
                getTree(tree, conf);
                treeList.add(tree);
            }
        } else {
            DetachedCriteria detachedCriteria = DetachedCriteria.forClass(BaseProductType.class);
            detachedCriteria.add(Restrictions.eq(BaseProductType.DELETEFLAG, deleteFlag));
            detachedCriteria.add(Restrictions.eq(BaseProductType.TYPECODE, resourcesId));
            List<BaseProductType> confPList = baseProductTypeDao.findByCriteria(detachedCriteria);

            DetachedCriteria criteria = DetachedCriteria.forClass(BaseProductType.class);
            criteria.add(Restrictions.eq(BaseProductType.DELETEFLAG, deleteFlag));
            criteria.add(Restrictions.like(BaseProductType.TYPECODE, resourcesId + "%"));
            criteria.addOrder(Order.asc(BaseProductType.ORDERVALUE));
            criteria.add(Restrictions.isNotNull(BaseProductType.PARENTID));
            List<BaseProductType> confList = baseProductTypeDao.findByCriteria(criteria);

            if (confPList.size() > 0) {
                for (BaseProductType conf : confPList) {
                    Tree tree = new Tree();

                    getTree(tree, conf);

                    getTreeChildren(tree, confList);
                    treeList.add(tree);
                }
            } else {
                for (BaseProductType conf : confList) {
                    Tree tree = new Tree();

                    getTree(tree, conf);

                    //                    getTreeChildren(tree, confList);
                    treeList.add(tree);
                }
            }
        }

        return treeList;
    }

    public List<BaseProductType> getListByPartner(String partnerid) {
        List<BaseProductType> rs = null;
        String sql = "SELECT a.* FROM business_producttype a LEFT JOIN business_partnerproduct b ON a.id=b.producttypeid WHERE b.PartnerId='" + partnerid + "'";
        Query query = baseProductTypeDao.createSQLQuery(sql).addEntity(BaseProductType.class);
        rs = query.list();
        return rs;
    }

    /**
     * 根据厂商得类别树
     * @param partnerid
     * @return
     */
    public List<Tree> getTreeBypartner(String partnerid) {
        List<Tree> tree = new ArrayList<Tree>();
        List<BaseProductType> rs = getListByPartner(partnerid);
        for (BaseProductType baseProductType : rs) {
            Tree treeItem = new Tree();
            getTree(treeItem, baseProductType);
            tree.add(treeItem);
        }
        return tree;

    }

}
