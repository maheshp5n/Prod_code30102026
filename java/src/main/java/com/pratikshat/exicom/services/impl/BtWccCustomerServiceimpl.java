package com.pratikshat.exicom.services.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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

import com.google.auto.value.processor.escapevelocity.ParseException;
import com.pratikshat.exicom.entity.BtCase;
import com.pratikshat.exicom.entity.BtCustomerWcc;
import com.pratikshat.exicom.entity.BtCustomerWccCase;
//import com.pratikshat.exicom.entity.BtCustomerWccFilesUpload;
import com.pratikshat.exicom.entity.BtCustomerWccReject;
import com.pratikshat.exicom.entity.BtWccCustomStatus;
import com.pratikshat.exicom.entity.CaseDetails;
import com.pratikshat.exicom.entity.CaseReasonDetails;
import com.pratikshat.exicom.entity.CaseStageDetails;
import com.pratikshat.exicom.entity.CaseTypes;
import com.pratikshat.exicom.entity.CircleDetails;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.LegalEntity;
import com.pratikshat.exicom.entity.LoginUser;
 import com.pratikshat.exicom.entity.PODFileUpload;
import com.pratikshat.exicom.entity.UploadedFiles;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
 import com.pratikshat.exicom.model.request.PodRequest;
import com.pratikshat.exicom.repository.BtCaseRepository;
//import com.pratikshat.exicom.repository.BtCustomerWccFilesUploadRepository;
import com.pratikshat.exicom.repository.BtCustomerWccRepository;
import com.pratikshat.exicom.repository.BtCustomerWccRepositoryCustom;
import com.pratikshat.exicom.repository.BtSiteDetailsRepository;
import com.pratikshat.exicom.repository.BtWccCustomerRepository;
import com.pratikshat.exicom.repository.BusinessDomainRepository;
 import com.pratikshat.exicom.repository.PODFileUploadRepository;
import com.pratikshat.exicom.repository.UserProfileRepository;
//import com.pratikshat.exicom.repository.custom.BtCustomerWccFilesUploadRepositoryCustom;
import com.pratikshat.exicom.services.BtTobeApprovedService;
import com.pratikshat.exicom.services.BtWccCustomerService;
import com.pratikshat.exicom.services.FileStorageService;

@Service
public class BtWccCustomerServiceimpl implements BtWccCustomerService{
	
	@Autowired
	BtWccCustomerRepository wccRepo;
	
	@Autowired
	UserProfileRepository profileRepository;
	
	@Autowired
	BtCustomerWccRepository btCustomerWccRepository;
	
	@Autowired
	private FileStorageService fileStorageService;
	
//	@Autowired
//	BtCustomerWccFilesUploadRepository fileUploadrepo;

	@Autowired
	BtTobeApprovedService dbservice;
	
	@Autowired
	JdbcTemplate jdbc;
	
	@Autowired
	BtCaseRepository caserepo;
	
	SimpleDateFormat inputDate=new SimpleDateFormat("yyyy-MM-dd");
	
	 @Autowired
	 PODFileUploadRepository dbfileUpload;
	 
	 @PersistenceContext
	 EntityManager em;
	
	@Override
	public Map<String, Object> getSingle(Object id, LoginUser user) throws BadRequestException, UnauthoriseException {
		boolean view = (profileRepository.hasObjectAccess(user.getUsername(), BtCustomerWcc.class, UserProfileRepository.VIEW) || 
				profileRepository.hasObjectAccess(user.getUsername(), BtCustomerWcc.class, UserProfileRepository.VIEW_ALL));
		if(view){
			return btCustomerWccRepository.getWccCustomerSingle(Long.parseLong(id.toString()));
		}else {
			throw new UnauthoriseException("you are not authorise..");
		}
		
	}

