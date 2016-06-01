<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <%@ include file="/common/global.jsp" %>
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
		width: 180px;
		height:28px"
	}

.row-input-oneColumn {
    width: 480px;
}
body {
    font-size: 12px;
}
.handprocess_order .li_form label.editableLabel {
    color: #eeb422;
}

.handprocess_order .li_form label.editableLabel2 {
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
				<i class="icon-reorder"></i>&nbsp;&nbsp;&nbsp;申请变更
			    <span class="tright" >订单编号：<span id="Code">${model.orderCode}</span></span>
			</div>
			<div class="tools">
			</div>
		</div>
	</div>	
	<!--表单、底部按钮 -->
	<div class="portlet-body form">
	<form  id="_sino_eoss_cuotomer_addform" class='form-horizontal' method="post"/>
	<!--隐藏字段start -->
	<input type="hidden" id="_eoss_customer_id" value="${model.id }" name="id"/>
	<input type="hidden" id="orderName" value="${model.orderName }" name="orderName"/>
	<input type="hidden" id="_interPurchas" value="" name="interPurchas"/>
	<div class="handprocess_order" id="SheetDiv">
	<br>
		<h3>订单基本信息</h3>
		
		<ul class="clearfix" >
			<li id="field_userName" class="li_form" style="width:600px">
				<label class="editableLabel">订单名称：</label>${model.orderName }
			</li>
		</ul>
		<ul class="clearfix" >
			<li id="field_userName" class="li_form" style="width: 600px;">
		         <label class="editableLabel" style="color:#e99031;">变更内容：</label>
		         <textarea id="remark" name="remark" rows="3" cols="30" style="width:450px" value="" placeholder='请输入变更内容' required data-content="请输入变更内容"></textarea>
		   </li>
		</ul>
       </div>     
</form>
	<!--按钮组-->
    		<div id="bottom_button" class="handprocess_btngroup">
        		<input id="sp_Add" type="button" value="提交"  class="btn btn-success" >
        		<input id="no_back" type="button" value="取消" class="btn">
    		</div>
     </div>
</div>
</div>

 <script language="javascript">
 seajs.use('js/page/business/order/change',function(change){
	 change.init();
    });
</script>
</body>
</html>