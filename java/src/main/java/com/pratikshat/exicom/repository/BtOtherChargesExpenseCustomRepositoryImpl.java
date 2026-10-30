package com.pratikshat.exicom.repository;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pratikshat.exicom.entity.BtExpenseManagement;
import com.pratikshat.exicom.entity.BtOtherChargesExpense;
import com.pratikshat.exicom.entity.BtToBeApproved;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.ExpenseManagement;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.LoginUserRoles;
import com.pratikshat.exicom.entity.OtherChargesExpense;
import com.pratikshat.exicom.entity.OtherChargesTypes;
import com.pratikshat.exicom.entity.ToBeApproved;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.OtherChargesExpenseRequest;
import com.pratikshat.exicom.model.response.StatusMessageResponse;

@Repository
public class BtOtherChargesExpenseCustomRepositoryImpl implements BtOtherChargesExpenseCustomRepository {

	@PersistenceContext
	EntityManager entityManager;
	
	@Autowired
	com.pratikshat.exicom.interceptor.WorkflowExecuter workflow;
	
	
	@Override
	public OtherChargesExpenseRequest saveDetails(OtherChargesExpenseRequest request, LoginUser user)
			throws BadRequestException, UnauthoriseException {
if(request!=null) {
			BtExpenseManagement expenseManagement = null;
			try {
				Query selectExpenseQuery = entityManager.createQuery(
						"select em from BtExpenseManagement em join em.status st  left join em.owner onr where em.isDeleted=false and em.id=?1", BtExpenseManagement.class);

				selectExpenseQuery.setParameter(1, request.getExpenseManagementId());
				expenseManagement = (BtExpenseManagement) selectExpenseQuery.getSingleResult();
			} catch (NoResultException ex) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Expense does not exist");
			}
			if(!expenseManagement.getOwner().getEmailId().equalsIgnoreCase(user.getUsername())) {
				throw new com.pratikshat.exicom.exception.BadRequestException("You are not authorised");
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
				throw new BadRequestException("You can't able to edit this record");
			}
			
			BtOtherChargesExpense otherChargesExpense = new BtOtherChargesExpense();
			otherChargesExpense.setAmount(request.getAmount());
			otherChargesExpense.setAmountToDisbursed(request.getAmount());
			otherChargesExpense.setBillNo(request.getBillNo());
			otherChargesExpense.setCreatedDate(new Date());
			otherChargesExpense.setCreatedBy(new EmployeeDetails(user.getUsername()));
			otherChargesExpense.setDate(new Date());
			otherChargesExpense.setBtExpenseManagement(expenseManagement);
			otherChargesExpense.setLastModifiedBy(new EmployeeDetails(user.getUsername()));
			otherChargesExpense.setLastModifiedDate(new Date());
			otherChargesExpense.setOtherChargesType(new OtherChargesTypes(request.getOtherChargesTypeId()));
			otherChargesExpense.setOwner(new EmployeeDetails(user.getUsername()));
			otherChargesExpense.setRemark(request.getRemark());
			workflow.executeWorkflow(entityManager, otherChargesExpense, "INSERT", "PRE", user);
			entityManager.persist(otherChargesExpense);
			
			SimpleDateFormat dateformatter = new SimpleDateFormat("yyyyMMdd");
			String manualId = null;
			manualId = "OC-" + dateformatter.format(new java.util.Date()) + "-"
			+ com.pratikshat.exicom.config.MiscellaneousUtils.fillZero(String.valueOf(otherChargesExpense.getId()),5);
			otherChargesExpense.setManualId(manualId);
			entityManager.persist(otherChargesExpense);
			
			workflow.executeWorkflow(entityManager, otherChargesExpense, "INSERT", "POST", user);
			
			request.setId(otherChargesExpense.getId());

			// search index entry
			try {
				com.pratikshat.exicom.entity.GlobalSearchIndex _search = new com.pratikshat.exicom.entity.GlobalSearchIndex();
				_search.setKeyName("manualId");
				_search.setObjectDetailsUrl("otherchargesexpense/" + otherChargesExpense.getId());
				_search.setObjectIdName("id");
				_search.setObjectIdValue("" + otherChargesExpense.getId());
				_search.setObjectName("BtOtherChargesExpense");
				_search.setSearchString(manualId);
				entityManager.persist(_search);
				} catch (Exception e) {
				throw new BadRequestException("");
			}
			BtExpenseManagement expense = expenseTotal(expenseManagement, entityManager);
			
			entityManager.persist(expense);
			
			System.out.println("jifierw");
			entityManager.flush();
			entityManager.clear();
			return request;
		}else {
			throw new BadRequestException("Request Parameter not found");
		}
	}

	@Override
	public OtherChargesExpenseRequest updateDetails(OtherChargesExpenseRequest request, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		if(request!=null) {
			Query selectOtherChargesExpenseQuery=entityManager.createQuery("select oc from BtOtherChargesExpense oc where oc.isDeleted=false and oc.id=?1",BtOtherChargesExpense.class);
			selectOtherChargesExpenseQuery.setParameter(1,request.getId());
			BtOtherChargesExpense otherChargesExpense=(BtOtherChargesExpense)selectOtherChargesExpenseQuery.getSingleResult();
			if(otherChargesExpense!=null) {
				
				BtExpenseManagement expenseManagement = null;
				try {
					Query selectExpenseQuery = entityManager.createQuery(
							"select em from BtExpenseManagement em join em.status st  left join em.owner onr where em.isDeleted=false and em.id=?1", BtExpenseManagement.class);

					selectExpenseQuery.setParameter(1, request.getExpenseManagementId());
					expenseManagement = (BtExpenseManagement) selectExpenseQuery.getSingleResult();
				} catch (NoResultException ex) {
					throw new com.pratikshat.exicom.exception.BadRequestException("Expense does not exist");
				}
				BtOtherChargesExpense oExpense = null;
				
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
						oExpense = updateOtherChargesWithOwner(otherChargesExpense,request,user,entityManager);	
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
					 oExpense = updateOtherChargesOtherThanOwner(otherChargesExpense, request,expenseManagement.getOwner().getEmailId() , user, entityManager);
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
		                    		oExpense = updateOtherChargesOtherThanOwner(otherChargesExpense, request,expenseManagement.getOwner().getEmailId() , user, entityManager);

		                    	}
		                    }
					
				}}
				
				}
