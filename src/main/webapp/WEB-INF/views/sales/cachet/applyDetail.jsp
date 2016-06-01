<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/global.jsp"%>
<%@ page import="com.sinobridge.systemmanage.util.Global"%> 
<%@ page import="com.sinobridge.systemmanage.vo.SystemUser"%> 
<html>
<head>
	<title>盖章申请</title>
	<%@ include file="/common/include-base-boostrap-styles.jsp" %>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formstyle.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/dialog.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/tip.css" type="text/css" ></link>
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
</head>
<body>
	<div class="salecontent">
		<div class="top">
			<div class="caption">
				<i class="icon-reorder"></i>&nbsp;&nbsp;&nbsp;盖章申请
				<span class="tright" >合同编号：${model.contractCode}</span>
			
			</div>
			<div class="tools">
				<!--a href="javascript:;" class="reload"><i class="icon-repeat"></i>返回</a-->
			</div>
		</div>
	</div>
		<div class="portlet-body form">
			<!-- BEGIN FORM-->
			<form id='_sino_eoss_sales_cachet_approveForm' class='form-horizontal' >
				<!--隐藏字段start -->
				<!--  隐藏的合同ID-->
				<input type='hidden' name='id' id='id'  value="${cachetModel.id}"/>
				<input type='hidden' name='processInstanceId' id='processInstanceId'  value="${cachetModel.processInstanceId}"/>
				<input type='hidden' name='attachIds' id='attachIds'  value="${cachetModel.attachIds}"/>
				<input type='hidden' name='creator' id='creator'  value="${cachetModel.creator}"/>
				<input type='hidden' name='createTime' id='createTime'  value="${model.createTime}"/>
				<input type='hidden' name='salesContractId' id='salesContractId'  value="${model.id}"/>
				<!--  隐藏的合同名称-->
				<input type='hidden' name='salesContractName' id='salesContractName'  value="${model.contractName}"/>
				<!--  隐藏的合同类型ID-->
				<input type="hidden" name="contractTypeId" id="contractTypeId" value="${model.contractType}"/>
				<!--隐藏的客户ID，用于回显-->
				<input type='hidden' name='customerId' id='_eoss_sales_customerId'  value="${model.customerId}"/>
				<!--  隐藏的附件ID-->
				<input type='hidden' name='attachIds' id='attachIds'/>
				<!--  隐藏的流程中所需要的任务ID-->
				<input type="hidden" id="taskId" name="taskId" value="${taskId}" >
				<!--  隐藏的流程中所需要的工单ID-->
				<input type="hidden" id="procInstId" name="procInstId" value="${procInstId}" >
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
					<h4 class="form-section-title">销售合同文档</h4>
				</div>
				<!-- 小标题end -->
				<!-- 4行start -->
				<div class=" row-inline" >
					<div id="uplaodfile"></div>
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
					<h4 class="form-section-title">审核日志</h4>
					<span style="float: right; width: 140px; margin-top: -30px;">
						<c:if test="${approName != ''&&approName != null}">
							当前审批人:${approName}
						</c:if>
					</span>
				</div>
				<!-- 小标题end -->
				<!-- 5行start -->	
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
									<td class="sino_table_label">${logs.time }</td>
									<td class="sino_table_label">${logs.apprDesc }</td>
								</tr>
							</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
				<!-- 5行end -->	
				<br class='float-clear' />
				<c:if test="${isFlow=='isFlow'}">
				<!-- 分割行start -->
				<div class=" row-inline" >
					<hr size=1 class="dashed-inline">
				</div>
				<!-- 分割行end -->
				<!-- 小标题start -->
				<div class=" row-inline" >
					<h4 class="form-section-title">审批</h4>
				</div>
				<!-- 小标题end -->
				<!-- 审核第1行start -->
				<div class=" row-inline" >
					<div class=''>
						<label class="checkbox inline">
							同意<input type="radio" id="inlineCheckbox1" name="isAgree" value="1" checked="checked">
						</label>
						<label class="checkbox inline">
							驳回<input type="radio" id="inlineCheckbox2" name="isAgree" value="0" >
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
				<!-- 审核第3行end -->
				<br class='float-clear' />
				<div class="form-actions">
					<div style="text-align:center">
						<a id='_sino_eoss_sales_contract_cachet_submit' role="button" class="btn btn-success"><i class="icon-ok"></i>确认</a>&nbsp;&nbsp;&nbsp;&nbsp;
						<a id='_sino_eoss_sales_cachet_back' role="button" class="btn"><i class="icon-remove "></i>取消</a>
					</div>
				</div>
				</c:if>
				</form>
			<!-- END FORM-->  
		</div>
	</div>
	<script language="javascript">
		seajs.use('js/page/sales/cachet/applyDetail',function(applyDetail){
			applyDetail.init();
		});    
	</script>
</body>
</html>
