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
<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/user-selection.css" type="text/css"></link>
<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/dialog.css" type="text/css"></link>
<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/tip.css" type="text/css" ></link>
<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formSearchTip.css" type="text/css" ></link>

<title>供方管理</title>
<style type="text/css">
ul,ol {
	margin: 0;
}

div span a {
    color: #005EA7;
    text-decoration: none;
}
.ultrapower-table-box .title {
    font-size: 18px;
}
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
</style>
</head>
<body>
	<a class="btn btn-success" aria-hidden="true" data-toggle="modal" target="_self" href="#_sino_eoss_inventory_import_page" role="button" id="_sino_eoss_inventory_import" style="display:none;"><i class="icon-table"></i>导入</a>
	<div id="_sino_eoss_inventory_products_import_div"></div>
	<!-- <div>
	  <span class="title _fee_head_title" style="float:left;width:100%;margin-bottom:10px;margin-top:10px;"><h5>公司备件->出库入库审批</h5></span>
	</div> -->
	<span class="title _fee_head_title" style="float:left;width:100%;margin-bottom:10px;margin-top:20px;"><h5>公司备件->出库入库审批</h5></span>
	
	<div class="showbody" style="width: 100%; height: 110px;">
		<div class="advancedSearch" style="">
			<div id="allQuery_search_div">
				<ul class="clearfix">
					<li class="advancedSearch_li"><label
						for="key" class="editableLabel" style="margin-left:0px;float:left;">申请人：</label>
						<div id="search_creator" style="margin-left:0px;float:left;"></div>   
					</li>
					<li class="advancedSearch_li" style="float:right;">
						<a href="#" class="btn btn-primary" id="advancedSearch_btn" style="font-size:12px;float:right;"><i class="icon-search"></i>&nbsp;查询</a>
					</li>
				</ul>
			</div>
		</div>
	</div>
	
	<div id="uicTable" style="float:left;width:100%;"></div>
	<script language="javascript">
		seajs.use('js/page/business/inventory/approveManage', function(approveManage) {
			approveManage.init();
		});
	</script>
</body>
</html>