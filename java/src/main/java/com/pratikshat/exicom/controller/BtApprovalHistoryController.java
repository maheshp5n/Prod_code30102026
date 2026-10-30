package com.pratikshat.exicom.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;

@RestController
@RequestMapping("bt/api/approvalhistory")
public class BtApprovalHistoryController {
	

	@Autowired
	com.pratikshat.exicom.services.BtTobeApprovedService btToApproveService;
	
	
	@GetMapping
	public List<java.util.Map<String,Object>> getApprovalHistory(@RequestParam("objectName")String objectName,@RequestParam("objectId")long objectId) throws BadRequestException, UnauthoriseException {
			
	
			List<Map<String,Object>> d=btToApproveService.getApprovalHistory(objectName, objectId);
		
		return d;

		}

	}
