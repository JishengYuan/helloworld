<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ include file="/common/global.jsp" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
 <html>
  <head>
    <title>商务采购</title>
    <%@ include file="/common/include-base-boostrap-styles.jsp" %>
	<link href="${ctx}/skin/default/sales/sale.css" rel="stylesheet"/>
	<link rel="stylesheet" href="${ctx}/js/plugins/bootstrap/css/bootstrap-datetimepicker.min.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/sinobridge/pink/sino_form_grid.css" type="text/css" />
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formSelect.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formSearchTip.css" type="text/css" ></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formstyle.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/dialog.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/tip.css" type="text/css" ></link>

	<style type="text/css">
		.row-input{
		padding: 0 5px;
		width: 180px;
		height:28px"
	}
.handprocess_order ul{
  margin-bottom:5px;
}
.handprocess_order li.li_form {
    display: inline-block;
    float: left;
    font-size: 14px;
    line-height: 19px;
    padding: 0;
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
				<i class="icon-reorder"></i>&nbsp;&nbsp;&nbsp;付款申请
			    <span class="tright" >付款申请名称：${model.payApplyName }</span>
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
				<input type="hidden" id="payApplyName" name="payApplyName" value="${model.payApplyName}" >
				<input type="hidden" id="processInstanceId" name="processInstanceId" value="${model.processInstanceId}" >
    <div class="handprocess_order" id="SheetDiv">
	<br>
	<h3>付款申请信息</h3>
   	 <ul class="clearfix">
		    	 <li id="field_userName" class="li_form" style="width: 400px;" >
					<label  class="editableLabel">申请人：</label>${payApplyUser }
				</li>
   	 			<li id="field_userName" class="li_form" style="width: 500px;">
					<label class="editableLabel">科目类型：</label>
					<div id="coursesType">
			           <input type="hidden" name="coursesType" id="_coursesTypeId" value="${model.coursesType}"/>
				    </div>
				</li>
		</ul>
		 <ul class="clearfix" >
		   		 <li id="field_userName" class="li_form" style="width: 400px;">
					<label class="editableLabel">付款时间：</label>${model.planPayDate}
			     </li>
				<li id="field_userName" class="li_form" style="width: 500px;">
					<label for="orderType" class="editableLabel">发票类型：</label>
						<div id="_eoss_business_invoiceType">
				           <input type="hidden" name="invoiceType" id="_eoss_business_invoiceTypeId" value="${invoiceType }"/>
					    </div>
			    </li>
		 </ul>
		<ul class="clearfix" >
			<li id="field_userName" class="li_form"  style="width: 400px;">
				<label id="suppliers" class="editableLabel">供应商名称：</label>${model.supplierName.supplierName}
			</li>
			 <li id="field_userName" class="li_form"  style="width:400px">
				<label class="editableLabel" >银行账号：</label>${model.supplierName.bankAccount}
			</li>
		 </ul>
		    <ul class="clearfix" >
		    <li id="field_userName" class="li_form" style="width:400px" >
				<label class="editableLabel">开户银行：</label>${model.supplierName.bankName}
			</li>
				<li id="field_userName" class="li_form" style="width: 500px;" >
					<label  class="editableLabel">用途：</label>${model.remark }
				</li>		   
			 </ul>	
			<div style="clear:both;"></div>
					<table id="_order_payment"class="table  table-bordered">
						  <thead>
						    <tr style="background-color: #f3f3f3;color: #888888;">
						      <th style="width:8%;">订单编号</th>
						      <th style="width:6%;">供应商简称</th>
						      <th style="width:6%;">付款金额</th>
						      <th style="width:20%;">用途</th>
						    </tr>
						  </thead>
						  <tbody>
						   <c:forEach var="plan" items="${model.businessPaymentPlanModel}" varStatus="status">
								<tr>
									<td class="sino_table_label">${plan.orderCode}</td>
									<td class="sino_table_label">${plan.supplierShortName}</td>
									<td class="sino_table_label"><fmt:formatNumber value="${plan.amount}" type="currency" currencySymbol="￥"/></td>
									<td class="sino_table_label">${plan.remark}</td>
								</tr>
							</c:forEach>
						  </tbody>
						</table>
			<ul class="clearfix" >
			 <li id="field_userName" class="li_form" style="width: 400px;float:right;" >
					 <label style="float:left; margin-right: 32px;width:200px;"> 付款金额 
					 			<c:if test="${model.currency=='usd' }">
						        <span style="font-weight:bold;color:#e99031;">(美元)</span>
						        ：</label><div style="float:left;"><span style="font-size:18px;color:#e99031;font-weight:bold;width:200px;"><fmt:formatNumber value="${model.payAmonut}" type="currency" currencySymbol="$"/></span></div>
						      </c:if>
						      <c:if test="${model.currency!='usd' }">
						        <span style="font-weight:bold;color:#e99031;"></span>
						        ：</label><div style="float:left;"><span style="font-size:18px;color:#e99031;font-weight:bold;width:200px;"><fmt:formatNumber value="${model.payAmonut}" type="currency" currencySymbol="￥"/></span></div>
						      </c:if>
				 </li>
			</ul>	
			<c:if test="${not empty model.realPayAmount}">
	          <ul class="clearfix" >
				 <li id="field_userName" class="li_form" style="width: 400px;float:right;" >
					 <label style="float:left; margin-right: 32px;width:200px;">（财务）实付人民币：</label><div style="float:left;"><span style="font-size:18px;font-weight:bold;"><fmt:formatNumber value="${ model.realPayAmount}" type="currency" currencySymbol="￥"/></span></div>
				  </li>
			   </ul>
	         </c:if>
	
			<div class=" row-inline" >
		         <hr size=1 class="dashed-inline">
            </div> 
			<div style="clear:both;"></div>
				<h3>转账付款信息</h3>
					<ul class="clearfix" style="position: relative;">
							<li id="field_userName" class="li_form" style="width:400px;">
								<label class="editableLabel">公司：</label>${Company}
							</li>
							<li id="field_userName" class="li_form" style="width:400px;">
								<label class="editableLabel">开户银行：</label> ${mapList.BankName}
							</li>
						</ul>
						<%-- <ul class="clearfix" style="position: relative;padding-top:5px;">
							<li id="field_userName" class="li_form" style="width:400px;" >
								<label class="editableLabel">银行账号：</label>${mapList.BankAccount}
							</li>
							<li id="field_userName" class="li_form" style="width:400px;">
								<label class="editableLabel">账户余额：</label> ${mapList.Balance}
							</li>
						</ul> --%>
				    <ul class="clearfix" style="position: relative;">
						   <li id="field_userName" class="li_form" style="width:400px;">
						   		<label class="editableLabel">付款方式：</label>
						   		   <div id="_eoss_business_paymentMode">
							            <input type="hidden" name="PayMethod" id="_eoss_business_paymentModeId"  value="${model.payMethod}"/>
								    </div>
						   </li>
						  <li id="field_userName" class="li_form" style="width:400px;">
								<label class="editableLabel">出纳员：</label>${payUser}
						  </li>
				    </ul>
				     <ul class="clearfix" style="position: relative;padding-top:10px;">
						   <li id="field_userName" class="li_form" style="width: 400px;">
							    <label class="editableLabel">付款日期：</label>${model.realPayDate}
						  </div>
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
		<div style="clear:both;"></div>
			<div class="form-actions">
				<div style="text-align:center">
					<a id='_eoss_business_pay_back' role="button" class="btn">返回</a>
				</div>
			</div>
    </div>
    	<div style="clear:both;"></div>
	</form>
	</div>
  </div>
  
  
<script type="text/javascript">
seajs.use('js/page/business/payOrder/payFinish', function(payFinish){
	payFinish.init();
});    
</script>

</body>
</html>
