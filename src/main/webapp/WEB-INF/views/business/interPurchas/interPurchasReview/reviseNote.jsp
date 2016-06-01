<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ include file="/common/global.jsp" %>
<head>
     <title>内部采购</title>
    <%@ include file="/common/include-base-boostrap-styles.jsp" %>
	<link rel="stylesheet" href="${ctx}/skin/default/room.css" type="text/css" />
	<link href="${ctx}/skin/default/sales/sale.css" rel="stylesheet"/>
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
<br />
<div class="salecontent">
		<div class="top">
			<div class="caption">
				<i class="icon-reorder"></i>&nbsp;&nbsp;&nbsp;修改办公设备采购
			    <span class="tright" >内采编号：${model.purchasCode }</span>
			</div>
			<div class="tools">
				<!--a href="javascript:;" class="reload"><i class="icon-repeat"></i>返回</a-->
			</div>
		</div>
	</div>	
	<!--表单、底部按钮 -->
	<div class="portlet-body form">
	<form  id="_sino_eoss_cuotomer_addform" class='form-horizontal'  method="post">
	<!--  隐藏ID-->
	<input type="hidden" id="_eoss_customer_id" value="${model.id }" name="id"/>
	<!--  保存为草稿还是提交的标志位-->
	<input type='hidden' name='isSubmit' id='isSubmit' value=""/>
	<input type="hidden" id ="taskId" value="${taskId}" name="taskId" >
	<input type='hidden' name='purchasCode' id='purchasCode' value="${model.purchasCode }"/>
	<input type='hidden' name='processInstanceId' id='processInstanceId' value="${model.processInstanceId }"/>
	
	<div class="handprocess_order" id="SheetDiv">
		<h3 class="form-section-title">采购信息</h3>
		<ul class="clearfix" >
			<%-- <li id="field_userName" class="li_form" style="width: 320px;">
				<label  class="editableLabel"><span class="red">*</span>编号：</label>
				<input type="text" id="purchasCode" name="purchasCode" class="row-input" style="border: none; disabled="readonly" value="${model.purchasCode }"  placeholder='编号自动生成'/>
			</li> --%>
			<li id="field_userName" class="li_form" style="width: 320px;">
				<label class="editableLabel">申请单名称：</label>
				<input type="text" id="purchasName" name="purchasName" class="row-input" value="${model.purchasName }"  placeholder='请输入名称'/>
			</li>
			<li id="field_userName" class="li_form" style="width: 320px;">
				<label for="expectedDeliveryTime" class="editableLabel">期望到货时间：</label>
				    <div style="position: relative;" class="input-append date">
						 <input data-format="yyyy-MM-dd"  name="expectedDeliveryTime"  type="text"  style="width:150px" value="${model.expectedDeliveryTime}"></input>
						    <span class="add-on">
						      <i data-time-icon="icon-time" data-date-icon="icon-calendar">
						      </i>
							</span>
					  </div>
			</li>
		</ul>
			<li id="field_userName" class="li_form" style="width: 670px;margin-left:24px" >
				<label  class="editableLabel">用途：</label>
		        <textarea name="remark" id="remark" rows="3" cols="30" style="width:500px" value="">${model.remark }</textarea>
			</li>
		<div style="clear:both;"></div>
		
		<div class=" row-inline" >
				<hr size=1 class="dashed-inline">
		</div>
		
		<div class=" row-inline" >
				<h3 class="form-section-title">设备添加</h3>
				<div class="products_add_btn">
					<a id='products_add' role="button" class="btn btn-success"><i class="icon-plus"></i>添加</a>
				</div>
			</div>
			<!-- 小标题end -->
			<!-- 9行start -->	
			<div class=" row-inline" >
				<div class='table-tile-width'>
					<table id="products_table"class="table  table-bordered table-striped"  style="width: 1000px">
					  <thead>
					    <tr>
					      <th>序号</th>
					      <th>产品类型</th>
					      <th>产品厂商</th>
					      <th>产品型号</th>
					      <th>数量</th>
					      <th>操作</th>
					    </tr>
					  </thead>
</div>
					  <tbody>
					  <c:forEach var="product" items="${model.interPurchasProduct}" varStatus="status">
							<tr style='text-algin:center' id='tr_${ status.index + 1}'>
								<td >${ status.index + 1}<%-- <input id='_contract_product_id_${ status.index + 1}' name='contractProductIds' type='hidden'value='${product.id}'> --%></td>
								<td><span>${product.productTypeName }</span><input id='_product_Type_${ status.index + 1}' name='productTypes' type='hidden' value='${product.productType }'><input id='_product_Type_Name_${ status.index + 1}' name='productTypeNames' type='hidden' value='${product.productTypeName }'></td>
								<td><span>${product.productPartnerName }</span><input id='_product_Partner_${ status.index + 1}' name='productPartners' type='hidden' value='${product.productPartner }'><input id='_product_Partner_Name_${ status.index + 1}' name='productPartnerNames' type='hidden' value='${product.productPartnerName }'></td>
								<td><span>${product.productName}</span><input id='_product_No_${ status.index + 1}' name='productNos' type='hidden' value='${product.productNo }'><input id='_product_Name_${ status.index + 1}' name='productNames' type='hidden' value='${product.productName }'></td>
								<td><span>${product.quantity }</span><input id='_product_quantity_${ status.index + 1}' name='quantitys' type='hidden' value='${product.quantity }'></td>
								<td style='text-algin:center'><a serial_num='${ status.index + 1}' id='update_product_${ status.index + 1}'  class='btn btn-primary update_product'><i class='icon-pencil'></i>修改</a>&nbsp;&nbsp;&nbsp;<a serial_num='${ status.index + 1}' id='remove_product_${ status.index + 1}'  class='btn btn-danger remove_product'><i class='icon-remove'></i>删除</a></td>
							</tr>
						</c:forEach>
					  </tbody>
					</table>
				</div>
			</div>
		<div style="clear:both;"></div>
		
		<div class=" row-inline" >
				<hr size=1 class="dashed-inline">
		</div>
			
</form>
<!--设备添加弹出对话框 -------start-->
			 <div id="dailogs1" class="modal hide fade" role="dialog" tabIndex="-1" style="width:800px;height:460px">
		   		<div class="modal-header">
				   <h3 id="dtitle"></h3>
				   <div id="_product_alertMsg"></div>
		  		</div>
				<div class="modal-body" style="height:70%">
					<div id="dialogbody" ></div>
				</div>
				<div class="modal-footer">
					<button id="_do_cancel"  class="btn" data-dismiss="modal" aria-hidden="true" >取消</button >
					<a id="dsave"  class="btn btn-primary" >保存</a>
				</div>
			</div>
			<!--设备添加弹出对话框 -------end-->  		
	<!--按钮组-->
    		<div id="bottom_button" class="handprocess_btngroup">
        		<a id='sp_Add' role="button" class="btn btn-success"><i class="icon-ok"></i>从新提交</a>
        		<!-- <a id='no_Add' role="button" class="btn">放弃提交</a> -->
				<a id='no_back' role="button" class="btn">取消</a>
    		</div>
     </div>
</div>

 <script language="javascript">
 seajs.use('js/page/business/interPurchas/interPurchasReview/reviseNote',function(reviseNote){
	 reviseNote.init();
    });
</script>
</body>
</html>