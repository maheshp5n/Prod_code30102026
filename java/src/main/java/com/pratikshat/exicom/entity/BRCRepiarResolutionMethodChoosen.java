package com.pratikshat.exicom.entity;

import java.io.Serializable;

@javax.persistence.Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class BRCRepiarResolutionMethodChoosen implements java.io.Serializable,OldPresist<BRCRepiarResolutionMethodChoosen>{
	@javax.persistence.Id
	@javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	long id;
	@javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	BRCRepairInventoryReceiveRepairItem repariItem;
	
	@javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	BRCRepiarResolutionMethod resolutionMethods;
	
	public BRCRepiarResolutionMethodChoosen() {}
	public BRCRepiarResolutionMethodChoosen(long id) {
		this.id=id;
	}
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	/**
	 * @return the repariItem
	 */
	public BRCRepairInventoryReceiveRepairItem getRepariItem() {
		return repariItem;
	}
	/**
	 * @return the resolutionMethods
	 */
	public BRCRepiarResolutionMethod getResolutionMethods() {
		return resolutionMethods;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @param repariItem the repariItem to set
	 */
	public void setRepariItem(BRCRepairInventoryReceiveRepairItem repariItem) {
		this.repariItem = repariItem;
	}
	/**
	 * @param resolutionMethods the resolutionMethods to set
	 */
	public void setResolutionMethods(BRCRepiarResolutionMethod resolutionMethods) {
		this.resolutionMethods = resolutionMethods;
	}
	

	@javax.persistence.Transient transient BRCRepiarResolutionMethodChoosen old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(BRCRepiarResolutionMethodChoosen)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public BRCRepiarResolutionMethodChoosen gOld() {
		return old;
	}
	public String toString() {
			return ""+id;
	}
}
