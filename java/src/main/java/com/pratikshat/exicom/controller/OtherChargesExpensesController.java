package com.pratikshat.exicom.controller;

import java.io.IOException;
import java.util.List;

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
import com.pratikshat.exicom.entity.OtherChargesTypes;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.OtherChargesExpenseRequest;
import com.pratikshat.exicom.model.response.LocalConveyanceExpenseResponse;
import com.pratikshat.exicom.model.response.OtherChargesExpenseResponse;
import com.pratikshat.exicom.model.response.StatusMessageResponse;
import com.pratikshat.exicom.repository.OtherChargesExpenseRepository;
import com.pratikshat.exicom.repository.OtherChargesTypesRepository;

@RestController
@RequestMapping("api/otherchargesexpenses")
public class OtherChargesExpensesController {

	@Autowired
	OtherChargesExpenseRepository dbOtherChargesExpense;
	
	@Autowired
	OtherChargesTypesRepository dbOtherChargesTypes;
	@Autowired
	private com.pratikshat.exicom.repository.UserProfileRepository dbProfile;
	
	
	@GetMapping("otherchargestype/{businessDomainid}")
	public List<OtherChargesTypes> getOtherChargesByBusinessDomain(@PathVariable("businessDomainid")int businessDomainid){
		return dbOtherChargesTypes.getByBusinessDoamin_BusinessDomainId(businessDomainid);
	}
	
	
	@GetMapping("list/{expenseManagmentId}")
	public Page<OtherChargesExpenseResponse> getOtherChargesByExpenseId(@PathVariable("expenseManagmentId")long expenseManagmentId,@CurrentUser LoginUser user,Pageable page, HttpServletResponse response) throws IOException{
		if(dbProfile.hasObjectAccess(user.getUsername(), "ExpenseManagement", "viewRights")||dbProfile.hasObjectAccess(user.getUsername(), "ExpenseManagement", "viewAllRights")) {
			try {
				return dbOtherChargesExpense.getByExpenseManagmentId(expenseManagmentId,page);
			}catch(Exception e) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
				return null;
			}
			
		}
		return Page.empty();
		
	}
	
	@GetMapping("{id}")
	public OtherChargesExpenseResponse getOtherChargesDetails(@PathVariable("id")long otherChargesId,@CurrentUser LoginUser user,Pageable page, HttpServletResponse response) throws IOException{
		if(dbProfile.hasObjectAccess(user.getUsername(), "ExpenseManagement", "viewRights")||dbProfile.hasObjectAccess(user.getUsername(), "ExpenseManagement", "viewAllRights")) {
			try {
				return dbOtherChargesExpense.getById(otherChargesId);
			}catch(Exception e) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
				return null;
			}
			
		}
		return new OtherChargesExpenseResponse();
		
	}
	
	@PostMapping
	public OtherChargesExpenseResponse createOtherChargesExpense(@Valid @RequestBody OtherChargesExpenseRequest otherChargesExpenseRequest,@CurrentUser LoginUser user,HttpServletResponse response)throws IOException, BadRequestException {
		
		if(!dbProfile.hasObjectAccess(user.getUsername(),"ExpenseManagement", "createRights")) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
		try {
			
			if(otherChargesExpenseRequest.getExpenseManagementId()==0) {
				throw new BadRequestException("Expense id is null");
			}else if(otherChargesExpenseRequest.getDate()==null) {
				throw new BadRequestException("Date is null");
			}else if(otherChargesExpenseRequest.getBillNo()==null || otherChargesExpenseRequest.getBillNo().equalsIgnoreCase("")) {
				throw new BadRequestException("Bill No. is null");
			}else if(otherChargesExpenseRequest.getOtherChargesTypeId()==0) {
				throw new BadRequestException("Type Id is null");
			}else if(otherChargesExpenseRequest.getAmount()==0) {
				throw new BadRequestException("Amount is 0");
			}else {
				OtherChargesExpenseRequest _request = dbOtherChargesExpense.saveOtherChargesExpense(otherChargesExpenseRequest, user);
				
				return dbOtherChargesExpense.getById(_request.getId());	
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
	public OtherChargesExpenseResponse updateOtherChargesExpense(@Valid @RequestBody OtherChargesExpenseRequest otherChargesExpenseRequest,@CurrentUser LoginUser user,HttpServletResponse response)throws IOException {
		
		if(!dbProfile.hasObjectAccess(user.getUsername(),"ExpenseManagement", "editRights")) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
		
		try {
			
			if(otherChargesExpenseRequest.getExpenseManagementId()==0) {
				throw new BadRequestException("Expense id is null");
			}else if(otherChargesExpenseRequest.getId()==0) {
				throw new BadRequestException("Other charges expense id is null");
			} else if(otherChargesExpenseRequest.getDate()==null) {
				throw new BadRequestException("Date is null");
			}else if(otherChargesExpenseRequest.getBillNo()==null || otherChargesExpenseRequest.getBillNo().equalsIgnoreCase("")) {
				throw new BadRequestException("Bill No. is null");
			}else if(otherChargesExpenseRequest.getOtherChargesTypeId()==0) {
				throw new BadRequestException("Type Id is null");
			}else if(otherChargesExpenseRequest.getAmount()==0) {
				throw new BadRequestException("Amount is 0");
			}else {
				OtherChargesExpenseRequest _request = dbOtherChargesExpense.updateOtherChargesExpense(otherChargesExpenseRequest, user);
				
				return dbOtherChargesExpense.getById(_request.getId());	
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
	public ResponseEntity<?> deleteOtherChargesExpense(@PathVariable("id")long id,@CurrentUser LoginUser user, HttpServletResponse response)throws IOException{
		StatusMessageResponse simpleResponse = new StatusMessageResponse();
		if(dbProfile.hasObjectAccess(user.getUsername(), "ExpenseManagement", "createRights")||dbProfile.hasObjectAccess(user.getUsername(), "ExpenseManagement", "editRights")) {
			try {
				long otherChargesId = dbOtherChargesExpense.deleteOtherChargesExpense(id,user);
				
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
