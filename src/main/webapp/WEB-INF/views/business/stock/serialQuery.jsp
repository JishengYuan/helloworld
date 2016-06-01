<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.sinobridge.systemmanage.util.Global"%> 
<%@ page import="com.sinobridge.systemmanage.vo.SystemUser"%> 
<html>
<head>
	<title></title>
</head>
<body>
	<div>
		<h2>序列号管理</h2>
		<table border="0" >
			<tr>
				<td>订单编号：</td><td><input type="text" id="orderCode" /></td>
				<td>合同编号：</td><td><input type="text" id="contractCode" /></td>
				<td>入库单号：</td><td><input type="text" id="inboundCode" /></td>
			</tr>
			<tr>
				<td colspan="6" align="right">
					<a class="btn btn-primary pull-right" id="querySerial" >查询</a>
				</td>
			</tr>
		</table>
		<table align="center" id="taskTable" cellpadding="0" cellspacing="0" border="0" class="table table-striped table-bordered table-hover">
			<thead>
				<tr>
					<td width="20%">订单编号</td>
					<td width="20%">合同编号</td>
					<td width="30%">入库单号</td>
					<td width="20%">序列号</td>
					<td width="10%">操作选项</td>
				</tr>
			</thead>
			<tbody>
			
			</tbody>
		</table>
	</div>
</body>
<script language="javascript">
	seajs.use("js/page/business/stock/serialManage", function(serial) {
		serial.init();
	});
</script>
</html>