package com.pratikshat.exicom.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pratikshat.exicom.annotation.CurrentUser;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.ServiceOrder;
import com.pratikshat.exicom.entity.ServiceOrderReturn;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.ServiceOrderReturnRequest;
import com.pratikshat.exicom.repository.ServiceOrderItemReturnRepository;
import com.pratikshat.exicom.repository.UserProfileRepository;


@RestController
@RequestMapping("api/serviceorderreturn")
public class ServiceOrderReturnController {
	@Autowired
	ServiceOrderItemReturnRepository dbServiceOrderItem;
	
	@Autowired
	UserProfileRepository dbProfile;
	
	@GetMapping
	public List<Map<String,Object>> getPendingToReturn(@CurrentUser LoginUser user){
		
		return dbServiceOrderItem.getPendingServiceOrderItemToReturn(user.getUsername());
	}
	
	@PostMapping
	public List<Map<String,Object>> postServiceResourceOrder(
			@RequestBody ServiceOrderReturnRequest ids,
			@CurrentUser LoginUser user,HttpServletResponse response) throws IOException{
		
		try {
			
			dbServiceOrderItem.createServiceOrderReturn(ids, user);
			
		}catch(BadRequestException ex) {
			
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
			return null;
			
		}catch(UnauthoriseException ex) {
			
			response.sendError(HttpServletResponse.SC_FORBIDDEN,ex.getMessage());
			return null;
		}
		
		return dbServiceOrderItem.getPendingServiceOrderItemToReturn(user.getUsername());
	}
	
	@GetMapping("pending")
	public List<Map<String,Object>> getPendingToReceive(@CurrentUser LoginUser user){
		
		return dbServiceOrderItem.getPendingToReceiveReturn(user.getUsername());
	}
	@PostMapping("pending")
	public List<Map<String,Object>> postReceive(@RequestBody Set<Long>ids,@CurrentUser LoginUser user,HttpServletResponse response) throws IOException{
		
		try {
			
			dbServiceOrderItem.receiveServiceOrderReturn(ids, user);
			
		} catch (BadRequestException e) {
			
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
			return null;
			
		} catch (UnauthoriseException e) {
			
			response.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
			return null;
		}		
		return dbServiceOrderItem.getPendingToReceiveReturn(user.getUsername());
	}
	
	
	@GetMapping("list")
	public Page<Map<String,Object>> getServiceOrders(@RequestParam(value="filters",required=false)String filter,@CurrentUser LoginUser user,Pageable page){
		List<Long> ids=dbProfile.getLocationAndChildLocation(user.getUsername());
		if(dbProfile.hasObjectAccess(user.getUsername(), ServiceOrderReturn.class, UserProfileRepository.VIEW_ALL)) {
			if(filter!=null) {
				if(filter.equalsIgnoreCase("Received")) {
					return dbServiceOrderItem.getReceivedSOR(page);
				}
				if(filter.equalsIgnoreCase("Partially Received")) {
					return dbServiceOrderItem.getPartiallyReceivedSOR(page);
				}
				
				if(filter.equalsIgnoreCase("Pending to Receive")) {
					return dbServiceOrderItem.getPendingToReceiveSOR(page);
				}
				
				if(filter.equalsIgnoreCase("All")) {
					return dbServiceOrderItem.getSOR(page);
				}
			}
			return dbServiceOrderItem.getSOR(page);
		}
		if(dbProfile.hasObjectAccess(user.getUsername(), ServiceOrderReturn.class, UserProfileRepository.VIEW)) {
			if(filter!=null) {
				if(filter.equalsIgnoreCase("Received")) {
					return dbServiceOrderItem.getReceivedSOR(ids, page);
				}
				if(filter.equalsIgnoreCase("Partially Received")) {
					return dbServiceOrderItem.getPartiallyReceivedSOR(ids, page);
				}
				
				if(filter.equalsIgnoreCase("Pending to Receive")) {
					return dbServiceOrderItem.getPendingToReceiveSOR(ids, page);
				}
				
				if(filter.equalsIgnoreCase("All")) {
					return dbServiceOrderItem.getSOR(ids, page);
				}
			}
			return dbServiceOrderItem.getSOR(ids, page);
		}
		return Page.empty();	
	}
	
	@GetMapping("{serivceOrderReturnId}")
	public Map<String,Object> getPendingToReturn(@PathVariable("serivceOrderReturnId")long serivceOrderReturnId){
		
		return dbServiceOrderItem.getSORById(serivceOrderReturnId);
	}
	
	
	@GetMapping("{serivceOrderReturnId}/item")
	public Page<Map<String,Object>> getServiceOrders(@PathVariable("serivceOrderReturnId")long serivceOrderReturnId,@CurrentUser LoginUser user,Pageable page){
		return dbServiceOrderItem.getSORItemSORById(serivceOrderReturnId, page);	
	}
	
	
	@GetMapping("advancesearch")
	public List<Map<String,Object>> getAdvanceSearchSOR(@RequestParam Map<String,Object> request,@CurrentUser LoginUser user,HttpServletResponse  response) throws IOException, ParseException{
		
		if(dbProfile.hasObjectAccess(user.getUsername(), ServiceOrderReturn.class, UserProfileRepository.VIEW_ALL)) {
			return 	dbServiceOrderItem.advanceSearchSOR(request, user, null);
		}
		
		if(dbProfile.hasObjectAccess(user.getUsername(), ServiceOrderReturn.class, UserProfileRepository.VIEW)) {
			return 	dbServiceOrderItem.advanceSearchSOR(request, user, dbProfile.getLocationAndChildLocation(user.getUsername()));
		}
		
		response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
		return null;
		
	}
	
	
	@GetMapping("{serivceOrderReturnId}/items/advancesearch")
	public List<Map<String,Object>> getAdvanceSearchSOR(@PathVariable("serivceOrderReturnId")long serivceOrderReturnId, @RequestParam Map<String,Object> request,@CurrentUser LoginUser user,HttpServletResponse  response) throws IOException, ParseException{
		return dbServiceOrderItem.advanceSearchSORItems(serivceOrderReturnId, request);
	}
	
	@GetMapping(value = "/pdf")
	public ResponseEntity<?> createPdf(@RequestParam("serivceOrderReturnId")long serivceOrderReturnId,HttpServletResponse httpServletResponse,@CurrentUser LoginUser user) throws IOException {
		try {
			
			Map<String,Object> response = dbServiceOrderItem.getSORById(serivceOrderReturnId);
			if(response==null) {
				httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST,"Service Order Return does not exist");
				return null;
			}
	
			ByteArrayInputStream bis = dbServiceOrderItem.generatePackingListPdf(user, serivceOrderReturnId, "ServiceOrderReturn");
				
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Disposition", "inline; filename="+response.get("manualId")+".pdf");
			return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(new InputStreamResource(bis));
		} catch(BadRequestException ex) {
			ex.printStackTrace();
			httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
			return null;
		}catch(UnauthoriseException ex) {
			ex.printStackTrace();
			httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN, ex.getMessage());
			return null;
		} catch (Exception ex) {
			ex.printStackTrace();
			httpServletResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
			return null;
		}
	}
}
