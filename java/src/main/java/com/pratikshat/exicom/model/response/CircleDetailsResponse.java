package com.pratikshat.exicom.model.response;

import java.util.Date;

public class CircleDetailsResponse {
	String circleName;
	String accountOwnerId;
	String accountOwnerFirstName;
	String accountOwnerMiddleName;
	String accountOwnerLastName;
	String countryCode;
	String countryName;
	long circleManagerBattryId;
	String circleManagerBattryFirstName;
	String circleManagerBatteryMiddleName;
	String circleManagerBattryLastName;
	long circleManagerPowerId;
	String circleManagerPowerFirstName;
	String circleManagerPowerMiddleName;
	String circleManagerPowerLastName;
	long circleManagerEvId;
	String circleManagerEvFirstName;
	String circleManagerEvMiddleName;
	String circleManagerEvLastName;
	String createById;
	String createByFirstName;
	String createdByMiddleName;
	String createByLastName;
	Date createdDate; 
	String modifiedById;
	String modifiedByFirstName;
	String modifiedByMiddleName;
	String modifiedByLastName;
	Date lastModifiedDate;
	String zone;
	String zoneBattery;
	
	long powerLegalEntityId;
	String powerLegalEntityName;
	
	long batteryLegalEntityId;
	String batteryLegalEntityName;
	
	long evLegalEntityId;
	String evLegalEntityName;
	String legalEntityName;	

	public CircleDetailsResponse() {}
	
	public CircleDetailsResponse(com.pratikshat.exicom.entity.CircleDetails circleDetails) {
		this.circleName=circleDetails.getCircleName();
		if(circleDetails.getAccountOwner()!=null) {
			this.accountOwnerId=circleDetails.getAccountOwner().getEmailId();
			this.accountOwnerFirstName=circleDetails.getAccountOwner().getFirstName();
			this.accountOwnerMiddleName=circleDetails.getAccountOwner().getMiddleName();
			this.accountOwnerLastName=circleDetails.getAccountOwner().getLastName();
		}
		if(circleDetails.getCountry()!=null) {
			this.countryCode=circleDetails.getCountry().getConuntryId();
			this.countryName=circleDetails.getCountry().getCountryName();
		}
		if(circleDetails.getCircleManagerBattry()!=null) {
			this.circleManagerBattryId=circleDetails.getCircleManagerBattry().getContactId();
			this.circleManagerBattryFirstName=circleDetails.getCircleManagerBattry().getFirstName();
			this.circleManagerBatteryMiddleName=circleDetails.getCircleManagerBattry().getMiddleName();
			this.circleManagerBattryLastName=circleDetails.getCircleManagerBattry().getLastName();
			
			
		}
		if(circleDetails.getCircleManagerPower()!=null) {
			this.circleManagerPowerId=circleDetails.getCircleManagerPower().getContactId();
			this.circleManagerPowerFirstName=circleDetails.getCircleManagerPower().getFirstName();
			this.circleManagerPowerMiddleName=circleDetails.getCircleManagerPower().getMiddleName();
			this.circleManagerPowerLastName=circleDetails.getCircleManagerPower().getLastName();
			
		}
		
		if(circleDetails.getCircleManagerEv()!=null) {
			this.circleManagerEvId=circleDetails.getCircleManagerEv().getContactId();
			this.circleManagerEvFirstName=circleDetails.getCircleManagerEv().getFirstName();
			this.circleManagerEvMiddleName=circleDetails.getCircleManagerEv().getMiddleName();
			this.circleManagerEvLastName=circleDetails.getCircleManagerEv().getLastName();
			
		}
		if(circleDetails.getCreateBy()!=null) {
			this.createById=circleDetails.getCreateBy().getEmailId();
			this.createByFirstName=circleDetails.getCreateBy().getFirstName();
			this.createdByMiddleName=circleDetails.getCreateBy().getMiddleName();
			this.createByLastName=circleDetails.getCreateBy().getLastName();
		}
		this.createdDate=circleDetails.getCreatedDate();
		if(circleDetails.getModifiedBy()!=null) {
			this.modifiedById=circleDetails.getModifiedBy().getEmailId();
			this.modifiedByFirstName=circleDetails.getModifiedBy().getFirstName();
			this.modifiedByMiddleName=circleDetails.getModifiedBy().getMiddleName();
			this.modifiedByLastName=circleDetails.getModifiedBy().getLastName();
		}
		this.lastModifiedDate=circleDetails.getLastModifyedDate();
		if(circleDetails.getZone()!=null) {
			this.zone=circleDetails.getZone().getZoneName();
		}
		if(circleDetails.getZoneBattery()!=null){
			this.zoneBattery=circleDetails.getZoneBattery().getZoneName();
		}
		
		if(circleDetails.getServiceCenterPower()!=null) {
			this.powerLegalEntityId=circleDetails.getServiceCenterPower().getLegalEntityId();
			this.powerLegalEntityName=circleDetails.getServiceCenterPower().getLegalEntityName();	
		}
		
		if(circleDetails.getServiceCenterBattery()!=null) {
			this.batteryLegalEntityId=circleDetails.getServiceCenterBattery().getLegalEntityId();
			this.batteryLegalEntityName=circleDetails.getServiceCenterBattery().getLegalEntityName();	
		}
		
		if(circleDetails.getServiceCenterEv()!=null) {
			this.evLegalEntityId=circleDetails.getServiceCenterEv().getLegalEntityId();;
			this.evLegalEntityName=circleDetails.getServiceCenterEv().getLegalEntityName();	
		}
	}
	
	
	/**
	 * @return the zone
	 */
	public String getZone() {
		return zone;
	}

