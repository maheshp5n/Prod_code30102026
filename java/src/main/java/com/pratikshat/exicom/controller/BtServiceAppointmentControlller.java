package com.pratikshat.exicom.controller;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
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
import org.springframework.web.multipart.MultipartFile;

import com.pratikshat.exicom.annotation.CurrentUser;
import com.pratikshat.exicom.entity.BtFsrFileUpload;
import com.pratikshat.exicom.entity.BtServiceAppointment;
import com.pratikshat.exicom.entity.BtWccDetails;
import com.pratikshat.exicom.entity.ClaimStatus;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.PendingResponsibility;
import com.pratikshat.exicom.entity.ServiceAppointmentStatus;
import com.pratikshat.exicom.entity.TimeList;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.response.BtServiceAppointmentResponse;
import com.pratikshat.exicom.model.response.SimpleResponseMessage;
import com.pratikshat.exicom.repository.BtFsrFileUploadRepositry;
import com.pratikshat.exicom.repository.BtServiceAppoinmentProductConsumedRepository;
import com.pratikshat.exicom.repository.BtServiceAppointmentRepository;
import com.pratikshat.exicom.repository.BtServicePayoutRepository;
import com.pratikshat.exicom.repository.ClaimStatusRepository;
import com.pratikshat.exicom.repository.PendingResponsibilityRepository;
import com.pratikshat.exicom.repository.ServiceAppointmentStatusRepository;
import com.pratikshat.exicom.repository.TimeListRepository;
import com.pratikshat.exicom.repository.UserProfileRepository;
import com.pratikshat.exicom.services.BtAnotherServiceAppointmentService;
import com.pratikshat.exicom.services.BtServiceAppointmentProductConsumedService;
import com.pratikshat.exicom.services.BtServiceAppointmentService;
import com.pratikshat.exicom.services.FileStorageService;

@RestController
@RequestMapping("bt/api/serviceappointment")
public class BtServiceAppointmentControlller {
	
	@Autowired
	BtServiceAppointmentService serviceAppointment;
	@Autowired
	BtAnotherServiceAppointmentService anotherServiceAppointment;
	@Autowired
	ClaimStatusRepository dbClaimStatus;
	@Autowired
	PendingResponsibilityRepository dbPendingResponsibility;
	@Autowired
	TimeListRepository dbTime;	
	@Autowired
	ServiceAppointmentStatusRepository dbServiceAppointmentStatus;
	@Autowired
	BtServiceAppointmentRepository dbServiceAppointment;
	@Autowired
	UserProfileRepository dbProfile;
	@Autowired
	BtServiceAppoinmentProductConsumedRepository dbConsumed;
	@Autowired
	BtServiceAppointmentProductConsumedService serBtSAPC;
	@Autowired
	BtServiceAppointmentProductConsumedService serBtService;
	@Autowired
	BtServicePayoutRepository dbSp;
	@Autowired
	BtFsrFileUploadRepositry dbFsrFileUploadRepo;
	@Autowired 
	FileStorageService fileStorageService;
	

	@GetMapping("{serviceappointmentid}/consumed")
	public Page<Map<String,Object>> consumedProductDetails(
			@org.springframework.web.bind.annotation.PathVariable("serviceappointmentid")long serviceappointmentid,
			@com.pratikshat.exicom.annotation.CurrentUser com.pratikshat.exicom.entity.LoginUser user,
			Pageable pageable) {
		//return dbConsumed.getProductConsumed(serviceappointmentid,pageable);
		return dbConsumed.getProductConsumedNative(serviceappointmentid, pageable);
	}
	
	@GetMapping("related/{serviceAppointmentId}")
	public Page<com.pratikshat.exicom.model.response.ServiceResourseResponse> getServiceResourceByAppointmentNumber(@PathVariable("serviceAppointmentId")long serviceAppointmentId,Pageable page ){
		return dbServiceAppointment.getServiceResourseByServiceAppointment(serviceAppointmentId,page);
	}
	
