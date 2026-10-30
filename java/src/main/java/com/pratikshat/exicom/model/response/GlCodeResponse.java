package com.pratikshat.exicom.model.response;

public class GlCodeResponse {

	int id;
	String name;
	double totalAmount;
	
	public GlCodeResponse() {
		
	}
	
	public GlCodeResponse(int id, String name, double totalAmount) {
		this.id = id;
		this.name = name;
		this.totalAmount = totalAmount;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	
}
