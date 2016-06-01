<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/global.jsp"%>

<html>
	<head>
		<title>投标信息</title>
		<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
		<%@ include file="/common/include-base-boostrap-styles.jsp" %>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formSelect.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formstyle.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/dialog.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/tip.css" type="text/css" ></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/user-selection.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formSearchTip.css" type="text/css" ></link>
	<link rel="stylesheet" href="${ctx}/skin/default/editgrid/editgrid.css" type="text/css" />

	<link href="${ctx}/skin/default/sales/sale.css" rel="stylesheet"/>
	<link href="${ctx}/js/plugins/select2/css/select2.css" rel="stylesheet"/>
	<script src="${ctx}/js/plugins/seajs/sea.js" id="seajsnode" type="text/javascript"></script>
	<script src="${ctx}/js/plugins/seajs/config.js" type="text/javascript"></script>


<style type="text/css">
	.row-input{
		width: 180px;
		height:28px"
	}

.row-input-oneColumn {
    width: 480px;
}
.select2-container .select2-choice {
    border-radius: 4px;
    color: #555555;
    font-size: 14px;
    height: 20px;
    padding: 4px 6px;
    width: 180px;
    background-color: #ffffff;
    border: 1px solid #cccccc;
    box-shadow: 0 1px 1px rgba(0, 0, 0, 0.075) inset;
    transition: border 0.2s linear 0s, box-shadow 0.2s linear 0s;
    display: inline-block;
    margin-bottom: 0;
    vertical-align: middle;
    background-image: none;
}
.handprocess_order li.li_form {
    display: inline-block;
    float: left;
    font-size: 14px;
    line-height: 25px;
    padding: 3px 0;
    width: 300px;
}
body {
    font-size: 12px;
}
.table {
    margin-bottom: 20px;
    width: 100%;
    margin-left: 0px;
}

.handprocess_order {
    padding: 0px;
    border-bottom: 0px solid #d7d7d7;
}
.handprocess_order li.li_form label {
    color: #888;
    display: inline-block;
    float: left;
    text-align: right;
    width: 98px;
}
.handprocess_order .li_form label.editableLabel {
    color: #eeb422;
}

.handprocess_order .li_form label.editableLabel2 {
    color: #000000;
    margin-bottom: 0;
    margin-top: 3px;
}

</style>

</head>

<body style="width: 100%;overflow-x: hidden; ">   

<div class="salecontent">
		<div class="top">
			<div class="caption">
				<i class="icon-reorder"></i>&nbsp;&nbsp;&nbsp;审批投标信息
			    <!-- <span class="tright" >订单编号：<span id="Code"></span></span> -->
			</div>
			<div class="tools">
				<!--a href="javascript:;" class="reload"><i class="icon-repeat"></i>返回</a-->
			</div>
		</div>
	</div>	 

