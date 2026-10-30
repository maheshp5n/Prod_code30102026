package com.pratikshat.exicom.utils;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.pratikshat.exicom.entity.BoardingExpense;
import com.pratikshat.exicom.entity.ExpenseManagement;
import com.pratikshat.exicom.entity.LocalConveyanceExpense;
import com.pratikshat.exicom.entity.LodgingExpense;
import com.pratikshat.exicom.entity.TourExpense;
import com.pratikshat.exicom.exception.BadRequestException;

public class ExpenseApprovedAmountCalculation {
	private long objectId;
	private String userEmaiid;
	private javax.persistence.EntityManager entityManager;
	
	public ExpenseApprovedAmountCalculation(long objectId,String userEmaiid,javax.persistence.EntityManager entityManager) {
	
		this.objectId = objectId;
		this.userEmaiid = userEmaiid;
		this.entityManager = entityManager;
	}

	public void calculate() throws BadRequestException {
		
		ExpenseManagement expenseManagement = null;
		
		try {
			Query getQuery = entityManager.createQuery(
					"select em from ExpenseManagement em left join em.businessDomain bd left join em.contact cnt left join em.expensePurpose empur left join em.expenseType emty left join em.costCenter cc left join em.status st left join em.businessArea barea left join em.businessPlace bplc left join em.createdBy cby left join em.modifiedBy mby left join em.owner own where em.expenseManagementId=?1", ExpenseManagement.class);
			getQuery.setParameter(1, objectId);
			expenseManagement = (ExpenseManagement) getQuery.getSingleResult();
		}catch(NoResultException ex) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Expense does not exist");
		}
		
		// Tour Expense
		List<TourExpense> tourExpenses = null;
		try {
			Query tourExpenseQuery = entityManager.createQuery(
					"select te from TourExpense te left join te.expenseManagement em where em.expenseManagementId=?1");
			tourExpenseQuery.setParameter(1, objectId);
			tourExpenses = tourExpenseQuery.getResultList();
		} catch (NoResultException ex) {

		}
		
		if(tourExpenses!=null && !tourExpenses.isEmpty()) {
			for(int i=0; i<tourExpenses.size(); i++) {
				TourExpense te = tourExpenses.get(i);
				double fare = te.getFare();
				te.setApprovedAmount(fare);
				entityManager.persist(te);
			}
		}
		
		
		//Lodging Expense
		List<LodgingExpense> lodgingExpenses = null;
		try {
			Query lodgingExpenseQuery = entityManager.createQuery(
					"select le from LodgingExpense le left join le.expenseManagement em where em.expenseManagementId=?1");
			lodgingExpenseQuery.setParameter(1, objectId);
			lodgingExpenses = lodgingExpenseQuery.getResultList();
		} catch (NoResultException ex) {

		}
		
		if(lodgingExpenses!=null && !lodgingExpenses.isEmpty()) {
			for(int i=0; i<lodgingExpenses.size(); i++) {
				LodgingExpense le = lodgingExpenses.get(i);
				double amount = le.getAmount();
				double entitlement = le.getEntitlement();
				if(amount<entitlement) {
					le.setApprovedAmount(amount);
				}else {
					le.setApprovedAmount(entitlement);
				}
				entityManager.persist(le);
			}
		}
		
		
		
		// Tour Expense
		double totalApprovedTourAmount = 0;
		try {
			Query sumTourExpenseQuery = entityManager.createQuery(
					"select coalesce(sum(te.approvedAmount),0) as totalApprovedAmount from TourExpense te left join te.expenseManagement em where te.isDeleted=false and em.expenseManagementId=?1");
			sumTourExpenseQuery.setParameter(1, expenseManagement.getExpenseManagementId());
			totalApprovedTourAmount = (double) sumTourExpenseQuery.getSingleResult();
		} catch (NoResultException ex) {

		}
		
