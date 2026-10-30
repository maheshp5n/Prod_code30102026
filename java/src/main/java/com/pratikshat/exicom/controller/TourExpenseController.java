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
import com.pratikshat.exicom.model.request.TourExpenseRequest;
import com.pratikshat.exicom.model.response.StatusMessageResponse;
import com.pratikshat.exicom.model.response.TourExpenseResponse;
import com.pratikshat.exicom.repository.ModeOfTravelRepository;
import com.pratikshat.exicom.repository.OwnConveyanceRepository;
import com.pratikshat.exicom.repository.TourExpenseRepository;

@RestController
@RequestMapping("api/tourexpense")
public class TourExpenseController {

	@Autowired
	TourExpenseRepository dbTourExpense;
	
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
	public Page<TourExpenseResponse> getTourExpenseListByExpenseId(@PathVariable("expenseManagmentId")long expenseManagmentId,@CurrentUser LoginUser user,Pageable page, HttpServletResponse response)throws IOException{
		if(dbProfile.hasObjectAccess(user.getUsername(), "ExpenseManagement", "viewRights")||dbProfile.hasObjectAccess(user.getUsername(), "ExpenseManagement", "viewAllRights")) {
			try {
				return dbTourExpense.getByExpenseManagmentId(expenseManagmentId,page);
			}catch(Exception e) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
				return null;
			}
		}
		return Page.empty();
	}
	
	@GetMapping("{id}")
	public TourExpenseResponse getTourExpenseDetails(@PathVariable("id")long tourExpenseId,@CurrentUser LoginUser user,Pageable page, HttpServletResponse response)throws IOException{
		if(dbProfile.hasObjectAccess(user.getUsername(), "ExpenseManagement", "viewRights")||dbProfile.hasObjectAccess(user.getUsername(), "ExpenseManagement", "viewAllRights")) {
			try {
				return dbTourExpense.getById(tourExpenseId);
			}catch(Exception e) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
				return null;
			}
		}
		return new TourExpenseResponse();
		
	}
	
	@PostMapping
	public TourExpenseResponse createTourExpense(@Valid @RequestBody TourExpenseRequest tourExpenseRequest,@CurrentUser LoginUser user,HttpServletResponse response)throws IOException, BadRequestException {
		
		if(!dbProfile.hasObjectAccess(user.getUsername(),"ExpenseManagement", "createRights")) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
		
		try {
			if(tourExpenseRequest.getExpenseManagementId()==0) {
				throw new BadRequestException("Expense id is null");
			}else if(tourExpenseRequest.getDepartureDate()==null) {
				throw new BadRequestException("Departure date is null");
			}else if(tourExpenseRequest.getDepartureTime()==null || tourExpenseRequest.getDepartureTime().equalsIgnoreCase("")) {
				throw new BadRequestException("Departure time is null");
			}else if(tourExpenseRequest.getArrivalDate()==null) {
				throw new BadRequestException("Arrival date is null");
			}else if(tourExpenseRequest.getArrivalTime()==null || tourExpenseRequest.getArrivalTime().equalsIgnoreCase("")) {
				throw new BadRequestException("Arrival time is null");
			}else if(tourExpenseRequest.getFromPlace()==null || tourExpenseRequest.getFromPlace().equalsIgnoreCase("")) {
				throw new BadRequestException("From place is null");
			}else if(tourExpenseRequest.getToPlace()==null || tourExpenseRequest.getToPlace().equalsIgnoreCase("")) {
				throw new BadRequestException("To place is null");
			}else if(tourExpenseRequest.getModeOfTravelId()==0) {
				throw new BadRequestException("Mode of travel is null");
			}else {
				if(tourExpenseRequest.getModeOfTravelId()==8) {
					if(tourExpenseRequest.getOwnConveyanceId()==0) {
						throw new BadRequestException("Own conveyance is null");
					}
					if(tourExpenseRequest.getDistance()==0) {
						throw new BadRequestException("Distance is 0");
					}
				}else {
					if(!tourExpenseRequest.isCompanyPaid()) {
						if(tourExpenseRequest.getFare()==0) {
							throw new BadRequestException("Fare is 0");
						}
					}
					
				}
				
				TourExpenseRequest _request = dbTourExpense.saveTourExpense(tourExpenseRequest, user);
				
				return dbTourExpense.getById(_request.getId());	
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
	public TourExpenseResponse updateTourExpense(@Valid @RequestBody TourExpenseRequest tourExpenseRequest,@CurrentUser LoginUser user,HttpServletResponse response)throws IOException, BadRequestException {
		
		if(!dbProfile.hasObjectAccess(user.getUsername(),"ExpenseManagement", "editRights")) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
		
		try {
			if(tourExpenseRequest.getExpenseManagementId()==0) {
				throw new BadRequestException("Expense id is null");
			}else if(tourExpenseRequest.getId()==0) {
				throw new BadRequestException("Tour expense id is null");
			}else if(tourExpenseRequest.getDepartureDate()==null) {
				throw new BadRequestException("Departure date is null");
			}else if(tourExpenseRequest.getDepartureTime()==null || tourExpenseRequest.getDepartureTime().equalsIgnoreCase("")) {
				throw new BadRequestException("Departure time is null");
			}else if(tourExpenseRequest.getArrivalDate()==null) {
				throw new BadRequestException("Arrival date is null");
			}else if(tourExpenseRequest.getArrivalTime()==null || tourExpenseRequest.getArrivalTime().equalsIgnoreCase("")) {
				throw new BadRequestException("Arrival time is null");
			}else if(tourExpenseRequest.getFromPlace()==null || tourExpenseRequest.getFromPlace().equalsIgnoreCase("")) {
				throw new BadRequestException("From place is null");
			}else if(tourExpenseRequest.getToPlace()==null || tourExpenseRequest.getToPlace().equalsIgnoreCase("")) {
				throw new BadRequestException("To place is null");
			}else if(tourExpenseRequest.getModeOfTravelId()==0) {
				throw new BadRequestException("Mode of travel is null");
			}else {
				if(tourExpenseRequest.getModeOfTravelId()==8) {
					if(tourExpenseRequest.getOwnConveyanceId()==0) {
						throw new BadRequestException("Own conveyance is null");
					}
					if(tourExpenseRequest.getDistance()==0) {
						throw new BadRequestException("Distance is 0");
					}
				}else {
					if(!tourExpenseRequest.isCompanyPaid()) {
						if(tourExpenseRequest.getFare()==0) {
							throw new BadRequestException("Fare is 0");
						}
					}
				}
				
				TourExpenseRequest _request = dbTourExpense.updateTourExpense(tourExpenseRequest, user);
				
				return dbTourExpense.getById(_request.getId());	
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
				long localConveyanceId = dbTourExpense.deleteTourExpense(id,user);
				
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
