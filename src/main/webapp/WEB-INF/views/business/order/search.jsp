<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/global.jsp"%>

<style type="text/css">
.advancedSearch {
    padding: 1px 0;
}
.advancedSearch li.advancedSearch_li label {
    display: inline-block;
    line-height: 18px;
    text-align: right;
    vertical-align: middle;
    width: 100px;
}

.advancedSearch li.advancedSearch_li {
    float: left;
    font-size: 12px;
    padding: 2px 0;
    width: 290px;
}
.advancedSearch .advancedSearch_btn {
    border-top: 0px dashed #ccc;
    height: 30px;
    margin-top: 0px;
    padding-top: 5px;
    margin-bottom: 10px;
}
.handprocess_order .li_form label.editableLabel {
    color: #000000;
}
/* label.editableLabel {
    color: #000000;
} */
.selectDiv .uicSelectData ul li {
    height: 18px;
    line-height: 22px;
    padding: 0 0 3px;
}
</style>
<div class="showbody" style="width: 100%;">
	<div class="advancedSearch" style=" ">
		<div id="allQuery_search_div">
		    <ul class="clearfix">
				<li class="advancedSearch_li uic_form_select" style='height:30px;'>
					<label for="title" class="editableLabel" style=' margin-left:0px;'>订单类型：</label>
					<div id="orderType" style="margin-top: -20px; margin-left: 106px; margin-bottom: 20px; "></div>
					<input type="hidden" id="search_orderType" class="InpText f12 required" maxlength="230">
				</li>
				<li class="advancedSearch_li uic_form_select"  style='height:30px;'>
					<label for="title" class="editableLabel" style="margin-left:0px;">供应商名称：</label>
					<div id="supplierShortName" style="margin-top: -20px; margin-left: 106px; margin-bottom: 20px; "></div>
					<input type="hidden" id="search_supplierShortName" class="InpText f12 required" maxlength="256">
				</li>
				<li class="advancedSearch_li uic_form_select"  style='height:30px;'>
					<label for="title" class="editableLabel" style="margin-left:0px;">采购分类：</label>
					<div id="purchaseType" style="margin-top: -20px; margin-left: 106px; margin-bottom: 20px; "></div>
					<input type="hidden" id="search_purchaseType" class="InpText f12 required" maxlength="256">
				</li>
			</ul>
			<ul class="clearfix">
				
				<li  id="searchCon_flowName" class="advancedSearch_li uic_form_select"  style='height:36px;'>
				     <label for="title" class="editableLabel">订单编号：</label>
				     <input type="text" id="search_orderCode" style="width: 170px; height:28px;" class="InpText f12" maxlength="256" placeholder="模糊匹配订单编号">
				</li>
				<li id="searchCon_flowName" class="advancedSearch_li uic_form_select"  style='height:36px;'>
				     <label for="title" class="editableLabel">订单名称：</label>
				     <input type="text" id="search_orderName" style="width: 170px;height:28px;" class="InpText f12" maxlength="256" placeholder="模糊匹配订单名称">
				</li>	
				<li id="searchCon_flowName" class="advancedSearch_li uic_form_select"  style='height:36px;'>
				     <label for="title" class="editableLabel">订单金额：</label>
				     <input type="text" id="search_orderAmount" style="width: 170px;height:28px;" class="InpText f12" maxlength="256" placeholder="订单金额">
				</li>	
			</ul>
			
			<!-- <ul class="clearfix">
			    <li class="advancedSearch_li">
					<label for="title" class="editableLabel" style="margin-top: -59px; margin-left:580px;">供应商简称：</label>
					<div id="supplierShortName" style="margin-top: -59px; margin-left: 685px; margin-bottom: 20px; "></div>
					<input type="hidden" id="search_supplierShortName" style="width: 180px;" class="InpText f12 required" maxlength="256">
				</li>
				<li class="advancedSearch_li">
					<label for="title" class="editableLabel" style="margin-left:-226px;">付款情况：</label>
					<div id="pay" style="margin-top: -30px; margin-left: 104px; margin-bottom: 20px;"></div>
					<input type="hidden" id="search_pay" style="width: 208px;" class="InpText f12 required" maxlength="256">
				</li>
				<li class="advancedSearch_li">
					<label for="title" class="editableLabel" style="margin-top: -59px; margin-left:580px;">产品型号：</label>
					<div id="marque" style="margin-top: -59px; margin-left: 685px; margin-bottom: 20px; "></div>
					<input type="hidden" id="search_marque" style="width: 180px;" class="InpText f12 required" maxlength="256">
				</li>
			</ul> -->
			
			<!-- <p class="advancedSearch_btn"> -->
				<!-- <input type="button" value="添加" class="btn btn-success" id="addOrder_btn"> 
			    <input type="button" value="删除" class="submit" id="removeOrder_btn"> 
				<input type="button" value="查询" class="submit" id="advancedSearch_btn"/>  -->
				<!-- <input type="button" value="重置" class="submit" id="reset_btn"/> -->
				<div style="float:left;margin-bottom: 2px;">
					 <a href="#" class="btn btn-success" id="addOrder_btn" style="font-size:12px;"><i class="icon-plus"></i>&nbsp;添加</a>
					 <a href="#" class="btn btn-success" id="updateOrder_btn" style="font-size:12px;"><i class="icon-pencil"></i>&nbsp;修改</a>
				     <a href="#" class="btn btn-danger" id="removeOrder_btn" style="font-size:12px;"><i class="icon-minus"></i>&nbsp;删除</a>
				</div>
				<div style="padding-left:687px; margin-bottom: -30px;">
				<div style="margin-top:10px;">
					<input type="checkbox" class="accountCurrency" id="rmbId" name="accountCurrency"  value="1" style="margin-top:0px;"/> 人民币&nbsp;
					<input type="checkbox" class="accountCurrency" id="usdId" name="accountCurrency"  value="2" style="margin-top:0px;"/> 美元&nbsp;&nbsp;&nbsp;
				</div>
				</div>
                <div style="float:right;margin-bottom: 2px;">
               		 <a href="#" class="btn btn-primary" id="advancedSearch_btn" style="font-size:12px;"><i class="icon-search"></i>&nbsp;查询</a>
                </div>
			<!-- </p> -->
		</div>
	</div>
</div>
<script language="javascript">
	seajs.use('js/page/business/order/search', function(search) {
		search.init();
	});
</script>