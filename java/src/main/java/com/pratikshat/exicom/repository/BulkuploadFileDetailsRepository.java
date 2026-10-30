package com.pratikshat.exicom.repository;

import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.BulkuploadFileDetails;

@Repository
public interface BulkuploadFileDetailsRepository extends JpaRepository<BulkuploadFileDetails, Long> {
	@Query(value="select * from bulkupload_file_details where id=:id",nativeQuery=true)
	Map<String,Object> getById(@Param("id")Long id);
}
