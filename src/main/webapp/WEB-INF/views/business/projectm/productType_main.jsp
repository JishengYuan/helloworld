<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ include file="/common/global.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
 <html>
  <head>
    <link rel="stylesheet" href="${ctx }/js/plugins/jquery-easyui-1.3.3/themes/bootstrap/easyui.css"/> 
    <link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formTree.css" type="text/css"></link>
    <link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formstyle.css" type="text/css"></link> 
    <title>产品类别管理</title>
    
    <style type="text/css">
		.panel-header{
			width:60px;
		}
	</style>
    
  </head>
  <body>
    <div class="">
	  <div class="page-header">
		   <h2>产品类别管理
		   <c:if test="${fn:contains(powerActions,'editProductType') }">
	
			  <div class="pull-right">
			  	<a id="_sino_devicetype_add" role="button" data-toggle="modal" class="btn btn-primary">新增</a>
			  	<a id="_sino_devicetype_edit"  role="button" class="btn" data-toggle="modal" >修改</a>
			  	<a id='_sino_devicetype_del' class="btn ">删除</a>
			  </div>
			  
			 </c:if>
		  </h2>
	   </div>
       <div id="alertMsg"></div>
	<div id="_sino_devicetype_loadurl" syle="margin-left:100px;">
	      <table id="_sino_devicetype_treegrid"  class="easyui-treegrid">
	      </table>
	 </div>
	 <!--<div id="_sino_devicetype_uploadPic"></div>-->
	 <script language="javascript">
  	seajs.use('js/page/business/projectm/productType_main',function(productType_main){
  		productType_main.init();
	 });    
  
</script>

</body>
</html>