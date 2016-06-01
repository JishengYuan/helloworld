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
.selectDiv .uicSelectInp {
    background: none repeat scroll 0 0 #FFFFFF;
    border: 1px solid #CCCCCC;
    height: 20px;
    padding: 0 5px;
    position: relative;
    text-align: left;
    vertical-align: middle;
}
.selectDiv a.uicSelectMore {
    background: url(ctx+"/uflow/styles/excellenceblue/images/ultraselect/ms_ico2.png") no-repeat scroll 1px 0 rgba(0, 0, 0, 0);
    display: block;
    height: 21px;
    position: absolute;
    right: 0;
    top: 0;
    width: 22px;
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
.selectDiv .uicSelectInp {
    background: none repeat scroll 0 0 #FFFFFF;
    border: 1px solid #CCCCCC;
    cursor: pointer;
    height: 20px;
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
				<!-- 联系人信息是采用拼接成JSON格式的数据串，然后存放在这个name为tableData的隐藏域字段中的 -->
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
								class="red">*</span>供应商编号：</label> <input type="text" id="supplierCode" name="supplierCode" style="width: 150px;"
							class="InpText f12 required" value="${model.supplierCode }" maxlength="256" required data-content="请填写供应商编号">
						</li>
					</ul>
					<ul class="clearfix" style="position: relative;">
						<li id="field_userName" class="li_form" style="width: 300px;">
							<label for="userName" class="editableLabel"><span
								class="red">*</span>供应商全称：</label> <input type="text"
							name="supplierName" style="width: 150px;"
							class="InpText f12 required" value="${model.supplierName }" maxlength="256" required data-content="请填写供应商全称">
						</li>
						<li id="field_userName" class="li_form" style="width: 300px;">
							<label for="userName" class="editableLabel"><span
								class="red">*</span>公司简称：</label> 
								<input type="text" required data-content="请填写公司简称"
							name="shortName" style="width: 150px;" id="shortName" 
							class="InpText f12 required" value="${model.shortName }" maxlength="256">
						</li>
						<%-- <li id="field_userName" class="li_form" style="width: 300px;">
							<label for="userName" class="editableLabel">英文名称：</label> <input type="text"
							name="englishName" style="width: 150px;"
							class="InpText f12 required" value="${model.englishName }" maxlength="256">
						</li> --%>
						
						<li id="field_userName" class="li_form" style="width: 300px;">
							<label for="userName" class="editableLabel">
								<span class="red">*</span>供应商评级：
							</label>
							<div id="supplierGrade" class="InpText f12 required"></div>
						</li>
					</ul>
					<ul class="clearfix" style="position: relative;">
						<!-- 移动1 -->
						<li id="field_userName" class="li_form" style="width: 300px;">
							<label for="userName" class="editableLabel"><span
								class="red">*</span>合作关系：</label>
								<div id="relationShip" class="InpText f12 required"></div>
						</li>
						
						<li id="field_userName" class="li_form" style="width: 300px;">
							<label for="userName" class="editableLabel"><span
								class="red">*</span>电 话：</label> <input type="text"
							name="phone" style="width: 150px;"
							class="InpText f12 required" value="${model.phone }" maxlength="256" required data-content="请填写电话">
						</li>
						
						<li id="field_userName" class="li_form" style="width: 300px;">
							<label for="userName" class="editableLabel">
								<span class="red">*</span>返点厂商：
							</label>
							<input type="radio" value="1" name="enableReturnSpot" <c:if test="${model.enableReturnSpot == 1 }">checked</c:if> />是
							<input type="radio" value="2" name="enableReturnSpot" <c:if test="${model.enableReturnSpot != 1 }">checked</c:if> />否
						</li>
						<!-- <li id="field_userName" class="li_form" style="width: 300px;">
							<label for="userName" class="editableLabel"><span
								class="red">*</span>认证级别：</label>
								<div id="relationGrade" class="InpText f12 required"></div>
						</li> -->
						
					</ul>
					<%-- <ul class="clearfix" style="position: relative;">
						<li id="field_userName" class="li_form" style="width: 600px;">
							<label for="userName" class="editableLabel">公司规模：</label> <input type="text"
							name="scal" style="width: 450px;"
							class="InpText f12 required" value="${model.scal }" maxlength="256">
						</li> 
						<li id="field_userName" class="li_form" style="width: 300px;">
							<label for="userName" class="editableLabel">返点厂商：</label>
							<input type="radio" value="1" name="enableReturnSpot" <c:if test="${model.enableReturnSpot == 1 }">checked</c:if> />是
							<input type="radio" value="2" name="enableReturnSpot" <c:if test="${model.enableReturnSpot != 1 }">checked</c:if> />否
						</li>
					</ul>--%>
					<%-- <ul class="clearfix" style="position: relative;">
						<li id="field_userName" class="li_form" style="width: 300px;">
							<label for="userName" class="editableLabel"><span
								class="red">*</span>地区信息：</label>
							<input type="text" value="${model.country }-${model.province }-${model.city }" selectid="" value="" id="_country" name="_country" data="0" style="height:13px;width:150px;" required data-content="请填写地区信息"/>
						</li>
						<li id="field_userName" class="li_form" style="width: 300px;">
							<label for="userName" class="editableLabel"><span
								class="red">*</span>地区归属：</label> <input type="text" id="region"
							name="region" style="width: 150px;"
							class="InpText f12 required" value="${model.region }" maxlength="256">
						</li> 
						<li id="field_userName" class="li_form" style="width: 300px;">
							<label for="userName" class="editableLabel"><span
								class="red">*</span>电 话：</label> <input type="text"
							name="phone" style="width: 150px;"
							class="InpText f12 required" value="${model.phone }" maxlength="256" required data-content="请填写电话">
						</li>
					</ul> --%>
					<ul class="clearfix" style="position: relative;">
						<li id="field_userName" class="li_form" style="width: 300px;">
							<label for="userName" class="editableLabel"><span
								class="red">*</span>传真：</label> <input type="text"
							name="fax" style="width: 150px;"
							class="InpText f12 required" value="${model.fax }" maxlength="256" required data-content="请填写传真">
						</li>
						<li id="field_userName" class="li_form" style="width: 300px;">
							<label for="userName" class="editableLabel"><span
								class="red">*</span>邮政编码：</label> <input type="text"
							name="zipCode" style="width: 150px;"
							class="InpText f12 required" value="${model.zipCode }" maxlength="256" required data-content="请填写邮政编码-数字" pattern="number">
						</li>
						<li id="field_userName" class="li_form" style="width: 300px;">
							<label for="userName" class="editableLabel">
								<span class="red">*</span>网址：
							</label>
							<input type="text" name="web" style="width: 150px;"
							class="InpText f12 required" required data-content="请填写网址" value="${model.web }" maxlength="256">
						</li>
					</ul>
					<ul class="clearfix" style="position: relative;">
						<li class="li_form" style="width: 924px;"><label for="title"
							class="editableLabel"><span class="red">*</span>地址：</label> <input
							type="text" id="address" name="address" style="width: 750px;"
							class="InpText f12 required" maxlength="256" value="${model.address }" required data-content="请填写地址"></li>
					</ul>
					<%-- <ul class="clearfix" style="position: relative;">
						<li class="li_form" style="width: 924px;"><label for="title"
							class="editableLabel">其他地址：</label> <input
							type="text" id="otherAddress" name="otherAddress" style="width: 750px;"
							class="InpText f12 required" maxlength="256" value="${model.otherAddress }"></li>
					</ul> --%>
					<ul class="clearfix" style="position: relative;">
						<li id="field_userName" class="li_form" style="width: 455px;">
							<label for="userName" class="editableLabel"><span
								class="red">*</span>开户银行：</label> <input type="text" id="bankName"
							name="bankName" style="width: 299px;"
							class="InpText f12 required" value="${model.bankName }" maxlength="256" required data-content="请填写开户银行">
						</li>
						<li id="field_userName" class="li_form" style="width: 455px;">
							<label for="userName" class="editableLabel"><span
								class="red">*</span>银行账号：</label> <input type="text" id="bankAccount"
							name="bankAccount" style="width: 299px;"
							class="InpText f12 required" value="${model.bankAccount }" maxlength="256" required data-content="请填写银行账号-数字" pattern="number">
						</li>
					</ul>
					<%-- <ul class="clearfix" style="position: relative;">
						<li id="field_userName" class="li_form" style="width: 455px;">
							<label for="userName" class="editableLabel">税 号：</label> <input type="text" id="taxNo"
							name="taxNo" style="width: 299px;"
							class="InpText f12 required" value="${model.taxNo }" maxlength="256">
						</li>
						<li id="field_userName" class="li_form" style="width: 455px;">
							<label for="userName" class="editableLabel">机构代码证：</label> <input type="text" id="orgCode"
							name="orgCode" style="width: 299px;"
							class="InpText f12 required" value="${model.orgCode }" maxlength="256">
						</li>
					</ul> --%>
					<ul class="clearfix" style="position: relative;">
						<li class="li_form" style="width: 924px;">
							<label for="title" class="editableLabel">
								<span class="red">*</span>代理的品牌：
							</label> 
							<textarea rows="" cols="" name="bizScope" style="width:750px" required data-content="请填写代理的品牌">${model.bizScope }</textarea>
						</li>
						<%-- <li class="li_form" style="width: 455px;"><label for="title"
							class="editableLabel">公司背景：</label> 
							<textarea rows="" cols="" name="background" style="width:300px">${model.background }</textarea>
						</li> --%>
					</ul>
					<ul class="clearfix" style="position: relative;">
						<li class="li_form" style="width: 924px;">
						<label for="title" class="editableLabel">
							<span class="red">*</span>备注：
						</label> 
						<textarea required data-content="请填写备注信息" rows="" cols="" name="remark" style="width:750px">${model.remark }</textarea>
						</li>
					</ul>
					
					<h3>联系人信息</h3>
					<c:forEach items="${contactIds }" var="contactId">
						<input type="hidden" name="contactIds" value="${contactId }" />
					</c:forEach>
					<div id="editTable"></div>
				</div>
			</form>
			<!--按钮组-->
			<div id="bottom_button" class="handprocess_btngroup">
				<input type="button" id="_eoss_business_supplierdraft" value="保存草稿"
					 class="grey_btn33 cp" style="height:30px;">
				
				<input type="button" id="_eoss_business_supplieradd" value="提交" 
					onmouseout="this.className='orange_btn33 cp'"
					onmouseover="this.className='orange_btn33_hover cp'"
					class="orange_btn33 cp" style="height:30px;"> 
				<input type="button" id="_eoss_business_supplierback" value="返回"
					 class="grey_btn33 cp" style="height:30px;">
			</div>
		</div>
	</div>
</body>
<script language="javascript">
var form = ${form};
seajs.use('js/page/business/supplierm/supplierInfo/saveOrUpdate', function(saveOrUpdate) {
	saveOrUpdate.init();
});
</script>
</html>