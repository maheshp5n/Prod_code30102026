package com.pratikshat.exicom.controller;

import java.io.IOException;
import java.text.ParseException;
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
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.ResourceType;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.response.AccountsForList;
import com.pratikshat.exicom.model.response.LocationResponse;
import com.pratikshat.exicom.model.response.SSAResponse;
import com.pratikshat.exicom.model.response.ServiceAppointmentResponse;
import com.pratikshat.exicom.model.response.ServiceResourseResponse;
import com.pratikshat.exicom.repository.ResourceTypeRepository;
import com.pratikshat.exicom.repository.ServiceRecourseRecentVisitRepository;
import com.pratikshat.exicom.repository.ServiceRecourseRepository;
import com.pratikshat.exicom.repository.UserProfileRepository;



@RestController
@RequestMapping("bt/api/serviceresource")
public class BtServiceResourceController {
	@Autowired
	private ServiceRecourseRepository dbService;
	
	@Autowired
	private ResourceTypeRepository dbResourceType;
	
	@Autowired
	ServiceRecourseRecentVisitRepository dbServiceRecentVist;
	
	@Autowired
	private com.pratikshat.exicom.repository.UserProfileRepository dbProfile;
	
	int businessDomainId = 1;
	
	@GetMapping("forlist/{legalEntityId}")
	public java.util.List<com.pratikshat.exicom.model.response.ServiceResourseForList> getForList(@PathVariable("legalEntityId")long legalEntityId){
		return dbService.getServiceResourseList(legalEntityId, businessDomainId);
	}
	@GetMapping
	public Page<com.pratikshat.exicom.model.response.ServiceResourseResponse> getAll(Pageable page, @CurrentUser LoginUser user, HttpServletResponse response,@RequestParam(value="filtername",required=false)String filtername)throws IOException{
		if(dbProfile.hasObjectAccess(user.getUsername(), "ServiceRecource", "viewRights")||dbProfile.hasObjectAccess(user.getUsername(), "ServiceRecource", "viewAllRights")) {
			try {
				if(filtername!=null&&filtername.equalsIgnoreCase("related"))
					return dbService.getRecentVisit(user.getUsername(), page, businessDomainId); 
				return dbService.getByAll(page, businessDomainId);
			}catch(Exception e) {
				e.printStackTrace();
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
				return null;
			}
			
		}
		return Page.empty();
		
	}
	@GetMapping("{serviceid}")
	public com.pratikshat.exicom.model.response.ServiceResourseResponse getByServiceId(@PathVariable("serviceid")long serviceId,@CurrentUser LoginUser user, HttpServletResponse response) throws IOException{
		
		if(dbProfile.hasObjectAccess(user.getUsername(), "ServiceRecource", "viewRights")||dbProfile.hasObjectAccess(user.getUsername(), "ServiceRecource", "viewAllRights")) {
			try {
				ServiceResourseResponse servieResourseResponse = dbService.getByResourseId(serviceId);
				try {
					com.pratikshat.exicom.entity.ServiceResourceRecentVisit _recent=dbServiceRecentVist.findByServiceResourceIdAndVisitorId(serviceId, user.getUsername());
					if(_recent==null)
						_recent=new com.pratikshat.exicom.entity.ServiceResourceRecentVisit(serviceId, user.getUsername());
					else
						_recent.setVisitedDate(new java.util.Date());
					dbServiceRecentVist.save(_recent);
					dbServiceRecentVist.flush();
				}catch(Exception e) {}
				
				return servieResourseResponse;
			}catch(Exception e) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
				return null;
			}
			
		}
		return new ServiceResourseResponse();
	}
	
	
	@PostMapping
	public com.pratikshat.exicom.model.request.ServiceResourseRequest postServiceResourse(@RequestBody com.pratikshat.exicom.model.request.ServiceResourseRequest serviceRequest,@CurrentUser LoginUser user, HttpServletResponse response) throws IOException {
		
		if(!dbProfile.hasObjectAccess(user.getUsername(),"ServiceRecource", "createRights")) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
		
		try {
			
			return dbService.saveServiceResource(serviceRequest, user, businessDomainId);	
		} catch (Exception ex) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
			return null;
		}
		
	}
	@PutMapping("{serviceid}")
	public com.pratikshat.exicom.model.request.ServiceResourseRequest putData(@PathVariable("serviceid")long serviceid,@CurrentUser LoginUser user,@RequestBody com.pratikshat.exicom.model.request.ServiceResourseRequest req, HttpServletResponse response) throws IOException{
		
		if(!dbProfile.hasObjectAccess(user.getUsername(),"ServiceRecource", "editRights")) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
		
		try {
			if(dbService.countByServiceResourceId(serviceid)==0) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Service Resource deos not exist");
				return null;
			} 
			if(serviceid!=req.getServiceResourceId()) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Service Resource Id does not match");
				return null;
			}
			dbService.saveServiceResource(req, user, businessDomainId);
			return req;
		} catch (BadRequestException ex) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
			return null;
		} catch(UnauthoriseException ex) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN,ex.getMessage());
			return null;
		}
		catch (Exception ex) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
			return null;
		}
		
	}
	
	@GetMapping("resourcetype")
	public List<ResourceType> getResourceTypeList(){
		return dbResourceType.findAll();
	}
	
	
	@GetMapping("recentvisit")
	public Page<ServiceResourseResponse> getReceintVisist(@CurrentUser LoginUser user,Pageable page){
		return dbService.getRecentVisit(user.getUsername(), page, businessDomainId);
	}
	@GetMapping("legalentity/{legalentityid}/forlist")
	public java.util.List<com.pratikshat.exicom.model.response.ServiceResourseForList> getServiceResourceByLegalEntity(@PathVariable("legalentityid")long legalEntityId){
		return dbService.getServiceResourseListByLegalEntity(legalEntityId, businessDomainId);
	}
	
	@GetMapping("related/{serviceid}/ServiceAppointment")
	public Page<Map<String, Object>> getServiceAppointmentByServiceId(@PathVariable("serviceid")long serviceid,Pageable page){
		return dbService.getBtServiceAppointmentByServiceId(serviceid, page);
	}
