package com.pratikshat.exicom.controller;

import java.io.IOException;
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
import com.pratikshat.exicom.model.request.BoardingExpenseRequest;
import com.pratikshat.exicom.model.response.BoardingExpenseResponse;
import com.pratikshat.exicom.model.response.StatusMessageResponse;
import com.pratikshat.exicom.repository.UserProfileRepository;
import com.pratikshat.exicom.services.BtBoardingExpenseService;
import com.pratikshat.exicom.services.impl.BtBoardingExpenseServiceImpl;

@RestController
@RequestMapping("bt/api/boardingexpense")
public class BtBoardingExpenseController {

	@Autowired
	com.pratikshat.exicom.repository.UserProfileRepository dbProfile;
	
	@Autowired
	BtBoardingExpenseServiceImpl serviceImpl; 
	
	
	@GetMapping("list/{btExpenseManagmentId}")
	public Page<Map<String,Object>> getBtBoardingExpenseListByBtExpenseId(@PathVariable("btExpenseManagmentId") long btExpenseManagmentId,@CurrentUser LoginUser user,Pageable page,HttpServletResponse response)throws IOException, UnauthoriseException, BadRequestException{
		boolean view = dbProfile.hasObjectAccess(user.getUsername(), BtExpenseManagement.class, UserProfileRepository.VIEW);
		boolean viewAll = dbProfile.hasObjectAccess(user.getUsername(), BtExpenseManagement.class,UserProfileRepository.VIEW_ALL);
		
		if (view || viewAll) {
				return serviceImpl.getList(btExpenseManagmentId, page);
		} else {
			throw new UnauthoriseException("You are not authorised");
		}
	}
	
	@GetMapping("getbyid/{id}")
	public Map<String, Object> getBtBoardingExpenseDetails(@PathVariable("id")Object id,@CurrentUser LoginUser user,Pageable page, HttpServletResponse response)throws IOException, BadRequestException, UnauthoriseException{
		
		boolean view = dbProfile.hasObjectAccess(user.getUsername(), BtExpenseManagement.class, UserProfileRepository.VIEW);
		boolean viewAll = dbProfile.hasObjectAccess(user.getUsername(), BtExpenseManagement.class,UserProfileRepository.VIEW_ALL);
		
		if (view || viewAll) {
				return serviceImpl.getSingle(id, user);
		} else {
			throw new UnauthoriseException("You are not authorised");
		}
			
		
	}
	
	@PostMapping
	public Map<String, Object> createBtBoardingExpense(@Valid @RequestBody Map<String, Object> map,@CurrentUser LoginUser user,HttpServletResponse response)throws java.io.IOException, BadRequestException, UnauthoriseException {
		
		if(!dbProfile.hasObjectAccess(user.getUsername(),"BtExpenseManagement", "createRights")) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
				return serviceImpl.saveObject(map, user);	
			
	}
	
	
	@PutMapping
	public Map<String, Object> updateBtBoardingExpense(@Valid @RequestBody Map<String, Object> map,@CurrentUser LoginUser user,HttpServletResponse response) throws java.io.IOException, BadRequestException, UnauthoriseException{
		
		if(!dbProfile.hasObjectAccess(user.getUsername(),"BtExpenseManagement", "editRights")) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
				return serviceImpl.updateObject(map, user);
		
		
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<?> deleteBtBoardingExpense(@PathVariable("id")long id,@CurrentUser LoginUser user, HttpServletResponse response)throws IOException{
		StatusMessageResponse simpleResponse = new StatusMessageResponse();
		if(dbProfile.hasObjectAccess(user.getUsername(), "BtExpenseManagement", "editRights")) {
			try {
				StatusMessageResponse boardingresponse = serviceImpl.deleteBtBoardingExpense(id, user);
				
				simpleResponse.setMessage("Deleted successfully");
				simpleResponse.setStatus(HttpServletResponse.SC_OK);
				return ResponseEntity.status(HttpServletResponse.SC_OK).body(boardingresponse);
				
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
