package com.pratikshat.exicom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.ServiceAppointmentRecentVisit;

@Repository
public interface ServiceAppointmentRecentVisitRepository extends JpaRepository<ServiceAppointmentRecentVisit, Long> {

	ServiceAppointmentRecentVisit findByServiceAppointmentIdAndVisitorId(long serviceAppointmentId,String userEmailid);
}
