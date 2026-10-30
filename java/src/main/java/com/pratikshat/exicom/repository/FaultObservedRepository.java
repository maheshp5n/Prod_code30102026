package com.pratikshat.exicom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.FaultObserved;

@Repository
public interface FaultObservedRepository extends JpaRepository<FaultObserved, Integer> {
	@Query("select new com.pratikshat.exicom.model.response.BrcfaultObservedResponse(falt) from FaultObserved falt left join falt.domain")
	java.util.List<com.pratikshat.exicom.model.response.BrcfaultObservedResponse> getAll();
	
}
