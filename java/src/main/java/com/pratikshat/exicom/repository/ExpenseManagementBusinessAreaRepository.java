package com.pratikshat.exicom.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.ExpenseManagementBusinessArea;
import com.pratikshat.exicom.entity.ExpenseManagementExpensePurpose;
import com.pratikshat.exicom.model.response.BusinessAreaResponse;

@Repository
public interface ExpenseManagementBusinessAreaRepository extends JpaRepository<ExpenseManagementBusinessArea, Integer> {

	List<ExpenseManagementBusinessArea> findAll();
	
	@Query("select new com.pratikshat.exicom.model.response.BusinessAreaResponse(eba.BusinessAreaId,eba.BusinessAreaName) from ExpenseManagementBusinessArea eba join eba.division dv where dv.businessDomainId =:divisionId")
	List<BusinessAreaResponse> getByBusinessDomain(@Param("divisionId")int division);
	
	@Query(value ="SELECT * from expense_management_business_area where business_area_id=:id",nativeQuery = true)
	ExpenseManagementBusinessArea getByBusinessAreaId(@Param("id")int id);
	
	@Query("select eba from ExpenseManagementBusinessArea eba where eba.BusinessAreaId=:id")
	ExpenseManagementBusinessArea findByBusinessAreaId(@Param("id")int id);
	
	@Query(value ="SELECT eba.`business_area_id` as `businessAreaId`,eba.`business_area_name` as `businessAreaName` from expense_management_business_area eba join business_domain dv on eba.division_business_domain_id=dv.business_domain_id where dv.business_domain_id =:businessDomainId ",nativeQuery = true)
	List<Map<String, Object>> getByBusinessDomainId(@Param("businessDomainId") int businessDomainId);
}
