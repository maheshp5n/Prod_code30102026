package com.pratikshat.exicom.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.SmsSetting;
@Repository
public interface SmsSettingRepository extends JpaRepository<SmsSetting, Long>,SmsSettingRepositoryCustome {
	int countByUrl(String url);
	@Query(value="SELECT sms.`id`,\r\n" + 
			"		sms.`active`,\r\n" + 
			"		sms.`content_type` AS contentType,\r\n" + 
			"		sms.`created_date` AS createdDate,\r\n" + 
			"		sms.`method_name` AS method,\r\n" + 
			"		sms.`modified_date` AS modifiedDate,\r\n" + 
			"		sms.`url` AS url, \r\n" + 
			"		ccr.`email_id` AS createdByEmailId,\r\n" + 
			"		ccr.`first_name` AS createdByfirstName,\r\n" + 
			"		ccr.`middle_name` AS createdBymiddleName,\r\n" + 
			"		ccr.`last_name` AS createdBylastName,\r\n" + 
			"		mmr.`email_id` AS modifiedByEmailId,\r\n" + 
			"		mmr.`first_name` AS modifiedByfirstName,\r\n" + 
			"		mmr.`middle_name` AS modifiedByMiddleName,\r\n" + 
			"		mmr.`last_name` AS modifiedByLastName\r\n" + 
			"		FROM `sms_setting` sms \r\n" + 
			"			JOIN `employee_details` ccr ON ccr.`email_id`=sms.`created_by_email_id`\r\n" + 
			"			JOIN `employee_details` mmr ON mmr.`email_id`=sms.`modified_by_email_id`",nativeQuery=true)
	List<Map<String,Object>> getAllSmsServer();
	
	
	@Query( value="SELECT sms.`id`,\r\n" + 
			"		sms.`active`,\r\n" + 
			"		sms.`content_type` AS contentType,\r\n" + 
			"		sms.`created_date` AS createdDate,\r\n" + 
			"		sms.`method_name` AS method,\r\n" + 
			"		sms.`modified_date` AS modifiedDate,\r\n" + 
			"		sms.`url` AS url, \r\n" + 
			"		ccr.`email_id` AS createdByEmailId,\r\n" + 
			"		ccr.`first_name` AS createdByfirstName,\r\n" + 
			"		ccr.`middle_name` AS createdBymiddleName,\r\n" + 
			"		ccr.`last_name` AS createdBylastName,\r\n" + 
			"		mmr.`email_id` AS modifiedByEmailId,\r\n" + 
			"		mmr.`first_name` AS modifiedByfirstName,\r\n" + 
			"		mmr.`middle_name` AS modifiedByMiddleName,\r\n" + 
			"		mmr.`last_name` AS modifiedByLastName\r\n" + 
			"		FROM `sms_setting` sms \r\n" + 
			"			JOIN `employee_details` ccr ON ccr.`email_id`=sms.`created_by_email_id`\r\n" + 
			"			JOIN `employee_details` mmr ON mmr.`email_id`=sms.`modified_by_email_id` WHERE sms.id=:id",nativeQuery=true)
	Map<String,Object> getSmsServer(@Param("id")Long id);
	
	@Query(value="SELECT * FROM `sms_setting_headers` WHERE `sms_setting_id`=:id",nativeQuery=true)
	List<Map<String,Object>> getHeadersBySmsId(@Param("id")Long id);
	@Query(value="SELECT * FROM `sms_setting_params` WHERE sms_setting_id=:id",nativeQuery=true)
	List<Map<String,Object>> getParamsBySmsId(@Param("id")Long id);
	@Modifying
	@Query(value="update sms_setting set `active`=:flg",nativeQuery=true)
	void updateByActive(@Param("flg")boolean flg);
	
}
