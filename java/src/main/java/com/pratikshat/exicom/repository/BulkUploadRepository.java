package com.pratikshat.exicom.repository;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.Bulkupload;

@Repository
public interface BulkUploadRepository extends JpaRepository<Bulkupload, Long> {
	@Query(value="select * from bulkupload where assigned_to like :roles or assigned_to like :userid",nativeQuery=true,countQuery = "select count(*) from bulkupload where assigned_to like :roles or assigned_to like :userid")
	Page<Map<String,Object>> getBulkuploadOptions(@Param("roles")String roles,@Param("userid")String userid,Pageable page);
	@Query(value="select * from bulkupload where id=:id",nativeQuery=true)
	Map<String,Object> getBulkuploadById(@Param("id")Long id);
}
