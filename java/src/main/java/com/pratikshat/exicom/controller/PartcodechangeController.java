package com.pratikshat.exicom.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.services.PartcodechangeService;

@RestController
@RequestMapping("/api/partcodechange")

public class PartcodechangeController {
	
	private final Logger logger = LoggerFactory.getLogger(PartcodechangeController.class);
	
	@Autowired
	PartcodechangeService dbChangeService;
	
	
	@GetMapping("pending")
	public List<Map<String,Object>> getPendingList(@CurrentUser LoginUser user){
		return dbChangeService.getPendingToApprovedList(user);
	}
	
	@GetMapping("approved")
	public List<Map<String,Object>> getApprovedList(@CurrentUser LoginUser user){
		return dbChangeService.getApprovedList(user);
	}
	
	@GetMapping("approved/page")
	public Page<Map<String,Object>> getApprovedList(@CurrentUser LoginUser user,Pageable page){
		return dbChangeService.getApprovedList(user,page);
	}
	
	@PostMapping("{id}")
	public String approveById(@PathVariable("id")Long id, @RequestBody Map<String,Object> param,@CurrentUser LoginUser user,HttpServletResponse response) throws IOException {
		try {
			dbChangeService.updatePartcode(id, param, user);
			return "{}";
		} catch (BadRequestException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST,e.getMessage());
			logger.error("Error in udpate",e);
			
		} catch (UnauthoriseException e) {
			logger.error("Unauthorise access",e);
			response.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
		}
		return null;
	}
	@PostMapping
	public String approve(@RequestBody List<Long>ids,@CurrentUser LoginUser user,HttpServletResponse response) throws IOException{
		
		try {
			dbChangeService.approveDirect(ids, user);
			return "{}";
		} catch (BadRequestException e) {
			logger.error("Error in update",e);
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
		} catch (UnauthoriseException e) {
			logger.error("Unauthorise access",e);
			response.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
			
		}
		
		return null;
	}
	@GetMapping("pending/search")
	public List<Map<String,Object>> searchPendingToApprove(@RequestParam Map<String,Object> searchkey,@CurrentUser LoginUser user){
		return dbChangeService.searchPendingToApproveList(searchkey, user);
	}
	@GetMapping("approved/search")
	public List<Map<String,Object>> searchApproved(@RequestParam Map<String,Object> searchkey,@CurrentUser LoginUser user){
		return dbChangeService.searchApprovedList(searchkey, user);
	}
	
	@GetMapping("noworkflorequest")
	public List<Map<String,Object>> getElegibal(@CurrentUser LoginUser user){
		
		return dbChangeService.elegibleForChange(user);
		
	}
	@PostMapping("noworkflorequest")
	public String createNewNonWorkflowRequest(@RequestBody Map<String,Object> data,@CurrentUser LoginUser user,HttpServletResponse response) throws IOException{
		
		try {
			dbChangeService.createNonworkflowPartcodeChange(data, user);
			return "{}";
		} catch (BadRequestException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
		} catch(UnauthoriseException e) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
		}
		return null;
	}
}
