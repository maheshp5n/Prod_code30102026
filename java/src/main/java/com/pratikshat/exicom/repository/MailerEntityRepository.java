package com.pratikshat.exicom.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.MailerEntity;
@Repository
public interface MailerEntityRepository extends JpaRepository<MailerEntity, Long>,MailerEntityRepositoryCustome {
	@Query(value="SELECT \r\n" + 
			"	mailer.`id`,\r\n" + 
			"	mailer.`active`,\r\n" + 
			"	mailer.`created_date`,\r\n" + 
			"	mailer.`default_user_name`,\r\n" + 
			"	mailer.`last_modified_date`,\r\n" + 
			"	mailer.`required_authentication`,\r\n" + 
			"	mailer.`required_ssl`,\r\n" + 
			"	mailer.`required_tls_start`,\r\n" + 
			"	mailer.`smtp_port`,\r\n" + 
			"	mailer.`smtp_server`,\r\n" + 
			"	mailer.`ssl_port`,\r\n" + 
			"	mailer.`created_by_email_id`,\r\n" + 
			"	mailer.`last_modified_by_email_id`,\r\n" + 
			"	crem.`first_name` AS cr_first_name,\r\n" + 
			"	crem.`middle_name` AS cr_middle_name,\r\n" + 
			"	crem.`last_name` AS cr_last_name,\r\n" + 
			"	mrem.`first_name` AS mr_first_name,\r\n" + 
			"	mrem.`middle_name` AS mr_middle_name,\r\n" + 
			"	mrem.`last_name` AS mr_last_name\r\n" + 
			"	\r\n" + 
			" FROM `mailer_entity` mailer \r\n" + 
			"	JOIN `employee_details` crem ON crem.`email_id`=mailer.`created_by_email_id`\r\n" + 
			"	JOIN `employee_details` mrem ON mrem.`email_id`=mailer.`last_modified_by_email_id`",nativeQuery = true)
	List<Map<String,Object>> getAll();
	@Modifying
	@Query(value="update `mailer_entity` set `active`=:flg",nativeQuery=true)
	void updateAllActivity(@Param("flg")boolean flg);
	@Query(value="SELECT `id`,`active`,`mailer_property_name`,`mailerproperty_value` FROM `mailer_property` WHERE `mailer_entity_id`=:id",nativeQuery=true)
	List<Map<String,Object>> getMailerentityProperty(@Param("id")Long id);
}
