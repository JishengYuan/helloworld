define(function(require, exports, module) {
	var $ = require("jquery");
	require("formSelect");
	require("bootstrap");
	require("bootstrap-datetimepicker");
//	require("jqBootstrapValidation");
	require("formUser");
//	var DT_bootstrap = require("DT_bootstrap");
//	var zTree = require("zTree_core");
//	var zTree_excheck = require("zTree_excheck");
	var dataTable$ = require("dataTables");
	var interPurchasSearch = {
			config: {
				module: 'interPurchasSearch',
				creator:'creator',
	        },
	        methods :{
	        	initDocument : function(){
	        		var isSingle = 'false';
	        		var accordion = null;
	        		var userForm = null;
	        		var userId = null;
	        		// 选人组件
	        		var $fieldStaff = $("#creator");
	        		var optionss = {
	        			inputName : "staffValues",
	        			// labelName : "选择用户",
	        			showLabel : false,
	        			width : "150",
	        			name : "code",
	        			value : "root",
	        			selectUser : false,
	        			radioStructure : true
	        		// radioStructure : field.single
	        		}
	        		optionss.addparams = [ {
	        			name : "code",
	        			value : "root"
	        		} ];
	        		$fieldStaff.formUser(optionss);
	        		
	        		getTime();
	        		interPurchasSearch.doExecute('getCreator');
		        },
		        getCreator : function(){
		        	/*var creator = $("#"+interPurchasSearch.config.creator);
		        	creator.addClass("li_form");
					var optionCompPosType = {
							writeType : 'show',
							showLabel : false,
							url : ctx+"/business/interPurchas/getCreator?tmp="+Math.random(),
							onSelect:function(node){
								$('#search_creator').val($("#"+interPurchasSearch.config.creator).formSelect("getValue")[0]);
							},
							width : "280"
					};
					creator.formSelect(optionCompPosType);*/
					
		        },
		        goBack : function(m){
		        }
	        },
	        /**
			 * 执行方法操作
			 */
			doExecute : function(flag, param) {
				var method =interPurchasSearch.methods[flag];
				if( typeof method === 'function') {
					return method(param);
				} else {
					alert('操作 ' + flag + ' 暂未实现！');

				}
			}
	}
	 
    /**
     *  时间插件
     */
      function  getTime(){
     	  $('.date').datetimepicker({ 
     	        pickTime: false
     	 
     	      }); 
      }
        
	exports.init = function() {
		interPurchasSearch.doExecute('initDocument');
	}
});