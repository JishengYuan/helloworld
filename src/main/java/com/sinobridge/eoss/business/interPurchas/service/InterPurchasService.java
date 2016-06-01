package com.sinobridge.eoss.business.interPurchas.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinobridge.base.core.hibernate.id.IdentifierGeneratorImpl;
import com.sinobridge.base.core.service.DefaultBaseService;
import com.sinobridge.base.core.utils.PaginationSupport;
import com.sinobridge.eoss.bpm.service.process.ProcessService;
import com.sinobridge.eoss.business.interPurchas.dao.InterPurchasDao;
import com.sinobridge.eoss.business.interPurchas.dao.InterPurchasProductDao;
import com.sinobridge.eoss.business.interPurchas.model.InterPurchasModel;
import com.sinobridge.eoss.business.interPurchas.model.InterPurchasProductModel;
import com.sinobridge.eoss.business.order.constant.BusinessOrderContant;
import com.sinobridge.systemmanage.util.Global;
import com.sinobridge.systemmanage.vo.SystemUser;

@Service
@Transactional
public class InterPurchasService extends DefaultBaseService<InterPurchasModel, InterPurchasDao> {
    @Autowired
    private ProcessService processService;
    @Autowired
    private InterPurchasProductDao interPurchasProductDao;
    @Autowired
    private InterPurchasProductService interPurchasProductService;

    /**
     * <code>getCreator</code>
     * 查询用户名和ID
     * @return
     * @since   2014年5月23日    wangya
     */
    public List<Map<String, Object>> getCreator() {
        List<Map<String, Object>> mapList = getDao().getCreator();
        return mapList;
    }

