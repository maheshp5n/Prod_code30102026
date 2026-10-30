package com.pratikshat.exicom.model.request;

public class BtServiceAppointmentExpRequest {

	
	private long btServiceAppointmentExpId;
	private String costCenter;
	private double totalClaimAmount;
	private long btServiceAppointmentId;
	private long btExpId;
	private double claimAmount;
	private String remark;
	private double amountToDisbursed;
	
	/**
	 * @return the btServiceAppointmentExpId
	 */
	public long getBtServiceAppointmentExpId() {
		return btServiceAppointmentExpId;
	}
	/**
	 * @param btServiceAppointmentExpId the btServiceAppointmentExpId to set
	 */
	public void setBtServiceAppointmentExpId(long btServiceAppointmentExpId) {
		this.btServiceAppointmentExpId = btServiceAppointmentExpId;
	}
	/**
	 * @return the costCenter
	 */
	public String getCostCenter() {
		return costCenter;
	}
	/**
	 * @param costCenter the costCenter to set
	 */
	public void setCostCenter(String costCenter) {
		this.costCenter = costCenter;
	}
	/**
	 * @return the totalClaimAmount
	 */
	public double getTotalClaimAmount() {
		return totalClaimAmount;
	}
	/**
	 * @param totalClaimAmount the totalClaimAmount to set
	 */
	public void setTotalClaimAmount(double totalClaimAmount) {
		this.totalClaimAmount = totalClaimAmount;
	}
	/**
	 * @return the btServiceAppointmentId
	 */
	public long getBtServiceAppointmentId() {
		return btServiceAppointmentId;
	}
	/**
	 * @param btServiceAppointmentId the btServiceAppointmentId to set
	 */
	public void setBtServiceAppointmentId(long btServiceAppointmentId) {
		this.btServiceAppointmentId = btServiceAppointmentId;
	}
	/**
	 * @return the btExpId
	 */
	public long getBtExpId() {
		return btExpId;
	}
	/**
	 * @param btExpId the btExpId to set
	 */
	public void setBtExpId(long btExpId) {
		this.btExpId = btExpId;
	}
	/**
	 * @return the claimAmount
	 */
	public double getClaimAmount() {
		return claimAmount;
	}
	/**
	 * @param claimAmount the claimAmount to set
	 */
	public void setClaimAmount(double claimAmount) {
		this.claimAmount = claimAmount;
	}
	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public double getAmountToDisbursed() {
		return amountToDisbursed;
	}
	public void setAmountToDisbursed(double amountToDisbursed) {
		this.amountToDisbursed = amountToDisbursed;
	}
	@Override
	public String toString() {
		return "BtServiceAppointmentExpRequest [btServiceAppointmentExpId=" + btServiceAppointmentExpId
				+ ", costCenter=" + costCenter + ", totalClaimAmount=" + totalClaimAmount + ", btServiceAppointmentId="
				+ btServiceAppointmentId + ", btExpId=" + btExpId + ", claimAmount=" + claimAmount + ", remark="
				+ remark + ", amountToDisbursed=" + amountToDisbursed + "]";
	}
	
	
	
	
}
