package com.pratikshat.exicom.controller;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;


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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pratikshat.exicom.annotation.CurrentUser;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;

import com.pratikshat.exicom.repository.UserProfileRepository;
import com.pratikshat.exicom.services.BtProductRequestItemService;
import com.pratikshat.exicom.services.BtProductRequestService;
import com.pratikshat.exicom.services.BtProductTransferService;


@RestController
@RequestMapping("bt/api/productrequest")
public class BtProductRequestController {
	@Autowired
	BtProductRequestService btprSer;
	
	@Autowired
	UserProfileRepository dbProfile;
		
	@Autowired
	BtProductRequestItemService dbProductRequestItem;

	@Autowired
	BtProductTransferService dbProductTransferService;
	
	
	@GetMapping
	public Page<Map<String,Object>> getProductRequest(@CurrentUser LoginUser user,Pageable page,@RequestParam(value="filtername",required=false) String filterparam) throws BadRequestException, UnauthoriseException{
		
		
		if(filterparam!=null)
		
			return btprSer.getByFilterParam(filterparam, page, user);
		else
			return btprSer.getPage(page, user);
	}
	@GetMapping("advancesearch")
	public List<Map<String,Object>> getAdvanceSearch(@CurrentUser LoginUser user,@RequestParam Map<String,Object> filterparam) throws BadRequestException, UnauthoriseException{
		return btprSer.advanceSearch(filterparam, user);
	}
	@GetMapping("{id}")
	public Map<String,Object> getProductRequest(@CurrentUser LoginUser user,@PathVariable("id")Long id) throws BadRequestException, UnauthoriseException{
		
		return btprSer.getSingle(id, user);
	}
	@GetMapping("{id}/items")
	public ResponseEntity<?> getProductRequestItems(@PathVariable("id")Long id,Pageable page,@CurrentUser LoginUser user,@RequestParam(value="view",required = false)String view) throws BadRequestException, UnauthoriseException{
		if(view!=null&&view.equalsIgnoreCase("list"))
			return ResponseEntity.ok( dbProductRequestItem.getList(id,user));
		else
			return ResponseEntity.ok( dbProductRequestItem.getPage(id,page,user));
	}
	@GetMapping("{id}/items/advancesearch")
	public List<Map<String,Object>> getProductRequestItems(@PathVariable("id")Long id,@RequestParam Map<String,Object> filterparam,@CurrentUser LoginUser user) throws BadRequestException, UnauthoriseException{
		return dbProductRequestItem.advanceSearch(id, filterparam, user);
	}
	
	@PostMapping
	public Map<String,Object> createProductRequest(@RequestBody Map<String,Object> request,@CurrentUser LoginUser user) throws BadRequestException, UnauthoriseException{
		return btprSer.saveObject(request, user);
	}
	@PutMapping("{id}")
	public Map<String,Object> updateProductRequest(@PathVariable("id")Long id,@RequestBody Map<String,Object> request,@CurrentUser LoginUser user) throws BadRequestException, UnauthoriseException{
		 btprSer.updateObject(request, user);
		 return btprSer.getSingle(id, user); 
	}
	@PostMapping("{id}/items")
	public Map<String,Object> addProductRequestItems(@PathVariable("id")Long productRequestId,@RequestBody Map<String,Object> itemRequest,@CurrentUser LoginUser user) throws BadRequestException, UnauthoriseException{
		return dbProductRequestItem.saveObject(itemRequest, user);
	}
	@PutMapping("{id}/items/{itemid}")
	public Map<String,Object> updateProductRequestItems(@PathVariable("id")Long productRequestId,@PathVariable("itemid")Long productRequestItemId,@RequestBody Map<String,Object> itemRequest,@CurrentUser LoginUser user) throws BadRequestException, UnauthoriseException{
		return dbProductRequestItem.saveObject(itemRequest, user);
	}
	
	@DeleteMapping("{id}/items/{itemid}")
	public String deleteProductRequestItem(@PathVariable("id")Long btProductRequestId,@PathVariable("itemid")Long btProductRequestItemId,@CurrentUser LoginUser user) throws BadRequestException,UnauthoriseException{
		dbProductRequestItem.deleteProductItem(btProductRequestItemId, user);
		return "{}";
	}
	@PutMapping("{id}/changestatus")
	public String changeStatus(@PathVariable("id")Long productRequestId,@CurrentUser LoginUser user,@RequestBody Map<String,String> statusMap) 
			throws BadRequestException,UnauthoriseException{
		if(statusMap.get("status")==null)
			throw new BadRequestException("Status not found");
		btprSer.changeStatus(productRequestId, user,statusMap.get("status").toString());
		return "{}";
	}
	@PutMapping("{id}/changestatus/{itemid}")
	public String changeItemStatus(@PathVariable("id")Long productRequestId,@CurrentUser LoginUser user,@PathVariable("itemid")Long productRequestItemId,@RequestBody Map<String,String> statusMap)
			throws BadRequestException,UnauthoriseException{
		if(statusMap.get("status")==null)
				throw new BadRequestException("Status not found");

		dbProductRequestItem.updateStatus(productRequestItemId, statusMap.get("status").toString(), user);
		return "{}";
	}	
	
	@GetMapping("status/forlist")
	public java.util.List<String> getProductRequestStatusList() {
		List<String> list = new ArrayList<>();
		list.add("Draft");
		list.add("Open");
		list.add("Short Closed");
		list.add("Partial Received");
		list.add("Receipt Pending");
		list.add("Received");
		list.add("Submitted");
		list.add("Submitted BY SCH");
		return list;
	}

	@GetMapping("{productrequestid}/transfer")
	public ResponseEntity<?> getProductTransferByProductRequestId(@PathVariable("productrequestid")Long productRequestId,@CurrentUser LoginUser user,@RequestParam(value="listview",required=false)String listview,Pageable page) throws BadRequestException,UnauthoriseException{
		if(listview!=null&&listview.equalsIgnoreCase("yes"))
			return ResponseEntity.ok(dbProductTransferService.getProductTransferByPrductRequestId(productRequestId, user));
		return ResponseEntity.ok(dbProductTransferService.getProductTransferByPrductRequestId(productRequestId, user,page));	
	}
	@GetMapping("{productrequestid}/transfer/{productrequestitemid}")
	public List<Map<String,Object>> getProductTransferByProductRequestIdAndProductRequestItemId(@PathVariable("productrequestid")Long productRequestId,@PathVariable("productrequestitemid")Long productRequestItemId,@CurrentUser LoginUser user) throws BadRequestException,UnauthoriseException{
		return dbProductTransferService.getProductTransferByProductRequestIdAndProductTransferItemId(productRequestId, productRequestItemId, user);
	}

	@GetMapping("{productrequestid}/transfer/advancesearch")
	public List<Map<String,Object>> getProductTransferAdvanceSearch(@PathVariable("productrequestid")Long productRequestId,@RequestBody Map<String,Object> request,@CurrentUser LoginUser user) throws BadRequestException,UnauthoriseException{
		return dbProductTransferService.getAdvanceSearchByProductRequestId(productRequestId, request, user);
	}
	@GetMapping("{productrequestid}/submitted/date")
	public List<Map<String,Object>> getProductRequestSubmittedDate(@PathVariable("productrequestid")Long productRequestId,@CurrentUser LoginUser user){
		return btprSer.getProductRequestSubmittedDate(productRequestId,user);
	}
}
