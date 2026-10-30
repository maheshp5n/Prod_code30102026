package com.pratikshat.exicom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.CaseTypes;
@Repository
public interface CaseTypesRepository extends JpaRepository<com.pratikshat.exicom.entity.CaseTypes, Long> {
	@Query("select new com.pratikshat.exicom.model.response.CaseTypesForList(ct.caseTypeId,ct.caseTypesName) from CaseTypes ct where ct.vertical.businessDomainId=:vertical")
	java.util.List<com.pratikshat.exicom.model.response.CaseTypesForList> getByVertical(@Param("vertical")int vertical);
	
	com.pratikshat.exicom.entity.CaseTypes findByCaseTypeId(long id);
	
	@Query("select ct from CaseTypes ct where ct.caseTypesName=:caseTypeName and ct.vertical.businessDomainId=:vertical")
	CaseTypes getByCaseTypesNameAndVertical(@Param("caseTypeName") String caseTypeName, @Param("vertical")int vertical);
	
	
	@Query(value="SELECT \r\n" + 
			"`case_types_name` AS `name`\r\n" + 
			" FROM `case_types` \r\n" + 
			" WHERE `vertical_business_domain_id`=2\r\n" + 
			" ORDER BY `case_types_name` ASC",nativeQuery=true)
	java.util.List<String> getCaseTypeList();
	
	@Query("select c from CaseTypes c where c.wccTypeId=:id")
	List<CaseTypes> getCaseUsingWccTypeId(int id);
	
	
	@Query(value="SELECT `case_types_name` AS `name`\r\n"
			+ "FROM `case_types` WHERE`vertical_business_domain_id`=1" + 
			" ORDER BY `case_types_name` ASC",nativeQuery=true)
	java.util.List<String> getBtCaseTypeList();
	
	
	
}
