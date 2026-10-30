package com.pratikshat.exicom.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.BtCaseFileUpload;
import com.pratikshat.exicom.repository.custom.BtCaseFileUploadRepositoryCustome;

@Repository
public interface BtCaseFileUploadRepository extends JpaRepository<BtCaseFileUpload, Long>,BtCaseFileUploadRepositoryCustome {
	
	@Query(value="select * from bt_case_file_upload where id=:id",nativeQuery=true)
	Map<String,Object> getById(@Param("id")Long id);
	
	
	@Query(value="select * from bt_case_file_upload where bt_case_id=:btCaseId",nativeQuery=true)
	List<Map<String,Object>> getByBtCaseId(@Param("btCaseId")Long btCaseId);
	
}
