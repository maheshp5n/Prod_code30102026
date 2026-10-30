package com.pratikshat.exicom.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.ExpenseManagementBusinessPlace;
import com.pratikshat.exicom.entity.ExpenseManagementExpensePurpose;
import com.pratikshat.exicom.model.response.BusinessPlaceResponse;

@Repository
public interface ExpenseManagementBusinessPlaceRepository extends JpaRepository<ExpenseManagementBusinessPlace, Integer> {

	List<ExpenseManagementBusinessPlace> findAll();
	
	@Query("select new com.pratikshat.exicom.model.response.BusinessPlaceResponse(ebp.BusinessPlaceId,ebp.BusinessPlaceName) from ExpenseManagementBusinessPlace ebp join ebp.division dv where dv.businessDomainId =:divisionId")
	List<BusinessPlaceResponse> getByBusinessDomain(@Param("divisionId")int division);
	
	@Query(value ="SELECT * from expense_management_business_place where business_place_id=:id",nativeQuery = true)
	ExpenseManagementBusinessPlace getByBusinessPlaceId(@Param("id")int id);
	
	@Query("select ebp from ExpenseManagementBusinessPlace ebp where ebp.BusinessPlaceId=:id")
	ExpenseManagementBusinessPlace findByBusinessPlaceId(@Param("id")int id);
	
	@Query(value ="SELECT ebp.`business_place_id` as `businessPlaceId`,ebp.`business_place_name` as `businessPlaceName` from expense_management_business_place ebp join business_domain dv on ebp.division_business_domain_id=dv.business_domain_id where dv.business_domain_id =:businessDomainId",nativeQuery = true)
	List<Map<String, Object>> getByBusinessDomainId(@Param("businessDomainId") int businessDomainId);
}
