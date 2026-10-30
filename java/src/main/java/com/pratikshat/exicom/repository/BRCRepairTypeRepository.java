package com.pratikshat.exicom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.BRCRepairType;

@Repository
public interface BRCRepairTypeRepository extends JpaRepository<BRCRepairType, Integer> {

}
