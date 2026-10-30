package com.pratikshat.exicom.model.response;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.pratikshat.exicom.entity.Requisition;
import com.pratikshat.exicom.entity.RequisitionProductDetails;

public class RequisitionResponse {

	long id;
	String manualId;
	
	Set<RequisitionProductDetailsResponse> productList;
	
	long fromWarehouseId;
	String fromWarehouseEmailId;
	String fromWarehouseName;
	String requstingEntity;
	
	
	long toWarehouseId;
	String toWarehouseEmailId;
	String toWarehouseName;
	String requestingTo;
	
	String requisitionerFirtName;
	String requisitionerMiddleName;
	String requisitionerlastName;
	String requisitionerEmailId;
	
	Date requiredDate;
	
	long requisitionStatusId;
	String requisitionStatusName;
	
	long productTypeId;
	String productTypeName;
	
	long approvalStatusId;
	String approvalStatusName;
	
	boolean receivedStatus;
	boolean sentStatus;
	
	String ownerFirtName;
	String ownerMiddleName;
	String ownerlastName;
	String ownerEmailId;
	
	
	String createdByFirstName;
	String createdByMiddleName;
	String createByLastName;
	String createdByEmailId;
	Date createdDate;
	
	String modifiedByFirstName;
	String modifiedByMiddleName;
	String modifiedByLastName;
	String modifiedByEmailId;
	Date modifiedDate;
	
	String costCenter;
	String plant;
	
	String sentStatusMessage;
	
	float pendingForIssue;
	
	float totalQuantityRequired;
	float totalInTransit;
	float totalQuantityReceived;
	float totalQuantityIssued;
	
	

	String legalEnetityType;
	
	public RequisitionResponse(Requisition requisition) {
		this.id = requisition.getId();
		this.manualId = requisition.getManualId();
		this.requiredDate = requisition.getRequiredDate();
		this.receivedStatus = requisition.isReceivedStatus();
		this.sentStatus = requisition.isSentStatus();
		this.sentStatusMessage = requisition.getSentStatusMessage();
		this.pendingForIssue = 0;
		
		if(requisition.getFromWarehouse()!=null) {
			this.fromWarehouseId= requisition.getFromWarehouse().getWarehouseId();
			this.fromWarehouseEmailId = requisition.getFromWarehouse().getWarehouseEmail();
			this.fromWarehouseName = requisition.getFromWarehouse().getWarehouseName();
			
			this.costCenter = requisition.getFromWarehouse().getWarehousePlant();
			this.plant = requisition.getFromWarehouse().getWarehousePlant();
			if(requisition.getFromWarehouse().getLegalEntity()!=null) {
				if(requisition.getFromWarehouse().getLegalEntity().getLegalEntityType()!=null) {
					this.legalEnetityType = requisition.getFromWarehouse().getLegalEntity().getLegalEntityType().getTypeName();
				}
				
			}
			
			
			if(requisition.getFromWarehouse().getLegalEntity()!=null) {
				this.requstingEntity = requisition.getFromWarehouse().getLegalEntity().getLegalEntityName();
			}	
		}
		
		
		if(requisition.getToWarehouse()!=null) {
			this.toWarehouseId= requisition.getToWarehouse().getWarehouseId();
			this.toWarehouseEmailId = requisition.getToWarehouse().getWarehouseEmail();
			this.toWarehouseName = requisition.getToWarehouse().getWarehouseName();
			if(requisition.getToWarehouse().getLegalEntity()!=null) {
				this.requestingTo = requisition.getToWarehouse().getLegalEntity().getLegalEntityName();
			}	
		}
		
		
		if(requisition.getOwner()!=null) {
			this.requisitionerFirtName = requisition.getOwner().getFirstName();
			this.requisitionerMiddleName = requisition.getOwner().getMiddleName();
			this.requisitionerlastName = requisition.getOwner().getLastName();
			this.requisitionerEmailId = requisition.getOwner().getEmailId();
		}
		
		
		
		if(requisition.getRequisitionStatus()!=null) {
			this.requisitionStatusId= requisition.getRequisitionStatus().getStatusId();
			this.requisitionStatusName = requisition.getRequisitionStatus().getStatusName();
		}
		
		if(requisition.getRequisitionProductType()!=null) {
			this.productTypeId = requisition.getRequisitionProductType().getProductTypeId();
			this.productTypeName = requisition.getRequisitionProductType().getProductTypeName();
		}
		
		if(requisition.getRequisitionApprovalStatus()!=null) {
			this.approvalStatusId = requisition.getRequisitionApprovalStatus().getApprovalStatusId();
			this.approvalStatusName = requisition.getRequisitionApprovalStatus().getApprovalStatusName();
		}
		
		if(requisition.getOwner()!=null) {
			this.ownerFirtName = requisition.getOwner().getFirstName();
			this.ownerMiddleName = requisition.getOwner().getMiddleName();
			this.ownerlastName = requisition.getOwner().getLastName();
			this.ownerEmailId = requisition.getOwner().getEmailId();
		}
		
		
		if(requisition.getCreatedBy()!=null) {
			this.createdByFirstName = requisition.getCreatedBy().getFirstName();
			this.createdByMiddleName = requisition.getCreatedBy().getMiddleName();
			this.createByLastName = requisition.getCreatedBy().getLastName();
			this.createdByEmailId = requisition.getCreatedBy().getEmailId();
			this.createdDate = requisition.getCreatedDate();
		}
		
		
		if(requisition.getModifiedBy()!=null) {
			this.modifiedByFirstName = requisition.getModifiedBy().getFirstName();
			this.modifiedByMiddleName = requisition.getModifiedBy().getMiddleName();
			this.modifiedByLastName = requisition.getModifiedBy().getLastName();
			this.modifiedByEmailId = requisition.getModifiedBy().getEmailId();
			this.modifiedDate = requisition.getModifiedDate();
		}
	}

