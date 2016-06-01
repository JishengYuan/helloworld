/*
 * FileName: SalesExportService.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.sales.contract.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinobridge.base.core.service.DefaultBaseService;
import com.sinobridge.eoss.sales.contract.constant.SalesContractConstant;
import com.sinobridge.eoss.sales.contract.dao.SalesContractDao;
import com.sinobridge.eoss.sales.contract.dto.SalesExpprtDto;
import com.sinobridge.eoss.sales.contract.model.SalesContractModel;

/**
 * <p>
 * Description: 
 * </p>
 *
 * @author tigq
 * @version 1.0

 * <p>
 * History: 
 *
 * Date                     Author         Version     Description
 * ---------------------------------------------------------------------------------
 * 2015年1月15日 下午6:04:47          tigq        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Service
@Transactional
public class SalesExportService extends DefaultBaseService<SalesContractModel, SalesContractDao> {

    /**
     * 导出查询合同数据
     * @return
     */
    public List<SalesExpprtDto> exportContracts(Map<String, Object> searchMap) {
        List<SalesExpprtDto> rs = new ArrayList<SalesExpprtDto>();
        String whereStr = "";
        Object creatorString = searchMap.get(SalesContractModel.CREATOR);
        Object contractName = searchMap.get(SalesContractModel.CONTRACTNAME);
        Object contractShortname = searchMap.get(SalesContractModel.CONTRACTSHORTNAME);
        Object contractState = searchMap.get(SalesContractModel.CONTRACTSTATE);
        Object contractCode = searchMap.get(SalesContractModel.CONTRACTCODE);
        Object contractAmountString = searchMap.get(SalesContractModel.CONTRACTAMOUNT);
        Object customerIndustry = searchMap.get("cusIndustryId");
        Object customerIdtCustomer = searchMap.get("cusCustomerId");
        //        Object contractAmountStringb = searchMap.get("contractAmountb");

        Object contractStateNotEquals = searchMap.get("contractStateNotEquals");
        Object contractType = searchMap.get(SalesContractModel.CONTRACTTYPE);
        Object startTime = searchMap.get(SalesContractModel.CLOSETIME);
        Object endTime = searchMap.get("endTime");
        Object orgId = searchMap.get("orgId");
        Object customerInfo = searchMap.get(SalesContractModel.CUSTOMERID);
        //  String whereStr = "select c.id,c.contractName,c.ContractCode,c.ContractAmount,c.ContractState,cs.OrderStatus,cs.ReciveStatus,cs.CachetStatus,cs.InvoiceStatus,cs.changeStatus,c.isChanged,art.NAME_,art.CREATE_TIME_,art.ASSIGNEE_ ,c.ContractType,c.CreatorName,c.CreateTime,cust.ShortName from Sales_Contract c left join Sales_Contract_Status cs on c.id=cs.SaleContractId left join  sales_contract_product p on c.id = p.SaleContractId LEFT JOIN bpm_process_inst bpi ON c.ProcessInstanceId=bpi.ID_  LEFT JOIN act_ru_task art ON bpi.HI_PROC_INST_ID= art.proc_inst_id_  LEFT JOIN customer_info cust ON c.CustomerId=cust.id where 0=0 and cs.contractSnapShootId IS NULL";
        if (creatorString != null) {
            Long creator = Long.parseLong(creatorString + "");
            whereStr += " and a." + SalesContractModel.CREATOR + "=" + creator;
        }
        if (contractName != null) {
            whereStr += " and a." + SalesContractModel.CONTRACTNAME + " like '" + contractName + "%'";
        }
        if (contractShortname != null) {
            whereStr += " and a." + SalesContractModel.CONTRACTSHORTNAME + " like  '" + contractShortname + "%'";
        }
        if (contractState != null) {
            whereStr += " and a." + SalesContractModel.CONTRACTSTATE + "='" + contractState + "'";
        } else {
            //排除合并的合同
            whereStr += " and a.ContractState != 'HB' ";
        }
        if (contractStateNotEquals != null) {
            whereStr += " and a." + SalesContractModel.CONTRACTSTATE + "!='" + contractStateNotEquals + "'";
        }
        if (contractCode != null) {
            whereStr += " and a." + SalesContractModel.CONTRACTCODE + " like '" + contractCode + "%'";
        }
        if (contractType != null) {
            whereStr += " and a." + SalesContractModel.CONTRACTTYPE + " ='" + contractType + "'";
        }
        if (contractAmountString != null) {
            whereStr += " and a." + SalesContractModel.CONTRACTAMOUNT + " like '" + contractAmountString + "%'";
        }

        //        if (startTime != null) {
        //            whereStr += " and a.createtime >='" + startTime + "'";
        //        }
        //        if (endTime != null) {
        //            whereStr += " and a.createtime <='" + endTime + "'";
        //        }
        if (startTime != null) {
            whereStr += " and a.salesStartDate >='" + startTime + "'";
        }
        if (endTime != null) {
            whereStr += " and a.salesStartDate <='" + endTime + "'";
        }
        if (customerInfo != null) {
            whereStr += " and a." + SalesContractModel.CUSTOMERID + " ='" + customerInfo + "'";
        }
        if (customerIndustry != null) {
            whereStr += " and a.cusIndustryId = '" + customerIndustry + "'";
        }
        if (customerIdtCustomer != null) {
            whereStr += " and a.cusCustomerId = '" + customerIdtCustomer + "'";
        }
        if (orgId != null) {
            String sql = "SELECT orgcode FROM sys_orgnization b WHERE b.OrgId='" + orgId + "'";
            Query orgquery = getDao().createSQLQuery(sql);
            String orgcode = (String) orgquery.uniqueResult();
            whereStr += " AND a.Creator IN(SELECT s.staffid FROM sys_staff s  LEFT JOIN sys_stafforg o ON o.staffId = s.staffId LEFT JOIN sys_orgnization org ON o.orgId = org.orgId WHERE s.State!='0' AND org.orgCode  LIKE '" + orgcode + "%' )";
        }

        StringBuffer buffer = new StringBuffer();
        buffer.append("SELECT t1.*,t2.payAmount,t3.receiveAmount,t4.ordercost,t5.invoiceAmount FROM");
        buffer.append(" (SELECT a.id,a.`CreatorName`,c.name customerName,c.provinces,a.`ContractCode`,a.`ContractName`,a.`ContractType`,a.`ContractState`,a.`SalesStartDate`,cs.OrderStatus,b.cachetdate,a.`ContractAmount`,a.EstimateProfit,a.`InvoiceType` FROM sales_contract a left join Sales_Contract_Status cs on a.id=cs.SaleContractId LEFT JOIN sales_cachet b ON a.`id`=b.`SalesContractId`");
        buffer.append(" LEFT JOIN customer_info c ON a.`CustomerId`=c.`id` WHERE 1=1 " + whereStr + " order by a.salesStartDate desc) t1 LEFT JOIN");
        buffer.append(" (SELECT t.SaleContractId,SUM(t.saleamount) payAmount FROM (");
        buffer.append(" SELECT a.SaleContractId,a.orderid,b.amount*a.amountpercent saleamount FROM (");
        buffer.append(" SELECT t.SaleContractId,t.`OrderId`,SUM(t.`SubTotal`)/m.`OrderAmount` amountpercent FROM business_order_product t,business_order m");
        buffer.append(" WHERE t.`OrderId`=m.`id` GROUP BY t.`SaleContractId`,t.`OrderId`) a ,");
        buffer.append(" (SELECT b.`OrderId`,SUM(b.`Amount`) amount FROM business_pay_apply a,business_payment_plan b");
        buffer.append(" WHERE a.`id`=b.`PayOrderId` AND a.`CloseTime` IS NOT NULL GROUP BY b.`OrderId`) b WHERE a.OrderId=b.OrderId");
        buffer.append(" ) t GROUP BY t.SaleContractId) t2 ON t1.id=t2.SaleContractId LEFT JOIN");
        buffer.append(" (SELECT SalesId,SUM(amount) receiveAmount FROM business_sales_feeincome GROUP BY SalesId) t3 ON t1.id=t3.salesid LEFT JOIN");
        buffer.append(" (SELECT t.SaleContractId,SUM(t.`SubTotal`) ordercost FROM business_order_product t GROUP BY t.SaleContractId) t4 ON t1.id=t4.SaleContractId");
        buffer.append(" LEFT JOIN");
        buffer.append(" (SELECT t.`SalesContractId`,SUM(t.`InvoiceAmount`) invoiceAmount FROM sales_invoice_plan t WHERE t.`InvoiceStatus`='TGSH' GROUP BY t.SalesContractId) t5 ON t1.id=t5.SalesContractId GROUP BY t1.id ORDER BY t1.SalesStartDate DESC, t1.id DESC");
        String sql = buffer.toString();

        Query query = getDao().createSQLQuery(sql);
        List<Map<String, Object>> rst = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        rs = buildExcelList(rst);
        return rs;
    }

    /**
     * 构造导出EXCEL所需要的数据
     * @param datas
     * @return
     */
    public List<SalesExpprtDto> buildExcelList(List<Map<String, Object>> datas) {
        List<SalesExpprtDto> rs = new ArrayList<SalesExpprtDto>();
        for (Map<String, Object> map : datas) {
            SalesExpprtDto rsmap = new SalesExpprtDto();
            BigDecimal contractAmount = new BigDecimal(0.00);
            contractAmount = new BigDecimal(map.get("ContractAmount") == null ? "0.00" : map.get("ContractAmount").toString());
            rsmap.setCreatorName(map.get("CreatorName") == null ? "" : map.get("CreatorName").toString());//客户经理
            rsmap.setCustomerName(map.get("customerName") == null ? "" : map.get("customerName").toString());//客户名称
            rsmap.setProvinces(map.get("provinces") == null ? "" : map.get("provinces").toString());//省份
            rsmap.setContractCode(map.get("ContractCode") == null ? "" : map.get("ContractCode").toString());//合同编号
            rsmap.setContractName(map.get("ContractName") == null ? "" : map.get("ContractName").toString());//合同名称
            String contractType = map.get("ContractType").toString();

            if (contractType.equals("1000")) {
                contractType = "产品合同";
            } else if (contractType.equals("2000")) {
                contractType = "临时采购";
            } else if (contractType.equals("3000")) {
                contractType = "MA续保合同";
            } else if (contractType.equals("4000")) {
                contractType = "技术服务合同";
            } else if (contractType.equals("5000")) {
                contractType = "采购确认函";
            } else if (contractType.equals("6000")) {
                contractType = "软件开发合同";
            } else if (contractType.equals("7000")) {
                contractType = "公司备件";
            } else if (contractType.equals("8000")) {
                contractType = "客户配件";
            } else if (contractType.equals("9000")) {
                contractType = "备货合同";
            } else if (contractType.equals("0")) {
                contractType = "其他合同类型";
            }
            rsmap.setContractType(contractType);//合同类型
            String contractStatus = map.get("ContractState") == null ? "" : map.get("ContractState").toString();
            if (contractStatus.equals(SalesContractConstant.CONTRACT_STATE_CG)) {
                contractStatus = "草稿";
            } else if (contractStatus.equals(SalesContractConstant.CONTRACT_STATE_SH)) {
                contractStatus = "审核中";
            } else if (contractStatus.equals(SalesContractConstant.CONTRACT_STATE_TGSH)) {
                contractStatus = "审核通过";
            } else if (contractStatus.equals(SalesContractConstant.CONTRACT_STATE_FQ)) {
                contractStatus = "废弃";
            } else if (contractStatus.equals(SalesContractConstant.CONTRACT_STATE_CXTJ)) {
                contractStatus = "重新提交";
            } else if (contractStatus.equals(SalesContractConstant.CONTRACT_STATE_DGB)) {
                contractStatus = "待关闭";
            } else if (contractStatus.equals(SalesContractConstant.CONTRACT_STATE_CBYG)) {
                contractStatus = "成本预估";
            }
            rsmap.setContractState(contractStatus);//合同状态
            //合同订单状态
            rsmap.setContractOrderState(map.get("OrderStatus") == null ? "" : map.get("OrderStatus").toString());
            rsmap.setSalesStartDate(map.get("SalesStartDate") == null ? "" : map.get("SalesStartDate").toString());//合同生效日期
            rsmap.setCachetdate(map.get("cachetdate") == null ? "" : map.get("cachetdate").toString());//合同文本盖章日期

            rsmap.setContractAmount(contractAmount);//合同金额

            String invoiceType = map.get("InvoiceType") == null ? "" : map.get("InvoiceType").toString();
            if (invoiceType.equals("1")) {
                invoiceType = "无发票";
            } else if (invoiceType.equals("2")) {
                invoiceType = "增值税普通发票-设备（17%）";
            } else if (invoiceType.equals("3")) {
                invoiceType = "增值税专用发票-设备（17%）";
            } else if (invoiceType.equals("4")) {
                invoiceType = "增值税普通发票-服务（6%）";
            } else if (invoiceType.equals("5")) {
                invoiceType = "增值税专用发票-服务（6%）";
            } else if (invoiceType.equals("6")) {
                invoiceType = "建筑安装地税";
            }
            rsmap.setInvoiceType(invoiceType);//发票类型
            BigDecimal receiveAmount = new BigDecimal(0.00);
            if (map.get("receiveAmount") != null) {
                receiveAmount = new BigDecimal(map.get("receiveAmount").toString());
                rsmap.setReceiveAmount(receiveAmount);//已收款
                rsmap.setUnreceiveAmount(contractAmount.subtract(receiveAmount));//未收款
            } else {
                rsmap.setReceiveAmount(new BigDecimal(0.00));//已收款
                rsmap.setUnreceiveAmount(contractAmount);//未收款
            }
            BigDecimal invoiceAmount = new BigDecimal(0.00);
            if (map.get("invoiceAmount") != null) {
                invoiceAmount = new BigDecimal(map.get("invoiceAmount").toString());
                rsmap.setInvoiceAmount(invoiceAmount);//已开发票金额
                rsmap.setUninvoiceAmount(contractAmount.subtract(invoiceAmount));//未开发票金额
            } else {
                rsmap.setUninvoiceAmount(contractAmount);//未开发票金额
                rsmap.setInvoiceAmount(new BigDecimal(0.00));//已开发票金额
            }

            //TODO
            if (contractStatus.equals(SalesContractConstant.CONTRACT_STATE_DGB)) {
                rsmap.setCostAmount(contractAmount);//已结算合同额
            } else {
                rsmap.setCostAmount(new BigDecimal(0.00));//已结算合同额
            }
            BigDecimal ordercost = new BigDecimal(0.00);
            if (map.get("ordercost") != null) {
                ordercost = new BigDecimal(map.get("ordercost").toString());
                rsmap.setIsCost("是");//是否已结算
                rsmap.setProcurementCosts(ordercost);//合同下采购成本
            } else {
                rsmap.setIsCost("否");//是否已结算
                String estimateProfit =map.get("EstimateProfit")!=null? map.get("EstimateProfit").toString():"0";
                estimateProfit = estimateProfit.replaceAll("%", "");
                Double est = Double.valueOf(estimateProfit);
                est = est * 0.01 * contractAmount.doubleValue();
                BigDecimal estimaate = new BigDecimal(est).setScale(2, BigDecimal.ROUND_HALF_UP);
                rsmap.setProcurementCosts(estimaate);//合同下采购成本
            }
            BigDecimal payment = new BigDecimal(0.00);
            if (map.get("payAmount") != null) {
                payment = new BigDecimal(map.get("payAmount").toString());
                rsmap.setPayment(payment);//已付款
            } else {
                rsmap.setPayment(new BigDecimal(0.00));//已付款
            }
            rsmap.setUnpayment(ordercost.subtract(payment));//未付款
            BigDecimal capital = payment.subtract(receiveAmount);
            if (capital.compareTo(BigDecimal.ZERO) == -1) {
                rsmap.setCapital(BigDecimal.ZERO);//资金占用数
            } else {
                rsmap.setCapital(capital);//资金占用数
            }

            BigDecimal capitalCost = new BigDecimal(0.00);
            BigDecimal projectCost = new BigDecimal(0.00);
            rsmap.setCapitalCost(capitalCost);//占用资金成本
            rsmap.setProjectCost(projectCost);//项目实施成本
            BigDecimal capitalProjectOrder = capitalCost.add(projectCost);
            capitalProjectOrder = capitalProjectOrder.add(ordercost);
            BigDecimal contractMargin = contractAmount.subtract(capitalProjectOrder);
            rsmap.setContractMargin(contractMargin);//合同毛利
            BigDecimal afterTaxMargin = new BigDecimal(0.00);
            if (contractType.equals("1000")) {
                Double mar = contractMargin.doubleValue() / 1.17;
                afterTaxMargin = BigDecimal.valueOf(mar).setScale(2, BigDecimal.ROUND_HALF_UP);
            } else {
                Double mar = contractMargin.doubleValue() * 0.945;
                afterTaxMargin = BigDecimal.valueOf(mar).setScale(2, BigDecimal.ROUND_HALF_UP);
            }
            rsmap.setAfterTaxMargin(afterTaxMargin);//税后毛利
            BigDecimal profitRate = new BigDecimal(0.00);
            if (contractAmount.compareTo(BigDecimal.ZERO) == 1 && contractMargin.compareTo(BigDecimal.ZERO) == 1) {
                profitRate = contractMargin.divide(contractAmount, 2, BigDecimal.ROUND_HALF_DOWN);
            }
            Double pro = profitRate.doubleValue() * 100.00;
            profitRate = BigDecimal.valueOf(pro);
            rsmap.setProfitRate(profitRate);//毛利率
            rs.add(rsmap);
        }

        return rs;
    }
}
