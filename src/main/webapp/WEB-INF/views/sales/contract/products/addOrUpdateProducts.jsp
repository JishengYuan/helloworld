<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ include file="/common/global.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>添加设备</title>
    <link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formSelect.css" type="text/css"></link>
    <link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formTree.css" type="text/css"></link>
    <link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formstyle.css" type="text/css"></link>
    <style type="text/css">
		._sino_td_one{
			float:left;
			margin-right:3px;
			width:100px;
			height: 30px;
		}
		.one{
			margin-left:30px;
		}
		._sino_td_two{
			float:left;
			height: 30px;
		}
		._sino_table tr {
		    height: 50px;
		}
		.selectDIV_person a.uicUserMore {
		    background: url("${ctx}/js/plugins/uic/style/excellenceblue/uic/images/formImages/selecte/partner.png") no-repeat scroll 0 0 rgba(0, 0, 0, 0);
		}
		.icon_account {
		    background: url("${ctx}/js/plugins/uic/style/excellenceblue/uic/images/formImages/selecte/partner.png") no-repeat scroll 0 0 rgba(0, 0, 0, 0);
		}
		.treeMain .treeRight .treeRightSelect .treeRightBottom li span.nodeText s {
		    background: url("${ctx}/js/plugins/uic/style/excellenceblue/uic/images/formImages/selecte/partner.png") no-repeat scroll 0 0 rgba(0, 0, 0, 0);
		}
		.dropdown-menu{
			z-index:11111;
		}
	</style>
</head>
<body>
	   <form name="_sino_eoss_products_addform" id ="_sino_eoss_products_addform" class='form-horizontal'>
	   		<input type="hidden" id="_contract_productId" name="contractProductId" value="${model.id }">
			<input type="hidden" id="_productType" name="productType" value="${model.productType }">
			<input type="hidden" id="_sino_productTypeName" value="${model.productTypeName }">
			<input type="hidden" id="_partnerId" name="partnerId" value="${model.productPartner }">
			<input type="hidden" id="_sino_productPartnerName" value="${model.productPartnerName }">
	   		<input type="hidden" id="_productModelId" name="id" value="${model.productNo }">
	   		<input type="hidden" id="_sino_productModelName" value="${model.productName }">
	   <table class="_sino_table">
	   <tr style="display:none;">
	   	   <td class="_sino_td_one">产&nbsp品&nbsp&nbsp类&nbsp别:</td>
		   <td class="_sino_td_two" colspan="3"><div id="_sino_product_type"></div></td>
	   </tr>
	   	<tr>
		   <td class="_sino_td_one">&nbsp生&nbsp产&nbsp&nbsp厂&nbsp商:</td>
		   <td class="_sino_td_two">
				<div class="controls" id="_sino_partner_div" style="margin-left:0px;" >
					<input id="_sino_partner" type="text" value="${model.productPartnerName }" class="ultra-select-input3" data="0" data-content="请选择厂商" required/>
				</div>
			</td>
			<td class="_sino_td_one">&nbsp产&nbsp品&nbsp&nbsp型&nbsp号:</td>
		   <td class="_sino_td_two"><div id="_sino_product_model"></div></td>
		</tr>
		<tr>
		   <td class="_sino_td_one">产&nbsp品&nbsp&nbsp数&nbsp量:</td>
		   <td class="_sino_td_two"><input onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}if(this.value >5000){this.value=5000}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" type='number' min='1' id='_products_add_count' placeholder='请输入产品数量' value='<c:if test="${model.quantity == 0 }">1</c:if><c:if test="${model.quantity != 0 }">${model.quantity}</c:if>'/></td>
			<td class="_sino_td_one">&nbsp产&nbsp品&nbsp&nbsp单&nbsp价:</td>
		   <td class="_sino_td_two"><input type='text' id='_product_add_unitPrice' placeholder='请输入产品单价' value='${model.unitPrice }'/></td>
		</tr>
		<tr>
		   <td class="_sino_td_one">产&nbsp品&nbsp&nbsp总&nbsp价:</td>
		   <td class="_sino_td_two"><input type='text' id='_product_add_totalPrice' readonly="readonly" value='${model.totalPrice }'/></td>
		</tr>
		<tr>
		   <td class="_sino_td_one">&nbsp备&nbsp注:</td>
		   <td class="_sino_td_two">
		   <textarea placeholder='假如 型号为其他请输入产品备注' style="height:40px;" rows="2" cols="20" id='_product_add_remark'>${model.remark }</textarea>
		   </td>
		</tr>
		<tr>
		   <td class="_sino_td_one">是否附带服务:</td>
		   <td class="_sino_td_two" colspan="3">
		   	否<input type="radio" id="_server_time0" name="_server_time" value="0" checked>
		   	是<input type="radio" id="_server_time1" name="_server_time" value="1">
		   </td>
		</tr>
		<tr class="_service_time_tr" style="display:none;">
		   <td class="_sino_td_one">&nbsp开&nbsp始&nbsp&nbsp时&nbsp间:</td>
		   <td class="_sino_td_two">
		   		<div class='row-input'>
					<div class="input-append" id="startTime_div">
	  		    		<input data-format="yyyy-MM-dd" style="width:180px;" type="text" id="startTime" value="${param.startTime }"></input>
			    		<span class="add-on">
			    		<i data-time-icon="icon-time" data-date-icon="icon-calendar">
			   			</i>
					</span>
			    </div>
		   </td>
		   <td class="_sino_td_one one">结&nbsp束&nbsp&nbsp时&nbsp间:</td>
		   <td class="_sino_td_two">
		   		<div class='row-input'>
					<div class="input-append" id="endTime_div">
	  		    		<input data-format="yyyy-MM-dd" style="width:180px;" type="text" id="endTime" value="${param.endTime }"></input>
			    		<span class="add-on">
			    		<i data-time-icon="icon-time" data-date-icon="icon-calendar">
			   			</i>
					</span>
			    </div>
		   </td>
		</tr>
	   </table>
	   </form>
 <script language="javascript">
    seajs.use(['js/page/sales/contract/products/addOrUpdateProducts'],function(addOrUpdateProducts){
    	addOrUpdateProducts.init();
    });
</script>
</body>
</html>