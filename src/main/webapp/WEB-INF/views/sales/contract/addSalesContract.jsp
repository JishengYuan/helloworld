<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/global.jsp"%>
<%@ page import="com.sinobridge.systemmanage.util.Global"%> 
<%@ page import="com.sinobridge.systemmanage.vo.SystemUser"%> 
<html>
<head>
	<title>添加合同</title>
	<%@ include file="/common/include-base-boostrap-styles.jsp" %>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/sinobridge/pink/sino_form_grid.css" type="text/css" />
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formSelect.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formstyle.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/dialog.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/tip.css" type="text/css" ></link>
	<link rel="stylesheet" href="${ctx}/skin/default/editgrid/editgrid.css" type="text/css" />
	<link type="text/css" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/jquery-ui-1.8.17.custom.css" rel="stylesheet">
	<%
  	SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
   String sessionid =  request.getSession().getId();
   String username = systemUser.getUserName();
   String staffname = systemUser.getStaffName();
  %>
    <script >
      var sessionid='<%=sessionid%>';
      var username = '<%=username%>';
      var staffname = '<%=staffname%>';
  </script>
	<script src="${ctx}/js/plugins/seajs/sea.js" id="seajsnode" type="text/javascript"></script>
	<script src="${ctx}/js/plugins/seajs/config.js" type="text/javascript"></script>
	<script src="${ctx}/js/page/sales/contract/makePy.js" type="text/javascript"></script>
	<link href="${ctx}/skin/default/sales/sale.css" rel="stylesheet"/>
	<style type="text/css">
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
		body{
			 color: #666;
		    font: 12px/150% Arial,Verdana,"宋体";
			}
		ol, ul {
		    list-style: none outside none;
		}
		.row-column{
		float:left;
		margin-left:16px;
		}
		.row-column:first-child{
		margin-left:0px;
		}
		.row-label{
		color: #eeb422;
		}
	</style>
