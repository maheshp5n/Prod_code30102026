package com.pratikshat.exicom.repository;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pratikshat.exicom.entity.BoardingExpense;
import com.pratikshat.exicom.entity.BoardingLoadingDetails;
import com.pratikshat.exicom.entity.CityDetails;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.ExpenseManagement;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.ToBeApproved;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.BoardingExpenseRequest;

@Repository
public class BoardingExpenseCustomeRepositoryImpl implements BoardingExpenseCustomeRepository {

	@PersistenceContext
	EntityManager entityManager;
	@Autowired
	com.pratikshat.exicom.interceptor.WorkflowExecuter workflow;
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat sdfDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	@Transactional(isolation = Isolation.READ_UNCOMMITTED,propagation = Propagation.REQUIRES_NEW,rollbackFor = {Exception.class,RuntimeException.class,BadRequestException.class,UnauthoriseException.class,SQLException.class})
	@Override
	public BoardingExpenseRequest saveBoardingExpense(BoardingExpenseRequest request, LoginUser user) throws Exception {
		if (request != null) {
			
			Date fromDate = sdfDateTime.parse(sdf.format(request.getFromDate())+" "+request.getFromTime().trim());
			
			Date toDate = sdfDateTime.parse(sdf.format(request.getToDate())+" "+request.getToTime().trim());
			
			long diff = (toDate.getTime() - fromDate.getTime())/1000;
			if(diff<=0) {
				throw new com.pratikshat.exicom.exception.BadRequestException("To date/time must be grater than the from date/time");
			}
			
			int days = (int) (diff / (24*60*60));
	        long hours = diff/(60*60)%24;
	        
	        double noOfDays = 0;
	        
	        if(hours<6) {
	        	noOfDays = days;
	        }else if(hours>=6 && hours<=12) {
	        	noOfDays = days+0.5;
	        }else if(hours>12) {
	        	noOfDays = days+1;
	        }
			
			ExpenseManagement expenseManagement = null;
			try {
				Query selectExpenseQuery = entityManager.createQuery(
						"select em from ExpenseManagement em join em.status st left join em.owner onr where em.isDeleted=false and em.expenseManagementId=?1", ExpenseManagement.class);

				selectExpenseQuery.setParameter(1, request.getExpenseManagementId());
				expenseManagement = (ExpenseManagement) selectExpenseQuery.getSingleResult();
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
			if(!list.contains(statusId)) {
				throw new BadRequestException("You can't able to edit this record");
			}
			
			
			EmployeeDetails employeeDetails = null;
			try {
				Query selectEmployeeDetailsQuery = entityManager.createQuery(
						"select ed from EmployeeDetails ed left join ed.contact ct left join ct.grade gd where ed.emailId=?1", EmployeeDetails.class);
				selectEmployeeDetailsQuery.setParameter(1, user.getUsername());
				employeeDetails = (EmployeeDetails) selectEmployeeDetailsQuery.getSingleResult();
			} catch (NoResultException ex) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Employee does not exist");
			}
			
			
			CityDetails cityDetails = null;
			try {
				Query selectCityQuery = entityManager.createQuery(
						"select cd from CityDetails cd left join cd.cityCatagory cat where cd.cityName=?1", CityDetails.class);
				selectCityQuery.setParameter(1, request.getCityName());
				cityDetails = (CityDetails) selectCityQuery.getSingleResult();
			} catch (NoResultException ex) {
				throw new com.pratikshat.exicom.exception.BadRequestException("City does not exist");
			}
			
			
			double amount = 0;
			BoardingExpense boardingExpense = new BoardingExpense();
			
			if(employeeDetails.getContact()!=null) {
				BoardingLoadingDetails grade = employeeDetails.getContact().getGrade();
				if(grade!=null) {
					double boardingAmount = 0;
					if(cityDetails.getCityCatagory().getCityCatagoryName().equalsIgnoreCase("A")) {
						boardingAmount = grade.getBoardingAmountCityA();
					}else if(cityDetails.getCityCatagory().getCityCatagoryName().equalsIgnoreCase("B")) {
						boardingAmount = grade.getBoardingAmountCityB();
					}else if(cityDetails.getCityCatagory().getCityCatagoryName().equalsIgnoreCase("C")) {
						boardingAmount = grade.getBoardingAmountCityC();
					}
					
					amount = noOfDays*boardingAmount;
					
					boardingExpense.setCityCategory(cityDetails.getCityCatagory().getCityCatagoryName());
					boardingExpense.setEntitlement(amount);
					
					
					
				}	
			}
			
			
			
			boardingExpense.setAmount(amount);
			boardingExpense.setAmountToDisbursed(amount);
			boardingExpense.setCity(cityDetails);
			boardingExpense.setCreateDate(new Date());
			boardingExpense.setCreatedBy(employeeDetails);
			boardingExpense.setExpenseManagement(expenseManagement);
			boardingExpense.setFromDate(request.getFromDate());
			boardingExpense.setFromTime(request.getFromTime());
			boardingExpense.setModifiedBy(employeeDetails);
			boardingExpense.setModifiedDate(new Date());
			boardingExpense.setNoOfDayes(noOfDays);
			boardingExpense.setOtherCity(request.getOtherCity());
			boardingExpense.setOwnerDetails(employeeDetails);
			boardingExpense.setRemark(request.getRemark());
			boardingExpense.setToDate(request.getToDate());
			boardingExpense.setToTime(request.getToTime());
			
			workflow.executeWorkflow(entityManager, boardingExpense, "INSERT", "PRE", user);
			entityManager.persist(boardingExpense);
			workflow.executeWorkflow(entityManager, boardingExpense, "INSERT", "POST", user);
			
			Query updateManualId = entityManager.createQuery("update BoardingExpense be set be.manualId=?1 where be.id=?2");
			SimpleDateFormat dateformatter = new SimpleDateFormat("yyyyMMdd");
			String manualId = "BE-" + dateformatter.format(new java.util.Date()) + "-"
					+ com.pratikshat.exicom.config.MiscellaneousUtils.fillZero(String.valueOf(boardingExpense.getId()), 5);
			updateManualId.setParameter(1, manualId);
			updateManualId.setParameter(2, boardingExpense.getId());
			updateManualId.executeUpdate();
			
			request.setId(boardingExpense.getId());

			// search index entry
			try {
				com.pratikshat.exicom.entity.GlobalSearchIndex _search = new com.pratikshat.exicom.entity.GlobalSearchIndex();
				_search.setKeyName("manualId");
				_search.setObjectDetailsUrl("boardingexpense/" + boardingExpense.getId());
				_search.setObjectIdName("id");
				_search.setObjectIdValue("" + boardingExpense.getId());
				_search.setObjectName("BoardingExpense");
				_search.setSearchString(manualId);
				entityManager.persist(_search);
				
			} catch (Exception e) {
			}
			
			ExpenseManagement expense = expenseTotal(expenseManagement, entityManager);
			workflow.executeWorkflow(entityManager, expense, "UPDATE", "PRE", user);
			entityManager.persist(expense);
			workflow.executeWorkflow(entityManager, expense, "UPDATE", "POST", user);
			
			
			entityManager.flush();
			entityManager.clear();
			return request;
			
		} else {
			throw new Exception("Request Parameter not found");
		}
	}

