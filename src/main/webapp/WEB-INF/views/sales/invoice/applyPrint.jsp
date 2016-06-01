<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ include file="/common/global.jsp" %>
 <html>
  <head>
    <title>发票申请详情</title>

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
input, textarea, .uneditable-input {
    width: 600px;
}
@media   print{.onlyShow{display:none}}  		
	</style>
	<script src="${ctx}/js/plugins/seajs/sea.js" id="seajsnode" type="text/javascript"></script>
	<script src="${ctx}/js/plugins/seajs/config.js" type="text/javascript"></script>
  </head>
  <body>
  				<!--隐藏的客户ID，用于回显-->
				<input type='hidden' name='customerId' id='_eoss_sales_customerId'  value="${contract.customerId}"/>
				<input type='hidden' name='invoiceTypeId' id='_eoss_sales_invoiceTypeId'  value="${model.invoiceType}"/>
				
  <center>
	<table width="620" cellspacing="0" cellpadding="0" bordercolor="#000000" border="0">
		<tbody>
			<tr>
				<td align="center">
					<u>
						<font size="5" face="黑体" color="#000000">  发票申请单  </font>
					</u>
				</td>
			</tr>
			<tr>
				<td>
					<table>
						<tbody>
							<tr>
								<td>
									<table width="620" cellspacing="0" cellpadding="0" bordercolor="#000000" border="0">
										<tbody>
											<tr>
												<td width="270">
													<b>申请人：${contract.creatorName}</b>
												</td>
												<td width="116">
													${model.createTime}
												</td>
												<td width="240" align="right">
													<b>第${keyCode}</b>
													<b>号</b>
												</td>
											</tr>
										</tbody>
									</table>
								</td>
							</tr>
							<tr>
								<td>
									<table width="620" cellspacing="0" cellpadding="0" bordercolor="#000000" border="1">
										<tbody>
											<tr>
												<td width="120" height="75">
													<br>
													<b>销售合同信息 </b>
													<br>
												</td>
												<td width="490" colspan="3">
													<table border="0">
														<tbody>
															<tr>
																<td  width="80px">客户名称：</td>
																<td>
																	<span id="_customerInfoName"></span>
																</td>
															</tr>
															<tr>
																<td>合同编号：</td>
																<td>${contract.contractCode}</td>
															</tr>
															<tr>
																<td>合同名称：</td>
																<td>${contract.contractName}</td>
															</tr>
															<tr>
																<td>合同金额：</td>
																<td>
																	<fmt:formatNumber value="${contract.contractAmount }" type="currency" currencySymbol="￥"/>
																</td>
															</tr>
														
														</tbody>
													</table>
												</td>
											</tr>
											<tr>
												<td width="130" height="75">
													<br>
													<b>已开票信息 </b>
													<br>
												</td>
													<td width="490" colspan="3">   
													<fmt:formatNumber value="${conAmount }" type="currency" currencySymbol="￥"/>
													</td>
												</tr>
												<tr>
													<td width="130" height="75">
														<br>
														<b>本次开票金额 </b>
														<br>
													</td>
													<td width="490" colspan="3">
														<table>
															<tbody>
																<tr>
																	<td width="350">
																		<br>
																			<span id="invoiceType"></span> :
																			<fmt:formatNumber value="${model.invoiceAmount} " type="currency" currencySymbol="￥"/>
																		<br>
																	</td>
																		<td width="140" valign="bottom"> </td>
																	</tr>
																</tbody>
															</table>
														</td>
													</tr>
													<tr>
														<td width="180" height="75">
															<b>申 请 人 签 章</b>
														</td>
														<td width="180">  </td>
													</tr>
												</tbody>
											</table>
										</td>
									</tr>	
									<tr>
										<td>
											<table width="620" cellspacing="0" cellpadding="5" bordercolor="#000000" border="0">
												<tbody>
													<tr>
														<td width="300">
															<b>审批人：</b>
															${approver }
														</td>
														<td width="120">
															<b>审核：</b>
														</td>
														<td width="175">
															<b>证明或验收：</b>
														</td>
														<td width="140">
															<b>经手人：</b>
														</td>
													</tr>
												</tbody>
											</table>
										</td>
									</tr>
								</tbody>
							</table>
							<tr>
								<td><hr style="height:1px;border:none;border-top:1px dashed;" /></td>
							</tr>
							<br>
							<tr>
								<td><OBJECT id=WebBrowser
										classid=CLSID:8856F961-340A-11D0-A96B-00C04FD705A2 height=0
										width=0></OBJECT>
									<div class="onlyShow">
										<input name="button" type=button id="_print_button"; value="开始打印">
									</div>
								</td>
							</tr>
						</td>
					</tr>
				</tbody>
			</table>
		</center>
<br>
<br>
<br>
<br>


<script type="text/javascript">
seajs.use('js/page/sales/invoice/applyPrint', function(applyPrint){
	applyPrint.init();
});    
</script>
</body>
</html>














