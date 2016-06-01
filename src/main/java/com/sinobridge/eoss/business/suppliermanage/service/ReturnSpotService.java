package com.sinobridge.eoss.business.suppliermanage.service;

import java.math.BigInteger;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinobridge.base.core.service.DefaultBaseService;
import com.sinobridge.eoss.business.suppliermanage.SupplierConstants;
import com.sinobridge.eoss.business.suppliermanage.dao.ReturnSpotDao;
import com.sinobridge.eoss.business.suppliermanage.dao.ReturnSpotLogDao;
import com.sinobridge.eoss.business.suppliermanage.dao.SupplierInfoDao;
import com.sinobridge.eoss.business.suppliermanage.dto.SupplierReturnDto;
import com.sinobridge.eoss.business.suppliermanage.model.ReturnSpotLogModel;
import com.sinobridge.eoss.business.suppliermanage.model.ReturnSpotModel;
import com.sinobridge.eoss.business.suppliermanage.model.SupplierInfoModel;
import com.sinobridge.systemmanage.util.Global;
import com.sinobridge.systemmanage.util.StringUtil;
import com.sinobridge.systemmanage.vo.SystemUser;

@Service
@Transactional
public class ReturnSpotService extends DefaultBaseService<ReturnSpotModel, ReturnSpotDao>{


    @Autowired
    private SupplierInfoDao infoDao;
    
    @Autowired
    private ReturnSpotLogDao spotLogDao;
    
    /**
     * <code>getMapList</code>
     * 把前台列表展示页数据封装成List<Map>,
     * @param request
     * @return
     * @since   2014年5月23日    guokemenng
     */
    @SuppressWarnings("unchecked")
    public List<Map<String,Object>> getMapList(HttpServletRequest request,Integer pageNo,Integer pageSize){

        StringBuilder sb = new StringBuilder();
        String sql0 = "select s.id,r.returnAmount,r.remark ,s.supplierType,s.supplierCode,s.shortName from business_returnspot r left join bussiness_supplier s on r.supplierId = s.id order by r.CreateTime desc";
        String sql1 = "select s.id,r.returnAmount,r.remark ,s.supplierType,s.supplierCode,s.shortName from business_returnspot r left join bussiness_supplier s on r.supplierId = s.id  ";
        Query query = null;
        String searchParam = request.getParameter("searchParam");
        if(StringUtil.isEmpty(searchParam)){
            sb.append(sql0);
            query = this.getDao().createSQLQuery(sb.toString());
            query.setFirstResult(pageNo);
            query.setMaxResults(pageSize);
            query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        } else {
            sb.append(sql1);
            sb.append(" where");
            String[] ss = searchParam.split(",");
            String[] param = new String[ss.length];
            for(int i = 0;i < ss.length;i++){
                String[] sss = ss[i].split("_");
                if(i == 0){
                    if(sss[1].equals("supplierType")){
                        param[i] = sss[0];
                        sb.append(" s." + sss[1] + " = ? ");
                    } else if(sss[1].equals("supplierCode")){
                        param[i] = sss[0]+"%";
                        sb.append(" s." + sss[1] + " like ? ");
                    } else if(sss[1].equals("shortName")){
                        param[i] = sss[0]+"%";
                        sb.append(" s." + sss[1] + " like ? ");
                    } else if(sss[1].equals("smallReturn")){
                        param[i] = sss[0]+"%";
                        sb.append(" r.returnAmount >= ? ");
                    } else if(sss[1].equals("bigReturn")){
                        param[i] = sss[0]+"%";
                        sb.append(" r.returnAmount <= ? ");
                    }
                } else {
                    if(sss[1].equals("supplierType")){
                        param[i] = sss[0];
                        sb.append("and s." + sss[1] + " = ? ");
                    } else if(sss[1].equals("supplierCode")){
                        param[i] = sss[0]+"%";
                        sb.append("and s." + sss[1] + " like ? ");
                    } else if(sss[1].equals("shortName")){
                        param[i] = sss[0]+"%";
                        sb.append("and s." + sss[1] + " like ? ");
                    } else if(sss[1].equals("smallReturn")){
                        param[i] = sss[0]+"%";
                        sb.append("and r.returnAmount >= ? ");
                    } else if(sss[1].equals("bigReturn")){
                        param[i] = sss[0]+"%";
                        sb.append("and r.returnAmount <= ? ");
                    }
                }
            }
            sb.append(" order by r.CreateTime desc");
            query = this.getDao().createSQLQuery(sb.toString(),param);
            query.setFirstResult(pageNo);
            query.setMaxResults(pageSize);
            query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        }
        
        return query.list();
    }

