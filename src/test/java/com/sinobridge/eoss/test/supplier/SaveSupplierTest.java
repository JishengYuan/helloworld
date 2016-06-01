package com.sinobridge.eoss.test.supplier;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;

import com.sinobridge.base.core.spring.SpringContext;
import com.sinobridge.eoss.base.basedata.model.BaseCityModel;
import com.sinobridge.eoss.base.basedata.model.BaseCountryModel;
import com.sinobridge.eoss.base.basedata.model.BaserPovinceModel;
import com.sinobridge.eoss.base.basedata.service.BaseCityService;
import com.sinobridge.eoss.base.basedata.service.BaseCountryService;
import com.sinobridge.eoss.base.basedata.service.BaserPovinceService;
import com.sinobridge.eoss.business.suppliermanage.model.SupplierContactsModel;
import com.sinobridge.eoss.business.suppliermanage.model.SupplierInfoModel;
import com.sinobridge.eoss.business.suppliermanage.service.SupplierContactsService;
import com.sinobridge.eoss.business.suppliermanage.service.SupplierInfoService;
import com.sinobridge.systemmanage.util.StringUtil;

public class SaveSupplierTest {

    public static void addSupplierInfo() {
        SupplierInfoService supplierInfoService = (SupplierInfoService) SpringContext.getContext().getBean("supplierInfoService");

        Connection connect = SqlServerJDBC.getConnect();
        Statement stmt = null; 
        ResultSet rs = null;
        try {
            stmt = connect.createStatement();
            rs = stmt.executeQuery("SELECT  * FROM dbo.Supplier where status = 0");
            String supplierId = "";
            String supplierType = "";
            String bizOwner = "";
            String supplierCode = "";
            String supplierName = "";
            String shortName = "";
            String englishName = "";
            String grad = "";
            String scale = "";
            String relationShip = "";
            String relationGrade = "";
            String phone = "";
            String fax = "";
            String country = "";
            String province = "";
            String city = "";
            String region = "";
            String zipCode = "";
            String address = "";
            String otherAddress = "";
            String web = "";
            String bankName = "";
            String bankAccount = "";
            String taxNo = "";
            String orgCode = "";
            String bizScope = "";
            String background = "";
            String remark = "";
            String creator = "";

            while (rs.next()) {
                supplierId = rs.getString("supplierId");
                supplierType = rs.getString("supplierType");
                bizOwner = rs.getString("bizOwner");
                supplierCode = rs.getString("supplierCode");
                supplierName = rs.getString("supplierName");
                shortName = rs.getString("shortName");
                englishName = rs.getString("englishName");
                grad = rs.getString("grade");
                scale = rs.getString("scale");
                relationShip = rs.getString("relationShip");
                relationGrade = rs.getString("relationGrade");
                phone = rs.getString("phone");
                fax = rs.getString("fax");
                country = rs.getString("country");
                province = rs.getString("province");
                city = rs.getString("city");
                region = rs.getString("region");
                zipCode = rs.getString("zipCode");
                address = rs.getString("address");
                otherAddress = rs.getString("otherAddress");
                web = rs.getString("web");
                bankName = rs.getString("bankName");
                bankAccount = rs.getString("bankAccount");
                taxNo = rs.getString("taxNo");
                orgCode = rs.getString("orgCode");
                bizScope = rs.getString("bizScope");
                background = rs.getString("background");
                remark = rs.getString("remark");
                creator = rs.getString("creator");
                
                
                SupplierInfoModel s = new SupplierInfoModel();
                s.setId(Long.parseLong(supplierId));
                if(!StringUtil.isEmpty(supplierType)){
                    if(supplierType.equals("1")){
                        supplierType = "1000";
                    }
                    if(supplierType.equals("2")){
                        supplierType = "2000";
                    }
                    if(supplierType.equals("3")){
                        supplierType = "3000";
                    }
                    if(supplierType.equals("4")){
                        supplierType = "4000";
                    }
                    if(supplierType.equals("5")){
                        supplierType = "5000";
                    }
                    if(supplierType.equals("6")){
                        supplierType = "6000";
                    }
                    if(supplierType.equals("7")){
                        supplierType = "7000";
                    }
                    if(supplierType.equals("8")){
                        supplierType = "8000";
                    }
                    if(supplierType.equals("9")){
                        supplierType = "9000";
                    }
                    s.setSupplierType(supplierType);
                }
                s.setBizOwner(bizOwner);
                s.setSupplierCode(supplierCode);
                s.setSupplierName(supplierName);
                s.setShortName(shortName);
                s.setEnglishName(englishName);
                if(!StringUtil.isEmpty(grad)){
                    if(grad.equals("1")){
                        grad = "1000";
                    }
                    if(grad.equals("2")){
                        grad = "2000";
                    }
                    if(grad.equals("3")){
                        grad = "3000";
                    }
                    if(grad.equals("4")){
                        grad = "4000";
                    }
                    if(grad.equals("5")){
                        grad = "5000";
                    }
                    s.setGrade(grad);
                }
                
                if(!StringUtil.isEmpty(scale)){
                    if(scale.equals("1")){
                        scale = "1000";
                    }
                    if(scale.equals("2")){
                        scale = "2000";
                    }
                    if(scale.equals("3")){
                        scale = "3000";
                    }
                    s.setScal(scale);
                }
                
                if(!StringUtil.isEmpty(relationShip)){
                    if(relationShip.equals("1")){
                        relationShip = "1000";
                    }
                    if(relationShip.equals("2")){
                        relationShip = "2000";
                    }
                    if(relationShip.equals("3")){
                        relationShip = "3000";
                    }
                    s.setRelationShip(relationShip);
                }
                
                s.setRelationGrade(relationGrade);
                s.setPhone(phone);
                s.setFax(fax);
                s.setCountry(country);
                s.setProvince(province);
                s.setCity(city);
                s.setRegion(region);
                if(!StringUtil.isEmpty(zipCode)){
                    s.setZipCode(Integer.parseInt(zipCode));
                }
                s.setAddress(address);
                s.setOtherAddress(otherAddress);
                s.setWeb(web);
                s.setBankName(bankName);
                if(!StringUtil.isEmpty(bankAccount)){
                    s.setBankAccount(bankAccount);
                }
                s.setTaxNo(taxNo);
                s.setOrgCode(orgCode);
                s.setBizScope(bizScope);
                s.setBackground(background);
                s.setRemark(remark);
                s.setCreator(creator);
                s.setCreateTime(new Date());
                s.setEnableReturnSpot((short)2);
//                Long.parseLong(supplierId)；
                supplierInfoService.saveOrUpdate(s);;
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

    public static void addSupplierContacts() {
        
        SupplierContactsService supplierContactsService = (SupplierContactsService) SpringContext.getContext().getBean("supplierContactsService");
        SupplierInfoService supplierInfoService = (SupplierInfoService) SpringContext.getContext().getBean("supplierInfoService");
        
        Connection connect = SqlServerJDBC.getConnect();
        Statement stmt = null; 
        ResultSet rs = null;
        try {
            stmt = connect.createStatement();
            rs = stmt.executeQuery("SELECT  * FROM dbo.SupplierContacts");
            String supplierId = "";
            String contactName = "";
            String contactPhone = "";
            String remark = "";
            String contactTelPhone = "";
            String supplierContactsId = "";
            
            while (rs.next()) {
                supplierId = rs.getString("supplierId");
                contactName = rs.getString("name");
                contactPhone = rs.getString("phone");
                contactTelPhone = rs.getString("mobile");
                remark = rs.getString("remark");
                supplierContactsId = rs.getString("supplierContactsId");
                
                SupplierContactsModel c = new SupplierContactsModel();
                if(!StringUtil.isEmpty(supplierId)){
                    SupplierInfoModel s = supplierInfoService.get(Long.parseLong(supplierId));
                    if(s != null){
                        c.setId(Long.parseLong(supplierContactsId));
                       c.setContactName(contactName);
                       c.setContactPhone(contactPhone);
                       c.setContactTelPhone(contactTelPhone);
                       c.setSupplierInfo(s);
                       c.setRemark(remark);
                       supplierContactsService.saveOrUpdate(c);
                    }
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
    
    public static void updateSupplierInfo(){
        SupplierInfoService supplierInfoService = (SupplierInfoService) SpringContext.getContext().getBean("supplierInfoService");

        BaseCountryService baseCountryService = (BaseCountryService) SpringContext.getContext().getBean("baseCountryService");
        BaserPovinceService baserPovinceService = (BaserPovinceService) SpringContext.getContext().getBean("baserPovinceService");
        BaseCityService baseCityService = (BaseCityService) SpringContext.getContext().getBean("baseCityService");
        
        List<SupplierInfoModel> sL = supplierInfoService.find();
        for(SupplierInfoModel s : sL){
//            String bizOwner = s.getBizOwner();
//            if(!StringUtil.isEmpty(bizOwner)){
//                if(bizOwner.trim().endsWith("叶瑞萌")){
//                    s.setBizOwner("1007");
//                }
//                if(bizOwner.trim().endsWith("郭诗昕")){
//                    s.setBizOwner("246");
//                }
//                if(bizOwner.trim().endsWith("刘博")){
//                    s.setBizOwner("354");
//                }
//                if(bizOwner.trim().endsWith("黄旭")){
//                    s.setBizOwner("385");
//                }
//                if(bizOwner.trim().endsWith("刘莎莎")){
//                    s.setBizOwner("424");
//                }
//                if(bizOwner.trim().endsWith("任琳")){
//                    s.setBizOwner("464");
//                }
//                if(bizOwner.trim().endsWith("杨怡晨")){
//                    s.setBizOwner("465");
//                }
//                if(bizOwner.trim().endsWith("taxt")){
//                    s.setBizOwner("479");
//                }
//                if(bizOwner.trim().endsWith("李娜")){
//                    s.setBizOwner("641");
//                }
//                if(bizOwner.trim().endsWith("刘新芳")){
//                    s.setBizOwner("67");
//                }
//                if(bizOwner.trim().endsWith("崔晓宁")){
//                    s.setBizOwner("72");
//                }
//                if(bizOwner.trim().endsWith("宋双双")){
//                    s.setBizOwner("988");
//                }
//            }
//            String relationGrade = s.getRelationGrade();
//            if(!StringUtil.isEmpty(relationGrade)){
//                if(relationGrade.equals("1")){
//                    s.setRelationGrade("1000");
//                }
//                if(relationGrade.equals("2")){
//                    s.setRelationGrade("2000");
//                }
//                if(relationGrade.equals("3")){
//                    s.setRelationGrade("3000");
//                }
//            }
            String countryId = s.getCountry();
            if(!StringUtil.isEmpty(countryId)){
               BaseCountryModel m =  baseCountryService.get(Long.parseLong(countryId));
               if(m != null){
                   s.setCountry(m.getCountryCName());
               }
            }
            String provinceId = s.getProvince();
            if(!StringUtil.isEmpty(provinceId)){
               BaserPovinceModel p =  baserPovinceService.get(Long.parseLong(provinceId));
               if(p != null){
                   s.setProvince(p.getProvinceName());
               }
            }
            String cityId = s.getCity();
            if(!StringUtil.isEmpty(cityId)){
               BaseCityModel m =  baseCityService.get(Long.parseLong(cityId));
               if(m != null){
                   s.setCity(m.getCityName());
               }
            }
            supplierInfoService.update(s);
        }
    }
    
    public static void main(String[] args) {
//        addSupplierInfo();
//        addSupplierContacts();
        updateSupplierInfo();
    }

}
