package com.pratikshat.exicom.repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pratikshat.exicom.config.FileStorageProperties;
import com.pratikshat.exicom.entity.BtExpenseAccountsOnly;
import com.pratikshat.exicom.entity.BtExpenseManagement;
import com.pratikshat.exicom.entity.BtToBeApproved;
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
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.LoginUserRoles;
import com.pratikshat.exicom.entity.ToBeApproved;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.BtExpenseManagementRequest;
import com.pratikshat.exicom.model.request.ExpenseAccountsOnlyRequest;
import com.pratikshat.exicom.model.request.ExpenseManagementRequest;
import com.pratikshat.exicom.model.response.GlCodeResponse;
@Repository
public class BtExpenseManagementCustomRepositoryImpl implements BtExpenseManagementCustomRepository {

	@PersistenceContext
	EntityManager entityManager;
	@Autowired
	com.pratikshat.exicom.interceptor.WorkflowExecuter workflow;
	@Autowired
	BusinessDomainRepository domainDB;
	@Autowired
	ExpenseManagementExpensePurposeRepository btExpensePurposeDB;
	@Autowired
	ExpenseManagementBusinessAreaRepository btBusinessAreaDB;
	@Autowired
	ExpenseManagementBusinessPlaceRepository btBusinessPlaceDB;
	@Autowired
	ExpenseManagementCostCenterRepository btCostCenterDB;
	@Autowired
	ExpenseManagementExpenseTypeRepository btExpenseTypeDb;
	@Autowired
	private SimpMessagingTemplate messagingTemplate;
	
	@Autowired
	FileStorageProperties fileStorageProperties;
	
	@Autowired
	UserProfileRepository dbProfile;
	
	
	
	public void checkFroinKeys(int businessAreaId,int businessPlaceId) throws BadRequestException
	{
		if(businessAreaId!=0) {
			
			ExpenseManagementBusinessArea businessArea = btBusinessAreaDB.getByBusinessAreaId(businessAreaId);
			if(businessArea==null)
			{
				throw new BadRequestException("Business Area Id does not exist");
			}
			}
			if(businessPlaceId!=0){
			
			ExpenseManagementBusinessPlace businessPlace = btBusinessPlaceDB.getByBusinessPlaceId(businessPlaceId);
			if(businessPlace==null)
			{
				throw new BadRequestException("Business Place Id does not exist");
			}
			}
	}
	
