/*
 * FileName: ExcelBuilderUtil.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.sales.contract.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

/**
 * <code>ExcelBuilderUtil</code>
 * 
 * @version  1.0
 * @author  guokemenng
 * @since 1.0  2014年4月11日
 */
public class ExcelUtil {

    /**
     * <code>getCellvalue</code>
     * Excel的值转换成java对象
     * @param cell
     * @return
     * @since   2015年2月4日    guokemenng
     */
    public static Object getCellvalue(Cell cell) {
        if (cell == null) {
            return null;
        }
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_NUMERIC:
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    try {
                        return DateUtils.convertDateToString(cell.getDateCellValue(), "yyyy-MM-dd");
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } else {
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    String temp = cell.getStringCellValue();
                    return temp;
                }
            case Cell.CELL_TYPE_STRING:
                return cell.getStringCellValue();
            case Cell.CELL_TYPE_BLANK:
                return null;
            case Cell.CELL_TYPE_FORMULA:
                cell.setCellType(Cell.CELL_TYPE_STRING);
                String str = cell.getStringCellValue();
                return str;
            default:
                return null;
        }
    }
    
    public static void main(String[] args) {
        
        try {
            //把一张xls的数据表读到wb里
            HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(new File("E:/WORKSPACE/bridge/sinobridge-eoss/sinobridge-eoss-base/src/main/webapp/upload/attachment/salary/2014/04/18/15_20_13_263_11013.xls")));
            //读取第一页,一般一个excel文件会有三个工作表，这里获取第一个工作表来进行操作    HSSFSheet sheet = wb.getSheetAt(0);
            Sheet sheet = wb.getSheetAt(0);
            //循环遍历表sheet.getLastRowNum()是获取一个表最后一条记录的记录号，
            //如果总共有3条记录，那获取到的最后记录号就为2，因为是从0开始的
            for (int j = 0; j < sheet.getLastRowNum() + 1; j++) {
                //创建一个行对象
                Row row = sheet.getRow(j);
                //把一行里的每一个字段遍历出来
                for (int i = 0; i < row.getLastCellNum(); i++) {
                    //创建一个行里的一个字段的对象，也就是获取到的一个单元格中的值
                    Cell cell = row.getCell(i);
                    //在这里我们就可以做很多自己想做的操作了，比如往数据库中添加数据等
                    int m = cell.getCellType();
                    if(m == 0){
                        System.out.println(cell.getNumericCellValue());
                    }
                    if(m == 1){
                        System.out.println(cell.getStringCellValue());
                    }
                    if(i != 13){
                        if(m == 2){
                            FormulaEvaluator evaluator = cell.getSheet().getWorkbook().getCreationHelper().createFormulaEvaluator();
                            CellValue cellValue = evaluator.evaluate(cell);
                            System.out.println(cellValue.getNumberValue());
                        }
                    }
                    if(m == 3){
                        System.out.println(cell.getColumnIndex());
                    }
                    if(m == 4){
                        System.out.println(cell.getBooleanCellValue());
                    }
                    if(m == 5){
                        System.out.println(cell.getErrorCellValue());
                    }
                }
                System.out.println("=================================================");
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