</head>
<body>
	<!-- 遮盖层div -->
	<div id="_progress1" style="display: none; top: 0px; left: 0px; width: 100%; margin-left: 0px; height: 100%; margin-top: 0px; z-index: 1000; opacity: 0.7; text-align: center; background-color: rgb(204, 204, 204); position: fixed;">
	</div>
	<div id="_progress2" style="display: none; width: 233px; height: 89px; top: 45%; left: 45%; position: absolute; z-index: 1001; line-height: 23px; text-align: center;">
		<div style="top: 50%;position:fixed;">
			<div style="display: block;">数据处理中，请稍等...</div>
		</div>
	</div>
	
	<div class="salecontent">
		<div class="top">
			<div class="caption">
				<i class="icon-reorder"></i>&nbsp;&nbsp;&nbsp;添加合同信息
			</div>
			<div class="tools">
				<!--a href="javascript:;" class="reload"><i class="icon-repeat"></i>返回</a-->
			</div>
		</div>
	</div>
		<div class="portlet-body form">
			<!-- BEGIN FORM-->
			<form id='_sino_eoss_sales_contract_addform' name='_sino_eoss_sales_contract_addform' class='form-horizontal' >
				<!--隐藏字段start -->
				<input type='hidden' id='_is_submit_product'  value="1"/>
				<!--  隐藏的合同编码-->
				<!-- <input type='hidden' id='contractCode' name='contractCode'/> -->
				<!--  隐藏的tableGrid预存收款计划JSON数据-->
				<input type='hidden' name='tableData' id='tableData' />
				<!--  隐藏的合同类型ID-->
				<input type="hidden" name="contractType" id="_eoss_sales_contractTypeId"/>
				<!--  隐藏的结算币种ID-->
				<input type="hidden" name="accountCurrency" id="_eoss_sales_accountCurrencyId"/>
				<!--  隐藏的发票类型ID-->
				<input type="hidden" name="invoiceType" id="_eoss_sales_invoiceTypeId"/>
				<!--  隐藏的收款方式ID-->
				<input type="hidden" name="receiveWay" id="_eoss_sales_receiveWayId"/>
				<!--  隐藏的客户ID-->
				<input type="hidden" name="customerId" id="_eoss_sales_customerId"/>
				<!--  隐藏的客户编码-->
				<input type="hidden" name="customerInfoCode" id="_eoss_sales_customerInfoCode"/>
				<!--  隐藏的附件ID-->
				<input type='hidden' name='attachIds' id='attachIds'/>
				<!--  保存为草稿还是提交的标志位-->
				<input type='hidden' name='contractState' id='isSubmit' />
				<input type='hidden' name='customerContact' id='customerContact' value="${model.customerContact }" />
				<!--隐藏字段end -->
			<!-- 小标题start -->
			<div class="row-inline-first" >
				<h5 class="form-section-title">合同基本信息</h5>

			</div>
			<!-- 小标题end -->
			<!-- 1行start -->
			<div class=" row-inline" >
				<div class=''>
					<label class='row-label' for='contractName'  ><span style="color:red;">*</span>合同名称：</label>
					<div class='row-input'>
						<input class="row-input-oneColumn" type='text' id='contractName' name='contractName' placeholder='请输入合同名称' required data-content="请输入合同名称"/>
						<span class="help-block"></span>
					</div>
				</div>
			</div>
			<!-- 1行end -->
			<!-- 2行start -->
			
			<div class=" row-inline" >
			
			</div>
			 <!-- 
			 <div class=" row-inline" >
				<div class=''>
					<label class='row-label' for='contractName' >合同简称：</label>
					<div class='row-input'>
						<input type='text' id='contractShortName' name='contractShortName' placeholder='请输入合同简称'/>
						<span class="help-block"></span>
					</div>
				</div>
			</div>
			-->
			<div class=" row-inline" >
	
				<div class='row-column'>
					<label class='row-label' for='contractName' ><span style="color:red;">*</span>合同类型：</label>
					<div class='row-input' id="_eoss_sales_contractType"></div>
				</div>
				<div class='row-column'>
					<label class='row-label' for='contractName' ><span style="color:red;">*</span>合同金额：</label>
					<div class='row-input'>
						<div class="input-append">
							<input type='text'  class='row-input-addTip' id='contractAmount' name='contractAmount'  placeholder='请输入合同金额' pattern='number' required data-content="请输入合同金额"/>
							<span class="add-on">元</span>
						</div>
						<span class="help-block"></span>
					</div>
				</div>
				<div class='row-column'>
					<label class='row-label' for='invoiceType' ><span style="color:red;">*</span>发票类型：</label>
					<div class='row-input' id="_eoss_sales_invoiceType"></div>
				</div>
			</div>

			<!-- 2行end -->
			<!-- 3行start -->
			<div class=" row-inline" >

				<div class='row-column'>
					<label class='row-label' for='receiveWay' ><span style="color:red;">*</span>收款方式：</label>
					<div class='row-input' id="_eoss_sales_receiveWay"></div>
				</div>
				<div class='row-column'>
					<label class='row-label' for='accountCurrency' ><span style="color:red;">*</span>结算币种：</label>
					<div class='row-input' id="_eoss_sales_accountCurrency"></div>
				</div>
			</div>
	
		
			<!-- 分割行start -->
			<div class=" row-inline" >
				<hr size=1 class="dashed-inline">
			</div>
			<!-- 分割行end -->
			<!-- 小标题start -->
			<div class=" row-inline" >
				<h5 class="form-section-title">客户信息（甲方）
				</h5>
			</div>
			
			<!-- 小标题end -->
			<!-- 5行start -->
			<div id="_customer_result">
				<div class=" row-inline" >
					<div  style="float:left;width:826px;">
						<label class='row-label' for='contractName' ><span style="color:red;">*</span>客户选择：</label>
						<div class='row-input-halfColumn'>
							<div id="_select_customer_div" style="width:734px;">
							</div>
						</div>
					</div>
				 <div style="float:left;">
						<a id='_sino_eoss_sales_customerInfo_select'  role="button" href='#_sino_eoss_sales_products_import_page' role='button' target='_self'  data-toggle='modal' aria-hidden="true" class="btn btn-info"><i class="icon-search"></i>选择客户</a>
					</div>
				</div>
				
				<div class=" row-inline"  style="margin-top:10px;">
				<div class='row-column'>
					<label class='row-label'  style="font-weight:bold;"><span style="color:red;">*</span>合同编码：</label>
					<div class='row-input'>
						<input type='text' id='contractCode' name='contractCode' readonly="readonly"  placeholder='选中客户后自动生成' />
					</div>
				</div>
					<div class="row-column" style="float:left;width:220px;margin-top: 5px;">
						<label class='' for='contractName' >联&nbsp;&nbsp;系&nbsp;&nbsp;人：<span id="_customerContacts"></span></label>
					</div>
				    <div class="row-column"  style="float:left;width:200px;margin-top: 5px;">
						<label class='' for='contractName' >手机号码：<span id="_customerContactsPhone"></span></label>
					</div>
				</div>
			</div>
			
			<!-- 6行end -->
			<!-- 分割行start -->
			<div class=" row-inline" >
				<hr size=1 class="dashed-inline">
			</div>
			<!-- 分割行end -->
			<!-- 小标题start -->
			<div class=" row-inline" >
				<h5 class="form-section-title">公司信息(乙方)</h5>
			</div>
			<!-- 小标题end -->
			<!-- 7行start -->
			<div class=" row-inline" >
				<div class='' style="color:#888888;">
					<label ><span style="font-weight: bold;">公司名称：</span><span>${companyName }</span></label>
					<label style="margin-left:10px;"><span style="font-weight: bold;">开户银行：</span><span>${bankName }</span></label>
					<label style="margin-left:10px;"><span style="font-weight: bold;">银行账户：</span><span>${account }</span></label>
					<label style="margin-left:30px;"><span style="font-weight: bold;">客户经理：</span><span>${systemUser.staffName}</span></label>
				</div>
			</div>
			
		<!-- 此处根据合同类型，变更合同内容 -->	
		<div id="contractContent" style="float:left;">
			<div class=" row-inline" >
				<hr size=1 class="dashed-inline">
			</div>
			
			<div class=" row-inline" >
				<h5 class="form-section-title" id="service_h4">合同执行信息</h5>
			</div>
			<div class=" row-inline">
				<div class='row-column' id="serviceStartDate_div">
					<label class='row-label' for='accountCurrency' ><span style="color:red;">*</span>服务开始时间：</label>
					<div class='row-input'>
						<div class="input-append date">
				  		    <input data-format="yyyy-MM-dd" style="width:150px;" type="text" id="serviceStartDate_input" name="serviceStartDate" required data-content="请输入服务开始时间"></input>
						    <span class="add-on">
						      <i data-time-icon="icon-time" data-date-icon="icon-calendar">
						      </i>
							</span>
						  </div>
					</div>
				</div>
				<div class='row-column' id="serviceEndDate_div">
					<label class='row-label' for='invoiceType' ><span style="color:red;">*</span>服务结束时间：</label>
					<div class='row-input'>
						<div class="input-append date">
				  		    <input data-format="yyyy-MM-dd" style="width:150px;" type="text" id="serviceEndDate_input" name="serviceEndDate" required data-content="请输入服务结束时间"></input>
						    <span class="add-on">
						      <i data-time-icon="icon-time" data-date-icon="icon-calendar">
						      </i>
							</span>
						  </div>
					</div>
				</div>
			</div>
			
			<div class=" row-inline"  style="margin-top:40px;"></div>
			
			<div class="row-inline" id="estimateProfit_div">
				<!-- <div class='row-column' id="">
					<label class='row-label' for='accountCurrency' ><span style="color:red;">*</span>合同生效时间：</label>
					<div class='row-input'>
						<div class="input-append date">
				  		    <input data-format="yyyy-MM-dd" style="width:150px;" type="text" id="salesStartDate_input" name="salesStartDate" required data-content="请输入合同生效时间"></input>
						    <span class="add-on">
						      <i data-time-icon="icon-time" data-date-icon="icon-calendar">
						      </i>
							</span>
						  </div>
					</div>
				</div> -->
				<div class='row-column' id="">
					<label class='row-label' for='invoiceType' ><span style="color:red;">*</span>合同终止时间：</label>
					<div class='row-input'>
						<div class="input-append date">
				  		    <input data-format="yyyy-MM-dd" style="width:150px;" type="text" id="sales_EndDate_input" name="salesEndDate" required data-content="请输入合同终止时间"></input>
						    <span class="add-on">
						      <i data-time-icon="icon-time" data-date-icon="icon-calendar">
						      </i>
							</span>
						  </div>
					</div>
				</div>
				<div class='row-column'>
					<label class='row-label' for='receiveWay' ><span style="color:red;">*</span>预估毛利率：</label>
					<div class='row-input'>
						<input type="text" name="estimateProfit" value="10%" required data-content="请输入预估毛利率" style="width:50px;"><span>&nbsp;例如：5%，10%</span>
					</div>
				</div>
			</div>
			<div class=" row-inline"  style="margin-top:10px;">
			<div class='row-column'>
					<label class='row-label' for='receiveWay' >预估毛利说明：</label>
					<div class='row-input'>
						<input type="text" name="estimateProfitDesc"  style="width:800px;" placeholder="请输入估算毛利的依据和说明">
					</div>
				</div>
			</div>
			
			<div class="row-inline product_handler" >
				<hr size=1 class="dashed-inline">
			</div>
			<!-- 分割行end -->
			<!-- 小标题start -->
			<div class="row-inline product_handler" >
				<h5 class="form-section-title">合同清单
				<div style="float:right;width:15%;" class="product_handler"><a id="_sales_import_excel" href='${ctx }/sales/doc/projectProductTemplate.xls' target='_self' style="color:#A3A3A3;"><i class="icon-download-alt"></i>导入模板下载</a></div>
				</h5>
				<div class="row-inline">
					<label class='row-label' for='receiveWay' ><span style="color:red;">*</span>期望到货日期：</label>
					<div class='row-input'>
							<div class="input-append date">
				  		    <input data-format="yyyy-MM-dd" id="_hopeArriveTime" style="width:150px;" type="text" name="hopeArriveTime" required data-content="请输入期望到货时间"></input>
						    <span class="add-on">
						      <i data-time-icon="icon-time" data-date-icon="icon-calendar">
						      </i>
							</span>
						  </div>
					</div>
					<label class='row-label' style="margin-left: 10px;"><span style="color:red;"></span>期望到货地点：</label>
					<div class='row-input'>
						<input type="text" name="hopeArrivePlace" style="width:475px;" placeholder='请输入期望到货地点' />
					</div>
				</div>
				
				<div class=" row-inline"  style="margin-top:40px;"></div>
				<div class=""  style="float:left;">是否需要备品备件：<input type="radio" value="1" name="needother"  style="margin-top:0px;"/>&nbsp; 是   &nbsp; &nbsp;<input type="radio" value="0" name="needother" style="margin-top:0px;" checked/>&nbsp; 否</div>
				<div class="products_add_btn">

					<a id='_sino_eoss_sales_products_add' role="button" class="btn btn-success"><i class="icon-plus"></i>添加</a>
					&nbsp;&nbsp;&nbsp;
					<a id='_sino_eoss_sales_products_import'  role="button" href='#_sino_eoss_sales_products_import_page' role='button' target='_self'  data-toggle='modal' aria-hidden="true" class="btn btn-success"><i class="icon-table"></i>导入</a>
					&nbsp;&nbsp;&nbsp;
					<a id='_sino_eoss_sales_products_relate'  role="button" href='#_sino_eoss_sales_products_import_page' role='button' target='_self'  data-toggle='modal' aria-hidden="true" class="btn btn-success"><i class="icon-link"></i>关联备货合同</a>
				</div>
			</div>
			<!-- 小标题end -->
			<!-- 9行start -->	
			<div class=" row-inline product_handler" >
				<div class='table-tile-width'>
					<table id="_sino_eoss_sales_contract_products_table" class="table  table-bordered table-striped">
					  <thead>
					    <tr>
					      <th style="width:6%;">序号</th>
					      <th style="width:9%;" id="type_th">产品类型</th>
					      <th style="width:9%;" id="partner_th">产品厂商</th>
					      <th style="width:9%;" id="product_th">产品型号</th>
					      <th style="width:12%;">服务开始时间</th>
					      <th style="width:12%;">服务结束时间</th>
					      <th style="width:6%;">数量</th>
					      <th style="width:9%;">单价</th>
					      <th style="width:12%;">合计</th>
					      <th style="width:20%;">操作</th>
					    </tr>
					  </thead>
					  <tbody>
					  </tbody>
					</table>
					<table id="_sino_eoss_sales_contract_maproducts_table" style="display:none;" class="table  table-bordered table-striped">
					  <thead>
					    <tr>
					      <th style="width:6%;">序号</th>
					      <th style="width:9%;">产品厂商</th>
					      <th style="width:9%;">产品型号</th>
					      <th style="width:8%;">序列号</th>
					      <th style="width:9%;">服务期(月)</th>
					      <th style="width:11%;">服务开始日期</th>
					      <th style="width:11%;">服务结束日期</th>
					      <th style="width:9%;">销售价格</th>
					      <th style="width:9%;">销售数量</th>
					      <th style="width:9%;">销售总价</th>
					      <th style="width:9%;">设备属地</th>
					    </tr>
					  </thead>
					  <tbody>
					  </tbody>
					</table>
				</div>
			</div>
				<div class="product_handler" style="float:right;margin-right:40px;">合计:<span id="_product_total"  >0</span>元</div>
			<!-- 9行end -->	
			<!-- 分割行start -->
			<div class="row-inline collection_plan" >
				<hr size=1 class="dashed-inline">
			</div>
			<!-- 分割行end -->
			<!-- 小标题start -->
			<div class="row-inline collection_plan" >
				<h5 class="form-section-title">收款计划</h5>
			</div>
			<!-- 小标题end -->
			<!-- 10行start -->
			<div class="row-inline collection_plan">
				<div class='table-tile-width'>
					<div id="editTable"></div>
				</div>
			</div>	
			<!-- 10行end -->
		</div>
			
			<!-- 分割行start -->
			<div class=" row-inline" >
				<hr size=1 class="dashed-inline">
			</div>
			<!-- 分割行end -->
			<!-- 小标题start -->
			<div class="row-inline" >
				<h5 class="form-section-title" style="width:100px;float:left;">上传合同附件</h5>
				<div style="float:left;margin-top:11px;color:red;">（提示：必须包含合同附件、毛利预估表）</div>
			</div>
			
			<!-- 小标题end -->
			<!-- 11行start -->
			<div class=" row-inline" >
				<div id="uplaodfile"></div>
			</div>	
			<!-- 11行end -->								
			<br class='float-clear' />
			
			<div class='row-inline'>
				<label class='row-label' for='contractRemark' placeholder='请输入合同备注'><span style="color:red;"></span>备注：</label>
				<div class='row-input'>
					<div class="input-append">
						<textarea style="width:200%;" rows="2" cols="76" id='contractRemark' name='contractRemark'  placeholder='请输入合同备注'></textarea>
					</div>
				</div>
			</div>
			
			<br class='float-clear' />
			</form>
			<!-- END FORM-->
			<!--设备添加弹出对话框 -------start-->
			 <div id="dailogs1" class="modal hide fade" role="dialog" tabIndex="-1" style="width:800px;height:460px;margin-left:-400px">
		   		<div class="modal-header">
				   <h3 id="dtitle"></h3>
				   <div id="_product_alertMsg"></div>
		  		</div>
				<div class="modal-body" style="height:70%">
					<div id="dialogbody" ></div>
				</div>
				<div class="modal-footer">
					<button id="_do_cancel"  class="btn" data-dismiss="modal" aria-hidden="true" >取消</button >
					<a id="dsave"  class="btn btn-primary">保存</a>
				</div>
			</div>
			<!--导入 产品div -------end-->
			<div id="_sino_eoss_sales_products_import_div"></div>
			<!--设备添加弹出对话框 -------end-->
			<div class="form-actions">
				<div style='text-align:center;'>
					<a id='_sino_eoss_sales_contract_add' role="button" class="btn">保存草稿</a>
					&nbsp;&nbsp;&nbsp;&nbsp;
					<a id='_sino_eoss_sales_contract_submit' role="button" class="btn btn-success"><i class="icon-ok"></i>提交</a>
					&nbsp;&nbsp;&nbsp;&nbsp;
					<button id='_sino_eoss_sales_contract_back' type="button" class="btn">取消</button>
				</div>		
			</div>  
		</div>

	<script language="javascript">
		var form = ${form};  //从controller层接收收款计划data_grid数据模型
		seajs.use('js/page/sales/contract/addSalesContract',function(addSalesContract){
			addSalesContract.init();
		});    
	</script>
</body>
</html>
