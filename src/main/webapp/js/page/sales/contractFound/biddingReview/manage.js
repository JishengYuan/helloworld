define(function(require, exports, module) {
	var $ = require("jquery");
	require("bootstrap");
	require("coverage");
	require("bootstrap-datetimepicker");
	require("stringHelper");
	require("internetTable");
	require("confirm_dialog");
	require("uic/message_dialog");
	var StringBuffer = require("stringBuffer");
	require("changTag");
	require("formSelect");
	//子页面关闭刷新该父页面
	childColseRefresh=function(){
		$("#uicTable").tableOptions({
			pageNo : '1',
			addparams:[]
			});
			$("#uicTable").tableReload();
    }
	


	var contract = {
			config: {
				module: 'contract',
				uicTable : 'uicTable',
	            namespace: '/sales/contractFound/biddingReview'
	        },
	        methods :{
	        	initDocument : function(){
	        		contract.doExecute('initTable');
		        },
		        initTable : function(){
		        	var grid=$("#"+contract.config.uicTable).uicTable({
		        		title : "投标管理-->投标资质章信息",
		        		width : 'auto',
						height : 'auto',
						gridClass : "bbit-grid",
		        		searchItems:'name',
	    			    columns:[{ code: 'applyFundsName',name : '投标项目',width:'30%',process:contract.methods.handleLink},
	    			             { code: 'ShortName',name : '客户',width:'30%'},
			        			 { code: 'payTime', name :'投标时间',width:'15%'},
			        			 { code: 'totalFunds',name : '金额',width:'15%',process:contract.methods.totalFunds},
			        			 { code: 'CreatorName',name : '申请人',width:'10%'},
	    			    ],
	    			    url: ctx+contract.config.namespace+'/getBiddingReviewList?tmp='+Math.random(),
	    			    pageNo:1,
	    			    pageSize:10,
	    			    moreCellShow : false,
				        searchFun:function(){
				        	$("#"+contract.config.uicTable).tableOptions({
				        		pageNo : '1',
				        		addparams:[{
				        			name:"test",
				        			value:$('.searchs').find('input').first().val()
				        		}]
				        	});
				        	$("#"+contract.config.uicTable).tableReload();
				        },
				        //advancedFun:contract.methods.advancedFunction
				        });
		        	$('.searchs').find('input').first().hide();
		        	$('.searchs').find('label').first().hide();
		        	$('.doSearch').hide();
		        	$('.doAdvancedSearch').hide();
			        	//默认触发了搜索
			        	$('.doAdvancedSearch').click();
			        	//加载查询
			        	$(".advancedSearch",$("#"+contract.config.uicTable)).load(ctx+contract.config.namespace+'/search?tmp='+Math.random(),function(){
			        		$(".advancedSearch",$("#"+contract.config.uicTable)).css("height","auto");
			        		//绑定“查询”按钮
			        		$("#advancedSearch_btn").unbind('click').click(function () {
			        			contract.methods.doAdvancedFun();
				        	});
			        	});
		        },
		        handleLink:function(divInner, trid, row, count){
		        	var str='<a href="'+ctx+'/sales/contractFoundOpt/bidInfo?id='+row.ID+'&tmp='+Math.random()+'" target="_blank" style="color:#005EA7;">'+divInner+'</a>';
	    			return str;
		        },
		        totalFunds:function(divInner, trid, row, count){
		        	//return "￥"+fmoney(divInner,2);
		        	return divInner;
		        },
		        advancedFunction:function(type) {
		        	if(type){
		        		$(".advancedSearch",$("#"+contract.config.uicTable)).css("height","auto");
		        		//绑定“查询”按钮
		        		$("#advancedSearch_btn").unbind('click').click(function () {
		        			contract.methods.doAdvancedFun();
			        	});
		        	}
		        },
		        doAdvancedFun:function(){
		        	var contractName = $('#search_contractName').val();
		        	var contractAmount = $('#search_contractAmount').val();
		        	var contractAmountb = $('#search_contractAmountb').val();
		        	
		        	$("#"+contract.config.uicTable).tableOptions({
		        		pageNo : '1',
		        		addparams:[{name:"contractName",value:contractName},
		        		           {name:"contractAmountb",value:contractAmountb},
		        		           {name:"contractAmount",value:contractAmount}]
		        	});
		        	$("#"+contract.config.uicTable).tableReload();
		        },
		        reload : function(){
		        	var sd=$("#"+contract.config.uicTable).getTableCheckedRows();
		        	$("#"+contract.config.uicTable).tableOptions({
		        		pageNo : '1',
		        		addparams:[]
		        	});
		        	$("#"+contract.config.uicTable).tableReload();
		        },
		        cleanSearchInput : function(){
		        	$("#search_contractName").attr("value","");
		        	$("#search_contractCode").attr("value","");
		        	$("#search_contractAmount").attr("value","");
		        },
				
		        openurl : function(opts){
		        	var options = {};
		    		options.murl = ctx+contract.config.namespace + opts.url;
		    		if(opts.id){
		    			options.keyName = 'id';
		    			options.keyValue = opts.id;
		    		}
		    		window.open(options.murl);
		        }
	        },
	        /**
			 * 执行方法操作
			 */
			doExecute : function(flag, param) {
				var method =contract.methods[flag];
				if( typeof method === 'function') {
					return method(param);
				} else {
					alert('操作 ' + flag + ' 暂未实现！');

				}
			}
	}
	exports.init = function() {
		contract.doExecute('initDocument');
	}
	

	function fmoney(s, n) { 
		n = n > 0 && n <= 20 ? n : 2; 
		s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + ""; 
		var l = s.split(".")[0].split("").reverse(), r = s.split(".")[1]; 
		t = ""; 
		for (i = 0; i < l.length; i++) { 
		t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : ""); 
		} 
		return t.split("").reverse().join("") + "." + r; 
		}
	

  
});