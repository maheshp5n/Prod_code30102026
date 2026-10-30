package com.pratikshat.exicom.repository;

import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.BtPreRegisterReport;
import com.pratikshat.exicom.model.response.BtCaseProblemResponse;
import com.pratikshat.exicom.model.response.BtPreRegisterReportResponse;
import com.pratikshat.exicom.repository.custom.BtPreRegisterReportRepositoryCustom;

@Repository				
public interface BtPreRegisterReportRepository extends JpaRepository<BtPreRegisterReport, Long>,BtPreRegisterReportRepositoryCustom{
	
	@Modifying
	@Query("delete BtPreRegisterReport pr where pr.preRegistrationCases.id=:id")
	public void deleteReport(long id);
	
	@Query("select pr from BtPreRegisterReport pr join pr.preRegistrationCases cs where cs.id=:id")
	public List<BtPreRegisterReport> getListOfReport(long id);
	
	@Query("select new com.pratikshat.exicom.model.response.BtPreRegisterReportResponse(pr.probleReportedId,pr.problemName) from BtPreRegisterReport pr join pr.preRegistrationCases cs where cs.id=:caseId")
	Set<BtPreRegisterReportResponse> getByCaseId(@Param("caseId")long caseid);

	
}
