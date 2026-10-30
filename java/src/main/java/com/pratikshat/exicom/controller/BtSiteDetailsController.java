
package com.pratikshat.exicom.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
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
import com.pratikshat.exicom.entity.BtSiteDetails;
import com.pratikshat.exicom.entity.BulkUploadAction;
import com.pratikshat.exicom.entity.Bulkupload;
import com.pratikshat.exicom.entity.BulkuploadFileDetails;
import com.pratikshat.exicom.entity.CircleDetails;
import com.pratikshat.exicom.entity.GlobalSearchIndex;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.SSA;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.LegalEntityResponse;
import com.pratikshat.exicom.model.response.ServiceResourseResponse;
import com.pratikshat.exicom.model.response.WebMessage;
import com.pratikshat.exicom.repository.BtAssetsRepository;
import com.pratikshat.exicom.repository.BtCaseRepository;
import com.pratikshat.exicom.repository.BtServiceAppointmentRepository;
import com.pratikshat.exicom.repository.BtSiteDetailsRepository;
import com.pratikshat.exicom.repository.GlobalSearchIndexRepository;
import com.pratikshat.exicom.repository.LegalEntityRepository;
import com.pratikshat.exicom.repository.SSARepository;
import com.pratikshat.exicom.repository.ServiceRecourseRepository;
import com.pratikshat.exicom.repository.SiteDetailsRepository;
import com.pratikshat.exicom.repository.UserProfileRepository;
import com.pratikshat.exicom.services.BtSiteDetailsService;
import com.pratikshat.exicom.services.BulkuploadService;
import com.pratikshat.exicom.utils.ExcelParser;

//Author- Deepak Pnetha  
//Created date -31 may 2021

@RestController
@RequestMapping("bt/api/sitedetails")
public class BtSiteDetailsController {
	@Autowired
	BtSiteDetailsService btSiteService;
	@Autowired
	BulkuploadService dbBulkUploadService;
	@Value("${app.bulkupload.location}")
	String bulkuploadLocation;
	@Autowired
	SimpMessagingTemplate websocket;
	@Autowired
	GlobalSearchIndexRepository dbGlobalSearch;
	@Autowired
	BtSiteDetailsRepository dbBtSite;
	@Autowired
	com.pratikshat.exicom.repository.UserProfileRepository dbProfile;
	@Autowired
	LegalEntityRepository dbLegalEntity;
	@Autowired
	ServiceRecourseRepository dbServiceResource;
	@PersistenceContext
	javax.persistence.EntityManager entityManager;
	@Autowired
	BtCaseRepository dbCase;
	@Autowired
	BtServiceAppointmentRepository dbService;
	@Autowired
	BtAssetsRepository dbAsset;
	@Autowired
	SSARepository dbSSA;

	
/////////////////fetching all Site details///////////////
	@GetMapping
	Page<Map<String, Object>> getSiteDetails(Pageable page, @CurrentUser LoginUser user)
			throws BadRequestException, UnauthoriseException {
		return btSiteService.getPage(page, user);
	}

	////////////////// Find By site Id/////
	@GetMapping("{siteid}")
	Map<String, Object> getByBatteryStieId(@PathVariable("siteid") long siteid, @CurrentUser LoginUser user)
			throws BadRequestException, UnauthoriseException {
		return btSiteService.getSingle(siteid, user);
	}

	///////////////////// Creating Sites according
	///////////////////// conditions/////////////////////////////
	@PostMapping
	private Map<String, Object> saveBtSiteDetail(@RequestBody Map<String, Object> req, @CurrentUser LoginUser user)
			throws BadRequestException, UnauthoriseException {
		return btSiteService.saveObject(req, user);
	}

	////////////////////// Update Existing Site Details /////////////////////
	@PutMapping()
	private Map<String, Object> updateSiteDetail(@RequestBody Map<String, Object> reqq, @CurrentUser LoginUser user)
			throws BadRequestException, UnauthoriseException {
		return btSiteService.updateObject(reqq, user);
	}

