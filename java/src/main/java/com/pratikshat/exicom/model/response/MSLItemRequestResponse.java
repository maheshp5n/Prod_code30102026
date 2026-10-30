package com.pratikshat.exicom.model.response;

public class MSLItemRequestResponse {
	long mslRequestItemId;
	String partCode;
	String partDescription;
	float newMSL;
	float newHSL;
	
	public MSLItemRequestResponse () {}
	public MSLItemRequestResponse(long mslRequestItemId,String partCode,String partDescription,float newMSL,float newHSL) {
		this.mslRequestItemId=mslRequestItemId;
		this.partCode=partCode;
		this.partDescription=partDescription;
		this.newMSL=newMSL;
		this.newHSL=newHSL;
	}
	
	/**
	 * @return the mslRequestItemId
	 */
	public long getMslRequestItemId() {
		return mslRequestItemId;
	}
	/**
	 * @return the partCode
	 */
	public String getPartCode() {
		return partCode;
	}
	/**
	 * @return the partDescription
	 */
	public String getPartDescription() {
		return partDescription;
	}
	/**
	 * @return the newMSL
	 */
	public float getNewMSL() {
		return newMSL;
	}
	/**
	 * @return the newHSL
	 */
	public float getNewHSL() {
		return newHSL;
	}
	/**
	 * @param mslRequestItemId the mslRequestItemId to set
	 */
	public void setMslRequestItemId(long mslRequestItemId) {
		this.mslRequestItemId = mslRequestItemId;
	}
	/**
	 * @param partCode the partCode to set
	 */
	public void setPartCode(String partCode) {
		this.partCode = partCode;
	}
	/**
	 * @param partDescription the partDescription to set
	 */
	public void setPartDescription(String partDescription) {
		this.partDescription = partDescription;
	}
	/**
	 * @param newMSL the newMSL to set
	 */
	public void setNewMSL(float newMSL) {
		this.newMSL = newMSL;
	}
	/**
	 * @param newHSL the newHSL to set
	 */
	public void setNewHSL(float newHSL) {
		this.newHSL = newHSL;
	}
	
}
