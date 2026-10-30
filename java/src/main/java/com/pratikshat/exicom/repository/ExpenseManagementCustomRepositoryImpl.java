package com.pratikshat.exicom.repository;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipException;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.pratikshat.exicom.config.FileStorageProperties;
import com.pratikshat.exicom.entity.Approval;
import com.pratikshat.exicom.entity.ApprovalSteps;
import com.pratikshat.exicom.entity.BusinessDomain;
import com.pratikshat.exicom.entity.ContactDetails;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.ExpenseAccountsOnly;
import com.pratikshat.exicom.entity.ExpenseManagement;
import com.pratikshat.exicom.entity.ExpenseManagementBusinessArea;
import com.pratikshat.exicom.entity.ExpenseManagementBusinessPlace;
import com.pratikshat.exicom.entity.ExpenseManagementCostCenter;
import com.pratikshat.exicom.entity.ExpenseManagementExpensePurpose;
import com.pratikshat.exicom.entity.ExpenseManagementExpenseType;
import com.pratikshat.exicom.entity.ExpenseManagementStatus;
import com.pratikshat.exicom.entity.LocalConveyanceExpense;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.Quote;
import com.pratikshat.exicom.entity.QuotePdfHeaderAndFooter;
import com.pratikshat.exicom.entity.QuoteTermsAndConditions;
import com.pratikshat.exicom.entity.ServiceAppointment;
import com.pratikshat.exicom.entity.StateWithQuotePdf;
import com.pratikshat.exicom.entity.TaxAndVisitCharges;
import com.pratikshat.exicom.entity.ToBeApproved;
import com.pratikshat.exicom.entity.UploadedFiles;
import com.pratikshat.exicom.event.ExpenseHeaderFooterPageEvent;
import com.pratikshat.exicom.event.QuoteHeaderFooterPageEvent;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.Contacts;
import com.pratikshat.exicom.model.request.ExpenseAccountsOnlyRequest;
import com.pratikshat.exicom.model.request.ExpenseManagementRequest;
import com.pratikshat.exicom.model.request.LocalConveyanceExpenseRequest;
import com.pratikshat.exicom.model.response.BoardingExpenseResponse;
import com.pratikshat.exicom.model.response.ExpenseManagementResponse;
import com.pratikshat.exicom.model.response.GlCodeResponse;
import com.pratikshat.exicom.model.response.LocalConveyanceExpenseResponse;
import com.pratikshat.exicom.model.response.LodgingExpenseResponse;
import com.pratikshat.exicom.model.response.OtherChargesExpenseResponse;
import com.pratikshat.exicom.model.response.QuoteLineItemResponse;
import com.pratikshat.exicom.model.response.ServiceAppointmentResponse;
import com.pratikshat.exicom.model.response.TourExpenseResponse;
import com.pratikshat.exicom.model.response.UserResponse;
import com.pratikshat.exicom.utils.ExpensePDFGeneration;

@Repository
public class ExpenseManagementCustomRepositoryImpl implements ExpenseManagementCustomRepository {

	@PersistenceContext
	EntityManager entityManager;
	
	@Autowired
	com.pratikshat.exicom.interceptor.WorkflowExecuter workflow;
	
	@Autowired
	private SimpMessagingTemplate messagingTemplate;
	
	@Autowired
	FileStorageProperties fileStorageProperties;
	
	@Autowired
	UserProfileRepository dbProfile;

