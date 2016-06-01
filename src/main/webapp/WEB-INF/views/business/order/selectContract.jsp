<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/global.jsp"%>

<html>
	<head>
		<title>商务管理</title>
		<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
		<link rel="stylesheet" href="${ctx}/skin/default/room.css" type="text/css" />
		<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formSelect.css" type="text/css"></link>
		<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formTree.css" type="text/css"></link>
		<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/tip.css" type="text/css" ></link>
		<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/user-selection.css" type="text/css"></link>
		<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formSearchTip.css" type="text/css" ></link>
		<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formstyle.css" type="text/css"></link>
		<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/dialog.css" type="text/css"></link>
		<link rel="stylesheet" href="${ctx}/js/plugins/bootstrap/css/bootstrap-datetimepicker.min.css" type="text/css"></link>

</head>

<body>       
			<table width="98%" border="0">
				<tr>
					<td align="right">客户经理:</td>
					<td align="left"><div id='formStaff'></div></td>
					<td align="right">合同编号:</td>
					<td align="left"><input id="_contractCode" name="contractCode" type="text" value="" style="width:150px;"> </td>
					<td align="right">合同名称:</td>
					<td align="left"><input id="_contractName" name="contractName" type="text" value="" style="width:150px;"></td>
				</tr>
				<tr height="40px">
					<td colspan="6" align="right"><input  id="_sino_eoss_order_searchContract" type="button" value="查询" class="btn btn-primary pull-right" /></td>
				</tr>
			</table>
				<table id="taskTable" width="98%" cellpadding="0" cellspacing="0" border="0" class="table table-striped table-bordered table-hover">
					<thead>
						<tr>
							<th>选择</th>
							<th>销售合同编号</th>
							<th>销售合同名称</th>
						</tr>
					</thead>
					<tbody>			
					</tbody>
  				</table>
	<!--按钮组-->
    		
 <script language="javascript">
    seajs.use('js/page/business/order/selectContract',function(selectContract){
    	selectContract.init();
    });
</script>
</body>
</html>