<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/global.jsp"%>
<%@ include file="/common/include-base-boostrap-styles.jsp" %>

<html>
<head>
    <title>商务经理审批</title>
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
label{
 line-height: 24px;
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
    padding: 1px 0;
    width: 300px;
}

.table {
    margin-bottom: 20px;
    width: 95%;
    margin-left: 18px;
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
ul{
margin:0 0 0 25px;
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
				<i class="icon-reorder"></i>&nbsp;&nbsp;&nbsp;订单审批
			    <span class="tright" >订单编号：${model.orderCode }</span>
			</div>
			<div class="tools">
				<!--a href="javascript:;" class="reload"><i class="icon-repeat"></i>返回</a-->
			</div>
		</div>
	</div>	
	<div class="portlet-body form">
			<form id="_eoss_business_order" novalidate="novalidate" style="" method="post">
				<input type="hidden" name="id" id="id" value="${model.id }">
				<input type="hidden" name="invoiceType" id="_invoiceType" value="${model.invoiceType }">
				<input type="hidden" name="orderType" id="_orderType" value="${model.orderType}">
				<input type="hidden" name="purchaseType" id="_purchaseType" value="${model.purchaseType}">
				<input type="hidden" name="local" id="local" value="商务经理审批"/>
				<div class="handprocess_order" id="SheetDiv">
<br>
		        <h3>订单信息</h3>				
					<ul class="clearfix"  >
						<li id="field_userName" class="li_form" style="width:600px" >
							<label class="editableLabel" >订单名称：</label>${model.orderName }
						</li>
						<li id="field_userName" class="li_form"  style="color:#e99031;">
					          <label  class="editableLabel">订单金额：</label><i class="icon-jpy"></i>${model.orderAmount } 元
				         </li>
					</ul>
					<ul class="clearfix" >
						<li id="field_userName" class="li_form" >
							<label  class="editableLabel">订单类型：</label>
							<div id="orderType" class="div_layout"></div>
						</li>
						<li id="field_userName" class="li_form" >
							<label  class="editableLabel">采购分类：</label>
							<div id="purchaseType" class="div_layout"></div>
						</li>
						
						<li id="field_userName" class="li_form" >
							<label class="editableLabel">服务期(月)：</label>${model.serviceDate} 
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
						<li id="field_userName" class="li_form" >
							<label  class="editableLabel">结算币种：</label><span id="currency">${model.accountCurrency }</span>
							<input type="hidden" id="accountCurrency" value="${model.accountCurrency }"/>
						</li>
						<%-- <li id="field_userName" class="li_form" >
							<label for="orderType" class="editableLabel">利润类型：</label>
							<div id="_eoss_orderProfits">
					            <input type="hidden" name="orderProfits" id="_orderProfits" value="${model.orderProfits}"/>
						    </div>
						</li>
						<li id="field_userName" class="li_form" >
							<label  class="editableLabel">预估值：</label>${model.profitsValue}
						</li> --%>
			        </ul>
					<div style="clear:both;"></div>
					<div class=" row-inline" >
				         <hr size=1 class="dashed-inline">
		            </div> 
		         
				<h3>供应商信息（乙方）</h3>
					<ul class="clearfix">
					    <li id="field_userName" class="li_form">
							<label class="editableLabel">供应商类型：</label>
							<div id='_supplierType'>
							<input type="hidden" id="supplierType" name="supplierType" value="${model.supplierInfoModel.supplierType}">
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
					<div class=" row-inline" >
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
		        <div class=" row-inline" >
						<hr size=1 class="dashed-inline">
				</div>
		
				
				<h3>产品信息</h3>
					<table border="0" style="width: 100%" class="sino_table_body" id="product_table">
				<thead>
						<tr>
						 <c:if test="${mark=='HT'}">
						<td class="sino_table_label" style="background:none repeat scroll 0 0 #F3F3F3;font-weight:bold;" >合同名称</td>
						</c:if>
						<c:if test="${mark=='NC'}">
						<td class="sino_table_label" style="background:none repeat scroll 0 0 #F3F3F3;font-weight:bold;">内采名称</td>
						 </c:if>
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
				</thead>
				<tbody>
				 <c:forEach var="product" items="${model.businessOrderProductModel}" varStatus="status">
				  <c:if test="${product.salesContractModel.id!=null}">
					<tr>
						<td class="sino_table_label"><a style="color:#0000dd;" href="${ctx }/sales/contract/detail?id=${product.salesContractModel.id}"  target="_blank" >${product.salesContractModel.contractName}</a></td>
						<td class="sino_table_label">${product.productType }</td>
						<td class="sino_table_label">${product.vendorCode }</td>
						<td class="sino_table_label">${product.productNo }</td>
						<td class="sino_table_label">${product.quantity }</td>
						<td class="sino_table_label">￥${product.unitPrice }</td>
						<td class="sino_table_label">￥${product.subTotal }</td>
				<%-- 		<td class="sino_table_label" id="serviceType${ status.index}">${product.serviceType }</td>
						<td class="sino_table_label">${product.serviceStartTime }</td>
						<td class="sino_table_label">${product.serviceEndTime }</td> --%>
					</tr>
					</c:if>
					<c:if test="${product.interPurchasProduct.id!=null}">
					<tr>
						<td class="sino_table_label">${product.interPurchas.purchasName}</td>
						<td class="sino_table_label">${product.productType }</td>
						<td class="sino_table_label">${product.vendorCode }</td>
						<td class="sino_table_label">${product.productNo }</td>
						<td class="sino_table_label">${product.quantity }</td>
						<td class="sino_table_label">￥${product.unitPrice }</td>
						<td class="sino_table_label">￥${product.subTotal }</td>
						<%-- <td class="sino_table_label" id="serviceType${ status.index}">${product.serviceType }</td>
						<td class="sino_table_label">${product.serviceStartTime }</td>
						<td class="sino_table_label">${product.serviceEndTime }</td> --%>
					</tr>
					</c:if>
				</c:forEach>
				</tbody>
			</table>
                    <div class=" row-inline" >
				         <hr size=1 class="dashed-inline">
		            </div> 
			<h3>审批</h3>
			<!--审批按钮  -->
			
			    <input type="hidden" id="taskId" name="taskId" value="${taskId}" >
		            <ul class="clearfix" style="position: relative;">
					   <li id="field_userName" class="li_form" style="width: 400px;">
						    <label for="isNew" class="editableLabel" style="color:#e99031;">审批：</label>
						    <input type="radio" id="inlineCheckbox1" name="isAgree" value="1" checked="checked"/>通过&nbsp;&nbsp;&nbsp;
						    <input type="radio" id="inlineCheckbox2" name="isAgree" value="0"/>不通过&nbsp;&nbsp;&nbsp;
					   </li>
					   <li id="field_userName" class="li_form" style="width: 600px;">
					         <label class="editableLabel" style="color:#e99031;">审批意见：</label>
					         <textarea name="remark" rows="3" cols="30" style="width:450px"></textarea>
					   </li>
					 </ul>
					   <div class=" row-inline" >
				         <hr size=1 class="dashed-inline">
		            </div> 
					<div style="clear:both;"></div>
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
			</form>
		</div>
			<!--按钮组-->
			<div id="bottom_button" class="handprocess_btngroup">
				<!-- <input type="button" id="_order_OK" value="确认" class="grey_btn33 cp" style="height:30px;"> -->
				<a id='_order_OK' role="button" class="btn btn-success"><i class="icon-ok"></i>确认</a>&nbsp;&nbsp;&nbsp;&nbsp;
				<a id='_eoss_business_back' role="button" class="btn"><i class="icon-remove-cricle"></i>取消</a>
			</div>
	
     <script language="javascript">
		seajs.use('js/page/business/order/orderReview/swjlsp', function(swjlsp) {
			swjlsp.init();
		}); 
     </script>
</body>
</html>