<div class="portlet-body form">
<div class="handprocess_order" id="SheetDiv">
			<br><br>
			<form id="_sino_eoss_cuotomer_addform" class='form-horizontal' method="post" action="">
			<input type="hidden" id="foundId" name="foundId" value="${model.id }"/>
			<input type="hidden" id="totalFunds" name="totalFunds" value="${model.totalFunds }"/>
			<ul class="clearfix" >
				<li id="field_userName" class="li_form" style="width:400px">
					<label class="editableLabel">投标项目：</label>${model.applyFundsName}
				</li>
				<li id="field_userName" class="li_form" style="width:400px">
					<label class="editableLabel">客户：</label><div id='_customerInfoName' ></div>
					<input type="hidden" id='_eoss_sales_customerId' name='cusCustomerId' value="${model.cusCustomerId}" />
				</li>
			</ul>
    		<ul class="clearfix" >
				<li id="field_userName" class="li_form" style="width:400px">
					<label class="editableLabel">伙伴公司：</label>${model.partnerCompany}
				</li>
				<li id="field_userName" class="li_form" style="width:400px">
					<label class="editableLabel">盈    利：</label>${model.expectProfit}
				</li>
			</ul>
			<ul class="clearfix" >
				<li id="field_userName" class="li_form" style="width:600px">
					<label class="editableLabel">说明：</label>${model.applyDesc}
				</li>
			</ul>
			
			
		<div style="clear:both;"></div>
		<div class=" row-inline" >
				<hr size=1 class="dashed-inline">
		</div>
	
		<ul class="clearfix" >
			<div style="width:450px;float:left; margin-right: 10px;">
				<div class=" row-inline collection_plan" >
					<h5 class="form-section-title"><a name="colltionPlan">往来款(总计:${amount})</a></h5>
				</div>
				<table id='table_size' width="50%" cellspacing="0" border="0" class="table  table-bordered table-striped">
							<thead>
								<tr>
									<th>金额</th>
									<th>支付方式</th>
									<th>用途</th>
								</tr>
							</thead>
							<tbody>
								 <c:forEach var="tableDataSize" items="${model.salesContractSizeModel}" varStatus="status">
								 <tr>
								 	<td >${tableDataSize.applyPrices}</td>
								 	<td id="payType_${status.index}">${tableDataSize.payType}</td>
								 	<td id="payDesc_${status.index}">${tableDataSize.payDesc}</td>
								 	</tr>
								 </c:forEach>
							</tbody>
						</table>
				</div>
				<div style="width:450px;float:left;">
				<div class=" row-inline collection_plan" >
					<h5 class="form-section-title"><a name="colltionPlan">章</a></h5>
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
								 	<td id="chapterType_${status.index}">${tableDataSize.chapterType}</td>
								 	<td ><fmt:formatDate  value="${tableDataSize.borrowDate}" pattern="yyyy-MM-dd"/></td>
								 	<td ><fmt:formatDate  value="${tableDataSize.returnDate}" pattern="yyyy-MM-dd"/></td>
								 	<%-- <c:if test="${chapter.creatname!=''}">
								 		${chapter.creatname}
								 	</c:if> --%>
								 	</tr>
								 </c:forEach>
							</tbody>
						</table>
					</div>
			</ul>
			<ul class="clearfix" >
				<div style="width:450px;float:left; margin-right: 10px;">
				<div class=" row-inline collection_plan" >
					<h5 class="form-section-title"><a name="colltionPlan">资质</a></h5>
				</div>
				<table id='table_fication' width="50%" cellspacing="0" border="0" class="table  table-bordered table-striped">
							<thead>
								<tr>
									<th>资质名称</th>
									<th>资质类型</th>
								</tr>
							</thead>
							<tbody>
								 <c:forEach var="tableDataSize" items="${model.salesContractQualificationModel}" varStatus="status">
								 <tr>
								 	<td id="qualifications_${status.index}">${tableDataSize.qualifications}</td>
								 	<td id="qualificationsType_${status.index}">${tableDataSize.qualificationsType}</td>
								 	</tr>
								 </c:forEach>
							</tbody>
						</table>
				</div>
				<div style="width:450px;float:left;">
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
			</ul>
			
			<ul class="clearfix">
			<h5 class="form-section-title"><a name="colltionPlan">请选择一项填写</a></h5>
		   		<li class="li_form" style="width:100%">
		   		<input type="radio" name="isAgree" value="1" checked="checked" />项目划归
		   			<div id="contract" class="_contract_hidden">
							<div id="_select_contractName_div" style="width:500px;margin-top:-25px;margin-left:80px;">
							</div>
							<input type="hidden" id="contractCode" name="contractCode" />
					</div>
				</li>
				<li class="li_form" style="width:100%">
					<input type="radio" name="isAgree" value="0"/>往来款返还
					<div id='returns' class=" _return_hidden" style="display: none;margin-top:-25px;margin-left:95px;">
							<input type="text" id="returnPrice" name="returnPrice" value=""/>
					</div>
				</li>
			</ul>
			
</form>
		
	<!--按钮组-->
	<div id="bottom_button" class="modal-footer" style="text-align: center;">
	   	<input id="ok_Contract_Add" type="button" value="确定" class="btn btn-success"/>
   		<input id="back_page" type="button" value="返回" class="btn"/>
	</div>
	
   </div>
</div>

 <script language="javascript">
 seajs.use('js/page/sales/contractFound/biddingReview/bidApprove',function(bidApprove){
	 bidApprove.init();
    });
</script>

</body>
</html>