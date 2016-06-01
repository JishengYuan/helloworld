<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/global.jsp"%>
<HTML>
<HEAD>
<TITLE>商务管理报销单</title>
<meta name="save" content="history">
<META NAME="Generator" CONTENT="EditPlus">
<META NAME="Author" CONTENT="">
<META NAME="Keywords" CONTENT="">
<META NAME="Description" CONTENT="">
<script src="${ctx}/js/plugins/seajs/sea.js" id="seajsnode" type="text/javascript"></script>
<script src="${ctx}/js/plugins/seajs/config.js" type="text/javascript"></script>
<script src="${ctx}/js/plugins/jquery/jquery-1.9.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/js/plugins/qrcode/jquery.qrcode.js"></script>
<script type="text/javascript" src="${ctx}/js/plugins/qrcode/qrcode.js"></script>
<script type="text/javascript">
$(function(){
	var str =4+","+${proInstId}+","+${taskId}+",#";
	//$('#code').qrcode(str);
	$("#code").qrcode({
		render: "canvas",
		width: 80,
		height:80,
		text: str
	});
	
	
})

</script>
<style type="text/css">
BODY {
	FONT-FAMILY: "宋体", "宋体";
	FONT-SIZE: 10.5pt;
	LINE-HEIGHT: 12pt
}

P {
	FONT-FAMILY: "宋体", "宋体";
	FONT-SIZE: 10.5pt;
	LINE-HEIGHT: 12pt
}

BR {
	FONT-FAMILY: "宋体", "宋体";
	FONT-SIZE: 10.5pt;
	LINE-HEIGHT: 12pt
}

A:link {
	COLOR: #2222aa;
	TEXT-DECORATION: none
}

A:visited {
	TEXT-DECORATION: none
}

A:hover {
	COLOR: #cc0033;
	TEXT-DECORATION: underline
}

TD {
	FONT-FAMILY: "宋体";
	FONT-SIZE: 10.5pt
}

.unnamed1 {
	LINE-HEIGHT: 12pt
}
</style>

</HEAD>

