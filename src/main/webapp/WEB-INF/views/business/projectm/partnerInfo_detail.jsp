<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ include file="/common/global.jsp" %>
 <html>
  <head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="${ctx}/js/plugins/jquery-easyui-1.3.3/themes/icon.css"/>
    <link rel="stylesheet" href="${ctx}/js/plugins/jquery-easyui-1.3.3/themes/bootstrap/easyui.css"/>  
    <link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formTree.css" type="text/css"></link>
    <link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formstyle.css" type="text/css"></link>
    <link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formSelect.css" type="text/css"></link>
    <link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formstyle.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/skin/default/room.css" type="text/css" />
	<link rel="stylesheet" href="${ctx}/js/plugins/bootstrap/css/bootstrap-datetimepicker.min.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/sinobridge/pink/sino_form_grid.css" type="text/css" />
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formSearchTip.css" type="text/css" ></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/dialog.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/tip.css" type="text/css" ></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/jquery-ui-1.8.17.custom.css" type="text/css" ></link>
	<link rel="stylesheet" href="${ctx}/skin/default/editgrid/editgrid.css" type="text/css" />
    <title>厂商信息表</title>
    <style type="text/css">
    	.datagrid{
    		margin-top:20px;
    	}
    	/*覆盖easyui选中样式*/
		.datagrid-row-selected{
		background:none;
		}
    </style>
  </head>
  <body>
	  <div class="page-header">
		  <h2>厂商信息
		  	<div class="pull-right">
			     <button id="_sino_partner_add_back" type="button" class="btn btn-default btn-lg">
				  <span class="icon-repeat"></span>
				  返回
				</button>
			</div>
		  </h2>
	  </div>
    <div id="alertMsg"></div>
	<div id="_sino_devicetype_uploadPic" ></div>
	
		<div style="padding:10px 0 10px 60px;text-align: center;margin-left:-50px;">
			<form id="_sino_partner_add_form" method="post" style="text-align: left;">
			<input type="hidden" id="_model_currency" name="currency" value="${model.currency}">
			<input type="hidden" id="_sino_productType" name="productType" value="${typeIds }">
			<input type="hidden" id="_partner_id" value="${model.id }" name="id">
			<input type="hidden" id="_sino_base_picUrl" value="${model.partnerLogo }" name="partnerLogo">
			<input type="hidden" id="_sino_brandLogo">
			<div class="handprocess_order" id="SheetDiv">
		    	 <ul class="clearfix" >
					<li id="field_userName" class="li_form" >
						<label for="orderCode" class="editableLabel"> 厂商编码:</label>
		    				${model.partnerCode }
		    		</li>
					<li id="field_userName" class="li_form" >
						<label for="orderCode" class="editableLabel"> 厂商名称:</label>
		    				${model.partnerFullName }
		    		</li>
					<li id="field_userName" class="li_form" >
						<label for="orderCode" class="editableLabel"> 英文名称:</label>
		    				${model.partnerEnCode }
		    		</li>
		    	</ul>
		    	 <ul class="clearfix" >
					<li id="field_userName" class="li_form"  >
						<label for="orderCode" class="editableLabel"> 厂商地址:</label>
		    				${model.registerAddress }
		    		</li>
		    		<li id="field_userName" class="li_form" >
						<label for="orderCode" class="editableLabel"> 厂商邮箱:</label>
		    				${model.partnerEmail }
		    		</li>
		    		<li id="field_userName" class="li_form" >
						<label for="orderCode" class="editableLabel"> 厂商网址:</label>
		    				${model.webUrl }
		    		</li>
		    	</ul>
		    	 <ul class="clearfix" >
					<li id="field_userName" class="li_form" >
						<label for="orderCode" class="editableLabel"> 联系地址:</label>	
		    				${model.address }
		    		</li>		
		    		<li id="field_userName" class="li_form" >
						<label for="orderCode" class="editableLabel"> 服务热线:</label>	
		    		</li>
		    		<li id="field_userName" class="li_form" >
						<label for="orderCode" class="editableLabel"> 厂商网址:</label>
		    				${model.hotLine }
		    		</li>
		    		
		    	</ul>
		    	<ul class="clearfix" >
		    	 	<li id="field_userName" class="li_form"  style="width: 800px;">
						<label for="orderCode" class="editableLabel" > 厂商类型:</label>
						<c:forEach var="type" items="${typeList}" varStatus="status">
							<span id="dmValueMeaning"></span>
							<input type="hidden" id="partnerType" value="${model.partnerType}" name="partnerType">
							<%-- <c:if test="${model.partnerType ==  type.dmValue}">${type.dmValueMeaning },</c:if> --%>
						</c:forEach>
		    		</li>
		        	<li id="field_userName" class="li_form" style="width: 900px;" >
						<label for="orderCode" class="editableLabel"> 厂商产品类别:</label>	
		    		    	${typeNames}
		    		</li>
		    	</ul>
		    	 <ul class="clearfix" >
		    		<li id="field_userName" class="li_form" >
						<label for="orderCode" class="editableLabel"> 币种:</label>
		    		    	<div id="_sino_product_currency" class='controls controlss' style="margin-bottom:20px;float:left;margin-top: 10px;"></div>
		    		</li>
		    		<li id="field_userName" class="li_form" >
						<label for="orderCode" class="editableLabel"> 厂商OID:</label>	
		    				${model.partnerOid }
		    		</li>
		    		<li id="field_userName" class="li_form" >
						<label for="orderCode" class="editableLabel"> logo图标:</label>	
		    			<c:if test="${empty model.partnerLogo}">
		    				<img id="_sino_base_picPath" alt="预览" src="${ctx }/images/state/noUpLoad.jpg" style="height: 60px;  border: 1px solid #ccc" />
		    			</c:if>
		    			<c:if test="${model.partnerLogo != null&&model.partnerLogo != ''}">
		    				<img id="_sino_base_picPath" alt="预览" src="${ctx }${model.partnerLogo }" style="height: 60px; background-color: #ccc; border: 1px solid #ccc" />
		    			</c:if>
		    		</li>
		    	</ul>
		    	</div>
	    	</form>
		</div>
	
	<script language="javascript">
	  	seajs.use('js/page/business/projectm/partnerInfo_detail',function(partnerInfo_detail){
	  		partnerInfo_detail.init();
		 });    
	</script>

</body>
</html>