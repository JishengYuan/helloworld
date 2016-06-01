package com.sinobridge.eoss.sales.contract.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.sinobridge.eoss.sales.contract.constant.SalesContractConstant;

/**
 * <code>CodeUtils</code>
 * 
 * @version  1.0
 * @author  3unshine
 * @since 1.0  2014年6月11日
 */
public class CodeUtils {

    /**
     * <code>creatCode</code>
     * 时间转换成字符串
     * @param customerInfoCode 客户编码
     * @param serialNum 当前流水号
     * @return code 合同编码
     * @since   2014年6月11日    3unshine
     */
    public static String creatCode(String customerInfoCode, int serialNum) {
        String code = "";
        serialNum+=1;
        try {
            code = SalesContractConstant.CONTRACT_CODE_FIRST + "-" + customerInfoCode + "-" + DateUtils.convertDateToString(new Date(), "yyyyMMdd") + "-" + serialNum;
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

    /**
     * <code>stringToMap</code>
     * 字符转转成Map
     * @param mapText
     * @return Map<String, Object>
     * @since   2014年5月19日    3unshine
     */
    public static Map<String, Object> stringToMap(String mapText) {

        if (mapText == null || mapText.equals("")) {
            return null;
        }
        mapText = mapText.substring(1, mapText.length() - 1);

        Map<String, Object> map = new HashMap<String, Object>();
        String[] text = mapText.split(",");

        for (String str : text) {
            String[] keyText = str.split("="); // 转换key与value的数组  
            if (keyText.length < 1) {
                continue;
            }
            if (keyText.length == 1) {
            } else {
                String key = keyText[0]; // key  
                String value = keyText[1]; // value  
                if (!value.trim().equals("")) {
                    map.put(key, value);
                }
            }

        }
        return map;
    }

    /**
     * <code>getShowNumber</code>
     *
     * @param number
     * @return
     * @since   2014年12月18日    guokemenng
     */
    public static String getShowNumber(BigDecimal number) {

        String sb = "";
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        dfs.setDecimalSeparator('.');
        dfs.setGroupingSeparator(',');
        dfs.setMonetaryDecimalSeparator('.');
        DecimalFormat df = new DecimalFormat("###,###.##", dfs);
        sb = df.format(number);
        if (!sb.contains(".")) {
            sb = sb + ".00";
        }
        return sb.toString();
    }
}
