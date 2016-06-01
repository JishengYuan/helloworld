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

<link rel="stylesheet"href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/internetTable.css"type="text/css"></link>
<link rel="stylesheet"href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/processList.css"type="text/css"></link>


<title>我的投标</title>
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

	<div id="uicTable"></div>
	<script language="javascript">
		seajs.use('js/page/sales/contractFound/manage', function(manage) {
			manage.init();
		});
	</script>
</body>
</html>