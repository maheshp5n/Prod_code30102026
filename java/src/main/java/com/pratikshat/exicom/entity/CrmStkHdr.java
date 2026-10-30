package com.pratikshat.exicom.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CrmStkHdr implements java.io.Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	String mblnr;
	String mjahr;
	String budat;
	String ebeln;
	String rwerks;
	String iwerks;
	String crmDocno;
	String ivnoiceNo;
	@Column(columnDefinition = "TEXT")
	String invoiceDoc;
	String sflag;
	String tflag;
	String sydat;
	String sytim;
	String remark;
	String crmDocdate;
	String gflag;
	String mblnrG;
	String mjahrG;
	String remarkG;
	String mblnrC;
	String mjahrC;
	String remarkC;
	String mblnrU;
	String mjahrU;
	String remarkU;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMblnr() {
		return mblnr;
	}
	public void setMblnr(String mblnr) {
		this.mblnr = mblnr;
	}
	public String getMjahr() {
		return mjahr;
	}
	public void setMjahr(String mjahr) {
		this.mjahr = mjahr;
	}
	public String getBudat() {
		return budat;
	}
	public void setBudat(String budat) {
		this.budat = budat;
	}
	public String getEbeln() {
		return ebeln;
	}
	public void setEbeln(String ebeln) {
		this.ebeln = ebeln;
	}
	public String getRwerks() {
		return rwerks;
	}
	public void setRwerks(String rwerks) {
		this.rwerks = rwerks;
	}
	public String getIwerks() {
		return iwerks;
	}
	public void setIwerks(String iwerks) {
		this.iwerks = iwerks;
	}
	public String getCrmDocno() {
		return crmDocno;
	}
	public void setCrmDocno(String crmDocno) {
		this.crmDocno = crmDocno;
	}
	public String getIvnoiceNo() {
		return ivnoiceNo;
	}
	public void setIvnoiceNo(String ivnoiceNo) {
		this.ivnoiceNo = ivnoiceNo;
	}
	public String getInvoiceDoc() {
		return invoiceDoc;
	}
	public void setInvoiceDoc(String invoiceDoc) {
		this.invoiceDoc = invoiceDoc;
	}
	public String getSflag() {
		return sflag;
	}
	public void setSflag(String sflag) {
		this.sflag = sflag;
	}
	public String getTflag() {
		return tflag;
	}
	public void setTflag(String tflag) {
		this.tflag = tflag;
	}
	public String getSydat() {
		return sydat;
	}
	public void setSydat(String sydat) {
		this.sydat = sydat;
	}
	public String getSytim() {
		return sytim;
	}
	public void setSytim(String sytim) {
		this.sytim = sytim;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCrmDocdate() {
		return crmDocdate;
	}
	public void setCrmDocdate(String crmDocdate) {
		this.crmDocdate = crmDocdate;
	}
	public String getGflag() {
		return gflag;
	}
	public void setGflag(String gflag) {
		this.gflag = gflag;
	}
	public String getMblnrG() {
		return mblnrG;
	}
	public void setMblnrG(String mblnrG) {
		this.mblnrG = mblnrG;
	}
	public String getMjahrG() {
		return mjahrG;
	}
	public void setMjahrG(String mjahrG) {
		this.mjahrG = mjahrG;
	}
	public String getRemarkG() {
		return remarkG;
	}
	public void setRemarkG(String remarkG) {
		this.remarkG = remarkG;
	}
	public String getMblnrC() {
		return mblnrC;
	}
	public void setMblnrC(String mblnrC) {
		this.mblnrC = mblnrC;
	}
	public String getMjahrC() {
		return mjahrC;
	}
	public void setMjahrC(String mjahrC) {
		this.mjahrC = mjahrC;
	}
	public String getRemarkC() {
		return remarkC;
	}
	public void setRemarkC(String remarkC) {
		this.remarkC = remarkC;
	}
	public String getMblnrU() {
		return mblnrU;
	}
	public void setMblnrU(String mblnrU) {
		this.mblnrU = mblnrU;
	}
	public String getMjahrU() {
		return mjahrU;
	}
	public void setMjahrU(String mjahrU) {
		this.mjahrU = mjahrU;
	}
	public String getRemarkU() {
		return remarkU;
	}
	public void setRemarkU(String remarkU) {
		this.remarkU = remarkU;
	}
	
	
}
