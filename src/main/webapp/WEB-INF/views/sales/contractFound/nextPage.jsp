<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/global.jsp"%>

<html>
	<head>
		<title>投标信息</title>
		<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">

</head>
 <style type="text/css">
		._sino_td_one{
			float:left;
			margin-right:3px;
			width:100px;
			height: 30px;
		}
		.one{
			margin-left:30px;
		}
		._sino_td_two{
			float:left;
			height: 30px;
		}
		._sino_table tr {
		    height: 50px;
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
		.dropdown-menu{
			z-index:11111;
		}
		.handprocess_order li.li_form {
		    display: inline-block;
		    float: left;
		    font-size: 12px;
		    line-height: 19px;
		    padding: 0px 0;
		    width: 300px;
		}
		.table th, .table td {
		    border-top: 1px solid #dddddd;
		    line-height: 12px;
		    padding: 7px;
		    text-align: left;
		    vertical-align: top;
		}
		
.modal {
    background-clip: padding-box;
    background-color: #ffffff;
    border: 1px solid rgba(0, 0, 0, 0.3);
    border-radius: 6px;
    box-shadow: 0 3px 7px rgba(0, 0, 0, 0.3);
    left: 30%;
    margin-left: -301px;
    outline: medium none;
    position: fixed;
    top: 10%;
    width: 1100px;
    z-index: 1046;
}

	</style>
<body>    
	<div class="portlet-body form" style="margin-left: 40px;margin-top: -15px;">
	<div class="handprocess_order" id="SheetDiv" style="margin-top: 4px;margin-left: -12px;">
			<ul class="clearfix" style="height: 20px;">
				<li id="field_userName" class="li_form" style="width:400px;">
					<label class="">名称：</label>${model.applyFundsName}
				</li>
				<li id="field_userName" class="li_form" style="width:400px">
					<label class=''  >客户：</label><div id='_customerInfoName' ></div>
					<input type="hidden" id='_eoss_sales_customerId' name='cusCustomerId' value="${model.cusCustomerId}" />
				</li>
			</ul>
    		<ul class="clearfix" style="height: 20px;">
				<li id="field_userName" class="li_form" style="width:400px">
					<label class="">伙伴公司：</label>${model.partnerCompany}
				</li>
				<li id="field_userName" class="li_form" style="width:400px">
					<label class="">盈    利：</label>${model.expectProfit}
				</li>
			</ul>
			<ul class="clearfix" style="height: 20px;">
				<li id="field_userName" class="li_form" style="width:600px">
					<label class="">说明：</label>${model.applyDesc}
				</li>
			</ul>
    		<ul class="clearfix" >
    		<div style="width:460px;float:left; margin-right: 10px;">
					<h5 class="form-section-title"><a name="colltionPlan">往来款(总计：${amount}元)</a></h5>
				<table id='table_size' width="90%" cellspacing="0" border="0" class="table  table-bordered table-striped">
							<thead>
								<tr>
									<th>金额</th>
									<th>支付方式</th>
									<th>用途</th>
								</tr>
							</thead>
							<tbody>
								 <c:forEach var="tableDataSize" items="${size}" varStatus="status">
								 <tr>
								 	<td >${tableDataSize.applyPrices}</td>
								 	<td id="payType_${status.index}">${tableDataSize.payType}</td>
								 	<td id="payDesc_${status.index}">${tableDataSize.payDesc}</td>
								 	</tr>
								 </c:forEach>
							</tbody>
						</table>
				</div>
				<div style="width:460px;float:left;">
				
				<c:if test="${not empty chapter }">
				<div class=" row-inline collection_plan" >
					<h5 class="form-section-title"><a name="colltionPlan">章（借出）</a></h5>
				</div>
				<table id='table_chapter' width="90%" cellspacing="0" border="0" class="table  table-bordered table-striped">
							<thead>
								<tr>
									<th>章类型</th>
									<th>使用时间</th>
									<th>归还时间</th>
									<c:if test="${chap=='yes'}">
										<th>目前借用人</th>
									</c:if>
								</tr>
							</thead>
							<tbody>
								 <c:forEach var="tableDataSize" items="${chapter}" varStatus="status">
								 <tr>
								 	<td id="chapterType_${status.index}">${tableDataSize.chapterId}</td>
								 	<td ><fmt:formatDate  value="${tableDataSize.borrowDate}" pattern="yyyy-MM-dd"/></td>
								 	<td ><fmt:formatDate  value="${tableDataSize.returnDate}" pattern="yyyy-MM-dd"/></td>
									 	<c:if test="${tableDataSize.creatname!=''&&tableDataSize.creatname!=null}">
									 		<td >${tableDataSize.creatname}</td>
									 	</c:if>
								 </tr>
								 </c:forEach>
							</tbody>
						</table>
			<div style="width:460px;float:left; margin-right: 10px;"></div>
			</c:if>
			</div>
			<div style="width:460px;float:left; margin-right: 10px;">
			<c:if test="${not empty fication }">
				<div class=" row-inline collection_plan" >
					<h5 class="form-section-title"><a name="colltionPlan">资质（借出）</a></h5>
				</div>
				<table id='table_fication' width="50%" cellspacing="0" border="0" class="table  table-bordered table-striped" >
							<thead>
								<tr>
									<th>资质名称</th>
									<th>使用时间</th>
									<th>归还时间</th>
									<c:if test="${ficat=='yes'}">
										<th>目前借用人</th>
									</c:if>
								</tr>
							</thead>
							<tbody>
								 <c:forEach var="tableDataSize" items="${fication}" varStatus="status">
								 <tr>
								 	<td id="qualifications_${status.index}">${tableDataSize.qualificationId}</td>
								 	<td ><fmt:formatDate  value="${tableDataSize.forecastBorrowDate}" pattern="yyyy-MM-dd"/></td>
								 	<td ><fmt:formatDate  value="${tableDataSize.forecastReturnDate}" pattern="yyyy-MM-dd"/></td>
									 	<c:if test="${tableDataSize.creatname!=''&&tableDataSize.creatname!=null}">
									 		<td >${tableDataSize.creatname}</td>
									 	</c:if>
								 	</tr>
								 </c:forEach>
							</tbody>
						</table>
				
				<div style="width:460px;float:left;"></div>
				</c:if>
				</div>
				<c:if test="${not empty certificate }">
				<div style="width:460px;float:left; margin-right: 10px;">
				<div class=" row-inline collection_plan" >
					<h5 class="form-section-title"><a name="colltionPlan">证书</a></h5>
				</div>
				<table id='table_certificate' style="text-align: center;" width="50%" cellspacing="0" border="0" class="table  table-bordered table-striped">
							<thead>
								<tr>
									<th>证书名称</th>
									<th>数量</th>
								</tr>
							</thead>
							<tbody>
								 <c:forEach var="tableDataSize" items="${certificate}" varStatus="status">
								 	<tr>
								 	<td id="certificateType_${status.index}">${tableDataSize.certificateType}</td>
								 	<td >${tableDataSize.certificateNum}</td>
								 	</tr>
								 </c:forEach>
							</tbody>
						</table>
				</c:if>
			</ul>
   		</div>
    </div>
 <script language="javascript">
    seajs.use('js/page/sales/contractFound/nextPage',function(nextPage){
    	nextPage.init();
    });
</script>
</body>
</html>