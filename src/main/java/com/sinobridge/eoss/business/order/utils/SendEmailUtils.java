/*
 * FileName: SendEmailUtils.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.business.order.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author 袁继生
 * @version 1.0

 * <p>
 * History:
 *
 * Date                     Author         Version     Description
 * ---------------------------------------------------------------------------------
 * 2016年4月8日 上午9:32:59           袁继生           									1.0        To create
 * </p>
 *
 * @since
 * @see
 */
public class SendEmailUtils {
    /**
     * 读取classpath下面的config/application.properties中配置的发送邮件相关信息
     * @return 如果配置的default.mail.isSend的值为true，则需要发送
     */
    public static boolean isSend() {
        InputStream in = ClassLoader.getSystemResourceAsStream("config/application.properties");
        //InputStream in = SalesCompanyTest.class.getResourceAsStream("config/application.properties");
        Properties prop = new Properties();
        try {
            prop.load(in);
            String isSendStr = (String) prop.get("default.mail.isSend");
            if (isSendStr != null && "true".equals(isSendStr)) {
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
}
