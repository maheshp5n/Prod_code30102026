package com.pratikshat.exicom.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CrmStoHdr implements java.io.Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	String crmDocno;
	String budat;
	String iwersks;
	String isloc;
	String ebeln;
	@Column(columnDefinition = "TEXT")
	String document1;
	String sflag;
	String tflag;
	String deliv;
	String remarkD;
	String invoice;
	String remarkI;
	String mblnrG;
	String mjahrG;
	String remarkG;
	String sydat;
	String sytim;
	String remark;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCrmDocno() {
		return crmDocno;
	}
	public void setCrmDocno(String crmDocno) {
		this.crmDocno = crmDocno;
	}
	public String getBudat() {
		return budat;
	}
	public void setBudat(String budat) {
		this.budat = budat;
	}
	public String getIwersks() {
		return iwersks;
	}
	public void setIwersks(String iwersks) {
		this.iwersks = iwersks;
	}
	public String getIsloc() {
		return isloc;
	}
	public void setIsloc(String isloc) {
		this.isloc = isloc;
	}
	public String getEbeln() {
		return ebeln;
	}
	public void setEbeln(String ebeln) {
		this.ebeln = ebeln;
	}
	public String getDocument1() {
		return document1;
	}
	public void setDocument1(String document1) {
		this.document1 = document1;
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
	public String getDeliv() {
		return deliv;
	}
	public void setDeliv(String deliv) {
		this.deliv = deliv;
	}
	public String getRemarkD() {
		return remarkD;
	}
	public void setRemarkD(String remarkD) {
		this.remarkD = remarkD;
	}
	public String getInvoice() {
		return invoice;
	}
	public void setInvoice(String invoice) {
		this.invoice = invoice;
	}
	public String getRemarkI() {
		return remarkI;
	}
	public void setRemarkI(String remarkI) {
		this.remarkI = remarkI;
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
	
	
}