	@Override
	public Page<Map<String, Object>> getPage(Pageable page, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		

		
		if(profileRepository.hasObjectAccess(user.getUsername(), BtCustomerWcc.class, UserProfileRepository.VIEW_ALL)){
			return btCustomerWccRepository.getWccCustomer(page);
		}
		if(profileRepository.hasObjectAccess(user.getUsername(), BtCustomerWcc.class, UserProfileRepository.VIEW))
				{
			List<String> ur= new ArrayList<String>();
			ur= profileRepository.getListOfUserAndChildByUser(user.getUsername());
			List<String> urlist=new ArrayList<String>();
			for(String u:ur)
			{
				urlist.add(u);
			}
			
			return btCustomerWccRepository.getWccCustomerByUser(urlist, page);
			
			
				}
		return Page.empty();
		
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
		return null;
	}

	@Override
	public List<Map<String, Object>> advanceSearch(Map<String, Object> searchKey, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		boolean view = (profileRepository.hasObjectAccess(user.getUsername(), BtWccCustomStatus.class, UserProfileRepository.VIEW) ||
				profileRepository.hasObjectAccess(user.getUsername(), BtCustomerWcc.class, UserProfileRepository.VIEW_ALL));
		
				if(view)
				{
				
					List<Object> value=new ArrayList<>();
					String query = "SELECT wcc.`id` AS id, wcc.`manual_id` AS manualId,wcc.`created_date` AS createdDate, wcc.`last_modified_date` AS modifiedDate,  \r\n"
							+ "			 			 				CONCAT(IFNULL(emp.first_name,'') ,' ', IFNULL(emp.middle_name, ' '),' ', IFNULL(emp.last_name,'') ) AS `createdBy`, emp.email_id AS `createdByEmailId`,  \r\n"
							+ "			 			 				CONCAT(IFNULL(lemp.first_name,'') ,' ', IFNULL(lemp.middle_name, ' '),' ', IFNULL(lemp.last_name,'') ) AS `lastModifiedBy`, lemp.email_id AS `lastModifiedByEmailId`,  \r\n"
							+ "			 			 						wcc.`customer` AS customer,wcc.`jms_no` AS JmsNo, wcc.submitted_by_sch_date, wcc.`circle_circle_name` AS circle,cr.`reason_id` AS reasonId,  \r\n"
							+ "			 			 						cr.`case_reason_details_name` AS reasonName ,ct.`case_type_id` AS caseTypeId, ct.`case_types_name` AS caseTypeName,   \r\n"
							+ "			 								wcc.`grn_number` AS `grnNumber`, wcc.`grn_date` AS `grnDate`, \r\n"
							+ "			 			 					wcc.`approval_status` AS `approvalStatus`,wcc.approval_date AS `approvalDate`,  \r\n"
							+ "			 			 					wcr.`wcc_customer_reject_list` AS `wccRejectReason`,wcr.`id` AS `wccRejectReasonId`,st.status AS `wccStatus`,st.id AS `wccStatusId`,wcc.`customer_po_number` AS `customerPoNumber`,wcc.`customer_po_date` AS `customerPoDate`,  \r\n"
							+ "			 			 					wcc.`podnumber` AS `podnumber`,wcc.`poddate` AS `poddate`, wcc.`exicom_invoice_number`, wcc.`exicom_invoice_date`,wcc.`other_rejection_reason`, \r\n"
							+ "			 								wcc.`total_charges` AS `totalCharges`, wcc.`total_customer_spare_charges` AS `customerBillingRate`,  \r\n"
							+ "			 								wcc.`total_site_visit_charge` AS `customerRate`,  \r\n"
							+ "			 			 					wcc.`sapinvoice_reference_number` \r\n"
							+ "			 			 						 FROM `bt_customer_wcc` wcc   \r\n"
							+ "			 			 						LEFT JOIN `case_reason_details` cr ON cr.`reason_id` =wcc.`case_reason_reason_id`  \r\n"
							+ "			 			 						LEFT JOIN `case_types` ct ON ct.`case_type_id`=wcc.`casetype_case_type_id`  \r\n"
							+ "			 			 						LEFT JOIN `circle_details` cd ON cd.`circle_name`=wcc.`circle_circle_name` \r\n"
							+ "			 			 						LEFT JOIN `bt_customer_wcc_reject` wcr ON wcr.id=wcc.reject_reason_id  \r\n"
							+ "			 			 						LEFT JOIN `bt_wcc_custom_status` st ON st.id=wcc.status_id  \r\n"
							+ "			 			 						 JOIN `employee_details` emp ON emp.`email_id`=wcc.`created_by_email_id`  \r\n"
							+ "			 			 				JOIN `employee_details` lemp ON lemp.`email_id`=wcc.`last_modified_by_email_id` \r\n"
									+ "				WHERE 1=1";
					
					StringBuilder queryBuilder=new StringBuilder();
					queryBuilder.append(query);
					
					if(searchKey.get("manualID") !=null && !searchKey.get("manualID").toString().equalsIgnoreCase("")) {
						queryBuilder.append(" and  wcc.`manual_id` like ?");
						value.add("%"+ searchKey.get("manualID")+ "%");
					}
					if(searchKey.get("jmsNumber") !=null && !searchKey.get("jmsNumber").toString().equalsIgnoreCase("")) {
						queryBuilder.append(" and cases.`jms_no` like ?");
						value.add("%"+ searchKey.get("jmsNumber")+ "%");
					}
					if(searchKey.get("circle") !=null && !searchKey.get("circle").toString().equalsIgnoreCase("")) {
						queryBuilder.append(" and site.`circle_circle_name` like ?");
						value.add("%"+ searchKey.get("circle")+ "%");
					}
					if(searchKey.get("serviceCenter")!=null && !searchKey.get("serviceCenter").toString().equalsIgnoreCase("")) {
						queryBuilder.append(" and le.`legal_entity_name` like ?");
						value.add("%"+ searchKey.get("serviceCenter") +"%");
					}
					if(searchKey.get("reasonName")!=null && !searchKey.get("reasonName").toString().equalsIgnoreCase("")) {
						queryBuilder.append(" and cr.`case_reason_details_name` like ?");
						value.add("%"+ searchKey.get("reasonName")+ "%");
					}
					if(searchKey.get("caseType")!=null && !searchKey.get("caseType").toString().equalsIgnoreCase("")) {
						queryBuilder.append(" and ct.`case_types_name` like ?");
						value.add("%"+ searchKey.get("caseType")+ "%");
					}
					if(searchKey.get("customer")!=null && !searchKey.get("customer").toString().equalsIgnoreCase("")) {
						queryBuilder.append(" and cases.`customer` like ?");
						value.add("%"+ searchKey.get("customer") + "%");
					}
					if(searchKey.get("status")!=null && !searchKey.get("status").toString().equalsIgnoreCase("")) {
						queryBuilder.append(" and wcc.`approval_status` like ?");
						value.add("%"+ searchKey.get("status") + "%");
					}
					return jdbc.queryForList(queryBuilder.toString(),value.toArray());
					
					}
			else{
					 throw new UnauthoriseException("You are not authorise..");
				}
					
				
	}

