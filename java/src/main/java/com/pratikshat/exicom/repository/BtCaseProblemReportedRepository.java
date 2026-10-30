package com.pratikshat.exicom.repository;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.BtCaseProblemReported;
import com.pratikshat.exicom.model.response.BtCaseProblemResponse;

@Repository
public interface BtCaseProblemReportedRepository extends JpaRepository<BtCaseProblemReported, Long>{
	@Query("select new com.pratikshat.exicom.model.response.BtCaseProblemResponse(pr.probleReportedId,pr.problemName) from BtCaseProblemReported pr join pr.caseDetails cs where cs.id=:caseId")
	Set<BtCaseProblemResponse> getByCaseId(@Param("caseId")long caseid);

	@Query(value = "SELECT  \r\n"
			+ "		IFNULL(prb.problem_name,'') AS `Problem Name`, \r\n"
			+ "		IFNULL(COUNT(1),0) AS `Count` \r\n"
			+ "		FROM `bt_case_problem_reported` prb  \r\n"
			+ "		JOIN `bt_case` cd ON prb.`case_details_id`=cd.`id`\r\n"
			+ "		LEFT JOIN `bt_service_appointment` sa ON sa.`case_details_id`=cd.`id`\r\n"
			+ "		WHERE (sa.`owner_details_email_id` IN (:userList) \r\n"
			+ "			OR cd.`owner_details_email_id` IN (:userList))\r\n"
			+ "		AND YEAR(cd.`created_date`)=YEAR(NOW())  \r\n"
			+ "		AND MONTH(cd.`created_date`)=MONTH(NOW())  \r\n"
			+ "		GROUP BY prb.problem_name\r\n"
			+ "		ORDER BY COUNT(1) DESC LIMIT :limit",nativeQuery = true)
	List<Map<String, Object>> getProblem_mtd(List<String> userList, int limit);
	

}
