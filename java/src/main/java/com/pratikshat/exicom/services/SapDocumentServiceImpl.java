package com.pratikshat.exicom.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.ftp.session.FtpRemoteFileTemplate;
import org.springframework.stereotype.Service;

import com.pratikshat.exicom.sap.SapConnectionProvider;
import com.pratikshat.exicom.sap.SapEssProvider;
import org.springframework.beans.factory.annotation.Value;

@Service
public class SapDocumentServiceImpl implements SapDocumentService {

	@Autowired
	SapConnectionProvider sap;
	@Autowired
	FtpRemoteFileTemplate ftp;

	@Value("${ftp.username}")
	String username;
	@Value("${ftp.password}")
	String pw;
	@Value("${ftp.host}")
	String host;
	@Value("${ftp.port}")
	int port;

	@Autowired
	SapEssProvider btsap;

	private final static Logger LOGGER = LoggerFactory.getLogger(SapDocumentServiceImpl.class);

	@Override
	public List<Map<String, Object>> getStkHdrDocument(String crmdocnumber) {

		try {

			List<Map<String, Object>> resultdata = sap.getResultSet(
					"SELECT CRM_DOCNO AS hdrcrmdoc,INVOICE_NO,Invoice_Doc,'' AS rqdocnumber FROM CRM_STK_HDR where MBLNR IN (SELECT DISTINCT MBLNR FROM CRM_STK_TRANSFER WHERE CRM_DOCNO=? OR CRM_TRN_DOC_NO=?)",
					new Object[] { crmdocnumber, crmdocnumber });
			if (resultdata != null && resultdata.size() > 0) {
				for (Map<String, Object> data : resultdata) {
					if (data.get("Invoice_Doc") != null && data.get("Invoice_Doc").toString().startsWith("ftp://")) {

						data.put("Invoice_Doc", normlizePathAndgetFileAsString(data.get("Invoice_Doc").toString()));
					}
				}
			}

			return resultdata;
		} catch (Exception e) {
			LOGGER.error("error ==> {}", e);
			e.printStackTrace();
		}
		return new ArrayList<Map<String, Object>>();
	}

	@Override
	public List<Map<String, Object>> getStoHdrDocument(String crmdocnumber) {
		try {

			List<Map<String, Object>> resultdata = sap.getResultSet(
					"select distinct hdr.EBELN,hdr.INVOICE,hdr.Document1,hdr.Document2,hdr.Document3,hdr.Document4 from CRM_STO_HDR hdr  where hdr.CRM_DOCNO=? or hdr.PR_CRM_DOCNO=?",
					new Object[] { crmdocnumber, crmdocnumber });
			if (resultdata != null && resultdata.size() > 0) {
				for (Map<String, Object> data : resultdata) {
					if (data.get("Document1") != null && data.get("Document1").toString().startsWith("ftp://"))
						data.put("Document1", normlizePathAndgetFileAsString(data.get("Document1").toString()));
					if (data.get("Document2") != null && data.get("Document2").toString().startsWith("ftp://"))
						data.put("Document2", normlizePathAndgetFileAsString(data.get("Document2").toString()));
					if (data.get("Document3") != null && data.get("Document3").toString().startsWith("ftp://"))
						data.put("Document3", normlizePathAndgetFileAsString(data.get("Document3").toString()));
					if (data.get("Document4") != null && data.get("Document4").toString().startsWith("ftp://"))
						data.put("Document4", normlizePathAndgetFileAsString(data.get("Document4").toString()));
				}
			}
			return resultdata;

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new ArrayList<Map<String, Object>>();
	}

	@Override
	public List<Map<String, Object>> getBtStkHdrDocument(String crmdocnumber) {
		try {

			List<Map<String, Object>> resultdata = btsap.getResultSet(
					"SELECT CRM_DOCNO AS hdrcrmdoc,INVOICE_NO,Invoice_Doc,'' AS rqdocnumber FROM CRM_BT_STK_HDR where MBLNR IN (SELECT DISTINCT MBLNR FROM CRM_BT_STK_TRANSFER WHERE CRM_DOCNO=? OR CRM_TRN_DOC_NO=?)",
					new Object[] { crmdocnumber, crmdocnumber });
			if (resultdata != null && resultdata.size() > 0) {
				for (Map<String, Object> data : resultdata) {
					if (data.get("Invoice_Doc") != null && data.get("Invoice_Doc").toString().startsWith("ftp://")) {
						data.put("Invoice_Doc", normlizePathAndgetFileAsString(data.get("Invoice_Doc").toString()));
					}
				}
			}

			return resultdata;
		} catch (Exception e) {

			e.printStackTrace();
		}
		return new ArrayList<Map<String, Object>>();
	}

	@Override
	public List<Map<String, Object>> getBtStoHdrDocument(String crmdocnumber) {
		try {

			List<Map<String, Object>> resultdata = btsap.getResultSet(
					"select distinct hdr.EBELN,hdr.INVOICE,hdr.Document1,hdr.Document2,hdr.Document3,hdr.Document4 from CRM_BT_STO_HDR hdr  where hdr.CRM_DOCNO=? or hdr.PR_CRM_DOCNO=?",
					new Object[] { crmdocnumber, crmdocnumber });
			if (resultdata != null && resultdata.size() > 0) {
				for (Map<String, Object> data : resultdata) {
					if (data.get("Document1") != null && data.get("Document1").toString().startsWith("ftp://"))
						data.put("Document1", normlizePathAndgetFileAsString(data.get("Document1").toString()));
					if (data.get("Document2") != null && data.get("Document2").toString().startsWith("ftp://"))
						data.put("Document2", normlizePathAndgetFileAsString(data.get("Document2").toString()));
					if (data.get("Document3") != null && data.get("Document3").toString().startsWith("ftp://"))
						data.put("Document3", normlizePathAndgetFileAsString(data.get("Document3").toString()));
					if (data.get("Document4") != null && data.get("Document4").toString().startsWith("ftp://"))
						data.put("Document4", normlizePathAndgetFileAsString(data.get("Document4").toString()));
				}
			}
			return resultdata;

		} catch (Exception ex) {
			LOGGER.error("error => {}", ex);
			ex.printStackTrace();
		}
		return new ArrayList<Map<String, Object>>();
	}

	String getFileAsString(String path) {
		// path=path.replace("fpt://", "");
		if(ftp.exists(path)){
			InputStream fileStream = ftp.execute(session -> session.readRaw(path));
			
			try {
				
				return org.apache.commons.net.util.Base64.encodeBase64String(IOUtils.toByteArray(fileStream));
	
			} catch (IOException e) {
				LOGGER.error("unable to get file {} from ftp", path);
				e.printStackTrace();
			}
		}
		
		return null;
	}

	String normlizePathAndgetFileAsString(String path) {
		path = path.replace("ftp://", "");
		path = path.replace(host, "");
		path = path.replace(":" + port, "");
		return getFileAsString(path);
	}
}
