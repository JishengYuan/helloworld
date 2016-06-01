<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/global.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
	<title>发票申请详情</title>
	<%@ include file="/common/include-base-boostrap-styles.jsp" %>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formSelect.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/user-selection.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formSearchTip.css" type="text/css" ></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formstyle.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/dialog.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/tip.css" type="text/css" ></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/jquery-ui-1.8.17.custom.css" type="text/css" ></link>
	<link rel="stylesheet" href="${ctx}/skin/default/editgrid/editgrid.css" type="text/css" />
	<script src="${ctx}/js/plugins/seajs/sea.js" id="seajsnode" type="text/javascript"></script>
	<script src="${ctx}/js/plugins/seajs/config.js" type="text/javascript"></script>
	<link href="${ctx}/skin/default/sales/sale.css" rel="stylesheet"/>
	<style>
	.popover {
  		max-width: 730px;
  		height:50%;
	}
	</style>
	<style>
	.selectDiv .uicSelectInp {
	    background: none repeat scroll 0 0 #fff;
	    border: 1px solid #ccc;
	    cursor: pointer;
	    height: 12px;
	    position: relative;
	    text-align: left;
	    vertical-align: middle;
	}
	.selectDiv a.uicSelectMore {
	    background: url("${ctx}/js/plugins/uic/style/excellenceblue/uic/images/ultraselect/ms_ico2.png") no-repeat scroll 1px 0 rgba(0, 0, 0, 0);
	    display: block;
	    height: 23px;
	    position: absolute;
	    right: 0;
	    top: 0;
	    width: 25px;
	    margin-right:-3px;
	 }
    .selectDiv .uicSelectData {
    	 top: 23px;
    }
.form-horizontal .form-actions{
     padding-left:0px;
    }
    </style>
