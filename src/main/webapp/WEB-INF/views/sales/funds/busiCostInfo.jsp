<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/global.jsp"%>
<%@ page import="com.sinobridge.systemmanage.util.Global"%> 
<%@ page import="com.sinobridge.systemmanage.vo.SystemUser"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
	<title>${model.contractName}</title>
	<%@ include file="/common/include-base-boostrap-styles.jsp" %>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formSelect.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formstyle.css" type="text/css"></link>

	<script src="${ctx}/js/plugins/seajs/sea.js" id="seajsnode" type="text/javascript"></script>
	<script src="${ctx}/js/plugins/seajs/config.js" type="text/javascript"></script>
	<link href="${ctx}/skin/default/sales/sale.css" rel="stylesheet"/>
	<style type="text/css">
	.salecontent .caption{
	 float:left;
	}
	.salecontent .tools{
	 float:right;
	 }
	 .salecontent .tools a{
	  color: #ffffff;
	 }
	.portlet-body{
	margin-top:32px;
	color:#888888;
	}
	.handprocess_btngroup{
	padding:5px;
	text-align:center;
	}
	.divheader{
	background-color: #32a7f9;
    color: #fff;
    font-family: “宋体”;
    font-size: 15px;
    height: 36px;
    line-height: 36px;
    padding-left: 22px;
	
	}
	</style>
