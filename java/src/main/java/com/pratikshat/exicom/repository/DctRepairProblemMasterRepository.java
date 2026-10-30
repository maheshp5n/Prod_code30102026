package com.pratikshat.exicom.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pratikshat.exicom.entity.DctRepairProblemMaster;

public interface DctRepairProblemMasterRepository extends JpaRepository<DctRepairProblemMaster, Long> {
	@Query(value="select * from dct_repair_problem_master where product_product_part_code=:partCode",nativeQuery=true)
	List<Map<String,Object>> getProblemAndResultionByProductPartcode(@Param("partCode") String productPartcode);
	
	@Query(value="select * from dct_repair_problem_master",nativeQuery=true)
	List<Map<String,Object>> getProblemAndResultion();

}
