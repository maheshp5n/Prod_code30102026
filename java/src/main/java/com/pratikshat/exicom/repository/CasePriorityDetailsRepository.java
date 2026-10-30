package com.pratikshat.exicom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.CasePriorityDetails;
@Repository
public interface CasePriorityDetailsRepository extends JpaRepository<com.pratikshat.exicom.entity.CasePriorityDetails,Long> {

	@Query("select cp from CasePriorityDetails cp where cp.casePriorityName=:priorityName")
	CasePriorityDetails getCasePriorityByPriorityName(@Param("priorityName")String priorityName);
}
