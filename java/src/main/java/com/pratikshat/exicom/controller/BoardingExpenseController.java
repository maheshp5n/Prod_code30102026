package com.pratikshat.exicom.controller;

import java.io.IOException;

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
import org.springframework.web.bind.annotation.RestController;

import com.pratikshat.exicom.annotation.CurrentUser;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.BoardingExpenseRequest;
import com.pratikshat.exicom.model.request.LodgingExpenseRequest;
import com.pratikshat.exicom.model.response.BoardingExpenseResponse;
import com.pratikshat.exicom.model.response.StatusMessageResponse;
import com.pratikshat.exicom.repository.BoardingExpenseRepository;

@RestController
@RequestMapping("api/boardingexpense")
public class BoardingExpenseController {

	@Autowired
	BoardingExpenseRepository dbBoardingExpense;
	@Autowired
	com.pratikshat.exicom.repository.UserProfileRepository dbProfile;
	
	
	@GetMapping("list/{expenseManagmentId}")
	public Page<BoardingExpenseResponse> getBoardingExpenseListByExpenseId(@PathVariable("expenseManagmentId")long expenseManagmentId,@CurrentUser LoginUser user,Pageable page,HttpServletResponse response)throws IOException{
		if(dbProfile.hasObjectAccess(user.getUsername(), "ExpenseManagement", "viewRights")||dbProfile.hasObjectAccess(user.getUsername(), "ExpenseManagement", "viewAllRights")) {
			try {
				return dbBoardingExpense.getByExpenseManagmentId(expenseManagmentId,page);
			}catch(Exception e) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
				return null;
			}
			
		}
		return Page.empty();
		
	}
	
	@GetMapping("{id}")
	public BoardingExpenseResponse getBoardingExpenseDetails(@PathVariable("id")long boardingExpenseId,@CurrentUser LoginUser user,Pageable page, HttpServletResponse response)throws IOException{
		if(dbProfile.hasObjectAccess(user.getUsername(), "ExpenseManagement", "viewRights")||dbProfile.hasObjectAccess(user.getUsername(), "ExpenseManagement", "viewAllRights")) {
			try {
				return dbBoardingExpense.getById(boardingExpenseId);
			}catch(Exception e) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
				return null;
			}
			
		}
		return new BoardingExpenseResponse();
		
	}
	
	@PostMapping
	public BoardingExpenseResponse createBoardingExpense(@Valid @RequestBody BoardingExpenseRequest boardingExpenseRequest,@CurrentUser LoginUser user,HttpServletResponse response)throws java.io.IOException {
		if(!dbProfile.hasObjectAccess(user.getUsername(), "ExpenseManagement", "createRights")) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN,"You are not authorised");
			return null;
		}
		try {
			
			if(boardingExpenseRequest.getExpenseManagementId()==0) {
				throw new BadRequestException("Expense id is null");
			}else if(boardingExpenseRequest.getFromDate()==null) {
				throw new BadRequestException("From date is null");
			}else if(boardingExpenseRequest.getFromTime()==null || boardingExpenseRequest.getFromTime().equalsIgnoreCase("")) {
				throw new BadRequestException("From time is null");
			}else if(boardingExpenseRequest.getToDate()==null) {
				throw new BadRequestException("To date is null");
			}else if(boardingExpenseRequest.getToTime()==null || boardingExpenseRequest.getToTime().equalsIgnoreCase("")) {
				throw new BadRequestException("To time is null");
			}else if(boardingExpenseRequest.getCityName()==null || boardingExpenseRequest.getCityName().equalsIgnoreCase("")) {
				throw new BadRequestException("City is null");
			}else {
				BoardingExpenseRequest _request = dbBoardingExpense.saveBoardingExpense(boardingExpenseRequest, user);
				
				return dbBoardingExpense.getById(_request.getId());	
			}
		} catch(BadRequestException ex) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
			return null;
		}catch(UnauthoriseException ex) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, ex.getMessage());
			return null;
		} catch (Exception ex) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
			return null;
		}
	}
	
	
	@PutMapping
	public BoardingExpenseResponse updateBoardingExpense(@Valid @RequestBody BoardingExpenseRequest boardingExpenseRequest,@CurrentUser LoginUser user,HttpServletResponse response) throws java.io.IOException{
		if(!dbProfile.hasObjectAccess(user.getUsername(), "ExpenseManagement", "editRights")) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
		try {
			
			if(boardingExpenseRequest.getExpenseManagementId()==0) {
				throw new BadRequestException("Expense id is null");
			}else if(boardingExpenseRequest.getId()==0) {
				throw new BadRequestException("Boarding expense id is null");
			}else if(boardingExpenseRequest.getFromDate()==null) {
				throw new BadRequestException("From date is null");
			}else if(boardingExpenseRequest.getFromTime()==null || boardingExpenseRequest.getFromTime().equalsIgnoreCase("")) {
				throw new BadRequestException("From time is null");
			}else if(boardingExpenseRequest.getToDate()==null) {
				throw new BadRequestException("To date is null");
			}else if(boardingExpenseRequest.getToTime()==null || boardingExpenseRequest.getToTime().equalsIgnoreCase("")) {
				throw new BadRequestException("To time is null");
			}else if(boardingExpenseRequest.getCityName()==null || boardingExpenseRequest.getCityName().equalsIgnoreCase("")) {
				throw new BadRequestException("City is null");
			}else {
				BoardingExpenseRequest _request = dbBoardingExpense.updateBoardingExpense(boardingExpenseRequest, user);
				
				return dbBoardingExpense.getById(_request.getId());
			}
		} catch(BadRequestException ex) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
			return null;
		}catch(UnauthoriseException ex) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, ex.getMessage());
			return null;
		} catch (Exception ex) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
			return null;
		}
		
	}
	
	
	
	
	@DeleteMapping("{id}")
	public ResponseEntity<?> deleteBoardingExpense(@PathVariable("id")long id,@CurrentUser LoginUser user, HttpServletResponse response)throws IOException{
		StatusMessageResponse simpleResponse = new StatusMessageResponse();
		if(dbProfile.hasObjectAccess(user.getUsername(), "ExpenseManagement", "createRights")||dbProfile.hasObjectAccess(user.getUsername(), "ExpenseManagement", "editRights")) {
			try {
				long boardingId = dbBoardingExpense.deleteBoardingExpense(id,user);
				
				simpleResponse.setMessage("Deleted successfully");
				simpleResponse.setStatus(HttpServletResponse.SC_OK);
				return ResponseEntity.status(HttpServletResponse.SC_OK).body(simpleResponse);
				
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
