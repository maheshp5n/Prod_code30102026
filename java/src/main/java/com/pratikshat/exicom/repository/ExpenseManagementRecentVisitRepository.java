package com.pratikshat.exicom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.ExpenseManagementRecentVisit;

@Repository
public interface ExpenseManagementRecentVisitRepository extends JpaRepository<ExpenseManagementRecentVisit, Long> {
	ExpenseManagementRecentVisit findByExpenseManagementIdAndVisitorId(long expenseManagementId,String visitorId);
}
