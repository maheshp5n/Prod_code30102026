package com.pratikshat.exicom.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pratikshat.exicom.annotation.CurrentUser;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.repository.CaseProblemReportedRepository;
import com.pratikshat.exicom.repository.ExpenseManagementRepository;
import com.pratikshat.exicom.repository.LocationRepository;
import com.pratikshat.exicom.repository.PreRegistrationCasesRepository;
import com.pratikshat.exicom.repository.SaEngRatingRepository;
import com.pratikshat.exicom.repository.ServiceAppointmentRepository;
import com.pratikshat.exicom.repository.UserProfileRepository;


@RestController
@RequestMapping("api/mobile/dashboard")
public class EngineerDashboardController {
	
	@Autowired
	UserProfileRepository dbProfile;
	
	@Autowired
	ServiceAppointmentRepository dbServiceAppointment;
	
	@Autowired
	PreRegistrationCasesRepository dbPreRegistrationCases;
	
	@Autowired
	ExpenseManagementRepository dbExpenseManagement;
	
	@Autowired 
	SaEngRatingRepository saRatingDb;
	
	@Autowired
	LocationRepository dbLocation;
	
	@Autowired
	CaseProblemReportedRepository dbCaseProblem;
	
	@GetMapping("sa")
	public Map<String,Object> getServiceAppointmentOpenVsTotal(@CurrentUser LoginUser user,@RequestParam("type")String type){
		List<String> userList=dbProfile.getListOfUserAndChildByUser(user.getUsername());
		Map<String,Object> map = null;
		if(type!=null && type.equalsIgnoreCase("MTD")) {
			map = dbServiceAppointment.getServiceAppointment_open_vs_total_mtd(userList);
			if(map!=null && !map.isEmpty()) {
				return map;
			}else {
				map = new HashMap<String,Object>();
				map.put("total count", 0);
				map.put("open count", 0);
				
				return map;
			}
		}else if(type!=null && type.equalsIgnoreCase("YTD")) {
			map = dbServiceAppointment.getServiceAppointment_open_vs_total_ytd(userList);
			if(map!=null && !map.isEmpty()) {
				return map;
			}else {
				map = new HashMap<String,Object>();
				map.put("total count", 0);
				map.put("open count", 0);
				
				return map;
			}
		}else {
			map = new HashMap<String,Object>();
			map.put("total count", 0);
			map.put("open count", 0);
			
			return map;
		}
	}
	
	
	@GetMapping("prc")
	public Map<String,Object> getPRCOpenVsTotal(@CurrentUser LoginUser user,@RequestParam("type")String type){
		List<String> userList=dbProfile.getListOfUserAndChildByUser(user.getUsername());
		Map<String,Object> map = null;
		if(type!=null && type.equalsIgnoreCase("MTD")) {
			map = dbPreRegistrationCases.getPRC_open_vs_total_mtd(userList);
			if(map!=null && !map.isEmpty()) {
				return map;
			}else {
				map = new HashMap<String,Object>();
				map.put("total count", 0);
				map.put("open count", 0);
				
				return map;
			}
		}else if(type!=null && type.equalsIgnoreCase("YTD")) {
			map = dbPreRegistrationCases.getPRC_open_vs_total_ytd(userList);
			if(map!=null && !map.isEmpty()) {
				return map;
			}else {
				map = new HashMap<String,Object>();
				map.put("total count", 0);
				map.put("open count", 0);
				
				return map;
			}
		}else {
			map = new HashMap<String,Object>();
			map.put("total count", 0);
			map.put("open count", 0);
			
			return map;
		}
	}
	
	
	@GetMapping("expense")
	public Map<String,Object> getGenAndSAExpense(@CurrentUser LoginUser user,@RequestParam("type")String type){
		Map<String,Object> map = null;
		if(type!=null && type.equalsIgnoreCase("MTD")) {
			map = dbExpenseManagement.getGenAndSAExpense_mtd(user.getUsername());
			if(map!=null && !map.isEmpty()) {
				return map;
			}else {
				map = new HashMap<String,Object>();
				map.put("Gen Exp", 0);
				map.put("SA Exp", 0);
				
				return map;
			}
		}else if(type!=null && type.equalsIgnoreCase("YTD")) {
			map = dbExpenseManagement.getGenAndSAExpense_ytd(user.getUsername());
			if(map!=null && !map.isEmpty()) {
				return map;
			}else {
				map = new HashMap<String,Object>();
				map.put("Gen Exp", 0);
				map.put("SA Exp", 0);
				
				return map;
			}
		}else {
			map = new HashMap<String,Object>();
			map.put("Gen Exp", 0);
			map.put("SA Exp", 0);
			
			return map;
		}
	}
	
	
	@GetMapping("rating")
	public Map<String,Object> getRating(@CurrentUser LoginUser user){
		Map<String,Object> map = null;
		
		map = saRatingDb.getRating(user.getUsername());
		if(map!=null && !map.isEmpty()) {
			return map;
		}else {
			map = new HashMap<String,Object>();
			map.put("Avg Rating", 0);
			map.put("Max Rating", 0);
			
			return map;
		}
	}
	
	
	@GetMapping("inventory")
	public Map<String,Object> getInventory(@CurrentUser LoginUser user){
		Map<String,Object> map = null;
		
		map = dbLocation.getInventory(user.getUsername());
		if(map!=null && !map.isEmpty()) {
			return map;
		}else {
			map = new HashMap<String,Object>();
			map.put("BER", 0);
			map.put("DOA", 0);
			map.put("Faulty", 0);
			map.put("Ok", 0);
			map.put("Rejected", 0);
			map.put("Scrap", 0);
			return map;
		}
	}
	
	
	@GetMapping("complaint")
	public List<Map<String,Object>> getComplaint(@CurrentUser LoginUser user,@RequestParam("type")String type, @RequestParam("limit")int limit){
		List<Map<String,Object>> map = null;
		List<String> userList=dbProfile.getListOfUserAndChildByUser(user.getUsername());
		if(type!=null && type.equalsIgnoreCase("MTD")) {
			map = dbCaseProblem.getComplaint_mtd(userList,limit);
			if(map!=null && !map.isEmpty()) {
				return map;
			}else {
				map = new ArrayList<>();
				
				return map;
			}
		}else if(type!=null && type.equalsIgnoreCase("YTD")) {
			map = dbCaseProblem.getComplaint_ytd(userList,limit);
			if(map!=null && !map.isEmpty()) {
				return map;
			}else {
				map = new ArrayList<>();

				return map;
			}
		}else {
			map = new ArrayList<>();
			
			return map;
		}
	}

}
