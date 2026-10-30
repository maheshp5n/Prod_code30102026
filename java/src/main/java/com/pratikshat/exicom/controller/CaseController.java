package com.pratikshat.exicom.controller;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Color;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
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
import com.pratikshat.exicom.entity.Assets;
import com.pratikshat.exicom.entity.CaseDetails;
import com.pratikshat.exicom.entity.CaseExpectedProblem;
import com.pratikshat.exicom.entity.CaseFilesUpload;
import com.pratikshat.exicom.entity.CaseOriginDetails;
import com.pratikshat.exicom.entity.CasePriorityDetails;
import com.pratikshat.exicom.entity.CaseProblemReported;
import com.pratikshat.exicom.entity.CaseReasonDetails;
import com.pratikshat.exicom.entity.CaseRejectLog;
import com.pratikshat.exicom.entity.CaseStageDetails;
import com.pratikshat.exicom.entity.CaseStatusDetails;
import com.pratikshat.exicom.entity.CaseTypes;
import com.pratikshat.exicom.entity.ChangeCaseTypeLog;
import com.pratikshat.exicom.entity.ContactDetails;
import com.pratikshat.exicom.entity.CustomerInventory;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.LoginUserRoles;
import com.pratikshat.exicom.entity.PendingResponsibility;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.AccountCreate;
import com.pratikshat.exicom.model.request.CaseBulkRequest;
import com.pratikshat.exicom.model.request.CaseDocumentReceivedRequest;
import com.pratikshat.exicom.model.request.CaseInvoiceRequest;
import com.pratikshat.exicom.model.request.CaseProofOfDeliveryRequest;
import com.pratikshat.exicom.model.request.CaseRequest;
import com.pratikshat.exicom.model.request.CaseSearchRequest;
import com.pratikshat.exicom.model.request.ChangeCaseTypeRequest;
import com.pratikshat.exicom.model.request.Contacts;
import com.pratikshat.exicom.model.request.CustomerInventoryRequest;
import com.pratikshat.exicom.model.request.InvoiceUpdateRequest;
import com.pratikshat.exicom.model.request.PODUpdateRequest;
import com.pratikshat.exicom.model.request.RejectCaseRequest;
import com.pratikshat.exicom.model.response.CaseForList;
import com.pratikshat.exicom.model.response.CaseRejectLogResponse;
import com.pratikshat.exicom.model.response.CaseResponse;
import com.pratikshat.exicom.model.response.CaseStageResponse;
import com.pratikshat.exicom.model.response.CaseStatusResponse;
import com.pratikshat.exicom.model.response.SiteDetailsResponse;
import com.pratikshat.exicom.repository.AccountsOwnerDetailsRepository;
import com.pratikshat.exicom.repository.AssetsRepository;
import com.pratikshat.exicom.repository.CaseExpectedProblemRepository;
import com.pratikshat.exicom.repository.CaseFilesUploadRepository;
import com.pratikshat.exicom.repository.CaseRejectLogRepository;
import com.pratikshat.exicom.repository.ChangeCaseTypeConditionsRepository;
import com.pratikshat.exicom.repository.ContactDetailsRepository;
import com.pratikshat.exicom.repository.OppertunityRepository;
import com.pratikshat.exicom.repository.PendingResponsibilityRepository;
import com.pratikshat.exicom.repository.ServiceAppoinmentProductConsumedRepository;
import com.pratikshat.exicom.repository.ServiceAppointmentRepository;
import com.pratikshat.exicom.repository.SiteDetailsRepository;
import com.pratikshat.exicom.repository.UserProfileRepository;
import com.pratikshat.exicom.services.FileStorageService;
import com.pratikshat.exicom.utils.CaseDocumentReceivedParser;
import com.pratikshat.exicom.utils.CaseParser;
import com.pratikshat.exicom.utils.CaseRejectParser;
import com.pratikshat.exicom.utils.ChangeCaseTypeParser;
import com.pratikshat.exicom.utils.CustomerInventoryFileParser;
import com.pratikshat.exicom.utils.ExcelParser;
import com.pratikshat.exicom.utils.InvoiceUpdateParser;
import com.pratikshat.exicom.utils.PODParser;

@RestController
@RequestMapping("api/case")
public class CaseController {
	@Autowired
	com.pratikshat.exicom.repository.CaseOriginDetailsRepository dbCaseOrigin;
	@Autowired
	com.pratikshat.exicom.repository.CasePriorityDetailsRepository dbCasePriority;
	@Autowired
	com.pratikshat.exicom.repository.CaseProblemReportedRepository dbCaseProblem;
	@Autowired
	com.pratikshat.exicom.repository.CaseReasonDetailsRepository dbCaseReason;
	@Autowired
	com.pratikshat.exicom.repository.CaseStageDetailsRepository dbCaseStage;
	@Autowired
	com.pratikshat.exicom.repository.CaseStatusDetailsRepository dbCaseStatus;
	@Autowired
	com.pratikshat.exicom.repository.CaseTypesRepository dbCaseType;
	@Autowired
	com.pratikshat.exicom.repository.CaseDetailsRepository dbCase;
	@Autowired
	com.pratikshat.exicom.repository.CaseRecentVisitRepository dbRecent;
	@Autowired
	CaseExpectedProblemRepository dbCaseExpectedProblem;
	@Autowired
	ServiceAppointmentRepository dbServiceAppointment;
	@Autowired
	OppertunityRepository dbOppertunity;
	@Autowired
	ServiceAppoinmentProductConsumedRepository dbServiceAppoinmentProductConsumed;
	@Autowired
	com.pratikshat.exicom.repository.UserProfileRepository dbProfile;
	@Autowired
	AssetsRepository dbAssets;
	@Autowired
	SiteDetailsRepository dbSite;
	@Autowired
	AccountsOwnerDetailsRepository dbDev;
	
	@Autowired
	PendingResponsibilityRepository dbPendingResponsibility;
	
	@Autowired
	private ContactDetailsRepository dbContacts;
	
	@Autowired
	private CaseRejectLogRepository dbCaseRejectLog;
	
	@Autowired
	private ChangeCaseTypeConditionsRepository dbChangeCaseType;
	
	Logger logger = LoggerFactory.getLogger(CaseController.class);
	
	@Autowired
	private CaseFilesUploadRepository dbCaseFileUplaodRepo;

	@Autowired
	FileStorageService dbFileuploadService;
	
	@GetMapping("casereason/{casetype}")
	public java.util.List<com.pratikshat.exicom.model.response.CaseReasonForList> getCaseReason(
			@PathVariable("casetype") long casetype) {
		return dbCaseReason.getByCaseType(casetype);
	}

	@GetMapping("caseorigin")
	public java.util.List<com.pratikshat.exicom.entity.CaseOriginDetails> getCaseOriginDetails() {
		return dbCaseOrigin.findAll();
	}

	@GetMapping("casepriority")
	public java.util.List<com.pratikshat.exicom.entity.CasePriorityDetails> getCasePriority() {
		return dbCasePriority.findAll();
	}

	@GetMapping("casestage")
	public java.util.List<CaseStageResponse> getCaseStage(@RequestParam(name = "status") int status,
			@RequestParam(name = "division") int division) {
		List<Integer> list = new ArrayList<>();
		if (division == 1) {
			if (status == 1) {
				list.add(1);
			} else if (status == 2) {
				list.add(2);
			} else if (status == 3) {
				list.add(5);
				list.add(6);
			} else if (status == 4) {
				list.add(15);
			}

		} else if (division == 2) {

			if (status == 1) {
				list.add(7);
			} else if (status == 2) {
				list.add(8);
				list.add(18); //Document Awaited - Customer
			} else if (status == 3) {
				list.add(11);
				list.add(12);
			} else if (status == 4) {
				list.add(16);
			}
		} else if (division == 3) {

		}

		return dbCaseStage.getCaseStageByCaseStatusIdAndDivision(status, division, list);
	}

	@GetMapping("casestatus")
	public java.util.List<CaseStatusResponse> getStatus() {
		//return dbCaseStatus.getCaseStatusList();
		List<Integer> list = new ArrayList<>();
		list.add(1);	//New
		list.add(2);	//Closed
		list.add(3);	//In-Process
		return dbCaseStatus.getCaseStatusList(list);
	}

	@GetMapping("types/{verticalname}")
	public java.util.List<com.pratikshat.exicom.model.response.CaseTypesForList> getCaseTypes(
			@PathVariable("verticalname") int verticalname) {
		return dbCaseType.getByVertical(verticalname);
	}

