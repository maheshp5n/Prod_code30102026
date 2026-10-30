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
import org.apache.commons.lang.SerializationUtils;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.pratikshat.exicom.interceptor.ObjectAuditListener;

@Entity
@EntityListeners({AuditingEntityListener.class,ObjectAuditListener.class})
public class BtFsrOdcKitting  extends Auditable<EmployeeDetails> implements Serializable,OldPresist<BtFsrOdcKitting> {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	private String manualId;
	private String cbmsSerialNo;
	private String softwareVersionOfBattery;
	private String currentReading;
	private String voltageReading;
	private String macID;
	private String smpsSerialNo;
	private String odcType;
	private String odcSerialNo;
	private String smpsType;
	private String eNodeBType;
	private String projectType;
	private String unitType;
	private String rectifierRating;
	private long RjilSmpsMatcode;
	private long batterySNo1;
	private long batterySNo2;
	private long batterySNo3;
	private long batterySNo4;
	private double batterySoc1;
	private double batterySoc2;
	private double batterySoc3;
	private double batterySoc4;
	private String vendorEngName;
	private String vendorEngContact;
	private String batteryType;
	private String customerName;
	private String customerMobileNo;
	private Date dateOfSiteVisit;

	
	@Column(length = 20000)
	private String anyPhysicalDamage;
	private String otherIssue;
	
	@ManyToOne
	Location warehouse;
	
	@ManyToOne
	BtSiteType siteType;
	
	
	private String otherSiteType;
	
	@ManyToOne(fetch = FetchType.LAZY)
	BtServiceAppointment btServiceAppointment;
	
	@ManyToOne( fetch = FetchType.LAZY)
	BtCase btCase;
	
	@ManyToOne
	EmployeeDetails ownerDetails;
	
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

	public String getMacID() {
		return macID;
	}

	public void setMacID(String macID) {
		this.macID = macID;
	}

	public String getSmpsSerialNo() {
		return smpsSerialNo;
	}

	public void setSmpsSerialNo(String smpsSerialNo) {
		this.smpsSerialNo = smpsSerialNo;
	}

	public String getOdcType() {
		return odcType;
	}

	public void setOdcType(String odcType) {
		this.odcType = odcType;
	}

	public String getOdcSerialNo() {
		return odcSerialNo;
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

	public void setOdcSerialNo(String odcSerialNo) {
		this.odcSerialNo = odcSerialNo;
	}

	public String geteNodeBType() {
		return eNodeBType;
	}

	public void seteNodeBType(String eNodeBType) {
		this.eNodeBType = eNodeBType;
	}

	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	public String getUnitType() {
		return unitType;
	}

	public void setUnitType(String unitType) {
		this.unitType = unitType;
	}

	public String getRectifierRating() {
		return rectifierRating;
	}

	public void setRectifierRating(String rectifierRating) {
		this.rectifierRating = rectifierRating;
	}

	public String getSmpsType() {
		return smpsType;
	}

	public void setSmpsType(String smpsType) {
		this.smpsType = smpsType;
	}

	public long getRjilSmpsMatcode() {
		return RjilSmpsMatcode;
	}

	public void setRjilSmpsMatcode(long rjilSmpsMatcode) {
		RjilSmpsMatcode = rjilSmpsMatcode;
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

	public void setBatterySNo2(long batterySNoo2) {
		this.batterySNo2 = batterySNoo2;
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

	public String getBatteryType() {
		return batteryType;
	}

	public void setBatteryType(String batteryType) {
		this.batteryType = batteryType;
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

	public Location getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(Location warehouse) {
		this.warehouse = warehouse;
	}


	public Date getDateOfSiteVisit() {
		return dateOfSiteVisit;
	}

	public void setDateOfSiteVisit(Date dateOfSiteVisit) {
		this.dateOfSiteVisit = dateOfSiteVisit;
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

	public BtServiceAppointment getBtServiceAppointment() {
		return btServiceAppointment;
	}

	public void setBtServiceAppointment(BtServiceAppointment btServiceAppointment) {
		this.btServiceAppointment = btServiceAppointment;
	}

	public BtCase getBtCase() {
		return btCase;
	}

	public void setBtCase(BtCase btCase) {
		this.btCase = btCase;
	}

	public EmployeeDetails getOwnerDetails() {
		return ownerDetails;
	}

	public void setOwnerDetails(EmployeeDetails ownerDetails) {
		this.ownerDetails = ownerDetails;
	}

	public BtFsrOdcKitting getOld() {
		return old;
	}

	public void setOld(BtFsrOdcKitting old) {
		this.old = old;
	}

	@Override
	public BtFsrOdcKitting gOld() {
		// TODO Auto-generated method stub
		return old;
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
		// TODO Auto-generated method stub
		return ownerDetails;
	}

	@Override
	public void setOwner(EmployeeDetails u) {
		this.ownerDetails=u;
	}

	@Override
	public String getManualId() {
		// TODO Auto-generated method stub
		return this.manualId;
	}

	@Override
	public void setManualId(String manualId) {
		this.manualId=manualId;
	}
	 @Transient transient BtFsrOdcKitting old;	
		@PostLoad
		@Override
		public void old() {
		this.old=(BtFsrOdcKitting) SerializationUtils.clone(this);
		}
}