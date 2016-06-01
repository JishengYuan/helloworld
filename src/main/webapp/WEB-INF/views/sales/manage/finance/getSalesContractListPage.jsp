<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/global.jsp"%>
<%@ page import="com.sinobridge.systemmanage.util.Global"%> 
<%@ page import="com.sinobridge.systemmanage.vo.SystemUser"%> 
<html>
<head>
<title>合同标示</title>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
	<%@ include file="/common/include-base-boostrap-styles.jsp" %>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/sinobridge/pink/sino_form_grid.css" type="text/css" />
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formSelect.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formstyle.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/dialog.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/tip.css" type="text/css" ></link>
	<link rel="stylesheet" href="${ctx}/skin/default/editgrid/editgrid.css" type="text/css" />
	<link type="text/css" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/jquery-ui-1.8.17.custom.css" rel="stylesheet">		
<style type="text/css">
.bt5left{
	margin-left:30px;
}
.table th, .table td {
    border-top: 1px solid #dddddd;
    line-height: 21px;
    padding: 15px;
    text-align: left;
    vertical-align: top;
}
</style>
<script src="${ctx}/js/plugins/seajs/sea.js" id="seajsnode" type="text/javascript"></script>
	<script src="${ctx}/js/plugins/seajs/config.js" type="text/javascript"></script>
	<link href="${ctx}/skin/default/sales/sale.css" rel="stylesheet"/>
</head>

<body>
<div class="salecontent">
		<div class="top">
			<div class="caption">
				<i class="icon-reorder"></i>&nbsp;&nbsp;&nbsp;修改合同信息
			</div>
			<div class="tools">
				<!--a href="javascript:;" class="reload"><i class="icon-repeat"></i>返回</a-->
			</div>
		</div>
	</div>
		<div class="portlet-body form">
			<!-- BEGIN FORM-->
		<!-- <table id="taskTable" width="98%" cellpadding="0" cellspacing="0"
			border="0" class="table table-striped table-bordered table-hover">
			<thead>
				<tr>
					<th width="10%">选择</th>
					<th width="30%">合同编号</th>
					<th width="40%">合同名称</th>
					<th width="40%">合同金额</th>
				</tr>
			</thead>
		</table> -->
		<form id="_eoss_sales_finance_contracts" method="post" name="_eoss_sales_finance_contracts" class='form-horizontal'>
		<input type="hidden" name="id" value="${model.id }">
		<input type="hidden" name="orderid" value="${order.id }">
		<br>
		<br>
		<table border="0" style="width: 98%;margin-left:5px;margin-top:5px;" class="sino_table_static  table table-bordered" id="_fee_travel">
			<tbody>
				<tr>
					<td class="sino_table_label">合同名</td>
					<td colspan="3" class="sino_table_label">${order.orderName }</td>
				</tr>
				<tr>
					<td class="sino_table_label">合同号</td>
					<td colspan="3" id="tr_orderCode" class="sino_table_label">${order.orderCode }</td>
				</tr>
				<tr>
					<td class="sino_table_label">合同金额</td>
					<td class="sino_table_label" colspan="3" id="tr_orderAmount">${order.orderAmount }</td>
				</tr>
				<tr>
					<td class="sino_table_label">已付款金额</td>
					<td class="sino_table_label"><input type="text" style="width:90%;height:15px;" name="receiveAmount" value="${model.receiveAmount }" /></td>
					<td class="sino_table_label">未付款金额</td>
					<td class="sino_table_label"><input type="text" style="width:90%;height:15px;" name="unreceiveAmount" value="${model.unreceiveAmount }" /></td>
				</tr>
				<tr>
					<td class="sino_table_label">认证金额</td>
					<td class="sino_table_label"><input type="text" style="width:90%;height:15px;" name="invoiceAmount" value="${model.invoiceAmount }" /></td>
					<td class="sino_table_label">未认证金额</td>
					<td class="sino_table_label"><input type="text" style="width:90%;height:15px;" name="uninvoiceAmount" value="${model.uninvoiceAmount }" /></td>
				</tr>
			</tbody>
		</table>
		</form>
	</div>
	<div class="modal-footer">
	<div style='text-align:center;'>
		<a class="btn btn-primary" id="_sales_contracts_OK" data-dismiss="modal" aria-hidden="true">确定</a>
		<a class="btn" id="backUp" data-dismiss="modal" aria-hidden="true">关闭</a>
	</div>
	</div>

	<script language="javascript">
		seajs.use( 'js/page/sales/manage/finance/getSalesContractListPage',function(getSalesContractListPage) {
					getSalesContractListPage.init();
			});
	</script>
</body>
</html>