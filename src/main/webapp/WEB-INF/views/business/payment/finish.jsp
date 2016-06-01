<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ include file="/common/global.jsp" %>
 <html>
  <head>
    <title>商务采购</title>
    <%@ include file="/common/include-base-boostrap-styles.jsp" %>
	<link rel="stylesheet" href="${ctx}/skin/default/room.css" type="text/css" />
	<link href="${ctx}/skin/default/sales/sale.css" rel="stylesheet"/>
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

		
	</style>
	<script src="${ctx}/js/plugins/seajs/sea.js" id="seajsnode" type="text/javascript"></script>
	<script src="${ctx}/js/plugins/seajs/config.js" type="text/javascript"></script>
  </head>
  <body>
  
<div class="salecontent">
		<div class="top">
			<div class="caption">
				<i class="icon-reorder"></i>&nbsp;&nbsp;&nbsp;付款计划
			    <%-- <span class="tright" >订单编号：${model.orderCode }</span> --%>
			</div>
			<div class="tools">
				<!--a href="javascript:;" class="reload"><i class="icon-repeat"></i>返回</a-->
			</div>
		</div>
	</div>	
	<!--表单、底部按钮 -->
	<div class="portlet-body form">
		<form  id="_sino_eoss_cuotomer_addform" class='form-horizontal' method="post" action="">
		<form id='_sino_eoss_payment_addform' class='form-horizontal'>
			    <input type="hidden" id="_eoss_customer_id" value="${model.id}" name="id">
    <div class="handprocess_order" id="SheetDiv">
	<br>
	<br>
   	  <ul class="clearfix" >
			<li id="field_userName" class="li_form" style="width: 300px;">
				<label for="orderCode" class="editableLabel"> 订单编号：</label>${model.businessOrder.orderCode }
			</li>
			<li id="field_userName" class="li_form" style="width: 300px;">
				<label for="orderName" class="editableLabel"> 创建时间：</label>${model.createTime}
			</li>
			<li id="field_userName" class="li_form" style="width: 300px;">
				<label for="orderCode" class="editableLabel"> 创建人：</label>${model.creator}
			</li>
		</ul>	
			<div class=" row-inline" >
		         <hr size=1 class="dashed-inline">
            </div> 
			<div style="clear:both;"></div>
					<table id="_order_payment"class="table  table-bordered">
						  <thead>
						    <tr style="background-color: #f3f3f3;color: #888888;">
						      <th style="width:8%;">科目类型</th>
						      <th style="width:7%;">帐期(天)</th>
						      <th style="width:8%;">单据张数</th>
						      <th style="width:9%;">付款时间</th>
						      <th style="width:19%;">发票类型</th>
						      <th style="width:10%;">付款金额</th>
						      <th style="width:13%;">用途</th>
						    </tr>
						  </thead>
						  <tbody>
								<tr>
									<td class="sino_table_label" ><span id="coursesType"></span></td>
									<input type="hidden" id="_ctype" name="_ctype" value="${model.coursesType}"/>
									<td class="sino_table_label">${model.credit}</td>
									<td class="sino_table_label">${model.number}</td>
									<td class="sino_table_label"><fmt:formatDate pattern="yyyy-MM-dd" value="${model.planTime}" /></td>
									<td class="sino_table_label"><span id="invoiceTypePay"></span></td>
									<input type="hidden" id="_itype" name="_itype" value="${model.invoiceType}"/>
									<td class="sino_table_label">${model.amount}</td>
									<td class="sino_table_label">${model.remark }</td>
								</tr>
						  </tbody>
						</table>
			
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
					<a id='_eoss_business_payment_back' role="button" class="btn">返回</a>
				</div>
			</div>
    </div>
	</form>
	</div>
  </div>
  
  
<script type="text/javascript">
seajs.use('js/page/business/payment/finish', function(finish){
	finish.init();
});    
</script>

</body>
</html>
