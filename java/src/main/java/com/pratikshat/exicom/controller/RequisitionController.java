package com.pratikshat.exicom.controller;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.pratikshat.exicom.annotation.CurrentUser;

import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.Products;
import com.pratikshat.exicom.entity.Requisition;
import com.pratikshat.exicom.entity.RequisitionApprovalStatus;
import com.pratikshat.exicom.entity.RequisitionProductDetails;
import com.pratikshat.exicom.entity.RequisitionProductType;
import com.pratikshat.exicom.entity.RequisitionRecentVisit;
import com.pratikshat.exicom.entity.RequisitionStatus;
import com.pratikshat.exicom.entity.Warehouse;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.RequisitionProductDetailsRequest;
import com.pratikshat.exicom.model.request.RequisitionRequest;
import com.pratikshat.exicom.model.response.LodgingExpenseResponse;
import com.pratikshat.exicom.model.response.RequisitionProductDetailsResponse;
import com.pratikshat.exicom.model.response.RequisitionResponse;
import com.pratikshat.exicom.repository.RequisitionApprovalStatusRepository;
import com.pratikshat.exicom.repository.RequisitionProductDetailsRepository;
import com.pratikshat.exicom.repository.RequisitionProductTypeRepository;
import com.pratikshat.exicom.repository.RequisitionRecentVisitRepository;
import com.pratikshat.exicom.repository.RequisitionRepository;
import com.pratikshat.exicom.repository.RequisitionStatusRepository;

@RestController
@RequestMapping("api/requisition")
public class RequisitionController {

	@Autowired
	RequisitionStatusRepository dbRequisitionStatus;
	
	@Autowired
	RequisitionApprovalStatusRepository dbRequisitionApprovalStatus;
	
	@Autowired
	RequisitionProductTypeRepository dbRequisitionProductType;
	
	@Autowired
	RequisitionRepository dbRequisition;
	
	@Autowired
	RequisitionProductDetailsRepository dbRequisitionProductDetails;
	
	@Autowired
	RequisitionRecentVisitRepository dbRequisitionRecentVisit;
	
	@Autowired
	private com.pratikshat.exicom.repository.UserProfileRepository dbProfile;
	
	@GetMapping("status")
	public List<RequisitionStatus> getStatus(){
		return dbRequisitionStatus.findAll();
	}
	
	@GetMapping("producttype")
	public List<RequisitionProductType> getProductType(){
		return dbRequisitionProductType.findAll();
	}
	
	@GetMapping("approvalstatus")
	public List<RequisitionApprovalStatus> getApprovalStatus(){
		return dbRequisitionApprovalStatus.findAll();
	}
	