	@Override
	@Transactional(isolation = Isolation.READ_UNCOMMITTED,propagation = Propagation.REQUIRES_NEW,rollbackFor = {Exception.class,RuntimeException.class,BadRequestException.class,UnauthoriseException.class,SQLException.class})
	public BoardingExpenseRequest updateBoardingExpense(BoardingExpenseRequest request, LoginUser user) throws Exception {
		if (request != null) {
			Query selectBoardingExpenseQuery=entityManager.createQuery("select be from BoardingExpense be where be.isDeleted=false and be.id=?1",BoardingExpense.class);
			selectBoardingExpenseQuery.setParameter(1,request.getId());
			BoardingExpense boardingExpense=(BoardingExpense)selectBoardingExpenseQuery.getSingleResult();
			
			if(boardingExpense!=null) {
				
				ExpenseManagement expenseManagement = null;
				try {
					Query selectExpenseQuery = entityManager.createQuery(
							"select em from ExpenseManagement em join em.status st left join em.owner onr where em.isDeleted=false and em.expenseManagementId=?1", ExpenseManagement.class);

					selectExpenseQuery.setParameter(1, request.getExpenseManagementId());
					expenseManagement = (ExpenseManagement) selectExpenseQuery.getSingleResult();
				} catch (NoResultException ex) {
					throw new com.pratikshat.exicom.exception.BadRequestException("Expense does not exist");
				}
				
				BoardingExpense bExpense = null;
				
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
						bExpense = updateBoardingExpenseWithOwner(boardingExpense, request, user, entityManager);
					}
					
					//throw new com.pratikshat.exicom.exception.BadRequestException("You are not authorised");
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
					if(_toBeApproved.getAssignedTo().getEmailId().equalsIgnoreCase(user.getUsername())){
						bExpense = updateBoardingExpenseOtherThanOwner(boardingExpense, request, expenseManagement.getOwner().getEmailId(), user, entityManager);
					}else {
						throw new BadRequestException("You are not authorised");
					}
					
				}
				
				
				
				
				workflow.executeWorkflow(entityManager, bExpense, "UPDATE", "PRE", user);
				entityManager.persist(bExpense);
				workflow.executeWorkflow(entityManager, bExpense, "UPDATE", "POST", user);

				ExpenseManagement expense = expenseTotal(expenseManagement, entityManager);
				
				workflow.executeWorkflow(entityManager, expense, "UPDATE", "PRE", user);
				entityManager.persist(expense);
				workflow.executeWorkflow(entityManager, expense, "UPDATE", "POST", user);

				entityManager.flush();
				entityManager.clear();
				return request;
			}else {
				throw new Exception("No data found to save details");	
			}
		} else {
			throw new Exception("Request Parameter not found");
		}
	}
	
	private BoardingExpense updateBoardingExpenseWithOwner(BoardingExpense boardingExpense, BoardingExpenseRequest request, LoginUser user, EntityManager entityManager)throws Exception {
		
		Date fromDate = sdfDateTime.parse(sdf.format(request.getFromDate())+" "+request.getFromTime().trim());
		
		Date toDate = sdfDateTime.parse(sdf.format(request.getToDate())+" "+request.getToTime().trim());
		
		long diff = (toDate.getTime() - fromDate.getTime())/1000;
		if(diff<=0) {
			throw new com.pratikshat.exicom.exception.BadRequestException("To date/time must be grater than the from date/time");
		}
		
		int days = (int) (diff / (24*60*60));
        long hours = diff/(60*60)%24;
        
        double noOfDays = 0;
        
        if(hours<6) {
        	noOfDays = days;
        }else if(hours>=6 && hours<=12) {
        	noOfDays = days+0.5;
        }else if(hours>12) {
        	noOfDays = days+1;
        }
		
		EmployeeDetails employeeDetails = null;
		try {
			Query selectEmployeeDetailsQuery = entityManager.createQuery(
					"select ed from EmployeeDetails ed left join ed.contact ct left join ct.grade gd where ed.emailId=?1", EmployeeDetails.class);
			selectEmployeeDetailsQuery.setParameter(1, user.getUsername());
			employeeDetails = (EmployeeDetails) selectEmployeeDetailsQuery.getSingleResult();
		} catch (NoResultException ex) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Employee does not exist");
		}
		
		
		
		CityDetails cityDetails = null;
		try {
			Query selectCityQuery = entityManager.createQuery(
					"select cd from CityDetails cd left join cd.cityCatagory cat where cd.cityName=?1", CityDetails.class);
			selectCityQuery.setParameter(1, request.getCityName());
			cityDetails = (CityDetails) selectCityQuery.getSingleResult();
		} catch (NoResultException ex) {
			throw new com.pratikshat.exicom.exception.BadRequestException("City does not exist");
		}
		
		double amount = 0;
		BoardingLoadingDetails grade = employeeDetails.getContact().getGrade();
		if(grade!=null) {
			double boardingAmount = 0;
			if(cityDetails.getCityCatagory().getCityCatagoryName().equalsIgnoreCase("A")) {
				boardingAmount = grade.getBoardingAmountCityA();
			}else if(cityDetails.getCityCatagory().getCityCatagoryName().equalsIgnoreCase("B")) {
				boardingAmount = grade.getBoardingAmountCityB();
			}else if(cityDetails.getCityCatagory().getCityCatagoryName().equalsIgnoreCase("C")) {
				boardingAmount = grade.getBoardingAmountCityC();
			}
			amount = noOfDays*boardingAmount;
			boardingExpense.setCityCategory(cityDetails.getCityCatagory().getCityCatagoryName());
			boardingExpense.setEntitlement(amount);
			
		}
		
		
		boardingExpense.setAmount(amount);
		boardingExpense.setAmountToDisbursed(amount);
		boardingExpense.setCity(cityDetails);
		boardingExpense.setFromDate(request.getFromDate());
		boardingExpense.setFromTime(request.getFromTime());
		boardingExpense.setModifiedBy(employeeDetails);
		boardingExpense.setModifiedDate(new Date());
		boardingExpense.setNoOfDayes(noOfDays);
		boardingExpense.setOtherCity(request.getOtherCity());
		boardingExpense.setRemark(request.getRemark());
		boardingExpense.setToDate(request.getToDate());
		boardingExpense.setToTime(request.getToTime());
		
		return boardingExpense;
	}
	
	
