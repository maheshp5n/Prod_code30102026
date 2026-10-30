package com.pratikshat.exicom.entity;



@javax.persistence.Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class PackingCondition implements java.io.Serializable,OldPresist<PackingCondition>{
	@javax.persistence.Id
	@javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	int id;
	String name;
	boolean synced;
	
	
	
	public PackingCondition() {
	
	}
	public PackingCondition(int id) {
		
		this.id = id;
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	public boolean isSynced() {
		return synced;
	}
	public void setSynced(boolean synced) {
		this.synced = synced;
	}
	

	@javax.persistence.Transient transient PackingCondition old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(PackingCondition)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public PackingCondition gOld() {
		return old;
	}
	public String toString() {
			return ""+id;
	}
}
