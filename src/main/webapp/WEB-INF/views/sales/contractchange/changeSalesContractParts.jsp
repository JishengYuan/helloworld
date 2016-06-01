<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/global.jsp"%>
<%@ page import="com.sinobridge.systemmanage.util.Global"%> 
<%@ page import="com.sinobridge.systemmanage.vo.SystemUser"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
	<title>增加备品备件</title>
	<%@ include file="/common/include-base-boostrap-styles.jsp" %>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formSelect.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formstyle.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/dialog.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/tip.css" type="text/css" ></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/user-selection.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formSearchTip.css" type="text/css" ></link>
	<link rel="stylesheet" href="${ctx}/skin/default/editgrid/editgrid.css" type="text/css" />
<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/jquery-ui-1.8.17.custom.css" type="text/css" ></link>	
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
				  <c:if test="${ChangeApply=='1'&&model.contractType != '9000'}">                       
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
			</div>
			<div class="tools">
				<!--a href="javascript:;" class="reload"><i class="icon-repeat"></i>返回</a-->
			</div>
		</div>
	</div>
		<div class="portlet-body form">
			
			<div class="_spot_div" style="position: fixed; margin-top: 50px; left: 50%;margin:0 0 0 480px; bottom: 0px; border: 1px solid #d8d8d8;">
				<ul style="opacity:0.55;">
					<li>&nbsp;<a href="#salesObj">回顶端</a></li>
					<li>&nbsp;<a href="#salesBill">合同清单</a></li>
					<li>&nbsp;<a href="#colltionPlan">收款计划</a></li>
					<li>&nbsp;<a href="#salesFile">合同附件</a></li>
				</ul>
			</div>
			
			<!-- BEGIN FORM-->
			<form id='_sino_eoss_sales_contract_change_addform' name='_sino_eoss_sales_contract_approveForm' class='form-horizontal' >
				<!--隐藏字段start -->
				<!--  隐藏的合同ID-->
				<input type='hidden' name='id' id='id'  value="${model.id}"/>
			<!--  隐藏的合同变更申请的ID-->
			<input type='hidden' name='salesContractChangeApplyId' id='salesContractChangeApplyId'  value="${changeApplyModel.id}"/>
			<!--  隐藏的合同变更申请的变更内容类型-->
			<input type='hidden' name='changeType' id='changeType'  value="${changeApplyModel.changeType}"/>
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
				<!--  隐藏的合同金额-->
					<input type="hidden" id="contractAmount" name="contractAmount" value="${model.contractAmount}" >
				<input type='hidden' name='customerContact' id='customerContact' value="${model.customerContact }" />
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
						<span id="contractAmountshow">${model.contractAmount}</span>
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
			<div class=" row-inline"  style="margin-top:40px;"></div>
				
				<div class="products_add_btn">
					<!-- 产品总金额:<span id="_product_total"></span> -->
					<a id='_sino_eoss_sales_products_add' role="button" class="btn btn-success"><i class="icon-plus"></i>添加</a>
				</div>
			<!-- 小标题end -->
			<!-- 9行start -->	
			<div class="row-inline product_handler">
				<div class='table-tile-width'>
				<c:if test="${model.contractType != '9000'}">
					<table id="_sino_eoss_sales_contract_products_table"class="table  table-bordered">
					  <thead>
					    <tr id="_contract_show" style="background-color: #f3f3f3;color: #888888;">
					      <th style="width:6%;">序号</th>
					      <th style="width:9%;" id="type_th">产品类型</th>
					      <th style="width:9%;" id="partner_th">产品厂商</th>
					      <th style="width:9%;" id="product_th">产品型号</th>
					      <th style="width:12%;">服务开始时间</th>
					      <th style="width:12%;">服务结束时间</th>
					      <th style="width:6%;">数量</th>
					      <th style="width:9%;">单价</th>
					      <th style="width:12%;">合计</th>
					      <th style="width:20%;">操作</th>
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
									<td >${ status.index + 1}<input id='_product_Type_${ status.index + 1}' name='contractProductIds' type='hidden' value='${salesContractProductModel.id }'></td>
									<td name="relateDeliveryProduct_contract" tid="${salesContractProductModel.relateDeliveryProductId }"></td>
									<td >${salesContractProductModel.productTypeName }</td>
									<td >${salesContractProductModel.productPartnerName }</td>
									<td title="${salesContractProductModel.remark}">${salesContractProductModel.productName }</td>
									<td >${salesContractProductModel.serviceStartDate }</td>
									<td >${salesContractProductModel.serviceEndDate }</td>
									<td >${salesContractProductModel.quantity }</td>
									<td><span><fmt:formatNumber value="${salesContractProductModel.unitPrice }" type="currency" currencySymbol="￥"/></span><input id='_product_unitPrice_${ status.index + 1}' name='unitPrices' type='hidden' value='${salesContractProductModel.unitPrice }'></td>
								<td tdPrice="${salesContractProductModel.totalPrice }"><span><fmt:formatNumber value="${salesContractProductModel.totalPrice }" type="currency" currencySymbol="￥"/></span><input id='_product_totalPrice_${ status.index + 1}' name='totalPrices' type='hidden' value='${salesContractProductModel.totalPrice }'><input id='_product_remark_${ status.index + 1}' name='productRemarks' type='hidden' value='${salesContractProductModel.remark }'></td>
								<td style='text-algin:center'>
									<c:if test="${salesContractProductModel.relateDeliveryProductId == null||salesContractProductModel.relateDeliveryProductId == '' }">
										<a serial_num='${ status.index + 1}' id='_sino_eoss_sales_contract_update_product_${ status.index + 1}'  class='btn btn-primary _sino_eoss_sales_contract_update_product' style='width:40px;font-size:12px;padding:2px;'><i class='icon-pencil'></i>修改</a>
										<input type='hidden' name='relateDeliveryProductId' /><input type='hidden' name='relateContractProductId' value='0'/>
									</c:if>
									<c:if test="${salesContractProductModel.relateDeliveryProductId != null&&salesContractProductModel.relateDeliveryProductId != '' }">
										<input type='hidden' name='relateDeliveryProductId' value="${salesContractProductModel.relateDeliveryProductId }"/><input type='hidden' name='relateContractProductId' value='0'/>
									</c:if>
									&nbsp;&nbsp;&nbsp;<a id1="${salesContractProductModel.relateDeliveryProductId }" id2="${salesContractProductModel.id }" serial_num='${ status.index + 1}' id='_sino_eoss_sales_contract_remove_product_${ status.index + 1}'  class='btn btn-danger _sino_eoss_sales_contract_update_remove_product' style='padding:2px;'><i class='icon-remove'></i>删除</a>
								</td>
								</tr>
							</c:if>
							<c:if test="${salesContractProductModel.relateDeliveryProductId == null||salesContractProductModel.relateDeliveryProductId == '' }">
								<tr>
								<td >${ status.index + 1}<input id='_product_Type_${ status.index + 1}' name='contractProductIds' type='hidden' value='${salesContractProductModel.id }'></td>
								<td><span>${salesContractProductModel.productTypeName }</span><input id='_product_Type_${ status.index + 1}' name='productTypes' type='hidden' value='${salesContractProductModel.productType }'><input id='_product_Type_Name_${ status.index + 1}' name='productTypeNames' type='hidden' value='${salesContractProductModel.productTypeName }'></td>
								<td><span>${salesContractProductModel.productPartnerName }</span><input id='_product_Partner_${ status.index + 1}' name='productPartners' type='hidden' value='${salesContractProductModel.productPartner }'><input id='_product_Partner_Name_${ status.index + 1}' name='productPartnerNames' type='hidden' value='${salesContractProductModel.productPartnerName }'></td>
								<td><span>${salesContractProductModel.productName}</span><input id='_product_No_${ status.index + 1}' name='productNos' type='hidden' value='${salesContractProductModel.productNo }'><input id='_product_Name_${ status.index + 1}' name='productNames' type='hidden' value='${salesContractProductModel.productName }'></td>
								<td><span>${salesContractProductModel.serviceStartDate }</span><input id='_product_startTime_${ status.index + 1}' name='serviceStartDates' type='hidden' value='${salesContractProductModel.serviceStartDate }'></td>
								<td><span>${salesContractProductModel.serviceEndDate }</span><input id='_product_endTime_${ status.index + 1}' name='serviceEndDates' type='hidden' value='${salesContractProductModel.serviceEndDate }'></td>
								<td><span>${salesContractProductModel.quantity }</span><input id='_product_quantity_${ status.index + 1}' name='quantitys' type='hidden' value='${salesContractProductModel.quantity }'></td>
								<td><span><fmt:formatNumber value="${salesContractProductModel.unitPrice }" type="currency" currencySymbol="￥"/></span><input id='_product_unitPrice_${ status.index + 1}' name='unitPrices' type='hidden' value='${salesContractProductModel.unitPrice }'></td>
								<td tdPrice="${salesContractProductModel.totalPrice }"><span><fmt:formatNumber value="${salesContractProductModel.totalPrice }" type="currency" currencySymbol="￥"/></span><input id='_product_totalPrice_${ status.index + 1}' name='totalPrices' type='hidden' value='${salesContractProductModel.totalPrice }'><input id='_product_remark_${ status.index + 1}' name='productRemarks' type='hidden' value='${salesContractProductModel.remark }'></td>
								<td style='text-algin:center'>
									
								</td>
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
								<td class="sino_table_label" style="background:none repeat scroll 0 0 #F3F3F3;width:20%;">发票金额</td>
								<td class="sino_table_label" style="background:none repeat scroll 0 0 #F3F3F3;width:20%;">开发票日期</td>
								<td class="sino_table_label" style="background:none repeat scroll 0 0 #F3F3F3;width:20%;">发票类型</td>
								<td class="sino_table_label" style="background:none repeat scroll 0 0 #F3F3F3;width:20%;">备注</td>
								<td class="sino_table_label" style="background:none repeat scroll 0 0 #F3F3F3;width:20%;">发票状态</td>
							</tr>
						</thead>
						<tbody>
						<c:forEach var="invoice" items="${invoice}" varStatus="status1">
							<tr>
								<td class="sino_table_label"><fmt:formatNumber value="${invoice.invoiceAmount }" type="currency" currencySymbol="￥"/> <input type="hidden" class="_invoice_input" value="${invoice.invoiceAmount }" /></td>
								<td class="sino_table_label">${invoice.invoiceTime }</td>
								<td class="sino_table_label" id ="invoiceType${status1.index}">${invoice.invoiceType }</td>
								<td class="sino_table_label" >${invoice.remark }</td>
								<c:if test="${invoice.invoiceStatus == 'TGSH'}">
									<td class="sino_table_label" id ="invoiceStatus${status1.index}">审批通过</td>
								</c:if>
								<c:if test="${invoice.invoiceStatus == 'FQ'}">
									<td class="sino_table_label" id ="invoiceStatus${status1.index}">废弃</td>
								</c:if>
								<%-- <td class="sino_table_label" id ="invoiceStatus${status1.index}">${invoice.invoiceStatus }</td> --%>
								<c:if test="${invoice.invoiceStatus == 'SH'}">
									<td class="sino_table_label" id ="invoiceStatus${status1.index}">审批中</td>
								</c:if>
							</tr>
						</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
			</c:if>
			<!-- 13行end -->	
			<br class='float-clear' />
			
			<!-- 分割行end -->
			<!-- 小标题start -->
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
						<span><a href='${ctx}/sales/contractSnapShoot/toDetail?id=${ContractSnapShootList.id}' target='_blank' class='_contractChange'><i class='icon-pencil'></i>${ContractSnapShootList.saleContractVersion}</a></span>
					</div>
				</div>
			</div>
			</c:forEach>
			<!-- 12行end -->	
			</c:if>							

	
			<!-- 13行end -->	
			<br class='float-clear' />

			<div class="form-actions" >
				<div style="text-align:center;width:600px;">
					<a id='_sino_eoss_sales_contract_change_submit' role="button" class="btn btn-success"><i class="icon-ok"></i>提交</a>
					<a id='_sino_eoss_sales_contract_detailBack' role="button" class="btn"><i class="icon-remove-sign"></i>关闭</a>
				</div>
			</div>

		
			</form>
			<!-- END FORM-->  
		</div>
		
					<!--设备添加弹出对话框 -------start-->
			 <div id="dailogs1" class="modal hide fade" role="dialog" tabIndex="-1" style="width:800px;height:460px;margin-left:-400px">
		   		<div class="modal-header">
				   <h3 id="dtitle"></h3>
				   <div id="_product_alertMsg"></div>
		  		</div>
				<div class="modal-body" style="height:70%">
					<div id="dialogbody" ></div>
				</div>
				<div class="modal-footer">
					<button id="_do_cancel"  class="btn" data-dismiss="modal" aria-hidden="true" >取消</button >
					<a id="dsave"  class="btn btn-primary" data-dismiss="modal" aria-hidden="true" >保存</a>
				</div>
			</div>
			<!--设备添加弹出对话框 -------end-->  		
	<script language="javascript">
		var form = ${form};  //从controller层接受数据模型
		var flowFlag= "${flowStep}";
		seajs.use('js/page/sales/contractchange/changeSalesContractParts',function(changeSalesContractParts){
			changeSalesContractParts.init();
		});    
	</script>
</body>
</html>
