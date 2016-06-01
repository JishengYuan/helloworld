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
<div class="showbody" style="width: 100%;">
	<div >
		<div id="allQuery_search_div">
			<ul class="clearfix">
				<li id="searchCon_title" style="width: 350px;">
					<label for="title" class="editableLabel">合同名称：</label><input type="text" id="search_contractName" name="contractName" style="" class="" maxlength="256" placeholder="模糊匹配">
				</li>
				<li id="searchCon_key" style="width: 350px;">
					<label for="key" class="editableLabel">合同编号：</label><input type="text" id="search_contractCode" name="contractCode" style="" class="" maxlength="256" placeholder="输入编码或日期">
				</li>
				<!-- <li id="field_userName"  class="advancedSearch_li uic_form_select"  style="width: 200px; display: inline; line-height: 30px;">
			    	 <div style="float:left;" >
				 			<input type="checkbox" id="unpass" name="doState"  value='0' checked="checked" style="margin-top:0px;"/>&nbsp;未确认&nbsp;&nbsp;&nbsp;
						    <input type="checkbox" id="pass" name="doState"  value='1' style="margin-top:0px;"/>&nbsp;已确认&nbsp;&nbsp;&nbsp;
				  	</div>
				 </li> -->
			<!-- </ul>
			<ul class="clearfix"> -->
			<li id="searchCon_key" style="width: 400px;">
					<label for="key" class="editableLabel">合同金额(元)：</label><input type="text" id="search_contractAmount" name="contractAmount" style="width: 70px;"  maxlength="256">
					－
					<input type="text" id="search_contractAmountb" name="contractAmount" style="width: 70px;" maxlength="256">
				</li>
			</ul>
			<div class="advancedSearch_btn" style="margin-top: -10px;">
			   <div style="float:left;">
			      <a href="#" class="btn btn-danger" id="removeContract_btn" style="font-size:12px;" title="只能删除未确认的合同"><i class="icon-minus"></i>&nbsp;删除</a>
				</div>
                <div style="float:right;">
                <a href="#" class="btn btn-primary" id="advancedSearch_btn" style="font-size:12px;"><i class="icon-search"></i>&nbsp;查询</a>
				</div>
			</div>
		</div>
	</div>
</div>