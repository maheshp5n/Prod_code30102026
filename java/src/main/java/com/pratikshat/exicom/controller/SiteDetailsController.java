package com.pratikshat.exicom.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.NonUniqueResultException;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import com.pratikshat.exicom.entity.CaseOriginDetails;
import com.pratikshat.exicom.entity.CasePriorityDetails;
import com.pratikshat.exicom.entity.CaseProblemReported;
import com.pratikshat.exicom.entity.CaseReasonDetails;
import com.pratikshat.exicom.entity.CaseStageDetails;
import com.pratikshat.exicom.entity.CaseStatusDetails;
import com.pratikshat.exicom.entity.CaseTypes;
import com.pratikshat.exicom.entity.ContactDetails;
import com.pratikshat.exicom.entity.CustomerDeatils;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.PendingResponsibility;
import com.pratikshat.exicom.entity.SSA;
import com.pratikshat.exicom.entity.SiteDetails;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.AccountCreate;
import com.pratikshat.exicom.model.request.CaseBulkRequest;
import com.pratikshat.exicom.model.request.CaseRequest;
import com.pratikshat.exicom.model.request.LegalEntityResponse;
import com.pratikshat.exicom.model.request.SiteBulkRequest;
import com.pratikshat.exicom.model.request.SiteDetailsRequest;
import com.pratikshat.exicom.model.response.CircleDetailsResponse;
import com.pratikshat.exicom.model.response.SSAResponse;
import com.pratikshat.exicom.model.response.ServiceResourseResponse;
import com.pratikshat.exicom.model.response.SiteDetailsForList;
import com.pratikshat.exicom.model.response.SiteDetailsResponse;
import com.pratikshat.exicom.repository.AccountsRepository;
import com.pratikshat.exicom.repository.CircleDetailsRepository;
import com.pratikshat.exicom.repository.CustomerDetailsRepository;
import com.pratikshat.exicom.repository.LegalEntityRepository;
import com.pratikshat.exicom.repository.SSARepository;
import com.pratikshat.exicom.repository.ServiceRecourseRepository;
import com.pratikshat.exicom.repository.SiteDetailsRepository;
import com.pratikshat.exicom.repository.UserProfileRepository;
import com.pratikshat.exicom.utils.CaseParser;
import com.pratikshat.exicom.utils.ExcelParser;
import com.pratikshat.exicom.utils.SiteParser;

@RestController
@RequestMapping("api/sites")
public class SiteDetailsController {
	@Autowired
	SiteDetailsRepository dbSite;
	@Autowired
	com.pratikshat.exicom.repository.SiteDetailsRecentVisitRepository dbRecent;
	@Autowired
	com.pratikshat.exicom.repository.CaseDetailsRepository dbCase;
	@Autowired
	com.pratikshat.exicom.repository.UserProfileRepository dbProfile;
	@Autowired
	CircleDetailsRepository dbCircle;
	@Autowired
	CustomerDetailsRepository dbCustomerDetails;
	@Autowired
	SSARepository dbSSA;
	@Autowired
	AccountsRepository dbAccount;
	@Autowired
	LegalEntityRepository dbLegalEntity;
	@Autowired
	ServiceRecourseRepository dbServiceResource;

	Logger logger = LoggerFactory.getLogger(SiteDetailsController.class);

	@GetMapping
	Page<com.pratikshat.exicom.model.response.SiteDetailsResponse> getByAll(Pageable page, @CurrentUser LoginUser user,
			@RequestParam(value = "filtername", required = false) String filtername) {
		if (dbProfile.hasObjectAccess(user.getUsername(), "SiteDetails", "viewAllRights")
				|| dbProfile.hasObjectAccess(user.getUsername(), "SiteDetails", "viewRights")) {
			if (filtername != null && filtername.equalsIgnoreCase("related"))
				return dbSite.getRecentVisit(user.getUsername(), page);
			return dbSite.getByAll(page);
		}
		return Page.empty();
	}

