package com.pratikshat.exicom.repository;

import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.BtFsrFileUpload;
import com.pratikshat.exicom.repository.custom.BtFsrFileUploadCustom;

@Repository
public interface BtFsrFileUploadRepositry extends JpaRepository<BtFsrFileUpload, Long>,BtFsrFileUploadCustom {

	@Query(value = "SELECT * FROM `bt_fsr_file_upload` fup WHERE fup.`appointment_service_appointment_id`=:serAppId AND fup.`id`=:id ",nativeQuery = true)
	Map<String,Object> getByServiceAppointmentAndFileUploadId(@Param("serAppId") long serAppId,@Param("id") long id);

}
