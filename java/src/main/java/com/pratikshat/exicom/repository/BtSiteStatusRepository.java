package com.pratikshat.exicom.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.BtSiteStatus;

@Repository
public interface BtSiteStatusRepository extends JpaRepository<BtSiteStatus, Integer>{
	
	List<BtSiteStatus> findAll();
	
	@Query("select ct from BtSiteStatus ct where ct.id=:id")
	BtSiteStatus getBySiteStatusWithId(@Param("id") int id);
	

}
