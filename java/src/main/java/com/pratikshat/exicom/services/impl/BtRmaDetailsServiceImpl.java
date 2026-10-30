package com.pratikshat.exicom.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.pratikshat.exicom.entity.BtRMADetails;
import com.pratikshat.exicom.entity.BtRmaChangeStatusLog;
import com.pratikshat.exicom.entity.BtRmaRejectLog;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.UploadedFiles;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.ChangeStatusRmaRequest;
import com.pratikshat.exicom.model.request.RejectRmaRequest;
import com.pratikshat.exicom.model.response.BtRmaResponse;
import com.pratikshat.exicom.repository.BtChangeCaseTypeLogRepository;
import com.pratikshat.exicom.repository.BtRmaChangeStatusLogRepository;
import com.pratikshat.exicom.repository.BtRmaDetailsRepository;
import com.pratikshat.exicom.repository.BtRmaRejectLogRepository;
import com.pratikshat.exicom.repository.UploadedFilesRepository;
import com.pratikshat.exicom.repository.UserProfileRepository;
import com.pratikshat.exicom.services.BtRmaDetailsService;
import com.pratikshat.exicom.services.FileStorageService;

@Service
public class BtRmaDetailsServiceImpl implements BtRmaDetailsService {

	@Autowired
	BtRmaDetailsRepository dbRmaDetailsRepo;

	@Autowired
	UserProfileRepository dbProfile;

	@Autowired
	BtRmaRejectLogRepository dbRmaRejectLogRepo;
	
	@PersistenceContext
    EntityManager em;
	
	@Autowired
	BtRmaChangeStatusLogRepository dbRmaChangeStatusLogRepo;
	
	@Autowired
	private UploadedFilesRepository dbUploadFiles;
	
	@Autowired 
	FileStorageService fileStorageService;
	
	@Autowired
	JdbcTemplate jdbc;

	@Override
	public Map<String, Object> getSingle(Object id, LoginUser user) throws BadRequestException, UnauthoriseException {

		Long rmaId = Long.parseLong(id.toString());

		return dbRmaDetailsRepo.getByRmaId(rmaId);
	}

	@Override
	public Page<Map<String, Object>> getPage(Pageable page, LoginUser user)
			throws BadRequestException, UnauthoriseException {

		return dbRmaDetailsRepo.getAll(page);
	}

