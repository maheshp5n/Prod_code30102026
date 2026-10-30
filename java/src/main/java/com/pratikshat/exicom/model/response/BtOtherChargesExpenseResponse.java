package com.pratikshat.exicom.model.response;

import java.util.Date;

import com.pratikshat.exicom.entity.BtOtherChargesExpense;
import com.pratikshat.exicom.entity.OtherChargesExpense;

public class BtOtherChargesExpenseResponse {

	long id;
	String manualId;

	long btExpenseManagementId;
	String btExpenseManagementManualId;
	
	int businessDomainId;
	String businessDomainName;

	int otherChargesTypeId;
	String otherChargesTypeName;

	Date date;
	String billNo;
	double amount;
	String remark;

	String createdByEmailId;
	String createdByFirstName;
	String createdByMiddleName;
	String createdByLastName;
	Date createDate;

	String modifiedByEmailId;
	String modifiedByFirstName;
	String modifiedByMiddleName;
	String modifiedByLastName;
	Date modifiedDate;

	String ownerEmailId;
	String ownerFirstName;
	String ownerMiddleName;
	String ownerLastName;
	
	double amountToDisbursed;
	
	public BtOtherChargesExpenseResponse() {}
	
	public BtOtherChargesExpenseResponse(BtOtherChargesExpense otherChargesExpense) {
		
		this.id = otherChargesExpense.getId();
		this.manualId = otherChargesExpense.getManualId();

		if (otherChargesExpense.getBtExpenseManagement() != null) {
			this.btExpenseManagementId = otherChargesExpense.getBtExpenseManagement().getId();
			this.btExpenseManagementManualId = otherChargesExpense.getBtExpenseManagement().getManualId();
			
			if(otherChargesExpense.getBtExpenseManagement().getBusinessDomain()!=null) {
				this.businessDomainId = otherChargesExpense.getBtExpenseManagement().getBusinessDomain().getBusinessDomainId();
				this.businessDomainName = otherChargesExpense.getBtExpenseManagement().getBusinessDomain().getBusinessDomainName();
			}
		}

		if (otherChargesExpense.getOtherChargesType() != null) {
			this.otherChargesTypeId = otherChargesExpense.getOtherChargesType().getId();
			this.otherChargesTypeName = otherChargesExpense.getOtherChargesType().getName();
		}

		this.date = otherChargesExpense.getDate();
		this.billNo = otherChargesExpense.getBillNo();
		this.amount = otherChargesExpense.getAmount();
		this.remark = otherChargesExpense.getRemark();
		this.amountToDisbursed = otherChargesExpense.getAmountToDisbursed();

		if (otherChargesExpense.getCreatedBy() != null) {
			this.createdByEmailId = otherChargesExpense.getCreatedBy().getEmailId();
			this.createdByFirstName = otherChargesExpense.getCreatedBy().getFirstName();
			this.createdByMiddleName = otherChargesExpense.getCreatedBy().getMiddleName();
			this.createdByLastName = otherChargesExpense.getCreatedBy().getLastName();
			this.createDate = otherChargesExpense.getCreatedDate();
		}

		if (otherChargesExpense.getLastModifiedBy() != null) {
			this.modifiedByEmailId = otherChargesExpense.getLastModifiedBy().getEmailId();
			this.modifiedByFirstName = otherChargesExpense.getLastModifiedBy().getFirstName();
			this.modifiedByMiddleName = otherChargesExpense.getLastModifiedBy().getMiddleName();
			this.modifiedByLastName = otherChargesExpense.getLastModifiedBy().getLastName();
			this.modifiedDate = otherChargesExpense.getLastModifiedDate();
		}

		if (otherChargesExpense.getOwner() != null) {
			this.ownerEmailId = otherChargesExpense.getOwner().getEmailId();
			this.ownerFirstName = otherChargesExpense.getOwner().getFirstName();
			this.ownerMiddleName = otherChargesExpense.getOwner().getMiddleName();
			this.ownerLastName = otherChargesExpense.getOwner().getLastName();
		}
	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getManualId() {
		return manualId;
	}


	public void setManualId(String manualId) {
		this.manualId = manualId;
	}


	public long getBtExpenseManagementId() {
		return btExpenseManagementId;
	}


	public void setBtExpenseManagementId(long btExpenseManagementId) {
		this.btExpenseManagementId = btExpenseManagementId;
	}


	public String getBtExpenseManagementManualId() {
		return btExpenseManagementManualId;
	}


	public void setBtExpenseManagementManualId(String btExpenseManagementManualId) {
		this.btExpenseManagementManualId = btExpenseManagementManualId;
	}


	public int getOtherChargesTypeId() {
		return otherChargesTypeId;
	}


	public void setOtherChargesTypeId(int otherChargesTypeId) {
		this.otherChargesTypeId = otherChargesTypeId;
	}


	public String getOtherChargesTypeName() {
		return otherChargesTypeName;
	}


	public void setOtherChargesTypeName(String otherChargesTypeName) {
		this.otherChargesTypeName = otherChargesTypeName;
	}


	public Date getDate() {
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
	}


	public String getBillNo() {
		return billNo;
	}


	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}