	@Override
	@Transactional(rollbackFor = { BadRequestException.class, UnauthoriseException.class,
			Exception.class }, propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED)
	public Map<String, Object> customerWccApproval(Map<String, Object> request, List<MultipartFile> files,
			LoginUser user) throws UnauthoriseException, BadRequestException, java.text.ParseException {
		// TODO Auto-generated method stub
		boolean create = profileRepository.hasObjectAccess(user.getUsername(), BtWccCustomStatus.class, UserProfileRepository.CREATE);
		if(!create){
			throw new UnauthoriseException("You are not authorise.");
		}
		BtCustomerWcc wcc = new BtCustomerWcc();
		 wcc=btCustomerWccRepository.findById(Long.parseLong(request.get("id").toString()));
		if(wcc==null)
			throw new BadRequestException("No WCC Customer Found Against This id");
		
		if(wcc.getStatus().getId()==5 || wcc.getStatus().getId()==6 )
			throw new BadRequestException("Customer WCC already approved or rejected by Commercial");
		
//		List<Integer> status=new ArrayList<>();
//		status.add(2);   //Submitted
//		status.add(3);   //Approved By RSH
//		status.add(5);	 //Approved By Commercial
//		
//			if(status.contains(wcc.getStatus().getId()))
		if(wcc.getStatus().getId()!=3)
				throw new BadRequestException("Please approve Customer WCC By RSH");
			
		
//		if(request.get("customerPo")==null)throw new BadRequestException("Customer PO number is null");
//		
		if(request.get("sapInvoiceNumber")==null)throw new BadRequestException("SAP Invoice Number is null");
		
		if(request.get("invoiceNumber")==null)throw new BadRequestException("Exicom invoice number is null");
		
		if(request.get("invoiceDate")==null)throw new BadRequestException("Exicom invoice date is null");
		
	
		
		if(files!=null)
		{
			System.out.println(files.size());
			if(files.size()>3)throw new BadRequestException("File can not be upload more than 3");
			
			if(!files.isEmpty()) {
				for(MultipartFile wccfile:files) {
//					BtCustomerWccFilesUpload uploadFile=new BtCustomerWccFilesUpload();
					
					String fileName=null;
					String downloadFileUri=null;
					
					fileName=fileStorageService.storeFile(wccfile);
					downloadFileUri= ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/downloadFile/").path(fileName).toUriString();
					
				
					
					try {
						UploadedFiles globalFile=new UploadedFiles();
						globalFile.setObjectId(String.valueOf(wcc.getId()));
						globalFile.setObjectName("BtCustomerWcc");
						globalFile.setFileName(fileName);
						globalFile.setFileSize(wccfile.getSize());
						globalFile.setFileContentType(wccfile.getContentType());
						globalFile.setFileDownloadUri(downloadFileUri);
						globalFile.setCreatedDate(new java.util.Date());
						globalFile.setCreatedBy(new EmployeeDetails(user.getUsername()));
						btCustomerWccRepository.wccfileUpload(globalFile, wccfile, user);
						
						PODFileUpload uploadFile= new PODFileUpload();
						uploadFile.setFileUrl(downloadFileUri);
						uploadFile.setOwner(new EmployeeDetails(user.getUsername()));
						uploadFile.setUploadFileName(fileName);
						uploadFile.setWcc(wcc);
						uploadFile.setAddBy(2);
						dbfileUpload.saveDetails(uploadFile, user);
					}catch(Exception ex) {
						throw new BadRequestException(ex.getMessage());
					}
					
					
					
				}
//				System.out.println("test");
				wcc.setSAPInvoiceReferenceNumber(request.get("sapInvoiceNumber").toString());
				wcc.setExicomInvoiceNumber(request.get("invoiceNumber").toString());
				wcc.setExicomInvoiceDate(inputDate.parse(request.get("invoiceDate").toString()));
				wcc.setStatus(new BtWccCustomStatus(5));
				wcc.setApprovalStatus("Approved");
				wcc.setApprovalDate(new java.util.Date());
//				wcc.setRejectReason(null);
//				wcc.setRemarks("");
				wcc.setIsWccApprove(true);
				wcc.setStatus(new com.pratikshat.exicom.entity.BtWccCustomStatus(5));
				
				

				List<BtCase> btCase=btCustomerWccRepository.getCaseByWccId(Long.parseLong(request.get("id").toString()));
				
				for(BtCase btcase:btCase)
				{
					btcase.setStage(new CaseStageDetails(4));
					em.persist(btcase);
					caserepo.updateDetails(btcase, user);

				}
				btCustomerWccRepository.updateDetails(wcc, user);

//				BtCase cs=new BtCase();
//				cs= caserepo.getCaseWihtId(wcc.getBtCase().getId());
//
//			
//				if(cs!=null) {
//				try {
//					cs.setPODate(inputDate.parse(request.get("customerPoDate").toString()));
//					cs.setInvoiceDate(inputDate.parse(request.get("invoiceDate").toString()));
//				} catch (java.text.ParseException e) {
//					e.printStackTrace();
//				}
//				cs.setPONumber(request.get("customerPo").toString());
//				cs.setInvoiceNumber(request.get("invoiceNumber").toString());
//				cs.setStage(new com.pratikshat.exicom.entity.CaseStageDetails(4));
//
//				caserepo.updateDetails(cs, user);
//			
//				}else {
//					throw new BadRequestException("No case found in this customer wcc");
//				}
			}
	
			
		}
		
		
		return null;
	}

