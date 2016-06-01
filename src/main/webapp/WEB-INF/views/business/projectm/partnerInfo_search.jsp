<%@ page language="java" import="java.util.*"
	contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style type="text/css">
._sino_device_partner {
	margin: 0 auto;
}
._sino_device {
   padding-top: 3px;
    text-align: center;
	float: left;
}
._sino_device serviceType{
    line-height:30px;
}
.type input{
	padding:0 0 0 0;
	margin-bottom: 5px;
	margin-left:5px;
}
</style>
<script type="text/javascript">
</script>
<div class="_sino_device_partner">
<a href="#" id="_sino_device_remove" class="easyui-linkbutton" data-options="plain:true" style="float:left;">删除</a>
<div class="datagrid-btn-separator"/>
	<div class="_sino_device type" id="_service_Type">
		<c:forEach var="type" items="${typeList}" varStatus="status">
			<input type="radio" value="${type.dmValue }" name="type" id="_sino_device_typeCheck">${type.dmValueMeaning }</input> 
		</c:forEach>
	</div>
			<input id="partnername" name="partnername" />
			<a href="#" id="_sino_device_search" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-search'">查询</a>
			
	<div class="_sino_device serviceType" style="display:none;margin-top: -4px;margin-left:5px;" id="_sino_device_serviceType">
		<select name="serviceType" id="serviceType" style="width:110px;">
			<c:forEach var="type" items="${serviceList}" varStatus="status">
				<option value="${type.dmValue }">${type.dmValueMeaning }</option>
			</c:forEach>
		</select>
		
	</div>
	
	
</div>
<script language="javascript">
  	seajs.use('js/page/business/projectm/partnerInfo_search',function(partnerInfo_search){
  		partnerInfo_search.init();
	 });    
  
</script>