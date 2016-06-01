<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/global.jsp"%>

<html>
	<head>
		<title>投标信息</title>
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

<body style="width: 100%;overflow-x: hidden;">   

	<div class="salecontent">
		<div class="top">
			<div class="caption">
				<i class="icon-reorder"></i>&nbsp;&nbsp;&nbsp;审批投标信息
			</div>
		</div>
	</div>	 

<div class="portlet-body form">
<div class="handprocess_order" id="SheetDiv">
			<br><br>
			<form id="_sino_eoss_cuotomer_addform" class='form-horizontal' method="post" action="">
			<input type="hidden" id="foundId" name="foundId" value="${model.id }"/>
			<input type="hidden" id="taskId" name="taskId" value="${param.taskId }"/>
			<input type="hidden" id="flowStep" name="flowStep" value="${param.flowStep }"/>
			<input type="hidden" id="size_amount_t" name="size_amount_t" value=""/>
			
			<ul class="clearfix" >
				<li id="field_userName" class="li_form" style="width:400px">
					<label class="editableLabel">投标项目：</label>${model.applyFundsName}
				</li>
				<li id="field_userName" class="li_form" style="width:400px;">
					<label class="editableLabel">客&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;户：</label><div id='_customerInfoName' style="display:inline;" ></div>
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
				<li id="field_userName" class="li_form" style="width:300px;text-align:right;">
					<label class="editableLabel">申请人：</label>${model.creatorName} &nbsp;&nbsp;<fmt:formatDate  value="${model.createTime}" pattern="yyyy-MM-dd"/>
				</li>
			</ul>
			<c:if test="${page=='detail'}">	
				<ul class="clearfix" >
					<c:if test="${model.contractName!=null }">
						<li id="field_userName" class="li_form" style="width:600px">
							<label class="editableLabel">相关合同：</label>${model.contractName}
						</li>
					</c:if>
					<c:if test="${model.returnPrice!=null }">
						<li id="field_userName" class="li_form" style="width:600px">
							<label class="editableLabel">往来款归属：</label>${model.returnPrice}
						</li>
					</c:if>
				</ul>
			</c:if>
			
			
		<div style="clear:both;"></div>
		<div class=" row-inline" >
				<hr size=1 class="dashed-inline">
		</div>
	
		<ul class="clearfix" >
			<div style="width:460px;float:left; margin-right: 10px; margin-left: -10px;">
				<div class=" row-inline collection_plan" >
					<h5 class="form-section-title"><a name="colltionPlan">往来款</a></h5>
				</div>
				<table id='table_size' width="50%" cellspacing="0" border="0" class="table  table-bordered table-striped">
							<thead>
								<tr>
									<th width="25%">金额</th>
									<th width="25%">支付方式</th>
									<th width="25%">用途</th>
									<c:if test="${param.flowStep=='hehuasp' }"><th width="25%">修改</th></c:if>
								</tr>
							</thead>
							<tbody>
								 <c:forEach var="tableDataSize" items="${model.salesContractSizeModel}" varStatus="status">
								 <tr>
								 	<td >￥<span id="amount_${status.index}">${tableDataSize.applyPrices}</span></td>
								 	<td id="payType_${status.index}">${tableDataSize.payType}</td>
								 	<td id="payDesc_${status.index}">${tableDataSize.payDesc}</td>
								 	<c:if test="${param.flowStep=='hehuasp' }">
								 		<td><input id="update_Amount_${status.index}" name='check_amount' num="${status.index}" type="button" value="修改金额" tdValue="${tableDataSize.id}" class="btn "></td>
								 	</c:if>
								 	</tr>
								 </c:forEach>
							</tbody>
						</table>
				</div>
				<c:if test="${not empty model.salesContractChapterModel}">	
					<div style="width:460px;float:left;">
					<div class=" row-inline collection_plan" >
						<h5 class="form-section-title"><a name="colltionPlan">章（借出）</a></h5>
					</div>
					<table id='table_chapter' width="50%" cellspacing="0" border="0" class="table  table-bordered table-striped">
								<thead>
									<tr>
										<th>章类型</th>
										<th>使用时间</th>
										<th>归还时间</th>
									</tr>
								</thead>
								<tbody>
									 <c:forEach var="tableDataSize" items="${model.salesContractChapterModel}" varStatus="status">
									 <tr>
									 	<td id="chapterType_${status.index}">${tableDataSize.chapterId}</td>
									 	<td ><fmt:formatDate  value="${tableDataSize.forecastBorrowDate}" pattern="yyyy-MM-dd"/></td>
									 	<td ><fmt:formatDate  value="${tableDataSize.forecastReturnDate}" pattern="yyyy-MM-dd"/></td>
									 	<%-- <c:if test="${chapter.creatname!=''}">
									 		${chapter.creatname}
									 	</c:if> --%>
									 	</tr>
									 </c:forEach>
								</tbody>
							</table>
						</div>
				</c:if>
				</ul>
				<ul class="clearfix" >
				<c:if test="${not empty model.salesContractQualificationModel}">	
					<div style="width:460px;float:left; margin-right: 10px;margin-left: -10px;">
					<div class=" row-inline collection_plan" >
						<h5 class="form-section-title"><a name="colltionPlan">资质（借出）</a></h5>
					</div>
					<table id='table_fication' width="50%" cellspacing="0" border="0" class="table  table-bordered table-striped">
								<thead>
									<tr>
										<th>资质名称</th>
										<th>使用时间</th>
										<th>归还时间</th>
									</tr>
								</thead>
								<tbody>
									 <c:forEach var="tableDataSize" items="${model.salesContractQualificationModel}" varStatus="status">
									 <tr>
									 	<td id="qualifications_${status.index}" style="font-size: 12px;">${tableDataSize.qualificationId}</td>
									 	<td ><fmt:formatDate  value="${tableDataSize.forecastBorrowDate}" pattern="yyyy-MM-dd"/></td>
									 	<td ><fmt:formatDate  value="${tableDataSize.forecastReturnDate}" pattern="yyyy-MM-dd"/></td>
									 	</tr>
									 </c:forEach>
								</tbody>
							</table>
					</div>
				</c:if>
				<c:if test="${not empty model.salesContractsCertificateModel}">
					<div style="width:460px;float:left;">
					<div class=" row-inline collection_plan" >
						<h5 class="form-section-title"><a name="colltionPlan">证书</a></h5>
					</div>
					<table id='table_certificate' width="50%" cellspacing="0" border="0" class="table  table-bordered table-striped">
								<thead>
									<tr>
										<th>证书名称</th>
										<th>数量</th>
									</tr>
								</thead>
								<tbody>
									 <c:forEach var="tableDataSize" items="${model.salesContractsCertificateModel}" varStatus="status">
									 	<tr>
									 	<td id="certificateType_${status.index}">${tableDataSize.certificateType}</td>
									 	<td >${tableDataSize.certificateNum}</td>
									 	</tr>
									 </c:forEach>
								</tbody>
							</table>
					</div>
				</c:if>
			</ul>
		<c:if test="${param.flowStep=='hehuasp' }">
			<ul class="clearfix" >
			<div class=" row-inline collection_plan" >
					<h5 class="form-section-title"><a name="colltionPlan">往来款留存</a></h5>
				</div>
			<table style="margin-top:20px;" id="taskTable" cellpadding="0" cellspacing="0" border="0" class="table table-striped table-bordered table-hover">
					<thead>
						<tr>
							<th width="10%">发起人</th>
							<th width="30%">投标项目</th>
							<th width="20%">客户</th>
							<th width="10%">投标金额</th>
							<th width="10%">支付时间</th>
						</tr>
					</thead>
				<tbody>
					<c:forEach var="model" items="${modelList}" varStatus="status">
						<tr>
							<td>${model.creatorName }</td>
							<td>${model.applyFundsName }</td>
							<td>${model.cusCustomerId }</td>
							<td>${model.totalFunds }</td>
							<td>${model.payTime }</td>
						</tr>
					</c:forEach>
				</tbody>
				</table>
			</ul>
		</c:if>
			
	
	
	
	<c:if test="${page!='detail'}">	
		<c:if test="${param.flowStep!='CXTJ' }">	
			<ul class="clearfix">
				<li id="field_userName" class="li_form" style="width: 725px;">
					<label for="isNew" class="editableLabel" style="float:left;padding-top: 1px;height: 50px;">是否通过：</label>
				   		<input type="radio" name="isAgree" value="1" checked="checked" style="height:25px;margin:0;"/>审批通过</br>
						<input type="radio" name="isAgree" value="0" style="height:25px;margin:0;"/>审批不通过
				</li> 
			</ul>
			<ul class="clearfix" style="margin-top:10px;">
				<li id="field_userName" class="li_form" style="width: 950px;">
					<label for="isNew" class="editableLabel" style="float:left;padding-top:10px;"">审批意见：</label>
				   		<textarea name="remark" rows="2" cols="30" style="width:800px;height:40px;"></textarea>
				</li>
			</ul>
			
		</c:if>	
	</c:if>
	<ul>
	<c:if test="${param.flowStep!='CXTJ' }">	
		<h5 class="form-section-title"><a name="colltionPlan">审批日志</a></h5>
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
		</c:if>	
	</ul>
