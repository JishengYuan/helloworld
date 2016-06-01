<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ include file="/common/global.jsp" %>
 <html>
  <head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" href="${ctx}/skin/default/room.css" type="text/css" />
    <link rel="stylesheet" href="${ctx}/js/plugins/jquery-easyui-1.3.3/themes/icon.css"/>
    <link rel="stylesheet" href="${ctx}/js/plugins/jquery-easyui-1.3.3/themes/bootstrap/easyui.css"/>  
    
    <link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/ultra_select.css" type="text/css"></link>
    
    <link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formSelect.css" type="text/css"></link>
    <link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formTree.css" type="text/css"></link>
    <link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formstyle.css" type="text/css"></link>
    <title>产品型号管理</title>
	<style type="text/css">
		.row {
		    margin-left: 0;
		}
		._sino_td_one{
			float:left;
			margin-right:3px;
			width:100px;
			 height: 36px;
		}
		.one{
			margin-left:30px;
		}
		._sino_td_two{
			float:left;
		}
		._sino_td_top{
			margin-top:20px;
		}
		
		
		.form-horizontal .control-label {
			  margin-left: 10px;
			  width:130px;
			  height: 30px;
		}
		
		.tab-content {
		    overflow: visible;
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
		input[type="text"] {
		    margin-top: -7px; 
		}
		.uneditable-input { 
		    margin-top: -7px; 
		}
		.selectDiv .uicSelectInp {
		    margin-top: -7px; 
		}
		.selectDiv a.uicSelectMore {
		    top: -7px;
		}
	</style>
  </head>
  <body>
  <div class="page-header">
    <h2>型号信息
   	  	<div class="pull-right">
		     <button id="_sino_partner_product_back" type="button" class="btn btn-default btn-lg">
			  <span class="icon-repeat"></span>
			  返回
			</button>
		  </div>
   	  </h2>
  </div>
   	
   	  <div class="" style='float:left;text-alagin:center;' id="_tabs_div">

		<div class="tab-content">
		  <div class="tab-pane active" id="basic"> 
			<form id="_sino_partner_productModel_add_form" method="post" class='form-horizontal' style="width:770px;">
			<input type="hidden" id="_productModelId" name="id" value="${model.id }">
			<input type="hidden" id="_partnerId" name="partnerId" value="${model.partnerId }">
			<input type="hidden" id="_partner_name"  value="${partnerInfo.partnerFullName }">
			<input type="hidden" id="_productType" name="productType" value="${model.productType }">
			<input type="hidden" id="_currency" name="currency" value="${model.currency }">
			<input type="hidden" id="_brandCode" name="brandCode" value="${model.brandCode }">
			<input type="hidden" id="_productLine" name="productLine" value="${model.productLine }">
			<input type="hidden" id="_sino_base_picUrl" name="productIcon" value="${model.productIcon }">
			<input type="hidden" id="_sino_base_bigUrl" name="productBgImage" value="${model.productBgImage }">
			
			<input type="hidden" id="_sino_productTypeName" value="${productType.typeName }">
			<input type="hidden" id="_sino_productLineName" value="${productLine.productLine }">
		    	<table style="width: 800px;border: 2px solid rgb(243, 243, 243)" id="Tbl">
		    	
		    		<tr style="height:90px;">
		    			<td class="_sino_td_one  one" style="margin-top:40px"><span style="color:red;">*</span>产&nbsp品&nbsp&nbsp类&nbsp别:</td>
		    			<td class="_sino_td_two" style="margin-top:40px"><div id="_sino_product_type"></div></td>
		    			<td class="_sino_td_one" style="margin-left: 30px;margin-top:40px"><span style="color:red">*</span>&nbsp型&nbsp号&nbsp&nbsp名&nbsp称:</td>
		    		    <td  class="_sino_td_two" style="margin-top:40px">
		    			<input class="easyui-validatebox" type="text" name="productModelName" value="${model.productModelName }" data-content="请填写型号名称" required /></td>
		    		</tr>
		    		<tr style="height:60px">
		    			<td class="_sino_td_one one" style="margin-top:10px"><span style="color:red">*</span>型&nbsp号&nbsp&nbsp编&nbsp码:</td>
		    		    <td  class="_sino_td_two" style="margin-top:10px"><input type="text" class="easyui-validatebox" name="productModel" value="${model.productModel }" data-content="请填写型号编码" required /></td>
		    		 	<td class="_sino_td_one " style="margin-left: 30px;margin-top:10px"><span style="color:red">*</span>&nbsp生&nbsp产&nbsp&nbsp厂&nbsp商:</td>
		    		    <td class="_sino_td_two">
			    		     <div class="controls" id="_sino_partner_div" style="margin-left:0px;" >
				      		  <input id="_sino_partner" type="text" value="${partnerInfo.partnerFullName }" class="ultra-select-input3" data="0" data-content="请选择厂商" required/>
				      		</div> 
				      		<div id="_sino_partner_div"></div>
						</td>
		    		</tr>
		    		<tr style="height:60px">
		    			<td class="_sino_td_one" style="margin-left: 30px;"><span style="color:red">*</span>产&nbsp品&nbsp&nbsp描&nbsp述:</td>
		    			<td class="_sino_td_two">
		    				<textarea rows="2" cols="20" style="width:572px" name='productDesc' data-content="请填写型号描述" required >${model.productDesc }</textarea>
		    			</td>
		    		</tr>
		    		<tr style="height:60px">
			    		<td class="_sino_td_one _sino_td_top">
							  <a id="otherAll" role="button" data-toggle="modal" class="btn btn-primary" style="border-radius:5px;margin-left: 300px;width:100px;margin-top: -10px;" onclick='javacript:Href()'>《填写更多</a>
						</td>
					</tr>
		    		<tr style="height:60px">
		    			<td class="_sino_td_one" style="margin-left: 32px;">公&nbsp开&nbsp&nbsp报&nbsp价:</td>
		    			<td class="_sino_td_two">
		    				<input type="text" name="listPrice" value="${model.listPrice }">
		    			</td>
		    			<td class="_sino_td_one  one">币&nbsp&nbsp种:</td>
		    		    <td class="_sino_td_two">
			    		    <div id="_sino_product_currency" class='controls controlss' style="margin-left:-1px;"></div>
						</td>
		    		</tr>

		    		<tr style="height:60px">
		    			<td class="_sino_td_one" style="margin-left: 32px;">设计寿命(月):</td>
		    		    <td  class="_sino_td_two">
		    				<input class="easyui-validatebox" type="text" name="productPdlife" value="${model.productPdlife }"></input>
		    			</td>
		    			<td class="_sino_td_one one">重	量(kg):</td>
		    		    <td  class="_sino_td_two">
		    		    <input type="text" class="easyui-validatebox" name="productWeight" value="${model.productWeight }" /></td>
		    		</tr>
		    		<tr style="height:60px">
		    			<td class="_sino_td_one" style="margin-left: 32px;">外观尺寸(mm):</td>
		    		    <td  class="_sino_td_two">
		    			<input type="text" class="easyui-validatebox" name="productOutWard" value="${model.productOutWard }" /></td>
		    		    <td class="_sino_td_one one">产&nbsp品&nbsp&nbsp&nbsp&nbspOID:</td>
		    		    <td  class="_sino_td_two">
		    		    <input type="text" class="easyui-validatebox" name="productOid" value="${model.productOid }" /></td>
		    		</tr>
		    		<tr style="height:60px">
		    		    
		    			<td class="_sino_td_one" style="padding-top:10px;margin-left: 32px;">停产日期:</td>
		    		    <td  class="_sino_td_two" style="padding-top:10px;">
		    		    
		    		     	<div id="dateTimeStopDate" class="input-append date">
					  		    <input data-format="yyyy-MM-dd" style="width:180px;" type="text" name="stopDate" value="${model.stopDate }"></input>
							    <span class="add-on" style="margin-top:-7px">
							      <i data-time-icon="icon-time" data-date-icon="icon-calendar">
							      </i>
								</span>
							  </div>
					     </td>
					     <td class="_sino_td_one  one" style="padding-top:10px;">停止服务日期:</td>
		    		    <td  class="_sino_td_two" style="padding-top:10px;">
		    				
		    				<div id="dateTimeStopSevDate" class="input-append date">
					  		    <input data-format="yyyy-MM-dd" style="width:180px;" type="text" name="stopSevDate" value="${model.stopSevDate }"></input>
							    <span class="add-on" style="margin-top:-7px">
							      <i data-time-icon="icon-time" data-date-icon="icon-calendar">
							      </i>
								</span>
							  </div>
		    			</td>
		    		</tr>
		    		<tr style="height:60px">
		    			<td class="_sino_td_one" style="margin-left: 32px;">工作环境要求:</td>
		    			<td class="_sino_td_two" colspan="3">
		    				<textarea rows="2" cols="20" style="width:572px" name='productWorkDesc'>${model.productWorkDesc }</textarea>
		    			</td>
		    		</tr>
		    		<tr style="height:60px">
		    			<td colspan="4" class="_sino_td_one" style="margin-left: 32px;">图&nbsp&nbsp标:</td>
		    			<td class="_sino_td_two" colspan="3" style="padding-top:10px;">
		    				
		    				<c:if test="${empty model.productIcon}">
			    				<img id="_sino_base_picPath" alt="预览" src="${ctx }/images/state/noUpLoad.jpg" style="height: 60px;  border: 1px solid #ccc" />
			    			</c:if>
			    			<c:if test="${model.productIcon != null&&model.productIcon != ''}">
			    				<img id="_sino_base_picPath" alt="预览" src="${ctx }${model.productIcon }" style="height: 60px; background-color: #ccc; border: 1px solid #ccc" />
			    			</c:if>
		    				<a id="_sino_base_icon" role="button" data-toggle="modal" class="btn btn-primary">点击上传</a>
		    			</td>
		    		</tr>
		    		<tr style="height:60px">
		    			<td colspan="4" class="_sino_td_one" style="margin-left: 32px;">背&nbsp景&nbsp&nbsp图&nbsp片:</td>
		    			<td class="_sino_td_two" colspan="3" style="padding-top:10px;">
		    				<c:if test="${empty model.productBgImage}">
			    				<img id="_sino_base_picPath" alt="预览" src="${ctx }/images/state/noUpLoad.jpg" style="height: 60px;  border: 1px solid #ccc" />
			    			</c:if>
			    			<c:if test="${model.productBgImage != null&&model.productBgImage != ''}">
			    				<img id="_sino_base_bigPath" alt="预览" src="${ctx }${model.productBgImage }" style="height: 60px; background-color: #ccc; border: 1px solid #ccc" />
			    			</c:if>
		    				<a id="_sino_base_big" role="button" data-toggle="modal" class="btn btn-primary">点击上传</a>
		    			</td>
		    		</tr>
		    	</table>
			    	<div class="pull-right">
				  		<a id="_sino_partner_productModel_add" role="button" data-toggle="modal" class="btn btn-primary">确定</a>
					</div>
	    	</form>
			</div>
		</div>
		</div>
	   <script language="javascript">
		  	seajs.use('js/page/business/projectm/productModel_saveOrUpdate',function(productModel_saveOrUpdate){
		  		productModel_saveOrUpdate.init();
			 }); 
		    //----初始化隐藏第三行以后输入框
			 $.each($("#Tbl tr"), function(i){if(i > 3){this.style.display = 'none';}});
			//-----------------
		  	function Href() { //隐藏域函数
				$.each($("#Tbl tr"), function(i){
					if(i>3){
						if(this.style.display=='none'){
							this.style.display = '';
							$('#otherAll').text("《收起");
						}else{
							this.style.display = 'none';
							$('#otherAll').text("《填写更多");
						} 
				}});
		}
		</script>

</body>
</html>