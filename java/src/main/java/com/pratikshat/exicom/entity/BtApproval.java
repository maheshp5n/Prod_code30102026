package com.pratikshat.exicom.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@EntityListeners({AuditingEntityListener.class})
public class BtApproval extends Auditable<EmployeeDetails>{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	String objectName;
	String objectClassName;
	String objectIdFeildName;
	int totalStapes;
	String description;
	
	boolean active;
	
	@Column(columnDefinition = "TEXT")
	String initialSubmitters;
	
	String ctClassName;
	String ctClassPath;
	@Column(columnDefinition = "TEXT")
	String ctOnAction;
	
	public BtApproval() {}
	public BtApproval(Long id) {
		this.id=id;
	}
	
	@Override
	public Long getId() {
		
		return id;
	}
	@Override
	public void setId(Long id) {
		this.id=id;
	}
	@Override
	public EmployeeDetails getOwner() {
		
		return null;
	}
	@Override
	public void setOwner(EmployeeDetails u) {
		
		
	}
	@Override
	public String getManualId() {
		
		return null;
	}
	@Override
	public void setManualId(String manualId) {
		
	}
	public String getObjectName() {
		return objectName;
	}
	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}
	public String getObjectClassName() {
		return objectClassName;
	}
	public void setObjectClassName(String objectClassName) {
		this.objectClassName = objectClassName;
	}
	public String getObjectIdFeildName() {
		return objectIdFeildName;
	}
	public void setObjectIdFeildName(String objectIdFeildName) {
		this.objectIdFeildName = objectIdFeildName;
	}
	public int getTotalStapes() {
		return totalStapes;
	}
	public void setTotalStapes(int totalStapes) {
		this.totalStapes = totalStapes;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public String getInitialSubmitters() {
		return initialSubmitters;
	}
	public void setInitialSubmitters(String initialSubmitters) {
		this.initialSubmitters = initialSubmitters;
	}
	public String getCtClassName() {
		return ctClassName;
	}
	public void setCtClassName(String ctClassName) {
		this.ctClassName = ctClassName;
	}
	public String getCtClassPath() {
		return ctClassPath;
	}
	public void setCtClassPath(String ctClassPath) {
		this.ctClassPath = ctClassPath;
	}
	public String getCtOnAction() {
		return ctOnAction;
	}
	public void setCtOnAction(String ctOnAction) {
		this.ctOnAction = ctOnAction;
	}
	
}
