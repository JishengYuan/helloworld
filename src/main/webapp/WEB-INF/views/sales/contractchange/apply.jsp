<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/global.jsp"%>
<%@ page import="com.sinobridge.systemmanage.util.Global"%> 
<%@ page import="com.sinobridge.systemmanage.vo.SystemUser"%> 
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
</head>
<body>
<div style="position: fixed; margin-top: 50px; left: 50%;margin:0 0 0 480px; top: 30px; border: 1px solid #d8d8d8; background-color: orange;" class="_spot_div">
				<h5><font color=red>合同变更内容说明：<br></font></h5>
	
				<b>合同金额</b><br>
				只允许变更合同清单设备金额，不允许变更清单设备数量<br>
				<b>合同清单</b><br>
				                   1.商务部已经下单，不允许变更合同清单<br>
				                   2.如需要变更，请线下协调领导审批<br>
				 <b>备品备件</b><br>
				 只允许在合同清单中增加备品备件<br>
				                   <b>收款计划</b><br>
				                   只允许变更合同收款计划<br>
				                   <b>其他</b><br>
				                   只允许变更合同时间、联系人等类似的信息
			</div>
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
			<form id='_sino_eoss_sales_contract_change_addform' class='form-horizontal' >
				<!--隐藏字段start -->
				<!--  隐藏的合同ID-->
				<input type='hidden' name='saleContractId' id='salesContractId'  value="${model.id}"/>
				<!--  隐藏的合同类型ID-->
				<input type="hidden" name="contractTypeId" id="contractTypeId" value="${model.contractType}"/>
				<!--  隐藏的合同名称-->
				<input type='hidden' name='salesContractName' id='salesContractName'  value="${model.contractName}"/>
				<!--隐藏的客户ID，用于回显-->
				<input type='hidden' name='customerId' id='_eoss_sales_customerId'  value="${model.customerId}"/>
				<!--隐藏字段end -->
				<input type='hidden'  id='flat'  value=""/>
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
					<h4 class="form-section-title">变更的内容<span style="font-size:13px;"><font color=red >(请谨慎选择，变更会产生成本变化)</font></span></h4>
				</div>
				<!-- 小标题end -->
				<!-- 5行start -->
				<div class=" row-inline" >
					<div class=''>
						<c:if test="${statusModel.cachetStatus != '审批通过'&&statusModel.invoiceStatus != '审批通过'}">
							<label class='radio'>
								<input type="radio" name="changeType" value="1" />合同金额
							</label>&nbsp;
						</c:if>
						<c:if test="${statusModel.cachetStatus != '审批通过'&&statusModel.invoiceStatus == '未申请'&&statusModel.orderStatus == '未采购'}">
							<label class='radio'>
								<input type="radio" name="changeType" value="5"/>合同清单（设备变更）
							</label>&nbsp;
						</c:if>
						<label class='radio'>
							<input type="radio" name="changeType" value="9"/>添加产品
						</label>&nbsp;
						<!-- 
						<label class='radio'>
							<c:if test="${statusModel.cachetStatus != '审批通过'&&statusModel.invoiceStatus == '未申请'}">
								<input type="radio" name="changeType" value="7"/>合同清单（只变更设备型号）
							</c:if>
						</label>&nbsp;
						 -->
						<c:if test="${statusModel.cachetStatus != '审批通过'&&statusModel.invoiceStatus == '未申请'}">
							<label class='radio'>
								<input type="radio" name="changeType" value="8"/>收款计划
							</label>&nbsp;
						</c:if>
						<label class='radio'>
							<input type="radio" name="changeType" value="2"/>备品备件
						</label>&nbsp;
						<label class='radio'>
							<input type="radio" name="changeType" value="3"/>废弃
						</label>
						<label class='radio'>
							<input type="radio" name="changeType" value="4"  checked/>其他
						</label>
					</div>
				</div>
				<!-- 5行end -->
				<br class='float-clear' />
				<!-- 分割行start -->
				<div class=" row-inline" >
					<hr size=1 class="dashed-inline">
				</div>
				<!-- 小标题start -->
				<div class=" row-inline" >
					<h4 class="form-section-title">变更原因</h4>
				</div>
				<!-- 小标题end -->
				<!-- 4行start -->
				<div class=" row-inline" >
					<div class='row-input'>
						<textarea rows="3" placeholder='请输入详细的变更原因' required data-content="请输入详细的变更原因" name="remark" style="width:700px;"></textarea>
					</div>
				</div>
				<!-- 4行end -->
			</form>
			<!-- END FORM-->  
			<br class='float-clear' />
			<div class="form-actions">
				<div style='text-align:center;'>
					<a id='_sino_eoss_sales_contract_change_add' role="button" class="btn btn-success"><i class="icon-ok"></i>提交申请</a>
					<a id='_sino_eoss_sales_contract_change_back' role="button" class="btn"><i class="icon-reply"></i>取消</a>
				</div>
			</div>
		</div>
	<script language="javascript">
		seajs.use('js/page/sales/contractchange/apply',function(apply){
			apply.init();
		});    
	</script>
</body>
</html>
