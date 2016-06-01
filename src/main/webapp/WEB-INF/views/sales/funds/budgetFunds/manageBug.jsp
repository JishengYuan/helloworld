<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/global.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="${ctx}/skin/default/room.css" type="text/css" />
<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/internetTable.css" type="text/css"></link>
<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/processList.css" type="text/css"></link>

<link rel="stylesheet"	href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formstyle.css"	type="text/css"></link>
<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/dialog.css" type="text/css"></link>
<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/tip.css" type="text/css" ></link>

<title>商务成本管理</title>
<style type="text/css">
.mb10 {
    margin-top:5px;
    margin-bottom: 10px;
}
.rightmenu li {
font-size: 12px;
    float: left;
    line-height: 25px;
    margin: 0 20px 0 0;
}
.rightmenu li a {
    padding: 2px 10px;
}
.rightmenu li a:hover {
    background: none repeat scroll 0 0 #4D7DCF;
    color: #FFFFFF;
}
.rightmenu li.sel a {
    background: none repeat scroll 0 0 #4D7DCF;
    border: 2px solid #4D7DCF;
    color: #FFFFFF;
}
.blue02{
color:#1874CD;
}
.advancedSearch .advancedSearch_btn {
    border-top: 0 dashed #ccc;
    height: 30px;
    margin-bottom: 10px;
    margin-top: 0;
    padding-top: 0px;
}
.advancedSearch {
    padding: 5px 0;
}
hr {
    -moz-border-bottom-colors: none;
    -moz-border-left-colors: none;
    -moz-border-right-colors: none;
    -moz-border-top-colors: none;
    border-color: #1f9dd7;
    border-image: none;
    border-style: solid none;
    border-width: 1px 0;
    margin: 5px 0;
}
</style>
</head>
<body>
		 
	<div class="ultrapower-table-box">
	    <span class="title" style="font-size:14px; font-weight: normal;font-family:宋体;">合同管理-->合同预算</span>
	</div>
	<div>
		<!-- <div><a id="week" class="sel statist" style="font-size: 12px; float: left; padding: 2px 6px;" href="#">周</a></div>
		<div><a id="month" class="button statist" style="font-size: 12px; float: left; padding: 2px 6px;" href="#">月</a></div>
		<div><a id="total" class="button statist" style="font-size: 12px; float: left; padding: 2px 6px;" href="#">总</a></div> -->
		<div id="costControl" class="rightmenu mb10 clearfix">
			<ul>
	            	<li id='week'  class="sel statist"><a href="javascript:void(0)">周</a></li>
	            	<li id='month' class="button statist"><a href="javascript:void(0)">月</a></li>
	            	<li id='total' class="button statist" ><a href="javascript:void(0)">总</a></li>
	        </ul>
		</div>
		<div id="week_statistics"></div>
		<!-- <div id="month_statistics">月-----</div>
		<div id="total_statistics">总-----</div> -->
		<hr width="100%" style=""/>
	</div>
	<div id="uicTable"></div>
	<div style="float:left;width:960px">
	<c:if test="${sales!='[]' }">
			<div>温馨提示：</div>
			<div>近期需要回款的合同:
				<c:forEach var="sales" items="${sales}" varStatus="status">
					${sales.ContractName},
				</c:forEach>
			</div>
		</c:if>
		<br>
				<a id="addContract_btn" class="btn btn-success" title="只能选中一个合同" style="font-size:12px;" href="#">
				<i class="icon-plus"></i>
				 预测回款
				</a>
				<a id="removeContract_btn" class="btn btn-danger" title="只能选中一个合同" style="font-size:12px;" href="#">
				<i class="icon-plus"></i>
				预测发票
				</a>
			</div>
		
	<div id="hisRecieveTable"></div>
	<div id="hisInvoiceTable"></div>
	
	<div id="_sino_eoss_sales_products_import_div"></div>
	<!--添加弹出对话框 -------start-->
			 <div id="dailogs1" class="modal hide fade" role="dialog" tabIndex="-1" style="width:800px;height:460px;margin-left:-400px">
		   		<div class="modal-header">
				   <h3 id="dtitle"></h3>
				   <div id="_add_recieve"></div>
		  		</div>
				<div class="modal-body" style="height:70%">
					<div id="dialogbody" ></div>
				</div>
				<div class="modal-footer">
					<button id="_do_cancel"  class="btn" data-dismiss="modal" aria-hidden="true" >取消</button >
					<a id="save_recieve"  class="btn btn-primary">保存</a>
				</div>
			</div>
	<!--添加弹出对话框 -------end-->
		<!--添加弹出对话框 -------start-->
			 <div id="dailogs2" class="modal hide fade" role="dialog" tabIndex="-1" style="width:800px;height:460px;margin-left:-400px">
		   		<div class="modal-header">
				   <h3 id="dtitle_invoice"></h3>
				   <div id="_add_invoice"></div>
		  		</div>
				<div class="modal-body" style="height:70%">
					<div id="dialogbody_invoice" ></div>
				</div>
				<div class="modal-footer">
					<button id="_do_cancel"  class="btn" data-dismiss="modal" aria-hidden="true" >取消</button >
					<a id="save_invoice"  class="btn btn-primary">保存</a>
				</div>
			</div>
	<!--添加弹出对话框 -------end-->
<script>
	seajs.use("js/page/sales/funds/budgetFunds/manageBug",function(manageBug){
		manageBug.init();
	});
	
</script>
	
</body>


</html>