	public void checkFroinKey(int expenseTypeId,int costCenterId,int expensePurposeId,int domainId) throws BadRequestException
	{System.out.println("hghgjkj");
		
		if(expenseTypeId!=0) {
			
		ExpenseManagementExpenseType expenseType = btExpenseTypeDb.getByExpenseTypeId(expenseTypeId);
		if(expenseType==null)
		{
			throw new BadRequestException("Expense Type Id does not exist");
		}
			
		}
		if(costCenterId!=0) {
		
		ExpenseManagementCostCenter costCenter = btCostCenterDB.getByCostCenterId(costCenterId);
		if(costCenter==null)
		{
			throw new BadRequestException("Cost Center Id does not exist");
		}
		}
		
		if(expensePurposeId!=0) {
		
		ExpenseManagementExpensePurpose expensePurpose = btExpensePurposeDB.getByExpensePurposeId(expensePurposeId);
		if(expensePurpose==null)
		{
			throw new BadRequestException("Expense Purpose Id does not exist");
		}
		}
		if(domainId!=0) {
		BusinessDomain domain = domainDB.getByDomainId(domainId);
		if(domain==null)
		{
			throw new BadRequestException("Business Domain Id does not exist");
		}
		}
	}
	
	
	@Override
	public BtExpenseManagementRequest saveDetails(BtExpenseManagementRequest request, LoginUser user) throws BadRequestException, UnauthoriseException
			 {
		
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
		if (request != null) {

			ContactDetails contactDetails = null;
			try {
				
				Query getQuery = entityManager.createQuery("select cd from ContactDetails cd where cd.emailId=?1", ContactDetails.class);
				
				getQuery.setParameter(1, user.getUsername());
				
				contactDetails = (ContactDetails) getQuery.getSingleResult();
				
			}catch(NoResultException ex) {
				throw new BadRequestException("Contact does not exist");
			}
			
			if(contactDetails==null) {
				
				throw new BadRequestException("Contact does not exist");
			}
			BtExpenseManagement expenseManagement = new BtExpenseManagement();
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
			expenseManagement.setContact(contactDetails);
			expenseManagement.setExpensePurpose(new ExpenseManagementExpensePurpose(request.getExpensePurposeId()));
			expenseManagement.setExpenseSynced(false);
			expenseManagement.setLastModifiedBy(new EmployeeDetails(user.getUsername()));
			expenseManagement.setExpenseType(new ExpenseManagementExpenseType(request.getExpenseTypeId()));
			expenseManagement.setLastModifiedDate(new Date());
			expenseManagement.setPost(false);
			expenseManagement.setPurposeOfVisit(request.getPurposeOfVisit());
			expenseManagement.setRemark(request.getRemark());
			expenseManagement.setSynced(false);
			//expenseManagement.setDocumentDate(new Date(dateformatter.format(new Date())));
			
			expenseManagement.setOwner(new EmployeeDetails(user.getUsername()));
			
			workflow.executeWorkflow(entityManager, expenseManagement, "INSERT", "PRE", user);
			System.out.println("expenseManagement =>"+expenseManagement);
			
			entityManager.persist(expenseManagement);
			
			SimpleDateFormat dateformatter = new SimpleDateFormat("yyyyMMdd");
			String manualId = null;
			//" + dateformatter.format(new java.util.Date()) + "
			manualId = "EM-BT-"
			+ com.pratikshat.exicom.config.MiscellaneousUtils.fillZero(String.valueOf(expenseManagement.getId()),5);
			expenseManagement.setManualId(manualId);
			entityManager.persist(expenseManagement);
		
			workflow.executeWorkflow(entityManager, expenseManagement, "INSERT", "POST", user);
			
			request.setId(expenseManagement.getId());
//			try {
//				com.pratikshat.exicom.entity.GlobalSearchIndex _search = new com.pratikshat.exicom.entity.GlobalSearchIndex();
//				_search.setKeyName("manualId");
//				_search.setObjectDetailsUrl("id");
//				_search.setObjectIdValue("" + expenseManagement.getId());
//				_search.setObjectName("BtExpenseManagement");
//				_search.setSearchString(manualId);
//				entityManager.persist(_search);
//			} catch (Exception e) {
//			}
			entityManager.flush();
			entityManager.clear();
			return request;

		} else {
			throw new BadRequestException("Request Parameter Null");
		}
	}

	
	@Override
	public BtExpenseManagementRequest updateDetails(BtExpenseManagementRequest request, LoginUser user) throws BadRequestException, UnauthoriseException    {
		
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
		if (request != null) {
			ContactDetails contactDetails = null;
			try {
				Query getQuery = entityManager.createQuery("select cd from ContactDetails cd where cd.emailId=?1", ContactDetails.class);
				
				getQuery.setParameter(1, user.getUsername());
				contactDetails = (ContactDetails) getQuery.getSingleResult();
				
			}catch(NoResultException ex) {
				throw new BadRequestException("Contact does not exist");
			}
			
			if(contactDetails==null) {
				throw new BadRequestException("Contact does not exist");
			}
			
			Query getQuery = entityManager.createQuery(	"select em from BtExpenseManagement em join em.status st left join em.owner own where em.isDeleted=false and em.id=?1", BtExpenseManagement.class);
			getQuery.setParameter(1, request.getId());
			BtExpenseManagement expenseManagement = (BtExpenseManagement) getQuery.getSingleResult();
			
			if (expenseManagement != null) {
				BtExpenseManagement expense = null;
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
					list.add(25);   // Rejected By CC Head
					if(!list.contains(statusId)) {
						throw new BadRequestException("You can't able to edit this record");
					}else {
						expense = updateExpenseWithOwner(expenseManagement, request, user, entityManager);
					}
			}
				else {
					
					BtToBeApproved _toBeApproved=null;
					try {
						_toBeApproved=entityManager.createQuery("select apr from BtToBeApproved apr  where apr.ObjectName=?1 and apr.objectId=?2 and apr.status=?3",BtToBeApproved.class)
								.setParameter(1,"BtExpenseManagement")
								.setParameter(2,expenseManagement.getId())
								.setParameter(3,false)
								.getSingleResult();
					}catch(javax.persistence.NoResultException ex) {
						throw new BadRequestException("You are not authorised");
					}catch(javax.persistence.NonUniqueResultException ex) {
						throw new BadRequestException("You are not authorised");
					}
				String[] assignto=_toBeApproved.getAssignedTo().split(":",2);
				if(assignto[0].equalsIgnoreCase("USER"))
				{
				 if(assignto[1].equalsIgnoreCase(user.getUsername()))
				 {
					expense = updateExpenseOtherThanOwner(expenseManagement, request,expenseManagement.getOwner().getEmailId() , user, entityManager);
				 }
				 else
				 {
						throw new BadRequestException("You are not authorised");
				 }
				}
				if(assignto[0].equalsIgnoreCase("ROLE"))
				{
					java.util.List<LoginUserRoles> userRoles=entityManager.createQuery("select usr from LoginUserRoles usr join usr.loginUser lg where usr.authority=?1",LoginUserRoles.class)
		                    .setParameter(1, assignto[1])
		                    .getResultList();
		                if(userRoles!=null&&!userRoles.isEmpty()){
		                    for(LoginUserRoles ur:userRoles){
		                    	if(ur.getLoginUser().getUsername().equalsIgnoreCase(user.getUsername()))
		                    	{
		    					expense = updateExpenseOtherThanOwner(expenseManagement, request,expenseManagement.getOwner().getEmailId() , user, entityManager);

		                    	}
		                    }
					
				}}
				}
//				else {
//					
//					throw new BadRequestException("You are not authorised");
//				}
				
			
				
				
				workflow.executeWorkflow(entityManager, expenseManagement, "UPDATE", "PRE", user);
				
				entityManager.persist(expenseManagement);
				
				workflow.executeWorkflow(entityManager, expenseManagement, "UPDATE", "POST", user);
				
				entityManager.flush();
				entityManager.clear();
				return request;
			} else {
				throw new BadRequestException("No data found to save details");
			}
		} else {
			throw new BadRequestException("Request Parameter Null");
		}
	}
