<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <%@ include file="/common/global.jsp" %>
<head>
    <title>投标</title>
    <%@ include file="/common/include-base-boostrap-styles.jsp" %>
	<link href="${ctx}/skin/default/sales/sale.css" rel="stylesheet"/>
	<link href="${ctx}/js/plugins/select2/css/select2.css" rel="stylesheet"/>
	<link rel="stylesheet" href="${ctx}/js/plugins/bootstrap/css/bootstrap-datetimepicker.min.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/sinobridge/pink/sino_form_grid.css" type="text/css" />
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formSelect.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formSearchTip.css" type="text/css" ></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formstyle.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/dialog.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/tip.css" type="text/css" ></link>
	
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/tip.css" type="text/css" ></link>
	<link rel="stylesheet" href="${ctx}/skin/default/editgrid/editgrid.css" type="text/css" />
	<link type="text/css" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/jquery-ui-1.8.17.custom.css" rel="stylesheet">
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
<style type="text/css">
	.row-input{
		width: 180px;
		height:28px"
	}

.row-input-oneColumn {
    width: 260px;
}
.row-input-towColumn {
    width: 660px;
}
.select2-container .select2-choice {
    border-radius: 4px;
    color: #555555;
    font-size: 14px;
    height: 20px;
    padding: 4px 6px;
    width: 180px;
    background-color: #ffffff;
    border: 1px solid #cccccc;
    box-shadow: 0 1px 1px rgba(0, 0, 0, 0.075) inset;
    transition: border 0.2s linear 0s, box-shadow 0.2s linear 0s;
    display: inline-block;
    margin-bottom: 0;
    vertical-align: middle;
    background-image: none;
}
.handprocess_order li.li_form {
    display: inline-block;
    float: left;
    font-size: 14px;
    line-height: 25px;
    padding: 3px 0;
    width: 300px;
}
body {
    font-size: 12px;
}
.table {
    margin-bottom: 20px;
    width: 100%;
    margin-left: 0px;
}

.handprocess_order {
    padding: 0px;
    border-bottom: 0px solid #d7d7d7;
}
.handprocess_order li.li_form label {
    color: #888;
    display: inline-block;
    float: left;
    text-align: right;
    width: 98px;
}
.handprocess_order .li_form label.editableLabel {
    color: #eeb422;
}

.handprocess_order .li_form label.editableLabel2 {
    color: #000000;
    margin-bottom: 0;
    margin-top: 3px;
}
.selectDiv a.uicSelectMore {
    background: url("${ctx}/js/plugins/uic/style/excellenceblue/uic/images/ultraselect/ms_ico2.png") no-repeat scroll 1px 0 rgba(0, 0, 0, 0);
    display: block;
    height: 23px;
    position: absolute;
    right: 0;
    top: 0;
    width: 25px;
    margin-right:-3px;
}
.selectDiv .uicSelectInp {
    background: none repeat scroll 0 0 #FFFFFF;
    border: 1px solid #CCCCCC;
    cursor: pointer;
    height: 13px;
    position: relative;
    text-align: left;
    vertical-align: middle;
}
.selectDiv .uicSelectData {
    background: #fff none repeat scroll 0 0;
    border-radius: 0;
    box-shadow: none;
    min-height: 50px;
    position: absolute;
    z-index: 9999;
    height: 200px;
}
</style>
    <script src="${ctx}/js/plugins/seajs/sea.js" id="seajsnode" type="text/javascript"></script>
	<script src="${ctx}/js/plugins/seajs/config.js" type="text/javascript"></script>
	<script src="${ctx}/js/page/sales/contract/makePy.js" type="text/javascript"></script>
</head>
<body>
	<!-- 遮盖层div -->
	<div id="_progress1" style="display: none; top: 0px; left: 0px; width: 100%; margin-left: 0px; height: 100%; margin-top: 0px; z-index: 1000; opacity: 0.7; text-align: center; background-color: rgb(204, 204, 204); position: fixed;">
	</div>
	<div id="_progress2" style="display: none; width: 233px; height: 89px; top: 45%; left: 45%; position: absolute; z-index: 1001; line-height: 23px; text-align: center;">
		<div style="top: 50%;position:fixed;">
			<div style="display: block;">数据加载中，请稍等...</div>
		</div>
	</div>
