package com.pratikshat.exicom.model.response;

import com.pratikshat.exicom.entity.ProductTransferEwaybill;

public class ResponseProductTransfer {
	
ProductTransferEwaybill ewaybill;
	
	int number;

	public ProductTransferEwaybill getEwaybill() {
		return ewaybill;
	}

	public void setEwaybill(ProductTransferEwaybill ewaybill) {
		this.ewaybill = ewaybill;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}


}
