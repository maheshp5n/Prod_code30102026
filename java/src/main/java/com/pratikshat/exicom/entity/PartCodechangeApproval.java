package com.pratikshat.exicom.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class PartCodechangeApproval {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	String initRole;
	
	String assignedToRoles;
	
	Boolean active;
	
	Float allowedVariance;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getInitRole() {
		return initRole;
	}
	public void setInitRole(String initRole) {
		this.initRole = initRole;
	}
	public String getAssignedToRoles() {
		return assignedToRoles;
	}
	public void setAssignedToRoles(String assignedToRoles) {
		this.assignedToRoles = assignedToRoles;
	}
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
	public Float getAllowedVariance() {
		return allowedVariance;
	}
	public void setAllowedVariance(Float allowedVariance) {
		this.allowedVariance = allowedVariance;
	}
		
	
}
