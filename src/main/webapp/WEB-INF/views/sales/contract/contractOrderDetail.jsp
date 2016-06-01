	<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
	<%@ page language="java" contentType="text/html; charset=UTF-8"
		pageEncoding="UTF-8"%>
	<%@ include file="/common/global.jsp"%>
	<html>
	<head>
		<title>合同订单状态</title>
		<%@ include file="/common/include-base-boostrap-styles.jsp" %>
		<script src="${ctx}/js/plugins/seajs/sea.js" id="seajsnode" type="text/javascript"></script>
		<script src="${ctx}/js/plugins/seajs/config.js" type="text/javascript"></script>
		<link href="${ctx}/skin/default/sales/sale.css" rel="stylesheet"/>

		
	</head>
	<body>
		<div class="salecontent">
			<div class="top">
				<div class="caption">
					<i class="icon-reorder"></i>&nbsp;&nbsp;&nbsp;合同订单状态
					<span class="tright" style="color:#f4606c">合同编码：${model.contractCode}</span>
				
				</div>
				<div class="tools">
					<!--a href="javascript:;" class="reload"><i class="icon-repeat"></i>返回</a-->
				</div>
			</div>
		</div>
			<div class="portlet-body form" >
				<!-- BEGIN FORM-->
				<form id='_sino_eoss_sales_invoice_approveForm' class='form-horizontal' >
					<!--隐藏字段start -->
					<!--  隐藏的合同ID-->
					<input type='hidden' name='id' id='id'  value="${model.id}"/>
					<!--  隐藏的合同类型ID-->
					<input type="hidden" name="contractTypeId" id="contractTypeId" value="${model.contractType}"/>
					<!--  隐藏的合同名称-->
					<input type='hidden' name='contractName' id='contractName'  value="${model.contractName}"/>
					<!--隐藏的客户ID，用于回显-->
					<input type='hidden' name='customerId' id='_eoss_sales_customerId'  value="${model.customerId}"/>
					<!--  隐藏的tableData初始化数据-->
					<input type='hidden' name='tableData' id='tableData' />
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
							<div class='row-span-oneColumn'>
								<span>${model.contractName}</span>
							</div>
						</div>
					</div>
	
					<div class=" row-inline" >
						<div class=''>
							<label class='row-label' for='contractName' >合同类型：</label>
							<div class='row-span'>
								<span id="contractType"></span>
							</div>
						</div>
					</div>
					<div class=" row-inline" >
						<div class=''>
							<label class='row-label' for='contractName' >合同金额：</label>
							<div class='row-span'>
								<span id="contractAmount">${model.contractAmount}元</span>
							</div>
						</div>
					</div>
					<!-- 2行end -->
					<!-- 3行start -->
					<div class=" row-inline" >
						<div class=''>
							<label class='row-label' for='contractName' >行业名称：</label>
							<div class='row-span'>
								<span id="_industryName"></span>
							</div>
						</div>
					</div>
					<div class=" row-inline" >
						<div class=''>
							<label class='row-label' for='contractName' >行业客户：</label>
							<div class='row-span'>
								<span id="_customerIdtCustomerName"></span>
							</div>
						</div>
					</div>
					<!-- 3行end -->
					<!-- 4行start -->
					<div class=" row-inline" >
						<div class=''>
							<label class='row-label' for='contractName' >客户名称：</label>
							<div class='row-span-twoColumn'>
								<span id="_customerInfoName"></span>
							</div>
						</div>
					</div>
					<div class=" row-inline" >
						<div class=''>
							<label class='row-label' for='contractName' >联&nbsp;&nbsp;系&nbsp;&nbsp;人：</label>
							<div class='row-span'>
								<span id="_customerContacts"></span>
							</div>
						</div>
					</div>
					<div class=" row-inline" >
						<div class=''>
							<label class='row-label' for='customerContactsPhone' >手机号码：</label>
							<div class='row-span'>
								<span id="_customerContactsPhone"></span>
							</div>
						</div>
					</div>
					<!-- 4行end -->
					<br class='float-clear' />
					<!-- 分割行start -->
					<div class=" row-inline" >
						<hr size=1 class="dashed-inline">
					</div>
					<!-- 分割行end -->
					<!-- 小标题start -->
					<div class=" row-inline" >
						<h4 class="form-section-title">合同订单状态</h4>
					</div>	
			
					<!-- 小标题end -->
					<!-- 5行start -->	
					<div class=" row-inline" >
						<div class='table-tile-width'>
							<table id="_contract_order"class="table  table-bordered">
							  <thead>
								<tr style="background-color: #f3f3f3;color: #888888;">
								  <th style="width:18%;">订单名称</th>
								  <th style="width:18%;">订单编号</th>
								  <th style="width:8%;">采购员</th>
								  <th style="width:10%;">订单状态</th>
								  <th style="width:10%;">公司库房</th>
								  <th style="width:10%;">客户到货</th>
								  <th style="width:10%;">发货时间</th>
								</tr>
							  </thead>
							  <tbody>
								<c:forEach var="orderModel" items="${orderModel}" varStatus="status">
									<tr>
										<td class="sino_table_label">${orderModel.orderName }</td>
										<td class="sino_table_label">${orderModel.orderCode }</td>
										<td class="sino_table_label">${orderModel.creator }</td>
										<td class="sino_table_label" tdId="${orderModel.orderStatus }"></td>
										<c:if test="${orderModel.wareHouseStatus == 'N'}">
											<td class="sino_table_label">没到货</td>
										</c:if>
										<c:if test="${orderModel.wareHouseStatus == 'A'}">
											<td class="sino_table_label">全部到货</td>
										</c:if>
										<c:if test="${orderModel.wareHouseStatus == 'S'}">
											<td class="sino_table_label">部分到货</td>
										</c:if>
										<c:if test="${orderModel.wareHouseStatus == ''||orderModel.wareHouseStatus == null}">
											<td class="sino_table_label">--</td>
										</c:if>
										<td class="sino_table_label">${orderModel.arrivalStatus }</td>
										<td class="sino_table_label">${orderModel.arrivalTime }</td>
									</tr>
								</c:forEach>
							  </tbody>
							</table>
						</div>
					</div>
					<!-- 5行end -->	
					<br class='float-clear' />
					<!-- 分割行start -->
					<div class=" row-inline" >
						<hr size=1 class="dashed-inline">
					</div>
					<!-- 分割行end -->
					<!-- 小标题start -->
					<div class=" row-inline" >
						<h4 class="form-section-title">合同产品下单情况</h4>
					</div>	
						<div class=" row-inline" >
						<div class='table-tile-width'>
							<table id="_contract_order_product"class="table  table-bordered">
							  <thead>
								<tr style="background-color: #f3f3f3;color: #888888;">
								  <th style="width:10%;">产品类型</th>
								  <th style="width:10%;">厂商简称</th>
								  <th style="width:15%;">产品型号</th>
								  <th style="width:10%;">合同产品数</th>
								  <th style="width:20%;">订单名称</th>
								  <th style="width:10%;">订单下单数</th>
								</tr>
							  </thead>
							  <tbody>
								<c:forEach var="product" items="${product}" varStatus="status">
									<tr clas="_product_list" id="tr_${status.index }">
										<td class="sino_table_label" rowspan="1">${product.typeName }</td>
										<td class="sino_table_label" rowspan="1">${product.partnerName }</td>
										<td class="sino_table_label" rowspan="1">${product.pName }</td>
										<td class="sino_table_label" rowspan="1">${product.contractQua }</td>
										<td class="sino_table_label">${product.oName }</td>
										<td class="sino_table_label">${product.orderQua }</td>
									</tr>
								</c:forEach>
							  </tbody>
							</table>
						</div>
					</div>
					<!-- 5行end -->	
					<br class='float-clear' />
				</form>
				<!-- END FORM-->  
				<div class="form-actions">
				<div style='text-align:center;'>
					<button id='_sino_eoss_sales_contract_order_back' type="button" class="btn">关闭</button>
				</div>		
			</div>
		<script language="javascript">
			seajs.use('js/page/sales/contract/contractOrderDetail',function(contractOrderDetail){
				contractOrderDetail.init();
			});    
		</script>
	</body>
	</html>