<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/global.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link rel="stylesheet"
	href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/ultra_select.css"
	type="text/css"></link>

<link rel="stylesheet"
	href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formSelect.css"
	type="text/css"></link>
<link rel="stylesheet"
	href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formstyle.css"
	type="text/css"></link>

<link rel="stylesheet"
	href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/internetTable.css"
	type="text/css"></link>
<link rel="stylesheet"
	href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/processList.css"
	type="text/css"></link>

<title>新闻发布</title>
<style type="text/css">
ul,ol {
	margin: 0;
}

div span a {
    color: #005EA7;
    text-decoration: none;
}
.ultrapower-table-box .showbody {
    height: auto;
    overflow: visible;
    position: relative;
    text-align: center;
    z-index: 100;
}
</style>
</head>
<body>
	<div id="_eoss_add_returnSpot_div"></div>
	<a id='_eoss_add_returnSpot_a' supplierId="" href='#_eoss_add_returnSpot_page' role='button' target='_self'  data-toggle='modal' class='btn btn-primary' style='display:none'>添加</a>
	<div id="uicTable"></div>
	<script language="javascript">
		seajs.use('js/page/sales/contractMerge/manage', function(manage) {
			manage.init();
		});
	</script>
</body>
</html>