<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/global.jsp"%>
<html>
<head>
<title>待处理的商务报销付款</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="${ctx}/skin/default/room.css" type="text/css" />

<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/internetTable.css" type="text/css"></link>
<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/processList.css" type="text/css"></link>

<style type="text/css">
.mb10 {
    margin-top:5px;
    margin-bottom: 10px;
}
.rightmenu li {
font-size: 12px;
    float: left;
    line-height: 25px;
    margin: 0 20px 0 0;
}
.rightmenu li a {
    padding: 2px 10px;
}
.rightmenu li a:hover {
    background: none repeat scroll 0 0 #4D7DCF;
    color: #FFFFFF;
}
.rightmenu li.sel a {
    background: none repeat scroll 0 0 #4D7DCF;
    border: 2px solid #4D7DCF;
    color: #FFFFFF;
}
.blue02{
color:#1874CD;
}

</style>
</head>
<body>

  <div class="ultrapower-table-box">
	    	<span class="title" style="font-size:14px; font-weight: normal;font-family:宋体;">财务管理-->待处理的商务单</span>
	     </div>
		<div id="costControl" class="rightmenu mb10 clearfix">
			<ul>
	            	<li id='payment' class="sel"><a href="javascript:void(0)">待处理的付款单</a></li>
	            	<li id='reimburse' class="button"><a href="javascript:void(0)">待处理的报销单</a></li>
	        </ul>
		</div>
			<div id ="uicTable"></div>
			<div id ="uicTable2"></div>

	<script language="javascript">
		seajs.use('js/page/business/costTask/costTaskManage', function(costTaskManage) {
			costTaskManage.init();
		});
	</script>
</body>
</html>