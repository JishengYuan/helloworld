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
    margin-top: 10px;
    padding-top: 10px;
}
.advancedSearch li {
	width: 250px;
	float:left;
}
.input-append .add-on, .input-prepend .add-on{
	height:15px;
}
</style>
<div class="showbodya" id="allQuery_search_div" style="width: 100%;">
	<div>
		<div>
			<ul class="clearfix">
				<li id="searchCon_key">
					<label for="key" class="editableLabel">合同编号：</label><input type="text" id="search_contractCode" name="contractCode" style="width: 150px;" class="InpText f12" maxlength="256">
				</li>
				<li>
					<label for="key" class="editableLabel">
					出库时间：</label>
					<div class="input-append date">
			  		    <input id="startTime" data-format="yyyy-MM-dd" style="width: 70px;height:23px;font-size:12px;" class="InpText f12" maxlength="256" type="text" name="startTime"></input>
					    <span class="add-on">
				        <i data-time-icon="icon-time" data-date-icon="icon-calendar">
				        </i>
						</span>
				    </div>
				</li>
				<li style="width:200px;margin-left:-55px;">
					－
					<div class="input-append date">
			  		    <input id="endTime" data-format="yyyy-MM-dd" style="width: 70px;height:23px;font-size:12px;" class="InpText f12" maxlength="256" type="text" name="endTime"></input>
					    <span class="add-on">
				        <i data-time-icon="icon-time" data-date-icon="icon-calendar">
				        </i>
						</span>
				    </div>
				</li>
			</ul>
			<div class="advancedSearch_btn">
                <div style="float:right;">
                <a href="#" class="btn" id="advancedSearch_btn_add" style="font-size:12px;"><i class="icon-plus"></i>&nbsp;增加</a>
                <a href="#" class="btn btn-primary" id="advancedSearch_btn" style="font-size:12px;"><i class="icon-search"></i>&nbsp;查询</a>
				</div>
			</div>
		</div>
	</div>
</div>