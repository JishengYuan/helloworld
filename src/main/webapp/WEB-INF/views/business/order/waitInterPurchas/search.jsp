<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/global.jsp"%>
<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formSelect.css" type="text/css"></link>
<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formstyle.css" type="text/css"></link>
		<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formSearchTip.css" type="text/css" ></link>
<style type="text/css">
.advancedSearch li.advancedSearch_li label {
    display: inline-block;
    line-height: 18px;
    text-align: right;
    vertical-align: middle;
    width: 100px;
}
p, ul, ol, li, dl, dd, dt, form {
    list-style: none outside none;
}
.advancedSearch .advancedSearch_btn {
    border-top: 0px dashed #ccc;
    height: 30px;
    margin-top: 0px;
    padding-top: 5px;
}
.advancedSearch li.advancedSearch_li {
    float: left;
    font-size: 12px;
    padding: 3px 0;
    width: 300px;
}
.advancedSearch {
    padding: 10px 0;
}

</style>
<div class="showbody" >
	<div class="advancedSearch"  style="height: 25px;" >
		<div id="allQuery_search_div">
			<ul class="clearfix">
			
				<li class="advancedSearch_li">
				<label  class="editableLabel"  style="margin-top: -14px;">到货时间：</label>
				    <div style="position: relative;" class="input-append date">
						 <input data-format="yyyy-MM-dd"  name="expectedDeliveryTime" id="expectedDeliveryTime"  type="text"  style="width:150px"></input>
						    <span class="add-on">
						      <i data-time-icon="icon-time" data-date-icon="icon-calendar">
						      </i>
							</span>
					  </div>
			     </li>
				<li class="advancedSearch_li">
					<label class="editableLabel">申请人：</label>
					<div id="creator" style="margin-top: -24px; margin-left: 108px; "></div>
					      <input type="hidden" id="creator" name="creator" />
				</li>
				<li class="advancedSearch_li" >
					<p style="float:right;">
							<a style="font-size:12px;"  id="reset_btn" class="btn" href="#">&nbsp;重置&nbsp;</a>
							<a style="font-size:12px;" id="advancedSearch_btn" class="btn btn-primary" href="#"><i class="icon-search"></i>&nbsp;查询</a>
					</p>
				</li>
			</ul>
					</div>
		</div>
	</div>
</div>
<script language="javascript">
	seajs.use('js/page/business/order/waitInterPurchas/search', function(search) {
		search.init();
	});
</script>