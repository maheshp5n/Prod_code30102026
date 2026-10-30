package com.pratikshat.exicom.repository;

import java.io.ByteArrayInputStream;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pratikshat.exicom.config.FileStorageProperties;
import com.pratikshat.exicom.config.MiscellaneousUtils;
import com.pratikshat.exicom.entity.ApplicationNotificationDetails;
import com.pratikshat.exicom.entity.Approval;
import com.pratikshat.exicom.entity.ApprovalSteps;
import com.pratikshat.exicom.entity.Assets;
import com.pratikshat.exicom.entity.AssetsStatus;
import com.pratikshat.exicom.entity.BatteryFSR;
import com.pratikshat.exicom.entity.BusinessDomain;
import com.pratikshat.exicom.entity.CaseDetails;
import com.pratikshat.exicom.entity.CaseStageDetails;
import com.pratikshat.exicom.entity.CaseStatusDetails;
import com.pratikshat.exicom.entity.CaseTypes;
import com.pratikshat.exicom.entity.CircleDetails;
import com.pratikshat.exicom.entity.ClaimStatus;
import com.pratikshat.exicom.entity.ContactDetails;
import com.pratikshat.exicom.entity.ESignatureOnSA;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.PendingResponsibility;
import com.pratikshat.exicom.entity.Products;
import com.pratikshat.exicom.entity.SaEngRating;
import com.pratikshat.exicom.entity.ServiceAppointment;
import com.pratikshat.exicom.entity.ServiceAppointmentStatus;
import com.pratikshat.exicom.entity.ServiceRecource;
import com.pratikshat.exicom.entity.SiteDetails;
import com.pratikshat.exicom.entity.TemplateDetails;
import com.pratikshat.exicom.entity.TemplateType;
import com.pratikshat.exicom.entity.ToBeApproved;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.CaseRequest;
import com.pratikshat.exicom.model.request.SecondServiceAppointmentRequest;
import com.pratikshat.exicom.model.request.ServiceAppointmentReAssignRequest;
import com.pratikshat.exicom.model.request.ServiceAppointmentRequest;
import com.pratikshat.exicom.model.response.ServiceAppointmentResponse;
import com.pratikshat.exicom.utils.ClosedServiceAppointmentPDF;
import com.pratikshat.exicom.utils.MaterialTransferPackingList;
import com.pratikshat.exicom.utils.ServiceAppointmentUtil;
import com.pratikshat.exicom.utils.SmsNotifier;

@Repository
public class ServiceAppointmentRepositoryCustomImpl implements ServiceAppointmentRepositoryCustom {

	private static final org.slf4j.Logger logger = LoggerFactory
			.getLogger(ServiceAppointmentRepositoryCustomImpl.class);

	@PersistenceContext
	EntityManager entityManager;

	@Autowired
	private SimpMessagingTemplate messagingTemplate;

	@Autowired
	com.pratikshat.exicom.interceptor.WorkflowExecuter workflow;

	@Autowired
	FileStorageProperties fileStorageProperties;

	@Value("${feedbackurl}")
	String feedbackurl;

