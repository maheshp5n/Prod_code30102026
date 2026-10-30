package com.pratikshat.exicom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.LocationRecentVisit;

@Repository
public interface LocationRecentVisitRepository extends JpaRepository<LocationRecentVisit, Long> {
	LocationRecentVisit findByLocationIdAndVisitorId(long locationId,String visitorId);
	
}
