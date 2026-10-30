package com.pratikshat.exicom.services.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.pratikshat.exicom.entity.BtAssets;
import com.pratikshat.exicom.entity.BtAssetsAmcdetails;
import com.pratikshat.exicom.entity.BtAssetsTypeChoice;
import com.pratikshat.exicom.entity.BtCase;
import com.pratikshat.exicom.entity.BtCaseProblemReported;
import com.pratikshat.exicom.entity.BtPreRegisterReport;
import com.pratikshat.exicom.entity.BtPreRegistrationCaseActionLog;
import com.pratikshat.exicom.entity.BtPreRegistrationCases;
import com.pratikshat.exicom.entity.BtProblemMaster;
import com.pratikshat.exicom.entity.BtSiteDetails;
import com.pratikshat.exicom.entity.BusinessDomain;
import com.pratikshat.exicom.entity.CaseReasonDetails;
import com.pratikshat.exicom.entity.CaseStageDetails;
import com.pratikshat.exicom.entity.CaseStatusDetails;
import com.pratikshat.exicom.entity.ContactDetails;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.PendingResponsibility;
import com.pratikshat.exicom.entity.PreRegistrationCases;
import com.pratikshat.exicom.entity.ServiceRecource;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.response.BtCaseResponse;
import com.pratikshat.exicom.model.response.BtPrcResponse;
import com.pratikshat.exicom.model.response.PrcResponse;
import com.pratikshat.exicom.repository.BtAssetsAmcdetailsRepository;
import com.pratikshat.exicom.repository.BtAssetsRepository;
import com.pratikshat.exicom.repository.BtAssetsTypeChoiceRepository;
import com.pratikshat.exicom.repository.BtCaseRepository;
import com.pratikshat.exicom.repository.BtCustomerInventoryInRepository;
import com.pratikshat.exicom.repository.BtPreRegisterReportRepository;
import com.pratikshat.exicom.repository.BtPreRegistrationCaseActionLogRepository;
import com.pratikshat.exicom.repository.BtPreRegistrationCasesRepository;
import com.pratikshat.exicom.repository.BtProblemMasterRepository;
import com.pratikshat.exicom.repository.BtSiteDetailsRepository;
import com.pratikshat.exicom.repository.BusinessDomainRepository;
import com.pratikshat.exicom.repository.CaseReasonDetailsRepository;
import com.pratikshat.exicom.repository.CaseStageDetailsRepository;
import com.pratikshat.exicom.repository.CaseStatusDetailsRepository;
import com.pratikshat.exicom.repository.ContactDetailsRepository;
import com.pratikshat.exicom.repository.ServiceRecourseRepository;
import com.pratikshat.exicom.repository.UserProfileRepository;
import com.pratikshat.exicom.services.BtPreRegistrationCasesService;

@Service
public class BtPreRegistrationCasesServiceImpl implements BtPreRegistrationCasesService {

	@Autowired
	UserProfileRepository dbProfile;
	@Autowired
	BtAssetsRepository dbAssets;
	@Autowired
	BtAssetsAmcdetailsRepository dbAmc;
	@Autowired
	BtSiteDetailsRepository dbSite;
	@Autowired
	ContactDetailsRepository dbContact;
	@Autowired
	CaseReasonDetailsRepository dbCaseReson;
	@Autowired
	CaseStageDetailsRepository dbCaseStage;
	@Autowired
	ServiceRecourseRepository dbserviceRes;
	@Autowired
	BtPreRegistrationCasesRepository dbpreres;
	@Autowired
	BusinessDomainRepository dbDomain;
	@Autowired
	BtCaseRepository dbCase;
	@Autowired
	BtPreRegistrationCaseActionLogRepository dbaction;
	@Autowired
	JdbcTemplate jdbc;
	@Autowired
	BtPreRegisterReportRepository dbpreReport;
	@Autowired
	BtAssetsTypeChoiceRepository dbTypeChoice;
	@Autowired
	BtPreRegisterReportRepository dbProblem;
	@Autowired
	BtProblemMasterRepository problemMasterRepository;
	@Autowired
	CaseStatusDetailsRepository statusDetailsRepository;
	@Autowired
	BtCustomerInventoryInRepository dbBRCCase;
	@Autowired
	EntityManager em;

	SimpleDateFormat inputSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	SimpleDateFormat outputSdf = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	public Map<String, Object> getSingle(Object id, LoginUser user) throws BadRequestException, UnauthoriseException {
		boolean view = dbProfile.hasObjectAccess(user.getUsername(), BtPreRegistrationCases.class,
				UserProfileRepository.VIEW);
		boolean viewAll = dbProfile.hasObjectAccess(user.getUsername(), BtPreRegistrationCases.class,
				UserProfileRepository.VIEW_ALL);
		if (view || viewAll) {
			long _id = Long.valueOf(id.toString());
			return dbpreres.getSingleCase(_id);
		} else {
			throw new UnauthoriseException("You are not Authorized");
		}
	}

	@Override
	public Page<Map<String, Object>> getPage(Pageable page, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		boolean view = dbProfile.hasObjectAccess(user.getUsername(), BtPreRegistrationCases.class,
				UserProfileRepository.VIEW);
		boolean viewAll = dbProfile.hasObjectAccess(user.getUsername(), BtPreRegistrationCases.class,
				UserProfileRepository.VIEW_ALL);
		if (view || viewAll) {

			return dbpreres.getPage(page);
		} else {
			throw new UnauthoriseException("You are not Authorized");
		}
	}

