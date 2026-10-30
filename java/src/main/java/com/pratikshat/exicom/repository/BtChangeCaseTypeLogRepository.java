package com.pratikshat.exicom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.BtChangeCaseTypeLog;
import com.pratikshat.exicom.repository.custom.BtChangeCaseTypeLogRepositoryCustom;

@Repository
public interface BtChangeCaseTypeLogRepository extends JpaRepository<BtChangeCaseTypeLog, Long>,BtChangeCaseTypeLogRepositoryCustom{

}