</head>
<body>
	
	<div class="salecontent">
		<div class="top">
			<div class="caption">
				<i class="icon-reorder"></i>&nbsp;&nbsp;&nbsp;确认商务付款：${payOrderModel.payApplyName}   
				&nbsp;&nbsp;<span style="font-weight:bold;">
				<c:if test="${payOrderModel.currency=='usd' }">
				<fmt:formatNumber value="${payOrderModel.realPayAmount}"  type="currency" currencySymbol="￥"/>
				</c:if>
				<c:if test="${payOrderModel.currency!='usd' }">
				<fmt:formatNumber value="${payOrderModel.payAmonut}"  type="currency" currencySymbol="￥"/>
				</c:if>
				</span>
				&nbsp;&nbsp;已确认：￥${ qramount}, 未确认：<span style="font-weight:bold;color:orange;">￥${wqramount }</span>
				<input type="hidden" id="payAmount" value="${payOrderModel.payAmonut}">
				<input type="hidden" id="noAmount" value="${wqramount}">
			</div>
			<div class="tools">
				<a href="javascript:;" class="reloadback"><i class="icon-repeat"></i>返回</a>
			</div>
		</div>
	</div>
		<div class="portlet-body form" id="top" >
       			<c:forEach var="entity"  items="${rs}" varStatus="status1">
       			   <c:if test="${entity.payPlanStatus=='1' }">
					<div class="divheader" style="color:#777;background-color:#ccc;"><span style="font-weight:bold;font-size:15px;">订单：${entity.model.orderName} </span>,本次订单付款金额：<span style="font-weight:bold;"><fmt:formatNumber value="${entity.payOrderAmount}" type="currency" currencySymbol="￥"/></span>	&nbsp;&nbsp;<a href="javascript:;" class="order_show" id="${status1.index }"><i class="icon-plus"></i></a>
					          <div style="float:right;margin-right:10px;"><span class="icon-ok"></span>商务成本已确认</div> 
					   </c:if>
				   <c:if test="${entity.payPlanStatus!='1' }">
					<div class="divheader"><span style="font-weight:bold;font-size:15px;">订单：${entity.model.orderName} </span>,本次订单付款金额：<span style="font-weight:bold;"><fmt:formatNumber value="${entity.payOrderAmount}" type="currency" currencySymbol="￥"/></span>	&nbsp;&nbsp;<a href="javascript:;" class="order_show" id="${status1.index }"><i class="icon-plus"></i></a>
					    
					   </c:if>
					</div>
					<table cellpadding="0" cellspacing="0" border="0" class="table table-striped table-bordered table-hover table_order" id="order_${status1.index }" style="display:none;">
							<thead>
							<tr>
								<td class="sino_table_label" style="background:none repeat scroll 0 0 #F3F3F3">合同名称</td>
								<td class="sino_table_label" style="background:none repeat scroll 0 0 #F3F3F3">厂商简称</td>
								<td class="sino_table_label" style="background:none repeat scroll 0 0 #F3F3F3">产品型号</td>
								<td class="sino_table_label" style="background:none repeat scroll 0 0 #F3F3F3">数量</td>
								<td class="sino_table_label" style="background:none repeat scroll 0 0 #F3F3F3">单价</td>
								<td class="sino_table_label" style="background:none repeat scroll 0 0 #F3F3F3">总价</td>
							</tr>
						</thead>
						<tbody>
						 <c:forEach var="product" items="${entity.model.businessOrderProductModel}" varStatus="status">
							<tr>
								<td >${product.salesContractModel.contractName}</td>
								<td >${product.vendorCode }</td>
								<td >${product.productNo }</td>
								<td >${product.quantity }</td>
								<td ><fmt:formatNumber value="${product.unitPrice }" type="currency" currencySymbol="￥"/> </td>
								<td ><fmt:formatNumber value="${product.subTotal }" type="currency" currencySymbol="￥"/></td>
							</tr>
						</c:forEach>
						</tbody>
				</table>
					<c:set var="fido" value="${fn:trim(status1.index)}"/>  
				<c:forEach var="sales" items="${entity.sales}" varStatus="status">
				
				          <div style="width:960px;margin-top:0px;border-bottom: 1px solid #ccc;padding-top:5px;">
				          		  <div  style="float:left;width:400px;line-height:34px;margin-left:23px;"><a href="${ctx }/sales/contract/detail?id=${sales.id}&flowStep=SHOW" target="_blank"><span style="">${sales.contractName }</span></a><br/>${sales.contractCode }&nbsp;&nbsp;&nbsp;&nbsp;${sales.creatorName }</div>
					              <div  style="float:left;width:150px;line-height:34px;">合同金额<br><span style="font-weight:bold;"><fmt:formatNumber value="${sales.contractAmount }" type="currency" currencySymbol="￥"/> </span>&nbsp;<a href="javascript:;" class="sales_show"  id="${fido}${status.index }" ><i class="icon-plus"></i></a>
					              </div>
					              <div  style="float:left;width:180px;line-height:34px;">
									<c:if test="${sales.busiCost !=''}">
					                    已确认成本<br><span style="font-weight:bold;color:green;"><fmt:formatNumber value="${sales.busiCost }" type="currency" currencySymbol="￥"/></span>
					              </c:if>
					              <c:if test="${sales.busiCost==''}">
					                    已确认成本<br>￥0.00
					              </c:if>
					              </div>
					               <c:if test="${entity.payPlanStatus!='1' }">
					              <div  style="float:right;line-height:34px; margin-right: 10px;">本次成本:<input type="text" style="margin-bottom:0px;width:100px;" id="salesCost_${fido }${status.index }" name="${sales.contractCode }"  planid="${entity.payPlanId}" contractid="${sales.id}" busicost="${sales.busiCost}"/></div>
					               </c:if>
					               <div style="height:80px;"></div>
					           <table id="_sales_${fido }${status.index }" name="_sales_${status.index }" class="table  table-bordered table_order"  style="width:940px;margin-top:0px;display:none;margin-left:10px;">
					           	<tr style="background-color: #f3f3f3;color: #888888;">
								  <th style="width:10%;">产品类型</th>
								  <th style="width:10%;">厂商简称</th>
								  <th style="width:15%;">产品型号</th>
								  <th style="width:10%;">数量</th>
								  <th style="width:10%;">单价</th>
								  <th style="width:10%;">下单数量</th>
								  <th style="width:10%;">总价</th>
								  
					          </tr>
					          	<c:forEach var="product"  items="${sales.ordersaleproducts}" varStatus="status1">
									<tr class="_product_list" id="trall_${status1.index }">
										<td class="sino_table_label" >${product.typeName }</td>
										<td class="sino_table_label" >${product.partnerName }</td>
										<td class="sino_table_label" >${product.pName }</td>
										<td class="sino_table_label" >${product.orderQua }</td>
										<td class="sino_table_label"><fmt:formatNumber value="${product.unitprice }" type="currency" currencySymbol="￥"/></td>
										<td class="sino_table_label">${product.ordernum }</td>
										<td class="sino_table_label" name="prductAmount" tdvalue="${product.subtotal }"><fmt:formatNumber value="${product.subtotal }" type="currency" currencySymbol="￥"/></td>
									</tr>
								</c:forEach>
								<tr>
									 <td colspan=7 style="padding:8px;font-size:14px;">
										<div  style="float:right;width:200px;">合计金额:<span style="font-weight:bold;" id="totallProduct_${fido }${status.index }"></span></div>
									</td>
								</tr>
					          </table>
				          </div>
				
					   
				</c:forEach>
				</c:forEach>
				
							<!--按钮组-->
			<div id="bottom_button" class="handprocess_btngroup">
       			 <a id='_eoss_business_cost' role="button" class="btn btn-primary"><i class="icon-ok"></i>提交</a>
			     <a id='_eoss_business_order_back' role="button" class="btn"><i class="icon-remove-circle"></i>关闭</a>
			</div>
		</div>
	<script language="javascript">

		seajs.use('js/page/sales/funds/busiCostInfo',function(busiCostInfo){
			busiCostInfo.init();
		});    
	</script>
</body>
</html>
