package com.pratikshat.exicom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.DeliveryChallanShipmentMode;

@Repository
public interface DeliveryChallanShipmentModeRepository extends JpaRepository<DeliveryChallanShipmentMode, Long> {

	List<DeliveryChallanShipmentMode> findAll();
}
