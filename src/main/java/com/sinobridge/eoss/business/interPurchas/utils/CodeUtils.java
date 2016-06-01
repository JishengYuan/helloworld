package com.sinobridge.eoss.business.interPurchas.utils;

import java.text.ParseException;
import java.util.Date;

/**
 * <code>CodeUtils</code>
 * 
 * @version  1.0
 * @author  wangya
 * @since 1.0  2014年7月11日
 */
public class CodeUtils {

    /**
     * <code>creatCode</code>
     * 时间转换成字符串
     * @param serialNum 当前流水号
     * @return code 订单编码
     * @since   2014年6月11日    3unshine
     */
    public static String creatCode( int serialNum) {
        String code = "";
        int num=serialNum+1;
        try {
            code =DateUtils.convertDateToString(new Date(), "yyyy-MM-dd") + "-" + num;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return code;
    }
}