private BoardingExpense updateBoardingExpenseOtherThanOwner(BoardingExpense boardingExpense, BoardingExpenseRequest request,String ownerEmailId, LoginUser user, EntityManager entityManager)throws Exception {
		
		Date fromDate = sdfDateTime.parse(sdf.format(request.getFromDate())+" "+request.getFromTime().trim());
		
		Date toDate = sdfDateTime.parse(sdf.format(request.getToDate())+" "+request.getToTime().trim());
		
		long diff = (toDate.getTime() - fromDate.getTime())/1000;
		if(diff<=0) {
			throw new com.pratikshat.exicom.exception.BadRequestException("To date/time must be grater than the from date/time");
		}
		
		int days = (int) (diff / (24*60*60));
        long hours = diff/(60*60)%24;
        
        double noOfDays = 0;
        
        if(hours<6) {
        	noOfDays = days;
        }else if(hours>=6 && hours<=12) {
        	noOfDays = days+0.5;
        }else if(hours>12) {
        	noOfDays = days+1;
        }
		
		EmployeeDetails employeeDetails = null;
		try {
			Query selectEmployeeDetailsQuery = entityManager.createQuery(
					"select ed from EmployeeDetails ed left join ed.contact ct left join ct.grade gd where ed.emailId=?1", EmployeeDetails.class);
			selectEmployeeDetailsQuery.setParameter(1, ownerEmailId);
			employeeDetails = (EmployeeDetails) selectEmployeeDetailsQuery.getSingleResult();
		} catch (NoResultException ex) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Employee does not exist");
		}
		
		
		
		CityDetails cityDetails = null;
		try {
			Query selectCityQuery = entityManager.createQuery(
					"select cd from CityDetails cd left join cd.cityCatagory cat where cd.cityName=?1", CityDetails.class);
			selectCityQuery.setParameter(1, request.getCityName());
			cityDetails = (CityDetails) selectCityQuery.getSingleResult();
		} catch (NoResultException ex) {
			throw new com.pratikshat.exicom.exception.BadRequestException("City does not exist");
		}
		
		double amount = 0;
		BoardingLoadingDetails grade = employeeDetails.getContact().getGrade();
		if(grade!=null) {
			double boardingAmount = 0;
			if(cityDetails.getCityCatagory().getCityCatagoryName().equalsIgnoreCase("A")) {
				boardingAmount = grade.getBoardingAmountCityA();
			}else if(cityDetails.getCityCatagory().getCityCatagoryName().equalsIgnoreCase("B")) {
				boardingAmount = grade.getBoardingAmountCityB();
			}else if(cityDetails.getCityCatagory().getCityCatagoryName().equalsIgnoreCase("C")) {
				boardingAmount = grade.getBoardingAmountCityC();
			}
			amount = noOfDays*boardingAmount;
			boardingExpense.setCityCategory(cityDetails.getCityCatagory().getCityCatagoryName());
			boardingExpense.setEntitlement(amount);
			
		}
		
		
		boardingExpense.setAmount(amount);
		boardingExpense.setAmountToDisbursed(request.getAmountToDisbursed());
		boardingExpense.setCity(cityDetails);
		boardingExpense.setFromDate(request.getFromDate());
		boardingExpense.setFromTime(request.getFromTime());
		boardingExpense.setModifiedBy(new EmployeeDetails(user.getUsername()));
		boardingExpense.setModifiedDate(new Date());
		boardingExpense.setNoOfDayes(noOfDays);
		boardingExpense.setOtherCity(request.getOtherCity());
		boardingExpense.setRemark(request.getRemark());
		boardingExpense.setToDate(request.getToDate());
		boardingExpense.setToTime(request.getToTime());
		
		return boardingExpense;
	}
	
	
	public ExpenseManagement expenseTotal(ExpenseManagement expenseManagement, EntityManager entityManager) {

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
					"select coalesce(sum(te.fare),0) as totalFare, coalesce(sum(te.noOfDays),0) as totalDays, coalesce(sum(te.amountToDisbursed),0) as totalAmountToDisbursed from TourExpense te left join te.expenseManagement em where te.isDeleted=false and em.isDeleted=false and em.expenseManagementId=?1");
			sumTourExpenseQuery.setParameter(1, expenseManagement.getExpenseManagementId());
			sumTourExpenseExpense = (Object[]) sumTourExpenseQuery.getSingleResult();
		} catch (NoResultException ex) {

		}
		
		if (sumTourExpenseExpense != null) {
			totalTourAmount = (double) sumTourExpenseExpense[0];
			totalTourDays = (double) sumTourExpenseExpense[1];
			totalTourAmountToDisbursed = (double) sumTourExpenseExpense[2];
		}
		

		double totalTourAmountCompanyPaid = 0;
		try {
			Query sumTourExpenseCompanyPaidQuery = entityManager.createQuery(
					"select coalesce(sum(te.fare),0) as totalFare from TourExpense te left join te.expenseManagement em where te.isDeleted=false and em.isDeleted=false and te.companyPaid=true and em.expenseManagementId=?1");
			sumTourExpenseCompanyPaidQuery.setParameter(1, expenseManagement.getExpenseManagementId());
			totalTourAmountCompanyPaid = (double) sumTourExpenseCompanyPaidQuery.getSingleResult();
		} catch (NoResultException ex) {

		}
		
		// Lodging Expense
		Object[] sumLodgingExpense = null;
		double totalLodgingAmount = 0;
		double totalLodgingDays = 0;
		double totalLodgingAmountToDisbursed = 0;
		try {
			Query sumLodgingExpenseQuery = entityManager.createQuery(
					"select coalesce(sum(le.amount),0) as totalAmount, coalesce(sum(le.NoOfDayes),0) as totalDays, coalesce(sum(le.amountToDisbursed),0) as totalAmountToDisbursed from LodgingExpense le left join le.expenseManagement em where le.isDeleted=false and em.isDeleted=false and em.expenseManagementId=?1");
			sumLodgingExpenseQuery.setParameter(1, expenseManagement.getExpenseManagementId());
			sumLodgingExpense = (Object[]) sumLodgingExpenseQuery.getSingleResult();
		} catch (NoResultException ex) {

		}
		
		if (sumLodgingExpense != null) {
			totalLodgingAmount = (double) sumLodgingExpense[0];
			totalLodgingDays = (double) sumLodgingExpense[1];
			totalLodgingAmountToDisbursed = (double) sumLodgingExpense[2];
		}

		double totalLodgingCompanyPaid = 0;
		try {
			Query sumLodgingCompanyPaidQuery = entityManager.createQuery(
					"select coalesce(sum(le.amount),0) as totalFare from LodgingExpense le left join le.expenseManagement em where le.isDeleted=false and em.isDeleted=false and le.isComapnyPaid=true and em.expenseManagementId=?1");
			sumLodgingCompanyPaidQuery.setParameter(1, expenseManagement.getExpenseManagementId());
			totalLodgingCompanyPaid = (double) sumLodgingCompanyPaidQuery.getSingleResult();
		} catch (NoResultException ex) {

		}

		// Local Conveyance
		Object[] sumLocalConveyanceExpense = null;
		double totalLocalConveyanceAmount = 0;
		double totalLocalConveyanceAmountToDisbursed = 0;
		try {
			Query sumLocalConveyanceQuery = entityManager.createQuery(
					"select coalesce(sum(lce.amount),0) as totalAmount, coalesce(sum(lce.amountToDisbursed),0) as totalAmountToDisbursed from LocalConveyanceExpense lce left join lce.expenseManagement em where lce.isDeleted=false and em.isDeleted=false and em.expenseManagementId=?1");
			sumLocalConveyanceQuery.setParameter(1, expenseManagement.getExpenseManagementId());
			sumLocalConveyanceExpense = (Object[]) sumLocalConveyanceQuery.getSingleResult();
		} catch (NoResultException ex) {

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
					"select coalesce(sum(be.NoOfDayes),0) as totalDays, coalesce(sum(be.amount),0) as totalAmount, coalesce(sum(be.amountToDisbursed),0) as totalAmountToDisbursed from BoardingExpense be left join be.expenseManagement em where be.isDeleted=false and em.isDeleted=false and em.expenseManagementId=?1");
			sumBoardingExpenseQuery.setParameter(1, expenseManagement.getExpenseManagementId());
			sumBoardingExpense = (Object[]) sumBoardingExpenseQuery.getSingleResult();
		} catch (NoResultException ex) {

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
					"select coalesce(sum(och.amount),0) as totalAmount, coalesce(sum(och.amountToDisbursed),0) as totalAmountToDisbursed from OtherChargesExpense och left join och.expenseManagement em where och.isDeleted=false and em.isDeleted=false and em.expenseManagementId=?1");
			sumOtherChargesQuery.setParameter(1, expenseManagement.getExpenseManagementId());
			sumOtherExpense = (Object[]) sumOtherChargesQuery.getSingleResult();
		} catch (NoResultException ex) {

		}
		
		if(sumOtherExpense!=null) {
			totalOtherChargesAmount = (double)sumOtherExpense[0];
			totalOtherChargesAmountToDisbursed = (double) sumOtherExpense[1];
		}
		
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
		
		totalExpense = totalTourAmount+totalLodgingAmount+totalLocalConveyanceAmount+totalBoardingAmount+totalOtherChargesAmount;
		expenseManagement.setTotalExpense(totalExpense);
		
		totalCompanyPaid = totalTourAmountCompanyPaid+totalLodgingCompanyPaid;
		expenseManagement.setTotalCompanyPaid(totalCompanyPaid);

		double totalAmountToDisbursed = totalTourAmountToDisbursed+totalLodgingAmountToDisbursed+totalLocalConveyanceAmountToDisbursed+totalBoardingAmountToDisbursed+totalOtherChargesAmountToDisbursed;
		netAmountToPay = totalAmountToDisbursed-expenseManagement.getAdvanceAmount();
		expenseManagement.setNetAmountToPay(netAmountToPay);
		
		return expenseManagement;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW,isolation=Isolation.READ_UNCOMMITTED,rollbackFor= {BadRequestException.class,UnauthoriseException.class,RuntimeException.class,Exception.class,SQLException.class})
	public long deleteBoardingExpense(long boardingExpenseId, LoginUser user) throws Exception {
	
		if (boardingExpenseId != 0) {
			Query selectBoardingExpenseQuery=entityManager.createQuery("select be from BoardingExpense be left join be.expenseManagement em where be.isDeleted=false and be.id=?1",BoardingExpense.class);
			selectBoardingExpenseQuery.setParameter(1,boardingExpenseId);
			BoardingExpense boardingExpense=(BoardingExpense)selectBoardingExpenseQuery.getSingleResult();
			
			if(boardingExpense!=null) {
				
				ExpenseManagement expenseManagement = null;
				try {
					Query selectExpenseQuery = entityManager.createQuery(
							"select em from ExpenseManagement em join em.status st left join em.owner onr where em.isDeleted=false and em.expenseManagementId=?1", ExpenseManagement.class);

					selectExpenseQuery.setParameter(1, boardingExpense.getExpenseManagement().getExpenseManagementId());
					expenseManagement = (ExpenseManagement) selectExpenseQuery.getSingleResult();
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
					if(!list.contains(statusId)) {
						throw new BadRequestException("You can't able to delete this record");
					}
					
					
					boardingExpense.setDeleted(true);
					boardingExpense.setModifiedBy(new EmployeeDetails(user.getUsername()));
					boardingExpense.setModifiedDate(new Date());
					
					workflow.executeWorkflow(entityManager, boardingExpense, "UPDATE", "PRE", user);
					entityManager.persist(boardingExpense);
					workflow.executeWorkflow(entityManager, boardingExpense, "UPDATE", "POST", user);

					ExpenseManagement expense = expenseTotal(expenseManagement, entityManager);
					
					workflow.executeWorkflow(entityManager, expense, "UPDATE", "PRE", user);
					entityManager.persist(expense);
					workflow.executeWorkflow(entityManager, expense, "UPDATE", "POST", user);

					entityManager.flush();
					entityManager.clear();
				}
				
				return boardingExpenseId;
			}else {
				throw new Exception("Boarding Expense does not exist");	
			}
		} else {
			throw new Exception("Request Parameter not found");
		}
	}
}
