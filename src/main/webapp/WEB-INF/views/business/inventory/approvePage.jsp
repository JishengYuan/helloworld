<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/global.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/sinobridge/pink/sino_form_grid.css" type="text/css" />
<link href="${ctx}/skin/default/sales/sale.css" rel="stylesheet"/>
<link rel="stylesheet"href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/internetTable.css"type="text/css"></link>
<link rel="stylesheet"href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/processList.css"type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/bootstrap/css/bootstrap-datetimepicker.min.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/treetable/css/jquery.treetable.css" />
	
	<link rel="stylesheet" href="${ctx}/skin/default/room.css" type="text/css" />
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formSelect.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formTree.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/tip.css" type="text/css" ></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/user-selection.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formSearchTip.css" type="text/css" ></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formstyle.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/dialog.css" type="text/css"></link>



<title>备货审批</title>
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
._fee_head_title{
	color:#4d7dcf;
}
.title {
    background: none repeat scroll 0 0 #fefefe;
    border-bottom: 2px solid #4d7dcf;
    height: 30px;
    line-height: 30px;
    text-indent:10px;
    font-weight:normal;
    width:50%;
}
</style>
</head>
<body>
	<span class="title _fee_head_title" style="float:left;width:100%;margin-bottom:10px;margin-top:10px;"><h5>公司备件->出库审批页</h5></span>
	<div class="portlet-body form" style="float:left;padding:2px;">
	<div class="border01 handprocess" style="margin-top:-60px;">
		<form id='_sino_eoss_inventory_form' name='_sino_eoss_inventory_form' method="post">
			<input name="outId" id="outId" type="hidden" value="${model.id }"  />
			<input name="projectName" id="projectName" type="hidden" value="${model.projectName }"  />
			<input name="approveState" id="approveState" type="hidden" value="${approveState }"  />
			<div class="handprocess_order " style="margin-top:50px;">
			<h3>基本信息</h3>
				<ul class="clearfix" style="position: relative;">
					<li id="field_userName" class="li_form" style="width: 300px;">
						<label for="userName" class="editableLabel">申&nbsp; 请&nbsp;人：</label>
							${model.customerManageName }
					</li>
					<li id="field_userName" class="li_form" style="width: 300px;">
						<label for="userName" class="editableLabel">申请编码：</label> 
						${model.serialNum }
					</li>
					<li id="field_userName" class="li_form" style="width: 300px;">
						<label for="userName" class="editableLabel">申请时间：</label>
						<fmt:formatDate value="${model.borrowTime }" type="both" pattern="yyyy-MM-dd"/>
					</li>
				</ul>
				<ul class="clearfix" style="position: relative;">
					<li id="field_userName" class="li_form" style="width: 300px;">
						<label for="userName" class="editableLabel">归还时间：</label>
						<fmt:formatDate value="${model.dueTime }" type="both" pattern="yyyy-MM-dd"/>
							
					</li>
					<li id="field_userName" class="li_form" style="width: 500px;">
						<label for="userName" class="editableLabel">项目名称：</label> 
						${model.projectName }
<!-- 						<span id="projectNameSpan"></span> -->
					</li>
					<li id="field_userName" class="li_form" style="width: 300px;">
						<label for="userName" class="editableLabel">借货说明：</label>
						${model.remark }
					</li>
				</ul>
				<h3>用户信息</h3>
				<ul class="clearfix">
					<li id="field_userName" class="li_form" style="width: 300px;">
						<label for="userName" class="editableLabel">用户单位：</label>
						${model.userDept }
					</li>
					<li id="field_userName" class="li_form" style="width: 300px;">
						<label for="userName" class="editableLabel">用户联系人：</label>
						${model.userContact }
					</li>
					<li id="field_userName" class="li_form" style="width: 300px;">
						<label for="userName" class="editableLabel">联系电话：</label>
						${model.phone }
					</li>
				</ul>
				<ul class="clearfix">
				
					<li id="field_userName" class="li_form" style="width: 300px;">
						<label for="userName" class="editableLabel">邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;编：</label>
						${model.zipCode }
					</li>
					<li id="field_userName" class="li_form" style="width: 400px;">
						<label for="userName" class="editableLabel">通讯地址：</label>
						${model.address }
					</li>
				</ul>
				
				
				<table style="margin-top:20px;" id="taskTable" cellpadding="0" cellspacing="0" border="0" class="table table-striped table-bordered table-hover">
					<thead>
						<tr>
							<th width="25%">厂商</th>
							<th width="25%">型号</th>
							<th width="25%">数量</th>
							<c:if test="${approveState==2 }">
								<th width="25%">序列号</th>
							</c:if>
						</tr>
					</thead>
				<tbody id="taskTbody">
					<!-- <c:forEach var="product" items="${productList}" varStatus="status">
						<tr>
							<td>${product.partner }</td>
							<td>${product.productNo }</td>
							<td>${product.seriesNo }</td>
							<td>${product.appearance }</td>
						</tr>
					</c:forEach>-->
				</tbody> 
				</table>
				
				
				<div class="form-actions">
					<div style="text-align:center;" style="margin-bottom:-5px;">
						<a class="btn btn-success" role="button" id="_sino_eoss_sales_contract_submit"><i class="icon-ok"></i>确定</a>
						&nbsp;&nbsp;&nbsp;&nbsp;
						<button class="btn" type="button" id="_sino_eoss_sales_contract_back">返回</button>
					</div>		
				</div>
			</div>
		</div>
		</form>
	</div>
	<script language="javascript">
		seajs.use('js/page/business/inventory/approvePage', function(approvePage) {
			approvePage.init();
		});
	</script>
</body>
</html>