//				else {
//					ToBeApproved _toBeApproved=null;
//					try {
//						_toBeApproved=entityManager.createQuery("select apr from ToBeApproved apr join apr.assignedTo asg where apr.ObjectName=?1 and apr.objectId=?2 and apr.status=?3",ToBeApproved.class)
//								.setParameter(1,"ExpenseManagement")
//								.setParameter(2,expenseManagement.getId())
//								.setParameter(3,false)
//								.getSingleResult();
//					}catch(javax.persistence.NoResultException ex) {
//						throw new BadRequestException("You are not authorised");
//					}catch(javax.persistence.NonUniqueResultException ex) {
//						throw new BadRequestException("You are not authorised");
//					}
//					if(_toBeApproved.getAssignedTo().getEmailId().equalsIgnoreCase(user.getUsername())){
//						oExpense = updateOtherChargesOtherThanOwner(otherChargesExpense, request, expenseManagement.getOwner().getEmailId(), user, entityManager);
//					}else {
//						throw new BadRequestException("You are not authorised");
//					}
//				}
				
				
				workflow.executeWorkflow(entityManager, oExpense, "UPDATE", "PRE", user);
				entityManager.persist(oExpense);
				workflow.executeWorkflow(entityManager, oExpense, "UPDATE", "POST", user);
				
				BtExpenseManagement expense = expenseTotal(expenseManagement, entityManager);
				entityManager.persist(expense);
				
				entityManager.flush();
				entityManager.clear();
				return request;
			}else {
				throw new BadRequestException("No data found to save details");
			}
		}else {
			throw new BadRequestException("Request Parameter not found");
		}
	}

