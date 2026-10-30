package com.pratikshat.exicom.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.pratikshat.exicom.interceptor.ObjectAuditListener;

@Entity
@EntityListeners({ObjectAuditListener.class,AuditingEntityListener.class})
public class BtSchUseOnlyDetails extends Auditable<EmployeeDetails> implements Serializable,OldPresist<BtSchUseOnlyDetails>{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String sparePartName;
	private int sparePartQuantity;

	private String correctiveActions;
	
	@ManyToOne(fetch = FetchType.LAZY)
	BtServiceAppointment btServiceAppointment;
	
	@ManyToOne
	BtResolutionMethod resolutionMethod;
	
	@ManyToOne
	EmployeeDetails ownDetails;
	
	private String sparePartReplaced;
	
	private String sparePartReplacedFrom;
	
	private String customerRate;                   ////////for site visit charge
	
	private String customerBillingRate;            ////////for customer spare charge
	
	
	public String getSparePartName() {
		return sparePartName;
	}

	public void setSparePartName(String sparePartName) {
		this.sparePartName = sparePartName;
	}

	public int getSparePartQuantity() {
		return sparePartQuantity;
	}

	public void setSparePartQuantity(int sparePartQuantity) {
		this.sparePartQuantity = sparePartQuantity;
	}

	public BtServiceAppointment getBtServiceAppointment() {
		return btServiceAppointment;
	}

	public void setBtServiceAppointment(BtServiceAppointment btServiceAppointment) {
		this.btServiceAppointment = btServiceAppointment;
	}

	public BtResolutionMethod getResolutionMethod() {
		return resolutionMethod;
	}

	public void setResolutionMethod(BtResolutionMethod resolutionMethod) {
		this.resolutionMethod = resolutionMethod;
	}

	public EmployeeDetails getOwnDetails() {
		return ownDetails;
	}

	public void setOwnDetails(EmployeeDetails ownDetails) {
		this.ownDetails = ownDetails;
	}

	public String getSparePartReplacedFrom() {
		return sparePartReplacedFrom;
	}

	public void setSparePartReplacedFrom(String sparePartReplacedFrom) {
		this.sparePartReplacedFrom = sparePartReplacedFrom;
	}

	public String getSparePartReplaced() {
		return sparePartReplaced;
	}

	public void setSparePartReplaced(String sparePartReplaced) {
		this.sparePartReplaced = sparePartReplaced;
	}

	public String getCorrectiveActions() {
		return correctiveActions;
	}

	public void setCorrectiveActions(String correctiveActions) {
		this.correctiveActions = correctiveActions;
	}
	
	public String getCustomerRate() {
		return customerRate;
	}

	public void setCustomerRate(String customerRate) {
		this.customerRate = customerRate;
	}

	public String getCustomerBillingRate() {
		return customerBillingRate;
	}

	public void setCustomerBillingRate(String customerBillingRate) {
		this.customerBillingRate = customerBillingRate;
	}

	@Override
	public void old() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public BtSchUseOnlyDetails gOld() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id=id;
		
	}

	@Override
	public EmployeeDetails getOwner() {
		// TODO Auto-generated method stub
		return ownDetails;
	}

	@Override
	public void setOwner(EmployeeDetails u) {
		this.ownDetails=u;
		
	}

	@Override
	public String getManualId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setManualId(String manualId) {
		// TODO Auto-generated method stub
		
	}

}
