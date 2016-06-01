package com.sinobridge.eoss.business.projectmanage.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.sinobridge.eoss.business.projectmanage.model.BasePartnerInfo;
import com.sinobridge.systemmanage.util.ChineseSpell;
import com.sinobridge.systemmanage.util.StringUtil;

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
 * 2014年8月19日 下午4:47:03          guo_kemeng        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
public class PartnerUtils {

    public static final Map<String, String> map = new HashMap<String, String>() {
        private static final long serialVersionUID = -6054818902185221825L;
        {
            put("1", "AB");
            put("2", "CD");
            put("3", "EF");
            put("4", "GH");
            put("5", "IJ");
            put("6", "KL");
            put("7", "MN");
            put("8", "OP");
            put("9", "QR");
            put("10", "ST");
            put("11", "UV");
            put("12", "WX");
            put("13", "YZ");
        }
    };

    /**
     * <code>getInitializePartnerCty</code>
     * 得到厂商根据字母的分类列表
     * @return
     * @since   2014年8月19日    guokemenng
     */
    public static List<Map<String, Object>> getInitializePartnerCty() {
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>(13);
        for (int i = 0; i < 13; i++) {
            int id = i + 1;
            mapList.add(transferMap(id, PartnerUtils.map.get(String.valueOf(id))));
        }
        return mapList;
    }

    /**
     * <code>transferPartner</code>
     * 得到组装的厂商
     * @param id
     * @param partnerList
     * @return
     * @since   2014年8月19日    guokemenng
     */
    public static List<Map<String, Object>> transferPartner(String id, List<BasePartnerInfo> partnerList) {
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>(13);
        String type = map.get(id);
        for (BasePartnerInfo model : partnerList) {
            String name = model.getPartnerFullName();
            if (!StringUtil.isEmpty(name)) {
                String str = ChineseSpell.getFullSpell(name);
                String sr = str.toUpperCase();
                if (type.indexOf(sr.charAt(0)) != -1) {
                    mapList.add(transferPartner(model.getId(), model.getPartnerFullName()));
                }
            }
        }
        return mapList;
    }

    /**
     * <code>transferMap</code>
     * 组装Map
     * @param id
     * @param text
     * @return
     * @since   2014年8月19日    guokemenng
     */
    public static Map<String, Object> transferMap(Object id, Object text) {
        Map<String, Object> m = new HashMap<String, Object>();
        m.put("id", id);
        m.put("text", text);
        m.put("hasDepartment", true);
        m.put("checkbox", false);
        m.put("state", "closed");
        m.put("type", "department");
        m.put("iconCls", "icon_department");
        return m;
    }

    /**
     * <code>transferPartner</code>
     * 组装厂商成Map形式
     * @param id
     * @param text
     * @return
     * @since   2014年8月19日    guokemenng
     */
    public static Map<String, Object> transferPartner(Object id, Object text) {
        Map<String, Object> m = new HashMap<String, Object>();
        m.put("id", id);
        m.put("text", text);
        m.put("type", "account");
        m.put("iconCls", "icon_account");
        return m;
    }

    /**
     * <code>getTypeByPartner</code>
     * 根据厂商得到Type Id
     * @param model
     * @return
     * @since   2014年8月19日    guokemenng
     */
    public static String getTypeByPartner(BasePartnerInfo model) {
        String name = model.getPartnerFullName();
        String str = ChineseSpell.getFullSpell(name);
        String sr = str.toUpperCase();
        for (Entry<String, String> entry : map.entrySet()) {
            if(entry.getValue().indexOf(sr.charAt(0)) != -1){
                return entry.getKey();
            }
        }
        return "";
    }

    
    public static void main(String[] args) {
        List<Map<String, Object>> mapList = getInitializePartnerCty();
        for(Map<String,Object> m : mapList){
            System.out.println(m);
        }
    }
    
}
