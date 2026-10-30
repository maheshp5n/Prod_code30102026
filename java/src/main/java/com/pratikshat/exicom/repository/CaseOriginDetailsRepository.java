package com.pratikshat.exicom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.CaseOriginDetails;

@Repository
public interface CaseOriginDetailsRepository extends JpaRepository<com.pratikshat.exicom.entity.CaseOriginDetails,Long> {
	
	@Query("select co from CaseOriginDetails co where co.caseOrignName=:originName")
	CaseOriginDetails getCaseOriginByOriginName(@Param("originName")String originName);
}
