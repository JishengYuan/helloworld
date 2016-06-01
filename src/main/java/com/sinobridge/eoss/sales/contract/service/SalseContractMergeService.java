/*
 * FileName: SalseContractMergeService.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.sales.contract.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinobridge.base.core.exception.SinoRuntimeException;
import com.sinobridge.base.core.hibernate.id.IdentifierGeneratorImpl;
import com.sinobridge.base.core.service.DefaultBaseService;
import com.sinobridge.eoss.business.order.model.BusinessOrderModel;
import com.sinobridge.eoss.sales.contract.dao.SalesContractStatusDao;
import com.sinobridge.eoss.sales.contract.dao.SalesProductDao;
import com.sinobridge.eoss.sales.contract.dao.SalesReceivePlanDao;
import com.sinobridge.eoss.sales.contract.dao.SalseContractMergeDao;
import com.sinobridge.eoss.sales.contract.model.SalesContractModel;
import com.sinobridge.eoss.sales.contract.model.SalesContractProductModel;
import com.sinobridge.eoss.sales.contract.model.SalesContractReceivePlanModel;
import com.sinobridge.eoss.sales.contract.model.SalesContractStatusModel;
import com.sinobridge.eoss.sales.contract.model.SalseContractMergeModel;
import com.sinobridge.eoss.sales.invoice.model.SalesInvoicePlanModel;
import com.sinobridge.eoss.sales.invoice.service.SalesInvoiceService;
import com.sinobridge.systemmanage.util.SQLUtil;
import com.sinobridge.systemmanage.util.StringUtil;

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
 * 2015年3月23日 下午2:54:09          guo_kemeng        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Service
@Transactional
public class SalseContractMergeService extends DefaultBaseService<SalseContractMergeModel, SalseContractMergeDao> {

    @Autowired
    SalesContractService contractService;

    @Autowired
    private SalesReceivePlanDao salesReceivePlanDao;
    @Autowired
    private SalesProductDao salesProductDao;
    @Autowired
    private SalesContractStatusDao salesContractStatusDao;

    @Autowired
    SalesInvoiceService salesInvoiceService;

    /**
     * <code>mergeContracts</code>
     *
     * @param contractId
     * @param ids
     * @since   2015年3月26日    guokemenng
     */
    public void mergeContracts(Long contractId, String ids) {
        try {
            SalesContractModel mainModel = null;
            List<SalesContractModel> salesList = contractService.getSalesByIds(ids);
            for (SalesContractModel m : salesList) {
                Long mId = m.getId();
                if (mId.equals(contractId)) {
                    mainModel = m;
                }
            }

            SalesContractModel modelB = new SalesContractModel();
            long modelBId = IdentifierGeneratorImpl.generatorLong();
            modelB.setId(modelBId);

            //复制主合同
            copyContractAToContractB(mainModel, modelB);

            BigDecimal t = mainModel.getContractAmount();
            String attachIds = mainModel.getAttachIds();
            Set<BusinessOrderModel> mainBusinessOrderModels = mainModel.getBusinessOrderModel();

            Set<SalesContractProductModel> salesContractProductSnapShoots = new HashSet<SalesContractProductModel>(0);
            Set<SalesContractReceivePlanModel> salesContractRecivePlanSnapShoots = new HashSet<SalesContractReceivePlanModel>(0);
            SalesContractStatusModel mainSalesContractStatusModel = salesContractStatusDao.findContractStatusByContractId(mainModel.getId());

            List<SalesContractModel> updateSalesList = new ArrayList<SalesContractModel>();

            for (SalesContractModel sale : salesList) {
                if (!sale.getId().equals(contractId)) {
                    //金额相加
                    t = t.add(sale.getContractAmount());
                    //附件相加
                    String salesAttachIds = sale.getAttachIds();
                    if (!StringUtil.isEmpty(salesAttachIds)) {
                        attachIds += "," + salesAttachIds;
                    }

                    //产品
                    Set<SalesContractProductModel> salesContractProductModels = sale.getSalesContractProductModel();
                    if (salesContractProductModels.size() > 0) {
                        Iterator<SalesContractProductModel> it = salesContractProductModels.iterator();
                        while (it.hasNext()) {
                            SalesContractProductModel p = new SalesContractProductModel();
                            copyProductAToProductB(it.next(), p, sale);
                            salesContractProductSnapShoots.add(p);
                        }
                    }

                    //付款计划
                    Set<SalesContractReceivePlanModel> salesContractReceivePlans = sale.getSalesContractReceivePlans();

                    if (salesContractReceivePlans.size() > 0) {
                        Iterator<SalesContractReceivePlanModel> it = salesContractReceivePlans.iterator();
                        while (it.hasNext()) {
                            SalesContractReceivePlanModel salesContractReceivePlanModel = it.next();

                            SalesContractReceivePlanModel contractRecivePlanSnapShootModel = new SalesContractReceivePlanModel();
                            copyReveivePlansAToReveivePlansB(salesContractReceivePlanModel, contractRecivePlanSnapShootModel, sale);
                            salesContractRecivePlanSnapShoots.add(contractRecivePlanSnapShootModel);
                        }
                    }

                    //订单
                    Set<BusinessOrderModel> businessOrderModels = sale.getBusinessOrderModel();
                    String businessOrderModelIds = "";
                    if (businessOrderModels.size() > 0) {
                        Iterator<BusinessOrderModel> it = businessOrderModels.iterator();
                        while (it.hasNext()) {
                            BusinessOrderModel ob = it.next();
                            businessOrderModelIds += ob.getId() + ",";
                            mainBusinessOrderModels.add(ob);
                        }
                        if (businessOrderModelIds.split(",").length > 0) {
                            businessOrderModelIds = businessOrderModelIds.substring(0, businessOrderModelIds.lastIndexOf(","));
                        }
                    }

                    //发票
                    DetachedCriteria detachedCriteria = DetachedCriteria.forClass(SalesInvoicePlanModel.class);
                    detachedCriteria.add(Restrictions.eq(SalesInvoicePlanModel.CONTRACTID, sale.getId()));
                    detachedCriteria.add(Restrictions.ne(SalesInvoicePlanModel.INVOICESTATUS, "FQ"));
                    detachedCriteria.add(Restrictions.ne(SalesInvoicePlanModel.INVOICESTATUS, "BG"));
                    List<SalesInvoicePlanModel> salesInvoicePlanModels = salesInvoiceService.getSalesInvoicePlanListByContractId(detachedCriteria);
                    String salesInvoicePlanIds = "";
                    for (SalesInvoicePlanModel salesInvoicePlanModel : salesInvoicePlanModels) {
                        salesInvoicePlanIds += salesInvoicePlanModel.getId() + ",";
                        salesInvoicePlanModel.setSalesContractId(mainModel.getId());
                        salesInvoicePlanModel.setSalesContractName(mainModel.getContractName());
                    }
                    if (!StringUtil.isEmpty(salesInvoicePlanIds)) {
                        if (salesInvoicePlanIds.split(",").length > 0) {
                            salesInvoicePlanIds = salesInvoicePlanIds.substring(0, salesInvoicePlanIds.lastIndexOf(","));
                        }
                    }

                    salesInvoiceService.getDao().saveOrUpdateAll(salesInvoicePlanModels);
                    //付款
                    updateFeeincome(mainModel, mainSalesContractStatusModel, sale);

                    sale.setContractRemark((sale.getContractRemark() == null ? "" : sale.getContractRemark()) + "订单：" + businessOrderModelIds);
                    sale.setContractRemark((sale.getContractRemark() == null ? "" : sale.getContractRemark()) + "发票：" + salesInvoicePlanIds);
                    //                    sale.setBusinessOrderModel(null);
                    updateSalesList.add(sale);
                }
            }

            //状态还原
            mainModel.setContractState("TGSH");

            //金额
            mainModel.setContractAmount(t);
            //清单
            mergeProduct(contractId, ids);
            //付款
            mergeReceivePlans(contractId, ids);
            //附件
            mainModel.setAttachIds(attachIds);
            //订单
            mainBusinessOrderModels.addAll(mainModel.getBusinessOrderModel());
            //            mainModel.setBusinessOrderModel(mainBusinessOrderModels);
            for (BusinessOrderModel order : mainBusinessOrderModels) {
                addProductOrder(order.getId(), mainModel.getId());
            }

            contractService.update(mainModel);

            salesProductDao.saveOrUpdateAll(salesContractProductSnapShoots);
            salesReceivePlanDao.saveOrUpdateAll(salesContractRecivePlanSnapShoots);
            contractService.getDao().saveOrUpdateAll(updateSalesList);

            salesContractStatusDao.update(mainSalesContractStatusModel);

        } catch (SinoRuntimeException e) {
            e.printStackTrace();
        }

    }

    /**
     * <code>addProductOrder</code>
     * 增加订单和合同的关联关系
     * @param orderId
     * @param salesId
     * @since   2015年3月27日    guokemenng
     */
    public void addProductOrder(Long orderId, Long salesId) {
        //解除订单关联
        String sql2 = "INSERT INTO order_contract_map SET sales_contract = '" + salesId + "',business_order = '" + orderId + "'";
        getDao().createSQLQuery(sql2).executeUpdate();
    }

    /**
     * <code>copyProductAToProductB</code>
     * 把A产品复制到B产品
     * @param productA
     * @param productB
     * @since   2015年3月26日    guokemenng
     */
    public void copyProductAToProductB(SalesContractProductModel salesContractProductModel, SalesContractProductModel contractProductSnapShootModel, SalesContractModel sale) {

        contractProductSnapShootModel.setSalesContractModel(sale);
        contractProductSnapShootModel.setInvoiceType(salesContractProductModel.getInvoiceType());

        contractProductSnapShootModel.setProductName(salesContractProductModel.getProductName());
        contractProductSnapShootModel.setProductNo(salesContractProductModel.getProductNo());
        contractProductSnapShootModel.setProductPartner(salesContractProductModel.getProductPartner());
        contractProductSnapShootModel.setProductPartnerName(salesContractProductModel.getProductPartnerName());
        contractProductSnapShootModel.setProductType(salesContractProductModel.getProductType());
        contractProductSnapShootModel.setProductTypeName(salesContractProductModel.getProductTypeName());
        contractProductSnapShootModel.setQuantity(salesContractProductModel.getQuantity());
        contractProductSnapShootModel.setRemark(salesContractProductModel.getRemark());
        contractProductSnapShootModel.setServiceEndDate(salesContractProductModel.getServiceEndDate());
        contractProductSnapShootModel.setServiceStartDate(salesContractProductModel.getServiceStartDate());
        contractProductSnapShootModel.setServiceType(salesContractProductModel.getServiceType());
        contractProductSnapShootModel.setTotalPrice(salesContractProductModel.getTotalPrice());
        contractProductSnapShootModel.setUnitPrice(salesContractProductModel.getUnitPrice());

        contractProductSnapShootModel.setIsReady(salesContractProductModel.getIsReady());
        contractProductSnapShootModel.setSurplusNum(salesContractProductModel.getSurplusNum());
        contractProductSnapShootModel.setRelateDeliveryProductId(salesContractProductModel.getRelateDeliveryProductId());

        contractProductSnapShootModel.setSerialNumber(salesContractProductModel.getSerialNumber());
        contractProductSnapShootModel.setServicePeriod(salesContractProductModel.getServicePeriod());
        contractProductSnapShootModel.setEquipmentSplace(salesContractProductModel.getEquipmentSplace());
    }

    /**
     * <code>copyReveivePlansAToReveivePlansB</code>
     * 把A收款计划复制到B收款计划
     * @param salesContractReceivePlanModel
     * @param contractRecivePlanSnapShootModel
     * @param sale
     * @since   2015年3月26日    guokemenng
     */
    public void copyReveivePlansAToReveivePlansB(SalesContractReceivePlanModel salesContractReceivePlanModel, SalesContractReceivePlanModel contractRecivePlanSnapShootModel, SalesContractModel sale) {

        contractRecivePlanSnapShootModel.setSalesContract(sale);
        contractRecivePlanSnapShootModel.setCreateTime(salesContractReceivePlanModel.getCreateTime());
        contractRecivePlanSnapShootModel.setCreator(salesContractReceivePlanModel.getCreator());
        contractRecivePlanSnapShootModel.setCurrentAccountId(salesContractReceivePlanModel.getCurrentAccountId());
        //        long contractRecivePlanSnapShootId = IdentifierGeneratorImpl.generatorLong();
        //        contractRecivePlanSnapShootModel.setId(contractRecivePlanSnapShootId);
        contractRecivePlanSnapShootModel.setModifier(salesContractReceivePlanModel.getModifier());
        contractRecivePlanSnapShootModel.setModifyTime(salesContractReceivePlanModel.getModifyTime());
        contractRecivePlanSnapShootModel.setPayCondition(salesContractReceivePlanModel.getPayCondition());
        contractRecivePlanSnapShootModel.setPlanedReceiveAmount(salesContractReceivePlanModel.getPlanedReceiveAmount());
        contractRecivePlanSnapShootModel.setPlanedReceiveDate(salesContractReceivePlanModel.getPlanedReceiveDate());
        contractRecivePlanSnapShootModel.setRemark(salesContractReceivePlanModel.getRemark());
        contractRecivePlanSnapShootModel.setStatus(salesContractReceivePlanModel.getStatus());
    }

    /**
     * <code>mergeProduct</code>
     * 清单合并
     * @param contractId
     * @param ids
     * @since   2015年3月26日    guokemenng
     */
    public void mergeProduct(Long contractId, String ids) {
        try {
            //合同产品
            String sql = "update sales_contract_product p set p.SaleContractId = '" + contractId + "' where p.SaleContractId in " + SQLUtil.makeInStr(ids.split(","));
            getDao().createSQLQuery(sql).executeUpdate();

            //订单产品
            String sql1 = "update business_order_product p set p.SaleContractId = '" + contractId + "' where p.SaleContractId in " + SQLUtil.makeInStr(ids.split(","));
            getDao().createSQLQuery(sql1).executeUpdate();

            //解除订单关联
            String sql2 = "delete from order_contract_map where sales_contract in " + SQLUtil.makeInStr(ids.split(","));
            getDao().createSQLQuery(sql2).executeUpdate();
        } catch (SinoRuntimeException e) {
            e.printStackTrace();
        }
    }

    /**
     * <code>mergeProduct</code>
     * 清单合并
     * @param contractId
     * @param ids
     * @since   2015年3月26日    guokemenng
     */
    public void mergeReceivePlans(Long contractId, String ids) {
        try {
            String sql = "update sales_contract_receive_plan p set p.SalesContractId = '" + contractId + "' where p.SalesContractId in " + SQLUtil.makeInStr(ids.split(","));
            getDao().createSQLQuery(sql).executeUpdate();
        } catch (SinoRuntimeException e) {
            e.printStackTrace();
        }
    }

    /**
     * <code>copyContractAToContractA</code>
     * 把A合同复制到B合同
     * @param modelA
     * @param modelB
     * @since   2015年3月26日    guokemenng
     */
    public void copyContractAToContractB(SalesContractModel modelA, SalesContractModel modelB) {
        try {
            modelB.setAccountCurrency(modelA.getAccountCurrency());
            modelB.setAttachIds(modelA.getAttachIds());
            Set<BusinessOrderModel> businessOrderModels = modelA.getBusinessOrderModel();
            String businessOrderModelIds = "";
            if (businessOrderModels.size() > 0) {
                Iterator<BusinessOrderModel> it = businessOrderModels.iterator();
                while (it.hasNext()) {
                    businessOrderModelIds += it.next().getId() + ",";
                }
                if (businessOrderModelIds.split(",").length > 0) {
                    businessOrderModelIds = businessOrderModelIds.substring(0, businessOrderModelIds.lastIndexOf(","));
                }
            }
            modelB.setContractRemark(modelA.getContractRemark() + "订单：" + businessOrderModelIds);
            modelB.setContractAmount(modelA.getContractAmount());
            modelB.setContractCode(modelA.getContractCode() + "_HB");
            modelB.setContractName(modelA.getContractName());
            modelB.setContractShortName(modelA.getContractShortName());
            modelB.setContractState(modelA.getContractState());
            modelB.setContractType(modelA.getContractType());
            modelB.setCreateTime(modelA.getCreateTime());
            modelB.setCustomerId(modelA.getCustomerId());
            modelB.setInvoiceType(modelA.getInvoiceType());
            modelB.setIsChanged(modelA.getIsChanged());
            modelB.setOrderProcessor(modelA.getOrderProcessor());
            modelB.setProcessInstanceId(modelA.getProcessInstanceId());
            modelB.setReceiveWay(modelA.getReceiveWay());

            modelB.setEstimateProfit(modelA.getEstimateProfit());
            modelB.setEstimateProfitDesc(modelA.getEstimateProfitDesc());
            modelB.setBusiEstimateProfit(modelA.getBusiEstimateProfit());
            modelB.setBusiEstimateProfitDesc(modelA.getBusiEstimateProfitDesc());
            modelB.setHopeArriveTime(modelA.getHopeArriveTime());
            modelB.setContractRemark(modelA.getContractRemark());
            modelB.setSalesStartDate(modelA.getSalesStartDate());
            modelB.setSalesEndDate(modelA.getSalesEndDate());
            modelB.setServiceStartDate(modelA.getServiceStartDate());
            modelB.setServiceEndDate(modelA.getServiceEndDate());
            modelB.setContractRemark(modelA.getContractRemark());
            modelB.setHopeArrivePlace(modelA.getHopeArrivePlace());
            modelB.setContractState("HB");

            //原始与产品一对多条目
            Set<SalesContractProductModel> salesContractProductModels = modelA.getSalesContractProductModel();
            //新的与产品一对多条目
            Set<SalesContractProductModel> salesContractProductSnapShoots = new HashSet<SalesContractProductModel>(0);
            if (salesContractProductModels.size() > 0) {
                Iterator<SalesContractProductModel> it = salesContractProductModels.iterator();
                while (it.hasNext()) {
                    SalesContractProductModel salesContractProductModel = it.next();

                    SalesContractProductModel contractProductSnapShootModel = new SalesContractProductModel();
                    //                    long contractProductSnapShootsId = IdentifierGeneratorImpl.generatorLong();
                    //                    contractProductSnapShootModel.setId(contractProductSnapShootsId);
                    contractProductSnapShootModel.setSalesContractModel(modelB);
                    contractProductSnapShootModel.setInvoiceType(salesContractProductModel.getInvoiceType());

                    contractProductSnapShootModel.setProductName(salesContractProductModel.getProductName());
                    contractProductSnapShootModel.setProductNo(salesContractProductModel.getProductNo());
                    contractProductSnapShootModel.setProductPartner(salesContractProductModel.getProductPartner());
                    contractProductSnapShootModel.setProductPartnerName(salesContractProductModel.getProductPartnerName());
                    contractProductSnapShootModel.setProductType(salesContractProductModel.getProductType());
                    contractProductSnapShootModel.setProductTypeName(salesContractProductModel.getProductTypeName());
                    contractProductSnapShootModel.setQuantity(salesContractProductModel.getQuantity());
                    contractProductSnapShootModel.setRemark(salesContractProductModel.getRemark());
                    contractProductSnapShootModel.setServiceEndDate(salesContractProductModel.getServiceEndDate());
                    contractProductSnapShootModel.setServiceStartDate(salesContractProductModel.getServiceStartDate());
                    contractProductSnapShootModel.setServiceType(salesContractProductModel.getServiceType());
                    contractProductSnapShootModel.setTotalPrice(salesContractProductModel.getTotalPrice());
                    contractProductSnapShootModel.setUnitPrice(salesContractProductModel.getUnitPrice());

                    contractProductSnapShootModel.setIsReady(salesContractProductModel.getIsReady());
                    contractProductSnapShootModel.setSurplusNum(salesContractProductModel.getSurplusNum());
                    contractProductSnapShootModel.setRelateDeliveryProductId(salesContractProductModel.getRelateDeliveryProductId());

                    contractProductSnapShootModel.setSerialNumber(salesContractProductModel.getSerialNumber());
                    contractProductSnapShootModel.setServicePeriod(salesContractProductModel.getServicePeriod());
                    contractProductSnapShootModel.setEquipmentSplace(salesContractProductModel.getEquipmentSplace());

                    salesContractProductSnapShoots.add(contractProductSnapShootModel);
                }
            }
            modelB.setSalesContractProductModel(salesContractProductSnapShoots);
            //原始与收款计划一对多条目
            Set<SalesContractReceivePlanModel> salesContractReceivePlans = modelA.getSalesContractReceivePlans();
            //新的与收款计划一对多条目
            Set<SalesContractReceivePlanModel> salesContractRecivePlanSnapShoots = new HashSet<SalesContractReceivePlanModel>(0);
            if (salesContractReceivePlans.size() > 0) {
                Iterator<SalesContractReceivePlanModel> it = salesContractReceivePlans.iterator();
                while (it.hasNext()) {
                    SalesContractReceivePlanModel salesContractReceivePlanModel = it.next();

                    SalesContractReceivePlanModel contractRecivePlanSnapShootModel = new SalesContractReceivePlanModel();

                    contractRecivePlanSnapShootModel.setSalesContract(modelB);
                    contractRecivePlanSnapShootModel.setCreateTime(salesContractReceivePlanModel.getCreateTime());
                    contractRecivePlanSnapShootModel.setCreator(salesContractReceivePlanModel.getCreator());
                    contractRecivePlanSnapShootModel.setCurrentAccountId(salesContractReceivePlanModel.getCurrentAccountId());
                    //                    long contractRecivePlanSnapShootId = IdentifierGeneratorImpl.generatorLong();
                    //                    contractRecivePlanSnapShootModel.setId(contractRecivePlanSnapShootId);
                    contractRecivePlanSnapShootModel.setModifier(salesContractReceivePlanModel.getModifier());
                    contractRecivePlanSnapShootModel.setModifyTime(salesContractReceivePlanModel.getModifyTime());
                    contractRecivePlanSnapShootModel.setPayCondition(salesContractReceivePlanModel.getPayCondition());
                    contractRecivePlanSnapShootModel.setPlanedReceiveAmount(salesContractReceivePlanModel.getPlanedReceiveAmount());
                    contractRecivePlanSnapShootModel.setPlanedReceiveDate(salesContractReceivePlanModel.getPlanedReceiveDate());
                    contractRecivePlanSnapShootModel.setRemark(salesContractReceivePlanModel.getRemark());
                    contractRecivePlanSnapShootModel.setStatus(salesContractReceivePlanModel.getStatus());
                    salesContractRecivePlanSnapShoots.add(contractRecivePlanSnapShootModel);
                }
            }

            modelB.setSalesContractReceivePlans(salesContractRecivePlanSnapShoots);

            SalesContractStatusModel oldSalesContractStatusModel = salesContractStatusDao.findContractStatusByContractId(modelA.getId());
            SalesContractStatusModel contractStatusModel = new SalesContractStatusModel();
            contractStatusModel.setCachetStatus(oldSalesContractStatusModel.getCachetStatus());
            contractStatusModel.setInvoiceStatus(oldSalesContractStatusModel.getInvoiceStatus());
            contractStatusModel.setOrderStatus(oldSalesContractStatusModel.getOrderStatus());
            contractStatusModel.setReciveStatus(oldSalesContractStatusModel.getReciveStatus());
            contractStatusModel.setId(IdentifierGeneratorImpl.generatorLong());
            contractStatusModel.setSaleContractId(modelB.getId());

            contractService.create(modelB);
            salesProductDao.saveOrUpdateAll(salesContractProductSnapShoots);
            salesContractStatusDao.save(contractStatusModel);
            salesReceivePlanDao.saveOrUpdateAll(salesContractRecivePlanSnapShoots);
        } catch (SinoRuntimeException e) {
            e.printStackTrace();
        }

    }

    /**
     * <code>getSalesContractByCode</code>
     * 根据合同编码得到合同
     * @param contractCode
     * @return
     * @since   2015年3月30日    guokemenng
     */
    public SalesContractModel getSalesContractByCode(String contractCode) {
        SalesContractModel contract = null;
        String hql = "from SalesContractModel where contractCode = '" + contractCode + "'";
        List<SalesContractModel> salesList = contractService.getDao().find(hql);
        if (salesList.size() > 0) {
            contract = salesList.get(0);
        }
        return contract;
    }

    /**
     * <code>updateFeeincome</code>
     * 把modelA的付款移动到mainModel合同上并改变状态
     * @param mainModel
     * @param modelA
     * @since   2015年7月28日    guokemenng
     */
    @SuppressWarnings("unchecked")
    public void updateFeeincome(SalesContractModel mainModel, SalesContractStatusModel mainStatus, SalesContractModel modelA) {
        String sql0 = "select * from business_sales_feeIncome i where i.SalesId = '" + modelA.getId() + "'";

        List<Map<String, Object>> mapList = getDao().createSQLQuery(sql0).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        String feeIncomeIds = "";
        for (Map<String, Object> m : mapList) {
            String id = m.get("id").toString();
            feeIncomeIds += id + ",";
        }
        if (!StringUtil.isEmpty(feeIncomeIds)) {
            if (feeIncomeIds.split(",").length > 0) {
                feeIncomeIds = feeIncomeIds.substring(0, feeIncomeIds.lastIndexOf(","));
                modelA.setContractRemark((modelA.getContractRemark() == null ? "" : modelA.getContractRemark()) + "付款：" + feeIncomeIds);
            }
        }

        String sql = "update business_sales_feeIncome set SalesId = '" + mainModel.getId() + "' where SalesId = '" + modelA.getId() + "'";
        getDao().createSQLQuery(sql).executeUpdate();

        //状态
        SalesContractStatusModel oldSalesContractStatusModel = salesContractStatusDao.findContractStatusByContractId(modelA.getId());
        String invoiceStatus = oldSalesContractStatusModel.getInvoiceStatus();
        String reciveStatus = oldSalesContractStatusModel.getReciveStatus();
        BigDecimal b = new BigDecimal("0");
        if (!"未收款".equals(reciveStatus) && !"已收款".equals(reciveStatus)) {
            b = new BigDecimal(reciveStatus);
        }
        if ("已收款".equals(reciveStatus)) {
            b = modelA.getContractAmount();
        }

        String mainReciveStatus = mainStatus.getReciveStatus();
        if (!"未申请".equals(invoiceStatus)) {
            mainStatus.setInvoiceStatus(invoiceStatus);
        }
        if (!"未收款".equals(mainReciveStatus) && !"已收款".equals(mainReciveStatus)) {
            mainStatus.setReciveStatus(new BigDecimal(mainReciveStatus).add(b).toString());
        }
        if ("已收款".equals(reciveStatus) && "已收款".equals(mainReciveStatus)) {
            mainStatus.setReciveStatus("已收款");
        }
        if ("未收款".equals(reciveStatus) && "未收款".equals(mainReciveStatus)) {
            mainStatus.setReciveStatus("未收款");
        }
    }


    /**
     * 根据合同ID，查询被合并的合同列表
     * @param salescontractId
     * @return
     */
    public List<SalesContractModel> findSonContract(Long salescontractId) {
        SalseContractMergeModel salseContractMergeModel = getDao().findbySalesContractId(salescontractId);
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(SalesContractModel.class);
        if (salseContractMergeModel != null) {
            String[] sids = salseContractMergeModel.getContractIds().split(",");
            Long[] ids = new Long[sids.length];
            for (int i = 0; i < sids.length; i++) {
                ids[i] = Long.parseLong(sids[i]);

            }
            detachedCriteria.add(Restrictions.in("id", ids));
            List<SalesContractModel> rs = contractService.findByCriteria(detachedCriteria);
            return rs;
        } else {
            return null;
        }
    }


    /**
     * @param id
     * @return
     */
    public SalseContractMergeModel getSalesMergeBySalesId(Long salesId) {
        SalseContractMergeModel merge = getDao().getSalesMergeBySalesId(salesId);
        return merge;
    }


}
