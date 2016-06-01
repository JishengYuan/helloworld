<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/global.jsp"%>
<link rel="stylesheet"
	href="${ctx}/js/plugins/uic/style/sinobridge/pink/sino_form_grid.css"
	type="text/css" />

<html>
<head>
<title>供应商信息</title>
<style type="text/css">
table {
    border-collapse: separate;
    max-width: 100%;
}
.select-box {
    background: none repeat scroll 0 0 #FFFFFF;
    border: 1px solid #D7D7D7;
    border-radius: 6px;
    box-shadow: 3px 3px 10px #999999;
    position: absolute;
    top: 30px;
    z-index: 9999;
}

.form_table table th {
    background: none repeat scroll 0 0 #F3F3F3;
    border-bottom: 1px solid #D7D7D7;
    border-left: 1px solid #D7D7D7;
    border-top: 1px solid #D7D7D7;
    color: #888888;
    line-height: 22px;
    padding: 3px 3px 3px 10px;
    text-align: left;
}

.selectDiv a.uicSelectMore {
    display: block;
    height: 21px;
    position: absolute;
    right: 0;
    top: 0;
    width: 25px;
}
label.editableLabel {
    color: #E99031;
    margin-top: 3px;
}
</style>
</head>
<body>

	<div class="center" style="top: 40px;">
		<!--表单、底部按钮 -->
		<div class="border01 bg_grey handprocess" style="">
			<!-- 表单 -->
			<form id="_eoss_business_supplier" novalidate="novalidate" style="" method="post">
				<input type="hidden" id="_supplierType" value="${model.supplierInfo.supplierType }">
				<div class="handprocess_order bg_orag" style="">
					<h3>返点信息</h3>
					
					<ul class="clearfix" style="position: relative;">
						<li id="field_userName" class="li_form" style="width: 450px;">
							<label for="userName" class="editableLabel">供应商类型：</label>
							<div id="supplierType" class="InpText f12 required"></div>
						</li>
						<li id="field_userName" class="li_form" style="width: 450px;">
							<label for="userName" class="editableLabel">供应商编号：</label>${model.supplierInfo.supplierCode }
						</li>
					</ul>
					<ul class="clearfix" style="position: relative;">
						<li id="field_userName" class="li_form" style="width: 450px;">
							<label for="userName" class="editableLabel">供应商简称：</label>${model.supplierInfo.shortName }
						</li>
						<li id="field_userName" class="li_form" style="width: 450px;">
							<label for="userName" class="editableLabel">返点剩余量：</label>
							${model.returnAmount}
						</li>
					</ul>
				</div>
				<table border="0" style="width: 100%" class="sino_table_body">
				<thead>
					<tr>
						<td class="sino_table_label" style="background:none repeat scroll 0 0 #F3F3F3">序号</td>
						<td class="sino_table_label" style="background:none repeat scroll 0 0 #F3F3F3">日期</td>
						<td class="sino_table_label" style="background:none repeat scroll 0 0 #F3F3F3">收入</td>
						<td class="sino_table_label" style="background:none repeat scroll 0 0 #F3F3F3">支出</td>
						<td class="sino_table_label" style="background:none repeat scroll 0 0 #F3F3F3">操作人</td>
						<td class="sino_table_label" style="background:none repeat scroll 0 0 #F3F3F3">备注信息</td>
					</tr>
				</thead>
				<tbody>
				 <c:forEach var="log" items="${model.spotLogSet}" varStatus="status">
					<tr>
						<td class="sino_table_label">${status.index +1}</td>
						<td class="sino_table_label">${log.operatorTime }</td>
						<td class="sino_table_label">${log.amount }</td>
						<td class="sino_table_label">${log.banlance }</td>
						<td class="sino_table_label">${log.operaotor }</td>
						<td class="sino_table_label">${log.remark }</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
			</form>
			<!--按钮组-->
			<div id="bottom_button" class="handprocess_btngroup">
				<input type="button" id="_eoss_business_supplierback" value="返回"
					 class="grey_btn33 cp" style="height:30px;">
			</div>
		</div>
	</div>
</body>
<script language="javascript">
seajs.use('js/page/business/supplierm/returnSpot/detail', function(detail) {
	detail.init();
});
</script>
</html>