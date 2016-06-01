<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ include file="/common/global.jsp" %>
 <html>
  <head>
    <link rel="stylesheet" href="${ctx }/js/plugins/jquery-easyui-1.3.3/themes/icon.css"/>
    <link rel="stylesheet" href="${ctx }/js/plugins/jquery-easyui-1.3.3/themes/bootstrap/easyui.css"/>  
    <title>厂商信息表</title>
  </head>
  <body>
	  <div class="page-header">
		   <h2>厂商信息表
			<div class="pull-right">
		     	<c:if test="${fn:contains(powerActions,'editPartnerInfoModel') }">
			 	 	<a id="_sino_partner_add" role="button" data-toggle="modal" class="btn btn-primary">新增</a>
			 	 </c:if>
			  </div>
		  </h2>
	   </div>
    <div id="alertMsg"></div>
	<div id="_sino_partner_loadurl" style="width:100%">
	      <table id="_sino_partner_datagrid"  class="easyui-datagrid">
	      </table>
	 </div>
	 <div id="_sino_devicetype_uploadPic"></div>

<script language="javascript">
  	seajs.use('js/page/business/projectm/partnerInfo_main',function(partnerInfo_main){
  		partnerInfo_main.init();
	 });    
  
</script>

</body>
</html>