	@GetMapping
	public Page<com.pratikshat.exicom.model.response.CaseResponse> getAll(Pageable page, @CurrentUser LoginUser user,@RequestParam(value="filtername",required=false)String filtername) {
		if (dbProfile.hasObjectAccess(user.getUsername(), "CaseDetails", "viewAllRights")) {
			
			if(filtername!=null&&filtername.equalsIgnoreCase("Document Awaited - Customer")) {
				return dbCase.getCasesByStage(filtername, page);
			}
			if(filtername!=null&&filtername.equalsIgnoreCase("All")) {
				return dbCase.getAllCase(page);
			}
			if(filtername!=null&&filtername.equalsIgnoreCase("related")) {
				return dbCase.getRecentVisit(user.getUsername(), page);
			}
			if(filtername!=null&&filtername.equalsIgnoreCase("closed")) {
				List<String> list = new ArrayList<>();
				
					list.add("Closed");
					list.add("Rejected");
				
				return dbCase.getCasesByStatus(list, page);
			}
			if(filtername!=null&&filtername.equalsIgnoreCase("open")) {
				List<String> list = new ArrayList<>();
					list.add("New");
					list.add("In-Process");
				
				return dbCase.getCasesByStatus(list, page);
			}
			if(filtername!=null&&filtername.equalsIgnoreCase("To Be Invoiced")) {
				return dbCase.getCasesByStage(filtername, page);
			}
			
			//COMMERCIAL DETAILS FILTER
			if(filtername!=null&&filtername.equalsIgnoreCase("Work_Order_Received"))
				return dbCase.getCasesByStage("Work Order Received", page);
			if(filtername!=null&&filtername.equalsIgnoreCase("Invoiced"))
				return dbCase.getCasesByStage(filtername, page);
			if(filtername!=null&&filtername.equalsIgnoreCase("QuoteSent"))
				return dbCase.getCasesByStage("Quote Sent", page);
			if(filtername!=null&&filtername.equalsIgnoreCase("toBeInvoice"))
				return dbCase.getCasesByStage("To Be Invoiced", page);
			if(filtername!=null&&filtername.equalsIgnoreCase("docAwaitedInTransit"))
				return dbCase.getCasesByStage("Document in-Transit", page);
			
				
			return dbCase.getAllCase(page);
		}
		if (dbProfile.hasObjectAccess(user.getUsername(), "CaseDetails", "viewRights")) {
			List<String> _us = new ArrayList<String>();
			_us = dbProfile.getListOfUserAndChildByUser(user.getUsername());
			List<com.pratikshat.exicom.entity.EmployeeDetails> userLis = new ArrayList<com.pratikshat.exicom.entity.EmployeeDetails>();
			for (String u : _us) {
				userLis.add(new com.pratikshat.exicom.entity.EmployeeDetails(u));
			}
			
			if(filtername!=null&&filtername.equalsIgnoreCase("Document Awaited - Customer")) {
				return dbCase.getCasesByStage(filtername,userLis,page);
			}
			if(filtername!=null&&filtername.equalsIgnoreCase("All")) {
				return dbCase.getAllCase(userLis, page);
			}
			if(filtername!=null&&filtername.equalsIgnoreCase("related")) {
				return dbCase.getRecentVisit(user.getUsername(), page);
			}
			if(filtername!=null&&filtername.equalsIgnoreCase("closed")) {
				List<String> list = new ArrayList<>();
				
					list.add("Closed");
					list.add("Rejected");
				
				return dbCase.getCasesByStatus(list,userLis ,page);
			}
			if(filtername!=null&&filtername.equalsIgnoreCase("open")) {
				List<String> list = new ArrayList<>();
					list.add("New");
					list.add("In-Process");
				
				return dbCase.getCasesByStatus(list,userLis, page);
			}
			if(filtername!=null&&filtername.equalsIgnoreCase("To Be Invoiced")) {
				return dbCase.getCasesByStage(filtername,userLis, page);
			}
			
			//COMMERCIAL DETAILS FILTER
			if(filtername!=null&&filtername.equalsIgnoreCase("Work_Order_Received"))
				return dbCase.getCasesByStage("Work Order Received",userLis, page);
			if(filtername!=null&&filtername.equalsIgnoreCase("Invoiced"))
				return dbCase.getCasesByStage(filtername,userLis, page);
			if(filtername!=null&&filtername.equalsIgnoreCase("QuoteSent"))
				return dbCase.getCasesByStage("Quote Sent",userLis, page);
			if(filtername!=null&&filtername.equalsIgnoreCase("toBeInvoice"))
				return dbCase.getCasesByStage("To Be Invoiced",userLis, page);
			if(filtername!=null&&filtername.equalsIgnoreCase("docAwaitedInTransit"))
				return dbCase.getCasesByStage("Document in-Transit",userLis, page);
			
			
			return dbCase.getAllCase(userLis, page);
		}
		return Page.empty();
	}

	@GetMapping("{caseid}")
	public com.pratikshat.exicom.model.response.CaseResponse getByCaseId(
			@CurrentUser com.pratikshat.exicom.entity.LoginUser user, @PathVariable("caseid") long caseid) {
		com.pratikshat.exicom.model.response.CaseResponse _case = dbCase.getByCaseId(caseid);
		if (_case != null) {
			_case.setProblemReported(dbCaseProblem.getByCaseId(_case.getCaseId()));
		}
		try {
			com.pratikshat.exicom.entity.CaseRecentVisit _recent = dbRecent.findByCaseIdAndVisitorId(caseid,
					user.getUsername());
			if (_recent == null)
				_recent = new com.pratikshat.exicom.entity.CaseRecentVisit(caseid, user.getUsername());
			else
				_recent.setVisitedDate(new java.util.Date());
			dbRecent.save(_recent);
			dbRecent.flush();
		} catch (Exception e) {
		}
		return _case;
	}

	@PostMapping
	public com.pratikshat.exicom.model.response.CaseResponse createCase(
			@Valid @RequestBody com.pratikshat.exicom.model.request.CaseRequest caseDetails,
			@CurrentUser com.pratikshat.exicom.entity.LoginUser user, HttpServletResponse httpResponse)
			throws java.io.IOException {

		if (!dbProfile.hasObjectAccess(user.getUsername(), "CaseDetails", "createRights")) {
			httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}

		if (dbCase.countByCaseId(caseDetails.getCaseId()) > 0) {
			httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Caseid already exists");
			return null;
		}
//		if (dbCase.getCountByAssetsAndNotCaseStatus(caseDetails.getAssetsSerialNumber(), "Closed") > 0) {
//			httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST,
//					"Case for the assets already registered in open mode");
//			return null;
//		}
		
		if(caseDetails.getCaseProblemReported()==null || caseDetails.getCaseProblemReported().isEmpty()) {
			httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST,
					"Please choose expected problem");
			return null;
		}