<BODY topmargin="0">

	<CENTER>
		<TABLE border="0" width="660" cellpadding="0" cellspacing="0"
			bordercolor="#000000">
			<TR>
				<TD height="70"></TD>
			</TR>
			<TR>
				<TD align="center"><U><FONT face="黑体" color="#000000"
						size="5">&nbsp;&nbsp;报销单&nbsp;&nbsp;</FONT></U></TD>
			</TR>
			<TR>
				<TD>

					<TABLE>
						<TR>
							<TD>

								<TABLE border="0" width="620" cellpadding="0" cellspacing="0"
									bordercolor="#000000">
									<TR>
										<TD width="240"><B>单位：</B>${org}-${model.reimbursementUser }</TD>
										<TD width="120"><fmt:formatDate value="${model.createTime }" type="both" pattern="yyyy"/><B>年</B><fmt:formatDate value="${model.createTime }" type="both" pattern="MM"/>&nbsp;<B>月</B><fmt:formatDate value="${model.createTime }" type="both" pattern="dd"/>&nbsp;<B>日</B></TD>
										<TD width="100" align="right"><div style="position:absolute; top:5px;right:40px;margin-top:33px;" id="code"></div></TD>
									</TR>
								</TABLE>

							</TD>
						</TR>

						<TR>
							<TD>

								<TABLE border="1" width="750" cellpadding="0" cellspacing="0"
									bordercolor="#000000">
									<TR>
										<TD height="75" width="130">&nbsp;<BR>&nbsp;&nbsp;<B>摘&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;要</B>&nbsp;&nbsp;<BR>&nbsp;
										</TD>
										<TD width="490" colspan="3">&nbsp;&nbsp;<span style="font-size:16px;">${suInfo.supplierName }（开户行：${suInfo.bankName }&nbsp;&nbsp;&nbsp;账号：${suInfo.bankAccount }） <span id="coursesType"></span></span></TD>
										<input type="hidden" id="_coursesTypeId" name="coursesType" value="${type}"/>
									</TR>
									<TR>
										<TD height="75" width="130">&nbsp;<BR>&nbsp;&nbsp;<B>金&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;额</B>&nbsp;&nbsp;<BR>&nbsp;
										</TD>
										<TD width="490" colspan="3">
											<TABLE>
												<TR>


													<TD width="440">&nbsp;&nbsp;<B>人民币（大写）:</B>&nbsp;<span style="font-size:16px;"><U>${capitalMoney}</U></span></TD>
													<TD><span style="font-size:16px;"><U><fmt:formatNumber value="${model.amount}" type="currency" currencySymbol="￥"/>&nbsp;</U></span></TD>
												</TR>
											</TABLE>
										</TD>
									</TR>
									<TR>
										<TD height="75" width="130">&nbsp;<BR>&nbsp;&nbsp;<B>附&nbsp;单&nbsp;据&nbsp;张&nbsp;数</B><BR>&nbsp;
										</TD>
										<TD width="180" align="center">&nbsp;${num }</TD>
										<TD width="130">&nbsp;&nbsp;<B>领&nbsp;款&nbsp;人&nbsp;签&nbsp;章</B>&nbsp;&nbsp;
										</TD>
										<TD width="180" align="center">&nbsp;</TD>
									</TR>
								</TABLE>

							</TD>
						</TR>

						<TR>
							<TD>

								<TABLE border="0" width="680" cellpadding="5" cellspacing="0"
									bordercolor="#000000">
									<TR>
										<TD width="300"><B>审批人：</B>${approver }</TD>
										<TD width="150"><B>审核：</B>&nbsp;</TD>
										<TD width="200"><B>证明或验收：</B>&nbsp;</TD>
										<TD width="100"><B>经手人：</B>&nbsp;</TD>
									</TR>
								</TABLE>

							</TD>
						</TR>

						<TR>
							<TD height="107"></TD>
						</TR>

						<tr>
							<td>------------------------------------------------------------------------------------------------------------</td>
						</tr>
						<TR>
							<TD><OBJECT id=WebBrowser
									classid=CLSID:8856F961-340A-11D0-A96B-00C04FD705A2 height=0
									width=0></OBJECT>
								<div id=t>
									<input name="button" type=button id="_print_button"; value="开始打印">
								</div></TD>
						</TR>
						<tr>
							<td></td>
						</tr>

					</TABLE>
				</TD>
			</TR>
		</TABLE>
		<br>
		<br>
		<br>
		<br>
		<br>
		<br>
		<TABLE border="0" width="650" cellpadding="5" cellspacing="0"
			bordercolor="#000000">
			<TR>
				<TD>报销明细:</TD>
			</TR>
		</TABLE>
		<TABLE width="750" border=1 cellSpacing=0 cellPadding=0 id='_order_reim'>
			<tr>
				<td align="center">报销类型</td>
				<td align="center">单据张数</td>
				<td align="center">金额</td>
				<td align="center">采购合同号</td>
				<td align="center">客户</td>
				<td align="center">用途</td>
			</tr>

			<c:forEach var="item" items="${plan}" varStatus="status">
				<tr class="normalItem" ">
					<td align="center" width="10%" id="coursesType${status.index }" rowspan="${fn:length(item.contractReimbursements)+1 }"><span style="font-size:16px;">${item.coursesType }</span></td>
					<td align="center" width="10%" rowspan="${fn:length(item.contractReimbursements)+1 }"><span style="font-size:16px;">${item.number }</span></td>
					<td align="center" width="10%" rowspan="${fn:length(item.contractReimbursements)+1 }"><span style="font-size:16px;">￥${item.amount }</span></td>
					<td align="center" width="10%" rowspan="${fn:length(item.contractReimbursements)+1 }"><span style="font-size:16px;">${item.orderCode }</span></td>
					<td>
					<c:forEach var="reimbursement"  items="${item.contractReimbursements }" varStatus="rbmstatus">
					<c:if test="${reimbursement.contractName!=null }">
					<tr>
					<td align="center" width="10%"><span style="font-size:16px;">${reimbursement.custormer}</span></td>
					<td class="sino_table_label" colspan="6"><span style="font-size:16px;">${reimbursement.contractName },${reimbursement.contractCode },
							${reimbursement.creatorName },￥${reimbursement.contractAmount }
							报销发票：￥${reimbursement.rbmAmount }</span></td>
					</tr>
					</c:if>
					<c:if test="${reimbursement.PurchasName!=null }">
					<tr>
					<td align="center" width="10%"><span ></span></td>
					<td class="sino_table_label" colspan="6"><span style="font-size:16px;">${reimbursement.PurchasName },${reimbursement.PurchasCode },
							${reimbursement.Creator }</span>
							</td>
					</tr>
					</c:if>
					</c:forEach>
					</td>
				</tr>
			</c:forEach>

		</table>
	</CENTER>
	<br>
	<br>
	<br>
	<br>
	<script language="javascript">
		seajs.use('js/page/business/reimbursementApply/printReim', function(printReim) {
			printReim.init();
		});
	</script>
</BODY>
</HTML>
