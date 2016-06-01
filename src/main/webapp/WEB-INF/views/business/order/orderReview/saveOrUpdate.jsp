<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <%@ include file="/common/global.jsp" %>
 <%@ include file="/common/include-base-boostrap-styles.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>商务采购</title>
    <%@ include file="/common/include-base-boostrap-styles.jsp" %>
	<link rel="stylesheet" href="${ctx}/skin/default/room.css" type="text/css" />
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
<div class="salecontent">
		<div class="top">
			<div class="caption">
				<i class="icon-reorder"></i>&nbsp;&nbsp;&nbsp;修改订单信息
			    <span class="tright" >订单编号：${model.orderCode }</span>
			</div>
			<div class="tools">
				<!--a href="javascript:;" class="reload"><i class="icon-repeat"></i>返回</a-->
			</div>
		</div>
	</div>
		<!--表单、底部按钮 -->
		<div class="portlet-body form">
		<input type='hidden' id='_is_submit_product'  value="1"/>
		<form  id="_sino_eoss_cuotomer_updateform" class='form-horizontal' method="post" action="">
	    <input type="hidden" id="_eoss_customer_id" value="${model.id}" name="id"/>
	    <input type="hidden" id="_tableGridData" value="" name="tableGridData"/>
	    <input type="hidden" id="_contract" value="" name="contract"/>
	    <input type="hidden" id="_interPurchas" value="" name="interPurchas"/>
	    <input type="hidden" id="orderCode" name="orderCode"  value="${model.orderCode }" />
	    <input type="hidden" id="taskId" name="taskId" value="${taskId }">
	     <input type="hidden" id="createDate" name="createDate"  value="${model.createDate }" />
	    <input type="hidden" id="processInstanceId" name="processInstanceId" value="${model.processInstanceId }">
	    <!--保存为草稿还是提交的标志位-->
	    <input type='hidden' name='orderStatus' id='isSubmit' />
	
	<div class="handprocess_order " id="SheetDiv">
	<br>
	<h3>订单信息</h3>
		<ul class="clearfix" >
			<%-- <li id="field_userName" class="li_form" style="width: 350px;">
				<label class="editableLabel"><span class="red">*</span>订单编号：</label>
				<input type="text" id="orderCode" name="orderCode"  class="row-input"  value="${model.orderCode }"/>
			</li> --%>
				<li id="field_userName" class="li_form">
				<label class="editableLabel2">订单类型：</label>
				<div id="ordertypeString"></div> 
		        <input type="hidden" name="orderType" id="orderTypeId" value="${model.orderType}"/>
				<%-- <label class="editableLabel"><span style="color:red;">*</span>订单类型：</label>
				<div id="_eoss_business_ordertype">
		            <input type="hidden" name="orderType" id="_eoss_business_ordertypeId" value="${model.orderType}"/>
			    </div> --%>
			</li>
			<li id="field_userName" class="li_form" style="width:600px">
				<label class="editableLabel"><span style="color:red;">*</span>订单名称：</label>
				<input type="text" id="orderName" name="orderName"  class="row-input-oneColumn"  value="${model.orderName }"/>
			</li>
		</ul>
		<ul class="clearfix" >
			<li id="field_userName" class="li_form">
				<label  class="editableLabel"><span style="color:red;">*</span>采购分类：</label>
				<div id="_eoss_business_purchaseType">
		            <input type="hidden" name="purchaseType" id="_eoss_business_purchaseTypeId"  value="${model.purchaseType}"/>
			    </div>
			</li>
			<li id="field_userName" class="li_form">
				<label class="editableLabel"><span style="color:red;">*</span>发票类型：</label>
				<div id="_eoss_business_invoiceType">
		           <input type="hidden" name="invoiceType" id="_eoss_business_invoiceTypeId"  value="${model.invoiceType}"/>
			    </div>
			</li>
			<li id="field_userName" class="li_form">
				<label class="editableLabel"><span style="color:red;">*</span>服务期(月)：</label>
				<input type="text" id="serviceDate" name="serviceDate" class="row-input" value="${model.serviceDate} "/>
			</li>
		</ul>
		<ul class="clearfix" >
			<%-- <li id="field_userName" class="li_form">
				<label class="editableLabel"><span style="color:red;">*</span>到货时间：</label>
				    <div style="position: relative;" class="input-append date">
						 <input data-format="yyyy-MM-dd"  name="expectedDeliveryTime"  type="text"  style="width:150px" value="${model.expectedDeliveryTime}"></input>
						    <span class="add-on">
						      <i data-time-icon="icon-time" data-date-icon="icon-calendar">
						      </i>
							</span>
					  </div>
			</li> --%>
			<li id="field_userName" class="li_form" >
				<label for="orderType" class="editableLabel"><span style="color:red;">*</span>到货地点：</label>
				<div id="_business_deliveryAddress">
		            <input type="hidden" name="deliveryAddress" id="_business_deliveryAddressId"  value="${model.deliveryAddress}"/>
			    </div>
			</li>
			<li id="field_userName" class="li_form">
				<label for="orderType" class="editableLabel"><span style="color:red;">*</span>付款方式：</label>
				<div id="_eoss_business_paymentMode">
		            <input type="hidden" name="paymentMode" id="_eoss_business_paymentModeId"  value="${model.paymentMode}"/>
			    </div>
			</li>
			<li id="field_userName" class="li_form" >
				<label for="orderType" class="editableLabel"><span style="color:red;">*</span>结算币种：</label>
					<div id="_accountCurrency">
						<input type="hidden" name="accountCurrency" id="_eoss_business_accountCurrency"  value="${model.accountCurrency}"/>
					</div> 
			</li>
		</ul>
		<%-- <ul class="clearfix" >
			<li id="field_userName" class="li_form" >
				<label for="orderType" class="editableLabel"><span style="color:red;">*</span>利润类型：</label>
				<div id="_eoss_orderProfits">
		            <input type="hidden" name="orderProfits" id="_orderProfits"  value="${model.orderProfits}"/>
			    </div>
			</li>
			<li id="field_userName" class="li_form" >
				<label  class="editableLabel"><span style="color:red;">*</span>预估值：</label>
		            <input type="text" id="profitsValue" name="profitsValue" class="row-input" value="${model.profitsValue}" />
			</li>
		</ul> --%>
        <div style="clear:both;"></div>
        
        <div class=" row-inline" >
				<hr size=1 class="dashed-inline">
		</div>
		
          <h3>供应商信息(乙方)</h3>
		<ul class="clearfix">
		    <li id="field_userName" class="li_form">
				<label class="editableLabel"><span style="color:red;">*</span>供应商类型：</label>
				<div id="_supplierType">
				<input type="hidden" name="supplierType" id="_supplierTypeId" class="row-input" value="${model.supplierInfoModel.supplierType}"/>
				</div>
			</li>
			<li id="field_userName" class="li_form" >
				<label class="editableLabel"><span style="color:red;">*</span>供应商名称：</label>
				<input type="text" id='supplierInfoSelect'  name="supplierName"  class="row-input"  value="${model.supplierInfoModel.supplierName} "/>
				<input type="hidden" id='supplierId' name="supplierId" value='${model.supplierInfoModel.id}'/>
				<input type="hidden" id='suName'  name="supplierName"  class="row-input"  value="${model.supplierInfoModel.shortName} "/>
			</li>
			 <li id="field_userName" class="li_form">
				<label class="editableLabel"><span style="color:red;">*</span>联系人：</label>
				<input type="text" id='_contactName'  name="contactName"  class="row-input" value="${model.supplierContactsModel.contactName}"/>
				<input type="hidden" id='contactId'  name="contactId" value="${model.supplierContactsModel.id}" >
				<input type="hidden" id='coName'  name="contactName"  value="${model.supplierContactsModel.contactName}"/>
			</li>
		</ul>
		<ul class="clearfix">
			<li id="field_userName" class="li_form" >
				<label class="editableLabel2" >供应商编号：</label><span id='supplierCode'>${model.supplierInfoModel.supplierCode}</span>
				<input type="hidden" id="supplierCode" name="supplierCode" value="${model.supplierInfoModel.supplierCode}"/>
			</li>
			<li id="field_userName" class="li_form" style="width:600px" >
				<label class="editableLabel2">地址：</label><span id='address'>${model.supplierInfoModel.address}</span>
				<input type="hidden" id="address" class="row-input-oneColumn" readonly="readonly" value="${model.supplierInfoModel.address}"/>
			</li>
		</ul>
		<ul class="clearfix">
		    <li id="field_userName" class="li_form" >
				<label class="editableLabel2">邮政编码：</label><span id='zipCode'>${model.supplierInfoModel.zipCode}</span>
				<input type="hidden" id="zipCode" class="row-input" readonly="readonly" value="${model.supplierInfoModel.zipCode}"/>
			</li>
			<li id="field_userName" class="li_form" style="width:600px"  >
				<label class="editableLabel2">开户银行：</label><span id='bankNameSupplier'>${model.supplierInfoModel.bankName}</span>
				<input type="hidden" id="bankNameSupplier" class="row-input-oneColumn" readonly="readonly" value="${model.supplierInfoModel.bankName}"/>
			</li>
		</ul>
		<ul class="clearfix">
		    <li id="field_userName" class="li_form" >
				<label class="editableLabel2" >银行账号：</label><span id='BankAccount'>${model.supplierInfoModel.bankAccount}</span>
				<input type="hidden" id="BankAccount" class="row-input" readonly="readonly" value="${model.supplierInfoModel.bankAccount}"/>
			</li>
		    <li id="field_userName" class="li_form" >
				<label class="editableLabel2" >传真：</label><span id='fax'>${model.supplierInfoModel.fax}</span>
				<input type="hidden" id="fax" name="fax" class="row-input" readonly="readonly" value="${model.supplierInfoModel.fax}"/>
			</li>
			<li id="field_userName" class="li_form">
				<label class="editableLabel2" >联系人电话：</label><span id='_contactPhone'>${model.supplierContactsModel.contactPhone}</span>
				<input type="hidden" id="_contactPhone" class="row-input" readonly="readonly" value="${model.supplierContactsModel.contactPhone}"/>
			</li>
		</ul>
		<ul class="clearfix">
		    <li id="field_userName" class="li_form">
				<label class="editableLabel2">联系人手机号：</label><span id='_contactTelPhone'>${model.supplierContactsModel.contactTelPhone}</span>
				<input type="hidden" id="_contactTelPhone" class="row-input" readonly="readonly"  value="${model.supplierContactsModel.contactTelPhone}"/>
			</li>
		</ul>
		<div style="clear:both;"></div>
		<div class=" row-inline" >
				<hr size=1 class="dashed-inline">
		</div>
		<h3>利润预估（请至少填写一个！）</h3>
			<ul class="clearfix">
				<li id="field_userName" class="li_form" >
					<label class="editableLabel">厂商折扣(%)：</label>
					<input type="text" id="partnerPV" name="partnerPV" class="row-input" value="${model.partnerPV}"/>
				</li>
				<li id="field_userName" class="li_form" style="width: 260px;">
					<label class="editableLabel" style="width: 60px;">利润：</label>
					<input type="text" id="profitsValue" name="profitsValue" class="row-input" value="${model.profitsValue}"/>
				</li>
				<li id="field_userName" class="li_form" style="width: 350px;" >
					<label class="editableLabel" style="width: 110px;" >客户利润率(%)：</label>
					<input type="text" id="customerPV" name="customerPV" class="row-input" value="${model.customerPV}"/>
				</li>
			</ul>
		<div style="clear:both;"></div>
		<div class=" row-inline" >
				<hr size=1 class="dashed-inline">
		</div>
		
		<h3>公司信息（甲方）</h3>
		<ul class="clearfix">
			<li id="field_userName" class="li_form" style="width:600px">
				<label for="companyName" class="editableLabel2">公司名称：</label>${model.companyName}
				<input type="hidden" id="companyName" name="companyName"   value="${model.companyName}"/>
			</li>
			<li id="field_userName" class="li_form">
				<label for="companyName" class="editableLabel2">开户银行：</label>${model.bankName}
				<input type="hidden" id="bankName" name="bankName"  value="${model.bankName}"/>
			</li>
		</ul>
		<ul class="clearfix">
			<li id="field_userName" class="li_form" style="width:600px">
				<label for="companyAddress" class="editableLabel2">公司地址：</label>${model.companyAddress}
				<input type="hidden" id="companyAddress" name="companyAddress" value="${model.companyAddress}"/>
			</li>
			<li id="field_userName" class="li_form">
				<label for="companyAddress" class="editableLabel2">银行账号：</label>${model.bankAccount}
				<input type="hidden" id="bankAccount" name="bankAccount"  value="${model.bankAccount}"/>
			</li>
		</ul>
		<div style="clear:both;"></div>
		<div class=" row-inline" >
				<hr size=1 class="dashed-inline">
		</div>

		
			<h3>产品添加</h3>
