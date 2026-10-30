package com.pratikshat.exicom.repository;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.ReportRequest;
@Repository
public interface ReportRequestRepository extends JpaRepository<ReportRequest, Long> {
	@Query(value="SELECT wzd.report_name,req.request_date,req.report_status,req.id,IFNULL(req.file_size,0) as file_size FROM `report_request` req JOIN `report_wizard` wzd ON req.wizard_id=wzd.id where req.request_by_email_id=:useremailid",nativeQuery=true,countQuery = "SELECT count(*) as ctm FROM `report_request` req JOIN `report_wizard` wzd ON req.wizard_id=wzd.id where req.request_by_email_id=:useremailid")
	Page<Map<String,Object>> findByUserEmailId(@Param("useremailid")String userEmailId,Pageable page);
	@Query(value="SELECT wzd.report_name,req.request_date,req.report_status,req.id,IFNULL(req.file_size,0) as file_size FROM `report_request` req JOIN `report_wizard` wzd ON req.wizard_id=wzd.id where req.request_by_email_id=:useremailid and wzd.report_name like :reportname and date(req.request_date)=:reportdate",countQuery="SELECT count(*) as ctm FROM `report_request` req JOIN `report_wizard` wzd ON req.wizard_id=wzd.id where req.request_by_email_id=:useremailid and wzd.report_name like :reportname and date(req.request_date)=:reportdate",nativeQuery=true)
	Page<Map<String,Object>> searchByEmailIdReportNameAndDate(@Param("useremailid")String useremailid,@Param("reportname")String reportname,@Param("reportdate")String reportDate,Pageable page);
	
	@Query(value="SELECT wzd.report_name,req.request_date,req.report_status,req.id,IFNULL(req.file_size,0) AS file_size FROM `report_request` req JOIN `report_wizard` wzd ON req.wizard_id=wzd.id WHERE req.id=:id",nativeQuery=true)
	Map<String,Object> getReportByRequestId(@Param("id")long id);
}
