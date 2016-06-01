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
<link rel="stylesheet" href="${ctx}/skin/default/room.css"
	type="text/css" />
<link rel="stylesheet"
	href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formSelect.css"
	type="text/css"></link>
<link rel="stylesheet"
	href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formstyle.css"
	type="text/css"></link>
<style type="text/css">
input {
	vertical-align: middle;
	margin: 0;
	padding: 0
}

.file-box {
	position: relative;
	width: 340px
}

.file {
	position: absolute;
	top: 0;
	right: 80px;
	height: 24px;
	filter: alpha(opacity : 0);
	opacity: 0;
	width: 260px
}
</style>
</head>
<body>
	<div class="">
		<div class="ultrapower-table-box">
			<strong class="title">库存管理-->到库设备导入</strong>
		</div>
		<br>
		<div class="thumbnail">
			<fieldset>
				<legend>
					<font color="#ccc">样例文件</font>
				</legend>
				<img alt="样例文件" src="${ctx}/images/stock/inboundExample.jpg">
				<div style="color: #ccc">
					注意：Excel文档只能导入包含如下字段:<br /> 入库单号（自动生成），订单编号，批次，型号，数量，SN（序列号），收货人
				</div>
			</fieldset>
		</div>
		<br>
		<div style="height: 30px;">
			<form id="_excel_import_form" name="_excel_import_form"
				enctype="multipart/form-data" method="post">
				<div style="float: left;">
					选择库房：<select style="height: 22px;" id="_storePlace_select">
						<option value=1 checked>中电库房</option>
						<option value=2>机房库房</option>
						<option value=3>公司库房</option>
					</select>
				</div>

				<div class="file-box" style="float: left; margin-left: 20px;">
					<input type='text' name='textfield' id='textfield' class='txt'
						style="width: 180px; padding: 0; margin: 0; height: 22px;" /> <input
						type='button' class='filebtn' value='浏览...'
						style="background-color: #FFF; border: 1px solid #CDCDCD; height: 24px; width: 70px;" />
					<input type="file" name="attachment" class="file"
						id="_excel_import_input" size="28"
						onchange="document.getElementById('textfield').value=this.value" />
				</div>
			</form>
			
			<input type="button" id="_excel_sumit" name="submit"
						class="btn btn-primary" value="上传"
						style="padding: 2px 2px; height: 24px; width: 70px;" />
		</div>
		<!-- 
				<p style="text-align:center;">
					<a href="#" id="_excel_import_action" class="btn btn-primary" role="button">提交</a>&nbsp;&nbsp;
					<a href="#" id="dealExcel" class="btn btn-primary" role="button">一键入库</a>
				</p>
	 -->
	</div>
	<br>
	<div class="">

		<table align="center" id="taskTable" cellpadding="0" cellspacing="0"
			border="0" class="table table-striped table-bordered table-hover">
			<thead>
				<tr>
					<td>入库单号</td>
					<td>产品型号</td>
					<td>数量</td>
					<td>所入库房</td>
					<td>收货人</td>
					<td>入库时间</td>
				</tr>
			</thead>
		</table>
		<table align="center">
			<tbody>

			</tbody>
		</table>
	</div>
</body>
<script language="javascript">
	seajs.use("js/page/business/stock/excel", function(excel) {
		excel.init();
	});
</script>
</html>