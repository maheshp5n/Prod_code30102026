package com.pratikshat.exicom.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class QuotePdfHeaderAndFooter implements java.io.Serializable,OldPresist<QuotePdfHeaderAndFooter>{
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	long id;
	
	String templateName;
	
	String logo;
	String signature;
	
	String companyName;
	@Column(length = 500)
	String addressLine1;
	@Column(length = 500)
	String addressLine2;
	String city;
	String pincode;
	String stateName;
	
	String cin;
	String documentNumber;
	String telephone;
	String gstNumber;
	String website;
	
	@Column(length = 500)
	String footerline1;
	@Column(length = 500)
	String footerline2;
	@Column(length = 500)
	String footerline3;
	@Column(length = 500)
	String footerline4;
	@Column(length = 500)
	String footerline5;
	
	@ManyToOne(fetch = FetchType.LAZY)
	EmployeeDetails createdBy;
	Date createdDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	EmployeeDetails modifiedBy;
	Date modifiedDate;
	
	boolean isActive;
	
	@ManyToOne(fetch = FetchType.LAZY)
	BusinessDomain businessDomain;
	
	public QuotePdfHeaderAndFooter() {}
	
	public QuotePdfHeaderAndFooter(long id){
		this.id = id;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getAddressLine1() {
		return addressLine1;
	}
	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}
	public String getAddressLine2() {
		return addressLine2;
	}
	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getPincode() {
		return pincode;
	}
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	public String getCin() {
		return cin;
	}
	public void setCin(String cin) {
		this.cin = cin;
	}
	public String getDocumentNumber() {
		return documentNumber;
	}
	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getGstNumber() {
		return gstNumber;
	}
	public void setGstNumber(String gstNumber) {
		this.gstNumber = gstNumber;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public String getFooterline1() {
		return footerline1;
	}
	public void setFooterline1(String footerline1) {
		this.footerline1 = footerline1;
	}
	public String getFooterline2() {
		return footerline2;
	}
	public void setFooterline2(String footerline2) {
		this.footerline2 = footerline2;
	}
	public String getFooterline3() {
		return footerline3;
	}
	public void setFooterline3(String footerline3) {
		this.footerline3 = footerline3;
	}
	public String getFooterline4() {
		return footerline4;
	}
	public void setFooterline4(String footerline4) {
		this.footerline4 = footerline4;
	}
	public String getFooterline5() {
		return footerline5;
	}
	public void setFooterline5(String footerline5) {
		this.footerline5 = footerline5;
	}
	public EmployeeDetails getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(EmployeeDetails createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public EmployeeDetails getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(EmployeeDetails modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public BusinessDomain getBusinessDomain() {
		return businessDomain;
	}

	public void setBusinessDomain(BusinessDomain businessDomain) {
		this.businessDomain = businessDomain;
	}
	
	

	@javax.persistence.Transient transient QuotePdfHeaderAndFooter old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(QuotePdfHeaderAndFooter)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public QuotePdfHeaderAndFooter gOld() {
		return old;
	}
	public String toString() {
			return ""+id;
	}
}
