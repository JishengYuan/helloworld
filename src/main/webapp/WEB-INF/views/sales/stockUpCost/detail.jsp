<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/global.jsp"%>
<%@ page import="com.sinobridge.systemmanage.util.Global"%> 
<%@ page import="com.sinobridge.systemmanage.vo.SystemUser"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
	<title>分解备货合同成本</title>
	<%@ include file="/common/include-base-boostrap-styles.jsp" %>

	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formstyle.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/dialog.css" type="text/css"></link>

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
	<script src="${ctx}/js/plugins/seajs/sea.js" id="seajsnode" type="text/javascript"></script>
	<script src="${ctx}/js/plugins/seajs/config.js" type="text/javascript"></script>
	<link href="${ctx}/skin/default/sales/sale.css" rel="stylesheet"/>
	<style type="text/css">
	 .tright a{
	 color:#fff;
	 }
	 .tright a:hover,.tright a:focus{
	 color:#fff;
	 }
	 .row-label{
	 color:#888888;
	 }
	 .form_table table td {
	 color:#111111;
	 }
	 ._spot_div ul {
	margin-left:1px;
	 }
	 ._spot_div ul li{
	 	line-height:30px;
	 	border-bottom: 1px solid #cccccc;
	 	width:80px;
	 }

	 ._spot_div ul a:link{
	 	color:blue;
	 }
	 ._spot_div ul a:visited{
	 	color:blue;
	 }
	 ._spot_div ul a:hover{
	 	color:red;
	 }
	 .stroke {
 color: #c00;
 -webkit-text-stroke: 1px #000;
}
	.popover {
  		max-width: 730px;
  		height:50%;
	}
	.ri-top{
	margin-top:15px;
}
#contractAmount{
	color: red; 
	font-weight: 800; 
	font-size: 16px
}
.moneyfomat{
	font-weight:900;
	color: #f76107;
	font-size: 15px
}
#_product_total{
	font-weight:bold;
	color: #1076BE;
	font-size: 22px
}

	
	</style>
</head>
<body>
<!-- 遮盖层div -->
	<div id="_progress1" style="display: none; top: 0px; left: 0px; width: 100%; margin-left: 0px; height: 100%; margin-top: 0px; z-index: 1000; opacity: 0.7; text-align: center; background-color: rgb(204, 204, 204); position: fixed;">
	</div>
	<div id="_progress2" style="display: none; width: 233px; height: 89px; top: 45%; left: 45%; position: absolute; z-index: 1001; line-height: 23px; text-align: center;">
		<div style="top: 50%;position:fixed;">
			<div style="display: block;">数据处理中，请稍等...</div>
		</div>
	</div>
	
	<div class="salecontent">
		<div class="top">
			<div class="caption">
				<i class="icon-reorder"></i>&nbsp;&nbsp;&nbsp;备货合同成本划分为产品合同，产品合同编号：${model.contractCode}
			</div>
		</div>
	</div>
		<div class="portlet-body form" id="top" >

			<!-- BEGIN FORM-->
			<form id='_sino_eoss_sales_contract_addform' name='_sino_eoss_sales_contract_addform' class='form-horizontal' >
				<!--隐藏字段start -->
				<!--  隐藏的合同ID-->
				<input type='hidden' name='contractCostId' id='contractCostId'  value="${model.id}"/>
	
				
				<!--  保存新合同数据-->
				<input type='hidden' name='salesContractId' id='salesContractId'  value="${model.salesContractId}"/>
				<input type='hidden' id='contractCode' name='contractCode'  value="${model.contractCode}" />
				<input type='hidden' id='contractName' name='contractName'  value="${model.contractName}" />
				<input type='hidden' id='creatorName' name='creatorName'  value="${model.creatorName}" />
				<input type='hidden' name='contractAmount' id='contractAmount'  value="${model.contractAmount}"/>
				<!--  隐藏的tableGrid-->
				<input type='hidden' name='tableData' id='tableData' />
				<!--  隐藏的合同类型ID-->
				<input type="hidden" name="listNum" id="listNum" value="${listNum}">
				<input type="hidden" name="productList" id="productList" value="${productList}">
				<input type="hidden" name="doState" id="doState" value="${model.doState}">
			<!-- 小标题end -->
			<!-- 1行start -->
			<div style="height:30px;"></div>
			<div class=" row-inline" >
				<div class='ri-top'>
					<label class='row-label' for='contractName' >合同名称：</label>
					<div class='row-span'>
						<span>${model.contractName}</span>
					</div>
				</div>
			</div>
			<div class=" row-inline" >
				<div class='ri-top'>
					<label class='row-label' for='contractName' >合同金额：</label>
					<div class='row-span'>
						<span id="contractAmount"><fmt:formatNumber value="${model.contractAmount}" type="currency" currencySymbol="￥"/></span>
					</div>
				</div>
			</div>
			<div class=" row-inline" >
				<div class='ri-top'>
					<label class='row-label' for='contractName' >客户经理：</label>
					<div class='row-span'>
						<span >${model.creatorName}</span>
					</div>
					<!-- <div class='row-span'>
					</div> -->
				</div>
			</div>
		

			<!-- 分割行start -->
			<div class=" row-inline" >
				<hr size=1 class="dashed-inline">
			</div>
			<!-- 分割行end -->
				

			
			<!-- 小标题start -->
			<div class="row-inline product_handler">
				<h5 class="form-section-title"><a name="salesBill">关联备货合同列表</a></h5>
			</div>
			<!-- 小标题end -->
			<!-- 9行start -->	
			<div class="row-inline product_handler">
				<div id="product_table" class='table-tile-width'>
				</div>
			</div>
			<div class=" row-inline" >
				<hr size=1 class="dashed-inline">
			</div>			<div class="product_handler" style="float:right;margin-right:71px;margin-top: 18px;margin-bottom:10px;">本合同的商务成本合计:<span style="" id="_product_total"  >0</span>
			<input type="hidden" id="productTotal" name="productTotal" ></div>
			<!-- 9行end -->	

			<div class=" row-inline" >
				<hr size=1 class="dashed-inline">
			</div>
			<!-- 分割行end -->
			<br class='float-clear' />
			<!-- 分割行end -->
			


			<br class='float-clear' />
			<c:if test="${model.doState == 0}">
				<div class="form-actions">
					<div style='margin-left: 200px;'>
						&nbsp;&nbsp;&nbsp;&nbsp;
						<a id='_sino_eoss_sales_contract_submit' role="button" class="btn btn-success"><i class="icon-ok"></i>提交</a>
						&nbsp;&nbsp;&nbsp;&nbsp;
						<button id='_sino_eoss_sales_contract_back' type="button" class="btn"><i class="icon-remove"></i>取消</button>
					</div>		
			</div> 
			</c:if>
			<c:if test="${model.doState == 1}">
				<div class="form-actions">
					<div style="text-align: center;margin-right: 160px;">
						<button id='_sino_eoss_sales_contract_back' type="button" class="btn"><i class="icon-remove"></i>关闭</button>
					</div>
				</div>
			</c:if>
			
			</form>
			<!-- END FORM-->  
		</div>
	<script language="javascript">
		//var form = ${form};  //从controller层接受数据模型
		//var flowFlag= "${flowStep}";
		seajs.use('js/page/sales/stockUpCost/detail',function(detail){
			detail.init();
		});    
	</script>
</body>
</html>
