package com.pratikshat.exicom.controller;


import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.collections4.map.HashedMap;
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
import com.pratikshat.exicom.entity.BtMSLRequestItem;
import com.pratikshat.exicom.entity.BtMSLRequestRecentVisit;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.MSLRequestRecentVisit;
import com.pratikshat.exicom.entity.MSLRequestStatus;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.MSLRequestRequest;
import com.pratikshat.exicom.model.response.MSLRequestResponse;
import com.pratikshat.exicom.repository.BtMSLRequestRecentVisitRepository;
import com.pratikshat.exicom.repository.BtMSLRequestRepository;
import com.pratikshat.exicom.repository.MSLRequestRecentVisitRepository;
import com.pratikshat.exicom.repository.MSLRequestRepository;
import com.pratikshat.exicom.repository.MSLRequestStatusRepository;
import com.pratikshat.exicom.repository.UserProfileRepository;
import com.pratikshat.exicom.services.BtMSLRequestService;

@RestController
@RequestMapping("bt/api/mslrequest")
public class BtMSLRequestController {

	@Autowired
	MSLRequestStatusRepository dbMSLRequestStatus;
	
	@Autowired
	BtMSLRequestRepository BtMSLDB;
	
	@Autowired
	BtMSLRequestRecentVisitRepository btMSLRequestRecentVisitDB;
	
	@Autowired
	BtMSLRequestService btMSLService;
	
	@Autowired
	MSLRequestRecentVisitRepository dbMSLRequestRecentVisit;
	
	@Autowired
	com.pratikshat.exicom.repository.UserProfileRepository dbProfile;
	
	
	@GetMapping("status")
	public List<MSLRequestStatus> getStatus(){
		return dbMSLRequestStatus.findAll();
	}
	
	
	@PostMapping
	public Map<String,Object> createBtMSLRequest(@CurrentUser LoginUser user,@Valid @RequestBody Map<String,Object> mslRequest,HttpServletResponse httpResponse)throws java.io.IOException {
		if(!dbProfile.hasObjectAccess(user.getUsername(),"BtMSLRequest", "createRights")) {
			httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
		try {
			Map<String, Object> map=btMSLService.saveObject(mslRequest, user);
			return map;
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
	public Page<Map<String, Object>> addItem(@CurrentUser LoginUser user,
			@RequestBody Map<String,Object> itemRequest,
			@PathVariable("mslrequestid")long mslrequestid,Pageable page,
			HttpServletResponse response
			) throws java.io.IOException{
//		if(!dbProfile.hasObjectAccess(user.getUsername(),"BtMSLRequest", "createRights")) {
//			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
//			return null;
//		}
//		
		
		try {
			
			if(mslrequestid!=Long.parseLong(itemRequest.get("btMslRequestId").toString())) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "MSLRequest id not found");
				return null;
			}
			
			return btMSLService.addItems(user, itemRequest,page);
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
	@PutMapping("{btMslrequestid}/submitforapproval")
	public ResponseEntity<?> submitForApproval(@PathVariable("btMslrequestid")long mslrequestid,@RequestParam("remarks")String remarks, @CurrentUser LoginUser user,HttpServletResponse response) throws java.io.IOException{
		
		if(!dbProfile.hasObjectAccess(user.getUsername(),"BtMSLRequest", "editRights")) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
		try {
			btMSLService.submitForApproval(user, mslrequestid,remarks);
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
	public Map<String,Object> updateMSLRequest(@CurrentUser LoginUser user,@Valid @RequestBody Map<String,Object> mslRequest,HttpServletResponse httpResponse) throws java.io.IOException {
		if(!dbProfile.hasObjectAccess(user.getUsername(),"BtMSLRequest", "editRights")) {
			httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
//		try {
//			//.updateRequest(user, mslRequest);
//			//return dbMSLRequest.getById(mslRequest.getMslRequestId());	
//		}catch(BadRequestException ex) {
//			httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
//			return null;
//		}catch(UnauthoriseException ex) {
//			httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, ex.getMessage());
//			return null;
//		} catch (Exception ex) {
//			httpResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
//			return null;
//		}
		return null;
	}
	
	@GetMapping("{mslId}")
	public Map<String,Object> getByCaseId(@CurrentUser com.pratikshat.exicom.entity.LoginUser user,@PathVariable("mslId")long mslId){
		if(dbProfile.hasObjectAccess(user.getUsername(),"BtMSLRequest", "viewRights")||dbProfile.hasObjectAccess(user.getUsername(),"BtMSLRequest", "viewAllRights")) {
			Map<String, Object> _msl=BtMSLDB.getById(mslId);
			
			try {
				BtMSLRequestRecentVisit _recent=btMSLRequestRecentVisitDB.findByIdAndVisitorId(mslId, user.getUsername());
				if(_recent==null)
					_recent=new BtMSLRequestRecentVisit(mslId, user.getUsername());
				else
					_recent.setVisitedDate(new java.util.Date());
				btMSLRequestRecentVisitDB.save(_recent);
				dbMSLRequestRecentVisit.flush();
			}catch(Exception e) {}
			return _msl;
		}
		
		return new HashedMap<String, Object>();
	}
	
	
	
	@GetMapping("recentvisit")
	public Page<Map<String, Object>> getReceintVisist(@CurrentUser LoginUser user,Pageable page){
		return BtMSLDB.getRecentVisit(user.getUsername(), page);
	}
	
	@GetMapping
	public Page<Map<String, Object>> getAll(@CurrentUser LoginUser user,Pageable page){
		return BtMSLDB.getByOwner(user.getUsername(), page);
	}
	@GetMapping("releted/{mslrequestid}/lineitems")
	public Page<Map<String, Object>> getItemRequest(@PathVariable("mslrequestid")long mslrequestid,Pageable page){
		return BtMSLDB.getReleatedItemByRequestId(mslrequestid, page);
	}
	@GetMapping("bystatus")
	public Page<Map<String, Object>> getAllByStatus(@RequestParam("status")String status,Pageable page){
		return BtMSLDB.getByStatus(status, page);
	}
	
	@GetMapping("advancesearch")
	public List<Map<String, Object>> getAdvanceSearchresult(@RequestParam Map<String,Object> request,@CurrentUser LoginUser user,HttpServletResponse  response) throws IOException, ParseException, BadRequestException, UnauthoriseException{
		
		if(dbProfile.hasObjectAccess(user.getUsername(), com.pratikshat.exicom.entity.BtMSLRequest.class, UserProfileRepository.VIEW_ALL)) {
			return 	btMSLService.advanceSearch(request, user, null);
		}
		
		if(dbProfile.hasObjectAccess(user.getUsername(), com.pratikshat.exicom.entity.BtMSLRequest.class, UserProfileRepository.VIEW)) {
			return 	btMSLService.advanceSearch(request, user, dbProfile.getLocationAndChildLocation(user.getUsername()));
		}
		
		response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
		return null;
		
	}
}
