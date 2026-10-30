package com.pratikshat.exicom.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.CaseDetails;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.model.response.OppertunityResponse;
@Repository
public interface CaseDetailsRepository extends JpaRepository<com.pratikshat.exicom.entity.CaseDetails,Long>,CaseDetailsRepositoryCustome {
	int countByCaseId(long caseId);
	int countByManualId(String manualId);
	int countByCaseIdAndStatus_CaseStatusName(long caseId,String casestatus);
	int countByManualIdAndStatus_CaseStatusName(String caseManualId,String caseStatus);
	@Query("select count(ca) from CaseDetails ca left join ca.assets ast where ast.serialNumber=:assetsSerialNumber and ca.status.caseStatusName !=:caseStatusName")
	int getCountByAssetsAndNotCaseStatus(@Param("assetsSerialNumber")String assetsSerialNumber,@Param("caseStatusName")String caseStatusName);
	
	@Query("select ca from CaseDetails ca left join ca.assets ast left join ca.site st left join st.account acn left join st.primaryAccount pacn left join ca.ownerDetails onr left join ca.createdBy createdby left join ca.modifiedBy modified left join ca.engineer eng left join eng.contact econ left join ca.serviceCenter scr left join ca.businessDomain bd where ca.caseId=:caseId")
	CaseDetails findByCaseId(@Param("caseId")long caseId);
	
	com.pratikshat.exicom.entity.CaseDetails findByManualId(String manualId);
	@Query("select new com.pratikshat.exicom.model.response.CaseResponse(ca) from CaseDetails ca left join ca.assets ast left join ca.site st left join st.account acn left join ca.ownerDetails onr left join ca.engineer eng left join eng.contact engcnt left join engcnt.account engact left join ca.prc prc")
	Page<com.pratikshat.exicom.model.response.CaseResponse> getAllCase(Pageable page);
	
	@Query("select new com.pratikshat.exicom.model.response.CaseResponse(ca) from CaseDetails ca left join ca.assets ast left join ca.site st left join st.account acn left join ca.ownerDetails onr left join ca.engineer eng left join eng.contact engcnt left join engcnt.account engact left join ca.prc prc where onr in :users or ca.engineer.user in :users")
	Page<com.pratikshat.exicom.model.response.CaseResponse> getAllCase(@Param("users")java.util.List<com.pratikshat.exicom.entity.EmployeeDetails> emps,Pageable page);
	
	@Query("select new com.pratikshat.exicom.model.response.CaseResponse(ca) from CaseDetails ca left join ca.assets ast left join ca.site st left join st.account acn left join ca.ownerDetails onr left join ca.engineer eng left join eng.contact engcnt left join engcnt.account engact left join ca.prc prc where ca.status.caseStatusName=:caseStatusName")
	Page<com.pratikshat.exicom.model.response.CaseResponse> getByStatus(@Param("caseStatusName")String status,Pageable page);
	@Query("select new com.pratikshat.exicom.model.response.CaseResponse(ca) from CaseDetails ca left join ca.assets ast left join ca.site st left join st.account acn left join st.primaryAccount pacn left join ca.ownerDetails onr left join ca.createdBy createdby left join ca.modifiedBy modified left join ca.engineer eng left join eng.contact econ left join ca.serviceCenter scr left join econ.account engact left join ca.prc prc where ca.caseId=:caseId")
	com.pratikshat.exicom.model.response.CaseResponse getByCaseId(@Param("caseId")long caseId);
	
	@Query("select new com.pratikshat.exicom.model.response.CaseResponse(ca) from CaseDetails ca left join ca.assets ast left join ca.site st left join st.account acn left join st.primaryAccount pacn left join ca.ownerDetails onr left join ca.createdBy createdby left join ca.modifiedBy modified left join ca.engineer eng left join eng.contact econ left join ca.serviceCenter scr left join econ.account engact left join ca.prc prc where ca.manualId=:manualId")
	com.pratikshat.exicom.model.response.CaseResponse getByCaseManualId(@Param("manualId")String manualId);
	
