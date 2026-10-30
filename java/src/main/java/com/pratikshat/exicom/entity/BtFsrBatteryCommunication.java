package com.pratikshat.exicom.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.Transient;

import org.apache.commons.lang.SerializationUtils;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.pratikshat.exicom.interceptor.ObjectAuditListener;

@Entity
@EntityListeners({AuditingEntityListener.class,ObjectAuditListener.class})
public class BtFsrBatteryCommunication extends Auditable<EmployeeDetails> implements Serializable,OldPresist<BtFsrBatteryCommunication>{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String manuald;

	private long batterySNo1;
	private long batterySNo2;
	private long batterySNo3;
	private long batterySNo4;
	private long batterySNo5;
	private long batterySNo6;
	private long batterySNo7;
	private long batterySNo8;
	private double batterySoc1;
	private double batterySoc2;
	private double batterySoc3;
	private double batterySoc4;
	private double batterySoc5;
	private double batterySoc6;
	private double batterySoc7;
	private double batterySoc8;
	private String vendorEngName;
	private String vendorEngContact;
	private String customerName;
	private String customerMobileNo;
	private String otherSiteType;
	private String cbmsSerialNo;
	private String softwareVersionOfBattery;
	private String currentReading;
	private String voltageReading;
	private String chargeVoltage;
	private String floatVoltage;
	private Date dateOfSiteVisit;
	private String anyPhysicalDamage;
	@Column(length = 25)
	private String otherIssue;
	private String exicomIssues;
	
	@ManyToOne
	private BtSiteType siteType;
	@ManyToOne
	private CommissioningScope commissioningScope;
	@ManyToOne 
	private BtSiteStatus siteStatus;
	
	@ManyToOne
	private BtServiceAppointment serviceAppointment;
	
	@ManyToOne
	private BtCase btCase;
	
	@ManyToOne
	EmployeeDetails owner;
	
	@ManyToOne
	Location warehouse;
	
	
    public Date getDateOfSiteVisit() {
		return dateOfSiteVisit;
	}

	public void setDateOfSiteVisit(Date dateOfSiteVisit) {
		this.dateOfSiteVisit = dateOfSiteVisit;
	}

	public Location getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(Location warehouse) {
		this.warehouse = warehouse;
	}

	public String getManuald() {
		return manuald;
	}

	public void setManuald(String manuald) {
		this.manuald = manuald;
	}

	public long getBatterySNo1() {
		return batterySNo1;
	}

	public void setBatterySNo1(long batterySNo1) {
		this.batterySNo1 = batterySNo1;
	}

	public long getBatterySNo2() {
		return batterySNo2;
	}

	public void setBatterySNo2(long batterySNo2) {
		this.batterySNo2 = batterySNo2;
	}

	public long getBatterySNo3() {
		return batterySNo3;
	}

	public void setBatterySNo3(long batterySNo3) {
		this.batterySNo3 = batterySNo3;
	}

	public long getBatterySNo4() {
		return batterySNo4;
	}

	public void setBatterySNo4(long batterySNo4) {
		this.batterySNo4 = batterySNo4;
	}

	public long getBatterySNo5() {
		return batterySNo5;
	}

	public void setBatterySNo5(long batterySNo5) {
		this.batterySNo5 = batterySNo5;
	}

	public long getBatterySNo6() {
		return batterySNo6;
	}

	public void setBatterySNo6(long batterySNo6) {
		this.batterySNo6 = batterySNo6;
	}

	public long getBatterySNo7() {
		return batterySNo7;
	}

	public void setBatterySNo7(long batterySNo7) {
		this.batterySNo7 = batterySNo7;
	}

	public long getBatterySNo8() {
		return batterySNo8;
	}

	public void setBatterySNo8(long batterySNo8) {
		this.batterySNo8 = batterySNo8;
	}

	public double getBatterySoc1() {
		return batterySoc1;
	}

	public void setBatterySoc1(double batterySoc1) {
		this.batterySoc1 = batterySoc1;
	}

	public double getBatterySoc2() {
		return batterySoc2;
	}

	public void setBatterySoc2(double batterySoc2) {
		this.batterySoc2 = batterySoc2;
	}

	public double getBatterySoc3() {
		return batterySoc3;
	}

	public void setBatterySoc3(double batterySoc3) {
		this.batterySoc3 = batterySoc3;
	}

	public double getBatterySoc4() {
		return batterySoc4;
	}

	public void setBatterySoc4(double batterySoc4) {
		this.batterySoc4 = batterySoc4;
	}

	public double getBatterySoc5() {
		return batterySoc5;
	}

