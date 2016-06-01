/*
 * FileName: ExcelBuilderUtil.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.sales.contract.utils;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

/**
 * <code>ExcelBuilderUtil</code>
 *  只能用于合同导出
 * @version  1.0
 * @author  guokemenng
 * @since 1.0  2014年4月11日
 */
public class ExcelBuilderUtil {

    public static final String GETTER_PREFIX = "get"; //$NON-NLS-1$

    public static final String ISTER_PREFIX = "is";//$NON-NLS-1$

    public static final String SETTER_PREFIX = "set";//$NON-NLS-1$

    public static final int ACCESSOR_PREFIX_LENGTH = GETTER_PREFIX.length();

    public static final int SHEETNUM = 20000;

    /**
     * <code>buildMoreSheetPage</code>
     *
     * @param titles
     * @param list
     * @param fileds
     * @return
     * @since   2014年11月5日    guokemenng
     */
    @SuppressWarnings("rawtypes")
    public static Workbook buildMoreSheetPage(List<String> titles, List<Map<String, Object>> mapList, List<String> fileds) {
        Workbook wb = new HSSFWorkbook();
        for (Map<String, Object> map : mapList) {
            int m = 0;
            String sheetName = map.get("sheetName").toString();
            List list = (List) map.get("rowsList");

            CellStyle cellStyleTitle = buildTitleCellStyle(wb);
            CellStyle cellStyleValue = buildValueCellStyle(wb);
            CellStyle cellStyleDate = buildDateCellStyle(wb);
            CellStyle cellStyleNumber = buildNumberCellStyle(wb);

            int n = list.size();
            int sheets = 1;
            if (n % SHEETNUM == 0) {
                sheets = n / SHEETNUM;
            } else {
                sheets += Math.floor(n / SHEETNUM);
            }
            for (int q = 0; q < sheets; q++) {
                int indexY = 0;
                m++;
                Sheet sheet = wb.createSheet(sheetName + m);
                Row rowTitle = sheet.createRow(0);
                for (int i = 0; i < titles.size(); i++) {
                    Cell cell = rowTitle.createCell(i);
                    cell.setCellValue(titles.get(i));
                    cell.setCellStyle(cellStyleTitle);
                }
                for (int j = q * SHEETNUM; j < list.size(); j++) {
                    indexY++;
                    if (indexY < SHEETNUM) {
                        Row rowBody = sheet.createRow(indexY);
                        Object t = list.get(j);
                        for (int i = 0; i < fileds.size(); i++) {
                            Cell cell = rowBody.createCell(i);

                            Object o = get(t, fileds.get(i));

                            cell.setCellStyle(cellStyleValue);

                            if (o == null) {
                                cell.setCellType(3);
                            } else if ((o instanceof Number)) {
                                Number num = (Number) o;
                                cell.setCellValue(num.doubleValue());
                                cell.setCellType(0);
                                cell.setCellStyle(cellStyleNumber);
                            } else if ((o instanceof String)) {
                                cell.setCellValue((String) o);
                                cell.setCellType(1);
                            } else if ((o instanceof Date)) {
                                cell.setCellValue((Date) o);
                                cell.setCellType(0);

                                cell.setCellStyle(cellStyleDate);
                            } else if ((o instanceof Calendar)) {
                                cell.setCellValue((Calendar) o);
                                cell.setCellType(0);
                                cell.setCellStyle(cellStyleDate);
                            } else if ((o instanceof Double)) {
                                cell.setCellValue(((Double) o).doubleValue());
                                cell.setCellType(0);
                                cell.setCellStyle(cellStyleNumber);
                            } else {
                                cell.setCellValue(o.toString());
                                cell.setCellType(1);
                            }
                        }
                    }

                }

            }
        }
        return wb;
    }

    public static Workbook buildPage(List<String> titles, List<?> list, List<String> fileds) {

        Workbook wb = buildList(titles, list, fileds);
        return wb;
    }

