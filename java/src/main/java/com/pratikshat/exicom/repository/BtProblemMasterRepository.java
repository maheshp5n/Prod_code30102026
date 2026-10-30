package com.pratikshat.exicom.repository;

import java.util.List;

import org.jboss.logging.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pratikshat.exicom.entity.BtProblemMaster;

public interface BtProblemMasterRepository extends JpaRepository<BtProblemMaster, Integer>{
	
	@Query("select pm from BtProblemMaster pm")
	List<BtProblemMaster> getProblemMaster();
	@Query("select cp from BtProblemMaster cp where cp.type=:type")
	BtProblemMaster getByType(String type);

}
