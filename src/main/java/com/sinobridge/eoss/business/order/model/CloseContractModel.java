package com.sinobridge.eoss.business.order.model;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.sinobridge.eoss.sales.contract.model.SalesContractModel;

/**
 * <p>
 * Description: 关闭合同中间表
 * </p>


 * <p>
 * History: 
 *
 * Date                          Author      Version     Description
 * ---------------------------------------------------------------------------------
 * 2014年5月5日 上午10:42:25          wangya       1.0         To create
 * </p>
 * @param <createDate>
 *
 * @since 
 * @see     
 */
@Entity
@Table(name = "order_sales_close")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "salesContract" })
public class CloseContractModel implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private SalesContractModel salesContract;
    //1:待关闭，2：已关闭,0:驳回
    private String statuse;
    //成本预估
    private String cost;

    private String contractCreator;
    private int contractType;
    private String contractShortName;
    private BigDecimal contractAmount;
    
    private Long salesContractId;

    /**
     * @return the id
     */
    @Id
    @Column(name = "id", nullable = false, unique = true, length = 20)
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "SalesContractId")
    public SalesContractModel getSalesContract() {
        return salesContract;
    }

    public void setSalesContract(SalesContractModel salesContract) {
        this.salesContract = salesContract;
    }

    @Column(name = "Statuse", length = 8)
    public String getStatuse() {
        return statuse;
    }

    public void setStatuse(String statuse) {
        this.statuse = statuse;
    }

    @Column(name = "ContractType", length = 4)
    public int getContractType() {
        return contractType;
    }

    public void setContractType(int contractType) {
        this.contractType = contractType;
    }

    @Column(name = "ContractAmount", precision = 11, scale = 2)
    public BigDecimal getContractAmount() {
        return contractAmount;
    }

    public void setContractAmount(BigDecimal contractAmount) {
        this.contractAmount = contractAmount;
    }

    @Column(name = "ContractShortName", length = 64)
    public String getContractShortName() {
        return contractShortName;
    }

    public void setContractShortName(String contractShortName) {
        this.contractShortName = contractShortName;
    }

    @Column(name = "ContractCreator", length = 64)
    public String getContractCreator() {
        return contractCreator;
    }

    public void setContractCreator(String contractCreator) {
        this.contractCreator = contractCreator;
    }

    @Column(name = "Cost", length = 64)
    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    @Transient
    public Long getSalesContractId() {
        if(getSalesContract() != null){
            salesContractId = getSalesContract().getId();
        }
        return salesContractId;
    }

    public void setSalesContractId(Long salesContractId) {
        this.salesContractId = salesContractId;
    }

    
}
