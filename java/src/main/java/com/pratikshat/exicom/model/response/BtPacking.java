package com.pratikshat.exicom.model.response;

import java.util.Date;
import java.util.List;

public class BtPacking {
    String srouceWarehouseName;
    String sourceLegalEntity;
    String destinationWarehouseName;
    String destinationLegalEntity;
    String ref;
    String refType;
	Date creationDate;

    List<BtPackingItem> items;

    

	public BtPacking(String srouceWarehouseName, String sourceLegalEntity, String destinationWarehouseName,
			String destinationLegalEntity, String ref, String refType, List<BtPackingItem> items,Date creationDate) {
		this.srouceWarehouseName = srouceWarehouseName;
		this.sourceLegalEntity = sourceLegalEntity;
		this.destinationWarehouseName = destinationWarehouseName;
		this.destinationLegalEntity = destinationLegalEntity;
		this.ref = ref;
		this.refType = refType;
		this.items = items;
		this.creationDate=creationDate;
	}

	public BtPacking() {
	}

	public String getSrouceWarehouseName() {
		return srouceWarehouseName;
	}

	public void setSrouceWarehouseName(String srouceWarehouseName) {
		this.srouceWarehouseName = srouceWarehouseName;
	}

	public String getSourceLegalEntity() {
		return sourceLegalEntity;
	}

	public void setSourceLegalEntity(String sourceLegalEntity) {
		this.sourceLegalEntity = sourceLegalEntity;
	}

	public String getDestinationWarehouseName() {
		return destinationWarehouseName;
	}

	public void setDestinationWarehouseName(String destinationWarehouseName) {
		this.destinationWarehouseName = destinationWarehouseName;
	}

	public String getDestinationLegalEntity() {
		return destinationLegalEntity;
	}

	public void setDestinationLegalEntity(String destinationLegalEntity) {
		this.destinationLegalEntity = destinationLegalEntity;
	}

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public String getRefType() {
		return refType;
	}

	public void setRefType(String refType) {
		this.refType = refType;
	}

	public List<BtPackingItem> getItems() {
		return items;
	}

	public void setItems(List<BtPackingItem> items) {
		this.items = items;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

    

}
