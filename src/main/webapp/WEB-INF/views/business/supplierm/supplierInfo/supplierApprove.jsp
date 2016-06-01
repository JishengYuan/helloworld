<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/global.jsp"%>
 <%@ include file="/common/include-base-boostrap-styles.jsp" %>
<link rel="stylesheet"
	href="${ctx}/js/plugins/uic/style/sinobridge/pink/sino_form_grid.css" type="text/css" />
<script src="${ctx}/js/plugins/seajs/sea.js" id="seajsnode" type="text/javascript"></script>
	<script src="${ctx}/js/plugins/seajs/config.js" type="text/javascript"></script>
	<link href="${ctx}/skin/default/sales/sale.css" rel="stylesheet"/>




<html>
<head>
<title>供应商信息审批</title>
<style type="text/css">
.div_style{
		margin:0 auto;
		width:1002px;
	}

</style>
</head>
<body>
<!-- 遮盖层div -->
	<div id="_progress1" style="display: none; top: 0px; left: 0px; width: 100%; margin-left: 0px; height: 100%; margin-top: 0px; z-index: 1000; opacity: 0.7; text-align: center; background-color: rgb(204, 204, 204); position: fixed;">
	</div>
	<div id="_progress2" style="display: none; width: 233px; height: 89px; top: 45%; left: 45%; position: absolute; z-index: 1001; line-height: 23px; text-align: center;">
		<div style="top: 50%;position:fixed;">
			<div style="display: block;">数据加载中，请稍等...</div>
		</div>
	</div>

<div class="salecontent">
	<div class="top">
		<div class="caption">
			<i class="icon-reorder"></i>&nbsp;&nbsp;&nbsp;供应商信息审批
		</div>
	</div>