	@Transactional(isolation = Isolation.READ_UNCOMMITTED, rollbackFor = { Exception.class,BadRequestException.class,UnauthoriseException.class,
			RuntimeException.class }, propagation = Propagation.REQUIRES_NEW)
	@Override
	public ExpenseManagementRequest saveExpense(ExpenseManagementRequest request, LoginUser user) throws Exception {
		//SimpleDateFormat dateFormatInput = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//SimpleDateFormat dateFormatOutput = new SimpleDateFormat("yyyy-MM-dd");
		if (request != null) {

			ContactDetails contactDetails = null;
			try {
				Query getQuery = entityManager.createQuery(
						"select cd from ContactDetails cd where cd.emailId=?1", ContactDetails.class);
				getQuery.setParameter(1, user.getUsername());
				contactDetails = (ContactDetails) getQuery.getSingleResult();
				
			}catch(NoResultException ex) {
				throw new BadRequestException("Contact does not exist");
			}
			
			if(contactDetails==null) {
				throw new BadRequestException("Contact does not exist");
			}
			
			ExpenseManagement expenseManagement = new ExpenseManagement();
			//expenseManagement.setAdvanceAmount(request.getAdvanceAmount());
			if (request.getBusinessAreaId() != 0) {
				expenseManagement.setBusinessArea(new ExpenseManagementBusinessArea(request.getBusinessAreaId()));
			} else {
				expenseManagement.setBusinessArea(null);
			}

			if (request.getBusinessPlaceId() != 0) {
				expenseManagement.setBusinessPlace(new ExpenseManagementBusinessPlace(request.getBusinessPlaceId()));
			} else {
				expenseManagement.setBusinessPlace(null);
			}
			
			if(request.getBusinessDomainID()==1) {
				expenseManagement.setStatus(new ExpenseManagementStatus(1));
			}else if(request.getBusinessDomainID()==2) {
				expenseManagement.setStatus(new ExpenseManagementStatus(8));
			}else if(request.getBusinessDomainID()==3) {
				expenseManagement.setStatus(new ExpenseManagementStatus(15));
			}
			
			expenseManagement.setBusinessDomain(new BusinessDomain(request.getBusinessDomainID()));
			expenseManagement.setCostCenter(new ExpenseManagementCostCenter(request.getCostCenterId()));
			expenseManagement.setCreatedBy(new EmployeeDetails(user.getUsername()));
			expenseManagement.setCreatedDate(new Date());
			//expenseManagement.setDocumentDate(new Date());
			expenseManagement.setContact(contactDetails);
			expenseManagement.setExpensePurpose(new ExpenseManagementExpensePurpose(request.getExpensePurposeId()));
			expenseManagement.setExpenseSynced(false);
			expenseManagement.setModifiedBy(new EmployeeDetails(user.getUsername()));
			expenseManagement.setExpenseType(new ExpenseManagementExpenseType(request.getExpenseTypeId()));
			expenseManagement.setModifiedDate(new Date());
			//expenseManagement.setNarration(request.getNarration());
			expenseManagement.setPost(false);
			expenseManagement.setPurposeOfVisit(request.getPurposeOfVisit());
			expenseManagement.setRemark(request.getRemark());
			expenseManagement.setSynced(false);

//			if(request.getTourEndDate()!=null && request.getTourStartDate()!=null) {
//				
//				Date tourEndDate = dateFormatOutput.parse(dateFormatInput.format(request.getTourEndDate()));
//				Date tourStartDate = dateFormatOutput.parse(dateFormatInput.format(request.getTourStartDate()));
//
//				expenseManagement.setTourEndDate(tourEndDate);
//				expenseManagement.setTourEndTime(request.getTourEndTime());
//				expenseManagement.setTourStartDate(tourStartDate);
//				expenseManagement.setTourStartTime(request.getTourStartTime());
//
//				String tourStart = dateFormatOutput.format(tourStartDate) + " " + request.getTourStartTime();
//				Date tourSDate = dateFormatOutput.parse(tourStart);
//
//				String tourEnd = dateFormatOutput.format(tourEndDate) + " " + request.getTourEndTime();
//				Date tourEDate = dateFormatOutput.parse(tourEnd);
//
//				long diff = tourEDate.getTime() - tourSDate.getTime();
//				double tourDays = 0;
//
//				long diffHours = diff / (60 * 60 * 1000) % 24;
//				long diffDays = diff / (24 * 60 * 60 * 1000);
//
//				tourDays = diffDays;
//				if (diffHours >= 6 && diffHours < 12) {
//					tourDays = tourDays + 0.5;
//				} else if (diffHours >= 12) {
//					tourDays = tourDays + 1;
//				}
//
//				expenseManagement.setTotalTourDays(tourDays);
//				expenseManagement.setEligibleTourDays(tourDays);
//				
//			}
			
			//expenseManagement.setVisibleToFinance(request.getVisibleToFinance());
			expenseManagement.setOwner(new EmployeeDetails(user.getUsername()));
			workflow.executeWorkflow(entityManager, expenseManagement, "INSERT", "PRE", user);
			entityManager.persist(expenseManagement);
			workflow.executeWorkflow(entityManager, expenseManagement, "INSERT", "POST", user);
			Query updateManualId = entityManager.createQuery(
					"update ExpenseManagement em set em.expenseManagementManualId=?1 where em.expenseManagementId=?2");
			SimpleDateFormat dateformatter = new SimpleDateFormat("yyyyMMdd");
//			String manualId = "EM-" + dateformatter.format(new java.util.Date()) + "-"
//					+ com.pratikshat.exicom.config.MiscellaneousUtils
//							.fillZero(String.valueOf(expenseManagement.getExpenseManagementId()), 5);
			
			String manualId = "EM-"+ com.pratikshat.exicom.config.MiscellaneousUtils.fillZero(String.valueOf(expenseManagement.getExpenseManagementId()), 7);
			updateManualId.setParameter(1, manualId);
			updateManualId.setParameter(2, expenseManagement.getExpenseManagementId());
			updateManualId.executeUpdate();

			request.setExpenseManagementId(expenseManagement.getExpenseManagementId());

			// search index entry
			try {
				com.pratikshat.exicom.entity.GlobalSearchIndex _search = new com.pratikshat.exicom.entity.GlobalSearchIndex();
				_search.setKeyName("expenseManagementManualId");
				_search.setObjectDetailsUrl("expensesmanagement/" + expenseManagement.getExpenseManagementId());
				_search.setObjectIdName("expenseManagementId");
				_search.setObjectIdValue("" + expenseManagement.getExpenseManagementId());
				_search.setObjectName("ExpenseManagement");
				_search.setSearchString(manualId);
				entityManager.persist(_search);

			} catch (Exception e) {
			}
			entityManager.flush();
			entityManager.clear();
			return request;

		} else {
			throw new Exception("Request Parameter Null");
		}
	}