	@PostMapping
	public RequisitionResponse createRequisition(@Valid @RequestBody RequisitionRequest requisitionRequest,@CurrentUser com.pratikshat.exicom.entity.LoginUser user,HttpServletResponse httpServletResponse) throws IOException {
		
		if(!dbProfile.hasObjectAccess(user.getUsername(),"Requisition", "createRights")) {
			httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
		
		try {
			RequisitionRequest _request = dbRequisition.createRequisition(requisitionRequest, user);
			RequisitionResponse requisitionResponse = dbRequisition.getByRequisitionId(_request.getRequisitionId());
			Set<RequisitionProductDetailsResponse> requisitionProductDetailsList =dbRequisitionProductDetails.getByRequisitionId(_request.getRequisitionId());
			float quantityForIssue = 0;
			if(requisitionProductDetailsList!=null) {
				
				Iterator<RequisitionProductDetailsResponse> iterator = requisitionProductDetailsList.iterator();
				while (iterator.hasNext()) {
					RequisitionProductDetailsResponse response = iterator.next();
					quantityForIssue = quantityForIssue+response.getQuantity();
				}
				requisitionResponse.setProductList(requisitionProductDetailsList);
			}
			requisitionResponse.setPendingForIssue(quantityForIssue);
			requisitionResponse.setTotalQuantityRequired(quantityForIssue);
			return requisitionResponse;
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
	
	@GetMapping
	public Page<RequisitionResponse> getRequisitionListByOwner(@CurrentUser com.pratikshat.exicom.entity.LoginUser user,Pageable page, HttpServletResponse response)throws IOException{
		if(dbProfile.hasObjectAccess(user.getUsername(), "Requisition", "viewRights")||dbProfile.hasObjectAccess(user.getUsername(), "Requisition", "viewAllRights")) {
			try {
				return dbRequisition.getRequisitionByOwner(user.getUsername(), page);
			}catch(Exception e) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
				return null;
			}
			
		}
		return Page.empty();
		
	}
	
	@GetMapping("{requisitionId}")
	public RequisitionResponse getRequisitionDetails(@CurrentUser com.pratikshat.exicom.entity.LoginUser user,@PathVariable("requisitionId")long requisitionId,Pageable page, HttpServletResponse response)throws IOException{
		
		if(dbProfile.hasObjectAccess(user.getUsername(), "Requisition", "viewRights")||dbProfile.hasObjectAccess(user.getUsername(), "Requisition", "viewAllRights")) {
			try {
				RequisitionResponse requisitionResponse = dbRequisition.getByRequisitionId(requisitionId);
				if(requisitionResponse!=null) {
					requisitionResponse.setProductList(dbRequisitionProductDetails.getByRequisitionId(requisitionId));
				}
				try {
					RequisitionRecentVisit _recent=dbRequisitionRecentVisit.findByRequisitionIdAndVisitorId(requisitionId, user.getUsername());
					if(_recent==null)
						_recent=new RequisitionRecentVisit(requisitionId, user.getUsername());
					else
						_recent.setVisitedDate(new Date());
					dbRequisitionRecentVisit.save(_recent);
					dbRequisitionRecentVisit.flush();
					return requisitionResponse;
				}catch(Exception e) {}
			}catch(Exception e) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
				return null;
			}
			
		}
		return new RequisitionResponse();
	}
	
	@GetMapping("recentvisit")
	public Page<RequisitionResponse> getReceintVisist(@CurrentUser LoginUser user,Pageable page, HttpServletResponse response) throws IOException{
		
		if(dbProfile.hasObjectAccess(user.getUsername(), "Requisition", "viewRights")||dbProfile.hasObjectAccess(user.getUsername(), "Requisition", "viewAllRights")) {
			try {
				return dbRequisition.getRequisitionRecentVisitList(user.getUsername(), page);
			}catch(Exception e) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
				return null;
			}
			
		}
		return Page.empty();
	}
	
	@PutMapping
	public RequisitionResponse updateRequisition(@Valid @RequestBody RequisitionRequest requisitionRequest,@CurrentUser com.pratikshat.exicom.entity.LoginUser user,HttpServletResponse httpServletResponse)throws IOException {
		
		if(!dbProfile.hasObjectAccess(user.getUsername(),"Requisition", "editRights")) {
			httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
		
		try {
			dbRequisition.updateRequisition(requisitionRequest,user);
			RequisitionResponse requisitionResponse = dbRequisition.getByRequisitionId(requisitionRequest.getRequisitionId());
			Set<RequisitionProductDetailsResponse> requisitionProductDetailsList =dbRequisitionProductDetails.getByRequisitionId(requisitionRequest.getRequisitionId());
			float quantityForIssue = 0;
			if(requisitionProductDetailsList!=null) {
				
				Iterator<RequisitionProductDetailsResponse> iterator = requisitionProductDetailsList.iterator();
				while (iterator.hasNext()) {
					RequisitionProductDetailsResponse response = iterator.next();
					quantityForIssue = quantityForIssue+response.getQuantity();
				}
				requisitionResponse.setProductList(requisitionProductDetailsList);
			}
			requisitionResponse.setPendingForIssue(quantityForIssue);
			requisitionResponse.setTotalQuantityRequired(quantityForIssue);
			return requisitionResponse;
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
}