<div class="salecontent">
		<div class="top">
			<div class="caption">
				<i class="icon-reorder"></i>&nbsp;&nbsp;&nbsp;修改投标信息
			</div>
		</div>
	</div>	
	<!--表单、底部按钮 -->
	<div class="portlet-body form">
	<form  id="_sino_eoss_cuotomer_addform" class='form-horizontal' method="post">
	<!--隐藏字段start -->
	<input type="hidden" id="systemUser" value="${systemUser}" name="systemUser" />
	<input type="hidden" id="taskId" name="taskId" value="${param.taskId }"/>
	<input type="hidden" id="flowStep" name="flowStep" value="${param.flowStep }"/>
	<!--  隐藏的客户ID-->
	<input type="hidden" name="cusIndustryId" id="cusIndustryId" value="${model.cusIndustryId}"/>
	<!--  隐藏的客户ID-->
	<input type="hidden" name="cusCustomerId" id="cusCustomerId" value="${model.cusCustomerId}"/>
	<input type="hidden" name="id" id="id" value="${model.id}"/>
	<input type='hidden' name='tableDataSize' id='tableDataSize' />
	<input type='hidden' name='tableDataCertificate' id='tableDataCertificate' />
	<input type='hidden' name='tableDataQualification' id='tableDataQualification' />
	<input type='hidden' name='tableDataChapter' id='tableDataChapter' />
	
	
	<div class="handprocess_order" id="SheetDiv">
	<br>
		<h3>基本信息</h3>
		<ul class="clearfix" >
			<li id="field_userName" class="li_form" style="width:800px">
				<label class="editableLabel"><span style="color:red;">*</span>名称：</label>
				<input type="text" id="applyFundsName" name="applyFundsName" class="row-input-towColumn" value="${model.applyFundsName}" placeholder='请输入名称' required data-content='请输入名称'/>
			</li>
			<li  class="li_form" style="width:771px">
						<label class='editableLabel'  ><span style="color:red;">*</span>客户选择：</label>
						<div class='row-input-halfColumn'>
							<div id="_select_customer_div" style="width:500px;">
							</div>
						</div>
				 <div style="float:right;">
						<a id='_sino_eoss_sales_customerInfo_select'  role="button" href='#_sino_eoss_sales_products_import_page' role='button' target='_self'  data-toggle='modal' aria-hidden="true" class="btn btn-info"><i class="icon-search"></i>选择客户</a>
					</div>
			</li>
		</ul>
		<ul class="clearfix" >
			<li id="field_userName" class="li_form" style="width:400px">
				<label class="editableLabel"><span style="color:red;">*</span>伙伴公司：</label>
				<input type="text" id="partnerCompany" name="partnerCompany" class="row-input-oneColumn" value="${model.partnerCompany}"  placeholder='请输入伙伴公司' required data-content="请输入伙伴公司"/>
			</li>
			<li id="field_userName" class="li_form" style="width:400px">
				<label class="editableLabel"><span style="color:red;">*</span>盈    利：</label>
				<input type="text" id="expectProfit" name="expectProfit" class="row-input-oneColumn" value="${model.expectProfit}"  placeholder='请输入盈    利' required data-content="请输入盈    利"/>
			</li>
		</ul>
		<ul class="clearfix" >
			<li id="field_userName" class="li_form" style="width:800px">
				<label class="editableLabel"><span style="color:red;">*</span>说明：</label>
				<input type="text" id="applyDesc" name="applyDesc" class="row-input-towColumn"  value="${model.applyDesc}"  placeholder='请输入说明' required data-content="请输入说明"/>
			</li>
		</ul>
		<div style="clear:both;"></div>
		<div class=" row-inline" >
				<hr size=1 class="dashed-inline">
		</div>
		</div>
</form>
<div class="handprocess_order" id="SheetDiv">
	  <h3>往来款</h3>
		<div class="row-inline collection_plan">
				<div class='table-tile-width'>
					<div id="editTable1"></div>
				</div>
			</div>	
        <div style="clear:both;"></div>
        <div class=" row-inline" >
				<hr size=1 class="dashed-inline">
		</div>
		

		<h3>章（借出）</h3>
		<div class="row-inline collection_plan">
				<div class='table-tile-width'>
					<div id="editTable2"></div>
				</div>
			</div>	
		<div style="clear:both;"></div>
        <div class=" row-inline" >
				<hr size=1 class="dashed-inline">
		</div>
		

		<h3>资质（借出）</h3>
		<div class="row-inline collection_plan">
				<div class='table-tile-width'>
					<div id="editTable3"></div>
				</div>
			</div>	
		<div style="clear:both;"></div>
        <div class=" row-inline" >
				<hr size=1 class="dashed-inline">
		</div>
		

		<h3>证书</h3>
		<div class="row-inline collection_plan">
				<div class='table-tile-width'>
					<div id="editTable4"></div>
				</div>
			</div>	
		
            <div style="clear:both;"></div>
       </div>     
    </div>
	  <!-- editTable -->
       <div id="dailogs1" class="modal hide fade" role="dialog" tabIndex="-1" style="width:1070px;height: 510px;">
		   <div class="modal-header">
		         <a type="button" class="close" data-dismiss="modal" aria-hidden="true">×</a>
		         <h3 id="dtitle"></h3>
  		   </div>
		    <div class="modal-body" style="width:1070px;">
			     <div id="dialogbody" ></div>
		    </div>
		    <div id="bottom_button" class="modal-footer" style="text-align: center;">
        		<input id="ok_Contract_Add" type="button" value="提交" class="btn btn-success"/>
        		<!-- <input id="back_page" type="button" value="返回" class="btn"/> -->
    		</div>
        </div>
	  
	  <div id="_sino_eoss_sales_products_import_div"></div>
	<!--按钮组-->
    		<div id="bottom_button" class="handprocess_btngroup">
        		<input id="ok_Add" type="button" value="提交"  class="btn btn-success">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    		</div>
     </div>
</div>

 <script language="javascript">
 var form_size = ${form_size};  //从controller层接收收款计划data_grid数据模型
 var form_chapter = ${form_chapter}; 
 var form_fication = ${form_fication}; 
 var form_certificate = ${form_certificate}; 
 seajs.use('js/page/sales/contractFound/editBidding',function(editBidding){
	 editBidding.init();
    });
</script>
</body>
</html>