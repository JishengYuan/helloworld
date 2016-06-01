<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/global.jsp"%>

<html>
	<head>
		<title>商务管理</title>
		<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
		<link rel="stylesheet" href="${ctx}/skin/default/room.css" type="text/css" />
		<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formSelect.css" type="text/css"></link>
		<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formTree.css" type="text/css"></link>
		<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/tip.css" type="text/css" ></link>
		<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/user-selection.css" type="text/css"></link>
		<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formSearchTip.css" type="text/css" ></link>
		<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formstyle.css" type="text/css"></link>
		<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/dialog.css" type="text/css"></link>
		<link rel="stylesheet" href="${ctx}/js/plugins/bootstrap/css/bootstrap-datetimepicker.min.css" type="text/css"></link>

</head>

<body>       
	<input type="hidden" id='id' name='id' value="${model.id}" />
		<div id="search">
			<ul class="clearfix">
				<li class="advancedSearch_li"  style="height: 30px;margin-top: -8px;width:350px;">
					<label class="editableLabel"  style="width:70px;">租金：</label>
					<input type="text" id='rent' name='rent' value="${model.rent}" />
				</li>
			</ul>	
		</div>
    		
 <script language="javascript">
    seajs.use('js/page/business/inventory/updateRent',function(updateRent){
    	updateRent.init();
    });
</script>
</body>
</html>