</form>
	<!-- editTable -->
       <div id="dailogs1" class="modal hide fade" role="dialog" tabIndex="-1" style="width: 500px; height: 210px;">
		   <div class="modal-header">
		         <a type="button" class="close" data-dismiss="modal" aria-hidden="true">×</a>
		         <h3 id="dtitle"></h3>
  		   </div>
		    <div class="modal-body" style="width:1070px;">
			     <div id="dialogbody" ></div>
		    </div>
		    <div id="bottom_button" class="modal-footer" style="text-align: center;">
        		<input id="ok_Contract_dailogs" type="button" value="提交" class="btn btn-success"/>
    		</div>
        </div>	
	<!--按钮组-->
	<div id="bottom_button" class="modal-footer" style="text-align: center;">
		<c:if test="${page!='detail'}">	
			<c:if test="${param.flowStep!='CXTJ' }">	
		   		<input id="ok_Contract_Add" type="button" value="提交" class="btn btn-success"/>
		   	</c:if>
	   	</c:if>
   		<input id="back_page" type="button" value="返回" class="btn"/>
	</div>
	
   </div>
</div>

 <script language="javascript">
 seajs.use('js/page/sales/contractFound/foundApprove',function(foundApprove){
	 foundApprove.init();
    });
</script>

</body>
</html>