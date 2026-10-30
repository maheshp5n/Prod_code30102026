package com.pratikshat.exicom.repository;

import com.pratikshat.exicom.entity.BatteryFSRRecentVisit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BatteryFSRRecentVisitRepository extends JpaRepository<BatteryFSRRecentVisit,Long> {

	BatteryFSRRecentVisit findByBatteryFSRIdAndVisitorId(long batteryFSRId,String username);
	
}
