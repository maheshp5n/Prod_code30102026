package com.pratikshat.exicom.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.PrcProblemReported;

@Repository
public interface PrcProblemReportedRepository extends JpaRepository<PrcProblemReported, Long> {

	@Query("select new com.pratikshat.exicom.entity.PrcProblemReported(pr.probleReportedId,pr.problemName) from PrcProblemReported pr join pr.preRegistrationCases cs where cs.id=:caseId")
	java.util.Set<com.pratikshat.exicom.entity.PrcProblemReported> getByCaseId(@Param("caseId")long caseid);
	
	@Modifying
	@Transactional
	int deleteByPreRegistrationCases_Id(long caseId);
}
