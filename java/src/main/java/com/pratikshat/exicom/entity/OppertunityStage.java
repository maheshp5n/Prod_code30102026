package com.pratikshat.exicom.entity;

import javax.persistence.Entity;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class OppertunityStage implements java.io.Serializable,OldPresist<OppertunityStage>{

	@javax.persistence.Id
	@javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	long oppertunityStageId;
	
	String oppertunityStageName;
	
	boolean synced;

	public OppertunityStage(long oppertunityStageId) {
		this.oppertunityStageId = oppertunityStageId;
	}
	
	public OppertunityStage() {
	}

	public long getOppertunityStageId() {
		return oppertunityStageId;
	}

	public void setOppertunityStageId(long oppertunityStageId) {
		this.oppertunityStageId = oppertunityStageId;
	}

	public String getOppertunityStageName() {
		return oppertunityStageName;
	}

	public void setOppertunityStageName(String oppertunityStageName) {
		this.oppertunityStageName = oppertunityStageName;
	}

	public boolean isSynced() {
		return synced;
	}

	public void setSynced(boolean synced) {
		this.synced = synced;
	}
	
	

	@javax.persistence.Transient transient OppertunityStage old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(OppertunityStage)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public OppertunityStage gOld() {
		return old;
	}
	public String toString() {
			return ""+oppertunityStageId;
	}
}
