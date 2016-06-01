<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/global.jsp"%>

<style type="text/css">
.advancedSearch li.advancedSearch_li label {
    display: inline-block;
    line-height: 18px;
    text-align: right;
    vertical-align: middle;
    width: 100px;
}

.advancedSearch {
    padding: 7px 0;
}
li.advancedSearch_li {
    float: left;
    font-size: 12px;
    padding: 3px 0;
    width: 45%;
}

.advancedSearch .advancedSearch_btn {
    border-top: 0;
    height: 30px;
    margin-top: 0px;
    padding-top: 10px;
}
</style>
<div class="showbody" style="width: 100%;">
	<div class="advancedSearch" style="">
		<div id="allQuery_search_div">
			<ul class="clearfix">
				<li class="advancedSearch_li"><label
					for="title" class="editableLabel" style="float:left;">商务负责人：</label>
					<div id="bizOwner" style="float:left;"></div>
					<input type="hidden" id="search_bizOwner" style="width: 208px;" class="InpText f12" maxlength="256">
					</li>
				<li class="advancedSearch_li"><label
					for="key" class="editableLabel" style="float:left;">供应商类型：</label>
					<div id="supplierType" style="float:left;"></div>
					<input type="hidden" id="search_supplierType" />
				</li>
				<li style="position: relative; z-index: 300;" id="searchCon_flowName" class="advancedSearch_li uic_form_select"><label
					for="title" class="editableLabel">供应商编号：</label>
					<input type="text"
					id="search_supplierCode" style="width: 208px;margin-left: -6px;margin-bottom: 0;" class="InpText f12"
					maxlength="256">
				</li>
				<li class="advancedSearch_li"><label
					for="key" class="editableLabel" >公司简称：</label><input type="text"
					id="search_shortName" style="width: 208px;margin-left: 0px;" class="InpText f12"
					maxlength="256"></li>
			</ul>
			<p class="advancedSearch_btn">
				<input type="button" value="查询" class="submit" id="advancedSearch_btn" /> 
				<input type="button" value="重置" class="submit" id="reset_btn" />
			</p>
		</div>
	</div>
</div>
<script language="javascript">
	seajs.use('js/page/business/supplierm/supplierInfo/search', function(search) {
		search.init();
	});
</script>