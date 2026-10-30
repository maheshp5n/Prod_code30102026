package com.pratikshat.exicom.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.pratikshat.exicom.interceptor.ObjectAuditListener;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class BtExpenseAccountsOnly implements java.io.Serializable,OldPresist<BtExpenseAccountsOnly> {

	@Id
	@OneToOne(fetch=FetchType.LAZY)
	BtExpenseManagement expense;
	
	Date documentDate;
	
	@Column(length=500)
	String narration;
	
	@ManyToOne(fetch=FetchType.EAGER)
	ExpenseManagementBusinessArea businessArea;
	
	@ManyToOne(fetch=FetchType.EAGER)
	ExpenseManagementBusinessPlace businessPlace;
	
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails createdBy;
	
	@ManyToOne(fetch=FetchType.LAZY)
	EmployeeDetails lastModifiedBy;
	
	Date createdDate;
	Date lastModifiedDate;
	
	
	
	boolean postPark=false;



	/**
	 * @return the expense
	 */
	public BtExpenseManagement getExpense() {
		return expense;
	}



	/**
	 * @param expense the expense to set
	 */
	public void setExpense(BtExpenseManagement expense) {
		this.expense = expense;
	}



	/**
	 * @return the documentDate
	 */
	public Date getDocumentDate() {
		return documentDate;
	}



	/**
	 * @param documentDate the documentDate to set
	 */
	public void setDocumentDate(Date documentDate) {
		this.documentDate = documentDate;
	}



	/**
	 * @return the narration
	 */
	public String getNarration() {
		return narration;
	}



	/**
	 * @param narration the narration to set
	 */
	public void setNarration(String narration) {
		this.narration = narration;
	}



	/**
	 * @return the businessArea
	 */
	public ExpenseManagementBusinessArea getBusinessArea() {
		return businessArea;
	}



	/**
	 * @param businessArea the businessArea to set
	 */
	public void setBusinessArea(ExpenseManagementBusinessArea businessArea) {
		this.businessArea = businessArea;
	}



	/**
	 * @return the businessPlace
	 */
	public ExpenseManagementBusinessPlace getBusinessPlace() {
		return businessPlace;
	}



	/**
	 * @param businessPlace the businessPlace to set
	 */
	public void setBusinessPlace(ExpenseManagementBusinessPlace businessPlace) {
		this.businessPlace = businessPlace;
	}



	/**
	 * @return the createdBy
	 */
	public EmployeeDetails getCreatedBy() {
		return createdBy;
	}



	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(EmployeeDetails createdBy) {
		this.createdBy = createdBy;
	}



	/**
	 * @return the lastModifiedBy
	 */
	public EmployeeDetails getLastModifiedBy() {
		return lastModifiedBy;
	}



	/**
	 * @param lastModifiedBy the lastModifiedBy to set
	 */
	public void setLastModifiedBy(EmployeeDetails lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}



	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}



	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}



	/**
	 * @return the lastModifiedDate
	 */
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}



	/**
	 * @param lastModifiedDate the lastModifiedDate to set
	 */
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}



	/**
	 * @return the postPark
	 */
	public boolean isPostPark() {
		return postPark;
	}



	/**
	 * @param postPark the postPark to set
	 */
	public void setPostPark(boolean postPark) {
		this.postPark = postPark;
	}
	
	@javax.persistence.Transient transient BtExpenseAccountsOnly old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(BtExpenseAccountsOnly)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public BtExpenseAccountsOnly gOld() {
		return old;
	}
	public String toString() {
			return ""+expense;
	}
	
}
