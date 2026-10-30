package com.pratikshat.exicom.controller;

import java.io.IOException;
import java.util.HashMap;
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
import com.pratikshat.exicom.entity.BtExpenseManagement;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.LodgingExpenseRequest;
import com.pratikshat.exicom.model.response.LodgingExpenseResponse;
import com.pratikshat.exicom.model.response.StatusMessageResponse;
import com.pratikshat.exicom.repository.BtLodgingExpenseRepository;
import com.pratikshat.exicom.repository.UserProfileRepository;
import com.pratikshat.exicom.services.BtLodgingExpenseSercice;
import com.pratikshat.exicom.services.impl.BtLodgingExpenseSerciceImpl;

@RestController
@RequestMapping("bt/api/lodgingexpense")
public class BtLodgingExpenseController {

	@Autowired
	BtLodgingExpenseRepository dbLodgingExpense;
	
	@Autowired
	BtLodgingExpenseSercice  sercice;
	
	@Autowired
	private com.pratikshat.exicom.repository.UserProfileRepository dbProfile;
	
	
	@GetMapping("list/{btExpenseManagmentId}")
	public Page<Map<String, Object>> getLodgingExpenseListByExpenseId(@PathVariable("btExpenseManagmentId")long btExpenseManagmentId,@CurrentUser LoginUser user,Pageable page, HttpServletResponse response)throws IOException{
		boolean viewAll = dbProfile.hasObjectAccess(user.getUsername(), BtExpenseManagement.class,UserProfileRepository.VIEW_ALL);
		boolean view = dbProfile.hasObjectAccess(user.getUsername(), BtExpenseManagement.class, UserProfileRepository.VIEW);
		System.out.println("hello");
		
		if (viewAll || view) {	try {
			System.out.println("btExpenseManagmentId  => "+btExpenseManagmentId);
				return sercice.getList(btExpenseManagmentId, page);
			}catch(Exception e) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
				return null;
			}
			
		}
		return Page.empty();
		
	}
	
	@GetMapping("getbyid/{id}")
	public Map<String, Object> getLodgingExpenseDetails(@PathVariable("id")long id,@CurrentUser LoginUser user,Pageable page, HttpServletResponse response)throws IOException{
		boolean view = dbProfile.hasObjectAccess(user.getUsername(), BtExpenseManagement.class, UserProfileRepository.VIEW);
		System.out.println("id  =>  "+id);
		if (view ) {
			try {
				return sercice.getSingle(id, user);
			}catch(Exception e) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
				return null;
			}
			
		}
		return new HashMap<String, Object>();
		
	}
	
	@PostMapping
	public Map<String, Object> createLodgingExpense(@RequestBody Map<String, Object> map,@CurrentUser LoginUser user,HttpServletResponse response)throws IOException, BadRequestException, UnauthoriseException {
		
		
		System.out.println("user =>"+user);
		if(!dbProfile.hasObjectAccess(user.getUsername(),"BtExpenseManagement", "createRights")) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
				System.out.println("map => "+map);
				return sercice.saveObject(map, user);	
			}
	
	
	@PutMapping
	public Map<String, Object> updateLodgingExpense(@RequestBody Map<String, Object> map,@CurrentUser LoginUser user,HttpServletResponse response)throws IOException, BadRequestException, UnauthoriseException {
		System.out.println("user =>"+user);
		if(!dbProfile.hasObjectAccess(user.getUsername(),"BtExpenseManagement", "editRights")) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}	
				return sercice.updateObject(map, user);	
		}
	
	@DeleteMapping("{id}")
	public ResponseEntity<?> deleteLocalConveyanceExpense(@PathVariable("id")long id,@CurrentUser LoginUser user, HttpServletResponse response)throws IOException, BadRequestException, UnauthoriseException{
		StatusMessageResponse simpleResponse = new StatusMessageResponse();
		if(dbProfile.hasObjectAccess(user.getUsername(), "BtExpenseManagement", "editRights")) {
			
				StatusMessageResponse localConveyanceResponse = sercice.deleteLogdingExpense(id,user);
				
				simpleResponse.setMessage("Deleted Successfully");
				simpleResponse.setStatus(HttpServletResponse.SC_OK);
				return ResponseEntity.status(HttpServletResponse.SC_OK).body(localConveyanceResponse);
				
		}
		return null;
	}
}
