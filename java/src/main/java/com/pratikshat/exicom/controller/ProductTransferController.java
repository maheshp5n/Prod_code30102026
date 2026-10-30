package com.pratikshat.exicom.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.ParseException;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pratikshat.exicom.annotation.CurrentUser;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.LPTrasfer;
import com.pratikshat.exicom.entity.LProductRequest;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.response.ExpenseManagementResponse;
import com.pratikshat.exicom.model.response.LocationProductRequestItemResponse;
import com.pratikshat.exicom.model.response.LocationProductRequestResponse;
import com.pratikshat.exicom.model.response.LocationProductTransferItemResponse;
import com.pratikshat.exicom.model.response.LocationProductTransferLineItemResponse;
import com.pratikshat.exicom.repository.UserProfileRepository;

@RestController
@RequestMapping("api/producttransfer")
public class ProductTransferController {
	@Autowired
	com.pratikshat.exicom.repository.LpTransferRepository dbTransferRepo;
	@Autowired
	com.pratikshat.exicom.repository.UserProfileRepository dbProfile;
	
	@GetMapping("items")
	public Page<com.pratikshat.exicom.model.response.LocationProductTransferItemResponse> getTransferitems(@RequestParam(value="filtername",required=false)String filter,@CurrentUser com.pratikshat.exicom.entity.LoginUser user,Pageable page){
		List<Long> ids=dbProfile.getLocationAndChildLocation(user.getUsername());
		if(dbProfile.hasObjectAccess(user.getUsername(), "LPTrasfer", "viewAllRights")) {
			if(filter!=null) {
				if(filter.equalsIgnoreCase("myproducttransfer")) {
					return dbTransferRepo.getMyTransferItems(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()), page);
				}
				if(filter.equalsIgnoreCase("producttransferforme")) {
					return dbTransferRepo.getForMeTransferItems(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()), page);
				}
			}
			return dbTransferRepo.getTransferItems(page);
		}
		if(dbProfile.hasObjectAccess(user.getUsername(), "LPTrasfer", "viewRights")) {
			if(filter!=null) {
				if(filter.equalsIgnoreCase("myproducttransfer")) {
					return dbTransferRepo.getMyTransferItems(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()), page);
				}
				if(filter.equalsIgnoreCase("producttransferforme")) {
					return dbTransferRepo.getForMeTransferItems(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()), page);
				}
			}
			java.util.List<String> u=new java.util.ArrayList<String>();
			java.util.List<com.pratikshat.exicom.entity.EmployeeDetails> userList=new java.util.ArrayList<com.pratikshat.exicom.entity.EmployeeDetails>();
			u=dbProfile.getListOfUserAndChildByUser(user.getUsername());
			for(String ui:u) {
				userList.add(new com.pratikshat.exicom.entity.EmployeeDetails(ui));
			}
			return dbTransferRepo.getTransferItems(ids,page);
		}
		return Page.empty();
	}
	
	@GetMapping("items/{itemtransferId}")
	public com.pratikshat.exicom.model.response.LocationProductTransferItemResponse getTransferitemsByTransferItemId(@PathVariable("itemtransferId")long transferId){
		return dbTransferRepo.getTransferItemByTransferItemId(transferId);
	}
	@GetMapping("items/{itemtransferId}/lineitems")
	public java.util.List<com.pratikshat.exicom.model.response.LocationProductTransferLineItemResponse> getLineItemsByItemTransferId(@PathVariable("itemtransferId")long itemtransferId){
		return dbTransferRepo.getLineItemsByTransferItemId(itemtransferId);
	}
