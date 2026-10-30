package com.pratikshat.exicom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.BtCommissioningFSR;
import com.pratikshat.exicom.model.response.BtCommissioningFSRResponse;
import com.pratikshat.exicom.model.response.BtServiceFSRResponse;
import com.pratikshat.exicom.repository.custom.BtCommissioningFSRCustom;

@Repository
public interface BtCommissioningFSRRepository extends JpaRepository<BtCommissioningFSR, Long>,BtCommissioningFSRCustom{

	@Query("select new com.pratikshat.exicom.model.response.BtCommissioningFSRResponse(cfsr) "
			+ "from BtCommissioningFSR cfsr "
			+ "left join cfsr.btServiceAppointment sa "
			+ "left join cfsr.btSiteType siteTpe "
			+ "left join cfsr.btSiteEnvironmentalConditions ser "
			+ "left join cfsr.btCoolingMethod cm "
			+ "left join cfsr.btPowerPlantMake ppm "
			+ "left join cfsr.btPendingReasonCustomerBucket prcb "
			+ "where cfsr.id=:id")
	BtCommissioningFSRResponse getByCommissioningFSRId(@Param("id") long id);
	

	

}


