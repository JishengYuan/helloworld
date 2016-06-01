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
				<li id="searchCon_key">
					<label for="key" class="editableLabel">合同编号：</label><input type="text" id="search_contractCode" name="contractCode" style="width: 147px;" class="" maxlength="256" placeholder="输入编码或日期">
				</li>
				<li id="searchCon_key">
					<label for="key" class="editableLabel">合同金额(元)：</label><input type="text" id="search_contractAmount" name="contractAmount" style="width: 120px;"  maxlength="256">
				</li>
				 <li id="searchCon_key">
					<label for="title" class="editableLabel" >供应商简称：</label>
					<div id="supplierShortName" style="margin-top: -21px; margin-left: 90px; margin-bottom: 20px; "></div>
					<input type="hidden" id="search_supplierShortName" style="width: 180px;" class="InpText f12 required" maxlength="256">
				</li>
				<li class="advancedSearch_li"  style="height: 30px;margin-top: -8px;width:270px;">
					<label class="editableLabel"  style="margin-top: -13px;width:70px;">开始时间：</label>
					<div id="dateTimeStopDate" class="input-append date">
			  		    <input data-format="yyyy-MM-dd" style="width:120px;margin-left: -6px;" type="text" id="startTime" value="${startTime }"></input>
					    <span class="add-on">
					      <i data-time-icon="icon-time" data-date-icon="icon-calendar">
					      </i>
						</span>
					  </div>
				</li>
				<li class="advancedSearch_li"  style="height: 30px;margin-top: -8px;width:265px;">
					<label class="editableLabel"  style="margin-top: -13px;">结束时间：</label>
					<div id="dateTimeStopDate" class="input-append date">
			  		    <input data-format="yyyy-MM-dd" style="width:120px;margin-left: -6px;" type="text" id="endTime" value="${endTime }"></input>
					    <span class="add-on">
					      <i data-time-icon="icon-time" data-date-icon="icon-calendar">
					      </i>
						</span>
					  </div>
				</li>
				<li id="field_userName"  class="advancedSearch_li uic_form_select"  style="width: 250px; display: inline; line-height: 30px;">
			    	 <div style="float:right">
				 			<input type="checkbox" id="unpass" name="isAgree"  value='1' checked="checked"/>&nbsp;未完成&nbsp;&nbsp;&nbsp;
						    <input type="checkbox" id="pass" name="isAgree"  value='2'/>&nbsp;已完成&nbsp;&nbsp;&nbsp;
				  	</div>
				 </li>
				 <li  style="width: 100px;">
					 <div style="float:right;">
		                <a href="#" class="btn btn-primary" id="advancedSearch_btn" style="font-size:12px;"><i class="icon-search"></i>&nbsp;查询</a>
					 </div>
				 </li>
			</ul>
			   <!-- <div style="float:left;">
			      <a id='selectContract' aid="" href='#_sino_eoss_sales_products_import_page' role='button' target='_self' data-toggle='modal' aria-hidden="true" class="btn btn-success" style="font-size:12px;"><i class="icon-plus"></i>&nbsp;添加</a>
			      <a href="#" class="btn btn-danger" id="removeContract_btn" style="font-size:12px;" title="只能草稿状态的合同才可行删除"><i class="icon-minus"></i>&nbsp;删除</a>
				</div> -->
                
		</div>
	</div>
</div>
<script language="javascript">
seajs.use('js/page/sales/manage/finance/search', function(search) {
	search.init();
	});
</script>