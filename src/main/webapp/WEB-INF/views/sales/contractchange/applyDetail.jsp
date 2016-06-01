<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/global.jsp"%>
<%@ page import="com.sinobridge.systemmanage.util.Global"%> 
<%@ page import="com.sinobridge.systemmanage.vo.SystemUser"%> 
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
	<title>合同变更申请</title>
	<%@ include file="/common/include-base-boostrap-styles.jsp" %>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formSelect.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/user-selection.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formSearchTip.css" type="text/css" ></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formstyle.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/dialog.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/tip.css" type="text/css" ></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/jquery-ui-1.8.17.custom.css" type="text/css" ></link>
	<script src="${ctx}/js/plugins/seajs/sea.js" id="seajsnode" type="text/javascript"></script>
	<script src="${ctx}/js/plugins/seajs/config.js" type="text/javascript"></script>
	<link href="${ctx}/skin/default/sales/sale.css" rel="stylesheet"/>
	<style>
	
.lcolor {
    color: #eeb422;
}
	</style>
</head>
<body>
	<div class="salecontent">
		<div class="top">
			<div class="caption">
				<i class="icon-reorder"></i>&nbsp;&nbsp;&nbsp;合同变更申请
				<span class="tright" >合同编号：${model.contractCode}</span>
			
			</div>
			<div class="tools">
				<!--a href="javascript:;" class="reload"><i class="icon-repeat"></i>返回</a-->
			</div>
		</div>
	</div>
		<div class="portlet-body form">
			<!-- BEGIN FORM-->
			<form id='_sino_eoss_sales_contract_change_checkform' class='form-horizontal' >
				<!--隐藏字段start -->
				<!--  隐藏的合同ID-->
				<input type='hidden' name='saleContractId' id='salesContractId'  value="${model.id}"/>
				<!--  隐藏的合同类型ID-->
				<input type="hidden" name="contractTypeId" id="contractTypeId" value="${model.contractType}"/>
				<!--  隐藏的合同名称-->
				<input type='hidden' name='salesContractName' id='salesContractName'  value="${model.contractName}"/>
				<!--隐藏的客户ID，用于回显-->
				<input type='hidden' name='customerId' id='_eoss_sales_customerId'  value="${model.customerId}"/>
				<!--  隐藏的流程中所需要的任务ID-->
				<input type="hidden" id="taskId" name="taskId" value="${taskId}" >
				<!--  隐藏的流程中所需要的工单ID-->
				<input type="hidden" id="procInstId" name="procInstId" value="${procInstId}" >
				<input type="hidden" id="flowStep" name="flowStep" value="${flowStep}" >
				<input type="hidden" id="changeType" name="changeType" value="${contractChangeApplyModel.changeType}" >
				<input type="hidden" id="_remark" name="remark" value="${contractChangeApplyModel.remark}" >
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
							<span id="contractAmount">${model.contractAmount}元</span>
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
				<div class=" row-inline" >
					<div class=''>
						<label class='row-label' for='customerContactsPhone' >手机号码：</label>
						<div class='row-span'>
							<span id="_customerContactsPhone"></span>
						</div>
					</div>
				</div>
				<!-- 4行end -->
				<br class='float-clear' />
				<!-- 分割行start -->
				<div class=" row-inline" >
					<hr size=1 class="dashed-inline">
				</div>
				<!-- 分割行end -->
				<!-- 小标题start -->
				<div class=" row-inline" >
					<h4 class="form-section-title" style="color:red;">变更信息</h4>
				</div>
				<!-- 小标题end -->
				<!-- 5行start -->
				<div class=" row-inline" >
					<div class=''>
						<label class='row-label lcolor' for='changeType' >变更内容类型：</label>
						<div class='row-span'>
							<span id="changeType">
								<c:if test="${contractChangeApplyModel.changeType==1}">合同金额</c:if>
								<c:if test="${contractChangeApplyModel.changeType==2}">备品备件</c:if>
								<c:if test="${contractChangeApplyModel.changeType==5}">合同清单（设备变更）</c:if>
								<c:if test="${contractChangeApplyModel.changeType==7}">合同清单（只变更设备型号）</c:if>
								<c:if test="${contractChangeApplyModel.changeType==9}">添加产品 </c:if>
								<c:if test="${contractChangeApplyModel.changeType==8}">收款计划</c:if>
								<c:if test="${contractChangeApplyModel.changeType==4}">其他</c:if>
								<c:if test="${contractChangeApplyModel.changeType==3}">废弃</c:if>
							</span>
						</div>
					</div>
				</div>
				<!-- 5行end -->
				<br class='float-clear' />
				<!-- 6行start -->
				<div class=" row-inline" >
					<div class=''>
						<label class='row-label lcolor'  for='remark' >变更申请原因：</label>
						<div class='row-span'>
							<span id="remark">
								${contractChangeApplyModel.remark}
							</span>
						</div>
					</div>
				</div>
				<!-- 6行end -->
				<br class='float-clear' />
				<!-- 分割行start -->
				<div class=" row-inline" >
					<hr size=1 class="dashed-inline">
				</div>
				<!-- 小标题start -->
				<div class=" row-inline" >
					<h4 class="form-section-title">合同当前状态</h4>
				</div>
				<!-- 小标题end -->
				<!-- 7行start -->
				<div class=" row-inline" >
					<div class=''>
						<label class='row-label' for='cachetStatus' >合同盖章申请状态：</label>
						<div class='row-span'>
							<span id="cachetStatus">${salesContractStatusModel.cachetStatus}</span>
						</div>
					</div>
				</div>
				<%-- <div class=" row-inline" >
					<div class=''>
						<label class='row-label' for='invoiceStatus' >合同发票申请状态：</label>
						<div class='row-span'>
							<span id="invoiceStatus">${salesContractStatusModel.invoiceStatus}</span>
						</div>
					</div>
				</div> --%>
				<!-- 分割行start -->
				<div class=" row-inline" >
					<hr size=1 class="dashed-inline">
				</div>
				<div class=" row-inline" >
					<h4 class="form-section-title">合同订单状态</h4>
				</div>	
		
				<!-- 小标题end -->
				<!-- 5行start -->	
				<div class=" row-inline" >
					<div class='table-tile-width'>
						<table id="_contract_order"class="table  table-bordered">
						  <thead>
							<tr style="background-color: #f3f3f3;color: #888888;">
							  <th style="width:20%;">订单名称</th>
							  <th style="width:15%;">订单编号</th>
							  <th style="width:10%;">采购员</th>
							  <th style="width:10%;">订单状态</th>
							  <th style="width:10%;">公司库房</th>
							  <th style="width:10%;">客户到货</th>
							</tr>
						  </thead>
						  <tbody>
							<c:forEach var="orderModel" items="${orderModel}" varStatus="status">
								<tr>
									<td class="sino_table_label">${orderModel.orderName }</td>
									<td class="sino_table_label">${orderModel.orderCode }</td>
									<td class="sino_table_label">${orderModel.creator }</td>
									<td class="sino_table_label" tdId="${orderModel.orderStatus }"></td>
									<c:if test="${orderModel.wareHouseStatus == 'N'}">
										<td class="sino_table_label">没到货</td>
									</c:if>
									<c:if test="${orderModel.wareHouseStatus == 'A'}">
										<td class="sino_table_label">全部到货</td>
									</c:if>
									<c:if test="${orderModel.wareHouseStatus == 'S'}">
										<td class="sino_table_label">部分到货</td>
									</c:if>
									<c:if test="${orderModel.wareHouseStatus == ''||orderModel.wareHouseStatus == null}">
										<td class="sino_table_label">--</td>
									</c:if>
									<td class="sino_table_label">${orderModel.arrivalStatus }</td>
								</tr>
							</c:forEach>
						  </tbody>
						</table>
					</div>
				</div>
				
				<!-- 7行end -->
				<br class='float-clear' />
				<!-- 分割行start -->
				<div class=" row-inline" >
					<hr size=1 class="dashed-inline">
				</div>
				<!-- 分割行end -->
				<!-- 小标题start -->
				<div class=" row-inline" >
					<h4 class="form-section-title">审核日志</h4>
					<span style="float: right; width: 185px; margin-top: -30px;">
						<c:if test="${approName != ''&&approName != null}">
							<label class="lcolor">当前变更审批人</label>:${approName}
						</c:if>
					</span>
				</div>
				<!-- 小标题end -->
				<!-- 8行start -->	
				<div class=" row-inline" >
					<div class='table-tile-width'>
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
									<td class="sino_table_label"><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${logs.time }" /></td>
									<td class="sino_table_label">${logs.apprDesc }</td>
								</tr>
							</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
				<!-- 8行end -->	
				<br class='float-clear' />
				<c:if test="${flowStep=='BGSP'||flowStep=='BGSP2'}">
				<!-- 分割行start -->
				<div class=" row-inline" >
					<hr size=1 class="dashed-inline">
				</div>
				<!-- 小标题start -->
				<div class=" row-inline" >
					<h4 class="form-section-title">审批</h4>
				</div>
				<!-- 小标题end -->
				<!-- 审核第1行start -->
				<div class=" row-inline" >
					<div class=''>
						<label class="checkbox inline">
							同意:&nbsp;<input type="radio" id="inlineCheckbox1" name="isAgree" value="1" checked="checked" style="margin-top:0px;">
						</label>
						<label class="checkbox inline">
							驳回:&nbsp;<input type="radio" id="inlineCheckbox2" name="isAgree" value="0" style="margin-top:0px;"	>
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
							<textarea rows="3" name="remark" style="width:500px;"></textarea>
						</div>
					</div>
				</div>
				<!-- 审核第3行end -->
				<br class='float-clear' />
			<br class='float-clear' />
			<div class="form-actions">
				<div style='text-align:center;'>
					<a id='_sino_eoss_sales_contract_change_check' role="button" class="btn btn-success"><i class="icon-ok"></i>确认</a>
					<a id='_sino_eoss_sales_contract_change_cancel' role="button" class="btn"><i class="icon-reply"></i>取消</a>
				</div>
			</div>
			</c:if>
			<c:if test="${flowStep=='SHOW'}">
				<div style='text-align:center;'>
					<a id='_sino_eoss_sales_contract_change_back' role="button" class="btn"><i class="icon-reply"></i>返回</a>
				</div>
			</c:if>
			</form>
			<!-- END FORM-->  
		</div>
	<script language="javascript">
		seajs.use('js/page/sales/contractchange/applyDetail',function(applyDetail){
			applyDetail.init();
		});    
	</script>
</body>
</html>