<!-- 				<div Style="margin-left: 863px;width: 88px;margin-bottom: 5px">
				     <a id='selectContract' role="button" href="#myModal1" class="btn btn-success"><i class="icon-plus"></i>选择</a>
				</div>
 -->
 				<div>
				     	<a id='selectContract' role="button" href="#myModal1" class="btn btn-success" style="margin-left: 10px;width: 50px;margin-bottom: 5px"><i class="icon-plus" style="width:50px;"></i>选择</a>
						<a id='remove_product'style="margin-left: 665px;width: 70px;margin-bottom: 5px" class='btn btn-danger _remove_product'><i >删除勾选</i></a>
						<a id='remove_product_else' style="margin-right: 10px;width: 70px;margin-bottom: 5px" class='btn btn-danger _remove_product'><i >删除其他</i></a>
				</div>

	    <input type='hidden' name='tableData' id='tableData' />
</div>
		    <div id="alertMsg"></div>
			<table id="product_add" cellpadding="1" cellspacing="1" border="5" class="table table-striped table-bordered table-hover">
				<thead>
					<tr>
					     <c:if test="${mark=='HT'}">
						<td  style="background:none repeat scroll 0 0 #F3F3F3;font-weight:bold;" width="13%">合同名称</td>
						</c:if>
						<c:if test="${mark=='NC'}">
						<td style="background:none repeat scroll 0 0 #F3F3F3;font-weight:bold;" width="13%">内采名称</td>
						 </c:if>
					       <td width="8%">产品类型</td>
						<td width="8%">厂商简称</td>
					    <td width="10%">产品型号</td>
						<td width="8%">数量</td>
						<td width="8%">单价</td>
						<td width="8%">总价</td>
						<!-- <td width="10%">服务类型</td>
						<td width="10%">服务开始时间</td>
						<td width="10%">服务结束时间</td> -->
						<td width="4%"><input id="checkall" type="checkbox"  value=0 /></td>
					</tr>
				 <c:forEach var="product" items="${model.businessOrderProductModel}" varStatus="status">
				</thead>
				<tbody>
				  <c:if test="${product.salesContractModel.id!=null}">
					<tr style='text-algin:center' id='tr_${ status.index + 1}' name="trName" num='${ status.index + 1}' tdValue="${product.salesContractModel.id}">
						<td class="sino_table_label" id='contractName${ status.index + 1}' name="contractName" tdValue="${product.salesContractModel.contractName}" >${product.salesContractModel.contractName}</td>
						<td class="sino_table_label" id='productType${ status.index + 1}' name="productType" tdValue="${product.productType }">${product.productType }</td>
						<td class="sino_table_label" id='vendorCode${ status.index + 1}' name="vendorCode" tdValue="${product.vendorCode }">${product.vendorCode }</td>
						<td class="sino_table_label" id='productNo${ status.index + 1}' name="productNo" tdValue="${product.productNo }">${product.productNo }</td>
						<td class="sino_table_label" ><input class="quantityStyle" num='${ status.index + 1}' type="text" style="width:90%" id='quantity${ status.index + 1}' name="quantity" value="${product.quantity }" inputCount="${product.quantity }" /></td><input type="hidden" id="productNum${ status.index + 1}" name="productNum" value="0"/>
						<td class="sino_table_label" ><input  class="unitPriceStyle" num='${ status.index + 1}' type="text" style="width:90%" id='unitPrice${ status.index + 1}' name="unitPrice" value="${product.unitPrice }" /></td>
						<td class="sino_table_label" ><input class="sumStyle" num='${ status.index + 1}' type="text" style="width:90%" id='sub${ status.index + 1}' name="sub" value="${product.subTotal }"  /></td>
						<%-- <td class="sino_table_label" ><div id='inter_serviceType${ status.index + 1}'><input id='serviceType${ status.index + 1}' name='serviceType' type='hidden' style='width:60%' svalue='${product.serviceType }'/></div></td> --%>
						<%-- <td class="sino_table_label" ><div id='inter_serviceType${ status.index + 1}' value='${product.serviceType }'></div></td>
						<td class="sino_table_label" ><div style='position: relative;' class='input-append date'><input type='text' data-format='yyyy-MM-dd' id='serviceStartTime${ status.index + 1}' style='width:55px;font-size: 11px;' value='${product.serviceStartTime }'/><span class='add-on'><i data-time-icon='icon-time' data-date-icon='icon-calendar'></i></span></div></td>
 	 					<td class="sino_table_label" ><div style='position: relative;' class='input-append date'><input type='text' data-format='yyyy-MM-dd' id='serviceEndTime${ status.index + 1}' style='width:55px;font-size: 11px;' value='${product.serviceEndTime }'/><span class='add-on'><i data-time-icon='icon-time' data-date-icon='icon-calendar'></i></span></div></td>
						 --%>
						<td class="sino_table_label" id='productId${ status.index + 1}' style='display:none'  name="productId"  tdValue="${product.salesContractProductModel.id}"></td>
						<td class="sino_table_label" id ='mark${ status.index + 1}' style='display:none' name='mark ' tdValue="${product.mark}"></td>
						<td><input type='checkbox' name='myCheckbox' value="${ status.index + 1}" /></td>
						<%-- <td class="sino_table_label"><a serial_num='${ status.index + 1}'  id='_remove_product_${ status.index + 1}' style="width:60%" class='btn btn-danger _remove_product'><i style="width:100%">删除</i></a></td> --%>
					</tr>
					</c:if>
					<c:if test="${product.interPurchasProduct.id!=null}">
						<tr style='text-algin:center' id='tr_${ status.index + 1}' name="trName" num='${ status.index + 1}' tdValue="${product.interPurchas.id}">
							<td class="sino_table_label" id='contractName${ status.index + 1}' name="contractName" tdValue="${product.interPurchas.purchasName}" >${product.interPurchas.purchasName}</td>
							<td class="sino_table_label" id='productType${ status.index + 1}' name="productType" tdValue="${product.productType }">${product.productType }</td>
							<td class="sino_table_label" id='vendorCode${ status.index + 1}' name="vendorCode" tdValue="${product.vendorCode }">${product.vendorCode }</td>
							<td class="sino_table_label" id='productNo${ status.index + 1}' name="productNo" tdValue="${product.productNo }">${product.productNo }</td>
							<td class="sino_table_label" ><input class="quantityStyle" num='${ status.index + 1}' type="text" style="width:90%" id='quantity${ status.index + 1}' name="quantity" value="${product.quantity }"  inputCount="${product.quantity }" /></td><input type="hidden" id="productNum${ status.index + 1}" name="productNum" value="0"/>
							<td class="sino_table_label" ><input  class="unitPriceStyle" num='${ status.index + 1}' type="text" style="width:90%" id='unitPrice${ status.index + 1}' name="unitPrice" value="${product.unitPrice }" /></td>
							<td class="sino_table_label" ><input class="sumStyle" num='${ status.index + 1}' type="text" style="width:90%" id='sub${ status.index + 1}' name="sub" value="${product.subTotal }"  /></td>
							<%-- <td class="sino_table_label" ><div id='inter_serviceType${ status.index + 1}'><input id='serviceType${ status.index + 1}' name='serviceType' type='hidden' style='width:60%' svalue='${product.serviceType }'/></div></td> --%>
						<%-- 	<td class="sino_table_label" ><div id='inter_serviceType${ status.index + 1}' value='${product.serviceType }'></div></td>
							<td class="sino_table_label" ><div style='position: relative;' class='input-append date'><input type='text' data-format='yyyy-MM-dd' id='serviceStartTime${ status.index + 1}' style='width:55px;font-size: 11px;' value='${product.serviceStartTime }'/><span class='add-on'><i data-time-icon='icon-time' data-date-icon='icon-calendar'></i></span></div></td>
 	 					    <td class="sino_table_label" ><div style='position: relative;' class='input-append date'><input type='text' data-format='yyyy-MM-dd' id='serviceEndTime${ status.index + 1}' style='width:55px;font-size: 11px;' value='${product.serviceEndTime }'/><span class='add-on'><i data-time-icon='icon-time' data-date-icon='icon-calendar'></i></span></div></td>
						 --%>
							<td class="sino_table_label" id='productId${ status.index + 1}' style='display:none'  name="productId"  tdValue="${product.interPurchasProduct.id}"></td>
							<td class="sino_table_label" id ='mark${ status.index + 1}' style='display:none' name='mark ' tdValue="${product.mark}"></td>
							<%-- <td class="sino_table_label"><a serial_num='${ status.index + 1}'  id='_remove_product_${ status.index + 1}' style="width:60%" class='btn btn-danger _remove_product'><i style="width:100%">删除</i></a></td> --%>
							<td><input type='checkbox' name='myCheckbox' value="${ status.index + 1}" /></td>
						</tr>
					</c:if>
				  </c:forEach>
				</tbody>
			</table>
		  <li id="field_userName" class="li_form" style="width:200px;float: right;">
				<label Style="width:70px;"> 订单金额：</label><span id='tatol' style="margin-left:20px;"><fmt:formatNumber value="${model.orderAmount }" type="currency" currencySymbol="￥"/></span>
				<input type="hidden" id="orderAmount" name="orderAmount"  value="${model.orderAmount}"/>
				<%-- <input type="text" id="orderAmount" name="orderAmount" class="row-input" style="margin-left: 738px;margin-top: -25px;" readonly="readonly"  value="${model.orderAmount}"/> --%>
		</li>		
		<!--返点使用  -->
			<ul class="clearfix" style="position: relative;">
			   <li id="field_userName" class="li_form" style="width: 400px;">
				     <label style="color: #eeb422; width:110px;"><span style="color:red;">*</span>是否用返点数：</label>
				    <input type="radio" id="check_yes" name="spotChange" value="1" <c:if test="${model.spotNum>0 }">checked</c:if> />是&nbsp;&nbsp;&nbsp;
				    <input type="radio" id="check_no" name="spotChange" value="0" <c:if test="${model.spotNum<1 }">checked</c:if>/>否&nbsp;&nbsp;&nbsp;
			   </li>
			</ul>
			<%-- <ul class="_is_hidden" style="display:none;">	   
				<li class="li_form" style="width:600px">
			         <label class="editableLabel2" >可用返点数：</label>
			         <span id="nowSpotNum" name="nowSpotNum">0</span>
			   </li>
			 </ul>
			 <ul class="_is_hidden" style="display:none;">	
			   <li class="li_form" style="width:600px">
			         <label class="editableLabel" ><span style="color:red;">*</span>使用点数：</label>
			         <input type="text" id="spotNum" name="spotNum" value="${model.spotNum }"/>
			         <input type="hidden" id ="oldSpotNum" name="oldSpotNum" value="${model.spotNum }"/>
			   </li>
			  </ul>		 --%>
			   <table id="spot_order" class="table table-striped table-bordered table-hover _is_hidden" style="display:none;" >
			 	<thead>
					<tr>
						<td width="8%">选择</td>
					    <td width="15%">返点厂商</td>
						<td width="8%">可用返点数</td>
					    <td width="9%">使用点数</td>
					</tr>
				</thead>
			 </table>
			 <input type="hidden" id="spotNum" name="spotNum" value="${model.spotNum }"/>
			 <input type="hidden" id="spotSupplier" name="spotSupplier" value="${model.spotSupplier }"/>
			 <input type="hidden" id="spotId" name="spotId" value=""/>
			 <input type="hidden" id ="oldSpotNum" name="oldSpotNum" value="${model.spotNum }"/>
			 <input type="hidden" id ="oldspotSupplier" name="oldspotSupplier" value="${model.spotSupplier }"/>
		<div style="clear:both;"></div>
		<div class=" row-inline" >
				<hr size=1 class="dashed-inline">
		</div>
		<h3> 审批日志</h3>
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
		
