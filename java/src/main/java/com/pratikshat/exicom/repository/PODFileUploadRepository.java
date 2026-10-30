package com.pratikshat.exicom.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.PODFileUpload;
import com.pratikshat.exicom.model.response.PodFileUploadResponse;
import com.pratikshat.exicom.repository.custom.PODFileUploadRepositoryCustomer;

@Repository
public interface PODFileUploadRepository extends JpaRepository<PODFileUpload, Long>,PODFileUploadRepositoryCustomer{
	
//	@Query("select new com.pratikshat.exicom.model.response.PodFileUploadResponse(pod.id,pod.fileUrl) from PODFileUpload pod join pod.btCase cs where cs.id=:caseId ")
//	Set<PodFileUploadResponse> getBtCaseID(long caseId);

}