	@Override
	@Transactional(rollbackFor = { BadRequestException.class, UnauthoriseException.class,
			Exception.class }, propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED)
	public Map<String, Object> rejectCustomerWCC(Map<String, Object> req, LoginUser user) throws BadRequestException, UnauthoriseException {

		boolean create = profileRepository.hasObjectAccess(user.getUsername(), BtCustomerWccReject.class, UserProfileRepository.CREATE);
		if(!create){
			throw new UnauthoriseException("You are not authorise.");
		}
		BtCustomerWcc wcc = new BtCustomerWcc();
		 wcc=btCustomerWccRepository.findById(Long.parseLong(req.get("id").toString()));
		if(wcc==null)
			throw new BadRequestException("No WCC Customer Found Against This id");
		
		if(wcc.getApprovalStatus()!=null && wcc.getApprovalStatus().equals("Rejected"))
			throw new BadRequestException("Customer WCC already Rejected");
		
		if(wcc.getStatus().getId()==5 || wcc.getStatus().getId()==6 )
			throw new BadRequestException("Customer WCC already approved or rejected by Commercial");
		
		if(wcc.getStatus().getId()!=3)
			throw new BadRequestException("Please approve Customer WCC By RSH"); 
		
		
		if(req.get("remarks")==null) throw new BadRequestException("Please fill remarks");
		
		if(req.get("rejectReasonId")==null) throw new BadRequestException("Please fill rejection reason");
		
		 //////to be approval
//		Map<String, Object> apr=null;
//		try {
//	     apr=dbservice.getToBeApprovedBasedOnIdAndObjectName(wcc.getId(),"BtCustomerWcc",user);
//		}catch(NoResultException ex){
//			throw new BadRequestException("Action has already taken");
//		}
//		
//	     if(apr!=null)
//	     {
//	    	 apr.put("action", "Rejected");
//	    	 apr.put("remarks",req.get("remarks"));
//	    	  dbservice.updateObject(apr, user);
//	    	 
//	     }
//		
		//////////////// 
		 
		 
		int reasonId=0;
		reasonId=btCustomerWccRepository.findByRejectionId(Integer.parseInt(req.get("rejectReasonId").toString()));
		if(reasonId>0) {
//			BtCase cs=new BtCase();
//			cs= caserepo.getCaseWihtId(wcc.getBtCase().getId());
//		
//			if(cs!=null) {
//		
//			cs.setPODUploaded(false);
//
//			caserepo.updateDetails(cs, user);
//		
//			}else {
//				throw new BadRequestException("No case found in this customer wcc");
//			}
		List<BtCase> btCase=btCustomerWccRepository.getCaseByWccId(Long.parseLong(req.get("id").toString()));
		
		for(int i=0;i<btCase.size();i++)
		{
			btCase.get(i).setCustomerWccId(null);
			btCase.get(i).setCustomerWccManualId(null);
			btCase.get(i).setPODUploaded(false);
			btCase.get(i).setStage(new CaseStageDetails(17));
//			caserepo.updateDetails(btCase, user);

		}
		wcc.setRemarks(req.get("remarks").toString());
		wcc.setApprovalStatus("Rejected");
		wcc.setApprovalDate(new java.util.Date());
		wcc.setIsWccApprove(true);
		wcc.setRejectReason(new BtCustomerWccReject(Integer.parseInt(req.get("rejectReasonId").toString())));
		wcc.setStatus(new com.pratikshat.exicom.entity.BtWccCustomStatus(6));
		
	
		
		
		 btCustomerWccRepository.updateDetails(wcc, user);
	
		return req;
		}else {
			throw new BadRequestException("Wrong reason name");
		}
		
		
	}

