	<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
	<%@ page language="java" contentType="text/html; charset=UTF-8"
		pageEncoding="UTF-8"%>
	<%@ include file="/common/global.jsp"%>
	<html>
	<head>
		<title>合同订单详细状态</title>
		<%@ include file="/common/include-base-boostrap-styles.jsp" %>
		<script src="${ctx}/js/plugins/seajs/sea.js" id="seajsnode" type="text/javascript"></script>
		<script src="${ctx}/js/plugins/seajs/config.js" type="text/javascript"></script>
		<link href="${ctx}/skin/default/sales/sale.css" rel="stylesheet"/>
<style>

.portlet-body.form {
	width: 1000px;
    margin-top: 30px;
}
.row-inline-first {
    margin-top: 0px;
}
.portlet-body.form label {
    display: block;
    margin-bottom: 0px;
}
.form-section-title{
   margin:0;
   margin-top:5px;
}
.table_order th,.table_order td{
padding:3px;
font-size:13px;
}

</style>
		
	</head>
	<body id="top">
	<fmt:setLocale value="zh_cn"/>
		<div class="salecontent">
			<div class="top">
				<div class="caption">
					<i class="icon-reorder"></i>&nbsp;&nbsp;&nbsp;合同订单状态
					<span class="tright" >合同编码：${model.contractCode}</span>			
				</div>
			</div>
		</div>
			<div class="portlet-body form" >
				<!-- BEGIN FORM-->
				<form id='_sino_eoss_sales_invoice_approveForm' class='form-horizontal' >
					<!--隐藏字段start -->
					<!--  隐藏的合同ID-->
					<input type='hidden' name='id' id='id'  value="${model.id}"/>
					<!--  隐藏的合同类型ID-->
					<input type="hidden" name="contractTypeId" id="contractTypeId" value="${model.contractType}"/>
					<!--  隐藏的合同名称-->
					<input type='hidden' name='contractName' id='contractName'  value="${model.contractName}"/>
					<!--隐藏的客户ID，用于回显-->
					<input type='hidden' name='customerId' id='_eoss_sales_customerId'  value="${model.customerId}"/>
					<!--  隐藏的tableData初始化数据-->
					<input type='hidden' name='tableData' id='tableData' />
					<!--  隐藏的流程中所需要的任务ID-->
					<input type="hidden" id="taskId" name="taskId" value="${taskId}" >
					<!--  隐藏的流程中所需要的工单ID-->
					<input type="hidden" id="procInstId" name="procInstId" value="${procInstId}" >
					<!--隐藏字段end -->	
					<!-- 小标题start -->
					<div class="row-inline-first" >
						<h4 class="form-section-title">合同基本信息</h4>
					</div>
					<!-- 小标题end -->
					<!-- 1行start -->
					<div class=" row-inline"  >
						<div class=''>
							<label class='row-label' for='contractName'  >合同名称：</label>
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
								<span id="contractAmount" style="color: #eeb422;font-weight:bold;">${model.contractAmount}元</span>
							</div>
						</div>
					</div>
					<!-- 2行end -->
					<!-- 3行start -->
					<div class=" row-inline" >
						<div class=''>
							<label class='row-label' for='contractName' >行业名称：</label>
							<div class='row-span'>
								<span id="_industryName"></span>
							</div>
						</div>
					</div>
					<div class=" row-inline" >
						<div class=''>
							<label class='row-label' for='contractName' >行业客户：</label>
							<div class='row-span'>
								<span id="_customerIdtCustomerName"></span>
							</div>
						</div>
					</div>
					<!-- 3行end -->
					<!-- 4行start -->
					<div class=" row-inline" >
						<div class=''>
							<label class='row-label' for='contractName' >客户名称：</label>
							<div class='row-span-twoColumn'>
								<span id="_customerInfoName"></span>
							</div>
						</div>
					</div>
					<div class=" row-inline" >
						<div class=''>
							<label class='row-label' for='contractName' >联&nbsp;&nbsp;系&nbsp;&nbsp;人：</label>
							<div class='row-span'>
								<span id="_customerContacts"></span>
							</div>
						</div>
					</div>
					<div class=" row-inline" >
						<div class=''>
							<label class='row-label' for='customerContactsPhone' >手机号码：</label>
							<div class='row-span'>
								<span id="_customerContactsPhone"></span>
							</div>
						</div>
					</div>
					<!-- 4行end -->
					<br class='float-clear'  id="salesBill"/>
							<!-- 分割行start -->
					<div class=" row-inline"  style="margin-left:0;">
						<hr size=1 class="dashed-inline" style=" width: 1000px;">
					</div>
					<!-- 分割行end -->
					<!-- 小标题start -->
					<div class=" row-inline" >
						<h4 class="form-section-title" >合同清单</h4>
					</div>	
						<div class=" row-inline" >
					<c:if test="${model.contractType != '9000'}">
					<table id="_sino_eoss_sales_contract_products_table" style="width:960px;" class="table  table-bordered table_order">
					  <thead>
					    <tr id="_contract_show" style="background-color: #f3f3f3;color: #888888;width:960px;">
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
					    <tr id="_contract_hidden" style="background-color: #f3f3f3;color: #888888;display:none;width:960px;">
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
					<table id="_sino_eoss_sales_contract_readyproducts_table" style="width:960px;" class="table  table-bordered table_order">
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
					<!-- 5行end -->	
					
					<!-- 分割行start -->
					<div class=" row-inline"  style="margin-left:0;" id="orderBill">
						<hr size=1 class="dashed-inline" style=" width: 1000px;">
					</div>
					<!-- 分割行end -->
					<!-- 小标题start -->
					<div class=" row-inline" >
						<h4 class="form-section-title">合同下单明细</h4>
					</div>	
					<div class=" row-inline" >
					   <c:forEach var="orderModel" items="${orderModel}" varStatus="status">
					        <table id="_order_${status.index }" class="table  table-bordered table_order" name="ordertable" style="width:960px;margin-top:8px;">
					           <tr>
					              <td colspan=7 style="padding:8px;font-size:14px;"><div style="float:left;width:246px;">订单编号:${orderModel.OrderCode }</div><div  style="float:left;width:410px;">订单名称:${orderModel.OrderName }</div><div  style="float:left;width:150px;">到货状态:${orderModel.ArrivalStatus }</div><div  style="float:left;width:100px;">采购员:<span style="font-weight:bold;">${orderModel.Creator }</span></div></td>
					           </tr>

								<tr style="background-color: #f3f3f3;color: #888888;">
								  <th style="width:20%;" class="_order_hidden" display:none;>备货合同</th>
								  <th style="width:10%;">产品类型</th>
								  <th style="width:10%;">厂商简称</th>
								  <th style="width:15%;">产品型号</th>
								  <th style="width:10%;">下单数量</th>
								  <th style="width:15%;">单价</th>
								  <th style="width:15%;">合计</th>
								</tr>
								<c:forEach var="product"  items="${orderModel.orderProduts}" varStatus="status1">
									<tr class="_product_list" id="trall_${status1.index }">
										<%-- <c:if test="${product.ContractCode!=model.contractCode }">
											<td class="sino_table_label" ><a href="${ctx }/sales/contract/detail?id=${product.id }" style="color:blue;" target="_blank">${product.ContractCode }</a></td>
										</c:if>
										<c:if test="${product.ContractCode==model.contractCode }">
											<td class="sino_table_label" ></td>
										</c:if> --%>
										<td class="sino_table_label _order_hidden" display:none;>
											<c:if test="${product.ContractCode!=model.contractCode }">
												<a href="${ctx }/sales/contract/detail?id=${product.id }" style="color:blue;" target="_blank">${product.ContractCode }</a>
											</c:if>
										</td>
										<td class="sino_table_label" >${product.typeName }</td>
										<td class="sino_table_label" >${product.partnerName }</td>
										<td class="sino_table_label" >${product.pName }</td>
										<td class="sino_table_label" name="orderQua">${product.orderQua }</td>
										<td class="sino_table_label"><fmt:formatNumber value="${product.unitprice }" type="currency" currencySymbol="￥"/> </td>
										<td class="sino_table_label"><fmt:formatNumber value="${product.subtotal }" type="currency" currencySymbol="￥"/> </td>
									</tr>
								</c:forEach>
								 <tr>
					              <td colspan=7 style="padding:8px;font-size:14px;">
					              <div style="float:left;width:240px;">已付款:<fmt:formatNumber value="${orderModel.PayAmount }" type="currency" currencySymbol="￥"/></div>
					              <div  style="float:left;width:240px;">已开发票:<fmt:formatNumber value="${orderModel.ReimAmount }" type="currency" currencySymbol="￥"/></div>
					              <div  style="float:left;width:240px;">订单金额:<fmt:formatNumber value="${orderModel.OrderAmount }" type="currency" currencySymbol="￥"/></div>
					              <div  style="float:left;width:220px;">采购成本:<span style="font-weight:bold;color: #eeb422;"  ><fmt:formatNumber value="${orderModel.orderCost }" type="currency" currencySymbol="￥"/></span></div>
					              <input type="hidden" name="orderCost" value="${orderModel.orderCost }"/>
					             <!--  <div  style="float:left;width:176px;">下单数量合计:<span id="productNum"></span></div> -->
					              </td>
					           </tr>
					        </table>
					   </c:forEach>
					
					</div>
			
			<div style="float:right;margin-right: 23px;">采购成本合计:<span style="font-weight:bold;color: #eeb422;" id="orderTotall"></span></div>
					
			
					<br class='float-clear' />
				</form>
				<!-- END FORM-->  
				<div class="form-actions">
				<div style='text-align:center;'>
					<button id='_sino_eoss_sales_contract_order_back' type="button" class="btn">关闭</button>
				</div>		
			</div>
		<script language="javascript">
			seajs.use('js/page/sales/contract/contractOrderDetail',function(contractOrderDetail){
				contractOrderDetail.init();
			});    
		</script>
		

		<div id="back-to-top" style="display: block;">
			<a href="#salesBill"><div style="background-color:#aaaaaa;color:#fff;margin-bottom:3px;">合同<br/>清单</div></a>
			<a href="#orderBill"><div style="background-color:#aaaaaa;color:#fff;margin-bottom:3px;">订单<br/>明细</div></a>
			<a href="#top"><span></span></a>
		</div>

	</body>
	</html>