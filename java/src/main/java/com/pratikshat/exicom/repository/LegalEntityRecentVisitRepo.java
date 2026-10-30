package com.pratikshat.exicom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.LegalEntityReceintVisit;
@Repository
public interface LegalEntityRecentVisitRepo extends JpaRepository<LegalEntityReceintVisit, Long> {
	LegalEntityReceintVisit findByLegalEntityIdAndVisitorId(long legalEntityId,String visitorId);
}
