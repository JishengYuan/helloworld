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
p, ul, ol, li, dl, dd, dt, form {
    list-style: none outside none;
}
li.advancedSearch_li {
    float: left;
    font-size: 12px;
    padding: 3px 0;
    width: 45%;
}
.selectDiv .uicSelectInp {
    background: none repeat scroll 0 0 #FFFFFF;
    border: 1px solid #CCCCCC;
    cursor: pointer;
    height: 13px;
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
<div class="showbody" style="width: 100%;">
	<div class="advancedSearch" style="">
		<div id="allQuery_search_div">
			<ul class="clearfix">
				<li class="advancedSearch_li"><label
					for="key" class="editableLabel" style="margin-left: -30px; margin-top: -20px;">供应商类型：</label>
					<div id="supplierType" style="margin-top: -30px; margin-left: 64px;margin-bottom:20px"></div>
					<input type="hidden" id="search_supplierType" />
				</li>
				<li style="position: relative; z-index: 300;" id="searchCon_flowName" class="advancedSearch_li uic_form_select"><label
					for="title" class="editableLabel">供应商编号：</label>
					<input type="text"
					id="search_supplierCode" style="width: 208px;" class="InpText f12"
					maxlength="256">
				</li>
			</ul>
			<ul class="clearfix">
				<li class="advancedSearch_li"><label
					for="key" class="editableLabel" style="margin-left:-40px">公司简称：</label><input type="text"
					id="search_shortName" style="width: 208px;" class="InpText f12"
					maxlength="256"></li>
				<li class="advancedSearch_li"><label
					for="key" class="editableLabel" style="">返点数：</label>
					<input type="text" id="search_smallReturn" style="width: 88px;" class="InpText f12" maxlength="256">
					~<input type="text" id="search_bigReturn" style="width: 95px;" class="InpText f12" maxlength="256">
				</li>
			</ul>
			<p class="advancedSearch_btn">
				<input type="button" value="查询" class="submit"
					id="advancedSearch_btn"> <input type="button" value="重置"
					class="submit" id="reset_btn">
			</p>
		</div>
	</div>
</div>
<script language="javascript">
	seajs.use('js/page/business/supplierm/returnSpot/search', function(search) {
		search.init();
	});
</script>