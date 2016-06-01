<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/global.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/ultra_select.css" type="text/css"></link>

<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formSelect.css" type="text/css"></link>
<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formstyle.css" type="text/css"></link>

<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/internetTable.css" type="text/css"></link>
<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/processList.css" type="text/css"></link>
<title>商务管理-我的订单</title>
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
	overflow:visible;
}
</style>
</head>
<body>

	<div id="uicTable"></div>
	<!-- <ul class="clearfix" style="text-align: right;width: 300px;float: right;">	
		<li  id="searchCon_flowName" class="advancedSearch_li uic_form_select" style="width: 290px;">
			<lable class="editableLabel" style="margin-left:10px;">总金额：</lable><span id="totallAmount"></span>
			<lable class="editableLabel"style="margin-left:50px;">页面金额：</lable><span id="pageAmount"></span>
		</li>
	</ul> -->
	<script language="javascript">
		seajs.use('js/page/business/order/manage', function(manage) {
			manage.init();
		});
	</script>
</body>
</html>