package com.sinobridge.eoss.business.order.service;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinobridge.base.core.exception.SinoRuntimeException;
import com.sinobridge.base.core.hibernate.id.IdentifierGeneratorImpl;
import com.sinobridge.base.core.service.DefaultBaseService;
import com.sinobridge.base.core.utils.JacksonJsonMapper;
import com.sinobridge.base.core.utils.PaginationSupport;
import com.sinobridge.eoss.bpm.service.process.ProcessInstService;
import com.sinobridge.eoss.bpm.service.process.ProcessService;
import com.sinobridge.eoss.business.interPurchas.model.InterPurchasModel;
import com.sinobridge.eoss.business.interPurchas.service.InterPurchasService;
import com.sinobridge.eoss.business.order.OrderConstants;
import com.sinobridge.eoss.business.order.constant.BusinessOrderContant;
import com.sinobridge.eoss.business.order.dao.BusinessOrderDao;
import com.sinobridge.eoss.business.order.dao.BusinessOrderProductDao;
import com.sinobridge.eoss.business.order.dao.BusinessPayOrderDao;
import com.sinobridge.eoss.business.order.dao.BusinessPaymentPlanDao;
import com.sinobridge.eoss.business.order.dao.BusinessReimbursementDao;
import com.sinobridge.eoss.business.order.model.BusinessChangeModel;
import com.sinobridge.eoss.business.order.model.BusinessOrderModel;
import com.sinobridge.eoss.business.order.model.BusinessOrderProductModel;
import com.sinobridge.eoss.business.order.model.BusinessPayOrderModel;
import com.sinobridge.eoss.business.order.model.BusinessPaymentPlanModel;
import com.sinobridge.eoss.business.order.model.BusinessReimbursementModel;
import com.sinobridge.eoss.business.order.utils.SendEmailUtils;
import com.sinobridge.eoss.business.stock.dao.StockDao;
import com.sinobridge.eoss.business.stock.model.BusinessInboundBill;
import com.sinobridge.eoss.business.stock.model.InboundModel;
import com.sinobridge.eoss.business.stock.model.StockModel;
import com.sinobridge.eoss.business.stock.service.BusinessInboundBillService;
import com.sinobridge.eoss.business.suppliermanage.dao.ReturnSpotDao;
import com.sinobridge.eoss.business.suppliermanage.dao.ReturnSpotLogDao;
import com.sinobridge.eoss.business.suppliermanage.model.ReturnSpotLogModel;
import com.sinobridge.eoss.business.suppliermanage.model.ReturnSpotModel;
import com.sinobridge.eoss.business.suppliermanage.service.ReturnSpotService;
import com.sinobridge.eoss.business.suppliermanage.service.SupplierContactsService;
import com.sinobridge.eoss.business.suppliermanage.service.SupplierInfoService;
import com.sinobridge.eoss.sales.contract.constant.SalesContractConstant;
import com.sinobridge.eoss.sales.contract.dao.SalesContractStatusDao;
import com.sinobridge.eoss.sales.contract.model.SalesContractModel;
import com.sinobridge.eoss.sales.contract.model.SalesContractStatusModel;
import com.sinobridge.eoss.sales.contract.service.SalesContractService;
import com.sinobridge.eoss.sales.contract.service.SalesContractStatusService;
import com.sinobridge.eoss.salesfundsCost.model.FundsSalesBusiReimbursement;
import com.sinobridge.eoss.salesfundsCost.service.FundsSalesBusiReimbursementService;
import com.sinobridge.systemmanage.util.StringUtil;
import com.sinobridge.systemmanage.vo.SystemUser;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author vermouth
 * @version 1.0

 * <p>
 * History:
 *
 * Date                     Author         Version     Description
 * ---------------------------------------------------------------------------------
 * 2015年11月18日 下午1:58:48          vermouth        1.0         To create
 * </p>
 *
 * @since
 * @see
 */
/**
 * <p>
 * Description:
 * </p>
 *
 * @author vermouth
 * @version 1.0

 * <p>
 * History:
 *
 * Date                     Author         Version     Description
 * ---------------------------------------------------------------------------------
 * 2015年11月18日 下午1:58:53          vermouth        1.0         To create
 * </p>
 *
 * @since
 * @see
 */
@Service
@Transactional
public class BusinessOrderService extends DefaultBaseService<BusinessOrderModel, BusinessOrderDao> {
    @Autowired
    private BusinessOrderProductService businessOrderProductService;
    @Autowired
    private SalesContractService saleContractService;
    @Autowired
    private SupplierInfoService supplierInfoService;
    @Autowired
    private ProcessService processService;
    @Autowired
    private SupplierContactsService supplierContactsService;
    @Autowired
    private BusinessReimbursementDao businessReimbursementDao;
    @Autowired
    private InterPurchasService interPurchasService;
    @Autowired
    private BusinessPaymentPlanDao businessPaymentPlanDao;
    @Autowired
    private BusinessOrderProductDao businessOrderProductDao;
    @Autowired
    private SalesContractStatusDao salesContractStatusDao;
    @Autowired
    private SalesContractStatusService salesContractStatusService;

    @Autowired
    private BusinessInboundBillService inboundBillService;
    @Autowired
    private ReturnSpotLogDao returnSpotLogDao;
    @Autowired
    private ReturnSpotDao returnSpotDao;
    @Autowired
    private StockDao stocKDao;
    @Autowired
    private BusinessChangeService businessChangeService;
    @Autowired
    private ReturnSpotService returnSpotService;
    @Autowired
    private ProcessInstService processInstService;
    @Autowired
    private FundsSalesBusiReimbursementService fundsSalesBusiReimbursementService;

    @Autowired
    private BusinessPayOrderDao payOrderDao;

    /**
    * <code>delete</code>
    *
    * @param id
    * @since   2014年5月12日   wangya
    */
    @Override
    public void delete(Serializable id) {
        BusinessOrderModel customerIndustry = this.get(id);
        getDao().delete(customerIndustry);

    }

    //删除多个订单
    public void deletes(String[] ids) {
        List<BusinessOrderModel> delOrders = new ArrayList<BusinessOrderModel>();
        List<BusinessPaymentPlanModel> delPaymentPlans = new ArrayList<BusinessPaymentPlanModel>();
        List<BusinessOrderProductModel> delProducts = new ArrayList<BusinessOrderProductModel>();
        List<BusinessReimbursementModel> delReim = new ArrayList<BusinessReimbursementModel>();

        for (String id : ids) {
            Long trueId = Long.parseLong(id);
            // getdelectSalesOrderMap(id);
            BusinessOrderModel businessOrder = getDao().get(trueId);
            delOrders.add(businessOrder);

            DetachedCriteria detachedCriteria = DetachedCriteria.forClass(BusinessPaymentPlanModel.class);
            detachedCriteria.add(Restrictions.eq("businessOrder.id", trueId));
            detachedCriteria.createAlias("businessOrder", BusinessPaymentPlanModel.ORDERID);
            List<BusinessPaymentPlanModel> oldPayment = businessPaymentPlanDao.findByCriteria(detachedCriteria);
            delPaymentPlans.addAll(oldPayment);

            DetachedCriteria detachedReim = DetachedCriteria.forClass(BusinessPaymentPlanModel.class);
            detachedCriteria.add(Restrictions.eq("businessOrder.id", trueId));
            detachedCriteria.createAlias("businessOrder", BusinessReimbursementModel.ORDERID);
            List<BusinessReimbursementModel> oldReim = businessReimbursementDao.findByCriteria(detachedReim);
            delReim.addAll(oldReim);

            DetachedCriteria detCriteriaProduct = DetachedCriteria.forClass(BusinessOrderProductModel.class);
            detCriteriaProduct.add(Restrictions.eq("businessOrder.id", trueId));
            detCriteriaProduct.createAlias("businessOrder", BusinessOrderProductModel.BUSINESSORDER);
            List<BusinessOrderProductModel> oldProduct = businessOrderProductDao.findByCriteria(detCriteriaProduct);
            delProducts.addAll(oldProduct);

        }
        businessPaymentPlanDao.deleteAll(delPaymentPlans);
        businessOrderProductDao.deleteAll(delProducts);
        getDao().deleteAll(delOrders);

    }

