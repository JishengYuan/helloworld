package com.sinobridge.eoss.business.interPurchas.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.sinobridge.base.core.model.imp.DefaultBaseModel;

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
@Table(name = "business_inter_purchas_product")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "interPurchas"})
public class InterPurchasProductModel extends DefaultBaseModel {
       private static final long serialVersionUID = 1L;
       public static final String INTERPURCHAS = "interPurchas";
       
       private InterPurchasModel interPurchas;
       //厂商名称
       private long productPartner;
       //产品型号
       private String productName;
       //产品数量
       private int quantity;
       //产品类别
       private long productType;
       //产品类型
       private String productTypeName;
       //产品厂商
       private String productPartnerName;
       private long productNo;
       
     @Column(name = "ProductType", length = 20)  
     public long getProductType() {
        return productType;
    }

    public void setProductType(long productType) {
        this.productType = productType;
    }
    
    @Column(name = "ProductTypeName", length = 100)
    public String getProductTypeName() {
        return productTypeName;
    }

    public void setProductTypeName(String productTypeName) {
        this.productTypeName = productTypeName;
    }
    
    @Column(name = "ProductPartnerName", length = 100)
    public String getProductPartnerName() {
        return productPartnerName;
    }

    public void setProductPartnerName(String productPartnerName) {
        this.productPartnerName = productPartnerName;
    }
    
    @Column(name = "ProductNo", length = 20)
    public long getProductNo() {
        return productNo;
    }

    public void setProductNo(long productNo) {
        this.productNo = productNo;
    }

    @Column(name = "ProductPartner", length = 64)
     public long getProductPartner() {
        return productPartner;
    }
     
    public void setProductPartner(long productPartner) {
        this.productPartner = productPartner;
    }
    @Column(name = "ProductName", length = 64)
    public String getProductName() {
        return productName;
    }
    
    public void setProductName(String productName) {
        this.productName = productName;
    }

       @ManyToOne(fetch = FetchType.LAZY)
       @JoinColumn(name = "InterPurchasId")
    public InterPurchasModel getInterPurchas() {
        return interPurchas;
    }

    public void setInterPurchas(InterPurchasModel interPurchas) {
        this.interPurchas = interPurchas;
    }
    @Column(name = "Quantity", length = 8)
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
