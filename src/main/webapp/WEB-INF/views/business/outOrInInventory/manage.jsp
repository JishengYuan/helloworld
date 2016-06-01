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
	
	
	

<title>出入库管理</title>
<style type="text/css">
ul,ol {
	margin: 0;
}

div span a {
    color: #005EA7;
    text-decoration: none;
}
/* .ultrapower-table-box .title { */
/*     font-size: 18px; */
/* } */
.selectDiv .uicSelectInp {
    background: none repeat scroll 0 0 #FFFFFF;
    border: 1px solid #CCCCCC;
    cursor: pointer;
    height: 13px;
    position: relative;
    text-align: left;
    vertical-align: middle;
}
.selectDiv a.uicSelectMore {
    background: url("${ctx}/js/plugins/uic/style/excellenceblue/uic/images/ultraselect/ms_ico2.png") no-repeat scroll 1px 0 rgba(0, 0, 0, 0);
    display: block;
    height: 23px;
    position: absolute;
    right: 0;
    top: 0;
    width: 25px;
    margin-right:-3px;
}
.ultrapower-table-box .showbody {
    height: auto;
    overflow: visible;
    position: relative;
    text-align: center;
    z-index: 100;
}

._fee_head{
	background:none repeat scroll 0 0 #f1f1f1;
	height:30px;
	margin-top:10px;
	padding-top:8px;
}
._fee_head_title{
	color:#4d7dcf;
}
.title {
    background: none repeat scroll 0 0 #fefefe;
    border-bottom: 2px solid #4d7dcf;
    height: 30px;
    line-height: 30px;
    text-indent:10px;
    font-weight:normal;
    width:50%;
}
.rightmenu li {
    float: left;
    font-size: 12px;
    line-height: 25px;
    margin: 0 20px 0 0;
}
.rightmenu li a {
    padding: 2px 10px;
}
.rightmenu li a:hover {
    background: none repeat scroll 0 0 #4d7dcf;
    color: #ffffff;
}
.rightmenu li.sel a {
    background: none repeat scroll 0 0 #4d7dcf;
    border: 2px solid #4d7dcf;
    color: #ffffff;
}

.ultrapower-table-box .title {
	background: none repeat scroll 0 0 #fefefe;
	border-bottom: 2px solid #1f9dd7;
	height: 30px;
	line-height: 30px;
	display: block;
}

</style>
</head>
<body>
	<div style="height:45px;" class="ultrapower-table-box">
		<strong class="title">公司备件-->出入库管理</strong>
	</div> 
<!-- 	<div id="_sino_eoss_inventory_products_import_div"></div> -->
	<div class="rightmenu mb10 clearfix" id="costControl" style="margin-bottom:5px;">
			<ul>	
			<table border="1px" width="100%">
				<tr>
					<td width="88%;"><li class="sel" id="outInfo"><a id="_outInfo" href="javascript:void(0)">出库信息</a></li>
	            		<li class="button" id="inInfo"><a id="_inInfo" href="javascript:void(0)">归还信息</a></li></td>
					<td><!-- <a id="_outStorage" href="#myModal1" class="btn btn-success"><i class="icon-plus"></i>&nbsp;出库申请</a>  -->
						<c:if test="${loginUser=='liub' }">
	            			<a id="_inStorage" href="#myModal1" style="float: right;" class="btn btn-success"><i class="icon-plus"></i>&nbsp;入库归还</a> </td>
						</c:if>
				</tr>
			</table>
 			
	        </ul>
			</div>
	<div id="uicTable"></div>
	<div id="uicTable1" style="display:none;"></div>
	
	<script language="javascript">
		seajs.use('js/page/business/outOrInInventory/manage', function(manage) {
			manage.init();
		});
	</script>
</body>
</html>