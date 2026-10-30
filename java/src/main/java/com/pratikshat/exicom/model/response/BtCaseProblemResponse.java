package com.pratikshat.exicom.model.response;


public class BtCaseProblemResponse {
	
	private long probleReportedId;
	private String problemName;
	
	
	
	public BtCaseProblemResponse() {
	}

	public BtCaseProblemResponse(long probleReportedId, String problemName) {
		this.probleReportedId = probleReportedId;
		this.problemName = problemName;
	}
	
	public long getProbleReportedId() {
		return probleReportedId;
	}
	public void setProbleReportedId(long probleReportedId) {
		this.probleReportedId = probleReportedId;
	}
	public String getProblemName() {
		return problemName;
	}
	public void setProblemName(String problemName) {
		this.problemName = problemName;
	}
	
	

}
