package com.pratikshat.exicom.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.Transient;

import com.pratikshat.exicom.interceptor.ObjectAuditListener;

import org.apache.commons.lang3.SerializationUtils;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners({AuditingEntityListener.class,ObjectAuditListener.class})
public class BtCustomerInventoryIn extends Auditable<EmployeeDetails> implements Serializable,OldPresist<BtCustomerInventoryIn>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String manualId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    Location inLocation;

    @ManyToOne(fetch = FetchType.LAZY)
    BtRMADetails rma;

    String assetsSerialNumber;

    String productPartcode;

    Boolean returnedToCustomer;

    Date inDate;

    @ManyToOne(fetch=FetchType.LAZY)
    BtProducts product;

    String accountSapNumber;
    String accountName;

    @ManyToOne(fetch = FetchType.LAZY)
    EmployeeDetails owner;

    Integer stateCode;

    String circleName;
    String loatNumber;
    String trackingNumber;


    String docketNumber;
    String docketDate;
    String challanNumber;
    
    String receiveDate;

    @Column(columnDefinition = "TEXT")
    String remarks;

    //FINAL STATE CODE WHILE RETURNING

    Integer finalStateCode;

    //BRC NUMBER APPENDED BY SEFALI ARORA ON 28/10/2021

    String brcNumber;

    //AS PER NEW REQUIRMENT 
    String customerInventoryStage;
    String customerInventoryStatus;
    String contactName;
    String contactNumber;
    String customerLRNumber;
    String customerCourierPartnarName;
    String customerPlant;
    String receiveChallanDate;
    String packingCondation;
    String physicalDamage;
    
    Boolean serviceOrderDone;

    Boolean serviceOrderReturn;
    
    String caseWarranty;
    String dynamicWarranty;
    
    Boolean inStock;

    //QC APPROVAL +1 DATE
    Date dispatchInitDate;
    Date brcCaseCloseDate;
    
    //DUMMAY THING
    String dummayWarehouse;
    String dummayLocation;

    @ManyToOne(fetch = FetchType.LAZY)
    BtAssets assets;
    

    public Date getDispatchInitDate() {
        return dispatchInitDate;
    }

    public void setDispatchInitDate(Date dispatchInitDate) {
        this.dispatchInitDate = dispatchInitDate;
    }

    public Date getBrcCaseCloseDate() {
        return brcCaseCloseDate;
    }

    public BtAssets getAssets(){
        return this.assets;
    }
    public void setAssets(BtAssets assets){
        this.assets=assets;
    }

    public void setBrcCaseCloseDate(Date brcCaseCloseDate) {
        this.brcCaseCloseDate = brcCaseCloseDate;
    }

    public BtCustomerInventoryIn() {
    }

    public BtCustomerInventoryIn(Long id) {
        this.id = id;
    }

    

    public String getDummayWarehouse() {
        return dummayWarehouse;
    }

    public void setDummayWarehouse(String dummayWarehouse) {
        this.dummayWarehouse = dummayWarehouse;
    }

    public String getDummayLocation() {
        return dummayLocation;
    }

    public void setDummayLocation(String dummayLocation) {
        this.dummayLocation = dummayLocation;
    }

    public Boolean getInStock() {
        return inStock;
    }

    public void setInStock(Boolean inStock) {
        this.inStock = inStock;
    }

    public String getCaseWarranty() {
        return caseWarranty;
    }

    public void setCaseWarranty(String caseWarranty) {
        this.caseWarranty = caseWarranty;
    }

    public String getDynamicWarranty() {
        return dynamicWarranty;
    }

    public void setDynamicWarranty(String dynamicWarranty) {
        this.dynamicWarranty = dynamicWarranty;
    }

    public Boolean getServiceOrderDone() {
        return serviceOrderDone;
    }

    public void setServiceOrderDone(Boolean serviceOrderDone) {
        this.serviceOrderDone = serviceOrderDone;
    }

    public Boolean getServiceOrderReturn() {
        return serviceOrderReturn;
    }

    public void setServiceOrderReturn(Boolean serviceOrderReturn) {
        this.serviceOrderReturn = serviceOrderReturn;
    }

    public String getCustomerInventoryStage() {
        return customerInventoryStage;
    }

    public void setCustomerInventoryStage(String customerInventoryStage) {
        this.customerInventoryStage = customerInventoryStage;
    }

    public String getCustomerInventoryStatus() {
        return customerInventoryStatus;
    }

    public void setCustomerInventoryStatus(String customerInventoryStatus) {
        this.customerInventoryStatus = customerInventoryStatus;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getCustomerLRNumber() {
        return customerLRNumber;
    }

    public void setCustomerLRNumber(String customerLRNumber) {
        this.customerLRNumber = customerLRNumber;
    }

    public String getCustomerCourierPartnarName() {
        return customerCourierPartnarName;
    }

    public void setCustomerCourierPartnarName(String customerCourierPartnarName) {
        this.customerCourierPartnarName = customerCourierPartnarName;
    }

    public String getCustomerPlant() {
        return customerPlant;
    }

    public void setCustomerPlant(String customerPlant) {
        this.customerPlant = customerPlant;
    }

    public String getReceiveChallanDate() {
        return receiveChallanDate;
    }

    public void setReceiveChallanDate(String receiveChallanDate) {
        this.receiveChallanDate = receiveChallanDate;
    }

    public String getPackingCondation() {
        return packingCondation;
    }

    public void setPackingCondation(String packingCondation) {
        this.packingCondation = packingCondation;
    }

    public String getPhysicalDamage() {
        return physicalDamage;
    }

    public void setPhysicalDamage(String physicalDamage) {
        this.physicalDamage = physicalDamage;
    }

    public String getBrcNumber() {
        return brcNumber;
    }

    public void setBrcNumber(String brcNumber) {
        this.brcNumber = brcNumber;
    }

    public String getDocketNumber() {
        return docketNumber;
    }

    public void setDocketNumber(String docketNumber) {
        this.docketNumber = docketNumber;
    }

    public String getDocketDate() {
        return docketDate;
    }

    public void setDocketDate(String docketDate) {
        this.docketDate = docketDate;
    }

    public String getChallanNumber() {
        return challanNumber;
    }

    public void setChallanNumber(String challanNumber) {
        this.challanNumber = challanNumber;
    }

    public String getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(String receiveDate) {
        this.receiveDate = receiveDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Integer getFinalStateCode() {
        return finalStateCode;
    }

    public void setFinalStateCode(Integer finalStateCode) {
        this.finalStateCode = finalStateCode;
    }

    public String getCircleName() {
        return circleName;
    }

    public void setCircleName(String circleName) {
        this.circleName = circleName;
    }

    public String getLoatNumber() {
        return loatNumber;
    }

    public void setLoatNumber(String loatNumber) {
        this.loatNumber = loatNumber;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public String getAccountSapNumber() {
        return accountSapNumber;
    }

    public void setAccountSapNumber(String accountSapNumber) {
        this.accountSapNumber = accountSapNumber;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public Integer getStateCode() {
        return stateCode;
    }

    public void setStateCode(Integer stateCode) {
        this.stateCode = stateCode;
    }

    public BtProducts getProduct() {
        return product;
    }

    public void setProduct(BtProducts product) {
        this.product = product;
    }

    public Location getInLocation() {
        return inLocation;
    }

    public void setInLocation(Location inLocation) {
        this.inLocation = inLocation;
    }

    public BtRMADetails getRma() {
        return rma;
    }

    public void setRma(BtRMADetails rma) {
        this.rma = rma;
    }

    public String getAssetsSerialNumber() {
        return assetsSerialNumber;
    }

    public void setAssetsSerialNumber(String assetsSerialNumber) {
        this.assetsSerialNumber = assetsSerialNumber;
    }

    public String getProductPartcode() {
        return productPartcode;
    }

    public void setProductPartcode(String productPartcode) {
        this.productPartcode = productPartcode;
    }

    public Boolean getReturnedToCustomer() {
        return returnedToCustomer;
    }

    public void setReturnedToCustomer(Boolean returnedToCustomer) {
        this.returnedToCustomer = returnedToCustomer;
    }

    public Date getInDate() {
        return inDate;
    }

    public void setInDate(Date inDate) {
        this.inDate = inDate;
    }

    @Override
    @PostLoad
    public void old() {

        old=(BtCustomerInventoryIn)SerializationUtils.clone(this);
    }

    @Transient transient BtCustomerInventoryIn old;

    @Override
    public BtCustomerInventoryIn gOld() {
        
        return this.old;
    }

    @Override
    public Long getId() {
        
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id=id;
        
    }

    @Override
    public EmployeeDetails getOwner() {
        return this.owner;
    }

    @Override
    public void setOwner(EmployeeDetails u) {
        this.owner=u;
        
    }

    @Override
    public String getManualId() {
        return this.manualId;
        
    }

    @Override
    public void setManualId(String manualId) {
        this.manualId=manualId;
        
    }

    public String toString(){
        return ""+ this.id;
    }
    
}
