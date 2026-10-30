package com.pratikshat.exicom.repository;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pratikshat.exicom.entity.ApplicationNotificationDetails;
import com.pratikshat.exicom.entity.Assets;
import com.pratikshat.exicom.entity.BatteryFSR;
import com.pratikshat.exicom.entity.BusinessDomain;
import com.pratikshat.exicom.entity.CaseDetails;
import com.pratikshat.exicom.entity.CaseProblemReported;
import com.pratikshat.exicom.entity.CaseReasonDetails;
import com.pratikshat.exicom.entity.CaseRejectLog;
import com.pratikshat.exicom.entity.CaseStageDetails;
import com.pratikshat.exicom.entity.CaseStatusDetails;
import com.pratikshat.exicom.entity.CaseTypes;
import com.pratikshat.exicom.entity.ChangeCaseTypeConditions;
import com.pratikshat.exicom.entity.ChangeCaseTypeLog;
import com.pratikshat.exicom.entity.ClaimStatus;
import com.pratikshat.exicom.entity.ContactDetails;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.Oppertunity;
import com.pratikshat.exicom.entity.OppertunityForecastCategory;
import com.pratikshat.exicom.entity.OppertunityStage;
import com.pratikshat.exicom.entity.OppertunityType;
import com.pratikshat.exicom.entity.PendingResponsibility;
import com.pratikshat.exicom.entity.Quote;
import com.pratikshat.exicom.entity.ServiceAppointment;
import com.pratikshat.exicom.entity.ServiceAppointmentStatus;
import com.pratikshat.exicom.entity.ServiceRecource;
import com.pratikshat.exicom.entity.SiteDetails;
import com.pratikshat.exicom.entity.TemplateDetails;
import com.pratikshat.exicom.entity.TemplateType;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.CaseProofOfDeliveryRequest;
import com.pratikshat.exicom.model.request.CaseRequest;
import com.pratikshat.exicom.model.request.CaseSearchRequest;
import com.pratikshat.exicom.model.request.ChangeCaseTypeRequest;
import com.pratikshat.exicom.model.request.CustomerInventoryRequest;
import com.pratikshat.exicom.model.request.InvoiceUpdateRequest;
import com.pratikshat.exicom.model.request.RejectCaseRequest;
import com.pratikshat.exicom.model.response.CaseResponse;
import com.pratikshat.exicom.utils.ServiceAppointmentUtil;
import com.pratikshat.exicom.utils.SmsNotifier;

@Repository
public class CaseDetailsRepositoryCustomeImpl implements CaseDetailsRepositoryCustome {
	@PersistenceContext
	EntityManager entityManager;
	@Autowired
	private SimpMessagingTemplate messagingTemplate;
	
	@Autowired
	private UserProfileRepository dbProfile;
	
	@Autowired
	com.pratikshat.exicom.interceptor.WorkflowExecuter workflow;
	
	SimpleDateFormat inputSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	SimpleDateFormat outputSdf = new SimpleDateFormat("yyyy-MM-dd");
	
	@Value("${feedbackurl}")
	String feedbackurl;

