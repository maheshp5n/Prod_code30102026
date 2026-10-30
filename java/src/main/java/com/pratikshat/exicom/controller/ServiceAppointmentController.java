package com.pratikshat.exicom.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
import com.pratikshat.exicom.entity.ClaimStatus;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.LPTransferReceiveItem;
import com.pratikshat.exicom.entity.LoginUser;

import com.pratikshat.exicom.entity.PendingResponsibility;
import com.pratikshat.exicom.entity.ProductConsumptionType;
import com.pratikshat.exicom.entity.ServiceAppointment;
import com.pratikshat.exicom.entity.ServiceAppointmentRecentVisit;
import com.pratikshat.exicom.entity.ServiceAppointmentStatus;
import com.pratikshat.exicom.entity.TimeList;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.SecondServiceAppointmentRequest;
import com.pratikshat.exicom.model.request.ServiceAppointmentReAssignRequest;
import com.pratikshat.exicom.model.response.BatteryFSRResponse;
import com.pratikshat.exicom.model.response.ServiceAppointmentProductConsumedResponse;
import com.pratikshat.exicom.model.response.ServiceAppointmentResponse;
import com.pratikshat.exicom.model.response.SimpleResponseMessage;
import com.pratikshat.exicom.repository.BatteryFSRCustomRepository;
import com.pratikshat.exicom.repository.BatteryFSRCustomRepositoryImpl;
import com.pratikshat.exicom.repository.BatteryFSRRepository;
import com.pratikshat.exicom.repository.ClaimStatusRepository;
import com.pratikshat.exicom.repository.PendingResponsibilityRepository;
import com.pratikshat.exicom.repository.ProductConsumptionTypeRepository;
import com.pratikshat.exicom.repository.ServiceAppointmentRecentVisitRepository;
import com.pratikshat.exicom.repository.ServiceAppointmentStatusRepository;
import com.pratikshat.exicom.repository.TimeListRepository;
import com.pratikshat.exicom.repository.UploadedFilesRepository;
import com.pratikshat.exicom.repository.UserProfileRepository;

@RestController
@RequestMapping("api/serviceappointment")
public class ServiceAppointmentController {
	
	private static Logger logger=LoggerFactory.getLogger(ServiceAppointmentController.class);
	
	@Autowired
	ServiceAppointmentStatusRepository dbServiceAppointmentStatus;
	@Autowired
	PendingResponsibilityRepository dbPendingResponsibility;
	@Autowired
	ClaimStatusRepository dbClaimStatus;
	@Autowired
	com.pratikshat.exicom.repository.ServiceAppointmentRepository dbServiceAppointment;
	@Autowired
	com.pratikshat.exicom.repository.CaseDetailsRepository dbCase;
	@Autowired
	com.pratikshat.exicom.repository.ServiceAppoinmentProductConsumedRepository dbConsumed;
	@Autowired
	ServiceAppointmentRecentVisitRepository dbServiceAppointmentRecentVisit;
	@Autowired
	com.pratikshat.exicom.repository.LocationRepository dbLocation;
	@Autowired
	TimeListRepository dbTime;	
	@Autowired
	ProductConsumptionTypeRepository dbProductConsumptionType;
	@Autowired
	BatteryFSRRepository dbBatteryFSR;
	@Autowired
	com.pratikshat.exicom.repository.UserProfileRepository dbProfile;
	@Autowired
	private UploadedFilesRepository dbUploadFiles;
	
	@GetMapping("status")
	public List<ServiceAppointmentStatus> getServiceAppointmentStatus(){
		List<Long> list = new ArrayList<>();
		list.add(3L);
		list.add(4L);
		list.add(5L);
		//list.add(6L);
		return dbServiceAppointmentStatus.getStatus(list);
	}
	
	@GetMapping("pendingresponsibility")
	public List<PendingResponsibility> getPendingResponsibility(){
		return dbPendingResponsibility.findAll();
	}
	
