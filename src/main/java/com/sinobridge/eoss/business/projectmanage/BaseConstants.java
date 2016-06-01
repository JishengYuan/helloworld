/*
 * FileName: BaseConstants.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.business.projectmanage;

/**
 * <p>
 * Description: 
 * </p>
 *
 * @author guo_kemeng
 * @version 1.0

 * <p>
 * History: 
 *
 * Date                     Author         Version     Description
 * ---------------------------------------------------------------------------------
 * 2013-11-25 下午03:54:42          guo_kemeng        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
public class BaseConstants {
    
    /** 
     * String BASE_PRODUCTTYPE :      
     * 产品类别表 
     * @since  2013-11-25 guokemenng
     */
    public static final String BASE_PRODUCTTYPE = "Business_ProductType";
    
    /** 
     * String BASE_PARTNERINFO :   
     * 厂商信息管理表    
     * @since  2013-11-25 guokemenng
     */
    public static final String BASE_PARTNERINFO = "Business_PartnerInfo";
    /** 
     * String BASE_PARTNERBRAND :       
     * 厂商品牌信息
     * @since  2013-11-25 guokemenng
     */
    public static final String BASE_PARTNERBRAND = "Business_PartnerBrand";
    /** 
     * String BASE_PARTNERPRODUCT :       
     * 厂商产品类别
     * @since  2013-11-25 guokemenng
     */
    public static final String BASE_PARTNERPRODUCT = "Business_PartnerProduct";
    /** 
     * String Base_ProductLine :    
     * 厂商产品系列
     * @since  2013-11-25 guokemenng
     */
    public static final String BASE_PRODUCTLINE = "Business_ProductLine";
    /** 
     * String BASE_PRODUCTMODEL :    
     * 厂商产品型号   
     * @since  2013-11-25 guokemenng
     */
    public static final String BASE_PRODUCTMODEL = "Business_ProductModel";
//    /** 
//     * String BASE_PRODUCTINFOEXP :   
//     * 产品型号扩展配置表    
//     * @since  2013-11-25 guokemenng
//     */
//    public static final String BASE_PRODUCTINFOEXP = "Base_ProductInfoExp";
//    /** 
//     * String BASE_PRODUCTMODELDATA :  
//     * 产品型号数据表     
//     * @since  2013-11-25 guokemenng
//     */
//    public static final String BASE_PRODUCTMODELDATA = "Base_ProductModelData";
    
    /** 
     * String domainCode :       
     * 厂商信息表查询类型
     * @since  2013-11-13 guokemenng
     */
    public static final String BASEPARTNERINFO = "basePartnerinfo";
    /** 
     * String CONFPARTNERSERVICE :   
     * 厂商信息表查询类型中服务供应商类型    
     * @since  2013-11-13 guokemenng
     */
    public static final String BASEPARTNERSERVICE = "basePartnerService";
    /** 
     * String PARTNERBANDLEVEL :    
     * 品牌等级   
     * @since  2013-11-28 guokemenng
     */
    public static final String PARTNERBANDLEVEL = "partnerBandLevel";
    /** 
     * String PARTNERBRANDTYPE :
     * 品牌类型       
     * @since  2013-11-28 guokemenng
     */
    public static final String PARTNERBRANDTYPE = "partnerBrandType";
    
    /** 
     * String CONTACTSTYPE :
     * 联系人类型    
     * @since  2013-11-28 guokemenng
     */
    public static final String CONTACTSTYPE = "contactsType";

    /**
     * <code>Type</code>
     * 由于页面需要三个分类的值 先记录一下
     * 厂商类型
     * 分别对应： 1000  2000  3000
     * @version  1.0
     * @author  guokemenng
     * @since 1.0  2013-11-13
     */
    public enum Type {
        设备制造商, 设备供应商, 服务供应商;
    }

    /**
     * <code>ServiceType</code>
     *  服务供应商类型
     *  分别对应： 1000  2000  3000
     * @version  1.0
     * @author  guokemenng
     * @since 1.0  2013-11-14
     */
    public enum ServiceType {
        IT服务供应商, 物业服务供应商, 电力服务供应商;
    }

}
