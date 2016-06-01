<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ include file="/common/global.jsp" %>
 <html>
  <head>
    <title>商务报销审批</title>
    <%@ include file="/common/include-base-boostrap-styles.jsp" %>

	<link href="${ctx}/skin/default/sales/sale.css" rel="stylesheet"/>

	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/sinobridge/pink/sino_form_grid.css" type="text/css" />
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/dialog.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/tip.css" type="text/css" ></link>


	<style type="text/css">
		.row-input{
		padding: 0 5px;
		width: 180px;
		height:28px"
	}
.handprocess_order li.li_form {
    display: inline-block;
    float: left;
    font-size: 14px;
    line-height: 19px;
    padding: 3px 0;
    width: 32%;
}
.handprocess_order .li_form label.editableLabel {
    color: #000000;
}

input, textarea, .uneditable-input {
    width: 600px;
}
.form-horizontal .form-actions {
    padding-left: 0px;
}
#SheetDiv ul,h3{
  margin:0;
  padding:0;
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
				<i class="icon-reorder"></i>&nbsp;&nbsp;&nbsp;发票报销
			</div>
		</div>
	</div>	
	<!--表单、底部按钮 -->
	<div class="portlet-body form">
		<form  id="_sino_eoss_cuotomer_addform" class='form-horizontal' method="post" action="">
			    <input type="hidden" id="_eoss_customer_id" value="${model.id}" name="id">
			    <input type="hidden" id="flowStep" name="flowStep" value="${flowStep}" >
			    <input type="hidden" id="taskId" name="taskId" value="${taskId}" >
				<input type="hidden" id="procInstId" name="procInstId" value="${procInstId}" >
				<input type="hidden" id="reimbursementUser" name="reimbursementUser" value="${model.reimbursementUser}" >
				<input type="hidden" id="processInstanceId" name="processInstanceId" value="${model.processInstanceId}" >
    <div class="handprocess_order" id="SheetDiv">
	<br>
	        <div> <h3>报销名称：${model.reimbursementName } <div style="float:right;">${model.reimbursementUser } &nbsp;&nbsp;时间：${model.createTime }</div></h3>
	        </div>
	        <div style="margin-left:10px;">用途：${model.remark }</div>
			 
			<div style="clear:both;margin-top:5px;" ></div>
					<table id="_order_reim"class="table  table-bordered">
						  <thead>
						  <thead>
								<tr style="background-color: #f3f3f3;color: #888888;">					   
								    <td width="12%">订单编号</td>
									<td width="13%">供应商简称</td>
								    <td width="10%">发票金额</td>
									<td width="8%">发票数量</td>
									<td width="7%">税率</td>
									<td width="8%">科目类型</td>
									<td width="30%">用途</td>
								</tr>
						  </thead>
						  <tbody>
						   <c:forEach var="plan" items="${model.reimbursementModes}" varStatus="status">
								<tr>
									<td class="sino_table_label" rowspan="${fn:length(plan.contractReimbursements)+1 }" style='vertical-align : middle;'><a href="${ctx }/business/order/detail?id=${plan.businessOrder.id}&flat=search" target="_blank"><span style="color: #0000dd;">${plan.orderCode}</span></a></td>
									<td class="sino_table_label">${plan.supplierShortName}</td>
									<td class="sino_table_label"><fmt:formatNumber value="${plan.amount}" type="currency" currencySymbol="￥"/></td>
									<td class="sino_table_label">${plan.number}</td>
									<td class="sino_table_label" id="taxRate${ status.index}">${plan.invoiceType}</td>
									<td class="sino_table_label" id="coursesType${ status.index}">${plan.coursesType}</td>
									<td class="sino_table_label">${plan.remark}</td>
								</tr>
								<c:forEach var="reimbursement"  items="${plan.contractReimbursements }" varStatus="rbmstatus">
								<c:if test="${reimbursement.contractName!=null}">
									<tr>
									<td class="sino_table_label" colspan="6">${reimbursement.contractName },${reimbursement.contractCode },
									${reimbursement.creatorName },
									<fmt:formatNumber value="${reimbursement.contractAmount }" type="currency" currencySymbol="￥"/>;
									发票：<span style="font-size:16px;color:#e99031;font-weight:bold;"><fmt:formatNumber value="${reimbursement.rbmAmount }" type="currency" currencySymbol="￥"/></span></td>
									</tr>
								</c:if>
								<c:if test="${reimbursement.PurchasName!=null}">
									<tr>
									<td class="sino_table_label" colspan="6">${reimbursement.PurchasName },${reimbursement.PurchasCode },${reimbursement.Creator }
									
									</td>
									</tr>
								</c:if>
								</c:forEach>
							</c:forEach>
						  </tbody>
						</table>
			<ul class="clearfix" >
				 <li id="field_userName" class="li_form" style="width: 400px;float:right;" >
					 <label Style="float:right; width: 300px;">报销发票金额：<span style="font-size:18px;color:#e99031;font-weight:bold;"><fmt:formatNumber value="${model.amount}" type="currency" currencySymbol="￥"/></span>
				 </li>
			</ul>	
			<div class=" row-inline" >
		         <hr size=1 class="dashed-inline">
            </div> 
			<div style="clear:both;"></div>
			<c:if test="${isFlow=='isFlow'}">
				<h3>审批</h3>
				 <ul class="clearfix" style="position: relative;">
						   <li id="field_userName" class="li_form" style="width: 400px;">
						    <label style="color:#e99031;">审批：</label>
				 			<input type="radio" id="inlineCheckbox1" name="isAgree" value="1" style="margin:0 0 3px;" checked="checked"/>&nbsp;<span style="color:green;">通过</span>&nbsp;&nbsp;&nbsp;
				 			<br>
				 			<div style="margin-top:5px;">
						    <input type="radio" id="inlineCheckbox2" name="isAgree" value="0" style="margin:0 0 3px;" />&nbsp;<span style="color:red;">不通过</span>&nbsp;&nbsp;&nbsp;</div>
						   </li>
				</ul>
				<ul class="clearfix" style="position: relative;">
					 <li id="field_userName" class="li_form" style="width: 400px;">
						<label class='row-label' style="color:#e99031;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;审批意见：</label>
						<div class='row-input' style="padding:0;margin:0;">
							<textarea rows="3" name="remark"></textarea>
						</div>
					 </li>
				</ul>
				<div class=" row-inline" >
		        	 <hr size=1 class="dashed-inline">
           		 </div> 
				<div style="clear:both;"></div>
			</c:if>
			
			<h3>审批日志</h3>
					<table border="0" style="width: 100%;color:#888;" class="sino_table_body" id="_fee_travel">
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
		<div style="clear:both;"></div>
			<div class="form-actions">
				<div style="text-align:center">
					<c:if test="${isFlow=='isFlow'}">
						<a id='_sino_eoss_pay_submit' role="button" class="btn btn-success"><i class="icon-ok"></i>确认</a>&nbsp;&nbsp;&nbsp;&nbsp;
					</c:if>
					<c:if test="${isFlow!='isFlow' }">
						<c:if test="${model.reimBursStatus=='10'||isPrint=='1'}">
							<a role="button"  id="_print" class="btn btn-success" name="print"><i class="icon-ok"></i>打印</a>&nbsp;&nbsp;&nbsp;&nbsp;
						</c:if>
					</c:if>
					<a id='_eoss_business_pay_back' role="button" class="btn">返回</a>
				</div>
			</div>
    </div>
    	<div style="clear:both;"></div>
	</form>
	</div>
  </div>
  
  
<script type="text/javascript">
seajs.use('js/page/business/reimbursementApply/detail', function(detail){
	detail.init();
});    
</script>

</body>
</html>
