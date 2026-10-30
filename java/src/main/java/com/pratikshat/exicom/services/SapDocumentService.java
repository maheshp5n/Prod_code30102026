package com.pratikshat.exicom.services;

import java.util.List;
import java.util.Map;



public interface SapDocumentService {

	List<Map<String,Object>> getStkHdrDocument(String crmdocnumber);
	List<Map<String,Object>> getStoHdrDocument(String crmdocnumber);

	List<Map<String,Object>> getBtStkHdrDocument(String crmdocnumber);
	List<Map<String,Object>> getBtStoHdrDocument(String crmdocnumber);
}
