package com.pratikshat.exicom.controller;


import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pratikshat.exicom.annotation.CurrentUser;

import com.pratikshat.exicom.entity.CircleDetails;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;

import com.pratikshat.exicom.model.response.CircleDetailsResponse;
import com.pratikshat.exicom.model.response.SSAResponse;

import com.pratikshat.exicom.repository.CircleDetailsRepository;
import com.pratikshat.exicom.repository.SSARepository;
import com.pratikshat.exicom.repository.UserProfileRepository;


@RestController
@RequestMapping("bt/api/circle")
public class BtCircleController {
	@Autowired
	CircleDetailsRepository dbCircle;
	@Autowired
	com.pratikshat.exicom.repository.CircleRecentVisitRepository dbRecent;
	@Autowired
	SSARepository dbSSA;
	@Autowired
	private com.pratikshat.exicom.repository.UserProfileRepository dbUserProfile;
	
	int businessDomainId=1;
	
	@GetMapping
	public Page<com.pratikshat.exicom.model.response.CircleDetailsResponse> getAll(Pageable page,@CurrentUser LoginUser user,@RequestParam(value="filtername",required=false)String filtername){
		if(dbUserProfile.hasObjectAccess(user.getUsername(), "CircleDetails", "viewAllRights") || dbUserProfile.hasObjectAccess(user.getUsername(), "CircleDetails", "viewRights")) {
			if(filtername!=null&&filtername.equalsIgnoreCase("recent"))
				return dbCircle.getRecent(user.getUsername(), page);
			return dbCircle.getAll(page,businessDomainId);
		}
		return Page.empty();
	}
	
	@GetMapping("{circlename}")
	public com.pratikshat.exicom.model.response.CircleDetailsResponse getBycircleName(@PathVariable("circlename")String circleName,@CurrentUser LoginUser user, HttpServletResponse response)throws IOException{
		
		if(dbUserProfile.hasObjectAccess(user.getUsername(), "CircleDetails", "viewAllRights") || dbUserProfile.hasObjectAccess(user.getUsername(), "CircleDetails", "viewRights")) {
			
			CircleDetailsResponse circleDetailsResponse=dbCircle.getByCircleName(circleName);
			try {
				com.pratikshat.exicom.entity.CircleRecentVisit _recent=dbRecent.findByCircleNameAndVisitorId(circleName, user.getUsername());
				if(_recent!=null) {
					_recent.setVisitDate(new java.util.Date());
				}
				else {
					_recent=new com.pratikshat.exicom.entity.CircleRecentVisit(circleName, user.getUsername());
				}
				dbRecent.save(_recent);
			}catch(Exception e) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
				return null;
			}
			return circleDetailsResponse;
		}

		return new CircleDetailsResponse();
	}
	@GetMapping("recent")
	public Page<com.pratikshat.exicom.model.response.CircleDetailsResponse> getRecentVisit(@CurrentUser LoginUser user,Pageable page){
		if(dbUserProfile.hasObjectAccess(user.getUsername(), "CircleDetails", "viewRights")||dbUserProfile.hasObjectAccess(user.getUsername(), "CircleDetails", "viewAllRights")) {
			
			return dbCircle.getRecent(user.getUsername(), page);
		}
		return Page.empty();
	}
	
	@PostMapping
	public com.pratikshat.exicom.model.response.CircleDetailsResponse postCircle(@CurrentUser LoginUser user,@RequestBody com.pratikshat.exicom.model.request.CircleDetailsRequest circleRequest,HttpServletResponse response) throws IOException{
		if(!dbUserProfile.hasObjectAccess(user.getUsername(), "CircleDetails", "createRights")) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You don't have permission");
			return null;
		}
		try {
			
			if(dbCircle.countByCircleName(circleRequest.getCircleName())>0) {
				dbCircle.updateCircleDetails(circleRequest, user.getUsername(), businessDomainId);
			}
			else
			
				dbCircle.saveDetails(circleRequest, user.getUsername(),businessDomainId);
			
			return dbCircle.getByCircleName(circleRequest.getCircleName());	
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
	@PutMapping("{circleName}")
	public com.pratikshat.exicom.model.response.CircleDetailsResponse edit(@PathVariable("circleName")String circleName, @CurrentUser LoginUser user,@RequestBody com.pratikshat.exicom.model.request.CircleDetailsRequest circleRequest,HttpServletResponse response) throws IOException{
		if(!dbUserProfile.hasObjectAccess(user.getUsername(), "CircleDetails", "editRights")) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You don't have permission");
			return null;
		}
		try {
			if(dbCircle.countByCircleName(circleName)==0) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST,"Circle does not exists");
				return null;
			}
			if(!circleRequest.getCircleName().equalsIgnoreCase(circleName)) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST,"Circle name does not match");
				return null;
			}
			dbCircle.updateCircleDetails(circleRequest, user.getUsername(), businessDomainId);
			return dbCircle.getByCircleName(circleName);
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
	@GetMapping("forlist")
	public List<com.pratikshat.exicom.model.response.CircleDetailsForList> getforList(){
		return dbCircle.forList(businessDomainId);
	}
	
	@GetMapping("circle/forlist")
	public List<com.pratikshat.exicom.model.response.CircleZoneForList> getCircleAndZoneList(){
		return dbCircle.getCircleZoneList();
	}
	
	@GetMapping("related")
	public Page<SSAResponse> getSSAByCircle(@RequestParam("circleName")String circleName,Pageable page){
		return dbSSA.getSAAByCircleName(circleName, page);
	}
	
	@GetMapping("circle/legalentity/{id}")
	public List<com.pratikshat.exicom.model.response.CircleDetailsForList> getCircleListByLegalEntity(@PathVariable("id")long legalEntityId){
		return dbCircle.circleListByLegalEntity(legalEntityId);
	}
	
	@GetMapping("advancesearch")
	public List<CircleDetailsResponse> getAdvanceSearchresult(@RequestParam Map<String,Object> request,@CurrentUser LoginUser user,HttpServletResponse  response) throws IOException{
		if(dbUserProfile.hasObjectAccess(user.getUsername(), CircleDetails.class, UserProfileRepository.VIEW) || 
				dbUserProfile.hasObjectAccess(user.getUsername(), CircleDetails.class, UserProfileRepository.VIEW_ALL)) {
			return dbCircle.advanceSearch(request, user, null,businessDomainId);
		}
		response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
		return null;
		
	}
}
