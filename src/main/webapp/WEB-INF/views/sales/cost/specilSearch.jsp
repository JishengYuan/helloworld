<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/global.jsp"%>
<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formTree.css" type="text/css"></link>
<script src="${ctx}/js/page/sales/contract/makePy.js" type="text/javascript"></script>
<style type="text/css">

p, ul, ol, li, dl, dd, dt, form {
    list-style: none outside none;
}
.advancedSearch{
padding: 10px 0;
}
.advancedSearch li {
	width: 300px;
	float:left;
}

.advancedSearch {
    padding: 5px 0;
}
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
    padding-top: 0px;
}

</style>
<div class="showbody" style="width: 100%;">
	<div >
		<div id="allQuery_search_div">
			<ul class="clearfix">
				<li >
					<label for="key" class="editableLabel">合同编号：</label><input type="text" id="search_salesCode2" name="salesCode" style="" class="" maxlength="256" placeholder="输入编码或日期">
				</li>
				<li >
					<label for="key" class="editableLabel">合同名称：</label><input type="text" id="search_salesName2" name="salesName" style="" class="" maxlength="256" placeholder="合同名称模糊查询">
				</li>
				<li >
					<label for="key" class="editableLabel">合同金额：</label><input type="text" id="search_salesAmount2" name="salesAmount" style="" class="" maxlength="256" placeholder="输入合同金额">
				</li>

			</ul>
			<ul class="clearfix">
				<li>
					<label class="editableLabel">客户经理：</label>
					<div id="search_creator2" style="width: 150px; margin-left: 70px;margin-top: -20px;" ></div>
				</li>
				<li  style="height: 30px;margin-top: -8px;width:400px;">
					<label class="editableLabel"  style="width:70px;">时间区间：</label>
					<div id="dateTimeStopDate" class="input-append date" style="margin-top: 5px;">
			  		    <input data-format="yyyy-MM" style="width:66px;margin-left: -6px;" type="text" id="search_time2" value="${time}"></input>
					    <span class="add-on">
					      <i data-time-icon="icon-time" data-date-icon="icon-calendar">
					      </i>
						</span>
					 </div>~
					 <div id="dateTimeStopDate" class="input-append date" style="margin-top: 5px;">
			  		    <input data-format="yyyy-MM" style="width:66px;margin-left: -6px;" type="text" id="search_timeEnd2" value="${timeEnd}"></input>
					    <span class="add-on">
					      <i data-time-icon="icon-time" data-date-icon="icon-calendar">
					      </i>
						</span>
					 </div>
				</li>
			</ul>
			<div class="advancedSearch_btn">
                <div style="">
                	<a href="#" class="btn btn-primary" id="advancedSearch_btn2" style="font-size:12px;float:right;"><i class="icon-search"></i>&nbsp;查询</a>
				</div>
				
			</div>
		</div>
	</div>
</div>

<script language="javascript">
	seajs.use('js/page/sales/cost/specilSearch', function(specilSearch) {
		specilSearch.init();
	});
</script>