package com.pratikshat.exicom.repository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import com.pratikshat.exicom.entity.CaseStageDetails;
import com.pratikshat.exicom.entity.CaseStatusDetails;
import com.pratikshat.exicom.entity.CaseTypes;
import com.pratikshat.exicom.entity.ContactDetails;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.PendingResponsibility;
import com.pratikshat.exicom.entity.PrcProblemReported;
import com.pratikshat.exicom.entity.PreRegistrationCaseActionLog;
import com.pratikshat.exicom.entity.PreRegistrationCases;
import com.pratikshat.exicom.entity.ServiceAppointment;
import com.pratikshat.exicom.entity.ServiceRecource;
import com.pratikshat.exicom.entity.SiteDetails;
import com.pratikshat.exicom.entity.TemplateDetails;
import com.pratikshat.exicom.entity.TemplateType;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.CaseRequest;
import com.pratikshat.exicom.model.request.CaseSearchRequest;
import com.pratikshat.exicom.model.request.PrcRequest;
import com.pratikshat.exicom.model.response.CaseResponse;
import com.pratikshat.exicom.model.response.PrcResponse;
import com.pratikshat.exicom.utils.ServiceAppointmentUtil;
import com.pratikshat.exicom.utils.SmsNotifier;

@Repository
public class PreRegistrationCasesCustomeRepositoryImpl implements PreRegistrationCasesCustomeRepository {

	@PersistenceContext
	EntityManager entityManager;
	@Autowired
	private SimpMessagingTemplate messagingTemplate;
	@Autowired
	com.pratikshat.exicom.interceptor.WorkflowExecuter workflow;
	
	@Autowired
	private UserProfileRepository dbProfile;
	
	
	@Value("${feedbackurl}")
	String feedbackurl;
	
