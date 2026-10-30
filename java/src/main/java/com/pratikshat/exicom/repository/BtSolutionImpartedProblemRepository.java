package com.pratikshat.exicom.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.BtSolutionImpartedProblem;
import com.pratikshat.exicom.model.response.BtSolutionImpartedProblemResponse;

@Repository
public interface BtSolutionImpartedProblemRepository extends JpaRepository<BtSolutionImpartedProblem, Integer> {
	
	@Query("select new com.pratikshat.exicom.model.response.BtSolutionImpartedProblemResponse(solPrblm) from BtSolutionImpartedProblem solPrblm join solPrblm.btSchUseOnlyDetails schOnly join schOnly.btServiceAppointment  ser where ser.serviceAppointmentId=:id")
	Set<BtSolutionImpartedProblemResponse> getResponseBySolutionId(@Param("id") long serviceAppId   );
	
	@Query("select new com.pratikshat.exicom.model.response.BtSolutionImpartedProblemResponse(solPrblm) from BtSolutionImpartedProblem solPrblm join solPrblm.btSchUseOnlyDetails schOnly where schOnly.id=:id")
	Set<BtSolutionImpartedProblemResponse> getResponseForSolutionId(@Param("id") long id   );

}
