<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/global.jsp"%>
<link rel="stylesheet"	href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formSelect.css"	type="text/css"></link>
<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formTree.css" type="text/css"></link>
<link rel="stylesheet"	href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formstyle.css"	type="text/css"></link>


<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/user-selection.css" type="text/css"></link>
<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/dialog.css" type="text/css"></link>
<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/tip.css" type="text/css" ></link>
<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formSearchTip.css" type="text/css" ></link><style type="text/css">

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
	float:left;
}
</style>
<div class="showbody" style="width: 100%;">
	<div >
		<div id="allQuery_search_div">
		<ul class="clearfix">
				<li class="advancedSearch_li" style="height: 30px;width:300px;">
					<label for="key" class="editableLabel" style="margin-left:0px;float:left;">部门名称：</label>
					<div id="orgTree" style="margin-left:0px;float:left;"></div>   
				    <input id='orgTreeInput' type="hidden"/>  
				</li>
				<li class="advancedSearch_li" style="height: 30px;width:300px;">
					<label class="editableLabel">客户经理：</label>
					<div id="search_creator" style="width: 150px;margin-left: 100px;margin-top: -20px;" ></div>
				</li>
				<!-- <li class="advancedSearch_li" style="height: 30px;width:300px;">
					<label class="editableLabel">合同类型：</label>
					<div id="search_contractType" style="width: 150px;margin-left: 100px;margin-top: -20px;" ></div>
					</div>
				</li> -->
				<li class="advancedSearch_li" style="height: 30px;width:300px;">
					<label for="key" class="editableLabel">合同编号：</label><input type="text" id="search_contractCode" name="contractCode" style="width: 147px;" class="" maxlength="256" placeholder="输入编码或日期">
				</li>
			</ul>
			
			<ul class="clearfix">
				<li class="advancedSearch_li" style="height: 30px;width:300px;">
					<label for="key" class="editableLabel">合同金额(元)：</label><input type="text" id="search_contractAmount" name="contractAmount" style="width: 147px;"  maxlength="256">
				</li>
				<li class="advancedSearch_li"  style="height: 30px;width:500px;">
					<label class="editableLabel"  >订单生效时间：</label>
					<div id="dateTimeStopDate" class="input-append date" >
			  		    <input data-format="yyyy-MM-dd" style="width:80px;margin-left: -6px;" type="text" id="order_startTime" value=""></input>
					    <span class="add-on">
					      <i data-time-icon="icon-time" data-date-icon="icon-calendar">
					      </i>
						</span>
					  </div>
						~&nbsp;
					<div id="dateTimeStopDate" class="input-append date">
			  		    <input data-format="yyyy-MM-dd" style="width:80px;margin-left: -6px;" type="text" id="order_endTime" value=""></input>
					    <span class="add-on">
					      <i data-time-icon="icon-time" data-date-icon="icon-calendar">
					      </i>
						</span>
					  </div>
				</li>
				</ul>
				<ul class="clearfix">
					<li class="advancedSearch_li"   style="height: 30px;width:650px;">
						<label class="editableLabel" >合同生效时间：</label>
						<div id="dateTimeStopDate" class="input-append date">
				  		    <input data-format="yyyy-MM-dd" style="width:80px;margin-left: -6px;" type="text" id="sales_startTime" value="2015-01-01"></input>
						    <span class="add-on">
						      <i data-time-icon="icon-time" data-date-icon="icon-calendar">
						      </i>
							</span>
						  </div>
						  ~&nbsp;
						<div id="dateTimeStopDate" class="input-append date">
				  		    <input data-format="yyyy-MM-dd" style="width:80px;margin-left: -6px;" type="text" id="sales_endTime" value=""></input>
						    <span class="add-on">
						      <i data-time-icon="icon-time" data-date-icon="icon-calendar">
						      </i>
							</span>
						  </div>
					</li>
				 <li  style="width: 100px;">
					<div class="advancedSearch_btn"  style="height: 30px;width:300px;">
						<a style="font-size:12px;" id="reset_btn" class="btn" href="#">&nbsp;重置</a>
						<a style="font-size:12px;" id="advancedSearch_btn" class="btn btn-primary" href="#"><i class="icon-search"></i>&nbsp;查询</a>
						<a style="font-size:12px;" id="advanced_export" class="btn" href="#"><i class=" icon-cloud-download"></i>&nbsp;导出</a>
					</div>
				 </li>
			</ul>
		</div>
	</div>
</div>
<script language="javascript">
seajs.use('js/page/sales/contract/relevance/search', function(search) {
	search.init();
	});
</script>