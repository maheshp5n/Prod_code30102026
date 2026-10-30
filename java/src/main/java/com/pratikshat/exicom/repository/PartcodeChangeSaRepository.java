package com.pratikshat.exicom.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.PartcodeChangeSa;

@Repository
public interface PartcodeChangeSaRepository extends JpaRepository<PartcodeChangeSa, Long> {

	@Query("select ch from PartcodeChangeSa ch join ch.faultyProduct fprd left join ch.consumedProduct cprd join ch.sourceLocation sloc where ch.id=:id")
	public PartcodeChangeSa findByChangeId(@Param("id")Long id);
	
	@Query(value="select * from partcode_change_sa where assigned=false and (assigned_to like :role or assigned_to like :username)",nativeQuery=true)
	public List<Map<String,Object>> getElegibalPartcode(String role,String username);
	
	@Query(value="SELECT sa.`id`,\r\n" + 
			"	sa.`approved_status`,\r\n" + 
			"	sa.`assigned`,\r\n" + 
			"	sa.`assigned_to`,\r\n" + 
			"	sa.`circle`,\r\n" + 
			"	sa.`consumed_product_serial_number`,\r\n" + 
			"	sa.`create_date`,\r\n" + 
			"	sa.`engineer_name`,\r\n" + 
			"	sa.`faulty_product_serial_number`,\r\n" + 
			"	sa.`flow_type`,\r\n" + 
			"	sa.`modified_date`,\r\n" + 
			"	sa.`price_variance`,\r\n" + 
			"	sa.`request_date`,\r\n" + 
			"	sa.`sa_id`,\r\n" + 
			"	sa.`sa_manual_id`,\r\n" + 
			"	sa.`consumed_product_product_part_code`,\r\n" + 
			"	sa.`created_by_email_id`,\r\n" + 
			"	sa.`faulty_product_product_part_code`,\r\n" + 
			"	sa.`initiated_by_email_id`,\r\n" + 
			"	sa.`modified_by_email_id`,\r\n" + 
			"	sa.`source_location_location_id`\r\n" + 
			"\r\n" + 
			"	FROM location_product_line_items litem \r\n" + 
			"	JOIN location_product_item item ON item.id=litem.`location_product_item_id` \r\n" + 
			"	JOIN partcode_change_sa sa ON sa.`faulty_product_product_part_code`=item.`product_product_part_code` AND sa.`faulty_product_serial_number`=litem.`item_serial_number` AND sa.`assigned`=FALSE\r\n" + 
			"	JOIN `service_recource` sr ON sr.`location_location_id`=item.`location_location_id`\r\n" + 
			"	WHERE litem.`location_proeuct_line_item_state_product_state_id`=2 and sr.`user_email_id`=:emailId",nativeQuery=true)
	public List<Map<String,Object>> getElegibalPartCodeFromLocation(@Param("emailId")String userEmailId);
}