	///////////////// Advance Search According Multiple fields
	///////////////// value///////////////////
	@GetMapping("/advancesearch")
	private List<Map<String, Object>> advanceSearchForSiteDetails(@RequestParam Map<String, Object> searchKey,
			@CurrentUser LoginUser user) throws BadRequestException, UnauthoriseException {
		return btSiteService.advanceSearch(searchKey, user);
	}

///////////////// Search According Single  fields value///////////////////
	@GetMapping("forlist")
	private List<Map<String, Object>> forListSite(@RequestParam("search") String search, @CurrentUser LoginUser user)
			throws BadRequestException, UnauthoriseException {
		return btSiteService.searchForList(search, user);
	}

	//////// Download Templete///////////////////
	@GetMapping("/downloadTemplet")
	public ResponseEntity<ByteArrayResource> downloadBulkTemplate(HttpServletResponse httpResponse) throws IOException {
		HttpHeaders header = new HttpHeaders();
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("site");
		Object[][] bookData = { { "Exchange/Battery Site Name", "Battery Site Id",
				 "Battery Site Description", "Battery Circle", "Battery SSA",
				"Battery Cluster/District", "Battery Account SAP Number", "Battery Customer", "Battery Service Center",
				"Battery Service Resource", "Battery Infra Id", "Battery Site Address", "Rjil JC Name" ,"CMP" }, };
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
					if(columnCount!=11&& columnCount!=12 && columnCount!=13 && columnCount!=14) {
						cell.setCellStyle(style);
					}
				} else if (field instanceof Integer) {
					cell.setCellValue((Integer) field);
					if(columnCount!=11&& columnCount!=12 && columnCount!=13 && columnCount!=14) {
						cell.setCellStyle(style);
					}
				}
			}
		}
		header.setContentType(new MediaType("application", "force-download"));
		header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + "battery_site_template.xlsx");
		httpResponse.setContentType("application/xlsx");
		ByteArrayOutputStream bytest = new ByteArrayOutputStream();
		workbook.write(bytest);
		return new ResponseEntity<>(new ByteArrayResource(bytest.toByteArray()), header, HttpStatus.OK);
	}

