package com.pratikshat.exicom.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.ExpenseManagementBusinessPlace;
import com.pratikshat.exicom.entity.ExpenseManagementCostCenter;
import com.pratikshat.exicom.model.response.CostCenterResponse;

@Repository
public interface ExpenseManagementCostCenterRepository extends JpaRepository<ExpenseManagementCostCenter, Integer> {

	List<ExpenseManagementCostCenter> findAll();
	
	@Query(value ="SELECT * from expense_management_cost_center where cost_center_id=:id",nativeQuery = true)
	ExpenseManagementCostCenter getByCostCenterId(@Param("id")int id);
	
	@Query("select new com.pratikshat.exicom.model.response.CostCenterResponse(ecc.costCenterId,ecc.costCenterName) from ExpenseManagementCostCenter ecc join ecc.division dv where dv.businessDomainId =:divisionId")
	List<CostCenterResponse> getByBusinessDomain(@Param("divisionId")int division);
	
	@Query(value ="SELECT ecc.`cost_center_id` as `cost_center_id`,ecc.`cost_center_name` as `costCenterName` from expense_management_cost_center ecc join business_domain dv on ecc.division_business_domain_id=dv.business_domain_id where dv.business_domain_id =:businessDomainId",nativeQuery = true)
	List<Map<String, Object>> getByBusinessDomainId(@Param("businessDomainId") int businessDomainId);
}