	public void setBatterySoc5(double batterySoc5) {
		this.batterySoc5 = batterySoc5;
	}

	public double getBatterySoc6() {
		return batterySoc6;
	}

	public void setBatterySoc6(double batterySoc6) {
		this.batterySoc6 = batterySoc6;
	}

	public double getBatterySoc7() {
		return batterySoc7;
	}

	public void setBatterySoc7(double batterySoc7) {
		this.batterySoc7 = batterySoc7;
	}

	public double getBatterySoc8() {
		return batterySoc8;
	}

	public void setBatterySoc8(double batterySoc8) {
		this.batterySoc8 = batterySoc8;
	}

	public String getVendorEngName() {
		return vendorEngName;
	}

	public void setVendorEngName(String vendorEngName) {
		this.vendorEngName = vendorEngName;
	}

	public String getVendorEngContact() {
		return vendorEngContact;
	}

	public void setVendorEngContact(String vendorEngContact) {
		this.vendorEngContact = vendorEngContact;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerMobileNo() {
		return customerMobileNo;
	}

	public void setCustomerMobileNo(String customerMobileNo) {
		this.customerMobileNo = customerMobileNo;
	}

	public CommissioningScope getCommissioningScope() {
		return commissioningScope;
	}

	public void setCommissioningScope(CommissioningScope commissioningScope) {
		this.commissioningScope = commissioningScope;
	}

	public String getCbmsSerialNo() {
		return cbmsSerialNo;
	}

	public void setCbmsSerialNo(String cbmsSerialNo) {
		this.cbmsSerialNo = cbmsSerialNo;
	}

	public String getSoftwareVersionOfBattery() {
		return softwareVersionOfBattery;
	}

	public void setSoftwareVersionOfBattery(String softwareVersionOfBattery) {
		this.softwareVersionOfBattery = softwareVersionOfBattery;
	}

	public String getCurrentReading() {
		return currentReading;
	}

	public void setCurrentReading(String currentReading) {
		this.currentReading = currentReading;
	}

	public String getVoltageReading() {
		return voltageReading;
	}

	public void setVoltageReading(String voltageReading) {
		this.voltageReading = voltageReading;
	}

	public String getChargeVoltage() {
		return chargeVoltage;
	}

	public void setChargeVoltage(String chargeVoltage) {
		this.chargeVoltage = chargeVoltage;
	}

	public String getFloatVoltage() {
		return floatVoltage;
	}

	public void setFloatVoltage(String floatVoltage) {
		this.floatVoltage = floatVoltage;
	}

	public String getAnyPhysicalDamage() {
		return anyPhysicalDamage;
	}

	public void setAnyPhysicalDamage(String anyPhysicalDamage) {
		this.anyPhysicalDamage = anyPhysicalDamage;
	}

	public String getOtherIssue() {
		return otherIssue;
	}

	public void setOtherIssue(String otherIssue) {
		this.otherIssue = otherIssue;
	}

	public String getExicomIssues() {
		return exicomIssues;
	}

	public void setExicomIssues(String exicomIssues) {
		this.exicomIssues = exicomIssues;
	}

	public BtSiteType getSiteType() {
		return siteType;
	}

	public void setSiteType(BtSiteType siteType) {
		this.siteType = siteType;
	}

	public String getOtherSiteType() {
		return otherSiteType;
	}

	public void setOtherSiteType(String otherSiteType) {
		this.otherSiteType = otherSiteType;
	}

	public BtSiteStatus getSiteStatus() {
		return siteStatus;
	}

	public void setSiteStatus(BtSiteStatus siteStatus) {
		this.siteStatus = siteStatus;
	}

	public BtServiceAppointment getServiceAppointment() {
		return serviceAppointment;
	}

	public void setServiceAppointment(BtServiceAppointment serviceAppointment) {
		this.serviceAppointment = serviceAppointment;
	}

	public BtCase getBtCase() {
		return btCase;
	}

	public void setBtCase(BtCase btCase) {
		this.btCase = btCase;
	}

	

	@Override
	public BtFsrBatteryCommunication gOld() {
		// TODO Auto-generated method stub
		return this.old;
	}

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
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
	public void setOwner(EmployeeDetails owner) {
		this.owner=owner;
		
	}

	@Override
	public String getManualId() {
		// TODO Auto-generated method stub
		return this.manuald;
	}

	@Override
	public void setManualId(String manualId) {
		this.manuald=manualId;
		
	}
	@Transient transient BtFsrBatteryCommunication old;	
	@PostLoad
	@Override
	public void old() {
		old=(BtFsrBatteryCommunication) SerializationUtils.clone(this);
	}


}