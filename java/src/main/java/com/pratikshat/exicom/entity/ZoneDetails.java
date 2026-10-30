package com.pratikshat.exicom.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class ZoneDetails implements java.io.Serializable,OldPresist<ZoneDetails>{
	@Id
	String zoneName;
	@javax.persistence.ManyToOne
	EmployeeDetails createdBy;
	java.util.Date createdDate;
	@javax.persistence.ManyToOne
	EmployeeDetails modifyedBy;
	java.util.Date modifyedDate;
	
	boolean synced;
	/**
	 * @return the synced
	 */
	public boolean isSynced() {
		return synced;
	}


	/**
	 * @param synced the synced to set
	 */
	public void setSynced(boolean synced) {
		this.synced = synced;
	}


	public ZoneDetails() {}
	

	/**
	 * @return the zoneName
	 */
	public String getZoneName() {
		return zoneName;
	}

	/**
	 * @param zoneName the zoneName to set
	 */
	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}

	/**
	 * @param zoneName
	 */
	public ZoneDetails(String zoneName) {
		this.zoneName = zoneName;
	}


	/**
	 * @return the createdBy
	 */
	public EmployeeDetails getCreatedBy() {
		return createdBy;
	}


	/**
	 * @return the createdDate
	 */
	public java.util.Date getCreatedDate() {
		return createdDate;
	}


	/**
	 * @return the modifyedBy
	 */
	public EmployeeDetails getModifyedBy() {
		return modifyedBy;
	}


	/**
	 * @return the modifyedDate
	 */
	public java.util.Date getModifyedDate() {
		return modifyedDate;
	}


	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(EmployeeDetails createdBy) {
		this.createdBy = createdBy;
	}


	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(java.util.Date createdDate) {
		this.createdDate = createdDate;
	}


	/**
	 * @param modifyedBy the modifyedBy to set
	 */
	public void setModifyedBy(EmployeeDetails modifyedBy) {
		this.modifyedBy = modifyedBy;
	}


	/**
	 * @param modifyedDate the modifyedDate to set
	 */
	public void setModifyedDate(java.util.Date modifyedDate) {
		this.modifyedDate = modifyedDate;
	}
	

	@javax.persistence.Transient transient ZoneDetails old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(ZoneDetails)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public ZoneDetails gOld() {
		return old;
	}
	public String toString() {
			return ""+zoneName;
	}
}