	@Query("select new com.pratikshat.exicom.model.response.CaseResponse(ca) from CaseDetails ca left join ca.assets ast left join ca.site st left join st.account acn left join ca.ownerDetails onr left join ca.engineer eng left join eng.contact engcnt left join engcnt.account engact left join ca.prc prc where onr.emailId=:emailId")
	Page<com.pratikshat.exicom.model.response.CaseResponse> getByOwner(@Param("emailId")String emailId,Pageable page);
	@Query("select new com.pratikshat.exicom.model.response.CaseResponse(ca) from CaseDetails ca left join ca.assets ast left join ca.site st left join st.account acn left join ca.ownerDetails onr left join ca.engineer eng left join eng.contact engcnt left join engcnt.account engact left join ca.prc prc where ast.serialNumber=:serialNumber")
	Page<com.pratikshat.exicom.model.response.CaseResponse> getByAssetsSerialNumber(@Param("serialNumber")String serialNumber,Pageable page);
	
	@Query("select new com.pratikshat.exicom.model.response.CaseResponse(ca) from CaseDetails ca left join ca.assets ast left join ca.site st left join st.account acn left join ca.ownerDetails onr left join ca.engineer eng left join eng.contact engcnt left join engcnt.account engact left join ca.prc prc where ast.serialNumber=:serialNumber")
	java.util.List<com.pratikshat.exicom.model.response.CaseResponse> getListByAssetsSerialNumber(@Param("serialNumber")String assetsSerialNumber);
	
	@Query("select new com.pratikshat.exicom.model.response.CaseResponse(ca) from CaseDetails ca left join ca.assets ast left join ca.site st left join st.account acn left join ca.ownerDetails onr left join ca.engineer eng left join eng.contact engcnt left join engcnt.account engact left join ca.prc prc where st.siteId=:siteId")
	Page<com.pratikshat.exicom.model.response.CaseResponse> getBySiteId(@Param("siteId")long siteId,Pageable page);
	
	@Query("select new com.pratikshat.exicom.model.response.CaseResponse(ca) from CaseDetails ca left join ca.assets ast left join ca.site st left join st.account acn left join ca.ownerDetails onr left join ca.engineer eng left join eng.contact engcnt left join engcnt.account engact left join ca.prc prc where st.siteId=:siteId")
	java.util.List<com.pratikshat.exicom.model.response.CaseResponse> getListBySiteId(@Param("siteId")long siteId);
	
	@Query("select new com.pratikshat.exicom.model.response.CaseResponse(ca) from CaseDetails ca left join ca.assets ast left join ca.site st left join st.account acn left join ca.ownerDetails onr left join ca.engineer eng left join eng.contact engcnt left join engcnt.account engact left join ca.prc prc where acn.sapCustomerNumber=:accountSapnumber")
	Page<com.pratikshat.exicom.model.response.CaseResponse> getByAccountSapNumber(@Param("accountSapnumber")String accountSapnumber,Pageable page);
	
	@Query("select new com.pratikshat.exicom.model.response.CaseResponse(ca) from CaseDetails ca left join ca.assets ast left join ca.site st left join st.account acn left join ca.ownerDetails onr left join ca.engineer eng left join eng.contact engcnt left join engcnt.account engact left join ca.prc prc where acn.sapCustomerNumber=:accountSapnumber")
	java.util.List<com.pratikshat.exicom.model.response.CaseResponse> getListByAccountSapNumber(@Param("accountSapnumber")String accountSapnumber);

	@Query("select new com.pratikshat.exicom.model.response.CaseResponse(ca) from CaseDetails ca left join ca.assets ast left join ca.site st left join st.account acn left join ca.ownerDetails onr left join ca.engineer eng left join eng.contact engcnt left join engcnt.account engact left join ca.prc prc join CaseRecentVisit rvi on rvi.caseId=ca.caseId where rvi.visitorId=:emailId")
	Page<com.pratikshat.exicom.model.response.CaseResponse> getRecentVisit(@Param("emailId")String emailId,Pageable page);
	@Query("select ct from CaseDetails cs join cs.caseType ct where cs.caseId=:caseId")
	com.pratikshat.exicom.entity.CaseTypes getCaseTypesByCaseId(@Param("caseId")long caseId);
	
	@Query("select new com.pratikshat.exicom.model.response.CaseForList(ca.caseId,ca.manualId) from CaseDetails ca left join ca.status where ca.status.caseStatusName not in :caseStatus")
	java.util.List<com.pratikshat.exicom.model.response.CaseForList> getCaseForList(@Param("caseStatus")List<String> caseStatus);
		
