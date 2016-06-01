<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/global.jsp"%>
<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formTree.css" type="text/css"></link>
<script src="${ctx}/js/page/sales/contract/makePy.js" type="text/javascript"></script>
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
.advancedSearch .advancedSearch_btn {
    border-top: 0px dashed #ccc;
    height: 30px;
    margin-top: 0px;
    padding-top: 0px;
}

.advancedSearch li.advancedSearch_li {
    float: left;
    font-size: 12px;
    padding: 3px 0;
    width: 300px;
}


.modal-body{
			overflow-x:hidden;
		}
		.modal-body-a a{
			padding:10px;
			text-decoration:none;
		}
		.modal-body-c{
			float:left;
		}
		._c_div{
			background: none repeat scroll 0 0 #fff;
		    border: 1px solid #ddd;
		    height: 38px;
		    margin: 0 -1px -1px 0;
		    padding-top: 0;
		    text-align: center;
		    width: 180px;
		    float:left;
		}
		.modal-body-c a{
			background: none repeat scroll 0 0 rgba(0, 0, 0, 0);
		    border: 1px solid #fff;
		    display: inline-block;
		    height: 36px;
		    line-height: 36px;
		    overflow: hidden;
		    padding: 0;
		    position: relative;
		    text-overflow: ellipsis;
		    white-space: nowrap;
		    width: 178px;
		    text-decoration:none;
		}
		.modal-body-a a:hover{
			color:#0044cc;
		}
		.find-a-hide{
			display:none;
		}
		.find-a-show{
			display:block;
		}

		.attr {
		    border-top: 1px dotted #ccc;
		    overflow: hidden;
		    padding: 4px 0 2px;
		    width: 768px;
		}
		.attr .a-key {
		    float: left;
		    font-weight: 700;
		    line-height: 25px;
		    text-align: right;
		    width: 100px;
		}
		.attr .a-values .v-option {
		    height: 20px;
		    padding-top: 2px;
		    position: absolute;
		    right: 10px;
		    top: 0;
		    width: 105px;
		}
		.brand-attr{
			margin-top:-20px;
			margin-left:60px;
		}
		.brand-attr .a-values {
		    position: relative;
		    width: 635px;
		}
		.brand-attr .v-search {
		    height: 25px;
		    margin: 2px 0 5px;
		}
		.brand-attr .v-search input {
		    border: 1px solid #ccc;
		    color: #999;
		    float: left;
		    font-family: verdana;
		    height: 17px;
		    line-height: 17px;
		    padding: 3px 1px;
		    width: 160px;
		}
		.brand-attr .v-tabs {
		    padding-bottom: 5px;
		    width: 552px;
		}
		.brand-attr .v-tabs:after {
		    clear: both;
		    content: " ";
		    display: block;
		}
		.brand-attr .tabcon-multi {
		    background: none repeat scroll 0 0 #fff;
		    border: 1px solid #ddd;
		    height: 150px;
		    overflow-y: auto;
		    padding: 3px 0 3px 10px;
		}
		.brand-attr .tabcon div {
		    float: left;
		    height: 20px;
		    margin-right: 15px;
		    overflow: hidden;
		    padding-top: 5px;
		    width: 140px;
		}
		.brand-attr .v-tabs a {
		    color: #005aa0;
		    height: 15px;
		    line-height: 15px;
		    overflow: hidden;
		    text-decoration: none;
		    white-space: nowrap;
		}
		.brand-attr .v-tabs a:hover, .brand-attr .v-tabs a.curr {
		    color: #e4393c;
		}
		.brand-attr .img-logo .tabcon .brand-logo {
		    background: none repeat scroll 0 0 #fff;
		    border: 1px solid #ddd;
		    height: 34px;
		    margin-bottom: 5px;
		    padding-top: 0;
		    position: relative;
		    text-align: center;
		    width: 122px;
		}
		.brand-attr .img-logo .tabcon .brand-logo:hover {
		    border: 1px solid #e4393c;
		}
		.brand-attr .img-logo .tabcon .brand-logo a {
		    background: none repeat scroll 0 0 rgba(0, 0, 0, 0);
		    border: 1px solid rgba(0, 0, 0, 0);
		    display: inline-block;
		    height: 32px;
		    line-height: 34px;
		    padding: 0;
		    width: 120px;
		}
		.brand-attr .img-logo .tabcon .brand-logo:hover a {
		    background: url("i/2013112001.png") no-repeat scroll right bottom rgba(0, 0, 0, 0);
		    border: 1px solid #e4393c;
		}
		.brand-attr .img-logo .tabcon .brand-logo a img {
		    background: none repeat scroll 0 0 #fff;
		    left: 1px;
		    padding: 0 6px;
		    position: absolute;
		    top: 1px;
		    z-index: 1;
		}
		.brand-attr .img-logo .tabcon .brand-logo:hover a img {
		    display: none;
		}
		.brand-attr .img-logo .tabcon-multi .brand-logo {
		    width: 138px;
		}
		.brand-attr .img-logo .tabcon-multi .brand-logo a {
		    width: 136px;
		}
		.brand-attr .img-logo .tabcon-multi .brand-logo a img {
		    padding: 0 8px;
		}
		.brand-attr .tab {
		    height: 15px;
		    padding-top: 2px;
		}
		.brand-attr .tab li {
		    color: #005ea7;
		    cursor: pointer;
		    float: left;
		    font-family: verdana,宋体;
		    height: 14px;
		    line-height: 12px;
		    margin-right: 1px;
		    padding: 3px 5px;
		}
		.brand-attr .tab b {
		    border-color: #4598d2 transparent transparent;
		    border-style: solid dashed dashed;
		    border-width: 5px;
		    bottom: -10px;
		    display: none;
		    height: 0;
		    left: 50%;
		    margin-left: -4px;
		    overflow: hidden;
		    position: absolute;
		    width: 0;
		}
		.brand-attr .tab .curr {
		    background: none repeat scroll 0 0 #4598d2;
		    color: #fff;
		    position: relative;
		}
		.brand-attr .tab .curr b {
		    display: block;
		}
		.brand-attr .tab-con {
		    float: none;
		    height: auto;
		    margin: 0;
		    overflow: hidden;
		    padding: 0;
		    width: 578px;
		}
		.brand-attr .tab-con div {
		    overflow: hidden;
		    width: 125px;
		}
		.brand-attr .a-values .s-brands {
		    -moz-border-bottom-colors: none;
		    -moz-border-left-colors: none;
		    -moz-border-right-colors: none;
		    -moz-border-top-colors: none;
		    background: none repeat scroll 0 0 #fff;
		    border-color: -moz-use-text-color #ddd #ddd;
		    border-image: none;
		    border-right: 1px solid #ddd;
		    border-style: none solid solid;
		    border-width: 0 1px 1px;
		    display: none;
		    height: 14px;
		    margin-top: -1px;
		    padding: 8px 0;
		    width: 650px;
		}
		.brand-attr .a-values .s-brands .dt {
		    color: #999;
		    float: left;
		    padding-left: 10px;
		}
		.brand-attr .a-values .s-brands .dd {
		    float: left;
		    line-height: 14px;
		    margin-top: 1px;
		    padding: 0;
		    width: auto;
		}
		.brand-attr .a-values .s-brands .dd a {
		    background: url("i/20130606B.png") no-repeat scroll -70px -13px rgba(0, 0, 0, 0);
		    color: #e4393c;
		    float: left;
		    margin-right: 10px;
		    padding-left: 18px;
		}
		.brand-attr .s-brands .selected a, .brand-attr .s-brands .attr-select a:hover {
		    background: url("i/20130415i.png") no-repeat scroll -287px -14px rgba(0, 0, 0, 0);
		    color: #e4393c;
		    float: left;
		}
		.brand-attr.brand-selected-fold .s-brands {
		    border: 0 none;
		    display: block;
		}
		.brand-attr.brand-selected-unfold .s-brands {
		    display: block;
		    margin-top: -9px;
		    overflow: hidden;
		    position: relative;
		    z-index: 0;
		}
		.brand-attr .show-logo {
		    height: 79px;
		    margin-bottom: 10px;
		    overflow-x: hidden;
		    overflow-y: auto;
		    padding: 10px 0 0 10px;
		    position: relative;
		    width: 610px;
		}
		.brand-attr .show-logo div {
		    background: none repeat scroll 0 0 #fff;
		    border: 1px solid #ddd;
		    height: 38px;
		    margin: 0 -1px -1px 0;
		    padding-top: 0;
		    text-align: center;
		    width: 150px;
		}
		.brand-attr .show-logo div a {
		    background: none repeat scroll 0 0 rgba(0, 0, 0, 0);
		    border: 1px solid #fff;
		    display: inline-block;
		    height: 36px;
		    line-height: 36px;
		    overflow: hidden;
		    padding: 0;
		    position: relative;
		    text-overflow: ellipsis;
		    white-space: nowrap;
		    width: 148px;
		}
		.brand-attr .show-logo div b {
		    background: url("i/2013112001.png") no-repeat scroll right bottom rgba(0, 0, 0, 0);
		    bottom: 1px;
		    display: none;
		    height: 16px;
		    position: absolute;
		    right: 1px;
		    width: 16px;
		    z-index: 6;
		}
		.brand-attr .show-logo .hover, .brand-attr .show-logo .hover a, .brand-attr .show-logo .selected, .brand-attr .show-logo .selected a {
		    border: 1px solid #e4393c;
		    position: relative;
		    z-index: 5;
		}
		.brand-attr .show-logo .selected b {
		    display: block;
		}
		.brand-attr .show-logo div img {
		    background: none repeat scroll 0 0 #fff;
		    display: block;
		    height: 36px;
		    left: 0;
		    position: absolute;
		    top: 0;
		    width: 102px;
		    z-index: 1;
		}
		.brand-attr .show-logo .selected a:hover img, .brand-attr .show-logo .hover a img {
		    display: none;
		}
		.brand-attr .height185 {
		    height: 185px;
		    overflow-x: hidden;
		    overflow-y: auto;
		}
		.brand-attr .height185 span.clr {
		    height: 10px;
		}

		ol, ul {
		    list-style: none outside none;
		}
