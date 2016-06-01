<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/global.jsp"%>
<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/ultra_select.css" type="text/css"></link>
    <link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formSelect.css" type="text/css"></link>
    <link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formTree.css" type="text/css"></link>
    <link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formstyle.css" type="text/css"></link>
<style type="text/css">
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
.advancedSearch{
padding: 10px 0;
}
.advancedSearch .advancedSearch_btn {
    border-top: 0px dashed #ccc;
    height: 30px;
    margin-top: 10px;
    padding-top: 10px;
}
.advancedSearch li {
	float:left;
	margin-left:10px;
}
.input-append .add-on, .input-prepend .add-on{
	height:15px;
}

</style>
<div style="width: 100%;">
	<div>
		<div id="allQuery_search_div" class="allQuery_search_div">
			<input type="hidden" id="_productType" name="productType">
			<input type="hidden" id="_partnerId" name="partnerId">
			<ul class="clearfix" style="float:left;">
				<li id="searchCon_title">
					<label for="title" class="editableLabel">产品类型：</label>
					<div id="_sino_product_type" style="margin-top:-20px;margin-left:70px;"></div>
				</li>
				<li id="searchCon_key">
					<label for="key" class="editableLabel">厂商名称：</label>
					<div class="controls" id="_sino_partner_div" style="margin-left: 70px;margin-top:-20px;" >
						<input id="_sino_partner" type="text" value="${model.productPartnerName }" class="ultra-select-input3" data="0" data-content="请选择厂商" required/>
					</div>
				</li>
				<li>
					<label for="title" class="editableLabel">产品型号：</label><input type="text" id="search_orderCode" name="contractName" style="width: 150px;height:28px;" class="InpText f12" maxlength="256">
				</li>
			</ul>
			<div class="advancedSearch_btn">
			   <!-- <div style="float:left;">
			      <a href="#" class="btn btn-success" id="addContract_btn" style="font-size:12px;"><i class="icon-plus"></i>&nbsp;添加</a>
			      <a href="#" class="btn btn-danger" id="removeContract_btn" style="font-size:12px;"><i class="icon-minus"></i>&nbsp;删除</a>
				</div> -->
                <div style="float:right;">
                <a href="#" class="btn btn-primary" id="advancedSearch_btn" style="font-size:12px;"><i class="icon-search"></i>&nbsp;查询</a>

				</div>
			</div>
		</div>
	</div>
</div>