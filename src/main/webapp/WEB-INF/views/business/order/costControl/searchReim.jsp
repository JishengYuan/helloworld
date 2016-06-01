<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/global.jsp"%>
<link rel="stylesheet"	href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/ultra_select.css"	type="text/css"></link>
<link rel="stylesheet"href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formSelect.css"type="text/css"></link>
<link rel="stylesheet"href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formstyle.css"type="text/css"></link>

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
    padding: 5px 0;
}
</style>
<div class="showbody" style="width: 100%;">
	<div class="advancedSearch" style="">
		<div id="allQuery_search_div2">
			<ul class="clearfix" style="height: 30px;">
				<li class="advancedSearch_li uic_form_select" style="width:510px;" >
				<label class="editableLabel">报销时间：</label>
				    <div style="position: relative;" class="input-append date">
						 <input data-format="yyyy-MM-dd"  name="planReimTime" id="search_startdate" type="text"  style="width:150px"></input>
						    <span class="add-on">
						      <i data-time-icon="icon-time" data-date-icon="icon-calendar">
						      </i>
							</span>
					  </div> ~
				    <div style="position: relative;" class="input-append date">
						 <input data-format="yyyy-MM-dd"  name="reimTime" id="search_enddate" type="text"  style="width:150px"></input>
						    <span class="add-on">
						      <i data-time-icon="icon-time" data-date-icon="icon-calendar">
						      </i>
							</span>
					  </div>
			   </li>
			   <li class="advancedSearch_li" style="width:350px;">
					<label for="key" class="editableLabel">报销金额：</label>
					<input type="text" style="height:28px;" id="_reim_min" class="InpText f12" maxlength="256" placeholder="报销金额"><!--  ~ 
					<input type="text" style="width: 80px;height:28px;" id="_reim_max" class="InpText f12" maxlength="256" placeholder="报销金额"> -->
				</li>
				<!-- <li id="searchCon_flowName" class="advancedSearch_li uic_form_select"  style='height:40px;'>
				     <label for="title" class="editableLabel">供应商简称：</label>
				    <div id="supplierShortName" style="margin-top: -24px; margin-left: 106px; margin-bottom: 20px; "></div>
					<input type="hidden" id="search_supplierShortName" style="width: 180px;" class="InpText f12 required" maxlength="256">
				</li>	 -->
			</ul>
			<ul class="clearfix" style="height: 30px;">
				<li >
					<p class="advancedSearch_btn" style="float:right;">
						<a style="font-size:12px;" id="reset_btn2" class="btn" href="#">&nbsp;重置</a>
						<a style="font-size:12px;" id="advancedSearch_btn2" class="btn btn-primary" href="#"><i class="icon-search"></i>&nbsp;查询</a>
					</p>
				</li>	 
			</ul>
			<br>
		</div>
	</div>
</div>
<script language="javascript">
	seajs.use('js/page/business/order/costControl/searchReim', function(searchReim) {
		searchReim.init();
	});
</script>