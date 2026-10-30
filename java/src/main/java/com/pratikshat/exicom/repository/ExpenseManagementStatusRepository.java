package com.pratikshat.exicom.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.ExpenseManagementBusinessArea;
import com.pratikshat.exicom.entity.ExpenseManagementStatus;
import com.pratikshat.exicom.model.response.ExpenseStatusResponse;

@Repository
public interface ExpenseManagementStatusRepository extends JpaRepository<ExpenseManagementStatus,Integer> {

	List<ExpenseManagementStatus> findAll();
	
	@Query("select new com.pratikshat.exicom.model.response.ExpenseStatusResponse(ems.statusId,ems.statusName) from ExpenseManagementStatus ems join ems.division dv where dv.businessDomainId =:divisionId")
	List<ExpenseStatusResponse> getByBusinessDomain(@Param("divisionId")int division);
	
	@Query(value="SELECT \r\n" + 
			"`status_name` AS `name` \r\n" + 
			"FROM `expense_management_status`\r\n" + 
			"WHERE `division_business_domain_id`=2\r\n" + 
			"ORDER BY `status_name` ASC",nativeQuery=true)
	java.util.List<String> getExpenseStatusList();
	
	@Query(value="SELECT  ems.`status_id` as `statusId`,ems.`status_name` as `statusName` from expense_management_status ems join business_domain dv on ems.division_business_domain_id=dv.business_domain_id where dv.business_domain_id =:businessDomainId",nativeQuery=true)
	List<Map<String,Object>> getByBusinessDomainId(@Param("businessDomainId") int businessDomainId);

}
