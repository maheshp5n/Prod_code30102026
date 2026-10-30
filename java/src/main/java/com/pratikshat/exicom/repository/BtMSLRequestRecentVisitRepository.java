package com.pratikshat.exicom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.BtMSLRequestRecentVisit;
import com.pratikshat.exicom.entity.MSLRequestRecentVisit;

@Repository
public interface BtMSLRequestRecentVisitRepository extends JpaRepository<BtMSLRequestRecentVisit, Long> {

	BtMSLRequestRecentVisit findByIdAndVisitorId(long btMslId,String userEmailid);
}
