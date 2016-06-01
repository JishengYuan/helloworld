<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/global.jsp"%>
<%@ page import="com.sinobridge.systemmanage.util.Global"%> 
<%@ page import="com.sinobridge.systemmanage.vo.SystemUser"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
	<title>${model.contractName}</title>
	<%@ include file="/common/include-base-boostrap-styles.jsp" %>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formSelect.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formstyle.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/dialog.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/tip.css" type="text/css" ></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/user-selection.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formSearchTip.css" type="text/css" ></link>
	<link rel="stylesheet" href="${ctx}/skin/default/editgrid/editgrid.css" type="text/css" />
	<%
  	SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
   String sessionid =  request.getSession().getId();
   String username = systemUser.getUserName();
   String staffname = systemUser.getStaffName();
  %>
    <script >
      var sessionid='<%=sessionid%>';
      var username = '<%=username%>';
      var staffname = '<%=staffname%>';
  </script>
	<script src="${ctx}/js/plugins/seajs/sea.js" id="seajsnode" type="text/javascript"></script>
	<script src="${ctx}/js/plugins/seajs/config.js" type="text/javascript"></script>
	<link href="${ctx}/skin/default/sales/sale.css" rel="stylesheet"/>
	<style type="text/css">
	 .tright a{
	 color:#fff;
	 }
	 .tright a:hover,.tright a:focus{
	 color:#fff;
	 }
	 .row-label{
	 color:#888888;
	 }
	 .form_table table td {
	 color:#111111;
	 }
	 ._spot_div ul {
	margin-left:1px;
	 }
	 ._spot_div ul li{
	 	line-height:30px;
	 	border-bottom: 1px solid #cccccc;
	 	width:80px;
	 }

	 ._spot_div ul a:link{
	 	color:blue;
	 }
	 ._spot_div ul a:visited{
	 	color:blue;
	 }
	 ._spot_div ul a:hover{
	 	color:red;
	 }
	 .stroke {
 color: #c00;
 -webkit-text-stroke: 1px #000;
}
	.popover {
  		max-width: 730px;
  		height:50%;
	}
	</style>
