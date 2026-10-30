package com.pratikshat.exicom.repository;

import java.text.ParseException;
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

import com.pratikshat.exicom.entity.BoardingLoadingDetails;
import com.pratikshat.exicom.entity.BtBoardingExpense;
import com.pratikshat.exicom.entity.BtExpenseManagement;
import com.pratikshat.exicom.entity.BtServiceAppointment;
import com.pratikshat.exicom.entity.BtServiceAppointmentExp;
import com.pratikshat.exicom.entity.BtServiceAppointmentExpense;
import com.pratikshat.exicom.entity.BtToBeApproved;
import com.pratikshat.exicom.entity.BtTourExpense;
import com.pratikshat.exicom.entity.CityDetails;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.LoginUserRoles;
import com.pratikshat.exicom.entity.ToBeApproved;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.BoardingExpenseRequest;
import com.pratikshat.exicom.model.request.BtServiceAppointmentExpRequest;
import com.pratikshat.exicom.model.response.StatusMessageResponse;

@Repository
public class BtServiceAppointmentExpCustomRepositoryImpl implements BtServiceAppointmentExpCustomRepository {

	@Autowired
	BtServiceAppointmentRepositoryCustomeImpl  btServiceAppointmentImpl;
	
	@PersistenceContext
	EntityManager entityManager;
	
	@Autowired
	com.pratikshat.exicom.interceptor.WorkflowExecuter workflow;
	
	@Override
	public BtServiceAppointmentExpRequest saveDetails(BtServiceAppointmentExpRequest request, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		System.out.println("request  "+request);
		BtServiceAppointmentExpRequest reqs=new BtServiceAppointmentExpRequest();
		if(request!=null) {
			BtExpenseManagement expenseManagement=null;
			BtServiceAppointment btServiceAppointment=null;
			try {
				Query selectExpenseQuery = entityManager.createQuery(
						"select em from BtExpenseManagement em join em.status st left join em.owner onr where em.isDeleted=false and em.id=?1", BtExpenseManagement.class);

				selectExpenseQuery.setParameter(1, request.getBtExpId());
				expenseManagement = (BtExpenseManagement) selectExpenseQuery.getSingleResult();
			} catch (NoResultException ex) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Expense does not exist");
			}
			System.out.println("BtExpenseManagement  "+expenseManagement.toString());
			if(!expenseManagement.getOwner().getEmailId().equalsIgnoreCase(user.getUsername())) {
				throw new com.pratikshat.exicom.exception.BadRequestException("You are not authorised");
			}
			System.out.println("authorised  ");
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
			System.out.println("record ");
			EmployeeDetails employeeDetails = null;
			try {
				Query selectEmployeeDetailsQuery = entityManager.createQuery(
						"select ed from EmployeeDetails ed left join ed.contact ct left join ct.grade gd where ed.emailId=?1", EmployeeDetails.class);
				selectEmployeeDetailsQuery.setParameter(1, user.getUsername());
				employeeDetails = (EmployeeDetails) selectEmployeeDetailsQuery.getSingleResult();
			} catch (NoResultException ex) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Employee does not exist");
			}
			System.out.println("employeeDetails "+employeeDetails.toString());
			try {
				Query selectExpenseQuery = entityManager.createQuery(
						"select bsa from BtServiceAppointment bsa left join bsa.ownerDetails onr where bsa.serviceAppointmentId=?1", BtServiceAppointment.class);

				selectExpenseQuery.setParameter(1, request.getBtServiceAppointmentId());
				btServiceAppointment = (BtServiceAppointment) selectExpenseQuery.getSingleResult();
			} catch (NoResultException ex) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Expense does not exist");
			}
			System.out.println("btServiceAppointment  "+btServiceAppointment.toString());
			BtServiceAppointmentExp btServiceAppointmentExp=new BtServiceAppointmentExp();
			
			btServiceAppointmentExp.setBtExpenseManagement(expenseManagement);
			btServiceAppointmentExp.setBtServiceAppointment(btServiceAppointment);
			btServiceAppointmentExp.setClaimAmount(request.getClaimAmount());
			btServiceAppointmentExp.setAmountToDisbursed(request.getClaimAmount());
			btServiceAppointmentExp.setCreatedBy(employeeDetails);
			btServiceAppointmentExp.setCreatedDate(new Date());
			btServiceAppointmentExp.setCostCenter(request.getCostCenter());
			btServiceAppointmentExp.setLastModifiedBy(employeeDetails);
			btServiceAppointmentExp.setLastModifiedDate(new Date());
			btServiceAppointmentExp.setRemark(request.getRemark());
			btServiceAppointmentExp.setOwner(employeeDetails);
			System.out.println("saving");
			workflow.executeWorkflow(entityManager, btServiceAppointmentExp, "INSERT", "PRE", user);
			entityManager.persist(btServiceAppointmentExp);
			workflow.executeWorkflow(entityManager, btServiceAppointmentExp, "INSERT", "POST", user);
			System.out.println("btServiceAppointmentExp  "+btServiceAppointmentExp.getId());
