package com.pratikshat.exicom.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.PreRegistrationCases;

@Repository
public interface PreRegistrationCasesRepository extends JpaRepository<PreRegistrationCases, Long>, PreRegistrationCasesCustomeRepository {

	public static final String IGNORE="Ignore";
	public static final String GENERATE_CASE="Generate Case";
	
	int countById(long caseId);
	int countByManualId(String manualId);
	int countByIdAndStatus_CaseStatusName(long caseId,String casestatus);
	int countByManualIdAndStatus_CaseStatusName(String caseManualId,String caseStatus);
	
	com.pratikshat.exicom.entity.PreRegistrationCases findByManualId(String manualId);
	@Query("select new com.pratikshat.exicom.model.response.PrcResponse(ca,cd) from PreRegistrationCases ca left join ca.assets ast left join ca.site st left join st.account acn left join ca.ownerDetails onr left join CaseDetails cd on ca=cd.prc")
	Page<com.pratikshat.exicom.model.response.PrcResponse> getAllCase(Pageable page);
	
	@Query("select new com.pratikshat.exicom.model.response.PrcResponse(ca,cd) from PreRegistrationCases ca left join ca.assets ast left join ca.site st left join st.account acn left join ca.ownerDetails onr left join CaseDetails cd on ca=cd.prc where ca.lastAction is null")
	Page<com.pratikshat.exicom.model.response.PrcResponse> getAllPendingCase(Pageable page);
	
	@Query("select new com.pratikshat.exicom.model.response.PrcResponse(ca,cd) from PreRegistrationCases ca left join ca.assets ast left join ca.site st left join st.account acn left join ca.ownerDetails onr left join CaseDetails cd on ca=cd.prc where onr in :users or ca.engineer.user in :users")
	Page<com.pratikshat.exicom.model.response.PrcResponse> getAllCase(@Param("users")java.util.List<com.pratikshat.exicom.entity.EmployeeDetails> emps,Pageable page);
	
	@Query("select new com.pratikshat.exicom.model.response.PrcResponse(ca,cd) from PreRegistrationCases ca left join ca.assets ast left join ca.site st left join st.account acn left join ca.ownerDetails onr left join CaseDetails cd on ca=cd.prc where (onr in :users  or ca.engineer.user in :users) and ca.lastAction is null")
	Page<com.pratikshat.exicom.model.response.PrcResponse> getAllPendingCase(@Param("users")java.util.List<com.pratikshat.exicom.entity.EmployeeDetails> emps,Pageable page);

	@Query("select new com.pratikshat.exicom.model.response.PrcResponse(ca,cd) from PreRegistrationCases ca left join ca.assets ast left join ca.site st left join st.account acn left join st.primaryAccount pacn left join ca.ownerDetails onr left join ca.createdBy createdby left join ca.modifiedBy modified left join ca.engineer eng left join eng.contact econ left join ca.serviceCenter scr left join CaseDetails cd on ca=cd.prc where ca.id=:caseId")
	com.pratikshat.exicom.model.response.PrcResponse getByCaseId(@Param("caseId")long caseId);
	
	@Query("select count(ca) from PreRegistrationCases ca left join ca.assets ast where ast.serialNumber=:assetsSerialNumber and ca.status.caseStatusName !=:caseStatusName")
	int getCountByAssetsAndNotCaseStatus(@Param("assetsSerialNumber")String assetsSerialNumber,@Param("caseStatusName")String caseStatusName);
	
	@Query("select count(ca) from PreRegistrationCases ca left join ca.assets ast where ast.serialNumber=:assetsSerialNumber and ca.lastAction is null")
	int getCountByAssetsAndNoLastAction(@Param("assetsSerialNumber")String assetsSerialNumber);
	