		double totalTourAmountCompanyPaid = 0;
		try {
			Query sumTourExpenseCompanyPaidQuery = entityManager.createQuery(
					"select coalesce(sum(te.approvedAmount),0) as totalApprovedAmount from TourExpense te left join te.expenseManagement em where te.isDeleted=false and te.companyPaid=true and em.expenseManagementId=?1");
			sumTourExpenseCompanyPaidQuery.setParameter(1, expenseManagement.getExpenseManagementId());
			totalTourAmountCompanyPaid = (double) sumTourExpenseCompanyPaidQuery.getSingleResult();
		} catch (NoResultException ex) {

		}

		// Lodging Expense
		double totalApprovedLodgingAmount = 0;
		try {
			Query sumLodgingExpenseQuery = entityManager.createQuery(
					"select coalesce(sum(le.approvedAmount),0) as totalApprovedAmount from LodgingExpense le left join le.expenseManagement em where le.isDeleted=false and em.expenseManagementId=?1");
			sumLodgingExpenseQuery.setParameter(1, expenseManagement.getExpenseManagementId());
			totalApprovedLodgingAmount = (double) sumLodgingExpenseQuery.getSingleResult();
		} catch (NoResultException ex) {

		}

		double totalLodgingCompanyPaid = 0;
		try {
			Query sumLodgingCompanyPaidQuery = entityManager.createQuery(
					"select coalesce(sum(le.approvedAmount),0) as totalApprovedAmount from LodgingExpense le left join le.expenseManagement em where le.isDeleted=false and le.isComapnyPaid=true and em.expenseManagementId=?1");
			sumLodgingCompanyPaidQuery.setParameter(1, expenseManagement.getExpenseManagementId());
			totalLodgingCompanyPaid = (double) sumLodgingCompanyPaidQuery.getSingleResult();
		} catch (NoResultException ex) {

		}

		// Local Conveyance
		double totalLocalConveyanceAmount = 0;
		try {
			Query sumLocalConveyanceQuery = entityManager.createQuery(
					"select coalesce(sum(lce.amount),0) as totalAmount from LocalConveyanceExpense lce left join lce.expenseManagement em where lce.isDeleted=false and em.expenseManagementId=?1");
			sumLocalConveyanceQuery.setParameter(1, expenseManagement.getExpenseManagementId());
			totalLocalConveyanceAmount = (double) sumLocalConveyanceQuery.getSingleResult();
		} catch (NoResultException ex) {

		}
		
		// Boarding Expense
		double totalBoardingAmount = 0;
		try {
			Query sumBoardingExpenseQuery = entityManager.createQuery(
					"select coalesce(sum(be.amount),0) as totalAmount from BoardingExpense be left join be.expenseManagement em where be.isDeleted=false and em.expenseManagementId=?1");
			sumBoardingExpenseQuery.setParameter(1, expenseManagement.getExpenseManagementId());
			totalBoardingAmount = (double) sumBoardingExpenseQuery.getSingleResult();
		} catch (NoResultException ex) {

		}


		// Other Charges
		double totalOtherChargesAmount = 0;
		try {
			Query sumOtherChargesQuery = entityManager.createQuery(
					"select coalesce(sum(och.amount),0) as totalAmount from OtherChargesExpense och left join och.expenseManagement em where och.isDeleted=false and em.expenseManagementId=?1");
			sumOtherChargesQuery.setParameter(1, expenseManagement.getExpenseManagementId());
			totalOtherChargesAmount = (double) sumOtherChargesQuery.getSingleResult();
		} catch (NoResultException ex) {

		}	
		
		double totalExpense = totalApprovedTourAmount+totalApprovedLodgingAmount+totalLocalConveyanceAmount+totalBoardingAmount+totalOtherChargesAmount;
		
		double totalCompanyPaid = totalTourAmountCompanyPaid+totalLodgingCompanyPaid;

		double netAmountToPay = totalExpense-totalCompanyPaid-expenseManagement.getAdvanceAmount();
		
		expenseManagement.setNetAmountToPay(netAmountToPay);
		
		entityManager.persist(expenseManagement);
		
	}
}
