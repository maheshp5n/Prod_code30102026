package com.pratikshat.exicom.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ObjectDeffinitionRepo extends JpaRepository<com.pratikshat.exicom.entity.ObjectDefinition, String>,ObjectDeffinitionRepoCustome {
	com.pratikshat.exicom.entity.ObjectDefinition findByObjectName(String name);
	@Query(value="select object_name,object_lable_name from object_definition",nativeQuery=true)
	public List<Map<String,String>> getObjectForList();
	
	@Query(value="SELECT ol.`object_name`,ol.`object_id`,ol.`key_name`, ol.`value`,ol.old_value,ol.`created_date`,ol.`logged_by_email_id`,emp.`first_name`,emp.`last_name`,emp.`middle_name`,IFNULL(ofd.`object_field_label_name`,ol.`key_name`) AS fieldLabelName FROM object_log ol \r\n" + 
			"	JOIN object_definition od ON ol.`object_name`=od.`object_name`\r\n" + 
			"	LEFT JOIN object_field_definition ofd ON ofd.`object_def_object_name`=od.`object_name` AND ofd.`object_field_name`=ol.`key_name`\r\n" + 
			"	JOIN employee_details emp ON emp.`email_id`=ol.`logged_by_email_id` WHERE ol.`object_name`=:objectName AND ol.`object_id`=:objectId",nativeQuery = true)
	public List<Map<String,Object>> getObjectHisotory(@Param("objectName")String objectName,@Param("objectId")String objectId);
	
	@Query(value="SELECT ol.`object_name`,ol.`object_id`,ol.`key_name`, ol.`value`,ol.old_value,ol.`created_date`,ol.`logged_by_email_id`,emp.`first_name`,emp.`last_name`,emp.`middle_name`,IFNULL(ofd.`object_field_label_name`,ol.`key_name`) AS fieldLabelName FROM object_log ol \r\n" + 
			"	JOIN object_definition od ON ol.`object_name`=od.`object_name`\r\n" + 
			"	LEFT JOIN object_field_definition ofd ON ofd.`object_def_object_name`=od.`object_name` AND ofd.`object_field_name`=ol.`key_name`\r\n" + 
			"	JOIN employee_details emp ON emp.`email_id`=ol.`logged_by_email_id` WHERE ol.`object_name`=:objectName AND ol.`object_id`=:objectId",nativeQuery = true,countQuery =
			"SELECT count(*) as counterData FROM object_log ol \r\n" + 
					"	JOIN object_definition od ON ol.`object_name`=od.`object_name`\r\n" + 
					"	LEFT JOIN object_field_definition ofd ON ofd.`object_def_object_name`=od.`object_name` AND ofd.`object_field_name`=ol.`key_name`\r\n" + 
					"	JOIN employee_details emp ON emp.`email_id`=ol.`logged_by_email_id` WHERE ol.`object_name`=:objectName AND ol.`object_id`=:objectId")
	public Page<Map<String,Object>> getObjectHisotory(@Param("objectName")String objectName,@Param("objectId")String objectId,Pageable page);
	
}
