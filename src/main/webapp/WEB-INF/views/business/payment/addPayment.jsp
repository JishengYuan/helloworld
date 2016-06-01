<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ include file="/common/global.jsp" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
 <html>
  <head>
    <title>添加付款计划</title>
    <%@ include file="/common/include-base-boostrap-styles.jsp" %>
	<link href="${ctx}/skin/default/sales/sale.css" rel="stylesheet"/>
	<link href="${ctx}/js/plugins/select2/css/select2.css" rel="stylesheet"/>
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
				<i class="icon-reorder"></i>&nbsp;&nbsp;&nbsp;添加付款计划
			    <span class="tright" style="color:#f4606c" >未计划金额：<fmt:formatNumber value="${orderDueAmnount}" type="currency" currencySymbol="￥"/> </span>
			</div>
			<div class="tools">
				<!--a href="javascript:;" class="reload"><i class="icon-repeat"></i>返回</a-->
			</div>
		</div>
	</div>	
	<!--表单、底部按钮 -->
	<div class="portlet-body form">
   	<form  id="_sino_eoss_cuotomer_addform" class='form-horizontal' method="post" action="">
   	<%-- <input type="hidden" name="planStatus" id="planStatus" value="${model.planStatus}"/> --%>
   	<input type="hidden" name="order" id="order" value="${order}"/>
   	<input type="hidden" id="_eoss_payment_id" value="${model.id}" name="id"/>
   	<input type="hidden" id="orderDueAmnount" name="orderDueAmnount" value="${orderDueAmnount}"/>
   	<input type="hidden" id="orderCode" name="orderCode" value="${modelOrder.orderCode }" />
   	<!--  隐藏的tableData初始化数据-->
	<input type='hidden' name='tableData' id='tableData' />
	<input type='hidden' name='colseType' id='colseType' value="${colseType}"/>
	<input type='hidden' name='invoiceType' id='invoiceType_Id' value="${model.invoiceType }"/>
	
    <div class="handprocess_order" id="SheetDiv">
    <br>
	<h3>订单信息</h3>
   	  <ul class="clearfix" >
			<li id="field_userName" class="li_form" style="width: 300px;">
				<label for="orderCode" class="editableLabel">订单编号：</label>
				${model.orderCode }
			</li>
			<li id="field_userName" class="li_form" style="width: 600px;">
				<label for="orderName" class="editableLabel">订单名称：</label>
				${model.orderName}
			</li>
		</ul>
		<ul class="clearfix" >
			<li id="field_userName" class="li_form" style="width: 300px;">
				<label for="orderName" class="editableLabel">供应商编码：</label>
				${model.supplierInfoModel.supplierCode}
			</li>
			<li id="field_userName" class="li_form" style="width: 600px;">
				<label for="orderCode" class="editableLabel">供应商名称：</label>
				${model.supplierInfoModel.supplierName }
			</li>
		</ul>	
		<ul class="clearfix" >
				<li id="field_userName" class="li_form" style="width: 350px;">
					<label class="editableLabel">订单金额：</label><fmt:formatNumber value="${model.orderAmount}" type="currency" currencySymbol="￥"/>
					<input type="hidden" id="orderAmount" name="orderAmount" value="${model.orderAmount}"/>
				</li>
		</ul>
			<%-- <ul class="clearfix" >
				<li id="field_userName" class="li_form" style="width: 450px;">
					<label class="editableLabel">未计划金额：</label>
					<input type="text" id="orderDueAmnount" name="orderDueAmnount" style="border: none;width:180px;" disabled="readonly" value="${orderDueAmnount}"/>
				</li>
			</ul> --%>
			<h3>付款计划（可申请多，但不能超过未计划金额）</h3>
		   			<div id="editTable"></div>
			<!-- <div class=" row-inline" >
			</div> -->
			
			<%-- <ul class="clearfix" >
				<li id="field_userName" class="li_form" style="width: 350px;">
					<label class="editableLabel">科目类型：</label>
					<div id="_coursesType">
			           <input type="hidden" name="coursesType" id="_coursesTypeId" value="${model.coursesType}"/>
				    </div>
				</li>
				<li id="field_userName" class="li_form" style="width: 350px;">
					<label class="editableLabel">帐期(天)： </label>
					<input type="text" id="credit" name="credit" class="row-input" value="${model.credit}" placeholder='请输入天数' required data-content="请输入账期天数" />
				</li>
			</ul>
			<ul class="clearfix" >
				<li id="field_userName" class="li_form" style="width: 350px;">
					<label  class="editableLabel">单据张数：</label>
					<input type="text" id="number" name="number" class="row-input" value="${model.number}"  placeholder='请输入张数' required data-content="请输入单据张数" />
				</li>
				<li id="field_userName" class="li_form" style="width: 320px;">
				<label class="editableLabel">付款时间：</label>
				    <div style="position: relative;" class="input-append date">
						 <input data-format="yyyy-MM-dd"  name="planTime"  type="text"  style="width:150px" value="${model.planTime}"></input>
						    <span class="add-on">
						      <i data-time-icon="icon-time" data-date-icon="icon-calendar">
						      </i>
							</span>
					  </div>
			     </li>
		    </ul>
		    <ul class="clearfix" >
				<li id="field_userName" class="li_form" style="width: 350px;">
					<label for="orderType" class="editableLabel">发票类型：</label>
						<div id="_eoss_business_invoiceType">
				           <input type="hidden" name="invoiceType" id="_eoss_business_invoiceTypeId" value="${model.invoiceType}"/>
					    </div>
			    </li>
				<li id="field_userName" class="li_form" style="width: 350px;">
					<label class="editableLabel"> 付款金额： </label>
						<input type="text" id="amount" name="amount"  class="row-input" value="${model.amount}" placeholder='请输入金额' required data-content="请输入付款金额"/>
				</li>
				
		    </ul>
		    <li id="field_userName" class="li_form" style="width: 670px;margin-left:24px" >
				<label  class="editableLabel"> 用途：</label>
		        	<textarea name="remark" id="remark" rows="3" cols="30" style="width:500px" value="${model.remark }"placeholder='请输入用途' required data-content="请输入用途" ></textarea>
			</li> --%>
			
			
			
		   <div style="clear:both;"></div>
	</div>
	</form>
	<!--按钮组-->
    		<div id="bottom_button" class="handprocess_btngroup">
        		<!-- <input id="ok_Add" type="button" value="保存"   class="btn btn-success"> -->
        		<input id="sp_Add" type="button" value="提交"   class="btn btn-success">
        		<input id="no_back" type="button" value="取消"  class="btn">
        		<!-- <a id='ok_Add' role="button" class="btn btn-success"><i class="icon-ok"></i>保存为草稿</a> -->
    		</div>
    </div>
    </div>
<script type="text/javascript">
	var form = ${form};  //从controller层接收收款计划data_grid数据模型
	seajs.use('js/page/business/payment/addPayment', function(addPayment){
		addPayment.init();
	});    
</script>

</body>
</html>