package com.pratikshat.exicom.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

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
import com.pratikshat.exicom.model.response.StatusMessageResponse;
import com.pratikshat.exicom.repository.ModeOfTravelRepository;
import com.pratikshat.exicom.repository.OwnConveyanceRepository;
import com.pratikshat.exicom.repository.UserProfileRepository;
import com.pratikshat.exicom.services.impl.BtLocalConveyanceExpenseServiceImpl;

@RestController
@RequestMapping("bt/api/btlocalconveyance")
public class BtLocalConveyanceExpenseController {

	@Autowired
	BtLocalConveyanceExpenseServiceImpl serviceImpl; 
	
	@Autowired
	ModeOfTravelRepository dbModeOfTravel;
	
	@Autowired
	private com.pratikshat.exicom.repository.UserProfileRepository dbProfile;
	
	@Autowired
	OwnConveyanceRepository dbOwnConveyance;
	
	@GetMapping("modeoftravel")
	public List<Map<String, Object>> getModeOfTravel(){
		return dbModeOfTravel.getAll();
	}
	
	@GetMapping("ownconveyance/{modeoftravelid}")
	public List<Map<String, Object>> getOwnConveyance(@PathVariable("modeoftravelid")int modeoftravelid){
		return dbOwnConveyance.getByModeOfTravelId(modeoftravelid);
	}
	
	@PostMapping
	public Map<String, Object> createBtLocalConveyanceExpense(@RequestBody Map<String, Object> map,@CurrentUser LoginUser user,HttpServletResponse response) throws IOException, BadRequestException, UnauthoriseException
	{
		System.out.println("user =>"+user);
		if(!dbProfile.hasObjectAccess(user.getUsername(),"BtExpenseManagement", "createRights")) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
				System.out.println("map => "+map);
				return serviceImpl.saveObject(map, user);
	}
	
	@PutMapping
	public Map<String, Object> updateBtLocalConveyanceExpense(@RequestBody Map<String, Object> map,@CurrentUser LoginUser user,HttpServletResponse response) throws IOException, BadRequestException, UnauthoriseException
	{
		System.out.println("user =>"+user);
		if(!dbProfile.hasObjectAccess(user.getUsername(),"BtExpenseManagement", "editRights")) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
		return serviceImpl.updateObject(map, user);
	}
	
	@GetMapping("list/{btExpenseManagmentId}")
	public Page<Map<String,Object>> getBtLocalConveyanceExpenseByBtExpenseId(@PathVariable("btExpenseManagmentId")long btExpenseManagmentId,@CurrentUser LoginUser user,Pageable page, HttpServletResponse response) throws IOException
	{
		boolean viewAll = dbProfile.hasObjectAccess(user.getUsername(), BtExpenseManagement.class,UserProfileRepository.VIEW_ALL);
		boolean view = dbProfile.hasObjectAccess(user.getUsername(), BtExpenseManagement.class, UserProfileRepository.VIEW);
		System.out.println("hello");
		
		if (viewAll || view) {	try {
			return serviceImpl.getList(btExpenseManagmentId, page);
		}catch(Exception e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			return null;
		}
		}
		return null;
	}
	
	@GetMapping("getbyid/{id}")
	public Map<String, Object> getBtLocalConveyanceExpenseById(@PathVariable("id")Object id,@CurrentUser LoginUser user,Pageable page, HttpServletResponse response) throws IOException
	{
		boolean view = dbProfile.hasObjectAccess(user.getUsername(), BtExpenseManagement.class, UserProfileRepository.VIEW);
		System.out.println("id  =>  "+id);
		if (view ) {
			try {
				return serviceImpl.getSingle(id, user);
			}catch(Exception e) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
				return null;
			}
		}
		return null;
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<?> deleteLocalConveyanceExpense(@PathVariable("id")long id,@CurrentUser LoginUser user, HttpServletResponse response)throws IOException{
		StatusMessageResponse simpleResponse = new StatusMessageResponse();
		if(dbProfile.hasObjectAccess(user.getUsername(), "ExpenseManagement", "createRights")||dbProfile.hasObjectAccess(user.getUsername(), "ExpenseManagement", "editRights")) {
			try {
				StatusMessageResponse localConveyanceResponse = serviceImpl.deleteLocalConveyanceExpense(id,user);
				
				simpleResponse.setMessage("Deleted Successfully");
				simpleResponse.setStatus(HttpServletResponse.SC_OK);
				return ResponseEntity.status(HttpServletResponse.SC_OK).body(localConveyanceResponse);
				
			}catch(BadRequestException ex) {
				simpleResponse.setMessage(ex.getMessage());
				simpleResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body(simpleResponse);
			}catch(UnauthoriseException ex) {
				simpleResponse.setMessage(ex.getMessage());
				simpleResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
				return ResponseEntity.status(HttpServletResponse.SC_FORBIDDEN).body(simpleResponse);
			}catch(Exception e) {
				simpleResponse.setMessage(e.getMessage());
				simpleResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				return ResponseEntity.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).body(simpleResponse);
			}
		}else {
			simpleResponse.setMessage("You are not authorised");
			simpleResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
			return ResponseEntity.status(HttpServletResponse.SC_FORBIDDEN).body(simpleResponse);
		}
	}
	
}
