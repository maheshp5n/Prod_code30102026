package com.pratikshat.exicom.model.request;

public class CircleDetailsRequest {
	String circleName;
	String countryCode;
//	long circleManagerBattryId;
//	long circleManagerPowerId;
//	long circleManagerEv;
	
	String zoneName;
	long legalEntity;
	
	/**
	 * @return the circleName
	 */
	public String getCircleName() {
		return circleName;
	}
	/**
	 * @return the countryCode
	 */
	public String getCountryCode() {
		return countryCode;
	}
	/**
	 * @return the circleManagerBattryId
	 */
	
	/**
	 * @return the zoneName
	 */
	public String getZoneName() {
		return zoneName;
	}
	/**
	 * @param circleName the circleName to set
	 */
	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}
	/**
	 * @param countryCode the countryCode to set
	 */
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	/**
	 * @param circleManagerBattryId the circleManagerBattryId to set
	 */
	
	/**
	 * @param zoneName the zoneName to set
	 */
	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}
//	public long getCircleManagerBattryId() {
//		return circleManagerBattryId;
//	}
//	public void setCircleManagerBattryId(long circleManagerBattryId) {
//		this.circleManagerBattryId = circleManagerBattryId;
//	}
//	public long getCircleManagerPowerId() {
//		return circleManagerPowerId;
//	}
//	public void setCircleManagerPowerId(long circleManagerPowerId) {
//		this.circleManagerPowerId = circleManagerPowerId;
//	}
//	public long getCircleManagerEv() {
//		return circleManagerEv;
//	}
//	public void setCircleManagerEv(long circleManagerEv) {
//		this.circleManagerEv = circleManagerEv;
//	}
	public long getLegalEntity() {
		return legalEntity;
	}
	public void setLegalEntity(long legalEntity) {
		this.legalEntity = legalEntity;
	}
	
}
