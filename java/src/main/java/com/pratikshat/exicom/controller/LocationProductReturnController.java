package com.pratikshat.exicom.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.LProductRequest;
import com.pratikshat.exicom.entity.LProductReturnOrder;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.response.LocationProductRequestResponse;
import com.pratikshat.exicom.model.response.LocationProductTransferLineItemResponse;
import com.pratikshat.exicom.model.response.LpReturnLineItemResponse;
import com.pratikshat.exicom.model.response.LpReturnOrderItemResponse;
import com.pratikshat.exicom.model.response.LpReturnOrderResponse;
import com.pratikshat.exicom.repository.UserProfileRepository;
@RestController
@RequestMapping("api/reurnorder")
public class LocationProductReturnController {
	@Autowired
	com.pratikshat.exicom.repository.LpReturnRepository dbReturn;
	@Autowired
	com.pratikshat.exicom.repository.UserProfileRepository dbProfile;
	
	Logger logger=LoggerFactory.getLogger(LocationProductReturnController.class);
	
	@GetMapping
	public Page<com.pratikshat.exicom.model.response.LpReturnOrderResponse> getReturnorders(@RequestParam(value="filtername",required=false)String filter,Pageable page,@CurrentUser LoginUser user){
		if(dbProfile.hasObjectAccess(user.getUsername(), "LProductReturnOrder", "viewAllRights")) {
			if(filter!=null) {
				if(filter.equalsIgnoreCase("open")) {
					return dbReturn.getPendingToReceive(page);
				}
				if(filter.equalsIgnoreCase("sendbyme")) {
					return dbReturn.getSendByMe(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()), page);
				}
				if(filter.equalsIgnoreCase("sendtome")) {
					return dbReturn.getSendForMe(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()),page);
				}
				if(filter.equalsIgnoreCase("penditoreceiveforme")) {
					return dbReturn.getMyPendingToReceive(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()),page);
				}
			}
			return dbReturn.getReturnOrders(page);
		}
		if(dbProfile.hasObjectAccess(user.getUsername(), "LProductReturnOrder", "viewRights")) {
			java.util.List<String> usr=new java.util.ArrayList<String>();
			usr=dbProfile.getListOfUserAndChildByUser(user.getUsername());
			List<Long> ids=dbProfile.getLocationAndChildLocation(user.getUsername());
			java.util.List<com.pratikshat.exicom.entity.EmployeeDetails> userList=new java.util.ArrayList<com.pratikshat.exicom.entity.EmployeeDetails>();
			
			for(String u:usr) {
				userList.add(new com.pratikshat.exicom.entity.EmployeeDetails(u));
			}
			if(filter!=null) {
				if(filter.equalsIgnoreCase("open")) {
					return dbReturn.getPendingToReceive(ids,page);
				}
				if(filter.equalsIgnoreCase("sendbyme")) {
					return dbReturn.getSendByMe(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()), page);
				}
				if(filter.equalsIgnoreCase("sendtome")) {
					return dbReturn.getSendForMe(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()),page);
				}
				if(filter.equalsIgnoreCase("penditoreceiveforme")) {
					return dbReturn.getMyPendingToReceive(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()),page);
				}
			}
			return dbReturn.getReturnOrders(ids,page);
		}
		
		return Page.empty();
	}
	
	@GetMapping("{returnorderid}")
	public com.pratikshat.exicom.model.response.LpReturnOrderResponse getReturnOrder(@PathVariable("returnorderid")long returnorderId,@CurrentUser LoginUser user){
		if(dbProfile.hasObjectAccess(user.getUsername(), "LProductReturnOrder", "viewAllRights")||dbProfile.hasObjectAccess(user.getUsername(), "LProductReturnOrder", "viewRights")) {
			return dbReturn.getReturnOrder(returnorderId);
		}
		return new com.pratikshat.exicom.model.response.LpReturnOrderResponse();
	}
	@GetMapping("{returnorderid}/items")
	public Page<com.pratikshat.exicom.model.response.LpReturnOrderItemResponse> getItems(@PathVariable("returnorderid")long returnorderId,Pageable page){
		Page<com.pratikshat.exicom.model.response.LpReturnOrderItemResponse> items=dbReturn.getItem(returnorderId, page);
		for(int i=0;i<items.getContent().size();i++) {
			items.getContent().get(i).setLineItems(dbReturn.getLineItem(items.getContent().get(i).getReturnOrderItemId()));
		}
		return items;
	}
	@GetMapping("{returnorderid}/items/pendingtoreceive")
	public java.util.List<com.pratikshat.exicom.model.response.LpReturnOrderItemResponse> getPenditItemsToReceive(@PathVariable("returnorderid")long returnOrderId){
		java.util.List<com.pratikshat.exicom.model.response.LpReturnOrderItemResponse> items=dbReturn.getPendingItemToReceive(returnOrderId);
		for(int i=0;i<items.size();i++) {
			items.get(i).setLineItems(dbReturn.getPendingLineItemToReceive(items.get(i).getReturnOrderItemId()));
		}
		return items;
	}
	@PostMapping("{returnorderid}/receive")
	public com.pratikshat.exicom.model.request.LpReturnOrderReceiveRequest receiveItems(@PathVariable("returnorderid")long returnOrderId,
			@RequestBody com.pratikshat.exicom.model.request.LpReturnOrderReceiveRequest items,
			@CurrentUser com.pratikshat.exicom.entity.LoginUser user,
			HttpServletResponse response
			) throws java.io.IOException{
		
		if(!dbProfile.hasObjectAccess(user.getUsername(),"LProductReturnOrder", "editRights") || !dbProfile.hasObjectAccess(user.getUsername(),"LProductReturnOrder", "createRights")) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
		
		try {
			return dbReturn.saveReturnOrderReceive(items, new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
		}catch(com.pratikshat.exicom.exception.BadRequestException bd) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, bd.getMessage());
		}
		catch(com.pratikshat.exicom.exception.UnauthoriseException ex) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, ex.getMessage());
		}catch (Exception ex) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
		}
		return null;
	}
