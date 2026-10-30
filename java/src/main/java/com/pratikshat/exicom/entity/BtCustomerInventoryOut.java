package com.pratikshat.exicom.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.PostLoad;
import javax.persistence.Transient;

import com.pratikshat.exicom.interceptor.ObjectAuditListener;

import org.apache.commons.lang3.SerializationUtils;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners({AuditingEntityListener.class,ObjectAuditListener.class})
public class BtCustomerInventoryOut extends Auditable<EmployeeDetails> implements Serializable,OldPresist<BtCustomerInventoryOut>{
    @Id
    @Column(name = "id")
    Long id;

    String manualId;

    Integer outState;

    String trackingNumber;

    String loatNumber;

    Date outDate;

    //DATE BY EXCEL SHEET ACTUAL DATE OF OUT, AS BULK UPLOAD MAY DONE AT THE END OF MONTH   
    String actualOutDate;

    String outLrDocNumber;
    String outCourierPartner;
    String outdeleveryChallanNumber;
    String outGrnNumber;
    String outGrnDate;
    
    Date sysGrnDate;

    public BtCustomerInventoryOut(){}
    public BtCustomerInventoryOut(Long id){
        this.id=id;
    }

    

    public String getOutLrDocNumber() {
        return outLrDocNumber;
    }
    public void setOutLrDocNumber(String outLrDocNumber) {
        this.outLrDocNumber = outLrDocNumber;
    }
    public String getOutCourierPartner() {
        return outCourierPartner;
    }
    public void setOutCourierPartner(String outCourierPartner) {
        this.outCourierPartner = outCourierPartner;
    }
    public String getOutdeleveryChallanNumber() {
        return outdeleveryChallanNumber;
    }
    public void setOutdeleveryChallanNumber(String outdeleveryChallanNumber) {
        this.outdeleveryChallanNumber = outdeleveryChallanNumber;
    }
    public String getOutGrnNumber() {
        return outGrnNumber;
    }
    public void setOutGrnNumber(String outGrnNumber) {
        this.outGrnNumber = outGrnNumber;
    }
    public String getOutGrnDate() {
        return outGrnDate;
    }
    public void setOutGrnDate(String outGrnDate) {
        this.outGrnDate = outGrnDate;
    }
    public Integer getOutState() {
        return outState;
    }



    public void setOutState(Integer outState) {
        this.outState = outState;
    }



    public String getTrackingNumber() {
        return trackingNumber;
    }



    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }



    public String getLoatNumber() {
        return loatNumber;
    }



    public void setLoatNumber(String loatNumber) {
        this.loatNumber = loatNumber;
    }



    public Date getOutDate() {
        return outDate;
    }



    public void setOutDate(Date outDate) {
        this.outDate = outDate;
    }



    public String getActualOutDate() {
        return actualOutDate;
    }



    public void setActualOutDate(String actualOutDate) {
        this.actualOutDate = actualOutDate;
    }
    
    



  
	public Date getSysGrnDate() {
		return sysGrnDate;
	}
	public void setSysGrnDate(Date sysGrnDate) {
		this.sysGrnDate = sysGrnDate;
	}
	public BtCustomerInventoryIn getCustomerInventoryIn() {
        return customerInventoryIn;
    }



    public void setCustomerInventoryIn(BtCustomerInventoryIn customerInventoryIn) {
        this.customerInventoryIn = customerInventoryIn;
    }

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    BtCustomerInventoryIn customerInventoryIn;

    @ManyToOne(fetch = FetchType.LAZY)
    EmployeeDetails owner;


    @Transient transient BtCustomerInventoryOut old;

    @Override
    @PostLoad
    public void old() {
        old=SerializationUtils.clone(this);
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

    @Override
    public BtCustomerInventoryOut gOld() {
        
        return this.old;
    }

    @Override
    public Long getId() {
        
        return this.id;
    }

    public String toString(){
        return ""+id;
    }
    
}
