<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/global.jsp"%>
<%@ page import="com.sinobridge.systemmanage.util.Global"%> 
<%@ page import="com.sinobridge.systemmanage.vo.SystemUser"%> 
<html>
<head>
	<title>商务管理</title>
	<%@ include file="/common/include-base-boostrap-styles.jsp" %>
	<link href="${ctx}/skin/default/sales/sale.css" rel="stylesheet"/>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formSelect.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formstyle.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/dialog.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/tip.css" type="text/css" ></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/user-selection.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formSearchTip.css" type="text/css" ></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/jquery-ui-1.8.17.custom.css" type="text/css" ></link>
	<link rel="stylesheet" href="${ctx}/skin/default/editgrid/editgrid.css" type="text/css" />

<style type="text/css">
/*此处是销售合同工单的样式  */
</style>

	<script src="${ctx}/js/plugins/seajs/sea.js" id="seajsnode" type="text/javascript"></script>
	<script src="${ctx}/js/plugins/seajs/config.js" type="text/javascript"></script>
</head>
<body>
 	<div class="salecontent">
		<div class="top">
			<div class="caption">
				<i class="icon-reorder"></i>&nbsp;&nbsp;&nbsp;关闭销售合同
				<span class="tright" >合同编号：${model.salesContract.contractCode}</span>
			</div>
			<div class="tools">
				<!--a href="javascript:;" class="reload"><i class="icon-repeat"></i>返回</a-->
			</div>
		</div>
	</div>
		<div class="portlet-body form">
			<!-- BEGIN FORM-->
			<form id='_sino_eoss_sales_contract_approveForm' name='_sino_eoss_sales_contract_approveForm' class='form-horizontal' >
				<!--隐藏字段start -->
				<!--  隐藏的合同ID-->
				<input type='hidden' name='id' id='id'  value="${model.id}"/>
				<!--  隐藏的合同ID-->
				<input type='hidden' name='salesId' id='salesId'  value="${model.salesContract.id}"/>
				<!--  隐藏的合同编码-->
				<input type='hidden' id='contractCode' name='contractCode'  value="${model.salesContract.contractCode}" />
				<!--  隐藏的合同类型ID-->
				<input type="hidden" name="contractTypeId" id="contractTypeId" value="${model.salesContract.contractType}">
				<!--隐藏的结算币种，用于回显-->
				<input type='hidden' name='accountCurrency' id='_eoss_sales_accountCurrencyId'  value="${model.salesContract.accountCurrency}"/>
				<!--隐藏的发票类型，用于回显-->
				<input type='hidden' name='invoiceType' id='_eoss_sales_invoiceTypeId'  value="${model.salesContract.invoiceType}"/>
				<!--隐藏的收款方式，用于回显-->
				<input type='hidden' name='receiveWay' id='_eoss_sales_receiveWayId'  value="${model.salesContract.receiveWay}"/>
				<!--隐藏的客户ID，用于回显-->
				<input type='hidden' name='customerId' id='_eoss_sales_customerId'  value="${model.salesContract.customerId}"/>
				<!--隐藏字段end -->
			<!-- 小标题start -->
			<br>
			<br>
			<div class=" row-inline" >
				<h4 class="form-section-title">合同基本信息</h4>
			</div>
			<!-- 小标题end -->
			<!-- 1行start -->
			<br>
			<div class=" row-inline" >
				<div class=''>
					<label class='row-label' for='contractName' >合同名称：</label>
					<div class='row-span-oneColumn'>
						<span>${model.salesContract.contractName}</span>
					</div>
				</div>
			</div>
			<!-- 1行end -->
			<!-- 2行start -->
			<%-- <div class=" row-inline" >
				<div class=''>
					<label class='row-label' for='contractName' >合同简称：</label>
					<div class='row-span'>
						<span>${model.salesContract.contractShortName}</span>
					</div>
				</div>
			</div> --%>
			<div class=" row-inline" >
				<div class=''>
					<label class='row-label' for='contractName' >合同类型：</label>
					<div class='row-span'>
						<span id="contractType"></span>
					</div>
				</div>
			</div>
			<div class=" row-inline" >
				<div class=''>
					<label class='row-label' for='contractName' >合同金额：</label>
					<div class='row-span'>
						<span id="contractAmount">${model.salesContract.contractAmount}元</span>
					</div>
				</div>
			</div>
			<!-- 2行end -->
			<!-- 3行start -->
			<div class=" row-inline" >
				<div class=''>
					<label class='row-label' for='accountCurrency' >结算币种：</label>
					<div class='row-span'>
						<span id="accountCurrency"></span>
					</div>
				</div>
			</div>
			<div class=" row-inline" >
				<div class=''>
					<label class='row-label' for='invoiceType' >发票类型：</label>
					<div class='row-span'>
						<span id="invoiceType"></span>
					</div>
				</div>
			</div>
			<div class=" row-inline" >
				<div class=''>
					<label class='row-label' for='receiveWay' >收款方式：</label>
					<div class='row-span'>
						<span id="receiveWay"></span>
					</div>
				</div>
			</div>
			<!-- 3行end -->
			<!-- 分割行start -->
			<div class=" row-inline" >
				<hr size=1 class="dashed-inline">
			</div>
			<!-- 分割行end -->
			<!-- 小标题start -->
			<div class=" row-inline" >
				<h4 class="form-section-title">客户信息（甲方）</h4>
			</div>
			<!-- 小标题end -->
			<!-- 5行start -->
			<div class=" row-inline" >
				<div class=''>
					<label class='row-label' for='contractName' >行业选择：</label>
					<div class='row-span'>
						<span id="_industryName"></span>
					</div>
				</div>
			</div>
			<div class=" row-inline" >
				<div class=''>
					<label class='row-label' for='contractName' >行业客户：</label>
					<div class='row-span-twoColumn'>
						<span id="_customerIdtCustomerName"></span>
					</div>
				</div>
			</div>
			<!-- 5行end -->
			<!-- 6行start -->
			<div class=" row-inline" >
				<div class=''>
					<label class='row-label' for='contractName' >客户选择：</label>
					<div class='row-span'>
						<span id="_customerInfoName"></span>
					</div>
				</div>
			</div>
			<div class=" row-inline" >
				<div class=''>
					<label class='row-label' for='contractName' >联&nbsp;&nbsp;系&nbsp;&nbsp;人：</label>
					<div class='row-span'>
						<span id="_customerContacts"></span>
					</div>
				</div>
			</div>
			<div class=" row-inline" >
				<div class=''>
					<label class='row-label' for='contractName' >手机号码：</label>
					<div class='row-span'>
						<span id="_customerContactsPhone"></span>
					</div>
				</div>
			</div>
			<!-- 6行end -->
			<!-- 小标题start -->
			<%-- <div class=" row-inline" >
				<h4 class="form-section-title">公司信息(乙方)</h4>
			</div>
			<!-- 小标题end -->
			<!-- 7行start -->
			<div class=" row-inline" >
				<div class=''>
					<label class='row-label' for='contractName' >公司名称：</label>
					<div class='row-span-twoColumn'>
						<span >北京神州新桥科技有限公司</span>
					</div>
				</div>
			</div>
			<div class=" row-inline" >
				<div class=''>
					<label class='row-label' for='contractName' >客户经理：</label>
					<div class='row-span'>
						<span >${model.salesContract.creatorName }</span>
					</div>
				</div>
			</div>
			<!-- 7行end -->	
			<!-- 8行start -->
			<div class=" row-inline" >
				<div class=''>
					<label class='row-label' for='contractName' >开户银行：</label>
					<div class='row-span-twoColumn'>
						<span >中国工商银行紫竹院支行</span>
					</div>
				</div>
			</div>
			<div class=" row-inline" >
				<div class=''>
					<label class='row-label' for='contractName' >银行账户：</label>
					<div class='row-span'>
						<span >0200007609004774060</span>
					</div>
				</div>
			</div> --%>
			<!-- 8行end -->			
			<!-- 分割行start -->
			<div class=" row-inline" >
				<hr size=1 class="dashed-inline">
			</div>
			<!-- 分割行end -->
			<!-- 小标题start -->
			<div class=" row-inline" >
				<h4 class="form-section-title">设备信息</h4>
			</div>
			<!-- 小标题end -->
			<!-- 9行start -->	
			<div class=" row-inline" >
				<div class='table-tile-width'>
					<table id="_sino_eoss_sales_contract_products_table"class="table  table-bordered table-striped">
					  <thead>
					     <tr id="_contract_show" style="background-color: #f3f3f3;color: #888888;">
					      <th style="width:8%;text-align:center;">序号</th>
					      <th style="width:17%;text-align:center;">产品类型</th>
					      <th style="width:17%;text-align:center;">产品厂商</th>
					      <th style="width:17%;text-align:center;">产品型号</th>
					      <th style="width:11%;text-align:center;">数量</th>
					      <th style="width:12%;text-align:center;">单价</th>
					      <th style="width:18%;text-align:center;">合计</th>
					    </tr>
					    <tr id="_contract_hidden" style="background-color: #f3f3f3;color: #888888;display:none;">
					      <th style="width:5%;text-align:center;" >序号</th>
					      <th style="width:16%;text-align:center;">关联备货合同名称</th>
					      <th style="width:10%;text-align:center;" id="type_th">产品类型</th>
					      <th style="width:10%;text-align:center;" id="partner_th">产品厂商</th>
					      <th style="width:10%;text-align:center;" id="product_th">产品型号</th>
					      <th style="width:5%;text-align:center;">数量</th>
					      <th style="width:12%;text-align:center;">单价</th>
					      <th style="width:18%;text-align:center;">合计</th>
					    </tr>
					  </thead>
					  <tbody>
						<c:forEach var="salesContractProductModel" items="${model.salesContract.salesContractProductModel}" varStatus="status">
							<c:if test="${salesContractProductModel.relateDeliveryProductId != null&&salesContractProductModel.relateDeliveryProductId != '' }">
							<tr>
								<td style="text-align:center;">${ status.index + 1}</td>
								<td name="relateDeliveryProduct_contract" tid="${salesContractProductModel.relateDeliveryProductId }"></td>
								<td style="text-align:center;">${salesContractProductModel.productTypeName }</td>
								<td style="text-align:center;">${salesContractProductModel.productPartnerName }</td>
								<td style="text-align:center;">${salesContractProductModel.productName }</td>
								<td style="text-align:center;">${salesContractProductModel.quantity }</td>
								<td style="text-align:center;">${salesContractProductModel.unitPrice }</td>
								<td style="text-align:center;">${salesContractProductModel.totalPrice }</td>
							</tr>
						</c:if>
						<c:if test="${salesContractProductModel.relateDeliveryProductId == null||salesContractProductModel.relateDeliveryProductId == '' }">
							<tr>
								<td style="text-align:center;" name="relateDeliveryProduct_contract_">${ status.index + 1}</td>
								<td style="text-align:center;">${salesContractProductModel.productTypeName }</td>
								<td style="text-align:center;">${salesContractProductModel.productPartnerName }</td>
								<td style="text-align:center;">${salesContractProductModel.productName }</td>
								<td style="text-align:center;">${salesContractProductModel.quantity }</td>
								<td style="text-align:center;">${salesContractProductModel.unitPrice }</td>
								<td style="text-align:center;">${salesContractProductModel.totalPrice }</td>
							</tr>
						</c:if>
						</c:forEach>
					  </tbody>
					</table>
				</div>
			</div>
			<div class=" row-inline" >
				<hr size=1 class="dashed-inline">
			</div>
			<div class=" row-inline" >
				      <h4 class="form-section-title">订单信息</h4>
			    </div>
			    <div class=" row-inline" >
				<div class='table-tile-width'>
				<%-- <c:forEach var="orderProduct" items="${productmodel}" varStatus="status">
				<div class=''>
					<label class='row-label'>订单名称：</label>
					<div class='row-span'>
						<span id="orderName">${orderProduct.businessOrder.orderName}</span>
					</div>
				</div>
				<div class=''>
					<label class='row-label' >订单状态：</label>
					<div class='row-span'>
						<span id="orderStatus"></span>
						<input type="hidden" id="_orderStatus" value="${orderProduct.businessOrder.orderStatus}"/>
					</div>
				</div>
				
				<br> --%>
					<table id="_sino_eoss_order_products_table"class="table  table-bordered table-striped">
					<thead>
						<tr style="background-color: #f3f3f3;color: #888888;">
						    <td style="width:15%;text-align:center;">订单名称</td>
							<td style="width:15%;text-align:center;">产品型号</td>
							<td style="width:8%;text-align:center;">厂商编号</td>
							<td style="width:8%;text-align:center;">单价</td>
							<td style="width:8%;text-align:center;">数量</td>
							<!-- <td style="width:15%;text-align:center;">服务类型</td> -->
							<td style="width:15%;text-align:center;">总价</td>
						</tr>
					</thead>
					<tbody>
					 <c:forEach var="orderProduct" items="${productmodel}" varStatus="status">
						<tr>
							<td  style="text-align:center;" >${orderProduct.businessOrder.orderName}</td>
							<td  style="text-align:center;" >${orderProduct.productNo }</td>
							<td  style="text-align:center;" >${orderProduct.vendorCode }</td>
							<td  style="text-align:center;" >${orderProduct.unitPrice }</td>
							<td   style="text-align:center;">${orderProduct.quantity }</td>
							<%-- <td   style="text-align:center;"style="text-align:center;" id="serviceType${ status.index}">${orderProduct.serviceType }</td> --%>
							<td   style="text-align:center;">${orderProduct.subTotal }</td>
						</tr>
					</c:forEach>
					</tbody>
				</table>
				</div>
			</div>
			
			
			<div class=" row-inline" >
				<hr size=1 class="dashed-inline">
			</div>
			<!-- 分割行end -->
			<div class=" row-inline" >
				<h4 class="form-section-title">审批</h4>
			</div>
			<!-- 小标题end -->
			<!-- 审核第1行start -->
			<div class=" row-inline" >
				<div class=''>
					<label class="checkbox inline">
						同意<input type="radio" id="inlineCheckbox1" name="isAgree" value="1"  checked="checked" >
					</label>
					<label class="checkbox inline">
						驳回<input type="radio" id="inlineCheckbox2" name="isAgree" value="0">
					</label>
				</div>
			</div>
			<div class=" row-inline" >
				<div class=''>
					<label class='row-label' for='contractName' >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;预估成本：</label>
					<div class='row-input'>
						<input type="text" id="cost" name="cost" value="" placeholder='请输入预估成本' >
					</div>
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
			
			<br class='float-clear' />
			</form>
			<div class="modal-footer">
					<a id="dsave"  class="btn btn-primary" data-dismiss="modal" aria-hidden="true" >确定</a>
					<button id="_do_cancel"  class="btn" data-dismiss="modal" aria-hidden="true" >取消</button >
				</div>
			
			<!-- END FORM-->  
		</div>
	</div>
	<script language="javascript">
		seajs.use('js/page/business/closeContract/settlement',function(settlement){
			settlement.init();
		});    
	</script>
</body>
</html>
