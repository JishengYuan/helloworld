<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/global.jsp"%>
<%@ page import="com.sinobridge.systemmanage.util.Global"%> 
<%@ page import="com.sinobridge.systemmanage.vo.SystemUser"%> 
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
	<link rel="stylesheet" href="${ctx}/js/plugins/bootstrap/css/bootstrap-datetimepicker.min.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/sinobridge/pink/sino_form_grid.css" type="text/css" />
<style type="text/css">
table {
    border-collapse: separate;
    max-width: 100%;
}
.select-box {
    background: none repeat scroll 0 0 #FFFFFF;
    border: 1px solid #D7D7D7;
    border-radius: 6px;
    box-shadow: 3px 3px 10px #999999;
    position: absolute;
    top: 30px;
    z-index: 9999;
}

.form_table table th {
    background: none repeat scroll 0 0 #F3F3F3;
    border-bottom: 1px solid #D7D7D7;
    border-left: 1px solid #D7D7D7;
    border-top: 1px solid #D7D7D7;
    color: #888888;
    line-height: 22px;
    padding: 3px 3px 3px 10px;
    text-align: left;
}

.selectDiv a.uicSelectMore {
    display: block;
    height: 21px;
    position: absolute;
    right: 0;
    top: 0;
    width: 25px;
}
label.editableLabel {
    color: #E99031;
    margin-top: 3px;
    
}
.li_layout {
     width:300px;
     display: inline-block;
     float: left;
     font-size: 14px;
     line-height: 25px;
     padding: 3px 0;
     font-family: "Helvetica Neue",Helvetica,Arial,sans-serif;
     list-style: none outside none;
}
.div_layout{
     margin-top: -25px;
     margin-left: 80px;
}
.li_layout2 {
     width:400px;
     display: inline-block;
     float: left;
     font-size: 14px;
     line-height: 25px;
     padding: 3px 0;
}
</style>
	<script src="${ctx}/js/plugins/seajs/sea.js" id="seajsnode" type="text/javascript"></script>
	<script src="${ctx}/js/plugins/seajs/config.js" type="text/javascript"></script>
</head>
<body>
<div class="salecontent">
		<div class="top">
			<div class="caption">
				<i class="icon-reorder"></i>&nbsp;&nbsp;&nbsp;上传客户签收单
			    <span class="tright" >订单编号：${model.orderCode }</span>
			</div>
			<div class="tools">
				<!--a href="javascript:;" class="reload"><i class="icon-repeat"></i>返回</a-->
			</div>
		</div>
	</div>
		<!--表单、底部按钮 -->
		<div class="portlet-body form">
			<form id="_eoss_business_order" novalidate="novalidate" style="" method="post">
				<input type="hidden" name="id" id="id" value="${model.id }"/>
				<!--  隐藏的附件ID-->
				<input type='hidden' name='attachIds' id='attachIds'/>
				<div class="handprocess_order" id="SheetDiv">
				<br>
		        <h3>订单信息</h3>
		   	  <ul class="clearfix" >
					<li id="field_userName" class="li_form" style="width: 600px;">
						<label for="orderName" class="editableLabel">订单名称：</label>
						${model.orderName}
					</li>
					<li id="field_userName" class="li_form" style="width: 300px;">
							<label class="editableLabel">订单金额：</label><fmt:formatNumber value="${model.orderAmount }" type="currency" currencySymbol="￥"/>
					</li>
				</ul>	
					<ul class="clearfix" >
					<li id="field_userName" class="li_form" style="width: 600px;">
						<label for="orderCode" class="editableLabel">供应商名称：</label>
						${model.supplierInfoModel.supplierName }
					</li>
					<li id="field_userName" class="li_form" style="width: 300px;">
						<label for="orderName" class="editableLabel">供应商编码：</label>
						${model.supplierInfoModel.supplierCode}
					</li>
				</ul>
					
			   <h3>确认信息</h3>
				<ul class="clearfix" >
					<li id="field_userName" class="li_form" style="width: 600px;">
						<label for="orderName" class="editableLabel">发货时间：</label>
						 <div style="position: relative;" class="input-append date">
						 <input data-format="yyyy-MM-dd"  name="arrivalTime"  type="text"  style="width:150px" value="${model.arrivalTime}"></input>
						    <span class="add-on">
						      <i data-time-icon="icon-time" data-date-icon="icon-calendar">
						      </i>
							</span>
					  </div>
					</li>
					<div class=" row-inline" >
				        <div id="uplaodfile"></div>
			        </div>
				</ul>
			    <div style="clear:both;"></div>	
       </div>
</form>
			<!--按钮组-->
			<div id="bottom_button" class="handprocess_btngroup">
				<input type="button" id="_eoss_business_order_ok" value="确定" class="btn btn-success" style="height:30px;">
				<input type="button" id="_eoss_business_order_back" value="返回"  class="btn" style="height:30px;">
			</div>
		</div>
	</div>
	
<script language="javascript">
seajs.use('js/page/business/order/fileUpdate', function(fileUpdate) {
	fileUpdate.init();
}); 
</script>
</body>
</html>