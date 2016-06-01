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


    <title>入库归还</title>
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
			<i class="icon-reorder"></i>&nbsp;&nbsp;&nbsp;入库归还
		</div>
	</div>
</div>
	
	
<div class="div_style" style="float:top; margin-top:30px;"> 
	<form  id="_sino_eoss_inventory_addform2" class='form-horizontal' method="post" action="">
	<input type="hidden"  id="tableData" name="tableData" >
	<input type="hidden" id="returnUser" name="returnUser"/>
	<input type="hidden" id="hideUserName" name="hideUserName" value="${userName }"/>
 	<input type="hidden" id="hideStaffName" name="hideStaffName" value="${staffName }"/>

	
	<div class="handprocess_order grey_btn33" id="SheetDiv" style="padding-left: 60px;">
	<div style="width: 850px;">
		<h3>基本信息</h3>
		<ul class="clearfix" >
			<li id="field_userName" class="li_form" style="width: 400px;">
				<label for="userName" class="editableLabel" style="padding-top:3px;">借货编号：</label>
				<input type="hidden" id="jhSerialNum" name="jhSerialNum" style="width: 210px;" value="">
				
				<div id="_serialNum_div" ></div>
			</li>
			<li id="field_userName" class="li_form" style="width: 400px;">
				<label for="userName" class="editableLabel" style="padding-top:3px;">归还编号：</label>
				<input type="text" id="returnSerialNum" name="returnSerialNum" style="width: 210px;" value="${serialNoStr}" readonly="readonly">	
			</li>
			
		</ul>
		<ul class="clearfix">
			<li id="field_userName" class="li_form" style="width: 400px;">
				<label for="projecctName"  class="editableLabel" style="padding-top:3px;">归还人：</label>
				<div id='formStaff'></div>
			</li>
			<li id="field_userName" class="li_form" style="width: 400px;">
				<label for="projectNo" class="editableLabel" style="padding-top:3px;">归还时间：</label>
				<div style="position: relative;" class="input-append date">
					 <input data-format="yyyy-MM-dd" id="returnDate" name="returnDate" value="${dateTime }" type="text"  style="width:182px" placeholder="请填写归还时间" required data-content="请填写归还时间"></input>
					    <span class="add-on">
					      <i data-time-icon="icon-time" data-date-icon="icon-calendar">
					      </i>
						</span>
				  </div>
			</li>
		</ul>
		<ul class="clearfix">
			<li id="field_userName" class="li_form" style="width: 800px;">
				<label for="projecctName"  class="editableLabel" style="padding-top:20px;">归还说明：</label>
				<textarea name="remark" rows="2" cols="60"  style="width:610px"></textarea>
			</li>
		</ul>
		<h3>设备清单</h3>
		<table id="_editTable" class="table  table-bordered table-striped">
			<thead>
			<tr>
				<td width="5%"><input id="chk_all" name="chk_list2" type="checkbox" /></input></td>
				<td width="20%">厂商</td>
				<td width="20%">产品编号</td>
				<td width="20%">序列号</td>
				<td width="17%">数量</td>
				<td width="18%">成本</td>
			</tr>
			</thead>
		</table>
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
    seajs.use(['js/page/business/putOrInInventory/inStorageAdd'],function(inStorageAdd){
    	inStorageAdd.init();
    });
</script>
</body>
</html>