package com.pratikshat.exicom.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pratikshat.exicom.services.SapDocumentService;

@RestController
@RequestMapping("api/sapdoc")
public class SapDocumentController {
	@Autowired
	SapDocumentService dbSap;
	
	@GetMapping("stkhdrdoc/{crmdocnumber}")
	public List<Map<String,Object>> getStkHdrDocument(@PathVariable("crmdocnumber")String crmdocnumber){
		return dbSap.getStkHdrDocument(crmdocnumber);
	}
	@GetMapping("stohdrdoc/{crmdocnumber}")
	public List<Map<String,Object>> getStoHdrDocument(@PathVariable("crmdocnumber")String crmdocnumber){
		return dbSap.getStoHdrDocument(crmdocnumber);
	}
}
