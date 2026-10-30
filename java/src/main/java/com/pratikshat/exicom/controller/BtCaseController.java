package com.pratikshat.exicom.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.NonUniqueResultException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.pratikshat.exicom.annotation.CurrentUser;
import com.pratikshat.exicom.entity.BtAssetsTypeChoice;
import com.pratikshat.exicom.entity.BtCase;
import com.pratikshat.exicom.entity.BtCaseExpectedProblems;
import com.pratikshat.exicom.entity.BtCaseProblemReported;
import com.pratikshat.exicom.entity.BtCaseRejectLog;
import com.pratikshat.exicom.entity.BtChangeCaseTypeLog;
import com.pratikshat.exicom.entity.BtProblemMaster;
import com.pratikshat.exicom.entity.CaseOriginDetails;
import com.pratikshat.exicom.entity.CasePriorityDetails;
import com.pratikshat.exicom.entity.CaseReasonDetails;
import com.pratikshat.exicom.entity.CaseStageDetails;
import com.pratikshat.exicom.entity.CaseStatusDetails;

import com.pratikshat.exicom.entity.CaseTypes;
import com.pratikshat.exicom.entity.ChangeCaseTypeLog;
import com.pratikshat.exicom.entity.ContactDetails;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.PendingResponsibility;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.BtCaseBulkRequest;
import com.pratikshat.exicom.model.request.BtCaseRequest;
import com.pratikshat.exicom.model.request.CaseDocumentReceivedRequest;
import com.pratikshat.exicom.model.request.CaseInvoiceRequest;
import com.pratikshat.exicom.model.request.CaseProofOfDeliveryRequest;
import com.pratikshat.exicom.model.request.ChangeCaseTypeRequest;
import com.pratikshat.exicom.model.request.InvoiceUpdateRequest;
import com.pratikshat.exicom.model.request.PODUpdateRequest;
import com.pratikshat.exicom.model.request.PodRequest;
import com.pratikshat.exicom.model.request.RejectCaseRequest;
import com.pratikshat.exicom.model.response.BtCaseResponse;
import com.pratikshat.exicom.model.response.BtServiceFSRResponse;
import com.pratikshat.exicom.model.response.CaseStatusResponse;
import com.pratikshat.exicom.repository.AccountsOwnerDetailsRepository;
import com.pratikshat.exicom.repository.BtAssetsTypeChoiceRepository;
import com.pratikshat.exicom.repository.BtCaseExpectedProblemsRepository;
import com.pratikshat.exicom.repository.BtCaseProblemReportedRepository;
import com.pratikshat.exicom.repository.BtCaseRejectLogRepository;
import com.pratikshat.exicom.repository.BtCaseRepository;
import com.pratikshat.exicom.repository.BtProblemMasterRepository;
import com.pratikshat.exicom.repository.BtServiceAppoinmentProductConsumedRepository;
import com.pratikshat.exicom.repository.BtServiceAppointmentRepository;
import com.pratikshat.exicom.repository.BtSiteDetailsRepository;
import com.pratikshat.exicom.repository.CaseOriginDetailsRepository;
import com.pratikshat.exicom.repository.CasePriorityDetailsRepository;
import com.pratikshat.exicom.repository.CaseReasonDetailsRepository;
import com.pratikshat.exicom.repository.CaseStageDetailsRepository;
import com.pratikshat.exicom.repository.CaseStatusDetailsRepository;

import com.pratikshat.exicom.repository.CaseTypesRepository;
import com.pratikshat.exicom.repository.ChangeCaseTypeConditionsRepository;
import com.pratikshat.exicom.repository.ContactDetailsRepository;
import com.pratikshat.exicom.repository.PendingResponsibilityRepository;
import com.pratikshat.exicom.repository.UserProfileRepository;
import com.pratikshat.exicom.services.BtCaseFileUploadService;
import com.pratikshat.exicom.services.BtCaseService;
import com.pratikshat.exicom.services.FileStorageService;
import com.pratikshat.exicom.utils.BtCaseMissingParse;
import com.pratikshat.exicom.utils.BtCaseParser;
import com.pratikshat.exicom.utils.CaseDocumentReceivedParser;
import com.pratikshat.exicom.utils.CaseRejectParser;
import com.pratikshat.exicom.utils.ChangeCaseTypeParser;
import com.pratikshat.exicom.utils.ExcelParser;
import com.pratikshat.exicom.utils.InvoiceUpdateParser;
import com.pratikshat.exicom.utils.PODParser;

@RestController
@RequestMapping("bt/api/case")
public class BtCaseController {
	@Autowired
	ContactDetailsRepository dbContacts;
	@Autowired
	BtCaseService btCaseService;
	@Autowired
	CaseStatusDetailsRepository dbCaseStatus;
	@Autowired
	CaseTypesRepository dbCaseType;
	@Autowired
	AccountsOwnerDetailsRepository dbDev;
	@Autowired
	CaseReasonDetailsRepository dbCaseReason;
	@Autowired
	CaseStageDetailsRepository dbCaseStage;
	@Autowired
	CaseOriginDetailsRepository dbCaseOrigin;
	@Autowired
	CasePriorityDetailsRepository dbCasePriority;
	
	@Autowired
	PendingResponsibilityRepository dbPendingResponsibility;
	@Autowired
	BtCaseExpectedProblemsRepository dbCaseExpectedProblem;
	@Autowired
	UserProfileRepository dbProfile;
	@Autowired
	BtCaseRepository dbCase;
	@Autowired
	BtServiceAppointmentRepository dbServiceAppointment;
	@Autowired
	BtServiceAppoinmentProductConsumedRepository dbServiceAppoinmentProductConsumed;
	@Autowired
	BtCaseRejectLogRepository dbCaseRejectLog;
	@Autowired
	BtAssetsTypeChoiceRepository dbAssetsType;
	@Autowired
	BtCaseProblemReportedRepository dbCaseProblem;
	@Autowired
	ChangeCaseTypeConditionsRepository dbChangeCaseType;
	@Autowired
	BtCaseFileUploadService dbFileService;
	@Autowired
	FileStorageService dbStorageService;
	@Autowired
	BtSiteDetailsRepository dbSite;
	@Autowired
	BtCaseService caseService;
	@Autowired
	BtProblemMasterRepository dbproblem;
	@Autowired
	BtAssetsTypeChoiceRepository dbChoice;
	
	

	Logger logger = LoggerFactory.getLogger(BtCaseController.class);
	
	@GetMapping("casetypecasereason")
	public List<Map<String, Object>> getValues(@CurrentUser LoginUser user,@RequestParam ("type") String type, @RequestParam ("reason") String reason){
		if (dbProfile.hasObjectAccess(user.getUsername(), BtCase.class, UserProfileRepository.VIEW) || dbProfile.hasObjectAccess(user.getUsername(), BtCase.class, UserProfileRepository.VIEW_ALL)) {
			List<String> _us = new ArrayList<String>();
			_us = dbProfile.getListOfUserAndChildByUser(user.getUsername());
			List<String> userLis = new ArrayList<String>();
			for (String u : _us) {
				userLis.add(u);
			}
		return dbCase.getCaseOrServiceAppointment(type,reason,userLis);
		}
		return null;
	}
	
	// @GetMapping("wcccasetypecasereason")
	// public List<Map<String, Object>> getValues(@CurrentUser LoginUser user,@RequestParam ("type") String type, @RequestParam ("reason") String reason,@RequestParam ("wccId") long wccId){
	// 	if (dbProfile.hasObjectAccess(user.getUsername(), BtCase.class, UserProfileRepository.VIEW) || dbProfile.hasObjectAccess(user.getUsername(), BtCase.class, UserProfileRepository.VIEW_ALL)) {
	// 		List<String> _us = new ArrayList<String>();
	// 		_us = dbProfile.getListOfUserAndChildByUser(user.getUsername());
	// 		List<String> userLis = new ArrayList<String>();
	// 		for (String u : _us) {
	// 			userLis.add(u);
	// 		}
	// 	return dbCase.getCaseofwcc(type,reason,userLis,wccId);
	// 	}
	// 	return null;
	// }
	
//	@PutMapping("podupdate")
//	public List<Map<String, Object>> getByStage(@RequestPart("data")PodRequest request,@RequestPart("file") List<MultipartFile> files,@CurrentUser LoginUser user) throws BadRequestException, UnauthoriseException{
//		return btCaseService.podNewUpdate(request, files, user);
//	}

