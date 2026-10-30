package com.pratikshat.exicom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.CaseReasonDetails;
import com.pratikshat.exicom.entity.CaseStageDetails;
import com.pratikshat.exicom.entity.CaseTypes;
@Repository
public interface CaseReasonDetailsRepository extends JpaRepository<com.pratikshat.exicom.entity.CaseReasonDetails,Long> {
	@Query("select new com.pratikshat.exicom.model.response.CaseReasonForList(cr.reasonId,cr.caseReasonDetailsName) from CaseReasonDetails cr left join cr.caseType tp where tp.caseTypeId=:typeid")
	java.util.List<com.pratikshat.exicom.model.response.CaseReasonForList> getByCaseType(@Param("typeid")long typeid);
	
	@Query("select cr from CaseReasonDetails cr left join cr.caseType tp where tp.caseTypeId=:typeId and cr.caseReasonDetailsName=:reasonName And cr.vertical.businessDomainId=:division")
	CaseReasonDetails getByCaseTypeAndCaseReasonName(@Param("typeId")long typeId, @Param("reasonName")String reasonName, @Param("division")int division);

	@Query(value="SELECT \r\n" + 
			"DISTINCT `case_reason_details_name` AS `name`\r\n" + 
			"FROM `case_reason_details`\r\n" + 
			"WHERE `vertical_business_domain_id`=2\r\n" + 
			"ORDER BY `case_reason_details_name` ASC",nativeQuery=true)
	java.util.List<String> getCaseReasonList();
	
	@Query("select cr from CaseReasonDetails cr where cr.reasonId=:id")
	CaseReasonDetails getCaseReasonWithId(long id);
	
	@Query("select crd from CaseReasonDetails crd join crd.caseType cty join crd.vertical vrt where cty.caseTypeId=:caseTypeId and vrt.businessDomainId=:caseRecordTypeId")
	List<CaseReasonDetails> getResonWithCasetypeAndDomainId(long caseTypeId,int caseRecordTypeId);
	
	@Query("select crd.reasonId from CaseReasonDetails crd join crd.caseType cty join crd.vertical vrt where cty.caseTypeId=:caseTypeId and vrt.businessDomainId=:caseRecordTypeId")
	List<Long> getResonIDWithCasetypeAndDomainId(long caseTypeId,int caseRecordTypeId );
	
	@Query("select c from CaseReasonDetails c where c.caseType=:id")
	List<CaseReasonDetails> getCaseReasonDetailsUsingCaseType(CaseTypes id);
	
	@Query(value="SELECT DISTINCT `case_reason_details_name` AS `name`\r\n"
			+ "FROM `case_reason_details`\r\n"
			+ "WHERE `vertical_business_domain_id`=1\r\n"
			+ "ORDER BY `case_reason_details_name` ASC",nativeQuery=true)
	java.util.List<String> getBtCaseReasonList();
	
	
	@Query("select cr from CaseReasonDetails cr join cr.vertical vt join cr.caseType ct where cr.caseReasonDetailsName=:caseReasonDetailsName and vt.businessDomainId=:businessDomainId and ct.caseTypeId=:caseTypeId")
	CaseReasonDetails getCaseresonbyNameAndCase(@Param("caseReasonDetailsName") String caseReasonDetailsName,int businessDomainId ,long  caseTypeId);
	
}
