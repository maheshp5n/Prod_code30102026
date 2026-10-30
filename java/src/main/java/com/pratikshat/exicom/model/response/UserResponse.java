package com.pratikshat.exicom.model.response;

import java.util.Date;

public class UserResponse {
	String userName;
	String accountName;
	long contactId;
	String contactFirstName;
	String contactLastName;
	String contactMiddleName;
	String accountSapNumber;
	String contactDesignation;
	String contactEmailid;
	String contactDetailsEmployeeCode;
	int buisnessDomain;
	String buisnessDomainName;
	boolean accountNotExpired;
	boolean accountNonLocked;
	boolean creadentialNonExpired;
	boolean enabled;
	String profileName;
	String managerEmailId;
	String managerFirstName;
	String managerMiddleName;
	String managerLastName;
	String approverManagerEmailId;
	String approverManagerFirstName;
	String approverManagerMiddleName;
	String approverManagerLastName;
	String userRole;
	
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
	
	public UserResponse() {}
	
	
	
	
	public UserResponse(String userName, String accountName, long contactId, String contactFirstName,
			String contactLastName, String contactMiddleName, String accountSapNumber, String contactDesignation,
			String contactEmailid, String contactDetailsEmployeeCode, int buisnessDomain, String buisnessDomainName,
			boolean accountNotExpired, boolean accountNonLocked, boolean creadentialNonExpired, boolean enabled,
			String profileName, String managerEmailId, String managerFirstName, String managerMiddleName, String managerLastName,
			String approverManagerEmailId, String approverManagerFirstName, String approverManagerMiddleName, String approverManagerLastName,
			String createdByFirstName, String createdByMiddleName, String createdByLastName,String createdByEmailId, Date createdDate,
			String modifiedByFirstName, String modifiedByMiddleName, String modifiedByLastName,String modifiedByEmailId ,Date modifiedDate
			) {
		
		this.userName = userName;
		this.accountName = accountName;
		this.contactId = contactId;
		this.contactFirstName = contactFirstName;
		this.contactLastName = contactLastName;
		this.contactMiddleName = contactMiddleName;
		this.accountSapNumber = accountSapNumber;
		this.contactDesignation = contactDesignation;
		this.contactEmailid = contactEmailid;
		this.contactDetailsEmployeeCode = contactDetailsEmployeeCode;
		this.buisnessDomain = buisnessDomain;
		this.buisnessDomainName = buisnessDomainName;
		this.accountNotExpired = accountNotExpired;
		this.accountNonLocked = accountNonLocked;
		this.creadentialNonExpired = creadentialNonExpired;
		this.enabled = enabled;
		this.profileName = profileName;
		this.managerEmailId = managerEmailId;
		this.managerFirstName = managerFirstName;
		this.managerMiddleName = managerMiddleName;
		this.managerLastName = managerLastName;
		this.approverManagerEmailId = approverManagerEmailId;
		this.approverManagerFirstName = approverManagerFirstName;
		this.approverManagerMiddleName = approverManagerMiddleName;
		this.approverManagerLastName = approverManagerLastName;
		this.createdByFirstName = createdByFirstName;
		this.createdByMiddleName = createdByMiddleName;
		this.createdByLastName = createdByLastName;
		this.createdDate = createdDate;
		this.createdByEmailId =createdByEmailId;
		this.modifiedByFirstName = modifiedByFirstName;
		this.modifiedByMiddleName = modifiedByMiddleName;
		this.modifiedByLastName = modifiedByLastName;
		this.modifiedDate = modifiedDate;
		this.modifiedByEmailId = modifiedByEmailId;
	}




