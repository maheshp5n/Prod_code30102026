package com.pratikshat.exicom.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CsToFactoryMovement {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	@Column(name = "VBELN")
	String VBELN;
	@Column(name = "BSTKD")
	String BSTKD;
	@Column(name = "BSTDK")
	String BSTDK;
	@Column(name = "KUNNR")
	String KUNNR;
	@Column(name = "SKUNNR")
	String SKUNNR;
	@Column(name = "MBLNR")
	String MBLNR;
	@Column(name = "MJAHR")
	String MJAHR;
	@Column(name = "INVOICE")
	String INVOICE;
	@Column(name = "INVOICE_DATE")
	String INVOICE_DATE;
	@Column(name = "WADAT")
	String WADAT;
	@Column(name = "WERKS")
	String WERKS;
	@Column(name = "LGORT")
	String LGORT;
	@Column(name = "MATNR")
	String MATNR;
	@Column(name = "SERNR")
	String SERNR;
	@Column(name = "LFIMG")
	String LFIMG;
	@Column(name = "CRM_DOCNO")
	String CRM_DOCNO;
	@Column(name = "SFLAG")
	String SFLAG;
	@Column(name = "TFLAG")
	String TFLAG;
	@Column(name = "SYDAT")
	String SYDAT;
	@Column(name = "SYTIM")
	String SYTIM;
	@Column(name = "REMARK")
	String REMARK;
	
	@Column(name="POSNR")
	String POSNR;
	
	@Column(name="BWARTWA")
	String BWARTWA;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getVBELN() {
		return VBELN;
	}
	public void setVBELN(String vBELN) {
		VBELN = vBELN;
	}
	public String getBSTKD() {
		return BSTKD;
	}
	public void setBSTKD(String bSTKD) {
		BSTKD = bSTKD;
	}
	public String getBSTDK() {
		return BSTDK;
	}
	public void setBSTDK(String bSTDK) {
		BSTDK = bSTDK;
	}
	public String getKUNNR() {
		return KUNNR;
	}
	public void setKUNNR(String kUNNR) {
		KUNNR = kUNNR;
	}
	public String getSKUNNR() {
		return SKUNNR;
	}
	public void setSKUNNR(String sKUNNR) {
		SKUNNR = sKUNNR;
	}
	public String getMBLNR() {
		return MBLNR;
	}
	public void setMBLNR(String mBLNR) {
		MBLNR = mBLNR;
	}
	public String getMJAHR() {
		return MJAHR;
	}
	public void setMJAHR(String mJAHR) {
		MJAHR = mJAHR;
	}
	public String getINVOICE() {
		return INVOICE;
	}
	public void setINVOICE(String iNVOICE) {
		INVOICE = iNVOICE;
	}
	public String getINVOICE_DATE() {
		return INVOICE_DATE;
	}
	public void setINVOICE_DATE(String iNVOICE_DATE) {
		INVOICE_DATE = iNVOICE_DATE;
	}
	public String getWADAT() {
		return WADAT;
	}
	public void setWADAT(String wADAT) {
		WADAT = wADAT;
	}
	public String getWERKS() {
		return WERKS;
	}
	public void setWERKS(String wERKS) {
		WERKS = wERKS;
	}
	public String getLGORT() {
		return LGORT;
	}
	public void setLGORT(String lGORT) {
		LGORT = lGORT;
	}
	public String getMATNR() {
		return MATNR;
	}
	public void setMATNR(String mATNR) {
		MATNR = mATNR;
	}
	public String getSERNR() {
		return SERNR;
	}
	public void setSERNR(String sERNR) {
		SERNR = sERNR;
	}
	public String getLFIMG() {
		return LFIMG;
	}
	public void setLFIMG(String lFIMG) {
		LFIMG = lFIMG;
	}
	public String getCRM_DOCNO() {
		return CRM_DOCNO;
	}
	public void setCRM_DOCNO(String cRM_DOCNO) {
		CRM_DOCNO = cRM_DOCNO;
	}
	public String getSFLAG() {
		return SFLAG;
	}
	public void setSFLAG(String sFLAG) {
		SFLAG = sFLAG;
	}
	public String getTFLAG() {
		return TFLAG;
	}
	public void setTFLAG(String tFLAG) {
		TFLAG = tFLAG;
	}
	public String getSYDAT() {
		return SYDAT;
	}
	public void setSYDAT(String sYDAT) {
		SYDAT = sYDAT;
	}
	public String getSYTIM() {
		return SYTIM;
	}
	public void setSYTIM(String sYTIM) {
		SYTIM = sYTIM;
	}
	public String getREMARK() {
		return REMARK;
	}
	public void setREMARK(String rEMARK) {
		REMARK = rEMARK;
	}
	public String getPOSNR() {
		return POSNR;
	}
	public void setPOSNR(String pOSNR) {
		POSNR = pOSNR;
	}
	public String getBWARTWA() {
		return BWARTWA;
	}
	public void setBWARTWA(String bWARTWA) {
		BWARTWA = bWARTWA;
	}
	
	
	
}
