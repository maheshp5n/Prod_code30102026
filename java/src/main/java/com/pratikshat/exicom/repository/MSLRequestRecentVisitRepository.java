package com.pratikshat.exicom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.MSLRequestRecentVisit;

@Repository
public interface MSLRequestRecentVisitRepository extends JpaRepository<MSLRequestRecentVisit, Long>{

	MSLRequestRecentVisit findByMslIdAndVisitorId(long mslId,String userEmailid);
}