	/**
	 * @param zone the zone to set
	 */
	public void setZone(String zone) {
		this.zone = zone;
	}

	/**
	 * @return the circleName
	 */
	public String getCircleName() {
		return circleName;
	}
	/**
	 * @return the accountOwnerId
	 */
	public String getAccountOwnerId() {
		return accountOwnerId;
	}
	/**
	 * @return the accountOwnerFirstName
	 */
	public String getAccountOwnerFirstName() {
		return accountOwnerFirstName;
	}
	/**
	 * @return the accountOwnerLastName
	 */
	public String getAccountOwnerLastName() {
		return accountOwnerLastName;
	}
	/**
	 * @return the countryCode
	 */
	public String getCountryCode() {
		return countryCode;
	}
	/**
	 * @return the countryName
	 */
	public String getCountryName() {
		return countryName;
	}
	/**
	 * @return the circleManagerBattryId
	 */
	public long getCircleManagerBattryId() {
		return circleManagerBattryId;
	}
	/**
	 * @return the circleManagerBattryFirstName
	 */
	public String getCircleManagerBattryFirstName() {
		return circleManagerBattryFirstName;
	}
	/**
	 * @return the circleManagerBattryLastName
	 */
	public String getCircleManagerBattryLastName() {
		return circleManagerBattryLastName;
	}
	/**
	 * @return the circleManagerPowerId
	 */
	public long getCircleManagerPowerId() {
		return circleManagerPowerId;
	}
	/**
	 * @return the circleManagerPowerFirstName
	 */
	public String getCircleManagerPowerFirstName() {
		return circleManagerPowerFirstName;
	}
	/**
	 * @return the circleManagerPowerLastName
	 */
	public String getCircleManagerPowerLastName() {
		return circleManagerPowerLastName;
	}
	/**
	 * @return the createById
	 */
	public String getCreateById() {
		return createById;
	}
	/**
	 * @return the createByFirstName
	 */
	public String getCreateByFirstName() {
		return createByFirstName;
	}
	/**
	 * @return the createByLastName
	 */
	public String getCreateByLastName() {
		return createByLastName;
	}
	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}
	/**
	 * @return the modifiedById
	 */
	public String getModifiedById() {
		return modifiedById;
	}
	/**
	 * @return the modifiedByFirstName
	 */
	public String getModifiedByFirstName() {
		return modifiedByFirstName;
	}
	/**
	 * @return the modifiedByLastName
	 */
	public String getModifiedByLastName() {
		return modifiedByLastName;
	}
	/**
	 * @return the lastModifiedDate
	 */
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}
	/**
	 * @param circleName the circleName to set
	 */
	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}
	/**
	 * @param accountOwnerId the accountOwnerId to set
	 */
	public void setAccountOwnerId(String accountOwnerId) {
		this.accountOwnerId = accountOwnerId;
	}
	/**
	 * @param accountOwnerFirstName the accountOwnerFirstName to set
	 */
	public void setAccountOwnerFirstName(String accountOwnerFirstName) {
		this.accountOwnerFirstName = accountOwnerFirstName;
	}
	/**
	 * @param accountOwnerLastName the accountOwnerLastName to set
	 */
	public void setAccountOwnerLastName(String accountOwnerLastName) {
		this.accountOwnerLastName = accountOwnerLastName;
	}
	/**
	 * @param countryCode the countryCode to set
	 */
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	/**
	 * @param countryName the countryName to set
	 */
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	/**
	 * @param circleManagerBattryId the circleManagerBattryId to set
	 */
	public void setCircleManagerBattryId(long circleManagerBattryId) {
		this.circleManagerBattryId = circleManagerBattryId;
	}
	/**
	 * @param circleManagerBattryFirstName the circleManagerBattryFirstName to set
	 */
	public void setCircleManagerBattryFirstName(String circleManagerBattryFirstName) {
		this.circleManagerBattryFirstName = circleManagerBattryFirstName;
	}
	/**
	 * @param circleManagerBattryLastName the circleManagerBattryLastName to set
	 */
	public void setCircleManagerBattryLastName(String circleManagerBattryLastName) {
		this.circleManagerBattryLastName = circleManagerBattryLastName;
	}
	/**
	 * @param circleManagerPowerId the circleManagerPowerId to set
	 */
	public void setCircleManagerPowerId(long circleManagerPowerId) {
		this.circleManagerPowerId = circleManagerPowerId;
	}
	/**
	 * @param circleManagerPowerFirstName the circleManagerPowerFirstName to set
	 */
	public void setCircleManagerPowerFirstName(String circleManagerPowerFirstName) {
		this.circleManagerPowerFirstName = circleManagerPowerFirstName;
	}
	/**
	 * @param circleManagerPowerLastName the circleManagerPowerLastName to set
	 */
	public void setCircleManagerPowerLastName(String circleManagerPowerLastName) {
		this.circleManagerPowerLastName = circleManagerPowerLastName;
	}
	/**
	 * @param createById the createById to set
	 */
	public void setCreateById(String createById) {
		this.createById = createById;
	}
	/**
	 * @param createByFirstName the createByFirstName to set
	 */
	public void setCreateByFirstName(String createByFirstName) {
		this.createByFirstName = createByFirstName;
	}
	/**
	 * @param createByLastName the createByLastName to set
	 */
	public void setCreateByLastName(String createByLastName) {
		this.createByLastName = createByLastName;
	}
	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	/**
	 * @param modifiedById the modifiedById to set
	 */
	public void setModifiedById(String modifiedById) {
		this.modifiedById = modifiedById;
	}
	/**
	 * @param modifiedByFirstName the modifiedByFirstName to set
	 */
	public void setModifiedByFirstName(String modifiedByFirstName) {
		this.modifiedByFirstName = modifiedByFirstName;
	}
	/**
	 * @param modifiedByLastName the modifiedByLastName to set
	 */
	public void setModifiedByLastName(String modifiedByLastName) {
		this.modifiedByLastName = modifiedByLastName;
	}
	/**
	 * @param lastModifiedDate the lastModifiedDate to set
	 */
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public long getCircleManagerEvId() {
		return circleManagerEvId;
	}

	public void setCircleManagerEvId(long circleManagerEvId) {
		this.circleManagerEvId = circleManagerEvId;
	}

	public String getCircleManagerEvFirstName() {
		return circleManagerEvFirstName;
	}

	public void setCircleManagerEvFirstName(String circleManagerEvFirstName) {
		this.circleManagerEvFirstName = circleManagerEvFirstName;
	}

	public String getCircleManagerEvLastName() {
		return circleManagerEvLastName;
	}

	public void setCircleManagerEvLastName(String circleManagerEvLastName) {
		this.circleManagerEvLastName = circleManagerEvLastName;
	}

	public String getAccountOwnerMiddleName() {
		return accountOwnerMiddleName;
	}

	public void setAccountOwnerMiddleName(String accountOwnerMiddleName) {
		this.accountOwnerMiddleName = accountOwnerMiddleName;
	}

	public String getCircleManagerBatteryMiddleName() {
		return circleManagerBatteryMiddleName;
	}

	public void setCircleManagerBatteryMiddleName(String circleManagerBatteryMiddleName) {
		this.circleManagerBatteryMiddleName = circleManagerBatteryMiddleName;
	}

	public String getCircleManagerPowerMiddleName() {
		return circleManagerPowerMiddleName;
	}

	public void setCircleManagerPowerMiddleName(String circleManagerPowerMiddleName) {
		this.circleManagerPowerMiddleName = circleManagerPowerMiddleName;
	}

	public String getCircleManagerEvMiddleName() {
		return circleManagerEvMiddleName;
	}

	public void setCircleManagerEvMiddleName(String circleManagerEvMiddleName) {
		this.circleManagerEvMiddleName = circleManagerEvMiddleName;
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
	public long getPowerLegalEntityId() {
		return powerLegalEntityId;
	}

	public void setPowerLegalEntityId(long powerLegalEntityId) {
		this.powerLegalEntityId = powerLegalEntityId;
	}

	public String getPowerLegalEntityName() {
		return powerLegalEntityName;
	}

	public void setPowerLegalEntityName(String powerLegalEntityName) {
		this.powerLegalEntityName = powerLegalEntityName;
	}

	public long getBatteryLegalEntityId() {
		return batteryLegalEntityId;
	}

	public void setBatteryLegalEntityId(long batteryLegalEntityId) {
		this.batteryLegalEntityId = batteryLegalEntityId;
	}

	public String getBatteryLegalEntityName() {
		return batteryLegalEntityName;
	}

	public void setBatteryLegalEntityName(String batteryLegalEntityName) {
		this.batteryLegalEntityName = batteryLegalEntityName;
	}

	public long getEvLegalEntityId() {
		return evLegalEntityId;
	}

	public void setEvLegalEntityId(long evLegalEntityId) {
		this.evLegalEntityId = evLegalEntityId;
	}

	public String getEvLegalEntityName() {
		return evLegalEntityName;
	}

	public void setEvLegalEntityName(String evLegalEntityName) {
		this.evLegalEntityName = evLegalEntityName;
	}
	public String getLegalEntityName() {
		return legalEntityName;
	}

	public void setLegalEntityName(String legalEntityName) {
		this.legalEntityName = legalEntityName;
	}

	public String getZoneBattery() {
		return zoneBattery;
	}

	public void setZoneBattery(String zoneBattery) {
		this.zoneBattery = zoneBattery;
	}

	
}
