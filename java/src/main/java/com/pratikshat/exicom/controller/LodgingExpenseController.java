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
import com.pratikshat.exicom.model.request.LodgingExpenseRequest;
import com.pratikshat.exicom.model.request.TourExpenseRequest;
import com.pratikshat.exicom.model.response.LocalConveyanceExpenseResponse;
import com.pratikshat.exicom.model.response.LodgingExpenseResponse;
import com.pratikshat.exicom.model.response.StatusMessageResponse;
import com.pratikshat.exicom.repository.LodgingExpenseRepository;

@RestController
@RequestMapping("api/lodgingexpense")
public class LodgingExpenseController {

	@Autowired
	LodgingExpenseRepository dbLodgingExpense;
	@Autowired
	private com.pratikshat.exicom.repository.UserProfileRepository dbProfile;
	
	@GetMapping("list/{expenseManagmentId}")
	public Page<LodgingExpenseResponse> getLodgingExpenseListByExpenseId(@PathVariable("expenseManagmentId")long expenseManagmentId,@CurrentUser LoginUser user,Pageable page, HttpServletResponse response)throws IOException{
		
		if(dbProfile.hasObjectAccess(user.getUsername(), "ExpenseManagement", "viewRights")||dbProfile.hasObjectAccess(user.getUsername(), "ExpenseManagement", "viewAllRights")) {
			try {
				return dbLodgingExpense.getByExpenseManagmentId(expenseManagmentId,page);
			}catch(Exception e) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
				return null;
			}
			
		}
		return Page.empty();
		
	}
	
	@GetMapping("{id}")
	public LodgingExpenseResponse getLodgingExpenseDetails(@PathVariable("id")long lodgingExpenseId,@CurrentUser LoginUser user,Pageable page, HttpServletResponse response)throws IOException{
		if(dbProfile.hasObjectAccess(user.getUsername(), "ExpenseManagement", "viewRights")||dbProfile.hasObjectAccess(user.getUsername(), "ExpenseManagement", "viewAllRights")) {
			try {
				return dbLodgingExpense.getById(lodgingExpenseId);
			}catch(Exception e) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
				return null;
			}
			
		}
		return new LodgingExpenseResponse();
		
	}
	
	@PostMapping
	public LodgingExpenseResponse createLodgingExpense(@Valid @RequestBody LodgingExpenseRequest lodgingExpenseRequest,@CurrentUser LoginUser user,HttpServletResponse response)throws IOException {
		
		if(!dbProfile.hasObjectAccess(user.getUsername(),"ExpenseManagement", "createRights")) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
		
		try {
			if(lodgingExpenseRequest.getExpenseManagementId()==0) {
				throw new BadRequestException("Expense id is null");
			}else if(lodgingExpenseRequest.getFromDate()==null) {
				throw new BadRequestException("From date is null");
			}else if(lodgingExpenseRequest.getFromTime()==null || lodgingExpenseRequest.getFromTime().equalsIgnoreCase("")) {
				throw new BadRequestException("From time is null");
			}else if(lodgingExpenseRequest.getToDate()==null) {
				throw new BadRequestException("To date is null");
			}else if(lodgingExpenseRequest.getToTime()==null || lodgingExpenseRequest.getToTime().equalsIgnoreCase("")) {
				throw new BadRequestException("To time is null");
			}else if(lodgingExpenseRequest.getCityName()==null || lodgingExpenseRequest.getCityName().equalsIgnoreCase("")) {
				throw new BadRequestException("City is null");
			}else {
				if(!lodgingExpenseRequest.isSelfArrangement() && !lodgingExpenseRequest.isComapnyPaid()) {
					if(lodgingExpenseRequest.getHotelName()== null || lodgingExpenseRequest.getHotelName().equalsIgnoreCase("")) {
						throw new BadRequestException("Hotel name is null");
					}else if(lodgingExpenseRequest.getBillNo()== null || lodgingExpenseRequest.getBillNo().equalsIgnoreCase("")) {
						throw new BadRequestException("Bill No. is null");
					}else if(lodgingExpenseRequest.getAmount()==0) {
						throw new BadRequestException("Amount is 0");
					}
				}
				
				LodgingExpenseRequest _request = dbLodgingExpense.saveLodgingExpense(lodgingExpenseRequest, user);
				
				return dbLodgingExpense.getById(_request.getId());	
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
	public LodgingExpenseResponse updateLodgingExpense(@Valid @RequestBody LodgingExpenseRequest lodgingExpenseRequest,@CurrentUser LoginUser user,HttpServletResponse response)throws IOException {
		
		if(!dbProfile.hasObjectAccess(user.getUsername(),"ExpenseManagement", "editRights")) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
		try {
			
			if(lodgingExpenseRequest.getExpenseManagementId()==0) {
				throw new BadRequestException("Expense id is null");
			}else if(lodgingExpenseRequest.getId()==0) {
				throw new BadRequestException("Lodging expense id is null");
			}else if(lodgingExpenseRequest.getFromDate()==null) {
				throw new BadRequestException("From date is null");
			}else if(lodgingExpenseRequest.getFromTime()==null || lodgingExpenseRequest.getFromTime().equalsIgnoreCase("")) {
				throw new BadRequestException("From time is null");
			}else if(lodgingExpenseRequest.getToDate()==null) {
				throw new BadRequestException("To date is null");
			}else if(lodgingExpenseRequest.getToTime()==null || lodgingExpenseRequest.getToTime().equalsIgnoreCase("")) {
				throw new BadRequestException("To time is null");
			}else if(lodgingExpenseRequest.getCityName()==null || lodgingExpenseRequest.getCityName().equalsIgnoreCase("")) {
				throw new BadRequestException("City is null");
			}else {
				if(!lodgingExpenseRequest.isSelfArrangement() && !lodgingExpenseRequest.isComapnyPaid()) {
					if(lodgingExpenseRequest.getHotelName()== null || lodgingExpenseRequest.getHotelName().equalsIgnoreCase("")) {
						throw new BadRequestException("Hotel name is null");
					}else if(lodgingExpenseRequest.getBillNo()== null || lodgingExpenseRequest.getBillNo().equalsIgnoreCase("")) {
						throw new BadRequestException("Bill No. is null");
					}else if(lodgingExpenseRequest.getAmount()==0) {
						throw new BadRequestException("Amount is 0");
					}
				}
				
				LodgingExpenseRequest _request = dbLodgingExpense.updateLodgingExpense(lodgingExpenseRequest, user);
				
				return dbLodgingExpense.getById(_request.getId());	
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
	public ResponseEntity<?> deleteLocalConveyanceExpense(@PathVariable("id")long id,@CurrentUser LoginUser user, HttpServletResponse response)throws IOException{
		StatusMessageResponse simpleResponse = new StatusMessageResponse();
		if(dbProfile.hasObjectAccess(user.getUsername(), "ExpenseManagement", "createRights")||dbProfile.hasObjectAccess(user.getUsername(), "ExpenseManagement", "editRights")) {
			try {
				long localConveyanceId = dbLodgingExpense.deleteLogdingExpense(id,user);
				
				simpleResponse.setMessage("Deleted Successfully");
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
