<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/global.jsp"%>
<html>
<head>
	<title>${model.contractName}</title>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formstyle.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/tip.css" type="text/css" ></link>
	<link rel="stylesheet" href="${ctx}/skin/default/editgrid/editgrid.css" type="text/css" />
	
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/timeLineStyle.css" type="text/css"></link>
	<link href="${ctx}/skin/default/sales/sale.css" rel="stylesheet"/>
	
	<script src="${ctx }/skin/login/js/jquery-1.7.2.min.js" language="javascript"></script>
	<script language="javascript">
		$(document).ready(function(e) {
			var h = $(".about4_main ul li:first-child").height()/2;<!--第一个li高度的一半-->
			var h1 = $(".about4_main ul li:last-child").height()/2;<!--最后一个li高度的一半-->
			$(".line").css("top",h);
			$(".line").height($(".about4_main").height()-h1-h);
		}); 
	</script>
	<style type="text/css">
		.row-inline {
		    margin-left: 40px;
		}
	</style>
</head>
<body>
	<div class="salecontent">
		<div class="top">
			<div class="caption">
				<i class="icon-reorder"></i>&nbsp;&nbsp;&nbsp;合同编号：${model.contractCode}
			</div>
		</div>
	</div>
		<div class="portlet-body form" id="top" >

			<!-- BEGIN FORM-->
			<form id='_sino_eoss_sales_contract_addform' name='_sino_eoss_sales_contract_addform' class='form-horizontal' >
			<!-- 1行start -->
			<div style="height:40px;"></div>
			<div class=" row-inline" style="width: width: 420px;">
				<div class=''>
					<label class='row-label' for='contractName' >合同名称：</label>
					<div class='row-span' style="width: 340px;">
						<span>${model.contractName}</span>
					</div>
				</div>
			</div>
			<div class=" row-inline" style="width: 190px;">
				<div class=''>
					<label class='row-label' for='contractName' >合同金额：</label>
					<div class='row-span' style="width: 100px;">
						<span id="contractAmount"><fmt:formatNumber value="${model.contractAmount}" type="currency" currencySymbol="￥"/></span>
					</div>
				</div>
			</div>
			<div class=" row-inline" style="width: 190px;">
				<div class=''>
					<label class='row-label' for='contractName' >客户经理：</label>
					<div class='row-span' style="width: 100px;">
						<span >${model.creatorName}</span>
					</div>
					<!-- <div class='row-span'>
					</div> -->
				</div>
			</div>
			
			
		<div class="about4" style="margin-bottom: 40px; margin-left: 40px;width:100%;">
			<div class="about4_ch" style="margin-top: 60px; margin-bottom: 60px;">合同相关资金变动</div>
			<div class="about4_main"  style="margin-top: -25px;">
		
				<div class="line"></div>
				<ul>
					 <c:forEach var="log" items="${logs}" varStatus="status">
					 	<li>
					 		<fmt:formatDate  value="${log.opDate}" pattern="yyyy-MM-dd"/>，${log.opDesc}，金额：<fmt:formatNumber value="${log.opAmount}" type="currency" currencySymbol="￥"/>元
						</li>
					 </c:forEach>
				</ul>
			</div>
		</div>
		
		<div style="margin-bottom: 80px;">
		<div class=" row-inline" style="width: 200px;">
				<div class=''>
					<label class='row-label' for='contractName' >商务成本：</label>
					<div class='row-span' style="width: 130px;">
						<c:if test="${fundSales.businessCost!=null}">
							<fmt:formatNumber value="${fundSales.businessCost}" type="currency" currencySymbol="￥"/>
						 </c:if>
						 <c:if test="${fundSales.businessCost==null}">
							￥0.00
						 </c:if>
					</div>
				</div>
			</div>
			<div class=" row-inline" style="width: 200px;">
				<div class=''>
					<label class='row-label' for='contractName' >进项发票：</label>
					<div class='row-span' style="width: 130px;">
						<c:if test="${fundSales.incomeInvoice!=null}">
							<fmt:formatNumber value="${fundSales.incomeInvoice}" type="currency" currencySymbol="￥"/>
						 </c:if>
						 <c:if test="${fundSales.incomeInvoice==null}">
							￥0.00
						 </c:if>
					</div>
				</div>
			</div>
			<div class=" row-inline" style="width: 200px;">
				<div class=''>
					<label class='row-label' for='contractName' >出项发票：</label>
					<div class='row-span' style="width: 130px;">
						<c:if test="${fundSales.outInvoice!=null}">
							<fmt:formatNumber value="${fundSales.outInvoice}" type="currency" currencySymbol="￥"/>
						 </c:if>
						 <c:if test="${fundSales.outInvoice==null}">
							￥0.00
						 </c:if>
					</div>
				</div>
			</div>
			<div class=" row-inline" style="width: 200px;">
				<div class=''>
					<label class='row-label' for='contractName' >合同回款：</label>
					<div class='row-span' style="width: 130px;">
						 <c:if test="${fundSales.receiveAmount!=null}">
							<fmt:formatNumber value="${fundSales.receiveAmount}" type="currency" currencySymbol="￥"/>
						 </c:if>
						  <c:if test="${fundSales.receiveAmount==null}">
							￥0.00
						 </c:if>
					</div>
				</div>
			</div>
		</div>
		
		 <div style="margin-bottom: 20px;"></div>
			<div class="form-actions" style="margin-bottom: 20px;">
				<div style="text-align: center;">
					<button id='_sino_eoss_sales_contract_back' type="button" class="btn" onclick="window.close()"><i class="icon-remove"></i>关闭</button>
				</div>
			</div>
			
			</form>
			<!-- END FORM-->  
		</div>
</body>
</html>
