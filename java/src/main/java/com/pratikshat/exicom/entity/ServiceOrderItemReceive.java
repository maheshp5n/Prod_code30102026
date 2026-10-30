package com.pratikshat.exicom.entity;

import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.OldPresist;
import com.pratikshat.exicom.entity.ServiceOrderItem;
import com.pratikshat.exicom.entity.ServiceOrderItemReceive;
import com.pratikshat.exicom.entity.ServiceRecource;
import com.pratikshat.exicom.interceptor.ObjectAuditListener;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PostLoad;
import javax.persistence.Transient;
import org.apache.commons.lang.SerializationUtils;

@Entity
@EntityListeners({ObjectAuditListener.class})
public class ServiceOrderItemReceive implements Serializable, OldPresist<ServiceOrderItemReceive> {
  @Id
  @OneToOne(fetch = FetchType.LAZY)
  ServiceOrderItem item;
  
  Date receiveDate;
  
  @ManyToOne(fetch = FetchType.LAZY)
  EmployeeDetails createdBy;
  
  @ManyToOne(fetch = FetchType.LAZY)
  EmployeeDetails owner;
  
  @ManyToOne(fetch = FetchType.LAZY)
  EmployeeDetails lastModifiedBy;
  
  Date lastModifiedDate;
  
  @ManyToOne(fetch = FetchType.LAZY)
  ServiceRecource assignedTo;
  
  boolean returned;
  
  @Transient
  transient ServiceOrderItemReceive old;
  
  public ServiceOrderItemReceive() {}
  
  public ServiceOrderItemReceive(ServiceOrderItem item) {
    this.item = item;
  }
  
  public ServiceOrderItem getItem() {
    return this.item;
  }
  
  public void setItem(ServiceOrderItem item) {
    this.item = item;
  }
  
  public Date getReceiveDate() {
    return this.receiveDate;
  }
  
  public void setReceiveDate(Date receiveDate) {
    this.receiveDate = receiveDate;
  }
  
  public EmployeeDetails getCreatedBy() {
    return this.createdBy;
  }
  
  public void setCreatedBy(EmployeeDetails createdBy) {
    this.createdBy = createdBy;
  }
  
  public EmployeeDetails getOwner() {
    return this.owner;
  }
  
  public void setOwner(EmployeeDetails owner) {
    this.owner = owner;
  }
  
  public EmployeeDetails getLastModifiedBy() {
    return this.lastModifiedBy;
  }
  
  public void setLastModifiedBy(EmployeeDetails lastModifiedBy) {
    this.lastModifiedBy = lastModifiedBy;
  }
  
  public Date getLastModifiedDate() {
    return this.lastModifiedDate;
  }
  
  public void setLastModifiedDate(Date lastModifiedDate) {
    this.lastModifiedDate = lastModifiedDate;
  }
  
  public ServiceRecource getAssignedTo() {
    return this.assignedTo;
  }
  
  public void setAssignedTo(ServiceRecource assignedTo) {
    this.assignedTo = assignedTo;
  }
  
  public boolean isReturned() {
    return this.returned;
  }
  
  public void setReturned(boolean returned) {
    this.returned = returned;
  }
  
  @PostLoad
  public void old() {
    this.old = (ServiceOrderItemReceive)SerializationUtils.clone(this);
  }
  
  public ServiceOrderItemReceive gOld() {
    return this.old;
  }
  
  public String toString() {
    return "" + this.item;
  }
}
