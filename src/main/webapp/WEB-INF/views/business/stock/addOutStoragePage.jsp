<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.sinobridge.systemmanage.util.Global"%>
<%@ page import="com.sinobridge.systemmanage.vo.SystemUser"%>
<%@ include file="/common/global.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<link rel="stylesheet" href="${ctx}/skin/default/room.css"
	type="text/css" />
<link rel="stylesheet"
	href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formSelect.css"
	type="text/css"></link>
<link rel="stylesheet"
	href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formTree.css"
	type="text/css"></link>
<link rel="stylesheet"
	href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/tip.css"
	type="text/css"></link>
<link rel="stylesheet"
	href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/user-selection.css"
	type="text/css"></link>
<link rel="stylesheet"
	href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formSearchTip.css"
	type="text/css"></link>
<link rel="stylesheet"
	href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formstyle.css"
	type="text/css"></link>
<link rel="stylesheet"
	href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/dialog.css"
	type="text/css"></link>
	
<link rel="stylesheet"href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/internetTable.css"type="text/css"></link>
<link rel="stylesheet"href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/processList.css"type="text/css"></link>
</head>
<style type="text/css">
.bt5left {
}

/* div.dataTables_paginate {
    float: right;
	margin:1px;
}
.bt5right{
	margin-top:-40px;
	margin-right:10px;
} */
.ultrapower-table-box .operation {
	border-bottom: 0px solid #ccc;
	height: 30px;
	position: relative;
	text-align: left;
	width: 100%;
	z-index: 200;
}

.advancedSearch li.advancedSearch_li label {
	display: inline-block;
	line-height: 18px;
	text-align: right;
	vertical-align: middle;
	width: 100px;
}

p,ul,ol,li,dl,dd,dt,form {
	list-style: none outside none;
}

.advancedSearcha{ padding:15px 0}

.advancedSearcha li.advancedSearch_li{ padding:3px 0; width:45%; float:left; font-size:12px}

.advancedSearcha li.advancedSearch_li label{ width:80px; text-align:right; display:inline-block; *+float:left; vertical-align:middle; line-height:18px}

.advancedSearcha li.advancedSearch_li input.InpText{*+float:left}

.advancedSearcha li.advancedSearch_li div.selectDiv{*+float:left;}

.advancedSearcha li.advancedSearch_li a.uicSelectMore,.advancedSearch li.advancedSearch_li a.calendar{right:0!important;}

.advancedSearcha .advancedSearch_btn{ height:30px; margin-top:10px; padding-top:10px; border-top:1px dashed #CCC;}

.advancedSearcha .advancedSearch_btn input.submit{ border:0; background:url(../images/button/btn_search.png) no-repeat; width:133px; height:30px; line-height:30px;}

.advancedSearcha {
	padding: 10px 0;
}

.advancedSearcha .advancedSearch_btn {
	border-top: 0px dashed #ccc;
	height: 30px;
	margin-top: 10px;
	padding-top: 10px;
}

.advancedSearcha li {
	width: 250px;
	float: left;
}

.input-append .add-on,.input-prepend .add-on {
	height: 15px;
}
</style>
<body>
	
	<form id="_out_bound_form" name="_out_bound_form" method="post" style="margin-top:10px;">
		
		<div style="margin:0 auto;margin-top:10px;">
			到货地点:<input type="text" value="" id="storePlace" style="margin-top:10px;margin-right:10px;width:400px;" name="storePlace">
			<a id='_sino_eoss_sales_products_relate' style=""
			role="button" href='#_sino_eoss_sales_products_import_page'
			role='button' target='_self' data-toggle='modal' aria-hidden="true"
			class="btn btn-success"><i class="icon-plus"></i>选择合同</a>
		</div>
		
		<table id="_product_table" cellspacing="0" cellpadding="0" border="0" width="98%" class="table table-striped table-bordered table-hover dataTable no-footer" role="grid" aria-describedby="taskTable_info" style="width: 98%;">
			<thead>
				<tr role="row">
					<th width="20%" class="sorting_disabled" rowspan="1" colspan="1">产品编号</th>
					<th width="20%" class="sorting_disabled" rowspan="1" colspan="1">合同产品数量</th>
					<th width="20%" class="sorting_disabled" rowspan="1" colspan="1">已出库产品数量</th>
					<th width="20%" class="sorting_disabled" rowspan="1" colspan="1">需要出库数量</th>
					<th width="20%" class="sorting_disabled" rowspan="1" colspan="1"><input style="float:left;" type="checkbox" name="_del_product" id="_input_del_product" value="0" /> <a style="font-size: 13px; float: right; padding: 2px;" class="btn btn-danger" id="_a_del_product"><i class="icon-remove"></i>删除</a></th>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
	</form>
	
	<div id="_sino_eoss_sales_products_import_div"></div>
</body>
<script language="javascript">
	seajs.use("js/page/business/stock/addOutStoragePage", function(addOutStoragePage) {
		addOutStoragePage.init();
	});
</script>
</html>