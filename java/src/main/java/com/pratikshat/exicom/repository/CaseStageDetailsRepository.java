package com.pratikshat.exicom.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.CaseReasonDetails;
import com.pratikshat.exicom.entity.CaseStageDetails;
import com.pratikshat.exicom.model.response.CaseStageResponse;
@Repository
public interface CaseStageDetailsRepository extends JpaRepository<com.pratikshat.exicom.entity.CaseStageDetails,String> {

	//List<CaseStageDetails> findByCaseStatusIdAndDivision(long statusId,String division);
	
	@Query("select new com.pratikshat.exicom.model.response.CaseStageResponse(cs.id,cs.caseStageName) from CaseStageDetails cs where cs.caseStatus.id=:caseStatusId And cs.division.businessDomainId=:division and cs.id in :stagelist")
	List<CaseStageResponse> getCaseStageByCaseStatusIdAndDivision(@Param("caseStatusId")int caseStatusId,@Param("division")int division, @Param("stagelist")List<Integer> stagelist);
	
	
	@Query("select cs from CaseStageDetails cs where cs.caseStatus.id=:caseStatusId And cs.caseStageName=:stageName And cs.division.businessDomainId=:division")
	CaseStageDetails getCaseStageByCaseStatusIdAndStageName(@Param("caseStatusId")int caseStatusId,@Param("stageName")String stageName, @Param("division")int division);

	@Query(value="SELECT\r\n" + 
			"`case_stage_name` AS `name`\r\n" + 
			"FROM `case_stage_details`\r\n" + 
			"WHERE `division_business_domain_id`=2\r\n" + 
			"ORDER BY `case_stage_name` ASC",nativeQuery=true)
	java.util.List<String> getCaseStageList();
	
	
	@Query("select cst from CaseStageDetails cst left join cst.caseStatus cs left join cst.division dv where cs.id=:caseStatusId and dv.businessDomainId=:division")
	List<CaseStageDetails>  getCaseStageByStatusIdAndDivision(int caseStatusId,int division);
	
	@Query("select csd from CaseStageDetails csd where csd.id=:caseStatusId")
	CaseStageDetails  getCaseStageWithId(int caseStatusId);
	
	@Query("select stg from CaseStageDetails stg join stg.caseStatus sts join stg.division vrt where sts.id=:caseStatusId and vrt.businessDomainId=:caseRecordTypeId")
	List<CaseStageDetails> getStageWithStatusAndDomainId(int caseStatusId, int caseRecordTypeId);
	
	@Query("select stg.id from CaseStageDetails stg join stg.caseStatus sts join stg.division vrt where sts.id=:caseStatusId and vrt.businessDomainId=:caseRecordTypeId")
	List<Integer> getStageIdWithStatusAndDomainId(int caseStatusId, int caseRecordTypeId);
	
	@Query(value="SELECT\r\n" + 
			"`case_stage_name` AS `name`\r\n" + 
			"FROM `case_stage_details`\r\n" + 
			"WHERE `division_business_domain_id`=1\r\n" + 
			"ORDER BY `case_stage_name` ASC",nativeQuery=true)
	java.util.List<String> getBtCaseStageList();
	
	@Query(value = "SELECT csd.`id` AS id,csd.`case_stage_name` AS caseStageName\r\n"
			+ "\r\n"
			+ "FROM `case_stage_details` csd LEFT JOIN `case_status_details` st ON st.`id`=csd.`case_status_id`\r\n"
			+ "\r\n"
			+ "WHERE st.`id`=:statusId AND `division_business_domain_id` ='1'",nativeQuery = true)
	List<Map<String, Object>> getStageByStatusID(int statusId);

}
