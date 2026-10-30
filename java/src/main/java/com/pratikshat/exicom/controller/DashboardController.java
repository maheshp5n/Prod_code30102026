package com.pratikshat.exicom.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/dashboard")
public class DashboardController {
	@Autowired
	com.pratikshat.exicom.repository.CaseDetailsRepository dbCase;
	@GetMapping
	public List<Map<String,Object>> getCaseDetails(){
		return dbCase.getCaseTotal();
	}
	@GetMapping("qrcforactivecustomer")
	public List<Map<String,Object>> getQrcForActiveCustomer(){
		return dbCase.getQRCActionCustomer();
	}
	@GetMapping("topfivecomplaints")
	public List<Map<String,Object>> getTop5compliants(){
		return dbCase.getTop5Complaints();
	}
	@GetMapping("agingofcloser")
	public List<Map<String,Object>> getAgingOfCloserComplaint(){
		return dbCase.getAgingOfCloserComplaints();
	}
	@GetMapping("paidrepair")
	public List<Map<String,Object>> getPaidRepairDetails(){
		return dbCase.getPaidRepairDashboard();
	}
}
