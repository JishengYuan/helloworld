define(function(require, exports, module) {
	var $ = require("jquery");

	var qcListDesc = {

	        methods :{
	        	initDocument : function(){

		        	//关闭按钮
//		        	$(".stampcell").live(  
//		        	        'mouseover',  
//		        	        function(e) {
//		        		if(qcListDesc.methods.checkHover(e,this)){
//			        		 $(this).children("p").show();
//			        		   $(this).animate({height:"160px"},500);
//			        		   $(this).parent().animate({height:"160px"},500);
//		        		}
//
//		        	  });
//		        	$(".stampcell").live('mouseout', function(e) {
//		        		  if(qcListDesc.methods.checkHover(e,this)){
//		        		  $(this).children("p").hide();
//		        		  $(this).animate({height:"120px"},500);
//		        		  $(this).parent().animate({height:"120px"},500);
//		        		  }
//		        	  });
		      
		        
		        },
		        checkHover:function (e,target){
		            if (qcListDesc.methods.getEvent(e).type=="mouseover")  {
		                return !contains(target,qcListDesc.methods.getEvent(e).relatedTarget||qcListDesc.methods.getEvent(e).fromElement) && !((qcListDesc.methods.getEvent(e).relatedTarget||qcListDesc.methods.getEvent(e).fromElement)===target);
		            } else {
		                return !contains(target,qcListDesc.methods.getEvent(e).relatedTarget||qcListDesc.methods.getEvent(e).toElement) && !((qcListDesc.methods.getEvent(e).relatedTarget||qcListDesc.methods.getEvent(e).toElement)===target);
		            }
		        },
		        getEvent:function (e){
		            return e||window.event;
		        }
	        },
	        /**
			 * 执行方法操作
			 */
			doExecute : function(flag, param) {
				var method =qcListDesc.methods[flag];
				if( typeof method === 'function') {
					return method(param);
				} else {
					alert('操作 ' + flag + ' 暂未实现！');

				}
			}
	}
	
	exports.init = function() {
		qcListDesc.doExecute('initDocument');
	}
	
	function contains(parentNode, childNode) {  
	    if (parentNode.contains) {  
	        return parentNode != childNode && parentNode.contains(childNode);  
	    } else {  
	        return !!(parentNode.compareDocumentPosition(childNode) & 16);  
	    }  
	}
});