package com.pratikshat.exicom.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class AccountRecentVisit implements java.io.Serializable,OldPresist<AccountRecentVisit>{
	@Id
	String accountSapNumber;
	String visitorEmailId;
	Date visistdate;
	public AccountRecentVisit() {}
	/**
	 * @param accountSapNumber
	 * @param visitorEmailId
	 * @param visistdate
	 */
	public AccountRecentVisit(String accountSapNumber, String visitorEmailId, Date visistdate) {
		this.accountSapNumber = accountSapNumber;
		this.visitorEmailId = visitorEmailId;
		this.visistdate = visistdate;
	}
	/**
	 * @return the accountSapNumber
	 */
	public String getAccountSapNumber() {
		return accountSapNumber;
	}
	/**
	 * @param accountSapNumber the accountSapNumber to set
	 */
	public void setAccountSapNumber(String accountSapNumber) {
		this.accountSapNumber = accountSapNumber;
	}
	/**
	 * @return the visitorEmailId
	 */
	public String getVisitorEmailId() {
		return visitorEmailId;
	}
	/**
	 * @param visitorEmailId the visitorEmailId to set
	 */
	public void setVisitorEmailId(String visitorEmailId) {
		this.visitorEmailId = visitorEmailId;
	}
	/**
	 * @return the visistdate
	 */
	public Date getVisistdate() {
		return visistdate;
	}
	/**
	 * @param visistdate the visistdate to set
	 */
	public void setVisistdate(Date visistdate) {
		this.visistdate = visistdate;
	}
	

	@javax.persistence.Transient transient AccountRecentVisit old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(AccountRecentVisit)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public AccountRecentVisit gOld() {
		return old;
	}
	public String toString() {
			return ""+accountSapNumber;
	}
}