		try {
			CaseRequest req = dbCase.saveCaseDetails(caseDetails, user);
			com.pratikshat.exicom.model.response.CaseResponse _case = dbCase.getByCaseId(req.getCaseId());
			if (_case != null) {
				_case.setProblemReported(dbCaseProblem.getByCaseId(_case.getCaseId()));
			}
			return _case;
		} catch (BadRequestException ex) {
			ex.printStackTrace();
			httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
			return null;
		} catch (UnauthoriseException ex) {
			ex.printStackTrace();
			httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, ex.getMessage());
			return null;
		} catch (Exception ex) {
			ex.printStackTrace();
			httpResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
			return null;
		}
	}

	@GetMapping("recentvisit")
	public Page<com.pratikshat.exicom.model.response.CaseResponse> getReceintVisist(@CurrentUser LoginUser user,
			Pageable page) {
		return dbCase.getRecentVisit(user.getUsername(), page);
	}

	@GetMapping("caseexpectedproblem/{businessDomainId}")
	public java.util.List<CaseExpectedProblem> getCaseExpectedProblem(
			@PathVariable("businessDomainId") int businessDomainId) {
		return dbCaseExpectedProblem.findByBusinessDomain_BusinessDomainId(businessDomainId);
	}

	@GetMapping("caseforlist")
	public List<CaseForList> caseForList() {
		List<String> list = new ArrayList<>();
		list.add("Closed");
		list.add("Rejected");
		return dbCase.getCaseForList(list);
	}

	@PutMapping
	public CaseResponse updateCase(@Valid @RequestBody com.pratikshat.exicom.model.request.CaseRequest caseDetails,
			@CurrentUser com.pratikshat.exicom.entity.LoginUser user, HttpServletResponse httpResponse)
			throws IOException {

		try {
			
			if(caseDetails.getCaseProblemReported()==null || caseDetails.getCaseProblemReported().isEmpty()) {
				httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST,
						"Please choose expected problem");
				return null;
			}
			
			CaseDetails _caseDetails = dbCase.findByCaseId(caseDetails.getCaseId());
			if (_caseDetails == null) {
				httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Case does not exist");
				return null;
			} else {
				if (_caseDetails.getStatus().getCaseStatusName().equalsIgnoreCase("Closed")) {
					httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST,
							"Case Closed You can not update the case");
					return null;
				} else {
					if (dbProfile.hasObjectAccess(user.getUsername(), "CaseDetails", "editRights")) {

						CaseRequest _caseRequest = dbCase.updateCase(caseDetails, user);

						com.pratikshat.exicom.model.response.CaseResponse _case = dbCase
								.getByCaseId(_caseRequest.getCaseId());
						if (_case != null) {
							_case.setProblemReported(dbCaseProblem.getByCaseId(_case.getCaseId()));
						}
						return _case;

					} else {
						httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorized");
						return null;
					}

				}
			}

		} catch (BadRequestException ex) {
			ex.printStackTrace();
			httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
			return null;
		} catch (UnauthoriseException ex) {
			ex.printStackTrace();
			httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, ex.getMessage());
			return null;
		} catch (Exception ex) {
			ex.printStackTrace();
			httpResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
			return null;
		}

	}

	@GetMapping("releted/{caseid}/serviceappointment")
	Page<com.pratikshat.exicom.model.response.ServiceAppointmentResponse> getServiceAppointmentByCaseId(
			@PathVariable("caseid") long caseid, Pageable page) {
		return dbServiceAppointment.getServiceAppointmentByCaseId(caseid, page);
	}

	@GetMapping("releted/{caseid}/productconsumed")
	Page<com.pratikshat.exicom.model.response.ServiceAppointmentProductConsumedResponse> getProductConsumed(
			@PathVariable("caseid") long caseid, Pageable page) {
		return dbServiceAppoinmentProductConsumed.getProductConsumedByCaseId(caseid, page);
	}

	@GetMapping("releted/{caseid}/opertunity")
	Page<com.pratikshat.exicom.model.response.OppertunityResponse> getCaseOpertunity(
			@PathVariable("caseid") long caseid, Pageable page) {
		return dbOppertunity.getOppertunityByCaseId(caseid, page);
	}

	@GetMapping("bystatus")
	public Page<com.pratikshat.exicom.model.response.CaseResponse> getCaseListByStatus(
			@RequestParam("status") String status, Pageable page) {
		List<String> list = new ArrayList<>();
		if (status.equalsIgnoreCase("Closed")) {
			list.add("Closed");
			list.add("Rejected");
		} else if (status.equalsIgnoreCase("Open")) {
			list.add("New");
			list.add("In-Process");
		}
		return dbCase.getCasesByStatus(list, page);
	}

	@GetMapping("bystage")
	public Page<com.pratikshat.exicom.model.response.CaseResponse> getCaseListByStage(
			@RequestParam(value="stage",required=false) String stage, Pageable page,@RequestParam(value="filtername",required=false)String filtername,@CurrentUser LoginUser user) {
		if(dbProfile.hasObjectAccess(user.getUsername(), CaseDetails.class, UserProfileRepository.VIEW_ALL)) {
			if(filtername!=null)
				stage=filtername;
			if(stage!=null&&stage.equalsIgnoreCase("Work_Order_Received"))
				return dbCase.getCasesByStage("Work_Order_Received", page);
			if(stage!=null&&stage.equalsIgnoreCase("QuoteSent"))
				return dbCase.getCasesByStage("Quote Sent", page);
			if(stage!=null&&stage.equalsIgnoreCase("toBeInvoice"))
				return dbCase.getCasesByStage("To Be Invoiced", page);
			if(stage!=null&&stage.equalsIgnoreCase("docAwaitedInTransit"))
				return dbCase.getCasesByStage("Document in-Transit", page);
			
			return dbCase.getCasesByStage(stage, page);	
		}
		 if(dbProfile.hasObjectAccess(user.getUsername(), CaseDetails.class, UserProfileRepository.VIEW)) {
			List<EmployeeDetails> emps=new ArrayList<EmployeeDetails>();
			List<String> usr=dbProfile.getListOfUserAndChildByUser(user.getUsername());
			for(String u:usr) {
			 emps.add(new EmployeeDetails(u));
			}
			if(filtername!=null)
				stage=filtername;
			 
			if(stage!=null&&stage.equalsIgnoreCase("Work_Order_Received"))
					return dbCase.getCasesByStage("Work_Order_Received", page);
			if(stage!=null&&stage.equalsIgnoreCase("QuoteSent"))
					return dbCase.getCasesByStage("Quote Sent", page);
			if(stage!=null&&stage.equalsIgnoreCase("toBeInvoice"))
				return dbCase.getCasesByStage("To Be Invoiced", page);
			if(stage!=null&&stage.equalsIgnoreCase("docAwaitedInTransit"))
				return dbCase.getCasesByStage("Document in-Transit", page);
			return dbCase.getCasesByStage(stage,emps,page);
		 }
		return Page.empty();
		
	}

	@PutMapping("invoice")
	public com.pratikshat.exicom.model.response.CaseResponse updateCaseInvoice(
			@Valid @RequestBody CaseInvoiceRequest caseInvoiceRequest,
			@CurrentUser com.pratikshat.exicom.entity.LoginUser user, HttpServletResponse httpResponse)
			throws java.io.IOException {
		try {
			CaseDetails caseDetails = dbCase.findByCaseId(caseInvoiceRequest.getCaseId());

			if (caseDetails != null) {

				List<Integer> list = new ArrayList<>();
				list.add(21); // Document in-Transit - Battery
				list.add(22); // Document in-Transit - DCT
				list.add(3);  // To Be Invoiced - Battery
				list.add(9); // To Be Invoiced - DCT
				list.add(4); // Invoiced - Battery
				list.add(10); // Invoiced - DCT

				if (!list.contains(caseDetails.getStage().getId())) {
					httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "You can't update this record");
					return null;
				}

				if (caseInvoiceRequest.getPoDate() != null && 
						caseInvoiceRequest.getPoNumber() != null && 
						!caseInvoiceRequest.getPoNumber().equalsIgnoreCase("")) {
					caseDetails.setPODate(caseInvoiceRequest.getPoDate());
					caseDetails.setPONumber(caseInvoiceRequest.getPoNumber());
					
				}

				if (caseInvoiceRequest.getInvoiceNumber() != null && 
						!caseInvoiceRequest.getInvoiceNumber().equalsIgnoreCase("") && 
						caseInvoiceRequest.getInvoiceDate() != null) {
					caseDetails.setInvoiceNumber(caseInvoiceRequest.getInvoiceNumber());
					caseDetails.setInvoiceDate(caseInvoiceRequest.getInvoiceDate());
					
					if(caseDetails.getBusinessDomain().getBusinessDomainId()==1) { // Battery
						caseDetails.setStage(new CaseStageDetails(4)); // Invoiced
					}else if(caseDetails.getBusinessDomain().getBusinessDomainId()==2) { // DCT
						caseDetails.setStage(new CaseStageDetails(10)); // Invoiced	
					}else if(caseDetails.getBusinessDomain().getBusinessDomainId()==3) { // EV
						caseDetails.setStage(null); // Invoiced	
					}
				}

				if (caseInvoiceRequest.getVendorInvoiceNumber() != null && 
						!caseInvoiceRequest.getVendorInvoiceNumber().equalsIgnoreCase("") && 
						caseInvoiceRequest.getVendorInvoiceDate() != null) {
					caseDetails.setVendorInvoiceNumber(caseInvoiceRequest.getVendorInvoiceNumber());
					caseDetails.setVendorInvoiceDate(caseInvoiceRequest.getVendorInvoiceDate());
				}

				caseDetails.setModifiedBy(new EmployeeDetails(user.getUsername()));
				caseDetails.setModifiedDate(new Date());

				dbCase.save(caseDetails);

				com.pratikshat.exicom.model.response.CaseResponse _case = dbCase
						.getByCaseId(caseInvoiceRequest.getCaseId());
				if (_case != null) {
					_case.setProblemReported(dbCaseProblem.getByCaseId(_case.getCaseId()));
				}
				return _case;

			} else {
				httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Case does not exist");
				return null;
			}
		} catch (Exception ex) {
			httpResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
			return null;
		}
	}

	@PutMapping("proofofdelivery")
	public com.pratikshat.exicom.model.response.CaseResponse updateCaseProofOfDelivey(
			@Valid @RequestBody CaseProofOfDeliveryRequest caseProofOfDeliveryRequest,
			@CurrentUser com.pratikshat.exicom.entity.LoginUser user, HttpServletResponse httpResponse)
			throws java.io.IOException {
		try {

			if (dbProfile.hasObjectAccess(user.getUsername(), "CaseDetails", "editRights")) {
				
				CaseProofOfDeliveryRequest request = dbCase.updateProofOfDelivery(caseProofOfDeliveryRequest, user);
				
				com.pratikshat.exicom.model.response.CaseResponse _case = dbCase
						.getByCaseId(caseProofOfDeliveryRequest.getCaseId());
				if (_case != null) {
					_case.setProblemReported(dbCaseProblem.getByCaseId(_case.getCaseId()));
				}
				
				return _case;
			} else {
				httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorized");
				return null;
			}
		} catch (Exception e) {
			httpResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			return null;
		}
	}

	@GetMapping("/templateExcel")
	public ResponseEntity<ByteArrayResource> GetExcel(HttpServletResponse httpResponse) throws IOException {
		HttpHeaders header = new HttpHeaders();
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("case");
		Object[][] bookData = { { "Asset Serial Number", "Site Id", "Caller Phone No", "Case Type", "Case Reason",
			"Status", "Stage", "Case Origin", "Priority",  "Pending Responsibility",
			"Problem Reported (seprated by ;)", "Number Of SMR", "Other Problem Reported", "Remark"},};

		int rowCount = 0;

		CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setColor(Font.COLOR_RED);
        style.setFont(font);
		
		
		for (Object[] aBook : bookData) {
			Row row = sheet.createRow(rowCount);

			int columnCount = 0;

			for (Object field : aBook) {
				Cell cell = row.createCell(columnCount++);
				if (field instanceof String) {
					cell.setCellValue((String) field);
					if(columnCount!=12&&columnCount!=13&&columnCount!=14) {
						cell.setCellStyle(style);
					}
				} else if (field instanceof Integer) {
					cell.setCellValue((Integer) field);
					if(columnCount!=12&&columnCount!=13&&columnCount!=14) {
						cell.setCellStyle(style);
					}
				}
			}

		}

		header.setContentType(new MediaType("application", "force-download"));
		header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + "case_template.xlsx");
		httpResponse.setContentType("application/xlsx");
		ByteArrayOutputStream bytest = new ByteArrayOutputStream();
		workbook.write(bytest);
		return new ResponseEntity<>(new ByteArrayResource(bytest.toByteArray()), header, HttpStatus.OK);
	}
	@PostMapping("/import")
	public ResponseEntity<ByteArrayResource> mapReapExcelDatatoDB(@RequestParam("file") MultipartFile reapExcelDataFile,
	       @CurrentUser com.pratikshat.exicom.entity.LoginUser user, HttpServletResponse httpResponse)
	       throws IOException {
	    // System.out.println(reapExcelDataFile.getOriginalFilename());
	    String fileName = reapExcelDataFile.getOriginalFilename();
	    
	    
	    if (fileName.substring(fileName.lastIndexOf('.') + 1).equals("xlsx")) {
	       HttpHeaders header = new HttpHeaders();
	       

	       if (!dbProfile.hasObjectAccess(user.getUsername(), "CaseDetails", "createRights")) {
	          httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
	          return null;
	       }
	       
	       
	       List<Map<String,Object>> outData=new ArrayList<Map<String,Object>>();
	       ByteArrayOutputStream st=null;
	       try {
	          List<CaseBulkRequest> requests=new CaseParser().parseIn(reapExcelDataFile);
	          for(CaseBulkRequest re:requests) {
	             
	             Map<String,Object> d=new HashMap<String,Object>();
	             
	             
	             d.put("Asset Serial Number", re.getAssetSerialNumber());
	             d.put("Site Id", re.getSiteId());
	             d.put("Caller Phone No", re.getCallerPhoneNo());
	             d.put("Case Type", re.getCaseTypeName());
	             d.put("Case Reason", re.getCaseReasonName());
	             d.put("Status", re.getStatusName());
	             d.put("Stage", re.getStageName());
	             d.put("Case Origin", re.getCaseOriginName());
	             d.put("Priority", re.getPriorityName());
	             d.put("Pending Responsibility", re.getPendingResponsibility());
	             d.put("Problem Reported (seprated by ;)", re.getProblemsReported());
	             d.put("Number Of SMR", re.getNumberOfSMR());
	             d.put("Other Problem Reported", re.getOtherProblemReported());
	             d.put("Remark", re.getRemark());
	             
	             try {
	                
	                CaseRequest req = new CaseRequest();
	                
	                if (re.getAssetSerialNumber()==null || re.getAssetSerialNumber().trim().equals("")) {
	                   throw new BadRequestException("Asset is null");
	                }
	                
	                req.setAssetsSerialNumber(re.getAssetSerialNumber().trim());
	                
	                if(re.getSiteId()==null ||re.getSiteId().trim().equalsIgnoreCase("")) {
	                   throw new BadRequestException("Site Id is null");
	                }
	                
	                SiteDetailsResponse siteResponse = dbSite.getByManualSiteId(re.getSiteId().trim());
	                if(siteResponse==null) {
	                   throw new BadRequestException("Site does not exist");
	                }
	                
	                req.setSiteId(siteResponse.getSiteId());
	                req.setServiceCenterId(siteResponse.getServiceCenterId());
	                req.setServiceResourceId(siteResponse.getServiceResourceId());
	                req.setAssignByAssignmentRule(true);
	                
	                EmployeeDetails emp = dbDev.findByEmailId(user.getUsername());
	                
	                req.setCaseRecordTypeId(emp.getEmptype().getBusinessDomainId());
	                
	                if(re.getCallerPhoneNo()==null || re.getCallerPhoneNo().trim().equalsIgnoreCase("")) {
	                   throw new BadRequestException("Caller Phone No. is null");
	                }
	                
	                try {
	                   ContactDetails conntact =dbContacts.getContactsByPhoneNoAndAccount(re.getCallerPhoneNo().trim(), siteResponse.getAccountSapNumber());  
	                   if(conntact==null) {
	                      throw new BadRequestException("Caller does not exist");
	                   }
	                   req.setContactPersonId(conntact.getContactId());
	                }catch(NonUniqueResultException ex) {
	                   throw new BadRequestException("Multiple contact with the same contact no.");
	                }catch(Exception ex) {
	                   throw new BadRequestException(ex.getMessage());
	                }
	                
	                if( re.getCaseTypeName()==null || re.getCaseTypeName().trim().equalsIgnoreCase("")) {
	                   throw new BadRequestException("Case type is null");
	                }


	                CaseTypes caseType = dbCaseType.getByCaseTypesNameAndVertical(re.getCaseTypeName().trim(), emp.getEmptype().getBusinessDomainId());
	                if(caseType==null) {
	                   throw new BadRequestException("Case type does not exist");
	                }

	                List<String> acceptableCaseTypeNames = Arrays.asList("Under Warranty", "Under AMC", "Without Warranty");
	                Assets assets = dbAssets.findBySerialNumber(re.getAssetSerialNumber().trim());

	                // Ensure caseType is acceptable
	                if (!acceptableCaseTypeNames.contains(caseType.getCaseTypesName())) {
	                   throw new BadRequestException(caseType.getCaseTypesName() + " caseType is not allowed");
	                }

	                Date currentDate = Calendar.getInstance().getTime();
	                // ssy start
	                Date amcExpiryDate = assets.getAmcExpiryDate();
	                Date warrantyExpiryDate = assets.getWarrantyExpiryDate();
	                //ssy end
	                
//	                boolean isAmcExpired = assets.getAmcExpiryDate().getTime() < currentDate.getTime();
//	                boolean isWarrantyExpired = assets.getWarrantyExpiryDate().getTime() < currentDate.getTime();
	                //ssy st
	                boolean isAmcExpired = amcExpiryDate == null || amcExpiryDate.getTime() < currentDate.getTime();
	                boolean isWarrantyExpired = warrantyExpiryDate == null || warrantyExpiryDate.getTime() < currentDate.getTime();


	               


	                if (!isAmcExpired && !isWarrantyExpired) {
	                   // Both AMC and Warranty are active
	                   if (!caseType.getCaseTypesName().equals("Under Warranty")) {
	                      throw new BadRequestException(caseType.getCaseTypesName() + " is not allowed for Under Warranty Asset");
	                   }
	                } else if (!isWarrantyExpired) {
	                   // Only Warranty is active
	                   if (!caseType.getCaseTypesName().equals("Under Warranty")) {
	                      throw new BadRequestException(caseType.getCaseTypesName() + " is not allowed for Under Warranty Asset");
	                   }
	                } else if (!isAmcExpired) {
	                   // Only AMC is active
	                   if (!caseType.getCaseTypesName().equals("Under AMC")) {
	                      throw new BadRequestException(caseType.getCaseTypesName() + " is not allowed for Under AMC Asset");
	                   }
	                } else if(isAmcExpired && isWarrantyExpired) {
	                	// when both expired 
	                	if (!caseType.getCaseTypesName().equals("Without Warranty")) {
		                      throw new BadRequestException(caseType.getCaseTypesName() + " is not allowed for Without Warranty Asset");
		                   }
	                }else if(isAmcExpired) {
	                	//when amc expired
	                	if (!caseType.getCaseTypesName().equals("Without Warranty")) {
		                      throw new BadRequestException(caseType.getCaseTypesName() + " is not allowed for Without Warranty Asset");
		                   }
	                }
	                req.setCaseTypeId(caseType.getCaseTypeId());
	                
	                if(re.getCaseReasonName()==null || re.getCaseReasonName().trim().equalsIgnoreCase("")) {
	                   throw new BadRequestException("Case reason is null");
	                }
	                
	                CaseReasonDetails caseReason= dbCaseReason.getByCaseTypeAndCaseReasonName(caseType.getCaseTypeId(), re.getCaseReasonName().trim(),emp.getEmptype().getBusinessDomainId());
	                if(caseReason==null) {
	                   throw new BadRequestException("Case reason does not exist");
	                }
	                
	                req.setCaseReasonId(caseReason.getReasonId());
	                
	                
	                if(re.getStatusName()==null || re.getStatusName().trim().equalsIgnoreCase("")) {
	                   throw new BadRequestException("Case status is null");
	                }
	                
	                CaseStatusDetails caseStatus = dbCaseStatus.getCaseStatusByCaseStatusName(re.getStatusName().trim());
	                if(caseStatus==null) {
	                   throw new BadRequestException("Case status does not exist");
	                }
	                
	                req.setCaseStatusId(caseStatus.getId());
	                
	                
	                if(re.getStageName()==null || re.getStageName().trim().equalsIgnoreCase("")) {
	                   throw new BadRequestException("Case stage is null");
	                }
	                
	                CaseStageDetails caseStage = dbCaseStage.getCaseStageByCaseStatusIdAndStageName(caseStatus.getId(), re.getStageName().trim(),emp.getEmptype().getBusinessDomainId());
	                if(caseStage==null) {
	                   throw new BadRequestException("Case stage does not exist");
	                }
	                
	                req.setCaseStageId(caseStage.getId());
	                
	                if(re.getCaseOriginName()==null || re.getCaseOriginName().trim().equalsIgnoreCase("")) {
	                   throw new BadRequestException("Case origin is null");
	                }
	                
	                CaseOriginDetails caseOrigin = dbCaseOrigin.getCaseOriginByOriginName(re.getCaseOriginName().trim());
	                if(caseOrigin==null) {
	                   throw new BadRequestException("Case origin does not exist");
	                }
	                
	                req.setCaseOriginId(caseOrigin.getCaseOriginId());
	                
	                if(re.getPriorityName()==null || re.getPriorityName().trim().equalsIgnoreCase("")) {
	                   throw new BadRequestException("Case priority is null");
	                }
	                
	                CasePriorityDetails casePriority = dbCasePriority.getCasePriorityByPriorityName(re.getPriorityName().trim());
	                if(casePriority==null) {
	                   throw new BadRequestException("Case priority does not exist");
	                }
	                
	                req.setCasePriorityId(casePriority.getCasePriorityId());
	                
	                if(re.getPendingResponsibility()==null ||
	                      re.getPendingResponsibility().trim().equalsIgnoreCase("")) {
	                   throw new BadRequestException("Pending responsibility is null");
	                }
	                
	                PendingResponsibility pendingResponsibility = dbPendingResponsibility.getPendingResponsibilityByName(re.getPendingResponsibility().trim());
	                if(pendingResponsibility==null) {
	                   throw new BadRequestException("Pending responsibility does not exist");
	                }
	                
	                req.setPendingResponsibility(pendingResponsibility.getPendingResponsibilityId());
	                
	                if(re.getNumberOfSMR()!=null && !re.getNumberOfSMR().trim().equalsIgnoreCase("")) {
	                   try {
	                      int num = Integer.parseInt(re.getNumberOfSMR().trim());
	                      req.setNoOfSmr(re.getNumberOfSMR().trim());
	                   }catch(NumberFormatException ex){
	                      throw new BadRequestException("No. of SMR is not valid number");
	                   }
	                   
	                }
	                
	                if(re.getOtherProblemReported()!=null && !re.getOtherProblemReported().trim().equalsIgnoreCase("")) {
	                   req.setOtherProblemReported(re.getOtherProblemReported().trim());
	                }
	                
	                if(re.getRemark()!=null && !re.getRemark().trim().equalsIgnoreCase("")) {
	                   req.setRemarks(re.getRemark().trim());
	                }
	                
	                
	                req.setRJILTT01No(null);
	                req.setMissingAssets(null);
	                req.setInputFromEng(null);
	                
	                
	                if(re.getProblemsReported()==null || re.getProblemsReported().trim().equalsIgnoreCase("")) {
	                   throw new BadRequestException("Problem reported is null");
	                }
	                CaseProblemReported crp = new CaseProblemReported();
	                String[] list2 = re.getProblemsReported().trim().split(";");
	                   Set<com.pratikshat.exicom.entity.CaseProblemReported> caseProblemReported = new HashSet<com.pratikshat.exicom.entity.CaseProblemReported>();
	                   for (String li2 : list2) {
	                      CaseProblemReported cr = new CaseProblemReported();
	                      if (li2.length() != 0) {
	                         cr.setProblemName(li2.trim());
	                      }
	                      caseProblemReported.add(cr);
	                   }
	                   req.setCaseProblemReported(caseProblemReported);

	                   CaseRequest request = dbCase.saveCaseDetails(req, user);
	                   
	                   com.pratikshat.exicom.model.response.CaseResponse caseResponse = dbCase.getByCaseId(request.getCaseId());
	                d.put("STATUS", "Success");
	                d.put("Case Number", caseResponse.getCaseManualId());
	             }catch(Exception ex) {
	                logger.error("error in case bulk upload {}",ex);
	                d.put("STATUS", "Error "+ex.getMessage());
	                d.put("Case Number", "");
	             }
	             outData.add(d);
	          }
	          
	       }catch(Exception ex) {
	          logger.error("error in case bulk upload {}",ex);
	          httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
	          return null;
	       }
	       try {
	          st=new ExcelParser().genrateFile(outData);
	       }catch(Exception ex) {
	          logger.error("error in case bulk upload {}",ex);
	          httpResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,ex.getMessage());
	          return null;
	       }
	       
	       httpResponse.addHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=CaseResult.xlsx");
	       httpResponse.setContentType("application/vnd.ms-excel");
	       httpResponse.getOutputStream().write(st.toByteArray());
	       httpResponse.getOutputStream().flush();
	       httpResponse.getOutputStream().close();
	       return null;
	    } else {
	       httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "file is not excel");
	       return null;
	    }
	}
	//Old code without casetype validation below
