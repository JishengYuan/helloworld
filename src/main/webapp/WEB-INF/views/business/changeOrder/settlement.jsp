<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/global.jsp"%>
<%@ page import="com.sinobridge.systemmanage.util.Global"%> 
<%@ page import="com.sinobridge.systemmanage.vo.SystemUser"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>商务订单详情</title>
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
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/jquery-ui-1.8.17.custom.css" type="text/css" ></link>
	<link rel="stylesheet" href="${ctx}/skin/default/editgrid/editgrid.css" type="text/css" />
<style type="text/css">
	.row-input{
		width: 180px;
		height:28px"
	}

.row-input-oneColumn {
    width: 480px;
}
.handprocess_order li.li_form {
    display: inline-block;
    float: left;
    font-size: 14px;
    line-height: 25px;
    padding: 1px 0;
    width: 300px;
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
    color: #000000;
}

label.editableLabel {
    color: #000000;
   
}
.modal-footer {
    text-align: center;
}

label{
 line-height: 24px;
}
 .tright a{
	 color:#fff;
	 }
	 .tright a:hover,.tright a:focus{
	 color:#fff;
	 }
ul{
margin:0 0 0 25px;
}	 
</style>
    <script src="${ctx}/js/plugins/seajs/sea.js" id="seajsnode" type="text/javascript"></script>
	<script src="${ctx}/js/plugins/seajs/config.js" type="text/javascript"></script>
	<link href="${ctx}/skin/default/sales/sale.css" rel="stylesheet"/>
</head>
<body>

