<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ include file="/common/global.jsp" %>
 <html>
  <head>
    <title>商务采购</title>
    	<style>
    	</style>
    <script src="${ctx}/js/plugins/seajs/sea.js" id="seajsnode" type="text/javascript"></script>
	<script src="${ctx}/js/plugins/seajs/config.js" type="text/javascript"></script>
  </head>
  <body>
  <div class="portlet-body form">
			   	<form  id="_sino_eoss_cuotomer_addform" class='form-horizontal' method="post" action="">
			    <input type="hidden" id="_eoss_customer_id" value="${model.id}" name="id">
    <div class="handprocess_order" id="SheetDiv">
      <table cellspacing="0" cellpadding="0" width="952" border="0" align="center">
			<tbody>
				<tr height="35">
					<td colspan="6"> </td>
				</tr>
			</tbody>
		</table>

	<table cellspacing="0" cellpadding="0" width="952" border="1" align="center">
		<tbody>
			<tr height="40">
				<td class="xl35" width="172" height="35" colspan="2">
				版本：
					<font class="font8">QF-CD-14-V2.0</font>
				</td>
				<td width="480" colspan="2">
					<div class="style1" align="center"> <b>办公用品采购通知单</b></div>
				</td>
				<td width="300" colspan="2">编号：${model.purchasCode}</td>
			</tr>
			<tr height="41">
				<td height="41" colspan="2">申请人：${model.creator }</td>
				<td colspan="2">申请部门：${orgName }</td>
				<td colspan="2">期望到货日期：${model.expectedDeliveryTime }</td>
			</tr>
			<tr height="29">
				<td height="29" colspan="6">用途：${model.remark}</td>
			</tr>
			<tr height="27">
				<td class="xl46" height="27" colspan="6">
					<div class="style2" align="center">
						<strong>需 求 清 单</strong>
					</div>
				</td>
			</tr>
			<tr height="19">
				<td width="10%" height="19" align="center">序号</td>
				<td width="15%" align="center">产品类型</td>
				<td width="15%" align="center">产品厂商</td>
				<td width="15%" align="center">产品型号</td>
				<td width="15%" align="center">数量</td>
				<td width="25%" align="center">备注</td>
			</tr>
			 <c:forEach var="product" items="${model.interPurchasProduct}" varStatus="status">
					<tr>
						<td align="center">${ status.index + 1}</td>
						<td align="center">${product.productTypeName}</td>
						<td align="center">${product.productPartnerName}</td>
						<td align="center">${product.productName}</td>
						<td align="center">${product.quantity }</td>
						<td align="center"></td>
					</tr>
			</c:forEach>
			<tr height="54">
				<td height="54" colspan="6">部门经理意见：</td>
			</tr>
			<tr height="46">
				<td height="46" colspan="3">总经理意见：</td>
				<td>日期：</td>
				<td colspan="2"> </td>
			</tr>
		</tbody>
	</table>
<OBJECT id=WebBrowser classid=CLSID:8856F961-340A-11D0-A96B-00C04FD705A2 height=0 width=0></OBJECT>
	<div class="onlyShow " style="margin:0 auto;;width: 76px;">
		<input name="button" type=button id="_print_button"; value="开始打印">
	</div>
  
    
<script type="text/javascript">
seajs.use('js/page/business/payment/printPayment', function(printPayment){
	printPayment.init();
});    
</script>

</body>
</html>