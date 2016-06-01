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
    padding: 0px 0;
}
p, ul, ol, li, dl, dd, dt, form {
    list-style: none outside none;
}
.advancedSearch .advancedSearch_btn {
    border-top: 0px dashed #ccc;
    height: 30px;
    padding-top: 10px;
    margin-bottom: 10px;
    margin-top: 0px;
}

.advancedSearch li.advancedSearch_li {
    float: left;
    font-size: 12px;
    padding: 3px 0;
    width: 300px;
}
</style>
<div class="showbody" style="width: 100%;">
	<div class="advancedSearch" >
		<div id="allQuery_search_div2" style="height:40px;">
			<ul class="clearfix" >
				<li class="advancedSearch_li" id="searchCon_title">
					<label class="editableLabel">合同名称：</label><input type="text" id="search_Name" name="contractName" class="InpText f12 required" maxlength="256" style="width: 180px;" placeholder="合同名称">
				</li>
				<li class="advancedSearch_li" id="searchCon_key">
					<label class="editableLabel">合同编号：</label><input type="text" id="search_Code" name="contractCode" class="InpText f12 required" maxlength="256" style="width: 180px;"placeholder="合同编号">
				</li>
				<li class="advancedSearch_li" id="searchCon_key">
					<label class="editableLabel">合同金额：</label><input type="text" id="search_Amounts" name="contractAmount" class="InpText f12 required" maxlength="256" style="width: 180px;" placeholder="合同金额">
				</li>
			</ul>
			<ul class="clearfix" >
					<p class="advancedSearch_btn" style="float:right;margin-top:-15px ;">
						<a style="font-size:12px;" id="reset_btn2" class="btn" href="#">&nbsp;重置</a>
						<a style="font-size:12px;" id="advancedSearch_btn2" class="btn btn-primary" href="#"><i class="icon-search"></i>&nbsp;查询</a>
					</p>
			</ul>
		</div>
	</div>
</div>
<!-- <script language="javascript">
	seajs.use('js/page/business/order/waitContract/closeSearch', function(closeSearch) {
		closeSearch.init();
	});
</script> -->
