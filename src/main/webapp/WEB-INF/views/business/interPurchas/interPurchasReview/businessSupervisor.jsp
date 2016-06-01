<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/global.jsp"%>

<html>
<head>
    <title>内部采购</title>
    <%@ include file="/common/include-base-boostrap-styles.jsp" %>
	<link rel="stylesheet" href="${ctx}/skin/default/room.css" type="text/css" />
	<link href="${ctx}/skin/default/sales/sale.css" rel="stylesheet"/>
	<link rel="stylesheet" href="${ctx}/js/plugins/bootstrap/css/bootstrap-datetimepicker.min.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/sinobridge/pink/sino_form_grid.css" type="text/css" />
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formSelect.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formSearchTip.css" type="text/css" ></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formstyle.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/dialog.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/tip.css" type="text/css" ></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/jquery-ui-1.8.17.custom.css" type="text/css" ></link>
	<link rel="stylesheet" href="${ctx}/skin/default/editgrid/editgrid.css" type="text/css" />
<style type="text/css">
	.row-input{
		padding: 0 5px;
		width: 180px;
		height:28px"
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
    color: #000000;
}

</style>
	<script src="${ctx}/js/plugins/seajs/sea.js" id="seajsnode" type="text/javascript"></script>
	<script src="${ctx}/js/plugins/seajs/config.js" type="text/javascript"></script>
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
<br />
<div class="salecontent">
		<div class="top">
			<div class="caption">
				<i class="icon-reorder"></i>&nbsp;&nbsp;&nbsp;办公设备采购审批
			   <span class="tright" >内采编号：${model.purchasCode }</span>
			</div>
			<div class="tools">
				<!--a href="javascript:;" class="reload"><i class="icon-repeat"></i>返回</a-->
			</div>
		</div>
	</div>	
	<!--表单、底部按钮 -->
	<div class="portlet-body form">
			<form id="_eoss_business_interPurchas" novalidate="novalidate" style="" method="post">
				<input type="hidden" name="id" value="${model.id }">
				<input type="hidden" name="purchasStatus" id="purchasStatus" value="${model.purchasStatus}">
				<input type="hidden" name="mark" id="mark" value="${mark}"/>
				<div class="handprocess_order" id="SheetDiv">
		        <h3 class="form-section-title">采购信息</h3>
		<ul class="clearfix" >
			<%-- <li id="field_userName" class="li_form" style="width: 320px;">
				<label  class="editableLabel"><span class="red">*</span>编号：</label>${model.purchasCode }
			</li> --%>
			<li id="field_userName" class="li_form" style="width: 320px;">
				<label class="editableLabel">申请单名称：</label>${model.purchasName }
			</li>
			<li id="field_userName" class="li_form" style="width: 320px;">
				<label for="expectedDeliveryTime" class="editableLabel">期望到货时间：</label>${model.expectedDeliveryTime }
			</li>
		</ul>
			<li id="field_userName" class="li_form" style="width: 670px;margin-left:24px" >
				<label  class="editableLabel">用途：</label>${model.remark }
			</li>
		<div style="clear:both;"></div>
		<div class=" row-inline" >
				<hr size=1 class="dashed-inline">
		</div>
		
		<h3>产品信息</h3>
				<table border="0" style="width: 100%" class="sino_table_body" >
				
				<thead>
					<tr>
						<td class="sino_table_label" style="background:none repeat scroll 0 0 #F3F3F3">序号</td>
						<td class="sino_table_label" style="background:none repeat scroll 0 0 #F3F3F3">产品类型</td>
						<td class="sino_table_label" style="background:none repeat scroll 0 0 #F3F3F3">产品厂商</td>
						<td class="sino_table_label" style="background:none repeat scroll 0 0 #F3F3F3">产品型号</td>
						<td class="sino_table_label" style="background:none repeat scroll 0 0 #F3F3F3">数量</td>
					</tr>
				</thead>
				<tbody>
				 <c:forEach var="product" items="${model.interPurchasProduct}" varStatus="status">
					<tr>
						<td class="sino_table_label">${ status.index + 1}</td>
						<td class="sino_table_label">${product.productTypeName}</td>
						<td class="sino_table_label">${product.productPartnerName}</td>
						<td class="sino_table_label">${product.productName}</td>
						<td class="sino_table_label">${product.quantity }</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
			<div style="clear:both;"></div>
			<div class=" row-inline" >
					<hr size=1 class="dashed-inline">
			</div>
			<h3>审批</h3>
			<!--审批按钮  -->
			
			    <input type="hidden" id="taskId" name="taskId" value="${taskId}" >
		            <ul class="clearfix" style="position: relative;">
					   <li id="field_userName" class="li_form" style="width: 400px;">
						    <label for="isNew" class="editableLabel">审批是否通过：</label>
						    <input type="radio" id="inlineCheckbox1" name="isAgree" value="1" checked="checked"/>通过&nbsp;&nbsp;&nbsp;
						    <input type="radio" id="inlineCheckbox2" name="isAgree" value="0"/>不通过&nbsp;&nbsp;&nbsp;
					   </li>
					   <li id="field_userName" class="li_form" style="width: 600px;">
					         <label class="editableLabel">备注：</label>
					         <textarea name="remark" rows="3" cols="30" style="width:450px"></textarea>
					   </li>
					 </ul>
			
			<h3>审批日志</h3>
					<table border="0" style="width: 100%" class="sino_table_body" id="_fee_travel">
				<thead>
			<tr>
							<td class="sino_table_label" style="background:none repeat scroll 0 0 #F3F3F3;width:20%;">操作人</td>
							<td class="sino_table_label" style="background:none repeat scroll 0 0 #F3F3F3;width:20%;">审批阶段</td>
							<td class="sino_table_label" style="background:none repeat scroll 0 0 #F3F3F3;width:20%;">审批结果</td>
							<td class="sino_table_label" style="background:none repeat scroll 0 0 #F3F3F3;width:20%;">操作时间</td>
							<td class="sino_table_label" style="background:none repeat scroll 0 0 #F3F3F3;width:20%;">详情</td>
						</tr>
					</thead>
					<tbody>
					<c:forEach var="logs" items="${proInstLogList}" varStatus="status">
						<tr>
							<td class="sino_table_label">${logs.user }</td>
							<td class="sino_table_label">${logs.taskName }</td>
							<td class="sino_table_label">${logs.apprResultDesc }</td>
							<td class="sino_table_label">${logs.time }</td>
							<td class="sino_table_label">${logs.apprDesc }</td>
						</tr>
					</c:forEach>
					</tbody>
				</table>
		<div style="clear:both;"></div>		 
</form>
			<!--按钮组-->
			<div id="bottom_button" class="handprocess_btngroup">
				<!-- <input type="button" id="_order_OK" value="确认" class="grey_btn33 cp" style="height:30px;"> -->
				<a id='_order_OK' role="button" class="btn btn-success"><i class="icon-ok"></i>确认</a>
				<a id='_eoss_business_back' role="button" class="btn">取消</a>
			</div>
		</div>
	</div>
	
<script language="javascript">
seajs.use('js/page/business/interPurchas/interPurchasReview/businessSupervisor', function(businessSupervisor) {
	businessSupervisor.init();
}); 
</script>
</body>
</html>