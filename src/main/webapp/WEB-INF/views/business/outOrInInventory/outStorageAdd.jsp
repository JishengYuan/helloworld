<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ include file="/common/global.jsp" %>
<%@ page import="com.sinobridge.systemmanage.util.Global"%> 
<%@ page import="com.sinobridge.systemmanage.vo.SystemUser"%>
  
<head>
  <%
  	SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
   String sessionid =  request.getSession().getId();
   String username = systemUser.getUserName();
   String staffname = systemUser.getStaffName();
  %>
  <script >
      var sessionid='<%=sessionid%>';
      var username = '<%=username%>';
      var staffname = '<%=staffname%>';
  </script>


    <title>出库申请</title>
    <script src="${ctx}/js/plugins/seajs/sea.js" id="seajsnode" type="text/javascript"></script>
	<script src="${ctx}/js/plugins/seajs/config.js" type="text/javascript"></script>
	<link rel="stylesheet" href="${ctx}/js/plugins/bootstrap/css/bootstrap-datetimepicker.min.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formSelect.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formstyle.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formTree.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/tip.css" type="text/css" ></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/user-selection.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formSearchTip.css" type="text/css" ></link>
	
	<link href="${ctx}/skin/default/sales/innerSales.css" rel="stylesheet" type="text/css">
	<link rel="stylesheet" href="${ctx}/skin/default/editgrid/editgrid.css" type="text/css" />
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
			<i class="icon-reorder"></i>&nbsp;&nbsp;&nbsp;出库申请
		</div>
	</div>