</style>
<div class="showbody" style="width: 100%;">
	<div class="advancedSearch" style="">
		<div id="allQuery_search_div">
		<input type="hidden" id="orgId" name="orgId" value="${orgId }"/>
			<ul class="clearfix">
				<li class="advancedSearch_li" id="searchCon_title">
					<label for="title" class="editableLabel">合同名称：</label><input type="text" id="search_contractName" name="contractName" style="width: 150px;height:28px;" class="InpText f12" maxlength="256"  placeholder="合同名称模糊查询">
				</li>
				<li class="advancedSearch_li" id="searchCon_key">
					<label for="key" class="editableLabel">合同编号：</label><input type="text" id="search_contractCode" name="contractCode" style="width: 150px;height:28px;" class="InpText f12" maxlength="256" placeholder="合同编号、日期">
				</li>
				<li class="advancedSearch_li" id="searchCon_key">
					<label for="key" class="editableLabel">合同金额：</label><input type="text" id="search_contractAmount" name="contractAmount" style="width: 150px;height:28px;" class="InpText f12" maxlength="256" >
				</li>
			</ul>
			<ul class="clearfix">
				<li class="advancedSearch_li" >
					<label class="editableLabel">客户经理：</label>
					<div id="search_creator" style="width: 150px;margin-left: 100px;margin-top: -20px;" ></div>
				</li>
				<li class="advancedSearch_li" >
					<label class="editableLabel">合同类型：</label>
					<div id="contractType" style="width: 150px;margin-left: 100px;margin-top: -20px;"  >
						<input type="hidden" id="search_contractType" name="contractType"/>
					</div>
				</li>
				<li class="advancedSearch_li" >
					<label class="editableLabel">合同状态：</label>
					<div id="contractState" style="width: 150px;margin-left: 100px;margin-top: -20px;"  >
						<input type="hidden" id="search_contractState" name="contractState"/>
					</div>
				</li>
			</ul>
			<ul class="clearfix">
				
				<li class="advancedSearch_li" style="height: 30px;margin-top: -8px;width:331px;">
					<label class="editableLabel">客户名称：</label>
						<input type="hidden" id="search_customerInfo" name="customerInfo"/>
						<div id="customerInfo" style="margin-left: 100px;margin-top: -20px;"  >
						</div>
						<a style="height: 20px; margin-left: 300px; float: left; margin-top: -40px; width: 0px;" id='_sino_eoss_sales_customerInfo_select'  role="button" href='#_sino_eoss_sales_products_import_page' role='button' target='_self'  data-toggle='modal' aria-hidden="true" class="btn btn-success"><i class="icon-user" style="width:10px;margin-left:-5px;font-size:18px;"></i></a>
				</li>
				<li class="advancedSearch_li"  style="height: 30px;margin-top: -8px;width:270px;">
					<label class="editableLabel"  style="margin-top: -13px;width:70px;">开始时间：</label>
					<div id="dateTimeStopDate" class="input-append date">
			  		    <input data-format="yyyy-MM-dd" style="width:120px;margin-left: -6px;" type="text" id="startTime" value="2015-01-01"></input>
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
			</ul>
			<ul class="clearfix">
		
				<li class="advancedSearch_li" id="searchCon_key">
					<label class="editableLabel">行业：</label>
					<div id="_customerIndustry" style="height:28px;margin-left: 100px;margin-top: -20px;"  >
						<input type="hidden" id="search_customerIndustry"/>
					</div>
				</li>
				<li class="advancedSearch_li" >
					<label class="editableLabel">行业客户：</label>
					<div id="_customerIdtCustomer" style="width: 150px;margin-left: 100px;margin-top: -20px;"  >
					</div>
						<input type="hidden" id="search_customerIdtCustomer" />
				</li>
				
			</ul>
			<!-- <ul class="clearfix">
				
				<li class="advancedSearch_li" style="height: 30px;width:331px;">
				<label for="key" class="editableLabel" style="margin-left:0px;float:left;">部门名称：</label>
					<div id="orgTree" style="margin-left:0px;float:left;"></div>   
				    <input id='orgTreeInput' type="hidden"/>  
				</li>
				</ul> -->
				
			<p class="advancedSearch_btn">
				<a style="font-size:12px;" id="reset_btn" class="btn" href="#">&nbsp;重置</a>
				<a style="font-size:12px;" id="advancedSearch_btn" class="btn btn-primary" href="#"><i class="icon-search"></i>&nbsp;查询</a>
				<a style="font-size:12px;" id="advanced_export" class="btn" href="#"><i class=" icon-cloud-download"></i>&nbsp;导出</a>
			</p>
		</div>
	</div>
</div>
<script language="javascript">
	seajs.use('js/page/sales/contract/multipleCompre', function(multipleCompre) {
		multipleCompre.init();
	});
</script>