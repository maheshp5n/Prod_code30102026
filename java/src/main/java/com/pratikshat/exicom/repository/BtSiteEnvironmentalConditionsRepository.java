package com.pratikshat.exicom.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.BtSiteEnvironmentalConditions;
import com.pratikshat.exicom.entity.BtSiteType;

@Repository
public interface BtSiteEnvironmentalConditionsRepository extends JpaRepository<BtSiteEnvironmentalConditions, Long> {
	
	@Query("select ct from BtSiteEnvironmentalConditions ct where ct.id=:id")
	BtSiteEnvironmentalConditions getBySiteEnvironmentalConditionsWithId(@Param("id") long l);
	
	@Query(value = "SELECT 	sec.`id` AS id,	sec.`type_name` AS sitevalue FROM `bt_site_environmental_conditions` sec" , nativeQuery = true )
	public List<Map<String, Object>> getBtSiteEnvironmentalConditions();	

}
