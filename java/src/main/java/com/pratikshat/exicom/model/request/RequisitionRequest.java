package com.pratikshat.exicom.model.request;

import java.util.Date;
import java.util.Set;

public class RequisitionRequest {

	long requisitionId;
	long requisitionStatusId;
	
	Set<RequisitionProductDetailsRequest> productList;
	
	long fromWarehouseId;
	long toWarehouseId;
	Date requiredDate;
	long productTypeId;
	long approvalStatusId;
	
	
	
	public long getRequisitionStatusId() {
		return requisitionStatusId;
	}
	public void setRequisitionStatusId(long requisitionStatusId) {
		this.requisitionStatusId = requisitionStatusId;
	}
	public Set<RequisitionProductDetailsRequest> getProductList() {
		return productList;
	}
	public void setProductList(Set<RequisitionProductDetailsRequest> productList) {
		this.productList = productList;
	}
	public long getFromWarehouseId() {
		return fromWarehouseId;
	}
	public void setFromWarehouseId(long fromWarehouseId) {
		this.fromWarehouseId = fromWarehouseId;
	}
	public long getToWarehouseId() {
		return toWarehouseId;
	}
	public void setToWarehouseId(long toWarehouseId) {
		this.toWarehouseId = toWarehouseId;
	}
	public Date getRequiredDate() {
		return requiredDate;
	}
	public void setRequiredDate(Date requiredDate) {
		this.requiredDate = requiredDate;
	}
	public long getProductTypeId() {
		return productTypeId;
	}
	public void setProductTypeId(long productTypeId) {
		this.productTypeId = productTypeId;
	}
	public long getApprovalStatusId() {
		return approvalStatusId;
	}
	public void setApprovalStatusId(long approvalStatusId) {
		this.approvalStatusId = approvalStatusId;
	}
	public long getRequisitionId() {
		return requisitionId;
	}
	public void setRequisitionId(long requisitionId) {
		this.requisitionId = requisitionId;
	}
	
	
}