	@GetMapping("pendingresponsibility")
	public List<PendingResponsibility> getPendingResponsibility(){
		return dbPendingResponsibility.findAll();
	}
	@GetMapping("time")
	public List<TimeList> getTime(){
		return dbTime.findAll();
	}
	@GetMapping("claimstatus")
	public List<ClaimStatus> getClaimStatus(){
		return dbClaimStatus.findAll();
	}
	@PostMapping("reassign")
	public Map<String,Object> reAssignBtServiceAppoinment(@RequestBody Map<String, Object> req, @CurrentUser LoginUser user) throws Exception{
		return serviceAppointment.reAssignBtServiceAppointment(req, user);
	}
	@PostMapping
	public Map<String, Object> saveBtAppointment(@RequestBody Map<String, Object> req,@CurrentUser LoginUser user) throws BadRequestException, UnauthoriseException {
		return serviceAppointment.saveObject(req, user);
	}
	@PutMapping("{id}")
	public Map<String, Object> updateAssets(@RequestBody Map<String, Object> reqq,@PathVariable("id") long id,@CurrentUser LoginUser user) throws BadRequestException, UnauthoriseException {
		if(id!=Long.parseLong(reqq.get("serviceAppointmentId").toString())) 
			throw new BadRequestException("Service Appointment id is not atched");
		return serviceAppointment.updateObject(reqq, user);
	}
	@GetMapping("/advancesearch")
	public List<Map<String , Object>> advanceSearch(@RequestParam Map<String,Object> req, @CurrentUser LoginUser user) throws BadRequestException ,UnauthoriseException{
		
		return serviceAppointment.advanceSearch(req, user); 
		
	}
	@GetMapping("/{id}")
	public Map<String, Object> getById(@PathVariable( "id" )int id,@CurrentUser LoginUser user) throws BadRequestException, UnauthoriseException{
		return  serviceAppointment.getSingle(id, user);	
	}
	
	
	@GetMapping("res/{serid}")
	public BtServiceAppointmentResponse getByServiceId(@PathVariable( "serid" )long id,@CurrentUser LoginUser user) throws BadRequestException, UnauthoriseException{
		
		return  serviceAppointment.getByServiceAppId(id,user);	
	}
	
	@GetMapping("sch/{id}")
	public Page<?> getSchDetails(@PathVariable( "id" )long id,@CurrentUser LoginUser user,Pageable page) throws BadRequestException, UnauthoriseException{
		return  serviceAppointment.getSchDetailsAppId(id,user,page);	
	}
	
	@GetMapping
	public Page<Map<String, Object>> getPage(@CurrentUser LoginUser user,Pageable page,@RequestParam(value="filtername",required=false)String filtername) throws BadRequestException, UnauthoriseException{
		return serviceAppointment.getPage(user, page, filtername);
	}
	
	@GetMapping("forlist/servicePayout")
	public List<Map<String, Object>> getServicepayout(){
		return dbSp.forListForPayout();
	}
		
	@GetMapping("status/forlist")
	public java.util.List<String> getServiceAppointmentStatusList() {
		return dbServiceAppointmentStatus.getServiceAppointmentStatusList();
	}
	
	@GetMapping("forlist")
	public List<Map<String, Object>> forList(@RequestParam("search") String search,@CurrentUser LoginUser user) throws BadRequestException, UnauthoriseException{
		return	serviceAppointment.searchForList(search, user);
	}
	
	@GetMapping("byclaimstatus")
	public Page<Map<String, Object>> getServiceAppointmentListByClaimStatus(@RequestParam(name = "claimstatus") String claimstatus,Pageable page,@CurrentUser LoginUser user){
		return serviceAppointment.getServiceAppointmentListByClaimStatus(claimstatus, page, user);
	}
	
	@GetMapping("bystatus")
	public Page<Map<String, Object>> getServiceAppointmentListByStatus(@RequestParam(name = "status") String status,Pageable page,@CurrentUser LoginUser user){
		return serviceAppointment.getServiceAppointmentListByStatus(status, page, user);
	}
	
	@GetMapping("status")
	public List<ServiceAppointmentStatus> getServiceAppointmentStatus(){
		List<Long> list = new ArrayList<>();
		list.add(3L);
		list.add(4L);
		list.add(5L);
		//list.add(6L);
		return dbServiceAppointmentStatus.getStatus(list);
	}
	
