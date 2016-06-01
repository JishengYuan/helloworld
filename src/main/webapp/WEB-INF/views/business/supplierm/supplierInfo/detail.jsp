<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/global.jsp"%>
<link rel="stylesheet"
	href="${ctx}/js/plugins/uic/style/sinobridge/pink/sino_form_grid.css"
	type="text/css" />
<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formSelect.css" type="text/css"></link>
<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formstyle.css" type="text/css"></link>

<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/romerSelect.css" type="text/css"></link>
<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/rsearch-tips.css" type="text/css"></link>
<link rel="stylesheet" href="${ctx}/skin/default/editgrid/editgrid.css" type="text/css" />

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
				<input type="hidden" name="id" value="${model.id }">
				<input type="hidden" name="supplierType" id="_supplierType" value="${model.supplierType }">
				<input type="hidden" name="bizOwner" id="_bizOwner" value="${model.bizOwner }">
				<input type="hidden" name="grade" id="_grade" value="${model.grade }">
				<input type="hidden" name="relationShip" id="_relationShip" value="${model.relationShip }">
				<input type="hidden" name="relationGrade" id="_relationGrade" value="${model.relationGrade }">
				<input type="hidden" name="tableData" id="_tableData" value="">
				<div class="handprocess_order bg_orag" style="">
					<h3>供应商信息</h3>
					
					<ul class="clearfix" style="position: relative;">
						<li id="field_userName" class="li_form" style="width: 300px;">
							<label for="userName" class="editableLabel"><span
								class="red">*</span>供应商类型：</label>
							<div id="supplierType" class="InpText f12 required"></div>
						</li>
						<li id="field_userName" class="li_form" style="width: 300px;">
							<label for="userName" class="editableLabel"><span
								class="red">*</span>商务负责人：</label> 
								<%-- <input type="text" id="bizOwner" name="bizOwner" style="width: 150px;" class="InpText f12 required" value="${model.bizOwner }" maxlength="256"> --%>
							<div id="bizOwner" class="InpText f12 required"></div>
						</li>
						<li id="field_userName" class="li_form" style="width: 300px;">
							<label for="userName" class="editableLabel"><span
								class="red">*</span>供应商编号：</label> ${model.supplierCode }
						</li>
					</ul>
					<ul class="clearfix" style="position: relative;">
						<li id="field_userName" class="li_form" style="width: 300px;">
							<label for="userName" class="editableLabel"><span
								class="red">*</span>供应商全称：</label> ${model.supplierName }
						</li>
						<li id="field_userName" class="li_form" style="width: 300px;">
							<label for="userName" class="editableLabel"><span
								class="red">*</span>公司简称：</label> ${model.shortName }
						</li>
						<%-- <li id="field_userName" class="li_form" style="width: 300px;">
							<label for="userName" class="editableLabel">英文名称：</label> ${model.englishName }
						</li> --%>
						
						<li id="field_userName" class="li_form" style="width: 300px;">
							<label for="userName" class="editableLabel"><span
								class="red">*</span>供应商评级：</label>
								<div id="supplierGrade" class="InpText f12 required"></div>
						</li>
					</ul>
					<ul class="clearfix" style="position: relative;">
						
						<li id="field_userName" class="li_form" style="width: 300px;">
							<label for="userName" class="editableLabel"><span
								class="red">*</span>合作关系：</label>
								<div id="relationShip" class="InpText f12 required"></div>
						</li>
						<!-- <li id="field_userName" class="li_form" style="width: 300px;">
							<label for="userName" class="editableLabel"><span
								class="red">*</span>认证级别：</label>
								<div id="relationGrade" class="InpText f12 required"></div>
						</li> -->
						
						<li id="field_userName" class="li_form" style="width: 300px;">
							<label for="userName" class="editableLabel"><span
								class="red">*</span>电 话：</label> ${model.phone }
						</li>
						
						<li id="field_userName" class="li_form" style="width: 300px;">
							<label for="userName" class="editableLabel">返点厂商：</label> <c:if test="${model.enableReturnSpot == 1 }">是</c:if>
							<c:if test="${model.enableReturnSpot == 2 }">否</c:if>
						</li>
						
					</ul>
					
					<%-- <ul class="clearfix" style="position: relative;">
						<li id="field_userName" class="li_form" style="width: 600px;">
							<label for="userName" class="editableLabel">公司规模：</label> ${model.scal }
						</li>
					</ul> --%>
					
					<%-- <ul class="clearfix" style="position: relative;">
						<li id="field_userName" class="li_form" style="width: 300px;">
							<label for="userName" class="editableLabel"><span
								class="red">*</span>地区信息：</label>
							${model.country }-${model.province }-${model.city }
						</li>
						<li id="field_userName" class="li_form" style="width: 300px;">
							<label for="userName" class="editableLabel"><span
								class="red">*</span>地区归属：</label> ${model.region }
						</li>
					</ul> --%>
					
					<ul class="clearfix" style="position: relative;">
						<li id="field_userName" class="li_form" style="width: 300px;">
							<label for="userName" class="editableLabel"><span
								class="red">*</span>传真：</label> ${model.fax }
						</li>
						<li id="field_userName" class="li_form" style="width: 300px;">
							<label for="userName" class="editableLabel"><span
								class="red">*</span>邮政编码：</label> ${model.zipCode }
						</li>
						<li id="field_userName" class="li_form" style="width: 360px;">
							<label for="userName" class="editableLabel">网址：</label> ${model.web }
						</li>
					</ul>
					
					<ul class="clearfix" style="position: relative;">
						<li id="field_userName" class="li_form" style="width: 455px;">
							<label for="userName" class="editableLabel"><span
								class="red">*</span>开户银行：</label>${model.bankName }
						</li>
						<li id="field_userName" class="li_form" style="width: 455px;">
							<label for="userName" class="editableLabel"><span
								class="red">*</span>银行账号：</label> ${model.bankAccount }
						</li>
					</ul>
					
					<ul class="clearfix" style="position: relative;">
						<li class="li_form" style="width: 924px;"><label for="title"
							class="editableLabel"><span class="red">*</span>地址：</label> ${model.address }</li>
					</ul>
					<%-- <ul class="clearfix" style="position: relative;">
						<li class="li_form" style="width: 924px;"><label for="title"
							class="editableLabel">其他地址：</label> ${model.otherAddress }</li>
					</ul> --%>
					
					<%-- <ul class="clearfix" style="position: relative;">
						<li id="field_userName" class="li_form" style="width: 455px;">
							<label for="userName" class="editableLabel">税 号：</label> ${model.taxNo }
						</li>
						<li id="field_userName" class="li_form" style="width: 455px;">
							<label for="userName" class="editableLabel">机构代码证：</label> ${model.orgCode }
						</li>
					</ul> --%>
					<ul class="clearfix" style="position: relative;">
						<li class="li_form" style="width: 924px;"><label for="title"
							class="editableLabel">代理的品牌：</label> 
							${model.bizScope }
						</li>
						<%-- <li class="li_form" style="width: 455px;"><label for="title"
							class="editableLabel">公司背景：</label> 
							${model.background }
						</li> --%>
					</ul>
					<ul class="clearfix" style="position: relative;">
						<li class="li_form" style="width: 924px;"><label for="title"
							class="editableLabel">备注：</label> 
							${model.remark }
						</li>
					</ul>
				</div>
				<div style="margin-left:20px;"><h5>联系人信息</h5></div>
				<table border="0" style="width: 100%" class="sino_table_body">
				<thead>
					<tr>
						<td class="sino_table_label" style="background:none repeat scroll 0 0 #F3F3F3">姓名</td>
						<td class="sino_table_label" style="background:none repeat scroll 0 0 #F3F3F3">电话</td>
						<td class="sino_table_label" style="background:none repeat scroll 0 0 #F3F3F3">手机号</td>
						<td class="sino_table_label" style="background:none repeat scroll 0 0 #F3F3F3">备注</td>
					</tr>
				</thead>
				<tbody>
				 <c:forEach var="contacts" items="${model.contactsModels}" varStatus="status">
					<tr>
						<td class="sino_table_label">${contacts.contactName }</td>
						<td class="sino_table_label">${contacts.contactPhone }</td>
						<td class="sino_table_label">${contacts.contactTelPhone }</td>
						<td class="sino_table_label">${contacts.remark }</td>
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
seajs.use('js/page/business/supplierm/supplierInfo/detail', function(detail) {
	detail.init();
}); 
</script>
</html>