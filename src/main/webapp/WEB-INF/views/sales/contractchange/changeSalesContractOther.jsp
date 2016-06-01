<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/global.jsp"%>
<%@ page import="com.sinobridge.systemmanage.util.Global"%> 
<%@ page import="com.sinobridge.systemmanage.vo.SystemUser"%> 
<html>
<head>
	<title>合同其他信息变更</title>
	<%@ include file="/common/include-base-boostrap-styles.jsp" %>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formSelect.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formstyle.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/dialog.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/tip.css" type="text/css" ></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/user-selection.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formSearchTip.css" type="text/css" ></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/jquery-ui-1.8.17.custom.css" type="text/css" ></link>
	<link rel="stylesheet" href="${ctx}/skin/default/editgrid/editgrid.css" type="text/css" />
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
	<script src="${ctx}/js/plugins/seajs/sea.js" id="seajsnode" type="text/javascript"></script>
	<script src="${ctx}/js/plugins/seajs/config.js" type="text/javascript"></script>
	<link href="${ctx}/skin/default/sales/sale.css" rel="stylesheet"/>
	<style type="text/css">
	.row-column{
		float:left;
		margin-left:16px;
		}
		.row-column:first-child{
		margin-left:0px;
		}
		.row-label{
		color: #eeb422;
		}
		</style>