    /**
       * <code>doAddOrders</code>
       * 执行保存订单操作
     * @param spot
     * @param isAgree
     * @param amount
     * @param spotSupplier
     * @param spotSupplierId
       * @param map
       * @since   2014年6月6日    wangya
       */
    public String doAddOrders(BusinessOrderModel orderModel, SystemUser systemUser, String contract, String interPurchas, String tableData, int isAgree, float spot, String amount, String spotSupplier, String spotId) {
        try {
            long orderModelId = IdentifierGeneratorImpl.generatorLong();
            orderModel.setId(orderModelId);
            BigDecimal payAmount = new BigDecimal(0.00);
            orderModel.setPayAmount(payAmount);
            orderModel.setReimAmount(payAmount);
            //付款状态
            orderModel.setPayStatus(BusinessOrderContant.CG);
            //报销状态
            orderModel.setReimStatus(BusinessOrderContant.CG);
            //到货状态
            orderModel.setArrivalStatus(BusinessOrderContant.COMMODITY_NO);
            orderModel.setWareHouseStatus(BusinessOrderContant.WAREHOUSESTAATUS_NO);

            orderModel.setCreator(systemUser.getStaffName());
            orderModel.setCreatorId(systemUser.getUserName());
            Date d = new Date();
            orderModel.setCreateDate(d);
            Set<SalesContractModel> saleContractSet = new HashSet<SalesContractModel>();
            Set<InterPurchasModel> interPurchasSet = new HashSet<InterPurchasModel>();
            String contractIdwhere = "";
            //保存合同实体
            if (contract != null && contract != ",") {
                String[] contractId = contract.split(",");
                /*  String contractName = "";
                  contractName = getDao().findContractName(contractId);
                  if (!contractName.equals("")) {
                      return "合同：" + contractName + "已变更，无法下单。";
                  }*/
                for (int i = 0; i < contractId.length; i++) {
                    if (!StringUtil.isEmpty(contractId[i])) {
                        SalesContractModel saleContract = saleContractService.get(Long.parseLong(contractId[i]));
                        saleContractSet.add(saleContract);
                    }
                    contractIdwhere += "'" + contractId[i] + "',";
                }
                orderModel.setSalesContractModel(saleContractSet);
            }
            //String interPurchas = request.getParameter("interPurchas");
            //保存内采实体
            if (interPurchas != null && interPurchas != "") {
                String[] interPurchasId = interPurchas.split(",");
                for (int i = 0; i < interPurchasId.length; i++) {
                    if (!StringUtil.isEmpty(interPurchasId[i])) {
                        InterPurchasModel interPurchasModel = interPurchasService.get(Long.parseLong(interPurchasId[i]));

                        interPurchasSet.add(interPurchasModel);
                    }
                }
                orderModel.setInterPurchasModel(interPurchasSet);
            }
            // String tableData = request.getParameter("tableGridData");
            //保存订单产品
            List<Object> gridDataList = getTableGridData(tableData);
            if (gridDataList != null) {
                Map<String, Object> temp = new HashMap<String, Object>();
                for (int i = 0; i < gridDataList.size(); i++) {//加了非空判断
                    Map<String, Object> map = StringToMap(gridDataList.get(i).toString());//格式转换
                    if (map != null) {
                        String saleProId = map.get("column7").toString();
                        String productNo = map.get("column1").toString();
                        int quantity = Integer.parseInt(map.get("column3").toString());
                        if (temp.get(saleProId) != null) {
                            quantity = quantity + Integer.parseInt(temp.get(saleProId).toString());
                            int realQua = businessOrderProductService.findQuantity(Long.parseLong(saleProId));
                            if (quantity > realQua) {
                                return "[" + productNo + "]设备数量填写，大于合同中的数量！";
                            }
                        } else {
                            temp.put(saleProId, quantity);
                        }
                    }
                }
                /*  List<Map<String, Object>> rs = businessOrderProductService.getAllProcductsbycontractId(contractIdwhere);
                
                  for (Map<String, Object> map : rs) {
                      if (temp.get(map.get("productNo")) != null) {
                          if (Integer.parseInt(temp.get(map.get("productNo")).toString()) > Integer.parseInt(map.get("proNums").toString())) {
                              //说明页面填写的数量大于合同中的数量
                              return map.get("productNo").toString();
                          }
                      }
                  }*/
                if (isAgree == 1 && spot > 0) {
                    orderModel.setSpotNum(spot);//存返点数
                    orderModel.setSpotSupplier(spotSupplier);
                    //ReturnSpotModel spotModel = getSupplierSpot(Long.parseLong(spotSupplierId));
                    ReturnSpotModel spotModel = returnSpotService.get(Long.parseLong(spotId));
                    float num = spotModel.getReturnAmount();
                    num = num - spot;
                    spotModel.setReturnAmount(num);
                    returnSpotDao.saveOrUpdate(spotModel);
                    createrSpotLogs(spot, spotModel, systemUser.staffName, d, orderModelId, 0);//创建返点日志
                } else {
                    orderModel.setSpotNum(0);
                }
                //-------------------------
                if (orderModel.getOrderStatus().equals(BusinessOrderContant.ORDER_SH)) {
                    //创建工单
                    creatProcInst(orderModel, orderModelId, systemUser, interPurchas, contract, amount);
                }
                getDao().save(orderModel);
                //---------------------------
                businessOrderProductService.saveProducts(orderModel, gridDataList);
                //                orderModel.setBusinessOrderProductModel(businessOrderProductModels);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "false";
        }
        return "true";
    }

    /**保存返点日志
     * @param spot
     * @param spotModel
     * @param userName
     * @param d
     * @param orderModelId
     * @param subtract
     */
    private void createrSpotLogs(float spot, ReturnSpotModel spotModel, String userName, Date d, long orderModelId, float subtract) {
        ReturnSpotLogModel logs = new ReturnSpotLogModel();//存返点日志
        logs.setBanlance(spot);
        logs.setAmount(subtract);
        logs.setOperaotor(userName);
        logs.setOperatorTime(d);
        logs.setRemark(Long.toString(orderModelId));
        logs.setSpotModel(spotModel);
        returnSpotLogDao.save(logs);
    }

    /**
     * <code>creatProcInst</code>
     * 创建工单
     * @param amount
     * @param map
     * @since   2014年6月6日    wangya
     */
    private void creatProcInst(BusinessOrderModel orderModel, long orderModelId, SystemUser systemUser, String interPurchas, String contract, String amount) {
        //改内采中的订单状态
        if (interPurchas != null && interPurchas != "") {
            String[] interPurchasId = interPurchas.split(",");
            for (int i = 0; i < interPurchasId.length; i++) {
                if (!StringUtil.isEmpty(interPurchasId[i])) {
                    getDao().getInterStatus(Long.parseLong(interPurchasId[i]));
                }
            }
        }
        //改合同中的订单状态
        if (contract != null && contract != "") {
            String[] contractId = contract.split(",");
            for (int i = 0; i < contractId.length; i++) {
                if (!StringUtil.isEmpty(contractId[i])) {
                    // SalesContractModel saleContract=saleContractService.get(Long.parseLong(contractId[i]));
                    SalesContractStatusModel status = getDao().salesStart(Long.parseLong(contractId[i]));
                    status.setOrderStatus(SalesContractConstant.CONTRACT_ORDER_APPLY);
                    salesContractStatusService.saveOrUpdate(status);
                }
            }
        }
        Long valId = processService.nextValId();
        orderModel.setProcessInstanceId(valId);
        Map<String, Object> variableMap = new HashMap<String, Object>();
        variableMap.put("orderId", orderModelId);
        String name = orderModel.getOrderName() + "[￥" + amount + "]";
        //创建工单
        Long[] procInstId = processService.create(valId, name, systemUser.getUserName(), BusinessOrderContant.ORDER_KEY, 1, variableMap, null, null, orderModel.getOrderCode());
        System.out.println("创建工单成功：" + procInstId[0]);

    }

    /**保存重新提交的订单
     * @param orderModel
     * @param contract
     * @param interPurchas
     * @param tableData
     * @param taskId
     * @param systemUser
     * @param spot
     * @param isAgree
     * @param oldSpotNum
     * @param oldspotSupplier
     * @param spotSupplier
     * @return
     */
    public String doReviewUpdate(BusinessOrderModel orderModel, String contract, String interPurchas, String tableData, String taskId, SystemUser systemUser, float spot, int isAgree, float oldSpotNum, String oldspotSupplier, String spotSupplier) {
        try {
            Long id = orderModel.getId();
            Date d = new Date();
            orderModel.setCreateDate(d);
            BigDecimal payAmount = new BigDecimal(0.00);
            orderModel.setPayAmount(payAmount);
            orderModel.setReimAmount(payAmount);
            //付款状态
            orderModel.setPayStatus(BusinessOrderContant.CG);
            //报销状态
            orderModel.setReimStatus(BusinessOrderContant.CG);
            //到货状态
            orderModel.setArrivalStatus(BusinessOrderContant.COMMODITY_NO);
            orderModel.setWareHouseStatus(BusinessOrderContant.WAREHOUSESTAATUS_NO);
            String contractIdwhere = "";
            if (contract != null || contract != "") {//合同ID
                String[] contractId = contract.split(",");
                Set<SalesContractModel> saleContractSet = new HashSet<SalesContractModel>();
                for (int i = 0; i < contractId.length; i++) {
                    /* String contractName = "";
                     contractName = getDao().findContractName(contractId);
                     if (!contractName.equals("")) {
                         return "合同：" + contractName + "已变更，无法下单。";
                     }*/
                    if (!StringUtil.isEmpty(contractId[i])) {
                        SalesContractModel saleContract = saleContractService.get(Long.parseLong(contractId[i]));
                        saleContractSet.add(saleContract);
                    }
                    contractIdwhere += "'" + contractId[i] + "',";
                }
                orderModel.setSalesContractModel(saleContractSet);
            }
            if (interPurchas != null || interPurchas != "") {//内采ID
                String[] interPurchasId = interPurchas.split(",");
                Set<InterPurchasModel> interPurchasSet = new HashSet<InterPurchasModel>();
                for (int i = 0; i < interPurchasId.length; i++) {
                    if (!StringUtil.isEmpty(interPurchasId[i])) {
                        InterPurchasModel interPurchasModel = interPurchasService.get(Long.parseLong(interPurchasId[i]));
                        interPurchasSet.add(interPurchasModel);
                    }
                }
                orderModel.setInterPurchasModel(interPurchasSet);
            }
            List<Object> gridDataList = getTableGridData(tableData);
            Date time = new Date();
            if (isAgree == 1 && spot > 0) {//是否用返点
                if (oldSpotNum != spot || !oldspotSupplier.equals(spotSupplier)) {//返点是否有变化
                    orderModel.setSpotNum(spot);//存返点数
                    orderModel.setSpotSupplier(spotSupplier);//存返点厂商
                    if (oldspotSupplier.equals(spotSupplier)) {//如果使用的厂商一样
                        ReturnSpotModel spotModel = getSupplierNameSpot(oldspotSupplier);
                        float num = spotModel.getReturnAmount() - spot + oldSpotNum;
                        spotModel.setReturnAmount(num);
                        returnSpotDao.saveOrUpdate(spotModel);
                        float plus = 0;
                        float subtract = 0;
                        if (spot > oldSpotNum) {
                            plus = spot - oldSpotNum;
                        } else {
                            subtract = oldSpotNum - spot;
                        }
                        createrSpotLogs(plus, spotModel, systemUser.staffName, time, orderModel.getId(), subtract);//创建返点日志
                    } else {//如果使用的厂商不一样
                        List<ReturnSpotModel> spots = new ArrayList<ReturnSpotModel>();
                        ReturnSpotModel newSpot = getSupplierNameSpot(spotSupplier);//新厂商减点
                        float newNum = newSpot.getReturnAmount() - spot;
                        newSpot.setReturnAmount(newNum);
                        spots.add(newSpot);
                        createrSpotLogs(spot, newSpot, systemUser.staffName, time, orderModel.getId(), 0);//创建返点日志
                        if (oldSpotNum != 0) {
                            ReturnSpotModel oldSpot = getSupplierNameSpot(oldspotSupplier);//旧厂商加点
                            float oldNum = oldSpot.getReturnAmount() + oldSpotNum;
                            oldSpot.setReturnAmount(oldNum);
                            spots.add(oldSpot);
                            returnSpotDao.saveOrUpdateAll(spots);
                            createrSpotLogs(0, oldSpot, systemUser.staffName, time, orderModel.getId(), oldSpotNum);//创建返点日志
                        }
                    }
                }
            } else {
                orderModel.setSpotNum(0);
                if (oldSpotNum != 0) {
                    ReturnSpotModel oldSpot = getSupplierNameSpot(oldspotSupplier);
                    float oldNum = oldSpot.getReturnAmount() + oldSpotNum;
                    oldSpot.setReturnAmount(oldNum);
                    returnSpotDao.saveOrUpdate(oldSpot);
                    createrSpotLogs(0, oldSpot, systemUser.staffName, time, orderModel.getId(), oldSpotNum);//创建返点日志
                }

            }
            if (gridDataList != null) {//产品是否重复添加
                Map<String, Object> temp = new HashMap<String, Object>();
                for (int i = 0; i < gridDataList.size(); i++) {//加了非空判断
                    Map<String, Object> map = StringToMap(gridDataList.get(i).toString());//格式转换
                    if (map != null) {
                        String saleProId = map.get("column7").toString();
                        String productNo = map.get("column1").toString();
                        int quantity = Integer.parseInt(map.get("column3").toString());
                        if (temp.get(saleProId) != null) {
                            quantity = quantity + Integer.parseInt(temp.get(saleProId).toString());
                            int realQua = businessOrderProductService.findQuantity(Long.parseLong(saleProId));
                            if (quantity > realQua) {
                                return "[" + productNo + "]设备数量填写，大于合同中的数量！";
                            }
                        } else {
                            temp.put(saleProId, quantity);
                        }

                    }
                }
                /*if (gridDataList != null) {//订单产品
                    Map<String, Object> temp = new HashMap<String, Object>();
                    for (int i = 0; i < gridDataList.size(); i++) {//加了非空判断
                        Map<String, Object> map = StringToMap(gridDataList.get(i).toString());//格式转换
                        if (map != null) {
                            String productNo = map.get("column1").toString();
                            int quantity = Integer.parseInt(map.get("column3").toString());
                            if (temp.get(productNo) != null) {
                                quantity = quantity + Integer.parseInt(temp.get(productNo).toString());
                                temp.put(productNo, quantity);
                            } else {
                                temp.put(productNo, quantity);
                            }
                
                        }
                    }
                    List<Map<String, Object>> rs = businessOrderProductService.getAllProcductsbycontractId(contractIdwhere);
                
                    for (Map<String, Object> map : rs) {
                        if (temp.get(map.get("productNo")) != null) {
                            if (Integer.parseInt(temp.get(map.get("productNo")).toString()) > Integer.parseInt(map.get("proNums").toString())) {
                                //说明页面填写的数量大于合同中的数量
                                return map.get("productNo").toString();
                            }
                        }
                
                    }*/
                //更新流程变量-------------------------
                if (orderModel.getOrderStatus().equals(BusinessOrderContant.ORDER_SH)) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    //从新提交订单时提供的key值
                    map.put(BusinessOrderContant.IS_RE_SUBMIT, true);
                    processService.handle(taskId, systemUser.getUserName(), map, null, null);
                }
                //---------------------------
                if (id != null) {//删除原先的产品
                    businessOrderProductService.deleteOrderProduct(id);
                }
                getDao().saveOrUpdate(orderModel);
                String title = orderModel.getOrderName() + "[" + orderModel.getOrderAmount() + "]";
                //保存产品信息
                businessOrderProductService.saveProducts(orderModel, gridDataList);
                //更新流程标题
                getDao().updateOrderTitle(title, orderModel.getProcessInstanceId());
                //orderModel.setBusinessOrderProductModel(businessOrderProductModels);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "false";
        }
        return "ture";
    }

    /**保存订单
     * @param orderModel
     * @param systemUser
     * @param contract
     * @param interPurchas
     * @param tableData
     * @param spot
     * @param isAgree
     * @param oldSpotNum
     * @param amount
     * @param oldspotSupplier
     * @param spotSupplier
     * @return
     */
    public String doSaveOrUpdate(BusinessOrderModel orderModel, SystemUser systemUser, String contract, String interPurchas, String tableData, float spot, int isAgree, float oldSpotNum, String amount, String oldspotSupplier, String spotSupplier) {
        try {
            Long id = orderModel.getId();
            orderModel.setCreator(systemUser.getStaffName());
            orderModel.setCreatorId(systemUser.getUserName());
            /* Date d = new Date();
             orderModel.setCreateDate(d);*/
            BigDecimal payAmount = new BigDecimal(0.00);
            orderModel.setPayAmount(payAmount);
            orderModel.setReimAmount(payAmount);
            //付款状态
            orderModel.setPayStatus(BusinessOrderContant.CG);
            //报销状态
            orderModel.setReimStatus(BusinessOrderContant.CG);
            //到货状态
            orderModel.setArrivalStatus(BusinessOrderContant.COMMODITY_NO);
            orderModel.setWareHouseStatus(BusinessOrderContant.WAREHOUSESTAATUS_NO);

            //String contract = request.getParameter("contract");
            String[] contractId = contract.split(",");
            String contractIdwhere = "";
            Set<SalesContractModel> saleContractSet = new HashSet<SalesContractModel>();
            int flat = 0;
            for (int i = 0; i < contractId.length; i++) {
                String contractName = "";
                contractName = getDao().findContractName(contractId);
                if (!contractName.equals("")) {//判断合同状态，若是变更中的合同，提示无法下单
                    return "合同：" + contractName + "已变更，无法下单。";
                }
                if (!StringUtil.isEmpty(contractId[i])) {
                    SalesContractModel saleContract = saleContractService.get(Long.parseLong(contractId[i]));
                    saleContractSet.add(saleContract);
                    flat = 1;
                }
                contractIdwhere += "'" + contractId[i] + "',";
            }
            orderModel.setSalesContractModel(saleContractSet);
            //String interPurchas = request.getParameter("interPurchas");
            //內采---------------------------------------------
            String[] interPurchasId = interPurchas.split(",");
            Set<InterPurchasModel> interPurchasSet = new HashSet<InterPurchasModel>();
            for (int i = 0; i < interPurchasId.length; i++) {
                if (!StringUtil.isEmpty(interPurchasId[i])) {
                    InterPurchasModel interPurchasModel = interPurchasService.get(Long.parseLong(interPurchasId[i]));
                    interPurchasSet.add(interPurchasModel);
                }
            }
            orderModel.setInterPurchasModel(interPurchasSet);

            Date time = new Date();
            if (isAgree == 1 && spot > 0) {//是否用返点
                if (oldSpotNum != spot || !oldspotSupplier.equals(spotSupplier)) {//返点是否有变化
                    orderModel.setSpotNum(spot);//存返点数
                    orderModel.setSpotSupplier(spotSupplier);//存返点厂商
                    if (oldspotSupplier.equals(spotSupplier)) {//如果使用的厂商一样
                        ReturnSpotModel spotModel = getSupplierNameSpot(oldspotSupplier);
                        float num = spotModel.getReturnAmount() - spot + oldSpotNum;
                        spotModel.setReturnAmount(num);
                        returnSpotDao.saveOrUpdate(spotModel);
                        float plus = 0;
                        float subtract = 0;
                        if (spot > oldSpotNum) {
                            plus = spot - oldSpotNum;
                        } else {
                            subtract = oldSpotNum - spot;
                        }
                        createrSpotLogs(plus, spotModel, systemUser.staffName, time, orderModel.getId(), subtract);//创建返点日志
                    } else {//如果使用的厂商不一样
                        List<ReturnSpotModel> spots = new ArrayList<ReturnSpotModel>();
                        ReturnSpotModel newSpot = getSupplierNameSpot(spotSupplier);//新厂商减点
                        float newNum = newSpot.getReturnAmount() - spot;
                        newSpot.setReturnAmount(newNum);
                        spots.add(newSpot);
                        createrSpotLogs(spot, newSpot, systemUser.staffName, time, orderModel.getId(), 0);//创建返点日志
                        if (oldSpotNum != 0) {
                            ReturnSpotModel oldSpot = getSupplierNameSpot(oldspotSupplier);//旧厂商加点
                            float oldNum = oldSpot.getReturnAmount() + oldSpotNum;
                            oldSpot.setReturnAmount(oldNum);
                            spots.add(oldSpot);
                            createrSpotLogs(0, oldSpot, systemUser.staffName, time, orderModel.getId(), oldSpotNum);//创建返点日志
                        }
                        returnSpotDao.saveOrUpdateAll(spots);
                    }
                }
            } else {
                orderModel.setSpotNum(0);
                if (oldSpotNum != 0) {
                    ReturnSpotModel oldSpot = getSupplierNameSpot(oldspotSupplier);
                    float oldNum = oldSpot.getReturnAmount() + oldSpotNum;
                    oldSpot.setReturnAmount(oldNum);
                    returnSpotDao.saveOrUpdate(oldSpot);
                    createrSpotLogs(0, oldSpot, systemUser.staffName, time, orderModel.getId(), oldSpotNum);//创建返点日志
                }
            }

            List<Object> gridDataList = getTableGridData(tableData);
            if (gridDataList != null) {
                Map<String, Object> temp = new HashMap<String, Object>();
                if (flat == 1) {
                    for (int i = 0; i < gridDataList.size(); i++) {//加了非空判断
                        Map<String, Object> map = StringToMap(gridDataList.get(i).toString());//格式转换
                        if (map != null) {
                            String saleProId = map.get("column7").toString();
                            String productNo = map.get("column1").toString();
                            int quantity = Integer.parseInt(map.get("column3").toString());
                            if (temp.get(saleProId) != null) {
                                quantity = quantity + Integer.parseInt(temp.get(saleProId).toString());
                                int realQua = businessOrderProductService.findQuantity(Long.parseLong(saleProId));
                                if (quantity > realQua) {
                                    return "[" + productNo + "]设备数量填写，大于合同中的数量！";
                                }
                            } else {
                                temp.put(saleProId, quantity);
                            }

                        }
                    }
                }
                /* List<Map<String, Object>> rs = businessOrderProductService.getAllProcductsbycontractId(contractIdwhere);
                
                 for (Map<String, Object> map : rs) {
                     if (temp.get(map.get("productNo")) != null) {
                         if (Integer.parseInt(temp.get(map.get("productNo")).toString()) > Integer.parseInt(map.get("proNums").toString())) {
                             //说明页面填写的数量大于合同中的数量
                             return map.get("productNo").toString();
                         }
                     }
                
                 }*/
                if (id != null) {
                    businessOrderProductService.deleteOrderProduct(id);
                }
                getDao().saveOrUpdate(orderModel);
                //创建流程-------------------------
                if (orderModel.getOrderStatus().equals(BusinessOrderContant.ORDER_SH)) {
                    creatProcInst(orderModel, id, systemUser, interPurchas, contract, amount);
                }
                //---------------------------

                businessOrderProductService.saveProducts(orderModel, gridDataList);
                //orderModel.setBusinessOrderProductModel(businessOrderProductModels);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "false";
        }
        return "true";
    }

    /**删除订单
     * @param systemUser
     * @param orderModel
     */
    public void endOrder(SystemUser systemUser, BusinessOrderModel orderModel) {
        Long id = orderModel.getId();
        BusinessOrderModel order = this.get(id);
        if (order.getSpotNum() != 0) {//是否用返点，若用过返点，需加回返点数
            ReturnSpotModel oldSpot = getSupplierNameSpot(order.getSpotSupplier());//旧厂商加点
            float oldNum = oldSpot.getReturnAmount() + order.getSpotNum();
            oldSpot.setReturnAmount(oldNum);
            returnSpotDao.saveOrUpdate(oldSpot);
            Date time = new Date();
            createrSpotLogs(0, oldSpot, systemUser.staffName, time, orderModel.getId(), order.getSpotNum());//创建返点日志
        }
        Set<SalesContractModel> sales = order.getSalesContractModel();
        Iterator<SalesContractModel> s = sales.iterator();
        while (s.hasNext()) {
            SalesContractModel sale = s.next();
            List<BusinessOrderModel> gorder = getDao().findOrderInSales(sale.getId());//查询相关合同
            if (gorder.size() == 1) {
                getDao().updateConOrderStatus(sale.getId());//更新合同订单状态
            }
        }
        try {
            processInstService.removeProcessInst(order.getProcessInstanceId().toString());//退出流程
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        getDao().delete(order);
        // Set<BusinessOrderProductModel> products = order.getBusinessOrderProductModel();
        //businessOrderProductDao.deleteAll(products);
        /* getDao().endOrderStatus(id);
         Map<String, Object> map = new HashMap<String, Object>();
         //从新提交订单时提供的key值
         map.put(BusinessOrderContant.IS_RE_SUBMIT, false);
         String taskId = request.getParameter("taskId");
         //processService.handle(taskId, systemUser.getUserName(), map, null, null);
        */
    }

    /** 待处理的合同
     * @param searchMap
     * @param parseInt
     * @param pageSize
     * @param flat
     * @return
     */
    public PaginationSupport findCloseContract(HashMap<String, Object> searchMap, int parseInt, Integer pageSize, int flat) {
        return getDao().findCloseContract(searchMap, parseInt, pageSize, flat);
    }

    /**
    * <code>findSalesContractsByCriteria</code>
    * 条件查询符合的List
    * @param detachedCriteria 查询条件
    * @since 2014年6月25日  wangay
    */
    public List<BusinessOrderModel> findOrderByCriteria(DetachedCriteria detachedCriteria) {
        List<BusinessOrderModel> businessOrderModel = new ArrayList<BusinessOrderModel>();
        businessOrderModel = getDao().findByCriteria(detachedCriteria);
        return businessOrderModel;
    }

    /**订单选合同
     * @param searchMap
     * @param parseInt
     * @param iDisplayLength
     * @return
     */
    public PaginationSupport findSalesContract(HashMap<String, Object> searchMap, Integer parseInt, Integer iDisplayLength) {
        return getDao().findSalesContract(searchMap, parseInt, iDisplayLength);
    }

    /**
     * <code>getBizOwner</code>
     * 得到商务负责人集合
     * @return
     * @since   2014年5月23日   wangya
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> getOrderCreator() {
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        String[] param = new String[2];
        param[0] = OrderConstants.BIZOWNERASSIGN;
        param[1] = "1";
        String sql = "select staffId,staffName from sys_staff where staffId in (select staffId from sys_stafforg where orgId = ?) and state = ?";
        Query query = this.getDao().createSQLQuery(sql, param);
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List<Map<String, Object>> objList = query.list();
        for (Map<String, Object> objMap : objList) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", objMap.get("staffId"));
            map.put("name", objMap.get("staffName"));
            mapList.add(map);
        }
        return mapList;
    }

    /**查询订单产品
     * @param id
     * @return
     */
    public List<BusinessOrderProductModel> getOrderProduct(String id) {
        List<BusinessOrderProductModel> orderProduct = getDao().getOrderProduct(id);
        return orderProduct;
    }

    /**
      * <code>getOrgFullName</code>
      * 得到部门集合
      * @return
      * @since   2014年7月16日   wangya
      */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> getOrgFullName() {
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        String sql = "select OrgFullName from sys_orgnization";
        Query query = this.getDao().createSQLQuery(sql);
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List<Map<String, Object>> objList = query.list();
        for (Map<String, Object> objMap : objList) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", objMap.get("OrgId"));
            map.put("name", objMap.get("OrgFullName"));
            mapList.add(map);
        }
        return mapList;

    }

    /**
      * <code>getSupplierCode</code>
      * 得到供应商编码
      * @param id
      * @return
      * @since   2014年6月23日   wangya
      */
    public List<Map<String, Object>> getSupplierCode(String id) {
        List<Map<String, Object>> mapList = getDao().getSupplierCode(id);
        return mapList;
    }

    /**
     * <code>getSupplierContacts</code>
     * 得到供应商联系人
     * @param id
     * @return
     * @since   2014年6月23日   wangya
     */
    public List<Map<String, Object>> getSupplierContacts(String id) {
        List<Map<String, Object>> objList = getDao().getSupplierContacts(id);
        return objList;
    }

    /**
      * <code>getSupplierShortName</code>
      * 得到供应商简称集合
      * @return
      * @since   2014年7月16日   wangya
      */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> getSupplierShortName() {
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        String sql = "select id,ShortName from bussiness_supplier";
        Query query = this.getDao().createSQLQuery(sql);
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List<Map<String, Object>> objList = query.list();
        for (Map<String, Object> objMap : objList) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", objMap.get("id"));
            map.put("name", objMap.get("ShortName"));
            mapList.add(map);
        }
        return mapList;
    }

    @SuppressWarnings("unchecked")
    private List<Object> getTableGridData(String tableData) {
        ObjectMapper objectMapper = JacksonJsonMapper.getInstance();
        Map<String, Object> tableDataMap = null;
        List<Object> gridDataList = new ArrayList<Object>();
        try {
            tableDataMap = objectMapper.readValue(tableData, HashMap.class);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (tableDataMap != null && tableDataMap.size() > 0) {
            for (String key : tableDataMap.keySet()) {
                gridDataList = (List<Object>) tableDataMap.get(key);
            }
        }
        return gridDataList;
    }

    /**保存客户签收单
     * @param orderModel
     * @param fileId
     */
    public void savefile(BusinessOrderModel orderModel, String fileId) {
        Long id = orderModel.getId();
        BusinessOrderModel order = this.get(id);
        order.setArrivalTime(orderModel.getArrivalTime());
        if (!fileId.equals("")) {//上传客户签收单
            order.setAttachIds(fileId);
            order.setArrivalStatus(BusinessOrderContant.COMMODITY);
            Set<SalesContractModel> sales = order.getSalesContractModel();
            Iterator<SalesContractModel> it = sales.iterator();
            while (it.hasNext()) {
                SalesContractModel s = it.next();
                Set<BusinessOrderModel> business = s.getBusinessOrderModel();
                Iterator<BusinessOrderModel> bs = business.iterator();
                int flat = 0;
                while (bs.hasNext()) {//判断到货状态是否已经改变
                    BusinessOrderModel b = bs.next();
                    String arrival = b.getArrivalStatus();
                    if (!arrival.equals(BusinessOrderContant.COMMODITY)) {
                        flat = 1;
                    }
                }
                if (flat == 0) {//若没有，则修改合同订单状态
                    //合同订单状态：已到货
                    // getDao().getArrivalStatus(s.getId());
                    SalesContractStatusModel status = salesContractStatusDao.contractOrderStatus(s.getId());
                    status.setOrderStatus(SalesContractConstant.CONTRACT_ORDER_REACHCUSTOM);
                    salesContractStatusDao.update(status);
                }
            }
        }
        // getDao().savefile(id, fileId);
        this.update(order);
        //getDao().flush();

    }

    /**
      * <code>StringToMap</code>
      *
      * @param mapText
      * @return
      * @since   2014年6月6日   wangya
      */
    public synchronized Map<String, Object> StringToMap(String mapText) {
        if (mapText == null || mapText.equals("")) {
            return null;
        }
        mapText = mapText.substring(1, mapText.length() - 1);
        Map<String, Object> map = new HashMap<String, Object>();
        String[] text = mapText.split(",");
        for (String str : text) {
            String[] keyText = str.split("="); // 转换key与value的数组
            if (keyText.length < 1) {
                continue;
            }
            if (keyText.length == 1) {
            } else {
                String key = keyText[0]; // key
                String value = keyText[1]; // value
                if (!value.trim().equals("")) {
                    map.put(key, value);
                }
            }
        }
        return map;
    }

    /**更新状态为审批通过
     * @param id
     */
    public void updateOrderStatus(Long id) {
        //更新状态为审批通过
        getDao().orderOK(id);
    }

    /**
     * 采购申请审批通过，更新合同关联状态为采购中
     * @param id
     */
    public void updateSaleSatatus(Long id) {
        BusinessOrderModel orderModel = this.get(id);
        Set<SalesContractModel> salesContract = orderModel.getSalesContractModel();
        Iterator<SalesContractModel> it = salesContract.iterator();
        while (it.hasNext()) {
            SalesContractModel s = it.next();
            Long salesId = s.getId();
            String orderStatus = salesContractStatusService.changeContractStatus(String.valueOf(salesId), "order", String.valueOf(id));
            //合同中的订单状态为采购中
            SalesContractStatusModel status = salesContractStatusDao.contractOrderStatus(salesId);
            status.setOrderStatus(orderStatus);
            salesContractStatusDao.update(status);
            //System.out.println(status);
        }
        //************************王总审批通过后，若此订单下的是备货合同，并且关联的产品合同通过审批了，生成中间数据，计算备货部分的资金占用成本 Start************
        //getDao().getCpSales(id);//改到订单付款部分生成此表

    }

    /*
        *//**查询合同产品
          * @param id
          * @return
          */
    /*
    public List<SalesContractProductModel> findProduct(Long id) {
     List<SalesContractProductModel> product = getDao().findProduct(id);
     return product;
    }*/

    /**查询所有订单的总金额
     * @param searchMap
     * @return
     */
    public String findTotallAmount(HashMap<String, Object> searchMap) {
        String amount = getDao().findTotallAmount(searchMap);
        return amount;
    }

    /**
     * <code>relateOrderAndProduct</code>
     * 产品关联订单
     * @param orderId
     * @param inboundCode
     * @since   2014年10月11日    guokemenng
     */
    public void relateOrderAndProduct(Long orderId, String inboundCode) {
        try {
            BusinessOrderModel order = this.get(orderId);

            String orderCode = order.getOrderCode();
            String storePlace = "";
            String recipientName = "";

            List<BusinessInboundBill> billList = inboundBillService.getBillListByInboundNo(inboundCode);
            List<InboundModel> inboundList = inboundBillService.getInboundListByInboundNo(inboundCode);
            List<StockModel> stockList = inboundBillService.getStockListByOrderCode(orderCode);

            Map<String, Integer> orderMap = new HashMap<String, Integer>();
            Map<String, Integer> inboundMap = new HashMap<String, Integer>();

            Set<BusinessOrderProductModel> productSet = order.getBusinessOrderProductModel();
            Iterator<BusinessOrderProductModel> it = productSet.iterator();
            //订单原有的产品
            while (it.hasNext()) {
                BusinessOrderProductModel p = it.next();
                orderMap.put(p.getProductNo(), p.getQuantity());
            }

            //确认入库的产品
            for (InboundModel inbound : inboundList) {
                storePlace = inbound.getStorePlace();
                recipientName = inbound.getRecipientName();
                inboundMap.put(inbound.getProductCode(), inbound.getProductNum());
            }

            //此订单已入库的产品 和 确认入库的产品 综和
            for (StockModel stock : stockList) {
                String productNo = stock.getProductCode();
                Integer productNum = stock.getStockNum();
                if (inboundMap.get(productNo) == null) {
                    inboundMap.put(productNo, productNum);
                } else {
                    productNum += inboundMap.get(productNo);
                    inboundMap.put(productNo, productNum);
                }
            }
            //订单产品完全入库   标示
            boolean b = true;
            //比较订单产品是否都已经入库
            Iterator<String> iter = orderMap.keySet().iterator();
            while (iter.hasNext()) {
                String key = iter.next();
                Integer value = orderMap.get(key);
                if (inboundMap.get(key) == null) {
                    b = false;
                } else if (value != inboundMap.get(key)) {
                    b = false;
                }
            }
            //更新订单产品入库状态
            if (b) {
                order.setWareHouseStatus("A");
            } else {
                order.setWareHouseStatus("S");
            }
            this.update(order);

            //入库操作
            List<StockModel> stockModelList = new ArrayList<StockModel>();
            for (BusinessInboundBill bill : billList) {
                StockModel stock = new StockModel();
                stock.setOrderCode(orderCode);
                stock.setRecipientName(recipientName);
                stock.setStorePlace(storePlace);
                stock.setInboundCode(inboundCode);
                stock.setProductCode(bill.getProductCode());
                stock.setInboundTime(new Date());
                stock.setStockNum(bill.getProductNum());
                stock.setPono(bill.getPono());
                stock.setProductSn(bill.getProductSn());

                stockModelList.add(stock);
            }
            stocKDao.saveOrUpdateAll(stockModelList);

            //对于入库产品 改变状态
            inboundBillService.updateInboundState(inboundCode);
            //合同状态
            getDao().flush();
            if (b) {
                Set<SalesContractModel> contractModelSet = order.getSalesContractModel();
                Iterator<SalesContractModel> itS = contractModelSet.iterator();
                while (itS.hasNext()) {
                    SalesContractModel c = itS.next();
                    Set<BusinessOrderModel> orderSet = c.getBusinessOrderModel();
                    Iterator<BusinessOrderModel> itO = orderSet.iterator();
                    boolean ob = true;
                    while (itO.hasNext()) {
                        BusinessOrderModel o = itO.next();
                        if (!o.getWareHouseStatus().equals("A")) {
                            ob = false;
                        }
                    }
                    //如果合同所关联的所有订单 产品都到库房 则 合同订单状态改变
                    if (ob) {
                        SalesContractStatusModel status = getDao().salesStart(c.getId());
                        status.setOrderStatus(SalesContractConstant.CONTRACT_ORDER_DEVICEREACH);
                        salesContractStatusService.update(status);
                    }
                }
            }

        } catch (SinoRuntimeException e) {
            e.printStackTrace();
            log.error("操作失败");
            throw new SinoRuntimeException("操作失败");
        }
    }

    /**
     * <code>findOrder</code>
     * 查询订单
     * @param code
     * @return
     * @since   2014年10月27日    wangya
     */
    public BusinessOrderModel findOrder(String code) {
        BusinessOrderModel orderModel = getDao().findOrder(code);
        return orderModel;
    }

    /**
     * <code>updateInterStatus</code>
     * 更新內采状态
     * @param id
     * @return
     * @since   2014年11月7日    wangya
     */
    public void updateInterStatus(Long id) {
        getDao().updateInterOrderStatus(id);
    }

    /**
     * <code>findproductQue</code>
     * 查询合同产品数量
     * @param id
     * @return
     * @since   2014年11月7日    wangya
     */
    public int findproductQue(String id) {
        int num = getDao().findproductQue(Long.parseLong(id));
        return num;
    }

    /**
     * <code>findInterproductQue</code>
     *  查询內采产品数量
     * @param id
     * @return
     * @since   2014年11月7日    wangya
     */
    public int findInterproductQue(String id) {
        int num = getDao().findInterproductQue(Long.parseLong(id));
        return num;
    }

    /**
     * <code>findProdId</code>
     * 是否有流程ID
     * @param proId
     * @return
     * @since   2014年11月7日    wangya
     */
    public BusinessOrderModel findProdId(String proId) {
        BusinessOrderModel order = getDao().findProdId(proId);
        return order;
    }

    /**
     * <code>getOrderById</code>
     * 查询流程名称
     * @param orderId
     * @return
     * @since   2014年11月17日    wangya
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> getOrderById(Long id) {
        String sql = "SELECT c.id,c.OrderName,c.Creator,art.NAME_,art.CREATE_TIME_,art.ASSIGNEE_ FROM business_order c LEFT JOIN bpm_process_inst bpi ON c.ProcessInstanceId=bpi.ID_  LEFT JOIN act_ru_task art ON bpi.HI_PROC_INST_ID= art.proc_inst_id_ WHERE 0=0 AND c.Id = ?";
        List<Map<String, Object>> mapList = this.getDao().createSQLQuery(sql, new Object[] { id }).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        return mapList.get(0);
    }

    /**
     * <code>getSupplierSpot</code>
     *  查询厂商返点数
     * @param supplierId 供应商id
     * @return
     * @since   2014年11月20日    wangya
     */
    public ReturnSpotModel getSupplierSpot(long supplierId) {
        ReturnSpotModel num = getDao().getSupplierSpot(supplierId);
        return num;
    }

    /**
     * <code>getSupplierSpot</code>
     *  查询所有厂商返点数
     * @return
     * @since   2014年11月20日    wangya
     */
    public List<Map<String, Object>> getListSupplierSpot() {
        List<Map<String, Object>> num = getDao().getListSupplierSpot();
        return num;
    }

    /**
     * <code>rejectContract</code>
     *  订单驳回合同
     * @param id 合同id
     * @return
     * @since   2014年11月25日    wangya
     */
    public void rejectContract(long id) {
        getDao().deleteContractInvoice(id);//删除发票计划
        getDao().deleteContractCachet(id);//删除盖章申请
        getDao().updataContractStatus(id);//改合同状态
        getDao().deleteContractOrder(id);//删除合同状态表
    }

    /**
     * <code>findContratStatus</code>
     * 查询合同状态表
     * @param id 合同id
     * @return
     * @since   2014年11月25日    wangya
     */
    public List<SalesContractStatusModel> findContratStatus(String id) {
        List<SalesContractStatusModel> status = getDao().findContratStatus(id);
        return status;
    }

    /**
     * <code>findProductTypeName</code>
     * 查询产品类型
     * @param l
     * @param id 合同id
     * @return
     * @since   2014年11月28日    wangya
     */
    public String findProductTypeName(long id) {
        String TypeName = getDao().findProductTypeName(id);
        return TypeName;
    }

    /**查询订单编号是否存在
     * @param orderCode
     * @return
     */
    public List<Map<String, Object>> findOrderCode(String orderCode) {
        List<Map<String, Object>> check = getDao().findOrderCode(orderCode);
        return check;
    }

    /**保存变更信息
     * @param orderModel
     * @param systemUser
     * @param remark
     * @return
     */
    public String dosaveChange(BusinessOrderModel orderModel, SystemUser systemUser, String remark) {
        BusinessChangeModel change = new BusinessChangeModel();
        long changeId = IdentifierGeneratorImpl.generatorLong();
        change.setId(changeId);
        Date time = new Date();
        change.setCreatTime(time);
        change.setCreator(systemUser.getStaffName());
        change.setStatus(BusinessOrderContant.CHANGE_SP);
        change.setOrderName(orderModel.getOrderName());
        change.setOrderId(orderModel.getId());
        change.setRemark(remark);
        try {
            getDao().updateOrderStatusBG(orderModel.getId());//修改订单变更状态为SP
            businessChangeService.create(change);//保存变更申请
        } catch (Exception e) {
            e.printStackTrace();
            return "false";
        }
        return "true";
    }

    /**变更保存
     * @param orderModel
     * @param systemUser
     * @param contract
     * @param interPurchas
     * @param tableData
     * @param spot
     * @param isAgree
     * @param oldSpotNum
     * @param spotSupplier
     * @param oldspotSupplier
     * @return
     */
    public String doChangeUpdate(BusinessOrderModel orderModel, SystemUser systemUser, String contract, String interPurchas, String tableData, float spot, int isAgree, float oldSpotNum, String oldspotSupplier, String spotSupplier) {
        try {
            Long id = orderModel.getId();
            orderModel.setCreator(systemUser.getStaffName());
            orderModel.setCreatorId(systemUser.getUserName());
            Date d = new Date();
            String[] contractId = contract.split(",");
            String contractIdwhere = "";
            Set<SalesContractModel> saleContractSet = new HashSet<SalesContractModel>();
            for (int i = 0; i < contractId.length; i++) {
                /* String contractName = "";
                 if (!contractName.equals("")) {
                     return "合同：" + contractName + "已变更，无法下单。";
                 }*/
                if (!StringUtil.isEmpty(contractId[i])) {
                    SalesContractModel saleContract = saleContractService.get(Long.parseLong(contractId[i]));
                    saleContractSet.add(saleContract);
                }
                contractIdwhere += "'" + contractId[i] + "',";
            }
            orderModel.setSalesContractModel(saleContractSet);
            String[] interPurchasId = interPurchas.split(",");
            Set<InterPurchasModel> interPurchasSet = new HashSet<InterPurchasModel>();
            for (int i = 0; i < interPurchasId.length; i++) {
                if (!StringUtil.isEmpty(interPurchasId[i])) {
                    InterPurchasModel interPurchasModel = interPurchasService.get(Long.parseLong(interPurchasId[i]));
                    interPurchasSet.add(interPurchasModel);
                }
            }
            orderModel.setInterPurchasModel(interPurchasSet);
            Date time = new Date();
            if (isAgree == 1 && spot > 0) {//是否用返点
                if (oldSpotNum != spot || !oldspotSupplier.equals(spotSupplier)) {//返点是否有变化
                    orderModel.setSpotNum(spot);//存返点数
                    orderModel.setSpotSupplier(spotSupplier);//存返点厂商
                    if (oldspotSupplier.equals(spotSupplier)) {//如果使用的厂商一样
                        ReturnSpotModel spotModel = getSupplierNameSpot(oldspotSupplier);
                        float num = spotModel.getReturnAmount() - spot + oldSpotNum;
                        spotModel.setReturnAmount(num);
                        returnSpotDao.saveOrUpdate(spotModel);
                        float plus = 0;
                        float subtract = 0;
                        if (spot > oldSpotNum) {
                            plus = spot - oldSpotNum;
                        } else {
                            subtract = oldSpotNum - spot;
                        }
                        createrSpotLogs(plus, spotModel, systemUser.staffName, time, orderModel.getId(), subtract);//创建返点日志
                    } else {//如果使用的厂商不一样
                        List<ReturnSpotModel> spots = new ArrayList<ReturnSpotModel>();
                        ReturnSpotModel newSpot = getSupplierNameSpot(spotSupplier);//新厂商减点
                        float newNum = newSpot.getReturnAmount() - spot;
                        newSpot.setReturnAmount(newNum);
                        spots.add(newSpot);
                        createrSpotLogs(spot, newSpot, systemUser.staffName, time, orderModel.getId(), 0);//创建返点日志
                        if (oldSpotNum != 0) {
                            ReturnSpotModel oldSpot = getSupplierNameSpot(oldspotSupplier);//旧厂商加点
                            float oldNum = oldSpot.getReturnAmount() + oldSpotNum;
                            oldSpot.setReturnAmount(oldNum);
                            spots.add(oldSpot);
                            returnSpotDao.saveOrUpdateAll(spots);
                            createrSpotLogs(0, oldSpot, systemUser.staffName, time, orderModel.getId(), oldSpotNum);//创建返点日志
                        }
                    }
                }
            } else {
                orderModel.setSpotNum(0);
                if (oldSpotNum != 0) {
                    ReturnSpotModel oldSpot = getSupplierNameSpot(oldspotSupplier);
                    float oldNum = oldSpot.getReturnAmount() + oldSpotNum;
                    oldSpot.setReturnAmount(oldNum);
                    returnSpotDao.saveOrUpdate(oldSpot);
                    createrSpotLogs(0, oldSpot, systemUser.staffName, time, orderModel.getId(), oldSpotNum);//创建返点日志
                }

            }
            orderModel.setIsChange("0");
            List<Object> gridDataList = getTableGridData(tableData);
            if (gridDataList != null) {
                Map<String, Object> temp = new HashMap<String, Object>();
                for (int i = 0; i < gridDataList.size(); i++) {//加了非空判断
                    Map<String, Object> map = StringToMap(gridDataList.get(i).toString());//格式转换
                    if (map != null) {
                        String saleProId = map.get("column7").toString();
                        String productNo = map.get("column1").toString();
                        int quantity = Integer.parseInt(map.get("column3").toString());
                        if (temp.get(saleProId) != null) {
                            quantity = quantity + Integer.parseInt(temp.get(saleProId).toString());
                            int realQua = businessOrderProductService.findQuantity(Long.parseLong(saleProId));
                            if (quantity > realQua) {
                                return "[" + productNo + "]设备数量填写，大于合同中的数量！";
                            }
                        } else {
                            temp.put(saleProId, quantity);
                        }
                    }
                }
                /* List<Map<String, Object>> rs = businessOrderProductService.getAllProcductsbycontractId(contractIdwhere);
                
                 for (Map<String, Object> map : rs) {
                     if (temp.get(map.get("productNo")) != null) {
                         if (Integer.parseInt(temp.get(map.get("productNo")).toString()) > Integer.parseInt(map.get("proNums").toString())) {
                             //说明页面填写的数量大于合同中的数量
                             return map.get("productNo").toString();
                         }
                     }
                 }*/
                String orderName = orderModel.getOrderName();
                String orderCode = orderModel.getOrderCode();
                if (id != null) {
                    businessOrderProductService.deleteOrderProduct(id);
                }
                getDao().saveOrUpdate(orderModel);
                businessOrderProductService.saveProducts(orderModel, gridDataList);

                if (SendEmailUtils.isSend()) {
                    //变更的合同变更审批通过后给运营中心发邮件
                    processService.sendEmail("系统提醒:订单变更", "订单:" + orderName + "[" + orderCode + "]进行了变更", null, null, "tianj", null, null);
                    processService.sendEmail("系统提醒:订单变更", "订单:" + orderName + "[" + orderCode + "]进行了变更", null, null, "luanpl", null, null);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "false";
        }
        return "true";
    }

    /**查询合同产品列表
     * @param parseLong
     * @return
     */
    public List<Map<String, Object>> findProductUn(long parseLong) {
        List<Map<String, Object>> product = getDao().findProductUn(parseLong);
        return product;
    }

    /**添加订单生效时间
     * @param id
     * @param time
     */
    public void updateBeginTime(Long id, Date time) {
        getDao().updateBeginTime(id, time);

    }

    /**查询厂商
     * @param spotSupplier
     * @return
     */
    public ReturnSpotModel getSupplierNameSpot(String spotSupplier) {
        ReturnSpotModel spot = getDao().getSupplierNameSpot(spotSupplier);
        return spot;
    }

    public List<Map<String, Object>> findSalesByOrderId(Long id) {
        List<SalesContractModel> rs = getDao().findSalesByOrderId(id);
        List<Map<String, Object>> sales = new ArrayList<Map<String, Object>>();
        for (SalesContractModel salesContractModel : rs) {
            List<FundsSalesBusiReimbursement> reimbursements = fundsSalesBusiReimbursementService.getBusiReimbursement(salesContractModel.getId());
            List<Map<String, Object>> total = getDao().getOrderProductTotalAmount(id, salesContractModel.getId());
            Map<String, Object> map = new HashMap<String, Object>();
            BigDecimal totalAmount = new BigDecimal(0.00);
            if (total.size() > 0) {
                totalAmount = new BigDecimal(total.get(0).get("amount").toString());
            }
            BigDecimal tmp = new BigDecimal(0.00);
            for (FundsSalesBusiReimbursement fundsSalesBusiReimbursement : reimbursements) {
                tmp = tmp.add(fundsSalesBusiReimbursement.getContractBusiReimbursement());
            }
            List<FundsSalesBusiReimbursement> planAmount = fundsSalesBusiReimbursementService.getOrderPlanAmout(salesContractModel.getId());
            BigDecimal plantmp = new BigDecimal(0.00);
            for (FundsSalesBusiReimbursement fundsSalesBusiReimbursement : planAmount) {
                plantmp = plantmp.add(fundsSalesBusiReimbursement.getContractBusiReimbursement());
            }
            salesContractModel.setBusiReimbursement(tmp.toString());
            map.put("sales", salesContractModel);
            totalAmount = totalAmount.subtract(tmp);
            totalAmount = totalAmount.subtract(plantmp);
            map.put("amount", totalAmount);
            sales.add(map);
        }
        return sales;
    }

    /**
     * @param id
     * @return
     */
    public String getPayCurrenyType(Long orderId) {
        List<BusinessPayOrderModel> pay = getDao().getPayModel(orderId);
        String curreny = "";
        if (pay.size() > 0) {
            curreny = pay.get(0).getCurrency();
        }
        return curreny;
    }

    /**
     * @param id
     * @return
     */
    public List<Map<String, Object>> findNeicaiByOrderId(Long orderId) {
        List<InterPurchasModel> inter = getDao().getInterPurch(orderId);
        List<Map<String, Object>> sales = new ArrayList<Map<String, Object>>();
        for (InterPurchasModel interPurch : inter) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("neicai", interPurch);
            map.put("amount", "0");
            sales.add(map);
        }
        return sales;
    }

    /**
     * 查询公司信息
     * @return
     */
    public List<Map<String, Object>> findCompanyInfo() {
        return payOrderDao.findCompanyInfoById(1);
    }
}
