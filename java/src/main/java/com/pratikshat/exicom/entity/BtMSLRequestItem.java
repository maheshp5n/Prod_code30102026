package com.pratikshat.exicom.entity;

import java.io.Serializable;

import javax.persistence.Entity;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.pratikshat.exicom.interceptor.ObjectAuditListener;

@Entity
@javax.persistence.EntityListeners({AuditingEntityListener.class,ObjectAuditListener.class})
public class BtMSLRequestItem implements Serializable,OldPresist<BtMSLRequestItem>{

	@javax.persistence.Id
	@javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	long id;
	@javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	BtMSLRequest btMslRequest;
	@javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	BtProducts btProducts;
	float oldHSL;
	float newHSL;
	float oldMSL;
	float newMSL;
	String remarks;
	
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public BtMSLRequest getBtMslRequest() {
		return btMslRequest;
	}
	public void setBtMslRequest(BtMSLRequest btMslRequest) {
		this.btMslRequest = btMslRequest;
	}
	public BtProducts getBtProducts() {
		return btProducts;
	}
	public void setBtProducts(BtProducts btProducts) {
		this.btProducts = btProducts;
	}
	public float getOldHSL() {
		return oldHSL;
	}
	public void setOldHSL(float oldHSL) {
		this.oldHSL = oldHSL;
	}
	public float getNewHSL() {
		return newHSL;
	}
	public void setNewHSL(float newHSL) {
		this.newHSL = newHSL;
	}
	public float getOldMSL() {
		return oldMSL;
	}
	public void setOlfMSL(float oldMSL) {
		this.oldMSL = oldMSL;
	}
	public float getNewMSL() {
		return newMSL;
	}
	public void setNewMSL(float newMSL) {
		this.newMSL = newMSL;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public BtMSLRequestItem getOld() {
		return old;
	}
	public void setOld(BtMSLRequestItem old) {
		this.old = old;
	}
	@javax.persistence.Transient transient BtMSLRequestItem old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(BtMSLRequestItem)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public BtMSLRequestItem gOld() {
		return old;
	}
	public String toString() {
			return ""+id;
	}
}
