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
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formSelect.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formstyle.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/dialog.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/tip.css" type="text/css" ></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/user-selection.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formSearchTip.css" type="text/css" ></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/jquery-ui-1.8.17.custom.css" type="text/css" ></link>
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
</head>
<body>
	<div class="salecontent">
		<div class="top">
			<div class="caption">
				<i class="icon-reorder"></i>&nbsp;&nbsp;&nbsp;合同历史版本信息(版本号：${model.saleContractVersion})
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
			<div class="row-inline-first" >
				<h5 class="form-section-title">合同基本信息</h5>
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
					<label class='row-label' for='contractName' >客户选择：</label>
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
				<h5 class="form-section-title" id="service_h5">商务预估毛利率</h5>
			</div>
			<div class=" row-inline">
				<div class=''>
					<label class='row-label'>预估毛利：</label><div style="margin-top:5px;float:left;">${model.busiEstimateProfit }</div>
				</div>
			</div>
			<div class=" row-inline">
				<div class=''>
					<label class='row-label' for=''>预估毛利描述：</label><div style="margin-top:5px;float:left;">${model.busiEstimateProfitDesc }</div>
				</div>
			</div>
			<!-- 分割行start -->
			<div class=" row-inline" >
				<hr size=1 class="dashed-inline">
			</div>
			<!-- 分割行end -->
			<!-- 小标题start -->
			<div class="row-inline product_handler">
				<h5 class="form-section-title">合同清单</h5>
				<div class="row-inline">
					<label class='row-label' for='receiveWay' >期望到货日期：</label>
					<div class="row-input" style="margin-top:5px;">${model.hopeArriveTime }</div>
					<label class='row-label' for='receiveWay' style="margin-left:10px;" >期望到货地点：</label>
					<div class="row-input" style="margin-top:5px;">${model.hopeArrivePlace }</div>
				</div>
			</div>
			<!-- 小标题end -->
			<!-- 9行start -->	
			<!-- 9行start -->	
			<div class="row-inline product_handler">
				<div class='table-tile-width'>
				<c:if test="${model.contractType != '9000'}">
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
						<c:forEach var="salesContractProductModel" items="${model.salesContractProductSnapShoots}" varStatus="status">
							<c:if test="${salesContractProductModel.relateDeliveryProductId != null&&salesContractProductModel.relateDeliveryProductId != '' }">
								<tr>
									<td >${ status.index + 1}</td>
									<td name="relateDeliveryProduct_contract" tid="${salesContractProductModel.relateDeliveryProductId }"></td>
									<td >${salesContractProductModel.productTypeName }</td>
									<td >${salesContractProductModel.productPartnerName }</td>
									<td >${salesContractProductModel.productName }</td>
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
									<td >${salesContractProductModel.productName }</td>
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
					  <c:forEach var="salesContractProductModel" items="${model.salesContractProductSnapShoots}" varStatus="status">
							<tr>
								<td name="relateDeliveryProduct_contract_">${ status.index + 1}</td>
								<td >${salesContractProductModel.productTypeName }</td>
								<td >${salesContractProductModel.productPartnerName }</td>
								<td >${salesContractProductModel.productName }</td>
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
			<div class=" row-inline" >
				<hr size=1 class="dashed-inline">
			</div>
			<!-- 分割行end -->
			<!-- 小标题start -->
			<div class=" row-inline" >
				<h4 class="form-section-title">收款计划</h4>
			</div>
			<!-- 小标题end -->
			<!-- 10行start -->
			<div class=" row-inline" >
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
				<h4 class="form-section-title">合同附件</h4>
			</div>
			<!-- 小标题end -->
			<!-- 11行start -->
			<div class=" row-inline" >
				<div id="uplaodfile"></div>
			</div>	
			<!-- 11行end -->								
			<br class='float-clear' />
			<c:if test="${model.isChanged==1}">
			<!-- 分割行start -->
			<div class=" row-inline" >
				<hr size=1 class="dashed-inline">
			</div>
			<!-- 分割行end -->
			<!-- 小标题start -->
			<div class=" row-inline" >
				<h4 class="form-section-title">变更详情</h4>
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
			<br class='float-clear' />
			<!-- 分割行start -->
			<div class=" row-inline" >
				<hr size=1 class="dashed-inline">
			</div>
			<!-- 分割行end -->
			<!-- 小标题start -->
			<div class=" row-inline" >
				<h4 class="form-section-title">审核日志</h4>
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
								<td class="sino_table_label">${logs.time }</td>
								<td class="sino_table_label">${logs.apprDesc }</td>
							</tr>
						</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
			<!-- 13行end -->	
			<br class='float-clear' />
			</form>
			<!-- END FORM-->  
		</div>
	<script language="javascript">
		var form = ${form};  //从controller层接受数据模型
		seajs.use('js/page/sales/contract/contractSnapShootDetail',function(contractSnapShootDetail){
			contractSnapShootDetail.init();
		});    
	</script>
</body>
</html>
