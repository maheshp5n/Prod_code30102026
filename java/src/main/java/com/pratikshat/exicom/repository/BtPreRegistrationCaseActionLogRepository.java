package com.pratikshat.exicom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.BtPreRegistrationCaseActionLog;

@Repository
public interface BtPreRegistrationCaseActionLogRepository extends JpaRepository<BtPreRegistrationCaseActionLog, Long>,BtPreRegistrationCaseActionLogRepositoryCustom {

}