	@Transactional(isolation = Isolation.READ_UNCOMMITTED, rollbackFor = { Exception.class,BadRequestException.class,UnauthoriseException.class,
			RuntimeException.class }, propagation = Propagation.REQUIRES_NEW)
	@Override
	public ExpenseManagementRequest updateExpense(ExpenseManagementRequest request, LoginUser user) throws Exception {
		//SimpleDateFormat dateFormatInput = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//SimpleDateFormat dateFormatOutput = new SimpleDateFormat("yyyy-MM-dd");
		if (request != null) {
			ContactDetails contactDetails = null;
			try {
				Query getQuery = entityManager.createQuery(
						"select cd from ContactDetails cd where cd.emailId=?1", ContactDetails.class);
				getQuery.setParameter(1, user.getUsername());
				contactDetails = (ContactDetails) getQuery.getSingleResult();
				
			}catch(NoResultException ex) {
				throw new BadRequestException("Contact does not exist");
			}
			
			if(contactDetails==null) {
				throw new BadRequestException("Contact does not exist");
			}
			
			Query getQuery = entityManager.createQuery(
					"select em from ExpenseManagement em join em.status st left join em.owner own where em.isDeleted=false and em.expenseManagementId=?1", ExpenseManagement.class);
			getQuery.setParameter(1, request.getExpenseManagementId());
			ExpenseManagement expenseManagement = (ExpenseManagement) getQuery.getSingleResult();

			if (expenseManagement != null) {
				ExpenseManagement expense = null;
				if(expenseManagement.getOwner().getEmailId().equalsIgnoreCase(user.getUsername())) {
					
					int statusId = expenseManagement.getStatus().getStatusId();
					List<Integer> list = new ArrayList<>();
					list.add(1);	// Draft from Battery Division 
					list.add(7);	// Rejected from Battery Division 
					list.add(8);	// Draft from DCT Division 
					list.add(14);	// Rejected from DCT Division
					list.add(15);	// Draft from EV Division
					list.add(16);	// Rejected by Circle Manager from Battery Division
					list.add(17);	// Rejected by Zonal Manager from Battery Division
					list.add(18);	// Rejected by HOD from Battery Division
					list.add(19);	// Rejected by Finance from Battery Division
					list.add(20);	// Rejected by Circle Manager from DCT Division
					list.add(21);	// Rejected by Zonal Manager from DCT Division
					list.add(22);	// Rejected by HOD from DCT Division
					list.add(23);	// Rejected by Finance from DCT Division
					if(!list.contains(statusId)) {
						throw new BadRequestException("You can't able to edit this record");
					}else {
						expense = updateExpenseWithOwner(expenseManagement, request, user, entityManager);
						//expense.setContact(contactDetails);
					}
					//throw new BadRequestException("You are not authorised to edit this record");
				}else {
					
					ToBeApproved _toBeApproved=null;
					try {
						_toBeApproved=entityManager.createQuery("select apr from ToBeApproved apr join apr.assignedTo asg where apr.ObjectName=?1 and apr.objectId=?2 and apr.status=?3",ToBeApproved.class)
								.setParameter(1,"ExpenseManagement")
								.setParameter(2,expenseManagement.getExpenseManagementId())
								.setParameter(3,false)
								.getSingleResult();
					}catch(javax.persistence.NoResultException ex) {
						throw new BadRequestException("You are not authorised");
					}catch(javax.persistence.NonUniqueResultException ex) {
						throw new BadRequestException("You are not authorised");
					}
					
					if(_toBeApproved.getAssignedTo().getEmailId().equalsIgnoreCase(user.getUsername())||dbProfile.samroleUsers(_toBeApproved.getAssignedTo().getEmailId(), user.getUsername())){
						expense = updateExpenseOtherThanOwner(expenseManagement, request,expenseManagement.getOwner().getEmailId() , user, entityManager);
						//expense.setContact(contactDetails);
					}else {
						
						throw new BadRequestException("You are not authorised");
					}
				}
				
				
				//expenseManagement.setVisibleToFinance(request.getVisibleToFinance());
				workflow.executeWorkflow(entityManager, expense, "INSERT", "PRE", user);
				entityManager.persist(expense);
				workflow.executeWorkflow(entityManager, expense, "INSERT", "POST", user);
				entityManager.flush();
				entityManager.clear();
				return request;
			} else {
				throw new Exception("No data found to save details");
			}
		} else {
			throw new Exception("Request Parameter Null");
		}
	}
	
