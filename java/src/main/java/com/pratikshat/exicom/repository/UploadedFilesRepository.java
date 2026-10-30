package com.pratikshat.exicom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.UploadedFiles;

@Repository
public interface UploadedFilesRepository extends JpaRepository<UploadedFiles, Long> {

	@Query(value="SELECT uf.`id` AS id, uf.`file_name` AS fileName,\r\n" + 
			"	uf.`file_download_uri` AS fileDownloadUri,\r\n" + 
			"	uf.`file_content_type` AS fileContentType,\r\n" + 
			"	uf.`file_size` AS fileSize,\r\n" + 
			"	uf.`created_date` AS createdDate,\r\n" + 
			"	emp.`first_name`AS createdByFirstName,\r\n" + 
			"	emp.`last_name` AS createdByLastName,\r\n" + 
			"	emp.`middle_name` AS createdByMiddleName,\r\n" + 
			"	emp.`email_id` AS createdByEmailId \r\n" + 
			"	FROM `uploaded_files` uf\r\n" + 
			"	JOIN `employee_details` emp ON emp.`email_id`= uf.`created_by_email_id` WHERE uf.`is_deleted`=false AND uf.`object_name`=:objectName AND uf.`object_id`=:objectId",nativeQuery=true)
	java.util.List<java.util.Map<String,Object>> findByObjectNameAndObjectId(@Param("objectName")String objectName,@Param("objectId")String objectId);
	
	@Query(value="SELECT uf.`id` AS id, uf.`file_name` AS fileName,\r\n" + 
			"	uf.`file_download_uri` AS fileDownloadUri,\r\n" + 
			"	uf.`file_content_type` AS fileContentType,\r\n" + 
			"	uf.`file_size` AS fileSize,\r\n" + 
			"	uf.`created_date` AS createdDate,\r\n" + 
			"	emp.`first_name`AS createdByFirstName,\r\n" + 
			"	emp.`last_name` AS createdByLastName,\r\n" + 
			"	emp.`middle_name` AS createdByMiddleName,\r\n" + 
			"	emp.`email_id` AS createdByEmailId \r\n" + 
			"	FROM `uploaded_files` uf\r\n" + 
			"	JOIN `employee_details` emp ON emp.`email_id`= uf.`created_by_email_id` WHERE uf.`is_deleted`=false AND uf.`id`=:id",nativeQuery=true)
	java.util.Map<String,Object> findById(@Param("id")long id);
	
	@Query(value="select uf from UploadedFiles uf left join uf.createdBy cby left join uf.modifiedBy mby where uf.isDeleted=false and uf.id=:id")
	UploadedFiles getById(@Param("id")long id);
}
