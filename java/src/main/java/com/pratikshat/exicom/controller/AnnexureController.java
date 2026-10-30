package com.pratikshat.exicom.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import com.pratikshat.exicom.entity.AnnexureApprovalStatus;
import com.pratikshat.exicom.entity.AnnexureGLCode;
import com.pratikshat.exicom.entity.AnnexureRecentVisit;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.AnnexureLineItemRequest;
import com.pratikshat.exicom.model.request.AnnexureRequest;
import com.pratikshat.exicom.model.response.AnnexureLineItemResponse;
import com.pratikshat.exicom.model.response.AnnexureResponse;
import com.pratikshat.exicom.model.response.SimpleResponseMessage;
import com.pratikshat.exicom.repository.AnnexureApprovalStatusRepository;
import com.pratikshat.exicom.repository.AnnexureGLCodeRepository;
import com.pratikshat.exicom.repository.AnnexureLineItemRepository;
import com.pratikshat.exicom.repository.AnnexureRecentVisitRepository;
import com.pratikshat.exicom.repository.AnnexureRepository;

@RestController
@RequestMapping("api/annexure")
public class AnnexureController {

	@Autowired
	AnnexureRepository dbAnnexure;
	
	@Autowired
	AnnexureApprovalStatusRepository dbAnnexureApprovalStatus;
	
	@Autowired
	AnnexureRecentVisitRepository dbAnnexureRecentVisit;
	
	@Autowired
	AnnexureGLCodeRepository dbAnnexureGLCode;
	
	@Autowired
	AnnexureLineItemRepository dbAnnexureLineItem;
	
	@Autowired
	com.pratikshat.exicom.repository.UserProfileRepository dbProfile;
	
	@GetMapping("approvalstatus")
	public List<AnnexureApprovalStatus> getApprovalStatus(){
		return dbAnnexureApprovalStatus.findAll();
	}
	
	@GetMapping("glcode")
	public List<AnnexureGLCode> getGLCode(){
		return dbAnnexureGLCode.findAll();
	}
	
	
	@GetMapping
	public Page<AnnexureResponse> getAnnexurePageable(Pageable page,@CurrentUser LoginUser user,@RequestParam(value="filtername",required=false)String filtername){
		if(dbProfile.hasObjectAccess(user.getUsername(),"Annexure", "viewAllRights")) {
			if(filtername!=null&&filtername.equalsIgnoreCase("recentvisit"))
				return dbAnnexure.getAnnexureRecentVisit(user.getUsername(), page); 
			return dbAnnexure.getAnnexurePageable(page);
		}
		if(dbProfile.hasObjectAccess(user.getUsername(), "Annexure", "viewRights")) {
			List<String> uList=new ArrayList<String>();
			uList=dbProfile.getListOfUserAndChildByUser(user.getUsername());
			List<com.pratikshat.exicom.entity.EmployeeDetails> userList=new ArrayList<com.pratikshat.exicom.entity.EmployeeDetails>();
			for(String u:uList) {
				userList.add(new com.pratikshat.exicom.entity.EmployeeDetails(u));
			}
			return dbAnnexure.getAnnexurePageable(userList, page);
		}
		return Page.empty();
	}
	
	
	@GetMapping("recentvisit")
	public Page<AnnexureResponse> getReceintVisist(@CurrentUser LoginUser user,Pageable page){
		if(dbProfile.hasObjectAccess(user.getUsername(), "Annexure", "viewRights")||dbProfile.hasObjectAccess(user.getUsername(), "Annexure", "viewAllRights")) {
			
			return dbAnnexure.getAnnexureRecentVisit(user.getUsername(), page);
		}
		return Page.empty();
	}
	
	
	@GetMapping("{annexureId}")
	public AnnexureResponse getAnnexureById(@PathVariable("annexureId")long annexureId,@CurrentUser LoginUser user, HttpServletResponse response)throws IOException{
		if(dbProfile.hasObjectAccess(user.getUsername(), "Annexure", "viewRights")||dbProfile.hasObjectAccess(user.getUsername(), "Annexure", "viewAllRights")) {
			
			try {
				AnnexureRecentVisit _recent=dbAnnexureRecentVisit.findByAnnexureIdAndVisitorEmailId(annexureId, user.getUsername());
				if(_recent!=null) {
					_recent.setVisistdate(new java.util.Date());
				}
				else {
					_recent=new AnnexureRecentVisit(annexureId, user.getUsername());
				}
				dbAnnexureRecentVisit.save(_recent);
				return dbAnnexure.getAnnexureById(annexureId);
			}catch(Exception e) {
				e.printStackTrace();
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
				return null;
			}
			
		}
		return new AnnexureResponse();	
	}
	