	SimpleDateFormat inputSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	SimpleDateFormat outputSdf = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW,isolation=Isolation.READ_UNCOMMITTED,rollbackFor= {BadRequestException.class,UnauthoriseException.class,RuntimeException.class,Exception.class})
	public long createPreRegistrationCases(PrcRequest request, LoginUser user)
			throws UnauthoriseException, BadRequestException {
		if (request != null) {
			com.pratikshat.exicom.entity.PreRegistrationCases preRegistrationCases = new com.pratikshat.exicom.entity.PreRegistrationCases();

			
			if (request.getAssetsSerialNumber() != null) {
				Assets assets = null;
				try {
					Query assetsQuery = entityManager.createQuery(
							"select ast from Assets ast left join ast.site left join ast.status sts where ast.serialNumber=?1",
							com.pratikshat.exicom.entity.Assets.class);
					assetsQuery.setParameter(1, request.getAssetsSerialNumber());
					assets = (Assets) assetsQuery.getSingleResult();
					
					if(assets!=null) {
						preRegistrationCases.setAssets(assets);
						if(assets.getAmcExpiryDate()!=null) {
							Date expiryDate =outputSdf.parse(outputSdf.format(assets.getAmcExpiryDate()));
							Date today =outputSdf.parse(outputSdf.format(new Date()));
							
							if(expiryDate.compareTo(today)>=0) {
								preRegistrationCases.setPODate(outputSdf.parse(assets.getAmcPurchaseOrderDate()));
								preRegistrationCases.setPONumber(assets.getAmcPurchaseOrderNumber());
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
				if(caseReasonList.contains(request.getCaseReasonId())) {
					
					if(request.getCaseReasonId()==2L || request.getCaseReasonId()==12L) {
						if(request.getMissingAssets()==null) {
							throw new com.pratikshat.exicom.exception.BadRequestException("Please fill missing asset");
						}
					}
				}else {
					throw new com.pratikshat.exicom.exception.BadRequestException("Please select Asset");
					
				}	
			}
			
			
			SiteDetails site = null;
			if (request.getSiteId() != 0) {
				try {
					Query siteQuery = entityManager.createQuery(
							"select sti from SiteDetails sti join sti.account act where sti.siteId=?1",
							com.pratikshat.exicom.entity.SiteDetails.class);
					siteQuery.setParameter(1, request.getSiteId());
					site = (SiteDetails) siteQuery.getSingleResult();
					
					if(site!=null) {
						preRegistrationCases.setSite(site);
					}else {
						throw new com.pratikshat.exicom.exception.BadRequestException("Site does not exist");
					}
				}catch(NoResultException ex) {
					throw new com.pratikshat.exicom.exception.BadRequestException("Site does not exist");
				}
			} else {
				throw new com.pratikshat.exicom.exception.BadRequestException("Site does not exist");
			}
			

			if (request.getAssetsSerialNumber() != null) {
				try {
					Query assetsQuery = entityManager.createQuery(
							"select ast from Assets ast left join ast.site  where ast.serialNumber=?1",
							com.pratikshat.exicom.entity.Assets.class);
					assetsQuery.setParameter(1, request.getAssetsSerialNumber());
					Assets assets = (Assets) assetsQuery.getSingleResult();
					if (assets.getSite() != null) {
						if (assets.getSite().getSiteId() != request.getSiteId()) {
							throw new com.pratikshat.exicom.exception.BadRequestException(
									"Given site mismatch with the assets installed on the site");
						}
					} else {
						throw new com.pratikshat.exicom.exception.BadRequestException("Site does not exist AT ASSETS");
					}
				} catch (NoResultException ex) {
					throw new com.pratikshat.exicom.exception.BadRequestException("Asset does not exist");
				}
			}
			
			try {
				Query contactQuery = entityManager.createQuery(
						"select cnt from ContactDetails cnt join cnt.account act  where cnt.contactId=?1 and act.sapCustomerNumber=?2",
						com.pratikshat.exicom.entity.ContactDetails.class);
				contactQuery.setParameter(1, request.getContactPersonId());
				contactQuery.setParameter(2, site.getAccount().getSapCustomerNumber());
				com.pratikshat.exicom.entity.ContactDetails contact = (com.pratikshat.exicom.entity.ContactDetails) contactQuery.getSingleResult();
				
				if(contact!=null) {
					preRegistrationCases.setContactPerson(contact);
				}else {
					throw new com.pratikshat.exicom.exception.BadRequestException("Contact does not exist");
				}
			}catch(NoResultException ex) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Contact does not exist");
			}
			
			try {
				Query caseReasonQuery = entityManager.createQuery("select crd from CaseReasonDetails crd join crd.caseType cty join crd.vertical vrt where cty.caseTypeId=?1 and vrt.businessDomainId=?2",CaseReasonDetails.class);
				caseReasonQuery.setParameter(1, request.getCaseTypeId());
				caseReasonQuery.setParameter(2, request.getCaseRecordTypeId());
				List<CaseReasonDetails> reasons = caseReasonQuery.getResultList();
				
				List<Long> reasonList = new ArrayList<>();
				for(int i=0; i<reasons.size(); i++) {
					CaseReasonDetails reason = reasons.get(i);
					reasonList.add(reason.getReasonId());
				}
				
				
				if(!reasonList.contains(request.getCaseReasonId())) {
					throw new com.pratikshat.exicom.exception.BadRequestException("Wrong case reason");
				}
			}catch(NoResultException ex) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Wrong case reason");
			}
			
			try {
				Query caseStageQuery = entityManager.createQuery("select stg from CaseStageDetails stg join stg.caseStatus sts join stg.division vrt where sts.id=?1 and vrt.businessDomainId=?2",CaseStageDetails.class);
				caseStageQuery.setParameter(1, request.getCaseStatusId());
				caseStageQuery.setParameter(2, request.getCaseRecordTypeId());
				List<CaseStageDetails> stages = caseStageQuery.getResultList();
				
				List<Integer> reasonList = new ArrayList<>();
				for(int i=0; i<stages.size(); i++) {
					CaseStageDetails stage = stages.get(i);
					reasonList.add(stage.getId());
				}
				
				if(!reasonList.contains(request.getCaseStageId())) {
					throw new com.pratikshat.exicom.exception.BadRequestException("Wrong case stage");
				}
			}catch(NoResultException ex) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Wrong case stage");
			}
			
			if (request.getServiceResourceId() != 0) {
				ServiceRecource serviceRecource = null;
				try {
					Query caseStageQuery = entityManager.createQuery("select sr from ServiceRecource sr join sr.contact cnt join cnt.legalEntity le join cnt.account act join sr.location lc where le.legalEntityId=?1 and sr.serviceResourceId=?2",ServiceRecource.class);
					caseStageQuery.setParameter(1, request.getServiceCenterId());
					caseStageQuery.setParameter(2, request.getServiceResourceId());
					serviceRecource = (ServiceRecource)caseStageQuery.getSingleResult();
					
				}catch(NoResultException ex) {
					throw new com.pratikshat.exicom.exception.BadRequestException("Service Resource is wrong");
				}
				
				if(serviceRecource==null) {
					throw new com.pratikshat.exicom.exception.BadRequestException("Service Resource is wrong");
				}
				
				preRegistrationCases.setEngineer(serviceRecource);	
			}
			
			
			preRegistrationCases.setCaseReason(new com.pratikshat.exicom.entity.CaseReasonDetails(request.getCaseReasonId()));
			preRegistrationCases.setCaseType(new com.pratikshat.exicom.entity.CaseTypes(request.getCaseTypeId()));
			// Installation for Battery				// Installation for DCT
			if(request.getCaseReasonId()==8 || request.getCaseReasonId()==18) {
				
				if(request.getPoDate()!=null && request.getPoNumber()!=null) {
					preRegistrationCases.setPODate(request.getPoDate());
					preRegistrationCases.setPONumber(request.getPoNumber());	
				}else {
					throw new com.pratikshat.exicom.exception.BadRequestException("PO details missing");
				}
				
			}
			
			if(request.getCaseStatusId()==2 || request.getCaseStatusId()==4) {
				preRegistrationCases.setCloseDate(new Date());
				preRegistrationCases.setClosedBy(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
			}
			
			preRegistrationCases.setCreateDate(new java.util.Date());
			preRegistrationCases.setCreatedBy(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
			preRegistrationCases.setModifiedBy(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
			preRegistrationCases.setModifiedDate(new java.util.Date());
			preRegistrationCases.setNoOfsmr(request.getNoOfSmr());

			preRegistrationCases.setOrigin(new com.pratikshat.exicom.entity.CaseOriginDetails(request.getCaseOriginId()));
			preRegistrationCases.setOwnerDetails(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
			preRegistrationCases.setPriority(new com.pratikshat.exicom.entity.CasePriorityDetails(request.getCasePriorityId()));
			
			preRegistrationCases.setBusinessDomain(new com.pratikshat.exicom.entity.BusinessDomain(request.getCaseRecordTypeId()));
			preRegistrationCases.setRemarks(request.getRemarks());
			preRegistrationCases.setStage(new com.pratikshat.exicom.entity.CaseStageDetails(request.getCaseStageId()));
			preRegistrationCases.setStatus(new com.pratikshat.exicom.entity.CaseStatusDetails(request.getCaseStatusId()));
			preRegistrationCases.setMissingAssets(request.getMissingAssets());
			if (request.getServiceCenterId() != 0) {
				preRegistrationCases.setServiceCenter(new com.pratikshat.exicom.entity.LegalEntity(request.getServiceCenterId()));
			}

			preRegistrationCases.setOtherProblemReported(request.getOtherProblemReported());
			preRegistrationCases.setAssignByAssignmentRule(request.isAssignByAssignmentRule());
			preRegistrationCases.setInputFromEng(request.getInputFromEng());
			preRegistrationCases.setInternalRemark(request.getInternalRemark());
			preRegistrationCases.setRJILTT01No(request.getRJILTT01No());
			preRegistrationCases.setiSmsSend(request.isiSmsSend());
			if(request.getPendingResponsibility()!=0) {
				preRegistrationCases.setPendingResponsibility(new PendingResponsibility(request.getPendingResponsibility()));
			}
			
			workflow.executeWorkflow(entityManager, preRegistrationCases, "INSERT", "PRE", user);
			entityManager.persist(preRegistrationCases);
			
			
			Query updateManualId = entityManager.createQuery("update PreRegistrationCases cas set cas.manualId=?1 where cas.id=?2");
			SimpleDateFormat dateformatter = new SimpleDateFormat("yyyyMMdd");
			//String caseManualIdLastpart = dateformatter.format(new java.util.Date()) + "-"+ com.pratikshat.exicom.config.MiscellaneousUtils.fillZero(String.valueOf(preRegistrationCases.getId()),5);
			String caseManualIdLastpart = com.pratikshat.exicom.config.MiscellaneousUtils.fillZero(String.valueOf(preRegistrationCases.getId()),7);
			String manualId = null;
			if(request.getCaseRecordTypeId()==1) { // Battery
				manualId = "PRC-BTY-" + caseManualIdLastpart;
			}else if(request.getCaseRecordTypeId()==2) { // DCT
				manualId = "PRC-DCT-" + caseManualIdLastpart;
			}else if(request.getCaseRecordTypeId()==3) { // EV
				manualId = "PRC-EVC-" + caseManualIdLastpart;
			}
			
			updateManualId.setParameter(1, manualId);
			updateManualId.setParameter(2, preRegistrationCases.getId());
			updateManualId.executeUpdate();
			request.setCaseId(preRegistrationCases.getId());
			preRegistrationCases.setManualId(manualId);
			workflow.executeWorkflow(entityManager, preRegistrationCases, "INSERT", "POST", user);
			if (request.getCaseProblemReported() != null) {
				for (com.pratikshat.exicom.entity.PrcProblemReported pr : request.getCaseProblemReported()) {
						
					PrcProblemReported caseProblemReported= new PrcProblemReported();
					caseProblemReported.setProblemName(pr.getProblemName());
					caseProblemReported.setPreRegistrationCases(new PreRegistrationCases(preRegistrationCases.getId()));
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
				_search.setObjectDetailsUrl("prc/" + preRegistrationCases.getId());
				_search.setObjectIdName("id");
				_search.setObjectIdValue("" + preRegistrationCases.getId());
				_search.setObjectName("PreRegistrationCases");
				_search.setSearchString(manualId);
				entityManager.persist(_search);

			} catch (Exception e) {
			}

			
			CaseTypes caseType = null;
			try {
				Query caseTypeQuery = entityManager.createQuery("from CaseTypes ct where ct.caseTypeId=?1",
						CaseTypes.class);
				caseTypeQuery.setParameter(1, request.getCaseTypeId());
				caseType = (CaseTypes) caseTypeQuery.getSingleResult();
			} catch (NoResultException ex) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Case Type does not exist");
			}

			CaseStatusDetails caseStatus = null;
			try {
				Query CaseStatusDetailsQuery = entityManager.createQuery("from CaseStatusDetails cs where cs.id=?1",
						CaseStatusDetails.class);
				CaseStatusDetailsQuery.setParameter(1, request.getCaseStatusId());
				caseStatus = (CaseStatusDetails) CaseStatusDetailsQuery.getSingleResult();
			} catch (NoResultException ex) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Case Status does not exist");
			}

			CaseStageDetails caseStageDetails = null;
			try {
				Query caseStageDetailsQuery = entityManager.createQuery(
						"select csd from CaseStageDetails csd where csd.id=?1",
						CaseStageDetails.class);
				caseStageDetailsQuery.setParameter(1, request.getCaseStageId());
				caseStageDetails = (CaseStageDetails) caseStageDetailsQuery.getSingleResult();
			} catch (NoResultException ex) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Case Stage does not exist");
			}

			CaseReasonDetails caseReason = null;
			try {
				Query CaseReasonDetailsQuery = entityManager
						.createQuery("from CaseReasonDetails cr where cr.reasonId=?1", CaseReasonDetails.class);
				CaseReasonDetailsQuery.setParameter(1, request.getCaseReasonId());
				caseReason = (CaseReasonDetails) CaseReasonDetailsQuery.getSingleResult();
			} catch (NoResultException ex) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Case Reason does not exist");
			}
			
			
			Query siteDetailsQuery = entityManager.createQuery(
					"select st from SiteDetails st  left join st.circle cr left join cr.circleManagerBattry cb left join cr.circleManagerPower cp left join cr.circleManagerEv ev where st.siteId=?1",
					com.pratikshat.exicom.entity.SiteDetails.class);

			Query buisnessModuleQuery = entityManager
					.createQuery("select bs from BusinessDomain bs where bs.businessDomainId=?1", BusinessDomain.class);
			buisnessModuleQuery.setParameter(1, request.getCaseRecordTypeId());
			BusinessDomain buisnessModule = (BusinessDomain) buisnessModuleQuery.getSingleResult();
			com.pratikshat.exicom.entity.SiteDetails siteDetails = null;
			try {
				siteDetailsQuery.setParameter(1, request.getSiteId());
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
					
					com.pratikshat.exicom.entity.ApplicationNotificationDetails notificationOwner = new com.pratikshat.exicom.entity.ApplicationNotificationDetails();
					notificationOwner.setNofificationDate(new java.util.Date());
					notificationOwner.setNotificationActionUrl("prc/" + preRegistrationCases.getId());
					notificationOwner.setNotificationMessage("New Pre registration Case has been registered With Id " + manualId);
					notificationOwner.setNotificationRead(false);
					notificationOwner.setUserEmailId(user.getUsername());
					entityManager.persist(notificationOwner);

					try {
							messagingTemplate.convertAndSend("/topic/" + user.getUsername(), notificationOwner);
						} catch (Exception e) {
							e.printStackTrace();
						}
					
					
					com.pratikshat.exicom.entity.ApplicationNotificationDetails notificationSch = new com.pratikshat.exicom.entity.ApplicationNotificationDetails();
					notificationSch.setNofificationDate(new java.util.Date());
					notificationSch.setNotificationActionUrl("prc/" + preRegistrationCases.getId());
					notificationSch.setNotificationMessage("New Pre registration Case has been registered With Id " + manualId);
					notificationSch.setNotificationRead(false);
					notificationSch.setUserEmailId(emailId);
					entityManager.persist(notificationSch);

					try {
							messagingTemplate.convertAndSend("/topic/" + emailId, notificationSch);
						} catch (Exception e) {
							e.printStackTrace();
						}
			} else {
				throw new BadRequestException("Case Type Mismatch");
			}

			entityManager.flush();
			entityManager.clear();

		} else {
			throw new BadRequestException("No data found to save details");
		}

		return request.getCaseId();
	}


	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW,isolation=Isolation.READ_UNCOMMITTED,rollbackFor= {BadRequestException.class,UnauthoriseException.class,RuntimeException.class,Exception.class})
	public long updatePreRegistrationCases(PrcRequest request, LoginUser user)
			throws UnauthoriseException, BadRequestException {
		
		PreRegistrationCases preRegistrationCase = null;
		try {
			Query selectCaseDetailsQuery = entityManager.createQuery(
					"select ca from PreRegistrationCases ca left join ca.status cs left join ca.assets ast left join ast.account acn left join ca.site st left join ca.ownerDetails onr left join ca.createdBy createdby left join ca.modifiedBy modified left join ca.engineer eng left join eng.contact econ left join ca.serviceCenter scr where ca.id=?1",
					PreRegistrationCases.class);
			selectCaseDetailsQuery.setParameter(1, request.getCaseId());
			preRegistrationCase = (PreRegistrationCases) selectCaseDetailsQuery.getSingleResult();
		} catch (NoResultException ex) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Case does not exist");
		}

		if (preRegistrationCase != null) {
			if(!preRegistrationCase.getOwnerDetails().getEmailId().equalsIgnoreCase(user.getUsername())) {
				Map<String,String> group= dbProfile.getAccountAndRoleGroup(user);
				if(group.get("roleGroup").equalsIgnoreCase("CUSTOMERCARE")||group.get("roleGroup").equalsIgnoreCase("ADMIN")) {
					
				}
				else {
					throw new com.pratikshat.exicom.exception.BadRequestException("Your are not authorised to edit this record");
				}
				
			}
			
			if(preRegistrationCase.getLastAction()!=null) {
				throw new com.pratikshat.exicom.exception.BadRequestException("You can't edit this record");
			}
			
			List<Integer> list = new ArrayList<>();
			list.add(2); // Closed
			list.add(4); // Rejected
			
			if(list.contains(preRegistrationCase.getStatus().getId())) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Your can't edit this record");
			}
			
			if (request.getAssetsSerialNumber() != null) {
				Assets assets = null;
				try {
					Query assetsQuery = entityManager.createQuery(
							"select ast from Assets ast left join ast.site left join ast.status sts where ast.serialNumber=?1",
							com.pratikshat.exicom.entity.Assets.class);
					assetsQuery.setParameter(1, request.getAssetsSerialNumber());
					assets = (Assets) assetsQuery.getSingleResult();
					
					if(assets!=null) {
						preRegistrationCase.setAssets(assets);
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
				
				if(caseReasonList.contains(request.getCaseReasonId())) {
					if(request.getCaseReasonId()==2L || request.getCaseReasonId()==12L) {
						if(request.getMissingAssets()==null) {
							throw new com.pratikshat.exicom.exception.BadRequestException("Please fill missing asset");
						}
					}
				}else {
					throw new com.pratikshat.exicom.exception.BadRequestException("Please select Asset");
					
				}	
			}
			SiteDetails site = null;
			if (request.getSiteId() != 0) {
				try {
					Query siteQuery = entityManager.createQuery(
							"select sti from SiteDetails sti left join sti.account act where sti.siteId=?1",
							com.pratikshat.exicom.entity.SiteDetails.class);
					siteQuery.setParameter(1, request.getSiteId());
					site = (SiteDetails) siteQuery.getSingleResult();
					
					if(site!=null) {
						preRegistrationCase.setSite(site);
					}else {
						throw new com.pratikshat.exicom.exception.BadRequestException("Site does not exist");
					}
				}catch(NoResultException ex) {
					throw new com.pratikshat.exicom.exception.BadRequestException("Site does not exist");
				}
			} else {
				throw new com.pratikshat.exicom.exception.BadRequestException("Site does not exist");
			}
			

			if (request.getAssetsSerialNumber() != null) {
				try {
					Query assetsQuery = entityManager.createQuery(
							"select ast from Assets ast left join ast.site  where ast.serialNumber=?1",
							com.pratikshat.exicom.entity.Assets.class);
					assetsQuery.setParameter(1, request.getAssetsSerialNumber());
					Assets assets = (Assets) assetsQuery.getSingleResult();
					if (assets.getSite() != null) {
						if (assets.getSite().getSiteId() != request.getSiteId()) {
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
				contactQuery.setParameter(1, request.getContactPersonId());
				contactQuery.setParameter(2, site.getAccount().getSapCustomerNumber());
				com.pratikshat.exicom.entity.ContactDetails contact = (com.pratikshat.exicom.entity.ContactDetails) contactQuery.getSingleResult();
				
				if(contact!=null) {
					preRegistrationCase.setContactPerson(contact);
				}else {
					throw new com.pratikshat.exicom.exception.BadRequestException("Contact does not exist");
				}
			}catch(NoResultException ex) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Contact does not exist");
			}
			
			try {
				Query caseReasonQuery = entityManager.createQuery("select crd.reasonId from CaseReasonDetails crd join crd.caseType cty join crd.vertical vrt where cty.caseTypeId=?1 and vrt.businessDomainId=?2",Long.class);
				caseReasonQuery.setParameter(1, request.getCaseTypeId());
				caseReasonQuery.setParameter(2, request.getCaseRecordTypeId());
				List<Long> reasonList = (List<Long>)caseReasonQuery.getResultList();
				
				if(!reasonList.contains(request.getCaseReasonId())) {
					throw new com.pratikshat.exicom.exception.BadRequestException("Wrong case reason");
				}
			}catch(NoResultException ex) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Wrong case reason");
			}
			
			try {
				Query caseStageQuery = entityManager.createQuery("select stg.id from CaseStageDetails stg join stg.caseStatus sts join stg.division vrt where sts.id=?1 and vrt.businessDomainId=?2",Integer.class);
				caseStageQuery.setParameter(1, request.getCaseStatusId());
				caseStageQuery.setParameter(2, request.getCaseRecordTypeId());
				List<Integer> reasonList = (List<Integer>)caseStageQuery.getResultList();
				
				if(!reasonList.contains(request.getCaseStageId())) {
					throw new com.pratikshat.exicom.exception.BadRequestException("Wrong case stage");
				}
			}catch(NoResultException ex) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Wrong case stage");
			}
			
			
			if (request.getServiceResourceId() != 0) {
				ServiceRecource serviceRecource = null;
				try {
					Query caseStageQuery = entityManager.createQuery("select sr from ServiceRecource sr join sr.contact cnt join cnt.legalEntity le join cnt.account act join sr.location lc where le.legalEntityId=?1 and sr.serviceResourceId=?2",ServiceRecource.class);
					caseStageQuery.setParameter(1, request.getServiceCenterId());
					caseStageQuery.setParameter(2, request.getServiceResourceId());
					serviceRecource = (ServiceRecource)caseStageQuery.getSingleResult();
					
				}catch(NoResultException ex) {
					throw new com.pratikshat.exicom.exception.BadRequestException("Service Resource is wrong");
				}
				
				if(serviceRecource==null) {
					throw new com.pratikshat.exicom.exception.BadRequestException("Service Resource is wrong");
				}
				
				preRegistrationCase.setEngineer(serviceRecource);	
			}
			
			
			preRegistrationCase.setCaseReason(new com.pratikshat.exicom.entity.CaseReasonDetails(request.getCaseReasonId()));
			preRegistrationCase.setCaseType(new com.pratikshat.exicom.entity.CaseTypes(request.getCaseTypeId()));
			
			// Installation for Battery				// Installation for DCT
			if(request.getCaseReasonId()==8 || request.getCaseReasonId()==18) {
							
				if(request.getPoDate()!=null && request.getPoNumber()!=null) {
					preRegistrationCase.setPODate(request.getPoDate());
					preRegistrationCase.setPONumber(request.getPoNumber());	
				}else {
					throw new com.pratikshat.exicom.exception.BadRequestException("PO details missing");
				}
			}
			
			preRegistrationCase.setModifiedBy(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
			preRegistrationCase.setModifiedDate(new java.util.Date());
			preRegistrationCase.setNoOfsmr(request.getNoOfSmr());

			preRegistrationCase.setOrigin(new com.pratikshat.exicom.entity.CaseOriginDetails(request.getCaseOriginId()));
			//preRegistrationCase.setOwnerDetails(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
			preRegistrationCase.setPriority(new com.pratikshat.exicom.entity.CasePriorityDetails(request.getCasePriorityId()));
			
			preRegistrationCase.setRemarks(request.getRemarks());
			preRegistrationCase.setStage(new com.pratikshat.exicom.entity.CaseStageDetails(request.getCaseStageId()));
			preRegistrationCase.setStatus(new com.pratikshat.exicom.entity.CaseStatusDetails(request.getCaseStatusId()));
			preRegistrationCase.setMissingAssets(request.getMissingAssets());
			if (request.getServiceCenterId() != 0) {
				preRegistrationCase.setServiceCenter(new com.pratikshat.exicom.entity.LegalEntity(request.getServiceCenterId()));
			}

			preRegistrationCase.setOtherProblemReported(request.getOtherProblemReported());
			preRegistrationCase.setAssignByAssignmentRule(request.isAssignByAssignmentRule());
			preRegistrationCase.setInputFromEng(request.getInputFromEng());
			preRegistrationCase.setInternalRemark(request.getInternalRemark());
			preRegistrationCase.setRJILTT01No(request.getRJILTT01No());
			if(request.getPendingResponsibility()!=0) {
				preRegistrationCase.setPendingResponsibility(new PendingResponsibility(request.getPendingResponsibility()));
			}
			
			workflow.executeWorkflow(entityManager, preRegistrationCase, "UPDATE", "PRE", user);
			entityManager.persist(preRegistrationCase);
			workflow.executeWorkflow(entityManager, preRegistrationCase, "UPDATE", "POST", user);

			if (request.getCaseProblemReported() != null) {
				
				Query deleteProblemId = entityManager.createQuery("delete PrcProblemReported pr where pr.preRegistrationCases.id=?1");
				deleteProblemId.setParameter(1, request.getCaseId());
				deleteProblemId.executeUpdate();
				
				for (com.pratikshat.exicom.entity.PrcProblemReported pr : request.getCaseProblemReported()) {
					PrcProblemReported caseProblemReported= new PrcProblemReported();
					caseProblemReported.setProblemName(pr.getProblemName());
					caseProblemReported.setPreRegistrationCases(new PreRegistrationCases(request.getCaseId()));
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
				caseTypeQuery.setParameter(1, request.getCaseTypeId());
				caseType = (CaseTypes) caseTypeQuery.getSingleResult();
			} catch (NoResultException ex) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Case Type does not exist");
			}

			CaseStatusDetails caseStatus = null;
			try {
				Query CaseStatusDetailsQuery = entityManager.createQuery("from CaseStatusDetails cs where cs.id=?1",
						CaseStatusDetails.class);
				CaseStatusDetailsQuery.setParameter(1, request.getCaseStatusId());
				caseStatus = (CaseStatusDetails) CaseStatusDetailsQuery.getSingleResult();
			} catch (NoResultException ex) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Case Status does not exist");
			}

			CaseStageDetails caseStageDetails = null;
			try {
				Query caseStageDetailsQuery = entityManager.createQuery(
						"select csd from CaseStageDetails csd where csd.id=?1",
						CaseStageDetails.class);
				caseStageDetailsQuery.setParameter(1, request.getCaseStageId());
				caseStageDetails = (CaseStageDetails) caseStageDetailsQuery.getSingleResult();
			} catch (NoResultException ex) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Case Stage does not exist");
			}


			CaseReasonDetails caseReason = null;
			try {
				Query CaseReasonDetailsQuery = entityManager
						.createQuery("from CaseReasonDetails cr where cr.reasonId=?1", CaseReasonDetails.class);
				CaseReasonDetailsQuery.setParameter(1, request.getCaseReasonId());
				caseReason = (CaseReasonDetails) CaseReasonDetailsQuery.getSingleResult();
			} catch (NoResultException ex) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Case Reason does not exist");
			}

				 
			Query siteDetailsQuery = entityManager.createQuery(
					"select st from SiteDetails st  left join st.circle cr left join cr.circleManagerBattry cb left join cr.circleManagerPower cp left join cr.circleManagerEv ev where st.siteId=?1",
					com.pratikshat.exicom.entity.SiteDetails.class);

			Query buisnessModuleQuery = entityManager
					.createQuery("select bs from BusinessDomain bs where bs.businessDomainId=?1", BusinessDomain.class);
			buisnessModuleQuery.setParameter(1, request.getCaseRecordTypeId());
			BusinessDomain buisnessModule = (BusinessDomain) buisnessModuleQuery.getSingleResult();
			com.pratikshat.exicom.entity.SiteDetails siteDetails = null;
			try {
				siteDetailsQuery.setParameter(1, request.getSiteId());
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
					
					com.pratikshat.exicom.entity.ApplicationNotificationDetails notificationOwner = new com.pratikshat.exicom.entity.ApplicationNotificationDetails();
					notificationOwner.setNofificationDate(new java.util.Date());
					notificationOwner.setNotificationActionUrl("prc/" + preRegistrationCase.getId());
					notificationOwner.setNotificationMessage("Pre registration Case " + preRegistrationCase.getManualId() + "Updated");
					notificationOwner.setNotificationRead(false);
					notificationOwner.setUserEmailId(user.getUsername());
					entityManager.persist(notificationOwner);

					try {
							messagingTemplate.convertAndSend("/topic/" + user.getUsername(), notificationOwner);
						} catch (Exception e) {
							e.printStackTrace();
						}
					
					if(emailId!=null){
						com.pratikshat.exicom.entity.ApplicationNotificationDetails notificationSch = new com.pratikshat.exicom.entity.ApplicationNotificationDetails();
						notificationSch.setNofificationDate(new java.util.Date());
						notificationSch.setNotificationActionUrl("prc/" + preRegistrationCase.getId());
						notificationSch.setNotificationMessage("Pre registration Case " + preRegistrationCase.getManualId() + "Updated");
						notificationSch.setNotificationRead(false);
						notificationSch.setUserEmailId(emailId);
						entityManager.persist(notificationSch);
	
						try {
								messagingTemplate.convertAndSend("/topic/" + emailId, notificationSch);
							} catch (Exception e) {
								e.printStackTrace();
							}
					}

					
			} else {
				throw new BadRequestException("Case Type Mismatch");
			}
				entityManager.flush();
				entityManager.clear();
		} else {
			throw new BadRequestException("No data found to save details");
		}
		return request.getCaseId();
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW,isolation=Isolation.READ_UNCOMMITTED,rollbackFor= {BadRequestException.class,UnauthoriseException.class,RuntimeException.class,Exception.class})
	@Override
	public long actionOnPreRegistrationCase(long preCaseId, String action, String comment, LoginUser user)
			throws UnauthoriseException, BadRequestException {

		PreRegistrationCases preRegistrationCase = null;
		try {
			Query selectCaseDetailsQuery = entityManager.createQuery(
					"select ca from PreRegistrationCases ca left join ca.status cs left join ca.assets ast left join ast.account acn left join ca.site st left join ca.ownerDetails onr left join ca.createdBy createdby left join ca.modifiedBy modified left join ca.engineer eng left join eng.contact econ left join ca.serviceCenter scr left join ca.contactPerson cpr left join ca.caseType cty left join ca.caseReason crs left join ca.origin ori left join ca.priority pty left join ca.businessDomain bd left join ca.stage stg left join ca.pendingResponsibility pdr where ca.id=?1",
					PreRegistrationCases.class);
			selectCaseDetailsQuery.setParameter(1,preCaseId);
			preRegistrationCase = (PreRegistrationCases) selectCaseDetailsQuery.getSingleResult();
		} catch (NoResultException ex) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Pre registration case does not exist");
		}
		
		if(preRegistrationCase==null) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Pre registration case does not exist");
		}
		
		CaseDetails _caseExist = null;
		try {
			Query selectCaseDetailsQuery = entityManager.createQuery(
					"select ca from CaseDetails ca left join ca.prc pc where pc.id=?1",
					CaseDetails.class);
			selectCaseDetailsQuery.setParameter(1, preCaseId);
			_caseExist = (CaseDetails) selectCaseDetailsQuery.getSingleResult();
		} catch (NoResultException ex) {
			
		}
		
		if(_caseExist!=null) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Case already registered on this");
		}
		
		
		if(action.equalsIgnoreCase(PreRegistrationCasesRepository.GENERATE_CASE)) {
			System.out.println("i am at 1217"+preRegistrationCase.isiSmsSend());
			// Case Generation Code
			com.pratikshat.exicom.entity.CaseDetails _caseDetails = new com.pratikshat.exicom.entity.CaseDetails();
			_caseDetails.setAssets(preRegistrationCase.getAssets());
			_caseDetails.setSite(preRegistrationCase.getSite());
			_caseDetails.setContactPerson(preRegistrationCase.getContactPerson());
			_caseDetails.setEngineer(preRegistrationCase.getEngineer());
			_caseDetails.setCaseReason(preRegistrationCase.getCaseReason());
			_caseDetails.setCaseType(preRegistrationCase.getCaseType());
			_caseDetails.setPODate(preRegistrationCase.getPODate());
			_caseDetails.setPONumber(preRegistrationCase.getPONumber());
			_caseDetails.setCreateDate(new java.util.Date());
			_caseDetails.setCreatedBy(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
			_caseDetails.setModifiedBy(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
			_caseDetails.setModifiedDate(new java.util.Date());
			_caseDetails.setNoOfsmr(preRegistrationCase.getNoOfsmr());

			_caseDetails.setOrigin(preRegistrationCase.getOrigin());
			_caseDetails.setPriority(preRegistrationCase.getPriority());
			
			_caseDetails.setBusinessDomain(preRegistrationCase.getBusinessDomain());
			_caseDetails.setRemarks(preRegistrationCase.getRemarks());
			_caseDetails.setStage(preRegistrationCase.getStage());
			_caseDetails.setStatus(preRegistrationCase.getStatus());
			_caseDetails.setSynked(false);
			_caseDetails.setMissingAssets(preRegistrationCase.getMissingAssets());
			_caseDetails.setServiceCenter(preRegistrationCase.getServiceCenter());

			_caseDetails.setOtherProblemReported(preRegistrationCase.getOtherProblemReported());
			_caseDetails.setAssignByAssignmentRule(preRegistrationCase.isAssignByAssignmentRule());
			_caseDetails.setInputFromEng(preRegistrationCase.getInputFromEng());
			_caseDetails.setInternalRemark(preRegistrationCase.getInternalRemark());
			_caseDetails.setRJILTT01No(preRegistrationCase.getRJILTT01No());
			_caseDetails.setPendingResponsibility(preRegistrationCase.getPendingResponsibility());
            _caseDetails.setiSmsSend(preRegistrationCase.isiSmsSend());
			_caseDetails.setOwnerDetails(preRegistrationCase.getCreatedBy());
			_caseDetails.setPrc(new PreRegistrationCases(preCaseId));
			workflow.executeWorkflow(entityManager, _caseDetails, "INSERT", "PRE", user);
			entityManager.persist(_caseDetails);
			
			
			Query updateManualId = entityManager.createQuery("update CaseDetails cas set cas.manualId=?1 where cas.caseId=?2");
			SimpleDateFormat dateformatter = new SimpleDateFormat("yyyyMMdd");
			String caseManualIdLastpart = dateformatter.format(new java.util.Date()) + "-"+ com.pratikshat.exicom.config.MiscellaneousUtils.fillZero(String.valueOf(_caseDetails.getCaseId()),5);
			//String caseManualIdLastpart = com.pratikshat.exicom.config.MiscellaneousUtils.fillZero(String.valueOf(_caseDetails.getCaseId()),7);
			String manualId = null;
			if(preRegistrationCase.getBusinessDomain().getBusinessDomainId()==1) { // Battery
				manualId = "CS-BTY-" + caseManualIdLastpart;
			}else if(preRegistrationCase.getBusinessDomain().getBusinessDomainId()==2) { // DCT
				manualId = "CS-DCT-" + caseManualIdLastpart;
			}else if(preRegistrationCase.getBusinessDomain().getBusinessDomainId()==3) { // EV
				manualId = "CS-EVC-" + caseManualIdLastpart;
			}
			updateManualId.setParameter(1, manualId);
			updateManualId.setParameter(2, _caseDetails.getCaseId());
			updateManualId.executeUpdate();

			workflow.executeWorkflow(entityManager, _caseDetails, "INSERT", "POST", user);
			
			Query prcProblemReported = entityManager.createQuery(
					"select pr from PrcProblemReported pr join pr.preRegistrationCases cs where cs.id=?1",com.pratikshat.exicom.entity.PrcProblemReported.class);

			List<com.pratikshat.exicom.entity.PrcProblemReported> problemReporedList = null;
			try {
				prcProblemReported.setParameter(1, preCaseId);
				problemReporedList = (List<com.pratikshat.exicom.entity.PrcProblemReported>)prcProblemReported.getResultList();
			} catch (NoResultException ex) {

			}
			
			if (problemReporedList != null && !problemReporedList.isEmpty()) {
				for (com.pratikshat.exicom.entity.PrcProblemReported pr : problemReporedList) {
						
					CaseProblemReported caseProblemReported= new CaseProblemReported();
					caseProblemReported.setProblemName(pr.getProblemName());
					caseProblemReported.setCaseDetails(new CaseDetails(_caseDetails.getCaseId()));
					entityManager.persist(caseProblemReported);
				}
			}

			// search index entry
			try {
				com.pratikshat.exicom.entity.GlobalSearchIndex _search = new com.pratikshat.exicom.entity.GlobalSearchIndex();
				_search.setKeyName("manualId");
				_search.setObjectDetailsUrl("cases/" + _caseDetails.getCaseId());
				_search.setObjectIdName("caseId");
				_search.setObjectIdValue("" + _caseDetails.getCaseId());
				_search.setObjectName("Case");
				_search.setSearchString(manualId);
				entityManager.persist(_search);

			} catch (Exception e) {
			}
			
			
			Query buisnessModuleQuery = entityManager
					.createQuery("select bs from BusinessDomain bs where bs.businessDomainId=?1", BusinessDomain.class);
			buisnessModuleQuery.setParameter(1, preRegistrationCase.getBusinessDomain().getBusinessDomainId());
			BusinessDomain buisnessModule = (BusinessDomain) buisnessModuleQuery.getSingleResult();
			
			
			com.pratikshat.exicom.entity.SiteDetails siteDetails = null;
			Query siteDetailsQuery = entityManager.createQuery(
					"select st from SiteDetails st  left join st.circle cr left join cr.circleManagerBattry cb left join cr.circleManagerPower cp left join cr.circleManagerEv ev where st.siteId=?1",
					com.pratikshat.exicom.entity.SiteDetails.class);
			try {
				siteDetailsQuery.setParameter(1, preRegistrationCase.getSite().getSiteId());
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
			
			
			List<Integer> statusList = new ArrayList<>();
			statusList.add(1);	// New
			statusList.add(2);	// Closed
			statusList.add(4);	// Rejected
			
			if (statusList.contains(preRegistrationCase.getStatus().getId())) {
				
				try {
					Query caseOwner = entityManager
							.createQuery("update CaseDetails cs set cs.ownerDetails=?1 where cs.caseId=?2");
					caseOwner.setParameter(1, preRegistrationCase.getCreatedBy());
					caseOwner.setParameter(2, _caseDetails.getCaseId());
					caseOwner.executeUpdate();
				}catch (Exception e) {
					throw new com.pratikshat.exicom.exception.BadRequestException(e.getMessage());
				}
				
				com.pratikshat.exicom.entity.ApplicationNotificationDetails notification = new com.pratikshat.exicom.entity.ApplicationNotificationDetails();
				notification.setNofificationDate(new java.util.Date());
				notification.setNotificationActionUrl("cases/" + _caseDetails.getCaseId());
				notification.setNotificationMessage("New Case has been registread With Id " + manualId);
				notification.setNotificationRead(false);
				notification.setUserEmailId(preRegistrationCase.getCreatedBy().getEmailId());
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
					caseOwner.setParameter(2, _caseDetails.getCaseId());
					caseOwner.executeUpdate();
				} else {
					throw new com.pratikshat.exicom.exception.BadRequestException("Circle Manager Does not exist");
				}
				
				
				com.pratikshat.exicom.entity.ApplicationNotificationDetails notification = new com.pratikshat.exicom.entity.ApplicationNotificationDetails();
				notification.setNofificationDate(new java.util.Date());
				notification.setNotificationActionUrl("cases/" + _caseDetails.getCaseId());
				notification.setNotificationMessage("New Case has been registread With Id " + manualId);
				notification.setNotificationRead(false);
				notification.setUserEmailId(emailId);
				entityManager.persist(notification);

				try {
					messagingTemplate.convertAndSend("/topic/" + emailId, notification);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			
			// Service Appointment Generation block
			if(preRegistrationCase.getStatus()!=null && preRegistrationCase.getStatus().getId()==3){	// In-Process

				ServiceAppointment _serviceAppointment = new ServiceAppointmentUtil().createServiceAppointment(_caseDetails.getCaseId(), user.getUsername(), entityManager);
				
				
				workflow.executeWorkflow(entityManager, _serviceAppointment, "INSERT", "PRE", user);
				entityManager.persist(_serviceAppointment);
				workflow.executeWorkflow(entityManager, _serviceAppointment, "INSERT", "POST", user);
				
				
				
				Query updateServiceAppointmentManualId = entityManager.createQuery(
						"update ServiceAppointment sa set sa.appointmentNumber=?1 where sa.serviceAppointmentId=?2");
				SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
//				String serviceAppointmentManualId = "SA-" + df.format(new java.util.Date()) + "-"
//						+ com.pratikshat.exicom.config.MiscellaneousUtils
//								.fillZero(String.valueOf(_serviceAppointment.getServiceAppointmentId()), 5);
				
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
			if(preRegistrationCase.isiSmsSend()) {
				System.out.println("i am at 1217"+preRegistrationCase.isiSmsSend());
					try {
					String selectCase = "Select cd from ContactDetails cd where cd.emailId = ?1";
					ContactDetails cd = entityManager.createQuery(selectCase, ContactDetails.class)
							.setParameter(1, emailId).getSingleResult();
//					shagufta added for enf and customer mobile number for send SMS as new CR
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
//						eng mobile
						SmsNotifier sNotifiereng = new SmsNotifier(entityManager,
								engMobile, smsMessage, user.getUsername(),
								templateDetails.getTemplaeCode());
						sNotifiereng.sendNotification();
//						customer
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
		if(action.equalsIgnoreCase(PreRegistrationCasesRepository.IGNORE) || action.equalsIgnoreCase(PreRegistrationCasesRepository.GENERATE_CASE)) {
			preRegistrationCase.setLastAction(action);
			preRegistrationCase.setLastActionComment(comment);
			preRegistrationCase.setModifiedBy(new EmployeeDetails(user.getUsername()));
			preRegistrationCase.setModifiedDate(new Date());
			
			workflow.executeWorkflow(entityManager, preRegistrationCase, "UPDATE", "PRE", user);
			entityManager.persist(preRegistrationCase);
			workflow.executeWorkflow(entityManager, preRegistrationCase, "UPDATE", "POST", user);
			
			
			PreRegistrationCaseActionLog log = new PreRegistrationCaseActionLog();
			log.setAction(action);
			log.setActionTakenBy(new EmployeeDetails(user.getUsername()));
			log.setComment(comment);
			log.setPreRegistrationCases(preRegistrationCase);
			log.setTimestamp(new Date());
			
			workflow.executeWorkflow(entityManager, log, "UPDATE", "PRE", user);
			entityManager.persist(log);
			workflow.executeWorkflow(entityManager, log, "UPDATE", "POST", user);
		
		}else {
			throw new com.pratikshat.exicom.exception.BadRequestException("Action is wrong");
		}
		
		entityManager.flush();
		entityManager.clear();

		
		return preCaseId;
	}
	
	
	@Override
	@Transactional
	public List<PrcResponse> advanceSearch( Map<String,Object> request,List<String> users) {
		
		String queryString ="select new com.pratikshat.exicom.model.response.PrcResponse(ca,cd) from PreRegistrationCases ca left join ca.assets ast left join ca.site st left join st.account acn left join ca.ownerDetails onr left join CaseDetails cd on ca=cd.prc";
		if(users!=null) {
			queryString+=" where (ca.ownerDetails.emailId in (:userlist) or ca.engineer.user in (:userlist))";
		}else {
			queryString+=" where 1=1 ";
		}
		
		if(request.get("manualId")!=null&&!request.get("manualId").equals("")) {
			queryString+=" and ca.manualId like :manualId";
		}
		if(request.get("caseManualId")!=null&&!request.get("caseManualId").equals("")) {
			queryString+=" and cd.manualId like :caseManualId";
		}
		if(request.get("lastAction")!=null&&!request.get("lastAction").equals("")) {
			if(!request.get("lastAction").toString().equalsIgnoreCase("Pending")) {
				queryString+=" and ca.lastAction like :lastAction";	
			}else {
				queryString+=" and ca.lastAction IS NULL ";
			}
		}
		if(request.get("manualSiteId")!=null&&!request.get("manualSiteId").equals("")) {
			queryString+=" and st.manualSiteId like :manualSiteId";
		}
		if(request.get("assetSerialNumber")!=null&&!request.get("assetSerialNumber").equals("")) {
			queryString+=" and (ca.missingAssets like :assetSerialNumber or ast.serialNumber like :assetSerialNumber)";
		}
		if(request.get("type")!=null&&!request.get("type").equals("")) {
			queryString+=" and ca.caseType.caseTypesName like :type";
		}
		if(request.get("reason")!=null&&!request.get("reason").equals("")) {
			queryString+=" and ca.caseReason.caseReasonDetailsName like :reason";
		}
		if(request.get("status")!=null&&!request.get("status").equals("")) {
			queryString+=" and ca.status.caseStatusName like :status";
		}
		if(request.get("stage")!=null&&!request.get("stage").equals("")) {
			queryString+=" and ca.stage.caseStageName like :stage";
		}
																
		if(request.get("engineer")!=null&&!request.get("engineer").equals("")) {
			queryString+=" and ca.engineer.name like :engineer";									
		}
		if(request.get("account")!=null&&!request.get("account").equals("")) {
			queryString+=" and st.account.sapCustomerNumber like :account";
		}
		
		Query qry=entityManager.createQuery(queryString,PrcResponse.class);
		
		
		if(users!=null) {
			qry.setParameter("userlist", users);
		}
		
		
		if(request.get("manualId")!=null&&!request.get("manualId").equals("")) {
			qry.setParameter("manualId","%"+ request.get("manualId")+"%");
		}
		if(request.get("caseManualId")!=null&&!request.get("caseManualId").equals("")) {
			qry.setParameter("caseManualId","%"+ request.get("caseManualId")+"%");
		}
		if(request.get("lastAction")!=null&&!request.get("lastAction").equals("")) {
			if(!request.get("lastAction").toString().equalsIgnoreCase("Pending")) {
				qry.setParameter("lastAction","%"+ request.get("lastAction")+"%");	
			}
		}
		if(request.get("manualSiteId")!=null&&!request.get("manualSiteId").equals("")) {
			qry.setParameter("manualSiteId","%"+ request.get("manualSiteId")+"%");
		}
		if(request.get("assetSerialNumber")!=null&&!request.get("assetSerialNumber").equals("")) {
			qry.setParameter("assetSerialNumber","%"+ request.get("assetSerialNumber")+"%");
		}
		if(request.get("type")!=null&&!request.get("type").equals("")) {
			qry.setParameter("type","%"+ request.get("type")+"%");
		}
		if(request.get("reason")!=null&&!request.get("reason").equals("")) {
			qry.setParameter("reason","%"+ request.get("reason")+"%");
		}
		if(request.get("status")!=null&&!request.get("status").equals("")) {
			qry.setParameter("status","%"+ request.get("status")+"%");
		}
		if(request.get("stage")!=null&&!request.get("stage").equals("")) {
			qry.setParameter("stage","%"+ request.get("stage")+"%");
		}
																
		if(request.get("engineer")!=null&&!request.get("engineer").equals("")) {
			qry.setParameter("engineer","%"+ request.get("engineer")+"%");
		}
		if(request.get("account")!=null&&!request.get("account").equals("")) {
			qry.setParameter("account","%"+ request.get("account")+"%");
		}
		
		
		List<PrcResponse> csRes= qry.getResultList();
																										
		
		
		 return csRes;
	}

}
