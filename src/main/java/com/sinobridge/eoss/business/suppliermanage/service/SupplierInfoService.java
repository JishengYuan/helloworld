package com.sinobridge.eoss.business.suppliermanage.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinobridge.base.core.service.DefaultBaseService;
import com.sinobridge.eoss.business.suppliermanage.SupplierConstants;
import com.sinobridge.eoss.business.suppliermanage.dao.SupplierInfoDao;
import com.sinobridge.eoss.business.suppliermanage.model.ReturnSpotModel;
import com.sinobridge.eoss.business.suppliermanage.model.SupplierContactsModel;
import com.sinobridge.eoss.business.suppliermanage.model.SupplierInfoModel;
import com.sinobridge.systemmanage.util.Global;
import com.sinobridge.systemmanage.util.StringUtil;
import com.sinobridge.systemmanage.vo.SystemUser;

@Service
@Transactional
public class SupplierInfoService extends DefaultBaseService<SupplierInfoModel, SupplierInfoDao> {

    @Autowired
    private SupplierContactsService contactsService;

    @Autowired
    private ReturnSpotService spotService;

    /**
     * <code>getRowNames</code>
     * 得到联系人列名
     * @return
     * @since   2014年5月21日    guokemenng
     */
    public List<Map<String, Object>> getRowNames() {
        List<Map<String, Object>> rowNames = new ArrayList<Map<String, Object>>();
        /*HashMap<String, Object> contactId = new HashMap<String, Object>();
        contactId.put("name", "id");
        contactId.put("label", "联系人编号");
        contactId.put("type", "string");
        rowNames.add(contactId);*/

        HashMap<String, Object> contactName = new HashMap<String, Object>();
        contactName.put("name", "contactName");
        contactName.put("label", "姓名");
        contactName.put("type", "string");
        rowNames.add(contactName);

        HashMap<String, Object> contactPhone = new HashMap<String, Object>();
        contactPhone.put("name", "contactPhone");
        contactPhone.put("label", "电话");
        contactPhone.put("type", "string");
        rowNames.add(contactPhone);

        HashMap<String, Object> contactTelPhone = new HashMap<String, Object>();
        contactTelPhone.put("name", "contactTelPhone");
        contactTelPhone.put("label", "手机号");
        contactTelPhone.put("type", "string");
        rowNames.add(contactTelPhone);

        HashMap<String, Object> remark = new HashMap<String, Object>();
        remark.put("name", "remark");
        remark.put("label", "备注");
        remark.put("type", "string");
        rowNames.add(remark);
        return rowNames;
    }

    /**
     * <code>getRowDatas</code>
     * 得到联系人数据
     * @return
     * @since   2014年5月21日    guokemenng
     */
    public List<Map<String, Object>> getRowDatas(SupplierInfoModel info) {
        List<Map<String, Object>> rowDatas = new ArrayList<Map<String, Object>>();
        Set<SupplierContactsModel> contactsSet = info.getContactsModels();
        Iterator<SupplierContactsModel> it = contactsSet.iterator();
        while (it.hasNext()) {
            SupplierContactsModel c = it.next();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", c.getId());
            map.put("contactName", c.getContactName());
            map.put("contactPhone", c.getContactPhone());
            map.put("contactTelPhone", c.getContactTelPhone());
            map.put("remark", c.getRemark());
            rowDatas.add(map);
        }

        return rowDatas;
    }

