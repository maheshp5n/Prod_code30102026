package com.pratikshat.exicom.entity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners({AuditingEntityListener.class})
public class BtApprovalStapes extends Auditable<EmployeeDetails> {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	BtApproval approval;
		
	int stapeNumber;
	
	@Column(columnDefinition = "TEXT")
	String assignedTo;
	
	@Column(columnDefinition = "TEXT")
	String onApprove;
	@Column(columnDefinition = "TEXT")
	String onReject;
	
	String actionClassName;
	String classLoaderUrl;

	String stapeName;
	
	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id=id;
	}

	@Override
	public EmployeeDetails getOwner() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOwner(EmployeeDetails u) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getManualId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setManualId(String manualId) {
		// TODO Auto-generated method stub
		
	}

	public BtApproval getApproval() {
		return approval;
	}

	public void setApproval(BtApproval approval) {
		this.approval = approval;
	}

	public int getStapeNumber() {
		return stapeNumber;
	}

	public void setStapeNumber(int stapeNumber) {
		this.stapeNumber = stapeNumber;
	}

	public String getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo;
	}

	public String getOnApprove() {
		return onApprove;
	}

	public void setOnApprove(String onApprove) {
		this.onApprove = onApprove;
	}

	public String getOnReject() {
		return onReject;
	}

	public void setOnReject(String onReject) {
		this.onReject = onReject;
	}

	public String getActionClassName() {
		return actionClassName;
	}

	public void setActionClassName(String actionClassName) {
		this.actionClassName = actionClassName;
	}

	public String getClassLoaderUrl() {
		return classLoaderUrl;
	}

	public void setClassLoaderUrl(String classLoaderUrl) {
		this.classLoaderUrl = classLoaderUrl;
	}

	public String getStapeName() {
		return stapeName;
	}

	public void setStapeName(String stapeName) {
		this.stapeName = stapeName;
	}
	
	

}