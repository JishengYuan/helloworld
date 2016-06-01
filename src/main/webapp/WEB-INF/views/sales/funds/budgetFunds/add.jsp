<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ include file="/common/global.jsp" %>
 <%@ page import="com.sinobridge.systemmanage.util.Global"%> 
 <%@ page import="com.sinobridge.systemmanage.vo.SystemUser"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%
  	SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
  %>
 <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formSelect.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formTree.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/user-selection.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formSearchTip.css" type="text/css" ></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formstyle.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/dialog.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/tip.css" type="text/css" ></link>

    <link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formSelect.css" type="text/css"></link>
    <link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formstyle.css" type="text/css"></link>
	
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/romerSelect.css" type="text/css"></link>
<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/rsearch-tips.css" type="text/css"></link>
    <link rel="stylesheet" href="${ctx}/skin/default/editgrid/editgrid.css" type="text/css" />
<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/processList.css" type="text/css"></link>
  <style>
 
  </style>
</head>
<body>
<br/>
 
	  	   <div class="ultrapower-table-box" style="border-bottom: 2px solid #1f9dd7;height: 35px;">
	    		<span class="title" style="font-size:14px; font-weight: normal;font-family:宋体;border-bottom: 2px solid #FFFFFF;color: #333;">合同管理-->销售账目-->添加预算</span>
		   
		    <div  class="pull-right"> 
				     <button id="back" type="button" class="btn btn-default btn-lg">
					  <span class="icon-repeat"></span>
					  返回
					</button> 
		  	</div>
	      </div>
	      
	    <form name="addForm"  id ="addForm" class='form-horizontal' action="">
	     <input type="hidden" id="userId" value="${userName }" name="userId">
	   	 <input type="hidden" id="userName" value="<%=systemUser.getStaffName() %>" name="userName">
		  <table style="margin-top:15px;width: 900px;border: 2px solid rgb(243, 243, 243)" id="Tbl" > 
		   <tr style="height:60px">
		    	<td class="_sino_td_one _sino_td_top" style="text-align: center;">合同名称：</td>
		    	<td class="_sino_td_two _sino_td_top">	<div name='contractName' id='contractName' class="InpText f12 required" ><input type='text' name='contractName' id='contractName'  /></div> </td>
	    		<td class="_sino_td_one _sino_td_top" style="text-align: center;"> 收款人：</td>
			    <td class="_sino_td_two _sino_td_top">	 
				    <div id="_eoss_customer" style="float: left;">
						<div id='_customerManager' class="InpText f12 required">
						</div>
					</div>
				</td>
			</tr>
			<tr style="height:60px">
		    	<td class="_sino_td_one _sino_td_top" style="text-align: center;"> 预收回款：</td>
		    	<td class="_sino_td_two _sino_td_top"><input type='text' name='budgetReceive' id='budgetReceive'  /></td>
				<td class="_sino_td_one _sino_td_top" style="text-align: center;"> 预开发票：</td>
		    	<td class="_sino_td_two _sino_td_top"><input type='text' name='budgetInvoice' id='budgetInvoice'  /></td>
			</tr>
			<tr style="height:60px">
					<td class="_sino_td_one _sino_td_top" style="text-align: center;">预算周期：</td>
					<td class="_sino_td_two _sino_td_top">
					<div class="input-append date">
						 <input data-format="yyyy-MM-dd"  name="budgetDate" id="budgetDate" type="text"  style="width:177px"></input>
						    <span class="add-on">
						      <i data-time-icon="icon-time" data-date-icon="icon-calendar">
						      </i>
							</span>
					  </div> 
			 		</td>
			 		<td class="_sino_td_one _sino_td_top" style="text-align: center;">创建时间：</td>
					<td class="_sino_td_two _sino_td_top">
					<div class="input-append date">
						 <input data-format="yyyy-MM-dd"  name="createDate" id="createDate" type="text"  style="width:177px"></input>
						    <span class="add-on">
						      <i data-time-icon="icon-time" data-date-icon="icon-calendar">
						      </i>
							</span>
					  </div> 
			 		</td>
			</tr>
			<tr style="height:60px">
					<td class="_sino_td_one _sino_td_top" style="text-align: center;">合同编码：</td>
			    	<td class="_sino_td_two _sino_td_top"><input type='text' name='contractCode' id='contractCode' disabled="true"　readOnly="true"/></td>
					<td class="_sino_td_one _sino_td_top" style="text-align: center;">备注：</td>
			    	<td class="_sino_td_two _sino_td_top"><input type='text' name='remark' id='remark' /></td>
			</tr>
	 </table>
</form>
					<!--按钮组-->
				    	<div style="text-align:right;padding:0px;margin-top:20px;width:900px">
					    	<div style="margin-left:800px;" class="handprocess_btngroup" id="bottom_button">
						  		<a id="add" role="button" data-toggle="modal" class="btn btn-primary">确定</a>
							</div>
					    </div>
 <script language="javascript">
	seajs.use('js/page/sales/funds/budgetFunds/add', function(add) {
		add.init();
	});
</script>
</body>
</html>