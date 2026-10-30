package com.pratikshat.exicom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.BatteryComplaintStatus;

@Repository
public interface BatteryComplaintStatusRepository extends JpaRepository<BatteryComplaintStatus, Integer>{
	
	List<BatteryComplaintStatus> findAll();
}
