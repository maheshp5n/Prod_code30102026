package com.pratikshat.exicom.model.request;

import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.pratikshat.exicom.entity.Accounts;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.AddressInformation;


public class AccountCreate {
	@NotNull(message="SAP Account Name must not null")
	@NotEmpty(message="SAP Account Name must not null")
	String accountName;
	String accountOwner;
	String accountOwnerFirstName;
	String accountOwnerMiddleName;
	String accountOwnerLastName;
	
	String parentAccountSapNumber;
	
	String parentAccountName;
	long parentCustomerId;
	String parentCustomerName;
	
	String accountTypeName;
	int accountTypeId;
	String phone;
	String description;
	String accountCatagoryName;
	int accountCategoryId;
	String emailid;
	@NotNull(message="SAP Account Number must not null")
	@NotEmpty(message="SAP Account Number must not null")
	String sampAccountNumber;
	String billingAddress;
	String billingStreet;
	String billingCity;
	//String billingState;
	String billingPincode;
	//String billingCountry;
	String shippingAddress;
	String shippingStreet;
	String shippingCity;
	//String shippingState;
	String shippingPincode;
	//String shippingCountry;
	boolean shippingAsBilling;
	long billingStateId;
	String billingSteateName;
	long shippingStateId;
	String shippingStateName;
	String billingCountryId;
	String billingcountryName;
	String shippingCountryId;
	String shippingCountryName;
	String createdById;
	String createdByFirstName;
	String createdByMiddleName;
	String createdByLastName;
	Date createdDate;
	String modifiyedById;
	String modifiyedFirstName;
	String modifiyedMiddleName;
	String modifiyedLastName;
	Date modifiyedDate;
	
	
	public AccountCreate() {}
	public AccountCreate(Accounts account,EmployeeDetails owner,AddressInformation billingAddress,AddressInformation shippingAddress) {
		if(account!=null) {
			this.accountName=account.getName();
			this.accountOwner=owner.getEmailId();
			if(owner!=null) {
				this.accountOwnerFirstName=owner.getFirstName();
				this.accountOwnerMiddleName=owner.getMiddleName();
				this.accountOwnerLastName=owner.getLastName();
			}
			if(account.getAccountType()!=null) {
				this.accountTypeId=account.getAccountType().getAccountTypeId();
				this.accountTypeName = account.getAccountType().getAccountTypeName();
			}
			
			this.phone=account.getPhoneNumber();
			if(account.getBusinessDoamin()!=null) {
				this.accountCatagoryName=account.getBusinessDoamin().getBusinessDomainName();
				this.accountCategoryId = account.getBusinessDoamin().getBusinessDomainId();
			}
			
			this.description=account.getDescription();
			this.emailid=account.getEmailId();
			this.sampAccountNumber=account.getSapCustomerNumber();
			if(billingAddress!=null) {
				this.billingAddress=billingAddress.getAddress();
				this.billingStreet=billingAddress.getStreet();
				this.billingCity=billingAddress.getCity();
				//this.billingState=billingAddress.getState();
				this.billingPincode=billingAddress.getPincode();

			}
			if(shippingAddress!=null) {
				this.shippingAddress=shippingAddress.getAddress();
				this.shippingStreet=shippingAddress.getStreet();
				this.shippingCity=shippingAddress.getCity();
				this.shippingPincode=shippingAddress.getPincode();
			}
			if(billingAddress!=null) {
				if(billingAddress.getState()!=null) {
					this.billingStateId=billingAddress.getState().getStateId();
					this.billingSteateName=billingAddress.getState().getStateName();
					if(billingAddress.getState().getCountry()!=null) {
						this.billingCountryId=billingAddress.getState().getCountry().getConuntryId();
						this.billingcountryName=billingAddress.getState().getCountry().getCountryName();
					}					
				}				
			}
			if(shippingAddress!=null) {
				if(shippingAddress.getState()!=null) {
					this.shippingStateId=shippingAddress.getState().getStateId();
					this.shippingStateName=shippingAddress.getState().getStateName();
					if(shippingAddress.getState().getCountry()!=null) {
						this.shippingCountryId=shippingAddress.getState().getCountry().getConuntryId();
						this.shippingCountryName=shippingAddress.getState().getCountry().getCountryName();
					}
						
				}
				
			}
			if(shippingAddress!=null&&billingAddress!=null) {
				if(shippingAddress.getAddressInfoId()==billingAddress.getAddressInfoId())
					this.shippingAsBilling=true;
			}
			if(account.getParentAccount()!=null) {
				this.parentAccountSapNumber=account.getParentAccount().getSapCustomerNumber();
				this.parentAccountName=account.getParentAccount().getName();
			}
			if(account.getParentCustomer()!=null) {
				this.parentCustomerId=account.getParentCustomer().getCustomerId();
				this.parentCustomerName=account.getParentCustomer().getCustomerName();
			}
			if(account.getCreatedBy()!=null) {
				this.createdById=account.getCreatedBy().getEmailId();
				this.createdByFirstName=account.getCreatedBy().getFirstName();
				this.createdByMiddleName=account.getCreatedBy().getMiddleName();
				this.createdByLastName=account.getCreatedBy().getLastName();
			}
			this.createdDate=account.getCreatedDate();
			if(account.getModifiedBy()!=null) {
				this.modifiyedById=account.getModifiedBy().getEmailId();
				this.modifiyedFirstName=account.getModifiedBy().getFirstName();
				this.modifiyedMiddleName=account.getModifiedBy().getMiddleName();
				this.modifiyedLastName=account.getModifiedBy().getLastName();
			}
			this.modifiyedDate=account.getLastModified();
		}
		
	}
	
	
	/**
	 * @return the accountOwnerFirstName
	 */
	public String getAccountOwnerFirstName() {
		return accountOwnerFirstName;
	}
	/**
	 * @return the accountOwnerMiddleName
	 */
	public String getAccountOwnerMiddleName() {
		return accountOwnerMiddleName;
	}
	/**
	 * @return the accountOwnerLastName
	 */
	public String getAccountOwnerLastName() {
		return accountOwnerLastName;
	}
	/**
	 * @param accountOwnerFirstName the accountOwnerFirstName to set
	 */
	public void setAccountOwnerFirstName(String accountOwnerFirstName) {
		this.accountOwnerFirstName = accountOwnerFirstName;
	}
	/**
	 * @param accountOwnerMiddleName the accountOwnerMiddleName to set
	 */
	public void setAccountOwnerMiddleName(String accountOwnerMiddleName) {
		this.accountOwnerMiddleName = accountOwnerMiddleName;
	}
	/**
	 * @param accountOwnerLastName the accountOwnerLastName to set
	 */
	public void setAccountOwnerLastName(String accountOwnerLastName) {
		this.accountOwnerLastName = accountOwnerLastName;
	}
	/**
	 * @return the shippingAsBilling
	 */
	public boolean isShippingAsBilling() {
		return shippingAsBilling;
	}
	/**
	 * @param shippingAsBilling the shippingAsBilling to set
	 */
	public void setShippingAsBilling(boolean shippingAsBilling) {
		this.shippingAsBilling = shippingAsBilling;
	}
	/**
	 * @return the accountName
	 */
	public String getAccountName() {
		return accountName;
	}
	/**
	 * @param accountName the accountName to set
	 */
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	/**
	 * @return the accountOwner
	 */
	public String getAccountOwner() {
		return accountOwner;
	}
	/**
	 * @param accountOwner the accountOwner to set
	 */
	public void setAccountOwner(String accountOwner) {
		this.accountOwner = accountOwner;
	}
	
	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * @return the emailid
	 */
	public String getEmailid() {
		return emailid;
	}
	/**
	 * @param emailid the emailid to set
	 */
	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}
	/**
	 * @return the sampAccountNumber
	 */
	public String getSampAccountNumber() {
		return sampAccountNumber;
	}
	/**
	 * @param sampAccountNumber the sampAccountNumber to set
	 */
	public void setSampAccountNumber(String sampAccountNumber) {
		this.sampAccountNumber = sampAccountNumber;
	}
	/**
	 * @return the billingAddress
	 */
	public String getBillingAddress() {
		return billingAddress;
	}
	/**
	 * @param billingAddress the billingAddress to set
	 */
	public void setBillingAddress(String billingAddress) {
		this.billingAddress = billingAddress;
	}
	/**
	 * @return the billingStreet
	 */
	public String getBillingStreet() {
		return billingStreet;
	}
	/**
	 * @param billingStreet the billingStreet to set
	 */
	public void setBillingStreet(String billingStreet) {
		this.billingStreet = billingStreet;
	}
	/**
	 * @return the billingCity
	 */
	public String getBillingCity() {
		return billingCity;
	}
	/**
	 * @param billingCity the billingCity to set
	 */
	public void setBillingCity(String billingCity) {
		this.billingCity = billingCity;
	}
	/**
	 * @return the billingState
	 */
	/*public String getBillingState() {
		return billingState;
	}*/
	/**
	 * @param billingState the billingState to set
	 */
	/*public void setBillingState(String billingState) {
		this.billingState = billingState;
	}*/
	/**
	 * @return the billingPincode
	 */
	public String getBillingPincode() {
		return billingPincode;
	}
	/**
	 * @param billingPincode the billingPincode to set
	 */
	public void setBillingPincode(String billingPincode) {
		this.billingPincode = billingPincode;
	}
	/**
	 * @return the billingCountry
	 */
	/*public String getBillingCountry() {
		return billingCountry;
	}*/
	/**
	 * @param billingCountry the billingCountry to set
	 */
	/*public void setBillingCountry(String billingCountry) {
		this.billingCountry = billingCountry;
	}*/
	/**
	 * @return the shippingAddress
	 */
	public String getShippingAddress() {
		return shippingAddress;
	}
	/**
	 * @param shippingAddress the shippingAddress to set
	 */
	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}
	/**
	 * @return the shippingStreet
	 */
	public String getShippingStreet() {
		return shippingStreet;
	}
	/**
	 * @param shippingStreet the shippingStreet to set
	 */
	public void setShippingStreet(String shippingStreet) {
		this.shippingStreet = shippingStreet;
	}
	/**
	 * @return the shippingCity
	 */
	public String getShippingCity() {
		return shippingCity;
	}
	/**
	 * @param shippingCity the shippingCity to set
	 */
	public void setShippingCity(String shippingCity) {
		this.shippingCity = shippingCity;
	}
	/**
	 * @return the shippingState
	 */
	/*public String getShippingState() {
		return shippingState;
	}*/
	/**
	 * @param shippingState the shippingState to set
	 */
	/*public void setShippingState(String shippingState) {
		this.shippingState = shippingState;
	}*/
	/**
	 * @return the shippingPincode
	 */
	public String getShippingPincode() {
		return shippingPincode;
	}
	/**
	 * @param shippingPincode the shippingPincode to set
	 */
	public void setShippingPincode(String shippingPincode) {
		this.shippingPincode = shippingPincode;
	}
	/**
	 * @return the shippingCountry
	 */
	/*public String getShippingCountry() {
		return shippingCountry;
	}*/
	/**
	 * @param shippingCountry the shippingCountry to set
	 */
	/*public void setShippingCountry(String shippingCountry) {
		this.shippingCountry = shippingCountry;
	}*/
	/**
	 * @return the billingStateId
	 */
	public long getBillingStateId() {
		return billingStateId;
	}
	/**
	 * @return the billingSteateName
	 */
	public String getBillingSteateName() {
		return billingSteateName;
	}
	/**
	 * @return the shippingStateId
	 */
	public long getShippingStateId() {
		return shippingStateId;
	}
	/**
	 * @return the shippingStateName
	 */
	public String getShippingStateName() {
		return shippingStateName;
	}
	/**
	 * @return the billingCountryId
	 */
	public String getBillingCountryId() {
		return billingCountryId;
	}
	/**
	 * @return the billingcountryName
	 */
	public String getBillingcountryName() {
		return billingcountryName;
	}
	/**
	 * @return the shippingCountryId
	 */
	public String getShippingCountryId() {
		return shippingCountryId;
	}
	/**
	 * @return the shippingCountryName
	 */
	public String getShippingCountryName() {
		return shippingCountryName;
	}
	/**
	 * @param billingStateId the billingStateId to set
	 */
	public void setBillingStateId(long billingStateId) {
		this.billingStateId = billingStateId;
	}
	/**
	 * @param billingSteateName the billingSteateName to set
	 */
	public void setBillingSteateName(String billingSteateName) {
		this.billingSteateName = billingSteateName;
	}
	/**
	 * @param shippingStateId the shippingStateId to set
	 */
	public void setShippingStateId(long shippingStateId) {
		this.shippingStateId = shippingStateId;
	}
	/**
	 * @param shippingStateName the shippingStateName to set
	 */
	public void setShippingStateName(String shippingStateName) {
		this.shippingStateName = shippingStateName;
	}
	/**
	 * @param billingCountryId the billingCountryId to set
	 */
	public void setBillingCountryId(String billingCountryId) {
		this.billingCountryId = billingCountryId;
	}
	/**
	 * @param billingcountryName the billingcountryName to set
	 */
	public void setBillingcountryName(String billingcountryName) {
		this.billingcountryName = billingcountryName;
	}
	/**
	 * @param shippingCountryId the shippingCountryId to set
	 */
	public void setShippingCountryId(String shippingCountryId) {
		this.shippingCountryId = shippingCountryId;
	}
	/**
	 * @param shippingCountryName the shippingCountryName to set
	 */
	public void setShippingCountryName(String shippingCountryName) {
		this.shippingCountryName = shippingCountryName;
	}
	/**
	 * @return the parentAccountSapNumber
	 */
	public String getParentAccountSapNumber() {
		return parentAccountSapNumber;
	}
	/**
	 * @return the parentAccountName
	 */
	public String getParentAccountName() {
		return parentAccountName;
	}
	/**
	 * @return the parentCustomerId
	 */
	public long getParentCustomerId() {
		return parentCustomerId;
	}
	/**
	 * @return the parentCustomerName
	 */
	public String getParentCustomerName() {
		return parentCustomerName;
	}
	/**
	 * @param parentAccountSapNumber the parentAccountSapNumber to set
	 */
	public void setParentAccountSapNumber(String parentAccountSapNumber) {
		this.parentAccountSapNumber = parentAccountSapNumber;
	}
	/**
	 * @param parentAccountName the parentAccountName to set
	 */
	public void setParentAccountName(String parentAccountName) {
		this.parentAccountName = parentAccountName;
	}
	/**
	 * @param parentCustomerId the parentCustomerId to set
	 */
	public void setParentCustomerId(long parentCustomerId) {
		this.parentCustomerId = parentCustomerId;
	}
	/**
	 * @param parentCustomerName the parentCustomerName to set
	 */
	public void setParentCustomerName(String parentCustomerName) {
		this.parentCustomerName = parentCustomerName;
	}
	/**
	 * @return the createdById
	 */
	public String getCreatedById() {
		return createdById;
	}
	/**
	 * @return the createdByFirstName
	 */
	public String getCreatedByFirstName() {
		return createdByFirstName;
	}
	/**
	 * @return the createdByLastName
	 */
	public String getCreatedByLastName() {
		return createdByLastName;
	}
	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}
	/**
	 * @return the modifiyedById
	 */
	public String getModifiyedById() {
		return modifiyedById;
	}
	/**
	 * @return the modifiyedFirstName
	 */
	public String getModifiyedFirstName() {
		return modifiyedFirstName;
	}
	/**
	 * @return the modifiyedLastName
	 */
	public String getModifiyedLastName() {
		return modifiyedLastName;
	}
	/**
	 * @return the modifiyedDate
	 */
	public Date getModifiyedDate() {
		return modifiyedDate;
	}
	/**
	 * @param createdById the createdById to set
	 */
	public void setCreatedById(String createdById) {
		this.createdById = createdById;
	}
	/**
	 * @param createdByFirstName the createdByFirstName to set
	 */
	public void setCreatedByFirstName(String createdByFirstName) {
		this.createdByFirstName = createdByFirstName;
	}
	/**
	 * @param createdByLastName the createdByLastName to set
	 */
	public void setCreatedByLastName(String createdByLastName) {
		this.createdByLastName = createdByLastName;
	}
	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	/**
	 * @param modifiyedById the modifiyedById to set
	 */
	public void setModifiyedById(String modifiyedById) {
		this.modifiyedById = modifiyedById;
	}
	/**
	 * @param modifiyedFirstName the modifiyedFirstName to set
	 */
	public void setModifiyedFirstName(String modifiyedFirstName) {
		this.modifiyedFirstName = modifiyedFirstName;
	}
	/**
	 * @param modifiyedLastName the modifiyedLastName to set
	 */
	public void setModifiyedLastName(String modifiyedLastName) {
		this.modifiyedLastName = modifiyedLastName;
	}
	/**
	 * @param modifiyedDate the modifiyedDate to set
	 */
	public void setModifiyedDate(Date modifiyedDate) {
		this.modifiyedDate = modifiyedDate;
	}
	public String getAccountTypeName() {
		return accountTypeName;
	}
	public void setAccountTypeName(String accountTypeName) {
		this.accountTypeName = accountTypeName;
	}
	public int getAccountTypeId() {
		return accountTypeId;
	}
	public void setAccountTypeId(int accountTypeId) {
		this.accountTypeId = accountTypeId;
	}
	public String getAccountCatagoryName() {
		return accountCatagoryName;
	}
	public void setAccountCatagoryName(String accountCatagoryName) {
		this.accountCatagoryName = accountCatagoryName;
	}
	public int getAccountCategoryId() {
		return accountCategoryId;
	}
	public void setAccountCategoryId(int accountCategoryId) {
		this.accountCategoryId = accountCategoryId;
	}
	public String getCreatedByMiddleName() {
		return createdByMiddleName;
	}
	public void setCreatedByMiddleName(String createdByMiddleName) {
		this.createdByMiddleName = createdByMiddleName;
	}
	public String getModifiyedMiddleName() {
		return modifiyedMiddleName;
	}
	public void setModifiyedMiddleName(String modifiyedMiddleName) {
		this.modifiyedMiddleName = modifiyedMiddleName;
	}
	
	
}