</form>
			<!-- editTable -->
       <div id="dailogs1" class="modal hide fade" role="dialog" tabIndex="-1" style="width:780px;height: 510px;">
		   <div class="modal-header">
		         <a type="button" class="close" data-dismiss="modal" aria-hidden="true">×</a>
		         <h3 id="dtitle"></h3>
  		   </div>
		    <div class="modal-body" style="width:750px;">
			     <div id="dialogbody" ></div>
		    </div>
		    <div id="bottom_button" class="modal-footer" style="text-align: center;">
        		<!-- <input id="ok_Contract_Add" type="button" value="确定" class="btn btn-success"/> -->
        		<a id='ok_Contract_Add' role="button" class="btn btn-success"><i class="icon-ok"></i>确定</a>
    		</div>
      </div>

	<!--按钮组-->
    		<div id="bottom_button" class="handprocess_btngroup">
        		<a id='sp_Add' role="button" class="btn btn-success"><i class="icon-ok"></i>从新提交</a>
        		<a id='no_Add' role="button" class="btn">放弃并删除</a>
				<a id='no_back' role="button" class="btn">取消</a>
    		</div>
     </div>
</div>
 <script language="javascript">
 var form = ${form}; 
 seajs.use('js/page/business/order/orderReview/saveOrUpdate',function(saveOrUpdate){
		saveOrUpdate.init();
    });
</script>
</body>
</html>