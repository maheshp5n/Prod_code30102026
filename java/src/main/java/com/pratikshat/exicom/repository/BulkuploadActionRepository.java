package com.pratikshat.exicom.repository;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.BulkUploadAction;

@Repository
public interface BulkuploadActionRepository extends JpaRepository<BulkUploadAction, Long> {
	
	@Query(value="SELECT bid.`id`,\r\n" + 
			"bid.`created_date`,\r\n" + 
			"bid.`last_mofidied_date`,\r\n" + 
			"bid.`request_document_id`,\r\n" + 
			"bid.`result_document_id`,\r\n" + 
			"bid.`upload_status`,\r\n" + 
			"bid.`bulk_upload_id`,\r\n" + 
			"rbfid.file_name AS raw_file_name,\r\n" + 
			"fbfid.file_name AS result_file_name\r\n" + 
			" FROM bulk_upload_action bid\r\n" + 
			" LEFT JOIN `bulkupload_file_details` rbfid ON rbfid.id=bid.`request_document_id` \r\n" + 
			" LEFT JOIN `bulkupload_file_details` fbfid ON fbfid.id=bid.`result_document_id` WHERE bid.bulk_upload_id=:bid AND (bid.owner_roles LIKE :roles OR bid.owner_roles LIKE :usrs)",nativeQuery=true,countQuery = "SELECT count(*) \r\n"+ 
					" FROM bulk_upload_action bid\r\n" + 
					" LEFT JOIN `bulkupload_file_details` rbfid ON rbfid.id=bid.`request_document_id` \r\n" + 
					" LEFT JOIN `bulkupload_file_details` fbfid ON fbfid.id=bid.`result_document_id` WHERE bid.bulk_upload_id=:bid AND (bid.owner_roles LIKE :roles OR bid.owner_roles LIKE :usrs)")
	Page<Map<String,Object>> getBulkuploadActionBy(@Param("bid")Long bulkUploadId,@Param("roles")String userRole,@Param("usrs")String userEmailId,Pageable page);
	
	@Query(value="select * from bulk_upload_action where id=:id",nativeQuery = true)
	Map<String,Object> getBulkuploadActionById(@Param("id")Long id);
	
}
