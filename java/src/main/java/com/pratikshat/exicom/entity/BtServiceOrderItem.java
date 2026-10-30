package com.pratikshat.exicom.entity;

import java.io.Serializable;

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

import org.apache.commons.lang.SerializationUtils;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners({AuditingEntityListener.class,ObjectAuditListener.class})
public class BtServiceOrderItem extends Auditable<EmployeeDetails> implements Serializable,OldPresist<BtServiceOrderItem>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String manualId;

    @ManyToOne(fetch = FetchType.LAZY)
    BtServiceOrder serviceOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    BtProducts product;

    String serialNumber;

    Boolean customerInventory;

    @ManyToOne(fetch = FetchType.LAZY)
    BtCustomerInventoryIn inCustomerInventory;


    Boolean received;
    Boolean assigned;
    Boolean assignedReturned;
    Boolean returnedToSource;

    //PRODUCT STATUS AT CREATING SERVICE ORDER
    Integer aStatus;

    //PRODUCT STATUS AT RETURNING TO SOURCE LOCATION
    Integer rStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    EmployeeDetails assignedTo;

    @ManyToOne(fetch = FetchType.LAZY)
    EmployeeDetails owner;

    boolean synced;

    @Transient transient BtServiceOrderItem old;

    public BtServiceOrderItem(){}
    public BtServiceOrderItem(Long id){
        this.id=id;
    }

    

    public boolean isSynced() {
        return synced;
    }
    public void setSynced(boolean synced) {
        this.synced = synced;
    }
    public BtServiceOrder getServiceOrder() {
        return serviceOrder;
    }
    public void setServiceOrder(BtServiceOrder serviceOrder) {
        this.serviceOrder = serviceOrder;
    }
    public BtProducts getProduct() {
        return product;
    }
    public void setProduct(BtProducts product) {
        this.product = product;
    }
    public String getSerialNumber() {
        return serialNumber;
    }
    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }
    public Boolean getCustomerInventory() {
        return customerInventory;
    }
    public void setCustomerInventory(Boolean customerInventory) {
        this.customerInventory = customerInventory;
    }
    public BtCustomerInventoryIn getInCustomerInventory() {
        return inCustomerInventory;
    }
    public void setInCustomerInventory(BtCustomerInventoryIn inCustomerInventory) {
        this.inCustomerInventory = inCustomerInventory;
    }
    public Boolean getReceived() {
        return received;
    }
    public void setReceived(Boolean received) {
        this.received = received;
    }
    public Boolean getAssigned() {
        return assigned;
    }
    public void setAssigned(Boolean assigned) {
        this.assigned = assigned;
    }
    public Boolean getAssignedReturned() {
        return assignedReturned;
    }
    public void setAssignedReturned(Boolean assignedReturned) {
        this.assignedReturned = assignedReturned;
    }
    public Boolean getReturnedToSource() {
        return returnedToSource;
    }
    public void setReturnedToSource(Boolean returnedToSource) {
        this.returnedToSource = returnedToSource;
    }
    public Integer getaStatus() {
        return aStatus;
    }
    public void setaStatus(Integer aStatus) {
        this.aStatus = aStatus;
    }
    public Integer getrStatus() {
        return rStatus;
    }
    public void setrStatus(Integer rStatus) {
        this.rStatus = rStatus;
    }
    public EmployeeDetails getAssignedTo() {
        return assignedTo;
    }
    public void setAssignedTo(EmployeeDetails assignedTo) {
        this.assignedTo = assignedTo;
    }
    @PostLoad
    @Override
    public void old() {
        this.old=(BtServiceOrderItem)SerializationUtils.clone(this);
    }

    @Override
    public BtServiceOrderItem gOld() {
        
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
        return ""+id;
    }
}
