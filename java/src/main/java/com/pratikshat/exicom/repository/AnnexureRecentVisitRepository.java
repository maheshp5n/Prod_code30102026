package com.pratikshat.exicom.repository;

import com.pratikshat.exicom.entity.AnnexureRecentVisit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnexureRecentVisitRepository extends JpaRepository<AnnexureRecentVisit,Long> {

	AnnexureRecentVisit findByAnnexureIdAndVisitorEmailId(long annexureId,String username);
}