private BtExpenseManagement updateExpenseWithOwner(BtExpenseManagement expenseManagement, BtExpenseManagementRequest request, LoginUser user, EntityManager entityManager)throws BadRequestException {
	SimpleDateFormat dateformatter = new SimpleDateFormat("yyyy/MM/dd");
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
		if (request.getCostCenterId() != 0) {
		expenseManagement.setCostCenter(new ExpenseManagementCostCenter(request.getCostCenterId()));
		} else {
			expenseManagement.setCostCenter(null);
		}
		if (request.getExpensePurposeId() != 0) {
		expenseManagement.setExpensePurpose(new ExpenseManagementExpensePurpose(request.getExpensePurposeId()));
		} else {
			expenseManagement.setExpensePurpose(null);
		}
		expenseManagement.setExpenseSynced(false);
		expenseManagement.setLastModifiedBy(new EmployeeDetails(user.getUsername()));
		if (request.getExpenseTypeId() != 0) {
		expenseManagement.setExpenseType(new ExpenseManagementExpenseType(request.getExpenseTypeId()));
		} else {
			expenseManagement.setExpenseType(null);
		}
		try {
			expenseManagement.setLastModifiedDate(dateformatter.parse(new Date().toString()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		expenseManagement.setPost(false);
		expenseManagement.setPurposeOfVisit(request.getPurposeOfVisit());
		expenseManagement.setRemark(request.getRemark());
		expenseManagement.setSynced(false);
		return expenseManagement;
	}

private BtExpenseManagement updateExpenseOtherThanOwner(BtExpenseManagement expenseManagement, BtExpenseManagementRequest request,String ownerEmailId, LoginUser user, EntityManager entityManager)throws BadRequestException {
	SimpleDateFormat dateformatter = new SimpleDateFormat("yyyy/MM/dd");
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
	
	if (request.getCostCenterId() != 0) {
		expenseManagement.setCostCenter(new ExpenseManagementCostCenter(request.getCostCenterId()));
		} else {
			expenseManagement.setCostCenter(null);
		}
		if (request.getExpensePurposeId() != 0) {
		expenseManagement.setExpensePurpose(new ExpenseManagementExpensePurpose(request.getExpensePurposeId()));
		} else {
			expenseManagement.setExpensePurpose(null);
		}
	expenseManagement.setExpenseSynced(false);
	expenseManagement.setLastModifiedBy(new EmployeeDetails(user.getUsername()));
	if (request.getExpenseTypeId() != 0) {
		expenseManagement.setExpenseType(new ExpenseManagementExpenseType(request.getExpenseTypeId()));
		} else {
			expenseManagement.setExpenseType(null);
		}
	try {
		expenseManagement.setLastModifiedDate(dateformatter.parse(new Date().toString()));
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	expenseManagement.setPost(false);
	expenseManagement.setPurposeOfVisit(request.getPurposeOfVisit());
	expenseManagement.setRemark(request.getRemark());
	expenseManagement.setSynced(false);
	return expenseManagement;
}
	@Override
	public BtExpenseManagementRequest deleteDetails(BtExpenseManagementRequest t, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		
		return null;
	}

	@Override
	public ExpenseAccountsOnlyRequest saveUpdateExpenseAccountsOnly(
			ExpenseAccountsOnlyRequest expenseAccountsOnlyRequest,LoginUser user) throws BadRequestException, UnauthoriseException {
		BtExpenseManagement expenseManagement = null;
		try {
			Query getQuery = entityManager.createQuery(
					"select em from BtExpenseManagement em join em.status st left join em.owner own where em.id=?1", BtExpenseManagement.class);
			getQuery.setParameter(1, expenseAccountsOnlyRequest.getExpenseManagementId());
			expenseManagement = (BtExpenseManagement) getQuery.getSingleResult();
		}catch(NoResultException ex) {
			throw new BadRequestException("Expense does not exist");
		}catch(Exception ex) {
			throw new BadRequestException(ex.getMessage());
		}
		BtExpenseAccountsOnly accountsOnly = null;
		try {
			Query getQuery = entityManager.createQuery(
					"select aco from BtExpenseAccountsOnly aco left join BtExpenseManagement em on aco.expense=em where aco.expense=?1", BtExpenseAccountsOnly.class);
			getQuery.setParameter(1, expenseManagement);
			accountsOnly = (BtExpenseAccountsOnly) getQuery.getSingleResult();
		}catch(NoResultException ex) {
			
		}
		ToBeApproved _toBeApproved=null;
//		try {
//			_toBeApproved=entityManager.createQuery("select apr from ToBeApproved apr join apr.assignedTo asg where apr.ObjectName=?1 and apr.objectId=?2 and apr.status=?3",ToBeApproved.class)
//					.setParameter(1,"BtExpenseManagement")
//					.setParameter(2,expenseManagement.getId())
//					.setParameter(3,false)
//					.getSingleResult();
//		}catch(javax.persistence.NoResultException ex) {
//			throw new BadRequestException("You are not authorised");
//		}catch(javax.persistence.NonUniqueResultException ex) {
//			throw new BadRequestException("You are not authorised");
//		}
//		if(_toBeApproved.getAssignedTo().getEmailId().equalsIgnoreCase(user.getUsername())||dbProfile.samroleUsers(user.getUsername(), _toBeApproved.getAssignedTo().getEmailId())){
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
				BtExpenseAccountsOnly expenseAccountsOnly = new BtExpenseAccountsOnly();
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
//		}else {
//			throw new BadRequestException("You are not authorised");
//		}
	}

	@Override
	public List<GlCodeResponse> getGLCodeSummary(long btExpenseManagementId, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		List<GlCodeResponse> response = new ArrayList<>();
		
		Query getQuery = entityManager.createQuery(
				"select em from BtExpenseManagement em join em.status st left join em.costCenter cc left join em.owner own where em.id=?1", BtExpenseManagement.class);
		getQuery.setParameter(1, btExpenseManagementId);
		BtExpenseManagement expenseManagement = (BtExpenseManagement) getQuery.getSingleResult();		

		if (expenseManagement != null) {
			
			// Tour Expense
			double totalTourAmountToDisbursed = 0;
			try {
				Query sumTourExpenseQuery = entityManager.createQuery(
						"select coalesce(sum(te.amountToDisbursed),0) as totalAmountToDisbursed from BtTourExpense te left join te.btExpenseManagement em where te.isDeleted=false and em.id=?1");
				sumTourExpenseQuery.setParameter(1, expenseManagement.getId());
				totalTourAmountToDisbursed = (double) sumTourExpenseQuery.getSingleResult();
			} catch (NoResultException ex) {

			}
			
			// Lodging Expense
			double totalLodgingAmountToDisbursed = 0;
			try {
				Query sumLodgingExpenseQuery = entityManager.createQuery(
						"select coalesce(sum(le.amountToDisbursed),0) as totalAmountToDisbursed from BtLodgingExpense le left join le.btExpenseManagement em where le.isDeleted=false and em.id=?1");
				sumLodgingExpenseQuery.setParameter(1, expenseManagement.getId());
				totalLodgingAmountToDisbursed = (double) sumLodgingExpenseQuery.getSingleResult();
			} catch (NoResultException ex) {

			}
			
			// Boarding Expense
			double totalBoardingAmountToDisbursed = 0;
			try {
				Query sumBoardingExpenseQuery = entityManager.createQuery(
						"select coalesce(sum(be.amountToDisbursed),0) as totalAmountToDisbursed from BtBoardingExpense be left join be.btExpenseManagement em where be.isDeleted=false and em.id=?1");
				sumBoardingExpenseQuery.setParameter(1, expenseManagement.getId());
				totalBoardingAmountToDisbursed = (double) sumBoardingExpenseQuery.getSingleResult();
			} catch (NoResultException ex) {

			}
			

			// Local Conveyance
			double totalLocalConveyanceAmountToDisbursed = 0;
			try {
				Query sumLocalConveyanceQuery = entityManager.createQuery(
						"select coalesce(sum(lce.amountToDisbursed),0) as totalAmountToDisbursed from BtLocalConveyanceExpense lce left join lce.btExpenseManagement em where lce.isDeleted=false and em.id=?1");
				sumLocalConveyanceQuery.setParameter(1, expenseManagement.getId());
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
						"select new com.pratikshat.exicom.model.response.GlCodeResponse(oct.id, oct.name, coalesce(sum(och.amountToDisbursed),0)) from BtOtherChargesExpense och left join och.btExpenseManagement em join och.otherChargesType oct where och.isDeleted=false and em.id=?1 group by oct.id");
				sumOtherChargesQuery.setParameter(1, expenseManagement.getId());
				otherExpense = (List<GlCodeResponse>) sumOtherChargesQuery.getResultList();
			} catch (NoResultException ex) {

			}
			
			if(otherExpense!=null && !otherExpense.isEmpty()) {
				response.addAll(otherExpense);
			}
			return response;
		} else {
			throw new BadRequestException("Expense not found");
		}
	}

	@Override
	public void submitForApproval(long btExpenseManagementId, String remark, int businessDoaminId, LoginUser user) throws BadRequestException,UnauthoriseException {
		
		BtExpenseManagement expenseManagement = null;
		try {
			Query getQuery = entityManager.createQuery(
					"select em from BtExpenseManagement em left join em.status st left join em.owner own where em.id=?1",
					BtExpenseManagement.class);
			getQuery.setParameter(1, btExpenseManagementId);
			expenseManagement = (BtExpenseManagement) getQuery.getSingleResult();

		} catch (javax.persistence.NoResultException ex) {
			throw new BadRequestException("Expense Management does not exist");
		}
		if(!expenseManagement.getOwner().getEmailId().equalsIgnoreCase(user.getUsername())) {
			throw new BadRequestException("You are not authorised to submit");
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
		list.add(25);   // Rejected By CC Head
		if(!list.contains(statusId)) {
			throw new BadRequestException("You can't submit this record for approval");
		}
		
		if (businessDoaminId == 1) { // Battery
			expenseManagement.setStatus(new ExpenseManagementStatus(2));
		} else if (businessDoaminId == 2) { // DCT
			expenseManagement.setStatus(new ExpenseManagementStatus(9));
		} else if (businessDoaminId == 3) { // EV

		}
		
		
		System.out.println("Status  =>  "+expenseManagement.getStatus());
		
		
		workflow.executeWorkflow(entityManager, expenseManagement, "UPDATE", "PRE", user);
		
		entityManager.persist(expenseManagement);
		
		expenseManagement.setStatus(new com.pratikshat.exicom.entity.ExpenseManagementStatus(2));
		
		entityManager.persist(expenseManagement);

		workflow.executeWorkflow(entityManager, expenseManagement, "UPDATE", "POST", user);

		
		// approval process entry point
		try {
//			System.out.println("iubdsjah");
//			java.util.Set<String> _roles = new java.util.HashSet<String>();
//			java.util.Iterator ite = user.getAuthorities().iterator();
//			while (ite.hasNext()) {
//				com.pratikshat.exicom.entity.LoginUserRoles _rr = (com.pratikshat.exicom.entity.LoginUserRoles) ite
//						.next();
//				_roles.add(_rr.getAuthority());
//			}
//System.out.println("ijidusiuew");
//			boolean roleFound = false;
//			boolean userFound = false;
//			com.pratikshat.exicom.entity.Approval _appliedRule = new com.pratikshat.exicom.entity.Approval();
//
//			java.util.List<com.pratikshat.exicom.entity.Approval> _rulesLst = new java.util.ArrayList<com.pratikshat.exicom.entity.Approval>();
//
//			_rulesLst = entityManager.createQuery(
//					"select apr from Approval apr where apr.objectClassName=?1 and apr.active=?2 and apr.businessDomain.businessDomainId=?3",
//					com.pratikshat.exicom.entity.Approval.class).setParameter(1, "BtExpenseManagement")
//					.setParameter(2, true).setParameter(3, businessDoaminId)
//					.getResultList();
//System.out.println("kdpoap[");
//			for (com.pratikshat.exicom.entity.Approval _appr : _rulesLst) {
//				String submitters[] = _appr.getInitialSubmitters().split(",");
//				for (String submi : submitters) {
//					String _subs[] = submi.split(":");
//					if (_subs[0].equalsIgnoreCase("ROLE")) {
//						if (_roles.contains(_subs[1])) {
//							roleFound = true;
//							break;
//						}
//					}
//					System.out.println("]p][ppoias");
//					if (_subs[0].equalsIgnoreCase("USER")) {
//						if (user.getUsername().equalsIgnoreCase(_subs[1])) {
//							userFound = true;
//							break;
//						}
//					}
//				}
//				System.out.println("mkjoai");
//				if (roleFound || userFound) {
//					_appliedRule = _appr;
//					break;
//				}
//			}
//System.out.println("o[p[p-09wq");
//			com.pratikshat.exicom.entity.ApprovalSteps _stapes = entityManager
//					.createQuery("select stp from ApprovalSteps stp where stp.approval=?1 and stp.approvedStage=?2",
//							com.pratikshat.exicom.entity.ApprovalSteps.class)
//					.setParameter(1, _appliedRule).setParameter(2, 1).getSingleResult();
//System.out.println("[us878w7");
//			String assigned[] = _stapes.getApprovedBy().split(":");
//
//			String assigneUserEmailId = "";
//
//			if (assigned[0].equalsIgnoreCase("USER")) {
//				assigneUserEmailId = assigned[1];
//			}
//			if (assigned[0].equalsIgnoreCase("APPROVAL-MANAGER")) {
//				assigneUserEmailId = entityManager.createQuery(
//						"select apm.emailId from EmployeeDetails emp join emp.approvalManager apm where emp.emailId=?1",
//						String.class).setParameter(1, user.getUsername()).getSingleResult();
//			}
//System.out.println("opodou2q2");
//			if (assigned[0].equalsIgnoreCase("MANAGER")) {
//				assigneUserEmailId = entityManager.createQuery(
//						"select apm.emailId from EmployeeDetails emp join emp.manager apm where emp.emailId=?1",
//						String.class).setParameter(1, user.getUsername()).getSingleResult();
//			}
//System.out.println("opap]]");
//			if (assigned[0].equalsIgnoreCase("ROLE")) {
//				
//				assigneUserEmailId = getApproverEmail(assigned[1], user.getUsername(), entityManager);
//			}
//System.out.println("89798");
//			com.pratikshat.exicom.entity.ToBeApproved _toBeApproved = new com.pratikshat.exicom.entity.ToBeApproved();
//			_toBeApproved.setAssignedTo(new com.pratikshat.exicom.entity.EmployeeDetails(assigneUserEmailId));
//			_toBeApproved.setAssignedDate(new java.util.Date());
//			_toBeApproved.setObjectId(expenseManagement.getId());
//			_toBeApproved.setObjectName("BtExpenseManagement");
//			_toBeApproved.setObjectId(expenseManagement.getId());
//			_toBeApproved.setObjectManualId(expenseManagement.getManualId());
//			_toBeApproved.setObjectUrl("/btexpensesmanagement/" + expenseManagement.getId());
//			if(remark!=null && !remark.equalsIgnoreCase("")) {
//				_toBeApproved.setPreviousRemarks(remark);
//			}else {
//				_toBeApproved.setPreviousRemarks(null);	
//			}
//			_toBeApproved.setRemark("");
//			_toBeApproved.setApprovalRuleId(_appliedRule.getId());
//			_toBeApproved.setStage(1);
//			_toBeApproved.setStatus(false);
//			
//			workflow.executeWorkflow(entityManager, _toBeApproved, "INSERT", "PRE", user);
//			entityManager.persist(_toBeApproved);
//			workflow.executeWorkflow(entityManager, _toBeApproved, "INSERT", "POST", user);
//System.out.println("pp[2656");
//			com.pratikshat.exicom.entity.ApplicationNotificationDetails notificationDetails = new com.pratikshat.exicom.entity.ApplicationNotificationDetails();
//			notificationDetails.setNofificationDate(new java.util.Date());
//			notificationDetails.setNotificationActionUrl("/itemtobeapproved");
//			notificationDetails
//					.setNotificationMessage(String.format("BtExpense %s has been send to you for approval",
//							expenseManagement.getManualId()));
//			notificationDetails.setNotificationRead(false);
//			notificationDetails.setUserEmailId(assigneUserEmailId);
//
//			entityManager.persist(notificationDetails);
//System.out.println("989oiqo");
//			try {
//				messagingTemplate.convertAndSend("/topic/" + assigneUserEmailId, notificationDetails);
//			} catch (Exception ex) {
//			}

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
}
