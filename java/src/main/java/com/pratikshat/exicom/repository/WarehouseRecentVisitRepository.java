package com.pratikshat.exicom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface WarehouseRecentVisitRepository extends JpaRepository<com.pratikshat.exicom.entity.WarehouseRecentVisit, Long> {
	com.pratikshat.exicom.entity.WarehouseRecentVisit findByWarehouseIdAndVisitorId(long warehouseId,String userid);
}
