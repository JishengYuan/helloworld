<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ include file="/common/global.jsp" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
 <html>
  <head>
    <title>发票计划</title>
    <%@ include file="/common/include-base-boostrap-styles.jsp" %>
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
	.handprocess_order {
    padding: 0px;
    border-bottom: 0px solid #d7d7d7;
}
.handprocess_order li.li_form {
    line-height: 21px;
}
.handprocess_order li.li_form label {
    color: #888;
    display: inline-block;
    float: left;
    text-align: right;
    width: 98px;
}
.handprocess_order .li_form label.editableLabel {
    color: #000000;
}
input, textarea, .uneditable-input {
    width: 606px;
}
.popover {
  		max-width: 730px;
  		height:50%;
	}
.selectDiv .uicSelectInp {
	    background: none repeat scroll 0 0 #fff;
	    border: 1px solid #ccc;
	    cursor: pointer;
	    height: 14px;
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
	 table{
	  	font-size:12px;
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
				<i class="icon-reorder"></i>&nbsp;&nbsp;&nbsp;发票计划
			    <span class="tright" style="color:#f4606c">未开发票金额：<fmt:formatNumber value="${orderDueAmnount}" type="currency" currencySymbol="￥"/></span>
			</div>
			<div class="tools">
				<!--a href="javascript:;" class="reload"><i class="icon-repeat"></i>返回</a-->
			</div>
		</div>
	</div>	
	<!--表单、底部按钮 -->
	<div class="portlet-body form">
			   	<form  id="_sino_eoss_cuotomer_addform" class='form-horizontal' method="post" action="">
			    <input type="hidden" id="_eoss_customer_id" value="${model.id}" name="id">
				<input type="hidden" id="orderDueAmnount" name="orderDueAmnount" value="${orderDueAmnount}"/>
	    		<input type='hidden' name='tableData' id='tableData' />
    <div class="handprocess_order" id="SheetDiv">
    <br>
	<h3>订单信息</h3>
   	  <ul class="clearfix" >
			<li id="field_userName" class="li_form" style="width: 350px;">
				<label for="orderCode" class="editableLabel">订单编号：</label>
				${model.orderCode }
			</li>
			<li id="field_userName" class="li_form" style="width: 500px;">
				<label for="orderName" class="editableLabel">订单名称：</label>
				${model.orderName}
			</li>
		</ul>
		<ul class="clearfix" >
			<li id="field_userName" class="li_form" style="width: 350px;">
				<label class="editableLabel">订单金额：</label><fmt:formatNumber value="${model.orderAmount}" type="currency" currencySymbol="￥"/>
			</li>
			<li id="field_userName" class="li_form" style="width: 500px;">
				<label for="orderCode" class="editableLabel">供应商名称：</label>
				${model.supplierInfoModel.supplierName }
			</li>
		</ul>	
		<ul class="clearfix" >
			<li id="field_userName" class="li_form" style="width: 500px;">
				<label for="orderCode" class="editableLabel"> 相关发票申请：</label>
				<c:forEach var="reimApplys" items="${reimApplys}" varStatus="status">
					<a href="${ctx}/business/rbmApply/detail?id=${reimApplys.id}"><span style="color:blue">${reimApplys.reimbursementName}</span></a> &nbsp;&nbsp;&nbsp;
				</c:forEach>
				
			</li>	
		</ul>
			<div class=" row-inline" >
		         <hr size=1 class="dashed-inline">
            </div> 
			<div style="clear:both;"></div>
			<h3>发票计划</h3>
			<table id="_order_reim"class="table  table-bordered" style="width: 95%;margin-left: 24px;">
						  <thead>
						    <tr style="background-color: #f3f3f3;color: #888888;">
						        <th style="width:12%;">科目类型</th>
								<th style="width:8%;">单据张数</th>
								<th style="width:11%;">计划报销时间</th>
								<th style="width:11%;">实际报销时间</th>
								<th style="width:12%;">报销金额</th>
								<th style="width:43%;">用途</th>
						    </tr>
						  </thead>
						  <tbody>
							<c:forEach var="reim" items="${reim}" varStatus="status">
								<tr>
									<td class="sino_table_label" id="coursesType${ status.index}">${reim.coursesType}</td>
									<td class="sino_table_label">${reim.number}</td>
									<td class="sino_table_label">${reim.planTime}</td>
									<td class="sino_table_label">${reim.businessReimbursementApply.closeTime}</td>
									<td class="sino_table_label"><fmt:formatNumber value="${reim.amount}" type="currency" currencySymbol="￥"/></td>
									<td class="sino_table_label">${reim.remark }</td>
								</tr>
							</c:forEach>
						  </tbody>
						</table>
	</div>
			<div style="clear:both;"></div>
			
			<!-- 分割行start -->
			<div class="form-actions">
				<div style="text-align:center">
					<a id='_eoss_business_reim_back' role="button" class="btn"><i class="icon-remove-circle"></i>关闭</a>
				</div>
			</div>
	</form>
    </div>
  </div>
<script type="text/javascript">
	seajs.use('js/page/business/reimbursement/finish', function(finish){
		finish.init();
	});    
</script>

</body>
</html>