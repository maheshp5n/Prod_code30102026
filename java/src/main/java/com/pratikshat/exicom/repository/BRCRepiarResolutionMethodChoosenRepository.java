package com.pratikshat.exicom.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.BRCRepiarResolutionMethod;
import com.pratikshat.exicom.entity.BRCRepiarResolutionMethodChoosen;
import com.pratikshat.exicom.model.response.BRCRepiarResolutionMethodChoosenResponse;

@Repository
public interface BRCRepiarResolutionMethodChoosenRepository extends JpaRepository<BRCRepiarResolutionMethodChoosen,Long> {
	
	@Query("select new com.pratikshat.exicom.model.response.BRCRepiarResolutionMethodChoosenResponse(rmt.resolutionMethodId,rmt.resolutionMethodName) from BRCRepiarResolutionMethodChoosen chs left join chs.resolutionMethods rmt left join chs.repariItem item where item.repairId=:repairId")
	Set<BRCRepiarResolutionMethodChoosenResponse> getResolutionMethodByRepairId(@Param("repairId")long repairId);
}
