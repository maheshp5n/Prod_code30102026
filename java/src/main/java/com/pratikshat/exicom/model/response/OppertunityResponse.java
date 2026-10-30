package com.pratikshat.exicom.model.response;

import java.util.Date;

import com.pratikshat.exicom.entity.Oppertunity;

public class OppertunityResponse {
	
	long oppertunityId;
	String oppertunityNumber;
	String oppertunityOwnerFirtName;
	String oppertunityOwnerMiddleName;
	String oppertunityOwnerlastName;
	String oppertunityOwnerEmailId;
	
	String accountName;
	String accountId;
	
	long caseId;
	String caseNumber;
	
	String siteName;
	long siteId;
	
	String forcastCategory;
	long forcastCategoryId;
	
	String stage;
	long stageId;
	
	String type;
	long typeId;
	
	Date closeDate;
	double probability;
	double ammount;
	double expectedRevenue;
	
	String createdByFirstName;
	String createdByMiddleName;
	String createByLastName;
	String createdByEmailId;
	Date createdDate;
	
	String modifiedByFirstName;
	String modifiedByMiddleName;
	String modifiedByLastName;
	String modifiedByEmailId;
	Date modifiedDate;
	
	public OppertunityResponse(Oppertunity oppertunity) {
		this.oppertunityId=oppertunity.getOppertunityId();
		this.oppertunityNumber = oppertunity.getOppertunityNumber();
		if(oppertunity.getOwnerDetails()!=null) {
			this.oppertunityOwnerFirtName = oppertunity.getOwnerDetails().getFirstName();
			this.oppertunityOwnerMiddleName = oppertunity.getOwnerDetails().getMiddleName();
			this.oppertunityOwnerlastName = oppertunity.getOwnerDetails().getLastName();
			this.oppertunityOwnerEmailId = oppertunity.getOwnerDetails().getEmailId();
		}
		
		if(oppertunity.getAccounts()!=null) {
			this.accountId = oppertunity.getAccounts().getSapCustomerNumber();
			this.accountName = oppertunity.getAccounts().getName();
		}
		
		if(oppertunity.getCaseDetails()!=null) {
			this.caseId = oppertunity.getCaseDetails().getCaseId();
			this.caseNumber = oppertunity.getCaseDetails().getManualId();
			
			if(oppertunity.getCaseDetails().getAssets()!=null) {
				if(oppertunity.getCaseDetails().getAssets().getSite()!=null) {
					this.siteId = oppertunity.getCaseDetails().getAssets().getSite().getSiteId();
					this.siteName = oppertunity.getCaseDetails().getAssets().getSite().getSiteName();
				}
				
			}
		}
		
		if( oppertunity.getForcastCategory()!=null) {
			this.forcastCategory = oppertunity.getForcastCategory().getOppertunityForecastCategoryName();
			this.forcastCategoryId = oppertunity.getForcastCategory().getOppertunityForecastCategoryId();
		}
		
		if(oppertunity.getOppertunityStage()!=null) {
			this.stage = oppertunity.getOppertunityStage().getOppertunityStageName();
			this.stageId = oppertunity.getOppertunityStage().getOppertunityStageId();
		}
		
		if(oppertunity.getOppertunityType()!=null) {
			this.type= oppertunity.getOppertunityType().getOppertunityTypeName();
			this.typeId = oppertunity.getOppertunityType().getOppertunityTypeId();
		}
		
		this.closeDate = oppertunity.getCloseDate();
		this.probability = oppertunity.getProbability();
		this.ammount = oppertunity.getAmmount();
		this.expectedRevenue = oppertunity.getExpectedRevenue();
		
		if(oppertunity.getCreatedBy()!=null) {
			this.createdByFirstName = oppertunity.getCreatedBy().getFirstName();
			this.createdByMiddleName = oppertunity.getCreatedBy().getMiddleName();
			this.createByLastName = oppertunity.getCreatedBy().getLastName();
			this.createdByEmailId = oppertunity.getCreatedBy().getEmailId();
			this.createdDate = oppertunity.getCreateDate();
		}
		
		
		if(oppertunity.getModifiedBy()!=null) {
			this.modifiedByFirstName = oppertunity.getModifiedBy().getFirstName();
			this.modifiedByMiddleName = oppertunity.getModifiedBy().getMiddleName();
			this.modifiedByLastName = oppertunity.getModifiedBy().getLastName();
			this.modifiedByEmailId = oppertunity.getModifiedBy().getEmailId();
			this.modifiedDate = oppertunity.getModifiedDate();
		}
		
	}
	
	

