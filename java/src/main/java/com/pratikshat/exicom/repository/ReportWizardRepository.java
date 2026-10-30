package com.pratikshat.exicom.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.ReportWizard;
@Repository
public interface ReportWizardRepository extends JpaRepository<ReportWizard,Long>,ReportWizardCustome{
	
	//ADMIN ALL REPORT
	@Query(value="select id AS id,`created_date` AS createdDate,`last_modified_date` AS lastModifiedDate,`presentation_type` AS presentationType,`report_description` AS reportDescription,`report_name` AS reportName,`report_query` AS reportQuery,`report_type` AS reportType,`created_by_email_id` AS createdBy,`last_modified_by_email_id` AS lastModifiedBy,report_link_id as reportLinkId from report_wizard",nativeQuery=true)
	List<Map<String,Object>> getAll();
	
	@Query(value="select id AS id,`created_date` AS createdDate,`last_modified_date` AS lastModifiedDate,`presentation_type` AS presentationType,`report_description` AS reportDescription,`report_name` AS reportName,`report_query` AS reportQuery,`report_type` AS reportType,`created_by_email_id` AS createdBy,`last_modified_by_email_id` AS lastModifiedBy,report_link_id as reportLinkId from report_wizard where id=:Id",nativeQuery=true)
	Map<String,Object> getById(@Param("Id")Long Id);
	
	//PARAM
	@Query(value="SELECT `id` AS id,`param_display` AS paramDisplay,`param_identifier` AS paramIdentifier,`param_type` AS paramType,`report_wizard_id` AS reportWizard FROM report_wizard_param WHERE report_wizard_id=:reportId",nativeQuery=true)
	List<Map<String,Object>> getParamByReportId(@Param("reportId")Long reportId);
	
	//EXECUTER LIST BY ID
	@Query(value="SELECT `id` AS id,`executer` AS executer,`executer_type` AS executerType,`report_wizard_id` AS reportWizard FROM report_wizard_executer WHERE `report_wizard_id`=:reportId",nativeQuery=true)
	List<Map<String,Object>> getExecuterById(@Param("reportId")Long reportId);
	
	//LIST OF REPORT BY USER ROLE ANDY BY USER ID FOR USER
	@Query(value="select rm.id AS id,rm.`created_date` AS createdDate,rm.`last_modified_date` AS lastModifiedDate,rm.`presentation_type` AS presentationType,rm.`report_description` AS reportDescription,rm.`report_name` AS reportName,rm.`report_type` AS reportType,rm.`created_by_email_id` AS createdBy,rm.`last_modified_by_email_id` AS lastModifiedBy,rm.report_link_id as reportLinkId  from report_wizard rm join report_wizard_executer ex on rm.id=ex.report_wizard_id where ex.executer_type=:type and ex.executer=:executer",nativeQuery=true)
	List<Map<String,Object>> getAllReportByTypeAndExecuter(@Param("type")String type,@Param("executer")String executer);
	
	//LIST OF REPORT FOR HOM PAGE BY USER ID FOR USER
	@Query(value="select rm.id AS id,rm.`created_date` AS createdDate,rm.`last_modified_date` AS lastModifiedDate,rm.`presentation_type` AS presentationType,rm.`report_description` AS reportDescription,rm.`report_name` AS reportName,rm.`report_type` AS reportType,rm.`created_by_email_id` AS createdBy,rm.`last_modified_by_email_id` AS lastModifiedBy,rm.report_link_id as reportLinkId  from report_wizard rm join report_wizard_executer ex on rm.id=ex.report_wizard_id where ex.executer_type=:type and ex.executer=:executer and rm.report_type=:reportType",nativeQuery=true)
	List<Map<String,Object>> getAllReportByTypeAndExecuter(@Param("type")String executerType,@Param("executer")String executer,@Param("reportType")String reportType);
	
	@Query(value="SELECT `authorities` as id,`authorities` as `value` FROM `user_roles`",nativeQuery=true)
	List<Map<String,Object>> getRoles();
	
	@Query(value="SELECT `email_id` AS id,CONCAT(IFNULL(`first_name`,''),' ',IFNULL(`middle_name`,''),' ',IFNULL(`last_name`,'') ) AS `value` FROM `employee_details`",nativeQuery=true)
	List<Map<String,Object>> getUser();
	
	//FOR LIST
	@Query(value="select id,report_name,report_description from report_wizard",nativeQuery=true)
	List<Map<String,Object>> getReportNameforList();
	
}
