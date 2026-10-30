package com.pratikshat.exicom.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.pratikshat.exicom.entity.BtCaseRejectLog;
import com.pratikshat.exicom.model.response.CaseRejectLogResponse;
import com.pratikshat.exicom.repository.custom.BtCaseRejectLogRepositoryCustom;

@Repository
public interface BtCaseRejectLogRepository extends JpaRepository<BtCaseRejectLog, Long>,BtCaseRejectLogRepositoryCustom {
	
	@Query(value = "SELECT crl.`id` AS id,crl.`case_manual_id` AS caseManualId,crl.`remark` AS remark,crl.`created_date` AS createdDate,\r\n"
			+ "	cby.`email_id` AS createdByEmaill,\r\n"
			+ "	 cby.`first_name` AS createdByFirstName, cby.`middle_name` AS createdByMiddleName, cby.`last_name` AS createdByLastName,\r\n"
			+ "	 up.`file_name` AS fileName,up.`file_download_uri` AS fileUrl,up.`file_content_type` AS fileContentType,up.`file_size` AS fileSize\r\n"
			+ "	FROM `bt_case_reject_log` crl\r\n"
			+ "	JOIN `employee_details` cby ON cby.`email_id` = crl.`created_by_email_id`\r\n"
			+ "	JOIN `uploaded_files` up ON up.`file_download_uri`=crl.`file`\r\n"
			+ "	\r\n"
			+ "	WHERE crl.`case_manual_id`=:caseManualId",nativeQuery = true)
	List<Map<String, Object>> getCaseRejectLogByCaseManualId(@Param("caseManualId")String caseManualId);
}
