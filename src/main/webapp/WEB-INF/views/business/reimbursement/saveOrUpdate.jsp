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
				<i class="icon-reorder"></i>&nbsp;&nbsp;&nbsp;修改发票计划
			    <span class="tright" style="color:#f4606c">该订单可申请的发票金额：${orderDueAmnount}元</span>
			</div>
			<div class="tools">
				<!--a href="javascript:;" class="reload"><i class="icon-repeat"></i>返回</a-->
			</div>
		</div>
	</div>	
	<!--表单、底部按钮 -->
	<div class="portlet-body form">
   	<form  id="_sino_eoss_cuotomer_addform" class='form-horizontal' method="post" action="">
    <input type="hidden" id="_eoss_reim_id" value="${model.id}" name="id">
  	<input type="hidden" id="taskId" name="taskId" value="${taskId}" >
	<input type="hidden" id="procInstId" name="procInstId" value="${procInstId}" >
	<input type="hidden" id="flowStep" name="flowStep" value="${flowStep}" >
	<input type="hidden" name="order" id="order" value="${order}"/>
	<input type="hidden" id="orderDueAmnount" name="orderDueAmnount"  value="${orderDueAmnount}"/>
	<input type="hidden" id = "status" name = "status" value = ""/>
	<!--  隐藏的tableData初始化数据-->
	<input type='hidden' name='tableData' id='tableData' />
    <div class="handprocess_order" id="SheetDiv">
    <br>
	<h3>订单信息</h3>
   	  <ul class="clearfix" >
			<li id="field_userName" class="li_form" style="width: 350px;">
				<label for="orderCode" class="editableLabel">订单编号：</label>
				${model.orderCode }
			</li>
			<li id="field_userName" class="li_form" style="width: 350px;">
				<label for="orderName" class="editableLabel">订单名称：</label>
				${model.orderName}
			</li>
		</ul>
		<ul class="clearfix" >
			<li id="field_userName" class="li_form" style="width: 350px;">
				<label for="orderName" class="editableLabel">供应商编码：</label>
				${model.supplierInfoModel.supplierCode}
			</li>
			<li id="field_userName" class="li_form" style="width: 500px;">
				<label for="orderCode" class="editableLabel">供应商名称：</label>
				${model.supplierInfoModel.supplierName }
			</li>
		</ul>	
			<ul class="clearfix" >
				<li id="field_userName" class="li_form" style="width: 350px;">
					<label class="editableLabel">订单金额：</label>${model.orderAmount}
					<input type="hidden" id="orderAmount" name="orderAmount" value="${model.orderAmount}"/>
				</li>
			</ul>
			<h3>付款计划</h3>
				<div class=" row-inline" >
		   			<div id="editTable"></div>
			    </div>
		   <div style="clear:both;"></div>
	</div>
	</form>
	<!--按钮组-->
    		<div id="bottom_button" class="handprocess_btngroup">
        		<input id="_sino_eoss_reim_reSubmit" type="button" value="重新提交"  class="btn btn-success">
        		<input id="_sino_eoss_reim_end" type="button" value="删除计划"   class="btn">
        		<input id="no_back" type="button" value="取消" class="btn">
    		</div>
    		
    </div>
    </div>
<script type="text/javascript">
var form = ${form};  //从controller层接收收款计划data_grid数据模型
seajs.use('js/page/business/reimbursement/saveOrUpdate', function(saveOrUpdate){
	saveOrUpdate.init();
});    
</script>

</body>
</html>