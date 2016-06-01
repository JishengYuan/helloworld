<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page contentType="text/html; charset=UTF-8"  pageEncoding="utf-8"%>
<%@ include file="/common/global.jsp"%>

<html>
    <head>
       <title>pic</title>
	   <script src="${ctx}/js/plugins/jquery/jquery-1.6.min.js" id="seajsnode" type="text/javascript"></script>
    </head>
<body>
      <div class="modal-header">
		   <a type="button" class="close" data-dismiss="modal" aria-hidden="true">×</a>
		         <h3 id="myModalLabel">上传图片</h3>
	  </div>
         
      <div class="modal-body" >		
      	<div id="piccntent"></div>
      	<div style="">
		<div id="upload" style="display: none; width: 518px;margin-top:10px; text-align: right;">
			<!-- <a class="easyui-linkbutton" href="javascript:void(0)">上传</a> -->
			<a class="btn btn-primary"  id="btnConfirm" >上传</a >
			&nbsp;
		</div>
		<div id="webcms_content_close" style="width: 518px;margin-top:10px; text-align: right;display: none;">
		</div>
	</div>           
      </div>
    
   <div class="modal-footer">
        <a class="btn" data-dismiss="modal" aria-hidden="true">取消</a>
        <a type="submit" class="btn btn-primary"  id="pic_btnConfirm" >保存</a >
   </div >     
 <script language="javascript">
  		//上传图片操作;
		var imgs = [];
		function consolelog(str) {
		}
		function selectFileCallback(selectFiles) {
			// consolelog("选择了如下文件：");
			if (selectFiles.length) {
				baidu.g("upload").style.display = "";
				baidu.g("webcms_content_close").style.display = "none";
			}
			var obj;
			for ( var i = 0, iLen = selectFiles.length; i < iLen; i++) {
				obj = selectFiles[i];
				consolelog(obj.index, obj.name, obj.size);
			}
		}
		function exceedFileCallback(file) {
			consolelog("文件超出大小限制：");
			consolelog(file.index, file.name, file.size);
		}
		function deleteFileCallback(delFiles) {
			var obj;
			for ( var i = 0, iLen = delFiles.length; i < iLen; i++) {
				obj = delFiles[i];
				consolelog(obj.index, obj.name, obj.size);
			}
		}
		function startUploadCallback(file) {
		}

		function uploadErrorCallback(data) {
			consolelog("上传失败", data);
		}
		function allCompleteCallback() {
			consolelog("全部上传成功");
		}
		function upload() {
			flash.upload();
		}
		function pause() {
			flash.pause();
		}
		function changeFlashHeight(height) {
			flash.height = height;
			var obj = document.getElementById("flashcontent");
			obj.style.height = height + 2;
		}
		var options = {
			createOptions : {
				id : "flashID",
				url : ctx+'/js/plugins/swfupload/imageUploader.swf',
				width : "540",
				height : "200",
				errorMessage : "error in embeding flash",
				wmode : "transparent",
				ver : "9.0.0",
				vars : {
					url : ctx+"/business/projectm/baseUpload/uploadPic?tmp="+Math.random(),
					uploadDataFieldName : "attachment",
					picDescFieldName : "attachmentDescription",
					maxSize : 4,
					width : 510,
					height : 200,
					gridWidth : 135,
					gridHeight : 135,
					picWidth : 100,
					picHeight : 150,
					compressSize : 3,
					maxNum : 2,
					compressLength : 1280,
				},
				container : "piccntent"
			},
			selectFileCallback : "selectFileCallback",
			exceedFileCallback : "exceedFileCallback",
			deleteFileCallback : "deleteFileCallback",
			startUploadCallback : "startUploadCallback",
			uploadCompleteCallback : function(data) {
				var img = eval("("+data.info+")");
				imgs.push(img);
				
			},
			uploadErrorCallback : "uploadErrorCallback",
			allCompleteCallback : "allCompleteCallback",
			changeFlashHeight : "changeFlashHeight"
		};
		var flash = new baidu.flash.imageUploader(options);
		/**
		 * 绑定开始上传事件
		 */
		function addUploadListener() {
			baidu.g("upload").onclick = function() {
				flash.upload();
				this.style.display = "none";
				baidu.g("webcms_content_close").style.display = "";
			};
		}
		addUploadListener();   
		//关闭窗口并 把数据传递回去
	function _window_close(){
		if(imgs) {
			if(imgs.length) {
				var imgJson = imgs[0];
				var parent$ = parent.$;
				var big = '${param.big}';
				var brandLogo = '${param.brandLogo}';
				if(big != null && big != ''){
					//修改隐藏域的值
					parent$("#_sino_base_bigUrl").attr("value", imgJson.url);
					//图片预览效果
					parent$("#_sino_base_bigPath").attr("src", ctx + imgJson.url);
			     } else if(brandLogo != null && brandLogo != ""){
			    	 parent$("#_sino_brandLogo").attr("value", imgJson.url);
			    	 $("#_sino_brand_logo").hide();
 					$("#_sino_brand_logo").parent().append("<img style='height: 30px;  border: 1px solid #ccc' src='"+ctx + imgJson.url+"'>");
			     } else {
						//修改隐藏域的值
						parent$("#_sino_base_picUrl").attr("value", imgJson.url);
						//图片预览效果
						parent$("#_sino_base_picPath").attr("src", ctx + imgJson.url);
			     }
				imgs = null;
			}
		}
	}
  
</script>    
 </body>
</html>