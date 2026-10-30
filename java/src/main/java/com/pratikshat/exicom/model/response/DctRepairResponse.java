package com.pratikshat.exicom.model.response;

import java.util.Date;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import com.pratikshat.exicom.entity.DctRepair;
import com.pratikshat.exicom.entity.Products;

public class DctRepairResponse {	
	long dctRepairId;
	
	String serialNumber;
	String productPartcode;
	String productDescription;
	
	Date repariDate;
	
	String repairByFirstName;
	String repairByMiddleName;
	String repairByLastName;
	String repairByEmailId;
	
	String createdByFirstName;
	String createdByMiddleName;
	String createdByLastName;
	String createdByEmailId;
	
	String modifiedByFirstName;
	String modifiedByMiddleName;
	String modifiedByLastName;
	String modifiedByEmailId;
	
	String ownerFirstName;
	String ownerMiddleName;
	String ownerLastName;
	String ownerEmailId;
	
	Date createdDate;
	Date lastModifiedDate;
	
	long previousStateId;
	String previousStateName;
	
	long currentStateId;
	String currentStateName;
	
	double totalRepairAmount;
	
	String jobCardManualId;
	Long jobCardId;
	

	public DctRepairResponse() {
		
	}
	
	
	public DctRepairResponse(DctRepair dctRepair) {
		
		if(dctRepair!=null) {
			this.dctRepairId = dctRepair.getId();
			this.totalRepairAmount = dctRepair.getTotalRepairAmount();
			this.serialNumber = dctRepair.getSerialNumber();
			if(dctRepair.getfProduct()!=null) {
				this.productPartcode = dctRepair.getfProduct().getProductPartCode();
				this.productDescription = dctRepair.getfProduct().getPdoductDescription();
			}
			
			if(dctRepair.getRepairBy()!=null) {
				this.repairByFirstName = dctRepair.getRepairBy().getFirstName();
				this.repairByMiddleName = dctRepair.getRepairBy().getMiddleName();
				this.repairByLastName = dctRepair.getRepairBy().getLastName();
				this.repairByEmailId = dctRepair.getRepairBy().getEmailId();
				
				this.repariDate = dctRepair.getRepariDate();
			}
			
			if(dctRepair.getCreatedBy()!=null) {
				this.createdByFirstName = dctRepair.getCreatedBy().getFirstName();
				this.createdByMiddleName = dctRepair.getCreatedBy().getMiddleName();
				this.createdByLastName = dctRepair.getCreatedBy().getLastName();
				this.createdByEmailId = dctRepair.getCreatedBy().getEmailId();
				
				this.createdDate = dctRepair.getCreatedDate();
			}
			
			if(dctRepair.getModifiedBy()!=null) {
				this.modifiedByFirstName = dctRepair.getModifiedBy().getFirstName();
				this.modifiedByMiddleName = dctRepair.getModifiedBy().getMiddleName();
				this.modifiedByLastName = dctRepair.getModifiedBy().getLastName();
				this.modifiedByEmailId = dctRepair.getModifiedBy().getEmailId();
				
				this.lastModifiedDate = dctRepair.getLastModifiedDate();	
			}
			
			if(dctRepair.getOwner()!=null) {
				this.ownerFirstName = dctRepair.getOwner().getFirstName();
				this.ownerMiddleName = dctRepair.getOwner().getMiddleName();
				this.ownerLastName = dctRepair.getOwner().getLastName();
				this.ownerEmailId = dctRepair.getOwner().getEmailId();	
			}
			
			if(dctRepair.getPreviousStatus()!=null) {
				this.previousStateId = dctRepair.getPreviousStatus().getProductStateId();
				this.previousStateName = dctRepair.getPreviousStatus().getProductStateName();	
			}
			
			if(dctRepair.getCurrentStatus()!=null) {
				this.currentStateId = dctRepair.getCurrentStatus().getProductStateId();
				this.currentStateName = dctRepair.getCurrentStatus().getProductStateName();	
			}
			
			if(dctRepair.getJobCard()!=null) {
				this.jobCardId=dctRepair.getJobCard().getId();
				this.jobCardManualId=dctRepair.getJobCard().getManualId();
			}
		}
	}
	

	public long getDctRepairId() {
		return dctRepairId;
	}

	public void setDctRepairId(long dctRepairId) {
		this.dctRepairId = dctRepairId;
	}

	public Date getRepariDate() {
		return repariDate;
	}

	public void setRepariDate(Date repariDate) {
		this.repariDate = repariDate;
	}

	public String getRepairByFirstName() {
		return repairByFirstName;
	}

	public void setRepairByFirstName(String repairByFirstName) {
		this.repairByFirstName = repairByFirstName;
	}

	public String getRepairByMiddleName() {
		return repairByMiddleName;
	}

	public void setRepairByMiddleName(String repairByMiddleName) {
		this.repairByMiddleName = repairByMiddleName;
	}

	public String getRepairByLastName() {
		return repairByLastName;
	}

	public void setRepairByLastName(String repairByLastName) {
		this.repairByLastName = repairByLastName;
	}

	public String getRepairByEmailId() {
		return repairByEmailId;
	}

	public void setRepairByEmailId(String repairByEmailId) {
		this.repairByEmailId = repairByEmailId;
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

	public String getOwnerFirstName() {
		return ownerFirstName;
	}


	public void setOwnerFirstName(String ownerFirstName) {
		this.ownerFirstName = ownerFirstName;
	}


	public String getOwnerMiddleName() {
		return ownerMiddleName;
	}


	public void setOwnerMiddleName(String ownerMiddleName) {
		this.ownerMiddleName = ownerMiddleName;
	}


	public String getOwnerLastName() {
		return ownerLastName;
	}


	public void setOwnerLastName(String ownerLastName) {
		this.ownerLastName = ownerLastName;
	}


	public String getOwnerEmailId() {
		return ownerEmailId;
	}


	public void setOwnerEmailId(String ownerEmailId) {
		this.ownerEmailId = ownerEmailId;
	}


	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public long getPreviousStateId() {
		return previousStateId;
	}

	public void setPreviousStateId(long previousStateId) {
		this.previousStateId = previousStateId;
	}

	public String getPreviousStateName() {
		return previousStateName;
	}

	public void setPreviousStateName(String previousStateName) {
		this.previousStateName = previousStateName;
	}

	public long getCurrentStateId() {
		return currentStateId;
	}

	public void setCurrentStateId(long currentStateId) {
		this.currentStateId = currentStateId;
	}

	public String getCurrentStateName() {
		return currentStateName;
	}

	public void setCurrentStateName(String currentStateName) {
		this.currentStateName = currentStateName;
	}

	public double getTotalRepairAmount() {
		return totalRepairAmount;
	}

	public void setTotalRepairAmount(double totalRepairAmount) {
		this.totalRepairAmount = totalRepairAmount;
	}


	public String getSerialNumber() {
		return serialNumber;
	}


	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}


	public String getProductPartcode() {
		return productPartcode;
	}


	public void setProductPartcode(String productPartcode) {
		this.productPartcode = productPartcode;
	}


	public String getProductDescription() {
		return productDescription;
	}


	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}


	public String getJobCardManualId() {
		return jobCardManualId;
	}


	public void setJobCardManualId(String jobCardManualId) {
		this.jobCardManualId = jobCardManualId;
	}


	public Long getJobCardId() {
		return jobCardId;
	}


	public void setJobCardId(Long jobCardId) {
		this.jobCardId = jobCardId;
	}
	
	
	
}
