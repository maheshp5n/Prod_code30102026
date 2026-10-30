package com.pratikshat.exicom.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import com.pratikshat.exicom.entity.BtGlcodeAndCostcenter;

@Repository
public interface BtGLCodeAndCostCenterRepository extends JpaRepository<BtGlcodeAndCostcenter, Long> {

	@Query(value ="Select gc.`cost_center_and_gl_code` as costCenterAndGlCode from bt_glcode_and_costcenter gc where gc.`is_deleted`=false" ,nativeQuery = true)
	List<Map<String,Object>> getByGlCodeAndCostCenter();
	
	
	
}
