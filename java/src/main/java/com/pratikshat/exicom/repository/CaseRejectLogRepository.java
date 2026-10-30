package com.pratikshat.exicom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.CaseRejectLog;
import com.pratikshat.exicom.model.response.CaseRejectLogResponse;;

@Repository
public interface CaseRejectLogRepository extends JpaRepository<CaseRejectLog, Long> {

	@Query("select new com.pratikshat.exicom.model.response.CaseRejectLogResponse(crl,uf) from CaseRejectLog crl left join crl.createdBy cby left join UploadedFiles uf on crl.file=uf.fileDownloadUri where crl.caseManualId=:caseManualId")
	List<CaseRejectLogResponse> getCaseRejectLogByCaseManualId(@Param("caseManualId")String caseManualId);

}
