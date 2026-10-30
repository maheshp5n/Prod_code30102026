package com.pratikshat.exicom.services.impl;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.pratikshat.exicom.entity.ApplicationNotificationDetails;
import com.pratikshat.exicom.entity.AssetsStatus;
import com.pratikshat.exicom.entity.BtAnotherServiceAppointment;
import com.pratikshat.exicom.entity.BtAssets;
import com.pratikshat.exicom.entity.BtAssetsWarrantyDetails;
import com.pratikshat.exicom.entity.BtCase;
import com.pratikshat.exicom.entity.BtCaseProblemReported;
import com.pratikshat.exicom.entity.BtPendingReasonCustomerBucket;
import com.pratikshat.exicom.entity.BtProducts;
import com.pratikshat.exicom.entity.BtRMADetails;
import com.pratikshat.exicom.entity.BtResolutionMethod;
import com.pratikshat.exicom.entity.BtSchUseOnlyDetails;
import com.pratikshat.exicom.entity.BtServiceAppointment;
import com.pratikshat.exicom.entity.BtServicePayout;
import com.pratikshat.exicom.entity.BtSiteDetails;
import com.pratikshat.exicom.entity.BtSolutionImparted;
import com.pratikshat.exicom.entity.BtSolutionImpartedProblem;
import com.pratikshat.exicom.entity.BusinessDomain;
import com.pratikshat.exicom.entity.CaseDetails;
import com.pratikshat.exicom.entity.CaseReasonDetails;
import com.pratikshat.exicom.entity.CaseStageDetails;
import com.pratikshat.exicom.entity.CaseStatusDetails;
import com.pratikshat.exicom.entity.CircleDetails;
import com.pratikshat.exicom.entity.ClaimStatus;
import com.pratikshat.exicom.entity.ContactDetails;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.PendingResponsibility;
import com.pratikshat.exicom.entity.ServiceAppointment;
import com.pratikshat.exicom.entity.ServiceAppointmentStatus;
import com.pratikshat.exicom.entity.ServiceRecource;
import com.pratikshat.exicom.entity.TemplateDetails;
import com.pratikshat.exicom.entity.TemplateType;
import com.pratikshat.exicom.entity.ToBeApproved;
import com.pratikshat.exicom.entity.UserProfile;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
//import com.pratikshat.exicom.model.response.BtSchOnlyResponse;
import com.pratikshat.exicom.model.response.BtSchOnlyResponseCopy;
import com.pratikshat.exicom.model.response.BtServiceAppointmentResponse;
import com.pratikshat.exicom.repository.ApplicationNotificationDetailsRepository;
import com.pratikshat.exicom.repository.BtAnotherServiceAppointmentRepository;
import com.pratikshat.exicom.repository.BtAssetsRepository;
import com.pratikshat.exicom.repository.BtAssetsWarrantyDetailsRepository;
import com.pratikshat.exicom.repository.BtCaseRepository;
import com.pratikshat.exicom.repository.BtPendingReasonCustomerBucketRepository;
import com.pratikshat.exicom.repository.BtProductsRepository;
import com.pratikshat.exicom.repository.BtResolutionMethodRepository;
import com.pratikshat.exicom.repository.BtRmaDetailsRepository;
import com.pratikshat.exicom.repository.BtSchUseOnlyDetailsRepository;
import com.pratikshat.exicom.repository.BtServiceAppointmentRepository;
import com.pratikshat.exicom.repository.BtSiteDetailsRepository;
import com.pratikshat.exicom.repository.BtSolutionImpartedProblemRepository;
import com.pratikshat.exicom.repository.BtSolutionImpartedRepository;
import com.pratikshat.exicom.repository.CaseReasonDetailsRepository;
import com.pratikshat.exicom.repository.CaseStageDetailsRepository;
import com.pratikshat.exicom.repository.ContactDetailsRepository;
import com.pratikshat.exicom.repository.ServiceAppointmentStatusRepository;
import com.pratikshat.exicom.repository.ServiceRecourseRepository;
import com.pratikshat.exicom.repository.TemplateDetailsRepository;
import com.pratikshat.exicom.repository.UploadedFilesRepository;
import com.pratikshat.exicom.repository.UserProfileRepository;
import com.pratikshat.exicom.services.BtServiceAppointmentService;
import com.pratikshat.exicom.utils.DateConverter;
import com.pratikshat.exicom.utils.SmsNotifier;

@Service
public class BtServiceAppointmentServiceImpl implements BtServiceAppointmentService {

	@Autowired
	BtSiteDetailsRepository dbSite;
	@Autowired
	private SimpMessagingTemplate messagingTemplate;
	@Autowired
	ServiceAppointmentStatusRepository dbServiceStatus;
	@Autowired
	JdbcTemplate jdbc;
	@Autowired
	BtCaseRepository btCaserepo;
	@Autowired
	BtAssetsRepository dbAssets;
	@Autowired
	BtAssetsWarrantyDetailsRepository dbWarr;
	@Autowired
	BtServiceAppointmentRepository serviceRepo;
	@Autowired
	CaseStageDetailsRepository dbstage;
	@Autowired
	UserProfileRepository dbprofile;
	@Autowired
	ServiceRecourseRepository dbserviceResource;
	@Autowired
	CaseReasonDetailsRepository dbCasereason;
	@Autowired
	BtAssetsWarrantyDetailsRepository warrRepo;
	@Autowired
	BtAssetsRepository assetsRepo;
	@Autowired
	CaseStageDetailsRepository dbCaseSatge;
	@Autowired
	ContactDetailsRepository dbContact;
	@Autowired
	TemplateDetailsRepository dbTemplate;
	@Autowired
	BtAnotherServiceAppointmentRepository dbAnotherservice;
	@Autowired
	BtProductsRepository dbProduct;
	@Autowired
	ApplicationNotificationDetailsRepository dbApplication;
	@Autowired
	BtRmaDetailsRepository dbRmaDetailsRepo;
	@Autowired
	UserProfileRepository dbProfileRepo;
	@Autowired
	BtSolutionImpartedRepository dbSolutionImpartedRepo;
	@Autowired
	BtResolutionMethodRepository dbResolutionMethodRepo;
	@Autowired
	BtSchUseOnlyDetailsRepository dbSch;
	@Autowired
	UploadedFilesRepository filesRepository;
	@Autowired
	BtPendingReasonCustomerBucketRepository dbPendingReasonCustomerBucketRepo;
	@Autowired
	BtSolutionImpartedProblemRepository dbSolutionImpartedProblemRepo;
	@PersistenceContext
	EntityManager entityManager;

	@Value("${feedbackurl}")
	String feedbackurl;

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	public Map<String, Object> getSingle(Object id, LoginUser user) throws BadRequestException, UnauthoriseException {
		boolean view = dbprofile.hasObjectAccess(user.getUsername(), BtServiceAppointment.class,
				UserProfileRepository.VIEW);
		boolean viewall = dbprofile.hasObjectAccess(user.getUsername(), BtServiceAppointment.class,
				UserProfileRepository.VIEW_ALL);
		if (view || viewall) {
			Long _id = Long.parseLong(id.toString());
			return serviceRepo.getSingleAppointment(_id);
		} else {
			throw new UnauthoriseException("You are not authorised");
		}
	}

	@Override
	public Page<Map<String, Object>> getPage(Pageable page, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		boolean view = dbprofile.hasObjectAccess(user.getUsername(), BtServiceAppointment.class,
				UserProfileRepository.VIEW);
		boolean viewall = dbprofile.hasObjectAccess(user.getUsername(), BtServiceAppointment.class,
				UserProfileRepository.VIEW_ALL);
		if (view || viewall) {
			return serviceRepo.getPage(page);
		} else {
			throw new UnauthoriseException("You are not authorised");
		}
	}

