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
.showbody{
	overflow:visible;
}
</style>
<div class="showbody" style="width: 100%;overflow:visible;">
	<div >
		<div id="allQuery_search_div">
			<ul class="clearfix">
				<li  class="advancedSearch_li uic_form_select" style="width:350px;">
					<label for="title" class="editableLabel">发票报销名称：</label>
					<input type="text" id="search_reimbursementName" name="contractName" style="width: 170px;" maxlength="256" placeholder="模糊匹配">
				</li>

				<li class="advancedSearch_li uic_form_select" style="width:350px;">
					<label for="title" class="editableLabel">报销金额(元)：</label>
					<input type="text" id="search_reimbursementAmount" name="contractAmount" style="width: 180px;height: 28px;" class="InpText f12" maxlength="256" placeholder="输入报销金额">
					
				</li>
				 <li class="advancedSearch_li uic_form_select" style="width: 250px;">
				 			<input type="checkbox" id="unpass" name="isAgree"  value='1' checked="checked" style="margin-top:-1px;"/>&nbsp;未报销&nbsp;&nbsp;&nbsp;
						    <input type="checkbox" id="pass" name="isAgree"  value='2' style="margin-top:-1px;" />&nbsp;已报销&nbsp;&nbsp;&nbsp;
				 </li>
			</ul>
			 </ul>
		   	<ul class="clearfix">
			   <li class="advancedSearch_li uic_form_select"  style='height:30px;margin-top:-5px;width: 350px;'>
					<label for="title" class="editableLabel" style="margin-left:0px;">供应商名称：</label>
					<div id="supplierShortName" style="margin-top: -20px; margin-left: 106px; margin-bottom: 20px; "></div>
					<input type="hidden" id="search_supplierShortName" class="InpText f12 required" maxlength="256">
				</li>
				<li class="advancedSearch_li uic_form_select" style='height:30px;margin-top:-5px;width: 350px;'>
				     <label for="title" class="editableLabel">分项报销金额：</label>
				     <input type="text" id="search_amountPart" style="width: 180px;height:28px;" class="InpText f12" maxlength="256" placeholder="输入订单的报销金额">
				</li>	
			</ul>
			<div class="advancedSearch_btn">
			   <div style="float:left;">
			      <a href="#" class="btn btn-success" id="addApply_btn" style="font-size:12px;"><i class="icon-plus"></i>&nbsp;添加</a>
			    <!--   <a href="#" class="btn btn-danger" id="removeApply_btn" style="font-size:12px;" title="只能草稿状态的合同才可行删除"><i class="icon-minus"></i>&nbsp;删除</a> -->
				</div>
                <div style="float:right;">
                <a href="#" class="btn btn-primary" id="advancedSearch_btn" style="font-size:12px;"><i class="icon-search"></i>&nbsp;查询</a>

				</div>
			</div>
		</div>
	</div>
</div>
<script language="javascript">
	seajs.use('js/page/business/reimbursementApply/search', function(search) {
		search.init();
	});
</script>