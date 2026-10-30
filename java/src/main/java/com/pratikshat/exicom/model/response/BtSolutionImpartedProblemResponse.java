package com.pratikshat.exicom.model.response;

import com.pratikshat.exicom.entity.BtSolutionImpartedProblem;

public class BtSolutionImpartedProblemResponse {
	
	long id;
	String solutinName;
	long schId;
	public BtSolutionImpartedProblemResponse(long id, String solutinName) {
		super();
		this.id = id;
		this.solutinName = solutinName;
	}
	public BtSolutionImpartedProblemResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	public BtSolutionImpartedProblemResponse(BtSolutionImpartedProblem solPrblm) {
		this.id=solPrblm.getId();
		this.solutinName=solPrblm.getSolutionName();
		this.schId=solPrblm.getBtSchUseOnlyDetails().getId();
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getSolutinName() {
		return solutinName;
	}
	public void setSolutinName(String solutinName) {
		this.solutinName = solutinName;
	}
	public long getSchId() {
		return schId;
	}
	public void setSchId(long schId) {
		this.schId = schId;
	}
	
	
	

}