	@PostMapping
	public AnnexureResponse createAnnexure(@Valid @RequestBody AnnexureRequest annexureRequest,
			@CurrentUser LoginUser user, HttpServletResponse response) throws java.io.IOException {
		if(!dbProfile.hasObjectAccess(user.getUsername(), "Annexure", "createRights")) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
		try {
			AnnexureRequest request = dbAnnexure.saveAnnexure(annexureRequest, user);
			return dbAnnexure.getAnnexureById(request.getId());
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
	public AnnexureResponse updateAnnexure(@Valid @RequestBody AnnexureRequest annexureRequest,
			@CurrentUser LoginUser user, HttpServletResponse response) throws java.io.IOException {
		if(!dbProfile.hasObjectAccess(user.getUsername(), "Annexure", "editRights")) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
		try {
			AnnexureRequest request = dbAnnexure.updateAnnexure(annexureRequest, user);
			return dbAnnexure.getAnnexureById(request.getId());
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
	
	@GetMapping("lineitem")
	public Page<AnnexureLineItemResponse> getAnnexureLineItemPageable(@RequestParam("annexureId")long annexureId, Pageable pageable){
		return dbAnnexureLineItem.getAnnexureLineItemByAnnexureId(annexureId, pageable);
	}
	
	@PostMapping("lineitem")
	public AnnexureLineItemResponse addAnnexureLineItem(@Valid @RequestBody AnnexureLineItemRequest request, @CurrentUser LoginUser user, HttpServletResponse response)throws java.io.IOException {
		if(!dbProfile.hasObjectAccess(user.getUsername(), "Annexure", "createRights")) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
		try {
			AnnexureLineItemRequest lineItem = dbAnnexure.addAnnexureLineItem(request, user);
			return dbAnnexureLineItem.getAnnexureLineItemById(lineItem.getId());
		}catch(BadRequestException ex) {
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
	
	
	@PutMapping("lineitem")
	public AnnexureLineItemResponse updateAnnexureLineItem(@Valid @RequestBody AnnexureLineItemRequest request, @CurrentUser LoginUser user, HttpServletResponse httpServletResponse)throws java.io.IOException{
		if(!dbProfile.hasObjectAccess(user.getUsername(), "Annexure", "editRights")) {
			httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
		try {
			AnnexureLineItemRequest lineItem = dbAnnexure.updateAnnexureLineItem(request, user);
			return dbAnnexureLineItem.getAnnexureLineItemById(lineItem.getId());
		}catch(BadRequestException ex) {
			httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
			return null;
		}catch(UnauthoriseException ex) {
			httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN, ex.getMessage());
			return null;
		} catch (Exception ex) {
			httpServletResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
			return null;
		}
	}
	
	@GetMapping("lineitem/{id}")
	public AnnexureLineItemResponse getAnnexureLineItemById(@CurrentUser LoginUser user,@PathVariable("id")long id){
		return dbAnnexureLineItem.getAnnexureLineItemById(id);
	}
	
	
	@DeleteMapping("lineitem/{annexureLineItemId}")
	public SimpleResponseMessage deleteOppertunityLineItem(@PathVariable("annexureLineItemId")long annexureLineItemId, @CurrentUser LoginUser user, HttpServletResponse httpServletResponse)throws IOException {
		try {
			dbAnnexure.deleteAnnexureLineItem(annexureLineItemId, user);
			return new SimpleResponseMessage("deleted Successfully");
		}catch(BadRequestException ex) {
			httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
			return null;
		}catch(UnauthoriseException ex) {
			httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN, ex.getMessage());
			return null;
		} catch (Exception ex) {
			httpServletResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
			return null;
		}
		
	}
	
	@PostMapping("submitforapproval")
	public SimpleResponseMessage submitForApproval(@RequestParam("annexureId")long annexureId, @RequestParam("businessDoaminId")int businessDoaminId,@CurrentUser LoginUser user, HttpServletResponse httpServletResponse)throws java.io.IOException{
		if(!dbProfile.hasObjectAccess(user.getUsername(), "Annexure", "createRights") || !dbProfile.hasObjectAccess(user.getUsername(), "Annexure", "editRights")) {
			httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
		try {
			dbAnnexure.submitForApproval(annexureId, businessDoaminId, user);
			return new SimpleResponseMessage("submitted Successfully");
		} catch(BadRequestException ex) {
			httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
			return null;
		}catch(UnauthoriseException ex) {
			httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN, ex.getMessage());
			return null;
		} catch (Exception ex) {
			httpServletResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
			return null;
		}
		
		
	}
	
	@GetMapping(value = "/pdf")
	public ResponseEntity<?> createPdf(@RequestParam("annexureId")long annexureId,HttpServletResponse httpServletResponse,@CurrentUser LoginUser user) throws IOException {
		if(!dbProfile.hasObjectAccess(user.getUsername(), "Annexure", "createRights") || !dbProfile.hasObjectAccess(user.getUsername(), "Annexure", "editRights")) {
			httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
		
		try {
			
			AnnexureResponse annexureResponse = dbAnnexure.getAnnexureById(annexureId);
			
			if(annexureResponse!=null) {
				
				if(!annexureResponse.getOwnerEmailId().equalsIgnoreCase(user.getUsername())) {
					httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN,"You are not authorised");
					return null;	
				}
				
				ByteArrayInputStream bis = dbAnnexure.createPdf(annexureId,user);
				
				HttpHeaders headers = new HttpHeaders();
				headers.add("Content-Disposition", "inline; filename="+annexureResponse.getManualId()+".pdf");	
				
				return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(new InputStreamResource(bis));
				
			}else {
				httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST,"Annexure does not found");
				return null;
			}
			
			
		} catch(BadRequestException ex) {
			httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
			return null;
		}catch(UnauthoriseException ex) {
			httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN, ex.getMessage());
			return null;
		} catch (Exception ex) {
			httpServletResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
			return null;
		}
	}
}
