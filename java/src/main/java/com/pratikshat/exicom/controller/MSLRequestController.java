package com.pratikshat.exicom.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pratikshat.exicom.annotation.CurrentUser;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.LProductRequest;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.MSLRequest;
import com.pratikshat.exicom.entity.MSLRequestRecentVisit;
import com.pratikshat.exicom.entity.MSLRequestStatus;
import com.pratikshat.exicom.entity.Products;
import com.pratikshat.exicom.entity.Warehouse;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.MSLRequestRequest;
import com.pratikshat.exicom.model.response.LocationProductRequestResponse;
import com.pratikshat.exicom.model.response.MSLRequestResponse;
import com.pratikshat.exicom.repository.MSLRequestRecentVisitRepository;
import com.pratikshat.exicom.repository.MSLRequestRepository;
import com.pratikshat.exicom.repository.MSLRequestStatusRepository;
import com.pratikshat.exicom.repository.UserProfileRepository;

@RestController
@RequestMapping("api/mslrequest")
public class MSLRequestController {

	@Autowired
	MSLRequestStatusRepository dbMSLRequestStatus;
	
	@Autowired
	MSLRequestRepository dbMSLRequest;
	
	@Autowired
	MSLRequestRecentVisitRepository dbMSLRequestRecentVisit;
	
	@Autowired
	com.pratikshat.exicom.repository.UserProfileRepository dbProfile;
	
	
	@GetMapping("status")
	public List<MSLRequestStatus> getStatus(){
		return dbMSLRequestStatus.findAll();
	}
	
	
	@PostMapping
	public MSLRequestResponse createMSLRequest(@CurrentUser LoginUser user,@Valid @RequestBody MSLRequestRequest mslRequest,HttpServletResponse httpResponse)throws java.io.IOException {
		if(!dbProfile.hasObjectAccess(user.getUsername(),"MSLRequest", "createRights")) {
			httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
		try {
			dbMSLRequest.createRequest(user, mslRequest);
			 return dbMSLRequest.getById(mslRequest.getMslRequestId());
		} catch(BadRequestException ex) {
			httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
			return null;
		}catch(UnauthoriseException ex) {
			httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, ex.getMessage());
			return null;
		} catch (Exception ex) {
			httpResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
			return null;
		}
	}
	@PostMapping("{mslrequestid}/additem")
	public com.pratikshat.exicom.model.request.MSLItemRequestRequest addItem(@CurrentUser LoginUser user,
			@RequestBody com.pratikshat.exicom.model.request.MSLItemRequestRequest itemRequest,
			@PathVariable("mslrequestid")long mslrequestid,
			HttpServletResponse response
			) throws java.io.IOException{
		if(!dbProfile.hasObjectAccess(user.getUsername(),"MSLRequest", "createRights")) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
		
		
		try {
			
			if(mslrequestid!=itemRequest.getMslRequestId()) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "MSLRequest id not found");
				return null;
			}
			
			return dbMSLRequest.addItems(user, itemRequest);
		}
		catch(com.pratikshat.exicom.exception.BadRequestException br) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, br.getMessage());
		}
		catch(com.pratikshat.exicom.exception.UnauthoriseException un) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, un.getMessage());
		}
		catch(Exception ex) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
		}
		return null;
	}
	@PutMapping("{mslrequestid}/submitforapproval")
	public ResponseEntity<?> submitForApproval(@PathVariable("mslrequestid")long mslrequestid,@RequestParam("remarks")String remarks, @CurrentUser LoginUser user,HttpServletResponse response) throws java.io.IOException{
		
		if(!dbProfile.hasObjectAccess(user.getUsername(),"MSLRequest", "editRights")) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
		try {
			dbMSLRequest.submitForApproval(user, mslrequestid,remarks);
			ResponseEntity.ok("{\"ok\":\"ok\"}");
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
		return null;
	}
	@PutMapping
	public MSLRequestResponse updateMSLRequest(@CurrentUser LoginUser user,@Valid @RequestBody MSLRequestRequest mslRequest,HttpServletResponse httpResponse) throws java.io.IOException {
		if(!dbProfile.hasObjectAccess(user.getUsername(),"MSLRequest", "editRights")) {
			httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
		try {
			dbMSLRequest.updateRequest(user, mslRequest);
			return dbMSLRequest.getById(mslRequest.getMslRequestId());	
		}catch(BadRequestException ex) {
			httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
			return null;
		}catch(UnauthoriseException ex) {
			httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, ex.getMessage());
			return null;
		} catch (Exception ex) {
			httpResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
			return null;
		}
	}
	
	@GetMapping("{mslId}")
	public MSLRequestResponse getByCaseId(@CurrentUser com.pratikshat.exicom.entity.LoginUser user,@PathVariable("mslId")long mslId){
		if(dbProfile.hasObjectAccess(user.getUsername(),"MSLRequest", "viewRights")||dbProfile.hasObjectAccess(user.getUsername(),"MSLRequest", "viewAllRights")) {
			MSLRequestResponse _msl=dbMSLRequest.getById(mslId);
			
			try {
				MSLRequestRecentVisit _recent=dbMSLRequestRecentVisit.findByMslIdAndVisitorId(mslId, user.getUsername());
				if(_recent==null)
					_recent=new MSLRequestRecentVisit(mslId, user.getUsername());
				else
					_recent.setVisitedDate(new java.util.Date());
				dbMSLRequestRecentVisit.save(_recent);
				dbMSLRequestRecentVisit.flush();
			}catch(Exception e) {}
			return _msl;
		}
		
		return new MSLRequestResponse();
	}
	
	
	@GetMapping("recentvisit")
	public Page<MSLRequestResponse> getReceintVisist(@CurrentUser LoginUser user,Pageable page){
		return dbMSLRequest.getRecentVisit(user.getUsername(), page);
	}
	
	@GetMapping
	public Page<MSLRequestResponse> getAll(@CurrentUser LoginUser user,Pageable page){
		return dbMSLRequest.getByOwner(user.getUsername(), page);
	}
	@GetMapping("releted/{mslrequestid}/lineitems")
	public Page<com.pratikshat.exicom.model.response.MSLItemRequestResponse> getItemRequest(@PathVariable("mslrequestid")long mslrequestid,Pageable page){
		return dbMSLRequest.getReleatedItemByRequestId(mslrequestid, page);
	}
	@GetMapping("bystatus")
	public Page<MSLRequestResponse> getAllByStatus(@RequestParam("status")String status,Pageable page){
		return dbMSLRequest.getByStatus(status, page);
	}
	
	@GetMapping("advancesearch")
	public List<MSLRequestResponse> getAdvanceSearchresult(@RequestParam Map<String,Object> request,@CurrentUser LoginUser user,HttpServletResponse  response) throws IOException, ParseException{
		
		if(dbProfile.hasObjectAccess(user.getUsername(), com.pratikshat.exicom.entity.MSLRequest.class, UserProfileRepository.VIEW_ALL)) {
			return 	dbMSLRequest.advanceSearch(request, user, null);
		}
		
		if(dbProfile.hasObjectAccess(user.getUsername(), com.pratikshat.exicom.entity.MSLRequest.class, UserProfileRepository.VIEW)) {
			return 	dbMSLRequest.advanceSearch(request, user, dbProfile.getLocationAndChildLocation(user.getUsername()));
		}
		
		response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
		return null;
		
	}
}
