package com.pratikshat.exicom.repository;

import com.pratikshat.exicom.entity.DctRepairBulkConsumption;
import com.pratikshat.exicom.repository.custom.DctRepairBulkConsumptionRepositoryCustom;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DctRepairBulkConsumptionRepository extends JpaRepository<DctRepairBulkConsumption,Long>,DctRepairBulkConsumptionRepositoryCustom {
    
}
