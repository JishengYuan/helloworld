
define(function(require, exports, module) {
	var css  = require('js/plugins/ztree/zTreeStyle.css');
	var $ = require("jquery");
	var Map = require("map");
	require("jqBootstrapValidation");
	require("bootstrap");
	require("DT_bootstrap");
	require("uic/message_dialog");

	var StringBuffer = require("stringBuffer");
	    exports.init = function(){

	    	/*
			 * 绑定触发事件
			 */
			 $("#no_back").bind('click',function(){
				 _back();
	         });
	   }

	  	
	  	function _back(){
	  		window.close();
	  	}
			          
});		