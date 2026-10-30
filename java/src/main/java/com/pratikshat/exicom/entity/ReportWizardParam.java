package com.pratikshat.exicom.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.Transient;

import org.apache.commons.lang.SerializationUtils;

import com.pratikshat.exicom.interceptor.ObjectAuditListener;

@Entity
@EntityListeners(ObjectAuditListener.class)
public class ReportWizardParam implements Serializable, OldPresist<ReportWizardParam> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	ReportWizard reportWizard;
	
	String paramIdentifier;
	String paramDisplay;
	String paramType;
	
	@Transient transient ReportWizardParam old;
	
	@Override
	@PostLoad
	public void old() {
		old=(ReportWizardParam)SerializationUtils.clone(this);
	}

	@Override
	public ReportWizardParam gOld() {
		
		return old;
	}

	public ReportWizardParam() {}
	public ReportWizardParam(Long id) {
		this.id=id;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ReportWizard getReportWizard() {
		return reportWizard;
	}

	public void setReportWizard(ReportWizard reportWizard) {
		this.reportWizard = reportWizard;
	}

	public String getParamIdentifier() {
		return paramIdentifier;
	}

	public void setParamIdentifier(String paramIdentifier) {
		this.paramIdentifier = paramIdentifier;
	}

	public String getParamDisplay() {
		return paramDisplay;
	}

	public void setParamDisplay(String paramDisplay) {
		this.paramDisplay = paramDisplay;
	}

	public String getParamType() {
		return paramType;
	}

	public void setParamType(String paramType) {
		this.paramType = paramType;
	}

}
