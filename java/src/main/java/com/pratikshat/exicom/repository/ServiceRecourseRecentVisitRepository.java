package com.pratikshat.exicom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.ServiceResourceRecentVisit;

@Repository
public interface ServiceRecourseRecentVisitRepository extends JpaRepository<ServiceResourceRecentVisit,Long> {
	ServiceResourceRecentVisit findByServiceResourceIdAndVisitorId(long serviceId,String userEmailid);
	
}
