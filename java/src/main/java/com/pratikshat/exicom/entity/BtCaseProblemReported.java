package com.pratikshat.exicom.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;

import org.apache.commons.lang.SerializationUtils;
import org.springframework.data.annotation.Transient;

import com.pratikshat.exicom.interceptor.ObjectAuditListener;

@Entity
@EntityListeners(ObjectAuditListener.class)
public class BtCaseProblemReported implements Serializable, OldPresist<BtCaseProblemReported>{

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		long probleReportedId;
		String problemName;
		@ManyToOne(cascade = CascadeType.MERGE)
		BtCase caseDetails;
		boolean synced;

		public BtCaseProblemReported() {
		}
		
		public BtCaseProblemReported(long probleReportedId, String problemName) {
			this.probleReportedId = probleReportedId;
			this.problemName = problemName;
		}
		public long getProbleReportedId() {
			return probleReportedId;
		}
		public void setProbleReportedId(long probleReportedId) {
			this.probleReportedId = probleReportedId;
		}
		public String getProblemName() {
			return problemName;
		}
		public void setProblemName(String problemName) {
			this.problemName = problemName;
		}
		public BtCase getCaseDetails() {
			return caseDetails;
		}
		public void setCaseDetails(BtCase caseDetails) {
			this.caseDetails = caseDetails;
		}
		public boolean isSynced() {
			return synced;
		}
		public void setSynced(boolean synced) {
			this.synced = synced;
		}
	@Transient transient BtCaseProblemReported old;
	
	@PostLoad
	@Override
	public void old() {
		old=(BtCaseProblemReported) SerializationUtils.clone(this);
		
	}
	@Override
	public BtCaseProblemReported gOld() {
		
		return old;
	}

}
