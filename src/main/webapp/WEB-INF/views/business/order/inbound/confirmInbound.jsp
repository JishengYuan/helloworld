<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/common/global.jsp"%>
<title>订单到货确认</title>
<style type="text/css">
tr.group, tr.group:hover {
    background-color: #ddd !important;
}
.dataTables_length{
	margin-top: 10px; 
	padding-bottom: 40px;
}
</style>
</head>
<body>
	<div class="ultrapower-table-box">
		<strong class="title" style = "font-size:14px; font-weight: normal;font-family:宋体;display: block;">商务采购-->订单到货确认</strong>
	</div>
	<div id="_sino_eoss_order_products_relate_page_div"></div>
	<div class="">
		<table align="center" id="taskTable" cellpadding="0" cellspacing="0" border="0" class="table table-bordered table-hover">
			<thead>
				<tr>
					<td>入库单号</td>
					<td>产品型号</td>
					<td>数量</td>
					<td>所在库房</td>
					<td>到货时间</td>
				</tr>
			</thead>
		</table>
		<table align="center" >
			<tbody>
				
			</tbody>
		</table>
	</div>
</body>
<script language="javascript">
		seajs.use("js/page/business/order/inbound/confirmInbound", function(confirmInbound) {
			confirmInbound.init();
		});
</script>
</html>