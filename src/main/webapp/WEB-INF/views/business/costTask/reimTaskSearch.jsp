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

.advancedSearch .advancedSearch_btn {
    border-top: 0px dashed #ccc;
    height: 30px;
    margin-top: 0px;
    padding-top: 5px;
}

.advancedSearch li.advancedSearch_li {
    float: left;
    font-size: 12px;
    padding: 3px 0;
    width: 300px;
}
.advancedSearch {
    padding: 0px 0;
}
</style>
<div class="showbody" style="width: 100%;margin-bottom: 5px;">
	<div class="advancedSearch" style="">
		<div id="allQuery_search_div">
			<ul class="clearfix">
				<li class="advancedSearch_li" style="width:350px;">
					<label for="title" class="editableLabel" style="width:85px;">报销单名称：</label><input type="text" id="search_reimbursementName" name="reimbursementName" style="width: 230px;height:20px;margin-bottom:0;" class="InpText f12" maxlength="256" placeholder="人名或日期查询">
				</li>
				<li class="advancedSearch_li">
					<label for="key" class="editableLabel">报销金额：</label>
					<input type="text" id="search_amount" style="width: 150px;height:20px;margin-bottom:0;" class="InpText f12" maxlength="256" placeholder="报销金额">
				</li>
				<li class="advancedSearch_li" style="width:300px;">
					<a style="font-size:12px;margin-left: 100px;"id="advancedSearch_btn2" class="btn btn-primary" href="#"><i class="icon-search"></i>&nbsp;查询</a>
				</li>
			</ul>
		</div>
	</div>
</div>