    /**
     * <code>getCountByQuery</code>
     * 根据查询条件得到数据条数
     * @param request
     * @return
     * @since   2014年5月23日    guokemenng
     */
    public Integer getCountByQuery(HttpServletRequest request){

        String searchParam = request.getParameter("searchParam");
        String sql = "select count(*) from business_returnspot r left join bussiness_supplier s on r.supplierId = s.id";
        StringBuilder sb = new StringBuilder();
        Object o = null;
        if (StringUtil.isEmpty(searchParam)) {
            sb.append(sql);
            o = getDao().createSQLQuery(sb.toString()).list().get(0);
        } else {
            sb.append(sql);
            sb.append(" where");
            String[] ss = searchParam.split(",");
            String[] param = new String[ss.length];
            for(int i = 0;i < ss.length;i++){
                String[] sss = ss[i].split("_");
                if(i == 0){
                    if(sss[1].equals("supplierType")){
                        param[i] = sss[0];
                        sb.append(" s." + sss[1] + " = ? ");
                    } else if(sss[1].equals("supplierCode")){
                        param[i] = sss[0]+"%";
                        sb.append(" s." + sss[1] + " like ? ");
                    } else if(sss[1].equals("shortName")){
                        param[i] = sss[0]+"%";
                        sb.append(" s." + sss[1] + " like ? ");
                    } else if(sss[1].equals("smallReturn")){
                        param[i] = sss[0]+"%";
                        sb.append(" r.returnAmount >= ? ");
                    } else if(sss[1].equals("bigReturn")){
                        param[i] = sss[0]+"%";
                        sb.append(" r.returnAmount <= ? ");
                    }
                } else {
                    if(sss[1].equals("supplierType")){
                        param[i] = sss[0];
                        sb.append("and s." + sss[1] + " = ? ");
                    } else if(sss[1].equals("supplierCode")){
                        param[i] = sss[0]+"%";
                        sb.append("and s." + sss[1] + " like ? ");
                    } else if(sss[1].equals("shortName")){
                        param[i] = sss[0]+"%";
                        sb.append("and s." + sss[1] + " like ? ");
                    } else if(sss[1].equals("smallReturn")){
                        param[i] = sss[0]+"%";
                        sb.append("and r.returnAmount <= ? ");
                    } else if(sss[1].equals("bigReturn")){
                        param[i] = sss[0]+"%";
                        sb.append("and r.returnAmount <= ? ");
                    }
                }
            }
            o = getDao().createSQLQuery(sb.toString(),param).list().get(0);
        }
        if (o != null) {
            BigInteger b = (BigInteger) o;
            return b.intValue();
        } else {
            return 0;
        }
        
    }
    
    /**
     * <code>getSupplierInfo</code>
     * 得到供方和返点余额信息
     * @param id
     * @return
     * @since   2014年5月26日    guokemenng
     */
    public SupplierReturnDto getSupplierInfo(String id){
        
        SupplierInfoModel info = infoDao.get(Long.parseLong(id));
        SupplierReturnDto dto = new SupplierReturnDto();
        dto.setShortName(info.getShortName());
        dto.setSupplierCode(info.getSupplierCode());
        Iterator<ReturnSpotModel> it = info.getSpotModels().iterator();
        while(it.hasNext()){
            ReturnSpotModel spot = it.next();
            dto.setId(spot.getId());
            dto.setReturnAmount(spot.getReturnAmount());
        }
        
        return dto;
    }
    
    /**
     * <code>addReturnSpot</code>
     *
     * @param request
     * @since   2014年5月26日    guokemenng
     */
    public void addReturnSpot(HttpServletRequest request){
        String supplierId = request.getParameter("supplierId");
        String returnAmount = request.getParameter("returnAmount");
        String remark = request.getParameter("remark");
        
        SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
        ReturnSpotModel spot = new ReturnSpotModel();
        SupplierInfoModel info = infoDao.get(Long.parseLong(supplierId));
        spot.setSupplierInfo(info);
        spot.setRemark(remark);
        spot.setReturnAmount(Float.parseFloat(returnAmount));
        
        spot.setCreator(systemUser.getStaffName());
        spot.setCreateTime(new Date());
        this.create(spot);
        
        
    }
    /**
     * <code>updateReturnSpot</code>
     *
     * @param request
     * @since   2014年5月26日    guokemenng
     */
    public void updateReturnSpot(HttpServletRequest request){
        String returnSpotId = request.getParameter("returnSpotId");
        String returnType = request.getParameter("returnType");
        String returnAmount = request.getParameter("returnAmount");
        String remark = request.getParameter("remark");
        
        SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
        ReturnSpotModel spot = this.get(Long.parseLong(returnSpotId));
        Float f = null;
        
        ReturnSpotLogModel logModel = new ReturnSpotLogModel();
        logModel.setSpotModel(spot);
        logModel.setRemark(remark);
        
        if(returnType.equals(SupplierConstants.RETURNTYPE)){
            f = spot.getReturnAmount() + Float.parseFloat(returnAmount);
            logModel.setAmount(Float.parseFloat(returnAmount));
        } else {
            f = spot.getReturnAmount() - Float.parseFloat(returnAmount);
            logModel.setBanlance(Float.parseFloat(returnAmount));
        }
        spot.setReturnAmount(f);
        spot.setCreator(systemUser.getStaffName());
        this.update(spot);
        
        logModel.setOperaotor(systemUser.getStaffName());
        logModel.setOperatorTime(new Date());
        spotLogDao.save(logModel);
    }
    
    /**
     * <code>delReturnSpot</code>
     *
     * @param id
     * @since   2014年5月26日    guokemenng
     */
    public void delReturnSpot(String ids){
        String[] idss = ids.split(",");
        for(String id : idss){
            SupplierInfoModel info = infoDao.get(Long.parseLong(id));
            info.setEnableReturnSpot((short)2);
            Iterator<ReturnSpotModel> it = info.getSpotModels().iterator();
            while(it.hasNext()){
                ReturnSpotModel spot = it.next();
                this.getDao().delete(spot);
            }
            info.setSpotModels(null);
            infoDao.update(info);
        }
    }
    
    /**
     * <code>detail</code>
     *
     * @param id
     * @return
     * @since   2014年5月26日    guokemenng
     */
    public ReturnSpotModel detail(String id){
        SupplierInfoModel info = infoDao.get(Long.parseLong(id));
        Iterator<ReturnSpotModel> it = info.getSpotModels().iterator();
        ReturnSpotModel spot = null;
        while(it.hasNext()){
            spot = it.next();
        }
        return spot;
    }
    
}