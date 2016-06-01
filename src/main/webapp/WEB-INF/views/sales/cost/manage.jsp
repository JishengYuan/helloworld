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
<link rel="stylesheet"	href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/ultra_select.css"	type="text/css"></link>




<title>资金占用成本</title>
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
	    	<span class="title" style="font-size:14px; font-weight: normal;font-family:宋体;">资金成本占用</span>
	    </div>
	<div id="costControl" class="rightmenu mb10 clearfix">
		<ul>
            	<li id='normalContract' class="sel"><a href="javascript:void(0)">普通合同</a></li>
            	<li id='relationContract' class="button"><a href="javascript:void(0)">关联备货的合同</a></li>
        </ul>
	</div>  
	<div id="allClean">
		<div id="uicTable"></div>
		<div id="uicTable2"></div>
	</div>
	
	<!-- editTable -->
       <div id="dailogs1" class="modal hide fade" role="dialog" tabIndex="-1" style="width:580px;height: 210px;">
		   <div class="modal-header">
		         <a type="button" class="close" data-dismiss="modal" aria-hidden="true">×</a>
		         <h3 id="dtitle"></h3>
  		   </div>
		    <div class="modal-body" style="width:750px;">
			     <div id="dialogbody" ></div>
		    </div>
		    <div id="bottom_button" class="modal-footer" style="text-align: center;">
        		<input id="ok_Contract_Add" type="button" value="确定" class="btn btn-success"/>
    		</div>
        </div>
	<script language="javascript">
		seajs.use('js/page/sales/cost/manage', function(manage) {
			manage.init();
		});
	</script>
</body>
</html>