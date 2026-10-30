package com.pratikshat.exicom.entity;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.Transient;

import org.apache.commons.lang.SerializationUtils;

import com.pratikshat.exicom.interceptor.ObjectAuditListener;

@Entity
@EntityListeners(ObjectAuditListener.class)
public class BtPodCondition implements java.io.Serializable,OldPresist<BtPodCondition>{

	@Id
	@GeneratedValue(strategy  =  GenerationType.IDENTITY)
	private int id;
	
	private boolean toBeInvoiced;
	
	@ManyToOne
	CaseReasonDetails reason;
	
	private String remark;
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isToBeInvoiced() {
		return toBeInvoiced;
	}

	public void setToBeInvoiced(boolean toBeInvoiced) {
		this.toBeInvoiced = toBeInvoiced;
	}



	public CaseReasonDetails getReason() {
		return reason;
	}

	public void setReason(CaseReasonDetails reason) {
		this.reason = reason;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Transient transient BtPodCondition old;
	@PostLoad
	@Override
	public void old() {
		old=(BtPodCondition) SerializationUtils.clone(this);
		
	}

	@Override
	public BtPodCondition gOld() {
		// TODO Auto-generated method stub
		return old;
	}

}
