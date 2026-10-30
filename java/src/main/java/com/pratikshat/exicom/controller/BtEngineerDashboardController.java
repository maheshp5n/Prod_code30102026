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
import com.pratikshat.exicom.repository.BtCaseProblemReportedRepository;
import com.pratikshat.exicom.repository.BtExpenseManagementRepository;
import com.pratikshat.exicom.repository.BtPreRegistrationCasesRepository;
import com.pratikshat.exicom.repository.BtSaEngRatingRepository;
import com.pratikshat.exicom.repository.BtServiceAppointmentRepository;
import com.pratikshat.exicom.repository.LocationRepository;
import com.pratikshat.exicom.repository.UserProfileRepository;

@RestController
@RequestMapping("bt/api/mobile/dashboard")
public class BtEngineerDashboardController {

	@Autowired
	UserProfileRepository dbProfile;
	@Autowired
	BtServiceAppointmentRepository dbServiceAppointmentRepo;
	@Autowired
	BtPreRegistrationCasesRepository dbPreRegistrationCasesRepo;
	@Autowired
	BtExpenseManagementRepository dbExpenseManagementRepo;
	@Autowired
	BtSaEngRatingRepository dbSaEngRatingRepo;
	@Autowired
	BtCaseProblemReportedRepository dbCaseProblemReportedRepo;
	@Autowired 
	LocationRepository dbLocationRepo;

	@GetMapping("sa")
	public Map<String, Object> getServiceAppointmentOpenVsTotal(@CurrentUser LoginUser user,
			@RequestParam("type") String type) {
		List<String> userList = dbProfile.getListOfUserAndChildByUser(user.getUsername());
		System.out.println(userList);
		Map<String, Object> map = null;
		if (type != null && type.equalsIgnoreCase("MTD")) {
			System.out.println("MTD");
			map = dbServiceAppointmentRepo.getServiceAppointment_open_vs_total_mtd(userList);
			if (map != null && !map.isEmpty()) {
				System.out.println(map + " Map");
				return map;
			} else {
				System.out.println("Else");
				map = new HashMap<String, Object>();
				map.put("total count", 0);
				map.put("open count", 0);

				return map;
			}

		} else if (type != null && type.equalsIgnoreCase("YTD")) {
			map = dbServiceAppointmentRepo.getServiceAppointment_open_vs_total_ytd(userList);
			if (map != null && !map.isEmpty()) {
				System.out.println(map + " Map");
				return map;
			} else {
				System.out.println("Else");
				map = new HashMap<String, Object>();
				map.put("total count", 0);
				map.put("open count", 0);

				return map;
			}
		} else {
			map = new HashMap<String, Object>();
			map.put("total count", 0);
			map.put("open count", 0);

			return map;
		}
	}
	
	
	@GetMapping("prc")
	public Map<String, Object> getPRCTotalVsOpen(@CurrentUser LoginUser user,@RequestParam("type") String type ){
	 List<String> userList= dbProfile.getListOfUserAndChildByUser(user.getUsername());
	 Map<String, Object> map=null;
	 if(type!=null && type.equalsIgnoreCase("MTD")) {
		 map= dbPreRegistrationCasesRepo.getPRC_open_vs_total_mtd(userList);
		 if(map!=null && !map.isEmpty()) {
			 return map;
		 }else {
			 map=new HashMap<String,Object>();
			 map.put("total count", 0);
			 map.put("open count", 0);
			 
			 return map;
		 }
		 
	 }else if(type!=null && type.equalsIgnoreCase("YTD")) {
		 map=dbPreRegistrationCasesRepo.getPRC_open_vs_total_ytd(userList);
		 if(map!=null && !map.isEmpty()) {
			 return map;
		 }else {
			 map=new HashMap<String,Object>();
			 map.put("total count", 0);
			 map.put("open count", 0);
			 
			 return map;
		 }
		 
	 }else {
		 map=new HashMap<String,Object>();
		 map.put("total count", 0);
		 map.put("open count", 0);
		
		return map; 
	 }
	 
	}
	
	@GetMapping("expense")
	public Map<String,Object> getExpenseOpenVsTotal(@RequestParam("type") String type,@CurrentUser LoginUser user){
		Map<String, Object> map =null;
		if(type!=null && type.equalsIgnoreCase("MTD")) {
			map = dbExpenseManagementRepo.getExpense_open_vs_total_mtd(user.getUsername());
			if(map!=null && !map.isEmpty()) {
				return map;
			}else {
				map=new HashMap<String,Object>();
				map.put("total count", 0);
				map.put("open count", 0);
				
				return map;
			}
		}else if(type!=null && type.equalsIgnoreCase("YTD")) {
			map=dbExpenseManagementRepo.getExpense_open_vs_total_ytd(user.getUsername());
			if(map!=null && !map.isEmpty()) {
				return map;
			}else {
				map=new HashMap<String,Object>();
				map.put("total count", 0);
				map.put("open count", 0);
				
				return map;
			}
		}else {
			map=new HashMap<String, Object>();
			map.put("total count", 0);
			map.put("open count", 0);
			
			return map;
		}
	}
	
	@GetMapping("rating")
	public Map<String, Object> getRating(@CurrentUser LoginUser user){
		
		Map<String,Object> map=null;
		
		map=dbSaEngRatingRepo.getRating(user.getUsername());
		if(map!=null && !map.isEmpty()) {
			return map;
		}else {
			map=new HashMap<String, Object>();
			map.put("Avg Rating", 0.00);
			map.put("Max Rating", 5);
			return map;
		}
		
	}
	
	@GetMapping("complaint")
	public List<Map<String,Object>> getComplaintList(@CurrentUser LoginUser user,@RequestParam("type") String type, @RequestParam("limit")int limit){
		List<Map<String,Object>> map=null;
		
		List<String>  userList=dbProfile.getListOfUserAndChildByUser(user.getUsername());
		if(type!=null && type.equalsIgnoreCase("MTD")) {
			map=dbCaseProblemReportedRepo.getProblem_mtd(userList,limit);
			
			if(map!=null && !map.isEmpty()) {
				return map;
			}else {
				map=new ArrayList<>();
				
				return map;
			}
		}else if(type!=null && type.equalsIgnoreCase("YTD")) {
//			map=dbCaseProblemReportedRepo.getProblem_ytd(userList,limit);
			
			if(map!=null && !map.isEmpty()) {
				return map;
			}else {
				map= new ArrayList<>();
				
				return map;
			}
		}else {
			map=new ArrayList<>();
			
			return map;
		}
		
	}
	
	@GetMapping("inventory")
	public Map<String, Object> getInventory(@CurrentUser LoginUser user){
		
		Map<String, Object> map=null;
		map=dbLocationRepo.getBtInventory(user.getUsername());
		if(map!=null && !map.isEmpty()) {
			return map;
		}else {
			map=new HashMap<String,Object>();
			
			map.put("Faulty", 0.0);
			map.put("Scrap", 0.0);
			map.put("Ok", 0.0);
			map.put("Rejected", 0.0);
			map.put("BER", 0.0);
			
			return map;
		}
		
		
		
 
	}
	
	@GetMapping("/app/version")
	public Map<String, Object> getAppLatestVersion(){
		return dbSaEngRatingRepo.getAppLatestVersion();
		
	}

}