	@Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRES_NEW, rollbackFor = {
			Exception.class, RuntimeException.class, BadRequestException.class, UnauthoriseException.class })
	@Override
	public ServiceAppointmentResponse updateServiceAppointmentDetails(
			ServiceAppointmentRequest serviceAppointmentRequest, LoginUser user) throws Exception {
		String assetsSerialNumber = serviceAppointmentRequest.getAssetsSerialNumber();// null;
		String productPartCode = serviceAppointmentRequest.getPartCode();// null;
		com.pratikshat.exicom.entity.CaseDetails _caseDetails = null;

		if (serviceAppointmentRequest != null) {
			com.pratikshat.exicom.entity.ServiceAppointment serviceAppointment = null;

			try {
				serviceAppointment = entityManager.createQuery(
						"select sa from ServiceAppointment sa join sa.status st left join sa.claimStatus cs left join sa.circleManager cm join sa.ownerDetails owner where sa.serviceAppointmentId=?1",
						com.pratikshat.exicom.entity.ServiceAppointment.class)
						.setParameter(1, serviceAppointmentRequest.getServiceAppointmentId()).getSingleResult();
			} catch (NoResultException ex) {
				throw new BadRequestException("Service Appointment does not exist");
			}

			if (!serviceAppointment.getOwnerDetails().getEmailId().equalsIgnoreCase(user.getUsername())) {
				throw new com.pratikshat.exicom.exception.UnauthoriseException(
						"You are not authorised to edit this Service Appointment");
			}

			if (serviceAppointment.getClaimStatus() != null) {
				List<Long> list = new ArrayList<Long>();
				list.add(Long.valueOf(2)); // To be claimed
				list.add(Long.valueOf(4)); // Rejected By SCH
				list.add(Long.valueOf(6)); // Rejected By Finance
				list.add(Long.valueOf(8)); // Rejected By RSH
				System.out.println("cli" + serviceAppointment.getClaimStatus().getClaimStatusId());
				if (!list.contains(serviceAppointment.getClaimStatus().getClaimStatusId())) {
					throw new com.pratikshat.exicom.exception.UnauthoriseException("You can't edit this record");
				}
			}

			try {
				_caseDetails = entityManager.createQuery(
						"select cs from ServiceAppointment sa join sa.caseDetails cs left join cs.caseReason rs left join cs.caseType ct left join cs.assets ast left join cs.businessDomain bsd left join ast.product prd left join cs.site st where sa.serviceAppointmentId=?1",
						com.pratikshat.exicom.entity.CaseDetails.class)
						.setParameter(1, serviceAppointmentRequest.getServiceAppointmentId()).getSingleResult();

			} catch (javax.persistence.NoResultException ex) {
				throw new Exception("Case details not found");
			}

			// Exceptional - DCT // Exceptional - Battery
			if (_caseDetails.getCaseReason().getReasonId() == 12 || _caseDetails.getCaseType().getCaseTypeId() == 2) {
				throw new Exception("You can not close the exceptional case");
			}

//			if (_caseDetails != null && _caseDetails.getBusinessDomain() != null) {
//				if (_caseDetails.getBusinessDomain().getBusinessDomainId() == 1) { // Battery
//					try {
//						BatteryFSR fsr = entityManager.createQuery(
//								"select fsr from BatteryFSR fsr left join fsr.complaintStatus cs left join fsr.serviceAppointment sa where sa.serviceAppointmentId=?1",
//								BatteryFSR.class).setParameter(1, serviceAppointmentRequest.getServiceAppointmentId())
//								.getSingleResult();
//						if (fsr.getComplaintStatus() == null || fsr.getDateOfVisit() == null) {
//							throw new Exception("Please fill the Battery FSR first");
//						}
//
//					} catch (javax.persistence.NoResultException ex) {
//
//					}
//				}
//			}

			// Installation - Battery // Installation - DCT
			if (_caseDetails.getCaseReason().getReasonId() == 8 || _caseDetails.getCaseReason().getReasonId() == 18) {
				if (serviceAppointmentRequest.getStatusId() == 4) { // Complete
					assetsSerialNumber = serviceAppointmentRequest.getAssetsSerialNumber();
					productPartCode = serviceAppointmentRequest.getPartCode();
					if (assetsSerialNumber == null || productPartCode == null) {
						throw new Exception("Please provide Valid Assets Serial number and Product Part Code");
					}

					Assets _assets = null;
					try {
						Query assetsQuery = entityManager.createQuery(
								"select ast from Assets ast left join ast.account act left join ast.site st where ast.serialNumber=?1",
								com.pratikshat.exicom.entity.Assets.class);
						assetsQuery.setParameter(1, assetsSerialNumber);
						_assets = (Assets) assetsQuery.getSingleResult();
					} catch (NoResultException ex) {
						throw new com.pratikshat.exicom.exception.BadRequestException("Asset does not exist");
					} catch (Exception ex) {
						throw new com.pratikshat.exicom.exception.BadRequestException(ex.getMessage());
					}

					if (_assets == null) {
						throw new com.pratikshat.exicom.exception.BadRequestException("Asset does not exist");
					}

					if (_assets.getSite() != null) {
						throw new com.pratikshat.exicom.exception.BadRequestException(
								"Asset already installed on " + _assets.getSite().getManualSiteId());
					}

					SiteDetails site = null;
					if (_caseDetails.getSite().getSiteId() != 0) {
						try {
							Query siteQuery = entityManager.createQuery(
									"select sti from SiteDetails sti join sti.account act where sti.siteId=?1",
									com.pratikshat.exicom.entity.SiteDetails.class);
							siteQuery.setParameter(1, _caseDetails.getSite().getSiteId());
							site = (SiteDetails) siteQuery.getSingleResult();
						} catch (NoResultException ex) {
							throw new com.pratikshat.exicom.exception.BadRequestException("Site does not exist");
						}
					} else {
						throw new com.pratikshat.exicom.exception.BadRequestException("Site does not exist");
					}

					if (site == null) {
						throw new com.pratikshat.exicom.exception.BadRequestException("Site does not exist");
					}

					if (_assets.getAccount() != site.getAccount()) {
						throw new com.pratikshat.exicom.exception.BadRequestException(
								"Site account and Asset account does not matched");
					}

					if (_assets != null) {
						Date installDate = new Date();
						Calendar clendar = Calendar.getInstance(TimeZone.getTimeZone("IST"));
						clendar.setTime(installDate);
						clendar.add(Calendar.MONTH, _assets.getWarrantyFromDateOfInstallationInMonth());

						Date installationExpiry = clendar.getTime();
						_assets.setInstallDate(installDate);
						_assets.setInstallExpiryDate(installationExpiry);
						_assets.setSite(
								new com.pratikshat.exicom.entity.SiteDetails(_caseDetails.getSite().getSiteId()));
						_assets.setProduct(new Products(productPartCode));
						_assets.setStatus(new AssetsStatus(2)); // Installed
						Date warrantyExpiryDate = installationExpiry;

						if (_assets.getDateOfDeliveryExpiryDate() != null) {
							if (_assets.getDateOfDeliveryExpiryDate().getTime() < _assets.getInstallExpiryDate()
									.getTime()) {
								warrantyExpiryDate = _assets.getDateOfDeliveryExpiryDate();
							}
						}
						_assets.setWarrantyExpiryDate(warrantyExpiryDate);
						entityManager.persist(_assets);
					}

					/*
					 * 
					 * entityManager.
					 * createQuery("update Assets ast set ast.site=?1,ast.product=?2 where ast.serialNumber=?3"
					 * ) .setParameter(1, new
					 * com.pratikshat.exicom.entity.SiteDetails(_caseDetails.getSite().getSiteId()))
					 * .setParameter(2, new Products(productPartCode)) .setParameter(3,
					 * assetsSerialNumber) .executeUpdate();
					 */
					entityManager.createQuery("update CaseDetails cd set cd.assets=?1 where cd.caseId=?2")
							.setParameter(1, new Assets(assetsSerialNumber)).setParameter(2, _caseDetails.getCaseId())
							.executeUpdate();
				}

			} else {
				if (_caseDetails.getAssets() != null) {
					assetsSerialNumber = _caseDetails.getAssets().getSerialNumber();
					if (_caseDetails.getAssets().getProduct() != null) {
						productPartCode = _caseDetails.getAssets().getProduct().getProductPartCode();
					}
				}
			}

			if (serviceAppointmentRequest.getAcknowledgedByName() != null)
				serviceAppointment.setAcknowledgedByName(serviceAppointmentRequest.getAcknowledgedByName());
			if (serviceAppointmentRequest.getAcknowledgedByPhone() != null)
				serviceAppointment.setAcknowledgedByPhone(serviceAppointmentRequest.getAcknowledgedByPhone());
			serviceAppointment.setCorrectiveActions(serviceAppointmentRequest.getCorrectiveActions());
			serviceAppointment.setModifiedBy(new EmployeeDetails(user.getUsername()));
			serviceAppointment.setModifiedDate(new Date());
			serviceAppointment.setFaultObserved(serviceAppointmentRequest.getFaultObserved());
			serviceAppointment.setPendingResponsibility(
					new PendingResponsibility(serviceAppointmentRequest.getPendingResponsibilityId()));
			serviceAppointment.setPunchPointCustomer(serviceAppointmentRequest.getPunchPointCustomer());
			serviceAppointment.setPunchPointExiom(serviceAppointmentRequest.getPunchPointExiom());
			serviceAppointment.setRemark(serviceAppointmentRequest.getRemark());
			serviceAppointment.setStatus(new ServiceAppointmentStatus(serviceAppointmentRequest.getStatusId()));
			serviceAppointment.setSyncFlag(false);
			serviceAppointment.setLatitude(serviceAppointmentRequest.getLatitude());
			serviceAppointment.setLongitude(serviceAppointmentRequest.getLongitude());
			serviceAppointment.setAddress(serviceAppointmentRequest.getAddress());
			serviceAppointment.setClosedFromPlatform(serviceAppointmentRequest.getClosedFromPlatform());

			if (serviceAppointmentRequest.geteSignature() != null
					&& !serviceAppointmentRequest.geteSignature().toString().equalsIgnoreCase("")) {
				try {
					ESignatureOnSA esg = entityManager
							.createQuery("select esg from ESignatureOnSA esg where esg.id=?1", ESignatureOnSA.class)
							.setParameter(1, serviceAppointment).getSingleResult();

					esg.seteSignature(serviceAppointmentRequest.geteSignature());
					esg.setModifiedDate(new Date());
					entityManager.persist(esg);
				} catch (NoResultException ex) {
					ESignatureOnSA esg = new ESignatureOnSA();

					esg.setId(serviceAppointment);
					esg.seteSignature(serviceAppointmentRequest.geteSignature());
					esg.setModifiedDate(new Date());
					esg.setCreatedDate(new Date());
					entityManager.persist(esg);
				}
			}

			if (assetsSerialNumber != null && assetsSerialNumber.equalsIgnoreCase("")) {
				Assets _ast = null;
				try {
					_ast = entityManager.createQuery("select ast from Assets ast where ast=?1", Assets.class)
							.setParameter(1, new Assets(assetsSerialNumber)).getSingleResult();
					serviceAppointment.setAssets(_ast);
					serviceAppointment.setProduct(_ast.getProduct());

				} catch (NoResultException ex) {
					throw new BadRequestException("Wrong assets serial number");
				}

			}

			ServiceAppointmentStatus _states = entityManager
					.createQuery("select st from ServiceAppointmentStatus st where st.statusId=?1",
							ServiceAppointmentStatus.class)
					.setParameter(1, serviceAppointmentRequest.getStatusId()).getSingleResult();
			if (_states.getStatusId() == 4) { // Complete

				List<CaseStageDetails> _caseStageDetails = entityManager.createQuery(
						"select cst from CaseStageDetails cst left join cst.caseStatus cs left join cst.division dv where cs.id=?1 and dv.businessDomainId=?2",
						CaseStageDetails.class).setParameter(1, 2) // Closed
						.setParameter(2, serviceAppointment.getBusinessDomain().getBusinessDomainId()).getResultList();

				// com.pratikshat.exicom.entity.CaseDetails
				// _caseDetails=entityManager.createQuery("select cst from ServiceAppointment st
				// join st.caseDetails cst where
				// st.serviceAppointmentId=?1",com.pratikshat.exicom.entity.CaseDetails.class).setParameter(1,
				// serviceAppointmentRequest.getServiceAppointmentId()).getSingleResult();
				Query updateCase = entityManager.createQuery(
						"update CaseDetails cs set cs.status=?1, cs.closeDate=?2, cs.closedBy=?3, cs.modifiedDate=?4, cs.modifiedBy=?5, cs.stage=?6 where cs.caseId=?7");
				updateCase.setParameter(1, new CaseStatusDetails(2)); // closed
				updateCase.setParameter(2, new java.util.Date());
				updateCase.setParameter(3, new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
				updateCase.setParameter(4, new java.util.Date());
				updateCase.setParameter(5, new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));

				List<Long> reasonList = new ArrayList<>();

				if (_caseDetails.getBusinessDomain().getBusinessDomainId() == 1) { // Battery

					reasonList.add(5L); // Paid Repair - Battery
					reasonList.add(27L); // Paid Repair - Battery
					reasonList.add(7L); // Audit - Battery
					reasonList.add(20L); // Audit - Battery
					reasonList.add(23L); // Audit - Battery
					reasonList.add(8L); // Installation - Battery

					if (reasonList.contains(_caseDetails.getCaseReason().getReasonId())) {
						// CaseStageDetails stage = getCaseStage(_caseStageDetails, "Document Awaited -
						// Customer");
						updateCase.setParameter(6, new CaseStageDetails(17)); // Document Awaited - Customer
					} else {
						// CaseStageDetails stage = getCaseStage(_caseStageDetails, "Resolved");
						updateCase.setParameter(6, new CaseStageDetails(2)); // Resolved
					}
				} else if (_caseDetails.getBusinessDomain().getBusinessDomainId() == 2) { // DCT

					reasonList.add(17L); // Paid Repair - DCT
					reasonList.add(28L); // Paid Repair - DCT
					reasonList.add(36L); // Paid Repair - DCT
					reasonList.add(14L); // Preventive Maintenance - DCT
					reasonList.add(31L); // Preventive Maintenance - DCT
					reasonList.add(18L); // Installation - DCT
					reasonList.add(39L); // Preventive Maintenance - DCT
					reasonList.add(40L); // Acceptance Testing - DCT
					reasonList.add(42L); // SMPS Kitting Warehouse - DCT
					reasonList.add(43L); // Warehouse Health check-ups - DCT
					reasonList.add(46L); // Battery Bank Charging - DCT
					reasonList.add(45L); // Site Survey - DCT

					if (reasonList.contains(_caseDetails.getCaseReason().getReasonId())) {
						// CaseStageDetails stage = getCaseStage(_caseStageDetails, "Document Awaited -
						// Customer");
						updateCase.setParameter(6, new CaseStageDetails(18)); // Document Awaited - Customer
					} else {
						// CaseStageDetails stage = getCaseStage(_caseStageDetails, "Resolved");
						updateCase.setParameter(6, new CaseStageDetails(8)); // Resolved
					}
				}
				updateCase.setParameter(7, _caseDetails.getCaseId()).executeUpdate();

//				if (_caseDetails.getCaseReason().getCaseReasonDetailsName().equalsIgnoreCase("Paid Repair")
//						|| _caseDetails.getCaseReason().getCaseReasonDetailsName().equalsIgnoreCase("Preventive Maintenance")
//						|| _caseDetails.getCaseReason().getCaseReasonDetailsName().equalsIgnoreCase("Audit")
//						|| _caseDetails.getCaseReason().getCaseReasonDetailsName().equalsIgnoreCase("Installation")) {
//					CaseStageDetails stage = getCaseStage(_caseStageDetails, "Document Awaited");
//					updateCase.setParameter(6, stage);
//				} else {
//					CaseStageDetails stage = getCaseStage(_caseStageDetails, "Resolved");
//					updateCase.setParameter(6, stage);
//				}
//				updateCase.setParameter(7, _caseDetails.getCaseId()).executeUpdate();
			}

			if (serviceAppointment.getStatus() != null) {
				List<Long> list = new ArrayList<Long>();
				list.add(1L); // Unassigned
				list.add(2L); // Scheduled
				list.add(3L); // Dispatched

				if (!list.contains(serviceAppointment.getStatus().getStatusId())) {

					if (serviceAppointmentRequest.getVisitDate() != null) {
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						Date visitDate = sdf.parse(sdf.format(serviceAppointmentRequest.getVisitDate()));
						Date createdDate = sdf.parse(sdf.format(serviceAppointment.getCreateDate()));
						if (visitDate.getTime() - createdDate.getTime() < 0) {
							throw new BadRequestException("Visit date must be greater or equal to the created date");
						}
						serviceAppointment.setVisitDate(serviceAppointmentRequest.getVisitDate());
					} else {
						throw new BadRequestException("Please enter visit date");
					}

					List<Long> stlist = new ArrayList<Long>();
					stlist.add(4L); // Completed
					if (stlist.contains(serviceAppointment.getStatus().getStatusId())) {
						serviceAppointment.setClaimStatus(new ClaimStatus(1L)); // Submitted

						serviceAppointmentClaimApproval(serviceAppointment, _caseDetails.getBusinessDomain(), user);

						List<com.pratikshat.exicom.entity.ServiceAppointment> serviceAppointmentList = null;

						try {
							serviceAppointmentList = entityManager.createQuery(
									"select sa from ServiceAppointment sa join sa.status st join sa.caseDetails cd where cd.caseId=?1",
									com.pratikshat.exicom.entity.ServiceAppointment.class)
									.setParameter(1, _caseDetails.getCaseId()).getResultList();
						} catch (NoResultException ex) {
							throw new BadRequestException("Service Appointment does not exist");
						}

						for (int i = 0; i < serviceAppointmentList.size(); i++) {
							ServiceAppointment sa = serviceAppointmentList.get(i);

							List<Long> slist = new ArrayList<Long>();
							slist.add(5L);
							slist.add(6L);

							if (slist.contains(sa.getStatus().getStatusId())) {
								if (!serviceAppointment.getAppointmentNumber()
										.equalsIgnoreCase(sa.getAppointmentNumber())) {

									sa.setStatus(new ServiceAppointmentStatus(7L)); // Subsequently Completed
									sa.setModifiedBy(new EmployeeDetails(user.getUsername()));
									sa.setModifiedDate(new Date());

									sa.setClaimStatus(new ClaimStatus(1L)); // Submitted

									serviceAppointmentClaimApproval(sa, _caseDetails.getBusinessDomain(), user);

									entityManager.persist(sa);
								}
							}
						}
					}
				}
			}
			workflow.executeWorkflow(entityManager, serviceAppointment, "UPDATE", "PRE", user);
			entityManager.persist(serviceAppointment);
			workflow.executeWorkflow(entityManager, serviceAppointment, "UPDATE", "POST", user);

			if (serviceAppointmentRequest.getStatusId() == 4) {
				SaEngRating engRatting = null;
				try {
					engRatting = entityManager
							.createQuery("select sar from SaEngRating sar where sar.sa=?1", SaEngRating.class)
							.setParameter(1, serviceAppointment).getSingleResult();
				} catch (NoResultException ex) {
					String otp = MiscellaneousUtils.genOtp(5);

					TemplateDetails templateDetails = null;
					try {
						Query templateQuery = entityManager.createQuery(
								"select tmpd from TemplateDetails tmpd where tmpd.active=?1 and tmpd.templateType=?2 and tmpd.businessDomain=?3 and tmpd.eventName=?4",
								TemplateDetails.class);
						templateQuery.setParameter(1, true);
						templateQuery.setParameter(2, TemplateType.SMS);
						templateQuery.setParameter(3, new BusinessDomain(2));
						templateQuery.setParameter(4, "engineer_rating");

						templateDetails = (TemplateDetails) templateQuery.getSingleResult();

						String mess = templateDetails.getBody();
						String url = feedbackurl + "/engrating/" + serviceAppointment.getServiceAppointmentId() + "?vf="
								+ otp;
						mess = mess.replaceFirst("<var>", url);
						serviceAppointment.setVfCode(otp);

						entityManager.persist(serviceAppointment);
						if (serviceAppointment.getAcknowledgedByPhone() != null
								&& serviceAppointment.getAcknowledgedByPhone().length() >= 10) {

							SmsNotifier smNoti = new SmsNotifier(entityManager,
									serviceAppointment.getAcknowledgedByPhone(), mess, user.getUsername(),
									templateDetails.getTemplaeCode());
							smNoti.sendNotification();
						}

					} catch (Exception ext) {
						ext.printStackTrace();
					}

				}
				
				
//				 shagufta changes
				 
//			       com.pratikshat.exicom.entity.ServiceAppointment sa=(com.pratikshat.exicom.entity.ServiceAppointment );
//			       com.pratikshat.exicom.entity.CaseDetails _caseDetails= sa.getCaseDetails();

			   	if(_caseDetails.isiSmsSend()) {
			   				System.out.println("true==522"+_caseDetails.isiSmsSend());
			   				try {
			   					String selectCase = "Select cd from ContactDetails cd where cd.emailId = ?1";
			   					ContactDetails cd = entityManager.createQuery(selectCase, ContactDetails.class)
			   							.setParameter(1, user.getUsername()).getSingleResult();
			   					String customerMobile= _caseDetails.getContactPerson().getMobile();
			   					String engMobile=_caseDetails.getSite().getServiceResource().getUser().getContact().getMobile();
			                                     String SCH=	_caseDetails.getSite().getCircle().getCircleManagerPower().getMobile();
			                                     
			                                     java.util.List<com.pratikshat.exicom.entity.CaseProblemReported> pb=entityManager.createQuery("select pr from CaseProblemReported pr join pr.caseDetails cs where cs.id=?1",com.pratikshat.exicom.entity.CaseProblemReported.class).setParameter(1, _caseDetails.getCaseId()).getResultList();                   
			   					
			   					

			   					TemplateDetails templateDetails = null;
			   					java.lang.StringBuilder prob=new StringBuilder();
			   					if(pb!=null)
			   	       			{
			   	       				for(com.pratikshat.exicom.entity.CaseProblemReported prb:pb)
			   	       				{ 
			   	       					prob.append(prb.getProblemName().toString()+", ");
			   	       				}                  /* throw new com.pratikshat.exicom.exception.BadRequestException("prob="+bcase.getId().toString());*/
			   	       				
			   	       			}
			   				 String msgs=prob.toString();
			   					try {
			   						javax.persistence.Query templateQuery=entityManager.createQuery("select tmpd from TemplateDetails tmpd where tmpd.active=?1 and tmpd.templateType=?2 and tmpd.businessDomain=?3 and tmpd.eventName=?4",TemplateDetails.class);
			   						templateQuery.setParameter(1,true);
			   						templateQuery.setParameter(2,TemplateType.SMS);
			   						templateQuery.setParameter(3,new BusinessDomain(2));
			   						templateQuery.setParameter(4,"DCT_Case_closer");

			   						templateDetails = (TemplateDetails) templateQuery.getSingleResult();
			   					} catch (javax.persistence.NoResultException nex) {
			   						throw new com.pratikshat.exicom.exception.BadRequestException(nex);
			   					} catch (javax.persistence.NonUniqueResultException e) {
			   						throw new com.pratikshat.exicom.exception.BadRequestException(e);
			   					}
			   				

			   					String name = null;
			   					if (cd.getFirstName() != null
			   							&& !cd.getFirstName().equalsIgnoreCase("")) {
			   						name = cd.getFirstName();
			   					}
			   					if (cd.getMiddleName() != null
			   							&& !cd.getMiddleName().equalsIgnoreCase("")) {
			   						name = name + " " + cd.getMiddleName();
			   					}
			   					if (cd.getLastName() != null
			   							&& !cd.getLastName().equalsIgnoreCase("")) {
			   						name = name + " " + cd.getLastName();
			   					}

			   					String smsMessage = null;
			   					if (templateDetails != null) {
			   						smsMessage = templateDetails.getBody();
			   						smsMessage = smsMessage.replaceFirst("<var>", name);
			   						smsMessage = smsMessage.replaceFirst("<var>", _caseDetails.getManualId());
			   						smsMessage = smsMessage.replaceFirst("<var>", _caseDetails.getSite().getManualSiteId());
						             if(_caseDetails.getAssets()!=null) {
						            	 
						            	smsMessage = smsMessage.replaceFirst("<var>", _caseDetails.getAssets().getSerialNumber());
						            }
						            else if(_caseDetails.getMissingAssets()!=null){
			                            smsMessage = smsMessage.replaceFirst("<var>", _caseDetails.getMissingAssets());
						                }
						          
						            else {
			                        smsMessage = smsMessage.replaceFirst("<var>", "NONE");
					                }
			   				      


			   		           if(msgs.length()>=30){
			   		                        msgs=  msgs.substring(0,29);
			   		                        
			   		                 }
			   		            smsMessage = smsMessage.replaceFirst("<var>",msgs);
			   					}
			   					
			   					System.out.println( "the sms line =561"+smsMessage);

			   					if (smsMessage != null && !smsMessage.equalsIgnoreCase("")) {
			   						SmsNotifier sNotifier = new SmsNotifier(entityManager,
			   								SCH, smsMessage, user.getUsername(),
			   								templateDetails.getTemplaeCode());
			   						sNotifier.sendNotification();
//			   						eng sms
			   						SmsNotifier sNotifiereng = new SmsNotifier(entityManager,
			   								engMobile, smsMessage, user.getUsername(),
			   								templateDetails.getTemplaeCode());
			   						sNotifiereng.sendNotification();
			   						
//			   						contact mobile
			   						
			   						SmsNotifier sNotifiercon = new SmsNotifier(entityManager,
			   								customerMobile, smsMessage, user.getUsername(),
			   								templateDetails.getTemplaeCode());
			   						sNotifiercon.sendNotification();
			   					}
			   				} catch (Exception e) {
			   					e.printStackTrace();
			   				}
			   			}
			   	else {
			//throw new com.pratikshat.exicom.exception.BadRequestException("in else cond");
			   	}
				
			}


			entityManager.flush();
			entityManager.clear();
		} else {
			throw new Exception("No data found to save details");
		}
		return null;
	}

	/*
	 * @Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation =
	 * Propagation.REQUIRES_NEW, rollbackFor = { Exception.class,
	 * RuntimeException.class, SQLException.class })
	 * 
	 * @Override public ServiceAppointmentResponse
	 * createServiceAppointmentDetails(CaseRequest caseDetails, LoginUser user)
	 * throws Exception { if (caseDetails != null) {
	 * 
	 * com.pratikshat.exicom.entity.CaseDetails _caseDetails =
	 * entityManager.createQuery(
	 * "select cs from CaseDetails cs left join cs.assets ast left join ast.product where cs.caseId=?1"
	 * , com.pratikshat.exicom.entity.CaseDetails.class).setParameter(1,
	 * caseDetails.getCaseId()) .getSingleResult();
	 * 
	 * com.pratikshat.exicom.entity.ServiceAppointment _serviceAppointment = new
	 * com.pratikshat.exicom.entity.ServiceAppointment();
	 * 
	 * _serviceAppointment.setCaseDetails(new
	 * com.pratikshat.exicom.entity.CaseDetails(caseDetails.getCaseId()));
	 * _serviceAppointment.setCreateDate(new java.util.Date());
	 * _serviceAppointment.setCreatedBy(new
	 * com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
	 * _serviceAppointment.setModifiedBy(new
	 * com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
	 * _serviceAppointment.setModifiedDate(new java.util.Date());
	 * _serviceAppointment.setSyncFlag(false);
	 * _serviceAppointment.setOwnerDetails(new
	 * com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
	 * _serviceAppointment.setStatus(new
	 * com.pratikshat.exicom.entity.ServiceAppointmentStatus(2));
	 * _serviceAppointment.setPendingResponsibility(new PendingResponsibility(1));
	 * if (_caseDetails.getAssets() != null) { _serviceAppointment .setAssets(new
	 * com.pratikshat.exicom.entity.Assets(_caseDetails.getAssets().getSerialNumber(
	 * ))); if (_caseDetails.getAssets().getProduct() != null) {
	 * _serviceAppointment.setProduct(new com.pratikshat.exicom.entity.Products(
	 * _caseDetails.getAssets().getProduct().getProductPartCode())); } }
	 * com.pratikshat.exicom.entity.EmployeeDetails _empDetails = entityManager
	 * .createQuery("select emp from EmployeeDetails emp left join emp.contact ct where emp.emailId=?1"
	 * , com.pratikshat.exicom.entity.EmployeeDetails.class) .setParameter(1,
	 * user.getUsername()).getSingleResult();
	 * 
	 * _serviceAppointment.setCustomerRepresentativeName( String.format("%s %s",
	 * _empDetails.getFirstName(), _empDetails.getLastName())); if
	 * (_empDetails.getContact() != null) {
	 * _serviceAppointment.setCustomerRepresentativeContact(_empDetails.getContact()
	 * .getMobile()); } entityManager.persist(_serviceAppointment);
	 * 
	 * Query updateManualId = entityManager.createQuery(
	 * "update ServiceAppointment sa set sa.appointmentNumber=?1 where sa.serviceAppointmentId=?2"
	 * ); SimpleDateFormat dateformatter = new SimpleDateFormat("yyyyMMdd"); String
	 * manualId = "SA-" + dateformatter.format(new java.util.Date()) + "-" +
	 * com.pratikshat.exicom.config.MiscellaneousUtils
	 * .fillZero(String.valueOf(_serviceAppointment.getServiceAppointmentId()), 5);
	 * updateManualId.setParameter(1, manualId); updateManualId.setParameter(2,
	 * _serviceAppointment.getServiceAppointmentId());
	 * updateManualId.executeUpdate();
	 * 
	 * try { com.pratikshat.exicom.entity.GlobalSearchIndex _search = new
	 * com.pratikshat.exicom.entity.GlobalSearchIndex();
	 * _search.setKeyName("manualId");
	 * _search.setObjectDetailsUrl("serviceappointment/" +
	 * _serviceAppointment.getServiceAppointmentId());
	 * _search.setObjectIdName("serviceAppointmentId"); _search.setObjectIdValue(""
	 * + _serviceAppointment.getServiceAppointmentId());
	 * _search.setObjectName("ServiceAppointment");
	 * _search.setSearchString(manualId); entityManager.persist(_search);
	 * 
	 * } catch (Exception e) { }
	 * 
	 * entityManager.flush(); entityManager.clear();
	 * 
	 * com.pratikshat.exicom.model.response.ServiceAppointmentResponse res = new
	 * com.pratikshat.exicom.model.response.ServiceAppointmentResponse();
	 * res.setServiceAppointmentId(_serviceAppointment.getServiceAppointmentId());
	 * res.setServiceAppointmentManulaId(_serviceAppointment.getAppintmentNumber());
	 * res.setServiceAppointmentStatusId(_serviceAppointment.getStatus().getStatusId
	 * ()); return res; } else { throw new
	 * Exception("No data found to save details"); }
	 * 
	 * }
	 */

	// Create Service Appointment manually

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = {
			BadRequestException.class, UnauthoriseException.class, RuntimeException.class, Exception.class,
			SQLException.class })
	public ServiceAppointment createServiceAppointmentDetails(SecondServiceAppointmentRequest serviceRequest,
			LoginUser user) throws Exception {

		CaseDetails _caseDetails = null;
		try {
			Query selectCaseDetailsQuery = entityManager.createQuery(
					"select ca from CaseDetails ca left join ca.status sta left join ca.assets ast left join ast.account acn left join ast.site st left join ca.businessDomain bd left join ca.caseType ct left join ca.ownerDetails onr left join ca.createdBy createdby left join ca.modifiedBy modified left join ca.engineer eng left join eng.contact econ left join ca.serviceCenter scr where ca.caseId=?1",
					CaseDetails.class);
			selectCaseDetailsQuery.setParameter(1, serviceRequest.getCaseId());
			_caseDetails = (CaseDetails) selectCaseDetailsQuery.getSingleResult();
		} catch (NoResultException ex) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Case does not exist");
		}

		if (_caseDetails != null) {
			// Case status -- Close for 1 and Rejected for 2
			if (_caseDetails.getStatus().getId() == 2 || _caseDetails.getStatus().getId() == 4) {
				throw new com.pratikshat.exicom.exception.BadRequestException(
						"You can not create service appointment for this case");
			}

			SiteDetails siteDetails = null;
			String emailId = null;
			if (_caseDetails.getSite() != null) {
				try {
					Query siteDetailsQuery = entityManager.createQuery(
							"select st from SiteDetails st  left join st.circle cr left join cr.circleManagerBattry cb left join cr.circleManagerPower cp left join cr.circleManagerEv ev where st.siteId=?1",
							SiteDetails.class);
					siteDetailsQuery.setParameter(1, _caseDetails.getSite().getSiteId());
					siteDetails = (com.pratikshat.exicom.entity.SiteDetails) siteDetailsQuery.getSingleResult();
				} catch (NoResultException ex) {
					throw new com.pratikshat.exicom.exception.BadRequestException("Site does not exist");
				}

				if (siteDetails == null) {
					throw new com.pratikshat.exicom.exception.BadRequestException("Site does not exist");
				}

				if (siteDetails.getCircle() != null) {

					if (_caseDetails.getBusinessDomain().getBusinessDomainName().equalsIgnoreCase("Battery")) {
						if (siteDetails.getCircle() != null) {
							if (siteDetails.getCircle().getCircleManagerBattry() != null) {
								emailId = siteDetails.getCircle().getCircleManagerBattry().getEmailId();
							}
						}
					}

					if (_caseDetails.getBusinessDomain().getBusinessDomainName().equalsIgnoreCase("DCT")) {
						if (siteDetails.getCircle() != null) {
							if (siteDetails.getCircle().getCircleManagerPower() != null) {
								emailId = siteDetails.getCircle().getCircleManagerPower().getEmailId();
							}
						}
					}
					if (_caseDetails.getBusinessDomain().getBusinessDomainName().equalsIgnoreCase("EV")) {
						if (siteDetails.getCircle() != null) {
							if (siteDetails.getCircle().getCircleManagerEv() != null) {
								emailId = siteDetails.getCircle().getCircleManagerEv().getEmailId();
							}
						}
					}

					if (emailId == null) {
						throw new com.pratikshat.exicom.exception.BadRequestException("Circle Manager does not exist");
					}

					// ServiceAppointment _serviceAppointment =
					// createServiceAppointment(_caseDetails, _emp, siteDetails, entityManager);

					ServiceAppointment _serviceAppointment = new ServiceAppointmentUtil()
							.createServiceAppointment(_caseDetails.getCaseId(), user.getUsername(), entityManager);

					if (serviceRequest.isAssignmentRule()) {
						ServiceRecource serviceRecource = null;
						try {
							Query serviceAppointmentOwnerQuery = entityManager.createQuery(
									"select sr from CaseDetails cd left join cd.site st left join st.serviceResource sr left join sr.user ur where cd.caseId=?1",
									ServiceRecource.class);
							serviceAppointmentOwnerQuery.setParameter(1, serviceRequest.getCaseId());
							serviceRecource = (ServiceRecource) serviceAppointmentOwnerQuery.getSingleResult();
						} catch (NoResultException ex) {

						}

						if (serviceRecource != null) {
							_serviceAppointment.setOwnerDetails(serviceRecource.getUser());
							_serviceAppointment.setServiceResource(serviceRecource);
						} else {
							try {
								Query schQuery = entityManager.createQuery(
										"select sr from ServiceRecource sr left join sr.user ur where ur.emailId=?1",
										ServiceRecource.class);
								schQuery.setParameter(1, emailId);
								ServiceRecource sch = (ServiceRecource) schQuery.getSingleResult();

								_serviceAppointment.setOwnerDetails(sch.getUser());
								_serviceAppointment.setServiceResource(sch);
							} catch (NoResultException ex) {
								throw new com.pratikshat.exicom.exception.BadRequestException("SCH deos not exist");
							}
						}

					} else {

						ServiceRecource serviceRecource = null;
						try {
							Query serviceAppointmentOwnerQuery = entityManager.createQuery(
									"select sr from ServiceRecource sr left join sr.user ur where sr.serviceResourceId=?1",
									ServiceRecource.class);
							serviceAppointmentOwnerQuery.setParameter(1, serviceRequest.getServiceResourceId());
							serviceRecource = (ServiceRecource) serviceAppointmentOwnerQuery.getSingleResult();
						} catch (NoResultException ex) {

						}

						if (serviceRecource != null) {
							_serviceAppointment.setOwnerDetails(serviceRecource.getUser());
							_serviceAppointment.setServiceResource(serviceRecource);
						} else {
							try {
								Query schQuery = entityManager.createQuery(
										"select sr from ServiceRecource sr left join sr.user ur where ur.emailId=?1",
										ServiceRecource.class);
								schQuery.setParameter(1, emailId);
								ServiceRecource sch = (ServiceRecource) schQuery.getSingleResult();

								_serviceAppointment.setOwnerDetails(sch.getUser());
								_serviceAppointment.setServiceResource(sch);

							} catch (NoResultException ex) {
								throw new com.pratikshat.exicom.exception.BadRequestException("SCH deos not exist");
							}
						}

					}

					workflow.executeWorkflow(entityManager, _serviceAppointment, "INSERT", "PRE", user);
					entityManager.persist(_serviceAppointment);
					workflow.executeWorkflow(entityManager, _serviceAppointment, "INSERT", "POST", user);

					Query updateServiceAppointmentManualId = entityManager.createQuery(
							"update ServiceAppointment sa set sa.appointmentNumber=?1 where sa.serviceAppointmentId=?2");
					SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
//					String serviceAppointmentManualId = "SA-" + df.format(new java.util.Date()) + "-"
//							+ com.pratikshat.exicom.config.MiscellaneousUtils
//									.fillZero(String.valueOf(_serviceAppointment.getServiceAppointmentId()), 5);

					String serviceAppointmentManualId = "SA-" + com.pratikshat.exicom.config.MiscellaneousUtils
							.fillZero(String.valueOf(_serviceAppointment.getServiceAppointmentId()), 7);
					updateServiceAppointmentManualId.setParameter(1, serviceAppointmentManualId);
					updateServiceAppointmentManualId.setParameter(2, _serviceAppointment.getServiceAppointmentId());
					updateServiceAppointmentManualId.executeUpdate();
					// notification to service appointment Eng
					try {
						ApplicationNotificationDetails notification = new ApplicationNotificationDetails();
						notification.setNofificationDate(new java.util.Date());
						notification.setNotificationActionUrl(
								"/serviceappointment/" + _serviceAppointment.getServiceAppointmentId());
						notification.setNotificationMessage(String
								.format("Service Appointment %s assigned for your action", serviceAppointmentManualId));
						notification.setNotificationRead(false);
						notification.setUserEmailId(_serviceAppointment.getServiceResource().getUser().getEmailId());
						entityManager.persist(notification);
						messagingTemplate.convertAndSend(
								"/topci/" + _serviceAppointment.getServiceResource().getUser().getEmailId(),
								notification);
					} catch (Exception ex) {

					}
					try {
						com.pratikshat.exicom.entity.GlobalSearchIndex _search = new com.pratikshat.exicom.entity.GlobalSearchIndex();
						_search.setKeyName("appointmentNumber");
						_search.setObjectDetailsUrl(
								"serviceappointment/" + _serviceAppointment.getServiceAppointmentId());
						_search.setObjectIdName("serviceAppointmentId");
						_search.setObjectIdValue("" + _serviceAppointment.getServiceAppointmentId());
						_search.setObjectName("ServiceAppointment");
						_search.setSearchString(serviceAppointmentManualId);
						entityManager.persist(_search);

					} catch (Exception e) {
					}

					if (_caseDetails.getBusinessDomain().getBusinessDomainId() == 1) { // Battery

						BatteryFSR batteryFSR = new BatteryFSR();
						batteryFSR.setServiceAppointment(_serviceAppointment);
						entityManager.persist(batteryFSR);

						Query updateFSRManualId = entityManager
								.createQuery("update BatteryFSR fsr set fsr.manualId=?1 where fsr.id=?2");
						// String fsrManualId= "FSR-CBMS-LI-"+df.format(new
						// java.util.Date())+"-"+com.pratikshat.exicom.config.MiscellaneousUtils.fillZero(String.valueOf(batteryFSR.getId()),5);

						String fsrManualId = "FSR-CBMS-LI-" + com.pratikshat.exicom.config.MiscellaneousUtils
								.fillZero(String.valueOf(batteryFSR.getId()), 7);
						updateFSRManualId.setParameter(1, fsrManualId);
						updateFSRManualId.setParameter(2, batteryFSR.getId());
						updateFSRManualId.executeUpdate();

						// search index entry
						try {
							com.pratikshat.exicom.entity.GlobalSearchIndex _search = new com.pratikshat.exicom.entity.GlobalSearchIndex();
							_search.setKeyName("manualId");
							_search.setObjectDetailsUrl("Batteryfsr/" + batteryFSR.getId());
							_search.setObjectIdName("id");
							_search.setObjectIdValue("" + batteryFSR.getId());
							_search.setObjectName("BatteryFSR");
							_search.setSearchString(fsrManualId);
							entityManager.persist(_search);

						} catch (Exception e) {
						}
					}

					return _serviceAppointment;

				} else {
					throw new com.pratikshat.exicom.exception.BadRequestException("Circle does not exist");
				}
			} else {
				throw new com.pratikshat.exicom.exception.BadRequestException("Site does not exist");
			}
		} else {
			throw new com.pratikshat.exicom.exception.BadRequestException("Case does not exist");
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = {
			BadRequestException.class, UnauthoriseException.class, RuntimeException.class, Exception.class,
			SQLException.class })
	public List<String> getOkSerialNumber(long locationProductItemId, LoginUser user) throws Exception {
		return entityManager.createQuery(
				"select item.itemSerialNumber from LocationProductLineItems item join item.locationProductItem litem join item.locationProeuctLineItemState st where litem.id=?1 and st.productStateName=?2 order by item.createdDate",
				String.class).setParameter(1, locationProductItemId).setParameter(2, "Ok").getResultList();
	}

	private CaseStageDetails getCaseStage(List<CaseStageDetails> caseStageList, String stageName) {
		for (int i = 0; i < caseStageList.size(); i++) {
			if (caseStageList.get(i).getCaseStageName().equalsIgnoreCase(stageName)) {
				return caseStageList.get(i);
			}
		}
		return null;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = {
			BadRequestException.class, UnauthoriseException.class, RuntimeException.class, Exception.class,
			SQLException.class })
	@Override
	public void requestForAnotherServiceAppointment(long caseId, String remark, LoginUser user) throws Exception {

		CaseDetails _caseDetails = null;
		try {
			Query selectCaseDetailsQuery = entityManager.createQuery(
					"select ca from CaseDetails ca left join ca.status sta left join ca.assets ast left join ast.account acn left join ast.site st left join ca.businessDomain bd left join ca.caseType ct left join ca.ownerDetails onr left join ca.createdBy createdby left join ca.modifiedBy modified left join ca.engineer eng left join eng.contact econ left join ca.serviceCenter scr left join onr.contact onrcnt where ca.caseId=?1",
					CaseDetails.class);
			selectCaseDetailsQuery.setParameter(1, caseId);
			_caseDetails = (CaseDetails) selectCaseDetailsQuery.getSingleResult();
		} catch (NoResultException ex) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Case does not exist");
		}

		List<ServiceAppointment> SAList = null;
		try {
			SAList = entityManager.createQuery(
					"select sa from ServiceAppointment sa left join sa.caseDetails cs join sa.status st left join sa.claimStatus css left join sa.circleManager cm where cs.caseId=?1 order by sa.createDate desc",
					ServiceAppointment.class).setParameter(1, caseId).getResultList();

		} catch (javax.persistence.NoResultException ex) {
			throw new Exception("Service Appointment does not exist");
		}

		if (SAList == null) {
			throw new Exception("Service Appointment does not exist");
		}

		if (SAList.isEmpty()) {
			throw new Exception("Service Appointment does not exist");
		}

		List<Long> list = new ArrayList<Long>();
		list.add(5L); // In-complete
		list.add(6L); // Cannot complete
		list.add(7L); // Subsequently complete

		for (int i = 0; i < SAList.size(); i++) {
			ServiceAppointment serviceAppointment = SAList.get(i);
			if (!list.contains(serviceAppointment.getStatus().getStatusId())) {
				throw new Exception("You can not request for another service appointment on this case");
			}
		}

		ToBeApproved tobeApproved = null;
		try {
			Query tobeApprovedQuery = entityManager.createQuery(
					"select tobe from ToBeApproved tobe where tobe.ObjectName=?1 and tobe.objectId=?2 and tobe.status=?3",
					ToBeApproved.class);
			tobeApprovedQuery.setParameter(1, "CaseDetails");
			tobeApprovedQuery.setParameter(2, caseId);
			tobeApprovedQuery.setParameter(3, false);
			tobeApproved = (ToBeApproved) tobeApprovedQuery.getSingleResult();

		} catch (javax.persistence.NoResultException ex) {

		}

		if (tobeApproved != null) {
			throw new BadRequestException("Request for another service appointment on this case already submitted");
		}

		// approval process entry point
		try {

			java.util.Set<String> _roles = new java.util.HashSet<String>();
			java.util.Iterator ite = user.getAuthorities().iterator();
			while (ite.hasNext()) {
				com.pratikshat.exicom.entity.LoginUserRoles _rr = (com.pratikshat.exicom.entity.LoginUserRoles) ite
						.next();
				_roles.add(_rr.getAuthority());
			}

			boolean roleFound = false;
			boolean userFound = false;
			com.pratikshat.exicom.entity.Approval _appliedRule = new com.pratikshat.exicom.entity.Approval();

			java.util.List<com.pratikshat.exicom.entity.Approval> _rulesLst = new java.util.ArrayList<com.pratikshat.exicom.entity.Approval>();

			_rulesLst = entityManager.createQuery(
					"select apr from Approval apr where apr.objectClassName=?1 and apr.active=?2 and apr.businessDomain.businessDomainId=?3",
					com.pratikshat.exicom.entity.Approval.class).setParameter(1, "CaseDetails").setParameter(2, true)
					.setParameter(3, _caseDetails.getBusinessDomain().getBusinessDomainId()).getResultList();

			for (com.pratikshat.exicom.entity.Approval _appr : _rulesLst) {
				String submitters[] = _appr.getInitialSubmitters().split(",");
				for (String submi : submitters) {
					String _subs[] = submi.split(":");
					if (_subs[0].equalsIgnoreCase("ROLE")) {
						if (_roles.contains(_subs[1])) {
							roleFound = true;
							break;
						}
					}
					if (_subs[0].equalsIgnoreCase("USER")) {
						if (user.getUsername().equalsIgnoreCase(_subs[1])) {
							userFound = true;
							break;
						}
					}
				}
				if (roleFound || userFound) {
					_appliedRule = _appr;
					break;
				}
			}

			com.pratikshat.exicom.entity.ApprovalSteps _stapes = entityManager
					.createQuery("select stp from ApprovalSteps stp where stp.approval=?1 and stp.approvedStage=?2",
							com.pratikshat.exicom.entity.ApprovalSteps.class)
					.setParameter(1, _appliedRule).setParameter(2, 1).getSingleResult();

			String assigned[] = _stapes.getApprovedBy().split(":");

			String assigneUserEmailId = "";

			if (assigned[0].equalsIgnoreCase("USER")) {
				assigneUserEmailId = assigned[1];
			}
			if (assigned[0].equalsIgnoreCase("APPROVAL-MANAGER")) {
				assigneUserEmailId = entityManager.createQuery(
						"select apm.emailId from EmployeeDetails emp join emp.approvalManager apm where emp.emailId=?1",
						String.class).setParameter(1, user.getUsername()).getSingleResult();
			}

			if (assigned[0].equalsIgnoreCase("MANAGER")) {
				assigneUserEmailId = entityManager.createQuery(
						"select apm.emailId from EmployeeDetails emp join emp.manager apm where emp.emailId=?1",
						String.class).setParameter(1, user.getUsername()).getSingleResult();
			}

			if (assigned[0].equalsIgnoreCase("ROLE")) {
				assigneUserEmailId = getApproverEmail(assigned[1], user.getUsername(), entityManager);
			}

			com.pratikshat.exicom.entity.ToBeApproved _toBeApproved = new com.pratikshat.exicom.entity.ToBeApproved();
			_toBeApproved.setAssignedTo(new com.pratikshat.exicom.entity.EmployeeDetails(assigneUserEmailId));
			_toBeApproved.setAssignedDate(new java.util.Date());
			_toBeApproved.setObjectId(_caseDetails.getCaseId());
			_toBeApproved.setObjectName("CaseDetails");
			_toBeApproved.setObjectId(_caseDetails.getCaseId());
			_toBeApproved.setObjectManualId(_caseDetails.getManualId());
			_toBeApproved.setObjectUrl("/cases/" + _caseDetails.getCaseId());
			_toBeApproved.setRemark("");
			_toBeApproved.setApprovalRuleId(_appliedRule.getId());
			_toBeApproved.setStage(1);
			_toBeApproved.setStatus(false);
			_toBeApproved.setPreviousRemarks(remark);
			entityManager.persist(_toBeApproved);

			com.pratikshat.exicom.entity.ApplicationNotificationDetails notificationDetails = new com.pratikshat.exicom.entity.ApplicationNotificationDetails();
			notificationDetails.setNofificationDate(new java.util.Date());
			notificationDetails.setNotificationActionUrl("/itemtobeapproved");
			notificationDetails.setNotificationMessage(String.format(
					"Request for additional service appointment received against case %s", _caseDetails.getManualId()));
			notificationDetails.setNotificationRead(false);
			notificationDetails.setUserEmailId(assigneUserEmailId);

			entityManager.persist(notificationDetails);

			try {
				messagingTemplate.convertAndSend("/topic/" + assigneUserEmailId, notificationDetails);
			} catch (Exception ex) {
			}

			TemplateDetails templateDetails = null;
			try {
				Query templateQuery = entityManager.createQuery(
						"select tmpd from TemplateDetails tmpd where tmpd.active=?1 and tmpd.templateType=?2 and tmpd.businessDomain=?3 and tmpd.eventName=?4",
						TemplateDetails.class);
				templateQuery.setParameter(1, true);
				templateQuery.setParameter(2, TemplateType.SMS);
				templateQuery.setParameter(3, new BusinessDomain(2));
				templateQuery.setParameter(4, "request_for_sa");

				templateDetails = (TemplateDetails) templateQuery.getSingleResult();
			} catch (NoResultException nex) {
				nex.printStackTrace();
			} catch (NonUniqueResultException e) {
				e.printStackTrace();
			}
			String smsMessage = null;
			if (templateDetails != null) {
				smsMessage = templateDetails.getBody();
				smsMessage = smsMessage.replaceFirst("<var>", _toBeApproved.getObjectManualId());
				smsMessage = smsMessage.replaceFirst("<var>", feedbackurl + "/#/cases/" + _toBeApproved.getObjectId());
			}

			if (smsMessage != null && !smsMessage.equalsIgnoreCase("")) {
				SmsNotifier sNotifier = new SmsNotifier(entityManager,
						_caseDetails.getOwnerDetails().getContact().getMobile(), smsMessage, user.getUsername(),
						templateDetails.getTemplaeCode());
				sNotifier.sendNotification();
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new BadRequestException("Unable to submit request for approval");

		}

		entityManager.flush();
		entityManager.clear();
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = {
			BadRequestException.class, UnauthoriseException.class, RuntimeException.class, Exception.class,
			SQLException.class })
	@Override
	public ServiceAppointmentReAssignRequest reAssignServiceAppointment(ServiceAppointmentReAssignRequest request,
			LoginUser user) throws Exception {
		ServiceAppointment serviceAppointment = null;
		try {
			serviceAppointment = entityManager.createQuery(
					"select sa from ServiceAppointment sa left join sa.caseDetails cd join sa.status st left join sa.claimStatus cs join sa.circleManager cm where sa.serviceAppointmentId=?1",
					ServiceAppointment.class).setParameter(1, request.getServiceAppointmentId()).getSingleResult();

		} catch (javax.persistence.NoResultException ex) {
			throw new Exception("Service Appointment does not exist");
		}
		if (!serviceAppointment.getCircleManager().getEmailId().equalsIgnoreCase(user.getUsername())) {
			throw new Exception("You are not authorised");
		}

		List<Long> list = new ArrayList<Long>();
		list.add(1L); // Unassigned
		list.add(2L); // Scheduled
		list.add(3L); // Dispatched
		if (!list.contains(serviceAppointment.getStatus().getStatusId())) {
			throw new Exception("You can not re-assign this service appointment");
		}

		try {
			Query serviceResourceQuery = entityManager.createQuery(
					"select sr from ServiceRecource sr left join sr.user ur where sr.serviceResourceId=?1",
					ServiceRecource.class);
			serviceResourceQuery.setParameter(1, request.getServiceResourceId());
			ServiceRecource serviceResource = (ServiceRecource) serviceResourceQuery.getSingleResult();

			CaseDetails caseDetails = null;
			try {
				caseDetails = entityManager
						.createQuery("select cd from CaseDetails cd left join cd.engineer where cd.caseId=?1",
								CaseDetails.class)
						.setParameter(1, serviceAppointment.getCaseDetails().getCaseId()).getSingleResult();

			} catch (javax.persistence.NoResultException ex) {
				throw new Exception("Case does not exist");
			}

			caseDetails.setEngineer(serviceResource);
			entityManager.persist(caseDetails);

			serviceAppointment.setOwnerDetails(serviceResource.getUser());
			serviceAppointment.setServiceResource(serviceResource);
			serviceAppointment.setReassign(true);

			try {
				ApplicationNotificationDetails notification = new ApplicationNotificationDetails();
				notification.setNofificationDate(new java.util.Date());
				notification.setNotificationActionUrl(
						"/serviceappointment/" + serviceAppointment.getServiceAppointmentId());
				notification.setNotificationMessage(
						String.format("Service Appointment %s has been send to you for approval",
								serviceAppointment.getAppointmentNumber()));
				notification.setNotificationRead(false);
				notification.setUserEmailId(serviceResource.getUser().getEmailId());
				entityManager.persist(notification);
				messagingTemplate.convertAndSend(notification);
			} catch (Exception ex) {

			}
// comment for re requirment for not send sms on re assignment
//			try {
//
//				com.pratikshat.exicom.entity.ContactDetails contact = entityManager
//						.createQuery("select c from ContactDetails c where c.contactId=?1",
//								com.pratikshat.exicom.entity.ContactDetails.class)
//						.setParameter(1, serviceResource.getContact().getContactId()).getSingleResult();
//
//				com.pratikshat.exicom.entity.TemplateDetails templateDetails = null;
//
//				try {
//					Query templateQuery = entityManager.createQuery(
//							"select tmpd from TemplateDetails tmpd where tmpd.active=?1 and tmpd.templateType=?2 and tmpd.businessDomain=?3 and tmpd.eventName=?4",
//							TemplateDetails.class);
//					templateQuery.setParameter(1, true);
//					templateQuery.setParameter(2, TemplateType.SMS);
//					templateQuery.setParameter(3, new BusinessDomain(2));
//					templateQuery.setParameter(4, "assign_sa");
//
//					templateDetails = (TemplateDetails) templateQuery.getSingleResult();
//				} catch (javax.persistence.NoResultException nex) {
//					nex.printStackTrace();
//				} catch (javax.persistence.NonUniqueResultException e) {
//					e.printStackTrace();
//				}
//				String smsMessage = null;
//				if (templateDetails != null) {
//					smsMessage = templateDetails.getBody();
//					smsMessage = smsMessage.replaceFirst("<var>", serviceResource.getName());
//					smsMessage = smsMessage.replaceFirst("<var>", serviceAppointment.getAppointmentNumber());
//					smsMessage = smsMessage.replaceFirst("<var>",
//							feedbackurl + "/#/serviceappointment/" + serviceAppointment.getServiceAppointmentId());
//				}
//
//				if (smsMessage != null && !smsMessage.equalsIgnoreCase("")) {
//					com.pratikshat.exicom.utils.SmsNotifier sNotifier = new com.pratikshat.exicom.utils.SmsNotifier(
//							entityManager, contact.getMobile(), smsMessage, user.getUsername(),
//							templateDetails.getTemplaeCode());
//					sNotifier.sendNotification();
//				}
//			} catch (Exception ex) {
//				ex.printStackTrace();
//			}

		} catch (NoResultException ex) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Service Resource deos not exist");
		}

		entityManager.persist(serviceAppointment);

		entityManager.flush();
		entityManager.clear();

		return request;
	}

	private String getApproverEmail(String role, String userMailId, javax.persistence.EntityManager _em) {
		com.pratikshat.exicom.entity.EmployeeDetails emp = null;
		emp = _em
				.createQuery("select mng from EmployeeDetails emp join emp.manager mng where emp.emailId=?1",
						com.pratikshat.exicom.entity.EmployeeDetails.class)
				.setParameter(1, userMailId).getSingleResult();

		java.util.List<String> roles = _em.createQuery(
				"select rol.authority from LoginUserRoles rol join rol.loginUser usr where usr.username=?1",
				String.class).setParameter(1, emp.getEmailId()).getResultList();

		for (String _rl : roles) {
			if (_rl.equalsIgnoreCase(role)) {
				return emp.getEmailId();
			}
		}
		return getApproverEmail(role, emp.getEmailId(), _em);

	}

	public ServiceAppointment createServiceAppointment(CaseDetails caseDetails, EmployeeDetails _emp,
			SiteDetails siteDetails, EntityManager entityManager) {

		com.pratikshat.exicom.entity.ServiceAppointment _serviceAppointment = new com.pratikshat.exicom.entity.ServiceAppointment();

		_serviceAppointment.setCaseDetails(new com.pratikshat.exicom.entity.CaseDetails(caseDetails.getCaseId()));
		_serviceAppointment.setCreateDate(new java.util.Date());
		_serviceAppointment.setCreatedBy(_emp);
		_serviceAppointment.setModifiedBy(_emp);
		_serviceAppointment.setModifiedDate(new java.util.Date());
		_serviceAppointment.setSyncFlag(false);
		_serviceAppointment.setCircle(siteDetails.getCircle());
		_serviceAppointment.setReassign(false);
		if (caseDetails.getAssets() != null) {
			try {
				Query assetsQuery = entityManager.createQuery(
						"select ast from Assets ast left join ast.product prd where ast.serialNumber=?1", Assets.class);
				assetsQuery.setParameter(1, caseDetails.getAssets().getSerialNumber());
				Assets assets = (Assets) assetsQuery.getSingleResult();

				_serviceAppointment.setAssets(assets);
				_serviceAppointment.setProduct(assets.getProduct());
			} catch (NoResultException ex) {

			}
		}

		if (caseDetails.getCaseType().getCaseTypesName().equalsIgnoreCase("Under Warranty")) {
			_serviceAppointment.setCostCenter("3002010104");
		} else {
			_serviceAppointment.setCostCenter("3002010102");
		}
		if (caseDetails.getBusinessDomain().getBusinessDomainName().equalsIgnoreCase("Battery")) {
			if (siteDetails.getCircle() != null) {
				if (siteDetails.getCircle().getCircleManagerBattry() != null) {
					_serviceAppointment.setCircleManager(siteDetails.getCircle().getCircleManagerBattry());
				}
			}
		}

		if (caseDetails.getBusinessDomain().getBusinessDomainName().equalsIgnoreCase("DCT")) {
			if (siteDetails.getCircle() != null) {
				if (siteDetails.getCircle().getCircleManagerPower() != null) {
					_serviceAppointment.setCircleManager(siteDetails.getCircle().getCircleManagerPower());
				}
			}
		}
		if (caseDetails.getBusinessDomain().getBusinessDomainName().equalsIgnoreCase("EV")) {
			if (siteDetails.getCircle() != null) {
				if (siteDetails.getCircle().getCircleManagerEv() != null) {
					_serviceAppointment.setCircleManager(siteDetails.getCircle().getCircleManagerEv());
				}
			}
		}

		_serviceAppointment.setStatus(new com.pratikshat.exicom.entity.ServiceAppointmentStatus(3)); // dispatch
		_serviceAppointment.setPendingResponsibility(new PendingResponsibility(1));

		if (_emp.getMiddleName() != null) {
			String name = String.format("%s %s %s", _emp.getFirstName(), _emp.getMiddleName(), _emp.getLastName());
			_serviceAppointment.setCustomerRepresentativeName(name);
		} else {
			String name = String.format("%s %s", _emp.getFirstName(), _emp.getLastName());
			_serviceAppointment.setCustomerRepresentativeName(name);
		}

		if (_emp.getContact() != null) {
			_serviceAppointment.setCustomerRepresentativeContact(_emp.getContact().getMobile());
		}

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

		Calendar cal = Calendar.getInstance();
		String time = timeFormat.format(cal.getTime());

		_serviceAppointment.setEarliestStartPermittedDate(dateFormat.format(cal.getTime()));
		_serviceAppointment.setEarliestStartPermittedTime(time);

		cal.add(Calendar.DATE, 30);

		_serviceAppointment.setDueDate(dateFormat.format(cal.getTime()));
		_serviceAppointment.setDueTime(time);
		_serviceAppointment.settFlag("N");
		_serviceAppointment.setBusinessDomain(caseDetails.getBusinessDomain());
		_serviceAppointment.setClaimStatus(new ClaimStatus(2L)); // To be Claimed
		return _serviceAppointment;
	}

	@Override
	public List<ServiceAppointmentResponse> getAdvanceSearchResult(LoginUser user, Map<String, Object> filterData,
			List<String> users) throws ParseException {
		StringBuilder queryStringBuilder = new StringBuilder();
		queryStringBuilder.append(
				"select new com.pratikshat.exicom.model.response.ServiceAppointmentResponse(sa) from ServiceAppointment sa left join sa.caseDetails cs left join cs.assets ast left join cs.site st left join st.circle cl left join st.ssa zn left join sa.claimStatus clm left join sa.status ss where 1=1");
		if (filterData.get("sanumber") != null && !filterData.get("sanumber").equals(""))
			queryStringBuilder.append(" and sa.appointmentNumber like :sanumber");
		if (filterData.get("casenumber") != null && !filterData.get("casenumber").equals(""))
			queryStringBuilder.append(" and cs.manualId like :casenumber");
		if (filterData.get("site") != null && !filterData.get("site").equals(""))
			queryStringBuilder
					.append(" and (st.siteName like :site or st.infraId like :site or st.clusterDistrict like :site)");
		if (filterData.get("ctype") != null && !filterData.get("ctype").equals(""))
			queryStringBuilder.append(" and cs.caseType.caseTypesName like :ctype");
		if (filterData.get("creason") != null && !filterData.get("creason").equals(""))
			queryStringBuilder.append(" and cs.caseReason.caseReasonDetailsName like :creason");
		if (filterData.get("circle") != null && !filterData.get("creason").equals(""))
			queryStringBuilder.append(" and cl.circleName like :circle");
		if (filterData.get("assets") != null && !filterData.get("assets").equals(""))
			queryStringBuilder.append(" and ast.serialNumber like :assets");
		if (filterData.get("status") != null && !filterData.get("status").equals(""))
			queryStringBuilder.append(" and ss.statusName like :status");
		if (filterData.get("cstatus") != null && !filterData.get("cstatus").equals(""))
			queryStringBuilder.append(" and clm.claimStatusName like :cstatus");
		if (filterData.get("srs") != null && !filterData.get("srs").equals(""))
			queryStringBuilder.append(" and sa.serviceResource.name like :srs");
		if (filterData.get("crdf") != null && !filterData.get("crdf").equals("") && filterData.get("crdt") != null
				&& !filterData.get("crdt").equals(""))
			// queryStringBuilder.append(" and (sa.createDate between date(:crdf) and
			// date(:crdt))");
			queryStringBuilder.append(
					" and (function('date_format', sa.createDate, '%Y, %m, %d') between function('date_format', :crdf, '%Y, %m, %d') and function('date_format', :crdt, '%Y, %m, %d'))");
		if (users != null)
			queryStringBuilder.append(" and sa.ownerDetails in :users");
		Query qr = entityManager.createQuery(queryStringBuilder.toString(), ServiceAppointmentResponse.class);

		logger.info(queryStringBuilder.toString());

		if (filterData.get("sanumber") != null && !filterData.get("sanumber").equals(""))
			// queryStringBuilder.append(" and sa.appointmentNumber like '%:sanumber%'");
			qr.setParameter("sanumber", "%" + filterData.get("sanumber") + "%");

		if (filterData.get("casenumber") != null && !filterData.get("casenumber").equals(""))
			// queryStringBuilder.append(" and cs.manualId like '%:casenumber%'");
			qr.setParameter("casenumber", "%" + filterData.get("casenumber") + "%");

		if (filterData.get("site") != null && !filterData.get("site").equals(""))
			// queryStringBuilder.append(" and (st.siteName like '%:site%' or st.infraId
			// like '%:site%' or st.clusterDistrict like '%:site%')");
			qr.setParameter("site", "%" + filterData.get("site") + "%");

		if (filterData.get("ctype") != null && !filterData.get("ctype").equals(""))
			// queryStringBuilder.append(" and cs.caseType.caseTypesName like '%:ctype%'");
			qr.setParameter("ctype", "%" + filterData.get("ctype") + "%");

		if (filterData.get("creason") != null && !filterData.get("creason").equals(""))
			// queryStringBuilder.append(" and cs.caseReason.caseReasonDetailsName like
			// '%:creason%'");
			qr.setParameter("creason", "%" + filterData.get("creason") + "%");
		if (filterData.get("circle") != null && !filterData.get("creason").equals(""))
			// queryStringBuilder.append(" and cl.circleName like '%:circle%'");
			qr.setParameter("circle", "%" + filterData.get("circle") + "%");
		if (filterData.get("assets") != null && !filterData.get("assets").equals(""))
			// queryStringBuilder.append(" and ast.serialNumber like '%:assets%'");
			qr.setParameter("assets", "%" + filterData.get("assets") + "%");
		if (filterData.get("status") != null && !filterData.get("status").equals(""))
			// queryStringBuilder.append(" and ss.statusName like '%:status%'");
			qr.setParameter("status", "%" + filterData.get("status") + "%");
		if (filterData.get("cstatus") != null && !filterData.get("cstatus").equals(""))
			// queryStringBuilder.append(" and clm.claimStatusName like '%:cstatus%'");
			qr.setParameter("cstatus", "%" + filterData.get("cstatus") + "%");
		if (filterData.get("srs") != null && !filterData.get("srs").equals(""))
			// queryStringBuilder.append(" and sa.serviceResource.name like '%:srs%'");
			qr.setParameter("srs", "%" + filterData.get("srs") + "%");
		if (filterData.get("crdf") != null && !filterData.get("crdf").equals("") && filterData.get("crdt") != null
				&& !filterData.get("crdt").equals("")) {
			// queryStringBuilder.append(" and (sa.createDate between date(:crdf) and
			// date(:crdt))");
			SimpleDateFormat dformatter = new SimpleDateFormat("dd-MM-yy");
			qr.setParameter("crdf", filterData.get("crdf").toString());
			qr.setParameter("crdt", filterData.get("crdt").toString());
		}
		if (users != null) {
			List<EmployeeDetails> emps = new ArrayList<EmployeeDetails>();
			for (String usr : users) {
				emps.add(new EmployeeDetails(usr));
			}
			qr.setParameter("users", emps);
		}
		return qr.getResultList();
	}

	public void serviceAppointmentClaimApproval(ServiceAppointment serviceAppointment, BusinessDomain businessDomain,
			LoginUser user) throws BadRequestException {
		// approval process entry point
		try {

			java.util.Set<String> _roles = new java.util.HashSet<String>();
			java.util.Iterator ite = user.getAuthorities().iterator();
			while (ite.hasNext()) {
				com.pratikshat.exicom.entity.LoginUserRoles _rr = (com.pratikshat.exicom.entity.LoginUserRoles) ite
						.next();
				_roles.add(_rr.getAuthority());
			}

			boolean roleFound = false;
			boolean userFound = false;
			com.pratikshat.exicom.entity.Approval _appliedRule = new com.pratikshat.exicom.entity.Approval();

			java.util.List<com.pratikshat.exicom.entity.Approval> _rulesLst = new java.util.ArrayList<com.pratikshat.exicom.entity.Approval>();

			_rulesLst = entityManager.createQuery(
					"select apr from Approval apr where apr.objectClassName=?1 and apr.active=?2 and apr.businessDomain.businessDomainId=?3",
					com.pratikshat.exicom.entity.Approval.class).setParameter(1, "ServiceAppointment")
					.setParameter(2, true).setParameter(3, businessDomain.getBusinessDomainId()).getResultList();

			CircleDetails circleDetails = null;
			try {
				Query circleDetailsQuery = entityManager.createQuery(
						"select cr from CircleDetails cr left join cr.circleManagerBattry cb left join cr.circleManagerPower cp left join cr.circleManagerEv ev where cr=?1",
						com.pratikshat.exicom.entity.CircleDetails.class);
				// System.out.println("Circle name "+serviceAppointment.getCircle());
				circleDetailsQuery.setParameter(1, serviceAppointment.getCircle());
				circleDetails = (com.pratikshat.exicom.entity.CircleDetails) circleDetailsQuery.getSingleResult();
			} catch (NoResultException ex) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Circle does not exist");
			}

			String circleManagerEmailId = null;
			if (circleDetails != null) {
				if (businessDomain.getBusinessDomainId() == 1) { // Battery
					if (circleDetails.getCircleManagerBattry() != null) {
						circleManagerEmailId = circleDetails.getCircleManagerBattry().getEmailId();
					}
				}

				if (businessDomain.getBusinessDomainId() == 2) { // DCT
					if (circleDetails.getCircleManagerPower() != null) {
						circleManagerEmailId = circleDetails.getCircleManagerPower().getEmailId();
					}
				}
				if (businessDomain.getBusinessDomainId() == 3) { // EV
					if (circleDetails.getCircleManagerEv() != null) {
						circleManagerEmailId = circleDetails.getCircleManagerEv().getEmailId();
					}
				}
			} else {
				throw new com.pratikshat.exicom.exception.BadRequestException("Circle does not exist");
			}

			if (circleManagerEmailId == null) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Circle manager does not exist");
			}

			EmployeeDetails employeeDetails = null;
			try {
				Query employeeQuery = entityManager.createQuery(
						"select ed from EmployeeDetails ed join ed.contact ct join ct.legalEntity lg left join ed.approvalManager apm left join ed.manager mg where ed.emailId=?1",
						com.pratikshat.exicom.entity.EmployeeDetails.class);
				employeeQuery.setParameter(1, user.getUsername());
				employeeDetails = (com.pratikshat.exicom.entity.EmployeeDetails) employeeQuery.getSingleResult();
			} catch (NoResultException ex) {
				throw new com.pratikshat.exicom.exception.BadRequestException("EmployeeDetails does not exist");
			}

			if (employeeDetails == null) {
				throw new com.pratikshat.exicom.exception.BadRequestException("EmployeeDetails does not exist");
			}

			CaseDetails caseDetails = null;
			try {
				Query caseQuery = entityManager.createQuery(
						"select cd from CaseDetails cd join cd.serviceCenter where cd.caseId=?1",
						com.pratikshat.exicom.entity.CaseDetails.class);
				caseQuery.setParameter(1, serviceAppointment.getCaseDetails().getCaseId());
				caseDetails = (com.pratikshat.exicom.entity.CaseDetails) caseQuery.getSingleResult();
			} catch (NoResultException ex) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Case does not exist");
			}

			if (caseDetails == null) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Case does not exist");
			}

			for (com.pratikshat.exicom.entity.Approval _appr : _rulesLst) {
				String submitters[] = _appr.getInitialSubmitters().split(",");
				for (String submi : submitters) {
					String _subs[] = submi.split(":");

					if (caseDetails.getServiceCenter().getLegalEntityId() != employeeDetails.getContact()
							.getLegalEntity().getLegalEntityId()) {
						if (_subs[0].equalsIgnoreCase("SA")) {
							roleFound = true;
							break;
						}

//						if(serviceAppointment.isReassign()) {
//							if(_subs[0].equalsIgnoreCase("SA")) {
//								roleFound = true;
//								break;
//							}	
//						}
//						else {
//							if (_subs[0].equalsIgnoreCase("ROLE")) {
//								if (_roles.contains(_subs[1])) {
//									roleFound = true;
//									break;
//								}
//							}
//							if (_subs[0].equalsIgnoreCase("USER")) {
//								if (user.getUsername().equalsIgnoreCase(_subs[1])) {
//									userFound = true;
//									break;
//								}
//							}
//						}
					} else {
						if (_subs[0].equalsIgnoreCase("ROLE")) {
							if (_roles.contains(_subs[1])) {
								roleFound = true;
								break;
							}
						}
						if (_subs[0].equalsIgnoreCase("USER")) {
							if (user.getUsername().equalsIgnoreCase(_subs[1])) {
								userFound = true;
								break;
							}
						}
					}
				}
				if (roleFound || userFound) {
					_appliedRule = _appr;
					break;
				}
			}

			com.pratikshat.exicom.entity.ApprovalSteps _stapes = entityManager
					.createQuery("select stp from ApprovalSteps stp where stp.approval=?1 and stp.approvedStage=?2",
							com.pratikshat.exicom.entity.ApprovalSteps.class)
					.setParameter(1, _appliedRule).setParameter(2, 1).getSingleResult();

			String assigned[] = _stapes.getApprovedBy().split(":");

			String assigneUserEmailId = "";

			if (assigned[0].equalsIgnoreCase("USER")) {
				assigneUserEmailId = assigned[1];
			}
			if (assigned[0].equalsIgnoreCase("APPROVAL-MANAGER")) {
				assigneUserEmailId = entityManager.createQuery(
						"select apm.emailId from EmployeeDetails emp join emp.approvalManager apm where emp.emailId=?1",
						String.class).setParameter(1, user.getUsername()).getSingleResult();
			}

			if (assigned[0].equalsIgnoreCase("MANAGER")) {
				assigneUserEmailId = entityManager.createQuery(
						"select apm.emailId from EmployeeDetails emp join emp.manager apm where emp.emailId=?1",
						String.class).setParameter(1, user.getUsername()).getSingleResult();
			}

			if (assigned[0].equalsIgnoreCase("ROLE")) {
				assigneUserEmailId = getApproverEmail(assigned[1], user.getUsername(), entityManager);
			}

			if (assigned[0].equalsIgnoreCase("MANUAL")) {
				assigneUserEmailId = serviceAppointment.getCircleManager().getEmailId();
			}

			com.pratikshat.exicom.entity.ToBeApproved _toBeApproved = new com.pratikshat.exicom.entity.ToBeApproved();
			_toBeApproved.setAssignedTo(new com.pratikshat.exicom.entity.EmployeeDetails(assigneUserEmailId));
			_toBeApproved.setAssignedDate(new java.util.Date());
			_toBeApproved.setObjectId(serviceAppointment.getServiceAppointmentId());
			_toBeApproved.setObjectName("ServiceAppointment");
			_toBeApproved.setObjectId(serviceAppointment.getServiceAppointmentId());
			_toBeApproved.setObjectManualId(serviceAppointment.getAppointmentNumber());
			_toBeApproved.setObjectUrl("/serviceappointment/" + serviceAppointment.getServiceAppointmentId());
			_toBeApproved.setRemark("");
			_toBeApproved.setApprovalRuleId(_appliedRule.getId());
			_toBeApproved.setStage(1);
			_toBeApproved.setStatus(false);
			_toBeApproved.setPreviousRemarks(null);
			entityManager.persist(_toBeApproved);

			com.pratikshat.exicom.entity.ApplicationNotificationDetails notificationDetails = new com.pratikshat.exicom.entity.ApplicationNotificationDetails();
			notificationDetails.setNofificationDate(new java.util.Date());
			notificationDetails.setNotificationActionUrl("/itemtobeapproved");
			notificationDetails.setNotificationMessage(String.format("Service appointment %s received for your appoval",
					serviceAppointment.getAppointmentNumber()));
			notificationDetails.setNotificationRead(false);
			notificationDetails.setUserEmailId(assigneUserEmailId);

			entityManager.persist(notificationDetails);

			try {
				messagingTemplate.convertAndSend("/topic/" + assigneUserEmailId, notificationDetails);
			} catch (Exception ex) {
			}

		} catch (Exception ex) {
			logger.error("Error On Approval", ex);
			ex.printStackTrace();
			throw new BadRequestException("Unable to submit request for approval");

		}

	}

	@Transactional(isolation = Isolation.READ_UNCOMMITTED, rollbackFor = { Exception.class, BadRequestException.class,
			UnauthoriseException.class, RuntimeException.class }, propagation = Propagation.REQUIRES_NEW)
	@Override
	public ByteArrayInputStream generatePdf(LoginUser user, long id) throws Exception {

		ClosedServiceAppointmentPDF pdf = new ClosedServiceAppointmentPDF(entityManager, fileStorageProperties);
		return pdf.createPdf(user, id);
	}

	@Override
	public boolean verifyCode(long id, String vfCode) {
		ServiceAppointment apt = null;
		try {
			apt = entityManager
					.createQuery("select sa from ServiceAppointment sa where sa=?1", ServiceAppointment.class)
					.setParameter(1, new ServiceAppointment(id)).getSingleResult();
			if (apt.getVfCode() != null && apt.getVfCode().equals(vfCode))
				return true;
		} catch (NoResultException ex) {
			ex.printStackTrace();
		}
		return false;
	}
}