	@PostMapping("{serviceappointmentid}/consumed")
	public Map<String, Object> consumeProductDetails(
			@PathVariable("serviceappointmentid") long serviceappointmentid,
			@RequestBody Map<String, Object> consumeRequest,@CurrentUser LoginUser user) throws BadRequestException, UnauthoriseException {
		if(serviceappointmentid!= Long.parseLong(consumeRequest.get("serviceAppointmentId").toString())) {
			throw new BadRequestException("Please provide correct Service appointmentId");
		}
		return  serBtSAPC.saveObject(consumeRequest, user);
	}
	
	@DeleteMapping("deleteconsumed/{consumedid}")
	public ResponseEntity<?> deleteConsumedItems(@PathVariable("consumedid")long consumedid,@CurrentUser LoginUser user,HttpServletResponse response) throws IOException{
		try {
			serBtSAPC.deleteProduct(consumedid, user);
			return ResponseEntity.ok("{}");
		} catch (BadRequestException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
		} catch (UnauthoriseException e) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	@PostMapping("anotherserviceappointment")
	public SimpleResponseMessage requestForAnotherServiceAppointment(@RequestParam("caseId")long caseId, @RequestParam("remark")String remark, @CurrentUser LoginUser user, HttpServletResponse response) throws IOException{
		try {
			serviceAppointment.requestForAnotherServiceAppointment(caseId, remark, user);
			return new SimpleResponseMessage("submitted Successfully");
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
	
	@GetMapping("anotherserviceappointment/{id}")
	public Map<String, Object> geAnotherServiceAppointmentbyId(@PathVariable("id")int id, @CurrentUser LoginUser user)throws BadRequestException, UnauthoriseException{
		return anotherServiceAppointment.getSingle(id, user);
	}
	
	@GetMapping("download/{serAppId}/fsr/fileupload/{id}")
	public ResponseEntity<?> downloadFsrFileUpload(@PathVariable("serAppId")long serAppId,@PathVariable("id") long id ){
		Map<String, Object> fsrFileUpload=dbFsrFileUploadRepo.getByServiceAppointmentAndFileUploadId(serAppId,id);
		if(fsrFileUpload!=null){
			Resource resource=fileStorageService.loadFileAsResource(fsrFileUpload.get("physical_file_location").toString());
			return ResponseEntity.ok()
		               .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fsrFileUpload.get("file_name`").toString() + "\"")
		               .body(resource);
		}else {
			return ResponseEntity.badRequest().body("{}");
		}
		
	}
	
	@PostMapping("fsr/fileupload/{id}") 
	public Map<String, Object> fsrFileUpload(@PathVariable("id") long id,@RequestBody MultipartFile file,@CurrentUser LoginUser user) throws BadRequestException, UnauthoriseException{
		
		BtServiceAppointment appointment= dbServiceAppointment.findById(id).get();
		String fileName= file.getOriginalFilename();
		if(fileName.substring(fileName.lastIndexOf('.') + 1).equals("pdf") || fileName.substring(fileName.lastIndexOf('.') + 1).equals("jpg") || fileName.substring(fileName.lastIndexOf('.') + 1).equals("jpeg") ||fileName.substring(fileName.lastIndexOf('.') + 1).equals("png")) {
			if(appointment!=null) {
				String files=fileStorageService.storeFile(file);
				BtFsrFileUpload fsrFileUpload=new BtFsrFileUpload();
				fsrFileUpload.setAppointment(appointment);
				fsrFileUpload.setCreatedDate(new Date());
				fsrFileUpload.setCreatedBy(new EmployeeDetails(user.getUsername()));
				fsrFileUpload.setFileName(fileName);
				fsrFileUpload.setPhysicalFileLocation(files);
				dbFsrFileUploadRepo.saveDetails(fsrFileUpload, user);
				
			}else {
				throw new BadRequestException("Invalid appointment id");
			}
		}else {
			throw new BadRequestException("File format should be PDF,JPG,JPEG or PNG");
		}
		
		return null;
		
	}

}