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


<title>合同成本核算管理</title>

</head>
<body>
   	   <div id="alertMsg"></div>
	  <div id="uicTable"></div>
	  <div id="dailogs1" class="modal hide fade" role="dialog" tabIndex="-1">
		<div class="modal-header">
			<button class="close" type="button" data-dismiss="modal">×</button>
			<h3 id="dtitle">修改数据</h3>
		</div>
		<div class="modal-body">
			<div id="dialogbody" ></div>
		</div>
		<div class="modal-footer">
		<button class="btn" data-dismiss="modal" aria-hidden="true">取消</button >
			<button id="dsave"  class="btn btn-primary" >保存</button>
		</div>
	</div>
	<script language="javascript">
		seajs.use('js/page/sales/funds/manage', function(manage) {
			manage.init();
		});
	</script>
</body>
</html>