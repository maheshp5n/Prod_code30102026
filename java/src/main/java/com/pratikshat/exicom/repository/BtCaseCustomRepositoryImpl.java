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
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.pratikshat.exicom.entity.BtCase;
import com.pratikshat.exicom.entity.BtCaseProblemReported;
import com.pratikshat.exicom.entity.BtCaseRejectLog;
import com.pratikshat.exicom.entity.BtServiceAppointment;
import com.pratikshat.exicom.entity.BtSiteDetails;
import com.pratikshat.exicom.entity.BtWccDetails;
import com.pratikshat.exicom.entity.BusinessDomain;
import com.pratikshat.exicom.entity.BtCase;
import com.pratikshat.exicom.entity.BtCaseRejectLog;
import com.pratikshat.exicom.entity.CaseStageDetails;
import com.pratikshat.exicom.entity.CaseStatusDetails;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.GlobalSearchIndex;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.BtServiceAppointment;
import com.pratikshat.exicom.entity.ServiceAppointmentStatus;
import com.pratikshat.exicom.entity.UploadedFiles;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.RejectCaseRequest;
import com.pratikshat.exicom.services.FileStorageService;

public class BtCaseCustomRepositoryImpl implements BtCaseCustomRepository {

	@PersistenceContext
	EntityManager em;
	@Autowired
	BusinessDomainRepository dbDomain;
	@Autowired
	BtSiteDetailsRepository dbSite;
	@Autowired
	com.pratikshat.exicom.interceptor.WorkflowExecuter workflow;
	@Autowired
	private UserProfileRepository dbProfile;
	@Autowired
	BtServiceAppointmentRepository dbServiceApp;
	@Autowired
	BtCaseRejectLogRepository dbCaseRejectRepo;
	
	

	@Override
	public BtCase saveDetails(BtCase btCase, LoginUser user) throws BadRequestException, UnauthoriseException {
		workflow.executeWorkflow(em, btCase, "INSERT", "PRE", user);
		em.persist(btCase);
		SimpleDateFormat dateformatter = new SimpleDateFormat("yyyyMMdd");
		String manualId = null;
		manualId = "CS-BT-" + dateformatter.format(new java.util.Date()) + "-"
				+ com.pratikshat.exicom.config.MiscellaneousUtils.fillZero(String.valueOf(btCase.getId()), 5);
		btCase.setManualId(manualId);
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
				} else {
					throw new BadRequestException("Wrong business domain id");
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

		if (statusList.contains(btCase.getStatus().getId())) {
			btCase.setOwner(new EmployeeDetails(user.getUsername()));

		} else {
			if (emailId != null) {
				btCase.setOwnerDetails(new com.pratikshat.exicom.entity.EmployeeDetails(emailId));
			} else {
				throw new com.pratikshat.exicom.exception.BadRequestException("Circle Manager Does not exist");
			}
		}
		em.persist(btCase);
		workflow.executeWorkflow(em, btCase, "INSERT", "POST", user);

		try {
			GlobalSearchIndex _search = new GlobalSearchIndex();
			_search.setKeyName("manualId");
			_search.setObjectDetailsUrl("bat_case/" + btCase.getId());
			_search.setObjectIdName("id");
			_search.setObjectIdValue("" + btCase.getId());
			_search.setObjectName("BtCase");
			_search.setSearchString(manualId);
			em.persist(_search);
		} catch (Exception e) {
		}

		com.pratikshat.exicom.entity.ApplicationNotificationDetails notification = new com.pratikshat.exicom.entity.ApplicationNotificationDetails();
		notification.setNofificationDate(new java.util.Date());
		notification.setNotificationActionUrl("bat_case/" + btCase.getId());
		notification.setNotificationMessage("New Case has been registered With Id " + manualId);
		notification.setNotificationRead(false);
		notification.setUserEmailId(btCase.getOwnerDetails().getEmailId());
		em.persist(notification);

		return btCase;
	}

