<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.sinobridge.systemmanage.util.Global"%> 
<%@ page import="com.sinobridge.systemmanage.vo.SystemUser"%> 
<%@ include file="/common/global.jsp"%>
<html>
	<head>
		<title>详细信息</title>
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
		<script src="${ctx}/js/plugins/seajs/sea.js" id="seajsnode" type="text/javascript"></script>
		<script src="${ctx}/js/plugins/seajs/config.js" type="text/javascript"></script>
	</head>
<body>
	<div class="portlet-body form">
		<!-- BEGIN FORM-->
		<form id="dealCaseForm" name="dealCaseForm" class="form-horizontal">
			<table align="center" id="stockDetailTable" cellpadding="0" cellspacing="0" border="0" class="table table-striped table-bordered table-hover">
				<tr>
					<td colspan="6">
						<h4>详细信息</h4>
					</td>
				</tr>
				<tr>
					<td width="10%">订单编号</td>
					<td width="22%">${model.orderCode}</td>
					<td width="10%">合同编号</td>
					<td width="22%">${model.contractCode}</td>
					<td width="10%">入库时间</td>
					<td width="22%">${model.inboundTime}</td>
				</tr>
				<tr>
					<td>入库单号</td>
					<td>${model.inboundCode}</td>
					<td>出库时间</td>
					<td>${model.outboundTime}</td>
					<td>出库单号</td>
					<td>${model.outboundCode}</td>
				</tr>
				<tr>
					<td>批次</td>
					<td>${model.pono}</td>
					<td>收货人</td>
					<td>${model.recipientName}</td>
					<td>厂商名称</td>
					<td>${model.vendor}</td>
				</tr>
				<tr>
					<td>产品名称</td>
					<td>${model.productCode}</td>
					<td>型号</td>
					<td>${model.model}</td>
					<td>产品描述</td>
					<td>${model.productDescribe}</td>
				</tr>
				<tr>
					<td>库存状态</td>
					<c:if test="${model.stockState==0}">
						<td>已导入</td>
					</c:if>
					<c:if test="${model.stockState==1}">
						<td>已入库</td>
					</c:if>
					<c:if test="${model.stockState==2}">
						<td>已出库</td>
					</c:if>
					<c:if test="${model.stockState==3}">
						<td>借出</td>
					</c:if>
					<c:if test="${model.stockState==4}">
						<td>归还</td>
					</c:if>
					<td>库存数量</td>
					<td>${model.stockNum}</td>
					<td>存放地点</td>
					<td>${model.storePlace}</td>
				</tr>
				<tr>
					<td>备注</td>
					<td colspan="5">${model.note}</td>
				</tr>
				<tr>
					<td colspan="6">
						<a id="_back"  class="btn btn-primary" data-dismiss="modal" aria-hidden="true" >返回</a>
					</td>
				</tr>
			</table>
		</form>
		<!-- END FORM-->  
	</div>
</body>
<script language="javascript">
	seajs.use("js/page/business/stock/storageQuery", function(result) {
		result.init();
	});
</script>
</html>