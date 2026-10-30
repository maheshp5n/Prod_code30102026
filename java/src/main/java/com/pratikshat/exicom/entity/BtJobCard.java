package com.pratikshat.exicom.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.pratikshat.exicom.interceptor.ObjectAuditListener;

import org.apache.commons.lang.SerializationUtils;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners({AuditingEntityListener.class,ObjectAuditListener.class})
public class BtJobCard extends Auditable<EmployeeDetails> implements Serializable,OldPresist<BtJobCard>{
    
    //ID AS SERVICE ORDER ITEM ID MAKING ONE TO ONE WITHOUT PROPER ANNOTATION
    
    @Id
    Long id;

    String manualId;

    @ManyToOne(fetch = FetchType.LAZY)
    EmployeeDetails owner;

    @ManyToOne(fetch = FetchType.LAZY)
    BtServiceOrderItem serviceOrderItem;

    Boolean repared;

    Boolean qcDone;

    Boolean returnedToSource;

    Date assignedDate;

    Date returnDate;

    Date repairDate;

    Integer repairProductState;

    Integer previousState;

    Boolean assignedForQc;

    //ADDON

    String batteryVoltageBef;
    String batteryVoltageAft;
    String batteryAhAft;
    String cellVmax;
    String cellVmin;
    String correctiveAction;
    String engResolutionMethod;
    String engRemarks;
    String btBalanceAt;
    String cellBledge;
    String matSorteg;
    String berRemarks;
    String observation1;
    String observation2;
    String observation3;
    String observation4;



    // public BtJobCard getOld() {
    //     return old;
    // }
    // public void setOld(BtJobCard old) {
    //     this.old = old;
    // }
    public String getBerRemarks() {
        return berRemarks;
    }
    public void setBerRemarks(String berRemarks) {
        this.berRemarks = berRemarks;
    }
    public String getBatteryVoltageBef() {
        return batteryVoltageBef;
    }
    public void setBatteryVoltageBef(String batteryVoltageBef) {
        this.batteryVoltageBef = batteryVoltageBef;
    }
    public String getBatteryVoltageAft() {
        return batteryVoltageAft;
    }
    public void setBatteryVoltageAft(String batteryVoltageAft) {
        this.batteryVoltageAft = batteryVoltageAft;
    }
    public String getBatteryAhAft() {
        return batteryAhAft;
    }
    public void setBatteryAhAft(String batteryAhAft) {
        this.batteryAhAft = batteryAhAft;
    }
    public String getCellVmax() {
        return cellVmax;
    }
    public void setCellVmax(String cellVmax) {
        this.cellVmax = cellVmax;
    }
    public String getCellVmin() {
        return cellVmin;
    }
    public void setCellVmin(String cellVmin) {
        this.cellVmin = cellVmin;
    }
    public String getCorrectiveAction() {
        return correctiveAction;
    }
    public void setCorrectiveAction(String correctiveAction) {
        this.correctiveAction = correctiveAction;
    }
    public String getEngResolutionMethod() {
        return engResolutionMethod;
    }
    public void setEngResolutionMethod(String engResolutionMethod) {
        this.engResolutionMethod = engResolutionMethod;
    }
    public String getEngRemarks() {
        return engRemarks;
    }
    public void setEngRemarks(String engRemarks) {
        this.engRemarks = engRemarks;
    }
    public String getBtBalanceAt() {
        return btBalanceAt;
    }
    public void setBtBalanceAt(String btBalanceAt) {
        this.btBalanceAt = btBalanceAt;
    }
    public String getCellBledge() {
        return cellBledge;
    }
    public void setCellBledge(String cellBledge) {
        this.cellBledge = cellBledge;
    }
    public String getMatSorteg() {
        return matSorteg;
    }
    public void setMatSorteg(String matSorteg) {
        this.matSorteg = matSorteg;
    }
    //observation
    public String getObservation1() {
		return observation1;
	}
	public void setObservation1(String observation1) {
		this.observation1 = observation1;
	}
    
    public String getObservation2() {
		return observation2;
	}
	public void setObservation2(String observation2) {
		this.observation2 = observation2;
	}
	public String getObservation3() {
		return observation3;
	}
	public void setObservation3(String observation3) {
		this.observation3 = observation3;
	}
	public String getObservation4() {
		System.out.println(observation4);
		return observation4;
	}
	public void setObservation4(String observation4) {
		this.observation4 = observation4;
		System.out.println(observation4);
	}

	


	@ManyToOne(fetch = FetchType.LAZY)
    EmployeeDetails assignedTo;

    

    
	public BtJobCard(){}
    public BtJobCard(Long id){
        this.id=id;
    }

    

    public Boolean getAssignedForQc() {
        return assignedForQc;
    }
    public void setAssignedForQc(Boolean assignedForQc) {
        this.assignedForQc = assignedForQc;
    }
    public Integer getPreviousState() {
        return previousState;
    }
    public void setPreviousState(Integer previousState) {
        this.previousState = previousState;
    }
    public BtServiceOrderItem getServiceOrderItem() {
        return serviceOrderItem;
    }
    public void setServiceOrderItem(BtServiceOrderItem serviceOrderItem) {
        this.serviceOrderItem = serviceOrderItem;
    }
    public Boolean getRepared() {
        return repared;
    }
    public void setRepared(Boolean repared) {
        this.repared = repared;
    }
    public Boolean getQcDone() {
        return qcDone;
    }
    public void setQcDone(Boolean qcDone) {
        this.qcDone = qcDone;
    }
    public Boolean getReturnedToSource() {
        return returnedToSource;
    }
    public void setReturnedToSource(Boolean returnedToSource) {
        this.returnedToSource = returnedToSource;
    }
    public Date getAssignedDate() {
        return assignedDate;
    }
    public void setAssignedDate(Date assignedDate) {
        this.assignedDate = assignedDate;
    }
    public Date getReturnDate() {
        return returnDate;
    }
    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }
    public Date getRepairDate() {
        return repairDate;
    }
    public void setRepairDate(Date repairDate) {
        this.repairDate = repairDate;
    }
    public Integer getRepairProductState() {
        return repairProductState;
    }
    public void setRepairProductState(Integer repairProductState) {
        this.repairProductState = repairProductState;
    }
    public EmployeeDetails getAssignedTo() {
        return assignedTo;
    }
    public void setAssignedTo(EmployeeDetails assignedTo) {
        this.assignedTo = assignedTo;
    }



    @Transient transient BtJobCard old;

    @Override
    public void old() {
        this.old=(BtJobCard)SerializationUtils.clone(this);
    }

    @Override
    public BtJobCard gOld() {
        
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
