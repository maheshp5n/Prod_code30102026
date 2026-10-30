package com.pratikshat.exicom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.RequisitionRecentVisit;

@Repository
public interface RequisitionRecentVisitRepository extends JpaRepository<RequisitionRecentVisit, Long>{

	RequisitionRecentVisit findByRequisitionIdAndVisitorId(long requisitionId,String userEmailid);
}