	@Query("select new com.pratikshat.exicom.model.response.CaseResponse(ca) from CaseDetails ca left join ca.assets ast left join ca.site st left join st.account acn left join ca.ownerDetails onr left join ca.engineer eng left join eng.contact engcnt left join engcnt.account engact left join ca.prc prc where ca.status.caseStatusName in :status")
	Page<com.pratikshat.exicom.model.response.CaseResponse> getCasesByStatus(@Param("status")List<String> status,Pageable page);
	
	@Query("select new com.pratikshat.exicom.model.response.CaseResponse(ca) from CaseDetails ca left join ca.assets ast left join ca.site st left join st.account acn left join ca.ownerDetails onr left join ca.engineer eng left join eng.contact engcnt left join engcnt.account engact left join ca.prc prc where ca.status.caseStatusName in :status and (onr in :users or ca.engineer.user in :users)")
	Page<com.pratikshat.exicom.model.response.CaseResponse> getCasesByStatus(@Param("status")List<String> status,@Param("users")List<EmployeeDetails> users,Pageable page);
	
	
	@Query("select new com.pratikshat.exicom.model.response.CaseResponse(ca) from CaseDetails ca left join ca.assets ast left join ca.site st left join st.account acn left join ca.ownerDetails onr left join ca.engineer eng left join eng.contact engcnt left join engcnt.account engact left join ca.prc prc where ca.stage.caseStageName =:stage")
	Page<com.pratikshat.exicom.model.response.CaseResponse> getCasesByStage(@Param("stage")String stage,Pageable page);
	
	@Query("select new com.pratikshat.exicom.model.response.CaseResponse(ca) from CaseDetails ca left join ca.assets ast left join ca.site st left join st.account acn left join ca.ownerDetails onr left join ca.engineer eng left join eng.contact engcnt left join engcnt.account engact left join ca.prc prc where ca.stage.caseStageName =:stage and (onr in :users  or ca.engineer.user in :users)")
	Page<com.pratikshat.exicom.model.response.CaseResponse> getCasesByStage(@Param("stage")String stage,@Param("users")List<EmployeeDetails> user,Pageable page);
	
	
	@Query(value="SELECT year_result.case_types_name AS case_registred,year_result.case_reason_details_name AS case_sub_type,IFNULL(month_result.current_month_registred,0) AS curretn_month,IFNULL(year_result.current_month_registred,0) AS current_year FROM \r\n" + 
			"\r\n" + 
			"(SELECT  case_types_name,case_reason_details_name,IFNULL(COUNT(*),0) current_month_registred FROM `case_details` cs\r\n" + 
			"	JOIN `case_types` ct ON ct.`case_type_id`=  cs.case_type_case_type_id\r\n" + 
			"	JOIN `case_reason_details` cr ON cr.`reason_id`=cs.case_reason_reason_id\r\n" + 
			"	WHERE cs.`business_domain_business_domain_id` IN (1,2,3) AND YEAR(cs.`create_date`) =YEAR(NOW())\r\n" + 
			"	GROUP BY `case_types_name`,`case_reason_details_name`) AS year_result\r\n" + 
			"LEFT JOIN\r\n" + 
			"(SELECT case_types_name,case_reason_details_name,IFNULL(COUNT(*),0) current_month_registred FROM `case_details` cs\r\n" + 
			"	JOIN `case_types` ct ON ct.`case_type_id`=  cs.case_type_case_type_id\r\n" + 
			"	JOIN `case_reason_details` cr ON cr.`reason_id`=cs.case_reason_reason_id\r\n" + 
			"	WHERE cs.`business_domain_business_domain_id` IN (1,2,3) AND MONTH(cs.`create_date`)=MONTH(NOW()) AND YEAR(cs.`create_date`) =YEAR(NOW())\r\n" + 
			"	GROUP BY `case_types_name`,`case_reason_details_name`) AS month_result\r\n" + 
			"ON month_result.case_types_name=year_result.case_types_name AND month_result.case_reason_details_name=year_result.case_reason_details_name ORDER BY case_registred ASC,case_sub_type ASC",nativeQuery=true)
	java.util.List<java.util.Map<String,Object>> getCaseTotal();
	
