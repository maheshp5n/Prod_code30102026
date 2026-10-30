package com.pratikshat.exicom.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.BusinessDomain;
import com.pratikshat.exicom.entity.ExpenseManagementBusinessArea;
import com.pratikshat.exicom.entity.ExpenseManagementExpensePurpose;
import com.pratikshat.exicom.model.response.ExpensePurposeResponse;

@Repository
public interface ExpenseManagementExpensePurposeRepository extends JpaRepository<ExpenseManagementExpensePurpose, Integer> {

	@Query(value ="SELECT * from expense_management_expense_purpose where expense_purpose_id=:id",nativeQuery = true)
	ExpenseManagementExpensePurpose getByExpensePurposeId(@Param("id")int id);
	
	List<ExpenseManagementExpensePurpose> findAll();
	
	@Query("select new com.pratikshat.exicom.model.response.ExpensePurposeResponse(eep.expensePurposeId,eep.expensePurposeName) from ExpenseManagementExpensePurpose eep join eep.division dv where dv.businessDomainId =:divisionId")
	List<ExpensePurposeResponse> getByBusinessDomain(@Param("divisionId")int division);
	
	@Query(value ="select  eep.`expense_purpose_id` as`expensePurposeId`,eep.`expense_purpose_name` as `expensePurposeName` from expense_management_expense_purpose eep join business_domain dv on eep.division_business_domain_id=dv.business_domain_id where dv.business_domain_id =:businessDomainId",nativeQuery = true)
	List<Map<String,Object>> getByBusinessDomainId(@Param("businessDomainId") int businessDomainId);
}
