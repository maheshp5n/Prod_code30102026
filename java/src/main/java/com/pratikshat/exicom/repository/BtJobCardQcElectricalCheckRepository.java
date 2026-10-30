package com.pratikshat.exicom.repository;

import com.pratikshat.exicom.entity.BtJobCardQcElectricalCheck;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BtJobCardQcElectricalCheckRepository extends JpaRepository<BtJobCardQcElectricalCheck,Long>{

	@Query(value = "select value from bt_job_card_qc_fault_observedduringpdi",nativeQuery = true)
	public List<Map<String, Object>> getBtJobCardQcFaultObserved();
    
}
