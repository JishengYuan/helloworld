<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ include file="/common/global.jsp" %>
 <html>
  <head>
    <title>商务采购</title>

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
  

	<div class="portlet-body form">
			   	<form  id="_sino_eoss_cuotomer_addform" class='form-horizontal' method="post" action="">
			    <input type="hidden" id="_eoss_customer_id" value="${model.id}" name="id">
    <div class="handprocess_order" id="SheetDiv">
	<br>
	<center>
 		<table width="650" cellspacing="0" cellpadding="0" bordercolor="#000000" border="0">
			<tbody>
				<tr>
					<td height="25"></td>
				</tr>
				<tr>
					<td align="center" style="font-family: 黑体; font-size: 18pt;">
						<u> 付款单  </u>
					</td>
				</tr>
				<tr>
					<td>
						<table>
							<tbody>
								<tr>
									<td>
										<table width="650" cellspacing="0" cellpadding="0" bordercolor="#000000" border="0">
											<tbody>
												<tr>
													<td width="60"></td>
														<td width="100" align="left">
															<b>附件</b>
															<b>张</b>
														</td>
													<td width="250">
														<b>${model.planTime}</b>
													</td>
													<td width="100" align="right">
														<b>第</b>
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
																						<b>      即</b>
																						<b>付</b>
																					</td>
																					<td> ${model.businessOrder.supplierInfoModel.supplierName }(开户行：${model.businessOrder.supplierInfoModel.bankName } 帐号：${model.businessOrder.supplierInfoModel.bankAccount }):${model.coursesType}： ￥${model.amount}； </td>
																				</tr>
																				<tr>
																					<td width="500" height="1" bgcolor="#000000"> </td>
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
																									<td rowspan="2">              </td>
																									<td>  </td>
																									<td rowspan="2">
																										<b>款    </b>
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
																						<b>计人民币:</b>
																							<u>${capitalMoney }</u>
																					</td>
																					<td>
																						<b>￥</b>
																						<u>${model.amount} </u>
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
																						<td width="230">
																							<b>领款人:</b>
																							<u>        </u>
																						</td>
																						<td>
																							<table width="390" cellspacing="0" cellpadding="0" bordercolor="#000000" border="1">
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
											<td>-----------------------------------------------------------------------------------------</td>
										</tr>
						<tr>
							<td><OBJECT id=WebBrowser
									classid=CLSID:8856F961-340A-11D0-A96B-00C04FD705A2 height=0
									width=0></OBJECT>
								<div class="onlyShow">
									<input name="button" type=button id="_print_button"; value="开始打印">
								</div>
							</td>
						</tr>
						
					</tbody>
				</table>
				<br>
										
		   
			<h3>付款明细</h3>
					<table width="650" cellspacing="0" cellpadding="0" bordercolor="#000000" border="0" id="_payment_detail">
				<thead>
			<tr>
							<td width:20%;">科目类型</td>
							<td width:20%;">单据张数</td>
							<td width:20%;">付款金额</td>
							<td width:20%;">订单编号</td>
							<td width:20%;">用途</td>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td ><div id="coursesType"></div></td>
				           		<input type="hidden" name="coursesType" id="_coursesTypeId" value="${model.coursesType}"/>
							<td >${model.number }</td>
							<td >${model.amount }</td>
							<td >${model.businessOrder.orderCode}</td>
							<td >${model.remark }</td>
						</tr>
					</tbody>
				</table>
				
			<br>
			<br>
	</form>
    </div>
  </div>
<script type="text/javascript">
seajs.use('js/page/business/payment/printPayment', function(printPayment){
	printPayment.init();
});    
</script>

</body>
</html>