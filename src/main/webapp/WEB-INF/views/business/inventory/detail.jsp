<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/global.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="${ctx}/js/plugins/seajs/sea.js" id="seajsnode" type="text/javascript"></script>
<script src="${ctx}/js/plugins/seajs/config.js" type="text/javascript"></script>
<%@ include file="/common/include-base-boostrap-styles.jsp" %>
<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/sinobridge/pink/sino_form_grid.css" type="text/css" />
<link href="${ctx}/skin/default/sales/sale.css" rel="stylesheet"/>
<link rel="stylesheet"href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/internetTable.css"type="text/css"></link>
<link rel="stylesheet"href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/processList.css"type="text/css"></link>
<title>设备详情</title>
<style type="text/css">
.center {
    margin-left: auto;
    margin-right: auto;
    position: relative;
    width: 100%;
}
.sino_table_body tbody tr{
	border-bottom: 1px solid #D7D7D7;
}
.hstyle{
	font-size:16px;
	font:宋体;
	margin-bottom:10px;
}
table {
    border-collapse: separate;
    border-spacing: 0;
}
.border01 {
    border: 0px solid #d7d7d7;
}
.handprocess_order {
    border-bottom: 0px solid #d7d7d7;
    padding: 10px;
}
.handprocess_order li.li_form {
line-height:20px;
}
.handprocess_order ul{
margin:0;
}
.handprocess_order .li_form label.editableLabel {
margin-bottom:0px;
color: #888;
}
</style>
</head>
<body>
	<div class="salecontent">
		<div class="top">
			<div class="caption">
				<i class="icon-reorder"></i>&nbsp;&nbsp;&nbsp;设备信息
			</div>
			<div class="tools">
				<!--a href="javascript:;" class="reload"><i class="icon-repeat"></i>返回</a-->
			</div>
		</div>
	</div>
	<div class="portlet-body form">
	<div class="border01 handprocess" style="">
		<form>
			<input type="hidden" value="${model.id }" id="_productId">
			<div class="handprocess_order " style="margin-top:50px;">
				<ul class="clearfix" style="position: relative;">
					<li id="field_userName" class="li_form" style="width: 300px;">
						<label for="userName" class="editableLabel">厂商：</label>
							${model.partner }
					</li>
					<li id="field_userName" class="li_form" style="width: 300px;">
						<label for="userName" class="editableLabel">型号：</label> 
						${model.productNo }
					</li>
					<li id="field_userName" class="li_form" style="width: 300px;">
						<label for="userName" class="editableLabel">序列号：</label>
						${model.seriesNo }
					</li>
				</ul>
				<ul class="clearfix" style="position: relative;">
					<li id="field_userName" class="li_form" style="width: 300px;">
						<label for="userName" class="editableLabel">成本：</label> 
						<fmt:formatNumber value="${model.cost }" type="currency" currencySymbol="￥"/>
					</li>
					<li id="field_userName" class="li_form" style="width: 300px;">
						<label for="userName" class="editableLabel">租金：</label> 
						<fmt:formatNumber value="${model.rent }" type="currency" currencySymbol="￥"/>
					</li>
					<li id="field_userName" class="li_form" style="width: 300px;">
						<label for="userName" class="editableLabel">数量：</label> 
						${model.number }
					</li>
				</ul>
				<ul class="clearfix" style="position: relative;">
					<li id="field_userName" class="li_form" style="width: 300px;">
						<label for="userName" class="editableLabel">新旧：</label> 
						${model.appearance }
					</li>
					<li id="field_userName" class="li_form" style="width: 300px;">
						<label for="userName" class="editableLabel">状态：</label> 
						<c:if test="${model.state == '0'}">
							在库
						</c:if>
						<c:if test="${model.state == '1'}">
							待审批
						</c:if>
						<c:if test="${model.state == '2'}">
							已出库
						</c:if>
						<c:if test="${model.state == '3'}">
							待归还
						</c:if>
					</li>
					<li id="field_userName" class="li_form" style="width: 300px;">
						<label for="userName" class="editableLabel">位置：</label> 
						${model.location }
					</li>
				</ul>
				<ul class="clearfix" style="position: relative;">
					<li id="field_userName" class="li_form" style="">
						<label for="userName" class="editableLabel">备注：</label> 
						${model.remark }
					</li>
				</ul>
				<h4>设备借出归还记录</h4>
					<div id="uicTable"></div>
			</div>
		</div>
		</form>
	</div>
	<a class="btn btn-success" aria-hidden="true" data-toggle="modal" target="_self" href="#_sino_eoss_inventory_import_page" role="button" id="_sino_eoss_inventory_import" style="display:none;"><i class="icon-table"></i>导入</a>
	<div id="_sino_eoss_inventory_products_import_div"></div>
	<!-- editTable -->
       <div id="dailogs1" class="modal hide fade" role="dialog" tabIndex="-1" style="width:580px;height: 210px;">
		   <div class="modal-header">
		         <a type="button" class="close" data-dismiss="modal" aria-hidden="true">×</a>
		         <h3 id="dtitle"></h3>
  		   </div>
		    <div class="modal-body" style="width:750px;">
			     <div id="dialogbody" ></div>
		    </div>
		    <div id="bottom_button" class="modal-footer" style="text-align: center;">
        		<input id="ok_Contract_Add" type="button" value="确定" class="btn btn-success"/>
    		</div>
        </div>
	<script language="javascript">
		seajs.use('js/page/business/inventory/detail', function(detail) {
			detail.init();
		});
	</script>
</body>
</html>