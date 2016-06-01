<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ include file="/common/global.jsp" %>
 <html>
  <head>
    <title>商务采购</title>
<meta name="save" content="history">
<META NAME="Generator" CONTENT="EditPlus">
<META NAME="Author" CONTENT="">
<META NAME="Keywords" CONTENT="">
<META NAME="Description" CONTENT="">
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
TD {
	FONT-FAMILY: "宋体";
	FONT-SIZE: 10.5pt
}

input, textarea, .uneditable-input {
    width: 80px;
}

.handprocess_order .li_form label.editableLabel {
    color: #000000;
}
@media   print{.onlyShow{display:none}}  		
</style>

	<script src="${ctx}/js/plugins/seajs/sea.js" id="seajsnode" type="text/javascript"></script>
	<script src="${ctx}/js/plugins/seajs/config.js" type="text/javascript"></script>
		<script src="${ctx}/js/plugins/jquery/jquery-1.9.js" type="text/javascript"></script>
		<script type="text/javascript" src="${ctx}/js/plugins/qrcode/jquery.qrcode.js"></script>
<script type="text/javascript" src="${ctx}/js/plugins/qrcode/qrcode.js"></script>
<script type="text/javascript">
$(function(){
	var str =${type}+","+${proInstId}+","+${taskId}+",#";
	//$('#code').qrcode(str);
	$("#code").qrcode({
		render: "canvas",
		width: 80,
		height:80,
		text: str
	});
	
	
})
</script>
  </head>
  <body>
  

	<div class="portlet-body form">
			   	<form  id="_sino_eoss_cuotomer_addform" class='form-horizontal' method="post" action="">
			    <input type="hidden" id="_eoss_customer_id" value="${model.id}" name="id">
    <div class="handprocess_order" id="SheetDiv">
	<br>
	<center>
 		<table width="620" cellspacing="0" cellpadding="0" bordercolor="#000000" border="0">
			<tbody>
				<tr>
					<td height="25"></td>
				</tr>
				<tr>
					<td align="center" style="font-family: 黑体; font-size: 18pt;">
						<u>&nbsp;&nbsp;支出凭单&nbsp;&nbsp;</u>
					</td>
				</tr>
				<tr>
					<td>
						<table>
							<tbody>
								<tr>
									<td>
										<table width="610" cellspacing="0" cellpadding="0" bordercolor="#000000" border="0">
											<tbody>
												<tr>
														<td width="266" align="left">
															<b>附件&nbsp;&nbsp;&nbsp;&nbsp;</b>
															<b>张</b>
														</td>
													<td width="165">
														<b><fmt:formatDate value="${model.createTime}" type="both" pattern="yyyy"/><B>年</B><fmt:formatDate value="${model.createTime }" type="both" pattern="MM"/>&nbsp;<B>月</B><fmt:formatDate value="${model.createTime }" type="both" pattern="dd"/>&nbsp;<B>日</B></b>
													</td>
													<td width="100" align="right">
														<div style="position:absolute; top:1px;right:34px;margin-top:21px;"  id="code"></div>
													</td>
												</tr>
											</tbody>
										</table>
									</td>
								</tr>
								<tr>
									<td>
										<table width="750" cellspacing="0" cellpadding="0" bordercolor="#000000" border="1">
											<tbody>
												<tr>
													<td>
														<table border="0">
															<tbody>
																<tr>
																	<td height="20">  </td>
																</tr>
																<tr>
																	<td>
																		<table>
																			<tbody>
																				<tr>
																					<td rowspan="2">
																						<b>&nbsp;&nbsp;即</b>
																						<b>&nbsp;付</b>
																					</td>
																					<td> ${model.supplierName.supplierName }(开户行：${model.supplierName.bankName } 帐号：${model.supplierName.bankAccount }):<span id='coursesType'></span>：<c:if test="${model.currency=='usd' }">$</c:if><c:if test="${model.currency=='cny' }">￥</c:if> ${model.payAmonut}； </td>
																					<input type="hidden"id="_coursesTypeId" name="coursesType" value="${model.coursesType}"/>
																				</tr>
																				<tr>
																					<td width="600" height="1" bgcolor="#000000"> </td>
																				</tr>
																			</tbody>
																		</table>
																	</td>
																</tr>
													            <tr>
																	<td height="37">
																		<table>
																			<tbody>
																				<tr>
																					<td>
																						<table>
																							<tbody>
																								<tr>
																									<td rowspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
																									<td></td>
																									<td rowspan="2">
																										<b>款  &nbsp;</b>
																									</td>
																								</tr>
																								<tr>
																									<td width="280" height="1" bgcolor="#000000"> </td>
																								</tr>
																							</tbody>
																						</table>
																					</td>
																					<td rowspan="2">
																						<table cellspacing="0" cellpadding="1" bordercolor="#000000" border="1">
																							<tbody>
																								<tr>
																									<td width="280" height="30">
																										<b>对方科目编号</b>
																									</td>
																								</tr>
																							</tbody>
																						</table>
																					</td>
																				</tr>
																			</tbody>
																		</table>
																	</td>
																</tr>
														 		<tr>
																	<td height="37">
																		<table>
																			<tbody>
																				<tr>
																					<td width="440">
																					<c:if test="${model.currency!='usd' }">
																						<b>&nbsp;&nbsp;计人民币:</b>
																					</c:if>
																					<c:if test="${model.currency=='usd' }">
																						<b>&nbsp;&nbsp;&nbsp;计美金:</b>
																					</c:if>
																							<u>${capitalMoney }</u>
																					</td>
																					<td>	
																						<c:if test="${model.currency!='usd' }">
																							<u><fmt:formatNumber value="${model.payAmonut}" type="currency" currencySymbol="￥"/> </u>
																						</c:if>
																						<c:if test="${model.currency=='usd' }">
																							<u><fmt:formatNumber value="${model.payAmonut}" type="currency" currencySymbol="$"/> </u>
																						</c:if>
																						
																					</td>
																				</tr>
																			</tbody>
																		</table>
																	</td>
																</tr>
																<tr>
																	<td>
																		<table>
																			<tbody>
																					<tr>
																						<td width="250">
																							<b>&nbsp;&nbsp;领款人:</b><u>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</u>
																						</td>
																						<td>
																							<table width="440" cellspacing="0" cellpadding="0" bordercolor="#000000" border="1">
																								<tbody>
																									<tr>
																										<td width="20" height="80" align="center">
																											<b>主</b>
																												<br>
																											<b>管</b>
																										</td>
																											<td width="110"> </td>
																												<td width="20" align="center">
																													<b>财</b>
																													<br>
																													<b>务</b>
																													<br>
																													<b>主</b>
																													<br>
																													<b>管</b>
																												</td>
																											<td width="110"> </td>
																												<td width="20" align="center">
																													<b>出</b>
																													<br>
																													<b>纳</b>
																												</td>
																											<td width="110"> </td>
																										</tr>
																									</tbody>
																								</table>
																							</td>
																						</tr>
																					</tbody>
																				</table>
																			</td>
																		</tr>
																	</tbody>
																</table>
															</td>
														</tr>
													</tbody>
												</table>
											</td>
										</tr>
										<tr>
											<td>
												<table width="620" cellspacing="0" cellpadding="5" bordercolor="#ffffff" border="0">
													<tbody>
														<tr>
															<td width="300">
																<b>审批人：</b>
																${approver }
															</td>
														</tr>
													</tbody>
												</table>
											</td>
										</tr>
										<tr>
											<td height="25"> </td>
										</tr>
										<tr>
											<td>----------------------------------------------------------------------------------------------------------</td>
										</tr>
						<tr>
							<td><OBJECT id=WebBrowser
									classid=CLSID:8856F961-340A-11D0-A96B-00C04FD705A2 height=0
									width=0></OBJECT>
								<div class="onlyShow">
									<input name="button" type=button id="_print_button"; value="开始打印"/>
								</div>
							</td>
						</tr>
						
					</tbody>
				</table>
				<br>
										
		   
			<h3>付款明细</h3>
					<table width="750" border=1 cellSpacing=0 cellPadding=0  id="_payment_detail">
				<thead>
						<tr>
							<td align="center" >科目类型</td>
							<td align="center">付款金额</td>
							<td align="center">订单编号</td>
							<td align="center">用途</td>
						</tr>
					</thead>
					<tbody>
					<c:forEach var="plan" items="${plan}" varStatus="status">
						<tr>
							<td align="center" width="5%" id="type${status.index }">${model.coursesType}</td>
							<td align="center" width="5%" >${plan.amount }</td>
							<td align="center" width="5%" >${plan.orderCode}</td>
							<td align="center"width="25%" >${plan.remark }</td>
						</tr>
					</tbody>
					</c:forEach>
					</table>
					<br>
					<div style="width:750;"><b>备注：</b>${model.remark}</div>
				</table>
			<br>
			<br>
			
				
	</form>
    </div>
  </div>
<script type="text/javascript">
seajs.use('js/page/business/payOrder/printPay', function(printPay){
	printPay.init();
});    
</script>

</body>
</html>