	@Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = { Exception.class,RuntimeException.class,BadRequestException.class,UnauthoriseException.class}, propagation = Propagation.REQUIRES_NEW)
	@Override
	public CaseRequest saveCaseDetails(CaseRequest caseDetails, com.pratikshat.exicom.entity.LoginUser user)
			throws Exception {
		if(caseDetails.isiSmsSend()) {
			System.out.println("is sms send is -----"+caseDetails.isiSmsSend());
		}
		if (caseDetails != null) {
			com.pratikshat.exicom.entity.CaseDetails _caseDetails = new com.pratikshat.exicom.entity.CaseDetails();

			if (caseDetails.getAssetsSerialNumber() != null) {
				Assets assets = null;
				try {
					Query assetsQuery = entityManager.createQuery(
							"select ast from Assets ast left join ast.site left join ast.status sts where ast.serialNumber=?1",
							com.pratikshat.exicom.entity.Assets.class);
					assetsQuery.setParameter(1, caseDetails.getAssetsSerialNumber());
					assets = (Assets) assetsQuery.getSingleResult();
					
					if(assets!=null) {
						_caseDetails.setAssets(assets);
						if(assets.getAmcExpiryDate()!=null) {
							Date expiryDate =outputSdf.parse(outputSdf.format(assets.getAmcExpiryDate()));
							Date today =outputSdf.parse(outputSdf.format(new Date()));
							
							if(expiryDate.compareTo(today)>=0) {
								_caseDetails.setPODate(outputSdf.parse(assets.getAmcPurchaseOrderDate()));
								_caseDetails.setPONumber(assets.getAmcPurchaseOrderNumber());
							}	
						}
					}else {
						throw new com.pratikshat.exicom.exception.BadRequestException("Asset does not exist");
					}
				}catch(NoResultException ex) {
					throw new com.pratikshat.exicom.exception.BadRequestException("Asset does not exist");
				}catch(Exception ex) {
					throw new com.pratikshat.exicom.exception.BadRequestException(ex.getMessage());
				}
				
				if(assets!=null && assets.getStatus()!=null) {
					if(assets.getStatus().getAssetsStatusId()==1) {	// Dismantle
						throw new com.pratikshat.exicom.exception.BadRequestException("Asset is Dismantle");
					}
				}
			}else {
				List<Long> caseReasonList = new ArrayList<>();
				caseReasonList.add(12L); // Exceptional Case
				caseReasonList.add(18L); // Installation
				caseReasonList.add(2L); // Exceptional Case
				caseReasonList.add(8L); // Installation
				caseReasonList.add(43L); // Warehouse Health check-ups
				caseReasonList.add(45L); // Site Survey
				caseReasonList.add(46L); // Battery Bank Charging
				caseReasonList.add(40L); // Acceptance Testing
				caseReasonList.add(42L); // SMPS Kitting Warehouse
				if(caseReasonList.contains(caseDetails.getCaseReasonId())) {
					
					if(caseDetails.getCaseReasonId()==2L || caseDetails.getCaseReasonId()==12L) {
						if(caseDetails.getMissingAssets()==null) {
							throw new com.pratikshat.exicom.exception.BadRequestException("Please fill missing asset");
						}
					}
				}else {
					throw new com.pratikshat.exicom.exception.BadRequestException("Please select Asset");
					
				}	
			}
			
			
			
			SiteDetails site = null;
			if (caseDetails.getSiteId() != 0) {
				try {
					Query siteQuery = entityManager.createQuery(
							"select sti from SiteDetails sti join sti.account act where sti.siteId=?1",
							com.pratikshat.exicom.entity.SiteDetails.class);
					siteQuery.setParameter(1, caseDetails.getSiteId());
					site = (SiteDetails) siteQuery.getSingleResult();
					
					if(site!=null) {
						_caseDetails.setSite(site);
					}else {
						throw new com.pratikshat.exicom.exception.BadRequestException("Site does not exist");
					}
				}catch(NoResultException ex) {
					throw new com.pratikshat.exicom.exception.BadRequestException("Site does not exist");
				}
			} else {
				throw new com.pratikshat.exicom.exception.BadRequestException("Site does not exist");
			}
			

			if (caseDetails.getAssetsSerialNumber() != null) {
				try {
					Query assetsQuery = entityManager.createQuery(
							"select ast from Assets ast left join ast.site  where ast.serialNumber=?1",
							com.pratikshat.exicom.entity.Assets.class);
					assetsQuery.setParameter(1, caseDetails.getAssetsSerialNumber());
					Assets assets = (Assets) assetsQuery.getSingleResult();
					if (assets.getSite() != null) {
						if (assets.getSite().getSiteId() != caseDetails.getSiteId()) {
							throw new com.pratikshat.exicom.exception.BadRequestException(
									"Given site mismatch with the assets installed on the site");
						}
					} else {
						throw new com.pratikshat.exicom.exception.BadRequestException("Site does not exist");
					}
				} catch (NoResultException ex) {
					throw new com.pratikshat.exicom.exception.BadRequestException("Asset does not exist");
				}
			}
			
			try {
				Query contactQuery = entityManager.createQuery(
						"select cnt from ContactDetails cnt join cnt.account act  where cnt.contactId=?1 and act.sapCustomerNumber=?2",
						com.pratikshat.exicom.entity.ContactDetails.class);
				contactQuery.setParameter(1, caseDetails.getContactPersonId());
				contactQuery.setParameter(2, site.getAccount().getSapCustomerNumber());
				com.pratikshat.exicom.entity.ContactDetails contact = (com.pratikshat.exicom.entity.ContactDetails) contactQuery.getSingleResult();
				
				if(contact!=null) {
					_caseDetails.setContactPerson(contact);
				}else {
					throw new com.pratikshat.exicom.exception.BadRequestException("Contact does not exist");
				}
			}catch(NoResultException ex) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Contact does not exist");
			}
			
			try {
				Query caseReasonQuery = entityManager.createQuery("select crd from CaseReasonDetails crd join crd.caseType cty join crd.vertical vrt where cty.caseTypeId=?1 and vrt.businessDomainId=?2",CaseReasonDetails.class);
				caseReasonQuery.setParameter(1, caseDetails.getCaseTypeId());
				caseReasonQuery.setParameter(2, caseDetails.getCaseRecordTypeId());
				List<CaseReasonDetails> reasons = caseReasonQuery.getResultList();
				
				List<Long> reasonList = new ArrayList<>();
				for(int i=0; i<reasons.size(); i++) {
					CaseReasonDetails reason = reasons.get(i);
					reasonList.add(reason.getReasonId());
				}
				
				
				if(!reasonList.contains(caseDetails.getCaseReasonId())) {
					throw new com.pratikshat.exicom.exception.BadRequestException("Wrong case reason");
				}
			}catch(NoResultException ex) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Wrong case reason");
			}
			
			try {
				Query caseStageQuery = entityManager.createQuery("select stg from CaseStageDetails stg join stg.caseStatus sts join stg.division vrt where sts.id=?1 and vrt.businessDomainId=?2",CaseStageDetails.class);
				caseStageQuery.setParameter(1, caseDetails.getCaseStatusId());
				caseStageQuery.setParameter(2, caseDetails.getCaseRecordTypeId());
				List<CaseStageDetails> stages = caseStageQuery.getResultList();
				
				List<Integer> reasonList = new ArrayList<>();
				for(int i=0; i<stages.size(); i++) {
					CaseStageDetails stage = stages.get(i);
					reasonList.add(stage.getId());
				}
				
				if(!reasonList.contains(caseDetails.getCaseStageId())) {
					throw new com.pratikshat.exicom.exception.BadRequestException("Wrong case stage");
				}
			}catch(NoResultException ex) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Wrong case stage");
			}
			
			if (caseDetails.getServiceResourceId() != 0) {
				ServiceRecource serviceRecource = null;
				try {
					Query caseStageQuery = entityManager.createQuery("select sr from ServiceRecource sr join sr.contact cnt join cnt.legalEntity le join cnt.account act join sr.location lc where le.legalEntityId=?1 and sr.serviceResourceId=?2",ServiceRecource.class);
					caseStageQuery.setParameter(1, caseDetails.getServiceCenterId());
					caseStageQuery.setParameter(2, caseDetails.getServiceResourceId());
					serviceRecource = (ServiceRecource)caseStageQuery.getSingleResult();
					
				}catch(NoResultException ex) {
					throw new com.pratikshat.exicom.exception.BadRequestException("Service Resource is wrong");
				}
				
				if(serviceRecource==null) {
					throw new com.pratikshat.exicom.exception.BadRequestException("Service Resource is wrong");
				}
				
				_caseDetails.setEngineer(new com.pratikshat.exicom.entity.ServiceRecource(caseDetails.getServiceResourceId()));	
			}
			
			
			_caseDetails.setCaseReason(new com.pratikshat.exicom.entity.CaseReasonDetails(caseDetails.getCaseReasonId()));
			_caseDetails.setCaseType(new com.pratikshat.exicom.entity.CaseTypes(caseDetails.getCaseTypeId()));
			// Installation for Battery				// Installation for DCT
			if(caseDetails.getCaseTypeId()==2 || caseDetails.getCaseTypeId()==6) {
				
				if(caseDetails.getPoDate()!=null && caseDetails.getPoNumber()!=null) {
					_caseDetails.setPODate(caseDetails.getPoDate());
					_caseDetails.setPONumber(caseDetails.getPoNumber());	
				}else {
					throw new com.pratikshat.exicom.exception.BadRequestException("PO details missing");
				}
				
			}
			
			if(caseDetails.getCaseStatusId()==2 || caseDetails.getCaseStatusId()==4) {
				_caseDetails.setCloseDate(new Date());
				_caseDetails.setClosedBy(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
			}
			
			_caseDetails.setCreateDate(new java.util.Date());
			_caseDetails.setCreatedBy(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
			_caseDetails.setModifiedBy(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
			_caseDetails.setModifiedDate(new java.util.Date());
			_caseDetails.setNoOfsmr(caseDetails.getNoOfSmr());

			_caseDetails.setOrigin(new com.pratikshat.exicom.entity.CaseOriginDetails(caseDetails.getCaseOriginId()));
			_caseDetails.setOwnerDetails(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
			_caseDetails.setPriority(new com.pratikshat.exicom.entity.CasePriorityDetails(caseDetails.getCasePriorityId()));
			
			_caseDetails.setBusinessDomain(new com.pratikshat.exicom.entity.BusinessDomain(caseDetails.getCaseRecordTypeId()));
			_caseDetails.setRemarks(caseDetails.getRemarks());
			_caseDetails.setStage(new com.pratikshat.exicom.entity.CaseStageDetails(caseDetails.getCaseStageId()));
			_caseDetails.setStatus(new com.pratikshat.exicom.entity.CaseStatusDetails(caseDetails.getCaseStatusId()));
			_caseDetails.setSynked(false);
			_caseDetails.setMissingAssets(caseDetails.getMissingAssets());
			if (caseDetails.getServiceCenterId() != 0) {
				_caseDetails.setServiceCenter(new com.pratikshat.exicom.entity.LegalEntity(caseDetails.getServiceCenterId()));
			}

			_caseDetails.setOtherProblemReported(caseDetails.getOtherProblemReported());
			_caseDetails.setAssignByAssignmentRule(caseDetails.isAssignByAssignmentRule());
			_caseDetails.setInputFromEng(caseDetails.getInputFromEng());
			_caseDetails.setInternalRemark(caseDetails.getInternalRemark());
			_caseDetails.setRJILTT01No(caseDetails.getRJILTT01No());
			_caseDetails.setiSmsSend(caseDetails.isiSmsSend());
			if(caseDetails.getPendingResponsibility()!=0) {
				_caseDetails.setPendingResponsibility(new PendingResponsibility(caseDetails.getPendingResponsibility()));
			}
			
			if(caseDetails.getCaseStatusId()==2) {	// Closed
				_caseDetails.setCloseDate(new Date());
				_caseDetails.setClosedBy(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
			}
			
			workflow.executeWorkflow(entityManager, _caseDetails, "INSERT", "PRE", user);
			entityManager.persist(_caseDetails);
			
			
			Query updateManualId = entityManager.createQuery("update CaseDetails cas set cas.manualId=?1 where cas.caseId=?2");
			SimpleDateFormat dateformatter = new SimpleDateFormat("yyyyMMdd");
			String caseManualIdLastpart = dateformatter.format(new java.util.Date()) + "-"+ com.pratikshat.exicom.config.MiscellaneousUtils.fillZero(String.valueOf(_caseDetails.getCaseId()),5);
			//String caseManualIdLastpart = com.pratikshat.exicom.config.MiscellaneousUtils.fillZero(String.valueOf(_caseDetails.getCaseId()),7);
			String manualId = null;
			if(caseDetails.getCaseRecordTypeId()==1) { // Battery
				manualId = "CS-BTY-" + caseManualIdLastpart;
			}else if(caseDetails.getCaseRecordTypeId()==2) { // DCT
				manualId = "CS-DCT-" + caseManualIdLastpart;
			}else if(caseDetails.getCaseRecordTypeId()==3) { // EV
				manualId = "CS-EVC-" + caseManualIdLastpart;
			}
			
			updateManualId.setParameter(1, manualId);
			updateManualId.setParameter(2, _caseDetails.getCaseId());
			updateManualId.executeUpdate();
			caseDetails.setCaseId(_caseDetails.getCaseId());
			workflow.executeWorkflow(entityManager, _caseDetails, "INSERT", "POST", user);
			if (caseDetails.getCaseProblemReported() != null) {
				for (com.pratikshat.exicom.entity.CaseProblemReported pr : caseDetails.getCaseProblemReported()) {
						
					CaseProblemReported caseProblemReported= new CaseProblemReported();
					caseProblemReported.setProblemName(pr.getProblemName());
					caseProblemReported.setCaseDetails(new CaseDetails(_caseDetails.getCaseId()));
					entityManager.persist(caseProblemReported);
					/*
					 * Query updateProblemId = entityManager.createQuery(
					 * "update CaseProblemReported pr set pr.caseDetails.caseId=?1 where pr.probleReportedId=?2"
					 * ); updateProblemId.setParameter(1, _caseDetails.getCaseId());
					 * updateProblemId.setParameter(2, pr.getProbleReportedId());
					 * updateProblemId.executeUpdate();
					 */
				}
			}

			/*
			 * if(true) { throw new Exception("force excetion"); }
			 */

			// search index entry
			try {
				com.pratikshat.exicom.entity.GlobalSearchIndex _search = new com.pratikshat.exicom.entity.GlobalSearchIndex();
				_search.setKeyName("manualId");
				_search.setObjectDetailsUrl("cases/" + _caseDetails.getCaseId());
				_search.setObjectIdName("caseId");
				_search.setObjectIdValue("" + _caseDetails.getCaseId());
				_search.setObjectName("CaseDetails");
				_search.setSearchString(manualId);
				entityManager.persist(_search);

			} catch (Exception e) {
				
			}

			
			CaseTypes caseType = null;
			try {
				Query caseTypeQuery = entityManager.createQuery("from CaseTypes ct where ct.caseTypeId=?1",
						CaseTypes.class);
				caseTypeQuery.setParameter(1, caseDetails.getCaseTypeId());
				caseType = (CaseTypes) caseTypeQuery.getSingleResult();
			} catch (NoResultException ex) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Case Type does not exist");
			}

			CaseStatusDetails caseStatus = null;
			try {
				Query CaseStatusDetailsQuery = entityManager.createQuery("from CaseStatusDetails cs where cs.id=?1",
						CaseStatusDetails.class);
				CaseStatusDetailsQuery.setParameter(1, caseDetails.getCaseStatusId());
				caseStatus = (CaseStatusDetails) CaseStatusDetailsQuery.getSingleResult();
			} catch (NoResultException ex) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Case Status does not exist");
			}

			CaseStageDetails caseStageDetails = null;
			try {
				Query caseStageDetailsQuery = entityManager.createQuery(
						"select csd from CaseStageDetails csd where csd.id=?1",
						CaseStageDetails.class);
				caseStageDetailsQuery.setParameter(1, caseDetails.getCaseStageId());
				caseStageDetails = (CaseStageDetails) caseStageDetailsQuery.getSingleResult();
			} catch (NoResultException ex) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Case Stage does not exist");
			}

			CaseReasonDetails caseReason = null;
			try {
				Query CaseReasonDetailsQuery = entityManager
						.createQuery("from CaseReasonDetails cr where cr.reasonId=?1", CaseReasonDetails.class);
				CaseReasonDetailsQuery.setParameter(1, caseDetails.getCaseReasonId());
				caseReason = (CaseReasonDetails) CaseReasonDetailsQuery.getSingleResult();
			} catch (NoResultException ex) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Case Reason does not exist");
			}
			
			
			Query siteDetailsQuery = entityManager.createQuery(
					"select st from SiteDetails st  left join st.circle cr left join cr.circleManagerBattry cb left join cr.circleManagerPower cp left join cr.circleManagerEv ev where st.siteId=?1",
					com.pratikshat.exicom.entity.SiteDetails.class);

			Query buisnessModuleQuery = entityManager
					.createQuery("select bs from BusinessDomain bs where bs.businessDomainId=?1", BusinessDomain.class);
			buisnessModuleQuery.setParameter(1, caseDetails.getCaseRecordTypeId());
			BusinessDomain buisnessModule = (BusinessDomain) buisnessModuleQuery.getSingleResult();
			com.pratikshat.exicom.entity.SiteDetails siteDetails = null;
			try {
				siteDetailsQuery.setParameter(1, caseDetails.getSiteId());
				siteDetails = (com.pratikshat.exicom.entity.SiteDetails) siteDetailsQuery.getSingleResult();
			} catch (NoResultException ex) {

			}
			
			String emailId = null;
			if (siteDetails != null) {
				if (siteDetails.getCircle() != null) {
					if (buisnessModule.getBusinessDomainId()==1) { //Battery
						if (siteDetails.getCircle() != null) {
							if (siteDetails.getCircle().getCircleManagerBattry() != null) {
								emailId = siteDetails.getCircle().getCircleManagerBattry().getEmailId();
							}
						}
					}

					if (buisnessModule.getBusinessDomainId()==2) { // DCT
						if (siteDetails.getCircle() != null) {
							if (siteDetails.getCircle().getCircleManagerPower() != null) {
								emailId = siteDetails.getCircle().getCircleManagerPower().getEmailId();
							}
						}
					}
					if (buisnessModule.getBusinessDomainId()==3) { // EV
						if (siteDetails.getCircle() != null) {
							if (siteDetails.getCircle().getCircleManagerEv() != null) {
								emailId = siteDetails.getCircle().getCircleManagerEv().getEmailId();
							}
						}
					}
				}else {
					throw new com.pratikshat.exicom.exception.BadRequestException("Circle does not exist");
				}
			}else {
				throw new com.pratikshat.exicom.exception.BadRequestException("Site does not exist");	
			}

			if (caseType != null && caseStatus != null && caseReason != null && caseStageDetails != null) {
				
				List<Integer> statusList = new ArrayList<>();
				statusList.add(1);	// New
				statusList.add(2);	// Closed
				statusList.add(4);	// Rejected
				
				if (statusList.contains(caseStatus.getId())) {
					
					try {
						Query caseOwner = entityManager
								.createQuery("update CaseDetails cs set cs.ownerDetails=?1 where cs.caseId=?2");
						caseOwner.setParameter(1, new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
						caseOwner.setParameter(2, caseDetails.getCaseId());
						caseOwner.executeUpdate();
					}catch (Exception e) {
						throw new com.pratikshat.exicom.exception.BadRequestException(e.getMessage());
					}
					
					com.pratikshat.exicom.entity.ApplicationNotificationDetails notification = new com.pratikshat.exicom.entity.ApplicationNotificationDetails();
					notification.setNofificationDate(new java.util.Date());
					notification.setNotificationActionUrl("cases/" + _caseDetails.getCaseId());
					notification.setNotificationMessage("New Case has been registered With Id " + manualId);
					notification.setNotificationRead(false);
					notification.setUserEmailId(user.getUsername());
					entityManager.persist(notification);

					try {
							messagingTemplate.convertAndSend("/topic/" + user.getUsername(), notification);
						} catch (Exception e) {
							e.printStackTrace();
						}
//					new requirment of sms
				
					if(caseDetails.isiSmsSend() && caseDetails.getCaseStatusId()!=4) {
						System.out.println("true=="+caseDetails.isiSmsSend());
						try {
							
							String selectCase = "Select cd from ContactDetails cd where cd.emailId = ?1";
							ContactDetails cd = entityManager.createQuery(selectCase, ContactDetails.class)
									.setParameter(1, user.getUsername()).getSingleResult();
							String customerMobile= _caseDetails.getContactPerson().getMobile();
							String engMobile=_caseDetails.getSite().getServiceResource().getUser().getContact().getMobile();
						   String SCH=	_caseDetails.getSite().getCircle().getCircleManagerPower().getMobile();
						   System.out.println("SCH mobile=="+SCH);

							TemplateDetails templateDetails = null;
							
							try {
								Query templateQuery=entityManager.createQuery("select tmpd from TemplateDetails tmpd where tmpd.active=?1 and tmpd.templateType=?2 and tmpd.businessDomain=?3 and tmpd.eventName=?4",TemplateDetails.class);
								templateQuery.setParameter(1,true);
								templateQuery.setParameter(2,TemplateType.SMS);
								templateQuery.setParameter(3,new BusinessDomain(2));
								templateQuery.setParameter(4,"assign_case");

								templateDetails = (TemplateDetails) templateQuery.getSingleResult();
							} catch (NoResultException nex) {
								nex.printStackTrace();
							} catch (NonUniqueResultException e) {
								e.printStackTrace();
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
								smsMessage = smsMessage.replaceFirst("<var>", manualId);
								smsMessage = smsMessage.replaceFirst("<var>",
										feedbackurl+"/#/cases/" + _caseDetails.getCaseId());
							}
							
							System.out.println( "the sms line =561"+smsMessage);

							if (smsMessage != null && !smsMessage.equalsIgnoreCase("")) {
								SmsNotifier sNotifier = new SmsNotifier(entityManager,
										SCH, smsMessage, user.getUsername(),
										templateDetails.getTemplaeCode());
								sNotifier.sendNotification();
//								eng sms
								SmsNotifier sNotifiereng = new SmsNotifier(entityManager,
										engMobile, smsMessage, user.getUsername(),
										templateDetails.getTemplaeCode());
								sNotifiereng.sendNotification();
								
//								contact mobile
								
								SmsNotifier sNotifiercon = new SmsNotifier(entityManager,
										customerMobile, smsMessage, user.getUsername(),
										templateDetails.getTemplaeCode());
								sNotifiercon.sendNotification();
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

				}else {
					if (emailId != null) {
						Query caseOwner = entityManager
								.createQuery("update CaseDetails cs set cs.ownerDetails=?1 where cs.caseId=?2");
						caseOwner.setParameter(1, new com.pratikshat.exicom.entity.EmployeeDetails(emailId));
						caseOwner.setParameter(2, caseDetails.getCaseId());
						caseOwner.executeUpdate();
					} else {
						throw new com.pratikshat.exicom.exception.BadRequestException("Circle Manager Does not exist");
					}
					
					
					com.pratikshat.exicom.entity.ApplicationNotificationDetails notification = new com.pratikshat.exicom.entity.ApplicationNotificationDetails();
					notification.setNofificationDate(new java.util.Date());
					notification.setNotificationActionUrl("cases/" + _caseDetails.getCaseId());
					notification.setNotificationMessage("New Case has been registered With Id " + manualId);
					notification.setNotificationRead(false);
					notification.setUserEmailId(emailId);
					entityManager.persist(notification);

					try {
						messagingTemplate.convertAndSend("/topic/" + emailId, notification);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					
                      if(caseDetails.isiSmsSend() && caseDetails.getCaseStatusId()!=4) {
                    	  
      					try {
    						String selectCase = "Select cd from ContactDetails cd where cd.emailId = ?1";
    						ContactDetails cd = entityManager.createQuery(selectCase, ContactDetails.class)
    								.setParameter(1, emailId).getSingleResult();
//    						shagufta added for enf and customer mobile number for send SMS as new CR
    						String customerMobile= _caseDetails.getContactPerson().getMobile();
    						String engMobile=_caseDetails.getSite().getServiceResource().getUser().getContact().getMobile();
    						  String SCH=	_caseDetails.getSite().getCircle().getCircleManagerPower().getMobile();
    						
    						

    						TemplateDetails templateDetails = null;
    						
    						try {
    							Query templateQuery=entityManager.createQuery("select tmpd from TemplateDetails tmpd where tmpd.active=?1 and tmpd.templateType=?2 and tmpd.businessDomain=?3 and tmpd.eventName=?4",TemplateDetails.class);
    							templateQuery.setParameter(1,true);
    							templateQuery.setParameter(2,TemplateType.SMS);
    							templateQuery.setParameter(3,new BusinessDomain(2));
    							templateQuery.setParameter(4,"assign_case");

    							templateDetails = (TemplateDetails) templateQuery.getSingleResult();
    						} catch (NoResultException nex) {
    							nex.printStackTrace();
    						} catch (NonUniqueResultException e) {
    							e.printStackTrace();
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
    							smsMessage = smsMessage.replaceFirst("<var>", manualId);
    							smsMessage = smsMessage.replaceFirst("<var>",
    									feedbackurl+"/#/cases/" + _caseDetails.getCaseId());
    						}
    						if (smsMessage != null && !smsMessage.equalsIgnoreCase("")) {
    							SmsNotifier sNotifier = new SmsNotifier(entityManager,
    									SCH, smsMessage, user.getUsername(),
    									templateDetails.getTemplaeCode());
    							sNotifier.sendNotification();
//    							eng mobile
    							SmsNotifier sNotifiereng = new SmsNotifier(entityManager,
    									engMobile, smsMessage, user.getUsername(),
    									templateDetails.getTemplaeCode());
    							sNotifiereng.sendNotification();
//    							customer
    							SmsNotifier sNotifiercust = new SmsNotifier(entityManager,
    									customerMobile, smsMessage, user.getUsername(),
    									templateDetails.getTemplaeCode());
    							sNotifiercust.sendNotification();
    							
    						}

    					} catch (Exception e) {
    						e.printStackTrace();
    					}
                      }
					
				}
				
				
				// Service Appointment Generation block
				if(caseStatus!=null && caseStatus.getId()==3){	// In-Process

					ServiceAppointment _serviceAppointment = new ServiceAppointmentUtil().createServiceAppointment(caseDetails.getCaseId(), user.getUsername(), entityManager);
					caseDetails.setServiceResourceId(_serviceAppointment.getServiceResource().getServiceResourceId());
					
					
					workflow.executeWorkflow(entityManager, _serviceAppointment, "INSERT", "PRE", user);
					entityManager.persist(_serviceAppointment);
					workflow.executeWorkflow(entityManager, _serviceAppointment, "INSERT", "POST", user);
					
					
					
					Query updateServiceAppointmentManualId = entityManager.createQuery(
							"update ServiceAppointment sa set sa.appointmentNumber=?1 where sa.serviceAppointmentId=?2");
					SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
//					String serviceAppointmentManualId = "SA-" + df.format(new java.util.Date()) + "-"
//							+ com.pratikshat.exicom.config.MiscellaneousUtils
//									.fillZero(String.valueOf(_serviceAppointment.getServiceAppointmentId()), 5);
					
					String serviceAppointmentManualId = "SA-"+ com.pratikshat.exicom.config.MiscellaneousUtils.fillZero(String.valueOf(_serviceAppointment.getServiceAppointmentId()), 7);
					updateServiceAppointmentManualId.setParameter(1, serviceAppointmentManualId);
					updateServiceAppointmentManualId.setParameter(2, _serviceAppointment.getServiceAppointmentId());
					updateServiceAppointmentManualId.executeUpdate();
					// notification to service appointment Eng
					try {
						ApplicationNotificationDetails notification = new ApplicationNotificationDetails();
						notification.setNofificationDate(new java.util.Date());
						notification.setNotificationActionUrl(
								"/serviceappointment/" + _serviceAppointment.getServiceAppointmentId());
						notification.setNotificationMessage(
								String.format("Service Appointment %s assigned for your action",
										serviceAppointmentManualId));
						notification.setNotificationRead(false);
						notification
								.setUserEmailId(_serviceAppointment.getServiceResource().getUser().getEmailId());
						entityManager.persist(notification);
						messagingTemplate.convertAndSend("/topci/"+_serviceAppointment.getServiceResource().getUser().getEmailId(),notification);
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
					
					
					if(buisnessModule.getBusinessDomainId()==1) { // Battery
						
						BatteryFSR batteryFSR = new BatteryFSR();
						batteryFSR.setServiceAppointment(_serviceAppointment);
						entityManager.persist(batteryFSR);
						
						Query updateFSRManualId=entityManager.createQuery("update BatteryFSR fsr set fsr.manualId=?1 where fsr.id=?2");
						//String fsrManualId= "FSR-CBMS-LI-"+df.format(new java.util.Date())+"-"+com.pratikshat.exicom.config.MiscellaneousUtils.fillZero(String.valueOf(batteryFSR.getId()),5);
						String fsrManualId= "FSR-CBMS-LI-"+com.pratikshat.exicom.config.MiscellaneousUtils.fillZero(String.valueOf(batteryFSR.getId()),7);
						updateFSRManualId.setParameter(1,fsrManualId);
						updateFSRManualId.setParameter(2, batteryFSR.getId());
						updateFSRManualId.executeUpdate();
						
						//search index entry
						try {
							com.pratikshat.exicom.entity.GlobalSearchIndex _search=new com.pratikshat.exicom.entity.GlobalSearchIndex();
							_search.setKeyName("manualId");
							_search.setObjectDetailsUrl("Batteryfsr/"+batteryFSR.getId());
							_search.setObjectIdName("id");
							_search.setObjectIdValue(""+batteryFSR.getId());
							_search.setObjectName("BatteryFSR");
							_search.setSearchString(fsrManualId);
							entityManager.persist(_search);
							
						}catch(Exception e) {}
					}
						
						
						
					}
			} else {
				throw new Exception("Case Type Mismatch");
			}

			entityManager.flush();
			entityManager.clear();

		} else {
			throw new Exception("No data found to save details");
		}

		return caseDetails;
	}

	@Transactional(isolation = Isolation.READ_UNCOMMITTED, rollbackFor = { Exception.class,BadRequestException.class,UnauthoriseException.class,
			RuntimeException.class }, propagation = Propagation.REQUIRES_NEW)
	@Override
	public CaseRequest updateCase(CaseRequest caseDetails, LoginUser user) throws Exception {
		CaseDetails _caseDetails = null;
		try {
			Query selectCaseDetailsQuery = entityManager.createQuery(
					"select ca from CaseDetails ca left join ca.status cs left join ca.assets ast left join ast.account acn left join ca.site st left join ca.ownerDetails onr left join ca.createdBy createdby left join ca.modifiedBy modified left join ca.engineer eng left join eng.contact econ left join ca.serviceCenter scr where ca.caseId=?1",
					CaseDetails.class);
			selectCaseDetailsQuery.setParameter(1, caseDetails.getCaseId());
			_caseDetails = (CaseDetails) selectCaseDetailsQuery.getSingleResult();
		} catch (NoResultException ex) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Case does not exist");
		}

		if (_caseDetails != null) {
			
			if(!_caseDetails.getOwnerDetails().getEmailId().equalsIgnoreCase(user.getUsername())) {
				Map<String,String> group= dbProfile.getAccountAndRoleGroup(user);
				if(group.get("roleGroup").equalsIgnoreCase("CUSTOMERCARE")||group.get("roleGroup").equalsIgnoreCase("ADMIN")) {
					
				}
				else {
					throw new com.pratikshat.exicom.exception.BadRequestException("Your are not authorised to edit this record");
				}
				
			}
			
			List<Integer> list = new ArrayList<>();
			list.add(2); // Closed
			list.add(4); // Rejected
			
			if(list.contains(_caseDetails.getStatus().getId())) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Your can't edit this record");
			}
			
			if (caseDetails.getAssetsSerialNumber() != null) {
				Assets assets = null;
				try {
					Query assetsQuery = entityManager.createQuery(
							"select ast from Assets ast left join ast.site left join ast.status sts where ast.serialNumber=?1",
							com.pratikshat.exicom.entity.Assets.class);
					assetsQuery.setParameter(1, caseDetails.getAssetsSerialNumber());
					assets = (Assets) assetsQuery.getSingleResult();
					
					if(assets!=null) {
						_caseDetails.setAssets(assets);
					}else {
						throw new com.pratikshat.exicom.exception.BadRequestException("Asset does not exist");
					}
				}catch(NoResultException ex) {
					throw new com.pratikshat.exicom.exception.BadRequestException("Asset does not exist");
				}
				
				if(assets!=null && assets.getStatus()!=null) {
					if(assets.getStatus().getAssetsStatusId()==1) {	// Dismantle
						throw new com.pratikshat.exicom.exception.BadRequestException("Asset is Dismantle");
					}
				}
			}else {
				List<Long> caseReasonList = new ArrayList<>();
				caseReasonList.add(12L); // Exceptional Case
				caseReasonList.add(18L); // Installation
				caseReasonList.add(2L); // Exceptional Case
				caseReasonList.add(8L); // Installation
				caseReasonList.add(43L); // Warehouse Health check-ups
				caseReasonList.add(45L); // Site Survey
				caseReasonList.add(46L); // Battery Bank Charging
				caseReasonList.add(40L); // Acceptance Testing
				caseReasonList.add(42L); // SMPS Kitting Warehouse
				caseReasonList.add(52L); // Warehouse Health check-ups
				if(caseReasonList.contains(caseDetails.getCaseReasonId())) {
					if(caseDetails.getCaseReasonId()==2L || caseDetails.getCaseReasonId()==12L) {
						if(caseDetails.getMissingAssets()==null) {
							throw new com.pratikshat.exicom.exception.BadRequestException("Please fill missing asset");
						}
					}
				}else {
					throw new com.pratikshat.exicom.exception.BadRequestException("Please select Asset");
					
				}	
			}
			SiteDetails site = null;
			if (caseDetails.getSiteId() != 0) {
				try {
					Query siteQuery = entityManager.createQuery(
							"select sti from SiteDetails sti left join sti.account act where sti.siteId=?1",
							com.pratikshat.exicom.entity.SiteDetails.class);
					siteQuery.setParameter(1, caseDetails.getSiteId());
					site = (SiteDetails) siteQuery.getSingleResult();
					
					if(site!=null) {
						_caseDetails.setSite(site);
					}else {
						throw new com.pratikshat.exicom.exception.BadRequestException("Site does not exist");
					}
				}catch(NoResultException ex) {
					throw new com.pratikshat.exicom.exception.BadRequestException("Site does not exist");
				}
			} else {
				throw new com.pratikshat.exicom.exception.BadRequestException("Site does not exist");
			}
			

			if (caseDetails.getAssetsSerialNumber() != null) {
				try {
					Query assetsQuery = entityManager.createQuery(
							"select ast from Assets ast left join ast.site  where ast.serialNumber=?1",
							com.pratikshat.exicom.entity.Assets.class);
					assetsQuery.setParameter(1, caseDetails.getAssetsSerialNumber());
					Assets assets = (Assets) assetsQuery.getSingleResult();
					if (assets.getSite() != null) {
						if (assets.getSite().getSiteId() != caseDetails.getSiteId()) {
							throw new com.pratikshat.exicom.exception.BadRequestException(
									"Given site mismatch with the assets installed on the site");
						}
					} else {
						throw new com.pratikshat.exicom.exception.BadRequestException("Site does not exist");
					}
				} catch (NoResultException ex) {
					throw new com.pratikshat.exicom.exception.BadRequestException("Asset does not exist");
				}
			}
			
			try {
				Query contactQuery = entityManager.createQuery(
						"select cnt from ContactDetails cnt join cnt.account act  where cnt.contactId=?1 and act.sapCustomerNumber=?2",
						com.pratikshat.exicom.entity.ContactDetails.class);
				contactQuery.setParameter(1, caseDetails.getContactPersonId());
				contactQuery.setParameter(2, site.getAccount().getSapCustomerNumber());
				com.pratikshat.exicom.entity.ContactDetails contact = (com.pratikshat.exicom.entity.ContactDetails) contactQuery.getSingleResult();
				
				if(contact!=null) {
					_caseDetails.setContactPerson(contact);
				}else {
					throw new com.pratikshat.exicom.exception.BadRequestException("Contact does not exist");
				}
			}catch(NoResultException ex) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Contact does not exist");
			}
			
			try {
				Query caseReasonQuery = entityManager.createQuery("select crd.reasonId from CaseReasonDetails crd join crd.caseType cty join crd.vertical vrt where cty.caseTypeId=?1 and vrt.businessDomainId=?2",Long.class);
				caseReasonQuery.setParameter(1, caseDetails.getCaseTypeId());
				caseReasonQuery.setParameter(2, caseDetails.getCaseRecordTypeId());
				List<Long> reasonList = (List<Long>)caseReasonQuery.getResultList();
				
				if(!reasonList.contains(caseDetails.getCaseReasonId())) {
					throw new com.pratikshat.exicom.exception.BadRequestException("Wrong case reason");
				}
			}catch(NoResultException ex) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Wrong case reason");
			}
			
			try {
				Query caseStageQuery = entityManager.createQuery("select stg.id from CaseStageDetails stg join stg.caseStatus sts join stg.division vrt where sts.id=?1 and vrt.businessDomainId=?2",Integer.class);
				caseStageQuery.setParameter(1, caseDetails.getCaseStatusId());
				caseStageQuery.setParameter(2, caseDetails.getCaseRecordTypeId());
				List<Integer> reasonList = (List<Integer>)caseStageQuery.getResultList();
				
				if(!reasonList.contains(caseDetails.getCaseStageId())) {
					throw new com.pratikshat.exicom.exception.BadRequestException("Wrong case stage");
				}
			}catch(NoResultException ex) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Wrong case stage");
			}
			
			
			if (caseDetails.getServiceResourceId() != 0) {
				ServiceRecource serviceRecource = null;
				try {
					Query caseStageQuery = entityManager.createQuery("select sr from ServiceRecource sr join sr.contact cnt join cnt.legalEntity le join cnt.account act join sr.location lc where le.legalEntityId=?1 and sr.serviceResourceId=?2",ServiceRecource.class);
					caseStageQuery.setParameter(1, caseDetails.getServiceCenterId());
					caseStageQuery.setParameter(2, caseDetails.getServiceResourceId());
					serviceRecource = (ServiceRecource)caseStageQuery.getSingleResult();
					
				}catch(NoResultException ex) {
					throw new com.pratikshat.exicom.exception.BadRequestException("Service Resource is wrong");
				}
				
				if(serviceRecource==null) {
					throw new com.pratikshat.exicom.exception.BadRequestException("Service Resource is wrong");
				}
				
				_caseDetails.setEngineer(new com.pratikshat.exicom.entity.ServiceRecource(caseDetails.getServiceResourceId()));	
			}
			
			
			_caseDetails.setCaseReason(new com.pratikshat.exicom.entity.CaseReasonDetails(caseDetails.getCaseReasonId()));
			_caseDetails.setCaseType(new com.pratikshat.exicom.entity.CaseTypes(caseDetails.getCaseTypeId()));
			
			// Installation for Battery				// Installation for DCT
			if(caseDetails.getCaseTypeId()==2 || caseDetails.getCaseTypeId()==6) {
							
				if(caseDetails.getPoDate()!=null && caseDetails.getPoNumber()!=null) {
					_caseDetails.setPODate(caseDetails.getPoDate());
					_caseDetails.setPONumber(caseDetails.getPoNumber());	
				}else {
					throw new com.pratikshat.exicom.exception.BadRequestException("PO details missing");
				}
			}
			
			_caseDetails.setModifiedBy(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
			_caseDetails.setModifiedDate(new java.util.Date());
			_caseDetails.setNoOfsmr(caseDetails.getNoOfSmr());

			_caseDetails.setOrigin(new com.pratikshat.exicom.entity.CaseOriginDetails(caseDetails.getCaseOriginId()));
			//_caseDetails.setOwnerDetails(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
			_caseDetails.setPriority(new com.pratikshat.exicom.entity.CasePriorityDetails(caseDetails.getCasePriorityId()));
			
			_caseDetails.setRemarks(caseDetails.getRemarks());
			_caseDetails.setStage(new com.pratikshat.exicom.entity.CaseStageDetails(caseDetails.getCaseStageId()));
			_caseDetails.setStatus(new com.pratikshat.exicom.entity.CaseStatusDetails(caseDetails.getCaseStatusId()));
			_caseDetails.setSynked(false);
			_caseDetails.setMissingAssets(caseDetails.getMissingAssets());
			if (caseDetails.getServiceCenterId() != 0) {
				_caseDetails.setServiceCenter(new com.pratikshat.exicom.entity.LegalEntity(caseDetails.getServiceCenterId()));
			}

			_caseDetails.setOtherProblemReported(caseDetails.getOtherProblemReported());
			_caseDetails.setAssignByAssignmentRule(caseDetails.isAssignByAssignmentRule());
			_caseDetails.setInputFromEng(caseDetails.getInputFromEng());
			_caseDetails.setInternalRemark(caseDetails.getInternalRemark());
			_caseDetails.setRJILTT01No(caseDetails.getRJILTT01No());
			if(caseDetails.getPendingResponsibility()!=0) {
				_caseDetails.setPendingResponsibility(new PendingResponsibility(caseDetails.getPendingResponsibility()));
			}
			
			if(caseDetails.getCaseStatusId()==2) {	// Closed
				_caseDetails.setCloseDate(new Date());
				_caseDetails.setClosedBy(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
			}
			
			workflow.executeWorkflow(entityManager, _caseDetails, "UPDATE", "PRE", user);
			entityManager.persist(_caseDetails);
			workflow.executeWorkflow(entityManager, _caseDetails, "UPDATE", "POST", user);

			if (caseDetails.getCaseProblemReported() != null) {
				
				Query deleteProblemId = entityManager.createQuery("delete CaseProblemReported pr where pr.caseDetails.caseId=?1");
				deleteProblemId.setParameter(1, _caseDetails.getCaseId());
				deleteProblemId.executeUpdate();
				
				for (com.pratikshat.exicom.entity.CaseProblemReported pr : caseDetails.getCaseProblemReported()) {
					CaseProblemReported caseProblemReported= new CaseProblemReported();
					caseProblemReported.setProblemName(pr.getProblemName());
					caseProblemReported.setCaseDetails(new CaseDetails(_caseDetails.getCaseId()));
					entityManager.persist(caseProblemReported);
					/*
					 * Query updateProblemId = entityManager.createQuery(
					 * "update CaseProblemReported pr set pr.caseDetails.caseId=?1 where pr.probleReportedId=?2"
					 * ); updateProblemId.setParameter(1, _caseDetails.getCaseId());
					 * updateProblemId.setParameter(2, pr.getProbleReportedId());
					 * updateProblemId.executeUpdate();
					 */
				}
			}

			CaseTypes caseType = null;
			try {
				Query caseTypeQuery = entityManager.createQuery("from CaseTypes ct where ct.caseTypeId=?1",
						CaseTypes.class);
				caseTypeQuery.setParameter(1, caseDetails.getCaseTypeId());
				caseType = (CaseTypes) caseTypeQuery.getSingleResult();
			} catch (NoResultException ex) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Case Type does not exist");
			}

			CaseStatusDetails caseStatus = null;
			try {
				Query CaseStatusDetailsQuery = entityManager.createQuery("from CaseStatusDetails cs where cs.id=?1",
						CaseStatusDetails.class);
				CaseStatusDetailsQuery.setParameter(1, caseDetails.getCaseStatusId());
				caseStatus = (CaseStatusDetails) CaseStatusDetailsQuery.getSingleResult();
			} catch (NoResultException ex) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Case Status does not exist");
			}

			CaseStageDetails caseStageDetails = null;
			try {
				Query caseStageDetailsQuery = entityManager.createQuery(
						"select csd from CaseStageDetails csd where csd.id=?1",
						CaseStageDetails.class);
				caseStageDetailsQuery.setParameter(1, caseDetails.getCaseStageId());
				caseStageDetails = (CaseStageDetails) caseStageDetailsQuery.getSingleResult();
			} catch (NoResultException ex) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Case Stage does not exist");
			}


			CaseReasonDetails caseReason = null;
			try {
				Query CaseReasonDetailsQuery = entityManager
						.createQuery("from CaseReasonDetails cr where cr.reasonId=?1", CaseReasonDetails.class);
				CaseReasonDetailsQuery.setParameter(1, caseDetails.getCaseReasonId());
				caseReason = (CaseReasonDetails) CaseReasonDetailsQuery.getSingleResult();
			} catch (NoResultException ex) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Case Reason does not exist");
			}

				 
				Query siteDetailsQuery = entityManager.createQuery(
						"select st from SiteDetails st  left join st.circle cr left join cr.circleManagerBattry cb left join cr.circleManagerPower cp left join cr.circleManagerEv ev where st.siteId=?1",
						com.pratikshat.exicom.entity.SiteDetails.class);

				Query buisnessModuleQuery = entityManager
						.createQuery("select bs from BusinessDomain bs where bs.businessDomainId=?1", BusinessDomain.class);
				buisnessModuleQuery.setParameter(1, caseDetails.getCaseRecordTypeId());
				BusinessDomain buisnessModule = (BusinessDomain) buisnessModuleQuery.getSingleResult();
				com.pratikshat.exicom.entity.SiteDetails siteDetails = null;
				try {
					siteDetailsQuery.setParameter(1, caseDetails.getSiteId());
					siteDetails = (com.pratikshat.exicom.entity.SiteDetails) siteDetailsQuery.getSingleResult();
				} catch (NoResultException ex) {

				}
				
				String emailId = null;
				if (siteDetails != null) {
					if (siteDetails.getCircle() != null) {
						if (buisnessModule.getBusinessDomainId()==1) { //Battery
							if (siteDetails.getCircle() != null) {
								if (siteDetails.getCircle().getCircleManagerBattry() != null) {
									emailId = siteDetails.getCircle().getCircleManagerBattry().getEmailId();
								}
							}
						}

						if (buisnessModule.getBusinessDomainId()==2) { // DCT
							if (siteDetails.getCircle() != null) {
								if (siteDetails.getCircle().getCircleManagerPower() != null) {
									emailId = siteDetails.getCircle().getCircleManagerPower().getEmailId();
								}
							}
						}
						if (buisnessModule.getBusinessDomainId()==3) { // EV
							if (siteDetails.getCircle() != null) {
								if (siteDetails.getCircle().getCircleManagerEv() != null) {
									emailId = siteDetails.getCircle().getCircleManagerEv().getEmailId();
								}
							}
						}
					}else {
						throw new com.pratikshat.exicom.exception.BadRequestException("Circle does not exist");
					}
				}else {
					throw new com.pratikshat.exicom.exception.BadRequestException("Site does not exist");	
				}

				if (caseType != null && caseStatus != null && caseReason != null && caseStageDetails != null) {
					
					List<Integer> statusList = new ArrayList<>();
					statusList.add(1);	// New
					statusList.add(2);	// Closed
					statusList.add(4);	// Rejected
					
					if (statusList.contains(caseStatus.getId())) {
						
						try {
							Query caseOwner = entityManager
									.createQuery("update CaseDetails cs set cs.ownerDetails=?1 where cs.caseId=?2");
							caseOwner.setParameter(1, new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
							caseOwner.setParameter(2, caseDetails.getCaseId());
							caseOwner.executeUpdate();
						}catch (Exception e) {
							throw new com.pratikshat.exicom.exception.BadRequestException(e.getMessage());
						}
						
						com.pratikshat.exicom.entity.ApplicationNotificationDetails notification = new com.pratikshat.exicom.entity.ApplicationNotificationDetails();
						notification.setNofificationDate(new java.util.Date());
						notification.setNotificationActionUrl("cases/" + _caseDetails.getCaseId());
						notification.setNotificationMessage("New Case has been registread With Id " + _caseDetails.getManualId());
						//notification.setNotificationMessage("Case " + _caseDetails.getManualId()+" Updated");
						notification.setNotificationRead(false);
						notification.setUserEmailId(user.getUsername());
						entityManager.persist(notification);

						try {
								messagingTemplate.convertAndSend("/topic/" + user.getUsername(), notification);
							} catch (Exception e) {
								e.printStackTrace();
							}
					}else {
						
						if (emailId != null) {
							Query caseOwner = entityManager
									.createQuery("update CaseDetails cs set cs.ownerDetails=?1 where cs.caseId=?2");
							caseOwner.setParameter(1, new com.pratikshat.exicom.entity.EmployeeDetails(emailId));
							caseOwner.setParameter(2, caseDetails.getCaseId());
							caseOwner.executeUpdate();
						} else {
							throw new com.pratikshat.exicom.exception.BadRequestException("Circle Manager Does not exist");
						}
						
						
						com.pratikshat.exicom.entity.ApplicationNotificationDetails notification = new com.pratikshat.exicom.entity.ApplicationNotificationDetails();
						notification.setNofificationDate(new java.util.Date());
						notification.setNotificationActionUrl("cases/" + _caseDetails.getCaseId());
						notification.setNotificationMessage("Case " + _caseDetails.getManualId()+" Updated");
						notification.setNotificationRead(false);
						notification.setUserEmailId(emailId);
						entityManager.persist(notification);

						try {
							messagingTemplate.convertAndSend("/topic/" + emailId, notification);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				
				//Service Appointment generation code
				if(caseStatus!=null && caseStatus.getId()==3) {	// In-process
					
					List<Long> saStatusList = new ArrayList<>();
					saStatusList.add(1L);	// Unassigned
					saStatusList.add(2L);	// Scheduled
					saStatusList.add(3L);	// Dispatched
					
					List<ServiceAppointment> serviceAppointmentList= null;
					try {
						serviceAppointmentList = entityManager.createQuery(
								"select sa from ServiceAppointment sa join sa.status st join sa.caseDetails cd where cd.caseId=:caseId and st.statusId in :statusList",
								com.pratikshat.exicom.entity.ServiceAppointment.class)
								.setParameter("caseId", caseDetails.getCaseId())
								.setParameter("statusList", saStatusList).getResultList();
					} catch (NoResultException ex) {
						
					}
					
					if(serviceAppointmentList==null || serviceAppointmentList.isEmpty()) {
						ServiceAppointment _serviceAppointment = new ServiceAppointmentUtil().createServiceAppointment(caseDetails.getCaseId(), user.getUsername(), entityManager);
						caseDetails.setServiceResourceId(_serviceAppointment.getServiceResource().getServiceResourceId());
						
						
						
						workflow.executeWorkflow(entityManager, _serviceAppointment, "INSERT", "PRE", user);
						entityManager.persist(_serviceAppointment);
						workflow.executeWorkflow(entityManager, _serviceAppointment, "INSERT", "POST", user);
						
						String serviceAppointmentManualId = "SA-"+com.pratikshat.exicom.config.MiscellaneousUtils
								.fillZero(String.valueOf(_serviceAppointment.getServiceAppointmentId()), 7);
						
						_serviceAppointment.setAppointmentNumber(serviceAppointmentManualId);
						entityManager.persist(_serviceAppointment);
//						Query updateServiceAppointmentManualId = entityManager.createQuery(
//								"update ServiceAppointment sa set sa.appointmentNumber=?1 where sa.serviceAppointmentId=?2");
//						SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
////						String serviceAppointmentManualId = "SA-" + df.format(new java.util.Date()) + "-"
////								+ com.pratikshat.exicom.config.MiscellaneousUtils
////										.fillZero(String.valueOf(_serviceAppointment.getServiceAppointmentId()), 5);
//						
//						String serviceAppointmentManualId = "SA-"+com.pratikshat.exicom.config.MiscellaneousUtils
//										.fillZero(String.valueOf(_serviceAppointment.getServiceAppointmentId()), 7);
//						updateServiceAppointmentManualId.setParameter(1, serviceAppointmentManualId);
//						updateServiceAppointmentManualId.setParameter(2, _serviceAppointment.getServiceAppointmentId());
//						updateServiceAppointmentManualId.executeUpdate();
						// notification to service appointment Eng
						try {
							ApplicationNotificationDetails notification = new ApplicationNotificationDetails();
							notification.setNofificationDate(new java.util.Date());
							notification.setNotificationActionUrl(
									"/serviceappointment/" + _serviceAppointment.getServiceAppointmentId());
							notification.setNotificationMessage(
									String.format("Service Appointment %s assigned for your action",
											serviceAppointmentManualId));
							notification.setNotificationRead(false);
							notification
									.setUserEmailId(_serviceAppointment.getServiceResource().getUser().getEmailId());
							entityManager.persist(notification);
							messagingTemplate.convertAndSend("/topci/"+_serviceAppointment.getServiceResource().getUser().getEmailId(),notification);
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
						
						
						if(buisnessModule.getBusinessDomainId()==1) { // Battery
							
							BatteryFSR batteryFSR = new BatteryFSR();
							batteryFSR.setServiceAppointment(_serviceAppointment);
							entityManager.persist(batteryFSR);
							
							Query updateFSRManualId=entityManager.createQuery("update BatteryFSR fsr set fsr.manualId=?1 where fsr.id=?2");
							//String fsrManualId= "FSR-CBMS-LI-"+df.format(new java.util.Date())+"-"+com.pratikshat.exicom.config.MiscellaneousUtils.fillZero(String.valueOf(batteryFSR.getId()),5);
							
							String fsrManualId= "FSR-CBMS-LI-"+com.pratikshat.exicom.config.MiscellaneousUtils.fillZero(String.valueOf(batteryFSR.getId()),7);
							updateFSRManualId.setParameter(1,fsrManualId);
							updateFSRManualId.setParameter(2, batteryFSR.getId());
							updateFSRManualId.executeUpdate();
							
							//search index entry
							try {
								com.pratikshat.exicom.entity.GlobalSearchIndex _search=new com.pratikshat.exicom.entity.GlobalSearchIndex();
								_search.setKeyName("manualId");
								_search.setObjectDetailsUrl("Batteryfsr/"+batteryFSR.getId());
								_search.setObjectIdName("id");
								_search.setObjectIdValue(""+batteryFSR.getId());
								_search.setObjectName("BatteryFSR");
								_search.setSearchString(fsrManualId);
								entityManager.persist(_search);
								
							}catch(Exception e) {}
						}
						
//						sms send 
//						new requirment of sms
						if(_caseDetails.isiSmsSend() && _caseDetails.getStatus().getId()!=4) {
							System.out.println(caseDetails.isiSmsSend());
							try {
								String selectCase = "Select cd from ContactDetails cd where cd.emailId = ?1";
								ContactDetails cd = entityManager.createQuery(selectCase, ContactDetails.class)
										.setParameter(1, user.getUsername()).getSingleResult();
								String customerMobile= _caseDetails.getContactPerson().getMobile();
								String engMobile=_caseDetails.getEngineer().getUser().getContact().getMobile();
								
								

								TemplateDetails templateDetails = null;
								
								try {
									Query templateQuery=entityManager.createQuery("select tmpd from TemplateDetails tmpd where tmpd.active=?1 and tmpd.templateType=?2 and tmpd.businessDomain=?3 and tmpd.eventName=?4",TemplateDetails.class);
									templateQuery.setParameter(1,true);
									templateQuery.setParameter(2,TemplateType.SMS);
									templateQuery.setParameter(3,new BusinessDomain(2));
									templateQuery.setParameter(4,"assign_case");

									templateDetails = (TemplateDetails) templateQuery.getSingleResult();
								} catch (NoResultException nex) {
									nex.printStackTrace();
								} catch (NonUniqueResultException e) {
									e.printStackTrace();
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
									smsMessage = smsMessage.replaceFirst("<var>",
											feedbackurl+"/#/cases/" + _caseDetails.getCaseId());
								}

								if (smsMessage != null && !smsMessage.equalsIgnoreCase("")) {
									SmsNotifier sNotifier = new SmsNotifier(entityManager,
											cd.getMobile(), smsMessage, user.getUsername(),
											templateDetails.getTemplaeCode());
									sNotifier.sendNotification();
//									eng sms
									SmsNotifier sNotifiereng = new SmsNotifier(entityManager,
											engMobile, smsMessage, user.getUsername(),
											templateDetails.getTemplaeCode());
									sNotifiereng.sendNotification();
									
//									contact mobile
									
									SmsNotifier sNotifiercon = new SmsNotifier(entityManager,
											customerMobile, smsMessage, user.getUsername(),
											templateDetails.getTemplaeCode());
									sNotifiercon.sendNotification();
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				}		
			}
				
				List<ServiceAppointment> _serviceAppointment = new ServiceAppointmentUtil().updateServiceAppointment(caseDetails.getCaseId(), user.getUsername(), entityManager);
				if(_serviceAppointment!=null && !_serviceAppointment.isEmpty()) {
					for(int i=0; i<_serviceAppointment.size(); i++) {
						ServiceAppointment sa = _serviceAppointment.get(i);
						entityManager.persist(sa);
					}
				}
				
				entityManager.flush();
				entityManager.clear();
		} else {
			throw new Exception("No data found to save details");
		}
		return caseDetails;
	}
	
	
	public Oppertunity createOppertunity(CaseRequest caseRequest, CaseDetails _caseDetails, LoginUser user, EntityManager entityManager) {
		
		Oppertunity _oppertunity = new Oppertunity();
		try {
			Query assetQuery = entityManager.createQuery(
					"select ast from Assets ast left join ast.account ac where ast.serialNumber=?1",
					Assets.class);
			assetQuery.setParameter(1, caseRequest.getAssetsSerialNumber());
			Assets asset = (Assets) assetQuery.getSingleResult();
			_oppertunity.setAccounts(asset.getAccount());
		} catch (NoResultException ex) {

		}

		_oppertunity.setCaseDetails(new CaseDetails(_caseDetails.getCaseId()));
		_oppertunity.setCreateDate(new Date());
		_oppertunity.setCreatedBy(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
		_oppertunity.setForcastCategory(new OppertunityForecastCategory(2));
		_oppertunity
				.setModifiedBy(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
		_oppertunity.setModifiedDate(new Date());
		_oppertunity.setOppertunityStage(new OppertunityStage(1));
		_oppertunity.setOppertunityType(new OppertunityType(1));
		_oppertunity
				.setOwnerDetails(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
		_oppertunity.setSyncFlag(false);
		
		return _oppertunity;
	}
	
	
	
	
	
	private CaseStageDetails getCaseStage(List<CaseStageDetails> caseStageList, String stageName) {
		for(int i=0; i<caseStageList.size(); i++) {
			if(caseStageList.get(i).getCaseStageName().equalsIgnoreCase(stageName)) {
				return caseStageList.get(i);
			}
		}
		return null;
	}
	
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW,isolation=Isolation.READ_UNCOMMITTED,rollbackFor= {BadRequestException.class,UnauthoriseException.class,RuntimeException.class,Exception.class})
	public ChangeCaseTypeRequest changeCaseType(ChangeCaseTypeRequest req, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		
		CaseDetails _caseDetails = null;
		try {
			Query selectCaseDetailsQuery = entityManager.createQuery(
					"select ca from CaseDetails ca left join ca.status cs left join ca.caseType cty left join ca.caseReason crs where ca.manualId=?1",
					CaseDetails.class);
			selectCaseDetailsQuery.setParameter(1, req.getCaseManualId().trim());
			_caseDetails = (CaseDetails) selectCaseDetailsQuery.getSingleResult();
		} catch (NoResultException ex) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Case does not exist");
		} catch (NonUniqueResultException e) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Multiple Case exist");
		}catch(Exception ex) {
			throw new com.pratikshat.exicom.exception.BadRequestException(ex.getMessage());
		}
		
		
		List<Quote> quoteList = null;
		try {
			Query selectQuoteQuery = entityManager.createQuery(
					"select qt from Quote qt left join qt.quoteStatus qts left join qt.oppertunity op left join op.caseDetails cd where cd.manualId=?1",
					Quote.class);
			selectQuoteQuery.setParameter(1, req.getCaseManualId().trim());
			quoteList = (List<Quote>) selectQuoteQuery.getResultList();
		} catch (NoResultException ex) {
		} catch (NonUniqueResultException e) {
		}catch(Exception ex) {
			throw new com.pratikshat.exicom.exception.BadRequestException(ex.getMessage());
		}
		
		if(quoteList!=null && quoteList.size()>0) {
			
			List<Integer> quoteStatuslist = new ArrayList<>();
			quoteStatuslist.add(1);	// Draft
			quoteStatuslist.add(2);	// Work Order Received
			quoteStatuslist.add(4);	// Quote Sent
			quoteStatuslist.add(6);	// Submitted For Approval
			quoteStatuslist.add(7);	// Approved
			quoteStatuslist.add(8); // Invoiced
			quoteStatuslist.add(10); // Approved by Store
			
			for(int i=0;i<quoteList.size();i++) {
				Quote quote = quoteList.get(i);
				if(quoteStatuslist.contains(quote.getQuoteStatus().getId())) {
					throw new com.pratikshat.exicom.exception.BadRequestException("Quote "+quote.getQuoteName()+" is in the "+quote.getQuoteStatus().getName()+" status, Please reject the Quote");
				}
			}
		}
		
		
		if(_caseDetails!=null) {
			//if(_caseDetails.getStatus().getId()==2) { // Closed
				
//				List<Long> caseTypeList = new ArrayList<>();
//				caseTypeList.add(4L); // Without Warranty
//				caseTypeList.add(8L); // Without Warranty
				
				//if(caseTypeList.contains(_caseDetails.getCaseType().getCaseTypeId())) {
					
					CaseTypes caseType = null;
					try {
						Query caseTypeQuery = entityManager.createQuery("select ct from CaseTypes ct join ct.vertical vt where ct.caseTypesName=?1 and vt.businessDomainId=?2",CaseTypes.class);
						caseTypeQuery.setParameter(1, req.getCaseType().trim());
						caseTypeQuery.setParameter(2, _caseDetails.getBusinessDomain().getBusinessDomainId());
						caseType = (CaseTypes) caseTypeQuery.getSingleResult();
					} catch (NoResultException ex) {
						throw new com.pratikshat.exicom.exception.BadRequestException("Case Type does not exist");
					}

					CaseReasonDetails caseReason = null;
					try {
						Query CaseReasonDetailsQuery = entityManager.createQuery("select cr from CaseReasonDetails cr join cr.vertical vt join cr.caseType ct where cr.caseReasonDetailsName=?1 and vt.businessDomainId=?2 and ct.caseTypeId=?3", CaseReasonDetails.class);
						CaseReasonDetailsQuery.setParameter(1, req.getCaseReason().trim());
						CaseReasonDetailsQuery.setParameter(2, _caseDetails.getBusinessDomain().getBusinessDomainId());
						CaseReasonDetailsQuery.setParameter(3, caseType.getCaseTypeId());
						caseReason = (CaseReasonDetails) CaseReasonDetailsQuery.getSingleResult();
					} catch (NoResultException ex) {
						throw new com.pratikshat.exicom.exception.BadRequestException("Case Reason does not exist");
					}
					
					
					
					ChangeCaseTypeConditions conditions = null;
					try {
						Query conditionQuery = entityManager.createQuery("select con from ChangeCaseTypeConditions con where con.existingCaseType=?1 and con.existingCaseReason=?2 and con.newCaseType=?3 and con.newCaseReason=?4", ChangeCaseTypeConditions.class);
						conditionQuery.setParameter(1, _caseDetails.getCaseType().getCaseTypesName());
						conditionQuery.setParameter(2, _caseDetails.getCaseReason().getCaseReasonDetailsName());
						conditionQuery.setParameter(3, req.getCaseType().trim());
						conditionQuery.setParameter(4, req.getCaseReason().trim());
						conditions = (ChangeCaseTypeConditions) conditionQuery.getSingleResult();
					} catch (NoResultException ex) {
						throw new com.pratikshat.exicom.exception.BadRequestException("This operation is not allowed");
					}
					
					if(conditions==null) {
						throw new com.pratikshat.exicom.exception.BadRequestException("This operation is not allowed");
					}
					
					
					List<ServiceAppointment> serviceAppointmentList = null;
					try {
						Query selectSAQuery = entityManager.createQuery(
								"select sa from ServiceAppointment sa left join sa.caseDetails cd left join sa.modifiedBy mby where cd.caseId=?1",
								ServiceAppointment.class);
						selectSAQuery.setParameter(1, _caseDetails.getCaseId());
						serviceAppointmentList = (List<ServiceAppointment>) selectSAQuery.getResultList();
					} catch (NoResultException ex) {
						
					}catch(Exception ex) {
						ex.printStackTrace();
						throw new com.pratikshat.exicom.exception.BadRequestException(ex.getMessage());
					}
					
					ChangeCaseTypeLog log = new ChangeCaseTypeLog();
						
					Date poDate = null;
					if(caseType!=null && caseType.getCaseTypeId()==9) {	// Under AMC
						if(req.getPoNo()==null || req.getPoNo().equalsIgnoreCase("")) {
							throw new com.pratikshat.exicom.exception.BadRequestException("PO No. is blank");
						}
						
						if(req.getPoDate()!=null) {
							try {
								String StrPoDate = outputSdf.format(req.getPoDate());
								poDate = outputSdf.parse(StrPoDate);
							}catch(Exception ex){
								throw new com.pratikshat.exicom.exception.BadRequestException("PO Date format exception");
							}
						}else {
							throw new com.pratikshat.exicom.exception.BadRequestException("PO Date is null");	
						}
						
						log.setPoNo(req.getPoNo().trim());
						log.setPoDate(poDate);
						
						_caseDetails.setPONumber(req.getPoNo().trim());
						_caseDetails.setPODate(poDate);
					}
					
					
					log.setCaseDetails(_caseDetails);
					log.setPreviousCaseType(_caseDetails.getCaseType());
					log.setPreviousCaseReason(_caseDetails.getCaseReason());
					
					log.setCurrentCaseType(caseType);
					log.setCurrentCaseReason(caseReason);
					
					log.setModifiedBy(new EmployeeDetails(user.getUsername()));
					log.setModifiedDate(new Date());
					
					_caseDetails.setCaseType(caseType);
					_caseDetails.setCaseReason(caseReason);
					_caseDetails.setModifiedBy(new EmployeeDetails(user.getUsername()));
					_caseDetails.setModifiedDate(new Date());
					
					entityManager.persist(_caseDetails);
					
					entityManager.persist(log);
					
					if(serviceAppointmentList!=null) {
						for(int i=0;i<serviceAppointmentList.size();i++) {
							
							ServiceAppointment sa = serviceAppointmentList.get(i);
							
							sa.setCostCenter(caseReason.getCostCenter());
							sa.setModifiedBy(new EmployeeDetails(user.getUsername()));
							sa.setModifiedDate(new Date());
							
							entityManager.persist(sa);
						}	
					}
					return req;
					
//				}else {
//					throw new com.pratikshat.exicom.exception.BadRequestException("Case not of Without Warranty type");
//				}
//			}else {
//				throw new com.pratikshat.exicom.exception.BadRequestException("Case not closed");	
//			}
		}else {
			throw new com.pratikshat.exicom.exception.BadRequestException("Case does not exist");	
		}
	}

	@Override
	@Transactional
	public List<CaseResponse> getCaseSearchResult( CaseSearchRequest searchRequest,List<String> users) {
		
		String queryString ="select new com.pratikshat.exicom.model.response.CaseResponse(ca) from CaseDetails ca left join ca.assets ast left join ca.site st left join st.account acn left join st.primaryAccount pacn left join ca.ownerDetails onr left join ca.createdBy createdby left join ca.modifiedBy modified left join ca.engineer eng left join eng.contact econ left join econ.account engact left join ca.serviceCenter scr left join ca.prc prc ";
		if(users!=null) {
			queryString+=" where (ca.ownerDetails.emailId in (:userlist) or ca.engineer.user in (:userlist))";
		}
		else {
			queryString+=" where 1=1 ";
		}
		
		if(searchRequest.getCaseNumber()!=null&&!searchRequest.getCaseNumber().equalsIgnoreCase("")) {
			queryString+=" and ca.manualId like :casemanualid";
		}
		if(searchRequest.getSiteName()!=null&&!searchRequest.getSiteName().equalsIgnoreCase("")) {
				queryString+=" and st.manualSiteId like :manualSiteId";
		}
		if(searchRequest.getAssetsSerialNumber()!=null&&!searchRequest.getAssetsSerialNumber().equalsIgnoreCase("")) {
				queryString+=" and (ca.missingAssets like ':assets' or ast.serialNumber like :assets)";
		}
		if(searchRequest.getTypeName()!=null&&!searchRequest.getTypeName().equalsIgnoreCase("")) {
				queryString+=" and ca.caseType.caseTypesName like :type";
		}
		if(searchRequest.getReasonName()!=null&&!searchRequest.getReasonName().equalsIgnoreCase("")) {
			queryString+=" and ca.caseReason.caseReasonDetailsName like :reason";
		}
		if(searchRequest.getStatus()!=null&&!searchRequest.getStatus().equalsIgnoreCase("")) {
			queryString+=" and ca.status.caseStatusName like :status";
		}
		if(searchRequest.getStage()!=null&&!searchRequest.getStage().equalsIgnoreCase("")) {
			queryString+=" and ca.stage.caseStageName like :stage";
		}
																
		if(searchRequest.getEngName()!=null&&!searchRequest.getEngName().equalsIgnoreCase("")) {
			queryString+=" and eng.name like :eng";									
		}
		if(searchRequest.getAccount()!=null&&!searchRequest.getAccount().equalsIgnoreCase("")) {
			queryString+=" and st.account.sapCustomerNumber like :account";
		}
		if(searchRequest.getCustomer()!=null&&!searchRequest.getCustomer().equalsIgnoreCase("")) {
			queryString+=" and st.customer like :customer";
		}
		//DATE QUERY OPEN DATE
		
		if(searchRequest.getDateTimeOpenFrom()!=null&&searchRequest.getDateTimeOpenTo()!=null) {
			//queryString+=" and (date(ca.createDate) between date(:todateO) and date(:fromdateO))";
			queryString+=" and (function('date_format', ca.createDate, '%Y, %m, %d') between function('date_format', :fromdateO, '%Y, %m, %d') and function('date_format', :todateO, '%Y, %m, %d'))";
			
		}
		if(searchRequest.getDateTimeOpenFrom()!=null&&searchRequest.getDateTimeOpenTo()==null) {
			//queryString+=" and ca.createDate>:fromdateO";
			queryString+=" and function('date_format', ca.createDate, '%Y, %m, %d')>function('date_format', :fromdateO, '%Y, %m, %d')";
		}
		if(searchRequest.getDateTimeOpenFrom()==null&&searchRequest.getDateTimeOpenTo()!=null) {
			//queryString+=" and ca.createDate<:todateO";
			queryString+=" and function('date_format', ca.createDate, '%Y, %m, %d')>function('date_format', :todateO, '%Y, %m, %d')";
		}
		
		//DATE QUERY CLOSE DATE
		
		if(searchRequest.getDateTimeCloseFrom()!=null&&searchRequest.getDateTimeCloseTo()!=null) {
			//queryString+=" and date(ca.closeDate) between date(:todatec) and date(:fromdatec)";
			queryString+=" and (function('date_format', ca.closeDate, '%Y, %m, %d') between function('date_format', :fromdatec, '%Y, %m, %d') and function('date_format', :todatec, '%Y, %m, %d'))";
		}
		if(searchRequest.getDateTimeCloseFrom()!=null&&searchRequest.getDateTimeCloseTo()==null) {
			//queryString+=" and ca.closeDate>:fromdatec";
			queryString+=" and function('date_format', ca.closeDate, '%Y, %m, %d')>function('date_format', :fromdatec, '%Y, %m, %d')";
		}
		if(searchRequest.getDateTimeCloseFrom()==null&&searchRequest.getDateTimeCloseTo()!=null) {
			//queryString+=" and ca.closeDate<:todatec";
			queryString+=" and function('date_format', ca.closeDate, '%Y, %m, %d')>function('date_format', :todatec, '%Y, %m, %d')";
		}
		
		if(searchRequest.getPreRegistrationCaseNumber()!=null&&!searchRequest.getPreRegistrationCaseNumber().equalsIgnoreCase("")) {
			queryString+=" and prc.manualId like :manualId";
		}
		
		System.out.println(queryString);
		
		Query qry=entityManager.createQuery(queryString,CaseResponse.class);
		
		
		if(users!=null) {
			qry.setParameter("userlist", users);
			//queryString+=" where ca.ownerDetails.emailId in (:userlist)";
		}
		else {
			//queryString+=" where 1=1";
		}
		
		if(searchRequest.getCaseNumber()!=null&&!searchRequest.getCaseNumber().equalsIgnoreCase("")) {
			//queryString+=" and ca.manualId like :casemanualid";
			qry.setParameter("casemanualid","%"+ searchRequest.getCaseNumber()+"%");
		}
		if(searchRequest.getSiteName()!=null&&!searchRequest.getSiteName().equalsIgnoreCase("")) {
				//queryString+=" and st.siteName like :siteName";
			qry.setParameter("manualSiteId","%"+ searchRequest.getSiteName()+"%");
		}
		if(searchRequest.getAssetsSerialNumber()!=null&&!searchRequest.getAssetsSerialNumber().equalsIgnoreCase("")) {
			//	queryString+=" and (ca.missingAssets like ':assets' or ast.serialNumber like :assets)";
			qry.setParameter("assets", "%"+searchRequest.getAssetsSerialNumber()+"%");
		}
		if(searchRequest.getTypeName()!=null&&!searchRequest.getTypeName().equalsIgnoreCase("")) {
				//queryString+=" and ca.caseType.caseTypesName like :type";
			qry.setParameter("type","%"+ searchRequest.getTypeName()+"%");
		}
		if(searchRequest.getReasonName()!=null&&!searchRequest.getReasonName().equalsIgnoreCase("")) {
			//queryString+=" and ca.caseReason.caseReasonDetailsName like reason";
			qry.setParameter("reason","%"+ searchRequest.getReasonName()+"%");
		}
		if(searchRequest.getStatus()!=null&&!searchRequest.getStatus().equalsIgnoreCase("")) {
			//queryString+=" and ca.status.caseStatusName like :status";
			qry.setParameter("status","%"+ searchRequest.getStatus()+"%");
		}
		if(searchRequest.getStage()!=null&&!searchRequest.getStage().equalsIgnoreCase("")) {
			//queryString+=" and ca.stage.caseStageName like :stage";
			qry.setParameter("stage","%"+ searchRequest.getStage()+"%");
		}
																
		if(searchRequest.getEngName()!=null&&!searchRequest.getEngName().equalsIgnoreCase("")) {
			//queryString+=" and scr.name like :eng";		
			qry.setParameter("eng","%"+ searchRequest.getEngName()+"%");
		}
		if(searchRequest.getAccount()!=null&&!searchRequest.getAccount().equalsIgnoreCase("")) {
			qry.setParameter("account","%"+ searchRequest.getAccount()+"%");
		}
		if(searchRequest.getCustomer()!=null&&!searchRequest.getCustomer().equalsIgnoreCase("")) {
			//queryString+=" and st.customer like :customer";
			qry.setParameter("customer","%"+ searchRequest.getCustomer()+"%");
		}
		
		
		if(searchRequest.getDateTimeOpenFrom()!=null&&searchRequest.getDateTimeOpenTo()!=null) {
			//queryString+=" and (date(ca.createDate) between date(:todateO) and date(:fromdateO))";
			qry.setParameter("todateO", searchRequest.getDateTimeOpenTo());
			qry.setParameter("fromdateO", searchRequest.getDateTimeOpenFrom());
		}
		if(searchRequest.getDateTimeOpenFrom()!=null&&searchRequest.getDateTimeOpenTo()==null) {
			//queryString+=" and ca.createDate>:fromdateO";
			qry.setParameter("fromdateO", searchRequest.getDateTimeOpenFrom());
		}
		if(searchRequest.getDateTimeOpenFrom()==null&&searchRequest.getDateTimeOpenTo()!=null) {
			//queryString+=" and ca.createDate<:todateO";
			qry.setParameter("todateO", searchRequest.getDateTimeOpenTo());
		}
		
		//DATE QUERY CLOSE DATE
		
		if(searchRequest.getDateTimeCloseFrom()!=null&&searchRequest.getDateTimeCloseTo()!=null) {
			//queryString+=" and date(ca.closeDate) between date(:todatec) and date(:fromdatec)";
			qry.setParameter("todatec", searchRequest.getDateTimeCloseTo());
			qry.setParameter("fromdatec", searchRequest.getDateTimeCloseFrom());
		}
		if(searchRequest.getDateTimeCloseFrom()!=null&&searchRequest.getDateTimeCloseTo()==null) {
			//queryString+=" and ca.closeDate>:fromdatec";
			qry.setParameter("fromdatec", searchRequest.getDateTimeCloseFrom());
		}
		if(searchRequest.getDateTimeCloseFrom()==null&&searchRequest.getDateTimeCloseTo()!=null) {
			//queryString+=" and ca.closeDate<:todatec";
			qry.setParameter("todatec", searchRequest.getDateTimeCloseTo());
		}
		
		if(searchRequest.getPreRegistrationCaseNumber()!=null&&!searchRequest.getPreRegistrationCaseNumber().equalsIgnoreCase("")) {
			//queryString+=" and prc.manualId like :manualId";
			qry.setParameter("manualId", searchRequest.getPreRegistrationCaseNumber());
		}
		
		
		List<CaseResponse> csRes= qry.getResultList();
																										
		
		
		 return csRes;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW,isolation=Isolation.READ_UNCOMMITTED,rollbackFor= {BadRequestException.class,UnauthoriseException.class,RuntimeException.class,Exception.class})
	public RejectCaseRequest rejectCase(RejectCaseRequest rejectCaseRequest, LoginUser user)
			throws BadRequestException, UnauthoriseException {

		Map<String, String> group = dbProfile.getAccountAndRoleGroup(user);
		if(group.get("roleGroup").equalsIgnoreCase("ADMIN")) {
			CaseDetails _caseDetails = null;
			try {
				Query selectCaseDetailsQuery = entityManager.createQuery(
						"select ca from CaseDetails ca left join ca.status cs left join ca.caseType cty left join ca.caseReason crs where ca.manualId=?1",
						CaseDetails.class);
				selectCaseDetailsQuery.setParameter(1, rejectCaseRequest.getCaseManualId().trim());
				_caseDetails = (CaseDetails) selectCaseDetailsQuery.getSingleResult();
			} catch (NoResultException ex) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Case does not exist");
			} catch (NonUniqueResultException e) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Multiple Case exist");
			}catch(Exception ex) {
				throw new com.pratikshat.exicom.exception.BadRequestException(ex.getMessage());
			}
			
			if(_caseDetails==null) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Case does not exist");
			}
			
			
				//New															//In-Process
			if(_caseDetails.getStatus().getId()==1 || _caseDetails.getStatus().getId()==3) {
				List<ServiceAppointment> serviceAppointmentList = null;
				try {
					Query selectSAQuery = entityManager.createQuery(
							"select sa from ServiceAppointment sa left join sa.caseDetails cd left join sa.status st left join sa.modifiedBy mby where cd.caseId=?1",
							ServiceAppointment.class);
					selectSAQuery.setParameter(1, _caseDetails.getCaseId());
					serviceAppointmentList = (List<ServiceAppointment>) selectSAQuery.getResultList();
				} catch (NoResultException ex) {
					
				}catch(Exception ex) {
					ex.printStackTrace();
					throw new com.pratikshat.exicom.exception.BadRequestException(ex.getMessage());
				}
				boolean isComplete = false;
				if(serviceAppointmentList!=null) {
					for(int i=0;i<serviceAppointmentList.size();i++) {
						
						ServiceAppointment sa = serviceAppointmentList.get(i);
						if(sa.getStatus().getStatusId()==4) {	// Completed
							isComplete = true;
						}
						
					}	
				}
				
				if(!isComplete) {
					_caseDetails.setStatus(new CaseStatusDetails(4));	//Rejected
					_caseDetails.setStage(new CaseStageDetails(16));	// Rejected
					_caseDetails.setModifiedBy(new EmployeeDetails(user.getUsername()));
					_caseDetails.setModifiedDate(new Date());
					
					entityManager.persist(_caseDetails);
					
					if(serviceAppointmentList!=null) {
						for(int i=0;i<serviceAppointmentList.size();i++) {
							
							ServiceAppointment sa = serviceAppointmentList.get(i);
							
							sa.setStatus(new ServiceAppointmentStatus(8));	//Rejected
							sa.setModifiedBy(new EmployeeDetails(user.getUsername()));
							sa.setModifiedDate(new Date());
							
							entityManager.persist(sa);
							
						}	
					}
					
					CaseRejectLog log = new CaseRejectLog();
					log.setCaseManualId(rejectCaseRequest.getCaseManualId().trim());
					log.setRemark(rejectCaseRequest.getRemark().trim());
					log.setFile(rejectCaseRequest.getFile());
					
					log.setCreatedBy(new EmployeeDetails(user.getUsername()));
					log.setCreatedDate(new Date());
					
					entityManager.persist(log);
				}else {
					throw new com.pratikshat.exicom.exception.BadRequestException(
							"You can not reject this record");
				}
			}else if(_caseDetails.getStatus().getId()==2) {	//Closed
				
				List<Integer> list = new ArrayList<>();
				list.add(18);	// Document Awaited - Customer
				list.add(22);	//	Document in-Transit
				list.add(9);	//	To Be Invoiced
				
				if(list.contains(_caseDetails.getStage().getId())) {
					_caseDetails.setStage(new CaseStageDetails(23));	// Not To Be Invoiced
					_caseDetails.setModifiedBy(new EmployeeDetails(user.getUsername()));
					_caseDetails.setModifiedDate(new Date());
					
					entityManager.persist(_caseDetails);
					
					
					CaseRejectLog log = new CaseRejectLog();
					log.setCaseManualId(rejectCaseRequest.getCaseManualId().trim());
					log.setRemark(rejectCaseRequest.getRemark().trim());
					log.setFile(rejectCaseRequest.getFile());
					
					log.setCreatedBy(new EmployeeDetails(user.getUsername()));
					log.setCreatedDate(new Date());
					
					entityManager.persist(log);
				}else {
					throw new com.pratikshat.exicom.exception.BadRequestException(
							"You can not reject this record");
				}
			}else {
				throw new com.pratikshat.exicom.exception.BadRequestException(
						"You can not reject this record");	
			}
		}else if (group.get("roleGroup").equalsIgnoreCase("CUSTOMERCARE")) {
			CaseDetails _caseDetails = null;
			try {
				Query selectCaseDetailsQuery = entityManager.createQuery(
						"select ca from CaseDetails ca left join ca.status cs left join ca.caseType cty left join ca.caseReason crs where ca.manualId=?1",
						CaseDetails.class);
				selectCaseDetailsQuery.setParameter(1, rejectCaseRequest.getCaseManualId().trim());
				_caseDetails = (CaseDetails) selectCaseDetailsQuery.getSingleResult();
			} catch (NoResultException ex) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Case does not exist");
			} catch (NonUniqueResultException e) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Multiple Case exist");
			}catch(Exception ex) {
				throw new com.pratikshat.exicom.exception.BadRequestException(ex.getMessage());
			}
			
			if(_caseDetails==null) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Case does not exist");
			}
			
			
				//New															//In-Process
			if(_caseDetails.getStatus().getId()==1 || _caseDetails.getStatus().getId()==3) {
				List<ServiceAppointment> serviceAppointmentList = null;
				try {
					Query selectSAQuery = entityManager.createQuery(
							"select sa from ServiceAppointment sa left join sa.caseDetails cd left join sa.status st left join sa.modifiedBy mby where cd.caseId=?1",
							ServiceAppointment.class);
					selectSAQuery.setParameter(1, _caseDetails.getCaseId());
					serviceAppointmentList = (List<ServiceAppointment>) selectSAQuery.getResultList();
				} catch (NoResultException ex) {
					
				}catch(Exception ex) {
					ex.printStackTrace();
					throw new com.pratikshat.exicom.exception.BadRequestException(ex.getMessage());
				}
				boolean isComplete = false;
				if(serviceAppointmentList!=null) {
					for(int i=0;i<serviceAppointmentList.size();i++) {
						
						ServiceAppointment sa = serviceAppointmentList.get(i);
						if(sa.getStatus().getStatusId()==4) {	// Completed
							isComplete = true;
						}
						
					}	
				}
				
				if(!isComplete) {
					_caseDetails.setStatus(new CaseStatusDetails(4));	//Rejected
					_caseDetails.setStage(new CaseStageDetails(16));	// Rejected
					_caseDetails.setModifiedBy(new EmployeeDetails(user.getUsername()));
					_caseDetails.setModifiedDate(new Date());
					
					entityManager.persist(_caseDetails);
					
					if(serviceAppointmentList!=null) {
						for(int i=0;i<serviceAppointmentList.size();i++) {
							
							ServiceAppointment sa = serviceAppointmentList.get(i);
							
							sa.setStatus(new ServiceAppointmentStatus(8));	//Rejected
							sa.setModifiedBy(new EmployeeDetails(user.getUsername()));
							sa.setModifiedDate(new Date());
							
							entityManager.persist(sa);
							
						}	
					}
					
					CaseRejectLog log = new CaseRejectLog();
					log.setCaseManualId(rejectCaseRequest.getCaseManualId().trim());
					log.setRemark(rejectCaseRequest.getRemark().trim());
					log.setFile(rejectCaseRequest.getFile());
					
					log.setCreatedBy(new EmployeeDetails(user.getUsername()));
					log.setCreatedDate(new Date());
					
					entityManager.persist(log);
				}else {
					throw new com.pratikshat.exicom.exception.BadRequestException(
							"You can not reject this record");
				}
			}else {
				throw new com.pratikshat.exicom.exception.BadRequestException(
						"You can not reject this record");	
			}
		}else if (group.get("roleGroup").equalsIgnoreCase("COMMERCIAL")) {
			CaseDetails _caseDetails = null;
			try {
				Query selectCaseDetailsQuery = entityManager.createQuery(
						"select ca from CaseDetails ca left join ca.status cs left join ca.caseType cty left join ca.caseReason crs where ca.manualId=?1",
						CaseDetails.class);
				selectCaseDetailsQuery.setParameter(1, rejectCaseRequest.getCaseManualId().trim());
				_caseDetails = (CaseDetails) selectCaseDetailsQuery.getSingleResult();
			} catch (NoResultException ex) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Case does not exist");
			} catch (NonUniqueResultException e) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Multiple Case exist");
			}catch(Exception ex) {
				throw new com.pratikshat.exicom.exception.BadRequestException(ex.getMessage());
			}
			
			if(_caseDetails==null) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Case does not exist");
			}
			
			
			if(_caseDetails.getStatus().getId()==2) {	//Closed
				
				List<Integer> list = new ArrayList<>();
				list.add(18);	// Document Awaited - Customer
				list.add(22);	//	Document in-Transit
				list.add(9);	//	To Be Invoiced
				
				if(list.contains(_caseDetails.getStage().getId())) {
					_caseDetails.setStage(new CaseStageDetails(23));	// Not To Be Invoiced
					_caseDetails.setModifiedBy(new EmployeeDetails(user.getUsername()));
					_caseDetails.setModifiedDate(new Date());
					
					entityManager.persist(_caseDetails);
					
					
					CaseRejectLog log = new CaseRejectLog();
					log.setCaseManualId(rejectCaseRequest.getCaseManualId().trim());
					log.setRemark(rejectCaseRequest.getRemark().trim());
					log.setFile(rejectCaseRequest.getFile());
					
					log.setCreatedBy(new EmployeeDetails(user.getUsername()));
					log.setCreatedDate(new Date());
					
					entityManager.persist(log);
				}else {
					throw new com.pratikshat.exicom.exception.BadRequestException(
							"You can not reject this record");
				}
			}else {
				throw new com.pratikshat.exicom.exception.BadRequestException(
						"You can not reject this record");	
			}
		} else {
			throw new com.pratikshat.exicom.exception.BadRequestException(
					"Your are not authorised to edit this record");
		}

		return rejectCaseRequest;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW,isolation=Isolation.READ_UNCOMMITTED,rollbackFor= {BadRequestException.class,UnauthoriseException.class,RuntimeException.class,Exception.class})
	@Override
	public InvoiceUpdateRequest updateInvoice(InvoiceUpdateRequest req, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		
		Map<String, String> group = dbProfile.getAccountAndRoleGroup(user);
		
		if (group.get("roleGroup").equalsIgnoreCase("COMMERCIAL") || group.get("roleGroup").equalsIgnoreCase("Commercial Executive")) {
			CaseDetails _caseDetails = null;
			try {
				Query selectCaseDetailsQuery = entityManager.createQuery(
						"select ca from CaseDetails ca left join ca.status cs left join ca.caseType cty left join ca.caseReason crs left join ca.stage stg where ca.manualId=?1",
						CaseDetails.class);
				selectCaseDetailsQuery.setParameter(1, req.getCaseNumber().trim());
				_caseDetails = (CaseDetails) selectCaseDetailsQuery.getSingleResult();
			} catch (NoResultException ex) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Case does not exist");
			} catch (NonUniqueResultException e) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Multiple Case exist");
			}catch(Exception ex) {
				throw new com.pratikshat.exicom.exception.BadRequestException(ex.getMessage());
			}
			
			if(_caseDetails==null) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Case does not exist");
			}
			
			
			
			if(req.getPODate()!=null && req.getPONumber()!=null) {
				_caseDetails.setPODate(req.getPODate());
				_caseDetails.setPONumber(req.getPONumber().trim());
			}
			
			if(req.getInvoiceDate()!=null && req.getInvoiceNumber()!=null) {
				
				if(_caseDetails.getStage().getId()!=9) { // To be Invoiced
					throw new com.pratikshat.exicom.exception.BadRequestException("Case not in the to be invoiced stage");
				}
				
				_caseDetails.setInvoiceDate(req.getInvoiceDate());
				_caseDetails.setInvoiceNumber(req.getInvoiceNumber().trim());
				
				_caseDetails.setStage(new CaseStageDetails(10)); // Invoiced
			}
			
			if(req.getVendorInvoiceDate()!=null && req.getVendorInvoiceNumber()!=null) {
				_caseDetails.setVendorInvoiceDate(req.getVendorInvoiceDate());
				_caseDetails.setVendorInvoiceNumber(req.getVendorInvoiceNumber().trim());
			}
			
			_caseDetails.setModifiedBy(new EmployeeDetails(user.getUsername()));
			_caseDetails.setModifiedDate(new Date());
			
			entityManager.persist(_caseDetails);
		
		}else {
			throw new com.pratikshat.exicom.exception.BadRequestException(
					"Your are not authorised to edit this record");
		}
		return req;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW,isolation=Isolation.READ_UNCOMMITTED,rollbackFor= {BadRequestException.class,UnauthoriseException.class,RuntimeException.class,Exception.class})
	@Override
	public CaseProofOfDeliveryRequest updateProofOfDelivery(CaseProofOfDeliveryRequest request, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		
		if (request.getPodDate() != null && request.getPodNumber() != null && request.getCaseId() != 0) {
			
			CaseDetails caseDetails = null;
			try {
				Query selectCaseDetailsQuery = entityManager.createQuery(
						"select ca from CaseDetails ca left join ca.status cs left join ca.caseType cty left join ca.caseReason crs left join ca.stage stg where ca.caseId=?1",
						CaseDetails.class);
				selectCaseDetailsQuery.setParameter(1, request.getCaseId());
				caseDetails = (CaseDetails) selectCaseDetailsQuery.getSingleResult();
			} catch (NoResultException ex) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Case does not exist");
			} catch (NonUniqueResultException e) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Multiple Case exist");
			}catch(Exception ex) {
				throw new com.pratikshat.exicom.exception.BadRequestException(ex.getMessage());
			}

			if (caseDetails == null) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Case does not exist");
			}

				if (!caseDetails.getOwnerDetails().getEmailId().equalsIgnoreCase(user.getUsername())) {
					throw new com.pratikshat.exicom.exception.BadRequestException("You are not authorized");
				}

				if (caseDetails.getStatus().getId() != 2) { // Closed
					throw new com.pratikshat.exicom.exception.BadRequestException("Case is not closed");
				}
				
				
				List<Integer> list = new ArrayList<>();
				list.add(17); // Document Awaited - Customer
				list.add(18); // Document Awaited - Customer

				if (!list.contains(caseDetails.getStage().getId())) {
					throw new com.pratikshat.exicom.exception.BadRequestException("You can't update this record");
				}
				
				if (request.getPodDate() != null && request.getPodNumber() != null) {
					
					caseDetails.setPODDate(request.getPodDate());
					caseDetails.setPODNumber(request.getPodNumber());
					
					//caseDetails.setPodShippingExpectedDate(request.getPodShippingExpectedDate());
					caseDetails.setPodShippingMode(request.getPodShippingMode());
					//caseDetails.setPodShippingToName(request.getPodShippingToName());
					//caseDetails.setPodShippingTrackingNumber(request.getPodShippingTrackingNumber());

					if (caseDetails.getBusinessDomain().getBusinessDomainId() == 1) { // Battery
						caseDetails.setStage(new CaseStageDetails(21));
					} else if (caseDetails.getBusinessDomain().getBusinessDomainId() == 2) { // DCT
						caseDetails.setStage(new CaseStageDetails(22));
					} else if (caseDetails.getBusinessDomain().getBusinessDomainId() == 3) { // EV
						caseDetails.setStage(null);
					}

					caseDetails.setModifiedBy(new EmployeeDetails(user.getUsername()));
					caseDetails.setModifiedDate(new Date());

					entityManager.persist(caseDetails);

					
					return request;
					
				}else {
					throw new com.pratikshat.exicom.exception.BadRequestException("Please fill the PO date and PO Number");
				}
		} else {
			throw new com.pratikshat.exicom.exception.BadRequestException("Request parameter not filled");
		}
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW,isolation=Isolation.READ_UNCOMMITTED,rollbackFor= {BadRequestException.class,UnauthoriseException.class,RuntimeException.class,Exception.class})
	@Override
	public void documentReceived(long caseId, LoginUser user) throws BadRequestException, UnauthoriseException {

		CaseDetails caseDetails = null;
		try {
			Query selectCaseDetailsQuery = entityManager.createQuery(
					"select ca from CaseDetails ca left join ca.status cs left join ca.caseType cty left join ca.caseReason crs left join ca.stage stg where ca.caseId=?1",
					CaseDetails.class);
			selectCaseDetailsQuery.setParameter(1, caseId);
			caseDetails = (CaseDetails) selectCaseDetailsQuery.getSingleResult();
		} catch (NoResultException ex) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Case does not exist");
		} catch (NonUniqueResultException e) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Multiple Case exist");
		}catch(Exception ex) {
			throw new com.pratikshat.exicom.exception.BadRequestException(ex.getMessage());
		}

		if (caseDetails != null) {

			
			if (caseDetails.getStatus().getId() != 2) { // Closed
				throw new com.pratikshat.exicom.exception.BadRequestException("Case not closed");
			}
			
			List<Integer> stageList = new ArrayList<Integer>();
			stageList.add(21);	// Document in-Transit
			stageList.add(22);	// Document in-Transit
			
			if(!stageList.contains(caseDetails.getStage().getId())) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Case not in Document in-Transit stage");
			}

				if (caseDetails.getBusinessDomain().getBusinessDomainId() == 1) { // Battery
					caseDetails.setStage(new CaseStageDetails(3)); // To Be Invoiced
				} else if (caseDetails.getBusinessDomain().getBusinessDomainId() == 2) { // DCT
					caseDetails.setStage(new CaseStageDetails(9)); // To Be Invoiced
				} else if (caseDetails.getBusinessDomain().getBusinessDomainId() == 3) { // EV
					caseDetails.setStage(null);
				}

				caseDetails.setModifiedBy(new EmployeeDetails(user.getUsername()));
				caseDetails.setModifiedDate(new Date());

				entityManager.persist(caseDetails);
		} else {
			throw new com.pratikshat.exicom.exception.BadRequestException("Case does not exist");
		}
		
	}
}
