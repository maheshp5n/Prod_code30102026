package com.pratikshat.exicom.model.request;

public class Contacts {
	private long contactid;
	private String title;
	private String contactOwnerFirstName;
	private String contactOwnerMiddleName;
	private String contactOwnerLastName;
	private String contactOwnerEmailId;
	private String salutation;
	private String contactDepartment;
	private String contactFirstName;
	private String contactLastName;
	private String contactMiddleName;
	private String contactSuffix;
	private String accountSAPNumber;
	private String accountName;
	private String contactEmailId;
	private String contactFaxNumber;
	private String contactPhoneNumber;
	private String contactMobileNumber;
	private long contactMallingAddressId;
	private String contactMallingAddress;
	private String contactMallingStreet;
	private String contactMallingCity;
	//private String contactMallingState;
	private String contactMallingPincode;
	//private String contactMallingCountry;
	private long contactMallingStateId;
	private String contactMallingStateName;
	private String contactMallingCountryId;
	private String contactMallingCountryName;
	private String employeeCode;
	private String contactTypeName;
	private long contactTypeId;
	private String contactDesignationName;
	private long contactDesignationId;
	private String gradeName;
	private long legalEntityId;
	private String leagalEntityName;
	private String contactCreatedByEmailId;
	private String contactCreatedByFirstName;
	private String contactCreatedByMiddleName;
	private String contactCreatedByLastName;
	private java.util.Date contactCreatedDate;
	private String contactModifyedByEmailId;
	private String contactModifyedByFirstName;
	private String contactModifyedByMiddleName;
	private String contactModifyedByLastName;
	private java.util.Date contactModifyedDate;
	private int businessPlaceId;
	private String businessPlaceName;
	/**
	 * 
	 */
	public Contacts() {
	}
	public Contacts(com.pratikshat.exicom.entity.ContactDetails contactDetails) {
		if(contactDetails!=null) {
			this.contactid=contactDetails.getContactId();
			this.title=contactDetails.getTitle();
			this.employeeCode=contactDetails.getEmployeeCode();
			if(contactDetails.getContactType()!=null) {
				this.contactTypeId = contactDetails.getContactType().getTypeId();
				this.contactTypeName = contactDetails.getContactType().getTypeName();
			}
			if(contactDetails.getContactDesignation()!=null) {
				this.contactDesignationId = contactDetails.getContactDesignation().getDesignationId();
				this.contactDesignationName = contactDetails.getContactDesignation().getDesignationName();
			}
			
			if(contactDetails.getGrade()!=null) {
				this.gradeName=contactDetails.getGrade().getBoardingName();
				
			}
			if(contactDetails.getLegalEntity()!=null) {
				this.legalEntityId=contactDetails.getLegalEntity().getLegalEntityId();
				this.leagalEntityName=contactDetails.getLegalEntity().getLegalEntityName();
			}
			if(contactDetails.getContactOwner()!=null) {
				this.contactOwnerEmailId=contactDetails.getContactOwner().getEmailId();
				this.contactOwnerFirstName=contactDetails.getContactOwner().getFirstName();
				this.contactOwnerMiddleName=contactDetails.getContactOwner().getMiddleName();
				this.contactOwnerLastName=contactDetails.getContactOwner().getLastName();
			}
			
			if(contactDetails.getBusinessPlace()!=null) {
				this.businessPlaceId = contactDetails.getBusinessPlace().getBusinessPlaceId();
				this.businessPlaceName = contactDetails.getBusinessPlace().getBusinessPlaceName();
			}
			this.salutation=contactDetails.getSalutation();
			this.contactFirstName=contactDetails.getFirstName();
			this.contactMiddleName=contactDetails.getMiddleName();
			this.contactLastName=contactDetails.getLastName();
			this.contactSuffix=contactDetails.getSuffix();
			if(contactDetails.getAccount()!=null) {
				this.accountSAPNumber=contactDetails.getAccount().getSapCustomerNumber();
				this.accountName=contactDetails.getAccount().getName();
			}
			this.contactEmailId=contactDetails.getEmailId();
			this.contactFaxNumber=contactDetails.getFax();
			this.contactPhoneNumber=contactDetails.getPhone();
			this.contactMobileNumber=contactDetails.getMobile();
			this.contactDepartment=contactDetails.getDepartment();
			if(contactDetails.getAddressInfo()!=null) {
				this.contactMallingAddressId=contactDetails.getAddressInfo().getAddressInfoId();
				this.contactMallingAddress=contactDetails.getAddressInfo().getAddress();
				this.contactMallingStreet=contactDetails.getAddressInfo().getStreet();
				this.contactMallingCity=contactDetails.getAddressInfo().getCity();
			//	this.contactMallingState=contactDetails.getAddressInfo().getState();
				this.contactMallingStateId=contactDetails.getAddressInfo().getState().getStateId();
				this.contactMallingStateName=contactDetails.getAddressInfo().getState().getStateName();
				this.contactMallingPincode=contactDetails.getAddressInfo().getPincode();
			//	this.contactMallingCountry=contactDetails.getAddressInfo().getCountry();
				this.contactMallingCountryId=contactDetails.getAddressInfo().getState().getCountry().getConuntryId();
				this.contactMallingCountryName=contactDetails.getAddressInfo().getState().getCountry().getCountryName();
			}
			if(contactDetails.getCreatedBy()!=null) {
				this.contactCreatedByEmailId=contactDetails.getCreatedBy().getEmailId();
				this.contactCreatedByFirstName=contactDetails.getCreatedBy().getFirstName();
				this.contactCreatedByMiddleName=contactDetails.getCreatedBy().getMiddleName();
				this.contactCreatedByLastName=contactDetails.getCreatedBy().getLastName();
			}
			this.contactCreatedDate=contactDetails.getCreatedOn();
			if(contactDetails.getModifiedBy()!=null) {
				this.contactModifyedByEmailId=contactDetails.getModifiedBy().getEmailId();
				this.contactModifyedByFirstName=contactDetails.getModifiedBy().getFirstName();
				this.contactModifyedByMiddleName=contactDetails.getModifiedBy().getMiddleName();
				this.contactModifyedByLastName=contactDetails.getModifiedBy().getLastName();
			}
			this.contactModifyedDate=contactDetails.getLastModifiedOn();
			
		}
	}

	
	/**
	 * @return the contactid
	 */
	public long getContactid() {
		return contactid;
	}
	
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @return the contactOwnerFirstName
	 */
	public String getContactOwnerFirstName() {
		return contactOwnerFirstName;
	}
	/**
	 * @return the contactOwnerLastName
	 */
	public String getContactOwnerLastName() {
		return contactOwnerLastName;
	}
	/**
	 * @return the contactOwnerEmailId
	 */
	public String getContactOwnerEmailId() {
		return contactOwnerEmailId;
	}
	/**
	 * @return the salutation
	 */
	public String getSalutation() {
		return salutation;
	}
	/**
	 * @return the contactDepartment
	 */
	public String getContactDepartment() {
		return contactDepartment;
	}
	/**
	 * @return the contactFirstName
	 */
	public String getContactFirstName() {
		return contactFirstName;
	}
	/**
	 * @return the contactLastName
	 */
	public String getContactLastName() {
		return contactLastName;
	}
	/**
	 * @return the contactMiddleName
	 */
	public String getContactMiddleName() {
		return contactMiddleName;
	}
	/**
	 * @return the contactSuffix
	 */
	public String getContactSuffix() {
		return contactSuffix;
	}
	/**
	 * @return the accountSAPNumber
	 */
	public String getAccountSAPNumber() {
		return accountSAPNumber;
	}
	/**
	 * @return the accountName
	 */
	public String getAccountName() {
		return accountName;
	}
	/**
	 * @return the contactEmailId
	 */
	public String getContactEmailId() {
		return contactEmailId;
	}
	/**
	 * @return the contactFaxNumber
	 */
	public String getContactFaxNumber() {
		return contactFaxNumber;
	}
	/**
	 * @return the contactPhoneNumber
	 */
	public String getContactPhoneNumber() {
		return contactPhoneNumber;
	}
	/**
	 * @return the contactMobileNumber
	 */
	public String getContactMobileNumber() {
		return contactMobileNumber;
	}
	/**
	 * @return the contactMallingAddressId
	 */
	public long getContactMallingAddressId() {
		return contactMallingAddressId;
	}
	/**
	 * @return the contactMallingAddress
	 */
	public String getContactMallingAddress() {
		return contactMallingAddress;
	}
	/**
	 * @return the contactMallingStreet
	 */
	public String getContactMallingStreet() {
		return contactMallingStreet;
	}
	/**
	 * @return the contactMallingCity
	 */
	public String getContactMallingCity() {
		return contactMallingCity;
	}
	/**
	 * @return the contactMallingState
	 */
	/*public long getContactMallingState() {
		return contactMallingState;
	}*/
	/**
	 * @return the contactMallingPincode
	 */
	public String getContactMallingPincode() {
		return contactMallingPincode;
	}
	/**
	 * @return the contactMallingCountry
	 */
	/*public String getContactMallingCountry() {
		return contactMallingCountry;
	}*/
	/**
	 * @param contactid the contactid to set
	 */
	public void setContactid(long contactid) {
		this.contactid = contactid;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @param contactOwnerFirstName the contactOwnerFirstName to set
	 */
	public void setContactOwnerFirstName(String contactOwnerFirstName) {
		this.contactOwnerFirstName = contactOwnerFirstName;
	}
	/**
	 * @param contactOwnerLastName the contactOwnerLastName to set
	 */
	public void setContactOwnerLastName(String contactOwnerLastName) {
		this.contactOwnerLastName = contactOwnerLastName;
	}
	/**
	 * @param contactOwnerEmailId the contactOwnerEmailId to set
	 */
	public void setContactOwnerEmailId(String contactOwnerEmailId) {
		this.contactOwnerEmailId = contactOwnerEmailId;
	}
	/**
	 * @param salutation the salutation to set
	 */
	public void setSalutation(String salutation) {
		this.salutation = salutation;
	}
	/**
	 * @param contactDepartment the contactDepartment to set
	 */
	public void setContactDepartment(String contactDepartment) {
		this.contactDepartment = contactDepartment;
	}
	/**
	 * @param contactFirstName the contactFirstName to set
	 */
	public void setContactFirstName(String contactFirstName) {
		this.contactFirstName = contactFirstName;
	}
	/**
	 * @param contactLastName the contactLastName to set
	 */
	public void setContactLastName(String contactLastName) {
		this.contactLastName = contactLastName;
	}
	/**
	 * @param contactMiddleName the contactMiddleName to set
	 */
	public void setContactMiddleName(String contactMiddleName) {
		this.contactMiddleName = contactMiddleName;
	}
	/**
	 * @param contactSuffix the contactSuffix to set
	 */
	public void setContactSuffix(String contactSuffix) {
		this.contactSuffix = contactSuffix;
	}
	/**
	 * @param accountSAPNumber the accountSAPNumber to set
	 */
	public void setAccountSAPNumber(String accountSAPNumber) {
		this.accountSAPNumber = accountSAPNumber;
	}
	/**
	 * @param accountName the accountName to set
	 */
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	/**
	 * @param contactEmailId the contactEmailId to set
	 */
	public void setContactEmailId(String contactEmailId) {
		this.contactEmailId = contactEmailId;
	}
	/**
	 * @param contactFaxNumber the contactFaxNumber to set
	 */
	public void setContactFaxNumber(String contactFaxNumber) {
		this.contactFaxNumber = contactFaxNumber;
	}
	/**
	 * @param contactPhoneNumber the contactPhoneNumber to set
	 */
	public void setContactPhoneNumber(String contactPhoneNumber) {
		this.contactPhoneNumber = contactPhoneNumber;
	}
	/**
	 * @param contactMobileNumber the contactMobileNumber to set
	 */
	public void setContactMobileNumber(String contactMobileNumber) {
		this.contactMobileNumber = contactMobileNumber;
	}
	/**
	 * @param contactMallingAddressId the contactMallingAddressId to set
	 */
	public void setContactMallingAddressId(long contactMallingAddressId) {
		this.contactMallingAddressId = contactMallingAddressId;
	}
	/**
	 * @param contactMallingAddress the contactMallingAddress to set
	 */
	public void setContactMallingAddress(String contactMallingAddress) {
		this.contactMallingAddress = contactMallingAddress;
	}
	/**
	 * @param contactMallingStreet the contactMallingStreet to set
	 */
	public void setContactMallingStreet(String contactMallingStreet) {
		this.contactMallingStreet = contactMallingStreet;
	}
	/**
	 * @param contactMallingCity the contactMallingCity to set
	 */
	public void setContactMallingCity(String contactMallingCity) {
		this.contactMallingCity = contactMallingCity;
	}
	/**
	 * @param contactMallingState the contactMallingState to set
	 */
	/*public void setContactMallingState(String contactMallingState) {
		this.contactMallingState = contactMallingState;
	}*/
	/**
	 * @param contactMallingPincode the contactMallingPincode to set
	 */
	public void setContactMallingPincode(String contactMallingPincode) {
		this.contactMallingPincode = contactMallingPincode;
	}
	/**
	 * @param contactMallingCountry the contactMallingCountry to set
	 */
	/*public void setContactMallingCountry(String contactMallingCountry) {
		this.contactMallingCountry = contactMallingCountry;
	}
	*/
	/**
	 * @return the contactMallingStateId
	 */
	public long getContactMallingStateId() {
		return contactMallingStateId;
	}
	/**
	 * @return the contactMallingStateName
	 */
	public String getContactMallingStateName() {
		return contactMallingStateName;
	}
	/**
	 * @return the contactMallingCountryId
	 */
	public String getContactMallingCountryId() {
		return contactMallingCountryId;
	}
	/**
	 * @return the contactMallingCountryName
	 */
	public String getContactMallingCountryName() {
		return contactMallingCountryName;
	}
	/**
	 * @param contactMallingStateId the contactMallingStateId to set
	 */
	public void setContactMallingStateId(long contactMallingStateId) {
		this.contactMallingStateId = contactMallingStateId;
	}
	/**
	 * @param contactMallingStateName the contactMallingStateName to set
	 */
	public void setContactMallingStateName(String contactMallingStateName) {
		this.contactMallingStateName = contactMallingStateName;
	}
	/**
	 * @param contactMallingCountryId the contactMallingCountryId to set
	 */
	public void setContactMallingCountryId(String contactMallingCountryId) {
		this.contactMallingCountryId = contactMallingCountryId;
	}
	/**
	 * @param contactMallingCountryName the contactMallingCountryName to set
	 */
	public void setContactMallingCountryName(String contactMallingCountryName) {
		this.contactMallingCountryName = contactMallingCountryName;
	}
	/**
	 * @return the employeeCode
	 */
	public String getEmployeeCode() {
		return employeeCode;
	}
	
	
	/**
	 * @return the legalEntityId
	 */
	public long getLegalEntityId() {
		return legalEntityId;
	}
	/**
	 * @return the leagalEntityName
	 */
	public String getLeagalEntityName() {
		return leagalEntityName;
	}
	/**
	 * @param employeeCode the employeeCode to set
	 */
	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}
	
	
	/**
	 * @param legalEntityId the legalEntityId to set
	 */
	public void setLegalEntityId(long legalEntityId) {
		this.legalEntityId = legalEntityId;
	}
	/**
	 * @param leagalEntityName the leagalEntityName to set
	 */
	public void setLeagalEntityName(String leagalEntityName) {
		this.leagalEntityName = leagalEntityName;
	}
	/**
	 * @return the contactCreatedByEmailId
	 */
	public String getContactCreatedByEmailId() {
		return contactCreatedByEmailId;
	}
	/**
	 * @return the contactCreatedByFirstName
	 */
	public String getContactCreatedByFirstName() {
		return contactCreatedByFirstName;
	}
	/**
	 * @return the contactCreatedByLastName
	 */
	public String getContactCreatedByLastName() {
		return contactCreatedByLastName;
	}
	/**
	 * @return the contactCreatedDate
	 */
	public java.util.Date getContactCreatedDate() {
		return contactCreatedDate;
	}
	/**
	 * @return the contactModifyedByEmailId
	 */
	public String getContactModifyedByEmailId() {
		return contactModifyedByEmailId;
	}
	/**
	 * @return the contactModifyedByFirstName
	 */
	public String getContactModifyedByFirstName() {
		return contactModifyedByFirstName;
	}
	/**
	 * @return the contactModifyedByLastName
	 */
	public String getContactModifyedByLastName() {
		return contactModifyedByLastName;
	}
	/**
	 * @return the contactModifyedDate
	 */
	public java.util.Date getContactModifyedDate() {
		return contactModifyedDate;
	}
	/**
	 * @param contactCreatedByEmailId the contactCreatedByEmailId to set
	 */
	public void setContactCreatedByEmailId(String contactCreatedByEmailId) {
		this.contactCreatedByEmailId = contactCreatedByEmailId;
	}
	/**
	 * @param contactCreatedByFirstName the contactCreatedByFirstName to set
	 */
	public void setContactCreatedByFirstName(String contactCreatedByFirstName) {
		this.contactCreatedByFirstName = contactCreatedByFirstName;
	}
	/**
	 * @param contactCreatedByLastName the contactCreatedByLastName to set
	 */
	public void setContactCreatedByLastName(String contactCreatedByLastName) {
		this.contactCreatedByLastName = contactCreatedByLastName;
	}
	/**
	 * @param contactCreatedDate the contactCreatedDate to set
	 */
	public void setContactCreatedDate(java.util.Date contactCreatedDate) {
		this.contactCreatedDate = contactCreatedDate;
	}
	/**
	 * @param contactModifyedByEmailId the contactModifyedByEmailId to set
	 */
	public void setContactModifyedByEmailId(String contactModifyedByEmailId) {
		this.contactModifyedByEmailId = contactModifyedByEmailId;
	}
	/**
	 * @param contactModifyedByFirstName the contactModifyedByFirstName to set
	 */
	public void setContactModifyedByFirstName(String contactModifyedByFirstName) {
		this.contactModifyedByFirstName = contactModifyedByFirstName;
	}
	/**
	 * @param contactModifyedByLastName the contactModifyedByLastName to set
	 */
	public void setContactModifyedByLastName(String contactModifyedByLastName) {
		this.contactModifyedByLastName = contactModifyedByLastName;
	}
	/**
	 * @param contactModifyedDate the contactModifyedDate to set
	 */
	public void setContactModifyedDate(java.util.Date contactModifyedDate) {
		this.contactModifyedDate = contactModifyedDate;
	}
	public String getContactTypeName() {
		return contactTypeName;
	}
	public void setContactTypeName(String contactTypeName) {
		this.contactTypeName = contactTypeName;
	}
	public long getContactTypeId() {
		return contactTypeId;
	}
	public void setContactTypeId(long contactTypeId) {
		this.contactTypeId = contactTypeId;
	}
	public String getContactDesignationName() {
		return contactDesignationName;
	}
	public void setContactDesignationName(String contactDesignationName) {
		this.contactDesignationName = contactDesignationName;
	}
	public long getContactDesignationId() {
		return contactDesignationId;
	}
	public void setContactDesignationId(long contactDesignationId) {
		this.contactDesignationId = contactDesignationId;
	}
	public String getGradeName() {
		return gradeName;
	}
	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}
	public int getBusinessPlaceId() {
		return businessPlaceId;
	}
	public void setBusinessPlaceId(int businessPlaceId) {
		this.businessPlaceId = businessPlaceId;
	}
	public String getBusinessPlaceName() {
		return businessPlaceName;
	}
	public void setBusinessPlaceName(String businessPlaceName) {
		this.businessPlaceName = businessPlaceName;
	}
	public String getContactOwnerMiddleName() {
		return contactOwnerMiddleName;
	}
	public void setContactOwnerMiddleName(String contactOwnerMiddleName) {
		this.contactOwnerMiddleName = contactOwnerMiddleName;
	}
	public String getContactCreatedByMiddleName() {
		return contactCreatedByMiddleName;
	}
	public void setContactCreatedByMiddleName(String contactCreatedByMiddleName) {
		this.contactCreatedByMiddleName = contactCreatedByMiddleName;
	}
	public String getContactModifyedByMiddleName() {
		return contactModifyedByMiddleName;
	}
	public void setContactModifyedByMiddleName(String contactModifyedByMiddleName) {
		this.contactModifyedByMiddleName = contactModifyedByMiddleName;
	}
	
	
}
