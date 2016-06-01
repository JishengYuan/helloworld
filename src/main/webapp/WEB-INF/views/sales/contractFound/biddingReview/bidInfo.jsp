<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/global.jsp"%>

<html>
	<head>
		<title>投标审批通过信息操作</title>
		<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
		<%@ include file="/common/include-base-boostrap-styles.jsp" %>
		<link href="${ctx}/skin/default/sales/sale.css" rel="stylesheet"/>
		<script src="${ctx}/js/plugins/seajs/sea.js" id="seajsnode" type="text/javascript"></script>
		<script src="${ctx}/js/plugins/seajs/config.js" type="text/javascript"></script>
		<style type="text/css">
		.handprocess_order li.li_form{
		    float:left;
			padding:0;
			
		}
		.handprocess_order li.li_form label{
		color: #ff9933;
			margin:0;
			line-height: 25px;
			display: inline;
		}
		ul{
		margin-bottom:0px;
		}
		</style>
</head>

<body style="width: 100%;overflow-x: hidden; ">   
<div class="salecontent">
		<div class="top">
			<div class="caption">
				<i class="icon-reorder"></i>&nbsp;&nbsp;&nbsp;投标内容信息管理
			</div>
		</div>
	</div>	 

<div class="portlet-body form">
<div class="handprocess_order" id="SheetDiv">
			<br><br>

			<input type="hidden" id="foundId" name="foundId" value="${model.id }"/>
			<input type="hidden" id="totalFunds" name="totalFunds" value="${model.totalFunds }"/>
			<ul class="clearfix" >
				<li id="field_userName" class="li_form" style="width:400px">
					<label class="editableLabel">投标项目：</label>${model.applyFundsName}
				</li>
				<li id="field_userName" class="li_form" style="width:400px">
					<label class="editableLabel">客&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;户：</label><div id='_customerInfoName'  style="display: inline;"></div>
					<input type="hidden" id='_eoss_sales_customerId' name='cusCustomerId' value="${model.cusCustomerId}" />
				</li>
			</ul>
    		<ul class="clearfix" >
				<li id="field_userName" class="li_form" style="width:400px">
					<label class="editableLabel">伙伴公司：</label>${model.partnerCompany}
				</li>
				<li id="field_userName" class="li_form" style="width:400px">
					<label class="editableLabel">投标盈利：</label>${model.expectProfit}
				</li>
			</ul>
			<ul class="clearfix" >
				<li id="field_userName" class="li_form" style="width:600px">
					<label class="editableLabel">说&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;明：</label>${model.applyDesc}
				</li>
				<li id="field_userName" class="li_form" style="width:300px;text-align: right;">
					<label class="editableLabel">申请人：</label>${model.creatorName} &nbsp;&nbsp;<fmt:formatDate  value="${model.createTime}" pattern="yyyy-MM-dd"/> 
				</li>
			</ul>
			
			
		<div style="clear:both;"></div>
		<div class=" row-inline" >
				<hr size=1 class="dashed-inline">
		</div>
	
		<ul class="clearfix" >
			<div style="width:920px;float:left; margin-right: 10px;">
				<div class=" row-inline collection_plan" >
					<h5 class="form-section-title"><a name="colltionPlan">往来款</a></h5>
				</div>
				<table id='table_size'  cellspacing="0" border="0" class="table  table-bordered table-striped">
							<thead>
								<tr>
									<th width="10%">金额</th>
									<th width="20%">支付方式</th>
									<th width="30%">用途</th>
									<th width="30%">操作</th>
								</tr>
							</thead>
							<tbody>
								 <c:forEach var="tableDataSize" items="${model.salesContractSizeModel}" varStatus="status">
								 <tr>
								    <c:if test="${empty tableDataSize.realPayUser}">
								 	<td ><input id="realpayprice" name="realpayprice" value='${tableDataSize.applyPrices}'/></td>
								 	<td id="payType_${status.index}">${tableDataSize.payType}</td>
								 	<td id="payDesc_${status.index}">${tableDataSize.payDesc}</td>
								 	<td><div><button style="float:left;" class="btn btn-primary payfounds"  id="${tableDataSize.id}">付款</button></div></td>
								 	</c:if>
								 	<c:if test="${not empty tableDataSize.realPayUser}">
								 	<td >￥${tableDataSize.realPayPrices}</td>
								 	<td id="payType_${status.index}">${tableDataSize.payType}</td>
								 	<td id="payDesc_${status.index}">${tableDataSize.payDesc}</td>
								 	<td>已付款,${tableDataSize.realPayUser}(${tableDataSize.realPayDate})</td>
								 	</c:if>
								 	</tr>
								 </c:forEach>
							</tbody>
						</table>
				</div>
				
				<div style="width:920px;float:left;">
				<div class=" row-inline collection_plan" >
					<h5 class="form-section-title"><a name="colltionPlan">章</a></h5>
				</div>
				<table id='table_chapter' cellspacing="0" border="0" class="table  table-bordered table-striped">
							<thead>
								<tr>
									<th width="40%">章类型</th>
									<th width="20%">借出时间</th>
									<th  width="20%">归还时间</th>
									<th  width="20%">操作</th>
								</tr>
							</thead>
							<tbody>
								 <c:forEach var="tableDataSize" items="${model.salesContractChapterModel}" varStatus="status">
								 <tr>
								 	<td id="chapterType_${status.index}">${tableDataSize.chapterName}</td>
								 	<c:if test="${empty tableDataSize.chapterstate}">
								 	<td >预计：<fmt:formatDate  value="${tableDataSize.forecastBorrowDate}" pattern="yyyy-MM-dd"/></td>
								 	<td >预计：<fmt:formatDate  value="${tableDataSize.forecastReturnDate}" pattern="yyyy-MM-dd"/></td>
									<td><button style="float:left;" class="btn btn-primary stampborrow"  id="${tableDataSize.id}">借出</button></td>
								 	</c:if>
								 	<c:if test="${tableDataSize.chapterstate eq 0}">
								 		<td ><fmt:formatDate  value="${tableDataSize.realBorrowDate}" pattern="yyyy-MM-dd"/></td>
								 		<td >预计：<fmt:formatDate  value="${tableDataSize.forecastReturnDate}" pattern="yyyy-MM-dd"/></td>
										<td><button style="float:left;" class="btn btn-success stampreturn"  id="${tableDataSize.id}">归还</button></td>
								 	</c:if>
								 	<c:if test="${tableDataSize.chapterstate eq 1}">
								 		<td ><fmt:formatDate  value="${tableDataSize.realBorrowDate}" pattern="yyyy-MM-dd"/></td>
								 		<td ><fmt:formatDate  value="${tableDataSize.realReturnDate}" pattern="yyyy-MM-dd"/></td>
										<td>管理人：${tableDataSize.agreeBorrowUser}</td>
								 	</c:if>
								 	</tr>
								 </c:forEach>
							</tbody>
						</table>
					</div>
			
			
				<div style="width:920px;float:left; margin-right: 10px;">
				<div class=" row-inline collection_plan" >
					<h5 class="form-section-title"><a name="colltionPlan">资质</a></h5>
				</div>
				<table id='table_fication'  cellspacing="0" border="0" class="table  table-bordered table-striped">
							<thead>
								<tr>
									<th width="40%">资质名称</th>
									<th width="20%">借出时间</th>
									<th width="20%">归还时间</th>
									<th width="20%">操作</th>
								</tr>
							</thead>
							<tbody>
								 <c:forEach var="tableDataSize" items="${model.salesContractQualificationModel}" varStatus="status">
								 <tr>
								 	<td id="qualifications_${status.index}">${tableDataSize.qualificationName}</td>
								 	<c:if test="${empty tableDataSize.qualificationsState}">
								 	<td id="qualificationsType_${status.index}">预计：<fmt:formatDate  value="${tableDataSize.forecastBorrowDate}" pattern="yyyy-MM-dd"/></td>
								 	<td id="qualificationsType_${status.index}">预计：<fmt:formatDate  value="${tableDataSize.forecastReturnDate}" pattern="yyyy-MM-dd"/></td>
								 	<td><button style="float:left;" class="btn btn-primary qualificationborrow"  id="${tableDataSize.id}">借出</button></td>
								 	</c:if>
								 	<c:if test="${tableDataSize.qualificationsState eq 0}">
								 	<td id="qualificationsType_${status.index}"><fmt:formatDate  value="${tableDataSize.realBorrowDate}" pattern="yyyy-MM-dd"/></td>
								 	<td id="qualificationsType_${status.index}">预计：<fmt:formatDate  value="${tableDataSize.forecastReturnDate}" pattern="yyyy-MM-dd"/></td>
								 	<td><button style="float:left;" class="btn btn-success qualificationreturn"  id="${tableDataSize.id}">归还</button></td>
								 	</c:if>
								 	<c:if test="${tableDataSize.qualificationsState eq 1}">
								 	<td id="qualificationsType_${status.index}"><fmt:formatDate  value="${tableDataSize.realBorrowDate}" pattern="yyyy-MM-dd"/></td>
								 	<td id="qualificationsType_${status.index}"><fmt:formatDate  value="${tableDataSize.realReturnDate}" pattern="yyyy-MM-dd"/></td>
								 	<td>管理人：${tableDataSize.agreeBorrowUser}</td>
								 	</c:if>
								 	</tr>
								 </c:forEach>
							</tbody>
						</table>
				</div>
				<div style="width:920px;float:left;">
				<div class=" row-inline collection_plan" >
					<h5 class="form-section-title"><a name="colltionPlan">证书</a></h5>
				</div>
				<table id='table_certificate' width="50%" cellspacing="0" border="0" class="table  table-bordered table-striped">
							<thead>
								<tr>
									<th width="60%">证书名称</th>
									<th width="40%">数量</th>
								</tr>
							</thead>
							<tbody>
								 <c:forEach var="tableDataSize" items="${model.salesContractsCertificateModel}" varStatus="status">
								 	<tr>
								 	<td id="certificateType_${status.index}">${tableDataSize.certificateName}</td>
								 	<td >${tableDataSize.certificateNum}</td>
								 	</tr>
								 </c:forEach>
							</tbody>
						</table>
				</div>
			</ul>
		<div class=" row-inline" >
				<hr size=1 class="dashed-inline">
		</div>
			<ul class="clearfix">
			<h5 style="background-color:InactiveCaption"><a name="colltionPlan">投标保证金往来款总计￥${amount}</a></h5>
			<c:if test="${empty model.mergerProjectPrice and empty model.returnPrice}">
				<li class="li_form" style="width:100%">
		   		  <div style="float:left;line-height: 30px;">
		   			<input type="radio" name="isAgree" value="1" checked="checked"  style="margin:0;margin-bottom:4px;"/>&nbsp;项目划归
		   		  </div>
	   			<div id="contract" class="_contract_hidden"  style="float:left;">
						<div id="_select_contractName_div" style="width:260px;margin-left:5px;float:left;">
						</div>
						<input type="hidden" id="contractCode" name="contractCode" />
						<div style="float:left;line-height: 30px;"><lable style="float:left;">金额：</lable><input id="mergercontractPrice"  name="mergercontractPrice" type="text"/></div>
						<div style="float:left;margin-left:10px;">
							<button class="btn btn-success projectcontractPrice"  >确定</button>
						</div>
				</div>

				</li>
				<li class="li_form" style="width:100%;margin-top:10px;">
				     <div style="float:left;line-height: 30px;">
						<input type="radio" name="isAgree" value="0" style="margin:0;margin-bottom:4px;"/>&nbsp;往来款返还
					</div>
					<div id='returns' class=" _return_hidden" style="display: none;margin-left:5px;float:left;">
							<input type="text" id="returnPrice" name="returnPrice" value=""/>
							<button style="margin-bottom:10px;" class="btn btn-success returncontractPrice"  >确定</button>
					</div>
				</li>
			</c:if>
			<c:if test="${not empty model.mergerProjectPrice }">
			    <li class="li_form" style="width:100%">
			       投标保证金总金额：￥${amount}，已划归项目[${model.contractName }]金额：￥${model.mergerProjectPrice }
			    </li>
			</c:if>
			<c:if test="${not empty model.returnPrice }">
			    <li class="li_form" style="width:100%">
			       投标保证金总金额：￥${amount}，已返还金额：￥${model.returnPrice }
			    </li>
			</c:if>
		   		
				
			</ul>
		<div class=" row-inline" >
				<hr size=1 class="dashed-inline">
		</div>
		<ul class="clearfix">
			<h5 ><a name="colltionPlan">投标保证金操作日志</a></h5>
				<table border="0" style="width: 98%" class="sino_table_body" id="_log">
					   <thead>
			            	<tr>
								<td class="sino_table_label" style="background:none repeat scroll 0 0 #F3F3F3;width:20%;">时间</td>
								<td class="sino_table_label" style="background:none repeat scroll 0 0 #F3F3F3;width:80%;">内容</td>
							</tr>
						</thead>
						<tbody>
						<c:forEach var="logs" items="${fundLogList}" varStatus="status">
							<tr>
								<td class="sino_table_label"><fmt:formatDate  value="${logs.optDate }" pattern="yyyy-MM-dd"/></td>
								<td class="sino_table_label">${logs.optDesc }</td>
							</tr>
						 </c:forEach>
						</tbody>
				</table>
		</ul>	
		<ul class="clearfix">
			<h5 ><a name="colltionPlan">审批日志</a></h5>
				<table border="0" style="width: 98%" class="sino_table_body" id="_fee_travel">
					   <thead>
			            	<tr>
								<td class="sino_table_label" style="background:none repeat scroll 0 0 #F3F3F3;width:20%;">操作人</td>
								<td class="sino_table_label" style="background:none repeat scroll 0 0 #F3F3F3;width:20%;">审批阶段</td>
								<td class="sino_table_label" style="background:none repeat scroll 0 0 #F3F3F3;width:20%;">审批结果</td>
								<td class="sino_table_label" style="background:none repeat scroll 0 0 #F3F3F3;width:20%;">操作时间</td>
								<td class="sino_table_label" style="background:none repeat scroll 0 0 #F3F3F3;width:20%;">详情</td>
							</tr>
						</thead>
						<tbody>
						<c:forEach var="logs" items="${proInstLogList}" varStatus="status">
							<tr>
								<td class="sino_table_label">${logs.user }</td>
								<td class="sino_table_label">${logs.taskName }</td>
								<td class="sino_table_label">${logs.apprResultDesc }</td>
								<td class="sino_table_label"><fmt:formatDate  value="${logs.time }" pattern="yyyy-MM-dd"/></td>
								<td class="sino_table_label">${logs.apprDesc }</td>
							</tr>
						 </c:forEach>
						</tbody>
				</table>
			</ul> 
		
	<!--按钮组-->
	<div id="bottom_button" class="modal-footer" style="text-align: center;">
	   	
   		<input id="back_page" type="button" value="关闭" class="btn"/>
	</div>
	
   </div>
</div>

 <script language="javascript">
 seajs.use('js/page/sales/contractFound/biddingReview/bidInfo',function(bidInfo){
	 bidInfo.init();
    });
</script>

</body>
</html>