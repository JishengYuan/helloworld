<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/global.jsp"%>
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
.advancedSearch{
padding: 10px 0;
}
.advancedSearch .advancedSearch_btn {
    border-top: 0px dashed #ccc;
    height: 30px;
    margin-top: 0px;
    padding-top: 10px;
}
.tableli {
	width: 300px;
	float:left;
}
</style>
<div class="showbodytt" style="width: 100%;">
	<div >
		<div id="allQuery_search_div">
			<ul class="clearfix">
				<li class="tableli">
					<label class="editableLabel">客户经理：</label>
					<div id="search_creator" style="width: 150px;margin-left: 70px;margin-top: -25px;" ></div>
				</li>
				<li class="tableli" style="width:330px;">
					<label for="key" class="editableLabel">合同金额(元)：</label><input type="text" id="search_contractAmount" name="contractAmount" style="width: 70px;"  maxlength="256">万
					－
					<input type="text" id="search_contractAmountb" name="contractAmount" style="width: 70px;" maxlength="256">万
				</li>
				<li class="tableli">
					<label for="key" class="editableLabel">厂商：</label>
					<div id="_vendor_div" style="width: 150px;margin-left: 70px;margin-top: -20px;"  >
					</div>
					<input type="hidden" id="search_vendor"/>
				</li>
			</ul>
			<ul class="clearfix">
				<li class="tableli">
					<label for="title" class="editableLabel">客户名称：</label><input type="text" id="search_customerName" maxlength="256" placeholder="请输入客户名称">
				</li>
				<li class="tableli" style="width:500px;">
					<label class="editableLabel"  style="margin-top: -13px;">生效起止日期：</label>
					<div class="input-append date">
			  		    <input data-format="yyyy-MM-dd" style="width:80px;margin-left: -6px;" type="text" id="startTime"></input>
					    <span class="add-on">
					      <i data-time-icon="icon-time" data-date-icon="icon-calendar">
					      </i>
						</span>
					  </div>-
					  <div class="input-append date">
					 	 <input data-format="yyyy-MM-dd" style="width:80px;margin-left: -6px;" type="text" id="endTime"></input>
			    		  <span class="add-on">
					      <i data-time-icon="icon-time" data-date-icon="icon-calendar">
					      </i>
						  </span>
					  </div>
				</li>
			</ul>
			
			<ul class="clearfix">
				<li class="tableli">
					<label class="editableLabel">客户类型：</label>
					<div id="_customerType_div" style="width: 150px;margin-left: 70px;margin-top: -20px;"  >
					</div>
					<input type="hidden" id="search_customerType"/>
				</li>
				<li class="tableli" style="width:330px;">
					<label class="editableLabel">合同    &nbsp; 编码：</label>
					<input type="text" id="search_contractCode" style="margin-left:-6px;"/>
				</li>
				<li class="tableli">
					<label for="title" class="editableLabel">产品技术：</label>
					<div id="_proTechnology_div" style="width: 150px;margin-left: 70px;margin-top: -20px;"  >
					</div>
					<input type="hidden" id="search_proTechnology"/>
				</li>
			</ul>
			<ul class="clearfix">
				
				<li class="tableli">
					<label class="editableLabel">项目地点：</label>
					<input type="text" id="search_projectSite" style="margin-left:-6px;"/>
				</li>
				<li class="tableli" style="width:340px;">
					<label class="editableLabel">内部合同编号：</label>
					<input type="text" id="search_preContractCode" style="margin-left:-6px;"/>
				</li>
			</ul>
			
			<div class="advancedSearch_btn">
			   <div style="float:left;">
			      <a class="btn btn btn-primary" id="_importcontract_btn" style="font-size:12px;" href="#_sino_eoss_contract_import_page" aria-hidden="true" data-toggle="modal"><i class="icon-cloud-upload"></i>&nbsp;导入合同信息</a>
                  <a href="#" class="btn btn-primary" id="_update_search_type" style="font-size:12px;"><i class="icon-cloud-upload"></i>&nbsp;同步客户类型</a>
                  <a href="#_sino_eoss_contract_import_page" aria-hidden="true" data-toggle="modal" class="btn btn-primary" id="_importventor_btn" style="font-size:12px;"><i class="icon-cloud-upload"></i>&nbsp;导入厂商信息</a>
                  <a href="#_sino_eoss_contract_import_page" aria-hidden="true" data-toggle="modal" class="btn btn-primary" id="_importpretechnology_btn" style="font-size:12px;"><i class="icon-cloud-upload"></i>&nbsp;导入产品技术信息</a>
				</div>
                <div style="float:right;">
			      <a href="#" class="btn btn-success" id="advancedSearch_btn" style="font-size:12px;"><i class="icon-search"></i>&nbsp;查询</a>
				</div>
			</div>
		</div>
	</div>
</div>