<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ include file="/common/global.jsp" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
 <html>
  <head>
    <title>修改发票报销申请</title>
    <%@ include file="/common/include-base-boostrap-styles.jsp" %>
	<link href="${ctx}/skin/default/sales/sale.css" rel="stylesheet"/>
	<link href="${ctx}/js/plugins/select2/css/select2.css" rel="stylesheet"/>
	<link rel="stylesheet" href="${ctx}/js/plugins/bootstrap/css/bootstrap-datetimepicker.min.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/sinobridge/pink/sino_form_grid.css" type="text/css" />
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formSelect.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formSearchTip.css" type="text/css" ></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formstyle.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/dialog.css" type="text/css"></link>


	<style type="text/css">
		.row-input{
		padding: 0 5px;
		width: 280px;
		height:28px"
	}
	.handprocess_order {
    padding: 0px;
    border-bottom: 0px solid #d7d7d7;
}
.handprocess_order li.li_form label {
    color: #888;
    display: inline-block;
    float: left;
    text-align: right;
    width: 98px;
    line-height: 25px;
}
.handprocess_order .li_form label.editableLabel {
    color: #000000;
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
				<i class="icon-reorder"></i>&nbsp;&nbsp;&nbsp;修改发票报销
			</div>

		</div>
	</div>	
	<!--表单、底部按钮 -->
	<div class="portlet-body form">
   	<form  id="_sino_eoss_cuotomer_addform" class='form-horizontal' method="post" action="">
		<input type="hidden" id="createTime" name="createTime" value="${model.createTime }"/>
		<input type="hidden" id="id" name="id" value="${model.id }"/>
		<input type="hidden" id="flowStep" name="flowStep" value="${flowStep}" >
	    <input type="hidden" id="taskId" name="taskId" value="${taskId}" >
		<input type="hidden" id="procInstId" name="procInstId" value="${procInstId}" >
		<input type="hidden" id="processInstanceId" name="processInstanceId" value="${model.processInstanceId}" >
    <div class="handprocess_order" id="SheetDiv">
    <br>
	<h3>申请报销信息</h3>
   	  <ul class="clearfix" >
			<li id="field_userName" class="li_form" style="width: 350px;">
				<label for="orderCode" class="editableLabel">报销名称：</label>
				<input name="reimbursementName" value="${model.reimbursementName }" readonly>
			</li>
			<li id="field_userName" class="li_form" style="width: 250px;">
				<label for="orderName" class="editableLabel">报销人：</label>
				${model.reimbursementUser }
				<input type="hidden"name="reimbursementUser" value="${model.reimbursementUser }" >
			</li>
		</ul>
		   <div style="clear:both;"></div>
			<h3>添加发票信息（<span style="color:red;font-size:12px;">一个订单可申请多次，但不能超过订单总金额</span>）
			<div style="float:right;margin-right:20px;">
				     <a id='selectOrder' role="button" href="#myModal1" class="btn btn-success"><i class="icon-plus"></i>添加</a>
				</div>
			</h3>
			
				<input type='hidden' name='tableData' id='tableData' />

		    <div id="alertMsg"></div>
			<table id="_order_reim" cellpadding="1" cellspacing="1" border="5" class="table table-striped table-bordered table-hover">
				<thead>
					<tr>					   
					     <td width="15%">订单编号</td>
						<td width="10%">供应商简称</td>
						<td width="8%">未计划金额</td>
					    <td width="8%">发票金额</td>
						<td width="7%">发票数量</td>
						<td width="8%">发生日期</td>
						<td width="5%">税率</td>
						<td width="5%">科目类型</td>
						<td width="5%">操作</td>
					</tr>
				</thead>
				<tbody>
					 <c:forEach var="plan" items="${listMap}" varStatus="status">
								<tr id='tr_${ status.index + 1}'>
									<td id="orderCode${ status.index + 1}" name="orderCode" tdValue="${plan.orderCode}">${plan.orderCode}</td>
									<td id="supplierShortName${ status.index + 1}" name="supplierShortName" tdValue="${plan.supplierShortName}">${plan.supplierShortName}</td>
									<td id="payAmount${ status.index + 1}" name="payAmount" tdValue="${plan.unplanAmount}">${plan.unplanAmount}</td>
									<td ><input class="amount" num='${ status.index + 1}' type="text" style="width:85%" id='amount${ status.index + 1}' name="amount" value="${plan.invoiceAmount }"/></td>
									<td ><input class="credit" num='${ status.index + 1}' type="text" style="width:70%" id='credit${ status.index + 1}' name="credit" value="${plan.invoiceNum}"/></td>
									<td><div style='position: relative;' class='input-append date'><input type='text' data-format='yyyy-MM-dd' id='serviceEndTime${ status.index + 1}' style='width:80px' value='${plan.invoiceDate }'/><span class='add-on'><i data-time-icon='icon-time' data-date-icon='icon-calendar'></i></span></div></td>
		 	   						<td><div id='taxRate${ status.index + 1}'value='${plan.taxRateType }' style='width:90%'><span id='_taxRate${ status.index + 1}'></span></div></td>
		 	   						<td><div id='coursesType${ status.index + 1}'value='${plan.coursesType }' style='width:90%'><span id='_coursesType${ status.index + 1}'></span></div></td>
									<td id ='orderId${ status.index + 1}' style='display:none'  name='orderId' tdValue='${plan.orderId }'></td>
		 	   					    <td id ='supplierId${ status.index + 1}' style='display:none'  name='supplierId'  tdValue='${plan.supplierId }'></td>
		 	   					    <td id="remark${status.index + 1}" style='display:none'  name="remark"  tdValue='${plan.remark }'></td>
									<td class="sino_table_label"><a num='${ status.index + 1}'  id='_remove_product_${ status.index + 1}' style="width:60%" class='btn btn-danger _remove_product'><i style="width:100%;font-size: 12px;">删除</i></a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>

				<label Style="float:right; margin-right: 32px;"> 发票金额：<span id='tatol'></span></label>
				<input type="hidden" id="invoiceAmount" name="amount"  value="${model.amount }"/>
				
			<div id="editTable"></div>
			<input type="hidden" id="_tableGridData" name="tableGridData">
			<br>
			<ul class="clearfix" >
				<li id="field_userName" class="li_form" style="width: 600px;" >
					<label  class="editableLabel">用途：</label>
					<textarea rows="3" style="width: 400px;" name="remark" value="${model.remark }">${model.remark }</textarea>
				</li>		   
			 </ul>	
	</div>
	</form>
	
	       <div id="dailogs1" class="modal hide fade" role="dialog" tabIndex="-1" style="width:780px;height: 510px;">
		   <div class="modal-header">
		         <a type="button" class="close" data-dismiss="modal" aria-hidden="true">×</a>
		         <h3 id="dtitle"></h3>
  		   </div>
		    <div class="modal-body" style="width:750px;height:400px;">
			     <div id="dialogbody" ></div>
		    </div>
		    <div id="bottom_button" class="modal-footer" style="text-align: center;">
        		<input id="ok_Order_Add" type="button" value="确定" class="btn btn-success"/>
    		</div>
        </div>
        
	<!--按钮组-->
    		<div id="bottom_button" class="handprocess_btngroup">
        		<input id="sp_Add" type="button" value="提交"  class="btn btn-success">
        		<input id="_sino_eoss_pay_end" type="button" value="删除付款申请"   class="btn">
        		<input id="no_back" type="button" value="取消" class="btn">
    		</div>
    </div>
    </div>
<script type="text/javascript">
	//var form = ${form};  //从controller层接收收款计划data_grid数据模型
	seajs.use('js/page/business/reimbursementApply/update', function(update){
		update.init();
	});    
</script>

</body>
</html>