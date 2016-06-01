<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/global.jsp"%>

<html>
	<head>
		<title>章资质状态信息</title>
		<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
		<style type="text/css">
	     .stampcell{
	          width:220px;
	          height:120px;
	          background-color:#eee;
	          float: left;
	          margin:2px;
	          padding:5px;
	     }
		</style>
	</head>
	<body>
	<div style="width:960px;font-size: 14px;">
	<form action="">
	<fieldset>
	<legend>章</legend>
	<c:forEach var="qc" items="${qclist}" varStatus="status">
	<c:if test="${qc.qcType eq 1}">
		   <div class="stampcell">
		          <div style="text-align:center;">${qc.qcName }</div>
		          <c:if test="${qc.qcstatus eq 0 }">
		          <div style="text-align:center;font-size:50px;"><span class="icon-ok-sign" style="color:green;"></span></div>
		          </c:if>
		           <c:if test="${qc.qcstatus eq 1 }">
		          <div style="text-align:center;font-size:50px;"><span class="icon-minus-sign" style="color:red;"></span></div>
		          </c:if>
	         <p class="qcdescinfo" style="text-align:center;">${qc.usedesc}</p>
		   </div>
	  </c:if>
	 </c:forEach>
	 </fieldset>
	 </form>
	 
	<form action="">
	<fieldset>
	<legend>资质</legend>
	<c:forEach var="qc" items="${qclist}" varStatus="status">
	<c:if test="${qc.qcType eq 2}">
		   <div class="stampcell">
		          <div style="text-align:center;">${qc.qcName }</div>
		          <c:if test="${qc.qcstatus eq 0 }">
		             <c:if test="${qc.totals eq 1 }">
		             <div style="text-align:center;font-size:50px;"><span class="icon-ok-sign" style="color:green;"></span></div>
		             </c:if>
		             <c:if test="${qc.totals gt 1 }">
		             <div style="text-align:center;font-size:50px;"><span style="font-size:14px;">总数：${qc.totals}</span> <span class="icon-ok-sign" style="color:green;"></span><span style="font-size:14px;">借出：${qc.borrows}</span></div>
		             </c:if>
		          
		          </c:if>
		           <c:if test="${qc.qcstatus eq 1 }">
		          <div style="text-align:center;font-size:50px;"><span class="icon-minus-sign" style="color:red;"></span></div>
		          </c:if>
		         <p class="qcdescinfo" style="text-align:center;">${qc.usedesc}</p>
		   </div>
	  </c:if>
	 </c:forEach>
	 </fieldset>
	 </form>
	 
	<form action="">
	<fieldset>
	<legend>证书</legend>
	<c:forEach var="qc" items="${qclist}" varStatus="status">
	<c:if test="${qc.qcType eq 3}">
		   <div class="stampcell">
		          <div style="text-align:center;">${qc.qcName }</div>
		          <c:if test="${qc.qcstatus eq 0 }">
		          <div style="text-align:center;font-size:50px;"><span class="icon-ok-sign" style="color:green;"></span></div>
		          </c:if>
		           <c:if test="${qc.qcstatus eq 1 }">
		          <div style="text-align:center;font-size:50px;"><span class="icon-minus-sign" style="color:red;"></span></div>
		          </c:if>
	         <p class="qcdescinfo" >${qc.usedesc}</p>
		   </div>
	  </c:if>
	 </c:forEach>
	 </fieldset>
	 </form>	 
	 
	 
	 
	</div>
	</body>
	 <script language="javascript">
	/*	 seajs.use('js/page/sales/contractFound/biddingReview/qcListInfo',function(qcListInfo){
			 qcListInfo.init();
		    });
	*/
	</script>
</html>