<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/global.jsp"%>

<html>
<head>
<title>项目管理</title>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<link rel="stylesheet"
	href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formTree.css"
	type="text/css"></link>
<link rel="stylesheet"
	href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/tip.css"
	type="text/css"></link>
<link rel="stylesheet"
	href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/user-selection.css"
	type="text/css"></link>
<link rel="stylesheet"
	href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formSearchTip.css"
	type="text/css"></link>
<link rel="stylesheet"
	href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/dialog.css"
	type="text/css"></link>
</head>

<body>

	<div class="modal-header">
		<a type="button" class="close" data-dismiss="modal" aria-hidden="true">×</a>
		<h3 id="myModalLabel">请选择合同</h3>
	</div>
	<div class="modal-body" style="overflow: auto; height: 320px;">
		<table id="taskTable" width="98%" cellpadding="0" cellspacing="0"
			border="0" class="table table-striped table-bordered table-hover">
			<thead>
				<tr>
					<th width="20%">选择</th>
					<th width="40%">销售合同编号</th>
					<th width="40%">销售合同名称</th>
				</tr>
			</thead>
		</table>
		
	</div>
	<div class="modal-footer">
		<a class="btn btn-primary" id="_sales_contracts_relate" data-dismiss="modal" aria-hidden="true">确定</a>
		<a class="btn" data-dismiss="modal" aria-hidden="true">关闭</a>
	</div>
	<script language="javascript">
		seajs.use("js/page/business/stock/outStorageOption", function(result) {
			result.init();
		});
	</script>
</body>
</html>