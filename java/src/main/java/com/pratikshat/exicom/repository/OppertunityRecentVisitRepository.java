package com.pratikshat.exicom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.OppertunityRecentVisit;

@Repository
public interface OppertunityRecentVisitRepository extends JpaRepository<OppertunityRecentVisit, Long> {

	OppertunityRecentVisit findByOppertunityIdAndVisitorId(long oppertunityId,String userEmailid);
}