	@GetMapping("{siteid}")
	com.pratikshat.exicom.model.response.SiteDetailsResponse getByStieId(@PathVariable("siteid") long siteid,
			@CurrentUser LoginUser user) {
		if (dbProfile.hasObjectAccess(user.getUsername(), "SiteDetails", "viewAllRights")
				|| dbProfile.hasObjectAccess(user.getUsername(), "SiteDetails", "viewRights")) {
			try {
				com.pratikshat.exicom.entity.SiteDetailsRecentVisit _recent = dbRecent.findBySiteIdAndVisitorId(siteid,
						user.getUsername());
				if (_recent != null) {
					_recent.getSiteId(); // glti se glt ho gya tha to ye lga dia maine (shagufta)
					_recent.setVisitDate(new java.util.Date());
				} else {
					_recent = new com.pratikshat.exicom.entity.SiteDetailsRecentVisit(siteid, user.getUsername());
				}
				dbRecent.save(_recent);
			} catch (Exception e) {
			}
			return dbSite.getBySiteid(siteid);
		}
		return new com.pratikshat.exicom.model.response.SiteDetailsResponse();
	}

	@GetMapping("forlist")
	java.util.List<com.pratikshat.exicom.model.response.SiteDetailsForList> getForList() {
		return dbSite.getSiteForList();
	}

	@GetMapping("forlist/small")
	Page<SiteDetailsForList> getForList(@RequestParam("searchkey") String searchKey, Pageable page) {
		return dbSite.getSiteForList("%" + searchKey + "%", page);
	}

	@GetMapping("recentvisit")
	public Page<com.pratikshat.exicom.model.response.SiteDetailsResponse> getRecentVisit(@CurrentUser LoginUser user,
			Pageable page) {
		if (dbProfile.hasObjectAccess(user.getUsername(), "SiteDetails", "viewAllRights")
				|| dbProfile.hasObjectAccess(user.getUsername(), "SiteDetails", "viewRights")) {
			return dbSite.getRecentVisit(user.getUsername(), page);
		}
		return Page.empty();

	}

	@PostMapping
	com.pratikshat.exicom.model.response.SiteDetailsResponse postSiteDetails(
			@RequestBody com.pratikshat.exicom.model.request.SiteDetailsRequest req, @CurrentUser LoginUser user,
			HttpServletResponse response) throws IOException, Exception {
		if (!dbProfile.hasObjectAccess(user.getUsername(), "SiteDetails", "createRights")) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}

