<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <%@ include file="/common/global.jsp" %>
<head>
    <title>商务采购</title>
    <%@ include file="/common/include-base-boostrap-styles.jsp" %>
	<link href="${ctx}/skin/default/sales/sale.css" rel="stylesheet"/>
	<link href="${ctx}/js/plugins/select2/css/select2.css" rel="stylesheet"/>
	<link rel="stylesheet" href="${ctx}/js/plugins/bootstrap/css/bootstrap-datetimepicker.min.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/sinobridge/pink/sino_form_grid.css" type="text/css" />
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formSelect.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formSearchTip.css" type="text/css" ></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formstyle.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/dialog.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/tip.css" type="text/css" ></link>
	
<style type="text/css">
	.row-input{
		width: 180px;
		height:28px"
	}

.row-input-oneColumn {
    width: 480px;
}
.select2-container .select2-choice {
    border-radius: 4px;
    color: #555555;
    font-size: 14px;
    height: 20px;
    padding: 4px 6px;
    width: 180px;
    background-color: #ffffff;
    border: 1px solid #cccccc;
    box-shadow: 0 1px 1px rgba(0, 0, 0, 0.075) inset;
    transition: border 0.2s linear 0s, box-shadow 0.2s linear 0s;
    display: inline-block;
    margin-bottom: 0;
    vertical-align: middle;
    background-image: none;
}
.handprocess_order li.li_form {
    display: inline-block;
    float: left;
    font-size: 14px;
    line-height: 25px;
    padding: 3px 0;
    width: 300px;
}
body {
    font-size: 12px;
}
.table {
    margin-bottom: 20px;
    width: 100%;
    margin-left: 0px;
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
    color: #eeb422;
}

