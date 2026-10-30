package com.pratikshat.exicom.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class PlantDetails implements java.io.Serializable,OldPresist<PlantDetails>{
	@Id
	String plantCode;
	String plantName;
	
	public PlantDetails() {}
	public PlantDetails(String plantCode) {
		this.plantCode=plantCode;
	}
	/**
	 * @return the plantCode
	 */
	public String getPlantCode() {
		return plantCode;
	}
	/**
	 * @return the plantName
	 */
	public String getPlantName() {
		return plantName;
	}
	/**
	 * @param plantCode the plantCode to set
	 */
	public void setPlantCode(String plantCode) {
		this.plantCode = plantCode;
	}
	/**
	 * @param plantName the plantName to set
	 */
	public void setPlantName(String plantName) {
		this.plantName = plantName;
	}
	

	@javax.persistence.Transient transient PlantDetails old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(PlantDetails)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public PlantDetails gOld() {
		return old;
	}
	public String toString() {
			return ""+plantCode;
	}
}