	@Override
	@Transactional(rollbackFor = { BadRequestException.class, UnauthoriseException.class,
			Exception.class }, propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED)
	public Map<String, Object> saveObject(Map<String, Object> request, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		boolean create = dbprofile.hasObjectAccess(user.getUsername(), BtServiceAppointment.class,
				UserProfileRepository.CREATE);
		BtServiceAppointment appointment = new BtServiceAppointment();
		if (create) {
			BtCase caseDetails = btCaserepo.getAppointment(Long.valueOf(request.get("caseId").toString()));
			List<Long> saStatusList = new ArrayList<>();
			saStatusList.add(1L); // Unassigned
			saStatusList.add(2L); // Scheduled
			saStatusList.add(3L); // Dispatched
			List<BtServiceAppointment> serviceAppointmentList = serviceRepo
					.getListWithCaseIDandStatus(caseDetails.getId(), saStatusList);

			if (serviceAppointmentList != null && serviceAppointmentList.size() > 0) {
				throw new com.pratikshat.exicom.exception.BadRequestException(
						"Service Appointment already present in Dispatch,Unassigned or Scheduled status");
			}
			BtSiteDetails btSiteDetails = dbSite.getSiteWithId(caseDetails.getSite().getId());
			EmployeeDetails caseCreatedBy = serviceRepo.caseCreatedByEmployee(caseDetails.getCreatedBy().getEmailId());
			BtAssets assets = null;
			if (caseDetails.getBtAssets() != null) {
				assets = dbAssets.getAssetsForAppointment(caseDetails.getBtAssets().getId());
			}
			appointment.setCaseDetails(caseDetails);
			appointment.setSyncFlag(false);
			appointment.setApprovedByFinance(false);
			appointment.setCircle(btSiteDetails.getCircle());
			if (caseDetails.getBtAssets() != null) {
				appointment.setAssets(assets);
				appointment.setProduct(assets.getProducts());
			}
			if (caseDetails.getCaseReason() != null) {
				try {
					CaseReasonDetails caseReason = dbCasereason
							.getCaseReasonWithId(caseDetails.getCaseReason().getReasonId());
					appointment.setCostCenter(caseReason.getCostCenter());
				} catch (NoResultException ex) {
					throw new com.pratikshat.exicom.exception.BadRequestException("Case Reason does not exist");
				} catch (Exception e) {
					throw new com.pratikshat.exicom.exception.BadRequestException(e.getMessage());
				}
			}
			String circleManagerEmailId = null;
			if (caseDetails.getBusinessDomain().getBusinessDomainId() == 1) { // Battery
				if (btSiteDetails.getCircle() != null) {
					if (btSiteDetails.getCircle().getCircleManagerBattry() != null) {
						appointment.setCircleManager(btSiteDetails.getCircle().getCircleManagerBattry());
						circleManagerEmailId = btSiteDetails.getCircle().getCircleManagerBattry().getEmailId();
					}
				}
			}
			appointment.setStatus(new ServiceAppointmentStatus(3));
			appointment.setPendingResponsibility(new PendingResponsibility(1));

			String name = null;
			if (caseCreatedBy.getFirstName() != null && !caseCreatedBy.getFirstName().equalsIgnoreCase("")) {
				name = caseCreatedBy.getFirstName();
			}

			if (caseCreatedBy.getMiddleName() != null && !caseCreatedBy.getMiddleName().equalsIgnoreCase("")) {
				name = name + " " + caseCreatedBy.getMiddleName();
			}

			if (caseCreatedBy.getLastName() != null && !caseCreatedBy.getLastName().equalsIgnoreCase("")) {
				name = name + " " + caseCreatedBy.getLastName();
			}
			appointment.setCustomerRepresentativeName(name);

			if (caseCreatedBy.getContact() != null) {
				appointment.setCustomerRepresentativeContact(caseCreatedBy.getContact().getMobile());
			}

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

			Calendar cal = Calendar.getInstance();
			String time = timeFormat.format(cal.getTime());
			appointment.setEarliestStartPermittedDate(dateFormat.format(cal.getTime()));
			appointment.setEarliestStartPermittedTime(time);
			cal.add(Calendar.DATE, 30);
			appointment.setDueDate(dateFormat.format(cal.getTime()));
			appointment.setDueTime(time);
			appointment.settFlag("N");
			appointment.setBusinessDomain(caseDetails.getBusinessDomain());
			appointment.setClaimStatus(new ClaimStatus(2L)); // To be Claimed
			appointment.setApprovedByFinance(false);
			CaseStageDetails caseStageDetails = null;
			try {
				caseStageDetails = dbCaseSatge.getCaseStageWithId(caseDetails.getStage().getId());
			} catch (NoResultException ex) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Case Stage does not exist");
			}
			if (caseDetails.isAssignByAssignmentRule()) {
				if (caseStageDetails.getCaseStageName().equalsIgnoreCase("Assigned To Engineer")) {
					ServiceRecource serviceRecource = dbserviceResource
							.getServiceResourceWithCaseId(caseDetails.getId());
					if (serviceRecource != null) {
						appointment.setOwnerDetails(serviceRecource.getUser());
						appointment.setServiceResource(serviceRecource);
					} else {
						try {
							ServiceRecource sch = dbserviceResource.getByEmail(circleManagerEmailId);
							appointment.setOwnerDetails(sch.getUser());
							appointment.setServiceResource(sch);
						} catch (NoResultException ex) {
							throw new com.pratikshat.exicom.exception.BadRequestException("SCH deos not exist");
						}
					}
				} else if (caseStageDetails.getCaseStageName().equalsIgnoreCase("Assigned To Dispatcher")) {
					try {
						ServiceRecource sch = dbserviceResource.getByEmail(circleManagerEmailId);
						appointment.setOwnerDetails(sch.getUser());
						appointment.setServiceResource(sch);
					} catch (NoResultException ex) {
						throw new com.pratikshat.exicom.exception.BadRequestException("SCH deos not exist");
					}
				} else {
					try {
						ServiceRecource sch = dbserviceResource.getByEmail(circleManagerEmailId);
						appointment.setOwnerDetails(sch.getUser());
						appointment.setServiceResource(sch);
					} catch (NoResultException ex) {
						throw new com.pratikshat.exicom.exception.BadRequestException("SCH deos not exist");
					}
				}

			} else {
				if (caseStageDetails.getCaseStageName().equalsIgnoreCase("Assigned To Engineer")) {
					ServiceRecource serviceRecource = dbserviceResource
							.getServiceResourceWithId(caseDetails.getEngineer().getServiceResourceId());
					if (serviceRecource != null) {
						appointment.setOwnerDetails(serviceRecource.getUser());
						appointment.setServiceResource(serviceRecource);
					} else {
						try {
							ServiceRecource sch = dbserviceResource.getByEmail(circleManagerEmailId);
							appointment.setOwnerDetails(sch.getUser());
							appointment.setServiceResource(sch);
						} catch (NoResultException ex) {
							throw new com.pratikshat.exicom.exception.BadRequestException("SCH deos not exist");
						}
					}

				} else if (caseStageDetails.getCaseStageName().equalsIgnoreCase("Assigned To Dispatcher")) {
					try {
						ServiceRecource sch = dbserviceResource.getByEmail(circleManagerEmailId);
						appointment.setOwnerDetails(sch.getUser());
						appointment.setServiceResource(sch);
					} catch (NoResultException ex) {
						throw new com.pratikshat.exicom.exception.BadRequestException("SCH deos not exist");
					}
				} else {
					try {
						ServiceRecource sch = dbserviceResource.getByEmail(circleManagerEmailId);
						appointment.setOwnerDetails(sch.getUser());
						appointment.setServiceResource(sch);
					} catch (NoResultException ex) {
						throw new com.pratikshat.exicom.exception.BadRequestException("SCH deos not exist");
					}
				}
			}
			serviceRepo.saveDetails(appointment, user);
			caseDetails.setStage(new CaseStageDetails(5));
			caseDetails.setStatus(new CaseStatusDetails(3));
			btCaserepo.saveDetails(caseDetails, user);

		}
		return serviceRepo.getSingleAppointment(appointment.getId());
	}

	@Override
	@Transactional(rollbackFor = { BadRequestException.class, UnauthoriseException.class,
			Exception.class }, propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED)
	public Map<String, Object> updateObject(Map<String, Object> request, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		BtServiceAppointment appointment = null;
		String assetsSerialNumber = null;// null;
		String productPartCode = null;
		BtCase _caseDetails = null;
		BtRMADetails rmaa = new BtRMADetails();

		Number serviceAppointmentId = (Integer) request.get("serviceAppointmentId");
		appointment = serviceRepo.getServiceAppointment(serviceAppointmentId.longValue());
		Number num = (Integer) request.get("serviceAppointmentId");
		_caseDetails = btCaserepo.getCaseForService(num.longValue());

		if (request != null) {

			////////////////////////////////////////////// ONLY FOR SCH
			////////////////////////////////////////////// ////////////////////////////////////////
			////////////////////////////////////////////// USE//////////////////////////////////////////////////
			boolean craete = dbProfileRepo.hasObjectAccess(user.getUsername(), BtSchUseOnlyDetails.class,
					UserProfileRepository.CREATE);
			if (craete && _caseDetails.getOwner().getEmailId().equalsIgnoreCase(user.getUsername())) {
				System.out.println("inside sch");
					if(Boolean.parseBoolean(request.get("billingRequired").toString())==true)
					{
						appointment.setBillingRequired(true);
					}
					else
					{
						appointment.setBillingRequired(false);
					}
				BtSchUseOnlyDetails btSchUseOnlyDetails = new BtSchUseOnlyDetails();
				System.out.println("CorrectiveAction");
				if (request.get("correctiveActions") == null) {
					throw new BadRequestException("Corrective Action Not Found");
				}
				if (request.get("resolutionMethod") == null) {
					throw new BadRequestException("Resolution Method Is Not Found");
				}

				btSchUseOnlyDetails.setCorrectiveActions(request.get("correctiveActions").toString());
				btSchUseOnlyDetails.setBtServiceAppointment(appointment);

				BtResolutionMethod btResolutionMethod = dbResolutionMethodRepo
						.getByResolutionMethodWithId(Long.parseLong(request.get("resolutionMethod").toString()));
				if (btResolutionMethod == null)
					throw new BadRequestException("Resolution method not found");

				btSchUseOnlyDetails.setResolutionMethod(btResolutionMethod);
				if (btResolutionMethod.getId() == 1) {
					if (request.get("sparePart") == null) {
						throw new BadRequestException("Spare part is not found");
					}
					if (request.get("sparePartQuantity") == null) {
						throw new BadRequestException("Spare part quantity is not found");
					}
					if (request.get("sparePartReplaced") == null) {
						throw new BadRequestException("Spare part replaced is not found");
					}
					if (request.get("sparePartReplacedFrom") == null) {
						throw new BadRequestException("Spare replaced from is null");
					}
				}
				if (request.get("sparePart") != null)
					btSchUseOnlyDetails.setSparePartName(request.get("sparePart").toString());
				if (request.get("sparePartQuantity") != null)
					btSchUseOnlyDetails
							.setSparePartQuantity(Integer.parseInt(request.get("sparePartQuantity").toString()));
				if (request.get("sparePartReplaced") != null)
					btSchUseOnlyDetails.setSparePartReplaced(request.get("sparePartReplaced").toString());
				if (request.get("sparePartReplacedFrom") != null) {
					btSchUseOnlyDetails.setSparePartReplacedFrom(request.get("sparePartReplacedFrom").toString());
				}
				if (request.get("customerRate") != null) {
					btSchUseOnlyDetails.setCustomerRate(request.get("customerRate").toString());
				}
				if (request.get("customerBillingRate") != null) {
					btSchUseOnlyDetails.setCustomerBillingRate(request.get("customerBillingRate").toString());
				}
				appointment.setSchFill(true);
				serviceRepo.updateDetails(appointment, user);
				dbSch.saveDetails(btSchUseOnlyDetails, user);
				System.out.println("next");
				if (request.get("solutionImparted") != null) {
					@SuppressWarnings("unchecked")
					List<Map<String, Object>> ls = (List<Map<String, Object>>) request.get("solutionImparted");
					for (int i = 0; i < ls.size(); i++) {
						BtSolutionImpartedProblem btSolutionImpartedProblem = new BtSolutionImpartedProblem();
						btSolutionImpartedProblem.setSolutionName(ls.get(i).get("solutionName").toString());
						btSolutionImpartedProblem.setBtSchUseOnlyDetails(btSchUseOnlyDetails);
						dbSch.saveDetail(btSolutionImpartedProblem, user);
					}
				}

//				}
				Map<String, String> role = dbprofile.getAccountAndRoleGroup(user);
				String roleGroup = role.get("roleGroup");

				if (appointment.getOwnerDetails().getEmailId().equalsIgnoreCase(user.getUsername())
						&& roleGroup.equalsIgnoreCase("SCH")) {
					if (dbprofile.hasObjectAccess(user.getUsername(), BtServiceAppointment.class,
							UserProfileRepository.EDIT)) {
						if (appointment.getStatus().getStatusId() == 4) {
							throw new BadRequestException("Service appointment is already completed");
						}

						if (!appointment.getOwnerDetails().getEmailId().equalsIgnoreCase(user.getUsername())) {
							throw new com.pratikshat.exicom.exception.UnauthoriseException(
									"You are not authorised to edit this Service Appointment");
						}
						if (appointment.getClaimStatus() != null) {
							List<Long> list = new ArrayList<Long>();
							list.add(Long.valueOf(2)); // To be claimed
							list.add(Long.valueOf(4)); // Rejected By SCH
							list.add(Long.valueOf(6)); // Rejected By Finance
							list.add(Long.valueOf(8)); // Rejected By RSH
							if (!list.contains(appointment.getClaimStatus().getClaimStatusId())) {
								throw new com.pratikshat.exicom.exception.UnauthoriseException(
										"You can't edit this record");
							}
						}

//						if (_caseDetails.getCaseReason().getReasonId() == 2) {
//							throw new BadRequestException("You can not close the exceptional case");
//						}

//						if (_caseDetails.getCaseReason().getReasonId() == 8) {
//							if (Integer.parseInt(request.get("statusId").toString()) == 4) { // Complete
//
//								assetsSerialNumber = request.get("assetsSerialNumber").toString();
//								if (assetsSerialNumber == null || request.get("partCode") == null) {
//									throw new BadRequestException(
//											"Please provide valid assets serial number and product part code");
//								}
//								BtAssets _assets = dbAssets.getAssetsForAppointment(
//										Long.parseLong(request.get("assetsSerialNumber").toString()));
//
//								if (_assets == null) {
//									throw new com.pratikshat.exicom.exception.BadRequestException(
//											"Asset does not exist");
//								}
//
//								if (_assets.getSiteDetails() != null) {
//									throw new com.pratikshat.exicom.exception.BadRequestException(
//											"Asset already installed on " + _assets.getSiteDetails().getManualId());
//								}
//								BtSiteDetails site = null;
//								if (_caseDetails.getSite().getId() != 0) {
//									site = dbSite.getSiteWithId(_caseDetails.getSite().getId());
//								} else {
//									throw new com.pratikshat.exicom.exception.BadRequestException(
//											"Site does not exist");
//								}
//								if (site == null) {
//									throw new com.pratikshat.exicom.exception.BadRequestException(
//											"Site does not exist");
//								}
//
//								BtAssetsWarrantyDetails assetsWarrantyDetails = dbWarr.getWithAssetsId(_assets.getId());
//
//								// if (_assets.getAccount() != site.getAccount()) {
//								// throw new com.pratikshat.exicom.exception.BadRequestException(
//								// "Site account and Asset account does not matched");
//								// }
//								///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//								if (assetsWarrantyDetails != null) {
//									Date installDate = new Date();
//									Calendar clendar = Calendar.getInstance(TimeZone.getTimeZone("IST"));
//									clendar.setTime(installDate);
//									clendar.add(Calendar.MONTH,
//											assetsWarrantyDetails.getWarrantyFromDateOfInstallationInMonth());
//									Date installationExpiry = clendar.getTime();
//									assetsWarrantyDetails.setInstallDate(installDate);
//									assetsWarrantyDetails.setInstallExpiryDate(installationExpiry);
//									_assets.setSiteDetails(new BtSiteDetails(_caseDetails.getSite().getId()));
//									BtProducts btProducts = dbProduct
//											.getProductWithId(Long.parseLong(request.get("partCode").toString()));
//									_assets.setProducts(btProducts);
//									_assets.setStatus(new AssetsStatus(2)); // Installed
//									Date warrantyExpiryDate = installationExpiry;
//									if (assetsWarrantyDetails.getDateOfDeliveryExpiryDate() != null) {
//										if (assetsWarrantyDetails.getDateOfDeliveryExpiryDate()
//												.getTime() < assetsWarrantyDetails.getInstallExpiryDate().getTime()) {
//											warrantyExpiryDate = assetsWarrantyDetails.getDateOfDeliveryExpiryDate();
//										}
//									}
//									assetsWarrantyDetails.setWarrantyExpiryDate(warrantyExpiryDate);
//									assetsRepo.updateDetails(_assets, user);
//									warrRepo.updateDetails(assetsWarrantyDetails, user);
//								}
//								_caseDetails.setBtAssets(_assets);
//								btCaserepo.updateDetails(_caseDetails, user);
//							}
//						} else {
//							if (_caseDetails.getBtAssets() != null) {
//
//								if (_caseDetails.getBtAssets().getProducts() != null) {
//									productPartCode = _caseDetails.getBtAssets().getProducts().getManualId();
//								}
//							}
//						}
						System.out.println(request.get("acknowledgedByName") + " Name");
						if (request.get("acknowledgedByName") != null)
							appointment.setAcknowledgedByName(request.get("acknowledgedByName").toString());
						if (request.get("acknowledgedByPhone") != null)
							appointment.setAcknowledgedByPhone(request.get("acknowledgedByPhone").toString());

						Number pending = (Integer) request.get("pendingResponsibilityId");
						appointment.setPendingResponsibility(new PendingResponsibility(pending.longValue()));
						if (pending.longValue() == 2) {
							if (request.get("customerPendingBucketId") != null) {
								BtPendingReasonCustomerBucket pendingCustomerBucket = dbPendingReasonCustomerBucketRepo
										.getByPendingReasonCustomerWithId(
												Long.parseLong(request.get("customerPendingBucketId").toString()));
								appointment.setPendingReasonCustomerBucket(pendingCustomerBucket);
							}
						}

						if (request.get("rma") != null) {
							appointment.setRma(Boolean.parseBoolean(request.get("rma").toString()));
						}

						if (request.get("pendingResource") != null) {
							appointment.setPendingResource(request.get("pendingResource").toString());
						}
						if (request.get("punchPointCustomer") != null)
							appointment.setPunchPointCustomer(request.get("punchPointCustomer").toString());
						if (request.get("punchPointExiom") != null)
							appointment.setPunchPointExiom(request.get("punchPointExiom").toString());
						if (request.get("remark") != null)
							appointment.setRemark(request.get("remark").toString());
						appointment.setStatus(
								new ServiceAppointmentStatus(Long.valueOf(request.get("statusId").toString())));
						appointment.setSyncFlag(false);
						///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

						//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
						if (request.get("latitude") != null)
							appointment.setLatitude(Double.valueOf(request.get("latitude").toString()));
						if (request.get("longitude") != null)
							appointment.setLongitude(Double.valueOf(request.get("longitude").toString()));
						if (request.get("address") != null)
							appointment.setAddress(request.get("address").toString());
						if (request.get("closedFromPlatform") != null) {
							appointment.setClosedFromPlatform(request.get("closedFromPlatform").toString());
							if (request.get("closedFromPlatform").toString().equalsIgnoreCase("APP")) {
								if (request.get("eSignature") != null) {
									appointment.setEsignature(request.get("eSignature").toString());
								} else {
									throw new BadRequestException("Please fill esignature");
								}
							}
						}
						if (request.get("assetsSerialNumber") != null
								&& request.get("assetsSerialNumber").toString().equalsIgnoreCase("")) {
							BtAssets _ast = dbAssets.getWithObject(Long.parseLong(assetsSerialNumber));
							appointment.setAssets(_ast);
							appointment.setProduct(_ast.getProducts());
						}
						ServiceAppointmentStatus _status = dbServiceStatus
								.getStatusWithId(Long.parseLong(request.get("statusId").toString()));
						if (_status.getStatusId() == 4 || _status.getStatusId() == 5) {
							
							if(_caseDetails.getCaseReason().isFsrMandatory())
							{
							 if (appointment.isFsrCreated()) {
								List<CaseStageDetails> _caseStageDetails = dbstage.getCaseStageByStatusIdAndDivision(2,
										appointment.getBusinessDomain().getBusinessDomainId());
//								btCaserepo.UpdateCase(_caseDetails.getBusinessDomain().getBusinessDomainId(),
//										_caseDetails.getId(), _caseDetails.getCaseReason().getReasonId(), user);
							} else {
								throw new BadRequestException("FSR is not created");
							}
							}
							
							if(_caseDetails.getCaseReason().isUploadfsrMandatory())
							{
							List<Map<String, Object>> fileUploded = filesRepository.findByObjectNameAndObjectId(
									"BtServiceAppointment", String.valueOf(appointment.getId()));
							if (fileUploded.isEmpty()) {
								throw new BadRequestException(
										"Please upload file before completing service Appointment");
							}
							}
						}
						if (!request.get("servicePayout").toString().equalsIgnoreCase("0")) {
							List<Long> servicePayout = new ArrayList<Long>();
							servicePayout.add(1L);
							servicePayout.add(2L);
							servicePayout.add(3L);
							servicePayout.add(4L);
							if (!servicePayout.contains(Long.parseLong(request.get("servicePayout").toString()))) {
								throw new BadRequestException("Service payout is not Found");
							}
							appointment.setBtServicePayout(
									new BtServicePayout(Long.valueOf(request.get("servicePayout").toString())));
						}

						if (appointment.getStatus() != null) {
							List<Long> list = new ArrayList<Long>();
							list.add(1L); // Unassigned
							list.add(2L); // Scheduled
							list.add(3L); // Dispatched
							if (!list.contains(appointment.getStatus().getStatusId())) {
								Date visitDate = null;
								System.out.println("inside status");
								if (request.get("visitDate") != null) {

									visitDate = DateConverter.convertToDate(request.get("visitDate").toString());

									if (visitDate.getTime() - appointment.getCreatedDate().getTime() < 0) {
										throw new BadRequestException(
												"Visit date must be greater or equal to the created date");
									}

									appointment.setVisitDate(visitDate);
								} else {
									throw new BadRequestException("Please enter visit date");
								}
							}
						}

						serviceRepo.updateDetails(appointment, user);
//						if(appointment.getStatus().getStatusId()==4) {
//							BtCase btCase=appointment.getCaseDetails();
//						
//							int count=	btCaserepo.values(btCase.getCaseType().getCaseTypesName(), btCase.getCaseReason().getCaseReasonDetailsName(),btCase.getId());
//							System.out.println(count+" count is ");
//							if(count==1) {
//								btCase.setStage(new CaseStageDetails(17));
//								btCaserepo.updateDetails(btCase, user);
//							}
//							
//						}
					}

				}
//				else {
//					throw new BadRequestException("Not allowed");
//				}
			} else {
				System.out.println("else");
				if (dbprofile.hasObjectAccess(user.getUsername(), BtServiceAppointment.class,
						UserProfileRepository.EDIT)) {
					if (appointment.getStatus().getStatusId() == 4) {
						throw new BadRequestException("Service appointment is already completed");
					}

					if (!appointment.getOwnerDetails().getEmailId().equalsIgnoreCase(user.getUsername())) {
						throw new com.pratikshat.exicom.exception.UnauthoriseException(
								"You are not authorised to edit this Service Appointment");
					}
					if (appointment.getClaimStatus() != null) {
						List<Long> list = new ArrayList<Long>();
						list.add(Long.valueOf(2)); // To be claimed
						list.add(Long.valueOf(4)); // Rejected By SCH
						list.add(Long.valueOf(6)); // Rejected By Finance
						list.add(Long.valueOf(8)); // Rejected By RSH
						if (!list.contains(appointment.getClaimStatus().getClaimStatusId())) {
							throw new com.pratikshat.exicom.exception.UnauthoriseException(
									"You can't edit this record");
						}
					}

//					if (_caseDetails.getCaseReason().getReasonId() == 8) {
//						if (Integer.parseInt(request.get("statusId").toString()) == 4) { // Complete
//
////							assetsSerialNumber = request.get("assetsSerialNumber").toString();
//							if (assetsSerialNumber == null) {
//								throw new BadRequestException(
//										"Please provide valid assets serial number and product part code");
//							}
//							BtAssets _assets = dbAssets.getAssetsForAppointment(
//									Long.parseLong(request.get("assetsSerialNumber").toString()));
//
//							if (_assets == null) {
//								throw new com.pratikshat.exicom.exception.BadRequestException("Asset does not exist");
//							}
//
//							if (_assets.getSiteDetails() != null) {
//								throw new com.pratikshat.exicom.exception.BadRequestException(
//										"Asset already installed on " + _assets.getSiteDetails().getManualId());
//							}
//							BtSiteDetails site = null;
//							if (_caseDetails.getSite().getId() != 0) {
//								site = dbSite.getSiteWithId(_caseDetails.getSite().getId());
//							} else {
//								throw new com.pratikshat.exicom.exception.BadRequestException("Site does not exist");
//							}
//							if (site == null) {
//								throw new com.pratikshat.exicom.exception.BadRequestException("Site does not exist");
//							}
//
//							BtAssetsWarrantyDetails assetsWarrantyDetails = dbWarr.getWithAssetsId(_assets.getId());
//
//							// if (_assets.getAccount() != site.getAccount()) {
//							// throw new com.pratikshat.exicom.exception.BadRequestException(
//							// "Site account and Asset account does not matched");
//							// }
//							///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//							if (assetsWarrantyDetails != null) {
//								Date installDate = new Date();
//								Calendar clendar = Calendar.getInstance(TimeZone.getTimeZone("IST"));
//								clendar.setTime(installDate);
//								clendar.add(Calendar.MONTH,
//										assetsWarrantyDetails.getWarrantyFromDateOfInstallationInMonth());
//								Date installationExpiry = clendar.getTime();
//								assetsWarrantyDetails.setInstallDate(installDate);
//								assetsWarrantyDetails.setInstallExpiryDate(installationExpiry);
//								_assets.setSiteDetails(new BtSiteDetails(_caseDetails.getSite().getId()));
//								BtProducts btProducts = dbProduct
//										.getProductWithId(Long.parseLong(request.get("partCode").toString()));
//								_assets.setProducts(btProducts);
//								_assets.setStatus(new AssetsStatus(2)); // Installed
//								Date warrantyExpiryDate = installationExpiry;
//								if (assetsWarrantyDetails.getDateOfDeliveryExpiryDate() != null) {
//									if (assetsWarrantyDetails.getDateOfDeliveryExpiryDate()
//											.getTime() < assetsWarrantyDetails.getInstallExpiryDate().getTime()) {
//										warrantyExpiryDate = assetsWarrantyDetails.getDateOfDeliveryExpiryDate();
//									}
//								}
//								assetsWarrantyDetails.setWarrantyExpiryDate(warrantyExpiryDate);
//								assetsRepo.updateDetails(_assets, user);
//								warrRepo.updateDetails(assetsWarrantyDetails, user);
//							}
//							_caseDetails.setBtAssets(_assets);
//							btCaserepo.updateDetails(_caseDetails, user);
//						}
//					} else {
					if (_caseDetails.getBtAssets() != null) {

						if (_caseDetails.getBtAssets().getProducts() != null) {
							productPartCode = _caseDetails.getBtAssets().getProducts().getManualId();
						}
					}
				}
				System.out.println(request.get("acknowledgedByName") + " Name");
				if (request.get("acknowledgedByName") != null)
					appointment.setAcknowledgedByName(request.get("acknowledgedByName").toString());
				if (request.get("acknowledgedByPhone") != null)
					appointment.setAcknowledgedByPhone(request.get("acknowledgedByPhone").toString());

				Number pending = (Integer) request.get("pendingResponsibilityId");
				appointment.setPendingResponsibility(new PendingResponsibility(pending.longValue()));
				if (pending.longValue() == 2) {
					if (request.get("customerPendingBucketId") != null) {
						BtPendingReasonCustomerBucket pendingCustomerBucket = dbPendingReasonCustomerBucketRepo
								.getByPendingReasonCustomerWithId(
										Long.parseLong(request.get("customerPendingBucketId").toString()));
						appointment.setPendingReasonCustomerBucket(pendingCustomerBucket);
					}
				}

				if (request.get("rma") != null) {
					appointment.setRma(Boolean.parseBoolean(request.get("rma").toString()));
				}

				if (request.get("pendingResource") != null) {
					appointment.setPendingResource(request.get("pendingResource").toString());
				}
				if (request.get("punchPointCustomer") != null)
					appointment.setPunchPointCustomer(request.get("punchPointCustomer").toString());
				if (request.get("punchPointExiom") != null)
					appointment.setPunchPointExiom(request.get("punchPointExiom").toString());
				if (request.get("remark") != null)
					appointment.setRemark(request.get("remark").toString());
				appointment.setStatus(new ServiceAppointmentStatus(Long.valueOf(request.get("statusId").toString())));
				appointment.setSyncFlag(false);
				///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

				//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				if (request.get("latitude") != null)
					appointment.setLatitude(Double.valueOf(request.get("latitude").toString()));
				if (request.get("longitude") != null)
					appointment.setLongitude(Double.valueOf(request.get("longitude").toString()));
				if (request.get("address") != null)
					appointment.setAddress(request.get("address").toString());
				if (request.get("closedFromPlatform") != null)
					appointment.setClosedFromPlatform(request.get("closedFromPlatform").toString());
				if (request.get("closedFromPlatform").toString().equalsIgnoreCase("APP")) {
					if (request.get("eSignature") != null) {
						appointment.setEsignature(request.get("eSignature").toString());
					} else {
						throw new BadRequestException("Please fill esignature");
					}
				}
				if (request.get("assetsSerialNumber") != null
						&& request.get("assetsSerialNumber").toString().equalsIgnoreCase("")) {
					BtAssets _ast = dbAssets.getWithObject(Long.parseLong(assetsSerialNumber));
					appointment.setAssets(_ast);
					appointment.setProduct(_ast.getProducts());
				}
				ServiceAppointmentStatus _status = dbServiceStatus
						.getStatusWithId(Long.parseLong(request.get("statusId").toString()));
				if (_status.getStatusId() == 4 || _status.getStatusId() == 5) {
					if(_caseDetails.getCaseReason().isFsrMandatory())
					{
					if (appointment.isFsrCreated()) {
						List<CaseStageDetails> _caseStageDetails = dbstage.getCaseStageByStatusIdAndDivision(2,
								appointment.getBusinessDomain().getBusinessDomainId());
//							btCaserepo.UpdateCase(_caseDetails.getBusinessDomain().getBusinessDomainId(),
//									_caseDetails.getId(), _caseDetails.getCaseReason().getReasonId(), user);
					} else {
						throw new BadRequestException("FSR is not created");
					}
					}
					if(_caseDetails.getCaseReason().isUploadfsrMandatory())
					{
					List<Map<String, Object>> fileUploded = filesRepository
							.findByObjectNameAndObjectId("BtServiceAppointment", String.valueOf(appointment.getId()));
					if (fileUploded == null) {
						throw new BadRequestException("Please upload file before completing service Appointment");
					}
					}
				}
				if (!request.get("servicePayout").toString().equalsIgnoreCase("0")) {
					List<Long> servicePayout = new ArrayList<Long>();
					servicePayout.add(1L);
					servicePayout.add(2L);
					servicePayout.add(3L);
					servicePayout.add(4L);
					if (!servicePayout.contains(Long.parseLong(request.get("servicePayout").toString()))) {
						throw new BadRequestException("Service payout is not Found");
					}
					appointment.setBtServicePayout(
							new BtServicePayout(Long.valueOf(request.get("servicePayout").toString())));
				}

				if (appointment.getStatus() != null) {
					List<Long> list = new ArrayList<Long>();
					list.add(1L); // Unassigned
					list.add(2L); // Scheduled
					list.add(3L); // Dispatched
					if (!list.contains(appointment.getStatus().getStatusId())) {
						System.out.println("inside status");
						Date visitDate = null;
						if (request.get("visitDate") != null) {

							visitDate = DateConverter.convertToDate(request.get("visitDate").toString());

							if (visitDate.getTime() - appointment.getCreatedDate().getTime() < 0) {
								throw new BadRequestException(
										"Visit date must be greater or equal to the created date");
							}

							appointment.setVisitDate(visitDate);
						} else {
							throw new BadRequestException("Please enter visit date");
						}
					}
				}
				appointment.setSchFill(false);
				serviceRepo.updateDetails(appointment, user);
//					if(appointment.getStatus().getStatusId()==4) {
//						BtCase btCase=appointment.getCaseDetails();
//					int count=	btCaserepo.values(btCase.getCaseType().getCaseTypesName(), btCase.getCaseReason().getCaseReasonDetailsName(),btCase.getId());
//					System.out.println(count+" count is ");
//					if(count==1) {
//							btCase.setStage(new CaseStageDetails(17));
//							btCaserepo.updateDetails(btCase, user);
//						}
//						
//					}
			}
		}
//		}

		return serviceRepo.getSingleAppointment(appointment.getId());
	}

	@Override
	public List<Map<String, Object>> searchForList(String search, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		if (search != null && !search.equalsIgnoreCase("")) {
			List<Object> value = new ArrayList<>();
			String _query = "SELECT appointment.`service_appointment_id` AS id ,appointment.`manual_id` AS manulaId,\r\n"
					+ "ca.`manual_id` AS caseManulalId\r\n" + "\r\n" + "FROM `bt_service_appointment` appointment\r\n"
					+ "JOIN `bt_case` ca ON ca.`id`=appointment.`case_details_id`\r\n" + "	WHERE";
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append(_query);
			stringBuilder.append(" appointment.`manual_id` like ?");
			value.add("%" + search + "%");
			stringBuilder.append(" or ca.`manual_id` like ?");
			value.add("%" + search + "%");
			return jdbc.queryForList(stringBuilder.toString(), value.toArray());
		}
		return new ArrayList<Map<String, Object>>();
	}

	@Override
	public List<Map<String, Object>> advanceSearch(Map<String, Object> searchKey, LoginUser user)
			throws BadRequestException, UnauthoriseException {

//		||dbProfileRepo.hasObjectAccess(user.getUsername(),
//		 BtServiceAppointment.class, UserProfileRepository.VIEW_ALL)) {
//		 return btprRepo.advanceSearch(searchRequest,
//		 dbProfileRepo.getLocationAndChildLocation(user.getUsername()),user);

		List<Object> value = new ArrayList<>();
		String _query = "SELECT `service_appointment_id` AS serviceAppointmentId,appointment.`manual_id` AS manualId,`fault_observed` AS faultObserved, \r\n"
				+ "						`punch_point_exiom` AS punchPointExiom ,`punch_point_customer` AS punchPointCustomer,`visit_date` AS visitDate,\r\n"
				+ "						`customer_representative_name` AS customerRepresentativeName,`customer_representative_contact` AS customerRepresentativeContact,\r\n"
				+ "						`remark` AS remark,pr.`pending_responsibility_name` AS pendingResponsibilityName,pr.`pending_responsibility_id` AS pendingResponsibilityId,\r\n"
				+ "						ca.`id` AS caseId,ca.`manual_id` AS caseIdManual ,ca.`case_reason_reason_id` AS caseReason,\r\n"
				+ "						product.`manual_id` AS assetsPartCode,product.`pdoduct_description` AS assetDescription,\r\n"
				+ "						lentity.`legal_entity_id` AS serviceCenterId,lentity.`manual_id` AS serviceCenterManualId,lentity.`legal_entity_name` AS serviceCenterName,\r\n"
				+ "						serRes.`name` AS serviceResourceName, serRes.`service_resource_id` AS serviceResourceId,\r\n"
				+ "						assets.`manual_id` AS assetsSapSerialNumber,ac.`sap_customer_number` AS accountSapNumber, ac.`name` AS accountName,\r\n"
				+ "						ct.`case_type_id` AS caseType,ct.`case_types_name`AS caseTypeName,crd.`case_reason_details_name` AS caseReasonName,cst.`case_status_name` AS caseStatus,\r\n"
				+ "						site.`id` AS siteId,site.`manual_id` AS siteIdManual,site.`site_name` AS siteName,\r\n"
				+ "						cby.`first_name` AS createdByFirstName,cby.`middle_name` AS createdByMiddleName, cby.`last_name` AS createdByLastName,cby.`email_id` AS createByEmailId,\r\n"
				+ "						mby.`email_id` AS modifiedByEmailId,mby.`first_name` AS modifiedByFirstName,mby.`middle_name` AS modifiedByMiddleName, mby.`last_name` AS modifiedByLastName,\r\n"
				+ "						sa.`id` AS ssaId,employee.`contact_id` AS employeeCode,embp.`business_place_name` AS businessPlace,bld.`boarding_name` AS grade,\r\n"
				+ "						own.`first_name` AS ownerFirstName,own.`middle_name` AS ownerMiddleName, own.`last_name` AS ownerLastName,own.`email_id` AS ownerEmailId,\r\n"
				+ "						sa.`name` AS ssaName, contact.`contact_id` AS contactId,`due_date` AS dueDate,`due_time` AS dueTime,\r\n"
				+ "						`t_flag` AS tFlag,contact.`first_name` AS contactFirstName,contact.`middle_name` AS contactMiddleName, contact.`last_name` AS contactLastName,\r\n"
				+ "						contact.`email_id` AS circleManagerContactEmailId, contact.`mobile` AS contactPhoneNumber,appointment.`approved_by_finance`AS approvedByFinance,\r\n"
				+ "						ser.`status_name` AS serviceAppointmentStatusName,ser.`status_id` AS serviceAppointmentStatusId,appointment.`is_approved_by_finance` AS isApprovedByFinanceCheck,\r\n"
				+ "						cd.`circle_name` AS circleName,contact.`first_name` AS circleManagerContactFirstName,contact.`middle_name` AS circleManagerContactMiddleName,\r\n"
				+ "						contact.`last_name` AS circleManagerContactLastName,contact.`contact_id` AS circleManagerContactId,\r\n"
				+ "						cstatus.`claim_status_id` AS claimStatusId,appointment.`fixed_payout` AS fixedPayout,\r\n"
				+ "						cstatus.`claim_status_name` AS claimStatusName,\r\n"
				+ "						appointment.`cost_center` AS costCenter,appointment.`wcc` AS wcc,\r\n"
				+ "						appointment.`wcc_id` AS wccId ,appointment.`wcc_manual_id` AS wccManualId,appointment.`acknowledged_by_name` AS acknowledgedByName,\r\n"
				+ "						appointment.`acknowledged_by_phone` AS acknowledgedByPhone,appointment.`created_date` AS createDate,\r\n"
				+ "						appointment.`owner_details_email_id`\r\n" + "						 \r\n"
				+ "						 \r\n" + "						FROM `bt_service_appointment` appointment \r\n"
				+ "						JOIN `pending_responsibility` pr ON pr.`pending_responsibility_id` =appointment.`pending_responsibility_pending_responsibility_id`\r\n"
				+ "						JOIN `bt_case` ca ON ca.`id`=appointment.`case_details_id`\r\n"
				+ "						LEFT JOIN `bt_products` product ON product.`id`=appointment.`product_id`\r\n"
				+ "						JOIN `legal_entity` lentity ON lentity.`legal_entity_id`= ca.`service_center_legal_entity_id`\r\n"
				+ "						JOIN `service_recource` serRes ON serRes.`service_resource_id` =appointment.`service_resource_service_resource_id`\r\n"
				+ "						JOIN `contact_details` getCon ON getCon.`contact_id` =serRes.`contact_contact_id`\r\n"
				+ "						JOIN `expense_management_business_place` embp ON embp.`business_place_id`=getCon.`business_place_business_place_id`\r\n"
				+ "						LEFT JOIN `boarding_loading_details` bld ON bld.`boarding_name` =getCon.`grade_name`\r\n"
				+ "						LEFT JOIN `bt_assets` assets ON assets.`id`=ca.`bt_assets_id`\r\n"
				+ "						LEFT JOIN `accounts` ac ON ac.`sap_customer_number`=assets.`account_sap_customer_number`\r\n"
				+ "						LEFT JOIN `case_types` ct ON ct.`case_type_id` =ca.`case_type_case_type_id`\r\n"
				+ "						JOIN `case_status_details` cst ON cst.`id`=ca.`status_id`\r\n"
				+ "						JOIN `bt_site_details` site ON site.`id` =ca.`site_id`\r\n"
				+ "						JOIN `employee_details` cby ON cby.`email_id`=appointment.`created_by_email_id`\r\n"
				+ "						JOIN `employee_details` mby ON mby.`email_id`=appointment.`last_modified_by_email_id`\r\n"
				+ "						JOIN `employee_details` own ON own.`email_id`=appointment.`owner_details_email_id`\r\n"
				+ "						JOIN `ssa` sa ON sa.`id` =site.`ssa_id`\r\n"
				+ "						JOIN `business_domain` bd ON bd.`business_domain_id` = appointment.`business_domain_business_domain_id`\r\n"
				+ "						JOIN `service_appointment_status` ser ON ser.`status_id`=`status_status_id`\r\n"
				+ "						JOIN `circle_details` cd ON cd.`circle_name` =appointment.`circle_circle_name`\r\n"
				+ "						JOIN `contact_details` contact ON contact.`contact_id` =ca.`contact_person_contact_id`\r\n"
				+ "						JOIN `claim_status` cstatus ON cstatus.`claim_status_id`=appointment.`claim_status_claim_status_id`\r\n"
				+ "						JOIN `contact_details` employee ON employee.`contact_id` =own.`contact_contact_id`\r\n"
				+ "						JOIN `case_reason_details` crd ON crd.`reason_id`=ca.`case_reason_reason_id`\r\n"
				+ "						LEFT JOIN `bt_service_payout` sp ON sp.`id`=appointment.`bt_service_payout_id`"
				+ "			WHERE 1=1";
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append(_query);
		// get data according to hierarchy
		if (!dbProfileRepo.hasObjectAccess(user.getUsername(), BtServiceAppointment.class,
				UserProfileRepository.VIEW_ALL)) {
			if (dbProfileRepo.hasObjectAccess(user.getUsername(), BtServiceAppointment.class,
					UserProfileRepository.VIEW)) {
				List<String> userList = dbprofile.getListOfUserAndChildByUser(user.getUsername());

				String users = "";
				for (int i = 0; i < userList.size(); i++) {

					if (i > 0)
						users += ",";
					System.out.println(userList.get(i));
					users += "'" + userList.get(i) + "'";
				}

				queryBuilder.append(" and  (appointment.`owner_details_email_id` IN (" + users + ") ");
				queryBuilder.append(" or  ca.`owner_details_email_id` IN (" + users + ")) ");
			}
		}
		// end

		if (searchKey.get("caseIdManual") != null && !searchKey.get("caseIdManual").equals("")) {
			queryBuilder.append(" and ca.`manual_id` like ?");
			value.add("%" + searchKey.get("caseIdManual") + "%");
		}
		if (searchKey.get("manualId") != null && !searchKey.get("manualId").equals("")) {
			queryBuilder.append(" and appointment.`manual_id` like ?");
			value.add("%" + searchKey.get("manualId") + "%");
		}
		if (searchKey.get("siteIdManual") != null && !searchKey.get("siteIdManual").equals("")) {
			queryBuilder.append(" and site.`manual_id` like ?");
			value.add("%" + searchKey.get("siteIdManual") + "%");
		}
		if (searchKey.get("circleName") != null && !searchKey.get("circleName").equals("")) {
			queryBuilder.append(" and cd.`circle_name` like ?");
			value.add("%" + searchKey.get("circleName") + "%");
		}
		if (searchKey.get("assetsSerialNumber") != null && !searchKey.get("assetsSerialNumber").equals("")) {
			queryBuilder.append(" and assets.`manual_id` like ?");
			value.add("%" + searchKey.get("assetsSerialNumber") + "%");
		}
		if (searchKey.get("serviceAppointmentStatusName") != null
				&& !searchKey.get("serviceAppointmentStatusName").equals("")) {
			queryBuilder.append(" and ser.`status_name` like ?");
			value.add(searchKey.get("serviceAppointmentStatusName") + "%");
		}
		if (searchKey.get("claimStatusName") != null && !searchKey.get("claimStatusName").equals("")) {
			queryBuilder.append(" and cstatus.`claim_status_name` like ?");
			value.add("%" + searchKey.get("claimStatusName") + "%");
		}
		if (searchKey.get("serviceResourceName") != null && !searchKey.get("serviceResourceName").equals("")) {
			queryBuilder.append(" and serRes.`name` like ?");
			value.add("%" + searchKey.get("serviceResourceName") + "%");
		}
		if (searchKey.get("caseType") != null && !searchKey.get("caseType").equals("")) {
			queryBuilder.append(" and ct.`case_types_name` like ?");
			value.add("%" + searchKey.get("caseType") + "%");
		}
		if (searchKey.get("caseReason") != null && !searchKey.get("caseReason").equals("")) {
			queryBuilder.append(" and crd.`case_reason_details_name` like ?");
			value.add("%" + searchKey.get("caseReason") + "%");
		}
		if (searchKey.get("createdFrom") != null && !searchKey.get("createdFrom").toString().equalsIgnoreCase("")
				&& searchKey.get("createdTo") != null && !searchKey.get("createdTo").toString().equalsIgnoreCase("")) {
			queryBuilder.append(
					" and DATE_FORMAT (appointment.`created_date`,'%Y, %m, %d') BETWEEN DATE_FORMAT( ? ,'%Y, %m, %d') AND DATE_FORMAT( ?,'%Y, %m, %d')");
			value.add(searchKey.get("createdFrom"));
			value.add(searchKey.get("createdTo"));
		}

		return jdbc.queryForList(queryBuilder.toString(), value.toArray());

		//
		// }
		// else
		// throw new UnauthoriseException("You are not authorised");
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = {
			BadRequestException.class, UnauthoriseException.class, RuntimeException.class, Exception.class,
			SQLException.class })
	public Map<String, Object> reAssignBtServiceAppointment(Map<String, Object> request, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		BtServiceAppointment btServiceAppointment = null;
		boolean create = dbprofile.hasObjectAccess(user.getUsername(), BtServiceAppointment.class,
				UserProfileRepository.EDIT);
		if (create) {
			btServiceAppointment = serviceRepo
					.getServiceAppointment(Long.valueOf(request.get("serviceAppointmentId").toString()));
			if (!btServiceAppointment.getCircleManager().getEmailId().equalsIgnoreCase(user.getUsername())) {
				throw new UnauthoriseException("You are not authorised");
			}
			List<Long> list = new ArrayList<Long>();
			list.add(1L); // Unassigned
			list.add(2L); // Scheduled
			list.add(3L); // Dispatched
			if (!list.contains(btServiceAppointment.getStatus().getStatusId())) {
				throw new BadRequestException("You can not re-assign this service appointment");
			}
			try {
				ServiceRecource serviceResource = dbserviceResource
						.getServiceResourceWithId(Long.valueOf(request.get("serviceResourceId").toString()));
				BtCase btCase = btCaserepo.getCaseWihtId(btServiceAppointment.getCaseDetails().getId());
				btCase.setEngineer(serviceResource);
				btCaserepo.updateDetails(btCase, user);
				btServiceAppointment.setOwner(serviceResource.getUser());
				btServiceAppointment.setServiceResource(serviceResource);
				btServiceAppointment.setReassign(true);
				try {
					ApplicationNotificationDetails notification = new ApplicationNotificationDetails();
					notification.setNofificationDate(new java.util.Date());
					notification.setNotificationActionUrl(
							"/bat_serviceappointment/" + btServiceAppointment.getServiceAppointmentId());
					notification.setNotificationMessage(
							String.format("Service Appointment %s has been send to you for approval",
									btServiceAppointment.getManualId()));
					notification.setNotificationRead(false);
					notification.setUserEmailId(serviceResource.getUser().getEmailId());
					dbApplication.saveDetails(notification, user);
					messagingTemplate.convertAndSend(notification);
				} catch (Exception ex) {
				}
				// try {
				// ContactDetails contactDetails = dbContact
				// .getWithContactWithId(serviceResource.getContact().getContactId());
				// TemplateDetails templateDetails = null;
				// String templateCode = "1007234255164312860";
				// templateDetails = dbTemplate.getTemplateByTpeAndCode(TemplateType.SMS,
				// templateCode);
				// String smsMessage = null;
				// if (templateDetails != null) {
				// smsMessage = templateDetails.getBody();
				// smsMessage = smsMessage.replaceFirst("<var>", serviceResource.getName());
				// smsMessage = smsMessage.replaceFirst("<var>",
				// btServiceAppointment.getManualId());
				// smsMessage = smsMessage.replaceFirst("<var>",
				// "https://pulse.exicom.in/#/serviceappointment/"
				// + btServiceAppointment.getServiceAppointmentId());
				// }
				// if (smsMessage != null && !smsMessage.equalsIgnoreCase("")) {
				// SmsNotifier sNotifier = new SmsNotifier(entityManager,
				// contactDetails.getMobile(), smsMessage,
				// user.getUsername(), templateCode);
				// sNotifier.sendNotification();
				// }
				// } catch (Exception ex) {
				// ex.printStackTrace();
				// }
			} catch (NoResultException ex) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Service Resource deos not exist");
			}
			serviceRepo.updateDetails(btServiceAppointment, user);
		} else {
			throw new UnauthoriseException("You are not Authoraise");
		}
		return serviceRepo.getSingleAppointment(btServiceAppointment.getServiceAppointmentId());
	}

