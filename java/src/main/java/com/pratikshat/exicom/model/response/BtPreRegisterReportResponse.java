package com.pratikshat.exicom.model.response;

public class BtPreRegisterReportResponse {

	long probleReportedId;
	String problemName;
	
	
	public BtPreRegisterReportResponse(long probleReportedId, String problemName) {
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