	@Override
	public List<BtCustomerWccReject> getReasonList() {
		// TODO Auto-generated method stub
		return btCustomerWccRepository.getReasonList();
	}

	 @Override
	 @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = { BadRequestException.class, UnauthoriseException.class })
	 public BtCustomerWcc postWccCustomer(PodRequest request, List<MultipartFile> files, LoginUser user) throws BadRequestException, UnauthoriseException {

		 if(request.getCustomerPoNumber()==null)
		 {
			throw new BadRequestException("Please Fill Customer PO Number");
		 }
		 if(request.getCustomerPoDate()==null)
		 {
			 throw new BadRequestException("Please Fill Customer PO Date");
		 }
		
	 	 List<Long> val=new ArrayList<>();
		
	 	for(long i: request.getCaseId())
	 	{
	 		val.add(i);
	 	}
		
	 	List<BtCase> cases= caserepo.getByIDforPod(val);
	 	if(cases.get(0).getCaseReason().getCaseReasonDetailsName().equalsIgnoreCase("BRC Repair"))
	 	{
	 		if(request.getGrnNumber()==null)
	 		{
	 			throw new BadRequestException("Please Fill GRN Number");
	 		}
	 		if(request.getGrnDate()==null)
	 		{
	 			throw new BadRequestException("Please Fill GRN Date");
	 		}
	 		if(request.getPodNumber()==null)
	 		{
	 			throw new BadRequestException("Please Fill POD Number");
	 		}
	 		if(request.getPodDate()==null)
	 		{
	 			throw new BadRequestException("Please Fill POD Date");
	 		}
	 	}
	 	EmployeeDetails emp= new EmployeeDetails(user.getUsername());
	 	BtCustomerWcc wcc=new BtCustomerWcc();
	 	wcc.setStatus(new BtWccCustomStatus(2));
	 	wcc.setSubmittedBySchDate(new Date());
	 	wcc.setApprovalStatus("Submitted");
	 	wcc.setApprovalDate(new Date());
	 	wcc.setCasetype(new CaseTypes(cases.get(0).getCaseType().getCaseTypeId()));
	 	wcc.setCaseReason(new CaseReasonDetails(cases.get(0).getCaseReason().getReasonId()));
	 	wcc.setServiceCenter(new LegalEntity(cases.get(0).getServiceCenter().getLegalEntityId()));
	 	wcc.setCircle(new CircleDetails(cases.get(0).getSite().getCircle().getCircleName()));
	 	wcc.setPODNumber(request.getPodNumber());
		try {
			if(request.getPodDate()!=null)
			{
				wcc.setPODDate(inputDate.parse(request.getPodDate()));
			}
			} catch (java.text.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 	wcc.setPodShippingMode(request.getPodShippingMode());
		wcc.setPodShippingExpectedDate(request.getPodShippingExpectedDate());
		wcc.setPodShippingToName(request.getPodShippingToName());
		wcc.setPodShippingTrackingNumber(request.getPodShippingTrackingNumber());
		wcc.setJmsNo(request.getJmsNo());
		wcc.setCustomer(request.getCustomer());
		wcc.setTotalCharges(0);
		wcc.setTotalCustomerSpareCharges(0);
		wcc.setTotalSiteVisitCharge(0);
		wcc.setIsWccApprove(false);
		wcc.setOwner(emp);
		wcc.setCustomerPoNumber(request.getCustomerPoNumber());
		try {
			wcc.setCustomerPoDate(inputDate.parse(request.getCustomerPoDate()));
		}catch(java.text.ParseException e)
		{
			e.printStackTrace();
		}
		wcc.setGrnNumber(request.getGrnNumber());
		try
		{
			if(request.getGrnDate()!=null)
			{
			wcc.setGrnDate(inputDate.parse(request.getGrnDate()));
			}
		}catch(java.text.ParseException e) {
			e.printStackTrace();
		}
		try
		{
			if(request.getServicePeriodFrom()!=null)
			{
			wcc.setServicePeriodFrom(inputDate.parse(request.getServicePeriodFrom()));
			}
		}catch(java.text.ParseException e) {
			e.printStackTrace();
		}
		try
		{
			if(request.getServicePeriodTo()!=null)
			{
			wcc.setServicePeriodTo(inputDate.parse(request.getServicePeriodTo()));
			}
		}catch(java.text.ParseException e) {
			e.printStackTrace();
		}
		
		
									
	 	 btCustomerWccRepository.saveDetails(wcc, user);
		 
	 	for(BtCase case1:cases)
	 	{
	 		if(case1.isPODUploaded())
	 		{
	 			throw new BadRequestException("POD Request already In Pending..");
	 		}
//	 			try {
//	 				case1.setPODDate(inputDate.parse(request.getPodDate()));
//	 			} catch (java.text.ParseException e) {
//	 				// TODO Auto-generated catch block
//	 				e.printStackTrace();
//	 			}
				
//	 			case1.setPODNumber(request.getPodNumber());
//	 			case1.setPodShippingMode(request.getPodShippingMode());
//	 			case1.setPodShippingExpectedDate(request.getPodShippingExpectedDate());
//	 			case1.setPodShippingToName(request.getPodShippingToName());
//	 			case1.setPodShippingTrackingNumber(request.getPodShippingTrackingNumber());
//	 			case1.setCustomer(request.getCustomer());
//	 			case1.setJmsNo(request.getJmsNo());
	 			case1.setPODUploaded(true);
	 			case1.setCustomerWccId(wcc.getId().toString());
	 			case1.setCustomerWccManualId(wcc.getManualId());
	 			case1.setStage(new CaseStageDetails(3));
	 			
	 			BtCustomerWccCase cs=new BtCustomerWccCase();
	 			cs.setBtcase(case1);
	 			cs.setCustomerSpareCharges(0);
	 			cs.setSiteVisitCharge(0);
	 			cs.setTotalCharges(0);
	 			cs.setWcc(wcc);
	 			List<Map<String,Object>> charge = null;
	 			try
	 			{
	 				charge= btCustomerWccRepository.getTotalSACharges(case1.getId());
	 			}catch(Exception ex)
	 			{
	 				System.out.println("NO Data Found");
	 			}
	 			if(charge!=null)
	 			{
	 				for(int i=0;i<charge.size();i++)
	 				{
	 				float sCharge=0;
	 				float cCharge=0;
	 				float tCharge=0;
	 				sCharge=sCharge+Float.parseFloat(charge.get(i).get("siteVisitCharge").toString());
	 				cCharge=cCharge+Float.parseFloat(charge.get(i).get("customerSpareCharges").toString());
	 				tCharge=tCharge+sCharge+cCharge;
		 			cs.setSiteVisitCharge(sCharge);
	 				cs.setCustomerSpareCharges(cCharge);
	 				cs.setTotalCharges(tCharge);	 				
	 				}
	 			}
	 			em.persist(cs);
	 			wcc.setTotalSiteVisitCharge(wcc.getTotalSiteVisitCharge()+cs.getSiteVisitCharge());
	 			wcc.setTotalCustomerSpareCharges(wcc.getTotalCustomerSpareCharges()+cs.getCustomerSpareCharges());
	 			wcc.setTotalCharges(wcc.getTotalCharges()+cs.getTotalCharges());
	 			
	 			btCustomerWccRepository.updateDetails(wcc, user);
	 			caserepo.updateDetails(case1, user);

	 	}
		
		for(MultipartFile file:files)
			{
				PODFileUpload fileUpload=new PODFileUpload();
				String fileDownloadUri=null;
				String fileName=null;
			
				if(files!=null)
				{
					if(!files.isEmpty())
					{
						fileName = fileStorageService.storeFile(file);
					
						fileDownloadUri= ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/downloadFile/").path(fileName).toUriString();
						fileUpload.setWcc(wcc);
						fileUpload.setFileUrl(fileDownloadUri);
						fileUpload.setOwner(emp);
						fileUpload.setUploadFileName(fileName);
						fileUpload.setAddBy(1);
						dbfileUpload.saveDetails(fileUpload, user);
					
						try {
							UploadedFiles uploadedFiles=new UploadedFiles();
							uploadedFiles.setObjectName("BtPODUpdate");
							uploadedFiles.setObjectId(String.valueOf(wcc.getId()));
							uploadedFiles.setFileName(fileName);
							uploadedFiles.setFileSize(file.getSize());
							uploadedFiles.setFileContentType(file.getContentType());
							uploadedFiles.setFileDownloadUri(fileDownloadUri);
							uploadedFiles.setCreatedDate(new Date());
							uploadedFiles.setCreatedBy(new EmployeeDetails(user.getUsername()));
							caserepo.fileUpload(uploadedFiles, file, user);
						
						}catch(Exception ex)
						{
						
						}
					
			
					}
				}
			
			}
		
	 	return btCustomerWccRepository.getById(wcc.getId());
	 }

	@Override
	public List<Map<String, Object>> getWccData(long wccid) {
		// TODO Auto-generated method stub
		return btCustomerWccRepository.getTemplateData(wccid);
	}

	@Override
	public List<Map<String, Object>> getAllCase(long id, LoginUser user) {
		// TODO Auto-generated method stub
		return btCustomerWccRepository.getAllwccCase(id);
	}
	
	



//	@Override
//	public Page<Map<String, Object>> getCaseListByStage(Pageable page, String filtername, LoginUser user)
//			throws BadRequestException, UnauthoriseException {
//
//		if (dbProfile.hasObjectAccess(user.getUsername(), BtCase.class, UserProfileRepository.VIEW_ALL)) {
//			if (filtername != null)
//			if (filtername != null && filtername.equalsIgnoreCase("toBeInvoice"))
//				return wccRepo.getCasesByStage("To Be Invoiced", page);
//			if (filtername != null && filtername.equalsIgnoreCase("docAwaitedInTransit"))
//				return wccRepo.getCasesByStage("Document in-Transit", page);
//
//			return wccRepo.getCasesByStage(filtername, page);
//		}
//		if (dbProfile.hasObjectAccess(user.getUsername(), BtCase.class, UserProfileRepository.VIEW)) {
//			List<String> emps = new ArrayList<String>();
//			List<String> usr = dbProfile.getListOfUserAndChildByUser(user.getUsername());
//			for (String u : usr) {
//				emps.add(u);
//			}
//			if (filtername != null)
//			if (filtername != null && filtername.equalsIgnoreCase("toBeInvoice"))
//				return wccRepo.getCasesByStage("To Be Invoiced", page);
//			if (filtername != null && filtername.equalsIgnoreCase("docAwaitedInTransit"))
//				return wccRepo.getCasesByStage("Document in-Transit", page);
////			return btCaseRepository.getCasesByStage(stage, emps, page);
//		}
//		return Page.empty();
//		
//	}

}