//	@SuppressWarnings("unlikely-arg-type")
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = {
			BadRequestException.class, UnauthoriseException.class, RuntimeException.class, Exception.class,
			SQLException.class })
	public void requestForAnotherServiceAppointment(long caseId, String remark, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		BtCase _caseDetails = btCaserepo.getCaseWihtId(caseId);

		List<BtServiceAppointment> SAList = serviceRepo.getListWithCaseId(caseId);

		if (SAList == null) {
			throw new BadRequestException("Service Appointment does not exist");
		}

		if (SAList.isEmpty()) {
			throw new BadRequestException("Service Appointment does not exist");
		}
		List<Long> list = new ArrayList<Long>();
		list.add(5L);
		list.add(6L);
		list.add(7L);
		BtAnotherServiceAppointment anotherServiceAppointment = new BtAnotherServiceAppointment();
		
//		List<Integer> lst = new ArrayList<Integer>();
//		lst.add(3);
//		lst.add(7);
		
		
		/////////////////////////// Check Approve By SCH or Not
		/////////////////////////// kalindi///////////////////////////
		BtServiceAppointment st= SAList.get(SAList.size()-1);
		if(st.getClaimStatus().getClaimStatusId()!=3 && st.getClaimStatus().getClaimStatusId()!=7)
		{
			throw new BadRequestException(
					"You Can Not Request For Another Service Appointment On This Case First You Have To Approved By SCH Or RSH");
		}
	
		/////////////////////////// Check Approve By SCH or Not
		/////////////////////////// kalindi///////////////////////////

		for (int i = 0; i < SAList.size(); i++) {
			BtServiceAppointment serviceAppointment = SAList.get(i);
			if (!list.contains(serviceAppointment.getStatus().getStatusId())) {
				throw new BadRequestException("You can not request for another service appointment on this case");
			}

//			/////////////////////////// Check Approve By SCH or Not
//			/////////////////////////// kalindi///////////////////////////
//			if (serviceAppointment.getClaimStatus().getClaimStatusId() != 3) {
//				throw new BadRequestException(
//						"You Can Not Request For Another Service Appointment On This Case First You Have To Approved By SCH");
//			}
//			/////////////////////////// Check Approve By SCH or Not
//			/////////////////////////// kalindi///////////////////////////
			anotherServiceAppointment.setCaseDetails(_caseDetails);
			anotherServiceAppointment.setService(serviceAppointment);
		}
		anotherServiceAppointment.setRemark(remark);
		anotherServiceAppointment.setStatus("Approved");
		dbAnotherservice.saveDetails(anotherServiceAppointment, user);
	}

	@Override
	public Page<Map<String, Object>> getPage(LoginUser user, Pageable page, String filtername)
			throws BadRequestException, UnauthoriseException {
		if (dbprofile.hasObjectAccess(user.getUsername(), BtServiceAppointment.class, UserProfileRepository.VIEW_ALL)) {

			if (filtername != null && filtername.equalsIgnoreCase("all")) {
				return serviceRepo.getPage(page);
			}
			if (filtername != null && filtername.equalsIgnoreCase("tobeClaimed")) {
				return getServiceAppointmentListByClaimStatus(filtername, page, user);
			}
			if (filtername != null && filtername.equalsIgnoreCase("close")) {
				return getServiceAppointmentListByStatus(filtername, page, user);
			}
			if (filtername != null && filtername.equalsIgnoreCase("Approved")) {
				return getServiceAppointmentListByClaimStatus(filtername, page, user);
			}
			if (filtername != null && filtername.equalsIgnoreCase("open")) {
				return getServiceAppointmentListByStatus(filtername, page, user);
			}
			if (filtername != null && filtername.equalsIgnoreCase("Rejected")) {

				return getServiceAppointmentListByClaimStatus(filtername, page, user);
			}
			if (filtername != null && filtername.equalsIgnoreCase("postedinsap")) {
				return getServiceAppointmentListByClaimStatus(filtername, page, user);
			}
			return serviceRepo.getPage(page);
		}
		if (dbprofile.hasObjectAccess(user.getUsername(), BtServiceAppointment.class, UserProfileRepository.VIEW)) {
			List<String> userList = dbprofile.getListOfUserAndChildByUser(user.getUsername());

			List<String> emps = new ArrayList<String>();
			for (String u : userList) {

				emps.add(u);
			}

			if (filtername != null && filtername.equalsIgnoreCase("all")) {
				return serviceRepo.getAll(emps, page);
			}
			if (filtername != null && filtername.equalsIgnoreCase("tobeClaimed")) {
				return getServiceAppointmentListByClaimStatus(filtername, page, user);
			}
			if (filtername != null && filtername.equalsIgnoreCase("close")) {
				return getServiceAppointmentListByStatus(filtername, page, user);
			}
			if (filtername != null && filtername.equalsIgnoreCase("Approved")) {
				return getServiceAppointmentListByClaimStatus(filtername, page, user);
			}
			if (filtername != null && filtername.equalsIgnoreCase("open")) {
				return getServiceAppointmentListByStatus(filtername, page, user);
			}
			if (filtername != null && filtername.equalsIgnoreCase("Rejected")) {
				return getServiceAppointmentListByClaimStatus(filtername, page, user);
			}
			if (filtername != null && filtername.equalsIgnoreCase("postedinsap")) {
				return getServiceAppointmentListByClaimStatus(filtername, page, user);
			}
			return serviceRepo.getAll(emps, page);
		}
		return Page.empty();
	}

	@Override
	public Page<Map<String, Object>> getServiceAppointmentListByClaimStatus(String claimstatus, Pageable page,
			LoginUser user) {
		if (dbprofile.hasObjectAccess(user.getUsername(), BtServiceAppointment.class, UserProfileRepository.VIEW_ALL)) {
			List<String> claimStatusList = new ArrayList<>();
			if (claimstatus.equalsIgnoreCase("Approved")) {
				claimStatusList.add("Approved By SCH");
				claimStatusList.add("Approved By Finance");
				claimStatusList.add("Approved By RSH");
			} else if (claimstatus.equalsIgnoreCase("Rejected")) {
				claimStatusList.add("Rejected By SCH");
				claimStatusList.add("Rejected By Finance");
				claimStatusList.add("Rejected By RSH");
			} else if (claimstatus.equalsIgnoreCase("tobeClaimed"))
				claimStatusList.add("To be Claimed");
			else if (claimstatus.equalsIgnoreCase("postedinsap"))
				claimStatusList.add("Posted in SAP");
			else {
				claimStatusList.add(claimstatus);
			}
			return serviceRepo.getServiceAppointmentListByClaimStatus(claimStatusList, page);
		}
		if (dbprofile.hasObjectAccess(user.getUsername(), BtServiceAppointment.class, UserProfileRepository.VIEW)) {
			List<String> us = new ArrayList<String>();
			us = dbprofile.getListOfUserAndChildByUser(user.getUsername());
			List<String> emps = new ArrayList<String>();
			for (String u : us) {
				emps.add(u);
			}
			List<String> claimStatusList = new ArrayList<>();
			if (claimstatus.equalsIgnoreCase("Approved")) {
				claimStatusList.add("Approved By SCH");
				claimStatusList.add("Approved By Finance");
				claimStatusList.add("Approved By RSH");
			} else if (claimstatus.equalsIgnoreCase("Rejected")) {
				claimStatusList.add("Rejected By SCH");
				claimStatusList.add("Rejected By Finance");
				claimStatusList.add("Rejected By RSH");
			} else if (claimstatus.equalsIgnoreCase("tobeClaimed"))
				claimStatusList.add("To be Claimed");
			else if (claimstatus.equalsIgnoreCase("postedinsap"))
				claimStatusList.add("Posted in SAP");
			else {
				claimStatusList.add(claimstatus);
			}
			return serviceRepo.getServiceAppointmentListByClaimStatus(emps, claimStatusList, page);
		}
		return Page.empty();
	}

	@Override
	public Page<Map<String, Object>> getServiceAppointmentListByStatus(String status, Pageable page, LoginUser user) {
		List<String> statusList = new ArrayList<String>();
		// System.out.println("Status =>"+status);
		// System.out.println("User =>"+user.getUsername());
		if (status.equalsIgnoreCase("open")) {
			statusList.add("Unassigned");
			statusList.add("Scheduled");
			statusList.add("Dispatched");

		} else if (status.equalsIgnoreCase("close")) {
			statusList.add("In-Completed");
			statusList.add("Cannot Complete");
			statusList.add("Completed");
			statusList.add("Subsequently Completed");

		}
		if (dbprofile.hasObjectAccess(user.getUsername(), BtServiceAppointment.class, UserProfileRepository.VIEW_ALL)) {
			if (status.equalsIgnoreCase("open")) {
				return serviceRepo.getServiceAppointmentListByStatus(statusList, page);
			}
			if (status.equalsIgnoreCase("close")) {
				return serviceRepo.getServiceAppointmentListByStatus(statusList, page);
			}
		}
		if (dbprofile.hasObjectAccess(user.getUsername(), BtServiceAppointment.class, UserProfileRepository.VIEW)) {
			List<String> us = new ArrayList<String>();
			List<String> userlist = new ArrayList<String>();

			us = dbprofile.getListOfUserAndChildByUser(user.getUsername());
			for (String u : us) {
				userlist.add(u);
			}

			if (status.equalsIgnoreCase("open")) {

				return serviceRepo.getServiceAppointmentListByStatus(userlist, statusList, page);
			}
			if (status.equalsIgnoreCase("close")) {

				return serviceRepo.getServiceAppointmentListByStatus(userlist, statusList, page);
			}
		}

		return Page.empty();
	}

	@Override
	public void serviceAppointmentClaimApproval(ServiceAppointment serviceAppointment, BusinessDomain businessDomain,
			LoginUser user) throws BadRequestException {
		// try {
		// java.util.Set<String> _roles = new java.util.HashSet<String>();
		// java.util.Iterator ite = user.getAuthorities().iterator();
		// while (ite.hasNext()) {
		// com.pratikshat.exicom.entity.LoginUserRoles _rr =
		// (com.pratikshat.exicom.entity.LoginUserRoles) ite
		// .next();
		// _roles.add(_rr.getAuthority());
		// }
		//
		// boolean roleFound = false;
		// boolean userFound = false;
		// com.pratikshat.exicom.entity.Approval _appliedRule = new
		// com.pratikshat.exicom.entity.Approval();
		//
		// java.util.List<com.pratikshat.exicom.entity.Approval> _rulesLst = new
		// java.util.ArrayList<com.pratikshat.exicom.entity.Approval>();
		//
		// _rulesLst = entityManager.createQuery(
		// "select apr from Approval apr where apr.objectClassName=?1 and apr.active=?2
		// and apr.businessDomain.businessDomainId=?3",
		// com.pratikshat.exicom.entity.Approval.class).setParameter(1,
		// "ServiceAppointment")
		// .setParameter(2, true).setParameter(3, businessDomain.getBusinessDomainId())
		// .getResultList();
		//
		//
		// CircleDetails circleDetails= null;
		// try {
		// Query circleDetailsQuery = entityManager.createQuery(
		// "select cr from CircleDetails cr left join cr.circleManagerBattry cb left
		// join cr.circleManagerPower cp left join cr.circleManagerEv ev where cr=?1",
		// com.pratikshat.exicom.entity.CircleDetails.class);
		// //System.out.println("Circle name "+serviceAppointment.getCircle());
		// circleDetailsQuery.setParameter(1,serviceAppointment.getCircle());
		// circleDetails = (com.pratikshat.exicom.entity.CircleDetails)
		// circleDetailsQuery.getSingleResult();
		// } catch (NoResultException ex) {
		// throw new com.pratikshat.exicom.exception.BadRequestException("Circle does
		// not exist");
		// }
		//
		//
		// String circleManagerEmailId = null;
		// if (circleDetails != null) {
		// if (businessDomain.getBusinessDomainId() == 1) { // Battery
		// if (circleDetails.getCircleManagerBattry() != null) {
		// circleManagerEmailId = circleDetails.getCircleManagerBattry().getEmailId();
		// }
		// }
		//
		// if (businessDomain.getBusinessDomainId() == 2) { // DCT
		// if (circleDetails.getCircleManagerPower() != null) {
		// circleManagerEmailId = circleDetails.getCircleManagerPower().getEmailId();
		// }
		// }
		// if (businessDomain.getBusinessDomainId() == 3) { // EV
		// if (circleDetails.getCircleManagerEv() != null) {
		// circleManagerEmailId = circleDetails.getCircleManagerEv().getEmailId();
		// }
		// }
		// } else {
		// throw new com.pratikshat.exicom.exception.BadRequestException("Circle does
		// not exist");
		// }
		//
		// if (circleManagerEmailId == null) {
		// throw new com.pratikshat.exicom.exception.BadRequestException("Circle manager
		// does not exist");
		// }
		//
		// EmployeeDetails employeeDetails= null;
		// try {
		// Query employeeQuery = entityManager.createQuery(
		// "select ed from EmployeeDetails ed join ed.contact ct join ct.legalEntity lg
		// left join ed.approvalManager apm left join ed.manager mg where
		// ed.emailId=?1",
		// com.pratikshat.exicom.entity.EmployeeDetails.class);
		// employeeQuery.setParameter(1, user.getUsername());
		// employeeDetails = (com.pratikshat.exicom.entity.EmployeeDetails)
		// employeeQuery.getSingleResult();
		// } catch (NoResultException ex) {
		// throw new
		// com.pratikshat.exicom.exception.BadRequestException("EmployeeDetails does not
		// exist");
		// }
		//
		// if (employeeDetails == null) {
		// throw new
		// com.pratikshat.exicom.exception.BadRequestException("EmployeeDetails does not
		// exist");
		// }
		//
		//
		//
		// CaseDetails caseDetails= null;
		// try {
		// Query caseQuery = entityManager.createQuery(
		// "select cd from CaseDetails cd join cd.serviceCenter where cd.caseId=?1",
		// com.pratikshat.exicom.entity.CaseDetails.class);
		// caseQuery.setParameter(1, serviceAppointment.getCaseDetails().getCaseId());
		// caseDetails = (com.pratikshat.exicom.entity.CaseDetails)
		// caseQuery.getSingleResult();
		// } catch (NoResultException ex) {
		// throw new com.pratikshat.exicom.exception.BadRequestException("Case does not
		// exist");
		// }
		//
		// if (caseDetails == null) {
		// throw new com.pratikshat.exicom.exception.BadRequestException("Case does not
		// exist");
		// }
		//
		// for (com.pratikshat.exicom.entity.Approval _appr : _rulesLst) {
		// String submitters[] = _appr.getInitialSubmitters().split(",");
		// for (String submi : submitters) {
		// String _subs[] = submi.split(":");
		//
		// if(caseDetails.getServiceCenter().getLegalEntityId()!=employeeDetails.getContact().getLegalEntity().getLegalEntityId())
		// {
		// if(_subs[0].equalsIgnoreCase("SA")) {
		// roleFound = true;
		// break;
		// }
		// }else {
		// if (_subs[0].equalsIgnoreCase("ROLE")) {
		// if (_roles.contains(_subs[1])) {
		// roleFound = true;
		// break;
		// }
		// }
		// if (_subs[0].equalsIgnoreCase("USER")) {
		// if (user.getUsername().equalsIgnoreCase(_subs[1])) {
		// userFound = true;
		// break;
		// }
		// }
		// }
		// }
		// if (roleFound || userFound) {
		// _appliedRule = _appr;
		// break;
		// }
		// }
		//
		//
		// } catch (Exception ex) {
		// ex.printStackTrace();
		// throw new BadRequestException("Unable to submit request for approval");
		//
		// }

	}

	@Override
	public BtServiceAppointmentResponse getByServiceAppId(long id, LoginUser user) {

		BtServiceAppointmentResponse appointmentResponse = serviceRepo.getServiceAppointmentDataById(id);
//		if (appointmentResponse != null) {
//			appointmentResponse.setBtSolutionImpartedProblemResponses(
//					dbSolutionImpartedProblemRepo.getResponseBySolutionId(appointmentResponse.getAppointmentId()));
//			
//			appointmentResponse.setBtSchOnlyResponses(dbSch.getResponseByServiceAppId(appointmentResponse.getAppointmentId()));
////			Set<BtSchOnlyResponse> schResoponse =dbSch.getResponseByServiceAppId(appointmentResponse.getAppointmentId());
////			System.out.println(schResoponse.size());
////			for(BtSchOnlyResponse schRes:schResoponse) {
////				schRes.setImpartedProblemResponses(dbSolutionImpartedProblemRepo.getResponseBySolutionId(appointmentResponse.getAppointmentId()));
////			}
////			dbSch.getResponseByServiceAppId(appointmentResponse.getAppointmentId()).
////			if(dbSch.getResponseByServiceAppId(appointmentResponse.getAppointmentId())!=null){
////				
////			}
//			
//			
//		}
		return appointmentResponse;

	}

	@Override
	public boolean verifyCode(long id, String vfCode) {
		BtServiceAppointment apt = null;
		try {
			apt = entityManager
					.createQuery("select sa from BtServiceAppointment sa where sa=?1", BtServiceAppointment.class)
					.setParameter(1, new BtServiceAppointment(id)).getSingleResult();
			if (apt.getVfCode() != null && apt.getVfCode().equals(vfCode))
				return true;
		} catch (NoResultException ex) {
			ex.printStackTrace();
		}
		return false;
	}

	@Override
	public Page<BtSchOnlyResponseCopy> getSchDetailsAppId(long id, LoginUser user, Pageable pageable) {
		Page<BtSchOnlyResponseCopy> btSchOnlyResponseCopy = dbSch.getResponsByServiceAppId(id, pageable);
		if (btSchOnlyResponseCopy != null) {
			for (BtSchOnlyResponseCopy btSchOnlyResponseCopy2 : btSchOnlyResponseCopy) {
				btSchOnlyResponseCopy2.setProblem(
						dbSolutionImpartedProblemRepo.getResponseForSolutionId(btSchOnlyResponseCopy2.getSchOnlyId()));
			}
		}
		return btSchOnlyResponseCopy;
	}

}