//			try {
//				com.pratikshat.exicom.entity.GlobalSearchIndex _search = new com.pratikshat.exicom.entity.GlobalSearchIndex();
//				_search.setKeyName("manualId");
//				_search.setObjectDetailsUrl("boardingexpense/" + btServiceAppointmentExp.getId());
//				_search.setObjectIdName("id");
//				_search.setObjectIdValue("" + btServiceAppointmentExp.getId());
//				_search.setObjectName("BoardingExpense");
//				_search.setSearchString(manualId);
//				entityManager.persist(_search);
//				
//			} catch (Exception e) {
//			}
			System.out.println("btServiceAppointmentExp.getId()  "+btServiceAppointmentExp.getId());
			reqs.setBtServiceAppointmentExpId(btServiceAppointmentExp.getId());
			BtExpenseManagement expense = expenseTotal(expenseManagement, entityManager);
			entityManager.persist(expense);
//			BtServiceAppointmentExpense serviceAppointmentExpense=totalBtServiceAppointmentEXp(expenseManagement, entityManager);
//			serviceAppointmentExpense.setBtServiceAppointment(btServiceAppointment);
//			serviceAppointmentExpense.setCostCenter(request.getCostCenter());
			
			System.out.println("expenseTotal  "+expense.toString());
			entityManager.flush();
			entityManager.clear();
		}
		return reqs;
	}

	@Override
	public BtServiceAppointmentExpRequest updateDetails(BtServiceAppointmentExpRequest request, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		if(request!=null) {
			System.out.println("request  "+request);
			System.out.println("jksjak");
			System.out.println("request.getBtServiceAppointmentExpId()  "+request.getBtServiceAppointmentExpId());
			Query selectBtServiceAppointmentExpQuery=entityManager.createQuery("select sae from BtServiceAppointmentExp sae where sae.isDeleted=false and sae.id=?1",BtServiceAppointmentExp.class);
			selectBtServiceAppointmentExpQuery.setParameter(1,request.getBtServiceAppointmentExpId());
			BtServiceAppointmentExp btServiceAppointmentExp=(BtServiceAppointmentExp) selectBtServiceAppointmentExpQuery.getSingleResult();
			System.out.println("mkjsak");
			System.out.println("btServiceAppointmentExp1  "+btServiceAppointmentExp);
			if(btServiceAppointmentExp!=null) {
				System.out.println("btServiceAppointmentExp12  "+btServiceAppointmentExp);
			BtExpenseManagement expenseManagement=null;
			try {
				Query selectExpenseQuery = entityManager.createQuery(
						"select em from BtExpenseManagement em join em.status st left join em.owner onr where em.isDeleted=false and em.id=?1", BtExpenseManagement.class);

				selectExpenseQuery.setParameter(1, btServiceAppointmentExp.getBtExpenseManagement().getId());
				expenseManagement = (BtExpenseManagement) selectExpenseQuery.getSingleResult();
			} catch (NoResultException ex) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Expense does not exist");
			}
			
			System.out.println("expenseManagement  "+expenseManagement);
			
			
			BtServiceAppointmentExp serviceAppointmentExp=null;
			
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
				}
				else {
					serviceAppointmentExp = updateBtServiceAppointmentExpWithOwner(btServiceAppointmentExp, request, user, entityManager);
				System.out.println("serviceAppointmentExp with owner  "+serviceAppointmentExp);
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
				 serviceAppointmentExp = updateBtServiceAppointmentExpOtherThanOwner(btServiceAppointmentExp, request,expenseManagement.getOwner().getEmailId() , user, entityManager);
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
	                    		serviceAppointmentExp = updateBtServiceAppointmentExpOtherThanOwner(btServiceAppointmentExp, request,expenseManagement.getOwner().getEmailId() , user, entityManager);

	                    	}
	                    }
				
			}}
			}
			//else {
