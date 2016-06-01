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
<style type="text/css">
	.row-input{
		width: 180px;
		height:28px"
	}

.row-input-oneColumn {
    width: 480px;
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
    line-height: 20px;
    padding: 1px 0;
    width: 300px;
}
.table {
    margin-bottom: 20px;
    width: 98%;
    margin-left: 10px;
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
h3{
  margin:5px 0;
}
label{
 line-height: 20px;
 margin-bottom:0;
}
 .tright a{
	 color:#fff;
	 }
	 .tright a:hover,.tright a:focus{
	 color:#fff;
	 }
ul{
margin:0 0 0 2px;
}
.sino_table_body td.sino_table_label{
height:20px;
}
.dashed-inline {
    border-style: dashed;
    margin: 2px 0 1px;
    width: 960px;
}
.table_order th,.table_order td{
padding:3px;
font-size:13px;
}
.editableLabel{
color:#888 !important;
}
</style>
    <script src="${ctx}/js/plugins/seajs/sea.js" id="seajsnode" type="text/javascript"></script>
	<script src="${ctx}/js/plugins/seajs/config.js" type="text/javascript"></script>

</head>
<body id="top">
<div class="salecontent">
		<div class="top">
			<div class="caption">
				<i class="icon-reorder"></i>&nbsp;&nbsp;&nbsp;订单编号：${model.orderCode }
			    <span class="tright" >
			    <c:if test="${flat!='search'}">
			    	<c:if test="${display=='CG'}">
						<a name="hrefReload" href="#" hrefA="${ctx }/business/order/saveOrUpdate?id=${model.id}" ><i class="icon-credit-card"></i>修改</a>&nbsp;&nbsp;
					</c:if>
					<c:if test="${display=='TGSP'&&proId==0}">
						<a name="hrefReload" href="#" hrefA="${ctx }/business/order/fileUpdate?id=${model.id}"><i class="icon-edit"></i>上传客户签收单</a>&nbsp;&nbsp;
						<c:if test="${model.isChange!='CSP' }">
							<a name="hrefReload" href="#" hrefA="${ctx }/business/order/change?id=${model.id}"><i class="icon-edit"></i>申请变更</a>&nbsp;&nbsp;
						</c:if>
						<%-- <c:if test="${payment!='A' }">
							<c:if test="${payment=='N' }">
								<a name="hrefReload" id='payment' href="#" hrefA="${ctx }/business/payment/addPayment?id=${model.id}" ><i class="icon-plus-sign"></i>付款计划</a>&nbsp;&nbsp;
							</c:if>	
							<c:if test="${payment!='N' }">
								<a name="hrefReload" id='payment' href="#" hrefA="${ctx }/business/payment/detail?id=${model.id}" ><i class="icon-plus-sign"></i>付款计划</a>&nbsp;&nbsp;
							</c:if>
						</c:if>	
						<c:if test="${reim!='A' }">
							<c:if test="${reim=='N' }">
								<a name="hrefReload" id='_reimbursement' href="#" hrefA="${ctx }/business/reimbursement/addReim?id=${model.id}" ><i class="icon-credit-card"></i>发票计划</a>&nbsp;&nbsp;
							</c:if>
							<c:if test="${reim!='N' }">
								<a name="hrefReload" id='_reimbursement' href="#" hrefA="${ctx }/business/reimbursement/detail?id=${model.id}" ><i class="icon-credit-card"></i>发票计划</a>&nbsp;&nbsp;
							</c:if>
						</c:if> --%>
					</c:if>
				</c:if>	
				</span>
			</div>
			<div class="tools">
				<!-- <a href="javascript:;" class="reload"><i class="icon-repeat"></i>返回</a> -->
			</div>
		</div>
	</div>	

	<!--表单、底部按钮 -->
	<div class="portlet-body form">
			<!-- 表单 -->
			<form id="_eoss_business_order" novalidate="novalidate" style="" method="post">
				<input type="hidden" name="id" id="id" value="${model.id }"/>
				<input type="hidden" name="invoiceType" id="_invoiceType" value="${model.invoiceType }">
				<input type="hidden" name="orderType" id="_orderType" value="${model.orderType}">
				<input type="hidden" name="purchaseType" id="_purchaseType" value="${model.purchaseType}">
				
				<div class="handprocess_order" id="SheetDiv">
				<br>
		        <h3>订单基本信息</h3>			
					<ul class="clearfix" >
					   <%--  <li id="field_userName" class="li_form" style="width:600px" >
							<label class="editableLabel" >订单编号：</label>${model.orderCode }
						</li> --%>
						<li id="field_userName" class="li_form" style="width:600px" >
							<label class="editableLabel" >订单名称：</label>${model.orderName }
						</li>
						 <li id="field_userName" class="li_form" >
					          <label  class="editableLabel">订单金额：</label><fmt:formatNumber value="${model.orderAmount }" type="currency" currencySymbol="￥"/>
				         </li>

					</ul>
					<ul class="clearfix" >
					
					    <li id="field_userName" class="li_form" >
							<label  class="editableLabel">订单类型：</label>
							<div id="orderType" class="div_layout" ></div>
						</li>
						<li id="field_userName" class="li_form" >
							<label class="editableLabel">服务期(月)：</label>${model.serviceDate} 
						</li>
					    <li id="field_userName" class="li_form" >
							<label  class="editableLabel">采购分类：</label>
							<div id="purchaseType" class="div_layout"></div>
						</li>

					</ul>
					<ul class="clearfix" >
						<%-- <li id="field_userName" class="li_form" >
							<label for="expectedDeliveryTime" class="editableLabel">到货时间：</label>${model.expectedDeliveryTime}
						</li> --%>
					    <li id="field_userName" class="li_form" >
							<label class="editableLabel">到货地点：</label>
							<div id="_business_deliveryAddress" class="div_layout">
					            <input type="hidden" name="deliveryAddress" id="_business_deliveryAddressId"  value="${model.deliveryAddress}"/>
						    </div>
						</li>
						<li id="field_userName" class="li_form" >
				             <label  class="editableLabel">付款方式：</label>
				             <div id="_eoss_business_paymentMode" class="div_layout"></div>
				              <input type="hidden" name="paymentMode" id="_eoss_business_paymentModeId"  value="${model.paymentMode}"/>
			            </li>
						<li id="field_userName" class="li_form" >
							<label  class="editableLabel">发票类型：</label>
							<div id="invoiceType" class="div_layout"></div>
						</li>
			         </ul>
			         <ul class="clearfix" >
						<li id="field_userName" class="li_form" >
							<label  class="editableLabel">使用返点数：</label>${model.spotNum }
						</li>
						<c:if test="${model.spotNum>0}">
						<li id="field_userName" class="li_form" style="width:600px;">
							<label  class="editableLabel">返点厂商：</label>${model.spotSupplier }
						</li>
						</c:if>
			         <c:if test="${model.changeRemark!=null&&model.changeRemark!=''}">
						<li id="field_userName" class="li_form" >
							<label  class="editableLabel">订单驳回原因：</label>${model.changeRemark }
						</li>
			         </c:if>
			         <c:if test="${flat=='search'}">
			         	<li id="field_userName" class="li_form" >
							<label  class="editableLabel">订单状态：</label><span id="status"></span>
							<input type="hidden" id="orderStatus" value="${model.orderStatus }"/>
						</li>
			         </c:if>
			         <li id="field_userName" class="li_form" >
							<label  class="editableLabel">结算币种：</label><span id="currency">${model.accountCurrency }</span>
							<input type="hidden" id="accountCurrency" value="${model.accountCurrency }"/>
						</li>
			         </ul>
					<div style="clear:both;"></div>
					<div class=" row-inline" style="margin-left:0;">
			        	<hr size=1 class="dashed-inline">
	            	</div>
	            	
					
				<h3>供应商信息（乙方）</h3>
					<ul class="clearfix">
					    <li id="field_userName" class="li_form">
							<label class="editableLabel">供应商类型：</label>
							<div id='_supplierType'>
								<input type="hidden" id="supplierType" name="supplierType" value="${model.supplierInfoModel.supplierType}"/>
							</div>
						</li>
						<li id="field_userName" class="li_form"  style="width:600px" >
							<label class="editableLabel">供应商名称：</label>${model.supplierInfoModel.supplierName}
						</li>
						
					</ul>
					<ul class="clearfix">
						<li id="field_userName" class="li_form" >
							<label class="editableLabel" >供应商编号：</label>${model.supplierInfoModel.supplierCode}
							<div  id='supplierCode'></div>
						</li>
						 <li id="field_userName" class="li_form" style="width:600px" >
							<label class="editableLabel" >地址：</label>${model.supplierInfoModel.address}
							<div  id='address'></div>
						</li>
						 
					</ul>
					<ul class="clearfix">
					    <li id="field_userName" class="li_form">
							<label class="editableLabel">邮政编码：</label>${model.supplierInfoModel.zipCode}
							<div  id='zipCode'></div>
						</li>
					   <li id="field_userName" class="li_form" style="width:600px" >
							<label class="editableLabel">开户银行：</label>${model.supplierInfoModel.bankName}
							<div  id='bankNameSupplier'></div>
						</li>
					   
					</ul>
					<ul class="clearfix">
					    <li id="field_userName" class="li_form" >
							<label class="editableLabel" >银行账号：</label>${model.supplierInfoModel.bankAccount}
							<div  id='BankAccount'></div>
						</li>
						 <li id="field_userName" class="li_form" >
							<label class="editableLabel" >传真：</label>${model.supplierInfoModel.fax}
							<div  id='fax'></div>
						</li>
					</ul>
					<ul class="clearfix">
					   <li id="field_userName" class="li_form" >
							<label class="editableLabel">联系人：</label>${model.supplierContactsModel.contactName}
						</li>
				     	<li id="field_userName" class="li_form">
							<label class="editableLabel" >联系人电话：</label>${model.supplierContactsModel.contactPhone}
							<div id='_contactPhone'></div>
						</li>
					    <li id="field_userName" class="li_form">
							<label class="editableLabel">联系人手机号：</label>${model.supplierContactsModel.contactTelPhone}
						</li>
					</ul>
					<div style="clear:both;"></div>
					<div class=" row-inline" style="margin-left:0;">
							<hr size=1 class="dashed-inline">
					</div>
				<h3>利润预估</h3>
					<ul class="clearfix">
						<c:if test="${model.partnerPV!='' }">
							<li id="field_userName" class="li_form" >
								<label class="editableLabel">厂商折扣(%)：</label>${model.partnerPV}
							</li>
						</c:if>
						<c:if test="${model.profitsValue!='' }">
							<li id="field_userName" class="li_form" style="width: 260px;">
								<label class="editableLabel"  style="width: 60px;">利润：</label>${model.profitsValue}
							</li>
						</c:if>
						<c:if test="${model.customerPV!='' }">
							<li id="field_userName" class="li_form" style="width: 350px;" >
								<label class="editableLabel" style="width: 110px;" >客户利润率(%)：</label>${model.customerPV}
							</li>
						</c:if>
					</ul>
					<div style="clear:both;"></div>
					<div class=" row-inline" >
							<hr size=1 class="dashed-inline">
					</div>
				
			<h3>公司信息（甲方）</h3>
				<ul class="clearfix">
					<li id="field_userName" class="li_form" style="width:600px">
						<label class="editableLabel">公司名称：</label> ${model.companyName}
					</li>
					<li id="field_userName" class="li_form" >
						<label class="editableLabel" >开户银行：</label>${model.bankName}
					</li>
				</ul>
				<ul class="clearfix">
				<li id="field_userName" class="li_form" style="width:600px">
						<label class="editableLabel">公司地址：</label>${model.companyAddress}
					</li>
					
					<li id="field_userName" class="li_form">
						<label class="editableLabel">银行账号：</label>${model.bankAccount}
					</li>
				</ul>
		        <div style="clear:both;"></div>
		        
		        <div class=" row-inline" style="margin-left:0;" id="orderBill">
						<hr size=1 class="dashed-inline">
				</div>
				
					
				<h3>产品信息</h3>
    			<table cellpadding="0" cellspacing="0" border="0" class="table table-striped table-bordered table-hover table_order" id="product_table">
				<thead>
				<c:if test="${model.orderType!='4'}">
					<tr>
						<td class="sino_table_label" style="background:none repeat scroll 0 0 #F3F3F3">合同名称</td>
						<td class="sino_table_label" style="background:none repeat scroll 0 0 #F3F3F3">产品类型</td>
						<td class="sino_table_label" style="background:none repeat scroll 0 0 #F3F3F3">厂商简称</td>
						<td class="sino_table_label" style="background:none repeat scroll 0 0 #F3F3F3">产品型号</td>
						<td class="sino_table_label" style="background:none repeat scroll 0 0 #F3F3F3">数量</td>
						<td class="sino_table_label" style="background:none repeat scroll 0 0 #F3F3F3">单价</td>
						<td class="sino_table_label" style="background:none repeat scroll 0 0 #F3F3F3">总价</td>
						<!-- <td class="sino_table_label" style="background:none repeat scroll 0 0 #F3F3F3">服务类型</td>
						<td class="sino_table_label" style="background:none repeat scroll 0 0 #F3F3F3">服务开始时间</td>
						<td class="sino_table_label" style="background:none repeat scroll 0 0 #F3F3F3">服务结束时间</td> -->
					</tr>
				</c:if>
				<c:if test="${model.orderType =='4'}">
					<tr>
						<td class="sino_table_label" style="background:none repeat scroll 0 0 #F3F3F3">内部采购名称</td>
						<td class="sino_table_label" style="background:none repeat scroll 0 0 #F3F3F3">产品类型</td>
						<td class="sino_table_label" style="background:none repeat scroll 0 0 #F3F3F3">厂商简称</td>
						<td class="sino_table_label" style="background:none repeat scroll 0 0 #F3F3F3">产品型号</td>
						<td class="sino_table_label" style="background:none repeat scroll 0 0 #F3F3F3">数量</td>
						<td class="sino_table_label" style="background:none repeat scroll 0 0 #F3F3F3">单价</td>
						<td class="sino_table_label" style="background:none repeat scroll 0 0 #F3F3F3">总价</td>
						<!-- <td class="sino_table_label" style="background:none repeat scroll 0 0 #F3F3F3">服务类型</td>
						<td class="sino_table_label" style="background:none repeat scroll 0 0 #F3F3F3">服务开始时间</td>
						<td class="sino_table_label" style="background:none repeat scroll 0 0 #F3F3F3">服务结束时间</td> -->
					</tr>
				</c:if>
				</thead>
				<tbody>
				 <c:forEach var="product" items="${model.businessOrderProductModel}" varStatus="status">
				  <c:if test="${product.salesContractModel.id!=null}">
					<tr>
						<td >${product.salesContractModel.contractName}</td>
						<td >${product.productType }</td>
						<td >${product.vendorCode }</td>
						<td >${product.productNo }</td>
						<td >${product.quantity }</td>
						<td ><fmt:formatNumber value="${product.unitPrice }" type="currency" currencySymbol="￥"/> </td>
						<td ><fmt:formatNumber value="${product.subTotal }" type="currency" currencySymbol="￥"/></td>
						<%-- <td class="sino_table_label" id="serviceType${ status.index}">${product.serviceType }</td>
						<td class="sino_table_label">${product.serviceStartTime }</td>
						<td class="sino_table_label">${product.serviceEndTime }</td> --%>
					</tr>
					</c:if>
					<c:if test="${product.interPurchasProduct.id!=null}">
					<tr>
						<td >${product.interPurchas.purchasName}</td>
						<td >${product.productType }</td>
						<td >${product.vendorCode }</td>
						<td >${product.productNo }</td>
						<td >${product.quantity }</td>
						<td ><fmt:formatNumber value="${product.unitPrice }" type="currency" currencySymbol="￥"/> </td>
						<td ><fmt:formatNumber value="${product.subTotal }" type="currency" currencySymbol="￥"/></td>
						<%-- <td class="sino_table_label" id="serviceType${ status.index}">${product.serviceType }</td>
						<td class="sino_table_label">${product.serviceStartTime }</td>
						<td class="sino_table_label">${product.serviceEndTime }</td> --%>
					</tr>
					</c:if>
				</c:forEach>
				</tbody>
			</table>
				<div class=" row-inline" style="margin-left:0;" id="salesBill">
						<hr size=1 class="dashed-inline">
				</div>
				<h3>合同信息</h3>
				 <c:forEach var="sales" items="${sales}" varStatus="status">
					        <table id="_sales_${status.index }" name="_sales_${status.index }" class="table  table-bordered table_order"  style="width:940px;margin-top:0px;">
					           <tr>
					              <td colspan=7 style="padding:8px;font-size:14px;">
					              	<div  style="float:left;width:246px;">合同名称:<a href="${ctx }/sales/contract/detail?id=${sales.id}&flowStep=SHOW" target="_blank"><span style="color: #0000dd;">${sales.contractName }</span></a></div>
					              <div style="float:left;width:321px;">合同编号:${sales.contractCode }</div>
					              <div  style="float:left;width:200px;">合同金额:<fmt:formatNumber value="${sales.contractAmount }" type="currency" currencySymbol="￥"/> </div>
					              <div  style="float:left;width:120px;">客户经理:<span style="font-weight:bold;">${sales.creatorName }</span></div>
					              </td>
					           </tr>
					           	<tr style="background-color: #f3f3f3;color: #888888;">
								  <th style="width:10%;">产品类型</th>
								  <th style="width:10%;">厂商简称</th>
								  <th style="width:15%;">产品型号</th>
								  <th style="width:10%;">数量</th>
								  <th style="width:10%;">单价</th>
								  <th style="width:10%;">总价</th>
								  <th style="width:10%;">下单数量</th>
					          </tr>
					          	<c:forEach var="product"  items="${sales.ordersaleproducts}" varStatus="status1">
									<tr class="_product_list" id="trall_${status1.index }">
										<td class="sino_table_label" >${product.typeName }</td>
										<td class="sino_table_label" >${product.partnerName }</td>
										<td class="sino_table_label" >${product.pName }</td>
										<td class="sino_table_label" >${product.orderQua }</td>
										<td class="sino_table_label"><fmt:formatNumber value="${product.unitprice }" type="currency" currencySymbol="￥"/></td>
										<td class="sino_table_label" name="prductAmount" tdvalue="${product.subtotal }"><fmt:formatNumber value="${product.subtotal }" type="currency" currencySymbol="￥"/></td>
										<td class="sino_table_label">${product.ordernum }</td>
									</tr>
								</c:forEach>
								<tr>
									 <td colspan=7 style="padding:8px;font-size:14px;">
										<div  style="float:right;width:200px;">合计金额:<span style="font-weight:bold;" id="totallProduct_${status.index }"></span></div>
									</td>
								</tr>
					          </table>
				</c:forEach>
			 <div class=" row-inline" style="margin-left:0;" id="payremi">
						<hr size=1 class="dashed-inline">
				</div>
				<h3>付款计划信息</h3>
				<table cellpadding="0" cellspacing="0" border="0" class="table table-striped table-bordered table-hover table_order" id="_payment">
				<thead>
					<tr>
						<c:if test="${currency!='usd' }">		
							<td class="sino_table_label" >付款金额</td>
						</c:if>
						<c:if test="${currency=='usd' }">	
							<td class="sino_table_label" >付款金额（美金）</td>
							<td class="sino_table_label" >付款金额（人民币）</td>
						</c:if>
						<td class="sino_table_label" >计划付款日期</td>
						<td class="sino_table_label" >税率</td>
						<td class="sino_table_label" >实际付款日期</td>
					</tr>
				</thead>
				<tbody>
				 <c:forEach var="plan" items="${model.businessPaymentPlanModel}" varStatus="status">
					<tr>
						<c:if test="${currency!='usd' }">					
						<td >
							<fmt:formatNumber value="${plan.amount}" type="currency" currencySymbol="￥"/> 
						</td>
						</c:if>
						<c:if test="${currency=='usd' }">		
						<td >			
							<fmt:formatNumber value="${plan.amount}" type="currency" currencySymbol="$"/> 
						</td>
						<td >
							<fmt:formatNumber value="${plan.realPayAmount}" type="currency" currencySymbol="￥"/> 
						</td>
						</c:if>
						
						<td >${plan.planTime }</td>
						<td id="invoiceTypePay${ status.index}">${plan.invoiceType }</td>
						<td >${plan.payOrder.realPayDate }</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>

			
			<h3>发票计划信息</h3>
				<table cellpadding="0" cellspacing="0" border="0" class="table table-striped table-bordered table-hover table_order"  id="_reim">
				<thead>
					<tr>
						<td class="sino_table_label" >报销金额</td>
						<td class="sino_table_label" >计划报销日期</td>
						<td class="sino_table_label" >税率</td>
						<td class="sino_table_label" >实际报销时间</td>
					</tr>
				</thead>
				<tbody>
				 <c:forEach var="reim" items="${model.businessReimbursementModel}" varStatus="status">
					<tr>
						<td ><fmt:formatNumber value="${reim.amount}" type="currency" currencySymbol="￥"/></td>
						<td >${reim.planTime}</td>
						<td id="invoiceTypeReim_${status.index}">${reim.invoiceType}</td>
						<td >${reim.businessReimbursementApply.closeTime }</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
			    <div class=" row-inline" style="margin-left:0;">
						<hr size=1 class="dashed-inline">
				</div>
			<h3>客户签收单</h3>
					<div class=" row-inline" >
				        <div id="uplaodfile"></div>
			        </div>
			<div style="clear:both;"></div>	
			
			<h3>审批日志</h3>
					<table border="0" style="width: 100%" class="sino_table_body table_order" id="_fee_travel">
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
       </div>
       <c:if test="${param.orderCachet == 'orderCachet'}">
       		<div class=" row-inline" style="margin-left:0;">
						<hr size=1 class="dashed-inline">
				</div>
			<div style="margin-left:10px;"><h5>盖章审批</h5></div>
			<ul class="clearfix" >
			    <li id="field_userName" class="li_form" >
					<label class="editableLabel" style="margin-left:10px;">审批意见：</label>
					<textarea  id="_order_cachet_advice" style="width:80%;"></textarea>
				</li>
			</ul>
       </c:if>
       <c:if test="${param.orderCachet != 'orderCachet'&&model.cachetTime != ''&&model.cachetTime != null}">
       		<div class=" row-inline" style="margin-left:0;">
						<hr size=1 class="dashed-inline">
				</div>
			<div style="margin-left:10px;"><h5>盖章审批</h5></div>
			<ul class="clearfix" >
				<li id="field_userName" class="li_form" >
					<label class="editableLabel" style="margin-left:10px;">审批时间：</label>
					${model.cachetTime }
				</li>
			    <li id="field_userName" class="li_form" >
					<label class="editableLabel" style="margin-left:10px;">审批意见：</label>
					${model.cachetAdvice }
				</li>
			</ul>
       </c:if>
</form>
			<!--按钮组-->
			<div id="bottom_button" class="handprocess_btngroup">
				<c:if test="${param.orderCachet == 'orderCachet'}">
       				<a id='_eoss_business_order_cachet_sub' role="button" class="btn btn-success"><i class="icon-ok"></i>确认</a>
       			</c:if>
				<!-- <input type="button" id="_eoss_business_order_back" value="返回" class="btn" > -->
			    <a id='_eoss_business_order_back' role="button" class="btn"><i class="icon-remove-circle"></i>关闭</a>
			</div>
			<div style="clear:both;"></div>
		</div>
	</div>
	
<script language="javascript">
seajs.use('js/page/business/order/detail', function(detail) {
	detail.init();
}); 
</script>

		<div id="back-to-top" style="display: block;">
			<a href="#orderBill"><div style="background-color:#aaaaaa;color:#fff;margin-bottom:3px;">订单<br/>清单</div></a>
			<a href="#salesBill"><div style="background-color:#aaaaaa;color:#fff;margin-bottom:3px;">合同<br/>信息</div></a>
			<a href="#payremi"><div style="background-color:#aaaaaa;color:#fff;margin-bottom:3px;">付款<br/>发票</div></a>
			<a href="#top"><span></span></a>
		</div>
</body>
</html>