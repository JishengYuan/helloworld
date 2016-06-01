<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/global.jsp"%>
<%@ page import="com.sinobridge.systemmanage.util.Global"%> 
<%@ page import="com.sinobridge.systemmanage.vo.SystemUser"%> 
<html>
<head>
	<title>合同管理</title>
	<%@ include file="/common/include-base-boostrap-styles.jsp" %>
	<link href="${ctx}/skin/default/sales/sale.css" rel="stylesheet"/>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formSelect.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formstyle.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/dialog.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/tip.css" type="text/css" ></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/user-selection.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formSearchTip.css" type="text/css" ></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/jquery-ui-1.8.17.custom.css" type="text/css" ></link>
	<link rel="stylesheet" href="${ctx}/skin/default/editgrid/editgrid.css" type="text/css" />

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
	</style>
	<script src="${ctx}/js/plugins/seajs/sea.js" id="seajsnode" type="text/javascript"></script>
	<script src="${ctx}/js/plugins/seajs/config.js" type="text/javascript"></script>
</head>
<body>
	<!-- 遮盖层div -->
	<div id="_progress1" style="display: none; top: 0px; left: 0px; width: 100%; margin-left: 0px; height: 100%; margin-top: 0px; z-index: 1000; opacity: 0.7; text-align: center; background-color: rgb(204, 204, 204); position: fixed;">
	</div>
	<div id="_progress2" style="display: none; width: 233px; height: 89px; top: 45%; left: 45%; position: absolute; z-index: 1001; line-height: 23px; text-align: center;">
		<div style="top: 50%;position:fixed;">
			<div style="display: block;">数据加载中，请稍等...</div>
		</div>
	</div>
 	<div class="salecontent">
		<div class="top">
			<div class="caption">
				<i class="icon-reorder"></i>&nbsp;&nbsp;&nbsp;合同信息详情
				<span class="tright" >合同编号：${model.contractCode}</span>
			
			</div>
			<div class="tools">
				<!--a href="javascript:;" class="reload"><i class="icon-repeat"></i>返回</a-->
			</div>
		</div>
	</div>
		<div class="portlet-body form">
			<!-- BEGIN FORM-->
			<form id='_sino_eoss_sales_contract_approveForm' name='_sino_eoss_sales_contract_approveForm' class='form-horizontal' >
				<!--隐藏字段start -->
				<!--  隐藏的合同ID-->
				<input type='hidden' name='id' id='id'  value="${model.id}"/>
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
				<!--隐藏字段end -->
			<!-- 小标题start -->
			<br>
			<br>
			<div class=" row-inline" >
				<h4 class="form-section-title">合同基本信息</h4>
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
			<%-- <div class=" row-inline" >
				<div class=''>
					<label class='row-label' for='contractName' >合同简称：</label>
					<div class='row-span'>
						<span>${model.contractShortName}</span>
					</div>
				</div>
			</div> --%>
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
						<span id="contractAmount">${model.contractAmount}元</span>
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
				<h4 class="form-section-title">客户信息（甲方）</h4>
			</div>
		
			<!-- 5行end -->
			<!-- 6行start -->
			<div class=" row-inline" >
				<div class=''>
					<label class='row-label' for='contractName' >客户选择：</label>
					<div class='row-span-oneColumn'>
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
					<label class='row-label' for='contractName' >手机号码：</label>
					<div class='row-span'>
						<span id="_customerContactsPhone"></span>
					</div>
				</div>
			</div>
			<!-- 6行end -->
			
			<div class=" row-inline" >
				<div class=''>
					<label class='row-label' for='contractName' >客户经理：<span style="color:#111111;">${model.creatorName}</span></label>
					<!-- <div class='row-span'>
					</div> -->
				</div>
			</div>
			
			<div class=" row-inline" >
				<hr size=1 class="dashed-inline">
			</div>
			
			<div class=" row-inline" >
				<h5 class="form-section-title" id="service_h5">合同服务支持</h5>
			</div>
			<div class=" row-inline" id="serviceStartDate_div">
				<div class=''>
					<label class='row-label' for='accountCurrency' >服务开始时间：</label>
						<div class='row-span-halfColumn' >${model.serviceStartDate }</div>
				</div>
			</div>
			<div style="margin-left: 45px;" class=" row-inline" id="serviceEndDate_div">
				<div class=''>
					<label class='row-label' for='invoiceType'>服务结束时间：</label>
					     <div class='row-span-halfColumn' >${model.serviceEndDate }</div>
				</div>
			</div>
			<!-- 8行end -->			
			<!-- 分割行start -->
			<div class=" row-inline" >
				<hr size=1 class="dashed-inline">
			</div>
			<!-- 分割行end -->
			<!-- 小标题start -->
			<div class=" row-inline" >
				<h4 class="form-section-title">设备信息</h4>
			</div>
			<!-- 小标题end -->
			<!-- 9行start -->	
			<div class=" row-inline" >
				<div class='table-tile-width'>
					<table id="_sino_eoss_sales_contract_products_table"class="table  table-bordered table-striped">
					  <thead>
					     <tr id="_contract_show" style="background-color: #f3f3f3;color: #888888;">
					      <th style="width:8%;text-align:center;">序号</th>
					      <th style="width:17%;text-align:center;">产品类型</th>
					      <th style="width:17%;text-align:center;">产品厂商</th>
					      <th style="width:17%;text-align:center;">产品型号</th>
					      <th style="width:11%;text-align:center;">数量</th>
					      <th style="width:12%;text-align:center;">单价</th>
					      <th style="width:18%;text-align:center;">合计</th>
					    </tr>
					    <tr id="_contract_hidden" style="background-color: #f3f3f3;color: #888888;display:none;">
					      <th style="width:5%;text-align:center;" >序号</th>
					      <th style="width:16%;text-align:center;">关联备货合同名称</th>
					      <th style="width:10%;text-align:center;" id="type_th">产品类型</th>
					      <th style="width:10%;text-align:center;" id="partner_th">产品厂商</th>
					      <th style="width:10%;text-align:center;" id="product_th">产品型号</th>
					      <th style="width:5%;text-align:center;">数量</th>
					      <th style="width:12%;text-align:center;">单价</th>
					      <th style="width:18%;text-align:center;">合计</th>
					    </tr>
					  </thead>
					  <tbody>
						<c:forEach var="salesContractProductModel" items="${model.salesContractProductModel}" varStatus="status">
						<c:if test="${salesContractProductModel.relateDeliveryProductId != null&&salesContractProductModel.relateDeliveryProductId != '' }">
							<tr>
								<td style="text-align:center;">${ status.index + 1}</td>
								<td name="relateDeliveryProduct_contract" tid="${salesContractProductModel.relateDeliveryProductId }"></td>
								<td style="text-align:center;">${salesContractProductModel.productTypeName }</td>
								<td style="text-align:center;">${salesContractProductModel.productPartnerName }</td>
								<td style="text-align:center;">${salesContractProductModel.productName }</td>
								<td style="text-align:center;">${salesContractProductModel.quantity }</td>
								<td style="text-align:center;">${salesContractProductModel.unitPrice }</td>
								<td style="text-align:center;">${salesContractProductModel.totalPrice }</td>
							</tr>
						</c:if>
						<c:if test="${salesContractProductModel.relateDeliveryProductId == null||salesContractProductModel.relateDeliveryProductId == '' }">
							<tr>
								<td style="text-align:center;" name="relateDeliveryProduct_contract_">${ status.index + 1}</td>
								<td style="text-align:center;">${salesContractProductModel.productTypeName }</td>
								<td style="text-align:center;">${salesContractProductModel.productPartnerName }</td>
								<td style="text-align:center;">${salesContractProductModel.productName }</td>
								<td style="text-align:center;">${salesContractProductModel.quantity }</td>
								<td style="text-align:center;">${salesContractProductModel.unitPrice }</td>
								<td style="text-align:center;">${salesContractProductModel.totalPrice }</td>
							</tr>
						</c:if>
						</c:forEach>
					  </tbody>
					</table>
				</div>
			</div>
			<!-- 9行end -->	
			<!-- 分割行start -->
			<div class=" row-inline" >
				<hr size=1 class="dashed-inline">
			</div>
				<br class='float-clear' />
				<div class=" row-inline" >
				      <h4 class="form-section-title">订单信息</h4>
			    </div>
			    <div class=" row-inline" >
				<div class='table-tile-width'>
				<%--<c:forEach var="orderProduct" items="${productmodel}" varStatus="status">
					 <div class=''>
						<label class='row-label'>订单名称：</label>
						<div class='row-span'>
							<span id="orderName">${orderProduct.businessOrder.orderName}</span>
						</div>
					</div>
					<div class=''>
						<label class='row-label' >订单状态：</label>
						<div class='row-span'>
							<span id="orderStatus">${orderProduct.businessOrder.orderStatus}</span>
							<input type="hidden" id="_orderStatus" value="${orderProduct.businessOrder.orderStatus}"/>
						</div>
					</div> --%>
					<table id="_sino_eoss_order_products_table" class="table  table-bordered table-striped">
						<thead>
							<tr style="background-color: #f3f3f3;color: #888888;">
						     	<td style="width:15%;text-align:center;">订单名称</td>
						     	<td style="width:15%;text-align:center;">产品类型</td>
								<td style="width:15%;text-align:center;">产品型号</td>
								<td style="width:8%;text-align:center;">厂商简称</td>
								<td style="width:8%;text-align:center;">单价</td>
								<td style="width:6%;text-align:center;">数量</td>
								<td style="width:18%;text-align:center;">服务类型</td>
								<td style="width:10%;text-align:center;">总价</td>
							</tr>
						</thead>
						<tbody>
						<c:forEach var="orderProduct" items="${productmodel}" varStatus="status">
							<tr>
								<%-- <input type="hidden" name="serviceType" value="${orderProduct.serviceType }"/> --%>
								<td style="text-align:center;">${orderProduct.businessOrder.orderName }</td>
								<td style="text-align:center;">${orderProduct.productType }</td>
								<td style="text-align:center;">${orderProduct.productNo }</td>
								<td style="text-align:center;">${orderProduct.vendorCode }</td>
								<td style="text-align:center;">${orderProduct.unitPrice }</td>
								<td style="text-align:center;">${orderProduct.quantity }</td>
								<td style="text-align:center;" id="serviceType${ status.index}">${orderProduct.serviceType }</td>
								<td style="text-align:center;">${orderProduct.subTotal }</td>
							</tr>
				     	</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
			<br class='float-clear' />
			</form>
			<div class="modal-footer">
			        <c:if test="${orderstatus=='no' }">
						<a id="reject"  class="btn btn-primary" data-dismiss="modal" aria-hidden="true" >驳回销售合同</a>
					</c:if>
					<c:if test="${isApply == true && close == 'yes'}">
						<a id="dsave"  class="btn btn-primary" data-dismiss="modal" aria-hidden="true" >申请关闭销售合同</a>
					</c:if>
					<button id="_do_cancel"  class="btn" data-dismiss="modal" aria-hidden="true" ><i class="icon-remove-circle"></i>返回</button >
				</div>
			
			<!-- END FORM-->  
		</div>
	</div>
	<script language="javascript">
		seajs.use('js/page/business/order/waitContract/detail',function(detail){
			detail.init();
		});    
	</script>
</body>
</html>
