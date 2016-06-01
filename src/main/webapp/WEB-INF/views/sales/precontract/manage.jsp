<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/global.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet"	href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/ultra_select.css"	type="text/css"></link>
<link rel="stylesheet"href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formSelect.css"type="text/css"></link>
<link rel="stylesheet"href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formstyle.css"type="text/css"></link>
<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formTree.css" type="text/css"></link>

<link rel="stylesheet"href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/internetTable.css"type="text/css"></link>
<link rel="stylesheet"href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/processList.css"type="text/css"></link>

<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/user-selection.css" type="text/css"></link>
<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/dialog.css" type="text/css"></link>
<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/tip.css" type="text/css" ></link>
<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formSearchTip.css" type="text/css" ></link>


<title>合同智能检索</title>
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
</style>
</head>
<body>
	<div id="_sino_eoss_contract_import_div"></div>
	<div id="uicTable"></div>
	<ul class="clearfix" style="text-align: right;width: 350px;float: right;">	
		<li  id="searchCon_flowName" class="advancedSearch_li uic_form_select" style="width: 350px;">
			<lable class="editableLabel" style="margin-left:10px;">总金额：<span id="totallAmount">￥0</span>元</lable>
			<lable class="editableLabel"style="margin-left:10px;">页面金额：<span id="pageAmount">￥0</span>元</lable>
		</li>
	</ul>
	<script language="javascript">
		seajs.use('js/page/sales/precontract/manage', function(manage) {
			manage.init();
		});
	</script>
</body>
</html>