</head>
<body>
<!-- 遮盖层div -->
	<div id="_progress1" style="display: none; top: 0px; left: 0px; width: 100%; margin-left: 0px; height: 100%; margin-top: 0px; z-index: 1000; opacity: 0.7; text-align: center; background-color: rgb(204, 204, 204); position: fixed;">
	</div>
	<div id="_progress2" style="display: none; width: 233px; height: 89px; top: 45%; left: 45%; position: absolute; z-index: 1001; line-height: 23px; text-align: center;">
		<div style="top: 50%;position:fixed;">
			<div style="display: block;">数据处理中，请稍等...</div>
		</div>
	</div>
	

	
	<div class="salecontent">
		<div class="top">
			<div class="caption">
				<i class="icon-reorder"></i>&nbsp;&nbsp;&nbsp;合同编号：${model.contractCode}
					<c:if test="${!empty  merge}">
					<span style="font-weight:bold;color:orange;">[合并合同]</span>
					</c:if>
				<%-- <c:if test="${empty  model.isOld}"> --%>
				<c:if test="${model.contractState != 'HB' }">
				<span class="tright" >
					<c:if test="${draft=='1'}">
					<a name="hrefReload" href="#" hrefA="${ctx }/sales/contract/toUpdate?id=${model.id}" ><i class="icon-credit-card"></i>修改</a>&nbsp;&nbsp;
					</c:if>
					<c:if test="${InvoiceStatus=='1'}">
						<c:if test="${model.contractType != '2000'&&model.contractType != '7000'&&model.contractType != '8000'&&model.contractType != '9000'}">
							<a name="hrefReload" href="#" hrefA="${ctx }/sales/invoice/apply?colseType=addNew&id=${model.id}"><i class="icon-credit-card"></i>发票申请</a>&nbsp;&nbsp;
						</c:if>
					</c:if>
				<c:if test="${Cachet=='1'}">
				<c:if test="${model.contractType == '1000'||model.contractType == '3000'||model.contractType == '4000'||model.contractType == '5000'||model.contractType == '6000'}">
			      <a name="hrefReload" href="#" hrefA="${ctx }/sales/cachet/apply?id=${model.id}"><i class="icon-plus-sign"></i>盖章申请</a>&nbsp;&nbsp;
				  </c:if>
				  </c:if>        
				  <c:if test="${ChangeApply=='1'&&model.contractType != '9000'||Cachet=='1'}">                       
				       <a name="hrefReload" href="#" hrefA="${ctx }/sales/contractchange/apply?id=${model.id}"><i class="icon-edit"></i>变更申请</a>
			      </c:if>        
				  <c:if test="${Change=='1'}">  
				   <a name="hrefReload" href="#" hrefA="${ctx }/sales/contract/toContractChange?id=${model.id}" ><i class="icon-edit"></i>变更</a>
			      </c:if>
			       <c:if test="${ChangeDetail=='1'}">  
				   <a name="hrefReload" href="#" hrefA="${ctx }/sales/contractchange/applyDetail?id=${model.id}&flowStep=SHOW"><i class="icon-edit"></i>变更申请详情</a>
			      </c:if>
			      <input type="hidden" value="${Change}" id="_Change_input">
			      <a name="hrefReload" id="_invoice_a" href="#" hrefA="${ctx }/sales/invoice/applyDetail?id=${model.id}" style="display:none;"><i class="icon-edit"></i>发票申请</a>
			      </span>
			      </c:if>
			      <%-- 
			      </c:if>
			      <c:if test="${!empty  model.isOld}">
			      <span class="tright" style="color:yellow"><i class="icon-bell" style="font-size:20px;color:yellow"></i>该合同为迁移的：${model.isOld}</span>
			      </c:if> 
			      --%>   
			</div>
			<div class="tools">
				<!--a href="javascript:;" class="reload"><i class="icon-repeat"></i>返回</a-->
			</div>
		</div>
	</div>
		<div class="portlet-body form" id="top" >
		
		      <%-- <c:if test="${!empty  model.isOld}">
			    		<div style="position: fixed; margin-top: 50px; right: 5%;width:115px;border: 1px solid #eee; -moz-border-radius: 6px;-webkit-border-radius: 6px;border-radius:6px;">
			    		   <h4 style="color:red;text-align: center;">操作提示</h4>
			    		   <hr style="margin:0;">
			          <span class="stroke"> &nbsp;&nbsp;原ERP合同，相关的订单、发票、盖章、收款，都需要在原ERP系统中进行！</span>
				</div>
			  </c:if> --%>
	
			
	   <div id="back-to-top" style="display: block;">
			<a href="#salesBill"><div style="background-color:#aaaaaa;color:#fff;margin-bottom:3px;">合同<br/>清单</div></a>
			<a href="#colltionPlan"><div style="background-color:#aaaaaa;color:#fff;margin-bottom:3px;">收款<br/>计划</div></a>
			<a href="#salesFile"><div style="background-color:#aaaaaa;color:#fff;margin-bottom:3px;">合同<br/>附件</div></a>
			<a href="#invoicePlan"><div style="background-color:#aaaaaa;color:#fff;margin-bottom:3px;">发票<br/>计划</div></a>
			<a href="#auditLog"><div style="background-color:#aaaaaa;color:#fff;margin-bottom:3px;">审核<br/>日志</div></a>
			<a href="#top"><span></span></a>
		</div>

			
			<!-- BEGIN FORM-->
			<form id='_sino_eoss_sales_contract_approveForm' name='_sino_eoss_sales_contract_approveForm' class='form-horizontal' >
				<!--隐藏字段start -->
				<!--  隐藏的合同ID-->
				<input type='hidden' name='id' id='id'  value="${model.id}"/>
	
				<input type='hidden' name='contractType' id='_eoss_sales_contractTypeId'  value="${model.contractType}"/>
				
				<!--  隐藏的合同编码-->
				<input type='hidden' id='contractCode' name='contractCode'  value="${model.contractCode}" />
				<!--  隐藏的tableGrid-->
				<input type='hidden' name='tableData' id='tableData' />
				<!--  隐藏的合同类型ID-->
				<input type="hidden" name="contractTypeId" id="contractTypeId" value="${model.contractType}">
				<!--隐藏的结算币种，用于回显-->
				<input type='hidden' name='accountCurrency' id='_eoss_sales_accountCurrencyId'  value="${model.accountCurrency}"/>
				<!--隐藏的发票类型，用于回显-->
				<input type='hidden' name='invoiceType' id='_eoss_sales_invoiceTypeId'  value="${model.invoiceType}"/>
				<!--隐藏的收款方式，用于回显-->
				<input type='hidden' name='receiveWay' id='_eoss_sales_receiveWayId'  value="${model.receiveWay}"/>
				<!--隐藏的客户ID，用于回显-->
				<input type='hidden' name='customerId' id='_eoss_sales_customerId'  value="${model.customerId}"/>
				<!--  隐藏的合同带订单处理人ID-->
				<input type="hidden" name="orderProcessor" id="orderProcessorId">
				<!--  隐藏的流程中所需要的任务ID-->
				<input type="hidden" id="taskId" name="taskId" value="${taskId}" >
				<!--  隐藏的流程中所需要的工单ID-->
				<input type="hidden" id="procInstId" name="procInstId" value="${procInstId}" >
				
				<input type='hidden' name='customerContact' id='customerContact' value="${model.customerContact }" />
				<input type='hidden' name='contractAmount' value="${model.contractAmount }" />
				<!--隐藏字段end -->
			<!-- 小标题start -->
			<div class="row-inline-first" >
				<h5 class="form-section-title"><a name="salesObj">合同基本信息</a></h5>
			</div>
			<!-- 小标题end -->
			<!-- 1行start -->
			<div class=" row-inline" >
				<div class=''>
					<label class='row-label' for='contractName' >合同名称：</label>
					<div class='row-span-oneColumn'>
						<span>${model.contractName}</span>
					</div>
				</div>
			</div>
			<!-- 1行end -->
			<!-- 2行start -->
			<!-- 
			<div class=" row-inline" >
				<div class=''>
					<label class='row-label' for='contractName' >合同简称：</label>
					<div class='row-span'>
						<span>${model.contractShortName}</span>
					</div>
				</div>
			</div>
			 -->
			<div class=" row-inline" >
				<div class=''>
					<label class='row-label' for='contractName' >合同类型：</label>
					<div class='row-span'>
						<span id="contractType"></span>
					</div>
				</div>
			</div>
			<div class=" row-inline" >
				<div class=''>
					<label class='row-label' for='contractName' >合同金额：</label>
					<div class='row-span'>
						<span id="contractAmount">${model.contractAmount}</span>
					</div>
				</div>
			</div>
			<!-- 2行end -->
			<!-- 3行start -->
			<div class=" row-inline" >
				<div class=''>
					<label class='row-label' for='accountCurrency' >结算币种：</label>
					<div class='row-span'>
						<span id="accountCurrency"></span>
					</div>
				</div>
			</div>
			<div class=" row-inline" >
				<div class=''>
					<label class='row-label' for='invoiceType' >发票类型：</label>
					<div class='row-span'>
						<span id="invoiceType"></span>
					</div>
				</div>
			</div>
			<div class=" row-inline" >
				<div class=''>
					<label class='row-label' for='receiveWay' >收款方式：</label>
					<div class='row-span'>
						<span id="receiveWay"></span>
					</div>
				</div>
			</div>
			<!-- 3行end -->
			<!-- 分割行start -->
			<div class=" row-inline" >
				<hr size=1 class="dashed-inline">
			</div>
			<!-- 分割行end -->
			<!-- 小标题start -->
			<div class=" row-inline" >
				<h5 class="form-section-title">客户信息（甲方）</h5>
			</div>
			<!-- 小标题end -->
			<!-- 5行start -->
			<!-- <div class=" row-inline" >
				<div class=''>
					<label class='row-label' for='contractName' >行业选择：</label>
					<div class='row-span'>
						<span id="_industryName"></span>
					</div>
				</div>
			</div>
			<div class=" row-inline" >
				<div class=''>
					<label class='row-label' for='contractName' >行业客户：</label>
					<div class='row-span-twoColumn'>
						<span id="_customerIdtCustomerName"></span>
					</div>
				</div>
			</div> -->
			<!-- 5行end -->
			<!-- 6行start -->
			<div class=" row-inline" >
				<div class=''>
					<label class='row-label' for='contractName' >客户名称：</label>
					<div class='row-span-oneHalfColumn'>
						<span id="_customerInfoName"></span>
					</div>
				</div>
			</div>
			<div class=" row-inline" >
				<div class=''>
					<label class='row-label' for='contractName' >联&nbsp;&nbsp;系&nbsp;&nbsp;人：</label>
					<div class='row-span-halfColumn'>
						<span id="_customerContacts"></span>
					</div>
				</div>
			</div>
			<div class=" row-inline" >
				<div class=''>
					<label class='row-label' for='contractName' >手机号码：</label>
					<div class='row-span-halfColumn'>
						<span id="_customerContactsPhone"></span>
					</div>
				</div>
			</div>
			<!-- 6行end -->
			<!-- 分割行start -->
			<div class=" row-inline" >
				<hr size=1 class="dashed-inline">
			</div>
			<!-- 分割行end -->
			<!-- 小标题start -->
			<div class=" row-inline" >
				<h5 class="form-section-title">公司信息(乙方)</h5>
			</div>
			<!-- 小标题end -->
			<!-- 7行start -->
			<div class=" row-inline" >
				<div class=''>
					<label class='row-label' for='contractName' >公司名称：<span >北京神州新桥科技有限公司</span></label>
					<!-- <div class='row-span-twoColumn'>
					</div> -->
				</div>
			</div>
		
			<!-- 7行end -->	
			<!-- 8行start -->
			<div class=" row-inline" >
				<div class=''>
					<label class='row-label' for='contractName' >开户银行：<span >中国工商银行紫竹院支行</span></label>
					<!-- <div class='row-span-twoColumn'>
					</div> -->
				</div>
			</div>
			<div class=" row-inline" >
				<div class=''>
					<label class='row-label' for='contractName' >银行账户：<span>0200007609004774060</span></label>
					<!-- <div class='row-span'>
					</div> -->
				</div>
			</div>
			<div class=" row-inline" >
				<div class=''>
					<label class='row-label' for='contractName' >客户经理：<span style="color:#111111;">${model.creatorName}</span></label>
					<!-- <div class='row-span'>
					</div> -->
				</div>
			</div>
			<!-- 8行end -->
			
			<div class=" row-inline" >
				<h5 class="form-section-title" id="service_h5">合同执行信息</h5>
			</div>
			<div class=" row-inline">
				<div class='row-column' id="serviceStartDate_div">
					<label class='row-label' for='accountCurrency' >服务开始时间：</label><div class='row-input' style="margin-top:5px;">${model.serviceStartDate }</div>
				</div>
			</div>
			<div class=" row-inline">
				<div class='row-column' id="serviceEndDate_div">
					<label class='row-label' for='invoiceType'>服务结束时间：</label><div class='row-input' style="margin-top:5px;">${model.serviceEndDate }</div>
				</div>
			</div>
			<div class=" row-inline">
				<div class='row-column' id="">
					<label class='row-label' for='accountCurrency'>合同生效时间：</label>
					<div class='row-input' style="margin-top:5px;">
						${model.salesStartDate }
					</div>
				</div>
			</div>
			<div class=" row-inline">
				<div class='row-column' id="">
					<label class='row-label' for='invoiceType'>合同终止时间：</label>
					<div class='row-input' style="margin-top:5px;">
						${model.salesEndDate }
					</div>
				</div>
			</div>
			<div class=" row-inline" id="estimateProfit_div">
				<div class='row-column'>
					<label class='row-label' for='receiveWay'>预估毛利率：</label>
					<div class='row-input' style="margin-top:5px;">
						${model.estimateProfit }
					</div>
				</div>
			</div>
			<div class=" row-inline"  style="margin-top:10px;">
			<div style="width:800px;">
					<label class='row-label' for='receiveWay' >预估毛利说明：</label>
					<div class='row-input' style="margin-top:5px;">
						${model.estimateProfitDesc }
					</div>
				</div>
			</div>
			<div class=" row-inline" >
				<h5 class="form-section-title" id="service_h5">合同清单采购员</h5>
			</div>
			<div class=" row-inline">
				<div class=''>
					<label class='row-label'>采购员名称：</label><div style="margin-top:5px;float:left;" id="_sales_contracts_buyer">${model.orderProcessor }</div>
				</div>
			</div>
			<%-- <div class=" row-inline">
				<div class=''>
					<label class='row-label'>预估毛利：</label><div style="margin-top:5px;float:left;">${model.busiEstimateProfit }</div>
				</div>
			</div>
			<div class=" row-inline">
				<div class=''>
					<label class='row-label' for=''>预估毛利描述：</label><div style="margin-top:5px;float:left;">${model.busiEstimateProfitDesc }</div>
				</div>
			</div> --%>
			<!-- 分割行start -->
			<div class=" row-inline" >
				<hr size=1 class="dashed-inline">
			</div>
			<!-- 分割行end -->
			<!-- 小标题start -->
			<div class="row-inline product_handler">
				<h5 class="form-section-title"><a name="salesBill">合同清单</a></h5>
				<div class="row-inline">
					<label class='row-label' for='receiveWay' >期望到货日期：</label>
					<div class="row-input" style="margin-top:5px;">${model.hopeArriveTime }</div>
					<label class='row-label' for='receiveWay' style="margin-left:10px;" >期望到货地点：</label>
					<div class="row-input" style="margin-top:5px;">${model.hopeArrivePlace }</div>
				</div>
			</div>
			<!-- 小标题end -->
			<!-- 9行start -->	
			<div class="row-inline product_handler">
				<div class='table-tile-width'>
				<c:if test="${model.contractType != '9000'&&model.contractType != '3000'}">
					<table id="_sino_eoss_sales_contract_products_table"class="table  table-bordered">
					  <thead>
					    <tr id="_contract_show" style="background-color: #f3f3f3;color: #888888;">
					      <th style="width:6%;">序号</th>
					      <th style="width:15%;" id="type_th">产品类型</th>
					      <th style="width:10%;" id="partner_th">产品厂商</th>
					      <th style="width:15%;" id="product_th">产品型号</th>
					       <th style="width:12%;">服务开始时间</th>
					      <th style="width:12%;">服务结束时间</th>
					      <th style="width:6%;">数量</th>
					      <th style="width:12%;">单价</th>
					      <th style="width:18%;">合计</th>
					    </tr>
					    <tr id="_contract_hidden" style="background-color: #f3f3f3;color: #888888;display:none;">
					      <th style="width:6%;">序号</th>
					      <th style="width:16%;">关联备货合同名称</th>
					      <th style="width:8%;" id="type_th">产品类型</th>
					      <th style="width:8%;" id="partner_th">产品厂商</th>
					      <th style="width:8%;" id="product_th">产品型号</th>
					      <th style="width:13%;">服务开始时间</th>
					      <th style="width:13%;">服务结束时间</th>
					      <th style="width:6%;">数量</th>
					      <th style="width:8%;">单价</th>
					      <th style="width:10%;">合计</th>
					    </tr>
					  </thead>
								  <fmt:setLocale value="zh_cn"/>
					  <tbody>
						<c:forEach var="salesContractProductModel" items="${model.salesContractProductModel}" varStatus="status">
							<c:if test="${salesContractProductModel.relateDeliveryProductId != null&&salesContractProductModel.relateDeliveryProductId != '' }">
								<tr>
									<td >${ status.index + 1}</td>
									<td name="relateDeliveryProduct_contract" tid="${salesContractProductModel.relateDeliveryProductId }"></td>
									<td >${salesContractProductModel.productTypeName }</td>
									<td >${salesContractProductModel.productPartnerName }</td>
									<td title="${salesContractProductModel.remark}">${salesContractProductModel.productName }</td>
									<td >${salesContractProductModel.serviceStartDate }</td>
									<td >${salesContractProductModel.serviceEndDate }</td>
									<td >${salesContractProductModel.quantity }</td>
									<td > <fmt:formatNumber value="${salesContractProductModel.unitPrice }" type="currency" currencySymbol="￥"/> </td>
									<td tdPrice="${salesContractProductModel.totalPrice }"> <fmt:formatNumber value="${salesContractProductModel.totalPrice }" type="currency" currencySymbol="￥"/> </td>
								</tr>
							</c:if>
							<c:if test="${salesContractProductModel.relateDeliveryProductId == null||salesContractProductModel.relateDeliveryProductId == '' }">
								<tr>
									<td name="relateDeliveryProduct_contract_">${ status.index + 1}</td>
									<td >${salesContractProductModel.productTypeName }</td>
									<td >${salesContractProductModel.productPartnerName }</td>
									<td title="${salesContractProductModel.remark}">${salesContractProductModel.productName }</td>
									<td >${salesContractProductModel.serviceStartDate }</td>
									<td >${salesContractProductModel.serviceEndDate }</td>
									<td >${salesContractProductModel.quantity }</td>
									<td ><fmt:formatNumber value="${salesContractProductModel.unitPrice }" type="currency" currencySymbol="￥"/> </td></td>
									<td tdPrice="${salesContractProductModel.totalPrice }"> <fmt:formatNumber value="${salesContractProductModel.totalPrice }" type="currency" currencySymbol="￥"/> </td>
								</tr>
							</c:if>
						</c:forEach>
					  </tbody>
					</table>
					</c:if>
					<c:if test="${model.contractType == '9000'}">
					<table id="_sino_eoss_sales_contract_readyproducts_table" style="" class="table  table-bordered table-striped">
					  <thead>
					    <tr>
					      <th style="width:10%;">序号</th>
					      <th style="width:15%;">产品类型</th>
					      <th style="width:10%;">产品厂商</th>
					      <th style="width:15%;">产品型号</th>
					      <th style="width:6%;">数量</th>
					      <th style="width:12%;">关联数量</th>
					      <th style="width:18%;">剩余数量</th>
					      <th style="width:12%;">单价</th>
					      <th style="width:18%;">合计</th>
					    </tr>
					  </thead>
					  <tbody>
					  <c:forEach var="salesContractProductModel" items="${model.salesContractProductModel}" varStatus="status">
							<tr>
								<td name="relateDeliveryProduct_contract_">${ status.index + 1}</td>
								<td >${salesContractProductModel.productTypeName }</td>
								<td >${salesContractProductModel.productPartnerName }</td>
								<td title="${salesContractProductModel.remark}">${salesContractProductModel.productName }</td>
								<td >${salesContractProductModel.quantity }</td>
								<td >${salesContractProductModel.surplusNum }</td>
								<td style="font-weight:bold;">${salesContractProductModel.quantity - salesContractProductModel.surplusNum}</td>
								<td ><fmt:formatNumber value="${salesContractProductModel.unitPrice }" type="currency" currencySymbol="￥"/> </td></td>
								<td tdPrice="${salesContractProductModel.totalPrice }"> <fmt:formatNumber value="${salesContractProductModel.totalPrice }" type="currency" currencySymbol="￥"/> </td>
							</tr>
						</c:forEach>
					  </tbody>
					</table>
				</c:if>
				
				<c:if test="${model.contractType == '3000'}">
					<table id="_sino_eoss_sales_contract_maproducts_table" style="" class="table  table-bordered table-striped">
					  <thead>
					    <tr>
					      <th style="width:6%;">序号</th>
					      <th style="width:9%;">产品厂商</th>
					      <th style="width:9%;">产品型号</th>
					      <th style="width:8%;">序列号</th>
					      <th style="width:9%;">服务期(月)</th>
					      <th style="width:11%;">服务开始日期</th>
					      <th style="width:11%;">服务结束日期</th>
					      <th style="width:9%;">销售价格</th>
					      <th style="width:9%;">销售数量</th>
					      <th style="width:9%;">销售总价</th>
					      <th style="width:9%;">设备属地</th>
					    </tr>
					  </thead>
					  <tbody>
					  <c:forEach var="salesContractProductModel" items="${model.salesContractProductModel}" varStatus="status">
							<tr style='text-algin:center' id='tr_${ status.index + 1}'>
								<td >${ status.index + 1}</td>
								<td><span>${salesContractProductModel.productPartnerName }</span><input id='_product_Partner_${ status.index + 1}' name='productPartners' type='hidden' value='${salesContractProductModel.productPartner }'><input id='_product_Partner_Name_${ status.index + 1}' name='productPartnerNames' type='hidden' value='${salesContractProductModel.productPartnerName }'></td>
								<td title="${salesContractProductModel.remark}"><span>${salesContractProductModel.productName}</span><input id='_product_No_${ status.index + 1}' name='productNos' type='hidden' value='${salesContractProductModel.productNo }'><input id='_product_Name_${ status.index + 1}' name='productNames' type='hidden' value='${salesContractProductModel.productName }'></td>
								<td><span>${salesContractProductModel.serialNumber }</span><input id='_product_serialNumber_${ status.index + 1}' name='serialNumber' type='hidden' value='${salesContractProductModel.serialNumber }'></td>
								<td><span>${salesContractProductModel.servicePeriod }</span><input id='_product_servicePeriod_${ status.index + 1}' name='servicePeriod' type='hidden' value='${salesContractProductModel.servicePeriod }'></td>
								<td><span>${salesContractProductModel.serviceStartDate }</span><input id='_product_serviceStartDates_${ status.index + 1}' name='serviceStartDates' type='hidden' value='${salesContractProductModel.serviceStartDate }'></td>
								<td><span>${salesContractProductModel.serviceEndDate }</span><input id='_product_serviceEndDates_${ status.index + 1}' name='serviceEndDates' type='hidden' value='${salesContractProductModel.serviceEndDate }'></td>
								<td><span><fmt:formatNumber value="${salesContractProductModel.unitPrice }" type="currency" currencySymbol="￥"/></span><input id='_product_unitPrice_${ status.index + 1}' name='unitPrices' type='hidden' value='${salesContractProductModel.unitPrice }'></td>
								<td><span>${salesContractProductModel.quantity }</span><input id='_product_quantity_${ status.index + 1}' name='quantitys' type='hidden' value='${salesContractProductModel.quantity }'></td>
								<td><span><fmt:formatNumber value="${salesContractProductModel.totalPrice }" type="currency" currencySymbol="￥"/></span><input id='_product_totalPrice_${ status.index + 1}' name='totalPrices' type='hidden' value='${salesContractProductModel.totalPrice }'></td>
								<td><span>${salesContractProductModel.equipmentSplace }</span><input id='_product_equipmentSplace_${ status.index + 1}' name='equipmentSplace' type='hidden' value='${salesContractProductModel.equipmentSplace }'></td>
							</tr>
						</c:forEach>
					  </tbody>
					</table>
				</c:if>
				
				</div>
			</div>
			<div class="product_handler" style="float:right;margin-right:40px;">合计:<span id="_product_total"  >0</span>元</div>
			<!-- 9行end -->	
			<!-- 分割行start -->
			<div class=" row-inline collection_plan" >
				<hr size=1 class="dashed-inline">
			</div>
			<!-- 分割行end -->
			<!-- 小标题start -->
			<div class=" row-inline collection_plan" >
				<h5 class="form-section-title"><a name="colltionPlan">收款计划</a></h5>
			</div>
			<!-- 小标题end -->
			<!-- 10行start -->
			<div class=" row-inline collection_plan" >
				<div class='table-tile-width'>
					<div id="editTable"></div>
				</div>
			</div>	
			<!-- 10行end -->
			<!-- 分割行start -->
			<div class=" row-inline" >
				<hr size=1 class="dashed-inline">
			</div>
			
			<div class=" row-inline collection_plan" >
				<h5 class="form-section-title"><a name="colltionBill">回款明细</a>
					<div style="float:right;margin-right:50px;">
						<span id="_sales_feeincome_amount" style="color:red;"></span>
					</div>
				</h5>
			</div>
			<!-- 小标题end -->
			<!-- 10行start -->
			<div class=" row-inline collection_plan" >
				<div class='table-tile-width'>
					<div class="form_table clearfix" style="position: relative; z-index: auto;">
						<table id="_sales_feeincome" width="100%" cellspacing="0" border="0" id="editGridName">
							<thead>
							<tr>
							<th>回款金额</th>
							<th>回款日期</th>
							<th>收款人</th>
							<th>备注</th>
							</tr>
							</thead>
							<tbody>
							</tbody>
						</table>
					</div>
				</div>
			</div>	
			<!-- 10行end -->
			<!-- 分割行start -->
			<div class=" row-inline" >
				<hr size=1 class="dashed-inline">
			</div>
			
			<!-- 分割行end -->
			<!-- 小标题start -->
			<div class=" row-inline" >
				<h5 class="form-section-title"><a name="salesFile">合同附件</a></h5>
			</div>
			<!-- 小标题end -->
			<!-- 11行start -->
			<div class=" row-inline" >
				<div id="uplaodfile"></div>
			</div>	
			
			<br class='float-clear' />
			
			<div class='row-inline'>
				<label class='row-label' for='contractRemark' placeholder='请输入合同备注'><span style="color:red;"></span>备注：</label>
				<div style="float:left;margin-top:5px;">${model.contractRemark }</div>
			</div>
			<!-- 11行end -->								
			<br class='float-clear' />
			
			<c:if test="${isFlow!='isFlow'}">
			<div class=" row-inline" >
				<hr size=1 class="dashed-inline">
			</div>
			<!-- 分割行end -->
			<!-- 小标题start -->
			<c:if test="${fn:length(invoice) > 0 }">
			<div class=" row-inline" >
				<h5 class="form-section-title"><a name="invoicePlan">发票计划</a></h5>
			</div>
			<!-- 小标题end -->
			<!-- 13行start -->	
			<div class=" row-inline" >
				<div class='table-tile-width'>
					<table border="0" style="width: 100%" class="sino_table_body" id="_invoice_table">
						<thead>
							<tr>
								<td class="sino_table_label" style="background:none repeat scroll 0 0 #F3F3F3;width:15%;">发票金额</td>
								<td class="sino_table_label" style="background:none repeat scroll 0 0 #F3F3F3;width:15%;">开发票日期</td>
								<td class="sino_table_label" style="background:none repeat scroll 0 0 #F3F3F3;width:20%;">发票类型</td>
								<td class="sino_table_label" style="background:none repeat scroll 0 0 #F3F3F3;width:15%;">备注</td>
								<td class="sino_table_label" style="background:none repeat scroll 0 0 #F3F3F3;width:15%;">通过时间</td>
								<td class="sino_table_label" style="background:none repeat scroll 0 0 #F3F3F3;width:15%;">发票状态</td>
							</tr>
						</thead>
						<tbody>
						<c:forEach var="invoice" items="${invoice}" varStatus="status1">
							<tr>
								<td class="sino_table_label _invoiceAmount_td" invoiceFinalInspection="${invoice.finalInspection }" invoiceAmount="${invoice.invoiceAmount }" invoiceType="${invoice.invoiceType }"><fmt:formatNumber value="${invoice.invoiceAmount }" type="currency" currencySymbol="￥"/> <input type="hidden" class="_invoice_input" value="${invoice.invoiceAmount }" /></td>
								<td class="sino_table_label">${invoice.invoiceTime }</td>
								<td class="sino_table_label" id ="invoiceType${status1.index}">${invoice.invoiceType }</td>
								<td class="sino_table_label" >${invoice.remark }</td>
								<td class="sino_table_label" >${invoice.finalInspection }</td>
								<%-- <c:if test="${invoice.invoiceStatus == 'TGSH'}">
									<td class="sino_table_label" id ="invoiceStatus${status1.index}">审批通过</td>
								</c:if>
								<c:if test="${invoice.invoiceStatus == 'FQ'}">
									<td class="sino_table_label" id ="invoiceStatus${status1.index}">废弃</td>
								</c:if>
								<td class="sino_table_label" id ="invoiceStatus${status1.index}">${invoice.invoiceStatus }</td>
								<c:if test="${invoice.invoiceStatus == 'SH'}">
									<td class="sino_table_label" id ="invoiceStatus${status1.index}">审批中</td>
								</c:if> --%>
								<td><a href="#" name="contract_invoice_handler" porId="${invoice.processInstanceId}" id="porId_${ status1.index}" class="contract_invoice_handler">
								<font color="#1076be">审核日志详情</font></a></td>
							</tr>
						</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
			<div style="margin-left:20px;" id="_invoiceinfo_div">
				<label class="row-label">已开发票：<span id="_yes_invoice_span"></span></label>
				<label style="margin-left:20px;" class="row-label">未开发票：<span id="_not_invoice_span"></span></label>
			</div>
			</c:if>
			<!-- 13行end -->	
			<br class='float-clear' />
			<!-- 分割行end -->
			<!-- 小标题start -->
			<c:if test="${model.contractType=='9000' }">
			<div class=" row-inline" >
				<h5 class="form-section-title"><a name="invoicePlan">关联合同编号</h5>
			</div>
			<div class=" row-inline" >
				<c:forEach var="code" items="${salesCode}" varStatus="status1">
					<a href="${ctx}/sales/contract/detail?id=${code.id }" target="_blank"><span style="color: #0000dd;">${code.contractCode }</span></a>
				</c:forEach>
			</div>
			
			</c:if>
			<!-- 13行end -->	
			<br class='float-clear' />
			
			</c:if>
			
			<c:if test="${model.isChanged==1}">
			<!-- 分割行start -->
			<div class=" row-inline" >
				<hr size=1 class="dashed-inline">
			</div>
			<!-- 分割行end -->
			<!-- 小标题start -->
			<div class=" row-inline" >
				<h5 class="form-section-title">变更详情</h5>
			</div>
			<!-- 小标题end -->
			<!-- 12行start -->
			<c:forEach var="ContractSnapShootList" items="${ContractSnapShootList}" varStatus="status">
			<div class=" row-inline" >
				<div class=''>
					<label class='row-label' for='contractName' >历史版本：</label>
					<div class='row-span-oneColumn'>
						<span><a href='${ctx}/sales/contractSnapShoot/toDetail?id=${ContractSnapShootList.id}' target='_blank' class='_contractChange'><i class='icon-pencil'></i>${ContractSnapShootList.saleContractVersion}</a>原因：${ContractSnapShootList.remark}</span>
					</div>
				</div>
			</div>
			</c:forEach>
			<!-- 12行end -->	
			</c:if>							
			<br class='float-clear' />
			<!-- 分割行start -->
			<div class=" row-inline" >
				<hr size=1 class="dashed-inline">
			</div>
			
			<c:if test="${merge!=null}">
			<div class=" row-inline" >
				<h5 class="form-section-title">合并详情</h5>
			</div>
				<c:forEach var="merge" items="${merge}" varStatus="status">
			<div class=" row-inline" >
			<div class='row-label' for='contractName' >合并的子合同：</div>
				<div class='row-span-oneColumn'>
					<div><span class="stroke"> <a target="_blank" href="${ctx }/sales/contract/detail?id=${merge.id}"  style="color:#1076be;">${merge.contractName} </a></span>${merge.contractCode}</div>
					
				</div>
			</div>
			</c:forEach>
			<div class=" row-inline" >
				<div class='row-label' for='contractName' >合并原因：</div>
				<div class='row-span-oneColumn'>${mergelog.remark}</div>
			</div>
			</c:if>
			<br class='float-clear' />
			<!-- 分割行start -->
			<div class=" row-inline" >
				<hr size=1 class="dashed-inline">
			</div>
			
			<!-- 分割行end -->
			<!-- 小标题start -->
			<c:if test="${fn:length(proInstLogList) > 0 }">
			<div class=" row-inline" >
				<h5 class="form-section-title"><a name="auditLog">合同审核日志</a></h5>
			</div>
			<!-- 小标题end -->
			<!-- 13行start -->
			<div class=" row-inline" >
				<div class='table-tile-width'>
					<table border="0" style="width: 100%" class="sino_table_body" id="_fee_travel">
						<thead>
							<tr>
								<td class="sino_table_label" style="background:none repeat scroll 0 0 #F3F3F3;width:20%;">操作人</td>
								<td class="sino_table_label" style="background:none repeat scroll 0 0 #F3F3F3;width:20%;">审批阶段</td>
								<td class="sino_table_label" style="background:none repeat scroll 0 0 #F3F3F3;width:20%;">审批结果</td>
								<td class="sino_table_label" style="background:none repeat scroll 0 0 #F3F3F3;width:20%;">操作时间</td>
								<td class="sino_table_label" style="background:none repeat scroll 0 0 #F3F3F3;width:20%;">详情</td>
							</tr>
						</thead>
						<tbody>
						<c:forEach var="logs" items="${proInstLogList}" varStatus="status">
							<tr>
								<td class="sino_table_label">${logs.user }</td>
								<td class="sino_table_label">${logs.taskName }</td>
								<td class="sino_table_label">${logs.apprResultDesc }</td>
								<td class="sino_table_label"><fmt:formatDate value="${logs.time }" pattern="yyyy-MM-dd HH:mm"/> </td>
								<td class="sino_table_label">${logs.apprDesc }</td>
							</tr>
						</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
			</c:if>
			<c:if test="${(approName != ''&&approName != null)||fn:length(cachetLogList) > 0 }">
			<div class=" row-inline" >
				<h5 class="form-section-title"><a name="auditLog">盖章审核日志</a>
				<c:if test="${approName != ''&&approName != null}">
					<span style="font-size:10px;font-weight:normal;padding-left:12px;">当前审批人:${approName }</span>
				</c:if>
				</h5>
			</div>
			<!-- 小标题end -->
			<!-- 13行start -->
			<div class=" row-inline" >
				<div class='table-tile-width'>
					<table border="0" style="width: 100%" class="sino_table_body">
						<thead>
							<tr>
								<td class="sino_table_label" style="background:none repeat scroll 0 0 #F3F3F3;width:20%;">操作人</td>
								<td class="sino_table_label" style="background:none repeat scroll 0 0 #F3F3F3;width:20%;">审批阶段</td>
								<td class="sino_table_label" style="background:none repeat scroll 0 0 #F3F3F3;width:20%;">审批结果</td>
								<td class="sino_table_label" style="background:none repeat scroll 0 0 #F3F3F3;width:20%;">操作时间</td>
								<td class="sino_table_label" style="background:none repeat scroll 0 0 #F3F3F3;width:20%;">详情</td>
							</tr>
						</thead>
						<tbody>
						<c:forEach var="logs" items="${cachetLogList}" varStatus="status">
							<tr>
								<td class="sino_table_label">${logs.user }</td>
								<td class="sino_table_label">${logs.taskName }</td>
								<td class="sino_table_label">${logs.apprResultDesc }</td>
								<td class="sino_table_label"><fmt:formatDate value="${logs.time }" pattern="yyyy-MM-dd HH:mm"/> </td>
								<td class="sino_table_label">${logs.apprDesc }</td>
							</tr>
						</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
			</c:if>
			
			<!-- 13行end -->	
			<br class='float-clear' />
			<c:if test="${isFlow!='isFlow'}">
			<div class="form-actions" >
				<div style="text-align:center;width:600px;">
					<a id='_sino_eoss_sales_contract_detailBack' role="button" class="btn"><i class="icon-remove-sign"></i>关闭</a>
				</div>
			</div>
			</c:if>	
			<c:if test="${isFlow=='isFlow'}">
			<!-- 分割行start -->
			<div class=" row-inline" >
				<hr size=1 class="dashed-inline">
			</div>
			<!-- 分割行end -->
			<!-- 小标题start -->
			<div class=" row-inline" >
				<h5 class="form-section-title">审批</h5>
			</div>
			<!-- 小标题end -->
			<!-- 审核第1行end -->	
		
			
			<!-- 审核第1行start -->
			<div class=" row-inline" >
				<div class=''>
				<div style="float:left;width:400px;">
					<label class="checkbox inline">
						同意
					</label>
					<input type="radio" id="inlineCheckbox1" name="isAgree" value="1" checked="checked">
					<label class="checkbox inline">
						驳回
					</label>
					<input type="radio" id="inlineCheckbox2" name="isAgree" value="0">
					</div>

				</div>

			</div>
		
			
			<br class='float-clear' />
			<!-- 审核第3行start -->
			<div class=" row-inline" style="margin-top:15px">
				<div class='' style="float:left;width:400px;">
					<label class='row-label'  style="color:#222222;" for='contractName'  >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;审批意见：</label>
					<div class='row-input'>
						<textarea rows="3" name="remark"></textarea>
						<input type="hidden" id="flowFlag" name="flowFlag" value="${flowStep}" >
					</div>
				</div>
				<div style="float:left;width:380px;color:#888888;">
			     <font  color="orange">  温馨提示：</font><br/>
			                        1.销售合同金额，及发票类型是否合理；<br/>
			                        2.服务类型和服务期限是否合理；<br/>
			                        3.收款计划及验收文档是否合理；<br/>
			                        4.合同电子档与纸质合同是否一致，合同编号是否一致
			  </div>
			</div>
			
					<c:if test="${flowStep=='SWJLSP'}">
			<br class='float-clear' />
		    <!-- <div class=" row-inline" style="margin-top:15px">
				<div class=''>
					<label class='row-label' for='_formStaff'  style="color:orange;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;预估毛利：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
					<div class='row-input' ><input type="text" name="busiEstimateProfit"  style="width:50px;" value="10%">&nbsp;&nbsp;<span style="color:#888888;">(请输入百分比；例如：5%，10%)</span></div>
				</div>
			</div>
			<div class=" row-inline" style="margin-top:15px">
				<div class=''>
					<label class='row-label' for='_formStaff'  style="color:orange;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;预估毛利描述：</label>
					<div class='row-input' ><input type="text" name="busiEstimateProfitDesc"  style="width:500px;" placeholder="请输入预估毛利说明"></div>
				</div>
			</div> -->
			<!-- 审核第2行start -->
			<c:if test="${flat==1 }">
			<div class=" row-inline" style="margin-top:15px;width:800px;">
				<div class=''>
					<label class='row-label' for='_formStaff'  style="color:orange;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;选择采购员：&nbsp;&nbsp;&nbsp;</label>
					<div class='row-input' id='formStaff'></div>
				</div>
			</div>
			</c:if>
			<!-- 审核第2行end -->
			</c:if>	
			<!-- 审核第3行end -->
			<br class='float-clear' />
			<div class="form-actions">
				<div style="text-align:center;width:600px;">
					<a id='_sino_eoss_sales_contract_submit' role="button" class="btn btn-success"><i class="icon-ok"></i>确认</a>
					&nbsp;&nbsp;&nbsp;&nbsp;
					<a id='_sino_eoss_sales_contract_checkBack' role="button" class="btn"><i class="icon-remove-sign"></i>关闭</a>
				</div>
			</div>
			</c:if>
			</form>
			<!-- END FORM-->  
		</div>
	<script language="javascript">
		var form = ${form};  //从controller层接受数据模型
		var flowFlag= "${flowStep}";
		seajs.use('js/page/sales/contract/detail',function(detail){
			detail.init();
		});    
	</script>
</body>
</html>
