package com.pratikshat.exicom.model.request;

public class ApprovalRequest {
	long approvalId;
	String action;
	String remark;
	String reAssigned;
	
	public ApprovalRequest() {}
	
	/**
	 * @return the approvalId
	 */
	public long getApprovalId() {
		return approvalId;
	}
	/**
	 * @return the action
	 */
	public String getAction() {
		return action;
	}
	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * @return the reAssigned
	 */
	public String getReAssigned() {
		return reAssigned;
	}
	/**
	 * @param approvalId the approvalId to set
	 */
	public void setApprovalId(long approvalId) {
		this.approvalId = approvalId;
	}
	/**
	 * @param action the action to set
	 */
	public void setAction(String action) {
		this.action = action;
	}
	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * @param reAssigned the reAssigned to set
	 */
	public void setReAssigned(String reAssigned) {
		this.reAssigned = reAssigned;
	}
	
}
