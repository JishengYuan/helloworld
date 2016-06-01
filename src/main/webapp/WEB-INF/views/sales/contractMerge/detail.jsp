<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/global.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">


<link rel="stylesheet"
	href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/internetTable.css"
	type="text/css"></link>
<link rel="stylesheet"
	href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/processList.css"
	type="text/css"></link>

<title>新闻发布</title>
<style type="text/css">
   #allQuery_search_div{
     font-size:14px;
   }
</style>
</head>
<body>
	<div class="showbody" style="width: 100%;">
		<div class="ultrapower-table-box">
			<strong class="title">合同管理-->合同合并详情
			</strong>
		</div>
		
		<div class="advancedSearch" style="">
		<div id="allQuery_search_div">
			<form id='_sino_eoss_sales_merge_detailform' method="post">
			<input type="hidden" id="_contractIds_input" name="contractIds" value="${model.contractIds }" />
			<input type="hidden" id="_contractIds_input_" name="contractId" value="${model.contractId }" />
			<input type="hidden" name="id" value="${model.id }" />
			<ul class="clearfix" id="">
				<li class="advancedSearch_li"  style="height: 30px;width:400px;">
					<label class="editableLabel"  style="float:left;width: 90px;">名称：</label>
					${model.name }
				</li>
				<li class="advancedSearch_li"  style="height: 30px;width:400px;">
					<label class="editableLabel"  style="float:left;width: 90px;">申请时间：</label>
					${model.applyTime }
				</li>
			</ul>
			<ul class="clearfix" id="">
				<li class="advancedSearch_li"  style="height: 30px;width:888px;">
					<label class="editableLabel"  style="float:left;width: 90px;">备注：</label>
					${model.remark }
				</li>
			</ul>
			<hr>
			<div>合并后的合同（主合同）：<c:forEach var="sale" items="${sales}" varStatus="status">
					<c:if test="${model.contractId == sale.id}">
						<a target="_blank" href="${ctx }/sales/contract/detail?id=${sale.id}" style="color:#005EA7;">
								${sale.contractName }
						</a>
					</c:if>
				</c:forEach>
			</div>
			<div>
			  <div style="width:350px;padding-left:42px;margin-top:15px;">被合并的合同列表</div>
			       <table style="width:500px;margin-left:170px;margin-top:-15px;" class="table table-bordered">
			  		<c:forEach var="sale" items="${sales}" varStatus="status">
					<c:if test="${model.contractId != sale.id}">
						<tr>
						 <td>
						<a target="_blank" href="${ctx }/sales/contract/detail?id=${sale.id}" style="color:#005EA7;">
								${sale.contractName }
						</a>
						</td>
					</tr>
					</c:if>
				</c:forEach>
				</table>
			</div>
			
			<c:if test="${systemUser.userName == 'zhangjq' }">
			<ul class="clearfix" id="" >
				<li class="advancedSearch_li"  style="height: 30px;width:400px;margin-top:10px;">
					<label class="editableLabel"  style="float:left;width: 90px;color:#ff9933;">合并审批：</label>&nbsp;&nbsp;
					<input type="radio" name="state" value="2" checked style="margin-top:0;margin-right:5px;"/><span>同意</span>
					</br>
					<input type="radio" name="state" value="0" style="margin-left:100px;margin-top:0;margin-right:5px;" /><span>不同意</span>
				</li>
			</ul>
			</c:if>
			</form>
			<p class="advancedSearch_btn" style='margin-top:30px;'>
			<c:if test="${systemUser.userName == 'zhangjq' }">
				<a style="font-size:12px;" id="_sales_merge_submit" class="btn btn-primary"><i class="icon-ok"></i>&nbsp;提交</a>
			</c:if>
				<a style="font-size:12px;" id="_sales_merge_back" class="btn"><i class="icon-fast-backward"></i>&nbsp;返回</a>
			</p>
		</div>
	</div>
		
	</div>
	
	<script language="javascript">
		seajs.use('js/page/sales/contractMerge/detail', function(detail) {
			detail.init();
		});
	</script>
</body>
</html>