	public UserResponse(com.pratikshat.exicom.entity.LoginUser user,com.pratikshat.exicom.entity.EmployeeDetails employee) {
		if(user!=null) {
			this.userName=user.getUsername();
			this.accountNotExpired=user.isAccountNonExpired();
			this.accountNonLocked=user.isAccountNonLocked();
			this.creadentialNonExpired=user.isCredentialsNonExpired();
			this.enabled=user.isEnabled();
		}
		if(employee!=null) {
			if(employee.getEmptype()!=null) {
				this.buisnessDomain=employee.getEmptype().getBusinessDomainId();
				this.buisnessDomainName=employee.getEmptype().getBusinessDomainName();
			}
			
			if(employee.getContact()!=null) {
				if(employee.getContact().getAccount()!=null) {
					this.accountName=employee.getContact().getAccount().getName();
					this.accountSapNumber=employee.getContact().getAccount().getSapCustomerNumber();
				}
				this.contactId=employee.getContact().getContactId();
				this.contactFirstName=employee.getContact().getFirstName();
				this.contactLastName=employee.getContact().getLastName();
				this.contactMiddleName=employee.getContact().getMiddleName();
				this.contactEmailid=employee.getContact().getEmailId();
				this.contactDetailsEmployeeCode=employee.getContact().getEmployeeCode();
			}
			if(employee.getUserProfile()!=null) {
				this.profileName=employee.getUserProfile().getProfileName();
			}
			if(employee.getApprovalManager()!=null) {
				this.approverManagerEmailId=employee.getApprovalManager().getEmailId();
				this.approverManagerFirstName=employee.getApprovalManager().getFirstName();
				this.approverManagerMiddleName=employee.getApprovalManager().getMiddleName();
				this.approverManagerLastName=employee.getApprovalManager().getLastName();
			}
			if(employee.getManager()!=null) {
				this.managerEmailId=employee.getManager().getEmailId();
				this.managerFirstName=employee.getManager().getFirstName();
				this.managerMiddleName=employee.getManager().getMiddleName();
				this.managerLastName=employee.getManager().getLastName();
			}
			
			if(employee.getCreatedBy()!=null) {
				this.createdByFirstName = employee.getCreatedBy().getFirstName();
				this.createdByMiddleName = employee.getCreatedBy().getMiddleName();
				this.createdByLastName = employee.getCreatedBy().getLastName();
				this.createdDate = employee.getCreatedDate();
				this.createdByEmailId = employee.getCreatedBy().getEmailId();
			}
			
			if(employee.getModifiedBy()!=null) {
				this.modifiedByFirstName = employee.getModifiedBy().getFirstName();
				this.modifiedByMiddleName = employee.getModifiedBy().getMiddleName();
				this.modifiedByLastName = employee.getModifiedBy().getLastName();
				this.modifiedDate = employee.getModifiedDate();
				this.modifiedByEmailId = employee.getModifiedBy().getEmailId();
			}
		}
	}
	
	
	public UserResponse(String userName, String accountName, long contactId, String contactFirstName,
			String contactLastName, String contactMiddleName, String accountSapNumber, String contactDesignation,
			String contactEmailid, String contactDetailsEmployeeCode, int buisnessDomain, String buisnessDomainName,
			boolean accountNotExpired, boolean accountNonLocked, boolean creadentialNonExpired, boolean enabled,
			String profileName, String managerEmailId, String managerFirstName, String managerMiddleName, String managerLastName,
			String approverManagerEmailId, String approverManagerFirstName, String approverManagerMiddleName, String approverManagerLastName,
			String createdByFirstName, String createdByMiddleName, String createdByLastName,String createdByEmailId, Date createdDate,
			String modifiedByFirstName, String modifiedByMiddleName, String modifiedByLastName,String modifiedByEmailId ,Date modifiedDate,
			String role
			) {
		
		this.userName = userName;
		this.accountName = accountName;
		this.contactId = contactId;
		this.contactFirstName = contactFirstName;
		this.contactLastName = contactLastName;
		this.contactMiddleName = contactMiddleName;
		this.accountSapNumber = accountSapNumber;
		this.contactDesignation = contactDesignation;
		this.contactEmailid = contactEmailid;
		this.contactDetailsEmployeeCode = contactDetailsEmployeeCode;
		this.buisnessDomain = buisnessDomain;
		this.buisnessDomainName = buisnessDomainName;
		this.accountNotExpired = accountNotExpired;
		this.accountNonLocked = accountNonLocked;
		this.creadentialNonExpired = creadentialNonExpired;
		this.enabled = enabled;
		this.profileName = profileName;
		this.managerEmailId = managerEmailId;
		this.managerFirstName = managerFirstName;
		this.managerMiddleName = managerMiddleName;
		this.managerLastName = managerLastName;
		this.approverManagerEmailId = approverManagerEmailId;
		this.approverManagerFirstName = approverManagerFirstName;
		this.approverManagerMiddleName = approverManagerMiddleName;
		this.approverManagerLastName = approverManagerLastName;
		this.createdByFirstName = createdByFirstName;
		this.createdByMiddleName = createdByMiddleName;
		this.createdByLastName = createdByLastName;
		this.createdDate = createdDate;
		this.createdByEmailId =createdByEmailId;
		this.modifiedByFirstName = modifiedByFirstName;
		this.modifiedByMiddleName = modifiedByMiddleName;
		this.modifiedByLastName = modifiedByLastName;
		this.modifiedDate = modifiedDate;
		this.modifiedByEmailId = modifiedByEmailId;
		this.userRole = role;
	}
	
	
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @return the accountName
	 */
	public String getAccountName() {
		return accountName;
	}
	/**
	 * @return the contactId
	 */
	public long getContactId() {
		return contactId;
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
	 * @return the accountSapNumber
	 */
	public String getAccountSapNumber() {
		return accountSapNumber;
	}
	/**
	 * @return the contactDesignation
	 */
	public String getContactDesignation() {
		return contactDesignation;
	}
	/**
	 * @return the contactEmailid
	 */
	public String getContactEmailid() {
		return contactEmailid;
	}
	/**
	 * @return the contactDetailsEmployeeCode
	 */
	public String getContactDetailsEmployeeCode() {
		return contactDetailsEmployeeCode;
	}
	/**
	 * @return the buisnessDomain
	 */
	public int getBuisnessDomain() {
		return buisnessDomain;
	}
	/**
	 * @return the accountNotExpired
	 */
	public boolean isAccountNotExpired() {
		return accountNotExpired;
	}
	/**
	 * @return the accountNonLocked
	 */
	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}
	/**
	 * @return the creadentialNonExpired
	 */
	public boolean isCreadentialNonExpired() {
		return creadentialNonExpired;
	}
	/**
	 * @return the enabled
	 */
	public boolean isEnabled() {
		return enabled;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @param accountName the accountName to set
	 */
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	/**
	 * @param contactId the contactId to set
	 */
	public void setContactId(long contactId) {
		this.contactId = contactId;
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
	 * @param accountSapNumber the accountSapNumber to set
	 */
	public void setAccountSapNumber(String accountSapNumber) {
		this.accountSapNumber = accountSapNumber;
	}
	/**
	 * @param contactDesignation the contactDesignation to set
	 */
	public void setContactDesignation(String contactDesignation) {
		this.contactDesignation = contactDesignation;
	}
	/**
	 * @param contactEmailid the contactEmailid to set
	 */
	public void setContactEmailid(String contactEmailid) {
		this.contactEmailid = contactEmailid;
	}
	/**
	 * @param contactDetailsEmployeeCode the contactDetailsEmployeeCode to set
	 */
	public void setContactDetailsEmployeeCode(String contactDetailsEmployeeCode) {
		this.contactDetailsEmployeeCode = contactDetailsEmployeeCode;
	}
	/**
	 * @param buisnessDomain the buisnessDomain to set
	 */
	public void setBuisnessDomain(int buisnessDomain) {
		this.buisnessDomain = buisnessDomain;
	}
	/**
	 * @param accountNotExpired the accountNotExpired to set
	 */
	public void setAccountNotExpired(boolean accountNotExpired) {
		this.accountNotExpired = accountNotExpired;
	}
	/**
	 * @param accountNonLocked the accountNonLocked to set
	 */
	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}
	/**
	 * @param creadentialNonExpired the creadentialNonExpired to set
	 */
	public void setCreadentialNonExpired(boolean creadentialNonExpired) {
		this.creadentialNonExpired = creadentialNonExpired;
	}
	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}