    /**保存更新实体
     * @param request
     * @param interPurchas
     * @param interPurchasProduct
     */
    public void doAddOrders(HttpServletRequest request, InterPurchasModel interPurchas, List<InterPurchasProductModel> interPurchasProduct) {
        try {
            //设置ID
            long modelId = IdentifierGeneratorImpl.generatorLong();
            interPurchas.setId(modelId);
            //创建时间 
            Date d = new Date();
            interPurchas.setCreateTime(d);
            //创建者
            SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
            interPurchas.setCreator(systemUser.getStaffName());
            interPurchas.setCreatorId(systemUser.getUserId());
            getDao().save(interPurchas);
            if (interPurchas.getPurchasStatus().equals(BusinessOrderContant.INTERPURCHAS_SH)) {
                //创建工单
                createrProcInst(interPurchas, systemUser, modelId);
            }
            interPurchasProductDao.saveOrUpdateAll(interPurchasProduct);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * <code>findInterPurchasByCriteria</code>
     * 查询当天的内采数量
     * @param detachedCriteria 查询条件
     * @since 2014年6月25日  3unshine 
     */
    public List<InterPurchasModel> findInterPurchasByCriteria(DetachedCriteria detachedCriteria) {
        List<InterPurchasModel> interPurchasModels = new ArrayList<InterPurchasModel>();
        interPurchasModels = getDao().findByCriteria(detachedCriteria);
        return interPurchasModels;
    }

    /**
     * <code>update</code>
     * 更新实体
     * @param tableData
     * @since 2014年5月19日 wangya
     */
    public void update(InterPurchasModel interPurchas, List<InterPurchasProductModel> interPurchasProduct, SystemUser systemUser) {
        //创建时间 
        Date d = new Date();
        interPurchas.setCreateTime(d);
        interPurchas.setCreator(systemUser.getStaffName());
        interPurchas.setCreatorId(systemUser.getUserId());
        long id = interPurchas.getId();
        //删除原有产品记录
        interPurchasProductDao.deleteProductByPurchasId(id);
        interPurchasProductDao.saveOrUpdateAll(interPurchasProduct);
        getDao().saveOrUpdate(interPurchas);
        if (interPurchas.getPurchasStatus().equals(BusinessOrderContant.INTERPURCHAS_SH)) {
            //创建工单
            createrProcInst(interPurchas, systemUser, id);
        }
    }

    /**
     * <code>reviseNote</code>
     * 更新实体，重新提交
     * @param status 
     * @param tableData
     * @since 2014年5月19日 wangya
     */
    public void reviseNote(InterPurchasModel interPurchasModel, List<InterPurchasProductModel> interPurchasProductModel, SystemUser systemUser, String taskId, String status) {
        // TODO Auto-generated method stub
        //创建时间 
        Date d = new Date();
        interPurchasModel.setCreateTime(d);
        interPurchasModel.setCreator(systemUser.getStaffName());
        interPurchasModel.setCreatorId(systemUser.getUserId());
        interPurchasModel.setPurchasStatus(BusinessOrderContant.INTERPURCHAS_SH);
        long id = interPurchasModel.getId();
        //删除原有产品记录
        interPurchasProductDao.deleteProductByPurchasId(id);
        interPurchasProductDao.saveOrUpdateAll(interPurchasProductModel);
        getDao().saveOrUpdate(interPurchasModel);
        if (status.equals(BusinessOrderContant.CXTJ)) {
            Map<String, Object> map = new HashMap<String, Object>();
            //从新提交订单时提供的key值
            map.put(BusinessOrderContant.IS_RE_SUBMIT, true);
            processService.handle(taskId, systemUser.getStaffName(), map, null, null);
        }
    }

    /**
     * <code>creatProcInst</code>
     * 创建工单
     * @param tableData
     * @param tableData
     * @since 2014年7月7日 wangya
     */
    private void createrProcInst(InterPurchasModel interPurchas, SystemUser systemUser, long id) {
        Long valId = processService.nextValId();
        interPurchas.setProcessInstanceId(valId);
        Map<String, Object> variableMap = new HashMap<String, Object>();
        variableMap.put("interPurshasId", id);
        //创建工单
        Long[] procInstId = processService.create(valId, interPurchas.getPurchasName(), systemUser.getUserName(), "bnc", 1, variableMap, null, null, interPurchas.getPurchasCode());
        System.out.println("创建工单成功：" + procInstId[0]);
    }

    /**更新状态为审批通过,处理状态为等待下单
     * @param interPurchas
     */
    public void updateStatus(InterPurchasModel interPurchas) {
        Long id = interPurchas.getId();
        getDao().purchasStatusTG(id);

    }


    /**删除多个內采
     * @param ids
     */
    public void deletes(String[] ids) {
        List<InterPurchasProductModel> delInterProduct = new ArrayList<InterPurchasProductModel>();
        List<InterPurchasModel> delInter = new ArrayList<InterPurchasModel>();
        for (String id : ids) {
            Long trueId = Long.parseLong(id);
            InterPurchasModel interPurchas = getDao().get(trueId);
            delInter.add(interPurchas);

            DetachedCriteria detachedCriteria = DetachedCriteria.forClass(InterPurchasProductModel.class);
            detachedCriteria.add(Restrictions.eq("interPurchas.id", trueId));
            detachedCriteria.createAlias("interPurchas", InterPurchasProductModel.INTERPURCHAS);
            List<InterPurchasProductModel> oldProduct = interPurchasProductDao.findByCriteria(detachedCriteria);
            delInterProduct.addAll(oldProduct);
        }
        interPurchasProductDao.deleteAll(delInterProduct);
        getDao().deleteAll(delInter);
    }

    /**放弃提交
     * @param request
     * @param interPurchas
     */
    public void endInterPurchas(HttpServletRequest request, InterPurchasModel interPurchas) {
        SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
        Long id = interPurchas.getId();
        getDao().endInterPurchas(id);//更新状态
        Map<String, Object> map = new HashMap<String, Object>();
        //重新提交订单时提供的key值
        map.put(BusinessOrderContant.IS_RE_SUBMIT, false);
        String taskId = request.getParameter("taskId");
        processService.handle(taskId, systemUser.getUserName(), map, null, null);
    }

    /**內采列表
     * @param searchMap
     * @param parseInt
     * @param pageSize
     * @return
     */
    public PaginationSupport findInterPurchas(HashMap<String, Object> searchMap, int parseInt, Integer pageSize) {
        return getDao().findInterPurchas(searchMap, parseInt, pageSize);
    }

    /**通过工单ID，查询內采
     * @param proId
     * @return
     */
    public InterPurchasModel findProId(String proId) {
        InterPurchasModel inter = getDao().findProId(proId);
        return inter;
    }

    /**
     * <code>getInterById</code>
     *  判断是否能修改
     * @param orderId
     * @return
     * @since   2014年11月17日    wangya
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> getInterById(Long id) {
        String sql = "SELECT c.id,c.PurchasName,art.NAME_,art.CREATE_TIME_,art.ASSIGNEE_ FROM business_inter_purchas c LEFT JOIN bpm_process_inst bpi ON c.ProcessInstanceId=bpi.ID_  LEFT JOIN act_ru_task art ON bpi.HI_PROC_INST_ID= art.proc_inst_id_ WHERE 0=0 AND c.Id = ?";
        List<Map<String, Object>> mapList = this.getDao().createSQLQuery(sql, new Object[] { id }).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        return mapList.get(0);
    }

}
