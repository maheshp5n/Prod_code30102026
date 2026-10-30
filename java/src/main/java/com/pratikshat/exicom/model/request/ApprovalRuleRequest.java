package com.pratikshat.exicom.model.request;

import java.util.List;

public class ApprovalRuleRequest {

	long id;
	String objectClass;
	int approvalStages;
	String descriptions;
	boolean isActive;
	String entryCriteria;
	List<String> initialSubmitterList;
	int businessDomainId;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getObjectClass() {
		return objectClass;
	}
	public void setObjectClass(String objectClass) {
		this.objectClass = objectClass;
	}
	public int getApprovalStages() {
		return approvalStages;
	}
	public void setApprovalStages(int approvalStages) {
		this.approvalStages = approvalStages;
	}
	public String getDescriptions() {
		return descriptions;
	}
	public void setDescriptions(String descriptions) {
		this.descriptions = descriptions;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public String getEntryCriteria() {
		return entryCriteria;
	}
	public void setEntryCriteria(String entryCriteria) {
		this.entryCriteria = entryCriteria;
	}
	
	public List<String> getInitialSubmitterList() {
		return initialSubmitterList;
	}
	public void setInitialSubmitterList(List<String> initialSubmitterList) {
		this.initialSubmitterList = initialSubmitterList;
	}
	public int getBusinessDomainId() {
		return businessDomainId;
	}
	public void setBusinessDomainId(int businessDomainId) {
		this.businessDomainId = businessDomainId;
	}
	
	
}
