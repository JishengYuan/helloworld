package com.sinobridge.eoss.test.supplier;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.sinobridge.base.core.spring.SpringContext;
import com.sinobridge.eoss.business.projectmanage.model.BasePartnerInfo;
import com.sinobridge.eoss.business.projectmanage.model.BasePartnerProduct;
import com.sinobridge.eoss.business.projectmanage.model.BaseProductModel;
import com.sinobridge.eoss.business.projectmanage.model.BaseProductType;
import com.sinobridge.eoss.business.projectmanage.service.BasePartnerInfoService;
import com.sinobridge.eoss.business.projectmanage.service.BasePartnerProductService;
import com.sinobridge.eoss.business.projectmanage.service.BaseProductModelService;
import com.sinobridge.eoss.business.projectmanage.service.BaseProductTypeService;

public class SaveProductMTest {

    public static void addPartner() {

        BasePartnerInfoService basePartnerInfoService = (BasePartnerInfoService) SpringContext.getContext().getBean("basePartnerInfoService");

        Connection connect = SqlServerJDBC.getConnect();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = connect.createStatement();
            rs = stmt.executeQuery("SELECT * FROM dbo.Vendor");
            String vendorId = "";
            String vendorCode = "";
            String vendorName = "";
            String vendorEnName = "";
            String currency = "";

            while (rs.next()) {
                vendorId = rs.getString("vendorId");
                vendorCode = rs.getString("vendorCode");
                vendorName = rs.getString("vendorName");
                vendorEnName = rs.getString("vendorEnName");
                currency = rs.getString("currency");

                BasePartnerInfo p = new BasePartnerInfo();
                p.setId(vendorId);
                p.setPartnerCode(vendorCode);
                p.setPartnerEnCode(vendorEnName);
                p.setPartnerFullName(vendorName);
                p.setCurrency(currency);
                p.setDeleteFlag((short)1);
                basePartnerInfoService.create(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (connect != null) {
                    connect.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    
    public static void addPartnerProduct() {

        BasePartnerInfoService basePartnerInfoService = (BasePartnerInfoService) SpringContext.getContext().getBean("basePartnerInfoService");
        BaseProductTypeService baseProductTypeService = (BaseProductTypeService) SpringContext.getContext().getBean("baseProductTypeService");
        BasePartnerProductService basePartnerProductService = (BasePartnerProductService) SpringContext.getContext().getBean("basePartnerProductService");

        Connection connect = SqlServerJDBC.getConnect();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = connect.createStatement();
            rs = stmt.executeQuery("SELECT  * FROM dbo.ProductType");
            String productType = "";
            String productTypeId = "";
            String vendorId = "";
            String productTypeName = "";

            while (rs.next()) {
                productType = rs.getString("productType");
                productTypeId = rs.getString("productTypeId");
                vendorId = rs.getString("vendorId");
                vendorId = rs.getString("vendorId");
                productTypeName = rs.getString("productTypeName");
                
                BaseProductType type = baseProductTypeService.get(productType);
                if(type == null){
                    BaseProductType t = new BaseProductType();
                    t.setId(productType);
                    t.setTypeName(productTypeName);
                    t.setDeleteFlag((short)0);
                    baseProductTypeService.create(t);
                }
                BasePartnerInfo pa = basePartnerInfoService.get(vendorId);
                if(pa != null){
                    BasePartnerProduct p = new BasePartnerProduct();
                    p.setId(productTypeId);
                    p.setProductTypeId(productType);
                    p.setBasePartnerInfo(pa);
                    basePartnerProductService.create(p);
                }
                
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (connect != null) {
                    connect.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void addPartnerProductModel() {

        BasePartnerInfoService basePartnerInfoService = (BasePartnerInfoService) SpringContext.getContext().getBean("basePartnerInfoService");
        BaseProductTypeService baseProductTypeService = (BaseProductTypeService) SpringContext.getContext().getBean("baseProductTypeService");
        BaseProductModelService baseProductModelService = (BaseProductModelService) SpringContext.getContext().getBean("baseProductModelService");

        Connection connect = SqlServerJDBC.getConnect();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = connect.createStatement();
            rs = stmt.executeQuery("SELECT * FROM dbo.ProductList where productID >= 5000");
            String productID = "";
            String vendorCode = "";
            String productType = "";
            String productNo = "";
            String description = "";
            String currency = "";
            String listPrice = "";

            while (rs.next()) {
                productID = rs.getString("productID");
                vendorCode = rs.getString("vendorCode");
                productType = rs.getString("productType");
                productNo = rs.getString("productNo");
                description = rs.getString("description");
                currency = rs.getString("currency");
                listPrice = rs.getString("listPrice");
                
                String hql = "from BasePartnerInfo where partnerCode = ?";
                String[] param = new String[1];
                param[0] = vendorCode;
                List<BasePartnerInfo> infoList = basePartnerInfoService.getDao().find(hql, param);
                if(infoList.size() > 0){
                    BaseProductModel m = new BaseProductModel();
                    m.setId(productID);
                    m.setPartnerId(infoList.get(0).getId());
                    
                    String typeHql = "from BaseProductType where typeName = ?";
                    String[] paramName = new String[1];
                    paramName[0] = productType;
                    List<BaseProductType> typeList = baseProductTypeService.getDao().find(typeHql, paramName);
                    if(typeList.size() > 0){
                        m.setProductType(typeList.get(0).getId());
                    }
                    
                    m.setProductModel(productNo);
                    m.setProductDesc(description);
                    m.setCurrency(currency);
                    m.setListPrice(listPrice);
                    baseProductModelService.create(m);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (connect != null) {
                    connect.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static void main(String[] args) {
        addPartnerProductModel();
    }

}