	@GetMapping("releted/{caseid}/serviceappointment")
	public Page<Map<String, Object>> getServiceAppointmentByCaseId(@PathVariable("caseid") long caseid, Pageable page) {
		return btCaseService.getServiceAppointmentByCaseId(caseid, page);
	}

	@GetMapping("releted/{caseid}/productconsumed")
	public Page<Map<String, Object>> getProductConsumed(@PathVariable("caseid") long caseid, Pageable page) {
		return dbServiceAppoinmentProductConsumed.getProductConsumedByCaseId(caseid, page);
	}

	@PostMapping
	public BtCaseResponse saveBtCase(@RequestBody Map<String, Object> req, @CurrentUser LoginUser user)
			throws BadRequestException, UnauthoriseException {
		return btCaseService.saveDetails(req, user);
	}

	@GetMapping("{caseid}")
	public BtCaseResponse getByCaseId(@CurrentUser com.pratikshat.exicom.entity.LoginUser user,
			@PathVariable("caseid") long caseid) throws BadRequestException, UnauthoriseException {
		return btCaseService.getByCaseId(user, caseid);
	}

	@GetMapping
	public Page<Map<String, Object>> getPage(@CurrentUser LoginUser user, Pageable page,
			@RequestParam(value = "filtername", required = false) String filtername)
			throws BadRequestException, UnauthoriseException {
		return btCaseService.getAll(user, page, filtername);
	}

	@GetMapping("value")
	public Page<Map<String, Object>> getPageValue(@CurrentUser LoginUser user, Pageable page,
			@RequestParam(value = "filtername", required = false) String filtername)
			throws BadRequestException, UnauthoriseException {
		return btCaseService.getAll(user, page, filtername);
	}

	@PutMapping("{id}")
	public BtCaseResponse updateBtCase(@RequestBody Map<String, Object> reqq, @PathVariable("id") long id,
			@CurrentUser LoginUser user) throws BadRequestException, UnauthoriseException {
		return btCaseService.updateObjects(reqq, user);
	}

	@GetMapping("/advancesearch")
	public List<Map<String, Object>> advanceSearch(@RequestParam Map<String, Object> reqq, @CurrentUser LoginUser user,@RequestParam("size") int size,@RequestParam("page") int page)
			throws BadRequestException, UnauthoriseException {

		if (dbProfile.hasObjectAccess(user.getUsername(), BtCase.class, UserProfileRepository.VIEW_ALL))
			return btCaseService.advanceSearch(reqq, user,null,size,page);
		else if (dbProfile.hasObjectAccess(user.getUsername(), BtCase.class, UserProfileRepository.VIEW)) {
			
			
				return btCaseService.advanceSearch(reqq,user,dbProfile.getListOfUserAndChildByUser(user.getUsername()),size,page);
			
		}

		else

			throw new UnauthoriseException("You are not authorised");

	}

	@GetMapping("forlist")
	public List<Map<String, Object>> forList(@RequestParam("search") String search, @CurrentUser LoginUser user)
			throws BadRequestException, UnauthoriseException {
		return btCaseService.searchForList(search, user);
	}

	@GetMapping("casetype/forlist")
	public java.util.List<String> getCaseTypeList() {
		return dbCaseType.getBtCaseTypeList();
	}

	@GetMapping("casereason/forlist")
	public java.util.List<String> getCaseReasonList() {
		return dbCaseReason.getBtCaseReasonList();
	}

	@GetMapping("casestatus/forlist")
	public java.util.List<String> getCaseStatusList() {
		return dbCaseStatus.getBtCaseStatus();
	}

	@GetMapping("casestatus")
	public java.util.List<CaseStatusResponse> getStatus() {
		// return dbCaseStatus.getCaseStatusList();
		List<Integer> list = new ArrayList<>();
		list.add(1); // New
		list.add(2); // Closed
		list.add(3); // In-Process
		return dbCaseStatus.getCaseStatusList(list);
	}

	@GetMapping("casestage/forlist")
	public java.util.List<String> getCaseStageList() {
		return dbCaseStage.getBtCaseStageList();
	}

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
	
	 @GetMapping("casesubreason")
	    public List<Map<String,Object>> getCasesubReason(){
	        return btCaseService.getCasesubReason();
	    }
	

	@GetMapping("pendingresponsibility/forlist")
	public List<PendingResponsibility> getAll() {
		return dbPendingResponsibility.findAll();
	}

	@GetMapping("caseexpectedproblem/{id}")
	public java.util.List<BtCaseExpectedProblems> getCaseExpectedProblem(
			@PathVariable("id") int id) {
		return dbCaseExpectedProblem.findByBtProblemMaster_Id(id);
	}
	
	@GetMapping("problemMaster/forlist")
	public List<BtProblemMaster> getProblemMaster() {
		return dbproblem.getProblemMaster();
	}
	
	@GetMapping("casestage/{statusId}")
	public List<Map<String, Object>> getCaseStageByStatusId(@PathVariable("statusId") int statusId) {
		return dbCaseStage.getStageByStatusID(statusId);
	}

	@GetMapping("types/{verticalname}")
	public java.util.List<com.pratikshat.exicom.model.response.CaseTypesForList> getCaseTypes(
			@PathVariable("verticalname") int verticalname) {
		return dbCaseType.getByVertical(verticalname);
	}

	@GetMapping("caseforlist")
	public List<Map<String, Object>> caseForList() {
		List<String> list = new ArrayList<>();
		list.add("Closed");
		list.add("Rejected");
		return btCaseService.getCaseForList(list);
	}

	@GetMapping("reject")
	public List<Map<String, Object>> getRejectLog(
			@RequestParam(value = "caseManualId", required = false) String caseManualId, @CurrentUser LoginUser user,
			HttpServletResponse httpResponse) {
		return dbCaseRejectLog.getCaseRejectLogByCaseManualId(caseManualId);
	}

	@GetMapping("forlist/asstesType")
	public List<Map<String, Object>> getData() {
		return dbAssetsType.forListOfAssetsType();
	}

	@PostMapping("casefileupload/{caseid}")
	public String uploadFileForCase(@PathVariable("caseid") Long caseId, List<MultipartFile> files,
			@CurrentUser LoginUser user) throws BadRequestException, UnauthoriseException {
		dbFileService.saveFileList(caseId, files, user);
		return "{}";
	}

	@GetMapping("casefileupload/{caseid}/list")
	public List<Map<String, Object>> getUploadedFileList(@PathVariable("caseid") Long caseId,
			@CurrentUser LoginUser user) throws BadRequestException, UnauthoriseException {
		return dbFileService.getCaseFilesByBtCaseId(caseId, user);
	}

	@GetMapping("casefileupload/{caseid}/download/{uploadid}")
	public ResponseEntity<?> getUploadedFiles(@PathVariable("caseid") Long caseId,
			@PathVariable("uploadid") Long uploadId, @CurrentUser LoginUser user)
			throws BadRequestException, UnauthoriseException {

		Map<String, Object> upload = dbFileService.getSingle(uploadId, user);
		if (upload != null) {
			Resource resource = dbStorageService.loadFileAsResource(upload.get("physical_file_location").toString());
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION,
							"attachment; filename=\"" + upload.get("uploaded_file_name").toString() + "\"")
					.body(resource);
		}