//////////////////////Bulk Upload Site data Templete/////////////////////
	@PostMapping("/siteBulkupload")
	public Map<String, Object> uploadData(@RequestParam(value = "file") MultipartFile file, @CurrentUser LoginUser user)
			throws BadRequestException, UnauthoriseException {

		return uploadDataBatterySite(file, user);

	}

	public Map<String, Object> uploadDataBatterySite(MultipartFile file, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		// TODO Auto-generated method stub

		String fileName = file.getOriginalFilename();
		if (!fileName.substring(fileName.lastIndexOf('.') + 1).equals("xlsx")) {
			throw new com.pratikshat.exicom.exception.BadRequestException("file not excel");
		}
		Map<String, Object> resp = new HashMap();
		try {
			BulkuploadFileDetails inPutFile = new BulkuploadFileDetails();
			inPutFile.setFileType(file.getContentType());
			inPutFile.setSize(file.getSize());
			inPutFile.setFileName(file.getOriginalFilename());
			dbBulkUploadService.saveBulkuploadDocument(inPutFile, user);
			String finalPath = bulkuploadLocation + File.separator + inPutFile.getId() + "_"
					+ file.getOriginalFilename();
			Files.copy(file.getInputStream(), Paths.get(finalPath));
			// file.transferTo(new File(finalPath));
			inPutFile.setPhysicalPath(finalPath);
			inPutFile.setSize(file.getSize());
			dbBulkUploadService.updateFilePath(inPutFile, user, inPutFile.getId());

			// BULK UPLOAD ACTION
			BulkUploadAction actn = new BulkUploadAction();
			actn.setBulkUpload(new Bulkupload(2L));
			actn.setCreatedDate(new Date());
			actn.setRequestDocumentId(inPutFile.getId());
			actn.setUploadStatus("Processing");
			dbBulkUploadService.saveBulkuploadAction(actn, user, 6L);

			//////////////// Call diffrent Thread////////////////////////
			List<Map<String, Object>> btSiteData = parseSiteDataBattery(new File(finalPath));
			new BatterySiteBulkUpload(btSiteData, user, actn, file.getOriginalFilename()).start();
			/////// Get Back Response///////////
			resp.put("message", "Your file has been received and started processing in background");
			resp.put("status", "1");
			return resp;
		} catch (Exception ex) {
			ex.printStackTrace();
			resp.put("message", ex.getMessage());
			resp.put("status", "0");
			return resp;
		}
	}

	public class BatterySiteBulkUpload extends Thread {
		List<Map<String, Object>> requests;
		LoginUser user;
		BulkUploadAction actn;
		String fileName;

		public BatterySiteBulkUpload(List<Map<String, Object>> requests, LoginUser user, BulkUploadAction actn,
				String fileName) {
			this.requests = requests;
			this.user = user;
			this.actn = actn;
			this.fileName = fileName;
		}

		public void run() {
			try {
				List<Map<String, Object>> outData = new ArrayList<Map<String, Object>>();
				int total = requests.size();
				int done = 0;
				int error = 0;
				int pending = total;
				for (Map<String, Object> re : requests) {
					Map<String, Object> retrunExcelMapData = new HashMap<>();
					try {
						
						retrunExcelMapData.put("Site Name", re.get("siteName"));
						retrunExcelMapData.put("Site Id", re.get("siteManualId"));
						retrunExcelMapData.put("Infra Id", re.get("infraId"));
						retrunExcelMapData.put("Site Address", re.get("siteAddress"));
						retrunExcelMapData.put("Site Description", re.get("siteDescription"));
						retrunExcelMapData.put("Circle", re.get("circleName"));
						retrunExcelMapData.put("Ssa", re.get("ssaId"));
						retrunExcelMapData.put("Cluster District", re.get("clusterDistrict"));
						retrunExcelMapData.put("Battery Account SAP Number", re.get("accountSapNumber"));
						retrunExcelMapData.put("Service Center", re.get("serviceCenterid"));
						retrunExcelMapData.put("Service Resource", re.get("serviceResourceId"));
						retrunExcelMapData.put("Rjil JC Name", re.get("rjilJcName"));
						retrunExcelMapData.put("CMP", re.get("cmp"));
						retrunExcelMapData.put("Battery Customer", re.get("customer"));
						
						Map<String, Object> ss=dbSSA.getDistinctSsaId(re.get("ssaId").toString().trim());
						if(ss.size()==0 || ss==null) {
							throw new BadRequestException("SSA not found");
						}
						SSA ssa=dbSSA.findById(Long.parseLong(ss.get("id").toString())).get();
						if(ssa==null) {
							throw new BadRequestException("SSA not found");
						}
						LegalEntityResponse legalEntity = dbLegalEntity
								.getBylegalEntityName(re.get("serviceCenterid").toString());
						if (legalEntity == null) {
							throw new BadRequestException("Service Center does not exist");
						}
						ServiceResourseResponse serviceResource = dbServiceResource
								.getServiceResourseByLegalEntityAndName(legalEntity.getLegalentityId(),
										re.get("serviceResourceId").toString());
						if (serviceResource == null) {
							throw new BadRequestException("Service Resource does not exist");
						}
						re.replace("ssaId", ssa.getId());
						re.replace("serviceCenterid", legalEntity.getLegalentityId());
						re.replace("serviceResourceId", serviceResource.getServiceResourceId());
						btSiteService.saveObject(re, user);
						retrunExcelMapData.put("STATUS", "Uploaded");
						done++;
						pending--;
						websocket.convertAndSendToUser(user.getUsername(), "/topic/customerinventory",
								new WebMessage("info",
										"Total Record " + total + ",Total Done " + done + ", Total Error " + error
												+ " Total pending " + pending + ", Current process for Site Name "
												+ re.get("siteName") + " and Site Id " + re.get("siteId")
												+ " uploaded successfully"));
					} catch (Exception ex) {
						ex.printStackTrace();
						retrunExcelMapData.put("STATUS", "Error " + ex.getMessage());
						error++;
						pending--;
						websocket.convertAndSendToUser(user.getUsername(), "/topic/customerinventory",
								new WebMessage("error",
										"Total Record " + total + ",Total Done " + done + ", Total Error " + error
												+ " Total pending " + pending + ", unable to process for  Site Name "
												+ re.get("siteName") + " and Site Id " + re.get("siteId") + " due to "
												+ ex.getMessage()));
					}
					outData.add(retrunExcelMapData);
				}

				ExcelParser ex = new ExcelParser();
				BulkuploadFileDetails outPutFile = new BulkuploadFileDetails();
				outPutFile.setFileName("result_" + fileName);
				dbBulkUploadService.saveBulkuploadDocument(outPutFile, user);
				String outputFinalPath = bulkuploadLocation + File.separator + outPutFile.getId() + "_result_"
						+ fileName;
				FileOutputStream o = new FileOutputStream(outputFinalPath);
				o.write(ex.genrateFile(outData).toByteArray());
				o.flush();
				o.close();
				outPutFile.setPhysicalPath(outputFinalPath);
				outPutFile.setSize(new File(outputFinalPath).length());
				dbBulkUploadService.updateFilePath(outPutFile, user, outPutFile.getId());
				actn.setResultDocumentId(outPutFile.getId());
				actn.setUploadStatus("Completed");
				dbBulkUploadService.updateBulkuploadAction(actn, user, actn.getId());
				websocket.convertAndSendToUser(user.getUsername(), "/topic/customerinventorydata", outData);

			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public List<Map<String, Object>> parseSiteDataBattery(File file) throws Exception {
		List<Map<String, Object>> btSiteData = new ArrayList<Map<String, Object>>();
		DataFormatter formatter = new DataFormatter();
		if (!file.exists())

			throw new Exception("File not found");
		// ByteArrayInputStream str=new ByteArrayInputStream(new FileInputStream(file));
		Workbook wb = null;
		try {
			wb = new XSSFWorkbook(new FileInputStream(file));
		} catch (Exception ex) {

		}
		if (wb == null) {
			try {
				wb = new HSSFWorkbook(new FileInputStream(file));
			} catch (Exception ex) {

			}
		}
		if (wb == null)
			throw new Exception("Wrong file format");

		Sheet sheet = wb.getSheetAt(0);
		Iterator<Row> rows = sheet.iterator();
		rows.next();// SKIPPING FIRST ROW
		while (rows.hasNext()) {
			Map<String, Object> siteData = new HashMap<>();
			Row row = rows.next();

			siteData.put("siteName", formatter.formatCellValue(row.getCell(0)));
			siteData.put("siteManualId", formatter.formatCellValue(row.getCell(1)));
			siteData.put("siteDescription", formatter.formatCellValue(row.getCell(2)));
			siteData.put("circleName", formatter.formatCellValue(row.getCell(3)));
			siteData.put("ssaId", formatter.formatCellValue(row.getCell(4)));
			siteData.put("clusterDistrict", formatter.formatCellValue(row.getCell(5)));
			siteData.put("accountSapNumber", formatter.formatCellValue(row.getCell(6)));
			siteData.put("customer", formatter.formatCellValue(row.getCell(7)));
			siteData.put("serviceCenterid", formatter.formatCellValue(row.getCell(8)));
			siteData.put("serviceResourceId", formatter.formatCellValue(row.getCell(9)));
			siteData.put("infraId", formatter.formatCellValue(row.getCell(10)));
			siteData.put("siteAddress", formatter.formatCellValue(row.getCell(11)));
			siteData.put("rjilJcName", formatter.formatCellValue(row.getCell(12)));
			siteData.put("cmp", formatter.formatCellValue(row.getCell(13)));
			
			btSiteData.add(siteData);
		}
		return btSiteData;
	}

//	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = {
//			BadRequestException.class, UnauthoriseException.class, RuntimeException.class, Exception.class,
//			SQLException.class })
//	public void saveObjectData(Map<String, Object> siteDetails, LoginUser user)
//			throws BadRequestException, UnauthoriseException {
//		////////// save excel data////////////////
//		boolean createPermission = dbProfile.hasObjectAccess(user.getUsername(), BtSiteDetails.class,
//				UserProfileRepository.CREATE);
//		if (!createPermission) {
//			throw new UnauthoriseException("You are not authorised");
//		}
//
//		System.out.println("map data-- " + siteDetails);
//
//		if (siteDetails.get("siteManualId") == null
//				|| siteDetails.get("siteManualId").toString().trim().equalsIgnoreCase("")) {
//			throw new com.pratikshat.exicom.exception.BadRequestException("Site id is blank");
//		}
//
//		if (siteDetails.get("accountSapNumber") == null
//				|| siteDetails.get("accountSapNumber").toString().trim().equalsIgnoreCase("")) {
//			throw new com.pratikshat.exicom.exception.BadRequestException("Account is blank");
//		}
//
//		if (siteDetails.get("customer") == null || siteDetails.get("customer").toString().trim().equalsIgnoreCase("")) {
//			throw new com.pratikshat.exicom.exception.BadRequestException("Customer is blank");
//		}
//
//		if (siteDetails.get("siteName") == null || siteDetails.get("siteName").toString().trim().equalsIgnoreCase("")) {
//			throw new com.pratikshat.exicom.exception.BadRequestException("Site name is blank");
//		}
//
//		if (siteDetails.get("serviceCenterid").toString() == null||siteDetails.get("serviceCenterid").toString().trim().equalsIgnoreCase("")) {
//			throw new com.pratikshat.exicom.exception.BadRequestException("Service Center is blank");
//		}
//
//		if (siteDetails.get("serviceResourceId").toString() == null||siteDetails.get("serviceResourceId").toString().trim().equalsIgnoreCase("")) {
//			throw new com.pratikshat.exicom.exception.BadRequestException("Service Resource is blank");
//		}
//		if (siteDetails.get("circleName") == null
//				|| siteDetails.get("circleName").toString().trim().equalsIgnoreCase("")) {
//			throw new com.pratikshat.exicom.exception.BadRequestException("Circle is blank");
//		}
//		if (siteDetails.get("ssaId") == null || siteDetails.get("ssaId").toString().trim().equalsIgnoreCase("")) {
//			throw new com.pratikshat.exicom.exception.BadRequestException("SSA is blank");
//		}
//		if (siteDetails.get("clusterDistrict") == null
//				|| siteDetails.get("clusterDistrict").toString().trim().equalsIgnoreCase("")) {
//			throw new com.pratikshat.exicom.exception.BadRequestException("Cluster/District is blank");
//		}
//
//		if (siteDetails.get("siteDescription") == null
//				|| siteDetails.get("siteDescription").toString().trim().equalsIgnoreCase("")) {
//			throw new com.pratikshat.exicom.exception.BadRequestException("Description is blank");
//		}
//		////////// Check Given Battery Site Details already Exist In Data Base
//		////////// /////////////////
//		BtSiteDetails bsd = dbBtSite.getExistBatterySite(siteDetails.get("siteManualId").toString());
//		
//		Map<String, Object> zone = dbBtSite.getZonDetails(siteDetails.get("circleName").toString());
//
//		if (zone.get("zoneName") == null) {
//			throw new com.pratikshat.exicom.exception.BadRequestException("Zone not found");
//		}
//
//		Map<String, Object> customerExist = dbBtSite.getCustomerDetails(siteDetails.get("customer").toString());
//		if (customerExist.get("customerName") == null) {
//			throw new com.pratikshat.exicom.exception.BadRequestException("Customer not found");
//		}
//		List<CircleDetails> circleList = dbBtSite
//				.getCiculeList(legalEntity.getLegalentityId()	);
//
//		if (circleList == null || circleList.size() == 0) {
//			throw new com.pratikshat.exicom.exception.BadRequestException("Circle not found");
//		}
//	
//		if (bsd == null) {
//			com.pratikshat.exicom.entity.BtSiteDetails _btsiteDetails = new com.pratikshat.exicom.entity.BtSiteDetails();
//			_btsiteDetails.setAccount(
//					new com.pratikshat.exicom.entity.Accounts(siteDetails.get("accountSapNumber").toString()));
//			_btsiteDetails.setCircle(
//					new com.pratikshat.exicom.entity.CircleDetails(siteDetails.get("circleName").toString()));
//			_btsiteDetails.setClusterDistrict(siteDetails.get("clusterDistrict").toString());
//			_btsiteDetails.setCreatedBy(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
//			_btsiteDetails.setCreatedDate(new java.util.Date());
//			_btsiteDetails.setInfraId(siteDetails.get("infraId").toString());
//			_btsiteDetails.setLastModifiedBy(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
//			_btsiteDetails.setLastModifiedDate(new java.util.Date());
//			_btsiteDetails.setOwner(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
//			if (siteDetails.get("primaryAccountSapNumber") != null
//					&& !siteDetails.get("primaryAccountSapNumber").toString().isEmpty()) {
//				_btsiteDetails.setPrimaryAccount(new com.pratikshat.exicom.entity.Accounts(
//						siteDetails.get("primaryAccountSapNumber").toString()));
//			} else {
//				_btsiteDetails.setPrimaryAccount(null);
//			}
//			_btsiteDetails.setServiceCenter(new com.pratikshat.exicom.entity.LegalEntity(legalEntity.getLegalentityId()));
//				_btsiteDetails.setServiceResource(new com.pratikshat.exicom.entity.ServiceRecource(serviceResource.getServiceResourceId()));
//			_btsiteDetails.setSiteAddress(siteDetails.get("siteAddress").toString());
//			_btsiteDetails.setSiteDescription(siteDetails.get("siteDescription").toString());
//			_btsiteDetails.setSiteName(siteDetails.get("siteName").toString());
//			_btsiteDetails.setManualId(siteDetails.get("siteManualId").toString());
//			_btsiteDetails.setSsa(new SSA(Long.parseLong(siteDetails.get("ssaId").toString())));
//			_btsiteDetails.setZoneZoneName(zone.get("zoneName").toString());
//			_btsiteDetails.setCustomer(siteDetails.get("customer").toString());
//			//dbBtSite.saveDetails(_btsiteDetails, user);
//			dbBtSite.save(_btsiteDetails);
//		// entityManager.persist(_btsiteDetails);
//
//			// siteDetails.setSiteId(_btsiteDetails.getId());
//
//			// Global Search Entry Point
//			try {
//				GlobalSearchIndex _search = new GlobalSearchIndex();
//				_search.setSearchString(_btsiteDetails.getSiteName());
//				_search.setKeyName("siteName");
//				_search.setObjectName("SiteDetails");
//				_search.setObjectDetailsUrl("sites/" + _btsiteDetails.getId());
//				_search.setObjectIdName("siteId");
//				_search.setObjectIdValue(String.valueOf(_btsiteDetails.getId()));
//				// entityManager.persist(_search);
//				dbGlobalSearch.save(_search);
//				siteDetails.put("siteId", _btsiteDetails.getId());
//			} catch (Exception e) {
//			}
//			// Global Search Entry Point
//			try {
//				GlobalSearchIndex _search = new GlobalSearchIndex();
//				_search.setSearchString(_btsiteDetails.getManualId());
//				_search.setKeyName("manualSiteId");
//				_search.setObjectName("SiteDetails");
//				_search.setObjectDetailsUrl("sites/" + _btsiteDetails.getId());
//				_search.setObjectIdName("siteId");
//				_search.setObjectIdValue(String.valueOf(_btsiteDetails.getId()));
//				dbGlobalSearch.save(_search);
//				// entityManager.persist(_search);
//			} catch (Exception e) {
//			}
//		} else {
//			throw new com.pratikshat.exicom.exception.BadRequestException("Battery Site Already Exist");
//		}
//	}
/////////////////////////// case details base on site id ////////////////////////////
	@GetMapping("releted/{siteid}/case")
	public Page<Map<String, Object>> getReletedCaseWithSiteId(@PathVariable("siteid") long siteId, Pageable page) {
		return dbCase.getBySiteId(siteId, page);
	}
	
	@GetMapping("releted/{siteid}/serviceappointment")
	public Page<Map<String, Object>> getServiceAppointmentBySiteId(@PathVariable("siteid")long siteId, Pageable page){
		return dbService.getReleatedServiceAppoint(siteId, page);
	}
	
	@GetMapping("releted/{siteid}/assets")
	public Page<Map<String, Object>> getAssetsBySiteId(@PathVariable("siteid")long siteId,Pageable page){
		return dbAsset.getAssetsBySiteId(siteId, page);
	}
}

