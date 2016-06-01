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


<title>合同管理</title>
<style type="text/css">
.ultrapower-table-box .operation{
height: 0px;
}
.busibtn{
  margin:5px;
}

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
<div id="costControl" class="rightmenu mb10 clearfix">
		<ul>
            	<li id='busicoststock' class="sel"><a href="javascript:void(0)">关联备货成本确认</a></li>
            	<li id='busicostid'  class="button"><a href="javascript:void(0)">商务成本确认</a></li>
        </ul>
</div>
<!-- <a class="btn btn-primary busibtn" id="busicostid">商务成本确认</a><a class="btn btn-primary busibtn" id="busicostid">关联备货成本确认</a> -->
	<div id="uicTable"></div>
	<script language="javascript">
		seajs.use('js/page/sales/stockUpCost/manage', function(manage) {
			manage.init();
		});
	</script>
</body>
</html>