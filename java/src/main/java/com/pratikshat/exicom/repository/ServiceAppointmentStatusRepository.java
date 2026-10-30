package com.pratikshat.exicom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.ServiceAppointmentStatus;

@Repository
public interface ServiceAppointmentStatusRepository extends JpaRepository<ServiceAppointmentStatus, Long>{

	@Query("select st from ServiceAppointmentStatus st where st.statusId in :statuslist")
	List<ServiceAppointmentStatus> getStatus(@Param("statuslist")List<Long> statuslist);
	
	@Query("select st from ServiceAppointmentStatus st where st.statusId=:id")
	ServiceAppointmentStatus getStatusWithId(Long id);
	
	@Query(value="SELECT \r\n" + 
			"`status_name` AS `name`\r\n" + 
			"FROM `service_appointment_status`\r\n" + 
			"ORDER BY `status_name` ASC",nativeQuery=true)
	java.util.List<String> getServiceAppointmentStatusList();
}
