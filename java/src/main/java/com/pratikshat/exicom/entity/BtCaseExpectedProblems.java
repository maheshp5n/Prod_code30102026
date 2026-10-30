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
public class BtCaseExpectedProblems  implements java.io.Serializable,OldPresist<BtCaseExpectedProblems>{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	String problemName;
	
	boolean synced;
	
	@ManyToOne
	BtProblemMaster btProblemMaster;
	
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

	public BtProblemMaster getBtProblemMaster() {
		return btProblemMaster;
	}

	public void setBtProblemMaster(BtProblemMaster btProblemMaster) {
		this.btProblemMaster = btProblemMaster;
	}

	@Transient transient BtCaseExpectedProblems old;
	@PostLoad
	@Override
	public void old() {
		old=(BtCaseExpectedProblems) SerializationUtils.clone(this);
		
	}

	@Override
	public BtCaseExpectedProblems gOld() {
		return old;
	}

}
