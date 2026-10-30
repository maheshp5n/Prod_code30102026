package com.pratikshat.exicom.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.ExpenseManagementBusinessArea;
import com.pratikshat.exicom.entity.ExpenseManagementCostCenter;
import com.pratikshat.exicom.entity.ExpenseManagementExpenseType;
import com.pratikshat.exicom.model.response.ExpenseTypeResponse;

@Repository
public interface ExpenseManagementExpenseTypeRepository extends JpaRepository<ExpenseManagementExpenseType, Integer> {

	List<ExpenseManagementExpenseType> findAll();
	
	@Query(value ="SELECT * from expense_management_expense_type where expense_type_id=:id",nativeQuery = true)
	ExpenseManagementExpenseType getByExpenseTypeId(@Param("id")int id);
	
	@Query("select new com.pratikshat.exicom.model.response.ExpenseTypeResponse(eet.expenseTypeId,eet.expenseTypeName) from ExpenseManagementExpenseType eet join eet.division dv where dv.businessDomainId =:divisionId")
	List<ExpenseTypeResponse> getByBusinessDomain(@Param("divisionId")int division);
	
	@Query(value ="SELECT eet.`expense_type_id` as `expenseTypeId`,eet.`expense_type_name` as `expenseTypeName` from expense_management_expense_type eet join business_domain dv on eet.division_business_domain_id=dv.business_domain_id where dv.business_domain_id =:businessDomainId",nativeQuery = true)
	List<Map<String, Object>> getByBusinessDomainId(@Param("businessDomainId") int businessDomainId);
}
