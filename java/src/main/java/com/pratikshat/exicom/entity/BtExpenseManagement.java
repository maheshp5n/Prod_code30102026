package com.pratikshat.exicom.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.pratikshat.exicom.interceptor.ObjectAuditListener;

@Entity
@javax.persistence.EntityListeners({AuditingEntityListener.class,ObjectAuditListener.class})
public class BtExpenseManagement extends Auditable<EmployeeDetails> implements Serializable,OldPresist<BtExpenseManagement> {
	 	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private long id;
	    @ManyToOne(fetch = FetchType.LAZY)
	    EmployeeDetails owner;
	    private String manualId;
	    
	    @ManyToOne(fetch = FetchType.LAZY)
		BusinessDomain businessDomain;
		
		@ManyToOne(fetch = FetchType.LAZY)
		ContactDetails contact;
		
		Date documentDate;
		
		@ManyToOne(fetch = FetchType.LAZY)
		ExpenseManagementExpensePurpose expensePurpose;
		
		@ManyToOne(fetch = FetchType.LAZY)
		ExpenseManagementExpenseType expenseType;
		
		Date tourStartDate;
		String tourStartTime;
		
		Date tourEndDate;
		String tourEndTime;
		
		@ManyToOne(fetch = FetchType.LAZY)
		ExpenseManagementCostCenter costCenter;
		
		String purposeOfVisit;
		
		@ManyToOne(fetch = FetchType.LAZY)
		ExpenseManagementStatus status;
		
		@Column(length=1000)
		String remark;
		String narration;
		
		@ManyToOne(fetch = FetchType.LAZY)
		ExpenseManagementBusinessArea businessArea;
		
		@ManyToOne(fetch = FetchType.LAZY)
		ExpenseManagementBusinessPlace businessPlace;
		
		boolean isPost;
		
		boolean isExpenseSynced;
		double advanceAmount;
		String visibleToFinance;
		
		double totalTourAmount;
		double totalLodgingAmount;
		double totalBoardingAmount;
		double totalBpAmount;
		double totalLocalConveyanceAmount;
		Double totalServiceAppointmentAmount;
		double totalOtherChargesAmount;
		double totalExpense;
		double totalTourAmountCompanyPaid;
		double totalLodgingCompanyPaid;
		double totalCompanyPaid;
		double netAmountToPay;
		
		double totalTourDays;
		double eligibleTourDays;
		double totalBoardingDays;
		double totalLodgingDays;
		
		boolean isSynced;
		
		boolean isDeleted;
		

