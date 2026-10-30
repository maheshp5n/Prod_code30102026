package com.pratikshat.exicom.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pratikshat.exicom.annotation.CurrentUser;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.ServiceOrderJobCard;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.repository.ServiceOrderJobCardRepository;
import com.pratikshat.exicom.repository.UserProfileRepository;

@RestController
@RequestMapping("api/serviceorderjobcard")
public class ServiceOrderJobCardController {
	
	@Autowired
	ServiceOrderJobCardRepository dbJobCard;
	@Autowired
	UserProfileRepository dbUserprofile;
	
	@GetMapping
	public Page<Map<String,Object>> getJobCards(@CurrentUser LoginUser user,Pageable page,HttpServletResponse response,@RequestParam(value="filtername",required=false)String filtername) throws IOException{
		if(dbUserprofile.hasObjectAccess(user.getUsername(), ServiceOrderJobCard.class, UserProfileRepository.VIEW)) {
			List<String> ur = dbUserprofile.getListOfUserAndChildByUser(user.getUsername());
			if(filtername!=null&&filtername.equalsIgnoreCase("Pending for Repair")) {
				return dbJobCard.getJobCardByUserListByStatus(ur, false, page);
			}
			if(filtername!=null&&filtername.equalsIgnoreCase("Repaired")) {
				return dbJobCard.getJobCardByUserListByStatus(ur, true, page);
			}
				return dbJobCard.getJobCardByUserList(ur, page);
		}
			
		
			
		if(dbUserprofile.hasObjectAccess(user.getUsername(), ServiceOrderJobCard.class, UserProfileRepository.VIEW_ALL)) {
			if(filtername!=null&&filtername.equalsIgnoreCase("Pending for Repair")) {
				return dbJobCard.getJobCard(page,false);
			}
			if(filtername!=null&&filtername.equalsIgnoreCase("Repaired")) {
				return dbJobCard.getJobCard(page, true);
			}
			return dbJobCard.getJobCard(page);
		}
			
		return Page.empty();
	}
	@GetMapping("{id}")
	public Map<String,Object> getJobCard(@PathVariable("id")Long jobCardId,HttpServletResponse response,@CurrentUser LoginUser user) throws IOException{
		if(dbUserprofile.hasObjectAccess(user.getUsername(), ServiceOrderJobCard.class, UserProfileRepository.VIEW_ALL))
			return dbJobCard.getJobCardId(jobCardId);
		if(dbUserprofile.hasObjectAccess(user.getUsername(), ServiceOrderJobCard.class, UserProfileRepository.VIEW))
			return dbJobCard.getJobCardByUserListAndId(dbUserprofile.getListOfUserAndChildByUser(user.getUsername()), jobCardId);
		return new HashMap<String,Object>();
	}
	@PostMapping("{serviceresourceid}")
	public List<Map<String,Object>> createJobCard(@PathVariable("serviceresourceid")long serviceresourceid, @RequestBody List<Long> ids,@CurrentUser LoginUser user,HttpServletResponse response) throws IOException{
		if(!dbUserprofile.hasObjectAccess(user.getUsername(), ServiceOrderJobCard.class, UserProfileRepository.CREATE)) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
		try {
			
			dbJobCard.createJobCard(ids, serviceresourceid, user);
			return new ArrayList<Map<String,Object>>();
			
		} catch (BadRequestException e) {
			
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
			
		} catch (UnauthoriseException e) {
			
			response.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
		}
		
		return null;
	}
	
	@GetMapping("repairman")
	public List<Map<String,Object>> getRepariMan(@CurrentUser LoginUser user){
		List<String> userRole = new ArrayList<>();
		userRole.add("Repair Engineer (DCT)");
		return dbJobCard.getRepariEngineerUser(user.getUsername(), userRole);
		
	}
	
	@GetMapping("advancesearch")
	public List<Map<String,Object>> getAdvanceSearchResult(@RequestParam Map<String,Object> searchkey,@CurrentUser LoginUser user,HttpServletResponse response) throws IOException{
		if(dbUserprofile.hasObjectAccess(user.getUsername(), ServiceOrderJobCard.class, UserProfileRepository.VIEW_ALL)) {
			try {
				return dbJobCard.advanceSearch(searchkey, user, null, true);
			} catch (BadRequestException e) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
				e.printStackTrace();
				return null;
			} catch (UnauthoriseException e) {
				response.sendError(HttpServletResponse.SC_FORBIDDEN,e.getMessage());
				
				e.printStackTrace();
				return null;
			}
		}
		if(dbUserprofile.hasObjectAccess(user.getUsername(), ServiceOrderJobCard.class, UserProfileRepository.VIEW)) {
			try {
				return dbJobCard.advanceSearch(searchkey, user, dbUserprofile.getListOfUserAndChildByUser(user.getUsername()), false);
			} catch (BadRequestException e) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
				e.printStackTrace();
				return null;
			} catch (UnauthoriseException e) {
				response.sendError(HttpServletResponse.SC_FORBIDDEN,e.getMessage());
				e.printStackTrace();
				return null;
			}
		}
		else {
			try {
				return dbJobCard.advanceSearch(searchkey, user, null, false);
			} catch (BadRequestException e) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
				e.printStackTrace();
				return null;
			} catch (UnauthoriseException e) {
				response.sendError(HttpServletResponse.SC_FORBIDDEN,e.getMessage());
				e.printStackTrace();
				return null;
			}
		}
	}
}
