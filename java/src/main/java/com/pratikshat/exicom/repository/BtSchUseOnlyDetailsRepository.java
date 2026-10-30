package com.pratikshat.exicom.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.BtSchUseOnlyDetails;
//import com.pratikshat.exicom.model.response.BtSchOnlyResponse;
import com.pratikshat.exicom.model.response.BtSchOnlyResponseCopy;

@Repository
public interface BtSchUseOnlyDetailsRepository extends JpaRepository<BtSchUseOnlyDetails, Long>,BtSchUseOnlyDetailsRepositoryCustom{
	
	public BtSchUseOnlyDetails findById(long id);
	
//	@Query("select new com.pratikshat.exicom.model.response.BtSchOnlyResponse(sch) from BtSchUseOnlyDetails sch join sch.btServiceAppointment ser where ser.serviceAppointmentId=:id")
//	Set<BtSchOnlyResponse> getResponseByServiceAppId(@Param("id") long id);
	
	@Query("select new com.pratikshat.exicom.model.response.BtSchOnlyResponseCopy(sch) from BtSchUseOnlyDetails sch join sch.btServiceAppointment ser  where ser.serviceAppointmentId=:id")
	Page<BtSchOnlyResponseCopy> getResponsByServiceAppId(@Param("id") long id,Pageable pageable);
	
}
