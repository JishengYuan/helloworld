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
.advancedSearch{
padding: 10px 0;
}
.advancedSearch .advancedSearch_btn {
    border-top: 0px dashed #ccc;
    height: 30px;
    margin-top: 0px;
    padding-top: 10px;
}
.advancedSearch li {
	width: 300px;
	float:left;
}
</style>
<div class="showbodytt" style="width: 100%;">
	<div >
		<div id="allQuery_search_div">
			<ul class="clearfix">
				<li id="searchCon_title">
					<label for="title" class="editableLabel">投标项目：</label><input type="text" id="search_contractName" name="contractName" style="" class="" maxlength="256" placeholder="模糊匹配">
				</li>
				<li id="searchCon_key">
					<label for="key" class="editableLabel">投标金额(元)：</label><input type="text" id="search_contractAmount" name="contractAmount" style="width: 70px;"  maxlength="256">
					－
					<input type="text" id="search_contractAmountb" name="contractAmount" style="width: 70px;" maxlength="256">
				</li>
				<li>
				<a href="#" class="btn btn-primary" id="advancedSearch_btn" style="font-size:12px;"><i class="icon-search"></i>&nbsp;查询</a>
				</li>
			</ul>
			

		</div>
	</div>
</div>