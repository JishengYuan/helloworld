<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/global.jsp"%>

<style type="text/css">
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

.advancedSearch li.advancedSearch_li {
    float: left;
    font-size: 12px;
    padding: 1px 0;
    width: 290px;
}
.advancedSearch .advancedSearch_btn {
    border-top: 0px dashed #ccc;
    height: 30px;
    margin-top: -25px;
    padding-top: 0px;
}
</style>
<div class="showbody" style="width: 100%;">
	<div class="advancedSearch" style="">
		<div id="allQuery_search_div">
			<ul class="clearfix">
				
				<li  id="searchCon_flowName" class="advancedSearch_li uic_form_select" style="width: 290px;">
				     <label for="title" class="editableLabel">订单编号：</label>
				     <input type="text" id="search_orderCode" style="width: 170px; height:28px" class="InpText f12" maxlength="256"  placeholder="订单编号，日期">
				</li>
				<li id="searchCon_flowName" class="advancedSearch_li uic_form_select" style="width: 290px;">
				     <label for="title" class="editableLabel">订单名称：</label>
				     <input type="text" id="search_orderName" style="width: 170px;height:28px" class="InpText f12" maxlength="256" placeholder="订单名称模糊查询">
				</li>	
				<li id="searchCon_flowName" class="advancedSearch_li uic_form_select" style="width: 290px;">
				     <label for="title" class="editableLabel">订单金额：</label>
				     <input type="text" id="search_orderAmount" style="width: 170px;height:28px" class="InpText f12" maxlength="256" placeholder="订单金额">
				</li>	
			</ul>
			<ul class="clearfix">
				<li class="advancedSearch_li">
					<label for="title" class="editableLabel" style="margin-top: 16px; ">订单类型：</label>
					<div id="orderType" style="margin-top: -25px; margin-left: 106px; margin-bottom: 20px; "></div>
					<input type="hidden" id="search_orderType" class="InpText f12 required" maxlength="230">
				</li>
				<li class="advancedSearch_li" >
					<label for="title" class="editableLabel" style="margin-top: 16px; ">采购员：</label>
					<div id="creator" style="margin-top: -26px; margin-left:106px; margin-bottom: 20px;"></div>
					<input type="hidden" id="search_creator" class="InpText f12 required" maxlength="256">
				</li>
				<li class="advancedSearch_li">
					<label for="title" class="editableLabel"  style="margin-top: 16px;">采购分类：</label>
					<div id="purchaseType" style="margin-top: -26px; margin-left: 106px; margin-bottom: 20px; "></div>
					<input type="hidden" id="search_purchaseType" class="InpText f12 required" maxlength="256">
				</li>
			</ul>
			<ul class="clearfix">
			    <li class="advancedSearch_li" style="height:66px;">
					<label for="title" class="editableLabel" style="margin-top: -4px; ">供应商简称：</label>
					<div id="supplierShortName" style="margin-top: -24px; margin-left: 106px; margin-bottom: 20px; "></div>
					<input type="hidden" id="search_supplierShortName" style="width: 180px;" class="InpText f12 required" maxlength="256">
				</li>
				<li class="advancedSearch_li">
					<label for="title" class="editableLabel" style="margin-top: -4px; ">付款情况：</label>
					<div id="payStatus" style="margin-top: -24px; margin-left: 106px; margin-bottom: 20px;"></div>
					<input type="hidden" id="search_payStatus" style="width: 208px;" class="InpText f12 required" maxlength="256">
				</li>
				<li class="advancedSearch_li">
					<label for="title" class="editableLabel" style="margin-top: -4px;">发票状态：</label>
					<div id="reimStatus" style="margin-top: -24px; margin-left: 106px; margin-bottom: 20px; "></div>
					<input type="hidden" id="search_reimStatus" style="width: 180px;" class="InpText f12 required" maxlength="256">
				</li>
			</ul>
			<ul class="clearfix">
			    <li class="advancedSearch_li"  style="height: 30px;margin-top: -30px;margin-left:6px;">
					<label class="editableLabel"  style="margin-top: -13px;">订单开始时间：</label>
					<div id="dateTimeStopDate" class="input-append date">
			  		    <input data-format="yyyy-MM-dd" style="width:144px;margin-left: -6px;" type="text" id="startTime" value="${startTime }"></input>
					    <span class="add-on">
					      <i data-time-icon="icon-time" data-date-icon="icon-calendar">
					      </i>
						</span>
					  </div>
				</li>
				<li class="advancedSearch_li"  style="height: 30px;margin-top: -30px;">
					<label class="editableLabel"  style="margin-top: -13px;">结束时间：</label>
					<div id="dateTimeStopDate" class="input-append date">
			  		    <input data-format="yyyy-MM-dd" style="width:144px;margin-left: -6px;" type="text" id="endTime" value="${endTime }"></input>
					    <span class="add-on">
					      <i data-time-icon="icon-time" data-date-icon="icon-calendar">
					      </i>
						</span>
					  </div>
				</li>
			</ul>
			
			<p class="advancedSearch_btn" style="float:right;">
				<!-- <input type="button" value="查询" class="submit"	id="advancedSearch_btn"> 
				<input type="button" value="重置" class="submit" id="reset_btn"> -->
				<a style="font-size:12px;" id="reset_btn" class="btn" href="#">&nbsp;重置</a>
				<a style="font-size:12px;" id="advancedSearch_btn" class="btn btn-primary" href="#"><i class="icon-search"></i>&nbsp;查询</a>
			</p>
		</div>
	</div>
</div>
<script language="javascript">
	seajs.use('js/page/business/order/orderSearch/allSearch', function(allSearch) {
		allSearch.init();
	});
</script>