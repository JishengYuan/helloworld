<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/global.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet"	href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formSelect.css"	type="text/css"></link>
<link rel="stylesheet"	href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formstyle.css"	type="text/css"></link>

<link rel="stylesheet"	href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/internetTable.css"	type="text/css"></link>
<link rel="stylesheet"	href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/processList.css"	type="text/css"></link>

<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/user-selection.css" type="text/css"></link>
<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/dialog.css" type="text/css"></link>
<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/tip.css" type="text/css" ></link>
<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formSearchTip.css" type="text/css" ></link>

<title>合同发票查询</title>
<style type="text/css">
.ultrapower-table-box .operation {
    border-bottom: 0px solid #ccc;
    height: 0px;
    position: relative;
    text-align: left;
    width: 100%;
    z-index: 200;
}
.ultrapower-table-box .showbody {
    height: auto;
    overflow: visible;
    position: relative;
    text-align: center;
    z-index: 100;
}
</style>
</style>
</head>
<body>

	<div id="uicTable"></div>
	<script language="javascript">
		seajs.use('js/page/sales/invoice/manage', function(manage) {
			manage.init();
		});
	</script>
</body>
</html>