	@Override
	public Map<String, Object> saveObject(Map<String, Object> request, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> updateObject(Map<String, Object> request, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, Object>> searchForList(String search, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		// TODO Auto-generated method stub
		Page<Map<String, Object>> list = dbRmaDetailsRepo.getRmaList(search, null);
		return list.getContent();
	}

	@Override
	public List<Map<String, Object>> advanceSearch(Map<String, Object> searchRequest, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		
		List<Object> value = new ArrayList<>();
		String query= "SELECT \r\n"
				+ "    rma.`id` AS id, rma.`created_date` AS createdDate, rma.`last_modified_date` AS lastModifiedDate, \r\n"
				+ "    rma.`close_date` AS closeDate, rma.`manual_id` AS manualId, rma.`rma_status` AS rmaStatus,\r\n"
				+ "    rma.`created_by_email_id` AS createdEmailId, rma.`last_modified_by_email_id` AS modifiedEmailId,\r\n"
				+ "    rma.`case_details_id` AS caseId, btcase.`manual_id` AS caseManualId, rma.`owner_email_id` AS ownerEmailId, \r\n"
				+ "    rma.`service_appointment_service_appointment_id` AS serviceappointmentId, ser.`manual_id` AS serviceAppManualId\r\n"
				+ "	FROM `btrmadetails` rma\r\n"
				+ "	JOIN `bt_case` btcase ON btcase.id=rma.`case_details_id`\r\n"
				+ "	LEFT JOIN `bt_service_appointment` ser ON ser.`service_appointment_id`=rma.`service_appointment_service_appointment_id`\r\n"
				+ "	WHERE 1=1\r\n";
		StringBuilder queryBuilder= new StringBuilder();
		queryBuilder.append(query);
			if(searchRequest.get("rmaManualId") !=null && !searchRequest.get("rmaManualId").equals("")) {
				queryBuilder.append(" and rma.manual_id like ?");
				value.add("%"+searchRequest.get("rmaManualId")+"%");
			}
			if(searchRequest.get("rmaStatus")!=null && !searchRequest.get("rmaStatus").equals("")) {
				queryBuilder.append("and rma.`rma_status` like ?");
				value.add("%"+searchRequest.get("rmaStatus")+"%");
			}
			if(searchRequest.get("caseManualId")!=null && !searchRequest.get("caseManualId").equals("")) {
				queryBuilder.append("and btcase.`manual_id` like ?");
				value.add("%"+searchRequest.get("caseManualId")+"%");
			}
							
		 return jdbc.queryForList(queryBuilder.toString(), value.toArray());
	}

	@Override
	public Page<Map<String, Object>> getAll(LoginUser user, Pageable page, String filtername)
			throws BadRequestException, UnauthoriseException {
		if (filtername != null && filtername.equalsIgnoreCase("All")) {
			return dbRmaDetailsRepo.getAll(page);
		}
		return Page.empty();

	}

	@Override
	@Transactional(rollbackFor = { BadRequestException.class, UnauthoriseException.class,
			Exception.class }, propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED)
	public BtRmaRejectLog rejectRMAById(Map<String, Object> rejectRmaRequest,MultipartFile file, LoginUser user)
			throws BadRequestException, UnauthoriseException {

		if (!dbProfile.hasObjectAccess(user.getUsername(), BtRmaRejectLog.class, UserProfileRepository.CREATE)
				|| !dbProfile.hasObjectAccess(user.getUsername(), BtRmaRejectLog.class, UserProfileRepository.EDIT)) {
			throw new BadRequestException("You are not authorized");
		}

		if (rejectRmaRequest.get("id") == null
				|| rejectRmaRequest.get("id").toString().equalsIgnoreCase(""))
			throw new BadRequestException("RMA manual id is null");
		if (rejectRmaRequest.get("remarks") == null || rejectRmaRequest.get("remarks").toString().equalsIgnoreCase(""))
			throw new BadRequestException("RMA remarks is null");

		BtRMADetails rma = dbRmaDetailsRepo.findById(Long.parseLong(rejectRmaRequest.get("id").toString()));
			
			if (rma == null)
			throw new BadRequestException("RMA not created against this manual id");
			
//			List<java.util.Map<String,Object>> fileupload = dbUploadFiles.findByObjectNameAndObjectId("BtRMADetails",rma.getId().toString());
//			if(fileupload.size()==0)
//				throw new BadRequestException("Atleast one file upload of RMA");
			
		Map<String, String> profile = dbProfile.getAccountAndRoleGroup(user);
		String role = profile.get("roleGroup");

		if (!role.contains("SCH"))
			throw new BadRequestException("Profile should be SCH to reject RMA");

		if (!rma.getRmaStatus().toLowerCase().equalsIgnoreCase("open"))
			throw new BadRequestException("RMA Status should be OPEN");

		rma.setRmaStatus("REJECTED ON CUSTOMER REQUEST");

		dbRmaDetailsRepo.updateDetails(rma, user);

		BtRmaRejectLog rejectLog = new BtRmaRejectLog();

		rejectLog.setRmaManualId(rma.getManualId());
		rejectLog.setRemarks(rejectRmaRequest.get("remarks").toString());
		rejectLog.setCreatedBy(new EmployeeDetails(user.getUsername()));
		rejectLog.setCreatedDate(new java.util.Date());
		
		String fileName= file.getOriginalFilename();
		String files= fileStorageService.storeFile(file);
		String fileDownloadUrl= ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/downloadFile/").path(files).toUriString();

		UploadedFiles uploadedFiles = new UploadedFiles();
		uploadedFiles.setObjectName("BtRmaDetails");
		uploadedFiles.setObjectId(String.valueOf(rma.getId()));
		uploadedFiles.setFileName(files);
		uploadedFiles.setFileSize(file.getSize());
		uploadedFiles.setFileContentType(file.getContentType());
		uploadedFiles.setFileDownloadUri(fileDownloadUrl);
		uploadedFiles.setCreatedDate(new Date());
		uploadedFiles.setCreatedBy(new EmployeeDetails(user.getUsername()));

            rejectLog.setFileName(fileName);
		rejectLog.setPhysicalFileLocation(files);
		rejectLog.setMethod("Manual");

		dbRmaRejectLogRepo.saveDetails(rejectLog,uploadedFiles, user);

		return rejectLog;
	}

	@Override
	@Transactional(rollbackFor = { BadRequestException.class, UnauthoriseException.class,
			Exception.class }, propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED)
	public RejectRmaRequest rejectRma(RejectRmaRequest rejectRmaRequest, LoginUser user) throws BadRequestException, UnauthoriseException {

		Map<String, String> group = dbProfile.getAccountAndRoleGroup(user);

		String role=group.get("roleGroup");
		if(role.equals("SCH")) {
			
			BtRMADetails rma=null;
			
				rma= dbRmaDetailsRepo.findByManualId(rejectRmaRequest.getRmaManualId());
				if(rma==null) throw new BadRequestException("RMA does not exist");
				
//				List<java.util.Map<String,Object>> fileupload = dbUploadFiles.findByObjectNameAndObjectId("BtRMADetails",rma.getId().toString());
//				if(fileupload.size()==0)
//					throw new BadRequestException("Atleast one file upload of RMA");
				
				if (!rma.getRmaStatus().toLowerCase().equalsIgnoreCase("open"))
					throw new BadRequestException("RMA Status should be OPEN");

				rma.setRmaStatus("REJECTED ON CUSTOMER REQUEST");

				dbRmaDetailsRepo.updateDetails(rma, user);

				BtRmaRejectLog rejectLog = new BtRmaRejectLog();

				rejectLog.setRmaManualId(rejectRmaRequest.getRmaManualId());
				rejectLog.setRemarks(rejectRmaRequest.getRemarks());
				rejectLog.setCreatedBy(new EmployeeDetails(user.getUsername()));
				rejectLog.setCreatedDate(new java.util.Date());
				rejectLog.setMethod("Bulk Upload");

				dbRmaRejectLogRepo.saveDetails(rejectLog, user);
			
			
		}else {
			throw new BadRequestException("You are not authorized to edit this record");
		}
		
		return rejectRmaRequest;
	}
	
	@Override
	@Transactional(rollbackFor = { BadRequestException.class, UnauthoriseException.class,
			Exception.class }, propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED)
	public ChangeStatusRmaRequest changeStatusRma(ChangeStatusRmaRequest changeStatusRmaRequest, LoginUser user) throws BadRequestException, UnauthoriseException {
		
		Map<String, String> group = dbProfile.getAccountAndRoleGroup(user);
		String role=group.get("roleGroup");
		if(role.equals("SCH")) {
			BtRMADetails rma =null;
			
			rma= dbRmaDetailsRepo.findByManualId(changeStatusRmaRequest.getRmaManualId());
			
			if(rma==null) throw new BadRequestException("No RMA found against this id");
			
			if(changeStatusRmaRequest.getRemarks()==null || changeStatusRmaRequest.getRemarks().equalsIgnoreCase(""))
				throw new BadRequestException("Remark is null");
			
//			List<java.util.Map<String,Object>> fileupload = dbUploadFiles.findByObjectNameAndObjectId("BtRMADetails",rma.getId().toString());
//			if(fileupload.size()==0)
//				throw new BadRequestException("Atleast one file upload of RMA");
			
			if(rma.getRmaStatus().toLowerCase().equalsIgnoreCase("open")) {
				rma.setRmaStatus("UNDER TRANSIT BY CUSTOMER");
				dbRmaDetailsRepo.updateDetails(rma, user);
				
				BtRmaChangeStatusLog changeStatuslog =new BtRmaChangeStatusLog();
				changeStatuslog.setRmaManualId(changeStatusRmaRequest.getRmaManualId());
				changeStatuslog.setRemarks(changeStatusRmaRequest.getRemarks());
				changeStatuslog.setCreatedBy(new EmployeeDetails(user.getUsername()));
				changeStatuslog.setCreatedDate(new Date());
				changeStatuslog.setMethod("Bulk Upload");
				
				dbRmaChangeStatusLogRepo.saveDetails(changeStatuslog, user);
				
			}else if(rma.getRmaStatus().toLowerCase().equalsIgnoreCase("REJECTED ON CUSTOMER REQUEST")) {
				throw new BadRequestException("you can't change the status");
			}else {
				throw new BadRequestException("In order to change status it should be open");
			}			
		}else {
			throw new BadRequestException("Only SCH can change the status");
		}
		return null;
	}

	@Override
	@Transactional(rollbackFor = { BadRequestException.class, UnauthoriseException.class,
			Exception.class }, propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED)
	public BtRmaResponse updateStatus(Map<String, Object> changeStatusRequest, MultipartFile file, LoginUser user) throws BadRequestException, UnauthoriseException {
		
		if(!dbProfile.hasObjectAccess(user.getUsername(), BtRmaChangeStatusLog.class, UserProfileRepository.CREATE)
				|| !dbProfile.hasObjectAccess(user.getUsername(), BtRmaChangeStatusLog.class, UserProfileRepository.EDIT))
			throw new BadRequestException("You are not authorized");
		
		Map<String, String> group = dbProfile.getAccountAndRoleGroup(user);
		String role=group.get("roleGroup");
		if(role.equals("SCH")) {
			
			BtRMADetails rma = dbRmaDetailsRepo.findById(Long.parseLong(changeStatusRequest.get("id").toString()));
			
			if(rma==null) throw new BadRequestException("No RMA found against this Id");
			
			if(changeStatusRequest.get("remarks")==null) throw new BadRequestException("Remarks is null");
			
//			List<java.util.Map<String,Object>> fileupload = dbUploadFiles.findByObjectNameAndObjectId("BtRMADetails",rma.getId().toString());
//			if(fileupload.size()==0)
//				throw new BadRequestException("Atleast one file upload of RMA");
			
			if(rma.getRmaStatus().toLowerCase().equalsIgnoreCase("open")) {
				rma.setRmaStatus("UNDER TRANSIT BY CUSTOMER");
				dbRmaDetailsRepo.updateDetails(rma, user);
				
				BtRmaChangeStatusLog changeStatusLog =new BtRmaChangeStatusLog();
				changeStatusLog.setRmaManualId(rma.getManualId());
				changeStatusLog.setRemarks(changeStatusRequest.get("remarks").toString());
				changeStatusLog.setCreatedBy(new EmployeeDetails(user.getUsername()));
				changeStatusLog.setCreatedDate(new Date());
				String fileName= file.getOriginalFilename();
				String files= fileStorageService.storeFile(file);
				String fileDownloadUrl= ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/downloadFile/").path(files).toUriString();
				changeStatusLog.setFileName(fileName);
				changeStatusLog.setPhysicalFileLocation(files);
				changeStatusLog.setMethod("Manual");
				
				UploadedFiles uploadedFiles = new UploadedFiles();
				uploadedFiles.setObjectName("BtRmaDetails");
				uploadedFiles.setObjectId(String.valueOf(rma.getId()));
				uploadedFiles.setFileName(files);
				uploadedFiles.setFileSize(file.getSize());
				uploadedFiles.setFileContentType(file.getContentType());
				uploadedFiles.setFileDownloadUri(fileDownloadUrl);
				uploadedFiles.setCreatedDate(new Date());
				uploadedFiles.setCreatedBy(new EmployeeDetails(user.getUsername()));
				
				dbRmaChangeStatusLogRepo.saveDetails(changeStatusLog,uploadedFiles, user);
			}else if(rma.getRmaStatus().toLowerCase().equalsIgnoreCase("REJECTED ON CUSTOMER REQUEST")) {
				throw new BadRequestException("you can't change the status");
			}else {
				throw new BadRequestException("In order to change status it should be open");
			}			
		}else {
			throw new BadRequestException("Only SCH can change the status");
		}

		return null;
	}

	

	

}
