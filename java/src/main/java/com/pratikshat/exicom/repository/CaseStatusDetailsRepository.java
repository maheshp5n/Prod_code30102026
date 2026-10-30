package com.pratikshat.exicom.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.CaseStatusDetails;
import com.pratikshat.exicom.model.response.CaseStatusResponse;
@Repository
public interface CaseStatusDetailsRepository extends JpaRepository<CaseStatusDetails, String> {

	@Query("select new com.pratikshat.exicom.model.response.CaseStatusResponse(cs.id,cs.caseStatusName) from CaseStatusDetails cs")
	List<CaseStatusResponse> getCaseStatusList();
	
	CaseStatusDetails findById(long id);
	
	@Query("select cs from CaseStatusDetails cs where cs.caseStatusName=:statusName")
	CaseStatusDetails getCaseStatusByCaseStatusName(@Param("statusName")String statusName);
	
	@Query("select new com.pratikshat.exicom.model.response.CaseStatusResponse(cs.id,cs.caseStatusName) from CaseStatusDetails cs where cs.id in :statuslist")
	List<CaseStatusResponse> getCaseStatusList(@Param("statuslist")List<Integer> statuslist);
	
	@Query(value="SELECT\r\n" + 
			"`case_status_name` AS `name`\r\n" + 
			"FROM `case_status_details`\r\n" + 
			"ORDER BY `case_status_name` ASC",nativeQuery=true)
	java.util.List<String> getCaseStatus();
	
	@Query(value="SELECT\r\n" + 
			"`case_status_name` AS `name`\r\n" + 
			"FROM `case_status_details`\r\n" + 
			"ORDER BY `case_status_name` ASC",nativeQuery=true)
	java.util.List<String> getBtCaseStatus();
}