		public BtExpenseManagement() {}
		public BtExpenseManagement(long id) {
			this.id = id;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public EmployeeDetails getOwner() {
			return owner;
		}

		public void setOwner(EmployeeDetails owner) {
			this.owner = owner;
		}

		public String getManualId() {
			return manualId;
		}

		public void setManualId(String manualId) {
			this.manualId = manualId;
		}

		public BusinessDomain getBusinessDomain() {
			return businessDomain;
		}

		public void setBusinessDomain(BusinessDomain businessDomain) {
			this.businessDomain = businessDomain;
		}

		public ContactDetails getContact() {
			return contact;
		}

		public void setContact(ContactDetails contact) {
			this.contact = contact;
		}

		public Date getDocumentDate() {
			return documentDate;
		}

		public void setDocumentDate(Date documentDate) {
			this.documentDate = documentDate;
		}

		public ExpenseManagementExpensePurpose getExpensePurpose() {
			return expensePurpose;
		}

		public void setExpensePurpose(ExpenseManagementExpensePurpose expensePurpose) {
			this.expensePurpose = expensePurpose;
		}

		public ExpenseManagementExpenseType getExpenseType() {
			return expenseType;
		}

		public void setExpenseType(ExpenseManagementExpenseType expenseType) {
			this.expenseType = expenseType;
		}

		public Date getTourStartDate() {
			return tourStartDate;
		}

		public void setTourStartDate(Date tourStartDate) {
			this.tourStartDate = tourStartDate;
		}

		public String getTourStartTime() {
			return tourStartTime;
		}

		public void setTourStartTime(String tourStartTime) {
			this.tourStartTime = tourStartTime;
		}

		public Date getTourEndDate() {
			return tourEndDate;
		}

		public void setTourEndDate(Date tourEndDate) {
			this.tourEndDate = tourEndDate;
		}

		public String getTourEndTime() {
			return tourEndTime;
		}

		public void setTourEndTime(String tourEndTime) {
			this.tourEndTime = tourEndTime;
		}

		public ExpenseManagementCostCenter getCostCenter() {
			return costCenter;
		}

		public void setCostCenter(ExpenseManagementCostCenter costCenter) {
			this.costCenter = costCenter;
		}

		public String getPurposeOfVisit() {
			return purposeOfVisit;
		}

		public void setPurposeOfVisit(String purposeOfVisit) {
			this.purposeOfVisit = purposeOfVisit;
		}

		public ExpenseManagementStatus getStatus() {
			return status;
		}

		public void setStatus(ExpenseManagementStatus status) {
			this.status = status;
		}

		public String getRemark() {
			return remark;
		}

		public void setRemark(String remark) {
			this.remark = remark;
		}

		public String getNarration() {
			return narration;
		}

		public void setNarration(String narration) {
			this.narration = narration;
		}

		public ExpenseManagementBusinessArea getBusinessArea() {
			return businessArea;
		}

		public void setBusinessArea(ExpenseManagementBusinessArea businessArea) {
			this.businessArea = businessArea;
		}

		public ExpenseManagementBusinessPlace getBusinessPlace() {
			return businessPlace;
		}

		public void setBusinessPlace(ExpenseManagementBusinessPlace businessPlace) {
			this.businessPlace = businessPlace;
		}

		public boolean isPost() {
			return isPost;
		}

		public void setPost(boolean isPost) {
			this.isPost = isPost;
		}

		public boolean isExpenseSynced() {
			return isExpenseSynced;
		}

		public void setExpenseSynced(boolean isExpenseSynced) {
			this.isExpenseSynced = isExpenseSynced;
		}

		public double getAdvanceAmount() {
			return advanceAmount;
		}

		public void setAdvanceAmount(double advanceAmount) {
			this.advanceAmount = advanceAmount;
		}

		public String getVisibleToFinance() {
			return visibleToFinance;
		}

		public void setVisibleToFinance(String visibleToFinance) {
			this.visibleToFinance = visibleToFinance;
		}

		public double getTotalTourAmount() {
			return totalTourAmount;
		}

		public void setTotalTourAmount(double totalTourAmount) {
			this.totalTourAmount = totalTourAmount;
		}

		public double getTotalLodgingAmount() {
			return totalLodgingAmount;
		}

		public void setTotalLodgingAmount(double totalLodgingAmount) {
			this.totalLodgingAmount = totalLodgingAmount;
		}

		public double getTotalBoardingAmount() {
			return totalBoardingAmount;
		}

		public void setTotalBoardingAmount(double totalBoardingAmount) {
			this.totalBoardingAmount = totalBoardingAmount;
		}

		public double getTotalBpAmount() {
			return totalBpAmount;
		}

		public void setTotalBpAmount(double totalBpAmount) {
			this.totalBpAmount = totalBpAmount;
		}

		public double getTotalLocalConveyanceAmount() {
			return totalLocalConveyanceAmount;
		}

		public void setTotalLocalConveyanceAmount(double totalLocalConveyanceAmount) {
			this.totalLocalConveyanceAmount = totalLocalConveyanceAmount;
		}

		public Double getTotalServiceAppointmentAmount() {
			return totalServiceAppointmentAmount;
		}

		public void setTotalServiceAppointmentAmount(Double totalServiceAppointmentAmount) {
			this.totalServiceAppointmentAmount = totalServiceAppointmentAmount;
		}

		public double getTotalOtherChargesAmount() {
			return totalOtherChargesAmount;
		}

		public void setTotalOtherChargesAmount(double totalOtherChargesAmount) {
			this.totalOtherChargesAmount = totalOtherChargesAmount;
		}

		public double getTotalExpense() {
			return totalExpense;
		}

		public void setTotalExpense(double totalExpense) {
			this.totalExpense = totalExpense;
		}

		public double getTotalTourAmountCompanyPaid() {
			return totalTourAmountCompanyPaid;
		}

		public void setTotalTourAmountCompanyPaid(double totalTourAmountCompanyPaid) {
			this.totalTourAmountCompanyPaid = totalTourAmountCompanyPaid;
		}

		public double getTotalLodgingCompanyPaid() {
			return totalLodgingCompanyPaid;
		}

		public void setTotalLodgingCompanyPaid(double totalLodgingCompanyPaid) {
			this.totalLodgingCompanyPaid = totalLodgingCompanyPaid;
		}

		public double getTotalCompanyPaid() {
			return totalCompanyPaid;
		}

		public void setTotalCompanyPaid(double totalCompanyPaid) {
			this.totalCompanyPaid = totalCompanyPaid;
		}

		public double getNetAmountToPay() {
			return netAmountToPay;
		}

		public void setNetAmountToPay(double netAmountToPay) {
			this.netAmountToPay = netAmountToPay;
		}

		public double getTotalTourDays() {
			return totalTourDays;
		}

		public void setTotalTourDays(double totalTourDays) {
			this.totalTourDays = totalTourDays;
		}

		public double getEligibleTourDays() {
			return eligibleTourDays;
		}

		public void setEligibleTourDays(double eligibleTourDays) {
			this.eligibleTourDays = eligibleTourDays;
		}

		public double getTotalBoardingDays() {
			return totalBoardingDays;
		}

		public void setTotalBoardingDays(double totalBoardingDays) {
			this.totalBoardingDays = totalBoardingDays;
		}

		public double getTotalLodgingDays() {
			return totalLodgingDays;
		}

		public void setTotalLodgingDays(double totalLodgingDays) {
			this.totalLodgingDays = totalLodgingDays;
		}

		public boolean isSynced() {
			return isSynced;
		}

		public void setSynced(boolean isSynced) {
			this.isSynced = isSynced;
		}

		public boolean isDeleted() {
			return isDeleted;
		}

		public void setDeleted(boolean isDeleted) {
			this.isDeleted = isDeleted;
		}

		@javax.persistence.Transient transient BtExpenseManagement old;
		@Override
		@javax.persistence.PostLoad
		public void old() {
			old=(BtExpenseManagement)org.apache.commons.lang.SerializationUtils.clone(this);
		}
		@Override
		public BtExpenseManagement gOld() {
			return old;
		}
		public String toString() {
				return ""+id;
		}
		

				
		
}
