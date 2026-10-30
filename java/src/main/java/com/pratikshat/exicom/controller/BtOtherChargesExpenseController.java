package com.pratikshat.exicom.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pratikshat.exicom.annotation.CurrentUser;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.OtherChargesTypes;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.OtherChargesExpenseRequest;
import com.pratikshat.exicom.model.response.OtherChargesExpenseResponse;
import com.pratikshat.exicom.model.response.StatusMessageResponse;
import com.pratikshat.exicom.repository.BtOtherChargesExpenseRepository;
import com.pratikshat.exicom.repository.OtherChargesTypesRepository;
import com.pratikshat.exicom.services.impl.BtOtherChargesExpenseServiceImpl;

@RestController
@RequestMapping("bt/api/otherchargesexpenses")
public class BtOtherChargesExpenseController {

	@Autowired
	private com.pratikshat.exicom.repository.UserProfileRepository dbProfile;
	
	@Autowired
	BtOtherChargesExpenseRepository btOtherChargesExpenseDB;
	
	@Autowired
	BtOtherChargesExpenseServiceImpl serviceImpl;
	
	@Autowired
	OtherChargesTypesRepository dbOtherChargesTypes;
	
	
	@GetMapping("otherchargestype/{businessDomainid}")
	public List<Map<String,Object>> getOtherChargesByBusinessDomain(@PathVariable("businessDomainid")int businessDomainid){
		return dbOtherChargesTypes.getByBusinessDoaminBusinessDomainId(businessDomainid);
	}
	
	
	@GetMapping("list/{btexpenseManagmentId}")
	public Page<Map<String,Object>> getOtherChargesByExpenseId(@PathVariable("btexpenseManagmentId")long btexpenseManagmentId,@CurrentUser LoginUser user,Pageable page, HttpServletResponse response) throws IOException{
		if(dbProfile.hasObjectAccess(user.getUsername(), "BtExpenseManagement", "viewRights")||dbProfile.hasObjectAccess(user.getUsername(), "BtExpenseManagement", "viewAllRights")) {
			try {
				return serviceImpl.getAll(btexpenseManagmentId, page);
			}catch(Exception e) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
				return null;
			}
			
		}
		return Page.empty();
		
	}
	
	@GetMapping("getbyid/{id}")
	public Map<String, Object> getOtherChargesDetails(@PathVariable("id")Object id,@CurrentUser LoginUser user,Pageable page, HttpServletResponse response) throws IOException{
		if(dbProfile.hasObjectAccess(user.getUsername(), "BtExpenseManagement", "viewRights")||dbProfile.hasObjectAccess(user.getUsername(), "BtExpenseManagement", "viewAllRights")) {
			try {
				return serviceImpl.getSingle(id, user);
			}catch(Exception e) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
				return null;
			}
			
		}
		return null;
		
		
	}
	
	@PostMapping
	public Map<String, Object> createOtherChargesExpense( @RequestBody Map<String, Object> request,@CurrentUser LoginUser user,HttpServletResponse response)throws IOException, BadRequestException, UnauthoriseException {
		
		if(!dbProfile.hasObjectAccess(user.getUsername(),"BtExpenseManagement", "createRights")) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
		
				return serviceImpl.saveObject(request, user);	
		
	}
	
	
	@PutMapping
	public Map<String, Object> updateOtherChargesExpense( @RequestBody Map<String, Object> request,@CurrentUser LoginUser user,HttpServletResponse response)throws IOException, BadRequestException, UnauthoriseException {
		
		if(!dbProfile.hasObjectAccess(user.getUsername(),"BtExpenseManagement", "editRights")) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
		
		
				return serviceImpl.updateObject(request, user);	
		
	}
	
	
	@DeleteMapping("{id}")
	public ResponseEntity<?> deleteOtherChargesExpense(@PathVariable("id")long id,@CurrentUser LoginUser user, HttpServletResponse response)throws IOException, BadRequestException, UnauthoriseException{
		StatusMessageResponse simpleResponse = new StatusMessageResponse();
		if(dbProfile.hasObjectAccess(user.getUsername(), "BtExpenseManagement", "editRights")) {
			
				StatusMessageResponse otherChargesResponse = serviceImpl.deleteOtherChargesExpense(id,user);
				
				simpleResponse.setMessage("Deleted Successfully");
				simpleResponse.setStatus(HttpServletResponse.SC_OK);
				
				return ResponseEntity.status(HttpServletResponse.SC_OK).body(otherChargesResponse);
				
			
		}else {
//			simpleResponse.setMessage("You are not authorised");
//			simpleResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
			return ResponseEntity.status(HttpServletResponse.SC_FORBIDDEN).body("You are not authorised");
		}
	}
}