<div class="salecontent">
		<div class="top">
			<div class="caption">
			    	<i class="icon-reorder"></i>&nbsp;&nbsp;&nbsp;变更审批
			    <span class="tright" >
				</span>
			</div>
		</div>
	</div>	

	<!--表单、底部按钮 -->
	<div class="portlet-body form">
			<!-- 表单 -->
			<form id="_eoss_business_order" novalidate="novalidate" style="" method="post">
				<input type="hidden" name="id" id="id" value="${model.id }"/>
				<input type="hidden" name="invoiceType" id="_invoiceType" value="${order.invoiceType }">
				<input type="hidden" name="orderType" id="_orderType" value="${order.orderType}">
				<input type="hidden" name="purchaseType" id="_purchaseType" value="${order.purchaseType}">
				
				<div class="handprocess_order" id="SheetDiv">
				<br>
				<h3>变更申请信息</h3>
					<ul class="clearfix" >
						<li id="field_userName" class="li_form"  >
							<label class="editableLabel" >申请人：</label>${model.creator }
						</li>
						 <li id="field_userName" class="li_form" >
					          <label  class="editableLabel">申请时间：</label>${model.creatTime }
				         </li>
					</ul>
					<ul class="clearfix" >
						<li id="field_userName" class="li_form" style="width:600px" >
							<label class="editableLabel" style="font-size:18px;color:#e99031;font-weight:bold;">变更描述：</label>${model.remark }
						</li>
					</ul>
					<div style="clear:both;"></div>
					<div class=" row-inline" >
			        	<hr size=1 class="dashed-inline">
	            	</div>
		        <h3>订单基本信息</h3>			
					<ul class="clearfix" >
					  <li id="field_userName" class="li_form" >
							<label  class="editableLabel">订单类型：</label>
							<div id="orderType" class="div_layout" ></div>
						</li>
						<li id="field_userName" class="li_form" style="width:600px" >
							<label class="editableLabel" >订单名称：</label>${order.orderName }
						</li>
					</ul>
					<ul class="clearfix" >
						 <li id="field_userName" class="li_form" >
					          <label  class="editableLabel">订单金额：</label><fmt:formatNumber value="${order.orderAmount }" type="currency" currencySymbol="￥"/>
				         </li>
						<li id="field_userName" class="li_form" >
							<label class="editableLabel">服务期(月)：</label>${order.serviceDate} 
						</li>
					    <li id="field_userName" class="li_form" >
							<label  class="editableLabel">采购分类：</label>
							<div id="purchaseType" class="div_layout"></div>
						</li>
					</ul>
					<ul class="clearfix" >
					    <li id="field_userName" class="li_form" >
							<label class="editableLabel">到货地点：</label>
							<div id="_business_deliveryAddress" class="div_layout">
					            <input type="hidden" name="deliveryAddress" id="_business_deliveryAddressId"  value="${order.deliveryAddress}"/>
						    </div>
						</li>
						<li id="field_userName" class="li_form" >
				             <label  class="editableLabel">付款方式：</label>
				             <div id="_eoss_business_paymentMode" class="div_layout"></div>
				              <input type="hidden" name="paymentMode" id="_eoss_business_paymentModeId"  value="${order.paymentMode}"/>
			            </li>
						<li id="field_userName" class="li_form" >
							<label  class="editableLabel">发票类型：</label>
							<div id="invoiceType" class="div_layout"></div>
						</li>
			         </ul>
			         <ul class="clearfix" >
						<li id="field_userName" class="li_form" >
							<label  class="editableLabel">使用返点数：</label>${order.spotNum }
						</li>
						<li id="field_userName" class="li_form"  style="width:600px" >
							<label class="editableLabel">供应商名称：</label>${order.supplierInfoModel.supplierName}
						</li>
					</ul>
					<ul class="clearfix">
						<c:if test="${order.partnerPV!='' }">
							<li id="field_userName" class="li_form" >
								<label class="editableLabel">厂商折扣(%)：</label>${order.partnerPV}
							</li>
						</c:if>
						<c:if test="${order.profitsValue!='' }">
							<li id="field_userName" class="li_form" style="width: 260px;">
								<label class="editableLabel"  style="width: 60px;">利润：</label>${order.profitsValue}
							</li>
						</c:if>
						<c:if test="${order.customerPV!='' }">
							<li id="field_userName" class="li_form" style="width: 350px;" >
								<label class="editableLabel" style="width: 110px;" >客户利润率(%)：</label>${order.customerPV}
							</li>
						</c:if>
					</ul>
					
					<div style="clear:both;"></div>
					<div class=" row-inline" >
							<hr size=1 class="dashed-inline">
					</div>
				
				<h3>产品信息</h3>
				<table border="0" style="width: 100%" class="sino_table_body" id="product_table">
				<thead>
				<c:if test="${order.orderType!='4'}">
					<tr>
						<td class="sino_table_label" style="background:none repeat scroll 0 0 #F3F3F3">合同名称</td>
						<td class="sino_table_label" style="background:none repeat scroll 0 0 #F3F3F3">产品类型</td>
						<td class="sino_table_label" style="background:none repeat scroll 0 0 #F3F3F3">厂商简称</td>
						<td class="sino_table_label" style="background:none repeat scroll 0 0 #F3F3F3">产品型号</td>
						<td class="sino_table_label" style="background:none repeat scroll 0 0 #F3F3F3">数量</td>
						<td class="sino_table_label" style="background:none repeat scroll 0 0 #F3F3F3">单价</td>
						<td class="sino_table_label" style="background:none repeat scroll 0 0 #F3F3F3">总价</td>
					</tr>
				</c:if>
				<c:if test="${order.orderType =='4'}">
					<tr>
						<td class="sino_table_label" style="background:none repeat scroll 0 0 #F3F3F3">内部采购名称</td>
						<td class="sino_table_label" style="background:none repeat scroll 0 0 #F3F3F3">产品类型</td>
						<td class="sino_table_label" style="background:none repeat scroll 0 0 #F3F3F3">厂商简称</td>
						<td class="sino_table_label" style="background:none repeat scroll 0 0 #F3F3F3">产品型号</td>
						<td class="sino_table_label" style="background:none repeat scroll 0 0 #F3F3F3">数量</td>
						<td class="sino_table_label" style="background:none repeat scroll 0 0 #F3F3F3">单价</td>
						<td class="sino_table_label" style="background:none repeat scroll 0 0 #F3F3F3">总价</td>
					</tr>
				</c:if>
				</thead>
				<tbody>
				 <c:forEach var="product" items="${order.businessOrderProductModel}" varStatus="status">
				  <c:if test="${product.salesContractModel.id!=null}">
					<tr>
						<td class="sino_table_label">${product.salesContractModel.contractName}</td>
						<td class="sino_table_label">${product.productType }</td>
						<td class="sino_table_label">${product.vendorCode }</td>
						<td class="sino_table_label">${product.productNo }</td>
						<td class="sino_table_label">${product.quantity }</td>
						<td class="sino_table_label"><fmt:formatNumber value="${product.unitPrice }" type="currency" currencySymbol="￥"/> </td>
						<td class="sino_table_label"><fmt:formatNumber value="${product.subTotal }" type="currency" currencySymbol="￥"/></td>
					</tr>
					</c:if>
					<c:if test="${product.interPurchasProduct.id!=null}">
					<tr>
						<td class="sino_table_label">${product.interPurchas.purchasName}</td>
						<td class="sino_table_label">${product.productType }</td>
						<td class="sino_table_label">${product.vendorCode }</td>
						<td class="sino_table_label">${product.productNo }</td>
						<td class="sino_table_label">${product.quantity }</td>
						<td class="sino_table_label"><fmt:formatNumber value="${product.unitPrice }" type="currency" currencySymbol="￥"/> </td>
						<td class="sino_table_label"><fmt:formatNumber value="${product.subTotal }" type="currency" currencySymbol="￥"/></td>
					</tr>
					</c:if>
				</c:forEach>
				</tbody>
			</table>
 				<div class=" row-inline" >
						<hr size=1 class="dashed-inline">
				</div>
				<h3>付款计划信息</h3>
				<table border="0" style="width: 100%" class="sino_table_body" id="_payment">
				<thead>
					<tr>
						<td class="sino_table_label" style="width:9%;">付款金额</td>
						<td class="sino_table_label" style="width:9%;">计划付款日期</td>
						<td class="sino_table_label" style="width:20%;">发票类型</td>
						<td class="sino_table_label" style="width:9%;">实际付款日期</td>
					</tr>
				</thead>
				<tbody>
				 <c:forEach var="plan" items="${order.businessPaymentPlanModel}" varStatus="status">
					<tr>
						<td class="sino_table_label"><fmt:formatNumber value="${plan.amount}" type="currency" currencySymbol="￥"/> </td>
						<td class="sino_table_label">${plan.planTime }</td>
						<td class="sino_table_label" id="invoiceTypePay${ status.index}">${plan.invoiceType }</td>
						<td class="sino_table_label">${plan.payOrder.closeTime }</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
			    <div class=" row-inline" >
						<hr size=1 class="dashed-inline">
				</div>
			
			<h3>发票计划信息</h3>
				<table border="0" style="width: 100%" class="sino_table_body" id="_reim">
				<thead>
					<tr>
						<td class="sino_table_label" style="width:9%;">报销金额</td>
						<td class="sino_table_label" style="width:9%;">计划报销日期</td>
						<td class="sino_table_label" style="width:9%;">实际报销时间</td>
					</tr>
				</thead>
				<tbody>
				 <c:forEach var="reim" items="${order.businessReimbursementModel}" varStatus="status">
					<tr>
						<td class="sino_table_label"><fmt:formatNumber value="${reim.amount}" type="currency" currencySymbol="￥"/></td>
						<td class="sino_table_label">${reim.planTime }</td>
						<td class="sino_table_label">${reim.businessReimbursementApply.closeTime }</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
			    <div class=" row-inline" >
						<hr size=1 class="dashed-inline">
				</div>
			<h3>审批</h3>
			<div class=" row-inline" >
				<div class=''>
					<label class="checkbox inline">
						同意<input type="radio" id="inlineCheckbox1" name="isAgree" value="1" checked="checked">
					</label>
					<label class="checkbox inline">
						驳回<input type="radio" id="inlineCheckbox2" name="isAgree" value="0" >
					</label>
				</div>
			</div>
			<br class='float-clear' />
			<!-- 审核第3行start -->
			<div class=" row-inline" style="margin-top:15px">
				<div class=''>
					<label class='row-label' for='contractName' >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;审批意见：</label>
					<div class='row-input'>
						<textarea rows="3" name="remark"></textarea>
					</div>
				</div>
			</div>
			
			<div style="clear:both;"></div>
       </div>
</form>
			<!--按钮组-->
			<div class="modal-footer">
					<a id="dsave"  class="btn btn-primary" data-dismiss="modal" aria-hidden="true" >确定</a>
					<button id="_do_cancel"  class="btn" data-dismiss="modal" aria-hidden="true" >取消</button >
				</div>
			<div style="clear:both;"></div>
		</div>
	</div>
	
<script language="javascript">
seajs.use('js/page/business/changeOrder/settlement', function(settlement) {
	settlement.init();
}); 
</script>
</body>
</html>