	public double getAmount() {
		return amount;
	}


	public void setAmount(double amount) {
		this.amount = amount;
	}


	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}


	public String getCreatedByEmailId() {
		return createdByEmailId;
	}


	public void setCreatedByEmailId(String createdByEmailId) {
		this.createdByEmailId = createdByEmailId;
	}


	public String getCreatedByFirstName() {
		return createdByFirstName;
	}


	public void setCreatedByFirstName(String createdByFirstName) {
		this.createdByFirstName = createdByFirstName;
	}


	public String getCreatedByLastName() {
		return createdByLastName;
	}


	public void setCreatedByLastName(String createdByLastName) {
		this.createdByLastName = createdByLastName;
	}


	public Date getCreateDate() {
		return createDate;
	}


	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}


	public String getModifiedByEmailId() {
		return modifiedByEmailId;
	}


	public void setModifiedByEmailId(String modifiedByEmailId) {
		this.modifiedByEmailId = modifiedByEmailId;
	}


	public String getModifiedByFirstName() {
		return modifiedByFirstName;
	}


	public void setModifiedByFirstName(String modifiedByFirstName) {
		this.modifiedByFirstName = modifiedByFirstName;
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


	public String getOwnerEmailId() {
		return ownerEmailId;
	}


	public void setOwnerEmailId(String ownerEmailId) {
		this.ownerEmailId = ownerEmailId;
	}


	public String getOwnerFirstName() {
		return ownerFirstName;
	}


	public void setOwnerFirstName(String ownerFirstName) {
		this.ownerFirstName = ownerFirstName;
	}


	public String getOwnerLastName() {
		return ownerLastName;
	}


	public void setOwnerLastName(String ownerLastName) {
		this.ownerLastName = ownerLastName;
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


	public String getCreatedByMiddleName() {
		return createdByMiddleName;
	}


	public void setCreatedByMiddleName(String createdByMiddleName) {
		this.createdByMiddleName = createdByMiddleName;
	}


	public String getModifiedByMiddleName() {
		return modifiedByMiddleName;
	}


	public void setModifiedByMiddleName(String modifiedByMiddleName) {
		this.modifiedByMiddleName = modifiedByMiddleName;
	}


	public String getOwnerMiddleName() {
		return ownerMiddleName;
	}


	public void setOwnerMiddleName(String ownerMiddleName) {
		this.ownerMiddleName = ownerMiddleName;
	}

	public double getAmountToDisbursed() {
		return amountToDisbursed;
	}

	public void setAmountToDisbursed(double amountToDisbursed) {
		this.amountToDisbursed = amountToDisbursed;
	}
	
	

}