	@Override
	public BtCase deleteDetails(BtCase btCase, LoginUser user) throws BadRequestException, UnauthoriseException {
		return null;

	}

	@Override
	public BtCase updateDetails(BtCase btCase, LoginUser user) throws BadRequestException, UnauthoriseException {
		BtCase _exits = null;
		try {
			_exits = em.createQuery("select act from BtCase act where act.id=?1", BtCase.class)
					.setParameter(1, btCase.getId()).getSingleResult();
		} catch (NoResultException ex) {
			throw new BadRequestException("case not found");
		} catch (NonUniqueResultException ex) {
			throw new BadRequestException("Multiple Cases found with Id " + btCase.getManualId());
		}
		_exits.setPendingResponsibility(btCase.getPendingResponsibility());//
		_exits.setRemarks(btCase.getRemarks());//
		_exits.setInternalRemark(btCase.getInternalRemark());//
		_exits.setInputFromEng(btCase.getInputFromEng());//
		_exits.setBtAssets(btCase.getBtAssets());//
		_exits.setCaseReason(btCase.getCaseReason());//
		_exits.setCaseType(btCase.getCaseType());//
		_exits.setPODate(btCase.getPODate());//
		_exits.setPONumber(btCase.getPONumber());//
		_exits.setAssignByAssignmentRule(btCase.isAssignByAssignmentRule());//
		_exits.setPriority(btCase.getPriority());//
		_exits.setStage(btCase.getStage());//
		_exits.setMissingAssets(btCase.getMissingAssets());//
		_exits.setNoOfsmr(btCase.getNoOfsmr());//
		_exits.setOrigin(btCase.getOrigin());//
		_exits.setSite(btCase.getSite());//
		_exits.setContactPerson(btCase.getContactPerson());//
		_exits.setEngineer(btCase.getEngineer());//
		_exits.setStatus(btCase.getStatus());//
		_exits.setSynked(btCase.isSynked());//
		_exits.setRJILTT01No(btCase.getRJILTT01No());//
		_exits.setServiceCenter(btCase.getServiceCenter());//
		_exits.setOtherProblemReported(btCase.getOtherProblemReported());///
		_exits.setCloseDate(btCase.getCloseDate());//
		_exits.setClosedBy(btCase.getClosedBy());//
		_exits.setAssetsTypeChoice(btCase.getAssetsTypeChoice());
		_exits.setInvoiceNumber(btCase.getInvoiceNumber());
		_exits.setInvoiceDate(btCase.getInvoiceDate());
		_exits.setVendorInvoiceDate(btCase.getVendorInvoiceDate());
		_exits.setVendorInvoiceNumber(btCase.getVendorInvoiceNumber());
		_exits.setPoamount(btCase.getPoamount());
		_exits.setLineitem(btCase.getLineitem());
		_exits.setBtProblemMaster(btCase.getBtProblemMaster());
		_exits.setRma(btCase.isRma());
		_exits.setJmsNo(btCase.getJmsNo());
		_exits.setPodFile(btCase.getPodFile());
		workflow.executeWorkflow(em, _exits, "UPDATE", "PRE", user);
		em.persist(_exits);
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
				} else {
					throw new BadRequestException("Wrong business domain id");
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

		if (statusList.contains(btCase.getStatus().getId())) {
			_exits.setOwner(new EmployeeDetails(user.getUsername()));

		} else {
			if (emailId != null) {
				_exits.setOwnerDetails(new com.pratikshat.exicom.entity.EmployeeDetails(emailId));
			} else {
				throw new com.pratikshat.exicom.exception.BadRequestException("Circle Manager Does not exist");
			}
		}
		em.persist(_exits);
		workflow.executeWorkflow(em, _exits, "UPDATE", "POST", user);
		return null;
	}

