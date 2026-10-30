package com.pratikshat.exicom.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.BatteryFSR;
import com.pratikshat.exicom.model.response.BatteryFSRResponse;

@Repository
public interface BatteryFSRRepository extends JpaRepository<BatteryFSR, Long>,BatteryFSRCustomRepository {

	@Query("select new com.pratikshat.exicom.model.response.BatteryFSRResponse(fsr) from BatteryFSR fsr left join fsr.createdBy cby left join fsr.mdifiedBy mby left join fsr.serviceAppointment sa left join sa.caseDetails cd left join sa.serviceResource sr left join sr.contact cnt where fsr.id=:id")
	BatteryFSRResponse getBatteryFSRById(@Param("id")long id);
	
	@Query("select new com.pratikshat.exicom.model.response.BatteryFSRResponse(fsr) from BatteryFSR fsr left join fsr.createdBy cby left join fsr.mdifiedBy mby left join fsr.serviceAppointment sa left join sa.caseDetails cd left join sa.serviceResource sr left join sr.contact cnt")
	Page<BatteryFSRResponse> getBatteryFSR(Pageable pageable);
	
	@Query("select new com.pratikshat.exicom.model.response.BatteryFSRResponse(fsr) from BatteryFSR fsr left join fsr.createdBy cby left join fsr.mdifiedBy mby left join fsr.serviceAppointment sa left join sa.caseDetails cd left join sa.serviceResource sr left join sr.contact cnt where sa.ownerDetails in :userlist")
	Page<BatteryFSRResponse> getBatteryFSR(@Param("userlist")List<com.pratikshat.exicom.entity.EmployeeDetails>userlist, Pageable pageable);
	
	@Query("select new com.pratikshat.exicom.model.response.BatteryFSRResponse(fsr) from BatteryFSR fsr left join fsr.createdBy cby left join fsr.mdifiedBy mby left join fsr.serviceAppointment sa left join sa.caseDetails cd left join sa.serviceResource sr left join sr.contact cnt join BatteryFSRRecentVisit rc on fsr.id=rc.batteryFSRId where rc.visitorId=:username")
	Page<BatteryFSRResponse> getBatteryFSRRecentVisit(@Param("username")String username,Pageable page);
	
	
	@Query("select new com.pratikshat.exicom.model.response.BatteryFSRResponse(fsr) from BatteryFSR fsr left join fsr.createdBy cby left join fsr.mdifiedBy mby left join fsr.serviceAppointment sa left join sa.caseDetails cd left join sa.serviceResource sr left join sr.contact cnt where cd.caseId=:caseId")
	Page<BatteryFSRResponse> getBatteryFSRByCaseId(@Param("caseId")long caseId,Pageable page);
	
	
	@Query("select new com.pratikshat.exicom.model.response.BatteryFSRResponse(fsr) from BatteryFSR fsr left join fsr.createdBy cby left join fsr.mdifiedBy mby left join fsr.serviceAppointment sa left join sa.caseDetails cd left join sa.serviceResource sr left join sr.contact cnt where sa.serviceAppointmentId=:serviceAppointmentId")
	Page<BatteryFSRResponse> getBatteryFSRByServiceAppointmentId(@Param("serviceAppointmentId")long serviceAppointmentId,Pageable page);
	
	
}
