<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/global.jsp"%>
<%@ page import="com.sinobridge.systemmanage.util.Global"%> 
<%@ page import="com.sinobridge.systemmanage.vo.SystemUser"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>商务待分配成本的付款</title>
    <style type="text/css">
	    .busibtn{
		  margin:5px;
		}
		.operation{
		border-bottom: 1px solid #ccc;
		margin-bottom:10px;
		}
		.mb10 {
		    margin-top:5px;
		    margin-bottom: 10px;
		}
		.rightmenu li {
		font-size: 12px;
		    float: left;
		    line-height: 25px;
		    margin: 0 20px 0 0;
		}
		.rightmenu li a {
		    padding: 2px 10px;
		}
		.rightmenu li a:hover {
		    background: none repeat scroll 0 0 #4D7DCF;
		    color: #FFFFFF;
		}
		.rightmenu li.sel a {
		    background: none repeat scroll 0 0 #4D7DCF;
		    border: 2px solid #4D7DCF;
		    color: #FFFFFF;
		}
		.blue02{
		color:#1874CD;
		}
		
    </style>

</head>
<body >

	<!--表单、底部按钮 -->
	<div class="portlet-body form">	
	<div id="costControl" class="rightmenu mb10 clearfix">
		<ul>
            	<li id='busicoststock' class="button"><a href="javascript:void(0)">关联备货成本确认</a></li>
            	<li id='busicostid'  class="sel"><a href="javascript:void(0)">商务成本确认</a></li>
        </ul>
	</div>
               <div class="operation"></div>
    			<table cellpadding="0" cellspacing="0" border="0" class="table table-striped table-bordered table-hover table_order" id="product_table">
				<thead>
						<th >未确认的付款</th>
						<th >付款金额</th>
						<th >操作</th>
				</thead>
				<tbody>
				 <c:forEach var="product" items="${rs}" varStatus="status">
					<tr>
						<td >${product.payApplyName}</td>
						<c:if test="${product.realPayAmount!=null}">
							<td ><fmt:formatNumber value="${product.realPayAmount }" type="currency" currencySymbol="￥"/></td>
						</c:if>
						<c:if test="${product.realPayAmount==null}">
							<td ><fmt:formatNumber value="${product.payAmonut }" type="currency" currencySymbol="￥"/></td>
						</c:if>
						<td ><a class="btn btn-primary confirmcost" id="${product.id}">手工确认</a></td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
				
	
	
<script language="javascript">
seajs.use('js/page/sales/funds/busiCostList', function(busiCostList) {
	busiCostList.init();
}); 
</script>


</body>
</html>