	public String getBuisnessDomainName() {
		return buisnessDomainName;
	}


	public void setBuisnessDomainName(String buisnessDomainName) {
		this.buisnessDomainName = buisnessDomainName;
	}


	public String getProfileName() {
		return profileName;
	}


	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}


	public String getManagerEmailId() {
		return managerEmailId;
	}


	public void setManagerEmailId(String managerEmailId) {
		this.managerEmailId = managerEmailId;
	}


	public String getManagerFirstName() {
		return managerFirstName;
	}


	public void setManagerFirstName(String managerFirstName) {
		this.managerFirstName = managerFirstName;
	}


	public String getManagerLastName() {
		return managerLastName;
	}


	public void setManagerLastName(String managerLastName) {
		this.managerLastName = managerLastName;
	}


	public String getApproverManagerEmailId() {
		return approverManagerEmailId;
	}


	public void setApproverManagerEmailId(String approverManagerEmailId) {
		this.approverManagerEmailId = approverManagerEmailId;
	}


	public String getApproverManagerFirstName() {
		return approverManagerFirstName;
	}


	public void setApproverManagerFirstName(String approverManagerFirstName) {
		this.approverManagerFirstName = approverManagerFirstName;
	}


	public String getApproverManagerLastName() {
		return approverManagerLastName;
	}


	public void setApproverManagerLastName(String approverManagerLastName) {
		this.approverManagerLastName = approverManagerLastName;
	}


	public String getUserRole() {
		return userRole;
	}


	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}




	public String getManagerMiddleName() {
		return managerMiddleName;
	}




	public void setManagerMiddleName(String managerMiddleName) {
		this.managerMiddleName = managerMiddleName;
	}




	public String getApproverManagerMiddleName() {
		return approverManagerMiddleName;
	}




	public void setApproverManagerMiddleName(String approverManagerMiddleName) {
		this.approverManagerMiddleName = approverManagerMiddleName;
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




	public Date getModifiedDate() {
		return modifiedDate;
	}




	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}




	public String getCreatedByEmailId() {
		return createdByEmailId;
	}




	public void setCreatedByEmailId(String createdByEmailId) {
		this.createdByEmailId = createdByEmailId;
	}




	public String getModifiedByEmailId() {
		return modifiedByEmailId;
	}




	public void setModifiedByEmailId(String modifiedByEmailId) {
		this.modifiedByEmailId = modifiedByEmailId;
	}
	
	
}