	@Query("select ca from PreRegistrationCases ca left join ca.assets ast left join ca.site st left join st.account acn left join st.primaryAccount pacn left join ca.ownerDetails onr left join ca.createdBy createdby left join ca.modifiedBy modified left join ca.engineer eng left join eng.contact econ left join ca.serviceCenter scr left join ca.businessDomain bd where ca.id=:caseId")
	PreRegistrationCases findByCaseId(@Param("caseId")long caseId);
	
	
	@Query("select new com.pratikshat.exicom.model.response.PrcResponse(ca,cd) from PreRegistrationCases ca left join ca.assets ast left join ca.site st left join st.account acn left join ca.ownerDetails onr left join CaseDetails cd on ca=cd.prc where ca.lastAction=:action")
	Page<com.pratikshat.exicom.model.response.PrcResponse> getAllCaseWithAction(@Param("action")String action, Pageable page);
	
	@Query("select new com.pratikshat.exicom.model.response.PrcResponse(ca,cd) from PreRegistrationCases ca left join ca.assets ast left join ca.site st left join st.account acn left join ca.ownerDetails onr left join CaseDetails cd on ca=cd.prc where onr in :users and ca.lastAction=:action")
	Page<com.pratikshat.exicom.model.response.PrcResponse> getAllCaseWithAction(@Param("users")java.util.List<com.pratikshat.exicom.entity.EmployeeDetails> emps, @Param("action")String action, Pageable page);
	
	@Query("select new com.pratikshat.exicom.model.response.PrcResponse(ca,cd) from PreRegistrationCases ca left join ca.assets ast left join ca.site st left join st.account acn left join st.primaryAccount pacn left join ca.ownerDetails onr left join ca.createdBy createdby left join ca.modifiedBy modified left join ca.engineer eng left join eng.contact econ left join ca.serviceCenter scr left join ca.businessDomain bd left join CaseDetails cd on ca=cd.prc where ast.serialNumber=:assetsSerialNumber")
	Page<com.pratikshat.exicom.model.response.PrcResponse> getAllCasesByAssetSerialNumber(@Param("assetsSerialNumber")String assetsSerialNumber, Pageable page);
	
	@Query("select new com.pratikshat.exicom.model.response.PrcResponse(ca,cd) from PreRegistrationCases ca left join ca.assets ast left join ca.site st left join st.account acn left join st.primaryAccount pacn left join ca.ownerDetails onr left join ca.createdBy createdby left join ca.modifiedBy modified left join ca.engineer eng left join eng.contact econ left join ca.serviceCenter scr left join ca.businessDomain bd left join CaseDetails cd on ca=cd.prc where st.siteId=:siteId")
	Page<com.pratikshat.exicom.model.response.PrcResponse> getAllCasesBySiteId(@Param("siteId")long siteId, Pageable page);
	
	@Query("select new com.pratikshat.exicom.model.response.PrcResponse(ca,cd) from PreRegistrationCases ca left join ca.assets ast left join ca.site st left join st.account acn left join st.primaryAccount pacn left join ca.ownerDetails onr left join ca.createdBy createdby left join ca.modifiedBy modified left join ca.engineer eng left join eng.contact econ left join ca.serviceCenter scr left join ca.businessDomain bd left join CaseDetails cd on ca=cd.prc where ca.id=:caseId")
	Page<com.pratikshat.exicom.model.response.PrcResponse> getAllPrcByCaseId(@Param("caseId")long caseId, Pageable page);
	
	
	
