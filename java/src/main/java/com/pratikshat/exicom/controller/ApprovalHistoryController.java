package com.pratikshat.exicom.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pratikshat.exicom.entity.ToBeApproved;

@RestController
@RequestMapping("api/approvalhistory")
public class ApprovalHistoryController {

	@Autowired
	com.pratikshat.exicom.repository.ToBeApprovedRepository dbToBeApproved;
	
	
	@GetMapping
	public List<java.util.Map<String,Object>> getApprovalHistory(@RequestParam("objectName")String objectName,@RequestParam("objectId")long objectId) {
		
		List<Map<String,Object>> d=dbToBeApproved.findByObjectNameAndObjectId(objectName, objectId);
		
		return d;
	}
}
