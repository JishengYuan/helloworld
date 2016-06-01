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
		<div id="search">
			<ul class="clearfix">
				<li class="advancedSearch_li"  style="height: 30px;margin-top: -8px;width:350px;">
					<label class="editableLabel"  style="width:70px;">时间：</label>
					<div id="dateTimeStopDate" class="input-append date" style="margin-top: 5px;">
			  		    <input data-format="yyyy-MM-dd" style="width:180px;margin-left: -6px;" type="text" id="add_time" value="${time}"></input>
					    <span class="add-on">
					      <i data-time-icon="icon-time" data-date-icon="icon-calendar">
					      </i>
						</span>
					  </div>
				</li>
			</ul>	
		</div>
    		
 <script language="javascript">
    seajs.use('js/page/sales/cost/selectTime',function(selectTime){
    	selectTime.init();
    });
</script>
</body>
</html>