	@GetMapping("claimstatus")
	public List<ClaimStatus> getClaimStatus(){
		return dbClaimStatus.findAll();
	}
	@PostMapping
	public com.pratikshat.exicom.model.response.ServiceAppointmentResponse postServiceAppoinment(@CurrentUser LoginUser user,@RequestBody SecondServiceAppointmentRequest serviceAppoinment,HttpServletResponse response) throws IOException{
		
		if(serviceAppoinment.getCaseId()==0) {
			try {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST,"Case details not found");
				return null;
			}catch(Exception ex) {
				ex.printStackTrace();
				return null;
			}
		}
		if(!dbProfile.hasObjectAccess(user.getUsername(), "ServiceAppointment", "createRights")) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
		try {
			if(dbCase.countByCaseIdAndStatus_CaseStatusName(serviceAppoinment.getCaseId(),"Close")>0) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST,"Case already closed");
				return null;
			}
			if(dbCase.countByCaseIdAndStatus_CaseStatusName(serviceAppoinment.getCaseId(), "Rejected")>0) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST,"Case is rejected");
			}
			java.util.List<String> _status=new java.util.ArrayList<String>();
			_status.add("Completed");
			_status.add("Unassigned");
			_status.add("Scheduled");
			_status.add("Dispatched");
			if(dbServiceAppointment.countByCaseDetails_caseIdAndStatus_StatusNameIn(serviceAppoinment.getCaseId(),_status)>0) {
				response.sendError(HttpServletResponse.SC_CONFLICT,"A Service Appointment already in progress or Service Appointment is compleated with this case");
				return null;
			}
			ServiceAppointment res= dbServiceAppointment.createServiceAppointmentDetails(serviceAppoinment, user);
			return dbServiceAppointment.getByAppointmentId(res.getServiceAppointmentId());
			
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
	@GetMapping
	public Page<com.pratikshat.exicom.model.response.ServiceAppointmentResponse> getServiceAppointMent(@CurrentUser LoginUser user,HttpServletResponse response,Pageable page,@RequestParam(value="filtername",required=false)String filtername){
		System.out.println(filtername);
		if(dbProfile.hasObjectAccess(user.getUsername(), "ServiceAppointment", "viewAllRights")) {
			if(filtername!=null&&filtername.equalsIgnoreCase("related")) {
				return dbServiceAppointment.getRecentVisit(user.getUsername(), page);
			}
			if(filtername!=null&&filtername.equalsIgnoreCase("all")) {
				return dbServiceAppointment.getAll(page);
			}
			if(filtername!=null&&filtername.equalsIgnoreCase("tobeClaimed")) {
				return getServiceAppointmentListByClaimStatus(filtername,page,user);
			}
			if(filtername!=null&&filtername.equalsIgnoreCase("close")) {
				
				return getServiceAppointmentListByStatus(filtername,page,user);
			}
			if(filtername!=null&&filtername.equalsIgnoreCase("Approved")) {
				return getServiceAppointmentListByClaimStatus(filtername,page,user);
			}
			if(filtername!=null&&filtername.equalsIgnoreCase("open")) {
				return	getServiceAppointmentListByStatus(filtername,page,user);
			}
			if(filtername!=null&&filtername.equalsIgnoreCase("Rejected")) {
				
				return getServiceAppointmentListByClaimStatus(filtername,page,user);
			}
			if(filtername!=null&&filtername.equalsIgnoreCase("postedinsap")) {
				return getServiceAppointmentListByClaimStatus(filtername,page,user);
			}
			return dbServiceAppointment.getAll(page);
		}
		if(dbProfile.hasObjectAccess(user.getUsername(),"ServiceAppointment", "viewRights")){
			List<String> userList=dbProfile.getListOfUserAndChildByUser(user.getUsername());
			
			List<com.pratikshat.exicom.entity.EmployeeDetails> emps=new ArrayList<com.pratikshat.exicom.entity.EmployeeDetails>();
			for(String u:userList) {
				//System.out.println("Users Under => "+u);
				emps.add(new com.pratikshat.exicom.entity.EmployeeDetails(u));
			}
			
			if(filtername!=null&&filtername.equalsIgnoreCase("related")) {
				return dbServiceAppointment.getRecentVisit(user.getUsername(), page);
			}
			if(filtername!=null&&filtername.equalsIgnoreCase("all")) {
				return dbServiceAppointment.getAll(emps,page);
			}
			if(filtername!=null&&filtername.equalsIgnoreCase("tobeClaimed")) {
				return getServiceAppointmentListByClaimStatus(filtername,page,user);
			}
			if(filtername!=null&&filtername.equalsIgnoreCase("close")) {
				return getServiceAppointmentListByStatus(filtername,page,user);
			}
			if(filtername!=null&&filtername.equalsIgnoreCase("Approved")) {
				return getServiceAppointmentListByClaimStatus(filtername,page,user);
			}
			if(filtername!=null&&filtername.equalsIgnoreCase("open")) {
				return	getServiceAppointmentListByStatus(filtername,page,user);
			}
			if(filtername!=null&&filtername.equalsIgnoreCase("Rejected")) {
				return getServiceAppointmentListByClaimStatus(filtername,page,user);
			}
			if(filtername!=null&&filtername.equalsIgnoreCase("postedinsap")) {
				return getServiceAppointmentListByClaimStatus(filtername,page,user);
			}
			
			return dbServiceAppointment.getAll(emps,page);
		}
		return Page.empty();
	}
	@PutMapping
	public com.pratikshat.exicom.model.response.ServiceAppointmentResponse updateServiceAppointment(
			@CurrentUser LoginUser user,
			@RequestBody com.pratikshat.exicom.model.request.ServiceAppointmentRequest request,
			HttpServletResponse response) throws IOException{
		if(!dbProfile.hasObjectAccess(user.getUsername(), "ServiceAppointment", "editRights")) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
		
		if(dbServiceAppointment.countByServiceAppointmentId(request.getServiceAppointmentId())==0) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST,"Service appointment not exist");
			return null;
		}
		
		if(request.getFaultObserved()==null || request.getFaultObserved().equalsIgnoreCase("")) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST,"Fault Observed is null");
			return null;
		}
		
		if(request.getCorrectiveActions()==null || request.getCorrectiveActions().equalsIgnoreCase("")) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST,"Corrective action is null");
			return null;
		}
		
		java.util.List<String> _status=new java.util.ArrayList<String>();
		_status.add("Cannot Complete");
		_status.add("Completed");
		_status.add("In-Completed");
		_status.add("Subsequently Completed");
		
		if(request.getStatusId()==3) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST,"You can not save the service appointment in dispatch mode");
			return null;
		}
		
		/*
		if(dbServiceAppointment.countByServiceAppointmentIdAndStatus_StatusNameIn(request.getServiceAppointmentId(), _status)>0) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST,"You can not edit this record");
			return null;
		}*/
		List<java.util.Map<String,Object>> fileList = dbUploadFiles.findByObjectNameAndObjectId("ServiceAppointment", String.valueOf(request.getServiceAppointmentId()));
		if(fileList==null || fileList.isEmpty()) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST,"Please upload the FSR");
			return null;
		}
		
		if(request.getClosedFromPlatform()!=null) {
			if(request.getClosedFromPlatform().equalsIgnoreCase("APP")) {
				if(request.geteSignature()==null) {
					response.sendError(HttpServletResponse.SC_BAD_REQUEST,"Please take E-Signature");
					return null;
				}
			}
		}else {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST,"You are using the older version, Please update");
			return null;
		}
		
		try {
			dbServiceAppointment.updateServiceAppointmentDetails(request, user);
			return dbServiceAppointment.getByAppointmentId(request.getServiceAppointmentId());
		}catch(BadRequestException ex) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
			return null;
		}catch(UnauthoriseException ex) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, ex.getMessage());
			return null;
		} catch (Exception ex) {
			ex.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
			return null;
		}
	}
	
	
	@PostMapping("{serviceappointmentid}/consumed")
	public Page<ServiceAppointmentProductConsumedResponse> consumeProductDetails(
			@org.springframework.web.bind.annotation.PathVariable("serviceappointmentid") long serviceappointmentid,
			@org.springframework.web.bind.annotation.RequestBody com.pratikshat.exicom.model.request.ServiceAppointmentProductConsumedRequest consumeRequest,
			@com.pratikshat.exicom.annotation.CurrentUser com.pratikshat.exicom.entity.LoginUser user,
			HttpServletResponse response
			) throws IOException {
		if(serviceappointmentid!=consumeRequest.getServiceAppointmentNumber()) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Please provide correct Service appointmentId");
		}
		
		String owner=dbServiceAppointment.getOwnerEmailId(consumeRequest.getServiceAppointmentNumber());
		if(owner==null||!owner.equalsIgnoreCase(user.getUsername())) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised to consume products");
		}
		if(dbProfile.hasObjectAccess(user.getUsername(),"ServiceAppointment", "editRights")) {
			try {
//				dbConsumed.consumeProducts(consumeRequest, user);	
				
				if(consumeRequest.getConsumeType().equalsIgnoreCase("Replaced")||consumeRequest.getConsumeType().equalsIgnoreCase("Consumed")){
					dbConsumed.consumeProductsWithFaultyProduct(consumeRequest, user);
				}
				else {
					dbConsumed.consumeProducts(consumeRequest, user);	
				}
				
				return dbConsumed.getProductConsumed(serviceappointmentid,PageRequest.of(0,5));
				
			} catch (Exception e) {
				logger.error("Error {}",e);
				e.printStackTrace();
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
			}
		}
		else {
			response.sendError(HttpServletResponse.SC_FORBIDDEN,"You are not authorised to consume products");
		}
		return null;
		
	}
	@GetMapping("{serviceappointmentid}/consumed")
	public Page<Map<String,Object>> consumedProductDetails(
			@org.springframework.web.bind.annotation.PathVariable("serviceappointmentid")long serviceappointmentid,
			@com.pratikshat.exicom.annotation.CurrentUser com.pratikshat.exicom.entity.LoginUser user,
			Pageable pageable) {
		//return dbConsumed.getProductConsumed(serviceappointmentid,pageable);
		return dbConsumed.getProductConsumedNative(serviceappointmentid, pageable);
	}
		
	@GetMapping("{serviceAppointmentId}")
	public ServiceAppointmentResponse getByServiceAppointmentId(@CurrentUser com.pratikshat.exicom.entity.LoginUser user,@PathVariable("serviceAppointmentId")long serviceAppointmentId){
		ServiceAppointmentResponse _serviceAppointment=dbServiceAppointment.getByAppointmentId(serviceAppointmentId);
		try {
			ServiceAppointmentRecentVisit _recent=dbServiceAppointmentRecentVisit.findByServiceAppointmentIdAndVisitorId(serviceAppointmentId, user.getUsername());
			if(_recent==null)
				_recent=new ServiceAppointmentRecentVisit(serviceAppointmentId, user.getUsername());
			else
				_recent.setVisitedDate(new java.util.Date());
			dbServiceAppointmentRecentVisit.save(_recent);
			dbServiceAppointmentRecentVisit.flush();
		}catch(Exception e) {}
		return _serviceAppointment;
	}
	
	@GetMapping("recentvisit")
	public Page<ServiceAppointmentResponse> getReceintVisist(@CurrentUser LoginUser user,Pageable page){
		
		if(dbProfile.hasObjectAccess(user.getUsername(),"ServiceAppointment", "viewAllRights") || dbProfile.hasObjectAccess(user.getUsername(), "ServiceAppointment", "viewRights")) {
			return dbServiceAppointment.getRecentVisit(user.getUsername(), page);
		}
		
		return Page.empty();
	}
	
	
	@GetMapping("byclaimstatus")
	public Page<ServiceAppointmentResponse> getServiceAppointmentListByClaimStatus(@RequestParam(name = "claimstatus") String claimstatus,Pageable page,@CurrentUser LoginUser user){
		if(dbProfile.hasObjectAccess(user.getUsername(),"ServiceAppointment", "viewAllRights")) {
			List<String> claimStatusList = new ArrayList<>();
			if(claimstatus.equalsIgnoreCase("Approved")) {
				claimStatusList.add("Approved By SCH");
				claimStatusList.add("Approved By Finance");
				claimStatusList.add("Approved By RSH");
			}else if(claimstatus.equalsIgnoreCase("Rejected")) {
				claimStatusList.add("Rejected By SCH");
				claimStatusList.add("Rejected By Finance");
				claimStatusList.add("Rejected By RSH");
			}else if(claimstatus.equalsIgnoreCase("tobeClaimed"))
				claimStatusList.add("To be Claimed");
			else if(claimstatus.equalsIgnoreCase("postedinsap"))
				claimStatusList.add("Posted in SAP");
			else {
				claimStatusList.add(claimstatus);
			}
			return dbServiceAppointment.getServiceAppointmentListByClaimStatus(claimStatusList, page);
		}
		if(dbProfile.hasObjectAccess(user.getUsername(), "ServiceAppointment", "viewRights")) {
			List<String> us=new ArrayList<String>();
			us=dbProfile.getListOfUserAndChildByUser(user.getUsername());
			List<com.pratikshat.exicom.entity.EmployeeDetails> emps=new ArrayList<com.pratikshat.exicom.entity.EmployeeDetails>();
			for(String u:us) {
				emps.add(new com.pratikshat.exicom.entity.EmployeeDetails(u));
			}
			List<String> claimStatusList = new ArrayList<>();
			if(claimstatus.equalsIgnoreCase("Approved")) {
				claimStatusList.add("Approved By SCH");
				claimStatusList.add("Approved By Finance");
				claimStatusList.add("Approved By RSH");
			}else if(claimstatus.equalsIgnoreCase("Rejected")) {
				claimStatusList.add("Rejected By SCH");
				claimStatusList.add("Rejected By Finance");
				claimStatusList.add("Rejected By RSH");
			}else if(claimstatus.equalsIgnoreCase("tobeClaimed"))
				claimStatusList.add("To be Claimed");
			else if(claimstatus.equalsIgnoreCase("postedinsap"))
				claimStatusList.add("Posted in SAP");
			else {
				claimStatusList.add(claimstatus);
			}
			return dbServiceAppointment.getServiceAppointmentListByClaimStatus(emps,claimStatusList,page);
		}
		return Page.empty();
	}
	
	
	@GetMapping("bystatus")
	public Page<ServiceAppointmentResponse> getServiceAppointmentListByStatus(@RequestParam(name = "status") String status,Pageable page,@CurrentUser LoginUser user){
		List<String> statusList = new ArrayList<String>();
		//System.out.println("Status =>"+status);
		//System.out.println("User =>"+user.getUsername());
		if(status.equalsIgnoreCase("open")) {
			statusList.add("Unassigned");
			statusList.add("Scheduled");
			statusList.add("Dispatched");
			
			
		}else if(status.equalsIgnoreCase("close")) {
			statusList.add("In-Completed");
			statusList.add("Cannot Complete");
			statusList.add("Completed");
			statusList.add("Subsequently Completed");
			
		}
		if(dbProfile.hasObjectAccess(user.getUsername(), "ServiceAppointment","viewAllRights")) {
			if(status.equalsIgnoreCase("open")) {
				return dbServiceAppointment.getServiceAppointmentListByStatus(statusList, page);
			}
			if(status.equalsIgnoreCase("close")) {
				return dbServiceAppointment.getServiceAppointmentListByStatus(statusList, page);
			}
		}
		if(dbProfile.hasObjectAccess(user.getUsername(), "ServiceAppointment", "viewRights")) {
			List<String> us=new ArrayList<String>();
			List<com.pratikshat.exicom.entity.EmployeeDetails> userlist=new ArrayList<com.pratikshat.exicom.entity.EmployeeDetails>();
			
			us=dbProfile.getListOfUserAndChildByUser(user.getUsername());
			for(String u:us) {
				userlist.add(new com.pratikshat.exicom.entity.EmployeeDetails(u));
			}
			
			if(status.equalsIgnoreCase("open")) {
				
				return dbServiceAppointment.getServiceAppointmentListByStatus(userlist,statusList, page);
			}
			if(status.equalsIgnoreCase("close")) {
				
				return dbServiceAppointment.getServiceAppointmentListByStatus(userlist,statusList, page);
			}
		}
		
		return Page.empty();
	}
	@GetMapping("{serviceappointid}/okproduct")
	public List<com.pratikshat.exicom.model.response.LocationProductItemForList> getOkProduct(@PathVariable("serviceappointid")long serviceappointid,@CurrentUser LoginUser user,HttpServletResponse response) throws java.io.IOException{
		String ownerName= dbServiceAppointment.getOwnerEmailId(serviceappointid);
		if(ownerName.equalsIgnoreCase(user.getUsername())) {
			com.pratikshat.exicom.model.response.LocationResponse _location=dbServiceAppointment.getLocationByServiceAppointment(serviceappointid);
			_location.getLocationId();
			return dbLocation.getOkProductByLocationIdWtihDescription(_location.getLocationId());
		}
		else {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
		}
		
		return null;
	}
	@GetMapping("okproductserialnumber/{locationproductid}")
	public List<String> getOkproductSerialNumber(@PathVariable("locationproductid")long locationproductid,@CurrentUser LoginUser user) throws Exception{
		return dbServiceAppointment.getOkSerialNumber(locationproductid, user);
	}
		
	@GetMapping("time")
	public List<TimeList> getTime(){
		return dbTime.findAll();
	}
	

	// created by shyamu
	@GetMapping("related/{serviceAppointmentId}")
	public Page<com.pratikshat.exicom.model.response.ServiceResourseResponse> getServiceResourceByAppointmentNumber(@PathVariable("serviceAppointmentId")long serviceAppointmentId,Pageable page ){
		return dbServiceAppointment.getServiceResourseByServiceAppointment(serviceAppointmentId,page);
	}
	
	@GetMapping("productconsumedtype")
	public List<ProductConsumptionType> getProductConsumedType(){
		return dbProductConsumptionType.findAll();
	}
	
	@PostMapping("anotherserviceappointment")
	public SimpleResponseMessage requestForAnotherServiceAppointment(@RequestParam("caseId")long caseId, @RequestParam("remark")String remark, @CurrentUser LoginUser user, HttpServletResponse response) throws IOException{
		
		if(!dbProfile.hasObjectAccess(user.getUsername(), "ServiceAppointment", "editRights")) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
		try {
			dbServiceAppointment.requestForAnotherServiceAppointment(caseId, remark, user);
			return new SimpleResponseMessage("submitted Successfully");
		} catch(BadRequestException ex) {
			logger.error("Error {}",ex);
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
			return null;
		}catch(UnauthoriseException ex) {
			logger.error("Error {}",ex);
			response.sendError(HttpServletResponse.SC_FORBIDDEN, ex.getMessage());
			return null;
		} catch (Exception ex) {
			logger.error("Error {}",ex);
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
			return null;
		}
	}
	
	@GetMapping("related/{serviceAppointmentId}/BatteryFSR")
	public Page<BatteryFSRResponse> getBatteryFSRByAppointmentNumber(@PathVariable("serviceAppointmentId")long serviceAppointmentId, Pageable pageable){
		
		return dbBatteryFSR.getBatteryFSRByServiceAppointmentId(serviceAppointmentId, pageable);
	}
	
	@GetMapping("forlist/byowner")
	public List<com.pratikshat.exicom.model.response.ServiceAppointmentResponse> getClosedServiceAppointmentForAnnexure(@CurrentUser LoginUser user,HttpServletResponse response){
		List<Long> claimStatusList = new ArrayList<Long>();
		claimStatusList.add(3L); // Approved By SCH
		return dbServiceAppointment.getServiceAppointmentListByStatusForAnnexure(claimStatusList, user.getUsername());
	}
	
	@PostMapping("reassign")
	public ServiceAppointmentResponse reAssignServiceAppoinment(@CurrentUser LoginUser user,@RequestBody ServiceAppointmentReAssignRequest request,HttpServletResponse response) throws IOException{
		
		if(!dbProfile.hasObjectAccess(user.getUsername(), "CaseDetails", "editRights")) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
		
		try {
			if(dbCase.countByCaseIdAndStatus_CaseStatusName(request.getCaseId(),"Closed")>0) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST,"Case already closed");
				return null;
			}
			if(dbCase.countByCaseIdAndStatus_CaseStatusName(request.getCaseId(), "Rejected")>0) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST,"Case is rejected");
			}
			ServiceAppointmentReAssignRequest res= dbServiceAppointment.reAssignServiceAppointment(request, user);
			return dbServiceAppointment.getByAppointmentId(res.getServiceAppointmentId());
			
		} catch(BadRequestException ex) {
			logger.error("Error {}",ex);
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
			return null;
		}catch(UnauthoriseException ex) {
			logger.error("Error {}",ex);
			response.sendError(HttpServletResponse.SC_FORBIDDEN, ex.getMessage());
			return null;
		} catch (Exception ex) {
			logger.error("Error {}",ex);
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
			return null;
		}
	}
	
	
	@GetMapping("forlist/forannexure")
	public List<com.pratikshat.exicom.model.response.ServiceAppointmentResponse> getServiceAppointmentForAnnexure(@CurrentUser LoginUser user,HttpServletResponse response){
		List<Long> statusList = new ArrayList<Long>();
		statusList.add(3L); // Approved By SCH
		return dbServiceAppointment.getServiceAppointmentListByClaimStatusForAnnexure(statusList, user.getUsername());
	}
	@DeleteMapping("deleteconsumed/{consumedid}")
	public ResponseEntity<?> deleteConsumedItems(@PathVariable("consumedid")long consumedid,@CurrentUser LoginUser user,HttpServletResponse response) throws IOException{
		try {
			dbConsumed.deleteProducts(consumedid, user);
			return ResponseEntity.ok("{}");
		} catch (BadRequestException e) {
			logger.error("Error {}",e);
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
		} catch (UnauthoriseException e) {
			logger.error("Error {}",e);
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	@GetMapping("advancesearch")
	public List<ServiceAppointmentResponse> getAdvanceSearchResult(@CurrentUser LoginUser user,@RequestParam Map<String,Object> data ) throws ParseException{
		
		if(dbProfile.hasObjectAccess(user.getUsername(), ServiceAppointment.class, UserProfileRepository.VIEW_ALL))
			return dbServiceAppointment.getAdvanceSearchResult(user, data,null);
		if(dbProfile.hasObjectAccess(user.getUsername(), ServiceAppointment.class, UserProfileRepository.VIEW))
			return dbServiceAppointment.getAdvanceSearchResult(user, data, dbProfile.getListOfUserAndChildByUser(user.getUsername()));
		return new ArrayList<ServiceAppointmentResponse>();
	}
	
	@GetMapping("status/forlist")
	public java.util.List<String> getServiceAppointmentStatusList() {
		return dbServiceAppointmentStatus.getServiceAppointmentStatusList();
	}
	
	
	
	@GetMapping(value = "/pdf")
	public ResponseEntity<?> createPdf(@RequestParam("serviceAppointmentId")long serviceAppointmentId,HttpServletResponse httpServletResponse,@CurrentUser LoginUser user) throws Exception {
		try {
			
			ServiceAppointmentResponse _serviceAppointment=dbServiceAppointment.getByAppointmentId(serviceAppointmentId);

			if(_serviceAppointment==null) {
				httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST,"Service Appointment does not exist");
				return null;
			}
				
			List<Long> statusList = new ArrayList<>();
			statusList.add(4L);
			statusList.add(5L);
			statusList.add(6L);
			statusList.add(7L);
			
			if(!statusList.contains(_serviceAppointment.getServiceAppointmentStatusId())) {
				httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST,"Service Appointment is not closed");
				return null;
			}
			
			ByteArrayInputStream bis = dbServiceAppointment.generatePdf(user, serviceAppointmentId);
				
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Disposition", "inline; filename="+_serviceAppointment.getServiceAppointmentManulaId()+".pdf");
			return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(new InputStreamResource(bis));
		} catch(BadRequestException ex) {
			ex.printStackTrace();
			httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
			return null;
		}catch(UnauthoriseException ex) {
			ex.printStackTrace();
			httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN, ex.getMessage());
			return null;
		}
	}
//	shagufta
	
	@GetMapping("getOkSRNDetails/{okserialNumber}")
	public List<Map<String,Object>> getOKSRDetails( @PathVariable("okserialNumber")String okserialNumber){
		
		return dbServiceAppointment.getRecievedetailsofTransferItems('%'+okserialNumber+'%');
	}
	
}
