package com.pratikshat.exicom.model.request;

import java.util.Set;

public class MrnItemsRequest {
	Long id;
	Long mrnDetailsId;
	String productPartCode;
	Float qty;
	Double rateUnitQty;
	Double taxableAmount;
	Float discount;
	Double taxableAmountAfterDiscount;
	Float igst;
	Float cgst;
	Float sgst;
	Double totalAmount;
	Double returnQty;
	
	Set<String> serialNumbers;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMrnDetailsId() {
		return mrnDetailsId;
	}

	public void setMrnDetailsId(Long mrnDetailsId) {
		this.mrnDetailsId = mrnDetailsId;
	}

	public String getProductPartCode() {
		return productPartCode;
	}

	public void setProductPartCode(String productPartCode) {
		this.productPartCode = productPartCode;
	}

	

	public Double getRateUnitQty() {
		return rateUnitQty;
	}

	public void setRateUnitQty(Double rateUnitQty) {
		this.rateUnitQty = rateUnitQty;
	}

	public Double getTaxableAmount() {
		return taxableAmount;
	}

	public void setTaxableAmount(Double taxableAmount) {
		this.taxableAmount = taxableAmount;
	}

	public Float getDiscount() {
		return discount;
	}

	public void setDiscount(Float discount) {
		this.discount = discount;
	}

	public Double getTaxableAmountAfterDiscount() {
		return taxableAmountAfterDiscount;
	}

	public void setTaxableAmountAfterDiscount(Double taxableAmountAfterDiscount) {
		this.taxableAmountAfterDiscount = taxableAmountAfterDiscount;
	}

	public Float getIgst() {
		return igst;
	}

	public void setIgst(Float igst) {
		this.igst = igst;
	}

	public Float getCgst() {
		return cgst;
	}

	public void setCgst(Float cgst) {
		this.cgst = cgst;
	}

	public Float getSgst() {
		return sgst;
	}

	public void setSgst(Float sgst) {
		this.sgst = sgst;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Double getReturnQty() {
		return returnQty;
	}

	public void setReturnQty(Double returnQty) {
		this.returnQty = returnQty;
	}

	public Set<String> getSerialNumbers() {
		return serialNumbers;
	}

	public void setSerialNumbers(Set<String> serialNumbers) {
		this.serialNumbers = serialNumbers;
	}

	public Float getQty() {
		return qty;
	}

	public void setQty(Float qty) {
		this.qty = qty;
	}
	
	
	
}	
