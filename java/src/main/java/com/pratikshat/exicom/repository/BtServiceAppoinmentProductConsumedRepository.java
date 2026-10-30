package com.pratikshat.exicom.repository;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.BtServiceAppoinmentProductConsumed;

@Repository
public interface BtServiceAppoinmentProductConsumedRepository extends JpaRepository<BtServiceAppoinmentProductConsumed, Long>,BtServiceAppointmentProductConsumedCustomeRepo{

	@Query(value = "SELECT sa.`service_appointment_id` AS serviceAppointmentId,sa.`manual_id` AS serviceAppointmentManualId,\r\n"
			+ "	rp.`manual_id` AS replaceProductPartCode,rp.`product_description` AS replaceProductDescription,\r\n"
			+ "	cp.`manual_id` AS consumeProductPartCode,rp.`product_description` AS consumeProductDescription,\r\n"
			+ "	pct.`consumption_type_name` AS consumedType,btsapc.`quantity` AS consumedQty,btsapc.`remark` AS remark,\r\n"
			+ "	btsapc.`consume` AS consume\r\n"
			+ "\r\n"
			+ "FROM `bt_service_appoinment_product_consumed` btsapc\r\n"
			+ "JOIN `bt_service_appointment` sa ON sa.`service_appointment_id`=btsapc.`service_appointment_service_appointment_id`\r\n"
			+ "JOIN `bt_products` rp ON rp.`id`=btsapc.`replaced_id`\r\n"
			+ "JOIN `bt_products` cp ON cp.`id`=btsapc.`consumed_id`\r\n"
			+ "JOIN `product_consumption_type` pct ON pct.`consumption_type_name` = btsapc.`consumption_type_consumption_type_name`\r\n"
			+ "JOIN `bt_case` btcase ON btcase.`id`=sa.`case_details_id`\r\n"
			+ "\r\n"
			+ "WHERE btcase.`id`=:caseId",nativeQuery = true)
	Page<Map<String, Object>> getProductConsumedByCaseId(@Param("caseId")long caseId, Pageable pageable);
	
	@Query(value="SELECT  \r\n"
			+ "				srv.`id` AS id,\r\n"
			+ "				srv.`service_appointment_service_appointment_id` AS serviceAppointmentId,\r\n"
			+ "				sr.`manual_id` AS serviceAppointmentManualId,\r\n"
			+ "				srv.`consumption_type_consumption_type_name` AS consumedType,\r\n"
			+ "				srv.`created_date` AS consumeDate, \r\n"
			+ "				cp.`manual_id` AS partCode,\r\n"
			+ "				cp.`id` AS productId,\r\n"
			+ "				srv.`quantity` AS consumedQty,\r\n"
			+ "				srv.`remark` AS remark,\r\n"
			+ "				cp.`product_description` AS consumedProductDescription,\r\n"
			+ "				cp.`product_type` AS consumedProductType,\r\n"
			+ "				rp.`product_description` AS replacedProductDescription,\r\n"
			+ "				rp.`manual_id` AS faultyPartCode,\r\n"
			+ "				rp.`product_type` AS replacedProductType\r\n"
			+ "				FROM `bt_service_appoinment_product_consumed` srv\r\n"
			+ "				JOIN `bt_service_appointment` sr ON srv.`service_appointment_service_appointment_id`=sr.`service_appointment_id`\r\n"
			+ "				LEFT JOIN `bt_products` cp ON cp.`id`=srv.`consumed_id`\r\n"
			+ "				LEFT JOIN `bt_products` rp ON rp.`id`=srv.`replaced_id`" + 
			  "	WHERE srv.`service_appointment_service_appointment_id`=:serviceAppointmentId",nativeQuery=true,countQuery = "SELECT COUNT(*) AS counterQuery\r\n" + 
					"FROM `bt_service_appoinment_product_consumed` srv\r\n"
					+ "				JOIN `bt_service_appointment` sr ON srv.`service_appointment_service_appointment_id`=sr.`service_appointment_id`\r\n"
					+ "				LEFT JOIN `bt_products` cp ON cp.`id`=srv.`consumed_id`\r\n"
					+ "				LEFT JOIN `bt_products` rp ON rp.`id`=srv.`replaced_id`\r\n" + 
					"	WHERE srv.`service_appointment_service_appointment_id`=:serviceAppointmentId")
	Page<Map<String,Object>> getProductConsumedNative(@Param("serviceAppointmentId")long serviceAppointmentId,Pageable pageable);
}
