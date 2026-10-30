package com.pratikshat.exicom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.CasePriorityDetails;
import com.pratikshat.exicom.entity.PendingResponsibility;

@Repository
public interface PendingResponsibilityRepository extends JpaRepository<PendingResponsibility, String> {
		List<PendingResponsibility> findAll();
		
		@Query("select pr from PendingResponsibility pr where pr.PendingResponsibilityName=:pendingResponsibilityName")
		PendingResponsibility getPendingResponsibilityByName(@Param("pendingResponsibilityName")String pendingResponsibilityName);
}
