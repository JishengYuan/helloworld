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
					<label for="title" class="editableLabel">合同名称：</label><input type="text" id="search_contractName" name="contractName" style="" class="" maxlength="256" placeholder="模糊匹配">
				</li>
				<li id="searchCon_key">
					<label for="key" class="editableLabel">合同编号：</label><input type="text" id="search_contractCode" name="contractCode" style="" class="" maxlength="256" placeholder="输入编码或日期">
				</li>
				<li id="searchCon_key">
					<label for="key" class="editableLabel">合同金额(元)：</label><input type="text" id="search_contractAmount" name="contractAmount" style="width: 70px;"  maxlength="256">
					－
					<input type="text" id="search_contractAmountb" name="contractAmount" style="width: 70px;" maxlength="256">
				</li>
				<li style="width:30px;">
					<label for="key" class="editableLabel" id="_contract_moreSearch_btn"><span class="icon-double-angle-down" style="font-size:25px;"></span></label>
				</li>
			</ul>
			
			<ul class="clearfix" id="_contract_moreSearch" style="display:none;">
				
				<li >
					<label class="editableLabel">合同状态：</label>
					<div id="contractState" style="width: 150px;margin-left: 70px;margin-top: -20px;"  >
						<input type="hidden" id="search_contractState" name="contractState"/>
					</div>
				</li>
				<li>
					<label class="editableLabel"  style="margin-top: -13px;width:70px;">开始时间：</label>
					<div id="dateTimeStopDate" class="input-append date">
			  		    <input data-format="yyyy-MM-dd" style="width:120px;margin-left: -6px;" type="text" id="startTime"></input>
					    <span class="add-on">
					      <i data-time-icon="icon-time" data-date-icon="icon-calendar">
					      </i>
						</span>
					  </div>
				</li>
				<li>
					<label class="editableLabel"  style="margin-top: -13px;">结束时间：</label>
					<div id="dateTimeStopDate" class="input-append date">
			  		    <input data-format="yyyy-MM-dd" style="width:120px;margin-left: -6px;" type="text" id="endTime" value="${endTime }"></input>
					    <span class="add-on">
					      <i data-time-icon="icon-time" data-date-icon="icon-calendar">
					      </i>
						</span>
					  </div>
				</li>
			</ul>
			
			<div class="advancedSearch_btn">
			   <div style="float:left;">
			      <a href="#" class="btn btn-success" id="addContract_btn" style="font-size:12px;"><i class="icon-plus"></i>&nbsp;添加</a>
			      <a href="#" class="btn btn-danger" id="removeContract_btn" style="font-size:12px;" title="只能草稿状态的合同才可行删除"><i class="icon-minus"></i>&nbsp;删除</a>
				</div>
                <div style="float:right;">
                <a href="#" class="btn btn-primary" id="advancedSearch_btn" style="font-size:12px;"><i class="icon-search"></i>&nbsp;查询</a>
				<a style="font-size:12px;" id="advanced_export" class="btn" href="#"><i class=" icon-cloud-download"></i>&nbsp;导出</a>
				</div>
			</div>
		</div>
	</div>
</div>