	@Query(value="SELECT \r\n" + 
			"IFNULL(COUNT(1),0) AS `total count`,\r\n" + 
			"IFNULL(t.`open count`,0) AS `open count`\r\n" + 
			"FROM `pre_registration_cases` prc\r\n" + 
			"JOIN `service_recource` sr ON sr.`service_resource_id`=prc.`engineer_service_resource_id`\r\n" + 
			"JOIN `site_details` st ON st.`site_id`=prc.`site_site_id`\r\n" + 
			"JOIN `circle_details` cr ON cr.`circle_name`=st.`circle_circle_name`\r\n" + 
			"JOIN `contact_details` cnt ON cnt.`contact_id`=cr.`circle_manager_power_id`\r\n"+
			"CROSS JOIN (\r\n" + 
			"	SELECT \r\n" + 
			"	COUNT(1) AS `open count` \r\n" + 
			"	FROM `pre_registration_cases` prc\r\n" + 
			"	JOIN `service_recource` sr ON sr.`service_resource_id`=prc.`engineer_service_resource_id`\r\n" +
			"	JOIN `site_details` st ON st.`site_id`=prc.`site_site_id`\r\n" + 
			"	JOIN `circle_details` cr ON cr.`circle_name`=st.`circle_circle_name`\r\n" + 
			"	JOIN `contact_details` cnt ON cnt.`contact_id`=cr.`circle_manager_power_id`\r\n"+
			"	WHERE prc.`last_action`=NULL\r\n" + 
			"	AND (sr.`user_email_id` IN (:userList)\r\n" +
			"		OR cnt.`email_id` IN (:userList))\r\n" + 
			"	AND YEAR(prc.`create_date`)=YEAR(NOW()) \r\n" + 
			"	AND MONTH(prc.`create_date`)=MONTH(NOW())) t\r\n" + 
			"WHERE (sr.`user_email_id` IN (:userList)\r\n" +
			" 	OR cnt.`email_id` IN (:userList))\r\n" + 
			"AND YEAR(prc.`create_date`)=YEAR(NOW()) \r\n" + 
			"AND MONTH(prc.`create_date`)=MONTH(NOW())\r\n" + 
			"GROUP BY t.`open count`", nativeQuery=true)
	Map<String,Object> getPRC_open_vs_total_mtd(@Param("userList")List<String> userList);
	
	@Query(value="SELECT \r\n" + 
			"IFNULL(COUNT(1),0) AS `total count`,\r\n" + 
			"IFNULL(t.`open count`,0) AS `open count`\r\n" + 
			"FROM `pre_registration_cases` prc\r\n" + 
			"JOIN `service_recource` sr ON sr.`service_resource_id`=prc.`engineer_service_resource_id`\r\n" + 
			"JOIN `site_details` st ON st.`site_id`=prc.`site_site_id`\r\n" + 
			"JOIN `circle_details` cr ON cr.`circle_name`=st.`circle_circle_name`\r\n" + 
			"JOIN `contact_details` cnt ON cnt.`contact_id`=cr.`circle_manager_power_id`\r\n"+
			"CROSS JOIN (\r\n" + 
			"	SELECT \r\n" + 
			"	COUNT(1) AS `open count` \r\n" + 
			"	FROM `pre_registration_cases` prc\r\n" + 
			"	JOIN `service_recource` sr ON sr.`service_resource_id`=prc.`engineer_service_resource_id`\r\n" + 
			"	JOIN `site_details` st ON st.`site_id`=prc.`site_site_id`\r\n" + 
			"	JOIN `circle_details` cr ON cr.`circle_name`=st.`circle_circle_name`\r\n" + 
			"	JOIN `contact_details` cnt ON cnt.`contact_id`=cr.`circle_manager_power_id`\r\n"+
			"	WHERE prc.`last_action`=NULL\r\n" + 
			"	AND (sr.`user_email_id` IN (:userList)\r\n" +
			"		OR cnt.`email_id` IN (:userList))) t\r\n" + 
			"WHERE (sr.`user_email_id` IN (:userList)\r\n" +
			" 	OR cnt.`email_id` IN (:userList))\r\n" +
			"AND DATE_FORMAT(prc.`create_date`,'%Y-%m-%d') \r\n" + 
			"	BETWEEN \r\n" + 
			"		CASE\r\n" + 
			"			WHEN MONTH(NOW())<4 THEN DATE_FORMAT(CONCAT(YEAR(NOW()-1),'-04','-01'),'%Y-%m-%d')\r\n" + 
			"			ELSE DATE_FORMAT(CONCAT(YEAR(NOW()),'-04','-01'),'%Y-%m-%d')\r\n" + 
			"		END\r\n" + 
			"	AND \r\n" + 
			"		CASE\r\n" + 
			"			WHEN MONTH(NOW())<4 THEN DATE_FORMAT(CONCAT(YEAR(NOW()),'-03','-31'),'%Y-%m-%d')\r\n" + 
			"			ELSE DATE_FORMAT(CONCAT(YEAR(NOW())+1,'-03','-31'),'%Y-%m-%d')\r\n" + 
			"		END\r\n" + 
			"GROUP BY t.`open count`", nativeQuery=true)
	Map<String,Object> getPRC_open_vs_total_ytd(@Param("userList")List<String> userList);
}
