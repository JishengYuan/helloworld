<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/global.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="${ctx}/skin/default/room.css" type="text/css" />
<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/ultra_select.css" type="text/css"></link>

<link rel="stylesheet" href="${ctx}/skin/default/room.css" type="text/css" />
<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formSelect.css" type="text/css"></link>
<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formstyle.css" type="text/css"></link>

<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/internetTable.css" type="text/css"></link>
<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/processList.css" type="text/css"></link>

<title>商务管理</title>
<style type="text/css">
ul,ol {
	margin: 0;
}

div span a {
    color: #005EA7;
    text-decoration: none;
}

.ultrapower-table-box .operation {
    border-bottom: 1px solid #ccc;
    height: 10px;
    position: relative;
    text-align: left;
    width: 100%;
    z-index: 200;
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

ul,ol {
	margin: 0;
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

  <div class="ultrapower-table-box">
	    	<span class="title" style="font-size:14px; font-weight: normal;font-family:宋体;">商务采购-->订单变更审批&供方信息审批</span>
  </div>
  
  <div id="costControl" class="rightmenu mb10 clearfix">
		<ul>
            	<li id='normalContract' class="sel"><a href="javascript:void(0)">订单变更审批</a></li>
            	<li id='relationContract' class="button"><a href="javascript:void(0)">供方信息审批</a></li>
        </ul>
	</div>  
	<div id="allClean">
		<div id="uicTable"></div>
		<div id="uicTable2"></div>
	</div>
  
  
	<div id="costControl" class="rightmenu mb10 clearfix">
	<div id="uicTable"></div>
	<script language="javascript">
		seajs.use('js/page/business/changeOrder/manage', function(manage) {
			manage.init();
		});
	</script>
</body>
</html>