</div>
	
	
<div class="div_style" style="float:top; margin-top:30px;"> 
	<form  id="_sino_eoss_inventory_addform" class='form-horizontal' method="post" action="">
	<input type="hidden"  id="tableData" name="tableData" >
	<input type="hidden" id="customerManage" name="customerManage"/>
   	<input type="hidden" id="engineers" name="engineers"/>
   	
   	<input type="hidden" id="hideUserName" name="hideUserName" value="${userName }"/>
 	<input type="hidden" id="hideStaffName" name="hideStaffName" value="${staffName }"/>
	<input type="hidden" id='_tableGridData' name="tableGridData" value="" />
	<div class="handprocess_order grey_btn33" id="SheetDiv" style="padding-left: 60px;">
	<div style="width: 850px;">
		<h3>基本信息</h3>
		<ul class="clearfix" >
			
			<li id="field_userName" class="li_form" style="width: 400px;">
				<label for="userName" class="editableLabel" style="padding-top:3px;">借货编号：</label>
				<input type="text" id="serialNum" name="serialNum" style="width: 210px;" value="${serialNoStr }" readonly="readonly">
				
			</li>
			<li id="field_userName" class="li_form" style="width: 400px;">
				<label for="projectNo" class="editableLabel" style="padding-top:3px;">借货时间：</label>
				<div style="position: relative;" class="input-append date">
					 <input data-format="yyyy-MM-dd" id="borrowTime" name="borrowTime" value="${dateTime }"  type="text"  style="width:180px" placeholder="请填写借货时间" required data-content="请填写借货时间"></input>
					    <span class="add-on">
					      <i data-time-icon="icon-time" data-date-icon="icon-calendar">
					      </i>
						</span>
				  </div>
			</li>
			
		</ul>
		
		<ul class="clearfix">
			<li id="field_userName" class="li_form" style="width: 600px;">
				<label for="projectNo" class="editableLabel" style="padding-top:3px;">归还时间：</label>
				<div style="position: relative;" class="input-append date">
					 <input data-format="yyyy-MM-dd" id="dueTime" name="dueTime" value="${dueTime}"  type="text"  style="width:180px" placeholder="请填写归还时间" required data-content="请填写归还时间"></input>
					    <span class="add-on">
					      <i data-time-icon="icon-time" data-date-icon="icon-calendar">
					      </i>
						</span>
				  </div>
			</li>
		</ul>

		<ul class="clearfix">
			<li class="li_form" style="width: 400px;">
			    <label class="editableLabel" style="float:left;padding-top:4px;">客户经理：</label>
			    <div id="_eoss_customer" style="float:left;">
			   		<div id='formStaff'></div>
				</div>
			</li>
			<li class="li_form" style="width: 400px;">
			    <label class="editableLabel" style="float:left;padding-top:4px;">驻点工程师：</label>
			    <div id="_eoss_customer" style="float:left;">
			   		<div id='formStaff2'></div>
				</div>
			</li>
		</ul>
		<ul>
			<li id="field_userName" class="li_form" style="width: 800px;">
				<label for="contractName" class="editableLabel" style="padding-top:3px;">项目名称：</label>
				<div id="_project_div" ></div>
				<input type="hidden" id="projectName" name="projectName" value=""/> 	
				<input type="hidden" id="projectId" name="projectId" value=""/> 		
			
			</li>
		</ul>
		<ul class="clearfix">
			<li id="field_userName" class="li_form" style="width: 800px;">
				<label for="userName" class="editableLabel" style="padding-top:3px;">借货说明：</label>
				<input type="text" id="remark" name="remark" style="width: 610px;" value="">
			</li>
		</ul>
		<h3>用户信息</h3>
		<ul class="clearfix">
			<li id="field_userName" class="li_form" style="width: 400px;">
				<label for="userName" class="editableLabel" style="padding-top:3px;">用户单位：</label>
				<input type="text" id="userDept" name="userDept" style="width: 210px;" value="" placeholder="请填写用户单位" required data-content="请填写用户单位">
			</li>
			<li id="field_userName" class="li_form" style="width: 400px;">
				<label for="userName" class="editableLabel" style="padding-top:3px;">用户联系人：</label>
				<input type="text" id="userContact" name="userContact" style="width: 210px;" value="">
			</li>
		</ul>
			<ul class="clearfix">
			<li id="field_userName" class="li_form" style="width: 400px;">
				<label for="userName" class="editableLabel" style="padding-top:3px;">联系电话：</label>
				<input type="text" id="phone" name="phone" style="width: 210px;"  value="">
			</li>
			<li id="field_userName" class="li_form" style="width: 400px;">
				<label for="userName" class="editableLabel" style="padding-top:3px;">邮编：</label>
				<input type="text" id="zipCode" name="zipCode" style="width: 210px;" value="">
			</li>
		</ul>
		<ul class="clearfix">
			<li id="field_userName" class="li_form" style="width: 800px;">
				<label for="userName" class="editableLabel" style="padding-top:3px;">通讯地址：</label>
				<input type="text" id="address" name="address" style="width: 610px;" value="">
			</li>
		</ul>
		<h3>设备清单</h3>
		<div id='_editTable_'></div>
		<div class="form_table clearfix" style="position: relative; z-index: auto;">
		<table id="editTableName" cellspacing="0" width="100%" border="0">
				<!-- <tr>
					<td class="tdoperagroup" colspan="4">
						<span class="add">
							<a id="addRow" href="#">添加行</a>
						</span>
						<span class="del">
							<a id="delRow" href="#">删除行</a>
						</span>
					</td>
				</tr> -->
			<tr>
				<!-- <th style="width:30px;"><input id="chk_all" name="chk_list2" type="checkbox" /></th> -->
				<th width='30%'>厂商</th>
				<th width='30%'>产品编号</th>
				<th width='20%'>数量</th>
				<th width='20%'>成本</th>
			</tr>
			<c:forEach var="product" items="${product}" varStatus="status">
				<tr>
					<td class="sino_table_label" tdValue='${product.Partner}'>${product.Partner}</td>
					<td class="sino_table_label" tdValue='${product.ProductNo}'>${product.ProductNo}</td>
					<td class="sino_table_label"><input type="text" num="${status.index}" id='num_${status.index}' name='productNum' class="productNum" value='${product.num}' tdValue='${product.num}'/></td>
					<td class="sino_table_label"><fmt:formatNumber value="${product.Rent}" type="currency" currencySymbol="￥"/></td>
				</tr>
			</c:forEach>
	</table>
	</div>
		
		</div>
</form>
</div>

	<!--按钮组-->
    		<div id="bottom_button" class="handprocess_btngroup">
        		<input id="ok_Save" type="button" value="提交"  class="btn btn-primary">
        		<input id="no_back" type="button" value="取消" class="btn">
    		</div>

 <script language="javascript">
 	//var form = ${form};
    seajs.use(['js/page/business/outOrInInventory/outStorageAdd'],function(outStorageAdd){
    	outStorageAdd.init();
    });
</script>
</body>
</html>