//				ToBeApproved _toBeApproved=null;
//				try {
//					_toBeApproved=entityManager.createQuery("select apr from ToBeApproved apr join apr.assignedTo asg where apr.ObjectName=?1 and apr.objectId=?2 and apr.status=?3",ToBeApproved.class)
//							.setParameter(1,"BtExpenseManagement")
//							.setParameter(2,expenseManagement.getId())
//							.setParameter(3,false)
//							.getSingleResult();
//				}catch(javax.persistence.NoResultException ex) {
//					throw new BadRequestException("You are not authorised");
//				}catch(javax.persistence.NonUniqueResultException ex) {
//					throw new BadRequestException("You are not authorised");
//				}
//				if(_toBeApproved.getAssignedTo().getEmailId().equalsIgnoreCase(user.getUsername())){
//					serviceAppointmentExp = updateBtServiceAppointmentExpOtherThanOwner(btServiceAppointmentExp, request, expenseManagement.getOwner().getEmailId(), user, entityManager);
//				System.out.println("serviceAppointmentExp wothout owner  "+serviceAppointmentExp);
//				}else {
//					throw new BadRequestException("You are not authorised");
//				}
//				
//			}
//			
			System.out.println("final serviceAppointmentExp  "+serviceAppointmentExp);
			workflow.executeWorkflow(entityManager, serviceAppointmentExp, "UPDATE", "PRE", user);
			entityManager.persist(serviceAppointmentExp);
			workflow.executeWorkflow(entityManager, serviceAppointmentExp, "UPDATE", "POST", user);
			request.setBtServiceAppointmentExpId(serviceAppointmentExp.getId());
			
			BtExpenseManagement expense = expenseTotal(expenseManagement, entityManager);
			entityManager.persist(expense);
			
			entityManager.flush();
			entityManager.clear();
			
			return request;
			}
			else {
				throw new BadRequestException("No data found to save details");	
			}
			
			}else {
				throw new BadRequestException("Request Parameter not found");
			}
		
	
		
	}

	private BtServiceAppointmentExp updateBtServiceAppointmentExpWithOwner(BtServiceAppointmentExp btServiceAppointmentExp, BtServiceAppointmentExpRequest request, LoginUser user, EntityManager entityManager)throws BadRequestException {
		
		System.out.println("owner");
			EmployeeDetails employeeDetails = null;
			try {
				Query selectEmployeeDetailsQuery = entityManager.createQuery(
						"select ed from EmployeeDetails ed left join ed.contact ct left join ct.grade gd where ed.emailId=?1", EmployeeDetails.class);
				selectEmployeeDetailsQuery.setParameter(1, user.getUsername());
				employeeDetails = (EmployeeDetails) selectEmployeeDetailsQuery.getSingleResult();
			} catch (NoResultException ex) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Employee does not exist");
			}
			
			System.out.println(" owner email  "+employeeDetails);
			btServiceAppointmentExp.setClaimAmount(request.getClaimAmount());
			btServiceAppointmentExp.setAmountToDisbursed(request.getClaimAmount());
			btServiceAppointmentExp.setLastModifiedBy(employeeDetails);
			btServiceAppointmentExp.setLastModifiedDate(new Date());
			btServiceAppointmentExp.setRemark(request.getRemark());
			
			return btServiceAppointmentExp;
		}
		
		
	private BtServiceAppointmentExp updateBtServiceAppointmentExpOtherThanOwner(BtServiceAppointmentExp btServiceAppointmentExp, BtServiceAppointmentExpRequest request,String ownerEmailId, LoginUser user, EntityManager entityManager)throws BadRequestException {
			
		
			System.out.println("without owner ");
			EmployeeDetails employeeDetails = null;
			try {
				Query selectEmployeeDetailsQuery = entityManager.createQuery(
						"select ed from EmployeeDetails ed left join ed.contact ct left join ct.grade gd where ed.emailId=?1", EmployeeDetails.class);
				selectEmployeeDetailsQuery.setParameter(1, ownerEmailId);
				employeeDetails = (EmployeeDetails) selectEmployeeDetailsQuery.getSingleResult();
			} catch (NoResultException ex) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Employee does not exist");
			}
			System.out.println(" without owner email  "+employeeDetails);
			btServiceAppointmentExp.setAmountToDisbursed(request.getAmountToDisbursed());
			btServiceAppointmentExp.setLastModifiedBy(new EmployeeDetails(user.getUsername()));
			btServiceAppointmentExp.setLastModifiedDate(new Date());
			btServiceAppointmentExp.setRemark(request.getRemark());
			
			
			
			return btServiceAppointmentExp;
		}

	
	public BtServiceAppointmentExpense totalBtServiceAppointmentEXp(BtExpenseManagement expenseManagement, EntityManager entityManager)
	{
		BtServiceAppointmentExpense expense=new BtServiceAppointmentExpense();
		
		Object[] sumBtServiceAppoinmentExp = null;
		double totalBtServiceAppoinmentExp = 0;
		
		try {
			Query sumBtServiceAppoinmentExpQuery = entityManager.createQuery(
					"select coalesce(sum(sae.claimAmount),0) as totalAmount from BtServiceAppointmentExp sae left join sae.btExpenseManagement em where sae.isDeleted=false and em.isDeleted=false and em.id=?1");
			sumBtServiceAppoinmentExpQuery.setParameter(1, expenseManagement.getId());
			sumBtServiceAppoinmentExp = (Object[]) sumBtServiceAppoinmentExpQuery.getSingleResult();
		} catch (NoResultException ex) {

		}
		
		if(sumBtServiceAppoinmentExp!=null) {
			totalBtServiceAppoinmentExp = (double)sumBtServiceAppoinmentExp[0];
			
		}
		
		expense.setBtExpenseManagement(expenseManagement);
		expense.setTotalClaimAmount(totalBtServiceAppoinmentExp);
		return expense;
		
	}
		public BtExpenseManagement expenseTotal(BtExpenseManagement expenseManagement, EntityManager entityManager) {
System.out.println("jnksja");
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

			}
			
			if (sumTourExpenseExpense != null) {
				totalTourAmount = (double) sumTourExpenseExpense[0];
				totalTourDays = (double) sumTourExpenseExpense[1];
				totalTourAmountToDisbursed = (double) sumTourExpenseExpense[2];
			}
			
			System.out.println("kcjxkzj");
			double totalTourAmountCompanyPaid = 0;
			try {
				Query sumTourExpenseCompanyPaidQuery = entityManager.createQuery(
						"select coalesce(sum(te.fare),0) as totalFare from BtTourExpense te left join te.btExpenseManagement em where te.isDeleted=false and em.isDeleted=false and te.companyPaid=true and em.id=?1");
				sumTourExpenseCompanyPaidQuery.setParameter(1, expenseManagement.getId());
				totalTourAmountCompanyPaid = (double) sumTourExpenseCompanyPaidQuery.getSingleResult();
			} catch (NoResultException ex) {

			}
			System.out.println("[][]");
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

			}
			System.out.println("p[p[xz");
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

			}

			System.out.println("pocpiza");
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

			}
			
			if(sumLocalConveyanceExpense!=null) {
				totalLocalConveyanceAmount = (double)sumLocalConveyanceExpense[0];
				totalLocalConveyanceAmountToDisbursed = (double)sumLocalConveyanceExpense[1];
			}
         System.out.println("nn xz");
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
			
			System.out.println("fdfdsfa");
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

			}

			if (sumBoardingExpense != null) {
				totalBoardingDays = (double) sumBoardingExpense[0];
				totalBoardingAmount = (double) sumBoardingExpense[1];
				totalBoardingAmountToDisbursed = (double)sumBoardingExpense[2];
			}
