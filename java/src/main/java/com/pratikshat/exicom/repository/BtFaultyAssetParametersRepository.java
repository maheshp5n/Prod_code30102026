package com.pratikshat.exicom.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.BtFaultyAssetParameters;
import com.pratikshat.exicom.entity.BtServiceAppointment;
import com.pratikshat.exicom.model.response.BtFaultyAssetParametersResponse;

@Repository
public interface BtFaultyAssetParametersRepository extends JpaRepository<BtFaultyAssetParameters, Long>,BtFaultyAssetParametersCustomRepository {

	
	@Query("select new com.pratikshat.exicom.model.response.BtFaultyAssetParametersResponse(fa) from BtFaultyAssetParameters fa left join fa.activeAlarms aca"
			+ " left join fa.btFsr fsr where fsr.id=:id")
	Set<BtFaultyAssetParametersResponse> getAssetsById(long id);
	
	@Modifying
	@Query(value = "DELETE FROM `bt_faulty_asset_parameters`  WHERE `bt_fsr_id`=:serviceFSRId",nativeQuery = true)
	void deleteByCommFSRId(@Param("serviceFSRId") long serviceFSRId);
	
	@Query(value ="SELECT COUNT(*) AS countNumber\r\n"
			+ "	FROM `bt_faulty_asset_parameters` fastp\r\n"
			+ "WHERE fastp.`bt_fsr_id` =:id",nativeQuery = true)
	long getFaultyAssetParameterCoumt(@Param("id") long id);
}