	@Override
	public BtCase getAppointment(long id) throws BadRequestException {
		BtCase BtCase = null;
		try {
			Query caseQuery = em.createQuery("select ca from BtCase ca left join ca.status cs left join ca.caseType ct"
					+ " left join ca.stage cst left join ca.caseReason crs left join ca.businessDomain bd left join ca.btAssets ast"
					+ " left join ast.account acn left join ca.site st left join st.circle cl left join ca.ownerDetails onr left join ca.createdBy cby"
					+ " left join ca.engineer eng left join eng.contact econ left join ca.serviceCenter scr where ca.id=?1",
					BtCase.class);
			caseQuery.setParameter(1, id);
			BtCase = (BtCase) caseQuery.getSingleResult();
		} catch (NoResultException ex) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Case does not exist");
		}
		return BtCase;
	}

	@Override
	public BtCase getCaseForService(long id) throws BadRequestException {
		BtCase BtCase = null;
		try {

			BtCase = em.createQuery(
					"select cs from BtServiceAppointment sa join sa.caseDetails cs left join cs.caseReason rs left join cs.caseType ct left join cs.btAssets ast left join cs.businessDomain bsd left join ast.products prd left join cs.site st where sa.serviceAppointmentId=?1",
					BtCase.class).setParameter(1, id).getSingleResult();

		} catch (javax.persistence.NoResultException ex) {
			throw new BadRequestException("Case details not found");
		}
		return BtCase;
	}

	@Override
	public void UpdateCase(int domainId, long caseId, long resonId, LoginUser user) throws BadRequestException {
		Query updateCase = em.createQuery("update BtCase cs set cs.stage=?1 where cs.id=?2");
//		updateCase.setParameter(1, new CaseStatusDetails(2)); // closed
		List<Long> reasonList = new ArrayList<>();

		if (domainId == 1) { // Battery
			reasonList.add(5L); // Paid Repair - Battery
			reasonList.add(27L); // Paid Repair - Battery
			reasonList.add(7L); // Audit - Battery
			reasonList.add(20L); // Audit - Battery
			reasonList.add(23L); // Audit - Battery
			reasonList.add(8L); // Installation - Battery

			if (reasonList.contains(resonId)) {
				// CaseStageDetails stage = getCaseStage(_caseStageDetails, "Document Awaited -
				// Customer");
				updateCase.setParameter(1, new CaseStageDetails(17)); // Document Awaited - Customer
			} else {
				// CaseStageDetails stage = getCaseStage(_caseStageDetails, "Resolved");
				updateCase.setParameter(1, new CaseStageDetails(2)); // Resolved
			}
			updateCase.setParameter(2, caseId).executeUpdate();
		}

	}

	@Override
	public BtCase getCaseWihtId(long id) throws BadRequestException {
		BtCase _caseDetails = null;
		try {
			_caseDetails = em.createQuery(
					"select ca from BtCase ca left join ca.status cs left join ca.btAssets ast left join ast.account acn left join ca.site st left join ca.ownerDetails onr left join ca.engineer eng left join eng.contact econ left join ca.serviceCenter scr where ca.id=?1",
					BtCase.class).setParameter(1, id).getSingleResult();
		} catch (NoResultException ex) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Case does not exist");
		}
		return _caseDetails;
	}

	@Override
	public BtCase getCaseUsingPerCase(long id) throws BadRequestException {
		BtCase btCase = null;
		try {
			btCase = em.createQuery("select ca from BtCase ca left join ca.prc pc where pc.id=?1", BtCase.class)
					.setParameter(1, id).getSingleResult();
		} catch (Exception e) {

		}

		return btCase;
	}

	@Override
	public List<BtCase> getCaseWithWcc(BtWccDetails wcc) throws BadRequestException {
		List<BtCase> btcase = null;
		try {
			btcase = em.createQuery(
					"select csd from BtWccServiceAppointment wca join wca.serviceAppointment sa join sa.caseDetails csd where wca.wcc=?1",
					BtCase.class).setParameter(1, wcc).getResultList();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new BadRequestException("Unable to get case details");
		}
		return btcase;
	}

	@Override
	public BtCaseProblemReported saveDetails(BtCaseProblemReported t, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		workflow.executeWorkflow(em, t, "INSERT", "PRE", user);
		em.persist(t);
		workflow.executeWorkflow(em, t, "INSERT", "POST", user);
		return null;
	}

	@Override
	public void deleteCaseProblemReported(long id) {
		Query deleteProblemId = em.createQuery("delete BtCaseProblemReported pr where pr.caseDetails.id=?1");
		deleteProblemId.setParameter(1, id);
		deleteProblemId.executeUpdate();
	}

	@Override
	public EmployeeDetails employeeDetails(String email) throws BadRequestException {
		EmployeeDetails caseCreatedBy = null;
		try {
			Query getUserDetailsQuery = em.createQuery(
					"select emp from EmployeeDetails emp left join ContactDetails ct on emp.emailId=ct.emailId where emp.emailId=?1");
			getUserDetailsQuery.setParameter(1, email);
			caseCreatedBy = (com.pratikshat.exicom.entity.EmployeeDetails) getUserDetailsQuery.getSingleResult();
		} catch (NoResultException ex) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Created by does not exist");
		}
		return caseCreatedBy;
	}

	@Override
	public BtCase getCaseForInvoiceUpdate(String manualId) throws BadRequestException, UnauthoriseException {
		BtCase _caseDetails = null;
		try {
			Query selectCaseDetailsQuery = em.createQuery(
					"select ca from BtCase ca left join ca.status cs left join ca.caseType cty left join ca.caseReason crs left join ca.stage stg where ca.manualId=?1",
					BtCase.class);
			selectCaseDetailsQuery.setParameter(1, manualId);
			_caseDetails = (BtCase) selectCaseDetailsQuery.getSingleResult();
		} catch (NoResultException ex) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Case does not exist");
		} catch (NonUniqueResultException e) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Multiple Case exist");
		} catch (Exception ex) {
			throw new com.pratikshat.exicom.exception.BadRequestException(ex.getMessage());
		}
		return _caseDetails;
	}

	@Override
	public BtCase findByCaseManualId(String manualId) throws BadRequestException {
		BtCase btCase = null;
		try {
			btCase = (BtCase) em.createQuery(
					"select ca from BtCase ca left join ca.btAssets ast left join ca.site st left join st.account acn left join st.primaryAccount pacn left join ca.ownerDetails onr left join ca.engineer eng left join eng.contact econ left join econ.account engact left join ca.serviceCenter scr left join ca.prc prc where ca.manualId=?1")
					.setParameter(1, manualId).getSingleResult();

		} catch (NoResultException ex) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Case does not exist");
		} catch (NonUniqueResultException e) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Multiple Case exist");
		} catch (Exception ex) {
			throw new com.pratikshat.exicom.exception.BadRequestException(ex.getMessage());
		}
		return btCase;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = {
			BadRequestException.class, UnauthoriseException.class, RuntimeException.class, Exception.class })
	public RejectCaseRequest rejectCase(RejectCaseRequest rejectCaseRequest, LoginUser user)
			throws BadRequestException, UnauthoriseException {

		Map<String, String> group = dbProfile.getAccountAndRoleGroup(user);
		System.out.println(group.get("roleGroup") + " group");
		if (group.get("roleGroup").equalsIgnoreCase("ADMIN")) {
			BtCase _caseDetails = null;
			try {
				Query selectCaseDetailsQuery = em.createQuery(
						"select ca from BtCase ca left join ca.status cs left join ca.caseType cty left join ca.caseReason crs where ca.manualId=?1",
						BtCase.class);
				selectCaseDetailsQuery.setParameter(1, rejectCaseRequest.getCaseManualId().trim());
				_caseDetails = (BtCase) selectCaseDetailsQuery.getSingleResult();
			} catch (NoResultException ex) {
				throw new BadRequestException("Case does not exist");
			} catch (NonUniqueResultException e) {
				throw new BadRequestException("Multiple Case exist");
			} catch (Exception ex) {
				throw new BadRequestException(ex.getMessage());
			}

			if (_caseDetails == null) {
				throw new BadRequestException("Case does not exist");
			}

			// New //In-Process
			if (_caseDetails.getStatus().getId() == 1 || _caseDetails.getStatus().getId() == 3) {
				List<BtServiceAppointment> serviceAppointmentList = null;
				try {
					////
					Query selectSAQuery = em.createQuery(
							"select sa from BtServiceAppointment sa left join sa.caseDetails cd left join sa.status st where cd.id=?1",

							BtServiceAppointment.class);
					selectSAQuery.setParameter(1, _caseDetails.getId());
					serviceAppointmentList = (List<BtServiceAppointment>) selectSAQuery.getResultList();
				} catch (NoResultException ex) {

				} catch (Exception ex) {
					ex.printStackTrace();
					throw new BadRequestException(ex.getMessage());
				}
				boolean isComplete = false;
				if (serviceAppointmentList != null) {
					for (int i = 0; i < serviceAppointmentList.size(); i++) {

						BtServiceAppointment sa = serviceAppointmentList.get(i);
						if (sa.getStatus().getStatusId() == 4) { // Completed
							isComplete = true;
						}

					}
				}
				/////
				if (!isComplete) {
					_caseDetails.setStatus(new CaseStatusDetails(4)); // Rejected
					_caseDetails.setStage(new CaseStageDetails(16)); // Rejected
					_caseDetails.setLastModifiedBy(new EmployeeDetails(user.getUsername()));
					_caseDetails.setLastModifiedDate(new Date());

					updateDetails(_caseDetails, user);

					if (serviceAppointmentList != null) {
						for (int i = 0; i < serviceAppointmentList.size(); i++) {

							BtServiceAppointment sa = serviceAppointmentList.get(i);

							sa.setStatus(new ServiceAppointmentStatus(8)); // Rejected
							sa.setLastModifiedBy(new EmployeeDetails(user.getUsername()));
							sa.setLastModifiedDate(new Date());

							dbServiceApp.updateDetails(sa, user);

						}
					}

					BtCaseRejectLog log = new BtCaseRejectLog();
					log.setCaseManualId(rejectCaseRequest.getCaseManualId().trim());
					log.setRemark(rejectCaseRequest.getRemark().trim());
					log.setFile(rejectCaseRequest.getFile());

					log.setCreatedBy(new EmployeeDetails(user.getUsername()));
					log.setCreatedDate(new Date());

					dbCaseRejectRepo.updateDetails(log, user);
				} else {
					throw new BadRequestException("You can not reject this record");
				}
			} else if (_caseDetails.getStatus().getId() == 2) { // Closed

				List<Integer> list = new ArrayList<>();
				list.add(18); // Document Awaited - Customer
				list.add(22); // Document in-Transit
				list.add(9); // To Be Invoiced

				if (list.contains(_caseDetails.getStage().getId())) {
					_caseDetails.setStage(new CaseStageDetails(23)); // Not To Be Invoiced
					_caseDetails.setLastModifiedBy(new EmployeeDetails(user.getUsername()));
					_caseDetails.setLastModifiedDate(new Date());

					updateDetails(_caseDetails, user);

					BtCaseRejectLog log = new BtCaseRejectLog();
					log.setCaseManualId(rejectCaseRequest.getCaseManualId().trim());
					log.setRemark(rejectCaseRequest.getRemark().trim());
					log.setFile(rejectCaseRequest.getFile());

					log.setCreatedBy(new EmployeeDetails(user.getUsername()));
					log.setCreatedDate(new Date());

					dbCaseRejectRepo.updateDetails(log, user);
				} else {
					throw new BadRequestException("You can not reject this record");
				}
			} else {
				throw new BadRequestException("You can not reject this record");
			}
		} else if (group.get("roleGroup").equalsIgnoreCase("CUSTOMERCARE")) {
			BtCase _caseDetails = null;
			try {
				Query selectCaseDetailsQuery = em.createQuery(
						"select ca from BtCase ca left join ca.status cs left join ca.caseType cty left join ca.caseReason crs where ca.manualId=?1",
						BtCase.class);
				selectCaseDetailsQuery.setParameter(1, rejectCaseRequest.getCaseManualId().trim());
				_caseDetails = (BtCase) selectCaseDetailsQuery.getSingleResult();
			} catch (NoResultException ex) {
				throw new BadRequestException("Case does not exist");
			} catch (NonUniqueResultException e) {
				throw new BadRequestException("Multiple Case exist");
			} catch (Exception ex) {
				throw new BadRequestException(ex.getMessage());
			}

			if (_caseDetails == null) {
				throw new BadRequestException("Case does not exist");
			}
			System.out.println(_caseDetails.getStatus().getId()+" is id");
			// New //In-Process
			if (_caseDetails.getStatus().getId() == 1 || _caseDetails.getStatus().getId() == 3) {
				List<BtServiceAppointment> serviceAppointmentList = null;
				try {
					Query selectSAQuery = em.createQuery(

							"select sa from BtServiceAppointment sa left join sa.caseDetails cd left join sa.status st where cd.id=?1",
							BtServiceAppointment.class);
					selectSAQuery.setParameter(1, _caseDetails.getId());
					serviceAppointmentList = (List<BtServiceAppointment>) selectSAQuery.getResultList();
				} catch (NoResultException ex) {

				} catch (Exception ex) {
					ex.printStackTrace();
					throw new BadRequestException(ex.getMessage());
				}
				boolean isComplete = false;
				if (serviceAppointmentList != null) {
					for (int i = 0; i < serviceAppointmentList.size(); i++) {

						BtServiceAppointment sa = serviceAppointmentList.get(i);
						if (sa.getStatus().getStatusId() == 4) { // Completed
							isComplete = true;
						}

					}
				}

				if (!isComplete) {
					_caseDetails.setStatus(new CaseStatusDetails(4)); // Rejected
					_caseDetails.setStage(new CaseStageDetails(16)); // Rejected
					_caseDetails.setLastModifiedBy(new EmployeeDetails(user.getUsername()));
					_caseDetails.setLastModifiedDate(new Date());

					updateDetails(_caseDetails, user);

					if (serviceAppointmentList != null) {
						for (int i = 0; i < serviceAppointmentList.size(); i++) {

							BtServiceAppointment sa = serviceAppointmentList.get(i);

							sa.setStatus(new ServiceAppointmentStatus(8)); // Rejected
							sa.setLastModifiedBy(new EmployeeDetails(user.getUsername()));
							sa.setLastModifiedDate(new Date());

							dbServiceApp.updateDetails(sa, user);

						}
					}

					BtCaseRejectLog log = new BtCaseRejectLog();
					log.setCaseManualId(rejectCaseRequest.getCaseManualId().trim());
					log.setRemark(rejectCaseRequest.getRemark().trim());
					log.setFile(rejectCaseRequest.getFile());

					log.setCreatedBy(new EmployeeDetails(user.getUsername()));
					log.setCreatedDate(new Date());

					dbCaseRejectRepo.updateDetails(log, user);
				} else {
					throw new com.pratikshat.exicom.exception.BadRequestException("You can not reject this record");
				}
			} else {
				throw new com.pratikshat.exicom.exception.BadRequestException("You can not reject this record");
			}
		} else if (group.get("roleGroup").equalsIgnoreCase("COMMERCIAL")) {
			BtCase _caseDetails = null;
			try {
				Query selectCaseDetailsQuery = em.createQuery(
						"select ca from BtCase ca left join ca.status cs left join ca.caseType cty left join ca.caseReason crs where ca.manualId=?1",
						BtCase.class);
				selectCaseDetailsQuery.setParameter(1, rejectCaseRequest.getCaseManualId().trim());
				_caseDetails = (BtCase) selectCaseDetailsQuery.getSingleResult();
			} catch (NoResultException ex) {
				throw new BadRequestException("Case does not exist");
			} catch (NonUniqueResultException e) {
				throw new BadRequestException("Multiple Case exist");
			} catch (Exception ex) {
				throw new BadRequestException(ex.getMessage());
			}

			if (_caseDetails == null) {
				throw new BadRequestException("Case does not exist");
			}

			if (_caseDetails.getStatus().getId() == 2) { // Closed

				List<Integer> list = new ArrayList<>();
				list.add(17); // Document Awaited - Customer
				list.add(21); // Document in-Transit
				list.add(3); // To Be Invoiced

				if (list.contains(_caseDetails.getStage().getId())) {
					_caseDetails.setStage(new CaseStageDetails(23)); // Not To Be Invoiced
					_caseDetails.setLastModifiedBy(new EmployeeDetails(user.getUsername()));
					_caseDetails.setLastModifiedDate(new Date());

					updateDetails(_caseDetails, user);

					BtCaseRejectLog log = new BtCaseRejectLog();
					log.setCaseManualId(rejectCaseRequest.getCaseManualId().trim());
					log.setRemark(rejectCaseRequest.getRemark().trim());
					log.setFile(rejectCaseRequest.getFile());

					log.setCreatedBy(new EmployeeDetails(user.getUsername()));
					log.setCreatedDate(new Date());

					em.persist(log);
				} else {
					throw new BadRequestException("You can not reject this record");
				}
			} else {
				throw new BadRequestException("You can not reject this record");
			}
		} else {
			throw new BadRequestException("Your are not authorised to edit this record");
		}

		return rejectCaseRequest;
	}

	@Override
	public List<BtCase> getByStage(String manualId) throws BadRequestException {
		List<BtCase> cases = null;
		try {
			cases = em.createQuery("select ca from BtCase ca left join ca.stage st where st.caseStageName=?1",
					BtCase.class).setParameter(1, manualId).getResultList();
		} catch (Exception e) {

		}
		return cases;
	}

	@Override
	public List<BtCase> getByListId(long[] id) throws BadRequestException {
		List<BtCase> cases = null;
		try {
			cases = em.createQuery("select ca from BtCase ca left join ca.stage st where ca.id in id=?1",
					BtCase.class).setParameter(1, id).getResultList();
		} catch (Exception e) {

		}
		return cases;
	}

	@Override
	public BtCase updateDetail(BtCase btCase,MultipartFile file, LoginUser user) throws BadRequestException, UnauthoriseException {
		BtCase _exits = null;
		try {
			_exits = em.createQuery("select act from BtCase act where act.id=?1", BtCase.class)
					.setParameter(1, btCase.getId()).getSingleResult();
		} catch (NoResultException ex) {
			throw new BadRequestException("case not found");
		} catch (NonUniqueResultException ex) {
			throw new BadRequestException("Multiple Cases found with Id " + btCase.getManualId());
		}
		
		
		_exits.setPODate(btCase.getPODate());
		_exits.setPODNumber(btCase.getPODNumber());
		_exits.setPodShippingMode(btCase.getPodShippingMode());
		_exits.setPodShippingExpectedDate(btCase.getPodShippingExpectedDate());
		_exits.setPodShippingToName(btCase.getPodShippingToName());
		_exits.setPodShippingTrackingNumber(btCase.getPodShippingTrackingNumber());
		_exits.setJmsNo(btCase.getJmsNo());
		_exits.setPodFile(btCase.getPodFile());
		workflow.executeWorkflow(em, _exits, "UPDATE", "PRE", user);
		em.persist(_exits);
		
	
		workflow.executeWorkflow(em, _exits, "UPDATE", "POST", user);
		return null;
		
	}

	@Override
	public void fileUpload(UploadedFiles files,MultipartFile file,LoginUser user) throws BadRequestException, UnauthoriseException {
		workflow.executeWorkflow(em, files, "INSERT", "PRE", user);
		em.persist(files);
		workflow.executeWorkflow(em, files, "INSERT", "POST", user);
		
	}

	

}