private BtOtherChargesExpense updateOtherChargesWithOwner(BtOtherChargesExpense otherChargesExpense, OtherChargesExpenseRequest request, LoginUser user, EntityManager entityManager)throws BadRequestException{
		
		otherChargesExpense.setAmount(request.getAmount());
		otherChargesExpense.setAmountToDisbursed(request.getAmount());
		otherChargesExpense.setBillNo(request.getBillNo());
		otherChargesExpense.setDate(request.getDate());
		otherChargesExpense.setLastModifiedBy(new EmployeeDetails(user.getUsername()));
		otherChargesExpense.setLastModifiedDate(new Date());
		otherChargesExpense.setOtherChargesType(new OtherChargesTypes(request.getOtherChargesTypeId()));
		otherChargesExpense.setRemark(request.getRemark());
		
		return otherChargesExpense;
	}
	
	
	private BtOtherChargesExpense updateOtherChargesOtherThanOwner(BtOtherChargesExpense otherChargesExpense, OtherChargesExpenseRequest request, String ownerEmailId, LoginUser user, EntityManager entityManager)throws BadRequestException{
		
		otherChargesExpense.setAmount(request.getAmount());
		otherChargesExpense.setAmountToDisbursed(request.getAmountToDisbursed());
		otherChargesExpense.setBillNo(request.getBillNo());
		otherChargesExpense.setDate(request.getDate());
		otherChargesExpense.setLastModifiedBy(new EmployeeDetails(user.getUsername()));
		otherChargesExpense.setLastModifiedDate(new Date());
		otherChargesExpense.setOtherChargesType(new OtherChargesTypes(request.getOtherChargesTypeId()));
		otherChargesExpense.setRemark(request.getRemark());
		
		return otherChargesExpense;
	}
	
	
	
	public BtExpenseManagement expenseTotal(BtExpenseManagement expenseManagement, EntityManager entityManager) throws BadRequestException {

		double totalBpAmount = 0;

		double totalExpense = 0;

		double totalCompanyPaid = 0;

		double netAmountToPay = 0;

		// Tour Expense
		double totalTourAmount = 0;
		double totalTourDays = 0;
		double totalTourAmountToDisbursed = 0;
		Object[] sumTourExpenseExpense = null;
		try {
			Query sumTourExpenseQuery = entityManager.createQuery(
					"select coalesce(sum(te.fare),0) as totalFare, coalesce(sum(te.noOfDays),0) as totalDays, coalesce(sum(te.amountToDisbursed),0) as totalAmountToDisbursed from BtTourExpense te left join te.btExpenseManagement em where te.isDeleted=false and em.isDeleted=false and em.id=?1");
			sumTourExpenseQuery.setParameter(1, expenseManagement.getId());
			sumTourExpenseExpense = (Object[]) sumTourExpenseQuery.getSingleResult();
		} catch (NoResultException ex) {
			throw new BadRequestException("No Data Found");
		}
		
		if (sumTourExpenseExpense != null) {
			totalTourAmount = (double) sumTourExpenseExpense[0];
			totalTourDays = (double) sumTourExpenseExpense[1];
			totalTourAmountToDisbursed = (double) sumTourExpenseExpense[2];
		}
		

		double totalTourAmountCompanyPaid = 0;
		try {
			Query sumTourExpenseCompanyPaidQuery = entityManager.createQuery(
					"select coalesce(sum(te.fare),0) as totalFare from BtTourExpense te left join te.btExpenseManagement em where te.isDeleted=false and em.isDeleted=false and te.companyPaid=true and em.id=?1");
			sumTourExpenseCompanyPaidQuery.setParameter(1, expenseManagement.getId());
			totalTourAmountCompanyPaid = (double) sumTourExpenseCompanyPaidQuery.getSingleResult();
		} catch (NoResultException ex) {
			throw new BadRequestException("No Data Found");
		}
		
		// Lodging Expense
		Object[] sumLodgingExpense = null;
		double totalLodgingAmount = 0;
		double totalLodgingDays = 0;
		double totalLodgingAmountToDisbursed = 0;
		try {
			Query sumLodgingExpenseQuery = entityManager.createQuery(
					"select coalesce(sum(le.amount),0) as totalAmount, coalesce(sum(le.NoOfDayes),0) as totalDays, coalesce(sum(le.amountToDisbursed),0) as totalAmountToDisbursed from BtLodgingExpense le left join le.btExpenseManagement em where le.isDeleted=false and em.isDeleted=false and em.id=?1");
			sumLodgingExpenseQuery.setParameter(1, expenseManagement.getId());
			sumLodgingExpense = (Object[]) sumLodgingExpenseQuery.getSingleResult();
		} catch (NoResultException ex) {
			throw new BadRequestException("No Data Found");
		}
		
		if (sumLodgingExpense != null) {
			totalLodgingAmount = (double) sumLodgingExpense[0];
			totalLodgingDays = (double) sumLodgingExpense[1];
			totalLodgingAmountToDisbursed = (double) sumLodgingExpense[2];
		}

		double totalLodgingCompanyPaid = 0;
		try {
			Query sumLodgingCompanyPaidQuery = entityManager.createQuery(
					"select coalesce(sum(le.amount),0) as totalFare from BtLodgingExpense le left join le.btExpenseManagement em where le.isDeleted=false and em.isDeleted=false and le.isComapnyPaid=true and em.id=?1");
			sumLodgingCompanyPaidQuery.setParameter(1, expenseManagement.getId());
			totalLodgingCompanyPaid = (double) sumLodgingCompanyPaidQuery.getSingleResult();
		} catch (NoResultException ex) {
			throw new BadRequestException("No Data Found");
		}

		// Local Conveyance
		Object[] sumLocalConveyanceExpense = null;
		double totalLocalConveyanceAmount = 0;
		double totalLocalConveyanceAmountToDisbursed = 0;
		try {
			Query sumLocalConveyanceQuery = entityManager.createQuery(
					"select coalesce(sum(lce.amount),0) as totalAmount, coalesce(sum(lce.amountToDisbursed),0) as totalAmountToDisbursed from BtLocalConveyanceExpense lce left join lce.btExpenseManagement em where lce.isDeleted=false and em.isDeleted=false and em.id=?1");
			sumLocalConveyanceQuery.setParameter(1, expenseManagement.getId());
			sumLocalConveyanceExpense = (Object[]) sumLocalConveyanceQuery.getSingleResult();
		} catch (NoResultException ex) {
			throw new BadRequestException("No Data Found");
		}
		
		if(sumLocalConveyanceExpense!=null) {
			totalLocalConveyanceAmount = (double)sumLocalConveyanceExpense[0];
			totalLocalConveyanceAmountToDisbursed = (double)sumLocalConveyanceExpense[1];
		}

		// Boarding Expense
		Object[] sumBoardingExpense = null;
		double totalBoardingAmount = 0;
		double totalBoardingDays = 0;
		double totalBoardingAmountToDisbursed = 0;
		try {
			Query sumBoardingExpenseQuery = entityManager.createQuery(
					"select coalesce(sum(be.NoOfDayes),0) as totalDays, coalesce(sum(be.amount),0) as totalAmount, coalesce(sum(be.amountToDisbursed),0) as totalAmountToDisbursed from BtBoardingExpense be left join be.btExpenseManagement em where be.isDeleted=false and em.isDeleted=false and em.id=?1");
			sumBoardingExpenseQuery.setParameter(1, expenseManagement.getId());
			sumBoardingExpense = (Object[]) sumBoardingExpenseQuery.getSingleResult();
		} catch (NoResultException ex) {
			throw new BadRequestException("No Data Found");
		}

		if (sumBoardingExpense != null) {
			totalBoardingDays = (double) sumBoardingExpense[0];
			totalBoardingAmount = (double) sumBoardingExpense[1];
			totalBoardingAmountToDisbursed = (double)sumBoardingExpense[2];
		}
		// Other Charges
		Object[] sumOtherExpense = null;
		double totalOtherChargesAmount = 0;
		double totalOtherChargesAmountToDisbursed = 0;
		try {
			Query sumOtherChargesQuery = entityManager.createQuery(
					"select coalesce(sum(och.amount),0) as totalAmount, coalesce(sum(och.amountToDisbursed),0) as totalAmountToDisbursed from BtOtherChargesExpense och left join och.btExpenseManagement em where och.isDeleted=false and em.isDeleted=false and em.id=?1");
			sumOtherChargesQuery.setParameter(1, expenseManagement.getId());
			sumOtherExpense = (Object[]) sumOtherChargesQuery.getSingleResult();
		} catch (NoResultException ex) {
			throw new BadRequestException("No Data Found");
		}
		
		if(sumOtherExpense!=null) {
			totalOtherChargesAmount = (double)sumOtherExpense[0];
			totalOtherChargesAmountToDisbursed = (double) sumOtherExpense[1];
		}
		
		
		//btServiceAppointmentExpense
		 Object[] sumBtServiceAppoinmentExp = null;
			double totalBtServiceAppoinmentExpClaimAmount = 0;
			double totalBtServiceAppoinmentExpAmountToDisbursed = 0;
			
			try {
				Query sumBtServiceAppoinmentExpQuery = entityManager.createQuery(
						"select coalesce(sum(sae.claimAmount),0) as totalClaimAmount, coalesce(sum(sae.amountToDisbursed),0) as totalAmountToDisbursed from BtServiceAppointmentExp sae left join sae.btExpenseManagement em where sae.isDeleted=false and em.isDeleted=false and em.id=?1");
				sumBtServiceAppoinmentExpQuery.setParameter(1, expenseManagement.getId());
				sumBtServiceAppoinmentExp = (Object[]) sumBtServiceAppoinmentExpQuery.getSingleResult();
			} catch (NoResultException ex) {

			}
			
			if(sumBtServiceAppoinmentExp!=null) {
				totalBtServiceAppoinmentExpClaimAmount = (double) sumBtServiceAppoinmentExp[0];
				totalBtServiceAppoinmentExpAmountToDisbursed=(double) sumBtServiceAppoinmentExp[1];
			}
		
		
			expenseManagement.setTotalServiceAppointmentAmount(totalBtServiceAppoinmentExpClaimAmount);
			
		expenseManagement.setTotalTourAmount(totalTourAmount);
		expenseManagement.setTotalTourDays(totalTourDays);
		expenseManagement.setTotalTourAmountCompanyPaid(totalTourAmountCompanyPaid);

		expenseManagement.setTotalLodgingAmount(totalLodgingAmount);
		expenseManagement.setTotalLodgingDays(totalLodgingDays);
		expenseManagement.setTotalLodgingCompanyPaid(totalLodgingCompanyPaid);
		
		expenseManagement.setTotalLocalConveyanceAmount(totalLocalConveyanceAmount);

		expenseManagement.setTotalBoardingAmount(totalBoardingAmount);
		expenseManagement.setTotalBoardingDays(totalBoardingDays);

		expenseManagement.setTotalOtherChargesAmount(totalOtherChargesAmount);
		
		expenseManagement.setTotalBpAmount(totalBpAmount);
		
		totalExpense = totalTourAmount+totalLodgingAmount+totalLocalConveyanceAmount+totalBoardingAmount+totalOtherChargesAmount+totalBtServiceAppoinmentExpClaimAmount;
		expenseManagement.setTotalExpense(totalExpense);
		
		totalCompanyPaid = totalTourAmountCompanyPaid+totalLodgingCompanyPaid;
		expenseManagement.setTotalCompanyPaid(totalCompanyPaid);

		double totalAmountToDisbursed = totalTourAmountToDisbursed+totalLodgingAmountToDisbursed+totalLocalConveyanceAmountToDisbursed+totalBoardingAmountToDisbursed+totalOtherChargesAmountToDisbursed+totalBtServiceAppoinmentExpAmountToDisbursed;
		netAmountToPay = totalAmountToDisbursed-expenseManagement.getAdvanceAmount();
		expenseManagement.setNetAmountToPay(netAmountToPay);
		
		return expenseManagement;
	}	
	
	@Override
	public OtherChargesExpenseRequest deleteDetails(OtherChargesExpenseRequest t, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatusMessageResponse deleteOtherChargesExpense(long id, LoginUser user) throws BadRequestException, UnauthoriseException {
		StatusMessageResponse response = new StatusMessageResponse();
		if (id != 0) {
			Query selectOtherChargesExpenseQuery=entityManager.createQuery("select oc from BtOtherChargesExpense oc left join oc.btExpenseManagement em where oc.isDeleted=false and oc.id=?1",BtOtherChargesExpense.class);
			selectOtherChargesExpenseQuery.setParameter(1,id);
			BtOtherChargesExpense otherChargesExpense=(BtOtherChargesExpense)selectOtherChargesExpenseQuery.getSingleResult();
			
			if(otherChargesExpense!=null) {
				
				BtExpenseManagement expenseManagement = null;
				try {
					Query selectExpenseQuery = entityManager.createQuery(
							"select em from BtExpenseManagement em join em.status st left join em.owner onr where em.isDeleted=false and em.id=?1", BtExpenseManagement.class);

					selectExpenseQuery.setParameter(1, otherChargesExpense.getBtExpenseManagement().getId());
					expenseManagement = (BtExpenseManagement) selectExpenseQuery.getSingleResult();
				} catch (NoResultException ex) {
					throw new com.pratikshat.exicom.exception.BadRequestException("Expense does not exist");
				}
				
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
						throw new BadRequestException("You can't able to delete this record");
					}
					
					
					otherChargesExpense.setDeleted(true);
					otherChargesExpense.setLastModifiedBy(new EmployeeDetails(user.getUsername()));
					otherChargesExpense.setLastModifiedDate(new Date());
					
					workflow.executeWorkflow(entityManager, otherChargesExpense, "UPDATE", "PRE", user);
					entityManager.persist(otherChargesExpense);
					workflow.executeWorkflow(entityManager, otherChargesExpense, "UPDATE", "POST", user);

					BtExpenseManagement expense = expenseTotal(expenseManagement, entityManager);
					
					workflow.executeWorkflow(entityManager, expense, "UPDATE", "PRE", user);
					entityManager.persist(expense);
					workflow.executeWorkflow(entityManager, expense, "UPDATE", "POST", user);

					entityManager.flush();
					entityManager.clear();
				}
				response.setMessage("BtOther Charges Expesne Deleted successfully");
				return response;
			}else {
				throw new BadRequestException("Other Charges Expense does not exist");	
			}
		} else {
			throw new BadRequestException("Request Parameter not found");
		}
	}

}
