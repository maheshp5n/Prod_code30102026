package com.pratikshat.exicom.model.request;

import java.util.List;

import com.pratikshat.exicom.entity.BtServiceAppointmentExpense;

public class BtServiceAppoinmentExpRequest {

	
	private long BtExpId;
	private String totalClaim;
	private String costCenter;
	List<BtServiceAppointmentExpense> btServiceAppoinmentList;
	/**
	 * @return the btExpId
	 */
	public long getBtExpId() {
		return BtExpId;
	}
	/**
	 * @param btExpId the btExpId to set
	 */
	public void setBtExpId(long btExpId) {
		BtExpId = btExpId;
	}
	/**
	 * @return the totalClaim
	 */
	public String getTotalClaim() {
		return totalClaim;
	}
	/**
	 * @param totalClaim the totalClaim to set
	 */
	public void setTotalClaim(String totalClaim) {
		this.totalClaim = totalClaim;
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
	 * @return the btServiceAppoinmentList
	 */
	public List<BtServiceAppointmentExpense> getBtServiceAppoinmentList() {
		return btServiceAppoinmentList;
	}
	/**
	 * @param btServiceAppoinmentList the btServiceAppoinmentList to set
	 */
	public void setBtServiceAppoinmentList(List<BtServiceAppointmentExpense> btServiceAppoinmentList) {
		this.btServiceAppoinmentList = btServiceAppoinmentList;
	}
	
	
	
}