//	shagufta find By Id
	@GetMapping("transferLineitem/{id}")
	public LpReturnLineItemResponse getLPProductTransferLineItemID(@PathVariable("id")long id,@CurrentUser LoginUser userDetails) {
			return dbReturn.getLineItemById(id);
	}
	@PutMapping("{returnorderid}/additems")
	public Map<String,Integer> addItem(@PathVariable("returnorderid")long returnorderid,@RequestBody List<Long>ids,@CurrentUser LoginUser user,HttpServletResponse response) throws IOException{
		int error=0;
		int sc=0;
		for(long id:ids) {
			try {
				dbReturn.addIitem(returnorderid, id, user);
				sc++;
			} catch (BadRequestException e) {
				error++;
				logger.error("Error On Return Order Item add {}",e);
				e.printStackTrace();
			} catch (UnauthoriseException e) {
				// TODO Auto-generated catch block
				error++;
				logger.error("Error On Return Order Item add {}",e);
				e.printStackTrace();
			}
		}
		Map<String,Integer> message=new HashMap<String,Integer>();
		message.put("s", sc);
		message.put("e", error);
		return message;
	}
	@DeleteMapping("{returnorderid}/items/{lineitemid}")
	public Map<String,Object> deleteReturnOrderLineItem(@PathVariable("returnorderid") long returnOrderId,@PathVariable("lineitemid")long lineItemId,@CurrentUser LoginUser user,HttpServletResponse response) throws IOException{
		try {
			dbReturn.removeItem(lineItemId, user);
			return new HashMap<String,Object>();
		} catch (BadRequestException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST,e.getMessage());
		} catch (UnauthoriseException e) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN,e.getMessage());
			
		}
		return null;
	}
	@PutMapping("{returnorderid}/submit")
	public String getSubmit(@PathVariable("returnorderid")long returnorderid,@CurrentUser LoginUser user,HttpServletResponse response) throws IOException{
		
		try {
			dbReturn.submit(returnorderid, user);
			return "{}";
		} catch (BadRequestException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
		} catch (UnauthoriseException e) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
		}
		return null;
	}
	
	@GetMapping("{returnorderid}/items/page")
	public Page<Map<String,Object>> getItemsBYPage(@PathVariable("returnorderid")long returnorderId,Pageable page){
		Page<Map<String,Object>> items=dbReturn.getItemNative(returnorderId, page);
		return items;
	}
	
	
	@GetMapping("advancesearch")
	public List<LpReturnOrderResponse> getAdvanceSearchresult(@RequestParam Map<String,Object> request,@CurrentUser LoginUser user,HttpServletResponse  response) throws IOException, ParseException{
		
		if(dbProfile.hasObjectAccess(user.getUsername(), LProductReturnOrder.class, UserProfileRepository.VIEW_ALL)) {
			return 	dbReturn.advanceSearch(request, user, null);
		}
		
		if(dbProfile.hasObjectAccess(user.getUsername(), LProductReturnOrder.class, UserProfileRepository.VIEW)) {
			return 	dbReturn.advanceSearch(request, user, dbProfile.getLocationAndChildLocation(user.getUsername()));
		}
		
		response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
		return null;
	}
	
	@GetMapping("{returnorderid}/items/page/advancesearch")
	public List<Map<String,Object>> getItemsBYPage(@PathVariable("returnorderid")long returnorderId,@RequestParam Map<String,Object> request, @CurrentUser LoginUser user,HttpServletResponse  response) throws IOException, ParseException{
		if(returnorderId!=0) {
			return dbReturn.getItemNativeAdvanceSearch(returnorderId, request);	
		}
		
		response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Return Order Id is 0");
		return null;
	}
	
	@GetMapping("status/forlist")
	public java.util.List<String> getProductRequestStatusList() {
		List<String> list = new ArrayList<>();
		list.add("Close");
		list.add("Draft");
		list.add("Partial Receive");
		list.add("Submitted");
		return list;
	}
	
	@GetMapping(value = "/pdf")
	public ResponseEntity<?> createPdf(@RequestParam("productReturnId")long productReturnId,HttpServletResponse httpServletResponse,@CurrentUser LoginUser user) throws IOException {
		try {
			
			com.pratikshat.exicom.model.response.LpReturnOrderResponse response = dbReturn.getReturnOrder(productReturnId);

			if(response==null) {
				httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST,"Product Return Order does not exist");
				return null;
			}
				
				ByteArrayInputStream bis = dbReturn.generatePackingListPdf(user, productReturnId, "ReturnOrder");
				
				HttpHeaders headers = new HttpHeaders();
				headers.add("Content-Disposition", "inline; filename="+response.getReturnOrderManualId()+".pdf");
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