	@Query(value="SELECT yearly.case_status_name,yearly.case_stage_name,IFNULL(monthly.case_registred_current_month,0) AS monthly,IFNULL(yearly.case_registred_ytd,0) AS yearly FROM \r\n" + 
			"\r\n" + 
			"(SELECT csd.`case_status_name`,cstd.`case_stage_name`,COUNT(*) AS case_registred_ytd FROM `case_details` cs\r\n" + 
			"	JOIN `case_status_details` csd ON cs.`status_id`=csd.`id`\r\n" + 
			"	JOIN `case_stage_details` cstd ON cs.`stage_id`=cstd.`id` WHERE YEAR(cs.`create_date`)=YEAR(NOW())\r\n" + 
			" GROUP BY csd.`case_status_name`,cstd.`case_stage_name`) AS yearly\r\n" + 
			" \r\n" + 
			" LEFT JOIN \r\n" + 
			" \r\n" + 
			" (SELECT csd.`case_status_name`,cstd.`case_stage_name`,COUNT(*) AS case_registred_current_month FROM `case_details` cs\r\n" + 
			"	JOIN `case_status_details` csd ON cs.`status_id`=csd.`id`\r\n" + 
			"	JOIN `case_stage_details` cstd ON cs.`stage_id`=cstd.`id` WHERE YEAR(cs.`create_date`)=YEAR(NOW()) AND MONTH(cs.`create_date`)=MONTH(NOW())\r\n" + 
			" GROUP BY csd.`case_status_name`,cstd.`case_stage_name`) AS monthly\r\n" + 
			" \r\n" + 
			" ON monthly.case_status_name=yearly.case_status_name AND monthly.case_stage_name=yearly.case_stage_name",nativeQuery = true)
	java.util.List<java.util.Map<String,Object>> getQRCActionCustomer();
	@Query(value="SELECT yerpo.problem_name,IFNULL(mrepo.monthly,0) AS monthly,IFNULL(yerpo.yearly,0) AS YTD FROM \r\n" + 
			"(SELECT prb.problem_name,COUNT(*) AS yearly FROM `case_problem_reported` prb\r\n" + 
			"	JOIN `case_details` cs ON prb.`case_details_case_id`=cs.`case_id`\r\n" + 
			"	WHERE YEAR(cs.`create_date`)=YEAR(NOW())\r\n" + 
			"	GROUP BY prb.`problem_name`)AS yerpo\r\n" + 
			"LEFT JOIN \r\n" + 
			"(SELECT prb.problem_name,COUNT(*) AS monthly FROM `case_problem_reported` prb\r\n" + 
			"	JOIN `case_details` cs ON prb.`case_details_case_id`=cs.`case_id`\r\n" + 
			"	WHERE YEAR(cs.`create_date`)=YEAR(NOW()) AND MONTH(cs.create_date)=MONTH(NOW())\r\n" + 
			"	GROUP BY prb.`problem_name`) AS mrepo\r\n" + 
			"ON mrepo.problem_name=yerpo.problem_name ORDER BY monthly DESC LIMIT 10",nativeQuery=true)
	java.util.List<java.util.Map<String,Object>> getTop5Complaints();
	@Query(value="SELECT dummary.no_of_days,IFNULL(final_result.no_of_case_registred_monthly,0) AS no_of_case_registred_monthly FROM\r\n" + 
			"(SELECT '<=1 Days' AS no_of_days,0 AS no_of_case_registred_monthly\r\n" + 
			"UNION ALL\r\n" + 
			"SELECT '>1 Days& <=2 Days' AS no_of_days,0 AS no_of_case_registred_monthly\r\n" + 
			"UNION ALL\r\n" + 
			"SELECT '3-7 Days' AS no_of_days,0 AS no_of_case_registred_monthly\r\n" + 
			"UNION ALL\r\n" + 
			"SELECT '7-15 Days' AS no_of_days,0 AS no_of_case_registred_monthly\r\n" + 
			"UNION ALL\r\n" + 
			"SELECT '15-30 Days' AS no_of_days,0 AS no_of_case_registred_monthly\r\n" + 
			"UNION ALL\r\n" + 
			"SELECT '>30 Days' AS no_of_days,0 AS no_of_case_registred_monthly) AS dummary\r\n" + 
			"\r\n" + 
			"LEFT JOIN \r\n" + 
			"(SELECT yresult.no_of_days,COUNT(*) AS no_of_case_registred_monthly FROM (\r\n" + 
			"\r\n" + 
			"SELECT DATEDIFF(close_date,`create_date`), \r\n" + 
			"	CASE \r\n" + 
			"		WHEN DATEDIFF(close_date,`create_date`)>30 THEN \">30 Days\"\r\n" + 
			"		WHEN DATEDIFF(close_date,`create_date`)>15 AND DATEDIFF(close_date,`create_date`)<=30 THEN \"15-30 Days\"\r\n" + 
			"		WHEN DATEDIFF(close_date,`create_date`)>7 AND DATEDIFF(close_date,`create_date`)<=15 THEN \"7-15 Days\" \r\n" + 
			"		WHEN DATEDIFF(close_date,`create_date`)>3 AND DATEDIFF(close_date,`create_date`)<=7 THEN \"3-7 Days\" \r\n" + 
			"		WHEN DATEDIFF(close_date,`create_date`)>1 AND DATEDIFF(close_date,`create_date`)<=2 THEN \">1 Days& <=2 Days\" \r\n" + 
			"		ELSE \"<=1 Days\"\r\n" + 
			"	END AS no_of_days\r\n" + 
			"FROM `case_details` WHERE `close_date` IS NOT NULL AND YEAR(close_date)=YEAR(NOW()) AND MONTH(close_date)=MONTH(NOW())) AS yresult GROUP BY yresult.no_of_days) AS final_result \r\n" + 
			"\r\n" + 
			"ON dummary.no_of_days=final_result.no_of_days",nativeQuery = true)
	java.util.List<java.util.Map<String,Object>> getAgingOfCloserComplaints();
	@Query(value="SELECT * FROM \r\n" + 
			"(SELECT semi_final_year.case_types_name,\r\n" + 
			"	semi_final_year.activity_name,\r\n" + 
			"	IFNULL(semi_final_month.count_case_current_month,0) AS count_case_current_month,\r\n" + 
			"	IFNULL(semi_final_month.current_month_revenue,0) AS current_month_revenue,\r\n" + 
			"	IFNULL(semi_final_year.count_case_current_year,0) AS count_case_current_year,\r\n" + 
			"	IFNULL(semi_final_year.current_year_revenue,0) AS current_year_revenue FROM \r\n" + 
			"\r\n" + 
			"(SELECT ctype.`case_types_name`,'Opportunity' AS activity_name,COUNT(*) AS count_case_current_year,0 AS current_year_revenue FROM `oppertunity` opr\r\n" + 
			"	JOIN `case_details` cs ON opr.`case_details_case_id`=cs.`case_id`\r\n" + 
			"	JOIN `case_types` ctype ON ctype.`case_type_id`=cs.`case_type_case_type_id` \r\n" + 
			"	\r\n" + 
			"	WHERE YEAR(opr.`create_date`)=YEAR(NOW())\r\n" + 
			"	\r\n" + 
			"	GROUP BY ctype.`case_types_name`) AS semi_final_year\r\n" + 
			"	\r\n" + 
			"LEFT JOIN \r\n" + 
			"\r\n" + 
			"(SELECT ctype.`case_types_name`,'Opportunity' AS activity_name,COUNT(*) AS count_case_current_month,0 AS current_month_revenue FROM `oppertunity` opr\r\n" + 
			"	JOIN `case_details` cs ON opr.`case_details_case_id`=cs.`case_id`\r\n" + 
			"	JOIN `case_types` ctype ON ctype.`case_type_id`=cs.`case_type_case_type_id` \r\n" + 
			"	\r\n" + 
			"	WHERE MONTH(opr.`create_date`) =MONTH(NOW()) AND YEAR(opr.`create_date`)=YEAR(NOW())\r\n" + 
			"	\r\n" + 
			"	GROUP BY ctype.`case_types_name`) AS semi_final_month\r\n" + 
			"\r\n" + 
			"ON semi_final_year.case_types_name=semi_final_month.case_types_name AND semi_final_year.activity_name=semi_final_month.activity_name\r\n" + 
			"\r\n" + 
			"UNION ALL\r\n" + 
			"\r\n" + 
			"SELECT  quote_semifinal_yearly.case_types_name,\r\n" + 
			"	quote_semifinal_yearly.activity_name,\r\n" + 
			"	IFNULL(quote_semifinal_monthly.no_of_quote_montyly,0) AS no_of_quote_montyly,\r\n" + 
			"	IFNULL(quote_semifinal_monthly.current_month_revenue,0) AS current_month_revenue,\r\n" + 
			"	IFNULL(quote_semifinal_yearly.no_of_quote_yearly,0) AS no_of_quote_yearly,\r\n" + 
			"	IFNULL(quote_semifinal_yearly.current_year_revenue,0) AS current_year_revenue FROM\r\n" + 
			"\r\n" + 
			"(SELECT ctype.`case_types_name`,'Quotes Sent' AS activity_name,COUNT(*) AS no_of_quote_yearly,SUM(qt.`sub_total`) AS current_year_revenue FROM `quote` qt\r\n" + 
			"	JOIN `oppertunity` opr ON opr.`oppertunity_id`=qt.`oppertunity_oppertunity_id`\r\n" + 
			"	JOIN `case_details` cs ON cs.`case_id`=opr.`case_details_case_id`\r\n" + 
			"	JOIN `case_types` ctype ON ctype.`case_type_id`=cs.`case_type_case_type_id`\r\n" + 
			"	WHERE YEAR(qt.`quote_sent_date`)=YEAR(NOW()) AND qt.`is_deleted`=FALSE AND qt.`quote_status_id` IN (2,4,7,8)\r\n" + 
			"	GROUP BY ctype.`case_types_name`) AS quote_semifinal_yearly\r\n" + 
			"	\r\n" + 
			"LEFT JOIN	\r\n" + 
			"	\r\n" + 
			"(SELECT ctype.`case_types_name`,'Quotes Sent' AS activity_name,COUNT(*) AS no_of_quote_montyly,SUM(qt.`sub_total`) AS current_month_revenue FROM `quote` qt\r\n" + 
			"	JOIN `oppertunity` opr ON opr.`oppertunity_id`=qt.`oppertunity_oppertunity_id`\r\n" + 
			"	JOIN `case_details` cs ON cs.`case_id`=opr.`case_details_case_id`\r\n" + 
			"	JOIN `case_types` ctype ON ctype.`case_type_id`=cs.`case_type_case_type_id`\r\n" + 
			"	WHERE YEAR(qt.`quote_sent_date`)=YEAR(NOW()) AND MONTH(qt.`quote_sent_date`)=MONTH(NOW()) AND qt.`is_deleted`=FALSE AND qt.`quote_status_id` IN (2,4,7,8)\r\n" + 
			"	GROUP BY ctype.`case_types_name`) AS quote_semifinal_monthly\r\n" + 
			"	\r\n" + 
			"ON quote_semifinal_yearly.case_types_name=quote_semifinal_monthly.case_types_name AND quote_semifinal_yearly.activity_name=quote_semifinal_monthly.activity_name) AS final ORDER BY 1 ASC,2 ASC",nativeQuery = true)
	java.util.List<java.util.Map<String,Object>> getPaidRepairDashboard();
	
	
	@Query("select new com.pratikshat.exicom.model.response.CaseResponse(ca) from CaseDetails ca left join ca.assets ast left join ca.site st left join st.account acn left join ca.ownerDetails onr left join ca.prc pr left join ca.engineer eng left join eng.contact engcnt left join engcnt.account engact left join ca.prc prc where pr.id=:prcId")
	com.pratikshat.exicom.model.response.CaseResponse getCasesByPreRegistrationCase(@Param("prcId")long prcId);
	
	@Query("select ca from CaseDetails ca left join ca.assets ast left join ca.site st left join st.account acn left join st.primaryAccount pacn left join ca.ownerDetails onr left join ca.createdBy createdby left join ca.modifiedBy modified left join ca.engineer eng left join eng.contact econ left join econ.account engact left join ca.serviceCenter scr left join ca.prc prc where ca.manualId=:manualId")
	CaseDetails findByCaseManualId(@Param("manualId")String manualId);
	
}
