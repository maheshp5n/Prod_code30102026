package com.pratikshat.exicom.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.BtPowerPlantMake;
import com.pratikshat.exicom.entity.BtSiteType;

@Repository
public interface BtPowerPlantMakeRepository extends JpaRepository<BtPowerPlantMake, Long> {
	
	@Query("select ct from BtPowerPlantMake ct where ct.id=:id")
	BtPowerPlantMake getByPowerPlantMakeWithId(@Param("id") long l);
	
	@Query(value="SELECT  ppm.`id` AS id, ppm.`type_value` AS powerValue FROM `bt_power_plant_make` ppm", nativeQuery = true)
	public List<Map<String,Object>> getBtPowerPlantMake();

}
