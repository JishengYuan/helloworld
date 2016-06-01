<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/global.jsp"%>
<html>
<head>
	<title>重新提交发票申请</title>
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
    </style>
</head>
<body>
	<div class="salecontent">
		<div class="top">
			<div class="caption">
				<i class="icon-reorder"></i>&nbsp;&nbsp;&nbsp;发票申请
				<span class="tright" style="color:#f4606c">该合同可申请的发票金额：${surplusAmount}元</span>
			</div>
			<div class="tools">
				<!--a href="javascript:;" class="reload"><i class="icon-repeat"></i>返回</a-->
			</div>
		</div>
	</div>
		<div class="portlet-body form">
			<!-- BEGIN FORM-->
			<form id='_sino_eoss_sales_invoice_reApplyForm' class='form-horizontal' >
				<!--隐藏字段start -->
				<!--  隐藏的合同ID-->
				<input type='hidden' name='id' id='id'  value="${model.id}"/>
				<input type='hidden' name='salesContractId'  value="${model.id}"/>
				<!--  隐藏的合同类型ID-->
				<input type="hidden" name="contractTypeId" id="contractTypeId" value="${model.contractType}"/>
				<!--  隐藏的合同名称-->
				<input type='hidden' name='contractName' id='contractName'  value="${model.contractName}"/>
				<!--隐藏的客户ID，用于回显-->
				<input type='hidden' name='customerId' id='_eoss_sales_customerId'  value="${model.customerId}"/>
				<!--  隐藏的tableData初始化数据-->
				<input type='hidden' name='tableData' id='tableData' />
				<!--  隐藏的发票状态-->
				<input type='hidden' name='invoiceState' id='invoiceState' value="SH"/>
				<!--进入流程后重新审批的标志位-->
				<input type="hidden" id="flowFlag" name="flowFlag" value="${flowStep}" >
				<!--  隐藏的流程中所需要的任务ID-->
				<input type="hidden" id="taskId" name="taskId" value="${taskId}" >
				<!--  隐藏的流程中所需要的工单ID-->
				<input type="hidden" id="procInstId" name="procInstId" value="${procInstId}" >
				<!--  隐藏的剩余可开发票金额数据-->
				<input type='hidden' name='surplusAmount' id='surplusAmount' value="${surplusAmount}"/>
				
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
				<div class=" row-inline" >
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
				</div>
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
					<h4 class="form-section-title">发票申请条目(可申请多条，但不能超过合同总金额)</h4>
				</div>
				<!-- 小标题end -->
				<!-- 4行start -->
				<div class=" row-inline" >
		   			<div id="editTable"></div>
				</div>
				<!-- 4行end -->
			</form>
			<!-- END FORM-->  
			<br class='float-clear' />
			<div class="form-actions">
				<div style='text-align:center;'>
					<a id='_sino_eoss_sales_contract_invoice_reSubmit' role="button" class="btn btn-success"><i class="icon-ok"></i>重新提交</a>
					<a id='_sino_eoss_sales_contract_invoice_NotReSubmit' role="button" class="btn btn-danger"><i class="icon-remove"></i>废弃该合同发票申请</a>
					<a id='_sino_eoss_sales_contract_invoice_cancel' role="button" class="btn"><i class="icon-reply"></i>取消</a>
				</div>
			</div>
		</div>
	<script language="javascript">
		var form = ${form};  //从controller层接收收款计划data_grid数据模型
		seajs.use('js/page/sales/invoice/reApply',function(reApply){
			reApply.init();
		});    
	</script>
</body>
</html>
