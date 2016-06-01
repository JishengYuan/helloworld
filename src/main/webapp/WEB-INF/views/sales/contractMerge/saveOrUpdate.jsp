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

<title>新闻发布</title>
<style type="text/css">
ul,ol {
	margin: 0;
}

div span a {
    color: #005EA7;
    text-decoration: none;
}
.ultrapower-table-box .showbody {
    height: auto;
    overflow: visible;
    position: relative;
    text-align: center;
    z-index: 100;
}
</style>
</head>
<body>
	<div class="showbody" style="width: 100%;">
		<div class="ultrapower-table-box">
			<strong class="title">合同管理-->合同合并新增
			</strong>
		</div>
		
		<div class="advancedSearch" style="">
		<div id="allQuery_search_div">
			<ul class="clearfix" id="_select_sales_ul">
				<li class="advancedSearch_li"  style="height: 30px;width:666px;">
					<label class="editableLabel"  style="width:100px;">
						<a style="font-size:12px;" id="add_selectSales_btn" class="btn btn-primary"><i class="icon-plus"></i>&nbsp;添加合同</a>					
					</label>
				</li>
				<li class="advancedSearch_li"  style="height: 30px;width:888px;">
					<label class="editableLabel"  style="margin-top:5px;float:left;width: 140px;">请选择要合并的合同：</label>
					<span style="float:left;" name="_select_sales_div" id="_select_sales_0"></span>
					<!-- <a href="javascript:void(0)" style="margin-top:5px;float:left;color:#389ae2;" id="_select_sales_a_0">删除</a> -->
				</li>
			</ul>
			<form id='_sino_eoss_sales_merge_addform' method="post">
			<input type="hidden" id="_contractIds_input" name="contractIds" />
			<input type="hidden" id="_contractIds_input_" name="contractId" />
			<ul class="clearfix" id="">
				<li class="advancedSearch_li"  style="height: 30px;width:888px;">
					<label class="editableLabel"  style="margin-top:5px;float:left;width: 140px;">备注：</label>
					<textarea style="width:712px;height:40px;" name="remark"></textarea>
				</li>
			</ul>
			</form>
			<p class="advancedSearch_btn" style='margin-top:30px;'>
				<a style="font-size:12px;" id="_sales_merge_submit" class="btn btn-primary"><i class="icon-plus"></i>&nbsp;提交</a>
				<a style="font-size:12px;" id="_sales_merge_back" class="btn"><i class="icon-fast-backward"></i>&nbsp;返回</a>
			</p>
		</div>
	</div>
		
	</div>
	
	<script language="javascript">
		seajs.use('js/page/sales/contractMerge/saveOrUpdate', function(saveOrUpdate) {
			saveOrUpdate.init();
		});
	</script>
</body>
</html>