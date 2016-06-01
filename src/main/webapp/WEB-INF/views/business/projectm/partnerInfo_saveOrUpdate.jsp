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
    <title>厂商信息表</title>
    <style type="text/css">
    	.datagrid{
    		margin-top:20px;
    	}
    	/*覆盖easyui选中样式*/
		.datagrid-row-selected{
		background:none;
		}
		input[type="text"] {
		    margin-top: 12px;
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
	<div id="_sino_devicetype_uploadPic"></div>
	
		<div style="padding:10px 0 10px 60px;text-align: center;margin-left:-50px;">
			<form id="_sino_partner_add_form" method="post" style="text-align: left;">
			<input type="hidden" id="_model_partnerType" value="${model.partnerType}">
			<input type="hidden" id="_model_currency" name="currency" value="${model.currency}">
			<input type="hidden" id="_sino_productType" name="productType" value="${typeIds }">
			<input type="hidden" id="_sino_productType_names" value="${typeNames}">
			<input type="hidden" id="_partner_id" value="${model.id }" name="id">
			<input type="hidden" id="_sino_base_picUrl" value="${model.partnerLogo }" name="partnerLogo">
			<input type="hidden" id="_sino_brandLogo">
		    	<table style="width: 800px;border: 2px solid rgb(243, 243, 243)" id="Tbl">
		    		<tr style="height:60px;">
		    			<td style="margin-top:50px;"><span style="color:red;margin-left:30px">*</span>厂商名称:</td>
		    		    <td style="margin-top:50px">
		    				<input class="easyui-validatebox" style="margin-left:-88px;" type="text" name="partnerFullName" value="${model.partnerFullName }" required data-content="请填写厂商简称"></input></td>
		    		    <td style="margin-top:50px"><span style="color:red;">*</span>厂商编码:</td>
		    		    <td style="margin-top:50px">
		    				<input class="easyui-validatebox" type="text" name="partnerCode" value="${model.partnerCode }" required data-content="请填写厂商编码" id="partnerCode"></input></td>
		    			</tr>
		    		<%--<tr style="height:60px;">
		    		    <td style="text-align: left;">英文名称:</td>
		    		    <td>
		    				<input class="easyui-validatebox" type="text" name="partnerEnCode" value="${model.partnerEnCode }"></input>
		    			</td> 
		    			<td>厂商地址:</td>
		    		    <td>
		    				<input class="easyui-validatebox" type="text" name="registerAddress" value="${model.registerAddress }"></input>
		    			</td>
		    		</tr>
		    		 <tr>
		    			<td style="text-align: left;">厂商邮箱:</td>
		    		    <td>
		    				<input class="easyui-validatebox" type="text" name="partnerEmail" value="${model.partnerEmail }" data-content="请填写正确的邮箱" pattern="email"></input>
		    			</td>
		    			<td>厂商网址:</td>
		    		    <td>
		    				<input class="easyui-validatebox" type="text" name="webUrl" value="${model.webUrl }"></input>
		    			</td>
		    		</tr> --%>
		    		<tr style="height:60px;">
		    			<td style="margin-top:10px"><span style="color:red;margin-left:30px">*</span>联系地址:</td>
		    		    <td colspan="3">
		    				<input style="width:567px;margin-left:-88px" class="easyui-validatebox" type="text" name="address" value="${model.address }" required data-content="请填写联系地址"></input>
		    			</td>
		    		</tr>
		    		<tr style="height:60px;">
		    			<td style="margin-top:10px"><span style="color:red;margin-left:30px">*</span>服务热线:</td>
		    		    <td colspan="3" style="margin-top:10px">
		    				<input style="width:460px;margin-left:-88px" class="easyui-validatebox" type="text" name="hotLine" value="${model.hotLine }" required data-content="请填写服务热线"></input><span style="color:red;">多个热线以逗号分隔</span>
		    			</td>
		    		</tr>
		    		  <tr>
		    		    <td style="margin-top:10px"><span style="color:red;margin-left:30px">*</span>厂商类型:</td> 
		    		    <td colspan="3" style="margin-top:10px">
								<c:forEach var="type" items="${typeList}" varStatus="status">
									<input type="checkbox" style="margin-top:-2px" value="${type.dmValue }" name="partnerType" id="${type.dmValue }" <c:if test="${model.partnerType ==  type.dmValue}">checked</c:if>>&nbsp;${type.dmValueMeaning }&nbsp;&nbsp;</input> 
								</c:forEach>
								 <select class="easyui-combobox" name="servicePartnerType" style="display:none" id="_sino_device_serviceType" style="width:110px;">
									<c:forEach var="type" items="${serviceList}" varStatus="status">
										<option value="${type.dmValue }" <c:if test="${model.servicePartnerType ==  type.dmValue}">selected</c:if>>${type.dmValueMeaning }</option>
									</c:forEach>
								</select> 
		    			</td>
		    			<!--  <td style="text-align: left;">币种:</td>
		    		    <td>
		    		    	<div id="_sino_product_currency" class='controls controlss' style="margin-bottom:20px;float:left;margin-top: 10px;"></div>
		    		    </td> --> 
		    		</tr> 
		    		<tr>
		    		    <td ><span style="color:red;margin-left:30px">*</span>添加厂商支持的产品类别:</td>
		    		    <td colspan="3">
		    		    	<div id="_sino_product_type" class='controls controlss' style="margin-bottom:20px;float:left;margin-top: 10px;">
		    		    	
		    		    	<a id="modi_roles11" href='#modi_roles'  target="_self"  role='button' style='float:left;margin-top:8px;color:blue;' data-toggle='modal' >
							[编辑]</a>
							<span style='margin-top:8px;float:left;' id='sysRole'></span>
		    		    	</div>
		    		    </td>
		    	
		    		</tr>
		    		<%-- <tr>
		    			<td style="text-align: left;">厂商OID:</td>
		    		    <td colspan="3">
		    				<input style="width:400px;" class="easyui-validatebox" type="text" name="partnerOid" value="${model.partnerOid }"></input><span style="color:red;">多个OID以逗号分隔</span>
		    			</td>
		    		</tr> 
		    		<tr>
		    			<td style="text-align: left;">logo图标:</td>
		    		    <td colspan="3">
		    			<c:if test="${empty model.partnerLogo}">
		    				<img id="_sino_base_picPath" alt="预览" src="${ctx }/images/state/noUpLoad.jpg" style="height: 60px;  border: 1px solid #ccc" />
		    			</c:if>
		    			<c:if test="${model.partnerLogo != null&&model.partnerLogo != ''}">
		    				<img id="_sino_base_picPath" alt="预览" src="${ctx }${model.partnerLogo }" style="height: 60px; background-color: #ccc; border: 1px solid #ccc" />
		    			</c:if>
		    				
		    				<a id="_sino_partner_logo" href='#_sino_partner_brand_logo_page' data-toggle='modal' role='button' data-toggle='modal' class='btn btn-primary' >点击上传</a>
		    		</tr>--%>
		    	</table>
		    	<table id="_sino_partnerBrand_datagrid"  class="easyui-datagrid">
      			</table>
		    	<div style="text-align:right;padding:50px;margin-right:100px;">
			    	<div class="pull-right">
				  		<a id="_sino_partner_add_partner" role="button" data-toggle="modal" class="btn btn-primary">确定</a>
					</div>
			    </div>
	    	</form>
		</div>
	<div id="modi_roles_div"></div>
	<script language="javascript">
	  	seajs.use('js/page/business/projectm/partnerInfo_saveOrUpdate',function(partnerInfo_saveOrUpdate){
	  		partnerInfo_saveOrUpdate.init();
		 }); 
	  //----初始化隐藏第三行以后输入框
		 $.each($("#Tbl tr"), function(i){if(i==3){this.style.display = 'none';}});
	</script>

</body>
</html>