<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/global.jsp"%>

<html>
	<head>
		<title>选择发票报销订单</title>
		<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
		<link rel="stylesheet" href="${ctx}/skin/default/room.css" type="text/css" />
		<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formSelect.css" type="text/css"></link>
		<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formSearchTip.css" type="text/css" ></link>
		<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formstyle.css" type="text/css"></link>
      <style type="text/css">
      .bt5left {
		    margin-left: 30px;
		}
      </style>

</head>

<body >       
			<table width="100%" border="0" >
				<tr>
					<td align="right">供应商类型:</td>
					<td align="left"><div id='_supplierType'></div> </td>
					<td align="right">供应商名称:</td>
					<td align="left">	<input type="text" id='supplierInfoSelect' name="supplierId"  class="row-input"  value="" />
													<input type="hidden" id='_supplierInfoSelect'  class="row-input"  value="" />
				</td>
				</tr>
			</table>
			<br>
				<table id="taskTable" width="100%" cellpadding="0" cellspacing="0" border="0" class="table table-striped table-bordered table-hover">
					<thead>
						<tr>
							<th style="width:10%;">选择</th>
							<th style="width:30%;">订单编号</th>
							<th style="width:40%;">订单名称</th>
							<th style="width:20%;">订单金额</th>
						</tr>
					</thead>
					<tbody>			
					</tbody>
  				</table>
	<!--按钮组-->
    		<input type="hidden" id='supplierId' class="row-input"  value="" />
 <script language="javascript">
    seajs.use('js/page/business/reimbursementApply/selectOrderView',function(selectOrder){
    	selectOrder.init();
    });
</script>
</body>
</html>