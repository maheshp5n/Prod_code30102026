package com.pratikshat.exicom.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;

import org.apache.commons.lang.SerializationUtils;
import org.springframework.data.annotation.Transient;

import com.pratikshat.exicom.interceptor.ObjectAuditListener;

@Entity
@EntityListeners(ObjectAuditListener.class)
public class BtSolutionImpartedProblem implements Serializable, OldPresist<BtSolutionImpartedProblem>{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String	solutionName;
	@ManyToOne(fetch = FetchType.LAZY)
	private  BtSchUseOnlyDetails btSchUseOnlyDetails;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSolutionName() {
		return solutionName;
	}
	public void setSolutionName(String solutionName) {
		this.solutionName = solutionName;
	}
	public BtSchUseOnlyDetails getBtSchUseOnlyDetails() {
		return btSchUseOnlyDetails;
	}
	public void setBtSchUseOnlyDetails(BtSchUseOnlyDetails btSchUseOnlyDetails) {
		this.btSchUseOnlyDetails = btSchUseOnlyDetails;
	}
	
	
	@Transient transient BtSolutionImpartedProblem old;
	
	@PostLoad
	@Override
	public void old() {
		old=(BtSolutionImpartedProblem) SerializationUtils.clone(this);
		
	}
	@Override
	public BtSolutionImpartedProblem gOld() {
		
		return old;
	}
	

}
