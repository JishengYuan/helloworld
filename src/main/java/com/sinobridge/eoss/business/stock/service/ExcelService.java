package com.sinobridge.eoss.business.stock.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.sinobridge.base.core.service.DefaultBaseService;
import com.sinobridge.base.core.utils.UploadUtil;
import com.sinobridge.eoss.business.order.dao.BusinessOrderDao;
import com.sinobridge.eoss.business.stock.dao.ExcelDao;
import com.sinobridge.eoss.business.stock.dao.SerialDao;
import com.sinobridge.eoss.business.stock.dao.StockDao;
import com.sinobridge.eoss.business.stock.model.ExcelModel;
import com.sinobridge.eoss.sales.contract.dao.SalesContractDao;

/**
 *	excel导入表
 */
@Service
@Transactional
public class ExcelService extends DefaultBaseService<ExcelModel, ExcelDao>{
	
	@Autowired
	SalesContractDao salesContractDao;
	@Autowired
	BusinessOrderDao businessOrderDao;
	@Autowired
	StockDao stockDao;
	@Autowired
	SerialDao serialDao;
	
	//List<BusinessOrderModel> businessOrderList = businessOrderDao.find("from BusinessOrderModel");
	//List<SalesContractModel> salesContractList = salesContractDao.find("from SalesContractModel");
	
    /**
     * 上传Excel 得到文件储存路径
     * @param request
     * @return
     */
    public String doUpload(HttpServletRequest request) {
        MultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        MultipartHttpServletRequest multipartRequest = resolver.resolveMultipart(request);
        CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest.getFile("attachment");
        String picName = file.getFileItem().getName();
        String webPath = UploadUtil.buildWebPath(UploadUtil.getBasePath() + "excel/", picName);
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
        return webPath;
    }

