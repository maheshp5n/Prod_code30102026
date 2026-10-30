package com.pratikshat.exicom.repository;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.pratikshat.exicom.entity.BtExpenseManagement;
import com.pratikshat.exicom.entity.BtServiceAppointment;
import com.pratikshat.exicom.entity.BtServiceAppointmentExp;
import com.pratikshat.exicom.entity.BtServiceAppointmentExpense;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.BtServiceAppointmentExpRequest;

@Repository
public class BtServiceAppointmentExpenseRepositoryCustomImpl implements BtServiceAppointmentExpenseRepositoryCustom {

	@PersistenceContext
	EntityManager entityManager;
	
	@Autowired
	com.pratikshat.exicom.interceptor.WorkflowExecuter workflow;
	
	@Override
	public BtServiceAppointmentExpRequest saveDetails(BtServiceAppointmentExpRequest request, LoginUser user)
			throws BadRequestException, UnauthoriseException {
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
			
			if(!expenseManagement.getOwner().getEmailId().equalsIgnoreCase(user.getUsername())) {
				throw new com.pratikshat.exicom.exception.BadRequestException("You are not authorised");
			}
			
			
			try {
				Query selectExpenseQuery = entityManager.createQuery(
						"select bsa from BtServiceAppointment bsa left join bsa.ownerDetails onr where bsa.serviceAppointmentId=?1", BtServiceAppointment.class);

				selectExpenseQuery.setParameter(1, request.getBtExpId());
				btServiceAppointment = (BtServiceAppointment) selectExpenseQuery.getSingleResult();
			} catch (NoResultException ex) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Expense does not exist");
			}
				
				BtServiceAppointmentExpense btServiceAppointmentExpense=new BtServiceAppointmentExpense(); 
				btServiceAppointmentExpense.setBtExpenseManagement(expenseManagement);
				
				btServiceAppointmentExpense.setCostCenter(request.getCostCenter());
				btServiceAppointmentExpense.setTotalClaimAmount(request.getTotalClaimAmount());
				btServiceAppointmentExpense.setCreatedBy(new EmployeeDetails(user.getUsername()));
				btServiceAppointmentExpense.setCreatedDate(new Date());
				btServiceAppointmentExpense.setRemark(request.getRemark());
				
				
				workflow.executeWorkflow(entityManager, btServiceAppointmentExpense, "INSERT", "PRE", user);
				entityManager.persist(btServiceAppointmentExpense);
				
				SimpleDateFormat dateformatter = new SimpleDateFormat("yyyyMMdd");
				String manualId = null;
				manualId = "SA-" + dateformatter.format(new java.util.Date()) + "-"
				+ com.pratikshat.exicom.config.MiscellaneousUtils.fillZero(String.valueOf(btServiceAppointmentExpense.getId()),5);
				btServiceAppointmentExpense.setManualId(manualId);
				entityManager.persist(btServiceAppointmentExpense);
				
				workflow.executeWorkflow(entityManager, btServiceAppointmentExpense, "INSERT", "POST", user);
				
			
		}
		return null;
	}

	@Override
	public BtServiceAppointmentExpRequest updateDetails(BtServiceAppointmentExpRequest request, LoginUser user)
			throws BadRequestException, UnauthoriseException {
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
			
			if(!expenseManagement.getOwner().getEmailId().equalsIgnoreCase(user.getUsername())) {
				throw new com.pratikshat.exicom.exception.BadRequestException("You are not authorised");
			}
			try {
			Query selectExpenseQuery = entityManager.createQuery(
					"select bsa from BtServiceAppointment bsa left join bsa.ownerDetails onr where bsa.serviceAppointmentId=?1", BtServiceAppointment.class);

			selectExpenseQuery.setParameter(1, request.getBtExpId());
			btServiceAppointment = (BtServiceAppointment) selectExpenseQuery.getSingleResult();
		} catch (NoResultException ex) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Expense does not exist");
		}
			
			BtServiceAppointmentExpense btServiceAppointmentExpense=new BtServiceAppointmentExpense(); 
			btServiceAppointmentExpense.setBtExpenseManagement(expenseManagement);
			
			btServiceAppointmentExpense.setCostCenter(request.getCostCenter());
			btServiceAppointmentExpense.setTotalClaimAmount(request.getTotalClaimAmount());
			btServiceAppointmentExpense.setCreatedBy(new EmployeeDetails(user.getUsername()));
			btServiceAppointmentExpense.setCreatedDate(new Date());
			btServiceAppointmentExpense.setRemark(request.getRemark());
			
			
			workflow.executeWorkflow(entityManager, btServiceAppointmentExpense, "UPDATE", "PRE", user);
			entityManager.persist(btServiceAppointmentExpense);
			workflow.executeWorkflow(entityManager, btServiceAppointmentExpense, "UPDATE", "POST", user);

//			BtExpenseManagement expense = expenseTotal(expenseManagement, entityManager);
//			entityManager.persist(expense);
			
		}
		return null;
	}

	@Override
	public BtServiceAppointmentExpRequest deleteDetails(BtServiceAppointmentExpRequest t, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		// TODO Auto-generated method stub
		return null;
	}

}
