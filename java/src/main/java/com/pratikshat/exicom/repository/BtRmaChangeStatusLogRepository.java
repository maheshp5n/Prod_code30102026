package com.pratikshat.exicom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.BtRmaChangeStatusLog;
import com.pratikshat.exicom.repository.custom.BtRmaChangeStatusLogCustom;
import com.pratikshat.exicom.repository.custom.BtRmaRejectLogCustom;

@Repository
public interface BtRmaChangeStatusLogRepository extends JpaRepository<BtRmaChangeStatusLog, Long> ,BtRmaChangeStatusLogCustom{

}
