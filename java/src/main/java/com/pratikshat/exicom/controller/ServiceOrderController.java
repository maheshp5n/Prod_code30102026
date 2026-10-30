package com.pratikshat.exicom.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.pratikshat.exicom.entity.LProductRequest;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.ServiceOrder;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.response.LocationProductRequestResponse;
import com.pratikshat.exicom.repository.ServiceOrderRepository;
import com.pratikshat.exicom.repository.UserProfileRepository;

@RestController
@RequestMapping("api/serviceorder")
public class ServiceOrderController {
	
	@Autowired
	ServiceOrderRepository dbServiceOrder;
	@Autowired
	UserProfileRepository dbProfile;
	
	@GetMapping
	public Page<Map<String,Object>> getServiceOrders(@RequestParam(value="filters",required=false)Integer filterid,@CurrentUser LoginUser user,Pageable page){
		List<Integer> _fileter=new ArrayList<Integer>();
		List<Long> ids=dbProfile.getLocationAndChildLocation(user.getUsername());
		if(filterid==null||filterid==0) {
			_fileter.add(1);
			_fileter.add(2);
			_fileter.add(3);
			_fileter.add(4);
			_fileter.add(5);
		}
		else {
			_fileter.add(filterid);
		}
		
		if(dbProfile.hasObjectAccess(user.getUsername(), ServiceOrder.class, UserProfileRepository.VIEW_ALL)) {
			return dbServiceOrder.getServiceOrders(_fileter, page);
		}
		if(dbProfile.hasObjectAccess(user.getUsername(), ServiceOrder.class, UserProfileRepository.VIEW)) {
			return dbServiceOrder.getServiceOrders(ids, _fileter, page);
		}
		
		return Page.empty();
	}
	@GetMapping("{id}")
	public Map<String,Object> getServiceOrder(@PathVariable("id")Long id,@CurrentUser LoginUser user){
		List<Long> ids=dbProfile.getLocationAndChildLocation(user.getUsername());
		if(dbProfile.hasObjectAccess(user.getUsername(), ServiceOrder.class, UserProfileRepository.VIEW_ALL))
			return dbServiceOrder.getServiceOrder(id);
		if(dbProfile.hasObjectAccess(user.getUsername(), ServiceOrder.class, UserProfileRepository.VIEW))
			return dbServiceOrder.getServiceOrder(id, ids);
		
		return new HashMap<String,Object>();
	}
	@PostMapping
	public Map<String,Object> saveWorkOrder(@RequestBody Map<String,Object> request,@CurrentUser LoginUser user,HttpServletResponse response) throws IOException{
		if(!dbProfile.hasObjectAccess(user.getUsername(), ServiceOrder.class,UserProfileRepository.CREATE)) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
		try {
			dbServiceOrder.createServiceOrder(request,user);
			return request;
		}catch(BadRequestException ex) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
		}
		catch(UnauthoriseException ex) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, ex.getMessage());
		}
		return null;
	}
	@GetMapping("{id}/items")
	public List<Map<String,Object>> getServiceOrderItems(@PathVariable("id")Long id,@CurrentUser LoginUser user){
		
		return dbServiceOrder.getServiceOrderItems(id);
	}
	
	@GetMapping("faultydevice")
	public List<Map<String,Object>> getFaultyDevice(@CurrentUser LoginUser user){
		return dbServiceOrder.getNotOkeItems(user.getUsername());
	}
	@GetMapping("reparicenter")
	public List<Map<String,Object>> getRepariCenter(){
		return dbServiceOrder.getRepariCenter();
	}
	@GetMapping("pendingtoreceive")
	public List<Map<String,Object>> getPendingToReceive(@CurrentUser LoginUser user){
		return dbServiceOrder.getPendingToReceiveByUserId(user.getUsername());
	}
	@GetMapping("{id}/pendingtoreceive")
	public List<Map<String,Object>> getPendingToReceive(@PathVariable("id")Long id, @CurrentUser LoginUser user){
		return dbServiceOrder.getPendingToReceiveByUserIdAndOrderId(user.getUsername(), id);
	}
	@PostMapping("receive")
	public Map<String,Object> receiveItems(@RequestBody List<Long>ids,@CurrentUser LoginUser user,HttpServletResponse response) throws IOException{
		
		try {
			dbServiceOrder.receiveServiceOrder(ids, user);
			return new HashMap<String,Object>();
		} catch (BadRequestException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
			
		} catch (UnauthoriseException e) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
		}
		return null;
	}
	
	@GetMapping("reparieng")
	public List<Map<String,Object>> getRepariEngUnder(@CurrentUser LoginUser user){
		return dbServiceOrder.getRepairEngUnder(user.getUsername());
	}
	
	
	@GetMapping("advancesearch")
	public List<Map<String,Object>> getAdvanceSearchresult(@RequestParam Map<String,Object> request,@CurrentUser LoginUser user,HttpServletResponse  response) throws IOException, ParseException{
		
		if(dbProfile.hasObjectAccess(user.getUsername(), ServiceOrder.class, UserProfileRepository.VIEW_ALL)) {
			return 	dbServiceOrder.advanceSearch(request, user, null);
		}
		
		if(dbProfile.hasObjectAccess(user.getUsername(), ServiceOrder.class, UserProfileRepository.VIEW)) {
			return 	dbServiceOrder.advanceSearch(request, user, dbProfile.getLocationAndChildLocation(user.getUsername()));
		}
		
		response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
		return null;
		
	}
	
	
	@GetMapping("faultydevice/advancesearch")
	public List<Map<String,Object>> getFaultyDeviceAdvanceSearch(@RequestParam Map<String,Object> request,@CurrentUser LoginUser user) throws ParseException{
		
		return dbServiceOrder.getNotOkeItemsAdvanceSearch(request, user);
	}
	
	@GetMapping("{id}/items/advancesearch")
	public List<Map<String,Object>> getServiceOrderItemsAdvanceSearch(@PathVariable("id")long id, @RequestParam Map<String,Object> request, @CurrentUser LoginUser user, HttpServletResponse  response) throws IOException, ParseException{
		if(id!=0) {
			return dbServiceOrder.getServiceOrderItemsAdvanceSearch(id, request);
		}else {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "You are not authorised");
			return null;
		}
		
	}
	
	
	@GetMapping(value = "/pdf")
	public ResponseEntity<?> createPdf(@RequestParam("serviceOrderId")long serviceOrderId,HttpServletResponse httpServletResponse,@CurrentUser LoginUser user) throws IOException {
		try {
			
			Map<String,Object> response = dbServiceOrder.getServiceOrder(serviceOrderId);
			if(response==null) {
				httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST,"Service Order does not exist");
				return null;
			}
	
			ByteArrayInputStream bis = dbServiceOrder.generatePackingListPdf(user, serviceOrderId, "ServiceOrder");
				
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
