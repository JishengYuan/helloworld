/*
 * FileName: FileUtils.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.business.order.utils;

import com.sinobridge.systemmanage.vo.FileVo;

/**
 * <p>
 * Description: 
 * </p>
 *
 * 
 * @version 1.0

 * <p>
 * History: 
 *
 * Date                     Author         Version     Description
 * ---------------------------------------------------------------------------------
 * 2014年7月10日 上午11:08:46          wangya       1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
public class FileUtils {
    public static FileVo creatFileUploadData() {
        FileVo fileVo = new FileVo();
        fileVo.setName("upfile");
        fileVo.setType("type");
        fileVo.setBoolRequire(true);
        fileVo.setBoolWrite(true);
        fileVo.setLabel("附件上传");
        return fileVo;
    }
}
