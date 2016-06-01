/*
 * FileName: MoneyCurrencyChange.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.business.order.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>
 * Description: 美元汇率转换工具类
 * </p>
 *
 * @author tigq
 * @version 1.0

 * <p>
 * History: 
 *
 * Date                     Author         Version     Description
 * ---------------------------------------------------------------------------------
 * 2016年2月1日 下午3:36:27          tigq        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
public class MoneyCurrencyChange {
    private Log logger = LogFactory.getLog(this.getClass());
    private static final String pattern = "yyyy-MM-dd HH:mm:ss:SSS";
    private HttpClient httpClient = null;

    private long startTime = 0L;
    private long endTime = 0L;
    private int status = 0;

    private final String apiKey = "f08fc60215dce2e16a2443c45be9a84e";//百度汇率转换apikey
    private final static String currentCurrenyUrl = "http://api.k780.com:88/?app=finance.rate&scur=USD&tcur=CNY&appkey=10003&sign=b59bc3ef6191eb9f747dd4e83c99f2a4";

    public static String getGetResponse(String url) {
        String html = "";
        // 构造HttpClient的实例
        HttpClient httpClient = new HttpClient();
        // 创建GET方法的实例
        GetMethod getMethod = new GetMethod(url);
        // 使用系统提供的默认的恢复策略
        getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
        try {
            // 执行getMethod
            int statusCode = httpClient.executeMethod(getMethod);
            if (statusCode != HttpStatus.SC_OK) {
                System.err.println("Method failed: " + getMethod.getStatusLine());
            }
            // 处理内容
            html = getMethod.getResponseBodyAsString();
        } catch (HttpException e) {
            // 发生致命的异常，可能是协议不对或者返回的内容有问题
            System.out.println("Please check your provided http address!");
            e.printStackTrace();
        } catch (IOException e) {
            // 发生网络异常
            e.printStackTrace();
        } finally {
            // 释放连接
            getMethod.releaseConnection();
        }
        return html;
    }

    public static String getPostResponse(String url) throws HttpException, IOException {
        String html = "";
        HttpClient httpClient = new HttpClient();
        PostMethod postMethod = new PostMethod(url);
        postMethod.setRequestHeader("accept", "*/*");
        postMethod.setRequestHeader("connection", "Keep-Alive");
        postMethod.setRequestHeader("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
        postMethod.setRequestHeader("Accept-Language", "zh-cn,zh;q=0.5");
        // postMethod.setRequestHeader("Accept-Encoding", "gzip,deflate");
        // postMethod.setRequestHeader("Content-Type", "text/html;charset=utf-8");
        // 填入各个表单域的值
        NameValuePair[] data = { new NameValuePair("msg", "你好") };
        // 将表单的值放入postMethod中
        postMethod.setRequestBody(data);
        // 执行postMethod
        int statusCode = httpClient.executeMethod(postMethod);
        // HttpClient对于要求接受后继服务的请求，象POST和PUT等不能自动处理转发
        // 301或者302
        if (statusCode == HttpStatus.SC_MOVED_PERMANENTLY || statusCode == HttpStatus.SC_MOVED_TEMPORARILY) {
            // 从头中取出转向的地址
            Header locationHeader = postMethod.getResponseHeader("location");
            String location = null;
            if (locationHeader != null) {
                location = locationHeader.getValue();
                System.out.println("The page was redirected to:" + location);
            } else {
                System.err.println("Location field value is null.");
            }
            return html;
        }
        // html = postMethod.getResponseBodyAsString();

        System.out.println(postMethod.getResponseCharSet());
        // byte[] responseBody = postMethod.getResponseBody();

        BufferedReader in = new BufferedReader(new InputStreamReader(postMethod.getResponseBodyAsStream(), postMethod.getResponseCharSet()));
        StringBuffer sb = new StringBuffer();
        int chari;
        while ((chari = in.read()) != -1) {
            sb.append((char) chari);
        }
        html = sb.toString();
        in.close();
        postMethod.releaseConnection();

        return html;
    }

    public static class UTF8PostMethod extends PostMethod {
        public UTF8PostMethod(String url) {
            super(url);
        }

        @Override
        public String getRequestCharSet() {
            return super.getRequestCharSet();
            // return "UTF-8";
        }

    }

    /**
     * 获得当前人民币和美元的汇率
     * @return
     */
    public static Map<String, String> getCurrentRate() {
        String a = MoneyCurrencyChange.getGetResponse(currentCurrenyUrl);
        System.out.println(a);
        JSONObject jsonObject = JSONObject.fromObject(a);

        JSONObject rs = (JSONObject) jsonObject.get("result");
        Map<String, String> rsmap = new HashMap<String, String>();
        rsmap.put("ratenm", rs.getString("ratenm"));//美元/人民币
        rsmap.put("rate", rs.getString("rate"));//汇率
        rsmap.put("rateDate", rs.getString("update"));//发布时间
        System.out.println(rsmap);
        return rsmap;
    }

    /**
     * 将人民币根据当前汇率转成美金
     * @param cnytotal
     * @return
     */
    public static BigDecimal getChangeUsd(BigDecimal cnytotal) {
        BigDecimal rs = new BigDecimal(0.00);
        Map<String, String> rsmap = getCurrentRate();
        rs = cnytotal.divide(new BigDecimal(rsmap.get("rate")), 10, BigDecimal.ROUND_HALF_UP);
        rs = rs.setScale(2, BigDecimal.ROUND_HALF_UP);
        return rs;
    }

    public static void main(String args[]) {
        BigDecimal a = new BigDecimal("60011");
        System.out.println(MoneyCurrencyChange.getChangeUsd(a));
    }
}
