<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/global.jsp"%>
<%@ page import="com.sinobridge.systemmanage.util.Global"%> 
<%@ page import="com.sinobridge.systemmanage.vo.SystemUser"%> 
<html>
<head>
	<title>合同信息变更</title>
	<%@ include file="/common/include-base-boostrap-styles.jsp" %>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formSelect.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formstyle.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/dialog.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/tip.css" type="text/css" ></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/user-selection.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formSearchTip.css" type="text/css" ></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/jquery-ui-1.8.17.custom.css" type="text/css" ></link>
	<link rel="stylesheet" href="${ctx}/skin/default/editgrid/editgrid.css" type="text/css" />
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
	.row-column{
		float:left;
		margin-left:16px;
		}
		.row-column:first-child{
		margin-left:0px;
		}
		.row-label{
		color: #eeb422;
		}
		</style>
</head>
<body>
	<div class="salecontent">
		<div class="top">
			<div class="caption">
				<i class="icon-reorder"></i>&nbsp;&nbsp;&nbsp;合同信息变更
				<span class="tright" >合同编号：${model.contractCode}</span>
			
			</div>
			<div class="tools">
				<!--a href="javascript:;" class="reload"><i class="icon-repeat"></i>返回</a-->
			</div>
		</div>
	</div>
		<div class="portlet-body form">
		<!-- BEGIN FORM-->
		<form id='_sino_eoss_sales_contract_change_addform' name='_sino_eoss_sales_contract_addform' class='form-horizontal' >
			<!--隐藏字段start -->
			<input type='hidden' id='_is_submit_product'  value="1"/>
			<input type='hidden' name='contractId' id='id'  value="${model.id}"/>
			<!--  隐藏的合同变更申请的ID-->
			<input type='hidden' name='salesContractChangeApplyId' id='salesContractChangeApplyId'  value="${changeApplyModel.id}"/>
			<!--  隐藏的合同变更申请的变更内容类型-->
			<input type='hidden' name='changeType' id='changeType'  value="${changeApplyModel.changeType}"/>
			<!--  隐藏的tableGrid-->
			<!--隐藏的合同编码-->
			<input type='hidden' id='contractCode' name='contractCode'  value="${model.contractCode}"/>
			<!--隐藏的合同类型，用于回显-->
			<input type='hidden' name='contractType' id='_eoss_sales_contractTypeId'  value="${model.contractType}"/>
			<!--隐藏的结算币种，用于回显-->
			<input type='hidden' name='accountCurrency' id='_eoss_sales_accountCurrencyId'  value="${model.accountCurrency}"/>
			<!--隐藏的发票类型，用于回显-->
			<input type='hidden' name='invoiceType' id='_eoss_sales_invoiceTypeId'  value="${model.invoiceType}"/>
			<!--隐藏的收款方式，用于回显-->
			<input type='hidden' name='receiveWay' id='_eoss_sales_receiveWayId'  value="${model.receiveWay}"/>
			<!--隐藏的客户ID，用于回显-->
			<input type='hidden' name='customerId' id='_eoss_sales_customerId'  value="${model.customerId}"/>
			<!--隐藏的合同状态-->
			<input type='hidden' name='contractState' id='contractState'  value="SH"/>
			<!--隐藏的创建人ID-->
			<input type='hidden' name='creator' value="${model.creator}"/>
			<!--隐藏的添加时间-->
			<input type='hidden' name='createTime' value="${model.createTime}"/>
			<!--隐藏的工单ID-->
			<input type='hidden' name='processInstanceId' value="${model.processInstanceId}"/>
			<!--隐藏的附件ID-->
			<input type='hidden' name='attachIds' id='attachIds'/>
			<input type='hidden' name='customerContact' id='customerContact' value="${model.customerContact }" />
			<!--暂定行业，行业客户，客户，联系人,手机，合同编码不能修改，只读,所以将下列加载下拉列表方法注释-->
			<!--隐藏的行业ID，用于回显-->
			<!--<input type='hidden' name='IndustryId' id='_eoss_sales_IndustryId'/>-->
			<!--隐藏的行业客户ID，用于回显-->
			<!--<input type='hidden' name='idtCustomerId' id='_eoss_sales_idtCustomerId'/>-->
			<!--预存收款计划JSON数据-->
			<input type='hidden' name='tableData' id='tableData' />
			<!--保存为草稿还是提交的标志位-->
			<input type='hidden' name='isSubmit' id='isSubmit' />
			<!--进入流程后重新审批的标志位-->
			<input type="hidden" id="flowFlag" name="flowFlag" value="${flowStep}" >
			<!--  隐藏的流程中所需要的任务ID-->
			<input type="hidden" id="taskId" name="taskId" value="${taskId}" >
			<!--  隐藏的流程中所需要的工单ID-->
			<input type="hidden" id="procInstId" name="procInstId" value="${procInstId}" >
			<!--隐藏字段end -->
			<!-- 小标题start -->
			<div class="row-inline-first" >
				<h4 class="form-section-title">合同基本信息</h4>
			</div>
			<!-- 小标题end -->
			<!-- 1行start -->
			<div class=" row-inline" >
				<div class=''>
					<label class='row-label' for='contractName' >合同名称：</label>
					<div class='row-input'>
						<input class="row-input-oneColumn" type='text' id='contractName' name='contractName' placeholder='请输入合同名称' value="${model.contractName}" required data-content="请输入合同名称"/>
						<span class="help-block"></span>
					</div>
				</div>
			</div>
			<!-- 1行end -->
			<!-- 2行start -->
			<%-- <div class=" row-inline" >
				<div class=''>
					<label class='row-label' for='contractName' >合同简称：</label>
					<div class='row-input'>
						<input type='text' id='contractShortName' name='contractShortName' placeholder='请输入合同简称' value="${model.contractShortName}" />
						<span class="help-block"></span>
					</div>
				</div>
			</div> --%>
			<div class=" row-inline" >
				<div class=''>
					<label class='row-label' for='contractName' >合同类型：</label>
					<div class='row-input' id="_eoss_sales_contractType" style="width:220px;margin-top:5px;font-size:14px;"></div>
				</div>
			</div>
			<div class=" row-inline" >
				<div class='row-column'>
					<label class='row-label' for='contractName' >合同金额：</label>
					<div class='row-input'>
						<div class="input-append">
							<input type='text'  class='row-input-addTip' id='contractAmount' name='contractAmount'  placeholder='请输入合同金额' value="${model.contractAmount}" pattern='number' required data-content="请输入合同金额"/>
							<span class="add-on">元</span>
						</div>
						<span class="help-block"></span>
					</div>
				</div>
				<div class='row-column'>
					<label class='row-label' for='receiveWay' >收款方式：</label>
					<div class='row-input' id="_eoss_sales_receiveWay"></div>
				</div>
			</div>
			<!-- 2行end -->
			<!-- 3行start -->
			<div class=" row-inline" >
				<div class='row-column'>
					<label class='row-label' for='invoiceType' >发票类型：</label>
					<div class='row-input' id="_eoss_sales_invoiceType"></div>
				</div>
				<div class='row-column'>
					<label class='row-label' for='accountCurrency' >结算币种：</label>
					<div class='row-input' id="_eoss_sales_accountCurrency"></div>
				</div>
			</div>
			<!-- 3行end -->
			<!-- 分割行start -->
			<div class=" row-inline" >
				<hr size=1 class="dashed-inline">
			</div>
			<!-- 分割行end -->
			<!-- 小标题start -->
			<div class=" row-inline" >
				<h4 class="form-section-title">客户信息（甲方）</h4>
			</div>
			<!-- 小标题end -->
			<!-- 5行start -->
			<!-- <div class=" row-inline" >
				<div class=''>
					<label class='row-label' for='industryName' >行业选择：</label>
					<div class='row-input' id='_industryName'></div>
					<div class='row-input'>
						<input type="text"  id='_industryName'/>
						<span class="help-block"></span>
					</div>
				</div>
			</div>
			<div class=" row-inline" >
				<div class=''>
					<label class='row-label' for='customerIdtCustomer' >行业客户：</label>
					<div class='row-div-twoColumn' id='_customerIdtCustomer'></div>
					<div class='row-input'>
						<input class='row-input-twoColumn' type="text" id='_customerIdtCustomerName'>
						<span class="help-block"></span>
					</div>
				</div>
			</div> -->
			<!-- 5行end -->
			<!-- 6行start -->
			<div class=" row-inline" >
				<div class=''>
					<label class='row-label' for='customerInfo' >客户选择：</label>
					<!--<div class='row-input' id='_customerInfo'></div>-->
					<div class='row-div-oneHalfColumn' >
						<!-- <input type="text"  id='_customerInfoName' style='width:356px'> -->
						<span class="help-block" id='_customerInfoName'></span>
					</div>
				</div>
			</div>
			<div class=" row-inline" >
				<div class=''>
					<label class='row-label' for='customerContacts' >联&nbsp;&nbsp;系&nbsp;&nbsp;人：</label>
					<div class='row-input-halfColumn'>
						<!-- <input type="text"  id="_customerContacts"> -->
						<span class="help-block" id="_customerContacts"></span>
					</div>
				</div>
			</div>
			<div class=" row-inline" >
				<div class=''>
					<label class='row-label' for='customerContactsPhone' >手机号码：</label>
					<div class='row-input-halfColumn'>
						<!-- <input type="text"  id="_customerContactsPhone"> -->
						<span class="help-block" id="_customerContactsPhone"></span>
					</div>
				</div>
			</div>
			<!-- 6行end -->
			<!-- 分割行start -->
			<div class=" row-inline" >
				<hr size=1 class="dashed-inline">
			</div>
			<!-- 分割行end -->
			<!-- 小标题start -->
			<div class=" row-inline" >
				<h4 class="form-section-title">公司信息(乙方)</h4>
			</div>
			<!-- 小标题end -->
			<!-- 7行start -->
			<div class=" row-inline" >
				<div class=''>
					<label class='row-label' for='contractName' >公司名称：<span>北京神州新桥科技有限公司</span></label>
					<!-- <div class='row-input'>
						<input class='row-input-twoColumn' readonly="readonly"  type="text" value="北京神州新桥科技有限公司 ">
						<span class="help-block"></span>
					</div> -->
				</div>
			</div>
			<div class=" row-inline" >
				<div class=''>
					<label class='row-label' for='contractName' >客户经理：<span>${model.creatorName}</span></label>
					<div class='row-input'>
					<!--TODO 暂时没有向后台传入该字段,显示的时候不要用readonly的input框(初步定将这几个字段定义为常量)-->
						<%-- <input type="text" name='creatorName' readonly="readonly"  value="${model.creatorName}">
						<span class="help-block"></span> --%>
					</div>
				</div>
			</div>
			<!-- 7行end -->	
			<!-- 8行start -->
			<div class=" row-inline" >
				<div class=''>
					<label class='row-label' for='contractName' >开户银行：<span>中国工商银行紫竹院支行</span></label>
					<!-- <div class='row-input'>
						<input class='row-input-twoColumn' type="text" readonly="readonly"   value="中国工商银行紫竹院支行">
						<span class="help-block"></span>
					</div> -->
				</div>
			</div>
			<div class=" row-inline" >
				<div class=''>
					<label class='row-label' for='contractName' >银行账户：<span>0200007609004774060</span></label>
					<div class='row-input'>
					<!--TODO 暂时没有向后台传入该字段,显示的时候不要用readonly的input框(初步定将这几个字段定义为常量)-->
						<!-- <input type="text"  readonly="readonly"  value="0200007609004774060"> 
						<span class="help-block"></span> -->
					</div>
				</div>
			</div>
			
			<div class=" row-inline" >
				<h4 class="form-section-title" id="service_h4">合同执行信息</h4>
			</div>
			<div class=" row-inline" id="serviceStartDate_div">
				<div class=''>
					<label class='row-label' for='accountCurrency' ><span style="color:red;">*</span>服务开始时间：</label>
					<div class='row-input'>
						<div class="input-append date">
				  		    <input data-format="yyyy-MM-dd" style="width:150px;" type="text" id="serviceStartDate_input" name="serviceStartDate" required data-content="请输入服务开始时间" value="${model.serviceStartDate }"></input>
						    <span class="add-on">
						      <i data-time-icon="icon-time" data-date-icon="icon-calendar">
						      </i>
							</span>
						  </div>
					</div>
				</div>
			</div>
			<div class=" row-inline" id="serviceEndDate_div">
				<div class=''>
					<label class='row-label' for='invoiceType' ><span style="color:red;">*</span>服务结束时间：</label>
					<div class='row-input'>
						<div class="input-append date">
				  		    <input data-format="yyyy-MM-dd" style="width:150px;" type="text" id="serviceEndDate_input" name="serviceEndDate" required data-content="请输入服务结束时间" value="${model.serviceEndDate }"></input>
						    <span class="add-on">
						      <i data-time-icon="icon-time" data-date-icon="icon-calendar">
						      </i>
							</span>
						  </div>
					</div>
				</div>
			</div>
			
			<div class=" row-inline"  style="margin-top:40px;"></div>
			
			<div class=" row-inline" id="estimateProfit_div">
				<%-- <div class='row-column' id="">
					<label class='row-label' for='accountCurrency' ><span style="color:red;">*</span>合同生效时间：</label>
					<div class='row-input'>
						<div class="input-append date">
				  		    <input data-format="yyyy-MM-dd" style="width:150px;" type="text" id="salesStartDate_input" name="salesStartDate" value="${model.salesStartDate }" required data-content="请输入合同生效时间"></input>
						    <span class="add-on">
						      <i data-time-icon="icon-time" data-date-icon="icon-calendar">
						      </i>
							</span>
						  </div>
					</div>
				</div> --%>
				<div class='row-column' id="">
					<label class='row-label' for='invoiceType' ><span style="color:red;">*</span>合同终止时间：</label>
					<div class='row-input'>
						<div class="input-append date">
				  		    <input data-format="yyyy-MM-dd" style="width:150px;" type="text" id="sales_EndDate_input" name="salesEndDate" value="${model.salesEndDate }" required data-content="请输入合同终止时间"></input>
						    <span class="add-on">
						      <i data-time-icon="icon-time" data-date-icon="icon-calendar">
						      </i>
							</span>
						  </div>
					</div>
				</div>
				<div class='row-column'>
					<label class='row-label' for='receiveWay' ><span style="color:red;">*</span>预估毛利率：</label>
					<div class='row-input'>
						<input type="text" name="estimateProfit" style="width:50px;" required data-content="请输入预估毛利率" value="${model.estimateProfit }">
					</div>
				</div>
			</div>
			
			<div class=" row-inline"  style="margin-top:10px;">
			<div class='row-column'>
					<label class='row-label' for='receiveWay' >预估毛利说明：</label>
					<div class='row-input'>
						<input type="text" name="estimateProfitDesc" value="${model.estimateProfitDesc }" style="width:800px;" placeholder="请输入估算毛利的依据和说明">
					</div>
				</div>
			</div>
			
			<!-- 8行end -->
			<!-- 分割行start -->
			<div class="row-inline product_handler" >
				<hr size=1 class="dashed-inline">
			</div>
			<!-- 分割行end -->
			<!-- 小标题start -->
			<div class="row-inline product_handler" >
				<h5 class="form-section-title">合同清单
					<div style="float:right;width:15%;" class="product_handler"><a style="color:#A3A3A3;" id="_sales_import_excel" href='${ctx }/sales/doc/projectProductTemplate.xls' target='_self'><i class="icon-download-alt"></i>导入模板下载</a></div>
				</h5>
				<div class="row-inline">
					<label class='row-label' for='receiveWay' ><span style="color:red;">*</span>期望到货日期：</label>
					<div class='row-input'>
							<div class="input-append date">
							<input data-format="yyyy-MM-dd" id="_hopeArriveTime" style="width:150px;" type="text" name="hopeArriveTime" value="${model.hopeArriveTime }" required data-content="请输入期望到货时间"></input>
						    <span class="add-on">
						      <i data-time-icon="icon-time" data-date-icon="icon-calendar">
						      </i>
							</span>
						  </div>
					</div>
					<label class='row-label' style="margin-left: 10px;"><span style="color:red;"></span>期望到货地点：</label>
					<div class='row-input'>
						<input type="text" name="hopeArrivePlace" value="${model.hopeArrivePlace }" style="width:475px;" placeholder='请输入期望到货地点' />
					</div>
				</div>
				
				<div class="products_add_btn">
					<!-- 产品总金额:<span id="_product_total"></span> -->
					<a id='_sino_eoss_sales_products_add' role="button" class="btn btn-success"><i class="icon-plus"></i>添加</a>
					<a id='_sino_eoss_sales_products_import'  role="button" href='#_sino_eoss_sales_products_import_page' role='button' target='_self'  data-toggle='modal' aria-hidden="true" class="btn btn-success"><i class="icon-plus"></i>导入</a>
					<a id='_sino_eoss_sales_products_relate'  role="button" href='#_sino_eoss_sales_products_import_page' role='button' target='_self'  data-toggle='modal' aria-hidden="true" class="btn btn-success"><i class="icon-plus"></i>关联备货合同</a>
				</div>
			</div>
			<!-- 小标题end -->
			<!-- 9行start -->
			<div class="row-inline product_handler" >
				<div class='table-tile-width'>
				<c:if test="${model.contractType != '3000'}">
					<table id="_sino_eoss_sales_contract_products_table"class="table  table-bordered table-striped">
					  <thead>
					    <tr>
					      <th style="width:6%;">序号</th>
					      <th style="width:9%;" id="type_th">产品类型</th>
					      <th style="width:9%;" id="partner_th">产品厂商</th>
					      <th style="width:9%;" id="product_th">产品型号</th>
					      <th style="width:12%;">服务开始时间</th>
					      <th style="width:12%;">服务结束时间</th>
					      <th style="width:6%;">数量</th>
					      <th style="width:9%;">单价</th>
					      <th style="width:12%;">合计</th>
					      <th style="width:20%;">操作</th>
					    </tr>
					  </thead>
					  <tbody>
						<c:forEach var="salesContractProductModel" items="${model.salesContractProductModel}" varStatus="status">
							<tr style='text-algin:center' id='tr_${ status.index + 1}'>
								<%-- <td >${ status.index + 1}<input id='_contract_product_id_${ status.index + 1}' name='contractProductIds' type='hidden'value='${salesContractProductModel.id}'></td> --%>
								<td >${ status.index + 1}</td>
								<td><span>${salesContractProductModel.productTypeName }</span><input id='_product_Type_${ status.index + 1}' name='productTypes' type='hidden' value='${salesContractProductModel.productType }'><input id='_product_Type_Name_${ status.index + 1}' name='productTypeNames' type='hidden' value='${salesContractProductModel.productTypeName }'></td>
								<td><span>${salesContractProductModel.productPartnerName }</span><input id='_product_Partner_${ status.index + 1}' name='productPartners' type='hidden' value='${salesContractProductModel.productPartner }'><input id='_product_Partner_Name_${ status.index + 1}' name='productPartnerNames' type='hidden' value='${salesContractProductModel.productPartnerName }'></td>
								<td><span>${salesContractProductModel.productName}</span><input id='_product_No_${ status.index + 1}' name='productNos' type='hidden' value='${salesContractProductModel.productNo }'><input id='_product_Name_${ status.index + 1}' name='productNames' type='hidden' value='${salesContractProductModel.productName }'></td>
								<td><span>${salesContractProductModel.serviceStartDate }</span><input id='_product_startTime_${ status.index + 1}' name='serviceStartDates' type='hidden' value='${salesContractProductModel.serviceStartDate }'></td>
								<td><span>${salesContractProductModel.serviceEndDate }</span><input id='_product_endTime_${ status.index + 1}' name='serviceEndDates' type='hidden' value='${salesContractProductModel.serviceEndDate }'></td>
								<td><span>${salesContractProductModel.quantity }</span><input id='_product_quantity_${ status.index + 1}' name='quantitys' type='hidden' value='${salesContractProductModel.quantity }'></td>
								<td><span><fmt:formatNumber value="${salesContractProductModel.unitPrice }" type="currency" currencySymbol="￥"/></span><input id='_product_unitPrice_${ status.index + 1}' name='unitPrices' type='hidden' value='${salesContractProductModel.unitPrice }'></td>
								<td><span><fmt:formatNumber value="${salesContractProductModel.totalPrice }" type="currency" currencySymbol="￥"/></span><input id='_product_totalPrice_${ status.index + 1}' name='totalPrices' type='hidden' value='${salesContractProductModel.totalPrice }'><input id='_product_remark_${ status.index + 1}' name='productRemarks' type='hidden' value='${salesContractProductModel.remark }'></td>
								<td style='text-algin:center'>
									<c:if test="${salesContractProductModel.relateDeliveryProductId == null||salesContractProductModel.relateDeliveryProductId == '' }">
										<a serial_num='${ status.index + 1}' id='_sino_eoss_sales_contract_update_product_${ status.index + 1}'  class='btn btn-primary _sino_eoss_sales_contract_update_product' style='width:40px;font-size:12px;padding:2px;'><i class='icon-pencil'></i>修改</a>
										<input type='hidden' name='relateDeliveryProductId' /><input type='hidden' name='relateContractProductId' value='0'/>
									</c:if>
									<c:if test="${salesContractProductModel.relateDeliveryProductId != null&&salesContractProductModel.relateDeliveryProductId != '' }">
										<input type='hidden' name='relateDeliveryProductId' value="${salesContractProductModel.relateDeliveryProductId }"/><input type='hidden' name='relateContractProductId' value='${salesContractProductModel.id }'/>
									</c:if>
									&nbsp;&nbsp;&nbsp;<a id1="${salesContractProductModel.relateDeliveryProductId }" id2="${salesContractProductModel.id }" serial_num='${ status.index + 1}' id='_sino_eoss_sales_contract_remove_product_${ status.index + 1}'  class='btn btn-danger _sino_eoss_sales_contract_update_remove_product' style='padding:2px;'><i class='icon-remove'></i>删除</a>
								</td>
							</tr>
						</c:forEach>
					  </tbody>
					</table>
					</c:if>
					<c:if test="${model.contractType == '3000'}">
					<table id="_sino_eoss_sales_contract_maproducts_table" style="display:none;" class="table  table-bordered table-striped">
					  <thead>
					    <tr>
					      <th style="width:6%;">序号</th>
					      <th style="width:9%;">产品厂商</th>
					      <th style="width:9%;">产品型号</th>
					      <th style="width:8%;">序列号</th>
					      <th style="width:9%;">服务期(月)</th>
					      <th style="width:11%;">服务开始日期</th>
					      <th style="width:11%;">服务结束日期</th>
					      <th style="width:9%;">销售价格</th>
					      <th style="width:9%;">销售数量</th>
					      <th style="width:9%;">销售总价</th>
					      <th style="width:9%;">设备属地</th>
					    </tr>
					  </thead>
					  <tbody>
					  <c:forEach var="salesContractProductModel" items="${model.salesContractProductModel}" varStatus="status">
							<tr style='text-algin:center' id='tr_${ status.index + 1}'>
								<td >${ status.index + 1}</td>
								<td><span>${salesContractProductModel.productPartnerName }</span><input id='_product_Partner_${ status.index + 1}' name='productPartners' type='hidden' value='${salesContractProductModel.productPartner }'><input id='_product_Partner_Name_${ status.index + 1}' name='productPartnerNames' type='hidden' value='${salesContractProductModel.productPartnerName }'></td>
								<td><span>${salesContractProductModel.productName}</span><input id='_product_No_${ status.index + 1}' name='productNos' type='hidden' value='${salesContractProductModel.productNo }'><input id='_product_Name_${ status.index + 1}' name='productNames' type='hidden' value='${salesContractProductModel.productName }'></td>
								<td><span>${salesContractProductModel.serialNumber }</span><input id='_product_serialNumber_${ status.index + 1}' name='serialNumber' type='hidden' value='${salesContractProductModel.serialNumber }'></td>
								<td><span>${salesContractProductModel.servicePeriod }</span><input id='_product_servicePeriod_${ status.index + 1}' name='servicePeriod' type='hidden' value='${salesContractProductModel.servicePeriod }'></td>
								<td><span>${salesContractProductModel.serviceStartDate }</span><input id='_product_serviceStartDates_${ status.index + 1}' name='serviceStartDates' type='hidden' value='${salesContractProductModel.serviceStartDate }'></td>
								<td><span>${salesContractProductModel.serviceEndDate }</span><input id='_product_serviceEndDates_${ status.index + 1}' name='serviceEndDates' type='hidden' value='${salesContractProductModel.serviceEndDate }'></td>
								<td><span><fmt:formatNumber value="${salesContractProductModel.unitPrice }" type="currency" currencySymbol="￥"/></span><input id='_product_unitPrice_${ status.index + 1}' name='unitPrices' type='hidden' value='${salesContractProductModel.unitPrice }'></td>
								<td><span>${salesContractProductModel.quantity }</span><input id='_product_quantity_${ status.index + 1}' name='quantitys' type='hidden' value='${salesContractProductModel.quantity }'></td>
								<td><span><fmt:formatNumber value="${salesContractProductModel.totalPrice }" type="currency" currencySymbol="￥"/></span><input id='_product_totalPrice_${ status.index + 1}' name='totalPrices' type='hidden' value='${salesContractProductModel.totalPrice }'></td>
								<td><span>${salesContractProductModel.equipmentSplace }</span><input id='_product_equipmentSplace_${ status.index + 1}' name='equipmentSplace' type='hidden' value='${salesContractProductModel.equipmentSplace }'></td>
							</tr>
						</c:forEach>
					  </tbody>
					</table>
					</c:if>
				</div>
			</div>
			<div class="product_handler" style="float:right;margin-right:40px;">合计:<span id="_product_total">0</span>元</div>
			<!-- 9行end -->	
			<!-- 分割行start -->
			<div class=" row-inline collection_plan" >
				<hr size=1 class="dashed-inline">
			</div>
			<!-- 分割行end -->
			<!-- 小标题start -->
			<div class="row-inline" >
				<h4 class="form-section-title" collection_plan>收款计划</h4>
			</div>
			<!--导入 产品div -------end-->
			<div id="_sino_eoss_sales_products_import_div"></div>
			<!-- 小标题end -->
			<!-- 10行start -->
			<div class="row-inline collection_plan" >
				<div class='table-tile-width'>
					<div id="editTable"></div>
				</div>
			</div>	
			<!-- 10行end -->
			<!-- 分割行start -->
			<div class="row-inline" >
				<hr size=1 class="dashed-inline">
			</div>
			<!-- 分割行end -->
			<!-- 小标题start -->
			<div class=" row-inline" >
				<h4 class="form-section-title">合同附件</h4>
			</div>
			<!-- 小标题end -->
			<!-- 11行start -->
			<div class=" row-inline" >
				<div id="uplaodfile"></div>
			</div>	
			<!-- 11行end -->								
			<br class='float-clear' />
			</form>
			<!-- END FORM-->
			
			<!--设备添加弹出对话框 -------start-->
			 <div id="dailogs1" class="modal hide fade" role="dialog" tabIndex="-1" style="width:800px;height:460px;margin-left:-400px">
		   		<div class="modal-header">
				   <h3 id="dtitle"></h3>
				   <div id="_product_alertMsg"></div>
		  		</div>
				<div class="modal-body" style="height:70%">
					<div id="dialogbody" ></div>
				</div>
				<div class="modal-footer">
					<button id="_do_cancel"  class="btn" data-dismiss="modal" aria-hidden="true" >取消</button >
					<a id="dsave"  class="btn btn-primary" data-dismiss="modal" aria-hidden="true" >保存</a>
				</div>
			</div>
			<!--设备添加弹出对话框 -------end-->  			
			<div class="form-actions">
				<div style='text-align:center;'>
					<a id='_sino_eoss_sales_contract_change_submit' role="button" class="btn btn-success"><i class="icon-ok"></i>提交</a>
					<a id='_sino_eoss_sales_contract_change_cancel' role="button" class="btn"><i class="icon-reply"></i>取消</a>
				</div>
			</div>
		</div>
	<script language="javascript">
		var form = ${form};  //从controller层接受数据模型
		seajs.use('js/page/sales/contractchange/changeSalesContract',function(changeSalesContract){
			changeSalesContract.init();
		});    
	</script>
</body>
</html>
