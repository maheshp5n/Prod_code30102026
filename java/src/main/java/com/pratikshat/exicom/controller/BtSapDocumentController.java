package com.pratikshat.exicom.controller;

import java.util.List;
import java.util.Map;

import com.pratikshat.exicom.services.SapDocumentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("bt/api/sapdoc")
public class BtSapDocumentController {
    @Autowired
	SapDocumentService dbSap;
	
	@GetMapping("stkhdrdoc/{crmdocnumber}")
	public List<Map<String,Object>> getStkHdrDocument(@PathVariable("crmdocnumber")String crmdocnumber){
		return dbSap.getBtStkHdrDocument(crmdocnumber);
	}
	@GetMapping("stohdrdoc/{crmdocnumber}")
	public List<Map<String,Object>> getStoHdrDocument(@PathVariable("crmdocnumber")String crmdocnumber){
		return dbSap.getBtStoHdrDocument(crmdocnumber);
	}
}
