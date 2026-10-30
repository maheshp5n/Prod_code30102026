package com.pratikshat.exicom.repository;


import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.CaseFilesUpload;
import com.pratikshat.exicom.repository.custom.CaseFilesUploadRepositoryCustome;

@Repository
public interface CaseFilesUploadRepository extends JpaRepository<CaseFilesUpload, Long>,CaseFilesUploadRepositoryCustome {
	@Query(value="select * from case_files_upload where case_details_case_id=:id",nativeQuery=true)
	List<Map<String,Object>> getFileUploadedOncaseByCaseId(@Param("id")Long caseId);
	@Query(value="select * from case_files_upload where case_details_case_id=:caseid and id=:id",nativeQuery=true)
	Map<String,Object> getFilesByCaseIdAndUploadId(@Param("caseid")Long caseid,@Param("id")Long fileuploadid);

}