//	
//	@GetMapping("related/{serviceResourceId}/location")
//	public Page<LocationResponse> getLocationByServiceResource(@PathVariable("serviceResourceId")long serviceResourceId,Pageable page){
//		return dbService.getLocationByServiceResource(serviceResourceId, page);
//	}
	
	@GetMapping("forList")
	public java.util.List<com.pratikshat.exicom.model.response.ServiceResourseForList> getServiceResourceForList(){
		return dbService.getServiceResourceForList(businessDomainId);
	}
	
	@GetMapping("advancesearch")
	public List<ServiceResourseResponse> getAdvanceSearchresult(@RequestParam Map<String,Object> request,@CurrentUser LoginUser user,HttpServletResponse  response) throws IOException, ParseException{
		
		if(dbProfile.hasObjectAccess(user.getUsername(), com.pratikshat.exicom.entity.ServiceRecource.class, UserProfileRepository.VIEW_ALL)) {
			return 	dbService.advanceSearch(request, user, businessDomainId);
		}
		
		if(dbProfile.hasObjectAccess(user.getUsername(), com.pratikshat.exicom.entity.ServiceRecource.class, UserProfileRepository.VIEW)) {
			return 	dbService.advanceSearch(request, user, businessDomainId);
		}
		
		response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
		return null;
		
	}
	
	@GetMapping("rating")
	public Page<Map<String,Object>> getRating(Pageable page){
		return dbService.getServiceResourceRating(page, businessDomainId);
	}
	@GetMapping("rating/search")
	public List<Map<String,Object>> searchRating(@RequestParam Map<String,Object> data){
		return dbService.getServiceResourceRatingAdvanceSearch(data, businessDomainId);
	}
	
}