	@Override
	@Transactional(rollbackFor = { BadRequestException.class, UnauthoriseException.class,
			Exception.class }, propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED)
	public BtPrcResponse saveObjects(Map<String, Object> request, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		boolean create = dbProfile.hasObjectAccess(user.getUsername(), BtPreRegistrationCases.class,
				UserProfileRepository.CREATE);
		if (create) {
			BtPreRegistrationCases cases = new BtPreRegistrationCases();
			

			if (request.get("assetsId") != null
					&& !request.get("assetsId").toString().equalsIgnoreCase("")) {
				BtAssets assets = dbAssets.getAssetsForAppointment(Long.parseLong(request.get("assetsId").toString()));
				if (assets != null) {
					cases.setAssets(assets);
					BtAssetsAmcdetails amcdetails = dbAmc.getWithId(assets.getId());
					if (amcdetails.getAmcExpiryDate() != null) {
						Date expiryDate;
						try {
							expiryDate = outputSdf.parse(outputSdf.format(amcdetails.getAmcExpiryDate()));
							Date today = outputSdf.parse(outputSdf.format(new Date()));
							if (expiryDate.compareTo(today) >= 0) {
								cases.setPODate(outputSdf.parse(amcdetails.getAmcPurchaseOrderDate()));
								cases.setPONumber(amcdetails.getAmcPurchaseOrderNumber());
							}
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}

					} else {
						throw new com.pratikshat.exicom.exception.BadRequestException("Asset does not exist");
					}
			

				if (assets != null && assets.getStatus() != null) {
					if (assets.getStatus().getAssetsStatusId() == 1) { // Dismantle
						throw new com.pratikshat.exicom.exception.BadRequestException("Asset is Dismantle");
					}
				}
				
				//find asset on PRC
				
				long checkAssetInPRC= dbpreres.checkAssetsStatusPRC(assets.getId());
				
				if(checkAssetInPRC>0)
					throw new BadRequestException("Case already logged on this Asset");
				
				// Find asset on Case

				long checkAsset = dbCase.checkAssetStatusInAllCase(assets.getId());

				if (checkAsset > 0)
					throw new BadRequestException("Case already logged on this Asset");

				// Find Asset with Open Status in BRC Case

				long checkAssetInBRC = dbBRCCase.checkAssetStatusIbBRCCase(assets.getId());

				if (checkAssetInBRC > 0)
					throw new BadRequestException("Case already logged on this Asset");

				//
				
			} else {
				List<Long> caseReasonList = new ArrayList<>();
				caseReasonList.add(7L); // Exceptional Case
				caseReasonList.add(8L); // Installation
				caseReasonList.add(51L); // Warehouse Health check-ups
				caseReasonList.add(48L); // Site Survey
				caseReasonList.add(47L); // Battery Bank Charging
//					if (caseReasonList.contains(Long.parseLong(request.get("caseReasonId").toString()))) {

						if ((Integer) request.get("caseReasonId") == 7L || (Integer) request.get("caseReasonId") == 8L) {
							if (request.get("missingAssets") == null) {
								throw new com.pratikshat.exicom.exception.BadRequestException("Please fill missing asset");
							}
						}
//					} else {
//						throw new com.pratikshat.exicom.exception.BadRequestException("Please select asset");
//					}
			}
			BtSiteDetails site = null;
			if (request.get("siteId") != null) {
				site = dbSite.getSiteWithId(Long.valueOf(request.get("siteId").toString()));
				cases.setSite(site);
			} else {
				throw new com.pratikshat.exicom.exception.BadRequestException("Site does not exist");
			}
			if (request.get("assetsId") != null) {
				BtAssets assets = dbAssets.getAssetsForAppointment(Long.parseLong(request.get("assetsId").toString()));
				if (assets.getSiteDetails() != null) {
					if (!assets.getSiteDetails().getId().equals(Long.valueOf(request.get("siteId").toString()))) {
						System.out.println("assets id is " + assets.getSiteDetails().getId() + " "
								+ Long.valueOf(request.get("siteId").toString()));
						throw new com.pratikshat.exicom.exception.BadRequestException(
								"Given site mismatch with the assets installed on the site");
					}
				} else {
					throw new com.pratikshat.exicom.exception.BadRequestException("Site does not exist");
				}
			}
			if(request.get("contactPersonId") ==null)
				throw new BadRequestException("Contact person id is null");
			ContactDetails contact = dbContact.getWithContactIdAndAccountId(
					Long.valueOf(request.get("contactPersonId").toString()), site.getAccount().getSapCustomerNumber());
			cases.setContactPerson(contact);
			
			
			if(request.get("missingAssets")!=null) {
				
				CaseReasonDetails caseReason=dbCaseReson.getCaseReasonWithId(Long.parseLong(request.get("caseReasonId").toString()));
				cases.setCaseReason(caseReason);
				
				
			}else {
			try {
				long caseTypeId = Long.parseLong(request.get("caseTypeId").toString());
				List<CaseReasonDetails> reasons = dbCaseReson.getResonWithCasetypeAndDomainId(Long.valueOf(caseTypeId),
						(int) request.get("caseRecordTypeId"));
				List<Long> reasonList = new ArrayList<>();
				for (int i = 0; i < reasons.size(); i++) {
					CaseReasonDetails reason = reasons.get(i);
					reasonList.add(reason.getReasonId());
				}
				if (request.get("caseReasonId") != null
						&& !reasonList.contains(Long.parseLong(request.get("caseReasonId").toString()))) {
					throw new com.pratikshat.exicom.exception.BadRequestException("Wrong case reason");
				}
			} catch (NoResultException ex) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Wrong case reason");
			}
			cases.setCaseReason(new com.pratikshat.exicom.entity.CaseReasonDetails(
					Long.parseLong(request.get("caseReasonId").toString())));
			}

			
			
			if (request.get("typeChoice") != null) {
				BtAssetsTypeChoice assetsTypeChoice = dbTypeChoice
						.getTypeById(Integer.parseInt(request.get("typeChoice").toString()));
				cases.setAssetsTypeChoice(assetsTypeChoice);
				if (assetsTypeChoice == null) {
					throw new BadRequestException("No Type Choice found");
				}
			}
			try {
				List<CaseStageDetails> stages = dbCaseStage.getStageWithStatusAndDomainId(
						(int) request.get("caseStatusId"), (int) request.get("caseRecordTypeId"));
				List<Integer> reasonList = new ArrayList<>();

				for (int i = 0; i < stages.size(); i++) {
					CaseStageDetails stage = stages.get(i);
					reasonList.add(stage.getId());
				}
				if (!reasonList.contains(Integer.parseInt(request.get("caseStageId").toString()))) {
					throw new com.pratikshat.exicom.exception.BadRequestException("Wrong case stage");
				}
			} catch (NoResultException ex) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Wrong case stage");
			}
			if (request.get("serviceResourceId") != null) {
				int serviceCenterId = (Integer) request.get("serviceCenterId");
				int serviceResourceId = (Integer) request.get("serviceResourceId");
				ServiceRecource serviceRecource = dbserviceRes.getServiceResourceWithIdAndServiceid(
						Long.valueOf(serviceCenterId), Long.valueOf(serviceResourceId));
				cases.setEngineer(serviceRecource);
			}
			cases.setCaseType(
					new com.pratikshat.exicom.entity.CaseTypes(Long.parseLong(request.get("caseTypeId").toString())));
			if ((Integer) request.get("caseReasonId") == 8 || (Integer) request.get("caseReasonId") == 18) {
				
				if(request.get("poDate")!=null) {
					try {
						cases.setPODate(inputSdf.parse(request.get("poDate").toString()));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
					if (request.get("poamount") != null)
						cases.setPoamount(request.get("poamount").toString());
					if (request.get("lineitem") != null)
						cases.setLineitem(request.get("lineitem").toString());
					if(request.get("poNumber")!=null) 
						cases.setPONumber(request.get("poNumber").toString());
			}
			if ((Integer) request.get("caseStatusId") == 2 || (Integer) request.get("caseStatusId") == 4) { // Closed
				cases.setCloseDate(new Date());
				cases.setClosedBy(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
			}
			if(request.get("noOfSmr")!= null)
				cases.setNoOfsmr(request.get("noOfSmr").toString());
			cases.setOrigin(new com.pratikshat.exicom.entity.CaseOriginDetails((int) request.get("caseOriginId")));
			cases.setOwnerDetails(new EmployeeDetails(user.getUsername()));
			cases.setPriority(
					new com.pratikshat.exicom.entity.CasePriorityDetails((int) request.get("casePriorityId")));
			cases.setBusinessDomain(
					new com.pratikshat.exicom.entity.BusinessDomain((int) request.get("caseRecordTypeId")));
			if (request.get("remarks") != null) {
				cases.setRemarks(request.get("remarks").toString());
			}
			cases.setStage(new com.pratikshat.exicom.entity.CaseStageDetails((int) request.get("caseStageId")));
			cases.setStatus(new com.pratikshat.exicom.entity.CaseStatusDetails((int) request.get("caseStatusId")));
			if (request.get("missingAssets") != null) {
				cases.setMissingAssets(request.get("missingAssets").toString());
			}
			if (request.get("serviceCenterId") != null) {
				int serviceCenterId = (Integer) request.get("serviceCenterId");
				cases.setServiceCenter(new com.pratikshat.exicom.entity.LegalEntity(Long.valueOf(serviceCenterId)));
			}
			if (request.get("otherProblemReported") != null) {
				cases.setOtherProblemReported(request.get("otherProblemReported").toString());
			}
			if (request.get("isAssignByAssignmentRule") != null) {
				cases.setAssignByAssignmentRule((boolean) request.get("isAssignByAssignmentRule"));
			}
			if (request.get("inputFromEng") != null) {
				cases.setInputFromEng(request.get("inputFromEng").toString());
			}
			if (request.get("internalRemark") != null) {
				cases.setInternalRemark(request.get("internalRemark").toString());
			}
			if (request.get("RJILTT01No") != null) {
				cases.setRJILTT01No(request.get("RJILTT01No").toString());
			}
			if(request.get("openDate")!=null) {
				
					cases.setOpenDate(request.get("openDate").toString());
							}
			
			if(request.get("ticketNo")!=null) {
				cases.setTicketNo(request.get("ticketNo").toString());
			}
			if (request.get("pendingResponsibility") != null) {
				int pendingResponsibility = (Integer) request.get("pendingResponsibility");
				cases.setPendingResponsibility(new PendingResponsibility(Long.valueOf(pendingResponsibility)));
			}
			if (request.get("btProblemMaster") != null) {
				List<Integer> lst = new ArrayList<>();
				lst.add(1);
				lst.add(2);
				lst.add(3);
				System.out.println(request.get("btProblemMaster"));
				if(lst.contains(request.get("btProblemMaster"))){
					cases.setBtProblemMaster(
							new BtProblemMaster(Integer.parseInt(request.get("btProblemMaster").toString())));
				} else {
					throw new BadRequestException("No Problem Type Found");
				}
			}
			dbpreres.saveDetails(cases, user);

			if (request.get("caseProblemReported") != null) {

				List<Map<String, Object>> ls = (List<Map<String, Object>>) request.get("caseProblemReported");
				for (int i = 0; i < ls.size(); i++) {
					BtPreRegisterReport caseProblemReported = new BtPreRegisterReport();
					caseProblemReported.setProblemName(ls.get(i).get("problemName").toString());
					System.out.println(ls.size());
					System.out.println(ls.get(i).get("problemName").toString());
					caseProblemReported.setPreRegistrationCases(cases);
					dbpreReport.saveDetails(caseProblemReported,user);
				}
			}
			
			return dbpreres.getByCaseId(cases.getId());
		} else {
			throw new UnauthoriseException("You are not authorize..");
		}
	}

	@Override
	@Transactional
	public Map<String, Object> updateObject(Map<String, Object> request, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		boolean edit = dbProfile.hasObjectAccess(user.getUsername(), BtPreRegistrationCases.class,
				UserProfileRepository.EDIT);
		BtPreRegistrationCases btPreRegistrationCases = null;
		if (edit) {
			btPreRegistrationCases = dbpreres.getCasesWithId(Long.valueOf(request.get("id").toString()));
			if (btPreRegistrationCases != null) {
				if (!btPreRegistrationCases.getOwnerDetails().getEmailId().equalsIgnoreCase(user.getUsername())) {
					Map<String, String> group = dbProfile.getAccountAndRoleGroup(user);
					if (group.get("roleGroup").equalsIgnoreCase("CUSTOMERCARE")
							|| group.get("roleGroup").equalsIgnoreCase("ADMIN")) {

					} else {
						throw new com.pratikshat.exicom.exception.BadRequestException(
								"Your are not authorised to edit this record");
					}
				}
				if (btPreRegistrationCases.getLastAction() != null) {
					throw new com.pratikshat.exicom.exception.BadRequestException("You can't edit this record");
				}
				List<Integer> list = new ArrayList<>();
				list.add(2); // Closed
				list.add(4); // Rejected
				if (list.contains(btPreRegistrationCases.getStatus().getId())) {
					throw new com.pratikshat.exicom.exception.BadRequestException("Your can't edit this record");
				}
				if (request.get("assetsId") != null
						&& !request.get("assetsId").toString().equalsIgnoreCase("")) {
					BtAssets assets = null;
					assets = dbAssets.getAssetsForAppointment(Long.parseLong(request.get("assetsId").toString()));
					btPreRegistrationCases.setAssets(assets);
					if (assets != null && assets.getStatus() != null) {
						if (assets.getStatus().getAssetsStatusId() == 1) { // Dismantle
							throw new com.pratikshat.exicom.exception.BadRequestException("Asset is Dismantle");
						}
					}
					btPreRegistrationCases.setMissingAssets("");
					// Find asset
					
//					long checkAsset = dbCase.checkAssetStatusInAllCaseInUpdate(assets.getId(),_caseDetails.getId());
//
//					if (checkAsset > 0)
//						throw new BadRequestException("Case already logged on this Asset");

					// Find Asset with Open Status in BRC Case

//					long checkAssetInBRC = dbBRCCase.checkAssetStatusIbBRCCase(assets.getId());
//
//					if (checkAssetInBRC > 0)
//						throw new BadRequestException("Case already logged on this Asset");

					//
					
				} else {
					btPreRegistrationCases.setAssets(null);
					List<Long> caseReasonList = new ArrayList<>();
					caseReasonList.add(7L); // Exceptional Case
					caseReasonList.add(8L); // Installation
					caseReasonList.add(51L); // Warehouse Health check-ups
					caseReasonList.add(48L); // Site Survey
					caseReasonList.add(47L); // Battery Bank Charging
//					caseReasonList.add(40L); // Acceptance Testing
//					caseReasonList.add(42L); // SMPS Kitting Warehouse

//					if (caseReasonList.contains(Long.valueOf(request.get("caseReasonId").toString()))) {
						if (Long.valueOf(request.get("caseReasonId").toString()) == 7L
								|| (Long.valueOf(request.get("caseReasonId").toString()) == 8L)) {
							if (request.get("missingAssets") == null) {
								throw new com.pratikshat.exicom.exception.BadRequestException(
										"Please fill missing asset");
							}
						}
//					} else {
//						throw new com.pratikshat.exicom.exception.BadRequestException("Please select Asset");
//					}
				}
				BtSiteDetails site = null;
				if (request.get("siteId") != null) {
					int siteID = (Integer) request.get("siteId");
					site = dbSite.getSiteWithId(Long.valueOf(siteID));
					btPreRegistrationCases.setSite(site);
				} else {
					throw new com.pratikshat.exicom.exception.BadRequestException("Site does not exist");
				}
				if (request.get("assetsId") != null
						&& !request.get("assetsId").toString().equalsIgnoreCase("")) {
					BtAssets assets = dbAssets.getAssetsForAppointment(Long.parseLong(request.get("assetsId").toString()));
					if (assets.getSiteDetails() != null) {
						if (assets.getSiteDetails().getId() != Long.parseLong(request.get("siteId").toString())) {
							throw new com.pratikshat.exicom.exception.BadRequestException(
									"Given site mismatch with the assets installed on the site");
						}
					} else {
						throw new com.pratikshat.exicom.exception.BadRequestException("Site does not exist");
					}
				}
				ContactDetails contact = dbContact.getWithContactIdAndAccountId(
						Long.valueOf(request.get("contactPersonId").toString()),
						site.getAccount().getSapCustomerNumber());
				btPreRegistrationCases.setContactPerson(contact);
				if(request.get("missingAssets")!=null) {
					
					CaseReasonDetails caseReason=dbCaseReson.getCaseReasonWithId(Long.parseLong(request.get("caseReasonId").toString()));
					btPreRegistrationCases.setCaseReason(caseReason);
					
					
				}else {
				try {
					long caseTypeId = Long.parseLong(request.get("caseTypeId").toString());
					List<CaseReasonDetails> reasons = dbCaseReson.getResonWithCasetypeAndDomainId(Long.valueOf(caseTypeId),
							(int) request.get("caseRecordTypeId"));
					List<Long> reasonList = new ArrayList<>();
					for (int i = 0; i < reasons.size(); i++) {
						CaseReasonDetails reason = reasons.get(i);
						reasonList.add(reason.getReasonId());
					}
					if (request.get("caseReasonId") != null
							&& !reasonList.contains(Long.parseLong(request.get("caseReasonId").toString()))) {
						throw new com.pratikshat.exicom.exception.BadRequestException("Wrong case reason");
					}
				} catch (NoResultException ex) {
					throw new com.pratikshat.exicom.exception.BadRequestException("Wrong case reason");
				}
				btPreRegistrationCases.setCaseReason(new com.pratikshat.exicom.entity.CaseReasonDetails(
						Long.parseLong(request.get("caseReasonId").toString())));
				}
				try {
					List<Integer> reasonList = dbCaseStage.getStageIdWithStatusAndDomainId(
							(int) request.get("caseStatusId"), (int) request.get("caseRecordTypeId"));

					if (!reasonList.contains(request.get("caseStageId"))) {
						throw new com.pratikshat.exicom.exception.BadRequestException("Wrong case stage");
					}
				} catch (NoResultException ex) {
					throw new com.pratikshat.exicom.exception.BadRequestException("Wrong case stage");
				}
				
				if ( !request.get("typeChoice").toString().equalsIgnoreCase("0")) {
					BtAssetsTypeChoice assetsTypeChoice = dbTypeChoice
							.getTypeById(Integer.parseInt(request.get("typeChoice").toString()));
					btPreRegistrationCases.setAssetsTypeChoice(assetsTypeChoice);
					if (assetsTypeChoice == null) {
						throw new BadRequestException("No Type Choice found");
					}
				}
				if (request.get("serviceResourceId") != null) {
					ServiceRecource serviceRecource = null;
					serviceRecource = dbserviceRes.getServiceResourceWithIdAndServiceid(
							Long.valueOf(request.get("serviceCenterId").toString()),
							Long.valueOf(request.get("serviceResourceId").toString()));
					btPreRegistrationCases.setEngineer(serviceRecource);
				}
				btPreRegistrationCases.setCaseType(
						new com.pratikshat.exicom.entity.CaseTypes(Long.valueOf(request.get("caseTypeId").toString())));
				if ((int) request.get("caseReasonId") == 8 || (int) request.get("caseReasonId") == 18) {

					if(request.get("poDate")!=null) {
						try {
							btPreRegistrationCases.setPODate(inputSdf.parse(request.get("poDate").toString()));
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
						if (request.get("poamount") != null)
							btPreRegistrationCases.setPoamount(request.get("poamount").toString());
						if (request.get("lineitem") != null)
							btPreRegistrationCases.setLineitem(request.get("lineitem").toString());
						if(request.get("poNumber")!=null) 
							btPreRegistrationCases.setPONumber(request.get("poNumber").toString());
				}
				btPreRegistrationCases.setOrigin(
						new com.pratikshat.exicom.entity.CaseOriginDetails((int) request.get("caseOriginId")));
				btPreRegistrationCases.setPriority(
						new com.pratikshat.exicom.entity.CasePriorityDetails((int) request.get("casePriorityId")));
				if (request.get("remarks") != null) {
					btPreRegistrationCases.setRemarks(request.get("remarks").toString());
				}
				if(request.get("noOfSmr")!=null)
					btPreRegistrationCases.setNoOfsmr(request.get("noOfSmr").toString());
				btPreRegistrationCases
						.setStage(new com.pratikshat.exicom.entity.CaseStageDetails((int) request.get("caseStageId")));
				btPreRegistrationCases.setStatus(
						new com.pratikshat.exicom.entity.CaseStatusDetails((int) request.get("caseStatusId")));
				if (request.get("missingAssets") != null) {
					btPreRegistrationCases.setMissingAssets(request.get("missingAssets").toString());
				}
				if (request.get("serviceCenterId") != null) {
					btPreRegistrationCases.setServiceCenter(new com.pratikshat.exicom.entity.LegalEntity(
							Long.valueOf(request.get("serviceCenterId").toString())));
				}
				if (request.get("otherProblemReported") != null) {
					btPreRegistrationCases.setOtherProblemReported(request.get("otherProblemReported").toString());
				}
				if (request.get("isAssignByAssignmentRule") != null) {
					btPreRegistrationCases.setAssignByAssignmentRule((boolean) request.get("isAssignByAssignmentRule"));
				}
				if (request.get("inputFromEng") != null) {
					btPreRegistrationCases.setInputFromEng(request.get("inputFromEng").toString());
				}
				if (request.get("internalRemark") != null) {
					btPreRegistrationCases.setInternalRemark(request.get("internalRemark").toString());
				}
				if (request.get("RJILTT01No") != null) {
					btPreRegistrationCases.setRJILTT01No(request.get("RJILTT01No").toString());
				}
				if (request.get("pendingResponsibility") != null) {
					btPreRegistrationCases.setPendingResponsibility(
							new PendingResponsibility(Long.valueOf(request.get("pendingResponsibility").toString())));
				}
				
				
				///////////////////////////////////////////////////updat///////////////////////////////////
				
				if(request.get("openDate")!=null) {
					
						btPreRegistrationCases.setOpenDate(request.get("openDate").toString());
		
				}
				
				if(request.get("ticketNo")!=null) {
					btPreRegistrationCases.setTicketNo(request.get("ticketNo").toString());
				}

				if ((int) request.get("caseStatusId") == 2) { // Closed
					btPreRegistrationCases.setCloseDate(new Date());
					btPreRegistrationCases
							.setClosedBy(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
				}
				if (request.get("btProblemMaster") != null) {
					List<Integer> lst = new ArrayList<>();
					lst.add(1);
					lst.add(2);
					lst.add(3);
					System.out.println(request.get("btProblemMaster"));
					if(lst.contains(request.get("btProblemMaster"))){
						btPreRegistrationCases.setBtProblemMaster(
								new BtProblemMaster(Integer.parseInt(request.get("btProblemMaster").toString())));
					} else {
						throw new BadRequestException("No Problem Type Found");
					}
				}

				dbpreres.updateDetails(btPreRegistrationCases, user);

				if (request.get("caseProblemReported") != null) {
					////// delete Query
					dbpreReport.deleteReport(btPreRegistrationCases.getId());
					List<Map<String, Object>> ls = (List<Map<String, Object>>) request.get("caseProblemReported");
					for (int i = 0; i < ls.size(); i++) {
						BtPreRegisterReport caseProblemReported = new BtPreRegisterReport();
						caseProblemReported.setProblemName(ls.get(i).get("problemName").toString());
						caseProblemReported.setPreRegistrationCases(btPreRegistrationCases);
						dbpreReport.saveDetails(caseProblemReported,user);
					}
				}
			}
		} else {
			throw new UnauthoriseException("You are Not authorized");
		}
		return dbpreres.getSingleCase(btPreRegistrationCases.getId());
	}

	@Override
	public List<Map<String, Object>> searchForList(String search, LoginUser user)
			throws BadRequestException, UnauthoriseException {

		if (search != null && !search.equalsIgnoreCase("")) {
			List<Object> value = new ArrayList<>();
			String _query = "SELECT prcase.`id` AS preCaseId,prcase.`manual_id` AS preCaseManualId ,ast.`manual_id` AS assetsSerialNumber\r\n"
					+ "FROM `bt_pre_registration_cases` prcase JOIN `bt_assets` ast ON ast.`id` = prcase.`assets_id`\r\n"
					+ "	WHERE";
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append(_query);
			stringBuilder.append(" prcase.`manual_id` like ?");
			value.add("%" + search + "%");
			stringBuilder.append(" or ast.`manual_id` like ?");
			value.add("%" + search + "%");
			return jdbc.queryForList(stringBuilder.toString(), value.toArray());
		}
		return new ArrayList<Map<String, Object>>();
	}

	@Override
	public List<Map<String, Object>> advanceSearch(Map<String, Object> searchKey, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		List<Object> value = new ArrayList<>();
		String _query = "SELECT \r\n" + "	prcase.`id` AS preCaseId,prcase.`manual_id` AS preCaseManualId ,\r\n"
				+ "	bd.`business_domain_id` AS caseRecordTypeId,bd.`business_domain_name` AS caseRecordTypeName, ct.`case_type_id` AS caseTypeID,\r\n"
				+ "	cbd.`business_domain_id` AS verticalTypeId, cbd.`business_domain_name` AS verticalTypeName,ct.`case_types_name` AS caseTypeName,\r\n"
				+ "	cr.`reason_id` AS caseReasonId,cr.`case_reason_details_name` AS caseReasonName,ast.`manual_id` AS assetsSerialNumber,\r\n"
				+ "	btp.`manual_id` AS assetsPartCode,btp.`pdoduct_description` AS assetDescription,\r\n"
				+ "	csd.`case_status_name` AS caseStatusName,csd.`id` AS caseStatusId,\r\n"
				+ "	cod.`case_origin_id` AS caseOriginId , cod.`case_orign_name` AS caseOriginName,\r\n"
				+ "	cstage.`id` AS caseStageId , cstage.`case_stage_name` AS caseStageName,\r\n"
				+ "	cpd.`case_priority_id` AS casePriorityId, cpd.`case_priority_name` AS casePriorityName,\r\n"
				+ "	cd.`contact_id` AS contactPersonId,cd.`first_name` AS contactPersonFirstName, cd.`middle_name` AS contactPersonMiddleName,\r\n"
				+ "	cd.`last_name` AS contactPersonLastName , cd.`email_id` AS contactPersonEmailId, cd.`mobile` AS contactPersonContactNo,\r\n"
				+ "	prcase.`no_ofsmr` AS noOfSmr, prcase.`remarks` AS remarks,prcase.`created_date` AS createdDate, prcase.`close_date` AS closedDate,\r\n"
				+ "	cby.`email_id` AS createdByEmailId, cby.`first_name` AS createdByFirstName,cby.`middle_name` AS createdByMiddleName,cby.`last_name`AS createdByLastName,\r\n"
				+ "	mby.`email_id` AS modifiedByEmailId, mby.`first_name` AS modifiedByFirstName,mby.`middle_name` AS modifiedByMiddleName,mby.`last_name`AS modifiedByLastName,\r\n"
				+ "	prcase.`other_problem_reported` AS otherProblemReported,\r\n"
				+ "	pr.`pending_responsibility_id` AS pendingResponsibilityId, pr.`pending_responsibility_name` AS pendingResponsibility,\r\n"
				+ "	prcase.`rjiltt01no` AS RJIL_TT01_No,\r\n"
				+ "	ac.`sap_customer_number` AS accountSapNumber, ac.`name` AS accountName,\r\n"
				+ "	pac.`sap_customer_number`  AS primaryAccountSapNumber, pac.`name` AS primaryAccountName,\r\n"
				+ "	site.`manual_id` AS siteManualId, site.`site_name` AS siteName, site.`id` AS siteId,\r\n"
				+ "	se.`service_resource_id` AS serviceResourceId,se.`name` AS serviceResourceName,\r\n"
				+ "	le.`legal_entity_id` AS serviceCenterId, le.`legal_entity_name` AS serviceCenterName,\r\n"
				+ "	prcase.`missing_assets` AS missingAssets, prcase.`is_assign_by_assignment_rule` AS isAssignByAssignmentRule,\r\n"
				+ "	prcase.`input_from_eng` AS inputFromEng, prcase.`internal_remark` AS internalRemark, prcase.`last_action` AS lastAction,\r\n"
				+ "	prcase.`last_action_comment` AS lastActionComment,\r\n"
				+ "	own.`first_name` AS caseOwnerFirstName,own.`middle_name` AS caseOwnerMiddleName,own.`last_name`AS caseOwnerLastName,own.`email_id` AS ownerEmailId,\r\n"
				+ "	btcase.`manual_id` AS caseManualId\r\n" + "	\r\n" + " \r\n"
				+ "	FROM `bt_pre_registration_cases` prcase\r\n"
				+ "	JOIN `bt_assets` ast ON ast.`id` = prcase.`assets_id`\r\n"
				+ "	LEFT JOIN `bt_products` btp ON btp.`id`=ast.`products_id`\r\n"
				+ "	JOIN `bt_site_details` site ON site.`id`=prcase.`site_id`\r\n"
				+ "	LEFT JOIN `accounts` ac ON ac.`sap_customer_number` = site.`account_sap_customer_number`\r\n"
				+ "	LEFT JOIN `accounts` pac ON pac.`sap_customer_number` = site.`primary_account_sap_customer_number`\r\n"
				+ "	JOIN `business_domain` bd ON bd.`business_domain_id` = prcase.`business_domain_business_domain_id`\r\n"
				+ "	JOIN `case_types` ct ON ct.`case_type_id` = prcase.`case_type_case_type_id`\r\n"
				+ "	LEFT JOIN `business_domain` cbd ON cbd.`business_domain_id`=ct.`vertical_business_domain_id`\r\n"
				+ "	JOIN `case_reason_details` cr ON cr.`reason_id`=prcase.`case_reason_reason_id`\r\n"
				+ "	JOIN `contact_details` cd ON cd.`contact_id`= prcase.`contact_person_contact_id`\r\n"
				+ "	JOIN `case_status_details` csd ON csd.`id` = prcase.`status_id`\r\n"
				+ "	LEFT JOIN `pending_responsibility` pr ON pr.`pending_responsibility_id`=prcase.`pending_responsibility_pending_responsibility_id`\r\n"
				+ "	JOIN `case_stage_details` cstage ON cstage.`id` = prcase.`stage_id`\r\n"
				+ "	JOIN `case_origin_details` cod ON cod.`case_origin_id` = prcase.`origin_case_origin_id`\r\n"
				+ "	JOIN `case_priority_details` cpd ON cpd.`case_priority_id`=`priority_case_priority_id`\r\n"
				+ "	JOIN `legal_entity` le ON le.`legal_entity_id`=prcase.`service_center_legal_entity_id`\r\n"
				+ "	JOIN `service_recource` se ON se.`service_resource_id`=prcase.`engineer_service_resource_id`\r\n"
				+ "	LEFT JOIN `bt_case` btcase ON btcase.`prc_id`=prcase.`id`\r\n"
				+ "	JOIN `employee_details` cby ON cby.`email_id`=prcase.`created_by_email_id`\r\n"
				+ "	JOIN `employee_details` mby ON mby.`email_id`=prcase.`last_modified_by_email_id`\r\n"
				+ "	LEFT JOIN `employee_details` own ON own.`email_id`=prcase.`owner_details_email_id`" + "WHERE 1=1";

		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append(_query);
		if (searchKey.get("manualID") != null && !searchKey.get("manualID").toString().equalsIgnoreCase("")) {
			queryBuilder.append(" and prcase.`manual_id` like ?");
			value.add("%" + searchKey.get("manualID") + "%");
		}
		if (searchKey.get("siteManualId") != null && !searchKey.get("siteManualId").toString().equals("")) {
			queryBuilder.append(" and site.`manual_id` like ?");
			value.add("%" + searchKey.get("siteManualId") + "%");
		}
		if (searchKey.get("assetsSerialNumber") != null && !searchKey.get("assetsSerialNumber").toString().equals("")) {
			queryBuilder.append(" and ast.`manual_id` like ?");
			value.add("%" + searchKey.get("assetsSerialNumber") + "%");
		}
		if (searchKey.get("caseTypeName") != null && !searchKey.get("caseTypeName").toString().equals("")) {
			queryBuilder.append(" and ct.`case_types_name` like ?");
			value.add("%" + searchKey.get("caseTypeName") + "%");
		}
		if (searchKey.get("caseStatusName") != null && !searchKey.get("caseStatusName").toString().equals("")) {
			queryBuilder.append(" and csd.`case_status_name` like ?");
			value.add("%" + searchKey.get("caseStatusName") + "%");
		}
		if (searchKey.get("engineer") != null && !searchKey.get("engineer").toString().equals("")) {
			queryBuilder.append(" and se.`name` like ?");
			value.add("%" + searchKey.get("engineer") + "%");
		}
		if (searchKey.get("caseManualId") != null && !searchKey.get("caseManualId").toString().equals("")) {
			queryBuilder.append(" and btcase.`manual_id` like ?");
			value.add("%" + searchKey.get("caseManualId") + "%");
		}

		return jdbc.queryForList(queryBuilder.toString(), value.toArray());
	}

	@Override
	@Transactional
	public Map<String, Object> actionOnBtPreRegistrationCase(Map<String, Object> req , LoginUser user)
			throws UnauthoriseException, BadRequestException {
		BtPreRegistrationCases btPreRegistrationCases = null;

		btPreRegistrationCases = dbpreres.getCasesWithId(Long.parseLong(req.get("caseId").toString()));
		BtCase _caseExist = dbCase.getCaseUsingPerCase(Long.parseLong(req.get("caseId").toString()));
		if (_caseExist != null) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Case already registered on this");
		}
		if (req.get("action").toString().equalsIgnoreCase(BtPreRegistrationCasesRepository.GENERATE_CASE)) {
			if(btPreRegistrationCases.getAssets()!=null) {
				
				// Find asset PRC

				long checkAsset = dbCase.checkAssetStatusInAllCase(Long.valueOf(btPreRegistrationCases.getAssets().toString()));

				if (checkAsset > 0)
					throw new BadRequestException("Case already logged on this Asset");

				// Find Asset with Open Status in BRC Case

				long checkAssetInBRC = dbBRCCase.checkAssetStatusIbBRCCase(Long.valueOf(btPreRegistrationCases.getAssets().toString()));

				if (checkAssetInBRC > 0)
					throw new BadRequestException("Case already logged on this Asset");

				//
			}
			
			BtCase btCase = new BtCase();
			btCase.setBtAssets(btPreRegistrationCases.getAssets());
			btCase.setSite(btPreRegistrationCases.getSite());
			btCase.setContactPerson(btPreRegistrationCases.getContactPerson());
			btCase.setEngineer(btPreRegistrationCases.getEngineer());
			btCase.setCaseReason(btPreRegistrationCases.getCaseReason());
			btCase.setCaseType(btPreRegistrationCases.getCaseType());
			btCase.setPODate(btPreRegistrationCases.getPODate());
			btCase.setPONumber(btPreRegistrationCases.getPONumber());
			btCase.setNoOfsmr(btPreRegistrationCases.getNoOfsmr());
			btCase.setOrigin(btPreRegistrationCases.getOrigin());
			btCase.setPriority(btPreRegistrationCases.getPriority());
			btCase.setBusinessDomain(btPreRegistrationCases.getBusinessDomain());
			btCase.setRemarks(btPreRegistrationCases.getRemarks());
			btCase.setStage(btPreRegistrationCases.getStage());
			btCase.setStatus(btPreRegistrationCases.getStatus());
			btCase.setSynked(false);
			btCase.setMissingAssets(btPreRegistrationCases.getMissingAssets());
			btCase.setServiceCenter(btPreRegistrationCases.getServiceCenter());
			btCase.setOtherProblemReported(btPreRegistrationCases.getOtherProblemReported());
			btCase.setAssignByAssignmentRule(btPreRegistrationCases.isAssignByAssignmentRule());
			btCase.setInputFromEng(btPreRegistrationCases.getInputFromEng());
			btCase.setInternalRemark(btPreRegistrationCases.getInternalRemark());
			btCase.setRJILTT01No(btPreRegistrationCases.getRJILTT01No());
			btCase.setPendingResponsibility(btPreRegistrationCases.getPendingResponsibility());
			btCase.setAssetsTypeChoice(btPreRegistrationCases.getAssetsTypeChoice());
			btCase.setOwnerDetails(btPreRegistrationCases.getCreatedBy());
			btCase.setBtProblemMaster(btPreRegistrationCases.getBtProblemMaster());
			btCase.setOpenDate(btPreRegistrationCases.getOpenDate());
			btCase.setTicketNo(btPreRegistrationCases.getTicketNo());
			btCase.setPrc(new BtPreRegistrationCases(Long.parseLong(req.get("caseId").toString())));
			dbCase.saveDetails(btCase, user);
							
       				
			List<BtPreRegisterReport> value = dbpreReport.getListOfReport(Long.parseLong(req.get("caseId").toString()));

			if (value != null && !value.isEmpty()) {int i=1;
				for (BtPreRegisterReport pr : value) {
					
					System.out.println("count"+i++);
					BtCaseProblemReported caseProblemReported = new BtCaseProblemReported();
					caseProblemReported.setProblemName(pr.getProblemName());
					caseProblemReported.setCaseDetails(btCase);
					dbCase.saveDetails(caseProblemReported,user);
				}
			}
			
			
	///////////////////////// Sending SMS ////////////////////////////////////////////////////////
			

			com.pratikshat.exicom.entity.TemplateDetails templateDetails = null;
   					try {
       				javax.persistence.Query smsTemplateQuery = em.createQuery(
       				"select tmpd from TemplateDetails tmpd where tmpd.active=?1 and tmpd.templateType=?2 and tmpd.businessDomain=?3 and tmpd.eventName=?4",
       				com.pratikshat.exicom.entity.TemplateDetails.class);
       				smsTemplateQuery.setParameter(1,true);
       				smsTemplateQuery.setParameter(2,com.pratikshat.exicom.entity.TemplateType.SMS);
       				smsTemplateQuery.setParameter(3,new com.pratikshat.exicom.entity.BusinessDomain(2));
       				smsTemplateQuery.setParameter(4,"ESS_Case_Log");
       				
       				templateDetails = (com.pratikshat.exicom.entity.TemplateDetails) smsTemplateQuery.getSingleResult();
   					}catch(Exception ex)
   					{
   						
   					}
       				if(templateDetails!=null)
       				{
//		    	   com.pratikshat.exicom.entity.BtSiteDetails sites=em.createQuery("select st from BtSiteDetails st where st.id=?1",com.pratikshat.exicom.entity.BtSiteDetails.class).setParameter(1, btCase.getSite().getId()).getSingleResult();
		   		   java.util.List<com.pratikshat.exicom.entity.BtCaseProblemReported> pb=em.createQuery("select pr from BtCaseProblemReported pr join pr.caseDetails cs where cs.id=?1",com.pratikshat.exicom.entity.BtCaseProblemReported.class).setParameter(1, btCase.getId()).getResultList();
		       			
		                               
		       		/*	} catch (javax.persistence.NoResultException nex) {
		       				nex.printStackTrace();
		       			} catch (javax.persistence.NonUniqueResultException e) {
		       				e.printStackTrace();
		       			}*/
		       			java.lang.StringBuilder prob=new StringBuilder();

		       			
		       			if(pb!=null)
		       			{
		       				prob.append(pb.get(0).getProblemName().toString());

//		       				for(com.pratikshat.exicom.entity.BtCaseProblemReported prb:pb)
//		       				{
//		       					prob.append(prb.getProblemName().toString()+", ");
//		                                      /* throw new com.pratikshat.exicom.exception.BadRequestException("prob="+bcase.getId().toString());*/
//		       				}
		       			}
		       			

		       				String smsMessage = null;
		       			if (templateDetails != null) {
		       				smsMessage = templateDetails.getBody();
		       				smsMessage = smsMessage.replaceFirst("<var>", btCase.getManualId());
		       				smsMessage = smsMessage.replaceFirst("<var>", btCase.getSite().getManualId());
		                              if(btCase.getBtAssets()!=null)
		                              {
		       				smsMessage = smsMessage.replaceFirst("<var>", ""+ btCase.getBtAssets().getManualId());
		                                }
		                               else
		                                {
		       				smsMessage = smsMessage.replaceFirst("<var>", ""+btCase.getMissingAssets());
		                                }

		              String msgs=prob.toString();


		               if(msgs.length()>=30){
		                        msgs=  msgs.substring(0,29);
		                        
		                 }
		                   
		                  smsMessage = smsMessage.replaceFirst("<var>", msgs);
		               /*throw new com.pratikshat.exicom.exception.BadRequestException("prob="+bcase.getId().toString());*/
		       			}
		       			

		       			if (smsMessage != null && !smsMessage.equalsIgnoreCase("")) {
		       			/*	com.pratikshat.exicom.utils.SmsNotifier sNotifier = new com.pratikshat.exicom.utils.SmsNotifier(em, "9911100679",
		       					smsMessage, btCase.getOwnerDetails().getEmailId(), templateDetails.getTemplaeCode());
		       				sNotifier.sendNotification();*/
//			com.pratikshat.exicom.utils.SmsNotifier sNotifier1 = new com.pratikshat.exicom.utils.SmsNotifier(em, "9560919594",
//		       					smsMessage, btCase.getOwnerDetails().getEmailId(), templateDetails.getTemplaeCode());
//		       				sNotifier1.sendNotification();

		com.pratikshat.exicom.utils.SmsNotifier sNotifier2 = new com.pratikshat.exicom.utils.SmsNotifier(em, btCase.getSite().getCircle().getCircleManagerBattry().getMobile(),
		   					smsMessage, btCase.getSite().getCircle().getCircleManagerBattry().getEmailId(), templateDetails.getTemplaeCode());
		     			sNotifier2.sendNotification();
//		       				com.pratikshat.exicom.utils.SmsNotifier sNotifier1 = new com.pratikshat.exicom.utils.SmsNotifier(em, "9825272799",
//			       					smsMessage, btCase.getOwnerDetails().getEmailId(), templateDetails.getTemplaeCode());
//			       				sNotifier1.sendNotification();
//			       	com.pratikshat.exicom.utils.SmsNotifier sNotifier3 = new com.pratikshat.exicom.utils.SmsNotifier(em, "8210158154",
//				       					smsMessage, btCase.getOwnerDetails().getEmailId(), templateDetails.getTemplaeCode());
//				       				sNotifier3.sendNotification();
		       			
		       			}
       				}
		       			/*throw new com.pratikshat.exicom.exception.BadRequestException("prob="+prob);*/

		      /* 		}catch(Exception ex) {
		       			ex.printStackTrace();
		       		}*/
       				
       		///////////////////////////////////////// SMS/////////////////////////////////////////////
       				
       				
       				
			
			BusinessDomain buisnessModule = dbDomain.getDomainWithId(btCase.getBusinessDomain().getBusinessDomainId());
			BtSiteDetails btSiteDetails = dbSite.getSiteWithId(btCase.getSite().getId());
			String emailId = null;
			if (btSiteDetails != null) {
				if (btSiteDetails.getCircle() != null) {
					if (buisnessModule.getBusinessDomainId() == 1) { // Battery
						if (btSiteDetails.getCircle() != null) {
							if (btSiteDetails.getCircle().getCircleManagerBattry() != null) {
								emailId = btSiteDetails.getCircle().getCircleManagerBattry().getEmailId();
							}
						}
					}
				} else {
					throw new com.pratikshat.exicom.exception.BadRequestException("Circle does not exist");
				}
			} else {
				throw new com.pratikshat.exicom.exception.BadRequestException("Site does not exist");
			}
			List<Integer> statusList = new ArrayList<>();
			statusList.add(1); // New
			statusList.add(2); // Closed
			statusList.add(4); // Rejected

			if (statusList.contains(btPreRegistrationCases.getStatus().getId())) {
				BtCase caseDetail = dbCase.getCaseWihtId(btCase.getId());
				caseDetail.setOwnerDetails(btPreRegistrationCases.getCreatedBy());
			} else {
				if (emailId != null) {
					BtCase caseDetails = dbCase.getCaseWihtId(btCase.getId());
					caseDetails.setOwnerDetails(new com.pratikshat.exicom.entity.EmployeeDetails(emailId));
				} else {
					throw new com.pratikshat.exicom.exception.BadRequestException("Circle Manager Does not exist");
				}
			}
		}
		CaseStatusDetails status= statusDetailsRepository.getCaseStatusByCaseStatusName("Closed");
		btPreRegistrationCases.setStatus(status);
																				///Stage-2 -Resolved					
		CaseStageDetails stage= dbCaseStage.getCaseStageWithId(2);
		btPreRegistrationCases.setStage(stage); 
		if (req.get("action").toString().equalsIgnoreCase(BtPreRegistrationCasesRepository.IGNORE)
				|| req.get("action").toString().equalsIgnoreCase(BtPreRegistrationCasesRepository.GENERATE_CASE)) {
			btPreRegistrationCases.setLastAction(req.get("action").toString());
			btPreRegistrationCases.setLastActionComment(req.get("comment").toString());
			dbpreres.updateDetails(btPreRegistrationCases, user);
			BtPreRegistrationCaseActionLog actionLog = new BtPreRegistrationCaseActionLog();
			actionLog.setAction(req.get("action").toString());
			actionLog.setActionTakenBy(new EmployeeDetails(user.getUsername()));
			actionLog.setComment(req.get("comment").toString());
			actionLog.setPreRegistrationCases(btPreRegistrationCases);
			actionLog.setTimestamp(new Date());
			dbaction.saveDetails(actionLog, user);

		} else {
			throw new com.pratikshat.exicom.exception.BadRequestException("Action is wrong");
		}
		return dbpreres.getSingleCase(btPreRegistrationCases.getId());
	}
	

	@Override
	public Page<Map<String, Object>> getPrcBySiteId(long siteId, Pageable page) {
		return dbpreres.getAllCasesBySiteId(siteId,page);
	}
	@Override
	public Map<String, Object> getCaseBtPrcId(long id) {
		return dbCase.getCasesByPreRegistrationCase(id);
	}

	@Override
	public Page<BtPrcResponse> getAllCaseList(String action, Pageable page, LoginUser user) throws UnauthoriseException {
		if (dbProfile.hasObjectAccess(user.getUsername(), BtPreRegistrationCases.class, UserProfileRepository.VIEW_ALL)) {
			return dbpreres.getAllCaseWithAction(action, page);
		}
		if (dbProfile.hasObjectAccess(user.getUsername(),  BtPreRegistrationCases.class, UserProfileRepository.VIEW)) {
			List<String> _us = new ArrayList<String>();
			_us = dbProfile.getListOfUserAndChildByUser(user.getUsername());
			List<com.pratikshat.exicom.entity.EmployeeDetails> userLis = new ArrayList<com.pratikshat.exicom.entity.EmployeeDetails>();
			for (String u : _us) {
				userLis.add(new com.pratikshat.exicom.entity.EmployeeDetails(u));
			}
			return dbpreres.getAllCaseWithAction(userLis, action, page);
		}
		return Page.empty();
	}
	@Override
	public BtPrcResponse getByCaseId(LoginUser user, long caseid) throws BadRequestException, UnauthoriseException {
		boolean view=dbProfile.hasObjectAccess(user.getUsername(), BtCase.class, UserProfileRepository.VIEW);
		if(view) {
			BtPrcResponse _case = dbpreres.getByCaseId(caseid);
		if (_case != null) {
			_case.setProblemReported(dbProblem.getByCaseId(_case.getPrcCaseId()));
		}
		return _case;
		}else
			throw new UnauthoriseException("you are not Authorize");
		
	}

	@Override
	public Map<String, Object> saveObject(Map<String, Object> request, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		// TODO Auto-generated method stub
		return null;
	}
}