	public OppertunityResponse() {
		
	}



	public long getOppertunityId() {
		return oppertunityId;
	}

	public void setOppertunityId(long oppertunityId) {
		this.oppertunityId = oppertunityId;
	}

	public String getOppertunityNumber() {
		return oppertunityNumber;
	}

	public void setOppertunityNumber(String oppertunityNumber) {
		this.oppertunityNumber = oppertunityNumber;
	}

	public String getOppertunityOwnerFirtName() {
		return oppertunityOwnerFirtName;
	}

	public void setOppertunityOwnerFirtName(String oppertunityOwnerFirtName) {
		this.oppertunityOwnerFirtName = oppertunityOwnerFirtName;
	}

	public String getOppertunityOwnerlastName() {
		return oppertunityOwnerlastName;
	}

	public void setOppertunityOwnerlastName(String oppertunityOwnerlastName) {
		this.oppertunityOwnerlastName = oppertunityOwnerlastName;
	}

	public String getOppertunityOwnerEmailId() {
		return oppertunityOwnerEmailId;
	}

	public void setOppertunityOwnerEmailId(String oppertunityOwnerEmailId) {
		this.oppertunityOwnerEmailId = oppertunityOwnerEmailId;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public long getCaseId() {
		return caseId;
	}

	public void setCaseId(long caseId) {
		this.caseId = caseId;
	}

	public String getCaseNumber() {
		return caseNumber;
	}

	public void setCaseNumber(String caseNumber) {
		this.caseNumber = caseNumber;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public long getSiteId() {
		return siteId;
	}

	public void setSiteId(long siteId) {
		this.siteId = siteId;
	}

	public String getForcastCategory() {
		return forcastCategory;
	}

	public void setForcastCategory(String forcastCategory) {
		this.forcastCategory = forcastCategory;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getCloseDate() {
		return closeDate;
	}

	public void setCloseDate(Date closeDate) {
		this.closeDate = closeDate;
	}

	public double getProbability() {
		return probability;
	}

	public void setProbability(double probability) {
		this.probability = probability;
	}

	public double getAmmount() {
		return ammount;
	}

	public void setAmmount(double ammount) {
		this.ammount = ammount;
	}

	public double getExpectedRevenue() {
		return expectedRevenue;
	}

	public void setExpectedRevenue(double expectedRevenue) {
		this.expectedRevenue = expectedRevenue;
	}

	public String getCreatedByFirstName() {
		return createdByFirstName;
	}

	public void setCreatedByFirstName(String createdByFirstName) {
		this.createdByFirstName = createdByFirstName;
	}

	public String getCreateByLastName() {
		return createByLastName;
	}

	public void setCreateByLastName(String createByLastName) {
		this.createByLastName = createByLastName;
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



	public long getForcastCategoryId() {
		return forcastCategoryId;
	}



	public void setForcastCategoryId(long forcastCategoryId) {
		this.forcastCategoryId = forcastCategoryId;
	}



	public long getStageId() {
		return stageId;
	}



	public void setStageId(long stageId) {
		this.stageId = stageId;
	}



	public long getTypeId() {
		return typeId;
	}



	public void setTypeId(long typeId) {
		this.typeId = typeId;
	}



	public String getOppertunityOwnerMiddleName() {
		return oppertunityOwnerMiddleName;
	}



	public void setOppertunityOwnerMiddleName(String oppertunityOwnerMiddleName) {
		this.oppertunityOwnerMiddleName = oppertunityOwnerMiddleName;
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
	
	
	
	
	

	
}
