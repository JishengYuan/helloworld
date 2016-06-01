<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ include file="/common/global.jsp" %>
 <html>
  <head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="${ctx}/js/plugins/jquery-easyui-1.3.3/themes/icon.css"/>
    <link rel="stylesheet" href="${ctx}/js/plugins/jquery-easyui-1.3.3/themes/bootstrap/easyui.css"/>  
    
    <link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/ultra_select.css" type="text/css"></link>
    
    <link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formSelect.css" type="text/css"></link>
    <link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formTree.css" type="text/css"></link>
    <link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formstyle.css" type="text/css"></link>
    <link rel="stylesheet" href="${ctx}/skin/default/room.css" type="text/css" />
    <title>厂商产品型号</title>
    
    <style type="text/css">
    .well {
	    background-color: #FFFFFF;
	    border: 0 solid #E3E3E3;
	    border-radius: 4px;
	    box-shadow: 0 0 0 rgba(0, 0, 0, 0.05) inset;
	    margin-bottom: 10px;
	    min-height: 20px;
	    padding: 5px;
	}
	.select-box .title {
		    font-family: "微软雅黑";
		    font-size: 12px;
		    height: 40px;
		    line-height: 20px;
		    margin: 0 auto;
		    padding: 5px 0;
		    vertical-align: middle;
		}
		.select-box .model-box {
		    height: auto;
		    margin: 10px auto;
		    overflow: hidden;
		    padding-left: 20px;
		    text-align: center;
		    width: 90%;
		}
		.select-box .pag-box {
		    margin-top: 20px;
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
    </style>
    
  </head>
  <body>
	  <div  class="page-header" style="margin-top:12px;margin-bottom:10px;">
   		<h2>产品型号
   		<c:if test="${fn:contains(powerActions,'editProductModel') }">
   		
   		 	<div  class="pull-right">
   		 		<a id="_sino_partner_productmodel_create" href="#" class="btn btn-primary">新建</a>
	  		</div>
	  			<script>
				      var optcustomer=true;
				</script>
	  		</c:if>
   	  	</h2>
   	  </div>

   		<div class="well" style="width:96%;" role="form" id="_search" >
		 <div style="width:100%;height:40px;">
			 <div style="float:left;width:40%">
			    <label class="sr-only" for="exampleInputEmail2" style="float:left;padding-top:4px;">产品分类：</label>
			    <div id="_sino_product_type" style="float:left;"></div>
			  </div> 
			  <div style="float:left;width:50%">
			    <label class="sr-only" style="float:left;padding-top:4px;">厂商：</label>
			    <input id="_sino_partner_partnerId" type="hidden" value="" />
			    <div id="_sino_partner_div" style="float:left;">
				</div>
			  </div>
			  <div style="float:left;margin-left:0px;margin-top:5px;">
			    <label class="sr-only" style="float:left;padding-top:4px;">型号名称：</label>
			    <div id="_sino_partner_product_name" style="float:left;">
					<input id="_sino_partner_product_name_i" type="text" />
				</div>
			  </div>
		  </div>
		   <div style="width:100%;height:30px;">
		   <button class="btn btn-primary pull-right" id="_sino_partner_model_search" style="margin-left:10px;" >
				查询
			</button>
			
			<button class="btn pull-right"  id="_sino_partner_model_reload">
				重置
			</button>
			</div>
		</div>
   		<div id="alertMsg"></div>
		<table id="taskTable" cellpadding="0" cellspacing="0" border="0" class="table table-striped table-bordered table-hover">
			<thead>
				<tr>
					<th width="15%">产品型号</th>
					<th width="10%">型号名称</th>
					<th width="10%">设计寿命</th>
					<th width="10%">停产日期</th>
					<th width="13%">停止服务日期</th>
					<th width="10%">产品类别</th>
					<th width="10%">厂商名称</th>
				</tr>
			</thead>
		<tbody>
		</tbody>
		</table>
		
	   <script language="javascript">
		  	seajs.use('js/page/business/projectm/productModel_main',function(productModel_main){
		  		productModel_main.init();
			 });    
		</script>

</body>
</html>