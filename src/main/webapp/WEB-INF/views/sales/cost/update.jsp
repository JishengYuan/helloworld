<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/global.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
	<title>资金占用成本</title>
	<%@ include file="/common/include-base-boostrap-styles.jsp" %>
	<link href="${ctx}/js/plugins/select2/css/select2.css" rel="stylesheet"/>
	<link rel="stylesheet" href="${ctx}/js/plugins/bootstrap/css/bootstrap-datetimepicker.min.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/sinobridge/pink/sino_form_grid.css" type="text/css" />
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formSelect.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formSearchTip.css" type="text/css" ></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formstyle.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/dialog.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/tip.css" type="text/css" ></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/jquery-ui-1.8.17.custom.css" type="text/css" ></link>
	<link rel="stylesheet" href="${ctx}/skin/default/editgrid/editgrid.css" type="text/css" />
	<link href="${ctx}/skin/default/sales/sale.css" rel="stylesheet"/>
	
	<style type="text/css">
	.inputStyle{
		width:120px;
	}
	</style>
</head>
	<script src="${ctx}/js/plugins/seajs/sea.js" id="seajsnode" type="text/javascript"></script>
	<script src="${ctx}/js/plugins/seajs/config.js" type="text/javascript"></script>
<body>
	
	<div class="salecontent">
		<div class="top">
			<div class="caption">
				<i class="icon-reorder"></i>&nbsp;&nbsp;&nbsp;合同编号：${model.contractCode}
			</div>
		</div>
	</div>
		<div class="portlet-body form" id="top" >
			
			<!-- BEGIN FORM-->
			<form id='_sino_eoss_sales_cost_Form' method="post" action="" class='form-horizontal' >
				<!--隐藏字段start -->
			<%-- 	<!--  隐藏的合同ID-->--%>
				<input type='hidden' name='id' id='id' value="${model.id}"/> 
	<div class="handprocess_order" id="SheetDiv">
				<br>
		        <h3>合同基本信息</h3>			
					<ul class="clearfix" >
						<li id="field_userName" class="li_form" style="width:600px" >
							<label class="editableLabel" >合同名称：</label>${model.contractName}
						</li>
				    </ul>
					<ul class="clearfix" >
					    <li id="field_userName" class="li_form" style="width:300px">
							<label  class="editableLabel">销售：</label>${model.creatorName}
						</li>
						 <li id="field_userName" class="li_form" style="width:300px" >
					          <label  class="editableLabel">合同金额：</label><fmt:formatNumber value="${model.contractAmount}" type="currency" currencySymbol="￥"/>
				         </li>
						<li id="field_userName" class="li_form" style="width:300px">
							<label  class="editableLabel">计算时间：</label>${model.createTime}
						</li>
					</ul>
					<h3>合同成本相关信息</h3>	
					<ul class="clearfix" >
						 <li id="field_userName" class="li_form" style="width:300px">
							<label  class="editableLabel">回款金额：</label>
							<input class="inputStyle" type="text" id='salesReceive' name='salesReceive' value='${model.salesReceive}'/>
						</li>
						 <li id="field_userName" class="li_form" style="width:300px">
							<label  class="editableLabel">付款金额：</label>
							<input class="inputStyle" type='text' id='orderPay' name='orderPay' value='${model.orderPay}' />
						</li>
					    <li id="field_userName" class="li_form" style="width:300px">
							<label class="editableLabel">资金成本占用：</label>
							<input class="inputStyle" type='text' id='cost' name='cost' value='${model.cost}' />
						</li>
					</ul>
			</div>
					
</form>
			<!--按钮组-->
			<div id="bottom_button" class="handprocess_btngroup">
				<a id='_eoss_business_OK' role="button" class="btn btn-success"><i class="icon-ok"></i>确定</a>
			    <a id='_eoss_business_back' role="button" class="btn"><i class="icon-remove-circle"></i>关闭</a>
			</div>
			<div style="clear:both;"></div>
		</div>
	<script language="javascript">
		seajs.use('js/page/sales/cost/update',function(update){
			update.init();
		});    
	</script>
</body>
</html>
