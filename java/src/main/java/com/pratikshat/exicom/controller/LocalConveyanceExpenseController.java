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
import com.pratikshat.exicom.entity.ModeOfTravel;
import com.pratikshat.exicom.entity.OwnConveyance;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.LocalConveyanceExpenseRequest;
import com.pratikshat.exicom.model.request.TourExpenseRequest;
import com.pratikshat.exicom.model.response.LocalConveyanceExpenseResponse;
import com.pratikshat.exicom.model.response.StatusMessageResponse;
import com.pratikshat.exicom.repository.LocalConveyanceExpenseRepository;
import com.pratikshat.exicom.repository.ModeOfTravelRepository;
import com.pratikshat.exicom.repository.OwnConveyanceRepository;

@RestController
@RequestMapping("api/localconveyance")
public class LocalConveyanceExpenseController {

	
	@Autowired
	LocalConveyanceExpenseRepository dbLocalConveyance;
	
	@Autowired
	ModeOfTravelRepository dbModeOfTravel;
	
	@Autowired
	OwnConveyanceRepository dbOwnConveyance;
	@Autowired
	private com.pratikshat.exicom.repository.UserProfileRepository dbProfile;
	
	@GetMapping("modeoftravel")
	public List<ModeOfTravel> getModeOfTravel(){
		return dbModeOfTravel.findAll();
	}
	
	@GetMapping("ownconveyance/{modeoftravelid}")
	public List<OwnConveyance> getOwnConveyance(@PathVariable("modeoftravelid")int modeoftravelid){
		return dbOwnConveyance.getByModeOfTravel_id(modeoftravelid);
	}
	
	
	@GetMapping("list/{expenseManagmentId}")
	public Page<LocalConveyanceExpenseResponse> getLocalConveyanceExpenseListByExpenseId(@PathVariable("expenseManagmentId")long expenseManagmentId,@CurrentUser LoginUser user,Pageable page, HttpServletResponse response) throws IOException{
		
		if(dbProfile.hasObjectAccess(user.getUsername(), "ExpenseManagement", "viewRights")||dbProfile.hasObjectAccess(user.getUsername(), "ExpenseManagement", "viewAllRights")) {
			try {
				return dbLocalConveyance.getByExpenseManagmentId(expenseManagmentId,page);
			}catch(Exception e) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
				return null;
			}
			
		}
		return Page.empty();
		
		
	}
	
	@GetMapping("{id}")
	public LocalConveyanceExpenseResponse getLocalConveyanceExpenseDetails(@PathVariable("id")long localConveyanceId,@CurrentUser LoginUser user,Pageable page, HttpServletResponse response) throws IOException{
		
		if(dbProfile.hasObjectAccess(user.getUsername(), "ExpenseManagement", "viewRights")||dbProfile.hasObjectAccess(user.getUsername(), "ExpenseManagement", "viewAllRights")) {
			try {
				return dbLocalConveyance.getById(localConveyanceId);
			}catch(Exception e) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
				return null;
			}
			
		}
		return new LocalConveyanceExpenseResponse();
		
	}
	
	@PostMapping
	public LocalConveyanceExpenseResponse createLocalConveyanceExpense(@Valid @RequestBody LocalConveyanceExpenseRequest localConveyanceExpenseRequest,@CurrentUser LoginUser user,HttpServletResponse response) throws IOException {
		
		if(!dbProfile.hasObjectAccess(user.getUsername(),"ExpenseManagement", "createRights")) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
		
		try {
			
			if(localConveyanceExpenseRequest.getExpenseManagementId()==0) {
				throw new BadRequestException("Expense id is null");
			}else if(localConveyanceExpenseRequest.getDate()==null) {
				throw new BadRequestException("Date is null");
			}else if(localConveyanceExpenseRequest.getFromPlace()==null || localConveyanceExpenseRequest.getFromPlace().equalsIgnoreCase("")) {
				throw new BadRequestException("From place is null");
			}else if(localConveyanceExpenseRequest.getToPlace()==null || localConveyanceExpenseRequest.getToPlace().equalsIgnoreCase("")) {
				throw new BadRequestException("To place is null");
			}else if(localConveyanceExpenseRequest.getModeOfTravelId()==0) {
				throw new BadRequestException("Mode of travel is null");
			}else {
				if(localConveyanceExpenseRequest.getModeOfTravelId()==8) {
					if(localConveyanceExpenseRequest.getOwnConveyanceId()==0) {
						throw new BadRequestException("Own conveyance is null");
					}
					if(localConveyanceExpenseRequest.getDistance()==0) {
						throw new BadRequestException("Distance is 0");
					}
				}else {
					if(localConveyanceExpenseRequest.getAmount()==0) {
						throw new BadRequestException("Amount is 0");
					}
				}
				
				LocalConveyanceExpenseRequest _request = dbLocalConveyance.saveLocalConveyanceExpense(localConveyanceExpenseRequest, user);
				
				return dbLocalConveyance.getById(_request.getId());
			}
		} catch(BadRequestException ex) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
			return null;
		}catch(UnauthoriseException ex) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, ex.getMessage());
			return null;
		} catch (Exception ex) {
			ex.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
			return null;
		}
	}
	
	
	@PutMapping
	public LocalConveyanceExpenseResponse updateLocalConveyanceExpense(@Valid @RequestBody LocalConveyanceExpenseRequest localConveyanceExpenseRequest,@CurrentUser LoginUser user,HttpServletResponse response) throws IOException {
		
		if(!dbProfile.hasObjectAccess(user.getUsername(),"ExpenseManagement", "editRights")) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
		
		try {
			
			if(localConveyanceExpenseRequest.getExpenseManagementId()==0) {
				throw new BadRequestException("Expense id is null");
			}else if(localConveyanceExpenseRequest.getId()==0) {
				throw new BadRequestException("Local conveyance id is null");
			} else if(localConveyanceExpenseRequest.getDate()==null) {
				throw new BadRequestException("Date is null");
			}else if(localConveyanceExpenseRequest.getFromPlace()==null || localConveyanceExpenseRequest.getFromPlace().equalsIgnoreCase("")) {
				throw new BadRequestException("From place is null");
			}else if(localConveyanceExpenseRequest.getToPlace()==null || localConveyanceExpenseRequest.getToPlace().equalsIgnoreCase("")) {
				throw new BadRequestException("To place is null");
			}else if(localConveyanceExpenseRequest.getModeOfTravelId()==0) {
				throw new BadRequestException("Mode of travel is null");
			}else {
				if(localConveyanceExpenseRequest.getModeOfTravelId()==8) {
					if(localConveyanceExpenseRequest.getOwnConveyanceId()==0) {
						throw new BadRequestException("Own conveyance is null");
					}
					if(localConveyanceExpenseRequest.getDistance()==0) {
						throw new BadRequestException("Distance is 0");
					}
				}else {
					if(localConveyanceExpenseRequest.getAmount()==0) {
						throw new BadRequestException("Amount is 0");
					}
				}
				
				LocalConveyanceExpenseRequest _request = dbLocalConveyance.updateLocalConveyanceExpense(localConveyanceExpenseRequest, user);
				
				return dbLocalConveyance.getById(_request.getId());
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
				long localConveyanceId = dbLocalConveyance.deleteLocalConveyanceExpense(id,user);
				
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
