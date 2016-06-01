package com.sinobridge.eoss.business.order.utils;

import java.text.ParseException;
import java.util.Date;

/**
 * <code>CodeUtils</code>
 * 
 * @version  1.0
 * @author  wangya
 * @since 1.0  2014年6月11日
 */
public class CodeUtils {

    /**
     * <code>creatCode</code>
     * 时间转换成字符串
     * @param supplierCode 供应商简称
     * @return code 订单编码
     * @since   2014年6月11日    3unshine
     */
    public static String creatCode(String supplierCode) {
        String code = "";
        try {
            code = supplierCode + "-" + DateUtils.convertDateToString(new Date(), "yyyyMMdd");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return code;
    }

    /**
     * <code>stringArrayToLongArray</code>
     * 将String数组转换为Long类型数组
     * @param strs
     * @return
     * @since 2014年7月9日    3unshine
     */
    public static Long[] stringArrayToLongArray(String[] strs) {
        Long[] longs = new Long[strs.length];
        for (int i = 0; i < strs.length; i++) {
            String str = strs[i];
            long theLong = Long.valueOf(str);
            longs[i] = theLong;
        }
        return longs;
    }

}
