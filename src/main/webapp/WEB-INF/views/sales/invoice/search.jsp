<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/global.jsp"%>
<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formTree.css" type="text/css"></link>
<script src="${ctx}/js/page/sales/contract/makePy.js" type="text/javascript"></script>
<style type="text/css">
.ultrapower-table-box .operation {
    border-bottom: 0;
    height: 0px;
    position: relative;
    text-align: left;
    width: 100%;
    z-index: 200;
}
.advancedSearch li.advancedSearch_li label {
    display: inline-block;
    line-height: 18px;
    text-align: right;
    vertical-align: middle;
    width: 100px;
}

.advancedSearch{
	padding: 10px 0;
}
.advancedSearch .advancedSearch_btn {
    border-top: 0 ;
    height: 30px;
    margin-top: -40px;
    padding-top: 0;
}
.advancedSearch li {
	float:left;
}

</style>
<div class="showbody" style="width: 100%;">
	<div >
		<div id="allQuery_search_div">
			<ul class="clearfix">
				<li style="margin-left:30px;height:30px;width:260px;text-align:right;">
					<label for="key" class="editableLabel" style="margin-left:0px;float:left;">部门名称：</label>
					<div id="orgTree" style="margin-left:0px;float:left;"></div>   
				    <input id='orgTreeInput' type="hidden"/>  
				</li>
				<li id="searchCon_title" style="margin-left:20px;width:510px;">
					<label for="startTime" class="editableLabel">审批时间：</label>
					<div class="input-append date">
			  		    <input data-format="yyyy-MM-dd" style="width:120px;margin-left: -6px;" type="text" id="startTime" ></input>
					    <span class="add-on">
					      <i data-time-icon="icon-time" data-date-icon="icon-calendar">
					      </i>
						</span>
					  </div>
					  &nbsp;&nbsp;<span style="font-weight: bold;font-size:14px;">——</span>&nbsp;&nbsp;
					   <div class="input-append date">
			  		    <input data-format="yyyy-MM-dd" style="width:120px;margin-left: -6px;" type="text" id="endTime" "></input>
					    <span class="add-on">
					      <i data-time-icon="icon-time" data-date-icon="icon-calendar">
					      </i>
						</span>
					  </div>
				</li>
				<li id="searchCon_key" style="margin-left:1px;">
					<label for="search_minAmount" class="editableLabel">发票金额(元)：</label><input type="text" id="search_minAmount" style="width:150px;" maxlength="256">
				</li>
				<li  class="advancedSearch_li" style="margin-left:10px;width:290px;">
					<label for="search_contract" class="editableLabel">合同名称：</label>
					<input type="text" id="search_contract" style="width:150px;" maxlength="256" />
				</li>
				<li  style="margin-left:10px;width:280px;height:30px;">
					<label for="search_creatorUser" style="margin-left:20px;margin-bottom:-20px;" class="editableLabel">客户经理：</label>
					<div id="search_creatorUser" style="width:150px;margin-left:90px;margin-top: -20px;" ></div>
				</li>
				
				<li style="margin-top:10px;margin-right:390px;width:230px;text-align:center;float:right;">
					<a href="#" class="btn" id="advancedSearch_btn" style="font-size:12px;">
						<i class="icon-search"></i>&nbsp;查询
					</a>
                	<a href="#" class="btn btn-primary" id="advancedSearch_reset" style="font-size:12px;">
                		<i class="icon-repeat"></i>&nbsp;重置
                	</a>
                	<a href="#" class="btn" id="advancedSearch_export" style="font-size:12px;">
                		<i class="icon-repeat"></i>&nbsp;导出
                	</a>
				</li>
				
			</ul>
		</div>
	</div>
</div>
<script language="javascript">
	seajs.use('js/page/sales/invoice/search', function(search) {
		search.init();
	});
</script>