//	shagufta find By Id
	@GetMapping("transferLineitem/{id}")
	public LocationProductTransferLineItemResponse getLegalentity(@PathVariable("id")long id,@CurrentUser LoginUser userDetails) {
			return dbTransferRepo.getProductTransferItemsById(id);
	}
	
	
	@GetMapping("advancesearch")
	public List<LocationProductTransferItemResponse> getAdvanceSearchresult(@RequestParam Map<String,Object> request,@CurrentUser LoginUser user,HttpServletResponse  response) throws IOException, ParseException{
		
		if(dbProfile.hasObjectAccess(user.getUsername(), LPTrasfer.class, UserProfileRepository.VIEW_ALL)) {
			return 	dbTransferRepo.advanceSearch(request, user, null);
		}
		
		if(dbProfile.hasObjectAccess(user.getUsername(), LPTrasfer.class, UserProfileRepository.VIEW)) {
			return 	dbTransferRepo.advanceSearch(request, user, dbProfile.getLocationAndChildLocation(user.getUsername()));
		}
		
		response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
		return null;
		
	}
	
	@GetMapping("items/{itemtransferId}/lineitems/advancesearch")
	public java.util.List<com.pratikshat.exicom.model.response.LocationProductTransferLineItemResponse> getLineItemsByItemTransferIdAdvanceSearch(@PathVariable("itemtransferId")long transferId,@RequestParam Map<String,Object> request,@CurrentUser LoginUser user,HttpServletResponse  response) throws IOException, ParseException{
		if(transferId!=0) {
			return dbTransferRepo.getLineItemsByTransferItemIdAdvanceSearch(transferId,request);
		}
		
		response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Return Order Id is 0");
		return null;
		
		
		
	}
	
	
	@GetMapping(value = "/pdf")
	public ResponseEntity<?> createPdf(@RequestParam("productTransferId")long productTransferId,HttpServletResponse httpServletResponse,@CurrentUser LoginUser user) throws IOException {
		try {
			
			LPTrasfer response = dbTransferRepo.getProductTransferById(productTransferId);
			
			if(response==null) {
				httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST,"Product Trasnfer does not exist");
				return null;
			}
			
			ByteArrayInputStream bis = dbTransferRepo.generatePackingListPdf(user, productTransferId, "ProductTransfer");
				
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Disposition", "inline; filename="+response.getLocationProductTransferManualId()+".pdf");
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
	
	
	@GetMapping
	public Page<Map<String,Object>> getProductTransfer(@RequestParam(value="filtername",required=false)String filter,@CurrentUser com.pratikshat.exicom.entity.LoginUser user,Pageable page){
		List<Long> ids=dbProfile.getLocationAndChildLocation(user.getUsername());
		if(dbProfile.hasObjectAccess(user.getUsername(), "LPTrasfer", "viewAllRights")) {
			if(filter!=null) {
				if(filter.equalsIgnoreCase("myproducttransfer")) {
					return dbTransferRepo.getMyProductTransfer(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()), page);
				}
				if(filter.equalsIgnoreCase("producttransferforme")) {
					return dbTransferRepo.getForMeProductTransfer(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()), page);
				}
			}
			return dbTransferRepo.getProductTransfers(page);
		}
		if(dbProfile.hasObjectAccess(user.getUsername(), "LPTrasfer", "viewRights")) {
			if(filter!=null) {
				if(filter.equalsIgnoreCase("myproducttransfer")) {
					return dbTransferRepo.getMyProductTransfer(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()), page);
				}
				if(filter.equalsIgnoreCase("producttransferforme")) {
					return dbTransferRepo.getForMeProductTransfer(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()), page);
				}
			}
			java.util.List<String> u=new java.util.ArrayList<String>();
			java.util.List<com.pratikshat.exicom.entity.EmployeeDetails> userList=new java.util.ArrayList<com.pratikshat.exicom.entity.EmployeeDetails>();
			u=dbProfile.getListOfUserAndChildByUser(user.getUsername());
			for(String ui:u) {
				userList.add(new com.pratikshat.exicom.entity.EmployeeDetails(ui));
			}
			return dbTransferRepo.getProductTransfers(ids,page);
		}
		return Page.empty();
	}
	
	@GetMapping("{transferId}")
	public Map<String,Object> getProductTransferById(@PathVariable("transferId")long transferId){
		return dbTransferRepo.getTransferById(transferId);
	}
	
	@GetMapping("transferItems/{transferId}")
	public Page<com.pratikshat.exicom.model.response.LocationProductTransferItemResponse> getProductTransferItemByTransferId(@PathVariable("transferId")long transferId, Pageable page){
		return dbTransferRepo.getTransferItemsByTrnasferId(transferId, page);
	}
	
	@GetMapping("productTransfer/advancesearch")
	public List<Map<String,Object>> getProductTransferAdvanceSearchresult(@RequestParam Map<String,Object> request,@CurrentUser LoginUser user,HttpServletResponse  response) throws IOException, ParseException{
		
		if(dbProfile.hasObjectAccess(user.getUsername(), LProductRequest.class, UserProfileRepository.VIEW_ALL)) {
			return 	dbTransferRepo.productTransferAdvanceSearch(request, user, null);
		}
		
		if(dbProfile.hasObjectAccess(user.getUsername(), LProductRequest.class, UserProfileRepository.VIEW)) {
			return 	dbTransferRepo.productTransferAdvanceSearch(request, user, dbProfile.getLocationAndChildLocation(user.getUsername()));
		}
		
		response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
		return null;
		
	}
}
