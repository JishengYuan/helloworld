<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ include file="/common/global.jsp" %>
<%@ page import="com.sinobridge.systemmanage.util.Global"%> 
<%@ page import="com.sinobridge.systemmanage.vo.SystemUser"%>
 <%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt_rt"%>
  
<head>
    <title>借货单详情 </title>
    <script src="${ctx}/js/plugins/seajs/sea.js" id="seajsnode" type="text/javascript"></script>
	<script src="${ctx}/js/plugins/seajs/config.js" type="text/javascript"></script>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/tip.css" type="text/css" ></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formSearchTip.css" type="text/css" ></link>
	
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/sinobridge/pink/sino_form_grid.css" type="text/css" />

	 <link rel="stylesheet" href="${ctx}/skin/default/index.css" type="text/css" />
  	 <%@ include file="/common/include-base-boostrap-styles.jsp" %>
  	 <link href="${ctx}/skin/default/sales/sale.css" rel="stylesheet"/>
<style type="text/css">
	.div_style{
		margin:0 auto;
		width:1002px;
	}

	
</style>

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
			<i class="icon-reorder"></i>&nbsp;&nbsp;&nbsp;借货单编号：${outModel.serialNum }
		</div>
	</div>
</div>
	
	
<div class="div_style" style="float:top; margin-top:30px;"> 
	<form  id="_sino_eoss_inventory_addform" class='form-horizontal' method="post" action="">
	<input type="hidden"  id="tableData" name="tableData" >
	<input type="hidden" id="customerManage" name="customerManage"/>
   	<input type="hidden" id="engineers" name="engineers"/>
   	<input type="hidden" name="projectName" id="projectName" value="">
	
	<div class="handprocess_order grey_btn33" id="SheetDiv" style="padding-left: 60px;">
	<div style="width: 850px;">
		<h3>基本信息</h3>
		<ul class="clearfix" >
			<li id="field_userName" class="li_form" style="width: 400px;">
				<label for="projecctName"  class="editableLabel" style="padding-top:3px;">借货时间：</label>
				<fmt_rt:formatDate pattern="yyyy-MM-dd" value="${outModel.borrowTime }" />	
			</li>
			<li id="field_userName" class="li_form" style="width: 400px;">
				<label for="projecctName"  class="editableLabel" style="padding-top:3px;">归还时间：</label>
				<fmt_rt:formatDate pattern="yyyy-MM-dd" value="${outModel.dueTime }" />	
				
			</li>
		</ul>
		<ul class="clearfix">
			<li class="li_form" style="width: 400px;">
			    <label class="editableLabel" style="float:left;padding-top:4px;">项目经理：</label>
			     ${outModel.customerManageName }
			</li>
			<li class="li_form" style="width: 400px;">
			    <label class="editableLabel" style="float:left;padding-top:4px;">驻点工程师：</label>
			    ${enginners }
			</li>
		</ul>
		<ul class="clearfix" >
			<li id="field_userName" class="li_form" style="width: 800px;">
				<label for="userName" class="editableLabel" style="padding-top:3px;">项目名称：</label>
				${outModel.projectName }
			</li>
		</ul>
		<ul class="clearfix" >
			<li id="field_userName" class="li_form" style="width: 800px;">
				<label for="projecctName"  class="editableLabel" style="padding-top:3px;">借货说明：</label>
				${outModel.remark }
			</li>	
		</ul>
		
		<h3>用户信息</h3>
		<ul class="clearfix">
			<li id="field_userName" class="li_form" style="width: 400px;">
				<label for="userName" class="editableLabel" style="padding-top:3px;">用户单位：</label>
				${outModel.userDept }
			</li>
			<li id="field_userName" class="li_form" style="width: 400px;">
				<label for="userName" class="editableLabel" style="padding-top:3px;">用户联系人：</label>
				${outModel.userContact }
			</li>
		</ul>
		<ul class="clearfix">
			<li id="field_userName" class="li_form" style="width: 400px;">
				<label for="userName" class="editableLabel" style="padding-top:3px;">联系电话：</label>
				${outModel.phone }
			</li>
			<li id="field_userName" class="li_form" style="width: 400px;">
				<label for="userName" class="editableLabel" style="padding-top:3px;">邮编：</label>
				${outModel.zipCode }
			</li>
		</ul>
		<ul class="clearfix">
			<li id="field_userName" class="li_form" style="width: 400px;">
				<label for="userName" class="editableLabel" style="padding-top:3px;">通讯地址：</label>
				${outModel.address }
			</li>
		</ul>
	
		<h3>设备清单</h3>
		
					<table border="0" style="width: 100%" class="sino_table_body" id="_fee_travel">
				    <thead>
			             <tr>
<!-- 							<td class="sino_table_label" style="background:none repeat scroll 0 0 #F3F3F3;width:20%;">产品编号</td> -->
<!-- 							<td class="sino_table_label" style="background:none repeat scroll 0 0 #F3F3F3;width:20%;">数量</td> -->
<!-- 							<td class="sino_table_label" style="background:none repeat scroll 0 0 #F3F3F3;width:20%;">成本</td> -->
<!-- 							<td class="sino_table_label" style="background:none repeat scroll 0 0 #F3F3F3;width:20%;">状态</td> -->
							<th width="20%">厂商</th>
							<th width="20%">型号</th>
							<th width="20%">序列号</th>
							<th width="20%">数量</th>
							<th width="20%">状态</th>
<!-- 							<c:if test="${approveState==2 }"> --!>
<!-- 								<th width="25%">序列号</th> -->
<!-- 							</c:if> -->
										
						</tr>
					</thead>
					<tbody>
					<c:if test="${not empty productList}">
					<c:forEach var="logs" items="${productList}" varStatus="status">
						<tr>
							<td class="sino_table_label">${logs.Partner }</td>
							<td class="sino_table_label">${logs.ProductNo }</td>
							<td class="sino_table_label">${logs.SeriesNo }</td>
							<td class="sino_table_label">1</td>
							<td class="sino_table_label">
								<c:if test="${logs.State==0 }">在库</c:if>
								<c:if test="${logs.State==1 }">待审批</c:if>
								<c:if test="${logs.State==2 }">已借出</c:if>
								<c:if test="${logs.State==3 }">待归还</c:if>
								<c:if test="${logs.State==4 }">已归还</c:if>
							</td>
						</tr>
					 </c:forEach>
					 </c:if>
					 </tbody>
				</table>
		</div>
</form>
</div>

	<!--按钮组-->
    		<div id="bottom_button" class="handprocess_btngroup">
        		<input id="no_back" type="button" value="关闭" class="btn">
    		</div>

 <script language="javascript">
    seajs.use(['js/page/business/outOrInInventory/outStorageDetail'],function(outStorageDetail){
    	outStorageDetail.init();
    });
</script>
</body>
</html>