    @SuppressWarnings("rawtypes")
    public static Workbook buildList(List<String> titles, Collection<?> list, List<String> fileds) {
        Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet();
        //客户名称 341像素
        sheet.setColumnWidth(1, 48 * 256);
        //合同编号 201像素
        sheet.setColumnWidth(3, 28 * 256);
        //合同名称
        sheet.setColumnWidth(4, 48 * 256);
        //合同类型 81像素
        sheet.setColumnWidth(5, 11 * 256);
        //合同订单状态
        sheet.setColumnWidth(7, 13 * 256);
        //创建时间  75像素
        sheet.setColumnWidth(8, 10 * 256);
        //合同文本盖章日期
        sheet.setColumnWidth(9, 16 * 256);
        //合同金额
        sheet.setColumnWidth(10, 14 * 256);
        //资金占用数
        sheet.setColumnWidth(11, 14 * 256);
        //合同下采购成本
        sheet.setColumnWidth(12, 14 * 256);
        //占用资金成本
        sheet.setColumnWidth(13, 14 * 256);
        //项目实施成本
        sheet.setColumnWidth(14, 14 * 256);
        //合同毛利
        sheet.setColumnWidth(15, 14 * 256);
        //税收毛利
        sheet.setColumnWidth(16, 14 * 256);
        //毛利率
        sheet.setColumnWidth(17, 14 * 256);
        sheet.setColumnWidth(18, 14 * 256);
        sheet.setColumnWidth(19, 14 * 256);
        sheet.setColumnWidth(20, 14 * 256);
        sheet.setColumnWidth(21, 14 * 256);
        sheet.setColumnWidth(22, 14 * 256);
        sheet.setColumnWidth(23, 14 * 256);
        sheet.setColumnWidth(24, 14 * 256);
        //发票类型
        sheet.setColumnWidth(26, 26 * 256);
        int indexY = 0;
        Row rowTitle = sheet.createRow(0);
        CellStyle cellStyleTitle = buildTitleCellStyle(wb);
        CellStyle cellStyleValue = buildValueCellStyle(wb);
        CellStyle cellStyleDate = buildDateCellStyle(wb);
        CellStyle cellStyleNumber = buildNumberCellStyle(wb);

        for (int i = 0; i < titles.size(); i++) {
            Cell cell = rowTitle.createCell(i);
            cell.setCellValue(titles.get(i));
            cell.setCellStyle(cellStyleTitle);
        }

        for (Iterator i$ = list.iterator(); i$.hasNext();) {
            Object t = i$.next();
            indexY++;

            Row rowBody = sheet.createRow(indexY);

            for (int i = 0; i < fileds.size(); i++) {
                Cell cell = rowBody.createCell(i);

                Object o = get(t, fileds.get(i));

                cell.setCellStyle(cellStyleValue);

                if (o == null) {
                    cell.setCellType(3);
                } else if ((o instanceof Number)) {
                    Number num = (Number) o;
                    cell.setCellValue(num.doubleValue());
                    cell.setCellType(0);
                    cell.setCellStyle(cellStyleNumber);
                } else if ((o instanceof String)) {
                    cell.setCellValue((String) o);
                    cell.setCellType(1);
                } else if ((o instanceof Date)) {
                    cell.setCellValue((Date) o);
                    cell.setCellType(0);

                    cell.setCellStyle(cellStyleDate);
                } else if ((o instanceof Calendar)) {
                    cell.setCellValue((Calendar) o);
                    cell.setCellType(0);
                    cell.setCellStyle(cellStyleDate);
                } else if ((o instanceof Double)) {
                    cell.setCellValue(((Double) o).doubleValue());
                    cell.setCellType(0);
                    cell.setCellStyle(cellStyleNumber);
                } else {
                    cell.setCellValue(o.toString());
                    cell.setCellType(1);
                }
            }

        }

        return wb;
    }

    protected static CellStyle buildTitleCellStyle(Workbook wb) {
        Font headerFont = wb.createFont();
        headerFont.setBoldweight((short) 700);

        CellStyle style = createBorderedStyle(wb);
        style.setAlignment((short) 2);
        style.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());

        style.setFillPattern((short) 1);
        style.setFont(headerFont);