	public RequisitionResponse() {
	
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getManualId() {
		return manualId;
	}

	public void setManualId(String manualId) {
		this.manualId = manualId;
	}

	public Set<RequisitionProductDetailsResponse> getProductList() {
		return productList;
	}

	public void setProductList(Set<RequisitionProductDetailsResponse> productList) {
		this.productList = productList;
	}

	public long getFromWarehouseId() {
		return fromWarehouseId;
	}

	public void setFromWarehouseId(long fromWarehouseId) {
		this.fromWarehouseId = fromWarehouseId;
	}

	public String getFromWarehouseEmailId() {
		return fromWarehouseEmailId;
	}

	public void setFromWarehouseEmailId(String fromWarehouseEmailId) {
		this.fromWarehouseEmailId = fromWarehouseEmailId;
	}

	public String getRequstingEntity() {
		return requstingEntity;
	}

	public void setRequstingEntity(String requstingEntity) {
		this.requstingEntity = requstingEntity;
	}

	public long getToWarehouseId() {
		return toWarehouseId;
	}

	public void setToWarehouseId(long toWarehouseId) {
		this.toWarehouseId = toWarehouseId;
	}

	public String getToWarehouseEmailId() {
		return toWarehouseEmailId;
	}

	public void setToWarehouseEmailId(String toWarehouseEmailId) {
		this.toWarehouseEmailId = toWarehouseEmailId;
	}

	public String getRequestingTo() {
		return requestingTo;
	}

	public void setRequestingTo(String requestingTo) {
		this.requestingTo = requestingTo;
	}

	public String getRequisitionerFirtName() {
		return requisitionerFirtName;
	}

	public void setRequisitionerFirtName(String requisitionerFirtName) {
		this.requisitionerFirtName = requisitionerFirtName;
	}

	public String getRequisitionerlastName() {
		return requisitionerlastName;
	}

	public void setRequisitionerlastName(String requisitionerlastName) {
		this.requisitionerlastName = requisitionerlastName;
	}

	public String getRequisitionerEmailId() {
		return requisitionerEmailId;
	}

	public void setRequisitionerEmailId(String requisitionerEmailId) {
		this.requisitionerEmailId = requisitionerEmailId;
	}

	public Date getRequiredDate() {
		return requiredDate;
	}

	public void setRequiredDate(Date requiredDate) {
		this.requiredDate = requiredDate;
	}

	public long getRequisitionStatusId() {
		return requisitionStatusId;
	}

	public void setRequisitionStatusId(long requisitionStatusId) {
		this.requisitionStatusId = requisitionStatusId;
	}

	public String getRequisitionStatusName() {
		return requisitionStatusName;
	}

	public void setRequisitionStatusName(String requisitionStatusName) {
		this.requisitionStatusName = requisitionStatusName;
	}

	public long getProductTypeId() {
		return productTypeId;
	}

	public void setProductTypeId(long productTypeId) {
		this.productTypeId = productTypeId;
	}

	public String getProductTypeName() {
		return productTypeName;
	}

	public void setProductTypeName(String productTypeName) {
		this.productTypeName = productTypeName;
	}

	public long getApprovalStatusId() {
		return approvalStatusId;
	}

	public void setApprovalStatusId(long approvalStatusId) {
		this.approvalStatusId = approvalStatusId;
	}

	public String getApprovalStatusName() {
		return approvalStatusName;
	}

	public void setApprovalStatusName(String approvalStatusName) {
		this.approvalStatusName = approvalStatusName;
	}

	public boolean isReceivedStatus() {
		return receivedStatus;
	}

	public void setReceivedStatus(boolean receivedStatus) {
		this.receivedStatus = receivedStatus;
	}

	public boolean isSentStatus() {
		return sentStatus;
	}

	public void setSentStatus(boolean sentStatus) {
		this.sentStatus = sentStatus;
	}

	public String getOwnerFirtName() {
		return ownerFirtName;
	}

	public void setOwnerFirtName(String ownerFirtName) {
		this.ownerFirtName = ownerFirtName;
	}

	public String getOwnerlastName() {
		return ownerlastName;
	}

	public void setOwnerlastName(String ownerlastName) {
		this.ownerlastName = ownerlastName;
	}

	public String getOwnerEmailId() {
		return ownerEmailId;
	}

	public void setOwnerEmailId(String ownerEmailId) {
		this.ownerEmailId = ownerEmailId;
	}

	public String getCreatedByFirstName() {
		return createdByFirstName;
	}

	public void setCreatedByFirstName(String createdByFirstName) {
		this.createdByFirstName = createdByFirstName;
	}

	public String getCreateByLastName() {
		return createByLastName;
	}

	public void setCreateByLastName(String createByLastName) {
		this.createByLastName = createByLastName;
	}

	public String getCreatedByEmailId() {
		return createdByEmailId;
	}

	public void setCreatedByEmailId(String createdByEmailId) {
		this.createdByEmailId = createdByEmailId;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedByFirstName() {
		return modifiedByFirstName;
	}

	public void setModifiedByFirstName(String modifiedByFirstName) {
		this.modifiedByFirstName = modifiedByFirstName;
	}

	public String getModifiedByLastName() {
		return modifiedByLastName;
	}

	public void setModifiedByLastName(String modifiedByLastName) {
		this.modifiedByLastName = modifiedByLastName;
	}

	public String getModifiedByEmailId() {
		return modifiedByEmailId;
	}

	public void setModifiedByEmailId(String modifiedByEmailId) {
		this.modifiedByEmailId = modifiedByEmailId;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getCostCenter() {
		return costCenter;
	}

	public void setCostCenter(String costCenter) {
		this.costCenter = costCenter;
	}

	public String getPlant() {
		return plant;
	}

	public void setPlant(String plant) {
		this.plant = plant;
	}

	public String getSentStatusMessage() {
		return sentStatusMessage;
	}

	public void setSentStatusMessage(String sentStatusMessage) {
		this.sentStatusMessage = sentStatusMessage;
	}

	public float getPendingForIssue() {
		return pendingForIssue;
	}

	public void setPendingForIssue(float pendingForIssue) {
		this.pendingForIssue = pendingForIssue;
	}

	public String getLegalEnetityType() {
		return legalEnetityType;
	}

	public void setLegalEnetityType(String legalEnetityType) {
		this.legalEnetityType = legalEnetityType;
	}
	
	
	public float getTotalQuantityRequired() {
		return totalQuantityRequired;
	}

	public void setTotalQuantityRequired(float totalQuantityRequired) {
		this.totalQuantityRequired = totalQuantityRequired;
	}

	public float getTotalInTransit() {
		return totalInTransit;
	}

	public void setTotalInTransit(float totalInTransit) {
		this.totalInTransit = totalInTransit;
	}

	public float getTotalQuantityReceived() {
		return totalQuantityReceived;
	}

	public void setTotalQuantityReceived(float totalQuantityReceived) {
		this.totalQuantityReceived = totalQuantityReceived;
	}

	public float getTotalQuantityIssued() {
		return totalQuantityIssued;
	}

	public void setTotalQuantityIssued(float totalQuantityIssued) {
		this.totalQuantityIssued = totalQuantityIssued;
	}

	public String getFromWarehouseName() {
		return fromWarehouseName;
	}

	public void setFromWarehouseName(String fromWarehouseName) {
		this.fromWarehouseName = fromWarehouseName;
	}

	public String getToWarehouseName() {
		return toWarehouseName;
	}

	public void setToWarehouseName(String toWarehouseName) {
		this.toWarehouseName = toWarehouseName;
	}

	public String getRequisitionerMiddleName() {
		return requisitionerMiddleName;
	}

	public void setRequisitionerMiddleName(String requisitionerMiddleName) {
		this.requisitionerMiddleName = requisitionerMiddleName;
	}

	public String getOwnerMiddleName() {
		return ownerMiddleName;
	}

	public void setOwnerMiddleName(String ownerMiddleName) {
		this.ownerMiddleName = ownerMiddleName;
	}

	public String getCreatedByMiddleName() {
		return createdByMiddleName;
	}

	public void setCreatedByMiddleName(String createdByMiddleName) {
		this.createdByMiddleName = createdByMiddleName;
	}

	public String getModifiedByMiddleName() {
		return modifiedByMiddleName;
	}

	public void setModifiedByMiddleName(String modifiedByMiddleName) {
		this.modifiedByMiddleName = modifiedByMiddleName;
	}
	
	
}
