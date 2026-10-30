package com.pratikshat.exicom.model.request;

public class QuoteLineItemSumQueryResponse {
	
	Double totalPrice;
	Double cgstAmount;
	Double sgstAmount;
	Double igstAmount;
	Double totalPriceWithTax;
	public Double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public Double getCgstAmount() {
		return cgstAmount;
	}
	public void setCgstAmount(Double cgstAmount) {
		this.cgstAmount = cgstAmount;
	}
	public Double getSgstAmount() {
		return sgstAmount;
	}
	public void setSgstAmount(Double sgstAmount) {
		this.sgstAmount = sgstAmount;
	}
	public Double getIgstAmount() {
		return igstAmount;
	}
	public void setIgstAmount(Double igstAmount) {
		this.igstAmount = igstAmount;
	}
	public Double getTotalPriceWithTax() {
		return totalPriceWithTax;
	}
	public void setTotalPriceWithTax(Double totalPriceWithTax) {
		this.totalPriceWithTax = totalPriceWithTax;
	}
	
	
}