	private ExpenseManagement updateExpenseWithOwner(ExpenseManagement expenseManagement, ExpenseManagementRequest request, LoginUser user, EntityManager entityManager)throws Exception {
		
		expenseManagement.setAdvanceAmount(request.getAdvanceAmount());
		if (request.getBusinessAreaId() != 0) {
			expenseManagement.setBusinessArea(new ExpenseManagementBusinessArea(request.getBusinessAreaId()));
		} else {
			expenseManagement.setBusinessArea(null);
		}

		if (request.getBusinessPlaceId() != 0) {
			expenseManagement
					.setBusinessPlace(new ExpenseManagementBusinessPlace(request.getBusinessPlaceId()));
		} else {
			expenseManagement.setBusinessPlace(null);
		}
		
		expenseManagement.setCostCenter(new ExpenseManagementCostCenter(request.getCostCenterId()));
		expenseManagement.setExpensePurpose(new ExpenseManagementExpensePurpose(request.getExpensePurposeId()));
		expenseManagement.setExpenseSynced(false);
		expenseManagement.setModifiedBy(new EmployeeDetails(user.getUsername()));
		expenseManagement.setExpenseType(new ExpenseManagementExpenseType(request.getExpenseTypeId()));
		expenseManagement.setModifiedDate(new Date());
		//expenseManagement.setNarration(request.getNarration());
		expenseManagement.setPost(false);
		expenseManagement.setPurposeOfVisit(request.getPurposeOfVisit());
		expenseManagement.setRemark(request.getRemark());
		expenseManagement.setSynced(false);
		return expenseManagement;
	}
	
	private ExpenseManagement updateExpenseOtherThanOwner(ExpenseManagement expenseManagement, ExpenseManagementRequest request,String ownerEmailId, LoginUser user, EntityManager entityManager)throws Exception {
		
		
		expenseManagement.setAdvanceAmount(request.getAdvanceAmount());
		if (request.getBusinessAreaId() != 0) {
			expenseManagement.setBusinessArea(new ExpenseManagementBusinessArea(request.getBusinessAreaId()));
		} else {
			expenseManagement.setBusinessArea(null);
		}

		if (request.getBusinessPlaceId() != 0) {
			expenseManagement
					.setBusinessPlace(new ExpenseManagementBusinessPlace(request.getBusinessPlaceId()));
		} else {
			expenseManagement.setBusinessPlace(null);
		}
		
		expenseManagement.setCostCenter(new ExpenseManagementCostCenter(request.getCostCenterId()));
		expenseManagement.setExpensePurpose(new ExpenseManagementExpensePurpose(request.getExpensePurposeId()));
		expenseManagement.setExpenseSynced(false);
		expenseManagement.setModifiedBy(new EmployeeDetails(user.getUsername()));
		expenseManagement.setExpenseType(new ExpenseManagementExpenseType(request.getExpenseTypeId()));
		expenseManagement.setModifiedDate(new Date());
		//expenseManagement.setNarration(request.getNarration());
		expenseManagement.setPost(false);
		expenseManagement.setPurposeOfVisit(request.getPurposeOfVisit());
		expenseManagement.setRemark(request.getRemark());
		expenseManagement.setSynced(false);
		return expenseManagement;
	}

