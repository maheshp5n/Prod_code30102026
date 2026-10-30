package com.pratikshat.exicom.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.ChangeCaseTypeConditions;

@Repository
public interface ChangeCaseTypeConditionsRepository extends JpaRepository<ChangeCaseTypeConditions, Long> {

	@Query(value="select distinct `new_case_type` from `change_case_type_conditions` where `existing_case_type`=:existing_case_type and `existing_case_reason`=:existing_case_reason",nativeQuery=true)
	List<Map<String,Object>> getNewCaseType(@Param("existing_case_type")String existing_case_type, @Param("existing_case_reason")String existing_case_reason);
	
	@Query("select con from ChangeCaseTypeConditions con where con.existingCaseType=?1 and con.existingCaseReason=?2 and con.newCaseType=?3 and con.newCaseReason=?4")
	ChangeCaseTypeConditions getChangeCaseTypeConditions(String existingCaseType,String existingCaseReason,String newCaseType,String newCaseReason);
	
	@Query(value="select `new_case_reason` from `change_case_type_conditions` where `existing_case_type`=:existing_case_type and `existing_case_reason`=:existing_case_reason and `new_case_type`=:new_case_type",nativeQuery=true)
	List<Map<String,Object>> getNewCaseReasonByNewCaseType(@Param("existing_case_type")String existing_case_type, @Param("existing_case_reason")String existing_case_reason, @Param("new_case_type")String new_case_type);
}