		return null;
	}

	@GetMapping("bystage")
	public Page<Map<String, Object>> getCaseListByStage(@RequestParam(value = "stage", required = false) String stage,
			Pageable page, @RequestParam(value = "filtername", required = false) String filtername,
			@CurrentUser LoginUser user) throws BadRequestException, UnauthoriseException {
		return btCaseService.getCaseListByStage(stage, page, filtername, user);
	}

	@PutMapping("proofofdelivery")
	public Map<String, Object> updateCaseProofOfDelivey(
			@Valid @RequestBody Map<String, Object> caseProofOfDeliveryRequest, @CurrentUser LoginUser user)
			throws BadRequestException, UnauthoriseException {
		return btCaseService.updateProofOfDelivery(caseProofOfDeliveryRequest, user);
	}

	@PutMapping("/bulkuploadInvoice")
	public ResponseEntity<ByteArrayResource> mapReadExcelDatatoDB(@RequestParam("file") MultipartFile reapExcelDataFile,
			@CurrentUser com.pratikshat.exicom.entity.LoginUser user, HttpServletResponse httpResponse)
			throws IOException {
		String fileName = reapExcelDataFile.getOriginalFilename();

		if (fileName.substring(fileName.lastIndexOf('.') + 1).equals("xlsx")) {
			HttpHeaders header = new HttpHeaders();

			List<Map<String, Object>> outData = new ArrayList<Map<String, Object>>();
			ByteArrayOutputStream st = null;
			try {
				List<InvoiceUpdateRequest> requests = new InvoiceUpdateParser().parseIn(reapExcelDataFile);
				for (InvoiceUpdateRequest re : requests) {
					Map<String, Object> d = new HashMap<String, Object>();
					d.put("Case No", re.getCaseNumber());
					d.put("PO Number", re.getPONumber());
					d.put("PO Date (YYYY-MM-DD)", re.getPODate());
					d.put("Invoice Number", re.getInvoiceNumber());
					d.put("Invoice Date (YYYY-MM-DD)", re.getInvoiceDate());

					d.put("Vendor Invoice Number", re.getVendorInvoiceNumber());
					d.put("Vendor Invoice Date (YYYY-MM-DD)", re.getVendorInvoiceDate());
					try {
						btCaseService.updateInvoice(re, user);
						d.put("STATUS", "Success");
					} catch (Exception ex) {
						logger.error("error in Invoice bulk upload {}", ex);
						d.put("STATUS", "Error " + ex.getMessage());
					}
					outData.add(d);
				}

			} catch (Exception ex) {
				logger.error("error in Invoice bulk upload {}", ex);
				httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
				return null;
			}
			try {
				st = new ExcelParser().genrateFile(outData);
			} catch (Exception ex) {
				logger.error("error in Invoice bulk upload {}", ex);
				httpResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
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

	@PostMapping("/podBulkUpload")
	public ResponseEntity<ByteArrayResource> updateBulkPOD(@RequestParam("file") MultipartFile reapExcelDataFile,
			@CurrentUser com.pratikshat.exicom.entity.LoginUser user, HttpServletResponse httpResponse)
			throws IOException {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		String fileName = reapExcelDataFile.getOriginalFilename();

		if (fileName.substring(fileName.lastIndexOf('.') + 1).equals("xlsx")) {
			HttpHeaders header = new HttpHeaders();

			if (!dbProfile.hasObjectAccess(user.getUsername(), BtCase.class, UserProfileRepository.EDIT)) {
				httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
				return null;
			}

			List<Map<String, Object>> outData = new ArrayList<Map<String, Object>>();
			ByteArrayOutputStream st = null;
			try {
				List<PODUpdateRequest> requests = new PODParser().parseIn(reapExcelDataFile);
				for (PODUpdateRequest re : requests) {

					Map<String, Object> d = new HashMap<String, Object>();

					d.put("Case Number", re.getCaseNumber());
					d.put("Tracking Number", re.getPodNumber());
					d.put("Tracking Date (YYYY-MM-DD)", re.getPodDate());
					d.put("Shipping Mode", re.getPodShippingMode());
					d.put("Shipping To Name", re.getPodShippingToName());
					d.put("Shipping Expected Date (YYYY-MM-DD)", re.getPodShippingExpectedDate());

					try {

						CaseProofOfDeliveryRequest req = new CaseProofOfDeliveryRequest();

						BtCaseResponse caseDetails = dbCase.getByCaseManualId(re.getCaseNumber().trim());

						if (caseDetails == null) {
							throw new BadRequestException("Case does not exist");
						}

						req.setCaseId(caseDetails.getCaseId());
						req.setPodNumber(re.getPodNumber());
						req.setPodDate(re.getPodDate());
						req.setPodShippingMode(re.getPodShippingMode());
						req.setPodShippingToName(re.getPodShippingToName());
						req.setPodShippingTrackingNumber(re.getPodShippingTrackingNumber());
						req.setPodShippingExpectedDate(sdf.format(re.getPodShippingExpectedDate()));

						CaseProofOfDeliveryRequest request = btCaseService.updateProofOfDelivery(req, user);

						d.put("STATUS", "Success");
					} catch (Exception ex) {
						logger.error("error in pod bulk upload {}", ex);
						d.put("STATUS", "Error " + ex.getMessage());
					}
					outData.add(d);
				}

			} catch (Exception ex) {
				logger.error("error in pod bulk upload {}", ex);
				httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
				return null;
			}
			try {
				st = new ExcelParser().genrateFile(outData);
			} catch (Exception ex) {
				logger.error("error in pod bulk upload {}", ex);
				httpResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
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

	@PostMapping("/documentreceived/bulkupload")
	public com.pratikshat.exicom.model.response.BtCaseResponse documentReceivedBulkUpload(
			@RequestParam("file") MultipartFile documentRececivedBulkUpload, @CurrentUser LoginUser user,
			HttpServletResponse httpResponse) throws java.io.IOException {

		String fileName = documentRececivedBulkUpload.getOriginalFilename();

		if (fileName.substring(fileName.lastIndexOf('.') + 1).equals("xlsx")) {
			HttpHeaders header = new HttpHeaders();

			if (!dbProfile.hasObjectAccess(user.getUsername(), BtCase.class, UserProfileRepository.EDIT)) {
				httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorized");
				return null;
			}

			List<Map<String, Object>> outData = new ArrayList<Map<String, Object>>();
			ByteArrayOutputStream st = null;
			try {
				List<CaseDocumentReceivedRequest> requests = new CaseDocumentReceivedParser()
						.parseIn(documentRececivedBulkUpload);
				for (CaseDocumentReceivedRequest documentReceivedRequest : requests) {

					Map<String, Object> d = new HashMap<String, Object>();
					d.put("Case Number", documentReceivedRequest.getCaseManualId());
					try {

						if (documentReceivedRequest.getCaseManualId() == null
								|| documentReceivedRequest.getCaseManualId().equalsIgnoreCase("")) {
							httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Case Number is null");
							return null;
						}

						BtCaseResponse caseResponse = dbCase
								.getByCaseManualId(documentReceivedRequest.getCaseManualId().trim());

						if (caseResponse == null) {
							httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Case does not exist");
							return null;
						}

						btCaseService.documentReceived(caseResponse.getCaseId(), user);

						d.put("STATUS", "Success");

					} catch (Exception ex) {
						logger.error("error in case document received bulk upload {}", ex);
						d.put("STATUS", "Error " + ex.getMessage());
					}
					outData.add(d);
				}

			} catch (Exception ex) {
				logger.error("error in case document received bulk upload {}", ex);
				httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
				return null;
			}
			try {
				st = new ExcelParser().genrateFile(outData);
			} catch (Exception ex) {
				logger.error("error in case document received bulk upload {}", ex);
				httpResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
				return null;
			}

			httpResponse.addHeader(HttpHeaders.CONTENT_DISPOSITION,
					"attachment; filename=CaseDocumentReceviedResult.xlsx");
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

	@GetMapping("/templateExcel")
	public ResponseEntity<ByteArrayResource> GetExcel(HttpServletResponse httpResponse) throws IOException {
		HttpHeaders header = new HttpHeaders();
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("case");
		Object[][] bookData = { { "Asset Serial Number","Asset Part Code", "Site Id", "Caller Phone No", "Case Type", "Case Reason",
			"Status", "Stage", "Case Origin", "Priority",  "Pending Responsibility",
			"Problem Reported (seprated by ;)", "Number Of Battery Modules", "Other Problem Reported", "Remark","Problem Master","RJIL TT01 No"},};

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
					if(columnCount!=14&&columnCount!=17) {
						cell.setCellStyle(style);
					}
				} else if (field instanceof Integer) {
					cell.setCellValue((Integer) field);
					if(columnCount!=14&&columnCount!=17) {
						cell.setCellStyle(style);
					}
				}
			}

		}

		header.setContentType(new MediaType("application", "force-download"));
		header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + "btcase_template.xlsx");
		httpResponse.setContentType("application/xlsx");
		ByteArrayOutputStream bytest = new ByteArrayOutputStream();
		workbook.write(bytest);
		return new ResponseEntity<>(new ByteArrayResource(bytest.toByteArray()), header, HttpStatus.OK);
	}
	
	@GetMapping("/missingAssets")
	public ResponseEntity<ByteArrayResource> GetMissingExcel(HttpServletResponse httpResponse) throws IOException {
		HttpHeaders header = new HttpHeaders();
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("MissingAssets");
		Object[][] bookData = { { "Missing Asset","Site Id", "Customer Name", "Caller Phone No", "Case Type", "Case Reason","Work/Asset Type Choice",
			"Status", "Stage", "Case Origin", "Priority",  "Pending Responsibility","Problem Master",
			"Problem Reported (seprated by ;)","Remark", "Number of Battery Modules",
					 "Other Problem Reported", "RJIL TT01 No"},};

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
					if(columnCount!=17&&columnCount!=18) {
						cell.setCellStyle(style);
					}
				} else if (field instanceof Integer) {
					cell.setCellValue((Integer) field);
					if(columnCount!=17&&columnCount!=18) {
						cell.setCellStyle(style);
					}
				}
			}

		}

		header.setContentType(new MediaType("application", "force-download"));
		header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + "btcase_missing_template.xlsx");
		httpResponse.setContentType("application/xlsx");
		ByteArrayOutputStream bytest = new ByteArrayOutputStream();
		workbook.write(bytest);
		return new ResponseEntity<>(new ByteArrayResource(bytest.toByteArray()), header, HttpStatus.OK);
	}

	
	@PostMapping("/missingAssets")
	public ResponseEntity<ByteArrayResource> missingAssets(@RequestParam("file") MultipartFile reapExcelDataFile,
			@CurrentUser com.pratikshat.exicom.entity.LoginUser user, HttpServletResponse httpResponse)
			throws IOException {
		System.out.println(reapExcelDataFile.getOriginalFilename());
		String fileName = reapExcelDataFile.getOriginalFilename();

		if (fileName.substring(fileName.lastIndexOf('.') + 1).equals("xlsx")) {
			HttpHeaders header = new HttpHeaders();

			if (!dbProfile.hasObjectAccess(user.getUsername(), BtCase.class, UserProfileRepository.CREATE)) {
				httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
				return null;
			}

			List<Map<String, Object>> outData = new ArrayList<Map<String, Object>>();
			ByteArrayOutputStream st = null;
			try {
				List<BtCaseBulkRequest> requests = new BtCaseMissingParse().parseIn(reapExcelDataFile);
				for (BtCaseBulkRequest re : requests) {

					Map<String, Object> d = new HashMap<String, Object>();

					d.put("Missing Asset", re.getMissingAssets());
					d.put("Caller Phone No", re.getCallerPhoneNo());
					d.put("Customer Name", re.getCustomerName());
					d.put("Site Id", re.getSiteId());
					d.put("Work/Asset Type Choice", re.getAssetsTypeChoice());
					d.put("Case Type", re.getCaseTypeName());
					d.put("Case Reason", re.getCaseReasonName());
					d.put("Status", re.getStatusName());
					d.put("Stage", re.getStageName());
					d.put("Case Origin", re.getCaseOriginName());
					d.put("Priority", re.getPriorityName());
					d.put("Pending Responsibility", re.getPendingResponsibility());
					d.put("Problem Master", re.getProblemMaster());
					d.put("Problem Reported (seprated by ;)", re.getProblemsReported());
					d.put("Number of Battery Modules", re.getNoOfBatteryModules());
					d.put("Other Problem Reported", re.getOtherProblemReported());
					d.put("Remark", re.getRemark());
					d.put("RJIL TT01 No", re.getRJILTT01No());
					System.out.println(re.getMissingAssets());

					try {

						BtCaseRequest req = new BtCaseRequest();

						if (re.getMissingAssets() == null || re.getMissingAssets().trim().equals("")) {
							throw new BadRequestException("Missing Assets is null");
						}

						req.setMissingAssets(re.getMissingAssets().trim());
						
						if (re.getCustomerName()== null || re.getCustomerName().trim().equals("")) {
							throw new BadRequestException("Customer Name is null");
						}

						req.setCustomerName(re.getCustomerName().trim());

						if (re.getSiteId() == null || re.getSiteId().trim().equalsIgnoreCase("")) {
							throw new BadRequestException("Site id is null");
						}

						Map<String, Object> siteResponse = dbSite.getByManualSiteId(re.getSiteId().trim());
						if (siteResponse.isEmpty() || siteResponse== null) {
							throw new BadRequestException("Site does not exist");
						}
						req.setServiceCenterId(Long.parseLong(siteResponse.get("serviceCenterId").toString()));
						req.setServiceResourceId(Long.parseLong(siteResponse.get("serviceResourceId").toString()));
						req.setSiteId(Long.parseLong(siteResponse.get("siteId").toString()));
						req.setAssignByAssignmentRule(true);

						EmployeeDetails emp = dbDev.findByEmailId(user.getUsername());

						req.setCaseRecordTypeId(emp.getEmptype().getBusinessDomainId());

						if (re.getCallerPhoneNo() == null || re.getCallerPhoneNo().trim().equalsIgnoreCase("")) {
							throw new BadRequestException("Caller Phone no. is null");
						}

						try {
							ContactDetails conntact = dbContacts.getContactsByPhoneNoAndAccount(
									re.getCallerPhoneNo().trim(), siteResponse.get("accountSapNumber").toString());
							if (conntact == null) {
								throw new BadRequestException("Caller does not exist");
							}
							req.setContactPersonId(conntact.getContactId());
						} catch (NonUniqueResultException ex) {
							throw new BadRequestException("Multiple contact with the same contact no.");
						} catch (Exception ex) {
							throw new BadRequestException(ex.getMessage());
						}
						
						if (re.getCaseTypeName() == null || re.getCaseTypeName().trim().equalsIgnoreCase("")) {
							throw new BadRequestException("Case type is null");
						}
						
						CaseTypes caseType = dbCaseType.getByCaseTypesNameAndVertical(re.getCaseTypeName().trim(),
								emp.getEmptype().getBusinessDomainId());
						if (caseType == null) {
							throw new BadRequestException("Case type does not exist");
						}

						req.setCaseTypeId(caseType.getCaseTypeId());

						if (re.getCaseReasonName() == null || re.getCaseReasonName().trim().equalsIgnoreCase("")) {
							throw new BadRequestException("Case reason is null");
						}

						CaseReasonDetails caseReason = dbCaseReason.getByCaseTypeAndCaseReasonName(
								caseType.getCaseTypeId(), re.getCaseReasonName().trim(),
								emp.getEmptype().getBusinessDomainId());
						if (caseReason == null) {
							throw new BadRequestException("Case reason does not exist");
						}

						req.setCaseReasonId(caseReason.getReasonId());

						if (re.getStatusName() == null || re.getStatusName().trim().equalsIgnoreCase("")) {
							throw new BadRequestException("Case status is null");
						}

						CaseStatusDetails caseStatus = dbCaseStatus
								.getCaseStatusByCaseStatusName(re.getStatusName().trim());
						if (caseStatus == null) {
							throw new BadRequestException("Case status does not exist");
						}

						req.setCaseStatusId(caseStatus.getId());

						if (re.getStageName() == null || re.getStageName().trim().equalsIgnoreCase("")) {
							throw new BadRequestException("Case stage is null");
						}

						CaseStageDetails caseStage = dbCaseStage.getCaseStageByCaseStatusIdAndStageName(
								caseStatus.getId(), re.getStageName().trim(), emp.getEmptype().getBusinessDomainId());
						if (caseStage == null) {
							throw new BadRequestException("Case stage does not exist");
						}

						req.setCaseStageId(caseStage.getId());

						if (re.getCaseOriginName() == null || re.getCaseOriginName().trim().equalsIgnoreCase("")) {
							throw new BadRequestException("Case origin is null");
						}

						CaseOriginDetails caseOrigin = dbCaseOrigin
								.getCaseOriginByOriginName(re.getCaseOriginName().trim());
						if (caseOrigin == null) {
							throw new BadRequestException("Case origin does not exist");
						}

						req.setCaseOriginId(caseOrigin.getCaseOriginId());
						
						if (re.getPriorityName() == null || re.getPriorityName().trim().equalsIgnoreCase("")) {
							throw new BadRequestException("Case priority is null");
						}

						CasePriorityDetails casePriority = dbCasePriority
								.getCasePriorityByPriorityName(re.getPriorityName().trim());
						if (casePriority == null) {
							throw new BadRequestException("Case priority does not exist");
						}

						req.setCasePriorityId(casePriority.getCasePriorityId());
						
					

						if (re.getPendingResponsibility() == null
								|| re.getPendingResponsibility().trim().equalsIgnoreCase("")) {
							throw new BadRequestException("Pending responsibility is null");
						}

						PendingResponsibility pendingResponsibility = dbPendingResponsibility
								.getPendingResponsibilityByName(re.getPendingResponsibility().trim());
						if (pendingResponsibility == null) {
							throw new BadRequestException("Pending responsibility does not exist");
						}

						req.setPendingResponsibility(pendingResponsibility.getPendingResponsibilityId());

						if (re.getNoOfBatteryModules() != null && !re.getNoOfBatteryModules().trim().equalsIgnoreCase("")) {
							try {
								int num = Integer.parseInt(re.getNoOfBatteryModules().trim());
								req.setNoOfSmr(re.getNoOfBatteryModules().trim());
							} catch (NumberFormatException ex) {
								throw new BadRequestException("No. of SMR is not valid number");
							}

						}
						if(re.getAssetsTypeChoice()==null || re.getAssetsTypeChoice().trim().equalsIgnoreCase("")) {
							throw new BadRequestException("AssetsTypeChoice is null.");
						}
						BtAssetsTypeChoice assetsTypeChoice=dbChoice.findByName(re.getAssetsTypeChoice().trim());
						
						if(assetsTypeChoice==null) {
							throw new BadRequestException(" Asstes Type Choice is  not Found..");
						}
						req.setAssetsTypeChoice(assetsTypeChoice.getId());
						if (re.getOtherProblemReported() != null
								&& !re.getOtherProblemReported().trim().equalsIgnoreCase("")) {
							req.setOtherProblemReported(re.getOtherProblemReported().trim());
						}
						
						if (re.getRemark() == null && re.getRemark().trim().equalsIgnoreCase("")) {
							throw new BadRequestException("Remark is null");
						}
						req.setRemarks(re.getRemark().trim());
						if(re.getRJILTT01No()!=null && !re.getRJILTT01No().trim().equalsIgnoreCase("")) {
							req.setRJILTT01No(re.getRJILTT01No().trim());
						}
						req.setInputFromEng(null);

						if (re.getProblemsReported() == null || re.getProblemsReported().trim().equalsIgnoreCase("")) {
							throw new BadRequestException("Problem reported is null");
						}
						if(re.getProblemMaster()==null || re.getProblemMaster().trim().equalsIgnoreCase("")) {
							throw new BadRequestException("Problem Master is null");
						}
						BtProblemMaster mas= dbproblem.getByType(re.getProblemMaster().trim());
						if(mas==null) {
							throw new BadRequestException("Problem Master is not found.");
						}
						req.setProblemMaster(mas.getId());
						BtCaseProblemReported crp = new BtCaseProblemReported();
						String[] list2 = re.getProblemsReported().trim().split(";");
						Set<BtCaseProblemReported> caseProblemReported = new HashSet<BtCaseProblemReported>();
						for (String li2 : list2) {
							BtCaseProblemReported cr = new BtCaseProblemReported();
							if (li2.length() != 0) {
								cr.setProblemName(li2.trim());
							}
							caseProblemReported.add(cr);
						}
						req.setCaseProblemReported(caseProblemReported);
						
						caseService.saveDetailRequest(req,user);
						d.put("STATUS", "Success");
					} catch (Exception ex) {
						logger.error("error in case bulk upload {}", ex);
						d.put("STATUS", "Error " + ex.getMessage());
						d.put("Case Number", "");
					}
					outData.add(d);
				}

			} catch (Exception ex) {
				logger.error("error in case bulk upload {}", ex);
				httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
				return null;
			}
			try {
				st = new ExcelParser().genrateFile(outData);
			} catch (Exception ex) {
				logger.error("error in case bulk upload {}", ex);
				httpResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
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
	
	
	@PostMapping("/import")
	public ResponseEntity<ByteArrayResource> mapReapExcelDatatoDB(@RequestParam("file") MultipartFile reapExcelDataFile,
			@CurrentUser com.pratikshat.exicom.entity.LoginUser user, HttpServletResponse httpResponse)
			throws IOException {
		// System.out.println(reapExcelDataFile.getOriginalFilename());
		String fileName = reapExcelDataFile.getOriginalFilename();

		if (fileName.substring(fileName.lastIndexOf('.') + 1).equals("xlsx")) {
			HttpHeaders header = new HttpHeaders();

			if (!dbProfile.hasObjectAccess(user.getUsername(), BtCase.class, UserProfileRepository.CREATE)) {
				httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
				return null;
			}

			List<Map<String, Object>> outData = new ArrayList<Map<String, Object>>();
			ByteArrayOutputStream st = null;
			try {
				List<BtCaseBulkRequest> requests = new BtCaseParser().parseIn(reapExcelDataFile);
				for (BtCaseBulkRequest re : requests) {

					Map<String, Object> d = new HashMap<String, Object>();

					d.put("Asset Serial Number", re.getAssetSerialNumber());
					d.put("Assest Part Code", re.getAssetPartCode());
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
					d.put("Number Of Battery Modules", re.getNumberOfSMR());
					d.put("Other Problem Reported", re.getOtherProblemReported());
					d.put("Problem Master", re.getProblemMaster());
					d.put("Remark", re.getRemark());
					d.put("RJIL TT01 No", re.getRJILTT01No());

					try {
						
						BtCaseRequest req = new BtCaseRequest();

						if (re.getAssetSerialNumber() == null || re.getAssetSerialNumber().trim().equals("")) {
							throw new BadRequestException("Asset serial no. is null");
						}

						req.setAssetsSerialNumber(re.getAssetSerialNumber().trim());
						
						if (re.getAssetPartCode() == null || re.getAssetPartCode().trim().equals("")) {
							throw new BadRequestException("Asset part code is null");
						}

						req.setAssetsPartCode(re.getAssetPartCode().trim());

						if (re.getSiteId() == null || re.getSiteId().trim().equalsIgnoreCase("")) {
							throw new BadRequestException("Site id is null");
						}

						Map<String, Object> siteResponse = dbSite.getByManualSiteId(re.getSiteId().trim());
						if (siteResponse == null) {
							throw new BadRequestException("Site does not exist");
						}

						req.setSiteId(Long.parseLong(siteResponse.get("siteId").toString()));
						req.setServiceCenterId(Long.parseLong(siteResponse.get("serviceCenterId").toString()));
						req.setServiceResourceId(Long.parseLong(siteResponse.get("serviceResourceId").toString()));
						req.setAssignByAssignmentRule(true);

						EmployeeDetails emp = dbDev.findByEmailId(user.getUsername());

						req.setCaseRecordTypeId(emp.getEmptype().getBusinessDomainId());

						if (re.getCallerPhoneNo() == null || re.getCallerPhoneNo().trim().equalsIgnoreCase("")) {
							throw new BadRequestException("Caller Phone no. is null");
						}
						
						try {
							ContactDetails conntact = dbContacts.getContactsByPhoneNoAndAccountForBat(
									re.getCallerPhoneNo().trim(), siteResponse.get("accountSapNumber").toString());
							if (conntact == null) {
								throw new BadRequestException("Caller does not exist");
							}
							req.setContactPersonId(conntact.getContactId());
						} catch (NonUniqueResultException ex) {
							throw new BadRequestException("Multiple contact with the same contact no.");
						} catch (Exception ex) {
							throw new BadRequestException(ex.getMessage());
						}

						if (re.getCaseTypeName() == null || re.getCaseTypeName().trim().equalsIgnoreCase("")) {
							throw new BadRequestException("Case type is null");
						}

						CaseTypes caseType = dbCaseType.getByCaseTypesNameAndVertical(re.getCaseTypeName().trim(),
								emp.getEmptype().getBusinessDomainId());
						if (caseType == null) {
							throw new BadRequestException("Case type does not exist");
						}

						req.setCaseTypeId(caseType.getCaseTypeId());

						if (re.getCaseReasonName() == null || re.getCaseReasonName().trim().equalsIgnoreCase("")) {
							throw new BadRequestException("Case reason is null");
						}

						CaseReasonDetails caseReason = dbCaseReason.getByCaseTypeAndCaseReasonName(
								caseType.getCaseTypeId(), re.getCaseReasonName().trim(),
								emp.getEmptype().getBusinessDomainId());
						if (caseReason == null) {
							throw new BadRequestException("Case reason does not exist");
						}

						req.setCaseReasonId(caseReason.getReasonId());

						if (re.getStatusName() == null || re.getStatusName().trim().equalsIgnoreCase("")) {
							throw new BadRequestException("Case status is null");
						}

						CaseStatusDetails caseStatus = dbCaseStatus
								.getCaseStatusByCaseStatusName(re.getStatusName().trim());
						if (caseStatus == null) {
							throw new BadRequestException("Case status does not exist");
						}

						req.setCaseStatusId(caseStatus.getId());

						if (re.getStageName() == null || re.getStageName().trim().equalsIgnoreCase("")) {
							throw new BadRequestException("Case stage is null");
						}

						CaseStageDetails caseStage = dbCaseStage.getCaseStageByCaseStatusIdAndStageName(
								caseStatus.getId(), re.getStageName().trim(), emp.getEmptype().getBusinessDomainId());
						if (caseStage == null) {
							throw new BadRequestException("Case stage does not exist");
						}

						req.setCaseStageId(caseStage.getId());

						if (re.getCaseOriginName() == null || re.getCaseOriginName().trim().equalsIgnoreCase("")) {
							throw new BadRequestException("Case origin is null");
						}

						CaseOriginDetails caseOrigin = dbCaseOrigin
								.getCaseOriginByOriginName(re.getCaseOriginName().trim());
						if (caseOrigin == null) {
							throw new BadRequestException("Case origin does not exist");
						}

						req.setCaseOriginId(caseOrigin.getCaseOriginId());
						

						if (re.getPriorityName() == null || re.getPriorityName().trim().equalsIgnoreCase("")) {
							throw new BadRequestException("Case priority is null");
						}

						CasePriorityDetails casePriority = dbCasePriority
								.getCasePriorityByPriorityName(re.getPriorityName().trim());
						if (casePriority == null) {
							throw new BadRequestException("Case priority does not exist");
						}

						req.setCasePriorityId(casePriority.getCasePriorityId());
						
						

						if (re.getPendingResponsibility() == null
								|| re.getPendingResponsibility().trim().equalsIgnoreCase("")) {
							throw new BadRequestException("Pending responsibility is null");
						}

						PendingResponsibility pendingResponsibility = dbPendingResponsibility
								.getPendingResponsibilityByName(re.getPendingResponsibility().trim());
						if (pendingResponsibility == null) {
							throw new BadRequestException("Pending responsibility does not exist");
						}

						req.setPendingResponsibility(pendingResponsibility.getPendingResponsibilityId());
						if(re.getNumberOfSMR()==null && re.getNumberOfSMR().toString().equalsIgnoreCase("")) {
							throw new BadRequestException("Number Of Battery Module is null...");
						}
						if (re.getNumberOfSMR() != null && !re.getNumberOfSMR().trim().equalsIgnoreCase("")) {
							try {
								int num = Integer.parseInt(re.getNumberOfSMR().trim());
								req.setNoOfSmr(re.getNumberOfSMR().trim());
							} catch (NumberFormatException ex) {
								throw new BadRequestException("No. of Battery Module is not valid number");
							}
						}

						if (re.getOtherProblemReported() != null
								&& !re.getOtherProblemReported().trim().equalsIgnoreCase("")) {
							req.setOtherProblemReported(re.getOtherProblemReported().trim());
						}

						if (re.getRemark() != null && !re.getRemark().trim().equalsIgnoreCase("")) {
							req.setRemarks(re.getRemark().trim());
						}
						if(re.getRJILTT01No()!=null && !re.getRJILTT01No().trim().equalsIgnoreCase("")) {
							req.setRJILTT01No(re.getRJILTT01No().trim());
						}
						System.out.println("RJIL Comment" + req.getRJILTT01No());
						System.out.println("richa.shukla@exicom.i IS "+ req.getRJILTT01No());
						req.setMissingAssets(null);
						req.setInputFromEng(null);
						if(re.getProblemMaster()==null || re.getProblemMaster().trim().equalsIgnoreCase("")) {
							throw new BadRequestException("Problem Master is null");
						}
						BtProblemMaster mas= dbproblem.getByType(re.getProblemMaster().trim());
						if(mas==null) {
							throw new BadRequestException("Problem Master is not found.");
						}
						req.setProblemMaster(mas.getId());
						System.out.println(mas.getId()+" problem master");
						if (re.getProblemsReported() == null || re.getProblemsReported().trim().equalsIgnoreCase("")) {
							throw new BadRequestException("Problem reported is null");
						}
						BtCaseProblemReported crp = new BtCaseProblemReported();
						String[] list2 = re.getProblemsReported().trim().split(";");
						Set<BtCaseProblemReported> caseProblemReported = new HashSet<BtCaseProblemReported>();
						for (String li2 : list2) {
							BtCaseProblemReported cr = new BtCaseProblemReported();
							if (li2.length() != 0) {
								cr.setProblemName(li2.trim());
							}
							caseProblemReported.add(cr);
						}
						req.setCaseProblemReported(caseProblemReported);
						
						caseService.saveDetailRequest(req,user);
						d.put("STATUS", "Success");
					} catch (Exception ex) {
						logger.error("error in case bulk upload {}", ex);
						d.put("STATUS", "Error " + ex.getMessage());
						d.put("Case Number", "");
					}
					outData.add(d);
				}

			} catch (Exception ex) {
				logger.error("error in case bulk upload {}", ex);
				httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
				return null;
			}
			try {
				st = new ExcelParser().genrateFile(outData);
			} catch (Exception ex) {
				logger.error("error in case bulk upload {}", ex);
				httpResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
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
					if (columnCount != 4 && columnCount != 5) {
						cell.setCellStyle(style);
					}
				} else if (field instanceof Integer) {
					cell.setCellValue((Integer) field);
					if (columnCount != 4 && columnCount != 5) {
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

	@GetMapping("/documentreceived/template")
	public ResponseEntity<ByteArrayResource> caseDocumentReceivedTemplate(HttpServletResponse httpResponse)
			throws IOException {
		HttpHeaders header = new HttpHeaders();
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("case_document_received");
		Object[][] bookData = { { "Case Number" }, };

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

	@GetMapping("/templateExcelInvoice")
	public ResponseEntity<ByteArrayResource> GetExcelInvoice(HttpServletResponse httpResponse) throws IOException {
		HttpHeaders header = new HttpHeaders();
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Excel Template caseInviceUpdate");
		Object[][] bookData = { { "Case No", "PO Number", "PO Date (YYYY-MM-DD)", "Invoice Number",
				"Invoice Date (YYYY-MM-DD)", "Vendor Invoice Number", "Vendor Invoice Date (YYYY-MM-DD)" },

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
					if (columnCount == 1) {
						cell.setCellStyle(style);
					}
				} else if (field instanceof Integer) {
					cell.setCellValue((Integer) field);
					if (columnCount == 1) {
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

	@GetMapping("/podTemplate")
	public ResponseEntity<ByteArrayResource> getPODTemplate(HttpServletResponse httpResponse) throws IOException {
		HttpHeaders header = new HttpHeaders();
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("POD");
		Object[][] bookData = { { "Case Number", "Tracking Number", "Tracking Date (YYYY-MM-DD)", "Shipping Mode" }, };

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

	@GetMapping("/caseRejectTemplate")
	public ResponseEntity<ByteArrayResource> getCaseRejectTemplate(HttpServletResponse httpResponse)
			throws IOException {
		HttpHeaders header = new HttpHeaders();
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("case_reject");
		Object[][] bookData = { { "Case Number", "Remark" }, };

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

	@PutMapping("changeCaseTypeSingle")
	public BtCaseResponse changeCaseTypeSingle(@RequestBody ChangeCaseTypeRequest request,
			@CurrentUser com.pratikshat.exicom.entity.LoginUser user, HttpServletResponse httpResponse)
			throws IOException {

		if (!dbProfile.hasObjectAccess(user.getUsername(), ChangeCaseTypeLog.class, UserProfileRepository.CREATE)
				|| !dbProfile.hasObjectAccess(user.getUsername(), ChangeCaseTypeLog.class,
						UserProfileRepository.EDIT)) {
			httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorise");
			return null;
		}

		try {

			if (request.getCaseManualId() == null || request.getCaseManualId().trim().equalsIgnoreCase("")) {
				httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Case Id is null");
				return null;
			}

			if (request.getCaseType() == null || request.getCaseType().trim().equalsIgnoreCase("")) {
				httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Case Type is blank");
				return null;
			}

			if (request.getCaseReason() == null || request.getCaseReason().trim().equalsIgnoreCase("")) {
				httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Case Reason is null");
				return null;
			}

			if (request.getCaseType().equalsIgnoreCase("Under AMC")) {
				if (request.getPoNo() == null || request.getPoNo().trim().equalsIgnoreCase("")) {
					httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "PO No. is blank");
					return null;
				}

				if (request.getPoDate() == null) {
					httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "PO Date is blank");
					return null;
				}
			}

			BtCase _caseDetails = dbCase.findByCaseManualId(request.getCaseManualId());
			if (_caseDetails == null) {
				httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Case does not exist");
				return null;
			} else {

				ChangeCaseTypeRequest _caseRequest = btCaseService.changeCaseType(request, user);

				com.pratikshat.exicom.model.response.BtCaseResponse _case = dbCase
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

	@PostMapping("changeCaseType")
	public ResponseEntity<?> bulkUdpated(@RequestParam(value = "file") MultipartFile file, @CurrentUser LoginUser user,
			HttpServletResponse response) throws IOException {
		if (!dbProfile.hasObjectAccess(user.getUsername(), BtChangeCaseTypeLog.class, UserProfileRepository.CREATE)
				|| !dbProfile.hasObjectAccess(user.getUsername(), BtChangeCaseTypeLog.class,
						UserProfileRepository.EDIT)) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorise");
			return null;
		}
		List<Map<String, Object>> outData = new ArrayList<Map<String, Object>>();
		ByteArrayOutputStream st = null;
		try {
			List<ChangeCaseTypeRequest> requests = new ChangeCaseTypeParser().parseIn(file);
			for (ChangeCaseTypeRequest re : requests) {
				Map<String, Object> d = new HashMap<String, Object>();
				d.put("Case Number", re.getCaseManualId());
				d.put("Case type", re.getCaseType());
				d.put("Case reason", re.getCaseReason());
				d.put("Po Number", re.getPoNo());
				d.put("Po Date (YYYY-MM-DD)", re.getPoDate());
				try {
					btCaseService.changeCaseType(re, user);
					d.put("STATUS", "Success");
				} catch (Exception ex) {
					d.put("STATUS", "Error " + ex.getMessage());
				}
				outData.add(d);
			}

		} catch (Exception ex) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
			return null;
		}
		try {
			st = new ExcelParser().genrateFile(outData);
		} catch (Exception ex) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
			return null;
		}
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=customerinventorystatus.xlsx");

		response.addHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=ChangeCaseTypeResult.xlsx");
		response.setContentType("application/vnd.ms-excel");
		response.getOutputStream().write(st.toByteArray());
		response.getOutputStream().flush();
		response.getOutputStream().close();
		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_OCTET_STREAM).body(st);
	}

	@GetMapping("changeCaseTypeSingle/caseType")
	public List<String> changeCaseTypeSingleCaseType(@RequestParam("existing_case_type") String existing_case_type,
			@RequestParam("existing_case_reason") String existing_case_reason, @CurrentUser LoginUser user,
			HttpServletResponse httpResponse) throws IOException {

		if (!dbProfile.hasObjectAccess(user.getUsername(), ChangeCaseTypeLog.class, UserProfileRepository.CREATE)
				|| !dbProfile.hasObjectAccess(user.getUsername(), ChangeCaseTypeLog.class,
						UserProfileRepository.EDIT)) {
			httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorise");
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

		List<Map<String, Object>> newCaseType = dbChangeCaseType.getNewCaseType(existing_case_type,
				existing_case_reason);

		List<String> listOfValue = new ArrayList<String>();
		if (!newCaseType.isEmpty()) {
			for (Map map : newCaseType) {
				listOfValue.addAll(map.values());
			}
		}

		return listOfValue;

	}

	@GetMapping("changeCaseTypeSingle/caseReason")
	public List<String> changeCaseTypeSingleCaseReason(@RequestParam("existing_case_type") String existing_case_type,
			@RequestParam("existing_case_reason") String existing_case_reason,
			@RequestParam("new_case_type") String new_case_type, @CurrentUser LoginUser user,
			HttpServletResponse httpResponse) throws IOException {

		if (!dbProfile.hasObjectAccess(user.getUsername(), ChangeCaseTypeLog.class, UserProfileRepository.CREATE)
				|| !dbProfile.hasObjectAccess(user.getUsername(), ChangeCaseTypeLog.class,
						UserProfileRepository.EDIT)) {
			httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorise");
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

		List<Map<String, Object>> newCaseReason = dbChangeCaseType.getNewCaseReasonByNewCaseType(existing_case_type,
				existing_case_reason, new_case_type);

		List<String> listOfValue = new ArrayList<String>();
		if (!newCaseReason.isEmpty()) {
			for (Map map : newCaseReason) {
				listOfValue.addAll(map.values());
			}
		}

		return listOfValue;
	}

	@PutMapping("invoice")
	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = {
			BadRequestException.class, UnauthoriseException.class, RuntimeException.class, Exception.class })
	public BtCaseResponse updateCaseInvoice(@RequestBody CaseInvoiceRequest caseInvoiceRequest,
			@CurrentUser LoginUser user) throws BadRequestException, UnauthoriseException {
		BtCase caseDetails = dbCase.getAppointment(caseInvoiceRequest.getCaseId());

		if (caseDetails == null) {
			throw new BadRequestException("Case is not exits");
		}

		List<Integer> list = new ArrayList<>();
		list.add(21); // Document in-Transit - Battery
		list.add(22); // Document in-Transit - DCT
		list.add(3); // To Be Invoiced - Battery
		list.add(9); // To Be Invoiced - DCT
		list.add(4); // Invoiced - Battery
		list.add(10); // Invoiced - DCT

		if (!list.contains(caseDetails.getStage().getId())) {
			throw new BadRequestException("You can't update this record");
		}

		if (caseInvoiceRequest.getPoDate() != null && caseInvoiceRequest.getPoNumber() != null
				&& !caseInvoiceRequest.getPoNumber().equalsIgnoreCase("")) {
			caseDetails.setPODate(caseInvoiceRequest.getPoDate());
			caseDetails.setPONumber(caseInvoiceRequest.getPoNumber());

		}

		if (caseInvoiceRequest.getInvoiceNumber() != null && !caseInvoiceRequest.getInvoiceNumber().equalsIgnoreCase("")
				&& caseInvoiceRequest.getInvoiceDate() != null) {
			caseDetails.setInvoiceNumber(caseInvoiceRequest.getInvoiceNumber());
			caseDetails.setInvoiceDate(caseInvoiceRequest.getInvoiceDate());

			if (caseDetails.getBusinessDomain().getBusinessDomainId() == 1) { // Battery
				caseDetails.setStage(new CaseStageDetails(4)); // Invoiced
			}
		}

		if (caseInvoiceRequest.getVendorInvoiceNumber() != null
				&& !caseInvoiceRequest.getVendorInvoiceNumber().equalsIgnoreCase("")
				&& caseInvoiceRequest.getVendorInvoiceDate() != null) {
			caseDetails.setVendorInvoiceNumber(caseInvoiceRequest.getVendorInvoiceNumber());
			caseDetails.setVendorInvoiceDate(caseInvoiceRequest.getVendorInvoiceDate());
		}
		dbCase.updateDetails(caseDetails, user);
		BtCaseResponse _case = dbCase.getByCaseId(caseDetails.getId()); 
		if (_case != null) {
			_case.setProblemReported(dbCaseProblem.getByCaseId(_case.getCaseId()));
		}
		return _case;
	}

	@PutMapping("reject")
	public BtCaseResponse rejectCase(@RequestBody Map<String, Object> rejectCaseRequest,
			@CurrentUser com.pratikshat.exicom.entity.LoginUser user, HttpServletResponse httpResponse)
			throws IOException {

		if (!dbProfile.hasObjectAccess(user.getUsername(), BtCaseRejectLog.class, UserProfileRepository.CREATE)
				|| !dbProfile.hasObjectAccess(user.getUsername(), BtCaseRejectLog.class, UserProfileRepository.EDIT)) {
			httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorized");
			return null;
		}

		try {

			if (rejectCaseRequest.get("caseManualId") == null
					|| rejectCaseRequest.get("caseManualId").toString().equalsIgnoreCase("")) {
				httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Case Id is null");
				return null;
			}

			if (rejectCaseRequest.get("remark") == null
					|| rejectCaseRequest.get("remark").toString().trim().equalsIgnoreCase("")) {
				httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Remark is blank");
				return null;
			}

			BtCase _caseDetails = dbCase.getCaseForInvoiceUpdate(rejectCaseRequest.get("caseManualId").toString());
			if (_caseDetails == null) {
				httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Case does not exist");
				return null;
			} else {

				Map<String, Object> _caseRequest = btCaseService.btCaseReject(rejectCaseRequest, user);

				BtCaseResponse _case = dbCase.getByCaseManualId(rejectCaseRequest.get("caseManualId").toString());
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

	@PostMapping("/caseRejectBulkUpload")
	public ResponseEntity<ByteArrayResource> rejectCaseBulkUpload(@RequestParam("file") MultipartFile reapExcelDataFile,
			@CurrentUser com.pratikshat.exicom.entity.LoginUser user, HttpServletResponse httpResponse)
			throws IOException {

		String fileName = reapExcelDataFile.getOriginalFilename();

		if (fileName.substring(fileName.lastIndexOf('.') + 1).equals("xlsx")) {
			HttpHeaders header = new HttpHeaders();

			if (!dbProfile.hasObjectAccess(user.getUsername(), BtCaseRejectLog.class, UserProfileRepository.CREATE)
					|| !dbProfile.hasObjectAccess(user.getUsername(), BtCaseRejectLog.class,
							UserProfileRepository.EDIT)) {
				httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorized");
				return null;
			}

			List<Map<String, Object>> outData = new ArrayList<Map<String, Object>>();
			ByteArrayOutputStream st = null;
			try {
				List<RejectCaseRequest> requests = new CaseRejectParser().parseIn(reapExcelDataFile);
				for (RejectCaseRequest rejectCaseRequest : requests) {

					Map<String, Object> d = new HashMap<String, Object>();

					d.put("Case Number", rejectCaseRequest.getCaseManualId());
					d.put("Remark", rejectCaseRequest.getRemark());

					try {

						if (rejectCaseRequest.getCaseManualId() == null
								|| rejectCaseRequest.getCaseManualId().equalsIgnoreCase("")) {
							throw new BadRequestException( "Case manual id is null");
							
						}

						if (rejectCaseRequest.getRemark() == null
								|| rejectCaseRequest.getRemark().trim().equalsIgnoreCase("")) {
							httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Remark is blank");
							return null;
						}

						BtCase _caseDetails = dbCase.findByCaseManualId(rejectCaseRequest.getCaseManualId().trim());
						if (_caseDetails == null) {
							httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Case does not exist");
							return null;
						}

						RejectCaseRequest _caseRequest = dbCase.rejectCase(rejectCaseRequest, user);
						d.put("STATUS", "Success");

					} catch (Exception ex) {
						logger.error("error in case reject bulk upload {}", ex);
						d.put("STATUS", "Error " + ex.getMessage());
					}
					outData.add(d);
				}

			} catch (Exception ex) {
				logger.error("error in case reject bulk upload {}", ex);
				httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
				return null;
			}
			try {
				st = new ExcelParser().genrateFile(outData);
			} catch (Exception ex) {
				logger.error("error in case reject bulk upload {}", ex);
				httpResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
				return null;
			}

			httpResponse.addHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=BtCaseRejectResult.xlsx");
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
//shagufta
//	@GetMapping(value = "/pdf")
//	public ResponseEntity<?> createPdf(@RequestParam("caseid")long caseid,HttpServletResponse httpServletResponse,@CurrentUser LoginUser user) throws Exception {
//		try {
//			BtCaseResponse _caseres=btCaseService.getByCaseId(user, caseid);
//			System.out.println(caseid);
//			
//			if(_caseres ==null) {
//				httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST,"Field Service Report does not exist");
//			}
//			System.out.println(_caseres.getCaseManualId());
//			ByteArrayInputStream bis=btCaseService.generatePdf(user,caseid);			
//			
//			HttpHeaders headers = new HttpHeaders();
//			headers.add("Content-Disposition", "inline; filename="+_caseres.getCaseManualId()+".pdf");
//			return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(new InputStreamResource(bis));
//		} catch(BadRequestException ex) {
//			ex.printStackTrace();
//			httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
//			return null;
//		}catch(UnauthoriseException ex) {
//			ex.printStackTrace();
//			httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN, ex.getMessage());
//			return null;
//		}
//	}
	

}
