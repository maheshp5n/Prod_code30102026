package com.pratikshat.exicom.model.response;

import java.util.Date;

import com.pratikshat.exicom.entity.SSA;

public class SSAResponse {

	long id;
	
	String name;

	String circleName;
	
	String createdByEmailId;
	String createdByFirstName;
	String createdByMiddleName;
	String createdByLastName;
	Date createdDate;
	
	String modifiedByEmailId;
	String modifiedByFirstName;
	String modifiedByMiddleName;
	String modifiedByLastName;
	Date modifiedDate;
	
	String ownerByEmailId;
	String ownerByFirstName;
	String ownerByMiddleName;
	String ownerByLastName;
	
	public SSAResponse() {}
	
	public SSAResponse(SSA ssa) {
		
		this.id = ssa.getId();
		
		this.name = ssa.getName();

		if(ssa.getCircle()!=null) {
			this.circleName = ssa.getCircle().getCircleName();
		}
		
		if(ssa.getCreatedBy()!=null) {
			this.createdByEmailId = ssa.getCreatedBy().getEmailId();
			this.createdByFirstName = ssa.getCreatedBy().getFirstName();
			this.createdByMiddleName = ssa.getCreatedBy().getMiddleName();
			this.createdByLastName = ssa.getCreatedBy().getLastName();
			this.createdDate = ssa.getCreatedDate();
		}
		
		if(ssa.getModifiedBy()!=null) {
			this.modifiedByEmailId = ssa.getModifiedBy().getEmailId();
			this.modifiedByFirstName = ssa.getModifiedBy().getFirstName();
			this.modifiedByMiddleName = ssa.getModifiedBy().getMiddleName();
			this.modifiedByLastName = ssa.getModifiedBy().getLastName();
			this.modifiedDate = ssa.getModifiedDate();
		}
		
		if(ssa.getOwner()!=null) {
			this.ownerByEmailId = ssa.getOwner().getEmailId();
			this.ownerByFirstName = ssa.getOwner().getFirstName();
			this.ownerByMiddleName = ssa.getOwner().getMiddleName();
			this.ownerByLastName = ssa.getOwner().getLastName();
		}
		
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCircleName() {
		return circleName;
	}
	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}
	public String getCreatedByEmailId() {
		return createdByEmailId;
	}
	public void setCreatedByEmailId(String createdByEmailId) {
		this.createdByEmailId = createdByEmailId;
	}
	public String getCreatedByFirstName() {
		return createdByFirstName;
	}
	public void setCreatedByFirstName(String createdByFirstName) {
		this.createdByFirstName = createdByFirstName;
	}
	public String getCreatedByMiddleName() {
		return createdByMiddleName;
	}
	public void setCreatedByMiddleName(String createdByMiddleName) {
		this.createdByMiddleName = createdByMiddleName;
	}
	public String getCreatedByLastName() {
		return createdByLastName;
	}
	public void setCreatedByLastName(String createdByLastName) {
		this.createdByLastName = createdByLastName;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getModifiedByEmailId() {
		return modifiedByEmailId;
	}
	public void setModifiedByEmailId(String modifiedByEmailId) {
		this.modifiedByEmailId = modifiedByEmailId;
	}
	public String getModifiedByFirstName() {
		return modifiedByFirstName;
	}
	public void setModifiedByFirstName(String modifiedByFirstName) {
		this.modifiedByFirstName = modifiedByFirstName;
	}
	public String getModifiedByMiddleName() {
		return modifiedByMiddleName;
	}
	public void setModifiedByMiddleName(String modifiedByMiddleName) {
		this.modifiedByMiddleName = modifiedByMiddleName;
	}
	public String getModifiedByLastName() {
		return modifiedByLastName;
	}
	public void setModifiedByLastName(String modifiedByLastName) {
		this.modifiedByLastName = modifiedByLastName;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public String getOwnerByEmailId() {
		return ownerByEmailId;
	}
	public void setOwnerByEmailId(String ownerByEmailId) {
		this.ownerByEmailId = ownerByEmailId;
	}
	public String getOwnerByFirstName() {
		return ownerByFirstName;
	}
	public void setOwnerByFirstName(String ownerByFirstName) {
		this.ownerByFirstName = ownerByFirstName;
	}
	public String getOwnerByMiddleName() {
		return ownerByMiddleName;
	}
	public void setOwnerByMiddleName(String ownerByMiddleName) {
		this.ownerByMiddleName = ownerByMiddleName;
	}
	public String getOwnerByLastName() {
		return ownerByLastName;
	}
	public void setOwnerByLastName(String ownerByLastName) {
		this.ownerByLastName = ownerByLastName;
	}
}
