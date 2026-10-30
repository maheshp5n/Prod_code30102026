package com.pratikshat.exicom.repository;

import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.BtAnotherServiceAppointment;

@Repository
public interface BtAnotherServiceAppointmentRepository extends JpaRepository<BtAnotherServiceAppointment, Long>,BtAnotherServiceAppointmentCustomRepository{

	@Query(value = "SELECT `service_service_appointment_id` AS `serviceAppointmentId` FROM `bt_another_service_appointment`  WHERE `id`=:id", nativeQuery=true)
	Map<String, Object> getAnotherServiceById(@Param("id") Long _id);


}
