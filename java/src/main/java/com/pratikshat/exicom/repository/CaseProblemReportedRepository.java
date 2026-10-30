package com.pratikshat.exicom.repository;



import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CaseProblemReportedRepository extends JpaRepository<com.pratikshat.exicom.entity.CaseProblemReported, Long> {
	@Query("select new com.pratikshat.exicom.entity.CaseProblemReported(pr.probleReportedId,pr.problemName) from CaseProblemReported pr join pr.caseDetails cs where cs.id=:caseId")
	java.util.Set<com.pratikshat.exicom.entity.CaseProblemReported> getByCaseId(@Param("caseId")long caseid);
	
	@Modifying
	@Transactional
	int deleteByCaseDetails_CaseId(long caseId);
	
	@Query(value="SELECT \r\n" + 
			"IFNULL(prb.problem_name,'') AS `Problem Name`,\r\n" + 
			"IFNULL(COUNT(1),0) AS `Count`\r\n" + 
			"FROM `case_problem_reported` prb  \r\n" + 
			"JOIN `case_details` cd ON prb.`case_details_case_id`=cd.`case_id`\r\n" + 
			"LEFT JOIN `service_appointment` sa ON sa.`case_details_case_id`=cd.`case_id`\r\n" + 
			"WHERE (sa.`owner_details_email_id` IN (:userList)\r\n" + 
			"	OR cd.`owner_details_email_id` IN (:userList))\r\n" + 
			"AND YEAR(cd.`create_date`)=YEAR(NOW()) \r\n" + 
			"AND MONTH(cd.`create_date`)=MONTH(NOW()) \r\n" + 
			"GROUP BY prb.problem_name\r\n" + 
			"ORDER BY COUNT(1) DESC LIMIT :limit", nativeQuery=true)
	List<Map<String,Object>> getComplaint_mtd(@Param("userList")List<String> userList,@Param("limit")int limit);
	
	@Query(value="SELECT \r\n" + 
			"IFNULL(prb.problem_name,'') AS `Problem Name`,\r\n" + 
			"IFNULL(COUNT(1),0) AS `Count`\r\n" + 
			"FROM `case_problem_reported` prb  \r\n" + 
			"JOIN `case_details` cd ON prb.`case_details_case_id`=cd.`case_id`\r\n" + 
			"LEFT JOIN `service_appointment` sa ON sa.`case_details_case_id`=cd.`case_id`\r\n" + 
			"WHERE (sa.`owner_details_email_id` IN (:userList)\r\n" + 
			"	OR cd.`owner_details_email_id` IN (:userList))\r\n" + 
			"AND DATE_FORMAT(cd.`create_date`,'%Y-%m-%d') \r\n" + 
			"BETWEEN \r\n" + 
			"	CASE\r\n" + 
			"		WHEN MONTH(NOW())<4 THEN DATE_FORMAT(CONCAT(YEAR(NOW()-1),'-04','-01'),'%Y-%m-%d')\r\n" + 
			"		ELSE DATE_FORMAT(CONCAT(YEAR(NOW()),'-04','-01'),'%Y-%m-%d')\r\n" + 
			"	END\r\n" + 
			"AND \r\n" + 
			"	CASE\r\n" + 
			"		WHEN MONTH(NOW())<4 THEN DATE_FORMAT(CONCAT(YEAR(NOW()),'-03','-31'),'%Y-%m-%d')\r\n" + 
			"		ELSE DATE_FORMAT(CONCAT(YEAR(NOW())+1,'-03','-31'),'%Y-%m-%d')\r\n" + 
			"	END\r\n" + 
			"GROUP BY prb.problem_name\r\n" + 
			"ORDER BY COUNT(1) DESC LIMIT :limit", nativeQuery=true)
	List<Map<String,Object>> getComplaint_ytd(@Param("userList")List<String> userList,@Param("limit")int limit);
}