	/**
     * 读取上传Excle数据
     * @param path
     * @return
     */
    public List<ExcelModel> readExcel(String path) {
        List<ExcelModel> excelList = new ArrayList<ExcelModel>();
        try {
            HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(new File(path)));
            //读取第一页,一般一个excel文件会有三个工作表，这里获取第一个工作表来进行操作    HSSFSheet sheet = wb.getSheetAt(0);
            Sheet sheet = wb.getSheetAt(0);
            //循环遍历表sheet.getLastRowNum()是获取一个表最后一条记录的记录号，
            //第一条为标题 从第二行开始读取数据
            for (int j = 1; j < sheet.getLastRowNum() + 1; j++) {
                //创建一个行对象
                ExcelModel ex = new ExcelModel();
                Row row = sheet.getRow(j);
                excelList.add(getExcel(ex, row));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return excelList;
    }

	/**
	* 得到cell值
	* @param ex
	* @param cell
	* @param i
	* @return
	*/
	public Object getCellvalue(ExcelModel ex, Cell cell, Integer i) {
		switch (cell.getCellType()) {
			case Cell.CELL_TYPE_NUMERIC:
				return cell.getNumericCellValue();
			case Cell.CELL_TYPE_STRING:
				return cell.getStringCellValue();
			case Cell.CELL_TYPE_BLANK:
				return null;
			case Cell.CELL_TYPE_FORMULA:
				return null;
			default : return null;
		} 
	}
	
	/**
     * 判断每一列cell的值属于哪个属性数据
     * @param ex
     * @param cell
     * @param i
     */
	public void makeExcel(ExcelModel ex, Cell cell, Integer i) {
		try {
			switch (i) {
				case 1: {	//入库单号
					Object o = getCellvalue(ex,cell,i);
					if(o != null){
						ex.setInboundCode(o.toString());
					}
					break;
				}
                case 2: {	//订单编号
                	Object o = getCellvalue(ex,cell,i);
                    if(o != null){
                    	ex.setOrderCode(o.toString());
                    }
                    break;
                }
                case 3: {	//合同编号
                	Object o = getCellvalue(ex,cell,i);
                    if(o != null){
                    	ex.setContractCode(o.toString());
                    }
                    break;
                }
                case 4: {	//批次号
                	Object o = getCellvalue(ex,cell,i);
                    if(o != null){
                    	ex.setPono(o.toString());
                    }
                    break;
                }
                case 5: {	//型号
                	Object o = getCellvalue(ex,cell,i);
                    if(o != null){
                    	ex.setModel(o.toString());
                    }
                	break;
                }
                case 6: {	//数量
                	Object o = getCellvalue(ex,cell,i);
                    if(o != null){
                    	ex.setNumber(1);
                    }
                    break;
                }
                case 7: {	//序列号
                	Object o = getCellvalue(ex,cell,i);
                    if(o != null){
                    	ex.setSerial(o.toString());
                    }
                	break;
                }
                case 8: {	//收货人
                	Object o = getCellvalue(ex,cell,i);
                    if(o != null){
                    	ex.setRecipientName(o.toString());
                    }
                    break;
                }
            }
        } catch (Exception e) {
            //e.printStackTrace();
            //System.out.println("该列的值是空的！");
        }
    }
	
	/**
     * 每行row 组建一个excel对象
     * @param ex
     * @param row
     * @return
     */
    public ExcelModel getExcel(ExcelModel ex, Row row) {
        //把一行里的每一个字段遍历出来
        for (int i = 0; i < row.getLastCellNum(); i++) {
            //创建一个行里的一个字段的对象，也就是获取到的一个单元格中的值
            Cell cell = row.getCell(i);
            makeExcel(ex, cell, i);
        }
        //（入库单号生成规则：In+当前年月日+批次号）
        String inboundCode = ex.getInboundCode();
        if("".equals(inboundCode)||inboundCode==null){
        	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			ex.setInboundCode("In-"+df.format(new Date())+"-"+ex.getPono());
        }
        //String orderCode = ex.getOrderCode();
        //String contractCode = ex.getContractCode();
        //if(("".equals(orderCode)||orderCode==null)&&("".equals(contractCode)||contractCode==null)){
        	
        //}
        return ex;
    }
    
    /**
     * 保存到数据库
     * @param request
     */
	public void uploadFile(HttpServletRequest request) {
        try {
            String filePath = doUpload(request);
            String realPath = UploadUtil.buildWebRootPath() + filePath;
            List<ExcelModel> excelList = readExcel(realPath);
            this.getDao().saveOrUpdateAll(excelList);

//            List<ExcelModel> oldList = this.getDao().find("from Excel");
//            List<ExcelModel> delList =  new ArrayList<ExcelModel>();
//            for(ExcelModel ex : excelList){
//            	for(ExcelModel ex1 : oldList){
//            		if(ex.getContractCode() .equals(ex1.getContractCode())|| ex.getOrderCode().equals(ex1. getOrderCode())){
//            			delList.add(ex);
//            		}
//            	}
//            }
//            this.getDao().deleteAll(delList);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	/**
	 * 一键入库（将exel表内容剪切到库存表、序列号表）
	 * @param request
	 */
	public void dealExcel () {
		//SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd mm:hh:ss");
		
//		List<ExcelModel> excelToStock = this.getDao().excelToStock();
//		List<ExcelModel> excelToSerail = this.getDao().excelToSerial();
//		List<StockModel> stockList = new ArrayList<StockModel>();
//		List<StockModel> stockAll = new ArrayList<StockModel>();
//		List<SerialModel> serialList = new ArrayList<SerialModel>();
//		List<SerialModel> serialAll = new ArrayList<SerialModel>();
		
		try {
			int stock = this.getDao().excelToStock();
			int serial = this.getDao().excelToSerial();
			if(stock!=0&&serial!=0){
				int del = this.getDao().delExcel();
				if(del!=0){
					System.out.println("excel表数据入库成功！");
				}
			}else{
				System.out.println("数据入库失败！");
			}
			//excel表数据对应到库存表
//			for(ExcelModel excel : excelToStock){
//				for(StockModel stock : stockList){
//					stock.setInboundCode(excel.getInboundCode());
//					stock.setStockState(StateUtil.YRK);
//					stock.setOrderCode(excel.getOrderCode());
//					stock.setContractCode(excel.getContractCode());
//					stock.setPono(excel.getPono());
//					stock.setModel(excel.getModel());
//					stock.setStockNum(excel.getNumber());
//					stock.setRecipientName(excel.getRecipientName());
//					//stock.setInboundTime(df.format(new Date()));
//					stock.setInboundTime(new Date());
//					stockAll.add(stock);
//				}
//				stockDao.saveOrUpdateAll(stockAll);
//			}
			//excel数据对应到序列号表
//			for(ExcelModel excel : excelToSerail){
//				for(SerialModel serial : serialList){
//					serial.setInboundCode(excel.getInboundCode());
//					serial.setOrderCode(excel.getOrderCode());
//					serial.setContractCode(excel.getContractCode());
//					serial.setSerial(excel.getSerial());
//					serialAll.add(serial);
//				}
//				serialDao.saveOrUpdateAll(serialAll);
//			}
			//入库完成后删除excel表数据
//			this.getDao().deleteAll(excelToStock);
//			this.getDao().deleteAll(excelToSerail);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

}