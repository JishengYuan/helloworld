package com.sinobridge.eoss.business.suppliermanage.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinobridge.base.core.service.DefaultBaseService;
import com.sinobridge.base.core.utils.JacksonJsonMapper;
import com.sinobridge.eoss.business.suppliermanage.dao.SupplierContactsDao;
import com.sinobridge.eoss.business.suppliermanage.model.SupplierContactsModel;
import com.sinobridge.eoss.business.suppliermanage.model.SupplierInfoModel;

@Service
@Transactional
public class SupplierContactsService extends DefaultBaseService<SupplierContactsModel, SupplierContactsDao> {

    /**
     * <code>saveSupplierContacts</code>
     * 保存联系人
     * @param infoId
     * @param tableData
     * @since   2014年5月21日    guokemenng
     */
    public void saveSupplierContacts(SupplierInfoModel infoModel, String tableData) {
        try {
            List<Map<String, Object>> mapList = buildContactsMapList(infoModel, tableData);
            if (mapList != null && mapList.size() > 0) {
                for (Map<String, Object> map : mapList) {
                    SupplierContactsModel contacts = new SupplierContactsModel();
                    contacts.setSupplierInfo(infoModel);
                    saveSupplierContacts(contacts, map);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据JSON数据串构造Map对象
     * @param infoModel
     * @param tableData
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> buildContactsMapList(SupplierInfoModel infoModel, String tableData) {
        ObjectMapper objectMapper = JacksonJsonMapper.getInstance();
        Map<String, Object> tableDataMap = null;
        try {
            tableDataMap = objectMapper.readValue(tableData, HashMap.class);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        //解析JSON数据串
        if (tableDataMap != null && tableDataMap.size() > 0) {
            for (String key : tableDataMap.keySet()) {
                List<Object> gridDataList = (List<Object>) tableDataMap.get(key);
                if (gridDataList != null && gridDataList.size() > 0) {
                    for (int i = 0; i < gridDataList.size(); i++) {//加了非空判断
                        Map<String, Object> map = StringToMap(gridDataList.get(i).toString());//格式转换
                        if (map.size() != 0) {
                            mapList.add(map);
                        }
                    }
                }
            }
        }
        return mapList;
    }

    /**
     * <code>saveSupplierContacts</code>
     * 执行保存联系人操作
     * @param map
     * @since   2014年5月21日    guokemenng
     */
    public void saveSupplierContacts(SupplierContactsModel contacts, Map<String, Object> map) {
        try {
            buildContactsModel(map, contacts);
            this.create(contacts);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * <code>StringToMap</code>
     *
     * @param mapText
     * @return
     * @since   2014年5月21日    guokemenng
     */
    public synchronized Map<String, Object> StringToMap(String mapText) {

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
     * <code>deleteContactsBySupplierId</code>
     *
     * @param suppilerId
     * @since   2014年5月21日    guokemenng
     */
    public void deleteContactsBySupplierId(Long suppilerId) {
        try {
            Long[] param = new Long[1];
            param[0] = suppilerId;
            String sql = "delete from bussiness_suppliercontacts where supplierId = ?";
            this.getDao().createSQLQuery(sql, param).executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void buildContactsModel(Map<String, Object> map, SupplierContactsModel contacts) {
        try {
            Object contactName = map.get("contactName");
            Object contactPhone = map.get("contactPhone");
            Object contactTelPhone = map.get("contactTelPhone");
            Object remark = map.get("remark");
            if (contactPhone != null) {
                contacts.setContactPhone(contactPhone + "");
            }
            if (contactTelPhone != null) {
                contacts.setContactTelPhone(contactTelPhone + "");
            }
            if (remark != null) {
                contacts.setRemark(remark + "");
            }
            if (contactName != null) {
                contacts.setContactName(contactName + "");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 按照供应商的id更新其对应联系人信息
     * @param supplierId  供应商ID
     */
    public void updateContactsBySupplierId(Long supplierId, SupplierInfoModel infoModel, String tableData, String[] contactIds) {
        List<Map<String, Object>> mapList = buildContactsMapList(infoModel, tableData);
        if (mapList != null && mapList.size() > 0) {
            int index = 0;
            for (Map<String, Object> map : mapList) {
                SupplierContactsModel contacts = new SupplierContactsModel();
                buildContactsModel(map, contacts);
                contacts.setSupplierInfo(infoModel);
                contacts.setId(Long.parseLong(contactIds[index]));
                index++;

                this.update(contacts);
                /*Object[] params = new Object[5];
                int index = 0;
                String sql = "update bussiness_suppliercontacts set ";
                if (!StringUtils.isNullOrEmpty(contacts.getContactName())) {
                    params[index++] = contacts.getContactName();
                    sql += "ContactName=?,";
                }
                if (!StringUtils.isNullOrEmpty(contacts.getContactPhone())) {
                    params[index++] = contacts.getContactPhone();
                    sql += "ContactPhone=?,";
                }
                if (!StringUtils.isNullOrEmpty(contacts.getContactTelPhone())) {
                    params[index++] = contacts.getContactTelPhone();
                    sql += "ContactTelPhone=?,";
                }
                if (!StringUtils.isNullOrEmpty(contacts.getRemark())) {
                    params[index++] = contacts.getRemark();
                    sql += "Remark=? ";
                }
                params[index++] = supplierId;
                sql += "where SupplierId=?";
                this.getDao().executeSql(sql, params);*/
            }
        }

    }

    @SuppressWarnings("unchecked")
    public List<SupplierContactsModel> getSupplierContactsList(Long supplierId) {
        List<SupplierContactsModel> contactsList = new ArrayList<SupplierContactsModel>();
        String hql = "SELECT * FROM bussiness_suppliercontacts WHERE SupplierId=?";
        Long[] params = { supplierId };
        List<Object[]> list = this.getDao().createSQLQuery(hql, params).list();
        if (list != null && list.size() > 0) {
            for (int x = 0; x < list.size(); x++) {
                Object[] rows = list.get(x);
                SupplierContactsModel model = new SupplierContactsModel();
                Long id = rows[0] != null ? (Long) rows[0] : null;
                model.setId(id);

                String contactName = rows[1] != null ? rows[1].toString() : null;
                model.setContactName(contactName);

                String contactPhone = rows[2] != null ? rows[2].toString() : null;
                String contactTelPhone = rows[3] != null ? rows[3].toString() : null;
                String remark = rows[4] != null ? rows[4].toString() : null;
                model.setContactPhone(contactPhone);
                model.setContactTelPhone(contactTelPhone);
                model.setRemark(remark);
                contactsList.add(model);
            }
        }
        return contactsList;
    }

}