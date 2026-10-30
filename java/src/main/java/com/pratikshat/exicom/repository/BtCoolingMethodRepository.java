package com.pratikshat.exicom.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.BtCoolingMethod;
import com.pratikshat.exicom.entity.BtSiteEnvironmentalConditions;

@Repository
public interface BtCoolingMethodRepository extends JpaRepository<BtCoolingMethod, Long> {
	
	@Query("select ct from BtCoolingMethod ct where ct.id=:id")
	BtCoolingMethod getByCoolingMethodWithId(@Param("id") long l);
	
	@Query(value="SELECT cm.`id` AS id,	cm.`type_value` AS coolingValue	FROM `bt_cooling_method` cm ", nativeQuery = true)
	public List<Map<String,Object>> getBtCoolingMethod();

}
