package com.sinobridge.eoss.business.interPurchas.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.sinobridge.eoss.business.order.model.BusinessOrderModel;

/**
 * <p>
 * Description: 内部采购的实体类
 * </p>


 * <p>
 * History: 
 *
 * Date                          Author      Version     Description
 * ---------------------------------------------------------------------------------
 * 2014年7月2日 上午10:42:25          wangya       1.0         To create
 * </p>
 * @param <createDate>
 *
 * @since 
 * @see     
 */
@Entity
@Table(name = "business_inter_purchas")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "interPurchasProduct", "busienssOrder" })
public class InterPurchasModel implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    public static final String CREATORID = "creatorId";
    public static final String CREATOR = "creator";
    public static final String EXPECTEDTIME = "expectedDeliveryTime";
    public static final String PURCHASSTATUS = "purchasStatus";
    public static final String CREATETIME = "createTime";
    public static final String INTERORDERSTATUS = "interOrderStatus";
    public static final String ID = "id";

    private long[] contractProductIds;
    private long[] productTypes;
    private String[] productTypeNames;
    private long[] productPartners;
    private String[] productPartnerNames;
    private long[] productNos;
    //产品型号
    private String[] productNames;
    private int[] quantitys;

    private String creator;
    private String creatorId;
    private String purchasName;
    private String purchasCode;
    private Date expectedDeliveryTime;
    //内采状态1cg,2sp,3end
    private String purchasStatus;
    //内采订单状态
    private String interOrderStatus;
    private String remark;
    private Date createTime;
    private Long id;
    /*//订单处理人UserName
    private String orderProcessor;*/
    //工单ID
    private Long processInstanceId;
    private Set<InterPurchasProductModel> interPurchasProduct = new HashSet<InterPurchasProductModel>(0);
    private Set<BusinessOrderModel> busienssOrder = new HashSet<BusinessOrderModel>(0);

    public InterPurchasModel() {
    }

    @Id
    @Column(name = "id", nullable = false, unique = true, length = 20)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "Creator", length = 32)
    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    @Column(name = "PurchasName", length = 64)
    public String getPurchasName() {
        return purchasName;
    }

    public void setPurchasName(String purchasName) {
        this.purchasName = purchasName;
    }

    @Column(name = "PurchasCode", length = 32)
    public String getPurchasCode() {
        return purchasCode;
    }

    public void setPurchasCode(String purchasCode) {
        this.purchasCode = purchasCode;
    }

    @Column(name = "PurchasStatus", length = 8)
    public String getPurchasStatus() {
        return purchasStatus;
    }

    public void setPurchasStatus(String purchasStatus) {
        this.purchasStatus = purchasStatus;
    }

    @Column(name = "Remark", length = 128)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "interPurchas")
    public Set<InterPurchasProductModel> getInterPurchasProduct() {
        return interPurchasProduct;
    }

    public void setInterPurchasProduct(Set<InterPurchasProductModel> interPurchasProduct) {
        this.interPurchasProduct = interPurchasProduct;
    }

    /**
     * @return the productTypes
     */
    @Transient
    public long[] getProductTypes() {
        return productTypes;
    }

    /**
     * @return the contractProductIds
     */
    @Transient
    public long[] getContractProductIds() {
        return contractProductIds;
    }

    /**
     * @param contractProductIds the contractProductIds to set
     */
    public void setContractProductIds(long[] contractProductIds) {
        this.contractProductIds = contractProductIds;
    }

    /**
     * @param productTypes the productTypes to set
     */
    public void setProductTypes(long[] productTypes) {
        this.productTypes = productTypes;
    }

    /**
     * @return the productTypeNames
     */
    @Transient
    public String[] getProductTypeNames() {
        return productTypeNames;
    }

    /**
     * @param productTypeNames the productTypeNames to set
     */
    public void setProductTypeNames(String[] productTypeNames) {
        this.productTypeNames = productTypeNames;
    }

    /**
     * @return the productPartners
     */
    @Transient
    public long[] getProductPartners() {
        return productPartners;
    }

    /**
     * @param productPartners the productPartners to set
     */
    public void setProductPartners(long[] productPartners) {
        this.productPartners = productPartners;
    }

    /**
     * @return the productPartnerNames
     */
    @Transient
    public String[] getProductPartnerNames() {
        return productPartnerNames;
    }

    /**
     * @param productPartnerNames the productPartnerNames to set
     */
    public void setProductPartnerNames(String[] productPartnerNames) {
        this.productPartnerNames = productPartnerNames;
    }

    /**
     * @return the productNos
     */
    @Transient
    public long[] getProductNos() {
        return productNos;
    }

    /**
     * @param productNos the productNos to set
     */
    public void setProductNos(long[] productNos) {
        this.productNos = productNos;
    }

    /**
     * @return the productNames
     */
    @Transient
    public String[] getProductNames() {
        return productNames;
    }

    /**
     * @param productNames the productNames to set
     */
    public void setProductNames(String[] productNames) {
        this.productNames = productNames;
    }

    /**
     * @return the quantitys
     */
    @Transient
    public int[] getQuantitys() {
        return quantitys;
    }

    /**
     * @param quantitys the quantitys to set
     */
    public void setQuantitys(int[] quantitys) {
        this.quantitys = quantitys;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "createTime")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return the processInstanceId
     */
    @Column(name = "ProcessInstanceId", length = 11)
    public Long getProcessInstanceId() {
        return processInstanceId;
    }

    /**
     * @param processInstanceId the processInstanceId to set
     */
    public void setProcessInstanceId(Long processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "ExpectedDeliveryTime")
    public Date getExpectedDeliveryTime() {
        return expectedDeliveryTime;
    }

    public void setExpectedDeliveryTime(Date expectedDeliveryTime) {
        this.expectedDeliveryTime = expectedDeliveryTime;
    }

    @Column(name = "CreatorId", length = 32)
    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    /*    *//**
             * @return the orderProcessor
             */
    /*
    @Column(name = "OrderProcessor", length = 32)
    public String getOrderProcessor() {
     return orderProcessor;
    }

    *//**
      * @param orderProcessorId the orderProcessor to set
      */
    /*
    public void setOrderProcessor(String orderProcessor) {
     this.orderProcessor = orderProcessor;
    }*/

    @ManyToMany(mappedBy = "interPurchasModel", cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
    public Set<BusinessOrderModel> getBusienssOrder() {
        return busienssOrder;
    }

    public void setBusienssOrder(Set<BusinessOrderModel> busienssOrder) {
        this.busienssOrder = busienssOrder;
    }

    @Column(name = "InterOrderStatus", length = 32)
    public String getInterOrderStatus() {
        return interOrderStatus;
    }

    public void setInterOrderStatus(String interOrderStatus) {
        this.interOrderStatus = interOrderStatus;
    }

}