//	@PostMapping("/import")
//	public ResponseEntity<ByteArrayResource> mapReapExcelDatatoDB(@RequestParam("file") MultipartFile reapExcelDataFile,
//			@CurrentUser com.pratikshat.exicom.entity.LoginUser user, HttpServletResponse httpResponse)
//			throws IOException {
//		// System.out.println(reapExcelDataFile.getOriginalFilename());
//		String fileName = reapExcelDataFile.getOriginalFilename();
//		
//		
//		if (fileName.substring(fileName.lastIndexOf('.') + 1).equals("xlsx")) {
//			HttpHeaders header = new HttpHeaders();
//			
//
//			if (!dbProfile.hasObjectAccess(user.getUsername(), "CaseDetails", "createRights")) {
//				httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
//				return null;
//			}
//			
//			
//			List<Map<String,Object>> outData=new ArrayList<Map<String,Object>>();
//			ByteArrayOutputStream st=null;
//			try {
//				List<CaseBulkRequest> requests=new CaseParser().parseIn(reapExcelDataFile);
//				for(CaseBulkRequest re:requests) {
//					
//					Map<String,Object> d=new HashMap<String,Object>();
//					
//					
//					d.put("Asset Serial Number", re.getAssetSerialNumber());
//					d.put("Site Id", re.getSiteId());
//					d.put("Caller Phone No", re.getCallerPhoneNo());
//					d.put("Case Type", re.getCaseTypeName());
//					d.put("Case Reason", re.getCaseReasonName());
//					d.put("Status", re.getStatusName());
//					d.put("Stage", re.getStageName());
//					d.put("Case Origin", re.getCaseOriginName());
//					d.put("Priority", re.getPriorityName());
//					d.put("Pending Responsibility", re.getPendingResponsibility());
//					d.put("Problem Reported (seprated by ;)", re.getProblemsReported());
//					d.put("Number Of SMR", re.getNumberOfSMR());
//					d.put("Other Problem Reported", re.getOtherProblemReported());
//					d.put("Remark", re.getRemark());
//					
//					try {
//						
//						CaseRequest req = new CaseRequest();
//						
//						if (re.getAssetSerialNumber()==null || re.getAssetSerialNumber().trim().equals("")) {
//							throw new BadRequestException("Asset is null");
//						}
//						
//						req.setAssetsSerialNumber(re.getAssetSerialNumber().trim());
//						
//						if(re.getSiteId()==null ||re.getSiteId().trim().equalsIgnoreCase("")) {
//							throw new BadRequestException("Site Id is null");
//						}
//						
//						SiteDetailsResponse siteResponse = dbSite.getByManualSiteId(re.getSiteId().trim());
//						if(siteResponse==null) {
//							throw new BadRequestException("Site does not exist");
//						}
//						
//						req.setSiteId(siteResponse.getSiteId());
//						req.setServiceCenterId(siteResponse.getServiceCenterId());
//						req.setServiceResourceId(siteResponse.getServiceResourceId());
//						req.setAssignByAssignmentRule(true);
//						
//						EmployeeDetails emp = dbDev.findByEmailId(user.getUsername());
//						
//						req.setCaseRecordTypeId(emp.getEmptype().getBusinessDomainId());
//						
//						if(re.getCallerPhoneNo()==null || re.getCallerPhoneNo().trim().equalsIgnoreCase("")) {
//							throw new BadRequestException("Caller Phone No. is null");
//						}
//						
//						try {
//							ContactDetails conntact =dbContacts.getContactsByPhoneNoAndAccount(re.getCallerPhoneNo().trim(), siteResponse.getAccountSapNumber());	
//							if(conntact==null) {
//								throw new BadRequestException("Caller does not exist");
//							}
//							req.setContactPersonId(conntact.getContactId());
//						}catch(NonUniqueResultException ex) {
//							throw new BadRequestException("Multiple contact with the same contact no.");
//						}catch(Exception ex) {
//							throw new BadRequestException(ex.getMessage());
//						}
//						
//						if( re.getCaseTypeName()==null || re.getCaseTypeName().trim().equalsIgnoreCase("")) {
//							throw new BadRequestException("Case type is null");
//						}
//						
//						CaseTypes caseType = dbCaseType.getByCaseTypesNameAndVertical(re.getCaseTypeName().trim(), emp.getEmptype().getBusinessDomainId());
//						if(caseType==null) {
//							throw new BadRequestException("Case type does not exist");
//						}
//						
//						req.setCaseTypeId(caseType.getCaseTypeId());
//						
//						if(re.getCaseReasonName()==null || re.getCaseReasonName().trim().equalsIgnoreCase("")) {
//							throw new BadRequestException("Case reason is null");
//						}
//						
//						CaseReasonDetails caseReason= dbCaseReason.getByCaseTypeAndCaseReasonName(caseType.getCaseTypeId(), re.getCaseReasonName().trim(),emp.getEmptype().getBusinessDomainId());
//						if(caseReason==null) {
//							throw new BadRequestException("Case reason does not exist");
//						}
//						
//						req.setCaseReasonId(caseReason.getReasonId());
//						
//						
//						if(re.getStatusName()==null || re.getStatusName().trim().equalsIgnoreCase("")) {
//							throw new BadRequestException("Case status is null");
//						}
//						
//						CaseStatusDetails caseStatus = dbCaseStatus.getCaseStatusByCaseStatusName(re.getStatusName().trim());
//						if(caseStatus==null) {
//							throw new BadRequestException("Case status does not exist");
//						}
//						
//						req.setCaseStatusId(caseStatus.getId());
//						
//						
//						if(re.getStageName()==null || re.getStageName().trim().equalsIgnoreCase("")) {
//							throw new BadRequestException("Case stage is null");
//						}
//						
//						CaseStageDetails caseStage = dbCaseStage.getCaseStageByCaseStatusIdAndStageName(caseStatus.getId(), re.getStageName().trim(),emp.getEmptype().getBusinessDomainId());
//						if(caseStage==null) {
//							throw new BadRequestException("Case stage does not exist");
//						}
//						
//						req.setCaseStageId(caseStage.getId());
//						
//						if(re.getCaseOriginName()==null || re.getCaseOriginName().trim().equalsIgnoreCase("")) {
//							throw new BadRequestException("Case origin is null");
//						}
//						
//						CaseOriginDetails caseOrigin = dbCaseOrigin.getCaseOriginByOriginName(re.getCaseOriginName().trim());
//						if(caseOrigin==null) {
//							throw new BadRequestException("Case origin does not exist");
//						}
//						
//						req.setCaseOriginId(caseOrigin.getCaseOriginId());
//						
//						if(re.getPriorityName()==null || re.getPriorityName().trim().equalsIgnoreCase("")) {
//							throw new BadRequestException("Case priority is null");
//						}
//						
//						CasePriorityDetails casePriority = dbCasePriority.getCasePriorityByPriorityName(re.getPriorityName().trim());
//						if(casePriority==null) {
//							throw new BadRequestException("Case priority does not exist");
//						}
//						
//						req.setCasePriorityId(casePriority.getCasePriorityId());
//						
//						if(re.getPendingResponsibility()==null ||
//								re.getPendingResponsibility().trim().equalsIgnoreCase("")) {
//							throw new BadRequestException("Pending responsibility is null");
//						}
//						
//						PendingResponsibility pendingResponsibility = dbPendingResponsibility.getPendingResponsibilityByName(re.getPendingResponsibility().trim());
//						if(pendingResponsibility==null) {
//							throw new BadRequestException("Pending responsibility does not exist");
//						}
//						
//						req.setPendingResponsibility(pendingResponsibility.getPendingResponsibilityId());
//						
//						if(re.getNumberOfSMR()!=null && !re.getNumberOfSMR().trim().equalsIgnoreCase("")) {
//							try {
//								int num = Integer.parseInt(re.getNumberOfSMR().trim());
//								req.setNoOfSmr(re.getNumberOfSMR().trim());
//							}catch(NumberFormatException ex){
//								throw new BadRequestException("No. of SMR is not valid number");
//							}
//							
//						}
//						
//						if(re.getOtherProblemReported()!=null && !re.getOtherProblemReported().trim().equalsIgnoreCase("")) {
//							req.setOtherProblemReported(re.getOtherProblemReported().trim());
//						}
//						
//						if(re.getRemark()!=null && !re.getRemark().trim().equalsIgnoreCase("")) {
//							req.setRemarks(re.getRemark().trim());
//						}
//						
//						
//						req.setRJILTT01No(null);
//						req.setMissingAssets(null);
//						req.setInputFromEng(null);
//						
//						
//						if(re.getProblemsReported()==null || re.getProblemsReported().trim().equalsIgnoreCase("")) {
//							throw new BadRequestException("Problem reported is null");
//						}
//						CaseProblemReported crp = new CaseProblemReported();
//						String[] list2 = re.getProblemsReported().trim().split(";");
//							Set<com.pratikshat.exicom.entity.CaseProblemReported> caseProblemReported = new HashSet<com.pratikshat.exicom.entity.CaseProblemReported>();
//							for (String li2 : list2) {
//								CaseProblemReported cr = new CaseProblemReported();
//								if (li2.length() != 0) {
//									cr.setProblemName(li2.trim());
//								}
//								caseProblemReported.add(cr);
//							}
//							req.setCaseProblemReported(caseProblemReported);
//
//							CaseRequest request = dbCase.saveCaseDetails(req, user);
//							
//							com.pratikshat.exicom.model.response.CaseResponse caseResponse = dbCase.getByCaseId(request.getCaseId());
//						d.put("STATUS", "Success");
//						d.put("Case Number", caseResponse.getCaseManualId());
//					}catch(Exception ex) {
//						logger.error("error in case bulk upload {}",ex);
//						d.put("STATUS", "Error "+ex.getMessage());
//						d.put("Case Number", "");
//					}
//					outData.add(d);
//				}
//				
//			}catch(Exception ex) {
//				logger.error("error in case bulk upload {}",ex);
//				httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
//				return null;
//			}
//			try {
//				st=new ExcelParser().genrateFile(outData);
//			}catch(Exception ex) {
//				logger.error("error in case bulk upload {}",ex);
//				httpResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,ex.getMessage());
//				return null;
//			}
//			
//			httpResponse.addHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=CaseResult.xlsx");
//			httpResponse.setContentType("application/vnd.ms-excel");
//			httpResponse.getOutputStream().write(st.toByteArray());
//			httpResponse.getOutputStream().flush();
//			httpResponse.getOutputStream().close();
//			return null;
//		} else {
//			httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "file is not excel");
//			return null;
//		}
//	}

	// commercial bulk upload
	@PutMapping("/bulkuploadInvoice")
	public ResponseEntity<ByteArrayResource> mapReadExcelDatatoDB(@RequestParam("file") MultipartFile reapExcelDataFile,
			@CurrentUser com.pratikshat.exicom.entity.LoginUser user, HttpServletResponse httpResponse)
			throws IOException {
		// System.out.println(reapExcelDataFile.getOriginalFilename());
		String fileName = reapExcelDataFile.getOriginalFilename();

		if (fileName.substring(fileName.lastIndexOf('.') + 1).equals("xlsx")) {
			HttpHeaders header = new HttpHeaders();
			
			
			List<Map<String,Object>> outData=new ArrayList<Map<String,Object>>();
			ByteArrayOutputStream st=null;
			try {
				List<InvoiceUpdateRequest> requests=new InvoiceUpdateParser().parseIn(reapExcelDataFile);
				for(InvoiceUpdateRequest re:requests) {
					Map<String,Object> d=new HashMap<String,Object>();
					d.put("Case No", re.getCaseNumber());
					d.put("PO Number", re.getPONumber());
					d.put("PO Date (YYYY-MM-DD)", re.getPODate());
					d.put("Invoice Number", re.getInvoiceNumber());
					d.put("Invoice Date (YYYY-MM-DD)", re.getInvoiceDate());
					
					d.put("Vendor Invoice Number", re.getVendorInvoiceNumber());
					d.put("Vendor Invoice Date (YYYY-MM-DD)", re.getVendorInvoiceDate());
					try {
						dbCase.updateInvoice(re, user);
						d.put("STATUS", "Success");
					}catch(Exception ex) {
						logger.error("error in Invoice bulk upload {}",ex);
						d.put("STATUS", "Error "+ex.getMessage());
					}
					outData.add(d);
				}
				
			}catch(Exception ex) {
				logger.error("error in Invoice bulk upload {}",ex);
				httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
				return null;
			}
			try {
				st=new ExcelParser().genrateFile(outData);
			}catch(Exception ex) {
				logger.error("error in Invoice bulk upload {}",ex);
				httpResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,ex.getMessage());
				return null;
			}
			
			httpResponse.addHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=InvoiceResult.xlsx");
			httpResponse.setContentType("application/vnd.ms-excel");
			httpResponse.getOutputStream().write(st.toByteArray());
			httpResponse.getOutputStream().flush();
			httpResponse.getOutputStream().close();
			return null;
		} else {
			httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "file is not excel");
			return null;
		}
	}

	// Template of Manage Commercial update invoice Excel API
	@GetMapping("/templateExcelInvoice")
	public ResponseEntity<ByteArrayResource> GetExcelInvoice(HttpServletResponse httpResponse) throws IOException {
		HttpHeaders header = new HttpHeaders();
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Excel Template caseInviceUpdate");
		Object[][] bookData = { { "Case No", "PO Number", "PO Date (YYYY-MM-DD)", "Invoice Number", "Invoice Date (YYYY-MM-DD)",
				"Vendor Invoice Number", "Vendor Invoice Date (YYYY-MM-DD)" },

		};
		
		CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setColor(Font.COLOR_RED);
        style.setFont(font);
		

		int rowCount = 0;

		for (Object[] aBook : bookData) {
			Row row = sheet.createRow(rowCount);

			int columnCount = 0;

			for (Object field : aBook) {
				Cell cell = row.createCell(columnCount++);
				sheet.autoSizeColumn(columnCount);
				if (field instanceof String) {
					cell.setCellValue((String) field);
					if(columnCount==1) {
						cell.setCellStyle(style);
					}
				} else if (field instanceof Integer) {
					cell.setCellValue((Integer) field);
					if(columnCount==1) {
						cell.setCellStyle(style);
					}
				}
			}

		}

		header.setContentType(new MediaType("application", "force-download"));
		header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + "invoice.xlsx");
		httpResponse.setContentType("application/xlsx");
		ByteArrayOutputStream bytest = new ByteArrayOutputStream();
		workbook.write(bytest);
		FileOutputStream fileOut = new FileOutputStream("invoice.xlsx");
		return new ResponseEntity<>(new ByteArrayResource(bytest.toByteArray()), header, HttpStatus.OK);
	}

	
	@PutMapping("documentreceived/{caseId}")
	public com.pratikshat.exicom.model.response.CaseResponse documentReceived(
			@PathVariable("caseId") long caseId,
			@CurrentUser com.pratikshat.exicom.entity.LoginUser user, HttpServletResponse httpResponse)
			throws java.io.IOException {
		try {

			if (dbProfile.hasObjectAccess(user.getUsername(), "CaseDetails", "editRights")) {
				if (caseId != 0) {

					dbCase.documentReceived(caseId,user);
					
					com.pratikshat.exicom.model.response.CaseResponse _case = dbCase.getByCaseId(caseId);
					if (_case != null) {
						_case.setProblemReported(dbCaseProblem.getByCaseId(_case.getCaseId()));
					}
					return _case;
					
				} else {
					httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Request parameter not filled");
					return null;
				}

			} else {
				httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorized");
				return null;
			}
		} catch (Exception e) {
			httpResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			return null;
		}
	}
	
	
	@PostMapping("changeCaseType")
	public ResponseEntity<?> bulkUdpated(@RequestParam(value="file")MultipartFile file,@CurrentUser LoginUser user,HttpServletResponse response)throws IOException{
		if(!dbProfile.hasObjectAccess(user.getUsername(), ChangeCaseTypeLog.class, UserProfileRepository.CREATE) ||
				!dbProfile.hasObjectAccess(user.getUsername(), ChangeCaseTypeLog.class, UserProfileRepository.EDIT)) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN,"You are not authorise");
			return null;
		}
		List<Map<String,Object>> outData=new ArrayList<Map<String,Object>>();
		ByteArrayOutputStream st=null;
		try {
			List<ChangeCaseTypeRequest> requests=new ChangeCaseTypeParser().parseIn(file);
			for(ChangeCaseTypeRequest re:requests) {
				Map<String,Object> d=new HashMap<String,Object>();
				d.put("Case Number", re.getCaseManualId());
				d.put("Case type", re.getCaseType());
				d.put("Case reason", re.getCaseReason());
				d.put("Po Number", re.getPoNo());
				d.put("Po Date (YYYY-MM-DD)", re.getPoDate());
				try {
					dbCase.changeCaseType(re, user);
					d.put("STATUS", "Success");
				}catch(Exception ex) {
					d.put("STATUS", "Error "+ex.getMessage());
				}
				outData.add(d);
			}
			
		}catch(Exception ex) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
			return null;
		}
		try {
			st=new ExcelParser().genrateFile(outData);
		}catch(Exception ex) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,ex.getMessage());
			return null;
		}
		//HttpHeaders headers = new HttpHeaders();
		//headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=customerinventorystatus.xlsx");
		
		response.addHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=ChangeCaseTypeResult.xlsx");
		response.setContentType("application/vnd.ms-excel");
		response.getOutputStream().write(st.toByteArray());
		response.getOutputStream().flush();
		response.getOutputStream().close();
		return null;
		//return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_OCTET_STREAM).body(st);
	}
	
	
	// Template for Change case type
	@GetMapping("/templateChangeCaseType")
	public ResponseEntity<ByteArrayResource> getTemplateChangeCaseType(HttpServletResponse httpResponse)
			throws IOException {
		HttpHeaders header = new HttpHeaders();
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("ChangeCaseType");
		Object[][] bookData = { { "Case Number", "Case type", "Case reason", "Po Number", "Po Date (YYYY-MM-DD)" },

		};

		CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setColor(Font.COLOR_RED);
        style.setFont(font);
		
		int rowCount = 0;

		for (Object[] aBook : bookData) {
			Row row = sheet.createRow(rowCount);

			int columnCount = 0;

			for (Object field : aBook) {
				Cell cell = row.createCell(columnCount++);
				sheet.autoSizeColumn(columnCount);
				if (field instanceof String) {
					cell.setCellValue((String) field);
					if(columnCount!=4&&columnCount!=5) {
						cell.setCellStyle(style);
					}
				} else if (field instanceof Integer) {
					cell.setCellValue((Integer) field);
					if(columnCount!=4&&columnCount!=5) {
						cell.setCellStyle(style);
					}
				}
			}

		}

		header.setContentType(new MediaType("application", "force-download"));
		header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + "ChangeCaseType.xlsx");
		httpResponse.setContentType("application/xlsx");
		ByteArrayOutputStream bytest = new ByteArrayOutputStream();
		workbook.write(bytest);
		FileOutputStream fileOut = new FileOutputStream("ChangeCaseType.xlsx");
		return new ResponseEntity<>(new ByteArrayResource(bytest.toByteArray()), header, HttpStatus.OK);
	}

	@PostMapping("searchincase")
	public List<CaseResponse> caseSearch(@RequestBody CaseSearchRequest req, @CurrentUser LoginUser user) {
		if (dbProfile.hasObjectAccess(user.getUsername(), CaseDetails.class, UserProfileRepository.VIEW_ALL))
			return dbCase.getCaseSearchResult(req, null);
		if (dbProfile.hasObjectAccess(user.getUsername(), CaseDetails.class, UserProfileRepository.VIEW)) {
			List<String> usrs = dbProfile.getListOfUserAndChildByUser(user.getUsername());
			if (usrs != null && usrs.size() > 0) {
				return dbCase.getCaseSearchResult(req, usrs);
			}
		}

		return new ArrayList<CaseResponse>();
	}
		
		
		
	@PutMapping("reject")
	public CaseResponse rejectCase(@RequestBody RejectCaseRequest rejectCaseRequest,
			@CurrentUser com.pratikshat.exicom.entity.LoginUser user, HttpServletResponse httpResponse)
			throws IOException {

		if (!dbProfile.hasObjectAccess(user.getUsername(), CaseRejectLog.class, UserProfileRepository.CREATE)
				|| !dbProfile.hasObjectAccess(user.getUsername(), CaseRejectLog.class, UserProfileRepository.EDIT)) {
			httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorized");
			return null;
		}

		try {

			if (rejectCaseRequest.getCaseManualId() == null
					|| rejectCaseRequest.getCaseManualId().equalsIgnoreCase("")) {
				httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Case Id is null");
				return null;
			}

			if (rejectCaseRequest.getRemark() == null || rejectCaseRequest.getRemark().trim().equalsIgnoreCase("")) {
				httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Remark is blank");
				return null;
			}

			CaseDetails _caseDetails = dbCase.findByCaseManualId(rejectCaseRequest.getCaseManualId());
			if (_caseDetails == null) {
				httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Case does not exist");
				return null;
			} else {

				RejectCaseRequest _caseRequest = dbCase.rejectCase(rejectCaseRequest, user);

				com.pratikshat.exicom.model.response.CaseResponse _case = dbCase
						.getByCaseManualId(rejectCaseRequest.getCaseManualId());
				if (_case != null) {
					_case.setProblemReported(dbCaseProblem.getByCaseId(_case.getCaseId()));
				}
				return _case;
			}

		} catch (BadRequestException ex) {
			ex.printStackTrace();
			httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
			return null;
		} catch (UnauthoriseException ex) {
			ex.printStackTrace();
			httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, ex.getMessage());
			return null;
		} catch (Exception ex) {
			ex.printStackTrace();
			httpResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
			return null;
		}

	}
	
	
	@GetMapping("reject")
	public List<CaseRejectLogResponse> getRejectLog(@RequestParam(value="caseManualId",required=false)String caseManualId, @CurrentUser LoginUser user, HttpServletResponse httpResponse) {
			if(caseManualId!=null && !caseManualId.equalsIgnoreCase("")) {
				return dbCaseRejectLog.getCaseRejectLogByCaseManualId(caseManualId);
			}
		return new ArrayList<CaseRejectLogResponse>();
	}
	
	
	@PutMapping("changeCaseTypeSingle")
	public CaseResponse changeCaseTypeSingle(@RequestBody ChangeCaseTypeRequest request,
			@CurrentUser com.pratikshat.exicom.entity.LoginUser user, HttpServletResponse httpResponse)
			throws IOException {

		if(!dbProfile.hasObjectAccess(user.getUsername(), ChangeCaseTypeLog.class, UserProfileRepository.CREATE) ||
				!dbProfile.hasObjectAccess(user.getUsername(), ChangeCaseTypeLog.class, UserProfileRepository.EDIT)) {
			httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN,"You are not authorise");
			return null;
		}

		try {

			if (request.getCaseManualId() == null
					|| request.getCaseManualId().trim().equalsIgnoreCase("")) {
				httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Case Id is null");
				return null;
			}

			if (request.getCaseType() == null || request.getCaseType().trim().equalsIgnoreCase("")) {
				httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Case Type is blank");
				return null;
			}
			
			if (request.getCaseReason() == null
					|| request.getCaseReason().trim().equalsIgnoreCase("")) {
				httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Case Reason is null");
				return null;
			}
			
			if(request.getCaseType().equalsIgnoreCase("Under AMC")) {
				if (request.getPoNo() == null || request.getPoNo().trim().equalsIgnoreCase("")) {
					httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "PO No. is blank");
					return null;
				}
				
				if (request.getPoDate() == null) {
					httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "PO Date is blank");
					return null;
				}
			}

			CaseDetails _caseDetails = dbCase.findByCaseManualId(request.getCaseManualId());
			if (_caseDetails == null) {
				httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Case does not exist");
				return null;
			} else {

				ChangeCaseTypeRequest _caseRequest = dbCase.changeCaseType(request, user);

				com.pratikshat.exicom.model.response.CaseResponse _case = dbCase
						.getByCaseManualId(request.getCaseManualId());
				if (_case != null) {
					_case.setProblemReported(dbCaseProblem.getByCaseId(_case.getCaseId()));
				}
				return _case;
			}

		} catch (BadRequestException ex) {
			ex.printStackTrace();
			httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
			return null;
		} catch (UnauthoriseException ex) {
			ex.printStackTrace();
			httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, ex.getMessage());
			return null;
		} catch (Exception ex) {
			ex.printStackTrace();
			httpResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
			return null;
		}

	}
	
	
	@GetMapping("changeCaseTypeSingle/caseType")
	public List<String> changeCaseTypeSingleCaseType(@RequestParam("existing_case_type")String  existing_case_type, @RequestParam("existing_case_reason")String existing_case_reason,
			@CurrentUser com.pratikshat.exicom.entity.LoginUser user, HttpServletResponse httpResponse)
			throws IOException {

		if(!dbProfile.hasObjectAccess(user.getUsername(), ChangeCaseTypeLog.class, UserProfileRepository.CREATE) ||
				!dbProfile.hasObjectAccess(user.getUsername(), ChangeCaseTypeLog.class, UserProfileRepository.EDIT)) {
			httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN,"You are not authorise");
			return null;
		}

		if (existing_case_type == null || existing_case_type.trim().equalsIgnoreCase("")) {
			httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Existing case type is blank");
			return null;
		}

		if (existing_case_reason == null || existing_case_reason.trim().equalsIgnoreCase("")) {
			httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Existing case reason is blank");
			return null;
		}

		List<Map<String,Object>> newCaseType=  dbChangeCaseType.getNewCaseType(existing_case_type, existing_case_reason);
		
		List<String>  listOfValue = new ArrayList<String>();
		if(!newCaseType.isEmpty()) {
			for(Map map: newCaseType){
			       listOfValue.addAll(map.values());
			   }
		}
		
		return listOfValue;
		   
	}
	
	
	
	@GetMapping("changeCaseTypeSingle/caseReason")
	public List<String> changeCaseTypeSingleCaseReason(@RequestParam("existing_case_type")String  existing_case_type, @RequestParam("existing_case_reason")String existing_case_reason,
			@RequestParam("new_case_type")String new_case_type,
			@CurrentUser com.pratikshat.exicom.entity.LoginUser user, HttpServletResponse httpResponse)
			throws IOException {

		if(!dbProfile.hasObjectAccess(user.getUsername(), ChangeCaseTypeLog.class, UserProfileRepository.CREATE) ||
				!dbProfile.hasObjectAccess(user.getUsername(), ChangeCaseTypeLog.class, UserProfileRepository.EDIT)) {
			httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN,"You are not authorise");
			return null;
		}

		if (existing_case_type == null || existing_case_type.trim().equalsIgnoreCase("")) {
			httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Existing case type is blank");
			return null;
		}

		if (existing_case_reason == null || existing_case_reason.trim().equalsIgnoreCase("")) {
			httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Existing case reason is blank");
			return null;
		}

		if (new_case_type == null || new_case_type.trim().equalsIgnoreCase("")) {
			httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "New case type is blank");
			return null;
		}
		
		List<Map<String,Object>> newCaseReason= dbChangeCaseType.getNewCaseReasonByNewCaseType(existing_case_type, existing_case_reason,new_case_type);
		
		List<String>  listOfValue = new ArrayList<String>();
		if(!newCaseReason.isEmpty()) {
			for(Map map: newCaseReason){
			       listOfValue.addAll(map.values());
			   }
		}
		
		return listOfValue;   
	}
	
	
	
	
	@GetMapping("/podTemplate")
	public ResponseEntity<ByteArrayResource> getPODTemplate(HttpServletResponse httpResponse) throws IOException {
		HttpHeaders header = new HttpHeaders();
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("POD");
		Object[][] bookData = { { "Case Number", "Tracking Number", "Tracking Date (YYYY-MM-DD)", "Shipping Mode"},};

		CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setColor(Font.COLOR_RED);
        style.setFont(font);
		
		int rowCount = 0;

		for (Object[] aBook : bookData) {
			Row row = sheet.createRow(rowCount);

			int columnCount = 0;

			for (Object field : aBook) {
				Cell cell = row.createCell(columnCount++);
				if (field instanceof String) {
					cell.setCellValue((String) field);
					cell.setCellStyle(style);
				} else if (field instanceof Integer) {
					cell.setCellValue((Integer) field);
					cell.setCellStyle(style);
				}
			}

		}

		header.setContentType(new MediaType("application", "force-download"));
		header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + "pod_template.xlsx");
		httpResponse.setContentType("application/xlsx");
		ByteArrayOutputStream bytest = new ByteArrayOutputStream();
		workbook.write(bytest);
		return new ResponseEntity<>(new ByteArrayResource(bytest.toByteArray()), header, HttpStatus.OK);
	}

	@PostMapping("/podBulkUpload")
	public ResponseEntity<ByteArrayResource> updateBulkPOD(@RequestParam("file") MultipartFile reapExcelDataFile,
			@CurrentUser com.pratikshat.exicom.entity.LoginUser user, HttpServletResponse httpResponse)
			throws IOException {
		System.out.println("bulk upload controller");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		String fileName = reapExcelDataFile.getOriginalFilename();
		
		
		if (fileName.substring(fileName.lastIndexOf('.') + 1).equals("xlsx")) {
			HttpHeaders header = new HttpHeaders();
			

			if (!dbProfile.hasObjectAccess(user.getUsername(), "CaseDetails", "editRights")) {
				httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised pitega");
				return null;
			}
			
			
			List<Map<String,Object>> outData=new ArrayList<Map<String,Object>>();
			ByteArrayOutputStream st=null;
			try {
				List<PODUpdateRequest> requests=new PODParser().parseIn(reapExcelDataFile);
				for(PODUpdateRequest re:requests) {
					
					Map<String,Object> d=new HashMap<String,Object>();
					
					
					d.put("Case Number", re.getCaseNumber());
					d.put("Tracking Number", re.getPodNumber());
					d.put("Tracking Date (YYYY-MM-DD)", re.getPodDate());
					d.put("Shipping Mode", re.getPodShippingMode());
//					d.put("Shipping To Name", re.getPodShippingToName());
//					d.put("Shipping Tracking Number", re.getPodShippingTrackingNumber());
//					d.put("Shipping Expected Date (YYYY-MM-DD)", re.getPodShippingExpectedDate());
					
					try {
						
						CaseProofOfDeliveryRequest req = new CaseProofOfDeliveryRequest();
						
						CaseResponse caseDetails = dbCase.getByCaseManualId(re.getCaseNumber().trim());
						
						if(caseDetails==null) {
							throw new BadRequestException("Case does not exist");
						}
						
						req.setCaseId(caseDetails.getCaseId());
						
						req.setPodNumber(re.getPodNumber());
						req.setPodDate(re.getPodDate());
						
						req.setPodShippingMode(re.getPodShippingMode());
//						req.setPodShippingToName(re.getPodShippingToName());
//						req.setPodShippingTrackingNumber(re.getPodShippingTrackingNumber());
//						req.setPodShippingExpectedDate(sdf.format(re.getPodShippingExpectedDate()));
						
						CaseProofOfDeliveryRequest request = dbCase.updateProofOfDelivery(req,user);
						
						d.put("STATUS", "Success");
					}catch(Exception ex) {
						logger.error("error in pod bulk upload {}",ex);
						d.put("STATUS", "Error "+ex.getMessage());
					}
					outData.add(d);
				}
				
			}catch(Exception ex) {
				logger.error("error in pod bulk upload {}",ex);
				httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
				return null;
			}
			try {
				st=new ExcelParser().genrateFile(outData);
			}catch(Exception ex) {
				logger.error("error in pod bulk upload {}",ex);
				httpResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,ex.getMessage());
				return null;
			}
			
			httpResponse.addHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=PODResult.xlsx");
			httpResponse.setContentType("application/vnd.ms-excel");
			httpResponse.getOutputStream().write(st.toByteArray());
			httpResponse.getOutputStream().flush();
			httpResponse.getOutputStream().close();
			return null;
		} else {
			httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "file is not excel");
			return null;
		}
	}
	
	@GetMapping("/caseRejectTemplate")
	public ResponseEntity<ByteArrayResource> getCaseRejectTemplate(HttpServletResponse httpResponse) throws IOException {
		HttpHeaders header = new HttpHeaders();
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("case_reject");
		Object[][] bookData = { { "Case Number", "Remark"},};

		int rowCount = 0;
		
		CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setColor(Font.COLOR_RED);
        style.setFont(font);

		for (Object[] aBook : bookData) {
			Row row = sheet.createRow(rowCount);

			int columnCount = 0;

			for (Object field : aBook) {
				Cell cell = row.createCell(columnCount++);
				if (field instanceof String) {
					cell.setCellValue((String) field);
					cell.setCellStyle(style);
				} else if (field instanceof Integer) {
					cell.setCellValue((Integer) field);
					cell.setCellStyle(style);
				}
			}

		}

		header.setContentType(new MediaType("application", "force-download"));
		header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + "case_reject_template.xlsx");
		httpResponse.setContentType("application/xlsx");
		ByteArrayOutputStream bytest = new ByteArrayOutputStream();
		workbook.write(bytest);
		return new ResponseEntity<>(new ByteArrayResource(bytest.toByteArray()), header, HttpStatus.OK);
	}
	
	
	@PostMapping("/caseRejectBulkUpload")
	public ResponseEntity<ByteArrayResource> rejectCaseBulkUpload(@RequestParam("file") MultipartFile reapExcelDataFile,
			@CurrentUser com.pratikshat.exicom.entity.LoginUser user, HttpServletResponse httpResponse)
			throws IOException {
		
		String fileName = reapExcelDataFile.getOriginalFilename();
		
		
		if (fileName.substring(fileName.lastIndexOf('.') + 1).equals("xlsx")) {
			HttpHeaders header = new HttpHeaders();
			

			if (!dbProfile.hasObjectAccess(user.getUsername(), CaseRejectLog.class, UserProfileRepository.CREATE)
					|| !dbProfile.hasObjectAccess(user.getUsername(), CaseRejectLog.class, UserProfileRepository.EDIT)) {
				httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorized");
				return null;
			}
			
			
			List<Map<String,Object>> outData=new ArrayList<Map<String,Object>>();
			ByteArrayOutputStream st=null;
			try {
				List<RejectCaseRequest> requests=new CaseRejectParser().parseIn(reapExcelDataFile);
				for(RejectCaseRequest rejectCaseRequest:requests) {
					
					Map<String,Object> d=new HashMap<String,Object>();
					
					
					d.put("Case Number", rejectCaseRequest.getCaseManualId());
					d.put("Remark", rejectCaseRequest.getRemark());
					
					try {
						
						if (rejectCaseRequest.getCaseManualId() == null
								|| rejectCaseRequest.getCaseManualId().equalsIgnoreCase("")) {
							httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Case Id is null");
							return null;
						}

						if (rejectCaseRequest.getRemark() == null || rejectCaseRequest.getRemark().trim().equalsIgnoreCase("")) {
							httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Remark is blank");
							return null;
						}

						CaseDetails _caseDetails = dbCase.findByCaseManualId(rejectCaseRequest.getCaseManualId().trim());
						if (_caseDetails == null) {
							httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Case does not exist");
							return null;
						} 
						
						RejectCaseRequest _caseRequest = dbCase.rejectCase(rejectCaseRequest, user);
						d.put("STATUS", "Success");
						
					}catch(Exception ex) {
						logger.error("error in case reject bulk upload {}",ex);
						d.put("STATUS", "Error "+ex.getMessage());
					}
					outData.add(d);
				}
				
			}catch(Exception ex) {
				logger.error("error in case reject bulk upload {}",ex);
				httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
				return null;
			}
			try {
				st=new ExcelParser().genrateFile(outData);
			}catch(Exception ex) {
				logger.error("error in case reject bulk upload {}",ex);
				httpResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,ex.getMessage());
				return null;
			}
			
			httpResponse.addHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=CaseRejectResult.xlsx");
			httpResponse.setContentType("application/vnd.ms-excel");
			httpResponse.getOutputStream().write(st.toByteArray());
			httpResponse.getOutputStream().flush();
			httpResponse.getOutputStream().close();
			return null;
		} else {
			httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "file is not excel");
			return null;
		}
	}
	
	
	
	@GetMapping("/documentreceived/template")
	public ResponseEntity<ByteArrayResource> caseDocumentReceivedTemplate(HttpServletResponse httpResponse) throws IOException {
		HttpHeaders header = new HttpHeaders();
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("case_document_received");
		Object[][] bookData = { { "Case Number"},};

		int rowCount = 0;
		
		CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setColor(Font.COLOR_RED);
        style.setFont(font);

		for (Object[] aBook : bookData) {
			Row row = sheet.createRow(rowCount);

			int columnCount = 0;

			for (Object field : aBook) {
				Cell cell = row.createCell(columnCount++);
				if (field instanceof String) {
					cell.setCellValue((String) field);
					cell.setCellStyle(style);
				} else if (field instanceof Integer) {
					cell.setCellValue((Integer) field);
					cell.setCellStyle(style);
				}
			}

		}

		header.setContentType(new MediaType("application", "force-download"));
		header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + "case_document_received.xlsx");
		httpResponse.setContentType("application/xlsx");
		ByteArrayOutputStream bytest = new ByteArrayOutputStream();
		workbook.write(bytest);
		return new ResponseEntity<>(new ByteArrayResource(bytest.toByteArray()), header, HttpStatus.OK);
	}
	
	
	
	
	@PostMapping("/documentreceived/bulkupload")
	public com.pratikshat.exicom.model.response.CaseResponse documentReceivedBulkUpload(
			@RequestParam("file") MultipartFile documentRececivedBulkUpload,
			@CurrentUser com.pratikshat.exicom.entity.LoginUser user, HttpServletResponse httpResponse)
			throws java.io.IOException {
		
		
		String fileName = documentRececivedBulkUpload.getOriginalFilename();
		
		
		if (fileName.substring(fileName.lastIndexOf('.') + 1).equals("xlsx")) {
			HttpHeaders header = new HttpHeaders();
			

			if (!dbProfile.hasObjectAccess(user.getUsername(), "CaseDetails", "editRights")) {
				httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorized");
				return null;
			}
			
			
			List<Map<String,Object>> outData=new ArrayList<Map<String,Object>>();
			ByteArrayOutputStream st=null;
			try {
				List<CaseDocumentReceivedRequest> requests=new CaseDocumentReceivedParser().parseIn(documentRececivedBulkUpload);
				for(CaseDocumentReceivedRequest documentReceivedRequest:requests) {
					
					Map<String,Object> d=new HashMap<String,Object>();
					
					
					d.put("Case Number", documentReceivedRequest.getCaseManualId());
				
					
					try {
						
						if (documentReceivedRequest.getCaseManualId() == null
								|| documentReceivedRequest.getCaseManualId().equalsIgnoreCase("")) {
							httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Case Number is null");
							return null;
						}

						CaseResponse caseResponse = dbCase.getByCaseManualId(documentReceivedRequest.getCaseManualId().trim());
						
						if(caseResponse==null) {
							httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Case does not exist");
							return null;
						}
						
						dbCase.documentReceived(caseResponse.getCaseId(),user);
						
						d.put("STATUS", "Success");
						
					}catch(Exception ex) {
						logger.error("error in case document received bulk upload {}",ex);
						d.put("STATUS", "Error "+ex.getMessage());
					}
					outData.add(d);
				}
				
			}catch(Exception ex) {
				logger.error("error in case document received bulk upload {}",ex);
				httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
				return null;
			}
			try {
				st=new ExcelParser().genrateFile(outData);
			}catch(Exception ex) {
				logger.error("error in case document received bulk upload {}",ex);
				httpResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,ex.getMessage());
				return null;
			}
			
			httpResponse.addHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=CaseDocumentReceviedResult.xlsx");
			httpResponse.setContentType("application/vnd.ms-excel");
			httpResponse.getOutputStream().write(st.toByteArray());
			httpResponse.getOutputStream().flush();
			httpResponse.getOutputStream().close();
			return null;
		} else {
			httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "file is not excel");
			return null;
		}
	}
	
	
	@GetMapping("casetype/forlist")
	public java.util.List<String> getCaseTypeList() {
		return dbCaseType.getCaseTypeList();
	}
	
	@GetMapping("casereason/forlist")
	public java.util.List<String> getCaseReasonList() {
		return dbCaseReason.getCaseReasonList();
	}
	
	@GetMapping("casestatus/forlist")
	public java.util.List<String> getCaseStatusList() {
		return dbCaseStatus.getCaseStatus();
	}
	
	@GetMapping("casestage/forlist")
	public java.util.List<String> getCaseStageList() {
		return dbCaseStage.getCaseStageList();
	}
	
	@GetMapping("customer/forlist")
	public java.util.List<String> getCustomerList(@RequestParam("customer")String customer) {
		return dbSite.getSiteCustomerList("%"+customer+"%");
	}
	
	@PostMapping("uploadfile/{caseid}")
	public String uploadCaseFile(@PathVariable("caseid")Long caseId,@CurrentUser LoginUser user,@RequestBody List<MultipartFile> files) throws BadRequestException, UnauthoriseException {
		if(dbProfile.hasObjectAccess(user.getUsername(), CaseFilesUpload.class, UserProfileRepository.CREATE)) {
			dbCaseFileUplaodRepo.uploadImage(caseId, files, user);
			return "{}";
		}
		else
			throw new UnauthoriseException("You are not authorised");
		
	}
	@GetMapping("{caseid}/downloadfile/{uploadid}")
	public ResponseEntity<?> downloadCasefile(@PathVariable("caseid")Long caseId,@PathVariable("uploadid")Long uploadId){
		
		Map<String,Object> upload=dbCaseFileUplaodRepo.getFilesByCaseIdAndUploadId(caseId, uploadId);
		
		if(upload!=null) {
			Resource res=dbFileuploadService.loadFileAsResource(upload.get("upload_physical_location").toString());
			return ResponseEntity.ok()
		               .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + upload.get("uploaded_file_name").toString() + "\"")
		               .body(res);
		}
		else
			return ResponseEntity.badRequest().body("{}");
	}
	
	@GetMapping("casefile/{caseid}")
	public List<Map<String,Object>> getCaseUploadFileList(@CurrentUser LoginUser user,@PathVariable("caseid")Long caseId){
		return dbCaseFileUplaodRepo.getFileUploadedOncaseByCaseId(caseId);
	}
}


