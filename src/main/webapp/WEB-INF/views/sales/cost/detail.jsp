<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/global.jsp"%>
<%@ page import="com.sinobridge.systemmanage.util.Global"%> 
<%@ page import="com.sinobridge.systemmanage.vo.SystemUser"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
	<title>资金占用成本</title>
	<%@ include file="/common/include-base-boostrap-styles.jsp" %>
	<link href="${ctx}/js/plugins/select2/css/select2.css" rel="stylesheet"/>
	<link rel="stylesheet" href="${ctx}/js/plugins/bootstrap/css/bootstrap-datetimepicker.min.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/sinobridge/pink/sino_form_grid.css" type="text/css" />
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formSelect.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formSearchTip.css" type="text/css" ></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formstyle.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/dialog.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/tip.css" type="text/css" ></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/jquery-ui-1.8.17.custom.css" type="text/css" ></link>
	<link rel="stylesheet" href="${ctx}/skin/default/editgrid/editgrid.css" type="text/css" />
	<link href="${ctx}/skin/default/sales/sale.css" rel="stylesheet"/>
	
	<style type="text/css">
	</style>
</head>
	<script src="${ctx}/js/plugins/seajs/sea.js" id="seajsnode" type="text/javascript"></script>
	<script src="${ctx}/js/plugins/seajs/config.js" type="text/javascript"></script>
<body>
	
	<div class="salecontent">
		<div class="top">
			<div class="caption">
				<i class="icon-reorder"></i>&nbsp;&nbsp;&nbsp;合同编号：${model.ContractCode}
			</div>
		</div>
	</div>
		<div class="portlet-body form" id="top" >
			
			<!-- BEGIN FORM-->
			<form id='_sino_eoss_sales_contract_approveForm' name='_sino_eoss_sales_contract_approveForm' class='form-horizontal' >
				<!--隐藏字段start -->
			<%-- 	<!--  隐藏的合同ID-->
				<input type='hidden' name='id' id='id'  value="${model.id}"/> --%>
	
	<div class="handprocess_order" id="SheetDiv">
				<br>
		        <h3>合同基本信息</h3>			
					<ul class="clearfix" >
						<li id="field_userName" class="li_form" style="width:600px" >
							<label class="editableLabel" >合同名称：</label>${model.ContractName}
						</li>
						 <li id="field_userName" class="li_form" style="width:300px" >
					          <label  class="editableLabel">合同金额：</label><fmt:formatNumber value="${model.ContractAmount}" type="currency" currencySymbol="￥"/>
				         </li>
					</ul>
					<ul class="clearfix" >
					    <li id="field_userName" class="li_form" style="width:300px">
							<label  class="editableLabel">销售：</label>${model.CreatorName}
						</li>
						 <li id="field_userName" class="li_form" style="width:300px">
							<label  class="editableLabel">回款金额：</label><fmt:formatNumber value="${model.salesReceive}" type="currency" currencySymbol="￥"/>
						</li>
						 <li id="field_userName" class="li_form" style="width:300px">
							<label  class="editableLabel">成本：</label><fmt:formatNumber value="${model.orderPay}" type="currency" currencySymbol="￥"/>
						</li>
					</ul>
					<ul class="clearfix" >
					    <li id="field_userName" class="li_form" style="width:300px">
							<label  class="editableLabel">资金成本占用：</label><fmt:formatNumber value="${model.cost}" type="currency" currencySymbol="￥"/>
						</li>
					</ul>
					
					
				<h3>${time}之前回款日志</h3>
    			<table cellpadding="0" cellspacing="0" border="0" class="table table-striped table-bordered table-hover table_order" >
							<thead>
								<tr>
								<th style="width:30%;">回款金额</th>
								<th style="width:30%;">回款日期</th>
								<th style="width:40%;">备注</th>
								</tr>
							</thead>
							<tbody>
							<c:forEach var="revice" items="${revice}" >
								<tr>
									<td><fmt:formatNumber value="${revice.Amount}" type="currency" currencySymbol="￥"/></td>
									<td><fmt:formatDate value="${revice.ReceiveTime}" pattern="yyyy-MM-dd"/> </td>
									<td><fmt:formatNumber value="${revice.Remark}" type="currency" currencySymbol="￥"/></td>
								</tr>
							</c:forEach>
							</tbody>
				</table>
						
				<h3>${agoTime}之前付款日志</h3>
				 <c:forEach var="order" items="${order}" varStatus="status">
					<table class="table  table-bordered table_order" name="ordertable" style="width:960px;margin-top:8px;">
						<tr>
							<td colspan=4 style="padding:8px;font-size:14px;">
							<div style="float:left;width:300px;">订单编号:<a href="${ctx}/business/order/detail?flat=search&id=${order.id}" target="_blank" style="color:#005EA7;">${order.OrderCode}</a></div><div style="float:left;width:246px;">订单金额:<fmt:formatNumber value="${order.OrderAmount}" type="currency" currencySymbol="￥"/></div><div style="float:left;width:246px;">订单付款:<fmt:formatNumber value="${order.pay}" type="currency" currencySymbol="￥"/>
							</div></td>
						</tr>
						<tr style="background-color: #f3f3f3;color: #888888;">
							<td style="width:40%;">合同编号</td>
							<td style="width:20%;">合同金额</td>
							<td style="width:20%;">销售</td>
							<td style="width:20%;">成本</td>
							<!-- <td>资金占用成本</td> -->
						</tr>
						<c:forEach var="sales" items="${order.sales}" varStatus="status1">
							<tr>
								<c:if test="${sales.id==salesId }">
									<td style="font-weight:bold;">
										<a href="${ctx }/sales/contract/detail?id=${sales.id}&flowStep=SHOW" target="_blank" style="color:#005EA7;">${sales.ContractCode}</a>
									</td>
								</c:if>
								<c:if test="${sales.id!=salesId }">
									<td>${sales.ContractCode}</td>
								</c:if>
								<td><fmt:formatNumber value="${sales.ContractAmount}" type="currency" currencySymbol="￥"/></td>
								<td>${sales.CreatorName}</td>
								<td><fmt:formatNumber value="${sales.payOrder}" type="currency" currencySymbol="￥"/></td>
								<%-- <td><fmt:formatNumber value="${sales.payOrder}" type="currency" currencySymbol="￥"/></td> --%>
							</tr>
						</c:forEach>
				</table>
					
					
			</c:forEach>
			 <div class=" row-inline" style="margin-left:0;" id="payremi">
						<hr size=1 class="dashed-inline">
				</div>
</form>
			<!--按钮组-->
			<div id="bottom_button" class="handprocess_btngroup">
			    <a id='_eoss_business_back' role="button" class="btn"><i class="icon-remove-circle"></i>关闭</a>
			</div>
			<div style="clear:both;"></div>
		</div>
	</div>
	<script language="javascript">
		seajs.use('js/page/sales/cost/detail',function(detail){
			detail.init();
		});    
	</script>
</body>
</html>