</head>
<body>
	<div class="salecontent">
		<div class="top">
			<div class="caption">
				<i class="icon-reorder"></i>&nbsp;&nbsp;&nbsp;合同信息变更
				<span class="tright" >合同编号：${model.contractCode}</span>
			
			</div>
			<div class="tools">
				<!--a href="javascript:;" class="reload"><i class="icon-repeat"></i>返回</a-->
			</div>
		</div>
	</div>
		<div class="portlet-body form">
		<!-- BEGIN FORM-->
		<form id='_sino_eoss_sales_contract_change_addform' name='_sino_eoss_sales_contract_addform' class='form-horizontal' >
			<!--隐藏字段start -->
			<input type='hidden' id='_is_submit_product'  value="1"/>
			<input type='hidden' name='id' id='id'  value="${model.id}"/>
			<!--  隐藏的合同变更申请的ID-->
			<input type='hidden' name='salesContractChangeApplyId' id='salesContractChangeApplyId'  value="${changeApplyModel.id}"/>
			<!--  隐藏的合同变更申请的变更内容类型-->
			<input type='hidden' name='changeType' id='changeType'  value="${changeApplyModel.changeType}"/>
			<!--  隐藏的tableGrid-->
			<!--隐藏的合同编码-->
			<input type='hidden' id='contractCode' name='contractCode'  value="${model.contractCode}"/>
			<!--隐藏的合同类型，用于回显-->
			<input type='hidden' name='contractType' id='_eoss_sales_contractTypeId'  value="${model.contractType}"/>
			<!--隐藏的结算币种，用于回显-->
			<input type='hidden' name='accountCurrency' id='_eoss_sales_accountCurrencyId'  value="${model.accountCurrency}"/>
			<!--隐藏的发票类型，用于回显-->
			<input type='hidden' name='invoiceType' id='_eoss_sales_invoiceTypeId'  value="${model.invoiceType}"/>
			<!--隐藏的收款方式，用于回显-->
			<input type='hidden' name='receiveWay' id='_eoss_sales_receiveWayId'  value="${model.receiveWay}"/>
			<!--隐藏的客户ID，用于回显-->
			<input type='hidden' name='customerId' id='_eoss_sales_customerId'  value="${model.customerId}"/>
			<!--隐藏的合同状态-->
			<input type='hidden' name='contractState' id='contractState'  value="SH"/>
			<!--隐藏的创建人ID-->
			<input type='hidden' name='creator' value="${model.creator}"/>
			<!--隐藏的添加时间-->
			<input type='hidden' name='createTime' value="${model.createTime}"/>
			<!--隐藏的工单ID-->
			<input type='hidden' name='processInstanceId' value="${model.processInstanceId}"/>
			<!--隐藏的附件ID-->
			<input type='hidden' name='attachIds' id='attachIds'/>
			<!--暂定行业，行业客户，客户，联系人,手机，合同编码不能修改，只读,所以将下列加载下拉列表方法注释-->
			<!--隐藏的行业ID，用于回显-->
			<!--<input type='hidden' name='IndustryId' id='_eoss_sales_IndustryId'/>-->
			<!--隐藏的行业客户ID，用于回显-->
			<!--<input type='hidden' name='idtCustomerId' id='_eoss_sales_idtCustomerId'/>-->
			<!--预存收款计划JSON数据-->
			<input type='hidden' name='tableData' id='tableData' />
			<!--保存为草稿还是提交的标志位-->
			<input type='hidden' name='isSubmit' id='isSubmit' />
			<!--进入流程后重新审批的标志位-->
			<input type="hidden" id="flowFlag" name="flowFlag" value="${flowStep}" >
			<!--  隐藏的流程中所需要的任务ID-->
			<input type="hidden" id="taskId" name="taskId" value="${taskId}" >
			<!--  隐藏的流程中所需要的工单ID-->
			<input type="hidden" id="procInstId" name="procInstId" value="${procInstId}" >
			
			<input type='hidden'  id='contractAmount' name='contractAmount'  value="${model.contractAmount}" readonly="readonly"/>
			<input type='hidden' name='customerContact' id='customerContact' value="${model.customerContact }" />
			<!--隐藏字段end -->
			<!-- 小标题start -->
			<div class="row-inline-first" >
				<h4 class="form-section-title">合同基本信息</h4>
			</div>
			<!-- 小标题end -->
			<!-- 1行start -->
			<div class=" row-inline" >
				<div class=''>
					<label class='row-label' for='contractName' >合同名称：</label>
					<div class='row-input'>
						<input class="row-input-oneColumn" type='text' id='contractName' name='contractName' placeholder='请输入合同名称' value="${model.contractName}" required data-content="请输入合同名称"/>
						<span class="help-block"></span>
					</div>
				</div>
			</div>
			<div class=" row-inline" >
				<div class=''>
					<label class='row-label' for='contractName' >合同类型：</label>
					<div class='row-input' id="_eoss_sales_contractType" style="width:220px;margin-top:5px;font-size:14px;"></div>
				</div>
			</div>
			<div class=" row-inline" >
				<div class='row-column'>
					<label class='row-label' for='contractName' >合同金额：</label>
					<div class='row-input'>
						<div class="input-append">
							<input type='text'  class='row-input-addTip' value="${model.contractAmount}" readonly="readonly"/>
							<span class="add-on">元</span>
						</div>
						<span class="help-block"></span>
					</div>
				</div>
				<div class='row-column'>
					<label class='row-label' for='receiveWay' >收款方式：</label>
					<div class='row-input' id="_eoss_sales_receiveWay"></div>
				</div>
			</div>
			<!-- 2行end -->
			<!-- 3行start -->
			<div class=" row-inline" >
				<div class='row-column'>
					<label class='row-label' for='invoiceType' >发票类型：</label>
					<div class='row-input' id="_eoss_sales_invoiceType"></div>
				</div>
				<div class='row-column'>
					<label class='row-label' for='accountCurrency' >结算币种：</label>
					<div class='row-input' id="_eoss_sales_accountCurrency"></div>
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
			<!-- <div class=" row-inline" >
				<div class=''>
					<label class='row-label' for='industryName' >行业选择：</label>
					<div class='row-input' id='_industryName'></div>
					<div class='row-input'>
						<input type="text"  id='_industryName'/>
						<span class="help-block"></span>
					</div>
				</div>
			</div>
			<div class=" row-inline" >
				<div class=''>
					<label class='row-label' for='customerIdtCustomer' >行业客户：</label>
					<div class='row-div-twoColumn' id='_customerIdtCustomer'></div>
					<div class='row-input'>
						<input class='row-input-twoColumn' type="text" id='_customerIdtCustomerName'>
						<span class="help-block"></span>
					</div>
				</div>
			</div> -->
			<!-- 5行end -->
			<!-- 6行start -->
			<div class=" row-inline" >
				<div class=''>
					<label class='row-label' for='customerInfo' >客户选择：</label>
					<!--<div class='row-input' id='_customerInfo'></div>-->
					<div class='row-div-oneHalfColumn' >
						<!-- <input type="text"  id='_customerInfoName' style='width:356px'> -->
						<span class="help-block" id='_customerInfoName'></span>
					</div>
				</div>
			</div>
			<div class=" row-inline" >
				<div class=''>
					<label class='row-label' for='customerContacts' >联&nbsp;&nbsp;系&nbsp;&nbsp;人：</label>
					<div class='row-input-halfColumn'>
						<!-- <input type="text"  id="_customerContacts"> -->
						<span class="help-block" id="_customerContacts"></span>
					</div>
				</div>
			</div>
			<div class=" row-inline" >
				<div class=''>
					<label class='row-label' for='customerContactsPhone' >手机号码：</label>
					<div class='row-input-halfColumn'>
						<!-- <input type="text"  id="_customerContactsPhone"> -->
						<span class="help-block" id="_customerContactsPhone"></span>
					</div>
				</div>
			</div>
			<!-- 6行end -->
			<!-- 分割行start -->
			<div class=" row-inline" >
				<hr size=1 class="dashed-inline">
			</div>
			<!-- 分割行end -->
			<!-- 小标题start -->
			<div class=" row-inline" >
				<h4 class="form-section-title">公司信息(乙方)</h4>
			</div>
			<!-- 小标题end -->
			<!-- 7行start -->
			<div class=" row-inline" >
				<div class=''>
					<label class='row-label' for='contractName' >公司名称：<span>北京神州新桥科技有限公司</span></label>
					<!-- <div class='row-input'>
						<input class='row-input-twoColumn' readonly="readonly"  type="text" value="北京神州新桥科技有限公司 ">
						<span class="help-block"></span>
					</div> -->
				</div>
			</div>
			<div class=" row-inline" >
				<div class=''>
					<label class='row-label' for='contractName' >客户经理：<span>${model.creatorName}</span></label>
					<div class='row-input'>
					<!--TODO 暂时没有向后台传入该字段,显示的时候不要用readonly的input框(初步定将这几个字段定义为常量)-->
						<%-- <input type="text" name='creatorName' readonly="readonly"  value="${model.creatorName}">
						<span class="help-block"></span> --%>
					</div>
				</div>
			</div>
			<!-- 7行end -->	
			<!-- 8行start -->
			<div class=" row-inline" >
				<div class=''>
					<label class='row-label' for='contractName' >开户银行：<span>中国工商银行紫竹院支行</span></label>
					<!-- <div class='row-input'>
						<input class='row-input-twoColumn' type="text" readonly="readonly"   value="中国工商银行紫竹院支行">
						<span class="help-block"></span>
					</div> -->
				</div>
			</div>
			<div class=" row-inline" >
				<div class=''>
					<label class='row-label' for='contractName' >银行账户：<span>0200007609004774060</span></label>
					<div class='row-input'>
					<!--TODO 暂时没有向后台传入该字段,显示的时候不要用readonly的input框(初步定将这几个字段定义为常量)-->
						<!-- <input type="text"  readonly="readonly"  value="0200007609004774060"> 
						<span class="help-block"></span> -->
					</div>
				</div>
			</div>
			
			<div class=" row-inline" >
				<h4 class="form-section-title" id="service_h4">合同执行信息</h4>
			</div>
			<div class=" row-inline" id="serviceStartDate_div">
				<div class=''>
					<label class='row-label' for='accountCurrency' ><span style="color:red;">*</span>服务开始时间：</label>
					<div class='row-input'>
						<div class="input-append date">
				  		    <input data-format="yyyy-MM-dd" style="width:150px;" type="text" id="serviceStartDate_input" name="serviceStartDate" required data-content="请输入服务开始时间" value="${model.serviceStartDate }"></input>
						    <span class="add-on">
						      <i data-time-icon="icon-time" data-date-icon="icon-calendar">
						      </i>
							</span>
						  </div>
					</div>
				</div>
			</div>
			<div class=" row-inline" id="serviceEndDate_div">
				<div class=''>
					<label class='row-label' for='invoiceType' ><span style="color:red;">*</span>服务结束时间：</label>
					<div class='row-input'>
						<div class="input-append date">
				  		    <input data-format="yyyy-MM-dd" style="width:150px;" type="text" id="serviceEndDate_input" name="serviceEndDate" required data-content="请输入服务结束时间" value="${model.serviceEndDate }"></input>
						    <span class="add-on">
						      <i data-time-icon="icon-time" data-date-icon="icon-calendar">
						      </i>
							</span>
						  </div>
					</div>
				</div>
			</div>
			
			<div class=" row-inline"  style="margin-top:40px;"></div>
			
			<div class=" row-inline" id="estimateProfit_div">
				<%-- <div class='row-column' id="">
					<label class='row-label' for='accountCurrency' ><span style="color:red;">*</span>合同生效时间：</label>
					<div class='row-input'>
						<div class="input-append date">
				  		    <input data-format="yyyy-MM-dd" style="width:150px;" type="text" id="salesStartDate_input" name="salesStartDate" value="${model.salesStartDate }" required data-content="请输入合同生效时间"></input>
						    <span class="add-on">
						      <i data-time-icon="icon-time" data-date-icon="icon-calendar">
						      </i>
							</span>
						  </div>
					</div>
				</div> --%>
				<div class='row-column' id="">
					<label class='row-label' for='invoiceType' ><span style="color:red;">*</span>合同终止时间：</label>
					<div class='row-input'>
						<div class="input-append date">
				  		    <input data-format="yyyy-MM-dd" style="width:150px;" type="text" id="sales_EndDate_input" name="salesEndDate" value="${model.salesEndDate }" required data-content="请输入合同终止时间"></input>
						    <span class="add-on">
						      <i data-time-icon="icon-time" data-date-icon="icon-calendar">
						      </i>
							</span>
						  </div>
					</div>
				</div>
				<div class='row-column'>
					<label class='row-label' for='receiveWay' ><span style="color:red;">*</span>预估毛利率：</label>
					<div class='row-input'>
						<input type="text" name="estimateProfit" style="width:50px;" required data-content="请输入预估毛利率" value="${model.estimateProfit }">
					</div>
				</div>
			</div>
			
			<div class=" row-inline"  style="margin-top:10px;">
			<div class='row-column'>
					<label class='row-label' for='receiveWay' >预估毛利说明：</label>
					<div class='row-input'>
						<input type="text" name="estimateProfitDesc" value="${model.estimateProfitDesc }" style="width:800px;" placeholder="请输入估算毛利的依据和说明">
					</div>
				</div>
			</div>
			
			<!-- 8行end -->
			<!-- 分割行start -->
			<div class="row-inline product_handler" >
				<hr size=1 class="dashed-inline">
			</div>
			<!-- 分割行end -->
			<!-- 小标题start -->
			<div class="row-inline product_handler" >
				<h5 class="form-section-title">合同清单
				</h5>
				<div class="row-inline">
					<label class='row-label' for='receiveWay' ><span style="color:red;">*</span>期望到货日期：</label>
					<div class='row-input'>
							<div class="input-append date">
							<input data-format="yyyy-MM-dd" id="_hopeArriveTime" style="width:150px;" type="text" name="hopeArriveTime" value="${model.hopeArriveTime }" required data-content="请输入期望到货时间"></input>
						    <span class="add-on">
						      <i data-time-icon="icon-time" data-date-icon="icon-calendar">
						      </i>
							</span>
						  </div>
					</div>
					<label class='row-label' style="margin-left: 10px;"><span style="color:red;"></span>期望到货地点：</label>
					<div class='row-input'>
						<input type="text" name="hopeArrivePlace" value="${model.hopeArrivePlace }" style="width:475px;" placeholder='请输入期望到货地点' />
					</div>
				</div>
				
			</div>
			<!-- 小标题end -->
			<!-- 9行start -->
			<div class="row-inline product_handler" >
				<div class='table-tile-width'>
				<c:if test="${model.contractType != '9000'}">
					<table id="_sino_eoss_sales_contract_products_table"class="table  table-bordered">
					  <thead>
					    <tr id="_contract_show" style="background-color: #f3f3f3;color: #888888;">
					      <th style="width:6%;">序号</th>
					      <th style="width:15%;" id="type_th">产品类型</th>
					      <th style="width:10%;" id="partner_th">产品厂商</th>
					      <th style="width:15%;" id="product_th">产品型号</th>
					       <th style="width:12%;">服务开始时间</th>
					      <th style="width:12%;">服务结束时间</th>
					      <th style="width:6%;">数量</th>
					      <th style="width:12%;">单价</th>
					      <th style="width:18%;">合计</th>
					    </tr>
					    <tr id="_contract_hidden" style="background-color: #f3f3f3;color: #888888;display:none;">
					      <th style="width:6%;">序号</th>
					      <th style="width:16%;">关联备货合同名称</th>
					      <th style="width:8%;" id="type_th">产品类型</th>
					      <th style="width:8%;" id="partner_th">产品厂商</th>
					      <th style="width:8%;" id="product_th">产品型号</th>
					      <th style="width:13%;">服务开始时间</th>
					      <th style="width:13%;">服务结束时间</th>
					      <th style="width:6%;">数量</th>
					      <th style="width:8%;">单价</th>
					      <th style="width:10%;">合计</th>
					    </tr>
					  </thead>
								  <fmt:setLocale value="zh_cn"/>
					  <tbody>
						<c:forEach var="salesContractProductModel" items="${model.salesContractProductModel}" varStatus="status">
							<c:if test="${salesContractProductModel.relateDeliveryProductId != null&&salesContractProductModel.relateDeliveryProductId != '' }">
								<tr>
									<td >${ status.index + 1}</td>
									<td name="relateDeliveryProduct_contract" tid="${salesContractProductModel.relateDeliveryProductId }"></td>
									<td >${salesContractProductModel.productTypeName }</td>
									<td >${salesContractProductModel.productPartnerName }</td>
									<td >${salesContractProductModel.productName }</td>
									<td >${salesContractProductModel.serviceStartDate }</td>
									<td >${salesContractProductModel.serviceEndDate }</td>
									<td >${salesContractProductModel.quantity }</td>
									<td > <fmt:formatNumber value="${salesContractProductModel.unitPrice }" type="currency" currencySymbol="￥"/> </td>
									<td tdPrice="${salesContractProductModel.totalPrice }"> <fmt:formatNumber value="${salesContractProductModel.totalPrice }" type="currency" currencySymbol="￥"/> </td>
								</tr>
							</c:if>
							<c:if test="${salesContractProductModel.relateDeliveryProductId == null||salesContractProductModel.relateDeliveryProductId == '' }">
								<tr>
									<td name="relateDeliveryProduct_contract_">${ status.index + 1}</td>
									<td >${salesContractProductModel.productTypeName }</td>
									<td >${salesContractProductModel.productPartnerName }</td>
									<td >${salesContractProductModel.productName }</td>
									<td >${salesContractProductModel.serviceStartDate }</td>
									<td >${salesContractProductModel.serviceEndDate }</td>
									<td >${salesContractProductModel.quantity }</td>
									<td ><fmt:formatNumber value="${salesContractProductModel.unitPrice }" type="currency" currencySymbol="￥"/> </td></td>
									<td tdPrice="${salesContractProductModel.totalPrice }"> <fmt:formatNumber value="${salesContractProductModel.totalPrice }" type="currency" currencySymbol="￥"/> </td>
								</tr>
							</c:if>
						</c:forEach>
					  </tbody>
					</table>
					</c:if>
					<c:if test="${model.contractType == '9000'}">
					<table id="_sino_eoss_sales_contract_readyproducts_table" style="" class="table  table-bordered table-striped">
					  <thead>
					    <tr>
					      <th style="width:10%;">序号</th>
					      <th style="width:15%;">产品类型</th>
					      <th style="width:10%;">产品厂商</th>
					      <th style="width:15%;">产品型号</th>
					      <th style="width:6%;">数量</th>
					      <th style="width:12%;">关联数量</th>
					      <th style="width:18%;">剩余数量</th>
					      <th style="width:12%;">单价</th>
					      <th style="width:18%;">合计</th>
					    </tr>
					  </thead>
					  <tbody>
					  <c:forEach var="salesContractProductModel" items="${model.salesContractProductModel}" varStatus="status">
							<tr>
								<td name="relateDeliveryProduct_contract_">${ status.index + 1}</td>
								<td >${salesContractProductModel.productTypeName }</td>
								<td >${salesContractProductModel.productPartnerName }</td>
								<td >${salesContractProductModel.productName }</td>
								<td >${salesContractProductModel.quantity }</td>
								<td >${salesContractProductModel.surplusNum }</td>
								<td style="font-weight:bold;">${salesContractProductModel.quantity - salesContractProductModel.surplusNum}</td>
								<td ><fmt:formatNumber value="${salesContractProductModel.unitPrice }" type="currency" currencySymbol="￥"/> </td></td>
								<td tdPrice="${salesContractProductModel.totalPrice }"> <fmt:formatNumber value="${salesContractProductModel.totalPrice }" type="currency" currencySymbol="￥"/> </td>
							</tr>
						</c:forEach>
					  </tbody>
					</table>
				</c:if>
				</div>
			</div>
			<div class="product_handler" style="float:right;margin-right:40px;">合计:<span id="_product_total">0</span>元</div>
			<!-- 9行end -->	
			<!-- 分割行start -->
			<div class=" row-inline collection_plan" >
				<hr size=1 class="dashed-inline">
			</div>
			<!-- 分割行end -->
			<!-- 小标题start -->
			<div class="row-inline" >
				<h4 class="form-section-title" collection_plan>收款计划</h4>
			</div>
			<!--导入 产品div -------end-->
			<div id="_sino_eoss_sales_products_import_div"></div>
			<!-- 小标题end -->
			<!-- 10行start -->
			<div class="row-inline collection_plan" >
				<div class='table-tile-width'>
					<div id="editTable"></div>
				</div>
			</div>	
			<!-- 10行end -->
			<!-- 分割行start -->
			<div class="row-inline" >
				<hr size=1 class="dashed-inline">
			</div>
			<!-- 分割行end -->
			<!-- 小标题start -->
			<div class=" row-inline" >
				<h4 class="form-section-title">合同附件</h4>
			</div>
			<!-- 小标题end -->
			<!-- 11行start -->
			<div class=" row-inline" >
				<div id="uplaodfile"></div>
			</div>	
			<!-- 11行end -->								
			<br class='float-clear' />
			</form>
			<!-- END FORM-->
			
			<!--设备添加弹出对话框 -------start-->
			 <div id="dailogs1" class="modal hide fade" role="dialog" tabIndex="-1" style="width:800px;height:460px;margin-left:-400px">
		   		<div class="modal-header">
				   <h3 id="dtitle"></h3>
				   <div id="_product_alertMsg"></div>
		  		</div>
				<div class="modal-body" style="height:70%">
					<div id="dialogbody" ></div>
				</div>
				<div class="modal-footer">
					<button id="_do_cancel"  class="btn" data-dismiss="modal" aria-hidden="true" >取消</button >
					<a id="dsave"  class="btn btn-primary" data-dismiss="modal" aria-hidden="true" >保存</a>
				</div>
			</div>
			<!--设备添加弹出对话框 -------end-->  			
			<div class="form-actions">
				<div style='text-align:center;'>
					<a id='_sino_eoss_sales_contract_change_submit' role="button" class="btn btn-success"><i class="icon-ok"></i>提交</a>
					<a id='_sino_eoss_sales_contract_change_cancel' role="button" class="btn"><i class="icon-reply"></i>取消</a>
				</div>
			</div>
		</div>
	<script language="javascript">
		var form = ${form};  //从controller层接受数据模型
		seajs.use('js/page/sales/contractchange/changeSalesContractOther',function(changeSalesContractOther){
			changeSalesContractOther.init();
		});    
	</script>
</body>
</html>