</head>
<body>
<!-- 
<div style="position: fixed; margin-top: 50px; right: 5px;;width:115px;border: 1px solid #eee; -moz-border-radius: 6px;-webkit-border-radius: 6px;border-radius:6px;padding:5px;">
			    		   <h4 style="color:red;text-align: center;">合同发票提示</h4>
			    		   <hr style="margin:0;">
			          <span class="stroke"> &nbsp;&nbsp;原ERP合同，没有申请完的发票，可以继续申请;<br>&nbsp;&nbsp;申请时由于发票金额不固定，建议一次申请一张;<br>&nbsp;&nbsp;如果计划做的严谨，也可以一次性将多张发票一次申请！</span>
				</div>
				 -->
	<div class="salecontent">
		<div class="top">
			<div class="caption">
				<i class="icon-reorder"></i>&nbsp;&nbsp;&nbsp;发票申请详情
				<c:if test="${flowStep != 'SHOW' }">
					<span class="tright" style="color:#f4606c">该合同可申请的发票金额：${surplusAmount}元</span>
				</c:if>
			</div>
			<div class="tools">
				<!--a href="javascript:;" class="reload"><i class="icon-repeat"></i>返回</a-->
			</div>
		</div>
	</div>
		<div class="portlet-body form">
			<!-- BEGIN FORM-->
			<form id='_sino_eoss_sales_invoice_approveForm' class='form-horizontal' >
				<!--隐藏字段start -->
				<!--  隐藏的合同ID-->
				<input type='hidden' name='id' id='id'  value="${model.id}"/>
				<!--  隐藏的合同类型ID-->
				<input type="hidden" name="contractTypeId" id="contractTypeId" value="${model.contractType}"/>
				<!--隐藏的发票类型，用于回显-->
			<input type='hidden' name='invoiceType' id='_eoss_sales_invoiceTypeId'  value="${model.invoiceType}"/>
				<!--  隐藏的合同名称-->
				<input type='hidden' name='contractName' id='contractName'  value="${model.contractName}"/>
				<!--隐藏的客户ID，用于回显-->
				<input type='hidden' name='customerId' id='_eoss_sales_customerId'  value="${model.customerId}"/>
				<!--  隐藏的tableData初始化数据-->
				<input type='hidden' name='tableData' id='tableData' />
				<!--  隐藏的流程中所需要的任务ID-->
				<input type="hidden" id="taskId" name="taskId" value="${taskId}" >
				<!--  隐藏的流程中所需要的工单ID-->
				<input type="hidden" id="procInstId" name="procInstId" value="${procInstId}" >
				<!--  隐藏的剩余可开发票金额数据-->
				<input type='hidden' name='surplusAmount' id='surplusAmount' value="${surplusAmount}"/>
				<!-- 要变更发票的Ids -->
				<input type='hidden' name='invoiceIds' id='invoiceIds'/>
				<!--隐藏字段end -->
				
				<!-- 小标题start -->
				<div class="row-inline-first" >
				</div>
				<!-- 小标题end -->
				<!-- 1行start -->
				<div class=" row-inline" >
					<div class=''>
						<label class='row-label' for='contractName' >合同名称：</label>
						<div class='row-span-oneColumn'>
							<span>${model.contractName}</span>
						</div>
					</div>
				</div>
				<!-- 1行end -->		
				<!-- 2行start -->
				<%-- <div class=" row-inline" >
					<div class=''>
						<label class='row-label' for='contractName' >合同简称：</label>
						<div class='row-span'>
							<span>${model.contractShortName}</span>
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
							<span id="contractAmount" style="font-size:18px;color:#333333"><%-- <fmt:formatNumber value="${model.contractAmount}" type="currency" currencySymbol="￥"/> --%>￥${model.contractAmount}</span>
						</div>
					</div>
				</div>
				<!-- 2行end -->
				<!-- 3行start -->
				<!-- <div class=" row-inline" >
					<div class=''>
						<label class='row-label' for='contractName' >行业名称：</label>
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
				</div> -->
				<!-- 3行end -->
				<!-- 4行start -->
				<div class=" row-inline" >
					<div class=''>
						<label class='row-label' for='contractName' >客户名称：</label>
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
				<div class=" row-inline" style='width:400px;'>
					<div class=''>
						<label class='row-label' for='customerContactsPhone' >手机号码：</label>
						<div class='row-span'>
							<span id="_customerContactsPhone"></span>
						</div>
					</div>
				</div><br />
				<c:if test="${isFlow=='isFlow'}">
				<div class=" row-inline" >
					<div class='' style="color:red;">
						<label class='row-label' for='customerContactsPhone' >已申请金额：</label>
						<div class='row-span'>
							<span><fmt:formatNumber value="${amount2 }" type="currency" currencySymbol="￥"/></span>
						</div>
					</div>
				</div>
				</c:if>
				<div class=" row-inline" >
					<div class='' style="color:red;">
						<label class='row-label' for='customerContactsPhone' >已开发票：</label>
						<div class='row-span'>
							<span><fmt:formatNumber value="${amount1 }" type="currency" currencySymbol="￥"/></span>
						</div>
					</div>
				</div>
				<!-- 4行end -->
				<br class='float-clear' />
				
				<!-- 分割行start -->
				<!-- 分割行end -->
				<!-- 小标题start -->
				<c:if test="${fn:length(salesInvoicePlanModels) > 0 }">
				<c:if test="${isFlow!='isFlow'}">
					<div class=" row-inline" >
						<hr size=1 class="dashed-inline">
					</div>
					<div class=" row-inline" >
						<h4 class="form-section-title">合同开票信息</h4>
					</div>
					<div class="products_add_btn">
						<a id='_sino_eoss_sales_invoicePlans_change' role="button" class="btn btn-success"><i class="icon-edit"></i>变更发票</a>
						<a id='_sino_eoss_sales_invoicePlans_add' role="button" class="btn btn-success"><i class="icon-plus"></i>继续添加</a>
					</div>

					
				<!-- 小标题end -->
				<!-- 5行start -->
				<div class=" row-inline" >
					<div class='table-tile-width'>
						<table id="_sino_eoss_sales_contract_invoiceApplys_table"class="table  table-bordered">
						  <thead>
						    <tr style="background-color: #f3f3f3;color: #888888;">
						      <th style="width:35px;">选择</th>
						      <th style="width:105px;">发票金额</th>
						      <th style="width:80px;">开发票日期</th>
						      <th style="width:225px;">发票类型</th>
						      <th style="width:70px;">状态</th>
						      <th style="width:300px;">备注</th>
						      <th style="width:120px;">操作</th>
						    </tr>
						  </thead>
						  <tbody>
							<c:forEach var="salesInvoicePlanModels" items="${salesInvoicePlanModels}" varStatus="status">
								<tr>
									<td><input name="_invoice_checkbox" type="checkbox" value="${salesInvoicePlanModels.id }" /></td>
									<td id="_td_count_${salesInvoicePlanModels.id }" tdamount="${salesInvoicePlanModels.invoiceAmount }"><fmt:formatNumber value="${salesInvoicePlanModels.invoiceAmount }" type="currency" currencySymbol="￥"/></td>
									<td><fmt:formatDate value="${salesInvoicePlanModels.invoiceTime }" pattern="yyyy-MM-dd"/></td>
									<td id="invoiceTypeId_${ status.index}" >${salesInvoicePlanModels.invoiceType }</td>
									<c:if test="${salesInvoicePlanModels.changeProInstanceId == null||salesInvoicePlanModels.changeProInstanceId == 0}">
										<td id="_td_${salesInvoicePlanModels.id }" tdState="${salesInvoicePlanModels.invoiceStatus }">
										<c:if test="${salesInvoicePlanModels.invoiceStatus == 'SH'}">
											审批中
										</c:if>
										<c:if test="${salesInvoicePlanModels.invoiceStatus == 'TGSH'}">
											审批通过
										</c:if>
										<c:if test="${salesInvoicePlanModels.invoiceStatus == 'FQ'}">
											待重新申请
										</c:if>
										<c:if test="${salesInvoicePlanModels.invoiceStatus == 'CXTJ'}">
												重新提交<span style="font-size:10px;">[变更]</span>
											</c:if>
										</td>
									</c:if>
									<c:if test="${salesInvoicePlanModels.changeProInstanceId != null&&salesInvoicePlanModels.changeProInstanceId != 0}">
										<td tdChange="1" id="_td_${salesInvoicePlanModels.id }" tdState="${salesInvoicePlanModels.invoiceStatus }">
											<c:if test="${salesInvoicePlanModels.invoiceStatus == 'SH'}">
												审批中<span style="font-size:10px;">[变更]</span>
											</c:if>
											<c:if test="${salesInvoicePlanModels.invoiceStatus == 'TGSH'}">
												审批通过<span style="font-size:10px;">[变更]</span>
											</c:if>
											<c:if test="${salesInvoicePlanModels.invoiceStatus == 'FQ'}">
												待重新申请<span style="font-size:10px;">[变更]</span>
											</c:if>
											<c:if test="${salesInvoicePlanModels.invoiceStatus == 'CXTJ'}">
												重新提交<span style="font-size:10px;">[变更]</span>
											</c:if>
										</td>
									</c:if>
									</td>
									<td>${salesInvoicePlanModels.remark }</td>
				
									<td >
																 <c:if test="${salesInvoicePlanModels.invoiceStatus=='TGSH'}">
									      <a href="#" name="contract_print" contractId="${salesInvoicePlanModels.id}" id="contractId_${ status.index}" class="contract_handler"><font color=Blue>打印</font></a>
									  </c:if>
									  <c:if test="${salesInvoicePlanModels.invoiceStatus !='TGSH'}">
									  	<%-- <c:forEach var="salesInvoicePlanModels1" items="${salesInvoicePlanModels1}" varStatus="status1">
												<c:if test="${status1.index ==  1}">
											      <a href="#" name="contract_print" contractId="${salesInvoicePlanModels.id}" id="contractId_${ status.index}" class="contract_handler"><font color=Blue>打印</font></a>
												</c:if>
									  	</c:forEach> --%>
									  	  <c:forEach var="isPrint" items="${isPrint}" varStatus="status1">
											<c:if test="${status1.index ==  status.index && isPrint.inPrint == '1'}">
										      <a href="#" name="contract_print" contractId="${salesInvoicePlanModels.id}" id="contractId_${ status.index}" class="contract_handler"><font color=Blue>打印</font></a>
											</c:if>
										</c:forEach>
									  </c:if>
							
									<a href="#" name="contract_handler" porId="${salesInvoicePlanModels.processInstanceId}" id="porId_${ status.index}" class="contract_handler"><font color=Blue>查看审核</font></a></td>
								</tr>
							</c:forEach>
						  </tbody>
						</table>
					</div>
				</div>
				</c:if>
				<div class=" row-inline" >
					<hr size=1 class="dashed-inline">
				</div>
				<!-- 分割行end -->
				<!-- 小标题start -->
	  		</c:if>
	  		<c:if test="${fn:length(changeInvoicePlanModels) > 0 }">
				<div class=" row-inline" >
					<h4 class="form-section-title">发票变更历史</h4>
				</div>
				<div class=" row-inline" >
						<div class='table-tile-width'>
							<table id="_sino_eoss_sales_contract_invoiceChangeApplys_table"class="table  table-bordered">
							  <thead>
							    <tr style="background-color: #f3f3f3;color: #888888;">
							      <th style="width:5%;">选择</th>
							      <th style="width:11%;">发票金额</th>
							      <th style="width:10%;">开发票日期</th>
							      <th style="width:20%;">发票类型</th>
							      <th style="width:10%;">备注</th>
							    </tr>
							  </thead>
							  <tbody>
								<c:forEach var="salesInvoicePlanModels" items="${changeInvoicePlanModels}" varStatus="status">
									<tr>
										<td>${status.index+1}</td>
										<td id="_td_count_${salesInvoicePlanModels.id }" tdamount="${salesInvoicePlanModels.invoiceAmount }"><fmt:formatNumber value="${salesInvoicePlanModels.invoiceAmount }" type="currency" currencySymbol="￥"/></td>
										<td><fmt:formatDate value="${salesInvoicePlanModels.invoiceTime }" pattern="yyyy-MM-dd"/></td>
										<td id="invoiceTypeId_${ status.index}" >${salesInvoicePlanModels.invoiceType }</td>
										<td>${salesInvoicePlanModels.remark }</td>
									</tr>
								</c:forEach>
							  </tbody>
							</table>
						</div>
					</div>
				</c:if>
				<br class='float-clear' />

				<c:if test="${isFlow!='isFlow'}">
			  		<div id="_invoice_add" style="display:none;">
			  				<div class=" row-inline" >
								<h4 class="form-section-title">发票申请</h4>
							</div>
							  <div class="row-inline" style="margin-right:18px;">
						   			<div id="editTable"></div>
						</div>
						<!-- END FORM-->  
						<br class='float-clear' />

					</div>
					<div class="form-actions">
						<div style='text-align:center;'>
							<a id='_sino_eoss_sales_invoice_add' style="display:none;" role="button" class="btn btn-success"><i class="icon-ok"></i>提交申请</a>
							&nbsp;&nbsp;&nbsp;&nbsp;<a id='_sino_eoss_sales_invoice_back' role="button" class="btn"><i class="icon-remove "></i>关闭</a>
						</div>
					</div>
				</c:if>
			
				<!-- 5行end -->	
			<br class='float-clear' />
			
			<c:if test="${isFlow=='isFlow'}">
				<!-- 分割行start -->
				<div class=" row-inline" >
					<div class='table-tile-width'>
						<table id="_sino_eoss_sales_contract_invoiceApplys_table"class="table  table-bordered">
						  <thead>
						    <tr style="background-color: #f3f3f3;color: #888888;">
						      <th style="width:5%;">序号</th>
						      <th style="width:12%;">发票金额</th>
						      <th style="width:10%;">开发票日期</th>
						      <th style="width:20%;">发票类型</th>
						      <th style="width:12%;">状态</th>
						      <th style="width:12%;">备注</th>
						      <th style="width:12%;">操作</th>
						    </tr>
						  </thead>
						  <tbody>
							<c:forEach var="salesInvoicePlanModels" items="${salesInvoicePlanModels}" varStatus="status">
								<tr>
									<td >${ status.index + 1}</td>
									<td>
										<fmt:formatNumber value="${salesInvoicePlanModels.invoiceAmount }" type="currency" currencySymbol="￥"/>
									</td>
									<td ><fmt:formatDate value="${salesInvoicePlanModels.invoiceTime }" pattern="yyyy-MM-dd"/></td>
									<td id="invoiceTypeId_${ status.index}" >${salesInvoicePlanModels.invoiceType }</td>
									<td >
										<c:if test="${salesInvoicePlanModels.invoiceStatus == 'SH'}">
											审批中
										</c:if>
										<c:if test="${salesInvoicePlanModels.invoiceStatus == 'TGSH'}">
											审批通过
										</c:if>
										<c:if test="${salesInvoicePlanModels.invoiceStatus == 'FQ'}">
											待重新申请
										</c:if>
										<c:if test="${salesInvoicePlanModels.invoiceStatus == 'CXTJ'}">
												重新提交<span style="font-size:10px;"></span>
											</c:if>
									</td>
									<td >${salesInvoicePlanModels.remark }</td>
									<td ><a href="#" name="contract_handler" porId="${salesInvoicePlanModels.processInstanceId}" id="porId_${ status.index}" class="contract_handler"><font color=Blue>审核日志详情</font></a></td>
								</tr>
							</c:forEach>
						  </tbody>
						</table>
				</div>
				</div>
				
					<div class=" row-inline" >
						<hr size=1 class="dashed-inline">
					</div>
				<!-- 小标题start -->
				<c:if test="${flowStep != 'SHOW' }">
				<div class=" row-inline" >
					<h4 class="form-section-title">审批</h4>
				</div>
				<!-- 小标题end -->
				<!-- 审核第1行start -->
				<div class=" row-inline" >
					<div class=''><input type="radio" id="inlineCheckbox1" name="isAgree" value="1" checked="checked" style="margin-left:18px;">
						<label class=" checkbox inline" style="padding-left:0px;">
							同意
						</label>
						 &nbsp;&nbsp;&nbsp;&nbsp;
						<input type="radio" id="inlineCheckbox2" name="isAgree" value="0" >
						<label class=" checkbox inline" style="padding-left:0px;">
							驳回
						</label>
					</div>
				</div>
				<!-- 审核第1行end -->	
				<br class='float-clear' />
				<!-- 审核第3行start -->
				<div class=" row-inline" style="margin-top:15px">
					<div class=''>
						<label class='row-label' for='contractName' >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;审批意见：</label>
						<div class='row-input'>
							<textarea rows="3" name="remark"></textarea>
							<input type="hidden" id="flowFlag" name="flowFlag" value="${flowStep}" >
						</div>
					</div>
				</div>
				</c:if>
				<!-- 审核第3行end -->
				<br class='float-clear' />
				<div class="form-actions">
				<c:if test="${flowStep != 'SHOW' }">
					<div style="text-align:center">
						<a id='_sino_eoss_sales_invoice_submit' role="button" class="btn btn-success"><i class="icon-ok"></i>确认</a>
						<a id='_sino_eoss_sales_invoice_back' role="button" class="btn"><i class="icon-remove "></i>取消</a>
					</div>
				</c:if>
				<c:if test="${flowStep == 'SHOW' }">
					<div style="text-align:center">
						<a id='_sino_eoss_sales_invoice_back' role="button" class="btn"><i class="icon-remove "></i>关闭</a>
					</div>
				</c:if>
				</div>
				</c:if>
			<!-- END FORM-->  
			</form>
		</div>
	<script language="javascript">
		var form = ${form};  //从controller层接收收款计划data_grid数据模型
		seajs.use('js/page/sales/invoice/applyDetail',function(applyDetail){
			applyDetail.init();
		});    
	</script>
</body>
</html>