    /**
     * <code>doSaveOrUpdate</code>
     * 保存供应商信息
     * @param request
     * @param infoModel
     * @since   2014年5月21日    guokemenng
     */
    public void doSaveOrUpdate(HttpServletRequest request, SupplierInfoModel infoModel) {
        try {
            Long id = infoModel.getId();
            if (id == null) {
                //如果用户点击"新增"按钮，此时是没有id的，进入if，表示增加供应商信息
                createSupplier(request, infoModel);
            } else {
                SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
                infoModel.setModifier(systemUser.getStaffName());
                Date d = new Date();
                infoModel.setModifyTime(d);

                //获取从前端传递过来的联系人JSON数据串
                String tableData = request.getParameter("tableData");
                String[] contactIds = request.getParameterValues("contactIds");
                contactsService.updateContactsBySupplierId(id, infoModel, tableData, contactIds);
                String _country = request.getParameter("_country");
                makeSupplierInfoModel(infoModel, _country);
                this.update(infoModel);
                Short enableReturnSpot = infoModel.getEnableReturnSpot();
                List<ReturnSpotModel> spotList = getSpotModelBySupplierId(infoModel.getId());
                if (SupplierConstants.ENABLERETURNSPOT != enableReturnSpot) {
                    if (spotList.size() > 0) {
                        Iterator<ReturnSpotModel> it = spotList.iterator();
                        while (it.hasNext()) {
                            ReturnSpotModel spot = it.next();
                            spotService.getDao().delete(spot);
                        }
                    }
                } else {
                    if (spotList.size() == 0) {
                        ReturnSpotModel spot = new ReturnSpotModel();
                        spot.setCreateTime(new Date());
                        spot.setSupplierInfo(infoModel);
                        spot.setReturnAmount(0);
                        spotService.create(spot);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //插入封装好的SupplierInfoModel中的数据到数据库相应表中
    public void createSupplier(HttpServletRequest request, SupplierInfoModel infoModel) {
        SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
        infoModel.setCreator(systemUser.getStaffName());
        Date d = new Date();
        infoModel.setCreateTime(d);
        String _country = request.getParameter("_country");
        makeSupplierInfoModel(infoModel, _country);
        this.create(infoModel);
        Short enableReturnSpot = infoModel.getEnableReturnSpot();
        if (SupplierConstants.ENABLERETURNSPOT == enableReturnSpot) {
            ReturnSpotModel spot = new ReturnSpotModel();
            spot.setCreateTime(new Date());
            spot.setSupplierInfo(infoModel);
            spot.setReturnAmount(0);
            spotService.create(spot);
        }
        String tableData = request.getParameter("tableData");
        contactsService.saveSupplierContacts(infoModel, tableData);
    }

    /**
     * <code>makeSupplierInfoModel</code>
     *
     * @param infoModel
     * @param _country
     * @since   2014年5月22日    guokemenng
     */
    public void makeSupplierInfoModel(SupplierInfoModel infoModel, String _country) {
        if (!StringUtil.isEmpty(_country)) {
            String[] ss = _country.split("-");
            int i = ss.length;
            if (i == 1) {
                infoModel.setCountry(ss[0]);
            }
            if (i == 2) {
                infoModel.setCountry(ss[0]);
                infoModel.setProvince(ss[1]);
            }
            if (i == 3) {
                infoModel.setCountry(ss[0]);
                infoModel.setProvince(ss[1]);
                infoModel.setCity(ss[2]);
            }
        }
    }

    /**
     * 保存供应商信息为草稿状态
     * @param request
     * @param infoModel
     */
    public void saveSupplierInfoAsDraft(HttpServletRequest request, SupplierInfoModel infoModel) {
        if (infoModel.getId() == null) //只有当用户点击的是新建按钮时，才会执行这里的插入操作
            createSupplier(request, infoModel);
    }

    /**
     * <code>detele</code>
     * 删除操作
     * @param ids  选中的供应商编号id串，按照逗号(,)分割
     * @since   2014年5月22日    guokemenng
     */
    public void deleteIds(String ids) {
        //按照逗号进行分割，得到选中的要删除的每一个供应商id数组
        String[] idArr = ids.split(",");
        for (String id : idArr) {
            StringBuffer sb = new StringBuffer();
            //由于测试时，测到了存在某个查询出来的联系人id信息的第一条记录，其对应的business_order表中就没有数据
            //所以这样返回的count(*)值也是0，导致也执行了this.delete(Long.parseLong(id));操作
            //所以还是使用in来挨个对子查询结果进行比对。如果都比对完，结果还是0，则说明没有外键约束，将其删除
            sb.append("SELECT COUNT(*) FROM business_order WHERE supplierContacts IN(");
            sb.append("SELECT id FROM bussiness_suppliercontacts WHERE SupplierId=?)");
            Long[] params = new Long[1];
            params[0] = Long.parseLong(id);
            //这个uniqueResult()在这里返回的是一个BitInteger对象，所以之前试图将其转型成Long类型，报出了异常
            BigInteger result = (BigInteger) this.getDao().createSQLQuery(sb.toString(), params).uniqueResult();
            if (result == null || result.longValue() == 0) {
                this.delete(Long.parseLong(id));
            } else {
                //如果不能删除，抛一个运行时异常，让调用者进行捕获，从而进行相应地处理
                throw new RuntimeException("business_order表中存在对bussiness_contacts表中主键id的外键约束，无法删除之！");
            }
        }

    }

    /**
     * <code>getBizOwner</code>
     * 得到商务负责人集合
     * @return
     * @since   2014年5月23日    guokemenng
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> getBizOwner() {
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        String[] param = new String[2];
        param[0] = SupplierConstants.BIZOWNERASSIGN;
        param[1] = "1";
        String sql = "select staffId,staffName from sys_staff where staffId in (select staffId from sys_stafforg where orgId = ?) and state = ?";

        Query query = this.getDao().createSQLQuery(sql, param);
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List<Map<String, Object>> objList = query.list();
        for (Map<String, Object> objMap : objList) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", objMap.get("staffId"));
            map.put("name", objMap.get("staffName"));
            mapList.add(map);
        }

        return mapList;
    }

    /**
     * <code>getSpotModelBySupplierId</code>
     * 根据厂商Id得到返点数
     * @param supplierId
     * @return
     * @since   2014年12月3日    guokemenng
     */
    @SuppressWarnings("unchecked")
    public List<ReturnSpotModel> getSpotModelBySupplierId(Long supplierId) {
        Object[] param = new Object[] { supplierId };
        String sql = "select * from business_returnspot where supplierId = ?";
        return getDao().createSQLQuery(sql, param).addEntity(ReturnSpotModel.class).list();
    }

    /**
     * @param name
     * @return
     */
    public int checkShortName(String name) {
        int flat = getDao().checkShortName(name);
        return flat;
    }

    /**
     * @param supplierCode
     * @return
     */
    public int checkSupplierCode(String supplierCode) {
        int flat = getDao().checkSupplierCode(supplierCode);
        return flat;
    }

}