</div>

	<div class="center" style="top: 25px;">
		<!--表单、底部按钮 -->
		<div class="border01 bg_grey " style="width:1002px;">
			<!-- 表单 -->
			<form id="_eoss_business_supplier" class='form-horizontal' style="" method="post">
				<input type="hidden" name="id" value="${model.id }">
				<input type="hidden" name="supplierType" id="_supplierType" value="${model.supplierType }">
				<input type="hidden" name="bizOwner" id="_bizOwner" value="${model.bizOwner }">
				<input type="hidden" name="grade" id="_grade" value="${model.grade }">
				<input type="hidden" name="relationShip" id="_relationShip" value="${model.relationShip }">
				<input type="hidden" name="relationGrade" id="_relationGrade" value="${model.relationGrade }">
				<input type="hidden" name="tableData" id="_tableData" value="">
				<h4>&nbsp;供应商信息</h4>
				<div class="handprocess_order" style="width: 983px;">
					
					
					<ul class="clearfix" >
						<li id="field_userName" class="li_form" style="width: 300px;">
							<label for="userName" class="editableLabel"><span
								class="red">*</span>供应商类型：</label>
							<div id="supplierType" class="InpText f12 required"></div>
						</li>
						<li id="field_userName" class="li_form" style="width: 300px;">
							<label for="userName" class="editableLabel"><span
								class="red">*</span>商务负责人：</label> 
							<div id="bizOwner" class="InpText f12 required"></div>
						</li>
						<li id="field_userName" class="li_form" style="width: 260px;">
							<label for="userName" class="editableLabel"><span
								class="red">*</span>供应商编号：</label> ${model.supplierCode }
						</li>
					</ul>
					<ul class="clearfix">
						<li id="field_userName" class="li_form" style="width: 300px;">
							<label for="userName" class="editableLabel"><span
								class="red">*</span>供应商全称：</label> ${model.supplierName }
						</li>
						<li id="field_userName" class="li_form" style="width: 300px;">
							<label for="userName" class="editableLabel"><span
								class="red">*</span>公司简称：</label> ${model.shortName }
						</li>
						<%-- <li id="field_userName" class="li_form" style="width: 260px;">
							<label for="userName" class="editableLabel">英文名称：</label> ${model.englishName }
						</li> --%>
						<li id="field_userName" class="li_form" style="width: 300px;">
							<label for="userName" class="editableLabel"><span
								class="red">*</span>供应商评级：</label>
								<div id="supplierGrade" ></div>
						</li>
						
					</ul>
					<ul class="clearfix">
						<!-- <li id="field_userName" class="li_form" style="width: 300px;">
							<label for="userName" class="editableLabel"><span
								class="red">*</span>供应商评级：</label>
								<div id="supplierGrade" ></div>
						</li> -->
						<li id="field_userName" class="li_form" style="width:300px;">
							<label for="userName" class="editableLabel"><span
								class="red">*</span>合作关系：</label>
								<div id="relationShip"></div>
						</li>
						
						<li id="field_userName" class="li_form" style="width: 300px;">
							<label for="userName" class="editableLabel">返点厂商：</label> <c:if test="${model.enableReturnSpot == 1 }">是</c:if>
							<c:if test="${model.enableReturnSpot == 2 }">否</c:if>
						</li>
						
						<li id="field_userName" class="li_form" style="width: 260px;">
							<label for="userName" class="editableLabel"><span
								class="red">*</span>电 话：</label> ${model.phone }
						</li>
						<!-- <li id="field_userName" class="li_form" style="width: 260px;">
							<label for="userName" class="editableLabel"><span
								class="red">*</span>认证级别：</label>
								<div id="relationGrade" ></div>
						</li> -->
						
					</ul>
					<%-- <ul class="clearfix" >
						<li id="field_userName" class="li_form" style="width: 300px;">
							<label for="userName" class="editableLabel">公司规模：</label> ${model.scal }
						</li>
						<li id="field_userName" class="li_form" style="width: 300px;">
							<label for="userName" class="editableLabel">返点厂商：</label> <c:if test="${model.enableReturnSpot == 1 }">是</c:if>
							<c:if test="${model.enableReturnSpot == 2 }">否</c:if>
						</li>
					</ul> --%>
					<!-- <ul class="clearfix" > -->
						<%-- <li id="field_userName" class="li_form" style="width: 300px;">
							<label for="userName" class="editableLabel"><span
								class="red">*</span>地区信息：</label>
							${model.country }-${model.province }-${model.city }
						</li>
						<li id="field_userName" class="li_form" style="width: 300px;">
							<label for="userName" class="editableLabel"><span
								class="red">*</span>地区归属：</label> ${model.region }
						</li> --%>
						<%-- <li id="field_userName" class="li_form" style="width: 260px;">
							<label for="userName" class="editableLabel"><span
								class="red">*</span>电 话：</label> ${model.phone }
						</li> --%>
					<!-- </ul> -->
					<ul class="clearfix" >
						<li id="field_userName" class="li_form" style="width: 300px;">
							<label for="userName" class="editableLabel"><span
								class="red">*</span>传真：</label> ${model.fax }
						</li>
						<li id="field_userName" class="li_form" style="width: 300px;">
							<label for="userName" class="editableLabel">网址：</label> ${model.web }
						</li>
						<li id="field_userName" class="li_form" style="width: 260px;">
							<label for="userName" class="editableLabel"><span
								class="red">*</span>邮政编码：</label> ${model.zipCode }
						</li>
					</ul>
					<ul class="clearfix">
						<li class="li_form" style="width: 924px;"><label for="title"
							class="editableLabel"><span class="red">*</span>地址：</label> ${model.address }</li>
					</ul>
					<%-- <ul class="clearfix" >
						<li class="li_form" style="width: 924px;"><label for="title"
							class="editableLabel">其他地址：</label> ${model.otherAddress }</li>
					</ul> --%>
					<ul class="clearfix" >
						<li id="field_userName" class="li_form" style="width: 300px;">
							<label for="userName" class="editableLabel"><span
								class="red">*</span>开户银行：</label>${model.bankName }
						</li>
						<li id="field_userName" class="li_form" style="width: 300px;">
							<label for="userName" class="editableLabel"><span
								class="red">*</span>银行账号：</label> ${model.bankAccount }
						</li>
						<%-- <li id="field_userName" class="li_form" style="width: 260px;">
							<label for="userName" class="editableLabel">税 号：</label> ${model.taxNo }
						</li> --%>
					</ul>
					<%-- <ul class="clearfix" >
						<li id="field_userName" class="li_form" style="width: 300px;">
							<label for="userName" class="editableLabel">机构代码证：</label> ${model.orgCode }
						</li>
					</ul> --%>
					<ul class="clearfix" >
						<li class="li_form" style="width: 900px;"><label for="title"
							class="editableLabel">代理的品牌：</label> 
							${model.bizScope }
						</li>
					</ul>
					<%-- <ul class="clearfix">
						<li class="li_form" style="width: 900px;"><label for="title"
							class="editableLabel">公司背景：</label> 
							${model.background }
						</li>
					</ul> --%>
					<ul class="clearfix">
						<li class="li_form" style="width: 924px;"><label for="title"
							class="editableLabel">备注：</label> 
							${model.remark }
						</li>
					</ul>
				</div>
				<h5>&nbsp;联系人信息</h5>
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
			<ul class="clearfix">
				<h5>&nbsp;审批</h5>
				<li id="field_userName" class="li_form" style="width: 725px;margin-left: 50px;">
				   		<input type="radio" name="isAgree" value="1" checked="checked" />审批通过</br>
						<input type="radio" name="isAgree" value="2"/>审批不通过
				</li> 
				<li>
					<label for="title" class="editableLabel">备注：</label> 
					<textarea rows="" cols="" name="cause" style="width:750px"></textarea>
				</li>
			</ul>

			
			</form>
			<!--按钮组-->
			<div id="bottom_button" class="handprocess_btngroup">
    			<input id="_eoss_business_submit" type="button" value="确定" class="btn btn-primary">
        		<input id="_eoss_business_back" type="button" value="取消"  class="btn">
    		</div>
		</div>
	</div>
</body>
<script language="javascript">
seajs.use('js/page/business/supplierm/supplierInfo/supplierApprove', function(supplierApprove) {
	supplierApprove.init();
}); 
</script>
</html>