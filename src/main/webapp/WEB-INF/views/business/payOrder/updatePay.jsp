<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ include file="/common/global.jsp" %>
 <html>
  <head>
    <title>商务采购</title>
    <%@ include file="/common/include-base-boostrap-styles.jsp" %>
	<link rel="stylesheet" href="${ctx}/skin/default/room.css" type="text/css" />
	<link href="${ctx}/skin/default/sales/sale.css" rel="stylesheet"/>
	<link href="${ctx}/js/plugins/select2/css/select2.css" rel="stylesheet"/>
	<link rel="stylesheet" href="${ctx}/js/plugins/bootstrap/css/bootstrap-datetimepicker.min.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/sinobridge/pink/sino_form_grid.css" type="text/css" />
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formSelect.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formSearchTip.css" type="text/css" ></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formstyle.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/dialog.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/tip.css" type="text/css" ></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/jquery-ui-1.8.17.custom.css" type="text/css" ></link>
	<link rel="stylesheet" href="${ctx}/skin/default/editgrid/editgrid.css" type="text/css" />


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
.selectDiv .uicSelectInp {
	    background: none repeat scroll 0 0 #fff;
	    border: 1px solid #ccc;
	    cursor: pointer;
	    height: 14px;
	    position: relative;
	    text-align: left;
	    vertical-align: middle;
	}
	.selectDiv a.uicSelectMore {
	    background: url("${ctx}/js/plugins/uic/style/excellenceblue/uic/images/ultraselect/ms_ico2.png") no-repeat scroll 1px 0 rgba(0, 0, 0, 0);
	    display: block;
	    height: 23px;
	    position: absolute;
	    right: 0;
	    top: 0;
	    width: 25px;
	    margin-right:-3px;
	 }
		
	</style>
		<script src="${ctx}/js/plugins/seajs/sea.js" id="seajsnode" type="text/javascript"></script>
	<script src="${ctx}/js/plugins/seajs/config.js" type="text/javascript"></script>
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
				<i class="icon-reorder"></i>&nbsp;&nbsp;&nbsp;修改付款申请
			    <span class="tright" style="color:#f4606c">付款申请名称：${model.payApplyName}</span>
			</div>
		</div>
	</div>	
	<!--表单、底部按钮 -->
	<div class="portlet-body form">
   	<form  id="_sino_eoss_cuotomer_addform" class='form-horizontal' method="post" action="">
    <input type="hidden" id="_eoss_payment_id" value="${model.id}" name="id">
  	<input type="hidden" id="taskId" name="taskId" value="${taskId}" >
	<input type="hidden" id="procInstId" name="procInstId" value="${procInstId}" >
	<input type="hidden" id="flowStep" name="flowStep" value="${flowStep}" >
	<input type="hidden" id="payApplyName" name="payApplyName" value="${model.payApplyName}" >
	<!--  隐藏的tableData初始化数据-->
	<input type="hidden" id="_tableGridData" value="" name="tableGridData"/>
	<input type="hidden" id="processInstanceId" name="processInstanceId" value="${model.processInstanceId}" >
    <div class="handprocess_order" id="SheetDiv">
	<br>
	<br>
	<h3>付款申请信息</h3>
	<ul class="clearfix">
		    <li id="field_userName" class="li_form"  style="width: 400px;">
				<label class="editableLabel"><span style="color:red;">*</span>供应商类型：</label>
				<div id='_supplierType'>
					<input type="hidden" name="supplierType" id="_supplierTypeId" class="row-input" value="${model.supplierName.supplierType}"/>
				</div>
			</li>
			<li id="field_userName" class="li_form"  style="width: 400px;">
				<label id="suppliers" class="editableLabel"><span style="color:red;">*</span>供应商名称：</label>
				<input type="text" id='supplierInfoSelect'  class="row-input"  value="" />
				<input type="hidden" id='_supplierInfoSelect' name="supplierId"  class="row-input"  value="${model.supplierName.id }" />
				<input type="hidden" id='suName'  class="row-input"  value="${model.supplierName.shortName} "/>
			</li>
	   </ul>
	   <ul class="clearfix">
	   		<li id="field_userName" class="li_form" style="width:400px" >
				<label class="editableLabel2">开户银行：</label><span id='bankNameSupplier'>${model.supplierName.bankName}</span>
				<div  id='bankNameSupplier'></div>
			</li>
		    <li id="field_userName" class="li_form"  style="width:400px">
				<label class="editableLabel2" >银行账号：</label><span id='BankAccount'>${model.supplierName.bankAccount}</span>
				<div  id='BankAccount'></div>
			</li>
		</ul>
		<ul class="clearfix" >
				<li id="field_userName" class="li_form" style="width: 400px;">
					<label class="editableLabel"><span style="color:red;">*</span>科目类型：</label>
					<div id="_coursesType">
			           <input type="hidden" name="coursesType" id="_coursesTypeId" value="${model.coursesType}"/>
				    </div>
				</li>
				<li id="field_userName" class="li_form" style="width: 400px;">
				<label class="editableLabel"><span style="color:red;">*</span>付款时间：</label>
				    <div style="position: relative;" class="input-append date">
						 <input data-format="yyyy-MM-dd"  name="planPayDate"  type="text"  style="width:150px" value="${model.planPayDate}"></input>
						    <span class="add-on">
						      <i data-time-icon="icon-time" data-date-icon="icon-calendar">
						      </i>
							</span>
					  </div>
			     </li>
		    </ul>
		    <ul class="clearfix" >
				<%-- <li id="field_userName" class="li_form" style="width: 400px;">
					<label for="orderType" class="editableLabel"><span style="color:red;">*</span>发票类型：</label>
						<div id="_eoss_business_invoiceType">
				           <input type="hidden" name="invoiceType" id="_eoss_business_invoiceTypeId" value="${invoiceType}"/>
					    </div>
			    </li> --%>
			    <li id="field_userName" class="li_form" style="width: 400px;">
					<label for="orderType" class="editableLabel"><span style="color:red;">*</span>税率：</label>
						<div id="_eoss_taxType">
				           <input type="hidden" name="taxType" id="_eoss_business_taxTypeIds" value=""/>
					    </div>
			    </li>
			  </ul>
			  <div style="height: 35px;">
			<div style="float:left;margin-left:10px;">
			            <div style="float:left; padding-top: 5px;font-weight:bold;">结算币种：</div>
				         <ul class="nav nav-pills" style="float:left;margin-bottom: 0;">
					      <li class="active">
					        <a href="#" class="selectCurrency"  id="cny">人民币</a>
					      </li>
					      <li><a href="#" class="selectCurrency"  id="usd" >美元</a></li>
					    </ul>
			</div>
			<input type="hidden" id="selectCurrency" name="currency"  value="cny"> 
			<div Style="margin-left: 863px;width: 88px;margin-bottom: 5px">
				     <a id='selectOrder' role="button" href="#myModal1" class="btn btn-success"><i class="icon-plus"></i>选择</a>
			</div>
			</div>
		   			<input type='hidden' name='tableData' id='tableData' />

		    <div id="alertMsg"></div>
			<table id="pay_order" cellpadding="1" cellspacing="1" border="5" class="table table-striped table-bordered table-hover">
				
				<thead>
					<tr>
						<td width="20%">订单编号</td>
					    <td width="15%">供方简称</td>
					    <td width="12%">订单金额</td>
						<td width="12%">未计划金额</td>
						<td width="15%">付款金额
							<c:if test="${model.currency=='usd' }"><span class="chgcurrency">(美元)</span></c:if>
							<c:if test="${model.currency=='rmb' }"><span class="chgcurrency">(人民币)</span></c:if>
						</td>
						<td width="12%">账期天数</td>
						<td width="10%">操作</td>
					</tr>
				</thead>
				<tbody>
					 <c:forEach var="plan" items="${listMap}" varStatus="status">
								<tr id="tr_${ status.index + 1}">
									<td id="orderCode${ status.index + 1}" name="orderCode" tdValue="${plan.orderCode}">${plan.orderCode}</td>
									<td id="supplierShortName${ status.index + 1}" name="supplierShortName" tdValue="${plan.supplierShortName}">${plan.supplierShortName}</td>
									<td id ='orderAmount${ status.index + 1}' name='orderAmount'  tdValue='${plan.orderAmount}'>${plan.orderAmount}</td>
									<td id="payAmount${ status.index + 1}" name="payAmount" tdValue="${plan.payAmount}">${plan.payAmount}</td>
									<td ><input class="amount" num='${ status.index + 1}' type="text" style="width:90%" id='amount${ status.index + 1}' name="amount" value="${plan.amount }"/></td>
									<td ><input class="credit" num='${ status.index + 1}' type="text" style="width:90%" id='credit${ status.index + 1}' name="credit" value="${plan.credit }"/></td>
									<td id ='orderId${ status.index + 1}' style='display:none'  name='orderId' tdValue='${plan.orderId }'></td>
									<td id ='supplierId${ status.index + 1}' style='display:none'  name='supplierId'  tdValue='${plan.supplierId }'></td>
									<td class="sino_table_label"><a num='${ status.index + 1}'  id='_remove_product_${ status.index + 1}' style="width:60%" class='btn btn-danger _remove_product'><i style="width:100%;font-size: 12px;">删除</i></a></td>
								</tr>
					</c:forEach>
				</tbody>
			</table>
			<input type='hidden' name="product_add_t" id="product_add_t" />
				<label Style="float:right; margin-right: 32px;"> 付款金额<span class="chgcurrency">(人民币)</span>:<span id='tatol'>${model.payAmonut}</span></label>
				<input type="hidden" id="payAmount" name="payAmount"  value="${model.payAmonut}"/>
				<input type="hidden" id = "currency" value="${model.currency }"/>
		   <div style="clear:both;"></div>
		    <ul class="clearfix" >
			    <li id="field_userName" class="li_form" style="width: 700px;" >
					<label  class="editableLabel">用途：</label>
			        	<textarea name="remark" id="remark" rows="3" cols="30" style="width:580px" value="${model.remark }"placeholder='请输入用途' required data-content="请输入用途" >${model.remark }</textarea>
				</li>
		    </ul>
	</div>
	</form>
		<!-- editTable -->
       <div id="dailogs1" class="modal hide fade" role="dialog" tabIndex="-1" style="width:780px;height: 510px;">
		   <div class="modal-header">
		         <a type="button" class="close" data-dismiss="modal" aria-hidden="true">×</a>
		         <h3 id="dtitle"></h3>
  		   </div>
		    <div class="modal-body" style="width:750px;">
			     <div id="dialogbody" ></div>
		    </div>
		    <div id="bottom_button" class="modal-footer" style="text-align: center;">
        		<input id="ok_Order_Add" type="button" value="确定" class="btn btn-success"/>
    		</div>
        </div>
	<!--按钮组-->
    		<div id="bottom_button" class="handprocess_btngroup">
        		<input id="_sino_eoss_pay_reSubmit" type="button" value="重新提交"   class="btn btn-success">
        		<input id="_sino_eoss_pay_end" type="button" value="放弃提交并删除"   class="btn">
        		<input id="no_back" type="button" value="取消" class="btn">
    		</div>
    		
    </div>
    </div>
<script type="text/javascript">
seajs.use('js/page/business/payOrder/updatePay', function(updatePay){
	updatePay.init();
});    
</script>

</body>
</html>