.handprocess_order .li_form label.editableLabel2 {
    color: #000000;
    margin-bottom: 0;
    margin-top: 3px;
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
				<i class="icon-reorder"></i>&nbsp;&nbsp;&nbsp;添加订单信息
			    <!-- <span class="tright" >订单编号：<span id="Code"></span></span> -->
			</div>
			<div class="tools">
				<!--a href="javascript:;" class="reload"><i class="icon-repeat"></i>返回</a-->
			</div>
		</div>
	</div>	
	<!--表单、底部按钮 -->
	<div class="portlet-body form">
	<form  id="_sino_eoss_cuotomer_addform" class='form-horizontal' method="post"/>
	<!--隐藏字段start -->
	<input type='hidden' id='_is_submit_product'  value="1"/>
	<input type="hidden" id="_eoss_customer_id" value="${model.id }" name="id"/>
	<input type="hidden" id="_tableGridData" value="" name="tableGridData"/>
	<input type="hidden" id="_contract" value="" name="_contract"/>
	<input type="hidden" id="_interPurchas" value="" name="interPurchas"/>
	<input type='hidden' name='orderStatus' id='orderStatus' value="${model.orderStatus}"/>
	<div class="handprocess_order" id="SheetDiv">
	<br>
		<h3>订单基本信息</h3>
		
		<ul class="clearfix" >
		    <li id="field_userName" class="li_form" >
				<label for="orderType" class="editableLabel"><span style="color:red;">*</span>订单类型：</label>
				<div id="_eoss_business_ordertype">
		            <input type="hidden" name="orderType" id="_eoss_business_ordertypeId" value="${model.orderType}"/>
		            <input type="hidden" id="Type" value=""/>
			    </div>
			</li>
			<li id="field_userName" class="li_form" style="width:600px">
				<label class="editableLabel"><span style="color:red;">*</span>订单名称：</label>
				<input type="text" id="orderName" name="orderName" class="row-input-oneColumn" value=""  placeholder='请输入订单名称' required data-content="请输入订单名称"/>
			</li>
		</ul>
		<ul class="clearfix" >
			<li id="field_userName" class="li_form" >
				<label for="orderType" class="editableLabel"><span style="color:red;">*</span>采购分类：</label>
				<div id="_eoss_business_purchaseType">
		            <input type="hidden" name="purchaseType" id="_eoss_business_purchaseTypeId" value="${model.purchaseType}"/>
			    </div>
			</li>
			<li id="field_userName" class="li_form" >
				<label for="orderType" class="editableLabel"><span style="color:red;">*</span>发票类型：</label>
				<div id="_eoss_business_invoiceType">
		           <input type="hidden" name="invoiceType" id="_eoss_business_invoiceTypeId" value="${model.invoiceType}"/>
			    </div>
			</li>
			<li id="field_userName" class="li_form" >
				<label class="editableLabel"><span style="color:red;">*</span>服务期(月)：</label>
				<input type="text" id="serviceDate" name="serviceDate"  class="row-input" value="12"  placeholder='请输入服务期（月）' required data-content="请输入服务期（月）" style="margin-left: 0px;"/>
			</li>
		</ul>
		<ul class="clearfix" >
			<!-- <li id="field_userName" class="li_form" >
				<label for="expectedDeliveryTime" class="editableLabel"><span style="color:red;">*</span>到货时间：</label>
				    <div style="position: relative;" class="input-append date">
						 <input data-format="yyyy-MM-dd"  name="expectedDeliveryTime" id="expectedDeliveryTime" type="text"  style="width:150px"></input>
						    <span class="add-on">
						      <i data-time-icon="icon-time" data-date-icon="icon-calendar">
						      </i>
							</span>
					  </div>
			</li> -->
			<li id="field_userName" class="li_form" >
				<label for="orderType" class="editableLabel"><span style="color:red;">*</span>到货地点：</label>
				<div id="_business_deliveryAddress">
		            <input type="hidden" name="deliveryAddress" id="_business_deliveryAddressId"  value="${model.deliveryAddress}"/>
			    </div>
			</li>
			<li id="field_userName" class="li_form" >
				<label for="orderType" class="editableLabel"><span style="color:red;">*</span>付款方式：</label>
				<div id="_eoss_business_paymentMode">
		            <input type="hidden" name="paymentMode" id="_eoss_business_paymentModeId"  value="${model.paymentMode}"/>
			    </div>
			</li>
			<li id="field_userName" class="li_form" >
				<label for="orderType" class="editableLabel"><span style="color:red;">*</span>结算币种：</label>
					<div id="_accountCurrency">
						<input type="hidden" name="accountCurrency" id="_eoss_business_accountCurrency"  value="${model.accountCurrency}"/>
					</div> 
			</li>
		</ul>
		<%-- <ul class="clearfix" >
			<li id="field_userName" class="li_form" >
				<label for="orderType" class="editableLabel"><span style="color:red;">*</span>利润类型：</label>
				<div id="_eoss_orderProfits">
		            <input type="hidden" name="orderProfits" id="orderProfits"  value="${model.orderProfits}"/>
			    </div>
			</li>
			<li id="field_userName" class="li_form" >
				<label  class="editableLabel"><span style="color:red;">*</span>预估值：</label>
		            <input type="text" id="profitsValue" name="profitsValue" class="row-input" value=""  placeholder='请输入预估值' required data-content="请输入预估值"/>
			</li>
		</ul> --%>
		<div style="clear:both;"></div>
		
		<div class=" row-inline" >
				<hr size=1 class="dashed-inline">
		</div>
		
		<h3>供应商信息（乙方）</h3>
		<ul class="clearfix">
		    <li id="field_userName" class="li_form">
				<label class="editableLabel">供应商类型：</label>
				<div id='_supplierType'></div>
			</li>
			<li id="field_userName" class="li_form" >
				<label id="suppliers" class="editableLabel"><span style="color:red;">*</span>供应商名称：</label>
				<input type="text" id='supplierInfoSelect' name="supplierId"  class="row-input"  value="" />
				<input type="hidden" id='_supplierInfoSelect'  class="row-input"  value="" />
			</li>
			 <li id="field_userName" class="li_form" >
				<label class="editableLabel"><span style="color:red;">*</span>联系人：</label>
				<input type="text" id='_contactName' name='contactId' class="row-input"  value=""/>
			</li>
			<li id="field_userName" class="li_form" style="width:600px">
				<label class="editableLabel"><span style="color:red;">*</span>订单编号：</label>
				<input type="text" id="orderCode" name="orderCode" class="row-input-oneColumn" value="" />
			</li>
		</ul>
		<ul class="clearfix">
			<li id="field_userName" class="li_form" >
				<label  class="editableLabel2" >供应商编号：</label><span id='supplierCode'></span>
				<input type="hidden" id="supplierCode"  value=""/>
				<div  id='supplierCode'></div>
			</li>
			<li id="field_userName" class="li_form" style="width:600px" >
				<label class="editableLabel2" >地址：</label><span id='address'></span>
				<!-- <input type="text" id="address" class="row-input-oneColumn" readonly="readonly" value=""/> -->
				<div  id='address'></div>
			</li>
		</ul>
		<ul class="clearfix">
		    <li id="field_userName" class="li_form">
				<label class="editableLabel2">邮政编码：</label><span id='zipCode'></span>
				<!-- <input type="text" id="zipCode" class="row-input" readonly="readonly" value=""/> -->
				<div  id='zipCode'></div>
			</li>
			<li id="field_userName" class="li_form" style="width:600px" >
				<label class="editableLabel2">开户银行：</label><span id='bankNameSupplier'></span>
				<!-- <input type="text" id="bankNameSupplier" class="row-input-oneColumn" readonly="readonly" value=""/> -->
				<div  id='bankNameSupplier'></div>
			</li>
		</ul>
		<ul class="clearfix">
		    <li id="field_userName" class="li_form" >
				<label class="editableLabel2" >银行账号：</label><span id='BankAccount'></span>
				<!-- <input type="text" id="BankAccount" class="row-input" readonly="readonly" value=""/> -->
				<div  id='BankAccount'></div>
			</li>
		    <li id="field_userName" class="li_form" >
				<label class="editableLabel2" >传真：</label><span id='fax'></span>
				<!-- <input type="text" id="fax" class="row-input" readonly="readonly" value=""/> -->
				<div  id='fax'></div>
			</li>
		   
			<li id="field_userName" class="li_form">
				<label class="editableLabel2" >联系人电话：</label><span id='_contactPhone'></span>
				<!-- <input type="text" id="_contactPhone" class="row-input" readonly="readonly" value=""/> -->
				<div id='_contactPhone'></div>
			</li>
		</ul>
		<ul class="clearfix">
		    <li id="field_userName" class="li_form">
				<label class="editableLabel2">联系人手机号：</label><span id='_contactTelPhone'></span>
				<!-- <input type="text" id="_contactTelPhone" class="row-input" readonly="readonly" value=""/> -->
				<div  id='_contactTelPhone'></div>
			</li>
		</ul>
		<div style="clear:both;"></div>
		<div class=" row-inline" >
				<hr size=1 class="dashed-inline">
		</div>
		<h3>利润预估（请至少填写一个！）</h3>
		<ul class="clearfix">
			<li id="field_userName" class="li_form" >
				<label class="editableLabel">厂商折扣(%)：</label>
				<input type="text" id="partnerPV" name="partnerPV" class="row-input" value=""/>
			</li>
			<li id="field_userName" class="li_form" style="width: 260px;">
				<label class="editableLabel" style="width: 60px;">利润：</label>
				<input type="text" id="profitsValue" name="profitsValue" class="row-input" value=""/>
			</li>
			<li id="field_userName" class="li_form" style="width: 350px;" >
				<label class="editableLabel" style="width: 110px;" >客户利润率(%)：</label>
				<input type="text" id="customerPV" name="customerPV" class="row-input" value=""/>
			</li>
		</ul>
		<div style="clear:both;"></div>
		<div class=" row-inline" >
				<hr size=1 class="dashed-inline">
		</div>
		
		<h3>公司信息（甲方）</h3>
		<ul class="clearfix">
			<li id="field_userName" class="li_form" style="width:600px">
				<label class="editableLabel2">公司名称：</label>
				<span>${requestScope.companyName }</span>
				<input type="hidden" id="companyName" name="companyName"   value="${requestScope.companyName }"/>
			</li>
			<li id="field_userName" class="li_form" >
				<label class="editableLabel2" >开户银行：</label>
				<span>${requestScope.bankName }</span>
				<input type="hidden" id="bankName" name="bankName" value="${requestScope.bankName }"/>
			</li>
		</ul>
		<ul class="clearfix">
		<li id="field_userName" class="li_form" style="width:600px">
				<label class="editableLabel2">公司地址：</label>
				<span>${requestScope.address }</span>
				<input type="hidden" id="companyAddress" name="companyAddress" value="${requestScope.address }"/>
			</li>
			
			<li id="field_userName" class="li_form">
				<label class="editableLabel2">银行账号：</label>
				<span>${requestScope.account }</span>
				<input type="hidden" id="bankAccount" name="bankAccount"  value="${requestScope.account }"/>
			</li>
		</ul>
        <div style="clear:both;"></div>
        
        <div class=" row-inline" >
				<hr size=1 class="dashed-inline">
		</div>
		
<!-- 		<h3>供应商内部合同信息</h3>
        <ul class="clearfix" >
			<li id="field_userName" class="li_form" style="width: 350px;">
				<label for="orderCode" class="editableLabel"><span class="red">*</span>订单号（PO#）：</label>
				<input type="text" id="orderCode" name="orderCode" class="row-input" style="border: none; readonly:readonly"   value=""  placeholder='订单编号自动生成'/>
			</li>
			<li id="field_userName" class="li_form" style="width: 350px;">
				<label for="orderName" class="editableLabel"><span class="red">*</span>SO#：</label>
				<input type="text" id="orderName" name="orderName" class="row-input" value=""  placeholder='请输入订单名称' required data-content="请输入订单名称"/>
			</li>
		</ul>
		 <ul class="clearfix" >
			<li id="field_userName" class="li_form" style="width: 350px;">
				<label for="orderCode" class="editableLabel"><span class="red">*</span>DealID：</label>
				<input type="text" id="orderCode" name="orderCode" class="row-input" style="border: none; readonly:readonly"   value=""  placeholder='订单编号自动生成'/>
			</li>
			<li id="field_userName" class="li_form" style="width: 350px;">
				<label for="orderName" class="editableLabel"><span class="red">*</span>SO#：</label>
				<input type="text" id="orderName" name="orderName" class="row-input" value=""  placeholder='请输入订单名称' required data-content="请输入订单名称"/>
			</li>
		</ul> -->

		<h3>产品添加</h3>
				<div>
				     	<a id='selectContract' role="button" href="#myModal1" class="btn btn-success" style="margin-left: 10px;width: 50px;margin-bottom: 5px"><i class="icon-plus" style="width:50px;"></i>选择</a>
						<a id='remove_product'style="margin-left: 665px;width: 70px;margin-bottom: 5px" class='btn btn-danger _remove_product'><i >删除勾选</i></a>
						<a id='remove_product_else' style="margin-right: 10px;width: 70px;margin-bottom: 5px" class='btn btn-danger _remove_product'><i >删除其他</i></a>
				</div>
			<input type='hidden' name='tableData' id='tableData' />

		    <div id="alertMsg"></div>
		    <!-- <table id="product_add" border="0" style="width: 100%" class="table sino_table_body"> -->
			<table id="product_add" cellpadding="1" cellspacing="1" border="5" class="table table-striped table-bordered table-hover">
				
				<thead>
					<tr>
					<c:if test="${model.orderType!='4'}">
					    <td width="15%">合同名称</td>
					 </c:if> 
					 <c:if test="${model.orderType=='4'}">
					    <td width="15%">内采名称</td>
					 </c:if>     
					    <td width="8%">产品类型</td>
						<td width="8%">厂商简称</td>
					    <td width="9%">产品型号</td>
						<td width="7%">数量</td>
						<td width="8%">单价</td>
						<td width="8%">总价</td>
						<!-- <td width="10%">服务类型</td>
						<td width="10%">服务开始时间</td>
						<td width="10%">服务结束时间</td> -->
						<td width="4%"><input id="checkall" type="checkbox" value=0 /></td>
					</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
			<input type='hidden' name="product_add_t" id="product_add_t" />
				<label Style="float:right; margin-right: 32px;"> 订单金额：<span id='tatol' name='tatol'></span>元</label>
				<input type="hidden" id="orderAmount" name="orderAmount"  value="${model.orderAmount}"/>
			<!--返点使用  -->
			<ul class="clearfix" style="position: relative;">
			   <li id="field_userName" class="li_form" style="width: 400px;">
				    <label style="color: #eeb422; width:110px;"><span style="color:red;">*</span>是否用返点数：</label>
				    <input type="radio" id="check_yes" name="spotChange" value="1" style="margin-bottom:5px;"/>&nbsp;是&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				    <input type="radio" id="check_no" name="spotChange" value="0"checked="checked" style="margin-bottom:5px;"/>&nbsp;否&nbsp;&nbsp;&nbsp;
			   </li>
			</ul>
			<!-- <ul class="_is_hidden" style="display:none;">	   
				<li class="li_form" style="width:600px">
			         <label class="editableLabel2" >可用返点数：</label>
			         <span id="nowSpotNum" name="nowSpotNum">0</span>
			   </li>
			   <li class="li_form" style="width:600px">
			         <label class="editableLabel" ><span style="color:red;">*</span>使用点数：</label>
			         <input type="text" id="spotNum" name="spotNum" value="0"/>
			   </li>
			 </ul> -->
			 <table id="spot_order" class="table table-striped table-bordered table-hover _is_hidden" style="display:none;" >
			 	<thead>
					<tr>
						<td width="8%">选择</td>
					    <td width="15%">返点厂商</td>
						<td width="8%">可用返点数</td>
					    <td width="9%">使用点数</td>
					</tr>
				</thead>
			 </table>
			 <input type="hidden" id="spotNum" name="spotNum" value="0"/>
			 <input type="hidden" id="spotSupplier" name="spotSupplier" value=""/>
			 <input type="hidden" id="spotId" name="spotId" value=""/>
            <div style="clear:both;"></div>
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
        		<input id="ok_Contract_Add" type="button" value="确定" class="btn btn-success"/>
    		</div>
        </div>
	  
	<!--按钮组-->
    		<div id="bottom_button" class="handprocess_btngroup">
        		<input id="ok_Add" type="button" value="保存"  class="btn btn-success">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        		<input id="sp_Add" type="button" value="提交"  class="btn btn-success" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        		<input id="no_back" type="button" value="取消" class="btn">
    		</div>
     </div>
</div>
</div>

 <script language="javascript">
 var form = ${form}; 
 seajs.use('js/page/business/order/addOrder',function(order_addOrder){
	 order_addOrder.init();
    });
</script>
</body>
</html>