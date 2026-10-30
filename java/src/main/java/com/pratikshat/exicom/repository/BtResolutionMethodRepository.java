package com.pratikshat.exicom.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.BtResolutionMethod;
import com.pratikshat.exicom.entity.BtSiteType;

@Repository
public interface BtResolutionMethodRepository extends JpaRepository<BtResolutionMethod, Integer>{
	
	@Query("select ct from BtResolutionMethod ct where ct.id=:id")
	BtResolutionMethod getByResolutionMethodWithId(@Param("id") long l);
	
	@Query(value = "SELECT rm.`id` AS id, rm.`name` AS resolutionMethodValue FROM `bt_resolution_method` rm" ,nativeQuery = true)
	public List<Map<String,Object>> getBtResolutionMethod();
	

}
