package com.pratikshat.exicom.repository;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
@org.springframework.stereotype.Repository
public interface ServiceAppoinmentProductConsumedRepository extends JpaRepository<com.pratikshat.exicom.entity.ServiceAppoinmentProductConsumed, Long>,ServiceAppointmentProductConsumedCustomeRepo {
	@Query("select new com.pratikshat.exicom.model.response.ServiceAppointmentProductConsumedResponse(sc.serviceAppointmentId,sc.appointmentNumber,prd.productPartCode,prd.pdoductDescription,spc.quantity,cnt.consumptionTypeName,spc.remark) from ServiceAppoinmentProductConsumed spc join spc.product prd join spc.serviceAppointment sc join spc.consumptionType cnt where sc.serviceAppointmentId=:serviceAppointmentId")
	Page<com.pratikshat.exicom.model.response.ServiceAppointmentProductConsumedResponse> getProductConsumed(@Param("serviceAppointmentId")long serviceAppointmentId, Pageable pageable);
	
	
	@Query("select new com.pratikshat.exicom.model.response.ServiceAppointmentProductConsumedResponse(sc.serviceAppointmentId,sc.appointmentNumber,prd.productPartCode,prd.pdoductDescription,spc.quantity,cnt.consumptionTypeName,spc.remark) from ServiceAppoinmentProductConsumed spc join spc.product prd join spc.serviceAppointment sc left join sc.caseDetails cd join spc.consumptionType cnt where cd.caseId=:caseId")
	Page<com.pratikshat.exicom.model.response.ServiceAppointmentProductConsumedResponse> getProductConsumedByCaseId(@Param("caseId")long caseId, Pageable pageable);
	
	@Query(value="SELECT \r\n" + 
			"	srv.id as id,\r\n"+
			"	srv.`service_appointment_service_appointment_id` AS serviceAppointmentId,\r\n" + 
			"	sr.`appointment_number` AS serviceAppointmentManualId,\r\n" + 
			"	srv.`consumption_type_consumption_type_name` AS consumedType,\r\n" + 
			"	srv.`created_date` AS consumeDate,\r\n" + 
			"	srv.`product_product_part_code` AS partCode,\r\n" + 
			"	srv.`quantity` AS consumedQty,\r\n" + 
			"	srv.`remark` AS remark,\r\n" + 
			"	prd.`pdoduct_description` AS partDescription,\r\n" + 
			"	prd.`product_type` AS productType,\r\n" + 
			"	litem.`consumed_line_item_serial_number` AS okSerialNumber,\r\n" + 
			"	litem.`replaced_line_item_serial_number` AS faultySerialNumber,\r\n" + 
			"	litem.`faulty_partcode` as faultyPartcode\r\n"+
			" FROM `service_appoinment_product_consumed` srv\r\n" + 
			"	JOIN `service_appointment` sr ON srv.`service_appointment_service_appointment_id`=sr.`service_appointment_id`\r\n" + 
			"	JOIN `products` prd ON prd.`product_part_code`=srv.`product_product_part_code`\r\n" + 
			"	LEFT JOIN `service_appointment_product_consumed_line_items` litem ON litem.`product_consumed_id`=srv.`id`\r\n" + 
			"	WHERE srv.`service_appointment_service_appointment_id`=:serviceAppointmentId",nativeQuery=true,countQuery = "SELECT COUNT(*) AS counterQuery\r\n" + 
					" FROM `service_appoinment_product_consumed` srv\r\n" + 
					"	JOIN `service_appointment` sr ON srv.`service_appointment_service_appointment_id`=sr.`service_appointment_id`\r\n" + 
					"	JOIN `products` prd ON prd.`product_part_code`=srv.`product_product_part_code`\r\n" + 
					"	LEFT JOIN `service_appointment_product_consumed_line_items` litem ON litem.`product_consumed_id`=srv.`id`\r\n" + 
					"	WHERE srv.`service_appointment_service_appointment_id`=:serviceAppointmentId")
	Page<Map<String,Object>> getProductConsumedNative(@Param("serviceAppointmentId")long serviceAppointmentId,Pageable pageable);
	@Query(value="SELECT \r\n" + 
			"	srv.id as id,\r\n"+
			"	srv.`service_appointment_service_appointment_id` AS serviceAppointmentId,\r\n" + 
			"	sr.`appointment_number` AS serviceAppointmentManualId,\r\n" + 
			"	srv.`consumption_type_consumption_type_name` AS consumedType,\r\n" + 
			"	srv.`created_date` AS consumeDate,\r\n" + 
			"	srv.`product_product_part_code` AS partCode,\r\n" + 
			"	srv.`quantity` AS consumedQty,\r\n" + 
			"	srv.`remark` AS remark,\r\n" + 
			"	prd.`pdoduct_description` AS partDescription,\r\n" + 
			"	prd.`product_type` AS productType,\r\n" + 
			"	litem.`consumed_line_item_serial_number` AS okSerialNumber,\r\n" + 
			"	litem.`replaced_line_item_serial_number` AS faultySerialNumber,\r\n" + 
			"	litem.`faulty_partcode` as faultyPartcode,\r\n"+
			"	sr.`case_details_case_id` AS caseId\r\n" + 
			" FROM `service_appoinment_product_consumed` srv\r\n" + 
			"	JOIN `service_appointment` sr ON srv.`service_appointment_service_appointment_id`=sr.`service_appointment_id`\r\n" + 
			"	JOIN `products` prd ON prd.`product_part_code`=srv.`product_product_part_code`\r\n" + 
			"	LEFT JOIN `service_appointment_product_consumed_line_items` litem ON litem.`product_consumed_id`=srv.`id`\r\n" + 
			"	WHERE sr.`case_details_case_id`=:caseId",nativeQuery=true,countQuery = "SELECT COUNT(*) AS counterQuery\r\n" + 
					" FROM `service_appoinment_product_consumed` srv\r\n" + 
					"	JOIN `service_appointment` sr ON srv.`service_appointment_service_appointment_id`=sr.`service_appointment_id`\r\n" + 
					"	JOIN `products` prd ON prd.`product_part_code`=srv.`product_product_part_code`\r\n" + 
					"	LEFT JOIN `service_appointment_product_consumed_line_items` litem ON litem.`product_consumed_id`=srv.`id`\r\n" + 
					"	WHERE sr.`case_details_case_id`=:caseId")
	Page<Map<String,Object>> getProductConsumedByCaseIdNative(@Param("caseId")long caseId, Pageable pageable);
	
}