	@Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRES_NEW, rollbackFor = {
			Exception.class,BadRequestException.class,UnauthoriseException.class, RuntimeException.class, SQLException.class })
	@Override
	public void submitForApproval(long expenseManagementId,String remark, int businessDomainId, LoginUser user) throws Exception {
		ExpenseManagement expenseManagement = null;
		try {
			Query getQuery = entityManager.createQuery(
					"select em from ExpenseManagement em left join em.status st left join em.owner own where em.expenseManagementId=?1",
					ExpenseManagement.class);
			getQuery.setParameter(1, expenseManagementId);
			expenseManagement = (ExpenseManagement) getQuery.getSingleResult();

		} catch (javax.persistence.NoResultException ex) {
			throw new Exception("Expense Management does not exist");
		}
		if(!expenseManagement.getOwner().getEmailId().equalsIgnoreCase(user.getUsername())) {
			throw new Exception("You are not authorised to submit");
		}
		
		int statusId = expenseManagement.getStatus().getStatusId();
		List<Integer> list = new ArrayList<>();
		list.add(1);	// Draft from Battery Division 
		list.add(7);	// Rejected from Battery Division 
		list.add(8);	// Draft from DCT Division 
		list.add(14);	// Rejected from DCT Division
		list.add(15);	// Draft from EV Division
		list.add(16);	// Rejected by Circle Manager from Battery Division
		list.add(17);	// Rejected by Zonal Manager from Battery Division
		list.add(18);	// Rejected by HOD from Battery Division
		list.add(19);	// Rejected by Finance from Battery Division
		list.add(20);	// Rejected by Circle Manager from DCT Division
		list.add(21);	// Rejected by Zonal Manager from DCT Division
		list.add(22);	// Rejected by HOD from DCT Division
		list.add(23);	// Rejected by Finance from DCT Division
		if(!list.contains(statusId)) {
			throw new BadRequestException("You can't submit this record for approval");
		}
		
		
		if (businessDomainId == 1) { // Battery
			expenseManagement.setStatus(new ExpenseManagementStatus(2));
		} else if (businessDomainId == 2) { // DCT
			expenseManagement.setStatus(new ExpenseManagementStatus(9));
		} else if (businessDomainId == 3) { // EV

		}
		expenseManagement.setStatus(new com.pratikshat.exicom.entity.ExpenseManagementStatus(2));
		entityManager.persist(expenseManagement);

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
					com.pratikshat.exicom.entity.Approval.class).setParameter(1, "ExpenseManagement")
					.setParameter(2, true).setParameter(3, businessDomainId)
					.getResultList();

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
			_toBeApproved.setObjectId(expenseManagement.getExpenseManagementId());
			_toBeApproved.setObjectName("ExpenseManagement");
			_toBeApproved.setObjectId(expenseManagement.getExpenseManagementId());
			_toBeApproved.setObjectManualId(expenseManagement.getExpenseManagementManualId());
			_toBeApproved.setObjectUrl("/expensesmanagement/" + expenseManagement.getExpenseManagementId());
			if(remark!=null && !remark.equalsIgnoreCase("")) {
				_toBeApproved.setPreviousRemarks(remark);
			}else {
				_toBeApproved.setPreviousRemarks(null);	
			}
			_toBeApproved.setRemark("");
			_toBeApproved.setApprovalRuleId(_appliedRule.getId());
			_toBeApproved.setStage(1);
			_toBeApproved.setStatus(false);
			
			workflow.executeWorkflow(entityManager, _toBeApproved, "INSERT", "PRE", user);
			entityManager.persist(_toBeApproved);
			workflow.executeWorkflow(entityManager, _toBeApproved, "INSERT", "POST", user);

			com.pratikshat.exicom.entity.ApplicationNotificationDetails notificationDetails = new com.pratikshat.exicom.entity.ApplicationNotificationDetails();
			notificationDetails.setNofificationDate(new java.util.Date());
			notificationDetails.setNotificationActionUrl("/itemtobeapproved");
			notificationDetails
					.setNotificationMessage(String.format("Expense %s has been send to you for approval",
							expenseManagement.getExpenseManagementManualId()));
			notificationDetails.setNotificationRead(false);
			notificationDetails.setUserEmailId(assigneUserEmailId);

			entityManager.persist(notificationDetails);

			try {
				messagingTemplate.convertAndSend("/topic/" + assigneUserEmailId, notificationDetails);
			} catch (Exception ex) {
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new BadRequestException("Unable to submit request for approval");

		}

		entityManager.flush();
		entityManager.clear();
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
	
	
	@Transactional(isolation=Isolation.READ_UNCOMMITTED,rollbackFor = {Exception.class,BadRequestException.class,UnauthoriseException.class,RuntimeException.class},propagation = Propagation.REQUIRES_NEW)
	@Override
	public ByteArrayInputStream createPdf(LoginUser user, ExpenseManagementResponse response) throws Exception {
		
		ExpensePDFGeneration expensePDFGeneration = new ExpensePDFGeneration(entityManager, fileStorageProperties);
		return expensePDFGeneration.createPdf(user, response);
	}

	@Transactional(isolation=Isolation.READ_UNCOMMITTED,rollbackFor = {Exception.class,BadRequestException.class,UnauthoriseException.class,RuntimeException.class},propagation = Propagation.REQUIRES_NEW)
	@Override
	public List<GlCodeResponse> getGLCodeSummary(long expenseManagementId, LoginUser user)throws Exception {
		List<GlCodeResponse> response = new ArrayList<>();
		
		Query getQuery = entityManager.createQuery(
				"select em from ExpenseManagement em join em.status st left join em.costCenter cc left join em.owner own where em.expenseManagementId=?1", ExpenseManagement.class);
		getQuery.setParameter(1, expenseManagementId);
		ExpenseManagement expenseManagement = (ExpenseManagement) getQuery.getSingleResult();		

		if (expenseManagement != null) {
			
			// Tour Expense
			double totalTourAmountToDisbursed = 0;
			try {
				Query sumTourExpenseQuery = entityManager.createQuery(
						"select coalesce(sum(te.amountToDisbursed),0) as totalAmountToDisbursed from TourExpense te left join te.expenseManagement em where te.isDeleted=false and em.expenseManagementId=?1");
				sumTourExpenseQuery.setParameter(1, expenseManagement.getExpenseManagementId());
				totalTourAmountToDisbursed = (double) sumTourExpenseQuery.getSingleResult();
			} catch (NoResultException ex) {

			}
			
			// Lodging Expense
			double totalLodgingAmountToDisbursed = 0;
			try {
				Query sumLodgingExpenseQuery = entityManager.createQuery(
						"select coalesce(sum(le.amountToDisbursed),0) as totalAmountToDisbursed from LodgingExpense le left join le.expenseManagement em where le.isDeleted=false and em.expenseManagementId=?1");
				sumLodgingExpenseQuery.setParameter(1, expenseManagement.getExpenseManagementId());
				totalLodgingAmountToDisbursed = (double) sumLodgingExpenseQuery.getSingleResult();
			} catch (NoResultException ex) {

			}
			
			// Boarding Expense
			double totalBoardingAmountToDisbursed = 0;
			try {
				Query sumBoardingExpenseQuery = entityManager.createQuery(
						"select coalesce(sum(be.amountToDisbursed),0) as totalAmountToDisbursed from BoardingExpense be left join be.expenseManagement em where be.isDeleted=false and em.expenseManagementId=?1");
				sumBoardingExpenseQuery.setParameter(1, expenseManagement.getExpenseManagementId());
				totalBoardingAmountToDisbursed = (double) sumBoardingExpenseQuery.getSingleResult();
			} catch (NoResultException ex) {

			}
			

			// Local Conveyance
			double totalLocalConveyanceAmountToDisbursed = 0;
			try {
				Query sumLocalConveyanceQuery = entityManager.createQuery(
						"select coalesce(sum(lce.amountToDisbursed),0) as totalAmountToDisbursed from LocalConveyanceExpense lce left join lce.expenseManagement em where lce.isDeleted=false and em.expenseManagementId=?1");
				sumLocalConveyanceQuery.setParameter(1, expenseManagement.getExpenseManagementId());
				totalLocalConveyanceAmountToDisbursed = (double) sumLocalConveyanceQuery.getSingleResult();
			} catch (NoResultException ex) {

			}

			GlCodeResponse rp = new GlCodeResponse(expenseManagement.getCostCenter().getCostCenterId(),
					expenseManagement.getCostCenter().getCostCenterName(),
					totalTourAmountToDisbursed+totalLodgingAmountToDisbursed+totalBoardingAmountToDisbursed+totalLocalConveyanceAmountToDisbursed);
			response.add(rp);
			

			// Other Charges
			List<GlCodeResponse> otherExpense = null;
			try {
				Query sumOtherChargesQuery = entityManager.createQuery(
						"select new com.pratikshat.exicom.model.response.GlCodeResponse(oct.id, oct.name, coalesce(sum(och.amountToDisbursed),0)) from OtherChargesExpense och left join och.expenseManagement em join och.otherChargesType oct where och.isDeleted=false and em.expenseManagementId=?1 group by oct.id");
				sumOtherChargesQuery.setParameter(1, expenseManagement.getExpenseManagementId());
				otherExpense = (List<GlCodeResponse>) sumOtherChargesQuery.getResultList();
			} catch (NoResultException ex) {

			}
			
			if(otherExpense!=null && !otherExpense.isEmpty()) {
				response.addAll(otherExpense);
			}
			
			return response;
		} else {
			throw new Exception("Expense not found");
		}
	}

	@Transactional(isolation=Isolation.READ_UNCOMMITTED,rollbackFor = {Exception.class,BadRequestException.class,UnauthoriseException.class,RuntimeException.class},propagation = Propagation.REQUIRES_NEW)
	@Override
	public ExpenseAccountsOnlyRequest saveUpdateExpenseAccountsOnly(
			ExpenseAccountsOnlyRequest expenseAccountsOnlyRequest, LoginUser user) throws Exception {
		
		
		ExpenseManagement expenseManagement = null;
		try {
			Query getQuery = entityManager.createQuery(
					"select em from ExpenseManagement em join em.status st left join em.owner own where em.expenseManagementId=?1", ExpenseManagement.class);
			getQuery.setParameter(1, expenseAccountsOnlyRequest.getExpenseManagementId());
			expenseManagement = (ExpenseManagement) getQuery.getSingleResult();
		}catch(NoResultException ex) {
			throw new BadRequestException("Expense does not exist");
		}catch(Exception ex) {
			throw new Exception(ex.getMessage());
		}
		
		ExpenseAccountsOnly accountsOnly = null;
		try {
			Query getQuery = entityManager.createQuery(
					"select aco from ExpenseAccountsOnly aco left join ExpenseManagement em on aco.expense=em where aco.expense=?1", ExpenseAccountsOnly.class);
			getQuery.setParameter(1, expenseManagement);
			accountsOnly = (ExpenseAccountsOnly) getQuery.getSingleResult();
		}catch(NoResultException ex) {
			
		}
		
		ToBeApproved _toBeApproved=null;
		try {
			_toBeApproved=entityManager.createQuery("select apr from ToBeApproved apr join apr.assignedTo asg where apr.ObjectName=?1 and apr.objectId=?2 and apr.status=?3",ToBeApproved.class)
					.setParameter(1,"ExpenseManagement")
					.setParameter(2,expenseManagement.getExpenseManagementId())
					.setParameter(3,false)
					.getSingleResult();
		}catch(javax.persistence.NoResultException ex) {
			throw new BadRequestException("You are not authorised");
		}catch(javax.persistence.NonUniqueResultException ex) {
			throw new BadRequestException("You are not authorised");
		}
		if(_toBeApproved.getAssignedTo().getEmailId().equalsIgnoreCase(user.getUsername())||dbProfile.samroleUsers(user.getUsername(), _toBeApproved.getAssignedTo().getEmailId())){
			if(accountsOnly!=null) {
				accountsOnly.setBusinessArea(new ExpenseManagementBusinessArea(expenseAccountsOnlyRequest.getBusinessAreaId()));
				accountsOnly.setBusinessPlace(new ExpenseManagementBusinessPlace(expenseAccountsOnlyRequest.getBusinessPlaceId()));
				accountsOnly.setDocumentDate(expenseAccountsOnlyRequest.getDocumentDate());
				accountsOnly.setNarration(expenseAccountsOnlyRequest.getNarration());
				accountsOnly.setPostPark(expenseAccountsOnlyRequest.isPostPark());
				accountsOnly.setLastModifiedBy(new EmployeeDetails(user.getUsername()));
				accountsOnly.setLastModifiedDate(new Date());
				expenseManagement.setPost(expenseAccountsOnlyRequest.isPostPark());
				entityManager.persist(accountsOnly);
			}else {
				ExpenseAccountsOnly expenseAccountsOnly = new ExpenseAccountsOnly();
				expenseAccountsOnly.setExpense(expenseManagement);
				expenseAccountsOnly.setBusinessArea(new ExpenseManagementBusinessArea(expenseAccountsOnlyRequest.getBusinessAreaId()));
				expenseAccountsOnly.setBusinessPlace(new ExpenseManagementBusinessPlace(expenseAccountsOnlyRequest.getBusinessPlaceId()));
				expenseAccountsOnly.setDocumentDate(expenseAccountsOnlyRequest.getDocumentDate());
				expenseAccountsOnly.setNarration(expenseAccountsOnlyRequest.getNarration());
				
				expenseAccountsOnly.setCreatedBy(new EmployeeDetails(user.getUsername()));
				expenseAccountsOnly.setCreatedDate(new Date());
				
				expenseAccountsOnly.setLastModifiedBy(new EmployeeDetails(user.getUsername()));
				expenseAccountsOnly.setLastModifiedDate(new Date());
				expenseAccountsOnly.setPostPark(expenseAccountsOnlyRequest.isPostPark());
				expenseManagement.setPost(expenseAccountsOnlyRequest.isPostPark());
				entityManager.persist(expenseAccountsOnly);
			}
			return expenseAccountsOnlyRequest;
		}else {
			throw new BadRequestException("You are not authorised");
		}
	}
	
	
	@Override
	public List<ExpenseManagementResponse> advanceSearch(Map<String, Object> filterData, LoginUser user, List<String>users)throws ParseException {
			StringBuilder queryStringBuilder=new StringBuilder();
			queryStringBuilder.append("select new com.pratikshat.exicom.model.response.ExpenseManagementResponse(em,aco) from ExpenseManagement em left join em.businessDomain bd left join em.contact cnt left join cnt.grade gd left join em.expensePurpose ep left join em.expenseType et left join em.costCenter cc left join em.status es left join em.businessArea ba left join em.businessPlace bp left join em.createdBy cby left join em.modifiedBy mby left join em.owner onr left join ExpenseAccountsOnly aco on aco.expense=em where 1=1 and em.isDeleted=false");
			if(filterData.get("expenseManagementManualId")!=null&&!filterData.get("expenseManagementManualId").equals(""))
				queryStringBuilder.append(" and em.expenseManagementManualId like :expenseManagementManualId");
			if(filterData.get("employee")!=null&&!filterData.get("employee").equals(""))
				queryStringBuilder.append(" and concat(coalesce(cnt.firstName,''),' ',coalesce(cnt.middleName,''),' ',coalesce(cnt.lastName)) like :employee");
			if(filterData.get("employeeCode")!=null&&!filterData.get("employeeCode").equals(""))
				queryStringBuilder.append(" and cnt.employeeCode like :employeeCode");
			if(filterData.get("expensePurposeName")!=null&&!filterData.get("expensePurposeName").equals(""))
				queryStringBuilder.append(" and ep.expensePurposeName like :expensePurposeName");
			if(filterData.get("expenseTypeName")!=null&&!filterData.get("expenseTypeName").equals(""))
				queryStringBuilder.append(" and et.expenseTypeName like :expenseTypeName");
			if(filterData.get("statusName")!=null&&!filterData.get("statusName").equals(""))
				queryStringBuilder.append(" and es.statusName like :statusName");
			
			if(filterData.get("documentDate")!=null&&!filterData.get("documentDate").equals(""))
				queryStringBuilder.append(" and function('date_format', aco.documentDate, '%Y, %m, %d')= function('date_format', :documentDate , '%Y, %m, %d')");
			if(users!=null)
				queryStringBuilder.append(" and onr in :users");
			
			Query qr=entityManager.createQuery(queryStringBuilder.toString(),ExpenseManagementResponse.class);
			
			
			if(filterData.get("expenseManagementManualId")!=null&&!filterData.get("expenseManagementManualId").equals(""))
				qr.setParameter("expenseManagementManualId","%"+ filterData.get("expenseManagementManualId")+"%");
			if(filterData.get("employee")!=null&&!filterData.get("employee").equals(""))
				qr.setParameter("employee", "%"+ filterData.get("employee")+"%");
			if(filterData.get("employeeCode")!=null&&!filterData.get("employeeCode").equals(""))
				qr.setParameter("employeeCode", "%"+ filterData.get("employeeCode")+"%");
			if(filterData.get("expensePurposeName")!=null&&!filterData.get("expensePurposeName").equals(""))
				qr.setParameter("expensePurposeName","%"+ filterData.get("expensePurposeName")+"%");
			if(filterData.get("expenseTypeName")!=null&&!filterData.get("expenseTypeName").equals(""))
				qr.setParameter("expenseTypeName", "%"+ filterData.get("expenseTypeName")+"%");
			if(filterData.get("statusName")!=null&&!filterData.get("statusName").equals(""))
				qr.setParameter("statusName","%"+  filterData.get("statusName")+"%");
			if(filterData.get("documentDate")!=null&&!filterData.get("documentDate").equals("")){
					qr.setParameter("documentDate",filterData.get("documentDate"));
				}
			
			if(users!=null) {
				List<EmployeeDetails> emps=new ArrayList<EmployeeDetails>();
				for(String usr:users) {
					emps.add(new EmployeeDetails(usr));
				}
				qr.setParameter("users", emps);
			}
			return qr.getResultList();
		}
}
