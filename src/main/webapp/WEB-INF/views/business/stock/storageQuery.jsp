<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.sinobridge.systemmanage.util.Global"%> 
<%@ page import="com.sinobridge.systemmanage.vo.SystemUser"%> 
<%@ include file="/common/global.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
		<link rel="stylesheet"href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/internetTable.css"type="text/css"></link>
		<link rel="stylesheet"href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/processList.css"type="text/css"></link>
		<link rel="stylesheet" href="${ctx}/skin/default/room.css" type="text/css" />
		<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formSelect.css" type="text/css"></link>
		<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formTree.css" type="text/css"></link>
		<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/tip.css" type="text/css" ></link>
		<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/user-selection.css" type="text/css"></link>
		<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formSearchTip.css" type="text/css" ></link>
		<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formstyle.css" type="text/css"></link>
		<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/dialog.css" type="text/css"></link>
	</head>
	<style type="text/css">
		ul,ol {
			margin: 0;
		}
		
		.ultrapower-table-box .operation {
		    border-bottom: 0px solid #ccc;
		    height: 0px;
		    position: relative;
		    text-align: left;
		    width: 100%;
		    z-index: 200;
		}
		.showbody{
			min-height:500px;
		}
	</style>
<body>
	<!-- <h2>库存查询</h2>
		<table border="0">
			<tr>
				<td><label class="control-label" for="menuName">厂商名称：</label></td>
				<td>
					<div class="control-group">
						<div id="_vendor"></div>
					</div>
					<input type="hidden" id="vendor" name="vendor" />
				</td>
				<td>订单编号：</td><td><input type="text" id="orderCode" name="orderCode" /></td>
				<td>合同编号：</td><td><input type="text" id="contractCode" name="contractCode" /></td>
			</tr>
			<tr>
				<td><label class="control-label" for="menuName">库存状态：</label></td>
				<td>
					<div class="control-group">
						<div id="_stockState"></div>
						<input type="hidden" id="stockState" name="stockState" />
					</div>
				</td>
				<td>入库单号：</td><td><input type="text" id="inboundCode" name="inboundCode" /></td>
				<td>出库单号：</td><td><input type="text" id="outboundCode" name="outboundCode" /></td>
			</tr>
			<tr>
				<td colspan="6" align="right">
					<a class="btn btn-primary pull-right" id="queryStock" >查询</a>
				</td>
			</tr>
		</table> -->
		
		<div id="uicTable"></div>
		
</body>
<script language="javascript">
	seajs.use("js/page/business/stock/storageQuery", function(result) {
		result.init();
	});
</script>
</html>