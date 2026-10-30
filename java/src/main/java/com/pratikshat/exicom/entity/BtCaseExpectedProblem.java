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
public class BtCaseExpectedProblem  implements java.io.Serializable,OldPresist<BtCaseExpectedProblem>{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	String problemName;
	
	boolean synced;
	
	@ManyToOne
	BusinessDomain businessDomain;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProblemName() {
		return problemName;
	}

	public void setProblemName(String problemName) {
		this.problemName = problemName;
	}

	public boolean isSynced() {
		return synced;
	}

	public void setSynced(boolean synced) {
		this.synced = synced;
	}

	public BusinessDomain getBusinessDomain() {
		return businessDomain;
	}

	public void setBusinessDomain(BusinessDomain businessDomain) {
		this.businessDomain = businessDomain;
	}

	@Transient transient BtCaseExpectedProblem old;
	@PostLoad
	@Override
	public void old() {
		old=(BtCaseExpectedProblem) SerializationUtils.clone(this);
		
	}

	@Override
	public BtCaseExpectedProblem gOld() {
		// TODO Auto-generated method stub
		return old;
	}

}
