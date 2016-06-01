<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ include file="/common/global.jsp" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
 <html>
  <head>
    <title>商务付款审批</title>
    <%@ include file="/common/include-base-boostrap-styles.jsp" %>
	<link href="${ctx}/skin/default/sales/sale.css" rel="stylesheet"/>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/sinobridge/pink/sino_form_grid.css" type="text/css" />
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
				<input type="hidden" id="paycurrency" name="paycurrency" value="${model.currency}" >
    <div class="handprocess_order" id="SheetDiv">
	<br>
	 <div style="height:30px;margin-top:10px;">
		 <div style="float:left;">供应商名称：${model.supplierName.supplierName}</div>
		 <div style="float:left;margin-left:20px;">开户银行：</label>${model.supplierName.bankName}</div>
	 </div>
	 <div style="height:30px;">
	 		<div style="float:left;">银行账号：${model.supplierName.bankAccount}</div>
	 		<div style="float:left;margin-left:20px;"><div style="float:left;">科目类型：</div><div id="coursesType" style="float:left;">
			           <input type="hidden" name="coursesType" id="_coursesTypeId" value="${model.coursesType}"/>
				    </div>
			</div>
			<div style="float:left;margin-left:20px;"><div style="float:left;">税率：</div><div id="_eoss_taxType"  style="float:left;">
				           <input type="hidden" name="taxType" id="_eoss_business_taxTypeId" value="${model.taxType}"/>
					    </div>
			</div>
			<div style="float:left;margin-left:20px;">计划付款时间：${model.planPayDate}</div>
			<div style="float:left;margin-left:20px;">申请人：${payApplyUser }</div>
	 </div>
	 <div style="height:30px;">
		 用途：${model.remark }
	 </div>		
			<div style="clear:both;"></div>
					<table id="_order_payment"class="table  table-bordered">
						  <thead>
						    <tr style="background-color: #f3f3f3;color: #888888;">
						      <th style="width:10%;">订单编号</th>
						      <th style="width:6%;">供应商简称</th>
						      <th style="width:6%;">付款金额
						      <c:if test="${model.currency=='usd' }">
						        <span style="font-weight:bold;color:#e99031;">(美元)</span>
						      </c:if>
						      </th>
						      <th style="width:20%;">用途</th>
						      <c:if test="${model.coursesType=='5'}">
						    	  <th style="width:8%;">签收单</th>
						      </c:if>
						      
						    </tr>
						  </thead>
						  <tbody>
						   <c:forEach var="plan" items="${model.businessPaymentPlanModel}" varStatus="status">
								<tr>
									<td class="sino_table_label"><a href="${ctx }/business/order/detail?id=${plan.businessOrder.id}&flat=search" target="_blank"><span style="color: #0000dd;">${plan.orderCode}</span></a></td>
									<td class="sino_table_label">${plan.supplierShortName}</td>
									<td class="sino_table_label">
									<c:if test="${model.currency=='usd' }">
									<fmt:formatNumber value="${plan.amount}" type="currency" currencySymbol="$"/>
									</c:if>
									<c:if test="${model.currency!='usd' }">
									<fmt:formatNumber value="${plan.amount}" type="currency" currencySymbol="￥"/>
									</c:if>
									</td>
									<td class="sino_table_label">${plan.remark}</td>
									<c:if test="${model.coursesType=='5'}">
										<td class="sino_table_label"><a href="${ctx }/business/order/fileDetail?id=${plan.businessOrder.id}" target="_blank"  class="pay_handler"><font color=Blue>已上传签收单</font></a></td>
									</c:if>
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
						      
					 <input type="hidden" id="payAmonut" name="payAmonut" value="${model.payAmonut}"/>
				 </li>
			</ul>	
	         <c:if test="${not empty model.realPayAmount}">
	          <ul class="clearfix" >
				 <li id="field_userName" class="li_form" style="width: 400px;float:right;" >
					 <label style="float:left; margin-right: 32px;width:200px;">（财务）实付人民币：</label><div style="float:left;"><span style="font-size:18px;font-weight:bold;"><fmt:formatNumber value="${ model.realPayAmount}" type="currency" currencySymbol="￥"/></span></div>
				  </li>
			   </ul>
	         </c:if>
			<div class=" " >
		         <hr size=1 class="dashed-inline">
            </div> 
			<div style="clear:both;"></div>
			<c:if test="${isFlow=='isFlow'}">
				<c:if test="${flowStep=='CWFH'}">
				<h3 style="margin-top:0px;">转账付款信息</h3>
					<ul class="clearfix" style="position: relative;">
							<li id="field_userName" class="li_form" style="width:400px;">
								<label >公司：</label>
									<div id="_company">
										<input type="hidden" id="payCompany" name="payCompany" value=""/>
									</div>
							</li>
							<li id="field_userName" class="li_form" style="width:400px;">
								<label >开户银行：</label> 
									<div id="_companyBank"></div>
										<input type="hidden" id="payCompanyBank" name="payCompanyBank" value=""/>
							</li>
						</ul>
						<ul class="clearfix" style="position: relative;padding-top:5px;">
							<li id="field_userName" class="li_form" style="width:400px;" >
								<label for="userName" class="editableLabel2">银行账号：</label>
								<span id="_bankAccount"></span>
							</li>
							<li id="field_userName" class="li_form" style="width:400px;">
								<label for="userName" class="editableLabel2">账户余额：</label> 
								<span id="_banlance" style="font-size:16px;font-weight:bold;color:green;"></span>
								<input type="hidden" id="banlance" name="banlance" value=""/>
							</li>
						</ul>
				    <ul class="clearfix" style="position: relative;">
						   <li id="field_userName" class="li_form" style="width:400px;">
						   		<label >付款方式：</label>
						   		   <div id="_eoss_business_paymentMode">
							            <input type="hidden" name="PayMethod" id="_eoss_business_paymentModeId"  value="${model.payMethod}"/>
								    </div>
						   </li>
						  <li id="field_userName" class="li_form" style="width:400px;">
								<label for="userName" >出纳员：</label>
									<div id="accountPersion"></div>
								<input type="hidden" id="payUser" name="payUser" value=""/>
						  </li>
				    </ul>
				     <ul class="clearfix" style="position: relative;padding-top:10px;">
						   <li id="field_userName" class="li_form" style="width: 400px;">
							    <label >付款日期：</label>
					 			<div style="position: relative;" class="input-append date">
									 <input data-format="yyyy-MM-dd"  name="realPayDate"  type="text"  style="width:183px" value='<fmt:formatDate value="${model.realPayDate}" type="date"/>'></input>
							    		<span class="add-on">
							    		  <i data-time-icon="icon-time" data-date-icon="icon-calendar">
							      		</i>
								</span>
						  </div>
						   </li>
						   <c:if test="${model.currency=='usd' }">
						   <li id="field_userName" class="li_form" style="width: 400px;">
						     <label >*付款<span style="color:#e99031;font-weight:bold;">(人民币)</span>：</label>
						     <input type="text"  id="realPayAmount" name="realPayAmount"   style="width:210px;"/>
						   </li>
						   </c:if>
				    </ul>
				
				<div class="" >
			         <hr size=1 class="dashed-inline">
	            </div> 
				<div style="clear:both;"></div>
				</c:if>
			<h3 style="margin-top:0px;">审批</h3>
			 <ul class="clearfix" style="position: relative;">
					   <li id="field_userName" class="li_form" style="width: 400px;">
						    <label style="color:#e99031;">审批：</label>
				 			<input type="radio" id="inlineCheckbox1" name="isAgree" value="1" style="margin:0 0 3px;" checked="checked"/>&nbsp;<span style="color:green;">通过</span>&nbsp;&nbsp;&nbsp;
				 			<br>
				 			<div style="margin-top:5px;">
						    <input type="radio" id="inlineCheckbox2" name="isAgree" value="0" style="margin:0 0 3px;" />&nbsp;<span style="color:red;">不通过</span>&nbsp;&nbsp;&nbsp;</div>
					   </li>
			</ul>
			<div class=" row-inline" style="margin-top:15px">
				<div class=''>
					<label class='row-label' style="color:#e99031;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;审批意见：</label>
					<div class='row-input'>
						<textarea rows="3" name="remark"></textarea>
					</div>
				</div>
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
						<c:if test="${model.planStatus=='10'||isPrint == '1'}">
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
seajs.use('js/page/business/payOrder/payDetail', function(payDetail){
	payDetail.init();
});    
</script>

</body>
</html>
