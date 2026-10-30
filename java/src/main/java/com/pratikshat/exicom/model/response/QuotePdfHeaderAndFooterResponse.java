package com.pratikshat.exicom.model.response;

import java.util.Date;
import java.util.List;

import com.pratikshat.exicom.entity.QuotePdfHeaderAndFooter;
import com.pratikshat.exicom.entity.QuoteTermsAndConditions;
import com.pratikshat.exicom.entity.StateWithQuotePdf;

public class QuotePdfHeaderAndFooterResponse {

	long id;
	
	String templateName;
	
	String logo;
	String signature;
	
	String companyName;
	String addressLine1;
	String addressLine2;
	String city;
	String pincode;
	String stateName;
	
	String cin;
	String documentNumber;
	String telephone;
	String gstNumber;
	String website;
	
	String footerline1;
	String footerline2;
	String footerline3;
	String footerline4;
	String footerline5;
	
	String createdByFirstName;
	String createdByMiddleName;
	String createdByLastName;
	String createdByEmailId;
	Date createdDate;
	
	String modifiedByFirstName;
	String modifiedByMiddleName;
	String modifiedByLastName;
	String modifiedByEmailId;
	Date modifiedDate;
	
	boolean isActive;
	
	List<TermsAndConditionsResponse> termsAndConditions;
	
	int businessDomainId;
	String businessDomainName;
	
	List<StateWithQuotePdf> stateList;
	
	public QuotePdfHeaderAndFooterResponse(){}
	
	public QuotePdfHeaderAndFooterResponse(QuotePdfHeaderAndFooter quotePdfHeaderAndFooter){
		this.id = quotePdfHeaderAndFooter.getId();
		this.templateName = quotePdfHeaderAndFooter.getTemplateName();
		
		this.logo = quotePdfHeaderAndFooter.getLogo();
		this.signature = quotePdfHeaderAndFooter.getSignature();
		
		this.companyName = quotePdfHeaderAndFooter.getCompanyName();
		this.addressLine1 = quotePdfHeaderAndFooter.getAddressLine1();
		this.addressLine2 = quotePdfHeaderAndFooter.getAddressLine2();
		this.city = quotePdfHeaderAndFooter.getCity();
		this.pincode = quotePdfHeaderAndFooter.getPincode();
		this.stateName = quotePdfHeaderAndFooter.getStateName();
		
		this.cin = quotePdfHeaderAndFooter.getCin();
		this.documentNumber = quotePdfHeaderAndFooter.getDocumentNumber();
		this.telephone = quotePdfHeaderAndFooter.getTelephone();
		this.gstNumber = quotePdfHeaderAndFooter.getGstNumber();
		this.website = quotePdfHeaderAndFooter.getWebsite();
		
		this.footerline1 = quotePdfHeaderAndFooter.getFooterline1();
		this.footerline2 = quotePdfHeaderAndFooter.getFooterline2();
		this.footerline3 = quotePdfHeaderAndFooter.getFooterline3();
		this.footerline4 = quotePdfHeaderAndFooter.getFooterline4();
		this.footerline5 = quotePdfHeaderAndFooter.getFooterline5();
		
		this.isActive = quotePdfHeaderAndFooter.isActive();
		
		if(quotePdfHeaderAndFooter.getCreatedBy()!=null) {
			this.createdByFirstName = quotePdfHeaderAndFooter.getCreatedBy().getFirstName();
			this.createdByMiddleName = quotePdfHeaderAndFooter.getCreatedBy().getMiddleName();
			this.createdByLastName = quotePdfHeaderAndFooter.getCreatedBy().getLastName();
			this.createdByEmailId = quotePdfHeaderAndFooter.getCreatedBy().getEmailId();
			
			this.createdDate = quotePdfHeaderAndFooter.getCreatedDate();
		}
		
		if(quotePdfHeaderAndFooter.getModifiedBy()!=null) {
			this.modifiedByFirstName = quotePdfHeaderAndFooter.getModifiedBy().getFirstName();
			this.modifiedByMiddleName = quotePdfHeaderAndFooter.getModifiedBy().getMiddleName();
			this.modifiedByLastName = quotePdfHeaderAndFooter.getModifiedBy().getLastName();
			this.modifiedByEmailId = quotePdfHeaderAndFooter.getModifiedBy().getEmailId();
			
			this.modifiedDate = quotePdfHeaderAndFooter.getModifiedDate();
		}
		
		if(quotePdfHeaderAndFooter.getBusinessDomain()!=null) {
			this.businessDomainId = quotePdfHeaderAndFooter.getBusinessDomain().getBusinessDomainId();
			this.businessDomainName = quotePdfHeaderAndFooter.getBusinessDomain().getBusinessDomainName();
		}
		
	}
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
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
	public String getCreatedByFirstName() {
		return createdByFirstName;
	}
	public void setCreatedByFirstName(String createdByFirstName) {
		this.createdByFirstName = createdByFirstName;
	}
	public String getCreatedByMiddleName() {
		return createdByMiddleName;
	}
	public void setCreatedByMiddleName(String createdByMiddleName) {
		this.createdByMiddleName = createdByMiddleName;
	}
	public String getCreatedByLastName() {
		return createdByLastName;
	}
	public void setCreatedByLastName(String createdByLastName) {
		this.createdByLastName = createdByLastName;
	}
	public String getCreatedByEmailId() {
		return createdByEmailId;
	}
	public void setCreatedByEmailId(String createdByEmailId) {
		this.createdByEmailId = createdByEmailId;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getModifiedByFirstName() {
		return modifiedByFirstName;
	}
	public void setModifiedByFirstName(String modifiedByFirstName) {
		this.modifiedByFirstName = modifiedByFirstName;
	}
	public String getModifiedByMiddleName() {
		return modifiedByMiddleName;
	}
	public void setModifiedByMiddleName(String modifiedByMiddleName) {
		this.modifiedByMiddleName = modifiedByMiddleName;
	}
	public String getModifiedByLastName() {
		return modifiedByLastName;
	}
	public void setModifiedByLastName(String modifiedByLastName) {
		this.modifiedByLastName = modifiedByLastName;
	}
	public String getModifiedByEmailId() {
		return modifiedByEmailId;
	}
	public void setModifiedByEmailId(String modifiedByEmailId) {
		this.modifiedByEmailId = modifiedByEmailId;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public List<TermsAndConditionsResponse> getTermsAndConditions() {
		return termsAndConditions;
	}

	public void setTermsAndConditions(List<TermsAndConditionsResponse> termsAndConditions) {
		this.termsAndConditions = termsAndConditions;
	}

	public int getBusinessDomainId() {
		return businessDomainId;
	}

	public void setBusinessDomainId(int businessDomainId) {
		this.businessDomainId = businessDomainId;
	}

	public String getBusinessDomainName() {
		return businessDomainName;
	}

	public void setBusinessDomainName(String businessDomainName) {
		this.businessDomainName = businessDomainName;
	}

	public List<StateWithQuotePdf> getStateList() {
		return stateList;
	}

	public void setStateList(List<StateWithQuotePdf> stateList) {
		this.stateList = stateList;
	}
	
	
	
}
