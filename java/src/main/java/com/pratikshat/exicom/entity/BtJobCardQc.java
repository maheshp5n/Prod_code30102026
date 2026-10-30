package com.pratikshat.exicom.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.pratikshat.exicom.interceptor.ObjectAuditListener;

import org.apache.commons.lang3.SerializationUtils;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners({AuditingEntityListener.class,ObjectAuditListener.class})
public class BtJobCardQc extends Auditable<EmployeeDetails> implements Serializable,OldPresist<BtJobCardQc>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String manualId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    BtJobCard jobCard;

    @ManyToOne(fetch = FetchType.LAZY)
    EmployeeDetails assignedTo;

    Date assignedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    EmployeeDetails owner;

    String pidDate;

    String physicalCheck;
    String physicalRemarks;
    String electricalCheck;
    String electricalRemarks;
    String faultObservedDuringPid;
    String correctiveActionTaken;
    String sohUpdation;
    String softwareUpdation;
    String parametersCheck;
    String voltageGapLt150mv;
    String vMax;
    String vMin;
    String finalApproval;
    //observations
    String qcobservation1;
    String qcobservation2;
    
    

    public BtJobCardQc() {
    }

    

    public String getPidDate() {
        return pidDate;
    }



    public void setPidDate(String pidDate) {
        this.pidDate = pidDate;
    }



    // public BtJobCardQc getOld() {
    //     return old;
    // }



    // public void setOld(BtJobCardQc old) {
    //     this.old = old;
    // }



    public BtJobCardQc(Long id) {
        this.id = id;
    }



    public BtJobCard getJobCard() {
        return jobCard;
    }



    public void setJobCard(BtJobCard jobCard) {
        this.jobCard = jobCard;
    }



    public EmployeeDetails getAssignedTo() {
        return assignedTo;
    }



    public void setAssignedTo(EmployeeDetails assignedTo) {
        this.assignedTo = assignedTo;
    }



    public Date getAssignedDate() {
        return assignedDate;
    }



    public void setAssignedDate(Date assignedDate) {
        this.assignedDate = assignedDate;
    }



    public String getPhysicalCheck() {
        return physicalCheck;
    }



    public void setPhysicalCheck(String physicalCheck) {
        this.physicalCheck = physicalCheck;
    }



    public String getPhysicalRemarks() {
        return physicalRemarks;
    }



    public void setPhysicalRemarks(String physicalRemarks) {
        this.physicalRemarks = physicalRemarks;
    }



    public String getElectricalCheck() {
        return electricalCheck;
    }



    public void setElectricalCheck(String electricalCheck) {
        this.electricalCheck = electricalCheck;
    }



    public String getElectricalRemarks() {
        return electricalRemarks;
    }



    public void setElectricalRemarks(String electricalRemarks) {
        this.electricalRemarks = electricalRemarks;
    }



    public String getFaultObservedDuringPid() {
        return faultObservedDuringPid;
    }



    public void setFaultObservedDuringPid(String faultObservedDuringPid) {
        this.faultObservedDuringPid = faultObservedDuringPid;
    }



    public String getCorrectiveActionTaken() {
        return correctiveActionTaken;
    }



    public void setCorrectiveActionTaken(String correctiveActionTaken) {
        this.correctiveActionTaken = correctiveActionTaken;
    }



    public String getSohUpdation() {
        return sohUpdation;
    }



    public void setSohUpdation(String sohUpdation) {
        this.sohUpdation = sohUpdation;
    }



    public String getSoftwareUpdation() {
        return softwareUpdation;
    }



    public void setSoftwareUpdation(String softwareUpdation) {
        this.softwareUpdation = softwareUpdation;
    }



    public String getParametersCheck() {
        return parametersCheck;
    }



    public void setParametersCheck(String parametersCheck) {
        this.parametersCheck = parametersCheck;
    }



    public String getVoltageGapLt150mv() {
        return voltageGapLt150mv;
    }



    public void setVoltageGapLt150mv(String voltageGapLt150mv) {
        this.voltageGapLt150mv = voltageGapLt150mv;
    }



    public String getvMax() {
        return vMax;
    }



    public void setvMax(String vMax) {
        this.vMax = vMax;
    }



    public String getvMin() {
        return vMin;
    }



    public void setvMin(String vMin) {
        this.vMin = vMin;
    }

    


    public String getQcobservation1() {
		return qcobservation1;
	}



	public void setQcobservation1(String qcobservation1) {
		this.qcobservation1 = qcobservation1;
	}



	public String getQcobservation2() {
		return qcobservation2;
	}



	public void setQcobservation2(String qcobservation2) {
		this.qcobservation2 = qcobservation2;
	}



	public String getFinalApproval() {
        return finalApproval;
    }



    public void setFinalApproval(String finalApproval) {
        this.finalApproval = finalApproval;
    }

    @Transient transient BtJobCardQc old;

    @Override
    public void old() {
       this.old=(BtJobCardQc)SerializationUtils.clone(this);
    }



    @Override
    public BtJobCardQc gOld() {
        
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
    
}
