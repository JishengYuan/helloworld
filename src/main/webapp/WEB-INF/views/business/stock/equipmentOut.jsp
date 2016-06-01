<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.sinobridge.systemmanage.util.Global"%> 
<%@ page import="com.sinobridge.systemmanage.vo.SystemUser"%> 
<html>
<script language="javascript">
	seajs.use('js/page/business/feemanage/feeBill/detail', function(update) {
		update.init();
	});
</script>
<head>
	<title></title>
</head>
<body>
	<div>
		<h2>备件借出</h2>
<!-- 		<table align="center" id="taskTable" cellpadding="0" cellspacing="0" border="0" class="table table-striped table-bordered table-hover"> -->
<!-- 			<tr> -->
<!-- 				<td>厂商名称： -->
<!-- 						<select id="" name=""> -->
<!-- 							<option value="">--请选择--</option> -->
<!-- 						</select> -->
<!-- 				</td> -->
<!-- 				<td>产品类型： -->
<!-- 						<select id="" name=""> -->
<!-- 							<option value="">--请选择--</option> -->
<!-- 						</select> -->
<!-- 				</td> -->
<!-- 				<td>入库类型： -->
<!-- 						<select id="" name=""> -->
<!-- 							<option value="">--请选择--</option> -->
<!-- 						</select> -->
<!-- 				</td> -->
<!-- 			</tr> -->
<!-- 			<tr> -->
<!-- 				<td>订单编号：<input type="text" id="" name="" /></td> -->
<!-- 				<td>产品编号：<input type="text" id="" name="" /></td> -->
<!-- 				<td>产品描述：<input type="text" id="" name="" /></td> -->
<!-- 			</tr> -->
<!-- 			<tr> -->
<!-- 				<td colspan="3" ><input type="button" id="_eoss_business_feeBillBack" value="查询" class="grey_btn33 cp" style="height:30px;"></td> -->
<!-- 			</tr> -->
<!-- 		</table> -->
<!-- 		库存产品清单： -->
<!-- 		<table align="center" id="taskTable" cellpadding="0" cellspacing="0" border="0" class="table table-striped table-bordered table-hover"> -->
<!-- 			<tr> -->
<!-- 				<td>序号</td> -->
<!-- 				<td>厂商编号</td> -->
<!-- 				<td>产品类型</td> -->
<!-- 				<td>产品编号</td> -->
<!-- 				<td>产品描述</td> -->
<!-- 				<td>库存数量</td> -->
<!-- 				<td>存放地点</td> -->
<!-- 				<td>备注</td> -->
<!-- 			</tr> -->
<!-- 		</table> -->
	</div>
	<!--按钮组-->
	<div id="bottom_button" class="handprocess_btngroup">
		
	</div>
</body>
</html>