System.out.println("-=-=-");
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

			}
			
			if(sumOtherExpense!=null) {
				totalOtherChargesAmount = (double)sumOtherExpense[0];
				totalOtherChargesAmountToDisbursed = (double) sumOtherExpense[1];
			}
			
			
			System.out.println("nbnxz");
			
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
			expenseManagement.setTotalServiceAppointmentAmount(totalBtServiceAppoinmentExpClaimAmount);
			
			expenseManagement.setTotalBpAmount(totalBpAmount);
			
			totalExpense = totalTourAmount+totalLodgingAmount+totalLocalConveyanceAmount+totalBoardingAmount+totalOtherChargesAmount+totalBtServiceAppoinmentExpClaimAmount;
			expenseManagement.setTotalExpense(totalExpense);
			
			totalCompanyPaid = totalTourAmountCompanyPaid+totalLodgingCompanyPaid;
			expenseManagement.setTotalCompanyPaid(totalCompanyPaid);
System.out.println("asaa");
			double totalAmountToDisbursed = totalTourAmountToDisbursed+totalLodgingAmountToDisbursed+totalLocalConveyanceAmountToDisbursed+totalBoardingAmountToDisbursed+totalOtherChargesAmountToDisbursed+totalBtServiceAppoinmentExpAmountToDisbursed;
			netAmountToPay = totalAmountToDisbursed-expenseManagement.getAdvanceAmount();
			expenseManagement.setNetAmountToPay(netAmountToPay);
			
			return expenseManagement;
		}
		
	@Override
	public BtServiceAppointmentExpRequest deleteDetails(BtServiceAppointmentExpRequest request, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public StatusMessageResponse deleteBtServiceAppointmentExp(long id, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		StatusMessageResponse response = new StatusMessageResponse();
		if (id != 0) {
		Query selectBtServiceAppointmentExpQuery=entityManager.createQuery("select te from BtServiceAppointmentExp te left join te.btExpenseManagement em where te.isDeleted=false and te.id=?1",BtServiceAppointmentExp.class);
		selectBtServiceAppointmentExpQuery.setParameter(1,id);
		BtServiceAppointmentExp btServiceAppointmentExp=(BtServiceAppointmentExp)selectBtServiceAppointmentExpQuery.getSingleResult();
		
		if(btServiceAppointmentExp!=null) {
			
			BtExpenseManagement expenseManagement = null;
			try {
				Query selectExpenseQuery = entityManager.createQuery(
						"select em from BtExpenseManagement em join em.status st left join em.owner onr where em.isDeleted=false and em.id=?1", BtExpenseManagement.class);

				selectExpenseQuery.setParameter(1, btServiceAppointmentExp.getBtExpenseManagement().getId());
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
				
//				BtServiceAppointment btServiceAppointment=new BtServiceAppointment();
				
				
				BtServiceAppointment btServiceAppointment=null;
				try {
				Query createAppointQuery=entityManager.createQuery("select sa from BtServiceAppointment sa left join BtServiceAppointmentExp te on te.btServiceAppointment=sa.serviceAppointmentId "
						+ "where te.id=?1",BtServiceAppointment.class);
				createAppointQuery.setParameter(1, id);
				btServiceAppointment=(BtServiceAppointment) createAppointQuery.getSingleResult();
				}catch(NoResultException rs) {
					throw new BadRequestException("Service appointment does not exist");
				}
				
				
				
				
				btServiceAppointmentExp.setDeleted(true);
				btServiceAppointmentExp.setLastModifiedBy(new EmployeeDetails(user.getUsername()));
				btServiceAppointmentExp.setLastModifiedDate(new Date());
				System.out.println("PREddf");
				
				workflow.executeWorkflow(entityManager, btServiceAppointmentExp, "UPDATE", "PRE", user);
				entityManager.persist(btServiceAppointmentExp);
				workflow.executeWorkflow(entityManager, btServiceAppointmentExp, "UPDATE", "POST", user);

				BtExpenseManagement expense = expenseTotal(expenseManagement, entityManager);
				
				workflow.executeWorkflow(entityManager, expense, "UPDATE", "PRE", user);
				entityManager.persist(expense);
				workflow.executeWorkflow(entityManager, expense, "UPDATE", "POST", user);

				btServiceAppointment.setServiceAppointmentExpense(false);
				btServiceAppointmentImpl.updateDetails(btServiceAppointment, user);
				
				entityManager.flush();
				entityManager.clear();
			}
			response.setMessage("btServiceAppointment Expense Deleted successfully");
			return response;
		}else {
			throw new BadRequestException("btServiceAppointment Exp does not exist");	
		}
	} else {
		throw new BadRequestException("Request Parameter not found");
	}
	}
}
