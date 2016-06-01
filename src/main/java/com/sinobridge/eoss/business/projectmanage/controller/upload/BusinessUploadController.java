/*
  * FileName: UploadController.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.business.projectmanage.controller.upload;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import com.sinobridge.base.core.utils.UploadUtil;

/**
 * <code>UploadController</code>
 * 
 * @version  1.0
 * @author  guokemenng
 * @since 1.0  2013-11-11
 */
@Controller
@RequestMapping(value = "/business/projectm/baseUpload")
public class BusinessUploadController {

    
    /**
     * <code>getBaseUploadPic</code>
     * 上传图片页面
     * @param request
     * @param response
     * @return
     * @since   2013-11-26    guokemenng
     */
    @RequestMapping(value = "/baseUploadPic")
    public ModelAndView getBaseUploadPic(HttpServletRequest request, HttpServletResponse response) {

        ModelAndView mav = new ModelAndView("business/projectm/baseUploadPic");

        return mav;
    }
    /**
     * <code>doUpload</code>
     * 上传图片
     * @param request
     * @param response
     * @return
     * @since   2013-11-26    guokemenng
     */
    @ResponseBody
    @RequestMapping(value = "/uploadPic")
    public Map<String, String> doUpload(HttpServletRequest request, HttpServletResponse response) {

//        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        MultipartHttpServletRequest multipartRequest = resolver.resolveMultipart(request);
        String[] description = (String[]) multipartRequest.getParameterMap().get("attachmentDescription");
        CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest.getFile("attachment");
        String picName = file.getFileItem().getName();

        String webPath = UploadUtil.buildWebPath(UploadUtil.getBasePath(), picName);
        String storeFilePath = UploadUtil.buildStroeFilePath(webPath);

        File descPath = new File(UploadUtil.buildPath(storeFilePath));
        if (!descPath.exists()) {
            descPath.mkdirs();
        }

        File descF = new File(storeFilePath);
        try {
            FileCopyUtils.copy(file.getBytes(), descF);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return UploadUtil.buildResult(picName, webPath, description[0]);
    }

}
