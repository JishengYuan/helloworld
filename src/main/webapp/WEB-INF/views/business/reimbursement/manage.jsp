<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/global.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="${ctx}/skin/default/room.css" type="text/css" />
<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/ultra_select.css" type="text/css"></link>

<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formSelect.css" type="text/css"></link>
<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formstyle.css" type="text/css"></link>

<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/internetTable.css" type="text/css"></link>
<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/processList.css" type="text/css"></link>

<title>发票计划</title>
<style type="text/css">
ul,ol {
	margin: 0;
}

div span a {
    color: #005EA7;
    text-decoration: none;
}
.advancedSearch li.advancedSearch_li label {
    display: inline-block;
    line-height: 18px;
    text-align: right;
    vertical-align: middle;
    width: 100px;
}
p, ul, ol, li, dl, dd, dt, form {
    list-style: none outside none;
}
li.advancedSearch_li {
    float: left;
    font-size: 12px;
    padding: 3px 0;
    width: 45%;
}
label.editableLabel{
  color: #61b0e9;
}
</style>
    <script src="${ctx}/js/plugins/seajs/sea.js" id="seajsnode" type="text/javascript"></script>
	<script src="${ctx}/js/plugins/seajs/config.js" type="text/javascript"></script>
</head>
<body>
<div class="portlet-body form"> 
	<input type="hidden" name="id" id="id" value="${model.id}">
<form  id="_sino_eoss_cuotomer_addform" class='form-horizontal' method="post" action="">
	<div class="handprocess_order bg_orag" id="SheetDiv">
	<ul class="clearfix" >
		<li  id="searchCon_flowName" class="advancedSearch_li uic_form_select"  style="width: 350px;" >
			<label for="orderCode" class="editableLabel" style="margin-top: 0px; margin-left:0px;" >订单编号：</label>${model.orderCode }
		</li>
		<li  id="searchCon_flowName" class="advancedSearch_li uic_form_select"  style="width: 350px;" >
			<label class="editableLabel"style="margin-top: 0px; margin-left:0px;" >订单名称：</label>${model.orderName }
		</li>
			<li  id="searchCon_flowName" class="advancedSearch_li uic_form_select" style="width: 350px;">
				<label for="orderCode" class="editableLabel" style="margin-top: 0px; margin-left:0px;">供应商名称：</label>
				${model.supplierInfoModel.supplierName }
			</li>
			<li  id="searchCon_flowName" class="advancedSearch_li uic_form_select"  style="width: 350px;">
				<label class="editableLabel" style="margin-top: 0px; margin-left:0px;">订单金额：</label>${model.orderAmount}
			</li>
	</ul>	
	</div>
</form>
</div>
	<div id="uicTable"></div>
	<script language="javascript">
	var orderId=${orderId};
		seajs.use('js/page/business/reimbursement/manage', function(manage) {
			manage.init();
		});
	</script>
</body>
</html>