        return style;
    }

    protected static CellStyle buildValueCellStyle(Workbook wb) {
        Font headerFont = wb.createFont();

        CellStyle style = createBorderedStyle(wb);
        style.setAlignment((short) 1);
        style.setFont(headerFont);
        return style;
    }

    protected static CellStyle buildNumberCellStyle(Workbook wb) {
        Font headerFont = wb.createFont();

        DataFormat format = wb.createDataFormat();
        CellStyle style = createBorderedStyle(wb);
        style.setAlignment((short) 2);
        style.setFont(headerFont);
        style.setDataFormat(format.getFormat("0.00"));
        return style;
    }

    protected static CellStyle buildDateCellStyle(Workbook wb) {
        Font headerFont = wb.createFont();

        CellStyle style = createBorderedStyle(wb);
        style.setAlignment((short) 1);

        style.setDataFormat(wb.createDataFormat().getFormat("yyyy-mm-dd"));

        style.setFont(headerFont);

        return style;
    }

    protected static CellStyle createBorderedStyle(Workbook wb) {
        CellStyle style = wb.createCellStyle();
        style.setBorderRight((short) 1);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderBottom((short) 1);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft((short) 1);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop((short) 1);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        return style;
    }

    public static Object get(Object targetObject, String propertyName) {
        Assert.notNull(targetObject);
        Class<?> clazz = targetObject.getClass();
        Method potentialGetter = findGetterByProperty(clazz, propertyName);
        if (potentialGetter != null) {
            return ReflectionUtils.invokeMethod(potentialGetter, targetObject);
        }
        return null;
    }

    public static Method findGetterByProperty(Class<?> clazz, String propertyName) {
        String getterName = parseGetter(propertyName);
        return ReflectionUtils.findMethod(clazz, getterName);
    }

    /**
     * <code>parseSetter</code>将一个字符串转换为JavaBean的setter方法,同时去掉字符串中_号和-号，
     * 并将这两个符号后面的第一个字符转换为大写。
     * 
     * 
     * @param str
     *            str
     * @return String
     * @since 2010-3-10 wangjunming
     */
    public static String parseSetter(String str) {
        String propertyName = parseProperty(str);
        // 如果以set开头，并且后面紧跟一个大写字母，则直接返回
        if (isSetter(propertyName)) {
            return propertyName;
        }
        // 在前面加set，并将set后的第一个字符变为大写
        StringBuffer buf = new StringBuffer(SETTER_PREFIX).append(propertyName.substring(0, 1).toUpperCase()).append(propertyName.substring(1, propertyName.length()));

        return buf.toString();
    }

    /**
     * <code>parseGetter</code>将一个字符串转换为JavaBean的getter方法,同时去掉字符串中_号和-号，
     * 并将这两个符号后面的第一个字符转换为大写。
     * 
     * @param str
     *            str
     * @return String
     * @since 2010-3-10 wangjunming
     */
    public static String parseGetter(String str) {
        String propertyName = parseProperty(str);
        // 如果以set开头，并且后面紧跟一个大写字母，则直接返回
        if (isGetter(propertyName)) {
            return propertyName;
        }
        // 在前面加set，并将set后的第一个字符变为大写
        StringBuffer buf = new StringBuffer(GETTER_PREFIX).append(propertyName.substring(0, 1).toUpperCase()).append(propertyName.substring(1, propertyName.length()));

        return buf.toString();
    }

    public static boolean isGetter(String s) {
        if (s == null) {
            return false;
        }
        return s.indexOf(GETTER_PREFIX) == 0 && Character.isUpperCase(s.charAt(ACCESSOR_PREFIX_LENGTH));
    }

    public static boolean isSetter(String s) {
        if (s == null) {
            return false;
        }
        return s.indexOf(SETTER_PREFIX) == 0 && Character.isUpperCase(s.charAt(ACCESSOR_PREFIX_LENGTH));
    }

    /**
     * <code>parseProperty</code>将一个字符串解析为属性的名字。例如：<br>
     * 
     * <pre>
     * user_name -&gt;userName;
     * username -&gt; username;
     * userName -&gt; userName;
     * User_NAME-&gt; userName;
     * USERNAME-&gt; uSERNAME;
     * </pre>
     * 
     * @param property
     *            String to be parsed.
     * @return String
     */
    public static String parseProperty(String property) {
        String propertyName = property;
        // 将_和-统一替换为-,方便以后操作
        propertyName = propertyName.replaceAll("_|-?1", "-"); //$NON-NLS-1$//$NON-NLS-2$
        while (propertyName.endsWith("-")) { // 删除位于尾部的-号//$NON-NLS-1$
            propertyName = propertyName.substring(0, propertyName.length() - 1);
        }
        // 如果包含"-"则全部转换为小写字母
        if (propertyName.indexOf("-") >= 0) {//$NON-NLS-1$
            propertyName = propertyName.toLowerCase();
        }
        // 删除所有的-号，并将后面的字符转换为大写
        int signIndex = 0;
        while ((signIndex = propertyName.indexOf("-")) >= 0) {//$NON-NLS-1$
            if (signIndex >= 0) {
                String c = String.valueOf(propertyName.charAt(signIndex + 1));
                propertyName = propertyName.replaceAll("-" + c, c.toUpperCase());//$NON-NLS-1$
            }
        }
        // 将首字母转为小写
        if (Character.isUpperCase(propertyName.charAt(0))) {
            StringBuffer buf = new StringBuffer(propertyName.substring(0, 1).toLowerCase()).append(propertyName.subSequence(1, propertyName.length()));
            propertyName = buf.toString();
        }

        return propertyName;
    }

    public static void main(String[] args) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("12", new BigDecimal("12.00"));

        System.out.println(map.get("12") instanceof Number);
    }

}
