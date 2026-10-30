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
public class ReportWizardExecuter implements Serializable, OldPresist<ReportWizardExecuter>{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	ReportWizard reportWizard;
	
	String executerType;
	String executer;
	
	@Transient transient ReportWizardExecuter old;
	
	@Override
	@PostLoad
	public void old() {
		old=(ReportWizardExecuter)SerializationUtils.clone(this);
	}
	@Override
	public ReportWizardExecuter gOld() {
		// TODO Auto-generated method stub
		return old;
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
	public String getExecuterType() {
		return executerType;
	}
	public void setExecuterType(String executerType) {
		this.executerType = executerType;
	}
	public String getExecuter() {
		return executer;
	}
	public void setExecuter(String executer) {
		this.executer = executer;
	}
	
	
	
}
