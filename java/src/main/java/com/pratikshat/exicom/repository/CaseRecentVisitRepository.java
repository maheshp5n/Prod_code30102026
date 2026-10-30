package com.pratikshat.exicom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.CaseRecentVisit;
@Repository
public interface CaseRecentVisitRepository extends JpaRepository<CaseRecentVisit, Long> {
	com.pratikshat.exicom.entity.CaseRecentVisit findByCaseIdAndVisitorId(long caseId,String userEmailid);
}