		try {

			if (dbSite.countByManualSiteId(req.getSiteManualId()) > 0) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "SiteId already exists");
				return null;
			}

			if (dbSite.countBySiteName(req.getSiteName()) > 0) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "SiteName  already exists");
				return null;
			}
			
			if(req.getInfraId() != null) {
				if (req.getInfraId() != null && !req.getInfraId().trim().equalsIgnoreCase("")) {

					if (dbSite.countByInfraId(req.getInfraId()) > 0) {
						response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Infraid  already exists");
						return null;
					}
				}
			}
			
			if(req.getIndusId() != null) {
				if (req.getIndusId() != null && !req.getIndusId().trim().equalsIgnoreCase("")) {

					if (dbSite.countByIndusId(req.getIndusId()) > 0) {
						response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Indusid  already exists");
						return null;
					}
				}
			}
			

			dbSite.saveDetails(req, user);
			return dbSite.getBySiteid(req.getSiteId());
		} catch (BadRequestException ex) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
			return null;
		} catch (UnauthoriseException ex) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, ex.getMessage());
			return null;
		} catch (Exception ex) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
			return null;
		}

	}

	@PutMapping("{siteid}")
	com.pratikshat.exicom.model.response.SiteDetailsResponse putDetails(
			@RequestBody com.pratikshat.exicom.model.request.SiteDetailsRequest req, @CurrentUser LoginUser user,
			@PathVariable("siteid") long siteId, HttpServletResponse response) throws IOException {
		if (!dbProfile.hasObjectAccess(user.getUsername(), "SiteDetails", "editRights")) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
		
		try {

			if (dbSite.countBySiteId(siteId) == 0) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Site does not exists");
				return null;
			}
			if(req.getIndusId()!=null) {
				
				List<Map<String, Object>> indusIdData = dbSite.getByIndusId(siteId, req.getIndusId().trim());
				if(req.getIndusId()!=null && !req.getIndusId().trim().equalsIgnoreCase("")) {
					if (indusIdData.size() > 0) {
						response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Indus Id Already Exist");
				return null;
					}
				}
			}
			
			if(req.getInfraId()!=null) {
			List<Map<String, Object>> indusIdData1 = dbSite.getByInfraId(siteId, req.getInfraId().trim());
				if(req.getInfraId()!=null && !req.getInfraId().trim().equalsIgnoreCase("")) {
					if (indusIdData1.size() > 0) {
						response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Infra Id Already Exist");
						return null;
					}
					}
	
			}
			
			List<Map<String, Object>> indusIdData2 = dbSite.getBySiteName(siteId, req.getSiteName().trim());
			
			if (indusIdData2.size() > 0) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Site Name Already Exist");
				return null;
			}
			SiteDetailsRequest siteRequest = dbSite.updateSiteDetails(req, user);
			return dbSite.getBySiteid(siteRequest.getSiteId());
		} catch (Exception ex) {
			ex.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
			return null;
		}

	}

	@GetMapping("case/{siteid}")
	public Page<com.pratikshat.exicom.model.response.CaseResponse> getCaseBySiteId(@PathVariable("siteid") long siteId,
			Pageable page) {
		return dbCase.getBySiteId(siteId, page);
	}

	@GetMapping("releted/{siteid}/case")
	public Page<com.pratikshat.exicom.model.response.CaseResponse> getReletedCaseWithSiteId(
			@PathVariable("siteid") long siteId, Pageable page) {
		return dbCase.getBySiteId(siteId, page);
	}

	@GetMapping("releted/{siteid}/serviceappointment")
	public Page<com.pratikshat.exicom.model.response.ServiceAppointmentResponse> getServiceAppointmentBySiteId(
			@PathVariable("siteid") long siteId, Pageable page) {
		return dbSite.getReleatedServiceAppoint(siteId, page);
	}

	@GetMapping("releted/{siteid}/assets")
	public Page<com.pratikshat.exicom.model.response.AssetsResponse> getAssetsBySiteId(
			@PathVariable("siteid") long siteId, Pageable page) {
		return dbSite.getAssetsBySiteId(siteId, page);
	}

	@GetMapping("related/ssa/{ssaid}")
	public Page<SiteDetailsResponse> getSiteBySSA(@PathVariable("ssaid") long ssaid, Pageable page) {
		return dbSite.getSiteDetailsBySsaId(ssaid, page);
	}

	@GetMapping("advancesearch")
	public List<SiteDetailsResponse> getAdvanceSearchDetails(@RequestParam Map<String, Object> keys,
			@CurrentUser LoginUser user) {
		if (dbProfile.hasObjectAccess(user.getUsername(), SiteDetails.class, UserProfileRepository.VIEW)
				|| dbProfile.hasObjectAccess(user.getUsername(), SiteDetails.class, UserProfileRepository.VIEW_ALL))
			return dbSite.getAdvanceSearchReslt(keys, user, null);
		return new ArrayList<>();
	}

	@GetMapping("/bulkTemplate")
	public ResponseEntity<ByteArrayResource> downloadBulkTemplate(HttpServletResponse httpResponse) throws IOException {
		HttpHeaders header = new HttpHeaders();
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("site");
		Object[][] bookData = { { "Exchange/Site Name", "Site Id", "Infra Id", "Site Address", "Site Description",
				"Circle", "SSA", "Cluster/District", "Account SAP Number", "Customer", "Service Center",
				"Service Resource", "Indus Id" }, };

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
					if (columnCount != 3 && columnCount != 4 && columnCount != 13) {
						cell.setCellStyle(style);
					}
				} else if (field instanceof Integer) {
					cell.setCellValue((Integer) field);
					if (columnCount != 3 && columnCount != 4 && columnCount != 13) {
						cell.setCellStyle(style);
					}
				}
			}

		}

		header.setContentType(new MediaType("application", "force-download"));
		header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + "site_template.xlsx");
		httpResponse.setContentType("application/xlsx");
		ByteArrayOutputStream bytest = new ByteArrayOutputStream();
		workbook.write(bytest);
		return new ResponseEntity<>(new ByteArrayResource(bytest.toByteArray()), header, HttpStatus.OK);
	}

	@PostMapping("/bulkCreate")
	public ResponseEntity<ByteArrayResource> bulkCreate(@RequestParam("file") MultipartFile reapExcelDataFile,
			@CurrentUser com.pratikshat.exicom.entity.LoginUser user, HttpServletResponse httpResponse)
			throws IOException {
		// System.out.println(reapExcelDataFile.getOriginalFilename());
		String fileName = reapExcelDataFile.getOriginalFilename();

		if (fileName.substring(fileName.lastIndexOf('.') + 1).equals("xlsx")) {
			HttpHeaders header = new HttpHeaders();

			if (!dbProfile.hasObjectAccess(user.getUsername(), "SiteDetails", "createRights")) {
				httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
				return null;
			}

			List<Map<String, Object>> outData = new ArrayList<Map<String, Object>>();
			ByteArrayOutputStream st = null;
			try {
				List<SiteBulkRequest> requests = new SiteParser().parseIn(reapExcelDataFile);
				for (SiteBulkRequest re : requests) {

					Map<String, Object> d = new HashMap<String, Object>();

					d.put("Exchange/Site Name", re.getSiteName());
					d.put("Site Id", re.getSiteId());
					d.put("Infra Id", re.getInfraId());
					d.put("Site Address", re.getSiteAddress());
					d.put("Site Description", re.getSiteDescription());
					d.put("Circle", re.getCircle());
					d.put("SSA", re.getSsa());
					d.put("Cluster/District", re.getClusterDistrict());
					d.put("Account SAP Number", re.getAccount());
					d.put("Customer", re.getCustomer());
					d.put("Service Center", re.getServiceCenter());
					d.put("Service Resource", re.getServiceResource());
					d.put("Indus Id", re.getIndusId());

					try {

						SiteDetailsRequest req = new SiteDetailsRequest();

						if (re.getSiteName() == null || re.getSiteName().trim().equals("")) {
							throw new BadRequestException("Exchange/Site Name is null");
						}

						if (re.getSiteId() == null || re.getSiteId().trim().equalsIgnoreCase("")) {
							throw new BadRequestException("Site Id is null");
						}

						if (re.getSiteDescription() == null || re.getSiteDescription().trim().equalsIgnoreCase("")) {
							throw new BadRequestException("Site Description is null");
						}

						if (re.getCircle() == null || re.getCircle().trim().equalsIgnoreCase("")) {
							throw new BadRequestException("Circle is null");
						}

						if (re.getSsa() == null || re.getSsa().trim().equalsIgnoreCase("")) {
							throw new BadRequestException("SSA is null");
						}

						if (re.getClusterDistrict() == null || re.getClusterDistrict().trim().equalsIgnoreCase("")) {
							throw new BadRequestException("Cluster/District is null");
						}

						if (re.getAccount() == null || re.getAccount().trim().equalsIgnoreCase("")) {
							throw new BadRequestException("Account is null");
						}

						if (re.getCustomer() == null || re.getCustomer().trim().equalsIgnoreCase("")) {
							throw new BadRequestException("Customer is null");
						}

						if (re.getServiceCenter() == null || re.getServiceCenter().trim().equalsIgnoreCase("")) {
							throw new BadRequestException("Service Center is null");
						}

						if (re.getServiceResource() == null || re.getServiceResource().trim().equalsIgnoreCase("")) {
							throw new BadRequestException("Service Resource is null");
						}

						CircleDetailsResponse circle = dbCircle.getByCircleName(re.getCircle());
						if (circle == null) {
							throw new BadRequestException("Circle does not exist");
						}

						SSAResponse ssa = dbSSA.getByNameAndCircle(re.getSsa(), re.getCircle());
						if (ssa == null) {
							throw new BadRequestException("SSA does not exist");
						}

						AccountCreate account = dbAccount.getBySapCustomerNumber(re.getAccount());
						if (account == null) {
							throw new BadRequestException("Account does not exist");
						}
//						if (account.getSampAccountNumber().startsWith("INDUS") && re.getIndusId().isEmpty()) {
//							throw new BadRequestException("Indus id null");
//						}

						CustomerDeatils customer = dbCustomerDetails.getByName(re.getCustomer());
						if (customer == null) {
							throw new BadRequestException("Customer does not exist");
						}

						LegalEntityResponse legalEntity = dbLegalEntity.getBylegalEntityName(re.getServiceCenter());
						if (legalEntity == null) {
							throw new BadRequestException("Service Center does not exist");
						}

						ServiceResourseResponse serviceResource = dbServiceResource
								.getServiceResourseByLegalEntityAndName(legalEntity.getLegalentityId(),
										re.getServiceResource());
						if (serviceResource == null) {
							throw new BadRequestException("Service Resource does not exist");
						}
//						if (re.getCustomer().startsWith("INDUS") && re.getIndusId().isEmpty()) {
//							throw new BadRequestException("Indus id null");
//						}

						req.setSiteManualId(re.getSiteId());
						req.setSiteName(re.getSiteName());
						req.setInfraId(re.getInfraId());
						req.setSiteAddress(re.getSiteAddress());
						req.setSiteDescription(re.getSiteDescription());
						req.setCircleName(circle.getCircleName());
						req.setSsaId(ssa.getId());
						req.setClusterDistrict(re.getClusterDistrict());
						req.setAccountSapNumber(account.getSampAccountNumber());
						req.setCustomer(customer.getCustomerName());
						req.setServiceCenterid(legalEntity.getLegalentityId());
						req.setServiceResourceId(serviceResource.getServiceResourceId());
						req.setIndusId(re.getIndusId());

						if (dbSite.countByManualSiteId(req.getSiteManualId()) > 0) {
							throw new BadRequestException("SiteId  is already exits");
						}

						if (dbSite.countBySiteName(req.getSiteName()) > 0) {
							throw new BadRequestException("SiteName is already exits");
						}

						if (req.getInfraId() != null && !req.getInfraId().trim().equalsIgnoreCase("")) {

							if (dbSite.countByInfraId(req.getInfraId()) > 0) {

								throw new BadRequestException("InfraId already exits");

							}
						}

						if (req.getIndusId() != null && !req.getIndusId().trim().equalsIgnoreCase("")) {

							if (dbSite.countByIndusId(req.getIndusId()) > 0) {

								throw new BadRequestException("IndusId is already exits");
							}
						}

						dbSite.saveDetails(req, user);

						d.put("STATUS", "Success");
					} catch (Exception ex) {
						logger.error("error in site bulk upload {}", ex);
						d.put("STATUS", "Error " + ex.getMessage());
					}
					outData.add(d);
				}

			} catch (Exception ex) {
				logger.error("error in site bulk upload {}", ex);
				httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
				return null;
			}
			try {
				st = new ExcelParser().genrateFile(outData);
			} catch (Exception ex) {
				logger.error("error in site bulk upload {}", ex);
				httpResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
				return null;
			}

			httpResponse.addHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=siteResult.xlsx");
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
}
