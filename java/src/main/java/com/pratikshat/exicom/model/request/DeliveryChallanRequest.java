package com.pratikshat.exicom.model.request;

import java.util.Date;

public class DeliveryChallanRequest {

	long id;
	long productRequestId;
	int receivedQuantity;
	long statusId;
	String receivedBy;
	boolean isSendShippingMail;
	long shippingMode;
	Date shippingDate;
	String name;
	String email;
	String number;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getProductRequestId() {
		return productRequestId;
	}
	public void setProductRequestId(long productRequestId) {
		this.productRequestId = productRequestId;
	}
	public int getReceivedQuantity() {
		return receivedQuantity;
	}
	public void setReceivedQuantity(int receivedQuantity) {
		this.receivedQuantity = receivedQuantity;
	}
	public long getStatusId() {
		return statusId;
	}
	public void setStatusId(long statusId) {
		this.statusId = statusId;
	}
	public String getReceivedBy() {
		return receivedBy;
	}
	public void setReceivedBy(String receivedBy) {
		this.receivedBy = receivedBy;
	}
	public boolean isSendShippingMail() {
		return isSendShippingMail;
	}
	public void setSendShippingMail(boolean isSendShippingMail) {
		this.isSendShippingMail = isSendShippingMail;
	}
	public long getShippingMode() {
		return shippingMode;
	}
	public void setShippingMode(long shippingMode) {
		this.shippingMode = shippingMode;
	}
	public Date getShippingDate() {
		return shippingDate;
	}
	public void setShippingDate(Date shippingDate) {
		this.shippingDate = shippingDate;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	
	
	
}
