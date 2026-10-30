package com.pratikshat.exicom.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class SapSalesData {

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
	
	@Column(name="SALE_TYPE")
	String SALE_TYPE;
	
	@Column(name="BQTY")
	String BQTY;
	
	@Column(name="CIRCLE")
	String CIRCLE;
	
	@Column(name="CNAME")
	String CNAME;
	
	@Column(name="DESCRIPTION")
	String DESCRIPTION;
	
	@Column(name="DOCKET_NO")
	String DOCKET_NO;
	
	@Column(name="OQTY")
	String OQTY;
	
	@Column(name="PRICE")
	String PRICE;
	
	@Column(name="SAP_INVOICE")
	String SAP_INVOICE;
	
	@Column(name="SAP_INVOICE_DATE")
	String SAP_INVOICE_DATE;
	
	@Column(name="SO_DATE")
	String SO_DATE;
	
	@Column(name="SO_NUMBER")
	String SO_NUMBER;
	
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
	public String getSALE_TYPE() {
		return SALE_TYPE;
	}
	public void setSALE_TYPE(String sALE_TYPE) {
		SALE_TYPE = sALE_TYPE;
	}
	public String getBQTY() {
		return BQTY;
	}
	public void setBQTY(String bQTY) {
		BQTY = bQTY;
	}
	public String getCIRCLE() {
		return CIRCLE;
	}
	public void setCIRCLE(String cIRCLE) {
		CIRCLE = cIRCLE;
	}
	public String getCNAME() {
		return CNAME;
	}
	public void setCNAME(String cNAME) {
		CNAME = cNAME;
	}
	public String getDESCRIPTION() {
		return DESCRIPTION;
	}
	public void setDESCRIPTION(String dESCRIPTION) {
		DESCRIPTION = dESCRIPTION;
	}
	public String getDOCKET_NO() {
		return DOCKET_NO;
	}
	public void setDOCKET_NO(String dOCKET_NO) {
		DOCKET_NO = dOCKET_NO;
	}
	public String getOQTY() {
		return OQTY;
	}
	public void setOQTY(String oQTY) {
		OQTY = oQTY;
	}
	public String getPRICE() {
		return PRICE;
	}
	public void setPRICE(String pRICE) {
		PRICE = pRICE;
	}
	public String getSAP_INVOICE() {
		return SAP_INVOICE;
	}
	public void setSAP_INVOICE(String sAP_INVOICE) {
		SAP_INVOICE = sAP_INVOICE;
	}
	public String getSAP_INVOICE_DATE() {
		return SAP_INVOICE_DATE;
	}
	public void setSAP_INVOICE_DATE(String sAP_INVOICE_DATE) {
		SAP_INVOICE_DATE = sAP_INVOICE_DATE;
	}
	public String getSO_DATE() {
		return SO_DATE;
	}
	public void setSO_DATE(String sO_DATE) {
		SO_DATE = sO_DATE;
	}
	public String getSO_NUMBER() {
		return SO_NUMBER;
	}
	public void setSO_NUMBER(String sO_NUMBER) {
		SO_NUMBER = sO_NUMBER;
	}
	
	
	
	
}
