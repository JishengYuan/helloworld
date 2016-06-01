/*
 * FileName: ExportInvoiceUtils.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.sales.invoice.utils;

import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

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
 * <p>
 * Description: 导出发票信息到excel的工具类
 * </p>
 *
 * @author Jack
 * @version 1.0

 * <p>
 * History:
 *
 * Date                     Author         Version     Description
 * ---------------------------------------------------------------------------------
 * 2016年5月23日 下午3:10:12          Jack        1.0         To create
 * </p>
 *
 * @since
 * @see
 */
public class ExportInvoiceUtils {
    public static final String GETTER_PREFIX = "get"; //$NON-NLS-1$

    public static final String ISTER_PREFIX = "is";//$NON-NLS-1$

    public static final String SETTER_PREFIX = "set";//$NON-NLS-1$

    public static final int ACCESSOR_PREFIX_LENGTH = GETTER_PREFIX.length();

    public static final int SHEETNUM = 20000;

    /**
     * 构造表格标题的格式
     * @param wb
     * @return
     */
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

    /**
     * 构造普通文本值的单元格格式
     * @param wb
     * @return
     */
    protected static CellStyle buildValueCellStyle(Workbook wb) {
        Font headerFont = wb.createFont();

        CellStyle style = createBorderedStyle(wb);
        style.setAlignment((short) 1);
        style.setFont(headerFont);
        return style;
    }

    /**
     * 构造数字类型的单元格格式
     * @param wb
     * @return
     */
    protected static CellStyle buildNumberCellStyle(Workbook wb) {
        Font headerFont = wb.createFont();

        DataFormat format = wb.createDataFormat();
        CellStyle style = createBorderedStyle(wb);
        style.setAlignment((short) 2);
        style.setFont(headerFont);
        style.setDataFormat(format.getFormat("0.00"));
        return style;
    }

    /**
     * 构造日期单元格的格式
     * @param wb
     * @return
     */
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

    /**
     * 根据传递的表格头字段信息集合、导出的数据集合以及数据传输对象的属性集合构造excel表格信息
     * @param titles  表格标题
     * @param list    表格要填充的数据
     * @param fields  封装数据的传输对象属性集合
     * @return
     */
    public static Workbook buildList(List<String> titles, Collection<?> list, List<String> fields) {
        Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet();
        sheet.setColumnWidth(0, 28 * 256);
        sheet.setColumnWidth(1, 50 * 256);
        sheet.setColumnWidth(2, 21 * 256);
        sheet.setColumnWidth(3, 15 * 256);
        sheet.setColumnWidth(4, 15 * 256);
        sheet.setColumnWidth(5, 20 * 256);
        sheet.setColumnWidth(6, 15 * 256);
        sheet.setColumnWidth(7, 15 * 256);

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
            for (int i = 0; i < fields.size(); i++) {
                Cell cell = rowBody.createCell(i);

                //通过反射调用其getter方法，从而获取每个属性对应的值
                Object o = get(t, fields.get(i));

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
        Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet();
        sheet.setColumnWidth(1, 48 * 256);

        Row rowTitle = sheet.createRow(0);
        Cell cell1 = rowTitle.createCell(0);
        cell1.setCellValue("合同编号");

        Cell cell2 = rowTitle.createCell(1);
        cell2.setCellValue("合同名称");

        Row body1 = sheet.createRow(1);
        body1.createCell(0).setCellValue("111");
        body1.createCell(1).setCellValue("软件签订的合同");
    }
}
