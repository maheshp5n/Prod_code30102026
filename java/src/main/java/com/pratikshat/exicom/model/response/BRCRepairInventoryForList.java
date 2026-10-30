package com.pratikshat.exicom.model.response;

public class BRCRepairInventoryForList {

	long receivingId;
	String receivingName;
	
	public BRCRepairInventoryForList(){
		
	}
	
	public BRCRepairInventoryForList(long receivingId,String receivingName){
		this.receivingId = receivingId;
		this.receivingName = receivingName;
	}

	public long getReceivingId() {
		return receivingId;
	}

	public void setReceivingId(long receivingId) {
		this.receivingId = receivingId;
	}

	public String getReceivingName() {
		return receivingName;
	}

	public void setReceivingName(String receivingName) {
		this.receivingName = receivingName;
	}
}
