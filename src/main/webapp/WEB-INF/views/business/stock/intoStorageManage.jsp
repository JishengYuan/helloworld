<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.sinobridge.systemmanage.util.Global"%> 
<%@ page import="com.sinobridge.systemmanage.vo.SystemUser"%> 
<%@ include file="/common/global.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
		<link rel="stylesheet" href="${ctx}/skin/default/room.css" type="text/css" />
		<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formSelect.css" type="text/css"></link>
		<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formTree.css" type="text/css"></link>
		<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/tip.css" type="text/css" ></link>
		<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/user-selection.css" type="text/css"></link>
		<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formSearchTip.css" type="text/css" ></link>
		<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formstyle.css" type="text/css"></link>
		<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/dialog.css" type="text/css"></link>
	</head>
<body>
	<h2>入库管理</h2>
	 <div id="alertMsg"></div> 
		<table align="center" cellpadding="0" cellspacing="0" border="0">
			<tr>
				<!--required data-validation-required-message="请输入出库单号！"-->
				<td>订单编号：</td><td><input type="text" id="orderCode"/></td>
				<td>合同编号：</td><td><input type="text" id="contractCode"/>&nbsp;&nbsp;</td>
				<td colspan="4" align="right">
					<a class="btn btn-primary pull-right" id="queryInbound" >查询</a>
				</td>
			</tr>
<!-- 			<tr> -->
<!-- 				<td>开始时间：</td> -->
<!-- 				<td> -->
<!-- 					<div id="dateTimeStopDate" class="input-append date"> -->
<%-- 						<input data-format="yyyy-MM-dd" style="width:180px;" type="text" id="timeA" name="timeA" value="${model.stopDate }"></input> --%>
<!-- 						<span class="add-on"> -->
<!-- 							<i data-time-icon="icon-time" data-date-icon="icon-calendar"></i> -->
<!-- 						</span> -->
<!-- 					</div> -->
<!-- 				</td> -->
<!-- 				<td>结束时间：</td> -->
<!-- 				<td> -->
<!-- 					<div id="dateTimeStopDate" class="input-append date"> -->
<%-- 						<input data-format="yyyy-MM-dd" style="width:180px;" type="text" id="timeB" name="timeB" value="${model.stopDate }"></input> --%>
<!-- 						<span class="add-on"> -->
<!-- 							<i data-time-icon="icon-time" data-date-icon="icon-calendar"></i> -->
<!-- 						</span> -->
<!-- 					</div> -->
<!-- 				</td> -->
<!-- 			</tr> -->
<!-- 			<tr> -->
<!-- 				<td colspan="4" align="right"><input type="button" value="查询" class="btn btn-primary pull-right" id="queryInbound" ></td> -->
<!-- 			</tr> -->
		</table>
		<table align="center" id="taskTable" cellpadding="0" cellspacing="0" border="0" class="table table-striped table-bordered table-hover">
			<thead>
				<tr>
					<td width="15%">入库单号</td>
					<td width="11%">订单编号</td>
					<td width="15%">合同编号</td>
					<td width="10%">产品名称</td>
					<td width="10%">产品型号</td>
					<td width="5%">数量</td>
					<td width="8%">入库状态</td>
					<td width="10%">入库时间</td>
					<td width="8%">收货人</td>
					<td width="8%">操作选项</td>
				</tr>
			</thead>
			<tbody>
			
			</tbody>
		</table>
</body>
<script language="javascript">
	seajs.use("js/page/business/stock/intoStorageManage", function(result) {
		result.init();
	});
</script>
</html>